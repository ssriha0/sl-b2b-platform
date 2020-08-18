package com.newco.marketplace.web.action.ajax;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegatesImpl.SOMonitorDelegateImpl;
import com.newco.marketplace.web.dto.AjaxCacheDTO;
import com.opensymphony.xwork2.Preparable;



public class AjaxActionTester extends SLBaseAction implements Preparable{
	private static final Logger logger = Logger.getLogger("AjaxAction");
	
	private static final long serialVersionUID = 1L;
	private SOMonitorDelegateImpl somDel = null;

	public AjaxActionTester(SOMonitorDelegateImpl somDel) 
	{
		this.somDel = somDel;
	}
	
	public String execute() throws Exception
	{
		return "success";
	}

	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	private AjaxCacheDTO getRequestParams(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		
		
		
		AjaxCacheDTO reqParam = new AjaxCacheDTO();

		if (request.getParameter("roleType")!=null){
			reqParam.setRoleType(request.getParameter("roleType").toUpperCase());
		}

		if (request.getParameter("userName")!=null){
			reqParam.setUserName(request.getParameter("userName").toUpperCase());
		}
		
		if (request.getParameter("companyId")!=null){
			Integer vendorId = Integer.parseInt(request.getParameter("companyId"));
			reqParam.setCompanyId(vendorId);
		}

		if (request.getParameter("vendBuyerResId")!=null){
			Integer resourceId = Integer.parseInt(request.getParameter("vendBuyerResId"));
			reqParam.setVendBuyerResId(resourceId);
		}

		if (request.getParameter("buyerPostedCount")!=null){
			Integer buyerPostedCount = Integer.parseInt(request.getParameter("buyerPostedCount"));
			reqParam.setRoutedTabCount(buyerPostedCount);
		}

		if (request.getParameter("providerPostedCount")!=null){
			Integer providerReceivedCount = Integer.parseInt(request.getParameter("providerReceivedCount"));
			reqParam.setRoutedTabCount(providerReceivedCount);
		}
		return reqParam;

	}

	

	
	public String soMonitorRefresh() {
		logger.debug("Entering the AjaxAction.soMonitorRefresh()");
		try{
			//TESTER CODE
			
			
			

			HttpServletRequest request = ServletActionContext.getRequest();
			
			
			Enumeration enum1 = request.getParameterNames();

			while (enum1.hasMoreElements()){
				String name = (String) enum1.nextElement();
				String values [] = request.getParameterValues(name);

				if(values !=null){
					for (int i=0; i < values.length; i++){
						
						
						System.out.println("name: " + name + " value: " + values[i]);
						//println (name + i + values [i]);
					}
				}

			}
			
			

			
			System.out.println("AjaxAction:refreshTabs()");
			
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			
			String tabToday = "040503";
			String tabAccepted = "62806";
			String tabProblem = "110575";
			String tabInactive = "22178";
			
			StringBuffer sb = new StringBuffer();
			sb.append("<message_output>");
			sb.append("<tab_today>" + System.currentTimeMillis() + "</tab_today>");
			sb.append("<tab_draft>" + System.nanoTime() + "</tab_draft>");
			sb.append("<tab_sent>" + tabToday + "</tab_sent>");
			sb.append("<tab_accepted>" + tabAccepted + "</tab_accepted>");
			sb.append("<tab_problem>" + tabProblem + "</tab_problem>");
			sb.append("<tab_inactive>" + tabInactive + "</tab_inactive>");
			sb.append("</message_output>");
			
			response.getWriter().write(sb.toString());

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
}


