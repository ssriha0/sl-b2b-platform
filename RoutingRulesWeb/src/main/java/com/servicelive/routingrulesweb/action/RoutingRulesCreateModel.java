package com.servicelive.routingrulesweb.action;

import java.io.Serializable;
import java.util.List;

import com.servicelive.routingrulesengine.vo.RuleConflictDisplayVO;


/**
 * This model holds the criteria the user has chosen from the LHS combo boxes on the Create/Edit
 * Rule page, as displayed on the RHS. Note that the LHS combo boxes are populated with
 * getXXXChoices() methods on the action class.
 * 
 * @author Steve Hunter
 * 
 */
public class RoutingRulesCreateModel
	implements Serializable
{
	public enum UserAction { VIEW, CREATE, EDIT }

	private static final long serialVersionUID = 20090925L;

	private String chosenProviderFirmsJson;
	private String chosenCriterionJson;
	private String chosenSpecsJobsJson;
	
	private String rulename, ruleId ;
	private String ruleStatus;
	private String firstname, lastname;
	private String email;
	private String tabType;
	private UserAction userAction;
	private List<RuleConflictDisplayVO> ruleConflictDisplayVO;

	
	/**
	 * @return the rulename
	 */
	public String getRulename()
	{
		return rulename;
	}


	/**
	 * @param rulename the rulename to set
	 */
	public void setRulename(String rulename)
	{
		this.rulename = rulename;
	}


	/**
	 * @return the firstname
	 */
	public String getFirstname()
	{
		return firstname;
	}


	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}


	/**
	 * @return the lastname
	 */
	public String getLastname()
	{
		return lastname;
	}


	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}


	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}


	/**
	 * @return the chosenProviderFirms as a JSON string
	 */
	public String getChosenProviderFirmsJson()
	{		
		return chosenProviderFirmsJson;
	}


	/**
	 * @param chosenProviderFirmsJson the chosenProviderFirms to set
	 */
	public void setChosenProviderFirmsJson(String chosenProviderFirmsJson)
	{
		this.chosenProviderFirmsJson= chosenProviderFirmsJson;
	}


	/**
	 * @return the userAction
	 */
	public UserAction getUserAction()
	{
		return userAction;
	}


	/**
	 * @param userAction the userAction to set
	 */
	public void setUserAction(UserAction userAction)
	{
		this.userAction = userAction;
	}


	/**
	 * @return the chosenCriterionJson
	 */
	public String getChosenCriterionJson()
	{
		return chosenCriterionJson;
	}


	/**
	 * @param chosenCriterionJson the chosenCriterionJson to set
	 */
	public void setChosenCriterionJson(String chosenCriterionJson)
	{
		this.chosenCriterionJson = chosenCriterionJson;
	}


	/**
	 * @return the chosenSpecsJobsJson
	 */
	public String getChosenSpecsJobsJson()
	{
		return chosenSpecsJobsJson;
	}


	/**
	 * @param chosenSpecsJobsJson the chosenSpecsJobsJson to set
	 */
	public void setChosenSpecsJobsJson(String chosenSpecsJobsJson)
	{
		this.chosenSpecsJobsJson = chosenSpecsJobsJson;
	}


	/**
	 * @return the ruleId
	 */
	public String getRuleId()
	{
		return ruleId;
	}


	/**
	 * @param ruleId the ruleId to set
	 */
	public void setRuleId(String ruleId)
	{
		this.ruleId = ruleId;
	}


	public String getTabType() {
		return tabType;
}


	public void setTabType(String tabType) {
		this.tabType = tabType;
	}


	public List<RuleConflictDisplayVO> getRuleConflictDisplayVO() {
		return ruleConflictDisplayVO;
	}


	public void setRuleConflictDisplayVO(
			List<RuleConflictDisplayVO> ruleConflictDisplayVO) {
		this.ruleConflictDisplayVO = ruleConflictDisplayVO;
	}
	
	public String getRuleStatus() {
		return ruleStatus;
	}
	
	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
	}



}
