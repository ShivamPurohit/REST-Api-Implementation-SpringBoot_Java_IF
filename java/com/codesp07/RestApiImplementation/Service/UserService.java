package com.codesp07.RestApiImplementation.Service;

import java.util.List;


import com.codesp07.RestApiImplementation.model.User;


public interface UserService{


	List<User> getUsers();


	User getValidUser(String email, String pass);

	User addUser(User user);

	boolean sendEmail(int otp,String email);

	String getValidatedUserEmail(String email);


	boolean getValidEmail(String email);

	public User fetchByUserEmailId(String emailId);
	
	public User updatePass(User user,String pass);


	String setPass(String pass, int Rollno);

	public User findByrollno(int Rollno);




}
