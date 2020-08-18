<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="com.newco.marketplace.auth.SecurityContext"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="error" scope="session" value="<%=session.getAttribute("ErrorMessage")%>" />
<html>
	<head> 
	<title>ServiceLive - Manage Auto Acceptance</title>
	
	<tiles:insertDefinition name="blueprint.base.meta" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/superfish.css" media="screen, projection">
	
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/autoAcceptance.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css"/>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/public.css"/>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/confirm.css"/>	
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/toggle-btn.css"/>	
	
	<!-- icons by Font Awesome - http://fortawesome.github.com/Font-Awesome -->
	<link href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome.min.css" rel="stylesheet" />
     		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	 .ie7 .bannerDiv{margin-left:-1150px;}  
		</style>
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/toolbox.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/supersubsforautoacceptance.js"></script> 
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/superfish.js"></script>
	 
	<script type="text/javascript">

	$(document).ready(function($){
		jQuery.noConflict();
		  
		$(".carTable td:nth-child(even)").addClass("even");
		$(".carTable td:nth-child(odd)").addClass("odd");
		$(".icon").removeClass("icon-share-alt");
		$(".activeTab  .icon").addClass("icon-share-alt");
		$(".activeTab  .tabTitle").css("margin-left","0");
		
		$("input[type='checkbox']").click(function() { 
			var id = $(this).attr("name");
			var ids=id.substr(0, 3);
			if(ids=='son'){
				var ruleid = id.replace("son-off-",""); 
				var value = $("#son-"+ruleid).is(":checked"); 
				if(value == false){
					$("#"+ruleid+"_smodel").val(false);
				}else{
					$("#"+ruleid+"_smodel").val(true);
					 }		
			}
		});
		
		$(".resText").focus(function(){
	 		$(this).css("border-color","#BBBBBB");
	 		$(this).css("color","black");
			if("Please enter the reason so that we can help you." == $(this).val()){
				$(this).val("");
			}	
			$(".carTable td:nth-child(even)").css({"background" :"#EEEEEE"});
			$(".carTable td:nth-child(odd)").css({"background" :"#FFFFFF"});
			$(this).parent("div").parent('td').css({"background" :"none"});
			$(this).parent("div").parent('td').siblings("td").css({"background" :"none"});
			$(this).parent("div").parent('td').parent('tr').addClass("highlight");	
		});
	 	
	 	$(".resText").blur(function(){
	 		if($.trim($(this).val())==""){
	 			$(this).val("Please enter the reason so that we can help you.");
	 		}
			$(this).parent("div").parent('td').parent('tr').removeClass("highlight");
			$(".carTable td:nth-child(even)").css({"background" :"#EEEEEE"});
			$(".carTable td:nth-child(odd)").css({"background" :"#FFFFFF"});
	 	});
	});
	

	 	function fetch(id,name){
	 		$(".buyerDiv").removeClass("activeTab");
	 		$(".icon").removeClass("icon-share-alt");
	 		$("#"+id).addClass("activeTab");
	 		$("#"+id+" .icon").addClass("icon-share-alt");
	 		$(".tabTitle").css("margin-left","10px");
	 		$(".activeTab  .tabTitle").css("margin-left","0");
	 		$('#buyerRules').html('<center><img src="${staticContextPath}/images/loading.gif" width="500px" height="200px"/></center>');
	 		$('#buyerRules').load("manageAutoOrderAcceptanceAction_loadDetails.action?id="+id+"&name="+encodeURIComponent(name));
	 	}
	 	
	 	function quickView(ruleId){
	 		$(".ruleView").hide();
			$('#'+ruleId+'ruleView').show();
			$('#'+ruleId+'ruleView').html('<div style="overflow: hidden; height: 160px;"><center><img src="${staticContextPath}/images/loading.gif" width="500px" height="160px"/></center></div>');
			$('#'+ruleId+'ruleView').load("manageAutoOrderAcceptanceAction_displayQuickView.action?id="+ruleId);
		}
		
	 	function countAreaChars(areaName, limit, evnt) {
	 		var txt = $.trim($('#' + areaName).val());
			if (txt.length > limit) {
				txt = txt.substring(0, limit);
				$('#' + areaName).val(txt);
				if (!evnt)
					var evnt = window.event;
				evnt.cancelBubble = true;
				if (evnt.stopPropagation) {
					evnt.stopPropagation();
				}
			}
		}
		
	</script>
	<style type="text/css">
		.superfish ul li {
		width:97%;
	}
	</style>
		
</head>
	<body>
		<div id="manageAutoAccpt">
			<div id="wrap" class="container">
				<tiles:insertDefinition name="blueprint.base.header" />
				<tiles:insertDefinition name="blueprint.base.navigation" />
				<div id="content" class="span-24 clearfix">
					<br><br>
					<h2 style="margin-left: 30px;">Manage Auto Acceptance</h2>
					
					<div class="buyersDiv">
						<c:forEach items="${carBuyers}" var="buyer" varStatus="status">
							<c:forEach items="${carBuyer}" var="carBuyer" varStatus="status">
								<div id="${buyer.key}" class='buyerDiv <c:if test="${buyer.key == carBuyer.key}"> activeTab</c:if>'	onclick="fetch('${buyer.key}','${buyer.value}');" style="float:left;">
									<div style="float: left;"><i class="icon" style="font-size: 12px; color:#33CCFF;"></i>
									</div>
									<div class="tabTitle" style="float: left;padding-left: 3px;margin-left: 10px;">
										<c:choose>
											<c:when
												test="${not empty buyer.value && fn:length(buyer.value) > 20}">
												    	${fn:substring(buyer.value,	0, 20)}
											</c:when>
											<c:when test="${not empty buyer.value}">
												    	${buyer.value}
											</c:when>
										</c:choose>
									</div>
								</div>
							</c:forEach>
							<div style="height: 1px"></div>
						</c:forEach>
					</div>
					<div class="rghtDiv">
						<div id="buyerRules" style="padding-left: 20px;margin-bottom: 80px;">
							<jsp:include page="auto_acceptance_grid.jsp"/>
						</div>
						
					</div>	
					<tiles:insertDefinition name="blueprint.base.footer" />
				</div>
			</div>
		</div>
	</body>
</html>
