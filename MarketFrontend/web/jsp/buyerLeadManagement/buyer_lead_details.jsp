<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="currentMenu" scope="request" value="buyerLeadsManagement"/>	
<html>
<head>
<title>Buyer Lead Details</title>

<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/styles/plugins/superfish.css"
	media="screen, projection" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/buttons.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/styles/plugins/public.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/javascript/confirm.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/toggle-btn.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/buyerLeadManagementStyle.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/styles/plugins/ui.datepicker.css" />
         
<link
	href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome.min.css"
	rel="stylesheet" />
<link
	href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome-ie7.min.css"
	rel="stylesheet" />

<!--  <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/orderManagement.css"/>-->


<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script language="JavaScript"
	src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
<script type="text/javascript"
	src="${staticContextPath}/javascript/buyer_lead_details.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.dataTables.js"></script>
<style type="text/css">
.ie7 .bannerDiv {
	margin-left: -1050px;
}
</style>
<script language="JavaScript" type="text/javascript">
$(document).ready(function() {
	$("#omSuccessDiv").hide();
  var widgetSuccessIndicator='${widgetSuccessInd}';
  var result='${resultMessage}';
  var failInd='${failInd}';
  var updateRewardType='${updateRewardPointsType}';
	if(widgetSuccessIndicator=='addNote')
	  {
		$("#omSuccessDiv").hide();
 	$("#omSuccessDiv").html("<div  class='successBox'><p>"+"Your note was successfully added"+"</p></div>");
 	$("#orderSummaryTab").css("padding-top","28px");
    $("#omSuccessDiv").show();
	  }
	  else if(widgetSuccessIndicator=='updateReward')
	  {
		  $("#omSuccessDiv").hide();
		  if(updateRewardType==1)
			  {
	 			 $("#omSuccessDiv").html("<div  class='successBox'><p>"+"Shop Your Way Points added successfully"+"</p></div>");
			  }
		  else 
			  {
	  			$("#omSuccessDiv").html("<div  class='successBox'><p>"+"Shop Your Way Points revoked successfully"+"</p></div>");
			  }
 		$("#orderSummaryTab").css("padding-top","28px");
    $("#omSuccessDiv").show();
	  }
	  else if(widgetSuccessIndicator=='cancelLead')
	  {
		  if(failInd==0)
			  {
			 	 $("#omSuccessDiv").hide(); 
			  	$("#omSuccessDiv").html("<div  class='successBox'><p>"+result+"</p></div>");
		 		$("#orderSummaryTab").css("padding-top","28px");
		    	$("#omSuccessDiv").show();
			  }
		  else
			  {
			  	$("#omSuccessDiv").hide();
			  	$("#omSuccessDiv").html("<div  class='failureBox'><p>"+result+"</p></div>");
		 		$("#orderSummaryTab").css("padding-top","28px");
		    	$("#omSuccessDiv").show();
			  }
	   
	  }
	  else
	  {
	 $("#omSuccessDiv").hide(); 
	  }
});

function auxNavLeadDetailsTab()
{
$("#auxNav").css("z-index","-200");
}
</script>
</head>
<body class="tundra acquity" onload="auxNavLeadDetailsTab();">
	<div id="page_margins">
		<div id="page">
			<div id="header">
				<tiles:insertDefinition name="newco.base.topnav" />
				<tiles:insertDefinition name="newco.base.blue_nav" />
				<tiles:insertDefinition name="newco.base.dark_gray_nav" />

			</div>

			<div class="mainWrapper">

				<div class="summaryHead">
					<b><font color="#00A0D2"> Service Request # </font></b><span
						id="leadId">${leadsDetails.slLeadId}</span>
					
						
				</div>


				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td width="85%" class="borderRight" align="left" valign="top">
							<div class="tabContainer">
								<div class="floatL">
									<ul class="tabs">
										<li id="orderSummaryHandle"
											class="tabHandle active accordionHead"
											data-tab="orderSummaryTab">Summary</li>
										<li id="test1Handle" class="tabHandle accordionHead"
											data-tab="test1Tab">Notes</li>
										<li id="test2Handle" class="tabHandle accordionHead"
											data-tab="test2Tab">History</li>
										<li id="test3Handle" class="tabHandle accordionHead"
											data-tab="test3Tab">Attachments</li>

									</ul>
								</div>

								<div class="clear"></div>

								<!-- To display Message -->
								<div id="omSuccessDiv" style="successBox"></div>
								<div id="osErrorDiv" class="errorBox" style="display: none;height:20px; padding-top:5px; padding-left:10px; width:98%"></div>
							</div>
							

							<div id="orderSummaryTab" class="tabArea"
								style="display: block; padding-top: 5px; border: 1px solid #CCCCCC; border-top: none;">
							
								<div class="tabHeader">
									<div style="position: absolute; font-weight: bold;">Customer Information</div>
								</div>

								<div id="errorDiv" class="errorBox error clearfix"
									style="padding: 10px; width: 90%; margin-left: 5px; display: none;"> </div>

									<div id="buyerLeadCustomerInformationDiv" style="display: block;">
									<jsp:include page="buyer_lead_customer_information.jsp"/>
									</div>
								<div class="tabHeader">
									<b>Order Information</b>
								</div>


								<table id="orderTable" cellpadding="10" cellspacing="15"
									width="100%" border="0">
									<tr>
										<td width="30%" align="center" ><b>Status:</b>
										<font>${fn:toUpperCase(leadsDetails.leadStatus)}</font>
										</td>
										<td width="30%" ><b>Project Category:</b>
										<c:choose><c:when test="${not empty leadsDetails.category}">${leadsDetails.category}
						</c:when>
						<c:otherwise>Not specified </c:otherwise></c:choose>
										</td>
										<td width="40%" ><b>Created On:</b><c:choose><c:when test="${not empty leadsDetails.createdOn}">${leadsDetails.createdOn}
						</c:when>
						<c:otherwise>Not specified </c:otherwise></c:choose></td>
									</tr>
									<c:if test="${not empty leadsDetails.lmsLeadId}">
									<tr>
									<td>
											<b style="padding-right:10px">LMS Service Request #:</b><c:choose><c:when test="${not empty leadsDetails.lmsLeadId}">${leadsDetails.lmsLeadId}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose> </td>
											<td></td>
											<td></td>
									</tr>
									</c:if>
								</table>
									
									<table cellpadding="10" cellspacing="15"
									width="100%" border="0" style="position:relative;top: -15px;left:15px">
									<tr>
										<td style="width:15%" align="right"><b>Title: </b></td>
										<td style="width:80%">${leadsDetails.skill}-${leadsDetails.projectTitle}</td>
									</tr>
									<tr>
										<td style="width:15%" align="right"><b>Lead Type: </b></td>
										<td style="width:80%"><c:choose><c:when test="${not empty leadsDetails.leadType}">${leadsDetails.leadType}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose>
										<c:if test="${leadsDetails.leadType=='Competitive'}">
										(${fn:length(providerDetails)})
										</c:if></td>
									</tr>
									<tr>
										<td style="width:15%" align="right"><b>Urgency: </b></td>
										<td style="width:90%"><b><font  color="#FFA500"><c:choose><c:when test="${not empty leadsDetails.urgencyOfService}">${leadsDetails.urgencyOfService}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose></font></b></td>
									</tr>
									<tr>
										<td style="width:15%" align="right"><b>Project Details: </b></td>
										<td style="width:90%"><c:choose><c:when test="${not empty leadsDetails.secondaryProject}">${leadsDetails.secondaryProject}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose></td>
									</tr>
									<tr>
										<td style="width:15%" align="right"><b>Preferred Date: </b></td>
										<td style="width:90%"><c:choose><c:when test="${not empty leadsDetails.preferredAppointmentTime}">${leadsDetails.preferredAppointmentTime}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose></td>
									</tr>
									<tr>
									    <td style="width:15%" align="right"><b>Preferred Time: </b></td>
										<td style="width:90%;">
										<c:choose><c:when test="${not empty leadsDetails.preferredTime}">${leadsDetails.preferredTime}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose>
									</td>
									</tr>
									<tr>
										<td style="width:15%" align="right"><b>Description: </b></td>
										<td style="width:90%;"><c:choose><c:when test="${not empty leadsDetails.description}">${leadsDetails.description}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose></td>
									</tr>
									<tr>
										<td style="width:15%" align="right"><b>Lead Source: </b></td>
										<td style="width:90%"><c:choose><c:when test="${not empty leadsDetails.leadSource}">${leadsDetails.leadSource}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose></td>
									</tr>
								</table>
								
								<c:forEach items="${providerDetails}" var="providerDetail">
                               
                                   <div id="providerInfoAcc" class="accordionPage">
                                        <div class="accordionHead lastAccordionHead">
                                          <font color="#FFFFFF"><span class="arrow"></span></font> <span><font color="#FFFFFF"><b>${providerDetail.businessName} (ID#${providerDetail.providerId})</b> </span>
	                                      <div id="providerStatus" class="floatR">
                                                <b> ${providerDetail.firmStatus}</b>
                                            </div>
                                            </font>
                                            <div class="clear"></div>
                                        </div>
                                        <div class="accordionContent hide">


                                            <table cellpadding="10" cellspacing="15" width="100%" border="0">
                                                <tr>
                                                <td  width="50%">
	                                                <table cellpadding="10" cellspacing="15" width="100%" border="0">
		                                                <tr>
		                                                    <td width="100%">${providerDetail.fullName} &nbsp;&nbsp;<a href="#" onclick="openProviderFirmProfile('${providerDetail.providerId}');">View firm profile</a></td>
		                                                </tr>
		                                                <tr>
		                                                    <td width="100%"><c:choose><c:when test="${not empty providerDetail.address}">${providerDetail.address}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose></td>
		                                                </tr>
		                                                <tr>
		                                                    <td width="100%"><b>Phone: </b>
		                                                        <c:choose><c:when test="${not empty providerDetail.phoneNo}">${providerDetail.phoneNo}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose></td>
		                                                </tr>
		                                                <tr>
		                                                   <td width="100%"><b>Alternate Phone:</b><c:choose><c:when test="${not empty providerDetail.mobileNo}">${providerDetail.mobileNo}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose> </td>
		                                                 </tr>
		                                                <tr>
		                                                   <td width="100%"><b></>Email: </b><c:choose><c:when test="${not empty providerDetail.email}"><a href='mailto:${providerDetail.email}'>${providerDetail.email}</a>
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose></td>
		                                                </tr>
		                                                
		                                               	<c:if test="${providerDetail.firmStatus!='NEW'}">
		                                                <tr>
		                                                <td width="100%"><b> Status Last updated on:</b> <c:choose><c:when test="${not empty providerDetail.modifiedDate}">${providerDetail.modifiedDate}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose></td>
		                                                </tr>
		                                                </c:if>
		                                            </table>
                                                 </td>
                                              
                                                 <td width="50%">
                                                 
                                                    <table cellpadding="10" cellspacing="15" width="100%" border="0">
                                                   
                                                        <c:if test="${providerDetail.firmStatus=='CANCELLED'}">
	                                                        <tr>
																<td width="40%"align="right"><b>Cancelled On:</b></td><td width="60%" align="left">
																<c:choose><c:when test="${not empty providerDetail.cancelledOn}">${providerDetail.cancelledOn}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose></td>
															</tr>
															<tr>
																<td width="40%" align="right"><b>Cancelled by:</b></td><td width="60%"> <c:choose><c:when test="${not empty providerDetail.cancelledBy}">${providerDetail.cancelledByFirstName} &nbsp; ${providerDetail.cancelledByLastName}  
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose></td>
															</tr>
															<tr>
																<td width="40%" align="right"><b>Reason: </b></td><td width="60%"><c:choose><c:when test="${not empty providerDetail.reason}">${providerDetail.reason}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose></td>
		                                                    </tr>
	                                                    </c:if>
	                                                    
	                                                    <c:if test="${providerDetail.firmStatus=='SCHEDULED'}">
		                                                    <tr>
			                                                    <td width="40%" align="right"><b>Provider Name: </b></td>
			                                                    <td width="60%">
			                                                    <c:if test="${not empty  providerDetail.resourceId}">
			                                                    <font color="#00A0D2"><a href="#" onclick="openProviderProfile('${providerDetail.resourceId}','${providerDetail.providerId}');">${providerDetail.resourceFullName}</a></font><br/>
			                                                    User Id # <font color="#00A0D2">${providerDetail.resourceId}</font><br />  
			                                                    <c:if test="${providerDetail.starRating!=null}"><img src="${staticContextPath}/images/common/stars_<c:out value="${providerDetail.starRating}"/>.gif" border="0" /></c:if>
			                                                   </c:if>
			                                                   <c:if test="${empty  providerDetail.resourceId}">
			                                                   Not Assigned</c:if>
			                                                   </td>
		                                                    </tr>
		                                                    <tr>
		                                                    <c:if test="${not empty  providerDetail.resourceId}">
			                                                    <td width="40%" align="right">
			                                                    
			                                                    <b>Phone:</b></td><td width="60%"><c:choose><c:when test="${not empty providerDetail.resourcePhoneNo}">${providerDetail.resourcePhoneNo}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose>(Primary)<br />
			                                                   <c:choose><c:when test="${not empty providerDetail.resourceMobileNo}">${providerDetail.resourceMobileNo}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose> (Alternate)
			                                                   
			                                                    </td>
			                                                    </c:if>                                       	
		                                                    </tr>
		                                                    <tr>
		                                                     <td width="50%" align="right"><b>Appointment Scheduled On :</b></td><td width="50%"><font color="#00A0D2">
		                                                     <c:choose><c:when test="${not empty providerDetail.serviceDate}">${providerDetail.serviceDate}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose></font></td>
		                                                    </tr>
		                                                    <tr>	          
		                                                    	<td width="40%"align="right"><b></b></td><td width="60%"><font color="#00A0D2"><c:choose><c:when test="${not empty providerDetail.resourceScheduledTime}">${providerDetail.resourceScheduledTime}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose></font></td>
		                                                    </tr>	                                                    
	                                                    </c:if>
	                                                    
	                                                    <c:if test="${providerDetail.firmStatus=='COMPLETED'}">
		                                                    <tr>
			                                                    <td width="40%" align="right"><b>Assigned Provider:  </b></td>
			                                                    <td width="60%"><font color="#00A0D2"><a href="#" onclick="openProviderProfile('${providerDetail.resourceId}','${providerDetail.providerId}');">${providerDetail.resourceFullName}</a></font><br />
			                                                    User Id # <font color="#00A0D2">${providerDetail.resourceId}</font><br />  
			                                                    <c:if test="${providerDetail.starRating!=null}"><img src="${staticContextPath}/images/common/stars_<c:out value="${providerDetail.starRating}"/>.gif" border="0" /></c:if>
			                                                    </td>
			                                                </tr>
			                                                <tr>    
			                                                    <td width="40%"align="right"><b>Phone:</b></td><td width="60%"><c:choose><c:when test="${not empty providerDetail.resourcePhoneNo}">${providerDetail.resourcePhoneNo}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose>(Primary)<br />
			                                                   <c:choose><c:when test="${not empty providerDetail.resourceMobileNo}">${providerDetail.resourceMobileNo}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose>  (Alternate)
			                                                    </td>
		                                                    </tr>
		                                                    <tr>
		                                                    	<td width="40%"align="right"><b>Completed On: </b></td><td width="60%"> <c:choose><c:when test="${not empty providerDetail.completedTime}">${providerDetail.completedTime}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose></td>
		                                                    </tr>	
		                                                    
		                                                    <tr>
		                                                    	<td width="40%" align="right" ><b>Number of Trips:</b></td><td width="60%"><c:choose><c:when test="${not empty providerDetail.visits}">${providerDetail.visits}
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose> Site visits </td>
		                                                    </tr>
		                                                    
		                                                    <tr>
		                                                    	<td width="40%" align="right"><b>Amount Collected:</b></td><td width="60%">$ 
		                                                    	<c:choose><c:when test="${not empty providerDetail.leadPrice}"><c:out value="${providerDetail.leadPrice}"></c:out>
						</c:when><c:otherwise>Not specified </c:otherwise></c:choose></td>
		                                                    </tr>
	                                                    </c:if>

                                                    </table>
                                                   
                                                    
                                                </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                               
                                    </c:forEach>

								
							</div> <!--div for cancel lead popup-->
							<div id="cancelLead"
								style="width: 500px; float: left; display: none; border: 1px solid #A8A8A8; font-family: Arial;">
							</div> <!--div for add note popup-->

							<div id="addLeadNote"
								style="width: 500px; float: left; display: none; border: 1px solid #A8A8A8; font-family: Arial;">
							</div> <!--div for update reward points lead popup-->
							<div id="updateReward" style="display: none; border: 1px solid #A8A8A8; font-family: Arial;">
							</div>

							<div id="test1Tab" class="tabArea"
								style="padding-top: 10px; border: 1px solid #CCCCCC; border-top: none;">
								<div id="viewNotesTab"></div>
							</div>

							<div id="test2Tab" class="tabArea"
								style="border: 1px solid #CCCCCC; border-top: none;">
								<div id="orderHistoryTab"></div>
							</div> <!--div for attachment tab-->
							<div id="test3Tab" class="tabArea"
								style="border: 1px solid #CCCCCC; border-top: none;">
								<div id="attachmentTab"></div>
								</div>

						</td>
						<td width="15%" class="borderRight" align="center" valign="top">
							<input class="btnContainer" type="submit" value="Add Note"
							style="margin-top: 5px;"
							onclick="buyerLeadWidget('buyerLeadNote','','');" /> <c:if
								test="${leadsDetails.leadStatus=='matched'}">
								<input class="btnContainer" type="submit"
									value="Cancel This Order" style="margin-top: 5px;"
									onclick="buyerLeadWidget('cancelLead','','');" />
							</c:if> 
								<c:if test="${leadsDetails.membershipId != '' &&  leadsDetails.membershipId != null}">
									<input class="btnContainer" type="submit"
								value="Update Reward Points" style="margin-top: 5px;"
									onclick="buyerLeadWidget('updateReward','${leadsDetails.slLeadId}','${leadsDetails.membershipId}');" />
								</c:if>

							<input class="btnContainer" type="submit" value="Return To LM"
							style="margin-top: 5px;"
							onclick="buyerLeadWidget('returnToLm','','');" />

						</td>
					</tr>
				</table>
			</div>
		</div>
		<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
	</div>


	<script type="text/javascript">
ProjectNew.initializeApp();
$('#test1Handle').click(function(event){
	$("#view-lead-note-monitor_wrapper").hide();
    $("#view-lead-note-monitor_filter").hide();
    $("#view_Notes_dataTables_Scroll").hide();
    $("#view-lead-note-monitor_filter").hide();
	$("#viewNoteLoadingLogo").show();	
            var url = "${contextPath}/buyerLeadOrderSummaryController_buyerLeadViewNotes.action";
      jQuery('#viewNotesTab').load(url);
});
 $('#test2Handle').click(function(event){
		$("#orderHistContainer").hide();
		$(".viewNotesInfo").hide();
		$(".leadTableLook").hide();
		$("#leadNote").hide();
		$("#historyLoadingLogo").show();
     var leadId='${leadsDetails.slLeadId}';
              var url = "${contextPath}/buyerLeadOrderSummaryController_orderHistory.action?leadId="+leadId;
              jQuery('#orderHistoryTab').load(url,function(){
                  $("#historyLoadingLogo").hide();
				  $(".viewNotesInfo").show();
                  });
});
 
 $('#test3Handle').click(function(event){
$("#attachmentDiv").hide();
$("#attachmentLoadingLogo").show();
     var leadId='${leadsDetails.slLeadId}';
              var url = "${contextPath}/buyerLeadManagementController_getAttachmentDetails.action?leadId="+leadId;
              jQuery('#attachmentTab').load(url,function()
{
$("#attachmentDiv").show();
$("#attachmentLoadingLogo").hide();
});
});
 
$('#phone').keypress(function (e){
       if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57)){
              return false;
       }
});
 
function buyerLeadWidget(widgetId,leadId,membershipId)
{           
	          if(widgetId=="cancelLead")
              {
                           loadCancelLead();
              }
              else if(widgetId=="buyerLeadNote")
              {
                     loadAddNoteLead(leadId);
                     
              }
              else if(widgetId=="updateReward")
              {
                     loadUpdateReward(leadId,membershipId);
              }
              else{
                     var url = "${contextPath}/buyerLeadManagementController.action";
                     window.location.href=url;
              }
              
}
 
function updateLeadInfo()
{
       var url = "${contextPath}/buyerLeadManagementController.action";
       window.location.href=url;
       
}
 
</script>
<script type="text/javascript">
                     
          function fnWaitForResponseShow(){
              jQuery("#overLay").show();
              jQuery("#waitPopUp").show();
       }
              function fnWaitForResponseClose(){
              jQuery("#overLay").hide();
              jQuery("#waitPopUp").hide();
       }
              function closeModal(id) {
                     
                     $('#'+id).jqmHide();
       }
            
</script>
<script type="text/javascript">
       
           function loadCancelLead() {
        	   $("#omSuccessDiv").hide(); 
              var winH = $(window).height();
                     fnWaitForResponseShow();
                     $("#cancelLead").load(
                                                "buyerLeadManagementController_cancelLead.action",
                                                function() {
                                                       fnWaitForResponseClose();
                                                       $("#cancelLead").addClass("jqmWindow");
                                                       //$("#modalTitleBlack").html("Cancel Lead(Lead Id:"+"${leadsDetails.slLeadId}"+")");
                                                       $("#cancelLead").css("width", "515px");
                                                       $("#cancelLead").css("height", "auto");
                                                       $("#cancelLead").css("marginLeft", "-300px");
                                                       $("#cancelLead").css('top',"30%");
                                                       $("#cancelLead").jqm({
                                                              modal : true
                                                       });
                                                       $("#cancelLead").fadeIn('slow');
                                                       $('#cancelLead').css('display', 'block');
                                                       $("#cancelLead").jqmShow();
                                                }); 
           }
          
           
           function loadAddNoteLead() {
        	   $("#omSuccessDiv").hide();   
              var winH = $(window).height();
                     fnWaitForResponseShow();
                     $("#addLeadNote").load(
                                                "buyerLeadManagementController_addNoteLead.action",
                                                function() {
                                                       fnWaitForResponseClose();
                                                       $("#addLeadNote").addClass("jqmWindow");
                                                       $("#addLeadNote").css("width", "40%");
                                                       $("#addLeadNote").css("height", "auto");
                                                       $("#addLeadNote").css("marginLeft", "-300px");
                                                       $("#addLeadNote").css('top',"30%");
                                                       $("#addLeadNote").jqm({
                                                              modal : true
                                                       });
                                                       $("#addLeadNote").fadeIn('slow');
                                                       $('#addLeadNote').css('display', 'block');
                                                       $("#addLeadNote").jqmShow();
                                                }); 
           }
         //On clicking Submit button in Lead Add Note  pop up
           function submitAddNotes(){
        	   $("#omSuccessDiv").hide(); 
              if(validateAddNotes()==true){
            	 	 var leadSkill='${leadsDetails.skill}';
					 var leadCategory='${leadsDetails.category}';
					 var leadProjectType='${leadsDetails.projectTitle}';
					 var leadEmailTitle=leadCategory+" "+leadSkill+" "+leadProjectType;
                     var url = 'buyerLeadManagementController_buyerLeadAddNote.action?leadEmailTitle='+leadEmailTitle;
                     $("#addNoteLead").attr("disabled",true);
                     $("#addNoteLead").removeClass("button action");
                     $("#addNoteLead").addClass("disableLeadSubmit");                     
                     var formValues = $('#frmCancelFromSOM').serializeArray();
			$
					.ajax({
                                   url: url,
                                   type: "POST",
                                   data: formValues,
                                   dataType : "json",
                                   success: function(data) {
                                          $('#addLeadNote').jqmHide();
							var leadId = '${leadsDetails.slLeadId}';
							var url = "${contextPath}/buyerLeadOrderSummaryController.action?leadId="
									+ leadId + "&widgetSuccessInd=addNote";
							$('#addLeadNote').jqmHide();
							var pathname = window.location.pathname;
							window.location.replace(url);
                                  },
                                  error: function(xhr, status, err) {
                                   $("#addNoteLead").attr("disabled",false);
                                   $("#addNoteLead").removeClass("disableLeadSubmit");
                     		       $("#addNoteLead").addClass("button action");
                                  location.href ="${contextPath}/homepage.action";
                                  }             
                           });
              }
        }
           
           function submitUpdateRewardPoints(){
        	   $("#omSuccessDiv").hide(); 
               if(validateUpdateRewardPoints()==true){
               $("#updateRewardPointsButtonId").attr("disabled",true);
	       	$("#updateRewardPointsButtonId").removeClass("button action");
                $("#updateRewardPointsButtonId").addClass("disableLeadSubmit");
                var shopyourway = document.getElementsByName('shopyourway');
                var shopyourway_value;
                for(var i = 0; i < shopyourway.length; i++){
                    if(shopyourway[i].checked){
                    	 shopyourway_value = shopyourway[i].value;
                    }
                }
                   var url = 'buyerLeadManagementController_submitUpdateRewardPoints.action';
                   var formValues = $('#frmUpdateRewardPoints').serializeArray();
			$
					.ajax({
                         url: url,
                         type: "POST",
                         data: formValues,
                         dataType : "json",
                         success: function(data) {
                             $('#updateReward').jqmHide();
							var leadId = '${leadsDetails.slLeadId}';
							var url = "${contextPath}/buyerLeadOrderSummaryController.action?leadId="
									+ leadId + "&widgetSuccessInd=updateReward&updateRewardPointsType="+shopyourway_value;
							
							var pathname = window.location.pathname;
							window.location.replace(url);
                       },
                       error: function(xhr, status, err) {
                     			   $("#updateRewardPointsButtonId").attr("disabled",false);
		       			           $("#updateRewardPointsButtonId").removeClass("disableLeadSubmit");
                     		 	   $("#updateRewardPointsButtonId").addClass("button action");
                       //location.href ="${contextPath}/homepage.action";
							location.href = "${contextPath}/homepage.action";
							
                       }        
                   });
               }
           }
	function loadUpdateReward(leadId, membershipId) {

		membershipId = document.getElementById("membershipId").value;

		$("#omSuccessDiv").hide(); 
              var winH = $(window).height();
                     fnWaitForResponseShow();
		$("#updateReward").load(
				"buyerLeadManagementController_updateRewardPoints.action?leadId="
						+ leadId + "&membershipId=" + membershipId,
                                                function() {
                                                       fnWaitForResponseClose();
                                                       $("#updateReward").addClass("jqmWindow");
                                                       $("#updateReward").css("width","50%");
                                                       $("#updateReward").css("height","auto");
                                                       $("#updateReward").jqm({
                                                              modal : true
                                                       });
                                                       $("#updateReward").fadeIn('slow');
                                                       $('#updateReward').css('display', 'block');
                                                       $("#updateReward").jqmShow();
                                                    
                                                    
                                                }); 
           }
           
           
       
       //On clicking Submit button in Lead cancellation  pop up
       function submitCancelLead(){
    	   $("#omSuccessDiv").hide(); 
		var retval=validateCancelLead();
		if (retval != "false") {
				$("#submitCancelLead").attr("disabled",true);
				$("#submitCancelLead").removeClass("button action");
				$("#submitCancelLead").addClass("disableLeadSubmit");
			var formValues = $('#frmCancelLead ').serializeArray();
			$
					.ajax({
        	url: 'buyerLeadManagementControllerAction_leadCancellation.action',
        	type: "POST",
        	data: formValues,
			dataType : "json",
			success: function(data) {
			
					$("#cancelLead").jqmHide();
							//Code modified to avoid Client DOM Open Redirect 
							/* location.href = "${contextPath}/buyerLeadOrderSummaryController.action?leadId="
												+ '${leadsDetails.slLeadId}'
												+ "&allProvInd="+data.chkAllProviderInd
												+ "&failInd="+data.failureIndicator
												+ "&widgetSuccessInd=cancelLead"
											+ "&popup=false"; */
												
							//Code mofified to avoid Client DOM Open Redirect
							location.href = encodeURI("${contextPath}/buyerLeadOrderSummaryController.action?leadId=")
									+ encodeURIComponent('${leadsDetails.slLeadId}')
									+ "&allProvInd="+encodeURIComponent(data.chkAllProviderInd)
									+ "&failInd="+encodeURIComponent(data.failureIndicator)
									+ "&widgetSuccessInd=cancelLead"
									+ "&popup=false";
							//	$("#omSuccessDiv").html("<div  class='successBox'><p>"+"Lead Cancelled Successfully"+"</p></div>");
							//	$("#omSuccessDiv").show();
			
			},
			error: function(xhr, status, err) {
								$("#submitCancelLead").attr("disabled",false);
			          		    $("#submitCancelLead").removeClass("disableLeadSubmit");
                     		    $("#submitCancelLead").addClass("button action");
				location.href ="${contextPath}/homepage.action";
	        }
	 	});
		}
	
	}
       
              
       function formatSelectedProviderIds(rejResources){
              var checkedResources = "";
              for(var i = 0; i < rejResources.length; i++){
                     if(rejResources[i].checked) {
                           var val1= rejResources[i].id;
                           var resource_id=val1.substring(33,val1.length);
                           checkedResources+=val1+",";
                     }
              }
              return checkedResources.substring(0,checkedResources.length-1);
       }
       
       
       </script>
</body>
</html>