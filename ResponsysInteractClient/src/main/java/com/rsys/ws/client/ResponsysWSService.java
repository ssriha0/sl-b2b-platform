

/**
 * ResponsysWSService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */

    package com.rsys.ws.client;

    /*
     *  ResponsysWSService java interface
     */

    public interface ResponsysWSService {
          

        /**
          * Auto generated method signature
          * Trigger Custom Event to 1 or more recipients.
                    * @param triggerCustomEvent
                
                    * @param sessionHeader
                
             * @throws com.rsys.ws.client.DuplicateApiRequestFault : 
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.CustomEventFault : 
         */

         
                     public com.rsys.ws.TriggerCustomEventResponse triggerCustomEvent(

                        com.rsys.ws.TriggerCustomEvent triggerCustomEvent,com.rsys.ws.SessionHeader sessionHeader)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.DuplicateApiRequestFault
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.CustomEventFault;

        

        /**
          * Auto generated method signature
          * Set images to a document.
                    * @param setDocumentImages
                
                    * @param sessionHeader0
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.DocumentFault : 
         */

         
                     public com.rsys.ws.SetDocumentImagesResponse setDocumentImages(

                        com.rsys.ws.SetDocumentImages setDocumentImages,com.rsys.ws.SessionHeader sessionHeader0)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.DocumentFault;

        

        /**
          * Auto generated method signature
          * Merge And Send Triggered Message for 1 or more recipients.
                    * @param mergeTriggerSMS
                
                    * @param sessionHeader1
                
             * @throws com.rsys.ws.client.DuplicateApiRequestFault : 
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.TriggeredMessageFault : 
         */

         
                     public com.rsys.ws.MergeTriggerSMSResponse mergeTriggerSMS(

                        com.rsys.ws.MergeTriggerSMS mergeTriggerSMS,com.rsys.ws.SessionHeader sessionHeader1)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.DuplicateApiRequestFault
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.TriggeredMessageFault;

        

        /**
          * Auto generated method signature
          * Merge And Send Triggered Message for 1 or more recipients.
                    * @param haMergeTriggerSms
                
                    * @param sessionHeader2
                
             * @throws com.rsys.ws.client.DuplicateApiRequestFault : 
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.TriggeredMessageFault : 
             * @throws com.rsys.ws.client.UnrecoverableErrorFault : 
         */

         
                     public com.rsys.ws.HaMergeTriggerSmsResponse haMergeTriggerSms(

                        com.rsys.ws.HaMergeTriggerSms haMergeTriggerSms,com.rsys.ws.SessionHeader sessionHeader2)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.DuplicateApiRequestFault
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.TriggeredMessageFault
          ,com.rsys.ws.client.UnrecoverableErrorFault;

        

        /**
          * Auto generated method signature
          * Merge data into a list returning the recipient ids.
                    * @param mergeListMembersRIID
                
                    * @param sessionHeader3
                
             * @throws com.rsys.ws.client.ListFault : 
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
         */

         
                     public com.rsys.ws.MergeListMembersRIIDResponse mergeListMembersRIID(

                        com.rsys.ws.MergeListMembersRIID mergeListMembersRIID,com.rsys.ws.SessionHeader sessionHeader3)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.ListFault
          ,com.rsys.ws.client.UnexpectedErrorFault;

        

        /**
          * Auto generated method signature
          * Creates an empty table with primary keys.
                    * @param createTableWithPK
                
                    * @param sessionHeader4
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.TableFault : 
         */

         
                     public com.rsys.ws.CreateTableWithPKResponse createTableWithPK(

                        com.rsys.ws.CreateTableWithPK createTableWithPK,com.rsys.ws.SessionHeader sessionHeader4)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.TableFault;

        

        /**
          * Auto generated method signature
          * Send Triggered Message to 1 or more recipients.
                    * @param triggerCampaignMessage
                
                    * @param sessionHeader5
                
             * @throws com.rsys.ws.client.DuplicateApiRequestFault : 
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.TriggeredMessageFault : 
         */

         
                     public com.rsys.ws.TriggerCampaignMessageResponse triggerCampaignMessage(

                        com.rsys.ws.TriggerCampaignMessage triggerCampaignMessage,com.rsys.ws.SessionHeader sessionHeader5)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.DuplicateApiRequestFault
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.TriggeredMessageFault;

        

        /**
          * Auto generated method signature
          * Deletes recipients from a list.
                    * @param deleteListMembers
                
                    * @param sessionHeader6
                
             * @throws com.rsys.ws.client.ListFault : 
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
         */

         
                     public com.rsys.ws.DeleteListMembersResponse deleteListMembers(

                        com.rsys.ws.DeleteListMembers deleteListMembers,com.rsys.ws.SessionHeader sessionHeader6)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.ListFault
          ,com.rsys.ws.client.UnexpectedErrorFault;

        

        /**
          * Auto generated method signature
          * Merge And Send Triggered Message for 1 or more recipients.
                    * @param mergeTriggerEmail
                
                    * @param sessionHeader7
                
             * @throws com.rsys.ws.client.DuplicateApiRequestFault : 
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.TriggeredMessageFault : 
         */

         
                     public com.rsys.ws.MergeTriggerEmailResponse mergeTriggerEmail(

                        com.rsys.ws.MergeTriggerEmail mergeTriggerEmail,com.rsys.ws.SessionHeader sessionHeader7)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.DuplicateApiRequestFault
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.TriggeredMessageFault;

        

        /**
          * Auto generated method signature
          * Deletes an existing folder.
                    * @param deleteFolder
                
                    * @param sessionHeader8
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.FolderFault : 
         */

         
                     public com.rsys.ws.DeleteFolderResponse deleteFolder(

                        com.rsys.ws.DeleteFolder deleteFolder,com.rsys.ws.SessionHeader sessionHeader8)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.FolderFault;

        

        /**
          * Auto generated method signature
          * Get content of a document.
                    * @param getDocumentContent
                
                    * @param sessionHeader9
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.DocumentFault : 
         */

         
                     public com.rsys.ws.GetDocumentContentResponse getDocumentContent(

                        com.rsys.ws.GetDocumentContent getDocumentContent,com.rsys.ws.SessionHeader sessionHeader9)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.DocumentFault;

        

        /**
          * Auto generated method signature
          * Creates a new folder.
                    * @param createFolder
                
                    * @param sessionHeader10
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.FolderFault : 
         */

         
                     public com.rsys.ws.CreateFolderResponse createFolder(

                        com.rsys.ws.CreateFolder createFolder,com.rsys.ws.SessionHeader sessionHeader10)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.FolderFault;

        

        /**
          * Auto generated method signature
          * Deletes records from a table.
                    * @param deleteTableRecords
                
                    * @param sessionHeader11
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.TableFault : 
         */

         
                     public com.rsys.ws.DeleteTableRecordsResponse deleteTableRecords(

                        com.rsys.ws.DeleteTableRecords deleteTableRecords,com.rsys.ws.SessionHeader sessionHeader11)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.TableFault;

        

        /**
          * Auto generated method signature
          * Merge data into a list extension returning the recipient ids.
                    * @param mergeIntoProfileExtension
                
                    * @param sessionHeader12
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.ListExtensionFault : 
         */

         
                     public com.rsys.ws.MergeIntoProfileExtensionResponse mergeIntoProfileExtension(

                        com.rsys.ws.MergeIntoProfileExtension mergeIntoProfileExtension,com.rsys.ws.SessionHeader sessionHeader12)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.ListExtensionFault;

        

        /**
          * Auto generated method signature
          * Check if folder exists in the Content Library.
                    * @param doesContentLibraryFolderExist
                
                    * @param sessionHeader13
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.FolderFault : 
         */

         
                     public com.rsys.ws.DoesContentLibraryFolderExistResponse doesContentLibraryFolderExist(

                        com.rsys.ws.DoesContentLibraryFolderExist doesContentLibraryFolderExist,com.rsys.ws.SessionHeader sessionHeader13)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.FolderFault;

        

        /**
          * Auto generated method signature
          * Truncate data in existing table.
                    * @param truncateTable
                
                    * @param sessionHeader14
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.TableFault : 
         */

         
                     public com.rsys.ws.TruncateTableResponse truncateTable(

                        com.rsys.ws.TruncateTable truncateTable,com.rsys.ws.SessionHeader sessionHeader14)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.TableFault;

        

        /**
          * Auto generated method signature
          * Retrieves records from table.
                    * @param retrieveTableRecords
                
                    * @param sessionHeader15
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.TableFault : 
         */

         
                     public com.rsys.ws.RetrieveTableRecordsResponse retrieveTableRecords(

                        com.rsys.ws.RetrieveTableRecords retrieveTableRecords,com.rsys.ws.SessionHeader sessionHeader15)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.TableFault;

        

        /**
          * Auto generated method signature
          * Set content to a document.
                    * @param setDocumentContent
                
                    * @param sessionHeader16
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.DocumentFault : 
         */

         
                     public com.rsys.ws.SetDocumentContentResponse setDocumentContent(

                        com.rsys.ws.SetDocumentContent setDocumentContent,com.rsys.ws.SessionHeader sessionHeader16)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.DocumentFault;

        

        /**
          * Auto generated method signature
          * Deletes an existing table.
                    * @param deleteTable
                
                    * @param sessionHeader17
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.TableFault : 
         */

         
                     public com.rsys.ws.DeleteTableResponse deleteTable(

                        com.rsys.ws.DeleteTable deleteTable,com.rsys.ws.SessionHeader sessionHeader17)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.TableFault;

        

        /**
          * Auto generated method signature
          * Merge data into a list.
                    * @param mergeListMembers
                
                    * @param sessionHeader18
                
             * @throws com.rsys.ws.client.ListFault : 
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
         */

         
                     public com.rsys.ws.MergeListMembersResponse mergeListMembers(

                        com.rsys.ws.MergeListMembers mergeListMembers,com.rsys.ws.SessionHeader sessionHeader18)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.ListFault
          ,com.rsys.ws.client.UnexpectedErrorFault;

        

        /**
          * Auto generated method signature
          * Delete asset in the Content Library.
                    * @param deleteContentLibraryItem
                
                    * @param sessionHeader19
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.DocumentFault : 
         */

         
                     public com.rsys.ws.DeleteContentLibraryItemResponse deleteContentLibraryItem(

                        com.rsys.ws.DeleteContentLibraryItem deleteContentLibraryItem,com.rsys.ws.SessionHeader sessionHeader19)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.DocumentFault;

        

        /**
          * Auto generated method signature
          * Deletes recipients from a profile extension.
                    * @param deleteProfileExtensionMembers
                
                    * @param sessionHeader20
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.ListExtensionFault : 
         */

         
                     public com.rsys.ws.DeleteProfileExtensionMembersResponse deleteProfileExtensionMembers(

                        com.rsys.ws.DeleteProfileExtensionMembers deleteProfileExtensionMembers,com.rsys.ws.SessionHeader sessionHeader20)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.ListExtensionFault;

        

        /**
          * Auto generated method signature
          * Get asset in the Content Library.
                    * @param getContentLibraryItem
                
                    * @param sessionHeader21
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.DocumentFault : 
         */

         
                     public com.rsys.ws.GetContentLibraryItemResponse getContentLibraryItem(

                        com.rsys.ws.GetContentLibraryItem getContentLibraryItem,com.rsys.ws.SessionHeader sessionHeader21)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.DocumentFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param createProfileExtensionTable
                
                    * @param sessionHeader22
                
             * @throws com.rsys.ws.client.ListFault : 
         */

         
                     public com.rsys.ws.CreateProfileExtensionTableResponse createProfileExtensionTable(

                        com.rsys.ws.CreateProfileExtensionTable createProfileExtensionTable,com.rsys.ws.SessionHeader sessionHeader22)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.ListFault;

        

        /**
          * Auto generated method signature
          * Creates an empty table.
                    * @param createTable
                
                    * @param sessionHeader23
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.TableFault : 
         */

         
                     public com.rsys.ws.CreateTableResponse createTable(

                        com.rsys.ws.CreateTable createTable,com.rsys.ws.SessionHeader sessionHeader23)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.TableFault;

        

        /**
          * Auto generated method signature
          * Login to the Responsys Web Services API using Certificate.
                    * @param loginWithCertificate
                
                    * @param authSessionHeader
                
             * @throws com.rsys.ws.client.AccountFault : 
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
         */

         
                     public com.rsys.ws.LoginWithCertificateResponse loginWithCertificate(

                        com.rsys.ws.LoginWithCertificate loginWithCertificate,com.rsys.ws.AuthSessionHeader authSessionHeader)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.AccountFault
          ,com.rsys.ws.client.UnexpectedErrorFault;

        

        /**
          * Auto generated method signature
          * Merge data into a table.
                    * @param mergeTableRecords
                
                    * @param sessionHeader24
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.TableFault : 
         */

         
                     public com.rsys.ws.MergeTableRecordsResponse mergeTableRecords(

                        com.rsys.ws.MergeTableRecords mergeTableRecords,com.rsys.ws.SessionHeader sessionHeader24)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.TableFault;

        

        /**
          * Auto generated method signature
          * Gets the launch info given a launch Id.
                    * @param getLaunchStatus
                
                    * @param sessionHeader25
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.CampaignFault : 
         */

         
                     public com.rsys.ws.GetLaunchStatusResponse getLaunchStatus(

                        com.rsys.ws.GetLaunchStatus getLaunchStatus,com.rsys.ws.SessionHeader sessionHeader25)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.CampaignFault;

        

        /**
          * Auto generated method signature
          * Create asset in the Content Library.
                    * @param createContentLibraryItem
                
                    * @param sessionHeader26
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.DocumentFault : 
         */

         
                     public com.rsys.ws.CreateContentLibraryItemResponse createContentLibraryItem(

                        com.rsys.ws.CreateContentLibraryItem createContentLibraryItem,com.rsys.ws.SessionHeader sessionHeader26)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.DocumentFault;

        

        /**
          * Auto generated method signature
          * Deletes a document.
                    * @param deleteDocument
                
                    * @param sessionHeader27
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.DocumentFault : 
         */

         
                     public com.rsys.ws.DeleteDocumentResponse deleteDocument(

                        com.rsys.ws.DeleteDocument deleteDocument,com.rsys.ws.SessionHeader sessionHeader27)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.DocumentFault;

        

        /**
          * Auto generated method signature
          * Get images from a document.
                    * @param getDocumentImages
                
                    * @param sessionHeader28
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.DocumentFault : 
         */

         
                     public com.rsys.ws.GetDocumentImagesResponse getDocumentImages(

                        com.rsys.ws.GetDocumentImages getDocumentImages,com.rsys.ws.SessionHeader sessionHeader28)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.DocumentFault;

        

        /**
          * Auto generated method signature
          * Merge data into a table using primary keys.
                    * @param mergeTableRecordsWithPK
                
                    * @param sessionHeader29
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.TableFault : 
         */

         
                     public com.rsys.ws.MergeTableRecordsWithPKResponse mergeTableRecordsWithPK(

                        com.rsys.ws.MergeTableRecordsWithPK mergeTableRecordsWithPK,com.rsys.ws.SessionHeader sessionHeader29)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.TableFault;

        

        /**
          * Auto generated method signature
          * Creates a document.
                    * @param createDocument
                
                    * @param sessionHeader30
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.DocumentFault : 
         */

         
                     public com.rsys.ws.CreateDocumentResponse createDocument(

                        com.rsys.ws.CreateDocument createDocument,com.rsys.ws.SessionHeader sessionHeader30)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.DocumentFault;

        

        /**
          * Auto generated method signature
          * Auhenticate the server.
                    * @param authenticateServer
                
             * @throws com.rsys.ws.client.AccountFault : 
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
         */

         
                     public com.rsys.ws.AuthenticateServerResponse authenticateServer(

                        com.rsys.ws.AuthenticateServer authenticateServer)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.AccountFault
          ,com.rsys.ws.client.UnexpectedErrorFault;

        

        /**
          * Auto generated method signature
          * Create folder in the Content Library.
                    * @param createContentLibraryFolder
                
                    * @param sessionHeader31
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.FolderFault : 
         */

         
                     public com.rsys.ws.CreateContentLibraryFolderResponse createContentLibraryFolder(

                        com.rsys.ws.CreateContentLibraryFolder createContentLibraryFolder,com.rsys.ws.SessionHeader sessionHeader31)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.FolderFault;

        

        /**
          * Auto generated method signature
          * Retrieves records from a list extension.
                    * @param retrieveProfileExtensionRecords
                
                    * @param sessionHeader32
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.ListExtensionFault : 
         */

         
                     public com.rsys.ws.RetrieveProfileExtensionRecordsResponse retrieveProfileExtensionRecords(

                        com.rsys.ws.RetrieveProfileExtensionRecords retrieveProfileExtensionRecords,com.rsys.ws.SessionHeader sessionHeader32)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.ListExtensionFault;

        

        /**
          * Auto generated method signature
          * Retrieves recipients from a list.
                    * @param retrieveListMembers
                
                    * @param sessionHeader33
                
             * @throws com.rsys.ws.client.ListFault : 
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
         */

         
                     public com.rsys.ws.RetrieveListMembersResponse retrieveListMembers(

                        com.rsys.ws.RetrieveListMembers retrieveListMembers,com.rsys.ws.SessionHeader sessionHeader33)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.ListFault
          ,com.rsys.ws.client.UnexpectedErrorFault;

        

        /**
          * Auto generated method signature
          * Delete folder in the Content Library.
                    * @param deleteContentLibraryFolder
                
                    * @param sessionHeader34
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.FolderFault : 
         */

         
                     public com.rsys.ws.DeleteContentLibraryFolderResponse deleteContentLibraryFolder(

                        com.rsys.ws.DeleteContentLibraryFolder deleteContentLibraryFolder,com.rsys.ws.SessionHeader sessionHeader34)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.FolderFault;

        

        /**
          * Auto generated method signature
          * Launch a Campaign Immediately.
                    * @param launchCampaign
                
                    * @param sessionHeader35
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.CampaignFault : 
         */

         
                     public com.rsys.ws.LaunchCampaignResponse launchCampaign(

                        com.rsys.ws.LaunchCampaign launchCampaign,com.rsys.ws.SessionHeader sessionHeader35)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.CampaignFault;

        

        /**
          * Auto generated method signature
          * List folders in the Responsys account.
                    * @param listFolders
                
                    * @param sessionHeader36
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
         */

         
                     public com.rsys.ws.ListFoldersResponse listFolders(

                        com.rsys.ws.ListFolders listFolders,com.rsys.ws.SessionHeader sessionHeader36)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault;

        

        /**
          * Auto generated method signature
          * Login to the Responsys Web Services API.
                    * @param login
                
             * @throws com.rsys.ws.client.AccountFault : 
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
         */

         
                     public com.rsys.ws.LoginResponse login(

                        com.rsys.ws.Login login)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.AccountFault
          ,com.rsys.ws.client.UnexpectedErrorFault;

        

        /**
          * Auto generated method signature
          * Update asset in the Content Library.
                    * @param setContentLibraryItem
                
                    * @param sessionHeader37
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.DocumentFault : 
         */

         
                     public com.rsys.ws.SetContentLibraryItemResponse setContentLibraryItem(

                        com.rsys.ws.SetContentLibraryItem setContentLibraryItem,com.rsys.ws.SessionHeader sessionHeader37)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.DocumentFault;

        

        /**
          * Auto generated method signature
          * List folders in the Content Library.
                    * @param listContentLibraryFolders
                
                    * @param sessionHeader38
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.FolderFault : 
         */

         
                     public com.rsys.ws.ListContentLibraryFoldersResponse listContentLibraryFolders(

                        com.rsys.ws.ListContentLibraryFolders listContentLibraryFolders,com.rsys.ws.SessionHeader sessionHeader38)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.FolderFault;

        

        /**
          * Auto generated method signature
          * Schedule a Campaign Launch.
                    * @param scheduleCampaignLaunch
                
                    * @param sessionHeader39
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.CampaignFault : 
         */

         
                     public com.rsys.ws.ScheduleCampaignLaunchResponse scheduleCampaignLaunch(

                        com.rsys.ws.ScheduleCampaignLaunch scheduleCampaignLaunch,com.rsys.ws.SessionHeader sessionHeader39)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.CampaignFault;

        

        /**
          * Auto generated method signature
          * Logout of the Responsys Web Services API.
                    * @param logout
                
                    * @param sessionHeader40
                
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
         */

         
                     public com.rsys.ws.LogoutResponse logout(

                        com.rsys.ws.Logout logout,com.rsys.ws.SessionHeader sessionHeader40)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.UnexpectedErrorFault;

        

        /**
          * Auto generated method signature
          * Merge And Send Triggered Message for 1 or more recipients.
                    * @param haMergeTriggerEmail
                
                    * @param sessionHeader41
                
             * @throws com.rsys.ws.client.DuplicateApiRequestFault : 
             * @throws com.rsys.ws.client.UnexpectedErrorFault : 
             * @throws com.rsys.ws.client.TriggeredMessageFault : 
             * @throws com.rsys.ws.client.UnrecoverableErrorFault : 
         */

         
                     public com.rsys.ws.HaMergeTriggerEmailResponse haMergeTriggerEmail(

                        com.rsys.ws.HaMergeTriggerEmail haMergeTriggerEmail,com.rsys.ws.SessionHeader sessionHeader41)
                        throws java.rmi.RemoteException
             
          ,com.rsys.ws.client.DuplicateApiRequestFault
          ,com.rsys.ws.client.UnexpectedErrorFault
          ,com.rsys.ws.client.TriggeredMessageFault
          ,com.rsys.ws.client.UnrecoverableErrorFault;

        

        
       //
       }
    