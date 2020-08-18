package com.servicelive.esb.integration.domain;

import org.apache.commons.lang.StringUtils;

import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ServiceOrder {
	private static final Log logger = LogFactory.getLog(ServiceOrder.class);
	
	private long serviceOrderId;
	private Blob customRefs;
	private Long transactionId;
	private Double laborSpendLimit;
	private Double partsSpendLimit;
	private String title;
	private String description;
	private String providerInstructions;
	private Date startDate;
	private String startTime;
	private Date endDate;
	private String endTime;
	private Long templateId;
	private Boolean providerServiceConfirmInd;
	private String partsSuppliedBy;
	private Boolean serviceWindowTypeFixed;
	private String mainServiceCategory;
	private String buyerTermsAndConditions;
	
	public ServiceOrder() {}
	
	public ServiceOrder(long serviceOrderId, Long transactionId, Blob customRefs) {
		this.serviceOrderId = serviceOrderId;
		this.transactionId = transactionId;
		this.customRefs = customRefs;
	}
	public ServiceOrder(long serviceOrderId, Long transactionId, Blob customRefs, String startWindowDate) {
		this.serviceOrderId = serviceOrderId;
		this.transactionId = transactionId;
		this.customRefs = customRefs;
		logger.info("serviceOrderId: "+serviceOrderId);
		logger.info("transactionId: "+transactionId);
		if(!(StringUtils.isBlank(startWindowDate))){
			logger.info("startWindowDate: "+startWindowDate);			
			try{
				SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
				//Date serviceOrderDate = new Date(startWindowDate);
				Date serviceOrderDate= (Date)formatter.parse(startWindowDate);
				logger.info("serviceOrderDate: "+serviceOrderDate);	
				//String date = formatter.format(serviceOrderDate);
				this.startDate = serviceOrderDate;
			}catch(ParseException e){
				logger.info("ParseException in ServiceOrder: "+e);
			}catch(Exception ex){
				logger.info("ParseException in ServiceOrder:  "+ex);
			}			
		}		
	}
	public ServiceOrder(long serviceOrderId, Blob customRefs,
			Long transactionId, Double laborSpendLimit, Double partsSpendLimit,
			String title, String description, String providerInstructions,
			Date startDate, String startTime, Date endDate, String endTime,
			Long templateId, Boolean providerServiceConfirmInd,
			String partsSuppliedBy, Boolean serviceWindowTypeFixed,
			String mainServiceCategory, String buyerTermsAndConditions) {
		super();
		this.serviceOrderId = serviceOrderId;
		this.customRefs = customRefs;
		this.transactionId = transactionId;
		this.laborSpendLimit = laborSpendLimit;
		this.partsSpendLimit = partsSpendLimit;
		this.title = title;
		this.description = description;
		this.providerInstructions = providerInstructions;
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
		this.templateId = templateId;
		this.providerServiceConfirmInd = providerServiceConfirmInd;
		this.partsSuppliedBy = partsSuppliedBy;
		this.serviceWindowTypeFixed = serviceWindowTypeFixed;
		this.mainServiceCategory = mainServiceCategory;
		this.buyerTermsAndConditions = buyerTermsAndConditions;
	}

	public String getBuyerTermsAndConditions() {
		return buyerTermsAndConditions;
	}

	public Blob getCustomRefs() {
		return customRefs;
	}

	public String getDescription() {
		return this.description;
	}

	public Date getEndDate() {
		return endDate;
	}
	
	public String getEndTime() {
		return endTime;
	}

	public Double getLaborSpendLimit() {
		return this.laborSpendLimit;
	}
	public String getMainServiceCategory() {
		return mainServiceCategory;
	}

	public Double getPartsSpendLimit() {
		return this.partsSpendLimit;
	}
	public String getPartsSuppliedBy() {
		return partsSuppliedBy;
	}

	public String getProviderInstructions() {
		return this.providerInstructions;
	}
	public Boolean getProviderServiceConfirmInd() {
		return providerServiceConfirmInd;
	}

	public long getServiceOrderId() {
		return serviceOrderId;
	}
	public Boolean getServiceWindowTypeFixed() {
		return serviceWindowTypeFixed;
	}

	public Date getStartDate() {
		return startDate;
	}
	public String getStartTime() {
		return startTime;
	}

	public Long getTemplateId() {
		return templateId;
	}
	public String getTitle() {
		return this.title;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setBuyerTermsAndConditions(String buyerTermsAndConditions) {
		this.buyerTermsAndConditions = buyerTermsAndConditions;
	}

	public void setCustomRefs(Blob customRefs) {
		this.customRefs = customRefs;
	}

	public void setDescription(String description) {
		this.description = description;		
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setLaborSpendLimit(Double laborSpendLimit) {
		this.laborSpendLimit = laborSpendLimit;		
	}

	public void setMainServiceCategory(String mainServiceCategory) {
		this.mainServiceCategory = mainServiceCategory;
	}

	public void setPartsSpendLimit(Double partsSpendLimit) {
		this.partsSpendLimit = partsSpendLimit;		
	}

	public void setPartsSuppliedBy(String partsSuppliedBy) {
		this.partsSuppliedBy = partsSuppliedBy;
	}

	public void setProviderInstructions(String providerInstructions) {
		this.providerInstructions = providerInstructions;		
	}

	public void setProviderServiceConfirmInd(Boolean providerServiceConfirmInd) {
		this.providerServiceConfirmInd = providerServiceConfirmInd;
	}

	public void setServiceOrderId(long serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}

	public void setServiceWindowTypeFixed(Boolean serviceWindowTypeFixed) {
		this.serviceWindowTypeFixed = serviceWindowTypeFixed;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public void setTitle(String title) {
		this.title = title;		
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;		
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
