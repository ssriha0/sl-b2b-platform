package com.servicelive.domain.buyer;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.servicelive.domain.LoggableBaseDomain;

@Entity()
@Table(name = "buyer_hold_time")
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class BuyerHoldTime extends LoggableBaseDomain {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6744094440737419820L;
	
	@Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="hold_time_id", unique=true, nullable=false)
    private Integer id;

	@Column(name = "day_diff")
	private Integer dayDiff;
	
	@Column(name = "hold_time")
	private Integer holdTime;
	
	@Column(name = "buyer_id")
	private Long buyerId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDayDiff() {
		return dayDiff;
	}

	public void setDayDiff(Integer dayDiff) {
		this.dayDiff = dayDiff;
	}

	public Integer getHoldTime() {
		return holdTime;
	}

	public void setHoldTime(Integer holdTime) {
		this.holdTime = holdTime;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}
