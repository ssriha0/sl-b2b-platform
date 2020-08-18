dojo.provide("newco.rthelper");

dojo.require("newco.servicelive.token.SOToken");
dojo.require("newco.servicelive.SOMRealTimeManager");


	// initNewSOMRTManager:
	//		A registry to make contextual calling/searching easier.
	// description:

	
newco.rthelper.initNewSOMRTManager = function(url, interval, cb){
			return new SOMRealTimeManager(url, interval, cb);
	}




	
	
	// createSOToken:
	//		A registry to make contextual calling/searching easier.
	// description:
newco.rthelper.createSOToken = function(/*div id of the routed count*/ routedDivId, 
						   				/*div id of the accepted count*/ acceptedDivId, 
						   				/*div id of the rejected count*/ rejectedDivId, 
						   				/*div id of the rejected count*/ spendLimit, 
						   				/*{ key: val} send to the listner server action */ realTimeStatus,
						   				/* service order num*/ soId,
						   				/* index is need to ensure request keys are unique*/index) {
			
			return new SOToken(routedDivId, acceptedDivId, rejectedDivId,spendLimit,
	 						   realTimeStatus, soId, index);
	}