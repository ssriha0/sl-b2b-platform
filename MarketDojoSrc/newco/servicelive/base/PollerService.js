// JavaScript Document
dojo.provide("newco.servicelive.base.PollerService");


// var x = new BaseListener( 5 );

dojo.declare("PollerService", null, {

	commonTopicName : "/so_update_event_topic",
	
	constructor: function( listenerURL, interval ){
	//alert("I was called BaseListener");
		this.interval = interval;
		this.theUrl = listenerURL;
			//alert("newco.servicelive.base.PollerService")
		//this.start();
	},
	
	constructor: function( listenerURL, interval, commonTopicName ){
	//alert("I was called BaseListener");
		this.interval = interval;
		this.theUrl = listenerURL;
		this.commonTopicName = commonTopicName;
			//alert("newco.servicelive.base.PollerService")
		//this.start();
	},
	
	onTick : function(){
		// summary: Method called every time the interval passes.  
		// Override to do something useful.
		dojo.publish(this.commonTopicName,[]);
	},
	
	start : function(){
		if (this.isRunning){
			window.clearInterval(this.timer);
		}	
		this.isRunning = true;
		this.timer = setInterval(dojo.hitch(this, "onTick"), this.interval);
		
	},
	
	stop : function(){
		this.isRunning = false;
		window.clearInterval(this.timer);
	},
	
	getUrl : function() {
		return this.theUrl;
	}
});
