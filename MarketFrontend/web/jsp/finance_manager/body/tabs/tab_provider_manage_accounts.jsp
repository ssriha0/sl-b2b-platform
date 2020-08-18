<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
	<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<div>
	<div style="HEIGHT:950px; OVERFLOW:auto">
<s:form action="fmManageAccounts_saveAccounts" id="fmOverview" theme="simple"
	enctype="multipart/form-data" method="POST">
	
<s:if test="%{!#session.isEscheatValidated}" >
<jsp:include page="validationMessages.jsp" />

</s:if>	
<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="FianaceManager.providerManageAccounts"/>
	</jsp:include>	

<div class="content">
	<h3>
		<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.slaccountinformation" />
	</h3>
	<p>
		<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.slaccountinformation.msgone" />
	</p>
	<p class="paddingBtm">
		<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.slaccountinformation.msgtwo" />
	</p>
	<div class="darkGrayModuleHdr">
		<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.bankaccountelectronicfunds" />
	</div>
	<div class="grayModuleContent mainWellContent clearfix"  >
		<p>
			<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.bankaccountelectronicfunds.msg" />
		</p>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="2" width="280">
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.description" />
						</label>
						<br />
						<s:if test="%{#session.buttonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="accountDescription"
									id="accountDescription" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{accountDescription}" maxlength="50" readonly="true" />
						</s:if>
						<s:else >
							<s:textfield theme="simple" name="accountDescription"
									id="accountDescription" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{accountDescription}" maxlength="50" />
						</s:else>
					</p>
				</td>
				<td></td>
			</tr>
						<tr>
				<td colspan="2" width="280">
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.accountHolder" />
						</label>
						<br />
						<s:if test="%{#session.buttonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="accountHolder"
									id="accountHolder" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{accountHolder}" maxlength="75" readonly="true" />
						</s:if>
						<s:else >
							<s:textfield theme="simple" name="accountHolder"
									id="accountHolder" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{accountHolder}" maxlength="75" />
						</s:else>
					</p>
				</td>
				<td></td>
			</tr>
			<tr>
				<td colspan="2">
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.accounttype" />
						</label>
						<br />
						<s:if test="%{#session.buttonMode == 'Edit Mode'}" >
						<s:select 	
		    				name="accountType"
		   					headerKey="-1"
		       				headerValue="Select One"
		     				cssStyle="width: 140px;" size="1"
		      				theme="simple"
							list="#session.accountTypeMap"
							listKey="id"
							listValue="descr"
							disabled="true"
						/>
						</s:if>
						<s:else>
							<s:select 	
		    				name="accountType"
		   					headerKey="-1"
		       				headerValue="Select One"
		     				cssStyle="width: 140px;" size="1"
		      				theme="simple"
							list="#session.accountTypeMap"
							listKey="id"
							listValue="descr"
						/>
						</s:else>
						
					</p>
				</td>
				<td>
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.financialinstitution" />
						</label>
						<br />
						<s:if test="%{#session.buttonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="financialInstitution"
									id="financialInstitution" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{financialInstitution}" maxlength="50" readonly="true" />
						</s:if>
						<s:else>
							<s:textfield theme="simple" name="financialInstitution"
									id="financialInstitution" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{financialInstitution}" maxlength="50" />
						</s:else>
						
					</p>
				</td>
			</tr>
			<tr><td colspan = 3><fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.routingnumberNote" /></td></tr>
			<tr>
				<td colspan="2">
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.routingnumber" />
						</label>
						<br />
						<s:if test="%{#session.buttonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="routingNumber"
									id="routingNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{routingNumber}" maxlength="9" readonly="true" />
						</s:if>
						<s:else>
						<tags:fieldError id="Routing Number" oldClass="paddingBtm">
							<s:textfield theme="simple" name="routingNumber"
									id="routingNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{routingNumber}" maxlength="9" />
						</tags:fieldError>
						</s:else>
						
					</p>
				</td>

				<td>
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.reenterroutingnumber" />
						</label>
						<br />
						<s:if test="%{#session.buttonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="confirmRoutingNumber"
									id="confirmAccountNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{confirmRoutingNumber}" maxlength="17" readonly="true"/>
						</s:if>
						<s:else>
						<tags:fieldError id="Re-enter Account Number" oldClass="paddingBtm">
							<s:textfield theme="simple" name="confirmRoutingNumber"
									id="confirmAccountNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{confirmRoutingNumber}" maxlength="17" />
						</tags:fieldError>
						</s:else>
						
					</p>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.accountnumber" />
						</label>
						<br />
						<s:if test="%{#session.buttonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="accountNumber"
									id="accountNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{accountNumber}" maxlength="17" readonly="true"/>
						</s:if>
						<s:else>
						<tags:fieldError id="Account Number" oldClass="paddingBtm">
							<s:textfield theme="simple" name="accountNumber"
									id="accountNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{accountNumber}" maxlength="17" />
						</tags:fieldError>
						</s:else>
						
					</p>
				</td>
				<td>
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.reenteraccountnumber" />
						</label>
						<br />
						<s:if test="%{#session.buttonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="confirmAccountNumber"
									id="confirmAccountNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{confirmAccountNumber}" maxlength="17" readonly="true"/>
						</s:if>
						<s:else>
						<tags:fieldError id="Re-enter Account Number" oldClass="paddingBtm">
							<s:textfield theme="simple" name="confirmAccountNumber"
									id="confirmAccountNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{confirmAccountNumber}" maxlength="17" />
						</tags:fieldError>
						</s:else>
						
					</p>
				</td>
			</tr>
			</table>
	</div>
	<div class="clearfix">
		<div class="formNavButtons">
		<table>
			<tr>
				<td>
					<s:if test="%{#session.buttonMode == 'Edit Mode'}" >
						<s:submit type="input" 
					 	 		  cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/editEntry.gif);width:74px; height:20px;"
					 			  cssClass="btn20Bevel" 
					       		  method="editAccounts"
					  			  theme="simple"
					  			  value=""/>	
					</s:if>
					<s:else>
						<s:submit type="input" 
					  			  cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/saveInformation.gif);width:109px; height:20px;"
					  			  cssClass="btn20Bevel" 
					  			  method="saveAccounts"
					  			  theme="simple"
					  			  value=""/>	
					</s:else>
				</td>
				<td>
				</td>
				<td>
					<s:if test="%{#session.buttonMode == 'Cancel Mode'}" >
						<s:submit type="input" 
					  			  cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/cancel.gif);width:54px; height:20px;"
					  			  cssClass="btn20Bevel" 
					  			  method="cancelAccounts"
					  			  theme="simple"
					  			  value=""/>	
					</s:if>
				<td>
			</tr>
		</table>
		
			
		</div>
	</div>
</div>

</s:form>

<c:if test="${SecurityContext.roleId == '2'}">

<s:form action="fmManageAccounts_saveEscheatAccounts" id="fmEscheatOverview" theme="simple"
	enctype="multipart/form-data" method="POST">
	
<s:if test="%{#session.isEscheatValidated}" >

	<jsp:include page="validationMessages.jsp" />
</s:if>	


	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="FianaceManager.providerManageAccounts"/>
	</jsp:include>	

<div class="content">
	
	<div class="darkGrayModuleHdr">
		<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.bankaccountescheatmentfunds" />
	</div>
	<div class="grayModuleContent mainWellContent clearfix"  > 
		<p>
			<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.bankaccountescheatmentfunds.msg" />
		</p>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="2" width="280">
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.description" />
						</label>
						<br />
						<s:if test="%{#session.escheatmentButtonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="escheatAccountDescription"
									id="accountDescription" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{escheatAccountDescription}" maxlength="50" readonly="true" />
						</s:if>
						<s:else >
							<s:textfield theme="simple" name="escheatAccountDescription"
									id="accountDescription" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{escheatAccountDescription}" maxlength="50" />
						</s:else>
					</p>
				</td>
				<td></td>
			</tr>
						<tr>
				<td colspan="2" width="280">
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.accountHolder" />
						</label>
						<br />
						<s:if test="%{#session.escheatmentButtonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="escheatAccountHolder"
									id="accountHolder" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{escheatAccountHolder}" maxlength="75" readonly="true" />
						</s:if>
						<s:else >
							<s:textfield theme="simple" name="escheatAccountHolder"
									id="accountHolder" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{escheatAccountHolder}" maxlength="75" />
						</s:else>
					</p>
				</td>
				<td></td>
			</tr>
			<tr>
				<td colspan="2">
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.accounttype" />
						</label>
						<br />
						<s:if test="%{#session.escheatmentButtonMode == 'Edit Mode'}" >
						<s:select 	
		    				name="escheatAccountType"
		   					headerKey="-1"
		       				headerValue="Select One"
		     				cssStyle="width: 140px;" size="1"
		      				theme="simple"
							list="#session.accountTypeMap"
							listKey="id"
							listValue="descr"
							disabled="true"
							value="60"
							
							
						/>
						</s:if>
						<s:else>
							<s:select 	
		    				name="escheatAccountType"
		   					headerKey="-1"
		       				headerValue="Select One"
		     				cssStyle="width: 140px;" size="1"
		      				theme="simple"
							list="#session.accountTypeMap"
							listKey="id"
							listValue="descr"
							value="60"
							disabled="true"
						/>
						</s:else>
						
					</p>
				</td>
				<td>
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.financialinstitution" />
						</label>
						<br />
						<s:if test="%{#session.escheatmentButtonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="escheatFinancialInstitution"
									id="financialInstitution" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{escheatFinancialInstitution}" maxlength="50" readonly="true" />
						</s:if>
						<s:else>
							<s:textfield theme="simple" name="escheatFinancialInstitution"
									id="financialInstitution" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{escheatFinancialInstitution}" maxlength="50" />
						</s:else>
						
					</p>
				</td>
			</tr>
			<tr><td colspan = 3><fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.routingnumberNote" /></td></tr>
			<tr>
				<td colspan="2">
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.routingnumber" />
						</label>
						<br />
						<s:if test="%{#session.escheatmentButtonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="escheatRoutingNumber"
									id="routingNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{escheatRoutingNumber}" maxlength="9" readonly="true" />
						</s:if>
						<s:else>
						<tags:fieldError id="Routing Number" oldClass="paddingBtm">
							<s:textfield theme="simple" name="escheatRoutingNumber"
									id="routingNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{escheatRoutingNumber}" maxlength="9" />
						</tags:fieldError>
						</s:else>
						
					</p>
				</td>

				<td>
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.reenterroutingnumber" />
						</label>
						<br />
						<s:if test="%{#session.escheatmentButtonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="escheatConfirmRoutingNumber"
									id="confirmAccountNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{escheatConfirmRoutingNumber}" maxlength="17" readonly="true"/>
						</s:if>
						<s:else>
						<tags:fieldError id="Re-enter Account Number" oldClass="paddingBtm">
							<s:textfield theme="simple" name="escheatConfirmRoutingNumber"
									id="confirmAccountNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{escheatConfirmRoutingNumber}" maxlength="17" />
						</tags:fieldError>
						</s:else>
						
					</p>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.accountnumber" />
						</label>
						<br />
						<s:if test="%{#session.escheatmentButtonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="escheatAccountNumber"
									id="accountNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{escheatAccountNumber}" maxlength="17" readonly="true"/>
						</s:if>
						<s:else>
						<tags:fieldError id="Account Number" oldClass="paddingBtm">
							<s:textfield theme="simple" name="escheatAccountNumber"
									id="accountNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{escheatAccountNumber}" maxlength="17" />
						</tags:fieldError>
						</s:else>
						
					</p>
				</td>
				<td>
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.reenteraccountnumber" />
						</label>
						<br />
						<s:if test="%{#session.escheatmentButtonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="escheatConfirmAccountNumber"
									id="confirmAccountNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{escheatConfirmAccountNumber}" maxlength="17" readonly="true"/>
						</s:if>
						<s:else>
						<tags:fieldError id="Re-enter Account Number" oldClass="paddingBtm">
							<s:textfield theme="simple" name="escheatConfirmAccountNumber"
									id="confirmAccountNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{escheatConfirmAccountNumber}" maxlength="17" />
						</tags:fieldError>
						</s:else>
						
					</p>
				</td>
			</tr>
			</table>
	</div>
	<div class="clearfix">
		<div class="formNavButtons">
		<table>
			<tr>
				<td>
					<s:if test="%{#session.escheatmentButtonMode == 'Edit Mode'}" >
						<s:submit type="input" 
					 	 		  cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/editEntry.gif);width:74px; height:20px;"
					 			  cssClass="btn20Bevel" 
					       		  method="editEscheatAccounts"
					  			  theme="simple"
					  			  value=""/>	
					</s:if>
					<s:else>
						<s:submit type="input" 
					  			  cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/saveInformation.gif);width:109px; height:20px;"
					  			  cssClass="btn20Bevel" 
					  			  method="saveEscheatAccounts"
					  			  theme="simple"
					  			  value=""/>	
					</s:else>
				</td>
				<td>
				</td>
				<td>
					<s:if test="%{#session.escheatmentButtonMode == 'Cancel Mode'}" >
						<s:submit type="input" 
					  			  cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/cancel.gif);width:54px; height:20px;"
					  			  cssClass="btn20Bevel" 
					  			  method="cancelEscheatmentAccounts"
					  			  theme="simple"
					  			  value=""/>	
					</s:if>
				<td>
			</tr>
		</table>
		
			
		</div>
	</div>
</div>

</s:form>
 </c:if>
</div>
</div>