<%@ page import="java.util.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"pageEncoding="ISO-8859-1"%> --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>

<style type="text/css">
.soDetailsHeader {
	float: left;
	width: 170px;
	height: 45px;
	margin: 10px;
	border-right: 1px solid;
}

.soEstimateBody {
	float: left;
	width: 800px;
	height: 250px;
	background: #f9f9f9;
}

.textColor {
	color: gray;
}

.displayHeader {
	height: 40px;
	background: whitesmoke;
	padding-top: 11px;
}

.addButton {
	background: #58585A;
	width: 76px;
	height: 31px;
	color: white;
	margin-top: 14px;
}
.fromSODesc{
  height: 25px;
}
.fromSODButton{
 cursor: pointer;
}

.fromSODFrom{
	width: 832px;
	margin-left: -6px;
}
.estimateButton {
	border: 1px solid;
	border-radius: 25px;
	padding: 6px 32px;
	background: white;
}

.laborPartItem {
	padding-right: 54px;
}

.tooltipCustom {
    position: relative;
    display: inline-block;
    border-bottom: 1px solid black;
}

.tooltipCustom .tooltipCustomtext {
    visibility: hidden;
    width: 120px;
    background-color: gray;
    color: black;
    text-align: center;
    border-radius: 6px;
    padding: 5px 0;
    position: absolute;
    z-index: 1;
    bottom: 125%;
    left: 50%;
    margin-left: -60px;
    opacity: 0;
    transition: opacity 1s;
}

.tooltipCustom .tooltipCustomtext::after {
    content: "";
    position: absolute;
    top: 100%;
    left: 50%;
    margin-left: -5px;
    border-width: 5px;
    border-style: solid;
    border-color: #555 transparent transparent transparent;
}

.tooltipCustom:hover .tooltipCustomtext {
    visibility: visible;
    opacity: 1;
}

</style>
<script type="text/javascript">
	/*
	 S.no:
	 Name:
	 Description:
	 Price:
	 Quantity:
	 Total:
	 Additional Comments:
	 finalTotalLaborPrice
	 finalTotalPartsPrice
	 }
	 */
	 
	var numbersOnly = /^[0-9]{0,5}(\.[0-9]?[0-9]?)?$/;
	var alphanumeric = /^([a-zA-Z0-9]+)$/;
	var numOnly = /^[0-9]{1,2}$/;
	
	function discountValidation(field){	
		var data = document.getElementById(field).value;
		var numb = Number(data);	
		if(!isNaN(numb)) {	
			if (numb >= 0 && numb < 100.01) {			
				$("#discountValue").val(data);
                $('#saveEstimate').removeAttr('disabled');
                getFinalEstimationTotalPrice();
			} else {		
                $("#discountValue").val("");
				document.getElementById("div4").innerHTML = "Discount should be less than 100.00";
				document.getElementById("div4").style.color = "Red";
               
                $('#saveEstimate').prop('disabled', 'disabled');
                //getFinalEstimationTotalPrice() ;
				return false;
			}	
			document.getElementById("div4").innerHTML = "";				
			return true; 
		} else {
            $("#discountValue").val("");
			document.getElementById("div4").innerHTML = "Discount should be number";
			document.getElementById("div4").style.color = "Red";
            //getFinalEstimationTotalPrice() ;
            $('#saveEstimate').prop('disabled', 'disabled');
			return false;
		}
	}
	
	function taxValidation(field){
		var data = document.getElementById(field).value;
		var numb = Number(data);	
		if(!isNaN(numb)) {
			if (numb >= 0 && numb < 99.01) {			
				$("#taxRate").val(data);
                $('#saveEstimate').removeAttr('disabled');
                getFinalEstimationTotalPrice();
			} else {		
                $("#taxRate").val("");
				document.getElementById("div5").innerHTML = "Tax should be less than 99.00";
				document.getElementById("div5").style.color = "Red";
                //getFinalEstimationTotalPrice();
                $('#saveEstimate').prop('disabled', 'disabled');
				return false;
			}	
			document.getElementById("div5").innerHTML = "";
			return true;
		} else {
			$("#taxRate").val("");
			document.getElementById("div5").innerHTML = "Tax should be number";
			document.getElementById("div5").style.color = "Red";
            //getFinalEstimationTotalPrice();
            $('#saveEstimate').prop('disabled', 'disabled');
			return false;
		}		
	}
	function nameValidation(field){
		var data = document.getElementById(field).value;		
		if(field == 'laborItemName' && data == '') {
			document.getElementById("div1").innerHTML = "Please enter name";
			document.getElementById("div1").style.color = "Red";
			return false;
		}else if(field == 'partItemName' && data == ''){
			document.getElementById("div2").innerHTML = "Please enter name";
			document.getElementById("div2").style.color = "Red";
			return false;
		}
		return true;			
	}
	
	function hourValidation(field, restrictionType){	
		var data = document.getElementById(field).value;
		
		if (data !== '') {
			if (restrictionType.test(data)) {
				$("#laborItemQty").val(data);
			} else {
				$("#laborItemQty").val("");
				document.getElementById("div1").innerHTML = "Hours should be numeric";
				document.getElementById("div1").style.color = "Red";
				return false;
			}
		} else {
			document.getElementById("div1").innerHTML = "Please enter Hours";
			document.getElementById("div1").style.color = "Red";
			return false;
		}	
		return true;
	}
	
	
	function quantityValidation(field, restrictionType){	
		var data = document.getElementById(field).value;		
		
		if (data !== '') {
			if (restrictionType.test(data)) {
				$("#partItemQty").val(data);
			} else {
				$("#partItemQty").val("");
				document.getElementById("div2").innerHTML = "Quantity should be numeric";
				document.getElementById("div2").style.color = "Red";
				return false;
			}
		} else {
			document.getElementById("div2").innerHTML = "Please enter quantity";
			document.getElementById("div2").style.color = "Red";
			return false;
		}
		return true;			
	}
	
	function priceValidation(field, restrictionType) {
		var data = document.getElementById(field).value;
		document.getElementById("div1").innerHTML = "";
		document.getElementById("div2").innerHTML = "";
		if(field == 'laborItemPrice'){
			if (data !== '') {
				if (restrictionType.test(data)) {
					if (data > 500) {
						var value = confirm("Are you sure the price is " + data
								+ "?");
						if (value == true) {
							$("#laborItemPrice").val(data);
						} else {
							$("#laborItemPrice").val("");
							return false;
						}
					}
					//document.getElementById("div1").innerHTML = "";
				} else {
					//SL-21840
					if(isNaN(data)){
						document.getElementById("div1").innerHTML = "Price should be numeric";
					}else{
						document.getElementById("div1").innerHTML = "Price should not be more than $99999.99";
					}
					
					document.getElementById("div1").style.color = "Red";
					return false;
				}
			} else {
				document.getElementById("div1").innerHTML = "Please enter price";
				document.getElementById("div1").style.color = "Red";
				return false;
			}
		} else if(field == 'partItemPrice'){
			if (data !== '') {
				if (restrictionType.test(data)) {
					if (data > 500) {
						var value = confirm("Are you sure the price is " + data
								+ "?");
						if (value == true) {
							$("#partItemPrice").val(data);
						} else {
							$("#partItemPrice").val("");
							return false;
						}
					}
					//document.getElementById("div1").innerHTML = "";
				} else {
					//SL-21840
					if(isNaN(data)){
						document.getElementById("div2").innerHTML = "Price should be numeric";
					}else{
						document.getElementById("div2").innerHTML = "Price should not be more than $99999.99";
					}
					document.getElementById("div2").style.color = "Red";
					return false;
				}
			} else {
				document.getElementById("div2").innerHTML = "Please enter price";
				document.getElementById("div2").style.color = "Red";
				return false;
			}
		}
		return true;
	}
	
	function numberValidation(field, restrictionType){	
		var data = document.getElementById(field).value;
		
		if (data !== '') {
			if (restrictionType.test(data)) {
				$("#partNumc").val(data);
			} else {
				$("#partNumc").val("");
				document.getElementById("div2").innerHTML = "Number should be alphanumeric";
				document.getElementById("div2").style.color = "Red";
				return false;
			}
		} else {
			document.getElementById("div2").innerHTML = "Please enter Number";
			document.getElementById("div2").style.color = "Red";
			return false;
		}	
		return true;
	}
	
	function referenceValidation(field, restrictionType){	
		var data = document.getElementById(field).value;	
		document.getElementById("div3").innerHTML = "";
		if (data !== '') {
			if (restrictionType.test(data)) {
				$("#refID").val(data);
                $('#saveEstimate').removeAttr('disabled');
			} else {
				$("#refID").val("");
				document.getElementById("div3").innerHTML = "Reference Number should be alphanumeric";
				document.getElementById("div3").style.color = "Red";
                $('#saveEstimate').prop('disabled', 'disabled');
				return false;
			}
		} else {
			document.getElementById("div3").innerHTML = "Please enter Reference Number";
			document.getElementById("div3").style.color = "Red";
            $('#saveEstimate').prop('disabled', 'disabled');
			return false;
		}	
		return true;
	}
	
	
	function checkChecBox(){
    	console.log("INSIDE discount");
		 if($("#discountCheck").is(':checked')){
				$("#discountTable").css("display","block");				
                document.getElementById("div4").innerHTML = "";
			}else{
            	$('#saveEstimate').removeAttr('disabled');
				$("#discountTable").css("display","none");
				$("#discountValue").val(0);
				$("#discountedAmount").val(0);
				$("#discountType").val("");
				$("#discountedPercentage").val(0);
				if($("#discountType").val()){
                    //console.log("dis .....");
                    //console.log(($("#discountType").val()));
				}
                //console.log();
				$("#discPercentage").text("");
				$("#discAmount").text("");
                document.getElementById("div4").innerHTML = "";
				getFinalEstimationTotalPrice();			
			}
	 }  
	 
	function checkChecBoxTax() {
    	console.log("INSIDE tax");
		if($("#taxCheck").is(':checked')) {
			$("#showTax").css("display","block");
            document.getElementById("div5").innerHTML = "";
		}else {
        	$('#saveEstimate').removeAttr('disabled');
			$("#showTax").css("display","none");
			$("#taxRate").val(0);
			$("#taxPercentageAndValue").text("");
            document.getElementById("div5").innerHTML = "";
			getFinalEstimationTotalPrice();
		}
	}

	
	$(document).ready(
			function() {
				var qty = $("#laborItemQty");
				var price = $("#laborItemPrice");
				qty.keyup(function() {
					var total = isNaN(parseFloat(qty.val()
							* $("#laborItemPrice").val())) ? 0
							:parseFloat( (qty.val() * $("#laborItemPrice").val())).toFixed(2);
					$("#labortotalPrice").val(total);
				});

				price.keyup(function() {
					var total = isNaN(parseFloat(qty.val()
							* $("#laborItemPrice").val()).toFixed(2)) ? 0
							:parseFloat( (qty.val() * $("#laborItemPrice").val())).toFixed(2);
					$("#labortotalPrice").val(total);
				});
               
                $("#laborItemPrice").keyup(function(){
                    var number = ($(this).val().split('.'));
                    if (number[1] && number[1].length > 2)                {
                        var price = $("#laborItemPrice").val();                      
                        var decimalIndex = price.indexOf(".");
                        var priceBeforedecimal= price.substring(0, decimalIndex);
                        var priceAfterdecimal =price.substring(decimalIndex+1, decimalIndex+3);
                       
                        $("#laborItemPrice").val(priceBeforedecimal+"."+priceAfterdecimal);                                                                 
                    }
                  });
                               
                $("#partItemPrice").keyup(function(){
                    var number = ($(this).val().split('.'));
                    if (number[1] && number[1].length > 2){
                        var price = $("#partItemPrice").val();
                        var decimalIndex = price.indexOf(".");
                        var priceBeforedecimal= price.substring(0, decimalIndex);
                        var priceAfterdecimal =price.substring(decimalIndex+1, decimalIndex+3);                       
                        $("#partItemPrice").val(priceBeforedecimal+"."+priceAfterdecimal);                                                                      
                    }
                  });                                                           
			});

	$(document).ready(
			function() {
				var qty = $("#partItemQty");
				var price = $("#partItemPrice");
				qty.keyup(function() {
					var total = isNaN(parseFloat(qty.val()
							* $("#partItemPrice").val()).toFixed(2)) ? 0 : (qty.val() * $(
							"#partItemPrice").val())
					$("#partItemTotalPrice").val(total);
				});

				price.keyup(function() {
					var total = isNaN(parseFloat(qty.val()
							* $("#partItemPrice").val()).toFixed(2)) ? 0 : (qty.val() * $(
							"#partItemPrice").val())
					$("#partItemTotalPrice").val(total);
				});
			});

	$(document).ready(
			function() {
				var qty = $("#otherItemQty");
				var price = $("#otherItemPrice");
				
				
				
				if($("#discountedAmount").val() && $("#discountedPercentage").val() && $("#discountType").val()){

					$('#discountCheck').prop('checked', 'checked');
					checkChecBox();
					if($("#discountType").val()==='PERCENTAGE'){
						$("#discountValue").val($("#discountedPercentage").val());	
					}
					if($("#discountType").val()==='AMOUNT'){
						$("#discountValue").val($("#discountedAmount").val());
					}
					
					$("#discountDropDown").val($("#discountType").val());
					 
					//calCulateDiscount();
					//getFinalEstimationTotalPrice();
					 
					}
					if($("#taxRate").val() == '' || isNAN($("#taxRate").val())){
						$("#taxRate").val(0.00);
					}
					$('#taxCheck').prop('checked', 'checked');
					checkChecBoxTax();
					getFinalEstimationTotalPrice();

			
				qty.keyup(function() {
					var total = isNaN(parseFloat(qty.val()
							* $("#otherItemPrice").val()).toFixed(2)) ? 0
							: (qty.val() * $("#otherItemPrice").val())
					$("#otherItemTotalPrice").val(total);
				});
				price.keyup(function() {
					var total = isNaN(parseFloat(qty.val()
							* $("#otherItemPrice").val()).toFixed(2)) ? 0
							: (qty.val() * $("#otherItemPrice").val())
					$("#otherItemTotalPrice").val(total);
				});
			});

	function calCulateDiscount() {
		if ($("#discountCheck").is(':checked')) {
			// getFinalEstimationTotalPrice();
			
			var discountValue;
			
			var taxRate;
			if(isNaN(parseFloat($("#discountValue").val()))){
				discountValue=0;	
			}else{
			 discountValue = parseFloat($("#discountValue").val()).toFixed(2);
				
			}
			
			var totalEstimationPrice = parseFloat($("#totalEstimationPrice").val()).toFixed(2);
			var total = totalEstimationPrice;
			var discountedAmount = 0;
			var selectedVal = $("#discountDropDown option:selected").val();
			if (selectedVal === 'PERCENTAGE' && !isNaN(totalEstimationPrice)&& !isNaN(discountValue)) {
				$("#discPercentage").text(discountValue + "%:");
				$("#discountType").val("PERCENTAGE");
				totalEstimationPrice = totalEstimationPrice - totalEstimationPrice * ((discountValue) / 100);
				discountedAmount = total - totalEstimationPrice;
				$("#discAmount").text("-$" + discountedAmount.toFixed(2));
				$("#discountedAmount").val(discountedAmount);
				$("#discountedPercentage").val(discountValue);
				$("#totalEstimationPrice").val(totalEstimationPrice.toFixed(2));

			} else if (selectedVal === 'AMOUNT' && !isNaN(totalEstimationPrice)
					&& !isNaN(discountValue)) {
                //console.log("executing anount part");
				$("#discPercentage").text(discountValue);
				$("#discAmount").text("-$" + discountValue);
				$("#discountType").val("AMOUNT");
				$("#discountedAmount").val(discountValue);
				$("#discountedPercentage").val(
						(discountValue / totalEstimationPrice) * 100);

				totalEstimationPrice = totalEstimationPrice - discountValue;
				$("#totalEstimationPrice").val(totalEstimationPrice.toFixed(2));
			}

		}
	}

	function calculateTax() {
		// getFinalEstimationTotalPrice();
		// calCulateDiscount();
		var taxRate;
		if(isNaN(parseFloat($("#taxRate").val()))){
			taxRate=0;	
		}else{
			 taxRate = parseFloat($("#taxRate").val());
			
		}
		var totalEstimationPrice = parseFloat($("#totalEstimationPrice").val());
		var total = totalEstimationPrice;
		var tax = 0;
		if (!isNaN(totalEstimationPrice) && !isNaN(taxRate)
				&& $("#taxCheck").is(':checked')) {
			totalEstimationPrice = totalEstimationPrice + totalEstimationPrice
					* (taxRate) / 100;
            //console.log("taxtotal" + totalEstimationPrice);
			tax = totalEstimationPrice - total;

			$("#taxPrice").val(tax.toFixed(2));
			$("#totalEstimationPrice").val(totalEstimationPrice.toFixed(2));
			$("#taxPercentageAndValue").text(taxRate + "% : " + tax.toFixed(2));
		}

	}
	
		
	function getFinalTotalByItem(targetTable) {

		var table = document.getElementById(targetTable);
		var rowCount = table.rows.length;
		var finalTotal = 0;

		if (targetTable === 'laborItemTable') {

			for (var i = 0; i < rowCount - 1; i++) {

				var total = parseFloat($("#totallbrPrice" + i).val());
                //console.log("total" + total);
				finalTotal = finalTotal + total;
			}
			$('#finalTotalLaborPrice').val(finalTotal);
		} 
		else if (targetTable === 'partItemTable') {

			for (var i = 0; i < rowCount - 1; i++) {

				var total = parseFloat($("#totalPartsPrice" + i).val());
                //console.log("total" + total);
				finalTotal = finalTotal + total;
			}
			$('#finalTotalPartsPrice').val(finalTotal);
		}

		// getFinalEstimationTotalPrice();

	}
	
	
	function getFinalEstimationTotalPrice() {
		// 1. getFinalTotalByItem
		getFinalTotalByItem('laborItemTable');
		getFinalTotalByItem('partItemTable');
		
		// 1.1 total
		var total = 0;
		if (!isNaN(parseFloat($("#finalTotalPartsPrice").val()).toFixed(2))) {
			total = parseFloat(total) + parseFloat($("#finalTotalPartsPrice").val());
		}

		if (!isNaN(parseFloat($("#finalTotalLaborPrice").val()).toFixed(2))) {
			total = parseFloat(total) + parseFloat($("#finalTotalLaborPrice").val());
		}

		if (!isNaN(parseFloat($("#otherItemTotalPrice0").val()).toFixed(2))) {
			total = total + parseFloat($("#otherItemTotalPrice0").val());
			$("#totalOtherServicePrice").val(
					parseFloat($("#otherItemTotalPrice0").val()));
		}

		$("#totalEstimationPrice").val(total.toFixed(2));
		
		// 2. Discount
		calCulateDiscount();
		
		// 3. Tax
		calculateTax();
	}
	
	function addItems(targetTable) {
		var table = document.getElementById(targetTable);
		var rowCount;
		var row;
		var index = $("#index").val();	

		 $("#laborItemAdd").show();
		 $("#partItemAdd").show();
		 
		if (targetTable === 'laborItemTable') {
			if (nameValidation('laborItemName') != true){
				return
			};
			if (priceValidation('laborItemPrice', numbersOnly) != true){
				return
			};
			if (hourValidation('laborItemQty',numOnly) != true){
				return
			};
			document.getElementById("div1").innerHTML = "";			
			if (index !== "null") {
                //console.log("index val");
                //console.log(index);
				row = table.insertRow(parseInt(index));
				row.style.outline = "thin solid";
				row.style.height = "30px";
				rowCount = parseInt(index);
				$("#index").val("null");
			} else {
				rowCount = table.rows.length;
				row = table.insertRow(rowCount); //to insert blank row
				row.style.outline = "thin solid";
				row.style.height = "30px";
			}
			document.getElementById(targetTable).style.display = "block";
			var cell1 = row.insertCell(0); //to insert first column
			var snoCol = document.createElement("input");
			snoCol.type = "text";
			snoCol.name = "estimateVO.estimateTasks[" + (rowCount - 1) + "].taskSeqNumber";
			snoCol.value = rowCount;
			snoCol.readOnly = true;
			snoCol.style.width = "40px";
			snoCol.style.border = "none";
			cell1.appendChild(snoCol);

			var cell2 = row.insertCell(1); //to insert second column
			var nameCol = document.createElement("input");
			nameCol.type = "text";
			nameCol.name = "estimateVO.estimateTasks[" + (rowCount - 1)
					+ "].taskName";
			nameCol.id = "idtaskName" + (rowCount - 1);

			nameCol.value = document.getElementById('laborItemName').value;
			/* nameCol.value = document.getElementById('laborItemName').value; */
			nameCol.readOnly = true;
			nameCol.style.width = "130px";
			nameCol.style.border = "none";
			cell2.appendChild(nameCol);

			var cell3 = row.insertCell(2); // to insert 3rd column
			var descriptionCol = document.createElement("input");
			descriptionCol.type = "text";
			descriptionCol.name = "estimateVO.estimateTasks[" + (rowCount - 1)
					+ "].description";
			descriptionCol.value = document.getElementById('laborItemDesc').value;
			descriptionCol.readOnly = true;
			descriptionCol.style.width = "190px";
			descriptionCol.style.border = "none";
			cell3.appendChild(descriptionCol);

			var cell4 = row.insertCell(3); //to insert 4th column
			var unitPriceCol = document.createElement("input");
			unitPriceCol.type = "text";
			unitPriceCol.name = "estimateVO.estimateTasks[" + (rowCount - 1)
					+ "].unitPrice";
			unitPriceCol.value = document.getElementById('laborItemPrice').value;
			unitPriceCol.readOnly = true;
			unitPriceCol.style.width = "70px";
			unitPriceCol.style.border = "none";
			cell4.appendChild(unitPriceCol);

			var cell5 = row.insertCell(4); //to insert 4th column
			var quantityCol = document.createElement("input");
			quantityCol.type = "text";
			quantityCol.name = "estimateVO.estimateTasks[" + (rowCount - 1) + "].quantity";
			quantityCol.value = document.getElementById('laborItemQty').value;
			quantityCol.readOnly = true;
			quantityCol.style.width = "60px";
			quantityCol.style.border = "none";
			cell5.appendChild(quantityCol);

			var cell6 = row.insertCell(5); //to insert 4th column
			var totalPriceCol = document.createElement("input");
			totalPriceCol.type = "text";
			totalPriceCol.name = "estimateVO.estimateTasks[" + (rowCount - 1) + "].totalPrice";
			totalPriceCol.id = "totallbrPrice" + (rowCount - 1);
			totalPriceCol.value = document.getElementById('labortotalPrice').value;
			totalPriceCol.readOnly = true;
			totalPriceCol.style.width = "71px";
			totalPriceCol.style.border = "none";
			cell6.appendChild(totalPriceCol);
			
			if(document.getElementById('laborItemCmt').value){
				
				divElem= document.createElement("div");

		          divElem.innerHTML='YES';
		          divElem.className="tooltipCustom" ;

		          spanEle= document.createElement("span");
		          spanEle.innerHTML=document.getElementById('laborItemCmt').value;
		          spanEle.className="tooltipCustomtext" ;
				 

				var cell7 = row.insertCell(6); //to insert 4th column
				var additionalDetailsCol = document.createElement("input");
				additionalDetailsCol.type = "hidden";
				additionalDetailsCol.name = "estimateVO.estimateTasks[" + (rowCount - 1) + "].additionalDetails";
				additionalDetailsCol.value = document.getElementById('laborItemCmt').value;
				additionalDetailsCol.readOnly = true;
				additionalDetailsCol.style.width = "200px";
				additionalDetailsCol.style.border = "none";
				divElem.appendChild(additionalDetailsCol);
	            divElem.appendChild(spanEle);
				cell7.appendChild(divElem);
				
			}else{
				var cell7 = row.insertCell(6); //to insert 4th column
				var additionalDetailsCol = document.createElement("input");
				additionalDetailsCol.type = "text";
				additionalDetailsCol.name = "estimateVO.estimateTasks[" + (rowCount - 1) + "].additionalDetails";
				additionalDetailsCol.value = document.getElementById('laborItemCmt').value;
				additionalDetailsCol.readOnly = true;
				additionalDetailsCol.style.width = "200px";
				additionalDetailsCol.style.border = "none";
				cell7.appendChild(additionalDetailsCol);
				
			}

			

			var cell8 = row.insertCell(7); // to insert 5th column
			var rowEditCol = document.createElement("a");
			var text = document.createTextNode("Edit");
			rowEditCol.appendChild(text);
			rowEditCol.setAttribute("href", "javascript:editRow(" + (rowCount) + "," + "\"laborItemTable\"" + ")");
			rowEditCol.name = "reqlink[]";
			cell8.appendChild(rowEditCol);

			var cell9 = row.insertCell(8); // to insert 3rd column
			var laborItemtype = document.createElement("input");
			laborItemtype.type = "hidden";
			laborItemtype.name = "estimateVO.estimateTasks[" + (rowCount - 1) + "].taskType";

			//otherItemTotalPrice.id="otherItemTotalPrice" + (rowCount - 1);
			laborItemtype.value = 'LABOR';

			cell9.appendChild(laborItemtype);

			getFinalEstimationTotalPrice();

			resetFields(targetTable);

			/*  $("#laborItemName").val("");
			 $("#laborItemDesc").val("");
			 $("#laborItemPrice").val("");
			 $("#laborItemQty").val("1");
			 $("#labortotalPrice").val("");
			 $("#labourItemTax").val("");
			 $("#laborItemCmt").val("");  */

			jQuery("#laborItemAdd").show();
			jQuery("#laborItemCancel").hide();
			jQuery("#divLaborItem").hide();
			
		} else if (targetTable === 'partItemTable') {
			/* if (nameValidationParts('partItemName') != true){
				return
			}; */
			if (nameValidation('partItemName') != true){
				return
			};
			if (numberValidation('partNumc', alphanumeric) != true){
				return
			};
			if (priceValidation('partItemPrice', numbersOnly) != true){
				return
			};
			if (quantityValidation('partItemQty', numOnly) != true){
				return
			};								
			document.getElementById("div2").innerHTML = "";
			if (index !== "null") {
                //console.log("index val");
                //console.log(index);
				row = table.insertRow(parseInt(index));
				row.style.outline = "thin solid";
				row.style.height = "30px";
				rowCount = parseInt(index);
				$("#index").val("null");
			} else {
				rowCount = table.rows.length;
				row = table.insertRow(rowCount); //to insert blank row
				row.style.outline = "thin solid";
				row.style.height = "30px";
			}
			
			document.getElementById(targetTable).style.display = "block";
			var partCell1 = row.insertCell(0); //to insert first column
			var partSnoCol = document.createElement("input");
			partSnoCol.type = "text";
			partSnoCol.name = "estimateVO.estimateParts[" + (rowCount - 1)
					+ "].partSeqNumber";
			partSnoCol.value = rowCount;
			partSnoCol.style.width = "40px";
			partSnoCol.style.border = "none";
			partCell1.appendChild(partSnoCol);

			var partCell2 = row.insertCell(1); //to insert second column
			var partNameCol = document.createElement("input");
			/* partNameCol.type = "text"; */
			partNameCol.name = "estimateVO.estimateParts[" + (rowCount - 1)
					+ "].partName";
			partNameCol.value = document.getElementById('partItemName').value;
			$("#partItemName").val("");
			partNameCol.readOnly = true;
			partNameCol.style.width = "130px";
			partNameCol.style.border = "none";
			;
			partCell2.appendChild(partNameCol);

			var partCell3 = row.insertCell(2); // to insert 3rd column
			var partDescriptionCol = document.createElement("input");
			partDescriptionCol.type = "text";
			partDescriptionCol.name = "estimateVO.estimateParts["+ (rowCount - 1) + "].description";
			partDescriptionCol.value = document.getElementById('partItemDesc').value;
			partDescriptionCol.readOnly = true;
			partDescriptionCol.style.width = "170px";
			partDescriptionCol.style.border = "none";
			partCell3.appendChild(partDescriptionCol);

			var partCell4 = row.insertCell(3); // to insert 3rd column
			var partNumberCol = document.createElement("input");
			partNumberCol.type = "text";
			partNumberCol.name = "estimateVO.estimateParts[" + (rowCount - 1)+ "].partNumber";
			partNumberCol.value = document.getElementById('partNumc').value;
			partNumberCol.readOnly = true;
			partNumberCol.style.width = "70px";
			partNumberCol.style.border = "none";
			partCell4.appendChild(partNumberCol);

			var partCell5 = row.insertCell(4); //to insert 4th column
			var partUnitPriceCol = document.createElement("input");
			partUnitPriceCol.type = "text";
			partUnitPriceCol.name = "estimateVO.estimateParts["+ (rowCount - 1) + "].unitPrice";
			partUnitPriceCol.value = document.getElementById('partItemPrice').value;
			partUnitPriceCol.readOnly = true;
			partUnitPriceCol.style.width = "70px";
			partUnitPriceCol.style.border = "none";
			partCell5.appendChild(partUnitPriceCol);

			var partCell6 = row.insertCell(5); //to insert 4th column
			var partQuantityCol = document.createElement("input");
			partQuantityCol.type = "text";
			partQuantityCol.name = "estimateVO.estimateParts[" + (rowCount - 1)+ "].quantity";
			partQuantityCol.value = document.getElementById('partItemQty').value;
			partQuantityCol.readOnly = true;
			partQuantityCol.style.width = "60px";
			partQuantityCol.style.border = "none";
			partCell6.appendChild(partQuantityCol);

			var partCell7 = row.insertCell(6); //to insert 4th column
			var partTotalPriceCol = document.createElement("input");
			partTotalPriceCol.type = "text";
			partTotalPriceCol.name = "estimateVO.estimateParts["+ (rowCount - 1) + "].totalPrice";
			partTotalPriceCol.id = "totalPartsPrice" + (rowCount - 1);
			partTotalPriceCol.value = document.getElementById('partItemTotalPrice').value;
			partTotalPriceCol.readOnly = true;
			partTotalPriceCol.style.width = "71px";
			partTotalPriceCol.style.border = "none";
			partCell7.appendChild(partTotalPriceCol);

			if(document.getElementById('partItemCmt').value){
				
				divElem= document.createElement("div");

		          divElem.innerHTML='YES';
		          divElem.className="tooltipCustom" ;

		          spanEle= document.createElement("span");
		          spanEle.innerHTML=document.getElementById('partItemCmt').value;
		          spanEle.className="tooltipCustomtext" ;
				 

		            var partCell8 = row.insertCell(7); //to insert 4th column
					var partAdditionalDetailsCol = document.createElement("input");
					partAdditionalDetailsCol.type = "hidden";
					partAdditionalDetailsCol.name = "estimateVO.estimateParts["+ (rowCount - 1) + "].additionalDetails";
					partAdditionalDetailsCol.value = document.getElementById('partItemCmt').value;
					partAdditionalDetailsCol.readOnly = true;
					partAdditionalDetailsCol.style.width = "150px";
					partAdditionalDetailsCol.style.border = "none";
				    divElem.appendChild(partAdditionalDetailsCol);
	            	divElem.appendChild(spanEle);
	            	partCell8.appendChild(divElem);
			}else{
				var partCell8 = row.insertCell(7); //to insert 4th column
				var partAdditionalDetailsCol = document.createElement("input");
				partAdditionalDetailsCol.type = "text";
				partAdditionalDetailsCol.name = "estimateVO.estimateParts["+ (rowCount - 1) + "].additionalDetails";
				partAdditionalDetailsCol.value = document.getElementById('partItemCmt').value;
				partAdditionalDetailsCol.readOnly = true;
				partAdditionalDetailsCol.style.width = "150px";
				partAdditionalDetailsCol.style.border = "none";
				partCell8.appendChild(partAdditionalDetailsCol);
				
			}

			var cell9 = row.insertCell(8); // to insert 5th column
			var rowEditCol = document.createElement("a");
			var text = document.createTextNode("Edit");
			rowEditCol.appendChild(text);
			rowEditCol.setAttribute("href", "javascript:editRow(" + (rowCount)+ "," + "\"partItemTable\"" + ")");
			rowEditCol.name = "reqlink[]";
			cell9.appendChild(rowEditCol);

			var cell10 = row.insertCell(9); // to insert 3rd column
			var partItemtype = document.createElement("input");
			partItemtype.type = "hidden";
			partItemtype.name = "estimateVO.estimateParts[" + (rowCount - 1)+ "].taskType";

			//otherItemTotalPrice.id="otherItemTotalPrice" + (rowCount - 1);
			partItemtype.value = 'PART'

			cell10.appendChild(partItemtype);

			getFinalEstimationTotalPrice();
			resetFields(targetTable);

			$("#partItemName").val("");
			$("#partItemDesc").val("");
			$("#partNumc").val("");
			$("#partItemPrice").val("");
			$("#partItemQty").val("1");
			$("#partItemTotalPrice").val("");
			$("#partItemTax").val("");
			$("#partItemCmt").val("");

			jQuery("#partItemAdd").show();
			jQuery("#partItemCancel").hide();
			jQuery("#divPartItem").hide();
		} else if (targetTable === 'otherItemTable'
				&& $("#otherItemAdd").is(':checked')) {
			document.getElementById(targetTable).style.display = "block";

			var partCell1 = row.insertCell(0); //to insert first column
			var partSnoCol = document.createElement("input");
			partSnoCol.type = "text";
			partSnoCol.name = "estimateVO.estimateOtherEstimateServices["+ (rowCount - 1) + "].otherServiceSeqNumber";
			partSnoCol.value = rowCount;
			partSnoCol.readOnly = true;
			partSnoCol.style.width = "40px";
			partSnoCol.style.border = "none";
			partCell1.appendChild(partSnoCol);

			var partCell2 = row.insertCell(1); //to insert second column
			var partNameCol = document.createElement("input");
			/* partNameCol.type = "text"; */
			partNameCol.name = "estimateVO.estimateOtherEstimateServices["+ (rowCount - 1) + "].otherServiceName";
			partNameCol.value = document.getElementById('otherItemName').value;
			partNameCol.readOnly = true;
			partNameCol.style.width = "130px";
			partNameCol.style.border = "none";
			partCell2.appendChild(partNameCol);

			var partCell3 = row.insertCell(2); // to insert 3rd column
			var partDescriptionCol = document.createElement("input");
			partDescriptionCol.type = "text";
			partDescriptionCol.name = "estimateVO.estimateOtherEstimateServices["+ (rowCount - 1) + "].description";
			partDescriptionCol.value = document.getElementById('otherItemDesc').value;
			partDescriptionCol.readOnly = true;
			partDescriptionCol.style.width = "321px";
			partDescriptionCol.style.border = "none";
			partCell3.appendChild(partDescriptionCol);

			var partCell4 = row.insertCell(3); // to insert 3rd column
			var otherItemPrice = document.createElement("input");
			otherItemPrice.type = "text";
			otherItemPrice.name = "estimateVO.estimateOtherEstimateServices["+ (rowCount - 1) + "].unitPrice";
			otherItemPrice.value = document.getElementById('otherItemPrice').value;
			otherItemPrice.readOnly = true;
			otherItemPrice.style.width = "70px";
			otherItemPrice.style.border = "none";
			partCell4.appendChild(otherItemPrice);

			var partCell5 = row.insertCell(4); // to insert 3rd column
			var otherItemQty = document.createElement("input");
			otherItemQty.type = "text";
			otherItemQty.name = "estimateVO.estimateOtherEstimateServices["+ (rowCount - 1) + "].quantity";
			otherItemQty.value = document.getElementById('otherItemQty').value;
			otherItemQty.readOnly = true;
			otherItemQty.style.width = "100px";
			otherItemQty.style.border = "none";
			partCell5.appendChild(otherItemQty);

			var partCell6 = row.insertCell(5); // to insert 3rd column
			var otherItemTotalPrice = document.createElement("input");
			otherItemTotalPrice.type = "text";
			otherItemTotalPrice.name = "estimateVO.estimateOtherEstimateServices["+ (rowCount - 1) + "].totalPrice";
			otherItemTotalPrice.id = "otherItemTotalPrice0";
			//otherItemTotalPrice.id="otherItemTotalPrice" + (rowCount - 1);
			otherItemTotalPrice.value = document.getElementById('otherItemTotalPrice').value;
            //console.log("otheritem price");
            //console.log(document.getElementById('otherItemTotalPrice').value);
			otherItemTotalPrice.readOnly = true;
			otherItemTotalPrice.style.width = "100px";
			otherItemTotalPrice.style.border = "none";
			partCell6.appendChild(otherItemTotalPrice);

			var cell7 = row.insertCell(6); // to insert 5th column
			var rowEditCol = document.createElement("a");
			var text = document.createTextNode("Edit");
			rowEditCol.appendChild(text);
			rowEditCol.setAttribute("href", "javascript:editRow(" + (rowCount)+ "," + "\"otherItemTable\"" + ")");
			rowEditCol.name = "reqlink[]";
			cell7.appendChild(rowEditCol);

			var cell8 = row.insertCell(7); // to insert 3rd column
			var OtherItemtype = document.createElement("input");
			OtherItemtype.type = "hidden";
			OtherItemtype.name = "estimateVO.estimateOtherEstimateServices["+ (rowCount - 1) + "].taskType";

			//otherItemTotalPrice.id="otherItemTotalPrice" + (rowCount - 1);
			OtherItemtype.value = 'OTHER'
			cell8.appendChild(OtherItemtype);

			resetFields(targetTable);
			getFinalEstimationTotalPrice();

			jQuery("#otherItemAdd").show();
			jQuery("#otherItemCancel").hide();
			jQuery("#divOtherItem").hide();
		}

		return false;

	}

	/* function delete_row(no) {
	 document.getElementById("itemRow" + no + "").outerHTML = "";
	 }

	 function goSubmit(rowindex) {
	 document.addItem.rowindex.value = rowindex;
	 document.addItem.action = "delete";
	 document.addItem.submit();

	 } */
	 
	function editRow(no, targetTable) { 
        //console.log("row number" + no);
        //console.log(targetTable);
       $("#laborItemAdd").hide();
       $("#partItemAdd").hide();
       $("#laborItemCancel").hide();
       $("#partItemCancel").hide();
    
       

		$("#index").val(no);

		if (no == 1) {
			var table = document.getElementById(targetTable);
            //console.log("if ...");
            //console.log(table.rows.length);
			document.getElementById(targetTable).style.display = "none";
		} else {
			document.getElementById(targetTable).style.display = "block";
		}
		if (targetTable === 'laborItemTable') {
			//console.log("if start");
			/* console.log($(
			 "input[name='estimateVO.estimateTasks[" + (no - 1)
			 + "].taskName']").val()) */;
			$("#laborItemName").val(
					$(
							"input[name='estimateVO.estimateTasks[" + (no - 1)
									+ "].taskName']").val());
			$("#laborItemDesc").val(
					$(
							"input[name='estimateVO.estimateTasks[" + (no - 1)
									+ "].description']").val());
			$("#laborItemPrice").val(
					$(
							"input[name='estimateVO.estimateTasks[" + (no - 1)
									+ "].unitPrice']").val());
			$("#laborItemQty").val(
					$(
							"input[name='estimateVO.estimateTasks[" + (no - 1)
									+ "].quantity']").val());
			$("#labortotalPrice").val(
					$(
							"input[name='estimateVO.estimateTasks[" + (no - 1)
									+ "].totalPrice']").val());
			//$("#labourItemTax").val("");
			$("#laborItemCmt").val(
					$(
							"input[name='estimateVO.estimateTasks[" + (no - 1)
									+ "].additionalDetails']").val());

			document.getElementById("laborItemTable").deleteRow(no);
			jQuery("#divLaborItem").show();

		} else if (targetTable === 'partItemTable') {
            //console.log("part item");
            //console.log(no);
			console.log($(
					"input[name='estimateVO.estimateParts[" + (no - 1)
							+ "].partName']").val());

			$("#partItemName").val(
					$(
							"input[name='estimateVO.estimateParts[" + (no - 1)
									+ "].partName']").val());
			$("#partItemDesc").val(
					$(
							"input[name='estimateVO.estimateParts[" + (no - 1)
									+ "].description']").val());
			$("#partNumc").val(
					$(
							"input[name='estimateVO.estimateParts[" + (no - 1)
									+ "].partNumber']").val());
			$("#partItemPrice").val(
					$(
							"input[name='estimateVO.estimateParts[" + (no - 1)
									+ "].unitPrice']").val());
			$("#partItemQty").val(
					$(
							"input[name='estimateVO.estimateParts[" + (no - 1)
									+ "].quantity']").val());
			$("#partItemTotalPrice").val(
					$(
							"input[name='estimateVO.estimateParts[" + (no - 1)
									+ "].totalPrice']").val());
			$("#partItemCmt").val(
					$(
							"input[name='estimateVO.estimateParts[" + (no - 1)
									+ "].additionalDetails']").val());

			document.getElementById("partItemTable").deleteRow(no);
			jQuery("#divPartItem").show();
            //console.log("part item end");
            //console.log($("#partItemName").val());

		} else if (targetTable === 'otherItemTable') {
			$("#otherItemName").val(
					$(
							"input[name='estimateVO.estimateOtherEstimateServices["
									+ (no - 1) + "].otherServiceName']").val());
			$("#otherItemDesc").val(
					$(
							"input[name='estimateVO.estimateOtherEstimateServices["
									+ (no - 1) + "].description']").val());
			$("#otherItemPrice").val(
					$(
							"input[name='estimateVO.estimateOtherEstimateServices["
									+ (no - 1) + "].unitPrice']").val());
			$("#partItemPrice").val(
					$(
							"input[name='estimateVO.estimateOtherEstimateServices["
									+ (no - 1) + "].quantity']").val());
			$("#otherItemQty").val(
					$(
							"input[name='estimateVO.estimateOtherEstimateServices["
									+ (no - 1) + "].totalPrice]").val());

			document.getElementById("otherItemTable").deleteRow(no);
			jQuery("#divOtherItem").show();

		}

	}

	function toggleInput(addBtnId, cancelBtnId, divId, enable) {
		

		if (enable) {
			// add
			jQuery("#" + addBtnId).hide();
			jQuery("#" + cancelBtnId).show();
			jQuery("#" + divId).show();
		} else {
			// cancel
			jQuery("#" + addBtnId).show();
			jQuery("#" + cancelBtnId).hide();
			jQuery("#" + divId).hide();
			if (cancelBtnId === 'otherItemCancel') {
				$('#otherItemAdd').prop('checked', false);

			}
		}
		
		if(cancelBtnId === 'laborItemCancel'){
			resetFields('laborItemTable');
			
		}else if(cancelBtnId === 'partItemCancel'){
			resetFields('partItemTable');
			
		}else if(cancelBtnId === 'otherItemCancel'){
			resetFields('otherItemTable');
		}
	}

	function toggleOtherItemCheckBox(addBtnId, cancelBtnId, divId, enable) {

		if ($("#otherItemAdd").is(':checked')) {
			// add
			jQuery("#" + addBtnId).hide();
			jQuery("#" + cancelBtnId).show();
			jQuery("#" + divId).show();
		} else {
			// cancel

			if (!isNaN(parseFloat($("#otherItemTotalPrice0").val()).toFixed(2))
					&& !isNaN(parseFloat($("#totalEstimationPrice").val()).toFixed(2))) {
				var total = 0;
				total = $("#totalEstimationPrice").val()
						- parseFloat($("#otherItemTotalPrice0").val()).toFixed(2);

				$("#totalEstimationPrice").val(total);
			}

			resetFields('otherItemTable');
			//$("#otherItemTable").empty();
			$('#otherItemTable tr:gt(0)').remove();
			$('#otherItemTable').hide();
			//$('#otherItemAdd').prop('checked', false);
			//otherItemTotalPrice0
			//executes when unchecked and substract the price from total

			jQuery("#" + addBtnId).show();
			jQuery("#" + cancelBtnId).hide();
			jQuery("#" + divId).hide();
		}
	}
	function resetFields(targetTable) {

		if (targetTable === 'laborItemTable') {

			$("#laborItemName").val("");
			$("#laborItemDesc").val("");
			$("#laborItemPrice").val("");
			$("#laborItemQty").val("1");
			$("#labortotalPrice").val("");
			$("#labourItemTax").val("");
			$("#laborItemCmt").val("");

		} else if (targetTable === 'partItemTable') {

			$("#partItemName").val("");
			$("#partItemDesc").val("");
			$("#partNumc").val("");
			$("#partItemPrice").val("");
			$("#partItemQty").val("1");
			$("#partItemTotalPrice").val("");
			$("#partItemTax").val("");
			$("#partItemCmt").val("");

			

		} else if (targetTable === 'otherItemTable') {

			$("#otherItemName").val("");
			$("#otherItemDesc").val("");
			$("#otherItemPrice").val("");
			$("#otherItemQty").val("1");
			$("#otherItemTotalPrice").val("");

			jQuery("#partItemAdd").show();
			jQuery("#partItemCancel").hide();
			jQuery("#divPartItem").hide();
			/* && $("#isAgeSelected").is(':checked')) */

		}

	}
	
	function saveORUpdateEstimate(event) {
		var length = document.getElementById('laborItemTable').rows.length;
		if (($('#divLaborItem').css('display') == 'block') ||($('#divPartItem').css('display') == 'block') || ($('#divOtherItem').css('display') == 'block') ) {
			alert("Please click on Add and SUBMIT");
			event.preventDefault();
			return false;
		}
		if(length > 1){
            //console.log("executing saveEstimate");
			if($("#estimationId").val() == "" || $("#estimateStatus").val() == "DRAFT"){
				$("#estimateStatus").val("NEW");			
			}else if($("#estimateStatus").val() == "ACCEPTED" || $("#estimateStatus").val() == "DECLINED" && $("#estimationId").val() != ""){
				$("#estimateStatus").val("UPDATED");
			}
			$("#saveEstimate").prop("disabled", true);
			document.formSaveEstimate.submit();
			return false;
		}else{
			alert("Please add atleast one labor item");		
			event.preventDefault();
			return false;
		} 
		return false;
		
		
		
	}
	
	

	function saveEstimateAsDraft() {		
		var length = document.getElementById('laborItemTable').rows.length;
		if (($('#divLaborItem').css('display') == 'block') ||($('#divPartItem').css('display') == 'block') || ($('#divOtherItem').css('display') == 'block') ) {
			alert("Please click on Add and Save as Draft");
			event.preventDefault();
			return false;
		}
		if(length > 1){
			$("#estimateStatus").val("DRAFT");
			$("#draftEstimate").prop("disabled", true);
			document.formSaveEstimate.submit();
            //console.log("executing saveEstimateasDraft");
			return false;
		}else{
			alert("Please add atleast one labor item");		
			event.preventDefault();
			return false;
		} 
		return false;
	}
	
</script>
<body>
	<div>
		<input type="hidden" id="accept_so_id" value="${soId}"> <input
			type="hidden" id="groupInd" value=""> <input type="hidden"
			id="index" value="null"> <input type="hidden"
			id="routeMethod" value="${routeMethod}">
		<div id="modalTitleBlack"
			style="background: #58585A; border-bottom: 2px solid black; color: #FFFFFF; text-align: center; height: 25px; padding-left: 8px; padding-top: 5px;">
			<c:choose>
				<c:when test="${estimateVO.estimationId == null}">
					<span style="font-size: 17px; font-weight: bold;"> Add
						Estimate</span>
				</c:when>
				<c:otherwise>
					<span style="font-size: 17px; font-weight: bold;"> View &
						Edit Estimate</span>
				</c:otherwise>
			</c:choose>

			<div style="float: right; padding: 5px; color: #CCCCCC;">
				<i class="icon-remove-circle"
					style="font-size: 20px; position: absolute; right: 5px; cursor: pointer; top: 5px;"
					onclick="closeModal('addEstimate');"></i>
			</div>
		</div>
		<!-- new code start -->
		<s:form name="formSaveEstimate" action="saveSOEstimate" method="post"
			theme="simple" class="${fromServiceOD} ? fromSODFrom : ">
			<div style="overflow-y: scroll; margin-left: 15px;">
				<div class="soDetailsHeader">					
					<p class="textColor">Reference S.O. # <span style="color: red;">*</span></p> 
					<s:textfield name="estimateVO.estimationRefNo" id="refID"
                        cssStyle="width: 60px;"
                        onblur="referenceValidation('refID',alphanumeric)" maxlength="8">
						</s:textfield>
						<div id="div3"></div>
				</div>				

				<div class="soDetailsHeader">
					<p class="textColor">Customer Name</p>
					<div>${estimateVO.serviceContact.firstName}
						${estimateVO.serviceContact.lastName}</div>
				</div>

				<div class="soDetailsHeader">
					<p class="textColor">Customer Location</p>
					<div>${estimateVO.serviceLocation.city}
						${estimateVO.serviceLocation.state}
						${estimateVO.serviceLocation.zip}</div>
				</div>

				<div class="soDetailsHeader">
					<p class="textColor">Date</p>
				<c:if test="${not empty estimateVO.estimationDate}">
                        <fmt:formatDate value="${estimateVO.estimationDate}"
                            pattern="MMM dd yyyy" />
				</c:if>
				<c:if test="${empty estimateVO.estimationDate}">
                        <fmt:formatDate value="<%=new java.util.Date()%>"
                            pattern="MMM dd, yyyy" />
				</c:if>
				</div>

				<div class="soEstimateBody">




					<s:hidden name="rowindex" />
					<s:hidden name="estimateVO.soId" />
					<%-- <s:hidden name="estimateVO.estimationRefNo" value="est55" /> --%>
					<s:hidden name="estimateVO.createdBy" />
					<s:hidden name="estimateVO.modifiedBy" />
					<s:hidden name="estimateVO.totalOtherServicePrice"
						id="totalOtherServicePrice" />
					<s:hidden name="estimateVO.vendorId" />
					<s:hidden name="estimateVO.discountType" id="discountType" />
					<s:hidden name="estimateVO.discountedPercentage"
						id="discountedPercentage" />
					<%-- <s:hidden name="estimateVO.discountedAmount" id="discountedAmount" value="0"
						 />
					<s:hidden name="estimateVO.taxPrice" value="0" id="taxPrice" /> --%>
                    <s:hidden name="estimateVO.discountedAmount" id="discountedAmount" />
					<s:hidden name="estimateVO.taxPrice"  id="taxPrice" />
					<s:hidden name="estimateVO.taxType" value="N.A" />
					<s:hidden name="estimateVO.status" id="estimateStatus" />
					<s:hidden name="estimateVO.estimationId" id="estimationId" />
					<s:hidden name="estimateVO.resourceId" />






					<div class="displayHeader">
						<strong>ADD LABOR ITEM<span style="color: red;">*</span></strong>
						<div style="float: right;">
							<input type="button" value="+ ADD ITEM" id="laborItemAdd"
								style="margin-right: 34px; font-size: 12px"
								onclick="toggleInput('laborItemAdd','laborItemCancel', 'divLaborItem',true);" />

							<input type="button" value="CANCEL"
								style="display: none; font-size: 12px" id="laborItemCancel"
								onclick="toggleInput('laborItemAdd','laborItemCancel','divLaborItem',false);" />
							<strong>Total Labor : $<s:textfield
									name="estimateVO.totalLaborPrice"
									cssStyle="width: 80px; border: none; background: whitesmoke;"
									id="finalTotalLaborPrice" readonly="true" />
							</strong>
						</div>
						<div id="div1"></div>
					</div>




					<TABLE id="laborItemTable"
						style="display:${(estimateVO.estimateTasks == null ||fn:length(estimateVO.estimateTasks) eq 0 ) ? 'none': 'block'};">

						<TR>
							<TD width="40px">S.no</TD>
							<TD width="130px">Name</TD>
							<TD width="190px">Description</TD>
							<TD>Price</TD>
							<TD width="60px">Hours</TD>
							<TD width="71px">Total</TD>
							<TD width="200px">Additional Comments</TD>
						</TR>

						<s:iterator value="estimateVO.estimateTasks" status="cnt"
							var="estimateTask">
							<tr style="outline: thin solid; height: 30px">
								<TD><s:textfield
										name="estimateVO.estimateTasks[%{#cnt.count-1}].taskSeqNumber"
										readonly="true" cssStyle="width: 40px; border: none;" /></TD>

								<TD><s:textfield
										name="estimateVO.estimateTasks[%{#cnt.count-1}].taskName"
										readonly="true" cssStyle="width: 130px; border: none;" /></TD>

								<TD><s:textfield
										name="estimateVO.estimateTasks[%{#cnt.count-1}].description"
										readonly="true" cssStyle="width: 190px; border: none;"  /></TD>

								<TD><s:textfield
										name="estimateVO.estimateTasks[%{#cnt.count-1}].unitPrice"
										readonly="true" cssStyle="width: 70px; border: none;" /></TD>

								<TD><s:textfield
										name="estimateVO.estimateTasks[%{#cnt.count-1}].quantity"
										readonly="true" cssStyle="width: 60px; border: none;" /></TD>

								<TD><s:textfield
										name="estimateVO.estimateTasks[%{#cnt.count-1}].totalPrice"
										id="totallbrPrice%{#cnt.count-1}" readonly="true"
										cssStyle="width: 71px; border: none;" /></TD>

								<%-- <TD><s:textfield
										name="estimateVO.estimateTasks[%{#cnt.count-1}].additionalDetails"
										readonly="true" cssStyle="border: none; width: 200px;" /></TD> --%>

								<c:if test="${not empty estimateTask.additionalDetails}">

									<td>
										<div class="tooltipCustom">
											YES <span class="tooltipCustomtext">${estimateTask.additionalDetails}</span>
											<s:hidden
												name="estimateVO.estimateTasks[%{#cnt.count-1}].additionalDetails"
										readonly="true" cssStyle="border: none; width: 200px;" />
										</div>

									</td>
									<%-- <td width="75px">${part.additionalDetails}</td> --%>
								</c:if>
								<c:if test="${empty estimateTask.additionalDetails}">
									<TD><s:textfield
											name="estimateVO.estimateTasks[%{#cnt.count-1}].additionalDetails"
											readonly="true" cssStyle="border: none; width: 200px;" /></TD>
								</c:if>

								<td>
								
								<a
									href="javascript:editRow(<s:property value='#cnt.count'/>,'laborItemTable')">Edit</a>
									</td>
							</tr>
						</s:iterator>

					</TABLE>
					<div id="divLaborItem" style="display: none;">
						<table>
							<tr>
								<td>Name<span style="color: red;">*</span><br> <input
									type="text" id="laborItemName" maxlength="50"
									style="width: 166px;" /></td>

								<td>Description<br> <textarea id="laborItemDesc"
                                        rows="1" cols="20" maxlength="255" style="width: 166px;"
                                        class="${fromServiceOD} ? fromSODesc : "></textarea>
								</td>

								<td>Price<span style="color: red;">*</span><br> <!-- <input type="text" id="laborItemPrice"
										style="width: 90px;" onblur="priceValidation('laborItemPrice',numbersOnly)"/> -->
									<input type="text" id="laborItemPrice" style="width: 90px;" />
								</td>

								<td>Hours<span style="color: red;">*</span><br> <input
									type="text" id="laborItemQty" value="1" maxlength="2"
									style="width: 90px;" />
								</td>

								<td>Total<br> <input type="text" id="labortotalPrice"
                                    style="width: 90px;" readonly="readonly" />
								</td>
							</tr>

							<tr>
								<!-- <td>Tax:<input type="text" id="labourItemTax" /></td> -->
                                <td>Additional Comments<br> <textarea
                                        id="laborItemCmt" rows="1" cols="20" maxlength="255"
                                        style="width: 166px; margin-right: 10px;"
                                        class="${fromServiceOD} ? fromSODesc : "></textarea>
								</td>
								<td><input type="button"
									onclick="addItems('laborItemTable')" value="Add"
									class="addButton" /></td>
							</tr>
						</table>
					</div>

					<div class="displayHeader">
						<strong>ADD PART ITEM</strong>
						<div style="float: right;">
							<input type="button" value="+ ADD ITEM"
								style="margin-right: 34px; font-size: 12px" id="partItemAdd"
								onclick="toggleInput('partItemAdd','partItemCancel', 'divPartItem',true);" />

							<input type="button" value="CANCEL"
								style="display: none; font-size: 12px" id="partItemCancel"
								onclick="toggleInput('partItemAdd','partItemCancel','divPartItem',false);" />
							<strong>Total Parts : $ <s:textfield
									name="estimateVO.totalPartsPrice"
									cssStyle="width: 80px; border: none; background: whitesmoke;"
									id="finalTotalPartsPrice" readonly="true" /></strong>
							<%-- Total : <s:textfield name="estimateVO.totalOtherServicePrice" id="totalOtherServicePrice"/> --%>

						</div>
						<div id="div2"></div>
					</div>
					<TABLE id="partItemTable"
						style="display: ${(estimateVO.estimateParts == null || fn:length(estimateVO.estimateParts) eq 0 ) ? 'none': 'block'};">

						<TR>
							<TD width="40px">S.no</TD>
							<TD width="130px">Name</TD>
							<TD width="170px">Description</TD>
							<TD width="60px">Number</TD>
							<TD width="60px">Price</TD>
							<TD width="60px">Quantity</TD>
							<TD width="71px">Total</TD>
							<TD width="200px">Additional Comments</TD>
						</TR>

						<s:iterator value="estimateVO.estimateParts" status="cnt"
							var="estimatePart">
							<tr style="outline: thin solid; height: 30px">
								<TD><s:textfield
										name="estimateVO.estimateParts[%{#cnt.count-1}].partSeqNumber"
										readonly="true" cssStyle="width: 40px; border: none;" /></TD>

								<TD><s:textfield
										name="estimateVO.estimateParts[%{#cnt.count-1}].partName"
										cssStyle="width: 130px; border: none;" readonly="true" /></TD>

								<TD><s:textfield
										name="estimateVO.estimateParts[%{#cnt.count-1}].description"
										cssStyle="width: 170px; border: none;" readonly="true" /></TD>

								<TD><s:textfield
										name="estimateVO.estimateParts[%{#cnt.count-1}].partNumber"
										cssStyle="width: 70px; border: none;" readonly="true" /></TD>

								<TD><s:textfield
										name="estimateVO.estimateParts[%{#cnt.count-1}].unitPrice"
										cssStyle="width: 70px; border: none;" readonly="true" /></TD>

								<TD><s:textfield
										name="estimateVO.estimateParts[%{#cnt.count-1}].quantity"
										cssStyle="width: 60px; border: none;" readonly="true" /></TD>

								<TD><s:textfield
										name="estimateVO.estimateParts[%{#cnt.count-1}].totalPrice"
										id="totalPartsPrice%{#cnt.count-1}"
										cssStyle="width: 71px; border: none;" readonly="true" /></TD>

								<%-- <TD><s:textfield
										name="estimateVO.estimateParts[%{#cnt.count-1}].additionalDetails"
										cssStyle="border: none; width: 150px;" readonly="true" /></TD> --%>
								<c:if test="${not empty estimatePart.additionalDetails}">

									<td>
										<div class="tooltipCustom">
											YES <span class="tooltipCustomtext">${estimatePart.additionalDetails}</span>
										<s:hidden
										name="estimateVO.estimateParts[%{#cnt.count-1}].additionalDetails"
										cssStyle="border: none; width: 150px;" readonly="true" />
										</div>

									</td>
									<%-- <td width="75px">${part.additionalDetails}</td> --%>
								</c:if>
								
								<c:if test="${empty estimatePart.additionalDetails}">
									<TD><s:textfield
										name="estimateVO.estimateParts[%{#cnt.count-1}].additionalDetails"
										cssStyle="border: none; width: 150px;" readonly="true" /></TD>
								</c:if>

								<td>
								
								<a
									href="javascript:editRow(<s:property value='#cnt.count'/>,'partItemTable')">Edit</a>
									</td>
							</tr>
						</s:iterator>



					</TABLE>

					<div id="divPartItem" style="display: none;">
						<table style="width: 100%">
							<tr>
								<td>Name<span style="color: red;">*</span></td>
								<td>Description</td>
								<td>Number<span style="color: red;">*</span></td>
								<td>Price<span style="color: red;">*</span></td>
								<td>Quantity<span style="color: red;">*</span></td>
								<td>Total</td>
							</tr>
							<tr>
								<td style="width: 10px"><input type="text"
									id="partItemName" maxlength="50" /></td>
								<td><textarea id="partItemDesc" rows="1" cols="20"
                                        maxlength="255" style="width: 166px;"
                                        class="${fromServiceOD} ? fromSODesc : "></textarea></td>
										
                                <td><input type="text" id="partNumc" style="width: 77px"
                                    maxlength="30" /></td>
									
								<td><input type="text" id="partItemPrice"
									style="width: 90px" /></td>
								<td><input type="text" id="partItemQty" value="1"
									style="width: 90px" maxlength="2" /></td>
								<td><input type="text" id="partItemTotalPrice"
                                    style="width: 90px" readonly="readonly" /></td>
							</tr>

							<tr>
								<!-- <td>Tax:<input type="text" id="partItemTax" /></td> -->
								<td>Additional Comments <!-- <input type="text" id="partItemCmt" /> -->
									<textarea id="partItemCmt" rows="1" cols="20" maxlength="255"
                                        style="width: 166px; margin-right: 10px;"
                                        class="${fromServiceOD} ? fromSODesc : "></textarea>
								</td>
								<td><input type="button" class="addButton"
									onclick="addItems('partItemTable')" value="Add" /></td>
							</tr>
						</table>
					</div>

					<table>
						<%-- <tr>
							<td align="center"><s:submit value="Submit" align="center" />
							</td>
							</tr> --%>
					</table>



					<TABLE id="otherItemTable"
						style="display: ${(estimateVO.estimateOtherEstimateServices == null || fn:length(estimateVO.estimateOtherEstimateServices) eq 0 ) ? 'none': 'block'};">


						<TR>
							<TD width="40px">S.no:</TD>
							<TD width="130px">Name:</TD>
							<TD width="170px">Description:</TD>
							<TD width="70px">Price:</TD>
							<TD width="70px">Quantity:</TD>

						</TR>
						<s:iterator value="estimateVO.estimateOtherEstimateServices"
							status="cnt" var="estimateOtherEstimateServices">
							<tr style="outline: thin solid; height: 30px">
								<TD><s:textfield
										name="estimateVO.estimateOtherEstimateServices[%{#cnt.count-1}].otherServiceSeqNumber"
										cssStyle="border: none; width: 40px;" readonly="true" /></TD>

								<TD><s:textfield
										name="estimateVO.estimateOtherEstimateServices[%{#cnt.count-1}].otherServiceName"
										cssStyle="border: none; width: 130px;" readonly="true" /></TD>

								<TD><s:textfield
										name="estimateVO.estimateOtherEstimateServices[%{#cnt.count-1}].description"
										cssStyle="border: none; width: 321px;" readonly="true" /></TD>

								<TD><s:textfield
										name="estimateVO.estimateOtherEstimateServices[%{#cnt.count-1}].unitPrice"
										style="width:70px;" readonly="true" /></TD>

								<TD><s:textfield
										name="estimateVO.estimateOtherEstimateServices[%{#cnt.count-1}].quantity"
										cssStyle="border: none; width: 70px;" readonly="true" /></TD>
								<TD><s:textfield
										name="estimateVO.estimateOtherEstimateServices[%{#cnt.count-1}].quantity"
										cssStyle="border: none; width: 70px;" readonly="true" /></TD>
								<td><a
									href="javascript:editRow(<s:property value='#cnt.count'/>,'otherItemTable')">Edit</a></td>
							</tr>
						</s:iterator>


					</TABLE>

					<div id="divOtherItem" style="display: none;">
						<table>
							<tr>
								<td>Name<br> <input type="text" id="otherItemName"
									maxlength="50" /></td>
								<td>Description<br> <!-- <input type="text" id="otherItemDesc" /> -->
									<textarea id="otherItemDesc" rows="1" cols="20" maxlength="255"
										style="width: 166px;"></textarea>
								</td>
								<td>Price<br> <input type="text" id="otherItemPrice"
									style="width: 66px" />
								</td>
								<td>Quantity<br> <input type="text" id="otherItemQty"
									value="1" maxlength="2" />
								</td>
								<td><br> <input type="text" id="otherItemTotalPrice" /></td>
								<TD><br> <input type="button"
									onclick="addItems('otherItemTable')" value="Add" /></TD>

							</tr>


 
						</table>

					</div>
					<input type="checkbox" name="discountCheck" id="discountCheck"
						onclick="checkChecBox()"> <strong>DISCOUNTS </strong> <br>
					<table style="display: none;" id="discountTable">
						<tr>
							<td>Type</td>
							<td>value</td>
						</tr>
						<tr>
							<td>
								<div id="showDiscount">
                                    <select id="discountDropDown"
                                        onchange="getFinalEstimationTotalPrice()">
										<option value="PERCENTAGE">Percentage</option>
										<option value="AMOUNT">Amount</option>
									</select>
								</div>
							</td>
							<td><input type="text"  id="discountValue"
                                style="margin-right: 450px;"
                                onkeyup="discountValidation('discountValue')"></td>
							<div id="div4"></div>
							<td><p id="discPercentage" style="margin-left: -22px;"></p></td>
							<td><p id="discAmount"></p></td>
						</tr>
					</table>



					<input type="checkbox" name="taxCheck" id="taxCheck" checked="checked" disabled="disabled"
						onclick="checkChecBoxTax()"> <strong>TAX</strong>

					<div id="showTax" style="display: none;">
					<div id="div5"></div>
						Percentage <br>
						<div>
							<s:textfield name="estimateVO.taxRate" id="taxRate"
                                onkeyup="taxValidation('taxRate')" style="float: left;" readonly="true"/>
							<p id="taxPercentageAndValue" style="margin-left: 688px;"></p>
						</div>
					</div>

					<!-- new code end -->
					<!-- <div style="position: absolute; bottom: 10px; right: 5px;">
				<input type="submit" value="Save as Draft"  onclick="saveEstimateAsDraft()" />
				</div>
				<div style="position: absolute; bottom: 10px; right: 5px;">
					<input type="submit" value="SUBMIT"  onclick="saveEstimate()"/>
				</div> -->

				</div>

			</div>
			<hr></hr>
			<div style="float: right;">
				<strong>Total Amount $ <s:textfield
						name="estimateVO.totalPrice" id="totalEstimationPrice"
						readonly="true" cssStyle="border: none" /></strong>
			</div>
			<div id="accept-popup-footer"
				style="float: left; background: #EEEEEE; border-top: 1px solid #CCCCCC; width: 100%; margin-top: 20px;">
			</div>
			<!-- <div style="position: absolute; bottom: 10px;">
<input type="button" value="Save As Draft" id="estimateDraft"
onclick="saveEstimateAsDraft();" />
</div> -->

			<!-- <div style="position: absolute; bottom: 10px; right: 100px;">
<input type="button" value="Preview Estimate" id="previewEstimate "
onclick="saveEstimate();" />
</div> -->
			<s:hidden name="fromServiceOD"/>
			<div style="height: 65px;">
				<c:if
					test="${estimateVO.status == 'DRAFT' || empty estimateVO.status }">
					<input type="submit" id="draftEstimate" value="Save as Draft"
                        onclick="saveEstimateAsDraft(event)"
                        class="${fromServiceOD} ? fromSODButton : "
						style="height: 37px; width: 170px; margin-top: 12px; margin-left: 25px; background: #58585A; color: white;" />
				</c:if>
				
                <input type="submit" id="saveEstimate" value="SUBMIT"
                    onclick="saveORUpdateEstimate(event)"
                    class="${fromServiceOD} ? fromSODButton : "
					style="float: right; height: 37px; width: 170px; margin-top: 12px; margin-right: 12px; background: #0081AF; color: white; margin-bottom: 10px;">
				
			</div>
		</s:form>

		<%-- 
<div style="position: absolute; bottom: 10px; right: 5px;">
<s:submit value="Submit" align="center" />
</div> --%>


		<%-- <div id="loadTimerDiv" style="display:none;">
  <jsp:include page="order_management_panel_accept.jsp"/>
</div> --%>
	</div>

	</div>

</body>
</html>