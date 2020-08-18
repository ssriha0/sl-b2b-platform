package com.newco.marketplace.web.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.newco.marketplace.constants.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.so.order.ServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.constants.SOConstants;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthenticationInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(AuthenticationInterceptor.class);
	
	public AuthenticationInterceptor() {
		super();
		log.info("Initializing ServiceLive AuthenticationInterceptor");
	}
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		Class<? extends Object> actionClass = invocation.getAction().getClass();
		
		if ( isOrderAssociatedPage(actionClass) ) {
					
			final ActionContext context = invocation.getInvocationContext();
			final HttpServletRequest request = (HttpServletRequest) context
					.get(StrutsStatics.HTTP_REQUEST);		
			SecurityContext securityContext = 
				(SecurityContext) request.getSession().getAttribute(SOConstants.SECURITY_KEY);			
			
			String orderId = extractOrderId(request);		
			
			if ( !isAssociated(securityContext, orderId) ) {
				log.warn("User " + securityContext.getUsername() + " made an attempt to access " + orderId + "!");
				return "NOT_ASSOCIATED_SO";
			}
			
		}
		return invocation.invoke();
	}

	private String extractOrderId(final HttpServletRequest request) {
		String orderId = null;
		orderId = (String) request.getParameter("soId");
		if (StringUtils.isBlank(orderId)){	
			orderId = (String) request.getAttribute("soId");
		}
		if (StringUtils.isBlank(orderId)){		
			orderId = (String) request.getSession().getAttribute("soId");
		}
		if (StringUtils.isBlank(orderId)){		
			orderId = (String) request.getSession().getAttribute(OrderConstants.SO_ID);
		}
		if (StringUtils.isBlank(orderId)){
			orderId = (String) request.getParameter("groupId");
		}
		if (StringUtils.isBlank(orderId)){
			orderId = (String)request.getSession().getAttribute(OrderConstants.GROUP_ID);
		}		
		if (StringUtils.isBlank(orderId)){
			orderId = (String)request.getSession().getAttribute("groupId");
		}
		return orderId;
	}

	private boolean isAssociated(SecurityContext securityContext, String orderId) throws Exception {
		boolean result = false;
		
		if (StringUtils.isBlank(orderId)) {
			return true;  // If there is not order id string, this is in create mode. let it go through.
		}
		
		ServiceOrderBO serviceOrderBO = 
			(ServiceOrderBO) MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderBOTarget");
		
		Integer roleId = securityContext.getRoleId();
		ServiceOrder soObj = getOrderObject(orderId, serviceOrderBO);
		
		if (soObj != null) {

			int companyId = securityContext.getCompanyId();
			int adminRoleId = securityContext.getAdminRoleId();

			if (roleId == OrderConstants.PROVIDER_ROLEID
					&& adminRoleId != OrderConstants.NEWCO_ADMIN_ROLEID) {
				if (soObj.getPriceModel().equals(Constants.PriceModel.BULLETIN)) {
					// grant access to the service order if it is a bulletin board order
					result = true;
				} else {
					try {
						result = serviceOrderBO.isVendorAssociatedToServiceOrder(
								soObj, companyId);
					} catch (BusinessServiceException e) {
						throw new Exception(e);
					}
				}
			} else if ((roleId == OrderConstants.BUYER_ROLEID || roleId == OrderConstants.SIMPLE_BUYER_ROLEID)
					&& adminRoleId != OrderConstants.NEWCO_ADMIN_ROLEID) {
				try {
					result = serviceOrderBO.isBuyerAssociatedToServiceOrder(
							soObj, companyId);
				} catch (BusinessServiceException e) {
					throw new Exception(e);
				}

			} else if (roleId == OrderConstants.NEWCO_ADMIN_ROLEID
					|| adminRoleId == OrderConstants.NEWCO_ADMIN_ROLEID) {
				result = true;
			}
		}else{ // if there is a serviceorder but it is not in the database it is a draft, let it go through.
			
			return true;
		}
		
		return result;
	}

	private ServiceOrder getOrderObject(String orderId,  ServiceOrderBO serviceOrderBO) throws Exception {
		
		ServiceOrder soObj = null;
		
		try {
			soObj = serviceOrderBO.getServiceOrder(orderId);
			if(soObj == null) {
				ServiceOrder myList = serviceOrderBO.getGroupedServiceOrders(orderId);
				if(myList != null) {
					soObj = myList;
				}
			}
		} catch (BusinessServiceException e) {
			throw new Exception(e);
		}
		return soObj;
	}

	private boolean isOrderAssociatedPage(Class<? extends Object> actionClass) {
		return actionClass.isAnnotationPresent(OrderAssociationPage.class);
	}

}
