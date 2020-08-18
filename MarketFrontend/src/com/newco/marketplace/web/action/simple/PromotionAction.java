package com.newco.marketplace.web.action.simple;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.business.iBusiness.promo.PromoBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.promo.PromoContentVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;

public class PromotionAction extends SLBaseAction {
	
	private static final long serialVersionUID = 1L;
	
	private IServiceOrderBO serviceOrderBo;
	private ISODetailsDelegate delegate;
	private PromoBO promoBO = null;
	private PromoContentVO promoContent = new PromoContentVO();
	private String soId = "";
	private String contentLocation ="";
	
	private static final Logger logger = Logger.getLogger("PromotionAction");
	
	public String displayPromotion(){

		createCommonServiceOrderCriteria();
		soId = getSoId();
		contentLocation = getContentLocation();
		ServiceOrder so = null;
		String groupId;
		try{
			if(StringUtils.isBlank(soId))
			{
				groupId = getParameter("groupId");
				if(groupId == null)
				{
					groupId = (String)getSession().getAttribute(OrderConstants.GROUP_ID);
				}
				soId = delegate.getFirstSoIdForGroup(groupId);
			}
			so = serviceOrderBo.getServiceOrder(soId);
		}catch(Exception e){
			logger.error("Service Order not found " + soId);
			return ERROR;
		}
			
		Integer loggedInRole = get_commonCriteria().getSecurityContext().getRoleId();
		
		if(loggedInRole == OrderConstants.PROVIDER_ROLEID){
			Integer roleId = so.getBuyer().getRoleId();
			PromoContentVO promoContentVO = new PromoContentVO();
			promoContentVO = promoBO.getSOPromotionContent(soId, contentLocation, roleId);
			promoContent = promoContentVO;
		}

		
		

		return SUCCESS;
	}


	protected HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getContentLocation() {
		return contentLocation;
	}

	public void setContentLocation(String contentLocation) {
		this.contentLocation = contentLocation;
	}

	public IServiceOrderBO getServiceOrderBo() {
		return serviceOrderBo;
	}

	public void setServiceOrderBo(IServiceOrderBO serviceOrderBo) {
		this.serviceOrderBo = serviceOrderBo;
	}

	public PromoBO getPromoBO() {
		return promoBO;
	}

	public void setPromoBO(PromoBO promoBO) {
		this.promoBO = promoBO;
	}


	public PromoContentVO getPromoContent() {
		return promoContent;
	}


	public void setPromoContent(PromoContentVO promoContent) {
		this.promoContent = promoContent;
	}


	public ISODetailsDelegate getDelegate() {
		return delegate;
	}


	public void setDelegate(ISODetailsDelegate delegate) {
		this.delegate = delegate;
	}

}
