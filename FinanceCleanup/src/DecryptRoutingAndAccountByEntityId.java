import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.newco.marketplace.dto.vo.CryptographyVO;

public class DecryptRoutingAndAccountByEntityId {

	/**
	 * @param args
	 */
	public static final String PROPERTY_FILE_WITH_PATH = "FinanceCleanupDb.properties";
	
	//while executing, add the VM arguments org.owasp.esapi.resources="/path/to/.esapi"
	public static void main(String[] entityIds) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();
		String dbUrl = null;
		String dbUserName = null;
		String dbPassword = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			dbUrl = SecurityUtility.getProperty(PROPERTY_FILE_WITH_PATH, "datasource.url");
			dbUserName = SecurityUtility.getProperty(PROPERTY_FILE_WITH_PATH, "datasource.username");
			dbPassword = SecurityUtility.getProperty(PROPERTY_FILE_WITH_PATH, "datasource.password");
			con = DriverManager.getConnection(dbUrl, dbUserName, dbPassword); 

			if (!con.isClosed()) {
				System.out.println("Successfully connected to " + "MySQL server using TCP/IP...\n\n\n\n");
			}
			System.out.println("Unencrypting accounts for entity id: " + entityIds[0]);

			// get the routing and account numbers encrypted
			String query = "SELECT 	routing_no, account_no FROM accounts_prod.account_hdr ah WHERE ah.entity_id = ? LIMIT 5";
			pstmt = con.prepareStatement(query); 
			pstmt.setInt(1, Integer.parseInt(entityIds[0])); 
															
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String routing_no = rs.getString(1);
				String account_no = rs.getString(2);
				System.out.println("Encrypted routing number: " + routing_no);
				System.out.println("Encrypted account number: " + account_no);

				CryptographyVO cryptographyVO = new CryptographyVO();
				cryptographyVO.setInput(routing_no);
				cryptographyVO.setKAlias("enKey");
				cryptographyVO = decryptKey(cryptographyVO);
				System.out.println("Decrypted routing_no: " + cryptographyVO.getResponse());

				cryptographyVO.setInput(account_no);
				cryptographyVO.setKAlias("enKey");
				cryptographyVO = decryptKey(cryptographyVO);
				System.out.println("Decrypted account_no: " + cryptographyVO.getResponse());

			}

		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				if (con != null) 
				con.close();
				if(rs!=null)
				rs.close();
				if(pstmt!=null)
				pstmt.close();
			} catch (SQLException e) {
			}
		}
	}

	public static CryptographyVO encryptKey(CryptographyVO cryptographyVO) {

		try {

			byte[] raw = new BASE64Decoder().decodeBuffer("P3Q6GUCryVD51h5JOo0WMQ==");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal((cryptographyVO.getInput()).getBytes("8859_1"));
			cryptographyVO.setResponse(new BASE64Encoder().encode(encrypted));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cryptographyVO;
	}

	public static CryptographyVO decryptKey(CryptographyVO cryptographyVO) {

		String originalString = null;

		try {

			byte[] raw = new BASE64Decoder().decodeBuffer("P3Q6GUCryVD51h5JOo0WMQ==");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte[] encrypted = new BASE64Decoder().decodeBuffer(cryptographyVO.getInput());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] original = cipher.doFinal(encrypted);
			originalString = new String(original, "8859_1");
			cryptographyVO.setResponse(originalString);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cryptographyVO;
	}

}
