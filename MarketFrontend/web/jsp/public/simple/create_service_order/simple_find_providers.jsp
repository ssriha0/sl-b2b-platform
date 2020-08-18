<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:directive.page import="com.newco.marketplace.interfaces.OrderConstants"/>
<c:set var="editMode" scope="page" value="<%=com.newco.marketplace.interfaces.OrderConstants.EDIT_MODE %>"/>
<c:set var="createMode" scope="page" value="<%=com.newco.marketplace.interfaces.OrderConstants.CREATE_MODE %>"/>
<c:set var="copyMode" scope="page" value="<%=com.newco.marketplace.interfaces.OrderConstants.COPY_MODE %>"/>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="pageTitle" scope="request" value="Find Providers" /> <% session.putValue("simpleBuyerOverride", new Boolean(true)); %>
<c:set var="showTags" scope="request" value="1" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" /> <%-- ss: this is a Google-suggested fix for the UI issue with the star-ratings PNGs --%>
		<title>Find Providers</title>
		<link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css" />
		<!--[if lt IE 8]>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
		<![endif]-->
		<!--[if lt IE 7]>
		<style type="text/css">body{behavior:url("/ServiceLiveWebUtil/css/csshover.htc");}</style><%-- ss: this needs to be here for IE6 to display the Category dropdown --%>
		<![endif]-->
		<script language="javascript" type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="javascript" type="text/javascript" src="${staticContextPath}/javascript/plugins/hoverIntent.js"></script>
		<script language="javascript" type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.bgiframe.js"></script>
		<script language="javascript" type="text/javascript" src="${staticContextPath}/javascript/plugins/superfish.js"></script>
		<script language="javascript" type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
		<script language="javascript" type="text/javascript" src="${staticContextPath}/javascript/nav.js"></script>
		<script language="javascript" type="text/javascript">

			jQuery.noConflict();
			jQuery(document).ready(function($){
				$(".disabledResults div.sticky").fadeTo("fast", 0);
			//	$('ul.sf-menu').find('ul').bgiframe({opacity:true });

				$("ul.sf-menu").mouseover(function(){
					$('ul.sf-menu ul').show();
				});
				
				$("a#exploreMktplace").addClass('selected');

			});
			
			var redirectUrl = "homepage.action";
			var loadingImgUrl =  "${staticContextPath}/images/simple/searchloading.gif";

			function collectUrlGoGWTandRedirectBacktoOriginal(strUrl,minSelect,minTotal){
			document.getElementById('url').value=strUrl;
			var selected=0;
			var total=0;
			total=parseInt(document.getElementById('totalProv').value);
			selected=document.getElementById('countProv').value;
			if((total>=minTotal)&&(selected<=minSelect)){
			jQuery.noConflict();
		    jQuery(document).ready(function($) {
	     		$('#chooseProvider').jqm({modal:true, toTop: true});
	     		$('#chooseProvider').jqmShow();
			 });
			 }else{
			 	 redirectUrl = strUrl;
				 redirectGWT();
				 document.getElementById("csoFindProviders").action = "${contextPath}/" +strUrl;
				 document.getElementById("csoFindProviders").method = "post";
			 }
			}
			function closeChooseProvider(){
			 if(document.getElementById('choose2').checked){
				 redirectUrl = document.getElementById('url').value;
				 redirectGWT();
				 document.getElementById("csoFindProviders").action = "${contextPath}/" + document.getElementById('url').value;
				 document.getElementById("csoFindProviders").method = "post";
				 }
			
			}
			function enableSidebar(){
				jQuery(document).ready(function($){
					$(".simpleFP #hpSidebar .disabledResults").addClass('enabledResults');
					$(".disabledResults div.sticky").fadeTo("fast", 100);
					$("div.step3").fadeIn();
					$(".gwt-view-all").show();
				});	
			}
			function doVerticleClick(id, text) {
				jQuery(document).ready(function($){
					var catText = text;
					var catID = id;
					$('ul.sf-menu ul').hide();
					$("a#CatID").text(catText);
					$("input#selected_verticle").val(catID);
					$("div.step2").fadeIn();
				});
			}
			function verticleClick(id, text) {
				doVerticleClick(id,text);
				refreshVerticle(id);
				document.getElementById("contFloat").style.display="block";
			}

			function exploreTheMarketplace(buyerTypeId)
			{
				document.getElementById('buyerId').value=buyerTypeId;
			    jQuery(document).ready(function($) {
		     		$('#exploreMarketPlace').jqm({modal:true, toTop: true});
		     		$('#exploreMarketPlace').jqmShow();
				 });
			}

			function showModal() {
		    jQuery(document).ready(function($) {
	     		$('#dialog').jqm({modal:true, toTop: true});
	     		$('#dialog').jqmShow();
			 });
			}

			function setHiddenFieldsAndShowModal(name, mainCategoryId, categoryId, subCategoryId, serviceTypeTemplateId, buyerTypeId) {
				document.getElementById('mod_pop_name').value=name;
				document.getElementById('mod_mainCategoryId').value=mainCategoryId;
				document.getElementById('mod_categoryId').value=categoryId;
				document.getElementById('mod_subCategoryId').value=subCategoryId;
				document.getElementById('mod_serviceTypeTemplateId').value=serviceTypeTemplateId;
				document.getElementById('mod_buyerId').value=buyerTypeId;
				showModal();
			}
			
			function viewAllContinue(isViewAll)
			{
				if (isViewAll == 1)
				{
					//document.getElementById("show_on_view_all").style.display = "block";
				}
				else
				{
					document.getElementById("show_on_view_all").style.display = "none";
				}
			}
		</script>
</head>
<c:choose>
	<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
		<body class="tundra acquity simple" onload="${onloadFunction}">
	</c:when>
	<c:otherwise>
		<body class="tundra acquity" onload="${onloadFunction}">
	</c:otherwise>
</c:choose>
<input type="hidden" id="url" value=""/>
<div id="page_margins">
	<div id="page">
		<div id="header">
			<tiles:insertDefinition name="newco.base.topnav.findprov"/>
			<tiles:insertDefinition name="newco.base.blue_nav"/>
			<tiles:insertDefinition name="newco.base.dark_gray_nav.findprov"/>
		</div>

		<div id="hpWrap" class="shaded clearfix simpleFP">
			<div id="hpContent">
				<div id="hpWorkflow" style="height: 50px;">
					<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId != 2}"> <img src="${staticContextPath}/images/simple/anon-step1.png" alt="Step 1 of 4" /> </c:if>
				</div>

				<jsp:include page="/jsp/buyer_admin/validationMessages.jsp" />
				<h3 id="filterTitle" class="hidden"> Select the work you need done </h3>

				<s:form id="csoFindProviders" action="csoFindProviders" theme="simple">
					<div id="gwtProviderSearchTileHeader"  >
							<!-- Widget will be inserted here using GWT. -->
					</div>
					<div class="gwtsearch step1 clearfix">
						<h3><span class="alt">Step 1 of 3</span> Select the Category of Service you need.</h3>
							<ul id="nav" class="sf-menu">
							<li>
								<a id="CatID" href="#a">Category</a>
								<c:choose>
										<c:when test="${simple_service_order_key != null}">
										<c:forEach var="entry" items="${simple_service_order_key.tabDTOs}">
														<c:if test="${entry.key == 'sso_find_providers'}">
															<ul>
															<c:forEach items="${entry.value.fetchedVerticles}" var="rnode">
																<li><a class='acategory' href="javascript:verticleClick('<c:out value="${rnode.nodeId}"></c:out>','<c:out value="${rnode.nodeName}"></c:out>')" ><c:out value="${rnode.nodeName}"></c:out> </a>
																	<ul>
																		<li>
																			<div class="cat-description">
																				<p class="alt">Select this category if you are looking for:</p>
																				<c:out escapeXml="false" value="${rnode.decriptionContent}"></c:out>
																			</div>
																		</li>
																	</ul>
																</li>
															</c:forEach>
															</ul>
														</c:if>
											</c:forEach>
										</c:when>
								</c:choose>


							</li>
						</ul>
					</div>
					<div id="gwtProviderSearchTile" class="gwtFormContainer" >
							<!-- Widget will be inserted here using GWT. -->
					</div>
					<div id="gwtProviderResultHeader" class="gwtProviderResultHeader" >
								<!-- Widget will be inserted here using GWT. -->
					</div>

					<div id="gwtProviderResultTile" class="gwtFormContainer" 	>
						<!-- Widget will be inserted here using GWT. -->
					</div>
					
					  <fmt:message bundle="${serviceliveCopyBundle}" var="minimumSelect" key="selectProviderModal.minimumSelectProviders"/>
					  <fmt:message bundle="${serviceliveCopyBundle}" var="minimumTotal" key="selectProviderModal.minimumTotalProviders"/>

					<div id="show_on_view_all" style="display: none;">
					<div class="sticky widget"><br /><br />
					<strong style="font-size: 12px;">Select providers and click "Continue" to next step.</strong>
					<br /><br />
					<c:choose><c:when test="${!IS_LOGGED_IN}">
						<c:choose><c:when test="${simpleBuyer}">
							<strong>
							 <a href="javascript:collectUrlGoGWTandRedirectBacktoOriginal('csoFindProviders_next.action','${minimumSelect}','${minimumTotal}');">
								<img src="${staticContextPath}/images/buttons/continue.jpg" />
							</a></strong>
						</c:when>
						<c:otherwise>
						<br />
							<a href="javascript:collectUrlGoGWTandRedirectBacktoOriginal('buyerRegistrationAction.action','${minimumSelect}','${minimumTotal}');">
							 <img src="${staticContextPath}/images/buttons/continue.jpg" class="" />
							</a>
						</c:otherwise></c:choose>

					</c:when>
					<c:otherwise>
						<c:if test="${simpleBuyer}">
						<div>
							<a href="javascript:collectUrlGoGWTandRedirectBacktoOriginal('csoFindProviders_next.action','${minimumSelect}','${minimumTotal}');">
								<img src="${staticContextPath}/images/buttons/continue.jpg"" />
							</a>
						</div>
						<c:if test="${(appMode == editMode)  ||(appMode == copyMode)}">
							<br clear="both">
								<div class="right" style="margin-top: 5px;">
									<a href="serviceOrderMonitor.action?displayTab=Saved">
									Cancel Edit
									</a>
							</div>
						</c:if>
						</c:if>
					</c:otherwise></c:choose>
					</div>
					</div>
				</s:form>
			</div>
			<div id="hpSidebar" class="fpSecondary">
			<div id="hpTip" class="widget tips" >
			
				<div class="i44 tips disabledResults" >
					
					<p id="aboveCont" class="bgApproved" style="display: inline-block">
						<strong>Background Check Approved</strong><br />
						Every Servicelive.com provider has passed a background check required by our Fortune 500 customers, encompassing criminal, vehicle, and civil records.
					</p>
									
				</div>

		</div>

		</div>
		<div class="clear"></div>
		<small>* Background checks by PlusOne</small>
		<!-- START FOOTER -->
		<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
		<!-- END FOOTER -->
	</div>

	<div id="zipCheck" class="jqmWindow"></div>
			<div class="jqmWindow" id="dialog">
				<div class="modalHomepage">
					<a href="#" class="jqmClose">Close</a>
				</div>
				<s:form name="enterZip" action="homepage_submitZip.action" method="post" theme="simple">
					<input type="hidden" name="simpleBuyer" value="${simpleBuyer}"/>
					<div class="modalContent">
						<h2> Please enter the Zip Code of the location where work will be done.</h2>
						<s:textfield onclick="clear_enter_zip();" onkeypress="clear_enter_zip();" onblur="add_enter_zip();" name="zipCode" id="zipCode" value="Enter Zip Code" />
						<s:submit type="image" cssClass="findProv" src="%{#request['staticContextPath']}/images/simple/button-find-sp.png" />
					</div>
					<input id="mod_pop_name" type="hidden" name="popularSimpleServices[0].name" value="<s:property value='name'/>" />
					<input id="mod_mainCategoryId" type="hidden" name="popularSimpleServices[0].mainCategoryId" value="<s:property value='mainCategoryId'/>" />
					<input id="mod_categoryId" type="hidden" name="popularSimpleServices[0].categoryId" value="<s:property value='categoryId'/>" />
					<input id="mod_subCategoryId" type="hidden" name="popularSimpleServices[0].subCategoryId" value="<s:property value='sategoryId'/>" />
					<input id="mod_serviceTypeTemplateId" type="hidden" name="popularSimpleServices[0].serviceTypeTemplateId" value="<s:property value='serviceTypeTemplateId'/>" />
					<input id="mod_buyerId" type="hidden" name="popularSimpleServices[0].buyerTypeId" value="<s:property value='buyerTypeId'/>" />
				</s:form>
			</div>
		</div>
		 <div  class="jqmWindow" id="chooseProvider">
      <div class="modalHomepage"> <a href="#" class="jqmClose">Close</a> </div>
      
    		<div class="modalContent">

				<h3 align='left'>Did you know that selecting more providers will improve the chances your requested date, time and price are accepted?</h3>
				
					<br><h3 align='left'><input name="chooseType" id="choose1" type="radio" checked="checked"/><span style="color:#00B2EE">&nbsp;I'd like to select more providers.</span></h3>
					<br><h3 align='left'><input name="chooseType" id="choose2" type="radio"/><span style="color:#00B2EE">&nbsp;I'd like to continue with the provider(s) I've selected.</span></h3>
					<br><br><input type="image" align='right' src="${staticContextPath}/images/buttons/continue.jpg" id="" onclick="closeChooseProvider();" class="btnBevel jqmClose"/>
					<br><br><br>
			</div><!-- /.modalContent -->
    </div>
	</div>
  
<script type="text/javascript">
  function findX(obj)
  {
    var curleft = 0;
    if(obj.offsetParent)
        while(1) 
        {
          curleft += obj.offsetLeft;
          if(!obj.offsetParent)
            break;
          obj = obj.offsetParent;
        }
    else if(obj.x)
        curleft += obj.x;
    return curleft;
  }

  function findY(obj)
  {
    var curtop = 0;
    if(obj.offsetParent)
        while(1)
        {
          curtop += obj.offsetTop;
          if(!obj.offsetParent)
            break;
          obj = obj.offsetParent;
        }
    else if(obj.y)
        curtop += obj.y;
    return curtop;
  }

var ns = (navigator.appName.indexOf("Netscape") != -1);
var px = document.layers ? "" : "px";
function slFloatDiv(id, sx, sy)
{
	console.log(id);
	
    var el=document.getElementById(id);
 	window[id + "_obj"] = el;
      
    el.sP=function(x,y){
		this.style.left=x+px;
		this.style.top=y+px;
	};
		
    el.flt=function()
    {
 	    var aboutElement = document.getElementById("aboveCont");
 	    
		var x = findX(aboutElement);
		var y;
		if(document.documentElement.scrollTop +200 <findY(aboutElement)+aboutElement.clientHeight  )
		{
		  y = findY(aboutElement)+aboutElement.clientHeight + 20;
		}
		else  {
		  y = document.documentElement.scrollTop + 220;
		}

        this.sP(x, y );
        setTimeout(this.id + "_obj.flt()", 40);
     }
    return el;
}
slFloatDiv("contFloat",400,20).flt();
</script>	
	<script language="javascript" type="text/javascript" src="com.newco.marketplace.gwt.providersearch.SimpleProviderSearch/com.newco.marketplace.gwt.providersearch.SimpleProviderSearch.nocache.js"></script>
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			<jsp:param name="PageName" value="simple.findProviders"/>
		</jsp:include>
</body>
</html>