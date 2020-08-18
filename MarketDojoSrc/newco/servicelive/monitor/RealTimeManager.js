// JavaScript Document
dojo.provide("newco.servicelive.monitor.RealTimeManager");
dojo.require("dojo.io.script");


dojo.declare("RealTimeManager", null, {

	constructor: function( ){
	},
	
	_doProcessWithHandler: function ( theUrl, theHandlerFunc, theContent, contentType ){
		//alert("The content in the real time mgr" + theContent);
		//alert("the url: " + theUrl);
		if(contentType == null){
			contentType = "xml";
		}
	
		
	   this._commonAjaxCallback = dojo.rawXhrPost({
				   	url :theUrl,
				   	handleAs: contentType,
				   	postData : theContent,
				   	load: theHandlerFunc,
				   	timeout: 20000,
				   	sync: false,
				   	handle: function(response, ioArgs){
			                //This function handles the response.
			                //Inside this function, the "this" variable
			                //will be the object used as the argument to the dojo.xhrGet() call.
							apphome=new dojo._Url(dijit.getDocumentWindow(dojo.doc).location.href,"");
							if(ioArgs.xhr.responseText.match("doLogin")!=null){
								dijit.getDocumentWindow(dojo.doc).location.pathname=newco.jsutils.getContext();
							}			                
			                if(response instanceof Error){
			                        if(response.dojoType == "cancel"){
			                                //The request was canceled by some other JavaScript code.
			                               // alert("Request canceled.");
			                        }else if(response.dojoType == "timeout"){
			                                //The request took over 5 seconds to complete.
			                                //alert("Request timed out.");
			                         $(newco.jsutils.getSelectedIfm()).contentWindow.showCommonMsg(0,'System timeout exception') 
			                                							
			                        }else{
			                               if(typeof _commonSOMgr == "undefined"){}
										   else{
												//alert("Request timed out.");	
												//_commonSOMgr.default_system_time = 60000 * 2;
											 	//$(newco.jsutils.getSelectedIfm()).contentWindow.showCommonMsg(0,'The Service Live Realtime System is down. <br>Please log out and contact customer support') 
												//_commonSOMgr._clearPersistStateCache();
												//_commonSOMgr.forceShutDown();
											}
			                        }
			                }else{
			                        //Success.Since original call wanted the response handled
			                        //as text (handleAs: "text"), response will be a text string.
			                        console.debug("Successful server response: " + response);
			                       
			                        //ioArgs is an object with some useful properties on it.
			                        //For instance, for XMLHttpRequest calls, ioArgs.xhr is the
			                        //XMLHttpRequest that was used for the call.
			                        console.debug("HTTP status code: ", ioArgs.xhr);
			                }
			                //If you think there could be other callback handlers registered with this deferred, then
			                //return response to propagate the same response to other callback handlers. Otherwise,
			                //the error callbacks may be called in the success case.
			                return response;
			          }
			   });
			   //If you think there could be other callback handlers registered with this deferred, then
			   //return response to propagate the same response to other callback handlers. Otherwise,
			   //the error callbacks may be called in the success case.
			   //this._commonAjaxCallback.addCallback( theHandlerFunc );
	},
	
	_doAjaxCallAsXML : function ( theUrl, content ) {
		 this._doProcessWithHandler( theUrl, this._getResultsAsXML, content, "xml" );
	},
	
	_doAjaxCallBackAsXML : function ( theUrl, content, fcn ) {
		this._doProcessWithHandler( theUrl, fcn, content, "xml" );
	},
	
	_doAjaxCallAsJSON : function ( theUrl, content ) {
		this._doProcessWithHandler( theUrl, this._getResultsAsJSON, content, "json-comment-filtered" );
	},

	_getResultsAsJSON : function ( json ) {
		return json;
	}
	
});