package com.servicelive.esb.listener;

import java.io.File;
import java.io.FileFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.Service;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.listeners.ListenerTagNames;
import org.jboss.soa.esb.listeners.lifecycle.AbstractThreadedManagedLifecycle;
import org.jboss.soa.esb.listeners.lifecycle.ManagedLifecycleException;
import org.jboss.soa.esb.listeners.message.MessageDeliverException;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.util.FileArchiveException;
import com.servicelive.util.ArchiveFileUtil;
import com.servicelive.util.MarketESBUtil;

public class OrderInputRIGateway extends AbstractThreadedManagedLifecycle {
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(OrderInjectCustomGateway.class);
	
	/** Required by ESB
	 * 
	 */
    private ConfigTree listenerConfig;
    private Service service;
    private ServiceInvoker serviceInvoker;
    
    private final long ONE_SECOND = 1000;
    private final int DEFAULT_FREQUENCY = 5;
    private final String FREQUENCY="frequency";
    private final String DIRECTORY = "directory";
    private final String INPUT_SUFFIX = "input-suffix";
    private final long timeBetween2Msgs = 1000; // in ms
    
    private FileFilter fileFilter, esbFileFilter;
    private final int maxRetryCount = 5;
    
    private final long ONE_HOUR = 3600 * ONE_SECOND; //in ms
    boolean isRunning = true;
    private boolean takeTSfromFileName = true;
    String pattern = "yyyyMMdd.hhmmS";
	SimpleDateFormat format = new SimpleDateFormat(pattern);

    
    /**
     * Map to keep the list of files which gave an error recently while parsing.
     * There are two reason for parsing to fail.
     * 1) File is not an Order Injection file.
     * 2) Some XMl syntax error in the file.      
     * 3) File is being copied. It takes sometime to FTP a file. 
     *    If gateway starts processing file in between it will give XML parse error.
     * 	  In this situation  should retry parsing file after sometime. Check removeBadFile method for implementation.
     * 
     */
    HashMap<String, Integer> errorMap = new HashMap<String, Integer>();
    
    private String inFolder, inputSuffix, errorFolder;
    private String esbMessageSuffix = "startProcessESB"; //no period(.) please
    private String workSuffix = "work"; //no period(.) please
    
    private int frequency;
    
    public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	// Just for unit testing	
	/*
	public OrderInjectCustomGateway() throws ConfigurationException   {				
		 inFolder = "/home/sldev/test";	     
	} */
    
	public OrderInputRIGateway(final ConfigTree config) throws ConfigurationException {
        super(config);
        this.listenerConfig = config;
 
        String serviceCategory = listenerConfig.getRequiredAttribute(ListenerTagNames.TARGET_SERVICE_CATEGORY_TAG);
        String serviceName = listenerConfig.getRequiredAttribute(ListenerTagNames.TARGET_SERVICE_NAME_TAG);
                
        try {
        	frequency = Integer.parseInt(listenerConfig.getRequiredAttribute(FREQUENCY)); 
        } catch (java.lang.NumberFormatException n) {
        	throw new ConfigurationException("Property frequency is not configured");
        }
        
        inFolder = listenerConfig.getRequiredAttribute(DIRECTORY);
        inputSuffix = listenerConfig.getRequiredAttribute(INPUT_SUFFIX);
        errorFolder = listenerConfig.getRequiredAttribute(ListenerTagNames.FILE_ERROR_DIR_TAG);
        
        if (inFolder == null)
        	throw new ConfigurationException("Property directory is not configured");
        
        if (inputSuffix == null)
        	throw new ConfigurationException("Property input-suffix is not configured");
        
        if (inputSuffix.charAt(0) == '.')
        	inputSuffix = inputSuffix.substring(1);
        
        if (errorFolder == null) {
        	throw new ConfigurationException("Property " + ListenerTagNames.FILE_ERROR_DIR_TAG + " is not configured");
        } 
        if (this.errorFolder.endsWith("/") || this.errorFolder.endsWith("\\")) {
			this.errorFolder = this.errorFolder.substring(0, this.errorFolder.length() - 1);
		}
        
        createFileFilter();
        service = new Service(serviceCategory, serviceName);
    }
 
    protected void doInitialise() throws ManagedLifecycleException {
        // Create the ServiceInvoker instance for the target service....
        try {
            serviceInvoker = new ServiceInvoker(service);
        } catch (MessageDeliverException e) {
            throw new ManagedLifecycleException("Failed to create ServiceInvoker for Service '" + service + "'.");
        }
    }
 
    @Override
    protected void doRun() {
    	while(isRunning()) {
    		// Wait for a message....
    		String fileName = waitForPayload();
    		if (Thread.interrupted() == true) {
    			isRunning  = false;    			
    		}
    		
    		// Send the message to the target service's Action Pipeline via
    		// the ServiceInvoker...
    		if (fileName != null) {
    			try {
    				
    				createEsbMsg(fileName); 
    			} catch (InterruptedException e) {
    				logger.warn("Recevied InterruptedException:" + e);
    				isRunning = false;
    			} catch (Exception e) {
    				logger.error("Error while creating the ESB Message: ", e);
    				e.printStackTrace();
    				try {
						ArchiveFileUtil.archiveFile(fileName, errorFolder, null);
					} catch (FileArchiveException e1) {
						logger.error(e1.getMessage());
					}
    			}
    		}
    	}
    }
    
    private void memoryPrint() {
    	long mb = 1024 * 1024;
    	if (logger.isInfoEnabled()) {
    		logger.info("Total Memory(MB):" + Runtime.getRuntime().totalMemory() / mb +
    				" : Free memory(MB):" + Runtime.getRuntime().freeMemory() / mb);
    	}
    }
    
    private void createEsbMsg(String fileName) throws Exception {
    	// Rename the file with a .work extension
    	String workFileName = MarketESBUtil.renameInputFileToWorkFile(fileName, workSuffix);
    	
		// Create the ESB message 
		Message esbMessage = MessageFactory.getInstance().getMessage();    			
		esbMessage.getProperties().setProperty(MarketESBConstant.ORIGINAL_FILE_FEED_NAME, workFileName);  
		serviceInvoker.deliverAsync(esbMessage);
		Thread.sleep(timeBetween2Msgs); //sleeping for some time to avoid race condition.
		
    	memoryPrint();
		
		if (logger.isInfoEnabled())
		  logger.info("Created message");	
    }
 
    /**
     * Check hot folder every X seconds. If file present then marshal it.
     * @return
     */
    private String waitForPayload() {
    	String file = null;
    	File ff = null;
    	try {
    		if (frequency == 0) {
    			frequency = DEFAULT_FREQUENCY;
    		}
    		Thread.sleep(frequency * ONE_SECOND);	
    		
    		if (isESBMessageExist() == false) {
    			file = getFileName();
    		}
    		
    		if (file != null) {
    			
    			file = inFolder + "/" + file;
    			logger.info("Reading file " + file);
    			 ff = new File(file);
    		}    		
    		
    	} catch (Exception e) {
    		// It looks like file is not yet copied yet completely. Other reason could be that file is corrupt.    		
    		logger.warn("Error in reading file:" + file);
    		if (ff != null)
    		  removeBadFile(ff);
    	}
    	
    	return file;
    }
    
    /**
     * Remove file from disk if input file system is not able to parse the file even after 5 attempts.
     * We have to make sure that  FTP process to copy file from Sears server does not take more than
     * 5 * frequency time otherwise fill will be deleted without processing.
     * 
     * @param xmlFile
     */
    private void removeBadFile(File xmlFile) {    
    	if (xmlFile == null)
    		return;
    	String file = xmlFile.getName();
    	if (errorMap.size() > 1000) { //keep size in check
    		errorMap = new HashMap<String, Integer>();
    	}

    	Integer count = errorMap.get(file);    	
    	if (count == null) {
    		errorMap.put(file, 1);
    		return;
    	}
    	
    	if (count  < maxRetryCount) { //retry 5 times
    		count ++;
    		errorMap.put(file, count);
    	} else { //remove file from disk
    		if (xmlFile != null) {
    			logger.info("Removing corrupt file " + file + " without processing");
    			xmlFile.delete();
    		}
    		errorMap.remove(file);
    	}
    }     	
	
	private String getFileExtension(String inputFilefeedName) {		
    	String ext = inputFilefeedName.substring(inputFilefeedName.lastIndexOf('.')+1, inputFilefeedName.length());
    	return ext;		
    }

    private void createFileFilter() {    		
    	fileFilter = new FileFilter() {
    		public boolean accept(File f) {
        		if (f.isFile()) {
        			String ext = getFileExtension(f.getName());
        			if (ext.equals(inputSuffix))
    				return true;
    			} 
    			return false;
    		}};
    		
    	esbFileFilter = new FileFilter() {
        	public boolean accept(File f) {
        		if (f.isFile()) {
        			String ext = getFileExtension(f.getName());
        			if (ext.equals(esbMessageSuffix))
        	    		return true;        	    		
    }
        		return false;
        	}};
    }

    /**
     * Get oldest Order Injection file name present on the disk. 
     * @return
     */
    
    private String getFileName() {
		File folder = new File(inFolder);	
		File[] listOfFiles = folder.listFiles(fileFilter);
		String oldestFilename = null;
		long oldestTS = 0;

		for (int i = 0; i < listOfFiles.length; i++) {			
			long newTS = getTS(listOfFiles[i]);
			if (oldestTS == 0 || oldestTS > newTS) {	 
				oldestFilename = listOfFiles[i].getName();
				oldestTS = newTS;
			}
		}
		//System.out.println("Oldest TS:" + oldestTS);
		return oldestFilename;
	}
	
    private long getTS (File file) {
		String oldestFilename = file.getName();
		if (takeTSfromFileName) {
			String [] arr = oldestFilename.split("\\.");
			if (arr.length > 3 && arr[2].length() == 8) {
				try {
					Date dt = format.parse(arr[1]+ "." + arr[2]);
					//System.out.println("DT:" + dt);
					return dt.getTime();					
				} catch (ParseException e) {
					//System.out.println(e.getMessage());
				}
			}
		} 

		logger.warn("File name is non compliant to Sears.");
		return file.lastModified();
	}
    	
	/**
	 * This method is used to make sure that system is currently not in the 
	 * middle of processing any ESB message.  
	 * 
	 * @return
	 */
	private boolean isESBMessageExist() {		
		File folder = new File(inFolder);	
		File[] listOfFiles = folder.listFiles(esbFileFilter);
		if (listOfFiles != null && listOfFiles.length > 0 && listOfFiles[0] != null) {
			long fileTS = listOfFiles[0].lastModified();
			long now =  Calendar.getInstance().getTime().getTime();
			// delete file if it is older than 2 hours. looks like there is some problem 
			// process ESB message lets delete the file. and move on.
			if (now - fileTS > (ONE_HOUR * 2)) {
				logger.info("Removing corrupt esb file " + listOfFiles[0].getName());
				listOfFiles[0].delete();
			}
			logger.info("Not doing anything. ESB message exist:" + listOfFiles[0].getName());			
			return true;
		}
		return false;
	}

}
