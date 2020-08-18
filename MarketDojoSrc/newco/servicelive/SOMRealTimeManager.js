/* THIS IS THE BASE NEWCO WIDGET */
dojo.provide("newco.servicelive.SOMRealTimeManager");
dojo.require("newco.servicelive.base.BaseTokenManager");
dojo.require("newco.servicelive.base.BaseRealTimeManager");

dojo.declare("SOMRealTimeManager", [BaseRealTimeManager, BaseTokenManager], {

	default_system_time : 10000,
		
	constructor :  function( url, interval, fcn) {
		// not needed uses the common cache
		this.commonCallBack = fcn;
		this.createCache(this._COMMON_CACHE_);
	},


	setSelectedWidget : function(widgetId, doSubmitAct){
		if(widgetId != null){
			//from dashboard - will pass displayTab or default one
			if(doSubmitAct != null &&
			   doSubmitAct == false){
			   this.widgetId = widgetId;
			   return;
			}
			if(widgetId == "Today"){
				//$('tab_lable').src = dojo.moduleUrl("dijit.themes.searssl","images/som/header_today.gif")
				//newco.jsutils.clearAllActionTiles(); 
				newco.jsutils.doIFrSubmit('TodaymyIframe');
				newco.jsutils.clearAllActionTiles();
				newco.jsutils.clearStatusSubstatus(widgetId);
			}
			else if(widgetId == "Draft" || widgetId == "Saved"){
				//$('tab_lable').src = dojo.moduleUrl("dijit.themes.searssl","images/som/header_draft.gif")
				//newco.jsutils.clearAllActionTiles(); 
				newco.jsutils.doIFrSubmit('DraftmyIframe');
				newco.jsutils.clearAllActionTiles();
				newco.jsutils.clearStatusSubstatus(widgetId);
			}
			else if(widgetId == "Posted"){
				//$('tab_lable').src = dojo.moduleUrl("dijit.themes.searssl","images/som/header_posted.gif")
				//newco.jsutils.clearAllActionTiles(); 
				newco.jsutils.doIFrSubmit('PostedmyIframe');
				newco.jsutils.clearAllActionTiles();
				newco.jsutils.clearStatusSubstatus(widgetId);
			}
			else if(widgetId == "Accepted"){
				//$('tab_lable').src = dojo.moduleUrl("dijit.themes.searssl","images/som/header_accepted.gif")
				//newco.jsutils.clearAllActionTiles(); 
				newco.jsutils.doIFrSubmit('AcceptedmyIframe');
				newco.jsutils.clearAllActionTiles();
				newco.jsutils.clearStatusSubstatus(widgetId);
			}
			else if(widgetId == "Problem"){
				//$('tab_lable').src = dojo.moduleUrl("dijit.themes.searssl","images/som/header_problem.gif")
				//newco.jsutils.clearAllActionTiles(); 
				newco.jsutils.doIFrSubmit('ProblemmyIframe');
				newco.jsutils.clearAllActionTiles();
				newco.jsutils.clearStatusSubstatus(widgetId);
			}
			else if(widgetId == "Inactive" || widgetId == "History"){
				//$('tab_lable').src = dojo.moduleUrl("dijit.themes.searssl","images/som/header_inactive.gif")
				//newco.jsutils.clearAllActionTiles(); 
				newco.jsutils.doIFrSubmit('InactivemyIframe');
				newco.jsutils.clearAllActionTiles();
				newco.jsutils.clearStatusSubstatus(widgetId);
			}
			else if(widgetId == "Search"){
				if($('tab_lable')){
					$('tab_lable').src = dojo.moduleUrl("dijit.themes.searssl","images/som/header_search.gif")
				}
					//newco.jsutils.clearAllActionTiles(); 
					newco.jsutils.clearSearchCriteria(widgetId);
					newco.jsutils.doIFrSubmit('SearchmyIframe');
					newco.jsutils.clearAllActionTiles();
			}
			else if(widgetId == "Received"){
				//$('tab_lable').src = dojo.moduleUrl("dijit.themes.searssl","images/som/header_received.gif")
				//newco.jsutils.clearAllActionTiles(); 
				newco.jsutils.doIFrSubmit('ReceivedmyIframe');
				newco.jsutils.clearAllActionTiles();
				newco.jsutils.clearStatusSubstatus(widgetId);
			}
		}
		this.widgetId = widgetId;
		
	},
	
	
	_handlerASyncUpdate : function(evt) {
	
		if(this.statePersisted == false){
			theContent = this._getQueryFromCache();
			if(theContent){
				//alert("I need to transmit "+theContent);
			}
			//$('debug').innerHTML = theContent;
			this._doAjaxCallBackAsXML( this.theUrl, theContent , this.commonCallBack);
		}
		else{
			//$('debug').innerHTML = $('listenerContainer').serialize();
			this._doAjaxCallBackAsXML( this.theUrl, $('listenerContainer').serialize() , this.commonCallBack);
		}
	},
	

	addToken : function( token ) {
		this.addToCache(this._COMMON_CACHE_, token);
	},
	
	reSync : function () {
		this.forceShutDown();
		this._serviceStart();
	}
	
}); 
