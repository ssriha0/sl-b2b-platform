/**
 * 
 */
package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupPerformanceLevel;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author hoza
 *
 */
public interface LookupPerformaceLevelDao extends BaseDao {
	public List<LookupPerformanceLevel> getAllPerformancelevels() throws Exception;
}
