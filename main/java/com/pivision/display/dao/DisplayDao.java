package com.pivision.display.dao;

import java.util.List;

import com.pivision.display.Display;
import com.pivision.user.User;

public interface DisplayDao {

	public void addDisplay(Display display);

	public void deleteDisplayWithUserMacId(User user, String macId);

	public List<Display> selectAllDisplays(User user);

	public Display findDisplayWithMacId(String macId);

	public int saveDisplayDetails(Display display);

}
