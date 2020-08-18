<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!-- NEW MODULE/ WIDGET-->
<div dojoType="dijit.TitlePane"
	title="Service Location & Contact Information" id=""
	class="contentWellPane">
	<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.serv.location.description"/>
	
	<c:if test="${groupOrderId != null}">
		<div  style="color : red" >Please note that any changes you make to this order will affect all orders in the group.</div>
	</c:if>
	
	
	<div id="orderGroupChildEditSection" >
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
					maxlength="100"/>
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
					maxlength="100"/>
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
	
	</div> <%-- End of Child Order that may be hidden--%>
	
	
	
	
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
	
	<c:forEach items="${serviceLocationContact.phones}" var="phones" varStatus="pVar" >
	
		<c:if test="${phones.phoneType==1}">
		
			<tr>
				<td width="175">
					<tags:fieldError id="phone">
					<label>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.phone.num"/>
					</label>
					<br />
						<input type="text" name="serviceLocationContact.phones[${pVar.index}].areaCode" id="serviceLocationContact.phones[${pVar.index}].areaCode" class="shadowBox grayText" style="width: 30px;"
							value="${phones.areaCode}" maxlength="3" />
						-
						<input type="text" name="serviceLocationContact.phones[${pVar.index}].phonePart1" id="serviceLocationContact.phones[${pVar.index}].phonePart1" class="shadowBox grayText" style="width: 30px;"
							value="${phones.phonePart1}" maxlength="3" />
						-
						<input type="text" name="serviceLocationContact.phones[${pVar.index}].phonePart2" id="serviceLocationContact.phones[${pVar.index}].phonePart2" class="shadowBox grayText" style="width: 45px;"
							value="${phones.phonePart2}" maxlength="4" />
					</tags:fieldError>
				</td>
				<td width="75">
					<label>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.ext"/>
					</label>
					<br />
					<input type="text" name="serviceLocationContact.phones[${pVar.index}].ext" id="serviceLocationContact.phones[${pVar.index}].ext" class="shadowBox grayText" style="width: 55px;"
						value="${phones.ext}" maxlength="5" />
				</td>
				<td width="130">
					<tags:fieldError id="Phone Type">
					<label>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.phone.type"/>
					</label>
					<br />
				
				<select name="serviceLocationContact.phones[${pVar.index}].phoneClassId" id="phoneClass1" style="width: 190px;">
					<option value="-1">Select One</option>
					<c:forEach items="${phoneTypes}" var="type">
					<c:if test="${phones.phoneClassId==type.id}">
						<option value="${type.id}" selected="selected">${type.descr}</option>
					</c:if>
						<option value="${type.id}">${type.descr}</option>
					</c:forEach>
				</select>
				</tags:fieldError>
					
				</td>
			</tr>
		</c:if>
		<c:if test="${phones.phoneType==2}">
			<tr>
				<td>
					<tags:fieldError id="Alternate_phone">
					<label>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.phone.alt"/>
					</label>
					<br />
					<input type="text" name="serviceLocationContact.phones[${pVar.index}].areaCode" id="serviceLocationContact.phones[${pVar.index}].areaCode" class="shadowBox grayText" style="width: 30px;"
						value="${phones.areaCode}" maxlength="3" />
					-
					<input type="text" name="serviceLocationContact.phones[${pVar.index}].phonePart1" id="serviceLocationContact.phones[${pVar.index}].phonePart1" class="shadowBox grayText" style="width: 30px;"
						value="${phones.phonePart1}" maxlength="3" />
					-
					<input type="text" name="serviceLocationContact.phones[${pVar.index}].phonePart2" id="serviceLocationContact.phones[${pVar.index}].phonePart2" class="shadowBox grayText" style="width: 45px;"
						value="${phones.phonePart2}" maxlength="4" />
					</tags:fieldError>
				</td>
				<td>
					<label>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.ext"/>
					</label>
					<br />
					<input type="text" name="serviceLocationContact.phones[${pVar.index}].ext" id="serviceLocationContact.phones[${pVar.index}].ext" class="shadowBox grayText" style="width: 55px;"
						value="${phones.ext}" maxlength="5" />
				</td>
				<td>
					<tags:fieldError id="Alternate Phone Type">
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.phone.type"/>
						</label>
						<br/>
		
						<select name="serviceLocationContact.phones[${pVar.index}].phoneClassId" id="phoneClass2" style="width: 190px;">
							<option value="-1">Select One</option>
							<c:forEach items="${phoneTypes}" var="type">
							<c:if test="${phones.phoneClassId==type.id}">
						       <option value="${type.id}" selected="selected">${type.descr}</option>
					        </c:if>
								<option value="${type.id}">${type.descr}</option>
							</c:forEach>
						</select>
					</tags:fieldError>
				</td>
			</tr>

		 </c:if>
		 <c:if test="${phones.phoneType==3}">
			<tr>
					<td width="175">
						<tags:fieldError id="Fax">
							<label>
							    <fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.fax"/>
							</label>
							<br />
							<input type="text" name="serviceLocationContact.phones[${pVar.index}].areaCode" id="serviceLocationContact.phones[${pVar.index}].areaCode" class="shadowBox grayText" style="width: 30px;"
								value="${phones.areaCode}" maxlength="3" />
							-
							<input type="text" name="serviceLocationContact.phones[${pVar.index}].phonePart1" id="serviceLocationContact.phones[${pVar.index}].phonePart1" class="shadowBox grayText" style="width: 30px;"
								value="${phones.phonePart1}" maxlength="3" />
							-
							<input type="text" name="serviceLocationContact.phones[${pVar.index}].phonePart2" id="serviceLocationContact.phones[${pVar.index}].phonePart2" class="shadowBox grayText" style="width: 45px;"
								value="${phones.phonePart2}" maxlength="4" />
							<input type="hidden" id="serviceLocationContact.phones[${pVar.index}].phoneClassId" 
												  name="serviceLocationContact.phones[${pVar.index}].phoneClassId"
												  value="6" />
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
					cssClass="excludeTinyMce shadowBox" value="%{serviceLocationContact.serviceLocationNote}"/>
		<s:textfield name="serviceLocNote_leftChars" size="4" maxlength="4" value="255" readonly="true"/> <fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.chars.left"/>

</div>
