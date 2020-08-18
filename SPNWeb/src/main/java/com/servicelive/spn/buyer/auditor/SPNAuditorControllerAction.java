package com.servicelive.spn.buyer.auditor;

import com.servicelive.spn.core.SPNBaseAction;
import com.servicelive.domain.userprofile.User;
import com.servicelive.spn.services.LookupService;
import com.servicelive.spn.services.interfaces.IServiceProviderService;
import com.servicelive.spn.services.interfaces.IUploadedDocumentStateService;
/**
 * 
 */
public class SPNAuditorControllerAction extends SPNBaseAction
{
	private static final long serialVersionUID = 3539905711707559214L;
	
	private IServiceProviderService serviceProviderService;
	private IUploadedDocumentStateService uploadedDocumentStateService;
	private LookupService lookupService;
	
	// Use the next 2 variables to pass 2 values thru to the routed tab.  Save a DB call.
	private String newApplicantsCount = "0";
	private String reApplicantsCount = "0";
	// Code Added for Jira SL-19384
	// Variable for count of 'Membership Under Review' status 
	private String membershipUnderReview = "0";
	
	
	
	public int getNumberFive()
	{
		return 5;
	}
	
	/**
	 *  
	 * @return String
	 * @throws Exception
	 */
	public String route() throws Exception
	{
		Integer buyerId = Integer.valueOf(0); // Logged in buyer company
		
		User user = getLoggedInUser();
		if(user == null)
			return "loginPage"; //TODO - define a global login page result name
		
		
		buyerId = user.getUserId();
		
		
		SPNApplicantCounts applicantCounts = getApplicantCounts(buyerId);
		
		// Pass these thru as parameters to redirected action.
		//applicantCounts.setNewApplicantsCount(0);
		//applicantCounts.setReApplicantsCount(1);
		setNewApplicantsCount(applicantCounts.getNewApplicantsCount() + "");
		setReApplicantsCount(applicantCounts.getReApplicantsCount() + "");
		// Code Added for Jira SL-19384
		setMembershipUnderReview(applicantCounts.getMembershipUnderReviewCount() + "");

		String auditorTab = getRequest().getParameter("auditorTab");
		
		if("membership".equals(auditorTab) && applicantCounts.getMembershipUnderReviewCount() > 0){
			return "membership_under_review_tab";
		}
		if(applicantCounts.getNewApplicantsCount() > 0)
		{
			return	"new_applicants_tab";
		}		
		else if(applicantCounts.getReApplicantsCount() > 0)
		{
			return "re_applicants_tab";
		}
		// Code Added for Jira SL-19384
		else if(applicantCounts.getMembershipUnderReviewCount() > 0)
		{
			return "membership_under_review_tab";
		}
		else
		{
			return "search_tab";
		}
	}
	







	/**
	 * 
	 * @return ServiceProviderService
	 */
	public IServiceProviderService getServiceProviderService()
	{
		return serviceProviderService;
	}


	/**
	 * 
	 * @param serviceProviderService
	 */
	public void setServiceProviderService(IServiceProviderService serviceProviderService)
	{
		this.serviceProviderService = serviceProviderService;
	}


	/**
	 * 
	 * @return UploadedDocumentStateService
	 */
	public IUploadedDocumentStateService getUploadedDocumentStateService()
	{
		return uploadedDocumentStateService;
	}

	/**
	 * 
	 * @param uploadedDocumentStateService
	 */
	public void setUploadedDocumentStateService(IUploadedDocumentStateService uploadedDocumentStateService)
	{
		this.uploadedDocumentStateService = uploadedDocumentStateService;
	}


	/**
	 * 
	 * @return LookupService
	 */
	public LookupService getLookupService()
	{
		return lookupService;
	}


	/**
	 * 
	 * @param lookupService
	 */
	public void setLookupService(LookupService lookupService)
	{
		this.lookupService = lookupService;
	}




	/**
	 * 
	 * @return String
	 */
	public String getReApplicantsCount()
	{
		return reApplicantsCount;
	}




	/**
	 * 
	 * @param reApplicantsCount
	 */
	public void setReApplicantsCount(String reApplicantsCount)
	{
		this.reApplicantsCount = reApplicantsCount;
	}




	/**
	 * 
	 * @return String
	 */
	public String getNewApplicantsCount()
	{
		return newApplicantsCount;
	}




	/**
	 * 
	 * @param newApplicantsCount
	 */
	public void setNewApplicantsCount(String newApplicantsCount)
	{
		this.newApplicantsCount = newApplicantsCount;
	}

	public String getMembershipUnderReview() {
		return membershipUnderReview;
	}

	public void setMembershipUnderReview(String membershipUnderReview) {
		this.membershipUnderReview = membershipUnderReview;
	}

}
