jQuery(function($) {
	
});
var FWebTable={
		options:{
			jsonContent:""
		},
		loadData:function(fwebResData){
			var fwebTableData=fwebResData.find("#"+FWeb.attr.FWebTableData);
			if(fwebTableData.length>0){
				$(fwebTableData).children().each(function(i){
					var name=$(this).attr("name");
					
					var table=$("#"+name);
					var head=table.find("tr["+FWeb.attr.FWebTableHead+"]").get(0);
					var hasData=false;
					$(this).find("#beans").children().each(function(n){
						var content=$(this).text();
						content=jQuery.parseJSON(content);
						FWebTable.options.jsonContent=content;
						
						var tr=$(head).clone(true);
						$(tr).removeAttr("FWebTable");
						var pojoID=$(tr).attr("rel")||"pojoID";
						var target=$(tr).attr("target");
						pojoID=pojoID.split(".");
						$.each(pojoID,function(i){
							FWebTable.options.jsonContent=FWebTable.options.jsonContent[pojoID[i]];
						});
						pojoID=FWebTable.options.jsonContent;
						if(target){
							$(tr).find("a").each(function(i){
								var re = new RegExp("_","g");
								target=target.replace(re, "."); 
								tabid = navTab._getTabs().eq(navTab._currentIndex).attr("tabid");
								$(this).attr("href",$(this).attr("href")+"?"+target+"="+pojoID+"&tabId="+tabid);
							});
						}
						$(tr).attr("rel",pojoID);
						
						//添加复选框
						var ids=$(tr).find("td["+FWeb.attr.FWebTableIDS+"]").get(0);
						$(ids).removeAttr("FWebTable");
						var check=$(ids).find(":checkbox").first();
						$(check).attr("value",pojoID);
						$(check).attr("id",pojoID);
//						$(ids).append(check);
						//添加数据
						FWebTable.parseData(content, $(tr));
						$(head).parent().append($(tr));
						hasData=true;
					});
					if(hasData){
						$(head).remove();
						var paging=$(this).find("#paging").get(0);
						var content=$(paging).text();
						content=jQuery.parseJSON(content);
						var datainfo=table.nextAll().find(".pages").find("div["+FWeb.attr.FWebTableDataInfo+"]");
						if($(datainfo).attr("FWebTable")){
							datainfo.remove();
							var span=$("<span>条，共"+content.allCount+"条记录，每页"+content.numPerPage+"条，当前第"+content.currentPage+"/"+content.pageCount+"页</span>");
							table.nextAll().find(".pages").append(span);
						}
						var pagination=table.nextAll().find(".pagination");
						pagination.attr("totalCount",content.allCount);
						pagination.attr("numPerPage",content.numPerPage);
						pagination.attr("currentPage",content.currentPage);
						
						table.nextAll().find("#pagerForm :hidden[name=numPerPage]").val(content.numPerPage);
					}else{
						$(head).parent().html("<tr><td><center>抱歉，没有任何记录。</center></td></tr>");
						var paging=$(this).find("#paging").get(0);
						var content=$(paging).text();
						content=jQuery.parseJSON(content);
						table.nextAll().find("#pagerForm :hidden[name=numPerPage]").val(content.numPerPage);
					}
				});
				$(fwebTableData).remove();
			}
		},
		parseData:function(content,tr){
			$.each(content ,function(i){
				var value=content[i];
				if(jQuery.type(value)=="object"){
					var id=i+"_";
					FWebTable.setValue(value,id,tr);
				}else{
					var o=tr.find("#"+i);
					if(!$(o).attr("type")){
						$(o).text(value);
					}else{
						$(o).val(value);
					}
				}
			});
		},
		setValue:function(content,id,tr){
			$.each(content ,function(n){
				var value=content[n];
				if(jQuery.type(value)=="object"){
					var nid=id+n+"_";
					FWebTable.setValue(value,nid,tr);
				}else{
					var o=tr.find("#"+id+n);
					if(!$(o).attr("type")){
						$(o).text(value);
					}else{
						$(o).val(value);
					}
//					alert(id+n+"="+value);
				}
			});
		}
};
