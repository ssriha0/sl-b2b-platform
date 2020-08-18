package com.servicelive.integrationtest.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.hsqldb.lib.FileUtil;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.integrationtest.bo.IIntegrationBO;
import com.servicelive.integrationtest.bo.IServiceOrderBO;
import com.servicelive.integrationtest.domain.QueryResults;
import com.servicelive.integrationtest.domain.ServiceOrder;
import com.servicelive.integrationtest.domain.Transaction;
import com.servicelive.integrationtest.util.Configuration;
import com.servicelive.integrationtest.xmlConverter.QueryCellConverter;
import com.servicelive.integrationtest.xmlConverter.QueryResultsConverter;
import com.servicelive.integrationtest.xmlConverter.QueryRowConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class OutputReportAction extends ActionSupport implements Preparable {
	
	private static final String BENCHMARK_FILE_SUFFIX = ".benchmark";

	private static final long serialVersionUID = -1895526754377998395L;
	
	private Configuration config;
	private XStream xstream;
	
	private IIntegrationBO integrationBO;
	private IServiceOrderBO serviceOrderBO;
	
	private String fullPath;
	private String reportNameType;
	private String customName;
	private Map<String, List<Transaction>> transactionMap = new HashMap<String, List<Transaction>>();
	
	private List<String> hsrFileName = new ArrayList<String>();
	private List<String> hsrOrderNum = new ArrayList<String>();
	
	private List<String> omsFileName = new ArrayList<String>();
	private List<String> omsOrderNum = new ArrayList<String>();
	
	private List<String> assurantFileName = new ArrayList<String>();
	private List<String> assurantOrderNum = new ArrayList<String>();
	
	public void prepare() {
		xstream = new XStream(new DomDriver());
		xstream.registerConverter(new QueryCellConverter());
		xstream.registerConverter(new QueryRowConverter());
		xstream.registerConverter(new QueryResultsConverter());
		xstream.alias("queryResults", QueryResults.class);
	}
	
	public String execute() {
		
		String reportName;
		if (reportNameType == null) {
			this.addActionError("No batch name type selected.");
			return ERROR;
		}
		if (reportNameType.equals("custom") && customName.length() > 0) {
			reportName = customName;
		} else if (reportNameType.equals("datetime")) {
			SimpleDateFormat dateFileFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
			reportName = dateFileFormat.format(new Date());
		} else {
			this.addActionError("No batch name supplied.");
			return ERROR;
		}
		
		Map<String, List<String>> hsrFileMap = createFileMap(hsrFileName, hsrOrderNum);
		Map<String, List<String>> omsFileMap = createFileMap(omsFileName, omsOrderNum);
		Map<String, List<String>> assurantFileMap = createFileMap(assurantFileName, assurantOrderNum);
		
		fullPath = config.getOutputRootFolder() + reportName;
		
		// if the folder already exists, append 2 to its name (or 3, and so on)
		if (FileUtil.exists(fullPath)) {
			String originalFullPath = fullPath;
			int suffix = 2;
			while (FileUtil.exists(fullPath)) {
				fullPath = originalFullPath + " " + suffix;
				suffix++;
			}
		}
		
		
		boolean hsrResults = outputResults(hsrFileMap, "hsr");
		boolean omsResults = outputResults(omsFileMap, "oms");
		boolean assurantResults = outputResults(assurantFileMap, "assurant");
		
		if (!hsrResults && !omsResults && !assurantResults) {
			this.addActionError("None of the transactions could be found in the database, so no results were produced. Refresh this page to try retrieving the transactions again.");
		}
		
		return SUCCESS;
		
	}
	
	private Map<String, List<String>> createFileMap(List<String> fileNames, List<String> orderNums) {
		Map<String, List<String>> fileMap = new TreeMap<String, List<String>>();
		
		for (int i = 0; i < fileNames.size(); i++) {
			List<String> orderNumbers;
			if (!fileMap.containsKey(fileNames.get(i))) {
				orderNumbers = new ArrayList<String>();
				fileMap.put(fileNames.get(i), orderNumbers);
			} else {
				orderNumbers = fileMap.get(fileNames.get(i));
			}
			orderNumbers.add(orderNums.get(i));
		}
		
		return fileMap;
	}
	
	private boolean outputResults(Map <String, List<String>> testMap, String directoryName) {
		boolean anyResultsOutput = false;
		
		for (String fileName : testMap.keySet()) {
			
			Map<String, List<QueryResults>> masterMap = new TreeMap<String, List<QueryResults>>();
			
			List<Transaction> transactions = integrationBO.retrieveTransactions(testMap.get(fileName));
			if (transactions.size() == 0) {
				this.addActionError("No transaction(s) from file " + fileName + " not found in database.");
				continue;
			}
			if (transactions.size() < testMap.get(fileName).size()) {
				this.addActionError("Some transaction(s) from file " + fileName + " not found in database.");
			}
			anyResultsOutput = true;
			
			List<ServiceOrder> serviceOrders = new ArrayList<ServiceOrder>();
			
			for (Transaction transaction : transactions) {
				if (transaction.getServiceLiveOrderId() != null) {
					ServiceOrder order = new ServiceOrder();
					order.setOrderId(transaction.getServiceLiveOrderId());
					order.setExternalOrderNum(transaction.getExternalOrderNumber());
					serviceOrders.add(order);
				}
			}
			transactionMap.put(fileName, transactions);
			
			for (ServiceOrder serviceOrder : serviceOrders) {
				masterMap.put(serviceOrder.getExternalOrderNum().substring(0, 2), serviceOrderBO.runAllQueriesForServiceOrder(serviceOrder.getOrderId()));
			}
			
			String filePath = fullPath + "/" + directoryName + "/";
			
			writeOutputToFiles(masterMap, filePath, fileName);
			
			File origBenchmark = new File(this.getClass().getClassLoader().getResource(directoryName).getPath() + fileName + BENCHMARK_FILE_SUFFIX);
			File newBenchmark = new File(filePath + fileName + "-benchmark.xml");
			
			if (origBenchmark.exists()) {
				try {
					FileUtils.copyFile(origBenchmark, newBenchmark);
				} catch (IOException e) {
					this.addActionError("Error copying benchmark file for file " + fileName + ".");
					e.printStackTrace();
				}
			} else {
				this.addActionError("Benchmark file not found for file " + fileName + ".");
			}
			
		}
		return anyResultsOutput;
	}
	
	private void writeOutputToFiles(Map<String, List<QueryResults>> testMap, String filePath, String fileName) {
		
		String xmlTestOutput = xstream.toXML(testMap);
		
        try {
        	File outTestFile = new File(filePath + fileName + "-test.xml");
        	FileUtils.writeStringToFile(outTestFile, xmlTestOutput);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setIntegrationBO(IIntegrationBO integrationBO) {
		this.integrationBO = integrationBO;
	}

	public IIntegrationBO getIntegrationTestBO() {
		return integrationBO;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setHsrFileName(List<String> hsrFileName) {
		this.hsrFileName = hsrFileName;
	}

	public List<String> getHsrFileName() {
		return hsrFileName;
	}

	public void setHsrOrderNum(List<String> hsrOrderNum) {
		this.hsrOrderNum = hsrOrderNum;
	}

	public List<String> getHsrOrderNum() {
		return hsrOrderNum;
	}

	public void setOmsFileName(List<String> omsFileName) {
		this.omsFileName = omsFileName;
	}

	public List<String> getOmsFileName() {
		return omsFileName;
	}

	public void setOmsOrderNum(List<String> omsOrderNum) {
		this.omsOrderNum = omsOrderNum;
	}

	public List<String> getOmsOrderNum() {
		return omsOrderNum;
	}

	public void setConfig(Configuration config) {
		this.config = config;
	}

	public Configuration getConfig() {
		return config;
	}

	public void setAssurantFileName(List<String> assurantFileName) {
		this.assurantFileName = assurantFileName;
	}

	public List<String> getAssurantFileName() {
		return assurantFileName;
	}

	public void setAssurantOrderNum(List<String> assurantOrderNum) {
		this.assurantOrderNum = assurantOrderNum;
	}

	public List<String> getAssurantOrderNum() {
		return assurantOrderNum;
	}

	public void setReportNameType(String reportNameType) {
		this.reportNameType = reportNameType;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public void setTransactionMap(Map<String, List<Transaction>> transactionMap) {
		this.transactionMap = transactionMap;
	}

	public Map<String, List<Transaction>> getTransactionMap() {
		return transactionMap;
	}

}
