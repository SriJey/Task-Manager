package com.example.mailService.model;

import java.util.List;

import lombok.Data;

@Data
public class MailDetails {
    private String reciverMailId;
    private String ccReciverMailId;
    private List<TaskStatus> taskStatus;
}
