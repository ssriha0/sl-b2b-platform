/**
 * 
 */
package com.newco.marketplace.test;

import java.util.ArrayList;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.ledger.ICreditCardBO;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.interfaces.LedgerConstants;

/**
 * @author schavda
 *
 */
public class CreditCardTest implements LedgerConstants {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("CreditCardTest-->ARGUMENT-->"+args[0]);
		String choice = args[0];
		CreditCardVO creditCardAuth = null;
		try{
			ICreditCardBO creditCardBO = (ICreditCardBO)MPSpringLoaderPlugIn.getCtx().getBean("creditCardBO");

			//Store Credit Card Information
			if("1".equals(choice)){
				CreditCardVO creditCardVO = new CreditCardVO();
				creditCardVO.setCardTypeId(new Long(7));
				creditCardVO.setCardHolderName("ABC XYZ");
				creditCardVO.setCardNo("5424180279791732");
				creditCardVO.setExpireDate("0808");
				creditCardVO.setCardVerificationNo("130");
				
				creditCardVO.setEntityTypeId(LEDGER_ENTITY_TYPE_BUYER); //Type=Buyer
				creditCardVO.setEntityId(new Integer(2)); //BuyerId
				creditCardVO.setCountryId(new Integer(1)); 
				creditCardVO.setAccountTypeId(CC_ACCOUNT_TYPE); //Credit Card
				creditCardVO.setAccountStatusId(new Integer(1));
				
				creditCardVO.setModifiedBy("schavda");
				
				creditCardBO.saveCardDetails(creditCardVO);
				System.out.println("CreditCardTest-->SAVE SUCCESSFUL");
			}
			else if("2".equals(choice)){
				CreditCardVO creditCardVO = new CreditCardVO();
				creditCardVO.setCardId(new Long(239801214));
				creditCardVO.setLocationTypeId(new Integer(5));
				creditCardVO = creditCardBO.getCardDetails(creditCardVO);
				System.out.println("CreditCardTest-->ENC CreditCardNo-->"+creditCardVO.getEncCardNo());
				System.out.println("CreditCardTest-->CreditCardNo-->"+creditCardVO.getCardNo());
			}
			else if("3".equals(choice)){
				CreditCardVO creditCardVO = new CreditCardVO();
				creditCardVO.setCardId(new Long(858425065));
				creditCardVO.setLocationTypeId(new Integer(5));
				creditCardVO.setTransactionAmount(22.33);
				creditCardVO.setCardVerificationNo("123");
				
//				creditCardAuth = creditCardBO.authorizeCardTransaction(creditCardVO);
				System.out.println("CreditCardTest-->AuthCode-->"+creditCardVO.getAuthorizationCode());
			}
			else if("4".equals(choice)){
				CreditCardVO creditCardVO = new CreditCardVO();
				creditCardVO.setEntityId(new Integer(2));
				
				ArrayList<CreditCardVO> alCards = creditCardBO.getCardsForEntity(creditCardVO);
				System.out.println("CreditCardTest-->SIZE-->"+alCards.size());
			}			
		}
		catch(Exception e){
			System.out.println("CreditCardTest-->EXCEPTION-->"+e.getMessage());
			e.printStackTrace();
		}

	}

}
