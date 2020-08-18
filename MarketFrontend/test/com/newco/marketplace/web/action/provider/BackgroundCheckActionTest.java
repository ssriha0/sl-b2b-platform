package com.newco.marketplace.web.action.provider;

import org.junit.Assert;
import org.junit.Test;

import com.newco.marketplace.web.delegates.provider.ITeamMemberDelegate;
import com.newco.marketplace.web.dto.provider.BackgroundCheckDTO;


public class BackgroundCheckActionTest {
	BackgroundCheckAction backgroundCheckAction;
	ITeamMemberDelegate iTeamMemberDelegate;
	BackgroundCheckDTO teamProfileDTO = new BackgroundCheckDTO();
	@Test
	public void doLoad(){
		backgroundCheckAction = new BackgroundCheckAction(iTeamMemberDelegate, teamProfileDTO);
		teamProfileDTO.setBackgroundCheckShared(true);
		teamProfileDTO.setAction("Other");
		
		backgroundCheckAction.setTeamProfileDto(teamProfileDTO);
	try {
			String result=backgroundCheckAction.execute();
			 Assert.assertEquals(result,"success");

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
