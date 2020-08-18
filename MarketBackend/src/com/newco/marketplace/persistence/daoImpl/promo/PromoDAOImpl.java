package com.newco.marketplace.persistence.daoImpl.promo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.enums.UserRole;
import com.newco.marketplace.persistence.iDao.promo.PromoDAO;
import com.newco.marketplace.vo.promo.PromoContentVO;
import com.newco.marketplace.vo.promo.PromoVO;
import com.sears.os.dao.impl.ABaseImplDao;

public class PromoDAOImpl extends ABaseImplDao implements PromoDAO {

    public PromoVO retrievePromo(ServiceOrder serviceOrder, UserRole userRole) {
		return null;
	}

	public List<PromoVO> findAllActivePromos() {
		Map<String, Date> dateTimeMap = new HashMap<String, Date>();
		Date currentDate = new Date();
		dateTimeMap.put("beginDateTime", currentDate);
		dateTimeMap.put("endDateTime", currentDate);
        List<PromoVO> list = (List<PromoVO>) queryForList("promo.selectActivePromos", dateTimeMap);
        return list;
    }

	public List<PromoVO> findActivePromosByRole(int roleId) {
        List<PromoVO> list = (List<PromoVO>) queryForList("promo.selectActivePromosByRole", roleId);
        return list;
    }

	public List<PromoVO> findActivatedPromosBySoId(String soId) {
        List<PromoVO> list = (List<PromoVO>) queryForList("promo.selectActivePromosBySO", soId);
        return list;
    }

	public PromoVO findActivePromoByType(String promoType, Integer roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("promoType", promoType);
		params.put("roleId", roleId);
		PromoVO promoVO = (PromoVO) queryForObject("promo.selectActivePromoByType", params);
		return promoVO;
	}

	public void activatePromoForSoId(String soId ,int promoId) {
		Map params = new HashMap();
		params.put("soId", soId);
		params.put("promoId", promoId);
        insert("promo.activatePromoForSO", params);
    }

    public int updatePostingFee(ServiceOrder so) {
		return update("so.updatePostingFee", so);
	}

    public List<PromoContentVO> getContentByTagPromoId(String tag ,int promoId, int roleId) {
		Map params = new HashMap();
		params.put("promoId", promoId);
		params.put("contentLocation", tag);
		params.put("roleId", roleId);
		ArrayList<PromoContentVO> list = null;
        list = (ArrayList<PromoContentVO>) queryForList("promo.selectPromoContentByID_Tag", params);
        return list;
    }

    public List<PromoVO> findAllActiveSoPromos(String soId ) {  // so_id used to prevent baddness in repititions.
    	// code change for SLT-2112
    	Map<String, String> parameter = new HashMap<String, String>();
    			parameter.put("soId", soId);
		List<PromoVO> list = (ArrayList<PromoVO>) queryForList("promo.selectActiveSoPromos", parameter);
        return list;
    }

}
