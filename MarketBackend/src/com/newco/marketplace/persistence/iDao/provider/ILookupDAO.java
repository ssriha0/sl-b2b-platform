package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;


import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.buyer.BuyerResourceVO;
import com.newco.marketplace.vo.provider.LookupVO;

/**
 * Interface for SecretQuestionDAO implementations
 *
 * @version
 * @author blars04
 *
 */
public interface ILookupDAO{

    public List loadSecretQuestions() throws DBException;
    public List loadReferrals()throws DBException;
    public List loadCompanySize()throws DBException;
    public List loadBusinessTypes()throws DBException;
    public List loadStates()throws DBException;
    public List loadMinorityTypes()throws DBException;
    public List loadServiceAreaRadius()throws DBException;
    public List loadWarrantyPeriods()throws DBException;
    public List loadSalesVolume()throws DBException;
    public List loadForeignOwnedPercent()throws DBException;
    public List loadStatus(String type)throws DBException;
    public List loadReasonCodes(LookupVO lookupVO)throws DBException;
    public List loadReasonCodeListOnClick(LookupVO lookupVO)throws DBException;
    public List loadPrimaryIndustry()throws DBException;
    public List loadBuyerPrimaryIndustry()throws DBException;
    public List loadContactMethod() throws DBException;
    public List loadGeneralActivity() throws DBException;
    public List loadTimeZone() throws DBException;
    public List loadLanguages() throws DBException;
    public List loadTimeInterval() throws DBException;
    public LookupVO loadTermsConditions(String type) throws DBException;
    public List<LookupVO> loadSimpleBuyerTermsConditions() throws DBException;
    public LookupVO isStateActive(String selectedState) throws DBException;
    public String getPrimaryIndustry(int primaryId) throws DBException;
    public String getBusinessType(int typeId) throws DBException;
    public String getCompanySize(int typeId) throws DBException;
    public String getForeignOwnPct(int typeId) throws DBException;
    public String getCompanyRole(int typeId) throws DBException;
    public String getWarrantyPeriod(int typeId) throws DBException;
    public LookupVO getActivityId(LookupVO  lookupVO) throws DBException;
    public List<LookupVO> getCredentialTypes() throws DBException;
    public List<LookupVO> getResourceCredentialTypes() throws DBException;
    public List<LookupVO> getAllCredentailCategories() throws DBException;
    public List<LookupVO> getAllResourceCredentailCategories() throws DBException;
    public List<LookupVO> getCredentailCategoriesByType(Integer typeId) throws DBException;
    public List<LookupVO> getResourceCredentailCategoriesByType(Integer typeId) throws DBException;
    public List loadProviderList(Integer vendor_id) throws DBException;
    //SL-20461 changes starts
	public List<String> loadBuyerList(Integer buyerId) throws DBException;
    //SL-20461 changes ends
	public List<BuyerResourceVO> loadOldBuyerDetails(Integer buyerId) throws DBException;
	
	//AddProviderSkillAPI
		public List loadRootSkillName() throws DBException;
		public List loadChildSkillName(String nodeId) throws DBException;
		public List loadServiceTypes(String nodeId) throws DBException;
    
}//end class SecretQuestionDAO