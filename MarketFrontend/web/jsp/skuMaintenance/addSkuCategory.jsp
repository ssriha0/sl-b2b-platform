	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
	<%@ taglib prefix="s" uri="/struts-tags"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
	
	<c:set var="contextPath" scope="request"
		value="<%=request.getContextPath()%>" />
	<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />	
	<c:set var="retlPriceInfo" value="<%= OrderConstants.RETAILPRICE_INFO%>" />
	<c:set var="priceInfo" value="<%= OrderConstants.PRICETYPE_INFO%>" />
	<c:set var="bidInf" value="<%= OrderConstants.BID_INFO%>" />
	<c:set var="billingInf" value="<%= OrderConstants.BILLING_INFO%>" />
	<!-- SL-20728 Enable Rich text editing -->
	 <script src="${staticContextPath}/javascript/tinymce/tinymce.min.js"></script>
	  <script>
	  var max_chars = 5000; //max characters
	  var chars_without_html = 0;
	  tinymce.init({ 
		 selector:'#taskComments',menubar: false,statusbar: false,
		 plugins: ["autolink autoresize link paste"],
		 toolbar1: '| undo redo | bold italic underline | formatselect | fontsizeselect |',
		 toolbar2: '| alignleft aligncenter alignright alignjustify | outdent indent | bullist numlist | link unlink | removeformat |',
		 block_formats:'Paragraph=p;Heading 1=h1;Heading 2=h2;Heading 3=h3;Heading 4=h4;Heading 5=h5;Heading 6=h6',
		 autoresize_max_height: 100,
  		 setup: function (editor) {
			        editor.on('change', function () {
			            editor.save();
			        });
			        editor.on('focus', function (editor, evt) {
			            clearDefaultText('taskComments');
			            chars_without_html = $.trim(tinyMCE.activeEditor.getContent().replace(/(<([^>]+)>)/ig, "")).length;
			            var key = editor.keyCode;
	                    $('#taskComments_leftChars').val(max_chars - chars_without_html);
	                    if (chars_without_html >= max_chars  && key != 8 && key != 46) {
	                    	editor.stopPropagation();
	                    	editor.preventDefault();
	                    }
			        });
			        editor.on('keyup', function (editor, evt) {
			        	chars_without_html = $.trim(tinyMCE.activeEditor.getContent().replace(/(<([^>]+)>)/ig, "")).length;
			        	var key = editor.keyCode;
	                    $('#taskComments_leftChars').val(max_chars - chars_without_html);
	                    if (chars_without_html >= max_chars && key != 8 && key != 46) {
	                    	editor.stopPropagation();
	                    	editor.preventDefault();
	                    }
			        });
			        editor.on('keydown', function (editor, evt) {
			        	chars_without_html = $.trim(tinyMCE.activeEditor.getContent().replace(/(<([^>]+)>)/ig, "")).length;
			        	var key = editor.keyCode;
			        	$('#taskComments_leftChars').val(max_chars - chars_without_html);
	                    if (chars_without_html >= max_chars && key != 8 && key != 46) {
	                    	editor.stopPropagation();
	                    	editor.preventDefault();
	                    }
			        });
			        
			  }
		  });
	  </script>
	
	<script type="text/javascript">
	$(document).ready(function() {
	var skusize = '${noOfSkus}';
	var initialLoad = "true";
	//for loading category and skill on selecting main category
		$('#mainCategory').change(function(){
			var mainCatId = $("#mainCategory option:selected").val();
			$.getJSON ('fetchCategory.action', {mainCatId:mainCatId}, function(data) {
		  		if(data == null || data == ""){
		  		}
		  		else{
					if(initialLoad != "true"){
		  			if($("#taskName").val() != " Enter task name"){
		  			$("#taskName").val(" Enter task name"); 
		  			}
		  			if($("#taskComments").val() != "Enter task comments"){
		  			$("#taskComments").val("Enter task comments");
		  			} 	
		  			}	
		  		var oldCatgegId = '${oldCatgegId}';
		  		// Reset category
		  		$("#category").html('<option value="-1">--Select One--</option>');
		  		// if(data.categoryList == ""){
		  		// Reset sub category
		  		$("#subCategory").html('<option value="-1">--Select One--</option>');	
		  		// }
		  		//pupulating category list
		  		$.each(data.categoryList, function(index,value){
		  			if(value.id==oldCatgegId){
		  			$("#category").append('<option value="'+value.id+'" selected>'+value.descr+'</option>');
		  			$('#category').trigger('change');
		  			}else{
		  			$("#category").append('<option value="'+value.id+'">'+value.descr+'</option>');
		  			}				
			  	});
			  	//populating skill
			  	$("#skill").html('<option value="-1">--Select One--</option>');
		  		$.each(data.skillList, function(index,value){
		  			if(value.id=='${oldSkill}'){
		  			$("#skill").append('<option value="'+value.id+'" selected>'+value.descr+'</option>');
		  			}else{
		  			$("#skill").append('<option value="'+value.id+'">'+value.descr+'</option>');
		  			}	
			  	});
			  	
			  	//populating tempate names
			  	$("#templateName").html('<option value="-1">--Select One--</option>');
		  		$.each(data.templateNames, function(index,value){
		  			if(value.templateId=='${oldTemplate}'){	  			
		  			$("#templateName").append('<option value="'+value.templateId+'" selected>'+value.templateName+'</option>');
		  			}else{
		  			$("#templateName").append('<option value="'+value.templateId+'">'+value.templateName+'</option>');
		  			}	
			  	});
			  	initialLoad = "false";		  	
		  		}
		  		});
		  	});
	
	//for populating subcategory on change of category	  	
		  	$('#category').change(function(){
			var categoryId = $("#category option:selected").val();
			$.getJSON ('fetchSubCategory.action', {categoryId:categoryId}, function(data) {
		  		if(data.subCategoryList == null || data.subCategoryList == ""){
		  		$("#subCategory").html('<option value="-1">--Select One--</option>');
		  			if(categoryId != -1){
		  				// Reset the sub category
		  				if('${oldsubCategId}'=='noSubCateg'){
		  					$("#subCategory").append('<option value="0" selected>No sub category</option>');
		  				}else{
		  					$("#subCategory").append('<option value="0">No sub category</option>');
		  				}
		  			}
		  		}
		  		else{
		  			$("#subCategory").html('<option value="-1">--Select One--</option>');
		  			if('${oldsubCategId}'=='noSubCateg'){
	  					$("#subCategory").append('<option value="0" selected>No sub category</option>');
	  				}else{
	  					$("#subCategory").append('<option value="0">No sub category</option>');
	  				}
		  			$.each(data.subCategoryList, function(index,value){
		  			if(value.id=='${oldsubCategId}'){
		  				$("#subCategory").append('<option value="'+value.id+'" selected>'+value.descr+'</option>');
		  			}else{
		  				$("#subCategory").append('<option value="'+value.id+'">'+value.descr+'</option>');
		  			}	
			  	});
		  		}
		  		});
		  	});
	
	var action = '${modalTitle}';
	//for initializing fields for update sku
	if(action == "Update SKU"){
	    var activeSKU = '${activeSKU}';
		$("#orderItemType").val( '${buyerSkuDetails.orderitemType}' ).attr('selected',true);
		$("#priceType").val( '${buyerSkuDetails.priceType}' ).attr('selected',true);
		if('${noOfSkus}'>1){
		$("#mainCategory").attr('disabled',true);
		}
		else if('${noOfSkus}' == 1 && activeSKU == 'true')
		{
		 $("#mainCategory").attr('disabled',true);	
		}
		$("#mainCategory").trigger('change');
		
		existingBillingMargin = $('#billingMargin').val();
		existingBillingPrice = $('#billingPrice').val();
	}
	
	//for initializing the the fields in case of add sku/update sku 
	 if(action !="Add SKU Category"){
		$('#skuCategoryName').val(jQuery.trim($("#skuCategory option:selected").text()));
		$('#skuCategoryName').attr("readonly",true);
		$('#skuCategoryName').css('background-color', '#E6E6E6');
		var id = $('#skuCategory').val();
		var selectedCategoryDescr = $('#desc'+id).text();
	    $('#skuCategoryDesc').val(selectedCategoryDescr);
		$('#skuCategoryDesc').attr("readonly",true);
		$('#skuCategoryDesc').css('background-color', '#E6E6E6');
		}
	
	 if(action =="Add SKU"){
		$("#mainCategory").val( '${mainCategoryId}' ).attr('selected',true);
		$("#mainCategory").attr('disabled',true);
		$("#mainCategory").trigger('change');
		}
		
	 if(action =="Add SKU Category" || action =="Add SKU"){
	    var isNonFunded = '${isNonFundedBuyer}';
	      if(isNonFunded == 'true'){
	         $('#bidMargin').val("0.00");
		     $('#billingMargin').val("0.00");
	      }else{
	         $('#bidMargin').val("");
		     $('#billingMargin').val("");
		   }
		}	
		
		// Setting the remaining characters
		// 1.SKU Category description
		
		
		var value = $('#skuNameDesc').text();
		var remainingLengthDesc = 255;
		if(value!='Enter SKU Description'){
			remainingLengthDesc = (255-value.length);
		}
	    $("#skuDesc_leftChars").val(remainingLengthDesc);
	    
	    var value = $('#taskComments').text();
		var remainingLengthDesc = 5000;
		if(value!='Enter task comments'){
			remainingLengthDesc = (5000-value.length);
		}
	    $("#taskComments_leftChars").val(remainingLengthDesc);
	    
		
	});
	
	//script to clear default text
		function clearDefaultText(clickedId){
		var temp1=$('#'+clickedId).attr('readonly');
		var temp2=$('#'+clickedId).val();
		if(( temp1== false ||temp1 == undefined) && (temp2== "Enter SKU Name"  || temp2== "Enter SKU Description" || temp2== " Enter task name" || temp2== "Enter task comments" || temp2=="Enter SKU Category Name" ||temp2=="Enter SKU Category Description"))
		{
	 		$('#'+clickedId).val("");
	 		if(clickedId == 'taskComments'){
	 			tinyMCE.get('taskComments').setContent("");
	 		}
	 	}
	 	}	
	 	
	    function fmtMoney(mnt,id){
	          mnt -= 0;
	          mnt = (Math.round(mnt*100))/100;
	          var x = (mnt == Math.floor(mnt)) ? mnt + '.00' 
	                    : ( (mnt*10 == Math.floor(mnt*10)) ? 
	                             mnt + '0' : mnt);
	                             
	          if(id == 'bidMargin' || x > 0 )
	            return x;
	               return "0.00";
	     }
		          	
		  	function fnCalculateBilling(obj){
		  		var action = '${modalTitle}';
		  	    var billingMarigin=$('#billingMargin').val();
		  		var billingPrice=$('#billingPrice').val();
		  	
		  	if(obj.value != ""){		
	            obj.value = fmtMoney(obj.value,obj.id);	  
				} 
				
				if(isNaN(billingMarigin)){
					$('#pricingErrorDiv').html("Please enter a valid billing margin");
		  	 		$('#pricingErrorDiv').show();
		  	 		$('#bidMargin').val("");
		  	 		return;
				}
				// to restrict the length of wholenumber greater than 7. 
				  var wholeNumber=obj.value;
		  			if(wholeNumber !="")
		  				{
		  				wholeNumber=parseInt(wholeNumber, 10); 	  	
		  				var length=(wholeNumber+"").length;
		 				if(length>7)
		  				{
		  					$('#pricingErrorDiv').html("Please enter a valid billing margin");
		  	 				$('#pricingErrorDiv').show();
		  					obj.value="0.00";
		  					return;
		  				} 
		  			} 
		  		  		//to disable billing section fields
	
		  		if((billingMarigin != "" || billingMarigin == "0.00") && (billingPrice == "" || billingPrice == "0.00")){
		  			$('#billingPrice').val("");
		  			$('#billingPrice').attr("disabled",true);
		  			$('#billingPrice').css('background-color', '#E6E6E6');	
		  			return;  			
		  		}
		  		if((billingPrice != "" || billingPrice == "0.00") && (billingMarigin == "" || billingMarigin == "0.00")){
		  			$('#billingMargin').val("");
		  			$('#billingMargin').attr("disabled",true);
		  			$('#billingMargin').css('background-color', '#E6E6E6');	  	
		  			return;		
		  		}
		  		if(action=="Update SKU"){
				if(billingPrice != "0.00" && billingMarigin != "0.00"){
					if(billingPrice != "0.00" && existingBillingPrice == "0.00"){
					$('#billingMargin').val("");
		  			$('#billingMargin').attr("disabled",true);
		  			$('#billingMargin').css('background-color', '#E6E6E6');	  	
		  			return;	
					}
					if(billingMarigin != "0.00" && existingBillingMargin == "0.00"){
					$('#billingPrice').val("");
		  			$('#billingPrice').attr("disabled",true);
		  			$('#billingPrice').css('background-color', '#E6E6E6');
		  			return;				
					}	  		
		  		}
				}
		  	}
		  	
		  	//bid price-bid margin-retail price calculation
		  	function fnCalculateBid(obj){
	
				var action = '${modalTitle}';
		  		$('#pricingErrorDiv').hide();	
		  		if(obj.value != ""){		
	            obj.value = fmtMoney(obj.value,obj.id);	  
				}          
		  		var bidMargin = $('#bidMargin').val();
		  		
		  		var success = true;
		  		
				if(bidMargin>100){
					$('#pricingErrorDiv').html("Margin should be a value less than 100");
		  	 		$('#pricingErrorDiv').show();
		  	 		$('#bidMargin').val("");
		  	 		return;
				}
				if(isNaN(bidMargin)){
					$('#pricingErrorDiv').html("Please enter a valid margin");
		  	 		$('#pricingErrorDiv').show();
		  	 		$('#bidMargin').val("");
		  	 		return;
				}
				// to restrict the length of wholenumber greater than 7. 
				  var wholeNumber=obj.value;
		  			if(wholeNumber !="")
		  				{
		  				wholeNumber=parseInt(wholeNumber, 10); 
		  	
		  				 var length=(wholeNumber+"").length;
		 				if(length>7)
		  				{
		  					if(obj.id == 'billingMargin'){
		  					$('#pricingErrorDiv').html("Please enter a valid billing margin");
		  					}else{
		  					$('#pricingErrorDiv').html("Please enter a value not more than $9999999.99");
		  					}
		  	 				$('#pricingErrorDiv').show();
		  					obj.value="0.00";
		  				} 
		  			} 
		  			
	
		  		var bidPrice = $('#bidPrice').val();
		  	    var retailPrice = $('#retailPrice').val();
	
		  		if((retailPrice != "" && bidMargin != "") || (retailPrice != "" && bidMargin != "" && $('#bidPrice').attr('disabled')=='true')){
		  		
		  			// On update, if the retail price and the bid margin is 0.00, do not re-calculate the bid price
		  			if(action=="Update SKU" && retailPrice == 0.00 && bidMargin == 0.00){
		  				return;
		  			}	 
		  			
		  			// if either of the bid margin and retail price is already calucated
		  			if($('#bidMargin').attr('disabled')|| $('#retailPrice').attr('disabled')){
		  				// Recalculate the read only value from the other two
		  				if($('#bidMargin').attr('disabled')){
		  					if(retailPrice!=0)
		  			 			bidMargin = ((retailPrice - bidPrice)/retailPrice)*100;
		  		   			else	
		  			 			bidMargin=0;
		  			 		$('#bidMargin').val(bidMargin.toFixed(2));
		  				}else if ($('#retailPrice').attr('disabled')){
		  					var retailPrice = (bidPrice/(100- bidMargin))*100;
		  					$('#retailPrice').val(retailPrice.toFixed(2));
		  				}
		  			}
		  			else{
		  				var bidPrice = (retailPrice*(100-bidMargin))/100;
		  				$('#bidPrice').val(bidPrice.toFixed(2));
		  				if($('#bidMargin').attr('disabled')==false && $('#retailPrice').attr('disabled')==false){
		  					$('#bidPrice').attr("disabled",true);
		  				    $('#bidPrice').css('background-color', '#E6E6E6');	  					
		  				}
		  				$('#retailPrice').val(parseFloat(retailPrice).toFixed(2));
		  				$('#bidMargin').val(parseFloat(bidMargin).toFixed(2));
		  			}
		  			return;
		  		}	  		
		  		if((retailPrice != "" && bidPrice != "") || (retailPrice != "" && bidPrice != "" && $('#bidMargin').attr('disabled')=='true')){
		  		   if(retailPrice!=0)
		  			 bidMargin = ((retailPrice - bidPrice)/retailPrice)*100;
		  		   else
		  			 bidMargin=0;
		  			$('#bidMargin').val(bidMargin.toFixed(2));
		  			if($('#bidPrice').attr('disabled')==false && $('#retailPrice').attr('disabled')==false){
		  			$('#bidMargin').attr("disabled",true);
		  			$('#bidMargin').css('background-color', '#E6E6E6');
		  			}
		  			$('#bidPrice').val(parseFloat(bidPrice).toFixed(2));
		  			$('#retailPrice').val(parseFloat(retailPrice).toFixed(2));
		  			return;
		  		}
		  		
		  		if((bidMargin != "" && bidPrice != "") || (bidMargin != "" && bidPrice != "" && $('#retailPrice').attr('disabled')=='true')){
		  			var retailPrice = (bidPrice/(100- bidMargin))*100;
		  			$('#retailPrice').val(retailPrice.toFixed(2));
		  			if($('#bidPrice').attr('disabled')==false && $('#bidMargin').attr('disabled')==false){
		  			$('#retailPrice').attr("disabled",true);
		  			$('#retailPrice').css('background-color', '#E6E6E6');
		  			}
		  			$('#bidPrice').val(parseFloat(bidPrice).toFixed(2));
		  			$('#bidMargin').val(parseFloat(bidMargin).toFixed(2));
		  			return;
		  		}		
		  	}
		  	
		  	function clearPreviousErrors(){
			  			$('#skuCategoryErrorDiv').html("");
		  	 			$('#skuCategoryErrorDiv').hide();
			  			$('#skuErrorDiv').html("");
		  	 			$('#skuErrorDiv').hide();	
		  	 			$('#pricingErrorDiv').html("");
		  	 			$('#pricingErrorDiv').hide();	
		  	 			$('#otherErrorDiv').html("");
		  	 			$('#otherErrorDiv').hide(); 
		  	 			$('#taskErrorDiv').html("");
		  	 			$('#taskErrorDiv').hide();
		  	 			$('#errorDiv').html("");
		  	 			$('#errorDiv').hide();	  	 			 	 				  	 			  	 				  	
		  	}
		  //SLT-1168
		  	function validateCreditCardNumber(creditCardNumber){
		  		var isValidNumber = false;
		  		var isValid = false;
		  		var ccCheckRegExp = /[^\d ]/;
		  		var numberProduct;
		  		var numberProductDigitIndex;
		  		var checkSumTotal = 0;
		  		var cardNumber=null;
		  		
		  		var replaceNumbersThatMightContainACreditCard = creditCardNumber.replace(/[^0-9]/g, ' ');
		  		var validateCCRegx=replaceNumbersThatMightContainACreditCard.match("\\b(?:4[ -]*(?:\\d[ -]*){11}(?:(?:\\d[ -]*){3})?\\d|"
						+ "(?:5[ -]*[1-5](?:[ -]*\\d){2}|(?:2[ -]*){3}[1-9]|(?:2[ -]*){2}[3-9][ -]*"
						+ "\\d|2[ -]*[3-6](?:[ -]*\\d){2}|2[ -]*7[ -]*[01][ -]*\\d|2[ -]*7[ -]*2[ -]*0)(?:[ -]*"
						+ "\\d){12}|3[ -]*[47](?:[ -]*\\d){13}|3[ -]*(?:0[ -]*[0-5]|[68][ -]*\\d)(?:[ -]*"
						+ "\\d){11}|6[ -]*(?:0[ -]*1[ -]*1|5[ -]*\\d[ -]*\\d)(?:[ -]*"
						+ "\\d){12}|(?:2[ -]*1[ -]*3[ -]*1|1[ -]*8[ -]*0[ -]*0|3[ -]*5(?:[ -]*"
						+ "\\d){3})(?:[ -]*\\d){11})\\b");
			
			    if(validateCCRegx!=null){
				  cardNumber=validateCCRegx[0];
			     }
		  		if(cardNumber!=null){
		  			var cardNumberOnly=cardNumber.replace(/[^0-9]/g, "");
		  			isValidNumber = !ccCheckRegExp.test(cardNumberOnly);
		  			var cardNumberLength = cardNumberOnly.length;
		  			if (isValidNumber){
		  				for (digitCounter = cardNumberLength - 1; digitCounter >= 0; digitCounter--)
		  				{
		  					checkSumTotal += parseInt (cardNumberOnly.charAt(digitCounter));
		  					digitCounter--;
		  					numberProduct = String((cardNumberOnly.charAt(digitCounter) * 2));
		  					for (var productDigitCounter = 0;productDigitCounter < numberProduct.length; productDigitCounter++)
		  					{
		  						checkSumTotal += 
		  						parseInt(numberProduct.charAt(productDigitCounter));
		  					}
		  				}
		  				isValid = (checkSumTotal % 10 == 0);
		  			}
		  		}
		  		return isValid;
		  	} 
	
			//validations
		  	function validate(){
		  	
		  	clearPreviousErrors();
		  	 var skuCatName = jQuery.trim($('#skuCategoryName').val());
		  	 var skuCatDesc = jQuery.trim($('#skuCategoryDesc').val());
		  	 var skuName = jQuery.trim($('#sku').val());
		  	 var skuDescription = jQuery.trim($('#skuNameDesc').val());	 
		  	 var taskName = jQuery.trim($('#taskName').val());
		  	 var taskComments = jQuery.trim($('#taskComments').val());
		  	 var skuCatErrorMsg="";
		  	 var skuErrorMsg= "";
		  	 var otherErrorMSg = "";
		  	 var pricingErrorMsg = "";
		  	 var taskErrorMsg = "";
		  	 var success = true;	  	 
		  	 var chkBox = $("#manageInd").attr('checked');
		  	 
		  	 var act = '${modalTitle}';
	
		  	 if(skuCatName == "" || skuCatName == "Enter SKU Category Name"){
		  	 	skuCatErrorMsg = "Please provide the SKU Category Name.</br>";
		  	 }
		  	 if(skuCatDesc == "" || skuCatDesc == "Enter SKU Category Description"){
		  	 	skuCatErrorMsg = skuCatErrorMsg + "Please provide the SKU Category Description.</br>";
		  	 }else if(validateCreditCardNumber(skuCatDesc)){					
					skuCatErrorMsg = "Please don't enter the credit card number in SKU Category Description.<br/>";
			 }	
		  	 if(skuCatErrorMsg != ""){
		  	 	$('#skuCategoryErrorDiv').html(skuCatErrorMsg);
		  	 	$('#skuCategoryErrorDiv').show();
		  	 	success = false;
		  	 }
		  	 if(skuName == "" || skuName == "Enter SKU Name"){
		  	 	skuErrorMsg = "Please provide a valid SKU Name.</br>";
		  	 }
		  	 if(skuDescription == "" || skuDescription == "Enter SKU Description"){
		  	 	skuErrorMsg = skuErrorMsg + "Please provide the SKU Description.</br>";
		  	 }else if(validateCreditCardNumber(skuDescription)){				
				 skuErrorMsg = "Please don't enter the credit card number in SKU Description.<br/>";
			 }	
		  	 if(skuErrorMsg != ""){
		  	 	$('#skuErrorDiv').html(skuErrorMsg);
		  	 	$('#skuErrorDiv').show();
		  	 	success = false;
		  	 }
		  	 if(chkBox==true && ($('#retailPrice').val() == "" || $('#retailPrice').val() == "0.00")){
		  	 	pricingErrorMsg = pricingErrorMsg + "Retail Price is a mandatory field for SKUs which are displayed in Manage Scope widget. Please enter a valid, non-zero value for Retail Price.</br>";
		  	 }	  	 
		  	 if($('#bidPrice').val() == "" && $('#retailPrice').val() == "" && $('#bidMargin').val() == ""){
		  	 	pricingErrorMsg = pricingErrorMsg + "Please provide a value for the Maximum Price. Alternatively, to calculate the Maximum Price, please provide values for both Retail Price and Margin.</br>";
		  	 }
		  	 if($('#retailPrice').val() == "" && $('#bidMargin').val() != ""){
		  	 	pricingErrorMsg = pricingErrorMsg + "Please provide a value for the Maximum Price. Alternatively, to calculate the Maximum Price, please provide a value for the Retail Price.</br>";
		  	 }
		  	 if($('#retailPrice').val() != "" && $('#bidMargin').val() == ""){
		  	 	pricingErrorMsg = pricingErrorMsg + "Please provide a value for the Maximum Price. Alternatively, to calculate the Maximum Price, please provide a value for the Margin.</br>";
		  	 }
		  	 if(pricingErrorMsg != ""){
		  	 	$('#pricingErrorDiv').html(pricingErrorMsg);
		  	 	$('#pricingErrorDiv').show();
		  	 	success = false;
		  	 }
	
		  	 if($('#mainCategory').val() == "-1"){
		  	 	otherErrorMSg = "Please select a Main Category.</br>";
		  	 }
		  	 if($('#templateName').val() == "-1"){
		  	 	otherErrorMSg = otherErrorMSg + "Please select a Template for the selected Main Category. </br>";
		  	 }
		  	 if($('#orderItemType').val() == "-1"){
		  	 	otherErrorMSg = otherErrorMSg + "Please select the Order Item Type.</br>";
		  	 }
		  	 if(otherErrorMSg != ""){
		  	  	$('#otherErrorDiv').html(otherErrorMSg);
		  	 	$('#otherErrorDiv').show();
		  	 	success = false;
		  	 }
		  	 
		  	 if((taskName == "" || taskName == " Enter task name") || (taskComments == "" || taskComments == "Enter task comments") || $('#category').val() == "-1" || $('#subCategory').val() == "-1" || $('#skill').val() == "-1"){
		  	 	taskErrorMsg = "Please provide all mandatory data for task management.";
		  	 }else if(validateCreditCardNumber(taskComments)){					
				    taskErrorMsg = "Please don't enter the credit card number in taskComments.<br/>";
		  	 }
		  	 if(taskErrorMsg !=""){
		  	  	$('#taskErrorDiv').html(taskErrorMsg);
		  	 	$('#taskErrorDiv').show();	
		  	 	success = false;  	 
		  	 }
		  	 
		  	 //assigning values to hidden variables
		  	 		$("#selectedSkill").val($("#skill option:selected").val());
		  	 		$("#selectedtemplateId").val($("#templateName option:selected").val());	  	 		
		  	 		if($('#subCategory').val() == "0"){
		  	 		$("#nodeId").val($("#category option:selected").val());
		  	 		}
		  	 		else{
		  	 		$("#nodeId").val($("#subCategory option:selected").val());
		  	 		}
					$("#manageScopeInd").val(chkBox);
					
		  	 	  	 //check for existing sku category in case of add sku category
		  	 if(act == "Add SKU Category" && skuCatName != "Enter SKU Category Name" && skuCatName != ""){
		  	 	var updateUrl='existingCategory_validate.action?skuCategoryName='+encodeURIComponent(skuCatName);
		  	 				$.ajax({
		  			url: updateUrl,
			  		success: function( data ) {
			  		var result = jQuery.trim(data);
			  		if(result == 'fail'){
			  		skuCatErrorMsg = skuCatErrorMsg +"Choose different SKU category name as there is already an existing SKU Category with this name</br>";		  		
			  			$('#skuCategoryErrorDiv').html(skuCatErrorMsg);
		  	 			$('#skuCategoryErrorDiv').show();
		  	 			success = false;
			  		}		  		
		  	 		if(!success){
				  	$('#title').html("Error SKU Category");
		  	 		$('#errorDiv').html("Your changes could not be saved. Please enter all the mandatory data and try again.");
		  	 		$('#errorDiv').show();
					window.location.href = "#errorDiv";
					}
			  		}
			  		});
		  	 }
		  	 	  	 
		  	 //validation for existing sku
			if(skuName != "Enter SKU Name" && skuName != ""){
		  		var parentCategId = -1;
		  		 //Check element exists,then get the value
		  	     if ($("#skuCategory").length > 0){
		  	          parentCategId=$('#skuCategory').val();
		  	       }
		  		var currentSkuId = $('#skuNameByCategory').val();
		  		if(act == "Add SKU"){
		  		currentSkuId = "-1";
		  		} 
		  	 	var updateUrl='sku_validate.action?skuNameUpdated='+encodeURIComponent(skuName)+'&parentCategId='+parentCategId+'&currentSkuId='+currentSkuId;
		  	 				$.ajax({
		  			url: updateUrl,
			  		success: function( data ) {
			  		var result =jQuery.trim(data);
			  		if(result=='fail'){
					skuErrorMsg = skuErrorMsg +"Choose different SKU name as there is already an existing SKU with this name</br>";		  		
			  			$('#skuErrorDiv').html(skuErrorMsg);
		  	 			$('#skuErrorDiv').show();
		  	 			success = false;
			  		}
			  		if(success){
		  	 		$('#title').html(act);
		  	 		
		  	 	
		  			if($('#retailPrice').attr('disabled')==true) 
		  			{
		  			 $('#retailPrice').attr('disabled',false);
		  			$('#retailPrice').attr('readOnly',true);
		  			 
		  			}
		  			
		  			if($('#bidPrice').attr('disabled')==true) 
		  			{
		  			 $('#bidPrice').attr('disabled',false);
		  				$('#bidPrice').attr('readOnly',true);
		  			 
		  			}
		  			
		  			if($('#bidMargin').attr('disabled')==true) 
		  			{
		  			 $('#bidMargin').attr('disabled',false);
		  				$('#bidMargin').attr('readOnly',true);
		  			 
		  			}
		  		
		  			
		  	 		addorupdateSku();
		  	 		}
		  	 		else{
		  	 		if(act == "Add SKU"){
				  	$('#title').html("Error - Add SKU");
				  	}
				  	else if(act == "Update SKU"){
				  	$('#title').html("Error - Update SKU");
				  	}
		  	 		$('#errorDiv').html("Your changes could not be saved. Please enter all the mandatory data and try again.");
		  	 		$('#errorDiv').show();
					window.location.href = "#errorDiv";
					}		  				  		
			  		}
			  		});
		  	 }
			if(!success){
				if(act == "Add SKU Category"){
				  	$('#title').html("Error SKU Category");
				  	}
				 if(act == "Add SKU"){
				  	$('#title').html("Error - Add SKU");
				  	}
				 else if(act == "Update SKU"){
				  	$('#title').html("Error - Update SKU");
				  	}
		  	 	 $('#errorDiv').html("Your changes could not be saved. Please enter all the mandatory data and try again.");
		  	 	 $('#errorDiv').show();
				 window.location.href = "#errorDiv";
			}
	
		  	}
		  	
		  	//submit function
		  	function addorupdateSku()
	 		{
	 		var actn = '${modalTitle}';
				var skuCatId = $('#skuCategory').val();
				var skuNameId = $('#skuNameByCategory').val();
				if(actn=="Add SKU"){
					skuNameId = null;
				}
				if(actn =="Add SKU Category"){
					skuNameId = null;
					skuCatId = null;
				}
				var skuName = $('#sku').val();
				var categoryName = $('#skuCategoryName').val();		
				var formValues = $('#skuMaintenance').serialize();
				$("input#submitButton").replaceWith("<div><b>Adding SKU...<b> </div> ");
				$('#cancelLink').replaceWith("<span><u style='color: gray;'>Cancel<u></span>");
				$.getJSON('saveData.action?skuCatId='+skuCatId+'&skuNameId='+skuNameId, formValues, function(data) {
				//for add sku category - refresh the page after saving sku category
				if(actn =="Add SKU Category"){	
					window.location.href = "sku_maintenancePage.action";		
				}
				//for add sku to sku category and update sku-after persisting data,reload the page and highlight the newlyadded/changed data
				else{
					$("#skuNameDisplay").load("sku_maintenanceSkuName.action?categoryId="+skuCatId,
						function() {
						//to highlight the newly added sku
	
						$("#skuNameByCategory option").each(function() {
							if(jQuery.trim($(this).text()) == 'Please Select') {
	   							 $(this).removeAttr('selected');            
	  								}
	  						if(jQuery.trim($(this).text()) == jQuery.trim(skuName)) {					
	   							 $(this).attr('selected', 'selected');            
	  								}                        
								});
						
						$("#skuNameDisplay").show();
	       					 hideOptionsku();	
	       					 $("#skuNameByCategory").trigger('click');
	       					 $("#closeButton").trigger('click');	
	 						$("#successMsg").html("The changes made have been successfully updated.<br> Please note that it may take up to 24 hours for these changes to propagate through the system and come into effect.");
							$("#successMsg").show();      					 
						});
	
				
				}
				});
	 		}
	
			$('#cancelLink').click(function(){
				$("#closeButton").trigger('click');
			});
			
			//script to reset billing margin
			$('#resetBilling').click(function(){		
				$('#billingMargin').css('background-color','white');
				$('#billingMargin').css('border','1px solid grey');			
				$('#billingPrice').css('background-color','white');
				$('#billingPrice').css('border','1px solid grey');						
				$("#billingMargin").val("");
				$('#billingMargin').attr("disabled",false);
				$("#billingPrice").val("");
				$('#billingPrice').attr("disabled",false);
			});
			
			//script to reset bid margin
			$('#resetBid').click(function(){
				$('#retailPrice').css('background-color','white');
				$('#retailPrice').css('border','1px solid grey');									
				$('#bidMargin').css('background-color','white');
				$('#bidMargin').css('border','1px solid grey');									
			    $('#bidPrice').css('background-color','white');
			    $('#bidPrice').css('border','1px solid grey');								    
				$("#retailPrice").val("");
				$('#retailPrice').attr("disabled",false);
				$("#bidMargin").val("");
				$('#bidMargin').attr("disabled",false);
				$("#bidPrice").val("");
				$('#bidPrice').attr("disabled",false);
			});
		
			function displayInfo(id){
				if(id == 'retailPriceInfo'){
				$("#retailPriceInfoDiv").show();
			}
				if(id == 'priceTypeInfo'){
				$("#priceTypeInfoDiv").show();
			}
				if(id == 'bidInfo'){
				$("#bidInfoDiv").show();
			}
				if(id == 'billingInfo'){
				$("#billingInfoDiv").show();
			}					
	 		}
		
			function hideInfo(id){ 
				if(id == 'retailPriceInfo'){
				$("#retailPriceInfoDiv").hide();
			}
				if(id == 'priceTypeInfo'){
				$("#priceTypeInfoDiv").hide();
			}
				if(id == 'bidInfo'){
				$("#bidInfoDiv").hide();
			}
				if(id == 'billingInfo'){
				$("#billingInfoDiv").hide();
			}					
	 		}
			//script to allow only numeric characters in retailprice,bid margin,bid price,billing price,billing margin
			function isNumberKey(evt){
	       		var charCode = (evt.which) ? evt.which : event.keyCode
	       		if (charCode != 46 && charCode != 45 && charCode > 31 && (charCode < 48 || charCode > 57))
	            return false;
	            return true;
	     	}
	     	
	     	function limitText(limitField,limitNum) {
	        if (limitField.value.length > limitNum) {
	                limitField.value = limitField.value.substring(0, limitNum);
	        }
	        } 
	        
	
	$(document).keydown(function (e) {
		if (e.keyCode == 27){ 
		 $.modal.close();    
		} 
		 var elementId = e.target.id;
		 if (elementId == 'retailPrice' || elementId == 'bidMargin' || elementId == 'bidPrice' ||  elementId == 'billingMargin'  || elementId == 'billingPrice'
		      || elementId == 'skuCategoryName' || elementId == 'skuCategoryDesc' || elementId == 'sku' || elementId == 'skuNameDesc') 
		 {
			  if($(e.target).attr("readonly"))
			  {
			      if (e.keyCode === 8) {
			         // Preventing backspace event...   
			          return false;
			      }
			  }   
		 }
	});
	</script>
	<style>
	.fontStyleSmallSize {
		font-family: Arial;
		font-size: 10px;
	}
	</style>
	<fmt:formatNumber value="${buyerSkuDetails.retailPrice}"
		var="retailPriceTemp1" type="NUMBER" minFractionDigits="2"
		maxFractionDigits="2" pattern="#0.00" />
	<fmt:formatNumber value="${buyerSkuDetails.bidMargin*100}"
		var="bidMarginTemp1" type="NUMBER" minFractionDigits="2"
		maxFractionDigits="2" pattern="#0.00" />
	<fmt:formatNumber value="${buyerSkuDetails.bidPrice}"
		var="bidPriceTemp1" type="NUMBER" minFractionDigits="2"
		maxFractionDigits="2" pattern="#0.00" />
	<fmt:formatNumber value="${buyerSkuDetails.billingMargin*100}"
		var="billMarginVal1" type="NUMBER" minFractionDigits="2"
		maxFractionDigits="2" pattern="#0.00" />
	<fmt:formatNumber value="${buyerSkuDetails.billingPrice}"
		var="billPriceVal1" type="NUMBER" minFractionDigits="2"
		maxFractionDigits="2" pattern="#0.00" />
	
	<div id="addSkuCategory" class="modal" style="padding: 0px 0px;">
		<div class="modalHomepage" style="padding-bottom: 20px;padding-right: 10px">
			<div style="float: left;" id="title">
				${modalTitle}
			</div>
			<a href="javascript: void(0)" id="closeButton"
				class="btnBevel simplemodal-close" style="color: white; float: right;">
				<img src="${staticContextPath}/images/widgets/tabClose.png" alt="X">
			</a>
		</div>
		<div class="modalheaderoutline">
			<div class="rejectServiceOrderFrame"
				style="border: 0px;padding-left: 20px; padding-bottom: 10px; ">
				<div class="rejectServiceOrderFrameBody" style="width: 820px;">
					<s:form name="skuMaintenance" id="skuMaintenance" theme="simple"
						action="skuMaintenanceAction" method="saveData">
						<input type="hidden" name="selectedMainCategory"
							id="selectedMainCategory" />
						<input type="hidden" name="selectedtemplateId"
							id="selectedtemplateId" />
						<input type="hidden" name="selectedCategory" id="selectedCategory" />
						<input type="hidden" name="selectedSubCategory"
							id="selectedSubCategory" />
						<input type="hidden" name="selectedSkill" id="selectedSkill" />
						<input type="hidden" name="nodeId" id="nodeId" />
	
						<div id="errorDiv" class="skuErrorBox skuErrorMsg"
							style="display: none; height: 25px; padding-top: 10px; padding-left: 21px; background-color: #FBE3E4;"></div>
	
						<!-- START OF SKU DETAILS -->
						<div id="skuCategoryErrorDiv"
							style="display: none; color: #FE0000;"></div>
						</br>
	
						<fieldset style="width: 780px;">
	
							<legend style="font-size: 15px; color:#00A0D2;">
								&nbsp;
								<b>SKU Category</b>&nbsp;&nbsp;
							</legend>
	
	<br />
	
							<table>
								<tbody>
									<tr width="100%;">
										<td width="100%;">
											&nbsp;&nbsp;&nbsp;
											<label>
												<b>SKU Category Name <span class="req">*</span>
												</b>
											</label>
											&nbsp;&nbsp;&nbsp;&nbsp;
											<s:textfield id="skuCategoryName" name="skuCategoryName"
												size="30" theme="simple" value="Enter SKU Category Name"
												onfocus="clearDefaultText(this.id);"
												cssClass="fontStyleSmallSize" maxlength="30" />											
												<span id="maxCountValue" name="maxCountValue">
													<i>(Maximum 30 characters)</i>
												</span>
										</td>
									</tr>
									<tr height="5px;">
										<td></td>
									</tr>
									<tr>
										<td>
											&nbsp;&nbsp;&nbsp;
											<label>
												<b>SKU Category Description <span class="req">*</span>
												</b>
											</label>
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;&nbsp;&nbsp;
											<s:textarea id="skuCategoryDesc" name="skuCategoryDesc"
												cssClass="text" theme="simple"
												cssStyle="overflow: scroll;font-family:Arial;font-size:10px;"
												value="Enter SKU Category Description" cols="80"
												onfocus="clearDefaultText(this.id);countAreaChars(this.form.skuCategoryDesc, this.form.skuCategoryDesc_leftChars, 255, event);"
												onkeyup="limitText(this,255);countAreaChars(this.form.skuCategoryDesc, this.form.skuCategoryDesc_leftChars, 255, event);"
												onkeydown="limitText(this,255);countAreaChars(this.form.skuCategoryDesc, this.form.skuCategoryDesc_leftChars, 255, event);">
											</s:textarea>
										</td>
									</tr>
									<tr>
										<td id="charCount">
											&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="text" readonly="readonly" size="3" maxlength="3"
												value="255" name="skuCategoryDesc_leftChars"
												style="font-family: Arial; font-size: 10px; border: none; color: grey; font-style: italic"
												id="reasonComment_leftChars">
											<i>characters remaining</i>
										</td>
									</tr>
								</tbody>
							</table>
							</br>
						</fieldset>
						<!-- END OF SKU CATEGORY DETAILS -->
						<br>
						<div id="skuErrorDiv" style="display: none; color: #FE0000;"></div>
						</br>
						<!-- START OF SKU DETAILS -->
						<fieldset style="width: 780px;">
	
							<legend style="font-size: 15px; color:#00A0D2;">
								&nbsp;
								<b>SKU</b>&nbsp;&nbsp;
							</legend>
							<br />
							<table>
								<tr>
									<td>
										&nbsp;&nbsp;&nbsp;
										<label>
											<b>SKU Name <span class="req">*</span>
											</b>
										</label>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<c:if test="${empty buyerSkuDetails.sku}">
											<c:set var="skuName" value="Enter SKU Name"></c:set>
										</c:if>
										<c:if test="${not empty buyerSkuDetails.sku}">
											<c:set var="skuName" value="${buyerSkuDetails.sku}"></c:set>
										</c:if>
	
										&nbsp;&nbsp;&nbsp;
										<s:textfield id="sku" name="sku"
											cssStyle="font-family:Arial;font-size:10px;" theme="simple"
											value="%{#attr['skuName']}" onfocus="clearDefaultText(this.id);"
											maxlength="18" />
											<i>(Maximum 18 characters)</i>
									</td>
									<td style="padding-left: 50px;">
										<c:choose><c:when test="${buyerSkuDetails.manageScopeInd == 'true'}">
	
											<input type="checkbox" id="manageInd" checked="checked" />
											<b>Display in Manage Scope widget.</b>
											<input type="hidden" id="manageScopeInd" name="manageScopeInd" />
	
										</c:when>
										<c:when test="${isNonFundedBuyer=='true'}">
										    <input type="checkbox" id="manageInd" disabled="true"/>
											   <b>Display in Manage Scope widget.</b>
											<input type="hidden" id="manageScopeInd" name="manageScopeInd" />
										</c:when>
										<c:otherwise>
											<input type="checkbox" id="manageInd" />
											<b>Display in Manage Scope widget.</b>
											<input type="hidden" id="manageScopeInd" name="manageScopeInd" />
										</c:otherwise></c:choose>
									</td>
								</tr>
								<tr height="5px;">
									<td></td>
								</tr>
								<tr>
	
									<td>
										&nbsp;&nbsp;&nbsp;
										<label>
											<b>SKU Description <span class="req">*</span>
											</b>
										</label>
									</td>
								</tr>
								<tr>
									<td>
										<c:if test="${empty buyerSkuDetails.skuDescription}">
											<c:set var="skuDesc" value="Enter SKU Description"></c:set>
										</c:if>
										<c:if test="${not empty buyerSkuDetails.skuDescription}">
											<c:set var="skuDesc" value="${buyerSkuDetails.skuDescription}"></c:set>
										</c:if>
										&nbsp;&nbsp;&nbsp;
										<s:textarea id="skuNameDesc" name="skuNameDesc" cssClass=""
											cssStyle="overflow: scroll;font-family:Arial;font-size:10px;"
											theme="simple" value="%{#attr['skuDesc']}" cols="80"
											onkeyup="limitText(this,255);countAreaChars(this.form.skuNameDesc, this.form.skuDesc_leftChars, 255, event);"
											onkeydown="limitText(this,255);countAreaChars(this.form.skuNameDesc, this.form.skuDesc_leftChars, 255, event);"
											onfocus="clearDefaultText(this.id);clearDefaultText(this.id);countAreaChars(this.form.skuNameDesc, this.form.skuDesc_leftChars, 255, event);">
										</s:textarea>
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="text" readonly="readonly" size="3" maxlength="3"
											value="255" name="skuDesc_leftChars"
											style="font-family: Arial; font-size: 10px; border: none; color: grey; font-style: italic"
											id="skuDesc_leftChars">
										<i>characters remaining</i>
	
									</td>
								</tr>
							</table>
							</br>
						</fieldset>
	
						<br>
	
						<fieldset style="width: 780px;">
							<legend style="font-size: 15px; color:#00A0D2;">
								&nbsp;
								<b>SKU Details</b>&nbsp;&nbsp;
							</legend>
							<!-- START OF MAINCAT|TEMPLATE|ORDERITEMTYPE -->
							<div id="otherErrorDiv" style="display: none; color: #FE0000;"></div>
							<table width="50%">
								<tr>
									<td style="padding-top: 10px; padding-left: 10px;">
										<label>
											<b>Main Category</b>
											<span class="req">*</span>
										</label>
									</td>
									<td style="padding-top: 10px;"></td>
									<td style="padding-top: 10px; padding-left: 50px;">
										<label>
											<b>Template Name&nbsp;<span class="req">*</span>
											</b>
										</label>
									</td>
								</tr>
								<tr>
									<td style="padding-top: 1px; padding-left: 10px;">
										<select name="mainCategory" id="mainCategory"
											style="width: 225px;">
											<option value="-1">--Select One--</option>
											<c:forEach var="mainCat" items="${mainCategoryList}">
												<c:choose><c:when test="${not empty buyerSkuDetails}">
													<c:forEach var="bskuTasks"
														items="${buyerSkuDetails.buyerSkuTasks}">
														<c:choose><c:when
															test="${bskuTasks.skillTree.rootNodeId == mainCat.nodeId}">
															<option value="${mainCat.nodeId}" selected="selected">${mainCat.nodeName}</option>
														</c:when>
														<c:otherwise>
															<option value="${mainCat.nodeId}">${mainCat.nodeName}</option>
														</c:otherwise></c:choose>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<option value="${mainCat.nodeId}">${mainCat.nodeName}</option>
												</c:otherwise></c:choose>
											</c:forEach>
	
										</select>
									</td>
									<td style="padding-top: 10px;"></td>
									<td style="padding-top: 1px; padding-left: 50px;">
										<select name="templateName" id="templateName"
											style="width: 225px;">
											<option value="-1">--Select One--</option>
										</select>
									</td>
								</tr>
								<tr>
									<td style="padding-top: 10px; padding-left: 10px;">
										<label>
											<b>Order Item Type&nbsp;<span class="req">*</span>
											</b>
										</label>
									</td>
								</tr>
								<tr>
									<td style="padding-top: 1px; padding-left: 10px;">
										<select name="orderItemType" id="orderItemType"
											style="width: 225px;">
											<option value="-1">--Select One--</option>
											<option value="LABOR">Labor</option>
											<!-- changing other as parts and material as part of SLT-2469 -->
											<option value="ADD_ON_PARTS_AND_MATERIAL">Add On Parts and Material</option>
											<option value="PART">Part</option>
											<option value="PERMIT">Permit</option>
										</select>
									</td>
									<br>
								</tr>
	
							</table>
							<br>
							<div id="pricingErrorDiv" style="display: none; color: #FE0000;"></div>
							</br>
							<!-- END OF MAINCAT|TEMPLATE|ORDERITEMTYPE -->
	
							<!-- START OF PRICING SECTION -->
							<fieldset style="margin-left: 10px; margin-right: 10px;">
	
								<legend style="font-size: 15px; color:#00A0D2;">
									&nbsp;
									<b>Pricing & Margin</b>&nbsp;&nbsp;
								</legend>
	
								<table style="padding-left: 10px;" width="80%">
									<tr>
										<td width="25%" style="padding-top: 15px;">
											<table
												style="width: 250px; height:70px; border-color: #111111; padding-left: 10px;"
												border="1" cellspacing="4"  >
												<!-- <tr>
				<td style="padding: 10px">
				<label><b>Price Type <span class="req">*</span></b></label>
				<select name="priceType" id="priceType" style="float:right;">
					<option value="-1">--Select One--</option>
					<option value="FIXED">Fixed</option>
					<option value="VARIABLE">Variable</option>
				</select>
				</td>
				<td style="padding-top: 10px;">
				<a class="" href="javascript:void(0);"><img id="priceTypeInfo" class=""
														src="${staticContextPath}/images/icons/sku_info.gif" onmouseover="displayInfo(this.id);" onmouseout="hideInfo(this.id);"/>  </a>
														
				<div id="priceTypeInfoDiv moreInfoStyle" style="display: none; left: 280px; top: 500px;z-index: 20;">${priceInfo}</div>											
				</td>			
				 </tr>-->
												<tr>
													<td
														style="padding-bottom: 2px; padding-top: 2px; border-style: none;">
														<label>
															<b>Retail Price&nbsp;($)</b>
														</label>
														<c:choose>
														 <c:when test="${isNonFundedBuyer}">
														   <s:textfield id="retailPrice" name="retailPrice"
															  theme="simple" value="0.00" disabled="true"
															  cssStyle="width:30%;margin-left:40px;font-family:Arial;font-size:10px;"/>
														 </c:when>
														 <c:otherwise>
														    <s:textfield id="retailPrice" name="retailPrice"
															theme="simple" value="%{#attr['retailPriceTemp1']}" maxlength="10"
															cssStyle="width:30%;margin-left:40px;font-family:Arial;font-size:10px;"
															onkeypress="return isNumberKey(event);"
															onblur="fnCalculateBid(this);" />
														 </c:otherwise>
														</c:choose>
													</td>
	
												</tr>
												<tr>
													<td style="padding-top: 10px; border-style: none;"
														height="10px">
														&nbsp;
													</td>
												</tr>
											</table>
											<span> <a href="javascript:void(0);" tabindex="-1"><img
														id="retailPriceInfo"
														src="${staticContextPath}/images/icons/sku_info.gif"
														onmouseover="displayInfo(this.id);"
														onmouseout="hideInfo(this.id);" style="position: relative; top: -78px; right: -240px;" /> </a> </span>
											<div id="retailPriceInfoDiv" class="moreDetails moreInfoStyle"
												style="display: none; position:absolute; left: 320px; top: 450px; z-index: 9999;">
												${retlPriceInfo}
											</div>
										<td width="8%">
											&nbsp;&nbsp;
										</td>
										<td width="25%" style="padding-top: 15px;">
											<table cellspacing="4" id="bid" border="1"
												style="border-color: #111111; padding-left: 10px; width: 230px;height:70px;">
												<tr>
													<td
														style="border-style: none; padding-bottom: 2px; padding-top: 2px;" width="60%">
														<label>
															<b>Margin</b>
														</label>
														<b>(%)</b>
													 </td>
													 <td style="border-style: none;" width="40%">
													   <c:choose>
													    <c:when test="${isNonFundedBuyer}">
													        <input type="text" style="font-family:Arial;font-size:10px;width:80%;" 
													          id="bidMargin" disabled="disabled" value="0.00" size="2" name="bidMargin">
															 </c:when>
													    <c:otherwise>
														   <s:textfield id="bidMargin" name="bidMargin" maxlength="9"
															  theme="simple" value="%{#attr['bidMarginTemp1']}" size="10"
															  cssStyle="font-family:Arial;font-size:10px;width:80%;"
															  onkeypress="return isNumberKey(event);"
															  onblur="fnCalculateBid(this);" />
														</c:otherwise>
														</c:choose>
													</td>
												</tr>
												<tr>
													<td style="border-style: none; padding-bottom: 2px;"
														height="25px">
														<label>
															<b>Maximum Price&nbsp;</b>
														</label>
														<b>($) <span class="req">*</span>
														</b>
													</td>
													<td style="border-style: none;">	
													   <c:choose>
													      <c:when test="${isNonFundedBuyer}">
													            <s:textfield id="bidPrice" name="bidPrice" 
															        theme="simple" value="0.00" disabled="true"
															        cssStyle="width:80%;font-family:Arial;font-size:10px;" />
													      </c:when>
													      <c:otherwise>
														    <s:textfield id="bidPrice" name="bidPrice" maxlength="10"
															    theme="simple" value="%{#attr['bidPriceTemp1']}" size="10"
															    cssStyle="width:80%;font-family:Arial;font-size:10px;"
															    onkeypress="return isNumberKey(event);"
															    onblur="fnCalculateBid(this);" />
														  </c:otherwise>
													   </c:choose>
													</td>
												</tr>
											</table>
											<span><a id="resetBid" href="javascript:void(0);"
												style="float: right;" tabindex="-1"><b>Reset</b>
											</a>
											</span>
											<span> <a href="javascript:void(0);" tabindex="-1"><img
														id="bidInfo" class=""
														src="${staticContextPath}/images/icons/sku_info.gif"
														onmouseover="displayInfo(this.id);"
														onmouseout="hideInfo(this.id);" style="position: relative; top: -78px; right: -220px;" /> </a> </span>
											<div id="bidInfoDiv" class="moreDetails moreInfoStyle"
												style="display: none; left: 570px; top: 475px; z-index: 20;">
												${bidInf}
											</div>
										</td>
										<td width="8%">
											&nbsp;&nbsp;
										</td>
										<td width="25%" style="padding-top: 15px;">
											<c:choose><c:when test="${SecurityContext.autoACH == false}">
												<table cellspacing="4" id="billing" border="1"
													style="border-color: #111111; padding-left: 10px; width: 230px;height:70px;">
													<tr>
														<td
															style="border-style: none; padding-bottom: 2px; padding-top: 2px;" width="60%">
															<label>
																<b>Billing Margin</b>
															</label>
															<b>(%)</b>
														</td>
														<td style="border-style: none;">
														  <c:choose>
														    <c:when test="${isNonFundedBuyer}">
														        <s:textfield id="billingMargin" name="billingMargin"
																    theme="simple" value="0.00" disabled="true"
																    cssStyle="width:80%;font-family:Arial;font-size:10px;" />
														    </c:when>
														    <c:otherwise>
															<s:textfield id="billingMargin" name="billingMargin"
																maxlength="9" theme="simple" value="%{#attr['billMarginVal1']}"
																onkeypress="return isNumberKey(event);" size="10"
																cssStyle="width:80%;font-family:Arial;font-size:10px;"
																onblur="fnCalculateBilling(this);" />
															</c:otherwise>
														  </c:choose>
														</td>
													</tr>
													<tr>
														<td style="border-style: none; padding-bottom: 2px;"
															height="25px">
															<label>
																<b>Billing Price&nbsp;</b>
															</label>
															<b>($)</b>
														</td>
														<td style="border-style: none;">
														    <c:choose>
														      <c:when test="${isNonFundedBuyer}">
														         <s:textfield id="billingPrice" name="billingPrice"
																  maxlength="10" theme="simple" value="0.00" disabled="true"
																  cssStyle="width:80%;font-family:Arial;font-size:10px;"
																  />
														      </c:when>
														      <c:otherwise>
															   <s:textfield id="billingPrice" name="billingPrice"
																  maxlength="10" theme="simple" value="%{#attr['billPriceVal1']}"
																  onkeypress="return isNumberKey(event);" size="10"
																  cssStyle="width:80%;font-family:Arial;font-size:10px;"
																  onblur="fnCalculateBilling(this);" />
														      </c:otherwise>
															</c:choose>
														</td>
													</tr>
												</table>
												<span><a id="resetBilling" href="javascript:void(0);"
													style="float: right;" tabindex="-1"><b>Reset</b>
												</a>
												</span>
												<span> <a href="javascript:void(0);" tabindex="-1"><img
														id="billingInfo" class=""
														src="${staticContextPath}/images/icons/sku_info.gif"
														onmouseover="displayInfo(this.id);"
														onmouseout="hideInfo(this.id);" style="position: relative; top: -78px; right: -220px;" /> </a> </span>
											<div id="billingInfoDiv" class="moreDetails moreInfoStyle"
												style="display: none; left: 525px; top: 470px; z-index: 20;">
												${billingInf}
											</div>
										</td>
										<td width="8">
											&nbsp;&nbsp;
										</td>
										</c:when>
										<c:otherwise>
											&nbsp;&nbsp;
										</c:otherwise></c:choose>
										<td width="1">
										</td>
									</tr>
								</table>
								</br>
							</fieldset>
							<br>
							<div id="taskErrorDiv" style="display: none; color: #FE0000;"></div>
							<!-- END OF PRICING SECTION -->
							</br>
							<!--START OF TASKS SECTION -->
							<fieldset style="width: 95%; margin-left: 10px;margin-right: 10px;">
	
								<legend style="font-size: 15px; color:#00A0D2;">
									&nbsp;<b>Task Management</b>&nbsp;&nbsp;
								</legend>
								<br />
								<table style="padding-left: 0px; width: 98%;">
									<tr>
										<td style="padding-left: 12px; width: 40%;">
											<label>
												<b>Category <span class="req">*</span>
												</b>
											</label>
										</td>
										<td style="padding-left: 12px; width: 40%;">
											<label>
												<b>Sub Category<span class="req">*</span>
												</b>
											</label>
										</td>
										<td style="padding-left: 12px; width: 20%;">
											<label>
												<b>Skill<span class="req">*</span>
												</b>
											</label>
										</td>
									</tr>
									<tr>
	
										<c:choose><c:when test="${empty buyerSkuDetails}">
											<c:set var="tname" value=" Enter task name"></c:set>
											<c:set var="tcomments" value="Enter task comments"></c:set>
										</c:when>
										<c:otherwise>
											<c:forEach var="taskDetails"
												items="${buyerSkuDetails.buyerSkuTasks}">
												<c:if test="${not empty taskDetails.taskName}">
													<c:set var="tname" value="${taskDetails.taskName}"></c:set>
												</c:if>
												<c:if test="${not empty taskDetails.taskComments}">
													<c:set var="tcomments" value="${taskDetails.taskComments}"></c:set>
												</c:if>
											</c:forEach>
										</c:otherwise></c:choose>
										<td
											style="padding-left: 10px; padding-top: 0px; text-indent: 0px;">
											<select name="category" id="category" style="width: 173px;">
												<option value="0">--Select One--</option>
											</select>
										</td>
										<td style="padding-left: 10px;">
											<select name="subCategory" id="subCategory"
												style="width: 168px;">
												<option value="0">--Select One--</option>
											</select>
										</td>
										<td style="padding-left: 10px;">
											<select name="skill" id="skill" style="width: 107px;">
												<option value="0">--Select One--</option>
											</select>
										</td>
									</tr>
									<tr>
										<td height="2px;"></td>
									</tr>
									<tr>
										<td style="padding-left: 12px;">
											<label>
												<b>Task Name</b><span class="req">*</span>
											</label>
										</td>
									</tr>
									<tr>
										<td style="padding-left: 10px;" colspan="2">
											<s:textfield id="taskName" name="taskName" theme="simple"
												value="%{#attr['tname']}"
												cssStyle="font-family:Arial;font-size:10px;width:225px;"
												onkeyup="limitText(this,35);" onkeydown="limitText(this,35);"
												onfocus="clearDefaultText(this.id);" />
											<i>(Maximum 35 characters)</i>
										</td>
									</tr>
									<tr>
										<td height="2px;"></td>
									</tr>
									<table style="padding-left: 0px; width:98%;">
										<tr>
											<td style="padding-left: 12px;">
												<label>
													<b>Task Comments</b><span class="req">*</span>
												</label>
											</td>
										</tr>
										<tr height="2px"><td></td></tr>	
										<tr>
											<td style="padding-left: 12px;">
												<s:textarea id="taskComments" name="taskComments" cols="125"
													rows="10" wrap="HARD" cssClass="text" theme="simple"
													value="%{#attr['tcomments']}"
													cssStyle="border-color:grey grey grey grey;overflow: scroll;font-family:Arial;font-size:10px;height: 100px;width:700px;"
													/>
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;&nbsp;&nbsp;
												<input type="text" readonly="readonly" size="4" maxlength="4"
													value="5000" name="taskComments_leftChars"
													style="font-family: Arial; font-size: 10px; border: none; color: grey; font-style: italic"
													id="taskComments_leftChars">
												<i>characters remaining</i>
											</td>
										</tr>
									</table>
								</table>
								</br>
							</fieldset>
							<!--END OF TASKS SECTION -->
							<br>
							<br>
						</fieldset>
						<table>
							<tr>
								<td style="padding-left: 33px; padding-top: 29px;">
									<u style="color: red;"> <a href="#" id="cancelLink"
										style="float: left; padding-right: 18px; color: red;"
										class="btnBevel simplemodal-close"><u style="color: red;"><b>Cancel</b>
										</u>
									</a></b> <span style="float: left;"> </span>
								</td>
								<td style="padding-left: 600px; padding-top: 24px;">
									<input id="submitButton" type="button" value="Submit"
										style="float: right;" class="action submit button"
										onclick="tinyMCE.triggerSave();validate();" />
								</td>
							</tr>
						</table>
	
					</s:form>
				</div>
			</div>
		</div>
	</div>
