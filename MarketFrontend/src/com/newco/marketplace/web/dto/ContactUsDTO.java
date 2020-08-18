package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.newco.marketplace.interfaces.OrderConstants;
import org.apache.commons.lang.StringUtils;

public class ContactUsDTO extends SOWBaseTabDTO implements OrderConstants{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1619241756797680794L;

	public static final String emailAddressPattern =
    	"\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
	
	private SOWContactLocationDTO contact = new SOWContactLocationDTO();
	private String category;
	private String subject;
	private String comments;

	
	public SOWContactLocationDTO getContact()
	{
		return contact;
	}

	public void setContact(SOWContactLocationDTO contact)
	{
		this.contact = contact;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getComments()
	{
		return comments;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}
	
	@Override
	public void validate() {
		clearAllErrors();
		setErrors(new ArrayList<IError>());
		
		//First Name is required
        if ((StringUtils.isBlank(contact.getFirstName())) || ((contact.getFirstName()).equalsIgnoreCase("[First Name]")))
        {
        	addError("First Name", FIRST_NAME_REQUIRED, OrderConstants.SOW_TAB_ERROR);
        }
                
       //Last Name is required
        if ((StringUtils.isBlank(contact.getLastName())) || ((contact.getLastName()).equalsIgnoreCase("[Last Name]")))
        {
        	addError("Last Name", LAST_NAME_REQUIRED, OrderConstants.SOW_TAB_ERROR);
        }
        
       //Email is required
        if ((StringUtils.isBlank(contact.getEmail())) || ((contact.getEmail()).equalsIgnoreCase("[E-mail Address]")))
        {
        	addError("Email", EMAIL_REQUIRED, OrderConstants.SOW_TAB_ERROR);
        }else if ((!StringUtils.isBlank(contact.getEmail())) && (validateEmail(contact.getEmail()) == false)){
        	//Email is present, check for validity of email address
        	addError("Email", VALID_EMAIL_ADDRESS, OrderConstants.SOW_TAB_ERROR);
        }
        
       //Comment is required
        if (StringUtils.isBlank(comments))
        {
        	addError("Comment", COMMENT_REQUIRED, OrderConstants.SOW_TAB_ERROR);
        }else{//To check for the special chars - for XSS
        	String pattern="[<>{}\\[\\];\\&()]";
        	Pattern p = Pattern.compile(pattern);
        	if(p.matcher(comments).find()){
        		comments = "";
        		addError("Comment", VALID_COMMENT, OrderConstants.SOW_TAB_ERROR);
        	}
        }

		
	}
	
	public boolean validateEmail(String email){
		Pattern pattern;
		Matcher matcher;
		boolean validEmail = true;
		pattern = Pattern.compile(emailAddressPattern);
		matcher = pattern.matcher(email);
		if (!matcher.matches())
			validEmail = false;
		
		return validEmail;
		
	}
		
	@Override
	public String getTabIdentifier()
	{
		return null;
	}
	




}
