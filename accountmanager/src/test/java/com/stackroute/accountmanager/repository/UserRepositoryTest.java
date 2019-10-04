package com.stackroute.accountmanager.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.accountmanager.model.User;

/**
 * UserRepository test class
 * @author VijayBhusan.Kumar
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
public class UserRepositoryTest {

	@Mock
	private transient UserRepository userRepository;
	private User user;
	
	@Before
	public void setup() {
		user = new User("u101", "Vijay", "Kumar", "password", new Date());
	}
	
	@Test
	public void testRegisterUserSuccess() {
		userRepository.save(user);
		Optional<User> optionalUser = userRepository.findById(user.getUserId());		
		assertThat(optionalUser.equals(user));
	}
	
	@Test
	public void testLoginUserSuccess() {
		final String userId = "u101";
		final String password = "password";
		userRepository.save(user);
		when(userRepository.findByUserIdAndPassword(userId, password)).thenReturn(user);		
		User foundUser = userRepository.findByUserIdAndPassword(userId, password);		
		assertThat(user.equals(foundUser));	
	}
}
