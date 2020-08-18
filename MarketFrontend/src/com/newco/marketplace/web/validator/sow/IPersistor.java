package com.newco.marketplace.web.validator.sow;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.delegates.ISOWizardPersistDelegate;
import com.newco.marketplace.web.dto.ServiceOrderWizardBean;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;

public interface IPersistor {
	public ServiceOrder persist(ServiceOrderWizardBean serviceOrderWizardBean, ISOWizardPersistDelegate isoWizardPersistDelegate, Integer buyerId, ServiceOrdersCriteria serviceOrdersCriteria,boolean routingPriorityApplied) throws BusinessServiceException;
	public ServiceOrder createBareBonesServiceOrder(ISOWizardPersistDelegate isoWizardPersistDelegate, Integer buyerId, Integer buyerResourceId, Integer buyerResContactId,String userName);
	public ServiceOrder persistSimpleServiceOrder(ServiceOrderWizardBean serviceOrderWizardBean, ISOWizardPersistDelegate isoWizardPersistDelegate, Integer buyerId, boolean routingPriorityApplied) throws BusinessServiceException;
	public ServiceOrder createBareBonesSimpleServiceOrder(ISOWizardPersistDelegate isoWizardPersistDelegate, Integer buyerId);
	public ServiceOrder persist(ServiceOrderWizardBean serviceOrderWizardBean, ISOWizardPersistDelegate isoWizardPersistDelegate, Integer buyerId,boolean routingPriorityApplied) throws BusinessServiceException; 

}
