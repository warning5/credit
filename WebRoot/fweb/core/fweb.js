var FWeb={
		JSESSIONID:"",
		attr:{
			FWebSubmitForm:"FWebSubmit=form",
			FWebSubmitAjax:"FWebSubmit=ajax",
			FWebAjaxAsync:"FWebAjaxAsync",
			//
			FWebTable:"FWebTable",
			FWebTableData:"FWebTableData",
			FWebTableDataInfo:"FWebTable=datainfo",
			FWebTableHead:"FWebTable=head",
			FWebTableRow:"FWebTable=row",
			FWebTableIDS:"FWebTable=ids",
			FWebTableNum:"FWebTable=number",
			FWebTableForm:"FWebTableForm",
			FWebTableContext:"FWebTableContext",
			//
			FWebSelectData:"FWebSelectData",
			FWebSelectLabel:"FWebSelectLabel",
			FWebSelectValue:"FWebSelectValue",
			//
			FWebTreeMenusData:"FWebTreeMenusData",
			//
			FWebTreeData:"FWebTreeData",
			FWebCheckTreeData:"FWebCheckTreeData",
//			FWebTree:"FWebTree",
//			FWebTreeIFrame:"iframe",
//			FWebTreeTarget:"target",
			//
			FWebFormData:"FWebFormData",
			//
			FWebMessageData:"FWebMessageData",
			FWebBizData:"FWebBizData",
			FWebHtmlData:"FWebHtmlData"
		},
		init:function(fwebResData){
			FWeb.JSESSIONID=fwebResData.attr("JSESSIONID")||"";
			FWebSelect.loadData(fwebResData);
			FWebTable.loadData(fwebResData);
			FWebTree.loadData(fwebResData);
			FWebCheckTree.loadData(fwebResData);
			FWebForm.loadData(fwebResData);
			FWebMessage.loadData(fwebResData);
			FWebTreeMenus.loadData(fwebResData);
			FWebHtml.loadData(fwebResData);
		},
		loadJavaScriptFile:function(){
			$("head").append("<script type='text/javascript' src='fweb/widgets/fweb-select.js'></script>");
			$("head").append("<script type='text/javascript' src='fweb/widgets/fweb-form.js'></script>");
			$("head").append("<script type='text/javascript' src='fweb/widgets/fweb-table.js'></script>");
			$("head").append("<script type='text/javascript' src='fweb/widgets/fweb-tree.js'></script>");
			$("head").append("<script type='text/javascript' src='fweb/widgets/fweb-checkTree.js'></script>");
			$("head").append("<script type='text/javascript' src='fweb/widgets/fweb-treeMenus.js'></script>");
			$("head").append("<script type='text/javascript' src='fweb/widgets/fweb-message.js'></script>");
			$("head").append("<script type='text/javascript' src='fweb/widgets/fweb-biz.js'></script>");
			$("head").append("<script type='text/javascript' src='fweb/widgets/fweb-html.js'></script>");
			
		},
		loadCSSFile:function(){
//			$("head").append("<link rel='stylesheet' type='text/css' href='fweb/css/fweb.css'>");
		},
		//随机数
		rand:function(number){
			var seed=new Date().getTime();
			seed=(seed*9301+49297) % 233280;
			seed=seed/(233280.0);
			return Math.ceil(seed*number);
		}
};
