/* THIS IS THE BASE NEWCO WIDGET */
dojo.provide("newco.servicelive.DashboardRealTimeManager");
dojo.require("newco.servicelive.base.BaseTokenManager");
dojo.require("newco.servicelive.base.BaseRealTimeManager");

dojo.declare("DashboardRealTimeManager", [BaseRealTimeManager, BaseTokenManager], {

	_DASHBOARD_CACHE : "dashboardCache", 

		
	constructor :  function( url, interval, fcn ) {
		// not needed uses the common cache
		// see newco.servicelive.base.BaseTokenManager
		this.commonCallBack = fcn;
		this.createCache(this._DASHBOARD_CACHE);
	},

	// _handlerASyncUpdate:
	//		A registry to make contextual calling/searching easier.
	// description:
	_handlerASyncUpdate : function(evt) {
	
		if(this.statePersisted == false){
			theContent = this._getQueryFromCache( this._DASHBOARD_CACHE );
			this._doAjaxCallBackAsXML( this.theUrl, theContent , this.commonCallBack);
		}
		else{
			this._doAjaxCallBackAsXML( this.theUrl, $('listenerContainer').serialize() , this.commonCallBack);
		}
	},
	
		
	addToken : function( token ) {
		this.addToCache(this._DASHBOARD_CACHE, token);
	},
	
	reSync : function () {
		this.forceShutDown();
		this._serviceStart();
	}

});