<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
	<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="FianaceManager.sladminManageFunds"/>
	</jsp:include>	

 <s:form action="fmManageFunds_withdrawFundsSLAOperations" theme="simple"
	 method="POST">
<c:set var="depositMessage" scope="request" value="<%=session.getAttribute("depositSuccessMsg")%>" />
<jsp:include page="validationMessages.jsp" />
<font color="red" size="2">
		<c:out value="${withdrawsuccess}" />
</font>

<p>

<s:if test="%{#session.depositStatus == 'success'}" >
	<font color="blue" size="1"> ${depositMessage} </font>
    <%session.removeAttribute("depositStatus"); %>
    <%session.removeAttribute("depositSuccessMsg"); %>
</s:if>

<s:if test="%{#session.WithdrawStatus == 'success'}" >
	<font color="blue" size="1">
		<fmt:message bundle="${serviceliveCopyBundle}"
		key="fm.managefunds.success" />
    </font>   
    <%session.removeAttribute("WithdrawStatus"); %>
</s:if>

</p>

<s:if test="%{#session.AccountStatus == 'invalidAccount'}" >
	<font color="red" size="2">
		<fmt:message bundle="${serviceliveCopyBundle}"
		key="fm.managefunds.invalidaccount" />
</font>
</s:if>
<!-- 
<p class="paddingBtm">
	<fmt:message bundle="${serviceliveCopyBundle}" key="fm.managefunds.msg" />
</p>  -->
<div class="darkGrayModuleHdr">
	<fmt:message bundle="${serviceliveCopyBundle}" key="fm.managefunds.withdraw" />
</div>
<div class="grayModuleContent mainWellContent clearfix">
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td width="350">
				<p>
					<label>
						<fmt:message bundle="${serviceliveCopyBundle}" key="fm.managefunds.withdrawtoaccount" />
					</label>
					<br />
				<tags:fieldError id="Withdraw to this Account." oldClass="paddingBtm">
				<s:select name="manageFundsTabDTO.accountId" headerKey="-1"
					headerValue="Select One" list="%{#session.accountList}"
					listKey="accountId" listValue="accountNameNum" cssStyle="width: 240px;"
					size="1" theme="simple" value="manageFundsTabDTO.accountId" />
				</tags:fieldError>
					<%-- <a href="#">Add New Bank Account</a> --%>
				</p>
			</td>
			<td width="350">
				<p>
					<label>
						Withdrawal Amount			
					</label>
					<br />
					<tags:fieldError id="Withdraw Amount" oldClass="paddingBtm">
					<s:if test="%{manageFundsTabDTO.withdrawAmount != null}" >
						<s:textfield  onfocus="clearTextbox(this)"  cssClass="shadowBox"
						name="manageFundsTabDTO.withdrawAmount"
						value="%{manageFundsTabDTO.withdrawAmount}"
						id="manageFundsTabDTO.withdrawAmount" cssStyle="width: 160px;"
						maxlength="10"/>
					</s:if>
					<s:else>
						<s:textfield  onfocus="clearTextbox(this)"  cssClass="shadowBox"
						name="manageFundsTabDTO.withdrawAmount"
						value="0.00"
						id="manageFundsTabDTO.withdrawAmount" cssStyle="width: 160px;"
						maxlength="10"/>
					</s:else>
					</tags:fieldError>
				</p>
			</td>
		</tr>
	</table>
</div>

<div class="clearfix">
		<div class="formNavButtons">
			<s:if test="%{#session.AccountStatus == 'invalidAccount'}" >
				<s:submit type="input" 
					  cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/withdrawFunds.gif);width:108px; height:20px;"
					  cssClass="btn20Bevel" 
					  theme="simple"
					  value=""
					  disabled="true" />	
			</s:if>
			<s:else>
				<s:submit type="input" 
					  cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/withdrawFunds.gif);width:108px; height:20px;"
					  cssClass="btn20Bevel" 
					  theme="simple"
					  value="" />
			</s:else>
				
	</div>
	</div>
</s:form>



<s:form action="fmManageFunds_depositFundsSLAOperations" theme="simple"
	 method="POST">
<font color="red" size="2">
		<c:out value="${depositSuccess}" />
</font>

<p>

<s:if test="%{#session.WithdrawStatus == 'success'}" >
	<font color="blue" size="1">
		<fmt:message bundle="${serviceliveCopyBundle}"
		key="fm.managefunds.success" />
    </font>   
    <%session.removeAttribute("depositStatus"); %>
</s:if>

</p>

<s:if test="%{#session.AccountStatus == 'invalidAccount'}" >
	<font color="red" size="2">
		<fmt:message bundle="${serviceliveCopyBundle}"
		key="fm.managefunds.invalidaccount" />
</font>
</s:if>
		<div class="darkGrayModuleHdr">
			<!-- <fmt:message bundle="${serviceliveCopyBundle}" key="fm.managefunds.withdraw" /> 	-->
			Deposit Funds
		</div>
		<div class="grayModuleContent mainWellContent clearfix">
		
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td width="350">
						<p>
							<label>
								<%-- <fmt:message bundle="${serviceliveCopyBundle}" key="fm.managefunds.withdrawtoaccount" /> --%>
								Deposit to this Account
							</label>
							<br />
						<tags:fieldError id="Deposit to this Account." oldClass="paddingBtm">
						<s:select name="manageFundsTabDTO.accountId" headerKey="-1"
							headerValue="Select One" list="%{#session.accountList}"
							listKey="accountId" listValue="accountNameNum" cssStyle="width: 240px;"
							size="1" theme="simple" value="manageFundsTabDTO.accountId" />
						</tags:fieldError>
							<%-- <a href="#">Add New Bank Account</a> --%>
						</p>
					</td>
					<td width="350">
						<p>
							<label>
								Deposit Amount
							</label>
							<br />
							<tags:fieldError id="Deposit Amount" oldClass="paddingBtm">
							<s:if test="%{manageFundsTabDTO.depositAmount != null}" >
								<s:textfield  onfocus="clearTextbox(this)"  cssClass="shadowBox"
								name="manageFundsTabDTO.depositAmount"
								value="%{manageFundsTabDTO.depositAmount}"
								id="manageFundsTabDTO.depositAmount" cssStyle="width: 160px;"
								maxlength="10"/>
							</s:if>
							<s:else>
								<s:textfield  onfocus="clearTextbox(this)"  cssClass="shadowBox"
								name="manageFundsTabDTO.depositAmount"
								value="0.00"
								id="manageFundsTabDTO.depositAmount" cssStyle="width: 160px;"
								maxlength="10"/>
							</s:else>
							</tags:fieldError>
						</p>
					</td>
				</tr>
			</table>
		</div>

	<div class="clearfix">
			<div class="formNavButtons">
				<s:if test="%{#session.AccountStatus == 'invalidAccount'}" >
					<s:submit type="input" 
						  cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/depositFunds.gif);width:95px; height:20px;"
						  cssClass="btn20Bevel" 
						  theme="simple"
						  value=""
						  disabled="true"
						  />	
				</s:if>
				<s:else>
					<s:submit type="input" 
						  cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/depositFunds.gif);width:95px; height:20px;"
						  cssClass="btn20Bevel" 
						  theme="simple"
						  value=""
						  />
				</s:else>
		</div>
	</div>
</s:form>

<p>
	<fmt:message bundle="${serviceliveCopyBundle}" key="fm.managefunds.note" />: 
	<fmt:message bundle="${serviceliveCopyBundle}" key="sl_admin.fm.managefunds.note.msg" />
</p>
	
