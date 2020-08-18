insert into lu_alert_role_master values ('B_OR_P', 'Buyer and Buyer Admin or Provider and Provider Admin', null);


delete from lu_aop_action_target_assoc where aop_action_target_id = 265;
delete from lu_aop_action_assoc where aop_action_id = 525;
delete from lu_action_master where action_id = 225;
update lu_aop_action_role_assoc set alert_to='BP',alert_cc='BA',alert_bcc='PA' where aop_action_id in
(select aop_action_id from lu_aop_action_target_assoc where template_id=25);


update lu_aop_action_role_assoc set alert_to='BP',alert_cc='BA',alert_bcc='PA' where aop_action_id in
(select aop_action_id from lu_aop_action_target_assoc where template_id=34);
 

update lu_aop_action_role_assoc set alert_to='B',alert_cc='BA',alert_bcc=NULL where aop_action_id in
(select aop_action_id from lu_aop_action_target_assoc where template_id=226);


delete from lu_aop_action_target_assoc where aop_action_target_id = 256;
delete from lu_aop_action_assoc where aop_action_id = 521;
update lu_aop_action_role_assoc set alert_to='B_OR_P',alert_cc=NULL,alert_bcc=NULL where aop_action_id in
(select aop_action_id from lu_aop_action_target_assoc where template_id=35);


delete from lu_aop_action_target_assoc where aop_action_target_id = 266;
delete from lu_aop_action_assoc where aop_action_id = 526;
delete from lu_action_master where action_id = 226;
update lu_aop_action_role_assoc set alert_to='BP',alert_cc='BA',alert_bcc='PA' where aop_action_id in
(select aop_action_id from lu_aop_action_target_assoc where template_id=32);

 
insert into lu_aop_action_role_assoc values (234,535,'SL','BP','BA','PA',NULL,NULL,'sajeev');


delete from lu_aop_action_target_assoc where aop_action_target_id = 267;
delete from lu_aop_action_assoc where aop_action_id = 527;
update lu_aop_action_role_assoc set alert_to='BP',alert_cc='BA',alert_bcc='PA' where aop_action_id in
(select aop_action_id from lu_aop_action_target_assoc where template_id=64);


insert into template values (251, 1, 'Released Service Order in Active Alert-Provider', 'Service Order $SO_ID was released by the Provider', '<html>
<head>
<style type="text/css">
            #email_body {font-family:Arial;
                        color: #505050;
                        font-size: 8.5pt;
                        font-weight: normal;
                        font-style: normal;
                        text-align: left;
                       background-color: #FFFFFF;
            }
</style>
</head>
<body>
<img src="cid:emailLogo"><br/><br/>
<div id="email_body">Service Order $SO_ID was released by the provider on $SO_RELEASE_DATE.
<br/><br/>Reason for release: $PROVIDER_COMMENT.
<br/><br/>
If you have questions, please contact us at:<br/><br/>
Email:contact@servicelive.com <br/>
ServiceLive, Inc. <br/>
1560 Cable Ranch Rd., Building A <br/>
Elm Building<br/>
San Antonio, TX 78245 <br/>
(888) 549 0640 <br/><br/>
Thank you!
</div>
</body>
</html>', null, current_timestamp, current_timestamp, 'kpalan4', null, 1, 55965);
update lu_aop_action_target_assoc set template_id = 251 where aop_action_target_id = 268;
insert into lu_aop_action_role_assoc values (235, 528, 'SL', 'AV', 'PA', null, current_timestamp, current_timestamp, 'kpalan4');


update lu_aop_action_role_assoc set alert_to='B',alert_cc='BA',alert_bcc=NULL where aop_action_id in
(select aop_action_id from lu_aop_action_target_assoc where template_id=66);


update lu_aop_action_role_assoc set alert_to='BP',alert_cc='BA',alert_bcc='PA' where aop_action_id in
(select aop_action_id from lu_aop_action_target_assoc where template_id=91);


update lu_aop_action_role_assoc set alert_to='BP',alert_cc='BA',alert_bcc='PA' where aop_action_id in
(select aop_action_id from lu_aop_action_target_assoc where template_id=26);


update lu_aop_action_role_assoc set alert_to='BP',alert_cc='BA',alert_bcc='PA' where aop_action_id in
(select aop_action_id from lu_aop_action_target_assoc where template_id=47);


update lu_aop_action_role_assoc set alert_to='B_OR_P',alert_cc=NULL,alert_bcc=NULL where aop_action_id in
(select aop_action_id from lu_aop_action_target_assoc where template_id=45);


insert into lu_aop_action_target_assoc values (280, 78, null, 73, current_timestamp, current_timestamp, 'kpalan4');
update lu_aop_action_role_assoc set alert_to='B',alert_cc='BA',alert_bcc=NULL where aop_action_id in
(select aop_action_id from lu_aop_action_target_assoc where template_id=73);


update lu_aop_action_target_assoc set template_id = 13 where aop_action_target_id = 82 and template_id = 75;
update lu_aop_action_role_assoc set alert_to='B_OR_P',alert_cc=NULL,alert_bcc=NULL where aop_action_id in
(select aop_action_id from lu_aop_action_target_assoc where template_id=13);
delete from lu_aop_action_target_assoc where aop_action_target_id = 278;
delete from lu_aop_action_assoc where aop_action_id = 538;
delete from lu_action_master where action_id = 238;


delete from lu_aop_action_target_assoc where aop_action_target_id = 276;
delete from lu_aop_action_assoc where aop_action_id = 536;
delete from lu_action_master where action_id = 236;


update lu_aop_action_role_assoc set alert_to='B',alert_cc='BA',alert_bcc=NULL where aop_action_id in
(select aop_action_id from lu_aop_action_target_assoc where template_id=90);


update lu_aop_action_role_assoc set alert_to='B',alert_cc='BA',alert_bcc=NULL where aop_action_id in
(select aop_action_id from lu_aop_action_target_assoc where template_id=24);
