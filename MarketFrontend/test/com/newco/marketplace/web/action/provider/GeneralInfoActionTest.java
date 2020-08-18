package com.newco.marketplace.web.action.provider;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Test;

import com.newco.marketplace.web.delegates.provider.IGeneralInfoDelegate;
import com.newco.marketplace.web.dto.provider.GeneralInfoDto;


public class GeneralInfoActionTest {
	GeneralInfoAction generalInfoAction;
	IGeneralInfoDelegate iGeneralInfoDelegate;               
	GeneralInfoDto generalInfoDto= new GeneralInfoDto();
	@Test
	public void validateForResourceHavingSamePersonalInfo(){
		
	try {
		iGeneralInfoDelegate = mock(IGeneralInfoDelegate.class);
			generalInfoAction = new GeneralInfoAction(iGeneralInfoDelegate,
					generalInfoDto);
			generalInfoDto.setAction("Other");
			generalInfoAction.setGeneralInfoDto(generalInfoDto);

			String result = generalInfoAction
					.execute();
			System.out.println(result);
			Assert.assertEquals(result, "success");

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
