package com.servicelive.spellchecker;

import java.util.HashSet;
import java.util.Set;

public class SpellCheckResultDto {
	boolean correct;
	Set<String> suggestions;
	
	public SpellCheckResultDto(boolean flag) {
		this.correct = flag;
		suggestions = new HashSet<String>();
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public Set<String> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(Set<String> suggestions) {
		this.suggestions = suggestions;
	}

}
