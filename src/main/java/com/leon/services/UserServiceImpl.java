package com.leon.services;

import com.leon.models.User;
import com.leon.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void initialize()
    {

    }

    @Override
    public void saveUser(User user)
    {
        userRepository.save(user);
    }

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
