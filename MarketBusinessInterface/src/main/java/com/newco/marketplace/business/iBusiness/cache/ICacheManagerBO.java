package com.newco.marketplace.business.iBusiness.cache;

import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.cache.CachedResultVO;
import com.newco.marketplace.dto.vo.cache.event.CacheEvent;
import com.newco.marketplace.exception.core.BusinessServiceException;


public interface ICacheManagerBO {

	public CachedResultVO getSummaryCounts(AjaxCacheVO ajaxCacheVO) throws BusinessServiceException;
	public CachedResultVO getDetailedCounts(AjaxCacheVO ajaxCacheVO) throws BusinessServiceException;
	public CachedResultVO getDashboardData(AjaxCacheVO ajaxCacheVO) throws BusinessServiceException;
	public CachedResultVO getAllSOMCounts(AjaxCacheVO ajaxCacheVo) throws BusinessServiceException;
	public void handleEvent(CacheEvent ce);

}
