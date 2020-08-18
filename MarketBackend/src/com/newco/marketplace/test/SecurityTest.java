package com.newco.marketplace.test;

import java.util.ArrayList;

import junit.framework.TestCase;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.security.ISecurityBO;
import com.newco.marketplace.dto.vo.AccessControlList;
import com.newco.marketplace.dto.vo.ActivityTemplate;
import com.newco.marketplace.dto.vo.ActivityVO;

public class SecurityTest extends TestCase{
	String userName;
	int roleId;
	int roleIdInsert;
	String userNameInsert;
	ArrayList<ActivityVO> activityList = new ArrayList<ActivityVO>();
	@Override
	protected void setUp() throws Exception{
		userName = "auditor01";
		userNameInsert = "auditor03";
		roleId = 4;
		roleIdInsert = 4;
		
		ActivityVO activityVO1 = new ActivityVO();
		activityVO1.setActivityId(1);
		
		ActivityVO activityVO2 = new ActivityVO();
		activityVO2.setActivityId(2);	
		
		ActivityVO activityVO3 = new ActivityVO();
		activityVO3.setActivityId(3);	
		
		ActivityVO activityVO4 = new ActivityVO();
		activityVO4.setActivityId(4);		
		
		activityList.add(activityVO1);
		activityList.add(activityVO2);
		activityList.add(activityVO3);
		activityList.add(activityVO4);
	}
	public void testSecurityTest(){
		ISecurityBO iSecurityBO = (ISecurityBO)MPSpringLoaderPlugIn.getCtx().getBean("securityBO");
		AccessControlList acl = iSecurityBO.getActivitiesByUserName(userName);
		System.out.println(acl.getActivities());	
		System.out.println("activityId="+acl.getActivities().get(0).getActivityId());
		System.out.println("activityId size="+acl.getActivities().size());
		assertTrue(acl.getActivities().size() == 2);
		
		ActivityTemplate at = iSecurityBO.getActivitiesForUserGroup(roleId);
		System.out.println(at.getActivities());
		System.out.println("activityId="+at.getActivities().get(0).getActivityId());
		System.out.println("activityId size="+at.getActivities().size());
		//assertTrue(at.getActivities().size() == 6);
		
		iSecurityBO.assignActivitiesToUserGroup(roleIdInsert, activityList);
		assertTrue(true);
		
		iSecurityBO.removeActivitiesFromUserGroup(roleIdInsert, activityList);
		assertTrue(true);
		
		iSecurityBO.assignActivitiesToUser(userNameInsert, activityList);
		assertTrue(true);	
		
		iSecurityBO.removeActivitiesFromUser(userNameInsert, activityList);
		assertTrue(true);
		
	}
	public static void main(String args[]){

	}
}
