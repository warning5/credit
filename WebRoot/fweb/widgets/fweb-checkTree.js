jQuery(function($) {
	
});
var FWebCheckTree={
		loadData:function(fwebResData){
			var fwebCheckTreeData=$("#"+FWeb.attr.FWebCheckTreeData);
			if(!jQuery.isEmptyObject(fwebCheckTreeData)){
				$(fwebCheckTreeData).children().each(function(i){
					var name=$(this).attr("name");
					var ftree=$("#"+name);
					$(this).children().each(function(j){
						var tree=$(this);
//						tree.find("a").each(function(k){
//							$(this).attr("rel",ftree.attr("rel")||"jbsxBox");
//							$(this).attr("target","ajax");
//							var target=ftree.attr("target")||"function.id";
//							var href=ftree.attr("href")||"javascript";
//							href=href+"?"+target+"="+$(this).attr("pojo_id");
//							$(this).attr("href",href);
//						});
						tree.appendTo(ftree);
					});
				});
			}
		}
};
