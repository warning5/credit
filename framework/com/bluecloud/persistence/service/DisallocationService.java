package com.bluecloud.persistence.service;

import java.util.Map;

import com.bluecloud.persistence.exception.PersistenceServiceException;

public interface DisallocationService {

	/**
	 * 
	 * @param params
	 */
	public void disallocate(Map<String, Object> params) throws PersistenceServiceException;
}
