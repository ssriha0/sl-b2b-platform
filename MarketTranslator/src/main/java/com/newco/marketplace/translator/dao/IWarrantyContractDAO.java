package com.newco.marketplace.translator.dao;

import java.util.List;

public interface IWarrantyContractDAO {
	
	public void save(WarrantyContract entity);

	public WarrantyContract update(WarrantyContract entity);

	public WarrantyContract findById(Integer id);

	public List<WarrantyContract> findByProperty(String propertyName, Object value);

	public List<WarrantyContract> findByModifiedBy(Object modifiedBy);

	public List<WarrantyContract> findAll();

	public WarrantyContract findByClientAndContract(Client client, String contractNumber);

}
