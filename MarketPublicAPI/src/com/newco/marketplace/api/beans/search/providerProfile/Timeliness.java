package com.newco.marketplace.api.beans.search.providerProfile;

public enum Timeliness {
	HIGHLY_SATISFIED(5,1),
	VERY_SATISFIED(4,2),
	SATISFIED(3,3),
	SOMEWHAT_SATISFIED(2,4),
	DISSATISFIED(1,5);

	private int rank;
	private int answerId;
	
	private Timeliness(int rank, int answerId) {
		this.rank = rank;
		this.answerId = answerId;
	}

	public int getAnswerId() {
		return answerId;
	}

	public int getRank() {
		return rank;
	}
	
	public static int getAnswerId(int givenRank){
		int answerId = 0;
		switch(givenRank){
			case 5:
				answerId = Timeliness.HIGHLY_SATISFIED.getAnswerId();
				break;
			case 4:
				answerId = Timeliness.VERY_SATISFIED.getAnswerId();
				break;
			case 3:
				answerId = Timeliness.SATISFIED.getAnswerId();
				break;
			case 2:
				answerId = Timeliness.SOMEWHAT_SATISFIED.getAnswerId();
				break;
			case 1:
				answerId = Timeliness.DISSATISFIED.getAnswerId();
				break;
		}
		return answerId;
	}
}
