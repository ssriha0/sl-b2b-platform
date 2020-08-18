<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<style>
a.class, a.class:hover {
    text-decoration:underline;
    border:none; 
    margin-left: 0px;
    padding-left:0px;
}

</style>
<script type="text/javascript">

function editFunction(){
	document.getElementById('editLink').style.display = 'none';
	document.getElementById('cancelLink').style.display = 'block';
	document.getElementById('addonServicesDTO.creditCardNumber').value='';
	document.getElementById('addonServicesDTO.creditCardNumber').disabled='';
	document.getElementById('editOrCancelCreditCard').value='edited';
}

function cancelFunction(){
	document.getElementById('editLink').style.display = 'block';
	document.getElementById('cancelLink').style.display = 'none';
	document.getElementById('addonServicesDTO.creditCardNumber').value=document.getElementById('creditCardNumberActual').value;
	document.getElementById('addonServicesDTO.creditCardNumber').disabled='disabled';
	document.getElementById('editOrCancelCreditCard').value='edit';
}



</script>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<s:set name="totalElms" value="%{addonServicesDTO.addonServicesList.size()}" />
<c:if test="${!empty model.addonServicesDTO.addonServicesList}">
			<h3><fmt:message bundle="${serviceliveCopyBundle}" key="details.completepayment.addon.msg5" /><span class="req">*</span></h3>
			<p><fmt:message bundle="${serviceliveCopyBundle}" key="details.completepayment.addon.msg6" /></p>
			
			<div id="payments" class="clearfix">
				<div class="clearfix">
				
					<div class="left">									
						<s:if test="%{addonServicesDTO.paymentRadioSelection == 'CA'}">
							<label>Check</label><input onclick="jQuery('div#bycc').hide(); jQuery('div#bycheck').show(); jQuery('div#editOrCancelCC').hide();" type="radio" class="radio" id="pmtTypeCA" name="pmttype" value="CA" checked="checked">
						</s:if>
						<s:else>
							<label>Check</label><input onclick="jQuery('div#bycc').hide(); jQuery('div#bycheck').show(); jQuery('div#editOrCancelCC').hide();" type="radio" class="radio" id="pmtTypeCA" name="pmttype" value="CA" >				
						</s:else>
					</div>
					
					
					<div class="left">
						<s:if test="%{addonServicesDTO.paymentRadioSelection == 'CC'}">					
							<label>Credit Card</label><input onclick="jQuery('div#bycheck').hide(); jQuery('div#bycc').show(); jQuery('div#editOrCancelCC').show();" type="radio" class="radio" id="pmtTypeCC" name="pmttype" value="CC" checked="checked">
						</s:if>
						<s:else>
							<label>Credit Card</label><input onclick="jQuery('div#bycheck').hide(); jQuery('div#bycc').show(); jQuery('div#editOrCancelCC').show();" type="radio" class="radio" id="pmtTypeCC" name="pmttype" value="CC" >					
						</s:else>
					</div>
					
				</div>
				
				<s:if test="%{addonServicesDTO.paymentRadioSelection == 'CA'}">
					<div id="bycheck" style="display:block">
				</s:if>
				<s:else>
					<div id="bycheck" style="display:none">
				</s:else>
					<div class="left" style="width: 200px;height: 85px">
					<div class="clearfix" style="margin-bottom: 5px" id="checkNo">
						<label>
							Check Number <span class="req">*</span>
						</label>
						<s:textfield id="addonServicesDTO.checkNumber" onblur="hideToolTip()" name="addonServicesDTO.checkNumber"  cssClass="text toolTipShow" theme="simple" maxlength="16"/>
					</div>
					<div class="clearfix" id="checkAmt">
						<label>Amount <span class="req">*</span></label>
						<s:if test="%{getText('format.money', '0.00', {addonServicesDTO.checkAmount}) =='null'}">
							<s:textfield 
								id="addonServicesDTO.checkAmount" onblur="hideToolTip()" 
								name="addonServicesDTO.checkAmount"
								 
								cssClass="text toolTipShow" theme="simple" 
								onchange="addOnObject.validatePaymentAmount( this );"
								/>
						</s:if>
						<s:else>
							<s:textfield 
								id="addonServicesDTO.checkAmount" onblur="hideToolTip()" 
								name="addonServicesDTO.checkAmount"
								value="%{getText('format.money', '0.00', {addonServicesDTO.checkAmount})}"
								cssClass="text toolTipShow" theme="simple" 
								onchange="addOnObject.validatePaymentAmount( this );"
								/>
						</s:else>
					</div>
					</div>
					
						<strong>Mail Check To:</strong><br />
						Innovel Solutions<br />
						c/o: CONDUENT<br />
						P.O. Box 30694<br />
						Salt Lake City, Utah 84130-0694<br />
						
					
				</div>
				<s:if test="%{addonServicesDTO.paymentRadioSelection == 'CC'}">
					<div id="bycc" style="display:block">
				</s:if>
				<s:else>
					<div id="bycc" style="display:none">
				</s:else> 
				
					<div class="clearfix">
						<div class="left">
							<div class="clearfix">
								<label>
									End Customer Credit Card #
									<span class="req">*</span>
								</label>
								<%-- <input id="ccnum" type="text" class="text" /> --%>
								<s:textfield id="addonServicesDTO.creditCardNumber" name="addonServicesDTO.creditCardNumber" value="%{addonServicesDTO.creditCardNumber}" theme="simple" cssClass="text" cssStyle="width: 140px"  maxlength="16" />
							</div>
							<div id="editOrCancelCC" class="right" style="margin-bottom: 25px; width: 200px;">
								<a id="editLink" class="class right" onclick="editFunction();" style="color:#00A0D2;cursor: pointer">Edit </a>
                				<a id="cancelLink" class="class right" onclick="cancelFunction();" style="color:#00A0D2;cursor: pointer">Cancel</a>
							</div>
							<div class="clearfix"><label>Card Type <span class="req">*</span></label> 
								<s:select 	
					    				id="addonServicesDTO.selectedCreditCardType" 
					    				name="addonServicesDTO.selectedCreditCardType" 
					    				value="%{addonServicesDTO.selectedCreditCardType}"
					   					headerKey="-1"
					       				headerValue="Select One"
					     				cssStyle="width: 140px;" size="1"
					      				theme="simple"
										list="addonServicesDTO.creditCardOptions"
										listKey="value"
										listValue="label" />
							</div>
						</div>
						<div class="left last">
							<div class="clearfix" style="margin-bottom: 5px">
								<label>Expiration Date <span class="req">*</span></label> 
									<s:select 	
						    				id="addonServicesDTO.selectedMonth" 
						    				name="addonServicesDTO.selectedMonth"
						    				value="%{addonServicesDTO.selectedMonth}" 
						   					headerKey="-1"
						       				headerValue="-Month-"
						     				cssStyle="width: 100px;" size="1"
						      				theme="simple"
											list="addonServicesDTO.monthOptions"
											listKey="value"
											listValue="label" />
									<s:select 	
						    				id="addonServicesDTO.selectedYear" 
						    				name="addonServicesDTO.selectedYear"
						    				value="%{addonServicesDTO.selectedYear}"
						   					headerKey="-1"
						       				headerValue="-Year-"
						     				cssStyle="width: 80px;" size="1"
						      				theme="simple"
											list="addonServicesDTO.yearOptions"
											listKey="value"
											listValue="label" />
							</div>	
							<div class="clearfix" style="margin-bottom: 5px">
								<label>Pre Auth Number<span class="req">*</span></label>								
								<s:textfield
									id="addonServicesDTO.preAuthNumber"
									name="addonServicesDTO.preAuthNumber"
									value="%{addonServicesDTO.preAuthNumber}"
									cssClass="text" cssStyle="width: 180px" theme="simple" maxlength="7" />
							</div>
							<div class="clearfix" style="margin-bottom: 5px">
								<label>Amount Authorized<span class="req">*</span></label>
								<s:textfield 
									id="addonServicesDTO.amtAuthorized" 
									name="addonServicesDTO.amtAuthorized" 
									value="%{addonServicesDTO.amtAuthorized}"
									cssClass="text toolTipShow" theme="simple"
									onchange="addOnObject.validatePaymentAmount( this );" onblur="hideToolTip();"
								/>
							</div>
						</div>
					</div>	
<input type="hidden" class="text" id="editOrCancelCreditCard" name="addonServicesDTO.editOrCancel" value="${addonServicesDTO.editOrCancel}">	
	

	
	
<input type="hidden" class="text" id="creditCardNumberActual" name="addonServicesDTO.creditCardNumberActual" value="${addonServicesDTO.creditCardNumberActual}">  	

					
					</div>
													
				
				
			
			<div class="clearfix" style="clear: left;">
				<br/>
				<p>
					<fmt:message bundle="${serviceliveCopyBundle}" key="details.completepayment.addon.msg8" />
					<fmt:message bundle="${serviceliveCopyBundle}" key="details.completepayment.addon.msg7" />
				</p>
						</div>
						</div>
			</c:if>	
			
			
		<script type="text/javascript">
		
		jQuery(document).ready( function ($) {
			var editOrCancel=jQuery("#editOrCancelCreditCard").val();
			if($("#editLink"))
				$("#editLink").hide();
			document.getElementById('cancelLink').style.display = 'none';
			if('edit'==jQuery.trim(editOrCancel)){
				document.getElementById('editLink').style.display = 'block';
				document.getElementById('cancelLink').style.display = 'none';
				document.getElementById('addonServicesDTO.creditCardNumber').disabled='disabled';
				document.getElementById('editOrCancelCreditCard').value='edit';
			}

			else if('edited'==editOrCancel){
				document.getElementById('editLink').style.display = 'none';
				document.getElementById('cancelLink').style.display = 'block';
				document.getElementById('addonServicesDTO.creditCardNumber').disabled='';
				document.getElementById('editOrCancelCreditCard').value='edited';
			}
			else{
				
				document.getElementById('cancelLink').style.display = 'none';
				if($("#editOrCancelCreditCard"))
					$("#editOrCancelCreditCard").val('');
			}
		});	
			
				
			</script>