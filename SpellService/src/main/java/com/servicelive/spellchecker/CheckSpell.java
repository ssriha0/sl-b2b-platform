package com.servicelive.spellchecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.swabunga.spell.engine.SpellDictionary;
import com.swabunga.spell.engine.SpellDictionaryHashMap;

public class CheckSpell {
	File dic = new File("solr/conf/spellings.txt");
	//File dic = new File("/home/sldev/solr-tomcat/solr/conf/spellings.txt");
	
	SpellDictionary dictionary;
	SpellChecker spellChecker;
	private static CheckSpell obj;

	private CheckSpell () {
		try {
			if (dic.exists())
			  dictionary = new SpellDictionaryHashMap(this.dic);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		spellChecker = new SpellChecker(dictionary);
	}
	
	public static CheckSpell getInstance() {
			
		if (obj == null) {
			obj =  new CheckSpell();
		}
		
		return obj;
	}

	public SpellCheckResultDto checkSpell(String word, int count) {	
		return spellChecker.checkString(word, count);
	}
	
	/*
	public static void main(String arg[]) {
		SpellCheckResultDto dto = CheckSpell.getInstance().checkSpell("house, cleanin", 1);
		for (String val:dto.getSuggestions()) {
			System.out.println("Val:" + val);
		}
	}
	*/
}

