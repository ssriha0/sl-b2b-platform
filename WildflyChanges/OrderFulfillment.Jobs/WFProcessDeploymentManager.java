package com.servicelive.orderfulfillment.jbpm;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.jbpm.api.Deployment;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.RepositoryService;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;

/*
* 
* This singleton bean is called once at container instantiation time
* It checks jBPM repository and automatically deploys process files if
* they have changed
* 
* Process files are injected by Spring as a resource list
* 
* Current deployment strategy:
* - deployment name = process file name
* - deployment is triggered if file content is different to the content in the DB
* 
*/
public class WFProcessDeploymentManager
{
	private List<Resource> processFiles;
	private RepositoryService repositoryService;
	protected final Logger logger = Logger.getLogger(getClass());
	
	WFProcessDeploymentManager(RepositoryService rs, List<Resource> files) throws Exception {
		repositoryService = rs;
		processFiles = files;

		try {
			deploy();
		} catch(Exception e) {
			logger.error(e.getMessage());
            //I think we should stop the deployment because the new workflow is not deployed
            //it could cause unusual errors
            throw e;
		}
	}
	
	@Transactional
	private void deploy() throws Exception
	{
		logger.info("got " + processFiles.size() + " numbers of process files");
	    for (Resource pf : processFiles)
	    {
	    	// deployment name = process file name
    		String deploymentName = pf.getFilename();
    		logger.info(">> processing file name " + deploymentName);
    		
    		// find latest deployment with the name matching our process file name
    		List<Deployment> listOfDeployments = repositoryService.createDeploymentQuery().list();
    		long latestDeployment=0L;
    		String latestDeploymentId="";
    		for (Deployment d : listOfDeployments) {
    			if (deploymentName.equals(d.getName()) && d.getTimestamp()>latestDeployment) {
    					latestDeployment = d.getTimestamp();
    					latestDeploymentId=d.getId();
    			}
    		}
    		logger.info("latest deployment:"+latestDeploymentId);
    		
    		// should we trigger deployment of the file?
    		boolean isDeploymentNeeded=true;
    		
    		// first let's read content of the file we are considering to deploy 
	    	InputStream pfStream = pf.getInputStream();
	    	//JBOSS Upgrade
	    	String xmlString="";
	    	char current1;
		      while (pfStream.available() > 0) {
		        current1 = (char) pfStream.read();    
		        xmlString+=current1;  
		      }
            logger.info("xmlString"+xmlString.length());
            InputStream pfFileStream =new ByteArrayInputStream( xmlString.getBytes( "UTF-8" ));
            
    		byte fileContent[]=new byte[pfFileStream.available()];
    		pfFileStream.read(fileContent);
    		logger.info("file content length: "+fileContent.length);
    		
    		// if there is an existing deployment, then compare our file to it
    		if (latestDeployment!=0L) {
    			InputStream dStream = repositoryService.getResourceAsStream(latestDeploymentId, deploymentName);
    			if (dStream != null) {
	     			byte deployedContent[] = new byte[dStream.available()];
	    			dStream.read(deployedContent);
	        		logger.info("deployed content length: "+deployedContent.length);
	    			
	    			// if the process file hasn't changed, then we don't need to deploy
	    			if (Arrays.equals(fileContent, deployedContent)) isDeploymentNeeded=false;
	    		}
    		}
    		
    		logger.info("deployment is needed = "+isDeploymentNeeded);
    		if (isDeploymentNeeded & fileContent.length>0) {
    		    NewDeployment deployment = repositoryService.createDeployment();
    		    deployment.setName(deploymentName);
    		    deployment.setTimestamp(System.currentTimeMillis());
	    		deployment.addResourceFromString(deploymentName, new String(fileContent)) ;
	    		deployment.deploy();
	    		logger.info("process file " + deploymentName + " deployed");
    		}
	    }
	}
}
