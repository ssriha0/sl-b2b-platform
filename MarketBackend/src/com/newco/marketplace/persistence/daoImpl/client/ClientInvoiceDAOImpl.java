package com.newco.marketplace.persistence.daoImpl.client;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.dto.vo.client.ClientInvoiceOrderVO;
import com.newco.marketplace.persistence.iDao.client.IClientInvoiceDAO;

public class ClientInvoiceDAOImpl extends SqlMapClientDaoSupport implements IClientInvoiceDAO {

	public void saveClientInvoice(ClientInvoiceOrderVO clientInvoiceOrderVO) {
		try {
	        getSqlMapClient().insert("clientInvoiceOrder.insert", clientInvoiceOrderVO);
	
	    }
	    catch (Exception ex) {
	        logger.error(ex.getMessage(), ex);
	    }
	}

	public Integer getClientIDForName(String clientName) {
		Integer clientID = null;
		try {
	        clientID = (Integer) getSqlMapClient().queryForObject("clientInvoiceOrder.getClientIDForName", clientName);
	    }
	    catch (Exception ex) {
	        logger.error(ex.getMessage(), ex);
	    }
	    return clientID;
	}

	public ClientInvoiceOrderVO getClientInvoiceForSoID(String soID) {
		try {
			return (ClientInvoiceOrderVO) getSqlMapClient().queryForObject("clientInvoiceOrder.getClientInvoiceBySOID", soID);
		} catch (SQLException ex) {
			logger.error(ex.getMessage(), ex);
		}
		return null;
	}
	
	
	public ClientInvoiceOrderVO getClientInvoiceForIncident(String incidentId, int skuId) {
		try{
			Map paramMap = new HashMap<Integer, Object>();
			paramMap.put("incidentId", StringUtils.trim(incidentId));
			paramMap.put("skuId", new Integer(skuId));
			
			return (ClientInvoiceOrderVO) getSqlMapClient().queryForObject("clientInvoiceOrder.getClientInvoiceByIncidentID", paramMap);
		} catch (Exception e){
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	

}
