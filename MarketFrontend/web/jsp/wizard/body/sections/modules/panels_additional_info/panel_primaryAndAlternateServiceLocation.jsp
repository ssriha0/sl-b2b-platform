<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />	
<s:iterator value="serviceLocationContact.phones" status="pVar">

	
	<s:if
		test="%{serviceLocationContact.phones[#pVar.index].phoneClassId==1}">

		<s:set name="serviceLocContactWorkAreaCode"
			value="%{serviceLocationContact.phones[#pVar.index].areaCode}" />
		<s:set name="serviceLocContactWorkPart1"
			value="%{serviceLocationContact.phones[#pVar.index].phonePart1}" />
		<s:set name="serviceLocContactWorkPart2"
			value="%{serviceLocationContact.phones[#pVar.index].phonePart2}" />
		<s:set name="serviceLocContactWorkExt"
			value="%{serviceLocationContact.phones[#pVar.index].ext}" />
	</s:if>
	<s:elseif
		test="%{serviceLocationContact.phones[#pVar.index].phoneClassId==2}">
		<s:set name="serviceLocContactMobileAreaCode"
			value="%{serviceLocationContact.phones[#pVar.index].areaCode}" />
		<s:set name="serviceLocContactMobilePart1"
			value="%{serviceLocationContact.phones[#pVar.index].phonePart1}" />
		<s:set name="serviceLocContactMobilePart2"
			value="%{serviceLocationContact.phones[#pVar.index].phonePart2}" />
		<s:set name="serviceLocContactMobileExt"
			value="%{serviceLocationContact.phones[#pVar.index].ext}" />
	</s:elseif>
	<s:if
		test="%{serviceLocationContact.phones[#pVar.index].phoneClassId==0}">
		<s:set name="serviceLocContactHomeAreaCode"
			value="%{serviceLocationContact.phones[#pVar.index].areaCode}" />
		<s:set name="serviceLocContactHomePart1"
			value="%{serviceLocationContact.phones[#pVar.index].phonePart1}" />
		<s:set name="serviceLocContactHomePart2"
			value="%{serviceLocationContact.phones[#pVar.index].phonePart2}" />
		<s:set name="serviceLocContactHomeExt"
			value="%{serviceLocationContact.phones[#pVar.index].ext}" />
	</s:if>
	<s:if
		test="%{serviceLocationContact.phones[#pVar.index].phoneClassId==4}">
		<s:set name="serviceLocContactPagerAreaCode"
			value="%{serviceLocationContact.phones[#pVar.index].areaCode}" />
		<s:set name="serviceLocContactPagerPart1"
			value="%{serviceLocationContact.phones[#pVar.index].phonePart1}" />
		<s:set name="serviceLocContactPagerPart2"
			value="%{serviceLocationContact.phones[#pVar.index].phonePart2}" />
		<s:set name="serviceLocContactPagerExt"
			value="%{serviceLocationContact.phones[#pVar.index].ext}" />
	</s:if>
	<s:if
		test="%{serviceLocationContact.phones[#pVar.index].phoneClassId==5}">
		<s:set name="serviceLocContactOtherAreaCode"
			value="%{serviceLocationContact.phones[#pVar.index].areaCode}" />
		<s:set name="serviceLocContactOtherrPart1"
			value="%{serviceLocationContact.phones[#pVar.index].phonePart1}" />
		<s:set name="serviceLocContactOtherPart2"
			value="%{serviceLocationContact.phones[#pVar.index].phonePart2}" />
		<s:set name="serviceLocContactOtherExt"
			value="%{serviceLocationContact.phones[#pVar.index].ext}" />
	</s:if>
	<s:elseif
		test="%{serviceLocationContact.phones[#pVar.index].phoneClassId==6}">

		<s:set name="serviceLocContactFaxAreaCode"
			value="%{serviceLocationContact.phones[#pVar.index].areaCode}" />
		<s:set name="serviceLocContactFaxPart1"
			value="%{serviceLocationContact.phones[#pVar.index].phonePart1}" />
		<s:set name="serviceLocContactFaxPart2"
			value="%{serviceLocationContact.phones[#pVar.index].phonePart2}" />
	</s:elseif>
</s:iterator>

<div dojoType="dijit.TitlePane"
	title="<fmt:message bundle="${serviceliveCopyBundle}"  key="wizard.addinfo.primary.title" />"
	id="" class="contentWellPane">

	<p>
		<fmt:message bundle="${serviceliveCopyBundle}"
			key="wizard.addinfo.primary.description" />
		<br />
		<br />
		<strong><fmt:message bundle="${serviceliveCopyBundle}"
				key="wizard.addinfo.primary.header.servicelocation" />
		</strong>
	<table cellpadding="0" cellspacing="0" class="contactInfoTable"
		style="margin-top:0px;">
		<tr>
			<td class="column1">
				<p>
					${serviceLocationContact.firstName}
					${serviceLocationContact.lastName}
					<br>
					${serviceLocationContact.streetName1} <s:if test="%{serviceLocationContact.aptNo != null}">, Apt ${serviceLocationContact.aptNo}</s:if>
					<s:if test="%{serviceLocationContact.streetName2 != null}">
					<br>
					${serviceLocationContact.streetName2}
					</s:if>
					<br>
					${serviceLocationContact.city}
					<s:if test="%{serviceLocationContact.state != '-1'}">
          , ${serviceLocationContact.state}
          </s:if>
					${serviceLocationContact.zip}
				</p>
			</td>

			<td class="column2">
				<p>
					<strong> <s:iterator value="serviceLocationContact.phones"
							status="pVar">
							<s:if
								test="%{serviceLocationContact.phones[#pVar.index].phoneClassId==1}">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="wizard.addinfo.primary.label.workphone" />
								<br>
							</s:if>
							<s:if
								test="%{serviceLocationContact.phones[#pVar.index].phoneClassId==2}">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="wizard.addinfo.primary.label.mobilephone" />
								<br>
							</s:if>
							<s:if
								test="%{serviceLocationContact.phones[#pVar.index].phoneClassId==0}">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="wizard.addinfo.primary.label.homephone" />
								<br>
							</s:if>
							<s:if
								test="%{serviceLocationContact.phones[#pVar.index].phoneClassId==4}">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="wizard.addinfo.primary.label.pager" />
								<br>
							</s:if>
							<s:if
								test="%{serviceLocationContact.phones[#pVar.index].phoneClassId==5}">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="wizard.addinfo.primary.label.other" />
								<br>
							</s:if>
							<s:if
								test="%{serviceLocationContact.phones[2].areaCode!='' && serviceLocationContact.phones[#pVar.index].phoneClassId==6}">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="wizard.label.fax" />
								<br>
							</s:if>
						</s:iterator> <s:if test="%{serviceLocationContact.email !=null}">
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="wizard.label.email" />
						</s:if> </strong>
				</p>
			</td>
			<td class="column3">
				<p>
					<s:iterator value="serviceLocationContact.phones" status="pVar">
						<s:if
							test="%{serviceLocationContact.phones[#pVar.index].phoneClassId==1}">
							<s:label value="%{serviceLocContactWorkAreaCode}" theme="simple"
								cssStyle="width: 30px;" />
							<s:label value="%{serviceLocContactWorkPart1}" theme="simple"
								cssStyle="width: 30px;" />
							<s:label value="%{serviceLocContactWorkPart2}" theme="simple"
								cssStyle="width: 30px;" />
	      		&nbsp;<b>Ext</b>&nbsp;
				<s:label value="%{serviceLocContactWorkExt}" theme="simple"
								cssStyle="width: 30px;" />
							<br>
						</s:if>

						<s:if
							test="%{serviceLocationContact.phones[#pVar.index].phoneClassId==2}">
							<s:label value="%{serviceLocContactMobileAreaCode}"
								theme="simple" cssStyle="width: 30px;" />
							<s:label value="%{serviceLocContactMobilePart1}" theme="simple"
								cssStyle="width: 30px;" />
							<s:label value="%{serviceLocContactMobilePart2}" theme="simple"
								cssStyle="width: 30px;" />
				&nbsp;<b>Ext</b>&nbsp;
				<s:label value="%{serviceLocContactMobileExt}" theme="simple"
								cssStyle="width: 30px;" />								
							<br>
						</s:if>

						<s:if
							test="%{serviceLocationContact.phones[#pVar.index].phoneClassId==0}">
							<s:label value="%{serviceLocContactHomeAreaCode}" theme="simple"
								cssStyle="width: 30px;" />
							<s:label value="%{serviceLocContactHomePart1}" theme="simple"
								cssStyle="width: 30px;" />
							<s:label value="%{serviceLocContactHomePart2}" theme="simple"
								cssStyle="width: 30px;" />
				&nbsp;<b>Ext</b>&nbsp;
				<s:label value="%{serviceLocContactHomeExt}" theme="simple"
								cssStyle="width: 30px;" />								
							<br>
						</s:if>

						<s:if
							test="%{serviceLocationContact.phones[#pVar.index].phoneClassId==4}">
							<s:label value="%{serviceLocContactPagerAreaCode}" theme="simple"
								cssStyle="width: 30px;" />
							<s:label value="%{serviceLocContactPagerPart1}" theme="simple"
								cssStyle="width: 30px;" />
							<s:label value="%{serviceLocContactPagerPart2}" theme="simple"
								cssStyle="width: 30px;" />
				&nbsp;<b>Ext</b>&nbsp;
				<s:label value="%{serviceLocContactPagerExt}" theme="simple"
								cssStyle="width: 30px;" />								
							<br>
						</s:if>

						<s:if
							test="%{serviceLocationContact.phones[#pVar.index].phoneClassId==5}">
							<s:label value="%{serviceLocContactOtherAreaCode}" theme="simple"
								cssStyle="width: 30px;" />
							<s:label value="%{serviceLocContactOtherrPart1}" theme="simple"
								cssStyle="width: 30px;" />
							<s:label value="%{serviceLocContactOtherPart2}" theme="simple"
								cssStyle="width: 30px;" />
			&nbsp;<b>Ext</b>&nbsp;
				<s:label value="%{serviceLocContactOtherExt}" theme="simple"
								cssStyle="width: 30px;" />								
							<br>
						</s:if>
						<s:if
							test="%{serviceLocationContact.phones[2].areaCode!='' && serviceLocationContact.phones[#pVar.index].phoneClassId==6}">
							<s:label value="%{serviceLocContactFaxAreaCode}" theme="simple"
								cssStyle="width: 30px;" />
							<s:label value="%{serviceLocContactFaxPart1}" theme="simple"
								cssStyle="width: 30px;" />
							<s:label value="%{serviceLocContactFaxPart2}" theme="simple"
								cssStyle="width: 30px;" />
							<br>
						</s:if>


					</s:iterator>
					<s:label value="%{serviceLocationContact.email}" theme="simple"
						cssStyle="width: 30px;" />

				</p>
			</td>

		</tr>
	</table>
	<p>
		<s:checkbox id="altServiceLocationContactFlg"
			name="altServiceLocationContactFlg"
			value="%{altServiceLocationContactFlg}" theme="simple"
			onclick="toggleAltContactDisabled('enabledAltLoc')" />
		<fmt:message bundle="${serviceliveCopyBundle}"
			key="wizard.addinfo.primary.label.checkbox" />
	</p>

	<s:if test="%{altServiceLocationContactFlg}">
		<div id="enabledAltLoc" style="display:block">
	</s:if>
	<s:else>
		<div id="enabledAltLoc" style="display:none">
	</s:else>

	<p>
		<strong><fmt:message bundle="${serviceliveCopyBundle}"
				key="wizard.addinfo.primary.header.altservicelocation" />
		</strong>
	</p>

	<table width="350" cellpadding="0" cellspacing="0">
		<tr>
			<td width="185">
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.label.firstname" />
				<br />
				<s:textfield theme="simple"
					name="alternateLocationContact.firstName"
					id="alternateLocationContact.firstName"
					cssClass="shadowBox grayText" cssStyle="width: 160px;"
					value="%{alternateLocationContact.firstName}" maxlength="100" />
			<td width="165">
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.label.lastname" />
				<br />
				<s:textfield theme="simple" name="alternateLocationContact.lastName"
					id="alternateLocationContact.lastName"
					cssClass="shadowBox grayText" cssStyle="width: 160px;"
					value="%{alternateLocationContact.lastName}" maxlength="100" />
		</tr>
	</table>
	<table width="425" cellpadding="0" cellspacing="0" class="marginBtm">
		<tr>
			<td width="165">
				<tags:fieldError id="phone">
					<fmt:message bundle="${serviceliveCopyBundle}"
						key="wizard.label.phone.num" />
					<br />
					<s:textfield theme="simple"
						name="alternateLocationContact.phones[0].areaCode"
						id="alternateLocationContact.phones[0].areaCode"
						cssClass="shadowBox grayText" cssStyle="width: 30px;"
						value="%{alternateLocationContact.phones[0].areaCode}"
						maxlength="3" />
					-<s:textfield theme="simple"
						name="alternateLocationContact.phones[0].phonePart1"
						id="alternateLocationContact.phones[0].phonePart1"
						cssClass="shadowBox grayText" cssStyle="width: 30px;"
						value="%{alternateLocationContact.phones[0].phonePart1}"
						maxlength="3" />
					-<s:textfield theme="simple"
						name="alternateLocationContact.phones[0].phonePart2"
						id="alternateLocationContact.phones[0].phonePart2"
						cssClass="shadowBox grayText" cssStyle="width: 45px;"
						value="%{alternateLocationContact.phones[0].phonePart2}"
						maxlength="4" />
				</tags:fieldError>
			</td>
			<td width="75">
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.label.ext" />
				<br />
				<s:textfield theme="simple"
					name="alternateLocationContact.phones[0].ext"
					id="alternateLocationContact.phones[0].ext"
					cssClass="shadowBox grayText" cssStyle="width: 55px;"
					value="%{alternateLocationContact.phones[0].ext}" maxlength="5" />

			</td>
			<td width="130">
				<tags:fieldError id="Phone Type">
					<fmt:message bundle="${serviceliveCopyBundle}"
						key="wizard.label.phone.type" />
					<br />

					<s:select name="alternateLocationContact.phones[0].phoneClassId"
						headerKey="-1" headerValue="Select One" cssStyle="width: 125px;"
						size="1" theme="simple" list="phoneTypes" listValue="descr"
						listKey="id" />
					<%--			<s:hidden name="alternateLocationContact.phones[0].phoneType" value="1"/>--%>
				</tags:fieldError>
			</td>
		</tr>
		<tr>
			<td>
				<tags:fieldError id="Alternate_phone">
					<fmt:message bundle="${serviceliveCopyBundle}"
						key="wizard.label.phone.alt" />
					<br />
					<s:textfield theme="simple"
						name="alternateLocationContact.phones[1].areaCode"
						id="alternateLocationContact.phone.areacode"
						cssClass="shadowBox grayText" cssStyle="width: 30px;"
						value="%{alternateLocationContact.phones[1].areaCode}"
						maxlength="3" />
					-<s:textfield theme="simple"
						name="alternateLocationContact.phones[1].phonePart1"
						id="alternateLocationContact.phone.part1"
						cssClass="shadowBox grayText" cssStyle="width: 30px;"
						value="%{alternateLocationContact.phones[1].phonePart1}"
						maxlength="3" />
					-<s:textfield theme="simple"
						name="alternateLocationContact.phones[1].phonePart2"
						id="alternateLocationContact.phone.part2"
						cssClass="shadowBox grayText" cssStyle="width: 45px;"
						value="%{alternateLocationContact.phones[1].phonePart2}"
						maxlength="4" />
				</tags:fieldError>
			</td>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.label.ext" />
				<br />
				<s:textfield theme="simple"
					name="alternateLocationContact.phones[1].ext"
					id="alternateLocationContact.phones[1].ext"
					cssClass="shadowBox grayText" cssStyle="width: 55px;"
					value="%{alternateLocationContact.phones[1].ext}" maxlength="5" />
			</td>
			<td>
				<tags:fieldError id="Alternate Phone Type">
					<fmt:message bundle="${serviceliveCopyBundle}"
						key="wizard.label.phone.type" />
					<br />
					<s:select name="alternateLocationContact.phones[1].phoneClassId"
						headerKey="-1" headerValue="Select One" cssStyle="width: 125px;"
						size="1" theme="simple" list="phoneTypes" listKey="id"
						listValue="descr" />
					<%--			<s:hidden name="alternateLocationContact.phones[1].phoneType" value="2"/>--%>
				</tags:fieldError>
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<tags:fieldError id="Fax">
					<fmt:message bundle="${serviceliveCopyBundle}"
						key="wizard.label.fax" />
					<br />

					<s:textfield theme="simple"
						name="alternateLocationContact.phones[2].areaCode"
						id="alternateLocationContact.fax.areaCode"
						cssClass="shadowBox grayText" cssStyle="width: 30px;"
						value="%{alternateLocationContact.phones[2].areaCode}"
						maxlength="3" />
					-<s:textfield theme="simple"
						name="alternateLocationContact.phones[2].phonePart1"
						id="alternateLocationContact.fax.part1"
						cssClass="shadowBox grayText" cssStyle="width: 30px;"
						value="%{alternateLocationContact.phones[2].phonePart1}"
						maxlength="3" />
					-<s:textfield theme="simple"
						name="alternateLocationContact.phones[2].phonePart2"
						id="alternateLocationContact.fax.part2"
						cssClass="shadowBox grayText" cssStyle="width: 45px;"
						value="%{alternateLocationContact.phones[2].phonePart2}"
						maxlength="4" />
					<s:hidden name="alternateLocationContact.phones[2].phoneType" value="3"/>
					<fmt:message bundle="${serviceliveCopyBundle}"
						key="wizard.addinfo.primary.label.optional" />
				</tags:fieldError>
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<tags:fieldError id="Email">
					<fmt:message bundle="${serviceliveCopyBundle}"
						key="wizard.label.email" />
					<br />
					<s:textfield theme="simple" name="alternateLocationContact.email"
						id="alternateLocationContact.email" cssClass="shadowBox grayText"
						cssStyle="width: 342px;" value="%{alternateLocationContact.email}"
						maxlength="40" />
				</tags:fieldError>

			</td>
		</tr>
	</table>
</div>
</div>


