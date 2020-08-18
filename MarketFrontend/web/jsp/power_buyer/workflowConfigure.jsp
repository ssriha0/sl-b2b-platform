<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
	<jsp:param name="PageName" value="PowerBuyer"/>
</jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>ServiceLive - Configure Workflow Monitor Queues</title>
<link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/admin.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css" href="/ServiceLiveWebUtil/styles/plugins/public.css" 
			media="screen, projection">
		
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript"
			src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/animatedcollapse.js">				
		</script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/hideShow.js"></script>
		<script type="text/javascript">
					dojo.require("dijit.layout.ContentPane");
					dojo.require("dijit.layout.TabContainer");
					dojo.require("dijit.TitlePane");
		</script>
		<script type="text/javascript">
			jQuery.noConflict(); 
		</script>
		<style type="text/css">
		#overlay {
			position: fixed;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			background-color: #000;
			filter: alpha(opacity = 70);
			-moz-opacity: 0.7;
			-khtml-opacity: 0.7;
			opacity: 0.7;
			z-index: 201;
			display: none;
		}
		
		.cnt223 a {
			text-decoration: none;
		}
		
		.popup {
			width: 100%;
			margin: 0 auto;
			display: none;
			position: relative;
			z-index: 202;
		}
		
		.cnt223 {
			min-width: 600px;
			width: 600px;
			min-height: 150px;
			margin: 100px auto;
			background: #f3f3f3;
			position: relative;
			z-index: 204;
			padding: 10px;
			border-radius: 5px;
			box-shadow: 0 2px 5px #000;
		}
		
		.cnt223 p {
			clear: both;
			color: #555555;
			text-align: justify;
		}
		
		.cnt223 p a {
			color: #d91900;
			font-weight: bold;
		}
		
		.cnt223 .x {
			float: right;
			height: 35px;
			left: 22px;
			position: relative;
			top: -25px;
			width: 34px;
		}
		
		.cnt223 .x:hover {
			cursor: pointer;
		}
		.selector{
			float: right;
			width: 18px;
			background-color: darkgray;
			position: relative;
		}
		</style>
		<script type='text/javascript'>
		
		<c:if test="${initialLoad && buyerQueueConfigured}">
		jQuery(document).ready(function ($)  {
				var overlay = $('<div id="overlay"></div>');
				overlay.show();
				overlay.appendTo(document.body);
				$('.popup').show();
				$('.close').click(function() {
					$('.popup').hide();
					overlay.appendTo(document.body).remove();
					return false;
				});
				$('.selector').click(function() {
					$('.popup').hide();
					overlay.appendTo(document.body).remove();
					return false;
				});
			
			});
		</c:if>
		</script>
		<script type='text/javascript'>
		 function cTrig(clickedid) { 
		      if (document.getElementById(clickedid).checked == true) {
		        return false;
		      } else {
		       var box= confirm("Are you sure you want to unselect an existing queue? Once unselected and saved you wont be able to configure it again.");
		        if (box==true)
		            return true;
		        else
		           document.getElementById(clickedid).checked = true;
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
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="PowerBuyer - Configure Queues"/>
	</jsp:include>	
	
	<div id="page_margins">
			<div id="page">
				<!-- START HEADER -->
				<div id="headerShort">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
				</div>	

		<c:if test="${initialLoad}">
			<div class='popup'>
				<div class='cnt223'>
					<button type="button" class='selector' id='selector' onclick="">x</button>
					
					<div style="margin-top: 10px; text-align: left; margin-left: 5px; font-size: 13px">
							<b>${SERVICE_ORDER_CRITERIA_KEY.FName},&nbsp;we have something new for you!</b>	
							 
					<div style="margin-top: 10px; text-align: left; font-size: 13px">
					This page contains the Service Order Workflow Monitor Queues. The queues to which you have already configured are displayed under Existing Workflow Monitor Queues.
					</div>
		
					<div style="margin-top: 10px; text-align: left; font-size: 13px">
					We have defined a standard set of queues and we are in the process of migrating to standard queues. The standard queues are listed under Standard Workflow Monitor Queues.
					</div>
		
					<div style="margin-top: 10px; text-align: left; font-size: 13px; color: red">
					Once a queue is unselected from existing queues and saved, you wont be able to configure it again.
					</div>
						
					<div style="margin-top: 10px; text-align: left; font-size: 13px">
					Standard queues can be selected/unselected and configured anytime.
				</div>
		
			</div>	
					
					<a href='' class='close' style="margin-left: 50px; font-size: 13px;text-decoration: underline;">Continue to Workflow Monitor Queue Set Up</a>
				</div>
			</div>
		</c:if>
		
		<div class="colLeft711">
				<div class="content">
						 
					<div>		
							<c:if test="${saved}"><font color="green"><fmt:message bundle="${serviceliveCopyBundle}" key="workflow.configure_queues.save"/></font></c:if>
					</div>
						  		
					<s:form
								action="pbConfigure_save"
								id="pbConfigure_save"
								name="pbConfigure_save"
								method="post"
								enctype="multipart/form-data" theme="simple">
					
					<c:if test="${buyerQueueConfigured}">		
					<div class="grayModuleContent mainWellContent">
						
						<div class="grayModuleHdr">
									Existing Workflow Monitor Queues
						 </div>
						 <br/>	
						  	
								<p>
									<fmt:message bundle="${serviceliveCopyBundle}" key="workflow.configure_queues.instr1"/>
								</p>
						
						<c:forEach items="${wfmBuyerQueues}" var="wfmBuyerQueue" varStatus="bqIndex">
						<br>
								<img id="img_${bqIndex.count}" name="img_${bqIndex.count}"
											src="${staticContextPath}/images/icons/plusIcon.gif"
											alt="Expand" onclick="collapseExpand(${bqIndex.count -1},'buyer')" />
								<b>${wfmBuyerQueue.category}</b>
								
								<div id="div_${bqIndex.count}" style="height: auto">				
								<c:forEach items="${wfmBuyerQueue.wfmQueueVos}" var="buyerQueue">
									<br>
									&nbsp;&nbsp;&nbsp;
								
									<input id="queue_cb${buyerQueue.queueId}" type="checkbox" value="${buyerQueue.queueId}" onchange="cTrig('queue_cb${buyerQueue.queueId}')"
									       name="queue_cb${buyerQueue.queueId}" 
											<c:if test="${buyerQueue.checkInd == 'Y'}">CHECKED</c:if>
									/> 
								<label>	 ${buyerQueue.queueName} <c:if test="${not empty buyerQueue.queueDesc}">- ${buyerQueue.queueDesc}</c:if> 
								</label>
								</c:forEach>
								</div>
								
							</c:forEach>	
							</div>	
						</c:if>
						
							<div class="grayModuleContent mainWellContent">
							
							 <div class="grayModuleHdr">
									Standard Workflow Monitor Queues
						  	</div>
						  		<br/>	
								<p>
									<fmt:message bundle="${serviceliveCopyBundle}" key="workflow.configure_queues.instr2"/>
								</p>
	
							<c:forEach items="${wfmQueues}" var="wfmQueue" varStatus="qIndex">
								<br>
								<img id="img_stnd_${qIndex.count}" name="img_${qIndex.count}"
											src="${staticContextPath}/images/icons/plusIcon.gif"
											alt="Expand" onclick="collapseExpand(${qIndex.count -1},'stnd')" />
								<b>${wfmQueue.category}</b>
								
								<div id="div_stnd_${qIndex.count}" style="height: auto">				
								<c:forEach items="${wfmQueue.wfmQueueVos}" var="queue">
									<br>
									&nbsp;&nbsp;&nbsp;
								
									<input type="checkbox" value="${queue.queueId}" 
									       name="queue_stnd${queue.queueId}" 
											<c:if test="${queue.checkInd == 'Y'}">CHECKED</c:if>
									/> 
									<label> ${queue.queueName} <c:if test="${not empty queue.queueDesc}">- ${queue.queueDesc}</c:if> </label>
								</c:forEach>
								</div>
								
							</c:forEach>	
							</div>	
							
						<div class="formNavButtons" id="saveButton"> 
									
								  <s:submit type="input"
									method="save"
									cssClass="button action submit"
									theme="simple" value="Save" />			
						</div>	
					</s:form>
				</div>
				</div>
					
				</div>
				<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
		</div>
		<script language="JavaScript" type="text/javascript">
				 var collapsableDivsArray = new Array();
				 var collapsableDivsStndArray = new Array();
				 
				 for(i=0;i<${fn:length(wfmBuyerQueues)};i++){
					collapsableDivsArray[i] = new animatedcollapse('div_' +(i+1), 250, false);
				 }
				 
				 for(i=0;i<${fn:length(wfmQueues)};i++){
					 collapsableDivsStndArray[i] = new animatedcollapse('div_stnd_' +(i+1), 250, false);
				}
				
				function collapseExpand(divIndex,type){
				
				var divId = divIndex + 1;
				
				if(type == "stnd"){
					
					divObject = document.getElementById('div_stnd_'+ divId);
					collapsableDivsStndArray[divIndex].slideit();
					
					if(divObject.style.height=="0px"){
						
						document.getElementById("img_stnd_" + (divIndex + 1)).src = "${staticContextPath}/images/icons/minusIcon.gif";
						
					}else {
						document.getElementById("img_stnd_" + (divIndex + 1)).src = "${staticContextPath}/images/icons/plusIcon.gif";
					}
					
				} else {
					divObject = document.getElementById('div_'+ divId);
					collapsableDivsArray[divIndex].slideit();
					
					if(divObject.style.height=="0px"){
						
						document.getElementById("img_" + (divIndex + 1)).src = "${staticContextPath}/images/icons/minusIcon.gif";
						
					}else {
						document.getElementById("img_" + (divIndex + 1)).src = "${staticContextPath}/images/icons/plusIcon.gif";
					}
				}
				
				return;
			}
		</script>
</body>
</html>