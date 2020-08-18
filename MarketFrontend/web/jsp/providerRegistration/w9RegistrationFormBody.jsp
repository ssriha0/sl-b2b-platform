<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<link href="${staticContextPath}/javascript/CalendarControl.css" rel="stylesheet" type="text/css">
<link href="${staticContextPath}/javascript/style.css" rel="stylesheet"	type="text/css" />
<link href="${staticContextPath}/javascript/confirm.css" rel="stylesheet"	type="text/css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
<link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery.modal.min.css" type="text/css"></link>


<div class="modalheaderoutline" >
<style type="text/css">
#ui-datepicker-div {
    border: 1px solid #666666;
    display: none;
    z-index: 3001111;
}
</style>


	<script language="javascript" type="text/javascript">
function siteHelp()
{

jQuery('#dpop').show();
	

}


function siteHelpHide()
{

jQuery('#dpop').hide();
	

}

</script>

<c:if test="${notw9Admin != true }">

<c:if test="${w9Modal != true }">
	<script language="javascript" type="text/javascript">
		function popitup(url) {
			newwindow=window.open("<s:url value='w9registrationAction_w9History.action'/>",'w9History','location=1,status=1,scrollbars=1,width=1000,height=600');
			if (window.focus) {newwindow.focus()}
				return false;
		}
		
	jQuery.noConflict();
	jQuery(document).ready(function($) {
	
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
	});
		
	 function modalOpen(dialog) {
	 		dialog.overlay.css({height: "205%", position: "absolute" })
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
		
/*
		$(document).ready(function(){

			$(".menu a").hover(function() {
			  $(this).next("em").animate({opacity: "show", top: "-75"}, "slow");
			}, function() {
			  $(this).next("em").animate({opacity: "hide", top: "-85"}, "fast");
			});

		});*/

		function showTable(){
			 document.getElementById(id).style.display = 'block';
		}	

	    function toggle(id) {
	    	var state = document.getElementById(id).style.display;
	    	if (state == 'block') {
	    		document.getElementById(id).style.display = 'none';
	    	} else {
	    		document.getElementById(id).style.display = 'block';
	    	}
	    }
	</script>

	<div class="w9history">
		<a class="active" href="#" onclick="popitup('w9registrationAction_w9History.action');">View W9-History</a>
	</div>
</c:if>
	<style type="text/css" media="screen">
    <!--
 
        .bubbleInfo {
            position: relative;
        }
        .trigger {
            position: absolute;
			color:#00A0D2;
			text-decoration:underline;            
        }
     
        /* Bubble pop-up */

        .popup {
                position: absolute;
                display: none;
                z-index: 50;
                border-collapse: collapse;
				border:1px solid #C3C3C3;
        }

        .popup td.corner {
                height: 15px;
                width: 19px;
        }
 
        .popup table.popup-contents {
                font-size: 12px;
                line-height: 1.2em;
                background-color: #fff;
                color: #666;
                font-family: "Lucida Grande", "Lucida Sans Unicode", "Lucida Sans", sans-serif;
                }

        table.popup-contents th {
                text-align: center;
                font-weight: bold;
				background-color:#E5EECC;
				padding:3px;                
                }

        table.popup-contents td {
                text-align: left;
                }
        
        tr#release-notes th {
                text-align: left;
                text-indent: -9999px;
                background: url(http://jqueryfordesigners.com/demo/images/coda/starburst.gif) no-repeat top right;
                height: 17px;
                }

        tr#release-notes td a {
                color: #333;
        }
        
    -->
    </style>


    <script type="text/javascript">
    <!--

    $(function () {
        $('.bubbleInfo').each(function () {
            var distance = 10;
            var time = 250;
            var hideDelay = 500;

            var hideDelayTimer = null;

            var beingShown = false;
            var shown = false;
            var trigger = $('.trigger', this);
            var info = $('.popup', this).css('opacity', 0);


            $([trigger.get(0), info.get(0)]).mouseover(function () {
                if (hideDelayTimer) clearTimeout(hideDelayTimer);
                if (beingShown || shown) {
                    // don't trigger the animation again
                    return;
                } else {
                    // reset position of info box
                    beingShown = true;

                    info.css({
                        top: -90,
                        left: -33,
                        display: 'block'
                    }).animate({
                        top: '-=' + distance + 'px',
                        opacity: 1
                    }, time, 'swing', function() {
                        beingShown = false;
                        shown = true;
                    });
                }

                return false;
            }).mouseout(function () {
                if (hideDelayTimer) clearTimeout(hideDelayTimer);
                hideDelayTimer = setTimeout(function () {
                    hideDelayTimer = null;
                    info.animate({
                        top: '-=' + distance + 'px',
                        opacity: 0
                    }, time, 'swing', function () {
                        shown = false;
                        info.css('display', 'none');
                    });

                }, hideDelay);

                return false;
            });
        });
    });
    
    //-->
    </script>
    
    <div align="left" class="modal"  id="popUpfaq" style="display:none">
				    	<div class="modalHomepage" style="background-color: white;" >
							<div style="float: left;">
							</div>
							<a id="closeLink" style="color:red;cursor: pointer" class="btnBevel simplemodal-close" onclick="fnReturnFocus()">Close</a>

						</div>
					  	  <h3 style="padding-top: 0px;">Frequently Asked Questions - Personal Information</h3>
					  	  <br><br>
					  	  
<img  id="firstCloseArrow" onclick="showFirstAnswer();"  style="padding: 0px 4px 0px 5px; position: relative; top: 2px;cursor: pointer;"
					 src="${staticContextPath}/images/widgets/blueRightArrow.gif" />
<img id="firstOpenArrow" onclick="showFirstAnswerOpen();" style="display:none; padding: 0px 4px 0px 5px; position: relative; top: 2px;cursor: pointer"
					 src="${staticContextPath}/images/widgets/grayDownArrow.gif" />
					  	 <span id="firstQuestion" class="firstQuestion" onclick="showFirstAnswer();" style="color:#00A0D2; font-family: Verdana,Arial,Helvetica,sans-serif;
    font-weight: bolder;cursor: pointer;">Why do I have to provide Personal Identification Information?</span>
						<span id="firstQuestionOpen" class="firstQuestion" onclick="showFirstAnswerOpen();" style="display:none; color:#666666; font-family: Verdana,Arial,Helvetica,sans-serif;
    font-weight: bolder;cursor: pointer;">Why do I have to provide Personal Identification Information?</span>
	<br>
			<div id="firstAnswer" style="display:none; border:1px solid #cecece; ">
						    <p style="font-size: 10px;  padding: 2px 0 6px;">To help the government fight the funding of terrorism and money laundering activities, Federal Law (31 CFR Parts 1010 and 1022 of the 
						    Bank Secrecy Act Regulations) requires us to ask for your name, address, date of birth, and your taxpayer identification number.</p>
						    </div>
						  
		
					<br>	
			<img  id="secondCloseArrow" onclick="showSecondAnswer();" style="padding: 0px 4px 0px 5px; position: relative; top: 2px;cursor: pointer"
					 src="${staticContextPath}/images/widgets/blueRightArrow.gif" />
<img  id="secondOpenArrow" onclick="showSecondAnswerOpen();" style="display:none; padding: 0px 4px 0px 5px; position: relative; top: 2px;cursor: pointer"
					 src="${staticContextPath}/images/widgets/grayDownArrow.gif" />		
		<span id="secondQuestion" class="secondQuestion" onclick="showSecondAnswer();" style="color:#00A0D2; font-family: Verdana,Arial,Helvetica,sans-serif;
    font-weight: bolder;cursor: pointer">What if I do not have a U.S. Taxpayer Identification Number? </span>
	<span id="secondQuestionOpen" class="secondQuestion" onclick="showSecondAnswerOpen();" style="display:none; color:#666666; font-family: Verdana,Arial,Helvetica,sans-serif;
    font-weight: bolder;cursor: pointer">What if I do not have a U.S. Taxpayer Identification Number? </span>
	<br>
				<div id="secondAnswer"  style="display:none ;border:1px solid #cecece;">
						    <p style="font-size: 10px;  padding: 2px 0 6px;">You must provide us with an alternate form of personal identification, such as a Passport issued by a recognized foreign government.</p> 
						  </div>
						  
		</div>

    
    
<div class="menugroup_list" id="faq" style="display: none;border: 0px;">
	<div class="menugroup_body" id="generalcompletionId">
		<div class="modalHomepage" style="background-color: black;" >
			<div style="float: left;">
			</div>
			<a id="closeLink" href="#" style="color:white;" onclick="jQuery.modal.impl.close(true);">Close</a>
		</div>
    </div>
    
  <div class="mainWellContent">
  <h3 style="float:left;">Frequently Asked Questions - Personal Information</h3><br>
  
  <div dojoType="dijit.TitlePane" title="Why do I have to provide personal identification Information?" id="" class="faqPane" open="false" style="float:left;">
    <p>To help the government fight the funding of terrorism and money laundering activities, Federal Law (31 CFR Parts 1010 and 1022 of the 
    Bank Secrecy Act Regulations) requires us to ask for your name, address, date of birth, and your taxpayer identification number.</p>
  </div>
  <div dojoType="dijit.TitlePane" title="What if I do not have a U.S. Taxpayer Identification Number? " id="" class="faqPane" open="false" style="float:left;">
    <p>You must provide us with an alternate form of personal identification, such as a Passport issued by a recognized foreign government.</p> 
  </div>
  <br>
</div> 
</div>
	
			
<div style="width: 700px; margin: 20px; text-align: left;" id="w9details" >
<div id="w9Messages" > </div>

		<c:if test="${w9prefillFlag == true && w9Modal != true }">
			<div class="warning">
				For your convenience, we have pre-filled the form below with information from your profile.  
				Your W9 will not be saved until you verify the information below is correct and make any adjustments needed.  
				When you're finished click the Save &amp; Continue &raquo; button at the bottom of the page to complete your W9.
			</div>
		</c:if>
		<c:if test="${w9Modal != true }">
			<form action="<s:url action="%{#request['saveAction']}" />" method="POST" id="frmW9Details" style="display: inline;">
		</c:if>
		<c:if test="${w9Modal == true }">
			<form method="POST" id="frmW9Details" style="display: inline;">
		</c:if>
		
		<div class="jqTitlePane jqcontentWellPane" id="taxIDWidget">
			<div class="jqTitlePaneTitle jqOpen" tabindex="0">
				<div class="menu_head" style="-moz-user-select: none;height:22px;"><img src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Legal Tax Identification Information</div>
			</div>
			<div class="jqTitlePaneContentOuter">
				<div class="jqReset">
					<div class="jqTitlePaneContentInner" style="padding-left: 10px;">
						<!-- begin form -->
						<div class="clearfix">
							<div id="content_right_header_text_w9_messages">
								<%@ include file="./message.jsp"%>
							</div>
							<div id="chooseTaxIdTypeErrorDiv" style="display:none;margin: 10px 0pt; padding-left: 30px;" class="errorBox error clearfix" >							
											<ul>
											<ul>
											<ul>
											<li>Please confirm if the Taxpayer ID Number type is EIN or SSN.</li>											
											</ul>
											</ul>
											</ul>
											
							</div>
							<!--  <a onClick="toggle('helpdiv')"><span style="font-weight: bold;">Site Help</span></a></br>-->
<div class="bubbleInfo" id="helpdiv">
        <div>
            <a class="trigger" id="download"><span id="siteHelp" onmouseover="siteHelp();" onmouseout="siteHelpHide();"  style="font-weight: bold;" >Site Help</span></a><br></br>
        </div>
        <table id="dpop" class="popup"  border="0" onmouseout="siteHelpHide();" onmouseover="siteHelp(); ">
                <tr>
                    <td>
                    	<table class="popup-contents"  cellspacing="0" cellpadding="0" border="1" width="100%">
        				  <tbody>                    	
                    			<tr><td colspan="3">Supplier requirements for specifying your business name and TIN</td></tr>
                                <tr>
                                        <th >Provider Type</th>
                                        <th>Business Name</th>
                                        <th>Required TIN</th>
                                </tr>
                                <tr>
                                        <td>Individual</td>
                                        <td>Name of individual</td>
                                        <td>SSN</td>
                                </tr>
                                <tr>
                                        <td>Sole Proprietorship</td>
                                        <td>Name of individual business owner<br></br>
                                        	(e.g.,John Smith", not the d/b/a name</td>
                                        <td>SSN preferred, but can be EIN</td>
								</tr>
                                <tr>
                                        <td>Limited Liability Company(LLC)</td>
                                        <td>a) If a disregarded entiry for tax purposes,<br></br>
                                        	the Owner's name as the business name <br></br>
                                        	followed the name of the LLC as d/b/a name.<br></br><br></br>
                                        	b) If NOT a disregarded entiry for tax purposes<br></br> 
                                        	   then the name of the LLC as the business name. 
                                        	</td>
                                        <td>a) SSN preferred if owner an INDIVIDUAL EIN<br></br>
                                        	   if OWNER is another corporation<br></br><br></br><br></br>
                                        	b) EIN of LLC   
                                        </td>
								</tr>
                                <tr>
                                        <td>Partnership</td>
                                        <td>The name of the partnership used for reporting income<br></br>
                                        	to the partners as the business name.</td>
                                        <td>EIN</td>
								</tr>
								<tr>
                                        <td>Corporation</td>
                                        <td>Name listed on Articles of Incorporation or on SS-4<br></br>
                                        	when corporation was formed</td>
                                        <td>EIN</td>
								</tr>
                                <tr>
                                        <td>Company</td>
                                        <td>a) If unincorporated business, name of individual owner<br></br>
                                        	   (sole proprietor) as business name.<br></br><br></br>
                                        	b) If incorporated business, full name of business as<br></br>
                                        	   business name.   
                                        </td>
                                        <td>a) SSN<br></br><br></br><br></br>
                                            b) EIN
                                        </td>
								</tr>
								<tr>
                                        <td>Joint Owners</td>
                                        <td>Name of joint owners whose TIN is used on Form W-9<br></br>
                                            must appear as business name
                                        </td>
                                        <td>Usually SSN, but could be EIN</td>
								</tr>
								<tr>
                                        <td>Trust</td>
                                        <td>Name listed on SS-4 when trust applied for TIN</td>
                                        <td>EIN</td>
								</tr>								
								<tr>
                                        <td>Individual Beneficiary of a Trust or Estate</td>
                                        <td>Individual's name</td>
                                        <td>SSN</td>
								</tr>			
							</tbody>						
						</table>
					</td>
		</table>
    </div>
							<p style="font-weight: bold;">
								See <a href="http://www.irs.gov/pub/irs-pdf/fw9.pdf?portlet=3">IRS W9 Form</a> for more instructions.
							</p>
								<p>An asterisk (<span class="req">*</span>) indicates a required field.</p>
							<p></p>
												
							<div class="left" style="width: 48%">
								<label>Legal Business Name <span class="req">*</span></label>
								<br /><input type="text" class="text" name="w9prefill.legalBusinessName" value="${w9prefill.legalBusinessName }">
								<p></p>
								

								<label>Tax Status <span class="req">*</span></label>
								<br />
								<select name="w9prefill.taxStatus.id">
									<option value="-1">
										Select One
									</option>
									<c:forEach items="${businessTypes}" var="busType">
										<c:if test="${busType.id == w9prefill.taxStatus.id}">
											<c:set var="sel_var" value="SELECTED" />
										</c:if>
										<c:if test="${busType.id != w9prefill.taxStatus.id}">
											<c:set var="sel_var" value="" />
										</c:if>
										<option value="${busType.id }"${sel_var}>
											${busType.descr }
										</option>
									</c:forEach>
								</select>
								<p></p>
								
								<c:choose>
									<c:when test="${w9isExist==true && w9isExistWithSSNInd == false}">
										<c:set var="errorBoxClass" value="errorBox clearfix" />
										<c:set var="errorBoxStyle" value="width: 140px; overflow-y: hidden; visibility: block;" />
									</c:when>
		            				<c:otherwise>
		            					<c:set var="errorBoxClass" value="" />
										<c:set var="errorBoxStyle" value="" />
		            				</c:otherwise>
		            			</c:choose>
		            			<c:set var="sel_var" value="checked" />
		            			<table>
		            			<tr>
		            			<td width="140px">
								<div class="${errorBoxClass}" id="w9ErrorMessage"
									style="${errorBoxStyle}">
									
										
										<c:forEach items="${taxPayerTypeIds}" var="taxPayerType">
											<c:if test="${taxPayerType.id == w9prefill.taxPayerTypeId}">
													<c:set var="sel_var" value="checked" />
											</c:if>
											<c:if test="${taxPayerType.id != w9prefill.taxPayerTypeId}">
													<c:set var="sel_var" value="" />
											</c:if>
	                  						<input type="radio" id="${taxPayerType.descr }" onclick="clickTaxPayerId();" name="w9prefill.taxPayerTypeId"  ${checkedEIN} value="${taxPayerType.id}" ${sel_var} />${taxPayerType.descr }
	                  					</c:forEach> 
	
	             				</div>
	             				</td><td>
	             				<span id="editTaxNo" style="display:none;">
	                  					&nbsp<a id="editLink" style="color:#00A0D2;cursor: pointer"  onclick="editLink();">Edit </a>
	                  					
	                  					&nbsp<a id="cancelLinks" style="display:none;color:#00A0D2;cursor: pointer"   onclick="cancelLink();">Cancel</a>
	                  			</span>
	                  			</td>
	                  			</tr>
	                  			</table>
 							 	<input type="hidden" class="text" id="editOrCancel" name="w9prefill.editOrCancel" value="${w9prefill.editOrCancel}">	
 								<input type="hidden" class="text" id="taxPayerTypeId" name="w9prefill.orginalTaxPayerTypeId" value="${w9prefill.orginalTaxPayerTypeId}">
 								<input type="hidden" class="text" id="einTaxId" name="taxId" value="">
 								<input type="hidden" class="text" id="confirmEinTaxId" name="taxId" value="">
 								<input type="hidden" class="text" id="ssnTaxId" name="taxId" value="">
 								<input type="hidden" class="text" id="ssnconfirmTaxId" name="taxId" value="">
 								
 							<input type="hidden" class="text" id="w9prefill.originalEinNo" name="w9prefill.originalEin" value="${w9prefill.originalEin}">
 							<input type="hidden" class="text" id="w9prefill.originalUnmaskedEin" name="w9prefill.originalUnmaskedEin" value="${w9prefill.originalUnmaskedEin}">	

								<label>Taxpayer ID Number (SSN or EIN) <span class="req">*</span></label>
								<br /><input type="text" id="taxPayerId" class="text" onfocus="clickTaxId();" name="w9prefill.ein" value="${w9prefill.ein }">
								<p></p>
								
								<label>Confirm Taxpayer ID Number (SSN or EIN) <span class="req">*</span></label>
								<br /><input type="text" id="confirmTaxPayerId" onfocus="clickConfirm();" class="text" name="w9prefill.ein2" value="${w9prefill.ein2 }">
								<p></p>								

								<label>Is your organization tax exempt? </label>
								
								<div class="switch yesno">
									<input type="hidden" name="w9prefill.isTaxExempt" value="false">								
								</div>

								
							</div>
							<div class="right" style="width: 48%">
								<label>Business Name (if different from Legal Business Name)</label>
								<br /><input type="text" class="text"name="w9prefill.doingBusinessAsName" value="${w9prefill.doingBusinessAsName }">
								<p></p>
								<div class="left" style="width: 70%">
								<label>Address <span class="req">*</span></label>
								<br /><input type="text" class="text" name="w9prefill.address.street1" value="${w9prefill.address.street1 }">
								<br /><input type="text" class="text" name="w9prefill.address.street2" value="${w9prefill.address.street2 }">
								<p></p>

								<label>City <span class="req">*</span></label>
								<br /><input type="text" class="text" name="w9prefill.address.city" value="${w9prefill.address.city }">
								<p></p>

									<div class="left" style="width: 48%; margin-right: 10px;">
										<label>State <span class="req">*</span></label>
										<br />
										<select name="w9prefill.address.state">
											<option value="-1">
												Select One
											</option>
											<c:forEach items="${stateCodes}" var="state">
												<c:if test="${state.type ==

w9prefill.address.state}">
													<c:set var="sel_var" value="SELECTED" />
												</c:if>
												<c:if test="${state.type !=

w9prefill.address.state}">
													<c:set var="sel_var" value="" />
												</c:if>
												<option value="${state.type}"${sel_var}>
													${state.descr }
												</option>
											</c:forEach>
										</select>										
									</div>

									<div class="left" style="width: 25%">
										<label>ZIP <span class="req">*</span></label>
										<br /><input type="text" class="text" name="w9prefill.address.zip" value="${w9prefill.address.zip }">
									</div>
								
								</div>
								<div class="right" style="width: 20%; padding-right: 14px">
									<label>Apt</label>
									<br /><input type="text" class="text" name="w9prefill.address.aptNo" value="${w9prefill.address.aptNo }">
								</div>
							</div>
							<br style="clear:both;"/>
						</div>						
						<!-- end form -->
					</div>
				</div>
			</div>
		</div>
		
		<div class="paddme">
			<h3>Certification</h3>
			<p>Under penalties of perjury, I certify that:</p>
			<ol>
				<li>The number shown on this form is my correct taxpayer identification number (or I am waiting for a number to be issued to me), and</li>
				<li>I am not subject to backup withholding because: (a) I am exempt from backup withholding, or (b) I have not been notified by the Internal Revenue Service (IRS) that I am subject to backup withholding as a result of a failure to report all interest or dividends, or (c) the IRS has notified me that I am no longer subject to backup withholding, and </li>
				<li>I am a U.S. person (including a U.S. resident alien).</li>
			</ol>
		</div>
			
		<div class="jqTitlePaneContentOuter">
			<div class="jqTitlePaneContentInner clearfix">
			<input type="checkbox" class="checkbox" name="w9prefill.isPenaltyIndicatiorCertified" value="true" /> Check to certify your legal tax identification. <span class="req">*</span> 
			</div>
		</div>
		
			<script type="text/javascript">
	var taxPayerTypeId=jQuery("#taxPayerTypeId").val();
	
	if (taxPayerTypeId != '' && taxPayerTypeId ==3)
	{
		jQuery("#editTaxNo").show();
		var editOrCancel= jQuery("#editOrCancel").val();
		
		if(editOrCancel=='edit')
		{
		jQuery('#cancelLinks').hide();
		jQuery('#editLink').show();
		jQuery("#editTaxNo").show();
		
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
		

}

</script>
<style type="text/css">
#ui-datepicker-div {
    border: 1px solid #666666;
    display: none;
    z-index: 3001111;
}

</style>
				<c:if test="${w9Modal == true }">
		
		<div id="dateOfBirth" class="paddme" style="width: 700px; display:none;" >
			<hr>
				When you reach a certain level of payment of transaction in the ServiceLive platform, you need to provide us with this additional information
				
				<span style="padding-left:365px;"><img src="${staticContextPath}/images/s_icons/help.png"/>&nbsp;&nbsp;</span>
				<a class="trigger" id="download" onClick="showPopUpFAQ();" href="#">
					<span style="font-weight: bold;">Why do I need to provide this information?</span></a>
				<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span class="req">What is your Date of Birth?</span>&nbsp;<span class="req">*</span>
				&nbsp;
				
				<input type="text" class="text" style="z-index: 999999;" id="dob" name="w9prefill.dob" value="${w9prefill.dob}" onClick="showDatepicker();">&nbsp;				
				<span style="font-size: 9px;">(Note: Date of Birth should be in MM/DD/YYYY format)</span><br><br>
			<hr>
		</div>
		</c:if>
				<c:if test="${w9Modal != true }">
		<div id="dateOfBirth" class="paddme" style="width: 900px; display:none;" >
			<hr>
				When you reach a certain level of payment of transaction in the ServiceLive platform, you need to provide us with this additional information
				
				<span style="padding-left:620px;"><img src="${staticContextPath}/images/s_icons/help.png"/>&nbsp;&nbsp;</span>
				<a class="trigger" id="download" onClick="showPopUpFAQ();" href="#">
					<span style="font-weight: bold;">Why do I need to provide this information?</span></a>
				<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span class="req">What is your Date of Birth?</span>&nbsp;<span class="req">*</span>
				&nbsp;
				
				<input type="text" class="text" style="z-index: 999999;" id="dob" name="w9prefill.dob" value="${w9prefill.dob}" onfocus="showDatepicker();">&nbsp;				
				<span style="font-size: 9px;">(Note: Date of birth should be in MM/DD/YYYY format)</span><br><br>
			<hr>
		</div>
		</c:if>
		
		
		<div class="paddme">
			<p> 
			This information will be stored as part of your Provider Firm Profile. You can update it at 
			any time.</p>
		</div>
		<c:if test="${w9Modal != true }">
			<div>
				<input type="submit" class="button" value="Save &amp; Continue &raquo;">
				<input type="submit" class="button" name="w9prefill.cancel" value="Cancel">
			</div>
		</c:if>
		</form>
		<c:if test="${w9Modal == true }">
			<div>
			<button id="modalSavebtn" onclick="submitW9();">Update Info</button>
			<button class="modalClose" onclick="hide_w9modal();">Cancel</button>
			</div>
		</c:if>

</div>
</c:if>

<c:if test="${notw9Admin == true }">
	<div style="background-color: #dddddd; font-weight: bold; font-size: 13px; padding: 5px; text-align: left;">
		ALERT-Provider Admin Action Required
	</div>
	<div style="padding: 5px; font-size: 12px; text-align: left;">
		Please notify your Provider Admin to complete the W9 Legal
		Tax Information in the Company Profile. Once this information is
		entered, you will be able to submit for payment on service orders.
	</div>
	<div style="padding: 5px; text-align: left;">
			<input type="button" onClick="hide_w9modal();" name="tellAdmin" value="Okay" style="width: 100px;" />
	</div>
</c:if>
</div>