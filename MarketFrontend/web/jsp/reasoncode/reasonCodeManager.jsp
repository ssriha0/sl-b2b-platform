<%@ page language="java"
	import="java.util.*,com.servicelive.routingrulesengine.RoutingRulesConstants"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<c:set var="maxComSize" scope="session" value="85" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>ServiceLive - Reason Code Manager</title>
		<tiles:insertDefinition name="blueprint.base.meta" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/styles/plugins/public.css"/>
		<link href="${staticContextPath}/javascript/confirm.css"
			rel="stylesheet" type="text/css" />
		
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css"/>


		
<style>
	.accordionButton {
        cursor: pointer;
     }

	.accordionContent {    
       width: 600px;
       display: none;
     }
</style>
<script	src="${staticContextPath}/javascript/jquery.simplemodal.1.4.4.min.js"
			type="text/javascript"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	      .ie7 .bannerDiv{margin-left:-1150px;}
		</style>
	</head>
	
	<body>	
		<div id="reasonCodeMgr">
			<div id="wrap" class="container">

				<tiles:insertDefinition name="blueprint.base.header" />
				<tiles:insertDefinition name="blueprint.base.navigation" />
		

				<div id="content" class="span-24 clearfix">
					<br />
					<b style="font-size: 19px; padding-left: 25px;">Manage Reason Codes</b>
					<table id="reasonCodeMgrTbl">
						<tr>
							<td width="28%"	style="vertical-align: top; padding-left: 25px; border-right: dotted;">
								<form id="addRsnForm" action="reasonCode_add.action">
								<input type="hidden" id="reasonTypeIdAdded" name ="reasonTypeIdAdded" />
									<div id="addReasonCode"	style="float: left; vertical-align: top">
										<br>
										<br>
										<div id="headingCode" style="float: left; width: 100%; padding-bottom: 12px">
											<b style="font-weight: bold; font-size: 14px;">Add Reason Codes</b>
										</div>
										<br>
										<br>
										<c:if test="${fieldErrors['rc.reasonCode'] != null || fieldErrors['rc.reasonCodeType'] != null}">
											<div id="errorMessageSpc">												
												<%@ include file="./message.jsp"%>											
											</div>
										</c:if>
										<div id="reasonCodeAdding" style="float: left; width: 100%; padding-bottom: 10px">
											<b style="font-size: small; font-weight: bold; color: threeddarkshadow;">Reason Code </b><font color="red">*</font> 
											<br />
											<textarea id="reasonComment" rows="3" cols="10" name="rc.reasonCode" 
												onkeyup="countAreaChars(this.form.reasonComment, this.form.reasonComment_leftChars, 100, event);" 
												onkeydown="countAreaChars(this.form.reasonComment, this.form.reasonComment_leftChars, 100, event);"
												onfocus="countAreaChars(this.form.reasonComment, this.form.reasonComment_leftChars, 100, event);"
												
												style="width: 190px">${code}</textarea>
											<input type="text" id="reasonComment_leftChars" name="reasonComment_leftChars" value="100" 
											   maxlength="3" size="3" readonly="readonly"/>
											Characters remaining
										</div>
			
										<div id="reasonCodeAdding" style="float: left">
										<br>
											<b style="font-size: small; font-weight: bold; color: threeddarkshadow">Type </b><font color="red">*</font> 
											<br />
											<select id="reasonSelector" name="rc.reasonCodeType"  onchange=setResonTypeId();
												style="float: left; font-size: small; height: 22px">
												<option value="-1">
													Choose Reason Type
												</option>
												<c:if test="${null != reasonTypeList && not empty reasonTypeList}">
													<c:forEach items="${reasonTypeList}" var="type">
														<option id="${type.reasonCodeType}" reasonId="${type.reasonTypeId}" value="${type.reasonCodeType}";>
															${type.reasonCodeType}
														</option>
													</c:forEach>
												</c:if>
											</select>

											<br>
											<br>
											<br>
											<input id="addButtonReasonCode" class="button action submit"
												style="text-transform: none;" type="button" value="Add" />
											</br>
										</div>
										<br />
									</div>
								</form>
							</td>

							<td width="72%" style="vertical-align: top;">					
								<c:if test="${fieldErrors['rc.reasonCode'] == null && fieldErrors['rc.reasonCodeType'] == null}">
									<div id="errorMessageSpc">
										<%@ include file="./message.jsp"%>
									</div>
								</c:if>
								<div id="reasonCodes">
									<c:set var="start" value="${fn:length(reasonTypeList)}"></c:set>										
									<c:forEach items="${reasonTypeList}" var="reasonType" begin="0" step="1" end="${start}" varStatus="totalcount">
										<c:if test = "${null != activeReasonMap[reasonType.reasonCodeType] && 
												null !=archivedReasonMap[reasonType.reasonCodeType] && 
												!(fn:length(activeReasonMap[reasonType.reasonCodeType])== 0 && 
												fn:length(archivedReasonMap[reasonType.reasonCodeType])== 0)}">	
																					
										<c:if test="${not empty reasonTypeList && null != reasonTypeList}">								
											<br/>									
											<div class="accordionButton" id="accordion${reasonType.reasonTypeId}" style="background: #58585A 
												url(${staticContextPath}/images/titleBarBg.gif);background-position: 
												right;background-repeat:no-repeat;height:25px">
											<img id="accordion${reasonType.reasonTypeId}Img" src="${staticContextPath}/images/widgets/arrowRight.gif"/>
											<b style="font-size: 14px;color:white;">&nbsp;&nbsp;<c:out value="${reasonType.reasonCodeType}"></c:out><br /></b>
											</div>
											<table style="padding-top: 1px;table-layout: fixed; width:100%" cellspacing="0" class="accordionContent" id="testt">
												
														<tr style="background-color: #DEDDDD;" >																							
															<td width="4%">
															</td>
															<td width="55%">
																<b style="font-weight: bold;">Reason Code</b>
															
															</td>
															<td width ="25%">
																<b style="font-weight: bold;">Created Date</b>
															
															</td>
															<td width="16%">
																<span id="view${reasonType.reasonTypeId}" onclick="view('1','${reasonType.reasonTypeId}');"
																	style="font-weight: bold; color: #1589FF; cursor: pointer;">
																	<u>View Archived</u></span>
																<span id="hide${reasonType.reasonTypeId}" onclick="hide('1','${reasonType.reasonTypeId}');"
																	style="display: none; font-weight: bold; color: #1589FF; cursor: pointer;">
																	<u>Hide Archived</u></span>
															</td>											
														</tr>											
											
																				
												<c:forEach items="${activeReasonMap[reasonType.reasonCodeType]}" var="activeReason" varStatus="status">											
													<tr <c:if test="${status.count%2==0}">style="background-color:#F3F4F4"</c:if>>
														<c:set var="count1" value="${status.count}"></c:set>														
														<td width="4%" id="${activeReason.reasonCodeId}" >															
															<img src="${staticContextPath}/images/widgets/cross.png" style="cursor:pointer" 
															onclick="checkInSO('${activeReason.reasonCodeId}','${activeReason.reasonCode}','${activeReason.reasonCodeType}','${reasonType.reasonTypeId}');"
															/>
														</td>
														<td width='55%'>
														<div style='width: 350px;word-wrap:break-word;font-weight: bold;'>
															${activeReason.reasonCode}
															</div>
														</td>
														<td width="25%">
														${activeReason.fmtCreatedDate}
														</td>
														<td width ="16%">
														</td>
													</tr>											
												</c:forEach>
												
												<c:if test="${count1%2==0}">
														<c:forEach items="${archivedReasonMap[reasonType.reasonCodeType]}" var="archivedReason" varStatus="stat">
															<tr <c:if test="${(stat.count%2==0 && count1%2==0) || (count1%2!=0 && stat.count%2!=0) }">
																			style="background-color:#F3F4F4;display:none;"</c:if> class="archCodes1${reasonType.reasonTypeId}" style="display:none;">															
																<td width="4%">
																
																</td>
																<td width="55%">
																<div style='width: 350px;word-wrap:break-word;font-weight: bold;font-style: italic;'>
																	${archivedReason.reasonCode} (archived on
																		<fmt:formatDate	value="${archivedReason.modifiedDate}" pattern="MM/dd/yy" />)
																	
																	</div>
																</td>
																<td width="25%">
																${archivedReason.fmtCreatedDate}
																</td>
																<td width ="16%">
																</td>
															</tr>
													</c:forEach>
												</c:if>	
											<c:if test="${count1%2!=0}">
												<c:forEach items="${archivedReasonMap[reasonType.reasonCodeType]}" var="archivedReason" varStatus="stat">
													<tr <c:if test="${stat.count%2!=0}">style="background-color:#F3F4F4;display:none;"</c:if> class="archCodes1${reasonType.reasonTypeId}" style="display:none;">															
													<td width="4%">
														
													</td>
													<td width="55%" style="word-break: break-all;word-wrap:break-word;">
													<div style='width: 350px;word-wrap:break-word;font-weight: bold;font-style: italic;'>
														${archivedReason.reasonCode} (archived on
															<fmt:formatDate	value="${archivedReason.modifiedDate}" pattern="MM/dd/yy" />)
														
													</div>
													</td>
													<td width="25%" >
														${archivedReason.fmtCreatedDate}
													</td>
													<td width ="16%">
													</td>
													</tr>
											</c:forEach>
											</c:if>																
										
									</table>								
								</c:if>
								</c:if>			
								</c:forEach>				
								</div>
							</td>
						</tr>
					</table>

					<tiles:insertDefinition name="blueprint.base.footer" />
				</div>
			</div>
		</div>
		<input id="popup" type="hidden" value=""/>
		
		<script type="text/javascript">	
		
		jQuery(document).ready(function () 
		{

			$('#reasonComment').focus();
			$("#addButtonReasonCode").click(function()
			{			
				$("#addRsnForm").submit();	
			});							  
	        	        
	        $("#reasonComment").click(function()
	        {
	        	$("#errorMessageSpc").hide();
	        });
	        
	        $("#reasonSelector").click(function()
	        {
	        	$("#errorMessageSpc").hide();
	        });
	        	        
	    jQuery.modal.defaults = {
		overlay: 50,
		overlayId: 'modalOverlay',
		overlayCss: {},
		containerId: 'modalContainer',
		containerCss: {},
		close: false,
		closeTitle: 'Close',
		closeClass: 'modalClose',
		persist: false,
		onOpen: null,
		onShow: null,
		onClose: null
		};
	      	var addedReasonId = '${reasonTypeId}';
	      
            //ACCORDION BUTTON ACTION
            $('.accordionButton').click(function() {
            	// Hide the error/success message
            	if(addedReasonId == ''){
            		$("#errorMessageSpc").hide();
            	}  		
            	var clickedTypeId = ($(this).attr("id"));
            	var ob=document.getElementById(clickedTypeId+'Img').src;
            	var isExpanded = false;
				if(ob.indexOf('arrowRight')!=-1){					
					document.getElementById(clickedTypeId+'Img').src="${staticContextPath}/images/widgets/arrowDown.gif";
				}
				
				if(ob.indexOf('arrowDown')!=-1){	
					isExpanded = true;		
					document.getElementById(clickedTypeId+'Img').src.src="${staticContextPath}/images/widgets/arrowRight.gif";
				}
				if(isExpanded==false){
					// For all others default the arrow to right
					var types = new Array();
					<c:forEach items="${reasonTypeList}" var="type" varStatus="status">
		  				types[${status.index}] = "${type.reasonTypeId}";
					</c:forEach>
					for (var type in types) {
						var typeValue = types[type];
						typeValue = 'accordion' + typeValue;
	  					if(typeValue != clickedTypeId){	
	  						if(document.getElementById(typeValue+'Img') != null){
	  							document.getElementById(typeValue+'Img').src="${staticContextPath}/images/widgets/arrowRight.gif";
							}
						}
	  				}
	  				
					$('.accordionContent').slideUp('normal');
	             	$(this).next().slideDown('normal');
				}
				addedReasonId = '';
				
            });
            
          //HIDE THE DIVS ON PAGE LOAD  
            $(".accordionContent").hide();
          
          
			if('' != addedReasonId){
				$('#'+addedReasonId).click();
			}
			
	   });
				
	function modalOpen(dialog) {
            dialog.overlay.fadeIn('fast', function() {
            dialog.container.fadeIn('fast', function() {
            dialog.data.hide().slideDown('slow');
            });
        });

 	}
  
    function modalOnClose(dialog) {
       		dialog.data.fadeOut('slow', function() {
            dialog.container.slideUp('slow', function() {
            dialog.overlay.fadeOut('slow', function() {
            jQuery.modal.close(); 
               });
           });
       });
    }
    
	function countAreaChars(areaName,counter,limit, evnt){
		if (areaName.value.length>limit) {
			areaName.value=areaName.value.substring(0,limit);
			
			//Commented to remove alert on exceeding char limit.
			//alert("The field limit is " + limit + " characters.");
		
			//Stop all further events generated (Event Bubble) for the characters user has already typed in .
			//For IE
			if (!evnt) var evnt = window.event;
			evnt.cancelBubble = true;
			//For FireFox
			if (evnt.stopPropagation) evnt.stopPropagation();
		}
		else
		counter.value = limit - areaName.value.length;
	}
	
	function setResonTypeId(){
	   var typeId= $('#reasonSelector > option:selected').attr('reasonId');	
	   var idSelector=	'accordion' + typeId;
	   $('#reasonTypeIdAdded').val(idSelector);
	}
	
	function deleteReason(action,code,id,type,typeId){
		code = code.replace(/%/g, "-prcntg-");
		code = encodeURIComponent(code);
		type = type.replace(/%/g, "-prcntg-");
		type = encodeURIComponent(type);
		window.location.replace("reasonCode_delete.action?rc.reasonCodeStatus="+action+"&code="+code+"&rc.reasonCodeId="+id+"&type="+type+"&reasonTypeId="+typeId);
	}
	
	function checkInSO(id,code,type,typeId) {
		$("#errorMessageSpc").hide();		
		document.getElementById('reasonComment').value="";
		document.getElementById('reasonSelector').options[0].selected="true";
		code = code.replace(/%/g, "-prcntg-");	
		type = type.replace(/%/g, "-prcntg-");
		code = encodeURIComponent(code);
		type = encodeURIComponent(type);
		typeId = 'accordion'+typeId;

	 	jQuery('#popupCancel').load("reasonCode_check.action?id="+id+"&code="+code+"&type="+type+"&reasonTypeId="+typeId,function(){
	 		jQuery("#popupCancel").modal({
                onOpen: modalOpen,
                onClose: modalOnClose,
                persist: true,
                containerCss: ({ width: "600px", height: "auto" })
            });
           });
        window.scrollTo(0,0);
        jQuery("#popup").val(id);           
     }
     
    function fnReturnFocus(){
    	var a=$("#popup").val();
		window.location.href = "#"+a;
		jQuery("#delete_popup").hide();
	}
    
    function view(no,type){
	    $("#errorMessageSpc").hide();
	    $("#view"+type).hide();
	   	$("#hide"+type).show();
     	$(".archCodes"+no+type).show();
     }
	        
	 function hide(no,type){
	    $("#hide"+type).hide();
	    $("#view"+type).show();
	    $(".archCodes"+no+type).hide();
	 }       
          
	</script>
	</body>
	<div id="popupCancel" class="modal"></div>
</html>
