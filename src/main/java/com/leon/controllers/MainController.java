package com.leon.controllers;

import com.leon.services.ConfigurationService;
import com.leon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class MainController
{
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private UserService userService;

    @CrossOrigin
    @RequestMapping("/reconfigure")
    public void reconfigure()
    {
        logger.info("Received request to reconfigure.");
        this.configurationService.reconfigure();
        this.userService.initialize();
    }

    @CrossOrigin
    @RequestMapping(value="/usage", method={POST})
    public void saveUsage(@RequestParam String app,@RequestParam String user, @RequestParam String action)
    {
        if(app == null || app.isEmpty())
        {
            logger.error("The app argument cannot be null or empty.");
            throw new IllegalArgumentException("app argument is invalid");
        }

        if(user == null || user.isEmpty())
        {
            logger.error("The user argument cannot be null or empty.");
            throw new IllegalArgumentException("user argument is invalid");
        }

        if(action == null || action.isEmpty())
        {
            logger.error("The action argument cannot be null or empty.");
            throw new IllegalArgumentException("action argument is invalid");
        }

        logger.info("Received request to persist usage data point for app: {}, and user: {}", app, user);
        this.userService.saveUsage(app, user, action);
    }

    @CrossOrigin
    @RequestMapping(value="/usage", method=RequestMethod.GET)
    public void getUsage(@RequestParam String app, @RequestParam Optional<String> user)
    {
        if(app == null || app.isEmpty())
        {
            logger.error("The app argument cannot be null or empty.");
            throw new IllegalArgumentException("app argument is invalid");
        }

        if(user.isPresent() && user.get() == "")
        {
            logger.error("The user argument cannot be empty.");
            throw new IllegalArgumentException("user argument is empty");
        }

        logger.info("Received request to retrieve app usage data for app: {} and user: {}", app, user.get());
        this.userService.getUsage(app, user);
    }
}