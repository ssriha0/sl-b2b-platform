package com.newco.marketplace.web.action.powerbuyer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.powerbuyer.ClaimVO;
import com.newco.marketplace.dto.vo.powerbuyer.RequeVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.IPowerBuyerDelegate;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.dto.PBClaimedOrderDTO;
import com.newco.marketplace.web.dto.PBClaimedTabDTO;
import com.newco.marketplace.web.dto.ServiceOrderNoteDTO;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class PBClaimedTabAction extends SLBaseAction implements Preparable,ModelDriven<PBClaimedTabDTO>
{
	private static final long serialVersionUID = 1L;
	private IPowerBuyerDelegate pbDelegate;
	private PBClaimedTabDTO pbClaimedTabDTO = null;
	private HashMap completedRadioMap = new HashMap();
	private ISOMonitorDelegate soMonitorDelegate;	
	private InputStream inputStream;
    public InputStream getInputStream() {
        return inputStream;
    }
    
    //SL-19820
    String showErrors;
    String pbwfMessage;
	
	public String getPbwfMessage() {
		return pbwfMessage;
	}

	public void setPbwfMessage(String pbwfMessage) {
		this.pbwfMessage = pbwfMessage;
	}

	public String getShowErrors() {
		return showErrors;
	}

	public void setShowErrors(String showErrors) {
		this.showErrors = showErrors;
	}

	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();
		
		// Init radio buttons
		completedRadioMap.put(1, "I have completed the action");
		completedRadioMap.put(0, "I cannot complete the action at this time");
		//SL-19820
		//pbClaimedTabDTO=(PBClaimedTabDTO) getSession().getAttribute("pbClaimedTabDTO");
		if(pbClaimedTabDTO==null){
			pbClaimedTabDTO=new PBClaimedTabDTO();
			//getSession().setAttribute("pbClaimedTabDTO",pbClaimedTabDTO);
		}
		String pbwfMessage = getParameter(Constants.SESSION.PB_WF_MESSAGE);
		if(StringUtils.isNotBlank(pbwfMessage)){
			setAttribute(Constants.SESSION.PB_WF_MESSAGE, pbwfMessage);
		}
		
		String showErrors = getParameter("ShowErrors");
		if("true".equals(showErrors)){
			setAttribute("ShowErrors", "true");
		}
	}

	public String execute() throws Exception
	{
	    
		List<ClaimVO> claimedSO = pbDelegate.getClaimedSO(get_commonCriteria().getVendBuyerResId());
		List<PBClaimedOrderDTO> list = new ArrayList<PBClaimedOrderDTO>();
		
		PBClaimedOrderDTO claimDTO;
		String soId=null;
		if(claimedSO.isEmpty())
			pbClaimedTabDTO.reset();
		if(!pbClaimedTabDTO.getAllMessages().isEmpty()){
			soId=pbClaimedTabDTO.getSoId();
		}
		boolean exists=false;
		Set<String> seen = new HashSet<String>();
		for (ClaimVO claimVO : claimedSO) {
			// remove duplicate SOs
			if (seen.contains(claimVO.getSoId()))
				continue;
			else
				seen.add(claimVO.getSoId());

			claimDTO = new PBClaimedOrderDTO();
			claimDTO.setSoId(claimVO.getSoId());
			if(soId!=null && soId.equals(claimVO.getSoId())){
				claimDTO.setSelected("checked");exists=true;
			}else{
				claimDTO.setSelected(null);
			}
			claimDTO.setTitle(claimVO.getSoTitle());
			claimDTO.setStatusId(claimVO.getStatusId());
			claimDTO.setDateTime(claimVO.getClaimDate());
			claimDTO.setDestinationTab(claimVO.getQueueDestinationTab());
			claimDTO.setParentGroupId(claimVO.getParentGroupId());
					
			list.add(claimDTO);
		}
		if(!StringUtils.isBlank(pbClaimedTabDTO.getSoId()) && !exists){
			pbClaimedTabDTO.reset();
		}
		//SL-19820
		//if(null==getSession().getAttribute("ShowErrors")||"".equals(getSession().getAttribute("ShowErrors"))){	
		if(null==getParameter("ShowErrors")||"".equals(getParameter("ShowErrors"))){	
			pbClaimedTabDTO.reset();
			for (PBClaimedOrderDTO  iList: list){				
				iList.setSelected(null);				
			}
		}
		setAttribute("orderList", list);
		setAttribute("todaysDate", getDateString());
		return "success";
	}

	
	public String submit() throws Exception
	{
		ClaimVO claimVO= new ClaimVO();
		boolean active=true;
		claimVO.setResourceId(get_commonCriteria().getVendBuyerResId());
		claimVO.setSoId(pbClaimedTabDTO.getSoId());
		if(!StringUtils.isBlank(pbClaimedTabDTO.getSoId()) && !pbDelegate.isCurrentUserTheClaimedUser(pbClaimedTabDTO.getSoId(), claimVO.getResourceId())){
			//SL-19820
			//getSession().setAttribute(Constants.SESSION.PB_WF_MESSAGE, "pb.claim.unclaim.inactive");
			pbwfMessage = "pb.claim.unclaim.inactive";
			setAttribute(Constants.SESSION.PB_WF_MESSAGE, "pb.claim.unclaim.inactive");
			active=false;			
		}		
		if(pbClaimedTabDTO.validateErrors()){		
			//SL-19820
			//getSession().setAttribute("ShowErrors","true");
			showErrors = "true";
			setAttribute("ShowErrors","true");
			return GOTO_COMMON_POWER_BUYER_CONTROLLER;
		}
		
		boolean status=false;		
		if(active && pbClaimedTabDTO.getCompletedRadio().equals("1")){
			claimVO.setReasonCode(Constants.CLAIM_HISTORY_CODE.SELF_UNCLAIM_COMPLETE);
			status=pbDelegate.completedClaimTask(pbClaimedTabDTO.getSoId(), get_commonCriteria().getVendBuyerResId(),Constants.CLAIM_HISTORY_CODE.SELF_UNCLAIM_COMPLETE);			
		}
		else if(active && pbClaimedTabDTO.getCompletedRadio().equals("0")){
			if(pbClaimedTabDTO.getDatetime()==null ){
				claimVO.setReasonCode(Constants.CLAIM_HISTORY_CODE.SELF_UNCLAIM_INCOMPLETE);
				status=pbDelegate.completedClaimTask(pbClaimedTabDTO.getSoId(), get_commonCriteria().getVendBuyerResId(),Constants.CLAIM_HISTORY_CODE.SELF_UNCLAIM_INCOMPLETE);
			}else{
				claimVO.setReasonCode(Constants.CLAIM_HISTORY_CODE.SELF_UNCLAIM_REQUED);
				RequeVO requeVO=new RequeVO();	
				requeVO.setResourceId(get_commonCriteria().getVendBuyerResId());
				requeVO.setBuyerId(get_commonCriteria().getCompanyId());
				requeVO.setSoId(pbClaimedTabDTO.getSoId());
				requeVO.setRequeDate(pbClaimedTabDTO.getDatetime());
				requeVO.setRequeTime(pbClaimedTabDTO.getTime());
				status=pbDelegate.requeSO(requeVO);
			}
		}

		ServiceOrderNoteDTO serviceOrderNoteDTO=new ServiceOrderNoteDTO();
		serviceOrderNoteDTO.setMessage(pbClaimedTabDTO.getNote());
		serviceOrderNoteDTO.setSoId(pbClaimedTabDTO.getSoId());
		serviceOrderNoteDTO.setRadioSelection(pbClaimedTabDTO.getRadioSelection());
		if(status)
			serviceOrderNoteDTO.setSubject(Constants.POWERBUYER+" "+Constants.CODEMAP.get(claimVO.getReasonCode()));
		else{
			serviceOrderNoteDTO.setSubject(Constants.POWERBUYER+" "+Constants.CODEMAP.get(Constants.CLAIM_HISTORY_CODE.SELF_UNCLAIM_INACTIVE));
		}
		serviceOrderNoteDTO.setNoteTypeId(SOConstants.INTERNAL_NOTE.intValue());
		serviceOrderNoteDTO.setModifiedBy(get_commonCriteria().getTheUserName());
		LoginCredentialVO lvRoles = get_commonCriteria().getSecurityContext().getRoles();
		
		soMonitorDelegate.serviceOrderAddNote(get_commonCriteria().getVendBuyerResId(), get_commonCriteria().getRoleId(), serviceOrderNoteDTO,lvRoles);
		if(!status){
			//SL-19820
			//getSession().setAttribute(Constants.SESSION.PB_WF_MESSAGE, "pb.claim.unclaim.inactive");
			setAttribute(Constants.SESSION.PB_WF_MESSAGE, "pb.claim.unclaim.inactive");
		}
		pbClaimedTabDTO.reset();
		return GOTO_COMMON_POWER_BUYER_CONTROLLER;		
	}	
	
	public String submitUnClaim() throws Exception {
		Integer resourceId = get_commonCriteria().getVendBuyerResId();
		//SL-19820
		//String soID = (String)getSession().getAttribute(OrderConstants.SO_ID);
		String soID = getParameter("soId");
		//TODO : SL-19820
		//Check the case of grouped order & single order in group
		//String groupSOID = (String)getSession().getAttribute(OrderConstants.GROUP_ID);
		String groupSOID = getParameter("groupId");
		boolean unclaimVerification = pbClaimedTabDTO.getUnclaimVerification();		
		String responseString="";
		boolean unclaimStatus = false;
		boolean ifPendingQueues = pbDelegate.checkIfPendingQueues(soID, groupSOID, get_commonCriteria().getVendBuyerResId(), get_commonCriteria().getCompanyId());
		boolean primaryQueueActionTaken = pbDelegate.primaryQueueActionTaken(soID, groupSOID, get_commonCriteria().getVendBuyerResId());
		if(ifPendingQueues) {
			if(unclaimVerification) {
				if(primaryQueueActionTaken) {//atleast 1 pending queue exists, prompt user to work on it
					responseString = "";
				} else {// user has not taken action on Primary queue 
					responseString = "primary_queue_action";
				}
			} else {//Proceed Un-claim with pending queues, if primary queue has been worked on
				primaryQueueActionTaken = pbDelegate.primaryQueueActionTaken(soID, groupSOID, get_commonCriteria().getVendBuyerResId());//TOD:Remove this line
				if(primaryQueueActionTaken) {
					if(groupSOID != null) {
						unclaimStatus = pbDelegate.completedClaimTask(groupSOID, get_commonCriteria().getVendBuyerResId(), Constants.CLAIM_HISTORY_CODE.SELF_UNCLAIM_COMPLETE);
					} else {
						unclaimStatus = pbDelegate.completedClaimTask(soID, get_commonCriteria().getVendBuyerResId(), Constants.CLAIM_HISTORY_CODE.SELF_UNCLAIM_COMPLETE);
					}
					if(unclaimStatus) {
						responseString = "Successfully un-claimed";
					} else {
						responseString = "Un-claim was unsuccessfull";
					}
				} else {//user has not taken action on the primary queue
					responseString = "primary_queue_action";
				}
			}
		} else {// no pending queues, the SO may have only 1 queue worked on it, proceed with un-claim
			if(groupSOID != null) {
				unclaimStatus = pbDelegate.completedClaimTask(groupSOID, get_commonCriteria().getVendBuyerResId(), Constants.CLAIM_HISTORY_CODE.SELF_UNCLAIM_COMPLETE);
			} else {
				unclaimStatus = pbDelegate.completedClaimTask(soID, get_commonCriteria().getVendBuyerResId(), Constants.CLAIM_HISTORY_CODE.SELF_UNCLAIM_COMPLETE);
			}
			if(unclaimStatus) {
				responseString = "Successfully un-claimed";
			} else {
				responseString = "Un-claim was unsuccessfull";
			}
		}
		
		responseString = responseString+OrderConstants.STRICT_DELIMITER+pbClaimedTabDTO.getUniqueNumber();  
		inputStream = new ByteArrayInputStream(responseString.getBytes());
		return SUCCESS;
	}

	public String clear() throws Exception
	{
		pbClaimedTabDTO.reset();
		return GOTO_COMMON_POWER_BUYER_CONTROLLER;		
	}	
	
	
	public PBClaimedTabDTO getModel()
	{
		return pbClaimedTabDTO;
	}

	public IPowerBuyerDelegate getPbDelegate() {
		return pbDelegate;
	}

	public void setPbDelegate(IPowerBuyerDelegate pbDelegate) {
		this.pbDelegate = pbDelegate;
	}

	public HashMap getCompletedRadioMap() {
		return completedRadioMap;
	}

	public void setCompletedRadioMap(HashMap completedRadioMap) {
		this.completedRadioMap = completedRadioMap;
	}

	public ISOMonitorDelegate getSoMonitorDelegate() {
		return soMonitorDelegate;
	}

	public void setSoMonitorDelegate(ISOMonitorDelegate soMonitorDelegate) {
		this.soMonitorDelegate = soMonitorDelegate;
	}
}
