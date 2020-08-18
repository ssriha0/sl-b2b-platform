package com.newco.marketplace.search.spellchecker;

public interface ISpellChecker {
	SpellCheckResponseDto checkSpell(String word, int requestedsuggestion);
}
