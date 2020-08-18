insert into lu_permissions ( activity_id , activity_name , inactive_ind , sort_order , descr , modified_by)
values (39,"SPN Auditor",1,35,"Audit In Network Provider","rajithag"); 



insert into lu_permission_role (role_activity_id , role_id , activity_id , sort_order , modified_by , permission_set_id)
values (80,3,39,51,"rajithag",29); 


insert into lu_permission_role (role_activity_id , role_id , activity_id , sort_order , modified_by , permission_set_id)
values (81,2,39,51,"rajithag",30); 

insert into lu_permissions_action (role_id , activity_id , action_name , action_descr)
values (3,39,"spnAuditorController_route","SPN Auditor routecontroller  Action");


insert into user_profile_permissions (user_name, role_activity_id, created_date, modified_date, modified_by)
select user_name,81,now(),now(),'hoza'  from user_profile where contact_id in (select contact_id from contact_company_role where company_role_id in (select app_value from application_properties where app_key = 'sl_admin_super_admin_com_role_id'));


--insert into user_profile_permissions (user_name, role_activity_id, created_date, modified_date, modified_by)
--values ('buyer0021',80,now(),now(),'hoza');