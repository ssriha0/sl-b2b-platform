package com.newco.marketplace.web.action.homepage;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.dto.vo.sitestatistics.PopularServicesVO;
import com.newco.marketplace.dto.vo.sitestatistics.SiteStatisticsVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLSimpleBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.IHomepageDelegate;
import com.newco.marketplace.web.dto.homepage.HomepageFormDTO;
import com.newco.marketplace.web.security.NonSecurePage;
import com.newco.marketplace.web.utils.Config;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

/**
 * $Revision: 1.8 $ $Author: cnair $ $Date: 2008/06/08 16:05:36 $
 */
@NonSecurePage
public class HomepageAction extends SLSimpleBaseAction implements Preparable, ValidationAware, ModelDriven<HomepageFormDTO>
{
	private static final long serialVersionUID = 0L;
	HttpServletRequest request;
	IHomepageDelegate delegate;
	HomepageFormDTO dto = new HomepageFormDTO();

	
	/* entry point for all methods, first comes here */
	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();
	}	
	
	public String displayPage() throws Exception
	{
		//if (getSession().getAttribute(OrderConstants.DEEPLINK_USERNAME) != null){
		//	return "login";
		//}
		//Set cookie to display browser compatibility banner.
		Cookie[] cookies = getRequest().getCookies();
		HttpServletResponse bannerCookieResponse=ServletActionContext.getResponse();
		boolean isBannerCookie=false;
		if(null != cookies){
			for (Cookie c : cookies) {
				if(null!= c && c.getName().equals("isDismissedBanner")){
					if (c.getValue() != null && !c.getValue().equalsIgnoreCase("null")){
						//if cookie has a value,no need to create a new cookie.
						if(c.getValue().equalsIgnoreCase("Yes") || c.getValue().equalsIgnoreCase("No") ){
							isBannerCookie=true;
						}
					}else{
						//Trying to create a session cookie to hold the value of isdismissedBanner
						Cookie bannerCoockie=new Cookie("isDismissedBanner","No");
						bannerCoockie.setMaxAge(-1);
						bannerCoockie.setPath("/");
						bannerCookieResponse.addCookie(bannerCoockie);
						isBannerCookie=true;
					   }
				    }
				}			
		}

		if(!isBannerCookie){
			Cookie bannerCoockie=new Cookie("isDismissedBanner","No");
			bannerCoockie.setMaxAge(-1);
			bannerCoockie.setPath("/");
			bannerCookieResponse.addCookie(bannerCoockie);
			isBannerCookie=true;
		}
		ServletActionContext.setResponse(bannerCookieResponse);
		Boolean isLoggedIn = (Boolean)getSession().getAttribute(SOConstants.IS_LOGGED_IN);
		if (null != isLoggedIn && isLoggedIn.booleanValue()) {
			return "dashboard";
		}
		
		HomepageFormDTO form = getModel();
		List<PopularServicesVO> proServices = delegate.getPopularProServices();
		List<PopularServicesVO> simpleServices = delegate.getPopularSimpleServices();
		SiteStatisticsVO siteStats = delegate.getSiteStatistics();
		List<String> blackoutStates = delegate.getBlackoutStates();
		
        NumberFormat nfPros = NumberFormat.getInstance(Locale.US);
        NumberFormat nfSatisfaction = NumberFormat.getInstance(Locale.US);
        
        nfPros.setMaximumFractionDigits(0);
        nfSatisfaction.setMaximumFractionDigits(2);
        
		String numRegisteredServicePros = nfPros.format(siteStats.getRegisteredServicePros());
		String perSatisfactionRating = nfSatisfaction.format(siteStats.getSatisfactionRating());
		
		form.setPopularProServices(proServices);
		form.setPopularSimpleServices(simpleServices);
		form.setNumProviders(numRegisteredServicePros);
		form.setPercentSatisfaction(perSatisfactionRating);
		form.setBlackoutStates(blackoutStates);
		
		setModel(form);
		
		return SUCCESS;
	}

	/**
	 * handles the explore the marketplace button in the header
	 * @return
	 * @throws Exception
	 */
	public String etm() throws Exception
	{
		setView(TO_SSO_FIND_PROVIDERS_VIEW);
		setAppMode(OrderConstants.CREATE_MODE);
	
		//Creating simple buyer designation to pass to the SSoWController
		getSession().setAttribute("simpleBuyer", new Boolean(true));

		clearErrorsAndMessages();
		
		return "to_sso_controller";
	}	
	
	public String submitZip() throws Exception
	{
		HomepageFormDTO hpDto = getModel();
		//Case where value is not getting submitted, obtain from query params
		if(StringUtils.isBlank(hpDto.getZipCode())){ 
			obtainQueryParamValues(hpDto);  
		}
		boolean exists = delegate.zipExists(hpDto.getZipCode());
		if(exists) {
			setView(TO_SSO_FIND_PROVIDERS_VIEW);
			setAppMode(OrderConstants.CREATE_MODE);
			Map<String, Object> sessionMap = 
				ActionContext.getContext().getSession();
			sessionMap.put(OrderConstants.SSO_HOMEPAGE_DTO, hpDto);	

			//Creating simple buyer designation to pass to the SSoWController
			if (hpDto.getPopularSimpleServices().get(0).getBuyerTypeId() != null)
			{
				if (hpDto.getPopularSimpleServices().get(0).getBuyerTypeId().intValue() == 1)
				{
					getSession().setAttribute("simpleBuyer", new Boolean(true));
				} else {
					getSession()
							.setAttribute("simpleBuyer", new Boolean(false));
				}
			}
			else
			{
				String simpleBuyer = getParameter("simpleBuyer");
				if(StringUtils.isNotBlank(simpleBuyer) && simpleBuyer.equalsIgnoreCase("true"))
				{
					getSession().setAttribute("simpleBuyer", new Boolean(true));					
					setAttribute("simpleBuyer", new Boolean(true));
				}
				else
				{
					getSession().setAttribute("simpleBuyer", new Boolean(false));
					setAttribute("simpleBuyer", new Boolean(false));
				}
			}			
			
			
			//Retrieve zip code to insert into the Describe and Schedule DTO
			getSession().setAttribute("csoZipCode", hpDto.getZipCode());
			
			//Retrieve zip code for create accounts
			getSession().setAttribute("caZipCode", hpDto.getZipCode());
			
			//Set up csoFindProvidersDTO to pass to the ssoControllerAction and then to csoFindProvidersAction
			getSession().setAttribute("popServicesVo", hpDto.getPopularSimpleServices().get(0));	
			
			clearErrorsAndMessages();
			
			return "to_sso_controller";
		}else{
			if(hpDto.getZipCode() == null){
				   addActionError(getTheResourceBundle().getString("Zip_Validation_Missing"));
			}
				if(!hpDto.isValidZip()){
					addActionError(getTheResourceBundle().getString("Zip_Validation"));
				}
			displayPage();	
			return "fail";
		}
	}
	/**
	 * This method obtains the query parameters and set them into the DTO
	 * This is to handle cases where the submitZip method is called from secure 
	 * pages since request does not contain the values submitted.
	 * @param hpDto
	 */
	private void obtainQueryParamValues(HomepageFormDTO hpDto) {
		String zipCode=getRequest().getParameter("ZipCodeEntered"); 
		if(StringUtils.isNotBlank(zipCode)){
			hpDto.setZipCode(zipCode);
		}
		String buyerTypeId=getRequest().getParameter("buyerTypeId");
		if(StringUtils.isNotBlank(buyerTypeId)&& StringUtils.isNumeric(buyerTypeId)){
			List<PopularServicesVO> popularProServices= new ArrayList<PopularServicesVO>() ;
			PopularServicesVO popularServicesVO=new PopularServicesVO();
			popularServicesVO.setBuyerTypeId(new Integer(buyerTypeId));
			popularProServices.add(popularServicesVO);
			hpDto.setPopularSimpleServices(popularProServices);			
		}
	}
	
	public HomepageFormDTO getModel()
	{
//		dto = (HomepageFormDTO)SSoWSessionFacility.getInstance().getTabDTO(OrderConstants.SSO_HOMEPAGE_DTO);
		return dto;	
	}

	public  void setModel(HomepageFormDTO dto)
	{
		this.dto = dto;
	}
	
	public void setDelegate(IHomepageDelegate delegate) {
		this.delegate = delegate;
	}

	
	public IHomepageDelegate getDelegate() {
		return delegate;
	}

	public HomepageFormDTO getDto() {
		return dto;
	}

	public void setDto(HomepageFormDTO dto) {
		this.dto = dto;
	}

	public void setServletRequest(HttpServletRequest request)
	{
	   this.request = request;
	}

	public ResourceBundle getTheResourceBundle() {
		return Config.getResouceBundle();
	}

}
