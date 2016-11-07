package com.pivision.schedule.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.pivision.display.Display;
import com.pivision.display.service.DisplayService;
import com.pivision.presentation.Presentation;
import com.pivision.presentation.service.PresentationService;
import com.pivision.schedule.ActiveSchedule;
import com.pivision.schedule.Schedule;
import com.pivision.schedule.formbean.ScheduleBean;
import com.pivision.schedule.service.ScheduleService;
import com.pivision.user.User;
import com.pivision.util.Constants;

@Controller
@RequestMapping("/schedule")
@SessionAttributes({"userBean","presentationList","displayList","scheduleList"})
public class ScheduleController {

	@Autowired
	ScheduleService scheduleService;

	@Autowired
	PresentationService presentationService;

	@Autowired
	DisplayService displayService;

	private static final Logger logger = Logger.getLogger(ScheduleController.class);

	@InitBinder
	public void customDataFormatBinder(WebDataBinder binder){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, "fromDate", new CustomDateEditor(sdf, false));
		binder.registerCustomEditor(Date.class, "toDate", new CustomDateEditor(sdf, false));
	}
	
	@ModelAttribute
	private void initMethod(Model model, @RequestParam(value = "linkNo", defaultValue = "1") String linkNo) {
		model.addAttribute("LINK_NO", linkNo);
	}

	// Home page Link
	@RequestMapping(value = "/showAllSchedule", method = RequestMethod.GET)
	public ModelAndView showAllDisplay(@ModelAttribute("formBean") ScheduleBean scheduleBean, HttpServletRequest req) {

		logger.debug("Schedule Controller - Show All Schedule");

		User userBean = (User) req.getSession().getAttribute("userBean");
		ModelAndView model = new ModelAndView("schedule");
		// Schedule List
		List<Schedule> scheduleList = scheduleService.findAllSchedule(userBean.getUserId());
		model.addObject("scheduleList", scheduleList);
		// Presentation List
		List<Presentation> presentationList = presentationService.findAllPresentation(userBean.getUserId());
		model.addObject("presentationList", presentationList);
		// Display List
		List<Display> displayList = displayService.findAllDisplay(userBean);
		model.addObject("displayList", displayList);
		// empty backing bean
		model.addObject("formBean", new ScheduleBean());

		return model;

	}

	// Home page Link
	@RequestMapping(value = "/saveScheduleDetails", method = RequestMethod.POST)
	public ModelAndView saveScheduleDetails(@Valid @ModelAttribute("formBean") ScheduleBean scheduleBean,
			BindingResult result, HttpServletRequest req) {
		
		User userBean = (User) req.getSession().getAttribute("userBean");
		ModelAndView model = new ModelAndView("schedule");
		
		if(result.hasErrors()){
			model.addObject("MSG","Please correct Data and submit again.");
			return model;
		}
		
		logger.debug(scheduleBean.getMacId());
		// schedule.setPresentation(scheduleBean.getPresentation());

		Schedule schedule = new Schedule();
		Display display = new Display();
		display.setMacId(scheduleBean.getMacId());
		Presentation presentation = new Presentation();
		presentation.setPresentationId(scheduleBean.getPresentationId());
		
		// Set schedule
		schedule.setUser(userBean);
		// schedule.setDisplay(display);
		schedule.setPresentation(presentation);
		
		schedule.setScheduleId(scheduleBean.getScheduleId());
		schedule.setScheduleName(scheduleBean.getScheduleName());
		schedule.setFrom_date(scheduleBean.getFromDate());
		schedule.setTo_date(scheduleBean.getToDate());
		schedule.setTimeOfDay(scheduleBean.getTimeOfDay());
		logger.debug(scheduleBean.getScheduleName());

		scheduleService.saveScheduleBean(schedule);

		// List All
		List<Schedule> scheduleList = scheduleService.findAllSchedule(userBean.getUserId());
		model.addObject("scheduleList", scheduleList);

		return model;
	}

	
	@RequestMapping(value = "/addDisplayToSchedule", method = RequestMethod.POST)
	public ModelAndView addDisplayToSchedule(@ModelAttribute("formBean") ScheduleBean scheduleBean,
			HttpServletRequest req) {

		User userBean = (User) req.getSession().getAttribute("userBean");
		logger.debug("MAC ID:" + scheduleBean.getMacId());
		logger.debug("Schedule id:" + scheduleBean.getScheduleId());
		ModelAndView model = new ModelAndView("schedule");
		String msg ="";
		Schedule schedule = scheduleService.getScheduleByID(String.valueOf(scheduleBean.getScheduleId()));
		try {
			String[] macIds = scheduleBean.getMacId().split(",");
			int counter = 0;
			
			for(int i = 0;i<macIds.length;i++){
				
				Display display = new Display();
				display.setMacId(macIds[i]);
				//schedule.setScheduleId(scheduleBean.getScheduleId());
				schedule.setUser(userBean);
				ActiveSchedule activeSchedule = new ActiveSchedule();
				activeSchedule.setScheduleName(schedule.getScheduleName());
				activeSchedule.setPresentation(schedule.getPresentation());
				activeSchedule.setUser(userBean);
				activeSchedule.setSchedule(schedule);
				activeSchedule.setDisplay(display);
				activeSchedule.setScheduleDuration(new Integer(scheduleBean.getDuration()));
				//activeSchedule.setFrequency(new Integer(scheduleBean.getFrequency()));

				activeSchedule.setActiveDate(new Date());
				activeSchedule.setEndDate(schedule.getTo_date());
				int status = scheduleService.addDisplayToSchd(activeSchedule);
				if(status == 0){
					logger.info("active schedule is already present for macId :: " + macIds[i]);
				}else{
					counter++;
					logger.info("Active schedule created for macId :: " +macIds[i]);
				}
				
			}
			// set the flag for schedule to published
			if(counter >0 ){// active schedule has been created for some display
				if(macIds.length == counter){
					msg = "Display are added to Schedule (" + scheduleBean.getScheduleId() + ") for user " + userBean.getUserId();
				}else{
					msg = "Some display got added to schedule " + scheduleBean.getScheduleId() + ") for user " + userBean.getUserId();
				}
				
				// Set the published flag
				/*schedule.setPublished("Y");
				schedule.setLastAccessed(new Date());
				scheduleService.updateFlag(schedule);*/
			}else{
				msg = "Display could not be added to schedule "+ scheduleBean.getScheduleId() +"for user " + userBean.getUserId()+
						". Please check the display selected";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{
			// List All
			List<Schedule> scheduleList = scheduleService.findAllSchedule(userBean.getUserId());
			model.addObject("scheduleList", scheduleList);
			model.addObject("MSG", msg);
		}
		return model;
	}

	
	
	
	
	@RequestMapping(value = "/publishSchedule", method = RequestMethod.POST)
	public ModelAndView publishSchedule(@RequestParam("scheduleIds") String scheduleIds,@ModelAttribute("formBean") ScheduleBean scheduleBean, HttpServletRequest req) {

		User userBean = (User) req.getSession().getAttribute("userBean");
		logger.debug("Schedule id:" + scheduleBean.getScheduleId());
		
		ModelAndView model = new ModelAndView("schedule");
		String msg ="";
		Schedule schedule = scheduleService.getScheduleByID(scheduleIds);
		
		//check if current date is in between the start date and end date of schedule
		Date today = new Date();
		try {
			if((schedule.getFrom_date().before(today)|| schedule.getFrom_date().equals(today)) &&
				schedule.getTo_date().after(today)||schedule.getTo_date().equals(today)){
				// Set the published flag
				schedule.setPublished(Constants.SCHD_PUBLISHED);
				schedule.setLastAccessed(new Date());
				scheduleService.publishSchedule(schedule);
				msg =  "Schedule published for the user "+userBean.getUserId();
			}else{
				msg =  "Date is outside of the Window. Can not publish";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			msg =  "Some error Ocuurred.";
		}finally{
			// List All
			List<Schedule> scheduleList = scheduleService.findAllSchedule(userBean.getUserId());
			model.addObject("scheduleList", scheduleList);
			model.addObject("MSG", msg);
		}
		
		return model;
	}

	
	@RequestMapping(value = "/unpublishSchedule", method=RequestMethod.POST)
	public ModelAndView unpublishSchedule(@RequestParam("scheduleIds") String scheduleIds, @ModelAttribute("formBean") ScheduleBean scheduleBean,HttpServletRequest req) {
		ModelAndView model = new ModelAndView("schedule");
		User userBean = (User) req.getSession().getAttribute("userBean");
		
		try {
			scheduleService.unpublishSchedule(userBean.getUserId(), scheduleIds);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally{
			// List All
			List<Schedule> scheduleList = scheduleService.findAllSchedule(userBean.getUserId());
			model.addObject("scheduleList", scheduleList);
			String msg = "Schedule ("+scheduleIds+") unpublished for user "+userBean.getUserId()+".";
			model.addObject("MSG",msg);
		}
		
		return model;

	}
	
	
	@RequestMapping(value = "/removeSchedule", method=RequestMethod.POST)
	public ModelAndView removeSchedule(@RequestParam("scheduleIds") String scheduleIds, @ModelAttribute("formBean") ScheduleBean scheduleBean,HttpServletRequest req) {
		ModelAndView model = new ModelAndView("schedule");
		User userBean = (User) req.getSession().getAttribute("userBean");
		logger.debug("Removing schedule:"+scheduleIds);
		String msg = "";
		try {
			String[] schIdArr = scheduleIds.split(",");
			for (int i = 0; i < schIdArr.length; i++) {
				scheduleService.deleteSchedule(userBean.getUserId(), schIdArr[i]);
			}
			
			msg = "Selected Schedules removed for user "+userBean.getUserId()+".";
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			msg = "Schedule ("+scheduleIds+") Could not be removed";
			e.printStackTrace();
		}finally{
			// List All
			List<Schedule> scheduleList = scheduleService.findAllSchedule(userBean.getUserId());
			model.addObject("scheduleList", scheduleList);
		}
		model.addObject("MSG",msg);
		return model;

	}
	
	
	@RequestMapping(value = "/removeActiveSchedule", method=RequestMethod.POST)
	public ModelAndView removeActiveSchedule(@ModelAttribute("formBean") ScheduleBean scheduleBean,@RequestParam("activeScheduleIds") String activeScheduleIds,HttpServletRequest req) {
		ModelAndView model = new ModelAndView("schedule");
		User userBean = (User) req.getSession().getAttribute("userBean");
		logger.debug("Removing Active schedule:" + activeScheduleIds);
		String msg = "";
		try {
			boolean status = scheduleService.removeActiveSchedule(userBean.getUserId(), activeScheduleIds);
			
			if(status){
				msg = "Active Schedule deleted for user "+userBean.getUserId();
			}else{
				msg = "Active Schedule can't be deleted.";
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			msg = "Active Schedule (" + activeScheduleIds + ") Could not be removed";
			e.printStackTrace();
		}finally{
			// List All
			List<Schedule> scheduleList = scheduleService.findAllSchedule(userBean.getUserId());
			model.addObject("scheduleList", scheduleList);
		}
		model.addObject("MSG",msg);
		return model;

	}
}
