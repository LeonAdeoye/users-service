package com.leon.services;

import com.leon.models.Usage;

import java.util.List;
import java.util.Optional;

public interface UserService
{
    void initialize();
    int getCurrentMonthIndex();
    void saveUsage(Usage usage);
    List<Usage> getUsage(String app, Optional<String> user);
}
