/*
1.	Enabling Secondary Questions: All primary questions are by default empty and secondary 
questions are grayed out. For Secondary questions that depend on the previous questions, the 
radio button selection should be grayed until the ?Yes? on the Primary question radio button 
is selected. All questions are required fields on this page. 
*/

function enableBackgroundCheck(plusOneUrl,resourceId,encryptedPlusOneKey){
	var url="'"+ plusOneUrl +resourceId+"&parm3="+encryptedPlusOneKey+"&parm4="+"Y"+"'";
	window.open(eval(url),'mywindow','width=800,height=800,toolbar=yes, location=yes,directories=yes,status=yes,menubar=yes,scrollbars=yes,copyhistory=yes, resizable=yes');
}


function enableBackgroundCheckFn(plusOneUrl,resourceId,encryptedPlusOneKey){
	var url="'"+ plusOneUrl +resourceId+"&parm3="+encryptedPlusOneKey+"&parm4="+"N"+"'";
	window.open(eval(url),'mywindow','width=800,height=800,toolbar=yes, location=yes,directories=yes,status=yes,menubar=yes,scrollbars=yes,copyhistory=yes, resizable=yes');
}

function setBackgroundConfirmInd(){
	document.getElementsByName('backgroundConfirmIndicator')[0].value="1";
}

function enableSelectByName(radioVal, name) {//Do you offer a warranty on labor? Logic

	if(radioVal.value == 1){	
		document.getElementsByName(name)[0].disabled=false;		
	}else{
		//document.getElementsByName(name)[0]
		document.getElementsByName(name)[0].value="";//clear value
		document.getElementsByName(name)[0].disabled=true;			
	}
}

function showHideStar(radioVal, name){  
	if(radioVal.value == 1){		
		document.getElementById(name).style.display = ""; 
	}else{		
		document.getElementById(name).style.display = 'none';				
	}	
}

function hideShowStar(radioVal, name){  
	if(radioVal.value != 1){		
		document.getElementById(name).style.display = ""; 
	}else{		
		document.getElementById(name).style.display = 'none';				
	}	
}

function setSSN(){
		
		var ssnLeft = document.getElementsByName("generalInfoDto.ssnLeft")[0];
		var ssnMiddle = document.getElementsByName("generalInfoDto.ssnMiddle")[0];
		var ssnRight = document.getElementsByName("generalInfoDto.ssnRight")[0];
		var ssn = document.getElementsByName("generalInfoDto.ssn")[0];
		var ssnValInd = document.getElementsByName("generalInfoDto.ssnValInd")[0];
		
		ssnValInd.value="1";
		ssn.value=ssnLeft.value+ssnMiddle.value+ssnRight.value;
}

function enableRadioButtonByName(radioVal, name){//generic function
	if(radioVal.value == 1){				
		document.getElementsByName(name)[0].disabled=false;
		document.getElementsByName(name)[1].disabled=false;
	}else{
		document.getElementsByName(name)[0].value="";//clear value
		document.getElementsByName(name)[1].value="";		
		document.getElementsByName(name)[0].disabled=true;
		document.getElementsByName(name)[1].disabled=true;
	}	
}
function enableWRadioButtonByName(radioVal, name){//generic function
	if(radioVal.value == 0){				
		document.getElementsByName(name)[0].disabled=false;
		document.getElementsByName(name)[1].disabled=false;
	}else{
		document.getElementsByName(name)[0].value="";//clear value
		document.getElementsByName(name)[1].value="";		
		document.getElementsByName(name)[0].disabled=true;
		document.getElementsByName(name)[1].disabled=true;
	}	
}

function updateScheduleCombo(radioVal){

	var dtoName = 'generalInfoDto.';
	var radiobtnname = radioVal.name;
	//var datename = radiobtnname.substring(15,18)
	//var dateEnd = radiobtnname.substring(18,21);
	//alert(datename + '  ' + dateEnd);
	var dateFromName = dtoName+radiobtnname+'Start';
	var dateToName = dtoName+radiobtnname+'End';
	
	var radiobtn = document.getElementsByName(radiobtnname);
	var radioValue =  '0';
	for (var i=0; i < radiobtn.length; i++) {
		if (radiobtn[i].checked == true) {
			radioValue = radiobtn[i].value;
		}
	}
	
	if (radioValue == '24') {
		document.getElementsByName(dtoName+radiobtnname+'NaInd')[0].value='0';
		document.getElementsByName(dtoName+radiobtnname+'24Ind')[0].value='1';
		document.getElementsByName(dateFromName)[0].disabled=true;
		document.getElementsByName(dateToName)[0].disabled=true;
	} else if (radioValue == 'na') {
		document.getElementsByName(dtoName+radiobtnname+'NaInd')[0].value='1';
		document.getElementsByName(dtoName+radiobtnname+'24Ind')[0].value='0';
		document.getElementsByName(dateFromName)[0].disabled=true;
		document.getElementsByName(dateToName)[0].disabled=true;
	} else {
		document.getElementsByName(dtoName+radiobtnname+'NaInd')[0].value='0';
		document.getElementsByName(dtoName+radiobtnname+'24Ind')[0].value='0';
		document.getElementsByName(dateFromName)[0].disabled=false;
		document.getElementsByName(dateToName)[0].disabled=false;
		document.getElementsByName(dateFromName)[0].value='09:00 AM';
		document.getElementsByName(dateToName)[0].value='05:00 PM';
	}	
}

function setIndicator(){
if (document.getElementsByName('sun')[0].checked==true){
  document.getElementsByName('generalInfoDto.sun24Ind')[0].value='1';
} 
if(document.getElementsByName('mon')[0].checked==true){
  document.getElementsByName('generalInfoDto.mon24Ind')[0].value='1';
} 
if(document.getElementsByName('tue')[0].checked==true){
  document.getElementsByName('generalInfoDto.tue24Ind')[0].value='1';
} 
if(document.getElementsByName('wed')[0].checked==true){
  document.getElementsByName('generalInfoDto.wed24Ind')[0].value='1';
}
if(document.getElementsByName('thu')[0].checked==true){
  document.getElementsByName('generalInfoDto.thu24Ind')[0].value='1';
} 
if(document.getElementsByName('fri')[0].checked==true){
  document.getElementsByName('generalInfoDto.fri24Ind')[0].value='1';
}
if(document.getElementsByName('sat')[0].checked==true){
  document.getElementsByName('generalInfoDto.sat24Ind')[0].value='1';
}

}

/*
* Perform nesseccery page initializations when jsp page is loaded
*/
function load(){

}


function popupJSP(urlToOpen)
{	
	var window_width = screen.availWidth/2;
	var window_height = screen.availHeight;
	var window_left = 5;
	var window_top = 5;
	var winParms = "Status=yes" + ",resizable=yes" + ",height="+window_height+",width="+window_width + ",left="+window_left+",top="+window_top;
	var newwindow = window.open(urlToOpen,'_blank',winParms);
	newwindow.focus()
}	

function performBusForeignOwned(radioVal, name) {
	if(radioVal.value == 1){				
		document.getElementsByName(name)[0].disabled=false;
	}else{
		document.getElementsByName(name)[0].value = "";
		document.getElementsByName(name)[0].disabled=true;	
	}
}	
function enableProviderListForAdminChange(obj){
	if(obj.checked)		
		document.getElementById('providerListForAdminChange').disabled=false
	else if(!obj.checked){
	   document.getElementById('providerListForAdminChange').disabled=true
	}
}
function changeResourseIdForAdminChange(Object1){
	var str=Object1.value;
	document.getElementById('newAdminName').value=str;
	document.getElementById('newAdminResourceId').value = str.substring(str.lastIndexOf("(")+1,str.lastIndexOf(")"));
}


function enableBuyerListForAdminChange(obj){
	if(obj.checked)		
		document.getElementById('buyerListForAdminChange').disabled=false
		
	else if(!obj.checked){
	   document.getElementById('buyerListForAdminChange').disabled=true
	}
}

function fetchNewBuyerName(Object1){
	
	var options = document.getElementById("buyerListForAdminChange").options; 
	            var selectedIndex = document.getElementById("buyerListForAdminChange").selectedIndex; 
	            var str = options[selectedIndex].text;
	            var selectedOptionValue = options[selectedIndex].value;
	         


		document.getElementById('newAdminFirstName').value=str.substring(0,str.lastIndexOf(","));
		document.getElementById('newAdminLastName').value=str.substring(str.lastIndexOf(",")+1,str.lastIndexOf("("));
		document.getElementById('newAdminResourceId').value = str.substring(str.lastIndexOf("(")+1,str.lastIndexOf(")"));
	
	}

function setAction(action,dto){
	jQuery("#"+dto+"Action").val(action);
}


//java script called in tab_licenses_list.jsp page for 'Next' and 'Previous' button

function assignAction(name){
if(name=='Next')	
{	
document.forms['licensesAndCerts'].action="licensesAndCertActiondoNextLicenses.action";
}
else if(name=='Prev')
{
	document.forms['licensesAndCerts'].action="licensesAndCertActiondoPrevLicenses.action";
}	
}

/*SLT-1475: Zipcode coverage*/
function openCoverageWindow(cp,url,urlRadius,zipcode,radius,mapurl){
	var newRadius;
	var dispZipcode = document.getElementsByName(zipcode);
	var mapboxUrl=document.getElementsByName(mapurl);
	var newzip=(dispZipcode)[0].value;
	var rad = document.getElementsByName(radius);
	var convertedRadius=(rad)[0].value;
	if(convertedRadius==1)
		convertedRadius=10;
	else if(convertedRadius==2)
		convertedRadius=25;
	else if(convertedRadius==3)
		convertedRadius=50;
	else if(convertedRadius==4)
		convertedRadius=100;
	else if(convertedRadius==5)
		convertedRadius=200;
	var finalurl=cp+url+newzip+urlRadius+convertedRadius+"&mapUrl="+mapboxUrl[0].value;
	var window_left = 5;
	var window_top = 5;
	var winParms = "Status=yes" + ",resizable=yes" + ",height="+640+",width="+1040 + ",left="+window_left+",top="+window_top;
	var newwindow = window.open(finalurl,'ServiceLive[Service Pro Profile]',winParms);
	newwindow.focus();
}

function setSelectedZipCodes(textbox){
	//alert(textbox.defaultValue);
	//alert(textbox.value);
	if(textbox.value!=textbox.oldvalue){
		document.getElementsByName("zipcodes")[0].value="";
	var zipcodeLabel = document.getElementById("msg");
	zipcodeLabel.innerHTML = "Please open the map to confirm the new coverage zipcodes.";
				zipcodeLabel.style.display = 'block';
	var radiusLabel = document.getElementById("coverageMsg");
	radiusLabel.style.display = 'none';
		
		
	}	
else{
	
	document.getElementsByName("zipcodes")[0].value=document.getElementsByName("zipcodesToPost")[0].value;
	var zipcodeLabel = document.getElementById("msg");
	zipcodeLabel.style.display = 'none';
	document.getElementById("coverageMsg").style.display = 'none';;
}
}
function setZipcodes(){
	document.getElementsByName("generalInfoDto.zipcodesCovered")[0].value=document.getElementsByName("zipcodes")[0].value;
	//alert("setZipcodes");
	//alert(document.getElementsByName("generalInfoDto.zipcodesCovered")[0].value);
}


function verifyDispatchZipcode(contextPath,actionName,url,urlRadius,zipcodetoURL,radius,mapurl){
var zipcodeLabel = document.getElementById("msg");
var coverageLabel = document.getElementById("coverageMsg");
		zipcodeLabel.style.display = 'none';
		coverageLabel.style.display = 'none';
		var dispatchZipCode=document.getElementsByName('generalInfoDto.dispAddZip')[0].value;
		var geoRange=document.getElementsByName('generalInfoDto.dispAddGeographicRange')[0].value;
		if(dispatchZipCode=='' && geoRange==''){
			zipcodeLabel.innerHTML = "<p class='errorMsg'>"+"Please provide dispatch zip and radius to populate map"+"</p>";
			zipcodeLabel.style.display = 'block';
			return;
		}
		// fixes to display correct exception message 
		if(dispatchZipCode==''){
			zipcodeLabel.innerHTML = "<p class='errorMsg'>"+"Please provide dispatch zip to populate map"+"</p>";
			zipcodeLabel.style.display = 'block';
			return;
		}
		if(geoRange==''){
			zipcodeLabel.innerHTML = "<p class='errorMsg'>"+"Please provide radius to populate map"+"</p>";
			zipcodeLabel.style.display = 'block';
			return;
		}
		
		//alert("inside utils.js");
		var ajaxurl = contextPath + "/generalInfoAction"+actionName+"?zipcode="+dispatchZipCode;
		//alert("inside utils.js");
	//	alert(dispatchZipCode);
		var xhttp = new XMLHttpRequest();
		 xhttp.open("GET", ajaxurl, true);
		xhttp.send();
		
		
	  xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var data=xhttp.responseText;
			//alert(data);
			if(data=="Valid Zip Code"){
			//	alert("calling myFunction");
				openCoverageWindow(contextPath,url,urlRadius,zipcodetoURL,radius,mapurl);
			}else{
			//	alert("HI");
					zipcodeLabel.innerHTML = "<p class='errorMsg'>"+data+"</p>";
					zipcodeLabel.style.display = 'block';
			}
							
		}
	 }; 
	
	
}

function setSelectedRadius(textbox, dispatch){
	//alert(textbox.value);
	//alert(textbox.defaultSelected);
	//alert(textbox.oldvalue);
	if(textbox.value!=textbox.oldvalue){
		//alert("changed");
		document.getElementsByName("zipcodes")[0].value="";
	var zipcodeLabel = document.getElementById("coverageMsg");
	zipcodeLabel.innerHTML = "Please open the map to confirm the new coverage zipcodes.";
				zipcodeLabel.style.display = 'block';
	var zipcodeLabel = document.getElementById("msg");
	zipcodeLabel.style.display = 'none';
		
		
	}	
	else if(dispatch.defaultValue==dispatch.value){
	//alert("not changed");
	document.getElementsByName("zipcodes")[0].value=document.getElementsByName("zipcodesToPost")[0].value;
	var zipcodeLabel = document.getElementById("coverageMsg");
	zipcodeLabel.style.display = 'none';
	ocument.getElementById("msg").style.display = 'none';;
}
}
