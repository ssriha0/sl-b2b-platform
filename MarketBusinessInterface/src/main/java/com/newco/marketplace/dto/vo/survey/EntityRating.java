package com.newco.marketplace.dto.vo.survey;

/**
 * @author smagar1
 *
 */
public class EntityRating {

	private Integer id;
	private Integer entityId;
	private Integer entityTypeId;
	private Integer ratingCount;
	private Double ratingScore;
	private String scoreType;
	private String scoreTimeLineType;
	private String modifiedBy;	
	
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public void setEntityTypeId(Integer entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	public void setRatingCount(Integer ratingCount) {
		this.ratingCount = ratingCount;
	}

	public void setRatingScore(Double ratingScore) {
		this.ratingScore = ratingScore;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

	public void setScoreTimeLineType(String scoreTimeLineType) {
		this.scoreTimeLineType = scoreTimeLineType;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Integer getId() {
		return id;
	}

	public Integer getRatingCount() {
		return ratingCount;
	}

	public Double getRatingScore() {
		return ratingScore;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public Integer getEntityTypeId() {
		return entityTypeId;
	}

	public String getScoreType() {
		return scoreType;
	}

	public String getScoreTimeLineType() {
		return scoreTimeLineType;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

}
