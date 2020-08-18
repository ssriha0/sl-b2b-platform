package com.newco.marketplace.web.dto;

public class FMAccountInfoDTO extends SerializedBaseDTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6657267820892592814L;
	private String selectedAccountType;
	private String description;
	private String eftAccountType;
	private String routingNumber;
	private String reenterRoutingNumber;	
	private String institution;
	private String acctNumber;
	private String reenterAcctNumber;
	private String lockboxNumber;
	private String firstName;
	private String lastName;
	private String ssn;
	public String getSelectedAccountType()
	{
		return selectedAccountType;
	}
	public void setSelectedAccountType(String selectedAccountType)
	{
		this.selectedAccountType = selectedAccountType;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getEftAccountType()
	{
		return eftAccountType;
	}
	public void setEftAccountType(String eftAccountType)
	{
		this.eftAccountType = eftAccountType;
	}
	public String getRoutingNumber()
	{
		return routingNumber;
	}
	public void setRoutingNumber(String routingNumber)
	{
		this.routingNumber = routingNumber;
	}
	public String getReenterRoutingNumber()
	{
		return reenterRoutingNumber;
	}
	public void setReenterRoutingNumber(String reenterRoutingNumber)
	{
		this.reenterRoutingNumber = reenterRoutingNumber;
	}
	public String getInstitution()
	{
		return institution;
	}
	public void setInstitution(String institution)
	{
		this.institution = institution;
	}
	public String getAcctNumber()
	{
		return acctNumber;
	}
	public void setAcctNumber(String acctNumber)
	{
		this.acctNumber = acctNumber;
	}
	public String getReenterAcctNumber()
	{
		return reenterAcctNumber;
	}
	public void setReenterAcctNumber(String reenterAcctNumber)
	{
		this.reenterAcctNumber = reenterAcctNumber;
	}
	public String getLockboxNumber()
	{
		return lockboxNumber;
	}
	public void setLockboxNumber(String lockboxNumber)
	{
		this.lockboxNumber = lockboxNumber;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	public String getSsn()
	{
		return ssn;
	}
	public void setSsn(String ssn)
	{
		this.ssn = ssn;
	}
	
	
}
