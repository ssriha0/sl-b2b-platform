package com.newco.marketplace.web.action.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.provider.InsuranceTypesVO;
import com.newco.marketplace.web.delegates.provider.IActivityRegistryDelegate;
import com.newco.marketplace.web.delegates.provider.IInsuranceDelegate;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.InsuranceInfoDto;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * $Revision: 1.8 $ $Author: glacy $ $Date: 2008/04/26 01:13:50 $
 */
public class ListInsuranceAction extends ActionSupport implements SessionAware {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5583152507759034292L;
	private static final Logger logger = Logger.getLogger(AddInsuranceAction.class.getName());
    private IInsuranceDelegate insuranceDelegate ;
    private InsuranceInfoDto insuranceInfoDto;
    private IActivityRegistryDelegate iActivityRegistryDelegate;
    private Map session;
    private List vliList;
    private List wciList;
    private List cbgliList;
    private List insuranceList;
    private String buttonType;
    private Map sSessionMap;
    private String status;
    private boolean addPolicy;
    

    
    public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public ListInsuranceAction(InsuranceInfoDto insuranceInfoDto,IInsuranceDelegate insuranceDelegate,IActivityRegistryDelegate iActivityRegistryDelegate){
    	this.insuranceInfoDto  = insuranceInfoDto;
    	this.insuranceDelegate = insuranceDelegate;
    	this.iActivityRegistryDelegate = iActivityRegistryDelegate;
    	
    }
    // method to get the insurance types residing into the database to show to
    // UI

    
    // method to load the insurance policy lists residing into the datase to
    // show it to the user
    public String listInsurance() throws Exception {

    	logger.debug("Entering into listinsurance method  in ListInsuranceAction.java");
               
        String vendorId = (String)getSession().get("vendorId");
        
        insuranceInfoDto.setVendorId(new Integer(vendorId));
        Map activityMap;
        try {
			insuranceInfoDto = insuranceDelegate
					.getInsuranceType(insuranceInfoDto);
			if(insuranceInfoDto.getInsuranceList() != null && !insuranceInfoDto.getInsuranceList().isEmpty()){
				logger.debug("---insuranceInfoDto.getInsuranceList().size()--------" + insuranceInfoDto.getInsuranceList().size());
			if(insuranceInfoDto.getInsuranceList().size() > 0)
				logger.debug("--ADd Policy--"+insuranceInfoDto.isAddPolicy());
				addPolicy = insuranceInfoDto.isAddPolicy();
			}
			else{
				logger.debug("---insuranceInfoDto.getInsuranceList() is zero--------");
				addPolicy = true;	
			}
			activityMap = iActivityRegistryDelegate
					.getProviderActivityStatus(vendorId);
		} catch (DelegateException ex) {
			ex.printStackTrace();
			logger.info("Exception Occured while processing the request due to"
					+ ex.getMessage());
			addActionError("Exception Occured while processing the request due to"
					+ ex.getMessage());
			return ERROR;
		}
        Boolean activity =  (Boolean)activityMap.get("Insurance");
        logger.debug("Activity---------"+activity.booleanValue());
        if(activity.booleanValue())
        	setStatus("1");
        else 
        	setStatus("0");
        
        insuranceList = new ArrayList();
        
       if(!(insuranceInfoDto.getInsuranceList() != null && insuranceInfoDto.getInsuranceList().isEmpty())){
    	   insuranceList = insuranceInfoDto.getInsuranceList();
    	   
    	   logger.debug("After getting insuranceInfoDto VLI "+ insuranceList.size());
        InsuranceTypesVO  insuranceTypesVO = (InsuranceTypesVO)insuranceList.get(0);
        logger.debug("Name......."+insuranceTypesVO.getName());
        logger.debug("Category Name......"+insuranceTypesVO.getCategoryName());
        logger.debug("Category Name......"+insuranceTypesVO.getExpirationDate());
        logger.debug("Ventor Credential Id......"+insuranceTypesVO.getVendorCredentialId());
        logger.debug("Document......"+insuranceTypesVO.getDocURL());
       }
       else
    	   logger.debug("After getting insuranceInfoDto VLI List is empty"); 
        return SUCCESS;
    }
    
    public String changeStatus() throws Exception {
    	
    	logger.debug("-----changeStatus------");
        String vendorId = (String)getSession().get("vendorId");
        logger.debug("Vendor Id--------"+vendorId);
        try {
			iActivityRegistryDelegate.updateActivityStatus(vendorId,
					ActivityRegistryConstants.INSURANCE);
		} catch (DelegateException ex) {
			ex.printStackTrace();
			logger.info("Exception Occured while processing the request due to"
					+ ex.getMessage());
			addActionError("Exception Occured while processing the request due to"
					+ ex.getMessage());
			return ERROR;
		}
        BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		baseTabDto.getTabStatus().put(ActivityRegistryConstants.INSURANCE,"complete");
	
         return "status";
     }
    
    public String loadInsurance() throws Exception {
    	
    	logger.debug("-----loadInsurance------");
      /* String vendorId = (String)getSession().get("vendorId");
       iActivityRegistryDelegate.updateActivityStatus(vendorId, ActivityRegistryConstants.BUSINESSINFO);*/
        return "loadInsurance";
    }
    public String loadTerms() throws Exception {
    	logger.debug("-----loadTerms------"); 
         return "loadTerms";
     }
    
    public String execute()
    {
    	logger.debug("inside execute");
    	return SUCCESS;
    }


	public List getVliList() {
		return vliList;
	}


	public void setVliList(List vliList) {
		this.vliList = vliList;
	}


	public List getWciList() {
		return wciList;
	}


	public void setWciList(List wciList) {
		this.wciList = wciList;
	}


	public List getCbgliList() {
		return cbgliList;
	}


	public void setCbgliList(List cbgliList) {
		this.cbgliList = cbgliList;
	}


	public List getInsuranceList() {
		return insuranceList;
	}


	public void setInsuranceList(List insuranceList) {
		this.insuranceList = insuranceList;
	}


	public String getButtonType() {
		return buttonType;
	}


	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}
    
	public void setSession(Map ssessionMap) {
		this.sSessionMap=ssessionMap;		
	}	
	public Map getSession() {
		return this.sSessionMap;		
	}


	public boolean isAddPolicy() {
		return addPolicy;
	}


	public void setAddPolicy(boolean addPolicy) {
		this.addPolicy = addPolicy;
	}
}
/*
 * Maintenance History
 * $Log: ListInsuranceAction.java,v $
 * Revision 1.8  2008/04/26 01:13:50  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.6.6.1  2008/04/23 11:41:40  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.7  2008/04/23 05:19:35  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.6  2008/02/26 18:18:05  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.5.2.1  2008/02/25 17:08:30  mhaye05
 * replaced system out println with logger.debug statements and some general code cleanup
 *
 */