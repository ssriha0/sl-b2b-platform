/**
 * 
 */
package com.newco.marketplace.test;

import org.springframework.context.ApplicationContext;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.ledger.ICreditCardBO;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.interfaces.LedgerConstants;

/**
 * @author schavda
 *
 */
public class CreditCardJUnitTest {
	
	private static CreditCardVO creditCardVO = new CreditCardVO();
	private static ICreditCardBO creditCardBO = null;
		


	protected static void setUp() throws Exception {
		creditCardVO.setCardTypeId(new Long(7));
		creditCardVO.setCardHolderName("Pooja");
		creditCardVO.setCardNo("5424180279791732");
		creditCardVO.setExpireDate("0808");
		creditCardVO.setCardVerificationNo("111");
		
		creditCardVO.setEntityTypeId(LedgerConstants.LEDGER_ENTITY_TYPE_BUYER); //Type=Buyer
		creditCardVO.setEntityId(new Integer(2)); //BuyerId
		creditCardVO.setCountryId(new Integer(1)); 
		creditCardVO.setAccountTypeId(LedgerConstants.CC_ACCOUNT_TYPE); //Credit Card
		creditCardVO.setAccountStatusId(new Integer(1));
		
		creditCardVO.setModifiedBy("schavda");
		
		//Check CardId = account_id from database
		creditCardVO.setCardId(new Long(12345678));
		creditCardVO.setLocationTypeId(new Integer(5));
		
		creditCardVO.setTransactionAmount(500.00);
		
	}

	public void test1SaveCardDetails(){
		try{
			creditCardBO.saveCardDetails(creditCardVO);
			
		}catch(Exception e){
			System.out.println("CreditCardJUnitTest-->test1SaveCardDetails()-->EXCEPTION-->");
			e.printStackTrace();
		}
	}
	
	public void test2GetCardDetails(){
		try{
			creditCardVO = creditCardBO.getCardDetails(creditCardVO);
			
			System.out.println("CreditCardTest-->ENC CreditCardNo-->"+creditCardVO.getEncCardNo());
			System.out.println("CreditCardTest-->CreditCardNo-->"+creditCardVO.getCardNo());
			
		}catch(Exception e){
			System.out.println("CreditCardJUnitTest-->test2GetCardDetails()-->EXCEPTION-->");
			e.printStackTrace();
		}
	}
	

	public static void main(String args[])
	{
		ApplicationContext ctx = MPSpringLoaderPlugIn.getCtx(); 
		ICreditCardBO creditCardBO = (ICreditCardBO)ctx.getBean("creditCardBO");
		try {
			setUp();
//			creditCardBO.authorizeCardTransaction(creditCardVO);
		} catch (Exception e) {
			
		}
	}
}
