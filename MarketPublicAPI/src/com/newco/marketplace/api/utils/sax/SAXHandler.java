package com.newco.marketplace.api.utils.sax;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.newco.marketplace.api.services.leadsmanagement.InsideSalesLeadsService;
import com.newco.marketplace.dto.vo.leadsmanagement.InsideSalesLeadCustVO;
import com.newco.marketplace.dto.vo.leadsmanagement.InsideSalesLeadVO;
import com.newco.marketplace.interfaces.InsideSalesConstants;

    public class SAXHandler extends DefaultHandler { 
    
    private static Logger logger = Logger.getLogger(SAXHandler.class);	
    public InsideSalesLeadVO insideSalesVo=new InsideSalesLeadVO(); 
    public List<InsideSalesLeadCustVO> insideSalesLeadCustList=new ArrayList<InsideSalesLeadCustVO>();
	public Map<String,String> insideSalesLeadMap=new HashMap<String,String>();
	public Map<String,String> insideSalesCustomFieldMap=new HashMap<String,String>(); 
    public String reason="";
    
	public SAXHandler(String reason){
		this.insideSalesVo=null;
		this.insideSalesLeadMap=null;
		this.insideSalesCustomFieldMap=null; 
		this.insideSalesLeadCustList=null;
		this.reason=reason;  
	}
        @Override
        public void startElement(String uri, String localName,
                                 String qName, Attributes attributes)
                throws SAXException {
        	logger.info("qName: "+qName);              
          if("lead".equals(qName)){
        	  insideSalesVo=new InsideSalesLeadVO(); 
        	  insideSalesLeadMap=new HashMap<String,String>();
        	  insideSalesCustomFieldMap=new HashMap<String,String>(); 
        	  insideSalesLeadCustList=new ArrayList<InsideSalesLeadCustVO>();
        	  
            int attributeLength = attributes.getLength(); 
            
            
            if(reason.equals(InsideSalesConstants.GET_LEAD_ID)){
            	          	
            	for (int i = 0; i < attributeLength; i++) {
                    
                    //
                    // Get attribute names and values
                    //
                    String attrName = attributes.getQName(i);
                    String attrVal = attributes.getValue(i);
                    
                    System.out.print(attrName + " = " + attrVal + "\n ");
                    if(attrName.equals(InsideSalesConstants.ID)){
                    	insideSalesVo.setId(attrVal);
                    	break;
                    }
                }            	            	            	
			} else if (reason.equals(InsideSalesConstants.LEAD_STATUS)) {
                for (int i = 0; i < attributeLength; i++) {
                
                    //
                    // Get attribute names and values
                    //
                    String attrName = attributes.getQName(i);
                    String attrVal = attributes.getValue(i);
                    
                    System.out.print(attrName + " = " + attrVal + "\n ");
                    if(!attrName.contains("customFields")){
                    	insideSalesLeadMap.put(attrName, attrVal);
                    }else{
                    	InsideSalesLeadCustVO insideSalesLeadCustVO=new InsideSalesLeadCustVO();
                    	insideSalesLeadCustVO.setCustomField(attrName);
                    	insideSalesLeadCustVO.setCustomFieldValue(attrVal);
                    	insideSalesLeadCustList.add(insideSalesLeadCustVO);
                    }
                }
                
                
                
                if(!insideSalesLeadMap.isEmpty()){
             	
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.ID)){
                		insideSalesVo.setId(insideSalesLeadMap.get(InsideSalesConstants.ID));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.STATUS)){
                		insideSalesVo.setStatus(insideSalesLeadMap.get(InsideSalesConstants.STATUS));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.DATE_MODIFIED)){
                		insideSalesVo.setStatusChangedTime(insideSalesLeadMap.get(InsideSalesConstants.DATE_MODIFIED)); 
                	} 
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.MODIFIED_BY_USER_ID)){
                		insideSalesVo.setChangerId(insideSalesLeadMap.get(InsideSalesConstants.MODIFIED_BY_USER_ID));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.OWNER_USER_ID)){
                		insideSalesVo.setOwnerUserId(insideSalesLeadMap.get(InsideSalesConstants.OWNER_USER_ID));
                	} 
                	
                	String changer=""; 
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.MODIFIED_BY_FIRST_NAME)){
                		changer=insideSalesLeadMap.get(InsideSalesConstants.MODIFIED_BY_FIRST_NAME);
                     }
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.MODIFIED_BY_LAST_NAME)){
                		changer=changer+insideSalesLeadMap.get(InsideSalesConstants.MODIFIED_BY_LAST_NAME);
                    }
                	if(StringUtils.isNotBlank(changer)){
                		insideSalesVo.setChanger(changer);
                	}
                	
                	String owner=""; 
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.OWNER_FIRST_NAME)){
                		owner=insideSalesLeadMap.get(InsideSalesConstants.OWNER_FIRST_NAME);
                     }
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.OWNER_LAST_NAME)){
                		owner=owner+insideSalesLeadMap.get(InsideSalesConstants.OWNER_LAST_NAME);
                    }
                	if(StringUtils.isNotBlank(owner)){
                		insideSalesVo.setOwner(owner);
                	}
                	
                	
                }	
                } else{
                
                for (int i = 0; i < attributeLength; i++) {
                
                    //
                    // Get attribute names and values
                    //
                    String attrName = attributes.getQName(i);
                    String attrVal = attributes.getValue(i);
                    
                    //logger.info(attrName + " = " + attrVal + "\n ");
                    if(!attrName.contains("customFields")){
                    	insideSalesLeadMap.put(attrName, attrVal);
                    }else{
                    	InsideSalesLeadCustVO insideSalesLeadCustVO=new InsideSalesLeadCustVO();
                    	insideSalesLeadCustVO.setCustomField(attrName);
                    	insideSalesLeadCustVO.setCustomFieldValue(attrVal);
                    	insideSalesLeadCustList.add(insideSalesLeadCustVO);
                    }
                }
                
                
                
                if(!insideSalesLeadMap.isEmpty()){
                	//set xml field value account_name to company_name
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.ACCOUNT_NAME)){
                		insideSalesVo.setCompanyName(insideSalesLeadMap.get(InsideSalesConstants.ACCOUNT_NAME));
                	}
                	
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.ID)){
                		insideSalesVo.setId(insideSalesLeadMap.get(InsideSalesConstants.ID));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.EXTERNAL_ID)){
                		insideSalesVo.setExternalId(insideSalesLeadMap.get(InsideSalesConstants.EXTERNAL_ID));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.OWNER_USER_ID)){
                		insideSalesVo.setOwnerUserId(insideSalesLeadMap.get(InsideSalesConstants.OWNER_USER_ID));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.OWNER_FIRST_NAME)){
                		insideSalesVo.setOwnerFirstName(insideSalesLeadMap.get(InsideSalesConstants.OWNER_FIRST_NAME));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.OWNER_LAST_NAME)){
                		insideSalesVo.setOwnerLastName(insideSalesLeadMap.get(InsideSalesConstants.OWNER_LAST_NAME));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.DATE_CREATED)){
                		insideSalesVo.setDateCreated(insideSalesLeadMap.get(InsideSalesConstants.DATE_CREATED)); 
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.CREATED_BY_USER_ID)){
                		insideSalesVo.setCreatedByUserId(insideSalesLeadMap.get(InsideSalesConstants.CREATED_BY_USER_ID));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.DATE_MODIFIED)){
                		insideSalesVo.setDateModified(insideSalesLeadMap.get(InsideSalesConstants.DATE_MODIFIED));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.MODIFIED_BY_USER_ID)){
                		insideSalesVo.setModifiedByUserId(insideSalesLeadMap.get(InsideSalesConstants.MODIFIED_BY_USER_ID));
                	} 
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.DELETED)){
                		insideSalesVo.setDeleted(insideSalesLeadMap.get(InsideSalesConstants.DELETED));
                	}                	
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.FIRST_NAME)){
                		insideSalesVo.setFirstName(insideSalesLeadMap.get(InsideSalesConstants.FIRST_NAME));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.MIDDLE_NAME)){
                		insideSalesVo.setMiddleName(insideSalesLeadMap.get(InsideSalesConstants.MIDDLE_NAME));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.LAST_NAME)){
                		insideSalesVo.setLastName(insideSalesLeadMap.get(InsideSalesConstants.LAST_NAME));
                	}  
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.BIRTHDATE)){
                		insideSalesVo.setBirthdate(insideSalesLeadMap.get(InsideSalesConstants.BIRTHDATE));
                	}                	
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.TITLE)){
                		insideSalesVo.setTitle(insideSalesLeadMap.get(InsideSalesConstants.TITLE));
                	}                 	
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.PHONE)){
                		insideSalesVo.setPhone(insideSalesLeadMap.get(InsideSalesConstants.PHONE));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.MOBILE_PHONE)){
                		insideSalesVo.setMobilePhone(insideSalesLeadMap.get(InsideSalesConstants.MOBILE_PHONE));
                	} 
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.FAX)){
                		insideSalesVo.setFax(insideSalesLeadMap.get(InsideSalesConstants.FAX));
                	}
                	/*home_phone
                	other_phone
                	email
                	email_opt_out
                	website
                	address_1
                	address_2
                	city
                	state
                	state_abbrev
                	zip
                	country
                	country_abbrev
                	assistant_first_name*/
                  	if(null!=insideSalesLeadMap.get(InsideSalesConstants.HOME_PHONE)){
                		insideSalesVo.setHomePhone(insideSalesLeadMap.get(InsideSalesConstants.HOME_PHONE));
                	}
                  	if(null!=insideSalesLeadMap.get(InsideSalesConstants.OTHER_PHONE)){
                		insideSalesVo.setOtherPhone(insideSalesLeadMap.get(InsideSalesConstants.OTHER_PHONE));
                	}                	
                  	if(null!=insideSalesLeadMap.get(InsideSalesConstants.EMAIL)){
                		insideSalesVo.setEmail(insideSalesLeadMap.get(InsideSalesConstants.EMAIL));
                	}              	
                  	if(null!=insideSalesLeadMap.get(InsideSalesConstants.EMAIL_OPT_OUT)){
                		insideSalesVo.setEmailOptOut(insideSalesLeadMap.get(InsideSalesConstants.EMAIL_OPT_OUT));
                	}
                  	if(null!=insideSalesLeadMap.get(InsideSalesConstants.WEBSITE)){
                		insideSalesVo.setWebsite(insideSalesLeadMap.get(InsideSalesConstants.WEBSITE));
                	}
                  	if(null!=insideSalesLeadMap.get(InsideSalesConstants.ADDR1)){
                		insideSalesVo.setAddr1(insideSalesLeadMap.get(InsideSalesConstants.ADDR1));
                	}
                  	if(null!=insideSalesLeadMap.get(InsideSalesConstants.ADDR2)){
                		insideSalesVo.setAddr2(insideSalesLeadMap.get(InsideSalesConstants.ADDR2));
                	}
                  	if(null!=insideSalesLeadMap.get(InsideSalesConstants.CITY)){
                		insideSalesVo.setCity(insideSalesLeadMap.get(InsideSalesConstants.CITY));
                	}
                  	if(null!=insideSalesLeadMap.get(InsideSalesConstants.STATE)){
                		insideSalesVo.setState(insideSalesLeadMap.get(InsideSalesConstants.STATE));
                	}
                  	if(null!=insideSalesLeadMap.get(InsideSalesConstants.STATE_ABBREV)){
                		insideSalesVo.setStateAbbrev(insideSalesLeadMap.get(InsideSalesConstants.STATE_ABBREV));
                	}
                  	if(null!=insideSalesLeadMap.get(InsideSalesConstants.ZIP)){
                		insideSalesVo.setZip(insideSalesLeadMap.get(InsideSalesConstants.ZIP));
                	}
                  	if(null!=insideSalesLeadMap.get(InsideSalesConstants.COUNTRY)){
                		insideSalesVo.setCountry(insideSalesLeadMap.get(InsideSalesConstants.COUNTRY));
                	}
                  	if(null!=insideSalesLeadMap.get(InsideSalesConstants.COUNTRY_ABBREV)){
                		insideSalesVo.setCountryAbbrev(insideSalesLeadMap.get(InsideSalesConstants.COUNTRY_ABBREV));
                	}
                  	if(null!=insideSalesLeadMap.get(InsideSalesConstants.ASSISTANT_FIRST_NAME)){
                		insideSalesVo.setAssistantFirstName(insideSalesLeadMap.get(InsideSalesConstants.ASSISTANT_FIRST_NAME));
                	}
                  	if(null!=insideSalesLeadMap.get(InsideSalesConstants.ASSISTANT_LAST_NAME)){
                		insideSalesVo.setAssistantLastName(insideSalesLeadMap.get(InsideSalesConstants.ASSISTANT_LAST_NAME));
                	}
                  	if(null!=insideSalesLeadMap.get(InsideSalesConstants.INDUSTRY)){
                		insideSalesVo.setIndustry(insideSalesLeadMap.get(InsideSalesConstants.INDUSTRY));
                	}    
                  	if(null!=insideSalesLeadMap.get(InsideSalesConstants.ANNUAL_REVENUE)){
                		insideSalesVo.setAnnualRevenue(insideSalesLeadMap.get(InsideSalesConstants.ANNUAL_REVENUE));
                	}
                  	if(null!=insideSalesLeadMap.get(InsideSalesConstants.NUMBER_OF_EMPLOYEES)){
                		insideSalesVo.setNumberOfEmployees(insideSalesLeadMap.get(InsideSalesConstants.NUMBER_OF_EMPLOYEES));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.CAMPAIGN_ID)){
                		insideSalesVo.setCampaignId(insideSalesLeadMap.get(InsideSalesConstants.CAMPAIGN_ID));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.CAMPAIGN)){
                		insideSalesVo.setCampaign(insideSalesLeadMap.get(InsideSalesConstants.CAMPAIGN));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.STATUS)){
                		insideSalesVo.setStatus(insideSalesLeadMap.get(InsideSalesConstants.STATUS));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.SOURCE)){
                		insideSalesVo.setSource(insideSalesLeadMap.get(InsideSalesConstants.SOURCE));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.RATING)){
                		insideSalesVo.setRating(insideSalesLeadMap.get(InsideSalesConstants.RATING));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.DESCRIPTION)){
                		insideSalesVo.setDescription(insideSalesLeadMap.get(InsideSalesConstants.DESCRIPTION));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.DO_NOT_CALL)){
                		insideSalesVo.setDoNotCall(insideSalesLeadMap.get(InsideSalesConstants.DO_NOT_CALL));
                	}
                	if(null!=insideSalesLeadMap.get(InsideSalesConstants.FED_DO_NOT_CALL)){
                		insideSalesVo.setFedDoNotCall(insideSalesLeadMap.get(InsideSalesConstants.FED_DO_NOT_CALL));
                	}
                   	if(null!=insideSalesLeadMap.get(InsideSalesConstants.STATUS_ID)){
                		insideSalesVo.setStatusId(insideSalesLeadMap.get(InsideSalesConstants.STATUS_ID));
                	}
                   	if(null!=insideSalesLeadMap.get(InsideSalesConstants.SOURCE_ID)){
                		insideSalesVo.setSourceId(insideSalesLeadMap.get(InsideSalesConstants.SOURCE_ID));
                	}
                   	
                   	
                   	
                }                
                
            }
            logger.info(insideSalesVo.toString());

        } 
        }

		
        
        
    }