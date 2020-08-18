package com.newco.marketplace.search.spellchecker.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;

import com.newco.marketplace.search.spellchecker.ISpellChecker;
import com.newco.marketplace.search.spellchecker.SpellCheckResponseDto;

public class SolrSpellChecker implements ISpellChecker {
	private CommonsHttpSolrServer server;

	public SolrSpellChecker(CommonsHttpSolrServer server) {
		this.server = server;
	}

	public SpellCheckResponseDto checkSpell(String word, int requestedsuggestion) {
		// TODO Auto-generated method stub
		SpellCheckResponseDto dto = new SpellCheckResponseDto(true);
		try {
			SolrQuery query = new  SolrQuery();
			query.setQueryType("dismax");
			query.set("rows", 0);
			query.setQuery(word);		
			query.set("spellcheck", "true");
			query.set("spellcheck.collate", "true");
			query.set("spellcheck.onlyMorePopular", "false");
			QueryResponse qr;

			qr = server.query(query);

			SpellCheckResponse sc = qr.getSpellCheckResponse();
			boolean correctSpellFlag = sc.isCorrectlySpelled();
			dto.setCorrect(correctSpellFlag);
			if(!correctSpellFlag) {
				String collationWord = sc.getCollatedResult();
				if(collationWord != null && !collationWord.equalsIgnoreCase(word)) {
					dto.setCorrect(false);
					dto.getSuggestions().add(collationWord);
				}
			}
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto;
	}

}
