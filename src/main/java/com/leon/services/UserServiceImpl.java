package com.leon.services;

import com.leon.models.Usage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService
{
    private Map<String, Usage> usageMap = new HashMap<>();

    @Override
    public void saveUsage(String app, String user, String action, boolean countUsage)
    {

    }

    @Override
    public List<Usage> getUsage(String app)
    {
        return new ArrayList<>();
    }
}
