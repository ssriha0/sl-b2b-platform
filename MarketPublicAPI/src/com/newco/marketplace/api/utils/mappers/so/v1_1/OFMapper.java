package com.newco.marketplace.api.utils.mappers.so.v1_1;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.serviceorder.BuyerResource;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification.EntityType;

public class OFMapper {

	public static Identification createBuyerIdentFromSecCtx(SecurityContext securityContext) {
		Identification id = new Identification();
		id.setEntityType(EntityType.BUYER);
		id.setCompanyId((long)securityContext.getCompanyId());
		id.setResourceId((long)securityContext.getVendBuyerResId());
		id.setUsername(securityContext.getUsername());
		id.setFullname(securityContext.getRoles().getFirstName()+" "+securityContext.getRoles().getLastName());
		id.setRoleId(securityContext.getRoleId());
		return id;
	}
	
	public static Identification createProviderIdentFromSecCtx(SecurityContext securityContext) {
        Identification id = new Identification();
        id.setEntityType(EntityType.PROVIDER);
        id.setCompanyId((long)securityContext.getCompanyId());
        id.setResourceId((long)securityContext.getVendBuyerResId());
        id.setUsername(securityContext.getUsername());
        id.setFullname(securityContext.getRoles().getFirstName()+" "+securityContext.getRoles().getLastName());
        id.setRoleId(securityContext.getRoleId());
        return id;
    }
	
	public static Identification createBuyerIdentFromSecCtxResource(SecurityContext securityContext,BuyerResource buyerResource,Contact contact) {
		Identification id = new Identification();
		id.setEntityType(EntityType.BUYER);
		id.setCompanyId((long)securityContext.getCompanyId());
		if(null!=buyerResource.getResourceId()){
			id.setResourceId((long)buyerResource.getResourceId());
		}else{
			 id.setResourceId((long)securityContext.getVendBuyerResId());
		}
		if(StringUtils.isNotBlank(buyerResource.getUserName())){
			id.setUsername(buyerResource.getUserName());
		}else{
			 id.setUsername(securityContext.getUsername());
		}
		if(null!=contact && null!= contact.getFirstName() && null!=contact.getLastName()){
			id.setFullname(contact.getFirstName()+" "+contact.getLastName());
		}else{
			id.setFullname(securityContext.getRoles().getFirstName()+" "+securityContext.getRoles().getLastName());
		}
		if(null!= buyerResource.getCompanyRoleId()){
			id.setRoleId(buyerResource.getCompanyRoleId());
		}else{
			 id.setRoleId(securityContext.getRoleId());
		}
		return id;
	}
}
