package com.stackroute.accountmanager.service;

import com.stackroute.accountmanager.exception.UserExistException;
import com.stackroute.accountmanager.exception.UserNotFoundException;
import com.stackroute.accountmanager.model.User;

public interface UserService {

	boolean saveUser(User user) throws UserExistException;
	User findByUserIdAndPassword(String userId, String password) throws UserNotFoundException;
}
