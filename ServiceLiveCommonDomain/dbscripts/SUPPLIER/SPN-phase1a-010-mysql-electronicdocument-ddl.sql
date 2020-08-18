drop table spnet_uploaded_electronic_doc_state; 

CREATE TABLE spnet_uploaded_electronic_doc_state (                                                                                          
                                       spn_doc_id int(11) unsigned NOT NULL,                                                                                                     
                                       prov_firm_id int(11) unsigned NOT NULL,                                                                                                   
                                       doc_state_id varchar(30) NOT NULL,                                                                                                        
                                       modified_by varchar(50) NOT NULL,                                                                                                         
                                       created_date timestamp NOT NULL default '0000-00-00 00:00:00',                                                                            
                                       modified_date timestamp NOT NULL default CURRENT_TIMESTAMP,                                                                               
                                       PRIMARY KEY  (prov_firm_id,spn_doc_id),                                                                                                 
                                       KEY spnet_uploaded_electronic_doc_state_vendor_id (prov_firm_id),                                                                       
                                       KEY spnet_uploaded_electronic_doc_state_state_id (doc_state_id),                                                                        
                                       KEY spnet_uploaded_electronic_doc_state_doc_id (spn_doc_id),                                                                            
                                       CONSTRAINT FK_spnet_uploaded_electronic_doc_state FOREIGN KEY (spn_doc_id) REFERENCES document (document_id),                       
                                       CONSTRAINT FK_spnet_uploaded_electronic_doc_state_id FOREIGN KEY (doc_state_id) REFERENCES lu_spnet_document_state (doc_state_id),  
                                       CONSTRAINT FK_spnet_uploaded_electronic_doc_state_vendor_id FOREIGN KEY (prov_firm_id) REFERENCES vendor_hdr (vendor_id)            
                                     ) ENGINE=InnoDB;