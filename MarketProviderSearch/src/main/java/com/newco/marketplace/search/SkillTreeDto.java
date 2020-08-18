package com.newco.marketplace.search;


public class SkillTreeDto {
  
	public final static int ERROR = -1;	 
	public final static int AMBIGUOUS = 3;
	public final static int MULTI_CHOISE = 4;
	public final static int WRONG_SPELLING = 5;
	
	public static final float MAX_SCORE_DIFF = 2.0f;
	public static final int MAX_DISAMBIGUATION_RESULT = 8;
	
	//private List list;
	private String suggestedSpelling;
	private boolean wrongSpelling;

	//private List<String> skillList;
	
	private int status = -1;
    
    
	
//	public SkillTreeDto() {
//		this.list  = new ArrayList<ProviderProfileBean>(); 
//	}
//
//	public List<ProviderProfileBean> getList() {
//		return list;
//	}
	
//	public List<String> getSkillList() {
//		if (skillList != null)
//			return skillList;
//
//		skillList =  new ArrayList<String>();
//		for (ProviderProfileBean p: list) {
//			skillList.add(p.getSkill());
//		}
//
//		return skillList;
//	}

//	public void setList(List<ProviderProfileBean> list) {
//		this.list = list;
//		status = -1;
//	}

	public String getSuggestedSpelling() {
		return suggestedSpelling;
	}

	public void setSuggestedSpelling(String suggestedSpelling) {
		this.suggestedSpelling = suggestedSpelling;
	}

	public boolean isWrongSpelling() {
		return wrongSpelling;
	}

	public void setWrongSpelling(boolean wrongSpelling) {
		this.wrongSpelling = wrongSpelling;
	}

	public int getStatus() {
//		if (status == -1) {
//			calculate();
//		}
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}	

	/*
	private void calculate() {		
		int count  = 0;	
		float currentscoure = 0.0f;

		if (list == null) {
			status = ERROR;
			return;
		}

		if (list.size() == 0) {
			if (wrongSpelling == false) {
				status = ERROR;
			} else {
				status = WRONG_SPELLING;
			}
			return;
		}

		if (list.size() == 1) {
			ProviderProfileBean dto0 = list.get(0);
			status = dto0.getSkillLevel();
			return;
		}


		if (list.size() > 1) {
			ProviderProfileBean dto0 = list.get(0);
			ProviderProfileBean dto1 = list.get(1);
			if (dto0.getScore() - dto1.getScore() > MAX_SCORE_DIFF) {
				status = dto0.getSkillLevel();
				return;
			}
//			} else if (dto0.getSkill().equals(dto1.getSkill())) { // avoid infinite look of ambiguation				
//				status = AMBIGUOUS;
//				return;
//			}
		}

		// else show top tree category
		status = MULTI_CHOISE;


		//		for (ProviderSkill dto : list) {
		//			count ++;
		//			if (currentscoure == 0.0) {
		//				currentscoure = dto.getScore();
		//			} else if (dto.getScore() - currentscoure > 1.0) {
		//				break;
		//			} else {
		//				currentscoure = dto.getScore();
		//			}
		//
		//			if (count > 3) 
		//				break;
		//		}
	} */
}
