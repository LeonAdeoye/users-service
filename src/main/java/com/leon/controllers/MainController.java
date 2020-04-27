package com.leon.controllers;

import com.leon.services.ConfigurationService;
import com.leon.services.UsageService;
import com.leon.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class MainController
{
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private UsageService usageService;
    @Autowired
    private UserService userService;

    @CrossOrigin
    @RequestMapping("/reconfigure")
    public void reconfigure()
    {
        logger.info("Received request to reconfigure.");
        this.configurationService.reconfigure();
        this.usageService.initialize();
        this.userService.initialize();
    }

    @CrossOrigin
    @RequestMapping("/heartbeat")
    public String heartBeat()
    {
        logger.debug("Received heartbeat request and will respond.");
        return "I am still here!";
    }
}