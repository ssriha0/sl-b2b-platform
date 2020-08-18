package com.newco.marketplace.business.businessImpl.provider.searchmatchrank;

import java.util.List;

import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CPortalAPIVORequest;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;
import com.newco.marketplace.business.iBusiness.provider.searchmatchrank.IProviderSearchMatchRankBO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.services.d2cproviderportal.ID2CProviderPortalService;
import com.newco.match.rank.service.IMatchCriteriaService;
import com.newco.match.rank.service.IRankMetricsBO;
import com.sears.os.business.ABaseBO;

/**
 * BO impl for D2C Provider portal flow
 * 
 * @author rranja1
 * 
 */
public class ProviderSearchMatchRankBOImpl extends ABaseBO implements IProviderSearchMatchRankBO {

	private ID2CProviderPortalService d2cProviderPortalService;
	private IRankMetricsBO rankMetricBo;
	private IMatchCriteriaService matchCriteriaServiceImpl;
	
	public IMatchCriteriaService getMatchCriteriaServiceImpl() {
		return matchCriteriaServiceImpl;
	}
	
	public void setMatchCriteriaServiceImpl(IMatchCriteriaService matchCriteriaServiceImpl) {
		this.matchCriteriaServiceImpl = matchCriteriaServiceImpl;
	}
	
	public ID2CProviderPortalService getD2cProviderPortalService() {
		return d2cProviderPortalService;
	}

	public void setD2cProviderPortalService(ID2CProviderPortalService d2cProviderPortalService) {
		this.d2cProviderPortalService = d2cProviderPortalService;
	}

	public IRankMetricsBO getRankMetricBo() {
		return rankMetricBo;
	}

	public void setRankMetricBo(IRankMetricsBO rankMetricBo) {
		this.rankMetricBo = rankMetricBo;
	}
	
	public List<D2CProviderAPIVO> getFirmDetailsListByMultipleCriteriaSearchMatchRank(final D2CPortalAPIVORequest d2cPortalAPIVO) throws DataServiceException {
		
		// ------------------------------------------------------------------------------------------ //
		// [A] SEARCH CRITERIA:
		// 1. Firm (Sears approved)/(ServiceLive Approved)
		// 2. DISPTACH ZONE
		// 3. SPN MEMBER(SKU -> Template -> SPN)
		// 4. HAS AGREED TO SKU PRICING
		// 5. AT LEAST 1-PROVIDER IN APPROVED(MARKET READY) STATUS
		// List<D2CProviderAPIVO> d2CProviderAPIVO = getFirmDetailsWithRetailPriceList(d2CPortalAPIVO); // initial IMPL.
		logger.info("Inside D2CProviderPortalBOImpl.getFirmDetailsListByMultipleCriteriaSearch");
		List<D2CProviderAPIVO> firmDetailsListVo = getD2cProviderPortalService().getFirmDetailsListByMultipleCriteriaSearch(d2cPortalAPIVO);
		
		if (null != firmDetailsListVo) {
			logger.info("Provider list size after SEARCH CRITERIA firmDetailsListVo : " + firmDetailsListVo.size());
			for (D2CProviderAPIVO d2cProviderAPIVO : firmDetailsListVo) {
				logger.info("Firm Id's after SEARCH CRITERIA firmDetailsListVo : " + d2cProviderAPIVO.getFirmId());
			}
		}
		// ------------------------------------------------------------------------------------------ //
		// [B] MATCHING CRITERIA: save the ranks in DB
		// 1. Has atleast 1 provider avail. on requested date and time window.
		// 2. Match to member profile(Details TBD)
		// -----------------------------------------------------
		// List<D2CProviderAPIVO> firmDetailsListVo
		// # firmId, 	 price, 	dailyLimit, buyerRetailPrice
		// '10202', 	'10.00', 	'6', 		'80.00'
		// '15897', 	'98.00', 	'2', 		'80.00'
		// '67225', 	'34.99', 	'3', 		'80.00'
		if (null != firmDetailsListVo) {
			if (firmDetailsListVo.size() > 0) {
				// check for preference dates
				// d2cPortalAPIVO.getPrefIdAndStartEndDateTimeSlotMap()
				// dateTimePrefAndStartEndSlotMap = pref date and time slot
				// Key : 1 Value : TimeSlotDTO [startTime=Thu Oct 26 12:00:00 IST 2017, endTime=Thu Oct 26 17:00:00 IST 2017, hourInd24=false, matchedSoPrefDateTime=false]
				// Key : 2 Value : TimeSlotDTO [startTime=Fri Oct 27 16:00:00 IST 2017, endTime=Fri Oct 27 20:00:00 IST 2017, hourInd24=false, matchedSoPrefDateTime=false]
				// Key : 3 Value : TimeSlotDTO [startTime=Sat Oct 28 08:00:00 IST 2017, endTime=Sat Oct 28 12:00:00 IST 2017, hourInd24=false, matchedSoPrefDateTime=false]
				if (null != d2cPortalAPIVO.getPrefIdAndStartEndDateTimeSlotMap()) {
					if ((d2cPortalAPIVO.getPrefIdAndStartEndDateTimeSlotMap()).size() > 0) {
						logger.info("Provider list size before MATCHING CRITERIA firmDetailsListVo : " + firmDetailsListVo.size());
						try {
							getMatchCriteriaServiceImpl().calculateAndSaveMatchingCriteria(d2cPortalAPIVO, firmDetailsListVo);
						} catch (Exception e) {
							e.printStackTrace();
						}
						logger.info("Provider list size after MATCHING CRITERIA firmDetailsListVo : " + firmDetailsListVo.size());
					}
				}
			}
		}
		
		
		// ------------------------------------------------------------------------------------------ //
		// [C] RANK CRITERIA:
		// 1. Matching %
		// 2. Performance metrics.
		// 3. Handicap.
		// firmDetailsListVo with it's rank
		String corelationId = null;
		if (null != firmDetailsListVo) {
			if (firmDetailsListVo.size() > 0) {
				logger.info("Provider list size before RANK CRITERIA firmDetailsListVo : " + firmDetailsListVo.size());
				corelationId = getRankMetricBo().getProviderByRankCriteria(firmDetailsListVo, d2cPortalAPIVO.getBuyerId());
				logger.info("Provider list size after RANK CRITERIA firmDetailsListVo : " + firmDetailsListVo.size() + " AND corelationId = " + corelationId);
				d2cPortalAPIVO.setCorelationId(corelationId);
			}
		}
		
		return firmDetailsListVo;
	}
	
	public List<D2CProviderAPIVO> getFirmDetailsWithBuyerPrice(String buyerId, String sku, String zip, List<String> firmList) throws DataServiceException {
		logger.info("Inside ProviderSearchMatchRankBOImpl.getFirmDetailsWithBuyerPrice");
		List<D2CProviderAPIVO> basicFirmDetailsVO = null;
		basicFirmDetailsVO = getD2cProviderPortalService().getFirmDetailsWithBuyerRetailPrice(buyerId, sku, zip, firmList);
		logger.info("Exiting ProviderSearchMatchRankBOImpl.getFirmDetailsWithBuyerPrice");
		return basicFirmDetailsVO;

	}
}