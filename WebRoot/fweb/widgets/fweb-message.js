jQuery(function($) {
	
});
var FWebMessage={
		options:{
			jsonMessage:""
		},
		loadData:function(fwebResData){
			var fwebMessageData=fwebResData.find("#"+FWeb.attr.FWebMessageData);
			if(fwebMessageData.length>0){
				$(fwebMessageData).children().each(function(i){
					var name=$(this).attr("name");
					var message=jQuery.parseJSON($(this).text());
					FWebMessage.options.jsonMessage=message;
					FWebMessage.show(name,message.message);
				});
				$(fwebMessageData).remove();
			}
		},
		show:function(id,text){
			var message=$("#"+id);
			message.css("color","red");
			message.text(text);
		},
		ajaxDone:function(json){
			var fwebResData=$(json);
			FWebMessage.loadData(fwebResData);
			return FWebMessage.options.jsonMessage;
		}
};