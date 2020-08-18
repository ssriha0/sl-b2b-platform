package com.servicelive.spn.services.buyer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.buyer.BuyerProviderFirmNote;
import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.buyer.BuyerServiceProNote;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.domain.spn.detached.SPNProviderDetailsNoteRowDTO;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.dao.buyer.BuyerProviderFirmNoteDao;
import com.servicelive.spn.dao.buyer.BuyerServiceProviderNoteDao;
import com.servicelive.spn.services.BaseServices;

/**
 * This is used for persisting as well as fetching  
 * @author hoza
 *
 */
public class BuyerNotesService extends BaseServices {
	/** Dao holder for Firm Notes 	 */
	private BuyerProviderFirmNoteDao buyerProviderFirmNoteDao;
	/** Dao holder for SP  Notes 	 */
	private BuyerServiceProviderNoteDao buyerServiceProviderNoteDao;

	private BuyerServices buyerServices;

	/**
	 * 
	 * @param buyerId
	 * @param providerFirmId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<BuyerProviderFirmNote> getProviderFirmNotes(Integer buyerId, Integer providerFirmId) throws Exception {
		return buyerProviderFirmNoteDao.getProviderFirmNotes(buyerId, providerFirmId);
	}
	
	/**
	 * 
	 * @param buyerId
	 * @param providerFirmId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<BuyerServiceProNote> getServiceProviderNotesForproviderFirm(Integer buyerId, Integer providerFirmId) throws Exception {
		return buyerServiceProviderNoteDao.getServiceProviderNotesForProviderFirm(buyerId, providerFirmId);
	}
	
	/**
	 * 
	 * @param buyerId
	 * @param serviceProId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<SPNProviderDetailsNoteRowDTO> getServiceProviderNotesAsDto(Integer buyerId, Integer serviceProId) throws Exception {
		List<BuyerServiceProNote> notes = getServiceProviderNotes(buyerId, serviceProId);

		List<SPNProviderDetailsNoteRowDTO> list = new ArrayList<SPNProviderDetailsNoteRowDTO>();
		for(BuyerServiceProNote note : notes) {
			SPNProviderDetailsNoteRowDTO dto = mapToDto(note);
			list.add(dto);
		}
		return list;
	}

	private SPNProviderDetailsNoteRowDTO mapToDto(BuyerServiceProNote note) {
		SPNProviderDetailsNoteRowDTO dto = new SPNProviderDetailsNoteRowDTO();
		dto.setDate(note.getModifiedDate());

		String modifiedBy = note.getModifiedBy();
		try {
			BuyerResource buyerResource = getBuyerServices().getBuyerResourceForUserName(modifiedBy);
			dto.setEnteredByID(buyerResource.getResourceId());
			dto.setEnteredByName(buyerResource.getContact().getFirstName() + " " + buyerResource.getContact().getLastName());
		} catch (Exception e) {
			e.printStackTrace();
			//TODO need to add logging
		}

		dto.setNotes(note.getComments());
		return dto;
	}

	/**
	 * 
	 * @param buyerId
	 * @param serviceProId
	 * @return
	 * @throws Exception
	 */
	private List<BuyerServiceProNote> getServiceProviderNotes(Integer buyerId, Integer serviceProId) throws Exception {
		return buyerServiceProviderNoteDao.getServiceProNotes(buyerId, serviceProId);
	}

	/**
	 * This method insert new Note for SP and BUYER combination and returns back Updated NOTE object
	 * @param buyerId
	 * @param serviceProId
	 * @param modifiedBy
	 * @param comments
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public BuyerServiceProNote persistServiceProNote(Integer buyerId, Integer serviceProId, String modifiedBy,String comments) throws Exception  {
		BuyerServiceProNote serviceProNote = new BuyerServiceProNote();
		serviceProNote.setId(null);
		serviceProNote.setBuyerId(new Buyer(buyerId));
		serviceProNote.setServiceProId(new ServiceProvider(serviceProId));
		serviceProNote.setComments(comments);
		serviceProNote.setModifiedBy(modifiedBy);
		handleDates(serviceProNote);
		serviceProNote = getBuyerServiceProviderNoteDao().persistServiceProNote(serviceProNote);
		return serviceProNote;
	}
	
	public BuyerProviderFirmNote persistProviderFirmNote(Integer buyerId, Integer providerFirmId, String modifiedBy,String comments) throws Exception {
		BuyerProviderFirmNote proFirmNote = new BuyerProviderFirmNote();
		proFirmNote.setId(null);
		proFirmNote.setBuyerId(new Buyer(buyerId));
		proFirmNote.setProviderFirmId(new ProviderFirm(providerFirmId));
		proFirmNote.setComments(comments);
		proFirmNote.setModifiedBy(modifiedBy);
		handleDates(proFirmNote);
		proFirmNote = getBuyerProviderFirmNoteDao().persistProviderFirmNote(proFirmNote);
		return proFirmNote;
	}
	
	
	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.BaseServices#handleDates(java.lang.Object)
	 */
	@Override
	protected void handleDates(Object entity) {
		if(entity != null && entity instanceof BuyerServiceProNote) {
			BuyerServiceProNote spNote = (BuyerServiceProNote) entity;
			if(spNote.getId() == null) {
				spNote.setCreatedDate(CalendarUtil.getNow());
			}
			spNote.setModifiedDate(CalendarUtil.getNow());
		}
		if(entity != null && entity instanceof BuyerProviderFirmNote) {
			BuyerProviderFirmNote pfNote = (BuyerProviderFirmNote) entity;
			if(pfNote.getId() == null) {
				pfNote.setCreatedDate(CalendarUtil.getNow());
			}
			pfNote.setModifiedDate(CalendarUtil.getNow());
		}

	}

	/**
	 * @return the buyerProviderFirmNoteDao
	 */
	private BuyerProviderFirmNoteDao getBuyerProviderFirmNoteDao() {
		return buyerProviderFirmNoteDao;
	}

	/**
	 * @param buyerProviderFirmNoteDao the buyerProviderFirmNoteDao to set
	 */
	public void setBuyerProviderFirmNoteDao(BuyerProviderFirmNoteDao buyerProviderFirmNoteDao) {
		this.buyerProviderFirmNoteDao = buyerProviderFirmNoteDao;
	}

	/**
	 * @return the buyerServiceProviderNoteDao
	 */
	private BuyerServiceProviderNoteDao getBuyerServiceProviderNoteDao() {
		return buyerServiceProviderNoteDao;
	}

	/**
	 * @param buyerServiceProviderNoteDao the buyerServiceProviderNoteDao to set
	 */
	public void setBuyerServiceProviderNoteDao(BuyerServiceProviderNoteDao buyerServiceProviderNoteDao) {
		this.buyerServiceProviderNoteDao = buyerServiceProviderNoteDao;
	}

	/**
	 * @param buyerServices the buyerServices to set
	 */
	public void setBuyerServices(BuyerServices buyerServices) {
		this.buyerServices = buyerServices;
	}

	/**
	 * @return the buyerServices
	 */
	private BuyerServices getBuyerServices() {
		return buyerServices;
	}

}
