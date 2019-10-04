package com.stackroute.accountmanager.controller;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.accountmanager.exception.UserExistException;
import com.stackroute.accountmanager.model.User;
import com.stackroute.accountmanager.service.SecurityTokenGenerator;
import com.stackroute.accountmanager.service.UserService;

/**
 * Controller class to control the request mapping flow
 * @RequestMapping used to route the http request which has the format "/accountmanager" 
 * @author VijayBhusan.Kumar
 *
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/accountmanager")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private SecurityTokenGenerator securityTokenGenerator;

	@PostMapping("/register")
	ResponseEntity<?> registerUser(@RequestBody User newUser) {
		try {
			userService.saveUser(newUser);
			return new ResponseEntity<String>("User registered successfully!", HttpStatus.CREATED);
		} catch (UserExistException uee) {
			return new ResponseEntity<String>("{ \"message\": \"" + uee.getMessage() + "\"}", HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/login")
	ResponseEntity<?> loginUser(@RequestBody User loginUser) {
		try {
			String userId = loginUser.getUserId();
			String password = loginUser.getPassword();

			if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(password)) {
				throw new Exception("User id or password is empty!");
			}

			User user = userService.findByUserIdAndPassword(userId, password);

			if (Objects.isNull(user)) {
				throw new Exception("User with given id doesn't exists!");
			}
			if (!password.equals(user.getPassword())) {
				throw new Exception("Invalid login credential, please try again!");
			}

			Map<String, String> tokenMap = securityTokenGenerator.generateToken(user);
			return new ResponseEntity<Map<String, String>>(tokenMap, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>("{ \"message\": \"" + e.getMessage() + "\"}", HttpStatus.UNAUTHORIZED);
		}
	}
}
