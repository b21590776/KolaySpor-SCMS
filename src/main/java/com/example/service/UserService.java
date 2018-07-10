package com.example.service;

import com.example.model.User;

import java.util.List;

public interface UserService {
	public User findUserByEmail(String email);
	User findUserByID(int id);
	public void saveUser(User user);
	public void addBalance(User user,double balance);
	public List<User> findUsersByBranchName(String name);
	void makeTrainer(User user);
	void banUser(User user);
	void makeVIP(User user);
	void makeManager(User user);
	void makeRegularMember(User user);
	List<User> findAllUsers();
	void makeAdmin(User user);
	void changeBranch(User user, String brnach);
}
