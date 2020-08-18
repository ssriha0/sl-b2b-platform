dojo.provide("newco.servicelive.base.BaseTokenManager");


dojo.declare("BaseTokenManager", null, {

	_COMMON_CACHE_ : "COMMON-RTCache",
	
	constructor : function ( ) {
		//alert(this._cacheManager.length);
		this.createCache( this._COMMON_CACHE_ );
	},
	
	constructor : function ( cacheName ) {
		// init the cache
		this._cacheManager = new Array();
		// added new element
		this._cacheManager[this._cacheManager.length] = new Array();
		for( var i = 0; i< this._cacheManager.length; i++)
		{
			this._cacheManager[cacheName] = new Array();
		}
		delete this._cacheManager[0];
		//alert(this._cacheManager.length);
	},

	
	_findCache : function( cacheName ){
		//alert(cacheName);
		//alert(this._cacheManager.length);
		var aCache = this._cacheManager[ cacheName ];
		//alert(aCache);
		if(aCache != null){
			return aCache;
		}
	},
	
	_clearCache : function( cacheName ){
		aCache = this._findCache(cacheName);
		//alert(aCache);
		if(aCache != null){
			aCache.clear();
		}
	},
	
	//destroyCache 
	_getCacheList : function() {
		return this._cacheManager;
	},


	addToCache : function (cacheName, obj) {
		this._findCache( cacheName ).push( obj );	
	},


	destroyCache : function( cacheName ) {
		 delete this.findCache( cacheName ) ;
	},


	createCache : function (newCacheName ) {
		this._cacheManager[this._cacheManager.length] = new Array();
		for( var i = 0; i< this._cacheManager.length; i++)
		{
			this._cacheManager[newCacheName] = new Array();
		}
		delete this._cacheManager[0];
	}

});
