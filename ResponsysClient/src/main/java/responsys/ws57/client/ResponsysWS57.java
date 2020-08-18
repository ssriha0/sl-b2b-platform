

/**
 * ResponsysWS57.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

    package responsys.ws57.client;

    /*
     *  ResponsysWS57 java interface
     */

    public interface ResponsysWS57 {
          

        /**
          * Auto generated method signature
          * 
                    * @param login
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.LoginResponse login(

                        responsys.ws57.Login login)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param importFolder
                
                    * @param sessionHeader
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.ImportFolderResponse importFolder(

                        responsys.ws57.ImportFolder importFolder,responsys.ws57.SessionHeader sessionHeader)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param getServerTimestamp
                
                    * @param sessionHeader0
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.GetServerTimestampResponse getServerTimestamp(

                        responsys.ws57.GetServerTimestamp getServerTimestamp,responsys.ws57.SessionHeader sessionHeader0)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param purgeDataSource
                
                    * @param sessionHeader1
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.PurgeDataSourceResponse purgeDataSource(

                        responsys.ws57.PurgeDataSource purgeDataSource,responsys.ws57.SessionHeader sessionHeader1)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param campaignsInDatamart
                
                    * @param sessionHeader2
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.CampaignsInDatamartResponse getCampaignsInDatamart(

                        responsys.ws57.CampaignsInDatamart campaignsInDatamart,responsys.ws57.SessionHeader sessionHeader2)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param getReportOptions
                
                    * @param sessionHeader3
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.GetReportOptionsResponse getReportOptions(

                        responsys.ws57.GetReportOptions getReportOptions,responsys.ws57.SessionHeader sessionHeader3)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param copyDocument
                
                    * @param sessionHeader4
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.CopyDocumentResponse copyDocument(

                        responsys.ws57.CopyDocument copyDocument,responsys.ws57.SessionHeader sessionHeader4)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * Cancel the Pending Operation.
                    * @param cancel
                
                    * @param sessionHeader5
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.CancelResponse cancel(

                        responsys.ws57.Cancel cancel,responsys.ws57.SessionHeader sessionHeader5)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param updateDataSourceUsingMultipleColumns
                
                    * @param sessionHeader6
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.UpdateDataSourceUsingMultipleColumnsResponse updateDataSourceUsingMultipleColumns(

                        responsys.ws57.UpdateDataSourceUsingMultipleColumns updateDataSourceUsingMultipleColumns,responsys.ws57.SessionHeader sessionHeader6)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param listIndexes
                
                    * @param sessionHeader7
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.ListIndexesResponse listIndexes(

                        responsys.ws57.ListIndexes listIndexes,responsys.ws57.SessionHeader sessionHeader7)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param showDocument
                
                    * @param sessionHeader8
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.ShowDocumentResponse showDocument(

                        responsys.ws57.ShowDocument showDocument,responsys.ws57.SessionHeader sessionHeader8)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param getCampaignProperties
                
                    * @param sessionHeader9
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.GetCampaignPropertiesResponse getCampaignProperties(

                        responsys.ws57.GetCampaignProperties getCampaignProperties,responsys.ws57.SessionHeader sessionHeader9)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param logout
                
                    * @param sessionHeader10
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.LogoutResponse logout(

                        responsys.ws57.Logout logout,responsys.ws57.SessionHeader sessionHeader10)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param scrubDataSource
                
                    * @param sessionHeader11
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.ScrubDataSourceResponse scrubDataSource(

                        responsys.ws57.ScrubDataSource scrubDataSource,responsys.ws57.SessionHeader sessionHeader11)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param listFolderContents
                
                    * @param sessionHeader12
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.ListFolderContentsResponse listFolderContents(

                        responsys.ws57.ListFolderContents listFolderContents,responsys.ws57.SessionHeader sessionHeader12)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param listFolderObjects
                
                    * @param sessionHeader13
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.ListFolderObjectsResponse listFolderObjects(

                        responsys.ws57.ListFolderObjects listFolderObjects,responsys.ws57.SessionHeader sessionHeader13)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param getCampaignStatus
                
                    * @param sessionHeader14
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.GetCampaignStatusResponse getCampaignStatus(

                        responsys.ws57.GetCampaignStatus getCampaignStatus,responsys.ws57.SessionHeader sessionHeader14)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param triggerFormRules
                
                    * @param sessionHeader15
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.TriggerFormRulesResponse triggerFormRules(

                        responsys.ws57.TriggerFormRules triggerFormRules,responsys.ws57.SessionHeader sessionHeader15)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param launchCampaign
                
                    * @param sessionHeader16
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.LaunchCampaignResponse launchCampaign(

                        responsys.ws57.LaunchCampaign launchCampaign,responsys.ws57.SessionHeader sessionHeader16)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * Check the result of an on going operations using the previously returned IntermediateResult.
                    * @param checkResult
                
                    * @param sessionHeader17
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.CheckResultResponse checkResult(

                        responsys.ws57.CheckResult checkResult,responsys.ws57.SessionHeader sessionHeader17)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param createDataSource
                
                    * @param sessionHeader18
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.CreateDataSourceResponse createDataSource(

                        responsys.ws57.CreateDataSource createDataSource,responsys.ws57.SessionHeader sessionHeader18)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param launchesInDatamart
                
                    * @param sessionHeader19
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.LaunchesInDatamartResponse getLaunchesInDatamart(

                        responsys.ws57.LaunchesInDatamart launchesInDatamart,responsys.ws57.SessionHeader sessionHeader19)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param stopCampaign
                
                    * @param sessionHeader20
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.StopCampaignResponse stopCampaign(

                        responsys.ws57.StopCampaign stopCampaign,responsys.ws57.SessionHeader sessionHeader20)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param listTablesForCampaign
                
                    * @param sessionHeader21
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.ListTablesForCampaignResponse listTablesForCampaign(

                        responsys.ws57.ListTablesForCampaign listTablesForCampaign,responsys.ws57.SessionHeader sessionHeader21)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param createSQLDataSource
                
                    * @param sessionHeader22
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.CreateSQLDataSourceResponse createSQLDataSource(

                        responsys.ws57.CreateSQLDataSource createSQLDataSource,responsys.ws57.SessionHeader sessionHeader22)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param getDataSourceSchema
                
                    * @param sessionHeader23
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.GetDataSourceSchemaResponse getDataSourceSchema(

                        responsys.ws57.GetDataSourceSchema getDataSourceSchema,responsys.ws57.SessionHeader sessionHeader23)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param deleteDataSource
                
                    * @param sessionHeader24
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.DeleteDataSourceResponse deleteDataSource(

                        responsys.ws57.DeleteDataSource deleteDataSource,responsys.ws57.SessionHeader sessionHeader24)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param setCampaignProperties
                
                    * @param sessionHeader25
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.SetCampaignPropertiesResponse setCampaignProperties(

                        responsys.ws57.SetCampaignProperties setCampaignProperties,responsys.ws57.SessionHeader sessionHeader25)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param removeDocument
                
                    * @param sessionHeader26
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.RemoveDocumentResponse removeDocument(

                        responsys.ws57.RemoveDocument removeDocument,responsys.ws57.SessionHeader sessionHeader26)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param copyCampaign
                
                    * @param sessionHeader27
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.CopyCampaignResponse copyCampaign(

                        responsys.ws57.CopyCampaign copyCampaign,responsys.ws57.SessionHeader sessionHeader27)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param addIndex
                
                    * @param sessionHeader28
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.AddIndexResponse addIndex(

                        responsys.ws57.AddIndex addIndex,responsys.ws57.SessionHeader sessionHeader28)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param runLaunchReport
                
                    * @param sessionHeader29
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.RunLaunchReportResponse runLaunchReport(

                        responsys.ws57.RunLaunchReport runLaunchReport,responsys.ws57.SessionHeader sessionHeader29)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param downloadDataSourceByTimestamp
                
                    * @param sessionHeader30
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.DownloadDataSourceByTimestampResponse downloadDataSourceByTimestamp(

                        responsys.ws57.DownloadDataSourceByTimestamp downloadDataSourceByTimestamp,responsys.ws57.SessionHeader sessionHeader30)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param appendDataSource
                
                    * @param sessionHeader31
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.AppendDataSourceResponse appendDataSource(

                        responsys.ws57.AppendDataSource appendDataSource,responsys.ws57.SessionHeader sessionHeader31)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param deleteIndex
                
                    * @param sessionHeader32
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.DeleteIndexResponse deleteIndex(

                        responsys.ws57.DeleteIndex deleteIndex,responsys.ws57.SessionHeader sessionHeader32)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param deleteFolder
                
                    * @param sessionHeader33
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.DeleteFolderResponse deleteFolder(

                        responsys.ws57.DeleteFolder deleteFolder,responsys.ws57.SessionHeader sessionHeader33)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param truncateTable
                
                    * @param sessionHeader34
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.TruncateTableResponse truncateTable(

                        responsys.ws57.TruncateTable truncateTable,responsys.ws57.SessionHeader sessionHeader34)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param unScheduleCampaign
                
                    * @param sessionHeader35
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.UnScheduleCampaignResponse unScheduleCampaign(

                        responsys.ws57.UnScheduleCampaign unScheduleCampaign,responsys.ws57.SessionHeader sessionHeader35)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param getDataSourceRecordCount
                
                    * @param sessionHeader36
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.GetDataSourceRecordCountResponse getDataSourceRecordCount(

                        responsys.ws57.GetDataSourceRecordCount getDataSourceRecordCount,responsys.ws57.SessionHeader sessionHeader36)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param exportFolder
                
                    * @param sessionHeader37
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.ExportFolderResponse exportFolder(

                        responsys.ws57.ExportFolder exportFolder,responsys.ws57.SessionHeader sessionHeader37)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param updateDataSource
                
                    * @param sessionHeader38
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.UpdateDataSourceResponse updateDataSource(

                        responsys.ws57.UpdateDataSource updateDataSource,responsys.ws57.SessionHeader sessionHeader38)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param runTriggeredMessageReport
                
                    * @param sessionHeader39
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.RunTriggeredMessageReportResponse runTriggeredMessageReport(

                        responsys.ws57.RunTriggeredMessageReport runTriggeredMessageReport,responsys.ws57.SessionHeader sessionHeader39)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param getLiveReportMetrics
                
                    * @param sessionHeader40
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.LiveReportMetricsResponse getLiveReportMetrics(

                        responsys.ws57.GetLiveReportMetrics getLiveReportMetrics,responsys.ws57.SessionHeader sessionHeader40)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param uploadDocument
                
                    * @param sessionHeader41
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.UploadDocumentResponse uploadDocument(

                        responsys.ws57.UploadDocument uploadDocument,responsys.ws57.SessionHeader sessionHeader41)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param createCampaign
                
                    * @param sessionHeader42
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.CreateCampaignResponse createCampaign(

                        responsys.ws57.CreateCampaign createCampaign,responsys.ws57.SessionHeader sessionHeader42)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param copyDataSource
                
                    * @param sessionHeader43
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.CopyDataSourceResponse copyDataSource(

                        responsys.ws57.CopyDataSource copyDataSource,responsys.ws57.SessionHeader sessionHeader43)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param deleteCampaign
                
                    * @param sessionHeader44
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.DeleteCampaignResponse deleteCampaign(

                        responsys.ws57.DeleteCampaign deleteCampaign,responsys.ws57.SessionHeader sessionHeader44)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param downloadDataSource
                
                    * @param sessionHeader45
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.DownloadDataSourceResponse downloadDataSource(

                        responsys.ws57.DownloadDataSource downloadDataSource,responsys.ws57.SessionHeader sessionHeader45)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param purgeDataSourceByTimestamp
                
                    * @param sessionHeader46
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.PurgeDataSourceByTimestampResponse purgeDataSourceByTimestamp(

                        responsys.ws57.PurgeDataSourceByTimestamp purgeDataSourceByTimestamp,responsys.ws57.SessionHeader sessionHeader46)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param listFolders
                
                    * @param sessionHeader47
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.ListFoldersResponse listFolders(

                        responsys.ws57.ListFolders listFolders,responsys.ws57.SessionHeader sessionHeader47)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        /**
          * Auto generated method signature
          * 
                    * @param createFolder
                
                    * @param sessionHeader48
                
             * @throws responsys.ws57.client.RIFault : 
         */

         
                     public responsys.ws57.CreateFolderResponse createFolder(

                        responsys.ws57.CreateFolder createFolder,responsys.ws57.SessionHeader sessionHeader48)
                        throws java.rmi.RemoteException
             
          ,responsys.ws57.client.RIFault;

        

        
       //
       }
    