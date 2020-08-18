/**
 * 
 */
package com.newco.marketplace.business.businessImpl.alert;

import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.dto.vo.alert.StateTransitionVO;
import com.newco.marketplace.dto.vo.alert.WfStateTransitionVO;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.persistence.iDao.alert.WfStateTransitionDao;

/**
 * @author sahmad7
 * 
 */
public class ServiceOrderStateDelegator {

	private final static Logger logger = Logger
			.getLogger(ServiceOrderStateDelegator.class.getName());

	public boolean delegateStateTransition(Object stateObject) {

		StateTransitionVO stateTransitionVO = (StateTransitionVO)stateObject;
		WfStateTransitionVO wfVo = new WfStateTransitionVO();
		wfVo.setTargetState(MPConstants.ROUTED);
		Class classDefinition;
		
		WfStateTransitionDao wfStateTransitionDao = (WfStateTransitionDao) MPSpringLoaderPlugIn.getCtx().getBean(ProviderConstants.STATE_TRANSITION_DAO);
		try {
//			wfVo = (WfStateTransitionVO) wfStateTransitionDao.query(wfVo);
//			classDefinition = Class.forName(wfVo.getActionClass());
//			StateTransitionRouter obj = (StateTransitionRouter) classDefinition.newInstance();
//			obj.generateAlert(stateTransitionVO);
			return true;
		} catch (Exception e){
			logger.info("ServiceOrderStateDelegator:delegateStateTransition(): " + e);
			return false;
		}
		
	}
	
	public Object loadBeanFromContext(String beanName){
		
		return MPSpringLoaderPlugIn.getCtx().getBean(beanName);
		
	}
}
