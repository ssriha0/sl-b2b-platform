package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.dto.vo.provider.TeamCredentialsLookupVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.vo.provider.TeamCredentialsVO;

public interface ITeamCredentialsDao {
    public List queryAllType() throws DataServiceException;
    public List queryCatByTypeId(int typeId) throws DataServiceException;
    public List queryCredByResourceId(int resourceId) throws DataServiceException;
    public List queryCredByResourceIdIncludedStates (int resourceId, List<Integer> listIncludedStates)throws DataServiceException;
    public TeamCredentialsVO queryCredById(TeamCredentialsVO teamCredentialsVO) throws DataServiceException;
    public TeamCredentialsVO queryCredByIdSec(TeamCredentialsVO teamCredentialsVO) throws DataServiceException;
    public TeamCredentialsVO insert(TeamCredentialsVO teamCredentialsVO) throws DataServiceException;
    public TeamCredentialsVO delete(TeamCredentialsVO teamCredentialsVO) throws DataServiceException;
    public void update(TeamCredentialsVO teamCredentialsVO) throws DataServiceException;
    public void updateWfStateId(TeamCredentialsVO teamCredentialsVO) throws DataServiceException;
    public TeamCredentialsVO getResourceName(TeamCredentialsVO objLicensesAndCertVO) throws DataServiceException;
	public Boolean isResourceCredentialIdExist(TeamCredentialsVO teamCredentialsVO) throws DataServiceException, DBException;
	
	public void updateCredential(TeamCredentialsVO teamCredentialsVO) throws DataServiceException;
	public Integer queryForCredExists(TeamCredentialsVO teamCredentialsVO) throws DataServiceException;
	public TeamCredentialsLookupVO getTeamCredLookup(String credDesc) throws DataServiceException;
	public List<TeamCredentialsLookupVO> queryCatByType(String typeDesc) throws DataServiceException;

}
