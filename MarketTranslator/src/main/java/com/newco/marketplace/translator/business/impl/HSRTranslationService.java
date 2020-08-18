/*
 * @(#)HSRTranslationService.java
 *
 * Copyright 2010 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.newco.marketplace.translator.business.impl;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.translator.dao.BuyerSkuTask;
import com.newco.marketplace.translator.dao.BuyerSkuTaskDAO;
import com.newco.marketplace.translator.dto.BuyerCredentials;
import com.newco.marketplace.translator.util.Constants;
import com.newco.marketplace.translator.util.DateUtil;
import com.newco.marketplace.webservices.dto.serviceorder.NoteRequest;

/**
 * @author sldev
 *
 */
public class HSRTranslationService extends ProductTranslationService {
	
	private static final Logger logger = Logger.getLogger(HSRTranslationService.class);
	private static final int DEFAULT_START_TIME = 8;
	private static final int DEFAULT_END_TIME = 17;
	private static final int DEFAULT_DAY_PAD = -1;
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.business.impl.ProductTranslationService#determineBuyer()
	 */
	@Override
	public void determineBuyer() throws Exception{
		Integer buyerId = applicationPropertiesService.getBuyerId(Constants.ApplicationPropertiesConstants.HSR_BUYER_ID);
		if (buyerId == null) {
			throw new Exception("Translation of order "+ request.getOrderNumber() + "-" + request.getUnitNumber() +
					" failed because " + Constants.ApplicationPropertiesConstants.HSR_BUYER_ID + " is not configured.");
		}
		BuyerCredentials buyerCred = buyerService.getBuyerCredentials(buyerId);
		request.setBuyerId(buyerId);
		request.setUserId(buyerCred.getUsername());
		request.setPassword(buyerCred.getPassword());
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.business.impl.ProductTranslationService#determineTemplate()
	 */
	@Override
	public void determineTemplate() throws Exception{
		if (mainSku != null && mainSku.getBuyerSoTemplate() != null && StringUtils.isNotEmpty(mainSku.getBuyerSoTemplate().getTemplateName())) {
			request.setTemplateName(mainSku.getBuyerSoTemplate().getTemplateName());
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.business.impl.ProductTranslationService#determineFinalSchedule()
	 */
	@Override
	public void determineFinalSchedule() {
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		SimpleDateFormat fmtTime = new SimpleDateFormat("hh:mm a");
		SimpleDateFormat fmtTimeMilitary = new SimpleDateFormat("HH:mm:ss");
		NumberFormat nfmt = NumberFormat.getInstance();
		nfmt.setMinimumIntegerDigits(2);
		
		int  offset = getTimeZoneOffset();
				
		int startHour = Math.abs(offset - DEFAULT_START_TIME);
		String startTime = nfmt.format(startHour ) + ":00:00"; 
		int endHour = Math.abs(offset - DEFAULT_END_TIME);
		String endTime = nfmt.format(endHour) + ":00:00"; 
		
		Date startServiceDate = DateUtil.getDateMidnight();
		startServiceDate = DateUtil.addDaysToDate(startServiceDate, new Integer(DEFAULT_DAY_PAD));		
		request.setAppointmentStartDate(fmt.format(startServiceDate));
		request.setAppointmentEndDate(fmt.format(DateUtil.getDateMidnight()));
		 
		try {
			Date tstartTime = fmtTimeMilitary.parse(startTime);
			request.setAppointmentStartTime(fmtTime.format(tstartTime));
			Date tendTime = fmtTimeMilitary.parse(endTime);
			request.setAppointmentEndTime(fmtTime.format(tendTime));
		} catch (ParseException e) {
			logger.error("Error parsing date", e);
		}
		request.setServiceDateTypeId(Integer.valueOf(Constants.RANGE_DATE));		
	}
	

	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.business.impl.ProductTranslationService#determineMainSkuAndTask()
	 */
	@Override
	public void determineMainSkuAndTask() throws Exception{
		String sku = request.getTasks().get(0).getJobCode();
		mainSku = buyerSkuDao.findBySkuAndBuyerID(sku, request.getBuyerId());
		if (mainSku == null) {
			logger.error("Error getting main Sku: primary sku " + sku + " is not configured.");
			NoteRequest note = new NoteRequest();
			note.setSubject("Error getting Main Sku");
			note.setNote("Error getting Main Sku: primary sku " + sku + " is not configured.");
			notes.add(note);
			request.setAutoRoute(new Boolean(false));
		}else{
			List<BuyerSkuTask> skuTasks = buyerSkuTaskDao.findByProperty(BuyerSkuTaskDAO.BUYER_SKU, mainSku);
			if (skuTasks.size() > 0) {
				mainSkuTask = skuTasks.get(0);
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.business.impl.ProductTranslationService#determineTitleAndOverview()
	 */
	@Override
	public void determineTitleAndOverview() {
		if (mainSku != null && mainSkuTask != null) {
			request.setSowTitle(" " + mainSku.getSku() + " - " + mainSkuTask.getTaskName());
			StringBuffer sb = new StringBuffer();
			sb.append(mainSkuTask.getTaskComments());
			if (StringUtils.isNotBlank(request.getBuyerSpecificFields().get(Constants.HSR_ORDER_TAKEN_TIME))) {
				sb.append("\n" + Constants.HSR_ORDER_TAKEN_TIME + request.getBuyerSpecificFields().get(Constants.HSR_ORDER_TAKEN_TIME));
			}
			if (StringUtils.isNotBlank(request.getBuyerSpecificFields().get(Constants.HSR_ORDER_TAKEN_DATE))) {
				sb.append("\n" + Constants.HSR_ORDER_TAKEN_DATE + request.getBuyerSpecificFields().get(Constants.HSR_ORDER_TAKEN_DATE));
			}
			if (StringUtils.isNotBlank(request.getBuyerSpecificFields().get(Constants.HSR_SERVICE_REQUESTED))) {
				sb.append("\n" + Constants.HSR_SERVICE_REQUESTED + request.getBuyerSpecificFields().get(Constants.HSR_SERVICE_REQUESTED));
			}
			if (StringUtils.isNotBlank(request.getBuyerSpecificFields().get(Constants.HSR_PROT_AGR_TYPE))) {
				sb.append("\n" + Constants.HSR_PROT_AGR_TYPE + request.getBuyerSpecificFields().get(Constants.HSR_PROT_AGR_TYPE));
			}
			if (StringUtils.isNotBlank(request.getBuyerSpecificFields().get(Constants.HSR_PROT_AGR_PLAN_TYPE))) {
				sb.append("\n" + Constants.HSR_PROT_AGR_PLAN_TYPE + request.getBuyerSpecificFields().get(Constants.HSR_PROT_AGR_PLAN_TYPE));
			}
			if (StringUtils.isNotBlank(request.getBuyerSpecificFields().get(Constants.HSR_PROT_AGR_EXP_DATE))) {
				sb.append("\n" + Constants.HSR_PROT_AGR_EXP_DATE + request.getBuyerSpecificFields().get(Constants.HSR_PROT_AGR_EXP_DATE));
			}
			if (StringUtils.isNotBlank(request.getBuyerSpecificFields().get(Constants.HSR_ORG_DELIVERY_DATE))) {
				sb.append("\n" + Constants.HSR_ORG_DELIVERY_DATE + request.getBuyerSpecificFields().get(Constants.HSR_ORG_DELIVERY_DATE));
			}			
			request.setSowDs(sb.toString());
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.business.impl.ProductTranslationService#determineMisc()
	 */
	@Override
	public void determineMisc() {
		mapCustomRefs();
		
	}
}
