

/* THIS IS THE BASE NEWCO WIDGET */
dojo.provide("newco.servicelive.CommonAjaxProxy");
dojo.require("newco.servicelive.monitor.RealTimeManager");

dojo.declare("CommonAjaxProxy", RealTimeManager,  {

	constructor: function( ){
		
	},
	
	doPostAction: function (  url, callBackMethod, updateElement, formElement ) {
		if(url == null || callBackMethod == null || formElement == null)
		{
			//alert("Please check method signature");
			return;
		}
		this._doProcessWithHandler(url, callBackMethod, $(formElement).serialize(), 'xml');
	},
	
	doPostURLParamAction: function (  url, callBackMethod ) {
		if(url == null || callBackMethod == null)
		{
			//alert("Please check method signature");
			return;
		}
		//alert("commonajax " + callBackMethod);
		
		
		
		this._doProcessWithHandler(url, callBackMethod, null, 'xml');
	}
	
});