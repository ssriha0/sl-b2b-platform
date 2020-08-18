
/**
 * CampaignProperties.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:41 LKT)
 */
            
                package responsys.ws57;
            

            /**
            *  CampaignProperties bean class
            */
        
        public  class CampaignProperties
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = CampaignProperties
                Namespace URI = urn:ws57.responsys
                Namespace Prefix = 
                */
            

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("urn:ws57.responsys")){
                return "";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        

                        /**
                        * field for CampaignName
                        */

                        
                                    protected java.lang.String localCampaignName ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCampaignName(){
                               return localCampaignName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CampaignName
                               */
                               public void setCampaignName(java.lang.String param){
                            
                                            this.localCampaignName=param;
                                    

                               }
                            

                        /**
                        * field for DisplayName
                        */

                        
                                    protected java.lang.String localDisplayName ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDisplayName(){
                               return localDisplayName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DisplayName
                               */
                               public void setDisplayName(java.lang.String param){
                            
                                            this.localDisplayName=param;
                                    

                               }
                            

                        /**
                        * field for Subject
                        */

                        
                                    protected java.lang.String localSubject ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSubject(){
                               return localSubject;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Subject
                               */
                               public void setSubject(java.lang.String param){
                            
                                            this.localSubject=param;
                                    

                               }
                            

                        /**
                        * field for Purpose
                        */

                        
                                    protected responsys.ws57.CampaignPurpose localPurpose ;
                                

                           /**
                           * Auto generated getter method
                           * @return responsys.ws57.CampaignPurpose
                           */
                           public  responsys.ws57.CampaignPurpose getPurpose(){
                               return localPurpose;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Purpose
                               */
                               public void setPurpose(responsys.ws57.CampaignPurpose param){
                            
                                            this.localPurpose=param;
                                    

                               }
                            

                        /**
                        * field for Attachment
                        * This was an Array!
                        */

                        
                                    protected responsys.ws57.InteractObject[] localAttachment ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAttachmentTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return responsys.ws57.InteractObject[]
                           */
                           public  responsys.ws57.InteractObject[] getAttachment(){
                               return localAttachment;
                           }

                           
                        


                               
                              /**
                               * validate the array for Attachment
                               */
                              protected void validateAttachment(responsys.ws57.InteractObject[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Attachment
                              */
                              public void setAttachment(responsys.ws57.InteractObject[] param){
                              
                                   validateAttachment(param);

                               
                                          if (param != null){
                                             //update the setting tracker
                                             localAttachmentTracker = true;
                                          } else {
                                             localAttachmentTracker = false;
                                                 
                                          }
                                      
                                      this.localAttachment=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param responsys.ws57.InteractObject
                             */
                             public void addAttachment(responsys.ws57.InteractObject param){
                                   if (localAttachment == null){
                                   localAttachment = new responsys.ws57.InteractObject[]{};
                                   }

                            
                                 //update the setting tracker
                                localAttachmentTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localAttachment);
                               list.add(param);
                               this.localAttachment =
                             (responsys.ws57.InteractObject[])list.toArray(
                            new responsys.ws57.InteractObject[list.size()]);

                             }
                             

                        /**
                        * field for AutoSenseAol
                        */

                        
                                    protected boolean localAutoSenseAol ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getAutoSenseAol(){
                               return localAutoSenseAol;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AutoSenseAol
                               */
                               public void setAutoSenseAol(boolean param){
                            
                                            this.localAutoSenseAol=param;
                                    

                               }
                            

                        /**
                        * field for ClickThru
                        */

                        
                                    protected responsys.ws57.ClickThru localClickThru ;
                                

                           /**
                           * Auto generated getter method
                           * @return responsys.ws57.ClickThru
                           */
                           public  responsys.ws57.ClickThru getClickThru(){
                               return localClickThru;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ClickThru
                               */
                               public void setClickThru(responsys.ws57.ClickThru param){
                            
                                            this.localClickThru=param;
                                    

                               }
                            

                        /**
                        * field for ClickTrack
                        */

                        
                                    protected responsys.ws57.InteractObject localClickTrack ;
                                

                           /**
                           * Auto generated getter method
                           * @return responsys.ws57.InteractObject
                           */
                           public  responsys.ws57.InteractObject getClickTrack(){
                               return localClickTrack;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ClickTrack
                               */
                               public void setClickTrack(responsys.ws57.InteractObject param){
                            
                                            this.localClickTrack=param;
                                    

                               }
                            

                        /**
                        * field for ConversionTrack
                        */

                        
                                    protected boolean localConversionTrack ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getConversionTrack(){
                               return localConversionTrack;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ConversionTrack
                               */
                               public void setConversionTrack(boolean param){
                            
                                            this.localConversionTrack=param;
                                    

                               }
                            

                        /**
                        * field for Document
                        * This was an Array!
                        */

                        
                                    protected responsys.ws57.Document[] localDocument ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDocumentTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return responsys.ws57.Document[]
                           */
                           public  responsys.ws57.Document[] getDocument(){
                               return localDocument;
                           }

                           
                        


                               
                              /**
                               * validate the array for Document
                               */
                              protected void validateDocument(responsys.ws57.Document[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Document
                              */
                              public void setDocument(responsys.ws57.Document[] param){
                              
                                   validateDocument(param);

                               
                                          if (param != null){
                                             //update the setting tracker
                                             localDocumentTracker = true;
                                          } else {
                                             localDocumentTracker = false;
                                                 
                                          }
                                      
                                      this.localDocument=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param responsys.ws57.Document
                             */
                             public void addDocument(responsys.ws57.Document param){
                                   if (localDocument == null){
                                   localDocument = new responsys.ws57.Document[]{};
                                   }

                            
                                 //update the setting tracker
                                localDocumentTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localDocument);
                               list.add(param);
                               this.localDocument =
                             (responsys.ws57.Document[])list.toArray(
                            new responsys.ws57.Document[list.size()]);

                             }
                             

                        /**
                        * field for HtmlToAol
                        */

                        
                                    protected boolean localHtmlToAol ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getHtmlToAol(){
                               return localHtmlToAol;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HtmlToAol
                               */
                               public void setHtmlToAol(boolean param){
                            
                                            this.localHtmlToAol=param;
                                    

                               }
                            

                        /**
                        * field for InlineMedia
                        */

                        
                                    protected boolean localInlineMedia ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getInlineMedia(){
                               return localInlineMedia;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InlineMedia
                               */
                               public void setInlineMedia(boolean param){
                            
                                            this.localInlineMedia=param;
                                    

                               }
                            

                        /**
                        * field for LogTable
                        * This was an Array!
                        */

                        
                                    protected responsys.ws57.LogTable[] localLogTable ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLogTableTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return responsys.ws57.LogTable[]
                           */
                           public  responsys.ws57.LogTable[] getLogTable(){
                               return localLogTable;
                           }

                           
                        


                               
                              /**
                               * validate the array for LogTable
                               */
                              protected void validateLogTable(responsys.ws57.LogTable[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param LogTable
                              */
                              public void setLogTable(responsys.ws57.LogTable[] param){
                              
                                   validateLogTable(param);

                               
                                          if (param != null){
                                             //update the setting tracker
                                             localLogTableTracker = true;
                                          } else {
                                             localLogTableTracker = false;
                                                 
                                          }
                                      
                                      this.localLogTable=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param responsys.ws57.LogTable
                             */
                             public void addLogTable(responsys.ws57.LogTable param){
                                   if (localLogTable == null){
                                   localLogTable = new responsys.ws57.LogTable[]{};
                                   }

                            
                                 //update the setting tracker
                                localLogTableTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localLogTable);
                               list.add(param);
                               this.localLogTable =
                             (responsys.ws57.LogTable[])list.toArray(
                            new responsys.ws57.LogTable[list.size()]);

                             }
                             

                        /**
                        * field for OpenSense
                        */

                        
                                    protected boolean localOpenSense ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getOpenSense(){
                               return localOpenSense;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OpenSense
                               */
                               public void setOpenSense(boolean param){
                            
                                            this.localOpenSense=param;
                                    

                               }
                            

                        /**
                        * field for ParentFolder
                        */

                        
                                    protected java.lang.String localParentFolder ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getParentFolder(){
                               return localParentFolder;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ParentFolder
                               */
                               public void setParentFolder(java.lang.String param){
                            
                                            this.localParentFolder=param;
                                    

                               }
                            

                        /**
                        * field for SkipMultiPart
                        */

                        
                                    protected boolean localSkipMultiPart ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getSkipMultiPart(){
                               return localSkipMultiPart;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SkipMultiPart
                               */
                               public void setSkipMultiPart(boolean param){
                            
                                            this.localSkipMultiPart=param;
                                    

                               }
                            

                        /**
                        * field for TestEmailAddress
                        */

                        
                                    protected java.lang.String localTestEmailAddress ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTestEmailAddress(){
                               return localTestEmailAddress;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TestEmailAddress
                               */
                               public void setTestEmailAddress(java.lang.String param){
                            
                                            this.localTestEmailAddress=param;
                                    

                               }
                            

                        /**
                        * field for Locale
                        */

                        
                                    protected responsys.ws57.RecipientLocale localLocale ;
                                

                           /**
                           * Auto generated getter method
                           * @return responsys.ws57.RecipientLocale
                           */
                           public  responsys.ws57.RecipientLocale getLocale(){
                               return localLocale;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Locale
                               */
                               public void setLocale(responsys.ws57.RecipientLocale param){
                            
                                            this.localLocale=param;
                                    

                               }
                            

                        /**
                        * field for CampaignVariable
                        * This was an Array!
                        */

                        
                                    protected responsys.ws57.CampaignVariable[] localCampaignVariable ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCampaignVariableTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return responsys.ws57.CampaignVariable[]
                           */
                           public  responsys.ws57.CampaignVariable[] getCampaignVariable(){
                               return localCampaignVariable;
                           }

                           
                        


                               
                              /**
                               * validate the array for CampaignVariable
                               */
                              protected void validateCampaignVariable(responsys.ws57.CampaignVariable[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param CampaignVariable
                              */
                              public void setCampaignVariable(responsys.ws57.CampaignVariable[] param){
                              
                                   validateCampaignVariable(param);

                               
                                          if (param != null){
                                             //update the setting tracker
                                             localCampaignVariableTracker = true;
                                          } else {
                                             localCampaignVariableTracker = false;
                                                 
                                          }
                                      
                                      this.localCampaignVariable=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param responsys.ws57.CampaignVariable
                             */
                             public void addCampaignVariable(responsys.ws57.CampaignVariable param){
                                   if (localCampaignVariable == null){
                                   localCampaignVariable = new responsys.ws57.CampaignVariable[]{};
                                   }

                            
                                 //update the setting tracker
                                localCampaignVariableTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localCampaignVariable);
                               list.add(param);
                               this.localCampaignVariable =
                             (responsys.ws57.CampaignVariable[])list.toArray(
                            new responsys.ws57.CampaignVariable[list.size()]);

                             }
                             

                        /**
                        * field for Utf8
                        */

                        
                                    protected boolean localUtf8 ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getUtf8(){
                               return localUtf8;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Utf8
                               */
                               public void setUtf8(boolean param){
                            
                                            this.localUtf8=param;
                                    

                               }
                            

                        /**
                        * field for OptOut
                        */

                        
                                    protected responsys.ws57.OptOut localOptOut ;
                                

                           /**
                           * Auto generated getter method
                           * @return responsys.ws57.OptOut
                           */
                           public  responsys.ws57.OptOut getOptOut(){
                               return localOptOut;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OptOut
                               */
                               public void setOptOut(responsys.ws57.OptOut param){
                            
                                            this.localOptOut=param;
                                    

                               }
                            

                        /**
                        * field for FromEmailAddress
                        */

                        
                                    protected java.lang.String localFromEmailAddress ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFromEmailAddress(){
                               return localFromEmailAddress;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param FromEmailAddress
                               */
                               public void setFromEmailAddress(java.lang.String param){
                            
                                            this.localFromEmailAddress=param;
                                    

                               }
                            

                        /**
                        * field for ReplyToEmailAddress
                        */

                        
                                    protected java.lang.String localReplyToEmailAddress ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getReplyToEmailAddress(){
                               return localReplyToEmailAddress;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ReplyToEmailAddress
                               */
                               public void setReplyToEmailAddress(java.lang.String param){
                            
                                            this.localReplyToEmailAddress=param;
                                    

                               }
                            

                        /**
                        * field for MarketingStrategy
                        */

                        
                                    protected java.lang.String localMarketingStrategy ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMarketingStrategy(){
                               return localMarketingStrategy;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MarketingStrategy
                               */
                               public void setMarketingStrategy(java.lang.String param){
                            
                                            this.localMarketingStrategy=param;
                                    

                               }
                            

                        /**
                        * field for MarketingProgram
                        */

                        
                                    protected java.lang.String localMarketingProgram ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMarketingProgram(){
                               return localMarketingProgram;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MarketingProgram
                               */
                               public void setMarketingProgram(java.lang.String param){
                            
                                            this.localMarketingProgram=param;
                                    

                               }
                            

                        /**
                        * field for InteractCampaignID
                        */

                        
                                    protected java.lang.String localInteractCampaignID ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getInteractCampaignID(){
                               return localInteractCampaignID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InteractCampaignID
                               */
                               public void setInteractCampaignID(java.lang.String param){
                            
                                            this.localInteractCampaignID=param;
                                    

                               }
                            

                        /**
                        * field for ExternalCampaignID
                        */

                        
                                    protected java.lang.String localExternalCampaignID ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getExternalCampaignID(){
                               return localExternalCampaignID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ExternalCampaignID
                               */
                               public void setExternalCampaignID(java.lang.String param){
                            
                                            this.localExternalCampaignID=param;
                                    

                               }
                            

                        /**
                        * field for TestDistributionList
                        */

                        
                                    protected responsys.ws57.InteractObject localTestDistributionList ;
                                

                           /**
                           * Auto generated getter method
                           * @return responsys.ws57.InteractObject
                           */
                           public  responsys.ws57.InteractObject getTestDistributionList(){
                               return localTestDistributionList;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TestDistributionList
                               */
                               public void setTestDistributionList(responsys.ws57.InteractObject param){
                            
                                            this.localTestDistributionList=param;
                                    

                               }
                            

                        /**
                        * field for DistributionList
                        */

                        
                                    protected responsys.ws57.InteractObject localDistributionList ;
                                

                           /**
                           * Auto generated getter method
                           * @return responsys.ws57.InteractObject
                           */
                           public  responsys.ws57.InteractObject getDistributionList(){
                               return localDistributionList;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DistributionList
                               */
                               public void setDistributionList(responsys.ws57.InteractObject param){
                            
                                            this.localDistributionList=param;
                                    

                               }
                            

                        /**
                        * field for SeedList
                        */

                        
                                    protected responsys.ws57.InteractObject localSeedList ;
                                

                           /**
                           * Auto generated getter method
                           * @return responsys.ws57.InteractObject
                           */
                           public  responsys.ws57.InteractObject getSeedList(){
                               return localSeedList;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SeedList
                               */
                               public void setSeedList(responsys.ws57.InteractObject param){
                            
                                            this.localSeedList=param;
                                    

                               }
                            

                        /**
                        * field for SuppressDataSource
                        * This was an Array!
                        */

                        
                                    protected responsys.ws57.InteractObject[] localSuppressDataSource ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSuppressDataSourceTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return responsys.ws57.InteractObject[]
                           */
                           public  responsys.ws57.InteractObject[] getSuppressDataSource(){
                               return localSuppressDataSource;
                           }

                           
                        


                               
                              /**
                               * validate the array for SuppressDataSource
                               */
                              protected void validateSuppressDataSource(responsys.ws57.InteractObject[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param SuppressDataSource
                              */
                              public void setSuppressDataSource(responsys.ws57.InteractObject[] param){
                              
                                   validateSuppressDataSource(param);

                               
                                          if (param != null){
                                             //update the setting tracker
                                             localSuppressDataSourceTracker = true;
                                          } else {
                                             localSuppressDataSourceTracker = false;
                                                 
                                          }
                                      
                                      this.localSuppressDataSource=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param responsys.ws57.InteractObject
                             */
                             public void addSuppressDataSource(responsys.ws57.InteractObject param){
                                   if (localSuppressDataSource == null){
                                   localSuppressDataSource = new responsys.ws57.InteractObject[]{};
                                   }

                            
                                 //update the setting tracker
                                localSuppressDataSourceTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localSuppressDataSource);
                               list.add(param);
                               this.localSuppressDataSource =
                             (responsys.ws57.InteractObject[])list.toArray(
                            new responsys.ws57.InteractObject[list.size()]);

                             }
                             

                        /**
                        * field for SupplementalDataSource
                        * This was an Array!
                        */

                        
                                    protected responsys.ws57.InteractObject[] localSupplementalDataSource ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSupplementalDataSourceTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return responsys.ws57.InteractObject[]
                           */
                           public  responsys.ws57.InteractObject[] getSupplementalDataSource(){
                               return localSupplementalDataSource;
                           }

                           
                        


                               
                              /**
                               * validate the array for SupplementalDataSource
                               */
                              protected void validateSupplementalDataSource(responsys.ws57.InteractObject[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param SupplementalDataSource
                              */
                              public void setSupplementalDataSource(responsys.ws57.InteractObject[] param){
                              
                                   validateSupplementalDataSource(param);

                               
                                          if (param != null){
                                             //update the setting tracker
                                             localSupplementalDataSourceTracker = true;
                                          } else {
                                             localSupplementalDataSourceTracker = false;
                                                 
                                          }
                                      
                                      this.localSupplementalDataSource=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param responsys.ws57.InteractObject
                             */
                             public void addSupplementalDataSource(responsys.ws57.InteractObject param){
                                   if (localSupplementalDataSource == null){
                                   localSupplementalDataSource = new responsys.ws57.InteractObject[]{};
                                   }

                            
                                 //update the setting tracker
                                localSupplementalDataSourceTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localSupplementalDataSource);
                               list.add(param);
                               this.localSupplementalDataSource =
                             (responsys.ws57.InteractObject[])list.toArray(
                            new responsys.ws57.InteractObject[list.size()]);

                             }
                             

                        /**
                        * field for SkipDuplicates
                        */

                        
                                    protected boolean localSkipDuplicates ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getSkipDuplicates(){
                               return localSkipDuplicates;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SkipDuplicates
                               */
                               public void setSkipDuplicates(boolean param){
                            
                                            this.localSkipDuplicates=param;
                                    

                               }
                            

                        /**
                        * field for AutoSense
                        */

                        
                                    protected boolean localAutoSense ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getAutoSense(){
                               return localAutoSense;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AutoSense
                               */
                               public void setAutoSense(boolean param){
                            
                                            this.localAutoSense=param;
                                    

                               }
                            

                        /**
                        * field for UseAccountWide
                        */

                        
                                    protected boolean localUseAccountWide ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getUseAccountWide(){
                               return localUseAccountWide;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param UseAccountWide
                               */
                               public void setUseAccountWide(boolean param){
                            
                                            this.localUseAccountWide=param;
                                    

                               }
                            

                        /**
                        * field for AcknowledgmentDocument
                        */

                        
                                    protected responsys.ws57.InteractObject localAcknowledgmentDocument ;
                                

                           /**
                           * Auto generated getter method
                           * @return responsys.ws57.InteractObject
                           */
                           public  responsys.ws57.InteractObject getAcknowledgmentDocument(){
                               return localAcknowledgmentDocument;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AcknowledgmentDocument
                               */
                               public void setAcknowledgmentDocument(responsys.ws57.InteractObject param){
                            
                                            this.localAcknowledgmentDocument=param;
                                    

                               }
                            

                        /**
                        * field for AcknowledgmentURL
                        */

                        
                                    protected java.lang.String localAcknowledgmentURL ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAcknowledgmentURL(){
                               return localAcknowledgmentURL;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AcknowledgmentURL
                               */
                               public void setAcknowledgmentURL(java.lang.String param){
                            
                                            this.localAcknowledgmentURL=param;
                                    

                               }
                            

                        /**
                        * field for FollowUp
                        */

                        
                                    protected responsys.ws57.InteractObject localFollowUp ;
                                

                           /**
                           * Auto generated getter method
                           * @return responsys.ws57.InteractObject
                           */
                           public  responsys.ws57.InteractObject getFollowUp(){
                               return localFollowUp;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param FollowUp
                               */
                               public void setFollowUp(responsys.ws57.InteractObject param){
                            
                                            this.localFollowUp=param;
                                    

                               }
                            

                        /**
                        * field for ResponseDB
                        */

                        
                                    protected responsys.ws57.InteractObject localResponseDB ;
                                

                           /**
                           * Auto generated getter method
                           * @return responsys.ws57.InteractObject
                           */
                           public  responsys.ws57.InteractObject getResponseDB(){
                               return localResponseDB;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResponseDB
                               */
                               public void setResponseDB(responsys.ws57.InteractObject param){
                            
                                            this.localResponseDB=param;
                                    

                               }
                            

     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;
        
        try{
          isReaderMTOMAware = java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(java.lang.IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }
     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       CampaignProperties.this.serialize(parentQName,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               parentQName,factory,dataSource);
            
       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"urn:ws57.responsys");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":CampaignProperties",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "CampaignProperties",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"campaignName", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"campaignName");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("campaignName");
                                    }
                                

                                          if (localCampaignName==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCampaignName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"displayName", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"displayName");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("displayName");
                                    }
                                

                                          if (localDisplayName==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDisplayName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"subject", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"subject");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("subject");
                                    }
                                

                                          if (localSubject==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSubject);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    if (localPurpose==null){

                                            java.lang.String namespace2 = "urn:ws57.responsys";

                                        if (! namespace2.equals("")) {
                                            java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

                                            if (prefix2 == null) {
                                                prefix2 = generatePrefix(namespace2);

                                                xmlWriter.writeStartElement(prefix2,"purpose", namespace2);
                                                xmlWriter.writeNamespace(prefix2, namespace2);
                                                xmlWriter.setPrefix(prefix2, namespace2);

                                            } else {
                                                xmlWriter.writeStartElement(namespace2,"purpose");
                                            }

                                        } else {
                                            xmlWriter.writeStartElement("purpose");
                                        }


                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localPurpose.serialize(new javax.xml.namespace.QName("urn:ws57.responsys","purpose"),
                                        factory,xmlWriter);
                                    }
                                 if (localAttachmentTracker){
                                       if (localAttachment!=null){
                                            for (int i = 0;i < localAttachment.length;i++){
                                                if (localAttachment[i] != null){
                                                 localAttachment[i].serialize(new javax.xml.namespace.QName("urn:ws57.responsys","attachment"),
                                                           factory,xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("attachment cannot be null!!");
                                        
                                    }
                                 }
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"autoSenseAol", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"autoSenseAol");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("autoSenseAol");
                                    }
                                
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("autoSenseAol cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAutoSenseAol));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    if (localClickThru==null){

                                            java.lang.String namespace2 = "urn:ws57.responsys";

                                        if (! namespace2.equals("")) {
                                            java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

                                            if (prefix2 == null) {
                                                prefix2 = generatePrefix(namespace2);

                                                xmlWriter.writeStartElement(prefix2,"clickThru", namespace2);
                                                xmlWriter.writeNamespace(prefix2, namespace2);
                                                xmlWriter.setPrefix(prefix2, namespace2);

                                            } else {
                                                xmlWriter.writeStartElement(namespace2,"clickThru");
                                            }

                                        } else {
                                            xmlWriter.writeStartElement("clickThru");
                                        }


                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localClickThru.serialize(new javax.xml.namespace.QName("urn:ws57.responsys","clickThru"),
                                        factory,xmlWriter);
                                    }
                                
                                    if (localClickTrack==null){

                                            java.lang.String namespace2 = "urn:ws57.responsys";

                                        if (! namespace2.equals("")) {
                                            java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

                                            if (prefix2 == null) {
                                                prefix2 = generatePrefix(namespace2);

                                                xmlWriter.writeStartElement(prefix2,"clickTrack", namespace2);
                                                xmlWriter.writeNamespace(prefix2, namespace2);
                                                xmlWriter.setPrefix(prefix2, namespace2);

                                            } else {
                                                xmlWriter.writeStartElement(namespace2,"clickTrack");
                                            }

                                        } else {
                                            xmlWriter.writeStartElement("clickTrack");
                                        }


                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localClickTrack.serialize(new javax.xml.namespace.QName("urn:ws57.responsys","clickTrack"),
                                        factory,xmlWriter);
                                    }
                                
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"conversionTrack", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"conversionTrack");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("conversionTrack");
                                    }
                                
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("conversionTrack cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localConversionTrack));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                              if (localDocumentTracker){
                                       if (localDocument!=null){
                                            for (int i = 0;i < localDocument.length;i++){
                                                if (localDocument[i] != null){
                                                 localDocument[i].serialize(new javax.xml.namespace.QName("urn:ws57.responsys","document"),
                                                           factory,xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("document cannot be null!!");
                                        
                                    }
                                 }
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"htmlToAol", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"htmlToAol");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("htmlToAol");
                                    }
                                
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("htmlToAol cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHtmlToAol));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"inlineMedia", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"inlineMedia");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("inlineMedia");
                                    }
                                
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("inlineMedia cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInlineMedia));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                              if (localLogTableTracker){
                                       if (localLogTable!=null){
                                            for (int i = 0;i < localLogTable.length;i++){
                                                if (localLogTable[i] != null){
                                                 localLogTable[i].serialize(new javax.xml.namespace.QName("urn:ws57.responsys","logTable"),
                                                           factory,xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("logTable cannot be null!!");
                                        
                                    }
                                 }
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"openSense", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"openSense");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("openSense");
                                    }
                                
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("openSense cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOpenSense));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"parentFolder", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"parentFolder");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("parentFolder");
                                    }
                                

                                          if (localParentFolder==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localParentFolder);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"skipMultiPart", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"skipMultiPart");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("skipMultiPart");
                                    }
                                
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("skipMultiPart cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSkipMultiPart));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"testEmailAddress", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"testEmailAddress");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("testEmailAddress");
                                    }
                                

                                          if (localTestEmailAddress==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTestEmailAddress);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    if (localLocale==null){

                                            java.lang.String namespace2 = "urn:ws57.responsys";

                                        if (! namespace2.equals("")) {
                                            java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

                                            if (prefix2 == null) {
                                                prefix2 = generatePrefix(namespace2);

                                                xmlWriter.writeStartElement(prefix2,"locale", namespace2);
                                                xmlWriter.writeNamespace(prefix2, namespace2);
                                                xmlWriter.setPrefix(prefix2, namespace2);

                                            } else {
                                                xmlWriter.writeStartElement(namespace2,"locale");
                                            }

                                        } else {
                                            xmlWriter.writeStartElement("locale");
                                        }


                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localLocale.serialize(new javax.xml.namespace.QName("urn:ws57.responsys","locale"),
                                        factory,xmlWriter);
                                    }
                                 if (localCampaignVariableTracker){
                                       if (localCampaignVariable!=null){
                                            for (int i = 0;i < localCampaignVariable.length;i++){
                                                if (localCampaignVariable[i] != null){
                                                 localCampaignVariable[i].serialize(new javax.xml.namespace.QName("urn:ws57.responsys","campaignVariable"),
                                                           factory,xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("campaignVariable cannot be null!!");
                                        
                                    }
                                 }
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"utf8", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"utf8");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("utf8");
                                    }
                                
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("utf8 cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUtf8));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    if (localOptOut==null){

                                            java.lang.String namespace2 = "urn:ws57.responsys";

                                        if (! namespace2.equals("")) {
                                            java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

                                            if (prefix2 == null) {
                                                prefix2 = generatePrefix(namespace2);

                                                xmlWriter.writeStartElement(prefix2,"optOut", namespace2);
                                                xmlWriter.writeNamespace(prefix2, namespace2);
                                                xmlWriter.setPrefix(prefix2, namespace2);

                                            } else {
                                                xmlWriter.writeStartElement(namespace2,"optOut");
                                            }

                                        } else {
                                            xmlWriter.writeStartElement("optOut");
                                        }


                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localOptOut.serialize(new javax.xml.namespace.QName("urn:ws57.responsys","optOut"),
                                        factory,xmlWriter);
                                    }
                                
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"fromEmailAddress", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"fromEmailAddress");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("fromEmailAddress");
                                    }
                                

                                          if (localFromEmailAddress==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFromEmailAddress);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"replyToEmailAddress", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"replyToEmailAddress");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("replyToEmailAddress");
                                    }
                                

                                          if (localReplyToEmailAddress==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localReplyToEmailAddress);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"marketingStrategy", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"marketingStrategy");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("marketingStrategy");
                                    }
                                

                                          if (localMarketingStrategy==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMarketingStrategy);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"marketingProgram", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"marketingProgram");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("marketingProgram");
                                    }
                                

                                          if (localMarketingProgram==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMarketingProgram);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"interactCampaignID", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"interactCampaignID");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("interactCampaignID");
                                    }
                                

                                          if (localInteractCampaignID==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localInteractCampaignID);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"externalCampaignID", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"externalCampaignID");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("externalCampaignID");
                                    }
                                

                                          if (localExternalCampaignID==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localExternalCampaignID);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    if (localTestDistributionList==null){

                                            java.lang.String namespace2 = "urn:ws57.responsys";

                                        if (! namespace2.equals("")) {
                                            java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

                                            if (prefix2 == null) {
                                                prefix2 = generatePrefix(namespace2);

                                                xmlWriter.writeStartElement(prefix2,"testDistributionList", namespace2);
                                                xmlWriter.writeNamespace(prefix2, namespace2);
                                                xmlWriter.setPrefix(prefix2, namespace2);

                                            } else {
                                                xmlWriter.writeStartElement(namespace2,"testDistributionList");
                                            }

                                        } else {
                                            xmlWriter.writeStartElement("testDistributionList");
                                        }


                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localTestDistributionList.serialize(new javax.xml.namespace.QName("urn:ws57.responsys","testDistributionList"),
                                        factory,xmlWriter);
                                    }
                                
                                    if (localDistributionList==null){

                                            java.lang.String namespace2 = "urn:ws57.responsys";

                                        if (! namespace2.equals("")) {
                                            java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

                                            if (prefix2 == null) {
                                                prefix2 = generatePrefix(namespace2);

                                                xmlWriter.writeStartElement(prefix2,"distributionList", namespace2);
                                                xmlWriter.writeNamespace(prefix2, namespace2);
                                                xmlWriter.setPrefix(prefix2, namespace2);

                                            } else {
                                                xmlWriter.writeStartElement(namespace2,"distributionList");
                                            }

                                        } else {
                                            xmlWriter.writeStartElement("distributionList");
                                        }


                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localDistributionList.serialize(new javax.xml.namespace.QName("urn:ws57.responsys","distributionList"),
                                        factory,xmlWriter);
                                    }
                                
                                    if (localSeedList==null){

                                            java.lang.String namespace2 = "urn:ws57.responsys";

                                        if (! namespace2.equals("")) {
                                            java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

                                            if (prefix2 == null) {
                                                prefix2 = generatePrefix(namespace2);

                                                xmlWriter.writeStartElement(prefix2,"seedList", namespace2);
                                                xmlWriter.writeNamespace(prefix2, namespace2);
                                                xmlWriter.setPrefix(prefix2, namespace2);

                                            } else {
                                                xmlWriter.writeStartElement(namespace2,"seedList");
                                            }

                                        } else {
                                            xmlWriter.writeStartElement("seedList");
                                        }


                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localSeedList.serialize(new javax.xml.namespace.QName("urn:ws57.responsys","seedList"),
                                        factory,xmlWriter);
                                    }
                                 if (localSuppressDataSourceTracker){
                                       if (localSuppressDataSource!=null){
                                            for (int i = 0;i < localSuppressDataSource.length;i++){
                                                if (localSuppressDataSource[i] != null){
                                                 localSuppressDataSource[i].serialize(new javax.xml.namespace.QName("urn:ws57.responsys","suppressDataSource"),
                                                           factory,xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("suppressDataSource cannot be null!!");
                                        
                                    }
                                 } if (localSupplementalDataSourceTracker){
                                       if (localSupplementalDataSource!=null){
                                            for (int i = 0;i < localSupplementalDataSource.length;i++){
                                                if (localSupplementalDataSource[i] != null){
                                                 localSupplementalDataSource[i].serialize(new javax.xml.namespace.QName("urn:ws57.responsys","supplementalDataSource"),
                                                           factory,xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("supplementalDataSource cannot be null!!");
                                        
                                    }
                                 }
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"skipDuplicates", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"skipDuplicates");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("skipDuplicates");
                                    }
                                
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("skipDuplicates cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSkipDuplicates));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"autoSense", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"autoSense");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("autoSense");
                                    }
                                
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("autoSense cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAutoSense));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"useAccountWide", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"useAccountWide");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("useAccountWide");
                                    }
                                
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("useAccountWide cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUseAccountWide));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    if (localAcknowledgmentDocument==null){

                                            java.lang.String namespace2 = "urn:ws57.responsys";

                                        if (! namespace2.equals("")) {
                                            java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

                                            if (prefix2 == null) {
                                                prefix2 = generatePrefix(namespace2);

                                                xmlWriter.writeStartElement(prefix2,"acknowledgmentDocument", namespace2);
                                                xmlWriter.writeNamespace(prefix2, namespace2);
                                                xmlWriter.setPrefix(prefix2, namespace2);

                                            } else {
                                                xmlWriter.writeStartElement(namespace2,"acknowledgmentDocument");
                                            }

                                        } else {
                                            xmlWriter.writeStartElement("acknowledgmentDocument");
                                        }


                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localAcknowledgmentDocument.serialize(new javax.xml.namespace.QName("urn:ws57.responsys","acknowledgmentDocument"),
                                        factory,xmlWriter);
                                    }
                                
                                    namespace = "urn:ws57.responsys";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"acknowledgmentURL", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"acknowledgmentURL");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("acknowledgmentURL");
                                    }
                                

                                          if (localAcknowledgmentURL==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAcknowledgmentURL);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    if (localFollowUp==null){

                                            java.lang.String namespace2 = "urn:ws57.responsys";

                                        if (! namespace2.equals("")) {
                                            java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

                                            if (prefix2 == null) {
                                                prefix2 = generatePrefix(namespace2);

                                                xmlWriter.writeStartElement(prefix2,"followUp", namespace2);
                                                xmlWriter.writeNamespace(prefix2, namespace2);
                                                xmlWriter.setPrefix(prefix2, namespace2);

                                            } else {
                                                xmlWriter.writeStartElement(namespace2,"followUp");
                                            }

                                        } else {
                                            xmlWriter.writeStartElement("followUp");
                                        }


                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localFollowUp.serialize(new javax.xml.namespace.QName("urn:ws57.responsys","followUp"),
                                        factory,xmlWriter);
                                    }
                                
                                    if (localResponseDB==null){

                                            java.lang.String namespace2 = "urn:ws57.responsys";

                                        if (! namespace2.equals("")) {
                                            java.lang.String prefix2 = xmlWriter.getPrefix(namespace2);

                                            if (prefix2 == null) {
                                                prefix2 = generatePrefix(namespace2);

                                                xmlWriter.writeStartElement(prefix2,"responseDB", namespace2);
                                                xmlWriter.writeNamespace(prefix2, namespace2);
                                                xmlWriter.setPrefix(prefix2, namespace2);

                                            } else {
                                                xmlWriter.writeStartElement(namespace2,"responseDB");
                                            }

                                        } else {
                                            xmlWriter.writeStartElement("responseDB");
                                        }


                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localResponseDB.serialize(new javax.xml.namespace.QName("urn:ws57.responsys","responseDB"),
                                        factory,xmlWriter);
                                    }
                                
                    xmlWriter.writeEndElement();
               

        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                      java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                      java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
                java.lang.String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "campaignName"));
                                 
                                         elementList.add(localCampaignName==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCampaignName));
                                    
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "displayName"));
                                 
                                         elementList.add(localDisplayName==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDisplayName));
                                    
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "subject"));
                                 
                                         elementList.add(localSubject==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSubject));
                                    
                            elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "purpose"));
                            
                            
                                    elementList.add(localPurpose==null?null:
                                    localPurpose);
                                 if (localAttachmentTracker){
                             if (localAttachment!=null) {
                                 for (int i = 0;i < localAttachment.length;i++){

                                    if (localAttachment[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                          "attachment"));
                                         elementList.add(localAttachment[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("attachment cannot be null!!");
                                    
                             }

                        }
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "autoSenseAol"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAutoSenseAol));
                            
                            elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "clickThru"));
                            
                            
                                    elementList.add(localClickThru==null?null:
                                    localClickThru);
                                
                            elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "clickTrack"));
                            
                            
                                    elementList.add(localClickTrack==null?null:
                                    localClickTrack);
                                
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "conversionTrack"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localConversionTrack));
                             if (localDocumentTracker){
                             if (localDocument!=null) {
                                 for (int i = 0;i < localDocument.length;i++){

                                    if (localDocument[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                          "document"));
                                         elementList.add(localDocument[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("document cannot be null!!");
                                    
                             }

                        }
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "htmlToAol"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHtmlToAol));
                            
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "inlineMedia"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInlineMedia));
                             if (localLogTableTracker){
                             if (localLogTable!=null) {
                                 for (int i = 0;i < localLogTable.length;i++){

                                    if (localLogTable[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                          "logTable"));
                                         elementList.add(localLogTable[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("logTable cannot be null!!");
                                    
                             }

                        }
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "openSense"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOpenSense));
                            
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "parentFolder"));
                                 
                                         elementList.add(localParentFolder==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localParentFolder));
                                    
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "skipMultiPart"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSkipMultiPart));
                            
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "testEmailAddress"));
                                 
                                         elementList.add(localTestEmailAddress==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTestEmailAddress));
                                    
                            elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "locale"));
                            
                            
                                    elementList.add(localLocale==null?null:
                                    localLocale);
                                 if (localCampaignVariableTracker){
                             if (localCampaignVariable!=null) {
                                 for (int i = 0;i < localCampaignVariable.length;i++){

                                    if (localCampaignVariable[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                          "campaignVariable"));
                                         elementList.add(localCampaignVariable[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("campaignVariable cannot be null!!");
                                    
                             }

                        }
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "utf8"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUtf8));
                            
                            elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "optOut"));
                            
                            
                                    elementList.add(localOptOut==null?null:
                                    localOptOut);
                                
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "fromEmailAddress"));
                                 
                                         elementList.add(localFromEmailAddress==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFromEmailAddress));
                                    
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "replyToEmailAddress"));
                                 
                                         elementList.add(localReplyToEmailAddress==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReplyToEmailAddress));
                                    
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "marketingStrategy"));
                                 
                                         elementList.add(localMarketingStrategy==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMarketingStrategy));
                                    
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "marketingProgram"));
                                 
                                         elementList.add(localMarketingProgram==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMarketingProgram));
                                    
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "interactCampaignID"));
                                 
                                         elementList.add(localInteractCampaignID==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInteractCampaignID));
                                    
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "externalCampaignID"));
                                 
                                         elementList.add(localExternalCampaignID==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExternalCampaignID));
                                    
                            elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "testDistributionList"));
                            
                            
                                    elementList.add(localTestDistributionList==null?null:
                                    localTestDistributionList);
                                
                            elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "distributionList"));
                            
                            
                                    elementList.add(localDistributionList==null?null:
                                    localDistributionList);
                                
                            elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "seedList"));
                            
                            
                                    elementList.add(localSeedList==null?null:
                                    localSeedList);
                                 if (localSuppressDataSourceTracker){
                             if (localSuppressDataSource!=null) {
                                 for (int i = 0;i < localSuppressDataSource.length;i++){

                                    if (localSuppressDataSource[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                          "suppressDataSource"));
                                         elementList.add(localSuppressDataSource[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("suppressDataSource cannot be null!!");
                                    
                             }

                        } if (localSupplementalDataSourceTracker){
                             if (localSupplementalDataSource!=null) {
                                 for (int i = 0;i < localSupplementalDataSource.length;i++){

                                    if (localSupplementalDataSource[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                          "supplementalDataSource"));
                                         elementList.add(localSupplementalDataSource[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("supplementalDataSource cannot be null!!");
                                    
                             }

                        }
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "skipDuplicates"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSkipDuplicates));
                            
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "autoSense"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAutoSense));
                            
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "useAccountWide"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUseAccountWide));
                            
                            elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "acknowledgmentDocument"));
                            
                            
                                    elementList.add(localAcknowledgmentDocument==null?null:
                                    localAcknowledgmentDocument);
                                
                                      elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "acknowledgmentURL"));
                                 
                                         elementList.add(localAcknowledgmentURL==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAcknowledgmentURL));
                                    
                            elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "followUp"));
                            
                            
                                    elementList.add(localFollowUp==null?null:
                                    localFollowUp);
                                
                            elementList.add(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                                      "responseDB"));
                            
                            
                                    elementList.add(localResponseDB==null?null:
                                    localResponseDB);
                                

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static CampaignProperties parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            CampaignProperties object =
                new CampaignProperties();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"CampaignProperties".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (CampaignProperties)responsys.ws57.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                 
                    
                    reader.next();
                
                        java.util.ArrayList list5 = new java.util.ArrayList();
                    
                        java.util.ArrayList list10 = new java.util.ArrayList();
                    
                        java.util.ArrayList list13 = new java.util.ArrayList();
                    
                        java.util.ArrayList list19 = new java.util.ArrayList();
                    
                        java.util.ArrayList list31 = new java.util.ArrayList();
                    
                        java.util.ArrayList list32 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","campaignName").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCampaignName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","displayName").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDisplayName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","subject").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSubject(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","purpose").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setPurpose(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setPurpose(responsys.ws57.CampaignPurpose.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","attachment").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list5.add(responsys.ws57.InteractObject.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone5 = false;
                                                        while(!loopDone5){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone5 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("urn:ws57.responsys","attachment").equals(reader.getName())){
                                                                    list5.add(responsys.ws57.InteractObject.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone5 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setAttachment((responsys.ws57.InteractObject[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                responsys.ws57.InteractObject.class,
                                                                list5));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","autoSenseAol").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAutoSenseAol(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","clickThru").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setClickThru(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setClickThru(responsys.ws57.ClickThru.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","clickTrack").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setClickTrack(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setClickTrack(responsys.ws57.InteractObject.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","conversionTrack").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setConversionTrack(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","document").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list10.add(responsys.ws57.Document.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone10 = false;
                                                        while(!loopDone10){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone10 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("urn:ws57.responsys","document").equals(reader.getName())){
                                                                    list10.add(responsys.ws57.Document.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone10 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setDocument((responsys.ws57.Document[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                responsys.ws57.Document.class,
                                                                list10));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","htmlToAol").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHtmlToAol(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","inlineMedia").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setInlineMedia(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","logTable").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list13.add(responsys.ws57.LogTable.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone13 = false;
                                                        while(!loopDone13){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone13 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("urn:ws57.responsys","logTable").equals(reader.getName())){
                                                                    list13.add(responsys.ws57.LogTable.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone13 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setLogTable((responsys.ws57.LogTable[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                responsys.ws57.LogTable.class,
                                                                list13));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","openSense").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOpenSense(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","parentFolder").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setParentFolder(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","skipMultiPart").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSkipMultiPart(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","testEmailAddress").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTestEmailAddress(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","locale").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setLocale(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setLocale(responsys.ws57.RecipientLocale.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","campaignVariable").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list19.add(responsys.ws57.CampaignVariable.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone19 = false;
                                                        while(!loopDone19){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone19 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("urn:ws57.responsys","campaignVariable").equals(reader.getName())){
                                                                    list19.add(responsys.ws57.CampaignVariable.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone19 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setCampaignVariable((responsys.ws57.CampaignVariable[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                responsys.ws57.CampaignVariable.class,
                                                                list19));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","utf8").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUtf8(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","optOut").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setOptOut(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setOptOut(responsys.ws57.OptOut.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","fromEmailAddress").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFromEmailAddress(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","replyToEmailAddress").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setReplyToEmailAddress(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","marketingStrategy").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMarketingStrategy(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","marketingProgram").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMarketingProgram(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","interactCampaignID").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setInteractCampaignID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","externalCampaignID").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setExternalCampaignID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","testDistributionList").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setTestDistributionList(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setTestDistributionList(responsys.ws57.InteractObject.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","distributionList").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setDistributionList(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setDistributionList(responsys.ws57.InteractObject.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","seedList").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setSeedList(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setSeedList(responsys.ws57.InteractObject.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","suppressDataSource").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list31.add(responsys.ws57.InteractObject.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone31 = false;
                                                        while(!loopDone31){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone31 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("urn:ws57.responsys","suppressDataSource").equals(reader.getName())){
                                                                    list31.add(responsys.ws57.InteractObject.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone31 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setSuppressDataSource((responsys.ws57.InteractObject[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                responsys.ws57.InteractObject.class,
                                                                list31));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","supplementalDataSource").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list32.add(responsys.ws57.InteractObject.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone32 = false;
                                                        while(!loopDone32){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone32 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("urn:ws57.responsys","supplementalDataSource").equals(reader.getName())){
                                                                    list32.add(responsys.ws57.InteractObject.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone32 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setSupplementalDataSource((responsys.ws57.InteractObject[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                responsys.ws57.InteractObject.class,
                                                                list32));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","skipDuplicates").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSkipDuplicates(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","autoSense").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAutoSense(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","useAccountWide").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUseAccountWide(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","acknowledgmentDocument").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setAcknowledgmentDocument(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setAcknowledgmentDocument(responsys.ws57.InteractObject.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","acknowledgmentURL").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAcknowledgmentURL(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","followUp").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setFollowUp(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setFollowUp(responsys.ws57.InteractObject.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("urn:ws57.responsys","responseDB").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setResponseDB(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setResponseDB(responsys.ws57.InteractObject.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                              
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
          