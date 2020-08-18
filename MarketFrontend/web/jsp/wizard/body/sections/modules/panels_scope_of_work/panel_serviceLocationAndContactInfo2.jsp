<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<s:iterator value="scopeOfWorkTabList">
<!-- NEW MODULE/ WIDGET-->
<div dojoType="dijit.TitlePane"
	title="Service Location & Contact Information" id=""
	class="contentWellPane">
	<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.serv.location.description"/>
	<p>
		<label>
			<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.serv.location.loc.type"/>
		</label>
		<br />

		<span class="formFieldOffset">
			<s:radio list="locationType" name="serviceLocationContact.locationTypeId" id="serviceLocationContact.locationTypeId" cssClass="antiRadioOffsets" value="serviceLocationContact.locationTypeId"/> 
		</span>
	</p>
	<table width="360" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2">
				<label>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.bus.name"/>
				</label>
				<br />
				<s:textfield theme="simple" name="serviceLocationContact.businessName" id="serviceLocationContact.businessName"
					cssClass="shadowBox grayText" cssStyle="width: 348px;"
					value="%{serviceLocationContact.businessName}" 
					maxlength="100"/>
			</td>
		</tr>
		<tr>
			<td width="185">
				<label>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.firstname"/>
				</label>
				<br />
				<s:textfield theme="simple" name="serviceLocationContact.firstName" id="serviceLocationContact.firstName"
					cssClass="shadowBox grayText" cssStyle="width: 160px;"
					value="%{serviceLocationContact.firstName}"  
					maxlength="50"/>
			</td>
			<td width="165">
				<label>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.lastname"/>
				</label>
				<br />
				<s:textfield theme="simple" name="serviceLocationContact.lastName" id="serviceLocationContact.lastName"
					cssClass="shadowBox grayText" cssStyle="width: 160px;"
					value="%{serviceLocationContact.lastName}"  
					maxlength="50"/>
			</td>
		</tr>

	</table>
	<table width="450" cellpadding="0" cellspacing="0">
		<tr>
			<td width="365">
				<label>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.street.name"/>
				</label>
				<br />

				<s:textfield theme="simple" name="serviceLocationContact.streetName1" id="serviceLocationContact.streetName1"
					cssClass="shadowBox grayText" cssStyle="width: 348px;"
					value="%{serviceLocationContact.streetName1}"  
					maxlength="30"/>
			</td>
			<td width="85">
				<label>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.apt.num"/>
				</label>
				<br />
				<s:textfield theme="simple" name="serviceLocationContact.aptNo" id="serviceLocationContact.aptNo"
					cssClass="shadowBox grayText" cssStyle="width: 80px;" value="%{serviceLocationContact.aptNo}"
					maxlength="10"/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<s:textfield theme="simple" name="serviceLocationContact.streetName2" id="serviceLocationContact.streetName2"
					cssClass="shadowBox grayText" cssStyle="width: 348px;"
					value="%{serviceLocationContact.streetName2}"  
					maxlength="30"/>
			</td>
		</tr>
	</table>
	<table width="460" cellpadding="0" cellspacing="0">
		<tr>
			<td width="145">
				<label>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.city"/>
				</label>
				<br />
				<s:textfield theme="simple"name="serviceLocationContact.city" id="serviceLocationContact.city" cssClass="shadowBox grayText"
					cssStyle="width: 110px;" value="%{serviceLocationContact.city}" 
					maxlength="30" />
			</td>
			<td width="80">
				<label>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.state"/>
				</label>
				<br />
			<c:set var="stateLabel" value=""/>
			<c:forEach var="stateDesc" items="${stateCodes}">
				<c:if test="${stateDesc.type == serviceLocationContact.state}">
					<c:set var="stateLabel" value="${stateDesc.descr}"/>
				</c:if>
			</c:forEach>
			<b><s:label name="serviceLocationContact.stateLabel" id="serviceLocationContact.stateLabel" value="%{#attr['stateLabel']}"/></b>
			<s:hidden name="serviceLocationContact.state" id="serviceLocationContact.state" value="%{serviceLocationContact.state}"/>
			</td>
			<td width="40">
			<tags:fieldError id="Zip" >		
					<label>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.zip"/>&nbsp;<font color="red">*</font>
					</label>
					<br/>
					<b><s:label name="serviceLocationContact.zipLabel" id="serviceLocationContact.zipLabel" value="%{serviceLocationContact.zip}"/></b>
					<s:hidden name="serviceLocationContact.zip" id="serviceLocationContact.zip" value="%{serviceLocationContact.zip}"/>
			</tags:fieldError>
			</td>
			<td width="2px">
			<br>
			&nbsp;-&nbsp;&nbsp;
			</td>
			<td width="50" align="left">
				<tags:fieldError id="Zipcode" >	
					<br/>		
					&nbsp;<s:textfield theme="simple" name="serviceLocationContact.zip4" id="serviceLocationContact.zip4" cssClass="shadowBox grayText"
						cssStyle="width: 30px;" value="%{serviceLocationContact.zip4}"  
						maxlength="4"/>
				</tags:fieldError>
			</td>
		</tr>
	</table>
	<p class="formFieldOffset" style="padding-bottom: 20px;"></p>
	<table width="500" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="3">
				<tags:fieldError id="Email">
				<label>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.email"/>
				</label>
				<br />

				<s:textfield theme="simple" name="serviceLocationContact.email" id="serviceLocationContact.email"
					cssClass="shadowBox grayText" cssStyle="width: 342px;" value="%{serviceLocationContact.email}"
					maxlength="255" />
				</tags:fieldError>
			</td>
		</tr>
	
	<c:forEach items="${serviceLocationContact.phones}" varStatus="pVar" >
	
		<s:if test="%{serviceLocationContact.phones[%{#attr['pVar'].index}].phoneType==1}">
		
			<tr>
				<td width="175">
				<tags:fieldError id="phone">
					<label>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.phone.num"/>
					</label>
					<br />
						<s:textfield theme="simple" name="serviceLocationContact.phones[%{#attr['pVar'].index}}].areaCode" id="serviceLocationContact.phones[%{#attr['pVar'].index}}].areaCode" cssClass="shadowBox grayText" cssStyle="width: 30px;"
							value="%{serviceLocationContact.phones[%{#attr['pVar'].index}].areaCode}" maxlength="3" />
						-
						<s:textfield theme="simple" name="serviceLocationContact.phones[%{#attr['pVar'].index}}].phonePart1" id="serviceLocationContact.phones[%{#attr['pVar'].index}].phonePart1" cssClass="shadowBox grayText" cssStyle="width: 30px;"
							value="%{serviceLocationContact.phones[%{#attr['pVar'].index}].phonePart1}" maxlength="3" />
						-
						<s:textfield theme="simple" name="serviceLocationContact.phones[%{#attr['pVar'].index}].phonePart2" id="serviceLocationContact.phones[%{#attr['pVar'].index}].phonePart2" cssClass="shadowBox grayText" cssStyle="width: 45px;"
							value="%{serviceLocationContact.phones[%{#attr['pVar'].index}].phonePart2}" maxlength="4" />
					</tags:fieldError>
				</td>
				<td width="75">
					<label>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.ext"/>
					</label>
					<br />
					<s:textfield theme="simple" name="serviceLocationContact.phones[%{#attr['pVar'].index}].ext" id="serviceLocationContact.phones[%{#attr['pVar'].index}].ext" cssClass="shadowBox grayText" cssStyle="width: 55px;"
						value="%{serviceLocationContact.phones[%{#attr['pVar'].index}].ext}" maxlength="5" />
				</td>
				<td width="130">
				<tags:fieldError id="Phone Type">
					<label>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.phone.type"/>
					</label>
					<br />
				<s:select 	
			    	name="serviceLocationContact.phones[%{#attr['pVar'].index}].phoneClassId"
			   		headerKey="-1"
			        headerValue="Select One"
			     	cssStyle="width: 190px;" size="1"
			      	theme="simple"
					list="#session.phoneTypes"
					listKey="id"
					listValue="descr"
				/>
				</tags:fieldError>
				</td>
			</tr>
		</s:if>
		<c:if test="%{serviceLocationContact.phones[${pVar.index}].phoneType==2}">
			<tr>
				<td>
					<tags:fieldError id="Alternate_phone">
					<label>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.phone.alt"/>
					</label>
					<br />
					<s:textfield theme="simple" name="serviceLocationContact.phones[%{#attr['pVar'].index}].areaCode" id="serviceLocationContact.phones[%{#attr['pVar'].index}].areaCode" cssClass="shadowBox grayText" cssStyle="width: 30px;"
						value="%{serviceLocationContact.phones[%{#attr['pVar'].index}].areaCode}" maxlength="3" />
					-
					<s:textfield theme="simple" name="serviceLocationContact.phones[%{#attr['pVar'].index}].phonePart1" id="serviceLocationContact.phones[%{#attr['pVar'].index}].phonePart1" cssClass="shadowBox grayText" cssStyle="width: 30px;"
						value="%{serviceLocationContact.phones[%{#attr['pVar'].index}].phonePart1}" maxlength="3" />
					-
					<s:textfield theme="simple" name="serviceLocationContact.phones[%{#attr['pVar'].index}].phonePart2" id="serviceLocationContact.phones[%{#attr['pVar'].index}].phonePart2" cssClass="shadowBox grayText" cssStyle="width: 45px;"
						value="%{serviceLocationContact.phones[%{#attr['pVar'].index}].phonePart2}" maxlength="4" />
					</tags:fieldError>
				</td>
				<td>
					<label>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.ext"/>
					</label>
					<br />
					<s:textfield theme="simple" name="serviceLocationContact.phones[%{#attr['pVar'].index}].ext" id="serviceLocationContact.phones[%{#attr['pVar'].index}].ext" cssClass="shadowBox grayText" cssStyle="width: 55px;"
						value="%{serviceLocationContact.phones[%{#attr['pVar'].index}].ext}" maxlength="5" />
				</td>
				<td>
					<tags:fieldError id="Alternate Phone Type">
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.phone.type"/>
						</label>
						<br/>
		
						<s:select 	
					    	name="serviceLocationContact.phones[%{#attr['pVar'].index}].phoneClassId"
					   		headerKey="-1"
					        headerValue="Select One"
					     	cssStyle="width: 190px;" size="1"
					      	theme="simple"
							list="#session.phoneTypes"
							listKey="id"
							listValue="descr"
						/>
					</tags:fieldError>
				</td>
			</tr>

		 </c:if>
		<c:if test="%{serviceLocationContact.phones[${pVar.index}].phoneType==3}">
		<tr>
				<td width="175">
				<tags:fieldError id="Fax">
					<label>
					    <fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.fax"/>
					</label>
					<br />
					<s:textfield theme="simple" name="serviceLocationContact.phones[%{#attr['pVar'].index}].areaCode" id="serviceLocationContact.phones[%{#attr['pVar'].index}].areaCode" cssClass="shadowBox grayText" cssStyle="width: 30px;"
						value="%{serviceLocationContact.phones[%{#attr['pVar'].index}].areaCode}" maxlength="3" />
					-
					<s:textfield theme="simple" name="serviceLocationContact.phones[%{#attr['pVar'].index}].phonePart1" id="serviceLocationContact.phones[%{#attr['pVar'].index}].phonePart1" cssClass="shadowBox grayText" cssStyle="width: 30px;"
						value="%{serviceLocationContact.phones[%{#attr['pVar'].index}].phonePart1}" maxlength="3" />
					-
					<s:textfield theme="simple" name="serviceLocationContact.phones[%{#attr['pVar'].index}].phonePart2" id="serviceLocationContact.phones[%{#attr['pVar'].index}].phonePart2" cssClass="shadowBox grayText" cssStyle="width: 45px;"
						value="%{serviceLocationContact.phones[%{#attr['pVar'].index}].phonePart2}" maxlength="4" />
					<input type="hidden" id="serviceLocationContact.phones[${pVar.index}].phoneClassId" 
										  name="serviceLocationContact.phones[${pVar.index}].phoneClassId"
										  value="6"/>
								
				</tags:fieldError>
				</td>
		</tr>
		</c:if>
		</c:forEach>
	</table>
	<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.serv.location.notes.description"/>
	<p>
		<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.serv.location.notes.label"/>
	</p>
	
	<s:textarea cssStyle="width: 657px" name="serviceLocationContact.serviceLocationNote" id="serviceLocationContact.serviceLocationNote" 
			  		onkeydown="countAreaChars(this, this.form.serviceLocNote_leftChars, 255, event);"
					onkeyup="countAreaChars(this, this.form.serviceLocNote_leftChars, 255, event);"
					cssClass="shadowBox" value="%{serviceLocationContact.serviceLocationNote}"/>
		<s:textfield name="serviceLocNote_leftChars" size="4" maxlength="4" value="" readonly="true"/> <fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.chars.left"/>

</div>
</s:iterator>
