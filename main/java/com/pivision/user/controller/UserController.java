package com.pivision.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.pivision.user.Role;
import com.pivision.user.User;
import com.pivision.user.UserAddressbook;
import com.pivision.user.formbean.AccountDetailBean;
import com.pivision.user.formbean.LoginBean;
import com.pivision.user.service.UserService;

@Controller
//@RequestMapping(value={"/user", "/index.jsp"})
@RequestMapping("/user")
@SessionAttributes("userBean")

public class UserController {

	@Autowired
	UserService userService;
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@ModelAttribute
	private void initMethod(Model model,@RequestParam(value="linkNo",defaultValue="1")String linkNo) {
		//System.out.println("Link Value :: "+linkNo);
		model.addAttribute("LINK_NO",linkNo);
	}
	
	@RequestMapping(value = {"/logout", ""}, method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest req) {
		ModelAndView model = new ModelAndView("start");
		req.getSession().removeAttribute("userBean");
		req.getSession(false).invalidate();
		return model;
	}

	// Home page Link
	@RequestMapping(value = {"/start", ""}, method = RequestMethod.GET)
	public ModelAndView startPage(HttpServletRequest req) {
		ModelAndView model = new ModelAndView("start");
		return model;
	}
	
	@RequestMapping(value = "/accountDetails", method = RequestMethod.GET)
	public ModelAndView accountPage(HttpServletRequest req) {
		User user = (User) req.getSession().getAttribute("userBean");
		
		User dbUser = userService.verfifyUser(user);
		ModelAndView model = new ModelAndView("account");
		model.addObject("userBean",dbUser);
		return model;
	}
	
	/*@RequestMapping(value = "/presentation", method = RequestMethod.GET)
	public ModelAndView presentationPage(HttpServletRequest req) {
		ModelAndView model = new ModelAndView("presentation");
		return model;
	}
	
	
	
	@RequestMapping(value = "/schedule", method = RequestMethod.GET)
	public ModelAndView schedulePage(HttpServletRequest req) {
		ModelAndView model = new ModelAndView("schedule");
		return model;
	}*/
	
	//////////////////////////////////////////

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView create(@Valid @ModelAttribute("loginBean") LoginBean loginBean, BindingResult result,HttpServletRequest req) {

		logger.debug("UserController.create()");
		
		ModelAndView model = new ModelAndView("account");;
		if(!result.hasErrors()){
			// Set entity
			User formUserDetails = new User();
			formUserDetails.setEmailId(loginBean.getEmailId());
			formUserDetails.setPassword(loginBean.getPassword());
	
			Role role = new Role();
			role.setRoleId(loginBean.getRoleId());
			formUserDetails.setRole(role);
			// Create User
			User dbUser = userService.register(formUserDetails);
			if(null == dbUser){
				//model = new ModelAndView("start");
				model.addObject("MSG", "This email id is already registered.");
			}else{
				//model = new ModelAndView("start");
				//model.addObject("userBean",dbUser);
				model.addObject("MSG", "User Account created Successfully.");
			}
			
			//model = new ModelAndView("start");
		}
		return model;

	}

	// Verify User During Login
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public ModelAndView verify(@ModelAttribute("loginBean1") LoginBean loginBean,HttpServletRequest req) {
		ModelAndView model = null;
		// Set entity
		User user = new User();
		user.setEmailId(loginBean.getEmailId());
		user.setPassword(loginBean.getPassword());

		// Verify value
		User dbUser = userService.verfifyUser(user);
		if (dbUser != null && dbUser.getPassword().equals(user.getPassword())) {
			model = new ModelAndView("account");
			//model.addObject("userDetails", dbUser);
			model.addObject("userBean",dbUser);
			req.getSession().setAttribute("AUTH_KEY",Long.parseLong(dbUser.getUserId()));
			
		} else {
			model = new ModelAndView("start");
			model.addObject("MSG","User Name and password doesnot Match.");
		}
		return model;
	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public ModelAndView updatePassword(@ModelAttribute("loginBean") LoginBean loginBean, HttpServletRequest req) {
		logger.debug("UserController.updatePassword()");
		User userBean = (User) req.getSession().getAttribute("userBean");
		ModelAndView model = null;
		model = new ModelAndView("account");
		User user = new User();
		logger.debug("Pass :: " + userBean.getPassword());

		if (loginBean.getPassword().equals("") || loginBean.getCnfrmPassword().equals("")) {
			model.addObject("MSG", "Field Cannot left balnk.");
		} else {
			if (loginBean.getPassword().equals(userBean.getPassword())) {
				user.setUserId(userBean.getUserId());
				user.setPassword(loginBean.getCnfrmPassword());
				User dbUser = userService.updatPassString(user);
				// Put in session
				model.addObject("userBean", dbUser);
				model.addObject("MSG", "Password changed Successfully.");
				// model.addObject("userDetails",dbUser);
			} else {
				model.addObject("userBean", user);
				model.addObject("MSG", "Current Password is wrong.");
			}
		}
		return model;

	}

	@RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
	public ModelAndView updateAddress(@Valid @ModelAttribute("accountBean") AccountDetailBean accountBean,BindingResult result, HttpServletRequest req) {
		logger.debug("UserController.updateAddress()");
		ModelAndView model = null;
		
		User user = (User) req.getSession().getAttribute("userBean");
		UserAddressbook uaddress = new UserAddressbook();
		uaddress.setUser(user);
		uaddress.setFirstName(accountBean.getFirstName());
		uaddress.setLastName(accountBean.getLastName());
		uaddress.setBuildingName(accountBean.getBuildingName());
		uaddress.setStreet1(accountBean.getStreet1());
		uaddress.setStreet2(accountBean.getStreet2());
		uaddress.setCity(accountBean.getCity());
		uaddress.setCompany(accountBean.getCompany());
		uaddress.setPhone(accountBean.getPhone());
		uaddress.setPinCode(accountBean.getPinCode());
		uaddress.setState(accountBean.getState());
		uaddress.setCountry(accountBean.getCountry());
		uaddress.setAddressType("COMM");
		
		if(result.hasErrors()){
			model =  new ModelAndView("account");
			user.setUserAddressbooks(uaddress);
			model.addObject("userBean",user);
			model.addObject("MSG","Please correct Data and submit again.");
			
		} else {
			User dbUser = userService.updatAddress(user, uaddress);
			model = new ModelAndView("account");
			model.addObject("userBean",dbUser);
			model.addObject("MSG", "Data Saved Successfully.");
		}
		return model;
	}
}
