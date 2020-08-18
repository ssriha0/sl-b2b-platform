package com.servicelive.domain.spn.detached;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servicelive.domain.lookup.LookupEmailTemplate;


/**
 * 
 * @author SVANLOO
 *
 */
public class Email implements Serializable {

	private static final long serialVersionUID = 20081219L;

	/**
	 *  overrides what's existing in the DB here...
	 */
	private List<String> toList = new ArrayList<String>();
	/**
	 * overrides what's in the DB otherwise keep null or empty
	 */
	private String from;  
	/**
	 * overrides what's in the DB otherwise keep null or empty
	 * @deprecated set up the template in cheetah mail
	 */
	@Deprecated
	private String subject;
	/**
	 * try not to use this one.. It would kill the purpose of the template in the DB ...overrides what's in the DB otherwise keep null or empty
	 * @deprecated set up the msg in cheetah mail
	 */
	@Deprecated
	private String msg;
	/**
	 * recommended to use this Map Dynamically
	 */
	private Map<String, String> params = new HashMap<String,String>();
	/**
	 * gets most info needed for the sending Email from here
	 */
	private LookupEmailTemplate template;
	/**
	 * @deprecated This is probably not used in cheetah mail.  It's really a reference to an html link which can be included in the params
	 */
	@Deprecated
	private List<EmailClassPathResource> classPathResources = new ArrayList<EmailClassPathResource>();

	/**
	 * 
	 */
	public Email() {
		super();
	}

	/**
	 * @return the to
	 */
	public List<String> getToList() {
		return toList;
	}

	/**
	 * @param to the to to set
	 */
	public void addTo(String to) {
		this.toList.add(to);
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the params
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void addParam(String key, String value) {
		this.params.put(key, value);
	}

	/**
	 * @return the template
	 */
	public LookupEmailTemplate getTemplate() {
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(LookupEmailTemplate template) {
		this.template = template;
	}

	/**
	 * @return the classPathResources
	 */
	public List<EmailClassPathResource> getClassPathResources() {
		return classPathResources;
	}

	/**
	 * @param classPathResources the classPathResources to set
	 */
	public void setClassPathResources(
			List<EmailClassPathResource> classPathResources) {
		this.classPathResources = classPathResources;
	}

	/**
	 * @param toList the toList to set
	 */
	public void setToList(List<String> toList) {
		this.toList = toList;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
}
