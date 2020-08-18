package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.LuActivity;

/**
 * @author sahmad7
 *
 */
public interface ILuActivityDao 
{
    public LuActivity query(LuActivity luActivity) throws DBException;
    public List queryList(LuActivity luActivity) throws DBException;

}
