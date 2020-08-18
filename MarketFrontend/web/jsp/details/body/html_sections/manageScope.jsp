
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/buttons.css">

<style type="text/css">
	.secondRow {background-color: #C2BEBF;}
	.thirdRow {	background-color: #DBD9DA;}
	.evenRow { background-color: #F6F6F6;}
	#addTaskStep1 {	background-color: #FAF9C3;}
	.ui-dialog-titlebar-close {	float: right}
	table#manageScopeTable td {font-size: 9pt;}
	table#manageScopeTable{table-layout: fixed;height: 490px;}
	#scopeChangeComments{height : 30px}
	#taskComments{height : 30px}
	table#taskList {padding: 5px;}
	table#taskList td {font-size: 8pt;}
	#taskListHdr td {font-size: 10pt;font-weight: bold;}
	#taskSummary tr {padding: 5px;}
	#completeTaskNotice { font-size: 7pt; text-align: justify; padding: 0 15px}
	.mngScopeHeaders { font-size: 10pt; font-weight: bold; background-color: #FAF9C3;}
	input {text-align:right;} 
	input#taskName{text-align:left;} 
	.priceMsg{color:red;font-size: 7pt;}
	.priceI {width : 60px}
	.priceF {width : 20px}
	textarea { resize: none;}
</style>

<script type="text/javascript">
	var taskId = 0;
	var saveClicked = false;
	var addTaskClicked = false;
	$("#skuId").focus();
	lockFields();
	
	function clearErrors(){
		$("#E1").html("");$("#E1").hide("");
		$("#E2").html("");$("#E2").hide("");
		$("#E3").html("");$("#E3").hide("");
		$("#E4").html("");$("#E4").hide("");
		clearPriceWarning();
	}
	
	function clearPriceWarning(){
		$('#priceWarning').hide();
        $('#priceLabel').css({'color':'black'});
	}
	
	$('#priceInteger').focus(function(){
		 $('#priceWarning').hide();
	});	
	
	$('#priceInteger').blur(function(){	
	$('#priceInteger').val($('#priceInteger').val().replace(/[^0-9]/g, '')); 
		if($('#priceInteger').val()>99){
			$('#priceWarning').show();
		}	
	});	
	
	$('#priceFraction').focus(function(){
		// $('#priceWarning').hide();
	});	
	
	$('#priceFraction').blur(function(){		
		$('#priceFraction').val($('#priceFraction').val().replace(/[^0-9]/g, ''));
		var fractionVal = $('#priceFraction').val();
		if($('#priceFraction').val().length==1){
			$('#priceFraction').val(fractionVal+'0');
		}
	});
	$('#priceInteger').change(function(){ 
  	  if($(this).val()>99){
    	    $('#priceWarning').show();
        	$('#priceLabel').css({'color':'red'});
	    }else {
    	   clearPriceWarning();
    	}
	});
	$('#prevCancel').click(function(){
		$("#cancellationInfo").css("display","block");
		$("#manageScopeDiv").css("display","none");
	});
	
	$('#addTask').click(function(){
		var soId = '${soId}';
 		if(addTaskClicked == false){
 			addTaskClicked = true;
 			taskId++;
 			clearErrors();
 			var formData = $('#addTaskForm').serialize();
			$.ajax({
	  			url: 'manageScope_addTask.action?id='+taskId+'&soId='+soId,
	  			data : formData,
	  			dataType : "json",
		  		success: function( data ) {
		  			if(data.taskErrors != ""){
	  					$.each(data.taskErrors, function(index,value){
	  						$('#'+value.msgType).append('<p class="errorMsg">'+value.msg+'</p>');
	  						$('#'+value.msgType).show();
	  					});
	  				} else {
	  				
	  				$("#skuId").html('<option value="-1" selected="selected">Select SKU</option>');	  				

	  				<c:choose>
	  				<c:when test="${skuSkillForCategory != null && not empty skuSkillForCategory}">
						<c:forEach var="listOuter" items="${skuSkillForCategory}">
							 <c:forEach var="listInner" items="${listOuter}">  
              					 var id=${listInner.key};
               					$("#skuId").append('<option value="'+id+'" selected><c:out value="${listInner.value}" /></option>');
               
                 			</c:forEach>  
       					 </c:forEach> 
			
	  					<c:if test="${skuSkill != null && not empty skuSkill}">
							$("#skuId").append('<option value="more">More...</option>');							
						</c:if>
					</c:when>
					<c:otherwise>
						<c:choose>
						<c:when test="${skuSkill != null && not empty skuSkill}">
							$("#skuId").append('<option value="more">More...</option>');
						</c:when>
						<c:otherwise>
							$("#skuId").append('<option value="0">NA</option>');								
						</c:otherwise>
						</c:choose>
					</c:otherwise>
					</c:choose>
	  				$("#skuId").val(-1);			
	  				
	  					var id = data.id;
	    				$('#taskList').append('<tr id="taskRowId-'+id+'" style="height: 20px">'+
								'<td onclick="deleteTask('+id+')" width="5%"><img src="${staticContextPath}/images/widgets/cross.png"/></td>'+
								'<td width="50%"><div id="taskNameCol-'+id+'">'+data.taskName+'</div></td>'+
								'<td align="right">'+
									'$<input type="text" maxlength="7" size="6" id="priceInteger-'+id+'" class="priceI" name="priceInteger" value="'+data.priceInteger+'" onkeypress="return isNumberKey(event);" onchange="updateTaskPrice('+id+')"/><b>.</b>'+
									'<input type="text" maxlength="2" size="2" id="priceFraction-'+id+'" class="priceF" name="priceFraction" value="'+data.priceFraction+'" onkeypress="return isNumberKey(event);" onchange="updateTaskPrice('+id+')"/>'+
								'</td>'+
							'</tr>'
						);
						
						$('#priceInteger-'+id).focus(function(){
							$('#taskWarning-'+id).hide();
						});
						$('#priceFraction-'+id).blur(function(){
							var fractionVal = $('#priceFraction-'+id).val();							
							if($('#priceFraction-'+id).val().length==1){
								$('#priceFraction-'+id).val(fractionVal+'0');
							}else if($('#priceFraction-'+id).val()==""){
								$('#priceFraction-'+id).val('00')
							}
						}); 
						
						$('#priceInteger-'+id).blur(function(){
							if($('#priceInteger-'+id).val() > 99){
								if($("#taskWarning-"+id).length>0){
									$("#taskWarning-"+id).show();
								}else{
									$('<div class="priceMsg" id="taskWarning-'+id+'">Warning, this task is over $99.99</div>').insertAfter('#taskNameCol-'+id);							
								}
							}						
	  					});	  					
						
						if(data.priceInteger > 99){
							if($("#taskWarning-"+id).length>0){
								$("#taskWarning-"+id).show();
							}else{
								$('<div class="priceMsg" id="taskWarning-'+id+'">Warning, this task is over $99.99</div>').insertAfter('#taskNameCol-'+id);							
							}							
	  					}
						$('#labourPrice').text('$'+data.labourPrice.toFixed(2));
						$('#maxLabourPrice').val(data.labourPrice);
						$('#totalPrice').text('$'+data.totalPrice.toFixed(2));
						$('#maxTotalPrice').val(data.totalPrice);
						clearForm();
						document.getElementById('tskCmntCntr').innerHTML = 600;
					}
					addTaskClicked = false;			
	  			}
			});
		}
	});

	function deleteTask(id){
		clearErrors();
		var maxLabour = $('#maxLabourPrice').val();
		var maxTotal = $('#maxTotalPrice').val();
		var soId = '${soId}';
		$.ajax({
	  		url: 'manageScope_deleteTask.action?id='+id+'&maxLabourPrice='+maxLabour+'&maxTotalPrice='+maxTotal+'&soId='+soId,
	  		dataType : "json",
	  		success: function( data ) {
	  			if(data.taskErrors != ""){
	  				$.each(data.taskErrors, function(index,value){
	  					$('#'+value.msgType).append('<p class="errorMsg">'+value.msg+'</p>');
	  				});
	  			} else {
	  				$('#taskRowId-'+id).remove();
	  				$('#labourPrice').text('$'+data.labourPrice.toFixed(2));
					$('#maxLabourPrice').val(data.labourPrice);
					$('#totalPrice').text('$'+data.totalPrice.toFixed(2));
					$('#maxTotalPrice').val(data.totalPrice);
	  			}
	  		}
	  	});
	}
	
	$('#skuId').change(function(){
		clearPriceWarning();
		var skuId = $(this).val();
		var soMainCatId = $("#soMainCategory").val();
		$('#sku').val(skuId);
		if(skuId == -1){
			clearForm();
			return;
		}
		
if(skuId == 'more'){

$("#skuId >option[value='more']").remove();

			<c:forEach items="${skuSkill}" var="skuCatVar">
	  					  var id=${skuCatVar.skuId};  		             
				
				$("#skuId").append('<option value="'+id+'" selected><c:out value="${skuCatVar.sku}" /></option>');
				</c:forEach>
				$('#skuId').val(-1);
				clearForm();
				return;
		}
		
		
		
		$.ajax({
	  		url: 'manageScope_selectSku.action?skuId='+skuId+'&soMainCatId='+soMainCatId,
	  		dataType : "json",
	  		success: function( data ) {
	  			if(data.taskErrors != ""){
	  				$.each(data.taskErrors, function(index,value){
	  					$('#'+value.msgType).append('<p class="errorMsg">'+value.msg+'</p>');
	  				});
	  			} else {
	  				$("#skill").html('<option value="-1">Select Skill</option>');
	  				if (skuId != -1){
	  					$.each(data.mainCategorySkills, function(index,value){
	  						if(value.descr==data.skill){
	  							$("#skill").append('<option value="'+value.id+'" selected>'+value.descr+'</option>');
	  						}else{
	  							$("#skill").append('<option value="'+value.id+'">'+value.descr+'</option>');
	  						}
		  					
		  				});
		  				$("#hiddenSkill").val(data.skill);  					
		  				$("#serviceType").val(data.serviceTypeTemplateId);
	  				}
	  				if(skuId == 0){
	  					$('#skillNodeId').val(data.skillNodeId);
	  					$('#tskCmntCntr').html('600');
	  					unlockField();
	  				}else if (skuId != -1){
		  				$('#sku').val(data.sku);
		  				$('#priceInteger').val(data.priceInteger);
		  				$('#priceFraction').val(data.priceFraction);
		  				$('#taskName').val(data.taskName);
						$('#taskComments').val(data.taskComments);
						$('#skillNodeId').val(data.skillNodeId);
						$('#tskCmntCntr').html(600 - data.taskComments.length);
						unlockField();
					}
	  			}
	  		}
	  	});
	});
	
	function clearForm(){
		$('#skuId').val('-1');
		$("#skill").html('<option value="-1">Select Skill</option>');
		$('#serviceType').val(-1);
		$('#priceInteger').val('');
	  	$('#priceFraction').val('');
	  	$('#taskName').val('');
		$('#taskComments').val('');
		$('#sku').val('-1');
		$("#skillNodeId").val('-1')
		clearPriceWarning();
		lockFields();
	}
	
	function isNumberKey(evt)
    {
    	if (!evt) evt = window.event;
        var charCode = (evt.which) ? evt.which : event.keyCode
        if(evt.shiftKey)
         return false;
        if (charCode > 31 && (charCode < 48 || charCode > 57)){
         switch(charCode){
           case 8:
           case 9:           
           case 37:
           		return true;
           default:
           		return false;
         }
          
        }
        return true;
       
     }
     
     function updateTaskPrice(id){
     	 var soId = '${soId}';
     	 if($('#actionInd').val()=='true'){
     	 	return false;
     	 }else{
     	 	$(".priceI").attr('disabled','true');
     	 	$(".priceF").attr('disabled','true');      	 	
     	 	$('#actionInd').val('true');
     	 } 
        // $('#checkReleaseAndPay').attr("disabled", "disabled");
         var maxLabour = $('#maxLabourPrice').val();         
		 var maxTotal = $('#maxTotalPrice').val();
		 
		 $('#priceInteger-'+id).val($('#priceInteger-'+id).val().replace(/[^0-9]/g, ''));
		 $('#priceFraction-'+id).val($('#priceFraction-'+id).val().replace(/[^0-9]/g, ''));
		 
		 var priceInteger = $('#priceInteger-'+id).val();
		 var priceFraction = $('#priceFraction-'+id).val();
		 
		 if(priceInteger==""){
         	$('#priceInteger-'+id).val('0');
         }
         if(priceFraction=="" || priceFraction=='0'){
         	$('#priceFraction-'+id).val('00');
         }
		 $.ajax({
		    url: 'manageScope_updateTaskPrice.action?id='+id+'&maxLabourPrice='+maxLabour+'&maxTotalPrice='+maxTotal+'&priceInteger='+priceInteger+'&priceFraction='+priceFraction+'&soId='+soId,
	  		dataType : "json",
	  		success: function( data ) {
	  			if(data.taskErrors != ""){
	  				$.each(data.taskErrors, function(index,value){
	  					$('#'+value.msgType).append('<p class="errorMsg">'+value.msg+'</p>');
	  					});
	  			} else {
	  				
	  				if(priceInteger > 99){
	  					if($("#taskWarning-"+id).length>0){
	  						$("#taskWarning-"+id).show();
	  					}else{
  							$('<div class="priceMsg" id="taskWarning-'+id+'">Warning, this task is over $99.99</div>').insertAfter('#taskNameCol-'+id);				
	  					} 						
	  				}
	  				$('#labourPrice').text('$'+data.labourPrice.toFixed(2));
					$('#maxLabourPrice').val(data.labourPrice);
					$('#totalPrice').text('$'+data.totalPrice.toFixed(2));
					$('#maxTotalPrice').val(data.totalPrice);
					
	  			}
	  			   //  $('#checkReleaseAndPay').attr("disabled","");
	  			     $(".priceI").removeAttr('disabled');
     	 			 $(".priceF").removeAttr('disabled');
	  			     $('#actionInd').val('false');
	  		}
	  	});
	  	
     }
     $("#providerApprove").click(function(){
     	if($("#providerApprove").is(":checked")){
     		$('#checkReleaseAndPay').val("NEXT");
     		$("#providerApproveIndicator").val('true');
     		
     	}else{
     		$('#checkReleaseAndPay').val("Submit Cancellation");
     		$("#providerApproveIndicator").val('false');
     	}
     });

     
     /*Cancellation*/
     $('#checkReleaseAndPay').click(function(){
     		var soId = '${soId}';
     	    clearErrors();
     	   // var maxTotal = $('#maxTotalPrice').val();
     	   //Update total price when form is submitted.
     	   	var maxTotal = updateTotalPrice();
     	   	$('#maxTotalPrice').val(maxTotal);
     	    var formData = $('#manageScopeForm').serialize();
     		$.ajax({
	  			url: 'manageScope_saveChanges.action?fromCancel=true&totalPrice='+maxTotal+'&soId='+soId,
	  			data : formData,
	  			dataType : "json",
	  			success: function( data ) {
	  				if(data.taskErrors != ""){
	  					$.each(data.taskErrors, function(index,value){
	  						if(value.msgType == 'E3'){
	  							$('#'+value.msgType).append(value.msg+'<br>');
	  							$('#'+value.msgType).show();
	  						}else{
	  							$('#'+value.msgType).html('');
	  							$('#'+value.msgType).append('<p class="errorMsg">'+value.msg+'</p>');
	  							$('#'+value.msgType).show();
	  						}
	  					});	  					
	  					
	  				}else{
	  				
	  					$("#scopeChangeCancelComments").val($("#scopeChangeComments").val());
	     				var reason = jQuery("#scopeChangeReason :selected").text();
	     				$("#scopeChangeCancelReason").val(reason);
	     				$("#scopeChangeCancelReasonCode").val($("#scopeChangeReason").val());
	     				document.frmCancelFromSOM.cancelAmt.value = parseFloat(maxTotal);
	     				
	     				var amount = parseFloat(maxTotal);
	     				amount = formatAsMoney(amount);
	     				var maxPrice = parseFloat($("#maxPrice").val());
	     				/*Commented to remove totalAmount > maxPrice validation and corresponding error messsage*/
	     				/* if(amount>maxPrice){
	     					$('#E3').html("The total maximum price exceeds the amount your profile allows.");
                            $('#E3').show();
	     				}else{ */
		     				if($("#providerApprove").is(":checked")){
					     		$("#cancellationInfo").css("display","none");
					     		$("#manageScopeDiv").css("display","none");
					     		$("#releasePaymentDiv").css("display","block");
					     		$("#releaseAmount").html(amount);
		     				}else{
		     					fnCancellationSubmit();
			  				}
			  			/*}*/
	  				}	  				
	  			}
	  		});
     	
     	
     });
     
     /* Manage scope */
     $('#saveChange').click(function(){
        var soId = '${soId}';
     	clearErrors();
     	if(saveClicked == false){
     		//updateTaskPrice();
     		saveClicked = true; //Disable multiple form submission
     		//Update total price when form is submitted.
     		var maxTotal = updateTotalPrice();
     		$('#maxTotalPrice').val(maxTotal);
     		//var maxTotal = $('#maxTotalPrice').val();
     		var formData = $('#manageScopeForm').serialize();
     		$.ajax({
	  			url: 'manageScope_saveChanges.action?totalPrice='+maxTotal+'&soId='+soId,
	  			data : formData,
	  			dataType : "json",
	  			success: function( data ) {
	  				if(data.taskErrors != ""){
	  					$.each(data.taskErrors, function(index,value){
	  						if(value.msgType == 'E3'){
	  							$('#'+value.msgType).append(value.msg+'<br>');
	  							$('#'+value.msgType).show();
	  						}else{
	  							$('#'+value.msgType).append('<p class="errorMsg">'+value.msg+'</p>');
	  							$('#'+value.msgType).show();
	  						}
	  					});
	  					saveClicked = false;
	  				} else {
	  					//jQuery("#manageScopeDialog").modal.close();
	  					$("#closeWindow").click();
	  					
						//code commented and modified to avoid DOM Open Client Redirect (CheckMarx Low) 
	  					//var pathname = window.location.pathname + "?soId=" + soId;
	  					var pathname = encodeURI(window.location.pathname) + "?soId=" + encodeURIComponent(soId);
	  					window.location.replace(pathname);
	  					//need to refresh the window. SOdetails action
	  				}
	  				
	  			}
	  		});
     	}
     });
      
      /*This function, updates totalMax price.*/
      function updateTotalPrice(){
     		var initPrice = $('#initPrice').val();
     		/* initPrice = labor price + permits price + parts price. */ 
     		var maxTotal = initPrice;  
     		/* Add amount of newly added tasks to calculate final total price.*/		
     		if(taskId !=0){
     			var id;
     			//Iterate for each newly added tasks.
     			for(id=1; id <=taskId ; ++id){
     				//If element exists
     				if($('#priceInteger-'+id).length > 0){
     					//Remove aphabets and special chars
     					$('#priceInteger-'+id).val($('#priceInteger-'+id).val().replace(/[^0-9]/g, ''));
			 			$('#priceFraction-'+id).val($('#priceFraction-'+id).val().replace(/[^0-9]/g, ''));
			 
					 	var priceInteger = $('#priceInteger-'+id).val();
			 			var priceFraction = $('#priceFraction-'+id).val();
			 
						if(priceInteger==""){
				         	//$('#priceInteger-'+id).val('0');
				         	priceInteger = '0';
				        }
				        if(priceFraction=="" || priceFraction=='0'){
				         	//$('#priceFraction-'+id).val('00');
				         	priceFraction = '00';
				        }
				        var taskPrice =  priceInteger+"."+priceFraction;
				        maxTotal = parseFloat(maxTotal) + parseFloat(taskPrice, 10);
     				}				
     			}     			
     		}
     		return maxTotal;  		     	 
     }
      
         
     function lockFields(){
     	$('#priceInteger').attr('disabled','true');
	  	$('#priceFraction').attr('disabled','true');
	  	$('#taskName').attr('disabled','true');
		$('#taskComments').attr('disabled','true');
     }
     
     function unlockField(){
     	$('#priceInteger').removeAttr('disabled');
	  	$('#priceFraction').removeAttr('disabled');
	  	$('#taskName').removeAttr('disabled');
		$('#taskComments').removeAttr('disabled');
     }
     
     $("#closeButton").click(function(){
     	jQuery.modal.impl.close(true);
     });
     
     function textCounter( field, countfield, maxlimit ) {
 		if ( field.value.length > maxlimit ) {
  			field.value = field.value.substring( 0, maxlimit );
  			field.blur();
  			field.focus();
  			return false;
 		} else {
 			 document.getElementById(countfield).innerHTML = maxlimit - field.value.length;
 		}
	}
	 function setSkill(){
	 	 var skillVal = $("#skill").val();
     	 var skill = $("#skill option:selected").text();
     	 if(skillVal=="-1"){
     	 	$("#hiddenSkill").val(skillVal);
     	 }else{
     	 	$("#hiddenSkill").val(skill);
     	 	$("#serviceType").val(skillVal);
     	 }
     }
</script>
</head>

<body>
<c:choose>
<c:when test="${fromCancel=='true'}">
	<div style="width: 650px;height: 530px;">
</c:when>
<c:otherwise>
	<div style="width: 650px;height: 510px;">
</c:otherwise>
</c:choose>
<div class="modalHomepage" style="height: 10px;vertical-align: middle;">
<div style="float: left;">Manage Scope Change</div>
<a href="javascript:void(0);" class="btnBevel simplemodal-close" id="closeButton" style="color: white;float:right"><img src="${staticContextPath}/images/icons/modalCloseX-transp.gif"></a></div>
<c:choose>
<c:when test="${fromCancel=='true'}">
	<table style="width: 100%;height: 530px" id="manageScopeTable">
</c:when>
<c:otherwise>
	<table style="width: 100%;height: 500px" id="manageScopeTable">
</c:otherwise>
</c:choose>
	<tr class="firstRow" height="50%">
		<td id="existingTasks" width="50%">
			<div id="E4" class="errorBox" style="display: none;"></div>
			<div class="mngScopeHeaders" style="padding: 10px">Existing Tasks:</div>
			<table width="100%" style="background-color: #DBD9DA;padding: 5px;" id="taskListHdr">
					<tr>
						<td width="5%"></td>
						<td width="65%">Task</td>
						<td width="30%" align="right">Price</td>
					</tr>
			</table>
			
			<div style="width:320px;height: 220px ; word-wrap: break-word; overflow-y: auto;overflow-x: hidden;background-color: #FAF9C3;">
			<table id="taskList" style="table-layout:fixed;" width="100%" style="padding: 5px;" cellspacing="0" >
				<c:forEach items="${tasks}" var="task" varStatus="index">
					<tr style="height: 20px" <c:if test="${index.count%2 == 0}">class="evenRow"</c:if>>
						<td width="5%">${index.count}.</td>
						<td width="50%">${task.title}</td>
						<td width="30%" align="right">	
					<c:choose>
					<c:when test="${'Deliver Merchandise'==task.title}">--.--</c:when>
					<c:when test="${task.finalPrice != null}">$<fmt:formatNumber value="${task.finalPrice}" minFractionDigits="2" maxFractionDigits="2"/></c:when>
							<c:otherwise>--.--</c:otherwise>
					</c:choose>
						</td>
					</tr>
				</c:forEach>
			</table>
			
			</div>
		</td>
		<td id="addTaskStep1" width="50%">		
		<div id = "E2" class="errorBox" style="display: none;"></div>
		<div class="mngScopeHeaders" style="padding: 10px 10px 0px">Step 1 - Add Tasks:</div>
		<form id="addTaskForm">
		<input type="hidden" name="maxTotalPrice" id="maxTotalPrice" value="${totalPrice}"/>
		<input type="hidden" name="maxLabourPrice" id="maxLabourPrice" value="${labourPrice}"/>
		<input type="hidden" name="skillNodeId" id="skillNodeId" value="-1" />
		<input type="hidden" name="sku" id="sku" value="-1"/>
		<input type="hidden" name="soMainCategory" id="soMainCategory" value="${soMainCatId }"/>
		<input type="hidden" name="actionInd" id="actionInd" value="false"/>
		<div style="padding: 2px">
		<select id="skuId" name="skuId" style="width: 150px" >
			<option value="-1" selected="selected">Select SKU</option>
			<c:choose>
			<c:when test="${skuSkillForCategory != null && not empty skuSkillForCategory}">
				<c:forEach var="listOuter" items="${skuSkillForCategory}">
					 <c:forEach var="listInner" items="${listOuter}">  
                  		<option value="${listInner.key}">${listInner.value}</option>
                	 </c:forEach>  
       		    </c:forEach>
			
				<c:if test="${skuSkill != null && not empty skuSkill}">
					<option value="more">More...</option>
				</c:if>
			</c:when>
			<c:otherwise>
				<c:choose>
				<c:when test="${skuSkill != null && not empty skuSkill}">
					<option value="more">More...</option>
				</c:when>
				<c:otherwise>
					<option value="0">NA</option>
				</c:otherwise>
				</c:choose>				
			</c:otherwise>
			</c:choose>
		</select>
		</div>
		<div style="padding: 2px">
			<select id="skill"  onchange="setSkill();" style="width: 150px">
			<option value="-1">Select Skill</option>
		</select>
		</div>
		<input type="hidden" name="skill" id="hiddenSkill" value=""/>
		<input type="hidden" name="serviceTypeTemplateId" id="serviceType" />
		<div style="padding: 2px">
		<table width="100%">
		<tr>
			<td>
			<span id="priceLabel">Task Price</span>&nbsp;<span style="color:red;">*</span> 
			<b> &nbsp &nbsp$ </b>
			<input type="text" size="6" maxlength="7" id="priceInteger" name="priceInteger" class="priceI" onkeypress="return isNumberKey(event);"/>
			
			<b> . </b>
			<input type="text" size="2" maxlength="2" id="priceFraction" name="priceFraction"  class="priceF" onkeypress="return isNumberKey(event);"/>
			 
			<td>
			<td width="30%">
			<span id="priceWarning" class="priceMsg" style="display: none;"><b>Warning, this task is over $99.99</b></span>
			</td>
		</tr>
		</table>
		</div>
		<div style="padding: 2px">
			Task Name &nbsp;<span style="color:red;">*</span>&nbsp;
			<input type="text" id="taskName" name="taskName" maxlength="45" />
		</div>	
		<div style="padding: 2px;">
			Task Comments &nbsp;<span style="color:red;">*</span><br>
			<textarea id="taskComments" name="taskComments" style="width:90%" rows="2"  onkeyup="textCounter(this,'tskCmntCntr',600);" onkeydown="textCounter(this,'tskCmntCntr',600);"></textarea>
			<div style="width:90%;">
				<div style="float: right"><span id="tskCmntCntr">600</span>&nbsp characters remaining</div>
			</div>
		</div>
		<div style="padding: 10px 0"> <input type="button" value="Add Task" id="addTask" class="button action" style="width: 90px"/> </div>
		</form>
		<div id="completeTaskNotice">
		<p><span style="font-family:Verdana,Arial,Helvetica,sans-serif" ><b>Complete this form once for each task.</b></span> Adding a task will
			affect the service order pricing and provider payment amount. 
			<c:if test="${fromCancel=='true'}">
			This pricing change will take effect after you complete this form and the
			order has been submitted to be canceled.</c:if>
		</p>
		</div>
		</td>
	</tr>
	<tr class="secondRow">
		<td>
		<div id="E3" style="background-color: #F9B7FF;margin: 10px;padding-top:-50px;display: none;"></div>
			<table width="100%" cellspacing="0" style="padding: 5px" id="taskSummary">
					<tr height="20px">
						<td width="10%"></td>
						<td width="60%" align="right">Maximum Labor:</td>
						<td width="30%" align="right" id="labourPrice">$<fmt:formatNumber value="${labourPrice}"  minFractionDigits="2" maxFractionDigits="2"/></td>
					</tr>
					<tr height="20px">
						<td width="10%"></td>
						<td width="60%" align="right">Maximum Parts:</td>
						<td width="30%" align="right">$<fmt:formatNumber value="${partsPrice}" minFractionDigits="2" maxFractionDigits="2"/></td>
					</tr>
						<c:if test="${isPermitOn=='true'}">
					<tr height="20px">
						<td width="10%"></td>
						<td width="60%" align="right">Permits:</td>
						<td width="30%" align="right">$<fmt:formatNumber value="${permitPrice}"  minFractionDigits="2" maxFractionDigits="2"/></td>
					</tr>
					</c:if>
					<tr height="20px">
						<td width="10%"></td>
						<td width="60%" align="right" style="background-color: white; vertical-align: middle;"><b>Total Maximum Price:</b></td>
						<td width="30%" align="right" style="background-color: white; vertical-align: middle;" id="totalPrice"><b>$<fmt:formatNumber value="${totalPrice}" minFractionDigits="2" maxFractionDigits="2"/></b></td>
					</tr>
			</table>
		</td>
		<td id="addTaskStep2">
			<div id="E1" class="errorBox" style="display: none;"></div>
			<div class="mngScopeHeaders" style="padding: 10px 10px 0px">Step 2 - Complete Scope Change:</div>
			<form id="manageScopeForm">
			<input type="hidden" id="initPrice" name="initPrice" value="${totalPrice}"/>
			<div style="padding : 5px 0 5px 0">
				<select id="scopeChangeReason" name="scopeChangeReason" style="width:250px">
					<option value="-1">Select A Reason for Scope Change</option>
					<c:forEach items="${reasonCodes}" var="reasonCode">
						<option value="${reasonCode.reasonCodeId}">${reasonCode.reasonCode}</option>
					</c:forEach>
				</select>&nbsp;<b><span style="color:red;">*</span></b>
			</div>
			<c:if test="${fromCancel=='true'}">
			<div style="padding: 3px">
				<input type="checkbox" id="providerApprove" name="providerApprove"/>
				<span style="padding-left: 1px;">Check this box if the provider has verbally </span><span style="padding-left: 15px;">acknowledged this cancellation.</span>
			</div>
			</c:if>
			<div>
				<b>Scope Change Comments <span style="color:red;">*</span></b><br>
				<textarea id="scopeChangeComments" name="scopeChangeComments" style="width:90%;" rows="2" onkeyup="textCounter(this,'scpCmntCntr',400);" onkeydown="textCounter(this,'scpCmntCntr',400);"></textarea>
				<div style="width:90%;">
					<div style="float: right;"><span id="scpCmntCntr">400</span>&nbsp characters remaining</div>
				</div>
			</div>
			</form>
		</td>
	</tr>
	<tr class="thirdRow" height="5%">
		<td colspan="2">
		<c:choose>
			<c:when test="${fromCancel=='true'}">
				<a href="javascript:void(0);" style="float: left;color: red;padding: 5px"  id="prevCancel"><u>Back</u></a>
				<div style="float: right;width: 130px;padding: 5px">
					<input type="button" id="checkReleaseAndPay" value="Submit Cancellation"  class="button action" style="width: 130px"/>
				</div>
			</c:when>
			<c:otherwise>
				<a href="javascript:void(0);" style="float: left;color: red;padding: 5px" class="simplemodal-close" id="closeWindow"><u>Close Window</u></a>
				<div style="float: right;width: 130px;padding: 5px">
					<input type="button" id="saveChange" value="Save Changes"  class="button action" style="width: 130px"/>
				</div>
			</c:otherwise>
		</c:choose>
		</td>
	</tr>
</table>
</div>
</body>
</html>