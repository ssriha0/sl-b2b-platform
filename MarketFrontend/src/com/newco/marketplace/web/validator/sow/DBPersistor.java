package com.newco.marketplace.web.validator.sow;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.BuyerResource;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.FundingVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.wizard.review.SOWReviewAction;
import com.newco.marketplace.web.delegates.ISOWizardPersistDelegate;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrderWizardBean;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.utils.ObjectMapperWizard;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;

public class DBPersistor implements IPersistor{
	ISOWizardPersistDelegate SOWizardPersistBean;
	private static final Logger logger = Logger.getLogger(DBPersistor.class.getName());
	public ServiceOrder persist(ServiceOrderWizardBean serviceOrderWizardBean,ISOWizardPersistDelegate isoWizardPersistDelegate, Integer buyerId,  ServiceOrdersCriteria commonCriteria,boolean routingPriorityApplied) throws BusinessServiceException{
		ServiceOrder serviceOrder =null;
		
		if (serviceOrderWizardBean!= null)
		{
			HashMap<String, Object> tabDTOs = serviceOrderWizardBean.getTabDTOs();
			//tabDTOs.put(OrderConstants.SO_ID, serviceOrderWizardBean.getSoId());
			serviceOrder = ObjectMapperWizard.convertSOWTabDTOsTOSO(tabDTOs,serviceOrderWizardBean.getSoId());
			serviceOrder.setGroupId(serviceOrderWizardBean.getGroupId());
			//Always return false if FundingTypeId is null or its Non-funded or Direct-Funded
			boolean preFunded = true;
			SecurityContext context = (SecurityContext)getSession().getAttribute("SecurityContext");
			if(context!=null)
				context.setIncreaseSpendLimitInd(false);
			if(serviceOrder.getFundingTypeId()!= null && LedgerConstants.FUNDING_TYPE_NON_FUNDED == serviceOrder.getFundingTypeId().intValue()) 
			{
				preFunded = false;
			}
			
			ServiceOrderDTO soDTO = (ServiceOrderDTO) tabDTOs.get("review");
			FundingVO fundingVO = new FundingVO();
			if (soDTO != null && soDTO.isTryingToPost() && preFunded)
			{
				fundingVO = isoWizardPersistDelegate.checkBuyerFundsForIncreasedSpendLimit(serviceOrder,buyerId);
			}
			if (!fundingVO.isEnoughFunds() && (serviceOrder.getFundingTypeId()!= null &&  
					LedgerConstants.SHC_FUNDING_TYPE != serviceOrder.getFundingTypeId().intValue() 
							&& LedgerConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER != serviceOrder.getFundingTypeId().intValue())){
				serviceOrder.setUpdateSoPriceFlag(false);
				if(context!=null)
					context.setIncreaseSpendLimitInd(true);
			}

			
			Buyer buyer = new Buyer();
			buyer.setBuyerId(buyerId);
			serviceOrder.setBuyer(buyer);
			serviceOrder.setPostFromFE(serviceOrderWizardBean.isPost());
			serviceOrder.setRouteFromFE(serviceOrderWizardBean.isRouteFromFE());
			if(serviceOrderWizardBean.getSoId()== null)
			{	
				logger.info("serviceOrderWizardBean.getSoId() is null");
				String soId = isoWizardPersistDelegate.processCreateDraftSO(serviceOrder);
				serviceOrder.setSoId(soId);
			}
			else{
				logger.info("before processUpdateDraftSO");
			 	if(LedgerConstants.SHC_FUNDING_TYPE == serviceOrder.getFundingTypeId().intValue() ||
			 			LedgerConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER == serviceOrder.getFundingTypeId().intValue() ||
						(fundingVO.isEnoughFunds() && !context.isIncreaseSpendLimitInd())){
					serviceOrder.setSoId(serviceOrderWizardBean.getSoId());
					//If wfstate_id is null, it means this is the first time the order is getting saved as Draft.
					//Set Order Creation date to NOW, only if this is the first time "Save as Draft" is clicked by user.
					if(serviceOrder.getWfStateId() ==  null) {
						//SL 15642 Setting  newSoIndicator as true when SO is being posted after copying SO
						serviceOrder.setNewSoIndicator(true);
						serviceOrder.setCreatedDate(new Timestamp(System.currentTimeMillis()));
						serviceOrder.setWfStateId(new Integer(OrderConstants.DRAFT_STATUS));
					}
					logger.info("just before processUpdateDraftSO");
					isoWizardPersistDelegate.processUpdateDraftSO(serviceOrder,routingPriorityApplied);
				}else{//did not perform edit throw error
					throw new BusinessServiceException("Either there was not enough money or it was increase spend limit - Order was not edited.");					
				}

			} 
		}
		return serviceOrder;
		
	}


	public ServiceOrder persist(ServiceOrderWizardBean serviceOrderWizardBean,ISOWizardPersistDelegate isoWizardPersistDelegate, Integer buyerId, boolean routingPriorityApplied) throws BusinessServiceException{
		ServiceOrder serviceOrder =null;
		
		if (serviceOrderWizardBean!= null)
		{
			
			
			HashMap<String, Object> tabDTOs = serviceOrderWizardBean.getTabDTOs();
			//tabDTOs.put(OrderConstants.SO_ID, serviceOrderWizardBean.getSoId());
			serviceOrder = ObjectMapperWizard.convertSOWTabDTOsTOSO(tabDTOs,serviceOrderWizardBean.getSoId());
			serviceOrder.setGroupId(serviceOrderWizardBean.getGroupId());
			//Always return false if FundingTypeId is null or its Non-funded or Direct-Funded
			boolean preFunded = true;
			//sl-18007
			serviceOrder.setAutoPost(serviceOrderWizardBean.isAutoPost());
			serviceOrder.setSaveAsDraftFE(serviceOrderWizardBean.isSaveAsDraft());
			if(serviceOrder.getFundingTypeId()!= null && LedgerConstants.FUNDING_TYPE_NON_FUNDED == serviceOrder.getFundingTypeId().intValue()) 
			{
				preFunded = false;
			}
			
			ServiceOrderDTO soDTO = (ServiceOrderDTO) tabDTOs.get("review");
			FundingVO fundingVO = new FundingVO();
			if (soDTO != null && soDTO.isTryingToPost() && preFunded)
			{
				fundingVO = isoWizardPersistDelegate.checkBuyerFundsForIncreasedSpendLimit(serviceOrder,buyerId);
			}

			
			Buyer buyer = isoWizardPersistDelegate.getBuyerAttrFromBuyerId(buyerId);
			serviceOrder.setBuyer(buyer);
            serviceOrder.setFundingTypeId(buyer.getFundingTypeId());
			if(serviceOrderWizardBean.getSoId()== null)
			{
				String soId = isoWizardPersistDelegate.processCreateDraftSO(serviceOrder);
				serviceOrder.setSoId(soId);
			}
			else{
				serviceOrder.setSoId(serviceOrderWizardBean.getSoId());
				//If wfstate_id is null, it means this is the first time the order is getting saved as Draft.
				//Set Order Creation date to NOW, only if this is the first time "Save as Draft" is clicked by user.
				if(serviceOrder.getWfStateId() ==  null) {
					//SL 15642 Setting  newSoIndicator as true when SO is being created for the first time 
					serviceOrder.setNewSoIndicator(true);
					serviceOrder.setCreatedDate(new Timestamp(System.currentTimeMillis()));
					serviceOrder.setWfStateId(new Integer(OrderConstants.DRAFT_STATUS));
					serviceOrder.setSaveAsDraft(serviceOrderWizardBean.isSaveAsDraft());
				}				
				isoWizardPersistDelegate.processUpdateDraftSO(serviceOrder,routingPriorityApplied);

			}
		}
		return serviceOrder;
		
	}


	public ServiceOrder persistSimpleServiceOrder(ServiceOrderWizardBean serviceOrderWizardBean,
			ISOWizardPersistDelegate isoWizardPersistDelegate, Integer buyerId,boolean routingPriorityApplied)
			throws BusinessServiceException{
		ServiceOrder serviceOrder =null;

		if (serviceOrderWizardBean!= null)
		{
			HashMap<String, Object> tabDTOs = serviceOrderWizardBean.getTabDTOs();

			serviceOrder = ObjectMapperWizard.convertSSODTOstoSO(tabDTOs,serviceOrderWizardBean.getSoId());

			Buyer buyer = new Buyer();
			buyer.setBuyerId(buyerId);
			serviceOrder.setBuyer(buyer);
			serviceOrder.setSoId(serviceOrderWizardBean.getSoId());
			if(serviceOrder.getWfStateId() ==  null) {
				serviceOrder.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			}
			serviceOrder.setWfStateId(new Integer(OrderConstants.DRAFT_STATUS));
			isoWizardPersistDelegate.processUpdateDraftSO(serviceOrder,routingPriorityApplied);

		}
		return serviceOrder;
	}

	public ServiceOrder createBareBonesServiceOrder(ISOWizardPersistDelegate isoWizardPersistDelegate, Integer buyerId,
			Integer buyerResourceId, Integer buyerResContactId,String userName) {
		ServiceOrder serviceOrder =null;
		if(buyerResourceId!=null && buyerId!=null && buyerResContactId!=null) {
			serviceOrder = new ServiceOrder();
			//Set Buyer Id (Buyer Company) and initialize Buyer Contact
			Buyer buyer = new Buyer();
			buyer.setBuyerId(buyerId);
			Contact buyerContact = new Contact();
			buyer.setBuyerContact(buyerContact);
			serviceOrder.setBuyer(buyer);
			//Set Buyer Resource Id (Buyer associate who is creating the Service Order)
			BuyerResource buyerResource = new BuyerResource();
			buyerResource.setResourceId(buyerResourceId);
			buyerResource.setContactId(buyerResContactId);

			//Set Buyer Resource into Buyer and ServiceOrder
			serviceOrder.setBuyerResource(buyerResource);
			serviceOrder.setBuyerContactId(buyerResContactId);//Set Buyer Resource Contact id as SO buyerContactId

			serviceOrder.setBuyerResourceId(buyerResourceId);
			serviceOrder.setCreatorUserName(userName);
			String soId = isoWizardPersistDelegate.processCreateDraftSO(serviceOrder);
			serviceOrder.setSoId(soId);

		}
		return serviceOrder;
	}

	public ServiceOrder
			createBareBonesSimpleServiceOrder(ISOWizardPersistDelegate isoWizardPersistDelegate,
											  Integer buyerId)
	{
		ServiceOrder serviceOrder =null;
		if(buyerId!=null) {
			serviceOrder = new ServiceOrder();
			//Set Buyer Id (Buyer Company) and initialize Buyer Contact
			Buyer buyer = new Buyer();
			buyer.setBuyerId(buyerId);
			Contact buyerContact = new Contact();
			buyer.setBuyerContact(buyerContact);
			serviceOrder.setBuyer(buyer);
			//Set Buyer Resource Id (Buyer associate who is creating the Service Order)
//			BuyerResource buyerResource = new BuyerResource();
//			buyerResource.setResourceId(buyerResourceId);
//			buyerResource.setContactId(buyerResContactId);
//
//			//Set Buyer Resource into Buyer and ServiceOrder
//			serviceOrder.setBuyerResource(buyerResource);
//			serviceOrder.setBuyerContactId(buyerResContactId);//Set Buyer Resource Contact id as SO buyerContactId
//
//			serviceOrder.setBuyerResourceId(buyerResourceId);
			String soId = isoWizardPersistDelegate.processCreateDraftSO(serviceOrder);
			serviceOrder.setSoId(soId);

		}
		return serviceOrder;
	}

	protected HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

}
