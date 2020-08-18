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
public class AccountHdrDataTokenization implements Runnable {

	private Connection connectionSchemaAccountsProd;
	private String oldKey;
	private String newKey;
	long offset = 0l;
	private HSAuthServiceCreditCardBO hsAuthServiceCreditCardBO;
	private String tokenizerUrl;
	private String tokenizerHeader;
	private String encryptionKey;
	String slStoreNo;

	public AccountHdrDataTokenization(
			Connection connectionSchemaAccountsProd,String tokenizerUrl,String tokenizerHeader,String encryptionKey) {
		this.connectionSchemaAccountsProd = connectionSchemaAccountsProd;
        this.tokenizerUrl=tokenizerUrl;
        this.tokenizerHeader=tokenizerHeader;
        this.encryptionKey=encryptionKey;
        this.slStoreNo="09930";

	}

	public void run() {
		// Call DB procedure
		Log.writeLog(Level.INFO, "Inside AccountHdrDataEncryption thread...");
		PreparedStatement statement = null;
		try {
			//int offset = Integer.valueOf(DBConstants.QUERY_INITIAL_OFFSET);
			offset = 0l;
			int limit = 10;// Integer.valueOf(DBConstants.QUERY_LIMIT);
			int resultListSize = 0;
			ResultSet resultSet = null;

			statement = connectionSchemaAccountsProd
					.prepareStatement(DBConstants.QUERY_CHUNK_ACCOUNT_HDR);


			do {
				statement.setLong(1, offset);
				statement.setInt(2, limit);

				resultSet = statement.executeQuery();
				// for each,
				resultListSize = tokenizeAccountData(resultSet); 

				//offset = offset + limit;
				Log.writeLog(Level.INFO,
						"AccountHdrDataEncryption : Fetch size : "
								+ resultListSize +" : Offset : "
								+ offset);
				connectionSchemaAccountsProd.commit();
			} while (resultListSize == limit);
			Log.writeLog(Level.INFO,
					"Exiting AccountHdrDataEncryption thread...");
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Exiting AccountHdrDataEncryption thread with exception..."
							+ e);
			e.printStackTrace();
		}
	}

	private int tokenizeAccountData(ResultSet resultSet) throws Exception {
		String accountNo = null;
		String decryptedAccountNo = null;
		PreparedStatement updateStatement = connectionSchemaAccountsProd
				.prepareStatement(DBConstants.UPDATE_NEW_DATA_ACCOUNT_HDR);

		CryptographyUtil crypto = new CryptographyUtil();
		int rows = 0;
		boolean isRowUpdated = false;
		String username="migration";
		 
		  //url and header can be fetched from db/property file.
        String creditCardAuthUrl="https://hfdvhshasvc1.vm.itg.corp.us.shldcorp.com:8943/HSSOMCreditAuthService/services/credit/tokenize";
       String creditCardAuthHeader="clientid:SLIVE,currentdatetime:2015,servicename:HSSOMTokenize,serviceversion:1.0,userid:athakkar"; 
       tokenizerHeader=tokenizerHeader+",userid:migration";
		while (resultSet.next()) {
			// 1. decrypt using old key and encrypt using new key
			if (null != resultSet.getString(2)) {
				try {
					accountNo=resultSet.getString(2);
					decryptedAccountNo = decryptAccountAndRoutingNo(accountNo);
//							resultSet.getString(2), secretOld);
//					
////					encryptedAdditionalData = crypto.encryptKey(
////							originalAdditionalString, newKey,
////							KeyRotationConstants.CHANGE_TO_BIT);
//					
//					encryptedAdditionalData = crypto.encryptKey(
//							originalAdditionalString, secretNew);
					
					hsAuthServiceCreditCardBO=new HSAuthServiceCreditCardBO();
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
					
					// updateStatement.executeUpdate();
				} catch (Exception e) {
					Log.writeLog(Level.SEVERE,
							"Could not convert row in AccountHdrDataEncryption with account_id : "
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
