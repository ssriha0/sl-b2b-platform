package com.newco.marketplace.promo;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.promo.PromoBO;
import com.newco.marketplace.dto.vo.fee.PromoConstants;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.promo.PromoDAO;
import com.newco.marketplace.persistence.iDao.so.buyer.BuyerDao;
import com.newco.marketplace.vo.promo.PromoContentVO;
import com.newco.marketplace.vo.promo.PromoVO;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.lookup.ILookupBO;
import com.servicelive.lookup.vo.AdminLookupVO;
import com.servicelive.lookup.vo.BuyerLookupVO;
import com.servicelive.wallet.serviceinterface.IWalletBO;
import com.servicelive.wallet.serviceinterface.requestbuilder.WalletRequestBuilder;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;

/**
 * @author nsanzer
 * 
 */
public class PromoBOImpl implements PromoBO {
	private PromoDAO promoDAO;
	private IBuyerBO buyerBO;
	private BuyerDao buyerDao;
	private IWalletBO walletBO;
	private WalletRequestBuilder walletRequestBuilder = new WalletRequestBuilder();
	private ILookupBO lookupBO;
	private static final Logger logger = Logger.getLogger("PromoBOImpl");
	
	public double retrievePromoPostingFeeSimpleBuyer(double postingFee) {
		List<PromoVO> promoVOs = promoDAO.findAllActivePromos();
		for (PromoVO promoVO : promoVOs) {
			if (promoVO.getPromoType().equals(
					PromoConstants.POSTING_FEE_TYPE)
					&& promoVO.getRoleID() == OrderConstants.SIMPLE_BUYER_ROLEID) {
				postingFee = promoVO.getPromoValue();
				break;
			}
		}
		return postingFee;
	}

	public Double retrievePromoPostingFeeByRoleId(double roleId) {
		Double postingFee = null;
		List<PromoVO> promoVOs = promoDAO.findAllActivePromos();
		for (PromoVO promoVO : promoVOs) {
			if (promoVO.getPromoType().equals(
					PromoConstants.POSTING_FEE_TYPE)
					&& promoVO.getRoleID() == roleId) {
				postingFee = promoVO.getPromoValue();
				break;
			}
		}
		return postingFee;
	}

	public void applyPromoPostingFee(ServiceOrder so, Buyer abuyer) {
		List<PromoVO> promoVOs = promoDAO.findAllActivePromos();
		for (PromoVO promoVO : promoVOs) {
			if (promoVO.getPromoType().equals(
					PromoConstants.POSTING_FEE_TYPE)
					&& promoVO.getRoleID() == abuyer.getRoleId()) {
				so.setPostingFee(promoVO.getPromoValue());
				break;
			}
		}
	}

	public void applyPromoWalletCredit(String promoCode, String promoType, Buyer buyer, double creditAmount) 
			throws SLBusinessServiceException {
		Long buyerId = new Long(buyer.getBuyerId());
		BuyerLookupVO buyerVO = lookupBO.lookupBuyer(buyerId);
		AdminLookupVO adminVO = lookupBO.lookupAdmin();

		PromoVO promoVO = promoDAO.findActivePromoByType(promoType, buyer.getRoleId());
		if (promoVO == null) {
			throw new SLBusinessServiceException("unknown promo: promoType=" + promoType + ", roleId=" + buyer.getRoleId());
		} else if (creditAmount > promoVO.getMaxPossibleValue()) {
			throw new SLBusinessServiceException("credit amount too high: promoType=" + promoType + ", roleId=" + buyer.getRoleId());
		}

		if (creditAmount <= 0.0) {
			creditAmount = promoVO.getPromoValue();
		}
		
		if (creditAmount > 0.0) {
			WalletVO request = walletRequestBuilder.adminCreditToBuyer(buyer.getUserName(), buyerId, 
					buyerVO.getBuyerState(), buyerVO.getBuyerV1AccountNumber(), adminVO.getSl3AccountNumber(),
					promoCode, creditAmount);
	
			walletBO.adminCreditToBuyer(request);
		}
	}

	public void updatePostingFee(ServiceOrder so) {
		promoDAO.updatePostingFee(so);
	}

	/**
	 * @param promoDAO
	 *            the promoDAO to set
	 */
	public void setPromoDAO(PromoDAO promoDAO) {
		this.promoDAO = promoDAO;
	}

	public PromoVO applySOPromotion(String so_id) {
		// TODO Auto-generated method stub
		return null;
	}

	public PromoVO getSOPromotion(String so_id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public double getPromoFee(String soId, Long buyerId, String feeType) throws BusinessServiceException{
		
		double value = getDefault(feeType);
		try{
			List<PromoVO> promoVOs = promoDAO.findActivatedPromosBySoId(soId);// select from so_promo joined to promo

			Buyer buyer = buyerBO.getBuyer(buyerId.intValue());
			Integer roleId = getBuyerDao().getBuyerRole(buyer.getUserName());
						
			for (PromoVO promoVO : promoVOs) {
				if(promoVO.getPromoType().equals(feeType)){
					if(roleId == promoVO.getRoleID()){
						value  = promoVO.getPromoValue();
						break;
					}
					
				}
			}
			
		}catch(Exception e){
			logger.error("erro occured while grtting Promo Fee due to " + e.getMessage());
			throw new BusinessServiceException(e);
		}

		//promo_value we will only return the value "Replace";
		return value;
	}

	private double getDefault(String feeType) {
		double defaultFee = 0.0;
		Double fee = (Double)PromoConstants.promoMap.get(feeType);
		if(fee != null){
			defaultFee = fee.doubleValue();
		}
		return defaultFee;
	}

//
//	private String getPromoTypeForFeeType(String feeType) {
//		// TODO Auto-generated method stub
//		if(feeType.equals("S")){}
//		
//		return null;
//	}

	
	
	public void applySOPromotions(String so_id, Long buyerId) throws BusinessServiceException {
		
		try{
			// look for all active promotions that are so dependant.
			List<PromoVO> promoVOs = promoDAO.findAllActiveSoPromos(so_id);
			
			//get buyer role id
			Buyer buyer = buyerBO.getBuyer(buyerId.intValue());
			Integer roleId = getBuyerDao().getBuyerRole(buyer.getUserName());
			// insert into so_promo table.
			for (PromoVO promoVO : promoVOs) {
				if(roleId == promoVO.getRoleID()){
					promoDAO.activatePromoForSoId(so_id,promoVO.getPromoID());
				}
			}
			
		}catch(Exception e){
			logger.error("error occurred in applySOPromotions");
			throw new BusinessServiceException("Error occured while applying promotions");
		}

		
	}
	public PromoContentVO getSOPromotionContent(String so_id, String contentLocation, int roleId) {
		PromoContentVO promoContentVO = new PromoContentVO();
		
		try{
			List<PromoVO> promoVOs = promoDAO.findActivatedPromosBySoId(so_id);
			//int promoId = 0;
			
			for (PromoVO promoVO : promoVOs) {
				
				if(roleId == promoVO.getRoleID()){
					for(PromoContentVO promoContent : promoVO.getPromoContent()){
						if(promoContent.getContentLocation().equals(contentLocation)){
							//promoId  = promoVO.getPromoID();
							promoContentVO = promoContent;
							break;
						}
					}
				}
/*				
				if(promoVO.getPromoContent().getContentLocation().equals(contentLocation)){
					if(roleId == promoVO.getRoleID()){
						promoId  = promoVO.getPromoID();
						break;
					}
				}*/
			}
/*			List<PromoContentVO> promoContentVOList = promoDAO.getContentByTagPromoId(contentLocation, promoId, roleId);
			if(promoContentVOList!= null && promoContentVOList.size() > 0){
				promoContentVO = promoContentVOList.get(0);
			}*/
		
		}catch(Exception e){
			logger.error("error occurred while getting soPromotion Content");
		}
		return promoContentVO;
	}


	public PromoDAO getPromoDAO() {
		return promoDAO;
	}

	public IBuyerBO getBuyerBO() {
		return buyerBO;
	}

	public void setBuyerBO(IBuyerBO buyerBO) {
		this.buyerBO = buyerBO;
	}

	public BuyerDao getBuyerDao() {
		return buyerDao;
	}

	public void setBuyerDao(BuyerDao buyerDao) {
		this.buyerDao = buyerDao;
	}

	public void setWalletBO(IWalletBO walletBO) {
		this.walletBO = walletBO;
	}

	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

}
