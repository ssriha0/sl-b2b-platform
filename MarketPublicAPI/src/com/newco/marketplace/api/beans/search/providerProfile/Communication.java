package com.newco.marketplace.api.beans.search.providerProfile;

public enum Communication {
	HIGHLY_SATISFIED(5,10),
	VERY_SATISFIED(4,11),
	SATISFIED(3,12),
	SOMEWHAT_SATISFIED(2,13),
	DISSATISFIED(1,14);

	private int rank;
	private int answerId;
	
	private Communication(int rank, int answerId) {
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
				answerId = Communication.HIGHLY_SATISFIED.getAnswerId();
				break;
			case 4:
				answerId = Communication.VERY_SATISFIED.getAnswerId();
				break;
			case 3:
				answerId = Communication.SATISFIED.getAnswerId();
				break;
			case 2:
				answerId = Communication.SOMEWHAT_SATISFIED.getAnswerId();
				break;
			case 1:
				answerId = Communication.DISSATISFIED.getAnswerId();
				break;
		}
		return answerId;
	}
}
