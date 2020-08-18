package com.newco.marketplace.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.businessImpl.ledger.NMMProcessor;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.ApplicationPropertiesVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataNotFoundException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AdminConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao;
import com.newco.marketplace.persistence.iDao.lookup.LookupDao;
import com.newco.marketplace.util.PropertiesUtils;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.21 $ $Author: akashya $ $Date: 2008/05/21 23:33:04 $
 */


public class ServiceLiveInitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8633542772646571705L;
	private static final Logger logger = Logger
	.getLogger(ServiceLiveInitServlet.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		super.doPost(req, resp);
	}

	@Override
	public void init() throws ServletException {
		
		ServletContext servletContext = this.getServletContext();
		
		ApplicationContext ctx = MPSpringLoaderPlugIn.getCtx();
		
		loadStaticContextRoot(servletContext, ctx);
		loadResouceBundle(servletContext);
		loadTimeIntervals(servletContext, ctx);
		loadStates(servletContext, ctx);
		loadTimeMaps(servletContext);
		loadEnvironmentInformation(servletContext, ctx);
		loadCompanyProfilesDD(servletContext, ctx);
		loadCompanyCredenitalsDD(servletContext, ctx);
		loadTeamProfilesDD(servletContext, ctx);
		loadTeamCredenitalsDD(servletContext, ctx);
		loadGoogleApiKey(servletContext,ctx);
		//Initiate NMM Process

		boolean doHeartbeat = false;
		String NMM_active = System.getProperty("NMM.active"); 
		if(org.apache.commons.lang.StringUtils.isBlank(NMM_active)){
			logger.info(":Could not find init parameter NMM.active defaulting to TRUE for doHeartbeat");
			doHeartbeat = true;
		}else{
			if(NMM_active.equals("true")){
				logger.info(":Found init parameter NMM.active value="+NMM_active+"setting to TRUE for doHeartbeat");
				doHeartbeat = true;
			}
			else
			{
				logger.info(":Found init parameter NMM.active value="+NMM_active+"setting to FALSE for doHeartbeat");
				doHeartbeat = false;
			}
		}
		if(doHeartbeat){
			NMMProcessor nmmProcessor = new NMMProcessor();
			Thread nmmThread = new Thread(nmmProcessor);
			nmmThread.start();
		}
	}
	
	
	private void loadGoogleApiKey(ServletContext servletContext,
			ApplicationContext ctx) { 
		// TODO Auto-generated method stub
		String google_map_key = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.GOOGLE_MAP_API_KEY);
		this.getServletContext().setAttribute(Constants.AppPropConstants.GOOGLE_MAP_API_KEY,google_map_key);
	}

	/**
	 * Load environment properties to display environment messages letting developers and internal users know
	 * what version of the build and what environment they are working with
	 * @param servletContext
	 * @param ctx
	 */
	protected void loadEnvironmentInformation(ServletContext servletContext, ApplicationContext ctx){

		ApplicationPropertiesVO appVersionProp = new ApplicationPropertiesVO();
		ApplicationPropertiesVO appEnvironmentProp = new ApplicationPropertiesVO();
		
		IApplicationPropertiesDao  applicationPropertiesDao = (IApplicationPropertiesDao) ctx.getBean("applicationPropertiesDao");
		try {
			appEnvironmentProp = applicationPropertiesDao.query(Constants.AppPropConstants.APPLICATION_ENVIRONMENT);
			appVersionProp = applicationPropertiesDao.query(Constants.AppPropConstants.MARKET_FRONTEND_VERSION);
	
		} catch (DataNotFoundException dnfe) {
			logger.error("[Exception thrown finding one of the environment properties ", dnfe);
        } catch (DataAccessException dae) {
			logger.error("[Exception thrown finding one of the environment properties ", dae);
        }
		this.getServletContext().setAttribute(Constants.AppPropConstants.MARKET_FRONTEND_VERSION,appVersionProp.getAppValue());
		this.getServletContext().setAttribute(Constants.AppPropConstants.APPLICATION_ENVIRONMENT,appEnvironmentProp.getAppValue());

	}
	/**
	 * Loads the Static context path into the application context
	 * @param servletContext
	 * @param ctx
	 */
	protected void loadStaticContextRoot(ServletContext servletContext, ApplicationContext ctx) {
		
		String staticPath = null;
		String strContext = null;
		ApplicationPropertiesVO prop = new ApplicationPropertiesVO();
		IApplicationPropertiesDao  applicationPropertiesDao = (IApplicationPropertiesDao) ctx.getBean("applicationPropertiesDao");
		
		try {
			prop = applicationPropertiesDao.query(Constants.AppPropConstants.STATIC_CONTEXT_ROOT);
			strContext = System.getProperty(Constants.AppPropConstants.RESOURCES_CONTEXT_ROOT);
			staticPath = strContext + "/" + prop.getAppValue();
		} catch (DataNotFoundException e1) {
			logger.error("[Exception thrown finding Context Root] ", e1);
        } catch (DataAccessException e) {
			logger.error("[Exception thrown finding Context Root] ", e);
        }
		this.getServletContext().setAttribute(Constants.AppPropConstants.STATIC_CONTEXT_ROOT,staticPath);
		System.setProperty(Constants.AppPropConstants.STATIC_CONTEXT_ROOT,staticPath);
	}
	
	/**
	 * loadResouceBundle loads the servicelive_copy resource bundle into the application context
	 * @param servletContext
	 */
	protected void loadResouceBundle(ServletContext servletContext) {
		ResourceBundle rb = ResourceBundle.getBundle("/resources/properties/servicelive_copy");
		LocalizationContext serviceliveBundle = new LocalizationContext(rb);
		servletContext.setAttribute("serviceliveCopyBundle",serviceliveBundle);
	}
	
	/**
	 * Loads a List of LookupVO's containing time interval data into the application context
	 * @param servletContext
	 * @param ctx
	 */
	protected void loadTimeIntervals(ServletContext servletContext, ApplicationContext ctx) {
		
		List<LookupVO> data = null;
		LookupDao lookupDAO = (LookupDao) ctx.getBean("lookupDao");

		try {
			data = lookupDAO.getTimeIntervals();
		} catch (DataServiceException e) {
			data = new ArrayList<LookupVO>();
		}
		servletContext.setAttribute(Constants.AppPropConstants.TIME_INTERVALS, data);
	}
	
	/**
	 * Loads a List of LookupVO's containing State data into the application context
	 * @param servletContext
	 * @param ctx
	 */
	protected void loadStates(ServletContext servletContext, ApplicationContext ctx) {
		
		List<LookupVO> data = null;
		LookupDao lookupDAO = (LookupDao) ctx.getBean("lookupDao");

		try {
			data = lookupDAO.getStateCodes();
		} catch (DataServiceException e) {
			data = new ArrayList<LookupVO>();
		}
		servletContext.setAttribute(Constants.SERVLETCONTEXT.STATES_LIST, data);
	}
	
	/**
	 *Loads a list of Real time for hours, minutes
	 *@param servletContext
	 */
    protected void loadTimeMaps(ServletContext servletContext) {
    	
    	List<String> HourList=new ArrayList<String>();
    	List<String> MinuteList=new ArrayList<String>();
    	List<String> AMPMList=new ArrayList<String>();
    	
    	for (int hour = 0; hour <= 12; ++hour) {	
    		String hourS = String.valueOf(hour);
    		if (hour < 10) hourS = "0"+ hourS;
    		HourList.add(hourS);
    	}
    	
    	servletContext.setAttribute(Constants.SERVLETCONTEXT.HOURS_LIST, HourList);
    	
    	for (int min = 0; min <= 59; ++min) {
    		String minute = String.valueOf(min);
    		if (min < 10) minute = "0"+ minute;
    		MinuteList.add(minute);
    	}
    	
    	servletContext.setAttribute(Constants.SERVLETCONTEXT.MINUTES_LIST, MinuteList);
    	AMPMList.add(OrderConstants.AM);
    	AMPMList.add(OrderConstants.PM);

    	servletContext.setAttribute(Constants.SERVLETCONTEXT.AMPM_LIST, AMPMList);
	}
    
    protected void loadCompanyProfilesDD(ServletContext servletContext, ApplicationContext ctx) {
    	ILookupBO  lookUpDel = 
    					(ILookupBO) ctx.getBean("lookupBO");
    	List<LookupVO> mainSelectListForCompanyApprovals = null;
		try {
			mainSelectListForCompanyApprovals = lookUpDel.getEntityStatusList(AdminConstants.COMPANY_PROFILE);
			servletContext.setAttribute(Constants.SESSION.AUDIT_APPROVAL_SELECTS, mainSelectListForCompanyApprovals);
			
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
    }
    
    protected void loadCompanyCredenitalsDD(ServletContext servletContext, ApplicationContext ctx) {
    	ILookupBO  lookUpDel = 
    					(ILookupBO) ctx.getBean("lookupBO");
    	List<LookupVO> mainSelectListForCompanyApprovals = null;
		try {
			mainSelectListForCompanyApprovals = lookUpDel.getEntityStatusList(AdminConstants.COMPANY_CREDENTIAL);
			servletContext.setAttribute(Constants.SESSION.AUDIT_APPROVAL_CREDENTIALS, mainSelectListForCompanyApprovals);
			
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
    }
    
    
    protected void loadTeamProfilesDD(ServletContext servletContext, ApplicationContext ctx) {
    	ILookupBO  lookUpDel = 
    					(ILookupBO) ctx.getBean("lookupBO");
    	List<LookupVO> theList = null;
		try {
			theList = lookUpDel.getEntityStatusList(AdminConstants.TEAM_MEMBER);
			servletContext.setAttribute(Constants.SESSION.AUDIT_TEAM_APPROVAL_SELECTS, theList);
			
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
    }
    
    protected void loadTeamCredenitalsDD(ServletContext servletContext, ApplicationContext ctx) {
    	ILookupBO  lookUpDel = 
    					(ILookupBO) ctx.getBean("lookupBO");
    	List<LookupVO> theList = null;
		try {
			theList = lookUpDel.getEntityStatusList(AdminConstants.TEAM_MEMBER_CREDENTIAL);
			servletContext.setAttribute(Constants.SESSION.AUDIT_TEAM_APPROVAL_CREDENTIALS, theList);
			
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
    }
  
	
}
/*
 * Maintenance History
 * $Log: ServiceLiveInitServlet.java,v $
 * Revision 1.21  2008/05/21 23:33:04  akashya
 * I21 Merged
 *
 * Revision 1.18.6.1  2008/05/19 22:25:42  schavda
 * NMMProcessor - Sharp HeartBeat Monitor
 *
 * Revision 1.18  2008/04/26 01:13:51  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.15.4.1  2008/04/01 22:04:15  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.16  2008/03/27 18:57:57  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.15.8.7  2008/03/25 20:08:51  mhaye05
 * code cleanup
 *
 * Revision 1.15.8.6  2008/03/19 13:19:38  dmill03
 * dmill03 check-point check in
 *
 * Revision 1.15.8.5  2008/03/14 05:32:32  dmill03
 * added audit widget features updates
 *
 * Revision 1.15.8.4  2008/03/14 00:58:39  dmill03
 * update admin apprval widget feature
 *
 * Revision 1.15.8.3  2008/03/13 18:08:16  dmill03
 * added aJax Support
 *
 * Revision 1.15.8.2  2008/03/12 23:20:09  dmill03
 * updated by dmil03 for audit widgets and ajax support
 *
 * Revision 1.15.8.1  2008/03/07 17:35:07  spate05
 * show us our environment and build number (for dev and qa only)
 *
 * Revision 1.15  2008/02/28 17:01:31  mhaye05
 * updated to only load the static_context_root and not one for dojo and one for images
 *
 * Revision 1.14  2008/02/18 17:55:55  pkoppis
 * updated with lists for hour,minute.
 *
 * Revision 1.13  2008/02/08 15:54:03  mhaye05
 * updated return types to be List and not ArrayList
 *
 * Revision 1.12  2008/02/07 20:46:25  usawant
 * *** empty log message ***
 *
 * Revision 1.11  2007/12/11 01:36:31  mhaye05
 * updated because ApplicationPropertiesDAOImpl.query now takes a String
 *
 * Revision 1.10  2007/12/10 23:53:00  glacy
 * SecurityTag and accompanying classes.
 *
 * Revision 1.9  2007/12/10 19:21:02  mhaye05
 * updated to match changes in IApplicationPropertiesDAO
 *
 * Revision 1.8  2007/12/08 15:04:29  mhaye05
 * modified to use AppPropConstants class that is now in the Constants class
 *
 * Revision 1.7  2007/12/06 02:12:07  mhaye05
 * cleaned up init() an added method to load time intervals
 *
 * Revision 1.6  2007/12/01 20:44:14  spate05
 * removed the need to load the entire applicationContext.
 *
 * Revision 1.5  2007/11/20 18:50:14  mhaye05
 * changed resource bundle name
 *
 * Revision 1.4  2007/11/20 14:08:04  mhaye05
 * added code to put resource bundle into application context
 *
 * Revision 1.3  2007/11/05 20:40:13  akashya
 * changed System.err.println to System.out.println
 *
 * Revision 1.2  2007/11/05 15:37:04  akashya
 * cleaned up imports
 *
 * Revision 1.1  2007/11/05 14:35:07  akashya
 * Initial Check In
 *
 */
