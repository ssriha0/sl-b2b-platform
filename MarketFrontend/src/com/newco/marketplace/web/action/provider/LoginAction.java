/*
 * $Id: LoginAction.java,v 1.9 2008/04/26 01:13:50 glacy Exp $
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.web.delegates.provider.ILoginDelegate;
import com.newco.marketplace.web.dto.provider.LoginDto;
import com.newco.marketplace.web.utils.Config;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.Validation;

/**
 * @author KSudhanshu
 *
 */
@Validation
public class LoginAction extends ActionSupport implements ModelDriven<LoginDto>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8656654386804097801L;
	private String username;
	private String password;
	private ILoginDelegate iLoginDelegate;
	private LoginDto loginDto = new LoginDto();	
	private Cryptography cryptography;
	
	/**
	 * @param loginDelegate
	 * @param loginDto
	 */
	public LoginAction(ILoginDelegate loginDelegate, LoginDto loginDto) {
		iLoginDelegate = loginDelegate;
		this.loginDto = loginDto;
	}

	@SkipValidation
	public String doLoad() throws Exception {
		return SUCCESS;
	}

	public void prepare() throws Exception
	{
		doLoad();
	}	
	/*
	 * (non-Javadoc)`
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() throws Exception {
		loginDto= getModel();
		
		//boolean temp = iLoginDelegate.getTempPasswordIndicator(loginDto.getUsername());
		//String password = CryptoUtil.encrypt(getPassword());
		//if (temp){
		//	password = CryptoUtil.encrypt(getPassword().trim());
		//}else{
		//	password = CryptoUtil.encrypt(getPassword());
		//}
		//loginDto.setPassword(password);

		int userLoginStatus = iLoginDelegate.validatePassword(loginDto);
		
		if(userLoginStatus == -2)
		{
			addFieldError("buyerRegistrationDTO.mobPhoneNo1", "Error while validating the Administrator mobile number.");
		}
		if (userLoginStatus == -1) {
			addFieldError("buyerRegistrationDTO.mobPhoneNo1", "Error while validating the Administrator mobile number.");
		}
		//User Id has been locked
		else if(userLoginStatus == 2){
			addActionError(Config.getResouceBundle().getString("loginForm.userid.locked.error"));
		 	return INPUT;
		} 
		//ActionContext.getContext().getSession().put("vendorId",
		//		iLoginDelegate.getVendorId(getUsername()));
		//ActionContext.getContext().getSession().put("providerName",
		//		iLoginDelegate.getProviderName(getUsername()));
		//ActionContext.getContext().getSession().put("username", getUsername());

		if (userLoginStatus == 1)
			return "changePassword";
		else
			return SUCCESS;
	}

	/**
	 * @return
	 */
	//public String getUsername() {
//		return username;
	//}

	/**
	 * @param username
	 */
	//public void setUsername(String username) {
	//	this.username = username;
	//}

	/**
	 * @return
	 */
	//public String getPassword() {
	//	return password;
	//}

	/**
	 * @param password
	 */
	//public void setPassword(String password) {
	//	this.password = password;
	//}
	
	public LoginDto getModel()
	{
		return loginDto;
	}
	
	public String display() throws Exception
	{		
	   //For SL-10675 
		Cookie[] cookies = getRequest().getCookies();
		boolean isBannerCookie=false;
		for (Cookie c : cookies) {
			
			if (c.getName().equals("username")) {			
				if(c.getValue() != null &&
						!c.getValue().equalsIgnoreCase("null")){
						CryptographyVO cryptographyVO = new CryptographyVO();
						cryptographyVO.setInput(c.getValue());
						cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);						
						cryptographyVO = getCryptography().decryptKey(cryptographyVO);		
						if(cryptographyVO != null && 
								null != cryptographyVO.getResponse()){
							getRequest().setAttribute("cookieUserName", cryptographyVO.getResponse()) ;					
						}							
					}
			}
			if (c.getName().equals("rememberId")) {
				if (c.getValue() != null
						&& !c.getValue().equalsIgnoreCase("null")) {
					getRequest().setAttribute("cookieRememberId", c.getValue());
				}
			}
		/*	if(c.getName().equals("isDismissedBanner")){
				if (c.getValue() != null && !c.getValue().equalsIgnoreCase("null")) {
					//if cookie has a value,no need to create a new cookie.
					if(c.getValue().equalsIgnoreCase("Yes") || c.getValue().equalsIgnoreCase("No") ){
						isBannerCookie=true;
					}
				}else{
					//Trying to create a session cookie to hold the value of isdismissedBanner
					Cookie bannerCoockie=new Cookie("isDismissedBanner","No");
					bannerCoockie.setMaxAge(-1);
					bannerCoockie.setPath("/");
					getResponse().addCookie(bannerCoockie);
					isBannerCookie=true;
				}
			}*/
			if (getRequest().getAttribute("cookieUserName") != null
					&& getRequest().getAttribute("cookieRememberId") != null)
				break;
		}
		//we have to ensure banner cookie is created if it is not present.
		/*if(!isBannerCookie){
			Cookie bannerCoockie=new Cookie("isDismissedBanner","No");
			bannerCoockie.setMaxAge(-1);
			bannerCoockie.setPath("/");
			getResponse().addCookie(bannerCoockie);
			isBannerCookie=true;
		}*/
		if (getRequest().getAttribute("cookieUserName") == null)
					getRequest().setAttribute("cookieUserName", "") ;			
		if (getRequest().getAttribute("cookieRememberId") == null)
			getRequest().setAttribute("cookieRememberId", "checked");
		
		
		return "displayPage";
	}
	
	public String getUsername(){
		return username;
	}

	public void setUsername(String value){
		username = value;
	}


	public String getPassword(){
		return password;
	}

	public void setPassword(String value){
		password = value;
	}
	
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public Cryptography getCryptography() {
		return cryptography;
	}

	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}
}