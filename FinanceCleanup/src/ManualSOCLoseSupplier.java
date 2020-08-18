import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.newco.marketplace.utils.MoneyUtil;

/**
 * Addresses: SL-10902, SL-10905, SL-10861 
 * "Not enough project funding allocated to this service order"
 * 
 * When the transaction between accounts schema and the supplier schema has an error, it could leave successful close and pay 
 * attempts in accounts.  Therefore the corresponding SOs in the supplier schema have to be closed manually.  This process generates those statements.
 * 
 * Run from Eclipse or the command line by passing in list of so_ids that need to be manually closed
 *      
 * @author nsanzer
 *
 */
public class ManualSOCLoseSupplier {
	public static final String PROPERTY_FILE_WITH_PATH = "FinanceCleanupDb.properties";
	
	//while executing, add the VM arguments org.owasp.esapi.resources="/path/to/.esapi"
	public static void main(String args[]) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();
		ArrayList<So> sos = new ArrayList<So>();
		String dbUrl = null;
		String dbUserName = null;
		String dbPassword = null;
		Properties properties = new Properties();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			dbUrl = SecurityUtility.getProperty(PROPERTY_FILE_WITH_PATH, "datasource.url");
			dbUserName = SecurityUtility.getProperty(PROPERTY_FILE_WITH_PATH, "datasource.username");
			dbPassword = SecurityUtility.getProperty(PROPERTY_FILE_WITH_PATH, "datasource.password");
			con = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
			if (!con.isClosed()) {
				System.out.println("Successfully connected to " + "MySQL server using TCP/IP...\n\n\n\n");
			}
			
			for (String soId : args) {
				ArrayList<Sku> skus = new ArrayList<Sku>();
				So so = new So();	
				// Check the state
				String query = "SELECT wf_state_id FROM so_hdr WHERE so_id = ?";
				pstmt = con.prepareStatement(query); // create a statement
				pstmt.setString(1, soId); // set input parameter
				rs = pstmt.executeQuery();
				while (rs.next()) {
					int wf_state_id = rs.getInt(1);
					so.soID = soId;
					so.wfState = wf_state_id;
				}
				
				query = "SELECT COUNT(*) FROM accounts_prod.fullfillment_entry f WHERE so_id = ? AND f.bus_trans_id = 120";
				pstmt = con.prepareStatement(query); // create a statement
				pstmt.setString(1, soId); // set input parameter
				rs = pstmt.executeQuery();
				while (rs.next()) {
					int count = rs.getInt(1);
					if (count > 0 && so.wfState < 180){
						so.canClose = true;	
					}
				}

				if (so.canClose) {
					// Get final price fee from fullfillment_entry
					query = "SELECT trans_amt FROM accounts_prod.fullfillment_entry f WHERE so_id = ? AND f.bus_trans_id = 120 AND f.fullfillment_entry_rule_id = 3700";
					pstmt = con.prepareStatement(query); // create a statement
					pstmt.setString(1, soId); // set input parameter
					rs = pstmt.executeQuery();
					while (rs.next()) {
						double amt = rs.getDouble(1);
						so.finalPrice = amt;
					}

					// Get final service fee from fullfillment_entry
					query = "SELECT trans_amt FROM accounts_prod.fullfillment_entry f WHERE so_id = ? AND f.bus_trans_id = 120 AND f.fullfillment_entry_rule_id = 3710";
					pstmt = con.prepareStatement(query); // create a statement
					pstmt.setString(1, soId); // set input parameter
					rs = pstmt.executeQuery();
					while (rs.next()) {
						double amt = rs.getDouble(1);
						so.serviceFee = amt;
					}

					// Get shc order id to query sku table
					query = "SELECT shc_order_id FROM supplier_prod.shc_order s WHERE so_id = ?";
					pstmt = con.prepareStatement(query); // create a statement
					pstmt.setString(1, soId); // set input parameter
					rs = pstmt.executeQuery();
					while (rs.next()) {
						int shcOrdID = rs.getInt(1);
						so.shcOrderID = shcOrdID;
					}

					// SELECT buyer_id, accepted_resource_id FROM so_hdr WHERE so_id = '189-3753-0406-94';
					query = "SELECT buyer_id, accepted_resource_id FROM so_hdr WHERE so_id = ?";
					pstmt = con.prepareStatement(query); // create a statement
					pstmt.setString(1, soId); // set input parameter
					rs = pstmt.executeQuery();
					while (rs.next()) {
						int buyer_id = rs.getInt(1);
						int accepted_resource_id = rs.getInt(2);
						so.buyerId = buyer_id;
						so.vendorId = accepted_resource_id;
					}

					query = "SELECT price_ratio, shc_order_sku_id FROM supplier_prod.shc_order_sku s WHERE s.shc_order_id = ? AND add_on_ind = 0";
					pstmt = con.prepareStatement(query); // create a statement
					pstmt.setInt(1, so.shcOrderID); // set input parameter
					rs = pstmt.executeQuery();
					// extract data from the ResultSet
					while (rs.next()) {
						Sku sku = new Sku();
						double ratio = rs.getDouble(1);
						int shc_order_sku_id = rs.getInt(2);
						sku.skuId = shc_order_sku_id;
						sku.skuFinalPrice = MoneyUtil.getRoundedMoney(ratio * so.finalPrice);
						sku.skuServiceFee = MoneyUtil.getRoundedMoney(ratio * so.serviceFee);
						skus.add(sku);
					}
					so.skusList = skus;
					sos.add(so);
				}else{
					System.out.println("Cannot close: " + so.soID + " Please try again later.");
				}
			}
			for (So so2 : sos) {
				sb.append("UPDATE shc_order SET wf_state_id = 180 WHERE so_id = '" + so2.soID + "';\n");
				sb.append("UPDATE so_price  SET modified_date = NOW(), final_service_fee = " + so2.serviceFee + " WHERE so_id = '" + so2.soID + "';\n");
				for (Sku sku2 : so2.skusList) {
					sb.append("  UPDATE shc_order_sku SET service_fee= " + sku2.skuServiceFee + ", final_price =  " + sku2.skuFinalPrice + " WHERE shc_order_sku_id = " + sku2.skuId + ";\n");	
				}

				sb.append("UPDATE buyer SET total_so_completed = total_so_completed+1,modified_date = NOW(),modified_by = '" + so2.buyerId + "' WHERE  buyer_id = " + so2.buyerId + ";\n");
				sb.append("UPDATE vendor_resource SET total_so_completed = total_so_completed+1,modified_date = NOW(), modified_by = '" + so2.buyerId + "' WHERE resource_id = " + so2.vendorId + ";\n");
				sb.append("UPDATE survey_response_summary SET total_so_completed = total_so_completed+1, modified_date = NOW(),modified_by = '" + so2.buyerId + "' WHERE vendor_resource_id = " + so2.vendorId + ";\n");
				sb.append("INSERT INTO so_logging ( so_id, action_id, chg_comment, created_date, modified_date, modified_by, role_id, created_by_name, entity_id)\n");
				sb.append("     VALUES ('" + so2.soID + "',1, 'Manually closed the SO from backend',NOW(),NOW(),'system',3,'administrator',1);\n");
				sb.append("UPDATE so_hdr SET wf_state_id = 180,so_substatus_id = NULL,last_status_chg = NOW(),last_status_id = 160, closed_date = NOW() WHERE so_id = '" + so2.soID + "';\n\n");
			}
			System.out.println(sb.toString());
			

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
	
	static class So {
		public String soID;
		public int shcOrderID;
		public double serviceFee;
		public double finalPrice;
		public int wfState;
		public int buyerId;
		public int vendorId;
		boolean canClose;
		ArrayList<Sku> skusList = new ArrayList<Sku>();
	}
	
	static class Sku {
		public int skuId;
		public double ratio;
		public boolean addon;
		public double skuServiceFee;
		public double skuFinalPrice;

	}
	
}