insert into lu_location_type (id, type, descr, sort_order) values (1, '', 'Business Address', 1);
insert into lu_location_type (id, type, descr, sort_order) values (2, '', 'Mailing Address', 2);
insert into lu_location_type (id, type, descr, sort_order) values (3, '', 'Home Address', 3);
insert into lu_location_type (id, type, descr, sort_order) values (4, '', 'Work Address', 4);
insert into lu_location_type (id, type, descr, sort_order) values (5, '', 'Billing Address', 5);
insert into lu_location_type (id, type, descr, sort_order) values (6, '', 'Other', 6);

-- insert data for wf sates
 insert into wf_states (wf_state_id, wf_entity, wf_state, wf_descr, sort_order, audit_link_id ) 
 values (1, 'Company Profile', 'Incomplete', 'Service Provider Company - Incomplete Information', 1, 1);
  insert into wf_states (wf_state_id, wf_entity, wf_state, wf_descr, sort_order, audit_link_id ) 
 values (2, 'Company Profile', 'Pending Approval', 'Service Provider Company - Company profile is complete and submitted', 2, 1);
  insert into wf_states (wf_state_id, wf_entity, wf_state, wf_descr, sort_order, audit_link_id ) 
 values (3, 'Company Profile', 'Sears Provider Approved', 'Service Provider Company -Approved', 3, 1);
 insert into wf_states (wf_state_id, wf_entity, wf_state, wf_descr, sort_order, audit_link_id ) 
 values (4, 'Team Member', 'Incomplete', 'Service Provider Company - Incomplete Information',4, 1);
 insert into wf_states (wf_state_id, wf_entity, wf_state, wf_descr, sort_order, audit_link_id ) 
 values (5, 'Team Member',  'Pending Approval', 'Service Provider - Ready For Review', 5, 1);
 insert into wf_states (wf_state_id, wf_entity, wf_state, wf_descr, sort_order, audit_link_id ) 
 values (6, 'Team Member', 'Approved (market ready)', 'Service Provider Company - Market Ready', 6, 1);


