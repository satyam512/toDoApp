package com.to_do.ToDoApp.configs;

import org.springframework.stereotype.Component;

@Component
public class UserContext {

    private static final ThreadLocal<Integer> userIdHolder = new ThreadLocal<>();

    public void setUserId(Integer userId) {
        userIdHolder.set(userId);
    }

    public Integer getUserId() {
        return userIdHolder.get();
    }

    public void clear() {
        userIdHolder.remove();
    }
}