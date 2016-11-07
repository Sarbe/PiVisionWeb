package com.pivision.presentation.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.pivision.presentation.Presentation;
import com.pivision.presentation.dao.PresentationDao;


@Repository
public class PresentationDaoJPAImpl implements PresentationDao {
	
	@PersistenceContext(unitName="PiVisionPersistence")
	private EntityManager entityManager;

	@Override
	public void uploadPresentation(Presentation prsntn) {
	//TypedQuery<Presentation> query = entityManager.createQuery(
	//			"SELECT p FROM Presentation p WHERE p.user.userId = '"+prsntn.getUser().getUserId()+"'", Presentation.class);
		
		entityManager.persist(prsntn);
		
	}
	@Override
	public List<Presentation> selectAllUserPresentations(String userId) {
		TypedQuery<Presentation> query = entityManager.createQuery(
				"SELECT p FROM Presentation p WHERE p.user.userId = '"+userId+"'", Presentation.class);
		List<Presentation> presentationList = query.getResultList();
		return presentationList;
	}
	@Override
	public void removePresentation(String userId,String PresentationId) {
		TypedQuery<Presentation> query = entityManager.createQuery(
				"SELECT p FROM Presentation p WHERE p.presentationId = '" + PresentationId + "' and p.user.userId = '"+userId+"'", Presentation.class);
		List<Presentation> prsntList = query.getResultList();
		if (prsntList != null && prsntList.size() > 0) {
			entityManager.remove(prsntList.get(0));
		} 
	}
	
	
	@Override
	public Presentation getPresentationById(String prsntnId) {
		TypedQuery<Presentation> query = entityManager.createQuery(
				"SELECT p FROM Presentation p WHERE p.presentationId = '" + prsntnId + "'", Presentation.class);
		List<Presentation> presentationList = query.getResultList();
		return presentationList.get(0);

	}
}
