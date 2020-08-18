/*
 * Created on May 2, 2007
 */
package com.newco.marketplace.business.businessImpl.skillTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.newco.marketplace.dto.vo.skillTree.SearchExpressionVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.persistence.iDao.skillTree.MarketplaceSearchDAO;

/**
 * @author zizrale
 */
public class MarketplaceSearchBean {

	private MarketplaceSearchDAO marketPlaceDao;
	 
	/**
	 * @param aNode 
	 * @return SkillNode[] returns all the children of a given node
	 */
	public SkillNodeVO[] popDownSkillTree(SkillNodeVO aNode){
		//returning all children for this node.
		List childrenNodes = marketPlaceDao.getChildrenForNode(aNode);
		return (SkillNodeVO[])childrenNodes.toArray();
	}
	 
	/**
	 * @param aNode
	 * @return parent of a given node
	 */
	public SkillNodeVO popupSkillTree(SkillNodeVO aNode){
		SkillNodeVO parent = marketPlaceDao.getParentForNode(aNode);
		return parent;
	}
	
	/**
	 * @param expression
	 * @return SearchResultContainer - an ArrayList of SearchResult objects
	 * Each SearchResult object is a branch of a tree in which the search expression was found
	 */
	public SearchResultContainer keywordSearchTree(String expression){
		SearchExpressionVO expr = new SearchExpressionVO();
		expr.setSearchExpression(expression);
		List matchingNodes = marketPlaceDao.searchNodeByExpression(expr);
		SearchResultContainer container = new SearchResultContainer();
		SearchResult result = new SearchResult();
		for (int i=0; i < matchingNodes.size(); i++){
			SkillNodeVO node = (SkillNodeVO)matchingNodes.get(i);
			
			ArrayList<SkillNodeVO> fullNodeList = new ArrayList<SkillNodeVO>();
			climbUpTheTree(node, fullNodeList);
			Collections.reverse(fullNodeList);
			
			result.setTreeBranch(fullNodeList);
			
			container.add(result);
		}
		return container;
	}
	
	/**
	 * @param nodeId
	 * @return ArrayList of SkillNodeVO
	 */
	public ArrayList<SkillNodeVO> getParentSkillStructById(int nodeId){
		
		SkillNodeVO node = new SkillNodeVO();
		node.setNodeId(nodeId);
			
		ArrayList<SkillNodeVO> fullNodeList = new ArrayList<SkillNodeVO>();
		node = getCompleteNode(node);
		climbUpTheTree(node, fullNodeList);
		//Collections.reverse(fullNodeList);
		
		return fullNodeList;
	}
//	public AJob[] keywordSearchJob(KeywordSearchExpression expression){};
//	public ASupplier[] geoSearch(GeoSearchElement geoSearchElement){};
//	public ASupplier[] atNode(SkillNode aNode){};
//	public ASupplier[] belowNode(SkillNode aNode){};
	
	private void climbUpTheTree(SkillNodeVO node, ArrayList<SkillNodeVO> treeBranch){
		treeBranch.add(node);
		SkillNodeVO parent = popupSkillTree(node);
		if (parent != null){
			climbUpTheTree(parent, treeBranch);
		}
	}
		
	private void walk(SkillNodeVO node) {
		
		List children = marketPlaceDao.getChildrenForNode(node);
		
		if (children == null || children.size() == 0)
			return;
		
		for (int i = 0; i < children.size(); i++) {
			SkillNodeVO childNode = (SkillNodeVO)children.get(i);
			walk(childNode);
		}
	}

	public void setMarketPlaceDao(MarketplaceSearchDAO marketPlaceDao) {
		this.marketPlaceDao = marketPlaceDao;
	}	
	
	private SkillNodeVO getCompleteNode(SkillNodeVO node){
		node = marketPlaceDao.getNodeNameById(node);
		return node;
	}
}
