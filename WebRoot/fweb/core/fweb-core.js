$(document).ready(function(){
	//$("head").append("<script type='text/javascript' src='fweb/core/fweb.js'></script>");
	//FWeb.loadCSSFile();
//	FWeb.loadJavaScriptFile();
	var fwebResData=$("#FWebResData");
	if(!jQuery.isEmptyObject(fwebResData)){
		FWeb.init(fwebResData);
		fwebResData.remove();
	}
});
