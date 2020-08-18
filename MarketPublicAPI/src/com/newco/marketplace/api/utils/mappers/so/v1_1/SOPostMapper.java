/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 11-Jun-2009	KMSTRSUP   Infosys				1.0
 *
 *
 */
package com.newco.marketplace.api.utils.mappers.so.v1_1;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.OrderStatus;
import com.newco.marketplace.api.beans.so.Phone;
import com.newco.marketplace.api.beans.so.post.SOPostResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.PhoneVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
/**
 * This class would act as a Mapper class for mapping service order
 * to SOPostResponse object
 *
 * @author Infosys
 * @version 1.0
 */
public class SOPostMapper {
	private Logger logger = Logger.getLogger(SOPostMapper.class);
	private ArrayList<LookupVO> lookUpVOList = null;
	private ILookupBO lookupBO;


	/**
	 * This method is for mapping Mapping service order post result to
	 * SOPostResponse Object.
	 *
	 * @param pResponse ProcessResponse
	 * @param serviceOrder ServiceOrder
	 * @throws DataException
	 * @return SOPostResponse
	 */
	public SOPostResponse mapServiceOrder(ProcessResponse pResponse,
			ServiceOrder serviceOrder) throws DataException {
		logger.info("Entering SOPostMapper.mapServiceOrder()");
		SOPostResponse soPostResponse = new SOPostResponse();
		Results results = new Results();

		OrderStatus orderStatus = new OrderStatus();
		logger.info("Setting service order details to " +
					"OrderStatus object of Response object");
		orderStatus.setSoId(serviceOrder.getSoId());
		if (null != serviceOrder.getStatus()) {
			orderStatus.setStatus(serviceOrder.getStatus());
		} else {
			orderStatus.setStatus("");
		}
		if (null != serviceOrder.getSubStatus()) {
			orderStatus.setSubstatus((serviceOrder.getSubStatus()));
		} else {
			orderStatus.setSubstatus("");
		}
		if (null != serviceOrder.getCreatedDate()) {

			orderStatus.setCreatedDate(CommonUtility.sdfToDate
					.format(serviceOrder.getCreatedDate()));
		} else {
			orderStatus.setCreatedDate("");
		}
		if (null != serviceOrder.getRoutedDate()) {

			orderStatus.setPostedDate(CommonUtility.sdfToDate
					.format(serviceOrder.getRoutedDate()));
		}
		if (null != serviceOrder.getAcceptedDate()) {

			orderStatus.setAcceptedDate(CommonUtility.sdfToDate
					.format(serviceOrder.getAcceptedDate()));
		}
		if (null != serviceOrder.getActivatedDate()) {

			orderStatus.setActiveDate(CommonUtility.sdfToDate
					.format(serviceOrder.getActivatedDate()));
		}
		if (null != serviceOrder.getCompletedDate()) {

			orderStatus.setCompletedDate(CommonUtility.sdfToDate
					.format(serviceOrder.getCompletedDate()));
		}
		if (null != serviceOrder.getClosedDate()) {
			orderStatus.setClosedDate(CommonUtility.sdfToDate
					.format(serviceOrder.getClosedDate()));
		}
		if (pResponse.getCode().equalsIgnoreCase(ServiceConstants.VALID_RC)) {
			results = Results.getSuccess(pResponse.getMessages().get(0));
		} else {
			logger.info("Setting result and message as Failure when the " +
												"Post Operation has failed");

			results = Results.getError(pResponse.getMessages().get(0),
					ResultsCode.FAILURE.getCode());
		}
		soPostResponse.setResults(results);
		soPostResponse.setOrderstatus(orderStatus);
		logger.info("Leaving SOPostMapper.mapServiceOrder()");
		return soPostResponse;

	}
	/**
	 * This method is for setting Phone number
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 *
	 */
	public void mapServiceContactPhone(
			ServiceOrder serviceOrder){
		List<PhoneVO> phoneVOList = new ArrayList<PhoneVO>();
		Phone phone=new Phone();
		PhoneVO phoneVO=new PhoneVO();
		if(serviceOrder.getServiceContact()!=null){
			phone.setExtension(serviceOrder.getServiceContact().getPhoneNoExt());
			phone.setNumber(serviceOrder.getServiceContact().getPhoneNo());
			if(null!=serviceOrder.getServiceContact().getPhoneTypeId()){
				phone.setPhoneType(serviceOrder.getServiceContact().getPhoneTypeId().toString());
			}
			phoneVO=mapPhoneDetails(phone, serviceOrder.getServiceContact().getPhoneTypeId());
			if(null!=serviceOrder.getServiceContact().getPhoneClassId()){
				phoneVO.setClassId(Integer.parseInt(serviceOrder.getServiceContact().getPhoneClassId()));
			}
			phoneVOList.add(phoneVO);

			if(null!=serviceOrder.getServiceContactAlt()){
				List<PhoneVO> altPhoneVOList = new ArrayList<PhoneVO>();
				Phone altPhone=new Phone();
				PhoneVO altPhoneVO=new PhoneVO();
				altPhone.setExtension(serviceOrder.getServiceContactAlt().getPhoneNoExt());
				altPhone.setNumber(serviceOrder.getServiceContactAlt().getPhoneNo());
				if(null!=serviceOrder.getServiceContactAlt().getPhoneTypeId()){
					altPhone.setPhoneType(serviceOrder.getServiceContactAlt().getPhoneTypeId().toString());
				}
				altPhoneVO=mapPhoneDetails(altPhone, serviceOrder.getServiceContactAlt().getPhoneTypeId());
				if(null!=serviceOrder.getServiceContactAlt().getPhoneClassId()){
					altPhoneVO.setClassId(Integer.parseInt(serviceOrder.getServiceContactAlt().getPhoneClassId()));
				}
				phoneVOList.add(altPhoneVO);
				Contact altContact=new Contact();
				//altContact=serviceOrder.getServiceContactAlt();
				//altContact.setPhones(altPhoneVOList);
				//serviceOrder.setServiceContactAlt(altContact);
				//serviceOrder.setAltServiceContact(altContact);
			}
			Contact contact=new Contact();

			contact=serviceOrder.getServiceContact();
			contact.setPhones(phoneVOList);
			serviceOrder.setServiceContact(contact);
		}

	}
	/**
	 * This method is for mapping Phone Details.
	 *
	 * @param phone
	 *            Phone
	 * @param phoneTypeId
	 *            int
	 * @return phoneVO
	 */
	private PhoneVO mapPhoneDetails(Phone phone, int phoneTypeId) {
		logger.info("Inside mapPhoneDetails--->Start");
		PhoneVO phoneVO = new PhoneVO();
		phoneVO.setPhoneNo(StringUtils.isEmpty(phone.getNumber()) ? null
				: phone.getNumber().replace("-", ""));
		phoneVO.setPhoneExt(phone.getExtension());
		phoneVO.setClassId(getPhoneTypeId(phone.getPhoneType()));
		phoneVO.setPhoneType(phoneTypeId);
		return phoneVO;
	}
	/**
	 * This method is for getting Phone TypeId.
	 *
	 * @param phoneType
	 *            String
	 * @return Integer
	 */
	private Integer getPhoneTypeId(String phoneType) {
		logger.info("Inside getPhoneTypeId--->Start");
		try {
			if (lookUpVOList == null) {
				lookUpVOList = lookupBO.getPhoneTypes();
			}
			for (LookupVO lookupVO : lookUpVOList) {
				if (phoneType.equalsIgnoreCase(lookupVO.getDescr())) {
					return lookupVO.getId();
				}
			}
		} catch (Exception e) {
			logger.error("Data Exception Occurred while getting  PhoneTypeId "
					+ e);
		}
		return null;
	}
	public ILookupBO getLookupBO() {
		return lookupBO;
	}
	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

}
