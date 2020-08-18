package com.newco.marketplace.business.businessImpl.buyer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerSubstatusAssocBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IIncidentBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.buyer.BuyerSubstatusAssocVO;
import com.newco.marketplace.dto.vo.incident.IncidentTrackingVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerSubstatusAssocDAO;

public class BuyerSubstatusAssocBO implements IBuyerSubstatusAssocBO {
	
	private static final Logger logger = Logger.getLogger(BuyerSubstatusAssocBO.class);
	
	private IBuyerSubstatusAssocDAO buyerSubstatusDAO;
	private IIncidentBO incidentBO;
	private IServiceOrderBO serviceOrderBO;
	private IBuyerBO buyerBO;
	

	public Map<String, List> getBuyerSubstatus(Integer buyerID, Integer statusId, Integer substatus) {
		List<BuyerSubstatusAssocVO> buyerSubstatusAssocVOList = null;
		List<String> statusList = new ArrayList<String>();
		List<String> commentsList = new ArrayList<String>();
		List<Integer> buyerSubstatusAssocIdList = new ArrayList<Integer>();
		Map<String, List> resultMap = new HashMap<String, List>();
		try {
			if (buyerID != null && statusId != null) {
				buyerSubstatusAssocVOList = buyerSubstatusDAO.getBuyerStatus(buyerID, statusId, substatus, null);
				for(BuyerSubstatusAssocVO buyersubstatus : buyerSubstatusAssocVOList){
					statusList.add(buyersubstatus.getBuyerStatus());
					commentsList.add(buyersubstatus.getComments());
					buyerSubstatusAssocIdList.add(buyersubstatus.getBuyerSubStatusAssocId());
				}
			}
			resultMap.put(OrderConstants.BUYER_STATUS, statusList);
			resultMap.put(OrderConstants.COMMENTS, commentsList);
			resultMap.put(OrderConstants.BUYER_SUBSTATUS_ASSOC_ID, buyerSubstatusAssocIdList);
			
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resultMap;
	}
	
	public List<BuyerSubstatusAssocVO> getBuyerSubstatusAssoc(Integer buyerID, Integer statusId, Integer substatus, String buyerSubstatus) {
		List<BuyerSubstatusAssocVO> buyerSubstatusAssocVOList = null;
		try {
			if (buyerID != null && statusId != null) {
				buyerSubstatusAssocVOList = buyerSubstatusDAO.getBuyerStatus(buyerID, statusId, substatus, buyerSubstatus);
			}
		}
		catch (Exception e) {
			logger.error("error occurred in BuyerSubstatusAssocBO.getBuyerSubstatusAssoc" + e.getMessage());
		}
		return buyerSubstatusAssocVOList;
	}
	
	public boolean getSkipAlertFlag(Integer buyerID, Integer statusId, Integer substatus, String soId) {
		boolean skipAlert = false;
		String incidentId = null;
		try{
			List<BuyerSubstatusAssocVO> buyerSubstatusAssocVOList = getBuyerSubstatusAssoc(buyerID, statusId, substatus, null);
			for(BuyerSubstatusAssocVO buyerAssoc: buyerSubstatusAssocVOList){
				if(buyerAssoc.getUpdateCount()!= null && buyerAssoc.getUpdateCount().intValue()>0){
					IncidentTrackingVO incidentVO = new IncidentTrackingVO();
					incidentId = getIncidentId(soId, buyerID);				
					incidentVO.setBuyerSubstatusAssocId(buyerAssoc.getBuyerSubStatusAssocId());
					incidentVO.setIncidentId(new Integer(incidentId));
					incidentVO.setSoId(soId);
					
					List<IncidentTrackingVO>  incidentVOList = incidentBO.getIncidentHistoryRecords(incidentVO);
					if(incidentVOList!= null && incidentVOList.size() >=  buyerAssoc.getUpdateCount()){
						skipAlert = true;
					}
				}
			}
		}catch(Exception e){
			logger.error("error occurred in BuyerSubstatusAssocBO.getSkipAlertFlag due to "+ e.getMessage());
		}
		
		return skipAlert;
	}
	
	public String getIncidentId(String soId, Integer buyerID){
		String incidentId = null;
		try{
				ServiceOrderCustomRefVO customRef = serviceOrderBO.getCustomRefValue(OrderConstants.SL_INCIDENT_REFERNECE_KEY, soId);
				incidentId = customRef.getRefValue();
		}catch(Exception e){
			logger.error("error occurred while getting incidentId " + e.getMessage());
		}
		return incidentId;
	}


	public IBuyerSubstatusAssocDAO getBuyerSubstatusDAO() {
		return buyerSubstatusDAO;
	}

	public void setBuyerSubstatusDAO(IBuyerSubstatusAssocDAO buyerSubstatusDAO) {
		this.buyerSubstatusDAO = buyerSubstatusDAO;
	}


	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public IBuyerBO getBuyerBO() {
		return buyerBO;
	}

	public void setBuyerBO(IBuyerBO buyerBO) {
		this.buyerBO = buyerBO;
	}

	public IIncidentBO getIncidentBO() {
		return incidentBO;
	}

	public void setIncidentBO(IIncidentBO incidentBO) {
		this.incidentBO = incidentBO;
	}




}
