dojo.provide("newco.servicelive.token.SOToken");
dojo.require("newco.servicelive.base.HtmlGen");
dojo.require("newco.jsutils");

dojo.declare("SOToken", HtmlGen, {

	constructor: function( routedDivId, 
						   acceptedDivId, 
						   rejectedDivId,
						   spendLimitDivId,
						   realTimeStatus,
						   soId,
						   index )
	{
		this.routedDivId = routedDivId;
		this.acceptedDivId = acceptedDivId;
		this.rejectedDivId = rejectedDivId;
		this.spendLimitDivId = spendLimitDivId;
		this.rtStatus = realTimeStatus;
		this.soId = soId;
		this.idx = index;
		this._buildSOStr( );
		this._addData( this._buildElement2("soId"+this.idx,soId));
	},
	
	updateToken: function ( routedVal, acceptedVal, rejectedVal, spendLimitVal ) {
	
		newco.jsutils.updateTheNode(this.routedDivId , routedVal);
		newco.jsutils.updateTheNode(this.acceptedDivId , acceptedVal);
		newco.jsutils.updateTheNode(this.rejectedDivId , rejectedVal);
		newco.jsutils.updateTheNode(this.spendLimitDivId , spendLimitVal);
	},
	
	_buildSOStr : function () {
		if(this.rtStatus  != null)
		{
			this._addData( this._buildElement2(this.soId+"_roCount",this.rtStatus.routedCnt) );
			this._addData( this._buildElement2(this.soId+"_acCount",this.rtStatus.acceptedCnt) );
			this._addData( this._buildElement2(this.soId+"_reCount",this.rtStatus.rejectedCnt) );
			this._addData( this._buildElement2(this.soId+"_slCount",this.rtStatus.spendLimit) );
		}
	},
	
	persistState :  function (  ) {
		theData = this._genHtml( this.hiddenType );
		return theData;
	}
	
});
