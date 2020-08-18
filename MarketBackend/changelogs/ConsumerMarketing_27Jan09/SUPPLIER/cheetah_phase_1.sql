insert into lu_action_master values(225,'processAcceptServiceOrder',NULL,'Accepting a Service Order',NULL,current_timestamp,current_timestamp,'nkurian');
insert into lu_aop_action_assoc values(525,1,225,current_timestamp,current_timestamp,'nkurian');
insert into lu_aop_action_target_assoc values(265,525,NULL,25,current_timestamp,current_timestamp,'nkurian');

insert into lu_action_master values(226,'reportResolution',NULL,'Issue Resolution',NULL,current_timestamp,current_timestamp,'kalapu');
insert into lu_aop_action_assoc values(526,1,226,current_timestamp,current_timestamp,'kalapu');
insert into lu_aop_action_target_assoc values(266,526,NULL,32,current_timestamp,current_timestamp,'kalapu');

insert into lu_action_master values(227,'releaseServiceOrderInAccepted',NULL,'Release service order in the accepted state',NULL,current_timestamp,current_timestamp,'kpalani');
insert into lu_aop_action_assoc values(527,1,227,current_timestamp,current_timestamp,'kpalani');
insert into lu_aop_action_target_assoc values(267,527,NULL,64,current_timestamp,current_timestamp,'kpalani');

insert into lu_action_master values(228,'releaseServiceOrderInActive',NULL,'Release service order in the active state',NULL,current_timestamp,current_timestamp,'rcheria');
insert into lu_aop_action_assoc values(528,1,228,current_timestamp,current_timestamp,'rcheria');
insert into lu_aop_action_target_assoc values(268,528,NULL,66,current_timestamp,current_timestamp,'rcheria');

insert into lu_action_master values(229,'processReRouteSO',NULL,'Buyer posts Service Order',NULL,current_timestamp,current_timestamp,'lpaul4');
insert into lu_aop_action_assoc values(529,1,229,current_timestamp,current_timestamp,'lpaul4');
insert into lu_aop_action_target_assoc values(269,529,NULL,213,current_timestamp,current_timestamp,'lpaul4');

insert into lu_action_master values(230,'sendAllProviderRejectAlert',NULL,'Service order rejected by all Providers',NULL,current_timestamp,current_timestamp,'ssubha');
insert into lu_aop_action_assoc values(530,1,230,current_timestamp,current_timestamp,'ssubha');
insert into lu_aop_action_target_assoc values(270,530,NULL,24,current_timestamp,current_timestamp,'ssubha');

insert into lu_action_master values(231,'saveSimpleBuyerRegistration',NULL,'Simple Buyer Registration',NULL,current_timestamp,current_timestamp,'lmundad');
insert into lu_aop_action_assoc values(531,1,231,current_timestamp,current_timestamp,'lmundad');
insert into lu_aop_action_target_assoc values(271,531,NULL,55,current_timestamp,current_timestamp,'lmundad');

insert into lu_action_master values(232,'processSupportAddNote',NULL,'Support Note added',NULL,current_timestamp,current_timestamp,'pangeen');
insert into lu_aop_action_assoc values(532,1,232,current_timestamp,current_timestamp,'pangeen');
insert into lu_aop_action_target_assoc values(272,532,NULL,75,current_timestamp,current_timestamp,'pangeen');

insert into lu_action_master values(233,'sendForgotUsernameMail',NULL,'ForgotUser Name Request',NULL,current_timestamp,current_timestamp,'cnair');
insert into lu_aop_action_assoc values(533,1,233,current_timestamp,current_timestamp,'cnair');
insert into lu_aop_action_target_assoc values(273,533,NULL,16,current_timestamp,current_timestamp,'cnair');

insert into lu_action_master values(234,'validateAns',NULL,'Reset Password Request',NULL,current_timestamp,current_timestamp,'rthalia');
insert into lu_aop_action_assoc values(534,1,234,current_timestamp,current_timestamp,'rthalia');
insert into lu_aop_action_target_assoc values(274,534,NULL,74,current_timestamp,current_timestamp,'rthalia');


insert into lu_action_master values(235,'rejectServiceOrder',NULL,'Provider Rejects the service order',NULL,current_timestamp,current_timestamp,'rthalia');
insert into lu_aop_action_assoc values(535,1,235,current_timestamp,current_timestamp,'rthalia');
insert into lu_aop_action_target_assoc values(275,535,NULL,39,current_timestamp,current_timestamp,'rthalia');

update template set eid = 37203  where template_id = 213;



insert into lu_action_master values(236,'processRouteSO',NULL,'Simple Buyer posts the service order',NULL,current_timestamp,current_timestamp,'kpalani');
insert into lu_aop_action_assoc values(536,1,236,current_timestamp,current_timestamp,'kpalani');
insert into lu_aop_action_target_assoc values(276,536,NULL,213,current_timestamp,current_timestamp,'kpalani');


insert into lu_action_master values (237, 'saveProfBuyerRegistration', null, 'Proffessional Buyer Registration', null, current_timestamp, current_timestamp, 'KPalani');
insert into lu_aop_action_assoc values (537, 1, 237, current_timestamp, current_timestamp, 'KPalani');
insert into lu_aop_action_target_assoc values (277, 537, null, 94, current_timestamp, current_timestamp, 'KPalani');
 
insert into lu_action_master values (238, 'processSOAddNote', null, 'Service Order Add Note', null, current_timestamp, current_timestamp, 'KPalani');
insert into lu_aop_action_assoc values (538, 1, 238, current_timestamp, current_timestamp, 'KPalani');
insert into lu_aop_action_target_assoc values (278, 538, null, 13, current_timestamp, current_timestamp, 'KPalani');
 
insert into lu_aop_action_role_assoc values(231,539,'SL','B',NULL,NULL,current_timestamp,current_timestamp,'KPalani')

