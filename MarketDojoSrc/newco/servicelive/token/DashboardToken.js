dojo.provide("newco.servicelive.token.DashboardToken");
dojo.require("newco.servicelive.token.CommonToken");

dojo.declare("DashboardToken", CommonToken, {

	constructor: function( roleType, 
						   companyId, 
						   vendBuyerResId){
		
	},
	
	persistState :  function ( ) {
		this.inherited(arguments)
		
	},
		
	
	updateToken: function ( newCount ) {
		if($('tab_1') != null )
		{
			$('tab_1').value = newCount ;
		}
		else
		{
			this.tabId.innerHTML = '0';
		}
		this.lastUpdated = new Date();
	}
});
