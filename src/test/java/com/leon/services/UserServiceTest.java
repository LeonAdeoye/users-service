package com.leon.services;
import com.leon.models.Usage;
import com.leon.repositories.UsageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
        userService.saveUsage("users app", "horatio", "get configurations", false);
        // Assert
        verify(userRepositoryMock, times(1)).save(usage);
    }

    @Test
    public void whenPassedValidConfiguration()
    {
        // Arrange
        List<Usage> usage = Arrays.asList(
                new Usage("users app", "horatio", "get configuration", new ArrayList<>(Collections.nCopies(12, OptionalInt.of(0)))),
                new Usage("user app", "harper", "get configuration", new ArrayList<>(Collections.nCopies(12, OptionalInt.of(0)))));

        when(userRepositoryMock.findAll()).thenReturn(usage);
    }
}
