<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ServiceLive[Service Pro Profile]</title>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
  
</head>

<body>
	<br>
	<table cellpadding="4px" cellspacing="0px" border="1" width="100%">
		<tr border="1">
			<td align="center" bgcolor="#06d32f">
			<b><font size="4">Zip Code Coverage Validation</font></b>
			</td>
				
		</tr>
		<tr>
			<td>
			<b><font size="4">The zip code coverage displayed on this map is based on the Dispatch <b>Zip Code and Geographical Range</b> selected for this Team Member.</font></b>
			<br/>
			<ul>
			<li>Please verify that the Team Member&apos;s zip code coverage is accurate.<b> They will only receive work for the zip codes selected.</b></li>
			<li>Click to deselect the zip code(s) you do not wish to cover.</li>
			<li>Click to select any additional zip code(s) you wish to cover.</li>
			<li>Click <q><b>Save Changes</b></q> when done.</li>
			<li>Upon clicking <q>Save Changes</q>, this window will close. Remember to click <b><q>Save</q></b> at the bottom of General information Tab to ensure all information you entered on the page is retained.</li></ul>
			</td>
		</tr>
	</table>
   

	 <iframe id="iframe-map"
		src="<%=request.getParameter("mapUrl")%>?zipcode=<%=request.getParameter("zipcode")%>&radius=<%=request.getParameter("radius")%>&domain=<%=request.getScheme()%>://<%=request.getServerName()%>:<%=request.getServerPort()%>"	frameborder="0"
		style="overflow: hidden; height: 1000px; width: 1000px; align: center" height="100%"
		width="200%"></iframe> 
		<!-- <iframe id="iframe-map"
		src='about:blank'	frameborder="0"
		style="overflow: hidden; height: 1000px; width: 1000px" height="100%"
		width="100%"></iframe> -->

<script>
	//var mapUrl=window.opener.document.getElementsByName("generalInfoDto.mapboxUrl")[0].value;

    window.addEventListener('message', function(event) {
		
        console.log("DEMO page outside event: ", event.data);
      //  alert("DEMO page outside event: ");
       // alert(event.data);
	   
        var zipcodeValues=JSON.stringify(event.data);
		//alert(zipcodeValues);
		 if (window.opener != null && !window.opener.closed) {
           /* var txtName = window.opener.document.getElementById("txtName");
            txtName.value = zipcodeValues;*/
            //window.opener.document.getElementsByName("generalInfoDto.zipcodesCovered")[0].value=zipcodeValues;
            window.opener.document.getElementsByName("zipcodes")[0].value=zipcodeValues;
			window.close();
        }
        // IMPORTANT: Check the origin of the data!
        if (~event.origin.indexOf(window.opener.document.getElementsByName("generalInfoDto.mapboxUrl")[0].value)) {
            // The data sent with postMessage is stored in event.data
            console.log("DEMO page inside event: " +  event.data);
           // alert("DEMO page inside event: ");
           // alert(event.data);
        } else {
            // The data hasn't been sent from your site! Be careful! Do not use it.
            // return;
        }
    });

    window.onload = function () {
		
		//alert(window.opener.document.getElementsByName("generalInfoDto.mapboxUrl")[0].value);
		var data=window.opener.document.getElementsByName("zipcodes")[0].value;
		
		$("#iframe-map").ready(function () { 
		if(null!=data){
			//alert(data.value);
			var jsonData=JSON.parse(data);
			//alert(jsonData);
			//var postedZipCode=JSON.stringify(data);
			var iframeWin = document.getElementById("iframe-map").contentWindow;
			//var data = { zipcodes: ['60657','60613','60614','60005'] };
			iframeWin.postMessage(jsonData, window.opener.document.getElementsByName("generalInfoDto.mapboxUrl")[0].value);
		}
		});
		};   
		
		
</script>

</body>
</html>