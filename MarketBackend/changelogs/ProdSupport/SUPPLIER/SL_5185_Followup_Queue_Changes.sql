create or replace view vw_pb_queue_follow_up as
select `so_hdr`.`so_id` AS `so_id`,`so_hdr`.`buyer_id` AS `buyer_id`,`so_hdr`.`wf_state_id` AS `wf_state_id`,`so_hdr`.`so_substatus_id` AS `so_substatus_id`,`so_hdr`.`service_date1` AS `service_date1`,`wf_alais`.`sort_order` AS `sort_order`,`pb_reque`.`reque_date` AS `REQUE_DATE`
from `so_hdr`
	join `pb_reque` on `so_hdr`.`so_id` = `pb_reque`.`so_id` and `pb_reque`.`reque_date` < now()
	join `lu_wf_states_alias` `wf_alais` on `so_hdr`.`wf_state_id` = `wf_alais`.`wf_state_id` and `wf_alais`.`role_id` = 3
where `so_hdr`.wf_state_id not in (120, 125, 180)
	and (not(exists(select 1 AS `1` from `pb_reque` `pbr`
			where `so_hdr`.`so_id` = `pbr`.`so_id` and `pbr`.`reque_date` > now())))
order by `pb_reque`.`reque_date` desc;
