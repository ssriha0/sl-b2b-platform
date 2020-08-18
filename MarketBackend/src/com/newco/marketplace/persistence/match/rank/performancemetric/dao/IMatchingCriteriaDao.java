package com.newco.marketplace.persistence.match.rank.performancemetric.dao;

import java.util.List;
import java.util.Map;

public interface IMatchingCriteriaDao {

	List<String> fetchProviderResourceListByProviderId(Integer firmId);

	void saveOrUpdateMatchingCriteria(String buyerID, Integer firmID, Integer score);

}
