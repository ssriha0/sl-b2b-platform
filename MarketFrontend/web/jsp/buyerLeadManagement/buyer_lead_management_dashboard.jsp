<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<script type="text/javascript">
$('#buyer-lead-monitor_filter').hide();
//$('#buyerLeadLoadingLogo').html('<img src="${staticContextPath}/images/loading.gif" width="572px" height="620px">');
$('#buyerLeadLoadingLogo').show();
function leadProfileDetails(leadId)
{//alert('leadId'+leadId);
var url = "${contextPath}/buyerLeadOrderSummaryController.action?leadId="+leadId+"&popup=false&widgetSuccessInd=false";
//alert('url-->>'+url);
window.location.href=url;
}
function checkSearchText(){
	if(document.getElementById("buyerLeadSearchText").value.length == 0){
		document.getElementById("buyerLeadSearchText").value = 'Enter text....'
	}
}
</script>
<html xmlns="http://www.w3.org/1999/xhtml">
<style type="text/css">
		.labelHdr
				{
				padding-left:10px;
		}
		.sorting_asc { 
			background: url('${staticContextPath}/images/grid/arrow-up-white.gif') no-repeat center left; 
		}
		.sorting_desc { 
			background: url('${staticContextPath}/images/grid/arrow-down-white.gif') no-repeat center left; 
		}
		input.buyerLeadDateField{width: 174px;position: relative;background-image: url('${staticContextPath}/images/icons/date.gif');background-repeat: no-repeat;background-position: right;}
	</style>
<p class="leadManagementHeading">Leads Management</p> 
<p style="position:relative;top:35px;left:15px;font-size:11px;width: 930px;padding-bottom: 22px;font-family:Verdana,Arial,Helvetica,sans-serif;color:#222222;">
The Lead Management enables functionalities for agents to manage service requests submitted by the customer. You can use the search tool below to find specific service request and view details.
</p>
<br>
<br>
<span id="buyerLeadSearchError" style="display:none;position:relative;left:10px;padding-bottom: 10px;top: 3px;" class="buyerLeadError">
</span>
<div  style="display: block;position:relative;top:22px;padding-bottom:40px;left: 1%;">
<table style="width:100%;">
	<tr>			
		<td style="width:42%;padding-bottom: 20px;">		
		<div style="padding-top:10px;padding-left:10px;">		
			<select id="buyerLeadSearch"  style="font-size:10px;width:180px;font-family:Verdana,Arial,Helvetica,sans-serif;">
						<option value="1">
						Customer Phone
						</option>
						<option value="2">
						Customer Last Name
						</option>
						<option value="3">
						Customer First Name
						</option>
						<option value="4">
						Service Request #
						</option>
						<!-- SL-20893:Removing LMS Id -->
						<!--<option value="11">
						LMS Service Request #
						</option>-->
						<option value="5">
						Provider Firm Id
						</option>
						<option value="6">
						Zip code
						</option>
						<option value="7">
						State
						</option>
						<option value="8">
						Created date
						</option>
						<option value="9">
						Project Type
						</option>
						<option value="10">
						Lead Status
						</option>
				</select>
				<input type="text" id="buyerLeadSearchText" maxlength="10" value="Enter text...." onblur="checkSearchText();" class="buyerLeadSearchList" style="width:185px;height:16px;font-size:10px;position:relative;left:3%;top: 0px;font-family:Verdana,Arial,Helvetica,sans-serif;"/>
				<select id="stateSearch" class="buyerLeadSearchList" style="display:none;font-size:10px;width:185px;left: 3%;position: relative;"><option value="-1">Please Select</option>
			 	<c:forEach var="buyerLeadStates" items="${buyerLeadStates}"> 
			 	<option id="${buyerLeadStates.type}"  value="${buyerLeadStates.descr}">${buyerLeadStates.descr}</option>
				</c:forEach> </select>
				<select id="projectTypeSearch"  class="buyerLeadSearchList" style="display:none;font-size:10px;width:185px;left: 3%;position: relative;font-family:Verdana,Arial,Helvetica,sans-serif;">
				<option value="-1">Please Select</option>
				<option value="7">Electrical</option>
				<option value="11">Plumbing</option>
				 </select>
				<select id="createdDateSearch"  class="buyerLeadSearchList" style="display:none;font-size:10px;width:185px;left: 3%;position: relative;font-family:Verdana,Arial,Helvetica,sans-serif;"><option value="-1">Please Select</option><option value="1">Specific date</option><option value="2">Date Range
				</option></select>
				<!-- SL-20893:Changing options of lead status -->
				<select id="leadSearch"  class="buyerLeadSearchList" style="display:none;font-size:10px;width:185px;left: 3%;position: relative;"><option value="-1">Please Select</option><option value="1">New</option>
				<option value="2">Working</option><option value="3">Scheduled</option><option value="4">Completed</option><option value="5">Cancelled</option><option value="6">Unmatched</option><option value="6">OUT-OF-AREA</option></select>
				
				<div id="specificDateSearch" style="display:none;width: 194px; padding-left: 50%;padding-top:15px;">
				<input type="text" class="buyerLeadDateField shadowBox"
							id="specificeFromDate" style="font-size:10px;font-family:Verdana,Arial,Helvetica,sans-serif;"
							value=""/>
				</div>
				<div id="rangeDateSearch" style="display:none;padding-top:18px;">
				<input type="text" class="buyerLeadDateField shadowBox" style="font-size:10px;font-family:Verdana,Arial,Helvetica,sans-serif;"
							id="dateRangeFromDate" 
							value="" /> to 
							<input type="text" 
								class="shadowBox buyerLeadDateField" id="dateRangeToDate"
								value="" style="font-size:10px" />
				</div>
				
				</div>
				<input id="buyerLeadSearchSubmit" type="submit" class="buyerLeadSubmitButton "value="SEARCH" />
				
				
			</td>
			
			<td style="border-left: 1px solid #CCCCCC;">
			
			<select id="buyerLeadFilter" style="font-size:10px;left: 2%;
    position: relative;
    top: 10px;font-family:Verdana,Arial,Helvetica,sans-serif;">
						<option value="-1">
							 Select a filter
						</option>
						<!-- SL-20893 Changing filters -->
						<option value="1">
						Multiple Leads with same phone number 
						</option>
						<option value="2">
						New leads, contact firm to confirm schedule
						</option>
						<option value="3">
						In working status, contact firm to confirm schedule
						</option>
						<option value="4">
						Appointment Scheduled, no Pro assigned
						</option>
						<option value="5">
						Pending Completion / Re-schedule Needed
						</option>
				</select>
				<a style="color:black;text-decoration: underline;" id="resetBuyerLeadFilter" class="resetFilterClass" href="#"><font font-family= "Verdana,Arial,Helvetica,sans-serif;"><b>Reset Filters</b></font></a>
			</td>
			</tr>
	</table>
	
	

</div>
<div id="buyerLeadLoadingLogo" style="display: block;">
<img src="${staticContextPath}/images/loading.gif" width="542px" height="450px" style="position:relative;top:0px;left:160px;" >
</div>
  <div style="padding-top: 3%;display:none;" id="buyerLeadDiv">
<table id="buyer-lead-monitor" border="0" cellpadding="0" cellspacing="0"
	style="width: 100%;display: none; height: 30px;">
	<thead>
		<tr style="background-color: #bbb;color: white;height:32px;">
					<th id="firmStatus" class="tabledHdr rqHdr" style="width:80px;cursor: pointer !important;text-align:left;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;outline: none;">
						<p class="labelHdr">Lead Status</p>
					</th>
					<th id="slLeadId" class="tabledHdr rqHdr" style="width:155px;cursor: pointer !important;text-align:left;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;outline: none;">
						<p class="labelHdr">Service Request #</p> 
					</th>
					<!-- SL-20893: Adding provider Firm in table -->
					<th id="providerFirm" class="tabledHdr rqHdr" style="width:150px;cursor: pointer !important;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;outline: none;">
						<p class="labelHdr">Provider Firm </p> 
					</th>
					<th id="description" class="tabledHdr rqHdr" style="width:230px;cursor: pointer !important;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;outline: none;">
						<p class="labelHdr">Title </p>
					</th>
					<th id="customerName" class="tabledHdr rqHdr" style="width:130px;cursor: pointer !important;text-align:left;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;outline: none;">
						<p class="labelHdr">Customer Name </p>
					</th>
					<th id="phoneNumber" class="tabledHdr rqHdr" style="width:125px;cursor: pointer !important;text-align:left;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;outline: none;">
						<p class="labelHdr">Customer Phone</p>
					</th>
					<th id="location" class="tabledHdr rqHdr" style="width:72px;cursor: pointer !important;text-align:left;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;outline: none;">
						<p class="labelHdr">Location</p>
					</th>
					<th id="createdDate" class="tabledHdr rqHdr" style="width:200px;cursor: pointer !important;text-align:left;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;outline: none;">
						<p class="labelHdr">Created Date</p>
					</th>
		</tr>
	</thead>
	<tbody id="buyerLeadData" style="width:100%;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;">
 	</tbody>
</table>

</div> 

</html>
