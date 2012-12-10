jQuery(function($) {
	
});
var FWebTreeMenus={
		loadData:function(fwebResData){
			var fwebTreeMenusData=$("#"+FWeb.attr.FWebTreeMenusData);
			if(!jQuery.isEmptyObject(fwebTreeMenusData)){
				$(fwebTreeMenusData).children().each(function(i){
					var name=$(this).attr("name");
					var treeMenus=$("#"+name);
					$(this).children().each(function(j){
						var tree=$(this);
						tree.addClass("trendsTree");
						tree.appendTo(treeMenus);
					});
				});
			}
		}
};
