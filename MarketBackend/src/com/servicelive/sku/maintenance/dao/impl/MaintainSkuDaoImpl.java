package com.servicelive.sku.maintenance.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.exception.core.DataServiceException;
import com.servicelive.autoclose.dao.impl.AbstractBaseDao;
import com.servicelive.domain.sku.maintenance.BuyerSkuCategory;
import com.servicelive.sku.maintenance.dao.MaintainSkuDao;
import com.servicelive.domain.sku.maintenance.*;
import com.servicelive.domain.buyer.Buyer;

@Transactional(propagation = Propagation.REQUIRED)
public class MaintainSkuDaoImpl extends AbstractBaseDao implements MaintainSkuDao{
	public List<BuyerSkuCategory> fetchBuyerSkuCategories(int buyerId, int skuCategoryId)
	{
		String hql = "from BuyerSkuCategoryEntity b where b.buyer.buyerId = :buyerId AND b.categoryId != :skuCategoryId  ORDER BY b.categoryName";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
		query.setParameter("skuCategoryId", skuCategoryId);
	
	try {			
		@SuppressWarnings("unchecked")
		List<BuyerSkuCategory> resultDao = query.getResultList();
		return resultDao;
	}
	catch (NoResultException e) {
		logger.error("MaintainSkuDaoImpl.findByBuyerId NoResultException");
		return null;
	}
			
	}
	public List<BuyerSkuCategory> fetchBuyerSkuCategories(int buyerId)
	{
		String hql = "from BuyerSkuCategoryEntity b where b.buyer.buyerId = :buyerId ORDER BY b.categoryName";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
	
	try {			
		@SuppressWarnings("unchecked")
		List<BuyerSkuCategory> resultDao = query.getResultList();
		return resultDao;
	}
	catch (NoResultException e) {
		logger.error("MaintainSkuDaoImpl.findByBuyerId NoResultException");
		return null;
	}		
		
	}	
	public List<BuyerSKUEntity> fetchBuyerSkuNameByCategory(Integer categoryId,Integer currentSkuId, Integer buyerId)
	{
		String hql ="";
		if(currentSkuId == null && buyerId == null){
			hql = "from BuyerSKUEntity b where b.buyerSkuCategory.categoryId = :categoryId ORDER BY b.sku";
		}
		else if(currentSkuId == null && buyerId != null){
			hql = "from BuyerSKUEntity b where b.buyer.buyerId = :buyerId ORDER BY b.sku";
		}
		else if(currentSkuId != null && buyerId !=null){
			hql = "from BuyerSKUEntity b where b.skuId != :currentSkuId AND b.buyer.buyerId = :buyerId ORDER BY b.sku";		
		}
		Query query = getEntityManager().createQuery(hql);

		if(currentSkuId == null && buyerId == null){
			query.setParameter("categoryId", categoryId);
		}	
		if(buyerId != null){
			query.setParameter("buyerId", buyerId);
		}
		if(currentSkuId != null){
			query.setParameter("currentSkuId", currentSkuId);
		}
	
	try {			
		@SuppressWarnings("unchecked")
		List<BuyerSKUEntity> result = query.getResultList();
		return result;
	}
	catch (NoResultException e) {
		logger.error("MaintainSkuDaoImpl.findByBuyerId NoResultException");
		return null;
	}
	}
	
	public List<Map<Integer, String>> fetchMainCategory(){
		String hql = "SELECT new map (s.nodeId as nodeId, s.nodeName as nodeName) from SkillTreeEntity s where s.level = 1 ORDER BY s.nodeName";
		Query query = getEntityManager().createQuery(hql);
		
		try {			
			@SuppressWarnings("unchecked")
			List<Map<Integer, String>> resultList = query.getResultList();
			return resultList;
		}
		catch (NoResultException e) {
			logger.error("MaintainSkuDaoImpl.fetchMainCategory NoResultException");
			return null;
		}	
		
	}
	
	public List<Map<Integer, String>> fetchTemplates(int nodeId, int buyerId){
		String hql = "SELECT new map (s.templateId as templateId, s.templateName as templateName) from BuyerSoTemplateEntity s where s.buyerId = :buyerId AND s.primarySkillCategoryId = :nodeId ORDER BY s.templateName";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
		query.setParameter("nodeId", nodeId);
		
		try {			
			@SuppressWarnings("unchecked")
			List<Map<Integer, String>> resultList = query.getResultList();
			return resultList;
		}
		catch (NoResultException e) {
			logger.error("MaintainSkuDaoImpl.fetchTemplates NoResultException");
			return null;
		}	
		
	}
	
	public List<SkillTree> fetchCategory(int nodeId){
		String hql = "from SkillTreeEntity s where s.parentNode = :nodeId";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("nodeId", nodeId);
		
		try {			
			@SuppressWarnings("unchecked")
			List<SkillTree> resultList = query.getResultList();
			return resultList;
		}
		catch (NoResultException e) {
			logger.error("MaintainSkuDaoImpl.fetchMainCategory NoResultException");
			return null;
		}	
		
	}
	
	public List<LuServiceTypeTemplate> fetchSkills(int nodeId){
		String hql = "from LuServiceTypeTemplateEntity s where s.nodeId = :nodeId";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("nodeId", nodeId);
		
		try {			
			@SuppressWarnings("unchecked")
			List<LuServiceTypeTemplate> resultList = query.getResultList();
			return resultList;
		}
		catch (NoResultException e) {
			logger.error("MaintainSkuDaoImpl.fetchMainCategory NoResultException");
			return null;
		}	
		
	}
	
		public BuyerSKUEntity skuDetailsBySkuIdAndCategoryId(Integer skuId,Integer categoryId)
	{
		String hqlcId = "from BuyerSKUEntity b where b.skuId= :skuId and b.buyerSkuCategory.categoryId=:categoryId";
		Query query = getEntityManager().createQuery(hqlcId);
		query.setParameter("skuId", skuId);
		query.setParameter("categoryId", categoryId);
	try {			
		
		BuyerSKUEntity resultSkuDetail = (BuyerSKUEntity) query.getSingleResult();
		return resultSkuDetail;
	}
	catch (NoResultException e) {
		logger.error("MaintainSkuDaoImpl.findByBuyerId NoResultException");
		return null;
	}
	}
	public BuyerSkuTask fetchSkillTreeBySkuId (Integer skuId)
	{  
		String hql = "from BuyerSkuTaskEntity  bst where bst.buyerSku.skuId= :skuId";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("skuId", skuId);
	try {			
		
		BuyerSkuTask result = (BuyerSkuTask) query.getSingleResult();
		return result;
	}
	catch (NoResultException e) {
		logger.error("MaintainSkuDaoImpl.findByBuyerId NoResultException");
		return null;
	}
	}
	
	public SkillTree fetchSkillTreeBySkuIdByNodeId(Integer nodeId)
	{  
		
		String hqls = "from SkillTreeEntity  where nodeId= :nodeId";
		Query querys = getEntityManager().createQuery(hqls);
		querys.setParameter("nodeId",nodeId);
	try {			
		
		SkillTree result = (SkillTree) querys.getSingleResult();
		return result;
	}
	catch (NoResultException e) {
		logger.error("MaintainSkuDaoImpl.findByBuyerId NoResultException");
		return null;
	}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public BuyerSkuCategory saveBuyerSkuCategory(BuyerSkuCategory buyerSkuCategory) {
		BuyerSkuCategory buyerSkuCategoryUpdated = null;
		try {
			buyerSkuCategoryUpdated = (BuyerSkuCategory) super.update(buyerSkuCategory);

		} catch (Exception e) {
			logger.info("MaintainSkuDaoImpl.saveBuyerSkuCategory "+e);
		}
		return buyerSkuCategoryUpdated; 
	}	
	
	// Method to get buyerSkuCategory for a specific sku category id.
	@Transactional(propagation = Propagation.SUPPORTS)
	public BuyerSkuCategory findBuyerSkuCategoryById(int skuCatId) {
		String hql = "from BuyerSkuCategoryEntity b where b.categoryId = :categoryId";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("categoryId", skuCatId);
		try {			
			@SuppressWarnings("unchecked")
			BuyerSkuCategory skuDetails = (BuyerSkuCategory) query.getSingleResult();
			return  skuDetails;
		}
		catch (NoResultException e) {
			logger.error("MaintainSkuDaoImpl.findBuyerSkuCategoryById NoResultException");
			return null;
		}
}
	
	public BuyerSoTemplate findTemplate(int templateId){

		try {			
			@SuppressWarnings("unchecked")
			BuyerSoTemplate buyerSoTemplate = getEntityManager().find(BuyerSoTemplate.class, templateId);
			return  buyerSoTemplate;
		}
		catch (NoResultException e) {
			logger.error("MaintainSkuDaoImpl.findTemplate NoResultException");
			return null;
		}

	}
	
	public LuServiceTypeTemplate findServiceType(int id){

		int serviceTypeTemplateId = id;
		try {			
			@SuppressWarnings("unchecked")
			LuServiceTypeTemplate luServiceTypeTemplate = getEntityManager().find(LuServiceTypeTemplate.class, serviceTypeTemplateId);
			return  luServiceTypeTemplate;
		}
		catch (NoResultException e) {
			logger.error("MaintainSkuDaoImpl.findTemplate NoResultException");
			return null;
		}

	}
	
	public SkillTree findSkillTree(int nodeId){
		try {			
			@SuppressWarnings("unchecked")
			SkillTree skillTree = getEntityManager().find(SkillTree.class, nodeId);
			return  skillTree;
		}
		catch (NoResultException e) {
			logger.error("MaintainSkuDaoImpl.findTemplate NoResultException");
			return null;
		}
	}
	
	public Buyer findBuyer(int buyerId){
		try {			
			@SuppressWarnings("unchecked")
			Buyer buyer = getEntityManager().find(Buyer.class, buyerId);
			return  buyer;
		}
		catch (NoResultException e) {
			logger.error("MaintainSkuDaoImpl.findBuyer NoResultException");
			return null;
		}
	}
	
	public BuyerSkuCategory findBuyerSkuCategory(int catgoryId){
		try {			
			@SuppressWarnings("unchecked")
			BuyerSkuCategory buyerSkuCategory = getEntityManager().find(BuyerSkuCategory.class, catgoryId);
			return  buyerSkuCategory;
		}
		catch (NoResultException e) {
			logger.error("MaintainSkuDaoImpl.findBuyerSkuCAtegory NoResultException");
			return null;
		}
	}
	
	public BuyerSKUEntity findBuyerSku(){
		
			String hql = "SELECT * FROM buyer_sku b ORDER BY b.created_date DESC LIMIT 1";
			Query query = getEntityManager().createNativeQuery(hql,BuyerSKUEntity.class);
		try {			
			
			BuyerSKUEntity result = (BuyerSKUEntity) query.getSingleResult();
			return result;
		}
		catch (NoResultException e) {
			logger.error("MaintainSkuDaoImpl.findBuyerSku NoResultException");
			return null;
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void addSkuCategory(Object object) {

		try {
		 	super.update(object);

		} catch (Exception e) {
			logger.info("MaintainSkuDaoImpl.saveBuyerSkuCategory "+e);
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void saveHistory(Object object) {

		try {
		 	super.save(object);

		} catch (Exception e) {
			logger.info("MaintainSkuDaoImpl.saveBuyerSkuCategory "+e);
		}
	}
	
@Transactional(propagation = Propagation.SUPPORTS) 
	public void deleteSku(List<Integer> skuList, String categoryName, String skuCategoryId){
		try {
			
			String deleteSkuhql = "";
			deleteSkuhql = "DELETE FROM BuyerSKUEntity bsk WHERE bsk.skuId IN (:skuIds) and bsk.buyerSkuCategory.categoryId =:skuCategoryId";
       	 	Query query = getEntityManager().createQuery(deleteSkuhql);
       	 	query.setParameter("skuIds", skuList);
       	    query.setParameter("skuCategoryId", Integer.parseInt(skuCategoryId));
       	    query.executeUpdate();	
		  
       	    String deleteCategoryhql="DELETE from BuyerSkuCategoryEntity b where b.categoryId=:skuCategoryId";
			int deleteCategoryFlag = checkForDeleteSkuCategory(skuCategoryId);
			if(deleteCategoryFlag == 0){
				Query deleteCategoryQuery = getEntityManager().createQuery(deleteCategoryhql);
				deleteCategoryQuery.setParameter("skuCategoryId", Integer.parseInt(skuCategoryId));
				deleteCategoryQuery.executeUpdate();
			}
	
		}catch(Exception e) {
			logger.debug("Error in deleteSku()");
		}catch(Throwable th) {
			logger.debug("Error in deleting sku category and sku in deleteSku()");
		}
	}
@SuppressWarnings("unchecked")
	public List<Integer> checkForDeleteSku(List<String> skuIds, String skuCategoryId) {
		List<Integer> resultList=null;
		
		//Code commented to avoid SQL Injection
		/*StringBuffer skuIdList = new StringBuffer("(");
		if(!skuIds.isEmpty()){
			for(String sku:skuIds) {
				skuIdList.append("'");
				skuIdList.append(sku+"'"+",");
			}
			skuIdList.replace(skuIdList.length()-1,skuIdList.length(),")");
		}
		String inactiveSkuSql="SELECT a.c FROM " +
				                      "(SELECT DISTINCT bs.sku_id AS c " +
				                           "FROM buyer_sku bs WHERE sku_id IN "+skuIdList.toString()+" AND sku_category="+skuCategoryId+") a " +
				                      "LEFT OUTER JOIN " +
				                      "(SELECT  DISTINCT st.sku_id AS d FROM so_hdr so " +
			                           "INNER JOIN so_tasks st ON st.so_id=so.so_id " +
			                           "WHERE st.sku_id IN"+skuIdList.toString()+" AND so.wf_state_id NOT IN (105,180,120,125) GROUP BY st.sku_id) b " +
				                      "ON a.c = b.d WHERE  b.d IS  NULL ;";*/
		
		//Code modified to avoid SQL Injection
				String inactiveSkuSql="SELECT a.c FROM " +
		                "(SELECT DISTINCT bs.sku_id AS c " +
		                     "FROM buyer_sku bs WHERE sku_id IN (:skuidlist) AND sku_category= :skucategoryid ) a " +
		                "LEFT OUTER JOIN " +
		                "(SELECT  DISTINCT st.sku_id AS d FROM so_hdr so " +
		                 "INNER JOIN so_tasks st ON st.so_id=so.so_id " +
		                 "WHERE st.sku_id IN (:skuidlist) AND so.wf_state_id NOT IN (105,180,120,125) GROUP BY st.sku_id) b " +
		                "ON a.c = b.d WHERE  b.d IS  NULL ;";
				Query inActiveListQuery = getEntityManager().createNativeQuery(inactiveSkuSql);
		inActiveListQuery.setParameter("skuidlist", skuIds);
		inActiveListQuery.setParameter("skucategoryid", skuCategoryId);
		inActiveListQuery.setParameter("skuidlist", skuIds);
		try {
	
			resultList=inActiveListQuery.getResultList();
		} 
		catch (NoResultException nre) {
			logger.error("MaintainSkuDaoImpl.checkForDeleteSku NoResultException");
		}
		return resultList;
	}
	
	public int checkForDeleteSkuCategory(String skuCategoryId) {
		
		String findSoIdhql = "SELECT COUNT(bs.skuId) FROM BuyerSKUEntity bs WHERE bs.buyerSkuCategory.categoryId = :skuCategoryId";
		int countOfSku=0;
		Query query = getEntityManager().createQuery(findSoIdhql);
		query.setParameter("skuCategoryId", Integer.parseInt(skuCategoryId));
		countOfSku =Integer.parseInt( query.getSingleResult().toString());
		return countOfSku;
	}
	
	//method for fetching buyerSkuCategoryHistory
	public List<SkuCategoryHistoy> fetchSkuCategoryHistory(Integer categoryId){
		
		String hql = "from SkuCategoryHistoy sch where sch.buyerSkuCategory.categoryId = :categoryId";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("categoryId", categoryId);
	
	try {			
		@SuppressWarnings("unchecked")
		List<SkuCategoryHistoy> result = query.getResultList();
		return result;
	}
	catch (NoResultException e) {
		logger.error("MaintainSkuDaoImpl.fetchSkuHistory NoResultException");
		return null;
	}
		
	}
	
	//method for fetching buyerSkuHistory
	public List<SkuHistory> fetchSkuHistory(Integer skuId){
		
		String hql = "from SkuHistory sh where sh.buyerSku.skuId = :skuId";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("skuId", skuId);
	
	try {			
		@SuppressWarnings("unchecked")
		List<SkuHistory> result = query.getResultList();
		return result;
	}
	catch (NoResultException e) {
		logger.error("MaintainSkuDaoImpl.fetchSkuHistory NoResultException");
		return null;
	}
		
	}
	public BuyerSkuCategory validateBuyerSKUCategory(Integer buyerId,Integer skuCategoryId, String updatedSkuName) {
		
		String hql = "from BuyerSkuCategoryEntity b where b.buyer.buyerId = :buyerId AND b.categoryId != :skuCategoryId  AND b.categoryName = :updatedSkuName";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
		query.setParameter("skuCategoryId", skuCategoryId);
		query.setParameter("updatedSkuName", updatedSkuName);
		
		try {			
			@SuppressWarnings("unchecked")
			BuyerSkuCategory buyerSkuCat  =  (BuyerSkuCategory)query.getSingleResult();
			return buyerSkuCat;
		}
		catch (NoResultException e) {
			logger.error("MaintainSkuDaoImpl.findByBuyerId NoResultException");
			return null;
		}			
	}
	public boolean isNonFundedBuyer(Integer buyerId) {
		boolean isNonFunded = false;
		String isFeaturePresent = "";
		try{
			String hql="SELECT CAST(feature AS CHAR(100)) FROM buyer_feature_set WHERE buyer_id = :buyerId AND feature = :feature AND active_ind=1";
			Query query = getEntityManager().createNativeQuery(hql);
			query.setParameter("buyerId", buyerId);
			query.setParameter("feature", "NON_FUNDED");
			isFeaturePresent = (String) query.getSingleResult();
			if(StringUtils.isNotBlank(isFeaturePresent)){
				isNonFunded = true;
			}
		}catch (NoResultException nre) {
			logger.error("Exception in validating isNonFunded Buyer "+ nre);
		}
		return isNonFunded;
	}

}
