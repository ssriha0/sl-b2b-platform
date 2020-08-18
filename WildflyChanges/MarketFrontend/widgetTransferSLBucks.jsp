<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="theId" scope="request"
	value="<%=request.getAttribute("tab")%>" />
<c:set var="role" value="${roleType}" />

<script language="JavaScript" type="text/javascript">
	function dropdownChange(account1, account2)
	{
		var dropdown = document.getElementById('transferFundsDTO.reasonCode');
		var reason = dropdown.value;
		var isDebit = false;
		var isEscheatment = false;
		document.getElementById('transferFundsDTO.amount').value='';
		document.getElementById('transferFundsDTO.note').value='';   
		if(reason == 10)
		{
			isDebit = false;
		}
		else if(reason == 20)
		{
			isDebit = false;			
		}
		else if(reason == 30)
		{
			isDebit = false;			
		}
		else if(reason == 40)
		{
			isDebit = true;			
		}
		else if(reason == 50)
		{
			isDebit = false;			
		}
		else if(reason == 60)
		{
			isDebit = true;			
		}
		
		else if(reason == 70)
		{
			isEscheatment = true;
			var StateRegulationNote = document.getElementById('StateRegulationNote').value;
			document.getElementById('transferFundsDTO.note').value=StateRegulationNote;
			var availableBalance=document.getElementById('availableBalance').value;
			availableBalance=availableBalance.replace('$','');
			document.getElementById('transferFundsDTO.amount').value=availableBalance;
		} 
		
		else if(reason == 90)
		{
			isEscheatment = true;
			var IRSlevyNote = document.getElementById('IRSLevyNote').value;
			document.getElementById('transferFundsDTO.note').value=IRSlevyNote;
		}
		
		else if(reason == 100)
		{
			isEscheatment = true;
			var LegalHoldNote = document.getElementById('LegalHoldNote').value;
			document.getElementById('transferFundsDTO.note').value=LegalHoldNote;
		}
		
		if(reason == 90 || reason == 100){
			var PermissibleHoldBalance=document.getElementById('PermissibleHoldBalance').value;
			PermissibleHoldBalance=PermissibleHoldBalance.replace('$','');
			document.getElementById('transferFundsDTO.amount').value=PermissibleHoldBalance;
		}
		

		if(isEscheatment)
		{
			//alert('From: Member To: ServiceLive');
			account1 = account2;
			account2 = 'First Data';
			var to = document.getElementById('toAccount');	
			var from = document.getElementById('fromAccount');
			//alert('TO=' + to + ' FROM=' + from);
			if(to != null)
				to.innerHTML = account1;
			if(to != null)
				from.innerHTML = account2; 
		}
		if(isDebit)
		{
			//alert('From: Member To: ServiceLive');
			var to = document.getElementById('toAccount');	
			var from = document.getElementById('fromAccount');
			//alert('TO=' + to + ' FROM=' + from);
			if(to != null)
				to.innerHTML = account1;
			if(to != null)
				from.innerHTML = account2;
		}
		else
		{
			//alert('From: ServiceLive To: Member');
			var to = document.getElementById('toAccount');	
			var from = document.getElementById('fromAccount');
			//alert('TO=' + to + ' FROM=' + from);
			if(to != null)
				document.getElementById('toAccount').innerHTML = account2;
			if(to != null)
				document.getElementById('fromAccount').innerHTML = account1;			
		}
		
		if(reason == -1)
		{
			var to = document.getElementById('toAccount');	
			var from = document.getElementById('fromAccount');
			//alert('TO=' + to + ' FROM=' + from);
			if(to != null)
				document.getElementById('toAccount').innerHTML = "--";
			if(to != null)
				document.getElementById('fromAccount').innerHTML = "--";					
		}
	}
	// Limit the text field to only numbers (with decimals)
	function format(input){
		var num = input.value.replace(/\,/g,'');
		if(!isNaN(num)){
			if(num.indexOf('.') > -1){
				numArr = num.split('.');
				if(numArr[1].length > 2)
				{
					var newnumber = Math.round(num*Math.pow(10,2))/Math.pow(10,2);
					input.value=newnumber;
				}
			}
		}
		else
		{
			input.value="0.0";		
		}
	}
</script>


<s:form action="financeManagerController_execute" id="widgetTransferSLBucks"
	name="widgetTransferSLBucks" method="post"
	enctype="multipart/form-data" theme="simple">
<input type="hidden" name="StateRegulationNote" id="StateRegulationNote" value="${StateRegulationNote}"/>	
<input type="hidden" name="availableBalance" id="availableBalance" value="${AvailableBalance}" />
<input type="hidden" name="LegalHoldNote" id="LegalHoldNote" value="${LegalHoldNote}"/>
<input type="hidden" name="IRSLevyNote" id="IRSLevyNote" value="${IRSLevyNote}"/>
<input type="hidden" name="PermissibleHoldBalance" id="PermissibleHoldBalance" value="${PermissibleHoldBalance}"/>
<input type="hidden" id="slBucksMail" value="${transferFundsDTO.slBucksMail}"/>
	<div dojoType="dijit.TitlePane" title="Transfer SL Bucks"
		id="widget_member_info_${theId}"
		style="padding-top: 1px; width: 249px;" open="true">

		<span class="dijitInfoNodeInner"> <a href="#"> </a> </span>

		<table border="0" cellpadding="0" cellspacing="0" class="">
		<tr class="error">
			<td colspan="2" class="errMsg alignRight">
				<s:if test="#request.tslbErrors != null">
							<div id="tslbErrors" style="color: red;">
							${request.tslbErrors}				
							</div>
				</s:if>
				<s:if test="#request.tslbSuccess != null">
							<div id="tslbSuccess" style="color: green;">
							${request.tslbSuccess}				
							</div>
				</s:if>				 
			</td>
			
		</tr>
			<tr>
				<td colspan=2>
					<b> Transfer Reason Code:</b>
				</td>
			</tr>
			<tr>
				<td colspan=2>
					<%-- <s:select list="" cssStyle="width:150px"/> --%>

					<select style="width: 185px" id="transferFundsDTO.reasonCode" name="transferFundsDTO.reasonCode" onchange="dropdownChange('ServiceLive', 'Member');" >
						<option value="-1">
						Select Code
						</option>
						<c:forEach items="${reasonCodes}" var="reason">
							<option value="${reason.id}">
								${reason.descr}
							</option>
						</c:forEach>
					</select>
					
				</td>
			</tr>
			
			<tr>
				<td>
					<b> From: </b>
				</td>
				<td>
					<p id="fromAccount">--</p>
				</td>
			</tr>
			
			<tr>
				<td>
					<b> To: </b>
				</td>
				<td>
					<p id="toAccount">--</p>
				</td>
			</tr>


			<tr>
				<td>
					<b> Amount: </b>
				</td>
				<td align="right">
					$
					<s:textfield id="transferFundsDTO.amount" name="transferFundsDTO.amount"					
						cssStyle="width: 100px;" cssClass="shadowBox grayText"
						value="%{transferFundsDTO.amount}" theme="simple" onkeyup="javascript:format(this);" />
				</td>
			</tr>

			<tr>
				<td colspan=2>
					Note:<font color="red">*</font>  
				</td>
			</tr>
			<tr>
				<td colspan=2>
			<textarea style="width: 150px;" name="transferFundsDTO.note"  id="transferFundsDTO.note" class="shadowBox grayText" onKeyDown="limitSLText('transferFundsDTO.note');" 
				onKeyUp="limitSLText('transferFundsDTO.note');"> ${transferFundsDTO.note}</textarea>
				</td>
			</tr>
		</table>
      <c:if test="${legalHoldPermission == true}">
			<input type="radio" name="transferFundsDTO.slBucksMail" value="mailRequire" checked>Send Email Notice<br>
			<input type="radio" name="transferFundsDTO.slBucksMail" value="mailNotRequire">Do Not Send Email Notice<br>
		</c:if>

		<table height=10px>
		</table>
			<s:submit type="input" method="buttonWidgetTransferSLFunds"
				src="%{#request['staticContextPath']}/images/common/spacer.gif"
				cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/transferFunds.gif); width:111px; height:20px;"
				cssClass="btn20Bevel" theme="simple" value=" " />
	</div>


</s:form>

