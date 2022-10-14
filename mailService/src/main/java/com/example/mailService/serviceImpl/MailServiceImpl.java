package com.example.mailService.serviceImpl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.mailService.model.MailDetails;
import com.example.mailService.model.MailModel;
import com.example.mailService.model.TaskStatus;
import com.example.mailService.service.MailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public String sendMail(List<MailDetails> mailDetails) {

        for (MailDetails mailDetails2 : mailDetails) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    sendTaskMail(mailDetails2);
                }
            };
            thread.start();

        }
        return "Sent";

    }

    @Override
    public int sendPasswordMail(MailModel mailModel) throws Exception {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sendPwdMail(mailModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        return 200;
    }

    // thread methods
    private void sendPwdMail(MailModel mailModel) throws Exception {
        System.out.println("sending mail");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setText(mailModel.getMessage(), true);
        mimeMessageHelper.setTo(mailModel.getRecieverMail());
        mimeMessageHelper.setSubject(mailModel.getSubject());
        javaMailSender.send(mimeMessage);
        System.out.println("sent");
    }

    private void sendTaskMail(MailDetails mailDetails) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            String mailContent = "";
            for (TaskStatus taskStatus : mailDetails.getTaskStatus()) {
                mailContent += "<p><span>" + taskStatus.getTaskQuestion() + " </span><span style='color:green' >"
                        + taskStatus.getStatus()
                        + "</span>&nbsp<span>" +
                        LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(taskStatus.getCompleted()))
                        + "</span>&nbsp<span>" +
                        LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(taskStatus.getDeadline()))
                        + "</span></p>";
            }
            mimeMessageHelper.setText(mailContent, true);
            mimeMessageHelper.setTo(mailDetails.getReciverMailId());
            mimeMessageHelper.setCc(mailDetails.getCcReciverMailId());
            mimeMessageHelper.setSubject("demo subject");
            javaMailSender.send(mimeMessage);
            System.out.println("Sent to -> " + mailDetails.getCcReciverMailId());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
