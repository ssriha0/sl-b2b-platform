/*
 * $Id: AddServiceProAction.java,v 1.11 2008/04/26 01:13:50 glacy Exp $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.newco.marketplace.web.action.provider;


import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.provider.TeamMemberVO;
import com.newco.marketplace.web.delegates.provider.IActivityRegistryDelegate;
import com.newco.marketplace.web.delegates.provider.IServiceProviderDelegate;
import com.newco.marketplace.web.delegates.provider.ITeamMemberDelegate;
import com.newco.marketplace.web.dto.provider.TeamProfileDTO;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author KSudhanshu
 *
 */
public class AddServiceProAction extends ActionSupport {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 286235072753870490L;
	private ITeamMemberDelegate iTeamMemberDelegate;
	private TeamProfileDTO teamProfileDTO;
	private SecurityContext securityContext;
	private static final Logger logger = Logger.getLogger(AddServiceProAction.class.getName());
	private HttpSession session;
	private SortedMap tempServiceProvidersList = new TreeMap(); // stores (firstname + lastname) as key and Resource id as value
	private Map serviceProvidersList = new HashMap(); // stores ResourceId as key and (firstname + last name) as value
	private int numberOfServiceProviders = 0;
	private String defaultServiceProvider = new String();
	private Integer defaultServiceProviderResourceId = new Integer(-1);
	private IActivityRegistryDelegate activityRegistryDelegate;



	private IServiceProviderDelegate iServiceProviderDelegate;

	/**
	 * @param serviceProviderDelegate
	 * @param iTeamMemberDelegate
	 * @param teamProfileDTO
	 */
	public AddServiceProAction(
			IServiceProviderDelegate serviceProviderDelegate,
			ITeamMemberDelegate iTeamMemberDelegate,
			TeamProfileDTO teamProfileDTO,
			IActivityRegistryDelegate activityRegistryDelegate) {
		this.iServiceProviderDelegate = serviceProviderDelegate;
		this.iTeamMemberDelegate = iTeamMemberDelegate;
		this.teamProfileDTO = teamProfileDTO;
		this.activityRegistryDelegate = activityRegistryDelegate;
	}	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() throws Exception {
		// this needs a new drop down
		// if there are multiple users already added that have skills associated
		// forward to the list
		// if there are multiple users forward to a drop down version of the ServiceProviderProfile Page
		// drop down contains a list of all team members for the company
		// asking who should we configure as a service professional
//		then sending to the service pro all tabs page with the "Will this person " disabled and set to yes and disabled
		// 
		
		try{
			
			setContextDetails();
			teamProfileDTO.setPrimaryInd(securityContext.isPrimaryInd());
			teamProfileDTO.setUserName((String)ActionContext.getContext().getSession().get("username"));
			
			teamProfileDTO.setVendorId(new Integer((String)ActionContext.getContext().getSession().get("vendorId")));
				teamProfileDTO.setAdminResourceId(securityContext.getAdminResId());			
			
			teamProfileDTO=iTeamMemberDelegate.getTeamMemberList(teamProfileDTO);
			
			/* Store FirstName+LastName in sorted order as key and ResourceId as value */
			tempServiceProvidersList = getTempServiceProvidersList(teamProfileDTO);

			// Exchange Keys and Values
			serviceProvidersList = exchangeKeysAndValues(tempServiceProvidersList);
			
			
			// To show team member grid
			teamProfileDTO=iTeamMemberDelegate.getTeamGridDetails(teamProfileDTO);
			if((teamProfileDTO != null)&&(teamProfileDTO.getTeamMemberList() != null)){
				numberOfServiceProviders =teamProfileDTO.getTeamMemberList().size();
			}
			logger.info("ServiceProviders List   :" + serviceProvidersList);
			teamProfileDTO.setAdminResourceId(securityContext.getAdminResId());	
			teamProfileDTO.setLoggedResourceId(securityContext.getVendBuyerResId());
			
			String resourceId = securityContext.getVendBuyerResId().toString();
			String vendorId = (String)ActionContext.getContext().getSession().get("vendorId");
			ActionContext.getContext().getSession().put("providerStatus", activityRegistryDelegate.getProviderStatus(vendorId));
			ActionContext.getContext().getSession().put("resourceStatus", activityRegistryDelegate.getResourceStatus(resourceId));
			
		}catch(DelegateException ex){
			ex.printStackTrace();
			logger.info("Exception Occured in loadUsers() of ManageUsersAction while processing the request due to"+ex.getMessage());
			addActionError("Exception Occured loadUsers() of ManageUsersAction while processing the request due to"+ex.getMessage());
			return ERROR;
		}
		
		
		return SUCCESS;
	}

	private void setContextDetails(){
		session = ServletActionContext.getRequest().getSession();
		securityContext = (SecurityContext) session.getAttribute("SecurityContext");
	}


	public String getDefaultServiceProvider() {
		return defaultServiceProvider;
	}


	public void setDefaultServiceProvider(String defaultServiceProvider) {
		this.defaultServiceProvider = defaultServiceProvider;
	}

	public Map getServiceProvidersList() {
		return serviceProvidersList;
	}


	public void setServiceProvidersList(Map serviceProvidersList) {
		this.serviceProvidersList = serviceProvidersList;
	}


	public int getNumberOfServiceProviders() {
		return numberOfServiceProviders;
	}


	public void setNumberOfServiceProviders(int numberOfServiceProviders) {
		this.numberOfServiceProviders = numberOfServiceProviders;
	}


	public Integer getDefaultServiceProviderResourceId() {
		return defaultServiceProviderResourceId;
	}


	public TeamProfileDTO getTeamProfileDTO() {
		return teamProfileDTO;
	}
	public void setTeamProfileDTO(TeamProfileDTO teamProfileDTO) {
		this.teamProfileDTO = teamProfileDTO;
	}
	public void setDefaultServiceProviderResourceId(
			Integer defaultServiceProviderResourceId) {
		this.defaultServiceProviderResourceId = defaultServiceProviderResourceId;
	}
	
	
	public SortedMap getTempServiceProvidersList(TeamProfileDTO teamProfileDTO )
	{
		SortedMap tempServiceProvidersList = new TreeMap();
		Iterator iterator = teamProfileDTO.getTeamMemberList().iterator();
		
		while(iterator.hasNext())
		{
			TeamMemberVO teamMemberVO = new TeamMemberVO();
			teamMemberVO = (TeamMemberVO) iterator.next();
			if(teamMemberVO.getResourceId().equals(teamProfileDTO.getAdminResourceId()))
			{
				setDefaultServiceProvider(teamMemberVO.getFirstName()+" "+teamMemberVO.getLastName());
				setDefaultServiceProviderResourceId(teamMemberVO.getResourceId());
				System.out.println("Provider ResourceId  " + getDefaultServiceProviderResourceId());
				System.out.println("Provider Name  " + getDefaultServiceProvider());
			}
			else
			{
				//serviceProvidersList.put(teamMemberVO.getResourceId(),teamMemberVO.getFirstName()+" "+teamMemberVO.getLastName());
				tempServiceProvidersList.put(teamMemberVO.getFirstName()+" "+teamMemberVO.getLastName(),teamMemberVO.getResourceId());
			}
		}
		return tempServiceProvidersList;
	}
	
	
	public Map exchangeKeysAndValues(SortedMap serviceProvidersList)
	{
		  Map tempMap = new LinkedHashMap();
		  Set keySet = serviceProvidersList.keySet();
	      Iterator i = keySet.iterator();
	      while (i.hasNext()) {
	          String key = (String) i.next();
	          Integer value = (Integer) serviceProvidersList.get(key);
	          System.out.println("adding " + value + " : " + key);
	          tempMap.put(value, key);
	      }
		return tempMap;
	}


}