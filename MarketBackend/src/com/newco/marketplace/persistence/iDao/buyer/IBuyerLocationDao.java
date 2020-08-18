package com.newco.marketplace.persistence.iDao.buyer;
import java.util.List;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.buyer.BuyerLocation;
/**
 * @author paugus2
 */
public interface IBuyerLocationDao
{
    public int update(BuyerLocation buyerLocation) throws DBException;
    public BuyerLocation query(BuyerLocation buyerLocation)throws DBException;
    public BuyerLocation insert(BuyerLocation buyerLocation)throws DBException;
    public List queryList(BuyerLocation buyerLocation)throws DBException;
}