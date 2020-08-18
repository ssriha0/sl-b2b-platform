drop view vw_spnet_providerfirm_summary;

drop view vw_spnet_serviceprovider_summary;

DELIMITER $$
create view vw_spnet_providerfirm_summary as 
select 
sh.spn_id as spnId,
pfs.provider_wf_state as providerFirmState,
pfs.provider_firm_id as providerFirmId,
b.buyer_id as buyerId,
sh.is_alias as isAlias
from 
spnet_provider_firm_state pfs 
join spnet_hdr sh on (pfs.spn_id = sh.spn_id and sh.is_alias = 0)
join vendor_hdr vh on vh.vendor_id = pfs.provider_firm_id 
join spnet_buyer b on ( b.spn_id = pfs.spn_id)

UNION ALL

select 
sh2.spn_id as spnId,
pfs.provider_wf_state as providerFirmState, 
pfs.provider_firm_id as providerFirmId,
b.buyer_id as buyerId,
sh.is_alias as isAlias
from 
spnet_provider_firm_state pfs 
join spnet_hdr sh on (pfs.spn_id = sh.spn_id and sh.is_alias = 1)
join spnet_hdr sh2 on (sh.alias_original_spn_id = sh2.spn_id)
join vendor_hdr vh on vh.vendor_id = pfs.provider_firm_id 
join spnet_buyer b on ( b.spn_id = pfs.spn_id)
$$


create view vw_spnet_serviceprovider_summary as 
select 
sh.spn_id as spnId,
pfs.provider_firm_id as providerFirmId,
vr.resource_id as serviceProviderId,
b.buyer_id as buyerId,
sh.is_alias as isAlias,
vr.wf_state_id,
vr.resource_ind,
pfs.provider_wf_state as providerFirmState,
sss.provider_wf_state as serviceProviderState
from 
spnet_serviceprovider_state sss 
join vendor_resource vr on (sss.service_provider_id = vr.resource_id)
join spnet_provider_firm_state pfs on (pfs.spn_id = sss.spn_id and pfs.provider_firm_id = vr.vendor_id)
join spnet_hdr sh on (sss.spn_id = sh.spn_id and sh.is_alias = 0)
join spnet_buyer b on ( sh.spn_id = b.spn_id)

UNION ALL

select 
sh2.spn_id as spnId,
pfs.provider_firm_id as providerFirmId,
vr.resource_id as serviceProviderId,
b.buyer_id as buyerId,
sh.is_alias as isAlias,
vr.wf_state_id,
vr.resource_ind,
pfs.provider_wf_state as providerFirmState,
sss.provider_wf_state as serviceProviderState
from 
spnet_serviceprovider_state sss 
join vendor_resource vr on (sss.service_provider_id = vr.resource_id)
join spnet_provider_firm_state pfs on (pfs.spn_id = sss.spn_id and pfs.provider_firm_id = vr.vendor_id)
join spnet_hdr sh on (sss.spn_id = sh.spn_id and sh.is_alias = 1)
join spnet_hdr sh2 on (sh2.spn_id = sh.alias_original_spn_id)
join spnet_buyer b on ( sh.spn_id = b.spn_id)



 $$
DELIMITER ;


