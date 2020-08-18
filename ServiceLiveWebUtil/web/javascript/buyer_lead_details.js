var ProjectNew = {
      upArrow :"&#9660;",
      	downArrow :"&#9658;",

    initializeApp : function(){
        ProjectNew.initTabs("#orderSummaryTab"); //Init tabs

        //Event listeners
        $(".tabHandle").bind("click", function(){
            $("#orderSummaryTab").css("padding-top","3px");
            $(".tabHandle").removeClass("active");
            $(this).addClass("active");
            var pageToShow = $(this).attr("data-tab");
             $("#omSuccessDiv").hide();
             $('#osErrorDiv').hide();
            $(".tabArea").hide();
            $("#"+pageToShow).show();
        });

        $(".accordionHead").bind("click", function(){
        	var accContentPage = $(this).next();
             if($(accContentPage).is(':visible'))
             {
             $($(this).children()[0]).html(ProjectNew.downArrow);
             $(accContentPage).hide();
             }
			else
			{
			$($(this).children()[0]).html(ProjectNew.upArrow);
			$(accContentPage).slideDown("fast");
			}
			$("#orderSummaryHandle").show();
			$("#test1Handle").show();
			$("#test2Handle").show();
			$("#test3Handle").show();
        });
    },

    initTabs : function(initTabToShow){
        $(".tabArea").hide();
        $(initTabToShow).show();

              this.initAccordion("#providerInfoAcc"); //Initial accordion view
    },

    initAccordion : function(initPageToShow){
              $("#providerStatus")//.html("Scheduled");

        $(".accordionContent").show(); //Hide all accordion contents initially..//edited
              $(".arrow").html(ProjectNew.downArrow);

              //Hide all arrows too
              var allArrows = ".accordionPage div.accordionHead";
              for(var i=1; i < $(allArrows).length; i++){
                     $($($(allArrows)[i]).children()[0]).html(ProjectNew.upArrow);
              }

              var selectedAccordionHead = initPageToShow+" div.accordionHead";
              $($(selectedAccordionHead).children()[0]).html(ProjectNew.upArrow); //Show the required arrow image (Up arrow)

              var selectedAccordion = initPageToShow+" div.accordionContent";
        $(selectedAccordion).show();
    },


    editField : function(editId)
    {
           var id = editId.substring(4,editId.length);
           $('#osErrorDiv').hide();
           $('#omSuccessDiv').hide();
           $("#orderSummaryTab").css("padding-top","5px");
           // hide all the data entry divs before enabling the required one alone.
           $("#editnameDiv").hide();
           $("#editmembershipDiv").hide();
           $("#editaddressDiv").hide();
           $("#editstateDiv").hide();
           $("#editphoneDiv").hide();
           $("#editemailDiv").hide();
           //show all main display divs before enabling the required one
           $("#nameDiv").show();
           $("#membershipDiv").show();
           $("#addressDiv").show();
           $("#stateDiv").show();
           $("#phoneDiv").show();
           $("#emailDiv").show();

           $("#"+id+"Div").hide();
           $("#selectedId").val(editId);
           $("#"+editId+"Div").show();
           $("#firstName").val($("#editedfirstName").val());
           $("#lastName").val($("#editedlastName").val());
           $("#membershipId").val($("#editedmembershipId").val());
           $("#street1").val($("#editedstreet1").val());
           $("#street2").val($("#editedstreet2").val());
           $("#email").val($("#editedemail").val());
           var phoneNo=$("#editedphone").val();
           var val = phoneNo.split('-');
        	  $('#phone_1').val(val[0]);
        	  $('#phone_2').val(val[1]);
        	  $('#phone_3').val(val[2]);
           
    },

       hideEditableFields : function(cancelId)
       {
    	   $('#osErrorDiv').hide();
           var id = cancelId.substring(6,cancelId.length);
           $("#edit"+id+"Div").hide();
           $("#"+id+"Div").show();
       },

       reset:  function(id, value)
    {
       var val = $("#"+id).val();
       if(val == null || val == ''){
              $("#"+id).val(value);
       }
    },

       resetValue:  function(cancelId, value)
        {
           var id = cancelId.substring(6,cancelId.length);
           $("#"+id).val(value);
           if('phone' == id){
           	  var val = value.split('-');
           	  $('#phone_1').val(val[0]);
           	  $('#phone_2').val(val[1]);
           	  $('#phone_3').val(val[2]);
           }
           $('#osErrorDiv').hide();
           $("#orderSummaryTab").css("padding-top","3px");
        },

       resetName:  function(cancelId, firstName, lastName)
        {
            $("#firstName").val(firstName);

            $("#lastName").val(lastName);

        },
       resetAddress:  function(cancelId, street1, street2)
        {
           $("#street1").val(street1);
           $("#street2").val(street2);
        },
       resetMembershipId:  function(cancelId, membershipId)
           {
           $("#membershipId").val(membershipId);
           }



};

       //functions to view attachment
       function viewDoc(docId, catId, docPath){
              if(8 == catId){
                     window.parent.showVideoTop(docPath);
              }
              else{
                     var loadForm = document.getElementById('attachmentForm');
                     loadForm.action = "buyerLeadManagementController_viewDocument.action?docId="+docId;
                     loadForm.submit();
              }
       }

	function submitLead(){
       	   $("#orderSummaryTab").css("padding-top","5px");
       	   var id = document.getElementById(id);
       	   var ind="";
       	   var leadId=jQuery.trim(document.getElementById('leadID').value);
       	   var selected=jQuery.trim(document.getElementById('selectedId').value);
       	   if(selected.match('editmembership'))
       	   {
       		   ind="mem";
       	   }
    	   var oldMemShipId=jQuery.trim(document.getElementById('memShipId').value);
    	   var loadForm = $("#leadEdit").serialize();
	       var url="/MarketFrontend/buyerLeadOrderSummaryController_updateCustomerInfo.action";

	       if(selected.match('editname')){
	       		if(jQuery.trim(document.getElementById('firstName').value) == '' || jQuery.trim(document.getElementById('lastName').value) == ''){
	       			$('#osErrorDiv').show();
   	 	    	  	$('#osErrorDiv').html("Please provide first name or last name");
   	 	    	  	$("#orderSummaryTab").css("padding-top","28px");
   	 	    	  	return;
	       		}
	       }
	       if(selected.match('editaddress')){
	       		if(jQuery.trim(document.getElementById('street1').value) == ''){
	       			$('#osErrorDiv').show();
   	 	    	  	$('#osErrorDiv').html("Please provide address");
   	 	    	  	$("#orderSummaryTab").css("padding-top","28px");
   	 	    	  	return;
	       		}
	       }
	       if(selected.match('editmembership')){
	       		if(jQuery.trim(document.getElementById('membershipId').value) == ''){
	       			$('#osErrorDiv').show();
   	 	    	  	$('#osErrorDiv').html("Please provide Shop Your Way Membership Id");
   	 	    	  	$("#orderSummaryTab").css("padding-top","28px");
   	 	    	  	return;
	       		}
	       }
	       $("#buyerLeadCustomerInformationDiv").load(url,loadForm,function() {
	   		   if((oldMemShipId==''&& ind.match('mem')))
		       {
	   			   var url = "/MarketFrontend/buyerLeadOrderSummaryController.action?leadId="+leadId+"&popup=false&widgetSuccessInd=false";
		           window.location.href=url;
		       }
       	   });



       }
     

       function convertPhoneNo(phone1,phone2,phone3)
       {
    	   var phoneNo = phone1+phone2+phone3;
    	   return phoneNo;
       }
       function validate()

   		{
    	   	var phoneNo1=jQuery.trim(document.getElementById('phone_1').value);
    	   	var phoneNo2;
    	   	phoneNo2=jQuery.trim(document.getElementById('phone_2').value);
    	   	var phoneNo3;
    	   	phoneNo3=jQuery.trim(document.getElementById('phone_3').value);

    	   	var phoneNo=convertPhoneNo(phoneNo1,phoneNo2,phoneNo3);
            var phoneno = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
   	 	    if((phoneNo.match(phoneno)) && phoneNo1 != '' && phoneNo2 != '' && phoneNo3 != '' )
   	 	    {
   	 	     	submitLead();
   	 	        $('#osErrorDiv').hide();
   	 	        return ;
   	 	    }
   	 	    else
   	 	    {
   	 	    	  // alert("In valid phone No");
   	 	    	  $('#osErrorDiv').show();
   	 	    	  $('#osErrorDiv').html("Please provide Valid phone number");
   	 	    	  $("#orderSummaryTab").css("padding-top","28px");
   	 	    	  return;
   	 	    }

       	}

    	function validateEmail()
    	{
	    	email=jQuery.trim(document.getElementById('email').value);
	    	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email) && email !='')
		    {
	    		submitLead();
	    		$('#osErrorDiv').hide();
	    		return true;
		    }
	    	else
		 	{
	    		$('#osErrorDiv').show();
	    		$("#orderSummaryTab").css("padding-top","28px");
	    		$('#osErrorDiv').html("Please provide a Valid Email id");
	    		return false;
		 	}
    	}

    	 //To open provider profile in a new window from lead management dashboard page
       	function openProviderProfile(resouceId,vendorId){
       			var url = "/MarketFrontend/providerProfileInfoAction_execute.action?resourceId="
       					+ resouceId + "&companyId=" + vendorId + "&popup=true";
       			newwindow = window
       					.open(url, '_publicproviderprofile',
       							'resizable=yes,scrollbars=yes,status=no,height=700,width=1000');
       		if (window.focus) {
       			newwindow.focus();
       			}
       	}
       	
        function openProviderFirmProfile(vendorId){
            var url = "/MarketFrontend/providerProfileFirmInfoAction_execute.action?vendorId=" + vendorId + "&popup=true";
            newwindow = window
                    .open(url, '_publicproviderprofile',
                            'resizable=yes,scrollbars=yes,status=no,height=700,width=1000');
	        if (window.focus) {
	            newwindow.focus();
	            }
	     }