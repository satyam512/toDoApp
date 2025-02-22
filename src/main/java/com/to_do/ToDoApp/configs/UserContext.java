package com.to_do.ToDoApp.configs;

import org.springframework.stereotype.Component;

@Component
public class UserContext {

    private static final ThreadLocal<String> usernameHolder = new ThreadLocal<>();

    public void setUserId(String userName) {
        usernameHolder.set(userName);
    }

    public String getUserId() {
        return usernameHolder.get();
    }

    public void clear() {
        usernameHolder.remove();
    }
}