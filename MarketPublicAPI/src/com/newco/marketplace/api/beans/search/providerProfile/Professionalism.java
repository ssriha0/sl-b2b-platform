package com.newco.marketplace.api.beans.search.providerProfile;

public enum Professionalism {
	HIGHLY_SATISFIED(5,20),
	VERY_SATISFIED(4,21),
	SATISFIED(3,22),
	SOMEWHAT_SATISFIED(2,23),
	DISSATISFIED(1,24);

	private int rank;
	private int answerId;
	
	private Professionalism(int rank, int answerId) {
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
				answerId = Professionalism.HIGHLY_SATISFIED.getAnswerId();
				break;
			case 4:
				answerId = Professionalism.VERY_SATISFIED.getAnswerId();
				break;
			case 3:
				answerId = Professionalism.SATISFIED.getAnswerId();
				break;
			case 2:
				answerId = Professionalism.SOMEWHAT_SATISFIED.getAnswerId();
				break;
			case 1:
				answerId = Professionalism.DISSATISFIED.getAnswerId();
				break;
		}
		return answerId;
	}
}
