dojo.provide("newco.servicelive.token.CommonToken");
dojo.require("newco.servicelive.base.HtmlGen");


dojo.declare("CommonToken", HtmlGen, {

	constructor: function( roleType, companyId, vendBuyerResId )
	{
		this._addData( this._buildElement2("roleType",roleType) );
		this._addData( this._buildElement2("companyId",companyId) );
		this._addData( this._buildElement2("vendBuyerResId",vendBuyerResId) );
	},
	
	persistState :  function ( ) 
	{
		theData = this._genHtml( this.hiddenType );
		return theData;
	},
	
	updateToken: function ( newCount ) {
	
	}
});
