/**
 * 
 */
package com.newco.marketplace.business.iBusiness.promo;

import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.promo.PromoContentVO;
import com.newco.marketplace.vo.promo.PromoVO;
import com.servicelive.common.exception.SLBusinessServiceException;

/**
 *
 * @author nsanzer
 */
public interface PromoBO {
	public double retrievePromoPostingFeeSimpleBuyer(double postingFee);
	public Double retrievePromoPostingFeeByRoleId(double roleId);
    public void applyPromoPostingFee(ServiceOrder so , Buyer abuyer );
    public void applyPromoWalletCredit(String promoCode, String promoType, Buyer buyer, double creditAmount) throws SLBusinessServiceException;
	public void updatePostingFee(ServiceOrder so);
	//public PromoVO getSOPromotion(String so_id);
	//public PromoVO applySOPromotion(String so_id);
	

	//	public PromoVO getSOPromotion(String so_id); 
	/* goes out and figures what promotions should be attahed to a so
 	 * Called when post
	 */ 
	public void applySOPromotions(String so_id, Long buyerId) throws BusinessServiceException;
	
	//takes in promo_type which is mapped to fee type and SO
	public double getPromoFee(String soId, Long buyerId, String fee_type) throws BusinessServiceException;
	
	
	// this looks for active promos for this serviceorder, if there is none it will see if there should be some.
	// if it finds some then it looks up in the promo_content table on this location and returns the contentVO
	public PromoContentVO getSOPromotionContent(String so_id , String contentLocation , int roleId);
		
	
	
	
	
}
