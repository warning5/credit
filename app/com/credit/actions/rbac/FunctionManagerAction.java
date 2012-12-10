/**
 * 
 */
package com.credit.actions.rbac;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bluecloud.mvc.exception.FragmentEventException;
import com.bluecloud.mvc.exception.HtmlFragmentException;
import com.bluecloud.mvc.external.FragmentAction;
import com.bluecloud.mvc.external.FragmentBeanRegister;
import com.bluecloud.mvc.external.widgets.FormWidget;
import com.bluecloud.mvc.external.widgets.MessageWidget;
import com.bluecloud.mvc.external.widgets.SelectWidget;
import com.bluecloud.mvc.external.widgets.TreeWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultFormWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultMessageWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultSelectWidget;
import com.bluecloud.mvc.external.widgets.impl.FunctionTreeWidget;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;
import com.credit.actions.beans.rbac.FunctionBean;
import com.credit.actions.beans.rbac.OperationBean;
import com.credit.rbac.bo.Function;
import com.credit.rbac.bo.Operation;
import com.credit.rbac.exception.RBACServiceException;
import com.credit.rbac.service.FunctionService;

/**
 * @author Hanlu
 *
 */
@Controller("funcManager")
public class FunctionManagerAction extends FragmentAction {
	private Log log =LogFactory.getLog(getClass());
	@Autowired
	private FunctionService funcService;
	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.Action#execute(com.bluecloud.mvc.web.http.HtmlFragmentRequest)
	 */
	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request)
			throws HtmlFragmentException {
		HtmlFragmentResponse res =getResponse();
		try {
			List<Function> funcs=funcService.getFunctions();
			if (funcs != null) {
				List<FunctionBean> beans = new ArrayList<FunctionBean>(funcs.size());
				for (Function function : funcs) {
					FunctionBean bean = new FunctionBean();
					bean.setFunction(function);
					beans.add(bean);
				}
				TreeWidget treeMenus = new FunctionTreeWidget("ftree", beans);
				res.addTree(treeMenus);
				res.forward("pages/rbac/function/funcManager.html");
			}
		} catch (RBACServiceException e) {
			log.error(e.getMessage(), e);;
			throw new HtmlFragmentException(e);
		}
		return res;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse showEdit(HtmlFragmentRequest request)
			throws FragmentEventException {
		HtmlFragmentResponse res =getResponse();
		try {
			FunctionBean bean=(FunctionBean) request.getForm("function");
			if(bean!=null){
				Function func=bean.getFunction();
				if(func!=null){
					String id=func.getId();
					func=funcService.getFunction(id);
				}
				if(func==null){
					
				}else{
					bean.setFunction(func);
					String pID=func.getParent();
					if(pID!=null){
						Function parent = funcService.getFunction(pID);
						bean.setParentName(parent.getName());
					}else{
						bean.setParentName("功能树");
					}
					FormWidget form=new DefaultFormWidget("function", bean);
					res.addForm(form);
				}
			}
			this.loadOperation(res);
			res.forward("pages/rbac/function/editFunction.html");
		} catch (Exception e) {
			log.error(e.getMessage(), e);;
			throw new FragmentEventException(e);
		}
		return res;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse showAdd(HtmlFragmentRequest request)
			throws FragmentEventException {
		HtmlFragmentResponse res =getResponse();
		try {
			FunctionBean bean=(FunctionBean) request.getForm("function");
			if(bean!=null){
				Function func=bean.getFunction();
				if(func!=null){
					String id=func.getId();
					func=funcService.getFunction(id);
				}
				if(func==null){
					Function newFunc=new Function();
					bean.setFunction(newFunc);
					bean.setParentName("功能树");
				}else{
					Function newFunc=new Function();
					newFunc.setParent(func.getId());
					bean.setParentName(func.getName());
					bean.setFunction(newFunc);
				}
				FormWidget form=new DefaultFormWidget("function", bean);
				res.addForm(form);
			}
			this.loadOperation(res);
			res.forward("pages/rbac/function/addFunction.html");
		} catch (Exception e) {
			log.error(e.getMessage(), e);;
			throw new FragmentEventException(e);
		}
		return res;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse addFunc(HtmlFragmentRequest request)
			throws FragmentEventException {
		HtmlFragmentResponse res =getResponse();
		try {
			FunctionBean bean=(FunctionBean) request.getForm("function");
			DefaultMessageWidget message = null;
			if(bean!=null){
				Function function=bean.getFunction();
				boolean isSuccess=funcService.saveFunction(function);
				if (isSuccess) {
					message = new DefaultMessageWidget("添加功能成功");
					message.setNavTabId(getName());
				} else {
					message = new DefaultMessageWidget( "添加功能失败");
					message.fail();
				}
			}else{
				message = new DefaultMessageWidget( "添加功能失败：请求错误");
				message.fail();
			}
			res.addMessage(message);
		} catch (Exception e) {
			log.error(e.getMessage(), e);;
			throw new FragmentEventException(e);
		}
		return res;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse editFunc(HtmlFragmentRequest request)
			throws FragmentEventException {
		HtmlFragmentResponse res =getResponse();
		try {
			FunctionBean bean=(FunctionBean) request.getForm("function");
			DefaultMessageWidget message;
			if(bean!=null){
				Function function=bean.getFunction();
				boolean isSuccess=funcService.updateFunction(function);
				if (isSuccess) {
					message = new DefaultMessageWidget("修改功能成功");
					message.setNavTabId(getName());
				} else {
					message = new DefaultMessageWidget( "修改功能失败");
					message.fail();
				}
			}else{
				message = new DefaultMessageWidget( "修改功能失败：请求错误");
				message.fail();
			}
			res.addMessage(message);
		} catch (Exception e) {
			log.error(e.getMessage(), e);;
			throw new FragmentEventException(e);
		}
		return res;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse deleteFunc(HtmlFragmentRequest request)
			throws FragmentEventException {
		HtmlFragmentResponse res =getResponse();
		try {
			MessageWidget message = null;
//			List<String> ids=request.getIDS();
//			if(ids==null){
				FunctionBean bean=(FunctionBean) request.getForm("function");
				if(bean!=null&&bean.getFunction()!=null){
					String id=bean.getFunction().getId();
					List<Function> funcs=funcService.getChildren(id);
					if(funcs==null||funcs.isEmpty()){
						List<String> ids=new ArrayList<String>();
						ids.add(bean.getFunction().getId());
						boolean isSuccess=funcService.deleteFunctions(ids);
						if (isSuccess) {
							message = new DefaultMessageWidget("删除功能成功");
							message.setNavTabId(getName());
						} else {
							message = new DefaultMessageWidget( "删除功能失败");
							message.fail();
						}
					}else{
						message = new DefaultMessageWidget( "删除功能失败,请先删除子功能");
						message.fail();
					}
				}
//			}
			res.addMessage(message);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		return res;
	}
	
	/**
	 * 
	 * @param res
	 * @throws RBACServiceException
	 */
	private void loadOperation(HtmlFragmentResponse res) throws RBACServiceException{
		List<Operation> operations=funcService.getOpearations();
		List<OperationBean> beans = new ArrayList<OperationBean>(operations.size());
		for(Operation operation:operations){
			OperationBean o=new OperationBean();
			o.setOperation(operation);
			beans.add(o);
		}
		SelectWidget select=new DefaultSelectWidget("function_operation_code",beans);
		res.addSelect(select);
	}
	
	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.FragmentAction#regBean()
	 */
	@Override
	protected FragmentBeanRegister regBean() {
		FragmentBeanRegister reg=new FragmentBeanRegister();
		reg.add("function", FunctionBean.class);
		return reg;
	}

	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.FragmentAction#getName()
	 */
	@Override
	public String getName() {
		return "funcManager";
	}

}
