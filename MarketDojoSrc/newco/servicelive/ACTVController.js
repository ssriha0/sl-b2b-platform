dojo.provide("newco.servicelive.ACTVController");
dojo.require("newco.servicelive.monitor.RealTimeManager");
dojo.declare("ACTVController", RealTimeManager, {

	allow_free_tabbing : true,
	
	constructor :  function( ) {		
	},
		
	constructor :  function( freeTabbing ) {
		this.allow_free_tabbing = freeTabbing;		
	}	
	
	
});