DELIMITER //

--
-- maintains created_date/modified_date
--
DROP TRIGGER IF EXISTS wfm_so_queues_ins;
create trigger wfm_so_queues_ins BEFORE INSERT on wfm_so_queues 
for each row begin
	set NEW.created_date = NOW(), NEW.modified_date = NOW();
end;

DROP TRIGGER IF EXISTS wfm_so_queues_upd;
create trigger wfm_so_queues_upd BEFORE UPDATE on wfm_so_queues 
for each row begin
	set NEW.modified_date = NOW();
end;

DROP TRIGGER IF EXISTS wfm_tasks_ins;
create trigger wfm_tasks_ins BEFORE INSERT on wfm_tasks 
for each row begin
	set NEW.created_date = NOW(), NEW.modified_date = NOW();
end;

DROP TRIGGER IF EXISTS wfm_tasks_upd;
create trigger wfm_tasks_upd BEFORE UPDATE on wfm_tasks 
for each row begin
	set NEW.modified_date = NOW();
end;

--
-- makes archive
--
DROP TRIGGER IF EXISTS wfm_so_queues_ins_after;
create trigger wfm_so_queues_ins_after AFTER INSERT on wfm_so_queues
for each row begin
	insert into wfm_so_queues_archive(
		archive_date,
		archive_type,
		so_queue_id,
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
	values (
		now(),
		'INSERT',
		new.so_queue_id,
		new.company_id,
		new.queue_id,
		new.queue_seq,
		new.so_id,
		new.queued_date,
		new.claimed_ind,
		new.claimed_date,
		new.claimed_by_id,
		new.claimed_from_queue_id,
		new.unclaimed_date,
		new.completed_ind,
		new.created_date,
		new.modified_date,
		new.grouped_ind,
		new.so_group_id,
		new.note_id,
		new.sort_order
	);
end;

DROP TRIGGER IF EXISTS wfm_so_queues_upd_after;
create trigger wfm_so_queues_upd_after AFTER UPDATE on wfm_so_queues 
for each row begin
	insert into wfm_so_queues_archive(
		archive_date,
		archive_type,
		so_queue_id,
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
	values (
		now(),
		'UPDATE',
		new.so_queue_id,
		new.company_id,
		new.queue_id,
		new.queue_seq,
		new.so_id,
		new.queued_date,
		new.claimed_ind,
		new.claimed_date,
		new.claimed_by_id,
		new.claimed_from_queue_id,
		new.unclaimed_date,
		new.completed_ind,
		new.created_date,
		new.modified_date,
		new.grouped_ind,
		new.so_group_id,
		new.note_id,
		new.sort_order
	);
end;

DROP TRIGGER IF EXISTS wfm_so_queues_del_before;
create trigger wfm_so_queues_del_before BEFORE DELETE on wfm_so_queues 
for each row begin
	insert into wfm_so_queues_archive(
		archive_date,
		archive_type,
		so_queue_id,
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
	values (
		now(),
		'DELETE',
		old.so_queue_id,
		old.company_id,
		old.queue_id,
		old.queue_seq,
		old.so_id,
		old.queued_date,
		old.claimed_ind,
		old.claimed_date,
		old.claimed_by_id,
		old.claimed_from_queue_id,
		old.unclaimed_date,
		old.completed_ind,
		old.created_date,
		old.modified_date,
		old.grouped_ind,
		old.so_group_id,
		old.note_id,
		old.sort_order
	);
end;

//
DELIMITER ;
