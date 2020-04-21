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
public class UserServiceTest
{
    @MockBean
    private UsageRepository userRepositoryMock;

    @Autowired
    private UserService userService;

    @Test
    public void whenPassedValidConfiguration_saveConfiguration_shouldCallSaveMethodInRepositoryMock()
    {
        // Arrange
        Usage usage = new Usage("users app", "horatio", "get configurations", new ArrayList<>(Collections.nCopies(12, OptionalInt.of(0))));
        // Act
        userService.saveUsage("users app", "horatio", "get configurations");
        // Assert
        verify(userRepositoryMock, times(1)).save(usage);
    }

    @Test
    public void whenPassedValidConfiguration()
    {
        // Arrange
        List<Usage> usages = Arrays.asList(
                new Usage("users app", "horatio", "get configuration", new ArrayList<>(Collections.nCopies(12, OptionalInt.of(10)))),
                new Usage("users app", "harper", "get configuration", new ArrayList<>(Collections.nCopies(12, OptionalInt.of(1)))));

        when(userRepositoryMock.findAll(new Sort(Sort.Direction.ASC, "app"))).thenReturn(usages);
        userService.initialize();
        List<OptionalInt> monthlyCounts = new ArrayList<>(Collections.nCopies(12, OptionalInt.of(10)));
        monthlyCounts.set(userService.getCurrentMonthIndex(), OptionalInt.of(11));

        Usage usage = new Usage("users app", "horatio", "get configuration", monthlyCounts);
        // Act
        userService.saveUsage("users app", "horatio", "get configuration");
        // Assert
        verify(userRepositoryMock, times(1)).save(usage);
    }
}
