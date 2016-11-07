package com.pivision.presentation.dao;

import java.util.List;

import com.pivision.presentation.Presentation;

public interface PresentationDao {

	public void uploadPresentation(Presentation prsntn);

	public List<Presentation> selectAllUserPresentations(String userId);

	public void removePresentation(String userId,String PresentationId);

	Presentation getPresentationById(String prsntnId);
}
