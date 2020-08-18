package com.newco.marketplace.web.action.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.provider.CheckedSkillsVO;
import com.newco.marketplace.vo.provider.LanguageVO;
import com.newco.marketplace.vo.provider.SkillNodeVO;
import com.newco.marketplace.vo.provider.SkillServiceTypeVO;
import com.newco.marketplace.web.delegates.provider.IAuditLogDelegate;
import com.newco.marketplace.web.delegates.provider.ISkillAssignDelegate;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.ResourceSkillAssignDto;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SkillAssignGeneralAction extends ActionSupport implements SessionAware, ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7087436723554881180L;
	private static final Logger logger = Logger.getLogger(SkillAssignGeneralAction.class.getName());
	private ResourceSkillAssignDto skillAssignDto;
	private ISkillAssignDelegate iSkillAssignDelegate;
	private IAuditLogDelegate auditLogDelegates;
	private String[] checkbox;
	private Map session;
	private List vliList;
	private List wciList;
	private List cbgliList;
	private Map sSessionMap;
	private HttpServletRequest request;

	public SkillAssignGeneralAction(ResourceSkillAssignDto skillAssignDto,ISkillAssignDelegate iSkillAssignDelegate) {
		this.skillAssignDto = skillAssignDto;
		this.iSkillAssignDelegate = iSkillAssignDelegate;
	}

	// method to get the insurance types residing into the database to show to UI
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
			resetAvailableSkills(skillAssignDto.getSkillTreeList());
			baseTabDto.setDtObject(null);
		}
		//add for header
			String resourceName=(String)ActionContext.getContext().getSession().get("resourceName");
			logger.info("skillAssignDto resourceName : "+resourceName);
			skillAssignDto.setFullResoueceName(resourceName);
		return SUCCESS;
	}
	
	
	/**
	 * Load the Languages and Skills selected by the resource
	 * @return INPUT
	 */
	public String loadSkills() {
		logger.info("SkillAssignGeneralAction.loadSkills() method starts");
		getSessionMessages();
		try {
			String resourceId = (String) ActionContext.getContext().getSession().get("resourceId");
			skillAssignDto.setResourceId(new Integer(resourceId).intValue());
			skillAssignDto = iSkillAssignDelegate.getGeneralSkills(skillAssignDto);
			sSessionMap.put("skillDto", skillAssignDto);
			ActionContext.getContext().setSession(sSessionMap);
			
			// get the checked node ids for my resource
			ArrayList<CheckedSkillsVO> checkedSkills = new ArrayList<CheckedSkillsVO>();
			checkedSkills = getCheckedSkills(new Integer(resourceId).intValue());
			// assign checked nodes to appropriate nodes in SkillNodeVO(arrayResponse array list)
			if (!skillAssignDto.getSkillTreeList().isEmpty())
				alignAvailableSkills(skillAssignDto.getSkillTreeList(),checkedSkills);
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
		logger.info("SkillAssignGeneralAction.loadSkills() method ends");
		return SUCCESS;
	}

	/**
	 * Get the checked node ids for my resource
	 * @param resourceId
	 * @return
	 * @throws Exception
	 */
	private ArrayList<CheckedSkillsVO> getCheckedSkills(long resourceId) throws Exception {
		logger.info("SkillAssignGeneralAction.getCheckedSkills() method starts");
		ArrayList<CheckedSkillsVO> checkedSkills = null;
		CheckedSkillsVO checkedSkillsVO = new CheckedSkillsVO();
		checkedSkillsVO.setResourceId(resourceId);
		checkedSkillsVO.setRootNodeId(-1);
		try {
			checkedSkills = iSkillAssignDelegate.getCheckedSkills(checkedSkillsVO);
		} catch (Exception e) {
		logger.info("Error in ResourceSkillAssignGeneralAction. Not able to get checked skills from SkillAssignBusinessBean."+ e);
		throw e;
		}
		logger.info("SkillAssignGeneralAction.getCheckedSkills() method ends");
		return checkedSkills;
	}

	/**
	 * Assign checked nodes to appropriate nodes in SkillNodeVO
	 * @param skillNodes
	 * @param checked
	 */
	private void alignAvailableSkills(ArrayList<SkillNodeVO> skillNodes,ArrayList<CheckedSkillsVO> checked) {
		logger.info("SkillAssignGeneralAction.alignAvailableSkills() method starts");
		for (int i = 0; i < skillNodes.size(); i++) {
			SkillNodeVO skill = (SkillNodeVO) skillNodes.get(i);
			skill.setActive(false);
			for (int j = 0; j < checked.size(); j++) {
				CheckedSkillsVO chkd = (CheckedSkillsVO) checked.get(j);
				if (skill.getNodeId() == chkd.getNodeId()) {
					skill.setActive(true);
				}
			}
		}
		logger.info("SkillAssignGeneralAction.alignAvailableSkills() method ends");
	}

	
	private void resetAvailableSkills(ArrayList<SkillNodeVO> skillNodes) {
		logger.info("SkillAssignGeneralAction.resetAvailableSkills() method starts");
		for (int i = 0; i < skillNodes.size(); i++) {
			SkillNodeVO skill = (SkillNodeVO)skillNodes.get(i);
			skill.setActive(false);
		}
		logger.info("SkillAssignGeneralAction.resetAvailableSkills() method ends");
	}
	/**
	 * Save languages and skills selected by the resource
	 * @return
	 */
	public String saveSkills() {
		logger.info("SkillAssignGeneralAction.saveSkills() method starts");
		//Get SkillAssignDto from Session.
		ResourceSkillAssignDto sSkillAssignDto = (ResourceSkillAssignDto) ActionContext
				.getContext().getSession().get("skillDto");
		setSkillAssignDto(sSkillAssignDto);
		Long resourceId = sSkillAssignDto.getResourceId();
		String reqType = (String)request.getParameter("type");
		Map<Integer, Integer> allSelectionsNodes = new TreeMap<Integer, Integer>();
		Map<Integer, Integer> orderOfNodes = new TreeMap<Integer, Integer>();
		ArrayList<SkillServiceTypeVO> skillServiceArrayChecked = new ArrayList<SkillServiceTypeVO>();
		ArrayList<SkillServiceTypeVO> skillServiceArrayToDelete = new ArrayList<SkillServiceTypeVO>();
		ArrayList<SkillServiceTypeVO> skillServiceArrayToAssign = new ArrayList<SkillServiceTypeVO>();
		ArrayList<LanguageVO> languageList = new ArrayList<LanguageVO>();
		Map paramMap = request.getParameterMap();
		int count = 1;
		int m = 0;
		boolean skillsSelection = false;
		for (Object objKey : paramMap.keySet()) {
			String[] values = (String[]) paramMap.get(objKey);
			String key = (String) objKey;
			// This assumes the naming scheme for checkboxes is chk_###
			if (key.toLowerCase().startsWith("chk_")) {
				if ("on".equalsIgnoreCase(values[0])) {
					// Checkbox is selected
					skillsSelection = true;
					StringTokenizer st = new StringTokenizer(key, "_");
					st.nextToken();
					Integer nodeId = new Integer(st.nextToken().trim());
					Integer orderBy = new Integer(st.nextToken().trim());
					orderOfNodes.put(orderBy, nodeId);
					SkillServiceTypeVO skillServiceTypes = new SkillServiceTypeVO();
					skillServiceTypes.setSkillNodeId(nodeId);
					skillServiceTypes.setServiceTypeId(-1);
					skillServiceArrayChecked.add(skillServiceTypes);
				} else {
					// Checkbox is not selected (form should never reach here)
					//logger.info("Should not have reached this code.  In ResourceSkillAssignGeneralAction, checkbox values other than 'on'");
				}
			} else if (key.toLowerCase().startsWith("lan_")) {
				StringTokenizer st = new StringTokenizer(key, "_");
				st.nextToken();
				Long langId = new Long(st.nextToken().trim());
				LanguageVO languageVO = new LanguageVO();
				languageVO.setResourceId(resourceId);
				languageVO.setLanguageId(langId);
				languageList.add(languageVO);
			}
		}//End for loop
		if(!skillsSelection)
		{
			addFieldError("skillAssignDto.resourceId","Please select one classification for the skills");
			BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
			.getSession().get("tabDto");
			
			baseTabDto.setDtObject(skillAssignDto);
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_SKILLS,"errorOn");
			logger.info("SkillAssignGeneralAction.saveSkills() method ends");
			return INPUT;
		}
		// move from ordered by order to allSelectionsNodes - should be ordered now
		for (Iterator i = orderOfNodes.entrySet().iterator(); i.hasNext();) {
			Map.Entry entry = (Map.Entry) i.next();
			allSelectionsNodes.put(new Integer(count), (Integer) entry.getValue());
			count++;
		}
		
		// separate into 2 buckets - for insert and for delete
		// this will compare what is checked in the db vs. what is now checked and separate accordingly
		try {
			separateInsertAndDelete(skillServiceArrayChecked,
					skillServiceArrayToDelete, skillServiceArrayToAssign);
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
		
		//Put Current Grid & allSelection Nodes in Session
		sSessionMap.put("currentGrid", new Integer(1));
		sSessionMap.put("skillsNodes", allSelectionsNodes);
		ActionContext.getContext().setSession(sSessionMap);
		sSkillAssignDto.setSkillServiceList(skillServiceArrayToAssign);
		sSkillAssignDto.setSkillServiceListToDelete(skillServiceArrayToDelete);
		sSkillAssignDto.setLanguageList(languageList);

		try {
			skillAssignDto = iSkillAssignDelegate.assignSkills(sSkillAssignDto);
			
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
		
			
			if(reqType.equalsIgnoreCase("back"))
				return "back";
			else if(reqType.equalsIgnoreCase("updateProfile"))
				return "updateProfile";
			else if(reqType.equalsIgnoreCase("forward"))
				return "next";
			else if(reqType.equalsIgnoreCase("stay"))
				return "generalSkills";
			
		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext().getSession().get("tabDto");
		baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_SKILLS,ActivityRegistryConstants.INCOMPLETE);
		logger.info("SkillAssignGeneralAction.saveSkills() method ends");
		return "save";
		
	}

	@SkipValidation
	public String doCancel() throws Exception {
		logger.info("SkillAssignGeneralAction.doCancel() method called");
		return "cancel";
	}
	
	/**
	 * Separate into 2 buckets - for insert and for delete
	 * this will compare what is checked in the db vs. what is now checked and separate accordingly
	 * @param newlyChecked
	 * @param toDelete
	 * @param toAssign
	 * @throws Exception
	 */
	private void separateInsertAndDelete(
			ArrayList<SkillServiceTypeVO> newlyChecked,
			ArrayList<SkillServiceTypeVO> toDelete,
			ArrayList<SkillServiceTypeVO> toAssign) throws Exception {

		// get the old checked guys:
		ArrayList<CheckedSkillsVO> oldCheckedSkills = new ArrayList<CheckedSkillsVO>();
		oldCheckedSkills = getCheckedSkills(skillAssignDto.getResourceId());

		// will fill the toDelete bucket with all the nodes that are in old, but not in new
		for (int i = 0; i < oldCheckedSkills.size(); i++) {
			CheckedSkillsVO oldChecked = (CheckedSkillsVO) oldCheckedSkills.get(i);
			boolean inNew = false;
			for (int j = 0; j < newlyChecked.size(); j++) {
				// if in old, but not in new, put in delete bucket
				SkillServiceTypeVO newChecked = (SkillServiceTypeVO) newlyChecked.get(j);
				if (oldChecked.getNodeId() == newChecked.getSkillNodeId()) {
					inNew = true;
				}
			}
			if (!inNew) {
				SkillServiceTypeVO skillServType = new SkillServiceTypeVO();
				skillServType.setSkillNodeId(oldChecked.getNodeId());
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
				if (oldChecked.getNodeId() == newChecked.getSkillNodeId()) {
					inOld = true;
				}
			}
			if (!inOld) {
				toAssign.add(newChecked);
			}
		}
	}

	public void setSession(Map ssessionMap) {
		this.sSessionMap = ssessionMap;
	}

	public Map getSession() {
		return this.sSessionMap;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}

	public ResourceSkillAssignDto getSkillAssignDto() {
		return skillAssignDto;
	}

	public ISkillAssignDelegate getISkillAssignDelegate() {
		return iSkillAssignDelegate;
	}

	public void setSkillAssignDto(ResourceSkillAssignDto skillAssignDto) {
		this.skillAssignDto = skillAssignDto;
	}

	public void setISkillAssignDelegate(ISkillAssignDelegate skillAssignDelegate) {
		iSkillAssignDelegate = skillAssignDelegate;
	}

	public String[] getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(String[] checkbox) {
		this.checkbox = checkbox;
	}

	public Map getSSessionMap() {
		return sSessionMap;
	}

	public void setSSessionMap(Map sessionMap) {
		sSessionMap = sessionMap;
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