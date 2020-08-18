<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>


	<div style="margin-left: 20px;margin-top: 20px;">
 	<div id="errDivForCredit"></div>
	</div>
	
	
<div class="darkGrayModuleHdr">
	Credit Card Account Information
</div>

<div class="grayModuleContent mainWellContent clearfix">

	<table cellpadding="0" cellspacing="0">
		<tr>
			<td width="220">
				<p>
					<tags:fieldError id="CardHolderName" oldClass="paddingBtm">	
					<label>
						Name on Card
					</label>
					<br />
					<c:choose>
					<c:when test="%{#session.cardbuttonMode == 'Edit Mode'}" >
						<s:textfield theme="simple" name="cardHolderName"
								id="cardHolderName" cssClass="shadowBox grayText"
								cssStyle="width: 200px;" value="%{cardHolderName}" maxlength="75" readonly="true" />
					</c:when>
					<c:otherwise>
						<s:textfield theme="simple" name="cardHolderName"
								id="cardHolderName" cssClass="shadowBox grayText"
								cssStyle="width: 200px;" value="%{cardHolderName}" maxlength="75" />
					</c:otherwise>
					</c:choose>
					</tags:fieldError>
				</p>
			</td>
			<td width="100"></td>
			<td></td>
		</tr>
		
		<tr>
			<td>
				<p>
					<tags:fieldError id="CardTypeId" oldClass="paddingBtm">	
					<label>
						Card Type
					</label>
					<br />
					<c:choose>
					<c:when test="%{#session.cardbuttonMode == 'Edit Mode'}" >
						<s:select  id="CardTypeId"	
			    				name="cardTypeId" disabled="true"
			   					headerKey="-1"
			       				headerValue="Select One"
			     				cssStyle="width: 140px;" size="1"
			      				theme="simple"
								list="#session.creditCardTypeMap"
								listKey="id"
								listValue="type"
								
							/>
					</c:when>
					<c:otherwise>
						<s:select id="CardTypeId"
		    				name="cardTypeId"
		   					headerKey="-1"
		       				headerValue="Select One"
		     				cssStyle="width: 140px;" size="1"
		      				theme="simple"
							list="#session.creditCardTypeMap"
							listKey="id"
							listValue="type"
							
						/>
					</c:otherwise>
					</c:choose>
					</tags:fieldError>
				</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>
					<tags:fieldError id="CardNumber" oldClass="paddingBtm">
					<label>
						Card Number
					</label>
					<br />
					<c:choose>
					<c:when test="%{#session.cardbuttonMode == 'Edit Mode'}" >
						<s:textfield theme="simple" name="cardNumber"
								id="cardNumber" cssClass="shadowBox grayText"
								cssStyle="width: 200px;" value="%{cardNumber}" maxlength="16" readonly="true"/>
								
				
					</c:when>
					<c:otherwise>
						<s:textfield theme="simple" name="cardNumber"
								id="cardNumber" cssClass="shadowBox grayText"
								cssStyle="width: 200px;" value="%{cardNumber}" maxlength="16" 
								/>
						<s:hidden name="tokenizeCardNumber" id="tokenizeCardNumber" value = "%{#session.tokenizeCardNumber}" />
						<s:hidden name="maskedCardNumber" id="maskedCardNumber" value="%{#session.maskedCardNumber}" />
						<s:hidden name="responseCode" id="responseCode" value="%{#session.responseCode}" />
						<s:hidden name="responseMessage" id="responseMessage" value="%{#session.responseMessage}" />
						<s:hidden name="creditCardAuthTokenizeUrl" id="manageAccountsTabDTO.CreditCardAuthTokenizeUrl" value = "%{#session.CreditCardAuthTokenizeUrl}" />
						<s:hidden name="creditCardTokenUrl" id="manageAccountsTabDTO.CreditCardTokenUrl" value = "%{#session.CreditCardTokenUrl}" />
						<s:hidden name="creditCardTokenAPICrndl" id="manageAccountsTabDTO.CreditCardTokenAPICrndl" value = "%{#session.CreditCardTokenAPICrndl}" />
						<s:hidden name="creditCardErrorMessage" id="creditCardErrorMessage" value = "%{#session.creditCardErrorMessage}" />
						
						
						
					</c:otherwise>
					</c:choose>
					</tags:fieldError>
				</p>
			</td>
		</tr>
		
		<tr>
			<td colspan="3">
				<p>
					<tags:fieldError id="ExpiryDate" oldClass="paddingBtm">
					<label>
						Expiration Date
					</label>
					<br />
					<c:choose>
					<c:when test="%{#session.cardbuttonMode == 'Edit Mode'}" >
						<select id="expirationMonth" name="expirationMonth" class="grayText" style="width: 60px;" disabled="disabled">
							<option value="01" <c:if test="${expirationMonth == 01}">selected</c:if>>Jan</option>
							<option value="02" <c:if test="${expirationMonth == 02}">selected</c:if>>Feb</option>
							<option value="03" <c:if test="${expirationMonth == 03}">selected</c:if>>Mar</option>
							<option value="04" <c:if test="${expirationMonth == 04}">selected</c:if>>Apr</option>
							<option value="05" <c:if test="${expirationMonth == 05}">selected</c:if>>May</option>
							<option value="06" <c:if test="${expirationMonth == 06}">selected</c:if>>Jun</option>
							<option value="07" <c:if test="${expirationMonth == 07}">selected</c:if>>Jul</option>
							<option value="08" <c:if test="${expirationMonth == 08}">selected</c:if>>Aug</option>
							<option value="09" <c:if test="${expirationMonth == 09}">selected</c:if>>Sep</option>
							<option value="10" <c:if test="${expirationMonth == 10}">selected</c:if>>Oct</option>
							<option value="11" <c:if test="${expirationMonth == 11}">selected</c:if>>Nov</option>
							<option value="12" <c:if test="${expirationMonth == 12}">selected</c:if>>Dec</option>
						</select>
						<select id="expirationYear" name="expirationYear" class="grayText" onclick="changeDropdown(this)"
							style="width: 60px;" disabled="disabled">
							<option value="08" <c:if test="${expirationYear == 08}">selected</c:if>>2008</option>
							<option value="09" <c:if test="${expirationYear == 09}">selected</c:if>>2009</option>
							<option value="10" <c:if test="${expirationYear == 10}">selected</c:if>>2010</option>
							<option value="11" <c:if test="${expirationYear == 11}">selected</c:if>>2011</option>
							<option value="12" <c:if test="${expirationYear == 12}">selected</c:if>>2012</option>
							<option value="13" <c:if test="${expirationYear == 13}">selected</c:if>>2013</option>
							<option value="14" <c:if test="${expirationYear == 14}">selected</c:if>>2014</option>
							<option value="15" <c:if test="${expirationYear == 15}">selected</c:if>>2015</option>
							<option value="16" <c:if test="${expirationYear == 16}">selected</c:if>>2016</option>
							<option value="17" <c:if test="${expirationYear == 17}">selected</c:if>>2017</option>
							<option value="18" <c:if test="${expirationYear == 18}">selected</c:if>>2018</option>
							<option value="19" <c:if test="${expirationYear == 19}">selected</c:if>>2019</option>
							<option value="20" <c:if test="${expirationYear == 20}">selected</c:if>>2020</option>
							<option value="21" <c:if test="${expirationYear == 21}">selected</c:if>>2021</option>
							<option value="22" <c:if test="${expirationYear == 22}">selected</c:if>>2022</option>
							<option value="23" <c:if test="${expirationYear == 23}">selected</c:if>>2023</option>
							<option value="24" <c:if test="${expirationYear == 24}">selected</c:if>>2024</option>
						</select>
					</c:when>
					
					<c:otherwise>
						<select id="expirationMonth" name="expirationMonth" class="grayText" style="width: 60px;" >
							<option value="01" <c:if test="${expirationMonth == 01}">selected</c:if>>Jan</option>
							<option value="02" <c:if test="${expirationMonth == 02}">selected</c:if>>Feb</option>
							<option value="03" <c:if test="${expirationMonth == 03}">selected</c:if>>Mar</option>
							<option value="04" <c:if test="${expirationMonth == 04}">selected</c:if>>Apr</option>
							<option value="05" <c:if test="${expirationMonth == 05}">selected</c:if>>May</option>
							<option value="06" <c:if test="${expirationMonth == 06}">selected</c:if>>Jun</option>
							<option value="07" <c:if test="${expirationMonth == 07}">selected</c:if>>Jul</option>
							<option value="08" <c:if test="${expirationMonth == 08}">selected</c:if>>Aug</option>
							<option value="09" <c:if test="${expirationMonth == 09}">selected</c:if>>Sep</option>
							<option value="10" <c:if test="${expirationMonth == 10}">selected</c:if>>Oct</option>
							<option value="11" <c:if test="${expirationMonth == 11}">selected</c:if>>Nov</option>
							<option value="12" <c:if test="${expirationMonth == 12}">selected</c:if>>Dec</option>
						</select>
						<select id="expirationYear" name="expirationYear" class="grayText" onclick="changeDropdown(this)"
							style="width: 60px;" >
							<option value="08" <c:if test="${expirationYear == 08}">selected</c:if>>2008</option>
							<option value="09" <c:if test="${expirationYear == 09}">selected</c:if>>2009</option>
							<option value="10" <c:if test="${expirationYear == 10}">selected</c:if>>2010</option>
							<option value="11" <c:if test="${expirationYear == 11}">selected</c:if>>2011</option>
							<option value="12" <c:if test="${expirationYear == 12}">selected</c:if>>2012</option>
							<option value="13" <c:if test="${expirationYear == 13}">selected</c:if>>2013</option>
							<option value="14" <c:if test="${expirationYear == 14}">selected</c:if>>2014</option>
							<option value="15" <c:if test="${expirationYear == 15}">selected</c:if>>2015</option>
							<option value="16" <c:if test="${expirationYear == 16}">selected</c:if>>2016</option>
							<option value="17" <c:if test="${expirationYear == 17}">selected</c:if>>2017</option>
							<option value="18" <c:if test="${expirationYear == 18}">selected</c:if>>2018</option>
							<option value="19" <c:if test="${expirationYear == 19}">selected</c:if>>2019</option>
							<option value="20" <c:if test="${expirationYear == 20}">selected</c:if>>2020</option>
							<option value="21" <c:if test="${expirationYear == 21}">selected</c:if>>2021</option>
							<option value="22" <c:if test="${expirationYear == 22}">selected</c:if>>2022</option>
							<option value="23" <c:if test="${expirationYear == 23}">selected</c:if>>2023</option>
							<option value="24" <c:if test="${expirationYear == 24}">selected</c:if>>2024</option>
						</select>
					</c:otherwise>
					
					</c:choose>
					</tags:fieldError>
				</p>
			</td>
		</tr>
		<tr>
		<td colspan="3">
		<span style="color: red; margin: 20px;">NOTE:</span> If your card does not have an expiration date, you do not need to enter.
		</td>
		</tr>
		<tr>
			<td>
				<p>
					<tags:fieldError id="BillingAddress1" oldClass="paddingBtm">	
					<label>
						Billing Address Line 1
					</label>
					<br />
					<c:choose>
					<c:when test="%{#session.cardbuttonMode == 'Edit Mode'}" >
						<s:textfield theme="simple" name="billingAddress1"
								id="billingAddress1" cssClass="shadowBox grayText"
								cssStyle="width: 200px;" value="%{billingAddress1}" maxlength="30" readonly="true"/>
					</c:when>
					<c:otherwise>
						<s:textfield theme="simple" name="billingAddress1"
								id="billingAddress1" cssClass="shadowBox grayText"
								cssStyle="width: 200px;" value="%{billingAddress1}" maxlength="30" />
					</c:otherwise>
					</c:choose>
					</tags:fieldError>
				</p>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<p>
					<tags:fieldError id="BillingAddress2" oldClass="paddingBtm">
					<label>
						Billing Address Line 2
					</label>
					<br />
					<c:choose>
					<c:when test="%{#session.cardbuttonMode == 'Edit Mode'}" >
						<s:textfield theme="simple" name="billingAddress2"
								id="billingAddress2" cssClass="shadowBox grayText"
								cssStyle="width: 200px;" value="%{billingAddress2}" maxlength="30" readonly="true"/>
					</c:when>
					<c:otherwise>
						<s:textfield theme="simple" name="billingAddress2"
								id="billingAddress2" cssClass="shadowBox grayText"
								cssStyle="width: 200px;" value="%{billingAddress2}" maxlength="30" />
					</c:otherwise>
					</c:choose>
					Optional
					</tags:fieldError>
				</p>
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td width="120">
				<p>
					<tags:fieldError id="City" oldClass="paddingBtm">
					<label>
						City
					</label>
					<br />
					<c:choose>
					<c:when test="%{#session.cardbuttonMode == 'Edit Mode'}" >
						<s:textfield theme="simple" name="billingCity"
								id="billingCity" cssClass="shadowBox grayText"
								cssStyle="width: 100px;" value="%{billingCity}" maxlength="30" readonly="true"/>
					</c:when>
					<c:otherwise>
						<s:textfield theme="simple" name="billingCity"
								id="billingCity" cssClass="shadowBox grayText"
								cssStyle="width: 100px;" value="%{billingCity}" maxlength="30" />
					</c:otherwise>
					</c:choose>
					</tags:fieldError>
				</p>
			</td>
			<td width="120">
				<p>
					<tags:fieldError id="State" oldClass="paddingBtm">
					<label>
						State
					</label>
					<br />
					<c:choose>
					<c:when test="%{#session.cardbuttonMode == 'Edit Mode'}" >
						<s:select id="billingState" disabled="true"
							name="billingState"
							value="%{billingState}"
							list="#application['stateCodes']" listKey="type"
							listValue="descr" cssStyle="width: 100px;"
							cssClass="shadowBox grayText">
						</s:select>
					</c:when>
					<c:otherwise>
						<s:select id="billingState"
							name="billingState"
							value="%{billingState}"
							list="#application['stateCodes']" listKey="type"
							listValue="descr" cssStyle="width: 100px;"
							cssClass="shadowBox grayText">
						</s:select>
					</c:otherwise>
					</c:choose>
					</tags:fieldError>
				</p>
			</td>
			<td>
				<p>
					<tags:fieldError id="Zip" oldClass="paddingBtm">
					<label>
						Zip
					</label>
					<br />
					<c:choose>
					<c:when test="%{#session.cardbuttonMode == 'Edit Mode'}" >
						<s:textfield id="billingZip" disabled="true"
							name="billingZip"
							value="%{billingZip}"
							 maxlength="5" cssStyle="width: 60px;"
							cssClass="shadowBox grayText" />
					</c:when>
					<c:otherwise>
						<s:textfield id="billingZip"
							name="billingZip"
							value="%{billingZip}"
							 maxlength="5" cssStyle="width: 60px;"
							cssClass="shadowBox grayText" />
					</c:otherwise>
					</c:choose>
					</tags:fieldError>
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
			<c:when test="%{#session.cardbuttonMode == 'Edit Mode'}" >
				<s:submit type="input" 
			 	 		  cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/editEntry.gif);width:74px; height:20px;"
			 			  cssClass="btn20Bevel" 
			       		  method="editCreditCardAccounts"
			  			  theme="simple"
			  			  value=""/>	
			</c:when>
			<c:otherwise>
				<s:submit type="input" onclick="javascript:tokenizeCreditCard(this);return false"
			  			  cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/saveInformation.gif);width:109px; height:20px;"
			  			  cssClass="btn20Bevel" 
			  			  
			  			  theme="simple"
			  			  value=" "/>	
			</c:otherwise>
		</c:choose>
		</td>
		
		<td>
			<c:if test="%{#session.cardbuttonMode == 'Cancel Mode'}" >
				<s:submit type="input" 
			  			  cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/cancel.gif);width:54px; height:20px;"
			  			  cssClass="btn20Bevel" 
			  			  method="cancelCreditCardAccounts"
			  			  theme="simple"
			  			  value=""/>	
			</c:if>
		</td>
	</tr>
</table>
	
</div>
