package com.newco.marketplace.AdminTool;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.iso.IIsoRequestProcessor;
import com.newco.marketplace.dto.vo.fullfillment.FullfillmentEntryVO;
import com.newco.marketplace.interfaces.FullfillmentConstants;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.persistence.iDao.ledger.IFullfillmentDao;
import com.newco.spring.jms.ISpringJMSFacility;


/**
 * @author paugus2
 * 
 */
public class AdminToolProcessor implements IAdminToolProcessor {

	private static final Logger logger = Logger.getLogger(AdminToolProcessor.class.getName());
	private  IFullfillmentDao fullfillmentDao;
	private  IIsoRequestProcessor isoRequestProcessor;
	private ISpringJMSFacility jmsFacility;

	public AdminToolProcessor() {
	}


	public static void main(String[] args) {
		AdminToolProcessor atp = new AdminToolProcessor();
		atp.processAdjustment(args);
	}

	public boolean processAdjustment(String[] args) {
		boolean adjusted = false;
		try {
			FullfillmentEntryVO fullfillmentEntryVO = null;
			if ((args != null) && (args.length > 0)) {
				for (int i = 0; i < args.length; i++) {
					if (NumberUtils.isNumber(args[i])) {
						fullfillmentEntryVO = getFullfillmentMessageDetail(new Long(args[i]));
						if (fullfillmentEntryVO != null) {
							setAdjustmentFlag(args, fullfillmentEntryVO, i);
							byte myBytes[] = isoRequestProcessor.processRequest(fullfillmentEntryVO);
							//For throttling of messages sent from Admin Tool
							jmsFacility.sendLocalMesage(myBytes,fullfillmentEntryVO.getPrimaryAccountNumber());
							adjusted = true;
						}
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return adjusted;
	}

	
	public boolean sendFullfillmentGroup(Long fullfillmentEntryId) {
		boolean sentFulfillmentFlag = false;
		try {
			FullfillmentEntryVO fullfillmentEntryVO = getGroupFullfillmentMessageDetail(fullfillmentEntryId);
			populateMessageIdentifier(fullfillmentEntryVO);
			if (fullfillmentEntryVO != null) {
				byte myBytes[] = isoRequestProcessor.processRequest(fullfillmentEntryVO);
				//For throttling of messages sent from Admin Tool
				jmsFacility.sendLocalMesage(myBytes,fullfillmentEntryVO.getPrimaryAccountNumber());
				sentFulfillmentFlag = true;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sentFulfillmentFlag;
	}
	/**
	 * Description: If adjustment flag is present, set message identifier as the
	 * proper type of adjustment
	 * 
	 * @param args
	 * @param fullfillmentEntryVO
	 * @param i
	 * @return
	 */
	private static void setAdjustmentFlag(String[] args, FullfillmentEntryVO fullfillmentEntryVO, int i) {
		if (args.length > (i + 1)) {
			String adjustment = args[i + 1];
			if ("adjustment".equalsIgnoreCase(adjustment)) {
				if (fullfillmentEntryVO.getMessageTypeId() != null) {
					if (FullfillmentConstants.MESSAGE_TYPE_REDEMPTION == fullfillmentEntryVO.getMessageTypeId().intValue()) {
						fullfillmentEntryVO.setMessageIdentifier(FullfillmentConstants.BALANCE_ADJUSTMENT_REDEEM_REQUEST);
					} else if (FullfillmentConstants.MESSAGE_TYPE_RELOAD == fullfillmentEntryVO.getMessageTypeId().intValue()) {
						fullfillmentEntryVO.setMessageIdentifier(FullfillmentConstants.BALANCE_ADJUSTMENT_RELOAD_REQUEST);
					}
				}
			}
		}
	}

	public FullfillmentEntryVO getFullfillmentMessageDetail(Long fullfillmentEntryId) throws Exception {
		FullfillmentEntryVO fullfillmentEntryVO = null;
		FullfillmentEntryVO nextFullfillmentEntryVO = null;
		try {
			logger.info("FullfillmentTransactionBO-->getFullfillmentMessageDetail()-->START");
			fullfillmentEntryVO = fullfillmentDao.getFullfillmentMessageDetail(fullfillmentEntryId);
			if (fullfillmentEntryVO!=null && (((fullfillmentEntryVO.getTransAmount() != null) && (fullfillmentEntryVO.getTransAmount().doubleValue() > 0.0)) ||
						(LedgerConstants.BUSINESS_TRANSACTION_NEW_PROVIDER == fullfillmentEntryVO.getBusTransId().intValue()) || 
						(LedgerConstants.BUSINESS_TRANSACTION_NEW_BUYER == fullfillmentEntryVO.getBusTransId().intValue())))
			{
				populateMessageIdentifier(fullfillmentEntryVO);
				nextFullfillmentEntryVO = fullfillmentDao.getNextFullfillmentEntryDetails(fullfillmentEntryVO);
				if (nextFullfillmentEntryVO != null) {
					fullfillmentEntryVO.setNextFullfillmentEntryId(nextFullfillmentEntryVO.getFullfillmentEntryId());
				}
			}
		} catch (Exception e) {
			System.out.println("getFullfillmentMessageDetail-->Exception-->");
		}
		return fullfillmentEntryVO;
	}
	
	public FullfillmentEntryVO getGroupFullfillmentMessageDetail(Long fullfillmentEntryId) throws Exception {
		FullfillmentEntryVO fullfillmentEntryVO = null;
		FullfillmentEntryVO nextFullfillmentEntryVO = null;
		try {
			logger.info("FullfillmentTransactionBO-->getGroupFullfillmentMessageDetail()-->START");
			fullfillmentEntryVO = fullfillmentDao.getFullfillmentMessageDetail(fullfillmentEntryId);
			nextFullfillmentEntryVO = fullfillmentDao.getNextFullfillmentEntryDetails(fullfillmentEntryVO);
			while (true) {
					if ((nextFullfillmentEntryVO != null) && (nextFullfillmentEntryVO.getFullfillmentEntryId() != null)) {
						if (((nextFullfillmentEntryVO.getTransAmount() != null) && (nextFullfillmentEntryVO.getTransAmount().doubleValue() > 0.0)) || (LedgerConstants.BUSINESS_TRANSACTION_NEW_PROVIDER == nextFullfillmentEntryVO.getBusTransId().intValue()) || (LedgerConstants.BUSINESS_TRANSACTION_NEW_BUYER == nextFullfillmentEntryVO.getBusTransId().intValue())) {
							fullfillmentEntryVO.setNextFullfillmentEntryId(nextFullfillmentEntryVO.getFullfillmentEntryId());
							break;
						} else {
							nextFullfillmentEntryVO = fullfillmentDao.getNextFullfillmentEntryDetails(nextFullfillmentEntryVO);
							if (nextFullfillmentEntryVO != null) {
								fullfillmentEntryVO.setNextFullfillmentEntryId(nextFullfillmentEntryVO.getFullfillmentEntryId());
							}
						}
					} else {
						break;
					}
			}
		} catch (Exception e) {
			logger.info("getGroupFullfillmentMessageDetail-->Exception-->");
		}
		return fullfillmentEntryVO;
	}
	
	private static void populateMessageIdentifier(FullfillmentEntryVO fullfillmentEntryVO) throws Exception {
		if (fullfillmentEntryVO.getMessageTypeId() != null) {
			if (FullfillmentConstants.MESSAGE_TYPE_ACTIVATION == fullfillmentEntryVO.getMessageTypeId().intValue()) {
				fullfillmentEntryVO.setMessageIdentifier(FullfillmentConstants.ACTIVATION_RELOAD_REQUEST);
			} else if (FullfillmentConstants.MESSAGE_TYPE_RELOAD == fullfillmentEntryVO.getMessageTypeId().intValue()) {
				fullfillmentEntryVO.setMessageIdentifier(FullfillmentConstants.ACTIVATION_RELOAD_REQUEST);
			} else if (FullfillmentConstants.MESSAGE_TYPE_REDEMPTION == fullfillmentEntryVO.getMessageTypeId().intValue()) {
				fullfillmentEntryVO.setMessageIdentifier(FullfillmentConstants.REDEMPTION_REQUEST);
			} else if (FullfillmentConstants.MESSAGE_TYPE_BALANCE_ENQ == fullfillmentEntryVO.getMessageTypeId().intValue()) {
				fullfillmentEntryVO.setMessageIdentifier(FullfillmentConstants.BALANCE_ENQUIRY_REQUEST);
			} else if (FullfillmentConstants.MESSAGE_TYPE_HEARTBEAT == fullfillmentEntryVO.getMessageTypeId().intValue()) {
				fullfillmentEntryVO.setMessageIdentifier(FullfillmentConstants.SHARP_HEARTBEAT_REQUEST);
			}
		}
	}

	public  IIsoRequestProcessor getIsoRequestProcessor() {
		return this.isoRequestProcessor;
	}

	public  void setIsoRequestProcessor(
			IIsoRequestProcessor isoRequestProcessor) {
		this.isoRequestProcessor = isoRequestProcessor;
	}

	public  IFullfillmentDao getFullfillmentDao() {
		return this.fullfillmentDao;
	}

	public  void setFullfillmentDao(IFullfillmentDao fullfillmentDao) {
		this.fullfillmentDao = fullfillmentDao;
	}

	public ISpringJMSFacility getJmsFacility() {
		return jmsFacility;
	}

	public void setJmsFacility(ISpringJMSFacility jmsFacility) {
		this.jmsFacility = jmsFacility;
	}


}
