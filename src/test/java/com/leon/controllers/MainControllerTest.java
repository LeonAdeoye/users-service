package com.leon.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leon.models.Configuration;
import com.leon.models.Usage;
import com.leon.services.ConfigurationService;
import com.leon.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

//    @Test
//    public void configurationPostRequestWhenPassedValidRequestBody_ShouldCallConfigurationServiceSaveConfigurationMethod() throws Exception
//    {
//        // Arrange
//        Usage usage = new Usage("Horatio", "surname", "Adeoye", "papa", "today");
//        // Act
//        mockMVC.perform(post("/usage")
//                .content(asJsonString(usage))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//        // Assert
//        verify(userServiceMock, times(1)).saveUsage(any(Usage.class));
//    }
//
//    @Test()
//    public void configurationPostRequestWithoutRequestBody_ShouldNeverCallConfigurationServiceSaveConfigurationMethod() throws Exception
//    {
//        // Act
//        mockMVC.perform(post("/configuration")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//        // Assert
//        verify(configurationServiceMock, never()).saveConfiguration(null);
//    }
}