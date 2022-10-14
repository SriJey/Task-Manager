package com.example.mailService.service;

import java.util.List;

import com.example.mailService.model.MailDetails;
import com.example.mailService.model.MailModel;

public interface MailService {
    String sendMail(List<MailDetails> mailDetails);

    int sendPasswordMail(MailModel mailModel) throws Exception;
}
