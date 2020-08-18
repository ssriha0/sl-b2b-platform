jQuery.noConflict();
//minify or pack this document before production
jQuery(document).ready(function($){
	
	//global stuff
	$("li.parentNavItem").mouseover(function(){
		$(this).addClass("over");
	});
	$("li.parentNavItem").mouseout(function(){
		$(this).removeClass("over");
	});
	$(".hidden").hide(); //hide all things with a class of hidden


	//on password blur, trim white spaces from the password field
	$("input#password").blur(function () {
		var str = $(this).val();
		str = jQuery.trim(str);
		$(this).val(""+ str +"");
	});


	// show and hide the submenus
	$("#simpleMenu li.hasChild").hover( function () {
			$(this).addClass("hovers");
		}, function () {
			$(this).removeClass("hovers");
	});
	// clear any input field that has a default value when you focus in it
	// give the input field a class of "clearValue"
	$("input.clearValue").click(function() {
		if( this.value == this.defaultValue ) {
			this.value = "";
		}
	}).focus(function() {
		if( this.value == this.defaultValue ) {
			this.value = "";
		}
	}).blur(function() {
		if( !this.value.length ) {
			this.value = this.defaultValue;
		}
	});
	//show the new credit card
	$("a#createNew").click(function(){
		if ($("div#creditCardDetails").is(':visible'))
		{
			$("div#creditCardDetails").hide();
		}
		else
		{
			$("div#creditCardDetails").show();
			$(".creditcard").children("input").attr("checked", false);
			$(".creditcard").children("input").val("");
		}
	});
	//existing creditcard - we want the hover bg in ie
	$(".creditcard").mouseover(function(){
		$(this).addClass("over");
	});
	$(".creditcard").mouseout(function(){
		$(this).removeClass("over");
	});

	//we want the background to stick on the clicked address
	$(".creditcard").click(function(){
		$("div#creditCardDetails").hide();
		//add it to this one
	});

	//existing locations - we want the hover bg in ie
	$(".addresslabel").mouseover(function(){
		$(this).addClass("over");
	});
	$(".addresslabel").mouseout(function(){
		$(this).removeClass("over");
	});
	//we want the background to stick on the clicked address
	$(".addresslabel").click(function(){
		//remove the class from other addresses
		$(".addresslabel").removeClass('selected');
		$(".addresslabel").children("input").attr("checked", false);

		//add it to this one
		$(this).addClass('selected');
		$(this).children("input").attr("checked", true);
		$(this).children("input").focus();
	});
	//toggle the name your address field on the join form
	$("input#homeAddr").click(function(){
		$("dl.nameAddress").slideToggle();
	});
	//toggle the join form if their home address isn't the one they want to use
	$("input#accountAddressCheck").click(function(){
		$("div.accountAddressNew").slideToggle();
		$("dl.addyTog").slideToggle();
	});
	$('#promoTerms').jqm({trigger: 'a#promoLink'});
	$("#serviceLiveBucks").jqm({trigger: 'a.openBucks'});



	$("a.acategory").click(function(){
		var catText = $(this).text();
		var catID = $(this).attr("title");
		$("a#CatID").text(catText);
		$("input#selected_verticle").val(catID);
		$("ul.sf-menu ul").hide();
		$("div.step2, div.step3").hide();
		$("div.step2").fadeIn();
	});

	$('ul.sf-menu').superfish();

	//$("a.acategory").click(function(){
		//var catText = $(this).text();
		//var catID = $(this).attr("title");
		//$("a#CatID").text(catText);
		//$("input#selected_verticle").val(catID);
		//$("ul.sf-menu ul").hide();
		//$("div.step2, div.step3").hide();
		//$("div.step2").fadeIn();
	//});

	$(".step2 input").click(function(){
		$("div.step3").fadeIn();
		$("#providerResults").addClass("enabledResults");
	});

	// For phone number masking
	$("input.phone").mask("(999) 999-9999");
	
	// Order Group Manager Search Fields Masking
	$(".ogmPhone").mask("(999) 999-9999");
	$(".ogmZip").mask("99999");	

	//Provider Insurance Tab Modal Dialogs
	$('#providerInsuranceDetailsModal').jqm({modal: true, toTop: true});
	
	
	//Terms Conditions Modal Dialogs and Agree Button handling
	$('#buyerTermsCondModal').jqm({modal: true, trigger: 'a.buyerTermsCondA'});
	$('#serviceLiveBucksModal').jqm({modal: true, trigger: 'a.serviceLiveBucksA'});
	$("input#simpleBuyerTermsCondAgreeBtn").click(function(){
	    $("input#simpleBuyerTermsCondChk").attr("checked", "checked");
	});
	$("input#simpleBuyerServiceLiveBucksAgreeBtn").click(function(){
	    $("input#simpleBuyerServiceLiveBucksChk").attr("checked", "checked");
	});

	$("#locNameBoxClick").click(function() {
		$(".nameAddress").toggle();
	});

	$("#workLocNameBoxClick").click(function() {
		$(".accountAddressNew").toggle();
	});

	$("#accAdC input.checked").click(function() {
		$(this).hide();
		$(this).attr("checked", "checked");
		$("#accAdC input.unchecked").show();
		$(".addy").fadeTo("slow", 0.33);
		$(".accountAddressNew").fadeIn();
	});

	$("#accAdC input.unchecked").click(function() {
		$(this).hide();
		$(this).attr("checked", "");
		$("#accAdC input.checked").show();
		$(".addy").fadeTo("slow", 1);
		$(".accountAddressNew").fadeOut();
	});

	//show the new location form
	$("a#createNewAddress").click(function(){
		$("div#newAddress").show();
		//remove the class from any selected addresses
		$(".addresslabel").removeClass('selected');
	});

	$("textarea.grow").focus(function(){
		$(this).animate({height: "300px"});
	});

	$("textarea.grow").blur(function(){
		$(this).animate({height: "60px"});
	});


	$("textarea.expand").focus(function(){
		$(this).animate({height: "100px"});
	});

	$("textarea.expand").blur(function(){
		$(this).animate({height: "40px"});
	});

	$(".exampleSO a").fancybox();

	$(".accList").accordion();
	$("#providerTabs > ul").tabs();

	$("#browser, .browser").treeview({
			control: "#treecontrol",
			cookieId: "treeview"
		});


        // Select all
        $("a#checkAll").click( function() {
            $("#ungroupedOrders input").attr('checked', true);
            return false;
        });

        // Select none
        $("a#uncheckAll").click( function() {
            $("#ungroupedOrders input").attr('checked', false);
            return false;
        });
	
	$(document).ready(JT_init);

	function JT_init(){
		$("a.jTip").hover(
				function(){JT_show(this.href,this.id,this.name)},
				function(){$('#JT').remove()}
			).click(function(){return false;});

		$("a.jTipUsername > input").focus(
			function(){JT_show($("a.jTipUsername").attr("href"),this.id,$("a.jTipUsername").attr("name"))}).click(function(){return false});
	
			$("a.jTipUsername > input").blur(function(){$('#JT').remove()});
		
		$("a.jTipReferralCode > input").focus(
			function(){JT_show($("a.jTipReferralCode").attr("href"),this.id,$("a.jTipReferralCode").attr("name"))}).click(function(){return false});
	
			$("a.jTipReferralCode > input").blur(function(){$('#JT').remove()});
	
	}

	function JT_show(url,linkId,title){
		if(title == false)title="&nbsp;";
		var de = document.documentElement;
		var w = self.innerWidth || (de&&de.clientWidth) || document.body.clientWidth;
		var hasArea = w - getAbsoluteLeft(linkId);
		var clickElementy = getAbsoluteTop(linkId) - 3; //set y position

		var queryString = url.replace(/^[^\?]+\??/,'');
		var params = parseQuery( queryString );
		if(params['width'] === undefined){params['width'] = 250};
		if(params['link'] !== undefined){
		$('#' + linkId).bind('click',function(){window.location = params['link']});
		$('#' + linkId).css('cursor','pointer');
		}

		if(hasArea>((params['width']*1)+75)){
			$("body").append("<div id='JT' style='width:"+params['width']*1+"px'><div id='JT_arrow_left'></div><div id='JT_close_left'>"+title+"</div><div id='JT_copy'><div class='JT_loader'><div></div></div>");//right side
			var arrowOffset = getElementWidth(linkId) + 11;
			var clickElementx = getAbsoluteLeft(linkId) + arrowOffset; //set x position
		}else{
			$("body").append("<div id='JT' style='width:"+params['width']*1+"px'><div id='JT_arrow_right' style='left:"+((params['width']*1)+1)+"px'></div><div id='JT_close_right'>"+title+"</div><div id='JT_copy'><div class='JT_loader'><div></div></div>");//left side
			var clickElementx = getAbsoluteLeft(linkId) - ((params['width']*1) + 15); //set x position
		}

		$('#JT').css({left: clickElementx+"px", top: clickElementy+"px"});
		$('#JT').show();
		$('#JT_copy').load(url);

	}

	function getElementWidth(objectId) {
		x = document.getElementById(objectId);
		return x.offsetWidth;
	}

	function getAbsoluteLeft(objectId) {
		// Get an object left position from the upper left viewport corner
		o = document.getElementById(objectId)
		oLeft = o.offsetLeft            // Get left position from the parent object
		while(o.offsetParent!=null) {   // Parse the parent hierarchy up to the document element
			oParent = o.offsetParent    // Get parent object reference
			oLeft += oParent.offsetLeft // Add parent left position
			o = oParent
		}
		return oLeft
	}

	function getAbsoluteTop(objectId) {
		// Get an object top position from the upper left viewport corner
		o = document.getElementById(objectId)
		oTop = o.offsetTop            // Get top position from the parent object
		while(o.offsetParent!=null) { // Parse the parent hierarchy up to the document element
			oParent = o.offsetParent  // Get parent object reference
			oTop += oParent.offsetTop // Add parent top position
			o = oParent
		}
		return oTop
	}

	function parseQuery ( query ) {
	   var Params = new Object ();
	   if ( ! query ) return Params; // return empty object
	   var Pairs = query.split(/[;&]/);
	   for ( var i = 0; i < Pairs.length; i++ ) {
		  var KeyVal = Pairs[i].split('=');
		  if ( ! KeyVal || KeyVal.length != 2 ) continue;
		  var key = unescape( KeyVal[0] );
		  var val = unescape( KeyVal[1] );
		  val = val.replace(/\+/g, ' ');
		  Params[key] = val;
	   }
	   return Params;
	}

	
	function blockEvents(evt) {
				  if(evt.target){
				  evt.preventDefault();
				  }else{
				  evt.returnValue = false;
				  }
	}

	
});
		
function handlePolicyModal(policyAmount,buttonType,credId,category,currentDocId,currentDocName) {
	var policyAmount = setPolicyModalAmount(category);	
	currentAmount = policyAmount;
	setPolicyModalTitle(category);
	//setDefaultPolicyDetails(policyAmount,category);
 	currentType = category;
	credentialId = credId;
	button = buttonType;	
	if (isAdmin) {
		if (credId == 0) {
			document.getElementById("insuranceApprovalWidget").style.display = 'none';
		} else {
			document.getElementById("credentialRequest.credentialKey").value = credId;	
			document.getElementById("vendorCredId").value = credId;
			document.getElementById("insuranceApprovalWidget").style.display = 'block';					
		}
	}
	document.getElementById('errorMessages').style.display="none";	
	document.getElementById('errorMessages').value="";
	jQuery(document).ready(function($) {
		$("input#insurancePolicyDto.agencyCountry").value = "";
		$("input#insurancePolicyDto.amount").value = policyAmount;
		$("input#insurancePolicyDto.agencyName").value = "";
		$("input#insurancePolicyDto.agencyState").value = "";
		$("input#insurancePolicyDto.carrierName").value = "";
		$("input#insurancePolicyDto.policyNumber").value = "";
		$("input#modal2ConditionalChangeDate2").value = "";
		$("input#modal2ConditionalChangeDate1").value = "";
		policyInfoSubmit(policyAmount,buttonType,credId,category,currentDocId,currentDocName);
		if (isAdmin) {	
			setCredentialStatus(category);
		}		
		$('#providerInsuranceDetailsModal a.jqmClose').click(function(){						
			if (isAdmin) {		
				var mainForm = document.getElementById('insuranceForm');
				
				//Code added as part of Jira SL-20645 -To capture time while auditing insurance
				var auditTime=document.getElementById('insurancePolicyDto.auditTimeLoggingIdNew').value;
				mainForm.action = "allTabView.action?tabView=tab4&auditInsTime="+auditTime;
				
				mainForm.submit();			
			}
			$('#providerInsuranceDetailsModal').hide();	
			$('.modalOverlay').hide();				
		});
		
		$('#providerInsuranceDetailsModal a.jqmCancel').click(function(){
			if (isAdmin) {		
				var mainForm = document.getElementById('insuranceForm');
				mainForm.action = "allTabView.action?tabView=tab4";
				mainForm.submit();			
			}			
			$('#providerInsuranceDetailsModal').hide();	
			$('.modalOverlay').hide();	

		//	$("#providerInsuranceDetailsModal").jqmClose();
		});
		
		 $('#providerInsuranceDetailsModal').show();
		 $('.modalOverlay').show();
		scroll(0,0);		
		
		// Show popup now
		// jQuery('#providerInsuranceDetailsModal').jqm({modal: true, toTop: true});
		// jQuery('#providerInsuranceDetailsModal').jqmShow();
		//$('#providerInsuranceDetailsModal').jqmClose(".jqmClose");
		//Provider Insurance Tab Modal Dialogs
		//$('#providerInsuranceDetailsModal').jqm({modal: true});
	});
}
	
function openPolicyDetails(category) {
	var policyAmount = setPolicyModalAmount(category);
	currentAmount = policyAmount;
    setPolicyModalTitle(category);
	setDefaultPolicyDetails(policyAmount,category);
	jQuery.noConflict();
	jQuery(document).ready(function($) {
		$('#providerInsuranceDetailsModal').show();
		 $('.modalOverlay').show();
		scroll(0,0);
	});
}

function handleInsuranceInformationModal(){
	jQuery(document).ready(function($) {	
		$('#newProviderInsuranceInformationModal').show();
		$('.modalOverlay').show();
		scroll(0,0);

		$('#newProviderInsuranceInformationModal a.jqmClose').click(function(){
			$('#newProviderInsuranceInformationModal').hide();	
			$('.modalOverlay').hide();
		});
	});
}
    function showDocumentUploadModal(dtitle,buyerId,buyerDocId,buttonType,proDocId,docName,spnId)
		{
			document.getElementById('uploadErrorMessages').style.display="none";	
			document.getElementById('uploadErrorMessages').value="";
			jQuery.noConflict();
			jQuery(document).ready(function($) {			
				document.getElementById("docTitle").innerHTML = 'Document Title : '+dtitle;
				if(buttonType == 'Update'){
					var docHTML = "<p class='paddingBtm'><strong>Document Name:</strong> <span> <a href='jsp/spn/spnMonitorAction_loadDocument.action?docID="+proDocId+"' target='blank'>"+docName+"</a></span></p>";
					document.getElementById('docDisplay').style.display="block";	
					document.getElementById('docDisplay').innerHTML = docHTML;
				}
				document.getElementById("dtitle").value = dtitle;
				document.getElementById("buyerId").value = buyerId;
				document.getElementById("buyerDocId").value = buyerDocId;
				document.getElementById("buttonType").value = buttonType;	
				document.getElementById("proDocId").value = proDocId;
				document.getElementById("spnId").value = spnId;
				document.getElementById("spnBuyerId").value = buyerId;
									
				$('#uploadDocument a.jqmCancel').click(function(){
					$('#uploadDocument').hide();	
					$('.modalOverlay').hide();
				});
				$('#uploadDocument').show(); 	
		   		$('.modalOverlay').show();
				scroll(0,0);	
			 });					
	}
