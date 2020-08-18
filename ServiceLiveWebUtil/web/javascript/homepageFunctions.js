jQuery(document).ready(function($){
	//set the Enter Zip Code background for inputs when the page loads.
		$("input#zipCode1, input#zipCode").css('background', '#FFF url(/ServiceLiveWebUtil/images/enter-zip-code.jpg) left center no-repeat');
		//change the background on focus or blur	
		$("input#zipCode1, input#zipCode").focus(function(){
			$(this).css("background", "#FFF");
			});
			$("input#zipCode1, input#zipCode").blur(function(){
				var t = $(this);
				//check to see if a value has been entered.  we dont want to show a bg image if user entered a zipcode.
				if (t.val() == '') {
					$(this).css('background', '#FFF url(/ServiceLiveWebUtil/images/enter-zip-code.jpg) left center no-repeat');
				}
			});

			//on password blur, trim white spaces from the password field
			$("input#password").blur(function () {
				var str = $(this).val();
				str = $.trim(str);
				$(this).val(""+ str +"");
			});

			$($.parseHTML("a")).addClass("selected");	

			// Shows one random testimonial
			var randNum = Math.floor(Math.random() * 6);
					$("#hpTestimonials li:eq(" + randNum + ")").show();

			// Shows Category's subcategories
			$("#hpCategories ul li h4 a").click(function() {
	  			//jQuery(this).parent().next().slideToggle('fast');
			});
			
			// Clears contents of input firlds
			$("#headerLogin input#username").focus(function() {
				if ($(this).val() == "Username") {
			    	$(this).val("");
				}
			  });
			
			$("#headerLogin input#password").focus(function() {
			    if ($(this).val() == "Password") {
			    	$(this).val("");
				}
			  });
			
			$("#hpEnterZip input.hpEnterZipZip").focus(function() {
			    if ($(this).val() == "Enter Zip Code") {
			    	$(this).val("");
				}
			  });
			
				
		
			$("li.parentNavItem").mouseover(function(){
				$(this).addClass("over");
			});
			$("li.parentNavItem").mouseout(function(){
				$(this).removeClass("over");
			});
});	
			

	function setBuyerType(buyerTypeId){
		document.getElementById('buyerId').value=buyerTypeId;
	}
		
	//modal video script
    function setModalVideo()
	{
       	jQuery('#modalVideo').jqm({modal:true, toTop: true});
	   	jQuery('#modalVideo').jqmShow();
	}

	function setNewZipFields(name, mainCategoryId, categoryId, subCategoryId, serviceTypeTemplateId, buyerTypeId)
	{ 
		document.form2.zipCode.value=document.getElementById('zipCode1').value;
		document.getElementById('pop_name').value=name;
		document.getElementById('mainCategoryId').value=mainCategoryId;
		document.getElementById('categoryId').value=categoryId;
		document.getElementById('subCategoryId').value=subCategoryId;
		document.getElementById('serviceTypeTemplateId').value=serviceTypeTemplateId;
		document.getElementById('buyerId').value=buyerTypeId;
		jQuery('#modalUserTypeChoose').jqm({modal:true, toTop: true});
		jQuery('#modalUserTypeChoose').jqmShow();
	}
			
	function setCategoryFields(name, mainCategoryId, categoryId, subCategoryId, serviceTypeTemplateId, buyerTypeId)
	{ 
		document.getElementById('pop_name').value=name;
		document.getElementById('mainCategoryId').value=mainCategoryId;
		document.getElementById('categoryId').value=categoryId;
		document.getElementById('subCategoryId').value=subCategoryId;
		document.getElementById('serviceTypeTemplateId').value=serviceTypeTemplateId;
		document.getElementById('buyerId').value=buyerTypeId;
	 	jQuery('#modalUserCategory').jqm({modal:true, toTop: true});
     	jQuery('#modalUserCategory').jqmShow();
	}
			
	function setDefineTermsCandidatesFields(name, mainCategoryId, categoryId, subCategoryId, serviceTypeTemplateId, buyerTypeId)
	{ 
     	jQuery('#modalDefineTermsCandidates').jqm({modal:true, toTop: true});
     	jQuery('#modalDefineTermsCandidates').jqmShow();
	}
			
	function setDefinePreScreenedFields(name, mainCategoryId, categoryId, subCategoryId, serviceTypeTemplateId, buyerTypeId)
	{ 
     	jQuery('#modalDefinePreScreened').jqm({modal:true, toTop: true});
     	jQuery('#modalDefinePreScreened').jqmShow();
	}
			
	function setDefineTermsFundPayFields(name, mainCategoryId, categoryId, subCategoryId, serviceTypeTemplateId, buyerTypeId)
	{ 
     	jQuery('#modalDefineTermsFundPay').jqm({modal:true, toTop: true});
     	jQuery('#modalDefineTermsFundPay').jqmShow();
	}
			
	function doMouseOver (id) {
		jQuery('#'+id).show();
	}
		
	function doMouseOut (id) {
		jQuery('#'+id).hide();
	} 
	
	jQuery(document).ready(function($){
		
		
	});

