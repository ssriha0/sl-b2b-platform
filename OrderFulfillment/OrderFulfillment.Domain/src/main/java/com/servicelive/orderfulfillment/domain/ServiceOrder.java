package com.servicelive.orderfulfillment.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Formula;



import com.servicelive.orderfulfillment.domain.type.ContactLocationType;
import com.servicelive.orderfulfillment.domain.type.ContactType;
import com.servicelive.orderfulfillment.domain.type.LegacySOSubStatus;
import com.servicelive.orderfulfillment.domain.type.LocationType;
import com.servicelive.orderfulfillment.domain.type.PartSupplierType;
import com.servicelive.orderfulfillment.domain.type.PhoneType;
import com.servicelive.orderfulfillment.domain.type.PriceModelType;
import com.servicelive.orderfulfillment.domain.type.SOFieldsChangedType;
import com.servicelive.orderfulfillment.domain.type.SOPriceType;
import com.servicelive.orderfulfillment.domain.type.SOTaskType;
import com.servicelive.orderfulfillment.domain.util.ConditionalOfferComparator;
import com.servicelive.orderfulfillment.domain.util.CreationComparator;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.domain.util.TimeChangeUtil;

@Entity
@Table(name = "so_hdr")
@XmlRootElement()
public class ServiceOrder extends SOBase {
	
	private static final int SO_VIEW_MODE_FLAG = 0;

	private static final long serialVersionUID = 6558490507956928210L;
	private static Logger logger = Logger.getLogger(ServiceOrder.class);

	public static String extractCustomRefValueFromList(String customRefKeyName, List<SOCustomReference> soCustomReferenceList) {
        SOCustomReference customRef = extractCustomReferenceFromList(customRefKeyName, soCustomReferenceList);
        return (null == customRef)? null : customRef.getBuyerRefValue();
    }

    public static SOCustomReference extractCustomReferenceFromList(String customRefKeyName, List<SOCustomReference> soCustomReferenceList) {
        if (soCustomReferenceList != null) {
            for (SOCustomReference customRef : soCustomReferenceList) {
                if (null != customRef.getBuyerRefTypeName() && customRef.getBuyerRefTypeName().trim().equals(customRefKeyName)) return customRef;
            }
        }
        logger.info("returning null");
        return null;
    }
		
	public static SOTask extractPrimaryTaskFromList(List<SOTask> taskList) {
        for (SOTask task : taskList) {
            if (task.isPrimaryTask() && !task.getTaskStatus().equals("DELETED")) return task;
        }
        return null;
    }

	@Id @Column(name = "so_id")
	@GeneratedValue(generator="soIdGenerator")
	@org.hibernate.annotations.GenericGenerator(name = "soIdGenerator", strategy = "com.servicelive.orderfulfillment.domain.util.ServiceOrderIdGenerator")
	private String soId;
	
	@Column(name = "buyer_id")
	private Long buyerId;
	
	@Column(name = "buyer_contact_id")
	private Integer buyerContactId;
	
	@Column(name = "creator_user_name")
	private String creatorUserName;
	
	@Column(name = "closer_user_name")
	private String closerUserName;
	
	@Column(name = "assoc_so_id")
	private String assocSoId;
	
	@Column(name = "assoc_reason_id")
	private Integer assocReasonId;
	
	@Column(name = "initial_routed_date")
	private Date initialRoutedDate;
	
	@Column(name = "routed_date")
	private Date routedDate;
	
	@Column(name = "accepted_date")
	private Date acceptedDate;
	
	@Column(name = "cancelled_date")
	private Date cancelledDate;
	
	@Column(name = "voided_date")
	private Date voidedDate;
	
	@Column(name = "completed_date")
	private Date completedDate;
	
	@Column(name = "closed_date")
	private Date closedDate;
	
	@Column(name = "act_arrival_start_date")
	private Date actArrivalStartDate;
	
	@Column(name = "act_arrival_end_date")
	private Date actArrivalEndDate;
	
	@Column(name = "pricing_type_id")
	private Integer pricingTypeId;
	
	@Column(name = "initial_price")
	private BigDecimal  initialPrice;
	
	@Column(name = "spend_limit_incr_comment")
	private String spendLimitIncrComment;
	
	@Column(name = "access_fee")
	private BigDecimal  accessFee;
	
	@Column(name = "sow_title")
	private String sowTitle;
	
	@Column(name = "sow_descr")
	private String sowDs;

	@Column(name = "provider_instr")
	private String providerInstructions;

	@Column(name = "buyer_terms_cond")
	private String buyerTermsCond;
	
	@Column(name ="buyer_so_id")
	private String buyerSoId;
	
	@Column(name = "provider_terms_cond_resp")
	private Integer providerTermsCondResp;
	
	@Column(name = "resolution_descr")
	private String resolutionDs;
	
	@Column(name = "wf_state_id")
	private Integer wfStateId;

	@Formula(value = "(Select w.wf_state from wf_states w where w.wf_state_id = wf_state_id)")
	@Column(insertable=false, updatable=false)
	private String wfStatus;
	
	@Formula(value = "(Select sl.role_id from so_logging sl where sl.so_id = so_id and sl.action_id IN (13,36) order by sl.created_date desc limit 1)")
	@Column(insertable=false, updatable=false)
	private Integer rescheduleRole;
		
	@Column(name = "so_substatus_id")
	private Integer wfSubStatusId;
	
	@Formula(value = "(Select o.descr from lu_so_substatus o where o.so_substatus_id = so_substatus_id)")
	@Column(insertable=false, updatable=false)
	private String wfSubStatus;
	
	
	@Formula(value = "(SELECT o.routing_rule_hdr_id FROM so_routing_rule_assoc o WHERE o.so_id = so_id limit 1)")
	@Column(insertable=false, updatable=false)
	private Integer condAutoRoutingRule;
	
	
	@Column(name = "primary_skill_category_id")
	private Integer primarySkillCatId;
	
	@Column(name = "last_status_chg")
	private Date lastStatusChange;
	
	@Column(name = "provider_service_confirm_ind")
	private Integer providerServiceConfirmInd;
	
	@Column(name = "spend_limit_labor")
	private BigDecimal spendLimitLabor;	
	
	@Column(name = "spend_limit_parts")
	private BigDecimal spendLimitParts;
	
	@Column(name = "accepted_vendor_id")
	private Long acceptedProviderId;
	
	@Column(name = "accepted_resource_id")
    private Long acceptedProviderResourceId;
	
	@Column(name = "final_price_labor")
	private BigDecimal finalPriceLabor;
	
	@Transient
	private BigDecimal soMaxLabor;
	
	@Transient
	private Integer spendLimitReasonId;

	@Transient
	private boolean taskLevelPricing;
	
	//new...for Performance
	@Transient
	private boolean isUpdate;
	
	//private boolean scopeChange;
	@Transient
	private ServiceOrder soForUpdateComparison;
	
	@Transient
	private boolean scopeChange;
	
	@Transient
	private boolean repost;
	
	@Column(name = "last_status_id")
	private Integer lastStatusId;
	
	@Formula(value = "(Select w.wf_state from wf_states w where w.wf_state_id = last_status_id)")
	@Column(insertable=false, updatable=false)
	private String lastStatus;
	
	@Column(name = "parts_supplied_by_id")
	private Integer partsSupplier;
	
	@Column(name = "final_price_parts")
	private BigDecimal finalPriceParts;
	
	@Column(name = "so_terms_cond_id")
	private Integer soTermsCondId;
	
	@Column(name = "doc_size_total")
	private Long docSizeTotal;
	
	@Column(name = "lock_edit_ind")
	private Integer lockEditInd = SO_VIEW_MODE_FLAG;
	
	@Column(name = "logo_document_id")
	private Integer logoDocumentId;
	
	@Column(name = "buyer_resource_id")
	private Long buyerResourceId;  
		
	@Column(name = "expired_date")
	private Date expiredDate;  

	@Column(name = "activated_date")
	private Date activatedDate;
	
	@Column(name = "problem_date")
	private Date problemDate;  
		
	@Column(name = "deleted_date")
	private Date deletedDate;  

	@Column(name = "buyer_so_terms_cond_id")
	private Integer buyerSOTermsCondId;
	
	@Column(name = "buyer_so_terms_cond_ind")
	private Integer buyerSOTermsCondInd;
	
	@Column(name = "buyer_so_terms_cond_date")
	private Date buyerTermsCondDate;
	
	@Column(name = "provider_so_terms_cond_id")
	private Integer providerOTermsCondId;
	
	@Column(name = "provider_so_terms_cond_ind")
	private Integer providerSOTermsCondInd;
	
    @Column(name = "provider_terms_cond_date")
	private Date providerTermsCondDate;

	@Column(name = "funding_type_id")
	private Integer fundingTypeId;
	
	@Column(name = "posting_fee")
	private BigDecimal  postingFee;

	@Column(name = "cancellation_fee")
	private BigDecimal  cancellationFee;
	
	@Column(name = "retail_price")
	private BigDecimal retailPrice;
	
	@Column(name = "retail_cancellation_fee")
	private BigDecimal  retailCancellationFee;
	
	@ManyToOne(optional = true, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "so_group_id")
	private SOGroup soGroup;

	@Column(name = "so_orig_group_id")
	private String orignalGroupId;
	
	@Column(name = "spn_id")
	private Integer spnId;
	
	@Column(name = "service_locn_time_zone")
	private String serviceLocationTimeZone;
	
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "serviceOrder")
//	private List<SODocument> documents = new ArrayList<SODocument>();
		
	@Column(name = "service_date_timezone_offset")
	private Integer serviceDateTimezoneOffset;
	
	@Column(name = "account_id")
	private Integer accountId;
	
	@Column(name = "share_contact_ind")
	private Boolean shareContactIndicator;
	
	@Column(name = "total_permit_price")
	private BigDecimal totalPermitPrice;
	
	@Column(name = "pending_cancel_date")
	private Date pendingCancelDate;
	
	@Transient
	private boolean isDateConvertedForPersistence = false;
	
	@Embedded
	private SOSchedule schedule;
	
	@Embedded
	@AttributeOverrides({
	        @AttributeOverride(name="serviceDateTypeId", column=@Column(name = "resched_date_type_id")),
	        @AttributeOverride(name="serviceDateGMT1", column=@Column(name = "resched_service_date1")),
	        @AttributeOverride(name="serviceDateGMT2", column=@Column(name = "resched_service_date2")),
            @AttributeOverride(name="serviceTimeStartGMT", column=@Column(name = "resched_service_time_start")),
            @AttributeOverride(name="serviceTimeEndGMT", column=@Column(name = "resched_service_time_end"))
	})
	private SOSchedule reschedule;
	
	@Transient
	private String modifiedByName;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "serviceOrder")
	private List<SONote> notes = new ArrayList<SONote>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "serviceOrder")
	private List<SOServiceDatetimeSlot> soServiceDatetimeSlot = new ArrayList<SOServiceDatetimeSlot>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "serviceOrder")
	private List<SOTask> tasks = new ArrayList<SOTask>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "serviceOrder")
	private List<RoutedProvider> routedResources = new ArrayList<RoutedProvider>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "serviceOrder")
	private List<TierRouteProviders> tierRoutedResources = new ArrayList<TierRouteProviders>();

//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "serviceOrder")
//	private List<SOEvent> events = new ArrayList<SOEvent>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "serviceOrder")
	private List<SOPart> parts = new ArrayList<SOPart>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "serviceOrder")
	private List<SOContact> contacts = new ArrayList<SOContact>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "serviceOrder")
	private List<SOLocation> locations = new ArrayList<SOLocation>();
	
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "serviceOrder")
	private Set<SOLogging> loggings = new HashSet<SOLogging>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "serviceOrder")
	private Set<SOCancellation> socancellations = new HashSet<SOCancellation>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "serviceOrder")
	private List<SOCustomReference> customReferences = new ArrayList<SOCustomReference>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "serviceOrder")
	private List<SOAddon> addons = new ArrayList<SOAddon>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "serviceOrder")
	private List<SOProviderInvoiceParts> soProviderInvoiceParts = new ArrayList<SOProviderInvoiceParts>();    
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "serviceOrder")
	private List<SOPartLaborPriceReason> soPartLaborPriceReason = new ArrayList<SOPartLaborPriceReason>();   
    
	//SL-18007 Added so_price_change_history 
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "serviceOrder")
	private List<SOPriceChangeHistory> soPriceChangeHistory = new ArrayList<SOPriceChangeHistory>();
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name="so_id",referencedColumnName="so_id")
	private SOPrice price;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name="so_id", referencedColumnName="so_id")
	private SOWorkflowControls soWorkflowControls;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name="so_id", referencedColumnName="so_id")
	private SOAdditionalPayment additionalPayment;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name="so_id", referencedColumnName="so_id")
    private ServiceOrderProcess serviceOrderProcess;

    @Column(name = "price_model")
	private String priceModel  = PriceModelType.UNKNOWN.name();
    
    @Column(name = "price_type")
	private String priceType = SOPriceType.SO_LEVEL.name();
    
    @Column(name = "assignment_type")
    private String assignmentType;
    
    @OneToOne( fetch=FetchType.EAGER, cascade= { CascadeType.ALL} )
    @JoinColumn(name = "so_id", unique=false, nullable=false)
	private SOScheduleStatus soScheduleStatus;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "serviceOrder")
	private List<SOScheduleHistory> scheduleHistory = new ArrayList<SOScheduleHistory>();

	public List<SOScheduleHistory> getScheduleHistory() {
		return scheduleHistory;
	}

	public void setScheduleHistory(List<SOScheduleHistory> scheduleHistory) {
		this.scheduleHistory = scheduleHistory;
	}

	public SOScheduleStatus getSoScheduleStatus() {
		return soScheduleStatus;
	}

	public void setSoScheduleStatus(SOScheduleStatus soScheduleStatus) {
		this.soScheduleStatus = soScheduleStatus;
	}

	public String getAssignmentType() {
		return assignmentType;
	}

	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}

	@Column(name = "service_date1_time")
    private String serviceDate1Time;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "serviceOrder")
	private List<SOManageScope> soManageScope = new ArrayList<SOManageScope>();

    @Transient
    private boolean nextDayService;
	
	@Transient
    private String divisionCode;

	@Transient
    private ValidationHolder validationHolder;

	@Transient
    private Integer condAutoRouteRuleId;

    @Transient
    private String condAutoRouteRuleName;

	@Transient
	private Integer templateId;
	
	@Transient
	private String primarySkillCategory;

	@Transient
	private String buyerState;

	@Transient
	private String logoDocumentTitle;
	
	@Transient
	private String cancellationComment = "";
	
	@Transient
	boolean orderPrepRequired = false;

    @Transient
    private String externalStatus;
    
    @Transient
    private SOLocation oldLocation;
    
    @Transient
    private SOSchedule oldSchedule;
    
    @Transient
    private String oldSowDesc;
    
    @Transient
    private String oldProviderInstructions;
    
    @Transient
    private String oldCustomRefs;
    
    @Transient
    private String updateCustomRefs;
    
    @Transient
    private Date cancellationRequestDate;
    
    @Transient
    private boolean isTasksPresent;
    
    @Transient
    private boolean createdViaAPI;
    
    //SL-17532
    @Transient
    private boolean isCustomRefPresent;
    
    //sl-16667
    @Transient
    private boolean isNewSoInjection;
    
    @Transient
    private boolean createWithOutTasks;
    
    @Transient
    private boolean jobCodeMismatch;
    
    @Transient
    private boolean createOrderInd;
    
  //sl-18007
    @Transient
  	private boolean isPost;
    
    @Transient
    private boolean isSaveAsDraftFE;
    
    @Transient
    private String routingPriorityInd;
    
    @Transient
    private String perfCriteriaLevel;
    
    @Transient
    private List<SOSalesCheckItems> items = new ArrayList<SOSalesCheckItems>();

    @Transient
    private Long inhomeAcceptedFirm;
    
    @Transient
    private String inhomeAcceptedFirmName;
    
    @Transient
	private boolean spendLimitDecrease;

    
    
	public String getRoutingPriorityInd() {
		return routingPriorityInd;
	}

	public void setRoutingPriorityInd(String routingPriorityInd) {
		this.routingPriorityInd = routingPriorityInd;
	}

	public String getPerfCriteriaLevel() {
		return perfCriteriaLevel;
	}

	public void setPerfCriteriaLevel(String perfCriteriaLevel) {
		this.perfCriteriaLevel = perfCriteriaLevel;
	}

	public boolean isSaveAsDraftFE() {
		return isSaveAsDraftFE;
	}

	public void setSaveAsDraftFE(boolean isSaveAsDraftFE) {
		this.isSaveAsDraftFE = isSaveAsDraftFE;
	}

	public boolean isPost() {
		return isPost;
	}

	public void setPost(boolean isPost) {
		this.isPost = isPost;
	}

	public boolean isCreateOrderInd() {
		return createOrderInd;
	}

	public void setCreateOrderInd(boolean createOrderInd) {
		this.createOrderInd = createOrderInd;
	}

	public boolean isJobCodeMismatch() {
		return jobCodeMismatch;
	}

	public void setJobCodeMismatch(boolean jobCodeMismatch) {
		this.jobCodeMismatch = jobCodeMismatch;
	}

	public boolean isCreateWithOutTasks() {
		return createWithOutTasks;
	}

	public void setCreateWithOutTasks(boolean createWithOutTasks) {
		this.createWithOutTasks = createWithOutTasks;
	}

	public boolean isNewSoInjection() {
		return isNewSoInjection;
	}

	public void setNewSoInjection(boolean isNewSoInjection) {
		this.isNewSoInjection = isNewSoInjection;
	}
    
	public boolean isCustomRefPresent() {
		return isCustomRefPresent;
	}
    //SL-17781
	@Transient
	private boolean isMandatoryCustomRefPresent;
	
	public boolean isMandatoryCustomRefPresent() {
		return isMandatoryCustomRefPresent;
	}

	public void setMandatoryCustomRefPresent(boolean isMandatoryCustomRefPresent) {
		this.isMandatoryCustomRefPresent = isMandatoryCustomRefPresent;
	}
	
	public void setCustomRefPresent(boolean isCustomRefPresent) {
		this.isCustomRefPresent = isCustomRefPresent;
	}

	public void addAddOn(SOAddon addon) {
    	if (addon == null || this.addons == null) return;
    	addon.setServiceOrder(this);
    	addons.add(addon);
    }


	public void addContact(SOContact contact) {
		contact.setServiceOrder(this);
		contacts.add(contact);
		if (locations.size() > 0){
			associateContactAndLocation();
		}
	}

	public void addCustomReference(SOCustomReference customReference) {
		if (customReference == null || this.customReferences == null) return;
		customReference.setServiceOrder(this);
		this.customReferences.add(customReference);
	}

    public void addLocation(SOLocation location) {
		location.setServiceOrder(this);
		locations.add(location);
		if (contacts.size() > 0){
			associateContactAndLocation();
		}
	}

    public void addLogging( SOLogging log ) {
		log.setServiceOrder(this);
		loggings.add(log);
	}

	public void addNote(SONote note){
        note.setServiceOrder(this);
        notes.add(note);
    }


	public void addPart(SOPart part) {
		if (part == null || this.parts == null) return;
		part.setServiceOrder(this);
		this.parts.add(part);
		
	}

    public void addRoutedResources(RoutedProvider rp) {
        if(null == this.routedResources) this.routedResources = new ArrayList<RoutedProvider>();
        this.routedResources.add(rp);
        rp.setServiceOrder(this);
    }
    
    public void addTierRoutedResources(TierRouteProviders trp) {
        if(null == this.tierRoutedResources) this.tierRoutedResources = new ArrayList<TierRouteProviders>();
        this.tierRoutedResources.add(trp);
        trp.setServiceOrder(this);
    }
    
    public void addSOServiceDatetimeSlots(SOServiceDatetimeSlot slot) {
    	if (null == soServiceDatetimeSlot) {
    		this.soServiceDatetimeSlot = new ArrayList<SOServiceDatetimeSlot>();
    	}
    	this.soServiceDatetimeSlot.add(slot);
    	slot.setServiceOrder(this);
    }

	public void afterUnmarshal(Unmarshaller  target, Object parent){
		associateContactAndLocation();
	}

	private void associateContactAndLocation(){
		joinContactAndLocation(getServiceContact(), getServiceLocation());
		joinContactAndLocation(getBuyerAssociateContact(), getBuyerAssociateLocation());
		joinContactAndLocation(getBuyerSupportContact(), getBuyerSupportLocation());
		joinContactAndLocation(getVendorResourceContact(), getVendorResourceLocation());
	}
	
	
	
	/**
	 * Compare a service order and put change data or keys in a map
	 * @param soNewData Order to compare with.
	 * @return
	 */
	public List<SOFieldsChangedType> compareTo(ServiceOrder soNewData) {
		List<SOFieldsChangedType> changed = new ArrayList<SOFieldsChangedType>();
		
		//primary skill category changed
		if (!soNewData.getPrimarySkillCatId().equals(this.getPrimarySkillCatId())){
			changed.add(SOFieldsChangedType.PRIMARY_SKILL_CATEGORY);
		}
		//Service Location
		if (this.getServiceLocation() == null && soNewData.getServiceLocation() != null){
			changed.add(SOFieldsChangedType.SERVICE_LOCATION);
		} else if (soNewData.getServiceLocation().compareTo(this.getServiceLocation()) != 0) {
			changed.add(SOFieldsChangedType.SERVICE_LOCATION);
			if (!soNewData.getServiceLocation().getZip().equals(this.getServiceLocation().getZip())){
				changed.add(SOFieldsChangedType.SERVICE_LOCATION_ZIP_CHANGED);
			}
		}
		
		//Contact
		if( nullSafeCompareTo(soNewData.getServiceContact(), this.getServiceContact()) != 0 ) {
			changed.add(SOFieldsChangedType.SERVICE_CONTACT);		
		}
		//check if other locations or contacts are changed
		if( nullSafeNotEqual(soNewData.getBuyerAssociateContact(), this.getBuyerAssociateContact())) {
			changed.add(SOFieldsChangedType.BUYER_ASSOCIATE_CONTACT);		
		}
		
		if( nullSafeNotEqual(soNewData.getBuyerAssociateLocation(), this.getBuyerAssociateLocation())) {
			changed.add(SOFieldsChangedType.BUYER_ASSOCIATE_LOCATION);		
		}
		
		if( nullSafeNotEqual(soNewData.getBuyerSupportContact(), this.getBuyerSupportContact())) {
			changed.add(SOFieldsChangedType.BUYER_SUPPORT_CONTACT);		
		}
		
		if( nullSafeNotEqual(soNewData.getBuyerSupportLocation(), this.getBuyerSupportLocation())) {
			changed.add(SOFieldsChangedType.BUYER_SUPPORT_LOCATION);		
		}

		if( nullSafeNotEqual(soNewData.getEndUserContact(), this.getEndUserContact())) {
			changed.add(SOFieldsChangedType.END_USER_CONTACT);		
		}
		
		if( nullSafeNotEqual(soNewData.getVendorResourceContact(), this.getVendorResourceContact())) {
			changed.add(SOFieldsChangedType.VENDOR_RESOURCE_CONTACT);		
		}
		
		if( nullSafeNotEqual(soNewData.getVendorResourceLocation(), this.getVendorResourceLocation())) {
			changed.add(SOFieldsChangedType.VENDOR_RESOURCE_LOCATION);		
		}
		
		//schedule changed
		//make sure that date is in same format
		soNewData.convertDatesToSOTimeZone();
		this.convertDatesToSOTimeZone();
        logger.info("SL 18533 New So date info"+soNewData.getSchedule());
        logger.info("SL 18533 Old SO date info"+this.getSchedule());
        logger.info("Sl 18533 value of  nullSafeCompareTo"+ nullSafeCompareTo(soNewData.getSchedule(), this.getSchedule()));
        if( nullSafeCompareTo(soNewData.getSchedule(), this.getSchedule()) != 0 ) {
			logger.info("Sl 18533 Before  adding to map  SOFieldsChangedType.SCHEDULE" );
			changed.add(SOFieldsChangedType.SCHEDULE);		
		}

		if (null != soNewData.getSoServiceDatetimeSlot()) {
			logger.info("adding to map  SOFieldsChangedType.SCHEDULE_SLOTS");
			changed.add(SOFieldsChangedType.SCHEDULE_SLOTS);
		}
	
		
		if(!soNewData.isScopeChange() || (soNewData.getWfStateId()==null && !soNewData.isUpdate()))
		{
		//Any change should be considered as scope change
			
		//Should front-end tasks be considered for scope change??
		//May need to write fresh code
		List<SOTask> existingTasks = this.getActiveTasks();
		List<SOTask> newTasks = soNewData.getActiveTasks();
		if (newTasks != null) {
			if (existingTasks != null){
				for (SOTask task : newTasks) {
					if (!existingTasks.contains(task)){
						changed.add(SOFieldsChangedType.TASKS_ADDED);
						break;
					}
				}
			} else {
				changed.add(SOFieldsChangedType.TASKS_ADDED);
			}
		}
		
		//find out deleted tasks
		if (existingTasks != null) {
			if (newTasks != null){
				for (SOTask task : existingTasks) {
					if (!newTasks.contains(task)){
						changed.add(SOFieldsChangedType.TASKS_DELETED);
						break;
					}
				}
			} else {
				changed.add(SOFieldsChangedType.TASKS_DELETED);
			}
		}
		
		}
		else
		{
			//Scope change happens only if there is a change in SKUS
			logger.info("SL-10863 : Inside compare task");
			
			List<SOTask> existingTasks = this.getTasksForScopeChangeComparison();
			List<SOTask> newTasks = soNewData.getTasksForScopeChangeComparison();
			
			if(existingTasks.size() != newTasks.size()){
				if(existingTasks.size() < newTasks.size())
					changed.add(SOFieldsChangedType.TASKS_ADDED);
				else{
					changed.add(SOFieldsChangedType.TASKS_DELETED);
				}
			}else{
				List<String> existingSkus = new ArrayList<String>();
				List<String> newSkus = new ArrayList<String>();
				for(SOTask task : existingTasks){
					StringBuilder str = new StringBuilder();
					str.append(task.getSequenceNumber()).append("-").append(task.getExternalSku());
					existingSkus.add(str.toString());
				}
				for(SOTask task : newTasks){
					StringBuilder str = new StringBuilder();
					str.append(task.getSequenceNumber()).append("-").append(task.getExternalSku());
					newSkus.add(str.toString());
				}
				for(String skuseq : newSkus){
					if(!existingSkus.contains(skuseq)){
						changed.add(SOFieldsChangedType.TASKS_DELETED);
						changed.add(SOFieldsChangedType.TASKS_ADDED);
						logger.info("SL-10863 : scope changed");
						break;
					}
				}
			}
			
			boolean continueToRoute=false;
			if(this.getWfStateId()!=null && this.getWfStateId()<150 && soNewData.isUpdate())
			{
	         continueToRoute=compareRuleId(soNewData.getCondAutoRouteRuleId());
			}
	        if(continueToRoute)
	        {
	        	if(soNewData.getSpendLimitLabor()!=null&& this.getSpendLimitLabor()!=null && (soNewData.getSpendLimitLabor().doubleValue()>this.getSpendLimitLabor().doubleValue()))
	        	{
	        		changed.add(SOFieldsChangedType.TASKS_ADDED);	
	        	}
	        	else
	        	{
	        		changed.add(SOFieldsChangedType.TASKS_DELETED);
	        	}
	        	
	        }
	        
		}
		/**SPM-1346:Compare AddOns based on sku,description & miscalaneous_ind
		 * addOns from updateFile:soNewData.getAddons()
		 * addOns existing in DB:this.getAddons().
		 * add ADDON_ADDED to changeField if new AddOns are added
		 *  */
		    if (soNewData.isUpdate){
		    	if(null != soNewData.getAddons()){
			        if(null != this.getAddons()){
			    	   for(SOAddon addOnFile : soNewData.getAddons()){
				          for(SOAddon addOnExists :this.getAddons()){
				        	  /**This method will compare addOns and 
				        	   * if its new one,set an indicator in addOnObj in file*/
				        	  addOnFile = checkAlreadyExistsAddOn(addOnFile,addOnExists);
				          }
				       }
			      }else{
			    	  changed.add(SOFieldsChangedType.ADDON_ADDED);
			      }
		          /**Iterate through latest modified list of addOns to check addOn is new or Not*/
			      for(SOAddon addOnFile : soNewData.getAddons()){
			    	 if(null!= addOnFile && addOnFile.isNewAddOne()){
			    		 changed.add(SOFieldsChangedType.ADDON_ADDED);
			    		 break;
			    	 }
			      }
		        }
			  /**Existing code ,Dont remove*/   
			  }else{
				  if (soNewData.getAddons() != null){
						if (this.getAddons() != null){
							for(SOAddon addOn : soNewData.getAddons()){
								if(!this.getAddons().contains(addOn)){
									changed.add(SOFieldsChangedType.ADDON_ADDED);
									break;
								}
							}
						} else {
							changed.add(SOFieldsChangedType.ADDON_ADDED);
						}
					}
			  }
			
		//change all parts if one of the parts is different		
    	  if (soNewData.getParts() != null){
  			if (this.getParts() != null){
  				for(SOPart part : soNewData.getParts()){
  					if (!this.getParts().contains(part)){
  						changed.add(SOFieldsChangedType.PARTS);
  						break;
  					}
  				}
  			} else {
  				changed.add(SOFieldsChangedType.PARTS);
  			}
  		} else if (this.getParts() != null){
  			changed.add(SOFieldsChangedType.PARTS);
  		}
	
		
		//changes in routed resources
		if(soNewData.getRoutedResources() != null){
			if (soNewData.getRoutedResources().size() == 0) {
				// B2C will send an empty list of providers if the schedule is being edited.  We don't
				// want to mark it as a routed resource change in that case.				
			} else {
			if(this.getRoutedResources() != null){
					if (this.getRoutedResources().size() == soNewData.getRoutedResources().size()) {
				for(RoutedProvider rp : soNewData.getRoutedResources()){
					if(!this.getRoutedResources().contains(rp)){
						changed.add(SOFieldsChangedType.ROUTED_RESOURCES);
						break;
					}
				}
			} else {
				changed.add(SOFieldsChangedType.ROUTED_RESOURCES);
			}
				} else {
					changed.add(SOFieldsChangedType.ROUTED_RESOURCES);
				}
			}
		} else if(this.getRoutedResources() != null){
			changed.add(SOFieldsChangedType.ROUTED_RESOURCES);
		}
		//changes in tier routed resources
		if(soNewData.getTierRoutedResources() != null){
			if (soNewData.getTierRoutedResources().size() == 0) {
				// B2C will send an empty list of providers if the schedule is being edited.  We don't
				// want to mark it as a routed resource change in that case.				
			} else {
				logger.info("ServiceOrder.java..INSIDE ELSE1..");
			if(this.getTierRoutedResources() != null){
				logger.info("ServiceOrder.java..this.getTierRoutedResources()!=null");
					if (this.getTierRoutedResources().size() == soNewData.getTierRoutedResources().size()) {
						logger.info("ServiceOrder.java..this.getTierRoutedResources().size() == soNewData.getTierRoutedResources().size()");	
				for(TierRouteProviders trp : soNewData.getTierRoutedResources()){
					if(!this.getRoutedResources().contains(trp)){
						logger.info("ServiceOrder.java..INSIDE 2ndIF..");
						changed.add(SOFieldsChangedType.TIER_ROUTED_RESOURCES);
						break;
					}
				}
			} else {
				logger.info("ServiceOrder.java..INSIDE ELSE2..");
				changed.add(SOFieldsChangedType.TIER_ROUTED_RESOURCES);
			}
				} else {
					logger.info("ServiceOrder.java..INSIDE ELSE3..");
					changed.add(SOFieldsChangedType.TIER_ROUTED_RESOURCES);
				}
			}
		} else if(this.getTierRoutedResources() != null){
			logger.info("ServiceOrder.java..INSIDE ELSE4..");
			changed.add(SOFieldsChangedType.TIER_ROUTED_RESOURCES);
		}
		//change in tier
		if( nullSafeNotEqual(soNewData.getSpnId(), this.getSpnId())) {
			logger.info("ServiceOrder.java..TIER_CHANGE..");
			changed.add(SOFieldsChangedType.TIER_CHANGE);		
		}
		
		//changes in so tier route Indicator
		if(soNewData.getSOWorkflowControls() != null && soNewData.getSOWorkflowControls().getTierRouteInd() != null){
			if(this.soWorkflowControls == null && soNewData.getSOWorkflowControls().getTierRouteInd()){
				changed.add(SOFieldsChangedType.TIER_ROUTE_IND);
			}
			else{
				if(this.soWorkflowControls != null && !soNewData.getSOWorkflowControls().getTierRouteInd().equals(this.soWorkflowControls.getTierRouteInd())){
					changed.add(SOFieldsChangedType.TIER_ROUTE_IND);
				}
			}			
		}
		
		
				
				
		//changes in performance score filter value
		if(soNewData.getSOWorkflowControls() != null && soNewData.getSOWorkflowControls().getPerformanceScore() != null){
			if(this.soWorkflowControls == null && soNewData.getSOWorkflowControls().getPerformanceScore()!=null){
				changed.add(SOFieldsChangedType.PERF_SCORE);
			}
			else{
				if(this.soWorkflowControls != null && !soNewData.getSOWorkflowControls().getPerformanceScore().equals(this.soWorkflowControls.getPerformanceScore())){
					changed.add(SOFieldsChangedType.PERF_SCORE);
				}
			}			
		}
		
		//Custom Ref are update/add delta
		if (soNewData.getCustomReferences() != null) {
			for (SOCustomReference ref : soNewData.getCustomReferences()) {
				boolean found = false;
				boolean newValue = false;
				if (this.getCustomReferences() != null) {
					for (SOCustomReference soRef : this.getCustomReferences()) {
						if (soRef.getBuyerRefTypeId().equals(ref.getBuyerRefTypeId())) {
							found = true;
							if (!soRef.getBuyerRefValue().equalsIgnoreCase(ref.getBuyerRefValue())) {
								newValue = true;
							}
							break;
						}
					}
				}
				if (!found || (found && newValue)) {
					changed.add(SOFieldsChangedType.CUSTOM_REF);
					break;
				}
			}
		}
		 
		if ( nullSafeNotEqual(soNewData.getProviderInstructions(), this.getProviderInstructions()) ) 
		{
					changed.add(SOFieldsChangedType.PROVIDER_INSTRUCTIONS);
		}

		if(!soNewData.getSowDs().equals(this.getSowDs())) {
			changed.add(SOFieldsChangedType.SO_DESCRIPTION);
		}
		if (!soNewData.sowTitle.equals(this.sowTitle)) {
			changed.add(SOFieldsChangedType.SO_TITLE);
		}
		if (nullSafeNotEqual(soNewData.buyerSoId, this.buyerSoId)) {
			changed.add(SOFieldsChangedType.SO_UNIQUE_ID);
		}
		 logger.info("NEW SO PRICE"+soNewData.getSpendLimitLabor());
		 logger.info("OLD SO PRICE"+this.getSpendLimitLabor());
		if (soNewData.getSpendLimitLabor().compareTo(this.getSpendLimitLabor()) != 0 || (soNewData.getSpendLimitLabor().intValue()==0 && this.getSpendLimitLabor().intValue()==0)){
			logger.info("Labor Price Changed");
			changed.add(SOFieldsChangedType.SPEND_LIMIT_LABOR_CHANGED);
		}
		
		if (soNewData.getSpendLimitParts().compareTo(this.getSpendLimitParts()) != 0 || (soNewData.getSpendLimitParts().intValue()==0 && this.getSpendLimitParts().intValue()==0)){
			logger.info("Parts Price Changed");
			changed.add(SOFieldsChangedType.SPEND_LIMIT_PARTS_CHANGED);
		}
		
		if (!soNewData.priceModel.equals(this.priceModel)){
			changed.add(SOFieldsChangedType.PRICE_MODEL);
		}
		
		if(soNewData.getSOWorkflowControls() != null && soNewData.getSOWorkflowControls().getSealedBidIndicator() != null){
			if(this.soWorkflowControls == null && soNewData.getSOWorkflowControls().getSealedBidIndicator()){
				changed.add(SOFieldsChangedType.SEALED_BID_ORDER_IND);
			}
			else{
				if(this.soWorkflowControls != null && !soNewData.getSOWorkflowControls().getSealedBidIndicator().equals(this.soWorkflowControls.getSealedBidIndicator())){
					changed.add(SOFieldsChangedType.SEALED_BID_ORDER_IND);
				}
			}			
		}

		if ( nullSafeNotEqual( soNewData.getBuyerTermsCond(), this.getBuyerTermsCond() ) ) {
					changed.add(SOFieldsChangedType.BUYER_TERMS_COND);
			}
		
		if ( nullSafeNotEqual( soNewData.providerServiceConfirmInd, this.providerServiceConfirmInd) ) {
			changed.add(SOFieldsChangedType.PROVIDER_SERVICE_CONFIRM_IND);
		}

		if ( nullSafeNotEqual( soNewData.getShareContactIndicator(), this.getShareContactIndicator()) ) {
			changed.add(SOFieldsChangedType.SHARE_CONTACT_IND);
		}
		
		return changed;
	}
 
	public boolean compareRuleId(Integer newCondAutoRouteRuleId)
	{
		Integer oldCondAutoRouteRuleId=null;
		if(this.getCondAutoRoutingRule()!=null)
		{
		oldCondAutoRouteRuleId=this.getCondAutoRoutingRule();
		}
		
        boolean continueToRoute=false;
        if(newCondAutoRouteRuleId != null && oldCondAutoRouteRuleId != null && newCondAutoRouteRuleId.intValue()!=oldCondAutoRouteRuleId.intValue())
        {
        	continueToRoute=true;	
        }
        else if(newCondAutoRouteRuleId == null && oldCondAutoRouteRuleId != null)
        {
        	continueToRoute=true;
        }
        else if(newCondAutoRouteRuleId != null && oldCondAutoRouteRuleId == null)
        {
        	continueToRoute=true;
        }
        else if(newCondAutoRouteRuleId == null && oldCondAutoRouteRuleId == null)
        {
        	continueToRoute=false;	
        }
        else if(newCondAutoRouteRuleId != null && oldCondAutoRouteRuleId!= null && newCondAutoRouteRuleId.intValue()==oldCondAutoRouteRuleId.intValue())
        {
        	continueToRoute=false;
        }
        else
        {
        	
        }
        
        return continueToRoute;
	}
	
	@PrePersist
	@PreUpdate
	@SuppressWarnings("unused")
	private void convertDatesToGMT() {
        //Timezone not specified. No conversion possible.
        if(StringUtils.isBlank(serviceLocationTimeZone))return;
        if (!isDateConvertedForPersistence()) {//If already converted to GMT no need to do it again
        	logger.debug("calling pre persist and update - " + soId);
        	if(schedule != null) schedule.populateGMT(serviceLocationTimeZone);
        	if(soServiceDatetimeSlot != null) this.populateScheduleSlotsGMT();
        	if(reschedule != null)reschedule.populateGMT(serviceLocationTimeZone);
        	if (routedResources != null){
    			for(RoutedProvider rp : routedResources){
    				if (rp.getSchedule() != null ){
    					rp.getSchedule().populateGMT(serviceLocationTimeZone);
    				}
    			}
        	}
            setDateConvertedForPersistence(true);
        }
	}
	
	@PostPersist
	@PostUpdate
	private void convertDatesToSOTimeZone() {
        //Timezone not specified. No conversion possible.
        if(StringUtils.isBlank(serviceLocationTimeZone)) return;
        if (isDateConvertedForPersistence()){ //if not converted to GMT no need to convert it back
        	logger.debug("calling post persist and update - " + soId);
        	if(schedule != null) schedule.populateLocalTime(serviceLocationTimeZone);
        	if(soServiceDatetimeSlot != null) this.populateScheduleSlotsLocalTime();
        	if(reschedule != null)reschedule.populateLocalTime(serviceLocationTimeZone);
        	if (routedResources != null){
    			for(RoutedProvider rp : routedResources){
    				if (rp.getSchedule() != null ){
    					rp.getSchedule().populateLocalTime(serviceLocationTimeZone);
    				}
    			}
        	}
            setDateConvertedForPersistence(false);
        }
	}

	private void populateScheduleSlotsLocalTime() {

		if (null != this.getSoServiceDatetimeSlot() && StringUtils.isNotBlank(this.serviceLocationTimeZone)) {
			for (SOServiceDatetimeSlot slot : this.getSoServiceDatetimeSlot()) {
				if (slot.getServiceStartDate() != null && StringUtils.isNotBlank(this.serviceLocationTimeZone)
						&& StringUtils.isNotBlank(slot.getServiceStartTimeDB())) {
					Calendar startDateTime = TimeChangeUtil.getCalTimeFromParts(slot.getServiceStartDateDB(), slot.getServiceStartTimeDB(),
							TimeZone.getTimeZone("GMT"));
					slot.setServiceStartDate(TimeChangeUtil.getDate(startDateTime, TimeZone.getTimeZone(this.serviceLocationTimeZone)));
					slot.setServiceStartTime(TimeChangeUtil.getTimeString(startDateTime, TimeZone.getTimeZone(this.serviceLocationTimeZone)));
				}

				if (slot.getServiceEndDate() != null && StringUtils.isNotBlank(this.serviceLocationTimeZone)
						&& StringUtils.isNotBlank(slot.getServiceEndTimeDB())) {
					Calendar endDateTime = TimeChangeUtil.getCalTimeFromParts(slot.getServiceEndDateDB(), slot.getServiceEndTimeDB(),
							TimeZone.getTimeZone("GMT"));
					slot.setServiceEndDate(TimeChangeUtil.getDate(endDateTime, TimeZone.getTimeZone(this.serviceLocationTimeZone)));
					slot.setServiceEndTime(TimeChangeUtil.getTimeString(endDateTime, TimeZone.getTimeZone(this.serviceLocationTimeZone)));
				}
			}
		}
	}

	public Date getAcceptedDate() {
		return acceptedDate;
	}

	public Long getAcceptedProviderId() {
		return acceptedProviderId;
	}

	public Long getAcceptedProviderResourceId() {
        return acceptedProviderResourceId;
    }

	public RoutedProvider getAcceptedResource() {
        if (acceptedProviderResourceId != null) {
			logger.debug("acceptedProviderResourceId is >> " + acceptedProviderResourceId);
            return getRoutedResource(acceptedProviderResourceId);
        }
		logger.debug("getAcceptedResource is null");
        return null;
    }

	public BigDecimal getAccessFee() {
		return accessFee;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public Date getActArrivalEndDate() {
		return actArrivalEndDate;
	}

	public Date getActArrivalStartDate() {
		return actArrivalStartDate;
	}

	public Date getActivatedDate() {
		return activatedDate;
	}

	public SOAdditionalPayment getAdditionalPayment() {
		return additionalPayment;
	}

	public List<SOAddon> getAddons() {
		return addons;
	}
	
	public List<SOProviderInvoiceParts> getSoProviderInvoiceParts() {
		return soProviderInvoiceParts;
	}

	public void setSoProviderInvoiceParts(
			List<SOProviderInvoiceParts> soProviderInvoiceParts) {
		this.soProviderInvoiceParts = soProviderInvoiceParts;
	}

	public Integer getAssocReasonId() {
		return assocReasonId;
	}

	public String getAssocSoId() {
		return assocSoId;
	}

	public SOContact getBuyerAssociateContact() {
		if (getContacts() != null){
			for(SOContact sc : getContacts()){
				if(sc.getContactLocationType() == ContactLocationType.BUYER_ASSOCIATION)
					return sc;
			}
		}
		return null;
	}

	public SOLocation getBuyerAssociateLocation() {
		if (getLocations() != null){
			for(SOLocation sl : getLocations()){
				if(sl.getSoLocationTypeId() == LocationType.BUYER_ASSOCIATION)
					return sl;
			}
		}
		return null;
	}

	public Integer getBuyerContactId() {
		return buyerContactId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public Long getBuyerResourceId() {
		return buyerResourceId;
	}

	public Integer getBuyerSOTermsCondId() {
		return buyerSOTermsCondId;
	}

	public Integer getBuyerSOTermsCondInd() {
		return buyerSOTermsCondInd;
	}

	public String getBuyerState(){
		return buyerState;
	}

	public SOContact getBuyerSupportContact() {
		if (getContacts() != null){
			for(SOContact sc : getContacts()){
				if(sc.getContactLocationType() == ContactLocationType.BUYER_SUPPORT)
					return sc;
			}
		}
		return null;
	}

	public SOLocation getBuyerSupportLocation() {
		if (getLocations() != null){
			for(SOLocation sl : getLocations()){
				if(sl.getSoLocationTypeId() == LocationType.BUYER_SUPPORT)
					return sl;
			}
		}
		return null;
	}

	public String getBuyerTermsCond() {
		return buyerTermsCond;
	}

	public Date getBuyerTermsCondDate() {
		return buyerTermsCondDate;
	}

	public String getCancellationComment() {
		return cancellationComment;
	}

	public BigDecimal getCancellationFee() {
		return cancellationFee;
	}

	public Date getCancelledDate() {
		return cancelledDate;
	}

	public Date getClosedDate() {
		return closedDate;
	}

//	public List<SODocument> getDocuments() {
//		return documents;
//	}

	public String getCloserUserName() {
		return closerUserName;
	}

//	public List<SOEvent> getEvents() {
//		return events;
//	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public Integer getCondAutoRouteRuleId() {
        return condAutoRouteRuleId;
    }

    public String getCondAutoRouteRuleName() {
        return condAutoRouteRuleName;
    }

	public List<SOContact> getContacts() {
		return contacts;
	}

	public String getCreatorUserName() {
		return creatorUserName;
	}

	public List<SOCustomReference> getCustomReferences() {
		return customReferences;
	}

	public String getCustomRefValue(String customRefKeyName) {
        return extractCustomRefValueFromList(customRefKeyName, this.getCustomReferences());
    }

	public Date getDeletedDate() {
		return deletedDate;
	}

	public String getDivisionCode() {
        return divisionCode;
    }

	public Long getDocSizeTotal() {
		return docSizeTotal;
	}

	public SOContact getEndUserContact() {
		for(SOContact sc : getContacts()){
			if(sc.getContactLocationType() == ContactLocationType.END_USER)
				return sc;
		}
		return null;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public BigDecimal getFinalPriceLabor() {
		return finalPriceLabor;
	}

	public BigDecimal getFinalPriceParts() {
		return finalPriceParts;
	}
	
	public BigDecimal getSoMaxLabor() {
		return soMaxLabor;
	}
	
	public Integer getSpendLimitReasonId() {
		return spendLimitReasonId;
	}
	
	public boolean isTaskLevelPricing() {
		return taskLevelPricing;
	}

	public void setTaskLevelPricing(boolean taskLevelPricing) {
		this.taskLevelPricing = taskLevelPricing;
	}

	public void setSpendLimitReasonId(Integer spendLimitReasonId) {
		this.spendLimitReasonId = spendLimitReasonId;
	}
	
	public void setSoMaxLabor(BigDecimal soMaxLabor) {
		this.soMaxLabor = soMaxLabor;
	}
	public BigDecimal getFinalPriceTotal() {
		BigDecimal total = BigDecimal.ZERO;
		if (finalPriceLabor != null) {
			total = total.add(finalPriceLabor);
		}
		if (finalPriceParts != null) {
			total = total.add(finalPriceParts);
		}
		
		return total;
	}
	
	public BigDecimal getTotalFinalPrice() {
		BigDecimal total = BigDecimal.ZERO;
		if (finalPriceLabor != null) {
			total = total.add(finalPriceLabor);
		}
		if (finalPriceParts != null) {
			total = total.add(finalPriceParts);
		}
		if(getTotalAddon() != null){
			total = total.add(getTotalAddon());
		}
		return total;
	}	

	public Integer getFundingTypeId() {
		return fundingTypeId;
	}

	public BigDecimal getInitialPrice() {
		return initialPrice;
	}

	public Date getInitialRoutedDate() {
		return initialRoutedDate;
	}

	public RoutedProvider getLastConditionalOffer(){
        if(null == this.routedResources || this.routedResources.size() == 0) return null;
        List<RoutedProvider> providerWithCondOffer = new ArrayList<RoutedProvider>();
        for(RoutedProvider rp : this.routedResources){
        	if(null != rp.getConditionalOfferDate()) providerWithCondOffer.add(rp);
        }
        
        if(providerWithCondOffer.size() == 0) return null;
        if(providerWithCondOffer.size() == 1) return providerWithCondOffer.get(0);
        
        Collections.sort(providerWithCondOffer, new ConditionalOfferComparator());
        return providerWithCondOffer.get(providerWithCondOffer.size() - 1);
    }

	public SONote getLastNote(){
        if(null == this.notes || this.notes.size() == 0) return null;
        if(this.notes.size() == 1) return this.notes.get(0);
        Collections.sort(this.notes, new CreationComparator());
        return this.notes.get(this.notes.size() - 1);
    }

	public Date getLastStatusChange() {
		return lastStatusChange;
	}

	public Integer getLastStatusId() {
		return lastStatusId;
	}
	
	public String getLastStatus() {
		return lastStatus;
	}

	public List<SOLocation> getLocations() {
		return locations;
	}

	public Integer getLockEditInd() {
		return lockEditInd;
	}

	public Set<SOLogging> getLoggings() {
		return loggings;
	}

	public Integer getLogoDocumentId() {
		return logoDocumentId;
	}

	public String getLogoDocumentTitle(){
		return this.logoDocumentTitle;
	}

	public List<SONote> getNotes() {
		return notes;
	}

	public String getOrignalGroupId() {
		return orignalGroupId;
	}

	public List<SOPart> getParts() {
		return parts;
	}

	public PartSupplierType getPartsSupplier() {
		if(partsSupplier == null)return null;
		return PartSupplierType.fromId(partsSupplier);
	}

	public BigDecimal getPostingFee() {
		return postingFee;
	}

	public SOPrice getPrice() {
		return price;
	}

	public PriceModelType getPriceModel() {
		return PriceModelType.valueOf(priceModel);
	}

	public Integer getPricingTypeId() {
		return pricingTypeId;
	}

	public String getPrimarySkillCategory() {
		return primarySkillCategory;
	}

	public Integer getPrimarySkillCatId() {
		return primarySkillCatId;
	}

	public SOTask getPrimaryTask() {
        return extractPrimaryTaskFromList(getTasks());
    }

	public Date getProblemDate() {
		return problemDate;
	}
	
	public Date getPendingCancelDate() {
		return pendingCancelDate;
	}	

	public String getProviderInstructions() {
		return providerInstructions;
	}

	public Integer getProviderOTermsCondId() {
		return providerOTermsCondId;
	}

	public Integer getProviderServiceConfirmInd() {
		return providerServiceConfirmInd;
	}

	public Integer getProviderSOTermsCondInd() {
		return providerSOTermsCondInd;
	}

	public Date getProviderTermsCondDate() {
		return providerTermsCondDate;
	}

	public Integer getProviderTermsCondResp() {
		return providerTermsCondResp;
	}

	/**
     * @return the reschedule
     */
    public SOSchedule getReschedule() {
        return reschedule;
    }

	public String getResolutionDs() {
		return resolutionDs;
	}

	public SOWorkflowControls getSOWorkflowControls() {
		return soWorkflowControls;
	}

	public BigDecimal getRetailCancellationFee() {
		return retailCancellationFee;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public Date getRoutedDate() {
		return routedDate;
	}

	public RoutedProvider getRoutedResource(Long providerResourceId) {
        if (getRoutedResources() != null) {
            for (RoutedProvider routedResource : getRoutedResources()) {
                if (routedResource.getProviderResourceId().equals(providerResourceId)) {
                    return routedResource;
                }
            }
        }
		logger.debug("getRoutedResource is null");
        return null;
    }

	public List<RoutedProvider> getRoutedResources() {
		return routedResources;
	}

	public SOSchedule getSchedule() {
		return schedule;
	}

	public SOContact getServiceContact() {
		if (getContacts() != null){
			for(SOContact sc : getContacts()){
				if(sc.getContactTypeId()==ContactType.PRIMARY && sc.getContactLocationType() == ContactLocationType.SERVICE)
					return sc;
			}
		}
		return null;
	}

	public String getServiceDate1Time() {
        return serviceDate1Time;
    }

	public Integer getServiceDateTimezoneOffset() {
		return serviceDateTimezoneOffset;
	}

	public Calendar getServiceEndDateTimeCalendar() {
		//make sure that the date is in service order time zone
    	convertDatesToSOTimeZone();
		if (null != schedule) {
			if (schedule.isRequestedServiceTimeTypeSingleDay()) {
				return getServiceStartDateTimeCalendar();
			}
			if (!StringUtils.isBlank(serviceLocationTimeZone) && null != schedule.getServiceDate2()) {
				TimeZone soTimeZone = TimeZone.getTimeZone(serviceLocationTimeZone);
				return TimeChangeUtil.getCalTimeFromParts(schedule.getServiceDate2(), schedule.getServiceTimeEnd(), soTimeZone);
			}
		}
    		return null;
    }

	public SOLocation getServiceLocation() {
		if (locations != null){
			for(SOLocation sl : getLocations()){
				if(sl.getSoLocationTypeId() == LocationType.SERVICE)
					return sl;
			}
		}
		return null;
	}

	public String getServiceLocationTimeZone() {
		return serviceLocationTimeZone;
	}

	public ServiceOrderProcess getServiceOrderProcess() {
        return serviceOrderProcess;
    }

	public Calendar getServiceStartDateTimeCalendar() {
		if (null != schedule) {
			// make sure that the date is in service order time zone
			convertDatesToSOTimeZone();
			if (!StringUtils.isBlank(serviceLocationTimeZone)) {
				TimeZone soTimeZone = TimeZone.getTimeZone(serviceLocationTimeZone);
				return TimeChangeUtil.getCalTimeFromParts(schedule.getServiceDate1(), schedule.getServiceTimeStart(), soTimeZone);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

    public Boolean getShareContactIndicator() {
		return shareContactIndicator;
	}

    public SOGroup getSoGroup() {
		return soGroup;
	}

    public String getSoId() {
		return soId;
	}

    public Integer getSoTermsCondId() {
		return soTermsCondId;
	}

    public String getSowDs() {
		return sowDs;
	}

    public String getSowTitle() {
		return sowTitle;
	}

	public String getBuyerSoId() {
		return buyerSoId;
	}

	public String getSpendLimitIncrComment() {
		return spendLimitIncrComment;
	}

    public BigDecimal getSpendLimitLabor() {
		if (spendLimitLabor == null) return PricingUtil.ZERO;
    	return spendLimitLabor;
	}

	public BigDecimal getSpendLimitParts() {
		if (spendLimitParts == null) return PricingUtil.ZERO;
		return spendLimitParts;
	}

    public Integer getSpnId() {
		return spnId;
	}

	public List<SOTask> getTasks() {
		return tasks;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public BigDecimal getTotalAddon(){
        return getTotalAddon(false);//include all coverage and does not skip Call collect    
    }

	public BigDecimal getTotalAddonWithoutPermits(){
		BigDecimal totalAddon = PricingUtil.ZERO;
		if(this.getAddons() != null){
			for(SOAddon tadd : this.getAddons()){
				if(tadd.getSku().equals("99888")) continue;
				totalAddon = totalAddon.add(tadd.getAddonPrice());
			}
		}
		return totalAddon;
           
    }
	
	public BigDecimal getTotalAddon(boolean skipCCPaymentType){
		BigDecimal totalAddon = PricingUtil.ZERO;
		if(this.getAddons() != null){
			for(SOAddon tadd : this.getAddons()){
				if(skipCCPaymentType && tadd.getCoverage() != null && tadd.getCoverage().equals("CC")) continue;
				totalAddon = totalAddon.add(tadd.getAddonPrice());
			}
		}
		return totalAddon;
	}

	public BigDecimal getTotalSpendLimit() {
		if (spendLimitLabor !=null && spendLimitParts != null)
			return spendLimitLabor.add(spendLimitParts);
		else if(spendLimitLabor != null)
			return spendLimitLabor;
		return PricingUtil.ZERO;
	}

	public BigDecimal getTotalSpendLimitWithPostingFee() {
		return getTotalSpendLimit().add(getPostingFee());
	}

	public ValidationHolder getValidationHolder() {
        return validationHolder;
    }

	public SOContact getVendorResourceContact() {
		if (getContacts() != null){
			for(SOContact sc : getContacts()){
				if(sc.getContactLocationType() == ContactLocationType.PROVIDER)
					return sc;
			}
		}
		return null;
	}

	public SOLocation getVendorResourceLocation() {
		if (getLocations() != null){
			for(SOLocation sl : getLocations()){
				if(sl.getSoLocationTypeId() == LocationType.PROVIDER)
					return sl;
			}
		}
		return null;
	}
	
	public SOContact getCustomerContact() {
		SOContact endUserContact = null;
		
		if (getContacts() != null){
			for(SOContact sc : getContacts()){
				if(sc!= null && sc.getContactTypeId() == ContactType.PRIMARY && sc.getContactLocationType() == ContactLocationType.SERVICE)						
					return sc;
				else if (sc!= null && sc.getContactLocationType() == ContactLocationType.END_USER)
					endUserContact = sc;
			}			
		}
		
		return endUserContact;
	}
	
	public SOLocation getCustomerAddress() {
		SOContact contact = getCustomerContact();				
		if ( contact != null && contact.getSoContactLocations() != null && contact.getSoContactLocations().size() > 0){
			return contact.getSoContactLocations().get(0).getLocation();
		}
		return null;
	}
	
	public SOPhone getCustomerPhoneNo() {
		SOContact contact = getCustomerContact();				
		if (contact != null && contact.getPhones() != null){
			for(SOPhone sp : contact.getPhones())
			{
				if(sp.getPhoneType() == PhoneType.PRIMARY)
					return sp;
			}
		}
		return null;
	}

	public Date getVoidedDate() {
		return voidedDate;
	}

	public Integer getWfStateId() {
		return wfStateId;
	}
	
	public String getWfStatus() {
		return wfStatus;
	}

	public String getWfSubStatus() {
		return wfSubStatus;
	}

	public Integer getWfSubStatusId() {
		return wfSubStatusId;
	}

	private boolean isDateConvertedForPersistence(){
        return isDateConvertedForPersistence;
    }

	public boolean isNextDayService() {
        return nextDayService;
    }
	
	public boolean isOrderPrepRequired() {
		return this.orderPrepRequired;
	}

	public boolean isPartPickupLocationAvailable() {
        for (SOPart part : this.getParts()) {
            if (null != part.getPickupLocation() && null != part.getPickupLocation().getZip()) return true;
        }
        return false;
    }

	public boolean isServiceEndDateTimePast() {
		convertDatesToSOTimeZone();
        Calendar currSoLocCalendarTime = new GregorianCalendar(TimeZone.getTimeZone(serviceLocationTimeZone));
        Calendar soSvcCalTime = getServiceEndDateTimeCalendar();

        return currSoLocCalendarTime.after(soSvcCalTime);
    }

	public boolean isServiceStartDateTimePast() {
		convertDatesToSOTimeZone();
        Calendar currSoLocCalendarTime = new GregorianCalendar(TimeZone.getTimeZone(serviceLocationTimeZone));
        Calendar soSvcCalTime = getServiceStartDateTimeCalendar();
        if(soSvcCalTime != null)
        	return currSoLocCalendarTime.after(soSvcCalTime);
        else
        	return false;
    }

	private void joinContactAndLocation(SOContact soContact, SOLocation soLocation) {
		if (soContact != null && soLocation != null 
				&& soContact.getSoContactLocations() != null 
				&& soContact.getSoContactLocations().size() > 0)
			soContact.setLocation(soLocation);		
	}

	@SuppressWarnings("unchecked")
	private int nullSafeCompareTo(Comparable newData, Comparable oldData ) {
		if( newData == null && oldData != null ) {
			return -1;
		} else if( newData != null && oldData == null ) {
			return 1;
		} else if( newData == null && oldData == null ) {
			return 0;
		} else {
			return newData.compareTo(oldData);
		}
	}

	private boolean nullSafeNotEqual( Object newData, Object oldData ) {		
		boolean retVal = ((newData == null) && (oldData != null)) || ((newData != null) && (oldData == null)) ||((newData != null) && (oldData != null) && !(newData.equals(oldData)));
		return 	retVal;
					
	}

	private boolean nullSafeNotEqualTmp( String newData, String oldData ) {
		boolean retVal = false;
		
		if((newData!=null)&&(oldData==null)) {
			retVal = true;
		}
		if((newData==null)&&(oldData!=null)) {
			retVal = true;
	    }
		if((newData!=null)&& (oldData!=null)) {			
			StringBuffer sNewData = new StringBuffer();
			StringBuffer sOldData = new StringBuffer();
			boolean isAlphaNewBegin = false;
			boolean isAlphaOldBegin = false;
			int nLength = newData.length();		
			for(int i=0;i<nLength;i++) {
				if(((int)newData.charAt(i)>32)&&((int)newData.charAt(i)<127)){
					if(((int)newData.charAt(i))!=58) {						
						isAlphaNewBegin = true;
					}
					if(isAlphaNewBegin)
					  sNewData.append(newData.charAt(i));
				}			
			}	
			int oLength = oldData.length();
			for(int i=0;i<oLength;i++) {
				if(((int)oldData.charAt(i)>32)&&((int)oldData.charAt(i)<127)){
					if(((int)oldData.charAt(i))!=58) {
						isAlphaOldBegin = true;
					}
					if(isAlphaOldBegin)
						sOldData.append(oldData.charAt(i));
				}			
			}
			nLength = sNewData.length();
			oLength = sOldData.length();			
			int fLength = (nLength<=oLength)?nLength:oLength;			
			for(int i=0;i<fLength;i++) {			
				if(sNewData.charAt(i)!= sOldData.charAt(i) ) {
					retVal = true;
					break;
				}			
			}			
			
		}
        return retVal;	
							
	}

	
	@PostLoad
    @SuppressWarnings("unused")
    private void postLoad(){
    	logger.debug("calling post load - " + soId);
        setDateConvertedForPersistence(true);
        convertDatesToSOTimeZone();
    }
	
	@XmlElement()
	public void setAcceptedDate(Date acceptedDate) {
		this.acceptedDate = acceptedDate;
	}

	@XmlElement()
	public void setAcceptedProviderId(Long acceptedProviderId) {
		this.acceptedProviderId = acceptedProviderId;
	}

	public void setAcceptedProviderResourceId(Long acceptedProviderResourceId) {
        this.acceptedProviderResourceId = acceptedProviderResourceId;
    }

	@XmlElement()
	public void setAccessFee(BigDecimal accessFee) {
		this.accessFee = accessFee;
	}

	@XmlElement()
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	@XmlElement
	public void setActArrivalEndDate(Date actArrivalEndDate) {
		this.actArrivalEndDate = actArrivalEndDate;
	}

	@XmlElement
	public void setActArrivalStartDate(Date actArrivalStartDate) {
		this.actArrivalStartDate = actArrivalStartDate;
	}

	@XmlElement()
	public void setActivatedDate(Date activatedDate) {
		this.activatedDate = activatedDate;
	}

	@XmlElement()
    public void setAdditionalPayment(SOAdditionalPayment additionalPayment) {
		this.additionalPayment = additionalPayment;
		if (this.additionalPayment != null) this.additionalPayment.setServiceOrder(this);
	}

	@XmlElementWrapper()
	@XmlElement(name = "soAddon")
	public void setAddons(List<SOAddon> addons) {
		this.addons = addons;
		if( this.addons != null ) {
		    for(SOChild child : this.addons){
			    child.setServiceOrder(this);
		    }
	    }
	}

	@XmlElement()
	public void setAssocReasonId(Integer assocReasonId) {
		this.assocReasonId = assocReasonId;
	}

	@XmlElement()
	public void setAssocSoId(String assocSoId) {
		this.assocSoId = assocSoId;
	}

	@XmlElement()
	public void setBuyerContactId(Integer buyerContactId) {
		this.buyerContactId = buyerContactId;
	}

	@XmlElement()
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	@XmlElement()
	public void setBuyerResourceId(Long buyerResourceId) {
		this.buyerResourceId = buyerResourceId;
	}

	@XmlElement()
	public void setBuyerSOTermsCondId(Integer buyerSOTermsCondId) {
		this.buyerSOTermsCondId = buyerSOTermsCondId;
	}

	@XmlElement()
	public void setBuyerSOTermsCondInd(Integer buyerSOTermsCondInd) {
		this.buyerSOTermsCondInd = buyerSOTermsCondInd;
	}

	@XmlElement
	public void setBuyerState(String buyerState) {
		this.buyerState = buyerState;
	}

    @XmlElement()
	public void setBuyerTermsCond(String buyerTermsCond) {
		this.buyerTermsCond = buyerTermsCond;
	}

	@XmlElement()
	public void setBuyerTermsCondDate(Date buyerTermsCondDate) {
		this.buyerTermsCondDate = buyerTermsCondDate;
	}

	public void setCancellationComment(String cancelComment) {
		this.cancellationComment = cancelComment;
	}

	@XmlElement()
	public void setCancellationFee(BigDecimal cancellationFee) {
		this.cancellationFee = cancellationFee;
	}

	@XmlElement()
	public void setCancelledDate(Date cancelledDate) {
		this.cancelledDate = cancelledDate;
	}

	@XmlElement
	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	@XmlElement()
	public void setCloserUserName(String closerUserName) {
		this.closerUserName = closerUserName;
	}

	@XmlElement
	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	@XmlElement()
    public void setCondAutoRouteRuleId(Integer condAutoRouteRuleId) {
        this.condAutoRouteRuleId = condAutoRouteRuleId;
    }

	@XmlElement()
    public void setCondAutoRouteRuleName(String condAutoRouteRuleName) {
        this.condAutoRouteRuleName = condAutoRouteRuleName;
    }

	@XmlElementWrapper()
	@XmlElement(name = "soContact")
	public void setContacts(List<SOContact> contacts) {
		this.contacts = contacts;
		if( this.contacts != null ) {
            for(SOChild child : this.contacts){
                child.setServiceOrder(this);
            }
		}
		if (locations != null && locations.size() > 0){
			associateContactAndLocation();
		}
	}

	@XmlElement()
	public void setCreatorUserName(String creatorUserName) {
		this.creatorUserName = creatorUserName;
	}

	@XmlElementWrapper()
	@XmlElement(name = "soCustomReference")
	public void setCustomReferences(List<SOCustomReference> customReferences) {
		this.customReferences = customReferences;
		if( this.customReferences != null ) {
            for(SOChild child : this.customReferences){
                child.setServiceOrder(this);
            }
        }
	}

	@XmlElement
	private void setDateConvertedForPersistence(boolean val){
        isDateConvertedForPersistence = val;
    }

	@XmlElement()
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	@XmlElement()
    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }
	
	@XmlElement()
	public void setDocSizeTotal(Long docSizeTotal) {
		this.docSizeTotal = docSizeTotal;
	}

	@XmlElement()
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	@XmlElement()
	public void setFinalPriceLabor(BigDecimal finalPriceLabor) {
		this.finalPriceLabor = finalPriceLabor;
	}

	@XmlElement()
	public void setFinalPriceParts(BigDecimal finalPriceParts) {
		this.finalPriceParts = finalPriceParts;
	}

    @XmlElement()
	public void setFundingTypeId(Integer fundingTypeId) {
		this.fundingTypeId = fundingTypeId;
	}

    @XmlElement
	public void setInitialPrice(BigDecimal initialPrice) {
		this.initialPrice = initialPrice;
	}

	@XmlElement()
	public void setInitialRoutedDate(Date initialRoutedDate) {
		this.initialRoutedDate = initialRoutedDate;
	}

	@XmlElement()
	public void setLastStatusChange(Date lastStatusChange) {
		this.lastStatusChange = lastStatusChange;
	}

	@XmlElement()
	public void setLastStatusId(Integer lastStatusId) {
		this.lastStatusId = lastStatusId;
	}
	
	@XmlElement()
	public void setLastStatus(String status) {
		this.lastStatus = status;
	}

	@XmlElementWrapper()
	@XmlElement(name = "soLocation")
	public void setLocations(List<SOLocation> locations) {
		this.locations = locations;
		if( this.locations != null ) {
            for(SOChild child : this.locations){
                child.setServiceOrder(this);
            }
		}
		if (contacts != null && contacts.size() > 0){
			associateContactAndLocation();
		}
	}

	@XmlElement()
	public void setLockEditInd(Integer lockEditInd) {
		this.lockEditInd = lockEditInd;
	}

	@XmlElementWrapper()
	@XmlElement(name = "soLogging")
	public void setLoggings(Set<SOLogging> loggings) {
		this.loggings = loggings;
		if( this.loggings != null ) {
            for(SOChild child : this.loggings){
                child.setServiceOrder(this);
            }
        }
	}

	@XmlElement()
	public void setLogoDocumentId(Integer logoDocumentId) {
		this.logoDocumentId = logoDocumentId;
	}

	@XmlElement
	public void setLogoDocumentTitle(String logoDocumentTitle) {
		this.logoDocumentTitle = logoDocumentTitle;
	}

	@XmlElement()
    public void setNextDayService(boolean nextDayService) {
        this.nextDayService = nextDayService;
    }
	
	@XmlElementWrapper()
	@XmlElement(name = "soNotes")
	public void setNotes(List<SONote> notes) {
		this.notes = notes;
		if( this.notes != null ) {
            for(SOChild child : this.notes){
                child.setServiceOrder(this);
            }
        }
	}
	
	@XmlElement()
	public void setOrderPrepRequired(boolean orderPrepRequired) {
		this.orderPrepRequired = orderPrepRequired;
	}
	
    @XmlElement()
	public void setOrignalGroupId(String orignalGroupId) {
		this.orignalGroupId = orignalGroupId;
	}
    
    @XmlElementWrapper()
	@XmlElement(name = "soParts")
	public void setParts(List<SOPart> parts) {
		this.parts = parts;
		if( this.parts != null ) {
			for(SOChild child : this.parts){
                child.setServiceOrder(this);
            }
        }
	}

    @XmlElement()
	public void setPartsSupplier(PartSupplierType partsSupplier) {
		if( partsSupplier != null ) {
		    this.partsSupplier = partsSupplier.getId();
		} else {
			this.partsSupplier = null;
	    }
	}

    @XmlElement()
	public void setPostingFee(BigDecimal postingFee) {
		this.postingFee = postingFee;
	}

    @XmlElement()
	public void setPrice(SOPrice price) {
		this.price = price;
		if (this.price != null) this.price.setServiceOrder(this);
	}
    
    public void setPriceModel(PriceModelType model) {
		if( model != null ) {
		    this.priceModel = model.name();
		} else {
			this.priceModel = null;
	    }
	}

    @XmlElement
	public void setPricingTypeId(Integer pricingTypeId) {
		this.pricingTypeId = pricingTypeId;
	}

    @XmlElement
	public void setPrimarySkillCategory(String primarySkillCategory){
		this.primarySkillCategory = primarySkillCategory;
	}

    @XmlElement()
	public void setPrimarySkillCatId(Integer primarySkillCatId) {
		this.primarySkillCatId = primarySkillCatId;
	}

    @XmlElement()
	public void setProblemDate(Date problemDate) {
		this.problemDate = problemDate;
	}
    
    @XmlElement()
	public void setPendingCancelDate(Date pendingCancelDate) {
		this.pendingCancelDate = pendingCancelDate;
	}    

    @XmlElement()
	public void setProviderInstructions(String providerInstructions) {
		this.providerInstructions = providerInstructions;
	}

    @XmlElement()
	public void setProviderOTermsCondId(Integer providerOTermsCondId) {
		this.providerOTermsCondId = providerOTermsCondId;
	}

    @XmlElement()
	public void setProviderServiceConfirmInd(Integer providerServiceConfirmInd) {
		this.providerServiceConfirmInd = providerServiceConfirmInd;
	}

	@XmlElement()
	public void setProviderSOTermsCondInd(Integer providerSOTermsCondInd) {
		this.providerSOTermsCondInd = providerSOTermsCondInd;
	}

    @XmlElement()
	public void setProviderTermsCondDate(Date providerTermsCondDate) {
		this.providerTermsCondDate = providerTermsCondDate;
	}

    @XmlElement()
	public void setSOWorkflowControls(SOWorkflowControls soWorkflowControls) {
		this.soWorkflowControls = soWorkflowControls;
		if (this.soWorkflowControls != null) this.soWorkflowControls.setServiceOrder(this);

	}
	@XmlElement()
	public void setProviderTermsCondResp(Integer providerTermsCondResp) {
		this.providerTermsCondResp = providerTermsCondResp;
	}

    /**
     * @param reschedule the reschedule to set
     */
    @XmlElement()
    public void setReschedule(SOSchedule reschedule) {
		//make sure that the conversion indicator is set to false
    	//since we are getting a new schedule
    	setDateConvertedForPersistence(false);
        this.reschedule = reschedule;
    }

    @XmlElement()
	public void setResolutionDs(String resolutionDs) {
		this.resolutionDs = resolutionDs;
	}

    @XmlElement()
	public void setRetailCancellationFee(BigDecimal retailCancellationFee) {
		this.retailCancellationFee = retailCancellationFee;
	}

    @XmlElement()
	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

    @XmlElement()
	public void setRoutedDate(Date routedDate) {
		this.routedDate = routedDate;
		//since we are setting the routed date on Service Order
		//we should have the routed provider and we should be able
		//to set the routed date on those as well
		for(RoutedProvider rp: this.routedResources){
			rp.setRoutedDate(routedDate);
		}			
	}

    @XmlElementWrapper()
	@XmlElement(name = "soRoutedProvider")
	public void setRoutedResources(List<RoutedProvider> routedResources) {
		this.routedResources = routedResources;
		for(SOChild child : this.routedResources){
			child.setServiceOrder(this);
		}
	}

    @XmlElement()
	public void setSchedule(SOSchedule schedule) {
		//make sure that the conversion indicator is set to false
    	//since we are getting a new schedule
    	setDateConvertedForPersistence(false);
    	this.schedule = schedule;
	}
    
	@XmlElement()
    public void setServiceDate1Time(String serviceDate1Time) {
        this.serviceDate1Time = serviceDate1Time;
    }
	
	@XmlElement()
	public void setServiceDateTimezoneOffset(Integer serviceDateTimezoneOffset) {
		this.serviceDateTimezoneOffset = serviceDateTimezoneOffset;
	}

	@XmlElement()
	public void setServiceLocationTimeZone(String serviceLocationTimeZone) {
		this.serviceLocationTimeZone = serviceLocationTimeZone;
	}
	
	@XmlElement
    public void setServiceOrderProcess(ServiceOrderProcess serviceOrderProcess) {
        this.serviceOrderProcess = serviceOrderProcess;
    }

	@XmlElement
	public void setShareContactIndicator(Boolean shareContactIndicator) {
		this.shareContactIndicator = shareContactIndicator;
	}
	
	@XmlTransient()
	public void setSoGroup(SOGroup soGroup) {
		this.soGroup = soGroup;
	}
    
	@XmlElement()
	public void setSoId(String soId) {
		this.soId = soId;
	}

	@XmlElement()
	public void setSoTermsCondId(Integer soTermsCondId) {
		this.soTermsCondId = soTermsCondId;
	}
		
	@XmlElement()
	public void setSowDs(String sowDs) {
		this.sowDs = sowDs;
	}

	@XmlElement()
	public void setSowTitle(String sowTitle) {
		this.sowTitle = sowTitle;
	}
	
	@XmlElement()
	public void setBuyerSoId(String buyerSoId) {
		this.buyerSoId = buyerSoId;
	}

	@XmlElement()
	public void setSpendLimitIncrComment(String spendLimitIncrComment) {
		this.spendLimitIncrComment = spendLimitIncrComment;
	}

    @XmlElement()
	public void setSpendLimitLabor(BigDecimal spendLimitLabor) {
		this.spendLimitLabor = spendLimitLabor;
	}
	
	@XmlElement()
	public void setSpendLimitParts(BigDecimal spendLimitParts) {
		this.spendLimitParts = spendLimitParts;
	}

	@XmlElement()
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	
	@XmlElementWrapper()
	@XmlElement(name = "soTask")
	public void setTasks(List<SOTask> tasks) {
		this.tasks = tasks;
		for(SOChild child : this.tasks){
			child.setServiceOrder(this);
		}
	}

	@XmlElement
	public void setTemplateId(Integer templateId){
		this.templateId = templateId;
	}

    @XmlElement()
    public void setValidationHolder(ValidationHolder validationHolder) {
        this.validationHolder = validationHolder;
    }

    @XmlElement()
	public void setVoidedDate(Date voidedDate) {
		this.voidedDate = voidedDate;
	}

	@XmlElement()
	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}

	@XmlElement()
	public void setWfStatus(String status) {
		this.wfStatus = status;
	}
	
	@XmlElement()
	public void setWfSubStatus(String subStatus) {
		this.wfSubStatus = subStatus;
	}

    @XmlElement()
	public void setWfSubStatusId(Integer wfSubStatusId) {
		this.wfSubStatusId = wfSubStatusId;
		if (wfSubStatusId == null) {
			this.wfSubStatus = "";
	}
		else {
			LegacySOSubStatus legacySOSubStatus = LegacySOSubStatus.fromId(wfSubStatusId);
			if (legacySOSubStatus == null) {
				this.wfSubStatus = "";
			}
			else {
				this.wfSubStatus = legacySOSubStatus.getDescription();
			}
		}
	}

    @Override
    public List<String> validate(){
        List<String> returnVal = new ArrayList<String>();

        if(schedule !=null)
            returnVal.addAll(schedule.validate());

        if(null != reschedule)
            returnVal.addAll(reschedule.validate());

        if(null!=price)
            returnVal.addAll(price.validate());
        
        return returnVal;
    }

    /**
     * This method is invoked at the time when a new serviceorder needs
     * to be created in the database. Some of the simple validation it
     * carries out are as listed:
     *
     * 1. Make sure that the buyer has been specified.
     * 2. There is a valid FundingType for the buyer.
     * 3. A contact has been setup of the ServiceOrder.
     * 4. A service location has been specified (Location where service is desired)
     * 5. Ensure the schedule has been specified.
     * 6. Ensure pricing information is present.
     *
     * @return Returns a list of string with messages indication failure causes. An empty
     * list indicates no errors.
     */
    public List<String> validateCreation() {
        List<String> returnVal = new ArrayList<String>();
        //If the order is NAME PRICE then validate pricing. Otherwise ignore.
        if(PriceModelType.NAME_PRICE== PriceModelType.valueOf(priceModel)){
            //Validate price:
            if(null != price) returnVal.addAll(price.validate());
            else returnVal.add("Please specify the pricing information.");
        }

        //Validate schedule:
        if(null != schedule) returnVal.addAll(schedule.validate());
        else returnVal.add("Please specify the Date/Time for the service order.");

        if(!contacts.isEmpty()){
            for(SOContact contact: contacts){
                returnVal.addAll(contact.validate());
            }
        }else returnVal.add("Contact information for buyer is missing.");
        return returnVal;
    }

    @XmlElement
    public void setExternalStatus(String externalStatus) {
        this.externalStatus = externalStatus;
    }

    public String getExternalStatus() {
        return externalStatus;
    }
 
	public SOLocation getOldLocation() {
		if(oldLocation == null)
			oldLocation = new SOLocation();
		return oldLocation;
	}

	public void setOldLocation(SOLocation oldLocation) {
		this.oldLocation = oldLocation;
	}

	public SOSchedule getOldSchedule() {
		return oldSchedule;
	}

	public void setOldSchedule(SOSchedule oldSchedule) {
		this.oldSchedule = oldSchedule;
	}

	public String getOldSowDesc() {
		return oldSowDesc;
	}

	public void setOldSowDesc(String oldSowDesc) {
		this.oldSowDesc = oldSowDesc;
	}

	public String getOldProviderInstructions() {
		return oldProviderInstructions;
	}

	public void setOldProviderInstructions(String oldProviderInstructions) {
		this.oldProviderInstructions = oldProviderInstructions;
	}

	public String getOldCustomRefs() {
		return oldCustomRefs;
	}

	public void setOldCustomRefs(String oldCustomRefs) {
		this.oldCustomRefs = oldCustomRefs;
	}

	public String getUpdateCustomRefs() {
		return updateCustomRefs;
	}

	public void setUpdateCustomRefs(String updateCustomRefs) {
		this.updateCustomRefs = updateCustomRefs;
	}

	public Date getCancellationRequestDate() {
		return cancellationRequestDate;
	}

	public void setCancellationRequestDate(Date cancellationRequestDate) {
		this.cancellationRequestDate = cancellationRequestDate;
	}

	public BigDecimal getTotalPermitPrice() {
		if (totalPermitPrice == null) return PricingUtil.ZERO;
    	return totalPermitPrice;
	}

	public void setTotalPermitPrice(BigDecimal totalPermitPrice) {
		this.totalPermitPrice = totalPermitPrice;
	}

	public String getModifiedByName() {
		return modifiedByName;
	}

	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}


	/*public boolean isScopeChange() {
		return scopeChange;
	}

	public void setScopeChange(boolean scopeChange) {
		this.scopeChange = scopeChange;
	}*/

	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	public boolean isUpdate() {
		return isUpdate;
	}
	
	public ServiceOrder getSoForUpdateComparison() {
		return soForUpdateComparison;
	}

	public void setSoForUpdateComparison(ServiceOrder soForUpdateComparison) {
		this.soForUpdateComparison = soForUpdateComparison;
	}

	public boolean isScopeChange() {
		return scopeChange;
	}

	public void setScopeChange(boolean scopeChange) {
		this.scopeChange = scopeChange;
	}

	public Integer getCondAutoRoutingRule() {
		return condAutoRoutingRule;
	}

	public void setCondAutoRoutingRule(Integer condAutoRoutingRule) {
		this.condAutoRoutingRule = condAutoRoutingRule;
	}

	public boolean isRepost() {
		return repost;
	}

	public void setRepost(boolean repost) {
		this.repost = repost;
	}

	public boolean isTasksPresent() {
		return isTasksPresent;
	}

	public void setTasksPresent(boolean isTasksPresent) {
		this.isTasksPresent = isTasksPresent;
	}

	public boolean isCreatedViaAPI() {
		return createdViaAPI;
	}

	public void setCreatedViaAPI(boolean createdViaAPI) {
		this.createdViaAPI = createdViaAPI;
	}
	public List<SOManageScope> getSoManageScope() {
		return soManageScope;
	}

	public void setSoManageScope(List<SOManageScope> soManageScope) {
		this.soManageScope = soManageScope;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}	
	
	public List<SOTask> getTasksForScopeChangeComparison(){
		List<SOTask> taskList = new ArrayList<SOTask>();
		for(SOTask task : this.tasks){
			if(task.getAutoInjectedInd() == 1 && !task.getTaskType().equals(SOTaskType.Delivery.getId()) && !task.getTaskStatus().equals("DELETED")){
				taskList.add(task);
			}
		}
		return taskList;
	}
	
	public List<SOTask> getActiveTasks(){
		List<SOTask> taskList = new ArrayList<SOTask>();
		for(SOTask task : this.tasks){
			if(task.getTaskStatus().equals("ACTIVE")){
				taskList.add(task);
			}
		}
		return taskList;
	}
	
	public List<SOTask> getCompletedTasks(){
		List<SOTask> taskList = new ArrayList<SOTask>();
		for(SOTask task : this.tasks){
			if(task.getTaskStatus().equals("COMPLETED")){
				taskList.add(task);
			}
		}
		return taskList;
	}
	
	public List<SOTask> getNonDeletedTasks(){
		List<SOTask> taskList = new ArrayList<SOTask>();
		for(SOTask task : this.tasks){
			if(!task.getTaskStatus().equals("DELETED")){
				taskList.add(task);
			}
		}
		return taskList;
	}
	
	
	public SOTask getDeliveryTask(){
		//this is based on the assumption that an SO will have only a single delivery Task
		SOTask dTask = null;
		for(SOTask task : this.tasks){
			logger.info("Manage scope ind :"+task.getManageScopeId());
			logger.info("Task Type:"+task.getTaskType());
			logger.info("Task Type :"+task.getTaskStatus());
			/**SL-19992:Added completed status to retrieve DT to prevent addition of extra delivery task in work started status while non scope updation file*/
			if(task.getManageScopeId()==null && task.getTaskType().equals(SOTaskType.Delivery.getId()) && (task.getTaskStatus().equals("ACTIVE")||task.getTaskStatus().equals("COMPLETED"))){
				dTask = task;
				break;
			}
		}
		return dTask;
	}
	
	public SOTask getCanceledDeliveryTask(){
		//this is based on the assumption that an SO will have only a single delivery Task
		SOTask dTask = null;
		for(SOTask task : this.tasks){
			if(task.getManageScopeId()==null && task.getTaskType().equals(SOTaskType.Delivery.getId()) && task.getTaskStatus().equals("CANCELED")){
				dTask = task;
				break;
			}
		}
		return dTask;
	}

	public Set<SOCancellation> getSocancellations() {
		return socancellations;
	}

	public void setSocancellations(Set<SOCancellation> socancellations) {
		this.socancellations = socancellations;
	}
	
	public List<SOTask> getManageScopeTasks(){
		List<SOTask> taskList = new ArrayList<SOTask>();
		for(SOTask task : this.tasks){
			if(null != task.getManageScopeId()){
				taskList.add(task);
			}
		}
		return taskList;
	}
	
	public BigDecimal getPartsPrice(){
		BigDecimal partsPrice = PricingUtil.ZERO;
		if (null != this.getSoProviderInvoiceParts()) {

			// Sprint 5, SLM-85 To include parts status of "Installed".

			for (SOProviderInvoiceParts parts : this
					.getSoProviderInvoiceParts()) {

				if (null != parts.getPartStatus()
						&& parts.getPartStatus().equalsIgnoreCase(
								"Installed")) {
					if (null != parts.getFinalPrice()) {
						partsPrice = partsPrice.add(parts.getFinalPrice());
					}
				}
			}

		}
		return partsPrice;
	}

	public List<SOPartLaborPriceReason> getSoPartLaborPriceReason() {
		return soPartLaborPriceReason;
	}

	public void setSoPartLaborPriceReason(
			List<SOPartLaborPriceReason> soPartLaborPriceReason) {
		this.soPartLaborPriceReason = soPartLaborPriceReason;
	}
	
	//SL-18007 Added so_price_change_history 
	public List<SOPriceChangeHistory> getSoPriceChangeHistory() {
			return soPriceChangeHistory;
	}
	@XmlElementWrapper()
	@XmlElement(name = "soPriceHistory")
	public void setSoPriceChangeHistory(
				List<SOPriceChangeHistory> soPriceChangeHistory) {
			this.soPriceChangeHistory = soPriceChangeHistory;
			if( this.soPriceChangeHistory != null ) {
			    for(SOChild child : this.soPriceChangeHistory){
				    child.setServiceOrder(this);
			    }
		    }
	}
	
	/**@Description:SMP-1346:Checking whether the addOns from updateFile and that from DB is same or Not
	 * @param addOnFile
	 * @param addOnExists
	 * @return SOAddon
	 */
	private SOAddon checkAlreadyExistsAddOn(SOAddon addOnFile, SOAddon addOnExists) {
		if(null != addOnFile && null != addOnExists ){
			//This will check addOns from Db and Update file whose misc_Ind is 1
			if(addOnFile.isMiscInd()== addOnExists.isMiscInd()&& addOnFile.isMiscInd()){
				     if(addOnFile.getSku().equals(addOnExists.getSku())){
						   addOnFile.setNewAddOne(false);
						}
			   }////This will check addOns from Db and Update file whose misc_Ind is 0
			else if(addOnFile.isMiscInd()== addOnExists.isMiscInd()&&(!addOnFile.isMiscInd())){
				   if(addOnFile.getSku().equals(addOnExists.getSku())){
					   if(addOnFile.getDescription().equals(addOnExists.getDescription())){
					     addOnFile.setNewAddOne(false);
					   }
					}
			   }
		   }
		return addOnFile;
	}
	
	@Transient
	private boolean fromCancelFlow;
	
	@Transient
	private boolean fromCompletionFlow;
	
	@Transient
	private boolean fromClosureFlow;
	
	@Transient
	private boolean fromRIScopeChangeFlowWhileCompleted;
	
	@Transient
	private boolean fromManageScope;
	
	@Transient
	private boolean fromUngroupSOFlowCancellation;
	
	public boolean isFromManageScope() {
		return fromManageScope;
	}

	public void setFromManageScope(boolean fromManageScope) {
		this.fromManageScope = fromManageScope;
	}

	public boolean isFromCompletionFlow() {
		return fromCompletionFlow;
	}

	public boolean isFromRIScopeChangeFlowWhileCompleted() {
		return fromRIScopeChangeFlowWhileCompleted;
	}

	public void setFromRIScopeChangeFlowWhileCompleted(
			boolean fromRIScopeChangeFlowWhileCompleted) {
		this.fromRIScopeChangeFlowWhileCompleted = fromRIScopeChangeFlowWhileCompleted;
	}

	public void setFromCompletionFlow(boolean fromCompletionFlow) {
		this.fromCompletionFlow = fromCompletionFlow;
	}

	public boolean isFromClosureFlow() {
		return fromClosureFlow;
	}

	public void setFromClosureFlow(boolean fromClosureFlow) {
		this.fromClosureFlow = fromClosureFlow;
	}	

	public boolean isFromCancelFlow() {
		return fromCancelFlow;
	}

	public void setFromCancelFlow(boolean fromCancelFlow) {
		this.fromCancelFlow = fromCancelFlow;
	}

	public boolean isFromUngroupSOFlowCancellation() {
		return fromUngroupSOFlowCancellation;
	}

	public void setFromUngroupSOFlowCancellation(
			boolean fromUngroupSOFlowCancellation) {
		this.fromUngroupSOFlowCancellation = fromUngroupSOFlowCancellation;
	}

	public List<TierRouteProviders> getTierRoutedResources() {
		return tierRoutedResources;
	}

	public void setTierRoutedResources(List<TierRouteProviders> tierRoutedResources) {
		this.tierRoutedResources = tierRoutedResources;
	}

	public Integer getRescheduleRole() {
		return rescheduleRole;
	}

	public void setRescheduleRole(Integer rescheduleRole) {
		this.rescheduleRole = rescheduleRole;
	}

	public List<SOSalesCheckItems> getItems() {
		return items;
	}

	public void setItems(List<SOSalesCheckItems> items) {
		this.items = items;
	}

	public Long getInhomeAcceptedFirm() {
		return inhomeAcceptedFirm;
	}

	public void setInhomeAcceptedFirm(Long inhomeAcceptedFirm) {
		this.inhomeAcceptedFirm = inhomeAcceptedFirm;
	}

	public String getInhomeAcceptedFirmName() {
		return inhomeAcceptedFirmName;
	}

	public void setInhomeAcceptedFirmName(String inhomeAcceptedFirmName) {
		this.inhomeAcceptedFirmName = inhomeAcceptedFirmName;
	}

	public boolean isSpendLimitDecrease() {
		return spendLimitDecrease;
	}

	public void setSpendLimitDecrease(boolean spendLimitDecrease) {
		this.spendLimitDecrease = spendLimitDecrease;
	}

	public List<SOServiceDatetimeSlot> getSoServiceDatetimeSlot() {
		return soServiceDatetimeSlot;
	}

	public void setSoServiceDatetimeSlot(List<SOServiceDatetimeSlot> soServiceDatetimeSlot) {
		this.soServiceDatetimeSlot = soServiceDatetimeSlot;
	}

	public void populateScheduleSlotsGMT() {
		if (null != this.getSoServiceDatetimeSlot() && StringUtils.isNotBlank(this.serviceLocationTimeZone)) {
			for (SOServiceDatetimeSlot slot : this.getSoServiceDatetimeSlot()) {
				if (null != slot.getServiceStartDate() && StringUtils.isNotBlank(slot.getServiceStartTime())) {
					Calendar startDateTime = TimeChangeUtil.getCalTimeFromParts(slot.getServiceStartDate(), slot.getServiceStartTime(),
							TimeZone.getTimeZone(this.serviceLocationTimeZone));
					slot.setServiceStartDateDB(TimeChangeUtil.getDate(startDateTime, TimeZone.getTimeZone("GMT")));
					slot.setServiceStartTimeDB(TimeChangeUtil.getTimeString(startDateTime, TimeZone.getTimeZone("GMT")));
				}

				if (null != slot.getServiceEndDate() && StringUtils.isNotBlank(slot.getServiceEndTime())) {
					Calendar endDateTime = TimeChangeUtil.getCalTimeFromParts(slot.getServiceEndDate(), slot.getServiceEndTime(),
							TimeZone.getTimeZone(this.serviceLocationTimeZone));
					slot.setServiceEndDateDB(TimeChangeUtil.getDate(endDateTime, TimeZone.getTimeZone("GMT")));
					slot.setServiceEndTimeDB(TimeChangeUtil.getTimeString(endDateTime, TimeZone.getTimeZone("GMT")));
				}
			}
		}
	}
}
