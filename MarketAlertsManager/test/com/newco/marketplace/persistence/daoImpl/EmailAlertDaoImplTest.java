package com.newco.marketplace.persistence.daoImpl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapSession;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.exception.core.DataServiceException;

public class EmailAlertDaoImplTest {
	private SqlMapClientTemplate template = new SqlMapClientTemplate();

	@Test
	public void testInsertToAlertTask() throws DataServiceException {
		 AlertTask alertTask = new AlertTask();
		 alertTask.setAlertTo("");
		 alertTask.setAlertCc("");
		 alertTask.setAlertBcc("");		
		
		 DataSource dataSource = mock(DataSource.class);
		 SqlMapClient sqlMapclient = mock(SqlMapClient.class);
		 SqlMapSession sqlMapSession = mock(SqlMapSession.class);

		 when(sqlMapclient.openSession()).thenReturn(sqlMapSession);
		 when(sqlMapclient.getDataSource()).thenReturn(dataSource);

		 EmailAlertDaoImpl emailAlertDaoImpl = new EmailAlertDaoImpl();
		 emailAlertDaoImpl.setSqlMapClient(sqlMapclient);
		 
		 boolean inserted = emailAlertDaoImpl.insertToAlertTask(alertTask);
		 assertTrue(inserted);
	}
	
	public final SqlMapClientTemplate getSqlMapClientTemplate() {
		  return this.template;
	}
}
