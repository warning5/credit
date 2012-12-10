jQuery(function($) {
	
});
var FWebForm={
		options:{
			jsonContent:""
		},
		loadData:function(fwebResData){
			var fwebFormData=fwebResData.find("#"+FWeb.attr.FWebFormData);
			if(fwebFormData.length>0){
				$(fwebFormData).children().each(function(i){
					var name=$(this).attr("name");
					var content=$(this).text();
					content=jQuery.parseJSON(content);
//					FWebForm.options.jsonContent=content;
					var form=$("#"+name);
					FWebForm.parseData(content,form);
				});
				$(fwebFormData).remove();
			}
		},
		parseData:function(content,form){
			$.each(content ,function(i){
				var value=content[i];
				if(jQuery.type(value)=="object"){
					var id=i+"_";
//					FWebForm.parseData(value,id,form);
					FWebForm.setValue(value,id,form);
				}else{
					form.find("#"+i).attr("value",value);
//			form.find("#"+i).val(value);
//					alert(i+"="+value);
				}
			});
		},
		setValue:function(content,id,form){
			$.each(content ,function(n){
				var value=content[n];
				if(jQuery.type(value)=="object"){
					var nid=id+n+"_";
					FWebForm.setValue(value,nid,form);
				}else{
					form.find("#"+id+n).attr("value",value);
//					alert(id+n+"="+value);
				}
			});
		}
};