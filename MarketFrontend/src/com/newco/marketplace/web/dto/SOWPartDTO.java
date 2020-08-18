package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;


import com.newco.marketplace.interfaces.OrderConstants;

public class SOWPartDTO extends SOWBaseTabDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2703762358837113602L;
	private String manufacturer;
	private String modelNumber;
	private String serialNumber;
	private String manufacturerPartNumber;
	private String partDesc;
	private String vendorPartNumber;
	private String quantity;
	private String additionalPartInfo;
	private String orderNumber;
	private String purchaseOrderNumber;
	private Integer partStatusId;
	private String standard = OrderConstants.ENGLISH;  // measurement
	private String length;
	private String height;
	private String weight;
	private String width;
	private Integer shippingCarrierId;
	private String shippingTrackingNo;
	private String shipDate;
	private Integer returnCarrierId;
	private String returnTrackingNo;
	private String returnTrackDate;
	private Integer coreReturnCarrierId;
	private String coreReturnTrackingNo;
	
	private String productLine;
	private Integer partId;

	private String otherShippingCarrier;
	private String otherReturnCarrier;
	private String otherCoreReturnCarrier;
	private String referencePartId;
	
	private Boolean providerBringparts;
	private SOWContactLocationDTO pickupContactLocation;
	private Boolean hasPartsAtDifferntLocation = false;
	private Boolean hasMorePartInfo;
	private Boolean hasPartPickUpInfo;
	private Boolean hasShippingInfo;
	private Boolean hasPartReturnShippingInfo;
	private Boolean enablePartShipStatus;
	private Boolean enablePartRetShipStatus;
	private Boolean enableCorePartRetShipStatus;
	
	public String getAltPartRef1() {
		return altPartRef1;
	}

	public void setAltPartRef1(String altPartRef1) {
		this.altPartRef1 = altPartRef1;
	}

	public String getAltPartRef2() {
		return altPartRef2;
	}

	public void setAltPartRef2(String altPartRef2) {
		this.altPartRef2 = altPartRef2;
	}

	private String altPartRef1;
	private String altPartRef2;
	
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getManufacturerPartNumber() {
		return manufacturerPartNumber;
	}

	public void setManufacturerPartNumber(String manufacturerPartNumber) {
		this.manufacturerPartNumber = manufacturerPartNumber;
	}

	public String getVendorPartNumber() {
		return vendorPartNumber;
	}

	public void setVendorPartNumber(String vendorPartNumber) {
		this.vendorPartNumber = vendorPartNumber;
	}
	public String getAdditionalPartInfo() {
		return additionalPartInfo;
	}

	public void setAdditionalPartInfo(String additionalPartInfo) {
		this.additionalPartInfo = additionalPartInfo;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public Integer getPartStatusId() {
		return partStatusId;
	}

	public void setPartStatusId(Integer partStatusId) {
		this.partStatusId = partStatusId;
	}

	public Boolean getProviderBringparts() {
		return providerBringparts;
	}


	
	public String getReturnTrackDate() {
		return returnTrackDate;
	}

	public void setReturnTrackDate(String returnTrackDate) {
		this.returnTrackDate = returnTrackDate;
	}
	
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getPartDesc() {
		return partDesc;
	}

	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}


	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Integer getShippingCarrierId() {
		return shippingCarrierId;
	}

	public void setShippingCarrierId(Integer shippingCarrierId) {
		this.shippingCarrierId = shippingCarrierId;
	}

	public String getShippingTrackingNo() {
		return shippingTrackingNo;
	}

	public void setShippingTrackingNo(String shippingTrackingNo) {
		this.shippingTrackingNo = shippingTrackingNo;
	}

	public Integer getReturnCarrierId() {
		return returnCarrierId;
	}

	public void setReturnCarrierId(Integer returnCarrierId) {
		this.returnCarrierId = returnCarrierId;
	}

	public String getReturnTrackingNo() {
		return returnTrackingNo;
	}

	public void setReturnTrackingNo(String returnTrackingNo) {
		this.returnTrackingNo = returnTrackingNo;
	}

	public Boolean isProviderBringparts() {
		return providerBringparts;
	}

	public void setProviderBringparts(Boolean providerBringparts) {
		this.providerBringparts = providerBringparts;
	}

	public SOWContactLocationDTO getPickupContactLocation() {
		return pickupContactLocation;
	}

	public void setPickupContactLocation(
			SOWContactLocationDTO pickupContactLocation) {
		this.pickupContactLocation = pickupContactLocation;
	}

	public String getOtherShippingCarrier() {
		return otherShippingCarrier;
	}

	public void setOtherShippingCarrier(String otherShippingCarrier) {
		this.otherShippingCarrier = otherShippingCarrier;
	}

	public List<IError> validate(int i) {
		clearAllErrors();
		clearAllWarnings();
		String str="";
		//TODO need to revisit 
	
		if (getPartDesc() != null && getPartDesc().length() > 750) {
			if(getTheResourceBundle() != null)
				str = getTheResourceBundle().getString("Part_Description_length_validation"); 
			
		addError("Part " + i + " --> "+ "Part Description",
				str,
				OrderConstants.SOW_TAB_ERROR);
		}
		if(StringUtils.isBlank(getPartDesc())){
			if(getTheResourceBundle() != null)
				str = getTheResourceBundle().getString("Part_Description_validation"); 
			
		addError("Part " + i + " --> " + "Part Description",	str, OrderConstants.SOW_TAB_ERROR);
		}
		
		
	    if(StringUtils.isBlank(getQuantity()) ){
			
			if(getTheResourceBundle() != null)
				str = getTheResourceBundle().getString("Part_Quantity_validation"); 
			
			addError("Part " + i + " --> ",
					str,
					OrderConstants.SOW_TAB_ERROR);
			}
		else if(getQuantity() != null && getQuantity().toString().length() <= 2){
			try{
				Integer qty = Integer.valueOf(getQuantity().toString());
				
			}catch(Exception e){
				if(getTheResourceBundle() != null)
					str = getTheResourceBundle().getString("Part_Quantity_validation"); 
				
				addError("Part " + i + " --> ",
						str,
						OrderConstants.SOW_TAB_ERROR);
			}
		}
	    		
		else if(getQuantity() != null && getQuantity().toString().length() > 2 ){
			
			if(getTheResourceBundle() != null)
				str = getTheResourceBundle().getString("Part_Quantity_Validation_Msg"); 
			
			addError("Part " + i + " --> ",
					str,
					OrderConstants.SOW_TAB_ERROR);
			}
		
		if(getLength() != null && getLength().length() > 0){
			Double len=null;
			try{
				len=Double.parseDouble(getLength());
			}catch(NumberFormatException nf){
				if(getTheResourceBundle() != null)
					str = getTheResourceBundle().getString("Part_Length_Validation_Msg"); 		
				
				addError("Part " + i + " --> " + getTheResourceBundle().getString("Part_length"), 
						str,
						OrderConstants.SOW_TAB_ERROR);
			}
		}
		if(getWidth() != null && getWidth().length() > 0){
			Double len = null;
			try{
				len=Double.parseDouble(getWidth());
			}catch(NumberFormatException wid){
				if(getTheResourceBundle() != null)
					str = getTheResourceBundle().getString("Part_Width_Validation_Msg"); 								
	        addError("Part " + i + " --> " + getTheResourceBundle().getString("Part_Width"), 
	        		str, 
	        		OrderConstants.SOW_TAB_ERROR);
			}
		}
		if(getHeight() != null && getHeight().length() > 0){
			Double len = null;
			try{
				len = Double.parseDouble(getHeight());
			}catch(NumberFormatException eh){
				if(getTheResourceBundle() != null)
					str = getTheResourceBundle().getString("Part_Height_Vlaidation_Msg"); 								
				
				addError("Part " + i + " --> " + getTheResourceBundle().getString("Part_Height"), 
						str,
						OrderConstants.SOW_TAB_ERROR);
			}
		}
		if(getWeight()!= null && getWeight().length() > 0){
			Double len = null;
			try{
				len=Double.parseDouble(getWeight());
			}catch(NumberFormatException wid){
				if(getTheResourceBundle() != null)
					str = getTheResourceBundle().getString("Part_Weight_Validation"); 								
	        addError("Part " + i + " --> " + getTheResourceBundle().getString("Part_weight"), 
	        		str, 
	        		OrderConstants.SOW_TAB_ERROR);
		  }
		}

		if(getShippingTrackingNo() != null && getShippingTrackingNo().length() > 50){
			if(getTheResourceBundle() != null)
				str = getTheResourceBundle().getString("Shipping_Tracking_No_Validation_Msg"); 								
			
			addError("Part " + i + " --> ", 
					str, 
					OrderConstants.SOW_TAB_ERROR);
		}
		if(getReturnTrackingNo() != null && getReturnTrackingNo().length() > 50){
			if(getTheResourceBundle() != null)
				str = getTheResourceBundle().getString("Return_Tracking_Id"); 											
			addError("Part " + i + " --> ",
					str, 
					OrderConstants.SOW_TAB_ERROR);
		}
		
		// validate shippingcarrier & trackingNo
		validateShippingCarrierAndTrackingNo(i);
		
		if ( pickupContactLocation!= null && isThereAnyPartPickupInfo() ) {
			validatePickUpContactPhones(i);
			validatePickUpContactEmail(i);
			validatePickUpContactAddress(i);
		}
		
		validateMiscFields(i);
		
		return getErrors();
	}

	private void validateMiscFields(int i) {
		if(StringUtils.isNotBlank(getModelNumber()) && !StringUtils.isAlphanumericSpace(getModelNumber())){
			addError("Part " + i + " --> " + "Model Number", 
					getTheResourceBundle().getString("Model_Number_Validation_Msg"), 
					OrderConstants.SOW_TAB_ERROR);
		}
		if(StringUtils.isNotBlank(getSerialNumber()) && !StringUtils.isAlphanumericSpace(getSerialNumber())){
			addError("Part " + i + " --> " + "Serial Number", 
					getTheResourceBundle().getString("Serial_Number_Validation_Msg"), 
					OrderConstants.SOW_TAB_ERROR);
		}
		if(StringUtils.isNotBlank(getOrderNumber()) && !StringUtils.isAlphanumericSpace(getOrderNumber())){
			addError("Part " + i + " --> " + "Order Number", 
					getTheResourceBundle().getString("Order_Number_Validation_Msg"), 
					OrderConstants.SOW_TAB_ERROR);
		}
		if(StringUtils.isNotBlank(getPurchaseOrderNumber()) && !StringUtils.isAlphanumericSpace(getPurchaseOrderNumber())){
			addError("Part " + i + " --> " + "PO Number", 
					getTheResourceBundle().getString("Purchase_Order_Number_Validation_Msg"), 
					OrderConstants.SOW_TAB_ERROR);
		}
		if(StringUtils.isNotBlank(getManufacturerPartNumber()) && !StringUtils.isAlphanumericSpace(getManufacturerPartNumber())){
			addError("Part " + i + " --> " + "OEM Number", 
					getTheResourceBundle().getString("OEM_Validation_Msg"), 
					OrderConstants.SOW_TAB_ERROR);
		}
		if(StringUtils.isNotBlank(getVendorPartNumber()) && !StringUtils.isAlphanumericSpace(getVendorPartNumber())){
			addError("Part " + i + " --> " + "Vendor Part Number", 
					getTheResourceBundle().getString("Vendor_Part_Number_Validation_Msg"), 
					OrderConstants.SOW_TAB_ERROR);
		}

		
	}

	private void validatePickUpContactAddress(int i) {		
			if(getPickupContactLocation().getFirstName() != null && getPickupContactLocation().getFirstName().trim().length() > 50){
				addError("Part " + i + " --> ", 
						getTheResourceBundle().getString("First_Name_Length_Validation"), 
						OrderConstants.SOW_TAB_ERROR);
			}	
			if(getPickupContactLocation().getStreetName1() == null || getPickupContactLocation().getStreetName1().length() < 1 ){
				addWarning("Part " + i + " --> ", 
						getTheResourceBundle().getString("Street_Name_Validation"), 
						OrderConstants.SOW_TAB_WARNING);
			}
			if(getPickupContactLocation().getStreetName1() != null && getPickupContactLocation().getStreetName1().length() > 50){
				addError("Part " + i + " --> ", 
						getTheResourceBundle().getString("Street_Length_Validation_Msg"), 
						OrderConstants.SOW_TAB_ERROR);
			}
			if(getPickupContactLocation().getStreetName2() != null && getPickupContactLocation().getStreetName2().length() > 30){
				addWarning("Part " + i + " --> ", 
						getTheResourceBundle().getString("Street2_Length_Validation_Msg"), 
						OrderConstants.SOW_TAB_WARNING);
				
			}
		/*if(pickupContactLocation.getCity() == null || pickupContactLocation.getCity().length()<1){
			addError("city", "City is a required field", OrderConstants.SOW_TAB_ERROR);
		}
		*/
		//Add State Validation
			if(getPickupContactLocation().getCity() == null || getPickupContactLocation().getCity().length()<1){
				addWarning("Part " + i + " --> ", 
						getTheResourceBundle().getString("City_Validation"), 
						OrderConstants.SOW_TAB_WARNING);
			}
		if(getPickupContactLocation().getCity() != null && getPickupContactLocation().getCity().length() > 50){
			addError("Part " + i + " --> ",
					getTheResourceBundle().getString("City_Length_Validation"),
					OrderConstants.SOW_TAB_ERROR);
		}
		if(getPickupContactLocation().getState() == null || getPickupContactLocation().getState().equals("")){
			addWarning("Part " + i + " --> ", 
					getTheResourceBundle().getString("State_Validation_Msg"),
					OrderConstants.SOW_TAB_WARNING);
		}
		if (pickupContactLocation.getZip() != null
				&& pickupContactLocation.getZip().length() > 0) {
			boolean valResult = false;
			String numPattern = "(\\d{5})";
			valResult = pickupContactLocation.getZip().matches(numPattern);
			if (valResult == false) {
				addError("Part " + i + " --> " +"Zip",
						getTheResourceBundle().getString("Zip_Validation"),
						OrderConstants.SOW_TAB_ERROR);

			}
		}
		if (pickupContactLocation.getZip() == null || (pickupContactLocation.getZip() != null && pickupContactLocation.getZip().trim().length()==0 )){
				addWarning("Part " + i + " --> " +"Zip",
						getTheResourceBundle().getString("Zip_Validation_Missing"),
						OrderConstants.SOW_TAB_ERROR);
			


		}
		if (pickupContactLocation.getZip4() != null && pickupContactLocation.getZip4().trim().length()==4 ){
			try{
				Integer.parseInt(pickupContactLocation.getZip4());
			}catch(NumberFormatException ne){
				addError(getTheResourceBundle().getString("Zip4"),
						getTheResourceBundle().getString("Zip4_Validation"),
						OrderConstants.SOW_TAB_ERROR);
			}
		}
	}

	private void validatePickUpContactEmail(int i) {
		if (pickupContactLocation.getEmail() != null
				&& pickupContactLocation.getEmail().trim().length() > 255) {
			addError("Part " + i + " --> "+getTheResourceBundle().getString("Email"),
					getTheResourceBundle()
							.getString("Email_Validation_Msg"),
					OrderConstants.SOW_TAB_ERROR);
		} else if (pickupContactLocation.getEmail() != null
				&& pickupContactLocation.getEmail().length() > 1) {
			Pattern p = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[_A-Za-z0-9-]+)");
			//Pattern p = Pattern.compile(".+@.+\\.[a-zA-Z]+");
			;
			Matcher m = p.matcher(pickupContactLocation.getEmail());
			boolean valResult = m.matches();
			if (valResult == false) {
				addError("Part " + i + " --> "+getTheResourceBundle().getString("Email"),
						getTheResourceBundle().getString(
								"Email_Pattern_Validation_Msg"),
						OrderConstants.SOW_TAB_ERROR);
			
		}
		 
}
	}

	private void validatePickUpContactPhones(int i) {
		if (pickupContactLocation.getPhones() != null) {
			List<SOWPhoneDTO> phones = pickupContactLocation.getPhones();
			for (int j = 0; j < phones.size(); j++) {
				SOWPhoneDTO iPhone = phones.get(j);
				if (iPhone.getPhone() != null
						&& iPhone.getPhone().trim().length() > 0) {
					boolean valResult = false;
					String numPattern = "(\\d{3})?\\d{3}\\d{4}";
					valResult = iPhone.getPhone().matches(numPattern);
					if (j == 0) {
						if(valResult == false){
							addError("Part " + i + " --> " +"phone", getTheResourceBundle().getString(
							"Phone_Missing_Number_Validation_Msg"),
							OrderConstants.SOW_TAB_ERROR);
						}
						if(iPhone.getPhoneClassId().equals(new Integer(-1))){
							addError("Part " + i + " --> "+getTheResourceBundle().getString("Phone_Type"),
							getTheResourceBundle().getString("Phone_Type_Validation"),
							OrderConstants.SOW_TAB_ERROR);
						}
						
					}
					if (j == 1) {
						if(valResult == false){
							addError("Part " + i + " --> "+"Alternate_phone",	getTheResourceBundle().getString(
							"Alternate_Phone_Missing_Number_Validation_Msg"),
							OrderConstants.SOW_TAB_ERROR);
						}
						if(iPhone.getPhoneClassId().equals(new Integer(-1))){
							addError("Part " + i + " --> "+getTheResourceBundle().getString("Alternate_Phone_Type"),
							getTheResourceBundle().getString("Alternate_Phone_Type_Validation"),
							OrderConstants.SOW_TAB_ERROR);
						}
						
					}
				}

			}
		}
	}

	private void validateShippingCarrierAndTrackingNo(int i) {
		if(getShippingCarrierId() == null && !StringUtils.isBlank(getShippingTrackingNo()) ){
			addError("Part " + i + " --> " +"Shipping Carrier", getTheResourceBundle().getString(
			"Shipping_Carrier_Validation_Msg"),	OrderConstants.SOW_TAB_ERROR);
		} 
		if(getReturnCarrierId() == null && !StringUtils.isBlank(getReturnTrackingNo()) ){
			addError("Part " + i + " --> " +"Part Return Carrier", getTheResourceBundle().getString(
			"Shipping_Carrier_Validation_Msg"),	OrderConstants.SOW_TAB_ERROR);
		}
		if(getCoreReturnCarrierId() == null && !StringUtils.isBlank(getCoreReturnTrackingNo()) ){
			addError("Part " + i + " --> " +"Core Part Return Carrier", getTheResourceBundle().getString(
			"Shipping_Carrier_Validation_Msg"),	OrderConstants.SOW_TAB_ERROR);
		}
		
		
		if(StringUtils.isBlank(getShippingTrackingNo()) && getShippingCarrierId()!= null){
			addError("Part " + i + " --> " +"Shipping Tracking No", getTheResourceBundle().getString(
			"Shipping_Tracking_No_Validation_Msg"),	OrderConstants.SOW_TAB_ERROR);
		}
		if(StringUtils.isBlank(getReturnTrackingNo()) && getReturnCarrierId()!= null){
			addError("Part " + i + " --> " +"Part Return Tracking No", getTheResourceBundle().getString(
			"Shipping_Tracking_No_Validation_Msg"),	OrderConstants.SOW_TAB_ERROR);
		}
        if(StringUtils.isBlank(getCoreReturnTrackingNo()) && getCoreReturnCarrierId()!= null ){
        	addError("Part " + i + " --> " +"Part Core Return Tracking No", getTheResourceBundle().getString(
			"Shipping_Tracking_No_Validation_Msg"),	OrderConstants.SOW_TAB_ERROR);
		}
		
		
		
		
	}
	
	public boolean isThereAnyMorePartInfo(){
		if (StringUtils.isNotBlank(additionalPartInfo)
				|| StringUtils.isNotBlank(manufacturerPartNumber) 
				|| StringUtils.isNotBlank(vendorPartNumber)
				|| StringUtils.isNotBlank(altPartRef1)
				|| StringUtils.isNotBlank(altPartRef2)
				|| StringUtils.isNotBlank(length)
				|| StringUtils.isNotBlank(height)
				|| StringUtils.isNotBlank(weight)) {
			return true;
		}		
		return false;
	}
	
	public boolean isThereAnyPartPickupInfo(){
		if ( pickupContactLocation!= null && (StringUtils.isNotBlank(pickupContactLocation.getLocationName())
				|| StringUtils.isNotBlank(pickupContactLocation.getStreetName1()) 
				|| StringUtils.isNotBlank(pickupContactLocation.getCity())
				|| StringUtils.isNotBlank(pickupContactLocation.getState())
				|| StringUtils.isNotBlank(pickupContactLocation.getZip())
				|| isTheyAnyCompletePhoneInfoComplete(pickupContactLocation.getPhones())
				|| StringUtils.isNotBlank(pickupContactLocation.getFirstName())
				|| StringUtils.isNotBlank(pickupContactLocation.getLastName())
				|| StringUtils.isNotBlank(pickupContactLocation.getEmail())) ) {
			return true;
		}		
		return false;
	}
	
	private boolean isTheyAnyCompletePhoneInfoComplete(List<SOWPhoneDTO> phoneList){
		for (SOWPhoneDTO phone : phoneList) {
			if(StringUtils.isNotBlank(phone.getAreaCode()) && StringUtils.isNotBlank(phone.getPhonePart1())
					&& StringUtils.isNotBlank(phone.getPhonePart2()) && phone.getPhoneType() != null){
				return true;
			}
		}
		return false;
	}
	
	public boolean isThereAnyShippingInfo(){
		if (shippingCarrierId != null || StringUtils.isNotBlank(shippingTrackingNo) 
				|| StringUtils.isNotBlank(shipDate)) {
			return true;
		}		
		return false;
	}
	
	public boolean isThereAnyReturnShippingInfo(){
		if (returnCarrierId!= null || StringUtils.isNotBlank(returnTrackingNo)
				|| coreReturnCarrierId!= null || StringUtils.isNotBlank(coreReturnTrackingNo) 
				|| StringUtils.isNotBlank(shipDate)) {
			return true;
		}		
		return false;
	}
	
		@Override
	public void validate() {
		//TODO it should not come here. It should go to validate(int i) method
		//return getErrors();
		setErrors(new ArrayList<IError>());
		setWarnings(new ArrayList<IWarning>());
	}

	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean getHasPartsAtDifferntLocation() {
		return hasPartsAtDifferntLocation;
	}

	public void setHasPartsAtDifferntLocation(Boolean hasPartsAtDifferntLocation) {
		this.hasPartsAtDifferntLocation = hasPartsAtDifferntLocation;
	}

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public String getOtherReturnCarrier() {
		return otherReturnCarrier;
	}

	public void setOtherReturnCarrier(String otherReturnCarrier) {
		this.otherReturnCarrier = otherReturnCarrier;
	}

	public String getReferencePartId() {
		return referencePartId;
	}

	public void setReferencePartId(String referencePartId) {
		this.referencePartId = referencePartId;
	}

	public String getShipDate() {
		return shipDate;
	}

	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}
	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	/**
	 * @return the coreReturnCarrierId
	 */
	public Integer getCoreReturnCarrierId() {
		return coreReturnCarrierId;
	}

	/**
	 * @param coreReturnCarrierId the coreReturnCarrierId to set
	 */
	public void setCoreReturnCarrierId(Integer coreReturnCarrierId) {
		this.coreReturnCarrierId = coreReturnCarrierId;
	}

	/**
	 * @return the coreReturnTrackingNo
	 */
	public String getCoreReturnTrackingNo() {
		return coreReturnTrackingNo;
	}

	/**
	 * @param coreReturnTrackingNo the coreReturnTrackingNo to set
	 */
	public void setCoreReturnTrackingNo(String coreReturnTrackingNo) {
		this.coreReturnTrackingNo = coreReturnTrackingNo;
	}

	/**
	 * @return the otherCoreReturnCarrier
	 */
	public String getOtherCoreReturnCarrier() {
		return otherCoreReturnCarrier;
	}

	/**
	 * @param otherCoreReturnCarrier the otherCoreReturnCarrier to set
	 */
	public void setOtherCoreReturnCarrier(String otherCoreReturnCarrier) {
		this.otherCoreReturnCarrier = otherCoreReturnCarrier;
	}

	/**
	 * @return the morePartInfo
	 */
	public Boolean getHasMorePartInfo() {
		return hasMorePartInfo;
	}

	/**
	 * @param morePartInfo the morePartInfo to set
	 */
	public void setHasMorePartInfo(Boolean hasMorePartInfo) {
		this.hasMorePartInfo = hasMorePartInfo;
	}

	/**
	 * @return the hasShippingInfo
	 */
	public Boolean getHasShippingInfo() {
		return hasShippingInfo;
	}

	/**
	 * @param hasShippingInfo the hasShippingInfo to set
	 */
	public void setHasShippingInfo(Boolean hasShippingInfo) {
		this.hasShippingInfo = hasShippingInfo;
	}

	/**
	 * @return the hasPartPickUpInfo
	 */
	public Boolean getHasPartPickUpInfo() {
		return hasPartPickUpInfo;
	}

	/**
	 * @param hasPartPickUpInfo the hasPartPickUpInfo to set
	 */
	public void setHasPartPickUpInfo(Boolean hasPartPickUpInfo) {
		this.hasPartPickUpInfo = hasPartPickUpInfo;
	}

	/**
	 * @return the hasPartReturnShippingInfo
	 */
	public Boolean getHasPartReturnShippingInfo() {
		return hasPartReturnShippingInfo;
	}

	/**
	 * @param hasPartReturnShippingInfo the hasPartReturnShippingInfo to set
	 */
	public void setHasPartReturnShippingInfo(Boolean hasPartReturnShippingInfo) {
		this.hasPartReturnShippingInfo = hasPartReturnShippingInfo;
	}

	/**
	 * @return the enablePartShipStatus
	 */
	public Boolean getEnablePartShipStatus() {
		return enablePartShipStatus;
	}

	/**
	 * @param enablePartShipStatus the enablePartShipStatus to set
	 */
	public void setEnablePartShipStatus(Boolean enablePartShipStatus) {
		this.enablePartShipStatus = enablePartShipStatus;
	}

	/**
	 * @return the enablePartRetShipStatus
	 */
	public Boolean getEnablePartRetShipStatus() {
		return enablePartRetShipStatus;
	}

	/**
	 * @param enablePartRetShipStatus the enablePartRetShipStatus to set
	 */
	public void setEnablePartRetShipStatus(Boolean enablePartRetShipStatus) {
		this.enablePartRetShipStatus = enablePartRetShipStatus;
	}

	/**
	 * @return the enableCorePartRetShipStatus
	 */
	public Boolean getEnableCorePartRetShipStatus() {
		return enableCorePartRetShipStatus;
	}

	/**
	 * @param enableCorePartRetShipStatus the enableCorePartRetShipStatus to set
	 */
	public void setEnableCorePartRetShipStatus(Boolean enableCorePartRetShipStatus) {
		this.enableCorePartRetShipStatus = enableCorePartRetShipStatus;
	}
	
}
