<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<s:iterator value="scopeOfWorkTabList">
<!-- NEW MODULE/ WIDGET-->
<div dojoType="dijit.TitlePane" title="Schedule" id=""
	class="contentWellPane">
	<fmt:message bundle="${serviceliveCopyBundle}"
		key="wizard.scopeofwork.schedule.description" />
	<p>
		<strong><label>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.scopeofwork.service.date.type" />
			</label> </strong>
		<br />
		<s:if test="%{serviceDateType == 1}">
			<span class="formFieldOffset"> <input type="radio"
					class="antiRadioOffsets" name="serviceDateType" value="1"
					checked="checked" onclick="selectFixedDate()"> Fixed
				&nbsp;&nbsp;&nbsp; <input type="radio" class="antiRadioOffsets"
					name="serviceDateType" value="2" id="serviceDateTypeId"
					onclick="selectRangeDate()"> Range </span>
		</s:if>
		<s:else>
			<span class="formFieldOffset"> <input type="radio"
					class="antiRadioOffsets" name="serviceDateType" value="1"
					onclick="selectFixedDate()"> Fixed &nbsp;&nbsp;&nbsp; <input
					type="radio" class="antiRadioOffsets" name="serviceDateType"
					value="2" onclick="selectRangeDate()" checked="checked">
				Range </span>
		</s:else>

	</p>
	<br />

	<table cellpadding="0" cellspacing="0" class="marginBtm">
		<tr>
			<td width="120">
				<strong><fmt:message bundle="${serviceliveCopyBundle}"
						key="wizard.scopeofwork.date" />
				</strong>
			</td>
			<td width="30"></td>
			<td width="110"></td>
		</tr>
	</table>


	<s:if test="%{serviceDateType == 1}">
		<table>
			<tr>
				<td>
					<table cellpadding="0" cellspacing="0" class="marginBtm">
						<tr>
							<td>
								<tags:fieldError id="Start service Date" oldClass="paddingBtm">
									<input type="text" dojoType="dijit.form.DateTextBox"
										class="shadowBox" id="modal2ConditionalChangeDate1"
										name="serviceDate1"
										value="<s:property value="%{serviceDate1}"/>"
										constraints="{min: '${todaysDate}'}"
										required="true"
										lang="en-us"
											/>
								</tags:fieldError>
							</td>
						</tr>
						<tr>
							<td></td>
						</tr>
						<tr>
							<td colspan="3">
								<strong>Time (Local Time for Service Location)</strong>
							</td>
						</tr>
						<tr>
							<td>
							<tags:fieldError id="Start Time" oldClass="paddingBtm">
								<s:select cssStyle="width: 90px;" id="conditionalStartTime"
									name="startTime" headerKey="[HH:MM]" headerValue="[HH:MM]"
									list="#application['time_intervals']" listKey="descr"
									listValue="descr" multiple="false" size="1" theme="simple" />
							</tags:fieldError>
							</td>
						</tr>
					</table>
				</td>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>

				<td>

					<div id="rangeDate" style="display: none">
						<table cellpadding="0" cellspacing="0" class="marginBtm">
							<tr>
								<td>
									&nbsp;&nbsp;to&nbsp;&nbsp;
								</td>
								<td>
									<tags:fieldError id="End service Date" oldClass="paddingBtm">
										<input type="text" dojoType="dijit.form.DateTextBox"
											class="shadowBox" id="modal2ConditionalChangeDate2"
											name="serviceDate2"
											value="<s:property value="%{serviceDate2}"/>"
											constraints="{min: '${todaysDate}'}"
											required="true"
											lang="en-us"
											/>
									</tags:fieldError>
								</td>
							</tr>
							<tr>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td colspan="2">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;&nbsp;to&nbsp;&nbsp;
								</td>
								<td>
									<tags:fieldError id="End Time" oldClass="paddingBtm">
										<s:select cssStyle="width: 90px;" id="conditionalEndTime"
											name="endTime" headerKey="[HH:MM]" headerValue="[HH:MM]"
											list="#application['time_intervals']" listKey="descr"
											listValue="descr" multiple="false" size="1" theme="simple" />
									</tags:fieldError>

								</td>
							</tr>
						</table>

					</div>


				</td>
			</tr>
		</table>
	</s:if>
	<s:else>
		<table>
			<tr>
				<td>
					<table cellpadding="0" cellspacing="0" class="marginBtm">
						<tr>
							<td>
								<tags:fieldError id="Start service Date" oldClass="paddingBtm">
									<input type="text" dojoType="dijit.form.DateTextBox"
										class="shadowBox" id="modal2ConditionalChangeDate1"
										name="serviceDate1"
										value="<s:property value="%{serviceDate1}"/>"
										constraints="{min: '${todaysDate}'}"
										required="true"
										lang="en-us"
										/>
								</tags:fieldError>
							</td>
						</tr>
						<tr>
							<td></td>
						</tr>
						<tr>
							<td colspan="3">
								<strong>Time (Local Time for Service Location)</strong>
							</td>
						</tr>
						<tr>
							<td>
							<tags:fieldError id="Start Time" oldClass="paddingBtm">
								<s:select cssStyle="width: 90px;" id="conditionalStartTime"
									name="startTime" headerKey="[HH:MM]" headerValue="[HH:MM]"
									list="#application['time_intervals']" listKey="descr"
									listValue="descr" multiple="false" size="1" theme="simple" />
							</tags:fieldError>		
							</td>
						</tr>
					</table>
				</td>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>

				<td>

					<div id="rangeDate">
						<table cellpadding="0" cellspacing="0" class="marginBtm">
							<tr>
								<td>
									&nbsp;&nbsp;to&nbsp;&nbsp;
								</td>
								<td>
									<tags:fieldError id="End service Date" oldClass="paddingBtm">
										<input type="text" dojoType="dijit.form.DateTextBox"
											class="shadowBox" id="modal2ConditionalChangeDate2"
											name="serviceDate2"
											value="<s:property value="%{serviceDate2}"/>"
											constraints="{min: '${todaysDate}'}"
											required="true"
											lang="en-us"
											/>
									</tags:fieldError>
								</td>
							</tr>
							<tr>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td colspan="2">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;&nbsp;to&nbsp;&nbsp;
								</td>
								<td>
									<tags:fieldError id="End Time" oldClass="paddingBtm">
										<s:select cssStyle="width: 90px;" id="conditionalEndTime"
											name="endTime" headerKey="[HH:MM]" headerValue="[HH:MM]"
											list="#application['time_intervals']" listKey="descr"
											listValue="descr" multiple="false" size="1" theme="simple" />
									</tags:fieldError>

								</td>
							</tr>
						</table>

					</div>


				</td>
			</tr>
		</table>
	</s:else>



	<br />
	<p>
		<strong><fmt:message bundle="${serviceliveCopyBundle}"
				key="wizard.scopeofwork.schedule.provider.confirm" />
		</strong>
	</p>
	<p class="formFieldOffset">
		<s:if test="%{confirmServiceTime == 0}">
			<input type="radio" class="antiRadioOffsets"
				name="confirmServiceTime" value="1" />
			<fmt:message bundle="${serviceliveCopyBundle}" key="yes" /> &nbsp;&nbsp;&nbsp;
			<input type="radio" class="antiRadioOffsets"
				name="confirmServiceTime" value="0" checked="checked" />
			<fmt:message bundle="${serviceliveCopyBundle}" key="no" />
		</s:if>
		<s:else>
			<input type="radio" class="antiRadioOffsets"
				name="confirmServiceTime" value="1" checked="checked" />
			<fmt:message bundle="${serviceliveCopyBundle}" key="yes" /> &nbsp;&nbsp;&nbsp;
			<input type="radio" class="antiRadioOffsets"
				name="confirmServiceTime" value="0" />
			<fmt:message bundle="${serviceliveCopyBundle}" key="no" />
		</s:else>
	</p>
</div>
</s:iterator>