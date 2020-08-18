package com.newco.marketplace.api.mobile.services.v3_1;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.getTeamMembers.ViewTeamMembersResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_1.NewMobileGenericMapper;
import com.newco.marketplace.business.iBusiness.mobile.INewMobileGenericBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.mobile.api.utils.validators.v3_1.NewMobileGenericValidator;
import com.newco.marketplace.vo.provider.TeamMemberDocumentVO;
import com.newco.marketplace.vo.provider.TeamMemberVO;

@APIResponseClass(ViewTeamMembersResponse.class)
public class ViewTeamMembersService extends BaseService {

private static final Logger LOGGER = Logger.getLogger(ViewTeamMembersService.class);
    
	private INewMobileGenericBO newMobileBO;
	private NewMobileGenericMapper newMobileMapper;
	private NewMobileGenericValidator mobileValidator;
	
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		List <TeamMemberVO> teamMemberVOList=null;
		List <TeamMemberDocumentVO> teamMemberDocumentIdList=null;
		ViewTeamMembersResponse viewTeamMembersResponse =new ViewTeamMembersResponse();
		
		
		try{
				viewTeamMembersResponse=mobileValidator.validateTeamMember(apiVO,viewTeamMembersResponse);
				if(viewTeamMembersResponse.getResults()==null){
					//fetching the team members List
					teamMemberVOList=newMobileBO.getTeamMemberList(apiVO.getProviderId());
					//fetching the documentIdList 
					teamMemberDocumentIdList=getDocumentIdList(teamMemberVOList);
					//setting the lists into the team member response object
					viewTeamMembersResponse=newMobileMapper.mapTeamMemberVOToResponse(teamMemberVOList,teamMemberDocumentIdList);
					Results results = Results.getSuccess(ResultsCode.GET_TEAM_MEMBERS_SUCCESS.getCode(),ResultsCode.GET_TEAM_MEMBERS_SUCCESS.getMessage());
					viewTeamMembersResponse.setResults(results);
				}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("Exception Occured in ViewTeamMembersService-->execute()--> Exception-->", ex);
			Results results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			viewTeamMembersResponse.setResults(results);
		}	
		return viewTeamMembersResponse;
	}
	
	/**
	 * @Description : Method to fetch the documentId list 
	 * @param teamMemberVOList
	 * @return List<TeamMemberDocumentVO>
	 */
	private List<TeamMemberDocumentVO> getDocumentIdList(List<TeamMemberVO> teamMemberVOList) {
		List<Integer> resourceIdList =null;
		List <TeamMemberDocumentVO> teamMemberDocumentIdList=null;
		//fetching the resourceIdList to fetch the corresponding document Id by passing the team member list
		resourceIdList = getResourceIdList(teamMemberVOList);
		if(null != resourceIdList && ! resourceIdList.isEmpty() ){
			try {
				teamMemberDocumentIdList=newMobileBO.getDocumentIdList(resourceIdList);
			} catch (BusinessServiceException e) {
				e.printStackTrace();
			}
		}
		return teamMemberDocumentIdList;
	}

	/**
	 * @Description : Method to get the resourceIdList to fetch the corresponding document Id, from the team member list
	 * @param teamMemberVOList
	 * @return List<Integer>
	 */
	private List<Integer> getResourceIdList(List <TeamMemberVO> teamMemberVOList){
		
		List<Integer> resourceIdList=null;
		if(null != teamMemberVOList && !teamMemberVOList.isEmpty()){
			resourceIdList = new ArrayList<Integer>();
			for(TeamMemberVO teamMemberVO:teamMemberVOList){
				resourceIdList.add(teamMemberVO.getResourceId());
			}
		}
		return resourceIdList;
	}
	public INewMobileGenericBO getNewMobileBO() {
		return newMobileBO;
	}
	public void setNewMobileBO(INewMobileGenericBO newMobileBO) {
		this.newMobileBO = newMobileBO;
	}
	public NewMobileGenericMapper getNewMobileMapper() {
		return newMobileMapper;
	}
	public void setNewMobileMapper(NewMobileGenericMapper newMobileMapper) {
		this.newMobileMapper = newMobileMapper;
	}
	public NewMobileGenericValidator getMobileValidator() {
		return mobileValidator;
	}
	public void setMobileValidator(NewMobileGenericValidator mobileValidator) {
		this.mobileValidator = mobileValidator;
	}
}
