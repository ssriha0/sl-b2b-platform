/*
 * ResourceSkillAssignAction.java    1.0     2007/05/17
 */
package com.newco.marketplace.web.action.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.audit.AuditUserProfileVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.provider.CheckedSkillsVO;
import com.newco.marketplace.vo.provider.ServiceTypesVO;
import com.newco.marketplace.vo.provider.SkillAssignRequest;
import com.newco.marketplace.vo.provider.SkillAssignResponse;
import com.newco.marketplace.vo.provider.SkillCompletionVO;
import com.newco.marketplace.vo.provider.SkillNodeVO;
import com.newco.marketplace.vo.provider.SkillServiceTypeVO;
import com.newco.marketplace.web.delegates.provider.IAuditLogDelegate;
import com.newco.marketplace.web.delegates.provider.ISkillAssignDelegate;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.ResourceSkillAssignDto;
import com.newco.marketplace.web.utils.ActivityCheckList;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;


/**
 * Struts Action class for ResourceSkillAssignAction
 * 
 * @version 
 * @author  Covansys
 *
 */
public class ResourceSkillAssignAction extends ActionSupport implements SessionAware, ServletRequestAware {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ResourceSkillAssignAction.class.getName());
    private ResourceSkillAssignDto skillAssignDto;
    private ISkillAssignDelegate iSkillAssignDelegate;
	private IAuditLogDelegate auditLogDelegates;
    private Map session;
    private Map sSessionMap;
    private HttpServletRequest request;
    
    public ResourceSkillAssignAction(ResourceSkillAssignDto skillAssignDto,
    		ISkillAssignDelegate iSkillAssignDelegate){
    	this.skillAssignDto  = skillAssignDto;
    	this.iSkillAssignDelegate = iSkillAssignDelegate;
    }
    
	@Override
	public void validate() {
		super.validate();
		if (hasFieldErrors()) {
			BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext().getSession().get("tabDto");
			baseTabDto.setDtObject(skillAssignDto);
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_SKILLS,"errorOn");
		}
		
	}
	
	
	
	public String doInput() throws Exception {
		logger.info("Resource Skill Assign.doInput()");
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		// getting messages from session
		getSessionMessages();
		if (baseTabDto!= null) {
		
			
			if (baseTabDto.getDtObject()!=null)
				skillAssignDto = (ResourceSkillAssignDto)baseTabDto.getDtObject();
			
			baseTabDto.setDtObject(null);
		}
		//add for header
			String resourceName=(String)ActionContext.getContext().getSession().get("resourceName");
			logger.info("skillAssignDto resourceName : "+resourceName);
			skillAssignDto.setFullResoueceName(resourceName);
		return SUCCESS;
	}
	
	/***
	 *  Assign Skills & Service Types for resource
	 * @return
	 * @throws Exception
	 */
	public String assignSkills() throws Exception{
		logger.info("ResourceSkillAssignAction.assignSkills() method starts");
		Map paramMap = request.getParameterMap();
		sSessionMap = ActionContext.getContext().getSession();
		ResourceSkillAssignDto sSkillAssignDto = (ResourceSkillAssignDto) ActionContext.getContext()
		.getSession().get("skillDto");
		setSkillAssignDto(sSkillAssignDto);
        SkillAssignRequest skillAssignRequest = new SkillAssignRequest();
        Long resourceId = (Long) skillAssignDto.getResourceId();
        Long vendorId = 0L;
        String tempvendorId = (String)ActionContext.getContext().getSession().get("vendorId");
        vendorId=new Long(tempvendorId);
        
        ArrayList<SkillServiceTypeVO> skillServiceArrayChecked = new ArrayList<SkillServiceTypeVO>();
        ArrayList<SkillServiceTypeVO> skillServiceArrayToDelete = new ArrayList<SkillServiceTypeVO>();
    	ArrayList<SkillServiceTypeVO> skillServiceArrayToAssign = new ArrayList<SkillServiceTypeVO>();
    	ActivityCheckList acl = new ActivityCheckList();
    	boolean serviceSelection = false;
    	boolean isSkillSelectionComplete = false;
        
    	int m=0;
        
        //retrieve check box values from each check boxes from the page.
        for (Object objKey : paramMap.keySet()) {
        	String[] values = (String[])paramMap.get(objKey);
        	String key = (String)objKey;

        	// This assumes the naming scheme for checkboxes is chk_###_###
        	if (key.toLowerCase().startsWith("chk_")) {
	        	if ("on".equalsIgnoreCase(values[0])) {
	        		// Checkbox is selected
	        		serviceSelection = true;
	        		StringTokenizer st = new StringTokenizer(key, "_");
	        		st.nextToken();
	        		int nodeId = Integer.parseInt(st.nextToken().trim());
	        		int servTypeId = Integer.parseInt(st.nextToken().trim());
	        		SkillServiceTypeVO skillServiceTypes = new SkillServiceTypeVO();
	        		skillServiceTypes.setSkillNodeId(nodeId);
	        		skillServiceTypes.setServiceTypeId(servTypeId);
	        		skillServiceArrayChecked.add(skillServiceTypes);
	        	} else {
	        		// Checkbox is not selected (form should never reach here)
	        		logger.debug("Should not have reached this code.  In ResourceSkillAssignGeneralAction, checkbox values other than 'on'");
	        	}
        	}
        }
        separateInsertAndDelete(skillServiceArrayChecked, skillServiceArrayToDelete, 
    			skillServiceArrayToAssign);
        
        //No service types selected. return to same page with error message 
        if(!serviceSelection){
        	//set error message
        	addFieldError("resourceSkillAssign","Please select one classification for the ServiceType");
			BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
			.getSession().get("tabDto");
			
			//reset service types check boxes for all skill sub categories
			resetAvailableSkills(skillAssignDto.getSkillTreeList());
			
			//reset all look up service type check boxes 
			resetAvailableServiceTypes(skillAssignDto.getServiceTypes());

			//save skillAssignDto
			baseTabDto.setDtObject(skillAssignDto);
			
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			//set the tab status to error
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_SKILLS,"errorOn");
			logger.info("SkillAssignGeneralAction.saveSkills() method ends");
			return INPUT;
        
        }
       
        // separate into 2 buckets - for insert and for delete
        // this will compare what is checked in the db vs. what is now checked and separate accordingly
       
        sSkillAssignDto.setSkillServiceList(skillServiceArrayToAssign);
        sSkillAssignDto.setSkillServiceListToDelete(skillServiceArrayToDelete);
        sSkillAssignDto.setRootNodeId(getRootId().intValue());
        
        //Save the selected service types into database 
        try{
               	iSkillAssignDelegate.assignSkills(sSkillAssignDto);
               	auditUserProfileLog(sSkillAssignDto);
        }catch (BusinessServiceException bse){
        	logger.debug("[BusinessServiceException] method: iSkillAssignDelegate.assignSkills(sSkillAssignDto)"+bse);
        }
        
        SkillCompletionVO skillComplete = new SkillCompletionVO();
        skillComplete.setResourceId(skillAssignDto.getResourceId());
        //reset session variables for this page
        //if forward, add one to currentGrid - if getKey = null, forward to next guy
        Integer currentKey = (Integer)ActionContext.getContext().getSession().get("currentGrid");
        Map<Integer, Integer> allNodes = (Map)getSSessionMap().get("skillsNodes");
        try {
        	isSkillSelectionComplete = iSkillAssignDelegate.getSkillsComplete(skillComplete);
        	if(currentKey == allNodes.size()){
        		if(request.getParameter("type").equalsIgnoreCase("stay")){
        			BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
					.getSession().get("tabDto");
        			addActionMessage(getText("skillassign.success.save"));
        			baseTabDto.getActionMessages().addAll(getActionMessages());
        		}
        		
        		iSkillAssignDelegate.updateSkillsActivity(sSkillAssignDto);
        		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
        			.getSession().get("tabDto");
        		baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_SKILLS,"complete");
        	}
        	else{
        		
        			 BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
        				.getSession().get("tabDto");
        		        baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_SKILLS,ActivityRegistryConstants.INCOMPLETE);
        		
        	}
        }catch(BusinessServiceException bse){
        	BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
			.getSession().get("tabDto");
    		baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_SKILLS,"errorOn");
        	logger.debug("[BusinessServiceException] " + "RESOURCE_SKILLS - ErrorOn");
        }
       
        int newKey = 0;
        if(request.getParameter("type").equalsIgnoreCase("forward")){
        	newKey = currentKey.intValue() + 1;
        }else if(request.getParameter("type").equalsIgnoreCase("back")){
        	newKey = currentKey.intValue() - 1;
        }else if(request.getParameter("type").equalsIgnoreCase("stay")){
        	newKey = currentKey.intValue();
        }
        
        sSessionMap.put("currentGrid", new Integer(newKey)); 
        // If the new key is 0 then we need to load general informaion screen
        if (newKey == 0){
        	return "backToGeneralSkills";
        }
       
        //If you are running short of new key which means you don't have anything to process
        // the Forward the request to next Tab
        if (newKey > allNodes.size()){
        	return "next";
        }
        logger.info("ResourceSkillAssignAction.assignSkills() method ends");
		return "nextSkill";
	}
	
	private void auditUserProfileLog(ResourceSkillAssignDto sSkillAssignDto)
	{
		XStream xstream = new XStream();
		Class[] classes = new Class[] {ResourceSkillAssignDto.class}; 
		xstream.processAnnotations(classes);
		String xmlContent = xstream.toXML(sSkillAssignDto);
		AuditUserProfileVO auditUserProfileVO = new AuditUserProfileVO();
		auditUserProfileVO.setActionPerformed("PROVIDER_USER_PROFILE_EDIT");
		SecurityContext securityContext = (SecurityContext) ActionContext.getContext().getSession().get("SecurityContext");
		if(securityContext!=null)
		{
			auditUserProfileVO.setLoginCompanyId(securityContext.getCompanyId());
			auditUserProfileVO.setLoginResourceId(securityContext.getVendBuyerResId());
			auditUserProfileVO.setRoleId(securityContext.getRoleId());
			if(securityContext.isSlAdminInd())
				auditUserProfileVO.setIsSLAdminInd(1);
			auditUserProfileVO.setModifiedBy(securityContext.getUsername());
			auditUserProfileVO.setUserProfileData(xmlContent);
			auditLogDelegates.auditUserProfile(auditUserProfileVO);
		}
	}

	/***
	 * Get Service Types for selected Skill 
	 * @return
	 * @throws Exception
	 */
	public String getSkills() throws Exception {
		
		logger.info("ResourceSkillAssignAction.getSkills() method starts");
		getSessionMessages();
		//Get SkillAssignDto from Session.
		ResourceSkillAssignDto sSkillAssignDto = (ResourceSkillAssignDto) ActionContext.getContext()
		.getSession().get("skillDto");
		setSkillAssignDto(sSkillAssignDto);
		SkillAssignRequest skillRequest = new SkillAssignRequest();
		Integer currentKey  =(Integer)ActionContext.getContext().getSession().get("currentGrid");
		Map<Integer, Integer> allNodes = (Map)ActionContext.getContext().getSession().get("skillsNodes");

		if(currentKey == null || currentKey <= 0){
			return "backToGeneralSkills";
		}
	
		Integer nodeId = getRootId();
		if (nodeId != null){
			skillAssignDto.setNodeId(nodeId.intValue());
		}
		SkillAssignResponse skillResponse = new SkillAssignResponse();
		ArrayList<SkillNodeVO> arrayResponse= new ArrayList<SkillNodeVO>();
		skillAssignDto.setLastServicePage(false);
		if(currentKey == allNodes.size())
			skillAssignDto.setLastServicePage(true);
		try {
			skillAssignDto = iSkillAssignDelegate.getSkills(skillAssignDto);
			if (skillResponse.getErrorId() == 0) {
				arrayResponse = skillAssignDto.getSkillTreeList();
			} else {
			logger.error("Error in ResourceSkillAssignAction.  Not able to get skills from SkillAssignBusinessBean.");
			}
		} catch (Exception e) {
			logger.error("Error in ResourceSkillAssignAction.  Not able to get skills from SkillAssignBusinessBean. " + e);
			throw e;
		}

		Long resourceIdLng = skillAssignDto.getResourceId();
		long resourceId = resourceIdLng.longValue();
		
		// get the checked node ids/serviceTypes for my resource
		ArrayList<CheckedSkillsVO> checkedSkills = new ArrayList<CheckedSkillsVO>();
		checkedSkills = getCheckedSkills(resourceId, nodeId.intValue());
		alignAvailableSkills(skillAssignDto.getSkillTreeList(), checkedSkills);
		checkCheckAll(skillAssignDto.getSkillTreeList(), skillAssignDto.getServiceTypes());
		logger.info("ResourceSkillAssignAction.getSkills() method ends");
		return "success";
	}
	
	private void checkCheckAll(ArrayList<SkillNodeVO> skillNodes, 
							ArrayList<ServiceTypesVO> serviceType) throws Exception {
		logger.info("ResourceSkillAssignAction.checkCheckAll() method starts");
		boolean checkFlag = true;
		if (skillNodes == null || skillNodes.size() <= 0
		||	serviceType == null || serviceType.size() <=0)
			return;
		
		int loopSize = serviceType.size();
		if (loopSize <= 0)
			return;
		
		for (int loop1 = 0; loop1 < loopSize; loop1++) {
			checkFlag = true;
			for (int i = 0; i < skillNodes.size(); i++) {
				SkillNodeVO skill = (SkillNodeVO)skillNodes.get(i);
			
				ArrayList<ServiceTypesVO> serviceTypes = skill.getServiceTypes();
				if (serviceTypes != null && serviceTypes.size() > 0)
				{	
					ServiceTypesVO serviceTypesVO = serviceTypes.get(loop1);
					if (serviceTypesVO == null)
						continue;
					
					if (!serviceTypesVO.isActive())
					{
						checkFlag = false;
						break;
					}
				}
			}
			
			if (checkFlag)
			{
				ServiceTypesVO serTypeVO = serviceType.get(loop1);
				if (serTypeVO != null)
					serTypeVO.setActive(true);
			}
		}
		
		logger.info("ResourceSkillAssignAction.checkCheckAll() method ends");
	}
	
	@SkipValidation
	public String doCancel() throws Exception {
		logger.info("ResourceSkillAssignAction.doCancel() method called");
		return "cancel";
	}
	
	
	/**
	 * GetRootId for session 
	 * @return
	 */
	private Integer getRootId(){
		logger.info("ResourceSkillAssignAction.getRootId() method starts");
		Integer currentKey  =(Integer)ActionContext.getContext().getSession().get("currentGrid");
		Map allSelectionsNodes =(Map)ActionContext.getContext().getSession().get("skillsNodes");
		logger.info("ResourceSkillAssignAction.getRootId() method ends");
		return (Integer)allSelectionsNodes.get(currentKey);
	}
	
	/**
	 * 
	 * @param resourceId
	 * @param rootNodeId
	 * @return checkedSkills
	 * @throws Exception
	 */
	private ArrayList<CheckedSkillsVO> getCheckedSkills(long resourceId, int rootNodeId) throws Exception{
		logger.info("ResourceSkillAssignAction.getCheckedSkills() method starts");
		ArrayList<CheckedSkillsVO> checkedSkills = null;
		CheckedSkillsVO checkedSkillsVO = new CheckedSkillsVO();
		checkedSkillsVO.setResourceId(resourceId);
		checkedSkillsVO.setRootNodeId(rootNodeId);
		try {
			checkedSkills = iSkillAssignDelegate.getCheckedSkills(checkedSkillsVO);
		} catch (Exception e) {
			logger.error("Error in ResourceSkillAssignGeneralAction.  Not able to get checked skills from SkillAssignBusinessBean." + e);
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
	private void alignAvailableSkills(ArrayList<SkillNodeVO> skillNodes, ArrayList<CheckedSkillsVO> checked) {
		logger.info("ResourceSkillAssignAction.alignAvailableSkills() method starts");
		for (int i = 0; i < skillNodes.size(); i++) {
			SkillNodeVO skill = (SkillNodeVO)skillNodes.get(i);
			skill.setActive(false);
			for (int j = 0; j < checked.size(); j++) {
				CheckedSkillsVO chkd = (CheckedSkillsVO)checked.get(j);
				if(skill.getNodeId() == chkd.getNodeId()) {
					skill.setActive(true);
					ArrayList<ServiceTypesVO> serviceTypes = skill.getServiceTypes();
					for(int k = 0; k < serviceTypes.size(); k++) {
						ServiceTypesVO servType = serviceTypes.get(k);
						if(servType.getServiceTypeId() == chkd.getServiceTypeId()){
							servType.setActive(true);
						}
					}
				}
			}
		}
		logger.info("ResourceSkillAssignAction.alignAvailableSkills() method ends");
	}
	/**
	 * This method is used to reset all the Service Types for each skill category check boxes to false 
	 * @param serviceTypes
	 */
	private void resetAvailableSkills(ArrayList<SkillNodeVO> skillNodes) {
		logger.info("ResourceSkillAssignAction.resetAvailableSkills() method starts");
		for (int i = 0; i < skillNodes.size(); i++) {
			SkillNodeVO skill = (SkillNodeVO)skillNodes.get(i);
			skill.setActive(false);
			ArrayList<ServiceTypesVO> serviceTypes = skill.getServiceTypes();
			for(int k = 0; k < serviceTypes.size(); k++) {
				ServiceTypesVO servType = serviceTypes.get(k);
				servType.setActive(false);
			}
		}
		logger.info("ResourceSkillAssignAction.resetAvailableSkills() method ends");
	}
	
	/**
	 * This method is used to reset all the Service Type check boxes to false 
	 * @param serviceTypes
	 */
	private void resetAvailableServiceTypes(ArrayList<ServiceTypesVO> serviceTypes) {
		for (int i = 0; i < serviceTypes.size(); i++) {
			ServiceTypesVO serTypes = serviceTypes.get(i);
			serTypes.setActive(false);
		}
	}
	/**
	 * 
	 * @param newlyChecked
	 * @param toDelete
	 * @param toAssign
	 * @throws Exception
	 */
	private void separateInsertAndDelete(
			ArrayList<SkillServiceTypeVO> newlyChecked,
			ArrayList<SkillServiceTypeVO> toDelete,
			ArrayList<SkillServiceTypeVO> toAssign) throws Exception {
		logger.info("ResourceSkillAssignAction.separateInsertAndDelete() method starts");
		
		// get the old checked guys:
		ArrayList<CheckedSkillsVO> oldCheckedSkills = new ArrayList<CheckedSkillsVO>();
		oldCheckedSkills = getCheckedSkills(skillAssignDto.getResourceId(),getRootId());
		// will fill the toDelete bucket with all the nodes that are in old, but
		// not in new
		
		if(oldCheckedSkills!=null){
			logger.info("separateInsertAndDelete method : Old CheckedSkills "+oldCheckedSkills.size());
		}
		for (int i = 0; i < oldCheckedSkills.size(); i++) {
			CheckedSkillsVO oldChecked = (CheckedSkillsVO) oldCheckedSkills.get(i);
			boolean inNew = false;
			for (int j = 0; j < newlyChecked.size(); j++) {
				// if in old, but not in new, put in delete bucket
				SkillServiceTypeVO newChecked = (SkillServiceTypeVO) newlyChecked.get(j);
				if (oldChecked.getNodeId() == newChecked.getSkillNodeId() 
						&& oldChecked.getServiceTypeId() == newChecked.getServiceTypeId()) {
					inNew = true;
				}
			}
			if (!inNew) {
				SkillServiceTypeVO skillServType = new SkillServiceTypeVO();
				skillServType.setSkillNodeId(oldChecked.getNodeId());
				skillServType.setServiceTypeId(oldChecked.getServiceTypeId());
				toDelete.add(skillServType);
			}
		}

		// will fill the toAssign bucket with all the nodes that are in new, but
		// not in old
		for (int i = 0; i < newlyChecked.size(); i++) {
			SkillServiceTypeVO newChecked = (SkillServiceTypeVO) newlyChecked.get(i);
			boolean inOld = false;
			for (int j = 0; j < oldCheckedSkills.size(); j++) {
				// if in new, but is not in old, put in assign bucket
				CheckedSkillsVO oldChecked = (CheckedSkillsVO) oldCheckedSkills.get(j);
				if (oldChecked.getNodeId() == newChecked.getSkillNodeId() 
						&& oldChecked.getServiceTypeId() == newChecked.getServiceTypeId()) {
					inOld = true;
				}
			}
			if (!inOld) {
				toAssign.add(newChecked);
			}
		}
		
		if(toDelete!=null){
			logger.info("separateInsertAndDelete method : toDelete size"+toDelete.size());
		}
		
		if(toAssign!=null){
			logger.info("separateInsertAndDelete method : toAssign size"+toAssign.size());
		}
		
	  logger.info("ResourceSkillAssignAction.separateInsertAndDelete() method ends");
	} 
	
	public String doNext() throws Exception {
		logger.info("ResourceSkillAssignAction.doNext() method starts");
		return "next";
	}
	
	public String doPrev() throws Exception {
		logger.info("ResourceSkillAssignAction.doPrev() method starts");
		return "prev";
	}

	public ResourceSkillAssignDto getSkillAssignDto() {
		return skillAssignDto;
	}
	public void setSkillAssignDto(ResourceSkillAssignDto skillAssignDto) {
		this.skillAssignDto = skillAssignDto;
	}
	public ISkillAssignDelegate getISkillAssignDelegate() {
		return iSkillAssignDelegate;
	}
	public void setISkillAssignDelegate(ISkillAssignDelegate skillAssignDelegate) {
		iSkillAssignDelegate = skillAssignDelegate;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public Map getSSessionMap() {
		return sSessionMap;
	}
	public void setSSessionMap(Map sessionMap) {
		sSessionMap = sessionMap;
	}
	public Map getSession() {
		return session;
	}
	public void setSession(Map session) {
		this.session = session;
	}
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		request=arg0;
		
	}
	
	private void getSessionMessages() {
		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
				.getSession().get("tabDto");
		// getting messages from session
		if (baseTabDto != null) {
			setFieldErrors(baseTabDto.getFieldsError());
			baseTabDto.setFieldsError(new HashMap());
			setActionMessages(baseTabDto.getActionMessages());
			baseTabDto.setActionMessages(new ArrayList<String>());
		}
	}

	public IAuditLogDelegate getAuditLogDelegates() {
		return auditLogDelegates;
	}

	public void setAuditLogDelegates(IAuditLogDelegate auditLogDelegates) {
		this.auditLogDelegates = auditLogDelegates;
	}
}