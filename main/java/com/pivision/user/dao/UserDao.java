package com.pivision.user.dao;

import com.pivision.user.User;
import com.pivision.user.UserAddressbook;

public interface UserDao {

	public User createUser(User user);

	public User updateUserPassword(User user);

	public void deleteUser(User user);

	public User getUserById(String emailId);

	public void deleteUseById(String emailId);

	public User verifyUser(User user);
	
	public User findUser(User user);

	public User updateUserAddress(User user, UserAddressbook uaddress);

}
