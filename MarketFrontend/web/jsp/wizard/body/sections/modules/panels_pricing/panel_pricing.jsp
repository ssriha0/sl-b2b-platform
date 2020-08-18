<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript">
	jQuery(document).ready(function() {

		jQuery("#increasePriceResponseMessage").hide();

		//jQuery(".priceHistory").hide();

	});

	function sumGroupSpendLimit() {
		var combinedMax = parseFloat(jQuery("#ogLaborSpendLimit").val())
				+ parseFloat(jQuery("#ogPartsSpendLimit").val())
				+ parseFloat(jQuery("#groupTotalPermits").val());
		jQuery("#groupCombinedMax").html(combinedMax.toFixed(2));
	}

	function showHistory(obj) {

		var idVal = obj.id;
		var curleft = curtop = 0;
		if (obj.offsetParent) {
			curleft = obj.offsetLeft;
			curtop = obj.offsetTop;
			while (obj = obj.offsetParent) {
				curleft += obj.offsetLeft
				curtop += obj.offsetTop
			}
		}

		jQuery("#" + idVal + "priceHistory").css('top', curtop - 290);
		jQuery("#" + idVal + "priceHistory").css('left', 450);
		jQuery("#" + idVal + "priceHistory").css('z-index', 99999);
		jQuery("#" + idVal + "priceHistory").css('display', 'block');
		jQuery("#" + idVal + "priceHistory").show();
	}
	function hidehistory(obj) {
		var idVal = obj.id;
		jQuery("#" + idVal + "priceHistory").hide();
		jQuery("#" + idVal + "priceHistory").css('display', 'none');
	}
	function fmtMoney(mnt) {
		mnt -= 0;
		mnt = (Math.round(mnt * 100)) / 100;
		var x = (mnt == Math.floor(mnt)) ? mnt + '.00' : ((mnt * 10 == Math
				.floor(mnt * 10)) ? mnt + '0' : mnt);

		if (x > 0)
			return x;
		return "0.00";
	}
	function findMaxLabor(obj) {

		if (!validatePositiveDecimal(obj.value)) {
			obj.value = "0.00";

		}

		obj.value = fmtMoney(obj.value);
		calculate();
	}
	function validatePositiveDecimal(value) {
		value = trim(value);
		// USE JAVAACRIPT REGEX
		if (value == "")
			value = "0.00";

		if (/^-?\d*(\.\d{1,2})?$/.test(value))
			if (value >= 0.00)
				return true;
		alert("Please enter a valid dollar amount");
		return false;
	}
	function trim(myString) {
		return myString.replace(/^\s+/g, '').replace(/\s+$/g, '')
<%=".replace(/\\$/g,'')"%>
	;
	}
	function calculate() {
		var totalElms = jQuery('#tasklength').val();
		var taskTotal = 0;
		var permitCustTotal = 0;
		var permitPrice = 0;
		var partsPrice = 0;
		var endCustValue = null;
		var endCustValueSell = null;
		for (i = 0; i < totalElms; i++) {

			var endCustObj = document.getElementById('tasks[' + i
					+ '].finalPrice');
			var taskType = document.getElementById('tasks[' + i + '].taskType').value;
			if (taskType == "0" || taskType == "") {
				if (endCustObj != null && endCustObj.value != null
						&& endCustObj.value != '') {
					endCustValue = endCustObj.value;
				} else {
					endCustValue = 0.0 * 1;
				}
				taskTotal = taskTotal + (1 * endCustValue);
			}
		}
		taskTotal = fmtMoney(taskTotal);
		var priceLength = taskTotal.length;
		if (priceLength > 10) {
			jQuery("#increasePriceResponseMessage").html(
					"The amount entered exceeds the maximum permitted amount");
			jQuery("#increasePriceResponseMessage").show();
		}
		jQuery("#maxLabor").html(taskTotal);
		permitPrice = fmtMoney(jQuery("#permitTotalPrice").val());
		taskTotal = (taskTotal * 1) + (1 * permitPrice);
		jQuery("#laborSpendLimit").val(fmtMoney(taskTotal));
		partsPrice = fmtMoney(jQuery("#partsSpendLimit").val());
		var partsPriceLength = partsPrice.length;
		if (partsPriceLength > 10) {
			jQuery("#increasePriceResponseMessage").html(
					"The amount entered exceeds the maximum permitted amount");
			jQuery("#increasePriceResponseMessage").show();
		}
		taskTotal = (taskTotal * 1) + (1 * partsPrice);
		jQuery("#combinedMax").html(fmtMoney(taskTotal));
	}

	function findSoTotal(obj) {
		if (!validatePositiveDecimal(obj.value)) {
			obj.value = "0.00";
			return false;
		}

		obj.value = fmtMoney(obj.value);
		calculateSoPrice();
	}

	
	function calculateSoPrice() {
	
		var laborPrice = document.getElementById("laborSpendLimit").value;
		var partsPrice = document.getElementById("partsSpendLimit").value;
		var taskTotal = (laborPrice * 1) + (partsPrice * 1);
		document.getElementById("combinedMaximum").innerHTML = fmtMoney(taskTotal);
		
		
	}
	
	
	function findSoTaxTotal(obj) {
		if (!validatePositiveDecimal(obj.value)) {
			obj.value = "0.00";
			return false;
		}

		obj.value = fmtMoney(obj.value);
		calculateSoTaxPrice();
	}

	
	function calculateSoTaxPrice() {
	
		var laborPrice = document.getElementById("actualLaborPrice").value;
		var partsPrice = document.getElementById("actualPartPrice").value;
		var laborTax = document.getElementById("laborTaxTotalUI").value;
		var partTax = document.getElementById("partTaxTotalUI").value;
		var totalTax = laborPrice*laborTax;
		var laborPriceWithTax=(totalTax*1) + (laborPrice*1);
		
		var totalPartTax = partsPrice*partTax;
		var partPriceWithTax=(totalPartTax*1) + (partsPrice*1);
		var taskTotal = (laborPriceWithTax * 1) + (partPriceWithTax * 1);
		document.getElementById("combinedMaximum").innerHTML = fmtMoney(taskTotal);
		
		document.getElementById("actualLaborWithTax").innerHTML = fmtMoney(laborPriceWithTax);
		document.getElementById("laborSpendLimit").value= fmtMoney(laborPriceWithTax);
		document.getElementById("calculatedTax").innerHTML = fmtMoney(totalTax);
		
		document.getElementById("actualPartWithTax").innerHTML = fmtMoney(partPriceWithTax);
		document.getElementById("partsSpendLimit").value= fmtMoney(partPriceWithTax);
		document.getElementById("calculatedPartTax").innerHTML = fmtMoney(totalPartTax);
		
	}
</script>
<style>
.priceHistory {
	position: absolute;
	left: 400px;
	top: 800px;
	width: 235px;
	background-color: white;
	border: 4px outset grey;
	z-index: 50;
}

.priceHistory_head {
	padding: 0px;
	font-family: arial;
	font-size: 11px;
	font-weight: bold;
}

.priceHistory_body {
	padding: 5px;
	font-family: arial;
	font-size: 10px;
	/*font-weight:bold;*/
}
</style>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />


<div dojoType="dijit.TitlePane" title="Pricing" class="contentWellPane">
	<!--	<p>
		You can price your project at a fixed or hourly rate and set spending
		limits for labor and parts (if the provider is to supply parts). See
		your ServiceLive administrator or click 'add funds' if you need to
		increase funds in your account, or to pre-fund additional projects.
	</p>
    <div class="hrText">
		Pricing Type
	</div>
	<p class="formFieldOffset">
		<s:radio list="pricingRadioOptions" name="selectedRadioPricing" id="selectedRadioPricing" cssClass="antiRadioOffsets" value="%{selectedRadioPricing}"/>
		
		<s:select 	
	    	name="selectedDropdownPricing"
	   		headerKey="-1"
	        headerValue="Select One"
	     	cssStyle="width: 100px;" size="1"
	      	theme="simple"
			list="rateOptions"
			listKey="value"
			listValue="label"
		/>
		
		
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$		
		<s:textfield
			theme="simple"
			name="hourlyRate"
			id="hourlyRate"
			cssClass="shadowBox grayText" cssStyle="width: 100px;"
			value="%{hourlyRate}" onfocus="clearTextbox(this)" 
			maxlength="15"/>      
		
		/hr
	</p>
		
-->
	<c:set var="disabled"
		value="${not empty groupOrderId and fn:length(groupOrderId)>0 }" />
	<c:set var="permitCount" value="0" />
	<c:if test="${taskLevelPrice }">
		<div class="errorBox clearfix" id="increasePriceResponseMessage"
			style="width: 400px; overflow-y: hidden;"></div>
		<b style="align: left">Service Order Pricing:</b>&nbsp; Task Level
<table width="420px" border=0>


			<tr height="28px">
				<th width="63%">Labor
				</td>
				<th width="17%">SKU
				</td>
				<th width="5%"></th>
				<th width="10%">Price<span class="req">*</span>
				</td>
				<td width="5%"></td>
			</tr>
			<c:set var="countTask" value="0" />
			<c:set var="seqNum" value="-1" />
			<c:set var="counterVar" value="0"></c:set>
			<c:forEach var="task" items="${tasks}" varStatus="rowCounter">
				<c:set var="countList" value="0" />

				<c:forEach var="history" items="${task.priceHistoryList}">
					<c:set var="countList" value="${countList+1}" />
				</c:forEach>

				<c:set var="count" value="${rowCounter.count}" />

				<c:set var="index" value="${rowCounter.count-1}" />

				<c:if test="${(task.taskType==null ||task.taskType!=1)}">
					<c:set var="countTask" value="${countTask+1}" />

					<c:if
						test="${task.sequenceNumber == 0 || task.sequenceNumber == seqNum}">
						<input type="hidden" id="tasks[${index}].finalPrice"
							name="tasks[${index}].finalPrice" value="0.00" />
					</c:if>

					<c:if
						test="${task.sequenceNumber != 0 && task.sequenceNumber != seqNum}">
						<c:set var="counterVar" value="${counterVar+1}"></c:set>
						<tr>
							<td>Task ${counterVar}: { ${task.taskName} }</td>
							<td>${task.sku}</td>
							<td>$</td>
							<td align="right"><c:if test="${task.sequenceNumber == 0}">
		0.00
		</c:if> <c:if test="${groupOrderId != null}">
									<fmt:formatNumber value="${task.finalPrice}" type="NUMBER"
										minFractionDigits="2" maxFractionDigits="2" />
								</c:if> <c:if test="${groupOrderId == null}">
									<c:if test="${task.finalPrice ==null ||task.finalPrice==''}">
										<input type="text"
											style="width: 54px; text-align: right; font-size: 10px; font-family: verdana;"
											class="text nonPermitTask" name="tasks[${index}].finalPrice"
											id="tasks[${index}].finalPrice" value="0.00"
											onchange="findMaxLabor(this);" maxlength="10" />
									</c:if>
									<c:if test="${task.finalPrice !=null && task.finalPrice!=''}">
										<input type="text" id="tasks[${index}].finalPrice"
											name="tasks[${index}].finalPrice" class="text nonPermitTask"
											style="width: 54px; text-align: right; font-size: 10px; font-family: verdana;"
											onchange="findMaxLabor(this);" maxlength="10"
											value='<fmt:formatNumber value="${task.finalPrice}" type="NUMBER"
								minFractionDigits="2" maxFractionDigits="2" />' />
									</c:if>
								</c:if>
							<td><c:if test="${countList>1}">
									<a id="${count}" onmouseover="showHistory(this)"
										onmouseout="hidehistory(this)" class="priceHistoryIcon"
										href="javascript:void(0);"><img
										src="${staticContextPath}/images/widgets/dollar.bmp" />
									</a>
								</c:if>
							</td>
						</tr>
					</c:if>
				</c:if>
				<c:if test="${task.taskType!=null && task.taskType==1}">
					<c:set var="permitCount" value="${permitCount+1}" />
				</c:if>

				<input type="hidden" name="tasks[${index}].taskType"
					id="tasks[${index}].taskType" value="${task.taskType}" />
				<c:if test="${! empty task.sequenceNumber}">
					<c:set var="seqNum" value="${task.sequenceNumber}" />
				</c:if>
			</c:forEach>

			<input type="hidden" name="tasklength" id="tasklength"
				value="${fn:length(tasks)}" />
			<tr>
				<td colspan="5">
					<hr /></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><b>Total Maximum Labor:</b>
				</td>
				<td>$</td>

				<td align="right"><label id="maxLabor"><fmt:formatNumber
							value="${laborSpendLimit-permitPrepaidPrice }" type="NUMBER"
							minFractionDigits="2" maxFractionDigits="2" />
				</label>
				<s:hidden id="laborSpendLimit" name="laborSpendLimit" />
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right"><b>Maximum Materials:</b>
				</td>
				<td>$</td>
				<td align="right"><c:if test="${groupOrderId != null}">
						<fmt:formatNumber value="${partsSpendLimit}" type="NUMBER"
							minFractionDigits="2" maxFractionDigits="2" />
					</c:if>
					<c:if test="${groupOrderId == null}">
						<input type="text"
							${disabled?"disabled":""} name="partsSpendLimit"
							id="partsSpendLimit" class="text"
							style="width: 54px; text-align: right; font-size: 10px; font-family: verdana;"
							maxlength="10" onchange="findMaxLabor(this);"
							value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.partsSpendLimit}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch>>
					</c:if>
				</td>
			</tr>
			<c:if test="${buyerId == 1000}">
				<c:if test="${permitCount>0}">
					<tr height="28px">
						<th colspan="5"><b>Permits</b>
						</td>
					</tr>
					<c:forEach var="task" items="${tasks}" varStatus="rowCounter">
						<c:set var="count" value="${rowCounter.count}" />
						<c:set var="index" value="${rowCounter.count-1}" />
						<c:if test="${(task.taskType==null ||task.taskType==1)&&(task.taskStatus=='ACTIVE')}">
							<tr>
								<td>Permit (Customer Pre-paid)</td>
								<td>99888</td>
								<td>$</td>
								<td align="right"><fmt:formatNumber
										value="${task.finalPrice}" type="NUMBER" minFractionDigits="2"
										maxFractionDigits="2" />
								</td>
								<td></td>
							</tr>
						</c:if>

					</c:forEach>
					<tr>
						<td colspan="5">
							<hr /></td>
					</tr>
				</c:if>
				<tr>

					<td colspan="2" align="right"><b>Total Permits </b>(Customer
						Pre-paid):</td>
					<td>$</td>
					<td align="right"><label id="permitTotals"><fmt:formatNumber
								value="${permitPrepaidPrice }" type="NUMBER"
								minFractionDigits="2" maxFractionDigits="2" />
					</label><input type="hidden" id="permitTotalPrice" name="permitTotalPrice"
						value="${permitPrepaidPrice }" />
					</td>
					<td></td>
				</tr>

			</c:if>
			<tr>
				<td colspan="5">
					<hr /></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><b>Combined Maximum: </b>
				</td>
				<td>$</td>
				<td align="right"><label id="combinedMax"><fmt:formatNumber
							value="${laborSpendLimit + partsSpendLimit}" type="NUMBER"
							minFractionDigits="2" maxFractionDigits="2" />
				</label>
				</td>
				<td></td>
			</tr>
			<c:if test="${showBillingEstimate}">
				<tags:fieldError id="Billing Estimate" oldClass="">
					<td colspan="2" align="right"><label> Billing Estimate
					</label>
					</td>

					<td>$</td>
					<td><input type="text" name="billingEstimate"
						id="billingEstimate" class="shadowBox grayText"
						style="width: 35px;" maxlength="10"
						value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.billingEstimate}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch>>
						</input>
					</td>
					<td></td>
				</tags:fieldError>
			</c:if>
		</table>

		<c:if test="${groupOrderId != null}">
			<div
				style="width: 630px; margin-top: 5px; background: #ffc url(../images/icons/conditional_exclamation.gif) no-repeat 3px 6px; border: solid 1px #fc6; padding: 5px 5px 5px 20px; font-weight: bold; position: relative; width: 630px; color: black">
				The new Group Maximum for Labor will be proportionally distributed
				at task level for all orders in the group.<br> And, any changes
				you make to this order will affect all orders in the group.
			</div>

			<table width="420px" border=0>
				<tr>
					<td colspan="5" align="right"><b>Enter a Group Maximum
							Price* </b>
					</td>

				</tr>

				<tr>
					<tags:fieldError id="Maximum Price for Labor" oldClass="">
						<td colspan="2" align="right"><label style="color: black">
								Group Maximum Price for Labor: </label>
						</td>
						<td>$</td>
						<td><input type="text" name="ogLaborSpendLimit"
							id="ogLaborSpendLimit" class="shadowBox grayText" size="6"
							style="text-align: right" maxlength="10"
							onchange="sumGroupSpendLimit()"
							value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.ogLaborSpendLimit}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch>>
							</input>
						</td>
						<td></td>

					</tags:fieldError>
				</tr>
				<tr>
					<td colspan="2" align="right"><c:if
							test="${showPartsSpendLimit}">
							<tags:fieldError id="Maximum Price for Parts" oldClass="">
								<label style="color: black"> Group Maximum Price for
									Parts: </label>
							</tags:fieldError>
						</c:if>
					</td>
					<td>$</td>
					<td><input type="text" name="ogPartsSpendLimit"
						id="ogPartsSpendLimit" size="6" onchange="sumGroupSpendLimit()"
						class="shadowBox grayText"
						style="text-align: right; font-size: 10px; font-family: verdana;"
						maxlength="10"
						value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.ogPartsSpendLimit}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch>>
						</input>
					</td>
					<td></td>



				</tr>
				<tr>
					<td colspan="2" align="right"><tags:fieldError
							id="Maximum Price for Labor" oldClass="">
							<label style="color: black"> Group Permits (Customer
								Pre-paid): </label>
						</tags:fieldError>
					</td>
					<td>$</td>
					<td><fmt:formatNumber
							value='${sowPricingTabDTO.groupTotalPermits}' type='NUMBER'
							minFractionDigits='2' maxFractionDigits='2' /> <input
						type="hidden" id="groupTotalPermits"
						value="${sowPricingTabDTO.groupTotalPermits}" />
					</td>
					<td></td>

				</tr>

				<tr>
					<td colspan="5">
						<hr /></td>
				</tr>

				<tr>
					<td colspan="2" align="right"><tags:fieldError
							id="Maximum Price for Labor" oldClass="">
							<label style="color: black"> Group Combined Maximum: </label>
						</tags:fieldError>
					</td>
					<td>$</td>
					<td><span id="groupCombinedMax"> <fmt:formatNumber
								value='${sowPricingTabDTO.ogPartsSpendLimit+sowPricingTabDTO.ogLaborSpendLimit+sowPricingTabDTO.groupTotalPermits}'
								type='NUMBER' minFractionDigits='2' maxFractionDigits='2' /> </span>
					</td>
					<td></td>



				</tr>

			</table>
		</c:if>
		<c:set var="countTask" value="0" />
		<c:set var="seqNum" value="-1" />
		<c:forEach var="task" items="${tasks}" varStatus="rowCounter">
			<c:set var="count" value="${rowCounter.count}" />
			<c:set var="cntList" value="0" />
			<c:forEach var="history" items="${task.priceHistoryList}">
				<c:set var="cntList" value="${cntList+1}" />
			</c:forEach>
			<c:if test="${(task.taskType==null ||task.taskType!=1)}">
				<c:if
					test="${task.sequenceNumber != 0 && task.sequenceNumber != seqNum}">
					<c:set var="countTask" value="${countTask+1}" />
				</c:if>
			</c:if>
			<c:if test="${cntList>1}">
				<c:forEach var="history" items="${task.priceHistoryList}">
					<div id="${count}priceHistory" class="priceHistory">
						<table cellspacing="0">

							<div class="priceHistory_head" style="float: left;">
								<th colspan="3">Price History for Task ${countTask}</th>
								<tr>
									<th width="25%" style="border-bottom: 1px solid #ccc;">Price
									</th>
									<th width="35%" style="border-bottom: 1px solid #ccc;">Date</th>
									<th width="40%" style="border-bottom: 1px solid #ccc;">
										Changed By</th>
								</tr>
							</div>

							<div class="priceHistory_body" align="left">
								<c:set var="cntHist" value="0" />
								<c:forEach var="history" items="${task.priceHistoryList}">
									<c:set var="cntHist" value="${cntHist+1}" />
									<tr>

										<td width="25%">$<fmt:formatNumber
												value='${history.price}' type='NUMBER' minFractionDigits='2'
												maxFractionDigits='2' /></td>

										<td width="35%">${history.modifiedDate}</td>

										<td width="40%">${history.modifiedByName} <c:if
												test="${history.modifiedByName!='SYSTEM'}">
    		(ID#${history.modifiedBy})
    		</c:if>
										</td>
									</tr>


								</c:forEach>
							</div>

							</tr>
						</table>
					</div>
				</c:forEach>
			</c:if>
			<c:if test="${! empty task.sequenceNumber}">
				<c:set var="seqNum" value="${task.sequenceNumber}" />
			</c:if>
		</c:forEach>
	</c:if>
	<!--Done as a part of JIRA SL-19728 Non Funded Buyer -->
	<c:if test="${!taskLevelPrice }">

		<c:if test="${sowPricingTabDTO.nonFundedInd==false}">
			<div style="margin-left: 25%">
				Enter a Maximum Price <span class="req">*</span>
			</div>
		</c:if>

		<table width="300px" border=0>
			<tags:fieldError id="Maximum Price for Labor" oldClass="">
			
			
			<c:choose>
			<c:when test="${sowPricingTabDTO.nonFundedInd==false && (buyerId == 3333 || buyerId == 7777) }">
				<tr style="text-align: right">
					<td>Maximum Labor Without Tax:</td>
					<td width="40px">$</td>
					<td><input type="text"
									${disabled?"disabled":""} onchange="findSoTaxTotal(this);"
									name="actualLaborPrice" id="actualLaborPrice"
									style="height: 15px; width: 40px; font-size: 10px; font-family: verdana; text-align: right;"
									class="text" maxlength="10"
									value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.laborSpendLimit / (1 + sowPricingTabDTO.laborTaxPercentage)}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch>>
					</td>				
							
				</tr>
				<c:set var="laborTaxTotalUI" value="0.0000"></c:set>
				<tr style="text-align: right">	
								<td>Labor Tax: (<fmt:formatNumber value="${sowPricingTabDTO.laborTaxPercentage * 100}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>%)</td>
								<td width="40px">$</td>
									<td><label id="calculatedTax"><fmt:formatNumber value="${(sowPricingTabDTO.laborSpendLimit * sowPricingTabDTO.laborTaxPercentage) / (1 + sowPricingTabDTO.laborTaxPercentage)}" type="NUMBER"
										minFractionDigits="2" maxFractionDigits="2" />
									</label>	
									
									<input type="hidden" name="laborTaxTotalUI" id="laborTaxTotalUI"
										value='<fmt:formatNumber value="${sowPricingTabDTO.laborTaxPercentage}" type="NUMBER" minFractionDigits="4" maxFractionDigits="4" />' />		
									 	
								</td>
								
				</tr>
							
				
				<tr style="text-align: right">
					<td>Maximum Labor:</td>
					<td width="40px">$</td>
					<td><label id="actualLaborWithTax">
					<fmt:formatNumber value='${sowPricingTabDTO.laborSpendLimit}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/>
					</label>
									<input type="hidden"
									value='<fmt:formatNumber value="${sowPricingTabDTO.laborSpendLimit}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>'
									name="laborSpendLimit" id="laborSpendLimit"
									style="height: 15px; width: 40px; font-size: 10px; font-family: verdana; text-align: right;"
									class="text" maxlength="10"
									/>
					</td>
				</tr>
				</c:when>
				
				<c:when test="${sowPricingTabDTO.nonFundedInd==false}">
						<tr style="text-align: right">
							<td>Maximum Labor:</td>
							<td width="40px">$</td>
							<td>
								<input type="text"
									${disabled?"disabled":""} onchange="findSoTotal(this);"
									name="laborSpendLimit" id="laborSpendLimit"
									style="height: 15px; width: 40px; font-size: 10px; font-family: verdana; text-align: right;"
									class="text" maxlength="10"
									value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.laborSpendLimit}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch>>
										</td>
						</tr>
				</c:when>
				<c:otherwise>
						<tr style="text-align: right">
							<td>Maximum Labor:</td>
							<td width="40px">$</td>
							<td>
								<input type="text" disabled="disabled" value="0.00"
									onchange="findSoTotal(this);" name="laborSpendLimit"
									id="laborSpendLimit"
									style="height: 15px; width: 40px; font-size: 10px; font-family: verdana; text-align: right;"
									class="text" maxlength="10"<%-- value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.laborSpendLimit}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch> --%>>
							</td>
						</tr>
				</c:otherwise>
				</c:choose>
				
			</tags:fieldError>
			<c:if test="${showPartsSpendLimit}">
				<tags:fieldError id="Maximum Price for Parts" oldClass="">
				<c:choose>
					<c:when test="${sowPricingTabDTO.nonFundedInd==false && (buyerId == 3333 || buyerId == 7777) }">
				<tr style="text-align: right">
					<td>Maximum Materials Without Tax:</td>
					<td width="40px">$</td>
					<td><input type="text"
									${disabled?"disabled":""} onchange="findSoTaxTotal(this);"
									name="actualPartPrice" id="actualPartPrice"
									style="height: 15px; width: 40px; font-size: 10px; font-family: verdana; text-align: right;"
									class="text" maxlength="10"
									value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.partsSpendLimit / (1 + sowPricingTabDTO.partsTax)}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch>>
					</td>				
							
				</tr>
				<c:set var="partTaxTotalUI" value="0.0000"></c:set>
				<tr style="text-align: right">	
						<td>Materials Tax: (<fmt:formatNumber value="${sowPricingTabDTO.partsTax * 100}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>%)</td>
								<td width="40px">$</td>
									<td><label id="calculatedPartTax"><fmt:formatNumber value="${(sowPricingTabDTO.partsSpendLimit * sowPricingTabDTO.partsTax) / (1 + sowPricingTabDTO.partsTax)}" type="NUMBER"
										minFractionDigits="2" maxFractionDigits="2" />
									</label>	
									
									<input type="hidden" name="partTaxTotalUI" id="partTaxTotalUI"
										value='<fmt:formatNumber value="${sowPricingTabDTO.partsTax}" type="NUMBER" minFractionDigits="4"		
						maxFractionDigits="4" />' />
						</td>
								
				</tr>
							
				
						<tr style="text-align: right">
								<td>Maximum Materials:</td>
								<td width="40px">$</td>
								<td><label id="actualPartWithTax">
								<fmt:formatNumber value='${sowPricingTabDTO.partsSpendLimit}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/>
								</label>
									<input type="hidden"
									
									name="partsSpendLimit" id="partsSpendLimit"
									style="height: 15px; width: 40px; font-size: 10px; font-family: verdana; text-align: right;"
									class="text" maxlength="10"
									/>
								</td>
						</tr>
				</c:when>
						
						
						<c:when test="${sowPricingTabDTO.nonFundedInd==false}">
									<tr style="text-align: right">
									<td>Maximum Materials:</td>
									<td width="40px">+&nbsp;&nbsp;&nbsp;$</td>
									<td>
									<input type="text"
										${disabled?"disabled":""} onchange="findSoTotal(this);"
										name="partsSpendLimit" id="partsSpendLimit"
										style="height: 15px; width: 40px; font-size: 10px; font-family: verdana; text-align: right;"
										class="text" maxlength="10"
										value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.partsSpendLimit}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch>>
									</td>
								</tr>	
						</c:when>
				<c:otherwise>
								
									<tr style="text-align: right">
									<td>Maximum Materials:</td>
									<td width="40px">+&nbsp;&nbsp;&nbsp;$</td>
									<td>
									<input type="text"
										<%-- ${disabled?"disabled":""} --%> onchange="findSoTotal(this);"
										name="partsSpendLimit" id="partsSpendLimit"
										style="height: 15px; width: 40px; font-size: 10px; font-family: verdana; text-align: right;"
										class="text" maxlength="10" disabled="disabled" value="0.00"<%-- value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.partsSpendLimit}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch> --%>>
									</td>
									</tr>
						</c:otherwise>
					</c:choose>
						
				</tags:fieldError>
			</c:if>
			
			<tr>
				<td colspan="3">
					<hr />
				</td>
			</tr>
			<tr style="text-align: right">
				<td>Combined Maximum:</td>
				<td width="40px">$</td>
				<td><label id="combinedMaximum"> <c:choose>
							<c:when test="${sowPricingTabDTO.nonFundedInd==false}">
								<fmt:formatNumber
									value='${sowPricingTabDTO.laborSpendLimit+sowPricingTabDTO.partsSpendLimit}'
									type='NUMBER' minFractionDigits='2' maxFractionDigits='2' />
							</c:when>
							<c:otherwise>
								<fmt:formatNumber value='0.00' type='NUMBER'
									minFractionDigits='2' maxFractionDigits='2' />
							</c:otherwise>
						</c:choose> </label></td>
			</tr>
			<c:if test="${showBillingEstimate}">
				<tags:fieldError id="Billing Estimate" oldClass="">
					<tr style="text-align: right">
						<td>Billing Estimate:</td>
						<td width="40px">$</td>
						<td><input type="text" name="billingEstimate"
							id="billingEstimate"
							style="height: 15px; width: 40px; font-size: 10px; font-family: verdana; text-align: right;"
							class="text" maxlength="10"
							value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.billingEstimate}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch>>
						</td>
					</tr>
				</tags:fieldError>
			</c:if>


			<c:if test="${groupOrderId != null}">
				<tr>
					<td>
						<div style="color: red">Please note that any changes you
							make to this order will affect all orders in the group.</div>
					</td>
				</tr>
				<tr width="150px">
					<td><tags:fieldError id="Maximum Price for Labor" oldClass="">
							<label style="width: 100%"> Group Maximum Price for Labor
							</label>
							<br>
					$ <input type="text" name="ogLaborSpendLimit"
								id="ogLaborSpendLimit" class="shadowBox grayText"
								style="width: 100px;" maxlength="10"
								value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.ogLaborSpendLimit}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch>>
							</input>
							<br>
						</tags:fieldError> <c:if test="${showPartsSpendLimit}">
							<tags:fieldError id="Maximum Price for Parts" oldClass="">
								<label> Group Maximum Price for Parts </label>
								<br>
					$ <input type="text" name="ogPartsSpendLimit"
									id="ogPartsSpendLimit" class="shadowBox grayText"
									style="width: 100px;" maxlength="10"
									value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.ogPartsSpendLimit}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch>>
								</input>
								<br>
							</tags:fieldError>
						</c:if></td>
				</tr>
			</c:if>

		</table>
	</c:if>
</div>
