INSERT 	INTO account_hdr	(account_id, entity_type_id, entity_id, country_id,	 account_type_id, account_status_id,	 account_no, card_type_id,
card_expire_date,	 card_holder_name, created_date, modified_date, modified_by, active_ind, enabled_ind, bill_locn_id, default_ind	)
VALUES(1220020001, 1112111211121112, 1000, 0, 30, 10,"@q23123", 30,01022010, Test, 020210, 020309, Test, 1, 1, 1, 1)
	
INSERT INTO account_auth_resp (account_id, response, resp_date, authorized_ind, ansi_code, cid_code, addr_code)
VALUES	(1220020001,	 1112111211121112,	 "test",	 "090910", 1, 2020,	 2020,	 2020)

SELECT hdr.cardid,hdr.countryid,hdr.entityid,hdr.entityTypeId,hdr.encCardNo,hdr.accountTypeId,hdr.accountStatusId,hdr.cardTypeId,hdr.expireDate,
hdr.cardHolderName,hdr.locnTypeId,hdr.active_ind,hdr.enabled_ind,hdr.billingAddress1,hdr.billingAddress2,hdr.billingCity,hdr.billingState,
hdr.zipcode,resp.ansi_code,resp.authorized_ind FROM vw_buyer_creditcard_details hdr LEFT OUTER JOIN account_auth_resp resp 
on hdr.cardid=resp.account_id WHERE entityid=1000 and enabled_ind = 1

SELECT * from vw_buyer_creditcard_details WHERE entityId = 1000 and active_ind = 1 and default_ind =1
SELECT * from vw_buyer_creditcard_details	WHERE entityId = 1000 and active_ind = 1 and enabled_ind = 0
UPDATE account_hdr set default_ind = 1	WHERE account_id = 1220020001