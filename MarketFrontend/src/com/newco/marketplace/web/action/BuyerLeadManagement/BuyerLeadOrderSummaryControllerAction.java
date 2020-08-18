package com.newco.marketplace.web.action.BuyerLeadManagement;
 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
 
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
 
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.so.pdf.SOPDFUtils;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.leadsmanagement.BuyerLeadManagementCriteriaVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadDetailsCriteriaVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadInfoVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ProviderInfoVO;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadHistoryVO;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadNotesVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.dto.BuyerLeadManagementTabDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.buyerleadmanagement.services.BuyerLeadManagementService;
import java.util.*;
import java.text.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool.impl.StackObjectPoolFactory;
 
 
public class BuyerLeadOrderSummaryControllerAction  extends SLBaseAction implements Preparable, ModelDriven<BuyerLeadManagementTabDTO> {
                private static final long serialVersionUID = 1L;
 
                private static final Logger logger = Logger
                                                .getLogger(BuyerLeadOrderSummaryControllerAction.class);
                private BuyerLeadManagementService buyerLeadManagementService;
                
                
                private BuyerLeadManagementTabDTO buyerLeadManagementTabDTO = new BuyerLeadManagementTabDTO();
                
          
                private String resultUrl = null;					
                private Integer sEcho=1;
				private String iTotalRecords="2";
				private String iTotalDisplayRecords="2";
				private String aaNoteData[][];
 
                
                public String getResultUrl() {
						return resultUrl;
					}


				public void setResultUrl(String resultUrl) {
					this.resultUrl = resultUrl;
				}
                
                
                public String execute() throws Exception {
                                                                                
                                LeadInfoVO leadDetails= new LeadInfoVO();
                                String leadId=(String) getRequest().getParameter("leadId");
                                List<String> phoneNoList=new ArrayList<String>();
                                leadDetails=getBuyerLeadManagementSummary(leadId);
                                if(null!=leadDetails.getPhoneNo())
                                {
                                	 String customerPhone=leadDetails.getPhoneNo();
                                	 getSession().setAttribute("UnformattedPhoneNo",customerPhone);
                                     leadDetails.setPhoneNo(SOPDFUtils.formatPhoneNumber(customerPhone));
                                     phoneNoList=new ArrayList<String>(Arrays.asList(leadDetails.getPhoneNo().split("-")));
                                }
                                if(null!=leadDetails.getPhoneNumber())
                                {
                                	leadDetails.setPhoneNumber(SOPDFUtils.formatPhoneNumber(leadDetails.getPhoneNumber()));
                                }
                                
                                getSession().setAttribute("leadsDetails",leadDetails);
                                getRequest().setAttribute("leadsDetails", leadDetails);
                                getSession().setAttribute("phoneNoList",phoneNoList);
                                
                                ProviderInfoVO  resourceDetails= new ProviderInfoVO();
                                ProviderInfoVO  resourceDetailsScore= new ProviderInfoVO();
                                 List<ProviderInfoVO> providerDetails= new ArrayList<ProviderInfoVO>();
                                 providerDetails=getBuyerLeadManagementProviderInfo(leadId);
                                 getSession().setAttribute("leadId",leadId);
                                 
                                 try {
                                 for (ProviderInfoVO providerDetail:providerDetails) {
                                	 
                                	 providerDetail.setPhoneNo(SOPDFUtils.formatPhoneNumber(providerDetail.getPhoneNo()));
                                	 providerDetail.setMobileNo(SOPDFUtils.formatPhoneNumber(providerDetail.getMobileNo()));
                                     String providerId = providerDetail.getProviderId();
                                     String firmStatus = providerDetail.getFirmStatus();
                                     providerDetail.setLeadId(leadId);
                                    
                                     if(null != providerId){
                                         if(firmStatus.equals("scheduled")||firmStatus.equals("cancelled")||firmStatus.equals("completed")){  
                                        	 logger.info("If condition");
                                        	 if(null==providerDetail.getResourceId() && firmStatus.equals("scheduled"))
                                             {
                                        		 providerDetail.setServiceDate(formatResourceDate(providerDetail.getServiceDate()));
                                        		 providerDetail.setResourceScheduledTime(providerDetail.getResourceScheduledTime());
                                        	}
                                        	 else
                                        	 {
                                             resourceDetails=getBuyerLeadManagementResourceInfo(providerDetail);
                                        	 }
                                             if(null!=resourceDetails){
                                            	 resourceDetailsScore=getBuyerLeadManagementResourceScore(resourceDetails);
                                             }
                                            
                                             try{
                                             if(null!=resourceDetails){
                                            	 
                                            	 if(null!=resourceDetails.getCancelledBy()){
                                            		 providerDetail.setCancelledBy(resourceDetails.getCancelledBy());
                                            		 if(null!= resourceDetails.getCancelledByFirstName()){
                                            			 providerDetail.setCancelledByFirstName(resourceDetails.getCancelledByFirstName());
                                            		 }	 
                                            		 if(null != resourceDetails.getCancelledByLastName()){
                                            		 	providerDetail.setCancelledByLastName(resourceDetails.getCancelledByLastName());
                                            		 }
                                            	 }
                                            	 
                                            	 if(null!=resourceDetails.getCancelledOn()){
	                                             providerDetail.setCancelledOn(formatResourceDate(resourceDetails.getCancelledOn()));
                                            	 }
                                            	 if(null!=resourceDetails.getReason()){
	                                             providerDetail.setReason(resourceDetails.getReason());}
                                            	 if(null!=resourceDetails.getVisits()){
	                                             providerDetail.setVisits(resourceDetails.getVisits());}
                                            	 if(null!=resourceDetails.getLeadPrice()){
	                                             providerDetail.setLeadPrice(resourceDetails.getLeadPrice());}
                                            	 if(null!=resourceDetails.getResourceFullName()){
	                                             providerDetail.setResourceFullName(resourceDetails.getResourceFullName());}
                                            	 
                                            	 if(null!=resourceDetails.getFirmBusinessName()){
    	                                             providerDetail.setFirmBusinessName(resourceDetails.getFirmBusinessName());}
                                                	 
	                                             if(null!=resourceDetails.getResourceScheduledTime()){
	                                             //providerDetail.setResourceScheduledTime(formatLeadTime(resourceDetails.getResourceScheduledTime()));
	                                             providerDetail.setResourceScheduledTime(resourceDetails.getResourceScheduledTime());
	                                             }
	                                             if(null!=resourceDetails.getResourceId()){
	                                             providerDetail.setResourceId(resourceDetails.getResourceId());}
	                                             if(null!=resourceDetails.getCompletedTime()){
	                                               providerDetail.setCompletedTime(formatResourceDate(resourceDetails.getCompletedTime()));
	                                             }
	                                             if(null!=resourceDetails.getServiceDate()){
	                                             providerDetail.setServiceDate(formatResourceDate(resourceDetails.getServiceDate()));
	                                             }
	                                             if(null!=resourceDetails.getResourcePhoneNo()){
	                                             String resourcePhoneNo=SOPDFUtils.formatPhoneNumber(resourceDetails.getResourcePhoneNo());
                                                 providerDetail.setResourcePhoneNo(resourcePhoneNo);}
	                                             if(null!=resourceDetails.getResourceMobileNo()){
	                                             String resourceMobileNo=SOPDFUtils.formatPhoneNumber(resourceDetails.getResourceMobileNo());
                                                 providerDetail.setResourceMobileNo(resourceMobileNo);}
                                             
	                                             if(firmStatus.equals("scheduled")||firmStatus.equals("completed")){
	                                             	 if(null!=resourceDetailsScore){
		                                        	 logger.info("If condition 1");
		                                         	 if (null!=resourceDetailsScore.getScore()){
		                                         	 
		                                     		 providerDetail.setStarRating(UIUtils.calculateScoreNumber(resourceDetailsScore.getScore()));	
		                                         	 }
	                                            	 }
	                                             }                                            
                                             }
                                             }catch (Exception e){
                                              	System.out.println("Exception :"+e);
                                             }
                                         }
                                     } 
                                     if(null!=providerDetail.getModifiedDate()){
                                     providerDetail.setModifiedDate(formatLeadDate(providerDetail.getModifiedDate(),"lastUpdatedDate"));
                                     }
                                     if(null!=providerDetail.getFirmStatus()){
                                     providerDetail.setFirmStatus(providerDetail.getFirmStatus().toUpperCase());}
                                     
                                 }
                                 	
                                 		
                                } catch (Exception e){
                                 	System.out.println("Exception :"+e);
                                }
                                 getSession().setAttribute("providerDetails",providerDetails);
                                 
                        		String widgetSuccessInd=(String) getRequest().getParameter("widgetSuccessInd");
                        		String message="";
                        		String failInd="";
                        		String allProvInd=(String) getRequest().getParameter("allProvInd");
                        		Boolean allProviderInd=Boolean.parseBoolean(allProvInd);
                        		if(getRequest().getParameter("failInd")!=null)
                        		{
                        			failInd=(String)getRequest().getParameter("failInd");
                            		Integer failureIndicator=Integer.parseInt(failInd);
                        			//Setting error message based on the failure indicator
                            		if(failureIndicator==0)
                            		{
                            			if(allProviderInd)
                            			{
                            				message="Lead cancelled successfully";
                            			}
                            			else
                            			{
                            				message="Lead cancelled from selected providers";
                            			}
                            		}
                            		else
                            		{
                            			message="lead cancellation failed!";
                            		}
                        		}
                        		
                        		
                        		
                        		
                        		
                        		//
                        		if(null!=widgetSuccessInd)
                        		{
                        			if(widgetSuccessInd.equals("addNote") || widgetSuccessInd.equals("cancelLead")||widgetSuccessInd.equals("updateReward"))
                        			{
                        			String updateRewardPointsType=(String) getRequest().getParameter("updateRewardPointsType");
                        			getSession().setAttribute("widgetSuccessInd",widgetSuccessInd);
                        			getSession().setAttribute("failInd",failInd);
                        			getSession().setAttribute("resultMessage",message);
                        			getSession().setAttribute("updateRewardPointsType",updateRewardPointsType);
                        			}
                        			else
                            		{
                            			getSession().setAttribute("widgetSuccessInd","false");	
                            		}
                        		}
                        		else
                        		{
                        			getSession().setAttribute("widgetSuccessInd","false");	
                        		}
                                return "success";
                                
                                
                }
                
                
                public String formatResourceDate(String date){
                	
                	String date1 = date;
            		String resultDate = "";
            		
                	SimpleDateFormat SDF = new SimpleDateFormat("EEEEEEE, MMM d, yyyy");
                	SimpleDateFormat SDF1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try{
                	   Date unFormatted=SDF1.parse(date1);
                       resultDate = SDF.format(unFormatted); 
                	}catch(Exception e){
                		System.out.println("Illegal Arguement Exception"+e.getMessage());
                	}
                    
                   
                	logger.info("date ="+resultDate);
                 	return resultDate;
                	
                	
                }
                
                
                
                public String formatLeadDate(String date,String dateFormatType){
                	
                	String date1 = date;
            		String resultDate = "";
            		SimpleDateFormat SDF=new SimpleDateFormat();
            		if(null!=dateFormatType && (dateFormatType.equals("PreferredDate")))
            		{
            			 SDF= new SimpleDateFormat("MMMM d, yyyy");	
            		}
            		else if(null!=dateFormatType && (dateFormatType.equals("createdOn")||dateFormatType.equals("lastUpdatedDate")))
            		{
            			SDF = new SimpleDateFormat("MMMM d, yyyy hh:mm a (z)");
            		}
            		else 
            		{
            			SDF= new SimpleDateFormat("yyyy hh:mm a (z)");	
            		}
                	
                	SimpleDateFormat SDF1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try{
                	   Date unFormatted=SDF1.parse(date1);
                       resultDate = SDF.format(unFormatted); 
                	}catch(Exception e){
                		System.out.println("Illegal Arguement Exception"+e.getMessage());
                	}
                   
                	logger.info("date ="+resultDate);
                 	return resultDate;
                }
                
                public String formatLeadTime(String time){
                	String preferredtime = time;
                	String resultDate1 = "";
                	String resultDate2 = "";
                	String resultTime  = "";
                	
                	
                	String[] parts = preferredtime.split("-", 2);
                	String string1 = parts[0];
                	String string2 = parts[1];
                	String formatedPreferredTime="";
                	
                	SimpleDateFormat newString = new SimpleDateFormat("h:mm a");
                	SimpleDateFormat oldstring = new SimpleDateFormat("HH:mm:ss");
                	 try{
                  	   Date unFormatted=oldstring.parse(string1);
                         resultDate1 = newString.format(unFormatted);
                         Date unFormatted1=oldstring.parse(string2);
                         resultDate2 = newString.format(unFormatted1);
                         resultTime = resultDate1+"-"+resultDate2;
                        
                         
                         String newPreferedTime[] = resultTime.split("-");
     					String dayTime=newPreferedTime[0].substring(newPreferedTime[0].length()-2, newPreferedTime[0].length());
     					
     					
     					if(dayTime.equalsIgnoreCase("AM"))
     					{
     						formatedPreferredTime="Morning"+"("+resultTime+")";
     					}
     					else
     					{
     						formatedPreferredTime="Evening"+"("+resultTime+")";
     					}
     						//leadDetails.setPreferredTime(formatedPreferredTime);
     					
                         
                  	}catch(Exception e){
                  		System.out.println("Illegal Arguement Exception"+e.getMessage());
                  	} 
                	 
                	 return formatedPreferredTime;
                }
               
                
                public LeadInfoVO getBuyerLeadManagementSummary(String leadId)
                {
                                LeadInfoVO leadDetails= new LeadInfoVO();
                                leadDetails=buyerLeadManagementService.getBuyerLeadManagementSummary(leadId);
                                if (null != leadDetails ){
                                                formatLeadDetails(leadDetails);
                                }
                                return leadDetails;
                }
                
                public String loadUpdatedLeadInformation()
                {
                	 LeadInfoVO leadDetails= new LeadInfoVO();
                     String leadId=(String) getRequest().getParameter("leadId");
                     leadDetails=getBuyerLeadManagementSummary(leadId);
                     List<String> phoneNoList=new ArrayList<String>();
                     if(null!=leadDetails.getPhoneNo())
                     {
                     	 String customerPhone=leadDetails.getPhoneNo();
                         leadDetails.setPhoneNo(SOPDFUtils.formatPhoneNumber(customerPhone));
                         phoneNoList=new ArrayList<String>(Arrays.asList(leadDetails.getPhoneNo().split("-")));
                         
                     }
                     getSession().setAttribute("leadsDetails",leadDetails);
                     getSession().setAttribute("phoneNoList",phoneNoList);
                     return "success";
                }
				public LeadInfoVO formatLeadDetails(LeadInfoVO leadDetails) {
			
					String urgency = leadDetails.getUrgencyOfService();
					if(null!=urgency){
					if(urgency.equals("SAME_DAY")){
					urgency = OrderConstants.SAME_DAY;
					leadDetails.setUrgencyOfService(urgency);
					}
					if(urgency.equals("NEXT_DAY")){
						urgency=OrderConstants.NEXT_DAY;
						leadDetails.setUrgencyOfService(urgency);
					}
					else if(urgency.equals("AFTER_TOMORROW")){
						urgency=OrderConstants.AFTER_TOMORROW;
						leadDetails.setUrgencyOfService(urgency);
					}
					}
					
					logger.info("urgency =" + urgency);
					String status = leadDetails.getLeadStatus().toLowerCase();
					leadDetails.setLeadStatus(status);
					String leadType = leadDetails.getLeadType();
					if(null!=leadType){
						if(leadType.equals("EXCLUSIVE")){
							
							leadType=OrderConstants.EXCLUSIVE;
						leadDetails.setLeadType(leadType);}
						
						if(leadType.equals("COMPETITIVE")){
							leadType=OrderConstants.COMPETITIVE;
						leadDetails.setLeadType(leadType);}
					}
					String skill=leadDetails.getSkill();
					if(null!=skill)
					{
						if(skill.equals("INSTALL"))
						{
							skill=OrderConstants.INSTALL;
							leadDetails.setSkill(skill);
						}
						if(skill.equals("REPAIR")){
							skill=OrderConstants.REPAIR;
							leadDetails.setSkill(skill);
						}
						else if (skill.equals("DELIVERY")){
							skill=OrderConstants.DELIVERY;
							leadDetails.setSkill(skill);
						}
					}
					if(null!=leadDetails.getPreferredAppointmentTime())	{
					leadDetails.setPreferredAppointmentTime(formatLeadDate(leadDetails.getPreferredAppointmentTime(),"PreferredDate"));
					}
					else
					{
						leadDetails.setPreferredAppointmentTime(OrderConstants.PREFERRED_DATE_NOT_AVAIABLE);	
					}
					if(null!=leadDetails.getCreatedOn())
					{
					leadDetails.setCreatedOn((formatLeadDate(leadDetails.getCreatedOn(),"createdOn")));
					}
					if(null!=leadDetails.getPreferredTime())
					{
						String preferredtime = leadDetails.getPreferredTime();
	                	String resultDate1 = "";
	                	String resultDate2 = "";
	                	String resultTime  = "";
	                	String[] parts = preferredtime.split("-", 2);
	                	String string1 = parts[0];
	                	String string2 = parts[1];
	                	SimpleDateFormat newString = new SimpleDateFormat("h:mm a");
	                	SimpleDateFormat oldstring = new SimpleDateFormat("HH:mm:ss");
	                	Date unFormatted;
						try {
							unFormatted = oldstring.parse(string1);
							 resultDate1 = newString.format(unFormatted);
	                         Date unFormatted1=oldstring.parse(string2);
	                         resultDate2 = newString.format(unFormatted1);
	                         resultTime = resultDate1+"-"+resultDate2;
	                         leadDetails.setPreferredTime(resultTime);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                        
				   // String preferredTime=formatLeadTime(leadDetails.getPreferredTime());
				    /*String newPreferedTime[] =preferredTime.split("-");
					String dayTime=newPreferedTime[0].substring(newPreferedTime[0].length()-2, newPreferedTime[0].length());
					String formatedPreferredTime="";
					if(dayTime.equalsIgnoreCase("AM"))
					{
						formatedPreferredTime="Morning"+"("+preferredTime+")";
					}
					else
					{
						formatedPreferredTime="Evening"+"("+preferredTime+")";
					}*/
						//leadDetails.setPreferredTime(formatLeadTime(leadDetails.getPreferredTime()));
						
					}
								
					return leadDetails;
				}
				
				
					
				
       
                public List<ProviderInfoVO> getBuyerLeadManagementProviderInfo(String leadId)
                {
                List<ProviderInfoVO>  ProviderDetails= new ArrayList<ProviderInfoVO>();
                ProviderDetails=buyerLeadManagementService.getBuyerLeadManagementProviderInfo(leadId);
                return ProviderDetails;
                }
                
                public ProviderInfoVO getBuyerLeadManagementResourceInfo(ProviderInfoVO leadProviderDetails)
                {
                                ProviderInfoVO  ResourceDetails= new ProviderInfoVO();
                                ResourceDetails=buyerLeadManagementService.getBuyerLeadManagementResourceInfo(leadProviderDetails);
                                
                return ResourceDetails;
                }
                public ProviderInfoVO getBuyerLeadManagementResourceScore(ProviderInfoVO resourceDetails)
                {
                	ProviderInfoVO  resourceDetailsScore= new ProviderInfoVO();
                	resourceDetailsScore=buyerLeadManagementService.getBuyerLeadManagementResourceScore(resourceDetails);
                	return resourceDetailsScore;
                }
    
                public String updateCustomerInfo(){
                                //String leadId = getRequest().getParameter("leadId");
                                BuyerLeadManagementTabDTO buyerLeadManagementTabDTO = getModel();
                                String customerPhone=buyerLeadManagementTabDTO.getPhoneNo1()+buyerLeadManagementTabDTO.getPhoneNo2()+buyerLeadManagementTabDTO.getPhoneNo3();
                                LeadDetailsCriteriaVO  leadDeatilsCriteriaVO= new LeadDetailsCriteriaVO ();
                                try {
                                                leadDeatilsCriteriaVO.setLeadId(buyerLeadManagementTabDTO.getLeadId());
                                                leadDeatilsCriteriaVO.setFirstName(buyerLeadManagementTabDTO.getFirstName());
                                                leadDeatilsCriteriaVO.setLastName(buyerLeadManagementTabDTO.getLastName());
                                                leadDeatilsCriteriaVO.setPhoneNo(customerPhone);
                                                leadDeatilsCriteriaVO.setEmail(buyerLeadManagementTabDTO.getEmail());
                                                leadDeatilsCriteriaVO.setMembershipId(buyerLeadManagementTabDTO.getShopYourWayId());
                                                leadDeatilsCriteriaVO.setState(buyerLeadManagementTabDTO.getState());
                                                leadDeatilsCriteriaVO.setStreet1(buyerLeadManagementTabDTO.getStreet1());
                                                leadDeatilsCriteriaVO.setStreet2(buyerLeadManagementTabDTO.getStreet2());
                                                leadDeatilsCriteriaVO.setCity(buyerLeadManagementTabDTO.getCity());
                                                leadDeatilsCriteriaVO.setState(buyerLeadManagementTabDTO.getState());
                                                leadDeatilsCriteriaVO.setZip(buyerLeadManagementTabDTO.getZip());
                                
                                buyerLeadManagementService.updateBuyerLeadCustomerInfo(leadDeatilsCriteriaVO);
                               
                                
                                
                                resultUrl ="/buyerLeadOrderSummaryController_loadUpdatedLeadInformation.action?leadId="+buyerLeadManagementTabDTO.getLeadId();
                                logger.info("resultURL ="+resultUrl);                
                                }catch (Exception e) {
                                                // TODO Auto-generated catch block
                                     e.printStackTrace();
                                }
                    return"success";
                }
                               
                public String buyerLeadViewNotes()
                {
                	return "success";
                	
                }
                
				public String getBuyerLeadNotes()
                {
					    String leadId=(String) getRequest().getParameter("leadId");
		                List<SLLeadNotesVO>  leadNotes= new ArrayList<SLLeadNotesVO>();
		                BuyerLeadManagementCriteriaVO buyerLeadViewNotesCriteriaVO= new BuyerLeadManagementCriteriaVO();
		                buyerLeadViewNotesCriteriaVO=buyerLeadManagementViewNotesCriteria(leadId);
		                leadNotes=fetchBuyerLeadViewNotesDetailsWithDataTable(buyerLeadViewNotesCriteriaVO);
		                getSession().setAttribute("leadNotes",leadNotes);
		                return "json";
                }
                
                //Buyer Lead History
                public String getBuyerLeadHistory()
                {
                String leadId=(String) getRequest().getParameter("leadId");
                List<SLLeadHistoryVO>  leadHistory= new ArrayList<SLLeadHistoryVO>();
                leadHistory=buyerLeadManagementService.getBuyerLeadHistory(leadId);
                
                String DATE_FORMAT = "MM/dd/yy HH:mm";
                if (null != leadHistory){
                int size = leadHistory.size();
                                String date ="";
                for(int i=0;i<size;i++){
	                if (null != leadHistory.get(i).getCreatedDate()){
	                	date = getFormatedDate(leadHistory.get(i).getCreatedDate(), DATE_FORMAT);
	                }
	                date = date + " ("+OrderConstants.SERVICELIVE_ZONE+")";
	                	leadHistory.get(i).setCreatedActualDate(date); 
	                }
                }
                getSession().setAttribute("leadHistory",leadHistory);
                return "success";
                }
                
                public BuyerLeadManagementService getBuyerLeadManagementService() {
                                return buyerLeadManagementService;
                }
 
 
                public void setBuyerLeadManagementService(
                                                BuyerLeadManagementService buyerLeadManagementService) {
                                this.buyerLeadManagementService = buyerLeadManagementService;
                }
 
                public void setModel(BuyerLeadManagementTabDTO buyerLeadManagementTabDTO) {
                                this.buyerLeadManagementTabDTO = buyerLeadManagementTabDTO;
                }
                
 
                public BuyerLeadManagementTabDTO getModel() {
                                // TODO Auto-generated method stub
                                return buyerLeadManagementTabDTO;
                }
 
 
                public void prepare() throws Exception {
                                createCommonServiceOrderCriteria();
                }
                
                
                protected void createCommonServiceOrderCriteria() {
                                SecurityContext securityContext = (SecurityContext) getSession().getAttribute(SECURITY_KEY);
                                boolean isLoggedIn=false;
                                if (securityContext == null) {
                                                getSession().setAttribute(IS_LOGGED_IN, Boolean.FALSE);
                                                isLoggedIn = false;
                                                return;
                                } else {
                                                getSession().setAttribute(IS_LOGGED_IN, Boolean.TRUE);
                                                isLoggedIn = true;
                                }
                                LoginCredentialVO lvo = securityContext.getRoles();
                                lvo.setVendBuyerResId(securityContext.getVendBuyerResId());
                                if (lvo == null) {
                                                // TODO somebody handle this better
                                                lvo = new LoginCredentialVO();
                                }
 
                                _commonCriteria = new ServiceOrdersCriteria();
                                _commonCriteria.setCompanyId(securityContext.getCompanyId());
                                if (lvo.getVendBuyerResId() != null && lvo.getVendBuyerResId().intValue() == -1) {
                                                // TODO:: Populate vendor resource id upon login when the schema is
                                                // updated.
                                                _commonCriteria.setVendBuyerResId(securityContext.getVendBuyerResId());
                                //            _commonCriteria.setVendBuyerResId(9999); //temporarily for testing
                                } else {
                                                _commonCriteria.setVendBuyerResId(lvo.getVendBuyerResId());
                                }
 
                                if (securityContext.isSlAdminInd()){
                                                _commonCriteria.setRoleId(securityContext.getRoleId());
                                                _commonCriteria.setTheUserName(securityContext.getUsername());
                                }else{
                                                _commonCriteria.setRoleId(lvo.getRoleId());
                                                _commonCriteria.setTheUserName(lvo.getUsername());
                                }
 
                                _commonCriteria.setFName(lvo.getFirstName());
                                _commonCriteria.setLName(lvo.getLastName());
                                _commonCriteria.setRoleType(securityContext.getRole());
                                _commonCriteria.setToday(Boolean.TRUE);
                                securityContext.setBuyerLoggedInd(securityContext.isBuyer());
                                _commonCriteria.setSecurityContext(securityContext);
 
                                // Set simple buyer flag in session
                                if (isLoggedIn && _commonCriteria.getSecurityContext().getRoleId().intValue() == 5) {
                                                getSession().setAttribute(IS_SIMPLE_BUYER, Boolean.TRUE);
                                } else {
                                                getSession().setAttribute(IS_SIMPLE_BUYER, Boolean.FALSE);
                                }
                                if(_commonCriteria.getSecurityContext().getRoles()!=null){
                                                if (isLoggedIn && _commonCriteria.getSecurityContext().getRoles().getRoleName().equals(OrderConstants.NEWCO_ADMIN)) {
                                                                getSession().setAttribute(IS_ADMIN, Boolean.TRUE);
                                                } else {
                                                                getSession().setAttribute(IS_ADMIN, Boolean.FALSE);
                                                }
                                }
                                getSession().setAttribute(OrderConstants.SERVICE_ORDER_CRITERIA_KEY, _commonCriteria);
 
                }
                
                
                public static String getFormatedDate(Date inputDate, String reqFormat) {
                                SimpleDateFormat sdf = new SimpleDateFormat(reqFormat);
                                return sdf.format(inputDate);
                }
                
                
                public List<SLLeadNotesVO> fetchBuyerLeadViewNotesDetailsWithDataTable(BuyerLeadManagementCriteriaVO buyerLeadViewNotesCriteriaVO)
            	{
                	List<SLLeadNotesVO> leadNotes=new  ArrayList<SLLeadNotesVO>();
            		iTotalRecords="10";//buyerLeadManagementService.getBuyerLeadManagementCount(buyerLeadManagementCriteriaVO).toString();
                	leadNotes =buyerLeadManagementService.getBuyerLeadViewNotes(buyerLeadViewNotesCriteriaVO);
            		iTotalDisplayRecords=iTotalRecords;
            		if(null!=leadNotes){
            		aaNoteData=new String[leadNotes.size()][5];
            		int count=0;
            		String DATE_FORMAT = "MM/dd/yy HH:mm";
            		String viewNotesCreatedDate = "";
            		for(SLLeadNotesVO  noteInfoVO:leadNotes){
            			String data[]=new String[5];
            			data[0]="";
            			data[1]="";
            			data[2]="";
            			data[3]="";
            			data[4]="";
            			data[0]=noteInfoVO.getCreatedBy()+"<br>("+OrderConstants.USER_ID+noteInfoVO.getEntityId().toString()+")";
            			data[1]=noteInfoVO.getNoteCategory();
            			data[2]=noteInfoVO.getNote();
            			data[3]=noteInfoVO.getAlertSendTo();
            			viewNotesCreatedDate = "";
            			if (null != noteInfoVO.getCreatedDate()){
            				viewNotesCreatedDate = getFormatedDate(noteInfoVO.getCreatedDate(), DATE_FORMAT);
                        }
            			viewNotesCreatedDate = viewNotesCreatedDate + " ("+OrderConstants.SERVICELIVE_ZONE+")";
       			
            			data[4]=viewNotesCreatedDate;
            			aaNoteData[count]=data;
            			count=count+1;
            		}
            		//buyerLeadManagementTabDTO.setAaNoteData(aaNoteData);
            		buyerLeadManagementTabDTO.setAaData(aaNoteData);
            		buyerLeadManagementTabDTO.setiTotalDisplayRecords(iTotalDisplayRecords);
            		buyerLeadManagementTabDTO.setiTotalRecords(iTotalRecords);
            		buyerLeadManagementTabDTO.setsEcho(sEcho);
            		getRequest().setAttribute("count",count);	
            		}
            		
            		return leadNotes;
            	}
            		
                public BuyerLeadManagementCriteriaVO buyerLeadManagementViewNotesCriteria(String leadId)
            	{
            		BuyerLeadManagementCriteriaVO buyerLeadManagementCriteriaVO= new BuyerLeadManagementCriteriaVO();
            		buyerLeadManagementCriteriaVO.setLeadId(leadId);
            		String searching="";
            		if(getRequest().getParameter("searching")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("searching"))){
            			searching = (String)getRequest().getParameter("searching");
            		}
            		getRequest().setAttribute("searching",searching);	
            		// to handle server side pagination.
            		int startIndex=0;
            		Integer numberOfRecords=0;
            		 String sortColumnName="";
            		 String sortOrder="";
            		 String sSearch="";
            		if(getRequest().getParameter("iDisplayStart")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("iDisplayStart"))){
            			startIndex = Integer.parseInt((String)getRequest().getParameter("iDisplayStart"));
            		}
            		
            		if(getRequest().getParameter("iDisplayLength")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("iDisplayLength"))){
            			numberOfRecords = Integer.parseInt((String)getRequest().getParameter("iDisplayLength"));
            		}
            		if(getRequest().getParameter("iSortCol_0")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("iSortCol_0"))){
            			String sortColumnId =(String)getRequest().getParameter("iSortCol_0");
            			
            				if(sortColumnId.equals("0")){
            						sortColumnName="createdBy";
            				}else if(sortColumnId.equals("1")){
            					sortColumnName="noteCategory";
            				}else if(sortColumnId.equals("2")){
            					sortColumnName="note";
            				}else if(sortColumnId.equals("3")){
            					sortColumnName="alertSendTo";
            				}else if(sortColumnId.equals("4")){
            					sortColumnName="createdDate";
            				}	
            		}
            		if(getRequest().getParameter("sSortDir_0")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("sSortDir_0"))){
            			sortOrder = (String)getRequest().getParameter("sSortDir_0");
            		}
            	
            		if(getRequest().getParameter("sSearch")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("sSearch"))){
            			sSearch = (String)getRequest().getParameter("sSearch");
            		}
            		
            		if(getRequest().getParameter("sEcho")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("sEcho"))){
            			sEcho = Integer.parseInt(getRequest().getParameter("sEcho")) ;
            		}
            					
            		buyerLeadManagementCriteriaVO.setSortOrder(sortOrder);
            		buyerLeadManagementCriteriaVO.setsSearch(sSearch);
            		buyerLeadManagementCriteriaVO.setSortColumnName(sortColumnName);
            		buyerLeadManagementCriteriaVO.setStartIndex(startIndex);
            		buyerLeadManagementCriteriaVO.setNumberOfRecords(numberOfRecords);
            		return buyerLeadManagementCriteriaVO;
            		}
				public Integer getsEcho() {
					return sEcho;
				}


				public void setsEcho(Integer sEcho) {
					this.sEcho = sEcho;
				}


				public String getiTotalRecords() {
					return iTotalRecords;
				}


				public void setiTotalRecords(String iTotalRecords) {
					this.iTotalRecords = iTotalRecords;
				}


				public String getiTotalDisplayRecords() {
					return iTotalDisplayRecords;
				}


				public void setiTotalDisplayRecords(String iTotalDisplayRecords) {
					this.iTotalDisplayRecords = iTotalDisplayRecords;
				}


				public String[][] getAaData() {
					return aaNoteData;
				}


				public void setAaData(String[][] aaNoteData) {
					this.aaNoteData = aaNoteData;
				}
               
}
