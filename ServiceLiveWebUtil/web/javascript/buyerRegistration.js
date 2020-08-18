//ss: required for buyer_registration.jsp
//ss: depends on jQuery
function copyValue(a)
		{
			if(a.checked)
			{
				document.getElementById("mailingStreet1").value = document.getElementById("businessStreet1").value;
				document.getElementById("mailingStreet2").value = document.getElementById("businessStreet2").value;
				document.getElementById("mailingAprt").value = document.getElementById("businessAprt").value;
				document.getElementById("mailingCity").value = document.getElementById("businessCity").value;
				document.getElementById("mailingState").value = document.getElementById("businessState").value;
				document.getElementById("mailingZip").value = document.getElementById("businessZip").value;
			}
			else
			{
				document.getElementById("mailingStreet1").value = "";
				document.getElementById("mailingStreet2").value = "";
				document.getElementById("mailingAprt").value = "";
				document.getElementById("mailingCity").value = "";
				document.getElementById("mailingState").value = "";
				document.getElementById("mailingZip").value = "";
			}
		}
		function agreeBucks(){
			document.getElementById("serviceLiveBucksInd").checked="checked";
		}

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

		jQuery(document).ready(function($){

		$('#modal2ConditionalChangeDate1').datepicker(
	                {dateFormat: "yy-mm-dd", changeMonth:true, changeYear:true,
	                onSelect: function() {jQuery("#modal2ConditionalChangeDate1").css('color','black');}});
		JT_init();
		$("#serviceLiveBucks").jqm();
		var dateValue=jQuery("#modal2ConditionalChangeDate1").val();
		if(dateValue==''|| dateValue=='YYYY-MM-DD'){
		jQuery("#modal2ConditionalChangeDate1").val('YYYY-MM-DD');
		jQuery("#modal2ConditionalChangeDate1").css('color','gray');
		}
	
		$('a.openBucks').click(function(){
			$("#serviceLiveBucks").jqmShow();
			return false;
		});

		$("#modal2ConditionalChangeDate1").click(function(){
			jQuery("#modal2ConditionalChangeDate1").unmask();
			jQuery("#modal2ConditionalChangeDate1").css('color','black');
			jQuery("#modal2ConditionalChangeDate1").mask('9999-99-99');
		});
        $('#modal2ConditionalChangeDate1').blur(function(event){
            var dateValue1=jQuery("#modal2ConditionalChangeDate1").val();
            if((dateValue1=='' || dateValue1 =='____-__-__')){
            $("#modal2ConditionalChangeDate1").val('YYYY-MM-DD');
            $("#modal2ConditionalChangeDate1").css('color','gray');
            event.stopPropagation();
            }
        });
        
		
		});
		


