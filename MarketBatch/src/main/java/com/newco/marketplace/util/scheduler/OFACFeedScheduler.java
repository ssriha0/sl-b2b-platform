
package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.ach.IOFACFile;

/**
 * 
 * $Revision: 1.2 $ $Author: akashya $ $Date: 2008/05/21 22:54:24 $
 *
 */
public class OFACFeedScheduler extends ABaseScheduler implements Job {
	
	private static final Logger logger = Logger.getLogger(OFACFeedScheduler.class.getName());
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			
		        ApplicationContext applicationContext = getApplicationContext(context);
		        IOFACFile ofacFileImpl  = (IOFACFile)applicationContext.getBean("ofacFile");
		        ofacFileImpl.writeOFACRecords();
				logger.info("OFAC feed Scheduler--> Completed: "+new Date());
		}
		catch(Exception e){
			logger.info("Caught Exception and ignoring",e);
		}
	}
	
	public static void main(String args[]){
		   ApplicationContext applicationContext = MPSpringLoaderPlugIn.getCtx();
		   IOFACFile myClass  = (IOFACFile)applicationContext.getBean("ofacFile");
		   try {
		    	//Calendar calendar= new GregorianCalendar();
				//Date todaysDate=calendar.getTime();
				//DateFormat formatter = new SimpleDateFormat( "MM/dd/yyyy" );
		        //String strTodaysDate = formatter.format(todaysDate);
		      	myClass.writeOFACRecords();
				
			} catch (Exception e) {
				logger.info("Caught Exception and ignoring",e);
			}
		   
	 }

}
