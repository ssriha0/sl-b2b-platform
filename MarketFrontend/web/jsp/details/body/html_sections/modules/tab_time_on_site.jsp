<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<style type="text/css">
#yesButton,#noButton{
			background: url("${staticContextPath}/images/common/button-action-bg.png");
			border:1px solid #b1770b;
			color:#222;
			font-family:Arial,Tahoma,sans-serif;
			font-size:1.1em;
			font-weight:bold;
			padding:3px 10px;
			cursor: pointer;
			-moz-border-radius:5px 5px 5px 5px;
			margin-top:  -5px;
			text-align: center;
			width: 80px;
		 }
#departureButton{
			background: url("${staticContextPath}/images/common/button-action-bg.png");
			border:1px solid #b1770b;
			color:#222;
			font-family:Arial,Tahoma,sans-serif;
			font-size:1.1em;
			font-weight:bold;
			padding:3px 10px;
			cursor: pointer;
			-moz-border-radius:5px 5px 5px 5px;
			margin-top:  -5px;
			text-align: center;
			width: 100px;
		}
#timeOnSiteTable tbody, #timeOnSiteTable thead
{
    display: block;
}
#timeOnSiteTable th, #timeOnSiteTable td
{
    width: 172px;
}
#timeOnSiteTable tbody 
{
   overflow-y: scroll;
   height: 150px;
   width: 664px;
   position: absolute;
}
		 </style>
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>

     <script type="text/javascript">
     $(document).ready(function () 
 		{
 	  document.getElementById("AddEditPanel").style.display='none';

 	});

      jQuery("#timeonsitelink").load("soDetailsQuickLinks.action?soId=${SERVICE_ORDER_ID}");  
      $(document).ready(function () 
 		{
      <s:if test="%{errors.size > 0}">
      	document.getElementById("AddEditPanel").style.display='block';
	 	document.getElementById("saveinsert").style.display='block';
      </s:if>
      <s:else>
    	document.getElementById("AddEditPanel").style.display='none';

      </s:else>
      });
     /*  
     var errors = jQuery(".errorMsg").html();
      if(!errors || 5 === errors.length){
    	  document.getElementById("AddEditPanel").style.display='none';
      }else{
    	  document.getElementById("AddEditPanel").style.display='block';
    	  document.getElementById("saveinsert").style.display='block';
      } */

     
      function selectAddPanel(arrivalDeparture,arrivalDate,soTripNo,visitId){	          
	     
	     clearAddEditPanel();
	     if(arrivalDeparture=='Arrival'){
	    	 document.getElementById("arrivalDeparture").value = "Arrival";
	     }else if(arrivalDeparture=='Departure'){
	    	 document.getElementById("arrivalDeparture").value = "Departure";
	     }
	     document.getElementById("tripNumber").value = soTripNo;
	     document.getElementById("visitId").value = visitId;
	     document.getElementById("arrivalDate").value = arrivalDate;
	     document.getElementById("AddEditPanel").style.display='block';
	     document.getElementById("saveinsert").style.display='block';
	    
	}
	  
	
	function   clearAddEditPanel(){
		jQuery("#errorTextTimeOnsiteTab").html("");
	    document.getElementById("visitId").value = "";
	    document.getElementById("tripNumber").value = "";
	    document.getElementById("arrivalDate").value = "";
	    document.getElementById("soId").value = "";
	 	document.getElementById("roleId").value = "";
	 	document.getElementById("arrivalDepartureDate").value = "";
	 	document.getElementById("timeOnSiteReason").selectedIndex= 0;
	    document.getElementById("arrivalDepartureTimeHourString").selectedIndex= 0;
	 	document.getElementById("arrivalDepartureTimeMinutesString").selectedIndex= 0; 
	 	document.getElementById("arrivalDepartureTimeAmPm").selectedIndex= 0;
	
	}  
	function findPosDateOnSite(obj,id) 
{
	var val=obj.value;
	var curleft = curtop = 0;
	if (obj.offsetParent) { 
		curleft = obj.offsetLeft;
		curtop = obj.offsetTop;
		while (obj = obj.offsetParent) { 
			curleft += obj.offsetLeft;
			curtop += obj.offsetTop;
		}
	}
	
	var flag=1;
	var dateStringNew=new String(val);
if(dateStringNew.indexOf('/')!=-1){
	var dateParts = dateStringNew.split("/");
      var selectedMonth = parseInt(dateParts[0],10);
      var selectedDay =  parseInt(dateParts[1],10);
 	var dayVal=dateParts[1];

 var monthVal=dateParts[0];

      var yearVal=dateParts[2];
      var selectedYear = parseInt(dateParts[2],10);
if((isNaN(monthVal))||(isNaN(dayVal))||isNaN((yearVal))){
flag=-3;
}
      if((selectedMonth >12) ||( selectedMonth <1)||(selectedDay <1)||(selectedDay >31)){
		flag=0;
	}
      var day='';
      var month='';
      var year='';
      day=selectedDay;
      month=selectedMonth;
	/*if(yearVal.length()>2){
		flag=-1;

	}*/
      if((selectedYear>=28)&&(selectedYear<1000) ){
      	year='19'+yearVal;
      }else if((selectedYear<28)&&(selectedYear<1000) ){
      	year='20'+yearVal;
      }else{
year=yearVal;
}
}else{
	flag=-2;
}
//var testDate=new Date(selectedDay ,selectedMonth-1,year);
var testDate=new Date(year,selectedMonth-1,selectedDay);

 if (testDate.getDate() != day) 

{
flag=-4;


}


 var tooltip =document.getElementById(id);

tooltip.style.visibility="hidden";

if(flag!=1){
            tooltip.style.left=(curleft-50)+'px';
        
            tooltip.style.top=(curtop-300)+'px';
           	tooltip.style.visibility="visible";
if(flag==-5){
 tooltip.innerHTML='Date is out of range';
}else{
tooltip.innerHTML='* The value entered is not valid';

}
}


}
	
	function confirmTimeOnSite(){	
		if(document.getElementById("timeOnSiteReason").selectedIndex == 0){
			jQuery("#errorTextTimeOnsiteTab").html("Please select a reason.");
		}else if(document.getElementById("arrivalDepartureDate").value == ""){
				jQuery("#errorTextTimeOnsiteTab").html("Please enter the date.");
		}else if(document.getElementById("arrivalDepartureTimeHourString").selectedIndex == 0){
			jQuery("#errorTextTimeOnsiteTab").html("Please select the hour.");
		}else if(document.getElementById("arrivalDepartureTimeMinutesString").selectedIndex == 0){
			jQuery("#errorTextTimeOnsiteTab").html("Please select the minutes.");
		}else if(document.getElementById("arrivalDepartureTimeAmPm").selectedIndex == 0){
			jQuery("#errorTextTimeOnsiteTab").html("Please select the time is AM or PM.");
		}else{
			$("#errorTextTimeOnsiteTab").html("");
			if('Arrival'==document.getElementById("arrivalDeparture").value){
				jQuery("#warningText").html("Arrival at "+document.getElementById("arrivalDepartureTimeHourString").value+":"+document.getElementById("arrivalDepartureTimeMinutesString").value+" "+document.getElementById("arrivalDepartureTimeAmPm").value+" for "+document.getElementById("arrivalDepartureDate").value+"?");
			}else if('Departure'==document.getElementById("arrivalDeparture").value){
				jQuery("#warningText").html("Departure at "+document.getElementById("arrivalDepartureTimeHourString").value+":"+document.getElementById("arrivalDepartureTimeMinutesString").value+" "+document.getElementById("arrivalDepartureTimeAmPm").value+" for "+document.getElementById("arrivalDepartureDate").value+"?");
			}

			$("#timeOnSiteConfirmModal").addClass("jqmWindow");
			$("#timeOnSiteConfirmModal").css("width", "300px");
			$("#timeOnSiteConfirmModal").css("height", "auto");
			$("#timeOnSiteConfirmModal").css("border","3px solid lightgrey");
			$("#timeOnSiteConfirmModal").css("margin-left", "-155px");
			$("#timeOnSiteConfirmModal").css("margin-top", "-25px");
			$("#timeOnSiteConfirmModal").css("zIndex",1000);
			$("#timeOnSiteConfirmModal").css("background-color","#FFFFFF");
			$("#timeOnSiteConfirmModal").modal({
	                onOpen: modalOpenAddCustomer,
	                onClose: modalOnClose,
	                persist: false,
	                containerCss: ({ width: "0px", marginLeft: "-40px",border:"none"})
	            });
		}
	}

	function closeConfirmTimeOnSite(){
		
		jQuery.modal.close();
		jQuery("#fmtimeonsite").submit(); 
		
	}
	
	function revisitInfo(soId, soTripNo){
		jQuery("#revisitNeeded").load("getRivisitInfo.action?&soId="+ soId+ "&soTripNo="+ soTripNo,function(){
	     	  jQuery("#revisitNeeded").modal({
	                onOpen: modalOpenAddCustomer,
	                onClose: modalOnClose,
	                persist: false,
	                containerCss: ({ width: "0px", marginLeft: "-40px",border:"none"})
	            });  
	     		jQuery("#customerResponse").fadeOut(5000);
	         	$("#revisitNeeded").addClass("jqmWindow");
	  			$("#revisitNeeded").css("width", "680px");
	  			$("#revisitNeeded").css("height", "auto");
	  			$("#revisitNeeded").css("top", "0px");
	  			$("#revisitNeeded").css("zIndex", 1000);
	  			$("#revisitNeeded").css("background-color","#FFFFFF");
	  			//$("#releaseServiceOrder").css("cursor","wait");
	  			$("#revisitNeeded").css("border-left-color","#A8A8A8");
	  			$("#revisitNeeded").css("border-right-color","#A8A8A8");
	  			$("#revisitNeeded").css("border-bottom-color","#A8A8A8");
	  			$("#revisitNeeded").css("border-top-color","#A8A8A8");
	  			$("#revisitNeeded").jqm({modal:true});
	  			//$("#modalContainer").hide();
	  			
	  			jQuery(window).scrollTop(0);
		});
	}
	
	function closeRevisitModal(){
		jQuery('#revisitNeeded').jqmHide();
		 jQuery("#modalOverlay").fadeOut('slow'); 
		 jQuery.modal.close(); 
	}

            </script>
       <div class="soNote">
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
	
	            <jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">                  <jsp:param name="PageName" value="ServiceOrderDetails.timeOnSite"/>            </jsp:include>	<link href="${staticContextPath}/javascript/CalendarControl.css"
      rel="stylesheet" type="text/css">

 <div id="rightsidemodules" class="colRight255 clearfix" >
           
         
         
         <p id="timeonsitelink" style="color:#000;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;"><span> </span></p>
      
       
    </div>

 

 <input type="hidden" name="roleId" id="roleId" value="<s:property value="%{roleId}"/>" />  

 
<div class="contentPane">
<div style="overflow: auto;">
<jsp:include   page="validationMessages.jsp" />
</div>
	<p class="paddingBtm">
		ServiceLive provider can record their arrival and departure at service location either from the mobile app or through Interactive Voice Response (IVR). Adding this as a manual record requires you to select a reason.
		Details of trip can be viewed only when departure is recorded from mobile.
	
	</p>
	<!-- NEW MODULE/ WIDGET-->
<div class="grayTableContainer" style="height: 180px; overflow : visible">
<table class="globalTableLook" cellpadding="0" cellspacing="0" id="timeOnSiteTable">
		<thead><tr>
			<th class="col1 odd" style="width:52px">
				Trip #
			</th>
			<th class="col2 even">
				Appt Date Time
			</th>
			<th class="col3 odd">
				Arrival
			</th>
			<th class="col4 even" style="width:189px">
				Departure 
			</th>
		</tr></thead>
<tbody>
			<c:forEach  items="${resultsList}" var="row">
			<tr>
				<td class="col1 odd" style="width:52px">
					${row.tripNum}
				</td>
				<td class="col2 even">
					<c:if test="${row.apptDateTimeEntry == null}">
								&nbsp;
					</c:if>
					${row.apptDateTimeEntry}
				</td>
				<td class="col3 odd">
				    <c:if test="${row.arrivalEntry == null}">
								&nbsp;
					</c:if>
					${row.arrivalEntry}
				</td>
				<td class="col4 even">
					<c:if test="${row.addDepartureInd}">
						<c:if test="${roleid == 1}">
							<input id = "departureButton" type="button" value="Add Departure" onclick="selectAddPanel('Departure', '${row.arrivalDate}','${row.tripNum}','${row.visitId}');"/>
						</c:if>
					</c:if>
				    <c:if test="${row.departureEntry == null}">
								&nbsp;
					</c:if>
				    ${row.departureEntry} 
				    <a onclick="revisitInfo('${row.soId}','${row.tripNum}')" style="text-decoration: underline; cursor: pointer; color: #00A0D2">${row.departureReason}</a>
				</td>				
			</tr>
			</c:forEach>
</tbody>
		</table>
	</div>
	
	  <c:if test="${roleid==1}">
	
                
	
	
	<div id="AddEditDeleteButton" style="visibility: visible;   width: 645px;">
		<br/>
		<a onclick="selectAddPanel('Arrival','','','')" style="text-decoration: underline; cursor: pointer; color: #00A0D2">Did you miss Check-In from IVR/Mobile? Click here</a>
	</div>
	
</c:if>
<form action="<s:url action='soDetailsTimeOnSite_saveinsert' />"  method="POST"  theme="simple"  id="fmtimeonsite">
	  <input type="hidden" name="visitId" id="visitId" value="${timeOnSiteDTO.visitId}" />
      <input type="hidden" name="soId" id="soId" value="<s:property value="%{soId}"/>" /> 
       <input type="hidden" name="roleId" id="roleId" value="<s:property value="%{roleId}"/>" />  
       <input type="hidden" name="arrivalDeparture" id="arrivalDeparture" value="${timeOnSiteDTO.arrivalDeparture}" />  
       <input type="hidden" name="arrivalDate" id="arrivalDate" value="${timeOnSiteDTO.arrivalDate}" />  
       <input type="hidden" name="tripNumber" id="tripNumber" value="${timeOnSiteDTO.tripNumber}" />  
       
         <div id="checkTime" class="divContainerUp" style="visibility:hidden">
      		</div> 

    
      
             <div id="AddEditPanel" style="display: none;   width: 665px;">
	
         <div  class="grayModuleHdr" style="width: 635px;">
		      Add Entry
		  </div>    
	             <div class="grayModuleContent" style="position: relative; width: 633px;">
	             <span id="errorTextTimeOnsiteTab" style="color:#FF0000;"></span>
	                  <table cellpadding="0" cellspacing="0">
                        <tr>  
                        <td>
                        <p>
                        <select id="timeOnSiteReason" name="timeOnSiteReason" value="${timeOnSiteDTO.timeOnSiteReason}" style="width: 155px;">
							<option value="-1">Select a Reason</option>
							<c:forEach items="${reasonCodes}" var="option"> 
								<option value="${option}" ${option == timeOnSiteDTO.timeOnSiteReason ? 'selected' : ''}>
									${option}
								</option>
							</c:forEach> 
						</select>
                        </p>
                       <input type="hidden" id="timedatearrival" value='<s:property value="%{arrivalDepartureDate}"/>'/>
                        <input type="hidden" id="timedatedep" value='<s:property value="%{departureDate}"/>'/>
                         <input type="hidden" id="txtArrivalDate1" value=""/>
                         
                         <input type="text" 
							class="shadowBox" style="width: 90px; position: relative; background: url(${staticContextPath}/images/s_icons/calendar.png) no-repeat scroll 80px 0 transparent;"
							name="arrivalDepartureDate" 
							id="arrivalDepartureDate" readonly
							  onfocus="showCalendarControl(this,'txtArrivalDate1');"
							lang="en-us"
							onkeyup="findPosDateOnSite(this,'checkTime')" onblur="assignDate(this,'txtArrivalDate1');" value="${timeOnSiteDTO.arrivalDepartureDate}"/>
						 <script>
						  var dateString=new String(document.getElementById('timedatearrival').value);
						  if((dateString!='')&&(dateString.indexOf('-')!=-1)){
                          var dateParts = dateString.split("-");
        
        selectedYear = dateParts[0];
        selectedMonth = dateParts[1];
        selectedDay = dateParts[2];
        document.getElementById('arrivalDepartureDate').value=selectedMonth+'/'+selectedDay+'/'+selectedYear;
        }
        
        
        </script>
						 
						<p>	
							<!--  Changed s:select to html select element due to issues in struts select tag with HDIV framework -->
							<select id="arrivalDepartureTimeHourString" name="arrivalDepartureTimeHourString" value="${timeOnSiteDTO.arrivalDepartureTimeHourString}" style="width: 60px;">
							<option value="[HH]">[HH]</option>
							<c:forEach var="hrList" items="${application['hoursList']}" varStatus="inx">
								<option value="${hrList}" ${hrList == timeOnSiteDTO.arrivalDepartureTimeHourString ? 'selected' : ''}>${hrList}</option>
							</c:forEach>
							</select>
				   			:
			                <select id="arrivalDepartureTimeMinutesString" name="arrivalDepartureTimeMinutesString" value="${timeOnSiteDTO.arrivalDepartureTimeMinutesString}" style="width: 60px;">
							<option value="[MM]">[MM]</option>
							<c:forEach var="minList" items="${application['minutesList']}" varStatus="inx">
								<option value="${minList}" ${minList == timeOnSiteDTO.arrivalDepartureTimeMinutesString ? 'selected' : ''}>${minList}</option>
							</c:forEach>
							</select>
				              
			                <select id="arrivalDepartureTimeAmPm" name="arrivalDepartureTimeAmPm" value="${timeOnSiteDTO.arrivalDepartureTimeAmPm}" style="width: 45px;">
							<option value="--">--</option>
							<c:forEach var="amPmList" items="${application['ampmList']}" varStatus="inx">
								<option value="${amPmList}" ${amPmList == timeOnSiteDTO.arrivalDepartureTimeAmPm ? 'selected' : ''}>${amPmList}</option>
							</c:forEach>
							</select>
						</p>
					</td>
					<td width="400">
						<!-- <div style="width:400px; padding-left:20px;">
						<br/>
						Provider must use Mobile App ot IVR to Check-In from service location. Not doing so will affect the .................................................................................. ...................................................................................
						</div>	 -->					
					</td>
				</tr>
                <tr>
                <td>
           
          
	           <div id="saveinsert" style="display: none;" class="clearfix">
	           <input type="image" src="${staticContextPath}/images/common/spacer.gif"
							width="80" height="20"
							style="background-image: url(${staticContextPath}/images/btn/add.gif);"
							class="btn20Bevel" 
							onclick="javascript:confirmTimeOnSite(); return false;"/>
</div>
				</td>	
			</tr>
	    	</table>
	    </div>
	  
   </div>  
	<div id="timeOnSiteConfirmModal" style="display: none">
 <div style="background: none repeat scroll 0% 0% rgb(88, 88, 90); border-bottom: 2px solid black; color: rgb(255, 255, 255); text-align: left; height: 20px; padding-left: 8px; padding-top: 5px;font-size:11px;font-weight:bold" id="modalTitleBlack">
	CONFIRMATION
 </div>	
		<p style="padding: 8px;font-size:11px; text-align: center;">
		Are you sure you want to add a record of<br/>
		<strong><span id="warningText"></span></strong>
		</p>
		<table style="padding-right ;position: relative; left: 8px; padding-top:10px;padding-bottom:10px" width="100%">
			<tr>
				<td width="67%">
					<input id = "noButton" type="button" class="simplemodal-close" value="Cancel"/>
					<br/>
				</td>
				<td width="33%">
					<s:submit type="input"
					method="saveinsert"
					src="%{#request['staticContextPath']}/images/common/spacer.gif"
					id="yesButton"
					cssClass="btn20Bevel" 
					theme="simple" value="Yes" onclick="closeConfirmTimeOnSite();"/>
				</td>
			</tr>
		</table>
	</div>
  </form>
  
 <div id="revisitNeeded"
			style="display: none">
</div>
   
</div>
</div>
