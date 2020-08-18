package com.servicelive.ibatis.sqlchecker;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.servicelive.ibatis.sqlchecker.rule.Rule;
import com.servicelive.ibatis.sqlchecker.rule.TableNameRule;

/**
 * 
 * @author svanloon
 * 
 */
public class MainApp {
	private static final Logger logger = Logger.getLogger(MainApp.class);	
	public static final String PROPERTY_FILE_WITH_PATH = "iBatisSqlCheckerDb.properties";
	private String startDirectories[] = {"../MarketBackend/src/resources/", "../Wallet", "../SPNBackend/src", "../MarketAlertsManager/src", "../MarketDocument/src", "../Wallet/ServiceLive.Common/src/main/resources", "../Wallet/ServiceLive.Lookup/src/main/resources", "../Wallet/Wallet.ACH/src/main/resources", "../Wallet/Wallet.Alert/src/main/resources", "../Wallet/Wallet.Batch/src/main/resources", "../Wallet/Wallet.CreditCard/src/main/resources", "../Wallet/Wallet.Ledger/src/main/resources", "../Wallet/Wallet.ValueLink/src/main/resources"};
	//private String includePattern = "(WarrantyMap.xml)$";
	// private String includePattern = "(jobCodeMap.xml)$";
	private String includePattern = "(Map.xml)$";
	// private String excludePattern = null;
	private String excludePattern = "(account_hdr_SqlMap.xml)??(onsiteVisitMap.xml)??";
	// private String excludePattern =
	// "(vendorHdrMap.xml)??(w9RegistrationMap.xml)??";
	private String lineBreak = "------------------------------------------------------------------------------------------------------------------------\n";
	private String jdbcDriver = "com.mysql.jdbc.Driver";
	private List<Rule> rules;

	public MainApp() {
		rules = new ArrayList<Rule>();
		rules.add(new TableNameRule("tableName"));
		//rules.add(new ExceptionRule("bad query", true, null));
		//rules.add(new ExplainPlanRule("impossible where", true, "Impossible WHERE"));
		//rules.add(new ExplainPlanRule("good query", true, null));
		//rules.add(new SqlRule("use keyword limit query", true, "limit"));
		//rules.add(new SqlRule("order by query", true, "order by"));
		//rules.add(new SqlRule("NOW() query", true, "now"));
		//rules.add(new SqlRule("semicolon query", true, ";"));
		//rules.add(new SqlRule("dyanimic keyword $ query", true, "$"));
		//rules.add(new ExceptionRule("messed up where", false, "near 'and"));

	}
	/**
	 * 
	 */
	//while executing, add the VM arguments org.owasp.esapi.resources="/path/to/.esapi"
	public void execute() throws Exception {

		File file = new File("explainPlans.txt");
		PrintWriter printWriter = new PrintWriter(file);
		Connection con = null;
		Connection con2 = null;
		Statistics statistics = new Statistics(rules);
		String dbUrl1 = null;
		String dbUrl2 = null;
		String dbUserName = null;
		String dbPassword = null;
		try {			
			dbUrl1 = SecurityUtility.getProperty(PROPERTY_FILE_WITH_PATH,"datasource.url");
			dbUrl2=SecurityUtility.getProperty(PROPERTY_FILE_WITH_PATH,"datasource.url2");
			dbUserName = SecurityUtility.getProperty(PROPERTY_FILE_WITH_PATH,"datasource.username");
			dbPassword = SecurityUtility.getProperty(PROPERTY_FILE_WITH_PATH,"datasource.password");
			
			con = ConnectionUtil.getConnection(dbUrl1, dbUserName, dbPassword, jdbcDriver);
			con2 = ConnectionUtil.getConnection(dbUrl2, dbUserName, dbPassword, jdbcDriver);
			XmlUtil xmlUtil = new XmlUtil();
			UrlFinder finder = new UrlFinder();
			SqlCreator sqlCreator = new SqlCreator();

			List<URL> iBatisFileUrls = finder.find(startDirectories, includePattern, excludePattern);
			for (URL iBatisFile : iBatisFileUrls) {
				Document doc = xmlUtil.createDocument(iBatisFile);
				List<Query> queries = sqlCreator.findCompleteSelect(doc);

				for (Query query : queries) {
					List<Rule> rulesFired;
					ExplainPlan explainPlan;
					String schema;
					Exception exc;
					try {
						schema = "supplier";
						explainPlan = handleExplain(query, con, schema);
						rulesFired = statistics.handleStatistics(new FullWrapper(query, schema, explainPlan));
						exc = null;
					} catch (java.sql.SQLException e) {
						try {
							schema = "account";
							explainPlan = handleExplain(query, con2, schema);
							rulesFired = statistics.handleStatistics(new FullWrapper(query, schema, explainPlan));
							exc = null;
						} catch (java.sql.SQLException e2) {
							exc = e2;
							schema = "unknown";
							rulesFired = statistics.handleStatistics(new FullWrapper(query, schema, e2));
							explainPlan = null;
						}
					}
					if(rulesFired != null && rulesFired.size() == rules.size()) {
						log(printWriter, iBatisFile, query, explainPlan, exc, rulesFired);
					}
				}
			}

			printWriter.write(statistics.toString());

		} finally {
			ConnectionUtil.closeNoException(con);
			printWriter.close();
			try{
				if(con!=null)
					con.close();
				
				if(con2!=null)
					con2.close();
				
			}
			catch(Exception e){
				logger.error("MainApp::execute::Error occured while closing connection", e);
			}
		}
	}

	private void log(PrintWriter printWriter, URL iBatisFile, Query query, ExplainPlan explainPlan, Exception exc, List<Rule> rulesFired) {
		StringBuilder sb = new StringBuilder();
		sb.append(iBatisFile.toString()).append("\nid=");
		sb.append(query.getId()).append("\n");
		sb.append(query.getSqlWithSubstitutedParameters());
		sb.append("\n");
		if(explainPlan != null) {
			String explainString = explainPlan.toString();
			sb.append(explainString).append("\n");
		}
		if(exc != null) {
			StringWriter sw = new StringWriter();
			exc.printStackTrace(new PrintWriter(sw));
			sb.append(sw.toString()).append("\n");
		}
		sb.append(lineBreak);
		sb.append(lineBreak);
		printWriter.write(sb.toString());
	}

	private ExplainPlan handleExplain(Query query, Connection con, String schema) throws SQLException {
		MySqlExplainPlanManager explainPlanManager = new MySqlExplainPlanManager();
		String sql = query.getSqlWithSubstitutedParameters();
		ExplainPlan explainPlan = explainPlanManager.runExplain(sql, con);
		return explainPlan;
	}

	/**
	 * 
	 * @param arg
	 */
	public static void main(String[] arg) {
		MainApp mainApp = new MainApp();
		try {
			mainApp.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
