/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 25-August-2009	KMSTRSUP   Infosys				1.0
 *
 *
 */
package com.newco.marketplace.api.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.SkuDetailsVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmServiceVO;
import com.newco.marketplace.dto.vo.provider.BasicFirmDetailsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.search.types.Category;
import com.newco.marketplace.search.types.CustomerFeedback;
import com.newco.marketplace.search.types.ProviderCredential;
import com.newco.marketplace.search.types.Skill;
import com.newco.marketplace.search.vo.ProviderListVO;
import com.newco.marketplace.search.vo.ProviderSearchRequestVO;
import com.newco.marketplace.search.vo.ProviderSearchResponseVO;
import com.newco.marketplace.search.vo.SkillRequestVO;
import com.newco.marketplace.search.vo.SkillTreeResponseVO;
import com.newco.marketplace.searchInterface.ISearchProvider;
/**
 * The purpose of this class is to return mock responses for the b2c
 * search services. The below services are available
 * 1. Search Provider By SkillTree
 * 2. Search  Provider By ZipCode
 * 3. Search Provider By ProfileId
 *
 */

public class SearchMockResponse implements ISearchProvider{
	private static Logger logger = Logger.getLogger(SearchMockResponse.class);
	/**
	 * This method returns a mock response for Provider By Zip Search
	 * @param req ProviderSearchRequestVO
	 * @return List<ProviderSearchResponseVO>
	 */
	public ProviderListVO getProviderList(
			ProviderSearchRequestVO req) {
		logger.info("Entering getProviderList Method");
		List<ProviderSearchResponseVO> providerSearchresponseVOList= new ArrayList<ProviderSearchResponseVO>();
		ProviderSearchResponseVO providerSearchResponseVO= new ProviderSearchResponseVO();
		providerSearchResponseVO.setName("Jack");
		providerSearchResponseVO.setMemberSince(new Date());
		providerSearchResponseVO.setDistance((float) 5.0);
		providerSearchResponseVO.setJobTitle("Test JobTitle");
		providerSearchResponseVO.setResourceId(5);
		providerSearchResponseVO.setImageUrl("Image URL");
		providerSearchResponseVO.setPrimaryIndustry("Primary");

		providerSearchResponseVO.setOverview("our Overview");
		providerSearchResponseVO.setCompanyId(22);
		providerSearchResponseVO.setBusinessStructure("My Business Structure");
		providerSearchResponseVO.setYearsInBusiness(4);
		providerSearchResponseVO.setCompanySize("300");
		providerSearchResponseVO.setNumberOfSLProjectsCompleted(3);


		providerSearchResponseVO.setLaborWarrantyDays(300);
		providerSearchResponseVO.setLaborPartsDays(600);
		providerSearchResponseVO.setOffersFreeEstimatesInd(true);
		providerSearchResponseVO.setWorkersCompInsurance(200);
		providerSearchResponseVO.setVehicalInsurance(100);
		providerSearchResponseVO.setGeneralInsurance(200);
		providerSearchResponseVO.setClosedOrderTotal(300);
		providerSearchResponseVO.setReviewCount(400);
		providerSearchResponseVO.setRatingTotal(500);
		providerSearchResponseVO.setAvgRating(400);
		providerSearchResponseVO.setAvgReviewerRating(900);
		providerSearchResponseVO.setCredentialTotal(800);
		providerSearchResponseVO.setTimelinessRatings(4);
		providerSearchResponseVO.setCommunicationRatings(5);
		providerSearchResponseVO.setProfessionalismRatings(4);
		providerSearchResponseVO.setQualityRatings(6);
		providerSearchResponseVO.setValueRatings(7);
		providerSearchResponseVO.setCleanlinessRatings(8);
		providerSearchResponseVO.setOverallRating(9);


		List<Skill> skills = new ArrayList<Skill>();
		List<Category> categoryList = new ArrayList<Category>();
		Category category= new Category();
		Skill skill= new Skill();
		skill.setLevel(1);
		skill.setName("TestName");
		SortedSet<String> typeList= new TreeSet<String>();
		typeList.add("Delivery");
		typeList.add("Installation");
		typeList.add("Repair");
		skill.setType(typeList);
		skills.add(skill);
		category.setSkillList(skills);
		category.setRoot("TestRoot");
		categoryList.add(category);
		providerSearchResponseVO.setCategories(categoryList);

		 List<ProviderCredential> credentialList= new ArrayList<ProviderCredential>();
		 ProviderCredential providerCredential= new ProviderCredential();
		 providerCredential.setCategory("our Category");
		 providerCredential.setName("CategoryNmae");
		 providerCredential.setSource("SourceName");
		 providerCredential.setType("TypeName");
		 credentialList.add(providerCredential);
		 providerSearchResponseVO.setLicenses(credentialList);

		 List<CustomerFeedback> reviews= new ArrayList<CustomerFeedback>();
		 CustomerFeedback feedback= new CustomerFeedback();
		 feedback.setComment("MyComment");
		 feedback.setRating(3);
		 feedback.setReviewDate(new Date());
		 feedback.setReviewerName("Jill");
		 reviews.add(feedback);
		 providerSearchResponseVO.setReviews(reviews);
		 providerSearchresponseVOList.add(providerSearchResponseVO);
		 logger.info("Exiting getProviderList Method");
		return new ProviderListVO(providerSearchresponseVOList, 0);
	}
	/**
	 * This method returns a mock response for Provider ById Search
	 * @param req ProviderSearchRequestVO
	 * @return List<ProviderSearchResponseVO>
	 */
	public List<ProviderSearchResponseVO> getProvidersById(
			ProviderSearchRequestVO req) {
		logger.info("Entering getProvidersById Method");
		List<ProviderSearchResponseVO> providerSearchresponseVOList= new ArrayList<ProviderSearchResponseVO>();
		ProviderSearchResponseVO providerSearchResponseVO= new ProviderSearchResponseVO();
		providerSearchResponseVO.setName("Jack");
		providerSearchResponseVO.setMemberSince(new Date());
		providerSearchResponseVO.setDistance((float) 5.0);
		providerSearchResponseVO.setJobTitle("Test JobTitle");
		providerSearchResponseVO.setResourceId(5);
		providerSearchResponseVO.setImageUrl("Image URL");
		providerSearchResponseVO.setPrimaryIndustry("Primary");

		providerSearchResponseVO.setOverview("our Overview");
		providerSearchResponseVO.setCompanyId(22);
		providerSearchResponseVO.setBusinessStructure("My Business Structure");
		providerSearchResponseVO.setYearsInBusiness(4);
		providerSearchResponseVO.setCompanySize("300");
		providerSearchResponseVO.setNumberOfSLProjectsCompleted(3);


		providerSearchResponseVO.setLaborWarrantyDays(250);
		providerSearchResponseVO.setLaborPartsDays(455);
		providerSearchResponseVO.setOffersFreeEstimatesInd(true);
		providerSearchResponseVO.setWorkersCompInsurance(200);
		providerSearchResponseVO.setVehicalInsurance(100);
		providerSearchResponseVO.setGeneralInsurance(200);
		providerSearchResponseVO.setClosedOrderTotal(300);
		providerSearchResponseVO.setReviewCount(400);
		providerSearchResponseVO.setRatingTotal(500);
		providerSearchResponseVO.setAvgRating(400);
		providerSearchResponseVO.setAvgReviewerRating(900);
		providerSearchResponseVO.setCredentialTotal(800);
		providerSearchResponseVO.setTimelinessRatings(4);
		providerSearchResponseVO.setCommunicationRatings(5);
		providerSearchResponseVO.setProfessionalismRatings(4);
		providerSearchResponseVO.setQualityRatings(6);
		providerSearchResponseVO.setValueRatings(7);
		providerSearchResponseVO.setCleanlinessRatings(8);
		providerSearchResponseVO.setOverallRating(9);


		List<Skill> skills = new ArrayList<Skill>();
		List<Category> categoryList = new ArrayList<Category>();
		Category category= new Category();
		Skill skill= new Skill();
		skill.setLevel(1);
		skill.setName("TestName");
		SortedSet<String> typeList= new TreeSet<String>();
		typeList.add("Delivery");
		typeList.add("Installation");
		typeList.add("Repair");
		skill.setType(typeList);
		skills.add(skill);
		category.setSkillList(skills);
		category.setRoot("Test root");
		categoryList.add(category);
		providerSearchResponseVO.setCategories(categoryList);

		 List<ProviderCredential> credentialList= new ArrayList<ProviderCredential>();
		 ProviderCredential providerCredential= new ProviderCredential();
		 providerCredential.setCategory("our Category");
		 providerCredential.setName("CategoryNmae");
		 providerCredential.setSource("SourceName");
		 providerCredential.setType("TypeName");
		 credentialList.add(providerCredential);
		 providerSearchResponseVO.setLicenses(credentialList);

		 List<CustomerFeedback> reviews= new ArrayList<CustomerFeedback>();
		 CustomerFeedback feedback= new CustomerFeedback();
		 feedback.setComment("MyComment");
		 feedback.setRating(5);
		 feedback.setReviewDate(new Date());
		 feedback.setReviewerName("Jill");
		 reviews.add(feedback);
		 providerSearchResponseVO.setReviews(reviews);
		 providerSearchresponseVOList.add(providerSearchResponseVO);

		 logger.info("Exiting getProvidersById Method");
		return providerSearchresponseVOList;
	}
	/**
	 * This method returns a mock response for the skill Tree Search
	 * @param vo SkillRequestVO
	 * @return List<SkillTreeResponseVO>
	 */
	public List<SkillTreeResponseVO> getSkillTree(SkillRequestVO vo) {
		logger.info("Entering getSkillTree Method");
		List<SkillTreeResponseVO> providerResponseList= new ArrayList<SkillTreeResponseVO>();
		SkillTreeResponseVO skillTreeResponse = new SkillTreeResponseVO();
		skillTreeResponse.setParentNodeId(1);
		skillTreeResponse.setParentNodeName("ParentNode1");
		skillTreeResponse.setNodeId(2);
		skillTreeResponse.setNodeName("RootNode1");
		providerResponseList.add(skillTreeResponse);
		skillTreeResponse = new SkillTreeResponseVO();
		skillTreeResponse.setParentNodeId(4);
		skillTreeResponse.setParentNodeName("ParentNode2");
		skillTreeResponse.setNodeId(5);
		skillTreeResponse.setNodeName("RootNode2");
		providerResponseList.add(skillTreeResponse);
		logger.info("Exiting getSkillTree Method");
		return providerResponseList;
	}
	
	public Map<String, Long> getCities(String state, List list) {
		// TODO Auto-generated method stub
		return null;
	}
	public int getProvidersCount(String state, String city, List list) {
		// TODO Auto-generated method stub
		return 0;
	}
	public Map<Integer, Integer> getProvidersCountBySkills(String state,
			String city) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Integer> getSkuCategoryIds(String buyerId, List<String> skuList)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	public ProviderListVO getProviders(
			ProviderSearchRequestVO providerSearchRequestVO) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Map<Integer, Long> getReviewCount(List<String> vendorIdList)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	//SL-21468 get the company Logo path
	public Map<Long, String> getCompanyLogo(List<String> firmIds) throws BusinessServiceException{
		// TODO Auto-generated method stub
		return null;
	}
	//SL-21468  get values from application_properties table 
	public Map<String, String> getCompanyLogoValues() throws BusinessServiceException{
		// TODO Auto-generated method stub
		return null;
	}
	
	//SL-21467
	public List<SkuDetailsVO> getSkusForCategoryIds(String buyerId, List<Integer> levelThreeCategories,String keyword) throws BusinessServiceException{
		return null;
	}	
	
	
	public Map<Long,Long> getParentNodeIds(List<Integer> nodeIds) throws BusinessServiceException{
		return null;
	}	

	public Map<Long,String> getSkillTypList() throws BusinessServiceException{
		return null;
	}	
	
	public Map<Long,String> getNodeNames(List<String> nodeIds) throws BusinessServiceException{
		return null;
	}	

	public List<String> getMainCategoryNames(List<Integer> categoryIdList)  throws BusinessServiceException{
		return null;
	}
	public Map<Integer,BasicFirmDetailsVO> getBasicFirmDetails(List<String> firmIds) throws BusinessServiceException{
		return null;
	}

	public Map<String,List<FirmServiceVO>> getVendorServiceDetails(List<String> firmIdList,List<Integer> categoryIdList) throws BusinessServiceException {
		return null;
	}
	

	
}
