<%@page
	import="com.newco.marketplace.interfaces.OrderConstants,com.newco.marketplace.web.dto.ServiceOrderDTO,com.newco.marketplace.web.constants.SOConstants,org.apache.struts2.ServletActionContext,com.newco.marketplace.constants.Constants,com.newco.marketplace.util.PropertiesUtils;"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<c:set var="SERVICE_ORDER_ID" scope="request"
	value="<%=request.getAttribute("SERVICE_ORDER_ID")%>" />
<c:set var="so" scope="request"
	value="<%=request.getAttribute("THE_SERVICE_ORDER")%>" />
<c:set var="searsBuyerId" scope="request"
	value="<%=PropertiesUtils.getPropertyValue(Constants.AppPropConstants.SEARS_BUYER_ID)%>" />
<c:set var="noJs" value="true" scope="request" />
<c:set var="noCss" value="true" scope="request" />



<div id="check2" style="display: none">
	<div id="check222" style="visibility: hidden">
	</div>

	<form action="/MarketFrontend/providerConditionalOffer.action"
		method="POST" id="conditionalOfferForm" name="acceptConditions2">
		<div class="modalheader">
			<b>Accept With Conditions GGGG</b>
		</div>
		<div class="modalheaderoutline">
			<div class="acceptWithConditionsFrame1" id="rescheduleServiceDate">

				<div class="acceptWithConditionsFrameBody">
					<b>Conditions</b>
					<br />
					<br />
					<select class="rejectServiceOrderSelectBox" id="condOfferReshedule"
						onchange="getNextModalNew('modal2','condOfferReshedule')">
						<option value="1" selected="selected">
							Reschedule Service Date
						</option>
						<option value="2">
							Increase Maximum Price
						</option>
						<option value="3">
							Reschedule Service Date & Maximum Price
						</option>
					</select>
					<br />
					<div id="counterOfferReasons1"></div>
					<br />
					<table cellpadding="0px" cellspacing="0px">
						<tr>
							<td>
								<strong>Date:</strong>
							</td>
							<td width="10px"></td>
							<td>
								<input type="radio" name="modal2Radio"
									onclick="getSpecificDateModalNew('modal2','modal3','condOfferReshedule')" />
							</td>
							<td width="2px"></td>
							<td>
								Specific
							</td>
							<td width="10px"></td>
							<td>
								<input type="radio" id="modal2RadioChecked" name="modal2Radio"
									checked="checked" />
							</td>
							<td width="2px"></td>
							<td>
								Range
							</td>
						</tr>
					</table>
					<br />
					<strong>Service Window:</strong>
					<table cellspacing="0px"
						style="background-color: #ededed; border: 4px solid #ededed; line-height: 18px">
						<tr>
							<td colspan="3">
								Date
							</td>
						</tr>

						<div id="check333" class="divContainerUp"
							style="visibility: hidden">

						</div>
						<tr>
							<td>
								<input type="hidden" name="conditionalChangeDate1"
									id="modal2ConditionalChangeDate1" />
								<input type="text"
									onFocus="findPosDate(this,'check333');showCalendarControl(this,'modal2ConditionalChangeDate1');"
									class="shadowBox" onkeyup="findPosDate(this,'check333')"
									onblur="hidMsg('check333');assignDate(this,'modal2ConditionalChangeDate1');"
									style="width: 90px; position: relative;" id="" name=""
									constraints="{min: '${todaysDate}'}" required="true"
									lang="en-us" />
							</td>

							<td>
								&nbsp;to&nbsp;
							</td>
							<td>
								<input type="hidden" name="conditionalChangeDate2"
									id="modal2ConditionalChangeDate2" />
								<input type="text"
									onFocus="findPosDate(this,'check333');showCalendarControl(this,'modal2ConditionalChangeDate2');"
									class="shadowBox" onkeyup="findPosDate(this,'check333')"
									onblur="hidMsg('check333');assignDate(this,'modal2ConditionalChangeDate2');"
									style="width: 90px; position: relative;" id="" name=""
									constraints="{min: '${todaysDate}'}" required="true"
									lang="en-us" />
							</td>
						</tr>
						<tr>
							<td colspan="3">
								Time
							</td>
						</tr>
						<tr>
							<td>
								<s:select cssStyle="width: 90px;" id="conditionalStartTime"
									name="conditionalStartTime"
									list="#application['time_intervals']" listKey="descr"
									listValue="descr" multiple="false" size="1" theme="simple" />
							</td>
							<td>
								&nbsp;to&nbsp;
							</td>
							<td>
								<s:select cssStyle="width: 90px;" id="conditionalEndTime"
									name="conditionalEndTime" list="#application['time_intervals']"
									listKey="descr" listValue="descr" multiple="false" size="1"
									theme="simple" />
							</td>
						</tr>
					</table>
					<br />
					<br />

				</div>
				<strong>Offer Expiration:</strong>
				<table cellspacing="0px"
					style="background-color: #ededed; border: 4px solid #ededed; line-height: 28px">
					<tr>
						<td>
							Date
						</td>
						<td>
							&nbsp;&nbsp;&nbsp;
						</td>
						<td>
							Time
						</td>
					</tr>
					<tr>
						<td>

							<input type="hidden" name="conditionalExpirationDate"
								id="modal2ConditionalExpirationDate" />
							<input type="text"
								onFocus="findPosDate(this,'check333');showCalendarControl(this,'modal2ConditionalExpirationDate');"
								class="shadowBox" onkeyup="findPosDate(this,'check333')"
								onblur="hidMsg('check333');assignDate(this,'modal2ConditionalExpirationDate');"
								style="width: 90px; position: relative" id="" name=""
								constraints="{min: '${todaysDate}'}" required="true"
								lang="en-us" />
						</td>
						<td>
							&nbsp;at&nbsp;
						</td>
						<td>
							<s:select cssStyle="width: 90px;" id="conditionalExpirationTime"
								name="conditionalExpirationTime"
								list="#application['time_intervals']" listKey="descr"
								listValue="descr" multiple="false" size="1" theme="simple" />
						</td>
					</tr>
				</table>
				<br />
				<br />
				<input type="image"
					src="${staticContextPath}/images/common/spacer.gif" width="132"
					height="22"
					onclick="setSelectedCounterOfferReasons('counterOfferReasons1');"
					style="background-image: url(${staticContextPath}/images/btn/acceptWithCond.gif); float: left;"
					class="btnBevel" />
				<a href="#"> <img
						src="${staticContextPath}/images/common/spacer.gif" width="55"
						height="22"
						style="background-image: url(${staticContextPath}/images/btn/cancel.gif); float: right; padding-right: 18px;"
						class="btnBevel modalClose" onclick="cancelModal()" /> </a>
				<div style="clear: both;"></div>
				<br />
				<br />
			</div>
		</div>
	</form>
</div>


<div id="checkModal1" style="display: none">
	<div class="modalheader">
		<b>Accept With Conditions BBB</b>
	</div>
	<div class="modalheaderoutline">
		<div class="acceptWithConditionsFrame" id="acceptWithConditions">
			<div class="acceptWithConditionsFrameBody">
				<b>Conditions</b>
				<br />
				<br />
				<select class="rejectServiceOrderSelectBox"
					id="conditionalOfferList"
					onchange="getNextModalNew('modal1','conditionalOfferList')">
					<option value="0" selected="selected">
						Select Code
					</option>
					<option value="1">
						Reschedule Service Date
					</option>
					<option value="2">
						Increase Maximum Price
					</option>
					<option value="3">
						Reschedule Service Date & Maximum Price
					</option>
				</select>
				<br />
				<br />
				<input type="image"
					src="${staticContextPath}/images/common/spacer.gif" width="55"
					height="22"
					style="background-image: url(${staticContextPath}/images/btn/cancel.gif); float: right; padding-right: 18px;"
					class="btnBevel modalClose" onclick="cancelModal()" />
				<div style="clear: both;"></div>
				<br />
				<br />
			</div>
		</div>
	</div>
</div>
<div id="check4" style="display: none">
	<form action="/MarketFrontend/providerConditionalOffer.action"
		method="POST" id="conditionalOfferForm">
		<div id="check444" class="divContainerUp" style="visibility: hidden">
		</div>
		<div class="modalheader">
			<b>Accept With Conditions CCC</b>
		</div>
		<div class="modalheaderoutline">
			<div class="acceptWithConditionsFrame1">

				<div class="acceptWithConditionsFrameBody">
					<b>Conditions</b>
					<br />
					<br />
					<%--<select class="rejectServiceOrderSelectBox">
						<option>
							Increase To
						</option>
					</select> --%>
					<select class="rejectServiceOrderSelectBox"
						id="condOfferSpendLimit"
						onchange="getNextModalNew('modal4','condOfferSpendLimit')">
						<option value="1">
							Reschedule Service Date2
						</option>
						<option value="2" selected="selected">
							Increase Maximum Price
						</option>
						<option value="3">
							Reschedule Service Date & Maximum Price
						</option>
					</select>
					<br />
					<div id="counterOfferReasons2"></div>
					<br />
					<strong>Amount:</strong>
					<table cellspacing="0px"
						style="background-color: #ededed; border: 4px solid #ededed; line-height: 18px">
						<tr>
							<td>
								&nbsp;Increase&nbsp;to&nbsp;
							</td>
							<td>
								$
								<%--Begin Accept SO Modal Scripts --%>
								<%--End Accept SO Modal Scripts--%>


								<input type="text" onFocus="findPos(this,'check444')"
									onkeyup="findPos(this,'check444');"
									onblur="hidMsg('check444');" name="conditionalSpendLimit"
									id="conditionalSpendLimit" />
							</td>
						</tr>
					</table>
					<br />
					<br />
					<strong>Offer Expiration:</strong>
					<table cellspacing="0px"
						style="background-color: #ededed; border: 4px solid #ededed; line-height: 28px">
						<tr>
							<td>
								Date
							</td>
							<td>
								Time
							</td>
						</tr>
						<tr>
							<td>
								<input type="hidden" id="modal4ConditionalExpirationDate"
									name="conditionalExpirationDate" />
								<input type="text"
									onFocus="findPosDate(this,'check444');showCalendarControl(this,'modal4ConditionalExpirationDate');"
									class="shadowBox" onkeyup="findPosDate(this,'check444')"
									onblur="hidMsg('check444');assignDate(this,'modal4ConditionalExpirationDate');"
									style="width: 90px; position: relative" name="" id=""
									constraints="{min: '${todaysDate}'}" required="true"
									lang="en-us" />
							</td>
							<td>
								&nbsp;at&nbsp;
							</td>
							<td>
								<s:select cssStyle="width: 90px;" id="conditionalExpirationTime"
									name="conditionalExpirationTime"
									list="#application['time_intervals']" listKey="descr"
									listValue="descr" multiple="false" size="1" theme="simple" />
							</td>
						</tr>
					</table>
					<br />
					<br />
					<input type="image"
						src="${staticContextPath}/images/common/spacer.gif" width="132"
						height="22"
						onclick="setSelectedCounterOfferReasons('counterOfferReasons2');"
						style="background-image: url(${staticContextPath}/images/btn/acceptWithCond.gif); float: left;"
						class="btnBevel" />
					<a href="#"> <img
							src="${staticContextPath}/images/common/spacer.gif" width="55"
							height="22"
							style="background-image: url(${staticContextPath}/images/btn/cancel.gif); float: right; padding-right: 18px;"
							class="btnBevel modalClose" onclick="cancelModal()" /> </a>
					<div style="clear: both;"></div>
					<br />
					<br />
				</div>
			</div>
		</div>
	</form>
</div>
<div id="check5" style="display: none">
	<form action="/MarketFrontend/providerConditionalOffer.action"
		method="POST" id="conditionalOfferForm">
		<div id="check555" class="divContainerUp" style="visibility: hidden">
		</div>
		<div class="modalheader">
			<b>Accept With Conditions DDD</b>
		</div>
		<div class="modalheaderoutline">
			<div class="acceptWithConditionsFrameBody3">
				<b>Conditions</b>
				<br />
				<br />
				<select class="rejectServiceOrderSelectBox"
					id="condOfferResheduleSpendLimit"
					onchange="getNextModalNew('modal5','condOfferResheduleSpendLimit')">
					<option value="1">
						Reschedule Service Date
					</option>
					<option value="2">
						Increase Maximum Price
					</option>
					<option value="3" selected="selected">
						Reschedule Service Date & Maximum Price
					</option>
				</select>
				<br />
				<div id="counterOfferReasons3"></div>
				<br />
				<table cellpadding="0px" cellspacing="0px">
					<tr>
						<td>
							<strong>Date:</strong>
						</td>
						<td width="10px"></td>
						<td>
							<input type="radio" name="modal5Radio"
								onclick="getSpecificDateModalNew('modal5','modal5-1','condOfferResheduleSpendLimit')" />
						</td>
						<td width="2px"></td>
						<td>
							Specific
						</td>
						<td width="10px"></td>
						<td>
							<input type="radio" id="modal5RadioChecked" name="modal5Radio"
								checked="checked" />
						</td>
						<td width="2px"></td>
						<td>
							Range
						</td>
					</tr>
				</table>
				<br />
				<strong>Service Window:</strong>
				<table cellspacing="0px"
					style="background-color: #ededed; border: 4px solid #ededed; line-height: 18px">
					<tr>
						<td colspan="3">
							Date
						</td>
					</tr>
					<tr>
						<td>
							<input type="hidden" id="modal5ConditionalChangeDate1"
								name="conditionalChangeDate1" />
							<input type="text"
								onFocus="findPosDate(this,'check555');showCalendarControl(this,'modal5ConditionalChangeDate1');"
								class="shadowBox" onkeyup="findPosDate(this,'check555')"
								onblur="hidMsg('check555');assignDate(this,'modal5ConditionalChangeDate1');"
								style="width: 90px; position: relative" name="" id=""
								constraints="{min: '${todaysDate}'}" required="true"
								lang="en-us" />
						</td>
						<td>
							&nbsp;to&nbsp;
						</td>
						<td>
							<input type="hidden" id="modal5ConditionalChangeDate2"
								name="conditionalChangeDate2" />
							<input type="text"
								onFocus="findPosDate(this,'check555');showCalendarControl(this,'modal5ConditionalChangeDate2');"
								class="shadowBox" onkeyup="findPosDate(this,'check555')"
								onblur="hidMsg('check555');assignDate(this,'modal5ConditionalChangeDate2');"
								style="width: 90px; position: relative" name="" id=""
								constraints="{min: '${todaysDate}'}" required="true"
								lang="en-us" />
						</td>
					</tr>
					<tr>
						<td colspan="3">
							Time
						</td>
					</tr>
					<tr>
						<td>
							<s:select cssStyle="width: 90px;" id="conditionalStartTime"
								name="conditionalStartTime"
								list="#application['time_intervals']" listKey="descr"
								listValue="descr" multiple="false" size="1" theme="simple" />
						</td>
						<td>
							&nbsp;to&nbsp;
						</td>
						<td>
							<s:select cssStyle="width: 90px;" id="conditionalEndTime"
								name="conditionalEndTime" list="#application['time_intervals']"
								listKey="descr" listValue="descr" multiple="false" size="1"
								theme="simple" />
						</td>
					</tr>
				</table>
				<br />
				<br />
				<strong>Amount:</strong>
				<table cellspacing="0px"
					style="background-color: #ededed; border: 4px solid #ededed; line-height: 18px">
					<tr>
						<td>
							&nbsp;Increase&nbsp;to&nbsp;
						</td>
						<td>
							$

							<input type="text" onfocus="findPos(this,'check555');"
								onkeyup="findPos(this,'check555');" onblur="hidMsg('check555');"
								name="conditionalSpendLimit" id="conditionalSpendLimit" />
						</td>
					</tr>
				</table>
				<br />
				<br />
				<strong>Offer Expiration:</strong>
				<table cellspacing="0px"
					style="background-color: #ededed; border: 4px solid #ededed; line-height: 28px">
					<tr>
						<td>
							Date
						</td>
						<td>
							&nbsp;&nbsp;&nbsp;
						</td>
						<td>
							Time
						</td>
					</tr>
					<tr>
						<td>
							<input type="hidden" id="modal5ConditionalExpirationDate"
								name="conditionalExpirationDate" />
							<input type="text" onkeyup="findPosDate(this,'check555')"
								onFocus="findPosDate(this,'check555');showCalendarControl(this,'modal5ConditionalExpirationDate');"
								class="shadowBox"
								onblur="hidMsg('check555');assignDate(this,'modal5ConditionalExpirationDate');"
								style="width: 90px; position: relative" name="" id=""
								constraints="{min: '${todaysDate}'}" required="true"
								lang="en-us" />
						</td>
						<td>
							&nbsp;at&nbsp;
						</td>
						<td>
							<s:select cssStyle="width: 90px;" id="conditionalExpirationTime"
								name="conditionalExpirationTime"
								list="#application['time_intervals']" listKey="descr"
								listValue="descr" multiple="false" size="1" theme="simple" />
						</td>
					</tr>
				</table>
				<br />
				<br />
				<input type="image"
					src="${staticContextPath}/images/common/spacer.gif" width="132"
					height="22"
					onclick="setSelectedCounterOfferReasons('counterOfferReasons3');"
					style="background-image: url(${staticContextPath}/images/btn/acceptWithCond.gif); float: left;"
					class="btnBevel" />
				<a href="#"> <img
						src="${staticContextPath}/images/common/spacer.gif" width="55"
						height="22"
						style="background-image: url(${staticContextPath}/images/btn/cancel.gif); float: right; padding-right: 18px;"
						class="btnBevel modalClose" onclick="cancelModal()" /> </a>
				<div style="clear: both;"></div>
				<br />
				<br />
			</div>
		</div>
	</form>
</div>

