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
    private List<OptionalInt> monthlyCount;

    public Usage()
    {
        this.id = "";
        this.app = "";
        this.user = "";
        this.action = "";
        this.lastUsageDate = LocalDate.now();
        this.monthlyCount = new ArrayList<>(Collections.nCopies(12, OptionalInt.of(0)));
    }

    public Usage(String app, String user, String action, List<OptionalInt> monthlyCount)
    {
        this.id = "";
        this.app = app;
        this.user = user;
        this.action = action;
        this.lastUsageDate = LocalDate.now();
        this.monthlyCount = new ArrayList<>(monthlyCount);
    }

    public Usage(String id, String app, String user, String action, List<OptionalInt> monthlyCount)
    {
        this.id = id;
        this.app = app;
        this.user = user;
        this.action = action;
        this.lastUsageDate = LocalDate.now();
        this.monthlyCount = new ArrayList<>(monthlyCount);
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

    public List<OptionalInt> getMonthlyCount()
    {
        return this.monthlyCount;
    }

    public void setMonthlyCount(List<OptionalInt> monthlyCount)
    {
        this.monthlyCount = monthlyCount;
    }

    public LocalDate getLastUsageDate()
    {
        return this.lastUsageDate;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Usage usage = (Usage) o;
        return this.id.equals(usage.id) &&
                this.app.equals(usage.app) &&
                this.lastUsageDate.equals(usage.lastUsageDate) &&
                this.user.equals(usage.user) &&
                this.action.equals(usage.action) &&
                this.monthlyCount.equals(usage.monthlyCount);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.id, this.app, this.user, this.action, this.monthlyCount, this.lastUsageDate);
    }

    @Override
    public String toString()
    {
        return "Usage{" +
                "id='" + this.id + '\'' +
                ", app='" + this.app + '\'' +
                ", user='" + this.user + '\'' +
                ", action='" + this.action + '\'' +
                ", lastUsageDate='" + this.lastUsageDate + '\'' +
                ", monthlyCount=" + this.monthlyCount +
                '}';
    }
}
