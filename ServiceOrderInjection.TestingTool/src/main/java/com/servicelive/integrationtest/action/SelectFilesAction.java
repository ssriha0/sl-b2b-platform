package com.servicelive.integrationtest.action;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class SelectFilesAction extends ActionSupport implements Preparable {

	private static final String ASSURANT_DATA_FILES_LOCATION = "assurant";
	private static final String HSR_DATA_FILES_LOCATION = "hsr";
	private static final String OMS_DATA_FILES_LOCATION = "oms";

	private static final String DATA_FILE_SUFFIX = ".data";

	private static final long serialVersionUID = -7326565695726700417L;
	
	private Map<String, String> assurantFiles = new TreeMap<String, String>();
	private Map<String, String> hsrFiles = new TreeMap<String, String>();
	private Map<String, String> omsFiles = new TreeMap<String, String>();
	
	public void prepare() {
		// The data and benchmark locations could be externalized and configured
		// through the properties file if it becomes inconvenient to have them
		// as part of the deployabled.
		//
		// The reason to keep them in the deployable is that there should be
		// some controlled process around changing the data and benchmark
		// files, as they establish the standard for correctness of the
		// system.
		URL assurantFolder = this.getClass().getClassLoader().getResource(ASSURANT_DATA_FILES_LOCATION);
		URL hsrFolder = this.getClass().getClassLoader().getResource(HSR_DATA_FILES_LOCATION);
		URL omsFolder = this.getClass().getClassLoader().getResource(OMS_DATA_FILES_LOCATION);
		
		addDataFilesToList(assurantFolder, assurantFiles);
		addDataFilesToList(hsrFolder, hsrFiles);
		addDataFilesToList(omsFolder, omsFiles);
		
	}
	
	public String execute() {
	
		return SUCCESS;
	}
	
	private void addDataFilesToList(URL folder, Map<String, String> fileList) {
		
		File file = new File(folder.getPath());
		File[] files = file.listFiles();
		
		for (File foundFile : files) {
			if (foundFile.getName().endsWith(DATA_FILE_SUFFIX)) {
				fileList.put(foundFile.getPath(), foundFile.getName().substring(0, foundFile.getName().length() - 5));
			}
		}
	}

	public Map<String, String> getAssurantFiles() {
		return assurantFiles;
	}

	public void setAssurantFiles(Map<String, String> assurantFiles) {
		this.assurantFiles = assurantFiles;
	}

	public Map<String, String> getHsrFiles() {
		return hsrFiles;
	}

	public void setHsrFiles(Map<String, String> hsrFiles) {
		this.hsrFiles = hsrFiles;
	}

	public Map<String, String> getOmsFiles() {
		return omsFiles;
	}

	public void setOmsFiles(Map<String, String> omsFiles) {
		this.omsFiles = omsFiles;
	}
}
