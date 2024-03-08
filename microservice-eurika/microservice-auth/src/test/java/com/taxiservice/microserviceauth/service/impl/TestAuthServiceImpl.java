package com.taxiservice.microserviceauth.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.taxiservice.microserviceauth.model.Role;
import com.taxiservice.microserviceauth.model.User;
import com.taxiservice.microserviceauth.service.AuthService;
import com.taxiservice.microserviceauth.service.DriverService;
import com.taxiservice.microserviceauth.service.UserService;
import com.taxiservice.microserviceauth.web.JwtProvider;
import com.taxiservice.microserviceauth.web.dto.JwtRequest;
import com.taxiservice.microserviceauth.web.dto.JwtResponse;

@ExtendWith(SpringExtension.class)
public class TestAuthServiceImpl {

	@Mock
	private AuthenticationManager authenticationManager;
	
	@Mock
	private JwtProvider jwtProvider;
	
	@Mock
	private UserService userService;
	
	@Mock
	private DriverService driverService;
	
	@Mock
	private PasswordEncoder encoder;
	
	@InjectMocks
	AuthServiceImpl authService;
	
	@Test
	public void testLogin() {
        // Arrange
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setPassword("passwrod");
        jwtRequest.setUsername("username");
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("encodedPassword");
        user.setRoles(List.of(Role.ROLE_USER));
        
        when(userService.getByUsername(jwtRequest.getUsername())).thenReturn(user);
        when(encoder.encode(jwtRequest.getPassword())).thenReturn("encodedPassword");
        when(jwtProvider.createAccessToken(anyLong(), anyString(),anyList())).thenReturn("accessToken");
        when(jwtProvider.createRefreshToken(anyLong(), anyString())).thenReturn("refreshToken");

        // Act
        JwtResponse jwtResponse = authService.login(jwtRequest);

        // Assert
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        assertEquals(user.getId(), jwtResponse.getId());
        assertEquals(user.getUsername(), jwtResponse.getUsername());
        assertEquals("accessToken", jwtResponse.getAccessToken());
        assertEquals("refreshToken", jwtResponse.getRefreshToken());
    }
}
