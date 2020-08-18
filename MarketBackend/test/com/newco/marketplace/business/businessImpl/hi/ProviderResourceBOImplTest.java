package com.newco.marketplace.business.businessImpl.hi;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.business.businessImpl.hi.ProviderResourceBOImpl;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.hi.provider.ProviderSkillVO;
import com.newco.marketplace.vo.hi.provider.SkillDetailVO;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.vo.hi.provider.ProviderAccountVO;


public class ProviderResourceBOImplTest {

 
	private static final Logger LOGGER  =Logger.getLogger(ProviderResourceBOImpl.class);
	private ProviderResourceBOImpl  providerResourceBO;
	
	@Before
	public void setUp() {
		providerResourceBO = new ProviderResourceBOImpl();
	}
	
	@Test
	public void validateProviderSkill(){
		ProviderSkillVO providerSkillVO=new ProviderSkillVO();
		SkillDetailVO skillDetailVO=new SkillDetailVO();
		List<String> serviceTypeList=new ArrayList<String>();
		List<SkillDetailVO> skillDetailVOList=new ArrayList<SkillDetailVO>();
		String serviceType1=null;
		String serviceType2=null;
		try {
			providerSkillVO.setProviderId("89100");
			skillDetailVO.setRootSkillName("Home Electronics");
			skillDetailVO.setSkillName("General Television");
			skillDetailVO.setSkillCategoryName("Plasma TV");
			serviceType1="Installation";
			serviceType2="Delivery";
			serviceTypeList.add(serviceType1);
			serviceTypeList.add(serviceType2);
			skillDetailVO.setServiceType(serviceTypeList);
			skillDetailVOList.add(skillDetailVO);
			providerSkillVO.setSkill(skillDetailVOList);
			providerSkillVO=providerResourceBO.validateProviderSkills(providerSkillVO);
		} catch (BusinessServiceException e) {
			LOGGER.error("Exception in Junit execution for Provider Skill"+ e.getMessage());
			e.printStackTrace();
		}
		Assert.assertNull(providerSkillVO.getResults());
	}
	
	//To test availability
		 @Test
		public void validateAvailability() throws Exception{
			ProviderAccountVO providerAccountVO=new ProviderAccountVO();
			providerAccountVO.setDuplicateWeekPresent(false);
			providerAccountVO.setSun24Ind("0");
			providerAccountVO.setSunNaInd("0");
			providerAccountVO.setSunStart(null);
			providerAccountVO.setSunEnd(null);

			providerAccountVO=providerResourceBO.validateUpdateProviderDetails(providerAccountVO);
			Assert.assertNotNull(providerAccountVO.getResults().getError());
			
		}
		
		 //To test secondary contact
		@Test
		public void validateSecondaryContact() throws Exception{
			ProviderAccountVO providerAccountVO=new ProviderAccountVO();
			providerAccountVO.setSecondaryContact(PublicAPIConstant.ALTERNATE_CONTACT);
			providerAccountVO.setAltEmail(null);
				
			providerAccountVO=providerResourceBO.validateUpdateProviderDetails(providerAccountVO);
			Assert.assertNotNull(providerAccountVO.getResults().getError());
				
		}
}
