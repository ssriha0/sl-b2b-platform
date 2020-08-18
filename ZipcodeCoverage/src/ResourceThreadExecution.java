import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class ResourceThreadExecution implements Runnable {
	
	private Integer offset;
	private Integer thread;
	private String db_host;
	private Integer limit;
	//private static String db_host = "10.3.2.177";
	private String db_port;
	private static String db_name = "supplier_prod";
	private static String db_user = "supply_usr";
	private static String db_pass = "supply";
	private static String zipCodeAPIKey = "WQHUOHHN7EIAB9KOQAS4";
	private static String QUERRY1 ="INSERT INTO vendor_resource_coverage (resource_id,zip,created_date,created_by,modified_date) VALUES(?,?,NOW(),?,NOW())";
	private static String QUERRY2 ="INSERT INTO vendor_resource_coverage_outof_states (resource_id,statecode,state_license_confirmation,created_date,created_by) VALUES(?,?,?,NOW(),?)";
	
	public ResourceThreadExecution(String db_host, String db_port, Integer offset,Integer thread,Integer limit){
		this.db_host=db_host;
		this.db_port=db_port;
		this.offset=offset;
		this.thread=thread;
		this.limit=limit;
	}

	@Override
	public void run() {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;

		try {
		
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			StringBuffer buffer=new StringBuffer();
			buffer.append("jdbc:mysql://");
			buffer.append(db_host);
			buffer.append(":");
			buffer.append(db_port);
			buffer.append("/");
			buffer.append(db_name);
			con = DriverManager.getConnection(buffer.toString(), db_user, db_pass);

			if (!con.isClosed()) {
				System.out.println("Thread-->"+thread+" Successfully connected to MySQL server using TCP/IP...");
			}
			con.setAutoCommit(false);
			//System.out.println("query execution starts");
			//String query = "SELECT vr.resource_id , vr.vendor_id , vr.locn_id,loc.zip, lsar.radius_miles FROM vendor_resource vr, location loc,lu_service_area_radius lsar WHERE wf_state_id = (SELECT wf_state_id FROM wf_states WHERE wf_state='Approved (Market Ready)') AND vr.locn_id = loc.locn_id\tAND vr.service_area_radius_id=lsar.id AND vr.resource_id NOT IN (SELECT resource_id FROM resource_coverage_area)";
			StringBuffer querry=new StringBuffer();
			querry.append("SELECT vr.resource_id, loc.zip, lsar.radius_miles,loc.state_cd FROM vendor_resource vr JOIN location loc ON vr.locn_id = loc.locn_id ");
			querry.append("JOIN lu_service_area_radius lsar ON lsar.id=vr.service_area_radius_id ");
			querry.append("WHERE vr.resource_id NOT IN(SELECT resource_id FROM vendor_resource_coverage) ");
			querry.append("ORDER BY lsar.radius_miles,vr.resource_id LIMIT "+limit);
			querry.append(" OFFSET "+offset);
			
			pstmt = con.prepareStatement(querry.toString());
			pstmt1 = con.prepareStatement(QUERRY1);
			pstmt2 = con.prepareStatement(QUERRY2);
			
			rs = pstmt.executeQuery();
		
			int counter = 0;
			int counter1 = 0;
			long totalStartTime=Calendar.getInstance().getTimeInMillis();
			Integer resourceId=null;
			String zip=null;
			Integer radius=null;
			String stateCode=null;
			
			while (rs.next()) {
				counter++;
				try {
					//long pStartTime= Calendar.getInstance().getTimeInMillis();
					resourceId=rs.getInt(1);
					zip = rs.getString(2);
					radius = rs.getInt(3);
					stateCode = rs.getString(4);
					if ((StringUtils.isNotEmpty(zip)) && (radius > 0)) {
						 //originalOut.println("Calling 3rd party API for getting the zipcodes for the resource :" + resourceId);
						System.out.println(System.currentTimeMillis()+"-->Thread-->"+thread+"==>Calling 3rd party API for getting the zipcodes for the resource :" + resourceId);
							//long startTime = Calendar.getInstance().getTimeInMillis();
							String jsonResponse = findZipCodesInRadiusFromAPI(zip, radius);
							//long endTime = Calendar.getInstance().getTimeInMillis();
							/*System.out.println("Time taken for calling the 3rd party API for fetching the zipcode api:"
									+ (endTime - startTime) + "ms");*/

							JSONObject obj = new JSONObject(jsonResponse);
							JSONArray array = obj.getJSONArray("DataList");
							/*System.out.println(
									"Fetching valid zip codes for the dispatch state :" + stateCode);*/
							List<String> validZip=getValidZipCodes(con,stateCode);
							List<String> outOfStateZips=new ArrayList<String>();;
							//System.out.println("Inserting zipcodes from the response for the resource : " +resourceId);
							for (int i = 0; i < array.length(); i++) {
								String zipCode = array.getJSONObject(i).getString("Code");
																
								//query = "INSERT INTO vendor_resource_coverage (resource_id,zip,created_date,created_by,modified_date) VALUES(?,?,NOW(),?,NOW())";
								//pstmt = con.prepareStatement(QUERRY1);
								pstmt1.setInt(1, resourceId);
								pstmt1.setString(2, zipCode);
								pstmt1.setString(3, "System");
								pstmt1.addBatch();
								//pstmt.executeUpdate();
								
								if(!validZip.contains(zipCode))
									outOfStateZips.add(zipCode);
	
								/*logger.info(
										"Successfully inserted the record in resource_Service_area table for the technician : "
												+ resourceId);*/
														
							}
							validZip=null;
							array=null;
							obj=null;
							jsonResponse=null;
							if(outOfStateZips!=null && outOfStateZips.size()>0){
								String zips="'" + StringUtils.join(outOfStateZips,"','") + "'";
								 List<StateZipcodeVO> states=getStatenames(con,zips);
								 if(states!=null&& states.size()>0){
									 Iterator<StateZipcodeVO> stateItr=states.iterator();
									 while(stateItr.hasNext()){
										 StateZipcodeVO state=stateItr.next();
										 //query = "INSERT INTO vendor_resource_coverage_outof_states (resource_id,statecode,zipcodes,created_date,created_by) VALUES(?,?,?,NOW(),?)";
										//pstmt = con.prepareStatement(QUERRY2);
										pstmt2.setInt(1, resourceId);
										pstmt2.setString(2, state.getStateCd());
										pstmt2.setString(3, null);
										pstmt2.setString(4, "System");
										pstmt2.addBatch();
										//pstmt2.executeUpdate();
									 }
								 }
								 states=null;
								 outOfStateZips=null;
								 pstmt2.executeBatch();
							}
							pstmt1.executeBatch();
							con.commit();
							//long pEndTime=Calendar.getInstance().getTimeInMillis();
							//System.out.println("Total time taken for the resource "+rs.getInt(1) + "is : " +(pEndTime-pStartTime) +"ms");
							resourceId=null;
							zip=null;
							radius=null;
							stateCode=null;
							counter1++;
						
						}
				} catch (Exception e) {
					//logger.error("Exception occurred while inserting records for the resource : " + rs.getInt(1)
							//+ ".Roll Back the changes");
					System.out.println("Exception occurred while inserting records for the resource : " + rs.getInt(1)
						+ ".Roll Back the changes" +e);
					con.rollback();
				}
			}
			long totalEndTime=Calendar.getInstance().getTimeInMillis();
			System.out.println(System.currentTimeMillis()+"-->Thread-->"+thread+"==>No. of records fetched :" + counter);
			System.out.println(System.currentTimeMillis()+"-->Thread-->"+thread+"==>No. of records successfully inserted :" + counter1);
			System.out.println(System.currentTimeMillis()+"-->Thread-->"+thread+"==>Total Time Taken for completing : " +counter+" records :" +(totalEndTime-totalStartTime) + "ms.");
			System.gc();
			////originalOut.println("No. of records fetched :" + counter);
			//originalOut.println("No. of records successfully inserted :" + counter1);
			//originalOut.println("Total Time Taken for completing : " +counter+" records :" +(totalEndTime-totalStartTime) + "ms.");
			//originalOut.close();
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
	
	private static String findZipCodesInRadiusFromAPI(String zip, int radius) throws Exception {
		//logger.info("entering FindZipCodesInRadius of GetResourceCoverageAreaForExistingResources");
		//String zipCodeAPIKey = "WQHUOHHN7EIAB9KOQAS4";
		//String zipCodeAPIKey = "PDJ0DXHEK9VHU5ABH1I9";
		zip=zip.trim();
		String apiurl = "http://api.zip-codes.com/ZipCodesAPI.svc/1.0/FindZipCodesInRadius?zipcode=" + zip
				+ "&minimumradius=0&maximumradius=" + radius + "&key=" + zipCodeAPIKey;

		String zipCodeData = "";
		HttpURLConnection conn=null;
		BufferedReader br=null;
		try {
			URL url = new URL(apiurl);
			String data = "";
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			//long startTime = Calendar.getInstance().getTimeInMillis();

			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			//long endTime = Calendar.getInstance().getTimeInMillis();
			/*System.out.println(
					"Time taken to get the data in buffer reader from zipcode api:" + (endTime - startTime) + "ms");
			logger.info("Time taken to get the data in buffer reader from zipcode api:" + (endTime - startTime) + "ms");*/

			StringBuffer buffer = new StringBuffer();

			//startTime = Calendar.getInstance().getTimeInMillis();
			int letter;
			while ((letter = br.read()) != -1) {
				buffer.append((char) letter);
			}
			//endTime = Calendar.getInstance().getTimeInMillis();
			/*System.out.println("Time taken to iterate while with zipcode data:" + (endTime - startTime) + "ms");
			logger.info("Time taken to iterate while with zipcode data:" + (endTime - startTime) + "ms");*/
			data = buffer.toString();
			
			String output;

			while ((output = br.readLine()) != null) {
				data = data + output;
			}
			zipCodeData = data != null ? data.substring(data.indexOf("{")) : "{}";
			buffer.setLength(0);
			//System.out.println(zipCodeData);
			
		} catch (Exception e) {
			//logger.error("Error occured while processing zip code data in findZipCodesInRadiusFromAPI ", e);
			throw new Exception("Exception occurred while calling the 3rd Party API for fetching zipcodes" + e);
		}
		finally{
			br.close();
			conn.disconnect();
		}

		//logger.info("exiting FindZipCodesInRadius of D2CProviderPortalDelegateImpl");
		return zipCodeData;
	}
	
	private static List<String> getValidZipCodes(Connection con,String stateCode)throws Exception {
		PreparedStatement pstmnt = null;
		ResultSet result = null;
		List<String> validZipList=new ArrayList<String>();
		StringBuffer queryString=new StringBuffer();
		queryString.append("SELECT zip FROM zip_geocode WHERE state_cd='"+stateCode+"'");
		pstmnt = con.prepareStatement(queryString.toString());
		result = pstmnt.executeQuery();
		while (result.next()) {
			validZipList.add(result.getString(1));
		}
		queryString.setLength(0);
		result.close();
		pstmnt.close();
		return validZipList;
	}
	
	private static List<StateZipcodeVO> getStatenames(Connection con,String zips)throws Exception {
		PreparedStatement pstmnt = null;
		ResultSet result = null;
		List<StateZipcodeVO> stateZipList=new ArrayList<StateZipcodeVO>();
		StringBuffer queryString=new StringBuffer();
		queryString.append("SELECT state_cd AS stateCd FROM zip_geocode WHERE zip IN ("+zips+") GROUP BY state_cd");
		pstmnt = con.prepareStatement(queryString.toString());
		result = pstmnt.executeQuery();
		while (result.next()) {
			StateZipcodeVO state=new StateZipcodeVO();
			state.setStateCd(result.getString(1));
			//state.setZipCode(result.getString(2));
			stateZipList.add(state);
		}
		queryString.setLength(0);
		result.close();
		pstmnt.close();
		return stateZipList;
		
	}

}
