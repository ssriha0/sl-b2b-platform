/**
 *
 */
package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.LeadVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderWfStatesCountsVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.provider.IVendorHdrDao;
import com.newco.marketplace.vo.hi.provider.ApproveFirmsVO;
import com.newco.marketplace.vo.hi.provider.ApproveProvidersVO;
import com.newco.marketplace.vo.hi.provider.BackgroundCheckHistoryProviderVO;
import com.newco.marketplace.vo.hi.provider.BackgroundCheckProviderVO;
import com.newco.marketplace.vo.hi.provider.ReasonCodeVO;
import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.vo.provider.VendorResource;

/**
 * @author sahmad7
 */
public class VendorHdrDaoImpl extends SqlMapClientDaoSupport implements IVendorHdrDao {
	private static final Logger logger = Logger.getLogger(VendorHdrDaoImpl.class);



	//private static final MessageResources messages = MessageResources.getMessageResources("DataAccessLocalStrings");

	public int update(VendorHdr vendorHdr) throws DBException {
		int result = 0;
		try {
			result = getSqlMapClient().update("pvendor_hdr.update", vendorHdr);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorHdrDaoImpl.update() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorHdrDaoImpl.update() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @VendorHdrDaoImpl.update() due to"
					+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorHdrDaoImpl.update() due to "
							+ ex.getMessage());
		}
		return result;
		//return getSqlMapClient().update("pvendor_hdr.update", vendorHdr);
	}

	
	public int updateLicenseAndInsuranceInd(VendorHdr vendorHdr) throws DBException {
		int result = 0;
		try {
			result = getSqlMapClient().update("pvendor_hdr_ind.update", vendorHdr);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorHdrDaoImpl.update() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorHdrDaoImpl.update() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @VendorHdrDaoImpl.update() due to"
					+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorHdrDaoImpl.update() due to "
							+ ex.getMessage());
		}
		return result;
		//return getSqlMapClient().update("pvendor_hdr.update", vendorHdr);
	}

	
	
	public int updateEIN(VendorHdr vendorHdr) throws DBException {
		int result = 0;
		try {
			result = getSqlMapClient().update("pvendor_hdr.updateEIN", vendorHdr);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorHdrDaoImpl.update() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorHdrDaoImpl.update() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @VendorHdrDaoImpl.update() due to"
					+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorHdrDaoImpl.update() due to "
							+ ex.getMessage());
		}
		return result;
		//return getSqlMapClient().update("pvendor_hdr.update", vendorHdr);
	}

	public int updateFM(VendorHdr vendorHdr) throws DBException {
		int result = 0;
		try {
			result = getSqlMapClient().update("pvendor_hdr.updateFM", vendorHdr);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorHdrDaoImpl.updateFM() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorHdrDaoImpl.updateFM() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @VendorHdrDaoImpl.updateFM() due to"
					+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorHdrDaoImpl.updateFM() due to "
							+ ex.getMessage());
		}
		return result;
		//return getSqlMapClient().update("pvendor_hdr.update", vendorHdr);
	}
	public int updateFMBuyer(Buyer buyer)throws DBException {
		int result = 0;  
		try {
			result = getSqlMapClient().update("buyer.updateFM", buyer);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception buyerDaoimpl.updateFM() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception buyerDaoimpl.updateFM() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception buyerDaoimpl.updateFM() due to"
					+ ex.getMessage());
			throw new DBException(
					"General Exception @buyerDaoimpl.updateFM() due to "
							+ ex.getMessage());
		}
		return result;
	}

	public VendorHdr query(VendorHdr vendorHdr) throws DBException {
		//return (VendorHdr) getSqlMapClient().queryForObject("pvendor_hdr.query", vendorHdr);
		VendorHdr result = null;

		try {
			result = (VendorHdr) getSqlMapClient().queryForObject(
					"pvendor_hdr.query", vendorHdr);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorHdrDaoImpl.query() due to"
				 	+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorHdrDaoImpl.query() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @VendorHdrDaoImpl.query() due to"
					+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorHdrDaoImpl.query() due to "
							+ ex.getMessage());
		}
		return result;
	}
	public Integer queryForBuyer(Integer vendorId) throws DBException
 	{
 		Integer result=null;
 		try {
			result = (Integer) getSqlMapClient().queryForObject(
					"buyercontact.query", vendorId);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorHdrDaoImpl.query() due to"
				 	+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorHdrDaoImpl.query() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @VendorHdrDaoImpl.query() due to"
					+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorHdrDaoImpl.query() due to "
							+ ex.getMessage());
		}
		return result;

 	}
	public Buyer queryForBuyer1(String vendorId) throws DBException {
		//return (VendorHdr) getSqlMapClient().queryForObject("pvendor_hdr.query", vendorHdr);
		Buyer result = null;

		try {
			result = (Buyer) getSqlMapClient().queryForObject(
					"buyercontact1.query", vendorId);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorHdrDaoImpl.query() due to"
				 	+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorHdrDaoImpl.query() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @VendorHdrDaoImpl.query() due to"
					+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorHdrDaoImpl.query() due to "
							+ ex.getMessage());
		}
		return result;
	}

	public VendorHdr insert(VendorHdr vendorHdr) throws DBException {
		Integer id = null;
		try {
			id = (Integer) getSqlMapClient().insert("pvendor_hdr.insert",
					vendorHdr);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorHdrDaoImpl.insert() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorHdrDaoImpl.insert() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @VendorHdrDaoImpl.insert() due to"
					+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorHdrDaoImpl.insert() due to "
							+ ex.getMessage());
		}
		//logger.debug("insert success id " +id);
		System.out.println("here is the id number (" + id + ")");
		vendorHdr.setVendorId(id.intValue());
		//logger.error("insert success id " + vendorHdr.getVendorId());
		return vendorHdr;
	}

	public List queryList(VendorHdr vendorHdr) throws DBException {
		List result = null;
		try {
			result = getSqlMapClient().queryForList("pvendor_hdr.query",
					vendorHdr);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorHdrDaoImpl.queryList() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorHdrDaoImpl.queryList() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @VendorHdrDaoImpl.queryList() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorHdrDaoImpl.queryList() due to "
							+ ex.getMessage());
		}
		return result;
		//return getSqlMapClient().queryForList("pvendor_hdr.query", vendorHdr);
	}

	public int updateQTnC(VendorHdr vendorHdr) throws DBException {
		//return getSqlMapClient().update("pvendor_hdr.updateQTnC", vendorHdr);
		int result = 0;
		try {
			result = getSqlMapClient().update("pvendor_hdr.updateQTnC",
					vendorHdr);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorHdrDaoImpl.updateQTnC() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorHdrDaoImpl.updateQTnC() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @VendorHdrDaoImpl.updateQTnC() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorHdrDaoImpl.updateQTnC() due to "
							+ ex.getMessage());
		}
		return result;
	}

	public int updateAuditClaimedBy(VendorHdr vendorHdr) throws DBException {
		Integer result = null;
		try {
			result = getSqlMapClient().update("pvendor_hdr.auditClaimedBy",
					vendorHdr);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @VendorHdrDaoImpl.updateAuditClaimedBy() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorHdrDaoImpl.updateAuditClaimedBy() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @VendorHdrDaoImpl.updateAuditClaimedBy() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorHdrDaoImpl.updateAuditClaimedBy() due to "
							+ ex.getMessage());
		}
		return result;
	}

	public VendorHdr queryAuditClaimedBy(VendorHdr vendorHdr)
			throws DBException {
		VendorHdr result = new VendorHdr();
		try {
			//logger.debug("VendorHdrDaoImpl.queryAuditClaimedBy " + vendorHdr.getVendorId());
			result = (VendorHdr) getSqlMapClient().queryForObject(
					"pvendor_hdr.getAuditClaimedBy", vendorHdr);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @VendorHdrDaoImpl.queryAuditClaimedBy() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorHdrDaoImpl.queryAuditClaimedBy() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @VendorHdrDaoImpl.queryAuditClaimedBy() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorHdrDaoImpl.queryAuditClaimedBy() due to "
							+ ex.getMessage());
		}
		return result;
	}

	public void updateWFStateId(VendorHdr vendorHdr) throws DBException {
		try {
			getSqlMapClient().update("pvendor_hdr.updateWFstate", vendorHdr);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @VendorHdrDaoImpl.updateWFStateId() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorHdrDaoImpl.updateWFStateId() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @VendorHdrDaoImpl.updateWFStateId() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorHdrDaoImpl.updateWFStateId() due to "
							+ ex.getMessage());
		}
	}

	public List<VendorHdr> queryCompaniesReadyForApproval() throws DBException {
		List<VendorHdr> vendorList = null;
		try {
			vendorList = (List<VendorHdr>)getSqlMapClient().queryForList("pvendor_hdr.getCompaniesReadyForApproval", null);
		} catch (SQLException ex) {
			logger.info("SQL Exception @VendorHdrDaoImpl.queryCompaniesReadyForApproval() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorHdrDaoImpl.queryCompaniesReadyForApproval() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			logger.info("General Exception @VendorHdrDaoImpl.queryCompaniesReadyForApproval() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorHdrDaoImpl.queryCompaniesReadyForApproval() due to "
							+ ex.getMessage());
		}
		return vendorList;
	}

	public int UpdateDbInsuranceSelection(VendorHdr vendorHdr)
			throws DBException {
		int result = 0;
		try {
			result = getSqlMapClient().update(
					"pvendor_hdr.UpdateDbInsuranceSelection", vendorHdr);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @VendorHdrDaoImpl.UpdateDbInsuranceSelection() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorHdrDaoImpl.UpdateDbInsuranceSelection() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @VendorHdrDaoImpl.UpdateDbInsuranceSelection() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorHdrDaoImpl.UpdateDbInsuranceSelection() due to "
							+ ex.getMessage());
		}
		return result;
		//return getSqlMapClient().update("pvendor_hdr.UpdateDbInsuranceSelection", vendorHdr);
	}

	public String getAnnualSalesVolume(int vendorId) throws DBException {
		String result = null;
		try {

			result = (String) getSqlMapClient().queryForObject(
					"getSalesAnnualVolume.query", vendorId);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @VendorHdrDaoImpl.getAnnualSalesVolume() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorHdrDaoImpl.getAnnualSalesVolume() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @VendorHdrDaoImpl.getAnnualSalesVolume() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @VendorHdrDaoImpl.queryAuditClaimedBy() due to "
							+ ex.getMessage());
		}
		return result;
	}

	public List<VendorResource> getResourceAndStatus(Integer vendorId) throws DBException{
		List<VendorResource> resultSet = new ArrayList<VendorResource>();

		try {

			resultSet = (List<VendorResource>) getSqlMapClient().queryForList(
					"getResourceAndStatus.query", vendorId);
		} catch (Exception ex) {
			logger.info("SQL Exception @VendorHdrDaoImpl.getResourceAndStatus() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @VendorHdrDaoImpl.getResourceAndStatus() due to "
							+ ex.getMessage());
		}
		return resultSet;
	}


	public String getStatusForResource(Integer resourceId) throws DBException {
		String currentStatus = null;
		try {
			currentStatus = (String)
					getSqlMapClient().queryForObject("getStatusForResource.query", resourceId);
		}catch(Exception ex) {
			logger.info("SQL Exception @VendorHdrDaoImpl.getStatusForResource() due to"
					+ ex.getMessage());
				throw new DBException(
			"SQL Exception @VendorHdrDaoImpl.getStatusForResource() due to "
					+ ex.getMessage());
		}
		return currentStatus;
	}

	public String getVendorCurrentStatus(Integer vendorId) throws DBException {
		String result = null;
		try {
			result = (String)getSqlMapClient().queryForObject("getVendorCurrentStatus.query",vendorId);
		}
        catch (Exception ex) {
        	logger.info("[VendorHdrDaoImpl--> getVendorCurrentStatus() --> Exception] "+ex.getMessage());
            throw new DBException("[VendorHdrDaoImpl--> getVendorCurrentStatus() --> Exception]", ex);
        }

		return result;
	}

	public Integer getVendorWFStateId(int vendorId) throws DBException {
		Integer wfStateId = 0;
		try
		{
			wfStateId = (Integer)getSqlMapClient().queryForObject("pvendor_hdr.getWFstate",vendorId);
		}
        catch (Exception ex)
        {
        	logger.info("[VendorHdrDaoImpl--> getVendorWFStateId() --> Exception] "+ex.getMessage());
            throw new DBException("[VendorHdrDaoImpl--> getVendorWFStateId() --> Exception]", ex);
        }

		return wfStateId;
	}
	
	public Integer getWCIInd(Integer vendorId) throws DBException {
		Integer indicator= -1;
		try
		{
			indicator = (Integer)getSqlMapClient().queryForObject("pvendor_hdr.getWCIInd",vendorId);
		}
        catch (Exception ex)
        {
        	logger.info("[VendorHdrDaoImpl--> getWCIInd() --> Exception] "+ex.getMessage());
            throw new DBException("[VendorHdrDaoImpl--> getWCIInd() --> Exception]", ex);
        
        }

		return indicator;
	}
	public Integer updateCBGLInsurance(CredentialProfile credentialProfile)throws DBException{
		 int result = 0;
			try {
				result = getSqlMapClient().update(
						"pvendor_hdr.updateCBGLIndAndAmount", credentialProfile);
			} catch (SQLException ex) {
				ex.printStackTrace();
				logger
						.info("SQL Exception @VendorHdrDaoImpl.UpdateDbInsuranceSelection() due to"
								+ ex.getMessage());
				throw new DBException(
						"SQL Exception @VendorHdrDaoImpl.UpdateDbInsuranceSelection() due to "
								+ ex.getMessage());
			} 
			return result;
	 }
	    public Integer updateWCInsurance(CredentialProfile credentialProfile)throws DBException{
	    	int result = 0;
			try {
				result = getSqlMapClient().update(
						"pvendor_hdr.updateWCIndAndAmount", credentialProfile);
			} catch (SQLException ex) {
				ex.printStackTrace();
				logger
						.info("SQL Exception @VendorHdrDaoImpl.UpdateDbInsuranceSelection() due to"
								+ ex.getMessage());
				throw new DBException(
						"SQL Exception @VendorHdrDaoImpl.UpdateDbInsuranceSelection() due to "
								+ ex.getMessage());
			} 
			return result;
	    }
	    public Integer updateVLInsurance(CredentialProfile credentialProfile)throws DBException{
	    	int result = 0;
			try {
				result = getSqlMapClient().update(
						"pvendor_hdr.updateVLIndAndAmount", credentialProfile);
			} catch (SQLException ex) {
				ex.printStackTrace();
				logger
						.info("SQL Exception @VendorHdrDaoImpl.UpdateDbInsuranceSelection() due to"
								+ ex.getMessage());
				throw new DBException(
						"SQL Exception @VendorHdrDaoImpl.UpdateDbInsuranceSelection() due to "
								+ ex.getMessage());
			} 
			return result;
	    }
	    public VendorHdr getInsuranceIndicators(Integer vendorId) throws DBException {
	    	VendorHdr vendorHdr = new VendorHdr();
			try
			{
				vendorHdr = (VendorHdr)getSqlMapClient().queryForObject("pvendor_hdr.getInsuranceIndicators",vendorId);
			}
	        catch (SQLException ex)
	        {
	        	logger.info("[VendorHdrDaoImpl--> getInsuranceIndicators() --> Exception] "+ex.getMessage());
	            throw new DBException("[VendorHdrDaoImpl--> getInsuranceIndicators() --> Exception]", ex);
	        
	        }

			return vendorHdr;
		}
	    /**
		 * Description: Retrieves the Company Name for the given vendorId	 * 
		 * @param Integer vendorId
		 * @return String companyName
		 */
	    public String getCompanyName(Integer vendorId)throws DBException{
	    	String companyName = null;
	    	try{
	    		companyName = (String)getSqlMapClient().queryForObject("getCompanyName.query", vendorId);
	    	}catch (SQLException ex) {
				ex.printStackTrace();
				logger.info("SQL Exception @VendorHdrDaoImpl.getCompanyName() due to "	+ ex.getMessage());
				throw new DBException("SQL Exception @VendorHdrDaoImpl.getCompanyName() due to " + ex.getMessage());
			} 
	    	return companyName;
	    }
	    
	    
	    
		// Used by Ajax- Cache
		public HashMap getLeadSummaryCountsProvider(Integer vendorId)
			throws DBException{
			try {
				int newCount = 0;
				int working = 0;
				int scheduled = 0;
				int completed = 0;
				int cancelled = 0;
				int stale=0;
				int totalLeads=0;
			
				HashMap summaryMap = new HashMap();
				ArrayList tabCountList;
			
				tabCountList = (ArrayList) getSqlMapClient().queryForList(
						"LeadWfStatesProviderCounts.query", vendorId);
			
				for (java.util.Iterator it = tabCountList.iterator(); it.hasNext();) {
			
					ServiceOrderWfStatesCountsVO tabCountsIterator = (ServiceOrderWfStatesCountsVO) it
							.next();
					String tabType = tabCountsIterator.getTabType();
			
					
			
					if (tabType.equals("NEW")
							) {
						newCount= tabCountsIterator.getLeadCount();
						totalLeads=totalLeads+newCount;
					} 
					else if (tabType.equals("WORKING")
							) {
						working= tabCountsIterator.getLeadCount();
						totalLeads=totalLeads+working;

					}else if (tabType.equals("SCHEDULED")
							) {
						scheduled= tabCountsIterator.getLeadCount();
						totalLeads=totalLeads+scheduled;

					}else if (tabType.equals("COMPLETED")
							) {
						completed= tabCountsIterator.getLeadCount();
						totalLeads=totalLeads+completed;

					}else if (tabType.equals("CANCELLED")
							) {
						cancelled= tabCountsIterator.getLeadCount();
						totalLeads=totalLeads+cancelled;

					}else if (tabType.equals("STALE")
							) {
						stale= tabCountsIterator.getLeadCount();
					}
				}
			
				/*routedCount = (Integer) queryForObject(
						"providerReceivedCount.query", ajaxCacheVO);
				
				bidCount = (Integer) queryForObject("providerBidCount.query", ajaxCacheVO);
			
				bBoardCount = (Integer) queryForObject("providerBulletinBoardCount.query", ajaxCacheVO);*/
				
				
				
				summaryMap.put("NEW", newCount);
				summaryMap.put("WORKING", working);
				summaryMap.put("SCHEDULED", scheduled);
				summaryMap.put("COMPLETED", completed);
				summaryMap.put("CANCELLED", cancelled);
				summaryMap.put("STALE", stale);
				summaryMap.put("TOTALLEADS", totalLeads);
				
				// find the conversion rate
				double conversionrate=(double)completed/(double)totalLeads;
				double conversionpercentage=conversionrate* 100;
				int roundedValue=(int) Math.round(conversionpercentage);
				summaryMap.put("CONVERSIONRATE", roundedValue);
				
				//
				
				
				return summaryMap;
			
			} catch (Exception e) {
			
				logger
						.error("[ServiceOrderDaoImpl.soTabCounts.querySOWfStatesBuyerCounts2]\n"
								+ e);
				throw new DBException(
						"Cannot retrieve service order buyer wf states counts");
			
			}
		}
	    
		
		// for buyer.
		
		public HashMap getLeadSummaryCountsBuyer(Integer buyerId)
				throws DBException{
				try {
					/*
					 * 
					 *  Unmatched
				         Matched
				         Completed
				         Cancelled
					 */
					int unmatched=0;
					int matched=0;
					int completed=0;
					int cancelled=0;
					int totalLeads=0;
					int nonLaunchMarket=0;
					
				
					HashMap summaryMap = new HashMap();
					ArrayList tabCountList;
				
					tabCountList = (ArrayList) getSqlMapClient().queryForList(
							"LeadWfStatesBuyerCounts.query", buyerId);
				
					for (java.util.Iterator it = tabCountList.iterator(); it.hasNext();) {
				
						ServiceOrderWfStatesCountsVO tabCountsIterator = (ServiceOrderWfStatesCountsVO) it
								.next();
						String tabType = tabCountsIterator.getTabType();
				
						
				
						if (tabType.equals("UNMATCHED")
								) {
							unmatched= tabCountsIterator.getLeadCount();
							totalLeads=totalLeads+unmatched;
						} 
						else if (tabType.equals("MATCHED")
								) {
							matched= tabCountsIterator.getLeadCount();
							totalLeads=totalLeads+matched;

						}else if (tabType.equals("COMPLETED")
								) {
							completed= tabCountsIterator.getLeadCount();
							totalLeads=totalLeads+completed;

						}else if (tabType.equals("CANCELLED")
								) {
							cancelled= tabCountsIterator.getLeadCount();
							totalLeads=totalLeads+cancelled;

						}
						else if (tabType.equals("OUT-OF-AREA")
								) {
							nonLaunchMarket= tabCountsIterator.getLeadCount();
							totalLeads=totalLeads+nonLaunchMarket;

						}
						
						
					}
				// total leads 
					
					summaryMap.put("UNMATCHED", unmatched);
					summaryMap.put("MATCHED", matched);
					summaryMap.put("COMPLETED", completed);
					summaryMap.put("CANCELLED", cancelled);
					summaryMap.put("TOTALLEADS", totalLeads);
					summaryMap.put("OUT-OF-AREA", nonLaunchMarket);

					
					return summaryMap;
				
				} catch (Exception e) {
				
					logger
							.error("[ServiceOrderDaoImpl.soTabCounts.querySOWfStatesBuyerCounts2]\n"
									+ e);
					throw new DBException(
							"Cannot retrieve service order buyer wf states counts");
				
				}
			}
		
		
		// For getting date for matched status.
		
		public List<LeadVO> getDateForMatchedStatus(Integer vendorId)
			throws DBException{
			try {
				
			
				List<LeadVO> dateList=new ArrayList<LeadVO>();
			
				dateList =(List<LeadVO>)getSqlMapClient().queryForList(
						"LeadMatchedDate.query", vendorId);
			
				
				return dateList;
			
			} catch (Exception e) {
			
				logger
						.info(" error in getDateForMatchedStatus "
								+ e);
				throw new DBException(
						"error in getDateForMatchedStatus");
			
			}
		}
	    
		public HashMap getStaleInfoCount(Integer vendorId,String staleAfter)
				throws DBException{
				try {
					/*
					 * 
					 *  
					 */
					int staleNew=0;
					int staleWorking=0;
				
					HashMap summaryMap = new HashMap();
					ArrayList tabCountList;
					
					LeadVO leadVO=new LeadVO();
					leadVO.setStaleAfter(staleAfter);
					leadVO.setVendorId(vendorId);
				
					tabCountList = (ArrayList) getSqlMapClient().queryForList(
							"getStaleInfoCount.query", leadVO);
					
					if(null!=tabCountList && tabCountList.size()>0){
				
					for (java.util.Iterator it = tabCountList.iterator(); it.hasNext();) {
				
						ServiceOrderWfStatesCountsVO tabCountsIterator = (ServiceOrderWfStatesCountsVO) it
								.next();
						String tabType = tabCountsIterator.getTabType();
				
						
				
						if (tabType.equals("NEW")
								) {
							staleNew= tabCountsIterator.getLeadCount();
							
						} 
						else if (tabType.equals("WORKING")
								) {
							staleWorking= tabCountsIterator.getLeadCount();
						

						}
					}
					}
					
					summaryMap.put("STALENEW", staleNew);
					summaryMap.put("STALEWORKING", staleWorking);
					
					return summaryMap;
				
				} catch (Exception e) {
				
					logger
							.error("[ServiceOrderDaoImpl.soTabCounts.querySOWfStatesBuyerCounts2]\n"
									+ e);
					throw new DBException(
							"Cannot retrieve service order buyer wf states counts");
				
				}
			}
		
		//SL-19667 get background status count
	public HashMap getBcCount(Integer vendorId)
			throws DBException {

		try {

			int bcNotStarted=0;
			int bcInProcess=0;
			int bcPendingSubmission=0;
			int bcNotCleared=0;
			int bcClear=0;
			int bcRecertificationDue=0;
			int bcRecertificationDueClear=0;
			int bcRecertificationDueInProcess=0;

			HashMap summaryMap = new HashMap();
			ArrayList tabCountList;

			tabCountList = (ArrayList) getSqlMapClient().queryForList(
					"BackgroundCheckStatusCounts.query", vendorId);

			for (java.util.Iterator it = tabCountList.iterator(); it.hasNext();) {

				ServiceOrderWfStatesCountsVO tabCountsIterator = (ServiceOrderWfStatesCountsVO) it
						.next();
				String tabType = tabCountsIterator.getTabType();

				if (tabType.equals("Not Started")) {
					bcNotStarted = tabCountsIterator.getCount();

				} else if (tabType.equals("Pending Submission")) {
					bcPendingSubmission = tabCountsIterator.getCount();
					
				} else if (tabType.equals("In Process")) {
					bcInProcess = tabCountsIterator.getCount();

				} else if (tabType.equals("Clear")) {
					bcClear = tabCountsIterator.getCount();

				} else if (tabType.equals("Not Cleared")) {
					bcNotCleared = tabCountsIterator.getCount();

				} else if (tabType.equals("RecertificationDueClear")) {
					bcRecertificationDueClear = tabCountsIterator.getCount();
					//bcClear = bcClear - bcRecertificationDueClear;
					bcRecertificationDue=bcRecertificationDue+bcRecertificationDueClear;
				}else if (tabType.equals("RecertificationDueInProcess")) {
					bcRecertificationDueInProcess = tabCountsIterator.getCount();
					//bcInProcess = bcInProcess - bcRecertificationDueInProcess;
					bcRecertificationDue=bcRecertificationDue+bcRecertificationDueInProcess;
				}
			}
			
			summaryMap.put("Not Started", bcNotStarted);
			summaryMap.put("Pending Submission", bcPendingSubmission);
			summaryMap.put("In Process", bcInProcess);
			summaryMap.put("Clear", bcClear);
			summaryMap.put("Not Cleared", bcNotCleared);  
			summaryMap.put("RecertificationDue", bcRecertificationDue); 

			return summaryMap;

		} catch (Exception e) {

			logger.error("[ServiceOrderDaoImpl.getBcCount]\n"
					+ e);
			throw new DBException(
					"Cannot retrieve service order buyer wf states counts");

		}

	}
		
		
		
		
	    public Integer getStaleInfo(Integer vendorId,String staleAfter){
	    	
	    	
	    	LeadVO lead=new LeadVO();
	    	lead.setStaleAfter(staleAfter);
	    	lead.setVendorId(vendorId);
	    	Integer count=null;  
	    	try{
	    		count=(Integer) getSqlMapClient().queryForObject(
					"getStaleInfo.query", lead);
	    	}
	    	catch(Exception e){
	    		logger.info(""+e);
	    	
	    	}
	    	return count;
			
	    	
	    }
	    /**@Description: getting firmType for HI provider Firm
		 * @param approveFirmsVOList
		 * @return
		 * @throws DataServiceException
		 */
		public List<Integer> getVendorType(List<ApproveFirmsVO> approveFirmsVOList)throws DataServiceException {
			List<Integer> vendorIdList = null;
			try{
				vendorIdList = (List<Integer>) getSqlMapClient().queryForList("getVendorType.query", approveFirmsVOList);
			}catch (Exception e) {
				logger.error("Exception in getting firmType in getVendorType()"+ e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
			return vendorIdList;
		}
		 /**@Description: getting ResourceType for HI provider
		 * @param approveProvidersVOList
		 * @return
		 * @throws DataServiceException
		 */
		public List<Integer> getResourceType(List<ApproveProvidersVO> approveProvidersVOList)throws DataServiceException{
			List<Integer> resourceIdList = null;
			try{
				resourceIdList = (List<Integer>) getSqlMapClient().queryForList("getResourceType.query", approveProvidersVOList);
			}catch (Exception e) {
				logger.error("Exception in getting resourceType in getResourceType()"+ e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
			return resourceIdList;
		}
		
		/**
		 * @param firmStatus
		 * @return
		 * @throws DataServiceException
		 */
		public Integer getWfStateIdForStatus(String firmStatus)throws DataServiceException {
			Integer wfStateId =null;
			try{
				wfStateId = (Integer) getSqlMapClient().queryForObject("getWfStateId.query", firmStatus);
			}catch (Exception e) {
				logger.error("Exception in getting wfState Id  in getWfStateIdForStatus()"+ e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
			return wfStateId;
		}


		/**
		 * @param adminResourceId
		 * @return
		 * @throws DataServiceException
		 */
		public String getAdminUserName(Integer adminResourceId)throws DataServiceException {
			String userName="";
			try{
				userName = (String) getSqlMapClient().queryForObject("getAdminUserName.query", adminResourceId);
			}catch (Exception e) {
				logger.error("Exception in getting username   in getAdminUserName()"+ e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
			return userName;
		}


		
		public boolean checkIfEligibleForStatusChange(Integer firmId)throws DataServiceException {
			boolean isEligible = false;
			try{
				ReasonCodeVO codeVo = (ReasonCodeVO) getSqlMapClient().queryForObject("getEligibleIndicator.query", firmId);
				if(null!=codeVo && codeVo.getTermsConInd() && codeVo.getBucksInd() ){
					isEligible =true;
				}
			}catch (Exception e) {
				logger.error("Exception in validating firm eligibility   in checkIfEligibleForStatusChange()"+ e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
			return isEligible;
		}

		public boolean checkIfProviderEligibleForStatusChange (Integer providerId)throws DataServiceException {
			boolean isEligible = false;
			try{
				ReasonCodeVO codeVo = (ReasonCodeVO) getSqlMapClient().queryForObject("getProviderEligibleIndicator.query", providerId);
				if(null!=codeVo && codeVo.getTermsConInd()){
					isEligible =true;
				}
			}catch (Exception e) {
				logger.error("Exception in validating provider eligibility   in checkIfProviderEligibleForStatusChange()"+ e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
			return isEligible;
		}
		
		    
		public Integer getProviderWFStateId(int providerId) throws DBException {
			Integer wfStateId = 0;
			try
			{
				wfStateId = (Integer)getSqlMapClient().queryForObject("pvendor_resource.getWFstate",providerId);
			}
		    catch (Exception ex)
		    {
		    	logger.info("[VendorHdrDaoImpl--> getProviderWFStateId() --> Exception] "+ex.getMessage());
		        throw new DBException("[VendorHdrDaoImpl--> getProviderWFStateId() --> Exception]", ex);
		    }
		
			return wfStateId;
		}
		
/**
 * @param firmStatus
 * @return
 * @throws DataServiceException
 */
		public Integer getProviderWfStateIdForStatus(String status)throws DataServiceException {
			Integer wfStateId =null;
			try{
				wfStateId = (Integer) getSqlMapClient().queryForObject("getProviderWfStateId.query", status);
			}catch (Exception e) {
				logger.error("Exception in getting wfState Id  in getProviderWfStateIdForStatus()"+ e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
			return wfStateId;
		}
		
		/**
		 * @param providerId
		 * @return
		 * @throws DBException
		 */
		public Integer getVendorIdForResource(int providerId) throws DataServiceException {
			Integer vendorId = 0;
			try
			{
				vendorId = (Integer)getSqlMapClient().queryForObject("getVendorIdForResource.query",providerId);
			}
		    catch (Exception ex)
		    {
		    	logger.info("[VendorHdrDaoImpl--> getVendorIdForResource() --> Exception] "+ex.getMessage());
		        throw new DataServiceException("[VendorHdrDaoImpl--> getVendorIdForResource() --> Exception]", ex);
		    }
		
			return vendorId;
		}
		/**
		 * @param providerId
		 * @return
		 * @throws DBException
		 */
		public ApproveProvidersVO getBackgroundCheckDataForProvider(int providerId) throws DataServiceException {
			ApproveProvidersVO approveProvidersVO = new ApproveProvidersVO();
			try
			{
				approveProvidersVO = (ApproveProvidersVO) getSqlMapClient().queryForObject("getBackgroundCheckForResource.query", providerId);
			}
		    catch (Exception ex)
		    {
		    	logger.info("[VendorHdrDaoImpl--> getBackgroundCheckStatus() --> Exception] "+ex.getMessage());
		        throw new DataServiceException("[VendorHdrDaoImpl--> getBackgroundCheckStatus() --> Exception]", ex);
		    }
		
			return approveProvidersVO;
		}
		/**
		 * @param backgroundCheckProviderVO
		 * @return
		 * @throws DBException
		 */
		public void updateBackgroundCheckStatus(BackgroundCheckProviderVO backgroundCheckProviderVO)throws DataServiceException{
			try
			{
				getSqlMapClient().update("providerBackGround.update", backgroundCheckProviderVO);
			}
		    catch (Exception ex)
		    {
		    	logger.info("[VendorHdrDaoImpl--> updateBackgroundCheckStatus() --> Exception] "+ex.getMessage());
		        throw new DataServiceException("[VendorHdrDaoImpl--> updateBackgroundCheckStatus() --> Exception]", ex);
		    }
		}
		
		/**
		 * @param backgroundCheckHistoryProviderVO
		 * @return
		 * @throws DBException
		 */
		public void updateSlProBackgroundHistory(BackgroundCheckHistoryProviderVO backgroundCheckHistoryProviderVO)throws DataServiceException{
			try
			{
				getSqlMapClient().insert("providerBackgroundHistory.insert",backgroundCheckHistoryProviderVO);
			}
		    catch (Exception ex)
		    {
		    	logger.info("[VendorHdrDaoImpl--> updateSlProBackgroundHistory() --> Exception] "+ex.getMessage());
		        throw new DataServiceException("[VendorHdrDaoImpl--> updateSlProBackgroundHistory() --> Exception]", ex);
		    }
		}


		/**
		 * @param providerId
		 * @return
		 * @throws DBException
		 */
		public void updateBackgroundCheckStatusInVendorResource(ApproveProvidersVO providersVO)throws DataServiceException{
			try{
				Integer BgStatus = Constants.backgroundCheckStatusMap().get(providersVO.getBackgroundCheckStatus());
				providersVO.setWfStatus(BgStatus);
				getSqlMapClient().update("vendorResourceBackGround.update", providersVO);
			}catch (Exception ex){
		    	logger.info("[VendorHdrDaoImpl--> updateBackgroundCheckStatusInVendorResource() --> Exception] "+ex.getMessage());
		        throw new DataServiceException("[VendorHdrDaoImpl--> updateBackgroundCheckStatusInVendorResource() --> Exception]", ex);
		    }
		}


		public void updateBackgroundCheckStatusInVendorResource(int providerId)throws DataServiceException {
			// TODO Auto-generated method stub
        }


		public Integer getApprovedmembers(Integer vendorId)throws DataServiceException {
			Integer noOfEmp =null;
			try{
				noOfEmp = (Integer) getSqlMapClient().queryForObject("getApprovedMarketReadyproviders.query", vendorId);
			}catch (Exception e) {
				logger.info("Exception in fetching the count of approved and market ready providers under the firm"+ e.getMessage());
				 throw new DataServiceException("[VendorHdrDaoImpl--> getApprovedmembers() --> Exception]", e);
			}
			return noOfEmp;
		}
        //Added for D2c provider
		public String buyerSkuPrimaryIndustry(Integer vendorId) throws DataServiceException{
			logger.info("Exception in VendorHdrDaoImpl.buyerSkuPrimaryIndustry");
			String count ="";
			try{
				count = (String) getSqlMapClient().queryForObject("getBuyerSkuPrimaryIndustryCount.query", vendorId);
			}catch (Exception e) {
				logger.info("Exception in fetching the count of VendorHdrDaoImpl.buyerSkuPrimaryIndustry for D2C provider"+ e.getMessage());
				 throw new DataServiceException("[VendorHdrDaoImpl--> buyerSkuPrimaryIndustry() --> Exception]", e);
			}
			return count;
		}
		
		//SLT-2235
		public boolean isLegalNoticeChecked(Integer vendorId)throws DataServiceException{		
					
			try {
					Integer termsLegalNoticeValue = (Integer) getSqlMapClient().queryForObject("getTermsLegalNoticeIndValue.query", vendorId);
					if(termsLegalNoticeValue == 1){					
						return false;
					}
						return true;
				} catch (Exception e) {
						logger.info("Exception in isLegalNoticeChecked() method"+e.getMessage());
						throw new DataServiceException("[VendorHdrDaoImpl--> isLegalNoticeChecked() --> Exception]",e);
				}
					
		}
		
}
