package com.stackroute.accountmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.accountmanager.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	User findByUserIdAndPassword(String userId, String password);
}
