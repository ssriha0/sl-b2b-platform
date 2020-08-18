insert into lu_so_substatus (so_substatus_id, descr, sort_order, created_date, modified_date, modified_by)
values (55, 'Close and Pay / Completion Record - Issue', 55, '2008-11-12', null, 'awadhwa');

insert into so_wf_substatus (wf_state_id,so_substatus_id,sort_order,created_date,modified_date,modified_by)
values (170, 55, 14, '2008-11-12', null, 'awadhwa');

