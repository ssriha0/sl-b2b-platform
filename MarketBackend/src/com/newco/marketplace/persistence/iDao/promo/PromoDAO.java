/**
 * 
 */
package com.newco.marketplace.persistence.iDao.promo;

import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.vo.promo.PromoContentVO;
import com.newco.marketplace.vo.promo.PromoVO;

/**
 * @author nsanzer
 * 
 */
public interface PromoDAO {
	
	public List<PromoVO> findAllActivePromos();

	public int updatePostingFee(ServiceOrder so);

	
	public List<PromoVO> findActivePromosByRole(int roleId);
	public List<PromoVO> findActivatedPromosBySoId(String soId);
	public PromoVO findActivePromoByType(String promoType, Integer roleId);
	
	public void activatePromoForSoId(String soId ,int promoId);
		
	public List<PromoVO> findAllActiveSoPromos(String soId);

	public List<PromoContentVO> getContentByTagPromoId(String tag ,int promoId, int roleId);
	
	
	
	
}
