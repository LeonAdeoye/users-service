package com.leon.services;

import com.leon.models.Usage;

import java.util.List;

public interface UserService
{
    void initialize();
    int getCurrentMonthIndex();
    void saveUsage(String app, String user, String action);
    List<Usage> getUsage(String app);
}
