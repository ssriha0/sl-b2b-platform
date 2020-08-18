package com.newco.marketplace.persistence.iDao.staging;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.staging.StagingOrderSkuVO;
import com.newco.marketplace.dto.vo.staging.StagingOrderVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface IStagingOrderSkuDAO
{
	public StagingOrderSkuVO getOrderSkuByOrderNumber( Integer orderNumber )throws DataServiceException;
	public List<StagingOrderSkuVO> getOrderSkusByOrderID(Integer orderID) throws DataServiceException;	
	public StagingOrderVO getOrderByServiceOrderID( String soID )throws DataServiceException;
	public Integer getOrderIDByServiceOrderID( String soID )throws DataServiceException;
	public void updateOrderSkuFinalPrice( StagingOrderSkuVO vo ) throws DataServiceException;
	
    public void deleteOrder(String soID) throws DataServiceException;	
    public void deleteOrderSku(Integer orderNumber) throws DataServiceException;
    
	public void insertOrder(StagingOrderVO orderVO) throws DataAccessException;
	public void insertOrderSku(StagingOrderSkuVO orderSkuVO) throws DataAccessException;
	
	public String getFirstServiceOrderID()throws DataServiceException; // For junit testing
	
}
