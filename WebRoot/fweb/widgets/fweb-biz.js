jQuery(function($) {
	
});
var FWebBiz={
		options:{
			jsonContent:""
		},
		loadData:function(fwebResData){
			var fwebBizData=fwebResData.find("#"+FWeb.attr.FWebBizData);
			if(fwebBizData.length>0){
				$(fwebBizData).children().each(function(i){
//					var name=$(this).attr("name");
					var content=$(this).text();
					content=jQuery.parseJSON(content);
					FWebBiz.options.jsonContent=content;
				});
				$(fwebBizData).remove();
			}
		}
};