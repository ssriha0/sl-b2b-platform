<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="theId" scope="request"
	value="<%=request.getAttribute("tab")%>" />
<c:set var="role" value="${roleType}" />
<form id="fmRefundsAction" name="fmRefundsAction" action="${contextPath}/fmRefundsAction_save.action">
	<div dojoType="dijit.TitlePane" title="Issue Refund"
		id="widget_issue_refund" style="padding-top: 1px; width: 249px;"
		open="true">
		<div id="msg" style="color:red;display: block;"></div>
		<div id="successmsg" style="color:green;display: block;">
		<s:if test="%{#session.Status == 'success'}" >
			<font color="green" size="1">
				<fmt:message bundle="${serviceliveCopyBundle}"
						key="sl_admin.fm.issuerefunds.success.msg" />
    		</font>  
    		<c:remove var="Status" scope="session"/>
    	</s:if>
    	</div>
		<br/>
		<b> Account: </b>
		<br>
		<select name="fmManageAccountsList" id="fmManageAccountsList">
			<option selected="selected">
				Select Code
			</option>
			<c:forEach var="manageAccountsTabDTO" items="${fmManageAccountsList}">
				<option value="${manageAccountsTabDTO.accountId}" name="accountId">
					${manageAccountsTabDTO.financialInstitution} ${manageAccountsTabDTO.accountNumber} - ${manageAccountsTabDTO.activeInd}
				</option>
			</c:forEach>
		</select>

		<br /><br/>
		<b> Amount: </b> $
		<input type="textfield" id="amount" name="amount" />
		<br />
		Note:<br/>
		<textarea style="width: 150px;" name="refundNote"  id="refundNote" class="shadowBox grayText" 
		onKeyDown="limitText(this.form.refundNote);" 
		onKeyUp="limitText(this.form.refundNote);"></textarea>
		<br/>
		<div id="formNavButtonsDiv">
			<a href="#widget_issue_refund" id="btnSubmit" >				
				<img src="${staticContextPath}/images/spacer.gif" width="72"
					height="22"
					style="background-image: url(${staticContextPath}/images/btn/submit.gif);"
					class="btnBevel" /> </a>
			<br/>
		</div>
		<div id="disabledDepositFundsDiv" style="display: none">
		Depositing funds ....
	</div>
	<div id="resDiv"></div>
</div>
</form>





