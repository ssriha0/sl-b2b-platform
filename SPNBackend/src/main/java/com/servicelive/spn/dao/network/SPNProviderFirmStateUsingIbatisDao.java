/**
 * 
 */
package com.servicelive.spn.dao.network;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;
import com.servicelive.spn.auditor.vo.SPNMeetAndGreetStateVO;
import com.servicelive.spn.auditor.vo.SPNProviderFirmStateOverride;
import com.servicelive.spn.auditor.vo.SPNProviderFirmStateVO;
import com.servicelive.spn.auditor.vo.SPNServiceProviderStateOverride;
import com.servicelive.spn.auditor.vo.SPNServiceProviderStateVO;
import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.services.BaseServices;

/**
 * @author madhup_chand
 *
 */
public class SPNProviderFirmStateUsingIbatisDao  extends ABaseImplDao {


	
	//SL 18780 Adding method for performance tuning
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateSPNProviderFirmState(SPNProviderFirmStateVO sPNProviderFirmStateVO)
	{
		logger.info("SL 18780 Inside updateSPNProviderFirmState method");
		try {
		  update("spnProviderFirmStateUpdate.update",sPNProviderFirmStateVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateAuditRequiredInd(Integer currentSpnId,String oldMarketState,Boolean auditRequired)
	{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("currentSpnId", currentSpnId);
			map.put("oldMarketState", oldMarketState);
			map.put("auditRequired", auditRequired);
			getSqlMapClient().update("spnUpdateAuditRequiredInd.update",map);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateSPNId(Integer currentSpnId,Integer aliasSpnId,List<String> memberStates)
	{
		try {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentSpnId", currentSpnId);
		map.put("aliasSpnId", aliasSpnId);
		map.put("memberStates", memberStates);
		getSqlMapClient().update("spnUpdateSPNId.update",map);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertProviderFirmsForAliasSPN(List<SPNProviderFirmStateVO> providerFirms)
	{
		try {
			splitInsert("providerFirmsforAliasSPN.insert", providerFirms);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	private void splitInsert(String query, List<SPNProviderFirmStateVO> spnProviderFirmList) throws DataServiceException{
        try{
            int noOfRecords = (spnProviderFirmList==null?0:spnProviderFirmList.size());
            int noOfIter = 0;
            //Setting default split count to 100 in case SPN_EDIT_SPLIT_INSERT_COUNT not set 
            int count=100;
            String splitCount=getSPNEditPropertyFromDB(SPNConstants.SPN_EDIT_SPLIT_INSERT_COUNT);
            logger.info("Split count from application property"+splitCount);
            if(null!=splitCount){
            	 count =Integer.parseInt(splitCount);	
            }
            if(noOfRecords>0){
                noOfIter = noOfRecords/count;
                if(noOfIter==0){
                    count = noOfRecords;
                    noOfIter =1;
                }
            }
            int loopCount = 0;
            while(noOfIter!=0){
                int endIndex = (loopCount+1)*count;
                if(noOfIter==1){
                    endIndex = spnProviderFirmList.size();
                }
                List<SPNProviderFirmStateVO> firms = spnProviderFirmList.subList(loopCount*count, endIndex);
                insert(query, firms);
               
                loopCount++;
                noOfIter--;
            }
        }catch (Exception dse){
           
        }
    }
	
	@Transactional(propagation = Propagation.REQUIRED)
	private void splitInsertForServiceProvider(String query, List<SPNServiceProviderStateVO> spnServiceProviderList) throws DataServiceException{
        try{
            int noOfRecords = (spnServiceProviderList==null?0:spnServiceProviderList.size());
            int noOfIter = 0;           
            //Setting default split count to 100 in case SPN_EDIT_SPLIT_INSERT_COUNT not set 
            int count=100;
            String splitCount=getSPNEditPropertyFromDB(SPNConstants.SPN_EDIT_SPLIT_INSERT_COUNT);
            logger.info("Split count from application property"+splitCount);
            if(null!=splitCount){
            	 count =Integer.parseInt(splitCount);	
            }
            if(noOfRecords>0){
                noOfIter = noOfRecords/count;
                if(noOfIter==0){
                    count = noOfRecords;
                    noOfIter =1;
                }
            }
            int loopCount = 0;
            while(noOfIter!=0){
                int endIndex = (loopCount+1)*count;
                if(noOfIter==1){
                    endIndex = spnServiceProviderList.size();
                }
                List<SPNServiceProviderStateVO> serviceProvider = spnServiceProviderList.subList(loopCount*count, endIndex);
                insert(query, serviceProvider);
               
                loopCount++;
                noOfIter--;
            }
        }catch (Exception dse){
           
        }
    }
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateWfStateOnly(List<String> states,String newWfState,Integer currentSpnId)
	{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("currentSpnId", currentSpnId);
			map.put("states", states);
			map.put("newWfState", newWfState);
			getSqlMapClient().update("spnUpdateWfStateOnly.update",map);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertServiceProviderForAliasSPN(List<SPNServiceProviderStateVO> serviceProvider)
	{
		try {
			logger.info("Inside insertServiceProviderForAliasSPN with total service providers "+serviceProvider.size());
			splitInsertForServiceProvider("serviceProviderforAliasSPN.insert", serviceProvider);
			logger.info("Successfully inserted entry for Service Provider");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SPNProviderFirmStateVO> findProviderFirmBySpnIdAndSPNWorkFlowStates(Integer currentSpnId,List<String> memberStates)
	{ List<SPNProviderFirmStateVO> spnProviderFirmrVOList=new ArrayList<SPNProviderFirmStateVO>();
	
	try {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentSpnId", currentSpnId);
		map.put("memberStates", memberStates);
		spnProviderFirmrVOList =  queryForList("spnFetchProivderFirmState.query", map);
	} catch (Exception ex) {
		logger.info("[ServiceOrderDaoImpl.queryList - Exception] "
				+ StackTraceHelper.getStackTrace(ex));
		
	}
		return spnProviderFirmrVOList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SPNServiceProviderStateVO> findSericeProviderBySpnIdAndSPNWorkFlowStates(Integer currentSpnId,List<String> memberStates)
	{
		List<SPNServiceProviderStateVO> spnServiceProviderVOList=new ArrayList<SPNServiceProviderStateVO>();
		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("currentSpnId", currentSpnId);
			map.put("memberStates", memberStates);
			spnServiceProviderVOList =  queryForList("spnFetchSericeProviderState.query", map);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.queryList - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			
		}
			return spnServiceProviderVOList;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SPNMeetAndGreetStateVO> findMeetAndGreetCriteriaBySpnId(Integer currentSpnId)
	{
		List<SPNMeetAndGreetStateVO> meetAndGreeFirmList=new ArrayList<SPNMeetAndGreetStateVO>();
		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("currentSpnId", currentSpnId);
			meetAndGreeFirmList =  queryForList("spnFetchMeetAndGreetBySpnId.query", map);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.queryList - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			
		}
			return meetAndGreeFirmList;
		
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertMeetAndGreetCriteriaForAliasSPN(List<SPNMeetAndGreetStateVO> meetNGreetCopies)
	{
		try {
			logger.info("Inside insertMeetAndGreetCriteriaForAliasSPN with total provider firms "+meetNGreetCopies.size());
			insert("insertMeetAndGreetforAliasSPN.insert", meetNGreetCopies);
			logger.info("Successfully inserted entry for Service Provider in  MeetAndGreet");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteMeetAndGreet(Integer currentSpnId)
	{
		logger.info("Inside deleteMeetAndGreet method");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("currentSpnId", currentSpnId);
			logger.info("Inside deleteMeetAndGreet method with spn id"+currentSpnId);
			delete("deleteMeetAndGreetBySpnId.delete", map);
			logger.info("Successfully deleted entry from meetAndGreet table with spn id"+currentSpnId);
		} catch (Exception ex) {
			logger.info("[ServiceOrderDaoImpl.queryList - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			
		}
	}
	
	//get value from application_properties for a key
		public String getSPNEditPropertyFromDB(String appKey)	throws DataServiceException
		{
		String value=(String) queryForObject("getSPNEditAppKeyValue.query",appKey);
		return value;
		}

		public void insertSpnetProviderFirmNetworkOverride(Integer currentSpnId, Integer aliasSpnId) {
			List<SPNProviderFirmStateOverride> spnProviderFirmStateOverride = null;
			try {
				spnProviderFirmStateOverride =  queryForList("spnFetchProviderFirmStateOverride.query", currentSpnId);
				if(null != spnProviderFirmStateOverride && !spnProviderFirmStateOverride.isEmpty()){
					for(SPNProviderFirmStateOverride providerFirmStateOverride : spnProviderFirmStateOverride){
						providerFirmStateOverride.setSpnId(aliasSpnId);
					}
					insert("insertProviderFirmStateOverride.insert", spnProviderFirmStateOverride);
					update("updateProviderFirmStateOverride.update",currentSpnId);
				}
			} catch (Exception ex) {
				logger.info("[ServiceOrderDaoImpl.queryList - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				
			}
		}

		public void insertSpnetProviderNetworkOverride(Integer currentSpnId, Integer aliasSpnId) {
			List<SPNServiceProviderStateOverride> spnServiceProviderStateOverride = null;
			try {
				spnServiceProviderStateOverride =  queryForList("spnFetchServiceProviderStateOverride.query", currentSpnId);
				if(null != spnServiceProviderStateOverride && !spnServiceProviderStateOverride.isEmpty())
				for(SPNServiceProviderStateOverride serviceProviderStateOverride : spnServiceProviderStateOverride){
					serviceProviderStateOverride.setSpnId(aliasSpnId);
				}
				insert("insertServiceProviderStateOverride.insert", spnServiceProviderStateOverride);
				update("updateProviderStateOverride.update",currentSpnId);
			} catch (Exception ex) {
				logger.info("[ServiceOrderDaoImpl.queryList - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				
			}
		}
		

}
