package com.newco.marketplace.persistence.daoImpl.leadsmanagement;

import java.util.Map;

import com.newco.marketplace.persistence.iDao.leadprofile.ILeadDao;



public class LeadManagementDaoFactory {
	
		private Map<Integer, ? extends ILeadDao> leadDAOs;
		
		public ILeadDao getLeadDAO(int daoType) {
			ILeadDao result = null;		
			if(leadDAOs != null) {
				result = leadDAOs.get(new Integer(daoType));
			}
			return result;
		}

		public Map<Integer, ? extends ILeadDao> getLeadDAOs() {
			return leadDAOs;
		}

		public void setLeadDAOs(Map<Integer, ? extends ILeadDao> leadDAOs) {
			this.leadDAOs = leadDAOs;
		}
		
}
