package com.newco.marketplace.business.iBusiness.iso;

public interface IWorkerMapper {

	public String findNextAvailableQueue(Long pan) throws Exception;
}
