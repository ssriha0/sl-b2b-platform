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

#hdr_note {
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
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/fmReports.js"></script>

</head>
</head>
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
					new request at any time.</div>
				<div class="darkGrayModuleHdr">Buyer Payments by TaxpayerID</div>
				<div class="grayModuleContent">
					<form action="" id="report_tax_byr" name="report_tax_byr">
						<input type="hidden" id="form_name_tax_byr" name="form_name"
							value="Buyer_TaxId_Report">
						<div
							style="padding-left: 5px; padding-top: 10px; padding-bottom: 5px;">
							<label style="padding-left: 0px;"> This report provides
								you a summary of payments you made to your Providers during a
								set period of time. You can use this report to help you prepare
								your 1099MISC statements for your providers. Although care has
								been taken to ensure the accuracy, completeness and reliability
								of the information provided, any information obtained from this
								report which is used in the preparation of 1099 statements
								should be verified by you. ServiceLive recommends that you
								consult with a professional accountant if you have questions
								regarding the preparation of 1099s. </label>
						</div>
						<c:if test="${formType== 'tax_byr' && not empty successMsg }">
								<div id="successMsg" class="successBox" style="background-color: #DDFFDD; border-color: #009900;border-style: solid;
								 border-width: 1px; margin-bottom: 2px; margin-top: 2px; text-align: left; ">
								<p class="successMsg" style="padding-left: 15px;">
								${successMsg}</p>
								</div>
							</c:if>
						<div
							style="padding-left: 5px; padding-top: 5px; padding-bottom: 5px;">
							<div id="errReport_tax_byr" class="errorBox"
								style="display: none;"></div>
						</div>
						<table width="100%">
							<tr>
								<td>
									<div class="grayModuleContent mainWellContent"
										style="padding: 5px 5px 5px 5px;">
										<table width="100%" cellpadding="10" id="reportTable">
											<tr>
												<td align="center" height="25%"><input type="radio"
													id="allProviders_tax_byr" name="allProviders_tax_byr"
													value="true" checked="checked"
													onclick="clearIds('tax_byr')"></td>
												<td><label> For all Providers </label></td>
												<td height="25px;">&nbsp;</td>
											</tr>
											<tr>
												<td align="center"><input type="radio"
													id="specific_tax_byr" name="allProviders_tax_byr"
													value="false" onclick="enableIds('tax_byr')"></td>
												<td><label> For Specific Providers </label></td>
												<td><input type="text" id="specificIds_tax_byr"
													name="specificIds" class="idsTextField shadowBox left"
													style="height: 12px; width: 500px;"
													onkeypress="return isNumberAndComma(event);" size="30"
													formInd="tax_byr" idType="provider"
													disabled="disabled"></td>
											</tr>
											<tr>
												<td>&nbsp;</td>
												<td colspan="2"><label style="font-size: xx-small;">
														You may enter up to 15 Provider IDs (separated by commas).
												</label></td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div style="padding-left: 0px;">
										<div class="grayModuleContent"
											style="margin-top: 5px; padding-left: 10px; width: 65%">
											<table cellpadding="10" id="reportTable" width="100%;">
												<tr>
													<td><input type="radio" id="calendarYr_tax_byr"
														name="yearRadio_tax_byr" value="true" checked="checked"
														onclick="clearCalendar('tax_byr')"></td>
													<td><label> Calendar Year </label></td>
													<td height="25px"><select id="yearSelect_tax_byr"
														class="yearSelect" name="yearSelect" style="width: 105px;">
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
													<td><input type="radio" id="range_tax_byr"
														name="yearRadio_tax_byr" value="false"
														onclick="disableYear('tax_byr')"></td>
													<td><label> Date Range </label></td>
													<td height="25px"><input type="text"
														id="fromDate_tax_byr" dojoType="dijit.form.DateTextBox"
														class="dateClass" name="dojoCalendarFromDate"
														style="height: 12px; border-color: #FFBF00; border-width: 1px; width: 120px;"
														lang="en-us" onblur="validateDate(this);"
														disabled="disabled" /> to <input type="text"
														id="toDate_tax_byr" dojoType="dijit.form.DateTextBox"
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
									</div>
								</td>
							</tr>
						</table>
						<div
							style="padding-top: 5px; padding-left: 10px; height: 30px; padding-bottom: 10px;">
							<table>
								<tr>
									<td><input id="display_tax_byr" class="button action"
										type="button" name="submit"
										style="width: 100px; font-weight: bold; font-size: 10px;"
										value="Submit" formInd="tax_byr" display="display"
										onclick="return validateForm(this);"></td>
									<td style="padding-left: 15px;"></td>
								</tr>
							</table>
						</div>
					</form>
					<c:set var="exportStatus" value="${exportStatus_buyer_tax_id}"
						scope="request">
					</c:set>
					<c:set var="formInd" value="tax_byr" scope="request">
					</c:set>
					<c:set var="statusMsg" value="${buyer_id_stat_msg}" scope="request">
					</c:set>
					<jsp:include
						page="/jsp/finance_manager/body/tabs/report_status.jsp" />
				</div>
				<div style="padding: 15px 0px 15px 0px;">
					<hr style="height: 2px; color: black;">
				</div>

				<div class="dijitContentPane dijitTabPane">
					<div class="darkGrayModuleHdr">Buyer Payments by Service
						Order</div>
					<div class="grayModuleContent">
						<form action="" id="report_so_byr" name="report_so_byr">
							<input type="hidden" id="form_name_so_byr" name="form_name"
								value="Buyer_So_Report">
							<div
								style="padding-left: 5px; padding-top: 10px; padding-bottom: 15px;">
								<label style="padding-left: 0px;"> This report provides
									you with the detail of payments you made to your Providers, by
									Service Order, during a set period of time. You can use this
									report to analyze the transactions summarized in the "Buyer
									Payments by TaxpayerID" report. </label>
							</div>
							<c:if test="${formType== 'so_byr' && not empty successMsg }">
								<div id="successMsg" class="successBox" style="background-color: #DDFFDD; border-color: #009900;border-style: solid;
								 border-width: 1px; margin-bottom: 2px; margin-top: 2px; text-align: left; ">
								<p class="successMsg" style="padding-left: 15px;">
								${successMsg}</p>
								</div>
							</c:if>
							<div
								style="padding-left: 5px; padding-top: 5px; padding-bottom: 5px;">
								<div id="errReport_so_byr" class="errorBox"
									style="display: none;"></div>
							</div>
							<table width="100%">
								<tr>
									<td>
										<div class="grayModuleContent mainWellContent"
											style="padding: 5px 5px 5px 5px;">
											<table width="100%" cellpadding="10" id="reportTable">
												<tr>
													<td align="center" height="25%"><input type="radio"
														id="allProviders_so_byr" name="allProviders_so_byr"
														value="true" checked="checked"
														onclick="clearIds('so_byr')"></td>
													<td><label> For all Providers </label></td>
													<td height="25px;">&nbsp;</td>
												</tr>
												<tr>
													<td align="center"><input type="radio"
														id="specific_so_byr" name="allProviders_so_byr"
														value="false" onclick="enableIds('so_byr')"></td>
													<td><label> For Specific Providers </label></td>
													<td><input type="text" id="specificIds_so_byr"
														name="specificIds" class="idsTextField shadowBox left"
														style="height: 12px; width: 500px;"
														onkeypress="return isNumberAndComma(event);" size="30"
														formInd="so_byr" idType="provider"
														disabled="disabled"></td>
												</tr>
												<tr>
													<td>&nbsp;</td>
													<td colspan="2"><label style="font-size: xx-small;">
															You may enter up to 15 Provider IDs (separated by commas).
													</label></td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div style="padding-left: 0px;">
											<div class="grayModuleContent"
												style="margin-top: 5px; padding-left: 10px; width: 65%">
												<table cellpadding="10" id="reportTable" width="100%;">
													<tr>
														<td><input type="radio" id="calendarYr_so_byr"
															name="yearRadio_so_byr" value="true" checked="checked"
															onclick="clearCalendar('so_byr')"></td>
														<td><label> Calendar Year </label></td>
														<td height="25px"><select id="yearSelect_so_byr"
															class="yearSelect" name="yearSelect"
															style="width: 105px;">
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
														<td><input type="radio" id="range_so_byr"
															name="yearRadio_so_byr" value="false"
															onclick="disableYear('so_byr')"></td>
														<td><label> Date Range </label></td>
														<td height="25px"><input type="text"
															id="fromDate_so_byr" dojoType="dijit.form.DateTextBox"
															class="dateClass" name="dojoCalendarFromDate"
															style="height: 12px; border-color: #FFBF00; border-width: 1px; width: 120px;"
															lang="en-us" onblur="validateDate(this);"
															disabled="disabled" /> to <input type="text"
															id="toDate_so_byr" dojoType="dijit.form.DateTextBox"
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
										</div>
									</td>
								</tr>
							</table>							
							<div
								style="padding-top: 5px; padding-left: 10px; height: 50px; padding-bottom: 10px;">
								<table>
									<tr>
										<td><input id="display_so_byr" class="button action"
											type="button" name="submit"
											style="width: 100px; font-weight: bold; font-size: 10px;"
											value="Submit" formInd="so_byr" display="display"
											onclick="return validateForm(this);" /></td>
										<td style="padding-left: 15px;"></td>
									</tr>
								</table>
							</div>
						</form>
						<c:set var="exportStatus" value="${exportStatus_buyer_so}"
							scope="request">
						</c:set>
						<c:set var="formInd" value="so_byr" scope="request">
						</c:set>
						<c:set var="statusMsg" value="${buyer_so_stat_msg}"
							scope="request">
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
		<c:remove var="successMsg" scope="session"/>
		<c:remove var="formType" scope="session"/>
		<c:remove var="errorMessage" scope="session" />
</body>
</html>
