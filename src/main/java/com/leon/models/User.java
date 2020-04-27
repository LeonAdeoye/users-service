package com.leon.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

enum RegionEnum
{
    ASIA,
    EMEA,
    AMERICA
}

@JsonIgnoreProperties(ignoreUnknown = true)
@Document("User")
public final class User
{
    @Id
    private String id;
    private String userId;
    private String fullName;
    private boolean isActive;
    private String deskName;
    private RegionEnum region;
    private String countryCode;
    private String location;

    public User(String id, String userId, String fullName, boolean isActive, String deskName, RegionEnum region, String countryCode, String location)
    {
        this.id = id;
        this.userId = userId;
        this.fullName = fullName;
        this.isActive = isActive;
        this.deskName = deskName;
        this.region = region;
        this.countryCode = countryCode;
        this.location = location;
    }

    public User(String userId, String fullName, boolean isActive, String deskName, RegionEnum region, String countryCode, String location)
    {
        this.userId = userId;
        this.fullName = fullName;
        this.isActive = isActive;
        this.deskName = deskName;
        this.region = region;
        this.countryCode = countryCode;
        this.location = location;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean active)
    {
        isActive = active;
    }

    public String getDeskName()
    {
        return deskName;
    }

    public void setDeskName(String deskName)
    {
        this.deskName = deskName;
    }

    public RegionEnum getRegion()
    {
        return region;
    }

    public void setRegion(RegionEnum region)
    {
        this.region = region;
    }

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", isActive=" + isActive +
                ", deskName='" + deskName + '\'' +
                ", region=" + region +
                ", countryCode='" + countryCode + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isActive() == user.isActive() &&
                Objects.equals(getId(), user.getId()) &&
                Objects.equals(getUserId(), user.getUserId()) &&
                Objects.equals(getFullName(), user.getFullName()) &&
                Objects.equals(getDeskName(), user.getDeskName()) &&
                getRegion() == user.getRegion() &&
                Objects.equals(getCountryCode(), user.getCountryCode()) &&
                Objects.equals(getLocation(), user.getLocation());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId(), getUserId(), getFullName(), isActive(), getDeskName(), getRegion(), getCountryCode(), getLocation());
    }
}
