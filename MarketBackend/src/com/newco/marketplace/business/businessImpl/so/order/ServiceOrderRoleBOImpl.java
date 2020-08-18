package com.newco.marketplace.business.businessImpl.so.order;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.so.order.ServiceOrderRoleBO;
import com.newco.marketplace.dto.vo.RoleVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderRole;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTabResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderWfStatesCountsVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class ServiceOrderRoleBOImpl implements ServiceOrderRoleBO{
	private static final Logger logger = Logger.getLogger(ServiceOrderRoleBO.class.getName());	
	private ServiceOrderDao sodao;

	public ServiceOrderDao getSodao() {
		return sodao;
	}

	public void setSodao(ServiceOrderDao sodao) {
		this.sodao = sodao;
	}
	
	public ProcessResponse process(RoleVO role){
		
		ProcessResponse processResponse = new ProcessResponse();
		ArrayList<ServiceOrderRole> sorole = new ArrayList();
		
		try{
			
			if(role.getRoleName().equalsIgnoreCase("provider")){
			
				sorole = getSodao().queryListProvider(role);			
				processResponse.setObj(sorole);
				return processResponse;
				
			} else if (role.getRoleName().equalsIgnoreCase("buyer")){
				
				sorole = getSodao().queryListBuyer(role);
				processResponse.setObj(sorole);
				return processResponse;
			}
		
		} catch (Exception e){
			
			e.printStackTrace();
		}
		return null;

	}
	
	public ArrayList getList(RoleVO role){
		
		//ProcessResponse processResponse = new ProcessResponse();
		ArrayList<ServiceOrderRole> sorole = new ArrayList();
		
		try{
			
			if(role.getRoleName().equalsIgnoreCase("provider")){
			
				sorole = getSodao().queryListProvider(role);			
				return sorole;
				
			} else if (role.getRoleName().equalsIgnoreCase("buyer")){
				
				sorole = getSodao().queryListBuyer(role);
				return sorole;
			}
		
		} catch (Exception e){
			
			e.printStackTrace();
		}
		return sorole;

	}
	
	public ArrayList<ServiceOrderWfStatesCountsVO> getTabs(AjaxCacheVO ajaxCacheVo){
		logger.debug("Entering getTabs from ServiceOrderRoleBO");
		ServiceOrderWfStatesCountsVO soOrderCounts = new ServiceOrderWfStatesCountsVO();
		ArrayList<ServiceOrderWfStatesCountsVO> soOrderCountsList = new ArrayList();
		
		try{
			
			if(ajaxCacheVo.getRoleType().equalsIgnoreCase(OrderConstants.PROVIDER)){
			
				soOrderCountsList = (ArrayList) getSodao().queryListSOWfStatesProviderCounts(ajaxCacheVo);
				
			} else if (ajaxCacheVo.getRoleType().equalsIgnoreCase(OrderConstants.BUYER)){
				
				soOrderCountsList = (ArrayList) getSodao().queryListSOWfStatesBuyerCounts(ajaxCacheVo);				

			} else if (ajaxCacheVo.getRoleType().equalsIgnoreCase(OrderConstants.SIMPLE_BUYER)){
				
				soOrderCountsList = (ArrayList) getSodao().queryListSOWfStatesBuyerCounts(ajaxCacheVo);				
	
			}
		
		} catch(DataServiceException dse){
			logger.error("Error in retrieving data for this user", dse);
		}
		catch (Exception e){
			logger.error("Caught a general exception", e);
		}
		
		return soOrderCountsList;
	}
	
	//TODO: SC- REMOVE IT
	public ArrayList<ServiceOrderTabResultsVO> getReceivedTabData(LoginCredentialVO lvo){
		
		ArrayList<ServiceOrderTabResultsVO> soSearchResults = new ArrayList<ServiceOrderTabResultsVO>();
		try{
			
			if(lvo.getRoleName().equalsIgnoreCase(OrderConstants.PROVIDER)){
			
				soSearchResults = (ArrayList<ServiceOrderTabResultsVO>) sodao.getSOProviderReceived(lvo.companyId);
				
			}
		
		} catch(DataServiceException dse){
			logger.error("Error in retrieving received tab data for this user: " + lvo.getCompanyId(), dse);
		}
		catch (Exception e){
			logger.error("Caught a general exception", e);
		}
		return soSearchResults;		
		
	}

    public ProcessResponse getSONotes(String soId) {
        // TODO Auto-generated method stub
        return null;
    }

    public ProcessResponse process(Integer buyerId, ServiceOrderNote note) {
        // TODO Auto-generated method stub
        return null;
    }

}
