package com.newco.marketplace.web.action.powerbuyer;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.BuyerQueueVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.action.base.SLSimpleBaseAction;
import com.newco.marketplace.web.delegates.IWorkflowQueueDelegate;
import com.newco.marketplace.web.dto.WFMCategoryDTO;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
//SLT-1615
public class PBWorkflowConfigureAction extends SLSimpleBaseAction implements Preparable, ValidationAware, ModelDriven<WFMCategoryDTO>{
	
	private static final long serialVersionUID = 1L;
	private WFMCategoryDTO wfmQueueDto = new WFMCategoryDTO();
	private IWorkflowQueueDelegate wfmDelegate;
	HttpServletRequest request;
	private static final String QUEUEID="queue_cb";
	private static final String STANDARD_QUEUEID="queue_stnd";
	Logger logger = Logger.getLogger(PBWorkflowConfigureAction.class);

	@Override
	public WFMCategoryDTO getModel() {
		
		return wfmQueueDto;
	}

	@Override
	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();	
	}
	
	public String displayPage() throws Exception
	{	
		SecurityContext securityContext = (SecurityContext) _commonCriteria.getSecurityContext();
		Integer buyerId = securityContext.getCompanyId();
		setAttribute("saved", false);
		setAttribute("initialLoad",true);
		return loadQueues(buyerId);
		
	}
	
	public String loadQueues(Integer buyerId){
			
		try {
			if(buyerId != null){
				logger.debug("Loading queues for buyer: "+ buyerId);
				Map<String,List<WFMCategoryDTO>> wfmQueues = wfmDelegate.getWfmQueueDetails(buyerId);
				setAttribute("wfmQueues", wfmQueues.get(Constants.STANDARD_QUEUES));
				
				List<WFMCategoryDTO> buyerQueues = wfmQueues.get(Constants.BUYER_QUEUES);
				if(buyerQueues == null || buyerQueues.size() == 0){
					setAttribute("buyerQueueConfigured", false);
				}else{
					setAttribute("buyerQueueConfigured", true);
				}
				setAttribute("wfmBuyerQueues", buyerQueues);
			}
			return SUCCESS;
		} catch (BusinessServiceException e) {
			return ERROR;
		}
	}
	
	public String save() throws Exception {
		
		SecurityContext securityContext = (SecurityContext) _commonCriteria.getSecurityContext();
		Integer buyerId = securityContext.getCompanyId();
		
		HttpServletRequest request = getRequest();
		Enumeration<String> e = request.getParameterNames();
		
		List<BuyerQueueVO> wfmBuyerQueues = new ArrayList<BuyerQueueVO>();
		List<Integer> queueIdList = new ArrayList<Integer>();
		
		String name = null;
		while (e.hasMoreElements()) {
			name = (String) e.nextElement();
			if (StringUtils.contains(name, QUEUEID)) {
				Integer queueId = new Integer(request.getParameter(name));
				queueIdList.add(queueId);
			} else if (StringUtils.contains(name, STANDARD_QUEUEID)) {
				Integer queueId = new Integer(request.getParameter(name));
				wfmBuyerQueues.add(createBuyerQueue(queueId, buyerId));
				queueIdList.add(queueId);
			}
		}
		logger.debug("Saving queues for buyer: "+ buyerId);
		wfmDelegate.saveWfmBuyerQueues(wfmBuyerQueues, queueIdList, buyerId);
		return "save";
	}
	
	public String execute(){
		setAttribute("saved", true);
		setAttribute("initialLoad",false);
		SecurityContext securityContext = (SecurityContext) _commonCriteria.getSecurityContext();
		Integer buyerId = securityContext.getCompanyId();
		loadQueues(buyerId);
		
		return SUCCESS;
	}
	
	public WFMCategoryDTO getWfmQueueDto() {
		return wfmQueueDto;
	}

	public void setWfmQueueDto(WFMCategoryDTO wfmQueueDto) {
		this.wfmQueueDto = wfmQueueDto;
	}

	public IWorkflowQueueDelegate getWfmDelegate() {
		return wfmDelegate;
	}

	public void setWfmDelegate(IWorkflowQueueDelegate wfmDelegate) {
		this.wfmDelegate = wfmDelegate;
	}
	
	// SLT-1894 START
	private BuyerQueueVO createBuyerQueue(Integer queueId, Integer buyerId) {

		BuyerQueueVO wfmBuyerQueueVO = new BuyerQueueVO();
		wfmBuyerQueueVO.setQueueId(queueId);
		wfmBuyerQueueVO.setBuyerId(buyerId);
		wfmBuyerQueueVO.setActiveInd(1);
		wfmBuyerQueueVO.setVisibilityInd(1);
		return wfmBuyerQueueVO;
	}
	// SLT-1894 END

}
