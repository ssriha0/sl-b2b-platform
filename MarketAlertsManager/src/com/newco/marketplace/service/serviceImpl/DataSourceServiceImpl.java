/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-Apr-2010	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.beans.EmailDataSource;
import com.newco.marketplace.business.DataSourceBO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.service.DataSourceService;
import com.newco.servicelive.campaigns.service.BaseService;
import com.newco.servicelive.campaigns.service.ResponsysDataSourceServiceImpl;

/**
 * This class is for populating data in Responsys datasource.
 * 
 * @author Infosys
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DataSourceServiceImpl extends BaseService implements DataSourceService{
	private DataSourceBO dataSourceBO;
	private ResponsysDataSourceServiceImpl providerDataSourceService;
	private boolean responsysEnableMode;
	private static final Logger logger = Logger.getLogger(DataSourceServiceImpl.class.getName());
	
	/**
	 * This method fetches records from email_datasource table,executes dataSourceSQL,
	 * fetches data from DB and populates Responsys datasource
	 * 
	 * @return void
	 */
	public void processDataSources( String name) throws DataServiceException {

		logger.info("Entering DataSourceServiceImpl.processDataSources()");
		try {
			if (isResponsysEnableMode()) {	
				String schedulerName=name;
				// Fetch data source rows from the table for the given scheduler name
				ArrayList<EmailDataSource> emailDataSourceList = (ArrayList<EmailDataSource>) getDataSourceBO().getDataSources(schedulerName);
			
				for (EmailDataSource dataSource:emailDataSourceList) {			
					List<HashMap<String, String>> dataList = dataSourceBO.fetchData(dataSource.getDataSourceSQL());
					if(null!=dataList&&dataList.size()>0){
						try{
							getProviderDataSourceService().appendRemoteDataSource(dataList,dataSource.getFolder(), dataSource.getDataSource(), dataSource.getParameters().split(","), dataSource.getDataSource());
							dataSourceBO.executePostProcessor(dataSource.getPostProcessorSQL());
						}catch (Exception e) {
							logger.error("DataSourceServiceImpl-->processDataSources-->EXCEPTION-->",e);
							
						}
					}
					
				}
			}
		} catch (Exception e) {
			logger.error("DataSourceServiceImpl-->processDataSources-->EXCEPTION-->",e);
			
		}
	}
	
	public void setDataSourceBO(DataSourceBO dataSourceBO) {
		this.dataSourceBO = dataSourceBO;
	}

	public DataSourceBO getDataSourceBO() {
		return dataSourceBO;
	}

	public ResponsysDataSourceServiceImpl getProviderDataSourceService() {
		return providerDataSourceService;
	}

	public void setProviderDataSourceService(
			ResponsysDataSourceServiceImpl providerDataSourceService) {
		this.providerDataSourceService = providerDataSourceService;
	}

	public boolean isResponsysEnableMode() {
		return responsysEnableMode;
	}

	public void setResponsysEnableMode(boolean responsysEnableMode) {
		this.responsysEnableMode = responsysEnableMode;
	}

}
