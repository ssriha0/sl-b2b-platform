/**
 * 
 */
package com.servicelive.gl.db;

/**
 * @author mjoshi1
 *
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.servicelive.gl.constants.DBConstants;
//import com.servicelive.gl.vo.FiscalCalendarVO;
//import com.servicelive.gl.vo.GLDetailVO;
import com.servicelive.gl.vo.SoToBeCorrectedVO;
import com.servicelive.wallet.batch.gl.vo.FiscalCalendarVO;
import com.servicelive.wallet.batch.gl.vo.GLDetailVO;

public class DBAccess {
	private static final Logger logger = Logger.getLogger(DBAccess.class.getName());

	//@SuppressWarnings("finally")
	public List<GLDetailVO> getGLDetails() throws SQLException {
		
	String query = "SELECT entry_id, nps_order, nps_unit, gl_unit, gl_division,  gl_account, gl_process_id, gl_category, ledger_rule, sell_value, so_funding_type,"+  
       "CASE WHEN ledger_rule = 'CEX' THEN provider_name ELSE transaction_type END "+             
       "transaction_id, transactionamount, order_number, gl_date_posted, transaction_date, peoplesoft_file, buyer_id, provider_id "+   
       "FROM accounts_prod.rpt_gl_detail WHERE buyer_id IS NULL AND gl_process_id BETWEEN 2372 AND 2374 limit 10;"; 
	
		List<GLDetailVO> glDetailsList = new ArrayList<GLDetailVO>();
		
		ResultSet rs;
		try {
			rs = getQueryResult(query);
			convertToGLDetailVOList(rs, glDetailsList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return glDetailsList;
	}
	
	public List<GLDetailVO> getGLDetailsRest() throws SQLException {
		
		String query = "SELECT entry_id, nps_order, nps_unit, gl_unit, gl_division,  gl_account, gl_process_id, gl_category, ledger_rule, sell_value, so_funding_type,"+  
	       "CASE WHEN ledger_rule = 'CEX' THEN provider_name ELSE transaction_type END "+             
	       "transaction_id, transactionamount, order_number, gl_date_posted, transaction_date, peoplesoft_file, buyer_id, provider_id "+   
	       "FROM accounts_prod.rpt_gl_detail WHERE (buyer_id IS NULL or buyer_id <> 3000) AND gl_process_id = 2344;"; 
		
			List<GLDetailVO> glDetailsList = new ArrayList<GLDetailVO>();
			
			ResultSet rs;
			try {
				rs = getQueryResult(query);
				convertToGLDetailVOList(rs, glDetailsList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return glDetailsList;
		}
	
	public List<GLDetailVO> getGLDetailsInHome() throws SQLException {
		
		String query = "SELECT entry_id, nps_order, nps_unit, gl_unit, gl_division,  gl_account, gl_process_id, gl_category, ledger_rule, sell_value, so_funding_type,"+  
	       "CASE WHEN ledger_rule = 'CEX' THEN provider_name ELSE transaction_type END "+             
	       "transaction_id, transactionamount, order_number, gl_date_posted, transaction_date, peoplesoft_file, buyer_id, provider_id "+   
	       "FROM accounts_prod.rpt_gl_detail WHERE buyer_id = 3000 AND gl_process_id = 2375 limit 10;"; 
		
			List<GLDetailVO> glDetailsList = new ArrayList<GLDetailVO>();
			
			ResultSet rs;
			try {
				rs = getQueryResult(query);
				convertToGLDetailVOList(rs, glDetailsList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return glDetailsList;
		}
	
	public String getApplicationProperty(String query, String prop){
		
		//String query = "SELECT p.app_value as prop FROM supplier_prod.application_properties p WHERE p.app_key = ?;";
		String result = "";
		query = query.replace("?", prop);
		ResultSet rs;
		try {
			rs = getQueryResult(query);
			while(rs.next()){
				result = rs.getString("app_value");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}



	@SuppressWarnings("finally")
	private ResultSet getQueryResult(String query) throws SQLException, Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {

			MysqlDataSource ds = new MysqlDataSource();
			ConnectionPoolMgr poolMgr = new ConnectionPoolMgr(ds, 10);
			connection = poolMgr.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			rs = preparedStatement.executeQuery();
			
			if (rs == null) {
				System.out.println("There was no data in database, for this query="+query);
				throw new Exception("There was no data in database, for this query="+query);
			}
	
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error occured during database operation...");
			//Log.writeLog( Level.SEVERE, "Error occured during database operation");
            throw new Exception("Error occured during database operation "+e);
        }
		finally {
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
			
			return rs;
		}
		
	}



	private void convertToGLDetailVOList(ResultSet rs,
			List<GLDetailVO> glDetailsList) throws SQLException {

		while(rs.next()){
			GLDetailVO detailVO = new GLDetailVO();
			detailVO.setBuyerId(rs.getInt("buyer_id"));
			detailVO.setEntryId(rs.getString("entry_id"));
			detailVO.setGlAccount(rs.getString("gl_account"));
			detailVO.setGlCategory(rs.getString("gl_category"));
			detailVO.setGlDatePosted(rs.getString("gl_date_posted"));
			detailVO.setGlDivision(rs.getString("gl_division"));
			detailVO.setGlProcessId(rs.getInt("gl_process_id"));
			detailVO.setGlUnit(rs.getString("gl_unit"));
			detailVO.setLedgerRule(rs.getString("ledger_rule"));
			detailVO.setNpsOrder(rs.getString("nps_order"));
			detailVO.setNpsUnit(rs.getString("nps_unit"));
			detailVO.setOrderNumber(rs.getString("order_number"));
			detailVO.setPeopleSoftFile(rs.getString("peoplesoft_file"));
			detailVO.setProviderId(rs.getInt("provider_id"));
			detailVO.setSellValue(new Double(rs.getDouble("sell_value")));
			detailVO.setSoFundingType(rs.getString("so_funding_type"));
			detailVO.setTransactionAmount(new Double(rs.getDouble("transactionAmount")));
			detailVO.setTransactionDate(rs.getString("transaction_date"));
			detailVO.setTransactionId(rs.getString("transaction_id"));
			glDetailsList.add(detailVO);
		}
	}



	public FiscalCalendarVO getFiscalCalendar(FiscalCalendarVO fiscalCalendarVO) throws SQLException {

		String query = "SELECT acct_yr, acct_qtr, acct_mo, cal_wk_start_date, cal_wk_end_date, period_wk, "+
		"no_wk_period, qtr_fiscal_wk, yr_fiscal_wk, acct_mo_descr "+
		"FROM ledger_fiscal_calendar WHERE '"+fiscalCalendarVO.getCheckFiscalWeek()+"' "+
		"BETWEEN cal_wk_start_date AND cal_wk_end_date";
		
		ResultSet rs;
		FiscalCalendarVO fiscalVO = null;
		try {
			rs = getQueryResult(query);
			fiscalVO = convertToFiscalCalendarVOList(fiscalCalendarVO, rs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return fiscalVO;
	}



	private FiscalCalendarVO convertToFiscalCalendarVOList(FiscalCalendarVO fiscalVO,
			ResultSet rs) throws SQLException {
		while(rs.next()){
			fiscalVO = new FiscalCalendarVO();
			fiscalVO.setAccountingYear(rs.getInt("acct_yr"));
			fiscalVO.setAccountingQuarter(rs.getInt("acct_qtr"));
			fiscalVO.setAccountingMonth(rs.getString("acct_mo"));
			fiscalVO.setCalendarStartWeek(rs.getInt("cal_wk_start_date"));
			fiscalVO.setCalendarEndWeek(rs.getString("cal_wk_end_date"));
			fiscalVO.setPreiodWeek(rs.getInt("period_wk"));
			fiscalVO.setNumberWorkPeriod(rs.getInt("no_wk_period"));
			fiscalVO.setQuarterFiscalWeek(rs.getString("qtr_fiscal_wk"));
			fiscalVO.setYearFiscalWeek(rs.getString("yr_fiscal_wk"));
			fiscalVO.setAccountingMonthDesc(rs.getString("acct_mo_descr"));
		}
		return fiscalVO;
	}



	public List<SoToBeCorrectedVO> getSoToBeCorrectedList(String buyerId) throws Exception {
		String query = "";
		if(buyerId.equals("1000")){
			query = DBConstants.QUERY_FETCH_ERROR_SO;
		}else if(buyerId.equals("3000")){
			query = DBConstants.QUERY_FETCH_ERROR_SO_INHOME;
		}
		query = query.replace("startDate", DBConstants.START_DATE);
		query = query.replace("endDate", DBConstants.END_DATE);
		query = query.replace("buyerId", DBConstants.BUYER_ID);
		
		List<SoToBeCorrectedVO> soToBeCorrectedList = new ArrayList<SoToBeCorrectedVO>();
		logger.info("Query for fetching the SOs: "+query);
		ResultSet rs = getQueryResult(query);
		soToBeCorrectedList = convertToSoToBeCorrectedVOList(rs, soToBeCorrectedList);
		return soToBeCorrectedList;
	}



	private List<SoToBeCorrectedVO> convertToSoToBeCorrectedVOList(ResultSet rs,
			List<SoToBeCorrectedVO> soToBeCorrectedList) throws SQLException {
		while(rs.next()){
			SoToBeCorrectedVO soToBeCorrectedVO = new SoToBeCorrectedVO();
			soToBeCorrectedVO.setSoId(rs.getString("soId"));
			soToBeCorrectedList.add(soToBeCorrectedVO);
		}
		return soToBeCorrectedList;
	}

	public void applyDataFixForWallet(SoToBeCorrectedVO soToBeCorrectedVO,
			DBAccess dba, String spName) throws Exception {
		String query = DBConstants.SP_DATA_FIX_WALLET;
		query = query.replace("~", spName);
		query = query.replace("?", "'"+soToBeCorrectedVO.getSoId()+"'");
		logger.info("Calling SP: "+query);
		ResultSet rs = getQueryResult(query);
		setSOProjBalAndCost(rs, soToBeCorrectedVO);
	}



	private void setSOProjBalAndCost(ResultSet rs,
			SoToBeCorrectedVO soToBeCorrectedVO) throws SQLException {
		String soDetails = "";
		while(rs.next()){
			soDetails = rs.getString("WalletFixedMesg");
		}
		logger.info("Output from SP - WalletFixedMesg: "+soDetails);
		String[] soDetailsArray = soDetails.split("\\|");
		soToBeCorrectedVO.setSoId(soDetailsArray[0]);
		soToBeCorrectedVO.setBuyerId(soDetailsArray[1]);
		soToBeCorrectedVO.setFundingTypeId(soDetailsArray[2]);
		soToBeCorrectedVO.setWfState(soDetailsArray[3]);
		soToBeCorrectedVO.setAvailableBalanceOld(Double.parseDouble(soDetailsArray[4]));
		soToBeCorrectedVO.setAvailableBalanceNew(Double.parseDouble(soDetailsArray[5]));
		/*soToBeCorrectedVO.setSoCost(Double.parseDouble(soDetailsArray[4]));
		soToBeCorrectedVO.setSoAddonCost(Double.parseDouble(soDetailsArray[5]));
		soToBeCorrectedVO.setSoPartCost(Double.parseDouble(soDetailsArray[6]));
		soToBeCorrectedVO.setSoProjBal(Double.parseDouble(soDetailsArray[7]));
		soToBeCorrectedVO.setSoIncSpendLimit(Double.parseDouble(soDetailsArray[8]));
		soToBeCorrectedVO.setSoDecSpendLimit(Double.parseDouble(soDetailsArray[9]));
		soToBeCorrectedVO.setSoProjBalCorrected(Double.parseDouble(soDetailsArray[10]));*/
	}

	
}
