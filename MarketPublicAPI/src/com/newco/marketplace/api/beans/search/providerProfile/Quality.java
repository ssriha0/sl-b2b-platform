package com.newco.marketplace.api.beans.search.providerProfile;

public enum Quality {
	HIGHLY_SATISFIED(5,30),
	VERY_SATISFIED(4,31),
	SATISFIED(3,32),
	SOMEWHAT_SATISFIED(2,33),
	DISSATISFIED(1,34);

	private int rank;
	private int answerId;
	
	private Quality(int rank, int answerId) {
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
				answerId = Quality.HIGHLY_SATISFIED.getAnswerId();
				break;
			case 4:
				answerId = Quality.VERY_SATISFIED.getAnswerId();
				break;
			case 3:
				answerId = Quality.SATISFIED.getAnswerId();
				break;
			case 2:
				answerId = Quality.SOMEWHAT_SATISFIED.getAnswerId();
				break;
			case 1:
				answerId = Quality.DISSATISFIED.getAnswerId();
				break;
		}
		return answerId;
	}
}
