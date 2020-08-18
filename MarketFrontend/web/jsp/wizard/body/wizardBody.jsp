<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ page import="org.owasp.esapi.ESAPI"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="soId" scope="request" value="<%=request.getAttribute("SERVICE_ORDER_ID")%>" />
<c:set var="isSOBeingEdited" value="<%=request.getSession().getAttribute("isBeingEdited_"+request.getAttribute("SERVICE_ORDER_ID"))%>" />
<%
  String varPrevious = request.getParameter("previous");
  String varPreviousNew=ESAPI.encoder().canonicalize(varPrevious);
  String vulnPrevious=ESAPI.encoder().encodeForHTML(varPreviousNew);
  String varNext = request.getParameter("next");
  String varNextNew=ESAPI.encoder().canonicalize(varNext);
  String vulnNext=ESAPI.encoder().encodeForHTML(varNextNew);
%>
<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="wizard.wizardBody"/>
	</jsp:include>


<%-- [Back to top] button links to line below --%>
<a name="wizard_top"></a>
<!-- START TAB PANE -->
<div id="mainTabContainer" dojoType="dijit.layout.TabContainer"
	style="height: 4500px; width: 711px; margin: 0;" class="wizardTabs">
	
	<c:set var= "onProvidersTab" value="false"/>
	<c:set var= "onScopeTabCreateMode" value="false"/>
	<input type="hidden" id="isCopy" value="${actionType }"/>
	<input type="hidden" id="startCopy" id="startCopy" value="<%=request.getAttribute("startCopy") %>"/>
	
	<!-- SL-21070 -->
	<c:if test="${isSOBeingEdited == true}">
		<div id = lockEditErrorMsgDiv>
			<jsp:include page="lockEdit_errorBody.jsp" /></div>
			
	</c:if>
	
	<c:forEach var="tab" items="${tabList}">
	    <a id="${tab.className}" class="${tab.className}" 
		dojoType="dojox.layout.LinkPane"
		 href="${tab.action}?SERVICE_ORDER_ID=${soId}" selected="${tab.selected}" cacheContent="false">
			<span id="${tab.title}" class="${tab.icon}">
				${tab.title}
			</span>
		</a>
			<c:if test="${tab.className == 'tab4' && tab.selected == 'true'}">
				<c:set var= "onProvidersTab" value="true"/>
			</c:if>
			<c:if test="${tab.className != 'tab4' && tab.selected == 'true' && (actionType == 'create' || actionType == 'copy')}">
				<c:set var= "onScopeTabCreateMode" value="true"/>
			</c:if>
			<c:if test="${actionType == 'copy'}">
				<c:set var= "onScopeTabCreateMode" value="true"/>				 
			</c:if>
	</c:forEach>
</div>
<!-- END TAB PANE -->

 <c:choose>
	<c:when test="${onProvidersTab}">
		<div id="providerSearch" style="display: block;">
			<div dojoType="dijit.layout.ContentPane"
				title="Additional Information"
				href=""
				class="colRight255 clearfix"
				preventCache="true" useCache="false" cacheContent="false">
				<jsp:include page="sections/modules/filter_available_providers.jsp" />	
			</div>
			<div class="clear">
			</div>
		</div>
	</c:when>
</c:choose> 

 <div id="providerSearch1" style="display: none;">
	<div dojoType="dijit.layout.ContentPane"
		title="Additional Information"
		href=""
		class="colRight255 clearfix"
		preventCache="true" useCache="false" cacheContent="false">
		<jsp:include page="sections/modules/filter_available_providers.jsp" />	
	</div>
	<div class="clear">
	</div>
</div> 
	
<c:choose>
	<c:when test="${onScopeTabCreateMode}">
		<%-- Zipcode modal dialog code starts --%>
		<form name="zipcodeDialogCancelForm" id="zipcodeDialogCancelForm" method="post" action="soWizardScopeOfWorkCreate_cancel.action">
		<input type="hidden" id="SERVICE_ORDER_ID" name="SERVICE_ORDER_ID" value="${soId}" />
		<div dojoType="dijit.Dialog" id="zipcodeModal" title="Service Location Zip Code"
			style="display:none">
			<table ><tr><td>
			<b>Please enter the zip code of the service location:</b>
			<br/><br/>
			<div id="errLabel" style="visibility:hidden"></div>
			<input type="text" id="serviceLocationZipCode" name="serviceLocationZipCode" size="5" maxlength="5">
			<br/><br/>
			
			<input id="zipCancel" type="image" src="${staticContextPath}/images/common/spacer.gif"
				width="55" height="22"
				style="background-image: url(${staticContextPath}/images/btn/cancel.gif); float: right; padding-right: 18px;"
				class="btnBevel" onclick="zipcodeCancel()" />
			<input id="zipContinue" type="image" src="${staticContextPath}/images/common/spacer.gif"
				width="55" height="22"
				style="background-image: url(${staticContextPath}/images/btn/continue.gif); float: right; padding-right: 18px;"
				class="btnBevel" onclick="zipcodeContinue()" />
			<br/><br/>
			</td></tr></table>
		</div>
		</form>
		<form name="zipcodeDialogCancelCopyForm" id="zipcodeDialogCancelCopyForm" method="post" action="soWizardScopeOfWorkCreate_cancel.action">
		<input type="hidden" id="SERVICE_ORDER_ID" name="SERVICE_ORDER_ID" value="${soId}" />
		<div dojoType="dijit.Dialog" id="zipcodeCopyModal" title="Copy Service Order"
			style="display:none">
			<table ><tr><td>
			<input name="locType" id="sameLoc" type="radio" onClick="showZipCode()" checked="checked"/>Same Service Location
			<br/><input name="locType" id="diffLoc" type="radio" onClick="showZipCode()"/>Different Service Location
			<br/>
			
			<div id="errLabelCopy" style="visibility:hidden"></div>
			<div id="editZip" style="visibility:hidden">
			<span id="zipHelpText" style='visibility:hidden; width: 20px; height: 20px;'>Please enter the zip code of the service location:</label><br/></span>
			<input type="text" id="editZipCode" value="" maxlength="5" size="5"/>
			
			</div>
			
			
			<br/><br/>
			
			<input id="zipCancelCopy" type="image" src="${staticContextPath}/images/common/spacer.gif"
				width="55" height="22"
				style="background-image: url(${staticContextPath}/images/btn/cancel.gif); float: right; padding-right: 18px;"
				class="btnBevel" onclick="zipcodeCancel()" />
			<input id="zipContinueCopy" type="image" src="${staticContextPath}/images/common/spacer.gif"
				width="55" height="22"
				style="background-image: url(${staticContextPath}/images/btn/continue.gif); float: right; padding-right: 18px;"
				class="btnBevel" onclick="zipcodeContinueCopy()" />
			<br/><br/>
			</td></tr></table>
		</div>
		</form>
		<div id="rightSideMenu1" style="display: block;">
			<jsp:include page="rightSideMenu.jsp" />
		</div>
		<%-- Zipcode modal dialog code ends --%>
	</c:when>
	<c:otherwise>
		<c:choose>
                 	  <c:when test="${onProvidersTab}">
                        <div id="rightSideMenu1" style="display: none;">
                               <div id="rightsidemodules3" dojoType="dijit.layout.ContentPane"
                               	<c:choose>
									<c:when test="${null != groupOrderId && '' != groupOrderId}">
                                       href="soDetailsQuickLinks.action?action=edit&groupId=${groupOrderId}" class="colRight255 clearfix">
                                    </c:when>
									<c:otherwise>
										href="soDetailsQuickLinks.action?action=edit&soId=${soId}" class="colRight255 clearfix">
									</c:otherwise>
								</c:choose>
                               </div>
                       </div>
                       </c:when>
                       <c:otherwise>
                               <div id="rightSideMenu1" style="display: block;">
                                       <div id="rightsidemodules3" dojoType="dijit.layout.ContentPane"
                                       <c:choose>
											<c:when test="${null != groupOrderId && '' != groupOrderId}">
												href="soDetailsQuickLinks.action?action=edit&groupId=${groupOrderId}" class="colRight255 clearfix">
											</c:when>
											<c:otherwise>
												href="soDetailsQuickLinks.action?action=edit&soId=${soId}" class="colRight255 clearfix">
											</c:otherwise>
										</c:choose>
		</div>
                               </div>
                       
                       </c:otherwise>
               </c:choose>
	</c:otherwise>
</c:choose>

<script>
			function getSoUrl(action){
	  			
 			  	 var path=encodeURI(window.location.href);		   
  			   if(path.indexOf("soId") > -1 && path.indexOf("soWizardController.action") > -1){  			  		    	
   				  so=path.match(/soId=(.+)/)[1];
   				  var soId=so.substring(0,16);
    			  window.location.href='${contextPath}/'+action+'?soId='+soId;
    						    
   					}
    			else{
    			
    				window.location.href='${contextPath}/'+action; 	
    				}   	  	  
				}
	
	function confirmDeleteTask(idx) {
		
	    if (confirm("Are you sure you want to delete task?")) {
	        
	        window.location.href='soWizardScopeOfWorkCreate_deleteTask.action?delIndex='+idx+'&SERVICE_ORDER_ID=${soId}';
	    }
    }
    
    var previous = '<%=vulnPrevious%>';
    var next = '<%=vulnNext%>';
    var prevTab = 'tab1';
	var currtab = 'tab1';
	var chkTab = '';
	var providerTab = false;
	if (next=='null' || next==''||next==null){
	}
	else{
		prevTab = next;
	}
	
	
	dojo.addOnLoad(function(){
		showZipcodeModalDialog();
    	var w =dijit.byId("mainTabContainer");
    	dojo.connect(w, "selectChild", freeTabbingFunction);
    	
    	jQuery.noConflict();
    	jQuery(document).ready(function($) {
			$.post("dashboardPeriodicRefresh.action", function(data) {
				var availBalance = data.getElementsByTagName('available_balance')[0].childNodes.item(0).data;
				var currBalance = data.getElementsByTagName('current_balance')[0].childNodes.item(0).data;
				
				
				$('#available_balance').html(availBalance);
				$('#current_balance').html(currBalance);
			}, 'xml');
		});
  	});
  	
   	 function dummyFunc(linkPane){
  	 	  // free tab fix
  	       _actvController.allow_free_tabbing=false;
  	       
  	       if (prevTab == 'tab1' ){
  	       		chkTab = prevTab;
  	       		currtab = linkPane.id;
  	       		var url = "soWizardScopeOfWorkCreate_setDtoForTab.action?rand="+currtab+"&SERVICE_ORDER_ID=${SERVICE_ORDER_ID}";
			     if (currtab != 'tab1'){
			    	var xx =dijit.byId(currtab);
    				xx.setHref('');
    				if(document.getElementById("err")){
    					document.getElementById("err").style.display = 'none';
    				}
    			}
			    newco.jsutils.doAjaxSubmit(url,callBack, null,'soWizardScopeOfWorkCreate');
			}
	  	 	if (prevTab == 'tab2' ){
	  	 		chkTab = prevTab;
	  	 		currtab = linkPane.id;
	  	 		var url = "soWizardAdditionalInfoCreate_setDtoForTab.action?rand="+currtab+"&SERVICE_ORDER_ID=${SERVICE_ORDER_ID}";
			    if (currtab != 'tab1'){
			    	var xx =dijit.byId(currtab);
    				xx.setHref('');
    				if(document.getElementById("err")){
    					document.getElementById("err").style.display = 'none';
    				}
    			}
			    newco.jsutils.doAjaxSubmit(url,callBack, null,'soWizardAdditionalInfoCreate');
			}
	  		if (prevTab == 'tab3' ){
	  			chkTab = prevTab;
	  	 		var url = "soWizardPartsCreate_setDtoForTab.action?SERVICE_ORDER_ID=${SERVICE_ORDER_ID}&rand=" + <%=Math.random()%>;
			    currtab = linkPane.id;
			    if (currtab != 'tab1'){
			    	var xx =dijit.byId(currtab);
    				xx.setHref('');
    				if(document.getElementById("err")){
    					document.getElementById("err").style.display = 'none';
    				}
    			}
			    newco.jsutils.doAjaxSubmit(url,callBack, null,'soWizardPartsCreate');
			}
			if (prevTab == 'tab4' ){
				chkTab = prevTab;
	  	 		var url = "soWizardProvidersCreate_setDtoForTab.action?SERVICE_ORDER_ID=${SERVICE_ORDER_ID}&rand=" + <%=Math.random()%>;
			    currtab = linkPane.id;
			    if (currtab != 'tab1'){
			    	var xx =dijit.byId(currtab);
    				xx.setHref('');
    				if(document.getElementById("err")){
    					document.getElementById("err").style.display = 'none';
    				}
    			}
			    newco.jsutils.doAjaxSubmit(url,callBack, null,'soWizardProvidersCreate');
			}
			if (prevTab == 'tab5' ){
				chkTab = prevTab;
	  	 		var url = "soWizardPricingCreate_setDtoForTab.action?SERVICE_ORDER_ID=${SERVICE_ORDER_ID}&rand=" + <%=Math.random()%>;
			    currtab = linkPane.id;
			    if (currtab != 'tab1'){
			    	var xx =dijit.byId(currtab);
    				xx.setHref('');
    				if(document.getElementById("err")){
    					document.getElementById("err").style.display = 'none';
    				}
    			}
			    newco.jsutils.doAjaxSubmit(url,callBack, null,'soWizardPricingCreate');
			}
			if (prevTab == 'tab6' ){
				chkTab = prevTab;
	  	 		var url = "soWizardReviewCreate_setDtoForTab.action?SERVICE_ORDER_ID=${SERVICE_ORDER_ID}&rand=" + <%=Math.random()%>;
			    currtab = linkPane.id;
			    if (currtab != 'tab1'){
			    	var xx =dijit.byId(currtab);
    				xx.setHref('');
    				if(document.getElementById("err")){
    					document.getElementById("err").style.display = 'none';
    				}
    			}
			    newco.jsutils.doAjaxSubmit(url,callBack, null,'soWizardReviewCreate');
			}
	 }
  	 
  	 //SL-19820 adding SERVICE_ORDER_ID attribute
  	 	 function freeTabbingFunction(linkPane){
  	 	  	// free tab fix
  	 	  	currtab = linkPane.id;
  	 		changeRightSideMenu();
  	       _actvController.allow_free_tabbing=true;
  	       chkTab=currtab;
  	 		//	alert('in the freeTabbingFunction The currtab='+currtab);
  	       if (currtab == 'tab1' ){
  	       		var url = "soWizardScopeOfWorkCreate_setDtoForTab.action?SERVICE_ORDER_ID=${soId}&rand="+<%=Math.random()%>;
			    newco.jsutils.doAjaxSubmit(url,callBack, null,'soWizardScopeOfWorkCreate');
			}
	  	 	if (currtab == 'tab2' ){
	  	 		var url = "soWizardAdditionalInfoCreate_setDtoForTab.action?SERVICE_ORDER_ID=${soId}&rand="+<%=Math.random()%>;
			    newco.jsutils.doAjaxSubmit(url,callBack, null,'soWizardAdditionalInfoCreate');
			}
	  		if (currtab == 'tab3' ){
	  	 		var url = "soWizardPartsCreate_setDtoForTab.action?SERVICE_ORDER_ID=${soId}&rand=" + <%=Math.random()%>;
			    newco.jsutils.doAjaxSubmit(url,callBack, null,'soWizardPartsCreate');
			}
			if (currtab == 'tab4' ){
	  	 		var url = "soWizardProvidersCreate_setDtoForTab.action?SERVICE_ORDER_ID=${soId}&rand=" + <%=Math.random()%>;
			    newco.jsutils.doAjaxSubmit(url,callBack, null,'soWizardProvidersCreate');
			}
			if (currtab == 'tab5' ){
	  	 		var url = "soWizardPricingCreate_setDtoForTab.action?SERVICE_ORDER_ID=${soId}&rand=" + <%=Math.random()%>;
			    newco.jsutils.doAjaxSubmit(url,callBack, null,'soWizardPricingCreate');
			}
			if (currtab == 'tab6' ){
	  	 		var url = "soWizardReviewCreate_setDtoForTab.action?SERVICE_ORDER_ID=${soId}&rand=" + <%=Math.random()%>;
			    newco.jsutils.doAjaxSubmit(url,callBack, null,'soWizardReviewCreate');
			}
			currtab = linkPane.id;
		
	 }
  	 
 	 //Sl-21070
  	 function previousButton(actionName,strutform,tab){
  		 
  		 var isSOBeingEdited='${isSOBeingEdited}';
  		 var currentSoId = '${soId}';
  		 if(actionName == 'soWizardReviewCreate_createAndRoute.action' && isSOBeingEdited=='true'){
  			 //alert("isSOBeingEdited:"+isSOBeingEdited);
  			 //alert("currentSoId:"+currentSoId);
			 jQuery.ajax({
				url: 'isSOEdited.action?currentSoId='+currentSoId,
	        	type: "POST",
				dataType : "json",
				success: function(data) {
					if(data.isBeingEdited == 'false'){
						document.getElementById("lockEditErrorMsgDiv").style.visibility = "hidden";
						document.getElementById(strutform).action = actionName+'?next='+tab;
			  	  	 	document.getElementById(strutform).submit();
					}
				},
				error: function(request, status, err) {
					document.getElementById(strutform).action = actionName+'?next='+tab;
		  	  	 	document.getElementById(strutform).submit();
				}
			}); 
  		 }else{
  			document.getElementById(strutform).action = actionName+'?next='+tab;
  	  	 	document.getElementById(strutform).submit();
  		 }
  	 }
  	 function nextButton(actionName,strutform,tab){
  	 	document.getElementById(strutform).action = actionName+'?next='+tab;
  	 	document.getElementById(strutform).submit();
  	 }
  	 var callBack = function(data) {
  	   var passFailResult = newco.jsutils.handleCBData(data);
	   if (chkTab == 'tab1'){
	   	if (passFailResult.pass_fail_status == "1") {
	   		document.getElementById('Scope of Work').className = passFailResult.addtional1; 
	   	 }
	   }
	   if (chkTab == 'tab2'){
	   	if (passFailResult.pass_fail_status == "1") {
	   		document.getElementById('Additional Info').className = passFailResult.addtional1; 
	   	 }
	   }
	   if (chkTab == 'tab3'){
	   	if (passFailResult.pass_fail_status == "1") {
	   		document.getElementById('Parts').className = passFailResult.addtional1; 
	   	 }
	   }
	   if (chkTab == 'tab4'){
	   	if (passFailResult.pass_fail_status == "1") {
	   		document.getElementById('Providers').className = passFailResult.addtional1; 
	   	 }
	   }
	   if (chkTab == 'tab5'){
	   	 if (passFailResult.pass_fail_status == "1") {
	   		document.getElementById('Pricing').className = passFailResult.addtional1; 
	   	 }
	   }		
	   	chkTab = currtab;	
	   	changeRightSideMenu();
  	 }

	  // this function limits the texts in textareas and creates a counter that counts down the chars left
	 function limitCharsTextarea(textarea, limit, infodiv)
	 {
	 	var text = textarea.value;	
	 	var textlength = text.length;
	 	var info = document.getElementById(infodiv);

	 	if(textlength > limit)
	 	{
	 		info.innerHTML = '<span style="color: red;">You cannot write more then '+limit+' characters!</span>';
	 		textarea.value = text.substr(0,limit);
	 		return false;
	 	}
	 	else
	 	{
	 		info.innerHTML = '<span><i><b>'+ (limit - textlength) +'</b> characters remaining</i></span>';
	 		return true;
	 	}
	 }
	 function IsNumeric(sText)

	 {
	    var ValidChars = "0123456789.";
	    var IsNumber=true;
	    var Char;
	    for (i = 0; i < sText.length && IsNumber == true; i++) 
	       { 
	       Char = sText.charAt(i); 
	       if (ValidChars.indexOf(Char) == -1) 
	          {
	          IsNumber = false;
	          }
	       }
	    return IsNumber;
	    
	    }

	function checkQtyValue(obj)
	{
		if(!IsNumeric(obj.value) || obj.value=="" || obj.value >100 || obj.value <=0)
			obj.value = 1;   
	}
	//SL-19820 adding SERVICE_ORDER_ID attribute
  	function changeRightSideMenu(){
	   if (currtab == 'tab1'){
  	   	 var xx =dijit.byId(currtab);
  	   	 document.getElementById("providerSearch1").style.display = 'none';
  	   	 if(document.getElementById("providerSearch")){
  	   	 	document.getElementById("providerSearch").style.display = 'none';
    	 }
    	 if(document.getElementById("rightSideMenu")){
  	   	 	document.getElementById("rightSideMenu").style.display = 'block';
  	   	 	document.getElementById("rightSideMenu1").style.display = 'none';
    	 }
    	 else{
    	 	document.getElementById("rightSideMenu1").style.display = 'block';
    	 }
  	   	 xx.setHref("soWizardScopeOfWorkCreate_execute.action?SERVICE_ORDER_ID=${soId}");
    	 prevTab = currtab;
    	 return;
       }	
  	   if (currtab == 'tab2'){
  	    var xx =dijit.byId(currtab);
  	    document.getElementById("providerSearch1").style.display = 'none';
  	   
  	    if(document.getElementById("providerSearch")){
  	   	 	document.getElementById("providerSearch").style.display = 'none';
    	}
    	if(document.getElementById("rightSideMenu")){
  	   	 	document.getElementById("rightSideMenu").style.display = 'block';
  	   	 	document.getElementById("rightSideMenu1").style.display = 'none';
    	 }
    	 else{
    	 	document.getElementById("rightSideMenu1").style.display = 'block';
    	 }
    	xx.setHref("soWizardAdditionalInfoCreate_createEntryPoint.action?SERVICE_ORDER_ID=${SERVICE_ORDER_ID}");
    	prevTab = currtab;
    	return;
       }
       if (currtab == 'tab3'){
        var xx =dijit.byId(currtab);
        document.getElementById("providerSearch1").style.display = 'none';
        if(document.getElementById("providerSearch")){
  	   	 	document.getElementById("providerSearch").style.display = 'none';
    	 }
    	 if(document.getElementById("rightSideMenu")){
  	   	 	document.getElementById("rightSideMenu").style.display = 'block';
  	   	 	document.getElementById("rightSideMenu1").style.display = 'none';
    	 }
    	 else{
    	 	document.getElementById("rightSideMenu1").style.display = 'block';
    	 }
    	xx.setHref("soWizardPartsCreate_createEntryPoint.action?SERVICE_ORDER_ID=${soId}");
    	prevTab = currtab;
    	return;
       }
       if (currtab == 'tab4'){
        var xx =dijit.byId(currtab);
        document.getElementById("providerSearch1").style.display = 'block';
        if(document.getElementById("rightSideMenu")){
  	   	 	document.getElementById("rightSideMenu").style.display = 'none';
  	   	 	document.getElementById("rightSideMenu1").style.display = 'none';
    	 }
    	 else{
    	 	document.getElementById("rightSideMenu1").style.display = 'none';
    	 }
        xx.setHref("soWizardProvidersCreate_createEntryPoint.action?SERVICE_ORDER_ID=${soId}");
    	prevTab = currtab;
    	return;
       }
       if (currtab == 'tab5'){
  	   
  	    var xx =dijit.byId(currtab);
  	    document.getElementById("providerSearch1").style.display = 'none';
  	    if(document.getElementById("providerSearch")){
  	   	 	document.getElementById("providerSearch").style.display = 'none';
    	 }
    	 if(document.getElementById("rightSideMenu")){
  	   	 	document.getElementById("rightSideMenu").style.display = 'block';
  	   	 	document.getElementById("rightSideMenu1").style.display = 'none';
    	 }
    	 else{
    	 	document.getElementById("rightSideMenu1").style.display = 'block';
    	 }
    	xx.setHref("soWizardPricingCreate_createEntryPoint.action?SERVICE_ORDER_ID=${soId}");
    	prevTab = currtab;
    	return;
       }
       if (currtab == 'tab6'){
        var xx =dijit.byId(currtab);
        document.getElementById("providerSearch1").style.display = 'none';
        if(document.getElementById("providerSearch")){
  	   	 	document.getElementById("providerSearch").style.display = 'none';
    	 }
    	 if(document.getElementById("rightSideMenu")){
  	   	 	document.getElementById("rightSideMenu").style.display = 'block';
  	   	 	document.getElementById("rightSideMenu1").style.display = 'none';
    	 }
    	 else{
    	 	document.getElementById("rightSideMenu1").style.display = 'block';
    	 }
    	xx.setHref("soWizardReviewCreate_createEntryPoint.action?SERVICE_ORDER_ID=${soId}");
    	prevTab = currtab;
    	return;
       }			
  	
  	
  	}
</script>
