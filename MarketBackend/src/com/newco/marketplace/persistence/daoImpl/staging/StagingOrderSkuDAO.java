package com.newco.marketplace.persistence.daoImpl.staging;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.staging.StagingOrderSkuVO;
import com.newco.marketplace.dto.vo.staging.StagingOrderVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.staging.IStagingOrderSkuDAO;
import com.sears.os.dao.impl.ABaseImplDao;

public class StagingOrderSkuDAO  extends ABaseImplDao  implements IStagingOrderSkuDAO
{
	
	private static final Logger logger = Logger.getLogger(StagingOrderSkuDAO.class);
	
	public StagingOrderSkuVO getOrderSkuByOrderNumber( Integer orderNumber )throws DataServiceException
	{
		return (StagingOrderSkuVO) queryForObject( "staging_order_sku.select_by_order_number", orderNumber );
	}
	
	public StagingOrderVO getOrderByServiceOrderID( String soID )throws DataServiceException
	{
		return (StagingOrderVO) queryForObject( "staging_order.select_by_so_id", soID );
	}

	public Integer getOrderIDByServiceOrderID( String soID )throws DataServiceException
	{
		return (Integer) queryForObject( "staging_order_id.select_by_so_id", soID );
	}
	
	
	public List<StagingOrderSkuVO> getOrderSkusByOrderID(Integer orderID) throws DataServiceException
	{
		return (List<StagingOrderSkuVO>) queryForList("staging_order_sku.select_list_by_order_id", orderID);
	}
	
	
	
	public void updateOrderSkuFinalPrice( StagingOrderSkuVO vo ) throws DataServiceException
	{
		update( "staging_order_sku.update_final_price", vo );	
	}

	public void insertOrder(StagingOrderVO orderVO) throws DataAccessException
	{
		try
		{
			insert("staging_order.insert",orderVO);
		}
		catch(DataAccessException de)
		{
			de.printStackTrace();
			throw  de;
		}
	}
	
	public void insertOrderSku(StagingOrderSkuVO orderSkuVO) throws DataAccessException
	{
		try
		{
			insert("staging_order_sku.insert",orderSkuVO);
		}
		catch(DataAccessException de)
		{
			de.printStackTrace();
			throw  de;		
		}
	}

	// For junit testing.  Need to get a single live/existing service order id
	public String getFirstServiceOrderID()throws DataServiceException
	{
		return (String) queryForObject( "so_hdr.select_first", null);
	}
	
	
    public void deleteOrder(String soID) throws DataServiceException {
        delete("staging_order.delete", soID);
    }
	
    public void deleteOrderSku(Integer orderNumber) throws DataServiceException {
        delete("staging_order_sku.delete", orderNumber);
    }
    
    
	
}
