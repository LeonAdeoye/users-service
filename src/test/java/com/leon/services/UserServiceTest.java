package com.leon.services;

import com.leon.models.EnumTypes;
import com.leon.models.User;
import com.leon.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest
{
    @MockBean
    private UserRepository userRepositoryMock;

    @Autowired
    private UserService userService;

    @Test
    public void whenPassedValidUser_saveUser_shouldCallSaveMethodInRepositoryMock()
    {
        // Arrange
        User user = new User("1","horatio", "Ethan Horatio Adeoye", true, "sales trading", EnumTypes.RegionEnum.EUROPE, "UK", "Liverpool");
        when(userRepositoryMock.save(user)).thenReturn(user);
        // Act
        userService.saveUser(user);
        // Assert
        verify(userRepositoryMock, times(1)).save(user);
    }

    @Test
    public void initialize_shouldCallFindAllMethodInRepositoryMock()
    {
        // Act
        userService.initialize();
        // Assert
        verify(userRepositoryMock, times(1)).findAll(new Sort(Sort.Direction.ASC, "userId"));
    }
}
