<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
	<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="FianaceManager.sladminRevenuePull"/>
	</jsp:include>

<s:form action="fmRevenuePull_setRevenuePullRequest" theme="simple"
	 method="POST">

	<s:if test="%{#session.revenuePullError == 'true'}">
		<div style="margin: 10px 0pt;" id="errorMsgs" class="errorBox clearfix">
			<s:iterator  value="errors" status="error">
			<p class="errorMsg">
				${errors[error.index].msg}
			</p>
			</s:iterator>
		</div>
			<%session.removeAttribute("errors"); %>
			<%session.removeAttribute("revenuePullError"); %>
	</s:if>

	<s:if test="%{#session.revenuePullStatus == 'success'}" >
	<font color="blue" size="1"> ${revenuePullTabDTO.successMsg} </font>
    <%session.removeAttribute("revenuePullSuccessMsg"); %>
	</s:if>

<div class="darkGrayModuleHdr">
	Revenue Pull
</div>
<div class="grayModuleContent mainWellContent clearfix">
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td width="325">
				<p>
					<label>
						Revenue Amount:<font color="red">*</font>	
					</label>
					<br />
					<s:if test="%{revenuePullTabDTO.revenuePullAmount != null}" >
						<s:textfield  onfocus="clearTextbox(this)"  cssClass="shadowBox"
						name="revenuePullTabDTO.revenuePullAmount"
						value="%{revenuePullTabDTO.revenuePullAmount}"
						id="revenuePullTabDTO.revenuePullAmount" cssStyle="width: 160px;"
						maxlength="10"/>
					</s:if>
					<s:else>
						<s:textfield  onfocus="clearTextbox(this)"  cssClass="shadowBox"
						name="revenuePullTabDTO.revenuePullAmount"
						value="0.00"
						id="revenuePullTabDTO.revenuePullAmount" cssStyle="width: 160px;"
						maxlength="10"/>
					</s:else>
				</p>
			</td>
			<td>
			<label>
			Revenue Pull Date:<font color="red">*</font>
			</label>
			<div id="calendars" >
						<p>
							<input type="text" dojoType="dijit.form.DateTextBox" class="shadowBox" id="CalendarOnDate" name="CalendarOnDate" value="${revenuePullTabDTO.calendarOnDate}" required="true" lang="en-us" />
						</p>
			</div> 
			</td>
		</tr>
		<tr>
			<td colspan=2>
			<label>
				Reason Comment:<font color="red">*</font>  
			</label>
			</td>
		</tr>
		<tr>
			<td colspan=2>
			<textarea style="width: 150px;" name="revenuePullNote"  id="revenuePullNote" class="shadowBox grayText" >${revenuePullTabDTO.revenuePullNote}</textarea>
			</td>
		</tr>
	</table>
</div>

<div class="clearfix">
		<div class="formNavButtons">
			
			<s:submit type="input" 
				cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/revenuePull.gif);width:108px; height:20px;"
				cssClass="btn20Bevel" 
				theme="simple"
				value="" />
				
	</div>
	</div>
</s:form>

<p>
	<fmt:message bundle="${serviceliveCopyBundle}" key="sl_admin.fm.revenuepull.note.msg" />
</p>