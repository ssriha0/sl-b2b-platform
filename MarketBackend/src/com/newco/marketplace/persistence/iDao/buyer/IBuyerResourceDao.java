package com.newco.marketplace.persistence.iDao.buyer;

import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.BuyerResource;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.DBException;
public interface IBuyerResourceDao {
	/**
	 * Inserts a buyer resource into the datastore
	 * 
	 * @param buyerResource
	 * @return @
	 */
	public BuyerResource insert(BuyerResource buyerResource)
			throws DBException;
	
	 public BuyerResource getQueryByBuyerId(int buyerId) throws DBException;
	 public  List<Contact>  getBuyerContactInfoFromBuyerId(int buyerId) throws DBException;
	 public BuyerResource getBuyerResourceInfo(int buyerResourceId) throws DBException;
}

