package com.newco.marketplace.web.action.provider;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.provider.CheckedSkillsVO;
import com.newco.marketplace.vo.provider.ServiceTypesVO;
import com.newco.marketplace.vo.provider.SkillAssignResponse;
import com.newco.marketplace.vo.provider.SkillNodeVO;
import com.newco.marketplace.web.delegates.provider.IPublicProfileDelegate;
import com.newco.marketplace.web.delegates.provider.ISkillAssignDelegate;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.PublicProfileDto;
import com.newco.marketplace.web.dto.provider.ResourceSkillAssignDto;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PublicProfileAction extends ActionSupport implements SessionAware,
		ServletRequestAware {
	private IPublicProfileDelegate publicProfileDelegate;
	private PublicProfileDto publicProfileDto;
	private Map sSessionMap;
	private HttpSession session;
	private HttpServletRequest request;
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(PublicProfileAction.class.getName());
	private SecurityContext securityContext;
	private String resourceId;
	private BaseTabDto tabDto;

	// variables for skill tree lists
	private ResourceSkillAssignDto skillAssignDto;
	private ISkillAssignDelegate iSkillAssignDelegate;
	// Node Id which should be passed thru hidden Parameter
	private Integer nodeId;

	public BaseTabDto getTabDto() {
		return tabDto;
	}

	public void setTabDto(BaseTabDto tabDto) {
		this.tabDto = tabDto;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public PublicProfileAction(IPublicProfileDelegate publicProfileDelegate,
			PublicProfileDto publicProfileDto,ISkillAssignDelegate iSkillAssignDelegate,ResourceSkillAssignDto skillAssignDto) {
		this.publicProfileDelegate = publicProfileDelegate;
		this.publicProfileDto = publicProfileDto;
		this.iSkillAssignDelegate=iSkillAssignDelegate;
		this.skillAssignDto=skillAssignDto;
		
	}

	public String doLoad() throws Exception {
		try {
			// ActionContext.getContext().getSession().put("resourceId",
			// resourceId);
			ActionContext.getContext().getSession().put("tabDto", tabDto);

			String vendorId = null;
			String resourceId;
			setContextDetails();
			
			if(securityContext == null){
				if(request.getSession().getAttribute("findProvider_companyId") != null) {
					//grab the  company id from the session.. if its not available in the session either.. you screwed..
					vendorId = "1";
					request.getSession().removeAttribute("findProvider_companyId");
				}
			}
			else {
				vendorId = securityContext.getCompanyId() + "";
			}
			resourceId = ActionContext.getContext().getSession().get("resourceId")	+ "";
			
			if (vendorId != null) {
				publicProfileDto.setVendorId(Integer.parseInt(vendorId.trim()));
			}
			logger.info("resourceid length-------------" + resourceId.trim());
			if (resourceId != null && resourceId.trim().length() > 0) {
				publicProfileDto.setResourceId(Integer.parseInt(resourceId));
			}
			publicProfileDto = publicProfileDelegate
					.getPublicProfile(publicProfileDto);
			
			
			if(securityContext == null) {
				publicProfileDto.setRole( OrderConstants.BUYER_ROLEID + "");
			}
			else {
			//Added Fix for Sears00046670. -Starts
			//-Ganapathy
			if(securityContext.getRole() != null)	publicProfileDto.setRole(securityContext.getRole());
			}
			//Added Fix for Sears00046670..- Ends
			logger.info("inside action ####################"
					+ publicProfileDto.getCompanySize());
			ArrayList temp= (ArrayList)publicProfileDto.getResourceSkillList();
			nodeId=(Integer)ActionContext.getContext().getSession().get("selectedNodeId");
			if(nodeId==null){
				if(temp!=null){
					int size=temp.size();
					for(int i=0;i<size;i++){
						SkillNodeVO skillNodeVO=(SkillNodeVO)temp.get(i);
						nodeId=skillNodeVO.getNodeId();
						break;
					}//for loop
				}//temp null check
			}//node id null check
			if(nodeId != null && nodeId != 0)
			getSkills(resourceId);
			else{
				ArrayList tempList = new ArrayList();
				skillAssignDto.setSkillTreeList(tempList);
				skillAssignDto.setServiceTypes(tempList);
			}
		} catch (DelegateException ex) {
			ex.printStackTrace();
			logger
					.info("Exception Occured in loadUsers() of ManageUsersAction while processing the request due to"
							+ ex.getMessage());
			addActionError("Exception Occured loadUsers() of ManageUsersAction while processing the request due to"
					+ ex.getMessage());
			return ERROR;
		}
		return "load";
	}
	
	public String loadSkills() throws Exception {
		doLoad();
		return "load";
	}
	

	private void setContextDetails() {
		session = ServletActionContext.getRequest().getSession();
		securityContext = (SecurityContext) session
				.getAttribute("SecurityContext");
	}

	public void setSession(Map ssessionMap) {
		// TODO Auto-generated method stub
		this.sSessionMap = ssessionMap;
	}

	public Map getSession() {
		// TODO Auto-generated method stub
		return this.sSessionMap;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		request = arg0;
	}

	public PublicProfileDto getPublicProfileDto() {
		return publicProfileDto;
	}

	public void setPublicProfileDto(PublicProfileDto publicProfileDto) {
		this.publicProfileDto = publicProfileDto;
	}

	public String getSkills(String resourceid) throws Exception {
		logger
				.info("----------getSkills() in PublicProfileAction  method starts-------");
		// Get SkillAssignDto from Session. 
		skillAssignDto.setResourceId(new Long(resourceid));
		if (nodeId != null) {
			skillAssignDto.setNodeId(nodeId.intValue());
		}
		SkillAssignResponse skillResponse = new SkillAssignResponse();
		ArrayList<SkillNodeVO> arrayResponse = new ArrayList<SkillNodeVO>();
		try {
			skillAssignDto = iSkillAssignDelegate.getSkills(skillAssignDto);
			if (skillResponse.getErrorId() == 0) {
				arrayResponse = skillAssignDto.getSkillTreeList();
			}
		} catch (Exception e) {
			System.out
					.println("Error in ResourceSkillAssignAction.  Not able to get skills from SkillAssignBusinessBean. "
							+ e);
			e.printStackTrace();
			throw e;
		}
		Long resourceIdLng = skillAssignDto.getResourceId();
		long resourceId = resourceIdLng.longValue();
		// get the checked node ids/serviceTypes for my resource
		ArrayList<CheckedSkillsVO> checkedSkills = new ArrayList<CheckedSkillsVO>();
		checkedSkills = getCheckedSkills(resourceId, nodeId.intValue());
		alignAvailableSkills(skillAssignDto.getSkillTreeList(), checkedSkills);
		checkCheckAll(skillAssignDto.getSkillTreeList(), skillAssignDto
				.getServiceTypes());
		logger.info("ResourceSkillAssignAction.getSkills() method ends");
		return "success";
	}

	/**
	 * 
	 * @param resourceId
	 * @param rootNodeId
	 * @return checkedSkills
	 * @throws Exception
	 */
	private ArrayList<CheckedSkillsVO> getCheckedSkills(long resourceId,
			int rootNodeId) throws Exception {
		logger
				.info("ResourceSkillAssignAction.getCheckedSkills() method starts");
		ArrayList<CheckedSkillsVO> checkedSkills = null;
		CheckedSkillsVO checkedSkillsVO = new CheckedSkillsVO();
		checkedSkillsVO.setResourceId(resourceId);
		checkedSkillsVO.setRootNodeId(rootNodeId);
		try {
			checkedSkills = iSkillAssignDelegate
					.getCheckedSkills(checkedSkillsVO);
		} catch (Exception e) {
			logger
					.error("Error in ResourceSkillAssignGeneralAction.  Not able to get checked skills from SkillAssignBusinessBean."
							+ e);
			throw e;
		}
		logger.info("ResourceSkillAssignAction.getCheckedSkills() method ends");
		return checkedSkills;
	}

	/**
	 * 
	 * @param skillNodes
	 * @param checked
	 */
	private void alignAvailableSkills(ArrayList<SkillNodeVO> skillNodes,
			ArrayList<CheckedSkillsVO> checked) {
		logger
				.info("ResourceSkillAssignAction.alignAvailableSkills() method starts");
		for (int i = 0; i < skillNodes.size(); i++) {
			SkillNodeVO skill = (SkillNodeVO) skillNodes.get(i);
			skill.setActive(false);
			for (int j = 0; j < checked.size(); j++) {
				CheckedSkillsVO chkd = (CheckedSkillsVO) checked.get(j);
				if (skill.getNodeId() == chkd.getNodeId()) {
					skill.setActive(true);
					ArrayList<ServiceTypesVO> serviceTypes = skill
							.getServiceTypes();
					for (int k = 0; k < serviceTypes.size(); k++) {
						ServiceTypesVO servType = serviceTypes.get(k);
						if (servType.getServiceTypeId() == chkd
								.getServiceTypeId()) {
							servType.setActive(true);
						}
					}
				}
			}
		}
		logger
				.info("ResourceSkillAssignAction.alignAvailableSkills() method ends");
	}

	private void checkCheckAll(ArrayList<SkillNodeVO> skillNodes,
			ArrayList<ServiceTypesVO> serviceType) throws Exception {
		logger.info("ResourceSkillAssignAction.checkCheckAll() method starts");
		boolean checkFlag = true;
		if (skillNodes == null || skillNodes.size() <= 0 || serviceType == null
				|| serviceType.size() <= 0)
			return;

		int loopSize = serviceType.size();
		if (loopSize <= 0)
			return;

		for (int loop1 = 0; loop1 < loopSize; loop1++) {
			checkFlag = true;
			for (int i = 0; i < skillNodes.size(); i++) {
				SkillNodeVO skill = (SkillNodeVO) skillNodes.get(i);

				ArrayList<ServiceTypesVO> serviceTypes = skill
						.getServiceTypes();
				if (serviceTypes != null && serviceTypes.size() > 0) {
					ServiceTypesVO serviceTypesVO = serviceTypes.get(loop1);
					if (serviceTypesVO == null)
						continue;

					if (!serviceTypesVO.isActive()) {
						checkFlag = false;
						break;
					}
				}
			}

			if (checkFlag) {
				ServiceTypesVO serTypeVO = serviceType.get(loop1);
				if (serTypeVO != null)
					serTypeVO.setActive(true);
			}
		}

		logger.info("ResourceSkillAssignAction.checkCheckAll() method ends");
	}

	public ResourceSkillAssignDto getSkillAssignDto() {
		return skillAssignDto;
	}

	public void setSkillAssignDto(ResourceSkillAssignDto skillAssignDto) {
		this.skillAssignDto = skillAssignDto;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

}