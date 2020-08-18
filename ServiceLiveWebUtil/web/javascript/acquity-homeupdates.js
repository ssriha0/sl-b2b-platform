jQuery.noConflict();
   jQuery(document).ready(function($) {
	
	    // Shows one random testimonial
		var randNum = Math.floor(Math.random() * 6);
				$("#hpTestimonials li:eq(" + randNum + ")").show();
		
	
		// Shows Category's subcategories
		$("#hpCategories ul li h4 a").click(function() {
  			//$(this).parent().next().slideToggle('fast');
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
		
			
 });


	
	
	
	