package com.servicelive.spn.dao.buyer;

import java.util.List;

import com.servicelive.domain.buyer.BuyerServiceProNote;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author hoza
 *
 */
public interface BuyerServiceProviderNoteDao extends BaseDao {
	/**
	 * This method would be used for fetching List of Noets for given Buyer and Service Provider combination
	 * @param buyerId
	 * @param serviceProId
	 * @return
	 * @throws Exception
	 */
	public List<BuyerServiceProNote> getServiceProNotes(Integer buyerId, Integer serviceProId) throws Exception;

	/**
	 * 
	 * @param buyerServiceProNote
	 * @return
	 * @throws Exception
	 */
	public BuyerServiceProNote persistServiceProNote(BuyerServiceProNote buyerServiceProNote) throws Exception;
	/**
	 * 
	 * @param buyerId
	 * @param providerFirmId
	 * @return
	 * @throws Exception
	 */
	public List<BuyerServiceProNote> getServiceProviderNotesForProviderFirm(Integer buyerId, Integer providerFirmId) throws Exception;
}
