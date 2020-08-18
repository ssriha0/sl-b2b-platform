package com.newco.marketplace.test;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.LuBuyerRefVO;
import com.newco.marketplace.dto.vo.LuProviderRespReasonVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.delegatesImpl.LookupDelegateImpl;

/**
 * @author Manish Patel
 * Desc:	To test Lookup class
 */

public class LookupDelegateTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		LookupDelegateImpl luDelegate = (LookupDelegateImpl)MPSpringLoaderPlugIn.getCtx().getBean("lookupManager");
		
		/* put comment if you do not want to test any method */
  
		reasonRespReasonTest(luDelegate);  // reason code list test
		shippingCarrierTest(luDelegate);  // shipping carrier list test
		stateCodesTest(luDelegate);  // states code list test
		phoneTypesTest(luDelegate); // phone types
		buyerReferenceLookup(luDelegate);
	}
	

	private static void reasonRespReasonTest(LookupDelegateImpl luDelegate){
		
		ArrayList<LuProviderRespReasonVO> reasonCodeList = null;
		LuProviderRespReasonVO	luReasonVO = new LuProviderRespReasonVO();
		luReasonVO.setSearchByResponse(3);
		
		try {
			reasonCodeList = luDelegate.getLuProviderRespReason(luReasonVO);
		} catch (BusinessServiceException e) {
			System.out.println("BusinessServiceException thrown : " + e.getMessage());
			e.printStackTrace();
		}
		if (reasonCodeList != null) {
			System.out.println("Total records return for reason Codes are : " + reasonCodeList.size());
			for (int i=0; i<reasonCodeList.size(); i++)
			{
				System.out.println("***** " + reasonCodeList.get(i).getRespReasonId()+" "+ reasonCodeList.get(i).getDescr() + " ***** ");
			}
		}
		else
		{
			System.out.println("***** Reason Code List is empty, meaning not bringing any records from table lu_so_provider_resp_reason ");
			System.out.println("***** check query of LookupMap.xml >>> lookup.reason for passed providerRespId, if not returning records than debug aplication. ");
		}
		
	}
	
	private static void shippingCarrierTest(LookupDelegateImpl luDelegate){	
		ArrayList<LookupVO> sc = null;
		try {
			sc = luDelegate.getShippingCarrier();
		} catch (BusinessServiceException e) {
			System.out.println("BusinessServiceException thrown : " + e.getMessage());
			e.printStackTrace();
		}
		if (sc != null) {
			System.out.println("Total records return for shipping carrier are : " + sc.size());
			for (int i=0; i<sc.size(); i++)
			{
				System.out.println("***** " + sc.get(i).getId()+" "+ sc.get(i).getDescr() + " ***** ");
			}
		}
		else
		{
			System.out.println("***** Shipping Carrier List is empty, meaning not bringing any records from table lu_shipping_carrier  ");
			System.out.println("***** check query of LookupMap.xml >>> lookup.shippingCarrier , if not returning records than debug aplication. ");
		}
	}

	private static void stateCodesTest(LookupDelegateImpl luDelegate){	
		List<LookupVO> states = luDelegate.getStateCodes();

		if (states != null) {
			System.out.println("Total records return for sates codes are : " + states.size());
			for (int i=0; i<states.size(); i++)
			{
				System.out.println("***** " + states.get(i).getType()+" "+ states.get(i).getDescr() + " ***** ");
			}
		}
		else
		{
			System.out.println("***** States Code List is empty, meaning not bringing any records from table lu_state_cds  ");
			System.out.println("***** check query of LookupMap.xml >>> lookup.statecodes , if not returning records than debug aplication. ");
		}
	}
	
	private static void phoneTypesTest(LookupDelegateImpl luDelegate){	
		ArrayList<LookupVO> pt = null;
		try {
			pt = luDelegate.getPhoneTypes();
		} catch (BusinessServiceException e) {
			System.out.println("BusinessServiceException thrown : " + e.getMessage());
			e.printStackTrace();
		}
		if (pt != null) {
			System.out.println("Total records return for phone types are : " + pt.size());
			for (int i=0; i<pt.size(); i++)
			{
				System.out.println("***** " + pt.get(i).getId()+" "+ pt.get(i).getDescr() + " ***** ");
			}
		}
		else
		{
			System.out.println("***** Phone Type List is empty, meaning not bringing any records from table lu_so_phone_class  ");
			System.out.println("***** check query of LookupMap.xml >>> lookup.phonetypes , if not returning records than debug aplication. ");
		}
	}	

	private static void buyerReferenceLookup(LookupDelegateImpl luDelegate){	
		ArrayList<LuBuyerRefVO> x = new ArrayList<LuBuyerRefVO>();
		try{
			x=  luDelegate.getBuyerRef("110");
		for(int i = 0;i<x.size(); i++)
		{
			System.out.print("===========>"+x.get(i).getRefType());
		}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}	
}
