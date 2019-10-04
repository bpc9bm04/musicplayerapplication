package com.stackroute.accountmanager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.accountmanager.exception.UserExistException;
import com.stackroute.accountmanager.exception.UserNotFoundException;
import com.stackroute.accountmanager.model.User;
import com.stackroute.accountmanager.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	/* (non-Javadoc)
	 * @see com.stackroute.accountmanager.service.UserService#saveUser(com.stackroute.accountmanager.model.User)
	 */
	@Override
	public boolean saveUser(User user) throws UserExistException {

		Optional<User> optionalUser = userRepository.findById(user.getUserId());
		if (optionalUser.isPresent()) {
			throw new UserExistException("User with same id already exists!");
		}
		userRepository.save(user);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.stackroute.accountmanager.service.UserService#findByUserIdAndPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public User findByUserIdAndPassword(String userId, String password) throws UserNotFoundException {

		User user = userRepository.findByUserIdAndPassword(userId, password);
		if (user == null) {
			throw new UserNotFoundException("Invalid user id or password!");
		}
		return user;
	}

}
