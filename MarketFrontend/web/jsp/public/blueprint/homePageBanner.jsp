<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title></title>
<style type="text/css">
   .bannerDiv{border-left: 0 none;
              border-right: 0 none;
              border-top: 0 none;
              font-weight: normal;
              padding: 7px 4px;
              text-align: center;
              background: none repeat scroll 0 0 #FFF1A8;
              border: 1px solid #CCCCCC;
              color: #222222;
              padding: 5px;
              height:15px;
              font-size:12px;
              font-family:sans-serif;
              position:relative;
              top:0px;
              width:100%;
              z-index: 999999999;
              } 
       
    
</style>
<script type="text/javascript">
      function getBannerCookie(name){
	       var re = new RegExp(name + "=([^;]+)");
	       var value = re.exec(document.cookie);
	       return (value != null) ? unescape(value[1]) : null;
	      }
      function setBannerCookie(c_name,value,exdays){
	      var exdate=new Date();
	      exdate.setDate(exdate.getDate() + exdays);
	      var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
	      document.cookie=c_name + "=" + c_value +";path=/";
	       }
      function displayBanner(){
		   var fullVersion;
	       var verOffset;
		   var positionFirstDot;
		   var majorVersion ;
		   var bannercookie = getBannerCookie('isDismissedBanner');
		   var nAgt = navigator.userAgent;
           var ieIndex = '<fmt:message bundle="${serviceliveCopyBundle}" key="name.browser.index.ie"/>';
           var ffIndex = '<fmt:message bundle="${serviceliveCopyBundle}" key="name.browser.index.ff"/>';
           var chromeIndex = '<fmt:message bundle="${serviceliveCopyBundle}" key="name.browser.index.chrome"/>';
           var ieSupportedVersion = '<fmt:message bundle="${serviceliveCopyBundle}" key="browser.ie.supported.version"/>';
           var ffSupportedVersion = '<fmt:message bundle="${serviceliveCopyBundle}" key="browser.ff.supported.version"/>';
           var ieSupportedMajorVersion = '8';
           var chromeSupportedVersion = '<fmt:message bundle="${serviceliveCopyBundle}" key="browser.chrome.supported.version"/>';
           var browserText = '<fmt:message bundle="${serviceliveCopyBundle}" key="banner.browser.text"/>';
		   //isDismissedBaner is getting from session cookie.
		   if(bannercookie !='' && bannercookie == 'No'){
		   //Based on browser version we have to show diffrent divs for incompatible browsers.
		
		   //Code to detect mobile device
		   		if(isMobile())
				 {
				   document.getElementById('bannerDiv').style.display = 'none';
				 }
		   	   //Code to handle firefox compatibility
			 else if ((verOffset=nAgt.indexOf(ffIndex))!=-1) {
                        fullVersion = nAgt.substring(verOffset+8,verOffset+12);
                          if(fullVersion != ffSupportedVersion){
		                       document.getElementById('bannerDiv').style.display = 'block';
		                       document.getElementById('spanText').innerHTML = browserText;
		                       
		                       }else{
		                       document.getElementById('bannerDiv').style.display = 'none';
		                       }
		               }
		   	 /*Commenting for showing banner in every IE versions 
		            //Code to handle IE compatibility
		            else if ((verOffset=nAgt.indexOf(ieIndex))!=-1){
                         fullVersion = nAgt.substring(verOffset+5,verOffset+8);
                         positionFirstDot = fullVersion.indexOf ('.');
                         //alert("positionFirstDot is "+positionFirstDot);
                         majorVersion = fullVersion.substring(0,positionFirstDot);
                         //alert("majorversion is "+majorVersion);
                            if (majorVersion != ieSupportedMajorVersion){   
		                           document.getElementById('bannerDiv').style.display = 'block';
		                           document.getElementById('spanText').innerHTML = browserText; 
		                           
		                    }else{
		                           document.getElementById('bannerDiv').style.display = 'none';
		                     }
                        }*/
		   	   //Code to handle Chrome compatibility
					 else if ((verOffset=nAgt.indexOf(chromeIndex))!=-1) {
						 fullVersion = nAgt.substring(verOffset+7,verOffset+11);
                        if(fullVersion != chromeSupportedVersion){
		                       document.getElementById('bannerDiv').style.display = 'block';
		                       document.getElementById('spanText').innerHTML = browserText;
		                       
		                       }else{
		                       document.getElementById('bannerDiv').style.display = 'none';
		                       }
				               }
                   //Rest of the browsers
                    else{
                    	  document.getElementById('bannerDiv').style.display = 'block';
                          document.getElementById('spanText').innerHTML = browserText;	
                    
                     
                    }
            //No need to show banner if it already dismissed once
		    }else if(bannercookie !='' && bannercookie == 'Yes'){
		         document.getElementById('bannerDiv').style.display = 'none';
		      }
		      
		    
	   }
	   function removeBanner(){
		  // alert('Changing page_margins marginTop');
		  document.getElementById('bannerDiv').style.display = 'none';
	      setBannerCookie('isDismissedBanner','Yes',null);
	   }
	   
	   function isMobile(){
		    return (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino|android|ipad|playbook|silk/i.test(navigator.userAgent||navigator.vendor||window.opera)||/1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test((navigator.userAgent||navigator.vendor||window.opera).substr(0,4)))
				}
</script>
</head>
<body>
   <div id="bannerDiv" class="bannerDiv" style="display:none;">
	     <span class="spanText" id="spanText"></span>
	     <a href="javascript:void(0);"  onclick="removeBanner();" style="text-decoration: underline;"> Dismiss </a>
  </div>
</body>
</html>