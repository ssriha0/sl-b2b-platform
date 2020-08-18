package com.servicelive.orderfulfillment.orderprep;

import java.util.ArrayList;
import java.util.List;

import com.servicelive.orderfulfillment.common.ServiceOrderValidationException;
import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ValidationHolder;
import com.servicelive.orderfulfillment.domain.type.NoteType;
import com.servicelive.orderfulfillment.domain.type.ProblemType;
import com.servicelive.orderfulfillment.domain.type.RoleType;
import com.servicelive.orderfulfillment.orderprep.buyer.OrderBuyer;
import com.servicelive.orderfulfillment.orderprep.processor.IOrderEnhancementProcessor;
import com.servicelive.orderfulfillment.validation.IServiceOrderValidator;

public class OrderEnhancementService {
    protected final Logger logger = Logger.getLogger(getClass());

    private static final long TEN_SECONDS_IN_MILLIS = 10 * 1000L;
    OrderBuyer orderBuyer;
    List<IOrderEnhancementProcessor> orderEnhancementProcessors;
    IServiceOrderValidator commonServiceOrderValidator;
    IServiceOrderValidator orderEnhancementValidator;


    public boolean enhanceServiceOrder(ServiceOrder serviceOrder) {
    	if (!serviceOrder.isOrderPrepRequired()) return false;
    	 
        waitOrderBuyerInitialized();

        serviceOrder.setValidationHolder(new ValidationHolder());
        OrderEnhancementContext orderEnhancementContext = new OrderEnhancementContext(orderBuyer);
        for (IOrderEnhancementProcessor enhancementProcessor : orderEnhancementProcessors) {
            enhancementProcessor.enhanceOrder(serviceOrder, orderEnhancementContext);
        }

        executeValidations(serviceOrder);
        assertNoValidationErrors(serviceOrder);
        processValidationWarnings(serviceOrder);
        return true;
    }

    private void waitOrderBuyerInitialized() {
        int retries = 6;
        while (!orderBuyer.isInitialized() && retries>0) {
            retries--;
            try {
                Thread.sleep(TEN_SECONDS_IN_MILLIS);
            } catch (InterruptedException ignored) {}
        }

        if (!orderBuyer.isInitialized()) {
            throw new ServiceOrderException("OrderBuyer object not yet initialized for buyer " + orderBuyer.getBuyerId());
        }
    }

    private void executeValidations(ServiceOrder serviceOrder) {
        ValidationHolder validationHolder = serviceOrder.getValidationHolder();
        commonServiceOrderValidator.validate(serviceOrder, validationHolder);
        orderEnhancementValidator.validate(serviceOrder, validationHolder);
    }

    private void assertNoValidationErrors(ServiceOrder serviceOrder) {
        if (serviceOrder.getValidationHolder().getErrors().size() > 0) {
            //StringBuilder sbldr = new StringBuilder("\nServiceOrder during order prep validation found errors:\n");
            //for (ProblemType error : serviceOrder.getValidationHolder().getErrors()) {
            //    sbldr.append(error.getMsg()).append("\n");
            //}
            //logger.error(sbldr.toString());
            //throw new ServiceOrderException(sbldr.toString());
            List<String> validationErrors = new ArrayList<String>();
            for (ProblemType error : serviceOrder.getValidationHolder().getErrors()){
                validationErrors.add(error.getMsg());
            }
            throw new ServiceOrderValidationException(validationErrors);
        }
    }

    private void processValidationWarnings(ServiceOrder serviceOrder) {
        addSONoteForEveryValidationWarning(serviceOrder);
    }

    private void addSONoteForEveryValidationWarning(ServiceOrder serviceOrder) {
        for (ProblemType warning : serviceOrder.getValidationHolder().getWarnings()) {
            SONote soNote = new SONote();
            soNote.setCreatedByName(RoleType.System.toString());
            soNote.setNote(warning.getMsg());
            soNote.setSubject(warning.getCode());
            soNote.setRoleId(RoleType.Buyer.getId());
            soNote.setEntityId(serviceOrder.getBuyerResourceId());
            soNote.setNoteTypeId(NoteType.GENERAL.getId());
            serviceOrder.addNote(soNote);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //  SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////

    public void setOrderBuyer(OrderBuyer orderBuyer) {
        this.orderBuyer = orderBuyer;
        if (!orderBuyer.isInitialized()) orderBuyer.initialize();
    }

    public void setOrderEnhancementProcessors(List<IOrderEnhancementProcessor> orderEnhancementProcessors) {
        this.orderEnhancementProcessors = orderEnhancementProcessors;
    }

    public void setCommonServiceOrderValidator(IServiceOrderValidator commonServiceOrderValidator) {
        this.commonServiceOrderValidator = commonServiceOrderValidator;
    }

    public void setOrderEnhancementValidator(IServiceOrderValidator orderEnhancementValidator) {
        this.orderEnhancementValidator = orderEnhancementValidator;
    }
}
