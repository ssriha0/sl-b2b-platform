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
public class CreditCardDataTokenization implements Runnable {

	private Connection connectionSchemaSupplierProd;
	private String oldKey;
	private String newKey;
	long offset = 0l;
	private HSAuthServiceCreditCardBO hsAuthServiceCreditCardBO;
	private String tokenizerUrl;
	private String tokenizerHeader;
	private String encryptionKey;
	private String slStoreNo;
    boolean additionalData;

	public CreditCardDataTokenization(Connection connectionSchemaSupplierProd,String tokenizerUrl,String tokenizerHeader,String encryptionKey,String slStoreNo,boolean additionalData) {
		this.connectionSchemaSupplierProd = connectionSchemaSupplierProd;
        this.tokenizerUrl=tokenizerUrl;
        this.tokenizerHeader=tokenizerHeader;
        this.encryptionKey=encryptionKey;
        this.additionalData=additionalData;
        this.slStoreNo = slStoreNo;
	}

	public void run() {
		
		Log.writeLog(Level.INFO, "Inside CreditCardDataTokenization thread...");
		PreparedStatement statement = null;
		try {
			offset = Integer.valueOf(DBConstants.QUERY_INITIAL_OFFSET);
			int limit =Integer.valueOf(DBConstants.QUERY_LIMIT);
			int resultListSize = 0;  
			ResultSet resultSet = null;
			
			if(!additionalData){
			statement = connectionSchemaSupplierProd.prepareStatement(DBConstants.QUERY_CHUNK_CREDIT_CARD_DATA);
			}else{
				statement = connectionSchemaSupplierProd.prepareStatement(DBConstants.QUERY_CHUNK_CREDIT_CARD_DATA_ADDITIONAL);	
			}

			do {
				statement.setLong(1, offset);
				statement.setInt(2, limit);

				resultSet = statement.executeQuery();
				// for each,
				resultListSize = tokenizeCreditCardData(resultSet); 

				//offset = offset + limit;
				Log.writeLog(Level.INFO,
						"CreditCardDataTokenization : Fetch size : "
								+ resultListSize +" : Offset : "
								+ offset);
				connectionSchemaSupplierProd.commit();
			} while (resultListSize == limit);
			Log.writeLog(Level.INFO,
					"Exiting CreditCardDataTokenization thread...");
		} catch (Exception e) {
			Log.writeLog(Level.SEVERE,
					"Exiting CreditCardDataTokenization thread with exception..."
							+ e);
			e.printStackTrace();
		}
	}

	private int tokenizeCreditCardData(ResultSet resultSet) throws Exception {
		String accountNo = null;
		String decryptedAccountNo = null;
		PreparedStatement updateStatement = connectionSchemaSupplierProd.prepareStatement(DBConstants.UPDATE_NEW_CREDIT_CARD_DATA);
        String error=null;
        String result=null;
		CryptographyUtil crypto = new CryptographyUtil();
		int rows = 0;
		boolean isRowUpdated = false;
		String username=DBConstants.USER_NAME; 
		//url and header
       // String creditCardAuthUrl="https://hfdvhshasvc1.vm.itg.corp.us.shldcorpc.com:8943/HSSOMCreditAuthService/services/credit/tokenize";
      // String creditCardAuthHeader="clientid:SLIVE,currentdatetime:2015,servicename:HSSOMTokenize,serviceversion:1.0,userid:athakkar"; 
       tokenizerHeader=tokenizerHeader+DBConstants.USER_ID; 
		while (resultSet.next()) {
			if (null != resultSet.getString(2)) {
				try {
					error=null;
			        result=null;
			    	TokenizeResponse tokenizeResponse=null;
			    	
					accountNo=resultSet.getString(2);
					decryptedAccountNo = decryptAccountAndRoutingNo(accountNo);
				if(null!=decryptedAccountNo){
			        //invoke the tokenizer webservice for each credit card number
					try{
						hsAuthServiceCreditCardBO=new HSAuthServiceCreditCardBO();
						tokenizeResponse=hsAuthServiceCreditCardBO.authorizeHSSTransaction(decryptedAccountNo, username,tokenizerUrl,tokenizerHeader,slStoreNo);
						
					}catch(Exception e){
						Log.writeLog(Level.SEVERE,
								"Exception while invoking credit card tokenization id : "
										+ resultSet.getString(1) + " -****** ERROR *****-");
						error=e.getMessage();
						// result should be error
						result="ERROR";
					}
				}else{
					result="ERROR";
					error="Not able to decrpyted credit card number";
				}
                   if(null!=tokenizeResponse && StringUtils.isNotBlank(tokenizeResponse.getToken()) && StringUtils.isNotBlank(tokenizeResponse.getMaskedAccount())){ 
                	// 2. update token, masked account number , result and the error if any
                       updateStatement.setString(1, tokenizeResponse.getToken());
    				   updateStatement.setString(2, tokenizeResponse.getMaskedAccount());
						result="SUCCESS";
						updateStatement.setString(3, result); 
	    				   updateStatement.setString(4,error); 
	    				   updateStatement.setString(5, resultSet.getString(2)); 
                   }else{
                	   updateStatement.setString(1, null);
    				   updateStatement.setString(2, null);
    				// result should be error if token or masked account number is null
    				   result="ERROR";
    				   updateStatement.setString(3, result); 
    				   updateStatement.setString(4,error); 
    				   updateStatement.setString(5, resultSet.getString(2)); 
						
                   }
    					// Added for batch execution
    					updateStatement.addBatch();
    					isRowUpdated = true; 
                 	} catch (Exception e) {
                 		Log.writeLog(Level.SEVERE,
							"Could not convert row in CreditCardDataEncryption with tokenization id : "
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
		cryptographyVO.setKAlias(MPConstants.CC_ENCRYPTION_KEY);
		cryptographyVO.setEncryptionKey(encryptionKey);
		Cryptography128 cryptography128=new Cryptography128();
		cryptographyVO = cryptography128.decryptKey128Bit(cryptographyVO);
		accountNo=cryptographyVO.getResponse();
		return accountNo;
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

	public boolean isAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(boolean additionalData) {
		this.additionalData = additionalData;
	}

	public String getSlStoreNo() {
		return slStoreNo;
	}

	public void setSlStoreNo(String slStoreNo) {
		this.slStoreNo = slStoreNo;
	}
	
	

}
