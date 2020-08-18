package com.newco.marketplace.business.businessImpl.providerSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.providerSearch.ProviderSkillRequestVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSkillResultsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSkillServiceTypeRequestVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.providerSearch.ProviderSearchDao;

/**
 * @author zizrale
 *
 * $Revision: 1.11 $ $Author: glacy $ $Date: 2008/05/29 21:52:15 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class RatingsCalculator {
	/**
	 * @param listOfProviders
	 * @return ArrayList
	 */
	public static List<ProviderSkillResultsVO> assignSkillRatingsToProviders(List<ProviderSkillResultsVO>listOfProviders ,
				List<ProviderSkillServiceTypeRequestVO> collectionOfParentStructs, 
				List<SkillNodeVO> origionalSkillNodes, Double weightFactorDecrement,
				ProviderSearchDao providerSearchDao ) throws DataServiceException{
		int total = 0;
	
		double rating = 0.0;  

		final class ScoringClass {
			int provider_id;
			Double current_score;
			Double current_decremented_score;
			Double Points;
			ProviderSkillResultsVO theVO;
			int coutOfSkillTypesPresen;
			
		}
		Map<Integer,ScoringClass > scoreCard = new HashMap<Integer,ScoringClass>();
		List<ProviderSkillResultsVO> newListOfProviders = new ArrayList<ProviderSkillResultsVO>();
		for(int indexInParentCollection = 0; 
				indexInParentCollection  < collectionOfParentStructs.size(); 
				indexInParentCollection++ ){

			ProviderSkillServiceTypeRequestVO currentProviderSkillServiceTypeRequestVO =  
					collectionOfParentStructs.get(indexInParentCollection);
			// level outside provider loop
			int levelOfThisTasksNode = origionalSkillNodes.get(indexInParentCollection).getLevel();
		    for(ProviderSkillResultsVO currentProviderResultVO :listOfProviders ){
		    	// loop through all of the guys I have in reference to the first ProviderSkillServiceTypeRequestVO 
		    	// and score them for the service nodes
		    	// this is score 1 for same level as target, score .5 for 1 off  = 1 * 1/2^difference ( starting at 0
		    	if(currentProviderSkillServiceTypeRequestVO.getAListOfTheSkillNodeIds().contains(currentProviderResultVO.getSkillNodeId()))
					{
		    			double score = 0;				
						/*
						 * If provider from main list !exists in my temp map 
						 * 	add him with the score in current_score
						 *  add his current_decremented_score
						 * else
						 * 	if he does exist and his score is lower
						 * 		replace his score
						 * 		replace his current_decremented_score
						 * 
						 */
						int delta_level = currentProviderResultVO.getSkillLevel() - levelOfThisTasksNode;
						if(Math.abs(delta_level) > 0){
							score = 1/(2^delta_level);
						}
						else{// need this for 0  << not really sure but whatever.
							score = 1;
						}
						if(scoreCard.containsKey(currentProviderResultVO.getProviderResourceId())){
							if(scoreCard.get(currentProviderResultVO.getProviderResourceId()).current_score < score){
								scoreCard.get(currentProviderResultVO.getProviderResourceId()).current_score = score;
								scoreCard.get(currentProviderResultVO.getProviderResourceId()).current_decremented_score = (weightFactorDecrement)*score;					
							}
						}else{
							ScoringClass aScore = new ScoringClass();
							aScore.provider_id = currentProviderResultVO.getProviderResourceId();
							aScore.current_score = score;
							aScore.current_decremented_score = (weightFactorDecrement)*score;
							aScore.theVO = currentProviderResultVO;
							scoreCard.put(currentProviderResultVO.getProviderResourceId(), aScore);
							newListOfProviders.add(currentProviderResultVO);
						}
					}//end if(currentProviderSkillServiceTypeRequestVO.getAListOfTheSkillNodeIds().
		    			//contains(currentProviderResultVO.getSkillNodeId()))
		    }//end for(ProviderSkillResultsVO currentProviderResultVO :listOfProviders ){
				/*
				 * Here we need to count the skill types we are looking for, and count the matches we are looking for.
				 * Theroretically we should have a 1 to 1 for node and skill type? so, I am not sure what to do here.
				 * I will assume that the skill I am looking for is in the same position as the request?
				 * 
				 * so that I have Nodes 3 and 5, and skill types 2 and 4, I will assume that node 3 is looking for skill type 3
				 * and node 5 is looking for skill type 4
				 * 
				 * If this holds true we could probably use this data to better in the initial query and run it once per skill?
				 * 
				 *  so apply the decrimenter to the score from above.
				 *  loop through tis result
				 *  	if the guy is here set his current_decrimented score to his current score  
				 * 
				 */
		    	// construct a request containing a list of guys, a list of skill nodes and a list of skill Types
		    	
		    	if(currentProviderSkillServiceTypeRequestVO.getSkillTypesList() != null && currentProviderSkillServiceTypeRequestVO.getSkillTypesList().size() > 0 && scoreCard.keySet().size() > 0){
		    		
			    
			        ProviderSkillRequestVO skillTypeRequest = new ProviderSkillRequestVO();
					skillTypeRequest.setResourceIdsList(new ArrayList<Integer>(scoreCard.keySet()));				
					
					// Here is where we are indexing the skill type array feighning correspondance that is not enforced.
					List<Integer> aList = new ArrayList<Integer>();
					
					
					try{
						aList.add(currentProviderSkillServiceTypeRequestVO.getSkillTypesList().get(indexInParentCollection));
					}catch(IndexOutOfBoundsException ie){
						aList = null;
					}
					if(aList != null){
						skillTypeRequest.setSkillTypesList(aList);
						
						skillTypeRequest.setSkillNodeList(currentProviderSkillServiceTypeRequestVO.getAListOfTheSkillNodeIds());
						// list of guys that have the skill type matched
				    	List<Integer> serviceTypeResults = providerSearchDao.getProvidersForServiceSkillType(skillTypeRequest);
				    	
				    	for(Integer skillGuyIndex : serviceTypeResults){
				    		if(scoreCard.containsKey(skillGuyIndex)){
				    			scoreCard.get(skillGuyIndex).current_decremented_score = scoreCard.get(skillGuyIndex).current_score;
				    		}
				    	}// end looping through skill type results		    	
				    	/*Set the current points to the current_decrimented_score for all in map */
					}// there was a mismatch in skill types to skill node ids.
		    	}// no skill types
		    	else{
		    		// no skill types so dont decriment score.
		    		for(ScoringClass aScore:scoreCard.values()){
			    		aScore.current_decremented_score = aScore.current_score;
			    	}
		    	}
		    	for(ScoringClass aScore:scoreCard.values()){
		    		if(aScore.Points == null)
		    			aScore.Points = 0.0;
		    		aScore.Points += aScore.current_decremented_score;
		    		aScore.current_score = 0.0;
		    		aScore.current_decremented_score = 0.0;
		    		
		    	}
		    	
		    	
		    }   //	END	for(int indexInParentCollection = 0; 
				//			indexInParentCollection  < collectionOfParentStructs.size(); 
				//			indexInParentCollection++ ){
			
	//			After loop through all of the collectionOfParentStructs
	//			SET POINTS = PPOINTS / collectionOfParentStructs.size()
    	for(ScoringClass aScore:scoreCard.values()){
    		aScore.Points = aScore.Points/collectionOfParentStructs.size();
    	}
    	for(ProviderSkillResultsVO providerResult :newListOfProviders){
    		providerResult.setProviderSkillRating(scoreCard.get(providerResult.getProviderResourceId()).Points);
    	}
    	return newListOfProviders;
    	
	}
	
	/**
	 * @param listOfProvidersFromHierarchies
	 * @return ArrayList<ProviderSkillResultsVO>
	 */
	public static ArrayList<ProviderSkillResultsVO> getAggregateProviderSkillsRatings(
		ArrayList<ArrayList<ProviderSkillResultsVO>> listOfProvidersFromHierarchies){
		ArrayList<ProviderSkillResultsVO> consolListFromHierarchies = new ArrayList<ProviderSkillResultsVO>();
		ArrayList<ProviderSkillResultsVO> uniqueConsolListFromHierarchies = new ArrayList<ProviderSkillResultsVO>();
		for(int i=0; i<listOfProvidersFromHierarchies. size(); i++){
			ArrayList listOfProviders = listOfProvidersFromHierarchies.get(i);
			consolListFromHierarchies.addAll(listOfProviders);
		}
		
		for(int i=0; i<consolListFromHierarchies.size(); i++){
			ProviderSkillResultsVO providerSkillResultsVO = consolListFromHierarchies.get(i);
			int providerResourceId = providerSkillResultsVO.getProviderResourceId();
			if(i != consolListFromHierarchies.size() - 1){
				for(int j=i+1; j<consolListFromHierarchies.size(); j++){
					ProviderSkillResultsVO providerSkillResultsVOInnerLoop = consolListFromHierarchies.get(j);
					int providerResourceIdInnerLoop = providerSkillResultsVOInnerLoop.getProviderResourceId();
					if(providerResourceId == providerResourceIdInnerLoop){
						providerSkillResultsVO.setProviderSkillRating(providerSkillResultsVO.getProviderSkillRating()
								+ providerSkillResultsVOInnerLoop.getProviderSkillRating());
					}
				}
			}
			boolean alreadyAdded = false;
			for(int j=0; j<uniqueConsolListFromHierarchies.size(); j++){
				
				ProviderSkillResultsVO providerSkillResultsVOInnerLoop = uniqueConsolListFromHierarchies.get(j);
				if(providerSkillResultsVOInnerLoop.getProviderResourceId() == providerResourceId){
					alreadyAdded = true;
				}
			}
			if(alreadyAdded == false){
				uniqueConsolListFromHierarchies.add(providerSkillResultsVO);
			}
		}

		//consolListFromHierarchies
		return uniqueConsolListFromHierarchies;
	}
}
/*
 * Maintenance History
 * $Log: RatingsCalculator.java,v $
 * Revision 1.11  2008/05/29 21:52:15  glacy
 * Changes for Simple Provider Search. zip
 *
 * Revision 1.10  2008/05/29 02:37:12  glacy
 * Changes for Simple Provider Search.
 *
 * Revision 1.9  2008/05/21 22:54:33  akashya
 * I21 Merged
 *
 * Revision 1.7  2008/05/02 21:23:46  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.6  2008/04/26 00:40:39  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.4.10.1  2008/04/23 11:42:23  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.5  2008/04/23 05:01:47  hravi
 * Reverting to build 247.
 *
 * Revision 1.4.28.3  2008/05/01 19:41:58  gjacks8
 * Greg's change to fix his crap - works though
 *
 * Revision 1.4.28.2  2008/04/29 03:11:28  glacy
 * SpnAutoRouting - ActualGlacy oopsie for no skill service types
 *
 * Revision 1.4.28.1  2008/04/29 02:03:34  glacy
 * SpnAutoRouting - ActualGlacy
 *
 * Revision 1.4  2008/02/21 18:53:54  mhaye05
 * removed System.out.println's
 *
 */