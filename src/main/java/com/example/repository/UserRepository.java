package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.User;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
	 User findByEmail(String email);
	 List<User> findUserByBranchName(String branchName);
	 User findUserById(int id);
}
