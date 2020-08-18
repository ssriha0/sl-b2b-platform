package com.servicelive.orderfulfillment.decision;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.utils.DateUtils;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.domain.spn.network.SPNTierMinutes;
import com.servicelive.orderfulfillment.common.DateUtil;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

import org.apache.log4j.Logger;
import org.jbpm.api.model.OpenExecution;

import sun.util.calendar.CalendarUtils;

public class RoutingBehaviourCheck extends AbstractServiceOrderDecision {
    /**
	 * 
	 */
	private static final long serialVersionUID = 250204626285590792L;
	protected final Logger logger = Logger.getLogger(getClass());

	public String decide(OpenExecution execution) {
		logger.info("Inside RoutingBehaviourCheck");
		ServiceOrder so = getServiceOrder(execution);
		Integer tier = (Integer)execution.getVariable(ProcessVariableUtil.AUTO_ROUTING_TIER);
		Integer currentTier = so.getSOWorkflowControls().getCurrentTier();
		Integer nextTier = so.getSOWorkflowControls().getNextTier();
		logger.info("Inside RoutingBehaviourCheck>>currentTier"+currentTier);
		logger.info("Inside RoutingBehaviourCheck>>nextTier"+nextTier);
		logger.info("Inside RoutingBehaviourCheck>>tierId"+tier);
		Integer minutesUntilNextTier = 0;
		Integer daysUntilNextTier = 0;
		Integer hoursUntilNextTier = 0;
		int routedProviders = 0;
		int routingEligibleProviders = 0;
		execution.setVariable(ProcessVariableUtil.TO_DRAFT,false);
		if(null!=currentTier){
			routedProviders = so.getRoutedResources().size();
			routingEligibleProviders = so.getTierRoutedResources().size();
		}
		 if(null==currentTier 
				|| (null!= currentTier && null != nextTier && (routedProviders==routingEligibleProviders) && null!= so.getSOWorkflowControls().getMpOverFlow() && !(so.getSOWorkflowControls().getMpOverFlow()))
				|| (null!= currentTier && null == nextTier && null!= so.getSOWorkflowControls().getMpOverFlow() && !(so.getSOWorkflowControls().getMpOverFlow()))){
		execution.setVariable(ProcessVariableUtil.TO_DRAFT,true);
		return "autoRouted";
		}else{
			Integer provInCurrentTier = (Integer)execution.getVariable(ProcessVariableUtil.PROVIDERS_IN_CURRENT_TIER);
			Integer provInPrevTiers = (Integer)execution.getVariable(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS);
			if(provInCurrentTier == null || provInPrevTiers == null){
				SPNHeader hdr = getSPNTierDetails(so.getSpnId());
				List<SPNTierMinutes> tierMinutes = hdr.getTierMinutes();
				int provs = 0;
				int currTierProvs = 0;
				for(SPNTierMinutes tierDetails:tierMinutes){
					Integer id = (Integer) tierDetails.getSpnTierPK().getTierId().getId();
					int i = id.intValue();
					logger.info("id"+i);
					logger.info("currentTier.intValue()"+currentTier.intValue());
					if(null!=nextTier && i== nextTier.intValue()){
					provs = tierDetails.getNoOfMembers();
					}
					else if(i==currentTier.intValue()){
						if(null!= tierDetails.getAdvancedDays() && tierDetails.getAdvancedDays().intValue()!=0){
							daysUntilNextTier = tierDetails.getAdvancedDays();
						}
						else if(null!=  tierDetails.getAdvancedHours() && tierDetails.getAdvancedHours().intValue()!=0){
							hoursUntilNextTier = tierDetails.getAdvancedHours();
						}
						else if(null!=  tierDetails.getAdvancedMinutes() && tierDetails.getAdvancedMinutes().intValue()!=0){
							minutesUntilNextTier = tierDetails.getAdvancedMinutes();
						}
					}
					/*if(i== currentTier.intValue()){
					currTierProvs = tierDetails.getNoOfMembers();
					}*/
				}
				execution.setVariable(ProcessVariableUtil.AUTO_ROUTING_TIER, currentTier);
				execution.setVariable(ProcessVariableUtil.PROVIDERS_IN_CURRENT_TIER, provs);
				//execution.setVariable(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS, currTierProvs);
			}
			logger.info("provInCurrentTier"+(Integer)execution.getVariable(ProcessVariableUtil.PROVIDERS_IN_CURRENT_TIER));
			logger.info("provInPrevTiers"+(Integer)execution.getVariable(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS));
			if((execution.getVariable(ProcessVariableUtil.RELEASE_ACTION_PERFORMED_IND)==null) || (execution.getVariable(ProcessVariableUtil.RELEASE_ACTION_PERFORMED_IND)!=null && ((Integer.parseInt((String)execution.getVariable(ProcessVariableUtil.RELEASE_ACTION_PERFORMED_IND)))!=1))){
				Calendar farFutureDate = new GregorianCalendar();
				String farFutureDateString = convertToJPDLDueDate(farFutureDate);
				System.out.println("date>>"+farFutureDateString);
				logger.info("RELEASE_ACTION_PERFORMED_IND is null or not equal to 1");
				execution.createVariable(ProcessVariableUtil.AUTO_TIER_ROUTE_DATE, farFutureDateString);
				execution.setVariable(ProcessVariableUtil.REJECTED_BY_ALL_PROV, "true");
			}
			else if(execution.getVariable(ProcessVariableUtil.RELEASE_ACTION_PERFORMED_IND)!=null 
					&& ((Integer.parseInt((String)execution.getVariable(ProcessVariableUtil.RELEASE_ACTION_PERFORMED_IND)))==1)){
            		String toBeRoutedDateString = "";
            		
            		
            		logger.info(" inside release tier performed action ");
            		// code to findout the tier to be routed
            		
        			Date initialTierPostedDate = so.getInitialRoutedDate();   
        			logger.info("initialTierPostedDate " +initialTierPostedDate);   
        			
            		SPNHeader hdr = getSPNTierDetails(so.getSpnId());

        			// get the details of all the SPN related tier information.
    				List<SPNTierMinutes> tierMinutes = hdr.getTierMinutes();
    				int provs = 0;
    				int totalNumberOfTierToBeRouted=0;
    				Map<Integer,Date> tierTimeDetails=new HashMap<Integer,Date>();
    				//setting the size of tier to be routed.     
    				totalNumberOfTierToBeRouted=tierTimeDetails.size(); 
    			
    				Calendar newcalendar = new GregorianCalendar();
    				newcalendar.setTime(initialTierPostedDate);
    				int lastTierId=0;
    				Date lastTierCompletionDate = null;
    				for(SPNTierMinutes tierDetails:tierMinutes){    
    					
    					
    					
    					logger.info(" tierDetails.getAdvancedMinutes()  "+tierDetails.getAdvancedMinutes().intValue());
    					Integer id = (Integer) tierDetails.getSpnTierPK().getTierId().getId();
    					int i = id.intValue();
    					
    					
    					Integer minutesTillNextTier = 0;
    					Integer daysTillNextTier = 0;
    					Integer hoursTillNextTier = 0; 
    					
    					
    					if(null!= tierDetails.getAdvancedDays() && tierDetails.getAdvancedDays().intValue()!=0){
    						logger.info(" inside days  ");
    						daysTillNextTier= tierDetails.getAdvancedDays();
    						}
    						if(null!=  tierDetails.getAdvancedHours() && tierDetails.getAdvancedHours().intValue()!=0){
        						logger.info(" inside hours  ");
        						hoursTillNextTier = tierDetails.getAdvancedHours();
    						}
    						if(null!=  tierDetails.getAdvancedMinutes() && tierDetails.getAdvancedMinutes().intValue()!=0){
        						logger.info(" inside minutes  ");
        						minutesTillNextTier	= tierDetails.getAdvancedMinutes();
    						}
    						
                			logger.info("minutesUntilNextTier:"+minutesTillNextTier.intValue());
                			logger.info("hoursUntilNextTier:"+hoursTillNextTier.intValue());  
                			logger.info("daysUntilNextTier:"+daysTillNextTier.intValue());
                		    if(minutesTillNextTier.intValue()!=0){
                		    	logger.info("Inside IFFFF");
                		    	newcalendar.add(Calendar.MINUTE,minutesTillNextTier.intValue());
                		    }
                		    else if(hoursTillNextTier.intValue() != 0){
                		    	newcalendar.add(Calendar.HOUR, hoursTillNextTier.intValue());
                		    }
                		    else if(daysTillNextTier.intValue()!=0){
                		    	newcalendar.add(Calendar.MINUTE,(daysTillNextTier.intValue()*24));
                		    }
                		  
                			Date dateValue = newcalendar.getTime();
    			
                			tierTimeDetails.put(id, dateValue);
                			logger.info("Tierid "+i);
                			logger.info("dateValue "+dateValue);    
                			lastTierId=id; 
                			lastTierCompletionDate=dateValue;
    				}  
    				
    				Date currentTime = new Date();
        			Integer tierToBeRouted=0;
        			boolean nowAfterTieredTime=false;
        			int count=0;  
        			int nextTierToBeRouted=0;
        			boolean timeElapsed = false;
        			int counting=0;
    				for(SPNTierMinutes tierDetails:tierMinutes){
    					count=count+1;
    				if((currentTime).after(tierTimeDetails.get(tierDetails.getSpnTierPK().getTierId().getId()))){
    					timeElapsed = true;
        				logger.info("Inside now is after the date to be routed");       				 	
        				//nextTierToBeRouted=;
        				nextTierToBeRouted=(Integer)tierDetails.getSpnTierPK().getTierId().getId();
        				counting=count;
    				}
    				/*else{
    					break;
    				}*/
    				}
    				logger.info("timeElapsed>>"+timeElapsed);
    				timeElapsed = true;
    				//this block needs to be executed only if time has been elapsed
    				if(timeElapsed){
    				logger.info("nextTierToBeRouted "+nextTierToBeRouted);
    				int countNext=0;
    				for(SPNTierMinutes tierDetails:tierMinutes){
    					countNext=countNext+1;
    					if(countNext>counting){
    						
    						tierToBeRouted=(Integer) tierDetails.getSpnTierPK().getTierId().getId();
    						logger.info("found tierToBeRouted"+tierToBeRouted); 
    						break;
    					}    					
    				}  
    				
    				// the current and tier to be routed are same, find the next tier.
    				int findTheNextTier=0;
    				int countNextTier=0;
    				for(SPNTierMinutes tierDetails:tierMinutes){
    					countNextTier=countNextTier+1;
    					if(countNextTier>countNext){
    						
    						findTheNextTier=(Integer) tierDetails.getSpnTierPK().getTierId().getId();
    						logger.info("findTheNextTier"+tierToBeRouted); 
    						break;
    					}    					
    				}  
    				
    				// marketplace overflow
    					/*if(null==nextTier){
            				nextTier=9999;
            			}*/
    					

        			logger.info(" tierToBeRouted = "+tierToBeRouted);

        			// to handle release scenario if order is accepted and release at the same tier
        			if(currentTier==tierToBeRouted.intValue())
        			{
        				logger.info("current tier == tier to be routed");
        			// need to set the tier date of next tier accurately.
        			logger.info("tierToBeRouted = "+tierToBeRouted);
                    	
        				    
        				if(findTheNextTier!=0){      
        					logger.info("5. findTheNextTier info "+tierTimeDetails.get(tierToBeRouted));
        				Calendar newcalendarTier = new GregorianCalendar();
        				newcalendarTier.setTime(tierTimeDetails.get(tierToBeRouted));
        				String toBeRoutedDateStringNext = ProcessVariableUtil.convertToJPDLDueDate(newcalendarTier);
        				execution.setVariable(ProcessVariableUtil.AUTO_TIER_ROUTE_DATE, toBeRoutedDateStringNext);
        				execution.setVariable(ProcessVariableUtil.RELEASE_ACTION_PERFORMED_IND,0);
        				}else if(currentTier==lastTierId){
        					logger.info("currentTier==lastTierId");
                			if(null!=lastTierCompletionDate){
                			Calendar newcalendarTier = new GregorianCalendar();
            				newcalendarTier.setTime(lastTierCompletionDate);
            				String toBeRoutedDateStringNext = ProcessVariableUtil.convertToJPDLDueDate(newcalendarTier);
            				execution.setVariable(ProcessVariableUtil.AUTO_TIER_ROUTE_DATE, toBeRoutedDateStringNext);
            				execution.setVariable(ProcessVariableUtil.RELEASE_ACTION_PERFORMED_IND,0);
                			}
        				}
        				
        				return "tierRouted";
        			} 
        			
        			
        			// consider 4 tier
        			// 9-9.03,9.03-9.06,9.06-9.09,9.09-9.12,9.12--->marketplace 
        			// 9.015 the order is released.
        			if(nextTier.intValue()>tierToBeRouted.intValue() && tierToBeRouted.intValue()!=0)
        			{
        				logger.info(" 1.tierTimeDetails.get(tierToBeRouted) "+ tierTimeDetails.get(tierToBeRouted));
        				// auto and next tier will be 9.03
        				Calendar newcalendarTier = new GregorianCalendar();
        				newcalendarTier.setTime(tierTimeDetails.get(tierToBeRouted));
        				String toBeRoutedDateStringNext = ProcessVariableUtil.convertToJPDLDueDate(newcalendarTier);
        				execution.setVariable(ProcessVariableUtil.AUTO_TIER_ROUTE_DATE, toBeRoutedDateStringNext);
        				
        				
        				//execution.setVariable(ProcessVariableUtil.NEXT_TIER_DUE_DATE, toBeRoutedDateStringNext);
       				
        			}
        			// 9.045 the order is released  / 9.075 order is released 
        			else if(nextTier.intValue()<=tierToBeRouted.intValue())
        			{
        				
        				Calendar farFutureDate = new GregorianCalendar();
        				logger.info("route to next tier now"+tierToBeRouted);
        				
        				logger.info("2. farFutureDate "+farFutureDate);
        				
        				// 9.045 now it should be routed to tier2 / 9.075 it should be routed to tier 3
        				String routedString=ProcessVariableUtil.convertToJPDLDueDate(farFutureDate);
                    	execution.setVariable(ProcessVariableUtil.AUTO_TIER_ROUTE_DATE, routedString); 
        				// 9.06 it should be routed to tier 3./ 9.105 it should routed to tier 4
                    	
        				logger.info("3. tierTimeDetails.get(tierToBeRouted) "+tierTimeDetails.get(tierToBeRouted));
        				if(tierToBeRouted.intValue()!=9999){
        				Calendar newcalendarTier = new GregorianCalendar();
        				newcalendarTier.setTime(tierTimeDetails.get(tierToBeRouted));
        				String toBeRoutedDateStringNext = ProcessVariableUtil.convertToJPDLDueDate(newcalendarTier);
        				execution.setVariable(ProcessVariableUtil.NEXT_TIER_DUE_DATE, toBeRoutedDateStringNext);
        				}
        			} 
        			
        			
        			
        			
        			
        			 int orginalTierToBeRouted=0;
        			 orginalTierToBeRouted=tierToBeRouted;
        			if(nextTierToBeRouted!=0 && nextTierToBeRouted==lastTierId){
    					logger.info("hdr.getMarketPlaceOverFlow()"+hdr.getMarketPlaceOverFlow());
    					if(hdr.getMarketPlaceOverFlow()){
    						//tierToBeRouted=9999;    // marketplace.
    						tierToBeRouted = nextTierToBeRouted;
    						logger.info(" marketplace overflow "); 
    						Calendar farFutureDate = new GregorianCalendar();
            			
            				String routedString=ProcessVariableUtil.convertToJPDLDueDate(farFutureDate);
                        	execution.setVariable(ProcessVariableUtil.AUTO_TIER_ROUTE_DATE, routedString); 
    						
    					}
    					else
    					{
    						logger.info(" next tier to be routed is last is the marketplace overflow is false ");
    						tierToBeRouted = nextTierToBeRouted;
    						Calendar farFutureDate = new GregorianCalendar();
            				String routedString=convertToJPDLDueDate(farFutureDate);
                        	execution.setVariable(ProcessVariableUtil.AUTO_TIER_ROUTE_DATE, routedString);
    						
    					}
    				}else{
    					tierToBeRouted = nextTierToBeRouted;
    				}
        			// need to take next tier. tier may not be the same.
     			   
        			logger.info(" tierToBeRouted is "+tierToBeRouted);
        			logger.info(" nextTier "+nextTier);
        			logger.info(" nextTierToBeRouted "+nextTierToBeRouted);
        			
            		execution.setVariable("currentTierSettingTobeRouted", currentTier);
            		execution.setVariable("nextTierSetingTobeRouted", nextTier);      

            		execution.setVariable(ProcessVariableUtil.RELEASE_ACTION_PERFORMED_IND,0);
            		//logger.info("Inside IF Release::"+toBeRoutedDateString);
            		//execution.setVariable(ProcessVariableUtil.AUTO_TIER_ROUTE_DATE, toBeRoutedDateString);
            		
            		
            		//tier to this
            		execution.setVariable(ProcessVariableUtil.AUTO_ROUTING_TIER,tierToBeRouted);

            		// need to find the missed tiers.
            		if(nextTier.intValue()<orginalTierToBeRouted && orginalTierToBeRouted!=0 && orginalTierToBeRouted!=4)
        			{    
        			String missedTiers="";
            		missedTiers=missedTiers+nextTier;
            		if(nextTier.intValue()<orginalTierToBeRouted)
        			{
            		for(SPNTierMinutes tierDetails:tierMinutes){
            	        Integer tierId= (Integer) tierDetails.getSpnTierPK().getTierId().getId();
            			if(tierId.intValue()<tierToBeRouted && tierId.intValue()>nextTier.intValue()){
            				missedTiers=missedTiers+","+tierId;
            			}
            		}
        			}
    				logger.info("missedTiers "+missedTiers);
            		execution.setVariable("missedTier", missedTiers);
            		
            		try
            		{
            		if(nextTierToBeRouted!=0 && nextTierToBeRouted==lastTierId){
                		logger.info(" it is going to route for last tier");
                		}
            		else
            		{
                		Map<Integer,Integer> providerCountMap=new HashMap<Integer,Integer>();
                		int countOfProviders=0;
                		if(null!=missedTiers){
                			List<Integer> missedList=new ArrayList<Integer>();
                			String missed[]=missedTiers.split(",");
                			for(int i=0;i<missed.length;i++){
                				Integer id=Integer.parseInt(missed[i]);
                				missedList.add(id);
                			}
                			
                			for(SPNTierMinutes tierDetails:tierMinutes){
                    			logger.info("tierToBeRouted while finding number of providers"+tierToBeRouted);
                    			if(missedList.contains(tierDetails.getSpnTierPK().getTierId().getId())){
                    				countOfProviders=countOfProviders+tierDetails.getNoOfMembers();
                    			}if(tierDetails.getSpnTierPK().getTierId().getId().equals(orginalTierToBeRouted)){
                    				countOfProviders=countOfProviders+tierDetails.getNoOfMembers();
                    			}
                    		}
                			Integer tierToHaveRouted = 0;
                			if(missedList.size()>0){
                				
                				tierToHaveRouted = missedList.get(missedList.size()-1);
                			logger.info("tierToHaveRouted"+tierToHaveRouted);
                			//execution.setVariable(ProcessVariableUtil.AUTO_ROUTING_TIER, tierToHaveRouted);
                			}else{
                				logger.info("no missed tiers");
                			//	execution.setVariable(ProcessVariableUtil.AUTO_ROUTING_TIER, currentTier);
                			}
                			// set next tier to be routed.
                			if(null!=tierToBeRouted && tierToBeRouted.intValue()!=0){
                			//	execution.setVariable(ProcessVariableUtil.NEXT_TIER_TO_BE_ROUTED_IS, tierToHaveRouted);
                			}
                			//execution.setVariable(ProcessVariableUtil.AUTO_ROUTING_TIER, tierToHaveRouted);
                			logger.info(" countOfProviders "+countOfProviders);
                    		execution.setVariable(ProcessVariableUtil.COUNT_OF_PROV_FOR_CURRENT_TIER, countOfProviders);
                		}
            		}
            		}catch(Exception e)
            		{
            		logger.info("Exception is "+e);	
            		}
        			}
			}else{
				logger.info("time not elapsed");
				Date initialTierRoutedDate = so.getSOWorkflowControls().getCurrentTierRoutedDate();
    			logger.info("InitialTierRoutedDate:"+initialTierRoutedDate);
    			Calendar calendar = new GregorianCalendar();
    			calendar.setTime(initialTierRoutedDate);
    			logger.info("minutesUntilNextTier:"+minutesUntilNextTier.intValue());
    			logger.info("hoursUntilNextTier:"+hoursUntilNextTier.intValue());  
    			logger.info("daysUntilNextTier:"+daysUntilNextTier.intValue());
    			Date nxtDate = null;
    		    if(minutesUntilNextTier.intValue()!=0){
    		    	logger.info("Inside IFFFF");
    		    	calendar.add(Calendar.MINUTE,minutesUntilNextTier.intValue());
    		    	nxtDate = org.apache.commons.lang.time.DateUtils.addMinutes(initialTierRoutedDate, minutesUntilNextTier.intValue());
    		    	calendar.setTime(nxtDate);
    		    }
    		    else if(hoursUntilNextTier.intValue() != 0){
    		    	calendar.add(Calendar.HOUR, hoursUntilNextTier.intValue());
    		    	nxtDate = org.apache.commons.lang.time.DateUtils.addHours(initialTierRoutedDate, hoursUntilNextTier.intValue());
    		    	calendar.setTime(nxtDate);
    		    }
    		    else if(daysUntilNextTier.intValue()!=0){
    		    	calendar.add(Calendar.MINUTE,(daysUntilNextTier.intValue()*24));
    		    	nxtDate = org.apache.commons.lang.time.DateUtils.addMinutes(initialTierRoutedDate, minutesUntilNextTier.intValue());
    		    	calendar.setTime(nxtDate);
    		    }
    		    Date now = new Date();
    			Date exactTimeToHaveRoutedNextTier = calendar.getTime();
				logger.info("exactTimeToHaveRoutedNextTier:"+exactTimeToHaveRoutedNextTier);
				logger.info("now:"+now);
				logger.info("nxtDate"+nxtDate);
				logger.info("initialTierRoutedDate2"+initialTierRoutedDate);
    				logger.info("Inside now is before the date to be routed");
    				toBeRoutedDateString = ProcessVariableUtil.convertToJPDLDueDate(calendar);
    			execution.setVariable(ProcessVariableUtil.RELEASE_ACTION_PERFORMED_IND,0);
    			execution.setVariable(ProcessVariableUtil.AUTO_TIER_ROUTE_DATE, toBeRoutedDateString); 
    			/*if(null!=nextTier){
    				execution.setVariable(ProcessVariableUtil.AUTO_ROUTING_TIER,nextTier);
    			}else{
    				execution.setVariable(ProcessVariableUtil.AUTO_ROUTING_TIER,9999);
    			}*/
			}
            		            		
			}		
		 return "tierRouted";
		}
    }   
	public static String convertToJPDLDueDate(Calendar calendar){
    	return String.format("date=%1$tH:%1$tM %1$tm/%1$td/%1$tY", calendar);
    }
}
