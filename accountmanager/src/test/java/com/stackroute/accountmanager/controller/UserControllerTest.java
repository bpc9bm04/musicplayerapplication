package com.stackroute.accountmanager.controller;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.accountmanager.model.User;
import com.stackroute.accountmanager.service.SecurityTokenGenerator;
import com.stackroute.accountmanager.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;
	@MockBean
	private SecurityTokenGenerator securityTokenGenerator;	
	@InjectMocks
	UserController userController;
	private User user;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		user = new User("u101", "Vijay", "Kumar", "password", new Date());		
	}
	
	@Test
	public void testRegisterUser() throws Exception {
		when(userService.saveUser(user)).thenReturn(true);
		mockMvc.perform(post("/accountmanager/register").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(jsonToString(user))).andExpect(status().isCreated()).andDo(print());
		verify(userService, times(1)).saveUser(Mockito.any(User.class));
		verifyNoMoreInteractions(userService);		
	}

	@Test
	public void testLoginUser() throws Exception{
		final String userId = "u101";
		final String password = "password";
		when(userService.saveUser(user)).thenReturn(true);
		when(userService.findByUserIdAndPassword(userId, password)).thenReturn(user);
		mockMvc.perform(post("/accountmanager/login").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(jsonToString(user))).andExpect(status().isOk());
		verify(userService, times(1)).findByUserIdAndPassword(user.getUserId(), user.getPassword());
		verifyNoMoreInteractions(userService);
	}
	
	/**
	 * Parse an object to Json String
	 * @param object
	 * @return
	 * @throws JsonProcessingException 
	 */
	private static String jsonToString(final Object obj) throws JsonProcessingException {
		String jsonContent;
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String strContent = mapper.writeValueAsString(obj);
			jsonContent = strContent;
		} catch (JsonProcessingException jpe) {
			jpe.printStackTrace();
			jsonContent = "Json parsing error!";
		}
		return jsonContent;
	}
}
