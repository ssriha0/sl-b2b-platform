drop table if exists spnet_provider_firm_state;
drop table if exists spnet_provider_firm_state_history;
drop table if exists spnet_serviceprovider_state;
drop table if exists spnet_serviceprovider_state_history;
drop table if exists spnet_document ;
drop table if exists spnet_campaign_network ;
drop table if exists spnet_buyer ;
drop table if exists spnet_approval_criteria ;
drop table if exists spnet_campaign_invitation_criteria ;

drop table if exists spnet_campaign_invitation_email;
drop table if exists spnet_campaign_invitation;
DROP TABLE IF EXISTS spnet_provider_firm_document ;
DROP TABLE IF EXISTS spnet_uploaded_document_state ;
drop table if exists spnet_hdr;
drop table if exists spnet_campaign_hdr ;



drop table if exists spnet_email_template;

drop table if exists spnet_workflow_status;
drop table if exists spnet_workflow_status_history;

drop table if exists lu_spnet_workflow_state;
drop table if exists lu_spnet_document_type;
drop table if exists lu_spnet_workflow_entity;
drop table if exists lu_spnet_minimum_rating;
DROP TABLE IF EXISTS lu_spnet_document_state ;
drop table if exists lu_spnet_approval_criteria;
drop table if exists lu_spnet_opt_out_reasons;


    create table spnet_approval_criteria (
        id integer(11)  unsigned  not null auto_increment,
        created_date datetime not null,
        modified_date datetime not null,
        modified_by varchar(50) not null,
        value text,
        spn_id integer unsigned,
        criteria_id integer,
        primary key (id)
    );

    create table spnet_buyer (
        id integer(11)  unsigned not null auto_increment unique,
        created_date datetime not null,
        modified_date datetime not null,
        modified_by varchar(50) not null,
        spn_id integer unsigned,
        buyer_id integer unsigned,
        primary key (id)
    );

    create table spnet_campaign_invitation_criteria (
        id integer(11)  unsigned  not null auto_increment unique,
        created_date datetime not null,
        modified_date datetime not null,
        modified_by varchar(50) not null,
        value text,
        criteria_id integer,
        campaign_id integer unsigned,
        primary key (id)
    );

    create table spnet_campaign_network (
        id integer(11)  unsigned  not null auto_increment unique,
        created_date datetime not null,
        modified_date datetime not null,
        modified_by varchar(50) not null,
        spn_id integer unsigned  not null,
        campaign_id integer unsigned,
        primary key (id)
    );

    create table spnet_campaign_hdr (
        campaign_id  integer(11)  unsigned not null auto_increment unique,
        created_date datetime not null,
        modified_date datetime not null,
        modified_by varchar(50) not null,
        campaign_name varchar(250) not null,
        end_date datetime not null,
        start_date datetime not null,
        primary key (campaign_id)
    );

    create table spnet_document (
        id integer(11)  unsigned  not null auto_increment unique,
        created_date datetime not null,
        modified_date datetime not null,
        modified_by varchar(50) not null,
        inactive_ind bit,
        spn_id integer unsigned,
        doc_type_id integer not null,
        document_id integer unsigned,
        primary key (id)
    );

     create table spnet_hdr (
        spn_id integer(11)  unsigned not null auto_increment unique ,
        created_date datetime not null,
        modified_date datetime not null,
        modified_by varchar(50) not null,
        contact_email varchar(255) not null,
        contact_name varchar(50) not null,
        contact_phone varchar(30) not null,
        spn_description varchar(255) not null,
        spn_instruction text not null,
        spn_name varchar(150) not null,
        primary key (spn_id)
    );

     create table lu_spnet_minimum_rating (
        id integer not null auto_increment,
        descr varchar(50),
        type varchar(50),
        primary key (id)
    );
    
    
	CREATE TABLE lu_spnet_opt_out_reasons (
  		id varchar(50) NOT NULL,
  		reason varchar(100) default NULL,
 		 PRIMARY KEY  (ID)
	);
    

    create table lu_spnet_approval_criteria (
        id integer not null auto_increment unique,
        column_name varchar(150) not null,
        criteria_level integer not null,
        descr varchar(50) not null,
        group_name  varchar(50) default NULL,
        lookup_table_name varchar(150) not null,
        value_type varchar(50) not null,
        primary key (id)
    );

    create table lu_spnet_document_type (
        id integer not null auto_increment unique,
        descr varchar(50) not null,
        sort_order integer,
        primary key (id)
    );

   create table spnet_email_template (
        template_id integer(11) unsigned  not null unique,
        created_date datetime not null,
        modified_date datetime not null,
        eid integer,
        priority integer not null,
        template_from varchar(255),
        template_name varchar(255) not null,
        template_source text not null,
        template_subject varchar(255) not null,
        template_to varchar(255),
        wf_entity_state varchar(30) not null,
        primary key (template_id)
    );

   create table spnet_workflow_status (
        entity_id integer(11) unsigned  not null,
        created_date datetime not null,
        modified_date datetime not null,
        modified_by varchar(50) not null,
        wf_entity_id varchar(20) not null,
        wf_entity_state varchar(30) not null,
        primary key (entity_id, wf_entity_id)
    );

     create table lu_spnet_workflow_entity (
        id varchar(20) not null unique,
        descr varchar(50) not null,
        primary key (id)
    );

    create table lu_spnet_workflow_state (
        id varchar(30) not null unique,
        descr varchar(50) not null,
        membership_status varchar(20),
        communication_subject varchar(100) NOT NULL default '',
        primary key (id)
    );

     create table spnet_workflow_status_history (
        id bigint not null,
        entity_id integer(11)  unsigned  not null,
        created_date datetime not null,
        modified_date datetime not null,
        modified_by varchar(50) not null,
        wf_entity_id varchar(20) not null,
        wf_entity_state varchar(30) not null,
        primary key (id,entity_id, wf_entity_id)
    );

    alter table spnet_email_template 
        add index FK_EMAIL_WF_ENTITY_STATE_ID (wf_entity_state), 
        add constraint FK_EMAIL_WF_ENTITY_STATE_ID 
        foreign key (wf_entity_state)
        references lu_spnet_workflow_state (id);
    


    alter table spnet_buyer 
        add index FK_SPNBUYER_BUYERID (buyer_id),
        add constraint FK_SPNBUYER_BUYERID
        foreign key (buyer_id) 
        references buyer (buyer_id);

    alter table spnet_buyer
        add index FK_SPNBUYER_SPNID (spn_id),
        add constraint FK_SPNBUYER_SPNID
        foreign key (spn_id)
        references spnet_hdr (spn_id);



    alter table spnet_campaign_network
        add index FK_CAMP_NETWORK_SPN_ID (spn_id), 
        add constraint FK_CAMP_NETWORK_SPN_ID 
        foreign key (spn_id) 
        references spnet_hdr (spn_id);

    alter table spnet_campaign_network
        add index FK_CAMP_NETWORK_CAMP_ID (campaign_id), 
        add constraint FK_CAMP_NETWORK_CAMP_ID
        foreign key (campaign_id) 
        references spnet_campaign_hdr (campaign_id);

    alter table spnet_document 
        add index FK_SPN_DOC_TYPE_ID (doc_type_id), 
        add constraint FK_SPN_DOC_TYPE_ID
        foreign key (doc_type_id) 
        references lu_spnet_document_type (id);

    alter table spnet_document
        add index FK_SPN_DOC_SPN_ID (spn_id), 
        add constraint FK_SPN_DOC_SPN_ID 
        foreign key (spn_id) 
        references spnet_hdr (spn_id);

    alter table spnet_document 
        add index FK_SPN_DOC_DOC_ID (document_id), 
        add constraint FK_SPN_DOC_DOC_ID 
        foreign key (document_id) 
        references document (document_id);


     alter table spnet_workflow_status 
        add index FK_WF_ENTITY_ID (wf_entity_id),
        add constraint FK_WF_ENTITY_ID 
        foreign key (wf_entity_id) 
        references lu_spnet_workflow_entity (id);

    alter table spnet_workflow_status 
        add index FK_WF_ENTITY_STATE_ID (wf_entity_state), 
        add constraint FK_WF_ENTITY_STATE_ID 
        foreign key (wf_entity_state) 
        references lu_spnet_workflow_state (id);


 alter table spnet_approval_criteria
        add index FK_APP_CRI_SPN_ID (spn_id),
        add constraint FK_APP_CRI_SPN_ID
        foreign key (spn_id)
        references spnet_hdr (spn_id);

    alter table spnet_approval_criteria
        add index FK_APP_CRI_CRITERIAID (criteria_id),
        add constraint FK_APP_CRI_CRITERIAID
        foreign key (criteria_id)
        references lu_spnet_approval_criteria (id);

alter table spnet_campaign_invitation_criteria
        add index FK_CAMP_APP_CRITERIA_CRITERIAID (criteria_id),
        add constraint FK_CAMP_APP_CRITERIA_CRITERIAID
        foreign key (criteria_id)
        references lu_spnet_approval_criteria (id);

    alter table spnet_campaign_invitation_criteria
        add index FK_CAMP_APP_CRITERIA_CAMPID (campaign_id),
        add constraint FK_CAMP_APP_CRITERIA_CAMPID
        foreign key (campaign_id)
        references spnet_campaign_hdr (campaign_id);

    CREATE TABLE spnet_campaign_invitation (
                             invitation_id bigint(20) unsigned NOT NULL auto_increment,
                             spn_id int(11) unsigned NOT NULL,
                             campaign_id int(11) unsigned NOT NULL,
                             prov_firm_id int(11) unsigned NOT NULL,
                             prov_firm_admin_id int(11) unsigned NOT NULL,
                             email_sent_to varchar(255) default NULL,
                             PRIMARY KEY  (invitation_id),
                             UNIQUE KEY invitation_unique_identifier (spn_id,campaign_id,prov_firm_id,prov_firm_admin_id),
                             KEY FK_CAMP_INVITATION_CAMP_ID (campaign_id),
                             KEY FK_CAMP_INVITATION_PF_ID (prov_firm_id),
                             KEY FK_CAMP_INVITATION_PFADMIN_ID (prov_firm_admin_id),
                             CONSTRAINT FK_CAMP_INVITATION_CAMP_ID FOREIGN KEY (campaign_id) REFERENCES spnet_campaign_hdr (campaign_id),
                             CONSTRAINT FK_CAMP_INVITATION_PFADMIN_ID FOREIGN KEY (prov_firm_admin_id) REFERENCES vendor_resource (resource_id),
                             CONSTRAINT FK_CAMP_INVITATION_PF_ID FOREIGN KEY (prov_firm_id) REFERENCES vendor_hdr (vendor_id),
                             CONSTRAINT FK_CAMP_INVITATION_SPN_ID FOREIGN KEY (spn_id) REFERENCES spnet_hdr (spn_id)
      ) ;


   CREATE TABLE spnet_campaign_invitation_email (
                                   invitation_id bigint(20) unsigned NOT NULL,
                                   email_template_id int(11) unsigned NOT NULL,                                                                                                   
                                   email_sent_time datetime NOT NULL,                                                                                                             
                                   PRIMARY KEY  (invitation_id,email_template_id),                                                                                              
                                   KEY FK_spnet_campaign_invitation_email_Id (email_template_id),                                                                               
                                   CONSTRAINT FK_spnet_campaign_invitation_email_invi_id FOREIGN KEY (invitation_id) REFERENCES spnet_campaign_invitation (invitation_id),  
                                   CONSTRAINT FK_spnet_campaign_invitation_email_Id FOREIGN KEY (email_template_id) REFERENCES spnet_email_template (template_id)           
                                 );

CREATE TABLE spnet_provider_firm_state (
                             spn_id int(11) unsigned NOT NULL,
                             provider_firm_id int(11) unsigned NOT NULL,                                                                                       
                             modified_date timestamp NOT NULL default CURRENT_TIMESTAMP,
                             created_date timestamp NOT NULL default 0 ,
                             modified_by varchar(30) NOT NULL,                                                                                                 
                             provider_wf_state varchar(30) NOT NULL,                                                                                           
                             PRIMARY KEY  (spn_id,provider_firm_id),
                             KEY FK_SPNET_PROVIDER_FIRM_STATE_VENDOR_ID (provider_firm_id),
                             KEY FK_SPNET_PROVIDER_FIRM_STATE_WF_ID (provider_wf_state),
                             CONSTRAINT FK_SPNET_PROVIDER_FIRM_STATE_WF_ID FOREIGN KEY (provider_wf_state) REFERENCES lu_spnet_workflow_state (id),
                             CONSTRAINT FK_SPNET_PROVIDER_FIRM_STATE_SPN_ID FOREIGN KEY (spn_id) REFERENCES spnet_hdr (spn_id),
                             CONSTRAINT FK_SPNET_PROVIDER_FIRM_STATE_VENDOR_ID FOREIGN KEY (provider_firm_id) REFERENCES vendor_hdr (vendor_id)
                           );


                           
                           
                            CREATE TABLE spnet_serviceprovider_state (
                             spn_id int(11) unsigned NOT NULL,                                                                                                 
                             service_provider_id int(11) unsigned NOT NULL,
                             modified_date timestamp NOT NULL default CURRENT_TIMESTAMP,
                             created_date timestamp NOT NULL default 0,
                             modified_by varchar(30) NOT NULL,                                                                                                 
                             provider_wf_state varchar(30) NOT NULL,                                                                                           
                             PRIMARY KEY  (spn_id,service_provider_id),
                             KEY FK_SPNET_SERVICE_PROVIDER_STATE_VENDOR_ID (service_provider_id),                                                              
                             KEY FK_SPNET_SERVICE_PROVIDER_STATE_WF_ID (provider_wf_state),                                                                 
                             CONSTRAINT FK_SPNET_SERVICE_PROVIDER_STATE_WF_ID FOREIGN KEY (provider_wf_state) REFERENCES lu_spnet_workflow_state (id),  
                             CONSTRAINT FK_SPNET_SERVICE_PROVIDER_STATE_SPN_ID FOREIGN KEY (spn_id) REFERENCES spnet_hdr (spn_id),
                             CONSTRAINT FK_SPNET_SERVICE_PROVIDER_STATE_VENDOR_ID FOREIGN KEY (service_provider_id) REFERENCES vendor_resource (resource_id)      
                           );


CREATE  TABLE IF NOT EXISTS lu_spnet_document_state (
   doc_state_id VARCHAR(30) UNIQUE NOT NULL, 
  descr VARCHAR(45) NOT NULL ,
  PRIMARY KEY (doc_state_id) )
COMMENT = 'Document state lookup';



-- -----------------------------------------------------
-- Table spnet_uploaded_document_state
-- -----------------------------------------------------



CREATE  TABLE IF NOT EXISTS spnet_uploaded_document_state (
  spn_doc_id integer(11) unsigned  NOT NULL ,
  prov_firm_upld_doc_id  integer(11) unsigned  NOT NULL ,
  buyer_id  integer(11) unsigned  NOT NULL ,
  prov_firm_id  integer(11) unsigned  NOT NULL ,
  doc_state_id VARCHAR(30) NOT NULL  ,
  PRIMARY KEY (prov_firm_upld_doc_id, buyer_id, prov_firm_id, spn_doc_id) ,
  CONSTRAINT spnet_prov_upld_doc_state_id
    FOREIGN KEY (doc_state_id )
    REFERENCES lu_spnet_document_state (doc_state_id ),
    CONSTRAINT spnet_prov_upld_doc_state_buyerid
    FOREIGN KEY (buyer_id )
    REFERENCES buyer (buyer_id ),
    CONSTRAINT spnet_prov_upld_doc_state_doc_id
    FOREIGN KEY (spn_doc_id )
    REFERENCES document (document_id ),
     CONSTRAINT spnet_prov_upld_doc_state_updoc_id
    FOREIGN KEY (prov_firm_upld_doc_id )
    REFERENCES document (document_id ),
    CONSTRAINT spnet_prov_upld_doc_state_vendor_id
    FOREIGN KEY (prov_firm_id)
    REFERENCES vendor_hdr (vendor_id)
)
COMMENT = 'used for geeting one place document status for the given buyer';


CREATE INDEX spnet_prov_upld_doc_state_updoc_id ON spnet_uploaded_document_state (prov_firm_upld_doc_id ASC) ;


CREATE INDEX spnet_prov_upld_doc_state_buyerid ON spnet_uploaded_document_state (buyer_id ASC) ;


CREATE INDEX spnet_prov_upld_doc_state_id ON spnet_uploaded_document_state (doc_state_id ASC) ;


CREATE INDEX spnet_prov_upld_doc_state_doc_id ON spnet_uploaded_document_state (spn_doc_id ASC) ;



-- -----------------------------------------------------
-- Table spnet_provider_firm_document
-- -----------------------------------------------------



CREATE  TABLE IF NOT EXISTS spnet_provider_firm_document (
  id integer(11) unsigned not null auto_increment unique ,
  spn_id integer(11) unsigned not null ,
  prov_firm_upld_doc_id integer(11) unsigned not null  ,
  prov_firm_id integer(11) unsigned not null  ,
  deleted_ind BOOLEAN NULL default false ,
  PRIMARY KEY (id) ,
  CONSTRAINT spnet_pf_document_spn_id
    FOREIGN KEY (spn_id )
    REFERENCES spnet_hdr (spn_id ),
  CONSTRAINT spnet_pf_document_doc_id
    FOREIGN KEY (prov_firm_upld_doc_id )
    REFERENCES document (document_id ),
  CONSTRAINT spnet_pf_document_vendor_id
    FOREIGN KEY (prov_firm_id)
    REFERENCES vendor_hdr (vendor_id)
)
COMMENT = 'Documents uploded by provider Firm for given SPN';


CREATE INDEX spnet_pf_document_spn_id ON spnet_provider_firm_document (spn_id ASC) ;


CREATE INDEX spnet_pf_document_doc_id ON spnet_provider_firm_document (prov_firm_upld_doc_id ASC) ;


CREATE INDEX spnet_pf_document_vendor_id ON spnet_provider_firm_document (prov_firm_id ASC) ;
                           


ALTER TABLE spnet_provider_firm_state ADD (app_submission_date timestamp null);

ALTER TABLE spnet_provider_firm_state ADD (agreement_ind int(1) default 0);

ALTER TABLE spnet_provider_firm_state ADD (opt_out_reason_code VARCHAR(50));

ALTER TABLE spnet_provider_firm_state ADD (opt_out_comment VARCHAR(100));

ALTER TABLE spnet_provider_firm_state ADD (reviewed_date timestamp null);

ALTER TABLE spnet_provider_firm_state ADD (reviewed_by varchar(50) null);


-- alter table spnet_provider_firm_document drop column modified_by;
alter table spnet_provider_firm_document add column modified_by varchar(50) not null;
update spnet_provider_firm_document set modified_by = 'SYSTEM';

-- alter table spnet_provider_firm_document drop column created_date;
alter table spnet_provider_firm_document add column created_date timestamp not null default 0;
update spnet_provider_firm_document set created_date = SYSDATE();

-- alter table spnet_provider_firm_document drop column modified_date;
alter table spnet_provider_firm_document add column modified_date timestamp NOT NULL default CURRENT_TIMESTAMP;
update spnet_provider_firm_document set modified_date = SYSDATE();



-- alter table spnet_uploaded_document_state drop column modified_by;
alter table spnet_uploaded_document_state add column modified_by varchar(50) not null;
update spnet_uploaded_document_state set modified_by = 'SYSTEM';

-- alter table spnet_uploaded_document_state drop column created_date;
alter table spnet_uploaded_document_state add column created_date timestamp not null default 0;
update spnet_uploaded_document_state set created_date = SYSDATE();

-- alter table spnet_uploaded_document_state drop column modified_date;
alter table spnet_uploaded_document_state add column modified_date timestamp NOT NULL default CURRENT_TIMESTAMP;
update spnet_uploaded_document_state set modified_date = SYSDATE();