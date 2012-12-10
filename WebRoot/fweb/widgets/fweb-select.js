jQuery(function($) {
	
});
var FWebSelect={
		loadData:function(fwebResData){
			var fwebSelectData=fwebResData.find("#"+FWeb.attr.FWebSelectData);
			if(fwebSelectData.length>0){
				$(fwebSelectData).children().each(function(i){
					var name=$(this).attr("name");
					var content=$(this).text();
					content=jQuery.parseJSON(content);
					var select=$("#"+name);
//					select.children().remove();
					FWebSelect.parseData(content,"",select);
				});
			}
		},
		parseData:function(content,id,select){
			$.each(content ,function(i){
				var l=select.attr(FWeb.attr.FWebSelectLabel);
				var v=select.attr(FWeb.attr.FWebSelectValue);
				var label=content[i];
				var value=content[i];
				l=l.split("_");
				$.each(l,function(n){
					label=label[l[n]];
				});
				v=v.split("_");
				$.each(v,function(k){
					value=value[v[k]];
				});
				var opt= $('<option>',{val:value,text:label});
				$(opt).appendTo(select);
			});
		}
};