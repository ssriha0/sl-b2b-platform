insert into lu_permission_set (permission_set_id,role_id,permission_set_name,sort_order) values ( 29,3,'Manage Select Provider Network',13);

insert into lu_permission_set (permission_set_id,role_id,permission_set_name,sort_order) values ( 30,2,'Manage Select Provider Network',13);

insert into lu_permissions ( activity_id , activity_name , inactive_ind , sort_order , descr , modified_by)
values (38,"Network Monitor",1,35,"Managing SPN Network ","rajithag"); 



insert into lu_permission_role (role_activity_id , role_id , activity_id , sort_order , modified_by , permission_set_id)
values (78,3,38,51,"rajithag",29); 


insert into lu_permission_role (role_activity_id , role_id , activity_id , sort_order , modified_by , permission_set_id)
values (79,2,38,51,"rajithag",30); 

insert into lu_permissions_action (role_id , activity_id , action_name , action_descr)
values (3,38,"spnLoginAction","SPN Login Action");


insert into user_profile_permissions (user_name, role_activity_id, created_date, modified_date, modified_by)
select user_name,78,now(),now(),'hoza'  from user_profile where contact_id in (select contact_id from contact_company_role where company_role_id in (select app_value from application_properties where app_key = 'sl_admin_super_admin_com_role_id'));