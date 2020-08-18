package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.interfaces.OrderConstants;

/**
 * $Revision: 1.12 $ $Author: glacy $ $Date: 2008/04/26 01:13:45 $
 */

/*
 * Maintenance History
 * $Log: ETMSearchDTO.java,v $
 * Revision 1.12  2008/04/26 01:13:45  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.10.2.1  2008/04/23 11:41:31  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.11  2008/04/23 05:19:46  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.10  2008/03/04 01:31:16  langara
 * *** empty log message ***
 *
 * Revision 1.9  2008/02/26 18:18:00  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.8.2.3  2008/02/25 21:16:41  usawant
 * Added changes for reading the selected languages list
 *
 * Revision 1.8.2.2  2008/02/22 21:16:52  usawant
 * Passing searchkey to backend made changes to initFacility and corresponding changes.
 *
 * Revision 1.8.2.1  2008/02/22 17:25:47  cgarc03
 * Added 'yesNoRadio' data member and getter/setter.
 *
 * Revision 1.8  2008/02/18 21:01:25  usawant
 * *** empty log message ***
 *
 * Revision 1.7  2008/02/17 05:40:54  usawant
 * *** empty log message ***
 *
 * Revision 1.6  2008/02/16 08:03:10  usawant
 * Made changes for incorporating CriteriaHandlerFacility for searching, sorting, pagination, filter criteria.
 *
 * Revision 1.5  2008/02/15 19:39:04  usawant
 * Changed filed names to match to DTO field names
 *
 * Revision 1.4  2008/02/14 23:44:49  mhaye05
 * Merged Feb4_release branch into head
 *
 */
public class ETMSearchDTO extends SOWBaseTabDTO implements OrderConstants{

	private static final long serialVersionUID = -8936949245454207018L;
	private List<SkillNodeVO> skillTreeMainCatList;
	private List<LookupVO> credentialsList;
	private List<LookupVO> languagesList;
	private List <LookupVO> ratingsList;
	private List <LookupVO> distanceList;
	private Map<String,Object> selectTopProviderList;
	private List<SPNMonitorVO> spnList;
	private List<LookupVO> performanceLevelList;
	private List<LookupVO> yesNoList; // to change the order 
	//private HashMap yesNoRadio = new HashMap();
	
	
	private String skillTreeMainCat="-1";
	private String marketReadySelection = "1";
	private String zipCd;
	private String distance="-1";
	private String credentials="-1";
	private String credentialCategory="-1";
	private String languages="-1";
	private List<Integer> selectedLanguagesList;
	private String ratings="-1";
	private Integer spn = 0;
	private List<String> perfLevels;
	
	public List<SkillNodeVO> getSkillTreeMainCatList() {
		return skillTreeMainCatList;
	}
	public void setSkillTreeMainCatList(List<SkillNodeVO> skillTreeMainCatList) {
		this.skillTreeMainCatList = skillTreeMainCatList;
	}
	public List<LookupVO> getCredentialsList() {
		return credentialsList;
	}
	public void setCredentialsList(List<LookupVO> credentialsList) {
		this.credentialsList = credentialsList;
	}
	public List<LookupVO> getLanguagesList() {
		return languagesList;
	}
	public void setLanguagesList(List<LookupVO> languagesList) {
		this.languagesList = languagesList;
	}
	/*public Map<String, Object> getRatingsList() {
		return ratingsList;
	}
	public void setRatingsList(Map<String, Object> ratingsList) {
		this.ratingsList = ratingsList;
	}
	public Map<String, Object> getDistanceList() {
		return distanceList;
	}
	public void setDistanceList(Map<String, Object> distanceList) {
		this.distanceList = distanceList;
	}*/
	public Map<String, Object> getSelectTopProviderList() {
		return selectTopProviderList;
	}
	public void setSelectTopProviderList(Map<String, Object> selectTopProviderList) {
		this.selectTopProviderList = selectTopProviderList;
	}
	public String getSkillTreeMainCat() {
		return skillTreeMainCat;
	}
	public void setSkillTreeMainCat(String skillTreeMainCat) {
		this.skillTreeMainCat = skillTreeMainCat;
	}
	public String getMarketReadySelection() {
		return marketReadySelection;
	}
	public void setMarketReadySelection(String marketReadySelection) {
		this.marketReadySelection = marketReadySelection;
	}
	public String getZipCd() {
		return zipCd;
	}
	public void setZipCd(String zipCd) {
		this.zipCd = zipCd;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getCredentials() {
		return credentials;
	}
	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}
	public String getCredentialCategory() {
		return credentialCategory;
	}
	public void setCredentialCategory(String credentialCategory) {
		this.credentialCategory = credentialCategory;
	}
	public String getRatings() {
		return ratings;
	}
	public void setRatings(String ratings) {
		this.ratings = ratings;
	}
	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void validate(String function) {
		if (function.equalsIgnoreCase("search")){
			if (skillTreeMainCat.equalsIgnoreCase("-1"))
		       {
		       	addError("Service Category", ENTER_SERVICE_CATEGORY, OrderConstants.SOW_TAB_ERROR);
		       }
			if ((zipCd == null)  || (zipCd.equalsIgnoreCase("")) )
		       {
		       	addError("Zip Cd", ENTER_ZIP, OrderConstants.SOW_TAB_ERROR);
		       }
			else if (!isZip5Digits(zipCd)){
				addError("Zip Cd", ENTER_FIVE_DIGITS_ZIP, OrderConstants.SOW_TAB_ERROR);
			}
		}else
		{
			if (distance.equalsIgnoreCase("-1") && 
					credentials.equalsIgnoreCase("-1") &&
					credentialCategory.equalsIgnoreCase("-1") &&
					ratings.equalsIgnoreCase("-1") &&
					languages.equalsIgnoreCase("-1")){
				
				addError("Search Filter", ENTER_ETM_FILTER, OrderConstants.SOW_TAB_ERROR);
			}
					
		}
		
	}
	
	private boolean isZip5Digits(String zipCd){
		boolean result = false;
		 if(zipCd == null) return result;
		 if(StringUtils.isNumeric(zipCd) && zipCd.length() == 5){
			 return true;
		 }
		return result;
	}
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}
	public String getLanguages() {
		return languages;
	}
	public void setLanguages(String languages) {
		this.languages = languages;
	}
	public List<Integer> getSelectedLanguagesList() {
		String selectedLanguages = getLanguages();
		selectedLanguagesList = new ArrayList();
		if ((selectedLanguages != null) && (!(selectedLanguages.equalsIgnoreCase("-1")))){
			String[] languages = selectedLanguages.split (", ");
			for (int i=0; i < languages.length; i++){
				if (!(languages[i].equalsIgnoreCase("-1"))){
					selectedLanguagesList.add(languages[i]!=null?new Integer(languages[i]):null);
				}
			}
		}	
		return selectedLanguagesList;
	}
	public void setSelectedLanguagesList(List<Integer> selectedLanguagesList) {
		this.selectedLanguagesList = selectedLanguagesList;
	}
	/**
	 * @return the spnList
	 */
	public List<SPNMonitorVO> getSpnList() {
		return spnList;
	}
	/**
	 * @param spnList the spnList to set
	 */
	public void setSpnList(List<SPNMonitorVO> spnList) {
		this.spnList = spnList;
	}
	/**
	 * @return the spn
	 */
	public Integer getSpn() {
		return spn;
	}
	/**
	 * @param spn the spn to set
	 */
	public void setSpn(Integer spn) {
		this.spn = spn;
	}
	/**
	 * @return the ratingsList
	 */
	public List<LookupVO> getRatingsList() {
		return ratingsList;
	}
	/**
	 * @param ratingsList the ratingsList to set
	 */
	public void setRatingsList(List<LookupVO> ratingsList) {
		this.ratingsList = ratingsList;
	}
	/**
	 * @return the distanceList
	 */
	public List<LookupVO> getDistanceList() {
		return distanceList;
	}
	/**
	 * @param distanceList the distanceList to set
	 */
	public void setDistanceList(List<LookupVO> distanceList) {
		this.distanceList = distanceList;
	}
	/**
	 * @return the perfLevels
	 */
	public List<String> getPerfLevels() {
		return perfLevels;
	}
	/**
	 * @param perfLevels the perfLevels to set
	 */
	public void setPerfLevels(List<String> perfLevels) {
		this.perfLevels = perfLevels;
	}
	/**
	 * @return the performanceLevelList
	 */
	public List<LookupVO> getPerformanceLevelList() {
		return performanceLevelList;
	}
	/**
	 * @param performanceLevelList the performanceLevelList to set
	 */
	public void setPerformanceLevelList(List<LookupVO> performanceLevelList) {
		this.performanceLevelList = performanceLevelList;
	}
	/**
	 * @return the yesNoList
	 */
	public List<LookupVO> getYesNoList() {
		return yesNoList;
	}
	/**
	 * @param yesNoList the yesNoList to set
	 */
	public void setYesNoList(List<LookupVO> yesNoList) {
		this.yesNoList = yesNoList;
	}

	
}
