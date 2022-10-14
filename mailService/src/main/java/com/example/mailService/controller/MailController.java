package com.example.mailService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.mailService.model.MailDetails;
import com.example.mailService.model.MailModel;
import com.example.mailService.service.MailService;

@RestController
public class MailController {

    @Autowired
    MailService mailService;

    @PostMapping
    public String mailer(@RequestBody List<MailDetails> mailDetails) {
        try {
            return mailService.sendMail(mailDetails);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
        }
    }

    @PostMapping("/sendrandom")
    public int sendPwd(@RequestBody MailModel mailModel) {
        try {
            return mailService.sendPasswordMail(mailModel);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, exception.getMessage());
        }
    }
}
