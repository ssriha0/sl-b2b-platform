package com.newco.marketplace.web.delegatesImpl.buyer;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.buyer.IBuyerRegistrationBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.buyer.BuyerRegistrationVO;
import com.newco.marketplace.web.delegates.buyer.IBuyerRegistrationDelegate;
import com.newco.marketplace.web.dto.buyer.BuyerRegistrationDTO;
import com.newco.marketplace.web.utils.BuyerRegistrationMapper;

public class BuyerRegistrationDelegateImpl implements IBuyerRegistrationDelegate{
	
	private IBuyerRegistrationBO iBuyerRegistrationBO;
	private static final Logger localLogger = Logger
			.getLogger(BuyerRegistrationDelegateImpl.class.getName());
	private BuyerRegistrationMapper buyerRegistrationMapper;

	public BuyerRegistrationDelegateImpl(
			IBuyerRegistrationBO buyerRegistrationBOImpl,
			BuyerRegistrationMapper buyerRegistrationMapper) {
		this.iBuyerRegistrationBO = buyerRegistrationBOImpl;
		this.buyerRegistrationMapper = buyerRegistrationMapper;
	
	}
	public BuyerRegistrationDTO loadRegistration(
			BuyerRegistrationDTO buyerRegistrationDTO)
			throws DelegateException {
		try {
			BuyerRegistrationVO buyerRegistrationVO = new BuyerRegistrationVO();
			buyerRegistrationVO = buyerRegistrationMapper.convertDTOtoVO(buyerRegistrationVO,buyerRegistrationDTO);
			buyerRegistrationVO = iBuyerRegistrationBO.loadRegistration(buyerRegistrationVO);
			buyerRegistrationDTO =  buyerRegistrationMapper.convertVOtoDTO(buyerRegistrationDTO, buyerRegistrationVO);
			
		} catch (BusinessServiceException ex) {
			localLogger
					.info("Business Service Exception @RegistrationDelegateImpl.loadRegistration() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"Business Service @RegistrationDelegateImpl.loadRegistration() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @RegistrationDelegateImpl.loadRegistration() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"General Exception @RegistrationDelegateImpl.loadRegistration() due to "
							+ ex.getMessage());
		}
		return buyerRegistrationDTO;
	}
	

	public BuyerRegistrationDTO loadData(BuyerRegistrationDTO buyerRegistrationDTO) throws DelegateException {
		try
		{
		BuyerRegistrationVO buyerRegistrationVO = new BuyerRegistrationVO();
		buyerRegistrationVO = buyerRegistrationMapper.convertDTOtoVO(buyerRegistrationVO,buyerRegistrationDTO);
		return buyerRegistrationMapper.convertVOtoDTO(buyerRegistrationDTO,iBuyerRegistrationBO.loadData(buyerRegistrationVO));
		} catch (BusinessServiceException ex) {
			localLogger
			.info("Business Service Exception @RegistrationDelegateImpl.loadData() due to"
					+ ex.getMessage());
				throw new DelegateException(
						"Business Service @RegistrationDelegateImpl.loadData() due to "
								+ ex.getMessage());
			} catch (Exception ex) {
				localLogger
						.info("General Exception @RegistrationDelegateImpl.loadData() due to"
								+ ex.getMessage());
				throw new DelegateException(
						"General Exception @RegistrationDelegateImpl.loadData() due to "
								+ ex.getMessage());
			}
	}
	
	public BuyerRegistrationDTO loadListData(BuyerRegistrationDTO buyerRegistrationDTO,Integer buyerId) throws DelegateException {
		try
		{
		BuyerRegistrationVO buyerRegistrationVO = new BuyerRegistrationVO();
		buyerRegistrationVO = buyerRegistrationMapper.convertDTOtoVO(buyerRegistrationVO,buyerRegistrationDTO);
		return buyerRegistrationMapper.convertVOtoDTO(buyerRegistrationDTO,iBuyerRegistrationBO.loadListData(buyerRegistrationVO,buyerId));
		} catch (BusinessServiceException ex) {
			localLogger
			.info("Business Service Exception @RegistrationDelegateImpl.loadData() due to"
					+ ex.getMessage());
				throw new DelegateException(
						"Business Service @RegistrationDelegateImpl.loadData() due to "
								+ ex.getMessage());
			} catch (Exception ex) {
				localLogger
						.info("General Exception @RegistrationDelegateImpl.loadData() due to"
								+ ex.getMessage());
				throw new DelegateException(
						"General Exception @RegistrationDelegateImpl.loadData() due to "
								+ ex.getMessage());
			}
	}
	
	public BuyerRegistrationDTO loadListData(BuyerRegistrationDTO buyerRegistrationDTO) throws DelegateException {
		try
		{
		BuyerRegistrationVO buyerRegistrationVO = new BuyerRegistrationVO();
		buyerRegistrationVO = buyerRegistrationMapper.convertDTOtoVO(buyerRegistrationVO,buyerRegistrationDTO);
		return buyerRegistrationMapper.convertVOtoDTO(buyerRegistrationDTO,iBuyerRegistrationBO.loadListData(buyerRegistrationVO));
		} catch (BusinessServiceException ex) {
			localLogger
			.info("Business Service Exception @RegistrationDelegateImpl.loadData() due to"
					+ ex.getMessage());
				throw new DelegateException(
						"Business Service @RegistrationDelegateImpl.loadData() due to "
								+ ex.getMessage());
			} catch (Exception ex) {
				localLogger
						.info("General Exception @RegistrationDelegateImpl.loadData() due to"
								+ ex.getMessage());
				throw new DelegateException(
						"General Exception @RegistrationDelegateImpl.loadData() due to "
								+ ex.getMessage());
			}
	}
	
	public boolean updateBuyerCompanyProfile(BuyerRegistrationDTO buyerRegistrationDTO) throws DelegateException {
		try
		{
			BuyerRegistrationVO buyerRegistrationVO = new BuyerRegistrationVO();
			buyerRegistrationVO = buyerRegistrationMapper.convertDTOtoVO(buyerRegistrationVO,buyerRegistrationDTO);
			return iBuyerRegistrationBO.updateBuyerCompanyProfile(buyerRegistrationVO);
		}
		catch (BusinessServiceException ex) {
			localLogger
			.info("Business Service Exception @RegistrationDelegateImpl.updateBuyerCompanyProfile() due to"
					+ ex.getMessage());
				throw new DelegateException(
						"Business Service @RegistrationDelegateImpl.updateBuyerCompanyProfile() due to "
								+ ex.getMessage());
			} catch (Exception ex) {
				localLogger
						.info("General Exception @RegistrationDelegateImpl.updateBuyerCompanyProfile() due to"
								+ ex.getMessage());
				throw new DelegateException(
						"General Exception @RegistrationDelegateImpl.updateBuyerCompanyProfile() due to "
								+ ex.getMessage());
			}
	}
	
	public boolean isBlackoutState(String stateCode) throws DelegateException {
		boolean flag = false;
		try{
			flag = iBuyerRegistrationBO.checkBlackoutState(stateCode);
			
		}catch(BusinessServiceException bse){
			throw new DelegateException("BusinessServiceException@BuyerRegistrationDelegateImpl.isBlackoutState");
		}catch(Exception e){
			throw new DelegateException("General Exception @BuyerRegistrationDelegateImpl.isBlackoutState");
		}
		return flag; //buyerRegistrationBO.
	}
	
	public void saveBuyerLead(BuyerRegistrationDTO buyerRegistrationDTO) throws DelegateException {
		try{
			//BuyerRegistrationDTO buyerRegistrationDTO = buyerDTO.convertToBuyerRegistrationDTO();
			
			BuyerRegistrationVO buyerRegistrationVO = new BuyerRegistrationVO();
			buyerRegistrationVO = buyerRegistrationMapper.convertDTOtoVO(buyerRegistrationVO, buyerRegistrationDTO);
			
			buyerRegistrationVO.setRoleId(OrderConstants.SIMPLE_BUYER_ROLEID);
			iBuyerRegistrationBO.SaveBlackoutBuyerLead(buyerRegistrationVO);
		}catch(BusinessServiceException bse){
			throw new DelegateException("BusinessServiceException@BuyerDelegateImpl.saveBuyerLead");
		}catch(Exception e){
			throw new DelegateException("General Exception@BuyerRegistrationDelegateImpl.saveBuyerLead");
		}
	}
	public BuyerRegistrationDTO saveBuyerRegistration(
			BuyerRegistrationDTO buyerRegistrationDTO)
			throws DelegateException, DuplicateUserException {
		try {
			BuyerRegistrationVO buyerRegistrationVO = new BuyerRegistrationVO();			
			buyerRegistrationVO = buyerRegistrationMapper.convertDTOtoVO(buyerRegistrationVO,buyerRegistrationDTO);
			buyerRegistrationVO =  iBuyerRegistrationBO.saveProfBuyerRegistration(buyerRegistrationVO);
			buyerRegistrationDTO = buyerRegistrationMapper.convertVOtoDTO(buyerRegistrationDTO,buyerRegistrationVO);
			
		}catch (BusinessServiceException ex) {
			localLogger
					.info("Business Service Exception @RegistrationDelegateImpl.saveRegistration() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"Business Service @RegistrationDelegateImpl.saveRegistration() due to "
							+ ex.getMessage(),ex);
		} catch(DuplicateUserException ex){
			localLogger
			        .info("DuplicateUserException @RegistrationDelegateImpl.saveRegistration() due to"
					+ ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			localLogger
					.info("General Exception @RegistrationDelegateImpl.saveRegistration() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"General Exception @RegistrationDelegateImpl.saveRegistration() due to "
							+ ex.getMessage(), ex);
		}
		return buyerRegistrationDTO;
	}
	
	public BuyerRegistrationDTO loadZipSet(
			BuyerRegistrationDTO buyerRegistrationDTO)
			throws DelegateException {
		
		try {
			BuyerRegistrationVO buyerRegistrationVO = new BuyerRegistrationVO();
			buyerRegistrationVO = buyerRegistrationMapper.convertDTOtoVO(buyerRegistrationVO,buyerRegistrationDTO);
			buyerRegistrationVO = iBuyerRegistrationBO.loadZipSet(buyerRegistrationVO);
			buyerRegistrationDTO = buyerRegistrationMapper.convertVOtoDTO(buyerRegistrationDTO,buyerRegistrationVO); 
			
		} catch (BusinessServiceException ex) {
			localLogger
					.info("Business Service Exception @BuyerRegistrationDelegateImpl.loadZipSet() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"Business Service @BuyerRegistrationDelegateImpl.loadZipSet() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("General Exception @BuyerRegistrationDelegateImpl.loadZipSet() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"General Exception @BuyerRegistrationDelegateImpl.loadZipSet() due to "
							+ ex.getMessage());
		}
		
		return buyerRegistrationDTO;
	}

	public List<String> getBlackoutStates() throws DelegateException {
		
		List<String> blackoutStates = new ArrayList<String>();
		try {
			blackoutStates = iBuyerRegistrationBO.getBlackoutStates();
		} catch(BusinessServiceException ex) {
			localLogger
			.info("Business Service Exception @BuyerRegistrationDelegateImpl.getBlackoutStates() due to"
					+ ex.getMessage());
		}
		
		return blackoutStates;		
	}
	/**
	 * @return the iBuyerRegistrationBO
	 */
	public IBuyerRegistrationBO getiBuyerRegistrationBO() {
		return iBuyerRegistrationBO;
	}
	/**
	 * @param buyerRegistrationBO the iBuyerRegistrationBO to set
	 */
	public void setiBuyerRegistrationBO(IBuyerRegistrationBO buyerRegistrationBO) {
		iBuyerRegistrationBO = buyerRegistrationBO;
	}
}
