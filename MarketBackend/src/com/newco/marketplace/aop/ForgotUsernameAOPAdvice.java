/**
 * 
 */
package com.newco.marketplace.aop;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.aop.AOPAdviceVO;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.persistence.iDao.provider.IContactDao;
import com.newco.marketplace.vo.provider.Contact;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

/**
 * @author karuppannan_p
 * 
 */
public class ForgotUsernameAOPAdvice extends BaseAOPAdvice {
	private IContactDao contactDao;

	public void before(Method arg0, Object[] arg1, Object arg2)
			throws Throwable {
		// Not implemented as of now
	}

	@SuppressWarnings("unchecked")
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		try {
			String methodName = method.getName();
			logger.debug("ForgotUsernameAOPAdvice-->afterReturning()-->MethodName=" + methodName);

			if (returnValue != null) {
				if (returnValue instanceof com.newco.marketplace.webservices.base.response.ProcessResponse) {
					if (((ProcessResponse) returnValue).isError()) {
						return;
					}
				}
			}

			// Fetch the AOP Types - Alert, Logging, Caching from database using
			// MethodName/Action
			List<AOPAdviceVO> alAOPAdviceVOs = getAOPAdvices(methodName);
			int iSize = alAOPAdviceVOs.size();

			Map<String, Object> hmParams = null;

			// Using Reflection, Call AOPMapper class's method to initialize the
			// HashMap with method arguments
			AOPMapper aopParamMap = new AOPMapper(args);
			Method methodNameParam = null;
			try { 
				methodNameParam = aopParamMap.getClass().getMethod(methodName);
			} catch (NoSuchMethodException me) {
				if (iSize != 0) {
				// if AOP has been configured in the database, we should not
				// be here
					logger.error("ForgotUsernameAOPAdvice-->afterReturning()-->NoSuchMethodException-->", me);
				}
				return;
			} catch (ClassCastException ce) {
				logger.error(
						"ForgotUsernameAOPAdvice-->afterReturning()-->ClassCastException-->"
								+ "The method " + methodName
								+ " last argument is not SecurityContext ", ce);
			}

			hmParams = (Map<String, Object>) methodNameParam.invoke(aopParamMap);
			hmParams.put(AOPConstants.AOP_METHOD_NAME, methodName);
			Contact contact = new Contact();
			contact.setContactId(contactDao.getContactIdByUserName((String)hmParams.get(AOPConstants.AOP_USERNAME)));
			contact = contactDao.query(contact);
			hmParams.put(AOPConstants.AOP_BUYERUSERNAME, contact.getFirstName() + " " + contact.getLastName());

			for (AOPAdviceVO aopAdviceVO : alAOPAdviceVOs) {
				try {

					// Check for AOP Advice name
					// For Alert
					if (AOPConstants.AOP_ADVICE_ALERT.equals(aopAdviceVO.getAopName())) {
						handleAOPAlertsForRequest(hmParams, aopAdviceVO);
					}
					// Logging
					else if (AOPConstants.AOP_ADVICE_LOGGING.equals(aopAdviceVO
							.getAopName())) {
						handleAOPLogging(aopAdviceVO, hmParams);
					}
					// Caching
					else if (AOPConstants.AOP_ADVICE_CACHING.equals(aopAdviceVO
							.getAopName())) {
						handleAOPCaching(aopAdviceVO, hmParams);
					}
				} catch (Exception e) {
					logger.error("ForgotUsernameAOPAdvice-->afterReturning()-->EXCEPTION-->"
									+ e.getMessage());
					continue;
				}
			}
		} catch (Exception ex) {
			logger.error("ForgotUsernameAOPAdvice-->afterReturning()-->EXCEPTION-->"
							+ ex.getMessage());
		}
	}

	/**
	 * @return the contactDao
	 */
	public IContactDao getContactDao() {
		return contactDao;
	}

	/**
	 * @param contactDao the contactDao to set
	 */
	public void setContactDao(IContactDao contactDao) {
		this.contactDao = contactDao;
	}
}