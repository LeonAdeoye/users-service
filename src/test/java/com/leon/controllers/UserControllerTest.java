package com.leon.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leon.models.EnumTypes;
import com.leon.models.User;
import com.leon.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest
{
    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private UserService userServiceMock;

    private static String asJsonString(final Object obj)
    {
        try
        {
            return new ObjectMapper().writeValueAsString(obj);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void userGetRequestWhenPassedValidUserArgument_ShouldCallUserServiceGetUserMethod() throws Exception
    {
        // Act
        mockMVC.perform(get("/user")
                .param("userId", "horatio"))
                .andExpect(status().isOk());
        // Assert
        verify(userServiceMock, times(1)).getUser("horatio");
    }

    @Test(expected = IllegalArgumentException.class)
    public void userGetRequestWhenPassedNullUserIdArgument_ShouldThrowIllegalArgumentException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(get("/user")
                    .param("userId", null));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(userServiceMock, never()).getUser(null);
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void userGetRequestWhenPassedEmptyUserIdArgument_ShouldThrowIllegalArgumentException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(get("/user")
                    .param("userId", ""));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(userServiceMock, never()).getUser("");
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test
    public void userPostRequestWhenPassedValidUserArgument_ShouldCallUserServiceSaveUserMethod() throws Exception
    {
        // Act
        User user = new User("horatio", "Ethan Horatio Adeoye", true, "sales trading", EnumTypes.RegionEnum.EUROPE, "UK", "Liverpool");
        mockMVC.perform(post("/user")
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // Assert
        verify(userServiceMock, times(1)).saveUser(user);
    }

    @Test
    public void userPostRequestWhenPassedInvalidUserArgument_ShouldNotCallSaveUserMethod() throws Exception
    {
        // Act
        mockMVC.perform(post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
        // Assert
        verify(userServiceMock, never()).saveUser(any());

    }

    @Test(expected = IllegalArgumentException.class)
    public void usersGetRequestWhenPassedEmptyDeskNameArgument_ShouldThrowIllegalArgumentException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(get("/users")
                    .param("deskName", ""));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(userServiceMock, never()).getUser("");
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test
    public void usersGetRequestWhenPassedValidDeskNameArgument_ShouldCallGetDeskUsersMethod() throws Throwable
    {
        // Act
        mockMVC.perform(get("/users")
                .param("deskName", "sales trading"))
                .andExpect(status().isOk());

        verify(userServiceMock, times(1)).getDeskUsers("sales trading");
    }

    @Test
    public void usersGetRequestWithoutDeskNameArgument_ShouldCallGetAllUsersMethod() throws Throwable
    {
        // Act
        mockMVC.perform(get("/users"))
                .andExpect(status().isOk());

        verify(userServiceMock, times(1)).getAllUsers();
    }
}