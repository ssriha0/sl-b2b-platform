dojo.provide("newco.jsutils");
dojo.require("dijit.Dialog");

dojo.require("newco.servicelive.CommonAjaxProxy");
dojo.require("newco.servicelive.token.PToken");
dojo.require("newco.servicelive.token.TabToken");
dojo.require("dojo.number");


// updateTabButtonLabel:
	//		A registry to make contextual calling/searching easier.
	// description:
	//		Objects of this class keep list of arrays in the form [name, check,
	//		wrap, directReturn] that are used to determine what the contextual
	//		result of a set of checked arguments is. All check/wrap functions
	//		in this registry should be of the same arity.


var _GLOBAL_CONTEXT_ = "/";
var _GLOBAL_STATIC_CONTEXT = "";

var _CURRENT_SELECTED_TAB = "";
var _CURRENT_SELECTED_IFRAME_TAB = "";
var _CURRENT_FIRING_ELEMENTS  = null;
var GLOBAL_OBJECT = new Object();
GLOBAL_OBJECT[0] = window;

dojo.addOnLoad(
	function() {
		dojo.unsubscribe('current_selected_tab');
		dojo.unsubscribe('the_firing_element');
		dojo.subscribe('current_selected_tab', this, newco.jsutils.handleTabEvent);
		dojo.subscribe('the_firing_element',this, newco.jsutils.setEventsElements );
	}
);

_CURRENT_FIRING_ELEMENTS
newco.jsutils.setCurrentSelectedTab = function(tabId) {
	_CURRENT_SELECTED_TAB = tabId;
}

newco.jsutils.setEventsElements = function(elementsArray) {
	//alert(elementsArray)
	_CURRENT_FIRING_ELEMENTS = elementsArray;
}

newco.jsutils.getEventsElements = function() {
	//alert("getEventsElements"+_CURRENT_FIRING_ELEMENTS)
	return _CURRENT_FIRING_ELEMENTS;
}


newco.jsutils.getCurrentSelectedIframe = function() {
	return _CURRENT_SELECTED_IFRAME_TAB;
}

newco.jsutils.setCurrentSelectedIframe = function(ifr) {
	 _CURRENT_SELECTED_IFRAME_TAB ='spn_type_'+ifr;
}


newco.jsutils.handleTabEvent = function(tabId){
	//clear market criteria back to default
	dojo.byId('marketId').selectedIndex = 0;
	//alert(tabId);
	//alert(frameId);
	newco.jsutils.doIFrSubmit2('spn_type_'+tabId,'spnMemberManager','showView.action');
	_CURRENT_SELECTED_IFRAME_TAB = 'spn_type_'+tabId;
	if(tabId != null && tabId == 50){
		$('remove').disabled  =  true;
		$('approve').disabled  =  true;
	}
	else if(tabId != null && tabId == 20){
		//alert($('approvedBtn').style.visibility);
		$('remove').disabled  =  false;
		$('approve').disabled  =  false;
		//alert($('approvedBtn').style.visibility);
	}else if(tabId != null && tabId == 40){
		$('remove').disabled  =  false;
		$('approve').disabled  =  true;
	}

}

newco.jsutils.doButtonAction = function( key ) {
	this.theIframe = newco.jsutils.getCurrentSelectedIframe();
	if(key != null && key == 'remove'){
		newco.jsutils.doIFrSubmit2(this.theIframe,'spnMemberManager','removeMembers.action');
	}
	else if(key != null && key == 'approve'){
		newco.jsutils.doIFrSubmit2(this.theIframe,'spnMemberManager','approveMembers.action');
	}
}

newco.jsutils.doFiltering = function( theElement ) {
	    this.theIframe = newco.jsutils.getCurrentSelectedIframe();
	    dojo.publish('the_firing_element',[theElement.options[theElement.selectedIndex].value]);
		newco.jsutils.doIFrSubmit2(this.theIframe,'spnMemberManager','filterGridResults.action');
}



newco.jsutils.getCurrentSelectedTab = function() {
	return _CURRENT_SELECTED_TAB;
}

newco.jsutils.updateTabButtonLabel = function(tabId, labelName, tabCount)
{
	try
	{
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
		}
		else
		{
			if(parent._commonSOMgr.widgetId == 'Posted' ||
				parent._commonSOMgr.widgetId == 'Received' ){
					parent._commonSOMgr._clearPersistStateCache();
					newco.jsutils.doIFrSubmit(parent._commonSOMgr.widgetId+"myIframe");
			 		//$('inner_realtime_grid').contentWindow.doAction();
			 		//Blow away the tokens

			}
		}
   }
	catch( ex )
	{
		if( GLOBAL_OBJECT[1] == 5 )         // SIMPLE BUYER ONLY
		{
			if( tabCount == 1 && (tabId == "Accepted" || tabId == "Today" || tabId == "Current" || tabId == "Complete" || tabId == "Closed") )
			{
				GLOBAL_OBJECT[0].location.reload( true );
			}
		}
	}
}
 newco.jsutils.setGlobalContext = function(context){
 	_GLOBAL_CONTEXT_ = context+"/";
 }

  newco.jsutils.setGlobalStaticContext = function(context){
 	_GLOBAL_STATIC_CONTEXT = context;
 }

  newco.jsutils.getStaticContext = function(){
 	return _GLOBAL_STATIC_CONTEXT;
 }

 newco.jsutils.getContext = function(){
 	return _GLOBAL_CONTEXT_;
 }


	 // getXMLElement:
	//		A registry to make contextual calling/searching easier.
	// description:
	//		Objects of this class keep list of arrays in the form [name, check,
	//		wrap, directReturn] that are used to determine what the contextual
	//		result of a set of checked arguments is. All check/wrap functions
	//		in this registry should be of the same arity.
newco.jsutils.getXMLElement  =	 function(element, data, tabId){
		var retVal  = "";

 		if(data.getElementsByTagName(element)[0]){
			retVal = data.getElementsByTagName(element)[0].childNodes[0].nodeValue;
			newco.jsutils.updateTabButtonLabel(tabId, tabId, retVal);
		}else{
		}
	}

newco.jsutils.getCommonXMLElement  =	 function(element, data){
		var retVal  = "";
 		if(data.getElementsByTagName(element)[0]){
			retVal = data.getElementsByTagName(element)[0].childNodes[0].nodeValue;
			return retVal
		}else{
			return null;
		}
	}

	// doIFrSubmit:
	//		A registry to make contextual calling/searching easier.
	// description:
	//		Objects of this class keep list of arrays in the form [name, check,
	//		wrap, directReturn] that are used to determine what the contextual
	//		result of a set of checked arguments is. All check/wrap functions
	//		in this registry should be of the same arity.
newco.jsutils.doIFrSubmit = function(/*id of the Iframe*/ iframeId ){
		if( newco.jsutils.isExist(iframeId) ){
			if($(iframeId).contentWindow){
				$(iframeId).contentWindow.doAction();
			}
		}
	}


newco.jsutils.doIFrSubmit2 = function(/*id of the Iframe*/ iframeId, /*action name*/action, /*method name*/method ){
		if( newco.jsutils.isExist(iframeId) ){
			if($(iframeId).contentWindow){
				$(iframeId).contentWindow.doAction(action,method);
			}
		}
	}

	// doIFrSubmit:
	//		A registry to make contextual calling/searching easier.
	// description:
	//		Objects of this class keep list of arrays in the form [name, check,
	//		wrap, directReturn] that are used to determine what the contextual
	//		result of a set of checked arguments is. All check/wrap functions
	//		in this registry should be of the same arity.
newco.jsutils.doFormSubmit = function(/*action name*/ actionName, /*common form Id*/ formId){
		if( newco.jsutils.isExist(formId) ){
			$(formId).action = actionName;
			$(formId).method = 'post';
			$(formId).submit();
		}
	}



    // formSubmit:
	// to submit the form based on the form id
newco.jsutils.formSubmit = function( /*common form Id*/ formId){
		if( newco.jsutils.isExist(formId) ){
					$(formId).method = 'post';
		        	$(formId).submit();
		}
	}


newco.jsutils.clearSearchCriteria = function(tabName){
	if( newco.jsutils.isExist($('searchType'))){
		var searchType = $("searchType");
		searchType.selectedIndex = 0;
	}

	if( newco.jsutils.isExist($('searchValue'))){
		var searchValue = $("searchValue");
		searchValue.value = "";
	}

	if( newco.jsutils.isExist($("statusId"+tabName))){
		var status = $("statusId"+tabName);
		status.selectedIndex = 0;
	}

	if( newco.jsutils.isExist($("subStatusId"+tabName))){
		var subStatus = $("subStatusId"+tabName);
		subStatus.selectedIndex = 0;
	}
}






newco.jsutils.detailView = function(/*the service order*/soId, /*rid*/ rId, /*command*/ goD){
		var formId = 'detailViewer';
		if( newco.jsutils.isExist(formId) ){
			newco.jsutils.Uvd('soId',soId)
			newco.jsutils.Uvd('rid',rId);
			newco.jsutils.Uvd('goD',goD);
			$(formId).method = 'post';
			$(formId).submit();
		}
	}
	// updateTheNode:
	//		A registry to make contextual calling/searching easier.
	// description:
newco.jsutils.Uvd = function(/* hidden val id*/ hvid, /*node value*/ nodeVal){
		if( newco.jsutils.isExist(hvid) ){
			$(hvid).value = nodeVal;
	 	}
	}

newco.jsutils.Uvd2 = function(iframeId,/* hidden val id*/ hvid, /*node value*/ nodeVal){
		if ($(iframeId).contentWindow){
			retVal = newco.jsutils.isExist($(iframeId).contentWindow.document.getElementById(hvid));

			if(retVal){
				$(iframeId).contentWindow.document.getElementById(hvid).value = nodeVal;
		 	}

		}

	}


	// updateTheNode:
	//		A registry to make contextual calling/searching easier.
	// description:
newco.jsutils.JumpToActionTab = function(/* The tab id*/ tabId, /* The Tab Container */tabContainer){
var theContainer;
		  if(container == null){
			 theContainer =  dijit.byId('mainTabContainer');
		   }
		   else{
		   	 theContainer =  dijit.byId(container);
		   }
	    if(dijit.byId(tabContainer))
	    {
	    	//theContainer =  dijit.byId(tabContainer);
			if(dijit.byId(tabId)){
		 		var tab = dijit.byId(tabId);
		    	 tab.selectChild(true);
		     //	 tab.refresh();
	 		}
	 	}
}

newco.jsutils.JumpToActionTabWithURL = function(/* The tab id*/ tabId, /* The Tab Container */container, /*The Url Location*/ theUrl){


		var theInnerContainer;
		  if(container == null){
			 theInnerContainer =  dijit.byId('mainTabContainer');
		   }
		   else{
		   	 theInnerContainer =  dijit.byId(container);
		   }
	    if(dijit.byId(theInnerContainer))
	    {
	    	var theContainer =  dijit.byId(theInnerContainer);

			if(dijit.byId(tabId)){
		 		var tab = dijit.byId(tabId);

		    	 tab.setHref(theUrl);
		    	 theContainer.selectChild(tab);
		    	 _commonSOMgr.widgetId=tabId;
		    	// tab.refresh();
	 		}
	 	}
}


	// updateTheNode:
	//		A registry to make contextual calling/searching easier.
	// description:
newco.jsutils.jumpToActionTabWithRefresh = function(/* The tab id*/ tabId, /* The tab container*/ container){
		//if(dijit.byId(tabId)){
		  var theContainer;
		  if(container == null){
			 theContainer =  dijit.byId('mainTabContainer');
		   }
		   else{
		   	 theContainer =  dijit.byId(container);
		   }
	   		if(dijit.byId(theContainer))
	   		{
	    		//theContainer =  dijit.byId(tabContainer);
				if(dijit.byId(tabId)){
		 			var tab = dijit.byId(tabId);
		    	 	theContainer.selectChild(tab);
		     	 	tab.refresh();
	 			}
	 		}
	 	//}
    }


    // jumpToPanelWithRefresh:
    // Refresh a tab and then jump to a panel on the tab
    newco.jsutils.jumpToPanelWithRefresh = function(tabId, anchorId, container) {
        newco.jsutils.jumpToActionTabWithRefresh(tabId, container);

        setTimeout("newco.jsutils.gotoAnchorTag('" + tabId + "','" + anchorId + "')", 1000);
    }//end function jumpToPanelWithRefresh


	// gotoAnchorTag:
	// Jump to the provided anchorId on the provided tabId
	newco.jsutils.gotoAnchorTag = function(tabId, anchorId) {
	    if(dijit.byId(tabId)){
            var selectedTab = dijit.byId(tabId);
            if (selectedTab.isLoaded) {
                //jump to anchor tag
                window.location.hash = anchorId;
            }//end if (isLoaded)
        }//end if (tabId)
	}//end function gotoAnchorTag


	// doFormSubmit:
	//		A registry to make contextual calling/searching easier.
	// description:
	//		Objects of this class keep list of arrays in the form [name, check,
	//		wrap, directReturn] that are used to determine what the contextual
	//		result of a set of checked arguments is. All check/wrap functions
	//		in this registry should be of the same arity.
newco.jsutils.doIfrFormSubmit = function(/*id of the Iframe*/ iframeId, /*id of the form*/ formId ){
		if( newco.jsutils.isExist(iframeId) ){
			if($(iframeId).contentWindow.document.getElementById(formId)){
				$(iframeId).contentWindow.document.getElementById(formId).submit();
			}
	 	}
	}


newco.jsutils.doIfrModalMessage = function(/*id of the Iframe*/ iframeId, /*pass or fail state*/currentState, /*the display msg*/theMsg )
{
		//alert('doIfrModalMessage()enter iframeid=' + iframeid);	
		
		if( newco.jsutils.isExistDojo(iframeId) )
		{
				$(iframeId).contentWindow.showCommonMsg(currentState, theMsg );
	 	}
	 	
		//alert('doIfrModalMessage()exit iframeid=' + iframeid);	
	 	
}

newco.jsutils.getSelectedIfm = function(){
	return _commonSOMgr.widgetId+'myIframe';
}


	// filterSubStatus:
	//		A registry to make contextual calling/searching easier.

	// description:
	//		Objects of this class keep list of arrays in the form [name, check,
	//		wrap, directReturn] that are used to determine what the contextual
	//		result of a set of checked arguments is. All check/wrap functions
	//		in this registry should be of the same arity.
newco.jsutils.doAjaxSubmit = function(/*action url*/ url, /*call back method*/  callBackFunction, pHolder, formId ){
		new CommonAjaxProxy().doPostAction(url, callBackFunction, pHolder, formId);
	}


newco.jsutils.doAjaxURLSubmit = function(/*action url*/ url, /*call back method*/  callBackFunction ){
		_actionProxy = new CommonAjaxProxy();
		_actionProxy.doPostURLParamAction(url, callBackFunction);
	}



newco.jsutils.doAjaxSubmit2 = function(/*action url*/url, /*call back method*/callBackFunction ){
				new Ajax.Request(url, {
					  method: 'get',
					  onSuccess: callBackFunction
				});
}

/*
 * This function will invoke the remove() on a html select list.
 * Elements will be cleared. Items are removed in ***REVERSE*** order
 * @param listId is the id attribute
 * @param keepFirstIndex is a boolean if you want to keep the first option (i.e. header value)
 */
newco.jsutils.clearSelectList = function(/*id attribute of list*/ listId, /*boolean*/keepFirstIndex){
	var theSelectList = $(listId);
	var firstIndex = null;
	if(keepFirstIndex == true || keepFirstIndex == null){
		firstIndex = 1;
	} else{
		firstIndex = 0;
	}
	if(theSelectList != null && theSelectList.options.length >firstIndex){
		for(i = theSelectList.options.length; i >= firstIndex; i--){
			theSelectList.remove(i);
		}
	}
}

newco.jsutils.loadSkillsAndCategories2 = function(elementId,formId, useAlt){
///*action url*/ url, /*call back method*/  callBackFunction, pHolder, formId
	var selectElement = document.getElementById(elementId);
	if(selectElement !=null && selectElement.selectedIndex >0 ){
		var url = "";
		if(useAlt != null && useAlt == true){
			 url = newco.jsutils.getContext() + "/"+ formId +
			"_loadSkillsAndCategories.action?mainServiceCategoryId=" + selectElement.options[selectElement.selectedIndex].value;

		}else{
		  url = newco.jsutils.getContext() + "/"+ formId +
			"_loadSkillsAndCategories.action?theCriteria.mainServiceCategoryId=" + selectElement.options[selectElement.selectedIndex].value;
		}
		selectElement.headerValue = "Loading...";
		newco.jsutils.doAjaxURLSubmit(url,newco.jsutils.skillsAndCategoriesAjaxResponse);
	}
}

newco.jsutils.confirmDeleteTask = function (formId,idx) {
	    if (confirm("Are you sure you want to delete task?")) {
	    	var url =  newco.jsutils.getContext() + "/"+ formId +
			"_deleteTask.action?delIndex="+idx;
	        window.location.href=url;
	    }
	}


newco.jsutils.loadResourceSkillsAndCategories = function(elementId,formId){
///*action url*/ url, /*call back method*/  callBackFunction, pHolder, formId
	var selectElement = document.getElementById(elementId);
	if(selectElement !=null && selectElement.selectedIndex >0 ){
		var url =  newco.jsutils.getContext() + "/"+ formId +
			"_loadResourceCredentialsSubCat.action?credentialId=" + selectElement.options[selectElement.selectedIndex].value;
		newco.jsutils.doAjaxURLSubmit(url,newco.jsutils.credentialCategoryAjaxResponse);
	}
	else {
		newco.jsutils.clearSelectList('credentialCategory',false);
	}
}

newco.jsutils.loadCompanySkillsAndCategories = function(elementId,formId){
///*action url*/ url, /*call back method*/  callBackFunction, pHolder, formId
	var selectElement = document.getElementById(elementId);
	if(selectElement !=null && selectElement.selectedIndex >0 ){
		var url =  newco.jsutils.getContext() + "/"+ formId +
			"_loadResourceCredentialsSubCat.action?credentialId=" + selectElement.options[selectElement.selectedIndex].value;
		newco.jsutils.doAjaxURLSubmit(url,newco.jsutils.credentialCategoryAjaxResponse2);
	}
	else {
		newco.jsutils.clearSelectList('credentialCategory2',false);
	}
}

newco.jsutils.skillsAndCategoriesAjaxResponse = function(xml){
	var mainServiceCategory = $('mainServiceCategory');
	mainServiceCategory.disabled = true;
	var categorySelectList = $( 'categorySelection_0');
	var skillSelectList = $('skillSelection_0');
	categorySelectList.disabled = false;
	skillSelectList.disabled = false;
	rootElement = xml.documentElement;

	//remove old options from categoryList
	newco.jsutils.clearSelectList('categorySelection_0', true);
	//remove old options from categoryList
	newco.jsutils.clearSelectList('skillSelection_0', true);

	var optionElement = null;//document.createElement('option');

	var skillList = xml.getElementsByTagName('skill');
	var categoryList = xml.getElementsByTagName('cat');
	for(i=0; i<skillList.length;i++){
		id = skillList[i].childNodes[0].firstChild.nodeValue;
		text = skillList[i].childNodes[1].firstChild.nodeValue;
		optionElement = document.createElement('option');
		optionElement.value = id;
		optionElement.text = text;
		try{
			skillSelectList.add(optionElement, null);
		} catch(ex){
			skillSelectList.add(optionElement); //IE
		}
	}

	for(i=0; i<categorySelectList.length; i++){
		id = categoryList[i].childNodes[0].firstChild.nodeValue;
		text = categoryList[i].childNodes[1].firstChild.nodeValue;
		optionElement = document.createElement('option');
		optionElement.value = id;
		optionElement.text = text;
		try{
		categorySelectList.add(optionElement,null);
		} catch(ex){
			categorySelectList.add(optionElement); //IE
		}
	}
}

newco.jsutils.loadSubcategories2 = function loadSubcategories(elementId,hiddenElement,formId, taskIndex, useAlt){

	var selectElement = $(elementId);
	var hiddenSelectId = $(hiddenElement);
	if(selectElement !=null && selectElement.selectedIndex != -1 && hiddenSelectId != null){
		hiddenSelectId.value = selectElement.options[selectElement.selectedIndex].value;
		var url = "";
		if(useAlt != null && useAlt == true){
			url = newco.jsutils.getContext() + "/"+ formId + "_loadSubcategories.action?selectedCategoryId=" +
			selectElement.options[selectElement.selectedIndex].value +
			"&taskIndex=" + taskIndex;
		}else{
		 url = newco.jsutils.getContext() + "/"+ formId + "_loadSubcategories.action?theCriteria.selectedCategoryId=" +
			selectElement.options[selectElement.selectedIndex].value +
			"&taskIndex=" + taskIndex;
		}
		newco.jsutils.doAjaxURLSubmit(url,newco.jsutils.subCategoriesAjaxResponse2);

	}
}

newco.jsutils.subCategoriesAjaxResponse2 = function(xml){
	rootElement = xml.documentElement;
	var selectedCategoryId = rootElement.childNodes[0].childNodes[0].nodeValue;
	var taskIndex = rootElement.childNodes[1].childNodes[0].nodeValue;
	var node = rootElement.childNodes[0].nodeName;

	var subCategorySelectList = document.getElementById( 'subCategorySelection_'+taskIndex);
	//remove old options from subCategorySelect
	newco.jsutils.clearSelectList('subCategorySelection_'+taskIndex, false);

	var optionElement = null;//document.createElement('option');
	var subcategoryList = xml.getElementsByTagName('subcat');

	if(subcategoryList != null && subCategorySelectList != null){

		if(subcategoryList.length < 1){
			newco.jsutils.clearSelectList('subCategorySelection_'+taskIndex, false);
			optionElement = document.createElement('option');
			optionElement.value = -1;
			optionElement.text = 'No Sub-Categories Available';
				try{
					subCategorySelectList.add(optionElement, null);
				} catch(ex){
					subCategorySelectList.add(optionElement); //IE
				}
			subCategorySelectList.disabled = true;
		}
		for(i=0; i<subcategoryList.length;i++){
			id = subcategoryList[i].childNodes[0].firstChild.nodeValue;
			text = subcategoryList[i].childNodes[1].firstChild.nodeValue;
			optionElement = document.createElement('option');
			optionElement.value = id;
			optionElement.text = text;
			try{
				subCategorySelectList.add(optionElement, null);
			} catch(ex){
				subCategorySelectList.add(optionElement); //IE
			}
		}
		if(subcategoryList.length > 0){
			subCategorySelectList.disabled = false;
		}
	}

}

newco.jsutils.subCategoriesAjaxResponse = function(xml){
	rootElement = xml.documentElement;
	var selectedCategoryId = rootElement.childNodes[0].childNodes[0].nodeValue;
	var taskIndex = rootElement.childNodes[1].childNodes[0].nodeValue;
	var skillSelectList = document.getElementById('skillSelection_'+taskIndex);
	skillSelectList.disabled = false;
	//remove old options from subCategorySelect
	newco.jsutils.clearSelectList('skillSelection_'+taskIndex, false);

	var subCategorySelectList = document.getElementById( 'subCategorySelection_'+taskIndex);
	subCategorySelectList.disabled = false;
	//remove old options from subCategorySelect
	newco.jsutils.clearSelectList('subCategorySelection_'+taskIndex, false);

	var skillList = xml.getElementsByTagName('skill');
	var optionElement = null;//document.createElement('option');
	for(i=0; i<skillList.length;i++){

		id = skillList[i].childNodes[0].firstChild.nodeValue;
		text = skillList[i].childNodes[1].firstChild.nodeValue;
		optionElement = document.createElement('option');
		optionElement.value = id;
		optionElement.text = text;
		try{
			skillSelectList.add(optionElement, null);
		} catch(ex){
			skillSelectList.add(optionElement); //IE
		}
	}

	optionElement = null;//document.createElement('option');
	var subcategoryList = xml.getElementsByTagName('subcat');

	if(subcategoryList != null && subCategorySelectList != null){

		if(subcategoryList.length < 1){
			newco.jsutils.clearSelectList('subCategorySelection_'+taskIndex, false);
			optionElement = document.createElement('option');
			optionElement.value = -1;
			optionElement.text = 'No Sub-Categories Available';
				try{
					subCategorySelectList.add(optionElement, null);
				} catch(ex){
					subCategorySelectList.add(optionElement); //IE
				}
				subCategorySelectList.options[0].selected = true;
			//subCategorySelectList.disabled = true;
		}
		for(i=0; i<subcategoryList.length;i++){
			id = subcategoryList[i].childNodes[0].firstChild.nodeValue;
			text = subcategoryList[i].childNodes[1].firstChild.nodeValue;
			optionElement = document.createElement('option');
			optionElement.value = id;
			optionElement.text = text;
			try{
				subCategorySelectList.add(optionElement, null);
			} catch(ex){
				subCategorySelectList.add(optionElement); //IE
			}
		}
		if(subcategoryList.length > 0){
			subCategorySelectList.disabled = false;
		}
	}

}

newco.jsutils.subCategoriesSkillsAjaxResponse =function(xml){
	rootElement = xml.documentElement;
	var selectedCategoryId = rootElement.childNodes[0].childNodes[0].nodeValue;
	var taskIndex = rootElement.childNodes[1].childNodes[0].nodeValue;
	var skillSelectList = document.getElementById( 'skillSelection_'+taskIndex);
	skillSelectList.disabled = false;
	//remove old options from subCategorySelect
	newco.jsutils.clearSelectList('skillSelection_'+taskIndex, false);

	var skillList = xml.getElementsByTagName('skill');
	var optionElement = null;//document.createElement('option');
	for(i=0; i<skillList.length;i++){

		id = skillList[i].childNodes[0].firstChild.nodeValue;
		text = skillList[i].childNodes[1].firstChild.nodeValue;
		optionElement = document.createElement('option');
		optionElement.value = id;
		optionElement.text = text;
		try{
			skillSelectList.add(optionElement, null);
		} catch(ex){
			skillSelectList.add(optionElement); //IE
		}
	}

}
newco.jsutils.credentialCategoryAjaxResponse = function(xml){
	rootElement = xml.documentElement;
	var categorySelectList = document.getElementById( 'credentialCategory');
	var optionElement = null;//document.createElement('option');
	var credCategoryList = xml.getElementsByTagName('credCategory');
	var selectedCredCat =  document.getElementById( 'selectedCredCat');
	
	newco.jsutils.clearSelectList('credentialCategory', false);

	if(credCategoryList != null){
		for(i=0;i<credCategoryList.length;i++){
			id = credCategoryList[i].childNodes[0].firstChild.nodeValue;
			text = credCategoryList[i].childNodes[1].firstChild.nodeValue;
			optionElement = document.createElement('option');
			optionElement.value = id;
			optionElement.text = text;
			try{
				categorySelectList.add(optionElement, null);
			} catch(ex){
				categorySelectList.add(optionElement); //IE
			}		
			if(selectedCredCat!=null && id==selectedCredCat.value){
				categorySelectList[i].selected=true;
			}				
		}
	}
}

newco.jsutils.credentialCategoryAjaxResponse2 = function(xml){
	rootElement = xml.documentElement;
	var categorySelectList = document.getElementById( 'credentialCategory2');
	var optionElement = null;//document.createElement('option');
	var credCategoryList = xml.getElementsByTagName('credCategory');

	newco.jsutils.clearSelectList('credentialCategory2', false);

	if(credCategoryList != null){
		for(i=0;i<credCategoryList.length;i++){
			id = credCategoryList[i].childNodes[0].firstChild.nodeValue;
			text = credCategoryList[i].childNodes[1].firstChild.nodeValue;
			optionElement = document.createElement('option');
			optionElement.value = id;
			optionElement.text = text;
			try{
				categorySelectList.add(optionElement, null);
			} catch(ex){
				categorySelectList.add(optionElement); //IE
			}
		}
	}
}

newco.jsutils.commonPopulateSelectListAX = function(xml){
	rootElement = xml.documentElement;
	var mainSelectList = xml.getElementsByTagName('select_set');
	newco.jsutils.clearSelectList('some_name', false);
	if(mainSelectList != null){
		var attr = mainSelectList[0].getAttribute('selectName', 0)
		if(attr) {
			newco.jsutils.clearSelectList(attr, false);
		}
		for(i=0;i<mainSelectList.length;i++){
			id = mainSelectList[i].childNodes[0].firstChild.nodeValue;
			text = mainSelectList[i].childNodes[1].firstChild.nodeValue;
			optionElement = document.createElement('option');
			optionElement.value = id;
			optionElement.text = text;
			try{
				$(attr).add(optionElement, null);
			} catch(ex){
				$(attr).add(optionElement); //IE
			}
		}
	}
}

newco.jsutils.commonPopulateSubSelectListAX = function(xml, selId){
	rootElement = xml.documentElement;
	var mainSubSelectList = xml.getElementsByTagName('ss_item');
	
	if(mainSubSelectList != null)
	{
		//alert('selId=' + selId + '\nlength=' + mainSubSelectList.length);
		newco.jsutils.clearSelectList(selId, false);
		for(i=0;i<mainSubSelectList.length;i++)
		{
			text = mainSubSelectList[i].childNodes[0].firstChild.nodeValue;
			id = mainSubSelectList[i].childNodes[1].firstChild.nodeValue;
			
			// We are experiencing duplicate elements with the same id that are not easy to spot/locate/see.
			// Need to loop thru explicitly to find our elements.
			var doc = document.getElementsByTagName('select');
			for (var j = 0; j < doc.length; j=j+1)
			{
				if(doc[j].id == selId.toString())
				{
					optionElement = document.createElement('option');
					optionElement.value = id;
					optionElement.text = text;
														
					// Handle IE vs non-IE browsers
					if (!dojo.isIE)					
					{
						//alert('Not IE: adding one ' + text);
						doc[j].appendChild(optionElement, null);										
					}
					else
					{
						//alert('IE: adding one' + text);
						doc[j].add(optionElement); //IE										
					}					
	   			}
			}               	           						
		}
		if(document.getElementById(selId)){
			var slElement = document.getElementById(selId);
			if(slElement.options.length == 1){
				slElement.options[0].selected=true;
			}
		}		
	}
}

newco.jsutils.setSelectKeyAndValueInContainer = function(selectId, containerId, type) {
	if(newco.jsutils.isExist(selectId) && newco.jsutils.isExist(containerId)) {
		this.selectKey = $(selectId).options[$(selectId).selectedIndex].text;
		this.selectVal = $(selectId).value
		newco.jsutils.Uvd('currentKey',this.selectKey);
		newco.jsutils.Uvd('currentVal',this.selectVal);
		newco.jsutils.Uvd('selectType',type);
	}
}

	// filterSubStatus:
	//		A registry to make contextual calling/searching easier.
	// description:
	//		Objects of this class keep list of arrays in the form [name, check,
	//		wrap, directReturn] that are used to determine what the contextual
	//		result of a set of checked arguments is. All check/wrap functions
	//		in this registry should be of the same arity.
newco.jsutils.calcSpendLimit = function(){
		theTab = _commonSOMgr.widgetId;		
		if (newco.jsutils.isExist('increaseLimit'+theTab) && $('increaseLimit'+theTab).value == ""){
			if(newco.jsutils.isExist('increaseSPendLimitResponseMessage'+theTab))
            	$('increaseSPendLimitResponseMessage'+theTab).innerHTML = "Enter Maximum Price for Labor";
            return false;
       	}
       	else if (newco.jsutils.isExist('increaseLimitParts'+theTab) && $('increaseLimitParts'+theTab).value == ""){
			if(newco.jsutils.isExist('increaseSPendLimitResponseMessage'+theTab))
            	$('increaseSPendLimitResponseMessage'+theTab).innerHTML = "Enter Maximum Price for Materials";
            return false;
       	}
       	else if ($('increaseLimit'+theTab).value < 0){
       		if(newco.jsutils.isExist('increaseSPendLimitResponseMessage'+theTab))
            	$('increaseSPendLimitResponseMessage'+theTab).innerHTML = "Please enter positive Maximum Price for Labor";
           	return false;
        }
        else if ($('increaseLimitParts'+theTab).value < 0){
       		if(newco.jsutils.isExist('increaseSPendLimitResponseMessage'+theTab))
            	$('increaseSPendLimitResponseMessage'+theTab).innerHTML = "Please enter positive Maximum Price for Materials";
           	return false;
        }
        else if (isNaN($('increaseLimit'+theTab).value) || isNaN($('increaseLimitParts'+theTab).value)){
        	if(newco.jsutils.isExist('increaseSPendLimitResponseMessage'+theTab))
            	$('increaseSPendLimitResponseMessage'+theTab).innerHTML = "Please enter amount in decimal format";
            	return false;
        }
        else{
         	if(newco.jsutils.isExist('increaseSPendLimitResponseMessage'+theTab))
            	$('increaseSPendLimitResponseMessage'+theTab).innerHTML = "";
        }
		var totalAmt = 0.0;
		var totalAmtParts = 0.0;
		
		var increasedSpendLimitComment ="";
		//totalAmt = parseFloat(newco.jsutils.removeFormatting($('currentSpendLimit').value)) + parseFloat($('increaseLimit'+theTab).value);
		totalAmt = parseFloat($('increaseLimit'+theTab).value);
		totalAmtParts = parseFloat($('increaseLimitParts'+theTab).value);				
		var currentLabor=parseFloat($('currentLimitLabor'+theTab).value);
		var currentParts=parseFloat($('currentLimitParts'+theTab).value);
		
		$('currentLimitLabor').value=$('currentLimitLabor'+theTab).value;
		$('currentLimitParts').value=$('currentLimitParts'+theTab).value;
		$('totalSpendLimit').value=totalAmt;
		$('totalSpendLimitParts').value=totalAmtParts;
		var totalAmtFinal = totalAmt + totalAmtParts;  
		var currentFinal = currentLabor+currentParts;
		var  increasedPrice  = totalAmtFinal - currentFinal;
	   
	        
		if(totalAmtFinal<currentFinal || totalAmtFinal == currentFinal){			
			$('increaseSPendLimitResponseMessage'+theTab).innerHTML = "New Maximum Price must be greater than the current Maximum Price";
			return false;
		}
     	
		if(newco.jsutils.isExist('totalAmt'+theTab)){
			   $('totalAmt'+theTab).innerHTML=formatCurrency(totalAmt+totalAmtParts);
		}

		
		/*
		if(newco.jsutils.isExist('totalAmtParts'+theTab)){
			   $('totalAmtParts'+theTab).innerHTML=formatCurrency(totalAmtParts);
		}
		*/		
		return true;
	}

newco.jsutils.isTransactionPriceAboveMaxSpendLimit = function(buyerMaxSpendLimit, oldTotalPrice,newTotalPrice){
	var success = true;
	var increasedPrice = 0.0;
	
	if(!isNaN(newTotalPrice)){		
         increasedPrice  = newTotalPrice - oldTotalPrice;         
    }
	if(buyerMaxSpendLimit != 0 && buyerMaxSpendLimit < increasedPrice){		
    	document.getElementById('increaseSPendLimitResponseMessage').innerHTML = "You have requested a transaction amount that exceeds your limit. Please contact your administrator for further action.";
   		document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
   		document.getElementById('increaseSPendLimitResponseMessage').style.visibility="visible";
    	success = false;
    }
	return success;
}



newco.jsutils.fnHandleRejectSO = function(){
	if(newco.jsutils.isExist('rejectReasonId'+_commonSOMgr.widgetId)){
		index 	= $('rejectReasonId'+_commonSOMgr.widgetId).selectedIndex;
		theVal  = $('rejectReasonId'+_commonSOMgr.widgetId).options[index].value;
		newco.jsutils.Uvd('reasonId',theVal);
	}

}

	// updateWithXmlData:
	//		A registry to make contextual calling/searching easier.
	// description:
	//		Objects of this class keep list of arrays in the form [name, check,
	//		wrap, directReturn] that are used to determine what the contextual
	//		result of a set of checked arguments is. All check/wrap functions
	//		in this registry should be of the same arity.
newco.jsutils.updateWithXmlData = function(/*element you want updated*/ element1, /*root node of xml*/ rootNode, /*xml data*/ xml){
		if( newco.jsutils.isExist(element1) && rootNode != null){
			theData =    xml.getElementsByTagName( rootNode )
			if(theData == null){
				$(element1).innerHTML = "Error occured";
			}

			if(theData[0].childNodes){
				if(theData[0].childNodes[0]){
					if(theData[0].childNodes[0].nodeValue){
						$(element1).innerHTML =
					       xml.getElementsByTagName( rootNode )[0].childNodes[0].nodeValue != null ?
					       xml.getElementsByTagName( rootNode )[0].childNodes[0].nodeValue : "n/a";
					}
				}
			}
	 	}
	}

newco.jsutils.updateOrderExpressMenu = function( /*tab information*/widgetId,
												 /*selected row index*/selectedRowIndex,
												 /*spendLimitLabor*/spendLimitLabor,
												 /*spendLimitParts*/spendLimitParts ){

	  	var currentIframe = $(newco.jsutils.getSelectedIfm()).contentWindow;
		var spendLimitTotal = 0.0;
	  	if(currentIframe){

	  		var dataGrid = "";
	  		if(widgetId == "Today"|| widgetId == "Current"){
				 dataGrid = currentIframe._dataGridListToday[selectedRowIndex];
			}
			if(widgetId == "Draft" || widgetId == "Saved"){
				dataGrid = currentIframe._dataGridListDraft[selectedRowIndex];
			}
			if(widgetId == "Posted"){
				dataGrid = currentIframe._dataGridListPosted[selectedRowIndex];
			}
			if(widgetId == "Accepted"){
				dataGrid = currentIframe._dataGridListAccepted[selectedRowIndex];
			}
			if(widgetId == "Problem"){
				dataGrid = currentIframe._dataGridListProblem[selectedRowIndex];
			}
			if(widgetId == "Inactive"|| widgetId == "History"){
				dataGrid = currentIframe._dataGridListInactive[selectedRowIndex];
			}
			if(widgetId == "Search"){
				dataGrid = currentIframe._dataGridListSearch[selectedRowIndex];
			}
			if(widgetId == "Received"){
				dataGrid = currentIframe._dataGridListReceived[selectedRowIndex];
			}

	  		dataGrid.spendLimitLabor = spendLimitLabor;
	  		dataGrid.spendLimitParts = spendLimitParts;
	  		spendLimitTotal = parseFloat(spendLimitLabor) + parseFloat(spendLimitParts);

	  		dataGrid.spendLimitTotal = formatCurrency(spendLimitTotal);

		  	dojo.byId('spendLimit'+theTab).innerHTML = formatCurrency(spendLimitTotal);
		    if(dojo.byId('currentAmt'+theTab)){
		    	dojo.byId('currentAmt'+theTab).innerHTML = formatCurrency(spendLimitTotal);
		    }

		    if(newco.jsutils.isExist( $('increaseLimit'+theTab))){
		  		$('increaseLimit'+theTab).value = formatMoneyDecimal(spendLimitLabor);
		  		$('currentLimitLabor'+theTab).value = formatMoneyDecimal(spendLimitLabor);
			}
			if(newco.jsutils.isExist( $('increaseLimitParts'+theTab))){
			  	$('increaseLimitParts'+theTab).value = formatMoneyDecimal(spendLimitParts);
		  		$('currentLimitParts'+theTab).value = formatMoneyDecimal(spendLimitParts);
			}

			if(newco.jsutils.isExist('increaseSPendLimitResponseMessage'+theTab)){
            	$('increaseSPendLimitResponseMessage'+theTab).innerHTML = "";
            }

	  	}

}



newco.jsutils.updateTheNode = function(/*element you want updated*/ element1, /*message to use in the node*/ message){
		if( newco.jsutils.isExist(element1) ){
			$(element1).innerHTML = message;
	 	}
	}


// isExist:
	//		A registry to make contextual calling/searching easier.
	// description:
	//		Objects of this class keep list of arrays in the form [name, check,
	//		wrap, directReturn] that are used to determine what the contextual
	//		result of a set of checked arguments is. All check/wrap functions
	//		in this registry should be of the same arity.
newco.jsutils.isExist = function( elementName ){
		if( $(elementName) ){
			return true;
		}else {
			return false;
}}

// isExist:
	//		A registry to make contextual calling/searching easier.
	// description:
	//		Objects of this class keep list of arrays in the form [name, check,
	//		wrap, directReturn] that are used to determine what the contextual
	//		result of a set of checked arguments is. All check/wrap functions
	//		in this registry should be of the same arity.
newco.jsutils.isExistDojo = function( elementName ){
		if(dijit.byId(elementName)){
			return true;
		}else {
			return false;
}}
newco.jsutils.displayModal = function(theId){
	if(dijit.byId(theId)){
		if(dijit.byId(theId).open){
			dijit.byId(theId).hide();
			dijit.byId(theId).show();
		}else{
			dijit.byId(theId).show();
		}}
	return;
}

newco.jsutils.displayModalWithMessage = function(theId, message, displayType){	//alert(theId)

	 if(displayType != null &&
	    displayType == 'SUCCESS' &&
	    newco.jsutils.isExist('messageS')){
	    $('messageS').innerHTML = message;
	    newco.jsutils.displayModal(theId);
	  }else if(displayType != null &&
	    displayType == 'ERROR' &&
	    newco.jsutils.isExist('message')){
	     $('message').innerHTML = message;
	      newco.jsutils.displayModal(theId);
	   }
	return;
}

newco.jsutils.closeModal = function(theId){	//alert(theId)
	if(dijit.byId(theId)){
			dijit.byId(theId).hide();
		}
	return;
}

newco.jsutils.handleCBData = function(data){
	var dataContainer = new Array();

	dataContainer[0] = {pass_fail_status : newco.jsutils.getCommonXMLElement('pass_fail',data),
						resultMessage    : newco.jsutils.getCommonXMLElement('message',data),
						/* optional information. This will be null if the action has not set it*/
						addtional1    	 : newco.jsutils.getCommonXMLElement('addtional_01',data),
						addtional2    	 : newco.jsutils.getCommonXMLElement('addtional_02',data),
						addtional3    	 : newco.jsutils.getCommonXMLElement('addtional_03',data),
						addtional4    	 : newco.jsutils.getCommonXMLElement('addtional_04',data)};

	return dataContainer[0];
}

newco.jsutils.clearNoteData = function(){
	 if(newco.jsutils.isExist( $('subject'+_commonSOMgr.widgetId) )) {
		$('subject'+_commonSOMgr.widgetId).value="";
	 }
	 if(newco.jsutils.isExist( $('message'+_commonSOMgr.widgetId) )){
	   	$('message'+_commonSOMgr.widgetId).value="";
	 }
}

 newco.jsutils.clearSpendLimitData = function(){
	 if(newco.jsutils.isExist( $('currentAmt'+_commonSOMgr.widgetId) )){
		$('currentAmt'+_commonSOMgr.widgetId).value="";
	 }
	if(newco.jsutils.isExist( $('increaseLimit'+_commonSOMgr.widgetId) )){
	  	$('increaseLimit'+_commonSOMgr.widgetId).value="";
	  	$('currentLimitLabor'+_commonSOMgr.widgetId).value="";
	}
	if(newco.jsutils.isExist( $('increaseLimitParts'+_commonSOMgr.widgetId) )){
	  	$('increaseLimitParts'+_commonSOMgr.widgetId).value="";
	  	$('currentLimitParts'+_commonSOMgr.widgetId).value="";
	}
	if(newco.jsutils.isExist( $('increaseSPendLimitResponseMessage'+_commonSOMgr.widgetId) )){
	 	$('increaseSPendLimitResponseMessage'+_commonSOMgr.widgetId).value="";
	}
}

newco.jsutils.clearCancelData = function(){
	 if(newco.jsutils.isExist( $('cancelComment'+_commonSOMgr.widgetId) )){
		$('cancelComment'+_commonSOMgr.widgetId).value="";
	 }
	 if(newco.jsutils.isExist( $('cancelServiceOrderResponseMessage'+_commonSOMgr.widgetId) )){
	   	$('cancelServiceOrderResponseMessage'+_commonSOMgr.widgetId).innerHTML="";
	 }
}

newco.jsutils.retrieveDocuments = function(expressData){
	theURL = newco.jsutils.getContext()+"soDocumentsAndPhotos_getDocumentsWidget.action";
	if(newco.jsutils.isExistDojo('widget_manage_documents_'+_commonSOMgr.widgetId)) {
		newco.jsutils.doAjaxSubmit(theURL, newco.jsutils.retrieveDocumentsCallBack, null, 'formHandler');
	}
}

newco.jsutils.retrieveDocumentsCallBack = function(xml) {

	// remove rows from current div
	dv = $("widget_doc_div_" + _commonSOMgr.widgetId);
	rootElement = xml.documentElement;
	documentsNode = rootElement.childNodes[0];
	var htmlHolder = '';
	dv_btn =  $("widget_doc_btns_div_" + _commonSOMgr.widgetId);
	dv_btn.innerHTML = "";
	selectedSOId = xml.getElementsByTagName('soId')[0].childNodes[0].nodeValue;
	// loop through document nodes

	for (i=0; i<documentsNode.childNodes.length; i++){
	 	documentNode = documentsNode.childNodes[i];
		id = xml.getElementsByTagName('document_id')[i].childNodes[0].nodeValue;
		name = xml.getElementsByTagName('document_name')[i].childNodes[0].nodeValue;
		size = xml.getElementsByTagName('document_size')[i].childNodes[0].nodeValue;
		category=xml.getElementsByTagName("document_category_id")[i].childNodes[0].nodeValue;
		docPath=xml.getElementsByTagName("document_path")[i].childNodes[0].nodeValue;
		try {
		   desc = xml.getElementsByTagName('document_desc')[i].childNodes[0].nodeValue;
		} catch (err) {
		   desc = '';
		}

		displayName = '(' + size + ' Kb) ';
		if (desc != '') {
			displayName = displayName + '- ' + desc;
		}
	
		if(category == 8){		
				postURL='javascript:showVideo('+'\"' + docPath +'\" )';				
		}else{
			postURL=newco.jsutils.getContext()+"SODocumentView?documentSelection="+id+"&soId="+selectedSOId;			
		}	
	    documentParams = {postAction:postURL, fileName:name, desc:displayName};
		htmlHolder = htmlHolder + newco.jsutils.documentHREFCreate(documentParams);
	   
	}
	dv.innerHTML = htmlHolder;

	soIdNode = rootElement.childNodes[1];
	bgImg   = newco.jsutils.getStaticContext()+"/images/btn/manageDocsPhotos.gif";
	var documentParams = {postAction:'javascript:newco.jsutils.submitManageDocsFromSOM(selectedSOId)', backgroundImg:bgImg};
	dv_btn.innerHTML = newco.jsutils.documentManageBtnCreate(documentParams);
  }

  newco.jsutils.submitManageDocsFromSOM = function(soId) {

  	if (parent._commonSOMgr.widgetId == 'Draft' || parent._commonSOMgr.widgetId == 'Saved'){
		postURL = newco.jsutils.getContext()+"soWizardController.action?soId=" + soId + "&action=edit&tab=draft";
		}
	else
		{
		postURL = newco.jsutils.getContext()+"monitor/soDetailsController.action?soId=" + soId ;
		}
		newco.jsutils.doFormSubmit(postURL,'soDocumentsAndPhotos');

  }

  newco.jsutils.jumpToAnchor = function(theTab, theAnchor){
  	var timeoutPeriod = 1000;
  	if(navigator.appName == "Microsoft Internet Explorer")
	{
 		timeoutPeriod = 3000;
	}

  	 if (theTab != "" && theAnchor != "" ){
    	setTimeout("newco.jsutils.gotoAnchorTag('" + theTab + "','" + theAnchor + "')", timeoutPeriod);
     }
  }


  	/**************************************************************
	 Common method -- getBaseTableRow
	 Generates an inital table row

	 firstTime - Boolean value to determine if a
	 			 Table Row or a Table Data element is created
	****************************************************************/
	newco.jsutils.getBaseTableRow = function ( tableId ) {
		if(newco.jsutils.isExist(tableId)){
			var tblBody;
				tblBody	= $(tableId);
				this.tr = tblBody.insertRow(tblBody.rows.length);
			return this.tr;
		}
	}

	newco.jsutils.getNewCell = function( size, row ) {
		var td		= row.insertCell(row.cells.length);
		return td;
	}


	newco.jsutils.clearTableData = function( tableId ) {
		var tblBody	= $(tableId);
		for(var i = 0; i<tblBody.rows.length; i++){
			tblBody.deleteRow(i);
		}
	}




	/**************************************************************
	Common method --documentHREFCreate method
	****************************************************************/
	newco.jsutils.documentHREFCreate = function( params ) {
				var theTemp = "<p><a href='#{postAction}'>#{fileName}&nbsp;#{desc}</a></p>";
				return this.htmlGen( new Template(theTemp), params );
		}


	/**************************************************************
	Common method --documentManageBtnCreate method
	****************************************************************/
	newco.jsutils.documentManageBtnCreate = function( params ) {
		var theTemp = "	<a href='#{postAction}'>" +
		              "<img src='"+newco.jsutils.getStaticContext()+"/images/spacer.gif' width='140' height='28' " +
		              "style='background-image: url(#{backgroundImg});' " +
		              "class='btn27'/></a>";
		return this.htmlGen( new Template(theTemp), params )
	}
	/**************************************************************
	Common method -- htmlGen method
	This method converts templates into offscreen elements. See
	Template.class in the prototype.js lib.

	template -  Template.class from the prototype lib. This object
				is used for commonly reused html elements
	params   -  object array of parameters for a given template

	****************************************************************/
	newco.jsutils.htmlGen= function ( template, params ) {
		return template.evaluate( params );
	}


newco.jsutils.displayActionTileModal = function(theTab, theState, theMsg){
	isProcessing = false
	if(theTab == 'Saved') theTab = 'Draft';
	if(theTab == 'History') theTab = 'Inactive';
	if(theTab == 'Today') theTab = 'Current';
	if(theState == 401)	{
		thePDijit = 'widgetProcessing'+theTab;
		if(dijit.byId(thePDijit)){
			if(newco.jsutils.isExist('messageReq'+theTab)){
					$('messageReq'+theTab).innerHTML = theMsg
			}
			if(dijit.byId(thePDijit) && dijit.byId(thePDijit).open){
					dijit.byId(thePDijit).hide();
					dijit.byId(thePDijit).show();
					isProcessing = true;
				}else{
				dijit.byId(thePDijit).show();
				isProcessing = true;
			}
			setTimeout("dijit.byId(thePDijit).hide()",20000);
		}
	}
	else if(theState == 0){
		theCommonDijit = 'widgetActionError'+theTab;
		if(dijit.byId(theCommonDijit)){
				if(newco.jsutils.isExist('message'+theTab)){
					$('message'+theTab).innerHTML = theMsg
				}
				processingModal = 'widgetProcessing'+theTab;
				if(dijit.byId(processingModal) && dijit.byId(processingModal).open == true){
					dijit.byId(processingModal).hide();
				}
				if(dijit.byId(theCommonDijit).open = true){
					dijit.byId(theCommonDijit).hide();
					dijit.byId(theCommonDijit).show();
				}else{
					dijit.byId(theCommonDijit).toggle();
				}
				setTimeout("dijit.byId(theCommonDijit).hide()",_commonSOMgr.default_system_time);
		}
	}
	else{
		if(dijit.byId('widgetActionSuccess'+theTab)){
			theCommonDijit 	= 'widgetActionSuccess'+theTab;
			processingModal = 'widgetProcessing'+theTab;
			if(newco.jsutils.isExist('messageS'+theTab)){
					$('messageS'+theTab).innerHTML = theMsg
			}
			if(isProcessing){
					dijit.byId('widgetProcessing'+theTab).hide();
					isProcessing = false;
			}
			if(dijit.byId(theCommonDijit).open = true){
					dijit.byId(theCommonDijit).hide();
					dijit.byId(theCommonDijit).show();
			}else{
				dijit.byId(theCommonDijit).show();
			}
			setTimeout("dijit.byId(theCommonDijit).hide()",7000);
		}
		return;
	}}

newco.jsutils.doModalCheck = function(theCommonDijit,processingModal ){
		if(dijit.byId(processingModal) && dijit.byId(processingModal).open){
				dijit.byId(processingModal).hide();
			}
			if(dijit.byId(theCommonDijit).open = true){
					dijit.byId(theCommonDijit).hide();
					dijit.byId(theCommonDijit).show();
			}
			else{
				dijit.byId(theCommonDijit).toggle();
}}
// setRowStateInfo:
	//		A registry to make contextual calling/searching easier.
	// description:
	//		Objects of this class keep list of arrays in the form [name, check,
	//		wrap, directReturn] that are used to determine what the contextual
	//		result of a set of checked arguments is. All check/wrap functions
	//		in this registry should be of the same arity.
newco.jsutils.setRowStateInfo = function( selectedRowIndex, soIdValue, resId, groupInd, groupId ){
	 theTab = _commonSOMgr.widgetId;
	 if(soIdValue != null && soIdValue != "")
	 {
	 	newco.jsutils.Uvd('soID',soIdValue);
	 }
	 if (resId) {
		  newco.jsutils.Uvd('resId',resId);
	 }
	 if(selectedRowIndex != null){
	 	newco.jsutils.Uvd('selectedRowIndex',selectedRowIndex);
	 }
	 if (groupInd) {
	 	newco.jsutils.Uvd('groupInd', 'true');
	 	newco.jsutils.Uvd('groupId', groupId);
	 } else {
	 	newco.jsutils.Uvd('groupInd', 'false');
	 	newco.jsutils.Uvd('groupId', '');
	 }

	//newco.jsutils.updateTheNode('currentAmt'+theTab,spendLmt);
	 if(dijit.byId('widget_oem_'+theTab)){
	 	if(dijit.byId('widget_oem_'+theTab).open == false)
	 		 	dijit.byId('widget_oem_'+theTab).toggle();
	}
}

newco.jsutils.clearWidgetsdata = function(){
 theTab = _commonSOMgr.widgetId;
	 newco.jsutils.Uvd('soID',null);
	 newco.jsutils.Uvd('resId',null);
	 newco.jsutils.Uvd('reasonId',null);
	 newco.jsutils.Uvd('selectedRowIndex',null);

	 newco.jsutils.Uvd('currentSpendLimit',null);
	 newco.jsutils.Uvd('increaseLimit'+theTab,null);
	 newco.jsutils.Uvd('increaseLimitParts'+theTab,null);
	 newco.jsutils.Uvd('currentLimitLabor'+theTab,null);
	 newco.jsutils.Uvd('currentLimitParts'+theTab,null);
	 newco.jsutils.Uvd('increasedSpendLimitComment'+theTab,'');
	 if(dojo.byId('currentAmt'+theTab))
	 {
	 	dojo.byId('currentAmt'+theTab).innerHTML = "";
	 }
	 if(dojo.byId('totalAmt'+theTab))
	 {
	 	dojo.byId('totalAmt'+theTab).innerHTML = "";
	 }
	 if(dojo.byId('increaseSPendLimitResponseMessage'+theTab))
	 {
	 	dojo.byId('increaseSPendLimitResponseMessage'+theTab).innerHTML = "";
	 }
	 if(dojo.byId('rejectServiceOrderResponseMessage'+theTab))
	 {
	 	dojo.byId('rejectServiceOrderResponseMessage'+theTab).innerHTML = "";
	 }
	 if(dojo.byId('soid'+theTab))
	 {
	 dojo.byId('soid'+theTab).innerHTML = "";
	 }
	 if(dojo.byId('title'+theTab))
	 {
	 dojo.byId('title'+theTab).innerHTML= "";
	 }
	 if(dojo.byId('spendLimit'+theTab))
	 {
	 dojo.byId('spendLimit'+theTab).innerHTML= "";
	 }
	 if(dojo.byId('totalFinalPrice'+theTab))
	 {
     dojo.byId('totalFinalPrice'+theTab).innerHTML= "";
	 }
	 if(dojo.byId('priceRangeDiv'+theTab))
	 {
     dojo.byId('priceRangeDiv'+theTab).innerHTML= "";
	 }
	 if(dojo.byId('buyer'+theTab))
	 {
	 dojo.byId('buyer'+theTab).innerHTML= "";
	 }
	 if(dojo.byId('endCustomer'+theTab))
	 {
	 dojo.byId('endCustomer'+theTab).innerHTML= "n/a";
	 }
	 if(dojo.byId('location'+theTab))
	 {
	 dojo.byId('location'+theTab).innerHTML= "";
	 }
	 if(dojo.byId('status'+theTab))
	 {
	 dojo.byId('status'+theTab).innerHTML= "";
	 }
}


newco.jsutils.hideDiv = function(id)
{
	var doc = document.getElementsByTagName('div');
	for (var i = 0; i < doc.length; i=i+1)
	{
		if(doc[i].id.indexOf(id) >= 0)
		{
	   		doc[i].style.display = "none";
	   	}
	}
}

newco.jsutils.hideDivInParent = function(id)
{
	var doc = parent.document.getElementsByTagName('div');
	for (var i = 0; i < doc.length; i=i+1)
	{
		if(doc[i].id.indexOf(id) >= 0)
		{
	   		doc[i].style.display = "none";
	   	}
	}
}
function showDivInParent(id)
{
	var doc = parent.document.getElementsByTagName('div');
	for (var i = 0; i < doc.length; i=i+1)
	{
		if(doc[i].id.indexOf(id) >= 0)
		{
	   		doc[i].style.display = "block";
	   	}
	}
}

newco.jsutils.clearRightSideMenus = function(){
	showDivInParent('rightMenu_');

	// Hide All Buttons, initially
	newco.jsutils.hideDivInParent('buttonSOM_');
	newco.jsutils.hideDivInParent('widgetSOM_');
}

newco.jsutils.displayRightSideMenu = function(status, providerResponseId, groupInd, groupId, powerBuyerInd, incSpendLimitInd, taskLevelPriceInd,viewOrderPricing,nonFundedInd)
{
	//TODO use statusId and compare with numbers/constants
	//alert('displayRightSideMenu() ' + " status: " + status + " theRole:" + theRole + " groupId:" + groupId + " providerResponseId:" + providerResponseId + " groupInd:" + groupInd + " powerBuyerInd:" + powerBuyerInd);

	showDivInParent('rightMenu_');

	if(groupInd == null)
	{
		//alert("groupInd was null. changed to 'false'");
		groupInd = false;
		groupId = '';
	}

	if(groupInd == undefined)
	{
		//alert("groupInd was undefined. changed to 'false'");
		groupInd = false;
		groupId = '';
	}

	// Hide All Buttons, initially
	newco.jsutils.hideDivInParent('buttonSOM_');
	newco.jsutils.hideDivInParent('widgetSOM_');

	if(lastOpenDivIndex == -1 && groupInd == false) {
		return;
	}
	// Display different widgets based on role (power buyer, buyer, simple buyer,provider,admin), status and orderType (grouped, child, individual)
	if(powerBuyerInd) {
		if (theRole == 3 || theRole == 5  || theRole == 2) {
			showDivInParent('widgetSOM_OrderExpressMenu');
			
			
		}
	} else if((theRole == 2)||(theRole == 3)) {
			
			
			if(((status == 'Pending Cancel') || (status == 165))){
			showDivInParent('widgetSOM_PendingCancel');
			
	}
	
		if(((status == 'Accepted') || (status == 150))){
			if ((groupInd || groupId == '') && (taskLevelPriceInd == "false")) {  // Show at group level or for individual SO, but not for child SO
				if(nonFundedInd=='false')
					{
					showDivInParent('widgetSOM_IncreaseSpendLimit');
					}
			}
			if ((groupInd || groupId == '') && (taskLevelPriceInd == "true") && (incSpendLimitInd == "true")) {  // Show at group level or for individual SO, but not for child SO
				if(nonFundedInd=='false')
				{
					showDivInParent('buttonSOM_IncreaseSpendLimit');
				}
			}
			showDivInParent('widgetSOM_AddNote');
			}
		 else if(((status == 'Active') || (status == 155))) {
			if ((groupInd || groupId == '') && (taskLevelPriceInd == "false")) {  // Show at group level or for individual SO, but not for child SO
				if(nonFundedInd=='false')
				{
				showDivInParent('widgetSOM_IncreaseSpendLimit');
				}
			}
			if ((groupInd || groupId == '') && (taskLevelPriceInd == "true") && (incSpendLimitInd == "true")) {  // Show at group level or for individual SO, but not for child SO
				if(nonFundedInd=='false')
				{
					showDivInParent('buttonSOM_IncreaseSpendLimit');
				}
			}
			showDivInParent('widgetSOM_AddNote'); 
		} else if((status == 'Cancelled') || (status == 120)) {
			showDivInParent('widgetSOM_AddNote');
		} else if((status == 'Closed') || (status == 180)) {
			showDivInParent('widgetSOM_AddNote');
		} else if((status == 'Completed') || (status == 160)) {
			showDivInParent('widgetSOM_AddNote');
		} else if((status == 'Draft') || (status == 100)) {
			if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
				showDivInParent('buttonSOM_DeleteDraft');
			}
			showDivInParent('widgetSOM_AddNote');
		} else if((status == 'Saved') || (status == 100)) {
			if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
				showDivInParent('buttonSOM_DeleteDraft');
			}
			showDivInParent('widgetSOM_AddNote');
		} else if((status == 'Expired') || (status == 130)) {
			if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
				showDivInParent('buttonSOM_VoidSO');
			}
			showDivInParent('widgetSOM_AddNote');
		} else if((status == 'Posted') || (status == 110)) { //Conditional is same as Posted for Buyer
			//TODO Un-comment increase spend limit block below later when email notification problem is fixed.
			/*if (groupInd || groupId == '') {  // Show at group level or for individual SO, but not for child SO
				showDivInParent('widgetSOM_IncreaseSpendLimit');
			}*/
			if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
				showDivInParent('buttonSOM_VoidSO');
			}
			showDivInParent('widgetSOM_AddNote');
		} else if(((status == 'Problem') || (status == 170))) {
			if ((groupInd || groupId == '') && (taskLevelPriceInd == "false")) {  // Show at group level or for individual SO, but not for child SO
				if(nonFundedInd=='false')
				{
				showDivInParent('widgetSOM_IncreaseSpendLimit');
				}
			}
			if ((groupInd || groupId == '') && (taskLevelPriceInd == "true") && (incSpendLimitInd == "true")) {  // Show at group level or for individual SO, but not for child SO
				if(nonFundedInd=='false')
				{
					showDivInParent('buttonSOM_IncreaseSpendLimit');
				}
			}			
			showDivInParent('widgetSOM_AddNote');
		} else if((status == 'Voided') || (status == 125)) {
			showDivInParent('widgetSOM_AddNote');
		}else if((status == 'Deleted') || (status == 105)) {
			showDivInParent('widgetSOM_AddNote');
		}

		// these show for all states
		showDivInParent('widgetSOM_OrderExpressMenu');
		showDivInParent('widgetSOM_IncidentTracker');
		if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
			showDivInParent('widgetSOM_ManageDocuments');
		}
		//Don't display for Draft
		if(!((status == 'Draft') || (status == 100))) {
			if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
				showDivInParent('buttonSOM_ViewPrintPDF');
			}
		}
		if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
			showDivInParent('buttonSOM_CopySO');
		}
		if(((status == 'Pending Cancel') || (status == 165))){
			newco.jsutils.hideDivInParent('buttonSOM_CopySO');
		}
	} else if(theRole == 5) { // Handle simple buyer
		if((status == 'Accepted') || (status == 150)) {
			if (groupInd || groupId == '') {  // Show at group level or for individual SO, but not for child SO
				showDivInParent('widgetSOM_IncreaseSpendLimit');
			}
			showDivInParent('widgetSOM_AddNote');
		} else if((status == 'Active') || (status == 155)) {
			if (groupInd || groupId == '') {  // Show at group level or for individual SO, but not for child SO
				if(nonFundedInd=='false')
				{
				showDivInParent('widgetSOM_IncreaseSpendLimit');
				}
			}
			showDivInParent('widgetSOM_AddNote');
		} else if((status == 'Cancelled') || (status == 120)) {
			showDivInParent('widgetSOM_AddNote');
		} else if((status == 'Closed') || (status == 180)) {
			showDivInParent('widgetSOM_AddNote');
		} else if((status == 'Completed') || (status == 160)) {
			showDivInParent('widgetSOM_AddNote');
		} else if((status == 'Draft') || (status == 'Saved') || (status == 100)) {
			if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
				showDivInParent('buttonSOM_DeleteDraft');
			}
			showDivInParent('widgetSOM_AddNote');
		} else if((status == 'Expired') || (status == 130)) {
			if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
				showDivInParent('buttonSOM_VoidSO');
			}
			showDivInParent('widgetSOM_AddNote');
		} else if((status == 'Posted') || (status == 110)) { //Conditional is same as Posted for Buyer
			//TODO Un-comment increase spend limit block below later when email notification problem is fixed.
			/*if (groupInd || groupId == '') {  // Show at group level or for individual SO, but not for child SO
				showDivInParent('widgetSOM_IncreaseSpendLimit');
			}*/
			if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
				showDivInParent('buttonSOM_VoidSO');
			}
			showDivInParent('widgetSOM_AddNote');
		} else if((status == 'Problem') || (status == 170)) {
			if (groupInd || groupId == '') {  // Show at group level or for individual SO, but not for child SO
				if(nonFundedInd=='false')
				{
				showDivInParent('widgetSOM_IncreaseSpendLimit');
				}
			}
			showDivInParent('widgetSOM_AddNote');
		} else if((status == 'Voided') || (status == 125)) {
			showDivInParent('widgetSOM_AddNote');
		}else if((status == 'Deleted') || (status == 105)) {
			showDivInParent('widgetSOM_AddNote');
		}

		// these show for all states
		showDivInParent('widgetSOM_OrderExpressMenu');
		if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
			showDivInParent('widgetSOM_ManageDocuments');
		}
		//Don't display for Draft
		if(((status != 'Draft') && (status != 'Saved') && (status != 100))) {
			if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
				showDivInParent('buttonSOM_ViewPrintPDF');
			}
		}
		if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
			showDivInParent('buttonSOM_CopySO');
		}
		if(((status == 'Pending Cancel') || (status == 165))){
			newco.jsutils.hideDivInParent('buttonSOM_CopySO');
		}
	} else if (theRole == 1) { // Handle provider
		// For all states
		showDivInParent('widgetSOM_OrderExpressMenu');
		
		if(status == 'Pending Cancel'||status == 165){
			if(viewOrderPricing=='true'){
				showDivInParent('widgetSOM_PendingCancelProvider');
			}else
				{
				newco.jsutils.hideDivInParent('widgetSOM_PendingCancelProvider');
				}
			
			}

		if((status == 'Accepted') || (status == 150)) {
			showDivInParent('widgetSOM_AddNote');
			if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
				showDivInParent('widgetSOM_ManageDocuments');
				showDivInParent('buttonSOM_ViewPrintPDF');
			}
		} else if((status == 'Active') || (status == 155)) {
			showDivInParent('widgetSOM_AddNote');
			if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
				showDivInParent('widgetSOM_ManageDocuments');
				showDivInParent('buttonSOM_ViewPrintPDF');
			}
		} else if((status == 'Cancelled') || (status == 120)) {
			showDivInParent('widgetSOM_AddNote');
			if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
				showDivInParent('widgetSOM_ManageDocuments');
				showDivInParent('buttonSOM_ViewPrintPDF');
			}
		} else if((status == 'Completed') || (status == 160)) {
			showDivInParent('widgetSOM_AddNote');
			if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
				showDivInParent('widgetSOM_ManageDocuments');
				showDivInParent('buttonSOM_ViewPrintPDF');
			}
		} else if((status == 'Closed') || (status == 180)) {
			showDivInParent('widgetSOM_AddNote');
			if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
				showDivInParent('widgetSOM_ManageDocuments');
				showDivInParent('buttonSOM_ViewPrintPDF');
			}
		} else if((status == 'Problem') || (status == 170)) {
			showDivInParent('widgetSOM_AddNote');
			if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
				showDivInParent('widgetSOM_ManageDocuments');
				showDivInParent('buttonSOM_ViewPrintPDF');
			}
		} else if((status == 'Received') || (status == 110)) {
			//providerResponseId=2 is Conditional Offer
			if(providerResponseId == 2) {
				if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
					showDivInParent('widgetSOM_ManageDocuments');
				}
				if (groupInd || groupId == '') {  // Show at group level or for individual SO, but not for child SO
					showDivInParent('buttonSOM_WithdrawCondAccept');
				}
			} else {
				if (groupInd == false) { // Do not show at group level, show it for an individual and a child SO
					showDivInParent('widgetSOM_ManageDocuments');
				}				
			}
		} else if((status == 'Pending Cancel') || (status == 165)) {
			showDivInParent('buttonSOM_ViewPrintPDF');
		}
	}
}

// displayErrorAlertModel:
	//		A registry to make contextual calling/searching easier.
	// description:
	//		Objects of this class keep list of arrays in the form [name, check,
	//		wrap, directReturn] that are used to determine what the contextual
	//		result of a set of checked arguments is. All check/wrap functions
	//		in this registry should be of the same arity.
newco.jsutils.clearAllActionTiles = function(){
	 // clear all widget data first
	 newco.jsutils.clearWidgetsdata();
	 // now close all active widget panels
	 theTab = _commonSOMgr.widgetId;

	 newco.jsutils.hideDiv('rightMenu_');
	 newco.jsutils.hideDivInParent('rightMenu_');

	 if(dijit.byId('widget_oem_'+theTab))
	 {
	 	if(dijit.byId('widget_oem_'+theTab).open == true)
	 		 	dijit.byId('widget_oem_'+theTab).toggle();
	 }
	 if(dijit.byId('widget_cancel_service_order_'+theTab))
	 {
	 	if(dijit.byId('widget_cancel_service_order_'+theTab).open == true)
	 		 	dijit.byId('widget_cancel_service_order_'+theTab).toggle();
	 }
	 if(dijit.byId('widget_add_note_'+theTab))
	 {
	 	if(dijit.byId('widget_add_note_'+theTab).open == true)
	 		 	dijit.byId('widget_add_note_'+theTab).toggle();
	 }
	 if(dijit.byId('widget_increase_spend_limit_'+theTab))
	 {
	 	if(dijit.byId('widget_increase_spend_limit_'+theTab).open == true)
	 		 	dijit.byId('widget_increase_spend_limit_'+theTab).toggle();
	 }

	//displayRightSideMenuWidgets();
}


newco.jsutils.updateOrderExpress = function(expressData, groupInd, groupInfo, primaryInd, dispatchInd,viewOrderPricing,unassigned){
	//Code added to print (User Id# xxxx) after user name
	 var bEndIndex = expressData.buyer.indexOf('(');
	 var pLnEndIndex = expressData.provider.indexOf(',');
	 var pEndIndex = expressData.provider.indexOf('(');

	 //alert(theTab);
	 dojo.byId('soid'+theTab).innerHTML = groupInd ? groupInfo.soId : expressData.soId;
	 dojo.byId('title'+theTab).innerHTML= groupInd ? groupInfo.theTitle : expressData.theTitle;
	 dojo.byId('spendLimit'+theTab).innerHTML= groupInd ? groupInfo.spendLimitTotal : expressData.spendLimitTotal;
	 
	 if (dojo.byId('spendLimit'+theTab).innerHTML != '0.00'){
		 //SL 15642 Start Changes to hide price in SOM for all tabs
		 if((viewOrderPricing=='true' &&  expressData.roleId == '1') || expressData.roleId == '3')
		 {
			 document.getElementById('spendLimitDiv'+theTab).style.display = 'block';
		 	 dojo.byId('spendLimit'+theTab).style.display = 'block';
		 }
		 else
		 {
			document.getElementById('spendLimitDiv'+theTab).style.display = 'none';
	 		dojo.byId('spendLimit'+theTab).style.display = 'none';
		 }
		 //SL 15642 End Changes to hide price in SOM for all tabs
	 } else {
		document.getElementById('spendLimitDiv'+theTab).style.display = 'none';
 		dojo.byId('spendLimit'+theTab).style.display = 'none';
	 }
	 
	 if((expressData.statusId == '165'))
	 {
	 if((expressData.roleId == '1'))
	 {
	 newco.jsutils.hideDivInParent('widgetSOM_PendingCancelProvider');
	 }
	 if((expressData.roleId == '3'))
	 {
	 newco.jsutils.hideDivInParent('widgetSOM_PendingCancel');
	 }
	 
	 dojo.byId('pendingCancelExpand'+theTab).style.display = 'none';
	 dojo.byId('pendingCancelCollapse'+theTab).style.display = 'block';
     dojo.byId('frmPendingCancel'+theTab).style.display = 'block';
	 dojo.byId('cancellationDiv'+theTab).style.display = 'block';
	 dojo.byId('amountCancelled'+theTab).style.display = 'block';
	 dojo.byId('errorMessage'+theTab).style.display = 'none';
	 dojo.byId('pendingCancelHistoryIcon'+theTab).style.display = 'block';
	 dojo.byId('spendLimitDiv'+theTab).style.display = 'none';
	 dojo.byId('spendLimit'+theTab).style.display = 'none';
	 	
	 
	 //provider view
	 if((expressData.roleId == '1') &&(viewOrderPricing=='true'))
	 {
	  window.scrollTo(1,100);
	dojo.byId('firstView'+theTab).style.display = 'none'; 
	  dojo.byId('providerDecision'+theTab).style.display = 'none';
	  dojo.byId('providerDecision_pend_resp'+theTab).style.display = 'none';
	  dojo.byId('newRequest'+theTab).style.display = 'none';
	 dojo.byId('agreeSubmit'+theTab).style.display = 'none';
	 dojo.byId('providerDisagree'+theTab).style.display = 'none';
	 
	 	 dojo.byId('agreeSubmit'+theTab).style.display = 'none';
	     dojo.byId('reportAproblem'+theTab).style.display = 'none';
	     dojo.byId('disagreeSubmit'+theTab).style.display = 'none';
	     dojo.byId('withdrawRequest'+theTab).style.display = 'none';
	     
	 
	 document.getElementById('serviceOrderId'+theTab).value = expressData.soId;
	 
	 
	 var soId=	expressData.soId;	
	 
	
	 
	 if((expressData. subStatus != '68')&& (expressData. subStatus != '69'))
	 {
	 
	 newco.jsutils.doAjaxURLSubmit('serviceOrderPendingInfo_getPendingCancelInfo.action?servicOrderId='+soId,pendingCancelNullProviderCB);
	 
	 }
	 if(expressData. subStatus == '68')
	 {
	 
		 newco.jsutils.doAjaxURLSubmit('serviceOrderPendingInfo_getPendingCancelInfo.action?servicOrderId='+soId,pendingCancelReviewProviderCB);
	
	 	
	 }
	 if (expressData. subStatus == '69')
	 {
	  newco.jsutils.doAjaxURLSubmit('serviceOrderPendingInfo_getPendingCancelInfo.action?servicOrderId='+soId,pendingCancelResponseProviderCB);
	 	
	 	
	 }
	 
	 
	 }
	 //buyer view
	 	if((expressData.roleId == '3')){
	 	
	 	window.scrollTo(1,100);
	 	dojo.byId('firstView'+theTab).style.display = 'none';
	  		dojo.byId('lastRequest'+theTab).style.display = 'none';
	  		dojo.byId('buyerDecision'+theTab).style.display = 'none';
	  		dojo.byId('newRequest'+theTab).style.display = 'none';
	  		dojo.byId('submit'+theTab).style.display = 'none';
	  		
	  	dojo.byId('submit'+theTab).style.display = 'none';
	    dojo.byId('disagreeSubmit'+theTab).style.display = 'none';
	    dojo.byId('withdrawRequest'+theTab).style.display = 'none';
	    	 dojo.byId('errorMsge'+theTab).style.display = 'none';
	    
	     	 dojo.byId('errorMessageW'+theTab).style.display = 'none';
	     
	 	    document.getElementById('serviceOrderId'+theTab).value = expressData.soId;
	 	     var soId=	expressData.soId;	
	 
	 		//staus=null
	 		if((expressData. subStatus != '68') && (expressData. subStatus != '69')){
	             
	             	  newco.jsutils.doAjaxURLSubmit('serviceOrderPendingInfo_getPendingCancelInfo.action?servicOrderId='+soId,pendingCancelNullBuyerCB);
	             
	             
	 
	 		}
	
			//status= pendingresponse
	 		if((expressData. subStatus == '69')){
	 
			newco.jsutils.doAjaxURLSubmit('serviceOrderPendingInfo_getPendingCancelInfo.action?servicOrderId='+soId,pendingCancelResponseBuyerCB);
				 
	             
			 }
	 
	 		//status= pending review
			if((expressData. subStatus == '68')){
				
	       newco.jsutils.doAjaxURLSubmit('serviceOrderPendingInfo_getPendingCancelInfo.action?servicOrderId='+soId,pendingCancelReviewBuyerCB);
	            
			} 
	}
	 
     }
     else
     {
     	 dojo.byId('cancellationDiv'+theTab).style.display = 'none';
     	  dojo.byId('amountCancelled'+theTab).style.display = 'none';
     
     }
	var bidPriceString = null;
	var bidScheduleString = null;
	
	// Min and Max bid/counter-offer price
	if(expressData.bidMax != 0)
	{
		bidPriceString = '$' + dojo.number.format(expressData.bidMin, {places:2}) + " to $" + dojo.number.format(expressData.bidMax, {places:2})
	}
	
	// Min and Max reschedule dates
	// We only want YYYY-MM-DD. Use substring() to clip the rest
	if(expressData.bidEarliestStartDate != null && expressData.bidEarliestStartDate != '')
	{
		bidScheduleString = expressData.bidEarliestStartDate.substring(0,10);
		if(expressData.bidLatestEndDate != null && expressData.bidLatestEndDate != '')
		{
			if( expressData.bidLatestEndDate.substring(0,10) != bidScheduleString)
			{
				bidScheduleString += ' to ';
				bidScheduleString += expressData.bidLatestEndDate.substring(0,10);
			}
		}
	}
	
	// Combine price range and date range into 2 lines
	dojo.byId('priceRange'+theTab).innerHTML = '';
	 if((viewOrderPricing=='true' &&  expressData.roleId == '1') || expressData.roleId == '3')
	 {
	if(bidPriceString != null)
	{
		dojo.byId('priceRange'+theTab).innerHTML += bidPriceString;
	}	
	if(bidScheduleString != null)
	{
		if(dojo.byId('priceRange'+theTab).innerHTML == '')
			dojo.byId('priceRange'+theTab).innerHTML = bidScheduleString;
		else
			dojo.byId('priceRange'+theTab).innerHTML +=  '<br/>' + bidScheduleString;
		
	}
	 }
	// Handle case where there are no bids or counter-offers
	 if((viewOrderPricing=='true' &&  expressData.roleId == '1') || expressData.roleId == '3')
	 {
	if((bidPriceString == null || bidPriceString == '')&& (bidScheduleString == null || bidScheduleString == ''))
	{
		if (dojo.byId('spendLimit'+theTab).innerHTML == '0.00'){
			dojo.byId('priceRange'+theTab).innerHTML = "No bids yet";
		} else {
			dojo.byId('priceRange'+theTab).innerHTML = "No counter offers";
		}
	}
	 }
	
	//alert('priceModelType:' + expressData.priceModelType);
	//alert('bidEarliestStartDate:' + expressData.bidEarliestStartDate + ' bidLatestEndDate:' + expressData.bidLatestEndDate);
	 if((viewOrderPricing=='true' &&  expressData.roleId == '1') || expressData.roleId == '3')
	 {
	if (dojo.byId('spendLimit'+theTab).innerHTML == '0.00'){
		dojo.byId('priceRangeDiv'+theTab).innerHTML= "<b>Bid Range:</b>";
	} else {
		dojo.byId('priceRangeDiv'+theTab).innerHTML= "<b>Counter Offer(s):</b>";
	}
	 
	if(dojo.byId('totalFinalPrice'+theTab)){
		 if(expressData.status=="Closed" || expressData.status=="Completed" || expressData.status=="Cancelled"){
			dojo.byId('totalFinalPrice'+theTab).innerHTML= dojo.number.format(expressData.totalFinalPrice, {places:2});
			document.getElementById('totalFinalPriceDiv'+theTab).style.display = 'block';
 			dojo.byId('totalFinalPrice'+theTab).style.display = 'block';
		}else if(expressData.status=="Deleted" || expressData.status=="Voided"){
			dojo.byId('totalFinalPrice'+theTab).innerHTML= dojo.number.format(0.00, {places:2});
			document.getElementById('totalFinalPriceDiv'+theTab).style.display = 'block';
 			dojo.byId('totalFinalPrice'+theTab).style.display = 'block';
		}else{
			dojo.byId('totalFinalPrice'+theTab).innerHTML="N/A";
			document.getElementById('totalFinalPriceDiv'+theTab).style.display = 'none';
 			dojo.byId('totalFinalPrice'+theTab).style.display = 'none';
		}
	}
	
	if(dojo.byId('spendLimit'+theTab)){
		if(expressData.status=="Deleted" || expressData.status=="Voided"){
			dojo.byId('spendLimit'+theTab).innerHTML= dojo.number.format(0.00, {places:2});			
		}
	}
	 }
	var priceModelType = expressData.priceModelType;
	
	if (priceModelType !== undefined && theTab !== undefined) {
		if (priceModelType == 1) { //bid request
			dojo.byId('spendLimit'+theTab).style.display = 'none';
			dojo.byId('spendLimitDiv'+theTab).style.display = 'none';
			dojo.byId('priceRange'+theTab).style.display = 'none';
			dojo.byId('priceRangeDiv'+theTab).style.display = 'none';
		}
	}
	
	 dojo.byId('buyer'+theTab).innerHTML= expressData.buyer;
	 
	 if(expressData.roleId == '3' && ( expressData.status=="Closed" 
		                            || expressData.status=="Accepted" 
		                            || expressData.status=="Active" 
		                            || expressData.status=="Problem" 
		                            || expressData.status=="Cancelled" 
		                            || expressData.status=="Pending Cancel")){
		 
	 	if(expressData.providerFirm !== undefined && expressData.providerFirm !== ""){
	 		dojo.byId('providerFirmLabel'+theTab).innerHTML="Provider Firm";
	 		dojo.byId('providerFirm'+theTab).innerHTML= expressData.providerFirm;
	 		if(expressData.firmBusinessNumber !== undefined && expressData.firmBusinessNumber !== ""){
	 			dojo.byId('firmBusinessNumber'+theTab).innerHTML= expressData.firmBusinessNumber;
	 		}
	 	}else{
	 		dojo.byId('providerFirmLabel'+theTab).innerHTML="";
	 		dojo.byId('providerFirm'+theTab).innerHTML= "";
	 		dojo.byId('firmBusinessNumber'+theTab).innerHTML= "";
	 	}
	 } else{
		 if(expressData.roleId == '3'){
			 dojo.byId('providerFirmLabel'+theTab).innerHTML="";
		 	 dojo.byId('providerFirm'+theTab).innerHTML= "";
		 	 dojo.byId('firmBusinessNumber'+theTab).innerHTML= "";
		 }
		   
	 }
	
	 dojo.byId('endCustomer'+theTab).innerHTML= expressData.endCustomer;
	 dojo.byId('endCustomerPrimaryPhoneNumberWidget'+theTab).innerHTML= expressData.endCustomerPrimaryPhoneNumberWidget;
	 
	 if(unassigned=='true'){
		 var unassignedIcon = "<img src='"+newco.jsutils.getStaticContext()+"/images/icons/icon-unassigned.png' title='Assign a provider prior to service start' style='height: 50%; width: 50%;'></img>";
	 	 dojo.byId('provider'+theTab).innerHTML="<div style='float:left;'>Unassigned</div><div style='float:left;padding-left:3px;'>"+unassignedIcon+"</div>";
	 }else{
		 dojo.byId('provider'+theTab).innerHTML= expressData.provider;
	 }
	 
	 if(expressData.provider!="N/A" && expressData.provider.indexOf('(') != -1){
		if(expressData.roleId == '3'){
			var provId = expressData.provider.substr(pEndIndex+1).substr(0,expressData.provider.substr(pEndIndex+1).indexOf(')'));
			dojo.byId('provider'+theTab).innerHTML= "<a href='javascript:void(0);' onclick='openProviderProfile ("+provId+","+expressData.firmId+", /MarketFrontend/)'>"+
		 	expressData.provider.substr(pLnEndIndex+1,pEndIndex-(pLnEndIndex+1)) +" "
		 	+expressData.provider.substr(0,pLnEndIndex) +" (User Id# " +expressData.provider.substr(pEndIndex+1)+"</a>";
			dojo.byId('providerPrimaryPhoneNumberWidget'+theTab).innerHTML= expressData.providerPrimaryPhoneNumberWidget;
			dojo.byId('providerAltPhoneNumberWidget'+theTab).innerHTML= expressData.providerAltPhoneNumberWidget;	
		}else{
			 dojo.byId('provider'+theTab).innerHTML= expressData.provider.substr(pLnEndIndex+1,pEndIndex-(pLnEndIndex+1)) +" "
		 	+expressData.provider.substr(0,pLnEndIndex) +" (User Id# " +expressData.provider.substr(pEndIndex+1);
			 dojo.byId('providerPrimaryPhoneNumberWidget'+theTab).innerHTML= expressData.providerPrimaryPhoneNumberWidget;
			 dojo.byId('providerAltPhoneNumberWidget'+theTab).innerHTML= expressData.providerAltPhoneNumberWidget;	
		}
			 	
	 }else if(expressData.provider=="N/A"){
			 dojo.byId('providerPrimaryPhoneNumberWidget'+theTab).innerHTML="";
			 dojo.byId('providerAltPhoneNumberWidget'+theTab).innerHTML= "";
		 }
		 
	  if(unassigned=='true'){
		 var unassignedIcon = "<img src='"+newco.jsutils.getStaticContext()+"/images/icons/icon-unassigned.png' title='Assign a provider prior to service start' style='height: 50%; width: 50%;'></img>";
	 	 dojo.byId('provider'+theTab).innerHTML="<div style='float:left;'>Unassigned</div><div style='float:left;padding-left:3px;'>"+unassignedIcon+"</div>";
	 }
	 dojo.byId('location'+theTab).innerHTML= expressData.location;
	 document.getElementById('toLoc').value = expressData.location;
	dojo.byId('status'+theTab).innerHTML= expressData.status;
	 document.getElementById('orderStatus').value = expressData.status;
     document.getElementById('zip').value = expressData.zip;
		if(expressData.statusId == '110' && expressData.roleId == '1') {
			// this signifies multiple provider scenario for provider admin, otherwise we get the dispatch addr populated for the non-admin user
			if((expressData.statusId == '110'|| expressData.statusId == 110)&&(primaryInd == true || primaryInd == 'true' || dispatchInd == 'true' || dispatchInd==true)&&(expressData.resourceDispatchAddress == null ||  expressData.resourceDispatchAddress == '')&&(priceModelType != 1 || priceModelType != '1')) {
				
				dojo.byId('distanceInMiles'+theTab).innerHTML=  'Distance varies by provider';
				document.getElementById('fromLoc').value   = '';
			} else {
	 	dojo.byId('distanceInMiles'+theTab).innerHTML= '('+expressData.distanceInMiles+' miles)';
				document.getElementById('fromLoc').value = expressData.resourceDispatchAddress;
			}
	 }
	 if(dojo.byId('claimedByResource'+theTab)){
 		dojo.byId('claimedByResource'+theTab).innerHTML = expressData.claimedforreviewby;
 	}
 	newco.jsutils.Uvd('currentSpendLimit', newco.jsutils.removeFormatting(groupInd ? groupInfo.spendLimitTotal : expressData.spendLimitTotal));
 	if(dojo.byId('currentAmt'+theTab)){
 		dojo.byId('currentAmt'+theTab).innerHTML = groupInd ? groupInfo.spendLimitTotal : expressData.spendLimitTotal;
 	}

 	if(dojo.byId('increaseLimit'+theTab)){
 		newco.jsutils.Uvd('increaseLimit'+theTab,groupInd ? groupInfo.spendLimitLabor : expressData.spendLimitLabor);
 		newco.jsutils.Uvd('currentLimitLabor'+theTab,groupInd ? groupInfo.spendLimitLabor : expressData.spendLimitLabor);
 	}

 	if(dojo.byId('increaseLimitParts'+theTab)){
 		newco.jsutils.Uvd('increaseLimitParts'+theTab,groupInd ? groupInfo.spendLimitParts : expressData.spendLimitParts);
 		newco.jsutils.Uvd('currentLimitParts'+theTab,groupInd ? groupInfo.spendLimitParts : expressData.spendLimitParts);
 	}

	 if(dojo.byId('totalAmt'+theTab)){
	 		dojo.byId('totalAmt'+theTab).innerHTML = "";
	 }

	
	 if(dojo.byId('increasedSpendLimitComment'+theTab)){
	 		newco.jsutils.Uvd('increasedSpendLimitComment'+theTab,'');
	 }
	 if(dojo.byId('reason_widget'+theTab)){
	 		newco.jsutils.Uvd('reason_widget'+theTab,'-1');
	 		dojo.byId('comment_widget'+theTab).style.background='#E3E3E3';
	 		dojo.byId('comment_widget'+theTab).disabled=true;

	 }
	 if(dojo.byId('comment_widget'+theTab)){
	 		newco.jsutils.Uvd('comment_widget'+theTab,'');
	 }
	 
	if(dojo.byId('incident_data'+theTab)){
		var incident_data = "<table border='0' cellspacing='0' cellpadding='0'>";
	 	var incidentIndex = 0;
		for(incidentIndex=0; incidentIndex<expressData.associatedIncidents.length; ++incidentIndex) {
			incident_data +="<tr>";
			incident_data += "<td>"+expressData.associatedIncidents[incidentIndex].ageOfOrder+"</td>";
			incident_data += "<td><a href='assurantEditIncident_displayPage.action?clientIncidentID=" + expressData.associatedIncidents[incidentIndex].incidentId + "&soID=" + expressData.associatedIncidents[incidentIndex].soId + "&monitorTab=" + theTab + "'>"+ expressData.associatedIncidents[incidentIndex].incidentId+"</href></td>";
			incident_data += "<td><a href='monitor/soDetailsController.action?soId="+
				expressData.associatedIncidents[incidentIndex].soId+"'>"+expressData.associatedIncidents[incidentIndex].soId+"</a></td>";
			incident_data += "</tr>\n";
		}
		if (incidentIndex == 0) {
			incident_data += "<tr><td>N/A</td></tr>"
		}
		incident_data += "</table>";
		dojo.byId('incident_data'+theTab).innerHTML = incident_data;
	}
	
	if(newco.jsutils.isExist('increaseSPendLimitResponseMessage'+theTab)){		
    	$('increaseSPendLimitResponseMessage'+theTab).innerHTML = "";
    }
	
	/*
	// Now try google to get driving distance
	dojo.byId('drivingDistanceWidget'+theTab).innerHTML = "<img src='"+newco.jsutils.getStaticContext()+"/images/spinner_small.gif'/>";
	var fromLocation = document.getElementById('fromLoc').value;
	var fromLocationZip = fromLocation.substr(-6,6); //??
	var toLocation = document.getElementById('toLoc').value;  
	var toLocationZip = document.getElementById('zip').value;
	var soStatus = document.getElementById('orderStatus').value;
	if (soStatus == 'Posted') {
		toLocation = toLocationZip;
	}
	var pt2ptDistance = expressData.distanceInMiles;
	populateDistance(fromLocation, fromLocationZip, toLocation, toLocationZip, pt2ptDistance);
	*/
}
/*
function populateDistance(fromLocation, fromLocationZip, toLocation, toLocationZip, pt2ptDistance) {
 	var gDir = new GDirections(); 
	//alert("fromLocation = "+fromLocation+", fromLocationZip="+fromLocationZip+", toLocation="+toLocation+", toLocationZip="+toLocationZip+".");
	GEvent.addListener(gDir,"load", function() {
		distanceObj = gDir.getDistance();
		//sleep(1000);
		//alert("Status Code = " + gDir.getStatus().code + " Distance = " + distanceObj.meters + "!");
		var distanceString = Math.round(distanceObj.meters * 0.0621)/100 + ' Miles';
		//dojo.byId('drivingDistanceWidget'+theTab).innerHTML = distanceString;
	});
	
	GEvent.addListener(gDir,"error", function() {
		//dojo.byId('drivingDistanceWidget'+theTab).innerHTML = pt2ptDistance + " Miles (Center zip to center zip)";
	});
	
	var directionString = "from: "+fromLocation+" to: "+toLocation;
	gDir.load(directionString);
}
*/
function sleep(delay) {
    var start = new Date().getTime();
    while (new Date().getTime() < start + delay);
}

  // ajax for pending cancel


 var pendingCancelNullProviderCB = function pendingCancelNullProviderCB(data){
		
          var buyerPrice=newco.jsutils.getCommonXMLElement('addtional_01',data);
          var buyerComments=newco.jsutils.getCommonXMLElement('addtional_02',data);
          var buyerEntryDate=newco.jsutils.getCommonXMLElement('addtional_03',data);
          var providerPrice=newco.jsutils.getCommonXMLElement('addtional_04',data);
          var providerComments=newco.jsutils.getCommonXMLElement('addtional_05',data);
          var providerEntryDate=newco.jsutils.getCommonXMLElement('addtional_06',data);
          
          dojo.byId('pendingCancelHistoryIcon'+theTab).style.display = 'block';
	 dojo.byId('buyerEntryDate'+theTab).innerHTML= buyerEntryDate;
	 dojo.byId('buyerPrice'+theTab).innerHTML= dojo.number.format(buyerPrice, {places:2});
	 dojo.byId('buyerComments'+theTab).innerHTML= buyerComments;
	 dojo.byId('firstView'+theTab).style.display = 'none';
	 dojo.byId('notice'+theTab).innerHTML= 'received a request in';
	 dojo.byId('providerDecision'+theTab).style.display = 'block';
	 dojo.byId('agreeSubmit'+theTab).style.display = 'block';
	 dojo.byId('withdrawRequest'+theTab).style.display = 'none';
	 dojo.byId('comment'+theTab).style.display = 'none';
	 dojo.byId('amountCancelled'+theTab).innerHTML= dojo.number.format(buyerPrice, {places:2});
	  document.getElementById("agreedFirstProvider"+theTab).checked=true;
	 
	 	showDivInParent('widgetSOM_PendingCancelProvider');
	 
	
 }
 var pendingCancelReviewProviderCB = function pendingCancelReviewProviderCB(data){
  var buyerPrice=newco.jsutils.getCommonXMLElement('addtional_01',data);
          var buyerComments=newco.jsutils.getCommonXMLElement('addtional_02',data);
          var buyerEntryDate=newco.jsutils.getCommonXMLElement('addtional_03',data);
          var providerPrice=newco.jsutils.getCommonXMLElement('addtional_04',data);
          var providerComments=newco.jsutils.getCommonXMLElement('addtional_05',data);
          var providerEntryDate=newco.jsutils.getCommonXMLElement('addtional_06',data);
          
  dojo.byId('buyerEntryDate'+theTab).innerHTML= providerEntryDate;
	 	 dojo.byId('buyerPrice'+theTab).innerHTML= dojo.number.format(providerPrice, {places:2});
	 	 dojo.byId('buyerComments'+theTab).innerHTML= providerComments;
	 	 dojo.byId('firstView'+theTab).style.display = 'block';
	 	 dojo.byId('notice'+theTab).innerHTML= 'requested';
	 	 dojo.byId('withdrawRequest'+theTab).style.display = 'block';
	 	 dojo.byId('comment'+theTab).style.display = 'block';
	 	 dojo.byId('amountCancelled'+theTab).innerHTML= dojo.number.format(providerPrice, {places:2});
	 	 
	 	 	 showDivInParent('widgetSOM_PendingCancelProvider');
	 
 
 
 }
 var pendingCancelResponseProviderCB = function pendingCancelResponseProviderCB(data){
  var buyerPrice=newco.jsutils.getCommonXMLElement('addtional_01',data);
          var buyerComments=newco.jsutils.getCommonXMLElement('addtional_02',data);
          var buyerEntryDate=newco.jsutils.getCommonXMLElement('addtional_03',data);
          var providerPrice=newco.jsutils.getCommonXMLElement('addtional_04',data);
          var providerComments=newco.jsutils.getCommonXMLElement('addtional_05',data);
          var providerEntryDate=newco.jsutils.getCommonXMLElement('addtional_06',data);
          
 dojo.byId('buyerEntryDate'+theTab).innerHTML= buyerEntryDate;
	 	dojo.byId('buyerPrice'+theTab).innerHTML= dojo.number.format(buyerPrice, {places:2});
	 	dojo.byId('buyerComments'+theTab).innerHTML= buyerComments;
	 	dojo.byId('firstView'+theTab).style.display = 'none';
	 	dojo.byId('notice'+theTab).innerHTML= 'received a request in';
	 	dojo.byId('providerDecision_pend_resp'+theTab).style.display = 'block';
	 	dojo.byId('agreeSubmit'+theTab).style.display = 'block';
	 	dojo.byId('withdrawRequest'+theTab).style.display = 'none';
	 	dojo.byId('comment'+theTab).style.display = 'block';
	 	dojo.byId('amountCancelled'+theTab).innerHTML= dojo.number.format(buyerPrice, {places:2});
 	   document.getElementById("agreedProvider"+theTab).checked=true;
 	   
 	 	 showDivInParent('widgetSOM_PendingCancelProvider');
 
 }
 
  var pendingCancelNullBuyerCB = function pendingCancelNullBuyerCB(data){
  
   var buyerPrice=newco.jsutils.getCommonXMLElement('addtional_01',data);
          var buyerComments=newco.jsutils.getCommonXMLElement('addtional_02',data);
          var buyerEntryDate=newco.jsutils.getCommonXMLElement('addtional_03',data);
          var providerPrice=newco.jsutils.getCommonXMLElement('addtional_04',data);
          var providerComments=newco.jsutils.getCommonXMLElement('addtional_05',data);
          var providerEntryDate=newco.jsutils.getCommonXMLElement('addtional_06',data);
          
 //dojo.byId('pendingCancelHistoryIcon'+theTab).style.display = 'none';
	              dojo.byId('buyerEntryDate'+theTab).innerHTML= buyerEntryDate;
				 dojo.byId('buyerPrice'+theTab).innerHTML= dojo.number.format(buyerPrice, {places:2});
				 dojo.byId('buyerComments'+theTab).innerHTML= buyerComments;
				 dojo.byId('firstView'+theTab).style.display = 'block';
				 dojo.byId('notice'+theTab).innerHTML= 'sent a request in';
				 dojo.byId('submit'+theTab).style.display = 'none';
				 dojo.byId('disagreeSubmit'+theTab).style.display = 'none';
				 dojo.byId('withdrawRequest'+theTab).style.display = 'none';
				 dojo.byId('comment'+theTab).style.display = 'block';
				 dojo.byId('amountCancelled'+theTab).innerHTML= dojo.number.format(buyerPrice, {places:2});
				
				 	 	 showDivInParent('widgetSOM_PendingCancel');
				 
 
 }
 var pendingCancelReviewBuyerCB = function pendingCancelReviewBuyerCB(data){
  var buyerPrice=newco.jsutils.getCommonXMLElement('addtional_01',data);
          var buyerComments=newco.jsutils.getCommonXMLElement('addtional_02',data);
          var buyerEntryDate=newco.jsutils.getCommonXMLElement('addtional_03',data);
          var providerPrice=newco.jsutils.getCommonXMLElement('addtional_04',data);
          var providerComments=newco.jsutils.getCommonXMLElement('addtional_05',data);
          var providerEntryDate=newco.jsutils.getCommonXMLElement('addtional_06',data);
          var availableBalance=newco.jsutils.getCommonXMLElement('addtional_07',data);
          var isSoLevelAutoACH=newco.jsutils.getCommonXMLElement('addtional_09',data);
 dojo.byId('lastRequest'+theTab).style.display = 'block';
				dojo.byId('buyerDecision'+theTab).style.display = 'block';
				dojo.byId('submit'+theTab).style.display = 'block';
				dojo.byId('notice'+theTab).innerHTML= 'received a request in';
				dojo.byId('buyerEntryDate'+theTab).innerHTML= providerEntryDate;
				dojo.byId('buyerPrice'+theTab).innerHTML= dojo.number.format(providerPrice, {places:2});
				dojo.byId('buyerComments'+theTab).innerHTML= providerComments;
				dojo.byId('soLevelAutoACH'+theTab).value= isSoLevelAutoACH;
				dojo.byId('buyerEntryDatelastRequest'+theTab).innerHTML= buyerEntryDate;
				dojo.byId('buyerPricelastRequest'+theTab).innerHTML= dojo.number.format(buyerPrice, {places:2});
				dojo.byId('withdrawRequest'+theTab).style.display = 'none';
				dojo.byId('comment'+theTab).style.display = 'block';
	            dojo.byId('amountCancelled'+theTab).innerHTML= dojo.number.format(providerPrice, {places:2});
	            document.getElementById("agreedRadio"+theTab).checked=true;
	            if(availableBalance!='')
	            {
	           dojo.byId('balance'+theTab).value= dojo.number.format(availableBalance, {places:2});
	            }
	            	 	 showDivInParent('widgetSOM_PendingCancel');
	            
 
 }
 var pendingCancelResponseBuyerCB = function pendingCancelResponseBuyerCB(data){
  var buyerPrice=newco.jsutils.getCommonXMLElement('addtional_01',data);
          var buyerComments=newco.jsutils.getCommonXMLElement('addtional_02',data);
          var buyerEntryDate=newco.jsutils.getCommonXMLElement('addtional_03',data);
          var providerPrice=newco.jsutils.getCommonXMLElement('addtional_04',data);
          var providerComments=newco.jsutils.getCommonXMLElement('addtional_05',data);
          var providerEntryDate=newco.jsutils.getCommonXMLElement('addtional_06',data);
          var availableBalance=newco.jsutils.getCommonXMLElement('addtional_07',data);
          var buyerPrvsAmount=newco.jsutils.getCommonXMLElement('addtional_08',data);
          var isSoLevelAutoACH=newco.jsutils.getCommonXMLElement('addtional_09',data);
          
 dojo.byId('buyerEntryDate'+theTab).innerHTML= buyerEntryDate;
				 dojo.byId('buyerPrice'+theTab).innerHTML= dojo.number.format(buyerPrice, {places:2});
				 dojo.byId('buyerComments'+theTab).innerHTML= buyerComments;
				 dojo.byId('soLevelAutoACH'+theTab).value= isSoLevelAutoACH;
				 dojo.byId('firstView'+theTab).style.display = 'block';
				 dojo.byId('notice'+theTab).innerHTML= 'sent a request in';
				 dojo.byId('submit'+theTab).style.display = 'none';
				 dojo.byId('disagreeSubmit'+theTab).style.display = 'none';
				 dojo.byId('withdrawRequest'+theTab).style.display = 'block';
				 dojo.byId('comment'+theTab).style.display = 'block';
	             dojo.byId('amountCancelled'+theTab).innerHTML= dojo.number.format(buyerPrice, {places:2});
	             
	             if(availableBalance!='')
	            {
	           dojo.byId('balance'+theTab).value= dojo.number.format(availableBalance, {places:2});
	            }
	            if(buyerPrvsAmount!='')
	            {
	            	           dojo.byId('buyerPrvsAmount'+theTab).value= dojo.number.format(buyerPrvsAmount, {places:2});
	            }
	            
	             	 	 showDivInParent('widgetSOM_PendingCancel');
	             
 
 }
 
 
 // end of ajax for pending cancel
          
          
          
newco.jsutils.openMap = function(){ 
      var status=document.getElementById('orderStatus').value;
      var fromLoc = document.getElementById('fromLoc');          
      var toLoc;
      if(status=='Received'){      	 
     	 toLoc = document.getElementById('zip');
      }else{     	  
          toLoc = document.getElementById('toLoc');
      }  
      var toLocVal = escape(toLoc.value);
      var fromLocVal = escape(fromLoc.value);      
      //alert('http://maps.google.com/maps?f=q&hl=en&geocode=&q=from:+'+fromLocVal+'+to:+'+toLocVal);  
      window.open('http://maps.google.com/maps?f=q&hl=en&geocode=&q=from:+'+fromLocVal+'+to:+'+toLocVal);
 }

newco.jsutils._updateRoutedDetailsBuyer = function( xml , gridId, node, i){

		 		var ajaxCount =node.slice(0,6);

		 		if (ajaxCount=="cCount"){
			 		cCountVal = xml.getElementsByTagName(node)[0].childNodes[0].nodeValue;
			 	}
			 	if (ajaxCount=="pCount"){
			 		pCountVal = xml.getElementsByTagName(node)[0].childNodes[0].nodeValue;
			 	}
			 	if (ajaxCount=="rCount"){
			 		rCountVal = xml.getElementsByTagName(node)[0].childNodes[0].nodeValue;
			 	}
			 	if (ajaxCount=="sCount"){
			 		sCountVal = xml.getElementsByTagName(node)[0].childNodes[0].nodeValue;
			 	}
		 		if (ajaxCount=="soidno"){
			 		soIdNo = xml.getElementsByTagName(node)[0].childNodes[0].nodeValue;
					parent._commonSOMgr.addToken(parent.newco.rthelper.createSOToken(pCountVal,cCountVal,rCountVal,sCountVal,
							{routedCnt:pCountVal, acceptedCnt:cCountVal, rejectedCnt:rCountVal, spendLimit:sCountVal},
							 soIdNo,i));
					parent._commonSOMgr.startService();
		 			cCountVal = "";
		 			pCountVal = "";
		 			rCountVal = "";
		 			sCountVal = "";
		 			soIdNo = "";
		 		}
		 		var retVal = "";
		 		var iFrameRef = document.getElementById(gridId);
		 		if(!dojo.isIE){
		 			retVal = newco.jsutils.isExist(iFrameRef.contentDocument.getElementById(node));
		 		}
		 		else{
		 			retVal = newco.jsutils.isExist(iFrameRef.contentWindow.document.getElementById(node));
		 		}

		 		if (retVal==true){
		 			var att = xml.getElementsByTagName(node)[0].childNodes[0].nodeValue;
		 			if(!dojo.isIE){
						iFrameRef.contentDocument.getElementById(node).innerHTML = att;
					}
					else{
						iFrameRef.contentWindow.document.getElementById(node).innerHTML = att;
					}
				}
}

newco.jsutils._updateRoutedDetailsProvider = function( xml , gridId, node, i){
		 		var ajaxCount =node.slice(0,6);
			 	if (ajaxCount=="sCount"){
					//token
			 		sCountVal = xml.getElementsByTagName(node)[0].childNodes[0].nodeValue;
			 	}
		 		if (ajaxCount=="soidno"){
			 		soIdNo = xml.getElementsByTagName(node)[0].childNodes[0].nodeValue;
					parent._commonSOMgr.addToken(new PToken(soIdNo,sCountVal,i));
					parent._commonSOMgr.startService();
		 			sCountVal = "";
		 			soIdNo = "";
		 		}
		 		var retVal = "";
		 		var iFrameRef = document.getElementById(gridId);
		 		if(!dojo.isIE){
		 			retVal = newco.jsutils.isExist(iFrameRef.contentDocument.getElementById(node));
		 		}
		 		else{
		 			retVal = newco.jsutils.isExist(iFrameRef.contentWindow.document.getElementById(node));
		 		}

		 		if (retVal==true){
		 			var att = xml.getElementsByTagName(node)[0].childNodes[0].nodeValue;
		 			var scountLabels;
		 			if(!dojo.isIE){
						iFrameRef.contentDocument.getElementById(node).innerHTML = att;
						scountLabels = iFrameRef.contentDocument.getElementsByTagName("label");
					}
					else{
						iFrameRef.contentWindow.document.getElementById(node).innerHTML = att;
						scountLabels = iFrameRef.contentWindow.document.getElementsByTagName("label");
					}
					//Since SO can be routed to diff resouces with the same provider
					// there can be more than 1 entry for the SO and getElementById will return only 1 record
					// hence get the element using tagname and then update it
					for (var i = 0; i < scountLabels.length; i++) {
					    labelid = scountLabels[i].getAttribute("id");
					    if(labelid == node){
					    	scountLabels[i].innerHTML = att;
					    }
					}
				}
}

newco.jsutils.removeFormatting = function(InString) {

	//	note that decimal point is kept in string
	if (InString == null || InString == ''){
		return InString;
	}else {
		RefString=".0123456789"
		Output=''
		for(count=0; count < InString.length; count++) {
			if (RefString.indexOf (InString.charAt(count), 0) > -1)
				Output += InString.charAt(count)
		}
		return Output
}}

// _getResultsAsXML:
	//		A registry to make contextual calling/searching easier.
	// description:
	//		Objects of this class keep list of arrays in the form [name, check,
	//		wrap, directReturn] that are used to determine what the contextual
	//		result of a set of checked arguments is. All check/wrap functions
	//		in this registry should be of the same arity.
newco.jsutils._getResultsAsXML = function( xml , gridId, roleType){
		GLOBAL_OBJECT[1] = roleType;
		var realtimeCount = 0;
		rootElement = xml.documentElement;
		for (i=0; i<rootElement.childNodes.length; i++){
			//Tag
		 	node = rootElement.childNodes[i].nodeName;
			if ( (node=="tab_today") || (node=="tab_draft") ||
		 		 (node=="tab_sent")  || (node=="tab_accepted") ||
		 		 (node=="tab_inactive") || (node=="tab_problem") ||
		 		 (node=="tab_received") || (node=="tab_posted") || (node == "sent_changed")){

		 		 var id = "";

		 		 if (node=="tab_today"){
		 		 	if( GLOBAL_OBJECT[1] == 5 )
		 		 		id = "Current";
		 		 	else
		 		 		id = "Today";
		 		 }else if (node=="tab_draft"){
		 		 	id = "Draft";
		 		 }else if(node=="tab_sent"){
		 		 	id = "Sent";
		 		 }else if(node=="tab_accepted"){
		 		 	id = "Accepted";
		 		 }else if(node=="tab_inactive"){
		 		 	id = "Inactive";
		 		 }else if(node=="tab_problem"){
		 		 	id = "Problem";
		 		 }else if(node=="tab_posted"){
		 		 //if it's a provider...it needs to be received
		 		 	id = "Posted";
		 		 	realtimeCount = xml.getElementsByTagName(node)[0].childNodes[0].nodeValue;
		 		 }else if(node=="tab_received"){
		 		 	id = "Received";
		 		 	realtimeCount = xml.getElementsByTagName(node)[0].childNodes[0].nodeValue;
		 		 }else if(node=="sent_changed"){
		 		 //if it's a provider...it needs to be received
		 		 	id = "SentChanged";
		 		 }
		 		 newco.jsutils.getXMLElement(node, xml, id);
		 	}
		 	else{
		 		if (parent._commonSOMgr.widgetId == 'Posted' || parent._commonSOMgr.widgetId == 'Received'){
		 			if(node=="clear_cache"){
						//Updates are going to occur...Clear the Cache
						parent._commonSOMgr._clearPersistStateCache();
						//LMA Added Tab Token
						parent._commonSOMgr.addToken(new TabToken(parent._commonSOMgr.widgetId,realtimeCount));
					}

					if (node=="tab_resubmit"){
						parent._commonSOMgr._clearPersistStateCache();
						newco.jsutils.doIFrSubmit(gridId);
					}else{
						if(parent._commonSOMgr.widgetId == 'Posted'){
							newco.jsutils._updateRoutedDetailsBuyer(xml, gridId, node,i);
						}
						if(parent._commonSOMgr.widgetId == 'Received'){
						 	newco.jsutils._updateRoutedDetailsProvider(xml, gridId, node,i);
						}
					}
				}
			}
		}
		return xml;
}

newco.jsutils.escapeApostrophe  = function(str){
	return str.replace("/'/g", "\'");
}



newco.jsutils.clearStatusSubstatus = function(tabName){

     if( newco.jsutils.isExist($("statusId"+tabName))){
		var status = $("statusId"+tabName);
		status.selectedIndex = 0;
	}

	if( newco.jsutils.isExist($("subStatusId"+tabName))){
		var subStatus = $("subStatusId"+tabName);
		subStatus.selectedIndex = 0;
	}
  }



newco.jsutils.validateAndSelectReasonCode = function(selectObj, checkText ){
	var id = selectObj+'SUB';
 	var slElement = null;
 	var selectedIndex = -1;
 	var slLength = document.getElementById(id).options.length;	
 	if(slElement == null)
 	{
 		var selectList = document.getElementsByTagName('select');
 		for(var i=0; i<selectList.length;i++)
 		{
 			if(selectList[i].id == id.toString())
 			{ 				 				
 				if(selectList[i].selectedIndex > -1)
 				{
 					//alert('finding selects with name: ' + id + ' and index: ' + selectList[i].selectedIndex);
 					slElement = selectList[i];
 					break;
 				}
			} 		
		} 		
 	}
 	if(slElement == null)
 	{
 	return true;
 	}

	//alert('finding selects with name: ' + id + ' and index: ' + slElement.selectedIndex);
 	if(slElement.selectedIndex != -1)
 	{
		selectedIndex = slElement.selectedIndex;
		var selectedValue = slElement.options[selectedIndex].value;
		var selectedText = slElement.options[selectedIndex].text;
		if(selectedText == checkText && slLength>1)
		{
			return true;		 
		}
		else
		{
			newco.jsutils.Uvd('credentialRequest.subSelectName' , selectedValue);
			return false;
		}
 	}
 	else
 	{
 		if(slLength ==1)
 		{
 			slElement.options[0].selected = true;
 			return false;
 		}
 		else
 		{
 			return true;
 		}
 	}


}


newco.jsutils.handleCommonAJAXCall = function(selectObj, selectElement, type, innerDiv, actionType, source)  {
				
				 this.formContainer = "";
				 if(newco.jsutils.checkForReset(selectObj.value,innerDiv)){
				 	return;
				 }
				 if(actionType != null && actionType == 'CredForm'){
				 	//newco.jsutils.setSelectKeyAndValueInContainer(selectElement,'credForm',type);
				 	newco.jsutils.Uvd('credentialRequest.subSelectName' ,selectObj.id+'SUB');
				 	newco.jsutils.Uvd('credentialRequest.selectType',type);
				 	newco.jsutils.Uvd('credentialRequest.currentVal',selectObj.value);
				 	newco.jsutils.Uvd('credentialRequest.currentKey', selectObj.options[selectObj.selectedIndex].text);
				 	newco.jsutils.Uvd('credentialRequest.actionSubmitType', source);
				 	this.formContainer = 'credForm';
				 }
				 else {
				 	// newco.jsutils.setSelectKeyAndValueInContainer(selectElement,'theFormContainer',type);
				 	newco.jsutils.Uvd('companyOrServicePro.subSelectName',selectObj.id+'SUB');
				 	newco.jsutils.Uvd('companyOrServicePro.selectType',type);
				 	newco.jsutils.Uvd('companyOrServicePro.currentVal',$(selectElement).value);
				 	newco.jsutils.Uvd('companyOrServicePro.currentKey',$(selectElement).options[$(selectElement).selectedIndex].text);
				 	newco.jsutils.Uvd('companyOrServicePro.actionSubmitType', actionType);
				 	this.formContainer = 'theFormContainer';
				 	// todo --  determine what actiontype is being sent
				 	// ServiceProCompany is needed for service pro calls

				 }
				 newco.jsutils.doAjaxSubmit(newco.jsutils.getContext()+'commonAuditorAjax.action',
                							newco.jsutils.auditorCBHandler,null,
                							this.formContainer);
               	 newco.jsutils.showDiv(innerDiv);
			}

			newco.jsutils.handleProviderFirmAppv = function (selectElement,type) {

                  var secToken =document.getElementById("ss").value; 
				 if(newco.jsutils.validateAndSelectReasonCode(selectElement,'Not Applicable')){
				 	alert("The status you selected requires at least one substatus selected. \nPlease select one or more sub-status. To select more then one sub-status,\npress the CTRL key and select one or more substatus");
				 	return false;
				 }
				 newco.jsutils.Uvd('companyOrServicePro.actionSubmitType', 'ProviderCompany');
				 newco.jsutils.doAjaxSubmit(newco.jsutils.getContext()+'auditorApproval.action?ss='+secToken,
                							newco.jsutils.handleProviderFirmAppvCBHandler,null,
                							'theFormContainer');
			}

			newco.jsutils.handleServiceProAppv = function (selectElement,type) {
			
				 var secToken =document.getElementById("ss").value;	
				 if(newco.jsutils.validateAndSelectReasonCode(selectElement,'Not Applicable')){
				 		alert("The status you selected requires at least one substatus selected. \nPlease select one or more sub-status. To select more then one sub-status,\npress the CTRL key and select one or more substatus");
				 	return false;
				 }
				 newco.jsutils.Uvd('companyOrServicePro.actionSubmitType', 'ServiceProCompany');
				 newco.jsutils.doAjaxSubmit(newco.jsutils.getContext()+'auditorApproval.action?ss='+secToken,
                							newco.jsutils.handleProviderFirmAppvCBHandler,null,
                							'theFormContainer');
			}



			newco.jsutils.handleCredentialAppv = function (selectElement,type, credentialKey,auditTimeLoggingId) {
				 var vendorCredId =document.getElementById("vendorCredId").value;
				 
				 if(auditTimeLoggingId == '0')
					{
					 auditTimeLoggingId=document.getElementById("credentialRequest.auditTimeLoggingIdNew").value;
				 }
				 
				 var secToken =document.getElementById("ss").value;
				 if(vendorCredId != '0'){
				 	credentialKey = vendorCredId;
				 }
				 if(newco.jsutils.validateAndSelectReasonCode(selectElement,'Not Applicable')){
				 		alert("The status you selected requires at least one substatus selected. \nPlease select one or more sub-status. To select more then one sub-status,\npress the CTRL key and select one or more substatus");
				 	return false;
				 }
				  var theFormContainer = "theFormContainer";
				 if(type == 'Company Credential')
				 {
				 	theFormContainer = 'credForm'
				 	 newco.jsutils.Uvd('credentialRequest.actionSubmitType', 'VendorCredential');
				 }
				 else if(type == 'Team Member Credential'){
				 	 newco.jsutils.Uvd('credentialRequest.actionSubmitType', 'ServiceProCredential');
				 }
				 
				newco.jsutils.Uvd('credentialRequest.credentialKey', credentialKey);
								 
				 newco.jsutils.doAjaxSubmit(newco.jsutils.getContext()+'auditorApproval.action?ss='+secToken+'&auditTimeLoggingIdNew='+auditTimeLoggingId,
                							newco.jsutils.handleProviderFirmAppvCBHandler,null,
                							'credForm');
			}


			newco.jsutils.handleEmailOption = function (elementObj, type){
				if(newco.jsutils.isExist(elementObj.id)){
					if(type == 'CredForm'){
						newco.jsutils.Uvd('credentialRequest.sendEmail',elementObj.value);
					}
					else{
						newco.jsutils.Uvd('companyOrServicePro.sendEmail',elementObj.value);
					}
				}
			}


			newco.jsutils.showDiv = function (theId) {
				var doc = document.getElementsByTagName('div');
				for (var i = 0; i < doc.length; i=i+1)
				{
					if(doc[i].id == theId.toString())
					{
	   					doc[i].style.display = "block";
	   				}
				}               	             	 
			}

			newco.jsutils.hideDiv = function (theId) {
				var doc = document.getElementsByTagName('div');
				for (var i = 0; i < doc.length; i=i+1)
				{
					if(doc[i].id == theId.toString())
					{
	   					doc[i].style.display = "none";
	   				}
				}               	             	 
			}

			newco.jsutils.checkForReset = function (selectElementValue, theElement){
				if(selectElementValue == -1){
					newco.jsutils.hideDiv(theElement);
					return true;
				}
				return false;
			}


			 newco.jsutils.auditorCBHandler = function(data){
				//alert('call back invoked');
			      var passFailRst = newco.jsutils.handleCBData(data);
				  if(passFailRst != null ){
				  	// do something here the call has returned
				  	if(passFailRst.pass_fail_status == 0)
				  		newco.jsutils.commonPopulateSubSelectListAX(data, passFailRst.addtional1);
				  }
			}

			 newco.jsutils.handleProviderFirmAppvCBHandler = function(data){
				//alert('call back invoked');
			      var passFailRst = newco.jsutils.handleCBData(data);
				  if(passFailRst != null ){
				  	// do something here the call has returned
				  	if(passFailRst.pass_fail_status == 0) {

				  		newco.jsutils.displayModalWithMessage('widgetActionSuccess',
					  											    passFailRst.resultMessage,'SUCCESS');

				  		if(  passFailRst.addtional3 == 'provider_service_pro_cred'  )
				  		  {
							if(newco.jsutils.isExist('provider_cred_status')){
								$('provider_cred_status').innerHTML ='Current status '+ passFailRst.addtional2;
								var elements = document.getElementsByClassName("cred_status");
								for(var i=0; i<elements.length; i++) {
								    elements[i].innerHTML ='Current status '+ passFailRst.addtional2;
								}
						  }
	 					  }else{
				  			$('currentStatus').innerHTML = '<b>'+passFailRst.addtional2+'</b>';
				  		}
				  		setTimeout("newco.jsutils.closeModal('widgetActionSuccess')",5000);
				  	}
				  	else
				  	{
				  	    newco.jsutils.displayModalWithMessage('widgetActionError',
					  											    passFailRst.resultMessage,'ERROR');

				  		setTimeout("newco.jsutils.closeModal('widgetActionError')",5000);
				  	}

				  }
			}


			newco.jsutils.commonSubmitAddNote = function (formId){
            	var success = false;
            	//var subject = $("subject").value;
            	var message = $("message").value;
            	newco.jsutils.Uvd('companyOrServicePro.actionSubmitType', 'AuditorNote');
            	//$("subjectLabelMsg").style.display="none";
            	$("subjectLabelMsg1").style.display="none";
               /*	if(subject == null || subject == "" || subject == "[subject]" || subject == "[Subject]"){
            		$("subjectLabelMsg").style.display="";
            		$("subjectLabelMsg").style.color="red";

               	}
            	else*/
            	 if (message == null || message == "" || message == "[Message]"){
              	   //	$("subjectLabelMsg1").style.display="";
            	   	$("subjectLabelMsg1").style.color="red";
                 }
            	else{
            		 success = true;
					 newco.jsutils.doAjaxSubmit(newco.jsutils.getContext()+'addAuditorNote.action',
                							newco.jsutils.commonAddNoteCallBack,null,
                							formId);
				}
				return success;
			}

			newco.jsutils.commonAddNoteCallBack = function(data){
				    var passFailRst = newco.jsutils.handleCBData(data);
				   if(passFailRst != null ){
					  	// do something here the call has returned
					  	if(passFailRst.pass_fail_status == 0) {
					  		newco.jsutils.displayModalWithMessage('widgetActionSuccess',
					  											    passFailRst.resultMessage,'SUCCESS');
					  		setTimeout("newco.jsutils.closeModal('widgetActionSuccess')",5000);
					  	}else{
					  		newco.jsutils.displayModalWithMessage('widgetActionError',
					  											   passFailRst.resultMessage,'ERROR');
					  		setTimeout("newco.jsutils.closeModal('widgetActionError')",5000);
					  	}
				  	}
			        newco.jsutils.resetNoteWidget();
			}

			newco.jsutils.captureNote = function(){
				// newco.jsutils.Uvd('commonNoteSubject', $('subject').value);
				 newco.jsutils.Uvd('commonMessageNote', $('message').value);
			}

			newco.jsutils.captureReviewComments = function(){
			
				var comment = null;				
				var txtList = document.getElementsByTagName('textarea');
				if(txtList != null)
				{
					for (var j = 0; j < txtList.length; j=j+1)
					{
						if(txtList[j].id == 'commonReviewComment')
						{
							if(txtList[j].value != null)
								comment = txtList[j].value;
						}
					}
				}					
				//alert('reviewComments:' + comment);
								
				newco.jsutils.Uvd('credentialRequest.commonReviewNote', comment);
			}
			
			newco.jsutils.limit_characters_255 = function(){
				// newco.jsutils.Uvd('commonNoteSubject', $('subject').value);
				 newco.jsutils.Uvd('credentialRequest.commonReviewNote', $('commonReviewComment').value);
				 if ($('commonReviewComment').value.length >= 256) 
				 { 
				 	$('commonReviewComment').value = $('commonReviewComment').value.substring(0,255); 
				 	alert('Comment is limited to 255 characters');
				 }
			}

			newco.jsutils.resetAddNoteSubject = function ()
			{
				/*if($('subject').value == '[Subject]' ){
					$('subject').value = '';
				 	newco.jsutils.Uvd('commonNoteSubject', '');
				}
				*/
			}

			newco.jsutils.resetAddNoteMessage = function ()
			{
				if($('message').value == '[Message]'){
				 $('message').value = '';
				 newco.jsutils.Uvd('commonMessageNote', '');
				}
			}

			newco.jsutils.resetNoteWidget = function() {
				if($('subject')){
					$('subject').value = '[Subject]';
				 	newco.jsutils.Uvd('companyOrServicePro.commonNoteSubject', '');
				}
				if($('message')){
				 $('message').value = '[Message]';
				  newco.jsutils.Uvd('commonMessageNote', '');
				}
			}

newco.jsutils.doSPNCriteriaValidation = function( nextPoint ) {

	var theSkillCount 		= 1;
	var theCatCount  		= 1;
	var hasSkillErrors 		= false;
	var hasCatErrors 		= false;
	var skillmsg 			= "";
	var catmsg 				= "";
	//select all select boxes with the class of spnCriteriaSkill
	//this.theList = $$("select.spnCriteriaSkill");

	dojo.query("select.spnCriteriaSkill").forEach(
		function(theItem){
			if(theItem.options[theItem.selectedIndex].value == -1){
				skillmsg+= theSkillCount +",";
				hasSkillErrors = true;
			}
			theSkillCount++;
		}
	);
	//select all select boxes with the class of spnCriteriaCat
	//this.theList = $$("select.spnCriteriaCat");

	dojo.query("select.spnCriteriaCat").forEach(
		function(theItem){
			if(theItem.options[theItem.selectedIndex].value == -1){
				catmsg+= theCatCount +",";
				hasCatErrors = true;
			}
			theCatCount++;
		}
	);
	if($('mainServiceCategory')){
		if($('mainServiceCategory').options[$('mainServiceCategory').selectedIndex].value == -1){
		  var commonMsg = 'Please select a <b>Main Service Category</b> or use Cancel to return to your previous page'
		  return newco.jsutils.spnErrorMsgHelper(commonMsg,5000);
		}
	}
	if(hasCatErrors && hasSkillErrors){
		 var commonMsg = 'There are multiple errors with your SPN Criteria <b>Categories and Skills</b><br>'
		 	 commonMsg+='Please verify <b>SPN Skill Criteria ('+skillmsg+')</b> and <b>SPN Categories Criteria ('+catmsg+')</b>';
		  return newco.jsutils.spnErrorMsgHelper(commonMsg,10000);
	}
	if(hasSkillErrors){
		 var commonMsg = 'Please select a <b>Skill</b> for <b>SPN Criteria '+skillmsg+'</b>'
		 return newco.jsutils.spnErrorMsgHelper(commonMsg,5000);
	}
	if(hasCatErrors){
	 var commonMsg = 'Please select a <b>Category</b> for <b>SPN Criteria '+catmsg+'</b>'
		 return newco.jsutils.spnErrorMsgHelper(commonMsg,5000);
	  }
	  else {
	   if(nextPoint == 'add'){
	   	 $('spnBuyerCriteriaBuilderAction').action = newco.jsutils.getContext()+'spnBuyerCriteriaBuilderAction_addSPNTask.action';
	  	 $('spnBuyerCriteriaBuilderAction').submit();
	   }else if(nextPoint == 'softPersistCriteria'){
	     $('spnBuyerCriteriaBuilderAction').action = newco.jsutils.getContext()+'spnBuyerCriteriaBuilderAction_softPersistCriteria.action';
	  	 $('spnBuyerCriteriaBuilderAction').submit();
	   }
	  }
}

newco.jsutils.spnErrorMsgHelper = function(commonMsg, showTime) {
		  newco.jsutils.displayModalWithMessage('spnCriteria',commonMsg,'ERROR');
		  setTimeout("newco.jsutils.closeModal('spnCriteria')",showTime);
		  return false;
}

newco.jsutils.jobCodeErrorMsgHelper = function(commonMsg, showTime) {
		  newco.jsutils.displayModalWithMessage('jobCodeCriteria',commonMsg,'ERROR');
		  setTimeout("newco.jsutils.closeModal('jobCodeCriteria')",showTime);
		  return false;
}

newco.jsutils.goToLocationFA = function(baseActionId, meth, action) {
	     $(baseActionId).action = newco.jsutils.getContext()+action+"_"+meth+".action";
	  	 $(baseActionId).submit();
}

newco.jsutils.loadCampaigns = function(action,meth,formId, theElement) {
	theVal = theElement.options[theElement.selectedIndex].value;
	if(theVal == -1){
		newco.jsutils.clearTableData('spnTb1');
		newco.jsutils.Uvd('theCurrentSpnId', theVal);
		newco.jsutils.Uvd('spid', theVal);
		setTimeout("newco.jsutils.clearTableData('spnTb1')",100)
		setTimeout("newco.jsutils.clearTableData('spnTb1')",200)
		$('innerMessage').innerHTML='No Campaign Selected -';
		return false;
	}
	else {
		$('innerMessage').innerHTML='Campaign Selected -'+theElement.options[theElement.selectedIndex].text;
	}
	newco.jsutils.Uvd('theCurrentSpnId', theVal);
	newco.jsutils.Uvd('spid', theVal);
	newco.jsutils.doAjaxSubmit(newco.jsutils.getContext()+action+"_"+meth+".action",
                							newco.jsutils.handleTableUpdate,null,
                							formId);
}


newco.jsutils.createCampaign = function(action,meth,formId) {
	newco.jsutils.doAjaxSubmit(newco.jsutils.getContext()+action+"_"+meth+".action",
                							newco.jsutils.handleTableUpdate,null,
                							formId);

}

newco.jsutils.handleTableUpdate = function(xml) {

	var commonAjaxResponse = newco.jsutils.handleCBData(xml);

	if(commonAjaxResponse != null && commonAjaxResponse.pass_fail_status == 1){
		 newco.jsutils.displayModalWithMessage('spnCampaignMgr',commonAjaxResponse.resultMessage,'ERROR');
		 setTimeout("newco.jsutils.closeModal('spnCampaignMgr')",5000);
		 return false;
	}
	var campaignHeaderData =  xml.getElementsByTagName('campaign');
	if(campaignHeaderData != null && campaignHeaderData.length == 0 ) {
				newco.jsutils.clearTableData('spnTb1');
				setTimeout("newco.jsutils.clearTableData('spnTb1')",100)
				setTimeout("newco.jsutils.clearTableData('spnTb1')",200)
	}
	else if(campaignHeaderData == null){newco.jsutils.clearTableData('spnTb1');
										setTimeout("newco.jsutils.clearTableData('spnTb1')",100)
										return false;}
	else {newco.jsutils.clearTableData('spnTb1');
		 setTimeout("newco.jsutils.clearTableData('spnTb1')",100)
		 setTimeout("newco.jsutils.clearTableData('spnTb1')",200)
		 setTimeout(function(){newco.jsutils.loadTable(xml)},350); }
}


newco.jsutils.loadTable = function(xml) {
			var campaignHeaderData =  xml.getElementsByTagName('campaign');
				for(i=0; i<campaignHeaderData.length;i++){
					 this.row = newco.jsutils.getBaseTableRow('spnTb1');

					 id = campaignHeaderData[i].childNodes[0].firstChild.nodeValue;

					 linkParams = {id:id,
					 			   linkDisplay:'Remove',
					 			   linkId:'link_'+i,
					 			   idx:i};

					 this.td = newco.jsutils.getNewCell(null,this.row);
					 this.td.setAttribute("width","15");
			 		 this.td.innerHTML = newco.jsutils.createRemoveLink(linkParams);

					 this.td = newco.jsutils.getNewCell(null,this.row);
			 		 this.td.setAttribute("width","39");
			 		 this.td.innerHTML = id;

					 this.td = newco.jsutils.getNewCell(null,this.row);
					 id = campaignHeaderData[i].childNodes[1].firstChild.nodeValue;
					 this.td.setAttribute("width","150");
					 this.td.setAttribute("align","center");
			 		 this.td.innerHTML = id;

					 this.td = newco.jsutils.getNewCell(null,this.row);
					 id = campaignHeaderData[i].childNodes[2].firstChild.nodeValue;
					 this.td.setAttribute("width","150");
					 this.td.setAttribute("align","center");
			 		 this.td.innerHTML = id;

					 this.td = newco.jsutils.getNewCell(null,this.row);
					 id = campaignHeaderData[i].childNodes[3].firstChild.nodeValue;
					 this.td.setAttribute("width","60");
			 		 this.td.innerHTML = id;
				}
}

newco.jsutils.createRemoveLink = function( params ) {
	    theTemp2 = "<a id='#{linkId}' href='javascript:newco.jsutils.removeCampaign(#{id},#{idx})'>#{linkDisplay}</a>";
				return this.htmlGen( new Template(theTemp2), params );
		}

newco.jsutils.removeCampaign = function(theId,idx) {
 		action = 'spnAdminAction';
		meth = 'deleteSPNCampaign';
		theEle = $('link_'+idx);
		//alert(theEle);
		$('spnTb1').deleteRow(theEle.parentNode.parentNode.rowIndex);
		this.theUrl = newco.jsutils.getContext()+action+"_"+meth+".action?selectedCampaignId="+theId+"&selectedSPN="+$('theCurrentSpnId').value
		newco.jsutils.doAjaxURLSubmit(this.theUrl,
                				   newco.jsutils.noAction);
}

newco.jsutils.noAction = function(xml){
	//newco.jsutils.loadCampaigns('spnAdminAction','loadCampaigns','spnAdminAction',$('selectedSPN'));
}


newco.jsutils.handleOptionSelect = function(source, target){
	 this.selectedOptions = new Array();
	if(newco.jsutils.isExist(source)){
		if($(source).options && $(source).options.length == 0){
			// added error message popup
			return false;
		}
		var thelength  = $(source).options.length;
		for(var i=thelength -1; i >=0; i--){
			var old = $(source).options[i];
			if (old.selected == true) {
				this.selectedOptions.push(old);
				$(source).remove(i);
			}
		}

	}
	if(this.selectedOptions.length > 0){
		dojo.forEach(this.selectedOptions,
						 function(oneEntry, theIndex){
						 	optionElement = document.createElement('option');
							optionElement.value = oneEntry.value;
							optionElement.text = oneEntry.text;
							try{
								$(target).options.add(optionElement, null);
								$(target).options[theIndex].selected = true
							} catch(ex){
								$(target).options.add(optionElement); //IE
								$(target).options[theIndex].selected = true;
							}

						});
			 }
		}


newco.jsutils.validateVal = function(element, theId){
	if(element.checked){
		$(theId).disabled = false;
	}
	else {
		$(theId).disabled = true;
		$(theId).value = '0.0';
	}
}

//Scan a string for all numerics: returns Boolean true if all numeric
newco.jsutils.isNumberChar =  function (theElement, theId){
		InString = theElement.value;
		RefString=".0123456789"
		if (isNaN(InString) ){
			$(theId).value = '0.0';
			newco.jsutils.spnErrorMsgHelper('Please enter numerics only!',5000);
    				return false
		}
	}





 function populateNotes(formId,elementId,target)
            {
            	var selectedElement = document.getElementById(formId).elements[elementId];
               // var selectedElement = document.getElementById(elementId);
                var selIndex =selectedElement.selectedIndex;
                var selectedValue = selectedElement.options[selIndex].value;
                if(selectedValue!=null)
                {
                    var selArr = selectedValue.split("|~|");
                    if(selArr.length >3){
                    		
	                        document.getElementById(formId).elements[target].value = selArr[0];
	                        document.getElementById(formId).elements['soTaskId'].value = selArr[1];
	                        document.getElementById(formId).elements['taskState'].value = selArr[2];
	                        document.getElementById(formId).elements['taskCode'].value = selArr[3];
	                                                
	                        if(selArr[2]=='requeue'){
	                        	var dateTime = getDateTime(selArr[4],selArr[5]);
	                        		document.getElementById(formId).elements['requeueDate'].disabled=false;
				                	document.getElementById(formId).elements['requeueTime'].disabled=false;
		                        var splArr = dateTime.split("|~|");
		                        if(splArr.length>1){
		                        	document.getElementById(formId).elements['requeueDate'].value=splArr[0];
		                        	document.getElementById(formId).elements['requeueTime'].value=splArr[1];
				                     
		                        }
	                        } else{
	                        	document.getElementById(formId).elements['requeueDate'].disabled=true;
			                	document.getElementById(formId).elements['requeueTime'].disabled=true;
	                        
	                        }
                     }else{
                     	
                     	document.getElementById(formId).elements[target].value = '';
                     }
                }

            }


 
 newco.jsutils.clearFilterValue = function(){
	 
	 		 
	 	var selIndex=document.getElementById('refType').selectedIndex;
	 
	 	var refType =document.getElementById('refType').options[selIndex].value;
	 	var refValue =document.getElementById('refVal').value;
		
	 	if(refType==0){
	 		var parameters = "?refType="+refType+"&refVal="+refValue;
	 		window.location.href='/MarketFrontend/pbController_execute.action'+parameters;
	 	}else{
			document.getElementById('refVal').value='';
	 	}
		
		
}

 newco.jsutils.submitFilter = function(){

	 	var selIndex=document.getElementById('refType').selectedIndex;
	 	var refType =document.getElementById('refType').options[selIndex].value;
		var refValue =document.getElementById('refVal').value;
		var searchByBuyerId = false;
		var selectedText =document.getElementById('refType').options[selIndex].text;
		if(selectedText=='Filter queues with specific buyer Id #'){
			searchByBuyerId =true;
		}
		//Changed for JIRA SLT-4454
		/*if(refType!=0 && refValue.length==0){
			alert('Please enter a valid filter value.');
			return false;
		}*/
		if(refType==0){
			document.getElementById('refVal').value='';
		}
		if(refValue=='#'){
		refValue='hash';
		}
		var parameters = "?refType="+refType+"&refVal="+refValue+"&searchByBuyerId="+searchByBuyerId;
		window.location.href='/MarketFrontend/pbController_execute.action'+parameters;
	
}


