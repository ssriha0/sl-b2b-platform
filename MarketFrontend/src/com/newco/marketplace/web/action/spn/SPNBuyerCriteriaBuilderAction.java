package com.newco.marketplace.web.action.spn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.dto.vo.skillTree.ServiceTypesVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.dto.SOTaskDTO;
import com.newco.marketplace.web.dto.SPNBuilderFormDTO;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class SPNBuyerCriteriaBuilderAction extends SPNBaseAction implements
		Preparable, ModelDriven<SPNBuilderFormDTO> {
	
	private static final long serialVersionUID = 7554315313327686916L;

	private SPNBuilderFormDTO spnCriteria = new SPNBuilderFormDTO();

	private static final Logger logger = Logger
			.getLogger("SPNBuyerCriteriaBuilderAction");

	public SPNBuyerCriteriaBuilderAction(ISOWizardFetchDelegate fetchDelegate) {
		this.fetchDelegate = fetchDelegate;
	}

	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		//maps();
		populateLookup();
	
	}

	public String displayPage() throws Exception {
		SPNBuilderFormDTO modelScopeDTO = getModel();
		if(modelScopeDTO != null && modelScopeDTO.getSpnId() != null) {
			if (modelScopeDTO.getTheCriteria().getResourceCredential() != null
					&& !modelScopeDTO.getTheCriteria().getResourceCredential().equals(new Integer(-1))) {
				List theList1 = 
						loadSubCatData(modelScopeDTO.getTheCriteria().getResourceCredential());
				setCommonResourceList(theList1);
			}
			
			
			if (modelScopeDTO.getTheCriteria().getCredentials() != null
					&& !modelScopeDTO.getTheCriteria().getCredentials().equals(new Integer(-1))) {
				List theList2 = 
					loadSubCatData(modelScopeDTO.getTheCriteria().getCredentials());
				setCommonCompanyCredList(theList2);
			}
			
		}
		setModel(modelScopeDTO);
		createEntryPoint();
	
		return SUCCESS;
	}


	
	public String softPersistCriteria() throws Exception {
		SPNBuilderFormDTO model = getModel();
		
		SPNBuilderFormDTO dto = (SPNBuilderFormDTO)getSession().getAttribute("spnBuilderFormInfo");
		dto.setTheCriteria(model.getTheCriteria());
		//dto.getTheCriteria().setMainServiceCategoryName("Test of Name Setting");
		
		getSession().setAttribute("spnBuilderFormInfo", dto);
		
		return TO_BUILDER;
	}

	
	/***************************************************************************
	 * Public Methods
	 **************************************************************************/

	public String createEntryPoint() throws Exception {
		skillTreeMainCat = fetchDelegate.getSkillTreeMainCategories();
		Iterator<SkillNodeVO> i = skillTreeMainCat.iterator();
		SkillNodeVO singleSkillNode;
		Map<Integer, String> mainServiceCategoryNamesMap = new HashMap<Integer, String>();
		skillSelectionMap = new HashMap();
		while (i.hasNext()) {
			singleSkillNode = i.next();
			mainServiceCategoryNamesMap.put(singleSkillNode.getNodeId(),
					singleSkillNode.getNodeName());
			if (singleSkillNode.getServiceTypes() != null) {
				skillSelectionMap.put(singleSkillNode.getNodeId(),
						new ArrayList(singleSkillNode.getServiceTypes()));
			}
		}

		getSession().setAttribute("mainServiceCategoryNamesMap",
				mainServiceCategoryNamesMap);
		getSession().setAttribute("mainServiceCategory", skillTreeMainCat);
		getSession().setAttribute("skillSelectionMap", skillSelectionMap);
		return SUCCESS;
	}

	public String loadSkillsAndCategories() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		StringBuffer xmlString = new StringBuffer("<message_output>");

		Integer req = (Integer) getModel().getTheCriteria().getMainServiceCategoryId();
		if (req != null && req > -1) {
			xmlString.append("<mainCategorySelected>" + req
					+ "</mainCategorySelected>");
			getSession().setAttribute("mainServiceCategoryId", req);
		}
		try {
			// scopeOfWorkDTO = getModel();
			getModel().getTheCriteria().setMainServiceCategoryId(req);
			Map<Integer, String> mainServiceCategoryNamesMap = (Map<Integer, String>) getSession()
					.getAttribute("mainServiceCategoryNamesMap");
			getModel().getTheCriteria().setMainServiceCategoryName(
					mainServiceCategoryNamesMap.get(req));
			skillSelection = getSkillSelectionMap().get(req);
			ServiceTypesVO singleSkill = new ServiceTypesVO();

			for (int i = 0; i < skillSelection.size(); i++) {
				singleSkill = skillSelection.get(i);
				xmlString.append("<skill>");
				xmlString.append("<skillId>" + singleSkill.getServiceTypeId()
						+ "</skillId>");
				xmlString.append("<skillName>"
						+ singleSkill.getDescription().replaceAll("&", "&amp;")
						+ "</skillName>");
				xmlString.append("</skill>");
			}
			getSession().setAttribute("skillSelection", skillSelection);
			setCategorySelection(fetchDelegate
					.getSkillTreeCategoriesOrSubCategories(getModel().getTheCriteria()
							.getMainServiceCategoryId()));

			if (getCategorySelection() != null
					&& !getCategorySelection().isEmpty()) {
				SkillNodeVO singleCategory = new SkillNodeVO();
				getSession().setAttribute("categorySelection",
						getCategorySelection());
				for (int i = 0; i < getCategorySelection().size(); i++) {
					singleCategory = getCategorySelection().get(i);
					xmlString.append("<cat>");
					xmlString.append("<categoryId>"
							+ singleCategory.getNodeId() + "</categoryId>");
					xmlString.append("<categoryName>"
							+ singleCategory.getNodeName().replaceAll("&",
									"&amp;") + "</categoryName>");
					xmlString.append("</cat>");
				}
			}
			xmlString.append("</message_output>");// ------------End XML
													// String
			response.getWriter().write(xmlString.toString());
			getModel().getTheCriteria().setTheResourceBundle(getTexts("servicelive"));
		} catch (Throwable t) {
			logger.error("Could not set skills and categories properly", t);
		}
		return NONE;
	}

	public String loadSubcategories() {
		ArrayList<SOTaskDTO> taskListToUpdate = null;
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		StringBuffer xmlString = new StringBuffer("<message_output>");// ------------Start
																		// XML
																		// String
		spnCriteria = getModel();
		if (spnCriteria.getTheCriteria().getTasks() != null) {
			getSession().setAttribute("taskList", spnCriteria.getTheCriteria().getTasks());
			taskListToUpdate = (ArrayList<SOTaskDTO>) spnCriteria.getTheCriteria().getTasks();
		} else if (getSession().getAttribute("taskList") != null) {
			taskListToUpdate = (ArrayList<SOTaskDTO>) getSession()
					.getAttribute("taskList");
		}
		Integer selectedCategoryId = spnCriteria.getTheCriteria().getSelectedCategoryId();
		SkillNodeVO singleSubCategory = new SkillNodeVO();

		if (selectedCategoryId != null && getTaskIndex() != null
				&& getTaskIndex() != -1) {
			SOTaskDTO updateSOTask = null;
			if (taskListToUpdate == null) {
				taskListToUpdate = new ArrayList();
				updateSOTask = new SOTaskDTO();
				updateSOTask.setCategoryId(selectedCategoryId);
				taskListToUpdate.add(updateSOTask);
			} else {
				updateSOTask = taskListToUpdate.get(getTaskIndex());
				updateSOTask.setCategoryId(selectedCategoryId);
				taskListToUpdate.set(getTaskIndex(), updateSOTask);
			}
			getSession().setAttribute("taskList", taskListToUpdate);
			xmlString.append("<categorySelectedId>" + selectedCategoryId
					+ "</categorySelectedId>");
			xmlString.append("<taskIndex>" + getTaskIndex() + "</taskIndex>");
		}
		try {

			/*
			 * Let's do some session magic. When a user selects a category,
			 * there may or may not be a list of sub-categories associated with
			 * that selection. Let's save this possible sub-category list in a
			 * session map. the SOTaskDTO's getCategories() and
			 * getSubCategories() will be involved
			 */
			Map<Integer, List<SkillNodeVO>> subCategorySelectionSessionMap = (TreeMap) getSession()
					.getAttribute("subCategorySelectionSessionMap");
			if (subCategorySelectionSessionMap == null) {
				subCategorySelectionSessionMap = new TreeMap();

			}

			if (selectedCategoryId != null && selectedCategoryId != -1) {
				subCategorySelection = fetchDelegate
						.getSkillTreeCategoriesOrSubCategories(selectedCategoryId);
			}

			if (!subCategorySelection.isEmpty()) {

				for (int i = 0; i < subCategorySelection.size(); i++) {
					singleSubCategory = subCategorySelection.get(i);
					xmlString.append("<subcat>");
					xmlString.append("<subCategoryId>"
							+ singleSubCategory.getNodeId()
							+ "</subCategoryId>");
					xmlString.append("<subCategoryName>"
							+ singleSubCategory.getNodeName().replaceAll("&",
									"&amp;") + "</subCategoryName>");
					xmlString.append("</subcat>");

				}

			} else {
				subCategorySelection = new ArrayList<SkillNodeVO>();
			}
			List<SOTaskDTO> tempTasks = spnCriteria.getTheCriteria().getTasks();
			if (tempTasks != null) {
				SOTaskDTO tempTaskDTO = tempTasks.get(getTaskIndex());
				tempTaskDTO.setSubCategoryList(subCategorySelection);
				tempTasks.set(getTaskIndex(), tempTaskDTO);
				spnCriteria.getTheCriteria().addTaskList(tempTasks);
			} else {

			}
			subCategorySelectionSessionMap.put(selectedCategoryId,
					subCategorySelection);
			getSession().setAttribute("subCategorySelectionSessionMap",
					subCategorySelectionSessionMap);

			xmlString.append("</message_output>");// ------------End XML
													// String
			response.getWriter().write(xmlString.toString());
		} catch (Throwable t) {
			logger.error("Could not set subcategories properly", t);
		}

		return NONE;
	}

	public String loadResourceCredentialsSubCat() throws Exception {
		return credentialCategory();
	}

	public String addSPNTask() throws Exception {
		SPNBuilderFormDTO theSpnCriteria = getModel();
		SOTaskDTO task;
		List<SOTaskDTO> taskList = theSpnCriteria.getTheCriteria().getTasks();
		if (taskList != null && taskList.size() > 0) {
			Iterator<SOTaskDTO> i = taskList.iterator();
			while (i.hasNext()) {
				task = i.next();
				task.setIsFreshTask(false);
			}
		}
		theSpnCriteria.getTheCriteria().addTask(new SOTaskDTO());
		if (theSpnCriteria.getTheCriteria().getResourceCredential() != null
				&& !theSpnCriteria.getTheCriteria().getResourceCredential().equals(new Integer(-1))) {
			setCommonResourceList((List)getSession().getAttribute(
							ProviderConstants.CREDENTIAL_CATEGORY_LIST));
		} 
		
		if (theSpnCriteria.getTheCriteria().getCredentials() != null
				&& !theSpnCriteria.getTheCriteria().getCredentials().equals(new Integer(-1))) {
			setCommonCompanyCredList(((List)getSession().getAttribute(
							ProviderConstants.CREDENTIAL_CATEGORY_LIST)));
		} 
		setModel(theSpnCriteria);
		prime();
		// return createEntryPoint();
		return SUCCESS;
	}

	private void prime() throws Exception {
		if (getModel().getTheCriteria().getMainServiceCategoryId() != null
				&& getModel().getTheCriteria().getMainServiceCategoryId() != -1) {
			for (int j = 0; j < getSkillTreeMainCat().size(); j++) {
				SkillNodeVO skillVo = getSkillTreeMainCat().get(j);
				if (skillVo.getNodeId().intValue() == getModel().getTheCriteria()
						.getMainServiceCategoryId().intValue()) {
					getModel().getTheCriteria()
							.setMainServiceCategoryName(skillVo.getNodeName());
					break;
				}
			}

			setCategorySelection(fetchDelegate
					.getSkillTreeCategoriesOrSubCategories(spnCriteria.getTheCriteria()
							.getMainServiceCategoryId()));
			getSession().setAttribute("categorySelection",
					getCategorySelection());
			skillSelection = getSkillSelectionMap().get(
					spnCriteria.getTheCriteria().getMainServiceCategoryId());
			getSession().setAttribute("skillSelection", skillSelection);
			Iterator<SOTaskDTO> initTasks = getModel().getTheCriteria().getTasks().iterator();
			SOTaskDTO singleTask;
			while (initTasks.hasNext()) {
				singleTask = initTasks.next();
				if (singleTask.getSubCategoryId() != null
						&& !singleTask.getSubCategoryId().equals(
								new Integer(-1))) {
					singleTask.setSubCategoryList(fetchDelegate
							.getSkillTreeCategoriesOrSubCategories(singleTask
									.getCategoryId()));

				} else if (singleTask.getCategoryId() != null
						&& !singleTask.getCategoryId().equals(new Integer(-1))) {

				}
			}
		}
	}

	public String deleteTask() throws BusinessServiceException {
		SPNBuilderFormDTO scopeOfWorkDTO2 = getModel();
		if(scopeOfWorkDTO2 == null)
			return null;
		List<SOTaskDTO> taskList = scopeOfWorkDTO2.getTheCriteria().getTasks();
		if(taskList != null && taskList.size()>0){
			taskList.remove(getDelIndex().intValue());
		}
		//scopeOfWorkDTO2.getTheCriteria().addTaskList(taskList);
		return SUCCESS;
	}

	public SPNBuilderFormDTO getModel() {
		
		return (SPNBuilderFormDTO) getSession().getAttribute("spnBuilderFormInfo");
	}

	public void setModel(SPNBuilderFormDTO criteria) {
		this.spnCriteria = criteria;
		getSession().setAttribute("spnBuilderFormInfo", criteria);
		
	}
	
	public List getCommonResourceList() {
		return commonResourceList;
	}


	public void setCommonResourceList(List commonResourceList) {
		this.commonResourceList = commonResourceList;
	}


	public List getCommonCompanyCredList() {
		return commonCompanyCredList;
	}


	public void setCommonCompanyCredList(List commonCompanyCredList) {
		this.commonCompanyCredList = commonCompanyCredList;
	}


}
