package com.leon.services;

import com.leon.models.User;
import com.leon.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService
{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private List<User> users = new ArrayList<>();

    @Autowired
    UserRepository userRepository;

    private Sort sortByDeskNameAsc()
    {
        return new Sort(Sort.Direction.ASC, "deskName");
    }

    @PostConstruct
    public void initialize()
    {
        users = userRepository.findAll(sortByDeskNameAsc());
        logger.info("Loaded {} users from persistence store during initialization.", users.size());
    }

    @Override
    public void saveUser(User user)
    {
        userRepository.save(user);
        users = userRepository.findAll(sortByDeskNameAsc());
    }

    @Override
    public List<User> getAllUsers()
    {
        return users;
    }

    @Override
    public List<User> getDeskUsers(String deskName)
    {
        return users.stream().filter(user -> user.getDeskName().equals(deskName)).collect(toList());
    }

    @Override
    public User getUser(String userId)
    {
        return users.stream().filter(user -> user.getUserId().equals(userId)).findFirst().orElse(null);
    }
}
