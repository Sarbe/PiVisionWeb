package com.pivision.user.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.pivision.user.User;
import com.pivision.user.UserAddressbook;
import com.pivision.user.dao.UserDao;


@Repository
public class UserDaoJPAImpl implements UserDao {
	
	@PersistenceContext(unitName="PiVisionPersistence")
	private EntityManager entityManager;

	
	// Create User - If exists return null or create and return user details
	public User createUser(User user) {
		User dbUser = verifyUser(user);
		if(null==dbUser){
			entityManager.persist(user);
			dbUser = verifyUser(user);
		}else{
			dbUser = null;
		}
		return dbUser;
	}

	@Override
	public User verifyUser(User user) {
		TypedQuery<User> query = entityManager.createQuery(
				"SELECT c FROM User c WHERE c.emailId = '" + user.getEmailId() + "'", User.class);
		List<User> userList = query.getResultList();
		if (userList != null && userList.size() > 0) {
			return findUser(userList.get(0));
		} else {
			return null;
		}

	}
	public User updateUserPassword(User user) {
		User dbUser = findUser(user);
		dbUser.setPassword(user.getPassword());
		entityManager.flush();
		return dbUser;
	}

	@Override
	public User updateUserAddress(User user, UserAddressbook uaddress) {
		//System.out.println("UserDaoJPAImpl.updateUserAddress()");
		//UserAddressbook address = entityManager.find(UserAddressbook.class, uaddress.getAddressBookId());
		
		TypedQuery<UserAddressbook> query = entityManager.createQuery(
				"SELECT c FROM UserAddressbook c WHERE c.user.userId = '" + user.getUserId() + "'",UserAddressbook.class);
		List<UserAddressbook> userAddList = query.getResultList();
		if(userAddList!=null && userAddList.size()>0){
			UserAddressbook address = userAddList.get(0);

			address.setFirstName(uaddress.getFirstName());
			address.setLastName(uaddress.getLastName());
			address.setBuildingName(uaddress.getBuildingName());
			address.setStreet1(uaddress.getStreet1());
			address.setStreet2(uaddress.getStreet2());
			address.setCity(uaddress.getCity());
			address.setCompany(uaddress.getCompany());
			address.setPhone(uaddress.getPhone());
			address.setPinCode(uaddress.getPinCode());
			address.setState(uaddress.getState());
			address.setCountry(uaddress.getCountry());
			entityManager.flush();
			return findUser(user);
			
		}else{
			entityManager.persist(uaddress);
			return findUser(user);
		}
	}
	
	public void deleteUser(User user) {

	}

	public User getUserById(String emailId) {
		return null;
	}

	public void deleteUseById(String emailId) {

	}

	@Override
	public User findUser(User user) {
		return entityManager.find(User.class, user.getUserId());
	}



	

}
