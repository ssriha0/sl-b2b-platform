
function displaySmartBanner(){
	var os = 'unknown';
	var userAgent = navigator.userAgent || navigator.vendor || window.opera;
	if( userAgent.match( /iPad/i ) || userAgent.match( /iPhone/i ) || 
	          userAgent.match( /iPod/i )){
		os = 'ios';
	}
	else if( userAgent.match( /Android/i )){
		os = 'android';
	}

	//If OS is either Android/IOS, display the banner
	if('unknown' != os){
	       new SmartBanner({
			daysHidden: 0,			
	             daysReminder: 0,			
	             appStoreLanguage: 'us', 
			title: 'ServiceLive',
			author: 'Transform ServiceLive LLC',
			button: 'OPEN',
			force: os
		});
	     
	     try{
	    	  $(".smartbanner-show").css("margin-top", "0px");
	     }catch(e){
	    	  var smartBannerView = document.getElementsByClassName("smartbanner-show")[0];
	    	  smartBannerView.setAttribute("style","margin-top:0px");
	     }
	     
	     //for home page 
	     if(document.getElementById('newnotice')){
	    	 if(document.getElementsByClassName("smartbanner-show")[0])
	    		 {
	    		 	document.getElementById('newnotice').style.marginTop ='90px';
	    		 	try
	    		 	{
	    		 		$(".smartbanner").css("margin-top", "-90px");
	    		 	}catch(e)
	    		 	{
	    		 		var smartView = document.getElementsByClassName("smartbanner")[0];
	    		 		smartView.setAttribute("style","margin-top:-90px");
	    		 	}
	    		 }
 		 }
	     
 		 //for login page 
	     else if(document.getElementById('login')){
	    	 if(document.getElementsByClassName("smartbanner-show")[0])
    		 {
	    		 document.getElementById('login').style.marginTop ='50px';
    		 }
 		 }
	     
 		 //for static pages
	     else{
	    	 try{
		    	  $(".smartbanner-show").css("margin-top", "90px");
		    	  $(".smartbanner-show").addClass("staticBanner");
		      }catch(e){
		    	  var smartBannerView = document.getElementsByClassName("smartbanner-show")[0];
		    	  smartBannerView.setAttribute("style","margin-top:90px");
		    	  smartBannerView.classList.add("staticBanner");
		      }
 		 }
	}	    
}


function closeSmartBanner(){
	if(document.getElementById('newnotice')){
		document.getElementById('newnotice').style.marginTop ='0px';
	}
	else if(document.getElementById('login')){
		document.getElementById('login').style.marginTop ='0px';
	}
	else{
		try{
	    	  $(".staticBanner").css("margin-top", "0px");
	      }catch(e){
	    	  var smartBannerView = document.getElementsByClassName("staticBanner")[0];
	    	  smartBannerView.setAttribute("style","margin-top:0px");
	      }
	}
}

