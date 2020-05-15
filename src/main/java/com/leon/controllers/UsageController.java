package com.leon.controllers;

import com.leon.models.Usage;
import com.leon.services.UsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class UsageController
{
    private static final Logger logger = LoggerFactory.getLogger(UsageController.class);

    @Autowired
    private UsageService usageService;

    @CrossOrigin
    @RequestMapping(value="/usage", method={POST,PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Usage saveUsage(@RequestParam final String app,@RequestParam final String user, @RequestParam final String action)
    {
        if(app == null || app.trim().isEmpty())
        {
            logger.error("The app argument cannot be null or empty.");
            throw new IllegalArgumentException("app argument is invalid");
        }

        if(user == null || user.trim().isEmpty())
        {
            logger.error("The user argument cannot be null or empty.");
            throw new IllegalArgumentException("user argument is invalid");
        }

        if(action == null || action.trim().isEmpty())
        {
            logger.error("The action argument cannot be null or empty.");
            throw new IllegalArgumentException("action argument is invalid");
        }

        logger.info("Received request to persist usage data point for app: '{}', and user: '{}', and action: '{}'", app, user, action);
        return this.usageService.saveUsage(new Usage(app, user, action));
    }

    @CrossOrigin
    @RequestMapping(value="/usage", method=RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Usage> getUsage(@RequestParam final String app, @RequestParam(required=false) final Optional<String> user)
    {
        if(app == null || app.trim().isEmpty())
        {
            logger.error("The app argument cannot be null or empty.");
            throw new IllegalArgumentException("app argument is invalid");
        }

        if(user.isPresent() && user.get().trim().isEmpty())
        {
            logger.error("The user argument cannot be empty.");
            throw new IllegalArgumentException("user argument is empty");
        }

        logger.info("Received request to retrieve usage data for app: '{}' and user: '{}'", app, (user.isPresent() ? user.get() : "empty"));
        return this.usageService.getUsage(app, user);
    }

    @CrossOrigin
    @RequestMapping(value="/usage/apps", method=RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public Set<String> getUsageApps()
    {
        logger.info("Received request to retrieve usage apps.");
        return this.usageService.getUsageApps();
    }
}