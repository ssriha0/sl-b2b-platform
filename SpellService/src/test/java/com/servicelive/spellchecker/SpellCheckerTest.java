package com.servicelive.spellchecker;

import org.junit.Assert;
import org.junit.Test;

public class SpellCheckerTest {

	private SpellChecker checker;
	
	@Test
	public void testIsINETWord(){
		checker = new SpellChecker();
		String word = "https://www.google.com";
		
		boolean isINETWord = false; 
		isINETWord = checker.isINETWord(word);
		Assert.assertTrue(isINETWord);
	}
	
	
}
