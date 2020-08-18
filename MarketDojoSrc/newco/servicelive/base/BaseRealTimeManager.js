
/* THIS IS THE BASE NEWCO WIDGET */
dojo.provide("newco.servicelive.base.BaseRealTimeManager");
dojo.require("newco.servicelive.base.PollerService");
dojo.require("newco.servicelive.monitor.RealTimeManager");
dojo.require("newco.servicelive.base.HtmlGen");

dojo.declare("BaseRealTimeManager", [PollerService, RealTimeManager, HtmlGen],  {

	constructor: function( url, interval ){
		this.theUrl = url;
		this.interval = interval;
		this._ajaxAction = null;
		this._createBasePersistanceContainer();
		this.statePersisted = false;
		this.clearedToStart = false;
		this._init();
	},
	
	_init : function( ) {
		dojo.subscribe(this.commonTopicName, this, this._handlerASyncUpdate);
	},
	
	startService : function() {
		this.clearedToStart = true;
		if($('listenerContainer') == null )
		{
			this._createBasePersistanceContainer();
		}
		else
		{
			this.internalRef = $('listenerContainer');
		}
		setTimeout( dojo.hitch(this, "_serviceStart"), 1000);
	},
	
	_serviceStart : function() {
		//alert("starting");
		if(this.clearedToStart)
			if(!this.isRunning)
				this.start()
	},

	forceShutDown : function () {
		if(this.isRunning)
		{
			this.stop();
			//$('debug').innerHTML = "";
		}
		this.clearedToStart = false;
		this.statePersisted = false;
	},
	
	
	_stopService : function() {
		if(this.isRunning)
		{
			this.stop();
		}
		//this.clearedToStart = false;
		//this.statePersisted = false;
	},
	
	
	
	_createBasePersistanceContainer : function( ) {
		if($('aContainer') == null)
		{
			document.write('<div id="aContainer"></div>')
		}
		containers = { listnerContainer : "listenerContainer",
					   queryListner : "queryKeys"};
		this._genQueryContainer( containers, 'aContainer');	
		this.internalRef = containers.listnerContainer;
		this.containIsCreated = true;
	},
	
	
	_persistCache : function( ) {
		if($('listenerContainer') != null){
			caches = this._findCache(this._COMMON_CACHE_)
			if( caches != null){
				for(var i = 0; i< caches.length; i++){
					var inner = caches[i];
					if(inner != null){
						this._setupCache( inner );
					}
				}
			}
			this.statePersisted = true;
		}
	},
	
	
	_getQueryFromCache : function( cacheKey ) {
			if(cacheKey == null)
			{
				caches = this._findCache(this._COMMON_CACHE_);
			}
			else
			{
				caches = this._findCache(cacheKey);
			}
			queryStr = "";
			if( caches != null){
				for(var i = 0; i< caches.length; i++){
					var inner = caches[i];
					if(inner != null){
						queryStr += inner.persistState();
					}
				}
			}
			outStr = "";
			if($('queryKeys') && queryStr != null && queryStr != "")
			{
				$('queryKeys').innerHTML = queryStr;
				//alert(" I added "+ $('queryKeys').innerHTML);
				this.statePersisted = true;
				if($('listenerContainer'))
				{
					outStr = $('listenerContainer').serialize();
				}
			}
			return outStr;
	},
	
	
	_clearPersistStateCache : function( ) {
		//if(!dojo.isIE)
		//{
			this._stopService();
		//}
		this._clearCache(this._COMMON_CACHE_);
		if($('queryKeys')){
			$('queryKeys').innerHTML = "";
		}
		this.statePersisted = false;
	},
	
	_setupCache : function( token ) {
				token.persistState( this.internalRef );
	},
	
	_getFromCache : function( token ) {
				token.persistState( );
	},
	
	_handlerASyncUpdate : function (evt) {
		// override me
	}
});
