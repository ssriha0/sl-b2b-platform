<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	jQuery(document).ready(function($) {
		<c:if test="${approvalItems.commercialGeneralLiabilitySelected == true}">
			$('.cchecktoggle').show();
		</c:if>
		<c:if test="${approvalItems.vehicleLiabilitySelected == true}">
			$('.vchecktoggle').show();
		</c:if>
		<c:if test="${approvalItems.workersCompensationSelected == true}">
			$('.wchecktoggle').show();
		</c:if>
		
		$('input.vshcheck').click(function() {
			$('.vchecktoggle').toggle();
			
	   		if(! $('input.vshcheck').attr('checked'))
	   		{
	   			$('#vehicleLiabilityAmt>input').val('');
	   			$('.vchecktoggle>input').removeAttr('checked');
	   		}
			
		});


		$('input.wshcheck').click(function() {
			$('.wchecktoggle').toggle();
			
	   		if(! $('input.wshcheck').attr('checked'))
	   		{
	   			$('.wchecktoggle>input').removeAttr('checked');
	   		}	   			
							
		});

		$('input.cshcheck').click(function() {
			$('.cchecktoggle').toggle();
			
	   		if(! $('.cshcheck>input').attr('checked'))
	   		{
	   			$('#commercialGeneralLiabilityAmt>input').val('');
	   			$('#commercialGeneralLiabilityVerified>input').removeAttr('checked');
	   		}
							
		});
	});
	
  //@bkumar2 SLT- 881 added method to restrict decimal value for vehical liability and Commercial General Liability
	/* function validatePositiveInteger( objAmount )
     {
		var values = objAmount.value;
   	
           // USE JAVAACRIPT REGEX
           if( values == "" )
        	   values = 0;
           if(/^([1-9]\d*)+$/.test(values))
        	   {	
        	   return true;
        	   }else{
        		   objAmount.value="";
        		   alert("Please Enter Valid Integer Amount");
        		   return false;   
        	   }
           	
                      
           
     } */
</script>

<table border="0" cellpadding="5" cellspacing="0">
	<thead>
		<th colspan="2">
			Insurance
		</th>
	</thead>
	<tbody>
		<tr>
			<td>
				<div class="left checkbox">
					<s:checkbox cssClass="checkbox vshcheck"
						id="approvalItems.vehicleLiabilitySelected"
						name="approvalItems.vehicleLiabilitySelected" fieldValue="true"
						value="%{approvalItems.vehicleLiabilitySelected}"></s:checkbox>
				</div>
				<label>
					Vehicle Liability
				</label>
			</td>
		</tr>
		<tr>
			<td class="textleft verify">
				<label class="vchecktoggle sm">
					Minimum Amount
					<span class="req">*</span> $
				</label>
				<span id="vehicleLiabilityAmt"><s:textfield id="approvalItems.vehicleLiabilityAmt"
					name="approvalItems.vehicleLiabilityAmt"
					value="%{approvalItems.vehicleLiabilityAmt}" theme="simple"
					cssClass="vchecktoggle text" cssStyle="width: 80" maxlength="9"/>
				</span>
			</td>
		</tr>
		<tr>
			<td class="textleft verify">
				<div class="left checkbox vchecktoggle">
					<s:checkbox cssClass="checkbox"
						id="approvalItems.vehicleLiabilityVerified"
						name="approvalItems.vehicleLiabilityVerified" fieldValue="true"
						value="%{approvalItems.vehicleLiabilityVerified}"></s:checkbox>
					Must Be Verified
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="left checkbox">
					<s:checkbox cssClass="checkbox wshcheck"
						id="approvalItems.workersCompensationSelected"
						name="approvalItems.workersCompensationSelected" fieldValue="true"
						value="%{approvalItems.workersCompensationSelected}"></s:checkbox>
				</div>
				<label>
					Workers Compensation
				</label>
			</td>
		</tr>
		<tr>
			<td class="textleft verify">
				<div class="left checkbox wchecktoggle">
					<s:checkbox cssClass="checkbox"
						id="approvalItems.workersCompensationVerified"
						name="approvalItems.workersCompensationVerified" fieldValue="true"
						value="%{approvalItems.workersCompensationVerified}"></s:checkbox>
					Must Be Verified
				</div>
			</td>
		</tr>
		<tr>
			<td>

				<div class="left checkbox">
					<s:checkbox cssClass="checkbox cshcheck"
						id="approvalItems.commercialGeneralLiabilitySelected"
						name="approvalItems.commercialGeneralLiabilitySelected"
						fieldValue="true"
						value="%{approvalItems.commercialGeneralLiabilitySelected}"></s:checkbox>
				</div>

				<label>
					Commercial General Liability
				</label>
			</td>
		</tr>
		<tr>
			<td class="textleft verify">
				<label class="cchecktoggle sm">
					Minimum Amount
					<span class="req">*</span> $
				</label>
				<span id="commercialGeneralLiabilityAmt"><s:textfield id="approvalItems.commercialGeneralLiabilityAmt"
					name="approvalItems.commercialGeneralLiabilityAmt"
					value="%{approvalItems.commercialGeneralLiabilityAmt}"
					theme="simple" cssClass="cchecktoggle text" cssStyle="width: 80"
					maxlength="9" />
					</span>
			</td>
		</tr>
		<tr>
			<td class="textleft verify">
				<div class="left checkbox cchecktoggle">
					<span id="commercialGeneralLiabilityVerified"><s:checkbox cssClass="checkbox"
						id="approvalItems.commercialGeneralLiabilityVerified"
						name="approvalItems.commercialGeneralLiabilityVerified"
						fieldValue="true"
						value="%{approvalItems.commercialGeneralLiabilityVerified}"></s:checkbox>
					</span>
				Must Be Verified
				</div>
			</td>
		</tr>
	</tbody>
</table>