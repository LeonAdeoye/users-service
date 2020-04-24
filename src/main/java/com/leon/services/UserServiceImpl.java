package com.leon.services;

import com.leon.models.Usage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.leon.repositories.UsageRepository;
import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class UserServiceImpl implements UserService
{
    private Map<String, List<Usage>> usageMap = new HashMap<>();

    @Autowired
    UsageRepository usageRepository;

    private Sort sortByIdAsc()
    {
        return new Sort(Sort.Direction.ASC, "app");
    }

    @PostConstruct
    public void initialize()
    {
        usageMap = usageRepository.findAll(new Sort(Sort.Direction.ASC, "app"))
            .stream()
            .collect(groupingBy(Usage::getApp));
    }

    public int getCurrentMonthIndex()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.MONTH);
    }

    private List<OptionalInt> createMonthlyCount(int monthIndex, int count)
    {
        List <OptionalInt> monthlyCount = new ArrayList<>(Collections.nCopies(12, OptionalInt.of(0)));
        monthlyCount.set(monthIndex, OptionalInt.of(count));
        return monthlyCount;
    }

    @Override
    synchronized public void saveUsage(String app, String user, String action)
    {
        List<Usage> appUsageList = usageMap.containsKey(app) ? usageMap.get(app) : new ArrayList<>();
        int currentMonthIndex = getCurrentMonthIndex();

        for(int index = 0; index < appUsageList.size(); ++index)
        {
            if(appUsageList.get(index).getUser().equalsIgnoreCase(user) && appUsageList.get(index).getAction().equalsIgnoreCase(action))
            {
                List<OptionalInt> monthlyCounts = appUsageList.get(index).getMonthlyCount();
                int currentMonthCount = monthlyCounts.get(currentMonthIndex).orElse(0);
                monthlyCounts.set(currentMonthIndex, OptionalInt.of(++currentMonthCount));
                usageRepository.save( new Usage(app, user, action, monthlyCounts));
                return;
            }
        }

        Usage newUsage = new Usage(app, user, action, createMonthlyCount(currentMonthIndex, 1));
        appUsageList.add(newUsage);
        usageMap.put(app, appUsageList);
        usageRepository.save(newUsage);
    }

    @Override
    public List<Usage> getUsage(String app, Optional<String> user)
    {
        if(user.isPresent())
            return usageMap.get(app).stream().filter(usage -> usage.getUser() == user.get()).collect(Collectors.toList());
        else
            return usageMap.get(app);
    }
}
