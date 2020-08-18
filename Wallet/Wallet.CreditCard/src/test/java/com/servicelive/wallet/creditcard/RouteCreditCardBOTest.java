package com.servicelive.wallet.creditcard;

import org.junit.Assert;
import org.junit.Test;

import com.servicelive.common.vo.SLCreditCardVO;

public class RouteCreditCardBOTest{

private RouteCreditCardBO bo;

@Test
public void testAuthorizeCardTransaction(){
	bo = new RouteCreditCardBO();
	SLCreditCardVO vo = new SLCreditCardVO();
	vo.setCardTypeId(1L);
	boolean isSearsCard = false;
	isSearsCard = bo.isSearsCard(vo);
	
	Assert.assertTrue(isSearsCard);
	
}
	
}
