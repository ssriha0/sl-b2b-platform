DELIMITER $$

DROP VIEW IF EXISTS vw_spnet_provider_match$$

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
coalesce(lpi.primary_industry_id,_latin1'-1') AS primaryIndustryId,
coalesce(lpi.descr,_latin1'NA') AS primaryIndustry,
ctres.phone_no AS phone,
ctres.email AS email,
ctres.email_alt AS email_alt,
ctres.first_name AS firstName,
ctres.last_name AS lastName,
coalesce(locn.city,_latin1'NA') AS city,
coalesce(lzip.zip,_latin1'NA') AS zip,
coalesce(locn.state_cd,_latin1'NA') AS dispatchLocState,
coalesce(mkt.market_id,_latin1'-1') AS dispatchLocMarketId,
coalesce(mkt.market_name,_latin1'NA') AS dispatchLocMarketName,
vrs.modified_date AS lastActivityDate,
vrs.created_date AS signUpdate,
coalesce(dzip.region,_latin1'-1') AS regionId,
coalesce(dzip.ri_district,_latin1'-1') AS destrictId
from
(
   (
      (
         (
            (
               (
                  (
                     (
                        (
                           (
                              vendor_resource vrs join vendor_hdr vh on
                              (
                                 (vrs.vendor_id = vh.vendor_id)
                              )
                           )
                           left join user_profile up on
                           (
                              (vrs.user_name = up.user_name)
                           )
                        )
                        left join user_roles ur on
                        (
                           (ur.role_id = up.role_id)
                        )
                     )
                     join wf_states wf on
                     (
                        (vrs.wf_state_id = wf.wf_state_id)
                     )
                  )
                  left join lu_primary_industry lpi on
                  (
                     (lpi.primary_industry_id = vh.primary_industry_id)
                  )
               )
               left join location locn on((locn.locn_id = vrs.locn_id) AND locn.locn_type_id = 4 )
            )
            left join lu_zip_market lzip on((locn.zip = lzip.zip))
         )
         left join lu_market mkt on((lzip.market_id = mkt.market_id))
      )
      left join contact ctres on((ctres.contact_id = vrs.contact_id))
   )
   left join region_district_zip dzip on((dzip.zip = lzip.zip))
)$$
DELIMITER ;