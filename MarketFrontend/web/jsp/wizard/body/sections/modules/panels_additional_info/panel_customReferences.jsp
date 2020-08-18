
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
	
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="com.newco.marketplace.web.dto.SOWCustomRefDTO"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- NEW MODULE/ WIDGET-->
<div dojoType="dijit.TitlePane" title="Custom References (Optional)"
	id="" class="contentWellPane">
	<p>
		<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.addinfo.custom.ref.description"/> ${foo}
	</p>

		<table width="600" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.addinfo.custom.ref.buyer.ref.label"/><br>
				</td>
			</tr>
		</table>

	<table cellpadding="0" cellspacing="0" width="600">
		<tr>
			<td width="180" valign="top">
				<p>
					<label>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.addinfo.custom.ref.type"/>
					</label>
				</p>
			</td>
			<td width="180">
				<p>
					<label>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.addinfo.custom.ref.value"/>
					</label>
				</p>
			</td>
		</tr>
		<tr>
			<s:iterator value="buyerRef" status="counter">
				<tr>
					<td>
						<p>
							<label>
								<c:if test="${required == 1 }">
								<span class="req">*</span>
								</c:if>
								${refType}
							</label>
						</p>
					</td>
					<td>
						<p>
						  <tags:fieldError id="referenceValue">
							<s:textfield name="%{'buyerRef[' + #counter.index + '].referenceValue'}" id="referenceValue" cssStyle="width: 150px;" cssClass="shadowBox grayText" theme="simple" maxlength="5000"/>
						  </tags:fieldError>										
						</p>
					</td>
				</tr>
			</s:iterator>
		</tr>


		<tr>
			<td>
		</tr>
		<tr><td colspan = 3>Fields marked with * in select box are mandatory. You must enter a reference value for each of those.</td></tr>
	</table>

</div>
