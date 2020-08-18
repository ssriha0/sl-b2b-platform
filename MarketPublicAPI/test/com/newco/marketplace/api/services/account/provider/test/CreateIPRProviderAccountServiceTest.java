package com.newco.marketplace.api.services.account.provider.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.newco.marketplace.api.beans.account.provider.CreateProviderAccountRequest;
import com.newco.marketplace.api.beans.account.provider.CreateProviderAccountResponse;
import com.newco.marketplace.api.services.account.provider.CreateIPRProviderAccountService;
import com.newco.marketplace.vo.provider.ProviderRegistrationVO;




@Ignore
public class CreateIPRProviderAccountServiceTest{
	
	static CreateIPRProviderAccountService createIPRProviderAccountService;
	
	@Before
	public void setUp() {
		createIPRProviderAccountService = new CreateIPRProviderAccountService();		
	}
	
	@Test
	public void validateJsonFormation(){
		createIPRProviderAccountService = new CreateIPRProviderAccountService();
		CreateProviderAccountRequest createProviderAccountRequest = new CreateProviderAccountRequest();
		CreateProviderAccountResponse createProviderAccountResponse = new CreateProviderAccountResponse();
		ProviderRegistrationVO providerRegistrationVO = new ProviderRegistrationVO();
		createProviderAccountRequest.setFirstName("FirstName");
		createProviderAccountRequest.setLastName("LastName");
		createProviderAccountRequest.setLegalBusinessName("Business");
		String request = "{\"parameters\":[{\"phone\":\"\",\"title\":\"\",\"external_id\":\"\",\"middle_name\":\"\",\"last_name\":\"LastName\",\"fax\":\"\",\"addr1\":\"\",\"addr2\":\"\",\"industry\":\"\",\"state_abbrev\":\"\",\"state\":\"\",\"email\":\"\",\"zip\":\"\",\"company_name\":\"Business\",\"website\":\"\",\"city\":\"\",\"date_created\":\"\",\"first_name\":\"FirstName\"}],\"operation\":\"addLead\"}";
		String jsonRequest = null;
		try {
		      // reflect the method by calling classObj.getDeclaredMethod(),
		      // classObj.getMethod() returns the method only if it is public
		      // first parameter if getMethod() or getDeclaredMethod() is the private method name,
		      // followed by the types of the parameters the method has,
		      //ie, if method has more than one parameter, give each parameter type coma separated.
		      Class<? extends CreateIPRProviderAccountService> classObj=createIPRProviderAccountService.getClass();

		       Method getInsideSalesRequestJSON = classObj.getDeclaredMethod("getInsideSalesRequestJSON", CreateProviderAccountRequest.class,CreateProviderAccountResponse.class,ProviderRegistrationVO.class);
		 
		       //setting accessible = true makes sure the private method can be invoked from outside
		       getInsideSalesRequestJSON.setAccessible(true);
		       // invoke the private method by below call, where return type of the method is WestSurveyVO
		       // here parameters of invoke are as below
		       // first parameter will be the object instance of the class in which the private method is situated
		       // followed by method arguments.
		       jsonRequest = (String) getInsideSalesRequestJSON.invoke(createIPRProviderAccountService,createProviderAccountRequest, createProviderAccountResponse,providerRegistrationVO);
   
		}catch (NoSuchMethodException e){
			e.printStackTrace();
		}catch (IllegalAccessException e){
			e.printStackTrace();
		}catch (IllegalArgumentException e){
			e.printStackTrace();
		}catch (InvocationTargetException e){
			e.printStackTrace();
		}
		 
		Assert.assertNotNull(jsonRequest);
		Assert.assertTrue(jsonRequest.equalsIgnoreCase(request));		
	}
	
	@Test
	public void validateLoginJsonFormation(){
		createIPRProviderAccountService = new CreateIPRProviderAccountService();
		createIPRProviderAccountService.setIsApiUserName("user");
		createIPRProviderAccountService.setIsApiPassword("pwd");
		createIPRProviderAccountService.setIsApiToken("gdfjghd");
		String request = "{\"parameters\":[\"user\",\"pwd\",\"gdfjghd\"],\"operation\":\"login\"}";
		String jsonRequest = null;
		try {
		      // reflect the method by calling classObj.getDeclaredMethod(),
		      // classObj.getMethod() returns the method only if it is public
		      // first parameter if getMethod() or getDeclaredMethod() is the private method name,
		      // followed by the types of the parameters the method has,
		      //ie, if method has more than one parameter, give each parameter type coma separated.
		      Class<? extends CreateIPRProviderAccountService> classObj=createIPRProviderAccountService.getClass();

		       Method getInsideSalesLoginRequestJSON = classObj.getDeclaredMethod("getInsideSalesLoginRequestJSON");
		 
		       //setting accessible = true makes sure the private method can be invoked from outside
		       getInsideSalesLoginRequestJSON.setAccessible(true);
		       // invoke the private method by below call, where return type of the method is WestSurveyVO
		       // here parameters of invoke are as below
		       // first parameter will be the object instance of the class in which the private method is situated
		       // followed by method arguments.
		       jsonRequest = (String) getInsideSalesLoginRequestJSON.invoke(createIPRProviderAccountService);
		       System.out.println(jsonRequest);
   
		}catch (NoSuchMethodException e){
			e.printStackTrace();
		}catch (IllegalAccessException e){
			e.printStackTrace();
		}catch (IllegalArgumentException e){
			e.printStackTrace();
		}catch (InvocationTargetException e){
			e.printStackTrace();
		}
		 
		Assert.assertNotNull(jsonRequest);
		Assert.assertTrue(jsonRequest.equalsIgnoreCase(request));		
	}
}
