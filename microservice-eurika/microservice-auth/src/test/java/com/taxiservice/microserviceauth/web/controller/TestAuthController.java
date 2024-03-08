package com.taxiservice.microserviceauth.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxiservice.microserviceauth.config.TestSecurityConfig;
import com.taxiservice.microserviceauth.model.User;
import com.taxiservice.microserviceauth.service.AuthService;
import com.taxiservice.microserviceauth.service.UserService;
import com.taxiservice.microserviceauth.web.dto.JwtRequest;
import com.taxiservice.microserviceauth.web.dto.JwtResponse;


@WebMvcTest(controllers = AuthController.class)
@Import(TestSecurityConfig.class)
public class TestAuthController {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AuthService authService;
	
	@MockBean
	private UserService userService;
	
	@Test
	public void test_Login_With_Good_Username_And_Password() throws Exception{
		JwtRequest jwtRequest = new JwtRequest();
		jwtRequest.setUsername("username");
		jwtRequest.setPassword("password");
		
		when(authService.login(any(JwtRequest.class))).thenReturn(
				JwtResponse.newBuilder()
				.username("username")
				.accessToken("accessToken")
				.refreshToken("refreshToken")
				.build());
		
		mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(jwtRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accessToken").value("accessToken"));
	}
	
	@Test
	public void test_Login_With_Empty_Username_And_Password() throws Exception{
		JwtRequest jwtRequest = new JwtRequest();
		jwtRequest.setUsername("");
		jwtRequest.setPassword("");
		
		MvcResult  result =  mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(jwtRequest)))
				.andExpect(status().isBadRequest())
				.andReturn();
		String content = result.getResponse().getContentAsString();
		
		assertEquals(content, "valid failed");
	}
	
	@Test
	public void test_Login_With_Null_Username_And_Password() throws Exception{
		JwtRequest jwtRequest = new JwtRequest();
		jwtRequest.setUsername(null);
		jwtRequest.setPassword(null);
		
		MvcResult  result =  mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(jwtRequest)))
				.andExpect(status().isBadRequest())
				.andReturn();
		String content = result.getResponse().getContentAsString();
		
		assertEquals(content, "valid failed");
	}
	
	@Test
	public void test_Register_With_Good_Username_And_Password() throws Exception{
		JwtRequest jwtRequest = new JwtRequest();
		jwtRequest.setUsername("username");
		jwtRequest.setPassword("password");
		
		when(userService.getByUsername(any(String.class))).thenReturn(null);
		doNothing().when(authService).register(jwtRequest);
		
		
		MvcResult result = mockMvc.perform(post("/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(jwtRequest)))
				.andExpect(status().isOk())
				.andReturn();
		String content = result.getResponse().getContentAsString();
		
		assertEquals(content, "Registration is successfully");
		
	}
	
	@Test
	public void test_Register_With_Taken_Username() throws Exception{
		JwtRequest jwtRequest = new JwtRequest();
		jwtRequest.setUsername("username");
		jwtRequest.setPassword("password");
		
		User user = new User();
		user.setUsername("username");
		user.setPassword("password");
		
		when(userService.getByUsername(any(String.class))).thenReturn(user);
		doNothing().when(authService).register(jwtRequest);
		
		mockMvc.perform(post("/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(jwtRequest)))
				.andExpect(status().isBadRequest());
		
		
	}
	
	@Test
	public void test_ValidateToken_With_Good_AccessToken() throws Exception{
		String accessToken = "accessToken";
		
		doNothing().when(authService).validation(any(String.class));
		
		MvcResult mvcResult = mockMvc.perform(get("/auth/validate")
				.queryParam("accessToken", accessToken)
				.content(accessToken))
				.andExpect(status().isOk())
				.andReturn();
		
		String content = mvcResult.getResponse().getContentAsString();
		
		assertEquals(content, "Token is valid");
	}
	
	@Test
	public void test_ValidateToken_With_Bad_AccessToken() throws Exception{
		String accessToken = "accessToken";
		
		doThrow(new AccessDeniedException("Access Deneid")).when(authService).validation(any(String.class));
		
		MvcResult mvcResult = mockMvc.perform(get("/auth/validate")
				.queryParam("accessToken", accessToken)
				.content(accessToken))
				.andExpect(status().isUnauthorized())
				.andReturn();
		
		String content = mvcResult.getResponse().getContentAsString();
		
		assertEquals(content, "Unauthorised");
	}
	
	@Test
	public void test_Refresh_With_Good_RefreshToken() throws Exception{
		String refreshToken = "refreshToken";
		
		when(authService.refresh(any(String.class))).thenReturn(
				JwtResponse.newBuilder()
				.username("username")
				.accessToken("accessToken")
				.refreshToken("newRefreshToken")
				.build());
		
		mockMvc.perform(get("/auth/refresh")
				.queryParam("refreshToken", refreshToken)
				.content(refreshToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.refreshToken").value("newRefreshToken"));
	}
	
	@Test
	public void test_Refresh_With_Bad_RefreshToken() throws Exception{
		String refreshToken = "BadRefreshToken";
		
		doThrow(new AccessDeniedException("Access Deneid")).when(authService).refresh(refreshToken);
		
		MvcResult mvcResult = mockMvc.perform(get("/auth/refresh")
				.queryParam("refreshToken", refreshToken)
				.content(refreshToken))
				.andExpect(status().isUnauthorized())
				.andReturn();
		
		String content = mvcResult.getResponse().getContentAsString();
		
		assertEquals(content, "Unauthorised");
	}
}
