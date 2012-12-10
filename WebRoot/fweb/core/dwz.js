$(document).ready(function(){
	FWebDWZ.loadCSSFile();
	FWebDWZ.loadJavaScriptFile();
	DWZ.init("fweb/dwzres/dwz.frag.xml", {
		//loginUrl:"../loginsub.jsp", loginTitle:"登录",	// 弹出登录对话框
		//loginUrl:"../login",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"fweb/dwzres/themes"});
		}
	});
});

var FWebDWZ={
		loadJavaScriptFile:function(){
			$("head").append("<script src='fweb/dwzres/javascripts/jquery.validate.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/jquery.bgiframe.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.core.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.util.date.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.validate.method.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.regional.zh.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.barDrag.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.drag.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.tree.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.accordion.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.ui.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.theme.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.switchEnv.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.alertMsg.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.contextmenu.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.navTab.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.tab.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.resize.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.dialog.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.dialogDrag.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.sortDrag.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.cssTable.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.stable.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.taskBar.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.ajax.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.pagination.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.database.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.datepicker.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.effects.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.panel.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.checkbox.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.history.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.combox.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.print.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.regional.zh.js' type='text/javascript'></script>");
			$("head").append("<script src='fweb/dwzres/javascripts/dwz.scrollCenter.js' type='text/javascript'></script>");
		},
		loadCSSFile:function(){
			$("head").append("<link href='fweb/dwzres/themes/default/style.css' rel='stylesheet' type='text/css' />");
			$("head").append("<link href='fweb/dwzres/themes/css/core.css' rel='stylesheet' type='text/css' />");
		}
};
