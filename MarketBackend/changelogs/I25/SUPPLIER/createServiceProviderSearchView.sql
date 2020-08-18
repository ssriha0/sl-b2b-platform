DELIMITER $$

DROP VIEW IF EXISTS `vw_searchportal_serviceprovider_search`$$

CREATE ALGORITHM=UNDEFINED DEFINER=`supply_usr`@`%` SQL SECURITY DEFINER VIEW `vw_searchportal_serviceprovider_search`

AS
 select  vrs.vendor_id as vendor_id,
                vrs.resource_id as resource_id,
                vrs.user_name as userName,
                vrs.background_state_id as backGroundStateId,
                vrs.wf_state_id as serviceProStateId,
                wf.wf_state as serviceProStatus,
		ur.role_id as roleId,
                ur.role_descr as roleType,
		vh.business_name as businessName,
                coalesce(lpi.primary_industry_id, '-1') as primaryIndustryId,
		coalesce(lpi.descr,'NA') as primaryIndustry,
		ctres.phone_no as phone,
		ctres.email as email,
		ctres.email_alt AS email_alt,
		ctres.first_name as firstName,
                ctres.last_name as lastName,
		coalesce(locn.city,'NA') as city,
		coalesce(lzip.zip,'NA') as zip,
		coalesce(locn.state_cd,'NA') as state,
                coalesce(mkt.market_id,'-1') as marketId,
		coalesce(mkt.market_name,'NA') as marketName,
		vrs.modified_date as lastActivityDate,
                vrs.created_date as signUpdate,
                coalesce(dzip.region,'-1') as regionId,
                coalesce(dzip.ri_district,'-1') as destrictId
		from vendor_resource  vrs
                join vendor_hdr vh on ( vrs.vendor_id = vh.vendor_id)
		left outer join user_profile up on (vrs.user_name = up.user_name)
		left outer join user_roles ur on ( ur.role_id = up.role_id)
                join wf_states wf on ( vrs.wf_state_id = wf.wf_state_id)
		left outer join lu_primary_industry lpi on ( lpi.primary_industry_id= vh.primary_industry_id)
		left outer join location locn on (locn.locn_id = vrs.locn_id)
		left outer join lu_zip_market lzip on (locn.zip = lzip.zip)
		left outer join lu_market mkt on (lzip.market_id = mkt.market_id )
		left outer join contact ctres on ( ctres.contact_id = vrs.contact_id)
                left outer join region_district_zip dzip on (dzip.zip = lzip.zip)
$$

DELIMITER ;