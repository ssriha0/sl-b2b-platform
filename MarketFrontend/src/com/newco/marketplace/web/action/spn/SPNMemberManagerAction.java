package com.newco.marketplace.web.action.spn;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.web.delegates.ISPNBuyerDelegate;
import com.newco.marketplace.web.dto.SPNMemberManagerSearchResultsDTO;
import com.newco.marketplace.web.utils.CriteriaHandlerFacility;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 *  Description of the Class
 *
 * @author     Douglas Miller
 * @created    April 29, 2008
 */
public class SPNMemberManagerAction extends SPNBaseAction implements Preparable,
		ModelDriven<SPNMemberManagerSearchResultsDTO> {

	private final static Logger logger = Logger.getLogger("SPNMemberManagerAction");
	private final static long serialVersionUID = -3224333572553660382L;
	private SPNMemberManagerSearchResultsDTO theModel = new SPNMemberManagerSearchResultsDTO();


	/**
	 *Constructor for the SPNMemberManagerAction object
	 *
	 * @param  buyerSpnDelegate  Description of the Parameter
	 */
	public SPNMemberManagerAction(ISPNBuyerDelegate buyerSpnDelegate) {
		this.buyerSpnDelegate = buyerSpnDelegate;
	}


	/**
	 *  
	 *  The prepare() method is called for every http request
	 *  Prepare is normally used to do any pre-action setup
	 *  funcations
	 *  
	 * @exception  Exception  Description of the Exception
	 */
	public void prepare() throws Exception {
		loadMarkets();
		createCommonServiceOrderCriteria();
	}


	/**
	 *  Method intiPageView is used to display the default page
	 *
	 * @return                name of the landing action key
	 * @exception  Exception  Common Exception for any error
	 */
	public String intiPageView() throws Exception {
		setAttribute("spnId", getRequest().getParameter("spnId"));
		return SUCCESS;
	}


	/**
	 *  Method loadGridContainer is used to the main container grid.
	 *  The resulting view will have a reference to the inner iframe.
	 *
	 * @return                name of the landing view key
	 * @exception  Exception  Common Exception for any error
	 */
	public String loadGridContainer() throws Exception {
		return SPN_GRID_IFRAME_CONTAINER_VIEW;
	}
	/**
	 *  Showview() method is called whenever the user clicks
	 *  on a viewable tab. Please note the Dojo based tab will
	 *  pass tab key information to help determine which data
	 *  to display.
	 *
	 * @return                Name of the landing action key
	 * @exception  Exception  Common Exception for any error
	 */
	public String showView() throws Exception {
		Integer searchType = getModel().getType();
		Integer theSpn  = getModel().getSpnId();
		
		// RESET THE DEFAULT CRITERIA FOR ALL STORED CRITERIA STATES.
		// THIS WILL CLEAR THE PAGINATION VO IF IT IS STORED IN SESSION
		_setDefaultCriteria(searchType,theSpn );
		//1. MAKE CALL TO BACKEND
		//2. THE DELEGATE WILL HANDLE RESETTING THE PAGENATION VO
		//3  TO RETRIEVE PAGING VO USE CriteriaHandlerFacility.getInstance().getPaginationItem();
		//	 HOWEVER THIS IS NOT NEEDED, SINCE THE CriteriaHandlerFacility handle
		//	 SESSION KEY MANAGEMENT OF THE PAGINATION VO
		SPNMemberManagerSearchResultsDTO updateDTO
				 = getBuyerSpnDelegate().getSPNMembers( getModel(), 
						 								CriteriaHandlerFacility.getInstance().getCriteria());
		ServletActionContext.getRequest().setAttribute(PAGINATION_RESULTS_SET, updateDTO.getResults());
		
		if(updateDTO == null || updateDTO.getResults().size() == 0){
			updateDTO.setNoResults(true);
		}
		setModel(updateDTO);
		return SPN_GRID_INNER_IFRAME_VIEW;
	}


	/**
	 *  Method pageGridResults() will return a list of member information
	 *  a single page at a time. Please refer to the SPNBaseAction action class for more 
	 *  information on the protected _handlePagingCriteria() method
	 *
	 * @return                name of the landing action key
	 * @exception  Exception  Common Exception for any error
	 * @see com.newco.marketplace.web.action.spn.SPNBaseAction
	 */
	public String pageGridResults() throws Exception {
		_handlePagingCriteria(CriteriaHandlerFacility.getInstance());
		//1. MAKE CALL TO BACKEND
		//2. THE DELEGATE WILL HANDLE RESETTING THE PAGENATION VO
		//3  TO RETRIEVE PAGING VO USE CriteriaHandlerFacility.getInstance().getPaginationItem();
		//	 HOWEVER THIS IS NOT NEEDED, SINCE THE CriteriaHandlerFacility handle
		//	 SESSION KEY MANAGEMENT OF THE PAGINATION VO
		SPNMemberManagerSearchResultsDTO updatedDTO
				 = getBuyerSpnDelegate().getSPNMembers(  getModel(),
						 								 CriteriaHandlerFacility.getInstance().getCriteria());
		ServletActionContext.getRequest().setAttribute(PAGINATION_RESULTS_SET, updatedDTO.getResults());
		
		setModel(updatedDTO);
		return SPN_GRID_INNER_IFRAME_VIEW;
	}


	/**
	 *  Method filterGridResults() will return a list of member information
	 *  filtered by market selection. Please refer to the SPNBaseAction action class for more 
	 *  information on the protected _handleFilterCriteria() method
	 *
	 * @return                name of the landing action key
	 * @exception  Exception  Common Exception for any error
	 * @see com.newco.marketplace.web.action.spn.SPNBaseAction
	 */
	public String filterGridResults() throws Exception {
		Integer filterCriteriaId = getModel().getFilterCriteriaId();
		// FILTER CRITERIA IS HANDLED IN THE CriteriaHandlerFacility. THE SELECTED FILTER
		// CRITERIA IS STORED IN SESSION UNTIL IT IS RESET DURING NORMAL USER INTERACTION.
		// SEE SPNBaseAction._setDefaultCriteria() in the showView() method for more information
		_handleFilterCriteria(CriteriaHandlerFacility.getInstance(), filterCriteriaId);
		//1. MAKE CALL TO BACKEND
		//2. THE DELEGATE WILL HANDLE RESETTING THE PAGENATION VO
		//3  TO RETRIEVE PAGING VO USE CriteriaHandlerFacility.getInstance().getPaginationItem();
		//	 HOWEVER THIS IS NOT NEEDED, SINCE THE CriteriaHandlerFacility handle
		//	 SESSION KEY MANAGEMENT OF THE PAGINATION VO
		SPNMemberManagerSearchResultsDTO updateDTO
				 = getBuyerSpnDelegate().getSPNMembers(getModel(), 
						 								CriteriaHandlerFacility.getInstance().getCriteria());
		ServletActionContext.getRequest().setAttribute(PAGINATION_RESULTS_SET, updateDTO.getResults());
		setModel(updateDTO);
		return SPN_GRID_INNER_IFRAME_VIEW;
	}
	
	
	public String removeMembers() throws Exception {
		Integer searchType = getModel().getType();
		Integer theSpn  = getModel().getSpnId();
		_setDefaultCriteria(searchType,theSpn );
		
		List<Integer> selectedMemberIds = 
						getModel().getSelectedMembersList();
		getBuyerSpnDelegate().removeSelectedMembers(selectedMemberIds);

		CriteriaHandlerFacility.getInstance().getPagingCriteria().configureForZeroBased();
		SPNMemberManagerSearchResultsDTO updateDTO
				 = getBuyerSpnDelegate().getSPNMembers( getModel(), 
						 								CriteriaHandlerFacility.getInstance().getCriteria());
		
		if(updateDTO == null || updateDTO.getResults().size() == 0){
			updateDTO.setNoResults(true);
		}
		setModel(updateDTO);
		return SPN_GRID_INNER_IFRAME_VIEW;
	}
	
	
	public String approveMembers( ) throws Exception {
		Integer searchType = getModel().getType();
		Integer theSpn  = getModel().getSpnId();
		_setDefaultCriteria(searchType,theSpn );
		
		List<Integer> selectedMemberIds = 
						getModel().getSelectedMembersList();
		getBuyerSpnDelegate().approveSelectedMembers(selectedMemberIds);

		CriteriaHandlerFacility.getInstance().getPagingCriteria().configureForZeroBased();
		SPNMemberManagerSearchResultsDTO updateDTO
				 = getBuyerSpnDelegate().getSPNMembers( getModel(), 
						 								CriteriaHandlerFacility.getInstance().getCriteria());
		if(updateDTO == null || updateDTO.getResults().size() == 0){
			updateDTO.setNoResults(true);
		}
		setModel(updateDTO);
		return SPN_GRID_INNER_IFRAME_VIEW;
	}


	/**
	 *  Gets the model attribute of the SPNMemberManagerAction object
	 *
	 * @return    The model value
	 */
	public SPNMemberManagerSearchResultsDTO getModel() {
		return this.theModel;
	}


	/**
	 *  Sets the model attribute of the SPNMemberManagerAction object
	 *
	 * @param  theModel  The new model value
	 */
	public void setModel(SPNMemberManagerSearchResultsDTO theModel) {
		this.theModel = theModel;
	}


	/**
	 *  Gets the memberManagerData attribute of the SPNMemberManagerAction object
	 *
	 * @param  type        Description of the Parameter
	 * @param  resourceId  Description of the Parameter
	 */
	private void getMemberManagerData(String type, Integer resourceId) {
	}

}

