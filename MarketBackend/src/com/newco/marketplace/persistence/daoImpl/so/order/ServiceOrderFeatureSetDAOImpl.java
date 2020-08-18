package com.newco.marketplace.persistence.daoImpl.so.order;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderFeatureVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.so.order.IServiceOrderFeatureSetDAO;

public class ServiceOrderFeatureSetDAOImpl extends SqlMapClientDaoSupport implements
		IServiceOrderFeatureSetDAO {
	
	private static final Logger logger = Logger.getLogger(ServiceOrderFeatureSetDAOImpl.class);

	public String getFeature(String soID, String feature)
			throws DataServiceException {
		String foundFeature = null;
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("soID", soID);
			params.put("feature", feature);
			foundFeature = (String) getSqlMapClient().queryForObject(
					"serviceOrderFeatureSet.getFeature", params);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @ServiceOrderFeatureSetDAOImpl.getFeature() due to"
							+ ex.getMessage());
			throw new DataServiceException(
					"SQL Exception @ServiceOrderFeatureSetDAOImpl.getFeature() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @ServiceOrderFeatureSetDAOImpl.getFeature() due to"
							+ ex.getMessage());
			throw new DataServiceException(
					"General Exception @ServiceOrderFeatureSetDAOImpl.getFeature() due to "
							+ ex.getMessage());
		}
		return foundFeature;
	}
	
	public void setFeature(String soID, String feature) throws DataServiceException {
		if (getFeature(soID, feature) == null) {
			try {
				ServiceOrderFeatureVO featureVO = new ServiceOrderFeatureVO();
				featureVO.setSoId(soID);
				featureVO.setFeature(feature);
				getSqlMapClient().insert("serviceOrderFeatureSet.insertFeature", featureVO);
			} catch (SQLException ex) {
				ex.printStackTrace();
				logger
						.info("SQL Exception @ServiceOrderFeatureSetDAOImpl.setFeature() due to"
								+ ex.getMessage());
				throw new DataServiceException(
						"SQL Exception @ServiceOrderFeatureSetDAOImpl.setFeature() due to "
								+ ex.getMessage());
			} catch (Exception ex) {
				ex.printStackTrace();
				logger
						.info("General Exception @ServiceOrderFeatureSetDAOImpl.setFeature() due to"
								+ ex.getMessage());
				throw new DataServiceException(
						"General Exception @ServiceOrderFeatureSetDAOImpl.setFeature() due to "
								+ ex.getMessage());
			}
		}
	}

}
