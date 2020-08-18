package com.newco.marketplace.web.action.jobcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.owasp.esapi.ESAPI;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.dto.vo.skillTree.ServiceTypesVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.IJobCodeDelegate;
import com.newco.marketplace.web.dto.JobCodeMappingDTO;
import com.newco.marketplace.web.dto.SkuTaskDTO;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class JobCodeMappingAction extends SLBaseAction implements Preparable, ModelDriven<JobCodeMappingDTO>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4121513660342738166L;
	private static final Logger logger = Logger.getLogger(JobCodeMappingAction.class.getName());

	private IJobCodeDelegate jobCodeDelegate;
	private JobCodeMappingDTO jobCodeMappingDto = new JobCodeMappingDTO();
	private ArrayList<SkillNodeVO> skillTreeMainCat;
	private HashMap<Integer, ArrayList<ServiceTypesVO>> skillSelectionMap;
	private ArrayList<ServiceTypesVO> skillSelection = new ArrayList<ServiceTypesVO>();
	private ArrayList<SkillNodeVO> categorySelection = new ArrayList<SkillNodeVO>();
	private ArrayList<SkillNodeVO> subCategorySelection = new ArrayList<SkillNodeVO>();
	
	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		
	}
	
	public JobCodeMappingDTO getModel() {	
		Integer mainCat = (Integer)getSession().getAttribute("mainServiceCategoryId");

		if(mainCat != null){
			jobCodeMappingDto.setMainCategoryId(mainCat);
		}
		
		return jobCodeMappingDto;
	}
	
	public void setModel(JobCodeMappingDTO jobCodeMappingDto) {		
		this.jobCodeMappingDto = jobCodeMappingDto;
	}

	public String execute() throws Exception {
		getSession().removeAttribute("mainServiceCategoryId");
		loadMainCategory();
		loadSOTemplates();
		return SUCCESS;	
	}	

	public String addSkuTask() throws Exception{
		logger.debug("Enterting JobCodeMappingAction --> addSkuTask()");
		jobCodeMappingDto = getModel();
		Integer buyerId = get_commonCriteria().getCompanyId();
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		try{
			if(jobCodeMappingDto != null){
				SkuTaskDTO skuTaskDTO = jobCodeMappingDto.getNewSkuTask();
				if(skuTaskDTO != null
						&& skuTaskDTO.getCategoryId() != null
						&& skuTaskDTO.getSkillId() != null){
					getJobCodeDelegate().addSkuTask(skuTaskDTO, jobCodeMappingDto.getJobCode(),
							jobCodeMappingDto.getSpecCode(), buyerId);
					logger.info("JobCodeMappingAction --> addSkuTask() --> " +
							"Task is inserted successfully");
					List<SkuTaskDTO> skuTaskList = getJobCodeDelegate().getSkuTasks(jobCodeMappingDto.getJobCode()
														,jobCodeMappingDto.getSpecCode(), buyerId);
					
					jobCodeMappingDto.setSkuTaskList(skuTaskList);
				}else{
					getRequest().setAttribute("isJobCodeError", "true");
					getRequest().setAttribute("jobCodeMsg", "Please select category and skill");
				}
			}else{
				getRequest().setAttribute("isJobCodeError", "true");
				getRequest().setAttribute("jobCodeMsg", "Error occured. Try again");
			}			
		}catch(BusinessServiceException bse){
			logger.error("Exception in JobCodeMappingAction --> addSkuTask() due to "+bse.getMessage());
			getRequest().setAttribute("isJobCodeError", "true");
			getRequest().setAttribute("jobCodeMsg", "Error occured. Try again");
		}finally{			
			Map<Integer,String> soTemplates = getJobCodeDelegate().getSoTemplates(buyerId);
			jobCodeMappingDto.setTemplateList(soTemplates);			
		}
		loadCategoriesIfExists();
		logger.debug("Leaving JobCodeMappingAction --> addSkuTask()");
		return SUCCESS;	
	}
	
	public String deleteSkuTask() throws Exception{
		logger.debug("Enterting JobCodeMappingAction --> deleteSkuTask()");	
		jobCodeMappingDto = getModel();
		Integer buyerId = get_commonCriteria().getCompanyId();
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		try{			
			String jobCode = getRequest().getParameter("sku");
			String catId = getRequest().getParameter("catId");
			String subCatId = getRequest().getParameter("subCatId");
			String skillId = getRequest().getParameter("skillId");
			
			if(StringUtils.isNotEmpty(jobCode)
					&& StringUtils.isNotEmpty(skillId)){
				SkuTaskDTO skuTaskDTO = new SkuTaskDTO();
				
				skuTaskDTO.setSkillId(Integer.valueOf(skillId));
				if(StringUtils.isNotEmpty(subCatId)){
					skuTaskDTO.setSubCategoryId(Integer.valueOf(subCatId));
				}
				if(StringUtils.isNotEmpty(catId)){
					skuTaskDTO.setCategoryId(Integer.valueOf(catId));
				}
				getJobCodeDelegate().deleteSkuTask(skuTaskDTO, jobCode, jobCodeMappingDto.getSpecCode(), buyerId);
				
				List<SkuTaskDTO> skuTaskList = getJobCodeDelegate().getSkuTasks(jobCode
						,jobCodeMappingDto.getSpecCode(), buyerId);
				jobCodeMappingDto.setSkuTaskList(skuTaskList);
			}else{
				getRequest().setAttribute("isJobCodeError", "true");
				getRequest().setAttribute("jobCodeMsg", "Please enter Job Code and a task");
			}					
		}catch(BusinessServiceException bse){
			logger.error("Exception in JobCodeMappingAction --> deleteSkuTask() due to "+bse.getMessage());
			getRequest().setAttribute("isJobCodeError", "true");
			getRequest().setAttribute("jobCodeMsg", "Error occured. Try again");
		}finally{
			Map<Integer,String> soTemplates = getJobCodeDelegate().getSoTemplates(buyerId);
			jobCodeMappingDto.setTemplateList(soTemplates);	
		}
		loadCategoriesIfExists();
		logger.debug("Leaving JobCodeMappingAction --> deleteSkuTask()");
		return SUCCESS;	
	}
	
	public String getJobCodeDetails() throws Exception{
		logger.debug("Enterting JobCodeMappingAction --> getJobCodeDetails()");	
		JobCodeMappingDTO jobCodeDto = new JobCodeMappingDTO();
		Integer buyerId = get_commonCriteria().getCompanyId();
		try{
			String jobCode = getRequest().getParameter("jobCode");
			String specCode = getRequest().getParameter("specCode");
			if(StringUtils.isNotEmpty(jobCode)
					&& StringUtils.isNotEmpty(specCode)){
				jobCodeDto = getJobCodeDelegate().getJobCodeDetails(jobCode, specCode, buyerId);
				jobCodeMappingDto.setTemplateList(jobCodeDto.getTemplateList());
			}else{
				getRequest().setAttribute("isJobCodeError", "true");
				getRequest().setAttribute("jobCodeMsg", "Please enter Job Code and Specialty Code");
			}
		}catch(BusinessServiceException bse){
			logger.error("Exception in JobCodeMappingAction --> deleteSkuTask() due to "+bse.getMessage());
			getRequest().setAttribute("isJobCodeError", "true");
			getRequest().setAttribute("jobCodeMsg", "Error occured. Try again ");
			Map<Integer,String> soTemplates = getJobCodeDelegate().getSoTemplates(buyerId);
			jobCodeMappingDto.setTemplateList(soTemplates);
			
		}		
		
		jobCodeMappingDto.setMainCategoryId(jobCodeDto.getMainCategoryId());
		getSession().setAttribute("mainServiceCategoryId",jobCodeMappingDto.getMainCategoryId());
		
		categorySelection = getLookupManager().getSkillTreeCategoriesOrSubCategories(jobCodeMappingDto.getMainCategoryId());
		loadMainCategory();
		skillSelection = getSkillSelectionMap().get(jobCodeMappingDto.getMainCategoryId());
        if(skillSelection != null){
			getSession().setAttribute("skillSelection", skillSelection);
		}else{
			skillSelection = new ArrayList<ServiceTypesVO>();
		}
		jobCodeMappingDto.setJobCode(jobCodeDto.getJobCode());
		jobCodeMappingDto.setSpecCode(jobCodeDto.getSpecCode());
		jobCodeMappingDto.setInclusionDescr(jobCodeDto.getInclusionDescr());
		jobCodeMappingDto.setMajorHeadingDescr(jobCodeDto.getMajorHeadingDescr());
		jobCodeMappingDto.setSubHeadingDescr(jobCodeDto.getSubHeadingDescr());
		jobCodeMappingDto.setSkuTaskList(jobCodeDto.getSkuTaskList());
		jobCodeMappingDto.setTemplateId(jobCodeDto.getTemplateId());
		jobCodeMappingDto.setTaskComment(jobCodeDto.getTaskComment());
		jobCodeMappingDto.setTaskName(jobCodeDto.getTaskName());
		
		logger.debug("Leaving JobCodeMappingAction --> getJobCodeDetails()");
		return SUCCESS;
	}
	
	public String saveJobCode() throws Exception{
		jobCodeMappingDto = getModel();
		Integer buyerId = get_commonCriteria().getCompanyId();
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		try{			
			if(jobCodeMappingDto.getTemplateId() != null
					&& jobCodeMappingDto.getTemplateId().intValue() > 0){
				if(jobCodeMappingDto.getSkuTaskList() != null
				&& jobCodeMappingDto.getSkuTaskList().size() > 0){
					getJobCodeDelegate().saveJobCode(jobCodeMappingDto, buyerId);
					getRequest().setAttribute("isJobCodeError", "false");
					getRequest().setAttribute("jobCodeMsg", "Successfully saved the job code");
					
					List<SkuTaskDTO> skuTaskList = getJobCodeDelegate().getSkuTasks(jobCodeMappingDto.getJobCode()
														,jobCodeMappingDto.getSpecCode(), buyerId);
					jobCodeMappingDto.setSkuTaskList(skuTaskList);
				}else{
					getRequest().setAttribute("isJobCodeError", "true");
					getRequest().setAttribute("jobCodeMsg", "Add task to save the job code");
				}
			}else{
				getRequest().setAttribute("isJobCodeError", "true");
				getRequest().setAttribute("jobCodeMsg", "Select a template for the job code");
			}
		}catch(BusinessServiceException bse){
			logger.error("Exception in JobCodeMappingAction --> saveJobCode() due to "+bse.getMessage());
			getRequest().setAttribute("isJobCodeError", "true");
			getRequest().setAttribute("jobCodeMsg", "Exception occured. Try again");			
		}finally{
			Map<Integer,String> soTemplates = getJobCodeDelegate().getSoTemplates(buyerId);
			jobCodeMappingDto.setTemplateList(soTemplates);
		}
		loadCategoriesIfExists();
		return SUCCESS;
	}
	
	public void loadMainCategory(){
		try {
			skillTreeMainCat = getLookupManager().getSkillTreeMainCategories();
			Iterator<SkillNodeVO> i = skillTreeMainCat.iterator();
			SkillNodeVO singleSkillNode;
			Map<Integer, String> mainServiceCategoryNamesMap = new HashMap<Integer,String>();
			skillSelectionMap = new HashMap();
			while(i.hasNext()){
				singleSkillNode = i.next();
				mainServiceCategoryNamesMap.put(singleSkillNode.getNodeId(), singleSkillNode.getNodeName());
				if(singleSkillNode.getServiceTypes() != null){
					skillSelectionMap.put(singleSkillNode.getNodeId(), new ArrayList(singleSkillNode.getServiceTypes()));
				}
			}
			getSession().setAttribute("mainServiceCategoryNamesMap", mainServiceCategoryNamesMap);
			getSession().setAttribute("mainServiceCategory", skillTreeMainCat);
			getSession().setAttribute("skillSelectionMap", skillSelectionMap);
			getSession().setAttribute("categorySelection", categorySelection);
			getSession().setAttribute("skillSelection", skillSelection);
		} catch (com.newco.marketplace.exception.core.BusinessServiceException e) {
			logger.error("Exception loading main skill category");
		}
	}
	
	public String loadSkillsAndCategories()throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");	
		StringBuffer xmlString = new StringBuffer("<message_output>");//------------Start XML String
		
		Integer req =(Integer) getRequest().getAttribute("mainServiceCategoryId");
		if(req !=null && req > -1){
			xmlString.append("<mainCategorySelected>" + req + "</mainCategorySelected>");			
		}else{
			String val = getRequest().getParameter("mainServiceCategoryId");
			if(val != null){
				req = Integer.valueOf(val);
			}
		}
		getSession().setAttribute("mainServiceCategoryId", req);
		try{	
			String nodeId = "";
			String nodeIdVal = "";
			jobCodeMappingDto.setMainCategoryId(req);
			Map<Integer, String> mainServiceCategoryNamesMap = (Map<Integer, String>)getSession().getAttribute("mainServiceCategoryNamesMap");
			skillSelection = getSkillSelectionMap().get(req);
			ServiceTypesVO singleSkill = new ServiceTypesVO();

			for(int i = 0; i<skillSelection.size(); i++){	
				singleSkill = skillSelection.get(i);
				xmlString.append("<skill>");
				xmlString.append("<skillId>" + singleSkill.getServiceTypeId() + "</skillId>");
				xmlString.append("<skillName>" + singleSkill.getDescription().replaceAll("&", "&amp;") + "</skillName>");
				xmlString.append("</skill>");
			}
			getSession().setAttribute("skillSelection", skillSelection);
			categorySelection = getLookupManager().getSkillTreeCategoriesOrSubCategories(jobCodeMappingDto.getMainCategoryId());
			
			if(categorySelection != null && !categorySelection.isEmpty()){
				SkillNodeVO singleCategory = new SkillNodeVO();
				if(singleCategory.getNodeId()!=null){
				nodeId = singleCategory.getNodeId().toString();
				nodeIdVal = ESAPI.encoder().encodeForXML(nodeId);
				}
				
				String nodeName = singleCategory.getNodeName();
				String nodeNameVal = ESAPI.encoder().encodeForXML(nodeName);
				getSession().setAttribute("categorySelection", categorySelection);
				for(int i = 0; i<categorySelection.size(); i++){
					singleCategory = categorySelection.get(i);
					xmlString.append("<cat>");
					xmlString.append("<categoryId>" + nodeIdVal + "</categoryId>");
					xmlString.append("<categoryName>" + nodeNameVal.replaceAll("&", "&amp;") + "</categoryName>");
					xmlString.append("</cat>");
				}
			}
			xmlString.append("</message_output>");//------------End XML String
			response.getWriter().write(xmlString.toString());
		}catch (Throwable t){
			logger.error("Could not set skills and categories properly", t);
		}
		return null;
	}
	
	public String loadSubcategories() throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");	
		StringBuffer xmlString = new StringBuffer("<message_output>");//------------Start XML String
		jobCodeMappingDto = getModel();
		
		Integer selectedCategoryId = null;
		String val = getRequest().getParameter("selectedCategoryId");
		if(val != null){
			selectedCategoryId = Integer.valueOf(val);
		}	
		SkillNodeVO singleSubCategory = new SkillNodeVO();
		String nodeId = "";
		String nodeName = "";
		String nodeIdVal = "";
		if(selectedCategoryId != null){			
			xmlString.append("<categorySelectedId>" + selectedCategoryId + "</categorySelectedId>");
			xmlString.append("<taskIndex>" + 0 + "</taskIndex>");
		}
		try{
			TreeMap<Integer, ArrayList<SkillNodeVO>> subCategorySelectionSessionMap =
					(TreeMap)getSession().getAttribute("subCategorySelectionSessionMap");
			if(subCategorySelectionSessionMap == null){
				subCategorySelectionSessionMap = new TreeMap();				
			}

			if(selectedCategoryId != null && selectedCategoryId != -1){
				subCategorySelection = getLookupManager().getSkillTreeCategoriesOrSubCategories(selectedCategoryId);
			}

			if(!subCategorySelection.isEmpty()){
				for(int i = 0; i< subCategorySelection.size(); i++){
					singleSubCategory = subCategorySelection.get(i);
					if(singleSubCategory.getNodeId()!=null){
					nodeId = singleSubCategory.getNodeId().toString();
					nodeIdVal = ESAPI.encoder().encodeForXML(nodeId);
					}
					nodeName = ESAPI.encoder().encodeForXML(singleSubCategory.getNodeName());
					xmlString.append("<subcat>");
					xmlString.append("<subCategoryId>" + nodeIdVal + "</subCategoryId>");
					xmlString.append("<subCategoryName>" + nodeName.replaceAll("&", "&amp;") + "</subCategoryName>");
					xmlString.append("</subcat>");					
				}				
			} else {
				subCategorySelection = new ArrayList<SkillNodeVO>();
			}
			
			subCategorySelectionSessionMap.put(selectedCategoryId, subCategorySelection);
			getSession().setAttribute("subCategorySelectionSessionMap", subCategorySelectionSessionMap);
			getSession().setAttribute("subCategorySelection", subCategorySelection);
			xmlString.append("</message_output>");//------------End XML String	
			response.getWriter().write(xmlString.toString());
		}catch (Throwable t){
			logger.error("Could not set subcategories properly", t);
		}
		
		return null;
	}
	
	private HashMap<Integer, ArrayList<ServiceTypesVO>> getSkillSelectionMap() {
		return (HashMap<Integer, ArrayList<ServiceTypesVO>>)getSession().getAttribute("skillSelectionMap");
	}
	
	private void loadSOTemplates(){
		Map<Integer, String> soTemplates = null;		
		Integer buyerId = get_commonCriteria().getCompanyId();
		
		try {
			soTemplates = getJobCodeDelegate().getSoTemplates(buyerId);
			jobCodeMappingDto.setTemplateList(soTemplates);
		} catch (BusinessServiceException e) {
			logger.error("Exception in JobCodeMappingAction retrieving buyer so templates");
		}
	}

	private void loadCategoriesIfExists(){
		Object catList = getSession().getAttribute("categorySelection");
		Object skillList = getSession().getAttribute("skillSelection");
		Object subCatList = getSession().getAttribute("subCategorySelection");
		if(catList != null){
			categorySelection = (ArrayList<SkillNodeVO>)catList;
		}
		
		if(skillList != null){
			skillSelection = (ArrayList<ServiceTypesVO>)skillList;
		}
		if(subCatList != null){
			subCategorySelection = (ArrayList<SkillNodeVO>)subCatList;
		}
	}
	/**
	 * @return the jobCodeDelegate
	 */
	public IJobCodeDelegate getJobCodeDelegate() {
		return jobCodeDelegate;
	}

	/**
	 * @param jobCodeDelegate the jobCodeDelegate to set
	 */
	public void setJobCodeDelegate(IJobCodeDelegate jobCodeDelegate) {
		this.jobCodeDelegate = jobCodeDelegate;
	}

	public ArrayList<SkillNodeVO> getSkillTreeMainCat() {
		return (ArrayList<SkillNodeVO>)getSession().getAttribute("mainServiceCategory");
	}


	public void setSkillTreeMainCat(ArrayList<SkillNodeVO> skillTreeMainCat) {
		this.skillTreeMainCat = skillTreeMainCat;
	}

	/**
	 * @return the categorySelection
	 */
	public ArrayList<SkillNodeVO> getCategorySelection() {
		return categorySelection;
	}

	/**
	 * @param categorySelection the categorySelection to set
	 */
	public void setCategorySelection(ArrayList<SkillNodeVO> categorySelection) {
		this.categorySelection = categorySelection;
	}

	/**
	 * @return the skillSelection
	 */
	public ArrayList<ServiceTypesVO> getSkillSelection() {
		return skillSelection;
	}

	/**
	 * @param skillSelection the skillSelection to set
	 */
	public void setSkillSelection(ArrayList<ServiceTypesVO> skillSelection) {
		this.skillSelection = skillSelection;
	}

	/**
	 * @return the subCategorySelection
	 */
	public ArrayList<SkillNodeVO> getSubCategorySelection() {
		return subCategorySelection;
	}

	/**
	 * @param subCategorySelection the subCategorySelection to set
	 */
	public void setSubCategorySelection(ArrayList<SkillNodeVO> subCategorySelection) {
		this.subCategorySelection = subCategorySelection;
	}

	/**
	 * @param skillSelectionMap the skillSelectionMap to set
	 */
	public void setSkillSelectionMap(
			HashMap<Integer, ArrayList<ServiceTypesVO>> skillSelectionMap) {
		this.skillSelectionMap = skillSelectionMap;
	}	
}
