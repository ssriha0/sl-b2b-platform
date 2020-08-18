dojo.provide("newco.servicelive.token.TabToken");
dojo.require("newco.servicelive.base.HtmlGen");
dojo.require("newco.jsutils");

dojo.declare("TabToken", HtmlGen, {

	constructor: function( tokenName, countVal ) {
		this.countVal = countVal;
		this._addData( this._buildElement2( tokenName, this.countVal) );
	},
	
	persistState :  function ( ) {
		theData = this._genHtml( this.hiddenType );
		return theData;
	},
		
	
	updateToken: function ( newCount ) {
		//newco.jsutils.updateTheNode(this.fieldId , newCount);
	}
});
