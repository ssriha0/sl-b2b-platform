<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<html>
	<head>
		<title>ServiceLive [Manage Custom References]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<script type="text/javascript">

		var form='addRef' //Form whose checkboxes need to be changed

		function setChecked(val) {
			var dml=document.forms[form];
			var len = dml.elements.length;
			var i=0;
			for( i=0 ; i<len ; i++) {
				dml.elements[i].checked=val;
			}
		}

		function edit_ref(buyerInput, providerinput, required, searchable, buyerRefTypeId,privateInd,editable,pdfRefInd,displayNoValue)
		{
			setChecked(0);//passing in zero causes unchecking of all checkboxes.
			document.getElementById("refType").value = document.getElementById('refType'+buyerRefTypeId).value;
			document.getElementById("refDescName").value = document.getElementById('refDesc'+buyerRefTypeId).value;
			document.getElementById("refEdit").value = "true";
			document.getElementById("buyerRefTypeId").value = buyerRefTypeId;
			document.getElementById("refHeader").innerHTML = "Edit Reference Type";
			
			//check now according to what has been passed in.
			if (buyerInput == 'true'){
				document.getElementById("buyerInputChk").checked = 'true';
				document.getElementById("private").style.display = 'block';	
			}else{
				document.getElementById("private").style.display = 'none';
			}			
			if (providerinput == 'true'){				
				document.getElementById("providerInputChk").checked = 'true';				
			}
			if (required == 'true'){
				document.getElementById("requiredChk").checked = 'true';
			}
			if (searchable == 'true'){
				document.getElementById("searchableChk").checked = 'true';
			}
			if (privateInd == 'true'){
				document.getElementById("privateIndChk").checked = 'true';
			}
			if (editable == 'true'){
				document.getElementById("editableChk").checked = 'true';
			}
			if (pdfRefInd == 'true'){
				document.getElementById("pdfChk").checked = 'true';
			}
			//SL-18825
			//Code added for new attribute 'Display field if no value'
			if (displayNoValue == 'true'){
				document.getElementById("displayNoValueChk").checked = 'true';
			}
			
			document.getElementById("refType").focus();

		}

		function delete_ref(buyerRefTypeId)
		{
			if(confirm("Do you really want to delete this Reference field")  == true ){
				document.getElementById("buyerRefTypeIdDel").value = buyerRefTypeId;
				var form = document.getElementById("refList");
				form.action="manageCustomRefs_delete.action?buyerRefTypeId="+buyerRefTypeId;  
				form.submit();			
			}
		}

		//To select the private attribute on selecting Buyer Input
		function setPrivateInd(){		
			if(document.getElementById("buyerInputChk").checked == true){ 
				document.getElementById("private").style.display = 'block';				
			}else{
				document.getElementById("private").style.display = 'none';
			}
		}
		
		//To hide the private attribute on selecting Provider Input
		function hidePrivateInd(){		
			if(document.getElementById("providerInputChk").checked == true){
				if(document.getElementById("buyerInputChk").checked == false){
					document.getElementById("private").style.display = 'none';
				}				
			}
		}
		//To check for the special chars('<' and '>') - for XSS
		function checkSpecialKeys(e) {						
			var evtobj=window.event? event : e ;
			var keyVal =(window.event) ? event.keyCode : e.which;		
			if(evtobj.shiftKey){
				if (keyVal == 188 || keyVal == 190 || keyVal == 60 || keyVal == 62 ){//188 - <,190 - > --- In IE & 60 - <,62 - > --- in Mozilla
	            	return false;
	        	}else{
	            	return true;
	       		 }
			}       
	     }
	     function checkSpecialChars(e){     	    	
	     	var pattern= new RegExp("[<>{}\\[\\]\\&()]");
	     	var el = document.getElementById('refDescName');
		    if(el){
		        var commentsVal = el.value;
				var isMatch= commentsVal.match(pattern);
				if(isMatch == null){
					document.getElementById('descriptionError').style.visibility = "hidden";
					return true;
				}else{
					if(document.getElementById('descriptionError')){						
						document.getElementById('descriptionError').style.visibility = "visible";
						el.value = "";//If there is a match then clear the input.
						return false;
					}
				}		
			}
	     }  
		</script>
		<style type="text/css">
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
		</style>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/slider.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/service_order_wizard.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js" type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	      .ie7 .bannerDiv{margin-left:-1020px;}
		</style>	
	</head>
	<body class="tundra acquity">
	    
	 	<div id="page_margins">
			<div id="page">
				<div id="header">
					<tiles:insertDefinition name="newco.base.topnav" />
					<tiles:insertDefinition name="newco.base.blue_nav" />
					<tiles:insertDefinition name="newco.base.dark_gray_nav" />
				</div>
				<!-- START CENTER -->
				<div id="hpWrap" class="shadedBuyerCustomRef clearfix">
					<div id="hpContent">
						<div id="hpIntro" class="clearfix">
							<div id="descriptionError" class="errorBox clearfix" style="width: 675px; visibility:hidden;">
								<p class="errorMsg">
									&nbsp;&nbsp;&nbsp;&nbsp;Description - Enter Valid Description 
								</p>
							</div>
							<jsp:include page="/jsp/buyer_admin/validationMessages.jsp" />
							<c:if test="${message != null}">
								<p>
									${message}
								</p>
								<br>
							</c:if>
							<br/>
							<h2>
								Manage Custom References
							</h2>
							<br/>
	
							<fmt:message bundle="${serviceliveCopyBundle}" key="custom.ref.txt" />
						</div>
						<s:form id="refList" theme="simple" action="manageCustomRefs">
							<div id="hpJoin" class="hpDescribe clearfix" style="width: 700px">
								<fmt:message bundle="${serviceliveCopyBundle}"key="required.field" />
								<br/>
							<div class="scrollerTableHdr acctOverviewHdr" style="width: 700px; height: 30px;">
							<table>
								<tr align="justify">
									<td width="20"></td>
									<td width="40"></td>
									<td width="150" align="left">Custom Reference Field</td>
									<td width="55">Buyer Input</td>
									<td width="60">Provider Input</td>
									<td width="60">Required</td>
									<td width="70">Searchable</td>
									<td width="50">Private</td>
									<td width="45">Editable</td>
									<td width="60" align="center">Visible in PDF</td>
									
									<!-- SL-18825 :Code added for new attribute 'Display field if no value' -->
									<td width="50" align="center">Display if Blank</td>
								
								</tr>
							</table>
							</div>	
							<div class="grayTableContainer" style="width: 700px; height: 200px;">
								<table cellpadding="0" cellspacing="0" class="gridTable acctOverview">
									<s:iterator value="buyerRefs" status="counter">
		     							<input id="refType${buyerRefTypeId}" type="hidden"  value="${referenceType}" />
		     							<input id="refDesc${buyerRefTypeId}" type="hidden"  value="${referenceDescription}" />
		     							<c:choose>
		     							<c:when test="${counter.odd}">
			    		             		<tr align="center">
										</c:when>
							            <c:otherwise>
							                 <tr bgcolor="#ededed" align="center">
							        	</c:otherwise>
							        	</c:choose>
											<td width="20">
												<a href="#" onclick="javascript:delete_ref('<s:property value="buyerRefTypeId"/>')">
													<img alt="" src="${staticContextPath}/images/icons/iconTrash.gif"></a>
											</td>
											<td width="40">
												<a href="#" onclick="javascript:edit_ref('<s:property value="buyerInput"/>', '<s:property value="providerInput"/>','<s:property value="required"/>', '<s:property value="searchable"/>', '<s:property value="buyerRefTypeId"/>','<s:property value="privateInd"/>','<s:property value="editable"/>','<s:property value="pdfRefInd"/>','<s:property value="displayNoValue"/>');return false;">Edit</a>
											</td>
											<td width="150" align="left">${referenceType}<br>${referenceDescription}</td>
											<td width="55">
												<c:if test="${buyerInput}">
													<img alt="" src="${staticContextPath}/images/s_icons/tick.png" />
												</c:if>
											</td>
											<td width="60">
												<c:if test="${providerInput}">
													<img alt="" src="${staticContextPath}/images/s_icons/tick.png"/>
												</c:if>
											</td>
											<td width="60">
												<c:if test="${required}">
													<img alt="" src="${staticContextPath}/images/s_icons/tick.png"/>
												</c:if>
											</td>
											<td width="75">
												<c:if test="${searchable}">
													<img alt="" src="${staticContextPath}/images/s_icons/tick.png"/>
												</c:if>
											</td>
											<td width="55">
												<c:if test="${privateInd}">
													<img alt="" src="${staticContextPath}/images/s_icons/tick.png"/>
												</c:if>
											</td>
											<td width="60">
												<c:if test="${editable}">
													<img alt="" src="${staticContextPath}/images/s_icons/tick.png"/>
												</c:if>
											</td>
											<td width="60">
												<c:if test="${pdfRefInd}">
													<img alt="" src="${staticContextPath}/images/s_icons/tick.png"/>
												</c:if>
											</td>
												
												<!-- SL-18825 :Code added for new attribute 'Display field if no value' -->
											<td width="50">
												<c:if test="${displayNoValue}">
													<img alt="" src="${staticContextPath}/images/s_icons/tick.png"/>
												</c:if>
											</td>
											
										</tr>
									</s:iterator>
								</table>
							</div>	
							<input id="buyerRefTypeIdDel" type="hidden" name="buyerRef.buyerRefTypeId" value="" />					
							</div>
						</s:form>
						<s:form id="addRef" theme="simple" action="manageCustomRefs_save"> 
							<br><br><div id="refHeader">Add Reference Type</div>
							__________________________________________________________________________
							<table border="0" cellpadding="0" cellspacing="2" summary="RefAndDesc">
								<caption> </caption>
								<thead>
									<tr align="left" valign="top">
										
										<th><span class="req">*</span>Reference Type Name</th>
										
										<th>Description</th>
									</tr>
								</thead>
								<tbody>
									<tr align="left" valign="top">
										<td>
										  <tags:fieldError id="refType">
											<s:textfield name="buyerRef.referenceType" id="refType" cssStyle="width: 150px;" cssClass="shadowBox grayText" theme="simple" maxlength="50"/>
										  </tags:fieldError>										
										</td>
										<td>
											<s:textfield name="buyerRef.referenceDescription" id="refDescName" cssStyle="width: 380px;" cssClass="shadowBox grayText" theme="simple"  maxlength="50" />
										</td>
									</tr>
								</tbody>
							</table>
							<br><br>
							<tags:fieldError id="BuyerProvider">
								<s:checkbox name="buyerRef.buyerInput" id="buyerInputChk" onclick="javascript:setPrivateInd();" />
									<fmt:message bundle="${serviceliveCopyBundle}" key="custom.ref.buyer.input.txt" /><br><br>
								<s:checkbox name="buyerRef.providerInput" id="providerInputChk" onclick="javascript:hidePrivateInd();"  />
									<fmt:message bundle="${serviceliveCopyBundle}" key="custom.ref.provider.input.txt" /><br><br>
	  					    </tags:fieldError>										
							<s:checkbox name="buyerRef.required" id="requiredChk" />
								<fmt:message bundle="${serviceliveCopyBundle}" key="custom.ref.required.txt" /><br><br>
							<s:checkbox name="buyerRef.searchable" id="searchableChk" />
								<fmt:message bundle="${serviceliveCopyBundle}" key="custom.ref.searchable.txt" /><br><br>
							<s:checkbox name="buyerRef.editable" id="editableChk" />
								<fmt:message bundle="${serviceliveCopyBundle}" key="custom.ref.editable.txt" /><br><br>
							<s:checkbox name="buyerRef.pdfRefInd" id="pdfChk" />
								<fmt:message bundle="${serviceliveCopyBundle}" key="custom.ref.pdfVisible.txt" /><br><br>
								
									<!-- SL-18825 :Code added for new attribute 'Display field if no value' -->												
							<s:checkbox name="buyerRef.displayNoValue" id="displayNoValueChk" />
								<fmt:message bundle="${serviceliveCopyBundle}" key="custom.ref.display.no.value.txt" /><br><br>	
														
							<div id="private" style="display:none;"> 
								<s:checkbox name="buyerRef.privateInd" id="privateIndChk" />
									<fmt:message bundle="${serviceliveCopyBundle}" key="custom.ref.private.ind.txt" />
							</div><br><br>
							<div class="clearfix buttnNav">
								<s:submit type='image' value=""
									src="%{#request['staticContextPath']}/images/simple/button-save.png"
									theme="simple" action="manageCustomRefs_save"
									/>
							</div>
							<input id="refEdit" type="hidden" name="buyerRef.refEdit" value="false" />
							<input id="buyerRefTypeId" type="hidden" name="buyerRef.buyerRefTypeId" value="" />
						</s:form>
					</div>
				</div>
				
				<!-- START FOOTER -->
				<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
				<!-- END FOOTER -->
				
			</div>
		</div>
	</body>
</html>
