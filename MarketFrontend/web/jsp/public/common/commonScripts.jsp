<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>





<script type="text/javascript">
	dojo.require("newco.servicelive.token.SOToken");
	dojo.require("newco.servicelive.token.TabToken");
	dojo.require("newco.servicelive.token.CommonToken");
	dojo.require("newco.servicelive.SOMRealTimeManager");

	_commonSOMgr = new SOMRealTimeManager('/MarketFrontend/market/refreshTabs.action', 30000);
//	_commonSOMgr.addToken(
	 		//The first three tokens get set in the action
//	 		new TabToken('BUYER', '1', '2', '3', '4')
//	 );

	_commonSOMgr.startService();


</script>
<!-- include common scripts here -->

<script language="JavaScript" type="text/javascript" >


	function updateTabButtonLabel(tabId, labelName, tabCount){
		//SentChanged is not a tabId
		if (tabId!="SentChanged"){
			var tabLabel = labelName + " (" + tabCount + ")";
			var widget = dijit.byId(tabId);
			var mainController  = dijit.byId("mainTabContainer");	
			var tablist = mainController.tablist;
			var tabButton = tablist.pane2button[widget];
			
			// set the new string in both the ContentPane and the TabButton widget
			widget.title = tabButton.label = tabLabel;
		
			// change the DOM with the new string
			tabButton.containerNode.innerHTML = tabLabel;
			
			//tabButton.title = "TESTING";
			// re-render the TabContainer
			mainController.resize();
		}else{
			//do your check here for sent tab click
		 //if clicked check result count from server 
		 //if changed then refresh the grid
		 //handler
		 //$('iFrameId').doAction();
	
			if(_commonSOMgr.widgetId == 'Posted'){
				//alert("This is the sentTab clicked");
					//alert("Refresh the grid. Count "  + tabCount);
					//refresh the grid
			 		$('iframeID').contentWindow.doAction();
			 		//Blow away the tokens
			 		_commonSOMgr._clearPersistStateCache();
			 		//Add the Tokens with the new count 
			 		 _commonSOMgr.addToken(	new TabToken(4,5,7, 86,153) );
			}
		}
	 }
	 
	 function getXMLElement(element, data, tabId){
		var retVal  = "";

 		if(data.getElementsByTagName(element)[0]){
			retVal = data.getElementsByTagName(element)[0].childNodes[0].nodeValue;
			updateTabButtonLabel(tabId, tabId, retVal);
		}else{
			//alert("the RetVal is null");
		}
	}
	 
</script>

<script language="JavaScript" type="text/javascript" >

function filterStatus(status){

	if(status != null && status != ''){
		document.getElementById('iframeID').src="/MarketFrontend/filterDataGrid.action?filterByStatus="+status;	
	}

}

function filterSubStatus(subStatus){
	if(subStatus != null && subStatus != ''){
		document.getElementById('iframeID').src="/MarketFrontend/filterDataGrid.action?filterBySubStatus="+subStatus;	
	}
}

</script>

