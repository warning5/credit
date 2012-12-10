/**
 * 
 */
package com.bluecloud.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.bluecloud.persistence.pojo.BaseBO;

/**
 * @author Hanlu
 * 
 */
public interface BaseDAO {

	/**
	 * 添加
	 * 
	 * @param bo
	 */
	public void add(BaseBO bo) throws Exception;

	/**
	 * 批量添加
	 * @param relations
	 */
	public void batchAdd(List<? extends BaseBO> relations)throws Exception;
	
	/**
	 * 修改
	 * 
	 * @param bo
	 */
	public void update(BaseBO bo)throws Exception;

	/**
	 * 批量修改
	 * 
	 * @param bo
	 */
	public void batchUpdate(Map<String,Object> params)throws Exception;
	
	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void delete(List<String> ids)throws Exception;

	/**
	 * 根据id获取信息
	 * @param <T>
	 * 
	 * @param id
	 * @return
	 */
	public <T> T getByID(String id)throws Exception;

	/**
	 * 获取全部信息
	 * @param <T>
	 * 
	 * @return
	 */
	public <T> List<T> list()throws Exception;

	/**
	 * 分页获取全部信息
	 * @param <T>
	 * 
	 * @return
	 */
	public <T> List<T> listLimit(RowBounds rows)throws Exception;
	
	/**
	 * 查询
	 * @param <T>
	 * 
	 * @param bo
	 * @return
	 */
	public <T> List<T> search(BaseBO bo)throws Exception;
	
	/**
	 * 分页查询
	 * @param <T>
	 * 
	 * @param bo
	 * @return
	 */
	public <T> List<T> searchLimit(BaseBO bo,RowBounds rows)throws Exception;
	
	/**
	 * 获取数据总数
	 * @return
	 */
	public int allCount(BaseBO bo)throws Exception;
}
