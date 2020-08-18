insert into lu_aop_action_role_assoc values(232,538,'SL','B',NULL,NULL,current_timestamp,current_timestamp,'KPalani');

insert into lu_alert_role_master values ('B_OR_P','Buyer or Provider',NULL);

update lu_aop_action_role_assoc set alert_to='B_OR_P',alert_cc='BA',alert_bcc='PA' where aop_action_id in
(select aop_action_id from lu_aop_action_target_assoc where template_id=13);

update lu_aop_action_role_assoc set alert_to='BP',alert_cc='BA',alert_bcc='PA' where aop_action_id in
(select aop_action_id from lu_aop_action_target_assoc where template_id=91);

update lu_aop_action_role_assoc set alert_to='BP',alert_cc='BA',alert_bcc='PA' where aop_action_id in
(select aop_action_id from lu_aop_action_target_assoc where template_id=64);

insert into lu_aop_action_role_assoc values(240,527,'SL', 'BP', 'BA', 'PA',current_timestamp,current_timestamp,'kalaspu');

