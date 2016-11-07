package com.pivision.user.service;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pivision.user.User;
import com.pivision.user.UserAddressbook;
import com.pivision.user.dao.UserDao;

@Component
@Path("/user")
public class UserService {

	@Autowired
	private UserDao userDao;

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.TEXT_HTML })
	@Transactional
	public Response registerFromForm(@FormParam("email") String email, @FormParam("password") String password) {

		//System.out.println("UserService.registerFromForm()");

		User user = new User();
		user.setEmailId(email);
		user.setPassword(password);
		user.setCreateDate(new Date());
		user.setLastLogon(new Date());
		user.setUpdateDate(new Date());

		return Response.status(201).entity("A new user has been registered with userid" + userDao.createUser(user))
				.build();
	}
	
	@Transactional
	public User verfifyUser(User user) {

		//System.out.println("UserService.verfifyUser()");
		
		return userDao.verifyUser(user);

	}

	@Transactional
	public User register(User user) {
		user.setCreateDate(new Date());
		user.setLastLogon(new Date());
		user.setUpdateDate(new Date());
		user.setUserType("user");
		return userDao.createUser(user);
	}

	@Transactional
	public User findUser(User user) {
		User dbUser = userDao.findUser(user);
		return dbUser;
	}
	
	@Transactional
	public User updatPassString(User user) {
		return userDao.updateUserPassword(user);
	}
	
	@Transactional
	public User updatAddress(User user, UserAddressbook uaddress) {
		return userDao.updateUserAddress(user,uaddress);
	}
	
	
	
	public void login() {
		// TODO Auto-generated method stub

	}

	public String logout() {
		// TODO Auto-generated method stub
		return null;
	}

	public String editProfile() {
		// TODO Auto-generated method stub
		return null;
	}


}
