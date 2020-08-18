/**
 * This is a utility class related to service order object
 */
package com.newco.marketplace.util.so;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.ledger.MarketPlaceTransactionVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.utils.SecurityUtil;

/**
 * @author Mahmud Khair
 *
 */
public class ServiceOrderUtil {
	
	private static final Logger logger = Logger.getLogger(ServiceOrderUtil.class);
	
	/**
	 * Iterates the custom ref values found in given service order; matches the reference field request; if found returns the corresponding value
	 * 
	 * @param so
	 * @param customRefType
	 * @return
	 */
	public static String getCustomReferenceValueByType(ServiceOrder so, String customRefType) {
		String customRefValue = null;
		List<ServiceOrderCustomRefVO> customRefs = so.getCustomRefs();
		if (customRefs != null && !customRefs.isEmpty()) {
			for (ServiceOrderCustomRefVO customRef : customRefs) {
				if (customRefType.equals(customRef.getRefType())) {
					customRefValue = customRef.getRefValue();
					break;
				}
			}
		}
		return customRefValue;
	}
	
	public static void enrichSecurityContext(SecurityContext securityContext, Integer buyerId){
		
		try {
			ApplicationContext ctx = MPSpringLoaderPlugIn.getCtx();
			IFinanceManagerBO financeManagerBO = (IFinanceManagerBO) ctx.getBean("financeManagerBO");
			Account autoAch = financeManagerBO.getAutoFundingIndicator(buyerId);
			if (autoAch.isEnabled_ind()){
				securityContext.setAutoACH(true);
				securityContext.setRole(OrderConstants.BUYER_ROLE);
				securityContext.setAccountID(autoAch.getAccount_id());
			}else{
				securityContext.setAutoACH(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return securityContext;
	}
	
	public static SecurityContext getSecurityContextForBuyer(Integer buyerId){
		SecurityContext securityContext = SecurityUtil.getSystemSecurityContext();
		securityContext.setCompanyId(buyerId);
		enrichSecurityContext(securityContext, buyerId);
		return securityContext;
	}
	
	/**
	 * Description: Main Sku's Final Amount = Final Spend Labor + Final Spend Parts
	 * @param marketVO
	 * @return
	 */
	public static double calcMainSkuFinalAmt(ServiceOrder so) {
		double mainSkuFinalAmount = 0.0;
		double labor = 0.0;
		double parts = 0.0;
		if (so.getLaborFinalPrice() != null){
			labor = so.getLaborFinalPrice();
		}
		if (so.getPartsFinalPrice() != null){
			parts = so.getPartsFinalPrice();
		}
		//Need Money Util here
		mainSkuFinalAmount = MoneyUtil.getRoundedMoney(labor + parts);
		logger.debug("order Final Amount " + mainSkuFinalAmount);
		return mainSkuFinalAmount;
	}
}
