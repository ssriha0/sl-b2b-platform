package com.newco.marketplace.dto.vo.providerSearch;

import java.util.List;

import com.newco.marketplace.vo.PaginationVO;
import com.sears.os.vo.SerializableBaseVO;

public class ETMProviderSearchResults extends SerializableBaseVO{
	
	private List<ETMProviderRecord> searchResults = null;
	private PaginationVO paginationVo;
	private String searchQueryKey = null;
	private String username;
	private String zipCd;
	
	public String getSearchQueryKey() {
		return searchQueryKey;
	}
	public void setSearchQueryKey(String searchQueryKey) {
		this.searchQueryKey = searchQueryKey;
	}
	public List<ETMProviderRecord> getSearchResults() {
		return searchResults;
	}
	public void setSearchResults(List<ETMProviderRecord> searchResults) {
		this.searchResults = searchResults;
	}
	public PaginationVO getPaginationVo() {
		return paginationVo;
	}
	public void setPaginationVo(PaginationVO paginationVo) {
		this.paginationVo = paginationVo;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the zipCd
	 */
	public String getZipCd() {
		return zipCd;
	}
	/**
	 * @param zipCd the zipCd to set
	 */
	public void setZipCd(String zipCd) {
		this.zipCd = zipCd;
	}
	
}
