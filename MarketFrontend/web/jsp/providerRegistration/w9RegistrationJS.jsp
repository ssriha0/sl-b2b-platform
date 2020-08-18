<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
			
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/jquery-form.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqmodal/jqModal.js"></script>
<script src="${staticContextPath}/scripts/plugins/jquery.mask.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqueryui/jquery-ui-1.10.4.custom.min.js"></script>

<script type="text/javascript">
	jQuery.noConflict();
	jQuery(document).ready(function($) {
	jQuery("#editTaxNo").hide();
	
	
	var taxPayerTypeId=jQuery("#taxPayerTypeId").val();
	
	if (taxPayerTypeId != '' && taxPayerTypeId ==3)
	{
		jQuery("#editTaxNo").show();
		var editOrCancel= jQuery("#editOrCancel").val();
		
		if(editOrCancel=='edit')
		{
		jQuery('#cancelLinks').hide();
		jQuery('#editLink').show();
		var num=document.getElementById('w9prefill.originalEinNo').value;
		jQuery('#taxPayerId').val(num);
		jQuery('#confirmTaxPayerId').val(num);
		jQuery('#taxPayerId').attr("disabled", true);
		jQuery('#confirmTaxPayerId').attr("disabled", true);
		}
	}
	
	if(document.getElementById('EIN').checked) {
 

	jQuery("#dateOfBirth").hide();
	var taxPayerTypeId=jQuery("#taxPayerTypeId").val();
	

if(taxPayerTypeId!='' && (taxPayerTypeId=='1' ||taxPayerTypeId=='3'))
		{
		
		jQuery("#editTaxNo").show();
		var editOrCancel= jQuery("#editOrCancel").val();
		
		if(editOrCancel=='edit')
		{
		jQuery('#cancelLinks').hide();
		jQuery('#editLink').show();
		var num=document.getElementById('w9prefill.originalEinNo').value;
		jQuery('#taxPayerId').val(num);
		jQuery('#confirmTaxPayerId').val(num);
		jQuery('#taxPayerId').attr("disabled", true);
		jQuery('#confirmTaxPayerId').attr("disabled", true);
		}
		else if(editOrCancel=='cancel')
		{
		jQuery('#editLink').hide();
		jQuery('#cancelLinks').show();
		jQuery('#taxPayerId').removeAttr("disabled"); 
		jQuery('#confirmTaxPayerId').removeAttr("disabled");
		/*jQuery("#taxPayerId").unmask();
		jQuery("#confirmTaxPayerId").unmask();  
		jQuery('#taxPayerId').val('');
		jQuery('#confirmTaxPayerId').val('');
		jQuery("#taxPayerId").mask('99-9999999');
		jQuery("#confirmTaxPayerId").mask('999-9999999');*/
		}
		
		}
		else
		{
		
		jQuery("#editTaxNo").hide();
		
		}

}
if(document.getElementById('SSN').checked) {



jQuery("#dateOfBirth").show();
var taxPayerTypeId=jQuery("#taxPayerTypeId").val();


if(taxPayerTypeId!='' && (taxPayerTypeId=='2' || taxPayerTypeId=='3'))
		{

		
		jQuery("#editTaxNo").show();
		var editOrCancel= jQuery("#editOrCancel").val();
		
		if(editOrCancel=='edit')
		{
		jQuery('#cancelLinks').hide();
		jQuery('#editLink').show();
		var num=document.getElementById('w9prefill.originalEinNo').value;
		jQuery('#taxPayerId').val(num);
		jQuery('#confirmTaxPayerId').val(num);
		jQuery('#taxPayerId').attr("disabled", true);
		jQuery('#confirmTaxPayerId').attr("disabled", true);
		}
		else if(editOrCancel=='cancel')
		{
		jQuery('#editLink').hide();
		jQuery('#cancelLinks').show();
		/*jQuery("#taxPayerId").unmask();
		jQuery("#confirmTaxPayerId").unmask(); 
		jQuery('#taxPayerId').removeAttr("disabled"); 
		jQuery('#confirmTaxPayerId').removeAttr("disabled"); 
		jQuery('#taxPayerId').val('');
		jQuery('#confirmTaxPayerId').val('');
		jQuery("#taxPayerId").mask('999-99-9999');
		jQuery("#confirmTaxPayerId").mask('999-99-9999');*/
		}
		
			}
		else
		{

		jQuery("#editTaxNo").hide();
		
		}
		

}
	
	
	
	/*jQuery("input:radio[name=w9prefill.taxPayerTypeId]").click(function() {   
		
		
		  var value = jQuery(this).val();
		  if(value==2)
		  {
		  jQuery("#dateOfBirth").show();
		  }
		  else
		  {
		 jQuery("#dateOfBirth").hide();
		  } 
		  }); */
		
		<c:if test="${w9isExist==true && w9prefill.taxPayerTypeId == 2}">
		
		jQuery("#dateOfBirth").show();
		</c:if>
		
		<c:if test="${w9isExist==true && w9prefill.taxPayerTypeId == 1}">
		
		jQuery("#dateOfBirth").hide();
		</c:if>
		
	

	if (${w9prefill.isTaxExempt })
	{
		$("div.yesno").css({"background-position":"bottom left"});
		$("div.yesno").children("input").val("true");
	}
	else
	{
		$("div.yesno").css({"background-position":"top left"});
		$("div.yesno").children("input").val("false");
	}
	$("div.yesno").click(
	function () {
		if($(this).children("input").val() == "true")
		{
			$(this).css({"background-position":"top left"});
			$(this).children("input").val("false");
		}
		else
		{
			$(this).css({"background-position":"bottom left"});
			$(this).children("input").val("true");
		}
	});
});


function showFirstAnswer()
{

      jQuery("#firstAnswer").slideToggle("slow"); 
      jQuery("#firstCloseArrow").hide();
      jQuery("#firstOpenArrow").show();
      jQuery("#firstQuestion").hide();
      jQuery("#firstQuestionOpen").show();

}
function showFirstAnswerOpen()
{

      jQuery("#firstAnswer").slideToggle("slow"); 
      jQuery("#firstOpenArrow").hide();
      jQuery("#firstCloseArrow").show();
      jQuery("#firstQuestionOpen").hide();
      jQuery("#firstQuestion").show();
      

}
function showSecondAnswer()
{

      jQuery("#secondAnswer").slideToggle("slow");
      jQuery("#secondCloseArrow").hide();
      jQuery("#secondOpenArrow").show();
      jQuery("#secondQuestion").hide();
      jQuery("#secondQuestionOpen").show(); 
      

}
function showSecondAnswerOpen()
{

      jQuery("#secondAnswer").slideToggle("slow");
      jQuery("#secondOpenArrow").hide();
      jQuery("#secondCloseArrow").show();
      jQuery("#secondQuestionOpen").hide();
      jQuery("#secondQuestion").show(); 

}




function showPopUpFAQ()
{
jQuery("#firstOpenArrow").hide();
      jQuery("#firstCloseArrow").show();
      jQuery("#firstQuestionOpen").hide();
      jQuery("#firstQuestion").show();
      jQuery("#secondOpenArrow").hide();
      jQuery("#secondCloseArrow").show();
      jQuery("#secondQuestionOpen").hide();
      jQuery("#secondQuestion").show(); 
      jQuery("#firstAnswer").hide();
      jQuery("#secondAnswer").hide();
      
      
			/*jQuery('#w9modal').jqm({modal:true, toTop: true});
			jQuery('#w9modal').jqmShow();*/
			jQuery("#popUpfaq").modal({
                onOpen: modalOpen,
                onClose: modalOnClose,
                persist: true,
                close: false,
                containerCss: ({ width: "600px", height: "auto", marginLeft: "50px" })
            });
}

function clickTaxId()
{
jQuery("#taxPayerId").unmask();
//jQuery('#taxPayerId').val('');


if(document.getElementById('SSN').checked) {

jQuery("#taxPayerId").mask('999-99-9999');

}
if(document.getElementById('EIN').checked) {

jQuery("#taxPayerId").mask('99-9999999');

}
}

function clickConfirm()
{

jQuery("#confirmTaxPayerId").unmask();

//jQuery('#confirmTaxPayerId').val('');

if(document.getElementById('SSN').checked) {



jQuery("#confirmTaxPayerId").mask('999-99-9999');
}
if(document.getElementById('EIN').checked) {



jQuery("#confirmTaxPayerId").mask('99-9999999');
}
}

function editLink()
{
/*jQuery("#taxPayerId").unmask();
jQuery("#confirmTaxPayerId").unmask();
jQuery('#taxPayerId').val('');
jQuery('#confirmTaxPayerId').val('');
*/
	if (!document.getElementById('EIN').checked && !document.getElementById('SSN').checked){
		jQuery("#content_right_header_text_w9_messages").hide();
		jQuery("#chooseTaxIdTypeErrorDiv").show();
	}else{
		jQuery("#chooseTaxIdTypeErrorDiv").hide();
		jQuery('#editTaxNo').show();
		jQuery('#editLink').hide();
		jQuery('#cancelLinks').show();
	
	
		jQuery('#taxPayerId').removeAttr("disabled"); 
		jQuery('#confirmTaxPayerId').removeAttr("disabled"); 
		
		if(document.getElementById('SSN').checked) {
				
			jQuery("#taxPayerId").mask('999-99-9999');
			jQuery("#confirmTaxPayerId").mask('999-99-9999');
		}
		if(document.getElementById('EIN').checked) {		
		
			jQuery("#taxPayerId").mask('99-9999999');
			jQuery("#confirmTaxPayerId").mask('99-9999999');
		}
		jQuery("#editOrCancel").val('cancel');

	}
}
function cancelLink()
{

jQuery('#cancelLinks').hide();

jQuery('#editLink').show();
var num=document.getElementById('w9prefill.originalEinNo').value;
jQuery('#taxPayerId').val(num);
jQuery('#confirmTaxPayerId').val(num);
jQuery('#taxPayerId').attr("disabled", true);
jQuery('#confirmTaxPayerId').attr("disabled", true);

jQuery("#editOrCancel").val('edit');

}

function clickTaxPayerId()
{
jQuery("#chooseTaxIdTypeErrorDiv").hide();
jQuery("#content_right_header_text_w9_messages").hide();
if(document.getElementById('EIN').checked) {

	jQuery("#dateOfBirth").hide();

	var taxPayerTypeId=jQuery("#taxPayerTypeId").val();


	var taxIds=jQuery('#taxPayerId').val();
	var confirmTaxIds=jQuery('#confirmTaxPayerId').val();
	
	jQuery('#ssnTaxId').val(taxIds);
	jQuery('#ssnconfirmTaxId').val(confirmTaxIds);

	if(taxPayerTypeId!='' && (taxPayerTypeId=='1' || taxPayerTypeId =='3'))
		{
		
		jQuery("#editTaxNo").show();
		var editOrCancel=jQuery("#editOrCancel").val();
		
		if(editOrCancel!='' && editOrCancel=='cancel')
		{
		jQuery('#editLink').hide();
		jQuery('#cancelLinks').show();
		jQuery('#taxPayerId').removeAttr("disabled"); 
		jQuery('#confirmTaxPayerId').removeAttr("disabled"); 
		jQuery("#taxPayerId").unmask();
		jQuery("#confirmTaxPayerId").unmask(); 
		var taxId=jQuery('#einTaxId').val();
		var confirmTaxId=jQuery('#confirmEinTaxId').val();
		
		jQuery('#taxPayerId').val(taxId);
		jQuery('#confirmTaxPayerId').val(confirmTaxId);
		jQuery("#taxPayerId").mask('99-9999999');
		jQuery("#confirmTaxPayerId").mask('99-9999999');
		
		}
		if(editOrCancel!='' && editOrCancel=='edit')
		{
		jQuery('#cancelLinks').hide();
		jQuery('#editLink').show();
		var num=document.getElementById('w9prefill.originalEinNo').value;
		jQuery('#taxPayerId').val(num);
		jQuery('#confirmTaxPayerId').val(num);
		jQuery('#taxPayerId').attr("disabled", true);
		jQuery('#confirmTaxPayerId').attr("disabled", true);
		}
	
		}
		else
		{
		
		jQuery("#editTaxNo").hide();
		jQuery('#taxPayerId').removeAttr("disabled"); 
		jQuery('#confirmTaxPayerId').removeAttr("disabled");
		jQuery("#taxPayerId").unmask();
		jQuery("#confirmTaxPayerId").unmask();  
		var taxId=jQuery('#einTaxId').val();
		var confirmTaxId=jQuery('#confirmEinTaxId').val();
		
		jQuery('#taxPayerId').val(taxId);
		jQuery('#confirmTaxPayerId').val(confirmTaxId);
		jQuery("#taxPayerId").mask('99-9999999');
		jQuery("#confirmTaxPayerId").mask('99-9999999');
		
		}

}

	if(document.getElementById('SSN').checked) {
		jQuery("#dateOfBirth").show();
		var taxPayerTypeId=jQuery("#taxPayerTypeId").val();
		var taxIds=jQuery('#taxPayerId').val();
		var confirmTaxIds=jQuery('#confirmTaxPayerId').val();
				
		jQuery('#einTaxId').val(taxIds);
		jQuery('#confirmEinTaxId').val(confirmTaxIds);

		if(taxPayerTypeId!='' && (taxPayerTypeId=='2' || taxPayerTypeId =='3'))
		{

		
			jQuery("#editTaxNo").show();
			var editOrCancel=jQuery("#editOrCancel").val();
			if(editOrCancel!='' && editOrCancel=='cancel')
			{
				jQuery('#editLink').hide();
				jQuery('#cancelLinks').show();
				jQuery('#taxPayerId').removeAttr("disabled"); 
				jQuery('#confirmTaxPayerId').removeAttr("disabled");
				jQuery("#taxPayerId").unmask();
				jQuery("#confirmTaxPayerId").unmask(); 
				
		
				var taxId=jQuery('#ssnTaxId').val();
				var confirmTaxId=jQuery('#ssnconfirmTaxId').val();
				
				jQuery('#taxPayerId').val(taxId);
				jQuery('#confirmTaxPayerId').val(confirmTaxId);
				
				jQuery("#taxPayerId").mask('999-99-9999');
				jQuery("#confirmTaxPayerId").mask('999-99-9999');
			}
			if(editOrCancel!='' && editOrCancel=='edit')
			{
				jQuery('#cancelLinks').hide();
				jQuery('#editLink').show();
				var num=document.getElementById('w9prefill.originalEinNo').value;
				jQuery('#taxPayerId').val(num);
				jQuery('#confirmTaxPayerId').val(num);
				jQuery('#taxPayerId').attr("disabled", true);
				jQuery('#confirmTaxPayerId').attr("disabled", true);
			}
		
		}
		else
		{

			jQuery("#editTaxNo").hide();
			jQuery('#taxPayerId').removeAttr("disabled"); 
			jQuery('#confirmTaxPayerId').removeAttr("disabled");
			jQuery("#taxPayerId").unmask();
			jQuery("#confirmTaxPayerId").unmask(); 
			var taxId=jQuery('#ssnTaxId').val();
			var confirmTaxId=jQuery('#ssnconfirmTaxId').val();
		
			jQuery('#taxPayerId').val(taxId);
			jQuery('#confirmTaxPayerId').val(confirmTaxId);
			
			jQuery("#taxPayerId").mask('999-99-9999');
			jQuery("#confirmTaxPayerId").mask('999-99-9999');
		
		}
		

}

}


function modalOpen(dialog) {
            dialog.overlay.fadeIn('fast', function() {
            dialog.container.fadeIn('fast', function() {
            dialog.data.hide().slideDown('slow');
            });
        });

 	}
  
     function modalOnClose(dialog) {
       dialog.data.fadeOut('slow', function() {
           dialog.container.slideUp('slow', function() {
               dialog.overlay.fadeOut('slow', function() {
                   jQuery.modal.close(); 
               });
           });
       });
    }
function showFAQ()
		{
			
			
			/*jQuery('#w9modal').jqm({modal:true, toTop: true});
			jQuery('#w9modal').jqmShow();*/
			jQuery("#faq").modal({
                onOpen: modalOpen,
                onClose: modalOnClose,
                persist: true,
                containerCss: ({ width: "600px", height: "auto", marginLeft: "-250px" })
            });
          
		}
		
	
function fnReturnFocus(){
	jQuery.modal.close(); 
	window.location.href = "#dateOfBirth";
	}
			

function showDatepicker()
{
	
	jQuery('#dob').datepicker(
		{
			dateFormat:'mm/dd/yy', changeMonth:true, changeYear:true, minDate: new Date(1900, 12 - 1, 1), yearRange: '-200:+200', maxDate: new Date(),
			onSelect: function() {jQuery("#dob").css('color','black');} 
		}).datepicker( "show" );

}

function hide_w9modal()
{
jQuery('#w9modal').hide();
jQuery('.simplemodal-container').hide();
jQuery('.simplemodal-overlay').hide();
var autoclose=jQuery("#autoclose").val();
				
				if(autoclose!='' && autoclose=='serviceincomplete')
				{

document.getElementById("frmCompleteForPaymentServiceIncomplete").method = "post";                              
document.getElementById("frmCompleteForPaymentServiceIncomplete").action = "${contextPath}/soDetailsController.action?soId=${soId}&defaultTab=Complete%20for%20Payment&displayTab=Today#ui-tabs-3";
document.getElementById("frmCompleteForPaymentServiceIncomplete").submit();


}
}


function submitW9()
{
	jQuery.noConflict();
		jQuery('#modalSavebtn').attr("disabled", true);
	
		var queryString = jQuery('#frmW9Details').formSerialize();
		jQuery.post('w9registrationAction_saveAjax.action',queryString, function(data) {
			//hide_w9modal();
			if (data == "")
			{
				//hide_w9modal();
				if (document.getElementById('formW9').value == "withdraw")
				{
					document.fmWithdrawFunds.submit();
				}
				else if (document.getElementById('formW9').value == "complete")
				{
				var autoclose=jQuery("#autoclose").val();
				
				if(autoclose!='' && autoclose=='serviceincomplete')
				{
				
				document.getElementById("frmCompleteForPaymentServiceIncomplete").method = "post";                              
                document.getElementById("frmCompleteForPaymentServiceIncomplete").action = "${contextPath}/soDetailsCompleteForPayment_completeSo.action?autoclose=true";
                document.getElementById("frmCompleteForPaymentServiceIncomplete").submit();
				}
				else
				{
					fnCompleteForpayment();
				}
				}
			}
			else
			{
				document.getElementById('fillWithW9').innerHTML = data;
				// start of function
				if(document.getElementById('EIN').checked) {

jQuery("#dateOfBirth").hide();
var taxPayerTypeId=jQuery("#taxPayerTypeId").val();

if(taxPayerTypeId!='' && (taxPayerTypeId=='1' ||taxPayerTypeId=='3'))
		{
		
		jQuery("#editTaxNo").show();
		var editOrCancel= jQuery("#editOrCancel").val();
		
		if(editOrCancel=='edit')
		{
		jQuery('#cancelLinks').hide();
		jQuery('#editLink').show();
		var num=document.getElementById('w9prefill.originalEinNo').value;
		jQuery('#taxPayerId').val(num);
		jQuery('#confirmTaxPayerId').val(num);
		jQuery('#taxPayerId').attr("disabled", true);
		jQuery('#confirmTaxPayerId').attr("disabled", true);
		}
		else if(editOrCancel=='cancel')
		{
		jQuery('#editLink').hide();
		jQuery('#cancelLinks').show();
		/*jQuery('#taxPayerId').removeAttr("disabled"); 
		jQuery('#confirmTaxPayerId').removeAttr("disabled"); 
		jQuery('#taxPayerId').val('');
		jQuery('#confirmTaxPayerId').val('');
		jQuery("#taxPayerId").mask('99-9999999');
		jQuery("#confirmTaxPayerId").mask('999-9999999');*/
		}
		
		}
		else
		{
		
		jQuery("#editTaxNo").hide();
		
		}

}
if(document.getElementById('SSN').checked) {



jQuery("#dateOfBirth").show();
var taxPayerTypeId=jQuery("#taxPayerTypeId").val();

if(taxPayerTypeId!='' && (taxPayerTypeId=='2' ||taxPayerTypeId=='3'))
		{

		
		jQuery("#editTaxNo").show();
		var editOrCancel= jQuery("#editOrCancel").val();
	
		if(editOrCancel=='edit')
		{
		jQuery('#cancelLinks').hide();
		jQuery('#editLink').show();
		var num=document.getElementById('w9prefill.originalEinNo').value;
		jQuery('#taxPayerId').val(num);
		jQuery('#confirmTaxPayerId').val(num);
		jQuery('#taxPayerId').attr("disabled", true);
		jQuery('#confirmTaxPayerId').attr("disabled", true);
		}
		else if(editOrCancel=='cancel')
		{
		jQuery('#editLink').hide();
		jQuery('#cancelLinks').show();
		/*jQuery('#taxPayerId').removeAttr("disabled"); 
		jQuery('#confirmTaxPayerId').removeAttr("disabled"); 
		jQuery('#taxPayerId').val('');
		jQuery('#confirmTaxPayerId').val('');
		jQuery("#taxPayerId").mask('999-99-9999');
		jQuery("#confirmTaxPayerId").mask('999-99-9999');*/
		}
		
			}
		else
		{

		jQuery("#editTaxNo").hide();
		
		}
	// end of function.	

}
			}
			jQuery('#dpop').hide();
	
		}, "html");
}



</script>