-- Time Window Expired - Arrival;
create or replace view vw_pb_queue_time_window_expired_arrival as
select `so_hdr`.`so_id` AS `so_id`,`so_hdr`.`buyer_id` AS `buyer_id`,`so_hdr`.`wf_state_id` AS `wf_state_id`,`so_hdr`.`so_substatus_id` AS `so_substatus_id`,`so_hdr`.`service_date1` AS `service_date1`,`wf_alais`.`sort_order` AS `sort_order`,`pb_reque`.`reque_date` AS `REQUE_DATE`,`so_hdr`.`created_date` AS `created_date`
from ((`so_hdr` left join `pb_reque` on((`so_hdr`.`so_id` = `pb_reque`.`so_id`))) join `lu_wf_states_alias` `wf_alais` on
	(((`so_hdr`.`wf_state_id` = `wf_alais`.`wf_state_id`) and (`wf_alais`.`role_id` = 3))))
where ((`pb_reque`.`reque_date` < now()) or isnull(`pb_reque`.`reque_date`))
	and (`so_hdr`.`wf_state_id` = 155)
	and (not(exists(select `so_onsite_visit`.`visit_id` AS `visit_id`
		from `so_onsite_visit` where ((`so_onsite_visit`.`arrival_date` is not null or `so_onsite_visit`.`departure_date` is not null ) and (`so_onsite_visit`.`so_id` = `so_hdr`.`so_id`))
	   )))
	and `so_hdr`.`so_substatus_id` is null
	and ((`so_hdr`.`activated_date` + interval 6 hour) < curdate())
order by `pb_reque`.`reque_date` desc;


-- Time Window Expired - Departure;
create or replace view vw_pb_queue_time_window_expired_departure as
select `so_hdr`.`so_id` AS `so_id`,`so_hdr`.`buyer_id` AS `buyer_id`,`so_hdr`.`wf_state_id` AS `wf_state_id`,`so_hdr`.`so_substatus_id` AS `so_substatus_id`,`so_hdr`.`service_date1` AS `service_date1`,`wf_alais`.`sort_order` AS `sort_order`,`pb_reque`.`reque_date` AS `REQUE_DATE`,`so_hdr`.`created_date` AS `created_date`
from ((`so_hdr` left join `pb_reque` on((`so_hdr`.`so_id` = `pb_reque`.`so_id`))) join `lu_wf_states_alias` `wf_alais` on
	(((`so_hdr`.`wf_state_id` = `wf_alais`.`wf_state_id`) and (`wf_alais`.`role_id` = 3))))
where ((`pb_reque`.`reque_date` < now()) or isnull(`pb_reque`.`reque_date`))
	and (`so_hdr`.`wf_state_id` = 155)
	and (exists(select `so_onsite_visit`.`visit_id` AS `visit_id`
		from `so_onsite_visit` where ((`so_onsite_visit`.`arrival_date` is not null) and (`so_onsite_visit`.`departure_date` is null) and (`so_onsite_visit`.`so_id` = `so_hdr`.`so_id`))
	   ))
	and (isnull(`so_hdr`.`so_substatus_id`) or (`so_hdr`.`so_substatus_id` <> 8 and `so_hdr`.`so_substatus_id` <> 37)) and
		((`so_hdr`.`activated_date` + interval 6 hour) < curdate())
order by `pb_reque`.`reque_date` desc;


-- Time Window Expired - Completion Record;
create or replace view vw_pb_queue_time_window_expired as
select `so_hdr`.`so_id` AS `so_id`,`so_hdr`.`buyer_id` AS `buyer_id`,`so_hdr`.`wf_state_id` AS `wf_state_id`,`so_hdr`.`so_substatus_id` AS `so_substatus_id`,`so_hdr`.`service_date1` AS `service_date1`,`wf_alais`.`sort_order` AS `sort_order`,`pb_reque`.`reque_date` AS `REQUE_DATE`,`so_hdr`.`created_date` AS `created_date`
from ((`so_hdr` left join `pb_reque` on((`so_hdr`.`so_id` = `pb_reque`.`so_id`))) join `lu_wf_states_alias` `wf_alais` on
	(((`so_hdr`.`wf_state_id` = `wf_alais`.`wf_state_id`) and (`wf_alais`.`role_id` = 3))))
where ((`pb_reque`.`reque_date` < now()) or isnull(`pb_reque`.`reque_date`))
	and (`so_hdr`.`wf_state_id` = 155)
	and (`so_hdr`.`so_substatus_id` = 8)
	and (((select `so_logging`.`created_date` AS `created_date` from `so_logging`
			where ((`so_logging`.`so_id` = `so_hdr`.`so_id`) and (`so_logging`.`action_id` = 35) and (`so_logging`.`new_value` = _latin1'8'))
			limit 1
		  ) + interval 24 hour) < curdate())
order by `pb_reque`.`reque_date` desc;
