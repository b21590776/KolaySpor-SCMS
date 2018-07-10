package com.example.service;

import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.Role;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;

import javax.transaction.Transactional;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("MEMBER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	@Override
	public void addBalance(User user,double balance) {
		user.setBalance(user.getBalance()+balance);
	}

	@Override
	public List<User> findUsersByBranchName(String branchName) {
		List<User> userList = new ArrayList<>();
		userRepository.findUserByBranchName(branchName).forEach(userList::add);
		return userList;
	}

	@Override
	public void banUser(User user) {
		Role userRole = roleRepository.findByRole("BANNED");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		user.setActive(0);
	}

	@Override
	public void makeRegularMember(User user) {
		user.setActive(1);
		user.setVIPStartTime("");
		Role userRole = roleRepository.findByRole("MEMBER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
	}

	@Override
	public void makeTrainer(User user) {
		Role userRole = roleRepository.findByRole("TRAINER");
		Role userRole2 = roleRepository.findByRole("VIP");
		Role userRole3 = roleRepository.findByRole("MEMBER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole,userRole2,userRole3)));
	}

	@Override
	public void makeVIP(User user) {
		user.setVIPStartTime(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
		Role userRole = roleRepository.findByRole("VIP");
		Role userRole2 = roleRepository.findByRole("MEMBER");
		if(user.getBalance()>=100) user.setBalance(user.getBalance()-100);
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole,userRole2)));
	}

	@Override
	public void makeAdmin(User user) {
		Role userRole = roleRepository.findByRole("ADMIN");
		Role userRole2 = roleRepository.findByRole("BRANCH MANAGER");
		Role userRole3 = roleRepository.findByRole("TRAINER");
		Role userRole4 = roleRepository.findByRole("VIP");
		Role userRole5 = roleRepository.findByRole("MEMBER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole,userRole2,userRole3,userRole4,userRole5)));
	}

	@Override
	public User findUserByID(int id) {
		return userRepository.findUserById(id);
	}

	@Override
	public List<User> findAllUsers() {
		List<User> resultList = new ArrayList<>();
		userRepository.findAll().forEach(resultList::add);
		return resultList;
	}

	@Override
	public void changeBranch(User user, String branch) {
		user.setBranchName(branch);
	}

	@Override
	public void makeManager(User user) {
		Role userRole = roleRepository.findByRole("BRANCH MANAGER");
		Role userRole1 = roleRepository.findByRole("TRAINER");
		Role userRole2 = roleRepository.findByRole("VIP");
		Role userRole3 = roleRepository.findByRole("MEMBER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole,userRole1,userRole2,userRole3)));
	}
}
