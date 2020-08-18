package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IWorkflowDao;
import com.newco.marketplace.vo.provider.AuditVO;
import com.newco.marketplace.vo.provider.WorkflowStateVO;

/**
 * @author KSudhanshu
 */
public class WorkflowDaoImpl extends SqlMapClientDaoSupport implements IWorkflowDao {

    
    public List<WorkflowStateVO> getStateTableMappings () throws DBException{
        List<WorkflowStateVO> wfList = new ArrayList<WorkflowStateVO>();
        List<WorkflowStateVO> list = null;
        try {
            list =  getSqlMapClient().queryForList("pworkflow.statesTableMap", new AuditVO());
            for (Object o: list) {
                wfList.add((WorkflowStateVO) o);
             }
        } catch (SQLException e) {

            throw new DBException("[WorkflowDaoImpl] - getStateTableMappings", e);
        }
        return (wfList);
    }//getStateTableMappings

}
