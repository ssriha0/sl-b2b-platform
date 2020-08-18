package com.servicelive.orderfulfillment.domain;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

/**
 * User: Yunus Burhani
 * Date: Mar 19, 2010
 * Time: 1:02:19 PM
 */
@Entity()
@Table(name = "so_group")
@XmlRootElement()
public class SOGroup extends SOBase{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3433776780370675358L;

	@Id
	@GeneratedValue(generator="soIdGenerator")
	@org.hibernate.annotations.GenericGenerator(name = "soIdGenerator", strategy = "com.servicelive.orderfulfillment.domain.util.ServiceOrderIdGenerator")
    @Column(name = "so_group_id")
    private String groupId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name="so_group_id",referencedColumnName="so_group_id")
    private SOGroupPrice groupPrice;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "soGroup")
	private Set<GroupRoutedProvider> groupRoutedProviders = Collections.emptySet();

    @Column(name = "group_service_date")
    private Date groupServiceDate;

    @Column(name = "group_service_time")
    private String groupServiceTime;

    @Column(name = "group_title")
    private String groupTitle;

    @Column(name = "lock_edit_ind")
    private Integer lockEditInd;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "soGroup")
    private Set<ServiceOrder> serviceOrders;

	@Column(name = "wf_state_id")
    private Integer wfStateId;
    
    public void addGroupRoutedProvider(GroupRoutedProvider grp){
		this.groupRoutedProviders.add(grp);
		grp.setSoGroup(this);
	}

    public void addServiceOrder(ServiceOrder so) {
		if (serviceOrders == null) serviceOrders = new HashSet<ServiceOrder>();
		serviceOrders.add(so);
		so.setSoGroup(this);		
	}

    public String getGroupId() {
        return groupId;
    }

    public SOGroupPrice getGroupPrice() {
        return groupPrice;
    }

    public Set<GroupRoutedProvider> getGroupRoutedProviders() {
		return groupRoutedProviders;
	}

    public Date getGroupServiceDate() {
        return groupServiceDate;
    }

    public String getGroupServiceTime() {
        return groupServiceTime;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public Integer getLockEditInd() {
        return lockEditInd;
    }

    public GroupRoutedProvider getRoutedProvider(Long providerResourceId) {
		for(GroupRoutedProvider grp : this.groupRoutedProviders){
			if (grp.getProviderResourceId().equals(providerResourceId)){
				return grp;
			}
		}
		return null;
	}

    public Set<ServiceOrder> getServiceOrders() {
        return serviceOrders;
    }

    public Integer getWfStateId() {
        return wfStateId;
    }

    @XmlElement()
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @XmlElement()
    public void setGroupPrice(SOGroupPrice groupPrice) {
        this.groupPrice = groupPrice;
        if (null != this.groupPrice){
            this.groupPrice.setSoGroup(this);
        }
    }

    @XmlElement()
    public void setGroupRoutedProviders(Set<GroupRoutedProvider> groupRoutedProviders) {
		this.groupRoutedProviders = groupRoutedProviders;
		for(GroupRoutedProvider grp : this.groupRoutedProviders){
			grp.setSoGroup(this);
		}
	}

    @XmlElement()
    public void setGroupServiceDate(Date groupServiceDate) {
        this.groupServiceDate = groupServiceDate;
    }

    @XmlElement()
    public void setGroupServiceTime(String groupServiceTime) {
        this.groupServiceTime = groupServiceTime;
    }

    @XmlElement()
    public void setGroupTitle(String groupTitle) {
    	if (!StringUtils.isEmpty(groupTitle)){
    		if (groupTitle.length() > 50){
    			groupTitle = groupTitle.substring(0, 50);
    		}
    	}
        this.groupTitle = groupTitle;
    }

	@XmlElement()
    public void setLockEditInd(Integer lockEditInd) {
        this.lockEditInd = lockEditInd;
    }

	@XmlElement()
    public void setServiceOrders(Set<ServiceOrder> serviceOrders) {
        this.serviceOrders = serviceOrders;
        for(ServiceOrder so : serviceOrders)
            so.setSoGroup(this);
    }
	
	@XmlElement()
	public void setWfStateId(Integer wfStateId) {
        this.wfStateId = wfStateId;
    }

	public ServiceOrder getFirstServiceOrder() {
		if(this.serviceOrders == null) return null;
		return this.serviceOrders.iterator().next();
	}
}
