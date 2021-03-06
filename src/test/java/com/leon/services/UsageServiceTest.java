package com.leon.services;
import com.leon.models.Usage;
import com.leon.repositories.UsageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsageServiceTest
{
    @MockBean
    private UsageRepository userRepositoryMock;

    @Autowired
    private UsageService usageService;

    @Test
    public void whenPassedValidUsage_saveUsage_shouldCallSaveMethodInRepositoryMock()
    {
        // Arrange
        List<Integer> monthlyCounts = new ArrayList<>(Collections.nCopies(12, 0));
        monthlyCounts.set(usageService.getCurrentMonthIndex(), 1);
        Usage usage = new Usage("1","users app", "horatio", "get configurations", monthlyCounts);
        // Act
        usageService.saveUsage(usage);
        // Assert
        verify(userRepositoryMock, times(1)).save(usage);
    }

    @Test
    public void whenPassedValidUsageForExistingAppButDifferentUser_saveUsage_shouldCallSaveMethodInRepositoryMock()
    {
        // Arrange
        List<Usage> usages = Arrays.asList(
                new Usage("1","users app", "horatio", "get configuration", new ArrayList<>(Collections.nCopies(12, 10))),
                new Usage("2","users app", "harper", "get configuration", new ArrayList<>(Collections.nCopies(12, 1))));

        when(userRepositoryMock.findAll(new Sort(Sort.Direction.ASC, "app"))).thenReturn(usages);
        usageService.initialize();
        List<Integer> monthlyCounts = new ArrayList<>(Collections.nCopies(12, 0));
        monthlyCounts.set(usageService.getCurrentMonthIndex(), 1);
        Usage usage = new Usage("3","users app", "saori", "get configuration", monthlyCounts);
        // Act
        usageService.saveUsage(usage);
        // Assert
        verify(userRepositoryMock, times(1)).save(usage);
    }

    @Test
    public void whenPassedValidUsageForNonExistentApp_saveUsage_shouldCallSaveMethodInRepositoryMock()
    {
        // Arrange
        List<Usage> usages = Arrays.asList(
                new Usage("1","users app", "horatio", "get configuration", new ArrayList<>(Collections.nCopies(12, 10))),
                new Usage("2","users app", "harper", "get configuration", new ArrayList<>(Collections.nCopies(12, 1))));

        when(userRepositoryMock.findAll(new Sort(Sort.Direction.ASC, "app"))).thenReturn(usages);
        usageService.initialize();
        List<Integer> monthlyCounts = new ArrayList<>(Collections.nCopies(12, 0));
        monthlyCounts.set(usageService.getCurrentMonthIndex(), 1);
        Usage usage = new Usage("3","config app", "saori", "get configuration", monthlyCounts);
        // Act
        usageService.saveUsage(usage);
        // Assert
        verify(userRepositoryMock, times(1)).save(usage);
    }

    @Test
    public void whenPassedValidUsageForExistingAppAndExistingUser_saveUsage_shouldCallSaveMethodInRepositoryMock()
    {
        // Arrange
        List<Usage> usages = Arrays.asList(
                new Usage("1","users app", "horatio", "get configuration", new ArrayList<>(Collections.nCopies(12, 10))),
                new Usage("2","users app", "harper", "get configuration", new ArrayList<>(Collections.nCopies(12, 1))));
        when(userRepositoryMock.findAll(new Sort(Sort.Direction.ASC, "app"))).thenReturn(usages);
        usageService.initialize();
        List<Integer> monthlyCounts = new ArrayList<>(Collections.nCopies(12, 10));
        monthlyCounts.set(usageService.getCurrentMonthIndex(), 11);
        Usage usage = new Usage("1","users app", "horatio", "get configuration", monthlyCounts);
        // Act
        usageService.saveUsage(usage);
        // Assert
        verify(userRepositoryMock, times(1)).save(usage);
    }

    @Test
    public void whenPassedExistingAppAndExistingUser_getUsage_shouldReturnCorrectSizeOf1()
    {
        // Arrange
        List<Usage> usages = Arrays.asList(
                new Usage("users app", "horatio", "get configuration", new ArrayList<>(Collections.nCopies(12, 10))),
                new Usage("users app", "harper", "get configuration", new ArrayList<>(Collections.nCopies(12, 20))),
                new Usage("config app", "harper", "get configuration", new ArrayList<>(Collections.nCopies(12, 30))));
        when(userRepositoryMock.findAll(new Sort(Sort.Direction.ASC, "app"))).thenReturn(usages);
        usageService.initialize();
        // Act
        List<Usage> result = usageService.getUsage(Optional.of("users app"), Optional.of("horatio"));
        // Assert
        assertEquals("should return only 1 usage in list", result.size(), 1);
        assertEquals("should return user's app usage", result.get(0).getApp(), "users app");
        assertEquals("should return user's app horatio usage", result.get(0).getUser(), "horatio");
    }

    @Test
    public void whenPassedExistingAppOnly_getUsage_shouldReturnCorrectSizeOf2()
    {
        // Arrange
        List<Usage> usages = Arrays.asList(
                new Usage("users app", "horatio", "get configuration", new ArrayList<>(Collections.nCopies(12, 10))),
                new Usage("users app", "harper", "get configuration", new ArrayList<>(Collections.nCopies(12, 20))),
                new Usage("config app", "harper", "get configuration", new ArrayList<>(Collections.nCopies(12, 30))));
        when(userRepositoryMock.findAll(new Sort(Sort.Direction.ASC, "app"))).thenReturn(usages);
        usageService.initialize();
        // Act
        List<Usage> result = usageService.getUsage(Optional.of("users app"), Optional.empty());
        // Assert
        assertEquals("should return only 2 usages in list", result.size(), 2);
        assertEquals("should return user's app usage", result.get(0).getApp(), "users app");
        assertEquals("should return user's app usage", result.get(1).getApp(), "users app");
        assertEquals("should return user's app horatio usage", result.get(0).getUser(), "horatio");
        assertEquals("should return user's app harper usage", result.get(1).getUser(), "harper");
    }
}
