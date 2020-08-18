delete from buyer_locations where buyer_id = (select buyer_id from buyer where user_name = 'testUser41'); 
delete from buyer_resource_locations where resource_id=(select resource_id from buyer_resource b where user_name = 'testUser41'); 
delete from user_profile_permissions where user_name = 'testUser41'; delete from buyer_resource where user_name = 'testUser41'; 
delete from location where locn_id = (select contact_id from buyer_resource where user_name='testUser41'); 
delete from contact where contact_id = (select contact_id from buyer_resource where user_name='testUser41'); 
delete from buyer where user_name = 'testUser41'; delete from user_profile where user_name = 'testUser41';