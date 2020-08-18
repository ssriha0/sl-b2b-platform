<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<title>Explore the ServiceLive Marketplace</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
<head>
	<style type="text/css">
		@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
		@import	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
	</style>
<link rel="stylesheet" type="text/css" 	href="${staticContextPath}/css/global.css"/>
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/main.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/tooltips.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/top-section.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/iehacks.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/sl_admin.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/service_order_wizard.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/buttons.css">
<link rel="stylesheet" type="text/css"	href="${staticContextPath}/css/dijitTabPane-serviceLive.css"/>
<link rel="stylesheet" type="text/css" 	href="${staticContextPath}/css/dijitTitlePane-serviceLive.css"/>
<link rel="stylesheet" type="text/css" 	href="${staticContextPath}/css/sears_custom.css"/>
	<style type="text/css">
	#selectedNetworkCheckboxList {width:70px; margin-top:10px;}
	#selectedNetworkCheckboxList label {padding:0;float:left; margin-bottom:5px; margin-top:1px;}
	#selectedNetworkCheckboxList input {padding:0; margin:0 2px 0 0; border:0; height:15px; width:15px; float:left; clear:left; vertical-align:top;}
	table.provSearch {width:100%;}
	input.action {-moz-border-radius-bottomleft:5px; -moz-border-radius-bottomright:5px; -moz-border-radius-topleft:5px; -moz-border-radius-topright:5px; 
background:transparent url(/ServiceLiveWebUtil/images/common/button-action-bg.png) repeat scroll 0 0;
border:2px solid #CCCCCC; color:#222222; cursor:pointer; display:block; font-family:Arial,sans-serif; font-size:11px;
font-weight:bold; padding:2px; text-align:center; text-transform:uppercase;}
input.action:hover {background:transparent url(/ServiceLiveWebUtil/images/common/button-action-hover.png) repeat scroll 0 0; color:#000000;}
	#filterAvailableProvidersID select {padding:4px 5px;}
	</style>
<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
				djConfig="isDebug: false ,parseOnLoad: true"></script>
				
			 
	<script type="text/javascript">
	    dojo.require("newco.jsutils");
		parent.dojo.require("dojo.parser");
		parent.dojo.require("dijit.Dialog");
		
	</script>	
	<script type="text/javascript">
		function doAction() {
			 newco.jsutils.displayModal('etmMsg');
			 $('etmSearchResults').action='${contextPath}/etmSearch_search.action';
			$('etmSearchResults').submit();
		}
		
		    
	    //Sort By coulumn
	function sortByColumn2(sortColumnName, statusSortForm, actionName){
 			//alert("action name "+actionName)
 			  var windowForm =  $('etmSearchResults');
             
               if(windowForm.sortColumnName.value == sortColumnName){
	               	if(windowForm.sortOrder.value == 'ASC'){
	               		windowForm.sortOrder.value = 'DESC';
	               	}
	               	else{
	               		windowForm.sortOrder.value = 'ASC';
	               	}
               }
               else
               {
               		windowForm.sortOrder.value = 'ASC';
               }
               windowForm.sortColumnName.value = sortColumnName;
               //alert(windowForm.sortColumnName.value);
                newco.jsutils.displayModal('etmMsg');
               $('etmSearchResults').action=actionName;
			   $('etmSearchResults').submit();
 			}       
		
		function sortImageFlip(sortColumnName, sortOrder){
				if (sortColumnName != "null" && sortOrder != "null"){
					  if(sortOrder == 'ASC'){
		              	$('sortBy'+sortColumnName).src = "${staticContextPath}/images/grid/arrow-up-white.gif";
		              	  
		              }
		              else{
		              	$('sortBy'+sortColumnName).src = "${staticContextPath}/images/grid/arrow-down-white.gif";
		              }
		              
		              $('sortByETMProvider').style.display = "none";
	              	  $('sortByETMTotalOrder').style.display = "none";
	              	  $('sortByETMDistance').style.display = "none";
	              	  $('sortBy'+sortColumnName).style.display = "block";
	              } 
	        }
		
	</script>
</head>
<%
	String sortColumnSession = (String)session.getAttribute("sortColumnName");
	String sortOrderSession = (String)session.getAttribute("sortOrder");
%>
<body class="tundra noBg" onload="javascript:sortImageFlip('<%=sortColumnSession%>', '<%=sortOrderSession%>');">
<!-- Display Server Side Validation Messages -->

<c:if test="${validationErrors != null && validationErrors[0] != null}">
	<div class="errorBox clearfix"
		style="width: 69%; visibility: visible; margin-left: 23px;">
		<c:forEach items="${validationErrors}" var="error">
			<p class="errorMsg">
				&nbsp;&nbsp;&nbsp;&nbsp;${error.fieldId} -
				${error.msg}
			</p>		
		</c:forEach>
	</div>
	<br>
</c:if>


<!-- NEW MODULE/ WIDGET
<div class="darkGrayModuleHdr">
	Available Providers
</div>
-->

<div  style="height:450px; margin-left:12px;">
					<div dojoType="dijit.layout.ContentPane" title="Additional Information"
					href="" class="colRight255 clearfix" preventCache="true"
					useCache="false" cacheContent="false" style="margin-top:-2px;">
					<jsp:include page="/jsp/explore_the_marketplace/widgetFilterAvailableProviders.jsp" />
					
				<div id="rightsidemodules" dojoType="dijit.layout.ContentPane" 
				 preventcache="true" usecache="false" cachecontent="false"
				class=""> </div>
				</div>		
						<jsp:include page="/jsp/explore_the_marketplace/availableProvidersTable.jsp" />
			
				<br/>
				<jsp:include page="/jsp/paging/pagingsupport.jsp">
					<jsp:param name="action" value="etmSearch_paginateResultSet.action"/>
				</jsp:include>

</div>
<form id="etmSearchResults"  name="etmSearchResults" method="post">
						<s:hidden name="skillTreeMainCat" id="skillTreeMainCat" />
						<s:hidden name="marketReadySelection" id="marketReadySelection" />
						<s:hidden name="zipCd" id="zipCd" />
						<input type="hidden" name="sortColumnName" id="sortColumnName" value="<%=sortColumnSession%>"/>
						<input type="hidden" name="sortOrder" id="sortOrder" value="<%=sortOrderSession%>" />
				</form>
</body>
</html>



