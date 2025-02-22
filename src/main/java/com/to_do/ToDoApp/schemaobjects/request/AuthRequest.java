package com.to_do.ToDoApp.schemaobjects.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}