/**
 * 
 */
package com.bluecloud.mvc.external.widgets;

import java.util.List;

import com.bluecloud.mvc.external.beans.FunctionTreeBean;


/**
 * @author Hanlu
 *
 */
public interface TreeWidget extends FragmentWidget {

	/**
	 * 删除父树中的子树数据
	 * @param parent 父树
	 * @param child 子树
	 */
	void reload(List<? extends FunctionTreeBean> parent, List<? extends FunctionTreeBean> child);

}
