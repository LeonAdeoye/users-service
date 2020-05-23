package com.leon.services;

import com.leon.models.Usage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.leon.repositories.UsageRepository;
import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
public class UsageServiceImpl implements UsageService
{
    private Map<String, List<Usage>> usageMap = new ConcurrentHashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(UsageServiceImpl.class);

    @Autowired
    UsageRepository usageRepository;

    private Sort sortByAppNameAsc()
    {
        return new Sort(Sort.Direction.ASC, "app");
    }

    @PostConstruct
    public void initialize()
    {
        List<Usage> usages = usageRepository.findAll(sortByAppNameAsc());
        logger.info("Loaded {} usages from persistence store during initialization.", usages.size());
        usageMap = usages.stream().collect(groupingBy(Usage::getApp));
    }

    public int getCurrentMonthIndex()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.MONTH);
    }

    private List<Integer> createMonthlyCount(int monthIndex, int count)
    {
        List <Integer> monthlyCount = new ArrayList<>(Collections.nCopies(12, 0));
        monthlyCount.set(monthIndex, count);
        return monthlyCount;
    }

    @Override
    synchronized public Usage saveUsage(Usage newUsage)
    {
        List<Usage> appUsageList = usageMap.containsKey(newUsage.getApp()) ? usageMap.get(newUsage.getApp()) : new ArrayList<>();
        int currentMonthIndex = getCurrentMonthIndex();

        for(int index = 0; index < appUsageList.size(); ++index)
        {
            Usage existingUsage = appUsageList.get(index);
            if(existingUsage.getUser().equalsIgnoreCase(newUsage.getUser()) && existingUsage.getAction().equalsIgnoreCase(newUsage.getAction()))
            {
                List<Integer> monthlyCounts = existingUsage.getMonthlyCount();
                int currentMonthCount = monthlyCounts.get(currentMonthIndex);
                monthlyCounts.set(currentMonthIndex, ++currentMonthCount);
                existingUsage.setMonthlyCount(monthlyCounts);
                existingUsage.setLastUsageDate(LocalDate.now());
                existingUsage = usageRepository.save(existingUsage);
                logger.info("Successfully saved existing usage {} in persistence store.", existingUsage);
                return existingUsage;
            }
        }

        newUsage.setMonthlyCount(createMonthlyCount(currentMonthIndex, 1));
        appUsageList.add(newUsage);
        usageMap.put(newUsage.getApp(), appUsageList);
        newUsage = usageRepository.save(newUsage);
        logger.info("Successfully saved new usage {} in persistence store.", newUsage);
        return newUsage;
    }

    @Override
    public List<Usage> getUsage(Optional<String> app, Optional<String> user)
    {
        logger.info("Successfully retrieved usages from usage map.");

        List<Usage> result;

        if(app.isPresent() && user.isPresent())
            return usageMap.get(app).stream().filter(usage -> usage.getUser().equals(user.get())).collect(toList());

        if(app.isPresent())
            return usageMap.get(app);

        return new ArrayList(usageMap.values());
    }



    @Override
    public Set<String> getUsageApps()
    {
        return this.usageMap.keySet();
    }
}
