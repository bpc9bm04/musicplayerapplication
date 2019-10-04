package com.stackroute.accountmanager.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.stackroute.accountmanager.constant.AuthConstant;
import com.stackroute.accountmanager.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtSecurityTokenGeneratorImpl implements SecurityTokenGenerator {

	@Override
	public Map<String, String> generateToken(User user) {
		String jwtToken = "";
		jwtToken = Jwts.builder().setSubject(user.getUserId()).setIssuedAt(new Date())
					   .signWith(SignatureAlgorithm.HS256, AuthConstant.BASE64_ENCODED_KEY).compact();

		Map<String, String> tokenMap = new HashMap<>();
		tokenMap.put(AuthConstant.JWT_TOKEN, jwtToken);
		tokenMap.put(AuthConstant.JWT_MESSAGE, "User logged in successfully!");
		return tokenMap;
	}
}
