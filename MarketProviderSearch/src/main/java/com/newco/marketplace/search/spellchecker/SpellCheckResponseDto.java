package com.newco.marketplace.search.spellchecker;

import java.util.ArrayList;
import java.util.List;

public class SpellCheckResponseDto {
	private boolean correct;
	private List<String> suggestions;
	private String word;
	
	public SpellCheckResponseDto(boolean flag) {
		this.correct = flag;
		suggestions = new ArrayList<String>();
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public List<String> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(List<String> suggestions) {
		this.suggestions = suggestions;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
}
