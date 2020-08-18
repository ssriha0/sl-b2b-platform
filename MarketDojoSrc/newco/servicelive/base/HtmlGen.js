dojo.provide("newco.servicelive.base.HtmlGen");


dojo.declare("HtmlGen", null, {

	hiddenType :  "hidden",
	
	constructor: function(){
		this._dataContainer = new Array();
	},
	
	_addData : function( /* element data, should be in the {test: 'test', etc..} format*/ data ) {
		this._dataContainer.push( data );
	},
	
	_buildElement : function( valName ) {
		if( valName != null)
		{
			return  {name : valName, value : valName};
		} 
	},
	
	_buildElement2 : function( theName, val ) {
		if( val != null && theName != null)
		{
			return  {name : theName, value : val, id: theName};
		} 
	},
	
	_genHtml : function( type )
	{
		tempGen = new Template('<input type="hidden" name="#{name}" value="#{value}" id="#{name}"/>');
		strOut = "";
		if(type == this.hiddenType)
		{
				for(var i = 0; i<this._dataContainer.length; i++)
				{
						conv = this._dataContainer[ i ];
					 	strOut += tempGen.evaluate(conv); 
				}
		}
		return strOut;
	},
	
	
	_genQueryContainer : function( keyData, containerId) {
		tempGen = new Template("<form id='#{listnerContainer}'><div id='#{queryListner}'></div></form>");
		strOut = tempGen.evaluate(keyData); 
		 if($(containerId))
		 {
		 	$(containerId).innerHTML = strOut;
		 }
	}
});