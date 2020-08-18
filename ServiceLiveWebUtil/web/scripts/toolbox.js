$(function() {
	//$("h2#page-title, h2#page-role, h2.jsifr").sifr({ 
	//	path: '/ServiceLiveWebUtil/fonts/', 
	//	font: 'HelveticaNeueBold' 
	//});
	jQuery(document).ready(function($) {
	    $("ul.superfish").supersubs({ 
	 		minWidth:    12,   
	        maxWidth:    27,  
	        extraWidth:  1    
	    }).superfish();  
	});

});



