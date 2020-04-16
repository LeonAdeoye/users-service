package com.leon.services;

import com.leon.models.Usage;

import java.util.List;

public interface UserService
{
    void saveUsage(String app, String user, String action, boolean countUsage);
    List<Usage> getUsage(String app);
}
