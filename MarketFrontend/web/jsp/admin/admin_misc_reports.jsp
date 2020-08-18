<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:useBean id="date" class="java.util.Date" />
<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<style type="text/css">
label {
	padding-left: 5px;
}

.secondRow {
	background-color: #C2BEBF;
}

.thirdRow {
	background-color: #DBD9DA;
}

.evenRow {
	background-color: #F6F6F6;
}

#addTaskStep1 {
	background-color: #FAF9C3;
}

table#reportTable tr {
	height: 20px;
}

input[type="radio"] {
	cursor: pointer;
}

input.button {
	cursor: pointer;
}

.successBox {
	background-color: #DDFFDD;
	border-color: #009900;
	border-style: solid;
	border-width: 1px;
	margin-bottom: 2px;
	margin-top: 2px;
	text-align: left;
}

.successMsg {
	padding-left: 15px;
}
</style>

<form action="" id="report_pay_admn" name="report_pay_admn">
	<input type="hidden" id="form_name_pay_admn" name="form_name"
		value="Admin_Payment_Report">
	<div class="grayModuleHdr">ServiceLive Admin Payments Report</div>
	<div class="grayModuleContent">
		<c:if test="${formType== 'pay_admn' && not empty successMsg }">
			<div id="successMsg" class="successBox">
				<p class="successMsg">${successMsg}</p>
			</div>
		</c:if>
		<div style="padding-left: 5px; padding-top: 2px; padding-bottom: 2px;">
			<div id="errReport_pay_admn" class="errorBox" style="display: none;"></div>
		</div>
		<div style="border: 1px; padding: 5px 5px 5px 5px; width: 100%">
			<div class="grayModuleContent mainWellContent" style="width: 50%">
				<table cellpadding="10" id="reportTable" width="100%;">
					<tr>
						<td><input type="radio" id="calendarYr_pay_admn"
							name="yearRadio_pay_admn" value="true" checked="checked"
							onclick="clearCalendar('pay_admn')"></td>
						<td><label> Calendar Year </label></td>
						<td height="25px"><select id="yearSelect_pay_admn"
							class="yearSelect" name="yearSelect" style="width: 105px;">
								<option value="0" selected="selected">Please Select</option>
								<c:forEach var="loop" begin="2008" end="${currentYear}"
									varStatus="years" step="1">
									<option value="${years.end - loop +years.begin}">
										<c:out value="${years.end - loop +years.begin}"></c:out>
									</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr height="20px">
						<td><input type="radio" id="range_pay_admn"
							name="yearRadio_pay_admn" value="false"
							onclick="disableYear('pay_admn')"></td>
						<td><label> Date Range </label></td>
						<td height="25px"><input type="text" id="fromDate_pay_admn"
							dojoType="dijit.form.DateTextBox" class="dateClass"
							name="dojoCalendarFromDate"
							style="height: 12px; border-color: #FFBF00; border-width: 1px; width: 120px;"
							lang="en-us" onblur="validateDate(this);"
							disabled="disabled" /> to <input type="text"
							id="toDate_pay_admn" dojoType="dijit.form.DateTextBox"
							class="dateClass" name="dojoCalendarToDate"
							style="height: 12px; border-color: #FFBF00; border-width: 1px; width: 120px;"
							lang="en-us" onblur="validateDate(this);"
							disabled="disabled" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td colspan="2"><label style="font-size: xx-small;">
								The maximum allowed timespan for the Date Range is 13 months. </label></td>
					</tr>
				</table>
			</div>
			<div
				style="padding-left: 5px; padding-top: 2px; padding-bottom: 2px;">
				<div id="errReportCount_pay_admn" class="errorBox"
					style="display: none;"></div>
			</div>
			<div style="padding-top: 15px; padding-left: 10px; height: 50px;">
				<input id="export_pay_admn" class="reportButton button action"
					type="button" name="submit"
					style="width: 110px; font-weight: bold; font-size: 10px;"
					value="Submit" formInd="pay_admn" display="export"
					onclick="return validateForm(this);">
			</div>
		</div>
		<c:set var="exportStatus" value="${exportStatus_admin_payment}"
			scope="request">
		</c:set>
		<c:set var="formInd" value="pay_admn" scope="request">
		</c:set>
		<c:set var="statusMsg" value="${admin_payment_stat_msg}"
			scope="request">
		</c:set>
		<jsp:include page="/jsp/finance_manager/body/tabs/report_status.jsp" />
	</div>
</form>
<script type="text/javascript">
	var formIndicator = '${formType}';
	var pos = 0;
	if (formIndicator == 'pay_admn') {
		jQuery('#calendarYr_pay_admn').focus();
		pos = jQuery('#calendarYr_pay_admn').offset().top;
	} else if (formIndicator == 'rev_prov') {
		jQuery('#providerIds_rev_prov').focus();
		pos = jQuery('#providerIds_rev_prov').offset().top;
	} else if (formIndicator == 'so_prov') {
		jQuery('#providerIds_so_prov').focus();
		pos = jQuery('#providerIds_so_prov').offset().top;
	} else if (formIndicator == 'so_byr') {
		jQuery('#buyerIds_so_byr').focus();
		pos = jQuery('#buyerIds_so_byr').offset().top;
	} else if (formIndicator == 'tax_byr') {
		jQuery('#buyerIds_tax_byr').focus();
		pos = jQuery('#buyerIds_tax_byr').offset().top;
	}
	scroll(0, pos - 100);
</script>
<c:remove var="successMsg" scope="session" />
<c:remove var="errorMessage" scope="session" />
<c:remove var="formType" scope="session" />


