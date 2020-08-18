package com.newco.marketplace.web.delegatesImpl.provider;

import java.util.HashMap;

import com.newco.marketplace.business.iBusiness.provider.IWarrantyBO;
import com.newco.marketplace.vo.provider.WarrantyVO;
import com.newco.marketplace.web.delegates.provider.IWarrantyDelegate;
import com.newco.marketplace.web.dto.provider.WarrantyDto;
import com.newco.marketplace.web.utils.WarrantyMapper;

/**
 * @author MTedder
 * This class contains is the delegate layer to pass the VO to the BO layer.
 */

public class WarrantyDelegateImpl implements IWarrantyDelegate{
	
	//Accept DTO and convert to VO using object mapper
	private WarrantyMapper warrantyMapper;
	//private WarrantyVO warrantyVO;
	//private WarrantyDto warrantyDto;
	private IWarrantyBO iWarrantyBO;

	/**
	 * @param warrantyMapper
	 * @param warrantyVO
	 * @param warrantyBO
	 */
	public WarrantyDelegateImpl(WarrantyMapper warrantyMapper,
			 IWarrantyBO warrantyBO ) {

		this.warrantyMapper = warrantyMapper;
	//	this.warrantyVO = warrantyVO;
		this.iWarrantyBO = warrantyBO;
	}

	/* (non-Javadoc)
	 * @see net.roseindia.IWarrantyDelegate#setWarrantyInfo(net.roseindia.WarrantyVO)
	 */
	public int saveWarrantyInfo(WarrantyDto wdto){
		//debug
		
		//map DTO to VO
		WarrantyVO warrantyVO = new WarrantyVO();
		warrantyVO = warrantyMapper.convertDTOtoVO(wdto, warrantyVO);
		//DAO logic
		iWarrantyBO.saveWarrantyData(warrantyVO);
		
		return 0;
	}

	/* (non-Javadoc)
	 * @see net.roseindia.IWarrantyDelegate#getWarrantyInfo()
	 */
	public WarrantyDto getWarrantyData(WarrantyDto wdto){
		WarrantyVO warrantyVO = new WarrantyVO();
		warrantyVO = warrantyMapper.convertDTOtoVO(wdto, warrantyVO);
		//call DAO logic
		warrantyVO = iWarrantyBO.getWarrantyData(warrantyVO);
		//map VO to DTO
		wdto = warrantyMapper.convertVOtoDTO(warrantyVO, wdto);
		return wdto;
	}

	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IWarrantyDelegate#deleteWarrantyInfo(com.newco.marketplace.web.dto.WarrantyDto)
	 */
	public void deleteWarrantyInfo(WarrantyDto wdto) {
		//map DTO to VO
		WarrantyVO warrantyVO = new WarrantyVO();
		warrantyVO = warrantyMapper.convertDTOtoVO(wdto, warrantyVO);
		//DAO logic
		iWarrantyBO.deleteWarrantyData(warrantyVO);		
	}
	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IWarrantyDelegate#updateWarrantyInfo(com.newco.marketplace.web.dto.WarrantyDto)
	 */
	public void updateWarrantyData(WarrantyDto wdto){
		//map DTO to VO
		WarrantyVO warrantyVO = new WarrantyVO();
		warrantyVO = warrantyMapper.convertDTOtoVO(wdto, warrantyVO);
		//DAO logic
		iWarrantyBO.updateWarrantyData(warrantyVO);	
	}
	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IWarrantyDelegate#loadPage()
	 */
	public WarrantyDto loadPage(WarrantyDto wdto) {
		//call DAO logic
		WarrantyVO warrantyVO = new WarrantyVO();
		warrantyVO = iWarrantyBO.loadPage(warrantyVO);
		
		//map VO to DTO
		wdto = warrantyMapper.convertVOtoDTO(warrantyVO, wdto);
		return wdto;
	}

	public HashMap getMapLuWarrantyPeriods() {
		HashMap map = iWarrantyBO.getMapLuWarrantyPeriods();
		
		return map;
	}
}
