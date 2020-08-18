package com.servicelive.wallet.remoteservice.dao;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapSession;
import com.servicelive.common.DataServiceException;
import com.servicelive.wallet.remoteservice.vo.MessageResultVO;

public class WalletMessageDaoTest {
private WalletMessageDao dao;
	
	@Test
	public void testInsertMessage(){
		dao = new WalletMessageDao();
		MessageResultVO result = new MessageResultVO();
		result.setResult(true);
		String messageId = "25";
		
		DataSource dataSource = mock(DataSource.class);
		SqlMapClient sqlMapclient = mock(SqlMapClient.class);
		SqlMapSession sqlMapSession = mock(SqlMapSession.class);
		SqlMapClientTemplate sqlMapclientTemplate = mock(SqlMapClientTemplate.class);
		
		//when(sqlMapclient.openSession()).thenReturn(sqlMapSession);
		//when(sqlMapclient.getDataSource()).thenReturn(dataSource);
		
		dao.setSqlMapClient(sqlMapclient);
		dao.setSqlMapClientTemplate(sqlMapclientTemplate);
		try {
			when(dao.queryForObject("walletmessage.read", messageId)).thenReturn(result);
			result = dao.getResult(messageId);
			Assert.assertTrue(result.getResult());
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
