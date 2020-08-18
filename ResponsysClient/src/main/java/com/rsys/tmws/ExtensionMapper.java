
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:47 EDT)
 */

            package com.rsys.tmws;
            /**
            *  ExtensionMapper class
            */
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "urn:tmws.rsys.com".equals(namespaceURI) &&
                  "LoginResult".equals(typeName)){
                   
                            return  com.rsys.tmws.LoginResult.Factory.parse(reader);
                        

                  }

              
                  if (
                  "urn:tmws.rsys.com".equals(namespaceURI) &&
                  "Status".equals(typeName)){
                   
                            return  com.rsys.tmws.Status.Factory.parse(reader);
                        

                  }

              
                  if (
                  "urn:tmws.rsys.com".equals(namespaceURI) &&
                  "TriggeredMessageResult".equals(typeName)){
                   
                            return  com.rsys.tmws.TriggeredMessageResult.Factory.parse(reader);
                        

                  }

              
                  if (
                  "urn:tmws.rsys.com".equals(namespaceURI) &&
                  "RecipientData".equals(typeName)){
                   
                            return  com.rsys.tmws.RecipientData.Factory.parse(reader);
                        

                  }

              
                  if (
                  "urn:tmws.rsys.com".equals(namespaceURI) &&
                  "EmailFormat".equals(typeName)){
                   
                            return  com.rsys.tmws.EmailFormat.Factory.parse(reader);
                        

                  }

              
                  if (
                  "urn:fault.tmws.rsys.com".equals(namespaceURI) &&
                  "ApiFault".equals(typeName)){
                   
                            return  com.rsys.tmws.fault.ApiFault.Factory.parse(reader);
                        

                  }

              
                  if (
                  "urn:tmws.rsys.com".equals(namespaceURI) &&
                  "PersonalizationData".equals(typeName)){
                   
                            return  com.rsys.tmws.PersonalizationData.Factory.parse(reader);
                        

                  }

              
                  if (
                  "urn:fault.tmws.rsys.com".equals(namespaceURI) &&
                  "ExceptionCode".equals(typeName)){
                   
                            return  com.rsys.tmws.fault.ExceptionCode.Factory.parse(reader);
                        

                  }

              
                  if (
                  "urn:tmws.rsys.com".equals(namespaceURI) &&
                  "CheckMessageResult".equals(typeName)){
                   
                            return  com.rsys.tmws.CheckMessageResult.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    