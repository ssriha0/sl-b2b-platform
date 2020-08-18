package com.newco.marketplace.business.iBusiness.serviceorder;


import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;

public interface ISOAjaxBO {
	public String getSummaryCounts (AjaxCacheVO ajaxCacheVO);
	public String getDetailedCounts (AjaxCacheVO ajaxCacheVO);
}
