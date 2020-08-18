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
public class SOAdditionalPaymentTokenization implements Runnable{

	private Connection connectionSchemaSupplierProd;
	private String oldKey;
	private String newKey;
	private String splitId;
	private boolean isGreater;
	String offset = "";
	private HSAuthServiceCreditCardBO hsAuthServiceCreditCardBO;
	private String tokenizerUrl;
	private String tokenizerHeader;
	private String encryptionKey;
	String slStoreNo;


	public SOAdditionalPaymentTokenization(
			Connection connectionSchemaSupplierProd, String splitId, boolean isGreater,String tokenizerUrl,String tokenizerHeader,String encryptionKey) {
		this.connectionSchemaSupplierProd = connectionSchemaSupplierProd;
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
				"Inside SOAdditionalDataEncryption thread..." + Thread.currentThread().getName());
		PreparedStatement statement = null;
		try {
			//int offset = Integer.valueOf(DBConstants.QUERY_INITIAL_OFFSET);
			if(isGreater){
				offset = splitId;
			} else {
				offset = "";
			}
			int limit = Integer.valueOf(100);
			int resultListSize = 0;
			ResultSet resultSet = null;

			if(isGreater){
				statement = connectionSchemaSupplierProd
					.prepareStatement(DBConstants.QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_GT);
			} else {
				statement = connectionSchemaSupplierProd
						.prepareStatement(DBConstants.QUERY_CHUNK_SO_ADDITIONAL_PAYMENT_LT);
			}

//			CryptographyUtil cryptographyUtil = new CryptographyUtil();
//			SecretKey secretNew = cryptographyUtil.getSecretForBits(newKey,
//					KeyRotationConstants.CHANGE_TO_BIT);
//			SecretKey secretOld = cryptographyUtil.getSecretForBits(oldKey,
//					KeyRotationConstants.CHANGE_FROM_BIT);
			// loop and get chunk data
			do {
				if(isGreater){
					statement.setString(1, offset);
					statement.setInt(2, limit);
				} else {
					statement.setString(1, offset);
					statement.setString(2, splitId);
					statement.setInt(3, limit);
				}

				Log.writeLog(Level.INFO, "SOAdditionalDataEncryption : Before Fetch : ");
				resultSet = statement.executeQuery();
				Log.writeLog(Level.INFO, "SOAdditionalDataEncryption : After Fetch : ");

				// for each,
				resultListSize = tokenizeAdditionalData(resultSet);

				//offset = offset + limit;
				Log.writeLog(Level.INFO,
						"SOAdditionalDataEncryption : Fetch size : "
								+ resultListSize +" : Offset : "
								+ offset);
				connectionSchemaSupplierProd.commit();
			} while (resultListSize == limit);
			Log.writeLog(Level.INFO,
					"Exiting SOAdditionalDataEncryption thread...");
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Exiting SOAdditionalDataEncryption thread with exception..."
							+ e);
			e.printStackTrace();
		}
	}

	private int tokenizeAdditionalData(ResultSet resultSet)
			throws Exception {
		String decryptedAccountNo = null;
		PreparedStatement updateStatement = connectionSchemaSupplierProd
				.prepareStatement(DBConstants.UPDATE_NEW_DATA_SO_ADDITIONAL_PAYMENT);

		CryptographyUtil crypto = new CryptographyUtil(); 
		int rows = 0;
		boolean isRowUpdated = false;
		String token="T"; 
		String maskedAcctNo="T";
         String accountNo="";
         String username="migration";
   
        //url and header can be fetched from db/property file.
        String creditCardAuthUrl="https://hfdvhshasvc1.vm.itg.corp.us.shldcorp.com:8943/HSSOMCreditAuthService/services/credit/tokenize";
       String creditCardAuthHeader="clientid:SLIVE,currentdatetime:2015,servicename:HSSOMTokenize,serviceversion:1.0,userid:athakkar";
       tokenizerHeader=tokenizerHeader+",userid:migration";

		while (resultSet.next()) {
			// 1. decrypt using old key and encrypt using new key
			if (StringUtils.isNotBlank(resultSet.getString(2))) {
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
							"Could not convert row in SOAdditionalDataEncryption with so_id : "
									+ resultSet.getString(1) + " -****** ERROR *****-");
					//throw e;
				}
			}
			offset = resultSet.getString(1);
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
	
	public boolean isGreater() {
		return isGreater;
	}

	public void setGreater(boolean isGreater) {
		this.isGreater = isGreater;
	}

	public String getSplitId() {
		return splitId;
	}

	public void setSplitId(String splitId) {
		this.splitId = splitId;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public HSAuthServiceCreditCardBO getHsAuthServiceCreditCardBO() {
		return hsAuthServiceCreditCardBO;
	}

	public void setHsAuthServiceCreditCardBO(
			HSAuthServiceCreditCardBO hsAuthServiceCreditCardBO) {
		this.hsAuthServiceCreditCardBO = hsAuthServiceCreditCardBO;
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
