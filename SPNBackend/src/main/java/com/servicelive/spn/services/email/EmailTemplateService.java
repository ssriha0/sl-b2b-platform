/**
 * 
 */
package com.servicelive.spn.services.email;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servicelive.domain.lookup.LookupEmailTemplate;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.spn.dao.lookup.LookupEmailTemplateDao;
import com.servicelive.spn.services.BaseServices;

/**
 * @author svanloo
 *
 */
public class EmailTemplateService extends BaseServices {
	private LookupEmailTemplateDao lookupEmailTemplateDao;
	private Map<String, List<LookupEmailTemplate>> emailTemplateCache = null;
	private static final String DELIMITER = "#";

	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.BaseServices#handleDates(java.lang.Object)
	 */
	@SuppressWarnings("unused")
	@Override
	protected void handleDates(Object entity) {
		//do nothing

	}

	/**
	 * @param action
	 * @param state
	 * @return List<LookupEmailTemplate>
	 */
	public List<LookupEmailTemplate> findEmailTemplatesByLookupSPNWorkflowState(LookupSPNWorkflowState state, String action) {
		if(emailTemplateCache == null) {
			try {
				emailTemplateCache = createEmailTemplateMap();
			} catch (Exception e) {
				throw new RuntimeException("Couldn't initialize emailCache", e);
			}
		}
		String key = createKey(state.getId(), action);
		List<LookupEmailTemplate> templates = emailTemplateCache.get(key);
		if(templates == null) {
			templates = new ArrayList<LookupEmailTemplate>();
		}
		return templates;
	}

	/**
	 * 
	 * @return List
	 * @throws Exception
	 */
	private List<LookupEmailTemplate> findAllEmailTemplates() throws Exception {
		return lookupEmailTemplateDao.findAll();
	}

	private Map<String, List<LookupEmailTemplate>> createEmailTemplateMap() throws Exception {
		Map<String, List<LookupEmailTemplate>> cache = new HashMap<String, List<LookupEmailTemplate>>();

		List<LookupEmailTemplate> emailTemplates = findAllEmailTemplates();
		for(LookupEmailTemplate template:emailTemplates) {
			LookupSPNWorkflowState tempState = template.getWorkflowState();
			String key = createKey(tempState.getId(), template.getAction());
			List<LookupEmailTemplate> tempEmailTemplates = cache.get(key);
			if(tempEmailTemplates == null) {
				tempEmailTemplates = new ArrayList<LookupEmailTemplate>();
				cache.put(key, tempEmailTemplates);
			}
			tempEmailTemplates.add(template);
		}
		return cache;
	}

	private String createKey(Object state, String action) {
		return state.toString() + DELIMITER + action;
	}

	/**
	 * @param lookupEmailTemplateDao the lookupEmailTemplateDao to set
	 */
	public void setLookupEmailTemplateDao(LookupEmailTemplateDao lookupEmailTemplateDao) {
		this.lookupEmailTemplateDao = lookupEmailTemplateDao;
	}

}
