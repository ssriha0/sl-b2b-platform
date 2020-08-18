package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import java.util.List;

import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;

/**
 * @author zizrale
 *
 */
public class LanguageParameterBean extends RatingParameterBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4569314594912860770L;

	private Integer languageId;
	
	private List<Integer> selectedLangs;
	
	
	
	public List<Integer> getSelectedLangs() {
		return selectedLangs;
	}

	public void setSelectedLangs(List<Integer> selectedLangs) {
		this.selectedLangs = selectedLangs;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
	

}
