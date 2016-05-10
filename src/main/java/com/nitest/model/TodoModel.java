package com.nitest.model;

import lombok.Data;

@Data
public class TodoModel {
    private Long id;
    private String subject;
    private String detail;
    private String status;
}
