package com.leon.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
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
    private LocalDate lastUsageDate;
    private List<Integer> monthlyCount;

    public Usage()
    {
        this.app = "";
        this.user = "";
        this.action = "";
        this.lastUsageDate = LocalDate.now();
        this.monthlyCount = new ArrayList<>(Collections.nCopies(12, 0));
    }

    public Usage(String app, String user, String action, List<Integer> monthlyCount)
    {
        this.app = app;
        this.user = user;
        this.action = action;
        this.lastUsageDate = LocalDate.now();
        this.monthlyCount = new ArrayList<>(monthlyCount);
    }

    public Usage(String id, String app, String user, String action, List<Integer> monthlyCount)
    {
        this.id = id;
        this.app = app;
        this.user = user;
        this.action = action;
        this.lastUsageDate = LocalDate.now();
        this.monthlyCount = new ArrayList<>(monthlyCount);
    }

    public Usage(String app, String user, String action)
    {
        this.app = app;
        this.user = user;
        this.action = action;
        this.lastUsageDate = LocalDate.now();
        this.monthlyCount = new ArrayList<>(Collections.nCopies(12,0));
    }

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getApp()
    {
        return this.app;
    }

    public void setApp(String app)
    {
        this.app = app;
    }

    public String getUser()
    {
        return this.user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getAction()
    {
        return this.action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public List<Integer> getMonthlyCount()
    {
        return this.monthlyCount;
    }

    public void setMonthlyCount(List<Integer> monthlyCount)
    {
        this.monthlyCount = monthlyCount;
    }

    public LocalDate getLastUsageDate()
    {
        return this.lastUsageDate;
    }

    public void setLastUsageDate(LocalDate lastUsageDate)
    {
         this.lastUsageDate = lastUsageDate;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Usage usage = (Usage) o;
        return Objects.equals(getId(), usage.getId()) &&
                getApp().equals(usage.getApp()) &&
                getUser().equals(usage.getUser()) &&
                getAction().equals(usage.getAction()) &&
                getLastUsageDate().equals(usage.getLastUsageDate()) &&
                getMonthlyCount().equals(usage.getMonthlyCount());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.getId(), this.getApp(), this.getUser(), this.getAction(), this.getMonthlyCount(), this.getLastUsageDate());
    }

    @Override
    public String toString()
    {
        return "Usage{" +
                "id='" + this.getId() + '\'' +
                ", app='" + this.getApp() + '\'' +
                ", user='" + this.getUser() + '\'' +
                ", action='" + this.getAction() + '\'' +
                ", lastUsageDate='" + this.getLastUsageDate() + '\'' +
                ", monthlyCount=" + this.getMonthlyCount() +
                '}';
    }
}
