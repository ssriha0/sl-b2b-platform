<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

<jsp:useBean id="date" class="java.util.Date" />
<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
<div id="overLay"
	style="display: none; z-index: 1000; width: 100%; height: 100%; position: fixed; opacity: 0.4; filter: alpha(opacity =   40); top: 0px; left: 0px; background-color: #E8E8E8; cursor: wait;"></div>
<form action="" id="report_tax_byr" name="report_tax_byr">
	<input type="hidden" id="form_name_tax_byr" name="form_name"
		value="Buyer_TaxId_Report">
	<div class="grayModuleHdr">Buyer Payments by TaxpayerID Report</div>
	<div class="grayModuleContent">
		<c:if test="${formType== 'tax_byr' && not empty successMsg }">
			<div id="successMsg" class="successBox">
				<p class="successMsg">${successMsg}</p>
			</div>
		</c:if>
		<div style="padding-left: 5px; padding-top: 2px; padding-bottom: 2px;">
			<div id="errReport_tax_byr" class="errorBox" style="display: none;"></div>
		</div>
		<table width="100%">
			<tr>
				<td width="45%">
					<div class="grayModuleContent mainWellContent"
						style="padding: 5px 5px 5px 5px;">
						<table width="100%" cellpadding="10" id="reportTable">
							<tr>
								<td>&nbsp;</td>
								<td>Select Buyer(s)</td>
								<td height="25px"><input type="text" id="buyerIds_tax_byr"
									name="buyerIds" class="idsTextField shadowBox left"
									style="height: 12px; width: 250px;"
									onkeypress="return isNumberAndComma(event);" size="30"
									formInd="tax_byr" idType="buyer" /></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td colspan="2" style="font-size: xx-small;" height="40px">
									You may enter up to 15 Buyer IDs (separated by commas).</td>
							</tr>
							<tr>
								<td align="center"><input type="radio"
									id="allProviders_tax_byr" name="allProviders_tax_byr"
									value="true" checked="checked" onclick="clearIds('tax_byr')"></td>
								<td><label> For all Providers </label></td>
								<td height="30px;">&nbsp;</td>
							</tr>
							<tr>
								<td align="center"><input type="radio"
									id="specific_tax_byr" name="allProviders_tax_byr" value="false"
									onclick="enableIds('tax_byr')"></td>
								<td><label> For Specific Providers </label></td>
								<td><input type="text" id="specificIds_tax_byr"
									name="specificIds" class="idsTextField shadowBox left"
									style="height: 12px; width: 250px;"
									onkeypress="return isNumberAndComma(event);" size="30"
									formInd="tax_byr" idType="provider"
									disabled="disabled" /></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td colspan="2"><label style="font-size: xx-small;">
										You may enter up to 15 Provider IDs (separated by commas). </label></td>
							</tr>
						</table>
					</div>
				</td>
				<td width="45%">
					<div style="padding-left: 15px;">
						<div class="grayModuleContent"
							style="padding: 5px 5px 5px 5px; width: 90%">
							<table cellpadding="10" id="reportTable" width="100%;">
								<tr>
									<td><input type="radio" id="calendarYr_tax_byr"
										name="yearRadio_tax_byr" value="true" checked="checked"
										onclick="clearCalendar('tax_byr')"></td>
									<td><label> Calendar Year </label></td>
									<td height="25px"><select id="yearSelect_tax_byr"
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
									<td><input type="radio" id="range_tax_byr"
										name="yearRadio_tax_byr" value="false"
										onclick="disableYear('tax_byr')"></td>
									<td><label> Date Range </label></td>
									<td height="25px"><input type="text" id="fromDate_tax_byr"
										dojoType="dijit.form.DateTextBox" class="dateClass"
										name="dojoCalendarFromDate"
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
			<tr>
				<td>
					<div style="padding-left: 0px;"></div>
				</td>
			</tr>
		</table>
		<div style="padding-top: 15px; padding-left: 10px; height: 50px;">
			<input id="display_tax_byr" class="reportButton button action"
				type="button" name="submit"
				style="width: 110px; font-weight: bold; font-size: 10px;"
				value="Submit" formInd="tax_byr" display="display"
				onclick="return validateForm(this);"> <span
				style="width: 15px;">&nbsp;</span>
		</div>
		<c:set var="exportStatus" value="${exportStatus_buyer_tax_id}"
			scope="request">
		</c:set>
		<c:set var="formInd" value="tax_byr" scope="request">
		</c:set>
		<c:set var="statusMsg" value="${buyer_id_stat_msg}" scope="request">
		</c:set>
		<jsp:include page="/jsp/finance_manager/body/tabs/report_status.jsp" />
	</div>
</form>
<form action="" id="report_so_byr" name="report_so_byr">
	<input type="hidden" id="form_name_so_byr" name="form_name"
		value="Buyer_So_Report">
	<div class="grayModuleHdr">Buyer Payments by Service Order Report
	</div>
	<div class="grayModuleContent">
		<c:if test="${formType== 'so_byr' && not empty successMsg }">
			<div id="successMsg" class="successBox">
				<p class="successMsg">${successMsg}</p>
			</div>
		</c:if>
		<div style="padding-left: 5px; padding-top: 2px; padding-bottom: 2px;">
			<div id="errReport_so_byr" class="errorBox" style="display: none;"></div>
		</div>
		<table width="100%">
			<tr>
				<td width="45%">
					<div class="grayModuleContent mainWellContent"
						style="padding: 5px 5px 5px 5px;">
						<table width="100%" cellpadding="10" id="reportTable">
							<tr>
								<td>&nbsp;</td>
								<td>Select Buyer(s)</td>
								<td><input type="text" id="buyerIds_so_byr" name="buyerIds"
									class="idsTextField shadowBox left"
									style="height: 12px; width: 250px;"
									onkeypress="return isNumberAndComma(event);" size="30"
									formInd="so_byr" idType="buyer" /></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td colspan="2" style="font-size: xx-small;" height="40px">
									You may enter up to 15 Buyer IDs (separated by commas).</td>
							</tr>
							<tr>
								<td align="center"><input type="radio"
									id="allProviders_so_byr" name="allProviders_so_byr"
									value="true" checked="checked" onclick="clearIds('so_byr')"></td>
								<td><label> For all Providers </label></td>
								<td height="25px;">&nbsp;</td>
							</tr>
							<tr>
								<td align="center"><input type="radio" id="specific_so_byr"
									name="allProviders_so_byr" value="false"
									onclick="enableIds('so_byr')"></td>
								<td><label> For Specific Providers </label></td>
								<td><input type="text" id="specificIds_so_byr"
									name="specificIds" class="idsTextField shadowBox left"
									style="height: 12px; width: 250px;"
									onkeypress="return isNumberAndComma(event);" size="30"
									formInd="so_byr" idType="provider"
									disabled="disabled" /></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td colspan="2"><label style="font-size: xx-small;">
										You may enter up to 15 Provider IDs (separated by commas). </label></td>
							</tr>
						</table>
					</div>
				</td>
				<td width="45%">
					<div style="padding-left: 15px;">
						<div class="grayModuleContent"
							style="padding: 5px 5px 5px 5px; width: 90%">
							<table cellpadding="10" id="reportTable" width="100%;">
								<tr>
									<td><input type="radio" id="calendarYr_so_byr"
										name="yearRadio_so_byr" value="true" checked="checked"
										onclick="clearCalendar('so_byr')"></td>
									<td><label> Calendar Year </label></td>
									<td height="25px"><select id="yearSelect_so_byr"
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
									<td><input type="radio" id="range_so_byr"
										name="yearRadio_so_byr" value="false"
										onclick="disableYear('so_byr')"></td>
									<td><label> Date Range </label></td>
									<td height="25px"><input type="text" id="fromDate_so_byr"
										dojoType="dijit.form.DateTextBox" class="dateClass"
										name="dojoCalendarFromDate"
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
			<tr>
				<td>
					<div style="padding-left: 0px;"></div>
				</td>
			</tr>
		</table>
		<div style="padding-top: 15px; padding-left: 10px; height: 50px;">
			<input id="display_so_byr" class="reportButton button action"
				type="button" name="submit"
				style="width: 110px; font-weight: bold; font-size: 10px;"
				value="Submit" formInd="so_byr" display="display"
				onclick="return validateForm(this);"> <span
				style="width: 15px;">&nbsp;</span>
		</div>
		<c:set var="exportStatus" value="${exportStatus_buyer_so}"
			scope="request">
		</c:set>
		<c:set var="formInd" value="so_byr" scope="request">
		</c:set>
		<c:set var="statusMsg" value="${buyer_so_stat_msg}" scope="request">
		</c:set>
		<jsp:include page="/jsp/finance_manager/body/tabs/report_status.jsp" />
	</div>
</form>




<form action="" id="report_so_prov" name="report_so_prov">
	<input type="hidden" id="form_name_so_prov" name="form_name"
		value="ProviderPaymentReportBySO">
	<div class="grayModuleHdr">Provider Payments by Service Order
		Report</div>
	<div class="grayModuleContent">
		<c:if test="${formType== 'so_prov' && not empty successMsg }">
			<div id="successMsg" class="successBox">
				<p class="successMsg">${successMsg}</p>
			</div>
		</c:if>
		<div style="padding-left: 5px; padding-top: 2px; padding-bottom: 2px;">
			<div id="errReport_so_prov" class="errorBox" style="display: none;"></div>
		</div>
		<table width="100%">
			<tr>
				<td width="45%" rowspan="2" height="100%">
					<div class="grayModuleContent mainWellContent"
						style="padding: 5px 5px 5px 5px;">
						<table width="100%" cellpadding="10" id="reportTable">
							<tr>
								<td>&nbsp;</td>
								<td height="25px">Select Provider(s)</td>
								<td><input type="text" id="providerIds_so_prov"
									name="providerIds" class="idsTextField shadowBox left"
									style="height: 12px; width: 250px;"
									onkeypress="return isNumberAndComma(event);" size="30"
									formInd="so_prov" idType="provider"
									/></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td colspan="2" style="font-size: xx-small;" height="40px">
									You may enter up to 15 Provider IDs (separated by commas).</td>
							</tr>
							<tr>
								<td align="center"><input type="radio"
									id="allBuyers_so_prov" name="allBuyers_so_prov" value="true"
									checked="checked" onclick="clearIds('so_prov')"></td>
								<td><label> For all Buyers </label></td>
								<td height="25px;">&nbsp;</td>
							</tr>
							<tr>
								<td align="center"><input type="radio"
									id="specific_so_prov" name="allBuyers_so_prov" value="false"
									onclick="enableIds('so_prov')"></td>
								<td><label> For Specific Buyers </label></td>
								<td><input type="text" id="specificIds_so_prov"
									name="specificIds" class="idsTextField shadowBox left"
									style="height: 12px; width: 250px;"
									onkeypress="return isNumberAndComma(event);" size="30"
									formInd="so_prov" idType="buyer"
									disabled="disabled" /></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td colspan="2"><label style="font-size: xx-small;">
										You may enter up to 15 Buyer IDs (separated by commas). </label></td>
							</tr>
						</table>
					</div>
				</td>
				<td width="45%">
					<div style="padding-left: 15px;">
						<div class="grayModuleContent"
							style="padding: 5px 5px 5px 5px; width: 90%">
							<table cellpadding="10" id="reportTable">
								<tr>
									<td><input type="radio" id="payment_so_prov"
										name="reportBy_so_prov" value="true" checked="checked"></td>
									<td><label> Report by Payment Date </label></td>
								</tr>
								<tr>
									<td><input type="radio" id="complete_so_prov"
										name="reportBy_so_prov" value="false"></td>
									<td><label> Report by Completion Date </label></td>
								</tr>
							</table>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td width="45%">
					<div style="padding-left: 15px;">
						<div class="grayModuleContent"
							style="padding: 5px 5px 5px 5px; width: 90%">
							<table cellpadding="10" id="reportTable" width="100%;">
								<tr>
									<td><input type="radio" id="calendarYr_so_prov"
										name="yearRadio_so_prov" value="true" checked="checked"
										onclick="clearCalendar('so_prov')"></td>
									<td><label> Calendar Year </label></td>
									<td height="25px"><select id="yearSelect_so_prov"
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
									<td><input type="radio" id="range_so_prov"
										name="yearRadio_so_prov" value="false"
										onclick="disableYear('so_prov')"></td>
									<td><label> Date Range </label></td>
									<td height="25px"><input type="text" id="fromDate_so_prov"
										dojoType="dijit.form.DateTextBox" class="dateClass"
										name="dojoCalendarFromDate"
										style="height: 12px; border-color: #FFBF00; border-width: 1px; width: 120px;"
										lang="en-us" onblur="validateDate(this);"
										disabled="disabled" /> to <input type="text"
										id="toDate_so_prov" dojoType="dijit.form.DateTextBox"
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
		<div style="padding-left: 5px; padding-top: 2px; padding-bottom: 2px;">
			<div id="errReportCount_so_prov" class="errorBox"
				style="display: none;"></div>
		</div>
		<div style="padding-top: 15px; padding-left: 10px; height: 50px;">
			<input id="display_so_prov" class="reportButton button action"
				type="button" name="submit"
				style="width: 110px; font-weight: bold; font-size: 10px;"
				value="Submit" formInd="so_prov" display="display"
				onclick="return validateForm(this);"> <span
				style="width: 15px;">&nbsp;</span>
		</div>
		<c:set var="exportStatus" value="${exportStatus_prov_so}"
			scope="request">
		</c:set>
		<c:set var="statusMsg" value="${prov_so_stat_msg}" scope="request">
		</c:set>
		<c:set var="formInd" value="so_prov" scope="request">
		</c:set>
		<jsp:include page="/jsp/finance_manager/body/tabs/report_status.jsp" />
	</div>
</form>


<form action="" id="report_rev_prov" name="report_rev_prov">
	<input type="hidden" id="form_name_rev_prov" name="form_name"
		value="Provider_Rev_Report">
	<div class="grayModuleHdr">Provider Revenue Summary Report</div>
	<div class="grayModuleContent">
		<c:if test="${formType== 'rev_prov' && not empty successMsg }">
			<div id="successMsg" class="successBox">
				<p class="successMsg">${successMsg}</p>
			</div>
		</c:if>
		<div style="padding-left: 5px; padding-top: 2px; padding-bottom: 2px;">
			<div id="errReport_rev_prov" class="errorBox" style="display: none;"></div>
		</div>
		<table width="100%">
			<tr>
				<td width="45%" rowspan="2" height="100%">
					<div class="grayModuleContent mainWellContent"
						style="padding: 5px 5px 5px 5px;">
						<table width="100%" cellpadding="10" id="reportTable">
							<tr>
								<td>&nbsp;</td>
								<td height="25px">Select Provider(s)</td>
								<td><input type="text" id="providerIds_rev_prov"
									name="providerIds" class="idsTextField shadowBox left"
									style="height: 12px; width: 250px;"
									onkeypress="return isNumberAndComma(event);" size="30"
									formInd="rev_prov" idType="provider"
									 /></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td colspan="2" style="font-size: xx-small;" height="40px">
									You may enter up to 15 Provider IDs (separated by commas).</td>
							</tr>
							<tr>
								<td align="center"><input type="radio"
									id="allBuyers_rev_prov" name="allBuyers_rev_prov" value="true"
									checked="checked" onclick="clearIds('rev_prov')"></td>
								<td><label> For all Buyers </label></td>
								<td height="25px;">&nbsp;</td>
							</tr>
							<tr>
								<td align="center"><input type="radio"
									id="specific_rev_prov" name="allBuyers_rev_prov" value="false"
									onclick="enableIds('rev_prov')"></td>
								<td><label> For Specific Buyers </label></td>
								<td><input type="text" id="specificIds_rev_prov"
									name="specificIds" class="idsTextField shadowBox left"
									style="height: 12px; width: 250px;"
									onkeypress="return isNumberAndComma(event);" size="30"
									formInd="rev_prov" idType="buyer"
									disabled="disabled" /></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td colspan="2"><label style="font-size: xx-small;">
										You may enter up to 15 Buyer IDs (separated by commas). </label></td>
							</tr>
						</table>
					</div>
				</td>
				<td width="45%">
					<div style="padding-left: 15px;">
						<div class="grayModuleContent"
							style="padding: 5px 5px 5px 5px; width: 90%">
							<table cellpadding="10" id="reportTable">
								<tr>
									<td><input type="radio" id="payment_rev_prov"
										name="reportBy_rev_prov" value="true" checked="checked"></td>
									<td><label> Report by Payment Date </label></td>
								</tr>
								<tr>
									<td><input type="radio" id="complete_rev_prov"
										name="reportBy_rev_prov" value="false"></td>
									<td><label> Report by Completion Date </label></td>
								</tr>
							</table>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td width="45%">
					<div style="padding-left: 15px;">
						<div class="grayModuleContent"
							style="padding: 5px 5px 5px 5px; width: 90%">
							<table cellpadding="10" id="reportTable" width="100%;">
								<tr>
									<td><input type="radio" id="calendarYr_rev_prov"
										name="yearRadio_rev_prov" value="true" checked="checked"
										onclick="clearCalendar('rev_prov')"></td>
									<td><label> Calendar Year </label></td>
									<td height="25px"><select id="yearSelect_rev_prov"
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
									<td><input type="radio" id="range_rev_prov"
										name="yearRadio_rev_prov" value="false"
										onclick="disableYear('rev_prov')"></td>
									<td><label> Date Range </label></td>
									<td height="25px"><input type="text"
										id="fromDate_rev_prov" dojoType="dijit.form.DateTextBox"
										class="dateClass" name="dojoCalendarFromDate"
										style="height: 12px; border-color: #FFBF00; border-width: 1px; width: 120px;"
										lang="en-us" onblur="validateDate(this);"
										disabled="disabled" /> to <input type="text"
										id="toDate_rev_prov" dojoType="dijit.form.DateTextBox"
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
		<div style="padding-left: 5px; padding-top: 2px; padding-bottom: 2px;">
			<div id="errReportCount_rev_prov" class="errorBox"
				style="display: none;"></div>
		</div>
		<div style="padding-top: 15px; padding-left: 10px; height: 50px;">
			<input id="display_rev_prov" class="reportButton button action"
				type="button" name="submit"
				style="width: 110px; font-weight: bold; font-size: 10px;"
				value="Submit" formInd="rev_prov" display="display"
				onclick="return validateForm(this);"> <span
				style="width: 15px;">&nbsp;</span>
		</div>
		<c:set var="exportStatus" value="${exportStatus_prov_rev}"
			scope="request">
		</c:set>
		<c:set var="formInd" value="rev_prov" scope="request">
		</c:set>
		<c:set var="statusMsg" value="${prov_rev_stat_msg}" scope="request">
		</c:set>
		<jsp:include page="/jsp/finance_manager/body/tabs/report_status.jsp" />
	</div>
</form>

