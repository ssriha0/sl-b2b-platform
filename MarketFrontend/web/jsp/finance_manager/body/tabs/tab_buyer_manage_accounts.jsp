<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<%@page import="java.util.Calendar,java.text.SimpleDateFormat"%>
<%
Calendar calendar = Calendar.getInstance(request.getLocale());
SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
%>
<s:form action="fmManageAccounts_saveAccounts" id="fmOverview" theme="simple"
	enctype="multipart/form-data" method="POST">
	<input type="hidden" name="transDate" id="transDate" value="<%=formatter.format(calendar.getTime())%>" />
	<s:hidden name="userName" id="manageAccountsTabDTO.userName" value = "%{#session.userName}" />
	<s:hidden name="creditCardAuthTokenizeXapikey" id="manageAccountsTabDTO.CreditCardAuthTokenizeXapikey" value = "%{#session.CreditCardAuthTokenizeXapikey}" />
	
<s:include value="validationMessages.jsp" />
	<div class="content">
		<h3><fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.slaccountinformation" /></h3>
		<p><fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.slaccountinformation.msgone" /></p>
		<%--  Bank Account --%>
		<s:include value="manage_accounts_panels/panel_bank_account.jsp" />
		<%--  Credit Card --%>
		<c:if test="${SecurityContext.companyId != searsBuyerId}">
			<s:include value="manage_accounts_panels/panel_credit_card_account.jsp" />
		</c:if>
	</div>
</s:form>
