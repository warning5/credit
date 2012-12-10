/**
 * 
 */
package com.bluecloud.persistence.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bluecloud.persistence.exception.PersistenceServiceException;
import com.bluecloud.persistence.pojo.Relation;

/**
 * @author Hanlu
 *
 */
public class RelationService {

	private Log log=LogFactory.getLog(RelationService.class);
	/**
	 * 
	 */
	public RelationService() {
	}

	/**
	 * 
	 * @param relationFKs
	 * @param relationPK
	 * @param allocationService 
	 * @return
	 * @throws PersistenceServiceException 
	 */
	protected boolean allocate(List<String> relationFKs, String relationPK, AllocationService allocationService) throws PersistenceServiceException {
		if(relationFKs==null||relationFKs.isEmpty()){
			return false;
		}
		if(relationPK==null||relationPK.trim().equals("")){
			return false;
		}
		List<Relation> relations=new ArrayList<Relation>(relationFKs.size());
		for(String relationFK:relationFKs){
			Relation relation=new Relation(relationPK,relationFK);
			relations.add(relation);
		}
		try{
			allocationService.allocate(relations);
		}catch (Exception e) {
			log.equals(e);
			throw new PersistenceServiceException(e.getMessage());
		}
		return true;
	}
	
	/**
	 * 
	 * @param relationFKs
	 * @param relationPK
	 * @param disallocationService
	 * @return
	 * @throws PersistenceServiceException 
	 */
	protected boolean disallocate(List<String> relationFKs, String relationPK,
			DisallocationService disallocationService) throws PersistenceServiceException {
		if(relationFKs==null||relationFKs.isEmpty()){
			return false;
		}
		if(relationPK==null||relationPK.trim().equals("")){
			return false;
		}
		List<Relation> relations=new ArrayList<Relation>(relationFKs.size());
		for(String groupid:relationFKs){
			Relation relation=new Relation(relationPK,groupid);
			relations.add(relation);
		}
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("relationPK", relationPK);
		params.put("relationFKs", relationFKs);
		try{
			disallocationService.disallocate(params);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new PersistenceServiceException(e.getMessage());
		}
		return true;
	}
}
