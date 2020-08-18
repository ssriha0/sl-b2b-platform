<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<html>
	<head>
		<style type="text/css">
	@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
	@import	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
</style>

		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/slider.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/service_order_wizard.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />

		<script type="text/javascript"
			src="${staticContextPath}/javascript/prototype.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false ,parseOnLoad: true"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/hideShow.js"></script>

		<script type="text/javascript">
		    dojo.require("newco.jsutils");
			parent.dojo.require("dojo.parser");
			parent.dojo.require("dijit.Dialog");
		</script>
		<script type="text/javascript">
		
		function doAction( action, method ) {
			    newco.jsutils.displayModal('loadingMsg${type}');
			  //alert("In ifrm"+parent.newco.jsutils.getEventsElements());
			  if(parent.newco.jsutils.getEventsElements() != null){
			  		handlePassElementsInfo(parent.newco.jsutils.getEventsElements());
			  	}
			    $('commonGridHandler').action = action+"_"+method;
				$('commonGridHandler').submit();
		}
		
		function handlePassElementsInfo( theElementArray ) {
			//alert(this.firstElement.name);
			newco.jsutils.Uvd('filterCriteriaId',theElementArray);
			parent.newco.jsutils.setEventsElements(null);
		}
		
		function showCommonMsg( theState, theMsg ){
			newco.jsutils.displayActionTileModal('',theState,theMsg);		
		}
		
		
		function submitForm(doc_id){
		
			var loadFormTest = document.getElementById('commonGridHandler');
						
			
			loadFormTest.action = '${contextPath}' + "/BuyerDocumentView?documentSelection=" + doc_id; 
			
			//alert('submitForm() docId=' + doc_id + 'action=' + loadFormTest.action);			
				try
				{
				
					loadFormTest.submit();
				} catch (error)
				{
					alert ('An error occurred while processing your document request. Check the filename and filepath.');
				}
			}
			
		function validateDocSelection(doc_id){
			var failedValidation = false;
			var exists = document.getElementById("documentSelection" + doc_id);

			if (exists){
				submitForm(doc_id);
			}else{
				document.getElementById('documentSelectionMsg').style.display = "inline";
				failedValidation = true;
				return;
			}
				
		}	
				
	</script>
	</head>
	
	<body class="tundra">

		<s:form action="spnMemberManager_showView" id="commonGridHandler">
		 <s:hidden name="type"/>
		 <s:hidden name="spnId"/>
		 <input name="filterCriteriaId" id="filterCriteriaId" type="hidden"/>
			<table class="spnTable" cellpadding="0" cellspacing="0" >
				<c:forEach items="${results}" var="row">
					<tr>
						<td class="column1">
							<input type="checkbox" name="selectedMembersList"  value="${row.spnNetworkId}" /> 
						</td>

						</td>

						<td class="column2" style="padding-left: 5px">
							${row.firstName} ${row.lastName} (ID# ${row.resourceId})
							<br />
							<b>Company ID#</b> ${row.vendorId} &nbsp;
						</td>
						<td class="column3">
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="spn.status.${row.spnStatusId}" /> &nbsp;
						</td>
						<td class="column4" style="text-align:left">
							<c:choose>
								<c:when test="${row.hasDocs}">
									<div id="docs_hidden_${row.resourceId}" style="display: block" >
										<a onclick="hideDivsEndingWith('hidden_' + ${row.resourceId}),showDivsEndingWith('shown_' + ${row.resourceId})">
											<b>view docs</b>
										</a>
									</div>
									<div id="docs_shown_${row.resourceId}" style="display: none" >
										<a onclick="hideDivsEndingWith('shown_' + ${row.resourceId}), showDivsEndingWith('hidden_' + ${row.resourceId})">
											<b>hide docs</b>
										</a>
										<br>
										<ul>
											<c:forEach items="${row.documents}" var="doc">
												<input type="hidden" name="documentSelection${doc.documentId}" id="documentSelection${doc.documentId}"
													value="${doc.documentId}" />
												<li>
													<a href="javascript:validateDocSelection(${doc.documentId})">
														${doc.fileName} </a>
												</li>
											</c:forEach>
										</ul>		
									</div>
									&nbsp;
								</c:when>
								<c:otherwise>
									N/A
								</c:otherwise>
							</c:choose>
						</td>
						<td class="column5">
							${row.servideOrdersCompleted} &nbsp;
						</td>
					</tr>
				</c:forEach>
			</table>
		</s:form>
			<jsp:include page="/jsp/paging/pagingsupport.jsp"></jsp:include>
		<div dojoType="dijit.Dialog" id="loadingMsg${type}" title="Please Wait">
			We are refreshing your results.
		</div>
	</body>
</html>
