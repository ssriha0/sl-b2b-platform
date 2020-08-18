ALTER TABLE lu_permission_set
ADD admin_ind SMALLINT DEFAULT '0' AFTER sort_order;

insert into lu_permission_set (permission_set_id,role_id,permission_set_name,sort_order,admin_ind) values (32,2,'Admin Only Actions-Newco',1,1);
insert into lu_permission_set (permission_set_id,role_id,permission_set_name,sort_order,admin_ind) values (33,1,'Admin Only Actions-Provider',1,1);
insert into lu_permission_set (permission_set_id,role_id,permission_set_name,sort_order,admin_ind) values (34,3,'Admin Only Actions-Enterprise Buyer',1,1);
insert into lu_permission_set (permission_set_id,role_id,permission_set_name,sort_order,admin_ind) values (35,5,'Admin Only Actions-Simple Buyer',1,1);

insert into lu_permission_role (role_activity_id,role_id,activity_id,sort_order,created_date,modified_date,modified_by,permission_set_id)
values(83,2,5,1,NOW(),NOW(),'paugus2',32);

insert into lu_permission_role (role_activity_id,role_id,activity_id,sort_order,created_date,modified_date,modified_by,permission_set_id)
values(84,1,5,1,NOW(),NOW(),'paugus2',33);

insert into lu_permission_role (role_activity_id,role_id,activity_id,sort_order,created_date,modified_date,modified_by,permission_set_id)
values(85,3,5,1,NOW(),NOW(),'paugus2',34);

insert into lu_permission_role (role_activity_id,role_id,activity_id,sort_order,created_date,modified_date,modified_by,permission_set_id)
values(86,5,5,1,NOW(),NOW(),'paugus2',35);

insert into user_profile_permissions (user_name,role_activity_id,created_date,modified_date,modified_by) (select user_name,84, NOW(),NOW(),'paugus2' from user_profile_permissions where role_activity_id = 5);

insert into user_profile_permissions (user_name,role_activity_id,created_date,modified_date,modified_by) (select user_name,83, NOW(),NOW(),'paugus2' from user_profile_permissions where role_activity_id = 18);

insert into user_profile_permissions (user_name,role_activity_id,created_date,modified_date,modified_by) (select user_name,85, NOW(),NOW(),'paugus2' from user_profile_permissions where role_activity_id = 42);

insert into user_profile_permissions (user_name,role_activity_id,created_date,modified_date,modified_by) (select user_name,86, NOW(),NOW(),'paugus2' from user_profile_permissions where role_activity_id = 55);

insert into lu_permission_role (role_activity_id,role_id,activity_id,sort_order,created_date,modified_date,modified_by,permission_set_id)
values (83,2,5,1,NOW(),NOW(),'paugus2',32);

delete from user_profile_permissions where role_activity_id in (5,18,42,55);

delete from lu_permission_role where role_activity_id in (5,18,42,55);
