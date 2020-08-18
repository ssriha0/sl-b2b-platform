DELIMITER $$

DROP VIEW IF EXISTS `vw_searchportal_buyerOnly_search`$$

CREATE ALGORITHM=UNDEFINED DEFINER=`supply_usr`@`%` SQL SECURITY DEFINER VIEW `vw_searchportal_buyerOnly_search`

AS
        select  distinct bh.buyer_id as buyer_id,
                brs.resource_id as buyer_resource_id,
                brs.user_name as userName,
		lftp.type as fundingType,
                ur.role_id as roleId,
                ur.role_descr as roleType,
		bh.business_name as businessName,
                bh.user_name as adminUserName,
		lpi.buyer_primary_industry_id as primaryIndustryId,
		lpi.descr as primaryIndustry,
		ct.phone_no as phone,
		ct.email as email,
		ct.email_alt AS email_alt,
		ct.first_name as adminFirstName,
		ct.last_name as admintLastName,
               	locn.city as city,
		lzip.zip as zip,
		locn.state_cd as state,
                mkt.market_id as marketId,
		mkt.market_name as marketName,
		brs.modified_date as lastActivityDate,
                bh.created_date as signUpdate
		from buyer bh
		join lu_funding_type lftp on ( lftp.funding_type_id =  bh.funding_type_id )
		left outer join lu_buyer_primary_industry lpi on ( lpi.buyer_primary_industry_id = bh.primary_industry_id)
		left outer join location locn on (locn.locn_id = bh.pri_locn_id)
		left outer join lu_zip_market lzip on (locn.zip = lzip.zip)
		left outer join lu_market mkt on (lzip.market_id = mkt.market_id )
		join contact ct on(ct.contact_id = bh.contact_id)
		left outer join so_hdr so on ( so.buyer_id = bh.buyer_id)
		join buyer_resource brs on ( brs.buyer_id = bh.buyer_id and brs.user_name = bh.user_name)
		join user_profile up on (brs.user_name = up.user_name)
		join user_roles ur on ( ur.role_id = up.role_id)
$$

DELIMITER ;