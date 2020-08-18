package com.servicelive.integrationtest.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.servicelive.integrationtest.util.Configuration;


public class SendToEsbAction extends ActionSupport {
	private static final long serialVersionUID = -1449941329706301520L;
	
	private Configuration config;
	private List<String> hsrList = new ArrayList<String>();
	private List<String> omsList = new ArrayList<String>();
	private List<String> assurantList = new ArrayList<String>();
	
	private List<String> hsrFiles = new ArrayList<String>();
	private List<String> omsFiles = new ArrayList<String>();
	private List<String> assurantFiles = new ArrayList<String>();
	
	private List<String> hsrOrders = new ArrayList<String>();
	private List<String> omsOrders = new ArrayList<String>();
	private List<String> assurantOrders = new ArrayList<String>();
	

	@Override
	public String execute() {
		
		if (hsrList.size() == 0 && omsList.size() == 0 && assurantList.size() == 0) {
			this.addActionError("No test files selected.");
			return ERROR;
		}
		
		List<String> hsrShortNames = new ArrayList<String>();
		for (String filename : hsrList) {
			File hsrFile = new File(filename);
			String shortFilename = hsrFile.getName().substring(0, hsrFile.getName().length() - 5);
			hsrShortNames.add(shortFilename);
			
			File newFile = new File(config.getHsrEsbInbox() + shortFilename);
			
			List<String> orderNums = writeFileAndGetOrderReferenceNumbers(hsrFile, newFile);
			for (String orderNum : orderNums) {
				hsrFiles.add(shortFilename);
				hsrOrders.add(orderNum);
			}
		}
		hsrList = hsrShortNames;
		
		List<String> omsShortNames = new ArrayList<String>();
		for (String filename : omsList) {
			File omsFile = new File(filename);
			String shortFilename = omsFile.getName().substring(0, omsFile.getName().length() - 5);
			omsShortNames.add(shortFilename);
			
			File newFile = new File(config.getOmsEsbInbox() + shortFilename);
			
			List<String> orderNums = writeFileAndGetOrderReferenceNumbers(omsFile, newFile);
			for (String orderNum : orderNums) {
				omsFiles.add(shortFilename);
				omsOrders.add(orderNum);
			}
		}
		omsList = omsShortNames;
		
		List<String> assurantShortNames = new ArrayList<String>();
		for (String filename : assurantList) {
			File assurantFile = new File(filename);
			String shortFilename = assurantFile.getName().substring(0, assurantFile.getName().length() - 5);
			assurantShortNames.add(shortFilename);
			
			File newFile = new File(config.getAssurantEsbInbox() + shortFilename);
			
			List<String> orderNums = writeFileAndGetOrderReferenceNumbers(assurantFile, newFile);
			for (String orderNum : orderNums) {
				assurantFiles.add(shortFilename);
				assurantOrders.add(orderNum);
			}
		}
		assurantList = assurantShortNames;
		
		return SUCCESS;
	}
	
	private List<String> writeFileAndGetOrderReferenceNumbers(File origFile, File newFile) {
		List<String> orderNumbers = new ArrayList<String>();
		List<String> unitNumbers = new ArrayList<String>();
		
		try {
			// read the file
			@SuppressWarnings("unchecked")
			List<String> lines = FileUtils.readLines(origFile);

			for (String eachLine : lines) {
				String trimmedLine = eachLine.trim();
				if (trimmedLine.length() > 0) {
					if (trimmedLine.indexOf("#UNITNUM#") != -1) {
						unitNumbers.add(getRandomString(unitNumbers.size(), 8));
					}
					if (trimmedLine.indexOf("#ORDERNUM#") != -1) {
						orderNumbers.add(getRandomString(orderNumbers.size(), 8));
					}
				}
			}
			
			if (unitNumbers.size() == 0 && orderNumbers.size() == 0) {
				this.addActionError(String.format("The file '%s' does not appear to be valid and was not sent to the ESB. It has %d #UNITNUM# tokens and %d #ORDERNUM# tokens, when an equal number of each were expected.", origFile.getName(), unitNumbers.size(), orderNumbers.size()));
			}
			else if (unitNumbers.size() > 0 && orderNumbers.size() > 0 && unitNumbers.size() != orderNumbers.size()) {
				this.addActionError(String.format("The file '%s' does not appear to be valid and was not sent to the ESB. It has no #UNITNUM# and #ORDERNUM# tokens, when at least one was expected.",origFile.getName()));
			}
			else {
				// write the output file to be processed by the ESB
				FileUtils.writeLines(newFile, lines);
			}
			
		} catch (IOException e) {
			this.addActionError(String.format("The following unexpected error occurred: '%s'. Please consult the appliction log files for more details.", e.getMessage()));
			e.printStackTrace();
		}
		
		return getExternalOrderNumbers(orderNumbers, unitNumbers);
	}

	private List<String> getExternalOrderNumbers(List<String> orderNumbers, List<String> unitNumbers) {
		List<String> externalOrderNumbers = new ArrayList<String>(Math.max(orderNumbers.size(), unitNumbers.size()));
		for (int i = 0; i < Math.max(orderNumbers.size(), unitNumbers.size()); i++) {
			String unitNumber = unitNumbers.size() > i ? unitNumbers.get(i) : "";
			String orderNumber = orderNumbers.size() > i ? orderNumbers.get(i) : "";
			externalOrderNumbers.add(unitNumber + orderNumber);
		}
		return externalOrderNumbers;
	}

	private String getRandomString(int radix, int length) {
		Random r = new Random();
		String token = Long.toString(Math.abs(r.nextLong()), 36);
		String unitNumber = String.format("%02d%s", radix, token.substring(0, length-2));
		return unitNumber;
	}
	
	
	public List<String> getHsrList() {
		return hsrList;
	}

	public List<String> getOmsList() {
		return omsList;
	}

	public List<String> getAssurantList() {
		return assurantList;
	}

	public void setHsrList(List<String> hsrList) {
		this.hsrList = hsrList;
	}
	public void setOmsList(List<String> omsList) {
		this.omsList = omsList;
	}
	public void setAssurantList(List<String> assurantList) {
		this.assurantList = assurantList;
	}
	public void setConfig(Configuration config) {
		this.config = config;
	}
	
	public List<String> getHsrFiles() {
		return hsrFiles;
	}
	public List<String> getOmsFiles() {
		return omsFiles;
	}
	public List<String> getAssurantFiles() {
		return assurantFiles;
	}
	public List<String> getHsrOrders() {
		return hsrOrders;
	}
	public List<String> getOmsOrders() {
		return omsOrders;
	}
	public List<String> getAssurantOrders() {
		return assurantOrders;
	}

}
