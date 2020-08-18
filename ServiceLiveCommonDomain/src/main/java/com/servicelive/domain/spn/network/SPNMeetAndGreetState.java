package com.servicelive.domain.spn.network;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.lookup.LookupSPNMeetAndGreetState;

/**
 * 
 * @author svanloon
 *
 */
@Entity
@Table ( name = "spnet_meetngreet_state")
public class SPNMeetAndGreetState extends LoggableBaseDomain {

	private static final long serialVersionUID = 20090129L;

	@EmbeddedId
	private SPNMeetAndGreetStatePk meetAndGreetStatePk;

	@ManyToOne
	@JoinColumn(name = "meetngreet_state_id", unique = false, nullable = false, insertable = true, updatable = true)
	private LookupSPNMeetAndGreetState state;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="meetngreet_date", unique = false, nullable = true, insertable = true, updatable = true)
    private Date meetAndGreetDate;

	@Column(name="comments", unique = false, nullable = true, insertable = true, updatable = true, length=150)
    private String comments;

	@Column(name="meetngreet_person", unique = false, nullable = true, insertable = true, updatable = true, length=100)
    private String meetAndGreetPerson;

	/**
	 * @return the meetAndGreetStatePk
	 */
	public SPNMeetAndGreetStatePk getMeetAndGreetStatePk() {
		return meetAndGreetStatePk;
	}

	/**
	 * @param meetAndGreetStatePk the meetAndGreetStatePk to set
	 */
	public void setMeetAndGreetStatePk(SPNMeetAndGreetStatePk meetAndGreetStatePk) {
		this.meetAndGreetStatePk = meetAndGreetStatePk;
	}

	/**
	 * @return the state
	 */
	public LookupSPNMeetAndGreetState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(LookupSPNMeetAndGreetState state) {
		this.state = state;
	}

	/**
	 * @return the meetAndGreetDate
	 */
	public Date getMeetAndGreetDate() {
		return meetAndGreetDate;
	}

	/**
	 * @param meetAndGreetDate the meetAndGreetDate to set
	 */
	public void setMeetAndGreetDate(Date meetAndGreetDate) {
		this.meetAndGreetDate = meetAndGreetDate;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the meetAndGreetPerson
	 */
	public String getMeetAndGreetPerson() {
		return meetAndGreetPerson;
	}

	/**
	 * @param meetAndGreetPerson the meetAndGreetPerson to set
	 */
	public void setMeetAndGreetPerson(String meetAndGreetPerson) {
		this.meetAndGreetPerson = meetAndGreetPerson;
	}                                                                                                 

}
