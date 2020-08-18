package com.newco.marketplace.persistence.daoImpl.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao;
import com.newco.marketplace.persistence.iDao.provider.IBusinessinfoDao;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.provider.BusinessinfoVO;
import org.apache.commons.lang.StringUtils;

/**
 * @author KSudhanshu
 *
 */
public class BusinessinfoDaoImpl extends SqlMapClientDaoSupport implements IBusinessinfoDao {

	private final Log logger = LogFactory.getLog(getClass());
	private IActivityRegistryDao activityRegistryDao;
	private static List primaryIndustry;
	private static final Logger localLogger = Logger
	.getLogger(LookupDAOImpl.class.getName());


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.persistence.iDao.IBusinessinfoDao#query(com.newco.marketplace.web.dto.BusinessinfoDto)
	 */
	public BusinessinfoVO save(BusinessinfoVO objBusinessinfoVO) {

		try {
				
				if(objBusinessinfoVO.getVendorId() != null)
				{// Changes Starts for Admin name change
					
                    if(StringUtils.isNotBlank(objBusinessinfoVO.getNewAdminResourceId()) && StringUtils.isNotBlank(objBusinessinfoVO.getOldAdminResourceId()) && objBusinessinfoVO.getNewAdminResourceId()!=objBusinessinfoVO.getOldAdminResourceId()){
                    Map<String,String> changeAdminNameMap=new HashMap<String,String>();
                    Map<String,String> auditChangeAdminNameMap=new HashMap<String,String>();
					changeAdminNameMap.put("v_new_resrc_id", objBusinessinfoVO.getNewAdminResourceId());
					changeAdminNameMap.put("v_old_resrc_id", objBusinessinfoVO.getOldAdminResourceId());
                    auditChangeAdminNameMap.put("OldAdminName",objBusinessinfoVO.getLastName()+","+objBusinessinfoVO.getFirstName()+"("+objBusinessinfoVO.getOldAdminResourceId()+")#");
                    auditChangeAdminNameMap.put("NewAdminName",objBusinessinfoVO.getNewAdminName());
                    auditChangeAdminNameMap.put("Vendor_Id", objBusinessinfoVO.getVendorId());
                    auditChangeAdminNameMap.put("modified_by", objBusinessinfoVO.getModifiedBy());
                    auditChangeAdminNameMap.put("associated_entity", "Provider Firm");
					getSqlMapClient().queryForObject("updateProviderAdminName.procedure",changeAdminNameMap);
					objBusinessinfoVO.setAdminInd(1);
					localLogger.info("Sucessfully Changed Admin: From: "+objBusinessinfoVO.getOldAdminResourceId()+" To: "+objBusinessinfoVO.getNewAdminResourceId());
					getSqlMapClient().insert("auditAdminNameChange.insert", auditChangeAdminNameMap);
					localLogger.info("Sucessfully inserted data in Table audit_provider_record");
                    }// Changes Ends for Admin name change
	
					getSqlMapClient().update("updateW9TaxStatusId.update",
								objBusinessinfoVO);
				
					getSqlMapClient().update("updateLocation.update",
							objBusinessinfoVO);
					//SL-19226 R6_3:Mail Address is not getting updated when saving it from the Front end
					//Added new update query to update mailing address
					getSqlMapClient().update("updateLocationMailingAddress.update",
							objBusinessinfoVO);
					getSqlMapClient().update("updateContactinfo.update",
							objBusinessinfoVO);
					getSqlMapClient().update("updateVendorresource.update",
							objBusinessinfoVO);
					getSqlMapClient().update("updateBusinessinfo.update",
							objBusinessinfoVO);
					

					if(getAnnualSalesRevenue(objBusinessinfoVO.getVendorId()) != null){
						getSqlMapClient().update("updateBusinessinfoVdrFinance.update",
							objBusinessinfoVO);
					}else if(objBusinessinfoVO.getAnnualSalesRevenue() != "-1"){
						objBusinessinfoVO = (BusinessinfoVO) getSqlMapClient().insert("insertBusinessinfoVdrFinance.insert",
								objBusinessinfoVO);
					}
				} else {
					objBusinessinfoVO = (BusinessinfoVO) getSqlMapClient().insert("insertBusinessinfo.insert",
							objBusinessinfoVO);
					objBusinessinfoVO = (BusinessinfoVO) getSqlMapClient().insert("insertBusinessinfoVdrFinance.insert",
							objBusinessinfoVO);
				}
				getActivityRegistryDao().updateActivityStatus(objBusinessinfoVO.getVendorId(), ActivityRegistryConstants.BUSINESSINFO);
		} catch (Exception ex) {
			logger.info("[BusinessinfoDaoImpl.insert or update - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
		}

		return objBusinessinfoVO;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.persistence.iDao.IBusinessinfoDao#query(com.newco.marketplace.web.dto.BusinessinfoDto)
	 */
	public BusinessinfoVO getData(BusinessinfoVO objBusinessinfoVO) {

		try {
			if(objBusinessinfoVO.getVendorId() != null ){
				objBusinessinfoVO = (BusinessinfoVO) getSqlMapClient().queryForObject("selectBusinessinfo.query",
						objBusinessinfoVO);
				objBusinessinfoVO.setAnnualSalesRevenue(getAnnualSalesRevenue(objBusinessinfoVO.getVendorId()));
				
				
			}
		} catch (Exception ex) {
			logger.info("[BusinessinfoDaoImpl.select - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
		}

		return objBusinessinfoVO;
	}
	
	
	public String getAnnualSalesRevenue(String vendor_id){
		String intAnnualSalesRevenue= null;
		String strAnnualSalesRevenue = null;
		
		try {
			intAnnualSalesRevenue = (String)getSqlMapClient().queryForObject("selectBusinessinfoVdrFinance.query", vendor_id);
			/*
			if(strAnnualSalesRevenue== null || strAnnualSalesRevenue.equals("")){
				intAnnualSalesRevenue = 0;
			}else{
				intAnnualSalesRevenue = (Integer.parseInt(strAnnualSalesRevenue));
			}
			*/
		} catch (Exception ex) {
			logger.info("[BusinessinfoDaoImpl.select - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
		}
		
		return intAnnualSalesRevenue; 
	}
	
	

	/**
	 * @return the activityRegistryDao
	 */
	public IActivityRegistryDao getActivityRegistryDao() {
		return activityRegistryDao;
	}

	/**
	 * @param activityRegistryDao the activityRegistryDao to set
	 */
	public void setActivityRegistryDao(IActivityRegistryDao activityRegistryDao) {
		this.activityRegistryDao = activityRegistryDao;
	}
	
	public String getCompanySizeDesc(Integer id)
	{
		String companySize = null;
		
		try {
			companySize = (String)getSqlMapClient().queryForObject("query.selectcompanysize", id);
			
		} catch (Exception ex) {
			logger.info("[BusinessinfoDaoImpl.getCompanySizeDesc - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
		}
		return companySize;
	}
	
	
	public BusinessinfoVO updateVendorFinance(BusinessinfoVO objBusinessinfoVO) throws DBException {

		try {
			
			if(objBusinessinfoVO.getVendorId() != null)
			{
				if(null!=objBusinessinfoVO.getAnnualSalesRevenue())
				{
					getSqlMapClient().update("updateBusinessinfoVdrFinance.update",objBusinessinfoVO);
				}
			}
		} catch (Exception ex) {
			logger.info("[BusinessinfoDaoImpl.updateVendorFinance - Exception] "
					+ ex.getMessage());
			
			throw new DBException(
					"General Exception @BusinessinfoDaoImpl.updateVendorFinance() due to "
							+ ex.getMessage());
		}

		return objBusinessinfoVO;
	}
	
	public BusinessinfoVO updateVendorAddressDetails(BusinessinfoVO objBusinessinfoVO) throws DBException {

		try {
				
			if(objBusinessinfoVO.getVendorId() != null)
			{
			
				if(null!=objBusinessinfoVO.getBusinessStreet1() && null!=objBusinessinfoVO.getBusinessCity() && null!=objBusinessinfoVO.getBusinessState() 
						&& null!=objBusinessinfoVO.getBusinessZip())
				{
					getSqlMapClient().update("updateLocation.update",objBusinessinfoVO);
				}

				if(null!=objBusinessinfoVO.getMailingStreet1() && null!=objBusinessinfoVO.getMailingCity() && null!=objBusinessinfoVO.getMailingState() 
						&& null!=objBusinessinfoVO.getMailingZip())
				{
					getSqlMapClient().update("updateLocationMailingAddress.update",objBusinessinfoVO);
				}
				
				if(null!= objBusinessinfoVO.getJobTitle() || null!=objBusinessinfoVO.getEmail() || null!=objBusinessinfoVO.getAltEmail())
				{	
					getSqlMapClient().update("updateContactinfo.update",objBusinessinfoVO);
				}
			}
		} catch (Exception ex) {
			logger.info("[BusinessinfoDaoImpl.updateVendorAddressDetails - Exception] "
					+ ex.getMessage());
			
			throw new DBException(
					"General Exception @BusinessinfoDaoImpl.updateVendorAddressDetails() due to "
							+ ex.getMessage());
		}

		return objBusinessinfoVO;
	}
	
	
	public BusinessinfoVO updateVendorHdr(BusinessinfoVO objBusinessinfoVO) throws DBException {

		try {
				if(objBusinessinfoVO.getVendorId() != null)
				{
					
					
					
					if(StringUtils.isNotBlank(objBusinessinfoVO.getDunsNo()) || null!=objBusinessinfoVO.getCompanySize() 
							||(null!=objBusinessinfoVO.getIsForeignOwned() && null!=objBusinessinfoVO.getForeignOwnedPct())
							|| null!=objBusinessinfoVO.getPrimaryIndustry() || StringUtils.isNotBlank(objBusinessinfoVO.getDescription())
							|| null!=objBusinessinfoVO.getBusinessPhone() || null!=objBusinessinfoVO.getBusPhoneExtn() 
							|| null!=objBusinessinfoVO.getBusinessFax() || null!=objBusinessinfoVO.getWebAddress() 
							|| null!=objBusinessinfoVO.getBusStartDt())
					{		
					getSqlMapClient().update("updateBusinessinfoVhdr.update",
							objBusinessinfoVO);
					}
				
					
				} 
		} catch (Exception ex) {
			logger.info("[BusinessinfoDaoImpl.updateVendorHdr - Exception] "
					+ ex.getMessage());
			
			throw new DBException(
					"General Exception @BusinessinfoDaoImpl.updateVendorHdr() due to "
							+ ex.getMessage());
		}

		return objBusinessinfoVO;
	}
	
	
	public BusinessinfoVO updateW9ForVendorHdr(BusinessinfoVO objBusinessinfoVO) throws DBException {
		try {
				if(objBusinessinfoVO.getVendorId() != null)
				{
					
					if(null!=objBusinessinfoVO.getBusStructure())
					{
						getSqlMapClient().update("updateW9TaxStatusId.update",objBusinessinfoVO);
					}
					
				} 
		} catch (Exception ex) {
			logger.info("[BusinessinfoDaoImpl.updateW9ForVendorHdr - Exception] "
					+ ex.getMessage());
				 
		}

		return objBusinessinfoVO;
	}
	
	
	public BusinessinfoVO updateVendorResource(BusinessinfoVO objBusinessinfoVO) throws DBException {

		try {
				
				if(objBusinessinfoVO.getVendorId() != null)
				{
					if((objBusinessinfoVO.getOwnerInd() == 1) || (objBusinessinfoVO.getDispatchInd() == 1) 
							|| (objBusinessinfoVO.getManagerInd() == 1) || (objBusinessinfoVO.getAdminInd() == 1)
							|| (objBusinessinfoVO.getOtherInd() == 1) || (objBusinessinfoVO.getSproInd() == 1))
					{
					getSqlMapClient().update("updateVendorResourceInfo.update",
							objBusinessinfoVO);
					}
					
				}
		} catch (Exception ex) {
			logger.info("[BusinessinfoDaoImpl.updateVendorResource - Exception] "
					+ ex.getMessage());
			
			throw new DBException(
					"General Exception @BusinessinfoDaoImpl.updateVendorResource() due to "
							+ ex.getMessage());
		}

		return objBusinessinfoVO;
	}

}

