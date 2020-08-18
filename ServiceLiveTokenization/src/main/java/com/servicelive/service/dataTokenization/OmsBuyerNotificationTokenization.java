package com.servicelive.service.dataTokenization;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.logging.Level;

import javax.crypto.SecretKey;

import org.apache.commons.lang.StringUtils;

import com.mysql.jdbc.Connection;
import com.servicelive.tokenization.constants.DBConstants;
import com.servicelive.tokenization.constants.TokenizationConstants;
import com.servicelive.tokenization.log.Log;
import com.servicelive.tokenization.util.CryptographyUtil;

/**
 * @author Infosys: Jun, 2014
 */
public class OmsBuyerNotificationTokenization implements Runnable{

	private Connection connectionSchemaIntegration;
	private String oldKey;
	private String newKey;
	private long splitId;
	private boolean isGreater;
	private HSAuthServiceCreditCardBO hsAuthServiceCreditCardBO;
	long offset = 0l;
	private String tokenizerUrl;
	private String tokenizerHeader;
	private String encryptionKey;
    String slStoreNo;

	public OmsBuyerNotificationTokenization(
			Connection connectionSchemaIntegration, long splitId, boolean isGreater
			,String encryptionKey) {
		this.connectionSchemaIntegration = connectionSchemaIntegration;

		
		this.splitId = splitId;
		this.isGreater = isGreater;
		this.tokenizerUrl=tokenizerUrl;
	    this.tokenizerHeader=tokenizerHeader;
        this.encryptionKey=encryptionKey;
        this.slStoreNo="09930";
	}

	public void run() {
		// Call DB procedure
		Log.writeLog(Level.INFO,
				"Inside OmsBuyerNotificationDataEncryption thread..." + Thread.currentThread().getName());
		PreparedStatement statement = null;
		try {
			//int offset = Integer.valueOf(DBConstants.QUERY_INITIAL_OFFSET);
			if(isGreater){
				offset = splitId;
			} else {
				offset = 0l;
			}
			int limit =10; // Integer.valueOf(DBConstants.QUERY_LIMIT);// just for hardcoding  
			int resultListSize = 0;
			ResultSet resultSet = null;
			
			StringBuilder queryString = new StringBuilder();
			if(isGreater){
				queryString.append(DBConstants.QUERY_CHUNK_OMS_BUYER_NOTIFICATION_GT);
			} else {
				queryString.append(DBConstants.QUERY_CHUNK_OMS_BUYER_NOTIFICATION_LT);
			}
			
			if(!StringUtils.isEmpty(DBConstants.PROCESS_OMS_START_DATE_YYYY_MM_DD) && 
					!StringUtils.isEmpty(DBConstants.PROCESS_OMS_END_DATE_YYYY_MM_DD)){
				queryString.append(" ").append(DBConstants.QUERY_CHUNK_OMS_BUYER_WHERE);
			}
			
			queryString.append(" ").append(DBConstants.QUERY_CHUNK_OMS_BUYER_ORDER);
			
			statement = connectionSchemaIntegration
					.prepareStatement(queryString.toString());

		
			// loop and get chunk data
			do {
				if(isGreater){
					statement.setLong(1, offset);
					if(!StringUtils.isEmpty(DBConstants.PROCESS_OMS_START_DATE_YYYY_MM_DD) && 
							!StringUtils.isEmpty(DBConstants.PROCESS_OMS_END_DATE_YYYY_MM_DD)){
						statement.setString(2, DBConstants.PROCESS_OMS_START_DATE_YYYY_MM_DD);
						statement.setString(3, DBConstants.PROCESS_OMS_END_DATE_YYYY_MM_DD);
						statement.setInt(4, limit);
					} else {
						statement.setInt(2, limit);
					}
				} else {
					statement.setLong(1, offset);
					statement.setLong(2, splitId);
					if(!StringUtils.isEmpty(DBConstants.PROCESS_OMS_START_DATE_YYYY_MM_DD) && 
							!StringUtils.isEmpty(DBConstants.PROCESS_OMS_END_DATE_YYYY_MM_DD)){
						statement.setString(3, DBConstants.PROCESS_OMS_START_DATE_YYYY_MM_DD);
						statement.setString(4, DBConstants.PROCESS_OMS_END_DATE_YYYY_MM_DD);
						statement.setInt(5, limit);
					} else {
						statement.setInt(3, limit);
					}
				}
				
				Log.writeLog(Level.INFO, "OmsBuyerNotificationDataEncryption : Before Fetch : ");
				resultSet = statement.executeQuery();
				Log.writeLog(Level.INFO, "OmsBuyerNotificationDataEncryption : After Fetch : ");

				// for each,
				resultListSize = tokenizeOmsData(resultSet);

				//offset = offset + limit;
				Log.writeLog(Level.INFO,
						"OmsBuyerNotificationDataEncryption : Fetch size : "
								+ resultListSize +" : Offset : "
								+ offset);
				connectionSchemaIntegration.commit();
			} while (resultListSize == limit);
			Log.writeLog(Level.INFO,
					"Exiting OmsBuyerNotificationDataEncryption thread...");
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Exiting OmsBuyerNotificationDataEncryption thread with exception..."
							+ e);
			e.printStackTrace();
		}
	}

	private int tokenizeOmsData(ResultSet resultSet) 
			throws Exception {
	
		PreparedStatement updateStatement = connectionSchemaIntegration
				.prepareStatement(DBConstants.UPDATE_NEW_DATA_OMS_BUYER_NOTIFICATION);

		int rows = 0;
		boolean isRowUpdated = false;
		//url and header can be fetched from db/property file.
        String creditCardAuthUrl="https://hfdvhshasvc1.vm.itg.corp.us.shldcorp.com:8943/HSSOMCreditAuthService/services/credit/tokenize";
       String creditCardAuthHeader="clientid:SLIVE,currentdatetime:2015,servicename:HSSOMTokenize,serviceversion:1.0,userid:athakkar";
       String username="migration";
       String decryptedAccountNo = null;
       String accountNo="";
       tokenizerHeader=tokenizerHeader+",userid:migration";

		while (resultSet.next()) {
			// 1. decrypt using old key and encrypt using new key
			if (null != resultSet.getString(2)) {
				try {
					
					accountNo=resultSet.getString(2);
					decryptedAccountNo = decryptAccountAndRoutingNo(accountNo);
					Log.writeLog(Level.INFO, "ok ");
					HSAuthServiceCreditCardBO  hsAuthServiceCreditCardBO=new HSAuthServiceCreditCardBO();   
					Log.writeLog(Level.INFO, "ok1");
					TokenizeResponse tokenizeResponse=hsAuthServiceCreditCardBO.authorizeHSSTransaction(decryptedAccountNo, username,tokenizerUrl,tokenizerHeader,slStoreNo);
	                   if(null!=tokenizeResponse && StringUtils.isNotBlank(tokenizeResponse.getToken()) && StringUtils.isNotBlank(tokenizeResponse.getMaskedAccount())){
						// 2. update actual table
						updateStatement.setString(1, tokenizeResponse.getToken());
						updateStatement.setString(2, tokenizeResponse.getMaskedAccount());
						updateStatement.setString(3, resultSet.getString(1));
	 
						// Added for batch execution
						updateStatement.addBatch();
						isRowUpdated = true; 
	                   }
				} catch (Exception e) {
					Log.writeLog(Level.SEVERE,
							"Could not convert row in OmsBuyerNotificationDataEncryption with omsBuyerNotificationId : "
									+ resultSet.getString(1) + " -****** ERROR *****-");
					//throw e;
				}
			}
			offset = resultSet.getLong(1);
			rows = rows + 1;
		}
		if (isRowUpdated) {
			// Added for batch execution
			updateStatement.executeBatch();
		}
		return rows;
	}
	
	private String decryptAccountAndRoutingNo(String accountNo) {
		CryptographyVO cryptographyVO = new CryptographyVO();
		cryptographyVO.setInput(accountNo);
		
		
		//Commenting the code for new 128decryption
		//cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
		//cryptographyVO = cryptography.decryptKey(cryptographyVO);
		cryptographyVO.setKAlias(MPConstants.CC_ENCRYPTION_KEY);
		cryptographyVO.setEncryptionKey(encryptionKey);
		Cryptography128 cryptography128=new Cryptography128();
		cryptographyVO = cryptography128.decryptKey128Bit(cryptographyVO);
		
		accountNo=cryptographyVO.getResponse();

		return accountNo;
	}

	public String getOldKey() {
		return oldKey;
	}

	public void setOldKey(String oldKey) {
		this.oldKey = oldKey;
	}

	public String getNewKey() {
		return newKey;
	}

	public void setNewKey(String newKey) {
		this.newKey = newKey;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public long getSplitId() {
		return splitId;
	}

	public void setSplitId(long splitId) {
		this.splitId = splitId;
	}

	public boolean isGreater() {
		return isGreater;
	}

	public void setGreater(boolean isGreater) {
		this.isGreater = isGreater;
	}

	public String getTokenizerUrl() {
		return tokenizerUrl;
	}

	public void setTokenizerUrl(String tokenizerUrl) {
		this.tokenizerUrl = tokenizerUrl;
	}

	public String getTokenizerHeader() {
		return tokenizerHeader;
	}

	public void setTokenizerHeader(String tokenizerHeader) {
		this.tokenizerHeader = tokenizerHeader;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}
    
	
	
}
