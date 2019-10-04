package com.stackroute.accountmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.stackroute.accountmanager.exception.UserExistException;
import com.stackroute.accountmanager.exception.UserNotFoundException;
import com.stackroute.accountmanager.model.User;
import com.stackroute.accountmanager.repository.UserRepository;

/**
 * Test class for UserServiceImpl
 * @author VijayBhusan.Kumar
 *
 */
public class UserServiceImplTest {
	
	@Mock
	private transient UserRepository userRepository;
	@InjectMocks
	private UserServiceImpl userServiceImpl;
	User user;
	Optional<User> optionalUser;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		user = new User("u101", "Vijay", "Kumar", "password", new Date());
		optionalUser = Optional.of(user);
	}
	
	@Test
	public void testSaveUserSuccess() throws UserExistException {
		when(userRepository.save(user)).thenReturn(user);
		boolean flag = userServiceImpl.saveUser(user);
		assertEquals("Can't register user", true, flag);
		verify(userRepository, times(1)).save(user);
	}
	
	@Test(expected = UserExistException.class)
	public void testSaveUserFailure() throws UserExistException{
		when(userRepository.findById(user.getUserId())).thenReturn(optionalUser);
		when(userRepository.save(user)).thenReturn(user);
		userServiceImpl.saveUser(user);
	}
	
	@Test
	public void testValidateSuccess() throws UserNotFoundException {
		when(userRepository.findByUserIdAndPassword(user.getUserId(), user.getPassword())).thenReturn(user);
		User foundUser = userServiceImpl.findByUserIdAndPassword(user.getUserId(), user.getPassword());
		assertNotNull(foundUser);
		assertEquals("u101", foundUser.getUserId());
		verify(userRepository, times(1)).findByUserIdAndPassword(user.getUserId(), user.getPassword());
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testValidateFailure() throws UserNotFoundException {
		when(userRepository.findById("u102")).thenReturn(null);
		userServiceImpl.findByUserIdAndPassword(user.getUserId(), user.getPassword());
	}
}
