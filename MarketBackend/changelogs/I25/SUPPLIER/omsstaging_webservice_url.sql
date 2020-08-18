
-- this has to be run in QA, for production the address is differant

insert into application_properties (app_key , app_value, app_desc, created_date, modified_date, modified_by) 
values ('staging_ws_endpoint', 'http://151.149.116.196:8180/omsstaging/services/StageOrderSEI', 'webservice url for Staging SO', now(),
now(), 'Rajitha');

