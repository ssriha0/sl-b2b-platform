    CREATE TABLE spnet_campaign_invitation (
                             invitation_id  integer generated by default as identity (start with 1),
                             spn_id Integer NOT NULL,
                             campaign_id Integer NOT NULL,
                             prov_firm_id Integer NOT NULL,
                             prov_firm_admin_id Integer  NOT NULL,
                             email_sent_to varchar(100) default NULL,
                             PRIMARY KEY  (invitation_id)
      ) ;
      
     CREATE TABLE spnet_campaign_invitation_email (
                                   invitation_id Integer NOT NULL,
                                   email_template_id Integer NOT NULL,                                                                                                   
                                   email_sent_time timestamp NOT NULL,                                                                                                             
                                   PRIMARY KEY  (invitation_id,email_template_id)                                                                                              
                                 );


     create table spnet_workflow_status_history (
        id  integer generated by default as identity (start with 1),
        entity_id Integer  not null,
        created_date timestamp not null,
        modified_date timestamp not null,
        modified_by varchar(50) not null,
        wf_entity_id varchar(20) not null,
        wf_entity_state varchar(30) not null,
        primary key (id)
    );
    
    
 CREATE TABLE dual(bogus_id varchar(50) NOT NULL);
 
 insert into dual (bogus_id) values('bogus id');
 
 