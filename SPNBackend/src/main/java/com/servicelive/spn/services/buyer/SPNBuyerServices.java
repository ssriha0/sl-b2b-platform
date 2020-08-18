/**
 * 
 */
package com.servicelive.spn.services.buyer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.spn.network.SPNBuyer;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.spn.dao.network.SPNBuyerDao;
import com.servicelive.spn.services.BaseServices;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * @author hoza
 *
 */
public class SPNBuyerServices extends BaseServices {
	private SPNBuyerDao spnBuyerDao;
	private BuyerServices buyerServices;
	
	/**
	 * 
	 * @param spnId
	 * @return List
	 * @throws Exception
	 */
	public List<SPNBuyer> getListForSPN(SPNHeader spnId) throws Exception {
		return spnBuyerDao.findByProperty("spnId", spnId);
	}
	
	/**
	 * Gets the list of SPN for the specified buyer and that is the primary and the spn is not an alias
	 * 
	 * @param buyerId
	 * @return List of SPNHeader
	 * @throws Exception
	 */
	public List<SPNHeader> getSPNListForBuyer(Integer buyerId) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String startDateTime = dateFormat.format(date);
        System.out.println("StartDateTime : " +startDateTime);
		List<SPNHeader> spnList = new ArrayList<SPNHeader>();
		Buyer buyer = buyerServices.getBuyerForId(buyerId);
		List<SPNBuyer> spnBuyers = spnBuyerDao.findByProperty("buyerId", buyer);
		System.out.println("Size of the SPN list : " +spnBuyers.size());
		for (SPNBuyer spnBuyer : spnBuyers) {
			System.out.println("Going through SPN : " +spnBuyer.getSpnId());
			if(spnBuyer.getSpnId().getIsAlias() == null || spnBuyer.getSpnId().getIsAlias().booleanValue() == false) {
				spnList.add(spnBuyer.getSpnId());
			}
		}
		Date date1 = new Date();
        String endDateTime = dateFormat.format(date1);
        System.out.println("EndDateTime : " +endDateTime);
        //added for sorting
        Collections.sort(spnList,new Comparator<SPNHeader>() {
        public int compare(SPNHeader o1, SPNHeader o2) {
				return o1.getSpnName().compareToIgnoreCase(o2.getSpnName());
			}
		});
		return spnList; 
	}

	/**
	 * @return the spnBuyerDao
	 */
	public SPNBuyerDao getSpnBuyerDao() {
		return spnBuyerDao;
	}

	/**
	 * @param spnBuyerDao the spnBuyerDao to set
	 */
	public void setSpnBuyerDao(SPNBuyerDao spnBuyerDao) {
		this.spnBuyerDao = spnBuyerDao;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.BaseServices#handleDates(java.lang.Object)
	 */
	@SuppressWarnings("unused")
	@Override
	protected void handleDates(Object entity) {
		// do nothing
	}

	/**
	 * @param buyerServices the buyerServices to set
	 */
	public void setBuyerServices(BuyerServices buyerServices) {
		this.buyerServices = buyerServices;
	}
	/**
	 * R11_0 SL_19387
	 * This method returns the value of that feature set.
	 * @param buyerId
	 * @param feature
	 */
	public String getBuyerFeatureSetValue(Integer buyerId, String feature) throws Exception {
			return spnBuyerDao.getBuyerFeatureSetValue(buyerId,feature);
	}
}
