package com.newco.marketplace.web.action.simple;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.sitestatistics.PopularServicesVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLSimpleBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.ICreateServiceOrderDelegate;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.delegates.ISOWizardPersistDelegate;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderDescribeAndScheduleDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderEditAccountDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderFindProvidersDTO;
import com.newco.marketplace.web.utils.SSoWSessionFacility;
import com.newco.marketplace.web.utils.SecurityChecker;
import com.opensymphony.xwork2.Preparable;


/**
 * $Revision: 1.11 $ $Author: cgarc03 $ $Date: 2008/06/02 15:03:40 $
 */
//@NonSecurePage
public class SSoWControllerAction extends SLSimpleBaseAction implements Preparable
{

	private static final long serialVersionUID = 0L;
	private ISOWizardPersistDelegate isoWizardPersistDelegate;
	private ICreateServiceOrderDelegate createServiceOrderDelegate;


	public SSoWControllerAction(ISOWizardFetchDelegate ifetchDelegate) {
		super.setFetchDelegate(ifetchDelegate);
	}

	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();
	}


	public String execute() throws Exception
	{
		SSoWSessionFacility.getInstance().invalidateSessionFacilityStates();

		String currentApplicationMode = getRequest().getParameter(OrderConstants.APP_MODE);
		String soID = getRequest().getParameter(OrderConstants.SO_ID);
		Boolean isSimpleBuyerZipEntered = (Boolean)getSession().getAttribute("simpleBuyer");
		String csoZipCode = (String)getSession().getAttribute(SSOW_ZIP);
		PopularServicesVO popServices  = (PopularServicesVO)getSession().getAttribute("popServicesVo");
		String resourceID = (String) getSession().getAttribute(SSOW_RESOURCE_ID);
		getSession().removeAttribute(SSOW_RESOURCE_ID);
		String nextViewChk = getRequest().getParameter(VIEW);
		SecurityChecker secChecker=new SecurityChecker();
		String nextView=secChecker.securityCheck(nextViewChk);
		
		getSession().setAttribute(OrderConstants.SO_ID, soID);
		getSession().setAttribute(SIMPLE_SERVICE_ORDER_WIZARD_INDICTATOR,true);

		//Set up next view and initialize the session facility.
		setNextViewAndInitSessionFacility(currentApplicationMode, nextView);

		CreateServiceOrderFindProvidersDTO findProvidersDTO =
			(CreateServiceOrderFindProvidersDTO)
			SSoWSessionFacility.getInstance().getTabDTO(OrderConstants.SSO_FIND_PROVIDERS_DTO);

		// The simpleBuyer session attribute is set on the home page if the user entered
		// a zip in the simple buyer panel.  We need to make sure that if the user is already
		// logged in, that they have the simple buyer role.
		Boolean isLoggedIn = setSimpleBuyerRole(isSimpleBuyerZipEntered,
				findProvidersDTO);

		//Get zip and location if they are not editing or copy
		if(!OrderConstants.COPY_MODE.equals(currentApplicationMode) && !OrderConstants.EDIT_MODE.equals(currentApplicationMode)){
			setZipAndState(csoZipCode, findProvidersDTO, isLoggedIn);
		}

		if(popServices != null){
			findProvidersDTO.setSelectedCategorys(popServices);
		}
		getSession().removeAttribute("popServicesVo");

		if (resourceID != null) {
			findProvidersDTO.getSelectedProviders().clear();
			findProvidersDTO.setResourceID(Integer.parseInt(resourceID));
			findProvidersDTO.setLockedProviderList(true);
		}
		if(findProvidersDTO.getFetchedVerticles().size() <= 0 ) {
			//Hit Db and fectht e verticles
			findProvidersDTO.setFetchedVerticles(getLookupManager().getNotBlackedOutSkillTreeMainCategories("-1", findProvidersDTO.getState()));
		}
		//Set zipCode into the Describe and Schedule DTO
		setDescribeAndSchedDto(currentApplicationMode, csoZipCode);
		getSession().removeAttribute("csoZipCode");

		return GOTO_COMMON_SIMPLE_SERVICE_ORDER_CONTROLLER;
	}

	/**
	 * @param currentApplicationMode
	 * @param csoZipCode
	 */
	private void setDescribeAndSchedDto(String currentApplicationMode,
			String csoZipCode) {
		CreateServiceOrderDescribeAndScheduleDTO csoDescAndSchedDTO =
			(CreateServiceOrderDescribeAndScheduleDTO)
			SSoWSessionFacility.getInstance().getTabDTO(OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO);
		if(currentApplicationMode != null
				&& currentApplicationMode.equalsIgnoreCase(OrderConstants.CREATE_MODE) && csoZipCode != null){
			csoDescAndSchedDTO.setZip(csoZipCode);
		}
	}

	/**
	 * Description: Set up next view and initialize the session facility.
	 * @param currentApplicationMode
	 * @param nextView
	 * @throws BusinessServiceException
	 */
	private void setNextViewAndInitSessionFacility(
			String currentApplicationMode, String nextView)
			throws BusinessServiceException {
		if(nextView == null )
		{
			setLandingAction(TO_SSO_HOMEPAGE_VIEW); // This is an action.
		}
		else {
			setLandingAction(nextView);
		}

		if(currentApplicationMode != null &&
		   currentApplicationMode.equalsIgnoreCase(OrderConstants.EDIT_MODE)){
			SSoWSessionFacility.getInstance().initSessionFactilty(fetchDelegate,OrderConstants.EDIT_MODE);
		}
		else if (currentApplicationMode != null &&
		   currentApplicationMode.equalsIgnoreCase(OrderConstants.COPY_MODE)){
			SSoWSessionFacility.getInstance().initSessionFactilty(fetchDelegate,OrderConstants.COPY_MODE);
			getSession().setAttribute(OrderConstants.SO_ID, null);
		}
		else{
			SSoWSessionFacility.getInstance().initSessionFactilty(fetchDelegate,OrderConstants.CREATE_MODE);
			String simpleBuyerId = SSoWSessionFacility.getInstance().createSimpleBuyerGUIDId();
			getSession().setAttribute(Constants.SESSION.SIMPLE_BUYER_GUID,simpleBuyerId);
		}
	}

	/**
	 * Description: The simpleBuyer session attribute is set on the home page if the user entered
	 * a zip in the simple buyer panel.  We need to make sure that if the user is already
	 * logged in, that they have the simple buyer role.
	 * @param isSimpleBuyerZipEntered
	 * @param findProvidersDTO
	 * @return <code>Boolean</code>
	 */
	private Boolean setSimpleBuyerRole(Boolean isSimpleBuyerZipEntered,
			CreateServiceOrderFindProvidersDTO findProvidersDTO) {
		Boolean isLoggedIn = (Boolean)getSession().getAttribute(SOConstants.IS_LOGGED_IN);
		if (null != isSimpleBuyerZipEntered) {
			if ( null != isLoggedIn && isLoggedIn.booleanValue()) {
				if (this.get_commonCriteria().getRoleId().intValue() == OrderConstants.SIMPLE_BUYER_ROLEID){
					findProvidersDTO.setSimpleBuyer(isSimpleBuyerZipEntered.booleanValue());
				}
			} else {
				findProvidersDTO.setSimpleBuyer(isSimpleBuyerZipEntered.booleanValue());
			}
			getSession().removeAttribute("simpleBuyer");
		} else {

			if ( null != isLoggedIn && isLoggedIn.booleanValue()) {
				if (this.get_commonCriteria().getRoleId().intValue() == OrderConstants.SIMPLE_BUYER_ROLEID){
					findProvidersDTO.setSimpleBuyer(true);
				}
			} else {
				findProvidersDTO.setSimpleBuyer(false);
			}
		}
		return isLoggedIn;
	}


	/**
	 * Description:  Set the zip and state based on whether logged in
	 * @param csoZipCode
	 * @param findProvidersDTO
	 * @param isLoggedIn
	 * @throws BusinessServiceException
	 */
	private void setZipAndState(String csoZipCode,
			CreateServiceOrderFindProvidersDTO findProvidersDTO,
			Boolean isLoggedIn) throws BusinessServiceException {
		if ( null != isLoggedIn && isLoggedIn.booleanValue() && get_commonCriteria().getVendBuyerResId() != -1)
		{
			getLocation(get_commonCriteria().getVendBuyerResId(), findProvidersDTO);
		}
		else
		{
			findProvidersDTO.setZip(csoZipCode);
			// put code to get state code here
			LocationVO lvo = getLookupManager().checkIfZipISValid(csoZipCode);
			if(lvo != null) {
				findProvidersDTO.setState(lvo.getState());
			}
		}
	}




	/**
	 * Description: Sets the zip and state into the findProvidersDTO for the buyer default location
	 * @param vendBuyerResId
	 * @param findProvidersDTO
	 */
	private void getLocation(Integer vendBuyerResId,
			CreateServiceOrderFindProvidersDTO findProvidersDTO) {
		CreateServiceOrderEditAccountDTO csoOrderEditDto = new CreateServiceOrderEditAccountDTO();
		try{
			csoOrderEditDto = createServiceOrderDelegate.getBuyerResourceInfo(vendBuyerResId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (csoOrderEditDto.getAddress() != null){
			findProvidersDTO.setZip(csoOrderEditDto.getAddress().getZipCode());
			findProvidersDTO.setState(csoOrderEditDto.getAddress().getState());
		}
	}


	public ISOWizardPersistDelegate getIsoWizardPersistDelegate() {
		return isoWizardPersistDelegate;
	}


	public void setIsoWizardPersistDelegate(
			ISOWizardPersistDelegate isoWizardPersistDelegate) {
		this.isoWizardPersistDelegate = isoWizardPersistDelegate;
	}


	public ICreateServiceOrderDelegate getCreateServiceOrderDelegate() {
		return createServiceOrderDelegate;
	}


	public void setCreateServiceOrderDelegate(
			ICreateServiceOrderDelegate createServiceOrderDelegate) {
		this.createServiceOrderDelegate = createServiceOrderDelegate;
	}


}
