/**
 * 
 */
package com.newco.marketplace.business.businessImpl.ledger;

import org.apache.log4j.Logger;

import com.newco.marketplace.AdminTool.IAdminToolProcessor;
import com.newco.marketplace.business.iBusiness.iso.IIsoRequestProcessor;
import com.newco.marketplace.business.iBusiness.iso.IIsoResponseProcessor;
import com.newco.marketplace.business.iBusiness.ledger.IBalanceInquiryProcessor;
import com.newco.marketplace.business.iBusiness.ledger.IFullfillmentTransactionBO;
import com.newco.marketplace.business.iBusiness.ledger.ILedgerFacilityBO;
import com.newco.marketplace.business.iBusiness.promo.PromoBO;
import com.newco.marketplace.business.iBusiness.provider.IEmailTemplateBO;
import com.newco.marketplace.dto.vo.fee.FeeConstants;
import com.newco.marketplace.dto.vo.fullfillment.FullfillmentEntryVO;
import com.newco.marketplace.interfaces.FullfillmentConstants;
import com.newco.marketplace.persistence.iDao.ledger.IFullfillmentDao;
import com.newco.marketplace.persistence.iDao.lookup.LookupDao;
import com.newco.marketplace.persistence.iDao.provider.ITemplateDao;
import com.newco.marketplace.persistence.iDao.so.buyer.BuyerDao;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.spring.jms.ISpringJMSFacility;
import com.sears.os.business.ABaseBO;

/**
 * @author schavda
 * 
 */
public class FullfillmentTransactionBO extends ABaseBO implements IFullfillmentTransactionBO {
	private static final Logger logger = Logger.getLogger(FullfillmentTransactionBO.class);
	private IAdminToolProcessor adminToolProcessor;
	private IFullfillmentDao fullfillmentDao;
	private IIsoResponseProcessor isoResponseProcessor;
	private IIsoRequestProcessor isoRequestProcessor;
	private ILedgerFacilityBO accountingTransactionManagementBO;
	private LookupDao lookupDao;
	private ISpringJMSFacility jmsFacility;
	private IBalanceInquiryProcessor balanceInquiryProcessor;
	private IEmailTemplateBO emailTemplateBO;
	private ITemplateDao templateDao;
	private ServiceOrderDao serviceOrderDao;
	private PromoBO promoBO;
	private BuyerDao buyerDao;
	private FeeConstants feeFacility;
	

	private FullfillmentEntryVO populateFirstMessage(FullfillmentEntryVO fullfillmentEntryVO, FullfillmentEntryVO fullfillmentEntryVOReturn) throws Exception {
		fullfillmentEntryVOReturn.setFullfillmentGroupId(fullfillmentEntryVO.getFullfillmentGroupId());
		fullfillmentEntryVOReturn.setFullfillmentEntryId(fullfillmentEntryVO.getFullfillmentEntryId());
		fullfillmentEntryVOReturn.setBusTransId(fullfillmentEntryVO.getBusTransId());
		fullfillmentEntryVOReturn.setFullfillmentEntryRuleId(fullfillmentEntryVO.getFullfillmentEntryRuleId());
		fullfillmentEntryVOReturn.setEntryTypeId(fullfillmentEntryVO.getEntryTypeId());
		fullfillmentEntryVOReturn.setEntityTypeId(fullfillmentEntryVO.getEntityTypeId());
		fullfillmentEntryVOReturn.setLedgerEntityId(fullfillmentEntryVO.getLedgerEntityId());
		fullfillmentEntryVOReturn.setTransAmount(fullfillmentEntryVO.getTransAmount());
		fullfillmentEntryVOReturn.setPrimaryAccountNumber(fullfillmentEntryVO.getPrimaryAccountNumber());
		fullfillmentEntryVOReturn.setSortOrder(fullfillmentEntryVO.getSortOrder());
		fullfillmentEntryVOReturn.setMessageTypeId(fullfillmentEntryVO.getMessageTypeId());
		populateMessageIdentifier(fullfillmentEntryVO);
		fullfillmentEntryVOReturn.setMessageIdentifier(fullfillmentEntryVO.getMessageIdentifier());
		fullfillmentEntryVOReturn.setPromoCodeId(fullfillmentEntryVO.getPromoCodeId());
		fullfillmentEntryVOReturn.setPromoCode(fullfillmentEntryVO.getPromoCode());
		fullfillmentEntryVOReturn.setStanId(fullfillmentEntryVO.getStanId());
		fullfillmentEntryVOReturn.setSoId(fullfillmentEntryVO.getSoId());
		fullfillmentEntryVOReturn.setReferenceNo(fullfillmentEntryVO.getSoId());
		fullfillmentEntryVOReturn.setEntryDate(fullfillmentEntryVO.getEntryDate());
		fullfillmentEntryVOReturn.setCreatedDate(fullfillmentEntryVO.getCreatedDate());
		fullfillmentEntryVOReturn.setModifiedDate(fullfillmentEntryVO.getModifiedDate());
		return fullfillmentEntryVOReturn;
	}

	private void populateMessageIdentifier(FullfillmentEntryVO fullfillmentEntryVO) throws Exception {
		if ((fullfillmentEntryVO != null) && (fullfillmentEntryVO.getMessageTypeId() != null)) {
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
			} else if (FullfillmentConstants.MESSAGE_TYPE_VOID == fullfillmentEntryVO.getMessageTypeId().intValue()) {
				fullfillmentEntryVO.setMessageIdentifier(FullfillmentConstants.REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID);
			}
		}
	}
					
	public IFullfillmentDao getFullfillmentDao() {
		return fullfillmentDao;
				}
								
	public void setFullfillmentDao(IFullfillmentDao fullfillmentDao) {
		this.fullfillmentDao = fullfillmentDao;
	}
				
	public IIsoRequestProcessor getIsoRequestProcessor() {
		return isoRequestProcessor;
			}
			
	public void setIsoRequestProcessor(IIsoRequestProcessor isoRequestProcessor) {
		this.isoRequestProcessor = isoRequestProcessor;
		}

	public IIsoResponseProcessor getIsoResponseProcessor() {
		return isoResponseProcessor;
		}

	public void setIsoResponseProcessor(IIsoResponseProcessor isoResponseProcessor) {
		this.isoResponseProcessor = isoResponseProcessor;
	}

	public ILedgerFacilityBO getAccountingTransactionManagementBO() {
		return accountingTransactionManagementBO;
 				}

	public void setAccountingTransactionManagementBO(ILedgerFacilityBO accountingTransactionManagementBO) {
		this.accountingTransactionManagementBO = accountingTransactionManagementBO;
 			}

	public LookupDao getLookupDao() {
		return lookupDao;
 				}

	public void setLookupDao(LookupDao lookupDao) {
		this.lookupDao = lookupDao;
 			}

	public ISpringJMSFacility getJmsFacility() {
		return jmsFacility;
 			}

	public void setJmsFacility(ISpringJMSFacility jmsFacility) {
		this.jmsFacility = jmsFacility;
 			}

	public IBalanceInquiryProcessor getBalanceInquiryProcessor() {
		return balanceInquiryProcessor;
			}

	public void setBalanceInquiryProcessor(IBalanceInquiryProcessor balanceInquiryProcessor) {
		this.balanceInquiryProcessor = balanceInquiryProcessor;
 				}

	public IEmailTemplateBO getEmailTemplateBO() {
		return emailTemplateBO;
 			}

	public void setEmailTemplateBO(IEmailTemplateBO emailTemplateBO) {
		this.emailTemplateBO = emailTemplateBO;
 				}

	public ITemplateDao getTemplateDao() {
		return templateDao;
 			}

	public void setTemplateDao(ITemplateDao templateDao) {
		this.templateDao = templateDao;
 			}
	
	public BuyerDao getBuyerDao() {
		return buyerDao;
 			}

	
	public ServiceOrderDao getServiceOrderDao() {
		return serviceOrderDao;
 		}

	public void setServiceOrderDao(ServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
 		}

	public PromoBO getPromoBO() {
		return promoBO;
 	}
 	
	public void setPromoBO(PromoBO promoBO) {
		this.promoBO = promoBO;
	}
 	
	public IAdminToolProcessor getAdminToolProcessor() {
		return adminToolProcessor;
 		}

	public void setAdminToolProcessor(IAdminToolProcessor adminToolProcessor) {
		this.adminToolProcessor = adminToolProcessor;
	}

	public void setBuyerDao(BuyerDao buyerDao) {
		this.buyerDao = buyerDao;
	}

	public FeeConstants getFeeFacility() {
		return feeFacility;
	}

	public void setFeeFacility(FeeConstants feeFacility) {
		this.feeFacility = feeFacility;
	}
}
