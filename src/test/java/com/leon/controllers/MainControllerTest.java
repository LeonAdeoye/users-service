package com.leon.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leon.models.Usage;
import com.leon.services.ConfigurationService;
import com.leon.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest
{
    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private ConfigurationService configurationServiceMock;

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
    public void usageGetRequestWhenPassedAppAndUserParams_ShouldCallUserServiceGetUsageMethod() throws Exception
    {
        // Act
        mockMVC.perform(get("/usage")
                .param("app", "users app")
                .param("user", "horatio"))
                .andExpect(status().isOk());
        // Assert
        verify(userServiceMock, times(1)).getUsage("users app", Optional.of("horatio"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void usageGetRequestWhenPassedInvalidAppParam_ShouldThrowIllegalArgumentException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(get("/usage")
                    .param("app", "")
                    .param("user", "horatio"));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(userServiceMock, never()).getUsage("", Optional.of("horatio"));
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void usageGetRequestWhenPassedInvalidUserParam_ShouldThrowIllegalArgumentException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(get("/usage")
                    .param("app", "users app")
                    .param("user", ""));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(userServiceMock, never()).getUsage("users app", Optional.of(""));
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test
    public void reconfigureGetRequest_shouldCallConfigurationServiceReconfigureMethod() throws Exception
    {
        // Act
        mockMVC.perform(get("/reconfigure"));
        // Assert
        verify(configurationServiceMock, times(1)).reconfigure();
    }

    @Test
    public void reconfigureGetRequest_shouldCallUserServiceInitializeMethod() throws Exception
    {
        // Act
        mockMVC.perform(get("/reconfigure"));
        // Assert
        verify(userServiceMock, times(1)).initialize();
    }

    @Test
    public void usagePostRequestWhenPassedValidRequestBody_ShouldCallUserServiceSaveUsageMethod() throws Exception
    {
        // Act
        mockMVC.perform(post("/usage")
                .param("app", "users app")
                .param("user", "horatio")
                .param("action", "get usage"))
                .andExpect(status().isOk());
        // Assert
        verify(userServiceMock, times(1)).saveUsage(new Usage("users app", "horatio", "get usage"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void usagePostRequestWhenPassedInvalidAppParam_ShouldThrowIllegalArgumentException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(post("/usage")
                    .param("app", null)
                    .param("user", "horatio")
                    .param("action", "get usage"));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(userServiceMock, never()).saveUsage(new Usage(null, "horatio", "get usage"));
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void usagePostRequestWhenPassedEmptyAppParam_ShouldThrowIllegalArgumentException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(post("/usage")
                    .param("app", "")
                    .param("user", "horatio")
                    .param("action", "get usage"));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(userServiceMock, never()).saveUsage(new Usage("", "horatio", "get usage"));
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void usagePostRequestWhenPassedInvalidUserParam_ShouldThrowIllegalArgumentException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(post("/usage")
                    .param("app", "user app")
                    .param("user", null)
                    .param("action", "get usage"));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(userServiceMock, never()).saveUsage(new Usage("user app", null, "get usage"));
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void usagePostRequestWhenPassedEmptyUserParam_ShouldThrowIllegalArgumentException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(post("/usage")
                    .param("app", "user app")
                    .param("user", "")
                    .param("action", "get usage"));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(userServiceMock, never()).saveUsage(new Usage("user app", "", "get usage"));
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void usagePostRequestWhenPassedEmptyActionParam_ShouldThrowIllegalArgumentException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(post("/usage")
                    .param("app", "user app")
                    .param("user", "horatio")
                    .param("action", ""));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(userServiceMock, never()).saveUsage(new Usage("user app", "horatio", ""));
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void usagePostRequestWhenPassedInvalidActionParam_ShouldThrowIllegalArgumentException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(post("/usage")
                    .param("app", "user app")
                    .param("user", "harper")
                    .param("action", null));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(userServiceMock, never()).saveUsage(new Usage("user app", "harper", null));
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }
}