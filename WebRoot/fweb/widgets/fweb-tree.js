jQuery(function($) {
	
});
var FWebTree={
		loadData:function(fwebResData){
			var fwebTreeData=$("#"+FWeb.attr.FWebTreeData);
			if(!jQuery.isEmptyObject(fwebTreeData)){
				$(fwebTreeData).children().each(function(i){
					var name=$(this).attr("name");
					var ftree=$("#"+name);
					$(this).children().each(function(j){
						var tree=$(this);
						tree.addClass("trendsTree");
						tree.find("a").each(function(k){
							$(this).attr("rel",ftree.attr("rel")||"jbsxBox");
							$(this).attr("target","ajax");
							var target=ftree.attr("target")||"function.id";
							var href=ftree.attr("href")||"javascript";
							href=href+"?"+target+"="+$(this).attr("pojo_id");
							$(this).attr("href",href);
						});
						tree.appendTo(ftree);
					});
				});
			}
		}
};
