package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import com.servicelive.domain.lookup.LookupSPNMeetAndGreetState;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupSPNMeetAndGreetStateDao;

/**
 * 
 * @author svanloon
 *
 */
public class LookupSPNMeetAndGreetStateDaoImpl extends AbstractBaseDao implements LookupSPNMeetAndGreetStateDao {
	@SuppressWarnings("unchecked")
	public List <LookupSPNMeetAndGreetState> findAll ( int... rowStartIdxAndCount) throws Exception {
		return ( List <LookupSPNMeetAndGreetState> )super.findAll("LookupSPNMeetAndGreetState", rowStartIdxAndCount);
	}

	public LookupSPNMeetAndGreetState findById(String id) throws Exception {
		return ( LookupSPNMeetAndGreetState) super.findById(LookupSPNMeetAndGreetState.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<LookupSPNMeetAndGreetState> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception {
		return ( List<LookupSPNMeetAndGreetState>) super.findByProperty("LookupSPNMeetAndGreetState",propertyName,value, rowStartIdxAndCount);
	}
}
