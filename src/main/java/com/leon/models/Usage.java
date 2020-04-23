package com.leon.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document("Usage")
public class Usage
{
    @Id
    private String id;
    private String app;
    private String user;
    private String action;
    private Date lastUsedTimeStamp;
    private List<OptionalInt> monthlyCount;

    public Usage()
    {
        this.id = "";
        this.app = "";
        this.user = "";
        this.action = "";
        lastUsedTimeStamp = new Date();
        this.monthlyCount = new ArrayList<>(Collections.nCopies(12, OptionalInt.of(0)));
    }

    public Usage(String app, String user, String action, List<OptionalInt> monthlyCount)
    {
        this.id = "";
        this.app = app;
        this.user = user;
        this.action = action;
        this.lastUsedTimeStamp = new Date();
        this.monthlyCount = new ArrayList<>(monthlyCount);
    }

    public Usage(String id, String app, String user, String action, List<OptionalInt> monthlyCount)
    {
        this.id = id;
        this.app = app;
        this.user = user;
        this.action = action;
        this.lastUsedTimeStamp = new Date();
        this.monthlyCount = new ArrayList<>(monthlyCount);
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getApp()
    {
        return app;
    }

    public void setApp(String app)
    {
        this.app = app;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public List<OptionalInt> getMonthlyCount()
    {
        return monthlyCount;
    }

    public void setMonthlyCount(List<OptionalInt> monthlyCount)
    {
        this.monthlyCount = monthlyCount;
    }

    public Date getLastUsedTimeStamp()
    {
        return lastUsedTimeStamp;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Usage usage = (Usage) o;
        return id.equals(usage.id) &&
                app.equals(usage.app) &&
                lastUsedTimeStamp.equals(usage.lastUsedTimeStamp) &&
                user.equals(usage.user) &&
                action.equals(usage.action) &&
                monthlyCount.equals(usage.monthlyCount);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, app, user, action, monthlyCount, lastUsedTimeStamp);
    }

    @Override
    public String toString()
    {
        return "Usage{" +
                "id='" + id + '\'' +
                ", app='" + app + '\'' +
                ", user='" + user + '\'' +
                ", action='" + action + '\'' +
                ", lastUpdatedTimestamp='" + lastUsedTimeStamp + '\'' +
                ", monthlyCount=" + monthlyCount +
                '}';
    }
}
