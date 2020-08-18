package com.servicelive.orderfulfillment.notification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.servicelive.domain.common.Contact;
import com.servicelive.marketplatform.provider.domain.SkillNode;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.jbpm.TransientVariable;
import com.servicelive.orderfulfillment.notification.enumerations.NotificationType;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

public class NotificationInfo {
    private ServiceOrder serviceOrder;
    private RoutedProvider acceptedProvider;
    private Identification identification;
    private Contact buyerContactInfo;
    private Contact acceptedProviderContactInfo;
    private NotificationType notificationType;
    private SkillNode mainSkillCategory;
    private String buyerDestAddr;
    private String soCreatorDestAddr;
    private List<String> routedProviderDestAddr = new ArrayList<String>();
    private String acceptedVendorDestAddr;
    private String buyerAdminDestAddr;
    private String providerAdminDestAddr;
    private String assurantFtpDest;
    private String assurantDestAddr;
    private String serviceLiveDestAddr;
    private String serviceLiveSupportAddr;
    private String orderId;
    private String externalOrderId;
    private BigDecimal laborSpendLimit;
    private BigDecimal partsSpendLimit;

    //Keeping this as default since most of the orders are from professional buyer
    private String consumer = "N";
    private String roleInd = "PB";
    private String priceModel;

    private Map<String,Object> processVariables;
    @SuppressWarnings("unchecked")
	private Map systemProperties;

    private Date currentDate;


    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public Contact getBuyerContactInfo() {
        return buyerContactInfo;
    }

    public RoutedProvider getAcceptedProvider() {
        return acceptedProvider;
    }

    public void setAcceptedProvider(RoutedProvider acceptedProvider) {
        this.acceptedProvider = acceptedProvider;
    }

    public void setBuyerContactInfo(Contact buyerContactInfo) {
        this.buyerContactInfo = buyerContactInfo;
    }

    public Contact getAcceptedProviderContactInfo() {
        return acceptedProviderContactInfo;
    }

    public void setAcceptedProviderContactInfo(Contact acceptedProviderContactInfo) {
        this.acceptedProviderContactInfo = acceptedProviderContactInfo;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public SkillNode getMainSkillCategory() {
        return mainSkillCategory;
    }

    public void setMainSkillCategory(SkillNode mainSkillCategory) {
        this.mainSkillCategory = mainSkillCategory;
    }

    public String getBuyerDestAddr() {
        return buyerDestAddr;
    }

    public String getSoCreatorDestAddr() {
        return soCreatorDestAddr;
    }

    public void setSoCreatorDestAddr(String soCreatorDestAddr) {
        this.soCreatorDestAddr = soCreatorDestAddr;
    }

    public void setBuyerDestAddr(String buyerDestAddr) {
        this.buyerDestAddr = buyerDestAddr;
    }

    public List<String> getRoutedProviderDestAddr() {
        return routedProviderDestAddr;
    }

    public void setRoutedProviderDestAddr(List<String> routedProviderDestAddr) {
        this.routedProviderDestAddr = routedProviderDestAddr;
    }

    public String getAcceptedVendorDestAddr() {
        return acceptedVendorDestAddr;
    }

    public void setAcceptedVendorDestAddr(String acceptedVendorDestAddr) {
        this.acceptedVendorDestAddr = acceptedVendorDestAddr;
    }

    public String getBuyerAdminDestAddr() {
        return buyerAdminDestAddr;
    }

    public void setBuyerAdminDestAddr(String buyerAdminDestAddr) {
        this.buyerAdminDestAddr = buyerAdminDestAddr;
    }

    public String getProviderAdminDestAddr() {
        return providerAdminDestAddr;
    }

    public void setProviderAdminDestAddr(String providerAdminDestAddr) {
        this.providerAdminDestAddr = providerAdminDestAddr;
    }

    public String getAssurantFtpDest() {
        return assurantFtpDest;
    }

    public void setAssurantFtpDest(String assurantFtpDest) {
        this.assurantFtpDest = assurantFtpDest;
    }

    public String getAssurantDestAddr() {
        return assurantDestAddr;
    }

    public void setAssurantDestAddr(String assurantDestAddr) {
        this.assurantDestAddr = assurantDestAddr;
    }

    public String getServiceLiveDestAddr() {
        return serviceLiveDestAddr;
    }

    public void setServiceLiveDestAddr(String serviceLiveDestAddr) {
        this.serviceLiveDestAddr = serviceLiveDestAddr;
    }

    public String getServiceLiveSupportAddr() {
        return serviceLiveSupportAddr;
    }

    public void setServiceLiveSupportAddr(String serviceLiveSupportAddr) {
        this.serviceLiveSupportAddr = serviceLiveSupportAddr;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Map<String, Object> getProcessVariables() {
        return processVariables;
    }

    public void setProcessVariables(Map<String, Object> processVariables) {
        this.processVariables = processVariables;
        if(null != processVariables.get(ProcessVariableUtil.PVKEY_SVC_ORDER_TYPE)
            && processVariables.get(ProcessVariableUtil.PVKEY_SVC_ORDER_TYPE).toString().toUpperCase().equals("CB")){
            this.consumer = "Y";
            this.roleInd = "CB";
        }
        if(null != processVariables.get(ProcessVariableUtil.PVKEY_IDENTIFICATION)){
            this.identification = (Identification) ((TransientVariable)processVariables.get(ProcessVariableUtil.PVKEY_IDENTIFICATION)).getObject(); 
        }
    }

    
	public @SuppressWarnings("unchecked") Map getSystemProperties() {
        return systemProperties;
    }

    
	public void setSystemProperties(@SuppressWarnings("unchecked") Map systemProperties) {
        this.systemProperties = systemProperties;
    }

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setLaborSpendLimit(BigDecimal laborSpendLimit) {
		this.laborSpendLimit = roundBigDecimal(laborSpendLimit);
	}

	public BigDecimal getLaborSpendLimit() {
		return laborSpendLimit;
	}

	public void setPartsSpendLimit(BigDecimal partsSpendLimit) {
		this.partsSpendLimit = roundBigDecimal(partsSpendLimit);
	}

	public BigDecimal getPartsSpendLimit() {
		return partsSpendLimit;
	}

    public String getExternalOrderId() {
        return externalOrderId;
    }

    public void setExternalOrderId(String externalOrderId) {
        this.externalOrderId = externalOrderId;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getRoleInd() {
        return roleInd;
    }

    public void setRoleInd(String roleInd) {
        this.roleInd = roleInd;
    }

    public String getPriceModel() {
        return priceModel;
    }

    public void setPriceModel(String priceModel) {
        this.priceModel = priceModel;
    }

    public Identification getIdentification() {
        return identification;
    }

    public void setIdentification(Identification identification) {
        this.identification = identification;
    }

    public BigDecimal getTotalSpendLimit(){
		if (laborSpendLimit != null && partsSpendLimit != null){
			return laborSpendLimit.add(partsSpendLimit);
		}else if (laborSpendLimit != null){
			return laborSpendLimit;
		}else if (partsSpendLimit != null){
			return partsSpendLimit;
		}else {
			return PricingUtil.ZERO;
		}
	}
    
    public BigDecimal roundBigDecimal(BigDecimal amt) {
    	NumberFormat formatter = new DecimalFormat("#0.00");    	
    	return new BigDecimal(formatter.format(amt));
    	
    }
}
