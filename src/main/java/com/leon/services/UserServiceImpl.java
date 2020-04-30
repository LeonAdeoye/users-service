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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService
{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private Map<String,User> usersMap = new ConcurrentHashMap<>();

    @Autowired
    UserRepository userRepository;

    private Sort sortByUserIdAsc()
    {
        return new Sort(Sort.Direction.ASC, "userId");
    }

    @PostConstruct
    public void initialize()
    {
        usersMap = userRepository.findAll(sortByUserIdAsc()).stream().collect(Collectors.toMap(User::getUserId, user -> user));
        logger.info("Loaded users from persistence store during initialization into map: {}", usersMap);
    }

    @Override
    public User saveUser(User user)
    {
        user = userRepository.save(user);
        usersMap.put(user.getUserId(), user);
        return user;
    }

    @Override
    public List<User> getAllUsers()
    {
        return new ArrayList<>(usersMap.values());
    }

    @Override
    public List<User> getDeskUsers(String deskName)
    {
        return usersMap.values().stream().filter(user -> user.getDeskName().equalsIgnoreCase(deskName)).collect(toList());
    }

    @Override
    public User getUser(String userId)
    {
        return usersMap.get(userId);
    }
}
