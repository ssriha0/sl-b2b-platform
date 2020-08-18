package com.newco.marketplace.dto.vo.fee;

import java.util.List;

import com.newco.marketplace.dto.vo.ledger.MarketPlaceTransactionVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;

public interface FeeConstants {

	// Fee Constants
	public final static long CANCEL_TIME_LIMIT = 86400000;

	public final static int FEE_TYPE = 1;

	public final static int PENALITY_TYPE = 2;

	public final static String FEE_TYPE_NAME = "Fee";

	public final static String PENALITY_TYPE_NAME = "Penality";

	public final static int PENALITY_TYPE_FIXED = 1;

	public final static int PENALITY_TYPE_PERCENTAGE = 2;

	public final static int AFTER_APPOINTMENT_START_TIME_HRS = 0;
	
	public final static double SERVICE_FEE_PERCENTAGE = 0.1;
	
	public final static String POSTING_FEE = "POSTING_FEE";
	public final static String LABOUR_SPEND_LIMIT = "LABOUR_SPEND_LIMIT";
	public final static String PARTS_SPEND_LIMIT = "PARTS_SPEND_LIMIT";
	public final static String FINAL_LABOUR = "FINAL_LABOUR";
	public final static String FINAL_PARTS = "FINAL_PARTS";
	public final static String FINAL_SERVICE_FEE = "FINAL_SERVICE_FEE";
	public final static String UPSELL_PROVIDER_TOTAL = "UPSELL_PROVIDER_TOTAL";
	public final static String UPSELL_SERVICE_FEE = "UPSELL_SERVICE_FEE";
	public final static String CANCELLATION_FEE = "CANCELLATION_FEE";
	public final static String RETAIL_CANCELLATION_FEE = "RETAIL_CANCELLATION_FEE";
	public final static String RETAIL_SO_PRICE = "RETAIL_SO_PRICE";
	public final static String INITIAL_SPEND_LIMIT = "INITIAL_SPEND_LIMIT";
	public final static String ZERO = "ZERO";
	public final static String ACH_AMOUNT = "ACH_AMOUNT";
	public double calcOrderFinalAmt(MarketPlaceTransactionVO marketVO);
	public Double calculateAddonTot(List<ServiceOrderAddonVO> addons);
	public double calcOrderLimitAmt(MarketPlaceTransactionVO marketVO);
	public Double calculateAddonSvcFee(List<ServiceOrderAddonVO> addons, double serviceFeePercentage);


}
