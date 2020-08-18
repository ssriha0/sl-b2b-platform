package com.servicelive.routingrulesengine.vo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.common.Contact;
import com.servicelive.domain.routingrules.RoutingRuleHdrHist;

import static org.apache.commons.lang.StringUtils.replaceOnce;


/**
 * RoutingRules Header History transfer object
 */
public class RoutingRulesHdrHistVO
	implements Serializable
{
	private static final long serialVersionUID = 20091016L;
	private static final String DATE_FORMAT = "MM/dd/yyyy hh:mma zzz";

	private Integer routingRuleHdrHistId;
	private Integer routingRuleHdrId;
	private String ruleName;
	private String ruleStatus;
	private String ruleComment;
	private String ruleAction;
	private String modifiedBy;
	private String modifiedDate;
	private String email;
	private String comment;
	private String fileName;


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public static List<RoutingRulesHdrHistVO> getVOFromDomainObjects(List<RoutingRuleHdrHist> domainObjs, List<String> comments)
	{
		List<RoutingRulesHdrHistVO> vos = new ArrayList<RoutingRulesHdrHistVO>(domainObjs.size());

		for (int i=0; i<domainObjs.size(); i++) {

			final RoutingRulesHdrHistVO vo = new RoutingRulesHdrHistVO();
			RoutingRuleHdrHist domainObj = domainObjs.get(i);
			vo.setRoutingRuleHdrHistId(domainObj.getRoutingRuleHdrHistId());
			vo.setRoutingRuleHdrId(domainObj.getRoutingRuleHdr().getRoutingRuleHdrId());
			vo.setRuleName(domainObj.getRuleName());
			vo.setRuleStatus(domainObj.getRuleStatus());
			vo.setRuleComment(domainObj.getRuleComment());
			vo.setRuleAction(domainObj.getRuleAction());
			vo.setModifiedDate(domainObj.getModifiedDate());
			vo.setFileName(domainObj.getFileName());
			vo.setComment(comments.get(i));

			vo.setModifiedBy("");
			BuyerResource buyerResource = domainObj.getModifiedBy();
			if (buyerResource != null) {
				Contact contact = buyerResource.getContact();
				if (contact != null) {
					String modifiedByContactFirstNameLastNameResourceId = contact.getFirstName() + " " + contact.getLastName() + " ("+buyerResource.getResourceId()+")";
					vo.setModifiedBy(modifiedByContactFirstNameLastNameResourceId);
				}
			}
			vos.add(vo);
		}

		return vos;
	}


	/**
	 * @return the routingRuleHdrHistId
	 */
	public Integer getRoutingRuleHdrHistId()
	{
		return routingRuleHdrHistId;
	}


	/**
	 * @param routingRuleHdrHistId
	 *            the routingRuleHdrHistId to set
	 */
	public void setRoutingRuleHdrHistId(Integer routingRuleHdrHistId)
	{
		this.routingRuleHdrHistId = routingRuleHdrHistId;
	}


	/**
	 * @return the routingRuleHdrId
	 */
	public Integer getRoutingRuleHdrId()
	{
		return routingRuleHdrId;
	}


	/**
	 * @param routingRuleHdrId the routingRuleHdrId to set
	 */
	public void setRoutingRuleHdrId(Integer routingRuleHdrId)
	{
		this.routingRuleHdrId = routingRuleHdrId;
	}


	/**
	 * @return the ruleName
	 */
	public String getRuleName()
	{
		return ruleName;
	}


	/**
	 * @param ruleName the ruleName to set
	 */
	public void setRuleName(String ruleName)
	{
		this.ruleName = ruleName;
	}


	/**
	 * @return the ruleStatus
	 */
	public String getRuleStatus()
	{
		return ruleStatus;
	}


	/**
	 * @param ruleStatus the ruleStatus to set
	 */
	public void setRuleStatus(String ruleStatus)
	{
		this.ruleStatus = ruleStatus;
	}


	/**
	 * @return the ruleComment
	 */
	public String getRuleComment()
	{
		return ruleComment;
	}


	/**
	 * @param ruleComment
	 *            the ruleComment to set
	 */
	public void setRuleComment(String ruleComment)
	{
		this.ruleComment = ruleComment;
	}


	/**
	 * @return the ruleAction
	 */
	public String getRuleAction()
	{
		return ruleAction;
	}


	/**
	 * @param ruleAction the ruleAction to set
	 */
	public void setRuleAction(String ruleAction)
	{
		this.ruleAction = ruleAction;
	}


	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy()
	{
		return modifiedBy;
	}


	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy)
	{
		this.modifiedBy = modifiedBy;
	}


	/**
	 * @return the createdDate
	 */
	public String getModifiedDate()
	{
		return modifiedDate;
	}


	/**
	 * @param createdDate the createdDate to set
	 */
	public void setModifiedDate(Date modifiedDate)
	{
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        TimeZone gmtTime = TimeZone.getTimeZone("CST");
        formatter.setTimeZone(gmtTime);
        String dateStr= formatter.format(modifiedDate);
        dateStr= replaceOnce (dateStr, "am ", "AM ");
        dateStr= replaceOnce (dateStr, "pm ", "PM ");
        if(gmtTime.inDaylightTime(modifiedDate)){
        	dateStr = dateStr.replace("CST", "CDT");
        }
		this.modifiedDate = dateStr;
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


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
