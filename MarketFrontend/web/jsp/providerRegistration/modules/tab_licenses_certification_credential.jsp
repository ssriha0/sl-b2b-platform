<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

		
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/main.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/buttons.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/styles/plugins/buttons.css" />

<link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery.modal.min.css" type="text/css"></link>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/formfields.js"></script>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", -1);
%>
<style>
	.simplemodal-container{
	background-color: #FFFFFF;
	}

/* Calendar*/

.dijitCalendarIncrementControl {
	/* next/prev month buttons */
	width:16px;
	height:16px;
}
.dijitCalendarIncrementControl {
	padding:.1em;
}

.dijitCalendarIncreaseInner,
.dijitCalendarDecreaseInner {
	visibility:hidden;
}

.dijitCalendarDecrease {
	background:url("${staticContextPath}/javascript/dojo/dijit/themes/tundra/images/arrowLeft.png") no-repeat center center;
}
.dijitCalendarDecrease {
	background-image:url("${staticContextPath}/javascript/dojo/dijit/themes/tundra/images/arrowLeft.gif");
}

.dijitCalendarIncrease {
	background:url(${staticContextPath}/javascript/dojo/dijit/themes/tundra/images/arrowRight.png) no-repeat center center;
}
.dijitCalendarIncrease {
	background-image:url("${staticContextPath}/javascript/dojo/dijit/themes/tundra/images/arrowRight.gif");
}

.dijitCalendarContainer  {
	font-size: 100%;
	border-collapse: collapse;
	border-spacing: 0;
	border: 1px solid #ccc;
	margin: 0;
}

.dijitCalendarMonthContainer th {
	/* month header cell */
	background:white url("${staticContextPath}/javascript/dojo/dijit/themes/tundra/images/calendarMonthLabel.png") repeat-x top;
	padding-top:.3em;
	padding-bottom:.1em;
	text-align:center;
}
.dijitCalendarMonthContainer th {
	padding-top:.1em;
	padding-bottom:0em;
}

.dijitCalendarDayLabelTemplate {
	/* day of week labels */
	background:white url("${staticContextPath}/javascript/dojo/dijit/themes/tundra/images/calendarDayLabel.png") repeat-x bottom;
	font-weight:normal;
	padding-top:.15em;
	padding-bottom:0em;
	border-top: 1px solid #eeeeee;
	color:#293a4b;
	text-align:center;
}

.dijitCalendarMonthLabel {
	/* day of week labels */
	color:#293a4b;
	font-size: 0.75em;
	font-weight: bold;
	text-align:center;
}

.dijitCalendarDateTemplate,
.dijitCalendarDateTemplate {
	font-size: 0.8em;
}

.dijitCalendarDateTemplate {
	/* style for each day cell */
	font-size: 0.9em;
	font-weight: bold;
	text-align: center;
	padding: 0.3em 0.3em 0.05em 0.3em;
	letter-spacing: 1px;
}


.dijitCalendarPreviousMonth,
.dijitCalendarNextMonth 		{
	/* days that are part of the previous or next month */
	color:#999999;
	background-color:#f8f8f8 !important;
}

.dijitCalendarPreviousMonthDisabled,
.dijitCalendarNextMonthDisabled	{
	/* days that are part of the previous or next month - disabled*/
	background-color:#a4a5a6 !important;
}

.dijitCalendarCurrentMonth {
	/* days that are part of this month */
	background-color:white !important;
}

.dijitCalendarCurrentMonthDisabled {
	/* days that are part of this month - disabled */
	background-color:#bbbbbc !important;
}

.dijitCalendarDisabledDate {
	/* one or the other? */
	/* background: url(images/noX.gif) no-repeat center center !important; */
	text-decoration:line-through !important;
	cursor:default !important;
}

.dijitCalendarCurrentDate {
	/* cell for today's date */
	text-decoration:underline;
	font-weight:bold;
}

.dijitCalendarSelectedDate {
	/* cell for the selected date */
	background-color:#bbc4d0 !important;
	color:black !important;
}


.dijitCalendarYearContainer {
	/* footer of the table that contains the year display/selector */
	background:white url("${staticContextPath}/javascript/dojo/dijit/themes/tundra/images/calendarYearLabel.png") repeat-x bottom;
	border-top:1px solid #ccc;
}

.dijitCalendarYearLabel {
	/* container for all of 3 year labels */
	margin:0;
	padding:0.4em 0 0.25em 0;
	text-align:center;
}

.dijitCalendarSelectedYear {
	/* label for selected year */
	color:black;
	padding:0.2em;
	padding-bottom:0.1em;
	background-color:#bbc4d0 !important;
}

.dijitCalendarNextYear,
.dijitCalendarPreviousYear {
	/* label for next/prev years */
	color:black !important;
	font-weight:normal;
}
</style>
<script type="text/javascript"
	src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
	djConfig="isDebug: false, parseOnLoad: true"></script>
<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit.Dialog");
			dojo.require("dijit._Calendar");
			dojo.require("dojo.date.locale");
			dojo.require("dojo.parser");
			dojo.require("dijit.form.DateTextBox");
			dojo.require("dijit.layout.LinkPane");
			dojo.require("newco.jsutils");
			//dojo.require("newco.servicelive.SOMRealTimeManager");
			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
			
			var contextPath = '${pageContext.request.contextPath}';
			// adduser_all_tabs.file
			newco.jsutils.setGlobalContext('${contextPath}');
			newco.jsutils.setGlobalStaticContext('${staticContextPath}');
			
	function removeCred()
	{
		jQuery("#declinemodal").css("background-color","#FFFFFF");
		jQuery("#declinemodal").modal({
  			 onOpen: modalOpenAddCustomer,
  			 onClose: modalOnClose,
   			persist: true,
  			 	containerCss: ({ width: "625px", height: "140px", top: "750px" ,border: "3px solid lightgrey"})
		});
		jQuery(".modalContainer").children("a.modalClose").removeClass("modalCloseImg");
		
	}	

	function modalOpenAddCustomer(dialog) {
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

</script>

	<c:if test="${sessionScope.tabDto.tabStatus['Credentials'] != null}">
		<tags:security actionName="auditAjaxAction">
			<%@ include file="/jsp/auditor/commonServiceProCredentialApprovalWidget.jsp"%>
		</tags:security>
	</c:if>
	
		
	<s:form action="teamCredentialActionloadCredentialDetails"
		theme="simple" enctype="multipart/form-data">
			<s:hidden name="teamCredentialsDto.resourceCredId" />
			<s:hidden name="teamCredentialsDto.resourceId" />
			<s:hidden name="teamCredentialsDto.size" />

				<!-- START HEADER -->
				<div id="content_right_header_text">
					<%@ include file="../message.jsp"%>
				</div>
				<div class="warningBox clearfix" width="100%" style="height:auto; display:none;" id="widgetWarningMessageID">
        			<p class="warningMsg contentWellPane">
              			<img src="${staticContextPath}/images/response_icon_yellow.gif" />              		              	
        				<span id="warningMsgPara"></span>	
        			</p>		
				</div>		
				<h3 class="paddingBtm">
					<s:property value="teamCredentialsDto.fullResoueceName"/>
				</h3>
				<!-- END HEADER -->
<p><b>An asterix (<span class="req">*</span>) indicates a required field</b></p> <br/>
				<div class="darkGrayModuleHdr">
					Licenses &amp; Certification Information
				</div>
				<div class="grayModuleContent mainWellContent clearfix">
					<p>
						Where were you issued your certification and when will it expire?
						Any confidential information that you share will
						<br />
						be used for verification purposes only.
					</p>
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td width="220">
								<c:choose>
								<c:when test="${fieldErrors['teamCredentialsDto.typeId'] == null}">
									<p class="paddingBtm">
								</c:when>
								<c:otherwise>
									<p class="errorBox">
								</c:otherwise>
								</c:choose>
								Type of Credential<span class="req">*</span>
								<br />
								<s:select name="teamCredentialsDto.typeId" id="teamCredentialsDto.typeId"
									value="%{teamCredentialsDto.typeId}"
									list="teamCredentialsDto.credentialTypeList" headerKey=""
									headerValue="--Select One--" listKey="typeId" listValue="type"
									onchange="javascript:changeTypeOfCredential();">
								</s:select>
								</p>
							</td>
							<td>
								<c:choose>
								<c:when
									test="${fieldErrors['teamCredentialsDto.categoryId'] == null}">
									<p class="paddingBtm">
								</c:when>
								<c:otherwise>
									<p class="errorBox">
								</c:otherwise>
								</c:choose>
								Category<span class="req">*</span>
								<br />
								<s:select name="teamCredentialsDto.categoryId" id="teamCredentialsDto.categoryId"
									value="%{teamCredentialsDto.categoryId}"
									list="teamCredentialsDto.credentialCatList" headerKey=""
									headerValue="--Select One--" listKey="catId"
									listValue="category" onchange="changeCaptionForCredentialNumber()">
								</s:select>
								</p>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<c:choose>
								<c:when
									test="${fieldErrors['teamCredentialsDto.licenseName'] == null}">
									<p class="paddingBtm">
								</c:when>
								<c:otherwise>
									<p class="errorBox">
								</c:otherwise>
								</c:choose>
								Name of License or Certification<span class="req">*</span>
								<br />
								<s:textfield name="teamCredentialsDto.licenseName"
									value="%{teamCredentialsDto.licenseName}" maxlength="150"
									size="60" theme="simple" cssStyle="width: 300px;" cssClass="shadowBox grayText"></s:textfield>
								</p>

							</td>
						</tr>
						<tr>
							<td width="221">
								<p>
								Issuer of Credential
								<br />
								<s:textfield name="teamCredentialsDto.issuerName"
									value="%{teamCredentialsDto.issuerName}" maxlength="100"
									theme="simple" cssStyle="width: 150px;" cssClass="shadowBox grayText"></s:textfield>
								</p>
							</td>
								<td width="222">
								<c:choose>
								<c:when
									test="${fieldErrors['teamCredentialsDto.credentialNumber'] == null}">
									<p class="paddingBtm">
								</c:when>
								<c:otherwise>
									<p class="errorBox">
								</c:otherwise>
								</c:choose>
                				<s:if test="%{teamCredentialsDto.typeId == 3 && teamCredentialsDto.categoryId == 24}">
                   					 Credential Number<span id="credentialNumberReqd" class="req">*</span><span id="credentialNumber" style="visibility:hidden">(If applicable)</span>
                				</s:if>
								<s:else>
                    				 Credential Number<span id="credentialNumberReqd" class="req" style="visibility:hidden">*</span><span id="credentialNumber">(If applicable)</span>														
				        		</s:else>		 						
								<br />
									<s:textfield name="teamCredentialsDto.credentialNumber" id="teamCredentialsDto.credentialNumber" 
										value="%{teamCredentialsDto.credentialNumber}" maxlength="20"
										theme="simple" cssStyle="width: 150px;" cssClass="shadowBox grayText"></s:textfield>
								</p>
							</td>
						</tr>
						<tr>
							<td width="221">
								<p>
									City (If applicable)
									<br />
									<s:textfield name="teamCredentialsDto.city"
										value="%{teamCredentialsDto.city}" maxlength="50"
										theme="simple" cssStyle="width: 150px;" cssClass="shadowBox grayText"></s:textfield>
								</p>
							</td>
							<td>
								<p>
									State (If applicable)
									<br />
									<s:select name="teamCredentialsDto.state"
										value="%{teamCredentialsDto.state}"
										list="#application['stateCodes']" headerKey=""
										headerValue="--Select One--" listKey="type" listValue="descr">
									</s:select>
								</p>
							</td>
						</tr>
						<tr>
							<td width="221">
								<p>
									County (If applicable)
									<br />
									<s:textfield name="teamCredentialsDto.county"
										value="%{teamCredentialsDto.county}" maxlength="20"
										theme="simple" cssStyle="width: 150px;" cssClass="shadowBox grayText"></s:textfield>
								</p>
							</td>
						</tr>
						<tr>
							<td width="221">
							<c:choose>
								<c:when
									test="${fieldErrors['teamCredentialsDto.issueDate'] == null}">
									<p class="paddingBtm">
								</c:when>
								<c:otherwise>
									<p class="errorBox">
								</c:otherwise>
							</c:choose>
								Issue Date<span class="req">*</span>
								<br />

								<input type="text" name="teamCredentialsDto.issueDate"
									dojoType="dijit.form.DateTextBox" 
									constraints="{datePattern:'MM/dd/yyyy'}"
									class="shadowBox"
									id="modal2ConditionalChangeDate1" maxlength="10"
									value="<s:property value="%{teamCredentialsDto.issueDate}"/>"   theme="simple" cssStyle="width: 80px;" cssClass="shadowBox grayText"/>
								</p>
							</td>

							<td>
								<c:choose>
								<c:when
									test="${fieldErrors['teamCredentialsDto.expirationDate'] == null}">
									<p class="paddingBtm">
								</c:when>
								<c:otherwise>
									<p class="errorBox">
								</c:otherwise>
								</c:choose>
								Expiration Date (if applicable)
								<br />
								<input type="text" dojoType="dijit.form.DateTextBox"
									constraints="{datePattern:'MM/dd/yyyy'}"
									class="shadowBox" id="modal2ConditionalChangeDate2"
									name="teamCredentialsDto.expirationDate"
									value="<s:property value="%{teamCredentialsDto.expirationDate}"/>"   theme="simple" cssStyle="width: 80px;" class="shadowBox grayText"/>
								</p>


							</td>
						</tr>
					</table>
				</div><!-- end of grayModuleContent mainWellContent clearfix -->
				<div class="darkGrayModuleHdr">
					Attach Credential Document
				</div>
				<div class="grayModuleContent mainWellContent clearfix">
				    <p>
						Please attach an electronic copy of the credential, if available.
						Credential details will be used for verification purposes<br/>
						and will not be posted online. You may upload a maximum of one
						file per license or certification. File size should be <br/>
						limited to 2 MB.
					</p>
				 <div>
					<div style="float: left;">
						<p>
							<label>
								Select file to upload
							</label>
							<br />
							<input type="file" class="shadowBox grayText"
								style="width: 300px;" name="teamCredentialsDto.file"
								class="btnBevel uploadBtn" />
							&nbsp;&nbsp;
							<input type="image"
								src="${staticContextPath}/images/images_registration/common/spacer.gif"
								width="72" height="20"
								style="background-image: url(${staticContextPath}/images/images_registration/btn/attach.gif); margin-bottom: -6px"
								class="btn20Bevel inlineBtn"
								onclick="javascript:attachDocument();" />
						 </p>
				<br clear="all" />
				<div class="filetypes"
					style="margin-right: 120px; _margin-right: 60px;">
					<p>
						&nbsp;
						<br />
					</p>
				</div>
				<br clear="all" />
				<div class="filetypes">
					<p>
						Preferred file types include:
						<br />
						.JPG | .PDF | .DOC | .GIF
						</br>
					</p>
				</div>
				
				   <s:if test="%{teamCredentialsDto.resourceCredId > 1}">
							<s:if test="%{teamCredentialsDto.credentialDocumentId > 0}">
								<table border=1 style="width: 350px;" cellpadding="0"
									cellspacing="0">
									<tr align="center"
										style="color: #fff; font-weight: bold; background: #4CBCDF; height: 23px;">
										<td>
											File Name
										</td>
										<td>
											File Size
										</td>
									</tr>
									<tr style="height: 23px;" align="center">
										<td class="column2">
											&nbsp;&nbsp;&nbsp;
											<s:property
												value="teamCredentialsDto.credentialDocumentFileName" />
										</td>
										<td class="column3">
											&nbsp;&nbsp;&nbsp;
											<s:property
												value="teamCredentialsDto.credentialDocumentFileSize" />
										</td>
									</tr>
								</table>
							</s:if>
						</s:if>
						<br/>
						<s:if test="%{teamCredentialsDto.resourceCredId > 1}">
							<s:if test="%{teamCredentialsDto.credentialDocumentId > 0}">
								<a
									href="jsp/providerRegistration/saveInsuranceTypeActiondisplayTheDocument.action?docId=<s:property value="teamCredentialsDto.credentialDocumentId "/>"
									target="blank"> <img
									src="${staticContextPath}/images/images_registration/common/spacer.gif"
									style="background-image: url(${staticContextPath}/images/images_registration/btn/view.gif);"
									width="72" height="20" /> </a>

								<!-- 
								<input type="image"
									src="${staticContextPath}/images/common/spacer.gif"
									width="72" height="20"
									style="background-image: url(${staticContextPath}/images/images_registration/btn/remove.gif);"
									onclick="javascript:removeDocumentDetail();" />
								-->
								
							</s:if>
						</s:if>
						</div><!--style="float: left;"-->
				</div><!--End Of outer Div-->
			</div><!-- End of grayModuleContent mainWellContent clearfix-->
			<div class="clearfix">
					<div class="formNavButtons">
						<input type = "hidden" name = "isFirstClick" id = "isFirstClick" value = "true">
						<input type = "hidden" name = "checkTrue" id = "checkTrue" value = "false">
						<input type="image"
								src="${staticContextPath}/images/common/spacer.gif"
								width="120" height="20"
								style="background-image: url(${staticContextPath}/images/btn/updateCredential.gif);"
								class="btn20Bevel" onclick="return updateCredentialNew();" />
									<input type="hidden" name="teamCredentialsDto.auditTimeLoggingIdNew" value="<%=request.getAttribute("auditTimeLoggingId")%>">
						<!-- SL-19459
							Code added for new alert message for 'Remove Credential' -->
						<s:if test="%{editFlag}">
									&nbsp;&nbsp; 
								<input id="removeCredentials" type="image"
								src="${staticContextPath}/images/common/spacer.gif"
								width="120" height="20"
								style="background-image: url(${staticContextPath}/images/btn/removeCredential.gif);"
								class="opendeclinemodal" onclick="removeCred(); return false;" />
								
						</s:if>
					</div>
					<div class="bottomRightLink">
						<a href="javascript:cancelCredential();">Cancel</a>
					</div>
			</div>

			<div id="declinemodal" class="jqmWindow" style="display: none" >
				 <div style="background: none repeat scroll 0% 0% rgb(88, 88, 90); border-bottom: 2px solid black; color: rgb(255, 255, 255); text-align: left; height: 15px; padding-left: 8px; padding-top: 5px;font-size:11px;font-weight:bold" id="modalTitleBlack">
					CONFIRMATION
				 </div>	
					<p style="padding: 8px;font-size:11px">
						<strong>		
							Warning: Once you remove this credential, it will no longer be visible to ServiceLive buyers and could negatively impact your membership in certain Select Provider Networks (SPN's).
						</strong>
					</p>
				   <table style="padding-right ;position: relative; left: 8px; padding-top:10px;padding-bottom:10px" width="100%">
					<tr>
						<td width="72%">
							<input id="noButton" type="button" value="Cancel" style="margin-left: 10px;margin-top: 5px;" class="cancel simplemodal-close" />
							<br/>
						</td>
						<td width="28%">
							<input id="yesButton" type="button" value="OK" style="width:90px;margin-left:50px;margin-top: 5px;" onclick="javascript:removeCredentilDetail();"/>	
						</td>
					</tr>
				</table>
			</div>
	</s:form>
