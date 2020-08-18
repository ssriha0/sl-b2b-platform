package com.newco.marketplace.business.iBusiness.so.order;

import java.util.ArrayList;

import com.newco.marketplace.dto.vo.RoleVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTabResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderWfStatesCountsVO;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public interface ServiceOrderRoleBO {
	
	public ProcessResponse process(RoleVO role);	
	public ArrayList getList(RoleVO role);

	public ArrayList<ServiceOrderWfStatesCountsVO> getTabs(AjaxCacheVO ajaxCacheVo);
	public ArrayList<ServiceOrderTabResultsVO> getReceivedTabData(LoginCredentialVO lvo);
}
