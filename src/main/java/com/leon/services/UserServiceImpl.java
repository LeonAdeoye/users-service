package com.leon.services;

import com.leon.models.User;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService
{
    @Override
    public List<User> getAllUsers()
    {
        return new ArrayList<>();
    }

    @Override
    public List<User> getDeskUsers(String desk)
    {
        return new ArrayList<>();
    }

    @Override
    public User getUser(String userId)
    {
        return null;
    }
}
