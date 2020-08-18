package com.newco.marketplace.business.businessImpl.provider;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.provider.IInsurancePolicyBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.VendorCredentialsDocumentVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.daoImpl.document.DocumentDaoFactory;
import com.newco.marketplace.persistence.iDao.provider.IAuditDao;
import com.newco.marketplace.persistence.iDao.provider.ICredentialDao;
import com.newco.marketplace.persistence.iDao.provider.IInsuranceTypesDao;
import com.newco.marketplace.persistence.iDao.provider.IUserProfileDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorCredentialsDocumentDAO;
import com.newco.marketplace.persistence.iDao.provider.IVendorHdrDao;
import com.newco.marketplace.vo.provider.AuditVO;
import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.vo.provider.InsuranceTypesVO;
import com.newco.marketplace.vo.provider.VendorHdr;




/**
 * @author GGanesan
 *
 */
public class InsurancePolicyBOImpl extends ABaseBO implements IInsurancePolicyBO{
		private  IVendorHdrDao iVendorHdrDao;
		private ICredentialDao iCredentialDao;
        private IUserProfileDao iUserProfileDao;
        private IInsuranceTypesDao iInsuranceTypesDao;
        private IVendorCredentialsDocumentDAO myVendorCredentialsDocumentDAO;
        private IAuditDao iAuditDao;
        private final String AUTO_LIABILITY = "Auto Liability"; 
        private final String WORKER_COMPENSATION = "Workman's Compensation"; 
        private final String GENERAL_LIABILITY = "General Liability"; 
        private DocumentDaoFactory documentDaoFactory;
        
        
        public InsurancePolicyBOImpl(IVendorHdrDao iVendorHdrDao,
        		IUserProfileDao iUserProfileDao,
        		IInsuranceTypesDao iInsuranceTypesDao,
        		IVendorCredentialsDocumentDAO myVendorCredentialsDocumentDAO,
        		IAuditDao auditDao
        		){
        	this.iVendorHdrDao= iVendorHdrDao;
            this.iUserProfileDao = 	iUserProfileDao;
            this.iInsuranceTypesDao = 	iInsuranceTypesDao;
            this.myVendorCredentialsDocumentDAO = myVendorCredentialsDocumentDAO;
            this.iAuditDao = auditDao;
        }
        
       

        public InsuranceTypesVO getInsuranceType(InsuranceTypesVO insuranceTypeVO) throws BusinessServiceException {
                //InsuranceResponse iResp = new InsuranceResponse();
                java.util.ArrayList insuranceType = new java.util.ArrayList();
                java.util.ArrayList insuranceTypesList = new java.util.ArrayList();
                
             
                //UserProfile up = new UserProfile();
            	              
                try {   
                		/*up.setUserName(insuranceInfoDto.getUserId());
                		up = iUserProfileDao.query(up);*/
                		int vendorId = insuranceTypeVO.getVendorId();	
                		
                        InsuranceTypesVO insuranceTypesVO1 = new InsuranceTypesVO();
                        InsuranceTypesVO insuranceTypesVO2 = new InsuranceTypesVO();
             		   InsuranceTypesVO insuranceTypesVO3 = new InsuranceTypesVO();
             		 
                        insuranceTypesVO1.setVendorId(vendorId);
                        insuranceTypesList = (java.util.ArrayList) iInsuranceTypesDao
                                        .queryListForInsuranceTypes(insuranceTypeVO);
                        
                       if (insuranceTypesList != null && insuranceTypesList.size()>0) {
                    	  
                    	   
                    	   int insuranceListSize = insuranceTypesList.size();
                    	   if(insuranceListSize == 1){
                    		   insuranceTypesVO1 = (InsuranceTypesVO)insuranceTypesList.get(0);
                    		   insuranceTypesVO1.setButtonType("EDIT");
                    		   if(insuranceTypesVO1.getCategoryName().equalsIgnoreCase(AUTO_LIABILITY))
                    		   {
                    			   insuranceTypesVO2.setCategoryName(WORKER_COMPENSATION);
                    			   insuranceTypesVO3.setCategoryName(GENERAL_LIABILITY);
                    			   
                    			  
                    		   }
                    		   else if(insuranceTypesVO1.getCategoryName().equalsIgnoreCase(WORKER_COMPENSATION)){
                    			   insuranceTypesVO2.setCategoryName(GENERAL_LIABILITY);
                    			   insuranceTypesVO3.setCategoryName(AUTO_LIABILITY);
                    			   
                    		   }
                    		   else if(insuranceTypesVO1.getCategoryName().equalsIgnoreCase(GENERAL_LIABILITY)){
                    			   insuranceTypesVO2.setCategoryName(WORKER_COMPENSATION);
                    			   insuranceTypesVO3.setCategoryName(AUTO_LIABILITY);
                    			   
                    		   }
                    		   
                    		   insuranceTypesVO2.setSource("No Policy Information");
                    		   insuranceTypesVO2.setButtonType("ADD");
                    		   insuranceTypesVO2.setExpirationDate(null);
                    		   insuranceTypesVO2.setCurrentDocumentId(0);
                    		   
                    		   insuranceTypesVO3.setSource("No Policy Information");
                    		   insuranceTypesVO3.setButtonType("ADD");
                    		   insuranceTypesVO3.setExpirationDate(null);
                    		   insuranceTypesVO3.setCurrentDocumentId(0);
                    		   	
                    		   insuranceTypesList.add(insuranceTypesVO2);
                    		   insuranceTypesList.add(insuranceTypesVO3);
                    		   
                    	   }
                    	   else if(insuranceListSize == 2){
                    		   insuranceTypesVO1 = (InsuranceTypesVO)insuranceTypesList.get(0);
                    		   insuranceTypesVO1.setButtonType("EDIT");
                    		   insuranceTypesVO2 = (InsuranceTypesVO)insuranceTypesList.get(1);
                    		   insuranceTypesVO2.setButtonType("EDIT");
                    		   if((insuranceTypesVO1.getCategoryName().equalsIgnoreCase(AUTO_LIABILITY) || 
                    				   insuranceTypesVO2.getCategoryName().equalsIgnoreCase(AUTO_LIABILITY)) &&
                    				   (insuranceTypesVO1.getCategoryName().equalsIgnoreCase(WORKER_COMPENSATION) || 
                            				   insuranceTypesVO2.getCategoryName().equalsIgnoreCase(WORKER_COMPENSATION))){
                    			   insuranceTypesVO3.setCategoryName(GENERAL_LIABILITY);
                    			             			   
                    		   }
                    		   else if((insuranceTypesVO1.getCategoryName().equalsIgnoreCase(GENERAL_LIABILITY) || 
                    				   insuranceTypesVO2.getCategoryName().equalsIgnoreCase(GENERAL_LIABILITY)) &&
                    				   (insuranceTypesVO1.getCategoryName().equalsIgnoreCase(WORKER_COMPENSATION) || 
                            				   insuranceTypesVO2.getCategoryName().equalsIgnoreCase(WORKER_COMPENSATION))){
                    			   insuranceTypesVO3.setCategoryName(AUTO_LIABILITY);
                    		   }
                    		   else if((insuranceTypesVO1.getCategoryName().equalsIgnoreCase(GENERAL_LIABILITY) || 
                    				   insuranceTypesVO2.getCategoryName().equalsIgnoreCase(GENERAL_LIABILITY)) &&
                    				   (insuranceTypesVO1.getCategoryName().equalsIgnoreCase(AUTO_LIABILITY) || 
                            				   insuranceTypesVO2.getCategoryName().equalsIgnoreCase(AUTO_LIABILITY))){
                    			   insuranceTypesVO3.setCategoryName(WORKER_COMPENSATION);
                    		   }
                    		   insuranceTypesVO3.setSource("No Policy Information");
                    		   insuranceTypesVO3.setButtonType("ADD");
                    		   insuranceTypesVO3.setExpirationDate(null);
                    		   insuranceTypesVO3.setCurrentDocumentId(0);
                    		   insuranceTypesList.add(insuranceTypesVO3);
                    	    		   
                    	   }
                    	   else if(insuranceListSize == 3){
                    		   insuranceTypesVO1 = (InsuranceTypesVO)insuranceTypesList.get(0);
                    		   insuranceTypesVO1.setButtonType("EDIT");
                    		   insuranceTypesList.set(0, insuranceTypesVO1);
                    		   insuranceTypesVO2 = (InsuranceTypesVO)insuranceTypesList.get(1);
                    		   insuranceTypesVO2.setButtonType("EDIT");
                    		   insuranceTypesList.set(1, insuranceTypesVO2);
                    		   insuranceTypesVO3 = (InsuranceTypesVO)insuranceTypesList.get(2);
                    		   insuranceTypesVO3.setButtonType("EDIT");
                    		   insuranceTypesList.set(2, insuranceTypesVO3);
                    		   
                    	   }
                    	   
                    	   /**
                    	    * Code Added for Document Information listing in the Add Insurance Page.
                    	    */
                    	   for (java.util.Iterator it = insuranceTypesList.iterator(); it
                           				.hasNext();) {
			                   InsuranceTypesVO insuranceDocVO = (InsuranceTypesVO) it
			                                   .next();
			                   VendorCredentialsDocumentVO myVCredentialsDocumentVO = new VendorCredentialsDocumentVO();
			                   myVCredentialsDocumentVO.setVendorCredId(insuranceDocVO
			                                   					.getVendorCredentialId());
			
			                   VendorCredentialsDocumentVO newvo = myVendorCredentialsDocumentDAO
			                                   .getMaxVendorCredentialDocument(myVCredentialsDocumentVO);
			                   if (newvo != null) {
                                   insuranceDocVO.setCurrentDocumentId(newvo
                                                   .getDocumentId());

                                   DocumentVO myDocumentVO = new DocumentVO();
                                   myDocumentVO.setDocumentId(newvo.getDocumentId());                                                        
                                        myDocumentVO =  documentDaoFactory.getDocumentDao(Constants.DocumentTypes.VENDOR)
                                                                                                  .retrieveDocumentMetaDataByDocumentId(myDocumentVO);
                                        if(myDocumentVO != null)
                                        {
                                        insuranceDocVO.setDocURL(myDocumentVO.getFileName());
                                        }
                                   logger.info("Document Id for Add Insurance Page ------>"
                                                   + newvo.getDocumentId());
                           }else {
			                           insuranceDocVO.setCurrentDocumentId(0);
			               }
			               if(insuranceDocVO.getWfStateId()== Constants.PENDING_APPROVAL_CD){
			            	   insuranceDocVO.setStatusDesc(Constants.PENDING_APPROVAL);
			               }else if(insuranceDocVO.getWfStateId()== Constants.VERIFIED_CD){
			            	   //Sl-21003 : method added to fetch the reason codes
			            	   AuditVO auditVO = new AuditVO(); 
				               auditVO.setVendorId(vendorId);
				               auditVO.setAuditKeyId(insuranceDocVO.getVendorCredentialId());				             
				               auditVO.setWfStateId(insuranceDocVO.getWfStateId());
			            	   String reasonCodeDescription = iAuditDao.getReasonCodeDescription(auditVO); 
			            	   if(!reasonCodeDescription.equals(Constants.VERIFIED)){
			            		   insuranceDocVO.setStatusDesc(Constants.APPROVED+"-"+reasonCodeDescription);
			            	   }
			            	   else{
			            		   insuranceDocVO.setStatusDesc(Constants.APPROVED);
			            	   }
			               }else if(insuranceDocVO.getWfStateId()== Constants.REVIEWED_CD){
			            	   insuranceDocVO.setStatusDesc(Constants.REVIEWED);
			               }
			               else{
			            	   AuditVO auditVO = new AuditVO(); 
				               auditVO.setVendorId(vendorId);
				               auditVO.setAuditKeyId(insuranceDocVO.getVendorCredentialId());				             
				               auditVO.setWfStateId(insuranceDocVO.getWfStateId());
				               String reasonCodeDescription = iAuditDao.getReasonCodeDescription(auditVO);   
				               insuranceDocVO.setStatusDesc(reasonCodeDescription);
			               }
			               
			               
                    	   }
                    	   insuranceTypeVO.setInsuranceList( insuranceTypesList);
                    	   insuranceTypeVO.setAddPolicy(false);
                }
                else
                {
                	
                	insuranceTypesList = new ArrayList();
                	insuranceTypesVO1.setCategoryName(AUTO_LIABILITY);
                	insuranceTypesVO1.setSource("No Policy Information");
         		   	insuranceTypesVO1.setButtonType("ADD");
         		   	insuranceTypesVO1.setExpirationDate(null);
         		   	insuranceTypesVO1.setCurrentDocumentId(0);
         		   	
         		   	
                	insuranceTypesVO2.setCategoryName(WORKER_COMPENSATION);
                	insuranceTypesVO2.setSource("No Policy Information");
         		   	insuranceTypesVO2.setButtonType("ADD");
         		   	insuranceTypesVO2.setExpirationDate(null);
         		   	insuranceTypesVO2.setCurrentDocumentId(0);
         		   	
     			   	insuranceTypesVO3.setCategoryName(GENERAL_LIABILITY);
         		   	insuranceTypesVO3.setSource("No Policy Information");
         		   	insuranceTypesVO3.setButtonType("ADD");
         		   	insuranceTypesVO3.setExpirationDate(null);
         		   	insuranceTypesVO3.setCurrentDocumentId(0);
         		   	
         		   insuranceTypesList.add(insuranceTypesVO1);
         		   insuranceTypesList.add(insuranceTypesVO2);
         		   insuranceTypesList.add(insuranceTypesVO3);
         		   insuranceTypeVO.setInsuranceList( insuranceTypesList);
         		   insuranceTypeVO.setAddPolicy(true);
                }
                    	   
                        
                } catch (DBException ex) {
			logger
					.info("DB Exception @InsurancePolicyBOImpl.getInsuranceType() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @InsurancePolicyBOImpl.getInsuranceType() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @InsurancePolicyBOImpl.getInsuranceType() due to "
							+ ex.getMessage());
		}

                return insuranceTypeVO;
        }

       

        public VendorHdr getInsuranceTypesInformation(
        		VendorHdr vHdr) throws BusinessServiceException  {
                java.util.ArrayList insuranceTypesList = new java.util.ArrayList();
                
                   try {
                        int vendorId = vHdr.getVendorId();
                        System.out.println("---------------vendorId-----"+vendorId);

                        if (vendorId != 0) {
                        		//Changes done for Insurance story # 17 START
                        				//vHdr.setVendorId(vendorId);
                        				vHdr=iVendorHdrDao.query(vHdr);
                        				if(vHdr!=null)
                        	        	{
                        	                        //logger.info("INSURANCE ID > INSIDE ");
                        	                System.out.println("*************    Start of changes for Insurance");

											String VLI = "";
											String WCI = "";
											String CBGLI = "";
											String VLIAmount = "";
											String WCIAmount = "";
											String CBGLIAmount = "";
											
                        	                try {
                        	                 VLI = vHdr.getVLI().toString();
                        	                 WCI = vHdr.getWCI().toString();
                        	                 CBGLI = vHdr.getCBGLI().toString();
                        	                 
                        	                 VLIAmount = vHdr.getVLIAmount();
                        	                 WCIAmount = vHdr.getWCIAmount();
                        	                 CBGLIAmount = vHdr.getCBGLIAmount();
                        	                 
                        	                 System.out.println("*************    VLI:"+VLI);
                        	                 System.out.println("*************    WCI:"+WCI);
                        	                 System.out.println("*************    CBGLI:"+CBGLI);
                        	                 
                        	                 
                        	                System.out.println("I am in BUSINS BEAN - Testing for nukk ");
                        	               
                        	               
                        	                if (VLI == null) VLI = "0";
		                					if (WCI == null ) WCI = "0";
		                					if (CBGLI == null) CBGLI = "0";
										    }catch (Exception e) {
												System.out.println("I am in catch block");
												VLI = "0";
												WCI = "0";
												CBGLI = "0";
											}
										    System.out.println("*************    VLI:"+VLI);
                       	                   System.out.println("*************    WCI:"+WCI);
                       	                   System.out.println("*************    CBGLI:"+CBGLI);
                        	                
                        	        	}
                        				//Changes done for Insurance story # 17 END

                            
                        }
                } catch (DBException ex) {
			logger
					.info("DB Exception @InsurancePolicyBOImpl.getInsuranceType() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @InsurancePolicyBOImpl.getInsuranceType() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @InsurancePolicyBOImpl.getInsuranceType() due to "
							+ ex.getMessage());
		}

                return vHdr;
        }

        public boolean UpdateDbInsuranceSelection(VendorHdr vHdr) throws BusinessServiceException  {
			System.out.println("*****************UpdateDbInsuranceSelection Method Business Bean QualificationTnCBusinessBean************");
			 try {
	    	 	System.out.println("\n\n\t\t Inside Policy BO Impl vendor Id" + vHdr.getVendorId());
	            int vendorId = vHdr.getVendorId();
	            System.out.println("\n\n\t\t Inside Policy BO Vendor Id " + vendorId); 
	            	
	            if (vendorId != 0) {
	              	int z=iVendorHdrDao.UpdateDbInsuranceSelection(vHdr);
				}
	            return true;
		}catch (DBException ex) {
			logger
			.info("DB Exception @InsurancePolicyBOImpl.getInsuranceType() due to"
					+ ex.getMessage());
	throw new BusinessServiceException(ex.getMessage());
} catch (Exception ex) {
	ex.printStackTrace();
	logger
			.info("General Exception @InsurancePolicyBOImpl.getInsuranceType() due to"
					+ ex.getMessage());
	throw new BusinessServiceException(
			"General Exception @InsurancePolicyBOImpl.getInsuranceType() due to "
					+ ex.getMessage());
}
	} 
	
        

    	public void sendWCINotRequiredAlert(SecurityContext security){
		//For Triggering AOP
    	}
    	
    	  
  		public int getEmailIndicator(Integer vendorId,String wci) throws BusinessServiceException{
			int emailIndicator=-1;
			Integer prevWCI;
			Integer currentWCI = new Integer(wci);
			try{
				prevWCI = iVendorHdrDao.getWCIInd(vendorId);
			if((null == prevWCI||1==prevWCI)&&(currentWCI==0)){
				emailIndicator=1;
			}
			}catch(DBException e){
				logger
				.error("General Exception @InsuranceDelegateImpl.loadRegistration() due to"
						+ e.getMessage());
			}
			return emailIndicator;
		}

	
	private String convertStringToDollar(String sAmount){
		String retVal ="";
		double dConversion = Double.parseDouble(sAmount);

		retVal = java.text.NumberFormat.getCurrencyInstance().format(dConversion);

		logger.info(retVal);
	    retVal = retVal.replace("$", "");
	    logger.info(retVal);
	    retVal = retVal.replace(",", "");
	    logger.info(retVal);

		return retVal;
	}
	public String isFirstTimeVisit(Integer vendorId) throws BusinessServiceException{
		String result="";
		VendorHdr vHdr = new VendorHdr();
		if(vendorId>0){
			try {
				vHdr=iVendorHdrDao.getInsuranceIndicators(vendorId);
				if(null != vHdr){
					Integer cbgli = vHdr.getCBGLI();
					Integer vli = vHdr.getVLI();
					Integer wci = vHdr.getWCI();
					if(cbgli == null  && vli == null && wci == null){
						result="0";
					} else {
						result="1";
					}
				}
			}catch (DBException ex) {
				logger.info("DB Exception @InsurancePolicyBOImpl.isFirstTimeVisit() due to"
						+ ex.getMessage());
				throw new BusinessServiceException(ex.getMessage());
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.info("General Exception @InsurancePolicyBOImpl.getInsuranceType() due to"
						+ ex.getMessage());
				throw new BusinessServiceException("General Exception @InsurancePolicyBOImpl.getInsuranceType() due to "
						+ ex.getMessage());
			}
		}
		return result;
	}
 

	public IVendorHdrDao getIVendorHdrDao() {
		return iVendorHdrDao;
	}



	public void setIVendorHdrDao(IVendorHdrDao vendorHdrDao) {
		iVendorHdrDao = vendorHdrDao;
	}



	public ICredentialDao getICredentialDao() {
		return iCredentialDao;
	}



	public void setICredentialDao(ICredentialDao credentialDao) {
		iCredentialDao = credentialDao;
	}



	public IUserProfileDao getIUserProfileDao() {
		return iUserProfileDao;
	}



	public void setIUserProfileDao(IUserProfileDao userProfileDao) {
		iUserProfileDao = userProfileDao;
	}



	public IInsuranceTypesDao getIInsuranceTypesDao() {
		return iInsuranceTypesDao;
	}



	public void setIInsuranceTypesDao(IInsuranceTypesDao insuranceTypesDao) {
		iInsuranceTypesDao = insuranceTypesDao;
	}



	public IAuditDao getIAuditDao() {
		return iAuditDao;
	}



	public void setIAuditDao(IAuditDao auditDao) {
		iAuditDao = auditDao;
	}

	
	
	/**
	 * @param documentDaoFactory
	 */
	public void setDocumentDaoFactory(DocumentDaoFactory documentDaoFactory) {
		this.documentDaoFactory = documentDaoFactory;
	}
	
	
    public CredentialProfile getAdditionalInsuranceList(CredentialProfile credProfile) throws BusinessServiceException {
        List additionalInsuranceList=new ArrayList();
        additionalInsuranceList=iInsuranceTypesDao.getAdditionalInsuranceList(credProfile);
        credProfile.setAdditionalInsuranceList(additionalInsuranceList);
		return credProfile;
         
     
}
}
