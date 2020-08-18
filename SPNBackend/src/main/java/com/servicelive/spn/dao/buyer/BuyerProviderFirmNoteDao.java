package com.servicelive.spn.dao.buyer;

import java.util.List;

import com.servicelive.domain.buyer.BuyerProviderFirmNote;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author hoza
 *
 */
public interface BuyerProviderFirmNoteDao extends BaseDao {
	/**
	 * This method would be used for fetching List of Noets for given Buyer and Provider Firm combination
	 * @param buyerId
	 * @param providerFirmId
	 * @return
	 * @throws Exception
	 */
	public List<BuyerProviderFirmNote> getProviderFirmNotes(Integer buyerId,Integer providerFirmId) throws Exception;
	/**
	 * persistProviderFirmNote will persist the information and return back the Entity with updated  with saved ( persisted) data
	 * @param providerFirmNote
	 * @return
	 * @throws Exception
	 */
	public BuyerProviderFirmNote persistProviderFirmNote(BuyerProviderFirmNote buyerProviderFirmNote) throws Exception;
}
