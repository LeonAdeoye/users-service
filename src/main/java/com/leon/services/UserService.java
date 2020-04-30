package com.leon.services;

import com.leon.models.User;
import java.util.List;

public interface UserService
{
    List<User> getAllUsers();
    List<User> getDeskUsers(String deskName);
    User getUser(String userId);
    void initialize();
    User saveUser(User user);
}
