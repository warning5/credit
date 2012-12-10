/**
 * 
 */
package com.bluecloud.persistence.service;

import java.util.List;

import com.bluecloud.persistence.exception.PersistenceServiceException;
import com.bluecloud.persistence.pojo.Relation;

/**
 * @author Hanlu
 *
 */
public interface AllocationService {

	/**
	 * 
	 * @param relations
	 */
	public void allocate(List<Relation> relations) throws PersistenceServiceException;
}
