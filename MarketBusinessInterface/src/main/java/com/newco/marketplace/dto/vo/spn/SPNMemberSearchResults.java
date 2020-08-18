package com.newco.marketplace.dto.vo.spn;

import java.util.List;

import com.newco.marketplace.vo.PaginationVO;
import com.sears.os.vo.SerializableBaseVO;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */

public class SPNMemberSearchResults extends SerializableBaseVO {
	private static final long serialVersionUID = -8040573131782165830L;
	private List<SPNMemberSearchResultVO> searchResults;
	private PaginationVO paginationVo;
	/**
	 * @return the searchResults
	 */
	public List<SPNMemberSearchResultVO> getSearchResults() {
		return searchResults;
	}
	/**
	 * @param searchResults the searchResults to set
	 */
	public void setSearchResults(List<SPNMemberSearchResultVO> searchResults) {
		this.searchResults = searchResults;
	}
	/**
	 * @return the paginationVo
	 */
	public PaginationVO getPaginationVo() {
		return paginationVo;
	}
	/**
	 * @param paginationVo the paginationVo to set
	 */
	public void setPaginationVo(PaginationVO paginationVo) {
		this.paginationVo = paginationVo;
	}
	
	
}
