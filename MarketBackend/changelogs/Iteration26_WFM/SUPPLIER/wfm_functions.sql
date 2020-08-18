delimiter //

--
-- Convert date to server timezone (which is hardwired to be 'CST6CDT')
--
drop function if exists fn_convert_to_server_tz;
create function fn_convert_to_server_tz(date_to_convert datetime, timezone varchar(50)) 
	returns datetime deterministic
begin
	declare date_converted datetime;
	
	set date_converted := convert_tz(date_to_convert, timezone, 'CST6CDT');
	if date_converted is null then
		set date_converted := date_to_convert;
	end if;
	
	return date_converted;
end;


--
-- Return service window start date in server timezone
--
drop function if exists fn_service_start_date_in_server_tz;
create function fn_service_start_date_in_server_tz(v_so_id varchar(30)) 
	returns datetime deterministic
begin
	declare v_date datetime;
	declare v_time time;
	declare v_timezone varchar(50);

	select 
		if(so.resched_service_date1 is null, so.service_date1, so.resched_service_date1), 
		if(so.resched_service_date1 is null, lu.full_time, lu2.full_time),
		so.service_locn_time_zone
		into v_date, v_time, v_timezone
	from so_hdr so 
		left join lu_time_interval lu on so.service_time_start = lu.descr
		left join lu_time_interval lu2 on so.resched_service_time_start = lu2.descr
	where so_id = v_so_id;

	return fn_convert_to_server_tz(addtime(v_date, v_time), 'GMT');
end;


--
-- Return service window end date in server timezone
--
drop function if exists fn_service_end_date_in_server_tz;
create function fn_service_end_date_in_server_tz(v_so_id varchar(30)) 
	returns datetime deterministic
begin
	declare v_date datetime;
	declare v_time time;
	declare v_timezone varchar(50);
	
	select 
		if(so.resched_service_date1 is null, 
			if(so.service_date_type_id = 1, so.service_date1, so.service_date2), 
			if(so.resched_date_type_id = 1, so.resched_service_date1, so.resched_service_date2)), 
		if(so.resched_service_date1 is null, 
			if(so.service_date_type_id = 1, lu_o_s.full_time, lu_o_e.full_time), 
			if(so.resched_date_type_id = 1, lu_r_s.full_time, lu_r_e.full_time)),
		so.service_locn_time_zone
		into v_date, v_time, v_timezone
	from so_hdr so 
		left join lu_time_interval lu_o_s on so.service_time_start = lu_o_s.descr
		left join lu_time_interval lu_r_s on so.resched_service_time_start = lu_r_s.descr
		left join lu_time_interval lu_o_e on so.service_time_end = lu_o_e.descr
		left join lu_time_interval lu_r_e on so.resched_service_time_end = lu_r_e.descr
	where so_id = v_so_id;

	return fn_convert_to_server_tz(addtime(v_date, v_time), 'GMT');
end;

//
delimiter ;
