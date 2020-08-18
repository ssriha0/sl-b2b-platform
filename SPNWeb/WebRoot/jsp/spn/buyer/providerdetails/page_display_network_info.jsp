<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<head>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/jqueryui/jquery-ui-1.10.4.custom.min.css" />
	<style type="text/css" media="screen">
		.visited a {
		    color: #00a0d2;
		    text-decoration: none;
		}
		.ui-widget-content a {
		    color: #00a0d2;
		}
	</style>
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqueryui/jquery-ui-1.10.4.custom.min.js"></script>
	<script type="text/javascript">
	function fnClearValue(){
		jQuery(".validUntil").val("");
	}
	
		function cancelForm(obj){
			jQuery(obj).parent().parent().parent().find('form')[0].reset();
	    	jQuery(obj).parent().parent().parent().hide();
		}
	jQuery(document).ready(function($){	
		var resId = '${vendorResourceId}';
		jQuery('.reasons').hide();
		jQuery('.forApprovedAndInactive').hide();
	});
	
	</script>
</head>

<body>
<div id="addNote">
	<h3>
		Add a Note&nbsp;
		<tags:authorities authorityName="View Member Manager">
			<img src="${staticContextPath}/images/icons/pencil-transp.gif" />
		</tags:authorities>
	</h3>
	<div id="addNoteForm" class="inlineEditForm">
		<form id="formAddNote" name="formAddNote">

			<input type="hidden" id="vendorId" name="vendorId"
				value="${vendorId}" />
			<input type="hidden" id="vendorResourceId" name="vendorResourceId"
				value="${vendorResourceId}" />

			<div class="error errorMsg errorMessage"></div>

			<label>
				Notes
				<span class="req">*</span>
			</label>
			<textarea rows="5" cols="40" class="addNoteArea"></textarea>
			<div id="buttons">
				<input type="button" class="action" value="Submit"
					onclick="addNote(this);" />
				<br />
				<a href="#" class="cancelLink" onclick="return false;">Cancel</a>
			</div>
			<p class="characterCount infoLabel">
				750 characters remaining
			</p>
		</form>
	</div>
</div>

<div id="viewNotes">
	<h3>
		<img src="${staticContextPath}/images/icons/arrowRight-darkgray.gif" />
		View Notes & Change History
	</h3>
	<div id="viewNotesDisplay"></div>
</div>

<div id="buyerNetworks">
	<h3>
		<c:choose>
		<c:when test="${vendorName != null}">			
			${vendorName} Networks
		</c:when>			
		<c:otherwise>
			${firstName} ${lastName} Networks
		</c:otherwise>
		</c:choose>
	</h3>

	<div id="changeNetworkStatusTemplate" class="inlineEditForm">
		<form id="statusChangeForm" action="testAction">
			<div class="error errorMsg errorMessage">
				<span>Error test</span>
			</div>
			<span id="warningText" style="color:#FF0000;"></span>
			<p>
				(
				<span class="req">*</span> required)
			</p>

			<input type="hidden" id="vendorId" name="vendorId"
				value="${vendorId}" />
			<input type="hidden" id="vendorResourceId" name="vendorResourceId"
				value="${vendorResourceId}" />
			<input type="hidden" id="spnId" name="spnId"
				value="${networkId}" />
			<input type="hidden" id="validityDate" name="validityDate" class="validityDate"
				value="${validityDate}" />
			<div style="width: 100%;margin-left: 17px;">
				<label style="padding-right: 5px:">Change Network Status to<span class="req">*</span></label> 
			<c:choose>
			<c:when test="${vendorResourceId != null}"><!-- FOR PROVIDER -->
				<fieldset class="membershipStatusSelector">
				<select id="networkStatus" name="networkStatus"
						onchange="fnValidateSelection(this);">
					<option value="-1" id="noStatus">-- Select Status --</option>		
					<option value="SP%20SPN%20APPROVED" id="approved">Network Provider</option>	
					<option value="SP%20SPN%20OUT%20OF%20COMPLIANCE" id="inactive">Inactive</option>	
					<option value="SP%20SPN%20REMOVED" id="remove">Remove</option>		
						</select>
				</fieldset>
			</c:when>
			<c:otherwise><!-- FOR FIRM -->
					<select id="networkStatus" name="networkStatus"
						onchange="fnValidateSelection(this);">
					<option value="-1" id="noStatus">-- Select Status --</option>	
					<option value="PF%20SPN%20MEMBER" id="approved">Network Provider</option>	
					<option value="PF%20FIRM%20OUT%20OF%20COMPLIANCE" id="inactive">Inactive</option>	
					<option value="PF%20SPN%20REMOVED%20FIRM" id="remove">Remove</option>		
						</select>
			</c:otherwise>
			</c:choose>
			</div>
			<br>			
			<div class="forApprovedAndInactive" style="display: block;">

			<p style="font-weight: bold;margin-left: 92px;">Valid Until<span class="req">*</span>    
			<span style="padding-left:2px">
			<input readonly="true" type="text" id="validUntil" style="width:125px;" name="validUntil" class="validUntil" onmousedown="fnDisplayDatePicker();" onkeydown="fnDisplayDatePicker();"></input>
			12:01 AM</span>
			</p>
			</div>
			<!-- <p class="infoLabel">
				Select an option above to change the membership status
			</p> -->
			
			<c:choose>
			<c:when test="${vendorResourceId != null}">
			<div id="applyAll" class="applyAll" style="display: none;">
			<br/>
			<div class="divClass" style="margin-left: 130px;">
			<input type="checkbox" id="applyAllNetworks" name="applyAllNetworks" value="0" class="applyAllProvider" onclick="displayInfo(this);"/><span onmouseover="showHistory(event,this)" onmouseout="closeHistory()" style="margin-left:12px;text-align:center;"><b>&nbsp;Apply to all of this provider's network<span class="dash">s</span></b></span>
			</div>
			<div style="margin-left:140px;">&nbsp;&nbsp;&nbsp;&nbsp;<b>--------------------------------------------</b></div>
			</div>
			<div class="arrowAddNoteDetails" id="arrowr_"></div>
									<div class="histDetailsDiv" id="expDetails">
										<div class="preCallHistoryHead"
											style="height: 20px; width: 100% px; background: #EEEEEE; -moz-border-radius: 8px 8px 0 0;">
											<span class="" style="float:left;margin-top:2px">&nbsp;&nbsp;Apply to all </span> <span
												style="font-family: Arial; float: right; font-size: 13px; font-weight: bold; font-style: normal; text-decoration: none; color: rgb(51, 51, 51); cursor: pointer;"
												onclick="closeHistory()" id="cache1">X</span>

										</div>
										<div
											style="margin-bottom: 5px; padding-left: 8px; border-top: 1px solid #CCCCCC; padding-top: 5px;">
											<div style="">
												Change this provider's Network Status in all of your networks of which they are a member.
											</div>
										</div>
									</div>
									
				<div id="infoText" class="infoText" style="display:none;color:#FF0000;margin-left:155px;">
					The Apply to All should only be used, if you wish to remove this Provider from all networks they are a part of.
				</div>
			</c:when>
			<c:otherwise>
			<div id="applyAll" class="applyAll" style="display: none;">
			<div class="divClass" style="margin-left: 130px;">
			<input type="checkbox" id="applyAllNetworks" name="applyAllNetworks" value="0" class="applyAllFirm" onclick="displayInfo(this);"/><span onmouseover="showHistory(event,this)" onmouseout="closeHistory()" style="margin-left:12px;"><b>&nbsp;Apply to all of this firm's network<span class="dash">s</span></span></b></span>
			</div>
			<div style="margin-left:140px;">&nbsp;&nbsp;&nbsp;&nbsp;<b>---------------------------------------</b></div>
			</div>
			<div class="arrowAddNoteDetails" id="arrowr_"></div>
									<div class="histDetailsDiv" id="expDetails">
										<div class="preCallHistoryHead"
											style="height: 20px; width: 100% px; background: #EEEEEE; -moz-border-radius: 8px 8px 0 0;">
											<span class="" style="float:left;margin-top:2px">&nbsp;&nbsp;Apply to all </span> <span
												style="font-family: Arial; float: right; font-size: 13px; font-weight: bold; font-style: normal; text-decoration: none; color: rgb(51, 51, 51); cursor: pointer;"
												onclick="closeHistory()" id="cache1">X</span>

										</div>
										<div
											style="margin-bottom: 5px; padding-left: 8px; border-top: 1px solid #CCCCCC; padding-top: 5px;">
											<div style="">
												Change this provider firm's Network Status in all of your networks of which they are a member.
											</div>
										</div>
									</div>
								<div id="infoTextFirm" class="infoTextFirm" style="display:none;color: #FF0000;margin-left:155px;">
					The Apply to All should only be used, if you wish to remove this Firm from all networks they are a part of.
				</div>
			</c:otherwise>
			</c:choose>
			<br>
			<fieldset class="reasons" style="margin-left: 105px;display: block;" id="reasons">
				<div class="reasonDropdownDiv" name="reasonDropdownDiv" style="padding-left:2px">
				</div>
			</fieldset>
			<div style="margin-left:85x;">
			<p>
				<span style="float:left;margin-left:85px;margin-right:5px;font-weight: bold;">Comments
				<span class="req">*</span></span>
		
			<textarea rows="5" cols="30" class="addNoteArea" name="comments" style="height:100px; width:300px;"></textarea></p>
			<p style="margin-left:160px;" class="characterCount infoLabel"> 
				750 characters remaining
			</p>
			</div>
						<div id="buttons" style="width:100%;float:none;">
						<div style="align:right;margin-left:155px;">
						<a href="#" class="cancelLink visited" onclick="cancelForm(this);return false;" style="margin-right:220px;">Cancel</a>
				<input id="submitNetworkStatusChange" align="right" width="100px;" type="button" value="Submit" onclick="submitNetworkChanges(this);" >
				
				</div>
			</div>
		
		</form>
	</div>
	<div id="buyerNetworksContent"></div>
</div>

<div id="loadSPNSpinner" class="jqmWindow">
	<div class="modal-content">
		<label>
			<span>Updating...</span>
		</label>
		<div>
			<img src="${staticContextPath}/images/simple/searchloading.gif" />
		</div>
		<div class="clearfix">
			<a class="cancel jqmClose left" href="#">Cancel</a>
		</div>
	</div>
</div>

</body>
