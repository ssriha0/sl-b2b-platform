delimiter //

--
-- WFM Queue update driver
--
drop procedure if exists sp_wfm_queue_update_all;
create procedure sp_wfm_queue_update_all()
begin
	declare v_queue_id int(10);
	declare v_queue_name varchar(80);
	declare v_buyer_id int(10);
	declare bq_cur_end boolean default false;
	
	declare bq_cur cursor for
		select bq.queue_id, q.queue_name, bq.buyer_id 
		from wfm_buyer_queues bq left join wfm_queues q on bq.queue_id = q.queue_id
		order by bq.buyer_id, bq.queue_sort_order;
		
	declare continue handler for not found set bq_cur_end = true;
	
	open bq_cur;
	
	doit: loop
		fetch bq_cur into v_queue_id, v_queue_name, v_buyer_id;
		if bq_cur_end then 
			leave doit;
		end if;
		
		select concat('Working on queue=', v_queue_id, ' (', v_queue_name, ') for buyer=', v_buyer_id) as 'started...';
		
		set @started = now();

		case v_queue_id
			when 1 then
				delete from wfm_so_queues
				where queue_id = 1 and company_id = v_buyer_id 
					and completed_ind = 1 and claimed_ind = 0;
			when 2 then
				delete from wfm_so_queues
				where queue_id = 2 and company_id = v_buyer_id 
					and completed_ind = 1 and claimed_ind = 0;
			when 11 then call sp_wfm_queue_mm_posted(v_buyer_id);
			when 12 then call sp_wfm_queue_mm_all_responded(v_buyer_id);
			when 13 then call sp_wfm_queue_mm_support(v_buyer_id);
			when 14 then call sp_wfm_queue_mm_expired(v_buyer_id);
			when 15 then call sp_wfm_queue_mm_oldest(v_buyer_id);
			when 16 then call sp_wfm_queue_mm_active(v_buyer_id);
			when 17 then call sp_wfm_queue_mm_completed(v_buyer_id);
			when 18 then call sp_wfm_queue_mm_problem(v_buyer_id);
			when 19 then call sp_wfm_queue_mm_poor_rating(v_buyer_id);
			when 31 then call sp_wfm_queue_call_customer(v_buyer_id);
			when 32 then call sp_wfm_queue_missing_information(v_buyer_id);
			when 33 then call sp_wfm_queue_find_provider(v_buyer_id);
			when 34 then call sp_wfm_queue_ensure_pros_acknowledge(v_buyer_id);
			when 35 then call sp_wfm_queue_repost(v_buyer_id);
			when 36 then call sp_wfm_queue_respond_counter_offers(v_buyer_id);
			when 37 then call sp_wfm_queue_first_scheduling(v_buyer_id);
			when 38 then call sp_wfm_queue_reschedule(v_buyer_id);
			when 39 then call sp_wfm_queue_expired_arrival(v_buyer_id);
			when 40 then call sp_wfm_queue_resolution_required(v_buyer_id);
			when 41 then call sp_wfm_queue_expired_departure(v_buyer_id);
			when 42 then call sp_wfm_queue_documentation(v_buyer_id);
			when 43 then call sp_wfm_queue_awaiting_payment(v_buyer_id);
			when 44 then call sp_wfm_queue_cancelled(v_buyer_id);
			when 45 then call sp_wfm_queue_validate_cancellation(v_buyer_id);
			else select concat('WARNING... do not know what to do about queue_name=', v_queue_name);
		end case;

		set @ended = now();
		select concat('Time Taken: ', timediff(@ended, @started)) as 'ended...';

	end loop;
	
	close bq_cur;

end;

--
-- Update a queue based on newly populated work queue
--
drop procedure if exists sp_wfm_queue_update_by_queue_company;
create procedure sp_wfm_queue_update_by_queue_company(v_queue_id int, v_company_id int, v_remove boolean)
begin
	select concat('Updating wfm_so_queues for queue=', v_queue_id, ', company=', v_company_id) as 'progress';
	
	--
	-- remove SOs that are no longer in the queue
	--
	if v_remove then
		delete from wfm_so_queues
		where queue_id = v_queue_id and company_id = v_company_id 
			and claimed_ind = 0
			and so_id not in (
				select so_id from z_work_wfm_so_queues where queue_id = v_queue_id and company_id = v_company_id
			);
	end if;
	
	--
	-- add new SOs
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
		unclaimed_date,
		completed_ind,
		created_date,
		modified_date,
		grouped_ind,
		so_group_id,
		note_id,
		sort_order
	)
	select
		company_id,
		queue_id,
		queue_seq,
		so_id,
		queued_date,
		claimed_ind,
		claimed_date,
		claimed_by_id,
		claimed_from_queue_id,
		unclaimed_date,
		completed_ind,
		created_date,
		modified_date,
		grouped_ind,
		so_group_id,
		note_id,
		sort_order
	from z_work_wfm_so_queues
	where queue_id = v_queue_id and company_id = v_company_id and so_id not in (
		select so_id from wfm_so_queues where queue_id = v_queue_id and company_id = v_company_id
	);
	
	--
	-- remove SOs that reached endstate
	--
	delete from wfm_so_queues
	where queue_id = v_queue_id and company_id = v_company_id 
		and completed_ind = 1 and claimed_ind = 0;

	--
	-- update sort order & so_group_id
	--
	update wfm_so_queues 
	set sort_order = 0
	where queue_id = v_queue_id and company_id = v_company_id;
	
	update wfm_so_queues w, z_work_wfm_so_queues z
	set
		w.sort_order = z.sort_order,
		w.grouped_ind = z.grouped_ind,
		w.so_group_id = z.so_group_id
	where w.queue_id = v_queue_id and w.company_id = v_company_id
		and w.queue_id = z.queue_id and w.company_id = z.company_id and w.so_id = z.so_id;
	
	--
	-- fix claimed status
	--
	update wfm_so_queues w, 
		(
			select distinct so_id, claimed_by_id, claimed_date, claimed_from_queue_id
			from wfm_so_queues
			where claimed_ind = 1 and so_group_id is null
		) t
	set 
		w.claimed_by_id = t.claimed_by_id,
		w.claimed_date = t.claimed_date,
		w.claimed_from_queue_id = t.claimed_from_queue_id
	where w.queue_id = v_queue_id and w.company_id = v_company_id
		and w.so_id = t.so_id;
			
	update wfm_so_queues w, 
		(
			select distinct so_group_id, claimed_by_id, claimed_date, claimed_from_queue_id
			from wfm_so_queues
			where claimed_ind = 1 and so_group_id is not null
		) t
	set 
		w.claimed_by_id = t.claimed_by_id,
		w.claimed_date = t.claimed_date,
		w.claimed_from_queue_id = t.claimed_from_queue_id
	where w.queue_id = v_queue_id and w.company_id = v_company_id
		and w.so_group_id = t.so_group_id;

	commit;
end;


--
-- MARKET MAKER QUEUES
--

--
-- Update queue: Marketplace Posted Orders
--
drop procedure if exists sp_wfm_queue_mm_posted;
create procedure sp_wfm_queue_mm_posted(v_company_id int)
begin
	declare v_queue_id int default 11;

	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.wf_state_id = 110										-- state=routed
		and so.routed_date + interval 1 hour < now()
		and (so.buyer_id not in (1000) or so.routed_date + interval 6 hour > now())
	order by fn_service_start_date_in_server_tz(so.so_id);
	
	commit;
	
	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;


--
-- Update queue: Marketplace Posted Orders - All Responded
--
drop procedure if exists sp_wfm_queue_mm_all_responded;
create procedure sp_wfm_queue_mm_all_responded(v_company_id int)
begin
	declare v_queue_id int default 12;

	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.buyer_id not in (1000)
		and so.wf_state_id = 110									-- state=routed
		and not exists (
			select 1
			from so_routed_providers p
			where p.so_id = so.so_id
				and (p.provider_resp_id is null or p.provider_resp_id not in (2, 3))	-- not conditional/rejected
		)
	order by fn_service_start_date_in_server_tz(so.so_id);
	
	commit;
	
	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;


--
-- Update queue: Support Request
--
drop procedure if exists sp_wfm_queue_mm_support;
create procedure sp_wfm_queue_mm_support(v_company_id int)
begin
	declare v_queue_id int default 13;

	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		exists (select 1 from so_notes nn where nn.so_id = so.so_id and nn.note_type_id = 1)	-- support notes
		and
		not exists (select 1 from so_notes nn where nn.so_id = so.so_id and nn.role_id = 2)		-- admin response
	order by so.created_date;
	
	commit;
	
	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;


--
-- Update queue: Expired Orders
--
drop procedure if exists sp_wfm_queue_mm_expired;
create procedure sp_wfm_queue_mm_expired(v_company_id int)
begin
	declare v_queue_id int default 14;

	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.wf_state_id = 130										-- state=expired
	order by so.expired_date desc;
	
	commit;
	
	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;


--
-- Update queue: 10 Oldest Service Orders
--
drop procedure if exists sp_wfm_queue_mm_oldest;
create procedure sp_wfm_queue_mm_oldest(v_company_id int)
begin
	declare v_queue_id int default 15;

	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.wf_state_id not in (100, 120, 125, 180)				-- state=not (routed, cancelled, voided, closed)
	order by
		ifnull((
			select max(od.sales_check_date)						-- sales_check_date is in EST
			from shc_order od 
			where od.so_id = so.so_id
		), so.created_date + interval 1 hour)
	limit 10;
	
	commit;
	
	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;


--
-- Update queue: Active - Greater than 72 hours
--
drop procedure if exists sp_wfm_queue_mm_active;
create procedure sp_wfm_queue_mm_active(v_company_id int)
begin
	declare v_queue_id int default 16;

	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.buyer_id not in (1000)
		and so.wf_state_id = 155										-- state=active
		and so.activated_date + interval 72 hour < now()
	order by so.activated_date;
	
	commit;
	
	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;


--
-- Update queue: Completed - Greater than 72 hours
--
drop procedure if exists sp_wfm_queue_mm_completed;
create procedure sp_wfm_queue_mm_completed(v_company_id int)
begin
	declare v_queue_id int default 17;

	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.buyer_id not in (1000, 1085)
		and so.wf_state_id = 160										-- state=completed
		and so.completed_date + interval 72 hour < now()
	order by so.completed_date;
	
	commit;
	
	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);

end;


--
-- Update queue: Problem - Greater than 72 hours
--
drop procedure if exists sp_wfm_queue_mm_problem;
create procedure sp_wfm_queue_mm_problem(v_company_id int)
begin
	declare v_queue_id int default 18;

	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.buyer_id not in (1000, 1085)
		and so.wf_state_id = 170										-- state=problem
		and so.problem_date + interval 72 hour < now()
	order by so.problem_date;
	
	commit;
	
	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;


--
-- Update queue: Poor Rating Review
--
drop procedure if exists sp_wfm_queue_mm_poor_rating;
create procedure sp_wfm_queue_mm_poor_rating(v_company_id int)
begin
	declare v_queue_id int default 19;

	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.wf_state_id = 180										-- state=closed
		and exists (
			select 1
			from survey_response_so res, survey_response_hdr hdr
			where so.so_id = res.so_id and res.response_hdr_id = hdr.response_hdr_id
				and hdr.overall_score <= 3.0
				and hdr.reviewed_ind = 0
		)
	order by so.closed_date;
	
	-- mark the surveys as 'to be reviewed'
	update survey_response_hdr
	set reviewed_ind = 1
	where response_hdr_id in (
		select res.response_hdr_id 
		from survey_response_so res, z_work_wfm_so_queues z
		where res.so_id = z.so_id and z.queue_id = v_queue_id
	);
	
	commit;
	
	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, false);
	
end;


--
-- SEARS QUEUES
--

--
-- Update queue: Contact Customer to Manage Expectation
--
drop procedure if exists sp_wfm_queue_call_customer;
create procedure sp_wfm_queue_call_customer(v_company_id int)
begin
	declare v_queue_id int default 31;

	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id and company_id = v_company_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.buyer_id = v_company_id
		and ifnull((
			select max(od.sales_check_date)						-- sales_check_date is in EST
			from shc_order od 
			where od.so_id = so.so_id
		), so.created_date + interval 1 hour) + interval 23 hour < now()
		and (
			so.wf_state_id in (100, 110)										-- state=draft,routed
			or
			(so.wf_state_id in (150, 155) and so.so_substatus_id = 50)			-- state=accepted,active/scheduling needed
		)
	order by
		ifnull((
			select max(od.sales_check_date)						-- sales_check_date is in EST
			from shc_order od 
			where od.so_id = so.so_id
		), so.created_date + interval 1 hour);
	
	commit;
	
	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;

--
-- Update queue: Missing Information
--
drop procedure if exists sp_wfm_queue_missing_information;
create procedure sp_wfm_queue_missing_information(v_company_id int)
begin
	declare v_queue_id int default 32;

	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id and company_id = v_company_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.buyer_id = v_company_id
		and so.wf_state_id = 100 and so.so_substatus_id = 36		-- state=draft/missing information
	order by
		ifnull((
			select max(od.sales_check_date)						-- sales_check_date is in EST
			from shc_order od 
			where od.so_id = so.so_id
		), so.created_date + interval 1 hour);
	
	commit;
	
	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;

--
-- Update queue: Find Provider to Post
--
drop procedure if exists sp_wfm_queue_find_provider;
create procedure sp_wfm_queue_find_provider(v_company_id int)
begin
	declare v_queue_id int default 33;

	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id and company_id = v_company_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.buyer_id = v_company_id
		and so.wf_state_id = 100 and so.so_substatus_id = 56		-- state=draft/no provider
	order by 
		ifnull((
			select max(od.sales_check_date)						-- sales_check_date is in EST
			from shc_order od 
			where od.so_id = so.so_id
		), so.created_date + interval 1 hour);
	
	commit;
	
	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;

--
-- Update queue: Ensure all Pros Acknowledge the SO
--
drop procedure if exists sp_wfm_queue_ensure_pros_acknowledge;
create procedure sp_wfm_queue_ensure_pros_acknowledge(v_company_id int)
begin
	declare v_queue_id int default 34;

	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id and company_id = v_company_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.buyer_id = v_company_id
		and so.wf_state_id = 110 									-- state=routed 
		and so.routed_date + interval 6 hour < now()
	order by fn_service_start_date_in_server_tz(so.so_id);
	
	commit;
	
	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;

--
-- Update queue: Repost the SO
--
drop procedure if exists sp_wfm_queue_repost;
create procedure sp_wfm_queue_repost(v_company_id int)
begin
	declare v_queue_id int default 35;

	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id and company_id = v_company_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.buyer_id = v_company_id
		and (
			so.wf_state_id = 130 									-- state=expired 
			or (
				so.wf_state_id = 110								-- state=posted
				and not exists (
					select 1
					from so_routed_providers p
					where p.so_id = so.so_id
						and (p.provider_resp_id is null or p.provider_resp_id <> 3)		-- not rejected
				)
			)
		)
	order by so.expired_date;
	
	commit;
	
	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;

--
-- Update queue: Respond to Counter Offers
--
drop procedure if exists sp_wfm_queue_respond_counter_offers;
create procedure sp_wfm_queue_respond_counter_offers(v_company_id int)
begin
	declare v_queue_id int default 36;

	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id and company_id = v_company_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.buyer_id = v_company_id
		and so.wf_state_id = 110 								-- state=posted 
		and exists (
			select 1
			from so_routed_providers p
			where so.so_id = p.so_id 
				and p.provider_resp_id = 2						-- conditional offer
		)
		and (
			exists (
				select 1
				from so_routed_providers p
				where so.so_id = p.so_id 
					and p.provider_resp_id = 2					-- conditional offer
					and fn_convert_to_server_tz(p.offer_expiration_date, 'GMT') < now() + interval 2 hour
			)
			or not exists (
				select 1
				from so_routed_providers p
				where p.so_id = so.so_id
					and (p.provider_resp_id is null or p.provider_resp_id not in (2, 3))	-- not conditional/rejected
			)
			or fn_service_start_date_in_server_tz(so.so_id) < now() + interval 12 hour
		)
	order by fn_service_start_date_in_server_tz(so.so_id);
	
	commit;
	
	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;

--
-- Update queue: First Time Scheduling Needed
--
drop procedure if exists sp_wfm_queue_first_scheduling;
create procedure sp_wfm_queue_first_scheduling(v_company_id int)
begin
	declare v_queue_id int default 37;
	
	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id and company_id = v_company_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.buyer_id = v_company_id
		and so.wf_state_id in (150, 155) and so.so_substatus_id = 50		-- state=accepted,active/scheduling needed
		and ifnull((
			select max(od.sales_check_date)						-- sales_check_date is in EST
			from shc_order od 
			where od.so_id = so.so_id
		), so.created_date + interval 1 hour) + interval 23 hour < now()
	order by fn_service_start_date_in_server_tz(so.so_id);
	
	commit;

	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;

--
-- Update queue: Reschedule Service Window
--
drop procedure if exists sp_wfm_queue_reschedule;
create procedure sp_wfm_queue_reschedule(v_company_id int)
begin
	declare v_queue_id int default 38;

	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id and company_id = v_company_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.buyer_id = v_company_id
		and (
			(so.wf_state_id in (150, 155) and so.so_substatus_id in (22,23))		-- state=accepted,active/rescheduled by end user,provider
			or
			(so.wf_state_id = 155 and so.so_substatus_id in (24,25))				-- state=active/rescheduled due to end user,provider no show
		)
	order by fn_service_start_date_in_server_tz(so.so_id);
	
	commit;
	
	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;

--
-- Update queue: Time Window Expired - Arrival
--
drop procedure if exists sp_wfm_queue_expired_arrival;
create procedure sp_wfm_queue_expired_arrival(v_company_id int)
begin
	declare v_queue_id int default 39;

	--
	-- update substate
	--
	update so_hdr 
	set so_substatus_id = 57
	where 
		buyer_id = v_company_id 
		and wf_state_id = 155 
		and (so_substatus_id is null or so_substatus_id not in (8, 20, 37, 59))		-- state=active/not (job done,provider on-site,cancellation request,validate POS cancellation)
		and fn_service_end_date_in_server_tz(so_id) < now();
	
	commit;

	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id and company_id = v_company_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.buyer_id = v_company_id
		and so.wf_state_id = 155 and so.so_substatus_id = 57		-- state=active/time window expired - arrival
	order by fn_service_start_date_in_server_tz(so.so_id);
	
	commit;
	
	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;

--
-- Update queue: Resolution Required
--
drop procedure if exists sp_wfm_queue_resolution_required;
create procedure sp_wfm_queue_resolution_required(v_company_id int)
begin
	declare v_queue_id int default 40;

	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id and company_id = v_company_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.buyer_id = v_company_id
		and so.wf_state_id = 170 									-- state=problem
	order by so.problem_date;
	
	commit;
	
	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;

--
-- Update queue: Time Window Expired - Departure
--
drop procedure if exists sp_wfm_queue_expired_departure;
create procedure sp_wfm_queue_expired_departure(v_company_id int)
begin
	declare v_queue_id int default 41;

	--
	-- update substate
	--
	update so_hdr so
	set so.so_substatus_id = 58
	where 
		so.buyer_id = v_company_id 
		and so.wf_state_id = 155 and so.so_substatus_id = 20		-- state=active/provider on-site
		and (
			select max(lo.modified_date) 
			from so_logging lo 
			where lo.so_id = so.so_id and lo.chg_comment = 'Service Order Sub Status Changed to Provider On-site'
		) + interval 6 hour < now();
		
	commit;

	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id and company_id = v_company_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.buyer_id = v_company_id
		and so.wf_state_id = 155 and so.so_substatus_id = 58		-- state=active/time window expired - departure 
	order by
		(
			select max(lo.modified_date) 
			from so_logging lo 
			where lo.so_id = so.so_id and lo.chg_comment = 'Service Order Sub Status Changed to Provider On-site'
		);
	
	commit;
	
	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;

--
-- Update queue: Assist Provider w Documentation
--
drop procedure if exists sp_wfm_queue_documentation;
create procedure sp_wfm_queue_documentation(v_company_id int)
begin
	declare v_queue_id int default 42;

	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id and company_id = v_company_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.buyer_id = v_company_id
		and (
			(
				so.wf_state_id = 155 and so.so_substatus_id = 8	-- state=active/job done
				and (
					select max(lo.modified_date) 
					from so_logging lo 
					where lo.so_id = so.so_id and lo.chg_comment = 'Service Order Sub Status Changed to Job Done'
				) + interval 24 hour < now()
			)
			or
			(so.wf_state_id = 160 and so.so_substatus_id = 35)	-- state=completed/awaiting documentation
		)
	order by 
		if(so.completed_date is null, '9999-12-31 23:59:59', so.completed_date),
		(
			select max(lo.modified_date) 
			from so_logging lo 
			where lo.so_id = so.so_id and lo.chg_comment = 'Service Order Sub Status Changed to Job Done'
		);
	
	commit;
	
	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;

--
-- Update queue: Audit & Approve for Payment
--
drop procedure if exists sp_wfm_queue_awaiting_payment;
create procedure sp_wfm_queue_awaiting_payment(v_company_id int)
begin
	declare v_queue_id int default 43;
	
	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id and company_id = v_company_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.buyer_id = v_company_id
		and so.wf_state_id = 160 and so.so_substatus_id = 34	-- state=completed/awaiting payment
	order by so.completed_date;
	
	commit;

	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;

--
-- Update queue: Cancelled
--
drop procedure if exists sp_wfm_queue_cancelled;
create procedure sp_wfm_queue_cancelled(v_company_id int)
begin
	declare v_queue_id int default 44;
	
	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id and company_id = v_company_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.buyer_id = v_company_id
		and so.wf_state_id = 155 and so.so_substatus_id = 37		-- state=active/cancellation request 
	order by
		(
			select max(lo.modified_date) 
			from so_logging lo 
			where lo.so_id = so.so_id and lo.chg_comment like 'Buyer is requesting cancellation of Service Order%'
		);
	
	commit;

	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;

--
-- Update queue: Validate POS Cancellation
--
drop procedure if exists sp_wfm_queue_validate_cancellation;
create procedure sp_wfm_queue_validate_cancellation(v_company_id int)
begin
	declare v_queue_id int default 45;
	
	--
	-- fetch SOs for the queue
	--
	delete from z_work_wfm_so_queues where queue_id = v_queue_id and company_id = v_company_id;
	
	set @num := 0;
	
	insert into z_work_wfm_so_queues (company_id, queue_id, so_id, queued_date, created_date, modified_date, grouped_ind, so_group_id, sort_order)
	select 
		v_company_id as company_id,
		v_queue_id as queue_id,
		so.so_id as so_id,
		now() as queued_date,
		now() as created_date,
		now() as modified_date,
		if(so.so_group_id is null, 0, 1) as grouped_ind,
		so.so_group_id as so_group_id,
		@num := @num + 1 num
	from so_hdr so
	where
		so.buyer_id = v_company_id
		and so.wf_state_id in (150, 155) and so.so_substatus_id = 59		-- state=accepted,active/validate pos cancellation
	order by so.created_date;
	
	commit;

	--
	-- update the queue
	--
	call sp_wfm_queue_update_by_queue_company(v_queue_id, v_company_id, true);
	
end;


//
delimiter ;
