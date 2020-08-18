package com.newco.marketplace.web.action.contactus;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.alert.AlertFactory;
import com.newco.marketplace.business.businessImpl.alert.AlertQueueProcessor;
import com.newco.marketplace.business.businessImpl.alert.EmailAlertQueueProcessor;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.dto.ContactUsDTO;
import com.newco.marketplace.web.dto.DropdownOptionDTO;
import com.newco.marketplace.web.dto.SOWContactLocationDTO;
import com.newco.marketplace.web.security.NonSecurePage;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@NonSecurePage
public class ContactUsAction extends SLBaseAction implements Preparable, ModelDriven<ContactUsDTO>
{
	private static final long serialVersionUID = 10002;//arbitrary number to get rid of warning
	private static final Logger logger = Logger
    .getLogger("DashboardAction");
	
	ISOWizardFetchDelegate fetchDelegate;
	
	private ContactUsDTO contactUsDTO = new ContactUsDTO();
	
	private String selectedPhoneType = "foo";
	private String selectedCategory = "bar";
	private ArrayList <LookupVO> phoneTypes=null;
	private ArrayList<DropdownOptionDTO> categoryDropdowns;
	private SOWContactLocationDTO contact= new SOWContactLocationDTO();
	
	public ContactUsAction(ISOWizardFetchDelegate fetchDelegate ) {
		this.fetchDelegate = fetchDelegate;
	}
	


	/**
	 * Description of the Method
	 * 
	 * @exception Exception
	 *                Description of the Exception
	 */
	public void prepare() throws Exception
	{
		// Get the phone dropdown list
		if(fetchDelegate != null)
		{
			phoneTypes = fetchDelegate.getPhoneTypes();
			setAttribute("phoneTypes", phoneTypes);
		}
		
		// Category Dropdown list
		initCategoryDropdowns();
	}
	
	private void initCategoryDropdowns()
	{
		categoryDropdowns = new ArrayList<DropdownOptionDTO>();
		DropdownOptionDTO dto;
		dto = new DropdownOptionDTO("Account & Password Assistance", "1", null);
		categoryDropdowns.add(dto);
		dto = new DropdownOptionDTO("Provider Registration", "2", null);
		categoryDropdowns.add(dto);
		dto = new DropdownOptionDTO("Service Order Process", "3", null);
		categoryDropdowns.add(dto);
		dto = new DropdownOptionDTO("Rules and Policies", "4", null);
		categoryDropdowns.add(dto);
		dto = new DropdownOptionDTO("Money Transfers/Payment", "5", null);
		categoryDropdowns.add(dto);
		dto = new DropdownOptionDTO("Sales/Partners", "6", null);
		categoryDropdowns.add(dto);
		dto = new DropdownOptionDTO("Suggestions/Feedback", "7", null);
		categoryDropdowns.add(dto);
		dto = new DropdownOptionDTO("Website Issues/Problems", "8", null);
		categoryDropdowns.add(dto);
		dto = new DropdownOptionDTO("Other", "9", null);
		categoryDropdowns.add(dto);
		setAttribute("categoryDropdownList", categoryDropdowns);
		
	}

	public String execute() throws Exception
	{
		return SUCCESS;
	}

	public String submit() throws Exception
	{
		contactUsDTO = getModel();
		contactUsDTO.validate();
		
		if (contactUsDTO.getErrors().size() > 0){
			return "failure";
		}else
		{
		StringBuffer message=new StringBuffer("Email from Contact Us Link");
		message.append("\nFirst Name :").append(contactUsDTO.getContact().getFirstName());
		message.append("\nLast Name  :").append(contactUsDTO.getContact().getLastName());		
		message.append("\nEmail      :").append(contactUsDTO.getContact().getEmail());		
		message.append("\nCategory   :").append(contactUsDTO.getCategory());		
		message.append("\nSubject    :").append(contactUsDTO.getSubject());		
		message.append("\nComments   :").append(contactUsDTO.getComments());		

		AlertQueueProcessor alertQueueProcessor = (EmailAlertQueueProcessor) AlertFactory.getAlertQueueProcessor("1");
		alertQueueProcessor.sendMessage(contactUsDTO.getContact().getEmail(), "support@servicelive.com", message.toString(), contactUsDTO.getSubject(), "", "");
		return SUCCESS;
		}
		
	}
	
	


	public ContactUsDTO getModel() {
		
		return contactUsDTO;
	}	
	
	public void setModel(ContactUsDTO dto) {

		this.contactUsDTO = dto;
	}
	
	
	public ISOWizardFetchDelegate getFetchDelegate()
	{
		return fetchDelegate;
	}



	public void setFetchDelegate(ISOWizardFetchDelegate fetchDelegate)
	{
		this.fetchDelegate = fetchDelegate;
	}



	public String getSelectedPhoneType()
	{
		return selectedPhoneType;
	}



	public void setSelectedPhoneType(String selectedPhoneType)
	{
		this.selectedPhoneType = selectedPhoneType;
	}



	public String getSelectedCategory()
	{
		return selectedCategory;
	}



	public void setSelectedCategory(String selectedCategory)
	{
		this.selectedCategory = selectedCategory;
	}



	public ArrayList<LookupVO> getPhoneTypes()
	{
		return phoneTypes;
	}



	public void setPhoneTypes(ArrayList<LookupVO> phoneTypes)
	{
		this.phoneTypes = phoneTypes;
	}



	public SOWContactLocationDTO getContact()
	{
		return contact;
	}



	public void setContact(SOWContactLocationDTO contact)
	{
		this.contact = contact;
	}



	public ContactUsDTO getContactUsDTO()
	{
		return getModel();
	}



	public void setContactUsDTO(ContactUsDTO contactUsDTO)
	{
		this.contactUsDTO = contactUsDTO;
	}



	public ArrayList<DropdownOptionDTO> getCategoryDropdowns()
	{
		return categoryDropdowns;
	}



	public void setCategoryDropdowns(ArrayList<DropdownOptionDTO> categoryDropdowns)
	{
		this.categoryDropdowns = categoryDropdowns;
	}
	
	
}
