package com.pivision.presentation.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pivision.presentation.Presentation;
import com.pivision.presentation.dao.PresentationDao;

@Component
public class PresentationService {

	private static final Logger logger = Logger.getLogger(PresentationService.class);
	
	@Autowired
	private PresentationDao presentationDao;
	
	public String createPresentation() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transactional
	public void uploadPresentation(Presentation pstn) {
		
		
		try {
			pstn.setCreatedDate(new Date());
			pstn.setLastUpdated(new Date());
			//System.out.println("uploading presentation");
			presentationDao.uploadPresentation(pstn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Error uploading presenation"+e.getMessage());
			e.printStackTrace();
			
		}
	}

	@Transactional
	public void removePresentation(String userId,String presentationId) {
		presentationDao.removePresentation(userId, presentationId);
	}

	@Transactional
	public String editTemplate(Presentation presentation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public String previewPresentation(Presentation presentation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public List<Presentation> findAllPresentation(String userId) {
		return presentationDao.selectAllUserPresentations(userId);
	}
	
	@Transactional
	public Presentation findPresentationById(String prsntnId) {
		return presentationDao.getPresentationById(prsntnId);
	}

}
