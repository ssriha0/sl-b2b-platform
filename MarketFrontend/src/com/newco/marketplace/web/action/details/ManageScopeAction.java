package com.newco.marketplace.web.action.details;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.ManageTaskVO;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.SOTaskDTO;
import com.newco.marketplace.web.dto.SOWError;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.service.ManageScopeService;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.servicelive.domain.lookup.LookupServiceType;
import com.servicelive.domain.reasoncodemgr.ReasonCode;
import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.domain.so.BuyerOrderSkuTask;


public class ManageScopeAction extends SLDetailsBaseAction implements ModelDriven<ManageTaskVO>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ManageScopeAction.class.getName());
	
	private List<SOTaskDTO> tasks = new ArrayList<SOTaskDTO>();
	private ManageTaskVO newTask = new ManageTaskVO ();
	private List<SOWError> taskErrors = new ArrayList<SOWError>();
	private BigDecimal permitPrice = new BigDecimal(0.00).setScale(2);
	private BigDecimal partsPrice = new BigDecimal(0.00).setScale(2);
	private BigDecimal labourPrice = new BigDecimal(0.00).setScale(2);
	private BigDecimal totalPrice = new BigDecimal(0.00).setScale(2);
	private List<ReasonCode> reasonCodes = new ArrayList<ReasonCode>() ;	
	private ManageScopeService manageScopeService;
	private ISODetailsDelegate detailsDelegate;
	
	private static final String SO_ID = "soId";
	private static final String FROM_CANCEL = "fromCancel";
	private static final String SKU_SKILL = "skuSkill";
	private static final String SKU_SKILL_FOR_CATEGORY = "skuSkillForCategory";
	private static final String SO_MAIN_CAT_ID = "soMainCatId";
	private static final String TOTAL_PRICE = "totalPrice";
	private static final String INIT_PRICE = "initPrice";
	private static final String PERMIT_ON = "isPermitOn";
	private static final String SCOPE_CHANGE_COMMENTS = "scopeChangeComments";
	private static final String SCOPE_CHANGE_REASON = "scopeChangeReason";
	static private String SOD_ACTION = "/soDetailsController.action";
	ProcessResponse prResp = null;
	String strErrorCode ="";
	String strErrorMessage="";
	String strResponse="";
	
	
	public void setManageScopeService(ManageScopeService manageScopeService) {
		this.manageScopeService = manageScopeService;
	}
 
	public String execute(){
		//Sl-19820: SCOPE_CHANGE_TASKS should be SCOPE_CHANGE_TASKS_soId
		String fromCancel = null;
		if(StringUtils.isNotBlank(getRequest().getParameter(FROM_CANCEL))){
			fromCancel = (String)getRequest().getParameter(FROM_CANCEL);
		}
		//SL-19820
		//ServiceOrderDTO serviceOrderDTO = getCurrentServiceOrderFromSession();
		String soId = getParameter(SO_ID);
		setAttribute(SO_ID, soId);
		ServiceOrderDTO serviceOrderDTO = getServiceOrder(soId);
		Integer soMainCatId = null;
		String buyerId = null;
		boolean permitOn=false;
		String primarySku=null;
		if(null != serviceOrderDTO){
			buyerId = serviceOrderDTO.getBuyerID();
			soMainCatId = serviceOrderDTO.getMainServiceCategoryId();
			List<SOTaskDTO> existingTasks = serviceOrderDTO.getTaskList();
			for (SOTaskDTO existingTask : existingTasks){
				if(existingTask.isPrimaryTask() && null == soMainCatId){
					soMainCatId = existingTask.getSkillId();
				}
				
				if(existingTask.isPrimaryTask()){
					primarySku= existingTask.getSku();
				}
				SOTaskDTO task = new SOTaskDTO();
				task.setTitle(existingTask.getTitle());
				task.setFinalPrice(existingTask.getFinalPrice());
				if (null != task.getFinalPrice()){
					if (existingTask.getTaskType().equals(1)) {
						permitPrice = permitPrice.add(new BigDecimal(task.getFinalPrice()).setScale(2, RoundingMode.HALF_UP));
					}else{
						labourPrice = labourPrice.add(new BigDecimal(task.getFinalPrice()).setScale(2, RoundingMode.HALF_UP));
					}
					if(null!= fromCancel && "true".equalsIgnoreCase(fromCancel)){
						task.setFinalPrice(0.00);
					}
				}
				tasks.add(task);
			}
			partsPrice = new BigDecimal(serviceOrderDTO.getPartsSpendLimit()).setScale(2, RoundingMode.HALF_UP);
			if(null!= fromCancel && "true".equalsIgnoreCase(fromCancel)){
				partsPrice = new BigDecimal(0.0).setScale(2);
				labourPrice = new BigDecimal(0.0).setScale(2);
				permitPrice = new BigDecimal(0.0).setScale(2);
			}
			if(null!=serviceOrderDTO.getPermitTaskList() && serviceOrderDTO.getPermitTaskList().size()>0){
				permitOn=true;
			}
		}
		totalPrice = totalPrice.add(labourPrice).add(permitPrice).add(partsPrice);
		List<Map<Integer, String>> skuSkillList = manageScopeService.fetchBuyerSkus(buyerId);
		
		// for displaying SKUs in Manage Scope widget
		List<Map<Integer, String>> skuSkillForCategory ;
		//For RI buyer we are loading SKUs based on Service Order division and for other buyer based on Main Category.
		if(buyerId.equals(Constants.SEARS_BUYER)){
			skuSkillForCategory = manageScopeService.findSKUsForDivision(primarySku, buyerId);	
		}else{
			skuSkillForCategory = manageScopeService.findSKUsForCategory(buyerId,soMainCatId);
		}
		List<Map<Integer, String>> skuSkillListNew =new ArrayList<Map<Integer, String>>();
		
		if(skuSkillList!=null && skuSkillList.size()>0 && skuSkillForCategory!=null && skuSkillForCategory.size()>0){
			for (Map<Integer, String> skuSkillMap : skuSkillList){				
				boolean found=false;				
				for (Map<Integer, String> skuSkillMapCategory : skuSkillForCategory) {					
					Object skillids = skuSkillMap.get("skuId");
					Object sku=skuSkillMap.get("sku");
					Set <Integer> ids = skuSkillMapCategory.keySet();
					for(Integer id :ids){
						if(id.toString().equals(skillids.toString())){
							found=true;
						}
					}				
					if(found){
						break;
					}
				}				
				if(!found){
					skuSkillListNew.add(skuSkillMap);	
				}
	
			}
		}else{
			skuSkillListNew=skuSkillList;
		}
		// end of display sku
		
		reasonCodes = manageScopeService.getScopeChangeReasonCodes(buyerId);
		getRequest().setAttribute(FROM_CANCEL, fromCancel);
		getRequest().setAttribute(SO_MAIN_CAT_ID, soMainCatId);
		getSession().setAttribute(SKU_SKILL, skuSkillListNew);
		getSession().setAttribute(SKU_SKILL_FOR_CATEGORY, skuSkillForCategory);
		
		
		newTask.setLabourPrice(labourPrice);
		
		newTask.setPartsPrice(partsPrice);
		
		newTask.setPermitPrice(permitPrice);
	
		newTask.setTotalPrice(totalPrice);
		

		if(permitOn)
		{
			//SL-19820
			//getSession().setAttribute(PERMIT_ON,"true");
			setAttribute(PERMIT_ON,"true");
		}
		else
		{
			//SL-19820
			//getSession().setAttribute(PERMIT_ON,"false");
			setAttribute(PERMIT_ON,"false");
		}
		//SL-19820
		getSession().setAttribute(Constants.SCOPE_CHANGE_TASKS+"_"+soId, new HashMap<Integer,ManageTaskVO>());
		setAttribute(Constants.SCOPE_CHANGE_TASKS, new HashMap<Integer,ManageTaskVO>());
		
		return SUCCESS;
	}
	

	public String addTask(){
		ManageTaskVO task = getModel();
		//SL-19820
		String soId = getParameter(SO_ID);
		setAttribute(SO_ID, soId);
		//validate
		if(isTaskFormValid(task)){
			StringBuilder taskName = new StringBuilder();
			if(null != task.getSku() && !StringUtils.isBlank(task.getSku()) && !("0").equals(task.getSku()))
				taskName.append(task.getSku()).append('-');
			taskName.append(task.getTaskName());
			if(null != task.getSkill() && !StringUtils.isBlank(task.getSkill()) && !("NA").equals(task.getSkill()))
				taskName.append('-').append(task.getSkill());
			task.setTaskName(taskName.toString());
			StringBuilder price = new StringBuilder();
			price.append(task.getPriceInteger()).append('.').append(task.getPriceFraction());
			task.setFinalPrice(new BigDecimal(price.toString()));			
			labourPrice = task.getMaxLabourPrice().add(task.getFinalPrice()).setScale(2, RoundingMode.HALF_UP);
			totalPrice = task.getMaxTotalPrice().add(task.getFinalPrice()).setScale(2, RoundingMode.HALF_UP);
			@SuppressWarnings("unchecked")
			//SL-19820
			//HashMap<Integer, ManageTaskVO> newTasks = (HashMap<Integer, ManageTaskVO>)getSession().getAttribute("scopeChangeTasks");
			HashMap<Integer, ManageTaskVO> newTasks = (HashMap<Integer, ManageTaskVO>)getSession().getAttribute("scopeChangeTasks_"+soId);
			newTasks.put(task.getId(), task);
			task.setLabourPrice(labourPrice);
			task.setTotalPrice(totalPrice);
			setNewTask(task);
			//SL-19820
			//getSession().setAttribute(Constants.SCOPE_CHANGE_TASKS,newTasks);
			getSession().setAttribute(Constants.SCOPE_CHANGE_TASKS+"_"+soId,newTasks);
			setAttribute(Constants.SCOPE_CHANGE_TASKS,newTasks);
		}
		return SUCCESS;
		
	}
	
	public String deleteTask() {
		try {
			ManageTaskVO task = getModel();
			String id = getRequest().getParameter("id");
			Integer taskId = new Integer(id);
			//SL-19820
			String soId = getParameter(SO_ID);
			setAttribute(SO_ID, soId);
			@SuppressWarnings("unchecked")			
			//HashMap<Integer, ManageTaskVO> newTasks = (HashMap<Integer, ManageTaskVO>) getSession()
			        //.getAttribute(Constants.SCOPE_CHANGE_TASKS);
			HashMap<Integer, ManageTaskVO> newTasks = (HashMap<Integer, ManageTaskVO>) getSession()
			        .getAttribute(Constants.SCOPE_CHANGE_TASKS+"_"+soId);
			ManageTaskVO selectedTask = newTasks.get(taskId);
			labourPrice = task.getMaxLabourPrice().subtract(selectedTask.getFinalPrice()).setScale(2, RoundingMode.HALF_UP);
			totalPrice = task.getMaxTotalPrice().subtract(selectedTask.getFinalPrice()).setScale(2, RoundingMode.HALF_UP);
			newTasks.remove(taskId);
			newTask.setLabourPrice(labourPrice);
			newTask.setTotalPrice(totalPrice);
			setNewTask(task);
			//SL-19820
			//getSession().setAttribute(Constants.SCOPE_CHANGE_TASKS, newTasks);
			getSession().setAttribute(Constants.SCOPE_CHANGE_TASKS+"_"+soId, newTasks);
			setAttribute(Constants.SCOPE_CHANGE_TASKS, newTasks);
		} catch (Exception e) {
			SOWError error = new SOWError(null,getMessage("manage_scope.delete.error"),"E4");
			taskErrors.add(error);
			newTask.setTaskErrors(taskErrors);
		}

		return SUCCESS;
	}
	
	private boolean isTaskFormValid(ManageTaskVO task){
		if(task.getSku().equals("-1")){
			SOWError error = new SOWError(null,getMessage("manage_scope.sku.required"),"E2");
			taskErrors.add(error);
			newTask.setTaskErrors(taskErrors);
			return false; 
		}
		if(task.getSkill() == null || StringUtils.isBlank(task.getSkill()) || task.getSkill().equals("-1")){
			SOWError error = new SOWError(null,getMessage("manage_scope.skill.required"),"E2");
			taskErrors.add(error);
			newTask.setTaskErrors(taskErrors);
			return false;
		}
		if((task.getPriceInteger() == null || task.getPriceInteger().equals("")) && (task.getPriceFraction()== null || task.getPriceFraction().equals(""))){
			SOWError error = new SOWError(null,getMessage("manage_scope.price.required"),"E2");
			taskErrors.add(error);
			newTask.setTaskErrors(taskErrors);
			return false;
		}else if((task.getPriceInteger() == null || task.getPriceInteger().equals("")) || (task.getPriceFraction()== null || task.getPriceFraction().equals(""))){
			SOWError error = new SOWError(null,getMessage("manage_scope.price.invalid"),"E2");
			taskErrors.add(error);
			newTask.setTaskErrors(taskErrors);
			return false;
		}
		if(task.getTaskName() == null || StringUtils.isBlank(task.getTaskName())){
			SOWError error = new SOWError(null,getMessage("manage_scope.name.required"),"E2");
			taskErrors.add(error);
			newTask.setTaskErrors(taskErrors);
			//addActionError("Task Name is required.");
			return false;
		}		
		if(task.getTaskComments() == null || StringUtils.isBlank(task.getTaskComments())){
			SOWError error = new SOWError(null,getMessage("manage_scope.task_comments.required"),"E2");
			taskErrors.add(error);
			newTask.setTaskErrors(taskErrors);
			return false;
		}else if(task.getTaskComments().length()>600){
			SOWError error = new SOWError(null,getMessage("manage_scope.task_comments.maxlength"),"E2");
			taskErrors.add(error);
			newTask.setTaskErrors(taskErrors);
			return false;
		}
		return true;
	}
	
	public String selectSku(){
		String skuId = getRequest().getParameter("skuId");		
		newTask = getModel();
		if(skuId.equals("0")){
			String mainCat = getRequest().getParameter(SO_MAIN_CAT_ID);
			if(null!=mainCat && !StringUtils.isBlank(mainCat)){
				Integer mainCategory = Integer.parseInt(mainCat);
				List<LookupServiceType> mainCategorySkills = manageScopeService.fetchSkills(mainCategory);
				List<LookupVO> skills = new ArrayList<LookupVO>();
				for(LookupServiceType serviceType :mainCategorySkills){
					LookupVO skill = new LookupVO();
					skill.setId(serviceType.getId());
					skill.setDescr(serviceType.getDescription());
					skills.add(skill);
				}
				newTask.setSkillNodeId(Long.parseLong(mainCat));
				newTask.setMainCategorySkills(skills);
				newTask.setSkuId(skuId);	
				
			}
			return SUCCESS;
		}
		@SuppressWarnings("unchecked")
		BuyerOrderSku sku = manageScopeService.fetchSKU(Integer.valueOf(skuId));
		newTask.setSku(sku.getSku());
		BigDecimal price = sku.getRetailPrice().setScale(2);
		if(null != price){
			String priceString = price.toString();
			String[] priceArray = priceString.split("\\.");
			newTask.setPriceInteger(priceArray[0]);
			newTask.setPriceFraction(priceArray[1]);
		}
		//Based on the assumption that one SKU will have only one skill type
		BuyerOrderSkuTask task = sku.getBuyerSkuTaskList().get(0);
		newTask.setTaskName(task.getTaskName());
		newTask.setTaskComments(task.getTaskComments());
		newTask.setServiceTypeTemplateId(task.getServiceTypeTemplateId());
		newTask.setSkill(task.getLuServiceTypeTemplate().getDescription());
		if(null!=task.getSkillNodeId()){
			Integer mainCategory = (Integer)task.getSkillNodeId().intValue();
			List<LookupServiceType> mainCategorySkills = manageScopeService.fetchSkills(mainCategory);
			List<LookupVO> skills = new ArrayList<LookupVO>();
			for(LookupServiceType serviceType :mainCategorySkills){
				LookupVO skill = new LookupVO();
				skill.setId(serviceType.getId());
				skill.setDescr(serviceType.getDescription());
				skills.add(skill);
			}
			newTask.setMainCategorySkills(skills);
		}
		
		newTask.setSkillNodeId(task.getSkillNodeId());
		newTask.setSkuId(skuId);	

		return SUCCESS;
	}
	
	
	
	public String updateTaskPrice(){
		ManageTaskVO task = getModel();
		@SuppressWarnings("unchecked")
		//SL-19820
		String soId = getParameter(SO_ID);
		setAttribute(SO_ID, soId);
		//HashMap<Integer, ManageTaskVO> newTasks = (HashMap<Integer, ManageTaskVO>) getSession()
		        //.getAttribute(Constants.SCOPE_CHANGE_TASKS);
		HashMap<Integer, ManageTaskVO> newTasks = (HashMap<Integer, ManageTaskVO>) getSession()
        	.getAttribute(Constants.SCOPE_CHANGE_TASKS+"_"+soId);
		ManageTaskVO selectedTask = newTasks.get(task.getId());
		StringBuilder price = new StringBuilder();
		price.append(task.getPriceInteger()).append('.').append(task.getPriceFraction());
		BigDecimal finalPrice = new BigDecimal(price.toString());
		labourPrice = task.getMaxLabourPrice().subtract(selectedTask.getFinalPrice()).add(finalPrice);
		totalPrice = task.getMaxTotalPrice().subtract(selectedTask.getFinalPrice()).add(finalPrice);
		selectedTask.setPriceFraction(task.getPriceFraction());
		selectedTask.setPriceInteger(task.getPriceInteger());
		selectedTask.setFinalPrice(finalPrice);
		selectedTask.setLabourPrice(labourPrice);
		selectedTask.setTotalPrice(totalPrice);
		setNewTask(selectedTask);
		newTasks.put(selectedTask.getId(), selectedTask);
		
		task.setLabourPrice(labourPrice);
		task.setTotalPrice(totalPrice);
		setNewTask(task);
		//SL_19820
		//getSession().setAttribute(Constants.SCOPE_CHANGE_TASKS, newTasks);
		getSession().setAttribute(Constants.SCOPE_CHANGE_TASKS+"_"+soId, newTasks);
		setAttribute(Constants.SCOPE_CHANGE_TASKS, newTasks);
		return SUCCESS;
	}
	
	public String saveChanges() {
		try {
			String fromCancel = null;
			if(StringUtils.isNotBlank(getRequest().getParameter(FROM_CANCEL))){
				fromCancel = (String)getRequest().getParameter(FROM_CANCEL);
			}
			@SuppressWarnings("unchecked")			
			//SL-19820
			String soId = getParameter(SO_ID);
			setAttribute(SO_ID, soId);
			//HashMap<Integer, ManageTaskVO> newTasks = (HashMap<Integer, ManageTaskVO>) getSession()
			        //.getAttribute(Constants.SCOPE_CHANGE_TASKS);
			HashMap<Integer, ManageTaskVO> newTasks = (HashMap<Integer, ManageTaskVO>) getSession()
	        	.getAttribute(Constants.SCOPE_CHANGE_TASKS+"_"+soId);
			if(null == newTasks || newTasks.isEmpty()){
				SOWError error = new SOWError(null,getMessage("manage_scope.newTask.required"),"E1");
				taskErrors.add(error);
				newTask.setTaskErrors(taskErrors);
				return SUCCESS;
			}
			SecurityContext securityContext = (SecurityContext) getSession().getAttribute(SECURITY_KEY);
			double spendLimitperSO = securityContext.getMaxSpendLimitPerSO();
			BigDecimal upFundCap = new BigDecimal(spendLimitperSO).setScale(2,RoundingMode.HALF_UP);
			String totalPrice = getRequest().getParameter(TOTAL_PRICE);
			String initPrice = getRequest().getParameter(INIT_PRICE);
			String scopeChangeComments = getRequest().getParameter(SCOPE_CHANGE_COMMENTS);
			Integer scopeChangeReason = Integer.valueOf(getRequest().getParameter(SCOPE_CHANGE_REASON));
			if(scopeChangeReason.equals(-1)){
				SOWError error = new SOWError(null,getMessage("manage_scope.reason.required"),"E1");
				taskErrors.add(error);
				newTask.setTaskErrors(taskErrors);
				return SUCCESS;
			}
			if("".equals(scopeChangeComments)){
				SOWError error = new SOWError(null,getMessage("manage_scope.scope_comments.required"),"E1");
				taskErrors.add(error);
				newTask.setTaskErrors(taskErrors);
				return SUCCESS;
			}else if(null != scopeChangeComments && scopeChangeComments.length()>400){
				SOWError error = new SOWError(null,getMessage("manage_scope.scope_comments.maxlength"),"E1");
				taskErrors.add(error);
				newTask.setTaskErrors(taskErrors);
				return SUCCESS;
			}
			
			BigDecimal newMax = new BigDecimal(totalPrice).setScale(2,RoundingMode.HALF_UP);
			BigDecimal oldMax = new BigDecimal(initPrice).setScale(2);
			BigDecimal amountRequired = newMax.subtract(oldMax);
			if (upFundCap.compareTo(new BigDecimal(0.00)) != 0 && amountRequired.compareTo(upFundCap) == 1) {
				SOWError error = new SOWError(null,getMessage("manage_scope.spendlimit.exceeded"),"E3");
				taskErrors.add(error);
				newTask.setTaskErrors(taskErrors);
			}
			//code to check funding type Id
			//SL-19820
			//String soId = getCurrentServiceOrderFromSession().getId();
            Integer soFundingTypeId=manageScopeService.getSoLevelFundingTypeId(soId);
            boolean isSoLevelAutoACH=false;
            if(soFundingTypeId.intValue()==40 || soFundingTypeId.intValue()==90){
            	isSoLevelAutoACH=true;
            }
            if (!isSoLevelAutoACH) {
				Integer buyerId = securityContext.getCompanyId();
				BigDecimal availableBalance = manageScopeService.getAvailableBalance(buyerId);
				if(amountRequired.compareTo(availableBalance) == 1){
					SOWError error = new SOWError(null,getMessage("manage_scope.insufficient_balance"),"E3");
					taskErrors.add(error);
					newTask.setTaskErrors(taskErrors);
				}
			}
			if(!taskErrors.isEmpty()){
				return SUCCESS;
			}
		
			if(null == fromCancel || !"true".equalsIgnoreCase(fromCancel)){
				 prResp=manageScopeService.changeScope(soId, newTasks, scopeChangeReason, scopeChangeComments, securityContext);
				 strErrorCode = prResp.getCode();
				 strErrorMessage = prResp.getMessages().get(0);
			    if (strErrorCode.equalsIgnoreCase(SYSTEM_ERROR_RC))
				   {	
					strResponse = ERROR; 
					//SL-19820
					//this.setReturnURL(SOD_ACTION);
					this.setReturnURL(SOD_ACTION+"?soId="+soId);
					this.setErrorMessage(strErrorMessage);
				}else {
					strResponse = SUCCESS;
					strErrorMessage="Your request was successfully saved";
					//Sl-19820
					//getSession().setAttribute(Constants.SESSION.SOD_MSG, strErrorMessage);
					getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+soId, strErrorMessage);
					setAttribute(Constants.SESSION.SOD_MSG, strErrorMessage);
				}
			}
			
		} catch (Exception e) {
			SOWError error = new SOWError(null,getMessage("manage_scope.save.error"),"E1");
			taskErrors.add(error);
		}
		return SUCCESS;
	}
	
	private String getMessage(String key){
		ServletContext servletContext = ServletActionContext.getServletContext();
		LocalizationContext localizationContext = (LocalizationContext)servletContext.getAttribute("serviceliveCopyBundle");
		String msg = localizationContext.getResourceBundle().getString(key);
		return msg;
	}
	
	//SL-19820
	private ServiceOrderDTO getServiceOrder(String soId) {
		ServiceOrderDTO dto = null;
		Integer roleId = 3;
		try{
			dto = detailsDelegate.getServiceOrder(soId, roleId, null);
		}catch(Exception e){
			logger.error("Error while fetching service order object");
		}
		return dto;
	}

	public List<SOTaskDTO> getTasks() {
		return tasks;
	}

	public void setTasks(List<SOTaskDTO> tasks) {
		this.tasks = tasks;
	}
	
	public ManageTaskVO getModel() {
		return newTask;
	}

	public List<SOWError> getTaskErrors() {
		return taskErrors;
	}

	public void setTaskErrors(List<SOWError> taskErrors) {
		this.taskErrors = taskErrors;
	}

	public ManageTaskVO getNewTask() {
		return newTask;
	}

	public void setNewTask(ManageTaskVO newTask) {
		this.newTask = newTask;
	}

	public BigDecimal getPartsPrice() {
		return partsPrice;
	}

	public void setPartsPrice(BigDecimal partsPrice) {
		this.partsPrice = partsPrice;
	}

	public BigDecimal getPermitPrice() {
		return permitPrice;
	}

	public void setPermitPrice(BigDecimal permitPrice) {
		this.permitPrice = permitPrice;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getLabourPrice() {
		return labourPrice;
	}

	public void setLabourPrice(BigDecimal labourPrice) {
		this.labourPrice = labourPrice;
	}

	public List<ReasonCode> getReasonCodes() {
		return reasonCodes;
	}

	public void setReasonCodes(List<ReasonCode> reasonCodes) {
		this.reasonCodes = reasonCodes;
	}
	
	public ISODetailsDelegate getDetailsDelegate() {
		return detailsDelegate;
	}

	public void setDetailsDelegate(ISODetailsDelegate detailsDelegate) {
		this.detailsDelegate = detailsDelegate;
	}

}