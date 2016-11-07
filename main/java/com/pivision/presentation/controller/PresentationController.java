package com.pivision.presentation.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.pivision.client.PayLoad;
import com.pivision.presentation.Presentation;
import com.pivision.presentation.formbean.PresentationBean;
import com.pivision.presentation.service.PresentationService;
import com.pivision.user.User;
import com.pivision.util.CommonFunction;
import com.pivision.util.Config;
import com.pivision.util.Constants;

@Controller
@RequestMapping("/presentation")
@SessionAttributes("userBean")

public class PresentationController {

	private static final Logger logger = Logger.getLogger(PresentationController.class);
	@Autowired
	PresentationService presentationService;
	
	@ModelAttribute
	private void initMethod(Model model,@RequestParam(value="linkNo",defaultValue="1")String linkNo) {
		model.addAttribute("LINK_NO",linkNo);
	}

	// Home page Link
	@RequestMapping(value = {"/showAllPresentation", ""}, method = RequestMethod.GET)
	public ModelAndView showAllPresentation(HttpServletRequest req) {
		User userBean = (User) req.getSession().getAttribute("userBean");
		ModelAndView model = new ModelAndView("presentation");
		List<Presentation> presentationList = presentationService.findAllPresentation(userBean.getUserId());
		model.addObject("presentationList",presentationList);
		return model;

	}
	
	//////////////////////////////////////////


	
	
    @RequestMapping(value = "/uploadPresentation", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView uploadPresentation(@RequestParam("imageFile") MultipartFile file, HttpServletRequest req,@Valid @ModelAttribute("pageBean") PresentationBean pageBean) {
		ModelAndView model = new ModelAndView("presentation");
		User userBean = (User) req.getSession().getAttribute("userBean");
		String name = req.getParameter("presentationName");

        // Creating the directory to store file
        String rootPath = Constants.imgPath;//req.getSession().getServletContext().getRealPath("/");
        if (!file.isEmpty()) {
        	String extn = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1, file.getOriginalFilename().length()) ;
        	String allowedExtn = Config.getKey("allowdImgExtn");
        	if(allowedExtn.indexOf(extn.toUpperCase())!=-1){
        		
	            try {
	                byte[] bytes = file.getBytes();
	                String imageNameWthExtn = userBean.getUserId()+"_"+System.currentTimeMillis()+"."+extn;//file.getOriginalFilename();
	                System.out.println(rootPath);
	                File dir = new File(rootPath + File.separator + "/media/presentation");
	                if (!dir.exists())
	                    dir.mkdirs();
	 
	                // Create the file on server
					File serverFile = new File(dir.getAbsolutePath() + File.separator + imageNameWthExtn);
	                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
	                stream.write(bytes);
	                stream.close();
	               // System.out.println("File Path:: "+serverFile.getAbsolutePath().substring(serverFile.getAbsolutePath().lastIndexOf(System.getProperty("file.separator"))+1,serverFile.getAbsolutePath().length()));
	                Presentation pstn = new Presentation();
	               // pstn.setPresentationName(serverFile.getAbsolutePath().substring(serverFile.getAbsolutePath().lastIndexOf(System.getProperty("file.separator"))+1,serverFile.getAbsolutePath().length()));
	                pstn.setPresentationName(pageBean.getPresentationName());
	                pstn.setLocation(pageBean.getLocation());
	                pstn.setCategory(pageBean.getCategory());
	                
	                pstn.setFileName(imageNameWthExtn);
	                pstn.setFileType(file.getContentType());
	                pstn.setContentType(extn.toLowerCase());
	                
	                pstn.setUser(userBean);
	                presentationService.uploadPresentation(pstn);
	                
	                List<Presentation> presentationList = presentationService.findAllPresentation(userBean.getUserId());
	        		model.addObject("presentationList",presentationList);
	                
	                model.addObject("MSG","Image Uploaded successfully");
	            } catch (Exception e) {
	            	 model.addObject("MSG","Image Uploaded Failed  => " + e.getMessage());
	            	 e.printStackTrace();
	            	 logger.error("error uploading presentation"+e.getLocalizedMessage());
	            }
        	}else{
        		   
                List<Presentation> presentationList = presentationService.findAllPresentation(userBean.getUserId());
        		model.addObject("presentationList",presentationList);
				model.addObject("MSG", "Only " + allowedExtn + " is allowed");
        	}
        } else {
        	   
			List<Presentation> presentationList = presentationService.findAllPresentation(userBean.getUserId());
			model.addObject("presentationList",presentationList);
			model.addObject("MSG","You failed to upload " + name + " because the file was empty.");
			
        }
        pageBean.reset();
        return model;
    }
    
    @RequestMapping(value = {"/removePresentation", ""}, method = RequestMethod.POST)
	public ModelAndView removePresentation(@RequestParam("presentationId") String presentationIds,HttpServletRequest req) {
    	User userBean = (User) req.getSession().getAttribute("userBean");
    	String errMsg ="";
    	ModelAndView model = new ModelAndView("presentation");;
		try {
			
			String[] pstnArr = presentationIds.split(",");
			
			for (int p = 0; p < pstnArr.length; p++) {
				presentationService.removePresentation(userBean.getUserId(), pstnArr[p]);
			}
			
			errMsg = "Selected Presentation removed for user "+userBean.getUserId()+".";
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			errMsg = "Could not delete presentation.";
		}finally{
			List<Presentation> presentationList = presentationService.findAllPresentation(userBean.getUserId());
			model.addObject("presentationList",presentationList);
		}
		
		model.addObject("MSG",errMsg);
		return model;
	}
    
    
    @RequestMapping(value = {"/getImage", ""}, method = RequestMethod.GET)
	public @ResponseBody PayLoad getImage(HttpServletRequest req) {
		//System.out.println("PresentationController.getImage()");
		String imgFileName = req.getParameter("imgFileName");
		String imgFileType = req.getParameter("imgFileType");
		String rootPath = Constants.imgPath;
		String imageBytes = CommonFunction.imagteToByte(rootPath + "/media/presentation/"+ imgFileName, imgFileType);
		PayLoad payLoad = new PayLoad();
		payLoad.setFileName(imgFileName);
		payLoad.setMimeType(imgFileType);
		payLoad.setMediaString(imageBytes);
    	
		return payLoad;

	}

	
}
