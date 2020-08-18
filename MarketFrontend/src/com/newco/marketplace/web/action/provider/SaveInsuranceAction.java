package com.newco.marketplace.web.action.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.web.action.base.SLAuditableBaseAction;
import com.newco.marketplace.web.delegates.provider.IInsuranceDelegate;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.InsuranceInfoDto;
import com.opensymphony.xwork2.ActionContext;

public class SaveInsuranceAction extends 
								 SLAuditableBaseAction 
								 implements SessionAware, ServletRequestAware{

    /**
	 * 
	 */
	private static final long serialVersionUID = -587460093759069345L;
    private static final Logger logger = Logger.getLogger(AddInsuranceAction.class.getName());
    private IInsuranceDelegate insuranceDelegate ;
    private InsuranceInfoDto insuranceInfoDto;
    private String method;
    private Map session;
    private List vliList;
    private List wciList;
    private List cbgliList;
    private Map sSessionMap;
	private HttpServletRequest request;
    private String credId;

    
    public SaveInsuranceAction(InsuranceInfoDto insuranceInfoDto,IInsuranceDelegate insuranceDelegate){
    	this.insuranceInfoDto  = insuranceInfoDto;
    	this.insuranceDelegate = insuranceDelegate;
    }
    // method to get the insrurance types residing into the database to show to
    // UI
   
    public String getMethod() {
		return method;
	}


	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public void validate() {
		super.validate();
		if (hasFieldErrors()) {
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto.setDtObject(insuranceInfoDto);
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.INSURANCE,"errorOn");
		}
	}
	
	// method to load the insurance policy lists residing into the datase to
    // show it to the user
    public String saveInsurance() throws Exception {
    	vliList = new ArrayList();
        vliList.add("Yes");
        vliList.add("No");
        
        wciList = new ArrayList();
        wciList.add("Yes");
        wciList.add("No");
        
        cbgliList = new ArrayList();
        cbgliList.add("Yes");
        cbgliList.add("No");

        /****************HardCoded for Testing - Starts*****************/ 	
        String userId = (String)getSessionMap().get("username");
        /****************HardCoded for Testing - Ends*****************/ 
        // insuranceTypesRequest.setUserId("testkapil");
        insuranceInfoDto.setUserId(userId);
       
        boolean saveInsurance =   insuranceDelegate.saveInsuranceInfo(insuranceInfoDto);
        if(method!=null&&method.equals("listInsurance")){
        	return "next";
        }
        return SUCCESS;
    }
    
 // method to load the insurance policy lists residing into the datase to
    // show it to the user
    public String listInsurance() throws Exception {
    	vliList = new ArrayList();
        vliList.add("Yes");
        vliList.add("No");
        
        wciList = new ArrayList();
        wciList.add("Yes");
        wciList.add("No");
        
        cbgliList = new ArrayList();
        cbgliList.add("Yes");
        cbgliList.add("No");

        /****************HardCoded for Testing - Starts*****************/ 	
        String userId = (String)getSessionMap().get("username");
        /****************HardCoded for Testing - Ends*****************/ 
        // insuranceTypesRequest.setUserId("testkapil");
        insuranceInfoDto.setUserId(userId);
       
        boolean saveInsurance =   insuranceDelegate.saveInsuranceInfo(insuranceInfoDto);

        return "next";
    }
    
    public String loadInsuranceTypePage()
    {
    	String tempid=request.getParameter("credId");
    	String tempCategory=request.getParameter("category");
    	if(tempid==null||tempid.trim().length()==0){
    		tempid="0";
    	}

    	getSessionMap().put("VendorCredId",tempid);	
    	getSessionMap().put("Category",tempCategory);
    	
			determineSLAdminFeature();
		
    	return "loadInsuranceTypePage";
    }
    
    public String execute()
    {
    	System.out.println("inside execute");
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


	public InsuranceInfoDto getInsuranceInfoDto() {
		return insuranceInfoDto;
	}


	public void setInsuranceInfoDto(InsuranceInfoDto insuranceInfoDto) {
		this.insuranceInfoDto = insuranceInfoDto;
	}
	
	public void setSession(Map ssessionMap) {
		this.sSessionMap=ssessionMap;		
	}	
	public Map getSessionMap() {
		return this.sSessionMap;		
	}

	public void setServletRequest(HttpServletRequest arg0) {
		request=arg0;
	}


	public String getCredId() {
		return credId;
	}


	public void setCredId(String credId) {
		this.credId = credId;
	}


	
     


}