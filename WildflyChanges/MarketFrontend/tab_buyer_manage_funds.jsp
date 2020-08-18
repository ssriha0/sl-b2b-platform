<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<c:set var="depositMessage" scope="request"
	value="<%=session.getAttribute("depositSuccessMsg")%>" />



<s:form action="fmManageFunds_depositFunds" theme="simple" method="POST"
	onsubmit="javascript:disableSubmitButton()">
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		<jsp:param name="PageName" value="FianaceManager.buyerManageFunds" />
	</jsp:include>

	<%--

	7/7/2008
	Hello.  I'm commenting this validationMessages.jsp out for now because it's not working as intended.  
	Why is this tab showing warning messages intended for the Financial Profile tab?  
	I sent an email to QA about the issue.
	
	- Kristin
	
	<jsp:include page="validationMessages.jsp" />

--%>
	<div id="errorMsgDiv">
	<jsp:include page="/jsp/buyer_admin/validationMessages.jsp" />
	<c:if test="${message != null}">
		<p>
			${message}
		</p>
		<br>
	</c:if>
	</div>
	<p>


		<c:set var="cvvStyle" value="display:none" />
		<c:set var="taxPayerStyle" value="clear: both;display:none" />

		<s:if test="%{#session.isTaxPayer == 'true'}">
			<c:set var="taxPayerStyle" value="clear: both;display:block" />

			<%
				session.removeAttribute("isTaxPayer");
				session.removeAttribute("ssnSaveInd");
			%>
		</s:if>

		<s:if test="%{#session.isCVVCode == 'true'}">
			<c:set var="cvvStyle" value="display:block" />
			<%
				session.removeAttribute("isCVVCode");
			%>
		</s:if>

		<s:if test="%{#session.depositStatus == 'success'}">
		<div id="successMsgDiv">
			<font color="blue" size="1"> ${depositMessage} </font>
		</div>
			<%
				session.removeAttribute("depositStatus");
			%>
			<%
				session.removeAttribute("depositSuccessMsg");
			%>
			<c:set var="taxPayerStyle" value="clear: both;display:none" />
			<%
				session.removeAttribute("isTaxPayer");
			%>
		</s:if>

		<s:if test="%{#session.WithdrawStatus == 'success'}">
			<font color="blue" size="1"> <fmt:message
					bundle="${serviceliveCopyBundle}" key="fm.managefunds.success" />
			</font>
			<%
				session.removeAttribute("depositStatus");
			%>
		</s:if>



	</p>



	<s:if test="%{#session.AccountStatus == 'invalidAccount'}">
		<font color="red" size="2"> <fmt:message
				bundle="${serviceliveCopyBundle}"
				key="fm.managefunds.invalidaccount" /> </font>
	</s:if>

	<div id="ssnWarningBlock"
		style="background-color: #F5F7A9; border-width: 1px; border-style: solid; border-color: #FBB117; display: none; color: #E41B17; width: 700px; height: 35px;"
		align="left">
		<br>
		<b>&nbsp&nbsp<img src="${staticContextPath}/images/icons/incIcon.gif"> Please enter your Personal Identification Information.</b>
	</div>

	<h3 style="padding: 10px;">
		Manage Your Funds
	</h3>
	<p style="padding: 10px;">
		Deposit funds into your ServiceLive Wallet so providers can be paid as
		soon as work is completed to your satisfaction. Deposits can be made
		from any bank account or credit card you wish for any amount you
		choose.
	</p>
	<p style="padding: 10px;">
		All account numbers are encrypted for your protection. To protect you
		from fraud, funds can only be withdrawn by contacting ServiceLive
		directly and withdrawals can only be credited back to the account you
		used for deposit.
	</p>



	<div class="jqmWindow2" id="popUpfaq"
		style="width: 800px; display: none; margin-top: 10%;" align="left">

		<div class="modalHomepage" style="background-color: white;">

			<div style="float: left;">

			</div>

			<a id="closeLink" href="javascript:void(0)" style="color: red;"
				onclick="closeFAQ();fnReturnFocus();">Close</a>

		</div>

		<div style="margin-left: 10px;">
			<h3>
				Frequently Asked Questions - Personal Information
			</h3>
			<br>
			<br>
			<img id="firstCloseArrow" onclick="showFirstAnswer();"
				style="padding: 0px 4px 0px 5px; position: relative; top: 2px;"
				src="${staticContextPath}/images/widgets/blueRightArrow.gif" />

			<img id="firstOpenArrow" onclick="showFirstAnswerOpen();"
				style="display: none; padding: 0px 4px 0px 5px; position: relative; top: 2px;"
				src="${staticContextPath}/images/widgets/grayDownArrow.gif" />

			<span id="firstQuestion" class="firstQuestion"
				onclick="showFirstAnswer();"
				style="color: #00A0D2; font-family: Verdana, Arial, Helvetica, sans-serif; font-weight: bolder;cursor: pointer;">Why
				do I have to provide Personal Identification Information?</span>

			<span id="firstQuestionOpen" class="firstQuestion"
				onclick="showFirstAnswerOpen();"
				style="display: none; color: #666666; font-family: Verdana, Arial, Helvetica, sans-serif; font-weight: bolder;cursor: pointer;">Why
				do I have to provide Personal Identification Information?</span>

			<br>
			<div id="firstAnswer"
				style="display: none; border: 1px solid #cecece; margin: 10px;">

				<p style="font-size: 10px; padding: 2px 0 6px;">
					To help the government fight the funding of terrorism and money
					laundering activities, Federal Law (31 CFR Parts 1010 and 1022 of
					the Bank Secrecy Act Regulations) requires us to ask for your name,
					address, date of birth, and your taxpayer identification number.
				</p>

			</div>

			<br>
			<img id="secondCloseArrow" onclick="showSecondAnswer();"
				style="padding: 0px 4px 0px 5px; position: relative; top: 2px;"
				src="${staticContextPath}/images/widgets/blueRightArrow.gif" />

			<img id="secondOpenArrow" onclick="showSecondAnswerOpen();"
				style="display: none; padding: 0px 4px 0px 5px; position: relative; top: 2px;"
				src="${staticContextPath}/images/widgets/grayDownArrow.gif" />

			<span id="secondQuestion" class="secondQuestion"
				onclick="showSecondAnswer();"
				style="color: #00A0D2; font-family: Verdana, Arial, Helvetica, sans-serif; font-weight: bolder;cursor: pointer;">What
				if I do not have a U.S. Taxpayer Identification Number? </span>

			<span id="secondQuestionOpen" class="secondQuestion"
				onclick="showSecondAnswerOpen();"
				style="display: none; color: #666666; font-family: Verdana, Arial, Helvetica, sans-serif; font-weight: bolder;cursor: pointer;">What
				if I do not have a U.S. Taxpayer Identification Number? </span>

			<br>
			<div id="secondAnswer"
				style="display: none; border: 1px solid #cecece; margin: 10px;">

				<p style="font-size: 10px; padding: 2px 0 6px;">
					You must provide us with an alternate form of personal
					identification, such as a Passport issued by a recognized foreign
					government.
				</p>
			</div>
			<br>
			<br>
		</div>

	</div>



	<div class="grayModuleContent">
		<table cellpadding="0" cellspacing="0" class="paddingBtm">
			<tr>
				<td width="140">
					<p>
						<strong>Option</strong>
					</p>
				</td>
				<td width="140">
					<p>
						<strong>Processing Time</strong>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<p>
						<strong>Bank Accounts</strong>
					</p>
				</td>
				<td>
				<c:choose>
					<c:when test="%{manageFundsTabDTO.autoACHInd == 'true'}">
						<p>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="fm.managefunds.autoACHApproval.processtime.msg" />
						</p>
					</c:when>
					<c:otherwise>
						<fmt:message bundle="${serviceliveCopyBundle}"
							key="fm.managefunds.nonAutoACHApproval.processtime.msg" />
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr>
				<td>
					<p>
						<strong>Credit Card</strong>
					</p>
				</td>
				<td>
					<p>
						Instantly
					</p>
				</td>
			</tr>
		</table>

	</div>
	<div class="grayModuleContent mainWellContent clearfix">
		<s:hidden name="einStoredFlag" id="einStoredFlag"
			value="%{#session.einStoredFlag}" />
		<s:hidden name="buyerTotalDeposit" id="buyerTotalDeposit"
			value="%{#session.buyerTotalDeposit}" />
		<s:hidden name="buyerThreshold" id="buyerThreshold"
			value="%{#session.buyerThreshold}" />
		<s:hidden name="buyerBitFlag" id="buyerBitFlag"
			value="%{#session.buyerBitFlag}" />
		<s:hidden name="isSSN" id="isSSN" value="%{#session.isSSN}" />
		<s:hidden name="ssnNo" id="ssnNo" value="%{#session.ssnNo}" />
		
		
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="350">
					<p>
						<label>
							Deposit from this account
						</label>
						<br />
						<tags:fieldError id="deposit from this Account."
							oldClass="paddingBtm">
							<s:select name="manageFundsTabDTO.accountList" headerKey="-1"
								id="manageFundsTabDTO.accountList" headerValue="Select One"
								list="%{#session.accountList}" listKey="accountTypeId"
								listValue="accountNameNum" cssStyle="width: 240px;" size="1"
								theme="simple" value="%{manageFundsTabDTO.accountList}"
								onchange="isCreditChecked(this)" />
						</tags:fieldError>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<div id="cvvCode" style="">
						<p>
							<label>
								Card Verification Number
							</label>
						</p>
						<s:password onfocus="clearTextbox(this)" cssClass="shadowBox"
							name="manageFundsTabDTO.cvvCode"
							value="%{manageFundsTabDTO.cvvCode}"
							id="manageFundsTabDTO.cvvCode" cssStyle="width: 160px;"
							maxlength="4" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="350">
					<p>
						<label>
							Amount:
						</label>
					</p>
					<p>
						<s:textfield onfocus="startPeriodicUpdater(event,this)"
							onblur="stopPeriodicUpdater(event)" cssClass="shadowBox"
							name="manageFundsTabDTO.depositAmount"
							value="%{manageFundsTabDTO.depositAmount}"
							id="manageFundsTabDTO.depositAmount" cssStyle="width: 160px;"
							maxlength="10" onkeyup="showEinEntryDiv(this)" />
					</p>
				</td>
			</tr>




		</table>
	</div>
	<div class="hpDescribe clearfix" style="${taxPayerStyle}" id="einDivBlock">

		<h2>
			Taxpayer/Personal Identification Information
		</h2>
		<p>
			You have reached a certain funding level in ServiceLive which
			requires us to collect your Taxpayer ID as personal identification as
			mandated by U.S. Governmental regulatory agencies. We are required to
			collect either your EIN (if you are a business) or your Social
			Security number and date of birth if you are an Individual or Sole
			Proprietor.
		</p>
		<p>

			<input type="radio" name="manageFundsTabDTO.einSsnInd" ${taxidEIN} id="ein" value="1" onclick="hideMsgs();showEin()" />
			EIN
			<input type="radio" name="manageFundsTabDTO.einSsnInd" ${taxidSSN} id="ssn" value="2" onclick="hideMsgs();showSsn()" />
			SSN
			<input type="radio" name="manageFundsTabDTO.einSsnInd" ${taxidAlt} id="alt" value="3" onclick="hideMsgs();showDivAlternateIdInfo()" />
			I do not have a U.S. Taxpayer ID
			

			<br />
		</p>



		<table span="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td span="50%">
					<div id="einBlock"
					 	<c:choose><c:when test="${taxidEIN == 'checked'}">style="display: block;"</c:when>
						<c:otherwise>style="display: none;"</c:otherwise></c:choose>>

						Taxpayer ID (EIN)
						<em class="req">*</em>
						<br />
						<tags:fieldError id="Taxpayer ID(EIN or SSN)" oldClass="errorBox">
							<s:textfield name="einTaxPayerId" id="einTaxPayerId"
								value="%{einTaxPayerId}" theme="simple" maxlength="9" onfocus="maskInput(this.id)"/>
						</tags:fieldError>

						<br>
						<br>

						Confirm Taxpayer ID (EIN)
						<em class="req">*</em>
						<br />
						<tags:fieldError id="Confirm Taxpayer ID(EIN or SSN)"
							oldClass="errorBox">
							<s:textfield name="confirmEinTaxPayerId"
								id="confirmEinTaxPayerId" value="%{confirmEinTaxPayerId}"
								theme="simple" maxlength="9" onfocus="maskInput(this.id)"/>
						</tags:fieldError>

					</div>
					<!-- SSN Block -->
					<div id="ssnBlock"
						<c:choose><c:when test="${taxidSSN == 'checked'}">style="display: block;"</c:when>
						<c:otherwise>style="display: none;"</c:otherwise></c:choose>>

						Taxpayer ID (SSN) 
						<em class="req">*</em>
						<br />
						<tags:fieldError id="Taxpayer ID(EIN or SSN)" oldClass="errorBox">
							<c:choose>
							<c:when test="${ssnSaveInd==false}">
							<s:textfield name="ssnTaxPayerId" id="ssnTaxPayerId" value="%{ssnTaxPayerId}" theme="simple" maxlength="9" disabled="true"/>
							&nbsp;&nbsp;<a id="editSSN" style="color: blue" onclick="editSSN()">Edit</a>	
							</c:when>
							<c:otherwise>
								<c:choose>
								<c:when test="${taxidEIN == 'checked'|| taxidAlt == 'checked'}">
									<s:textfield name="ssnTaxPayerId" id="ssnTaxPayerId" value="%{ssnTaxPayerId}" theme="simple" maxlength="9" disabled="true"/>
									<c:if test="${isSSN==true}">
									&nbsp;&nbsp;<a id="editSSN" style="color: blue" onclick="editSSN()">Edit</a>
									</c:if>	
								</c:when>
								<c:otherwise>
									<s:textfield name="ssnTaxPayerId" id="ssnTaxPayerId" value="%{ssnTaxPayerId}" theme="simple" maxlength="9" onfocus="maskInput(this.id)"/>
									<c:if test="${isSSN==true}">
									&nbsp;&nbsp;<a id="editSSN" style="color: blue" onclick="editSSN()">Cancel</a>	
									</c:if>
								</c:otherwise>
								</c:choose>
							</c:otherwise>
							</c:choose>
														
							<s:hidden id="ssnTaxPayerIdHidden" name="ssnTaxPayerIdHidden"
								value="%{ssnTaxPayerIdHidden}" />
							<input type="hidden" id="ssnSaveInd" name="ssnSaveInd" value="${ssnSaveInd}" />
							
						</tags:fieldError>

						<br>
						<br>

						Confirm Taxpayer ID (SSN)
						<em class="req">*</em>
						<br />
						<tags:fieldError id="Confirm Taxpayer ID(EIN or SSN)"
							oldClass="errorBox">
							<c:choose>
							<c:when test="${ssnSaveInd == 'false'}">
							<s:textfield name="confirmSsnTaxPayerId" id="confirmSsnTaxPayerId" value="%{confirmSsnTaxPayerId}" theme="simple" maxlength="9" disabled="true"/>
							</c:when>
							<c:otherwise>
								<c:choose>
								<c:when test="${taxidEIN == 'checked'|| taxidAlt == 'checked'}">
									<s:textfield name="confirmSsnTaxPayerId" id="confirmSsnTaxPayerId" value="%{confirmSsnTaxPayerId}" theme="simple" maxlength="9" disabled="true"/>
								</c:when>
								<c:otherwise>
									<s:textfield name="confirmSsnTaxPayerId" id="confirmSsnTaxPayerId" value="%{confirmSsnTaxPayerId}" theme="simple" maxlength="9" onfocus="maskInput(this.id)"/>
								</c:otherwise>
								</c:choose>							
							</c:otherwise>
							</c:choose>							
						</tags:fieldError>

					</div>
					<!-- SSN Block -->
					<div id="alternateIdInfoDivBlock"
						<c:choose>
						<c:when test="${taxidAlt == 'checked'}">style="display: block;"</c:when>
						<c:otherwise>style="display: none;"</c:otherwise></c:choose>>


						Document type
						<em class="req">*</em>
						<br />
						<tags:fieldError id="Type of Document" oldClass="errorBox">
							<s:textfield name="documentType" value="%{documentType}"
								theme="simple" maxlength="35" tabindex="1" />
						</tags:fieldError>

						<br>
						<br>

						Document Identification Number
						<em class="req">*</em>
						<br />
						<tags:fieldError id="Document ID Number" oldClass="errorBox">
							<s:textfield name="documentIdNo" id="documentIdNo"
								value="%{documentIdNo}" theme="simple" maxlength="15"
								tabindex="3" />
						</tags:fieldError>
						</label>
					</div>

				</td>
				<td span="50%">
					<div id="countryOfIssuanceBlock" 
						<c:choose><c:when test="${taxidAlt == 'checked'}">style="display: block;padding-left:50px;"</c:when>
						<c:otherwise>style="display: none;"</c:otherwise></c:choose>>

						Country of Issuance
						<em class="req">*</em>

						<br />
						<tags:fieldError id="Country of Issuance" oldClass="paddingBtm">
							<s:select list="%{#session.countryList}" headerValue="Select One"
								name="country" headerKey="-1" tabindex="2"></s:select>
						</tags:fieldError>

					</div>
				</td>
			</tr>
		</table>
		<table span="100%" cellpadding="0" cellspacing="0" id="dateOfBirth"
			<c:choose>
			<c:when test="${taxidSSN == 'checked'||taxidAlt == 'checked'}">style="display: block;"</c:when>
			<c:otherwise>style="display: none;"</c:otherwise></c:choose>>
			<tr>
				<td>
					<div id="ssnDobBlock"
						<c:choose><c:when test="${taxidSSN == 'checked'}">style="display: block;"</c:when>
						<c:otherwise>style="display: none;"</c:otherwise></c:choose>>
						<br>
						What is your Date of Birth?
						<em class="req">*</em>
						<br />
						<tags:fieldError id="Date of Birth" oldClass="errorBox">
							<s:textfield name="ssnDob" id="ssnDob" value="%{ssnDob}"
								theme="simple" maxlength="10" onfocus="showDatepicker(this.id);" />(Note: Date of Birth should be in MM/DD/YYYY format)
					</tags:fieldError>

					</div>
					<div id="altIdDobBlock"
						<c:choose><c:when test="${taxidAlt == 'checked'}">style="display: block;"</c:when>
						<c:otherwise>style="display: none;"</c:otherwise></c:choose>>
						<br>
						What is your Date of Birth?
						<em class="req">*</em>
						<br />
						<tags:fieldError id="Date of Birth" oldClass="errorBox">
							<s:textfield name="altIdDob" id="altIdDob" value="%{altIdDob}"
								theme="simple" maxlength="10" onfocus="showDatepicker(this.id);"
								tabindex="4" />(Note: Date of Birth should be in MM/DD/YYYY format)
					</tags:fieldError>

					</div>
				</td>
			</tr>
		</table>


		<br>
		<b>Important information for SL Bucks Wallet transactions</b>: To help
		the government fight the funding of terrorism and money laundering
		activities, Federal law requires us to ask for your name, address,
		date of birth, and your tax payer identification number
		<br>
		<img src="${staticContextPath}/images/s_icons/help.png" />
		<a class="trigger" id="download" onClick="showPopUpFAQ();" href="#">Why
			do I need to provide this information?</a>



	</div>

	<div class="clearfix">
		<div id="formNavButtonsDiv" class="formNavButtons">
			<c:choose>
			<c:when test="%{#session.AccountStatus == 'invalidAccount'}">
				<s:submit type="input"
					cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/depositFunds.gif);width:98px; height:20px;"
					cssClass="btn20Bevel" theme="simple" value=" " disabled="true" />
			</c:when>
			<c:otherwise>
				<s:submit type="input"
					cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/depositFunds.gif);width:98px; height:20px;"
					cssClass="btn20Bevel" theme="simple" value=" " />
			</c:otherwise>
			</c:choose>
			<br />
			<br />
			<span class="pymntServicesLabel">Payment Services Provided By:
				Integrated Payment Systems, Inc. </span>
		</div>
		<div id="disabledDepositFundsDiv" style="display: none">
			Depositing funds ....
		</div>
	</div>


</s:form>


