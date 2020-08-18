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

<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ServiceLive - Company Zipcode Coverage</title>

 <link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/admin.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css" href="/ServiceLiveWebUtil/styles/plugins/public.css" 
			media="screen, projection">
		
	
		<script language="JavaScript"
			src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/animatedcollapse.js">	 
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/hideShow.js"></script>
		
		<script type="text/javascript">
			jQuery.noConflict(); 
		</script>
		
	<style>
	
	 #iframe-map{
    height:0%;
    width:0%;
  }
.tooltip {
  position: relative;
  display: inline-block;
  font-size: 23px;
  font-weight: 900;
}

.tooltip .tooltiptext {
  visibility: hidden;
  width: 300px;
  background-color: #fff;
  color: #555;
  font-size: 10px;
  font-weight: 300;
   text-align: center;
  border-radius: 6px;
  border-style: groove;
  padding: 5px 0;
  position: absolute;
  z-index: 1;
  bottom: 125%;
  left: 50%;
  margin-left: -140px;
  opacity: 0;
  transition: opacity 0.3s;
}

.tooltip .tooltiptext::after {
  content: "";
  position: absolute;
  top: 100%;
  left: 50%;
  margin-left: -5px;
  border-width: 5px;
  border-style: solid;
  border-color: #555 transparent transparent transparent;
}

.tooltip:hover .tooltiptext {
  visibility: visible;
  opacity: 1;
}
</style>	

</head>


<body class="tundra">
<div id="page_margins">
			<div id="page">
				<table width="100%" border=0 cellspacing=0 cellpaddig=0>
					<tr>
						<td colspan=2>

							<!-- START HEADER -->
				<div id="headerShort" >
					
						<tiles:insertDefinition name="newco.base.topnav" />
						<tiles:insertDefinition name="newco.base.blue_nav" />
					
						<tiles:insertDefinition name="newco.base.dark_gray_nav" />
					</div>			
					<div id="zipCoverageDiv" class="content padd">
						<form id="zipcoverageForm" action="orderManagementControllerAction">
						
<table  cellpadding="4px" cellspacing="0px" border="0" width="100%" >
<tr>

<td style="padding-left: 10px ;">
<table cellpadding="4px" cellspacing="0px" border="0" width="100%">

	<thead>				
		<td width="500px" colspan="2" >	
			<div class="tooltip">State Coverage Filter
			  <span class="tooltiptext">State Filters allow you to see zip code coverage for your Approved Market Ready Providers.</span>
			</div>
		</td>
		<td width="500px" colspan="2">		
			<div class="tooltip">SPN Coverage Filter
			  <span class="tooltiptext">SPN filters allow you to see zip code coverage for a specific Buyer SPN Approved Providers.</span>
			</div>
		</td>
	</thead>
	<tr>
	<td colspan="2">&nbsp;</td>
	</tr>
	<tr>				
		<td width="150px">		
			<p class="paddingBtm">
				<font size="2"><b>State</b></font>
			</p>
		</td>
		<td width="20px">
			<p class="paddingBtm">
				<select name="stateId"
					id="stateId" class="dropdown-80" disabled onchange="loadFilter('state','stateCode', stateId); showZipcodesByFilter('stateCode', stateId)">
					<option value="-1">--Select--</option>
					<c:forEach var="states" items="${zipCoverageFiltersDTO.stateNameList}" varStatus="state">
						<option value="${states.stateCode}">
							${states.stateName}
						</option>
					</c:forEach>	
				</select>
			</p>
		</td>
		<td width="150px">		
			<p class="paddingBtm">
				<font size="2"><b>Buyer SPN</b></font>
			</p>
		</td>
		<td width="20px">
			<p class="paddingBtm">
				<select name="buyerSpnId"
					id="buyerSpnId" class="dropdown-80" disabled onchange="loadBuyerSpnFilterOnchange('spn', buyerSpnId); showZipcodesByFilter('buyerSpnId', buyerSpnId)">
					<option value="-1">--Select--</option>
					<c:forEach var="buyerSpn" items="${zipCoverageFiltersDTO.spnList}" varStatus="spn">
						<option value="${buyerSpn.spnId}">
							${buyerSpn.spnName}
						</option>
					</c:forEach>	
				</select>
			</p>
		</td>
	</tr>
	<tr>				
		<td width="150px">		
			<p class="paddingBtm">
				<font size="2"><b>Zip Code</b></font>
			</p>
		</td>
		<td width="20px">
			<p class="paddingBtm">
				<select name="zipId"
					id="zipId" class="dropdown-80" disabled onchange="loadFilter('zip','stateCode', stateId,'zipCode', zipId);showZipcodesByFilter('stateCode', stateId, 'zipCode', zipId)">
					<option value="-1">--Select--</option>
					<c:forEach var="zips" items="${zipCoverageFiltersDTO.zipList}" varStatus="zip">
						<option value="${zips.zipCode}">
							${zips.zipCode}
						</option>
					</c:forEach>	
				</select>
			</p>
		</td>
		<td width="150px">		
			<p class="paddingBtm">
				<font size="2"><b>Buyer SPN Approved Pro(s)</b></font>
			</p>
		</td>
		<td width="20px">
			<p class="paddingBtm">
				<select name="spnProviderId"
					id="spnProviderId" class="dropdown-80" disabled onchange="loadBuyerSpnFilterOnchange('spnProvider', buyerSpnId, spnProviderId); showZipcodesByFilter('buyerSpnId', buyerSpnId, 'buyerSpnProvId', spnProviderId)">
					<option value="-1">--Select--</option>
					<c:forEach var="spnProviders" items="${zipCoverageFiltersDTO.spnProvidersList}" varStatus="spnProvider">
						<option value="${spnProviders.providerId}">
							${spnProviders.providerId} - ${spnProviders.providerName}
						</option>
					</c:forEach>	
				</select>
			</p>
		</td>
	</tr>	
	<tr>				
		<td width="150px">		
			<p class="paddingBtm">
				<font size="2"><b>Approved Marketready Service Pro(s)</b></font>
			</p>
		</td>
		<td width="20px">
			<p class="paddingBtm">
				<select name="providerId"
					id="providerId" class="dropdown-80" disabled onchange="showZipcodesByFilter('stateCode', stateId, 'zipCode', zipId, 'resourceId', providerId)">
					<option value="-1">--Select--</option>
					<c:forEach var="providers" items="${zipCoverageFiltersDTO.providersList}" varStatus="provider">
						<option value="${providers.providerId}">
							 ${providers.providerName} (${providers.providerId})
						</option>
					</c:forEach>	
				</select>
			</p>
		</td>
		<td width="150px">		
			<p class="paddingBtm">
				<font size="2"><b>State</b></font>
			</p>
		</td>
		<td width="20px">
			<p class="paddingBtm">
				<select name="spnStateId"
					id="spnStateId" class="dropdown-80" disabled onchange="loadBuyerSpnFilterOnchange('spnState', buyerSpnId, spnProviderId, spnStateId); showZipcodesByFilter('buyerSpnId', buyerSpnId, 'buyerSpnProvId', spnProviderId, 'spnState', spnStateId)">
					<option value="-1">--Select--</option>
					<c:forEach var="spnProviders" items="${zipCoverageFiltersDTO.spnProvidersList}" varStatus="spnProvider">
						<option value="${spnProviders.providerId}">
							${spnProviders.providerId} - ${spnProviders.providerName}
						</option>
					</c:forEach>	
				</select>
			</p>
		</td>
	</tr>	
	<tr>				
		<td width="150px">		
			
		</td>
		<td width="20px">
			
		</td>
		<td width="150px">		
			<p class="paddingBtm">
				<font size="2"><b>Zip Code</b></font>
			</p>
		</td>
		<td width="20px">
			<p class="paddingBtm">
				<select name="spnZipCode"
					id="spnZipCode" class="dropdown-80" disabled onchange="showZipcodesByFilter('buyerSpnId', buyerSpnId, 'buyerSpnProvId', spnProviderId, 'spnState', spnStateId, 'spnZip', spnZipCode)">
					<option value="-1">--Select--</option>
					<c:forEach var="spnProviders" items="${zipCoverageFiltersDTO.spnProvidersList}" varStatus="spnProvider">
						<option value="${spnProviders.providerId}">
							${spnProviders.providerId} - ${spnProviders.providerName}
						</option>
					</c:forEach>	
				</select>
			</p>
		</td>
	</tr>	
	
	<tr class="black" >
		<td colspan="4" id="iframetd">
			<iframe id="iframe-map"
		
			src="${zipCoverageFiltersDTO.mapboxUrl}?defaultView=true"	frameborder="0"
			style="overflow: hidden"></iframe>
		</td>
		
	</tr>
	<tr>
	<td>&nbsp;</td>
	</tr>

	<tr>
		<td colspan="4" >

		
						
						
		<font size="4"><b>Frequently asked questions and answers</b></font>
			 
			 <br/>	<br/>
			
			<c:forEach items="${zipCoverageFiltersDTO.faqList}" var="faq" varStatus="faqIndex">
			
					<img id="img_${faqIndex.count}" name="img_${faqIndex.count}"
					src="${staticContextPath}/images/icons/plusIcon.gif" 
					alt="Expand" onclick="collapseExpand(${faqIndex.count -1})" />
					<b>${faq.question}</b>
					<div id="div_${faqIndex.count}" style="height: auto">
						${faq.answer}<br/><br/>
					</div>
					
				</c:forEach>	
							
	
</td>
	</tr>
	
	
	
</table>
</td>
</tr>

</table>



	<!-- <div id="providerMap" class="widget clearfix">
				<h2>
					Coverage Area
				</h2> -->
	<!-- google maps this -->
	<!-- 	<div id="drawmaps"
					style="width: 269px; height: 217px; margin-bottom: 5px; border: 1px solid silver;">
					<iframe width="269px" scrolling="no" height="217px" marginwidth="0"
						marginheight="0" frameborder="0"
						src="https://master.d1oe7ll96r4kpf.amplifyapp.com/?demo=true&selector=true&radius=${radius}&zipcode=${zipcode}"
						style="overflow:hidden;height:600px;width:600px;margin-left:300px;">
					</iframe>
				</div>
				</div> -->
	

<script type="text/javascript">

	 var collapsableDivsArray = new Array();
	 for(i=0;i<${fn:length(zipCoverageFiltersDTO.faqList)};i++){
		collapsableDivsArray[i] = new animatedcollapse('div_' +(i+1), 250, false);
	 }
	 

	function collapseExpand(divIndex){
		var divId = divIndex + 1;
		divObject = document.getElementById('div_'+ divId);
		collapsableDivsArray[divIndex].slideit();
		
		if(divObject.style.height=="0px"){
			
			document.getElementById("img_" + (divIndex + 1)).src = "${staticContextPath}/images/icons/minusIcon.gif";
			
		}else {
			document.getElementById("img_" + (divIndex + 1)).src = "${staticContextPath}/images/icons/plusIcon.gif";
		}
		return;
	}

   
	
	window.addEventListener('message', function (e) {
		if (e && e.data && e.data.isMapReady) {
			document.getElementById('stateId').disabled = !e.data.isMapReady;
			document.getElementById('zipId').disabled = !e.data.isMapReady;
			document.getElementById('providerId').disabled = !e.data.isMapReady;
			document.getElementById('buyerSpnId').disabled = !e.data.isMapReady;
			document.getElementById('spnProviderId').disabled = !e.data.isMapReady;
			document.getElementById('spnStateId').disabled = !e.data.isMapReady;
			document.getElementById('spnZipCode').disabled = !e.data.isMapReady;
			//alert(document.getElementById('iframe-map').src);
		}
	}, false);
		
    function showZipcodesByFilter(filter1, filterValue1,filter2, filterValue2,filter3, filterValue3, filter4, filterValue4){
		var paramValue1=null;
		var paramValue2=null;
		var paramValue3=null;
		var paramValue4=null;
		//alert(${zipCoverageFiltersDTO.mapboxUrl});
		var mapUrl='${zipCoverageFiltersDTO.mapboxUrl}';
		//alert(mapUrl);
		//var black = jQuery('.black');
		//black.show();
		
		jQuery('#iframetd').css('height','100px');
		jQuery('#iframe-map').css('width','100%');
        jQuery('#iframe-map').css('height','450%');
		if(filterValue1!=null && filterValue1.value!=-1){
			paramValue1=filterValue1.value;
		}
		if(filterValue2!=null && filterValue2.value!=-1){
			paramValue2=filterValue2.value;
		}
		if(filterValue3!=null && filterValue3.value!=-1){
			paramValue3=filterValue3.value;
		}
		if(filterValue4!=null && filterValue4.value!=-1){
			paramValue4=filterValue4.value;
		}
    	//alert(paramValue1);
		//alert(paramValue2);
		//alert(paramValue3);
		jQuery.ajax({
            type: "GET",
            url:"zipcoverage_getSelectedZipCodeValues.action?filter1="+filter1+"&filterValue1="+paramValue1+"&filter2="+filter2+"&filterValue2="+paramValue2+"&filter3="+filter3+"&filterValue3="+paramValue3+"&filter4="+filter4+"&filterValue4="+paramValue4,
            dataType: "json",
            success: function (data) {
				data=data.replace(/ 0+(?![\. }])/g, '');
//alert(data);
//alert(data);
//data=data.substring(12,data.length-1);;
//alert(data);
//alert(data[2]);
				var json=JSON.parse(data);
				//alert(json.length);
				
				//alert(json[0])
				//alert(json);
				//alert(json);
				//var jsonObj=JSON.parse(json);
				//document.getElementById('iframe-map').src = 'https://master.d1vjle3jjsdduw.amplifyapp.com/?zipcode='+json.zipcodes[0]+'&viewonly=true';
				//setTimeout(function(){
				//jQuery("#iframe-map").ready(function () {
				 //alert('ready');
				// var iframeWin = document.getElementById("iframe-map").contentWindow;
				//iframeWin.postMessage(json, 'https://master.d1vjle3jjsdduw.amplifyapp.com');	
			// }); }, 2000);
			var zoomValue=null;
			var actionMap='zipcodes-with-center';
			if(json.length>50){
				actionMap='state-and-zipcodes';
				zoomValue=8;
			}
			else if(json.length>15 && json.length<=50){
				actionMap='zipcodes-with-center';
				zoomValue=8;
			}else{
				actionMap='zipcodes-with-center';
				zoomValue=10;
			}
			const state = document.getElementById('stateId').value;
			const zipcode = document.getElementById('zipId').value;
			const spn = document.getElementById('buyerSpnId').value;
			const iframeWin = document.getElementById('iframe-map');
			if(state!=null && state!=-1){
				//alert(state);
				//alert(zipcode);
				var servicePro=document.getElementById('providerId').value;
				//alert(servicePro);
				//alert(json.length);
				if((zipcode==null || zipcode==-1) && (servicePro==null || servicePro==-1) && json.length>50){
					actionMap='state-and-zipcodes';
					zoomValue=8;
				}
				else{
					actionMap='zipcodes-with-center';
				}
				//else{
					if(actionMap=='zipcodes-with-center'){
						iframeWin.contentWindow.postMessage(
							{
								mapAction: actionMap, 
								centerZipcode: json[0],
								zoomLevel: zoomValue,						
								zipcodes: json 
							}, mapUrl
						);
					}else{
						 iframeWin.contentWindow.postMessage(
							{
								mapAction: actionMap, 
								state: state, 
								zoomLevel: zoomValue,	
								zipcodes: json 
							}, mapUrl
						);
					}
				//}
			}
			if(spn!=null && spn!=-1){
				var spnZip=document.getElementById('spnZipCode').value;
				if(spnZip!=null && spnZip!=-1){
					iframeWin.contentWindow.postMessage(
						{
							mapAction: 'zipcodes-with-center', 
							centerZipcode: json[0], 
							zipcodes: json,
							zoomLevel: 10
						}, mapUrl
					);
				}
				else{
					iframeWin.contentWindow.postMessage(
						{
							mapAction: 'zipcodes-with-center', 
							centerZipcode: json[0], 
							zipcodes: json 
						}, mapUrl
					);
				}
			}
			}
        });
	
		//var xhttp = new XMLHttpRequest();
		// xhttp.open("GET", //"zipcoverage_getSelectedZipCodeValues.action?filter1="+filter1+"&filterValue1="+paramValue1+"&filter2="+filter2+"&filterValue2="+paramValue2+"&filter3="+filter3+"&filterValue3="+paramValue3+"&filter4="+filter4+"&filterValue4="+paramValue4, true);
		//xhttp.send();
		
		
	  //xhttp.onreadystatechange = function() {
		//if (this.readyState == 4 && this.status == 200) {
			
			//var data=xhttp.responseText;
			//data=xhttp.responseText.replace(/ 0+(?![\. }])/g, '');
			//var json=JSON.parse(data);
			//alert(json);
			//var jsonObj=JSON.parse(json);
			
			//document.getElementById('iframe-map').src = 'https://master.d1oe7ll96r4kpf.amplifyapp.com/?zipcode='+jsonObj.zipcodes[0]+'&viewonly=true';
			//alert(jsonObj);
			//setTimeout(function(){
			//jQuery("#iframe-map").ready(function () {
				 //alert('ready');
			//	 var iframeWin = document.getElementById("iframe-map").contentWindow;
				//iframeWin.postMessage(jsonObj, 'https://master.d1oe7ll96r4kpf.amplifyapp.com');	
			// }); 
			//  }, 3000);	
			
			
			
			
		//}
	 // };
		
    }
	function loadFilter(changedFilter, filter1, filterValue1,filter2, filterValue2){
		//alert(changedFilter);
		var paramValue1=null;
		var paramValue2=null;
	
		if(filterValue1!=null && filterValue1.value!=-1){
			paramValue1=filterValue1.value;
		}
		if(filterValue2!=null && filterValue2.value!=-1){
			paramValue2=filterValue2.value;
		}
		var defValue="<option value=-1>--Select--</option>";
		//alert(paramValue1);
		jQuery.ajax({
            type: "GET",
            url:"zipcoverage_loadFiltersWithMetaData.action?changedFilter="+changedFilter+"&filterValue1="+paramValue1+"&filterValue2="+paramValue2,
            dataType: "json",
            success: function (data) {
				   jQuery("#providerId").empty();
				   jQuery(defValue).appendTo('#providerId');
				  if(changedFilter=='state'){
					 jQuery("#zipId").empty();
					   jQuery(defValue).appendTo('#zipId');
					 // alert('zip filter');
						jQuery.each(data.zipList,function(i,obj)
						{
						 //alert(obj.zipCode);
						 var div_data="<option value="+obj.zipCode+">"+obj.zipCode+"</option>";
					   // alert(div_data);
						jQuery(div_data).appendTo('#zipId'); 
						});  
				  }
                
				jQuery.each(data.providersList,function(i,obj)
                {
                 //alert(obj.providerId);
                  var div_data="<option value="+obj.providerId+">"+obj.providerName+" ("+obj.providerId+")"+"</option>";
               // alert(div_data);
               jQuery(div_data).appendTo('#providerId'); 
                });  
				
                }
          });
        jQuery("#buyerSpnId").val("-1");
		jQuery("#spnProviderId").empty();
		jQuery(defValue).appendTo('#spnProviderId');
		jQuery("#spnStateId").empty();
		jQuery(defValue).appendTo('#spnStateId');
		jQuery("#spnZipCode").empty();
		jQuery(defValue).appendTo('#spnZipCode');
		
	}
	
	function loadBuyerSpnFilterOnchange(changedFilter, filterValue1, filterValue2, filterValue3){
		var paramValue1=null;
		var paramValue2=null;
		var paramValue3=null;
		if(filterValue1!=null && filterValue1.value!=-1){
			paramValue1=filterValue1.value;
		}
		if(filterValue2!=null && filterValue2.value!=-1){
			paramValue2=filterValue2.value;
		}
		if(filterValue3!=null && filterValue3.value!=-1){
			paramValue3=filterValue3.value;
		}
		var defValue="<option value=-1>--Select--</option>";
		jQuery.ajax({
            type: "GET",
            url:"zipcoverage_loadSpnFiltersWithMetaData.action?changedFilter="+changedFilter+"&filterValue1="+paramValue1+"&filterValue2="+paramValue2+"&filterValue3="+paramValue3,
            dataType: "json",
            success: function (data) {
				
					if(changedFilter=='spn'){
					   jQuery("#spnProviderId").empty();
					   jQuery(defValue).appendTo('#spnProviderId');
						jQuery.each(data.spnProvidersList,function(i,obj)
						{
							var div_data="<option value="+obj.providerId+">"+obj.providerName+" ("+obj.providerId+")"+"</option>";
							jQuery(div_data).appendTo('#spnProviderId'); 
						});  
					}
					
					if(changedFilter=='spn' || changedFilter=='spnProvider'){
						jQuery("#spnStateId").empty();
						jQuery(defValue).appendTo('#spnStateId');
						jQuery.each(data.spnStateNameList,function(i,obj)
						{
							var div_data="<option value="+obj.stateCode+">"+obj.stateName+"</option>";
							jQuery(div_data).appendTo('#spnStateId'); 
						});  
					}
					
					if(changedFilter=='spn' || changedFilter=='spnProvider' ||  changedFilter=='spnState'){
						jQuery("#spnZipCode").empty();
						jQuery(defValue).appendTo('#spnZipCode');
						jQuery.each(data.spnZipList,function(i,obj)
						{
							var div_data="<option value="+obj.zipCode+">"+obj.zipCode+"</option>";
							jQuery(div_data).appendTo('#spnZipCode'); 
						});  
					}
				
                }
          });
         
		if(jQuery("#stateId option[value='-1']").length == 0)
			jQuery('#stateId').prepend(jQuery(defValue)); 	
		jQuery("#stateId").val("-1");
		jQuery("#zipId").empty();
		jQuery("#providerId").empty();
		jQuery(defValue).appendTo('#providerId');
		jQuery(defValue).appendTo('#zipId');
		
	}
	
	
		
</script>
</form>
					</div>
				
				</td>
					</tr>
				</table>
			</div>
		</div>
	<s:include value="/jsp/public/common/omitInclude.jsp">
			 <s:param name="PageName" value="common.dashboard"/>
		</s:include>	
		<div id="explainer"></div>
</body>
</html>