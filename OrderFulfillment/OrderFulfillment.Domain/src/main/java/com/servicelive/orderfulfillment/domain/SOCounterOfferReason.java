/**
 * 
 */
package com.servicelive.orderfulfillment.domain;

import java.text.Collator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;

/**
 * Entity for Table:
 * CREATE TABLE  `int_supplier_munged_next_release`.`so_providers_counter_offer_reasons` (
 *   `so_id` varchar(30) NOT NULL,
 *   `resource_id` int(11) NOT NULL,
 *   `provider_resp_id` int(10) DEFAULT NULL,
 *   `provider_resp_reason_id` int(10) DEFAULT NULL,
 *   `counter_offer_resp_reason_id` int(10) DEFAULT NULL COMMENT 'represents reason for placing the counter offer response',
 *   `created_date` datetime NOT NULL,
 *   `modified_date` datetime NOT NULL,
 *   `counter_offer_reason_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
 *   `so_routed_provider_id` int(10) unsigned NOT NULL,
 *   PRIMARY KEY (`counter_offer_reason_id`) USING BTREE
 *   KEY `sp_providers_counter_offer_reasons_fk1` (`so_routed_provider_id`),
 *   CONSTRAINT `sp_providers_counter_offer_reasons_fk1` FOREIGN KEY (`so_routed_provider_id`) REFERENCES `so_routed_providers` (`so_routed_provider_id`)
 * ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COMMENT='Stores counter offer reponse reason details'
 * 
 * Even though this entity defines the ServiceOrderId and the providerResourceId attributes, it does not have an association
 * to the corresponding objects. This allows simplicity in the object graph and we don't need to maintain the bi-directional
 * traversal required by JPA/HBM for persistence. The corresponding objects should have been created long before this entity
 * is created, and hence getting the values from the objects should not pose a persistence issue.
 * 
 * The providerResourceId is however used to map to RoutedProvider 
 *  
 * @author Mustafa Motiwala
 *
 */

@Entity
@Table(name = "so_providers_counter_offer_reasons")
@XmlRootElement(name="counteroffer")
@XmlAccessorType(XmlAccessType.FIELD)
public class SOCounterOfferReason  extends SOBase implements Comparable<SOCounterOfferReason>{

    /**
     * Generated version id.
     */
    private static final long serialVersionUID = 689962849228468456L;
    
    @Id@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="counter_offer_reason_id")
    @XmlAttribute(name="id")
    private Long id;
    
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="so_routed_provider_id", referencedColumnName="so_routed_provider_id"),
            @JoinColumn(name="so_id", referencedColumnName="so_id"),
            @JoinColumn(name="resource_id", referencedColumnName="resource_id")
    })
    @XmlTransient
    private RoutedProvider routedProvider;
    
    @Column(name="provider_resp_id")
    @XmlAttribute
    private Integer providerResponseId;
    
    @Column(name="provider_resp_reason_id")
    @XmlAttribute
    private Integer providerResponseReasonId;

    @Column(name="counter_offer_resp_reason_id")
    @XmlAttribute
    private Long responseReasonId;
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @return the providerResponseId
     */
    public ProviderResponseType getProviderResponse() {
        return ProviderResponseType.fromId(providerResponseId);
    }
    /**
     * @param response the providerResponseId to set
     */
    public void setProviderResponse(ProviderResponseType response) {
        this.providerResponseId = response.getId();
    }
    /**
     * @return the providerResponseReasonId
     */
    public Integer getProviderResponseReasonId() {
        return providerResponseReasonId;
    }
    /**
     * @param providerResponseReasonId the providerResponseReasonId to set
     */
    public void setProviderResponseReasonId(Integer providerResponseReasonId) {
        this.providerResponseReasonId = providerResponseReasonId;
    }
    /**
     * @return the responseReasonId
     */
    public Long getResponseReasonId() {
        return responseReasonId;
    }
    /**
     * @param responseReasonId the responseReasonId to set
     */
    public void setResponseReasonId(Long responseReasonId) {
        this.responseReasonId = responseReasonId;
    }
    
    public void afterUnmarshal(Unmarshaller  target, Object parent){
        if(parent instanceof RoutedProvider)
            this.routedProvider = (RoutedProvider)parent;
    }
    /**
     * @return the routedProvider
     */
    public RoutedProvider getRoutedProvider() {
        return routedProvider;
    }
    /**
     * @param routedProvider the routedProvider to set
     */
    public void setRoutedProvider(RoutedProvider routedProvider) {
        this.routedProvider = routedProvider;
    }

	public int compareTo(SOCounterOfferReason o) {
		return Collator.getInstance().compare(this.toString(), o.toString());
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();

		sb.append((providerResponseId != null) ? providerResponseId : "");
		sb.append((providerResponseReasonId != null) ? providerResponseReasonId : "");
		sb.append((responseReasonId != null) ? responseReasonId : "");
		return sb.toString();

	}
	
	/**
   	* A class that overrides equals must also override hashCode.
  	*/
	@Override 
	public int hashCode(){
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(responseReasonId).append(providerResponseId)
		 	.append(providerResponseReasonId);
		return hcb.toHashCode();
	}
	
	public boolean equals(Object aThat){
	    if ( this == aThat ) return true;
	    if ( !(aThat instanceof SOCounterOfferReason) ) return false;
	    SOCounterOfferReason that = (SOCounterOfferReason)aThat;
	    return compareTo(that) == 0;
	}
}
