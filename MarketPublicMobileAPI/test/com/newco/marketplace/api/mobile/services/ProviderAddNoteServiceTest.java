package com.newco.marketplace.api.mobile.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.addNotes.AddNoteRequest;
import com.newco.marketplace.api.mobile.beans.addNotes.NoteType;
import com.newco.marketplace.api.mobile.services.ProviderAddNoteService;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.vo.login.LoginCredentialVO;

public class ProviderAddNoteServiceTest{
	
	private ProviderAddNoteService service;
	protected HttpServletRequest httpRequest;
	protected HttpServletResponse httpResponse;
	
	@Test
	public void testExecute(){
		service = new ProviderAddNoteService();
		
		AddNoteRequest addNoteRequest = new AddNoteRequest();
		NoteType type = new NoteType();
		type.setPrivateInd(false);
		type.setSupportInd(false);
		type.setSubject("Test Subject");
		type.setNoteBody("Test Note Body");
		addNoteRequest.setNoteType(type);
		
		APIRequestVO apiVO = new APIRequestVO(httpRequest);
		apiVO.setSOId("");
		apiVO.setProviderResourceId(10254);
		apiVO.setRequestFromPostPut(addNoteRequest);
		
		SecurityContext securityContext = new SecurityContext("melrose1");
		securityContext.setSlAdminInd(false);
		
		LoginCredentialVO lvRoles = new LoginCredentialVO();
		lvRoles.setFirstName("firstName");
		lvRoles.setLastName("lastName");
		lvRoles.setUsername("melrose1");
		lvRoles.setRoleId(1);
		service.execute(apiVO);
	}

}
