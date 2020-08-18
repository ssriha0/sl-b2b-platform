package com.newco.marketplace.persistence.iDao.provider;


import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.BusinessinfoVO;


public interface IBusinessinfoDao {

	/**
	 * @param objBusinessinfoVO
	 * @return
	 */
	public BusinessinfoVO save(BusinessinfoVO objBusinessinfoVO);
	
	public BusinessinfoVO getData(BusinessinfoVO objBusinessinfoVO);
	public String getCompanySizeDesc(Integer id);
	
	public BusinessinfoVO updateVendorHdr(BusinessinfoVO objBusinessinfoVO) throws DBException;

	public BusinessinfoVO updateVendorAddressDetails(BusinessinfoVO objBusinessinfoVO) throws DBException;

	public BusinessinfoVO updateVendorFinance(BusinessinfoVO objBusinessinfoVO)throws DBException;

	public BusinessinfoVO updateVendorResource(BusinessinfoVO objBusinessinfoVO)throws DBException;
	
	public BusinessinfoVO updateW9ForVendorHdr(BusinessinfoVO objBusinessinfoVO) throws DBException;
	
}
