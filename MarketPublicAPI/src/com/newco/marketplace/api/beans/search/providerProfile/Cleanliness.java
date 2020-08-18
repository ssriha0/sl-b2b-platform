package com.newco.marketplace.api.beans.search.providerProfile;

public enum Cleanliness {
	HIGHLY_SATISFIED(5,50),
	VERY_SATISFIED(4,51),
	SATISFIED(3,52),
	SOMEWHAT_SATISFIED(2,53),
	DISSATISFIED(1,54);

	private int rank;
	private int answerId;
	
	private Cleanliness(int rank, int answerId) {
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
				answerId = Cleanliness.HIGHLY_SATISFIED.getAnswerId();
				break;
			case 4:
				answerId = Cleanliness.VERY_SATISFIED.getAnswerId();
				break;
			case 3:
				answerId = Cleanliness.SATISFIED.getAnswerId();
				break;
			case 2:
				answerId = Cleanliness.SOMEWHAT_SATISFIED.getAnswerId();
				break;
			case 1:
				answerId = Cleanliness.DISSATISFIED.getAnswerId();
				break;
		}
		return answerId;
	}
}
