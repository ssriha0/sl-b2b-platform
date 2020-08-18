
package org.hdiv.filter;

import org.junit.Assert;
import org.junit.Test;

public class SLAbstractValidatorHelperTest{
	private SLAbstractValidatorHelper req;
	@Test
	public void testValidateHDIVSuffix(){
		req = new SLValidatorHelperRequest();
		String value = "profileTabAction_execute.action-24124354654";
		boolean isValid;
		isValid = req.validateHDIVSuffix(value);
		Assert.assertFalse(isValid);
		}
}