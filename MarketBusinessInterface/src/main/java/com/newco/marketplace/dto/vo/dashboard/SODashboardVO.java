package com.newco.marketplace.dto.vo.dashboard;

import java.util.ArrayList;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author zizrale
 */
public class SODashboardVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5906045423003186196L;
	// Vital Statistics panel
	private Integer posted;
	private Integer accepted;
	private Integer problem;
	private Integer drafted;
	private Integer bid;
	private Integer bulletinBoard;
	//Sl-21645
	private Integer estimationRequest;
	private Integer todays;
	private Integer received;
	private Integer numRatingsReceived;
	private Integer numRatingsGiven;
	private Integer pendingCancel;
	
	//R12_1
	//SL-20362
	private Integer pendingReschedule;
	
	// Vital statistics
	private Double lifetimeRating;
	private Double currentRating;
	private Double givenRating;
	
	
	//Lead Statitics panel
	
	private Integer statusNew;
	private Integer working;
	private Integer scheduled;
	private Integer completed;
	private Integer cancelled;
	private Integer stale;
	private Integer totalLeads;
	private Integer conversionRate;
	private Integer averageResponse;
	private String goal;
	
	//buyer Lead statitics Panel
	
	private Integer buyerMatched;
	private Integer buyerUnMatched;
	private Integer buyerCompleted;
	private Integer buyerCancelled;
	


	
	
	// Finance Manager
	private double currentBalance;
	private double availableBalance;
	private String currentBalanceFormat;
	private String availableBalanceFormat;
	
	// List of star images to display for ratings (empty, half, full).
	private ArrayList<String> lifetimeStars;
	private ArrayList<String> currentStars;
	private ArrayList<String> givenStars;
	
	// Overall Status Monitor
	private String profileChanges;
	private Integer numIssues;
	private Integer numTechniciansUnapproved;
	private Integer numTechniciansApproved;
	private String firmStatus;
	
	//SL-19667 include background check status in Status Monitor
	private Integer bcNotStarted;
	private Integer bcInProcess;
	private Integer bcPendingSubmission;
	private Integer bcNotCleared;
	private Integer bcClear;
	private Integer bcRecertificationDue;

	
	// Received Service Orders
	private Integer totalOrders;
	private Double totalDollars = 0.0;
	
	//Rating value that maps on a particular star image
	private int lifetimeStarsNumber;
	private int currentStarsNumber;
	private int givenStarsNumber;
	
	
	public SODashboardVO()
	{
	}
	
	
	//R12_1
	//SL-20362
	public Integer getPendingReschedule() {
		return pendingReschedule;
	}


	public void setPendingReschedule(Integer pendingReschedule) {
		this.pendingReschedule = pendingReschedule;
	}



	public Integer getAccepted() {
		return accepted;
	}
	public void setAccepted(Integer accepted) {
		this.accepted = accepted;
	}
	public Integer getProblem() {
		return problem;
	}
	public void setProblem(Integer problem) {
		this.problem = problem;
	}
	public Integer getTodays() {
		return todays;
	}
	public void setTodays(Integer todays) {
		this.todays = todays;
	}
	
	public Integer getBid() {
		return bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}
	
	public Integer getBulletinBoard() {
		return bulletinBoard;
	}

	public void setBulletinBoard(Integer bulletinBoard) {
		this.bulletinBoard = bulletinBoard;
	}

	public Integer getPosted() {
		return posted;
	}
	public void setPosted(Integer posted) {
		this.posted = posted;
	}
	public Integer getNumRatingsReceived() {
		return numRatingsReceived;
	}
	public void setNumRatingsReceived(Integer numRatingsReceived) {
		this.numRatingsReceived = numRatingsReceived;
	}
	public Integer getNumRatingsGiven() {
		return numRatingsGiven;
	}
	public void setNumRatingsGiven(Integer numRatingsGiven) {
		this.numRatingsGiven = numRatingsGiven;
	}
	public Double getLifetimeRating() {
		return lifetimeRating;
	}
	public void setLifetimeRating(Double lifetimeRating) {
		this.lifetimeRating = lifetimeRating;
	}
	public Double getCurrentRating() {
		return currentRating;
	}
	public void setCurrentRating(Double currentRating) {
		this.currentRating = currentRating;
	}
	public Double getGivenRating() {
		return givenRating;
	}
	public void setGivenRating(Double givenRating) {

		this.givenRating = givenRating;
	}
	







	public ArrayList<String> getCurrentStars() {
		return currentStars;
	}




	public void setCurrentStars(ArrayList<String> currentStars) {
		this.currentStars = currentStars;
	}




	public ArrayList<String> getGivenStars() {
		return givenStars;
	}




	public void setGivenStars(ArrayList<String> givenStars) {
		this.givenStars = givenStars;
	}



	public ArrayList<String> getLifetimeStars() {
		return lifetimeStars;
	}




	public void setLifetimeStars(ArrayList<String> lifetimeStars) {
		this.lifetimeStars = lifetimeStars;
	}




	public double getAvailableBalance() {
		return availableBalance;
	}




	public void setAvailableBalance(double ab) {
		this.availableBalance = ab;
	}




	public double getCurrentBalance() {
		return currentBalance;
	}




	public void setCurrentBalance(double cb) {
		this.currentBalance = cb;
	}




	public String getProfileChanges() {
		return profileChanges;
	}




	public void setProfileChanges(String profileChanges) {
		this.profileChanges = profileChanges;
	}




	public Integer getNumIssues() {
		return numIssues;
	}




	public void setNumIssues(Integer numIssues) {
		this.numIssues = numIssues;
	}




	public Integer getNumTechniciansUnapproved() {
		return numTechniciansUnapproved;
	}




	public void setNumTechniciansUnapproved(Integer numTechniciansUnapproved) {
		this.numTechniciansUnapproved = numTechniciansUnapproved;
	}




	public Integer getTotalOrders() {
		return totalOrders;
	}




	public void setTotalOrders(Integer totalOrders) {
		this.totalOrders = totalOrders;
				
	}




	public Double getTotalDollars() {
		return totalDollars;
	}




	public void setTotalDollars(Double td) {
		this.totalDollars = td;
		
		// Only want 2 decimal places		
		if(td >=0.0)		
			totalDollars = Math.floor(totalDollars * 100.0) / 100.0;
		else
			totalDollars = 0.0;
		
	}

	public Integer getDrafted() {
		return drafted;
	}

	public void setDrafted(Integer drafted) {
		this.drafted = drafted;
	}

	public Integer getReceived() {
		return received;
	}

	public void setReceived(Integer received) {
		this.received = received;
	}

	public int getLifetimeStarsNumber() {
		return lifetimeStarsNumber;
	}

	public void setLifetimeStarsNumber(int lifetimeStarsNumber) {
		this.lifetimeStarsNumber = lifetimeStarsNumber;
	}

	public int getCurrentStarsNumber() {
		return currentStarsNumber;
	}

	public void setCurrentStarsNumber(int currentStarsNumber) {
		this.currentStarsNumber = currentStarsNumber;
	}

	public int getGivenStarsNumber() {
		return givenStarsNumber;
	}

	public void setGivenStarsNumber(int givenStarsNumber) {
		this.givenStarsNumber = givenStarsNumber;
	}

	/**
	 * @return the firmStatus
	 */
	public String getFirmStatus() {
		return firmStatus;
	}

	/**
	 * @param firmStatus the firmStatus to set
	 */
	public void setFirmStatus(String firmStatus) {
		this.firmStatus = firmStatus;
	}

	/**
	 * @return the numTechniciansApproved
	 */
	public Integer getNumTechniciansApproved() {
		return numTechniciansApproved;
	}

	/**
	 * @param numTechniciansApproved the numTechniciansApproved to set
	 */
	public void setNumTechniciansApproved(Integer numTechniciansApproved) {
		this.numTechniciansApproved = numTechniciansApproved;
	}

	public String getCurrentBalanceFormat() {
		return currentBalanceFormat;
	}

	public void setCurrentBalanceFormat(String currentBalanceFormat) {
		this.currentBalanceFormat = currentBalanceFormat;
	}

	public String getAvailableBalanceFormat() {
		return availableBalanceFormat;
	}

	public void setAvailableBalanceFormat(String availableBalanceFormat) {
		this.availableBalanceFormat = availableBalanceFormat;
	}

	public Integer getPendingCancel() {
		return pendingCancel;
	}

	public void setPendingCancel(Integer pendingCancel) {
		this.pendingCancel = pendingCancel;
	}

	public Integer getStatusNew() {
		return statusNew;
	}

	public void setStatusNew(Integer statusNew) {
		this.statusNew = statusNew;
	}

	public Integer getWorking() {
		return working;
	}

	public void setWorking(Integer working) {
		this.working = working;
	}

	public Integer getScheduled() {
		return scheduled;
	}

	public void setScheduled(Integer scheduled) {
		this.scheduled = scheduled;
	}

	public Integer getCompleted() {
		return completed;
	}

	public void setCompleted(Integer completed) {
		this.completed = completed;
	}

	public Integer getCancelled() {
		return cancelled;
	}

	public void setCancelled(Integer cancelled) {
		this.cancelled = cancelled;
	}

	public Integer getStale() {
		return stale;
	}

	public void setStale(Integer stale) {
		this.stale = stale;
	}

	public Integer getTotalLeads() {
		return totalLeads;
	}

	public void setTotalLeads(Integer totalLeads) {
		this.totalLeads = totalLeads;
	}

	public Integer getConversionRate() {
		return conversionRate;
	}

	public void setConversionRate(Integer conversionRate) {
		this.conversionRate = conversionRate;
	}

	public Integer getAverageResponse() {
		return averageResponse;
	}

	public void setAverageResponse(Integer averageResponse) {
		this.averageResponse = averageResponse;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public Integer getBuyerMatched() {
		return buyerMatched;
	}

	public void setBuyerMatched(Integer buyerMatched) {
		this.buyerMatched = buyerMatched;
	}

	public Integer getBuyerUnMatched() {
		return buyerUnMatched;
	}

	public void setBuyerUnMatched(Integer buyerUnMatched) {
		this.buyerUnMatched = buyerUnMatched;
	}

	public Integer getBuyerCompleted() {
		return buyerCompleted;
	}

	public void setBuyerCompleted(Integer buyerCompleted) {
		this.buyerCompleted = buyerCompleted;
	}

	public Integer getBuyerCancelled() {
		return buyerCancelled;
	}

	public void setBuyerCancelled(Integer buyerCancelled) {
		this.buyerCancelled = buyerCancelled;
	}

	public Integer getBcNotStarted() {
		return bcNotStarted;
	}

	public void setBcNotStarted(Integer bcNotStarted) {
		this.bcNotStarted = bcNotStarted;
	}

	public Integer getBcInProcess() {
		return bcInProcess;
	}

	public void setBcInProcess(Integer bcInProcess) {
		this.bcInProcess = bcInProcess;
	}

	public Integer getBcPendingSubmission() {
		return bcPendingSubmission;
	}

	public void setBcPendingSubmission(Integer bcPendingSubmission) {
		this.bcPendingSubmission = bcPendingSubmission;
	}

	public Integer getBcNotCleared() {
		return bcNotCleared;
	}

	public void setBcNotCleared(Integer bcNotCleared) {
		this.bcNotCleared = bcNotCleared;
	}

	public Integer getBcClear() {
		return bcClear;
	}

	public void setBcClear(Integer bcClear) {
		this.bcClear = bcClear;
	}

	public Integer getBcRecertificationDue() {
		return bcRecertificationDue;
	}

	public void setBcRecertificationDue(Integer bcRecertificationDue) {
		this.bcRecertificationDue = bcRecertificationDue;
	}


	public Integer getEstimationRequest() {
		return estimationRequest;
	}


	public void setEstimationRequest(Integer estimationRequest) {
		this.estimationRequest = estimationRequest;
	}

	 
	
	
}
