package com.to_do.ToDoApp.schemaobjects.request;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
}
