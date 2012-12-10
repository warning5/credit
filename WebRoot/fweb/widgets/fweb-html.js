jQuery(function($) {
	
});
var FWebHtml={
		options:{
			content:""
		},
		loadData:function(fwebResData){
			var fwebHtmlData=fwebResData.find("#"+FWeb.attr.FWebHtmlData);
			if(fwebHtmlData.length>0){
				$(fwebHtmlData).children().each(function(i){
					var name=$(this).attr("name");
					var content=$(this).html();
					$("#"+name).html(content);
//					FWebHtml.options.content=content;
				});
				$(fwebHtmlData).remove();
			}
		}
};