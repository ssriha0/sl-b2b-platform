package com.servicelive.marketplatform.dao;

import java.util.List;
import java.util.Map;


import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ServiceProvider;
import com.newco.marketplace.dto.vo.providerSearch.ProviderDetailsVO;

public interface IProviderDao extends INotificationEntityDao {
    ServiceProvider findProviderResourceById(long providerRsrcId);
    ProviderFirm findProviderById(long providerId);
    ServiceProvider  findProviderAdminResource(long providerId);
	List<ServiceProvider> findProviderResourcesByIds(List<Integer> resourceIds);
	List<ProviderDetailsVO> findProviderResourcesDetailsByIds(
			List<Integer> resourceIds);
}
