<%@page import="com.newco.marketplace.interfaces.OrderConstants,com.newco.marketplace.web.dto.ServiceOrderDTO,com.newco.marketplace.web.constants.SOConstants,org.apache.struts2.ServletActionContext,com.newco.marketplace.constants.Constants,com.newco.marketplace.util.PropertiesUtils,org.apache.commons.lang.StringEscapeUtils,org.owasp.esapi.ESAPI;"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<c:set var="SERVICE_ORDER_ID" scope="request"
	value="<%=request.getAttribute("SERVICE_ORDER_ID")%>" />
<c:set var="so" scope="request"
	value="<%=request.getAttribute("THE_SERVICE_ORDER")%>" /> 
<c:set var="SOD_ROUTED_RES_ID" scope="request"
	value="<%=request.getAttribute("routedResourceId")%>" />
<c:set var="GROUP_ID" scope="request"
	value="<%=request.getAttribute("groupOrderId")%>" />
<c:set var="bidderResourceId" scope="request"
	value="<%=request.getAttribute("bidderResourceId")%>" />
<c:set var="searsBuyerId" scope="request"
	value="<%=PropertiesUtils
									.getPropertyValue(Constants.AppPropConstants.SEARS_BUYER_ID)%>" />
<c:set var="hsrBuyerId" scope="request"
	value="<%=PropertiesUtils
									.getPropertyValue(Constants.AppPropConstants.HSR_BUYER_ID)%>" />
<c:set var="noJs" value="true" scope="request" />
<%-- tell header not to insert any JS --%>
<c:set var="noCss" value="true" scope="request" />
<%-- tell header not to insert any CSS --%>
<c:set var="roleId" value="${SERVICE_ORDER_CRITERIA_KEY.roleId}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
	<head>
		
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css">
			
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/support.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/so_details.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/acquity.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltipcss.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/styles/plugins/wfm.css" />
		<link href="${staticContextPath}/css/providerUtils.css"
			rel="stylesheet" type="text/css" />
			

		<!--[if lte IE 7]>
      <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
   <![endif]-->
		<link href="${staticContextPath}/javascript/StyleSheet.css"
			rel="stylesheet" type="text/css" />
		<link href="${staticContextPath}/javascript/confirm.css"
			rel="stylesheet" type="text/css" />
		<link href="${staticContextPath}/javascript/CalendarControl.css"
			rel="stylesheet" type="text/css">
		<link href="${staticContextPath}/javascript/style.css"
			rel="stylesheet" type="text/css" />
		<link href="${staticContextPath}/styles/plugins/ui.datepicker.css"
			rel="stylesheet" type="text/css"></link>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/ui.tabs2.css" media="screen, projection">
		<link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery.modal.min.css" type="text/css"></link>

		<!-- Start:Done as a part of checkmarx -->
		<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/lib/log4js.js"></script>
		
		<!-- esapi4js core -->
		<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/esapi.js"></script>
		
		<!-- esapi4js i18n resources -->
		<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/resources/i18n/ESAPI_Standard_en_US.properties.js"></script>
		
		<!-- esapi4js configuration -->
		<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/resources/Base.esapi.properties.js"></script>

		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/toolbox.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/tooltip.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dateTime/dateTime.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/plugins/tabs_jquery.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/CalendarControl.js"></script>
			<script type="text/javascript"
			src="${staticContextPath}/javascript/basicAjax.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/vars.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>

		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/countdown/jquery.countdown.min.js"></script>
		<script src="${staticContextPath}/javascript/googleAPI.js"
			type="text/javascript"></script>
			
			
		
		
		<style>
		a.cancel {
			color: red;
			text-decoration: underline;
			cursor: hand;
		}

		a.cancel:hover {
			color: red;
		}
		ul.ui-tabs-nav li a {
		    text-decoration: none;
		    color: #222;
		    font-weight: bold;
		    padding: 6px 6px 2px 4px;
		    outline: none;
		}

		.tabnav li a:active, .tabnav li.ui-tabs-selected a {
		    background: #dedbd1;
		    color: #222;
		    text-decoration: none;
		    border-color: #ccc;
		    color: #fff;
		}
		.ui-tabs-nav li.ui-tabs-active a {
			background: #33393C;
			color: #FFF;
		}
		.simplemodal-overlay {
			background-color: #000;
		}
		.simplemodal-container {
			background-color: #FFF;
		}
		.widget a {
			color: #00a0d2;
			text-decoration: none;
			background: no-repeat top left;
		}
		.widget a:hover {
			color: #00a0d2;			
			background: transparent; 
		}
		
		.modalheader {
			background: #FAFAFA url(../ServiceLiveWebUtil/javascript/dojo/dijit/themes/tundra/images/titleBarBg.gif) repeat-x scroll left bottom;
			cursor: move;
			outline-color: -moz-use-text-color;
			outline-style: none;
			outline-width: 0;
			padding: 4px 8px 2px 4px;
		}
		</style>

		<script type="text/javascript" language="JavaScript">
		    Base.esapi.properties.application.Name = "SL Application";
		    // Initialize the api
		    org.owasp.esapi.ESAPI.initialize();
		</script>
		<!-- End:Done as a part of checkmarx -->
			
		<script type="text/javascript">
    function modalOpenAddCustomer(dialog) {
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
                     
     function rejectSoModalOnClose(dialog) {
         clearRejectSOSelection();
         modalOnClose(dialog);
       }    

 	//fn to open provider profile ina new window
	function openProviderProfile(resouceId,vendorId){
			var url = "/MarketFrontend/providerProfileInfoAction_execute.action?resourceId="
					+ resouceId + "&companyId=" + vendorId + "&popup=true";
			newwindow = window
					.open(url, '_publicproviderprofile',
							'resizable=yes,scrollbars=yes,status=no,height=700,width=1000');
		if (window.focus) {
			newwindow.focus();
			}
		return false;
		
	}
                            
</script>

		<script type="text/javascript" charset="utf-8">
				jQuery(document).ready( function ($) {
					var tabContainers = $('div.tabs > div');
					tabContainers.hide().filter(':first').show();
					$('div.tabs ul.tabNavigation a').click(function () {
						tabContainers.hide();
						tabContainers.filter(this.hash).show();
						$('div.tabs ul.tabNavigation a').removeClass('selected');
						$(this).addClass('selected');
						return false;
					}).filter(':first').click();
					
					<c:if test="${param.rjct == 'y'}">
					reject();
					</c:if>
					
			});
				</script>

		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript">
		dojo.require("newco.jsutils");
            		function resetAddOnTotals()
          			{
          				jQuery('div#toggle').toggle();
          				if(document.getElementById("toggle").display=="block"){
          					document.getElementById("togglePayment").display="block";
          				}
					    else
					    {
							jQuery(".addOnPricing").empty();
							jQuery(".addOnEstPricing").empty();
							$('.addOnPrice').hide();
							if($('#pricingsummary').length > 0){
								calculatPricingSummary();
							}	
						}
          				
         				jQuery('td.col3').children('select').val('0');
         				jQuery('.col4,.col5').children('[type=text]').val('');
 	                    var objEndCustomerSubtotalTotal = document.getElementById('addonServicesDTO.endCustomerSubtotalTotal');
		                objEndCustomerSubtotalTotal.value = "";
		                var providerPaidTotal = document.getElementById('addonServicesDTO.providerPaidTotal');
		                providerPaidTotal.value = "";
		                var providerTaxPaidTotal = document.getElementById('addonServicesDTO.providerTaxPaidTotal');
		                if (providerTaxPaidTotal) {
		                	providerTaxPaidTotal.value = "";
		                }
		                var checkNumber = document.getElementById('addonServicesDTO.checkNumber');
		                if (checkNumber){
		                checkNumber.value = "";
		                }
		                var checkAmount = document.getElementById('addonServicesDTO.checkAmount');
		                if (checkAmount){
		                checkAmount.value = "";
		                }
		                var endCustomerAddOnSubtotalTotal = document.getElementById('endCustomerAddOnSubtotalTotal');
		                endCustomerAddOnSubtotalTotal.value = "0.00";
		                var endHidCustomerAddOnSubtotalTotal = document.getElementById('hidEndCustomerAddOnSubtotalTotal');
		                endHidCustomerAddOnSubtotalTotal.value = "";
		              	addOnObject.calculate();
		                
		                
          			}
            
            	    jQuery(document).ready(function($){
                			function warningTrigger() {
                              var warningMessage = "";
                           $("p.warningMessage").text(warningMessage);
 	                       $('#warningMessage').jqm({modal:true, toTop: true});
    	                   $('#warningMessage').jqmShow();
                              }
                   }); 
            
            
                  function formatAsMoney(mnt)
                  {
                      mnt -= 0;
                      mnt = (Math.round(mnt*100))/100;
                      return (mnt == Math.floor(mnt)) ? mnt + '.00' 
                                : ( (mnt*10 == Math.floor(mnt*10)) ? 
                                         mnt + '0' : mnt);
                  }
 
</script>

		<script type="text/javascript">
		function addPermitAddon(){
if($('.addonPermitClass').length>3){
	$("#addPermit").css("display", "none");	
}else{
var count =0;
count=$('.addOnClass').length;

var row="<tr class='addOnClass'><td  class='col1 odd first textcenter'><input type='hidden' name='addonServicesDTO.addonServicesList["+count+"].sku' id='addonServicesDTO.addonServicesList["+count+"].sku' value='99888'/><label>99888</label></td><td class='col2 even textleft'><label>PERMIT</label>";
	row=row+"<select class='addonPermitClass' onchange='addOnObject.doTotals(${totalElms})' style='width: 180px;' id='addonServicesDTO.addonServicesList["+count+"].permitType' name='addonServicesDTO.addonServicesList["+count+"].permitType'  size='1' theme='simple' >";
row=row+"<option value='-1'>-Select Permit Type-</option><c:forEach var='permits' items='${permitTypeList}' ><option value='${permits.id}' >${permits.type}</option></c:forEach></select></td>";
row=row+"<td class='col3 odd'><select theme='simple' class='text' id='addonServicesDTO.addonServicesList["+count+"].quantity' name='addonServicesDTO.addonServicesList["+count+"].quantity'  style='width: 50px' onchange='addOnObject.calcNewSubtotalForMisc("+count+ ", "+count+", this, true )'><option value='0' > Qty. </option><option value='0' >0</option><option value='1' >1</option></select>";
row=row+"<input type='hidden' id='qty_"+count+"' name='qty_" +count+"'/><input type='hidden' id='addonServicesDTO.addonServicesList["+count+"].description' name='addonServicesDTO.addonServicesList["+count+"].description'/><input type='hidden' id='addonServicesDTO.addonServicesList["+count+"].coverage' name='addonServicesDTO.addonServicesList["+count+"].coverage' value='PT'/><input type='hidden' id='addonServicesDTO.addonServicesList["+count+"].misc' name='addonServicesDTO.addonServicesList["+count+"].misc' value='true'/></td>";											 
row=row+"<td class='col4 even' style='text-align:right' > <input type='text' id='addonServicesDTO.addonServicesList["+count+"].providerPaid' name='addonServicesDTO.addonServicesList["+count+"].providerPaid'";
row=row+"theme='simple' class='text' style='color: #666; padding: 0px; width: 60px; text-align:right; border: 0px; background: transparent;' readonly='true'/> </td>";
row=row+"<td  class='col5 odd last'><input type='hidden' id='addonServicesDTO.addonServicesList["+count+"].endCustomerCharge' name='addonServicesDTO.addonServicesList["+count+"].endCustomerCharge' ";
row=row+"theme='simple' class='text'  style='width: 60px; text-align:right'/> <input type='hidden' id='addonServicesDTO.addonServicesList["+count+"].margin' name='addonServicesDTO.addonServicesList["+count+"].margin' value='0.0'/> ";
row=row+"<input type='text' id='addonServicesDTO.addonServicesList["+count+"].endCustomerSubtotal' name='addonServicesDTO.addonServicesList["+count+"].endCustomerSubtotal'";																								
row=row+" onchange='addOnObject.calcNewSubtotalForMisc("+count+ ", "+count+", this);' theme='simple' class='text'  style='width: 60px; text-align:right'/></td></tr>	"	;										
	if($('.addonPermitClass').length+1>3){
		$("#addPermit").css("display", "none");											
	}										
$('#addOnDetails .addOnClass:last').after(row);


}

}
var addOnObject = 
{
      calcNewSubtotal : function( index, totalElms )
      {
      		totalElms = $('.addOnClass').length;
    		
    	  var displayTax = document.getElementById('displayTax').value;
    		
            var objQty = document.getElementById('addonServicesDTO.addonServicesList[' + index + '].quantity');
            var objProviderTax = document.getElementById('addonServicesDTO.addonServicesList[' + index + '].providerTax');
            var objTaxPercentage = document.getElementById('addonServicesDTO.addonServicesList[' + index + '].taxPercentage');
            var objProviderPaid = document.getElementById('addonServicesDTO.addonServicesList[' + index + '].providerPaid');
            var objEndCustomerSubtotal = document.getElementById('addonServicesDTO.addonServicesList[' + index + '].endCustomerSubtotal');
            var objEndCustomerCharge = document.getElementById('addonServicesDTO.addonServicesList[' + index + '].endCustomerCharge');
            var objMargin = document.getElementById('addonServicesDTO.addonServicesList[' + index + '].margin');
          
       	  // check to let user select only 1 addon from - [Clean & Maintain] HA / Refrig.
    		if (displayTax) {
    			//var listOfSkus = ['3333430.1.Refrig','3333430.2.Refrig','3333430.3.Refrig','3333430.1.HA','3333430.2.HA','3333430.3.HA'];
    			var objSku = document.getElementById('addonServicesDTO.addonServicesList[' + index + '].sku');
    			if (objSku ) {
				var objSkuGroupType =document.getElementById('addonServicesDTO.addonServicesList[' + index + '].skuGroupType');
				
    				for( i = 0; i < totalElms; i++ ) {
					var objSkuOther = document.getElementById('addonServicesDTO.addonServicesList[' + i + '].sku');
						var objSkuGroupTypeTemp =document.getElementById('addonServicesDTO.addonServicesList[' + i + '].skuGroupType');
						
    					if (index != i && objSkuOther && objSkuGroupType.value == objSkuGroupTypeTemp.value && objSkuGroupType.value != 0) {
    						document.getElementById('addonServicesDTO.addonServicesList[' + i + '].quantity').value = 0;
    						document.getElementById('addonServicesDTO.addonServicesList[' + i + '].providerPaid').value = "";
    						document.getElementById('addonServicesDTO.addonServicesList[' + i + '].endCustomerSubtotal').value = "";
    						document.getElementById('addonServicesDTO.addonServicesList[' + i + '].providerTax').value = "";
    					}
    				}
    			}
    		}
       	
            var providerPaidUnit = (objEndCustomerCharge.value - objEndCustomerCharge.value * objMargin.value);
            var providerPaid = objQty.value * providerPaidUnit;
            var endCustomerSubtotal = objQty.value * objEndCustomerCharge.value;
            
            if( objQty.value > 0 )
            {
                  objProviderPaid.value = this.fmtMoney( providerPaid );
                  objEndCustomerSubtotal.value = this.fmtMoney( endCustomerSubtotal );
                
                if (displayTax && null != objTaxPercentage && null != objProviderTax && null != objTaxPercentage) {
              	  var providerTax =this.fmtMoney( endCustomerSubtotal * (objTaxPercentage.value * 1) / (1 + (objTaxPercentage.value * 1)));
              	  objProviderTax.value = providerTax == '' ? 0.00 : providerTax;
            }
          }
            else
            {
                  objProviderPaid.value = "";
                  objEndCustomerSubtotal.value = "";
                if (displayTax && null != objProviderTax) {
                	objProviderTax.value = "";
                }
            }
            
            if( ! this.doTotals(totalElms) )
            {
                  objEndCustomerSubtotal.value = "";
                  objProviderPaid.value = "";
                if (displayTax && null != objProviderTax) {
                	objProviderTax.value = "";
                }
                  objQty.value = 0;
                  alert( "The total of additional services can not be more than $2500.00.\nPlease enter valid value to proceed further." );
                  this.doTotals( totalElms );
            }
      },

      calcNewSubtotalForMisc : function( index, totalElms, obj, fromSelect )
      {		
    
      		totalElms = $('.addOnClass').length;
            var objQty = document.getElementById('addonServicesDTO.addonServicesList[' + index + '].quantity');
            if(objQty == null){
            	objQty = document.getElementById('qty_' + index);
            }            
            if(!fromSelect){
				document.getElementById('addonServicesDTO.addonServicesList['+index+'].endCustomerCharge').value = obj.value;
            }
            var objProviderPaid = document.getElementById('addonServicesDTO.addonServicesList[' + index + '].providerPaid');
            var objEndCustomerSubtotal = document.getElementById('addonServicesDTO.addonServicesList[' + index + '].endCustomerSubtotal');
            var objEndCustomerCharge = document.getElementById('addonServicesDTO.addonServicesList[' + index + '].endCustomerCharge');
            var objMargin = document.getElementById('addonServicesDTO.addonServicesList[' + index + '].margin');
            if( ! this.validatePositiveDecimal(objEndCustomerCharge.value) )
            {
                  objEndCustomerCharge.value = "0.00";
                  objEndCustomerSubtotal.value = "0.00";
            }
 
            // IF INPUT CHANGED TO 0.00
            if(!fromSelect){
	            if(obj.value == 0.00){
	                  objQty.value = 0;
	             }else if(obj.value > 0.00){
	                  objQty.value = 1;
	             }else if(obj.value == 0){
	                  objEndCustomerCharge.value = "0.00";
	                  objEndCustomerSubtotal.value = "0.00";
	            }
            }
            
            //var providerPaidUnit = this.fmtMoneyNan((1 - objMargin.value) * objEndCustomerCharge.value);
            var providerPaidUnit = (objEndCustomerCharge.value - objMargin.value * objEndCustomerCharge.value);
            var providerPaid = objQty.value * providerPaidUnit;
            var endCustomerSubtotal = objQty.value * objEndCustomerCharge.value;
            
            if( objQty.value > 0 )
            {
                  objProviderPaid.value = this.fmtMoney( providerPaid );
                  objEndCustomerSubtotal.value = this.fmtMoney( endCustomerSubtotal );
            }
            else
            {
                  objProviderPaid.value = "";
                  objEndCustomerSubtotal.value = "";
            }
            
            if( ! this.doTotals(totalElms) )
            {
                  objEndCustomerSubtotal.value = "";
                  objProviderPaid.value = "";
                  objQty.value = 0;
                  alert( "The total of additional services can not be more than $2500.00.\nPlease enter valid value to proceed further." );
                  this.doTotals( totalElms );
            }
      },
      doTotals : function( totalElms )
      {
     
   			totalElms = $('.addOnClass').length;
   			var displayTax = document.getElementById('displayTax').value;
   			
            var objEndCustomerSubtotal;
            var customerTotal = 0;
            var providerTotal = 0;
            var providerTaxTotal = 0;
            var endCustValue = null;
            var providerPaidUnit = 0.0;
            var providerPaid = 0.0;
            var providerTax = 0.0;
             var permitSkuInd = 0;
             var tot=0;
            for( i = 0; i < totalElms; i++ )
            {
            	  //customerTotal = 0;
            	var endCustObj = document.getElementById('addonServicesDTO.addonServicesList[' + i + '].endCustomerSubtotal');            	
            	if(endCustObj != null && endCustObj.value != null && endCustObj.value != '')
            	{
                  endCustValue = endCustObj.value;
                }
                else
                {
                  endCustValue = 0.0 * 1;
                }
                  customerTotal = customerTotal + ( 1 * endCustValue );
                  var objMargin = document.getElementById('addonServicesDTO.addonServicesList[' + i + '].margin');
                  var objSku = document.getElementById('addonServicesDTO.addonServicesList[' + i + '].sku');
                  var skuVal = objSku.value;
                  if(skuVal=="99888"){
                  
                  	 var objPermitType = document.getElementById('addonServicesDTO.addonServicesList[' + i + '].permitType');
                  	 if(objPermitType){
                  	 var selected_index = objPermitType.selectedIndex;
                  	 if(objPermitType.value!="-1" ){
					 var text = objPermitType.options[selected_index].text;
                  	 var desc = text;
                  	 document.getElementById('addonServicesDTO.addonServicesList[' + i + '].description').value=desc;
                  	  }
			                   	 var quantity = document.getElementById('addonServicesDTO.addonServicesList[' + i + '].quantity').value;
			                   	 if(quantity==1 ||quantity=='1'){
			                   	 	permitSkuInd = 1;
                  	 	
                  	 }
                  	}
                  }
                  var objEndCustomerCharge = document.getElementById('addonServicesDTO.addonServicesList[' + i + '].endCustomerCharge');
                  var objQty = document.getElementById('addonServicesDTO.addonServicesList[' + i + '].quantity');
                  if(objQty == null){
	                  objQty = document.getElementById('qty_' + i );
                  }
                  
                  providerPaidUnit = (objEndCustomerCharge.value - objMargin.value * objEndCustomerCharge.value) ;                  
                  providerPaid = (objQty.value * 1) * providerPaidUnit;
                  
                  var objTaxPercentage = document.getElementById('addonServicesDTO.addonServicesList[' + i + '].taxPercentage');
                  //code cahnges for SLT-2572
                  var  miscObj = document.getElementById('addonServicesDTO.addonServicesList[' + i + '].misc');
				  var checkMisc =true;
				  var tempEnsCust= null;
				  if(miscObj != null ){
					   tempEnsCust = document.getElementById('addonServicesDTO.addonServicesList[' + i + '].tempEnsCust');
					   if(tempEnsCust.value  == 0.0 ){
							tempEnsCust.value =  this.fmtMoney( endCustValue );
					   }
						else{
							var temtax = (1 * tempEnsCust.value) * (objTaxPercentage.value * 1) / (1 + (objTaxPercentage.value * 1));
							var endCustTaxValue =this.fmtMoney((tempEnsCust.value*1.0 )+ temtax);
							if (endCustTaxValue ==  endCustValue ){
							checkMisc =false;
							}
							else{
								tempEnsCust.value =  this.fmtMoney( endCustValue );
							}
						}   
				  }
                  
                  if (displayTax && null != objTaxPercentage) {
                	  if(miscObj != null ){
                		  providerTax = (1 * tempEnsCust.value) * (objTaxPercentage.value * 1) / (1 + (objTaxPercentage.value * 1));
                		  providerPaid += providerTax *1.0;
                	  }
                	  else{
                	 	 providerTax = (1 * endCustValue) * (objTaxPercentage.value * 1) / (1 + (objTaxPercentage.value * 1));
                	  }
                	  if(providerTax != ""){
    					  var taxObj = document.getElementById('addonServicesDTO.addonServicesList[' + i + '].tax');
    						  if(taxObj != null){
    						  taxObj.value = this.fmtMoney(providerTax);
    						  taxObj.type = "text";
    						  taxObj.disabled = true;
    						  }
    						}
                	  
                  else if (providerTax == "" && endCustValue != "") {
						var taxObj = document
								.getElementById('addonServicesDTO.addonServicesList['
										+ i + '].tax');
						if (taxObj != null) {
							taxObj.value = "0.00";
							taxObj.type = "text";
							taxObj.disabled = true;
						}
					}
                	  
                	//start code changes for SLT-2572
                	 					
						var objProviderPaidTemp = document.getElementById('addonServicesDTO.addonServicesList[' + i + '].providerPaid');
						if(miscObj != null  && objProviderPaidTemp != null  && checkMisc  ){
							customerTotal-= (1 * endCustValue);
							providerPaid=(1 * endCustValue)+providerTax;
							endCustObj.value = this.fmtMoney( providerPaid );
							objProviderPaidTemp.value = this.fmtMoney( providerPaid );
							customerTotal+= providerPaid;
						}
						//end code changes for SLT-2572
                	  
				}
				if (endCustValue == "") {
					var taxObj = document
							.getElementById('addonServicesDTO.addonServicesList['
									+ i + '].tax');
					if (taxObj != null) {
						taxObj.type = "hidden";
					}
				}
                  
                  var skipReqAddonObj = document.getElementById('addonServicesDTO.addonServicesList[' + i + '].skipReqAddon');
                  if(skipReqAddonObj != null && skipReqAddonObj.value == 'true')
                  {
                    providerPaid = 0.0 * 1.0;                    
                  }
                  providerTotal += providerPaid;
                  
                  providerTaxTotal += providerTax; 
                  
                  tot=i;
               }
              if(permitSkuInd == 1 ){
	               	$('#inner_document_grid').contents().find('#autocloseWarning').css("display","block"); 
	               	}
	               	if(permitSkuInd == 0 && !(document.getElementById('permitTaskList[0].finalPrice'))){
	               	 $('#inner_document_grid').contents().find('#autocloseWarning').css("display","none"); 
	               		
        	}
        
            if( customerTotal > 2500.00 )
            {
                  return false;
            }
            else
            {
       
                  var objEndCustomerSubtotalTotal = document.getElementById('addonServicesDTO.endCustomerSubtotalTotal');
                  var objEndCustomerAddOnSubtotalTotal = document.getElementById('endCustomerAddOnSubtotalTotal');
                  var objHidEndCustomerAddOnSubtotalTotal = document.getElementById('hidEndCustomerAddOnSubtotalTotal');
                   
                  var hiddenEndCustomerSubtotalTotal = document.getElementById('endCustomerSubtotalTotal');
                  customerTotal = this.fmtMoney( customerTotal );
                  if(customerTotal!=null){
                  if(objEndCustomerSubtotalTotal!= null){
                  objEndCustomerSubtotalTotal.value = customerTotal;
                  }
                  if(objHidEndCustomerAddOnSubtotalTotal!=null){
                  objHidEndCustomerAddOnSubtotalTotal.value = customerTotal;
                  }
                  if(objEndCustomerAddOnSubtotalTotal!=null){
                  objEndCustomerAddOnSubtotalTotal.value = customerTotal;
                  }
                  if(hiddenEndCustomerSubtotalTotal!=null){
               	  hiddenEndCustomerSubtotalTotal.value=customerTotal;
               	  }
               	  }
                 
                       providerTotal = this.fmtMoney( providerTotal );
                       providerTaxTotal = this.fmtMoney( providerTaxTotal );
                     
           
                  if( customerTotal == "" )
                        {
                        customerTotal = "0.00";
                        
                        }
                  if( providerTotal == ""  ){
                	  providerTotal = "0.00";
                  }
                  
                  if (providerTaxTotal == ""  ) {
                	  providerTaxTotal ="0.00";  
                  }
                  
                  // Variables set for pricing summary widget      
 			      jQuery(".addOnValue").html(providerTotal );
                  jQuery(".customerTotalVal").html(customerTotal);      
                
                  var objProviderPaidTotal = document.getElementById('addonServicesDTO.providerPaidTotal');
                  if(objProviderPaidTotal!=null){
                  objProviderPaidTotal.value = providerTotal;
                  if(objEndCustomerAddOnSubtotalTotal!=null){
                  	objEndCustomerAddOnSubtotalTotal.value = providerTotal;
                  }	
                  
                  var objProviderTaxPaidTotal = document.getElementById('addonServicesDTO.providerTaxPaidTotal');
                  if (null != objProviderTaxPaidTotal) {
                	  objProviderTaxPaidTotal.value = providerTaxTotal;
                	}
                 }
                  //alert( document.getElementById("pmtTypeCA").checked );
                  //alert( document.getElementById("pmtTypeCC").checked );
             
                  if( document.getElementById("pmtTypeCA")!=null && document.getElementById("pmtTypeCA").checked )
                  {
                        var objCheckAmount = document.getElementById("addonServicesDTO.checkAmount");
                        // addonServicesDTO.checkAmount
                        if( objCheckAmount.value != "" && objEndCustomerSubtotalTotal !=null)
                        var checkAmount= this.fmtMoney(objCheckAmount.value);
                              if( checkAmount != objEndCustomerSubtotalTotal.value )
                              {
                                    //alert( "Payment amount mismatch.\nPlease re-enter the customer check amount." );
                                    objCheckAmount.value = "";
                              }
                  }

					
                  else if( document.getElementById("pmtTypeCC")!=null && document.getElementById("pmtTypeCC").checked )
                  {
                        var objAmtAuthorized = document.getElementById("addonServicesDTO.amtAuthorized");
                        // addonServicesDTO.amtAuthorized
                        if( objAmtAuthorized.value != "" && objEndCustomerSubtotalTotal!=null)
                              if( objAmtAuthorized.value != objEndCustomerSubtotalTotal.value )
                              {
                                    //alert( "Payment amount mismatch\nPlease re-enter the customer authorized credit card charge amount" );
                                    objAmtAuthorized.value = "";
                              }
                  }

                  // ELSE ERROR !!!
 				  this.calculate();
                  return true;
            }
      },
     findTotals : function(obj)
      {
      if( ! this.validatePositiveDecimal(obj.value) )
            {
                  obj.value = "0.00";
                  this.calculate();
                  return false;
            }
            
      obj.value = this.fmtMoneyText(obj.value);
      this.calculate();
      },
       calculate : function()
      {
     
    	  // var buyerId = document.getElementById('buyerId').value;
    	   var partsTaxPer = document.getElementById('partsTaxPer').value;
    	   var laborTaxPer = document.getElementById('laborTaxPer').value;
    	   
     var objFinalLaborPriceTotal = document.getElementById('finalLaborPrice');
        
      
        if(objFinalLaborPriceTotal.value=='')
        {
       
       document.getElementById('finalLaborPrice').value="0.0";
        }
        var custTotal=0;
        var custValue = null;
        var endCustDueTotal = 0;
        var objProvidePaidAddOnSubtotalTotal =document.getElementById('addonServicesDTO.providerPaidTotal')
        var objProvideTaxPaidAddOnSubtotalTotal =document.getElementById('addonServicesDTO.providerTaxPaidTotal')
        var objEndCustomerAddOnSubtotalTotal = document.getElementById('hidEndCustomerAddOnSubtotalTotal');
        var objFinalMaxLaborPrice = document.getElementById('soMaxLabor');
        var objFinalLaborPrice = document.getElementById('finalLaborPrice');
         var objFinalMaxPartPrice = document.getElementById('soMaxPart');
        var objFinalPartPrice = document.getElementById('finalPartPrice');
         
        var objFinalPartsTaxPriceLabel = document.getElementById('finalPartsTaxPriceLabel');
        var objFinalLaborTaxPriceLabel = document.getElementById('finalLaborTaxPriceLabel');
        
        var objFinalPartsTaxPrice = document.getElementById('finalPartsTaxPrice');
        var objFinalLaborTaxPrice = document.getElementById('finalLaborTaxPrice');
         
        if(objEndCustomerAddOnSubtotalTotal != null && objEndCustomerAddOnSubtotalTotal.value != null && objEndCustomerAddOnSubtotalTotal.value != '')
            	{
                  custValue = objEndCustomerAddOnSubtotalTotal.value;
                }
        else
                {
                  custValue =  0.0 * 1;
                }
        custTotal = custTotal + ( 1 * custValue );
        endCustDueTotal = custTotal;
        custTotal=0;
        if(objProvidePaidAddOnSubtotalTotal != null && objProvidePaidAddOnSubtotalTotal.value != null && objProvidePaidAddOnSubtotalTotal.value != '')
            	{
                  custValue = objProvidePaidAddOnSubtotalTotal.value;
                }
        else
                {
                  custValue =  0.0 * 1;
                }
        custTotal = custTotal + ( 1 * custValue );
        
        /* if (objProvideTaxPaidAddOnSubtotalTotal != null && objProvideTaxPaidAddOnSubtotalTotal.value != null && objProvideTaxPaidAddOnSubtotalTotal.value != '') {
            custValue = objProvideTaxPaidAddOnSubtotalTotal.value;
        } else {
		    custValue =  0.0 * 1;
		}
		custTotal = custTotal + ( 1 * custValue ); */
        
        if(objFinalMaxLaborPrice != null && objFinalMaxLaborPrice.value != null && objFinalMaxLaborPrice.value != '')
            	{
                  custValue = (objFinalMaxLaborPrice.value * 1 ) + ((objFinalMaxLaborPrice.value * 1) * (laborTaxPer * 1));
                }
                else
                {
                  custValue =  0.0 * 1;
                }
        custTotal = custTotal + ( 1 * custValue );
        objFinalLaborPrice.value = this.fmtMoney(custValue) == "" ? "0.00" : this.fmtMoney(custValue);
        
        if(objFinalMaxPartPrice != null && objFinalMaxPartPrice.value != null && objFinalMaxPartPrice.value != '')
            	{
                  custValue = (objFinalMaxPartPrice.value * 1) +  ((objFinalMaxPartPrice.value * 1) * (partsTaxPer * 1));
                }
                else
                {
                  custValue = 0.0 * 1;
                } 
        custTotal = custTotal + ( 1 * custValue );
        objFinalPartPrice.value = this.fmtMoney(custValue) == "" ? "0.00" : this.fmtMoney(custValue);
        //objFinalLaborPrice.value = this.fmtMoney(objFinalLaborPrice.value * 1+ custValue * 1);
        var totalElms = $('.permitTaskRow').length;
        var permitTotal=0;
        var permitCustTotal=0;
        var endCustValue = null;
        var endCustValueSell = null;
        var finalTasksPrice = objFinalLaborPrice.value;
        var finalPartsPrice = 0.00;
        if(objFinalMaxPartPrice != null){
        	finalPartsPrice = (objFinalMaxPartPrice.value * 1) + ((objFinalMaxPartPrice.value * 1) * (partsTaxPer * 1));
        }	
        var totalPrice = (finalTasksPrice * 1) + (1 * finalPartsPrice);
        
        // if (buyerId == '3333' || buyerId == 3333 || buyerId == '7777' || buyerId == 7777) {
        //	totalPrice = (totalPrice * 1) + ((finalTasksPrice * 1) * (laborTaxPer * 1)) + ((finalPartsPrice * 1) * (partsTaxPer * 1));
        // }        
        if (null != objFinalLaborTaxPriceLabel && null != objFinalPartsTaxPriceLabel) {
        	
			var finalLaborTaxPriceTemp  = objFinalMaxLaborPrice != null ? (this.fmtMoney((objFinalMaxLaborPrice.value * 1) * (laborTaxPer * 1))) : "";
        	var finalPartTaxPriceTemp  = objFinalMaxPartPrice != null ? (this.fmtMoney((objFinalMaxPartPrice.value * 1) * (partsTaxPer * 1))) : ""; 
        	
	        objFinalLaborTaxPriceLabel.innerHTML = finalLaborTaxPriceTemp == "" ? "0.00" : finalLaborTaxPriceTemp;
	        objFinalPartsTaxPriceLabel.innerHTML = finalPartTaxPriceTemp == "" ? "0.00" : finalPartTaxPriceTemp; 
	        
	        objFinalLaborTaxPrice.value = finalLaborTaxPriceTemp == "" ? "0.00" : finalLaborTaxPriceTemp;
	        objFinalPartsTaxPrice.value = finalPartTaxPriceTemp == "" ? "0.00" : finalPartTaxPriceTemp; 
      	}

        for( i = 0; i < totalElms; i++ )
            {
            	 
            	var endCustObj = document.getElementById('permitTaskList[' + i + '].finalPrice');  
            	var endCustObjSell = document.getElementById('permitTaskList[' + i + '].sellingPrice');
            	var objPermitType = document.getElementById('permitTaskList[' + i + '].permitType');
                var selected_index = objPermitType.selectedIndex;
                if(objPermitType.value!="-1" && objPermitType.value!=-1){
					 var text = objPermitType.options[selected_index].text;
                  	 var desc = text;
                  	 document.getElementById('permitTaskList[' + i + '].permitTaskDesc').value=desc;
                }       	
            	if(endCustObj != null && endCustObj.value != null && endCustObj.value != '')
            	{
                  endCustValue = endCustObj.value;
                }
                else
                {
                  endCustValue = 0.0 * 1;
                }
                if(endCustObjSell != null && endCustObjSell.value != null && endCustObjSell.value != '')
            	{
                  endCustValueSell = endCustObjSell.value;
                }
                else
                {
                  endCustValueSell = 0.0 * 1;
                }
                
                
                permitTotal = permitTotal + ( 1 * endCustValue );
                if((1 * (endCustValue - endCustValueSell))<=0){
                	objFinalLaborPrice.value = this.fmtMoney(objFinalLaborPrice.value * 1+ (1 * (endCustValue)));
                	document.getElementById('permitTaskList[' + i + '].custChargeLabel').innerHTML="";
                	document.getElementById('permitTaskList[' + i + '].custCharge').value="";
                }else{
                	permitCustTotal = permitCustTotal +(1 * (endCustValue - endCustValueSell));
                	 document.getElementById('permitTaskList[' + i + '].custChargeLabel').innerHTML= "$"+this.fmtMoney(1 * (endCustValue - endCustValueSell));
                	 document.getElementById('permitTaskList[' + i + '].custCharge').value= this.fmtMoney(1 * (endCustValue - endCustValueSell));
                	objFinalLaborPrice.value = this.fmtMoney((objFinalLaborPrice.value * 1) + (1 * (endCustValueSell)));
                }
                
           }
        if(this.fmtMoney((totalPrice * 1) + (permitTotal*1))>0){
        	document.getElementById("finalWithoutAddonsLabel").innerHTML="$"+this.fmtMoney((totalPrice * 1)+(permitTotal*1));
        	document.getElementById("finalWithoutAddons").value="$"+this.fmtMoney((totalPrice * 1)+(permitTotal*1));
        }else{
        	if(document.getElementById("finalWithoutAddonsLabel") != null){
        		document.getElementById("finalWithoutAddonsLabel").innerHTML="$0.00";
        	}
        	if(document.getElementById("finalWithoutAddons") !=null){
        		document.getElementById("finalWithoutAddons").value="$0.00";
        	}
        	
        }
        var addonAmt =0;
        var custAddonAmt=0;
        var providerTax =0;
        if(document.getElementById("addonServicesDTO.providerPaidTotal")){
        	addonAmt=document.getElementById("addonServicesDTO.providerPaidTotal").value;
        	if(addonAmt==""){
        	addonAmt=0;
        	}
        }
        if(document.getElementById("addonServicesDTO.endCustomerSubtotalTotal")){
        	custAddonAmt=document.getElementById("addonServicesDTO.endCustomerSubtotalTotal").value;
        	if(custAddonAmt==""){
        	custAddonAmt=0;
        	}
        }
        if(document.getElementById("addonServicesDTO.providerTaxPaidTotal")){
        	providerTax=document.getElementById("addonServicesDTO.providerTaxPaidTotal").value;
        	if(providerTax==""){
        		providerTax=0;
        	}
        }
        var amt =0;
        var custAmt=0;
        if(document.getElementById("finalWithoutAddons")){
        	amt=this.fmtMoney(totalPrice * 1+(permitTotal*1));
        	if(amt==""){
        	amt=0;
        	}
        }
        if(document.getElementById("custChargeWithoutAddons")){
        	custAmt=this.fmtMoney(permitCustTotal);
        	if(custAmt==""){
        	custAmt=0;
        	if(document.getElementById('permitInd')){
	         document.getElementById("custChargeWithoutAddonsLabel").innerHTML="$0.00";
	         document.getElementById("custChargeWithoutAddons").value="$0.00";
	        }else{
	         document.getElementById("custChargeWithoutAddonsLabel").innerHTML="";
	         document.getElementById("custChargeWithoutAddons").value="";
	        }
        	
        	}else{
        	document.getElementById("custChargeWithoutAddonsLabel").innerHTML="$"+this.fmtMoney(permitCustTotal);
        	document.getElementById("custChargeWithoutAddons").value="$"+this.fmtMoney(permitCustTotal);
        	}
        }
        if(this.fmtMoney(addonAmt *1+amt*1)>0){
        document.getElementById("finalTotalLabel").innerHTML= "$"+this.fmtMoney(addonAmt *1+amt*1);
        document.getElementById("finalTotal").value= "$"+this.fmtMoney(addonAmt *1+amt*1);
        }else{
        document.getElementById("finalTotalLabel").innerHTML= "$0.00";
        document.getElementById("finalTotal").value= "$0.00";
        }
        if(this.fmtMoney(custAddonAmt *1+custAmt*1)>0){
		if(document.getElementById("custChargeFinalLabel") != null){
			document.getElementById("custChargeFinalLabel").innerHTML= "$"+this.fmtMoney(custAddonAmt *1+custAmt*1);
		}
        document.getElementById("custChargeFinal").value= "$"+this.fmtMoney(custAddonAmt *1+custAmt*1);
        }else{
        if(document.getElementById('permitInd')){
         document.getElementById("custChargeFinalLabel").innerHTML= "$0.00";
         document.getElementById("custChargeFinal").value= "$0.00";
        }else{
         document.getElementById("custChargeFinalLabel").innerHTML= "";
         document.getElementById("custChargeFinal").value= "";
         }
        }
        if(document.getElementById("permitTaskAddonPrice") != null){
		document.getElementById("permitTaskAddonPrice").value=this.fmtMoney(permitCustTotal);
        }
        custTotal = custTotal+(1*permitTotal);
        endCustDueTotal =endCustDueTotal + (1 *permitCustTotal);
        
        custTotal =  this.fmtMoney(custTotal);
        endCustDueTotal =  this.fmtMoney(custAddonAmt *1+custAmt*1);
        var objEndCustomerFinalTotal = document.getElementById('endCustomerFinalTotal');
        var objEndCustomerDueFinalTotal = document.getElementById('endCustomerDueFinalTotal');
        var objHidEndCustomerAddOnSubtotalTotal = document.getElementById('hidEndCustomerAddOnSubtotalTotal');
        var payment = 1;
        if(objHidEndCustomerAddOnSubtotalTotal.value==""||objHidEndCustomerAddOnSubtotalTotal.value=="0.00"){
        	payment = 0;
        }
        if(objEndCustomerFinalTotal != null){
		objEndCustomerFinalTotal.value = custTotal;
        }

        objEndCustomerDueFinalTotal.value = endCustDueTotal;
        if(endCustDueTotal=="" ){
        objEndCustomerDueFinalTotal.value = "0.00";
        }
        if(endCustDueTotal!="" ||payment==1){
        $("#togglePayment").css('display','block');
        }
        
        if(endCustDueTotal=="" && payment==0){
        $("#togglePayment").css('display','none');
        }
      },
      validatePaymentAmount : function( objCheckAmount )
      {
           // Commenting for JIRA SL-17898
           // var objSubtotalTotal = document.getElementById('addonServicesDTO.endCustomerSubtotalTotal');
            var objSubtotalTotal = document.getElementById('endCustomerDueFinalTotal');
            var checkAmount = this.trim(objCheckAmount.value);
            var subtotalTotal = this.trim(objSubtotalTotal.value);
            if( this.validatePositiveDecimal(checkAmount) )
            { 
            	checkAmount = this.fmtMoney( checkAmount );
                subtotalTotal = this.fmtMoney( subtotalTotal );
                objCheckAmount.value = checkAmount;
                if( checkAmount != subtotalTotal )
                  {
                        alert("Pre Authorization amount does not match with the Total Amount("+ subtotalTotal + "). Please confirm your selections. The Miscellaneous SKU may be used for price adjustments. Please contact Sears for assistance or questions." );
                        objCheckAmount.value = "";
                  }
            }
            else
            {
                  objCheckAmount.value = "";
            }
      },
      validatePositiveInteger : function( value )
      {
            // USE JAVAACRIPT REGEX
            if( value == "" )
                  value = 0;
            if(/^-?\d+$/.test(value))
                  if( value >= 0 )
                        return true;
            return false;
      },
      validatePositiveDecimal : function ( value )
      {
            value = this.trim(value);
            // USE JAVAACRIPT REGEX
            if( value == "" )
                  value = "0.00";
                  
            if(/^-?\d*(\.\d{1,2})?$/.test(value))
                  if( value >= 0.00 )
                        return true;
                        
            alert( "Please enter a valid dollar amount" );
            return false;
      },
      trim : function(myString)
      {
            return myString.replace( /^\s+/g,'').replace(/\s+$/g,'')<%=".replace(/\\$/g,'')"%>;
      },
      fmtMoney : function( mnt ) 
      {
          mnt -= 0;
          mnt = (Math.round(mnt*100))/100;
          var x = (mnt == Math.floor(mnt)) ? mnt + '.00' 
                    : ( (mnt*10 == Math.floor(mnt*10)) ? 
                             mnt + '0' : mnt);
                             
          if( x > 0 )
            return x;
          return "";
      },
      fmtMoneyText : function( mnt ) 
      {
          mnt -= 0;
          mnt = (Math.round(mnt*100))/100;
          var x = (mnt == Math.floor(mnt)) ? mnt + '.00' 
                    : ( (mnt*10 == Math.floor(mnt*10)) ? 
                             mnt + '0' : mnt);
      
          if( x > 0 )
            return x;
            
           if( x != 0 )
           alert( "Please enter a valid dollar amount" );
          return "0.00";
          
      },
      fmtMoneyNan : function( mnt ) 
      {
            if (mnt == 0){
                  return mnt;
            }
          mnt -= 0;
          mnt = (Math.round(mnt*100))/100;
          var x = (mnt == Math.floor(mnt)) ? mnt + '.00' 
                    : ( (mnt*10 == Math.floor(mnt*10)) ? 
                             mnt + '0' : mnt);
                             
          if( x > 0 )
            return x;
          return 0;
      },
      resetPermitType : function(index){
      	if(document.getElementById('addonServicesDTO.addonServicesList[' + index + '].permitType'))
      		{
      			if(document.getElementById('addonServicesDTO.addonServicesList[' + index + '].permitType').value == -1)
      			{
      				document.getElementById('addonServicesDTO.addonServicesList[' + index + '].description').value = "";      				
      }
      		}
      }
      
}
function initialize(totalElms)
{

      for( j = 0; j < totalElms - 1; j++ )
      {
            addOnObject.calcNewSubtotal( j, totalElms );
      }
      addOnObject.calcNewSubtotalForMisc( totalElms - 1, totalElms, document.getElementById('addonServicesDTO.addonServicesList['+(totalElms-1)+'].quantity') );
}
      
</script>

		<script type="text/javascript">
			var wasSubmitted=false;
			var specificDate = false;
			var prevModal = null;
			var Addflag = false;	
			var visitIdStr = null;
			var soIdStr = null;
			var arrivalDateStr = null;
			var	departureDateStr = null;
			var buttonPressed = null;
			var assignButtonPressed= 'true';
			
			function closeBucks(){
				 var acceptTerms = document.getElementById('acceptCheck');
                 acceptTerms.checked = false;
			}
			function closeBucksWarn(){
				var acceptTerms = document.getElementById('acceptCheck');
                acceptTerms.checked = false;
			}
			
			function checkBeforeSubmit(){
			      if(!wasSubmitted) {
			        wasSubmitted = true;
			        return wasSubmitted;
			      }
			      return false;
			    }    
			
			function agreeBucks(){				
				if(buttonPressed=="1"){				
					buttonPressed = 0;						
					jQuery.get('soDetailsQuickLinks!agreeBucks.action', callBackFuncForCondAccept);					
				}else{
					jQuery.get('soDetailsQuickLinks!agreeBucks.action',callBackFunc);
				}
				
			}
			
			function callBackFunc(){
				document.getElementById('captcha').style.display = "inline";			
			}	
			function callBackFuncForCondAccept(){
				check1();			
			}
			
			function SubmitAcceptServiceOrder(){
						var soId = '${SERVICE_ORDER_ID}';
						var groupId = '${groupOrderId}';
						var resId = '${routedResourceId}';
                  		var pageY;
						var selectedSlotId;
						var selectedPreferenceId ;
						var appointmentQueryParam="";
                  		
						if( $("#slotsAvailable") && $("#slotsAvailable").val() == "yes"){
							 selectedSlotId = $("#selectedSlotId").val();
							 selectedPreferenceId = $("#selectedPreferenceId").val();
							 appointmentQueryParam='&selectedSlotId='+selectedSlotId+'&selectedPreferenceId='+selectedPreferenceId;
	                  		
							
							if(selectedSlotId == "" || selectedPreferenceId == "" ){
	                  			document.getElementById("divAppointValidateMessage").innerHTML = "Please select your preference for appointment";
	                  			document.getElementById("divAppointValidateMessage").style.color = "Red";
	                  			return false;
	                  		}else{
	                  			document.getElementById("divAppointValidateMessage").innerHTML = "";
	                  		}
						}
				    	if(typeof(window.pageYOffset)=='number') {
				       		pageY=window.pageYOffset;
				       	}
				    	else {
				       		pageY=document.documentElement.scrollTop;
				        }
				        
				    	//var type = $('input:radio[name=assignmentType]:checked').val();
				    	var type = "typeFirm";
				    	if($("#soUsingSku").is(':checked')|| $("#notDispathcerInd").attr("value")=="true"){
				    	//Assign for provider 
				    		type = "typeProvider";
				    		
				    		if(document.getElementById('selectedResourceId')!= null ){
					       		var resourceId= document.getElementById('selectedResourceId').value;
	                  	   	 	if (document.getElementById('securityCode') != null) {
									var securityCode = document.getElementById('securityCode').value;
									window.location = '${contextPath}/soDetailsAcceptSO.action?securityCode=' + securityCode + '&position=' + pageY + '&acceptedFirmResId='+resourceId +'&assignee='+type+'&soId='+soId+'&groupOrderId='+groupId+'&resId='+resId+appointmentQueryParam;
								} else {
									window.location = '${contextPath}/soDetailsAcceptSO.action?acceptedFirmResId='+resourceId + '&assignee='+type+'&soId='+soId+'&groupOrderId='+groupId+'&resId='+resId+appointmentQueryParam;
								}
							}
				    		else if(document.getElementById('routedResourceId')!= null ){
					       		var resourceId= document.getElementById('routedResourceId').value;
					       		if (document.getElementById('securityCode') != null) {
									var securityCode = document.getElementById('securityCode').value;
									window.location = '${contextPath}/soDetailsAcceptSO.action?securityCode=' + securityCode + '&position=' + pageY + '&acceptedFirmResId='+resourceId +'&assignee='+type+'&soId='+soId+'&groupOrderId='+groupId+'&resId='+resId+appointmentQueryParam;
								} else {
									window.location = '${contextPath}/soDetailsAcceptSO.action?acceptedFirmResId='+resourceId + '&assignee='+type+'&soId='+soId+'&groupOrderId='+groupId+'&resId='+resId+appointmentQueryParam;
								}
				    		}
				    		else{
								if (document.getElementById('securityCode') != null) {
									var securityCode = document.getElementById('securityCode').value;
									window.location = '${contextPath}/soDetailsAcceptSO.action?securityCode=' + securityCode + '&position=' + pageY+'&soId='+soId+'&groupOrderId='+groupId+'&resId='+resId+appointmentQueryParam;
								} else {
									window.location = '${contextPath}/soDetailsAcceptSO.action?soId='+soId+'&groupOrderId='+groupId+'&resId='+resId+appointmentQueryParam;
								}
							}
				    	}else{ //Assign for Firm
				    		if (document.getElementById('securityCode') != null) {
								var securityCode = document.getElementById('securityCode').value;
								window.location = '${contextPath}/soDetailsAcceptSO.action?securityCode=' + securityCode + '&position=' + pageY + '&assignee='+type+'&soId='+soId+'&groupOrderId='+groupId+'&resId='+resId+appointmentQueryParam;
							} else {
								window.location = '${contextPath}/soDetailsAcceptSO.action?assignee='+type+'&soId='+soId+'&groupOrderId='+groupId+'&resId='+resId+appointmentQueryParam;
							}
				    	}
				    	
				    	/*if('typeFirm' == type){
				    		if (document.getElementById('securityCode') != null) {
								var securityCode = document.getElementById('securityCode').value;
								window.location = '${contextPath}/soDetailsAcceptSO.action?securityCode=' + securityCode + '&position=' + pageY + '&assignee='+type;
							} else {
								window.location = '${contextPath}/soDetailsAcceptSO.action?assignee='+type;
							}
				    	}else if('typeProvider' == type){
				    		if(document.getElementById('selectedResourceId')!= null ){
					       		var resourceId= document.getElementById('selectedResourceId').value;
	                  	   	 	if (document.getElementById('securityCode') != null) {
									var securityCode = document.getElementById('securityCode').value;
									window.location = '${contextPath}/soDetailsAcceptSO.action?securityCode=' + securityCode + '&position=' + pageY + '&acceptedFirmResId='+resourceId +'&assignee='+type;
								} else {
									window.location = '${contextPath}/soDetailsAcceptSO.action?acceptedFirmResId='+resourceId + '&assignee='+type;
								}
							}else{
								if (document.getElementById('securityCode') != null) {
									var securityCode = document.getElementById('securityCode').value;
									window.location = '${contextPath}/soDetailsAcceptSO.action?securityCode=' + securityCode + '&position=' + pageY;
								} else {
									window.location = '${contextPath}/soDetailsAcceptSO.action';
								}
							}
				    	} */
				        /*if(document.getElementById('selectedResourceId')!= null ){
				       		var resourceId= document.getElementById('selectedResourceId').value;
                  	   	 	if (document.getElementById('securityCode') != null) {
								var securityCode = document.getElementById('securityCode').value;
								window.location = '${contextPath}/soDetailsAcceptSO.action?securityCode=' + securityCode + '&position=' + pageY + '&acceptedFirmResId='+resourceId;
							} else {
								window.location = '${contextPath}/soDetailsAcceptSO.action?acceptedFirmResId='+resourceId;
							}
						}else{
							if (document.getElementById('securityCode') != null) {
								var securityCode = document.getElementById('securityCode').value;
								window.location = '${contextPath}/soDetailsAcceptSO.action?securityCode=' + securityCode + '&position=' + pageY;
							} else {
								window.location = '${contextPath}/soDetailsAcceptSO.action';
							}
						}*/
     			  }
     			  
     		function moveToTermsAndCondition(){
     			 location.href += "#terms_and_condition";	
     		}
     		
     		function moveToProviderPanelDiv(){
			     			
			     			var urlVal=location.href;     		
			     			var ind= urlVal.indexOf("#");     	
			     			
			     			if(ind!=-1){     				
			     				urlVal = urlVal.substring(0,ind);     		 
			 		location.href = encodeURI(urlVal+"#provider_panel"); 					
			     		 
			     			}else{     		
		 encodeURI(location.href += "#provider_panel");	
     		}
     			  
     		}
     		function showTimerOrNot(isCarOrder){
     			var soId = '${SERVICE_ORDER_ID}';
				var groupId = '${groupOrderId}';
     			if(isCarOrder == 'true'){
     				document.getElementById('loadTimerDiv').style.display = "none";	
     				$('#acceptButton').show();	
     			}else{
    				document.getElementById('captcha').style.display = "block";
     				var acceptedResourceId = $('#routedResourceId').attr("value");
     				$('#acceptButton').hide();
     				jQuery('#loadTimerDiv').load("loadTimerAjax.action?acceptedResourceId="+acceptedResourceId+"&assignee=typeProvider&soId="+soId+"&groupOrderId="+groupId, function(){	
     				    loadTimer();	
     					$('#loadTimerDiv').show(); 
     				});	
     			}
     		}	  
     		function toggleCaptcha(vendorBuckInd, primaryVendorInd,isCarOrder){
     					showTimerOrNot(isCarOrder);
     					var resourceId = $('#routedResourceId').attr("value");
     					$('#selectedResourceId').attr("val",resourceId);
     					$('#notDispathcerInd').attr("value","true");
     			  	  	var acceptTerms = document.getElementById('acceptCheck');
     			  	  	if(acceptTerms.checked == true){
     			  	  		if(vendorBuckInd != null && vendorBuckInd == 0){
     			  	  			if(primaryVendorInd == 0){
     			  	  				bucksWarn();
     			  	  			}
     			  	  			else
     			  	  				bucks();
	     			  		}
	     			  		else
	     			  			document.getElementById('captcha').style.display = "block";	
	     			  		}
					    else {
					       	document.getElementById('captcha').style.display = "none";
					       }
					    return;
     		}
     		function processEnter(e){
			            if (window.event) { e = window.event; }
			            if (e.keyCode == 13)
			            {
			              SubmitAcceptServiceOrder();
			            }
			}
			
			function disableCloseButton(){
     					document.getElementById('candpay').style.display = "none";	
	     			  	document.getElementById('candpayImage').style.display = "block";
					   	return;
     		}
     			 
			        
			
			function withDrawCondtionalOffer(){
				var isProviderAdmin= document.getElementById('isProviderAdmin').value;
				var dispatchInd = document.getElementById('isDispatchInd').value;
				if(isProviderAdmin == "true" || dispatchInd == "true"){
					
     				var urlVal=location.href;     		
     				var ind = urlVal.indexOf("#");     	
     			
     				if(ind!=-1){   
     									
     					urlVal = urlVal.substring(0,ind);       							 
			 			location.href = encodeURI(urlVal+"#providerConditionalOfferPanel");				 			   		 					
     		 
				}else{
					encodeURI(location.href += "#providerConditionalOfferPanel");        			 		     			 		
     				}	
     				
				
				}else{
					window.location = '${contextPath}/withdrawConditionalOffer.action';
				}	
				
			}
			
			function withDrawCounterOffer(resourceId, isFromOM){
				window.location = '${contextPath}/withdrawConditionalOffer.action?soId=${SERVICE_ORDER_ID}&groupId=${groupOrderId}&resourceId='+resourceId+'&fromOrderManagement='+isFromOM;
				
			}
			
			function fnEditServiceOrder(){
			var status = '${THE_SERVICE_ORDER_STATUS_CODE}';
			
			if(status==100)
			{
				window.location = '${contextPath}/soWizardController.action?soId=${SERVICE_ORDER_ID}&action=edit&tab=draft';
			}
			else
			{
				window.location = '${contextPath}/soWizardController.action?soId=${SERVICE_ORDER_ID}&action=edit&tab=posted';
			}
			}
			
			function hidMsg(id){
			 var tooltip =document.getElementById(id);
			tooltip.style.visibility="hidden";
			
			}
			function fnCopyServiceOrder(){				   		
		   		window.location = '${contextPath}/soWizardController.action?removeFromSession=WFM&soId=${SERVICE_ORDER_ID}&action=copy&tab=draft';
		   	}			
			 function placeCalendar(left,top) {
				var cal = document.getElementById('calendar')
				cal.style.visibility = 'visible';
				cal.style.left = left;
				cal.style.top = top;
			 }	
			 
			 var rejectCount=0;	
			 function reject(){
			 	rejectCount=0;
			 	var bidInd= document.getElementById("bidInd").value;	 			 	
			 	var adminCheck= document.getElementById("adminCheck").value;	 	
			 	var dispatchInd = document.getElementById("dispatchCheck").value;
				document.getElementById("reject_error").style.display='none';
				document.getElementById("reject_error_msg").innerHTML="";
				jQuery('.messageText').css("display", "none");

				//Added For Reject Reason Code
				document.getElementById("remaining_count").style.display='';
				document.getElementById("remaining_count").innerHTML="Max Length: 225";
				removeWhiteSpaceForComment();
				document.getElementById("comment_optional").style.display='none';	
				//Ending For Reject Reason Code
				
			 	if((adminCheck == 'true' || dispatchInd == 'true') && bidInd != 'ZERO_PRICE_BID'){			 	
 	    			jQuery("#rejectSo").modal({
               			 onOpen: modalOpenAddCustomer,
               			 onClose: rejectSoModalOnClose,
                			persist: true,
               			 	containerCss: ({ width: "550px", height: "400px", marginLeft: "-250px", top: "500px" })
            		});
           		 }else{
 	    	jQuery("#rejectSo").modal({
                onOpen: modalOpenAddCustomer,
                onClose: rejectSoModalOnClose,
                persist: true,
                containerCss: ({ width: "365px", height: "250px", marginLeft: "-250px", top: "500px" })
            });
            
           		 }
            
           jQuery("#customerResponse").fadeOut(5000);
}
		
		function displayRejButton(){	
				jQuery('.rejRemoveButtonClass').css("display", "none");	
				jQuery('.rejButtonClass').css("display", "block");	
		}
		function addBidNoteReply(text, parentActivityId, filterFollowup)
		{
			//alert(text + ' ' + parentActivityId);
			
			textareaId = '#replyText' + parentActivityId;
			replyText = $(textareaId).val().trim();
			if('' == replyText){
				$("#addReplyError"+parentActivityId).show();
				return false;
			}else{
				$("#addReplyError"+parentActivityId).hide();
			} 
			//alert(replyText);
			
			$('#bidNotesDiv').load("providerUtilsAddReplyAjax.action?soId=${SERVICE_ORDER_ID}",
			{
				replyText: replyText,
				parentActivityId: parentActivityId,
				filterFollowup: filterFollowup
			});
		}		

		function reportPostToActivityLog(parentActivityId, filterFollowup)
		{
			//alert(parentActivityId);
			$('#bidNotesDiv').load("providerUtilsReportPostAjax.action?${SERVICE_ORDER_ID}",
			{
				parentActivityId: parentActivityId,
				filterFollowup: filterFollowup
			});
		}		

		function makePostViewableActivityLog(parentActivityId)
		{
			//alert(parentActivityId);
			//alert('makePostViewableActivityLog');
			$('#bidNotesDiv').load("providerUtilsMakePostViewableAjax.action?soId=${SERVICE_ORDER_ID}",
			{
				parentActivityId: parentActivityId
			});
		}		

		function hidePostActivityLog(parentActivityId)
		{
			//alert(parentActivityId);
			//alert('hidePostActivityLog');
			$('#bidNotesDiv').load("providerUtilsHidePostAjax.action?soId=${SERVICE_ORDER_ID}",
			{
				parentActivityId: parentActivityId
			});
		}

		function markPostAsRead(activityId, filterFollowup, associationType) {
			$('#bidNotesDiv').load("providerUtilsMarkPostReadAjax.action?soId=${SERVICE_ORDER_ID}",
			{
				parentActivityId: activityId,
				filterFollowup: filterFollowup,
				associationType: associationType
			});
		}

		function flagPostForFollowup(activityId, associationType) {
			$('#bidNotesDiv').load("providerUtilsFlagPostForFollowupAjax.action?soId=${SERVICE_ORDER_ID}",
			{
				parentActivityId: activityId,
				associationType: associationType
			});
		}

		function filterFollowupPosts(followupOnly) {
			if (followupOnly) {
				$('#bidNotesDiv').load("providerUtilsViewFollowupPostsAjax.action?soId=${SERVICE_ORDER_ID}", {});
			} else {
				$('#bidNotesDiv').load("providerUtilsBidNotesAjax.action?soId=${SERVICE_ORDER_ID}", {});
			}
		}

		function markBidAsRead(activityId, filterFollowup)
		{
			$('#bidResponsesDiv').load("responseStatusMarkBidReadAjax.action?soId=${SERVICE_ORDER_ID}&groupOrderId=${groupOrderId}&status=${THE_SERVICE_ORDER_STATUS_CODE}",
			{
				activityId: activityId,
				filterFollowup: filterFollowup
			});
		}		

		function flagBidForFollowup(activityId)
		{
			$('#bidResponsesDiv').load("responseStatusFlagBidForFollowupAjax.action?soId=${SERVICE_ORDER_ID}&groupOrderId=${groupOrderId}&status=${THE_SERVICE_ORDER_STATUS_CODE}",
			{
				activityId: activityId
			});
		}

		function filterFollowupBids(followupOnly) {
			if (followupOnly) {
				$('#bidResponsesDiv').load("responseStatusViewFollowupBidsAjax.action?soId=${SERVICE_ORDER_ID}&groupOrderId=${groupOrderId}&status=${THE_SERVICE_ORDER_STATUS_CODE}", {});
			} else {
				$('#bidResponsesDiv').load("responseStatusBidResponsesAjax.action?soId=${SERVICE_ORDER_ID}&groupOrderId=${groupOrderId}&status=${THE_SERVICE_ORDER_STATUS_CODE}", {});
			}
		}

function findPos(obj,id) 
{
var val=obj.value;

var curleft = curtop = 0;
if (obj.offsetParent) { 
curleft = obj.offsetLeft
curtop = obj.offsetTop
while (obj = obj.offsetParent) { 
curleft += obj.offsetLeft
curtop += obj.offsetTop
}
}

}
	
	function formatPriceLabourandParts(obj,id) {
		roundToSecondDecimal(obj);
		findPos(obj,id);
	}
function findPosDate(obj,id) 
{
	var val=obj.value;
	if (val != "")	{
	var curleft = curtop = 0;
	if (obj.offsetParent) { 
				curleft = obj.offsetLeft
				curtop = obj.offsetTop
				while (obj = obj.offsetParent) { 
					curleft += obj.offsetLeft
					curtop += obj.offsetTop
				}
			}
			
			
			var flag=1;
			var dateStringNew=new String(val);
		if(dateStringNew.indexOf('/')!=-1){
			var dateParts = dateStringNew.split("/");
		      var selectedMonth = parseInt(dateParts[0],10);
		      var selectedDay =  parseInt(dateParts[1],10);
		 	var dayVal=dateParts[1];
		
		 var monthVal=dateParts[0];
		
		      var yearVal=dateParts[2];
		      var selectedYear = parseInt(dateParts[2],10);
		if((isNaN(monthVal))||(isNaN(dayVal))||isNaN((yearVal))){
		flag=-3;
		}
		      if((selectedMonth >12) ||( selectedMonth <1)||(selectedDay <1)||(selectedDay >31)){
				flag=0;
			}
		      var day='';
		      var month='';
		      var year='';
		      day=selectedDay;
		      month=selectedMonth;
			/*if(yearVal.length()>2){
				flag=-1;
		
			}*/
		      if((selectedYear>=28)&&(selectedYear<1000) ){
		      	year='19'+yearVal;
		      }else if((selectedYear<28)&&(selectedYear<1000) ){
		      	year='20'+yearVal;
		      }else{
		year=yearVal;
		}
		}else{
			flag=-2;
		}
		//var testDate=new Date(selectedDay ,selectedMonth-1,year);
		var testDate=new Date(year,selectedMonth-1,selectedDay);
		
		 if (testDate.getDate() != day) 
		
		{
		flag=-4;
		
		
		}
		
		var today=new Date();
		if(testDate<today){
		flag=-5;
		
		}
		 var tooltip =document.getElementById(id);
		
		tooltip.style.visibility="hidden";
		
		if(flag!=1){
		            tooltip.style.left=(curleft-415)+'px';
		        
		            tooltip.style.top=(curtop-195)+'px';
		             tooltip.style.width='130px';
		            tooltip.style.visibility="visible";
		            tooltip.style.zIndex="5500";
		if(flag==-5){
		 tooltip.innerHTML='* This value is out of range';
		}else{
		tooltip.innerHTML='* The value entered is not valid';
		
		}
		}

	}
}

function ShowToolTip(event)
        {
            var tooltip =document.getElementById("check2");


            tooltip.style.left=event.offsetX;
            tooltip.style.top=event.offsetY;
            tooltip.style.visibility="visible";
        }

 function checkRegEx1(){
     var re5digit = /^\d*(\.\d{2,2})$/;
     var num=document.getElementById('conditionalSpendLimit1').value;
     var ndx = num.search(re5digit);    
     if(ndx == -1){
     document.getElementById('conditionalSpendLimit1').title = "Only numbers, dot with two digit followed the decimal point allowed.";
     }else{
     document.getElementById('conditionalSpendLimit1').title = "";
     }
                                  												
} 





function checkRegEx2(){     
     var re5digit = /^\d*(\.\d{2,2})$/;
     var num=document.getElementById('conditionalSpendLimit2').value;
     var ndx = num.search(re5digit);
      if(ndx == -1){
     document.getElementById('conditionalSpendLimit1').title = "Only numbers, dot with two digit followed the decimal point allowed.";
     }else{
     document.getElementById('conditionalSpendLimit1').title = "";
     }
                                  												
} 
function checkRegEx3(){    
     var re5digit = /^\d*(\.\d{2,2})$/;
     var num=document.getElementById('conditionalSpendLimit3').value;
     var ndx = num.search(re5digit);
      if(ndx == -1){
     document.getElementById('conditionalSpendLimit1').title = "Only numbers, dot with two digit followed the decimal point allowed.";
     }else{
     document.getElementById('conditionalSpendLimit1').title = "";
     }
                                   												
} 
function checkModal4(){
     	jQuery("#check4").modal({
                onOpen: modalOpenAddCustomer,
                onClose: modalOnClose,
                persist: false,
                containerCss: ({ width: "320px", height: "460px", marginLeft: "-250px" })
            });
            
           jQuery("#customerResponse").fadeOut(5000);
}
function check1(){
     	jQuery("#checkModal1").modal({
                onOpen: modalOpenAddCustomer,
                onClose: modalOnClose,
                persist: false,
                containerCss: ({ width: "239px", height: "450px", marginLeft: "-250px" })
            });
            
           jQuery("#customerResponse").fadeOut(5000);
}		
function checkModal2(){
      	jQuery("#check2").modal({
                onOpen: modalOpenAddCustomer,
                onClose: modalOnClose,
                persist: true,
                containerCss: ({ width: "320px", height: "510px", marginLeft: "-250px" })
            });
            
           jQuery("#customerResponse").fadeOut(5000);
}
function checkModal5(){
     	jQuery("#check5").modal({
                onOpen: modalOpenAddCustomer,
                onClose: modalOnClose,
                persist: false,
                containerCss: ({ width: "375px", height: "590px", marginLeft: "-250px",overflow:"scroll" })
            });
            
           jQuery("#customerResponse").fadeOut(5000);
}	
function checkModal3(){
    	jQuery("#check3").modal({
                onOpen: modalOpenAddCustomer,
                onClose: modalOnClose,
                persist: false,
                containerCss: ({ width: "320px", height: "490px", marginLeft: "-250px" })
            });
            
           jQuery("#customerResponse").fadeOut(5000);
}	
function releaseServiceOrder(){
	var soId = '${SERVICE_ORDER_ID}';
	jQuery("#releaseServiceOrder").load('getReleaseReasonCodes.action?soId='+soId,function(){
     	 jQuery("#releaseServiceOrder").modal({
                onOpen: modalOpenAddCustomer,
                onClose: modalOnClose,
                persist: false,
                containerCss: ({ width: "600px", marginLeft: "120px",border:"none"})
            }); 
     		jQuery("#customerResponse").fadeOut(5000);
         	$("#releaseServiceOrder").addClass("jqmWindow");
  			$("#releaseServiceOrder").css("width", "600px");
  			$("#releaseServiceOrder").css("height", "auto");
  			$("#releaseServiceOrder").css("top", "0px");
  			$("#releaseServiceOrder").css("zIndex", 1000);
  			$("#releaseServiceOrder").css("background-color","#FFFFFF");
  			//$("#releaseServiceOrder").css("cursor","wait");
  			$("#releaseServiceOrder").css("border-left-color","#A8A8A8");
  			$("#releaseServiceOrder").css("border-right-color","#A8A8A8");
  			$("#releaseServiceOrder").css("border-bottom-color","#A8A8A8");
  			$("#releaseServiceOrder").css("border-top-color","#A8A8A8");
  			$("#releaseServiceOrder").jqm({modal:true});
  			//$("#modalContainer").hide();
  			
  			jQuery(window).scrollTop(0);
	});
} 
	function closeReleaseModal(){
		jQuery('#releaseServiceOrder').jqmHide();
		 jQuery("#modalOverlay").fadeOut('slow'); 
		 jQuery.modal.close(); 
    }

function reassignServiceOrder(){
     	jQuery("#reassignServiceOrder").modal({
                onOpen: modalOpenAddCustomer,
                onClose: modalOnClose,
                persist: false,
                containerCss: ({ width: "600px", height: "450px", marginLeft: "-150px" })
            });
           jQuery("#customerResponse").fadeOut(5000);
           jQuery(window).scrollTop(0);
}

function closeModal(id) {
	if(id==='addEstimate'){
		closeModalEstimate(id);
	}else{
   
	//jQuery("#" + id).jqmHide();
	jQuery("#modalOverlay").fadeOut('slow'); 
	jQuery.modal.close(); 
	assignButtonPressed='true';
}
}


function closeModalEstimate(id) {
if(id==='addEstimate'){

$('#addEstimate').css('display', 'none');

 var elem = document.getElementById("addEstimate");
 elem.parentElement.removeChild(elem);
//jQuery("#" + id).hide();
}

	
}

function assignServiceOrder(){

	if(assignButtonPressed=='true')
		{
		var soId = '${SERVICE_ORDER_ID}';
	assignButtonPressed='false';
       jQuery("#assignProviderDiv").load("loadAssignServiceOrder.action?soId="+soId,
			function(data) {
    	   jQuery("#assignProviderDiv").modal({
               onOpen: modalOpenAddCustomer,
               onClose: modalOnClose,
               persist: false,
               containerCss: ({ width: "550px", marginLeft: "-50px",border:"none",height: "0px"})
           }); 
    	  
				$("#assignProviderDiv").addClass("jqmWindow");
				$("#assignProviderDiv").css("width", "550px");
				$("#assignProviderDiv").css("height", "auto");
				$("#assignProviderDiv").css("left","53%");
				$("#assignProviderDiv").css("top", "0px");
				$("#assignProviderDiv").css("zIndex", 1000);
				$("#assignProviderDiv").css("background-color","#FFFFFF");
				/*$("#assignProviderDiv").css("border-left-color","#A8A8A8");
				$("#assignProviderDiv").css("border-right-color","#A8A8A8");
				$("#assignProviderDiv").css("border-bottom-color","#A8A8A8");
				$("#assignProviderDiv").css("border-top-color","#A8A8A8");*/
				//comment below code for jquery upgrade
				/* $("#assignProviderDiv").jqm({
					modal : true
				}); */
				jQuery("#assignProviderDiv").fadeIn('slow');
				jQuery('#assignProviderDiv').css('display', 'block');
				//jQuery("#assignProviderDiv").jqmShow();
				jQuery(window).scrollTop(0);
				// assignButtonPressed='true';
				
			});
}
}


function assignServiceProvider(soId, reassign) {
	var selectedProId = $('#selectProvider option:selected').val();
	var selectedProName = $('#selectProvider option:selected').text();
	var selectedProId = $('#selectProvider option:selected').val();
	if (selectedProId == '0') {
		$('#assignResponseMessage').html("Please select a Provider from the list below.");
		$('#assignResponseMessage').show();
	} else {
		window.location ='saveAssignServiceOrder.action?provider='+ selectedProId+'&soId='+soId;
	}
}


function fnManageScope(){
	// SL-17879 Closing the cancellation widget div and Manage Scope widget div if it is already in the page.
	var soId = '${SERVICE_ORDER_ID}';
	jQuery("#cancellationDiv").html("");
	jQuery("#manageScopeDiv").html("");
	jQuery("#manageScopeDialog").load('manageScope.action?soId='+soId,function(){
        jQuery("#manageScopeDialog").modal({
                onOpen: modalOpenAddCustomer,
                onClose: modalOnClose,
                persist: true,
                containerCss: ({ width: "650px", height: "530px"})
            });
        window.scrollTo(1,1);
	});
}

  function cancelSO(){
	  
			var soId = '${SERVICE_ORDER_ID}';
			fnBeforeCancel();
			jQuery('#cancellationDiv').load("soDetailsController_loadForCancelServiceOrder.action?soId="+soId,function() {	  
			var status = jQuery("#wfStateId").val();			
			if(status=="100"){
				jQuery("#cancelHeading").html("Delete Service Order");
				jQuery("#cancelButtonDiv> input:button").val("Delete Service Order");
			}else if(status=="110" || status=="130"){
				jQuery("#cancelHeading").html("Void Service Order");
				jQuery("#cancelButtonDiv> input:button").val("Void Service Order");
			}else{
				jQuery("#cancelHeading").html("Cancel Service Order");
			}
			
			var height = '560px';
			
			jQuery("#action").val("cancel");
			jQuery('#cancelBottomDiv').css({'background-color' : 'rgb(222,221,223)', 'height' : '45px'});
	 	 		jQuery("#cancellationDiv").modal({
		            onOpen: modalOpenAddCustomer,
		            onClose: modalOnClose,
		            persist: true,
		            containerCss: ({ width: "650px", height: height, position: "absolute", top: "05px", marginLeft: "100px" })
	            });
            	window.scrollTo(1,1);
			});	 
            	window.scrollTo(1,1);
    
}  

 function requestReshedule(){
	prepopulateRescheduleFields();
     jQuery("#requestReschedule").modal({
               onOpen: modalOpenAddCustomer,
               onClose: modalOnClose,
               persist: false,
               containerCss: ({ width: "850px", height: "500px", marginLeft: "125px",overflow:"auto"})
           });
     jQuery("#customerResponse").fadeOut(5000);
     jQuery(window).scrollTop(0);
			$(".modalCloseImg").attr("onclick","try{hideCalendarControl();}catch(e){}")
}
function prepopulateRescheduleFields(){
	var radioObj0=document.getElementById("rangeOfDates0");
	var radioObj1=document.getElementById("rangeOfDates1");
	var radioCheckedValue = "";
	if(null != document.getElementById("dateRange")){
		radioCheckedValue=document.getElementById("dateRange").value;
	}
	if(radioObj0.value==radioCheckedValue) {
		radioObj0.checked=true;		
		showRescheduleFixedDate()
	}else if (radioObj1.value==radioCheckedValue){		
		radioObj1.checked=true;		
		showRescheduleRangeDate()
	}else{
		radioObj1.checked=true; 
	}
	//Set preselected value for end time 
	var newStartTime = document.getElementById("newStartTime");	
	var startTime = "";
	if(null != document.getElementById("startTime")){
		startTime = document.getElementById("startTime").value;
	}
	if(startTime!=""){
		var i=0;		
		while ((i < newStartTime.options.length) && (newStartTime.options[i].value != startTime) )
		  {i++;}
		if (i < newStartTime.options.length)
		  {newStartTime.selectedIndex = i;}
	}
	document.getElementById("newStartTime").value="08:00 AM";
	//Set preselected value for end time 
	var newEndTime = document.getElementById("newEndTime");	
	var endTime = "";	
	if(null != document.getElementById("endTime")){
		endTime = document.getElementById("endTime").value;
	}
	if(endTime!=""){
		var i=0;		
		while ((i < newEndTime.options.length) && (newEndTime.options[i].value != endTime))
		  {i++;}
		if (i < newEndTime.options.length)
		  {newEndTime.selectedIndex = i;}
	}
	document.getElementById("newEndTime").value="05:00 PM";
}

function bucks(){
      	jQuery("#bucks").modal({
                onOpen: modalOpenAddCustomer,
                onClose: modalOnClose,
                close: false,
                persist: false,
                containerCss: ({ width: "500px", height: "230px", marginLeft: "-250px" })
            });
           jQuery("#customerResponse").fadeOut(5000);
}
function bucksWarn(){
    	jQuery("#bucksWarn").modal({
                onOpen: modalOpenAddCustomer,
                onClose: modalOnClose,
                close: false,
                persist: false,
                containerCss: ({ width: "500px", height: "80px", marginLeft: "-250px" })
            });
          jQuery("#customerResponse").fadeOut(5000);
}
var requestSubmitted = false;
function calculateSpendLimit(){
	requestSubmitted = false;
      	jQuery("#spendLimitId").modal({      
                onOpen: modalOpenAddCustomer,
                onClose: modalOnClose,
                persist: false,
                containerCss: ({ width: "385px", height: "auto", marginLeft: "-250px" })
            });
            
			window.scrollTo(1,1);            
           jQuery("#customerResponse").fadeOut(5000);
            jQuery(document).ready(function () {
            document.getElementById('increaseSPendLimitResponseMessage').style.display= "none";
            jQuery(".priceHistory").hide();
		$(".priceHistory_head").hide();
		$(".priceHistory_body").hide();
 		$('.priceHistoryIconDetail').mouseover(function(e)
			{
			var x = e.pageX;
			var y = e.pageY;
			var idVal=this.id;
				$("#"+idVal+"priceHistoryDetail").css('top',y-160);
				$("#"+idVal+"priceHistoryDetail").css('left',x-400);
				$("#"+idVal+"priceHistoryDetail").show();
			});
		$('.priceHistoryIconDetail').mouseout(function(e){
			jQuery(".priceHistory").hide();
		});			
		
 });  
}

function checkModal5New(){
     	jQuery("#modal5-1New").modal({
                onOpen: modalOpenAddCustomer,
                onClose: modalOnClose,
                persist: false,
                containerCss: ({ width: "240px", height: "580px", marginLeft: "-250px" ,overflow:"auto"})
            });
            
           jQuery("#customerResponse").fadeOut(5000);
}
 	 	
			function trim(myString){
				return myString.replace( /^\s+/g,'').replace(/\s+$/g,''); 
			}			
			
			function fnReportProblem(){
					//SubmitReportProblem('soDetailsSubmitProblem.action');                  
					  SubmitReportProblem(); 

				
			}
			
			function fnReportResolution(){
	
			if (fnValidateReportResolution() == "true"){
			 		var issComment = $("#resComment").val();
        			var issueComments=$.trim(issComment);
        			$("#resComment").val(issueComments);
					document.getElementById("frmReportResolution").method = "post";
					document.getElementById("frmReportResolution").action = "${contextPath}/soDetailsSubmitResolution.action?soId=${SERVICE_ORDER_ID}";
					document.getElementById("frmReportResolution").submit();
				}
			}
			
			var testing = function doAlert() {
				alert("In test ");	
			}
	    
			
		function acceptWithConditionsBux()
		{
			buttonPressed = 1;				
			bucks();
		}
		function acceptWithConditionsBuxWarn()
		{
			buttonPressed = 1;				
			bucksWarn();
		}
function cancelModal(){
	document.getElementById('conditionalOfferList').value=indexValue=0;
}

//To set the selected counter offer reasons
function setSelectedCounterOfferReasons(){
		var reasons = document.getElementsByName('counterOfferReasons');
		var checkedReasonsList = "";
		for(var count=0;count<reasons.length;count++)
		{
			if(reasons[count].checked == true)
			{
				checkedReasonsList = checkedReasonsList + reasons[count].value+"$"+',';
			}			
		}
		document.getElementById('checkedCounterOfferReasons').value = encodeURI(checkedReasonsList);	
}

//To show the counter offer reasons div
function displayCounterOfferReasonsDiv(theId,theModalID){
	var provRespId = document.getElementById(theId).value;
	jQuery.noConflict();	
	jQuery(document).ready(function($) {
		var queryString = $('#conditionalOfferForm'+provRespId).serialize();
		$.post('providerCouterOfferReasons.action?providerRespId='+provRespId, queryString, function(data) {
			if (data == "")
			{
				alert('no data');
			}
			else
			{	
				$('#counterOfferReasons'+provRespId).html(data);
				$('#counterOfferReasons'+provRespId).show();
			}
		}, "html");
	});
}

function getNextModalNew(theModalID,theId)
		{	
			var indexValue = 0;	
			
			if(!specificDate){	
				indexValue = document.getElementById(theId).value;	
				document.getElementById('conditionalOfferList').value=indexValue;								
			}
			
			displayCounterOfferReasonsDiv(theId,theModalID);	
			prevModal = theModalID;	
		
			switch(indexValue)
			{
				case "1":		
						checkModal2();
						prevModal = "modal2";	
						break;
				case "2":
						checkModal4();
						prevModal = "modal4";
						break;
				case "3":
						checkModal5();
						prevModal = "modal5";
						break;					
				default:if(specificDate){
							if(theModalID=="modal2"){
								checkModal2();
							}else if(theModalID=="modal4"){
								checkModal3();
							}else if(theModalID=="modal5"){
								checkModal5();
							}else if(theModalID=="modal3"){
								checkModal3();
							}else if(theModalID=="modal5-1"){
								checkModal5New();
							}
				 
							
						}
						else{
						    
						     check1();					
							
						}
						specificDate = false;	
						break;							
			}		
		}
		
		function viewMore(){
			var displayedPage=parseInt(document.getElementById("displayedPage").value);
			var totalCount = document.getElementsByName("reject_resource").length;	
			var totalPages = parseInt((totalCount/9))+(totalCount%9==0?0:1);
			var nextPage = displayedPage+1;
			if(displayedPage<totalPages){
				document.getElementById("page"+nextPage).style.display= "";
				document.getElementById("displayedPage").value=nextPage;
				document.getElementById("viewLess").style.display ="";
				document.getElementById("divider").style.display ="";
			}
			if(nextPage==totalPages){
				document.getElementById("viewMore").style.display ="none";
				document.getElementById("divider").style.display ="none";
			}
			var limit = nextPage*9;
		 	if(limit>=totalCount){limit=totalCount;}
			document.getElementById("rejResDispCount").innerHTML=limit;	
		}
		
		function viewLess(){
			var displayedPage=parseInt(document.getElementById("displayedPage").value);
			var totalCount = document.getElementsByName("reject_resource").length;	
			var totalPages = parseInt((totalCount/9))+(totalCount%9==0?0:1);
			var previousPage = displayedPage-1;
			if(displayedPage>1){
				document.getElementById("page"+displayedPage).style.display= "none";
				document.getElementById("displayedPage").value=previousPage;
				document.getElementById("viewMore").style.display ="";
				document.getElementById("divider").style.display ="";
			}
			if(previousPage==1){
				document.getElementById("viewLess").style.display ="none";
				document.getElementById("divider").style.display ="none";
			}
			var limit = previousPage*9;
		 	if(limit>=totalCount){limit=totalCount;}
			document.getElementById("rejResDispCount").innerHTML=limit;	
		}
		

		function checkAll(){
                  
         var rejResources = document.getElementsByName("reject_resource");
		 var totalCount = rejResources.length;	
		 var displayedPage=document.getElementById("displayedPage").value;
		 var limit = displayedPage*9;
		 if(limit>=totalCount){limit=totalCount;}
			for(var i = 0; i < limit; i++){
			rejResources[i].checked = true;
			
			}
          	            
          }
          
	 	function clearAll(){
         
         var bidInd= document.getElementById("bidInd").value;	 			 	
		 var adminCheck= document.getElementById("adminCheck").value;	 	
		 var dispatchInd = document.getElementById("dispatchCheck").value;
         if((adminCheck == 'true' || dispatchInd == 'true') && bidInd != 'ZERO_PRICE_BID'){
         var rejResources = document.getElementsByName("reject_resource");
		 var totalCount = rejResources.length;	
		 var displayedPage=document.getElementById("displayedPage").value;
		 var limit = displayedPage*9;
		 if(limit>totalCount){limit=totalCount;}	
			for(var i = 0; i < limit; i++){
			rejResources[i].checked = false;
			}
         }    
          }

		function submitRejectSO(obj)
		{  	
			var divObj=jQuery(obj).parent();
			var div2Obj=jQuery(divObj).parent();
			var adminCheck= document.getElementById("adminCheck").value;
			var dispatchInd = document.getElementById("dispatchCheck").value;	
			var bidInd= document.getElementById("bidInd").value;		 	
			var checkedResources="";
			var submitForm = document.getElementById('rejectForm');
			jQuery('#reject_error').css("display", "none");	
			 if((adminCheck == 'true' || dispatchInd == 'true') && bidInd != 'ZERO_PRICE_BID'){
				var rejResources = document.getElementsByName("reject_resource");
			
					for(var i = 0; i < rejResources.length; i++){
			
						if(rejResources[i].checked) {
							var val1= rejResources[i].id;			
							var resource_id=val1.substring(16,val1.length);
							checkedResources=checkedResources+","+resource_id;
						}
					}
			
			}else{
				checkedResources = submitForm.zpriceResId.value;
			}
		
			if(checkedResources=='' && (adminCheck == 'true' || dispatchInd == 'true')  && bidInd != 'ZERO_PRICE_BID'){						
				jQuery('#reject_error').css("display", "block");				
				jQuery('#reject_error_msg').html("Please select atleast 1 or more providers first");							
				return false;
					}

			
			var index1 = document.getElementById("reasonCodeList").selectedIndex;
			var selectedReasonCode = jQuery(div2Obj).children(".reasonTableClass").find('select').attr('id');
			var index = jQuery(div2Obj).children(".reasonTableClass").find('select').val();
			var venderRejectRreasonDesc = jQuery(div2Obj).children(".reasonTableClass").find('textarea').val();
			if(index == 0){
				$("#reject_error").css("display","block");
				$("#reject_error_msg").html("Please select reason to reject");
			return false;
			}

			// Reject Reason	
			if((index1==3 || index1==6 || index1==7 || index1==8)&& ($.trim($('#vendor_resp_comment').val())=="")) {
				$('#vendor_resp_comment').val($.trim($('#vendor_resp_comment').val()));    //Added
				$("#reject_error").css("display","block");
				$("#reject_error_msg").html("Please enter comment for selected reason.");
			    return false;	
			}
			$('#vendor_resp_comment').val($.trim($('#vendor_resp_comment').val()));  //Added
			
			// Reject Reason End
			if((rejectCount==0 ||rejectCount=="0")&& (adminCheck == 'true' || dispatchInd == 'true')  && bidInd != 'ZERO_PRICE_BID'){

				jQuery('.messageText').css("display", "block");
				jQuery('.rejButtonClass').css("display", "none");	
				jQuery('.rejRemoveButtonClass').css("display", "block");	
			 	rejectCount= rejectCount+1;
			 	return false;
			}

			document.getElementById("reasonId").value = index;				
			submitForm.resId.value = checkedResources;
			submitForm.reasonText.value = venderRejectRreasonDesc;	
			submitForm.submit();
		}
				
		 function clearRejectSOSelection()
		 {
					clearAll();
					document.getElementById("reasonCodeList").selectedIndex = 0;
					jQuery('.rejButtonClass').css("display", "block");	
					jQuery('.rejRemoveButtonClass').css("display", "none");
					//Adding for Reject Reson Code
					removeWhiteSpaceForComment();
					document.getElementById("remaining_count").style.display='';
					document.getElementById("remaining_count").innerHTML="Max Length: 225";
					document.getElementById("comment_optional").style.display='none';
					//End for Reject Reson Code
		 }
		 
		function removeWhiteSpaceForComment() {
			var myTxtArea = document.getElementById('vendor_resp_comment');
			myTxtArea.value =""; 
		}
		
		function getSpecificDateModalNew(toHideModal,toShowModal,theId)
		{
			var index = document.getElementById("conditionalOfferList").selectedIndex = 0;
			document.getElementById("modal5RadioChecked").checked = true;
			document.getElementById("modal5-1RadioChecked").checked = true;
			document.getElementById("modal2RadioChecked").checked = true;
			document.getElementById("modal3RadioChecked").checked = true;
			
			specificDate = true;
			getNextModalNew(toShowModal,theId);
		}
				
		function clearTextBox(textBoxID)
		{
			if (textBoxID.defaultValue == textBoxID.value) 
				textBoxID.value = "";									
		}
		
		 function clearTextboxDefaultValue(textBoxID,defaultText) {
			      if (textBoxID.value==defaultText) {
					textBoxID.value= "";
					}
			}
			
		function open_popup(page)
	    {
			var soId = '${SERVICE_ORDER_ID}';
			page = page + '?soID=' + soId;
		    window.open(page,'_blank','width=600,height=450,resizable=1,scrollbar=1');
		}
	
		
		function myHandler(id,newValue)
		{
			console.debug("onChange for id = " + id + ", value: " + newValue);
		}
		
            function myHandler(id,newValue)
            {
                  console.debug("onChange for id = " + id + ", value: " + newValue);
            }
            
            function submitForm(resourceId, vendorId, date1, date2, startTime, endTime, responseReasonId, spendLimit)
            {
                  
                  
                  var submitForm = document.getElementById("responseStatusForm");
                  
                  submitForm.resourceId.value = resourceId;
                  submitForm.vendorId.value = vendorId;
                  submitForm.conditionalChangeDate1.value = date1;
                  submitForm.conditionalChangeDate2.value = date2;
                  submitForm.conditionalStartTime.value = startTime;
                  submitForm.conditionalEndTime.value = endTime;
                  submitForm.responseReasonId.value = responseReasonId;
                  submitForm.conditionalSpendLimit.value = spendLimit;
                  
                  submitForm.submit();
            }
            
            function updateComments(resourceId, vendorId)
            {
                  var submitForm = document.getElementById("responseStatusUpdateForm");
                  var callStatusId = document.getElementById("callStatusId");
                  var mktMakerComments = document.getElementById("mktMakerComments");
                  var fromWFM = '${cameFromWorkflowMonitor}';
                  submitForm.resourceId.value = resourceId;
                  submitForm.vendorId.value = vendorId;
                  submitForm.callStatusId.value = callStatusId;
                  submitForm.mktMakerComments.value = mktMakerComments;
                  submitForm.cameFromWorkflowMonitor.value = fromWFM;
                  submitForm.submit();
            }
            //Making changes for defect SL-5033
            function updateMarketMakerComments(resourceId, firstName,lastName,index)
            {
                  var submitForm = document.getElementById("responseStatusUpdateForm");
                  var callStatusObj=document.getElementById("callStatusId["+index+"]");
                  var callStatusId = callStatusObj.options[callStatusObj.selectedIndex].value;
                  var callStatusDesc = callStatusObj.options[callStatusObj.selectedIndex].text;
                  var mktMakerComment = document.getElementById("mktMakerComments["+index+"]").value;
                  var fromWFM = '${cameFromWorkflowMonitor}';
                  submitForm.resourceId.value = resourceId;
                  submitForm.firstName.value = firstName;
                  submitForm.lastName.value = lastName;
                  submitForm.callStatusIdSelected.value=callStatusId;                 
                  submitForm.callStatusDescription.value = callStatusDesc;
                  submitForm.mktMakerComment.value=mktMakerComment;
                  submitForm.cameFromWorkflowMonitor.value = fromWFM;
                  submitForm.submit();
            }
            
            function cleanUpResched(){
                  document.getElementById("newStartDate").value = "";
                  document.getElementById("newEndDate").value = "";
                  document.getElementById("newEndTime").selectedIndex = 0;
                  document.getElementById("newStartTime").selectedIndex = 0;
                  closeModalWindow('requestReschedule');
            }     
            
            function cleanUpReassign(){
                  if(document.getElementById("reassignReason")!=null)
                  {
                        document.getElementById("reassignReason").value = "";
                  }
                  closeModalWindow('reassignServiceOrder');
            }
            
             function submitReassign() {  
                //Code Change Done Under JIRA Ticket SL-18563
            	var errorMsg = "";
                jQuery("#cancelE1").html("");
                jQuery("#cancelE1").css("display","none");
                var contactSelect = jQuery("#contactSelect").val();
                var reassignReason = jQuery("#reassignReason").val();
                var reassignReasonTrim=trim(reassignReason);
                jQuery("#reassignReason").val(reassignReasonTrim);
                               
                if(contactSelect==null){
                	errorMsg = "Please select a resource to reassign the service order.<br/>";
                }else if(reassignReasonTrim ==""){
                    errorMsg = errorMsg + "Please enter a reason for reassigning the service order.<br/>";
                }else{
                    SubmitReassignForm();
                }
                if(errorMsg!=""){
                	jQuery("#cancelE1").html(errorMsg);
                    jQuery("#cancelE1").css("display","block");
                }                              
                return errorMsg;
            }
    
            
            function fnCompleteForpayment(){
           
	            var objFinalLaborPrice = document.getElementById('finalLaborPrice');
	            var commentsCompletion = jQuery("#resComments").val();
	        	var resolutionComments=jQuery.trim(commentsCompletion);
	        	jQuery("#resComments").val(resolutionComments);
	      
		        if(objFinalLaborPrice.value=='')
		        {
		       document.getElementById('finalLaborPrice').value="0.0";
		        }
				
				
				if (document.getElementById("bycc") != null && window.getComputedStyle(document.getElementById("bycc")).display !== "none") 
				{ 
				//	alert("fnCompleteForpayment**********inside if calling check card valid");
					if(!checkCardValid())
					{
					//	alert("fnCompleteForpayment**********inside if not card valid");
						callCompleteSoAction();
					}
				}
				else
				{
					//alert("fnCompleteForpayment**********inside if not card valid");
					callCompleteSoAction();
				}
				

             }
			 
			 function callCompleteSoAction()
			 {
				//alert("err id:" + document.getElementById("addonServicesDTO.sowErrFieldId").value);
				//alert("err msg:" + document.getElementById("addonServicesDTO.sowErrMsg").value);
				  document.getElementById("frmCompleteForPayment").method = "post";                              
	              document.getElementById("frmCompleteForPayment").action = "${contextPath}/soDetailsCompleteForPayment_completeSo.action";
	              document.getElementById("frmCompleteForPayment").submit();
             }
            
            
            function showRescheduleRangeDate() {
           			document.getElementById('div_rescheduleDateRanged').style.display="block";
                   document.getElementById('rescheduleRangeMsg').style.display="block";
                  document.getElementById('rescheduleRangeFixedMsg').style.display="none";
            }
            
            function showRescheduleFixedDate() {
           
                  document.getElementById('div_rescheduleDateRanged').style.display="none";
                   document.getElementById('rescheduleRangeMsg').style.display="none";
                   document.getElementById('rescheduleRangeFixedMsg').style.display="block";
            }
            
      function fnCalcSpendLimit(){
			soDetailsCalcSpendLimitJq();
	  }
	function limitText(limitField,limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	}
	}
	
	function findMaxPrice(obj)
      {     
      obj.value = fmtMoney(obj.value);
      fnCalcSpendLimit();
      }
	   function fmtMoney(mnt) 
      {
          mnt -= 0;
          mnt = (Math.round(mnt*100))/100;
          var x = (mnt == Math.floor(mnt)) ? mnt + '.00' 
                    : ( (mnt*10 == Math.floor(mnt*10)) ? 
                             mnt + '0' : mnt);
                             
          if( x > 0 )
            return x;
               return "0.00";
      } 
	function findMaxLabor(obj)
      {     
      obj.value = fmtMoney(obj.value);
      fnCalcMaxLabor();
      } 
      
      function findMaximumLabor(obj)
      {
      obj.value = fmtMoney(obj.value);
      fnCalcTotalAmt();
      }
	function fnCalcMaxLabor(){
	var count= document.getElementById("taskCount").value;
	var taskPrice =0;
	var maxLabor = 0;
	var errorCount = 0;
	var valid = true;
	document.getElementById('taskErrorCount').value=0;
	for( var i = 0; i < count; i++ ){
	var taskType = document.getElementById('taskList[' + i + '].taskType').value;
	var sequenceNumber = document.getElementById('taskList[' + i + '].sequenceNumber').value;
					var taskPrice =document.getElementById('taskList[' + i + '].finalPrice').value;
					if(taskType!=1){
							maxLabor = maxLabor + taskPrice *1;
							}
					if(taskPrice < 0){
                			document.getElementById('increaseSPendLimitResponseMessage').innerHTML="Enter a valid amount for task";
                			document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
                			document.getElementById('increaseSPendLimitResponseMessage').style.visibility="visible";
                  			valid = false;
                  			errorCount++;
                  			if(document.getElementById('taskErrorCount')){
                  			document.getElementById('taskErrorCount').value=errorCount;
					}
                  			if(existJq('#maxLabor')&&existJq('#totalAmt')){
                  			document.getElementById('maxLabor').style.display="none";
                  			document.getElementById('totalAmt').style.display="none";
                  		}
               		}
               		if(errorCount==0) {
               				document.getElementById('increaseSPendLimitResponseMessage').style.display= "none";
                  			valid = true;
                  			if(existJq('#maxLabor')&&existJq('#totalAmt')){
                  			document.getElementById('maxLabor').style.display="block";
                  			document.getElementById('totalAmt').style.display="block";
                  			}
               			}
					}
				
					if(isNaN(maxLabor)){
					document.getElementById('increaseSPendLimitResponseMessage').innerHTML = "Enter increase in Maximum Price for Labor amount in decimal form.";
					document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
					document.getElementById('increaseSPendLimitResponseMessage').style.visibility="visible";
					}
				if(existJq('#maxLabor')){
				    document.getElementById('maxLabor').innerHTML = addOnObject.fmtMoney(maxLabor);
			}
			var permitPrice = parseFloat(document.getElementById('permitPrice').value);
			var totalSpendLimit = permitPrice + maxLabor;
			document.getElementById('totalSpendLimit').value = parseFloat(totalSpendLimit);	
			if(valid){
			fnCalcTotalAmt();
	}
	}
	
	function fnCalcTotalAmt(){
		var soTaskMaxLabor = parseFloat(document.getElementById('soTaskMaxLabor').value);
		var currentLimitLabor = parseFloat(document.getElementById('currentLimitLabor').value);
		var maxLabor = parseFloat(document.getElementById('maxLabor').innerHTML);
		var permitPrice = parseFloat(document.getElementById('permitPrice').value);
		var maxParts = parseFloat(document.getElementById('increaseLimitParts').value);
		var valid=true;
		var partsPrice=document.getElementById('increaseLimitParts').value;
		var partsPriceLength=partsPrice.length;
		
		if(maxParts < 0){
                	document.getElementById('increaseSPendLimitResponseMessage').innerHTML="Enter a valid amount for Materials";
                	document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
                	document.getElementById('increaseSPendLimitResponseMessage').style.visibility="visible";
                  	valid = false;
                  	if(existJq('#maxLabor')&&existJq('#totalAmt')){
                  	document.getElementById('maxLabor').style.display="none";
                 	document.getElementById('totalAmt').style.display="none";
                  	}		
               	}
               	else if (document.getElementById('taskErrorCount').value==0 && valid == true){
               					document.getElementById('increaseSPendLimitResponseMessage').style.display= "none";
                  				valid = true;
                  				if(existJq('#maxLabor')&&existJq('#totalAmt')){
                  							document.getElementById('maxLabor').style.display="block";
                  							document.getElementById('totalAmt').style.display="block";
                  							}
               	}
               	else if (document.getElementById('taskErrorCount').value>0)
               	{
               	document.getElementById('increaseSPendLimitResponseMessage').innerHTML="Enter a valid amount for task";
                document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
                document.getElementById('increaseSPendLimitResponseMessage').style.visibility="visible";
                valid=false;
                if(existJq('#maxLabor')&&existJq('#totalAmt')){
                  							document.getElementById('maxLabor').style.display="none";
                  							document.getElementById('totalAmt').style.display="none";
                  							}	
               	}
               	else{
               	valid=false;
               	}
        if(valid){
		if(isNaN(maxLabor)){
                var totalAmt = currentLimitLabor + maxParts ;
                 if(existJq('#maxLabor')){
                    document.getElementById('maxLabor').innerHTML = formatCurrency(soTaskMaxLabor).substring(1);
                   }
                }
                else{
                var totalAmt = maxLabor + maxParts + permitPrice;
                }
        if(isNaN(totalAmt)){
                 document.getElementById('increaseSPendLimitResponseMessage').innerHTML = "Enter increase in Maximum Price for Parts amount in decimal form.";
                 document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
                 document.getElementById('increaseSPendLimitResponseMessage').style.visibility="visible";
                   }
		document.getElementById('totalSpendLimitParts').value = parseFloat(maxParts);
		if(existJq('#totalAmt')){
			document.getElementById('totalAmt').innerHTML = formatCurrency(totalAmt).substring(1);
		}

		return "true";
	}
	}
	function validate(){
				if(!requestSubmitted){
	            var success = true;
	            var currentLimitPartsAmt = parseFloat($('#currentLimitParts').val());
                var currentLimitLaborAmt = parseFloat($('#currentLimitLabor').val());
              	var maxLabor = parseFloat(document.getElementById('totalSpendLimit').value);
				var maxParts = parseFloat(document.getElementById('totalSpendLimitParts').value);
				
              	  if(maxLabor < 0 || maxParts <0){
                  	success = false;
                  }
              	if(isNaN(maxLabor)){
              	var maxLabor=parseFloat($('#currentLimitLabor').val());
              	}
              	
              	currentPrice = currentLimitPartsAmt+currentLimitLaborAmt;
              	newPrice = maxLabor+maxParts;
              	var reasonCode = "";
              	var reasonCodeId = "";
            	if($('#reason_template').val()!= null){
            		reasonCodeId = document.getElementById('reason_template').value;
            		var selected_index = document.getElementById('reason_template').selectedIndex;
     			 	reasonCode = document.getElementById('reason_template').options[selected_index].text;
     			  	}               
				var reasonComment = document.getElementById('comment_template').value;
              	if(isNaN(newPrice) || (newPrice <= currentPrice)){
                  	document.getElementById('increaseSPendLimitResponseMessage').innerHTML ="New maximum price should be greater than current maximum price";
                  	document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
                  	document.getElementById('increaseSPendLimitResponseMessage').style.visibility="visible";
                  	success = false;
                 }
                 if(newPrice > 9999999.99){
                  	document.getElementById('increaseSPendLimitResponseMessage').innerHTML ="The price entered is invalid";
                  	document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
                  	document.getElementById('increaseSPendLimitResponseMessage').style.visibility="visible";
                  	success = false;
                 }
                 if(reasonCodeId == "-1"){
					document.getElementById('increaseSPendLimitResponseMessage').innerHTML = "Select a Reason for Spend Limit Increase";
                   	document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";  
                   	document.getElementById('increaseSPendLimitResponseMessage').style.visibility="visible";
                   	success = false;
                 }
                 if(reasonCodeId == "-2" && (reasonComment == null || reasonComment == "")){
                 	document.getElementById('increaseSPendLimitResponseMessage').innerHTML ="Enter Notes for Spend Limit Increase";
                   	document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
                   	document.getElementById('increaseSPendLimitResponseMessage').style.visibility="visible";
                   	success = false;
                 }
                 if(reasonCode == "" && (reasonComment == null || reasonComment == "")){
                 	document.getElementById('increaseSPendLimitResponseMessage').innerHTML ="Provide a Reason for Spend Limit Increase";
                   	document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
                   	document.getElementById('increaseSPendLimitResponseMessage').style.visibility="visible";
                   	success = false;
                 }

                 var maxSpendLimitPerSO  = ${SecurityContext.maxSpendLimitPerSO};
                 if(success){
                	 requestSubmitted = true;
                 	success = newco.jsutils.isTransactionPriceAboveMaxSpendLimit(maxSpendLimitPerSO,currentPrice,newPrice); 
                 }
           
	if(success)
	{
		fnSubmitIncreaseSpendLimit();
	}
	}
	}
      
	  function fnSubmitIncreaseSpendLimit(){
		    var reasonCode = "";
		    var reasonCodeId = "";
        	if($('#reason_template').val()!= null){
        		 reasonCodeId = document.getElementById('reason_template').value;
        		 var selected_index = document.getElementById('reason_template').selectedIndex;
			 	 reasonCode = document.getElementById('reason_template').options[selected_index].text;
			  	}                    		 
		  	var reasonComment = document.getElementById('comment_template').value;
		  	reasonComment = jQuery.trim(reasonComment);
			
			//SL-21448: Code change starts
		  	//To fix Buyer from seeing notes in service order details that do not apply when increasing the max price on service order.
		  	
		  	if(reasonCode != ""){
          	document.getElementById('increasedSpendLimitReason_detail').value = reasonCode;
			}else{
			document.getElementById('increasedSpendLimitReason_detail').value = reasonComment;
			}
          	//document.getElementById('increasedSpendLimitReason_detail').value = reasonCode;
          	
          	//SL-21448: Code change ends.
			
          	document.getElementById('increasedSpendLimitReasonId_detail').value = reasonCodeId;
            document.getElementById('increasedSpendLimitNotes_detail').value = reasonComment;
            document.getElementById('frmIncSpendLimit').action ="${contextPath}/incSpendLimit.action" ;
      		document.getElementById('frmIncSpendLimit').method="POST"; 
            document.getElementById('frmIncSpendLimit').submit();
                  
                  }

		function fnEnable(){
	    	var reasonCode = document.getElementById('reason_template').value;
	    	if(reasonCode == "-2"){
	    		document.getElementById('comment_template').disabled = false;
	    		document.getElementById('comment_template').value = "";
	    		document.getElementById('comment_template').style.background = '#FFFFFF';


	    	}else{
	    		document.getElementById('comment_template').disabled = true;
	    		document.getElementById('comment_template').value = "";
	    		document.getElementById('comment_template').style.background = '#E3E3E3';

				}
		}
	   function fnSubmitVoidSOInDetails(){           
	  		var soId = '${SERVICE_ORDER_ID}';
            fnBeforeCancel();
			jQuery('#cancellationDiv').load("soDetailsController_loadForCancelServiceOrder.action?soId="+soId,function() {
			jQuery("#cancelHeading").html("Void Service Order");
			jQuery("#cancelOrder").val("Void Service Order");
			jQuery("#action").val("void");
			var height = '560px';
			
			jQuery('#cancelBottomDiv').css({'background-color' : '#DEDDDD', 'height' : '45px'});
	 	 		jQuery("#cancellationDiv").modal({
		            onOpen: modalOpenAddCustomer,
		            onClose: modalOnClose,
		            persist: true,
		            containerCss: ({ width: "650px", height: height, marginLeft: "-300px" })
	            });
            	window.scrollTo(1,1);
			});	 
            	
			    	
	} 
	
	
	  function fnSubmitSOMDeleteDraft(){
	  		var soId = '${SERVICE_ORDER_ID}';
			fnBeforeCancel();
			jQuery('#cancellationDiv').load("soDetailsController_loadForCancelServiceOrder.action?soId="+soId,function() {
			jQuery("#cancelHeading").html("Delete Service Order");
			jQuery("#cancelOrder").val("Delete Service Order");
			jQuery("#action").val("delete");
			var height = '560px';
			
			jQuery('#cancelBottomDiv').css({'background-color' : '#DEDDDD', 'height' : '45px'});
	 	 		jQuery("#cancellationDiv").modal({
		            onOpen: modalOpenAddCustomer,
		            onClose: modalOnClose,
		            persist: true,
		            containerCss: ({ width: "650px", height: height, marginLeft: "-300px" })
	            });
            	window.scrollTo(1,1);
			});	 
          }
          
	function fnBeforeCancel(){
         	jQuery("#manageScopeDialog").html("");
			jQuery("#manageScopeDiv").html("");
			jQuery("#cancelE1").css("display","none");
			jQuery("#cancelE1").html("");
         }
	
	function selectEditPanel(visitId){   	
	    	     
	     if(visitId != null)
	     {	   
	        if(Addflag){
	     		Addflag = false;  
	     		UpdateAddEditPanel(visitIdStr,arrivalDateStr,soIdStr,departureDateStr);  
	     	}	
 		 	document.getElementById("AddEditPanel").style.display='block';
 		 	document.getElementById("saveupdate").style.display='block';
 		 	document.getElementById("saveinsert").style.display='none'; 		 
 		 }	 
	}
	
	
		
	
	function depDateBlankCheck(){
		if ((document.getElementById("txtDepartureDate").value=="")||(document.getElementById("txtDepartureDate").value==null)){
		 	 document.getElementById('departureDate').value= "";	
		}
		else
		{	 	
			document.getElementById('departureDate').value=document.getElementById('txtDepartureDate').value;		
		}	
	}
	
	function arrivalDateBlankCheck(){
		if ((document.getElementById("txtArrivalDate").value=="")||(document.getElementById("txtArrivalDate").value==null)){	
	 		 document.getElementById('arrivalDate').value= "";	 		 
		}
		else
		{	 	
			 document.getElementById('arrivalDate').value=document.getElementById('txtArrivalDate').value;		
		}	
	}
		
		
    function  UpdateAddEditPanel(visitId,arrivalDateCal,soId,departureDateCal)
	   {  
	   		  arrivalDateStr = arrivalDateCal;
	   	      departureDateStr = departureDateCal;
	   	      visitIdStr = visitId;
	   	      soIdStr = soId;
	   	  
              var arrivalDate1= new Date(arrivalDateCal); 
              var departureDate1=new Date(departureDateCal);   
            
              
		  if(!Addflag)
		  {  
		     clearAddEditPanel(); 
		     document.getElementById("visitId").value = visitId;		     
		     document.getElementById("soId").value = soId;		   
		    
		  if(!isNaN(arrivalDate1.getMonth()))  
		    {  
		     
		    

var arrival_date = arrivalDate1.getDate();
arrival_date = arrival_date + "";

if (arrival_date.length == 1)
   {
   arrival_date = "0" + arrival_date;
   }
var arrival_month = arrivalDate1.getMonth();
arrival_month = arrival_month + "";

if (arrival_month.length == 1)
   {
   arrival_month = "0" + arrival_month;
   }
var arrival_year = arrivalDate1.getFullYear();
document.getElementById("txtArrivalDate").value =arrival_month + "/" + arrival_date + "/" + arrival_year;
document.getElementById("arrivalDate").value =arrival_month + "/" + arrival_date + "/" + arrival_year;


		    
		 
		    
		     if(arrivalDate1.getHours() >=12 )
		     {		        
		        if(arrivalDate1.getHours() ==12)
		          {
		          document.getElementById("arrivalDepartureTimeHourString").value = arrivalDate1.getHours();
		           }
		          else
		          { 
		            var hours=arrivalDate1.getHours()-12;
		            document.getElementById("arrivalDepartureTimeHourString").value = hours;
		           } 
		            document.getElementById("arrivalDepartureTimeMinutesString").value = arrivalDate1.getMinutes(); 
		            document.getElementById("arrivalDepartureTimeAmPm").value = "PM";
		         
		     }
		     else
		     {
		    	document.getElementById("arrivalDepartureTimeHourString").value = arrivalDate1.getHours();
		     	document.getElementById("arrivalDepartureTimeMinutesString").value = arrivalDate1.getMinutes(); 
		     	document.getElementById("arrivalDepartureTimeAmPm").value = "AM";
		     }
		    } 
		    
		      if(!isNaN(departureDate1.getMonth()))  
		   	  {
		    
		       	    

var departure_date = departureDate1.getDate();
departure_date = departure_date + "";

if (departure_date.length == 1)
   {
   departure_date = "0" + departure_date;
   }
var departure_month = departureDate1.getMonth();
departure_month = departure_month + "";

if (departure_month.length == 1)
   {
   departure_month = "0" + departure_month;
   }
var departure_year = departureDate1.getFullYear();
document.getElementById("txtDepartureDate").value = departure_month + "/" + departure_date + "/" + departure_year;
document.getElementById("departureDate").value =departure_month + "/" + departure_date + "/" + departure_year;
		    
		     if(departureDate1.getHours() >= 12 )
		     {		     
		       if(departureDate1.getHours() ==12)
		          {
		          document.getElementById("departureTimeHourString").value = departureDate1.getHours();
		           }
		         else
		         {
		         var hours=departureDate1.getHours()-12;
		        document.getElementById("departureTimeHourString").value = hours;
		        document.getElementById("departureTimeMinutesString").value = departureDate1.getMinutes(); 
		        document.getElementById("departureTimeAmPm").value = "PM";
		        }		     
		     }
		     else
		     {
		     	document.getElementById("departureTimeHourString").value = departureDate1.getHours();
		    	 document.getElementById("departureTimeMinutesString").value = departureDate1.getMinutes(); 
		    	 document.getElementById("departureTimeAmPm").value = "AM";
		     }		     
		    } 		 
		}
	  }
	  
		function fnCalculate()
		{
		var totalElms = jQuery('.addOnClass').length;
            for( i = 0; i < totalElms; i++ )
            {
                  var objSku = document.getElementById('addonServicesDTO.addonServicesList[' + i + '].sku');
                  var skuVal = objSku.value;
                  if(skuVal=="99888"){
	  
                  	 var objPermitType = document.getElementById('addonServicesDTO.addonServicesList[' + i + '].permitType');
                  	 if(objPermitType){
                  	 var selected_index = objPermitType.selectedIndex;
                  	 if(objPermitType.value!="-1" ){
					 var text = objPermitType.options[selected_index].text;
                  	 var desc = text;
                  	 document.getElementById('addonServicesDTO.addonServicesList[' + i + '].description').value=desc;
                  	  }
			                   	 
                  	}
                  }
            }		
		}
			  
	  	function pop_w9modal()
		{
		
			//alert(jQuery('#fillWithW9'));
			jQuery('#w9modal > #fillWithW9').load("w9registrationAction_isW9exist.action", function() {
				//alert('inside2' + jQuery('#w9modal > #fillWithW9'));
				
				var finalTotalLabel=document.getElementById("finalTotalLabel").innerHTML;
				while(finalTotalLabel.charAt(0) == '$') 
				{
				finalTotalLabel=finalTotalLabel.substr(1);
				}
				
				var completeSOAmount=document.getElementById('completeSOAmount').value ;
				var providerThreshold=document.getElementById('providerThreshold').value ;
				
				if(completeSOAmount=='' || completeSOAmount==null)
				{
				completeSOAmount=0;
				}
				if(finalTotalLabel=='' || finalTotalLabel==null)
				{
				finalTotalLabel=0;
				}
				totalAmount=1.00*finalTotalLabel+1.00*completeSOAmount;
 
			totalAmount=formatMoney(totalAmount);
				
				
			
			var w9isDobNotAvailable= document.getElementById('w9isDobNotAvailable').value ;
			if(w9isDobNotAvailable=='' || w9isDobNotAvailable==null)
			{
			w9isDobNotAvailable=false;
			}
			
			var a=document.getElementById('w9isExist').value;
			var b=document.getElementById('w9isExistWithSSNInd').value;
			
				if(document.getElementById('w9isExist').value == "false" || document.getElementById('w9isExistWithSSNInd').value == "false"
				
				||(providerThreshold!='' && totalAmount>providerThreshold && w9isDobNotAvailable == "true"))
				{
				jQuery("#w9modal").modal({
                		onOpen: modalOpenAddCustomer,
                		onClose: modalOnClose,
                		persist: false,
                		containerCss: ({ width: "800px", height: "650px", marginLeft: "0px",marginBottom: "200",overflow:"auto",top: "16px"})
            		});
            		window.scrollTo(1,1);
           			jQuery("#customerResponse").fadeOut(5000);
           			
				
				}
				
				else if (document.getElementById('w9isExist').value == "true" && document.getElementById('w9isExistWithSSNInd').value == "true")
				{
					 // hide submit
			    	document.getElementById('submitDiv').style.display = 'none';
			    	// show submitting message
			    	document.getElementById('disabledSubmitDiv').style.display = 'block';
			    	if(document.getElementById('addonServicesDTO.addonServicesList[0].sku')!=null){
			    	fnCalculate();
			    	}
					fnCompleteForpayment();
				}
					
			});
		}
		
		function setPermitInd(){
		//new
		var isCheck = false;
		isCheck = document.getElementById('taskLevelPricing').value;
		if(isCheck){
					if(jQuery('#inner_document_grid').contents().find('#autocloseWarning').css("display")== "none"){
							if(document.getElementById('permitWarningStatusInd')){
							document.getElementById('permitWarningStatusInd').value='0';
							}
					}
					else{
							document.getElementById('permitWarningStatusInd').value='1';
					}
		}
	
		}
	
	function rescheduleSubmit(){
				var soId = '${SERVICE_ORDER_ID}';
				document.body.style.cursor='wait';
				var startDate=document.getElementById("newStartDate").value;
	 			var endDate=document.getElementById("newEndDate").value;
                var endTime=document.getElementById("newEndTime").value;
                //if(${roleId}==3){  
                //	var reasonCode=-1;
                //}
                //else{
                var reasonCode=document.getElementById("rescheduleReasonCode").value;
                //}
                var endTime= endTime.replace(" ","%20");
                var startTime=document.getElementById("newStartTime").value;
                var startTime= startTime.replace(" ","%20");
                var val="";
				if( document.getElementById("rangeOfDates0").checked == true ){
				val = document.getElementById("rangeOfDates0").value;
				
				}
				if( document.getElementById("rangeOfDates1").checked == true ){
				val = document.getElementById("rangeOfDates1").value;
				
				}
				var rangeDates=val;
 				var comments=document.getElementById("rescheduleComments").value;
 				
 				var validateRequestReschedUrl = '<s:url value="/requestReschedule_validateRequestReschedule.action" includeParams="none" />';

 				// Need to change & back to ? when HDIV is disabled.
 				validateRequestReschedUrl += "?newStartDate=" + startDate + "&newEndDate=" + endDate + "&newEndTime=" + endTime
					+ "&newStartTime=" + startTime + "&rangeOfDates=" + rangeDates + "&rescheduleComments=" + escape(comments)
					+ "&reasonCode=" + reasonCode + "&soId=" + soId;
 				
 				$("#msgError").load(validateRequestReschedUrl, function() {
                	if(document.getElementById("errorText") != null && document.getElementById("errorText").value == "") {
                		//To avoid multiple clicks to submit button,
                		//hiding the submit button after validation.
                		//$("#rescheduleSubmitBtn").hide();
                		var isNotSubmitted=checkBeforeSubmit();
                		if(isNotSubmitted){
                		    var rescheduleAction = '<s:url value="/requestReschedule.action" includeParams="none"/>';
                		    rescheduleAction += "?soId=" + soId;
                		    document.getElementById("rescheduleForm").action = rescheduleAction;
        	                document.getElementById("rescheduleForm").submit();
                		}
	                
                }else{
                	document.body.style.cursor='default';
                }
				});
				return false;
	
	}
	
	
	function selectProvider(acceptedResourceId){
		var soId = '${SERVICE_ORDER_ID}';
		var groupId = '${groupOrderId}';
		var isCarSo = document.getElementById('isCar').value;
		document.getElementById('selectedResourceId').value=acceptedResourceId;
		jQuery('#orderExpressMenu').load("loadQuickLinksAjax.action?acceptedResourceId="+acceptedResourceId+"&soId="+soId+"&groupId="+groupId);
		$('#loadTimerDiv').hide();
			document.getElementById('showTermsAndConditions').style.display='';
			if(isCarSo=='false'){
				document.getElementById('captcha').style.display = "block";
				$('#acceptButton').hide();
				jQuery('#loadTimerDiv').load("loadTimerAjax.action?acceptedResourceId="+acceptedResourceId+"&assignee=typeProvider&soId="+soId+"&groupOrderId="+groupId, function(){	
				    loadTimer();	
					$('#loadTimerDiv').show(); 
				});	
			}else{
				$('#acceptButtonForCarOrders').show();
			}
	}
	
	function assignProvider(isCarSo){
	    var soId = '${SERVICE_ORDER_ID}';
	    var groupId = '${groupOrderId}';
		$('#showTermsAndConditions').hide();
		$('#captcha').hide();
		$('#loadTimerDiv').hide();
		$('#acceptButtonForCarOrders').hide();
		$('#acceptButton').hide();
		var provider = $('input:radio[name=routedProvider]:checked').val();
		if($("#soUsingSku").is(':checked') ){
		//if(undefined != provider){
			var acceptedResourceId =$('#selectedResourceId').val();
			$('#showTermsAndConditions').hide();
			if(isCarSo=='false'){
				document.getElementById('captcha').style.display = "block";
				$('#acceptButton').hide();
				jQuery('#loadTimerDiv').load("loadTimerAjax.action?acceptedResourceId="+acceptedResourceId+"&assignee=typeProvider"+"&soId="+soId+"&groupOrderId="+groupId, function(){
				    loadTimer();	
					$('#loadTimerDiv').show(); 
				});	
			}else{
				$('#acceptButtonForCarOrders').show();
			}
		}
	}
	
	function selectFirm(isCarSo){
		var soId = '${SERVICE_ORDER_ID}';
		var groupId = '${groupOrderId}';
		$('#loadTimerDiv').hide();
		document.getElementById('showTermsAndConditions').style.display='';
		if(isCarSo=='false'){
				document.getElementById('captcha').style.display = "block";
				$('#acceptButton').hide();
				jQuery('#loadTimerDiv').load("loadTimerAjax.action?assignee=typeFirm&soId="+soId+"&groupOrderId="+groupId, function(){	
				    loadTimer();	
					$('#loadTimerDiv').show(); 
				});	
			}else{
				$('#acceptButtonForCarOrders').show();	
			}
	}
	
	//SL-3768
	function isEmptyOrSpaces(str){
    return str === null || str.match(/^ *$/) !== null;
}
	
	
	function checkCardValid(){
		//alert("calling check credit card valid");
		//alert("calling check credit card cctype is *******" + document.getElementById("addonServicesDTO.selectedCreditCardType").value);
		var ccType = document.getElementById("addonServicesDTO.selectedCreditCardType").value;
		//alert("calling check credit card ccNum is *******" + document.getElementById("addonServicesDTO.creditCardNumber").value);				
		var ccNum =  document.getElementById("addonServicesDTO.creditCardNumber").value;
				
		if(ccType === null || ccType == -1)
		{
			//alert("CardTypeId"+"CardTypeId_Description_Req_Validation");
			document.getElementById("addonServicesDTO.sowErrFieldId").value="Card Type";
			document.getElementById("addonServicesDTO.sowErrMsg").value="Card Type is required.";
			return false;
		}
		else if (ccNum===null || isEmptyOrSpaces(ccNum)){
			//alert("Credit_Card_Number"+"CardNumber_Description_Req_Validation");
			document.getElementById("addonServicesDTO.sowErrFieldId").value="Credit Card #";
			document.getElementById("addonServicesDTO.sowErrMsg").value="Card Number is required.";
			return false;
		}
		else if (!ccNum.includes("*")){
			
			if(!isCreditCardValid(ccNum,ccType))
			{
				//alert("Credit_Card_Number"+"Credit_Card_Number_Not_Valid");
				document.getElementById("addonServicesDTO.sowErrFieldId").value="Credit Card #";
				document.getElementById("addonServicesDTO.sowErrMsg").value="Credit card number is not valid.";
				return false;
			}
			else
			{
				return getAuthToken();
			}
		}
		else 
		{
			return false;
		}
	}
	
	function isCreditCardValid(ccNum,cardTypeId) {
	
		var validCC = false;
		//alert("hiccNum" + ccNum);
		//Check to see if the number is valid
		var sum = 0;
		var alternate = false;
		for (var i = ccNum.length - 1; i >= 0; i--) {
		    //alert("hii" + i);
			var n = parseInt(ccNum.substring(i, i + 1));
			//alert("hin" + n);
			if (alternate) {
				n *= 2;
				if (n > 9) {
					n = (n % 10) + 1;
				}
			}
			sum += n;
			alternate = !alternate;
		}

		validCC = (sum % 10 == 0);
		//alert("hivalidCC" + validCC);

			//if it's valid, check to see that the selected drop-down matches the number.
		if (validCC)
		{
		    //alert("hicardTypeId" + cardTypeId);
			// VISA -CreditCardConstants.CARD_ID_VISA = 6
			if (cardTypeId == 6)
			{
				if ((ccNum.length != 16) || parseInt(ccNum.substring(0, 1)) != 4) {
					validCC = false;
				}
			}

			// Discover card - CreditCardConstants.CARD_ID_DISCOVER = 6
			if (cardTypeId == 5)
			{ 
				if ((ccNum.length != 16) 
						|| (parseInt(ccNum.substring(0, 4)) != 6011
						&& parseInt(ccNum.substring(0, 2)) != 30)) {
					validCC = false;
				}
			}
			
			
			// MASTER CARD - CreditCardConstants.CARD_ID_MASTERCARD = 7
			else if (cardTypeId == 7)
			{
				if (ccNum.length != 16
						|| parseInt(ccNum.substring(0, 2)) < 51
						|| parseInt(ccNum.substring(0, 2)) > 55
						|| checkForSearsMasterCard(ccNum,cardTypeId)) {
					validCC = false;
				}
			}
			
			// SEARS MasterCard - CreditCardConstants.CARD_ID_SEARS_MASTERCARD =4
			else if (cardTypeId == 4 && (!checkForSearsMasterCard(ccNum,cardTypeId))) {
				validCC = false;
			}

			
			// AMEX
			// SLT-2591 and SLT-2592: Disable Amex
			/*if (cardTypeId == CreditCardConstants.CARD_ID_AMEX)
			{
				if (ccNum.length() != 15
						|| (Integer.parseInt(ccNum.substring(0, 2)) != 34 && Integer
								.parseInt(ccNum.substring(0, 2)) != 37)) {
					validCC = false;
				}
			}*/
			
			//
			else if ((cardTypeId == 0)) {

				if (ccNum.length != 16
						&& ccNum.length != 13) {
					validCC = false;
				}
				// Sears 16 digit Card
				else if ((ccNum.length == 16)
						&& (!checkForSearsCardOf16digits(ccNum,cardTypeId))) {
					validCC = false;
				}
				// Sears White Card
				else if ((ccNum.length == 13)
						&& (!checkForSearsWhiteCard(ccNum,cardTypeId))) {

					validCC = false;

				}

			}
			
			//Card not selected
			if (cardTypeId == -1)
			{
				validCC = false;
			}
		}

	//alert("hireturn" + validCC);	
		
		return validCC;
	}
	
	function checkForSearsMasterCard(cardNumber,cardTypeId)
	{
		if (cardNumber.length == 16 )//Sears Master card has card type id of 4
		{
			//"512106","512107","518537","512108","530226"
			var searsMasterCardInitial6digits = ["512106","512107","518537","512108","530226"];
			var cardInitialSixDigits = cardNumber.substring(0, 6);
			for (var i = 0; i < searsMasterCardInitial6digits.length;i++)
			{
				if (cardInitialSixDigits === searsMasterCardInitial6digits[i])
				{
					
					return true;
					
				}
			}
		}
		return false;
	}

	
	function checkForSearsWhiteCard(cardNumber,cardTypeId)
	{
		if (cardNumber != null && cardNumber.length== 13 && cardTypeId ==0)//Sears card has card type id of 0
		{
			//CreditCardConstants.SEARS_WHITE_CARD_BIN_RANGE - "00","01","02","03","04","05","06","07","08","09","11","20","21","34","36","40","44","48","50","54","57","60","64","70","75","80","81","82","95"
			var searsWhiteCardInitial2digits = ["00","01","02","03","04","05","06","07","08","09","11","20","21","34","36","40","44","48","50","54","57","60","64","70","75","80","81","82","95"];
			var cardInitialTwoDigits = cardNumber.substring(0, 2);
			for (var i = 0; i < searsWhiteCardInitial2digits.length;i++)
			{
				if (cardInitialTwoDigits === searsWhiteCardInitial2digits[i])
				{
					return true;
					
				}
			}
		}
		return false;
	}
	
	
	function checkForSearsCardOf16digits(cardNumber,cardTypeId)
	{
		if (cardNumber.length == 16 && cardTypeId == 0)//Sears card has card type id of 0
		{
			//CreditCardConstants.SEARS_CARD_16_BIN_RANGE - "504994","380000","381000","382000","383000"
			var searsCardInitial6digits = ["504994","380000","381000","382000","383000"];
			var cardInitialSixDigits = cardNumber.substring(0, 6);

			for (var i = 0; i < searsCardInitial6digits.length;i++)
			{
				if (cardInitialSixDigits === searsCardInitial6digits[i])
				{
					return true;
				}
			}

		}
		return false;
	}
	
	function getAuthToken(){
			
			//alert("tab prov comp docs : Calling doc ready method");
			var tokenUrl = document.getElementById('addonServicesDTO.CreditCardTokenUrl').value;
			var tokenAPICrndl = document.getElementById('addonServicesDTO.CreditCardTokenAPICrndl').value;
			//alert("tokenUrl***************" + tokenUrl);
			//alert("tokenAPICrndl***************" + tokenAPICrndl);
			console.log('calling token service',tokenUrl);
			
			var authToken;
			var tokenLife;
			
			if(jQuery("#authToken").val()==null || jQuery("#authToken").val()=="" || jQuery("#tokenLife").val()<jQuery.now())
			{
				jQuery.ajax({ 
			    	type:'GET',
			        url: tokenUrl,
		        	headers: {
				          Authorization: tokenAPICrndl
				    },
			        success: function( response ) {
						console.log('xml response: ', response);
			        	authToken = parseXMLElement('ns2:token', response.documentElement)
						tokenLife = parseXMLElement('ns2:tokenLife', response.documentElement)
						jQuery('#authToken').val(authToken);
						jQuery('#tokenLife').val(tokenLife);
					    console.log('successful token: ', authToken);
					    //setToken(authToken,parseInt(tokenLife)*1000 + jQuery.now());
					    return tokenizeCreditCard();
				    },
			        error: function( response ){
			          //alert("Credit Auth Authentication"+"Credit card authentication failed.");
					  document.getElementById("addonServicesDTO.sowErrFieldId").value="Credit Auth Authentication";
				      document.getElementById("addonServicesDTO.sowErrMsg").value="Credit card authentication failed.";
					  console.log('error payment: ', response);
					  return false;
			        },
					async: false
			    });
			}
			else
			{
				return false;
			}	
		}		   
	
		function setToken(authToken,tokenLife){
	    	 var setTokenURL="${contextPath}/soDetailsCompleteForPayment_setToken.action";
			//alert("setToken call action" + setTokenURL);
			//alert("addonServicesDTO.authToken" + authToken);
			//alert("addonServicesDTO.tokenLife" + tokenLife);
	    	jQuery.post({
		    	url: setTokenURL,
	        	data: {
				    "addonServicesDTO.authToken": authToken,
				    "addonServicesDTO.tokenLife": tokenLife
				  },
		        success: function( response ) {
					console.log('Xml Response is : ' , response);
					console.log('Token set in user session');
					return tokenizeCreditCard();
			    },
		        error: function( response ){
		          console.log('error payment: ', response);
		        }
		    });
	  	}
	
		function parseXMLElement(element, data) {
			var retVal = "";
			if (data.getElementsByTagName(element)[0]) {
					if (data.getElementsByTagName(element)[0].childNodes[0]) { 			
					retVal = data.getElementsByTagName(element)[0].childNodes[0].nodeValue;
					} else {
					retVal = null;
				}
				return retVal;
			} else {
				return null;
			}
		}
	
		
		function tokenizeCreditCard() {
			//alert("tokenizeCreditCard call action");
			var timestamp = jQuery('#transDate').val();
			 var tokenizeCreditCardUrl=document.getElementById('addonServicesDTO.CreditCardAuthTokenizeUrl').value;
			 var userName = document.getElementById("addonServicesDTO.userName").value;
		    var cardNumber = document.getElementById("addonServicesDTO.creditCardNumber").value;
		   //alert("setToken call action" + tokenizeCreditCardUrl);
		   	var responseMessage;
		   	var responseCode;
		   	var addlResponseData;
		   	var authToken = "Bearer " +jQuery('#authToken').val();
			var apiKey = document.getElementById("addonServicesDTO.CreditCardAuthTokenizeXapikey").value;
			var arr={ "acctNo" : cardNumber};
			var tokenizeCardNumber;
			var maskedCardNumber;
			//alert("before api call url tokenizeCreditCardUrl" + tokenizeCreditCardUrl);
			//alert("before api call param cardNumber" + cardNumber);
			//alert("before api call header Authorization" + authToken);
			//alert("before api call header userid" + userName);
			//alert("before api call header x-api-key" + apiKey);
			jQuery.ajax({

					type: 'POST',
   			        url: tokenizeCreditCardUrl,
   					data:JSON.stringify(arr),
   			       
   			     headers: {
			        	'Content-Type': 'application/json',
						'Accept':'application/json',
			        	'clientID':'SLIVE',
			        	'userid':userName,
			        	'Authorization':authToken,
						'x-api-key':apiKey
			        },
			        success: function( response ) {
			          	console.log('successful payment: ', response);
						
			          	var responseCode =response.ResponseCode;

			          	if(responseCode==00){
			          		tokenizeCardNumber = response.token;
			          		maskedCardNumber = response.mask
							
							jQuery('#correlationId').val(response.CorrelationId);
							jQuery('#maskedCardNumber').val(response.mask);
							jQuery('#tokenizeCardNumber').val(response.token);
							jQuery('#responseCode').val(responseCode);
							jQuery('#responseMessage').val(response.ResponseMessage);
							//alert("after api call header response.mask" + response.mask);
							//alert("after api call header response.token" + response.token);
							//alert("after api call header response" + response);
							document.getElementById("addonServicesDTO.responseXML").value=response;
							document.getElementById("addonServicesDTO.creditCardNumber").value=response.mask;
							document.getElementById("addonServicesDTO.sowErrFieldId").value="";
							document.getElementById("addonServicesDTO.sowErrMsg").value="";
							callCompleteSoAction();
							
							return true;
			          	}else{
			          		var errorMessage =response.messages;
					
			          		jQuery("#errorMessage").html(errorMessage);
			          		jQuery("#message").show();
							//alert("Credit Auth Authentication"+"Credit card authentication failed.");
							document.getElementById("addonServicesDTO.sowErrFieldId").value="Credit Auth Tokenization";
							document.getElementById("addonServicesDTO.sowErrMsg").value="Credit card tokenization failed.";
			          		return false;
			          	}
						
						
			        },
			        error: function( response ){
			          jQuery("#errorMessage").html("");
			          jQuery("#message").show();
			          console.log('error payment: ', response);
					  //alert("Credit Auth Authentication"+"Credit card authentication failed.");
					  document.getElementById("addonServicesDTO.sowErrFieldId").value="Credit Auth Tokenization";
					  document.getElementById("addonServicesDTO.sowErrMsg").value="Credit card tokenization failed.";
					  return false;
			        },
					async: false
			});

}

	</script>
		<script language="javascript" type="text/javascript">
           jQuery(document).ready(function($){
                  $("#promoContent").load("promoAction_displayPromotion.action?soId=${SERVICE_ORDER_ID}&groupId=${groupOrderId}&contentLocation=SODProvider" );
            });
    </script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/animatedcollapse.js">	</script>
	</head>
	<body class="tundra">
	<input type="hidden"  id="estimateId"
					value="${estimateId}" />
				<input type="hidden" id="isEstimationRequest"
					value="${isEstimationRequest}" />
					
					<input type="hidden" id="isProvider"
					value="${isProvider}" />
					
					<input type="hidden" id="showEstimateBtn"
					value="${showEstimateBtn}" />
					
					<input type="hidden" id="soid"
					value="${SERVICE_ORDER_ID}" />
					
					
		<input type="hidden" name="resourceID" id="resourceID"
			value="${SERVICE_ORDER_CRITERIA_KEY.vendBuyerResId}" />
		
		<div id="page_margins">
			<div id="page">

				<div id="header">
					<tiles:insertDefinition name="newco.base.topnav" />
					<tiles:insertDefinition name="newco.base.blue_nav" />
					<tiles:insertDefinition name="newco.base.dark_gray_nav" />

					<div id="pageHeader">

						<tiles:insertDefinition name="newco.base.headerTitle">
							<tiles:putAttribute name="headerTitleImage"
								value="${staticContextPath}/images/so_details/SODhdrSummary.gif"></tiles:putAttribute>
							<tiles:putAttribute name="headerTitleAlt"
								value="SODhdrSummary.gif"></tiles:putAttribute>
						</tiles:insertDefinition>
					</div>
					<div id="dateDashboard" align="right">
						<c:set var="dateString"
							value="<%=com.newco.marketplace.utils.DateUtils
										.getHeaderDate()%>"></c:set>
						<span id="date_dashboard">${dateString}</span>
					</div>
				</div>

				<div id="promoContent">
				</div>

				<%
					String resIdVar = request.getParameter("param.resId");
					String resIdNew = ESAPI.encoder().canonicalize(resIdVar);
					String resIdEnc = ESAPI.encoder().encodeForHTML(resIdNew);

					String groupIdVar = request.getParameter("param.groupId");
					String groupIdNew = ESAPI.encoder().canonicalize(groupIdVar);
					String groupIdEnc = ESAPI.encoder().encodeForHTML(groupIdNew);
				%>

				<c:set var="resId" value="<%=resIdEnc%>" />

				<form action="serviceOrderReject.action?soId=${SERVICE_ORDER_ID}&groupId=${GROUP_ID}" method="post"
					id="rejectForm">
					<input type="hidden" name="requestFrom" id="requestFrom"
						value="SOD" />
					<input type="hidden" name="reasonId" id="reasonId" />
					<input type="hidden" name="reasonText" id="reasonText" />
					<input type="hidden" name="resId" value="${resId}" />
					<input type="hidden" name="zpriceResId"
						value="<%=request.getAttribute("routedResourceId")%>" />
					<c:if test="${not empty groupIdEnc}">
						<c:set var="groupId" value="<%=groupIdEnc%>" />
						<input type="hidden" name="groupId" value="${groupId}" />
					</c:if>
				</form>

				<s:form action="soDetailsSummarySubStatusChange"
					id="summarySubStatusChange">
					<input type="hidden" name="subStatusIdChanged"
						id="subStatusIdChanged" />
					<input type="hidden" name="soId" id="soId" value="${SERVICE_ORDER_ID}" />
					<input type="hidden" name="statusId" id="statusId" value="${THE_SERVICE_ORDER_STATUS_CODE}" />
					<input type="hidden" name="resId" id="resId" value="${routedResourceId}" />
				</s:form>


				<s:form action="soDetailsSummaryBuyerReferenceChange"
					id="summaryBuyerReferenceChange">
					<input type="hidden" name="refVal" id="refVal" />
					<input type="hidden" name="refValOld" id="refValOld" />
					<input type="hidden" name="refType" id="refType" />
					<input type="hidden" name="soId" id="soId" value="${SERVICE_ORDER_ID}" />
					<input type="hidden" name="resId" id="resId" value="${routedResourceId}" />
				</s:form>

				<tiles:insertAttribute name="body" />


				<tiles:insertAttribute name="footer" />

			</div>
		</div>
		
		<!--  wildfly changes start -->
		
			<!-- start requestreschedule -->
			
		<div id="requestReschedule" title="Request Reschedule"
			style="display: none;max-width:850px; width:auto;height:499px;" class="modal">
			<form id="rescheduleForm" name="rescheduleForm" method="post">

				<div id="checkreqq" class="divContainerUp"
					style="visibility: hidden">
				</div>
			<div class="modalheader" style="background: #FAFAFA url(../ServiceLiveWebUtil/javascript/dojo/dijit/themes/tundra/images/titleBarBg.gif) repeat-x scroll left bottom;
    				cursor: move;outline-style: none;padding: 4px 8px 2px 4px;margin-left: -30px;">
				<c:choose>
					<c:when test="${so.rescheduleDates!= null}">
						<b>Edit Reschedule Request</b>
					</c:when>
					<c:otherwise>
						<b>Request A Reschedule</b>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="modalheaderoutline">
					<div class="serviceOrderAcceptFrame" style="width: 100%; margin-left: -18px;margin-top: 9px;">
						<div class="serviceOrderAcceptFrameBody" style="width: 100%;">
							<div id="msgError">
							</div>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="popup.requestReschedule.message" />
							<br />
							<br />
							<div style="background-color: #f7fc92;width: 664px;">
								<strong><fmt:message bundle="${serviceliveCopyBundle}"
										key="popup.service.current.appt" /> </strong>
								<br />
								<strong><fmt:message bundle="${serviceliveCopyBundle}"
										key="popup.service.dates" /> </strong>&nbsp;${so.appointmentDates}
								<br />
								<strong><fmt:message bundle="${serviceliveCopyBundle}"
										key="popup.service.request.date.window" /> </strong>&nbsp;${so.serviceWindow}
								<br />
							</div>
							
							<!-- JIRA Sl-19291: Display an alert if a reschedule is already pending. -->
							<c:if test="${so.rescheduleDates!= null}">
							<div class="darkRed reschdule_descr" style="padding-top: 10px">
							<b>This service order already has a pending reschedule request for
								${so.rescheduleDates}, at ${so.rescheduleServiceWindow}. 
								If you submit a new request, it will replace the existing reschedule request.</b>
							</div>
						</c:if>
							<br />
							<!-- Code has been changed under SL-18381 -->
							<table cellpadding="0px" cellspacing="0px">
								<tr>
									<c:if test="${roleId!=3}">
										<td width="100%">
											<strong>Reason</strong><font color="red">*</font>
											<br />
											<s:select cssStyle="width: 600px;" id="rescheduleReasonCode"
												headerKey="-1" headerValue="Select One" name="reasonCode"
												list="#attr.reasonCodeList" listKey="id" listValue="type"
												multiple="false" size="1" theme="simple" />
										</td>
									</c:if>
									<c:if test="${roleId==3}">
										<td width="100%">
											<strong>Reason</strong><font color="red">*</font>
											<br />
											<s:select cssStyle="width: 600px;" id="rescheduleReasonCode"
												headerKey="-1" headerValue="Select One" name="reasonCode"
												list="#attr.rescheduleReasonList" listKey="id" listValue="type"
												multiple="false" size="1" theme="simple" />
										</td>
									</c:if>
								</tr>
							</table>

							<table cellpadding="0px" cellspacing="0px">
								<tr>
									<td colspan="4">
										<strong><fmt:message
												bundle="${serviceliveCopyBundle}"
												key="popup.service.date.type" /> </strong>
										<br>
									</td>

									<td>
										<input type="radio" id="rangeOfDates1" name="rangeOfDates"
											value="1" onclick="javascript:showRescheduleRangeDate()"
											<c:if test="${RescheduleDTO.rangeOfDates != 0}">
										CHECKED
										</c:if> />
										<br>
									</td>
									<td>
										<fmt:message bundle="${serviceliveCopyBundle}"
											key="popup.service.date.range" />
										<br>
									</td>
									<td width="10px">
										<br>
									</td>
									<td>
										<input type="radio" id="rangeOfDates0" name="rangeOfDates"
											<c:if test="${RescheduleDTO.rangeOfDates == 0}">
										CHECKED
										</c:if>
											value="0" onclick="javascript:showRescheduleFixedDate()" />
										<br>
									</td>
									<td width="2px">
										<br>
									</td>
									<td>
										<fmt:message bundle="${serviceliveCopyBundle}"
											key="popup.service.date.fixed" />
										<br>
									</td>
								</tr>
							</table>
							<br />
							<br />

							<table width="800px">
								<tr>
									<td width="50%">
										<div id="rescheduleRangeMsg"
											style="display: block; width: 100px">
											<strong><fmt:message
													bundle="${serviceliveCopyBundle}"
													key="popup.service.request.date.window" /> </strong>
										</div>
										<div id="rescheduleRangeFixedMsg" style="display: none">
											<strong><fmt:message
													bundle="${serviceliveCopyBundle}"
													key="popup.service.request.date.fixed" /> </strong>
										</div>

										<table>
											<tr>
												<td>
													<table cellspacing="0px"
														style="background-color: #ededed; border: 4px solid #ededed; line-height: 18px">
														<tr>
															<td>
																<fmt:message bundle="${serviceliveCopyBundle}"
																	key="popup.service.date" />
																<font color="red">*</font>
																<br>
															</td>
														</tr>
														<tr>
															<td>
																<input type="hidden" id="newStartDate"
																	name="newStartDate"
																	value="${RescheduleDTO.newStartDate}" />
																<input type="text" class="shadowBox"
																	style="width: 90px; position: relative" id="" name=""
																	value="${RescheduleDTO.newStartDate}"
																	onkeyup="findPosDate(this,'checkreqq')"
																	onFocus="findPosDate(this,'checkreqq');showCalendarControl(this,'newStartDate');"
																	required="true"
																	onblur="hidMsg('checkreqq');assignDate(this,'newStartDate');"
																	lang="en-us" />
															</td>
														</tr>
														<tr>
															<td>
																<fmt:message bundle="${serviceliveCopyBundle}"
																	key="popup.service.time" />
																<br>
															</td>
														</tr>
														<tr>
															<td>
																<s:select cssStyle="width: 90px;" id="newStartTime"
																	name="newStartTime"
																	list="#application['time_intervals']" listKey="descr"
																	listValue="descr" multiple="false" size="1"
																	theme="simple" value="08:00 AM" />
																<br>
															</td>
														</tr>
													</table>
												</td>
												<td>
													<div id="div_rescheduleDateRanged">
														<table cellspacing="0px"
															style="background-color: #ededed; border: 4px solid #ededed; line-height: 18px">
															<tr>
																<td colspan="2">
																	&nbsp;
																</td>
															</tr>
															<tr>
																<td>
																	&nbsp;
																	<fmt:message bundle="${serviceliveCopyBundle}"
																		key="popup.to" />
																	<font color="red">*</font> &nbsp;&nbsp;
																</td>
																<td>
																	<input type="hidden" id="newEndDate" name="newEndDate"
																		value="${RescheduleDTO.newEndDate}" />
																	<input type="text" value="${RescheduleDTO.newEndDate}"
																		onkeyup="findPosDate(this,'checkreqq')"
																		onFocus="findPosDate(this,'checkreqq');showCalendarControl(this,'newEndDate');"
																		class="shadowBox"
																		style="width: 90px; position: relative"
																		onblur="hidMsg('checkreqq');assignDate(this,'newEndDate');"
																		id="" name="" constraints="{min: '${todaysDate}'}"
																		required="true" lang="en-us" />
																</td>
															</tr>
															<tr>
																<td colspan="2">
																	&nbsp;
																</td>
															</tr>
															<tr>
																<td>
																	&nbsp;
																	<fmt:message bundle="${serviceliveCopyBundle}"
																		key="popup.to" />
																	&nbsp;&nbsp;
																</td>
																<td>
																	<s:select cssStyle="width: 90px;" id="newEndTime"
																		name="newEndTime"
																		list="#application['time_intervals']" listKey="descr"
																		listValue="descr" multiple="false" size="1"
																		theme="simple" value="05:00 PM" />
																</td>
															</tr>
														</table>
													</div>
												</td>
											</tr>
										</table>
									</td>
									<td width="50%">
										<strong>Comments:</strong><font color="red">*</font>
										<br />
										<textarea id="rescheduleComments" name="rescheduleComments"
											style="font-family: sans-serif; font-weight: lighter; font-size: 12px;"
											cols="35">${RescheduleDTO.rescheduleComments}</textarea>
									</td>
								</tr>
							</table>

							<p>
								<img id="rescheduleSubmitBtn" src="${staticContextPath}/images/common/spacer.gif"
									width="132" height="18"
									style="background-image: url(${staticContextPath}/images/btn/requestReschedule.gif); float: left;"
									onclick="rescheduleSubmit()" class="btnBevel" />
								<a class="cancel simplemodal-close" style="cursor:default;">Cancel</a>
							</p>
						</div>
					</div>
				</div>
				<input type="hidden" name="startTime" id="startTime"
					value="${RescheduleDTO.newStartTime}" />
				<input type="hidden" name="endTime" id="endTime"
					value="${RescheduleDTO.newEndTime}" />
				<input type="hidden" name="dateRange" id="dateRange"
					value="${RescheduleDTO.rangeOfDates}" />
			</form>
		</div>
		
		<!-- end requestreschedule -->
		
		<div id="reassignServiceOrder" style="display: none"
			title="Reassign Service Order">
			<form method="post"
				action="/MarketFrontend/requestReassign!saveReassignSO.action"
				id="reassignForm">
              <input type="hidden" name="hiddenSoId" id="hiddenSoId" value="${SERVICE_ORDER_ID}" />
				<div class="modalheader">
					<b>Reassign Service Order</b>
				</div>
				<div id="cancelE1" class="errorBox"
					style="display: none; padding: 5px; width: 90%"></div>
				<div class="modalheaderoutline">
					<div class="serviceOrderReassignFrame"
						style="width: 500px; height: 300px" align="center">
						<div class="serviceOrderReassignFrameBody"
							style="width: 490px; height: 294px; border: 1px solid #ededed; vertical-align: middle;"
							align="center">
							<div style="margin-left: 8px;" align="left">
								<c:if test="${not empty contactList}">
									<br>
								Reassign to the selected service provider in your firm who also recieved this order:
								<br />
									<br />
									<s:select list="#attr.contactList" listKey="resourceId"
										listValue="displayName" size="4" theme="simple"
										cssStyle="width: 256px;" cssClass="grayText"
										id="contactSelect" name="contactSelect" />
									<br />
									<br />
								Reason:<span class="req">*</span>
								 <br />
									<br />
									<textarea style="width: 400px;" name="reassignReason" id="reassignReason" ></textarea>
									<br />
									<br />
									<br />
									<br />
									<div style="width: 300px; margin-top: -14px;">
										<div align="left">
											<p>
												<input id="submitReassignButton" type="image"
													src="${staticContextPath}/images/common/spacer.gif"
													align="middle" width="55" height="20"
													style="background-image: url(${staticContextPath}/images/btn/save.gif);"
													class="btn20Bevel" onclick="submitReassign();return false;" />
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<a href="#"><img
														src="${staticContextPath}/images/common/spacer.gif"
														align="middle" width="55" height="20"
														style="background-image: url(${staticContextPath}/images/btn/cancel.gif);"
														class="btnBevel simplemodal-close" /> </a>
											</p>
										</div>
									
							</div>
							</c:if>
							<c:if test="${empty contactList}">
								<br>
								There are no provider resources available within the firm to reassign the service order.
								 <br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<div align="left">
									<a href="#"><img
											src="${staticContextPath}/images/common/spacer.gif"
											align="middle" width="55" height="20"
											style="background-image: url(${staticContextPath}/images/btn/cancel.gif);"
											class="btnBevel simplemodal-close" /> </a>
								</div>
							</c:if>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div id="assignProviderDiv" style="display: none;">
		</div>

		<div style="width: 400px; height: 400px; display: none"
			id="manageScopeDialog">
		</div>
		<div id="releaseServiceOrder" title="Release Service Order"
			style="display: none">
		</div>
		<div id="cancelSoId" title="Cancel Service Order"
			style="display: none">
			<form id="frmCancelSO" name="frmCancelSO">
				<div align="center">
					<div class="modalheader">
						<b>Cancel Service Order</b>
					</div>					
					<div class="modalheaderoutline">
						<div class="serviceOrderAcceptFrame">
							<div class="serviceOrderAcceptHdr">
								<img
									src="${staticContextPath}/images/so_details/hdr_bar_cancellation.gif"
									border="0" align="left">
								<a href="#"><img
										src="${staticContextPath}/images/so_details/info_blue_bar.gif"
										border="0" align="right"> </a>
							</div>

							<div class="serviceOrderAcceptFrameBody">
								<!-- Display validation message -->
								<div class="errorBox clearfix" id="cancelSOErrorMessage"
									style="width: 490px; overflow-y: hidden; visibility: hidden;">
								</div>

								<c:if test="${statusCd==statusAccepted}">
									<div align="left">
										<b>Comments</b>
										<br />
										<textarea style="width: 500px;" id="cancelComment"
											name="cancelComment" required="true"></textarea>
									</div>
									<br />
								</c:if>
								<c:if test="${statusCd==statusActive}">
									<c:set var="currentSpendLimit"
										value="${THE_SERVICE_ORDER.totalSpendLimit}" scope="request" />
									<c:set var="currentSpendLimitLength"
										value="<%=String.valueOf(((ServiceOrderDTO) request
								.getAttribute("THE_SERVICE_ORDER"))
								.getTotalSpendLimit().toString().length())%>" />
									<c:if test="${currentSpendLimitLength == 0}">
										<c:set var="currentSpendLimit" value="0.0" />
										<c:set var="currentSpendLimitLength" value="3" />
									</c:if>
									<c:set var="currentSpendLimitFinal"
										value="${fn:substring(currentSpendLimit,0,currentSpendLimitLength)}" />
									<div class="inputArea" style="height: 150px; width: 500px;">
										<p>
											When a service order is in the Active state, you can no
											longer cancel the service order. You may, however, request
											the service provider to complete the service order for a
											lower amount.
										</p>
										<p>
											Specify an amount below that you would like the service
											provider to complete the service order and click the Send
											Cancellation Request button. A communication will go out to
											the provider with your request.
										</p>
										<p>
											Important: Sending the cancellation request will not
											guarantee the provider's agreement of the terms. It is a good
											idea to speak directly with the provider to explain your
											intent and get the provider's consent.
										</p>
									</div>
									<input type="hidden" name="currentSL" id="currentSL"
										value="${currentSpendLimitFinal}" />
									<p>
										<strong>Current Maximum Price:</strong> $
										<fmt:formatNumber value="${currentSpendLimitFinal}"
											type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
									</p>

									<p>
										<strong>Requested Cancellation amount</strong> (less than or
										equal to Maximum Price)
									</p>
									<p>
										$
										<input class="shadowBox grayText" style="width: 110px;"
											id="cancelAmt" name="cancelAmt" onfocus="clearTextbox(this)"
											value="45.00" />
									</p>
								</c:if>
								<p>
									<a href="#" onClick="fnCancellationSubmit()"> <img
											src="${staticContextPath}/images/common/spacer.gif"
											width="93" height="19"
											style="background-image: url(${staticContextPath}/images/btn/sendRequest.gif);"
											class="btnBevel" align="left" /> </a>

									<a href="#" onClick="javascript:fnClearCancelPopUpJq();"> <img
											src="${staticContextPath}/images/common/spacer.gif"
											width="55" height="22"
											style="background-image: url(${staticContextPath}/images/btn/cancel.gif);"
											class="btnBevel modalClose" align="right" /> </a>
								</p>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>

		<%--Begin Increase Maximum Price Modal JSP--%>
		<c:set var="currentSO" value="${THE_SERVICE_ORDER}" scope="request" />

		<div id="spendLimitId" style="display: none; z-index: 1">
			<form id="frmIncSpendLimit" name="frmIncSpendLimit">
				<input type="hidden" name="increasedSpendLimitReason_detail"
					id="increasedSpendLimitReason_detail" value="" />
				<input type="hidden" name="increasedSpendLimitReasonId_detail"
					id="increasedSpendLimitReasonId_detail" value="" />
				<input type="hidden" name="increasedSpendLimitNotes_detail"
					id="increasedSpendLimitNotes_detail" value="" />
				<div align="center">
					<c:if test="${currentSO.taskLevelPriceInd == false}">
						<div class="modalHomepage">
							<div style="float: left;">
								Increase Price
							</div>
							<a href="#" class="btnBevel simplemodal-close" style="color: white;"
								float:right>Close</a>
						</div>
						<div class="modalheaderoutline">
							<div class="rejectServiceOrderFrame"
								style="width: 239px; border: 0px; margin-left: -70px">

								<div class="rejectServiceOrderFrameBody"
									style="width: 300px; float: left;">

									<!-- Display validation message -->
									<div class="errorBox clearfix"
										id="increaseSPendLimitResponseMessage"
										style="width: 300px; overflow-y: hidden;">


									</div>


									Your current spend limit is shown below. Enter the new amount,
									then click "submit". You must provide a reason for the
									increase.
									<br />
									<br />
									<input type="hidden" name="taskLevelPriceInd"
										id="taskLevelPriceInd" value="${currentSO.taskLevelPriceInd}" />
									<input type="hidden" name="buyerId" id="buyerId"
										value="${currentSO.companyID}" />
									<input type="hidden" name="selectedSO" id="selectedSO"
										value="${currentSO.id}" />

									<b>&nbsp;&nbsp;Increase Current Maximum Price:</b>
									<table>
										<br />
										<br />
										<tr></tr>
										<tr>
										</tr>
										<table style="border-bottom: 1px solid #ccc;">
											<tr>
												<td
													style="float: right; padding-bottom: 15px; padding-left: 60px;">
													Maximum Labor:
												</td>
												<td width="10" style="padding-bottom: 15px;"></td>
												<td width="10" style="padding-bottom: 15px;">
													$
												</td>
												<td>
													<input type="hidden" id="currentLimitLabor"
														value="${currentSO.laborSpendLimit}"
														name="currentLimitLabor" />
													<input type="text" onFocus="findPos(this,'checkspend')"
														onkeyup="formatPriceLabourandParts(this,'checkspend')"
														onblur="findMaxPrice(this);" id="increaseLimit" size="9"
														value="<fmt:formatNumber value="${currentSO.laborSpendLimit / (1 + laborTaxPercentage)}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />"
														name="increaseLimit"
														class="textbox-60 alignRight shadowBox" align="right" />
												</td>
											</tr>
											<tr>
												<td style="float: right; padding-bottom: 15px;">
													Maximum Materials:
												</td>
												<td width="10" style="padding-bottom: 15px;"></td>
												<td width="10" style="padding-bottom: 15px;">
													$
												</td>
												<td>
													<input type="text" onFocus="findPos(this,'checkspend')"
														onkeyup="formatPriceLabourandParts(this,'checkspend')"
														onblur="findMaxPrice(this);" id="increaseLimitParts"
														size="9" name="increaseLimitParts"
														value="<fmt:formatNumber value="${currentSO.partsSpendLimit / (1 + partsTaxPercentage)}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />"
														class="textbox-60 alignRight shadowBox" align="right" />
												</td>
												<input type="hidden" id="currentLimitParts"
													name="currentLimitParts"
													value="${currentSO.partsSpendLimit}" />
											</tr>
											<c:if test="${ displayTax }">
												<tr>
													<td
														style="float: right; padding-bottom: 15px; padding-left: 60px;">
														Maximum Materials Tax (<fmt:formatNumber value="${partsTaxPercentage * 100}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>%):
													</td>
													<td width="10" style="padding-bottom: 15px;"></td>
													<td width="10" style="padding-bottom: 15px;">
														$
													</td>
													<td>
														<label id="maxPartsIncreaseTax" name="maxPartsIncreaseTax"><fmt:formatNumber value="${currentSO.partsSpendLimit * partsTaxPercentage / (1 + partsTaxPercentage)}"
																				type="NUMBER" minFractionDigits="2"
																				maxFractionDigits="2" /></label>
														
													</td>
												</tr>
											</c:if>
										</table>
										<table>
											<tr>
												<td style="padding-left: 20px">
													<b>New Combined Maximum:</b>
												</td>
												<td width="10" style="padding-bottom: 15px;"></td>
												<td width="10" style="padding-bottom: 15px;">
													$
												</td>
												<td align="left">
													<div id="totalAmt">
														<fmt:formatNumber
															value="${currentSO.partsSpendLimit+currentSO.laborSpendLimit}"
															type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
													</div>
												</td>
												<input type="hidden" name="totalSpendLimit"
													id="totalSpendLimit" />
												<input type="hidden" name="totalSpendLimitParts"
													id="totalSpendLimitParts" />
											</tr>
										</table>
									</table>
									<br />
									<b>Reason</b>
									<font color="red">*</font>
									<br />
									<c:choose>
									<c:when test="${fn:length(spendLimitReasonCodes) > 0}">
										<select name="reason_template" id="reason_template"
											width="300px" style="width: 300px; border: 2px solid #CCCCCC"
											onchange="fnEnable();">
											<option selected="selected" value="-1">
												- Select a reason -
											</option>
											<c:forEach var="reasonCode" items="${spendLimitReasonCodes}">
												<option value="${reasonCode.reasonCodeId}">
													${reasonCode.reasonCode}
												</option>
											</c:forEach>
											<option id="Other" value="-2">
												Other
											</option>
										</select>
										<br />
										<br />
										<b>Notes </b>
										<i>(Required if 'Other' is selected above)</i> :
								<br />
										<textarea id="comment_template" name="comment_template"
											disabled="disabled"
											style="width: 300px; background: #E3E3E3;"
											onkeyup="limitText(this,255);"
											onkeydown="limitText(this,255);"></textarea>
										<br />
									</c:when>
									<c:otherwise>
										<br />
										<br />
										<textarea id="comment_template" name="comment_template"
											class="shadowBox" style="width: 300px;"
											onkeyup="limitText(this,255);"
											onkeydown="limitText(this,255);"></textarea>
									</c:otherwise>
									</c:choose>
									<br />
									<br />
									<a onclick="validate();"><img
											src="${staticContextPath}/images/common/spacer.gif"
											width="72" height="22"
											style="background-image: url(${staticContextPath}/images/btn/submit.gif); float: right;"
											class="btnBevel" /> </a>
									<a href="#"
										style="float: left; padding-right: 18px; color: red;"
										class="btnBevel simplemodal-close"> Cancel </a>
									<br />
									<br />
									<div style="clear: both;"></div>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${currentSO.taskLevelPriceInd == true}">
						<div class="modalHomepage">
							<div style="float: left;">
								Increase Price
							</div>
							<a href="#" class="btnBevel simplemodal-close" style="color: white;"
								float:right>Close</a>
						</div>
						<div class="modalheaderoutline">
							<div class="rejectServiceOrderFrame"
								style="width: 350px; border: 0px">

								<div class="rejectServiceOrderFrameBody" style="width: 300px;">

									<!-- Display validation message -->
									<div class="errorBox clearfix"
										id="increaseSPendLimitResponseMessage"
										style="width: 300px; overflow-y: hidden;">
									</div>

									Your current spend limit is shown below. Enter the new amount,
									then click "submit". You must provide a reason for the
									increase.
									<br />
									<br />
									<input type="hidden" name="taskLevelPriceInd"
										id="taskLevelPriceInd" value="${currentSO.taskLevelPriceInd}" />
									<input type="hidden" name="buyerId" id="buyerId"
										value="${currentSO.companyID}" />
									<input type="hidden" name="selectedSO" id="selectedSO"
										value="${currentSO.id}" />
									<b>Increase Current Maximum Price:</b>

									<table>
										<br />
										<tr></tr>
									</table>

									<div style="overflow: auto; height: 130px">
										<table style="border-bottom: 1px solid #ccc;">
											<c:set var="index" value="0" />
											<c:set var="taskNumber" value="0" />
											<c:set var="seqNum" value="-1" />
											<c:forEach var="task" items="${currentSO.taskList}"
												varStatus="rowCounter">
												<c:if
													test="${!( task.taskStatus!=null && (task.taskStatus=='CANCELED' ||task.taskStatus=='DELETED'))}">
													<c:set var="count" value="${count+1}" />
													<input type="hidden" value="${task.sku}"
														id="taskList[${index}].sku" name="taskList[${index}].sku" />
													<input type="hidden" value="${task.taskId}"
														id="taskList[${index}].taskId"
														name="taskList[${index}].taskId" />
													<input type="hidden" value="${task.taskType}"
														id="taskList[${index}].taskType"
														name="taskList[${index}].taskType" />
													<input type="hidden" value="${task.sequenceNumber}"
														id="taskList[${index}].sequenceNumber"
														name="taskList[${index}].sequenceNumber" />
													<c:if
														test="${task.sequenceNumber == 0 || task.sequenceNumber == seqNum}">
														<input type="hidden" id="taskList[${index}].finalPrice"
															name="taskList[${index}].finalPrice" value="0.00" />
													</c:if>



													<c:if
														test="${task.sequenceNumber != 0 && task.sequenceNumber != seqNum}">
														<tr>
															<c:set var="taskNumber" value="${taskNumber+1}" />

															<td
																style="align: left; width: 190px; padding-bottom: 15px; padding-left: 8px">
																Task ${taskNumber}: ${task.title}
															</td>
															<td width="10" style="padding-bottom: 15px;"></td>
															<td style="padding-bottom: 15px;">
																$
															</td>
															<td width="10" style="padding-bottom: 15px;"></td>
															<td style="padding-bottom: 15px;">
																<c:if test="${task.taskType == 1}">
																	<td>
																		$
																		<fmt:formatNumber value="${task.sellingPrice}"
																			type="NUMBER" minFractionDigits="2"
																			maxFractionDigits="2" />
																		<input type="hidden"
																			id="taskList[${index}].finalPrice"
																			name="taskList[${index}].finalPrice"
																			value="${task.sellingPrice}" />
																	</td>
																</c:if>
																<c:if test="${task.taskType == 0}">
																	<td>
																		<input type="text"
																			onFocus="findPos(this,'checkspend')"
																			onkeyup="formatPriceLabourandParts(this,'checkspend')"
																			onblur="findMaxLabor(this);"
																			id="taskList[${index}].finalPrice" size="9"
																			value="<fmt:formatNumber value="${task.finalPrice}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />"
																			name="taskList[${index}].finalPrice"
																			class="textbox-60 alignRight shadowBox" />
																	</td>
																</c:if>
															</td>
															<td>
																<c:if test="${fn:length(task.priceHistoryList) > 1}">
																	<a id="${count}" class="priceHistoryIconDetail"
																		href="javascript:void(0);"><img
																			src="${staticContextPath}/images/widgets/dollaricon.gif" />
																	</a>
																</c:if>
															</td>

														</tr>
													</c:if>
													<c:if test="${! empty task.sequenceNumber }">
														<c:set var="seqNum" value="${task.sequenceNumber}" />
													</c:if>
													<c:set var="index" value="${index+1}" />
												</c:if>
											</c:forEach>
											<input type="hidden" value="${count}" id="taskCount" />
											<input type="hidden" value="${currentSO.prePaidPermitPrice}"
												id="permitPrice" />
											<input type="hidden" value="${currentSO.soTaskMaxLabor}"
												id="soTaskMaxLabor" />
											<tr>
												<td width="10">
												</td>
											</tr>
										</table>
									</div>
									<c:set var="seqNum" value="-1" />
									<c:forEach var="taskList" items="${currentSO.taskList}">
										<c:if
											test="${!( taskList.taskStatus!=null && (taskList.taskStatus=='CANCELED' ||taskList.taskStatus=='DELETED'))}">
											<c:set var="countHist" value="${countHist+1}" />
											<c:if
												test="${taskList.sequenceNumber != 0 && taskList.sequenceNumber != seqNum}">
												<c:set var="taskNumberHis" value="${taskNumberHis+1}" />
												<div id="${countHist}priceHistoryDetail"
													class="priceHistory"
													style="position: absolute; left: 400px; top: 800px; width: 215px; z-index: 999999;">
													<table cellspacing="0">
														<tr>
															<div class="priceHistory_head" style="float: left;">
															<th colspan="3">
																Price History for Task ${taskNumberHis}
															</th>
														<tr>
															<td width=60px; style="border-bottom: 1px solid #ccc;">
																Price
															</td>
															<td width=80px; style="border-bottom: 1px solid #ccc;">
																Date
															</td>
															<td width=90px; style="border-bottom: 1px solid #ccc;">
																Changed By
															</td>
														</tr>
														</div>
														</tr>
														<div class="priceHistory_body" align="left">

															<c:forEach var="history"
																items="${taskList.priceHistoryList}">
																<tr>
																	<td width=60px;>
																		$
																		<fmt:formatNumber value="${history.price}"
																			type="NUMBER" minFractionDigits="2"
																			maxFractionDigits="2" />
																	</td>

																	<td width=80px;>
																		${history.modifiedDate}
																	</td>

																	<td width=90px;>
																		${history.modifiedByName}
																		<c:if test="${history.modifiedByName!='SYSTEM'}">
    						(ID#${history.modifiedBy})
    					</c:if>
																	</td>
																</tr>
															</c:forEach>

														</div>
													</table>
												</div>
											</c:if>
											<c:if test="${! empty taskList.sequenceNumber }">
												<c:set var="seqNum" value="${taskList.sequenceNumber}" />
											</c:if>
										</c:if>
									</c:forEach>

									<input type="hidden" value="" id="taskErrorCount"
										name="taskErrorCount" />
									<table style="border-bottom: 1px solid #ccc; width: 268px;">
										<tr>
											<td width="150" align="right" style="padding-bottom: 15px;">
												Maximum Labor:
											</td>
											<td width="10" style="padding-bottom: 15px;"></td>
											<td>
												$
											</td>
											<td width="10" style="padding-bottom: 15px;"></td>
											<td align="left" style="padding-bottom: 15px;">
												<div id="maxLabor">
													<fmt:formatNumber value="${currentSO.soTaskMaxLabor}"
														type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
												</div>
											</td>
											<input type="hidden" name="totalSpendLimit"
												id="totalSpendLimit" />

											<input type="hidden" id="currentLimitLabor"
												value="${currentSO.laborSpendLimit}"
												name="currentLimitLabor" />
										</tr>
										</br>
										<tr>
											<td width="140" align="right" style="padding-bottom: 15px;">
												Maximum Materials:
											</td>
											<td width="10" style="padding-bottom: 15px;"></td>
											<td style="padding-bottom: 15px;">
												$
											</td>
											<td width="10" style="padding-bottom: 15px;"></td>
											<td style="padding-bottom: 15px;">
												<input type="hidden" id="totalSpendLimitParts"
													name="totalSpendLimitParts" />
												<input type="text" onFocus="findPos(this,'checkspend')"
													onkeyup="formatPriceLabourandParts(this,'checkspend')"
													onblur="findMaximumLabor(this);" id="increaseLimitParts"
													size="9" name="increaseLimitParts"
													value="<fmt:formatNumber value="${currentSO.partsSpendLimit}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />"
													class="textbox-60 alignRight shadowBox" />
											</td>
											<input type="hidden" id="currentLimitParts"
												name="currentLimitParts"
												value="${currentSO.partsSpendLimit}" />
										</tr>
									</table>
									<table>
										<tr>
											<td width="150" align="right">
												New Combined Maximum:
											</td>
											<td width="10"></td>
											<td>
												$
											</td>
											<td width="10"></td>
											<td align="center" style="padding-bottom: 15px;">
												<div id="totalAmt">
													<fmt:formatNumber
														value="${currentSO.laborSpendLimit+currentSO.partsSpendLimit}"
														type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
												</div>
											</td>
										</tr>
									</table>
									</table>
									<br>
									<br />
									<b>Reason <font color="red">*</font> :</b>
									<br>
									<c:choose>
									<c:when test="${fn:length(spendLimitReasonCodes) > 0}">
										<select name="reason_template" id="reason_template"
											width="300px" style="width: 300px; border: 2px solid #CCCCCC"
											onchange="fnEnable();">
											<option selected="selected" value="-1">
												- Select a reason -
											</option>
											<c:forEach var="reasonCode" items="${spendLimitReasonCodes}">
												<option value="${reasonCode.reasonCodeId}">
													${reasonCode.reasonCode}
												</option>
											</c:forEach>
											<option id="Other" value="-2">
												Other
											</option>
										</select>
										<br />
										<br />
										<b>Notes </b>
										<i>(Required if 'Other' is selected above)</i> :
								<br />
										<textarea id="comment_template" name="comment_template"
											disabled="disabled"
											style="width: 300px; background: #E3E3E3;"
											onkeyup="limitText(this,255);"
											onkeydown="limitText(this,255);"></textarea>
										<br />
									</c:when>
									<c:otherwise>
										<br />
										<textarea id="comment_template" name="comment_template"
											class="shadowBox" style="width: 300px;"
											onkeyup="limitText(this,255);"
											onkeydown="limitText(this,255);"></textarea>
									</c:otherwise>
									</c:choose>
									<br />
									<br />
									<a onclick="validate();"><img
											src="${staticContextPath}/images/common/spacer.gif"
											width="72" height="22"
											style="background-image: url(${staticContextPath}/images/btn/submit.gif); float: right;"
											class="btnBevel" /> </a>
									<a href="#"
										style="float: left; padding-right: 18px; color: red;"
										class="btnBevel simplemodal-close"> Cancel </a>
									<br />
									<br />
									<div style="clear: both;"></div>

								</div>
							</div>
						</div>

					</c:if>
				</div>
			</form>
		</div>
		
	<div id="cancellationDiv" name="cancellationDiv" class="modal" style="padding: 0px 0px; max-width:650px;width:auto;">

     </div>	

		<div id="modal5-1New" title="Accept With Conditions"
			style="display: none">
			<form action="/MarketFrontend/providerConditionalOffer.action"
				method="post" id="conditionalOfferForm2">
				<div id="check5new" class="divContainerUp"
					style="visibility: hidden">
				</div>
				<div class="modalheader">
					<b>Accept With Conditions EEE</b>
				</div>
				<div class="modalheaderoutline">
					<div class="acceptWithConditionsFrameBody">
						<b>Conditions</b>
						<br />
						<br />

						<select class="rejectServiceOrderSelectBox"
							id="condOfferResheduleSpecificSpendLimit"
							onchange="getNextModalNew('modal5-1','condOfferResheduleSpecificSpendLimit')">
							<option value="1">
								Reschedule Service Date
							</option>
							<option value="2">
								Increase Maximum Price
							</option>
							<option value="3" selected="selected">
								Reschedule Service Date & Maximum Price
							</option>
						</select>

						<br />
						<br />

						<table cellpadding="0px" cellspacing="0px">
							<tr>
								<td>
									<strong>Date:</strong>
								</td>
								<td width="10px"></td>
								<td>
									<input type="radio" name="modal5-1Radio"
										id="modal5-1RadioChecked" value="a" checked="checked" />
								</td>
								<td width="2px"></td>
								<td>
									Specific
								</td>
								<td width="10px"></td>
								<td>
									<input type="radio" name="modal5-1Radio"
										onclick="getSpecificDateModalNew('modal5-1','modal5','condOfferResheduleSpendLimit')" />
								</td>
								<td width="2px"></td>
								<td>
									Range
								</td>
							</tr>
						</table>
						<br />
						<br />
						<strong>Requested Date / Time:</strong>
						<table cellspacing="0px"
							style="background-color: #ededed; border: 4px solid #ededed; line-height: 18px">
							<tr>
								<td>
									<input type="hidden" id="modal5-1ConditionalChangeDate1"
										name="conditionalChangeDate1" />
									<input type="text"
										onFocus="findPosDate(this,'check5new');showCalendarControl(this,'modal5-1ConditionalChangeDate1');"
										class="shadowBox" onkeyup="findPosDate(this,'check5new')"
										onblur="hidMsg('check5new');assignDate(this,'modal5-1ConditionalChangeDate1');"
										style="width: 90px; position: relative" id="" name=""
										constraints="{min: '${todaysDate}'}" required="true"
										lang="en-us" />
								</td>
								<td>
									&nbsp;to&nbsp;
								</td>
								<td>
									<s:select cssStyle="width: 90px;" id="conditionalStartTime"
										name="conditionalStartTime"
										list="#application['time_intervals']" listKey="descr"
										listValue="descr" multiple="false" size="1" theme="simple" />
								</td>
							</tr>
						</table>
						<br />
						<br />
						<strong>Amount:</strong>
						<table cellspacing="0px"
							style="background-color: #ededed; border: 4px solid #ededed; line-height: 18px">
							<tr>
								<td>
									&nbsp;Increase&nbsp;to&nbsp;
								</td>
								<td>
									$

									<input type="text" onFocus="findPos(this,'check5new')"
										onkeyup="findPos(this,'check5new')"
										onblur="hidMsg('check5new');" name="conditionalSpendLimit"
										id="conditionalSpendLimit" />
								</td>
							</tr>
						</table>
						<br />
						<br />
						<strong>Offer Expiration:</strong>
						<table cellspacing="0px"
							style="background-color: #ededed; border: 4px solid #ededed; line-height: 18px">
							<tr>
								<td>
									<input type="hidden" id="modal5-1ConditionalExpirationDate"
										name="conditionalExpirationDate" />
									<input type="text" onkeyup="findPosDate(this,'check5new')"
										onFocus="findPosDate(this,'check5new');showCalendarControl(this,'modal5-1ConditionalExpirationDate');"
										class="shadowBox"
										onblur="hidMsg('check5new');assignDate(this,'modal5-1ConditionalExpirationDate');"
										style="width: 90px; position: relative" name="" id=""
										constraints="{min: '${todaysDate}'}" required="true"
										lang="en-us" />
								</td>
								<td>
									&nbsp;at&nbsp;
								</td>
								<td>
									<s:select cssStyle="width: 90px;"
										id="conditionalExpirationTime"
										name="conditionalExpirationTime"
										list="#application['time_intervals']" listKey="descr"
										listValue="descr" multiple="false" size="1" theme="simple" />
								</td>
							</tr>
						</table>
						<br />
						<br />
						<input type="image"
							src="${staticContextPath}/images/common/spacer.gif" width="132"
							height="22"
							style="background-image: url(${staticContextPath}/images/btn/acceptWithCond.gif); float: left;"
							class="btnBevel" />
						<a href="#"> <img
								src="${staticContextPath}/images/common/spacer.gif" width="55"
								height="22"
								style="background-image: url(${staticContextPath}/images/btn/cancel.gif); float: right; padding-right: 18px;"
								class="btnBevel modalClose" onclick="cancelModal()" /> </a>
						<div style="clear: both;"></div>
						<br />
						<br />
					</div>
				</div>
			</form>
		</div>
		<div id="check3" title="Accept With Conditions" style="display: none">
			<form action="/MarketFrontend/providerConditionalOffer.action"
				method="post" id="conditionalOfferForm">
				<div id="check3new" class="divContainerUp"
					style="visibility: hidden">
				</div>
				<div class="modalheader">
					<b>Accept With Conditions FFF</b>
				</div>
				<div class="modalheaderoutline">
					<div class="acceptWithConditionsFrame">

						<div class="acceptWithConditionsFrameBody">
							<b>Conditions</b>
							<br />
							<br />

							<select class="rejectServiceOrderSelectBox"
								id="condOfferResheduleSpecific"
								onchange="getNextModalNew('modal3','condOfferResheduleSpecific')">
								<option value="1" selected="selected">
									Reschedule Service Date1
								</option>
								<option value="2">
									Increase Maximum Price
								</option>
								<option value="3">
									Reschedule Service Date & Maximum Price
								</option>
							</select>
							<br />
							<br />

							<table cellpadding="0px" cellspacing="0px">
								<tr>
									<td>
										<strong>Date:</strong>
									</td>
									<td width="10px"></td>
									<td>
										<input type="radio" id="modal3RadioChecked" name="modal3Radio"
											checked="checked" />
									</td>
									<td width="2px"></td>
									<td>
										Specific
									</td>
									<td width="10px"></td>
									<td>
										<input type="radio" name="modal3Radio"
											onclick="getSpecificDateModalNew('modal3','modal2','condOfferReshedule')" />
									</td>
									<td width="2px"></td>
									<td>
										Range
									</td>
								</tr>
							</table>
							<br />
							<br />
							<strong>Requested Date / Time:</strong>
							<table cellspacing="0px"
								style="background-color: #ededed; border: 4px solid #ededed; line-height: 18px">
								<tr>
									<td>
										<input type="hidden" id="modal3ConditionalChangeDate1"
											name="conditionalChangeDate1" />
										<input type="text"
											onFocus="findPosDate(this,'check3new');showCalendarControl(this,'modal3ConditionalChangeDate1');"
											class="shadowBox" onkeyup="findPosDate(this,'check3new')"
											onblur="hidMsg('check3new');assignDate(this,'modal3ConditionalChangeDate1');"
											style="width: 90px; position: relative" id="" name=""
											constraints="{min: '${todaysDate}'}" required="true"
											lang="en-us" />
									</td>
									<td>
										&nbsp;to&nbsp;
									</td>
									<td>
										<s:select cssStyle="width: 90px;" id="conditionalStartTime"
											name="conditionalStartTime"
											list="#application['time_intervals']" listKey="descr"
											listValue="descr" multiple="false" size="1" theme="simple" />
									</td>
								</tr>
							</table>
							<br />
							<br />
							<strong>Offer Expiration:</strong>
							<table cellspacing="0px"
								style="background-color: #ededed; border: 4px solid #ededed; line-height: 18px">
								<tr>
									<td>
										<input type="hidden" id="modal3ConditionalExpirationDate"
											name="conditionalExpirationDate" />
										<input type="text"
											onFocus="findPosDate(this,'check3new');showCalendarControl(this,'modal3ConditionalExpirationDate');"
											class="shadowBox" onkeyup="findPosDate(this,'check3new')"
											onblur="hidMsg('check3new');assignDate(this,'modal3ConditionalExpirationDate');"
											style="width: 90px; position: relative" id="" name=""
											constraints="{min: '${todaysDate}'}" required="true"
											lang="en-us" />
									</td>
									<td>
										&nbsp;at&nbsp;
									</td>
									<td>
										<s:select cssStyle="width: 90px;"
											id="conditionalExpirationTime"
											name="conditionalExpirationTime"
											list="#application['time_intervals']" listKey="descr"
											listValue="descr" multiple="false" size="1" theme="simple" />
									</td>
								</tr>
							</table>
							<br />
							<br />
							<input type="image"
								src="${staticContextPath}/images/common/spacer.gif" width="132"
								height="22"
								style="background-image: url(${staticContextPath}/images/btn/acceptWithCond.gif); float: left;"
								class="btnBevel" />
							<a href="#"> <img
									src="${staticContextPath}/images/common/spacer.gif" width="55"
									height="22"
									style="background-image: url(${staticContextPath}/images/btn/cancel.gif); float: right; padding-right: 18px;"
									class="btnBevel modalClose" onclick="cancelModal()" /> </a>
							<div style="clear: both;"></div>
							<br />
							<br />
						</div>
					</div>
				</div>
			</form>
		</div>
	
		
		<!-- wildfly changes ending -->
		
		<script>
	function validateAndSubmitReleaseSOModal() {
	    /*var releaseSOComments = dojo.trim(document.getElementById('releaseSOComments').value);*/
	    var releaseSOComments = $('#releaseSOComments').val();
	    releaseSOComments = $.trim(releaseSOComments);
		var releasseSOErrLabel = document.getElementById("releaseSOErrLabel");
		var reasonId = jQuery('#releaseReasonCode option:selected').val();
		var reasonVal = jQuery('#releaseReasonCode option:selected').html();
		reasonVal = $.trim(reasonVal);
		releasseSOErrLabel.innerHTML = "";
		releasseSOErrLabel.style.visibility = 'hidden';
		var error = 0;
		if(reasonId== -1){
			releasseSOErrLabel.innerHTML = "<p class='errorMsg'>Please select a reason</p>";
			error =1;
		}else if ((reasonVal== 'Other (Enter Comments)' || reasonVal== 'others') && (releaseSOComments.length == 0 || releaseSOComments == "Comments") ){
			releasseSOErrLabel.innerHTML = "<p class='errorMsg'>Please enter comments</p>";
			error =1;
			document.getElementById('releaseSOComments').focus();
		}
		if (error == 1) {
			releasseSOErrLabel.style.visibility = 'visible';
		} else {
			
			 if ((reasonVal!= 'Other (Enter Comments)'|| reasonVal!= 'others')&& releaseSOComments == "Comments"){
				 $('#releaseSOComments').attr("value","");
			 }
					 document.forms['releaseSoForm'].submit();
		}
	}
	</script>

		<%--Begin Accept Bucks Agreement Modal JSP--%>
		<div id="bucks" style="display: none">
			<div style="height: 200px; width: 500px; overflow: auto">
				${acceptBucksSOTermsAndCond.termsCondContent}
			</div>
			<p align="center">
				<input type="button" value="Agree"
					id="providerServiceLiveBucksAgreeBtn" class="modalClose"
					onclick="agreeBucks();" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="Cancel" id="cancelBtn"
					class="btnBevel modalClose" onclick="closeBucks();" />
			</p>
		</div>
		<div id="bucksWarn" style="display: none">
			<div style="height: 50px; width: 500px; overflow: auto">
				Please ask Admin to accept payment Terms and Conditions before
				accepting Service Order
			</div>
			<p align="center">
				<input type="button" value="  OK  " id="okBtnBucksWarn"
					class="btnBevel modalClose" onclick="closeBucksWarn();" />
			</p>
		</div>

		<script type="text/javascript">
	
      //Calculates the spend limit

	</script>
		<%--End Increase Maximum Price Modal JSP--%>


		<%--Begin Cancel SO Modal JSP--%>
		<c:set var="statusCd" scope="page"
			value="${THE_SERVICE_ORDER_STATUS_CODE}" />

		<c:set var="statusAccepted" scope="page"
			value="<%=new Integer(OrderConstants.ACCEPTED_STATUS)%>" />
		<c:set var="statusActive" scope="page"
			value="<%=new Integer(OrderConstants.ACTIVE_STATUS)%>" />

		<script type="text/javascript">
	

		

	</script>
		<%--End Cancel SO Modal JSP--%>

		<%--Begin Accept SO Modal Scripts --%>
		<script type="text/javascript">


	
</script>
		<%--End Accept SO Modal Scripts--%>


		<script>
 
 
 		</script>
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			<jsp:param name="PageName" value="Service Order Details" />
		</jsp:include>

		<%-- start reject SO--%>
		<jsp:include page="popup_reject_service_order.jsp"></jsp:include>
		<%-- end reject SO--%>


		<%-- start conditional --%>
		
		<%-- end conditional --%>

		<c:set var="so" value="${THE_SERVICE_ORDER}" scope="request" />
		
		

		<!-- The follwing code scrolls down the page to captcha section when wrong security code is enter -->
		<c:if test="${captchaError ne null}">
			<c:choose>
				<c:when test="${not empty groupOrderId}">
					<script type="text/javascript">
      				var yPosition = 600;
      				var interval = 1500;
      				<c:forEach begin="1" end="${numberOfOrders}" step="1">
      				 	yPosition = yPosition + 25;
      				 	interval = interval + 1000;
      				</c:forEach>
      				//alert(yPosition);
      				setTimeout("window.scrollTo(100,yPosition)", interval);
      				//window.scrollTo(100,yPosition);
				</script>
				</c:when>
				<c:otherwise>
					<script type="text/javascript">
      				setTimeout("window.scrollTo(100,${captchaErrorPosition})",1500);
      			</script>
				</c:otherwise>

			</c:choose>
		</c:if>
		<%-- Modal for terms and conditions, only exists for the SO Bids view of the details page --%>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
		<div id="termsAndConditionsModal" class="jqmWindow">
			<div class="dialogHeader">
				<img src="/ServiceLiveWebUtil/images/icons/modalCloseX-transp.gif"
					id="modalClose-img" onclick="showHideTermsAndConditions('hide');" />
				<h3>
					ServiceLive Terms & Conditions
				</h3>
			</div>
			<h4>
				Terms & Conditions
			</h4>
			<div id="dialogBody">
				${acceptSOTermsAndCond.termsCondContent}
			</div>
		</div>		
		<script type="text/javascript">
		jQuery.noConflict();	
		 jQuery.modal.defaults = {
		overlay: 50,
		overlayId: 'modalOverlay',
		overlayCss: {},
		containerId: 'modalContainer',
		containerCss: {},
		close: false,
		closeTitle: 'Close',
		closeClass: 'modalClose',
		persist: false,
		onOpen: null,
		onShow: null,
		onClose: null
	};
 		function showHideTermsAndConditions(arg){
                              jQuery("#termsAndConditionsModal").jqm();
                              	if(arg == 'show'){
                              	jQuery("#termsAndConditionsModal").jqmShow();
                              	jQuery("#termsAndConditionsModal>#dialogBody").html(jQuery("#termsAndConditionsContainer").html());
                              	}else if(arg == 'hide'){
                              	jQuery("#termsAndConditionsModal").jqmHide();
                              	}
                              }
 		</script>
 				

		<div id="explainer" style="display: none;"></div>
		<%-- ss: video JS and modal, will be called from inside Docs & Photos iframe --%>
		<script type="text/javascript">
				function showVideoTop(videoId){				
				var browser=navigator.appName;
				var url = "http://www.youtube.com/v/"+videoId+"&hl=en&fs=1&rel=0";
			    jQuery(document).ready(function($) {
	    	    var vidObj = $('#videoModal');
			 	$(vidObj).find('#movieId').attr('value',url);
		     	$(vidObj).find('embed').attr('src',url);
		     	$(vidObj).css('top',$('#inner_document_grid').position().top +300).css('left','20%');

		     	var htmlToRewite = $(vidObj).html();
		     	$(vidObj).html(htmlToRewite); //needed for IE
		     	$(vidObj).jqm({modal:true, toTop: true});
		     	$(vidObj).jqmShow();
		     	

		    	});
			}

				function doOnChangeValue(Event, textArea, limit) {
					jQuery(document).ready(function($) {
					var remLength=limit;
					
					if (textArea.value.length > limit) {
						textArea.value = textArea.value.substring(0, 225);    // Added
					  //textArea.value=textArea.value.substring(0,limit); return;
					  return imposeMaxLength(Event, textArea, limit);
					  }
					else{
						 remLength = limit - textArea.value.length;
						 var displayMsg ="Remaining Length: "+remLength;
					 	 document.getElementById("remaining_count").style.display='';
						 document.getElementById("remaining_count").innerHTML=displayMsg;
					}

					}); 
				    }
				 	function checkAndMakeCommentMendatory(Object){
					     var index=Object.selectedIndex;
					    jQuery(document).ready(function($) {
					    if(index==3 || index==6 || index==7 || index==8)
					    document.getElementById("comment_optional").style.display='';
					    else
					    document.getElementById("comment_optional").style.display='none';
					    });
					    }
				 
				 
				    function imposeMaxLength(Event, Object, MaxLen){
				     return (Object.value.length < MaxLen)||(Event.keyCode == 8 ||Event.keyCode==46||(Event.keyCode>=35&&Event.keyCode<=40))
				    }

				 //Ending changes For Reject Reason Code	    
		</script>
		<div class="jqmWindow modalDefineTerms" id="videoModal"
			style="background-color: #000000; width: 520px; margin: 0;">
			<div class="modalHomepage">
				<a href="#" class="jqmClose">Close</a>
			</div>
			<div class="modalContent" style="padding-left: 20px;">
				<object width="480" height="295">
					<param name="movieId" id="movieId" value=""></param>
					<param id="all" name="allowFullScreen" value="true"></param>
					<param id="ddd" name="allowscriptaccess" value="always"></param>
					<embed src="" id="embedBid" type="application/x-shockwave-flash"
						allowscriptaccess="always" allowfullscreen="true" width="480"
						height="295"></embed>
				</object>
			</div>
		</div>
		
	</body>
</html>