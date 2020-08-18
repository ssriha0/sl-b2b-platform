package com.newco.marketplace.api.beans.search.providerProfile;

public enum Value {
	HIGHLY_SATISFIED(5,40),
	VERY_SATISFIED(4,41),
	SATISFIED(3,42),
	SOMEWHAT_SATISFIED(2,43),
	DISSATISFIED(1,44);

	private int rank;
	private int answerId;
	
	private Value(int rank, int answerId) {
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
				answerId = Value.HIGHLY_SATISFIED.getAnswerId();
				break;
			case 4:
				answerId = Value.VERY_SATISFIED.getAnswerId();
				break;
			case 3:
				answerId = Value.SATISFIED.getAnswerId();
				break;
			case 2:
				answerId = Value.SOMEWHAT_SATISFIED.getAnswerId();
				break;
			case 1:
				answerId = Value.DISSATISFIED.getAnswerId();
				break;
		}
		return answerId;
	}
}
