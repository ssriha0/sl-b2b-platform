drop table if exists spnet_email_template;

    create table spnet_email_template (
        template_id integer not null,
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

    alter table spnet_email_template 
        add index FK_EMAIL_WF_ENTITY_STATE_ID (wf_entity_state), 
        add constraint FK_EMAIL_WF_ENTITY_STATE_ID 
        foreign key (wf_entity_state) 
        references lu_spnet_workflow_state (id);
 