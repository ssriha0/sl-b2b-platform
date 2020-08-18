package com.newco.marketplace.web.action.ajax;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.ajax.SOToken;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegatesImpl.SOMonitorDelegateImpl;
import com.opensymphony.xwork2.Preparable;



public class AjaxAction extends SLBaseAction implements Preparable{
	private static final Logger logger = Logger.getLogger("AjaxAction");
	
	private static final long serialVersionUID = 1L;
	private SOMonitorDelegateImpl somDel = null;
	NumberFormat numberFormat = NumberFormat.getInstance();
	
	public AjaxAction(SOMonitorDelegateImpl somDel) 
	{
		this.somDel = somDel;
	}
	
	public String execute() throws Exception
	{
		return "success";
	}

	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		createCommonServiceOrderCriteria();
		
	}
	
	private ArrayList<SOToken> getRequestParamsProvider()throws Exception{
		ArrayList<SOToken> soTokenList = new ArrayList<SOToken>();
		HttpServletRequest request = ServletActionContext.getRequest();
		Enumeration enum1 = request.getParameterNames();
		String soId = "";
		String spendLimit = "";
		SOToken singleToken =null;
		
		try{
			while (enum1.hasMoreElements()){
				String name = (String) enum1.nextElement();
				String values [] = request.getParameterValues(name);
				if(values !=null){
					for (int i=0; i < values.length; i++){
						singleToken = new SOToken();
						if (name.startsWith("soId")){
							soId = values[i];
							spendLimit = request.getParameter(soId + "_slCount");
							singleToken.setSoId(soId);
							if(spendLimit !=null && !"".equals(spendLimit)){
								//Check the spendLimit format; allow only digits and decimal character
								Pattern pattern = Pattern.compile("[^0-9.]");
								Matcher amountMatcher = pattern.matcher(spendLimit);
								spendLimit = amountMatcher.replaceAll("");
								
								try{
									singleToken.setSpendLimit(Double.parseDouble(numberFormat.parse(spendLimit).toString()));
								}
								catch(ParseException pe){
									logger.error("getRequestParamsProvider-->ParseException-->", pe);
								}
							}
							else{
								singleToken.setSpendLimit(0.0);
							}
							
							//add to the arraylist
							soTokenList.add(singleToken);
						}
					}
				}
			}
		}catch(Exception e){
			logger.debug("Exception encountered AjaxAction.getRequestParamsProvider()", e);
			e.printStackTrace();
		}
		return soTokenList;
	}
	
	private Integer getRoutedTabCount(String roleType)throws Exception{
		Integer iCount = -1;
		
		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			if (roleType.equalsIgnoreCase("BUYER")){
				if (request.getParameter("Posted")!=null && request.getParameter("Posted").length() > 0){
					iCount = Integer.parseInt(request.getParameter("Posted"));
				}else{
					iCount = 0;
				}
			}else if (roleType.equalsIgnoreCase("PROVIDER")){
				if (request.getParameter("Received")!=null && request.getParameter("Received").length() > 0){
					iCount = Integer.parseInt(request.getParameter("Received"));
				}else{
					iCount = 0;
				}
			}
		}catch(Exception e){
			logger.debug("Exception encountered AjaxAction.getRoutedTabCount()", e);
			e.printStackTrace();
		}
		return iCount;
	}
	
	
	private ArrayList<SOToken> getRequestParamsBuyer()throws Exception{
		ArrayList<SOToken> soTokenList = new ArrayList<SOToken>();
		HttpServletRequest request = ServletActionContext.getRequest();
		Enumeration enum1 = request.getParameterNames();
		String soId = "";
		String rejectedCount = "";
		String routedCount = "";
		String condAcceptCount = ""; 
		String spendLimit = "";
		SOToken singleToken =null;
		
		try{
			while (enum1.hasMoreElements()){
				String name = (String) enum1.nextElement();
				String values [] = request.getParameterValues(name);
				if(values !=null){
					for (int i=0; i < values.length; i++){
						singleToken = new SOToken();
						if (name.startsWith("soId")){
							soId = values[i];
							rejectedCount = request.getParameter(soId + "_reCount");
							routedCount = request.getParameter(soId + "_roCount");
							condAcceptCount = request.getParameter(soId + "_acCount");
							spendLimit = request.getParameter(soId + "_slCount");
							//set on the dto 
							singleToken.setSoId(soId);
							singleToken.setRejectedCount(Integer.parseInt(rejectedCount));
							singleToken.setRoutedCount(Integer.parseInt(routedCount));
							singleToken.setCondAcceptCount(Integer.parseInt(condAcceptCount));
							if(spendLimit !=null && !"".equals(spendLimit)){
								//Check the spendLimit format; allow only digits and decimal character
								Pattern pattern = Pattern.compile("[^0-9.-]");
								Matcher amountMatcher = pattern.matcher(spendLimit);
								spendLimit = amountMatcher.replaceAll("");
								
								try{
									singleToken.setSpendLimit(Double.parseDouble(numberFormat.parse(spendLimit).toString()));
								}
								catch(ParseException pe){
									logger.error("getRequestParamsProvider-->ParseException-->", pe);
								}
							}
							else{
								singleToken.setSpendLimit(0.0);
							}
	
							//add to the arraylist
							soTokenList.add(singleToken);
						}
				
					}
				}
	
			}
		}catch(Exception e){
			logger.debug("Exception encountered AjaxAction.getRequestParamsBuyer()", e);
			e.printStackTrace();
		}
		return soTokenList;

	}
	
	public String buyerAjaxCalls(AjaxCacheVO ajaxCacheVO)throws Exception{
		int tabRoutedCount = -1;
		String xmlTabString = "";
		String xmlDetailString = "";
		String xmlString = "";
		
		ArrayList<SOToken> soTokenList = new ArrayList<SOToken>();

		try{
			soTokenList = getRequestParamsBuyer();
			
			//If the providerId, entityId and buyerId do not exist in the request parameter,
			//set the values down to the business bean so that you can parse it in the real time mgr
	
			tabRoutedCount = getRoutedTabCount("BUYER");
			//ajaxCacheVO.setRoutedTabCount(5);
			ajaxCacheVO.setRoutedTabCount(tabRoutedCount);
			ajaxCacheVO.setSoTokenList(soTokenList);
	
			if(ajaxCacheVO.getCompanyId() == 1000){
				HttpServletRequest request = ServletActionContext.getRequest();
				String summaryCounts = (String)request.getSession().getAttribute("summaryCounts");
				if(summaryCounts == null){
					Thread.sleep(100000);
					xmlTabString = this.somDel.getSummaryCounts(ajaxCacheVO);
					request.getSession().setAttribute("summaryCounts", xmlTabString);
				}else{
					xmlTabString = summaryCounts;
				}
			}else{
				xmlTabString = this.somDel.getSummaryCounts(ajaxCacheVO);
			}
			
			//sent_changed tells the jsUtils to refresh the grid...therefore...there is
			//no need to go get the details since we're refreshing it
			if (!xmlTabString.contains("sent_changed")){
				//Commenting this line out to see the impact of not runnign the AJAX calls on performance. 
				//Will uncomment this line later.
				xmlDetailString = this.somDel.getDetailedCounts(ajaxCacheVO);
			}
			
			xmlString = "<message_output>" + xmlTabString + xmlDetailString + "</message_output>";
		}catch(Exception e){
			logger.debug("Exception encountered AjaxAction.buyerAjaxCalls()", e);
			e.printStackTrace();
		}
		return xmlString;
	}
	
	
	public String providerAjaxCalls(AjaxCacheVO ajaxCacheVO)throws Exception{
		String xml = "";
		int tabRoutedCount = -1;
		String xmlTabString = "";
		String xmlDetailString = "";
		String xmlString = "";

		try{
			ArrayList<SOToken> soTokenList = new ArrayList<SOToken>();
			soTokenList = getRequestParamsProvider();
	
			//If the providerId, entityId and buyerId do not exist in the request parameter,
			//set the values down to the business bean so that you can parse it in the real time mgr
			tabRoutedCount = getRoutedTabCount("PROVIDER");
			ajaxCacheVO.setRoutedTabCount(tabRoutedCount);
			ajaxCacheVO.setSoTokenList(soTokenList);
			
			xmlTabString = this.somDel.getSummaryCounts(ajaxCacheVO);
			
			//sent_changed tells the jsUtils to refresh the grid...therefore...there is
			//no need to go get the details since we're refreshing it
			if (!xmlTabString.contains("sent_changed")){
				//Commenting this line out to see the impact of not runnign the AJAX calls on performance. 
				//Will uncomment this line later.
				//xmlDetailString = this.somDel.getDetailedCounts(ajaxCacheVO);
			}
				
			xmlString = "<message_output>" + xmlTabString + xmlDetailString + "</message_output>";
	
			//xmlString = "<message_output><tab_today>0</tab_today><tab_draft>0</tab_draft><tab_routed>1</tab_routed><tab_accepted>2</tab_accepted><tab_problem>0</tab_problem><pCount_001-6434041185-11>77</pCount_001-6434041185-11><cCount_001-6434041185-11>88</cCount_001-6434041185-11><rCount_001-6434041185-11>99</rCount_001-6434041185-11><sCount_001-6434041185-11>300.0</sCount_001-6434041185-11><soidno_001-6434041185-1>001-6434041185-11</soidno_001-6434041185-1><pCount_001-7618-9501-3000>17</pCount_001-7618-9501-3000><cCount_001-7618-9501-3000>28</cCount_001-7618-9501-3000><rCount_001-7618-9501-3000>39</rCount_001-7618-9501-3000><sCount_001-7618-9501-3000>400.0</sCount_001-7618-9501-3000><soidno_001-7618-9501-3000>001-7618-9501-3000</soidno_001-7618-9501-3000></message_output>";
	
			//xmlString = "<message_output><tab_today>0</tab_today><tab_draft>0</tab_draft><tab_routed>1</tab_routed><tab_accepted>2</tab_accepted><tab_problem>0</tab_problem><tab_resubmit>true</tab_resubmit></message_output>";
	
			//xmlString = "<message_output><SentChanged>true</SentChanged></message_output>";
		}catch(Exception e){
			logger.debug("Exception encountered AjaxAction.providerAjaxCalls()", e);
			e.printStackTrace();
		}
		return xmlString;
	}
	
	
	
	public String soMonitorRefresh() throws Exception{
		logger.debug("Entering the AjaxAction.soMonitorRefresh()");

		String userName = "";
		String roleType = "";
		String xmlString = "";
		Integer companyId = -1;
		
		
		try {
			//1.  Figure out the login credentials

			roleType = this._commonCriteria.getRoleType();
			userName= this._commonCriteria.getSecurityContext().getUsername();
			companyId = this._commonCriteria.getCompanyId();

			AjaxCacheVO ajaxCacheVO = new AjaxCacheVO();
			if( roleType.equalsIgnoreCase("simplebuyer") )
				ajaxCacheVO.setRoleType( "buyer" );
			else
				ajaxCacheVO.setRoleType( roleType );
			ajaxCacheVO.setUserName(userName);
			ajaxCacheVO.setCompanyId(companyId); //Provider or Buyer Admin

			//2.  Determine if the user is a buyer or a provider
			//if (roleType.equalsIgnoreCase("BUYER")){
			if( roleType.toUpperCase().contains("BUYER") )
			{
				xmlString = buyerAjaxCalls(ajaxCacheVO);
			}
			else if ( roleType.equalsIgnoreCase("PROVIDER") )
			{
				xmlString = providerAjaxCalls(ajaxCacheVO);
			}
			
			
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			//System.out.println(xmlString); //Don't check this in uncommented.
			response.getWriter().write(xmlString);

		} catch (Exception e) {
			logger.error("Exception encountered AjaxAction.soMonitorRefresh()", e);
		}
		return null;
	}
	
	
	
	
}


