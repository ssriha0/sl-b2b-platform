package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.WorkflowStateVO;

public interface IWorkflowDao  {
    
    public List<WorkflowStateVO> getStateTableMappings () throws DBException;

}//WorkflowDao
