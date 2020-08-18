<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
	<script language="javascript">
function whichButton(event)
{
  alert("Not Allow Right Click!");
if (event.button==2)//RIGHT CLICK
  {
  alert("Not Allow Right Click!");
  }

}
function noCTRL(e)
{
var code = (document.all) ? event.keyCode:e.which;
  alert("Not Allow Right Click!");
var msg = "Sorry, this functionality is disabled.";
if (parseInt(code)==17) //CTRL
{
alert("Not Allow Right Click!");
window.event.returnValue = false;
}
} 
</script>

	<div class="darkGrayModuleHdr">
		<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.bankaccountelectronicfunds" />
	</div>
	
		
	<div class="grayModuleContent mainWellContent clearfix">
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
						<c:choose>
						<c:when test="%{#session.buttonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="accountDescription"
									id="accountDescription" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{accountDescription}" maxlength="50" readonly="true" />
						</c:when>
						<c:otherwise>
							<s:textfield theme="simple" name="accountDescription"
									id="accountDescription" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{accountDescription}" maxlength="50" />
						</c:otherwise>
						</c:choose>
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
						<c:choose>
						<c:when test="%{#session.buttonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="accountHolder"
									id="accountHolder" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{accountHolder}" maxlength="75" readonly="true" />
						</c:when>
						<c:otherwise>
							<s:textfield theme="simple" name="accountHolder"
									id="accountHolder" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{accountHolder}" maxlength="75" />
						</c:otherwise>
						</c:choose>
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
						<c:choose>
						<c:when test="%{#session.buttonMode == 'Edit Mode'}" >
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
						</c:when>
						<c:otherwise>
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
						</c:otherwise>
						</c:choose>
					</p>
				</td>
				<td>
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.financialinstitution" />
						</label>
						<br />
						<c:choose>
						<c:when test="%{#session.buttonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="financialInstitution"
									id="financialInstitution" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{financialInstitution}" maxlength="50" readonly="true" />
						</c:when>
						<c:otherwise>
							<s:textfield theme="simple" name="financialInstitution"
									id="financialInstitution" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{financialInstitution}" maxlength="50" />
						</c:otherwise>
						</c:choose>
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
						<c:choose>
						<c:when test="%{#session.buttonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="routingNumber"
									id="routingNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{routingNumber}" maxlength="9" readonly="true" onmouseup="whichButton(event)"  onkeydown="return noCTRL(event)"/>
						</c:when>
						<c:otherwise>
						<tags:fieldError id="Routing Number" oldClass="paddingBtm">
							<s:textfield theme="simple" name="routingNumber"
									id="routingNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{routingNumber}" maxlength="9" onselect = "noCTRL(this);"/>
						</tags:fieldError>
						</c:otherwise>
						</c:choose>
					</p>
				</td>

				<td>
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.reenterroutingnumber" />
						</label>
						<br />
						<c:choose>
						<c:when test="%{#session.buttonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="confirmRoutingNumber"
									id="confirmAccountNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{confirmRoutingNumber}" maxlength="17" readonly="true"/>
						</c:when>
						<c:otherwise>
						<tags:fieldError id="Re-enter Account Number" oldClass="paddingBtm">
							<s:textfield theme="simple" name="confirmRoutingNumber"
									id="confirmAccountNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{confirmRoutingNumber}" maxlength="17" />
						</tags:fieldError>
						</c:otherwise>
						</c:choose>
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
						<c:choose>
						<c:when test="%{#session.buttonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="accountNumber"
									id="accountNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{accountNumber}" maxlength="17" readonly="true"/>
						</c:when>
						<c:otherwise>
						<tags:fieldError id="Account Number" oldClass="paddingBtm">
							<s:textfield theme="simple" name="accountNumber"
									id="accountNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{accountNumber}" maxlength="17" />
						</tags:fieldError>
						</c:otherwise>
						</c:choose>	
					</p>
				</td>
				<td>
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.manageaccounts.reenteraccountnumber" />
						</label>
						<br />
						<c:choose>
						<c:when test="%{#session.buttonMode == 'Edit Mode'}" >
							<s:textfield theme="simple" name="confirmAccountNumber"
									id="confirmAccountNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{confirmAccountNumber}" maxlength="17" readonly="true"/>
						</c:when>
						<c:otherwise>
						<tags:fieldError id="Re-enter Account Number" oldClass="paddingBtm">
							<s:textfield theme="simple" name="confirmAccountNumber"
									id="confirmAccountNumber" cssClass="shadowBox grayText"
									cssStyle="width: 200px;" value="%{confirmAccountNumber}" maxlength="17" />
						</tags:fieldError>
						</c:otherwise>
						</c:choose>
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
				<c:choose>
					<c:when test="%{#session.buttonMode == 'Edit Mode'}" >
						<s:submit type="input" 
					 	 		  cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/editEntry.gif);width:74px; height:20px;"
					 			  cssClass="btn20Bevel" 
					       		  method="editAccounts"
					  			  theme="simple"
					  			  value=""/>	
					</c:when>
					<c:otherwise>
						<s:submit type="input" 
					  			  cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/saveInformation.gif);width:109px; height:20px;"
					  			  cssClass="btn20Bevel" 
					  			  method="saveAccounts"
					  			  theme="simple"
					  			  value=" "/>	
					</c:otherwise>
				</c:choose>
				</td>
				<td>
				</td>
				<td>
					<c:if test="%{#session.buttonMode == 'Cancel Mode'}" >
						<s:submit type="input" 
					  			  cssStyle="background-image: url(%{#request['staticContextPath']/images/btn/cancel.gif);width:54px; height:20px;"
					  			  cssClass="btn20Bevel" 
					  			  method="cancelAccounts"
					  			  theme="simple"
					  			  value=""/>	
					</c:if>
				</td>
			</tr>
		</table>
			
		</div>
	</div>
	   <c:if test="${isSLAdmin == true}">
    	<c:if test="%{routingNumber != null}">
		<table bgcolor="#dedede" width="98%">
			<tr>
				<td>
				<u><fmt:message bundle="${serviceliveCopyBundle}" key="fm.sladmin.autoACHApproval.admin.use" /></u>
				</td>
			</tr>
			<tr>
				<td>
				<c:choose>
					<c:when test="%{autoACHInd == 'true'}" >
						<s:checkbox id="autoACHInd" value="%{autoACHInd}"
						name="autoACHInd"
						theme="simple" cssClass="checked"/>	
					</c:when>
					<c:otherwise>
						<s:checkbox id="autoACHInd" value="%{autoACHInd}"
						name="autoACHInd"
						theme="simple" />	
					</c:otherwise>
				</c:choose>
					<fmt:message bundle="${serviceliveCopyBundle}" key="fm.sladmin.autoACHApproval.approval.msg" />
					 <strong><fmt:message bundle="${serviceliveCopyBundle}" key="fm.sladmin.autoACHApproval.instanttransfer.msg" /></strong> 
					 <fmt:message bundle="${serviceliveCopyBundle}" key="fm.sladmin.autoACHApproval.ACHfunding.msg" />		

					<s:submit type="input" 
			  			  cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/saveInformation.gif);width:109px; height:20px;"
			  			  cssClass="btn20Bevel" 
			  			  method="saveAutoACHAccounts"
			  			  theme="simple"
			  			  value=""/>
				</td>
			</tr>
			</table>	
	   	    <br>				   	
			</c:if>		
		</c:if>
