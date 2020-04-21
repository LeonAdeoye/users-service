package com.leon.services;

import com.leon.models.Usage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.leon.repositories.UsageRepository;

import javax.annotation.PostConstruct;
import java.util.*;

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
        Usage newUsage;
        List<Usage> appUsageList = usageMap.get(app);
        List<OptionalInt> monthlyCounts = new ArrayList<>(Collections.nCopies(12, OptionalInt.of(0)));
        int currentMonthIndex = getCurrentMonthIndex();
        int currentCount = 0;

        if(appUsageList != null && appUsageList.size() > 0)
        {
            currentCount = 1;
            monthlyCounts = createMonthlyCount(currentMonthIndex, currentCount);

            for(int index = 0; index < appUsageList.size(); ++index)
            {
                if(appUsageList.get(index).getUser().equalsIgnoreCase(user) && appUsageList.get(index).getAction().equalsIgnoreCase(action))
                {
                    monthlyCounts = appUsageList.get(index).getMonthlyCount();
                    int currentMonthCount = monthlyCounts.get(currentMonthIndex).orElse(0);
                    currentCount = ++currentMonthCount;
                    monthlyCounts.set(currentMonthIndex, OptionalInt.of(currentCount));
                    newUsage = new Usage(app, user, action, monthlyCounts);
                    break;
                }
            }
        }
        else
        {
            appUsageList = new ArrayList<>();
            monthlyCounts = createMonthlyCount(currentMonthIndex, ++currentCount);
            newUsage = new Usage(app, user, action, monthlyCounts);
            appUsageList.add(newUsage);
            usageMap.put(app, appUsageList);
            usageRepository.save(newUsage);
        }
    }

    @Override
    public List<Usage> getUsage(String app)
    {
        return usageRepository.findByApp(app);
    }
}
