package com.newco.marketplace.persistence.daoImpl.provider;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.dto.vo.provider.TeamMemberVO;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.vo.buyer.BuyerResourceVO;
import com.newco.marketplace.vo.provider.LookupVO;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 *
 *
 */
public class LookupDAOImpl extends ABaseImplDao implements ILookupDAO {
	/**
	 * Unmanaged cache for states
	 */
	private static List stateList;

	/**
	 * Unmanaged cache for primary industry for providers
	 */
	private static List primaryIndustryForProviders;

	/**
	 * Unmanaged cache for primary industry buyers
	 */
	private static List primaryIndustryForBuyers;

	/**
	 * Unmanaged cache for company roles
	 */
	private static List roleList;

	/**
	 * Unmanaged cache for referrals
	 */
	private static List referralList;

	/**
	 *
	 */
	public LookupDAOImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.newco.marketplace.dao.lookup.LookupDAO#load()
	 */
	public List loadSecretQuestions() throws DBException {
		List result;
		try {
			result = queryForList("secret_questions_lookup.query", null);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @LookupDAOImpl.loadSecretQuestions() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadSecretQuestions() due to " + ex.getMessage(), ex);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.newco.marketplace.dao.lookup.LookupDAO#load()
	 */
	public List loadReferrals() throws DBException {
		if (referralList == null) {
			List result = null;
			try {
				result = queryForList("referral_cds_lookup.query", null);
				referralList = result;
			} catch (Exception ex) {
				logger.info("General Exception @LookupDAOImpl.loadReferrals() due to" + ex.getMessage());
				throw new DBException("General Exception @LookupDAOImpl.loadReferrals() due to " + ex.getMessage(), ex);
			}
		}
		return referralList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.newco.marketplace.dao.lookup.LookupDAO#load()
	 */
	public List loadCompanySize() throws DBException {
		List result = null;
		try {
			result = queryForList("company_size_lookup.query", null);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadCompanySize() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadCompanySize() due to " + ex.getMessage(), ex);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.newco.marketplace.dao.lookup.LookupDAO#load()
	 */
	public List loadBusinessTypes() throws DBException {
		List result = null;
		try {
			result = queryForList("business_type_lookup.query", null);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadBusinessTypes() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadBusinessTypes() due to " + ex.getMessage(), ex);
		}
		return result;
	}

	// Changes Starts for Admin Name Change 
	@SuppressWarnings("unchecked")
	public List loadProviderList(Integer vendor_id) throws DBException {
		List result = null;
		try {
			result =(List<TeamMemberVO>)queryForList("business_firm_provider_list.query", vendor_id);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadproviderList() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadproviderList() due to " + ex.getMessage(), ex);
		}
		return result;
	}
	// Changes Ends for Admin Name Change -->	
	
	
	/**
	 *
	 */
	public List loadStates() throws DBException {
		if (stateList == null) {
			try {
				stateList = queryForList("state_code_lookup.query", null);
			} catch (Exception ex) {
				logger.info("General Exception @LookupDAOImpl.loadStates() due to" + ex.getMessage());
				throw new DBException("General Exception @LookupDAOImpl.loadStates() due to " + ex.getMessage(), ex);
			}
		}
		return stateList;
	}

	public List loadMinorityTypes() throws DBException {
		List result;
		try {
			result = queryForList("minority_type_lookup.query", null);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadMinorityTypes() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadMinorityTypes() due to " + ex.getMessage(), ex);
		}
		return result;
	}

	public List loadServiceAreaRadius() throws DBException {
		List result;
		try {
			result = queryForList("service_area_radius_lookup.query", null);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadServiceAreaRadius() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadServiceAreaRadius() due to " + ex.getMessage(), ex);
		}
		return result;
	}

	public List loadWarrantyPeriods() throws DBException {
		List result;
		try {
			result = queryForList("warranty_periods_lookup.query", null);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadWarrantyPeriods() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadWarrantyPeriods() due to " + ex.getMessage(), ex);
		}
		return result;
	}

	public List loadSalesVolume() throws DBException {
		List result;
		try {
			result = queryForList("sales_volume_lookup.query", null);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadSalesVolume() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadSalesVolume() due to " + ex.getMessage(), ex);
		}
		return result;
	}

	public List loadForeignOwnedPercent() throws DBException {
		List result;
		try {
			result = queryForList("foreign_owned_percent.query", null);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadForeignOwnedPercent() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadForeignOwnedPercent() due to " + ex.getMessage(), ex);
		}
		return result;
	}

	public List loadStatus(String type) throws DBException {
		List result;
		try {
			result = queryForList("wf_states.query", type);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadStatus() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadStatus() due to " + ex.getMessage(), ex);
		}
		return result;
	}

	public List loadReasonCodes(LookupVO lookupVO) throws DBException {
		List result;
		try {
			result = queryForList("audit_reason_codes_load_by_id.query", lookupVO);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadReasonCodes() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadReasonCodes() due to " + ex.getMessage(), ex);
		}
		return result;
	}

	public List loadReasonCodeListOnClick(LookupVO lookupVO) throws DBException {
		List result;
		try {
			result = queryForList("audit_reason_codes_by_click.query", lookupVO);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadReasonCodeListOnClick() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadReasonCodeListOnClick() due to " + ex.getMessage(), ex);
		}
		return result;
	}

	public List loadPrimaryIndustry() throws DBException {
		try {
			primaryIndustryForProviders = queryForList("primary_industry.query", null);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadPrimaryIndustry() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadPrimaryIndustry() due to " + ex.getMessage(), ex);
		}
		return primaryIndustryForProviders;

	}

	public List loadBuyerPrimaryIndustry() throws DBException {
		if (primaryIndustryForBuyers == null) {
			try {
				primaryIndustryForBuyers = queryForList("buyer_primary_industry.query", null);
			} catch (Exception ex) {
				logger.info("General Exception @LookupDAOImpl.loadPrimaryIndustry() due to" + ex.getMessage());
				throw new DBException("General Exception @LookupDAOImpl.loadPrimaryIndustry() due to " + ex.getMessage(), ex);
			}
		}
		return primaryIndustryForBuyers;

	}

	/**
	 * Function to fetch the Primary and Secondary Contact Method.
	 */
	public List loadContactMethod() throws DBException {
		List result;
		try {
			result = queryForList("contact_method_lookup.query", null);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadContactMethod() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadContactMethod() due to " + ex.getMessage(), ex);
		}
		return result;
	}

	public List loadGeneralActivity() throws DBException {
		List result;
		try {
			result = queryForList("user_permissions_list.query", null);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadGeneralActivity() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadGeneralActivity() due to " + ex.getMessage(), ex);
		}
		return result;
	}

	public List loadTimeZone() throws DBException {
		List result;
		try {
			result = queryForList("timezone_list.query", null);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadTimeZone() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadTimeZone() due to " + ex.getMessage(), ex);
		}
		return result;
	}

	public List loadLanguages() throws DBException {
		List result;
		try {
			result = queryForList("language.query", null);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadLanguages() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadLanguages() due to " + ex.getMessage(), ex);
		}
		return result;

	}

	public List loadTimeInterval() throws DBException {
		List result;
		try {
			result = queryForList("time_interval_list.query", null);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadTimeInterval() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadTimeInterval() due to " + ex.getMessage(), ex);
		}
		return result;
	}

	/**
	 *
	 * @param lookupVO
	 * @return LookupVO
	 * @throws DBException
	 */
	public LookupVO loadTermsConditions(String type) throws DBException {
		LookupVO lookupVO;
		try {
			lookupVO = (LookupVO) queryForObject("terms_conditions_query", type);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadTermsConditions() due to" + ex.getMessage(), ex);
			throw new DBException("General Exception @LookupDAOImpl.loadTermsConditions() due to " + ex.getMessage(), ex);
		}
		return lookupVO;
	}

	/**
	 * This method is used to fetch terms and conditions for simple buyer registration Join Now screen.
	 *
	 * @param lookupVO
	 * @return List
	 * @throws DBException
	 */
	@SuppressWarnings("unchecked")
	public List<LookupVO> loadSimpleBuyerTermsConditions() throws DBException {
		List<LookupVO> lookupVOList;
		try {
			lookupVOList = queryForList("termsCond.simpleBuyer.query", null);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadSimpleBuyerTermsConditions() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadSimpleBuyerTermsConditions() due to " + ex.getMessage(), ex);
		}
		return lookupVOList;
	}

	/**
	 *
	 * @param lookupVO
	 * @return LookupVO
	 * @throws DBException
	 */

	public LookupVO isStateActive(String selectedState) throws DBException {
		LookupVO lookupVO;
		try {
			lookupVO = (LookupVO) queryForObject("is_state_active.query", selectedState);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.isSateActive() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.isSateActive() due to " + ex.getMessage(), ex);
		}

		return lookupVO;
	}

	/**
	 * 
	 */
	public String getPrimaryIndustry(int primaryId) throws DBException {
		String primaryIndustry;
		try {
			primaryIndustry = (String) queryForObject("getPrimaryIndustry.query", Integer.valueOf(primaryId));
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.getPrimaryIndustry() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.getPrimaryIndustry() due to " + ex.getMessage(), ex);
		}
		return primaryIndustry;
	}

	/**
	 *
	 */
	public String getBusinessType(int typeId) throws DBException {
		String businessType;
		try {
			businessType = (String) queryForObject("getBusinessType.query", Integer.valueOf(typeId));
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.getBusinessType() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.getBusinessType() due to " + ex.getMessage(), ex);
		}
		return businessType;
	}

	/**
	 *
	 * @param typeId
	 * @return
	 * @throws DBException
	 */
	public String getCompanySize(int typeId) throws DBException {
		String companySize;
		try {
			companySize = (String) queryForObject("getCompanySize.query", Integer.valueOf(typeId));
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.getCompanySize() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.getCompanySize() due to " + ex.getMessage(), ex);
		}
		return companySize;
	}

	/**
	 *
	 * @param typeId
	 * @return
	 * @throws DBException
	 */
	public String getForeignOwnPct(int typeId) throws DBException {
		String foreignOwnPct;
		try {
			foreignOwnPct = (String) queryForObject("getForeignOwnPct.query", Integer.valueOf(typeId));
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.getForeignOwnPct() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.getForeignOwnPct() due to " + ex.getMessage(), ex);
		}
		return foreignOwnPct;
	}

	/**
	 *
	 * @param typeId
	 * @return String
	 * @throws DBException
	 */
	public String getCompanyRole(int typeId) throws DBException {
		String companyRole;
		try {
			companyRole = (String) queryForObject("getCompanyRole.query", Integer.valueOf(typeId));
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.getCompanyRole() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.getCompanyRole() due to " + ex.getMessage(), ex);
		}
		return companyRole;
	}

	/**
	 *
	 * @param typeId
	 * @return String
	 * @throws DBException
	 */
	public String getWarrantyPeriod(int typeId) throws DBException {
		String warrantyPeriod;
		try {
			warrantyPeriod = (String) queryForObject("getWarrantPeriod.query", Integer.valueOf(typeId));
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.getWarrantyPeriod() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.getWarrantyPeriod() due to " + ex.getMessage(), ex);
		}
		return warrantyPeriod;
	}

	/**
	 *
	 * @param lookupVO
	 * @return LookupVO
	 * @throws DBException
	 */
	public LookupVO getActivityId(LookupVO lookupVO) throws DBException {
		LookupVO dblookupVO;
		try {
			dblookupVO = (LookupVO) queryForObject("user_activity_id.query", lookupVO);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.getActivityId() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.getActivityId() due to " + ex.getMessage(), ex);
		}
		return dblookupVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.ILookupDAO#getAllCredentailCategories()
	 */
	@SuppressWarnings("unchecked")
	public List<LookupVO> getAllCredentailCategories() throws DBException {
		List<LookupVO> result;
		try {
			result = queryForList("vendor_credential_categories.query", null);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.getAllCredentailCategories() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.getAllCredentailCategories() due to " + ex.getMessage(), ex);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.ILookupDAO#getCredentailCategoriesByType()
	 */
	@SuppressWarnings("unchecked")
	public List<LookupVO> getCredentailCategoriesByType(Integer typeId) throws DBException {
		List<LookupVO> result;
		try {
			result = queryForList("vendor_categories_by_credential.query", typeId);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.getCredentailCategoriesByType() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.getCredentailCategoriesByType() due to " + ex.getMessage(), ex);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.ILookupDAO#getCredentialTypes()
	 */
	@SuppressWarnings("unchecked")
	public List<LookupVO> getCredentialTypes() throws DBException {
		List<LookupVO> result;
		try {
			result = queryForList("vendor_credential_types.query", null);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.getAllCredentailCategories() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.getAllCredentailCategories() due to " + ex.getMessage(), ex);
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.ILookupDAO#getAllResourceCredentailCategories()
	 */
	@SuppressWarnings("unchecked")
	public List<LookupVO> getAllResourceCredentailCategories() throws DBException {
		List<LookupVO> result;
		try {
			result = queryForList("resource_credential_categories.query", null);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.getAllCredentailCategories() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.getAllCredentailCategories() due to " + ex.getMessage(), ex);
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.ILookupDAO#getResourceCredentailCategoriesByType(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<LookupVO> getResourceCredentailCategoriesByType(Integer typeId) throws DBException {
		List<LookupVO> result;
		try {
			result = queryForList("resource_categories_by_credential.query", typeId);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.getCredentailCategoriesByType() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.getCredentailCategoriesByType() due to " + ex.getMessage(), ex);
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.ILookupDAO#getResourceCredentialTypes()
	 */
	@SuppressWarnings("unchecked")
	public List<LookupVO> getResourceCredentialTypes() throws DBException {
		List<LookupVO> result;
		try {
			result = queryForList("resource_credential_types.query", null);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.getAllCredentailCategories() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.getAllCredentailCategories() due to " + ex.getMessage(), ex);
		}
		return result;
	}
	
	// Changes Starts for Buyer Admin Name Change SL-20461
	@SuppressWarnings("unchecked")
	public List loadBuyerList(Integer buyerId) throws DBException {
		List result = null;
		logger.info("Inside loadBuyerList LookupDaoImpl.java");
		try {
			result =(List)queryForList("business_buyer_list.query", buyerId);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadBuyerList() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadBuyerList() due to " + ex.getMessage(), ex);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<BuyerResourceVO> loadOldBuyerDetails(Integer vendor_id) throws DBException {
		List<BuyerResourceVO> result = new ArrayList<BuyerResourceVO>();
		try {
			result =(List<BuyerResourceVO>)queryForList("buyer_old_admin_details.query", vendor_id);
		} catch (Exception ex) {
			logger.info("General Exception @LookupDAOImpl.loadOldBuyerDetails() due to" + ex.getMessage());
			throw new DBException("General Exception @LookupDAOImpl.loadOldBuyerDetails() due to " + ex.getMessage(), ex);
		}
		return result;
	}
	// Changes Ends for Buyer Admin Name Change SL-20461

	//---------AddProviderSkill API------
		public List loadRootSkillName() throws DBException {
			List result = null;
			
			try {
				result = queryForList("lookup.fetchRootSkillName.query", null);
			} catch (Exception ex) {
				logger.info("General Exception @LookupDAOImpl.loadRootSkillName() due to" + ex.getMessage());
				throw new DBException("General Exception @LookupDAOImpl.loadRootSkillName() due to " + ex.getMessage(), ex);
			}
			return result;
		}
		public List loadChildSkillName(String nodeId) throws DBException {
			List result = null;
			
			try {
				result = queryForList("lookup.fetchChildSkillName.query", Integer.parseInt(nodeId));
			} catch (Exception ex) {
				logger.info("General Exception @LookupDAOImpl.loadChildSkillName() due to" + ex.getMessage());
				throw new DBException("General Exception @LookupDAOImpl.loadChildSkillName() due to " + ex.getMessage(), ex);
			}
			return result;
		}
		public List loadServiceTypes(String nodeId) throws DBException {
			List result = null;
			
			try {
				result = queryForList("lookup.fetchServiceTypes.query", Integer.parseInt(nodeId));
			} catch (Exception ex) {
				logger.info("General Exception @LookupDAOImpl.loadfetchServiceTypes() due to" + ex.getMessage());
				throw new DBException("General Exception @LookupDAOImpl.loadfetchServiceTypes() due to " + ex.getMessage(), ex);
			}
			return result;
		}

}
