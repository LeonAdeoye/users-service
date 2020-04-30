package com.leon.services;

import com.leon.models.Usage;

import java.util.List;
import java.util.Optional;

public interface UsageService
{
    void initialize();
    int getCurrentMonthIndex();
    Usage saveUsage(Usage usage);
    List<Usage> getUsage(String app, Optional<String> user);
}
