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

@Service
public class UserServiceImpl implements UserService
{
    private Map<String, List<Usage>> usageMap = new ConcurrentHashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UsageRepository usageRepository;

    private Sort sortByIdAsc()
    {
        return new Sort(Sort.Direction.ASC, "app");
    }

    @PostConstruct
    public void initialize()
    {
        List<Usage> list = usageRepository.findAll(sortByIdAsc());
        logger.info("Loaded {} usages from persistence store during initialization.", list.size());
        usageMap = list.stream().collect(groupingBy(Usage::getApp));
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
    synchronized public void saveUsage(Usage newUsage)
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
                usageRepository.save(existingUsage);
                logger.info("Successfully saved usage {} in persistence store.", existingUsage);
                return;
            }
        }

        newUsage.setMonthlyCount(createMonthlyCount(currentMonthIndex, 1));
        appUsageList.add(newUsage);
        usageMap.put(newUsage.getApp(), appUsageList);
        newUsage = usageRepository.save(newUsage);
        logger.info("Successfully saved usage {} in persistence store.", newUsage);
    }

    @Override
    public List<Usage> getUsage(String app, Optional<String> user)
    {
        List<Usage> result;

        if(user.isPresent())
            result = usageMap.get(app).stream().filter(usage -> usage.getUser().equals(user.get())).collect(Collectors.toList());
        else
            result = usageMap.get(app);

        logger.info("Successfully retrieved {} usages from usage map.", result.size());
        return result;
    }
}
