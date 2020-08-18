/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IResourceScheduleDao;
import com.newco.marketplace.vo.provider.GeneralInfoVO;

/**
 * @author KSudhanshu
 *
 */
public class ResourceScheduleDaoImpl  extends SqlMapClientDaoSupport implements IResourceScheduleDao {

	 private static final Logger localLogger = Logger.getLogger(ResourceScheduleDaoImpl.class);
	 
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IResourceScheduleDao#get(com.newco.marketplace.vo.provider.GeneralInfoVO)
	 */
	public GeneralInfoVO get(GeneralInfoVO generalInfoVO) throws DBException {
		try {
			generalInfoVO = (GeneralInfoVO) getSqlMapClient().queryForObject("generalInfo.resourceSchedule.get", generalInfoVO);
        }
        catch (SQLException ex) {
             localLogger.info("SQL Exception @ResourceScheduleDaoImpl.get() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @ResourceScheduleDaoImpl.get() due to "+ex.getMessage());
        }catch (Exception ex) {
             localLogger.info("General Exception @ResourceScheduleDaoImpl.get() due to"+ex.getMessage());
		     throw new DBException("General Exception @ResourceScheduleDaoImpl.get() due to "+ex.getMessage());
        }

        return generalInfoVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IResourceScheduleDao#insert(com.newco.marketplace.vo.provider.GeneralInfoVO)
	 */
	public GeneralInfoVO insert(GeneralInfoVO generalInfoVO) throws DBException {
		Integer id = null;

        try {
            id = (Integer)getSqlMapClient().insert("generalInfo.resourceSchedule.insert", generalInfoVO);
            generalInfoVO.setContactId(id.intValue());
        }
        catch (SQLException ex) {
             localLogger.info("SQL Exception @ResourceScheduleDaoImpl.insert() due to"+ex.getMessage());
		     throw new DBException("SQL Exception @ResourceScheduleDaoImpl.insert() due to "+ex.getMessage());
        }catch (Exception ex) {
             localLogger.info("General Exception @ResourceScheduleDaoImpl.insert() due to"+ex.getMessage());
		     throw new DBException("General Exception @ResourceScheduleDaoImpl.insert() due to "+ex.getMessage());
        }

        return generalInfoVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IResourceScheduleDao#update(com.newco.marketplace.vo.provider.GeneralInfoVO)
	 */
	public int update(GeneralInfoVO generalInfoVO) throws DBException {
		 int result = 0;

	        try {
	            result = getSqlMapClient().update("generalInfo.resourceSchedule.update", generalInfoVO);

	        } catch (SQLException ex) {
				localLogger.info("SQL Exception @ResourceScheduleDaoImpl.update() due to"
						+ ex.getMessage());
				throw new DBException(
						"SQL Exception @ResourceScheduleDaoImpl.update() due to "
								+ ex.getMessage());
			} catch (Exception ex) {
				localLogger
						.info("General Exception @ResourceScheduleDaoImpl.update() due to"
								+ ex.getMessage());
				throw new DBException(
						"General Exception @ResourceScheduleDaoImpl.update() due to "
								+ ex.getMessage());
			}
		return result;
	}
	
	
	public int updateResourceSchedule(GeneralInfoVO generalInfoVO) throws DBException {
		 int result = 0;

	        try {
	            result = getSqlMapClient().update("resourceSchedule.update", generalInfoVO);

	        } catch (SQLException ex) {
				localLogger.info("SQL Exception @ResourceScheduleDaoImpl.updateResourceSchedule() due to"
						+ ex.getMessage());
				throw new DBException(
						"SQL Exception @ResourceScheduleDaoImpl.updateResourceSchedule() due to "
								+ ex.getMessage());
			} catch (Exception ex) {
				localLogger
						.info("General Exception @ResourceScheduleDaoImpl.updateResourceSchedule() due to"
								+ ex.getMessage());
				throw new DBException(
						"General Exception @ResourceScheduleDaoImpl.updateResourceSchedule() due to "
								+ ex.getMessage());
			}
		return result;
	}

	public void deleteResourceSchdedule(GeneralInfoVO generalInfoVO) throws DBException{
		 
		try {
			getSqlMapClient().delete("resourceSchedule.delete", generalInfoVO);
		} catch (SQLException ex) {
			logger.error("SQL Exception @ResourceScheduleDaoImpl.deleteResourceSchdedule() due to"
					+ ex.getMessage());
			throw new DBException(
					"SQL Exception @ResourceScheduleDaoImpl.deleteResourceSchdedule() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			logger.error("General Exception @ResourceScheduleDaoImpl.deleteResourceSchdedule() due to"
					+ ex.getMessage());
			throw new DBException(
					"General Exception @ResourceScheduleDaoImpl.deleteOldSkillsNodes() due to "
							+ ex.getMessage());
		}
		 
	}

	public List<GeneralInfoVO> fetchProviderScheduleByProviderList(List<String> providerList) throws DBException {
		Map<String, Object> paramData = new HashMap<String, Object>();
		paramData.put("providerList", providerList);
		List<GeneralInfoVO> providerCapacityList = null;
		try {
			providerCapacityList = ((List<GeneralInfoVO>) getSqlMapClient().queryForList("generalInfo.resourceSchedule.get.list", paramData));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return providerCapacityList;
	}

}
