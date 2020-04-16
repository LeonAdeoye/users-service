package com.leon.controllers;

import com.leon.services.ConfigurationService;
import com.leon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    }

    @CrossOrigin
    @RequestMapping(value="/usage", method=RequestMethod.POST)
    public void saveUsage(@RequestParam String app,@RequestParam String user, @RequestParam String action, @RequestParam boolean countUsage)
    {
        logger.info("Received request to persist usage data point for app: {}, and user: {}", app, user);
        this.userService.saveUsage(app, user, action, countUsage);
    }

    @CrossOrigin
    @RequestMapping(value="/usage", method=RequestMethod.GET)
    public void getUsage(@RequestParam String app)
    {
        logger.info("Received request to retrieve app usage data for app: {}", app);
        this.userService.getUsage(app);
    }
}