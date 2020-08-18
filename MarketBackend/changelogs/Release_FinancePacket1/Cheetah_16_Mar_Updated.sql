delete from lu_aop_action_target_assoc where aop_action_target_id = 267;
delete from lu_aop_action_assoc where aop_action_id = 527;
update lu_aop_action_role_assoc set alert_to = 'BP', modified_date = current_timestamp, modified_by = 'kpalan4' where aop_role_assoc_id = 25;

update lu_aop_action_role_assoc set alert_to = 'BP', modified_date = current_timestamp, modified_by = 'kpalan4' where aop_role_assoc_id = 52;


update lu_aop_action_target_assoc set template_id = 13 where aop_action_target_id = 82 and template_id = 75;
insert into lu_alert_role_master values ('B_OR_P', 'Buyer and Buyer Admin or Provider and Provider Admin', null);
update lu_aop_action_role_assoc set alert_to = 'B_OR_P' where alert_to = 'SL_SUPPORT' and aop_role_assoc_id = 30;
