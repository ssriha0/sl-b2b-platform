alter table shc_order add column wf_state_id int(10) unsigned after so_id;
alter table shc_order add column nps_process_id int(10) unsigned after wf_state_id;
alter table shc_order add column sales_check_num varchar(50) after nps_process_id;
alter table shc_order add column sales_check_date datetime after sales_check_num;

