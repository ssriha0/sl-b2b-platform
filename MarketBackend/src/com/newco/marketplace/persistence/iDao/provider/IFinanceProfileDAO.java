/**
 * 
 */
package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.FinanceProfile;

public interface IFinanceProfileDAO {
	public void update(FinanceProfile finProfile) throws DBException;

	public FinanceProfile query(FinanceProfile finProfile) throws DBException;

	public void insert(FinanceProfile finProfile) throws DBException;

	public List queryList(FinanceProfile finProfile) throws DBException;

}
