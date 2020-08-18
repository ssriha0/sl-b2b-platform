package com.newco.marketplace.persistence.iDao.provider;

import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.vo.provider.InsuranceTypesVO;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;

public interface IInsuranceTypesDao {
	public List queryListForInsuranceTypes(InsuranceTypesVO insuranceTypesVO) throws DBException;
	public HashMap getAdditionalMapCategory(CredentialProfile cProfile );
	public List getAdditionalInsuranceList(CredentialProfile cProfile);

}
