package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.vo.provider.InsuranceType;

public interface IInsuranceTypeDao 
{
    public  List queryList(String str,InsuranceType insuranceType) throws DataAccessException;

}
