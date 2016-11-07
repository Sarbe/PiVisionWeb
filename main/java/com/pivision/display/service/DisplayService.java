package com.pivision.display.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pivision.display.Display;
import com.pivision.display.dao.DisplayDao;
import com.pivision.user.User;

@Component
@Path("/user")
public class DisplayService {

	@Autowired
	private DisplayDao displayDao;

	// @POST
	// @Path("/create")
	// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	// @Produces({ MediaType.TEXT_HTML })
	@Transactional
	public void addDisplay(Display display) {
		display.setLastModified(new Date());
		display.setAdded(new Date());
		display.setLastPinged(new Date());

		display.setDisplayStatus('N');
		displayDao.addDisplay(display);
	}

	@Transactional
	public List<Display> findAllDisplay(User user) {
		return displayDao.selectAllDisplays(user);
	}



	@Transactional
	public String checkStatus(Display display) {
		// TODO Auto-generated method stub
		return null;
	}
	@Transactional
	public void removeDisplay(User user, String macId) {
		 displayDao.deleteDisplayWithUserMacId(user, macId);
		
	}
	
	@Transactional
	public Display checkMacId(String macId) {
		return displayDao.findDisplayWithMacId(macId);

	}
	@Transactional
	public int saveDisplayDetails(Display display) {
		return displayDao.saveDisplayDetails(display);
		
	}
	
	

}
