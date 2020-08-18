package com.newco.marketplace.api.mobile.services;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.UpdateSOCompletionValidator;
import com.servicelive.orderfulfillment.domain.SOAdditionalPayment;
import com.servicelive.orderfulfillment.domain.SOAddon;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class UpdateSOCompletionValidatorTest {

	@Ignore
	@Test
	public void validateAdditionalPaymentTest() {
		ServiceOrder serviceOrder=new ServiceOrder();
		BigDecimal amountAuthorized = BigDecimal.valueOf(200);
		SOAdditionalPayment paymentDetails=new SOAdditionalPayment();
		paymentDetails.setPaymentAmount(amountAuthorized);
		serviceOrder.setAdditionalPayment(paymentDetails);
		List<SOAddon> addons =new ArrayList<SOAddon>();
		BigDecimal retailPrice = BigDecimal.valueOf(150);
		SOAddon addOn =new SOAddon();
		addOn.setAddonId(32676842);
		addOn.setQuantity(2);
		addOn.setRetailPrice(retailPrice);
		addons.add(addOn);
		serviceOrder.setAddons(addons);
		List<ErrorResult> validationErrors = new ArrayList<ErrorResult>();

		UpdateSOCompletionValidator updateSOCompletionValidator = new UpdateSOCompletionValidator();
		Class<? extends UpdateSOCompletionValidator> clazz=updateSOCompletionValidator.getClass();


		try {
			Method validateAdditionalPaymentAmt = clazz.getDeclaredMethod("validateAdditionalPaymentAmt", ServiceOrder.class,boolean.class,List.class );
			validateAdditionalPaymentAmt.setAccessible(true);
			Boolean isErrorOccured = (Boolean) validateAdditionalPaymentAmt.invoke(updateSOCompletionValidator, serviceOrder,false,validationErrors);
			
			Assert.assertTrue(isErrorOccured);

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}



