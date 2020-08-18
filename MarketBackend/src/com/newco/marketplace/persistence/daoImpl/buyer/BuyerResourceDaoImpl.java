package com.newco.marketplace.persistence.daoImpl.buyer;

import java.sql.SQLException;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.dto.vo.serviceorder.BuyerResource;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerResourceDao;

public class BuyerResourceDaoImpl extends SqlMapClientDaoSupport implements IBuyerResourceDao{
	
	public BuyerResource insert(BuyerResource buyerResource) throws DBException {
		Integer id = null;

        try {
            id = (Integer)getSqlMapClient().insert("buyerResource.insert", buyerResource);
            buyerResource.setResourceId(Integer.parseInt(id.toString()));
        }
        catch (SQLException ex) {
             logger.info("SQL Exception @BuyerResourceImpl.insert() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @BuyerResourceImpl.insert() due to "+ex.getMessage());
        }catch (Exception ex) {
             logger.info("General Exception @BuyerResourceImpl.insert() due to"+ex.getMessage());
		     throw new DBException("General Exception @BuyerResourceImpl.insert() due to "+ex.getMessage());
        }

        return buyerResource;
	}
	
	/**
	 * get the resource details by buyer id for prim ind 1
	 * @param buyerId
	 * @return
	 * @throws DBException
	 */
	//Story# 27185 - Finance Receive Buyer Payments NACHA file
	 public BuyerResource getQueryByBuyerId(int buyerId) throws DBException
	    {
		 BuyerResource result =null;
	    	try{
	    		result= (BuyerResource) getSqlMapClient().queryForObject("BuyerResourceByBuyerId.queryP", buyerId);
	    	}catch (SQLException ex) {
	            ex.printStackTrace();
			     logger.info("SQL Exception @BuyerResourceDaoImpl.getQueryByBuyerId() due to"+ex.getMessage());
			     throw new DBException("SQL Exception @BuyerResourceDaoImpl.getQueryByBuyerId() due to "+ex.getMessage());
	    	}catch (Exception ex) {
	           ex.printStackTrace();
			     logger.info("General Exception @BuyerResourceDaoImpl.getQueryByBuyerId() due to"+ex.getMessage());
			     throw new DBException("General Exception @BuyerResourceDaoImpl.getQueryByBuyerId() due to "+ex.getMessage());
	      }
	    	return result;

	    }
	 
	 public List<Contact> getBuyerContactInfoFromBuyerId(int buyerId) throws DBException
	 {
			try{

				return (List<Contact>) getSqlMapClient().queryForList("buyerContactInfoFromBuyerId.select", buyerId);
	    	}catch (Exception ex) {
			     logger.info("General Exception @BuyerResourceDaoImpl.getBuyerContactInfoFromBuyerId() due to"+ex.getMessage());
			     throw new DBException("General Exception @BuyerResourceDaoImpl.getBuyerContactInfoFromBuyerId() due to "+ex.getMessage());
		    }
	 
	 }
	 
	 public BuyerResource getBuyerResourceInfo(int buyerResourceId) throws DBException {
		 try{
				return (BuyerResource) getSqlMapClient().queryForObject("buyerResourceInfo.select", buyerResourceId);
	    	}catch (Exception ex) {
			     logger.error("General Exception @BuyerResourceDaoImpl.getBuyerContactInfoFromBuyerId() due to"+ex.getMessage());
			     throw new DBException("General Exception @BuyerResourceDaoImpl.getBuyerContactInfoFromBuyerId() due to "+ex.getMessage());
		    }
	 }
	 
}
