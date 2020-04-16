package com.leon.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document
public class Usage
{
    @Id
    private String id;
    private String app;
    private String user;
    private String action;
    private List<Integer> monthlyCount = new ArrayList<>();

    public Usage(String app, String user, String action, List<Integer> monthlyCount)
    {
        this.app = app;
        this.user = user;
        this.action = action;
        //TODO perfect
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

    public List<Integer> getMonthlyCount()
    {
        return monthlyCount;
    }

    public void setMonthlyCount(List<Integer> monthlyCount)
    {
        this.monthlyCount = monthlyCount;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Usage usage = (Usage) o;
        return id.equals(usage.id) &&
                app.equals(usage.app) &&
                user.equals(usage.user) &&
                action.equals(usage.action) &&
                monthlyCount.equals(usage.monthlyCount);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, app, user, action, monthlyCount);
    }

    @Override
    public String toString()
    {
        return "Usage{" +
                "id='" + id + '\'' +
                ", app='" + app + '\'' +
                ", user='" + user + '\'' +
                ", action='" + action + '\'' +
                ", monthlyCount=" + monthlyCount +
                '}';
    }
}
