package com.servicelive.esb.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.translator.util.DateUtil;
import com.newco.marketplace.webservices.dao.ShcMerchandise;
import com.newco.marketplace.webservices.dao.ShcOrder;
import com.newco.marketplace.webservices.dao.ShcOrderSku;
import com.newco.marketplace.webservices.dao.ShcUpsellPayment;
import com.newco.marketplace.webservices.dto.StagingDetails;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.constant.NPSConstants;
import com.servicelive.esb.dto.NPSCallCloseServiceOrder;
import com.servicelive.esb.dto.NPSCallCloseServiceOrders;
import com.servicelive.esb.dto.NPSDiscounts;
import com.servicelive.esb.dto.NPSJobCode;
import com.servicelive.esb.dto.NPSJobCodes;
import com.servicelive.esb.dto.NPSMerchandise;
import com.servicelive.esb.dto.NPSMerchandiseDisposition;
import com.servicelive.esb.dto.NPSMonetary;
import com.servicelive.esb.dto.NPSPayment;
import com.servicelive.esb.dto.NPSSalesCheck;
import com.servicelive.esb.vo.CryptographyVO;
import com.servicelive.util.Cryptography;

public class NPSCallCloseStagingMapper {

	private Logger logger = Logger.getLogger(NPSCallCloseStagingMapper.class);
	
	public NPSCallCloseServiceOrders mapCallCloseStagingData(
			StagingDetails stagingDetails) {
		NPSCallCloseServiceOrders npsCallCloseServiceOrders = new NPSCallCloseServiceOrders();
		ShcOrder[] shcOrderList = stagingDetails.getStageServiceOrder().toArray(new ShcOrder[0]);
		if (shcOrderList != null && shcOrderList.length > 0) {
			List<NPSCallCloseServiceOrder> npsDataList = new ArrayList<NPSCallCloseServiceOrder>();
			for (ShcOrder shcOrder : shcOrderList) {
				if (shcOrder != null) {
					NPSCallCloseServiceOrder npsCallCloseSO = convertShcOrderToNPSCallCloseServiceOrder(shcOrder);
					npsDataList.add(npsCallCloseSO);
				}
			}
			npsCallCloseServiceOrders.setNpsCallCloseServiceOrder(npsDataList);
		}
		return npsCallCloseServiceOrders;
	}

	/**
	 * Method which converts list of ShcOrder to NPSCallCloseServiceOrders
	 * 
	 * @param stagingDetails
	 * @return npsCallCloseServiceOrders
	 */
	public NPSCallCloseServiceOrder convertShcOrderToNPSCallCloseServiceOrder(
			ShcOrder shcOrder) {

		NPSCallCloseServiceOrder npsCallCloseSO = new NPSCallCloseServiceOrder();
		String unitNo = shcOrder.getUnitNo();
		String orderNo = shcOrder.getOrderNo();
		npsCallCloseSO.setUnitNo(unitNo);
		npsCallCloseSO.setOrderNo(orderNo);
		String salesCheckNum = shcOrder.getSalesCheckNum();
		String salesCheckDate = shcOrder.getSalesCheckDate();
		if (StringUtils.isNotBlank(salesCheckDate)) {
			salesCheckDate = salesCheckDate.substring(0,salesCheckDate.indexOf(" "));
		} else {
			logger.warn("OrderNo = ["+orderNo+"] UnitNo = ["+unitNo+"] The salesCheckDate is null! May cause issues during call close xml validation at NPS end.");
		}
		npsCallCloseSO.setSalesCheck(new NPSSalesCheck(salesCheckNum, salesCheckDate));
		String promisedDate = shcOrder.getPromisedDate();
		if (promisedDate != null) {
			int indexOfSpace = promisedDate.indexOf(" ");
			if (indexOfSpace != -1) {
				promisedDate = promisedDate.substring(0,indexOfSpace);
			}
			npsCallCloseSO.setRouteDate(promisedDate);
		}
		if (shcOrder.getCompletedDate() != null) {
			npsCallCloseSO.setInstalledDate(DateUtil.formatDate(shcOrder.getCompletedDate().getTime(), "yyyy-MM-dd"));
		}
		npsCallCloseSO.setTechID(NPSConstants.TECH_ID);
		npsCallCloseSO.setCallCode(NPSConstants.CALL_CODE);
		npsCallCloseSO.setStatusCode(NPSConstants.STATUS_CODE);
		npsCallCloseSO.setOrderType(NPSConstants.ORDER_TYPE);
		String techComments = shcOrder.getResolutionDescr();
		if (StringUtils.isNotBlank(techComments)) {
			techComments = chopXMLString(techComments, 120);
			npsCallCloseSO.setTechComments(techComments);
		} else {
			npsCallCloseSO.setTechComments("NA");
		}


		List<ShcUpsellPayment> shcUpsellPaymentList = new ArrayList<ShcUpsellPayment>(shcOrder.getShcUpsellPayments());
		NPSMonetary monetary = convertShcUpsellPaymentToNPSMonetary(shcUpsellPaymentList);
		
		npsCallCloseSO.setMonetary(monetary);

		npsCallCloseSO.setDiscounts(new NPSDiscounts());

		
		NPSPayment payment = convertShcUpsellPaymentToNPSPayment(shcUpsellPaymentList);
		npsCallCloseSO.setPayment(payment);

		
		List<ShcMerchandise> shcMerchandiseList = new ArrayList<ShcMerchandise>(shcOrder.getShcMerchandises());
		NPSMerchandise merchandise = convertShcMerchandiseToNPSMerchandise(shcMerchandiseList);
		npsCallCloseSO.setMerchandise(merchandise);

		List<ShcOrderSku> shcOrderSkuList = new ArrayList<ShcOrderSku>(shcOrder.getShcOrderSkus());
		NPSJobCodes jobCodes = convertShcOrderSkusToNPSJobCodes(shcOrderSkuList);
		npsCallCloseSO.setJobCodes(jobCodes);

		NPSMerchandiseDisposition merchandiseDisposition = new NPSMerchandiseDisposition();
		merchandiseDisposition
				.setFinalMerchandiseStatus(NPSConstants.FINAL_MERCHANDISE_STATUS);
		npsCallCloseSO.setMerchandiseDisposition(merchandiseDisposition);

		return npsCallCloseSO;
	}
	
	private String chopXMLString(String str, Integer length) {
		String choppedStr = str;
		String encodedStr = StringEscapeUtils.escapeXml(str);
		//encodedStr = StringUtils.replaceChars(encodedStr, "'", "&apos;");
		if (encodedStr.length() > length) {
			// Chop desired number of characters from starting
			String choppedEncodedStr = encodedStr.substring(0, length);
			
			// See if any encoded character got chopped off in the end of the string
			int lastAmpersandIndex = choppedEncodedStr.lastIndexOf("&");
			int semicolonAfterLastAmpersand = choppedEncodedStr.indexOf(";", lastAmpersandIndex);
			if (lastAmpersandIndex != -1 && semicolonAfterLastAmpersand == -1) { // Found '&' towards the end without matching ';'
				choppedEncodedStr = choppedEncodedStr.substring(0, lastAmpersandIndex);
			}
			
			// Decode back the data
 			logger.info("Before unescape: " + choppedEncodedStr);
			choppedStr = StringEscapeUtils.unescapeXml(choppedEncodedStr);
			logger.info("After unescape: " + choppedStr);
		}
		return choppedStr;
	}

	/**
	 * Method which converts ShcUpsellPayments to NPSMonetary
	 * 
	 * @param shcUpsellPayments
	 * @return npsMonetary
	 */
	private NPSMonetary convertShcUpsellPaymentToNPSMonetary(
			List<ShcUpsellPayment> shcUpsellPayments) {
		NPSMonetary npsMonetary = new NPSMonetary();
		if (null != shcUpsellPayments && !shcUpsellPayments.isEmpty()) {

			for (ShcUpsellPayment shcUpsellPayment : shcUpsellPayments) {
				if (null != shcUpsellPayment.getAmountCollected()) {
					double amountCollected = shcUpsellPayment.getAmountCollected().doubleValue();
					npsMonetary.setAmountCollected(new java.text.DecimalFormat("0.00").format(amountCollected));
					npsMonetary.setPrimaryAmountCollected(new java.text.DecimalFormat("0.00").format(amountCollected));
				}
				if (null != shcUpsellPayment.getPriAmountCollected()) {
					double priAmountCollected = shcUpsellPayment.getPriAmountCollected().doubleValue();
					npsMonetary.setPrimaryAmountCollected(new java.text.DecimalFormat("0.00").format(priAmountCollected));
				}
				if (null != shcUpsellPayment.getSecAmountCollected()) {
					double secAmountCollected = shcUpsellPayment.getSecAmountCollected().doubleValue();
					npsMonetary.setSecondaryAmountCollected(new java.text.DecimalFormat("0.00").format(secAmountCollected));
				}
				break;
			}
		}
		return npsMonetary;
	}

	/**
	 * Method which converts ShcUpsellPayments to NpsPayment
	 * 
	 * @param shcUpsellPayments
	 * @return npsPayment
	 */
	
	private NPSPayment convertShcUpsellPaymentToNPSPayment(
			List<ShcUpsellPayment> shcUpsellPayments) {
		NPSPayment npsPayment = new NPSPayment();
		Cryptography cryptography = new Cryptography();
		if (null != shcUpsellPayments && !shcUpsellPayments.isEmpty()) {
			for (ShcUpsellPayment shcUpsellPayment : shcUpsellPayments) {
				if (null != shcUpsellPayment.getAuthNo()) {
					npsPayment.setAuthNo(shcUpsellPayment.getAuthNo());
				}
				
				// if its not cash / cheque payment type, encrypt the card
				if (!org.apache.commons.lang.StringUtils
						.isBlank(shcUpsellPayment.getPaymentMethodInd())
						&& !shcUpsellPayment.getPaymentMethodInd().equals(
								NPSConstants.PAYMENT_CASH_CHEQUE_TYPE)) {

					CryptographyVO cryptographyVO = new CryptographyVO();
					cryptographyVO.setInput(shcUpsellPayment.getPaymentAccNo());
					cryptographyVO.setKAlias(NPSConstants.ENCRYPTION_KEY);

					cryptographyVO = cryptography.decryptKey(cryptographyVO);
					npsPayment.setPaymentAccNo(cryptographyVO.getResponse());

					String ccType =  shcUpsellPayment.getPaymentMethodInd();
					npsPayment.setPaymentMethodInd(ccType);
				} else if (!org.apache.commons.lang.StringUtils
						.isBlank(shcUpsellPayment.getPaymentMethodInd())
						&& shcUpsellPayment.getPaymentMethodInd().equals(
								NPSConstants.PAYMENT_CASH_CHEQUE_TYPE)) {
					npsPayment
							.setPaymentMethodInd(NPSConstants.PAYMENT_CASH_CHEQUE_TYPE_IND);
				}

				String callCloseExpDt = null;
				// pad exp date to 6 char
				String expDate = shcUpsellPayment.getPaymentExpDate();
				if (expDate != null && expDate.length() < 6) {
					expDate = "0" + expDate;
				}
				if (expDate != null ){
					callCloseExpDt = expDate.substring(0, 2)+ expDate.substring(4);
				}
									
				npsPayment.setPaymentExpDate(callCloseExpDt);
				// npsPayment.setPaymentExpDate(shcUpsellPayment.getPaymentExpDate());
				// npsPayment.setPaymentMethodInd(shcUpsellPayment.getPaymentMethodInd());
				if (null != shcUpsellPayment.getSecAuthNo()) {
					npsPayment.setSecAuthNo(shcUpsellPayment.getSecAuthNo()
							.toString());
				}
				npsPayment.setSecPaymentAccNo(shcUpsellPayment
						.getSecPaymentAccNo());
				npsPayment.setSecPaymentExpDate(shcUpsellPayment
						.getSecPaymentExpDate());
				npsPayment.setSecpaymentMethodInd(shcUpsellPayment
						.getSecPaymentMethodInd());
				break;
			}
		}
		return npsPayment;
	}

	/**
	 * Method which converts ShcMerchandises to NPSMerchandise
	 * 
	 * @param shcMerchandises
	 * @return npsMerchandise
	 */
	private NPSMerchandise convertShcMerchandiseToNPSMerchandise(
			List<ShcMerchandise> shcMerchandises) {
		NPSMerchandise npsMerchandise = new NPSMerchandise();
		if (null != shcMerchandises && !shcMerchandises.isEmpty()) {
			for (ShcMerchandise shcMerchandise : shcMerchandises) {
				npsMerchandise.setBrand(shcMerchandise.getBrand());
				npsMerchandise.setModel(shcMerchandise.getModel());
				npsMerchandise.setSlNo(shcMerchandise.getSerialNumber());
				break;
			}
		}
		return npsMerchandise;
	}

	/**
	 * Method which converts ShcOrderSkus to NPSJobCodes
	 * 
	 * @param shcOrderSkus
	 * @return npsJobCodes
	 */
	private NPSJobCodes convertShcOrderSkusToNPSJobCodes(
			List<ShcOrderSku> shcOrderSkus) {
		NPSJobCodes npsJobCodes = new NPSJobCodes();
		NPSJobCode npsJobCode = null;
		List<NPSJobCode> npsJobCodeList = new ArrayList<NPSJobCode>();
		SortedMap<Integer, ShcOrderSku> myMap = new TreeMap<Integer, ShcOrderSku>();
		for (ShcOrderSku shcOrderSku : shcOrderSkus) {
			myMap.put(shcOrderSku.getShcOrderSkuId(), shcOrderSku);
		}

		int seqNumber = 1;
		if (null != shcOrderSkus && !shcOrderSkus.isEmpty()) {
			for (ShcOrderSku shcOrderSku : myMap.values()) {
				int skuQty = shcOrderSku.getQty() == null ? 1 : shcOrderSku
						.getQty();
				for (int qtyCount = 0; qtyCount < skuQty; ++qtyCount) {
					npsJobCode = new NPSJobCode();
					npsJobCode.setSequenceNumber(new Integer(seqNumber)
							.toString());
					npsJobCode.setNumber(shcOrderSku.getSku());
					npsJobCode.setChargeCode(shcOrderSku.getChargeCode());
					npsJobCode.setType(shcOrderSku.getType());
				
					npsJobCode.setDescription(shcOrderSku.getDescription());
					npsJobCode.setCoverage(shcOrderSku.getCoverage());


					
					Double jobCodeAmount = shcOrderSku.getSellingPrice();

					if((shcOrderSku.getAddOnInd() == 1) || (shcOrderSku.getCoverage().equals("CC"))){
						npsJobCode.setCoverage("CC");
						jobCodeAmount = shcOrderSku.getRetailPrice(); 
					}
					else if (shcOrderSku.getPermitSkuInd() != null && shcOrderSku.getPermitSkuInd() == MarketESBConstant.PERMIT_SKU_IND_ONE)  {
						jobCodeAmount = shcOrderSku.getFinalPrice();
					}
					
					if (null != jobCodeAmount) {
						npsJobCode.setAmount(new java.text.DecimalFormat("0.00").format(jobCodeAmount));
					} else {
						npsJobCode.setAmount("0.00");
					}
					
					
					npsJobCode.setStatus(shcOrderSku.getStatus());
					npsJobCodeList.add(npsJobCode);
					++seqNumber;
				}
			}
		}
		npsJobCodes.setJobCodeList(npsJobCodeList);
		return npsJobCodes;
	}

}
