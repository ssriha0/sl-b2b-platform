package com.newco.marketplace.web.delegates.provider;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.web.dto.provider.InsuranceInfoDto;

/**
 * @author LENOVO USER
 *
 */
public interface IInsuranceDelegate {
	public InsuranceInfoDto getInsuranceInfo(InsuranceInfoDto insuranceInfoDto) throws DelegateException;
	public boolean saveInsuranceInfo(InsuranceInfoDto insuranceInfoDto) throws DelegateException;
	public InsuranceInfoDto getInsuranceType(InsuranceInfoDto insuranceInfoDto) throws DelegateException;
	public String isFirstTimeVisit(Integer vendorId) throws DelegateException;
	public boolean removeInsuranceInfo(InsuranceInfoDto insuranceInfoDto) throws DelegateException;
}
