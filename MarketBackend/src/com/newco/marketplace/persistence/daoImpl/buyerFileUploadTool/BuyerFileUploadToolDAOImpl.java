package com.newco.marketplace.persistence.daoImpl.buyerFileUploadTool;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.BuyerRefTypeVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadAuditErrorVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadAuditSuccessVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadFileVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.buyerFileUploadTool.IBuyerFileUploadToolDAO;

public class BuyerFileUploadToolDAOImpl extends SqlMapClientDaoSupport
		implements IBuyerFileUploadToolDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	public int testquery(int test) {
		logger.info("inside test query");
		int test1 = (Integer) getSqlMapClientTemplate().queryForObject(
				"count.primarycategory", test);
		logger.info("inside test query finished" + test1);
		return test1;
	}

	public int generateTestId() {
		int maxTestId = (Integer) getSqlMapClientTemplate().queryForObject(
				"so.selectmaxtestid");
		logger.info("max id is" + maxTestId);
		return maxTestId + 1000;
	}

	public boolean getValidZipCode(String zip) {
		try {
			logger.info("zip is :" + zip + ":");
			Integer zipCount = (Integer) getSqlMapClientTemplate()
					.queryForObject("so.getvalidzip", zip);
			logger.info("zip is " + zipCount);
			if (zipCount != 0) {
				logger.info("zip ids va-lid");
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
	}

	public List getTheSkillTree() {
		try {

			List skillTree = getSqlMapClientTemplate().queryForList(
					"so.gettheskilltree");
			return skillTree;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	public List<UploadFileVO> getFileToCreateSo() {
		logger.info("inside get file conetsntto prosess");
		//UploadAuditDTO uploadAuditDTO=(UploadAuditDTO)getSqlMapClientTemplate().queryForObject("getFileForCreateSo", nameOfFile);
		List<UploadFileVO> fileList = getSqlMapClientTemplate().queryForList("so.getFileForCreateSo");
		return fileList;
	}
	
	public void errorInsert(UploadAuditErrorVO uAEVO) {
		getSqlMapClientTemplate().insert("so.errorInsert", uAEVO);
		int temp = getSqlMapClientTemplate().update("so.error_audit_update", uAEVO);
		logger.debug("Rows Processed : " + temp);
	}
	
	public List getMainCategoryMap() {
		try {
			logger.info("inside main catmap");

			HashMap mainCatMap = new HashMap();

			List mainList = getSqlMapClientTemplate().queryForList(
					"so.getMainCategoryMap");
			return mainList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	public List getSubCatMap() {
		try {

			logger.info("inside sub catmap");
			HashMap skillMap = new HashMap();

			List subCatList = getSqlMapClientTemplate().queryForList(
					"so.getSubCategoryMap");
			return subCatList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	public List getSkillMap() {
		try {

			logger.info("inside skill map");
			HashMap skillMap = new HashMap();

			List skillList = getSqlMapClientTemplate().queryForList(
					"so.getSkillMap");
			return skillList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	public List getAllFileEntries() {
		try {

			List fileNameList = getSqlMapClientTemplate().queryForList(
					"so.getAllEntriesFromAudit");
			logger.info("getAllFileEntries");
			return fileNameList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	public List getShippingInfo() {
		try {

			List shipInfo = getSqlMapClientTemplate().queryForList(
					"so.getShippingInfo");
			logger.info("so.getShippingInfo");
			return shipInfo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	public String getStateForZip(String zip) {
		try {

			String state = (String) getSqlMapClientTemplate().queryForObject(
					"so.getstateforzip", zip);
			logger.info("so.getShippingInfo");
			return state;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	public String getUserEmail(int buyerId) {
		try {

			String email = (String) getSqlMapClientTemplate().queryForObject(
					"so.getUserEmail", buyerId);
			logger.info("so.getShippingInfo");
			return email;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}

	public List<BuyerRefTypeVO> getBuyerReferences(int buyerId) {
		try {
			List<BuyerRefTypeVO> buyerRefList = getSqlMapClientTemplate().queryForList("so.getBuyerReferences", buyerId);
			logger.info("so.getBuyerReferences");
			return buyerRefList;
		} catch (Exception ex) {
			logger.error("Query for getting reference fields failed!", ex);
			return null;
		}
	}

	public List getPartsSuppliedBy() {
		try {

			List partsSupList = getSqlMapClientTemplate().queryForList(
					"so.getPartsSuppliedBy");
			logger.info("so.getPartsSuppliedBy");
			return partsSupList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}

	public List getPhoneTypes() {
		try {

			List phoneTypeList = getSqlMapClientTemplate().queryForList(
					"so.getAllPhoneTypes");
			logger.info("so.getAllPhoneTypes");
			return phoneTypeList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}

	public List getPhoneClass() {
		try {

			List phoneClassList = getSqlMapClientTemplate().queryForList(
					"so.getPhoneClass");
			logger.info("so.getPhoneClass");
			return phoneClassList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}

	public int getBuyerContactId(int buyerId) {
		try {

			int contactId = (Integer) getSqlMapClientTemplate().queryForObject(
					"so.getBuyerContactId", buyerId);
			logger.info("so.getBuyerContactId");
			return contactId;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;

		}

	}

	public List getDocumentLookUp() {
		try {

			List docLookUpList = getSqlMapClientTemplate().queryForList(
					"so.getDocumentLookUp");
			logger.info("so.getDocumentLookUp");
			return docLookUpList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}
	
	public List getDocIdValLookup(int buyerId){
		try {
			List docIdLookUpList = getSqlMapClientTemplate().queryForList(
					"so.docIdVal",buyerId);
			logger.info("so.docIdVal");
			return docIdLookUpList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
		
	}

	public List getLogoIdValLookup(int buyerId){
		try {
			List logoIdLookUpList = getSqlMapClientTemplate().queryForList(
					"so.logoIdVal",buyerId);
			logger.info("so.logoIdVal");
			return logoIdLookUpList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
		
	}

	public List getLocationLookUp() {
		try {

			List docLocatist = getSqlMapClientTemplate().queryForList(
					"so.getLocationLookUp");
			logger.info("so.getLocationLookUp");
			return docLocatist;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}

	public void successInsert(UploadAuditSuccessVO uASVO){
		getSqlMapClientTemplate().insert("so.successUpload", uASVO);
		int temp = getSqlMapClientTemplate().update("so.success_audit_update",uASVO);
		logger.info("Rows Processed : "+temp);
		
	}
	
	public void markFileAsRead(String fileName) {
		try {

			int temp = getSqlMapClientTemplate().update("so.markFileAsRead",
					fileName);
			logger.info("so.getLocationLookUp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;

		}

	}
	
	@SuppressWarnings("unchecked")
	public List<UploadFileVO> getFilesSentToEsb() {
		try {

			List<UploadFileVO> uploadFileList = getSqlMapClientTemplate().queryForList(
					"so.getUploadFilesAtEsb");
			logger.info("so.getUploadFilesAtEsb");
			return uploadFileList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}
	
	public void markAsSentToESB(int fileId) {
		try {

			int temp = getSqlMapClientTemplate().update("so.auditSentToEsb_update",
					fileId);
			logger.info("so.auditSentToEsb_update");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;

		}
	}
	
	public void markAsRetrievedFromESB(int fileId) {
		try {

			int temp = getSqlMapClientTemplate().update("so.auditRetrievedFromEsb_update",
					fileId);
			logger.info("so.auditRetrievedFromEsb_update");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;

		}
	}

	public String insertDocumentDetails(int docId, String soId) {
		try {
			DocumentVO documentVO = new DocumentVO();
			documentVO.setDocumentId(docId);
			//documentVO.setDescription(descr);
			documentVO.setSoId(soId);
			getSqlMapClientTemplate().insert("so.insertDocDetails", documentVO);
			logger.info("so.getLocationLookUp");
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "failure";

		}

	}

/*	public int selectDocId(String title, String descr) {
		try {
			DocumentVO documentVO = new DocumentVO();
			documentVO.setTitle(title);
			documentVO.setDescription(descr);
			//documentVO.setSoId(soId);
			int docId = (Integer) getSqlMapClientTemplate().queryForObject(
					"so.selectDocId", documentVO);
			logger.info("so.selectDocId");
			return docId;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;

		}

	}*/
	
	
	public int selectLogoId(String title, int buyerId) {
		
		int docId = 0;
		
		DocumentVO documentVO = new DocumentVO();
		documentVO.setTitle(title);
		documentVO.setBuyerId(buyerId);
		Integer docIdInteger = (Integer)getSqlMapClientTemplate().queryForObject("so.selectLogoId", documentVO);
		
		if (docIdInteger != null) {
			docId = docIdInteger.intValue();
		}
		
		return docId;
	}
	
	public int selectDocId(String title, int buyerId) {
		
		int docId = 0;
		
		DocumentVO documentVO = new DocumentVO();
		documentVO.setTitle(title);
		documentVO.setBuyerId(buyerId);
		Integer docIdInt = (Integer)getSqlMapClientTemplate().queryForObject("so.selectDocId", documentVO);
		
		if (docIdInt != null) {
			docId = docIdInt.intValue();
		}
		
		return docId;
	}
	
}
