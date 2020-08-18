drop view vw_spnet_provider_match;

alter table vendor_resource add column user_name varchar(30) null;

alter table vendor_resource add column primary_ind integer null;

alter table vendor_resource add column background_state_id integer null;

alter table vendor_resource add column total_so_completed integer null;

alter table vendor_resource add column aggregate_rating_score double null;

alter table vendor_resource add column aggregate_rating_count integer null;

alter table vendor_resource add column hourly_rate  double null;

alter table vendor_resource add column resource_ind integer null;

alter table vendor_hdr add column ins_vehicle_liability_ind integer null;

alter table vendor_hdr add column ins_work_comp_ind integer null;

alter table vendor_hdr add column ins_gen_liability_ind integer null;

alter table vendor_hdr add column ins_vehicle_liability_amount double null;

alter table vendor_hdr add column ins_work_comp_amount double null;

alter table vendor_hdr add column ins_gen_liability_amount double null;

alter table vendor_hdr add column company_size_id integer null;

alter table vendor_hdr add column primary_industry_id integer null;

alter table vendor_resource add column locn_id integer null;


create table lu_zip_market (
zip varchar(10)  not null,
market_id integer not null
);

create table region_district_zip(
region integer not null,
prs_district integer not null,
ri_district integer not null,
zip varchar(10)
);

CREATE  VIEW vw_spnet_provider_match AS 
select
vrs.vendor_id AS vendor_id,
vrs.resource_id AS resource_id,
vrs.user_name AS userName,
vrs.background_state_id AS backGroundStateId,
vrs.wf_state_id AS serviceProServiceLiveStatusId,
vrs.primary_ind as isFirmAdmin,
vrs.total_so_completed AS totalSOcompleted,
vrs.aggregate_rating_count AS aggregateRatingCount,
vrs.aggregate_rating_score AS aggregateRatingScore,
vrs.hourly_rate AS hourlyRate,
vrs.resource_ind as resource_ind,
wf.wf_state AS serviceProServiceLiveStatus,
ur.role_id AS roleId,
ur.role_descr AS roleType,
vh.business_name AS businessName,
vh.ins_vehicle_liability_ind as autoLiabilityChecked,
vh.ins_work_comp_ind as workerCompChecked,
vh.ins_gen_liability_ind as generalLiabilityChecked, 
vh.ins_vehicle_liability_amount as autoLiabilityAmount, 
vh.ins_work_comp_amount as workerCompAmount, 
vh.ins_gen_liability_amount as generalLiabilityAmount ,
vh.company_size_id as providerFirmEmpoyeeSizeId,
coalesce(lpi.primary_industry_id,'-1') AS primaryIndustryId,
coalesce(lpi.descr,'NA') AS primaryIndustry,
ctres.phone_no AS phone,
ctres.email AS email,
ctres.email_alt AS email_alt,
ctres.first_name AS firstName,
ctres.last_name AS lastName,
coalesce(locn.city,'NA') AS city,
coalesce(lzip.zip,'NA') AS zip,
coalesce(locn.state_cd,'NA') AS dispatchLocState,
coalesce(mkt.market_id,'-1') AS dispatchLocMarketId,
coalesce(mkt.market_name,'NA') AS dispatchLocMarketName,
vrs.modified_date AS lastActivityDate,
vrs.created_date AS signUpdate,
coalesce(dzip.region,'-1') AS regionId,
coalesce(dzip.ri_district,'-1') AS destrictId
from
vendor_resource vrs join vendor_hdr vh on (vrs.vendor_id = vh.vendor_id)
left join user_profile up on  (vrs.user_name = up.user_name)
left join user_roles ur on (ur.role_id = up.role_id)
join wf_states wf on (vrs.wf_state_id = wf.wf_state_id)
left join lu_primary_industry lpi on  (lpi.primary_industry_id = vh.primary_industry_id)
left join location locn on(locn.locn_id = vrs.locn_id AND locn.locn_type_id = 4 )
left join lu_zip_market lzip on(locn.zip = lzip.zip)
left join lu_market mkt on(lzip.market_id = mkt.market_id)
left join contact ctres on(ctres.contact_id = vrs.contact_id)
left join region_district_zip dzip on(dzip.zip = lzip.zip);
