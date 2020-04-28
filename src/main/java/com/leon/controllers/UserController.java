package com.leon.controllers;

import com.leon.models.User;
import com.leon.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import static org.springframework.web.bind.annotation.RequestMethod.*;


@RestController
public class UserController
{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @CrossOrigin
    @RequestMapping(value="/users", method=RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<User> getUsers(@RequestParam(required = false) final Optional<String> deskName)
    {
        if(deskName.isPresent() && deskName.get().trim().isEmpty())
        {
            logger.error("The desk name argument cannot be empty.");
            throw new IllegalArgumentException("desk name argument is invalid.");
        }

        logger.info("Received request to retrieve a list of users" + (deskName.isPresent() ? " belonging to a desk: '" + deskName.get() + "'." : " for all desks."));

        if(deskName.isPresent())
            return this.userService.getDeskUsers(deskName.get());
        else
            return this.userService.getAllUsers();
    }

    @CrossOrigin
    @RequestMapping(value="/user", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User getUser(@RequestParam final String userId)
    {
        if(userId == null || userId.isEmpty())
        {
            logger.error("The userId argument cannot be null or empty.");
            throw new IllegalArgumentException("userId argument is invalid");
        }

        logger.info("Received request to retrieve user details using the user id: '{}'", userId);
        return this.userService.getUser(userId);
    }

    @CrossOrigin
    @RequestMapping(value="/user", method={PUT, POST}, consumes=MediaType.APPLICATION_JSON_VALUE)
    public void saveUser(@RequestBody final User user)
    {
        if(user == null)
        {
            logger.error("The user argument cannot be null.");
            throw new NullPointerException("user argument is null");
        }

        logger.info("Received request to persist user: '{}', and user: '{}', and action: '{}'", user);
        this.userService.saveUser(user);
    }
}