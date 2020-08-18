

/**
 * TriggeredMessageWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */

    package com.rsys.tmws.client;

    /*
     *  TriggeredMessageWS java interface
     */

    public interface TriggeredMessageWS {
          

        /**
          * Auto generated method signature
          * Login to the Triggered Message Web Service.
                    * @param login
                
             * @throws com.rsys.tmws.client.AccountFault : 
             * @throws com.rsys.tmws.client.UnexpectedErrorFault : 
         */

         
                     public com.rsys.tmws.LoginResponse login(

                        com.rsys.tmws.Login login)
                        throws java.rmi.RemoteException
             
          ,com.rsys.tmws.client.AccountFault
          ,com.rsys.tmws.client.UnexpectedErrorFault;

        

        /**
          * Auto generated method signature
          * Send Triggered Message to one or more recipients.
                    * @param sendTriggeredMessages
                
                    * @param sessionHeader
                
             * @throws com.rsys.tmws.client.UnexpectedErrorFault : 
             * @throws com.rsys.tmws.client.TriggeredMessageFault : 
         */

         
                     public com.rsys.tmws.SendTriggeredMessagesResponse sendTriggeredMessages(

                        com.rsys.tmws.SendTriggeredMessages sendTriggeredMessages,com.rsys.tmws.SessionHeader sessionHeader)
                        throws java.rmi.RemoteException
             
          ,com.rsys.tmws.client.UnexpectedErrorFault
          ,com.rsys.tmws.client.TriggeredMessageFault;

        

        /**
          * Auto generated method signature
          * Logout of the Triggered Message Web Service.
                    * @param logout
                
                    * @param sessionHeader0
                
             * @throws com.rsys.tmws.client.AccountFault : 
             * @throws com.rsys.tmws.client.UnexpectedErrorFault : 
         */

         
                     public com.rsys.tmws.LogoutResponse logout(

                        com.rsys.tmws.Logout logout,com.rsys.tmws.SessionHeader sessionHeader0)
                        throws java.rmi.RemoteException
             
          ,com.rsys.tmws.client.AccountFault
          ,com.rsys.tmws.client.UnexpectedErrorFault;

        

        /**
          * Auto generated method signature
          * Check Triggered Message Status for one or more trigger message IDs.
                    * @param checkTriggeredMessages
                
                    * @param sessionHeader1
                
             * @throws com.rsys.tmws.client.UnexpectedErrorFault : 
             * @throws com.rsys.tmws.client.TriggeredMessageFault : 
         */

         
                     public com.rsys.tmws.CheckTriggeredMessagesResponse checkTriggeredMessages(

                        com.rsys.tmws.CheckTriggeredMessages checkTriggeredMessages,com.rsys.tmws.SessionHeader sessionHeader1)
                        throws java.rmi.RemoteException
             
          ,com.rsys.tmws.client.UnexpectedErrorFault
          ,com.rsys.tmws.client.TriggeredMessageFault;

        

        
       //
       }
    