<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<jsp:useBean id="date" class="java.util.Date" />
<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
<fmt:formatDate value="${date}" pattern="mm/dd/yy" var="todaysDate" />
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/bulletinBoard/main.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/buttons.css">
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

.hdr_note {
	color: #FE0000;
	height: 50px;
	text-align: justify;
	padding-bottom: 5px;
}
.successBox{
	background-color: #DDFFDD;
    border-color: #009900;
    border-style: solid;
    border-width: 1px;
    margin-bottom: 2px;
    margin-top: 2px;
    text-align: left;
}
.successMsg{
    padding-left: 15px;
}
</style>
</head>
<!-- NEW MODULE/ WIDGET-->
<body>
<div id="overLay" style="display: none;z-index: 1000;width: 100%;height: 100%;position: fixed;opacity: 0.4;filter: alpha(opacity = 40);top: 0px;left: 0px;background-color: #E8E8E8 ;cursor: wait;"></div>
	<div class="colLeft900">
		<div class="content">
			<div class="dijitContentPane dijitTabPane">
				<div style="display: none;">
					<jsp:include
						page="/jsp/finance_manager/body/tabs/report_input_form.jsp" />
				</div>
				<div class="hdr_note"
					style="color: #FE0000; height: 50px; text-align: justify; padding-bottom: 5px;">
					Once you submit a report it will be placed in a queue with other
					users' requests. Requests are processed in the order in which they
					are received. Reports may not be immediately available. Please
					check the status of your request(s). Reports will be available to
					you for 7 days from the time the request is completed. After 7
					days, the report will be automatically deleted. You may submit a
					new request at any time.
				</div>
				<div class="darkGrayModuleHdr">Provider Payments by Service
					Order</div>
				<div class="grayModuleContent">
					<form action="" id="report_so_prov" name="report_so_prov">
						<input type="hidden" id="form_name_so_prov" name="form_name"
							value="ProviderPaymentReportBySO">

						<div
							style="padding-left: 5px; padding-top: 10px; padding-bottom: 5px;">
							This report details all Completed Service Orders for a set period
							of time. You can use this report to help you reconcile the net
							payments you receive against the gross amount of your service
							orders. You can also use this report to analyze payments you
							receive by a particular Buyer.</div>
						<div
							style="padding-left: 5px; padding-top: 5px; padding-bottom: 5px;">
							<c:if test="${formType== 'so_prov' && not empty successMsg }">
								<div id="successMsg" class="successBox" style="background-color: #DDFFDD; border-color: #009900;border-style: solid;
								 border-width: 1px; margin-bottom: 2px; margin-top: 2px; text-align: left; ">
								<p class="successMsg" style="padding-left: 15px;">
								${successMsg}</p>
								</div>
							</c:if>
							<div id="errReport_so_prov" class="errorBox"
								style="display: none;"></div>
						</div>
						<table width="100%">
							<tr>
								<td colspan="2">
									<div class="grayModuleContent mainWellContent"
										style="padding: 5px 5px 5px 5px;">
										<table width="100%" cellpadding="10" id="reportTable">
											<tr>
												<td align="center" height="25%"><input type="radio"
													id="allBuyers_so_prov" name="allBuyers_so_prov"
													value="true" checked="checked" style="cursor: pointer;"
													formInd="so_prov" onclick="clearIds('so_prov')" tabindex="1"></td>
												<td><label> For all Buyers </label></td>
												<td height="25px;">&nbsp;</td>
											</tr>
											<tr>
												<td align="center"><input type="radio"
													id="specific_so_prov" name="allBuyers_so_prov"
													value="false" style="cursor: pointer;" formInd="so_prov"
													onclick="enableIds('so_prov')" tabindex="2"></td>
												<td><label> For Specific Buyers </label></td>
												<td><input type="text" id="specificIds_so_prov"
													name="specificIds" class="idsTextField shadowBox left"
													style="height: 12px; width: 500px;"
													onkeypress="return isNumberAndComma(event);" size="30"
													formInd="so_prov" idType="buyer"
													disabled="disabled" tabindex="3"></td>
											</tr>
											<tr>
												<td>&nbsp;</td>
												<td colspan="2"><label style="font-size: xx-small;">
														You may enter up to 15 Buyer IDs (separated by commas). </label></td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
							<tr>
								<td width="30%">
									<div class="grayModuleContent"
										style="margin-top: 5px; padding-bottom: 5px">
										<table cellpadding="10" id="reportTable">
											<tr>
												<td height="25px"><input type="radio"
													id="payment_so_prov" name="reportBy_so_prov" value="true"
													checked="checked" style="cursor: pointer;" tabindex="4"></td>
												<td><label> Report by Payment Date </label></td>
											</tr>
											<tr>
												<td height="25px"><input type="radio"
													id="complete_so_prov" name="reportBy_so_prov" value="false"
													style="cursor: pointer;" tabindex="5"></td>
												<td><label> Report by Completion Date </label></td>
											</tr>
											<tr>
												<td colspan="3">&nbsp;</td>
											</tr>
										</table>

									</div>
								</td>
								<td>
									<div style="padding-left: 15px;">
										<div class="grayModuleContent"
											style="margin-top: 5px; padding-left: 10px;">
											<table cellpadding="10" id="reportTable" width="100%;">
												<tr>
													<td><input type="radio" id="calendarYr_so_prov"
														name="yearRadio_so_prov" value="true" checked="checked"
														style="cursor: pointer;" formInd="so_prov"
														onclick="clearCalendar('so_prov')" tabindex="6"></td>
													<td><label> Calendar Year </label></td>
													<td height="25px"><select id="yearSelect_so_prov"
														class="yearSelect" name="yearSelect" style="width: 105px;" tabindex="7">
															<option value="0" selected="selected">Please
																Select</option>
															<c:forEach var="loop" begin="2008" end="${currentYear}"
																varStatus="years" step="1">
																<option value="${years.end - loop +years.begin}">
																	<c:out value="${years.end - loop +years.begin}"></c:out>
																</option>
															</c:forEach>
													</select></td>
												</tr>
												<tr height="20px">
													<td><input type="radio" id="range_so_prov"
														name="yearRadio_so_prov" value="false"
														style="cursor: pointer;" formInd="so_prov"
														onclick="disableYear('so_prov')" tabindex="8"></td>
													<td><label> Date Range </label></td>
													<td height="25px"><input type="text"
														id="fromDate_so_prov" dojoType="dijit.form.DateTextBox"
														class="dateClass" name="dojoCalendarFromDate"
														style="border-color: #FFBF00; height: 12px; width: 120px;"
														lang="en-us" onblur="validateDate(this);"
														disabled="disabled" tabindex="9" /> to <input type="text"
														id="toDate_so_prov" dojoType="dijit.form.DateTextBox"
														class="dateClass" name="dojoCalendarToDate"
														style="border-color: #FFBF00; height: 12px; width: 120px;"
														lang="en-us" onblur="validateDate(this);"
														disabled="disabled" tabindex="10" /></td>
												</tr>
												<tr>
													<td>&nbsp;</td>
													<td colspan="2"><label style="font-size: xx-small;">
															The maximum allowed timespan for the Date Range is 13 months. </label></td>
												</tr>
											</table>
										</div>
									</div>
								</td>
							</tr>
						</table>						
						<div
							style="padding-top: 5px; padding-left: 10px; height: 30px; padding-bottom: 10px;">
							<table>
								<tr>
									<td><input type="button" id="display_so_prov" name="submit"
										value="Submit" class="button action" type="button"
										style="width: 100px; font-weight: bold; font-size: 10px;"
										formInd="so_prov" onclick="return validateForm(this);" tabindex="11" /></td>
									<td style="padding-left: 15px;"></td>
								</tr>
							</table>
						</div>
					</form>
					<c:set var="exportStatus" value="${exportStatus_prov_so}"
						scope="request">
					</c:set>
					<c:set var="statusMsg" value="${prov_so_stat_msg}" scope="request">
					</c:set>	
					<c:set var="formInd" value="so_prov" scope="request"></c:set>
					<jsp:include
						page="/jsp/finance_manager/body/tabs/report_status.jsp" />
				</div>
			</div>
			<div style="padding: 15px 0px 15px 0px;">
				<hr style="height: 2px; color: black;">
			</div>
			<div class="dijitContentPane dijitTabPane">
				<div class="darkGrayModuleHdr">Provider Revenue Summary Report</div>
				<div class="grayModuleContent">
					<form action="" id="report_rev_prov" name="report_reve_prov">
						<input type="hidden" id="form_name_rev_prov" name="form_name"
							value="Provider_Rev_Report">
						<div
							style="padding-left: 5px; padding-top: 5px; padding-bottom: 5px;">
							This report summarizes, by Buyer, your total Net Payments
							received for a set period of time.</div>
						<c:if test="${formType== 'rev_prov' && not empty successMsg }">
								<div id="successMsg" class="successBox" style="background-color: #DDFFDD; border-color: #009900;border-style: solid;
								 border-width: 1px; margin-bottom: 2px; margin-top: 2px; text-align: left; ">
								<p class="successMsg" style="padding-left: 15px;">
								${successMsg}</p>
								</div>
							</c:if>
						<div
							style="padding-left: 5px; padding-top: 5px; padding-bottom: 5px;">
							<div id="errReport_rev_prov" class="errorBox"
								style="display: none;"></div>
						</div>
						<table width="100%">
							<tr>
								<td colspan="2">
									<div class="grayModuleContent mainWellContent"
										style="padding: 5px 5px 5px 5px;">
										<table width="100%" cellpadding="10" id="reportTable">
											<tr>
												<td align="center" height="25%"><input type="radio"
													id="allBuyers_rev_prov" name="allBuyers_rev_prov"
													value="true" checked="checked"
													onclick="clearIds('rev_prov')" tabindex="12"></td>
												<td><label> For all Buyers </label></td>
												<td height="25px;">&nbsp;</td>
											</tr>
											<tr>
												<td align="center"><input type="radio"
													id="specific_rev_prov" name="allBuyers_rev_prov"
													value="false" onclick="enableIds('rev_prov')" tabindex="13" ></td>
												<td><label> For Specific Buyers </label></td>
												<td><input type="text" id="specificIds_rev_prov"
													name="specificIds" class="idsTextField shadowBox left"
													style="height: 12px; width: 500px;"
													onkeypress="return isNumberAndComma(event);" size="30"
													formInd="rev_prov" idType="buyer"
													disabled="disabled" tabindex="14"></td>
											</tr>
											<tr>
												<td>&nbsp;</td>
												<td colspan="2"><label style="font-size: xx-small;">
														You may enter up to 15 Buyer IDs (separated by commas). </label></td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
							<tr>
								<td width="30%">
									<div class="grayModuleContent"
										style="margin-top: 5px; padding-bottom: 5px">
										<table cellpadding="10" id="reportTable">
											<tr>
												<td height="25px"><input type="radio"
													id="reportBy_rev_prov" name="reportBy_rev_prov"
													value="true" checked="checked" tabindex="15"></td>
												<td><label> Report by Payment Date </label></td>
											</tr>
											<tr>
												<td height="25px"><input type="radio"
													id="reportBy_rev_prov" name="reportBy_rev_prov"
													value="false" tabindex="16"></td>
												<td><label> Report by Completion Date </label></td>
											</tr>
											<tr>
												<td colspan="3">&nbsp;</td>
											</tr>
										</table>

									</div>
								</td>
								<td>
									<div style="padding-left: 15px;">
										<div class="grayModuleContent"
											style="margin-top: 5px; padding-left: 10px;">
											<table cellpadding="10" id="reportTable" width="100%;">
												<tr>
													<td><input type="radio" id="calendarYr_rev_prov"
														name="yearRadio_rev_prov" value="true" checked="checked"
														onclick="clearCalendar('rev_prov')" tabindex="17"></td>
													<td><label> Calendar Year </label></td>
													<td height="25px"><select id="yearSelect_rev_prov"
														class="yearSelect" name="yearSelect" style="width: 105px;">
															<option value="0" selected="selected" tabindex="18">Please
																Select</option>
															<c:forEach var="loop" begin="2008" end="${currentYear}"
																varStatus="years" step="1">
																<option value="${years.end - loop +years.begin}">
																	<c:out value="${years.end - loop +years.begin}"></c:out>
																</option>
															</c:forEach>
													</select></td>
												</tr>
												<tr height="20px">
													<td><input type="radio" id="range_rev_prov"
														name="yearRadio_rev_prov" value="false"
														onclick="disableYear('rev_prov')" tabindex="19"></td>
													<td><label> Date Range </label></td>
													<td height="25px"><input type="text"
														id="fromDate_rev_prov" dojoType="dijit.form.DateTextBox"
														class="dateClass" name="dojoCalendarFromDate"
														formInd="rev_prov"
														style="height: 12px; border-color: #FFBF00; width: 120px;"
														lang="en-us" onblur="validateDate(this);"
														disabled="disabled" tabindex="20" /> to <input type="text"
														id="toDate_rev_prov" dojoType="dijit.form.DateTextBox"
														class="dateClass" name="dojoCalendarToDate"
														formInd="rev_prov"
														style="height: 12px; border-color: #FFBF00; width: 120px;"
														lang="en-us" onblur="validateDate(this);"
														disabled="disabled" tabindex="21"  /></td>
												</tr>
												<tr>
													<td>&nbsp;</td>
													<td colspan="2"><label style="font-size: xx-small;">
															The maximum allowed timespan for the Date Range is 13 months. </label></td>
												</tr>
											</table>
										</div>
									</div>
								</td>
							</tr>
						</table>						
						<div
							style="padding-top: 5px; padding-left: 10px; padding-bottom: 10px;">
							<table>
								<tr>
									<td><input id="display_rev_prov" class="button action" name="submit"
										style="width: 100px; font-weight: bold; font-size: 10px;"
										type="button" value="Submit" formInd="rev_prov"
										onclick="return validateForm(this);" tabindex="22" /></td>
									<td style="padding-left: 15px;"></td>
								</tr>
							</table>
						</div>
					</form>
					<c:set var="exportStatus" value="${exportStatus_prov_rev}"
						scope="request">
					</c:set>
					<c:set var="formInd" value="rev_prov" scope="request">
					</c:set>
					<c:set var="statusMsg" value="${prov_rev_stat_msg}" scope="request">
					</c:set>
					<jsp:include
						page="/jsp/finance_manager/body/tabs/report_status.jsp" />
				</div>
			</div>
			<div style="padding: 15px 0px 15px 0px;">
				<hr style="height: 2px; color: black;">
			</div>

		</div>
	</div>
<script type="text/javascript">	
	var formIndicator = '${formType}';
	jQuery('#allBuyers_'+formIndicator).focus();
</script>
<c:remove var="successMsg" scope="session"/>
<c:remove var="errorMessage" scope="session" />
<c:remove var="formType" scope="session"/>
</body>
</html>
