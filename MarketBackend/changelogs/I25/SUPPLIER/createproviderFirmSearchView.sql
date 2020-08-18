DELIMITER $$

DROP VIEW IF EXISTS `vw_searchportal_providerfirm_search`$$

CREATE ALGORITHM=UNDEFINED DEFINER=`supply_usr`@`%` SQL SECURITY DEFINER VIEW `vw_searchportal_providerfirm_search`

AS
select  vh.vendor_id as vendor_id,
                vh.wf_state_id as proFirmStateId,
                wf.wf_state as proFirmStatus,
		vh.business_name as businessName,
                coalesce(lpi.primary_industry_id, '-1') as primaryIndustryId,
		coalesce(lpi.descr,'NA') as primaryIndustry,
                vh.modified_date as lastActivityDate,
                vh.created_date as signUpdate,
                vh.referral_id as referralId,
		coalesce(locn.city,'NA') as city,
		coalesce(lzip.zip,'NA') as zip,
		coalesce(locn.state_cd,'NA') as state,
                coalesce(mkt.market_id,'-1') as marketId,
		coalesce(mkt.market_name,'NA') as marketName,
		coalesce(ctres.phone_no,'NA') as phone,
		coalesce(ctres.email,'NA')  as email,
		ctres.email_alt AS email_alt,
                coalesce(vrs.resource_id,'-1') as adminId,
                coalesce(vrs.user_name, 'NA') as adminUserName,
		coalesce(ctres.first_name,'NA') as adminFirstName,
                coalesce(ctres.last_name,'NA') as admintLastName,
                coalesce(dzip.region,'-1') as regionId,
                coalesce(dzip.ri_district,'-1') as destrictId
              	from vendor_hdr vh
		join wf_states wf on ( vh.wf_state_id = wf.wf_state_id)
                join vendor_resource vrs on ( vh.vendor_id = vrs.vendor_id and vrs.primary_ind = 1 )
                join vendor_location vloc on ( vloc.vendor_id = vh.vendor_id )
                join location locn on (locn.locn_id = vloc.locn_id and locn.locn_type_id = 1)
                left outer join lu_primary_industry lpi on ( lpi.primary_industry_id= vh.primary_industry_id)
		left outer join lu_zip_market lzip on (locn.zip = lzip.zip)
		left outer join lu_market mkt on (lzip.market_id = mkt.market_id )
		left outer join region_district_zip dzip on (dzip.zip = lzip.zip)
                left outer join contact ctres on ( ctres.contact_id = vrs.contact_id)
$$

DELIMITER ;