package com.newco.marketplace.web.delegatesImpl.provider;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.IMarketPlaceBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.vo.provider.MarketPlaceVO;
import com.newco.marketplace.web.delegates.provider.IMarketPlaceDelegate;
import com.newco.marketplace.web.dto.provider.MarketPlaceDTO;
import com.newco.marketplace.web.utils.MarketPlaceMapper;

/**
 * @author Covansys
 * 
 */
public class MarketPlaceDelegateImpl implements IMarketPlaceDelegate {

	private static final Logger logger = Logger.getLogger(MarketPlaceDelegateImpl.class.getName());
	
	//private MarketPlaceDTO marketPlaceDTO;
	private IMarketPlaceBO iMarketPlaceBO;
	private MarketPlaceMapper marketPlaceMapper;
	//private MarketPlaceVO marketPlaceVO;
	

	public MarketPlaceDelegateImpl(	IMarketPlaceBO iMarketPlaceBO,
									MarketPlaceMapper marketPlaceMapper) {
		this.iMarketPlaceBO = iMarketPlaceBO;
		this.marketPlaceMapper = marketPlaceMapper;
	}

	public MarketPlaceDTO loadMarketPlace(MarketPlaceDTO marketPlaceDTO, String provUserName)
												throws DelegateException {
		try {
			//Converts all the DTO values to VO values
			MarketPlaceVO marketPlaceVO = new MarketPlaceVO();
			marketPlaceVO = marketPlaceMapper.convertDTOtoVO(marketPlaceDTO, marketPlaceVO);
			
			//Gets the values for the new/already existing user
			marketPlaceVO = iMarketPlaceBO.loadMarketPlace(marketPlaceVO,provUserName);
			
			//Converts all the VO values to the DTO values 
			marketPlaceDTO = marketPlaceMapper.convertVOtoDTO(marketPlaceVO, marketPlaceDTO);
			
		} catch (BusinessServiceException ex) {
			logger
					.info("Business Service Exception @MarketPlaceDelegate.loadMarketPlace() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"Business Service @MarketPlaceDelegate.loadMarketPlace() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			logger
					.info("General Exception @MarketPlaceDelegate.loadMarketPlace() due to"
							+ ex.getMessage());
			throw new DelegateException(
					"General Exception @MarketPlaceDelegate.loadMarketPlace() due to "
							+ ex.getMessage());
		}
		return marketPlaceDTO;
	}
	//R16_1: SL-18979: Commenting out code since validating sms no is done through vibes call
	/*public boolean validateSmsNo(MarketPlaceDTO marketPlaceDTO) {
		MarketPlaceVO marketPlaceVO = new MarketPlaceVO();
		boolean valid = false;
		marketPlaceVO = marketPlaceMapper.convertDTOtoVO(marketPlaceDTO, marketPlaceVO);
		
		
		valid=iMarketPlaceBO.validateSmsNo(marketPlaceVO.getSmsAddress());
		
		return valid;
		
	}*/
	public MarketPlaceDTO updateMarketPlace(
			MarketPlaceDTO marketPlaceDTO)
			throws DelegateException, DuplicateUserException{
		try {
			//Converts all the DTO values to the VO values
			MarketPlaceVO marketPlaceVO = new MarketPlaceVO();
			marketPlaceVO = marketPlaceMapper.convertDTOtoVO(marketPlaceDTO, marketPlaceVO);
			
			//Update the new or modified values for the USER
			marketPlaceVO =  iMarketPlaceBO.updateMarketPlace(marketPlaceVO);
			
			//Converts all the VO values to the DTO values
			marketPlaceDTO = marketPlaceMapper.convertVOtoDTO(marketPlaceVO, marketPlaceDTO);
			
		} catch (DuplicateUserException dex) {
			logger
			.info("Business Service Exception @MarketPlaceDelegateImpl.updateMarketPlace() due to"
						+ dex.getMessage());
			throw dex;
		} catch (BusinessServiceException a_Ex) {
			logger
				.info("Business Service Exception @MarketPlaceDelegateImpl.updateMarketPlace() due to"
							+ a_Ex.getMessage());
			throw new DelegateException(
					"Business Service @MarketPlaceDelegateImpl.updateMarketPlace() due to "
							+ a_Ex.getMessage());
		} catch (Exception a_Ex) {
			logger
				.info("General Exception @MarketPlaceDelegateImpl.updateMarketPlace() due to"
							+ a_Ex.getMessage());
			throw new DelegateException(
					"General Exception @MarketPlaceDelegateImpl.updateMarketPlace() due to "
							+ a_Ex.getMessage());
		}
		
		return marketPlaceDTO;
	}
	
}
