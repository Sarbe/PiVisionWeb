package com.pivision.display.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.pivision.display.Display;
import com.pivision.display.formbean.DisplayBean;
import com.pivision.display.service.DisplayService;
import com.pivision.user.User;
import com.pivision.util.CommonFunction;
import com.pivision.util.Config;

@Controller
@RequestMapping("/display")
@SessionAttributes("userBean")

public class DisplayController {
	private static final Logger logger = Logger.getLogger(DisplayController.class);
	@Autowired
	DisplayService displayService;
	
	@ModelAttribute
	private void initMethod(Model model,@RequestParam(value="linkNo",defaultValue="1")String linkNo) {
		model.addAttribute("LINK_NO",linkNo);
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView displayPage(HttpServletRequest req) {
		ModelAndView model = new ModelAndView("display");
		return model;
	}
	
	@RequestMapping(value = "/showAllDisplay", method = RequestMethod.GET)
	public ModelAndView showAllDisplay(HttpServletRequest req) {
		
		User userBean = (User) req.getSession().getAttribute("userBean");
		ModelAndView model = new ModelAndView("display");
		List<Display> displayList = displayService.findAllDisplay(userBean);
		model.addObject("displayList",displayList);
		return model;

	}
	
	@RequestMapping(value = "/addDisplay", method = RequestMethod.POST)
	public ModelAndView addDisplay(@Valid @ModelAttribute("displaybean") DisplayBean displaybean, BindingResult result,HttpServletRequest req) {
		User userBean = (User) req.getSession().getAttribute("userBean");
		ModelAndView model = new ModelAndView("display");
		String msg = "";
		try {
			
			if(result.hasErrors()){
				msg = "Please correct Data and submit again.";
			} else {
				Display display = new Display();
				display.setDisplayName(displaybean.getDisplayName());
				display.setMacId(displaybean.getMacId());
				display.setDisplayKey(displaybean.getDisplayKey());
				display.setResolution(displaybean.getResolution());
				
				display.setDisplayLocation(displaybean.getDisplayLocation());
				display.setDisplayLocationType(displaybean.getDisplayLocationType());
				display.setDisplayAddress (displaybean.getDisplayAddress ());
				display.setDisplayCity(displaybean.getDisplayCity());
				display.setDisplayDistrict(displaybean.getDisplayDistrict());
				display.setDisplayState(displaybean.getDisplayState());
				display.setDisplayPin(displaybean.getDisplayPin());
				display.setFrequency(displaybean.getFrequency());
				display.setDuration(displaybean.getDuration());
				
				display.setPartyName(displaybean.getPartyName());
				display.setPartyPhone(displaybean.getPartyPhone());
				
				display.setUser(userBean);
				
				displayService.addDisplay(display);
				msg = "Display " + displaybean.getDisplayName() + " added for user " + userBean.getUserId(); 
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			msg = "Dislay with same Mac ID " + displaybean.getMacId() +" is already added.";
			
		}finally{
			List<Display> displayList = displayService.findAllDisplay(userBean);
			model.addObject("displayList",displayList);
		}
		model.addObject("MSG",msg);
		return model;
	}
	
	
	@RequestMapping(value = "/removeDisplay", method=RequestMethod.POST)
	public ModelAndView removeDisplay(@RequestParam("selectedMacIds") String macIds, HttpServletRequest req) {
		
		ModelAndView model = new ModelAndView("display");
		String msg = "";
		User userBean = (User) req.getSession().getAttribute("userBean");
		try {
			
			String[] macArr = macIds.split(",");
			for (int d = 0; d < macArr.length; d++) {
				displayService.removeDisplay(userBean, macArr[d]);
			}
			msg = "Selected Diaply removed for User " +userBean.getUserId();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			msg = "Some display are still in use.Cant proceed for Removal.";
		}finally{
			//List all
			List<Display> displayList = displayService.findAllDisplay(userBean);
			model.addObject("displayList",displayList);
		}
		model.addObject("MSG",msg);
		return model;

	}

	@RequestMapping(value = "/editDisplay", method=RequestMethod.POST)
	public ModelAndView editDisplay(@ModelAttribute("displaybean") DisplayBean displaybean, HttpServletRequest req) {
		
		ModelAndView model = new ModelAndView("display");
		System.out.println("DisplayController.editDisplay()");
		String msg = "";
		User userBean = (User) req.getSession().getAttribute("userBean");
		try {
			Display display = new Display();
			display.setMacId(displaybean.getMacId());
			display.setDuration(displaybean.getDuration());
			display.setFrequency(displaybean.getFrequency());

			int count = displayService.saveDisplayDetails(display);
			if(count == 1)
				msg = "Details saved for MACId: " + displaybean.getMacId();
			else
				msg = "Details could not saved for MACId: " + displaybean.getMacId();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			msg = "Some error Occured. Details could not be saved.";
		}finally{
			//List all
			List<Display> displayList = displayService.findAllDisplay(userBean);
			model.addObject("displayList",displayList);
		}
		model.addObject("MSG",msg);
		return model;

	}

	
	@RequestMapping(value = "/checkMacId", method = RequestMethod.GET)
	public @ResponseBody boolean checkMacId(@PathVariable("macId")String macId, HttpServletRequest req) {
		
		Display display = displayService.checkMacId(macId);
		if(display !=null && display.getMacId().equals(macId)){
			return true;
		}else{
			return false;
		}
	}
	@RequestMapping(value = {"/chkMacAvail", ""}, method = RequestMethod.GET)
	public @ResponseBody String chkMacAvail(HttpServletRequest req) throws InterruptedException {
		String macId = req.getParameter("macId");
		Display display = displayService.checkMacId(macId);
		Thread.sleep(2000);
		if(display !=null && display.getMacId().equals(macId)){
			return "P";
		}else{
			return "NP";
		}
	}
	@RequestMapping(value = {"/pincodeDtls", ""}, method = RequestMethod.GET)
	public @ResponseBody String pincodeDtls(HttpServletRequest req) throws InterruptedException {
		String pincode = req.getParameter("pincode");

		String response = "";
		try {
			String urlStr = "https://data.gov.in/api/datastore/resource.json?resource_id="
					+ Config.getKey("pincode-resource-id") + "&api-key=" + Config.getKey("pincode-api-key")
					+ "&filters[pincode]=" + pincode;
			response = CommonFunction.externalURL(urlStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
