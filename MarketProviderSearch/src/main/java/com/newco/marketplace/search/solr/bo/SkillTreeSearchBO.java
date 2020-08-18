package com.newco.marketplace.search.solr.bo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import com.newco.marketplace.search.solr.dto.SkillTreeDto;
import com.newco.marketplace.search.spellchecker.ISpellChecker;
import com.newco.marketplace.search.spellchecker.SpellCheckResponseDto;
import com.newco.marketplace.search.spellchecker.impl.JazzySpellChecker;
import com.newco.marketplace.search.spellchecker.impl.YahooSpellChecker;
import com.newco.marketplace.search.vo.BaseRequestVO;
import com.newco.marketplace.search.vo.BaseResponseVO;
import com.newco.marketplace.search.vo.SearchQueryResponse;
import com.newco.marketplace.search.vo.SkillRequestVO;
import com.newco.marketplace.search.vo.SkillTreeResponseVO;


public class SkillTreeSearchBO extends BaseSearchBO{
	private Logger logger = Logger.getLogger(SkillTreeSearchBO.class);

	QueryResponse qr;

	private ISpellChecker spellChecker;

	public SkillTreeSearchBO(String solrServerUrl,String proxyHost,Integer proxyPort, boolean isProxySet, 
			String yahooAppID, boolean isYahooSpellCheck, Long yahooServiceTimeOut, String yahooServiceURL) {
		super(solrServerUrl);

		if (isYahooSpellCheck) {
			spellChecker = new YahooSpellChecker(proxyHost,proxyPort, isProxySet, 
					yahooAppID, yahooServiceTimeOut, yahooServiceURL);
		} else {
			//spellChecker = new SolrSpellChecker(server);
			spellChecker = new JazzySpellChecker(solrServerUrl);
		}
	}

	private long getCount(String requestString) {
		SolrQuery query = new  SolrQuery();
		query.setQuery(requestString);
		query.setIncludeScore(true);
		query.setRows(0);
		query.setQueryType("dismax");
		try {
			qr = server.query(query);
			SolrDocumentList sdl = qr.getResults();
			if (sdl != null)
				return sdl.getNumFound();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public SearchQueryResponse query(BaseRequestVO baseReq) {
		List<BaseResponseVO> voList = new ArrayList<BaseResponseVO>();
		SkillRequestVO skillTreeVO = (SkillRequestVO)baseReq;
		String requestString = "";
		String collationWord = null;
		QueryResponse qr = null;
		boolean spellCheck = true;

		SolrQuery query = new  SolrQuery();
		if(skillTreeVO.getSkillTree() != null){
			requestString = skillTreeVO.getSkillTree().trim();
		}
		if (!skillTreeVO.isSpellCheck()) {
			spellCheck = false;
		}

		logger.info("requestString::"+requestString);
		
		// Escape special characters to avoid exclusion
		requestString = escapeString(requestString);
		
		//logger.info("Query for thrid party spellcheck service: " + query);
		if (spellCheck) 
		  collationWord = checkSpelling(requestString);

		//if (collationWord == null) 
		{ 	
			logger.info("requestString::"+requestString);
			query.setQuery(requestString);
			query.setIncludeScore(true);
			query.setRows(Integer.valueOf(SkillTreeDto.MAX_DISAMBIGUATION_RESULT));
			query.set("score", "true");
			query.setQueryType("dismax");
			
			if (requestString.compareTo("") == 0) {
				query.setQuery("*:*");			
				query.set("rows", 2000);
			} else {			
				query.set("rows", 200);
				query.setQuery(requestString);		
			}
			logger.info("Solr Query:" + query.toString());
			
			query.set("spellcheck", false);
			query.setQuery(requestString);
			voList.add(addSpelllCheckResult(requestString, collationWord));

			logger.info("final Solr Query:" + query.toString());
			try {
				qr = server.query(query);
				List<SkillTreeDto> beans = qr.getBeans(SkillTreeDto.class);
				for (SkillTreeDto dto : beans) {			
					voList.add(convertDTOToVO(dto));
				}
			} catch(SolrServerException e) {
				logger.error("Error in solr query: " + e.getMessage());
			}
		} 
		/* else {//wrong spelling
			voList.add(addSpelllCheckResult(requestString, collationWord));
		} */
		return new SearchQueryResponse(voList);    	
	}


	private String escapeString(String input){
		
		String[] metaCharacters = {"\\", "-", "&&", "||", "!", "(", ")", "{", "}", "[", "]", "^", "~", "*", "?", ":", ";"};
		
		String outputString="";
	    for (int i = 0 ; i < metaCharacters.length ; i++){
	        if(input.contains(metaCharacters[i])){
	            outputString = input.replace(metaCharacters[i],"\\"+metaCharacters[i]);
	            input = outputString;
	        }
	    }
	    return input;
	}


	private String checkSpelling(String requestString) {
		final int suggestionCount = 3;
		long maxCount = 0;
		int idx = 0;

		try {
			SpellCheckResponseDto response = spellChecker.checkSpell(requestString, suggestionCount);
			if (response.isCorrect())
				return null;
			else {
				int loopCount = response.getSuggestions().size();
				if (loopCount > suggestionCount)
					loopCount = suggestionCount;

				for (int i = 0; i < loopCount; i++) {
					long ll = getCount(response.getSuggestions().get(i));
					if (ll > maxCount) {
						maxCount = ll;
						idx = i;
					}
				}    			 			
				return response.getSuggestions().get(idx);
			}
		} catch(Exception e) {
			logger.error("Error in spellcheck service: " + e.getMessage());
		}
		return null;
	}


	private SkillTreeResponseVO convertDTOToVO(SkillTreeDto dto){
		SkillTreeResponseVO responseVO = new SkillTreeResponseVO();
		responseVO.setNodeId(dto.getNodeid());
		responseVO.setNodeName(dto.getNodename());
		responseVO.setParentNodeId(dto.getParentid());
		responseVO.setParentNodeName(dto.getParentname());
		responseVO.setLevel(dto.getLevel());
		responseVO.setScore(new DecimalFormat("0.00000000000").format(dto.getScore()));
		return responseVO;
	}

	private SkillTreeResponseVO addSpelllCheckResult(String originalTer, String collation){
		return new SkillTreeResponseVO(originalTer, collation);
	}

}

