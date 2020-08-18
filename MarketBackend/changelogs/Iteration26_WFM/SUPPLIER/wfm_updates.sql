--
-- add/update substates
--
insert into lu_so_substatus values(56, 'No Provider', 56, '2009-01-28 13:00:00', '2009-01-28 13:00:00', 'jong');
insert into lu_so_substatus values(57, 'Time Window Expired � Arrival', 57, '2009-01-28 13:00:00', '2009-01-28 13:00:00', 'jong');
insert into lu_so_substatus values(58, 'Time Window Expired � Departure', 58, '2009-01-28 13:00:00', '2009-01-28 13:00:00', 'jong');
insert into lu_so_substatus values(59, ' Validate POS Cancellation', 59, '2009-01-28 13:00:00', '2009-01-28 13:00:00', 'jong');

--
-- states - substates matrix
--
insert into so_wf_substatus (wf_state_id, so_substatus_id, sort_order, created_date, modified_date, modified_by)
values 
	(100, 56, 59, now(), now(), 'jong'),
	(150, 37, 58, now(), now(), 'jong'),
	(150, 59, 59, now(), now(), 'jong'),
	(155, 50, 56, now(), now(), 'jong'),
	(155, 57, 57, now(), now(), 'jong'),
	(155, 58, 58, now(), now(), 'jong'),
	(155, 59, 59, now(), now(), 'jong');

--
-- misc
--
alter table survey_response_hdr add reviewed_ind tinyint(4) not null default 0;

--
-- migrate followup queue for sears
--
insert into wfm_so_queues (
	company_id,
	queue_id,
	queue_seq,
	so_id,
	queued_date,
	claimed_ind,
	claimed_date,
	claimed_by_id,
	claimed_from_queue_id,
	grouped_ind,
	so_group_id,
	sort_order,
	created_date, 
	modified_date
)
select
	1000,
	2,
	0,
	vw.so_id, 
	vw.REQUE_DATE,
	if(cl.resource_id is null, 0, 1), 
	cl.claim_date,
	cl.resource_id,
	if(cl.resource_id is null, null, 2), 
	if(so.so_group_id is null, 0, 1), 
	so.so_group_id,
	0,
	now(),
	now()
from vw_pb_queue_follow_up vw 
	left join so_hdr so on vw.so_id = so.so_id
	left join pb_claim cl on vw.so_id = cl.so_id;


insert into application_properties (app_key, app_value, app_desc, created_date) values('sears_buyer_id', 1000, 'Adding Sears buyer id for WFM - displaying SP phone no', now());
