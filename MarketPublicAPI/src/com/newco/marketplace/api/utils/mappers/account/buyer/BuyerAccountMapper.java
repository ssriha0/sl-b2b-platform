/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 25-Sept-2009	pgangra   SHC				1.0
 * 
 * 
 */

package com.newco.marketplace.api.utils.mappers.account.buyer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.account.buyer.CreateBuyerAccountRequest;
import com.newco.marketplace.api.beans.account.buyer.CreateBuyerAccountResponse;
import com.newco.marketplace.api.beans.account.buyer.ModifyBuyerAccountRequest;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.buyer.BuyerRegistrationVO;

/**
 * This is a utility mapper class used for converting to and from VO objects
 * for Buyer Account create, modify functionality.
 * 
 * @author priti
 *
 */
public class BuyerAccountMapper {

	private static final Logger logger = Logger
			.getLogger(BuyerAccountMapper.class);


	/**
	 * This method is used for Create Buyer Account request and converts POJO
	 * request object(BuyerAccountRequest) to VO (BuyerRegistrationVO).
	 * 
	 * @param pojo
	 * @return BuyerRegistrationVO
	 * @throws DataException
	 */
	public static BuyerRegistrationVO getVOForCreateBuyerAccount(
			CreateBuyerAccountRequest pojo) throws DataException {
		if (pojo == null) {
			logger.error("Create Buyer Account Request: POJO object created "
					+ "from request XML String did not have any value.");
			throw new DataException("POJO Object created from XML did not "
					+ "have any values");
		}

		BuyerRegistrationVO vo = new BuyerRegistrationVO();
		vo.setUserName(pojo.getUserName());
		vo.setEmail(pojo.getEmail());
		
		if(StringUtils.isEmpty(pojo.getBuyerType())) {
			logger.error("Un-recognized buyerType, please refer to the schema definition for supported buyerTypes");
			throw new DataException ("Un-recognized buyerType, please refer to the schema definition for supported buyerTypes");
		} else if (pojo.getBuyerType().trim().equalsIgnoreCase("Simple")) {
			vo.setRoleWithinCom(OrderConstants.SIMPLE_BUYER_COMPANY_ROLE_ID+"");
			vo.setRoleId(OrderConstants.SIMPLE_BUYER_ROLEID);
			vo.setSimpleBuyerInd(true);
		} else if(pojo.getBuyerType().trim().equalsIgnoreCase("Pro")) {
			logger.error("The current implementation does not support Enterprise buyer");
			throw new DataException ("The current implementation does not support Enterprise buyer");
		}
		
		vo.setFirstName(pojo.getFirstName());
		vo.setLastName(pojo.getLastName());
		
		if(pojo.getIsHomeAddress()==null || pojo.getIsHomeAddress()==0) {
			vo.setLocName(pojo.getAddressLabel());
		} else {
			vo.setHomeAddressInd("true");
			vo.setLocName("Home");
		}
		
		vo.setBusinessStreet1(pojo.getAddress1());
		vo.setBusinessStreet2(pojo.getAddress2());
		vo.setBusinessCity(pojo.getCity());
		vo.setBusinessState(pojo.getStateCode());
		vo.setBusinessZip(pojo.getZip());
		vo.setApiFlag(true);
		vo.setBusPhoneNo1(pojo.getPrimaryPhone());
		vo.setMobPhoneNo1(pojo.getAlternatePhone());
		
		vo.setPromotionalMailInd(pojo.getSendSpecialOffer());
		vo.setHowDidYouHear(OrderConstants.SIMPLE_BUYER_ROLEID+"");//Without this value, creating new order fails.
		return vo;
	}


	/**
	 * This method is used for Modify Buyer Account request and converts POJO
	 * request object(ModifyBuyerAccountRequest) to VO (BuyerRegistrationVO).
	 * 
	 * @param pojo
	 * @return BuyerRegistrationVO
	 * @throws DataException
	 */
	public static BuyerRegistrationVO getVOForModifyBuyerAccount(
			ModifyBuyerAccountRequest pojo) throws DataException {
		if (pojo == null) {
			logger.error("Modify Buyer Account Request: POJO object created "
					+ "from request XML String did not have any value.");
			throw new DataException("POJO Object created from XML did not "
					+ "have any values");
		}

		BuyerRegistrationVO vo = new BuyerRegistrationVO();
		vo.setEmail(pojo.getEmail());
		vo.setFirstName(pojo.getFirstName());
		vo.setLastName(pojo.getLastName());
		vo.setBusinessStreet1(pojo.getAddress1());
		vo.setBusinessStreet2(pojo.getAddress2());
		vo.setBusinessCity(pojo.getCity());
		vo.setBusinessState(pojo.getStateCode());
		vo.setBusinessZip(pojo.getZip());
		vo.setBusPhoneNo1(pojo.getPrimaryPhone());
		vo.setBusPhoneNo2(pojo.getAlternatePhone());
		//BuyerResourceId is read from URL
		vo.setBuyerContactResourceId(pojo.getBuyerResourceId());
		//additional fields for FinCen
		vo.setEinNo(pojo.getEinNo());
		vo.setSsnInd(pojo.getSsnInd());
		vo.setDateOfBirth(pojo.getDateOfBirth());
		vo.setAltIdType(pojo.getAltIdType());
		vo.setAltIdCountry(pojo.getAltIdCountry());
		return vo;
	}

	
	
	/**
	 * This method is used for Create Buyer Account response and converts
	 * VO(BuyerRegistrationVO) to POJO(BuyerAccountResponse).
	 * 
	 * @param vo
	 * @return BuyerAccountResponse
	 * @throws DataException
	 */
	public static CreateBuyerAccountResponse convertVOToPOJOForCreateBuyerAccount(
			BuyerRegistrationVO vo) throws DataException {

		CreateBuyerAccountResponse response = new CreateBuyerAccountResponse();

		if (vo == null) {
			logger.error("Create Buyer Account Request: failed, Value "
					+ "object returned has no data.");
			throw new DataException("Create Buyer Account Request failed");
		}

		response.setResults(Results.getSuccess());
		response.setBuyerResourceId(vo.getBuyerContactResourceId());
		response.setBuyerId(vo.getBuyerId());
		logger.info("Response:::: BuyerID: " + vo.getBuyerId());
		return response;
	}

}
