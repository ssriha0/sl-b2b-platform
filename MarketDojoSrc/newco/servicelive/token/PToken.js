dojo.provide("newco.servicelive.token.PToken");
dojo.require("newco.servicelive.base.HtmlGen");
dojo.declare("PToken", HtmlGen, {

	constructor: function( soId, slCount, index ) {
		this.soId 		= soId;
		this.slCount 	= slCount;
		//this.resourceId = resourceId
		this.idx 		= index;
		
		this._addData( this._buildElement2( "soId"+this.idx, this.soId) );
		this._addData( this._buildElement2(this.soId+"_slCount",this.slCount) );
		
	},
	
	persistState :  function ( ) {
		theData = this._genHtml( this.hiddenType );
		return theData;
	},
		
	
	updateToken: function ( newCount ) {
		//newco.jsutils.updateTheNode(this.fieldId , newCount);
	}
});
