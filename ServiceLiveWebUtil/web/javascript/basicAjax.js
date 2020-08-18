var http_request = false;
var pageRedirectAfterTime = 10;
//var rndNum = 0
function makeRequest(url, parameters, functionName) {
	
	// alert('make request....');
	
	http_request = false;
	if (window.XMLHttpRequest) { // Mozilla, Safari,...
		http_request = new XMLHttpRequest();
		if (http_request.overrideMimeType) {
             // set type accordingly to anticipated content type
            //http_request.overrideMimeType('text/xml');
			http_request.overrideMimeType('text/html');
		}
	} else {
		if (window.ActiveXObject) { // IE
			try {
				http_request = new ActiveXObject("Msxml2.XMLHTTP");
			}
			catch (e) {
				try {
					http_request = new ActiveXObject("Microsoft.XMLHTTP");
				}
				catch (e) {
				}
			}
		}
	}
	if (!http_request) {
		//alert('Cannot create XMLHTTP instance. Browser not supported.');
		return false;
	}
	
	http_request.onreadystatechange = functionName;
	if (parameters == null || parameters == "") {
			http_request.open('POST', url, true);
	} else {
			http_request.open('POST', url + parameters, true);
		
	}
	http_request.send(null);
}

function alertContents() {
	
	if (http_request.readyState == 4) {
		if (http_request.status == 200) {
			//alert('rndNum :'+rndNum);
			result = http_request.responseText;
			
			var selArr = result.split("|~|");
			                        if(selArr.length >= 4){
			                        // selArr[0]= status (erorr or success string), selArr[1]= queueId, selArr[2]= message, selArr[3]= uniqueNumber, selArr[4]= new requeue date
			                        // Check is the element exists before setting the innerHTML or value.
			
				                                  if(document.getElementById('messageSpan'+selArr[1]+''+selArr[3]) != null){ 
					                                  	var messageSpan = document.getElementById('messageSpan'+selArr[1]+''+selArr[3]);
					                                  	var innderDivId = 'theInnerDiv'+selArr[1]+''+selArr[3];
					                                   	var newdiv = document.createElement("div");
					                                   	newdiv.setAttribute('id', innderDivId);
					                                   	// Remove the existing child nodes. i.e. existing messages.
					                                   	if(messageSpan.hasChildNodes()){
					                                   		var innerDiv = document.getElementById(innderDivId)
					                                   		messageSpan.removeChild(innerDiv);
					                                   	}
					                                   	
					                                   	try {
					                                   		// alert($ESAPI.encoder().encodeForHTML(selArr[2]));
															newdiv.innerHTML = $ESAPI.encoder().encodeForHTML(selArr[2]);
															messageSpan.appendChild(newdiv);
														} catch (e) {
															
														}
									
				                                   }
				                                  
			                        }
		                        
			                        // If the operation was performed successfully refresh the page to display the recently
			                        // added queues or notes.
			                        if(selArr[0]=='success'){
			                        	//window.location.href=''+selArr[selArr.length-1];
			               				//location.reload();
			               				 window.setTimeout("reLoadPage()",pageRedirectAfterTime);         	
			                        } else if(selArr[0]=='exception'){
			                        		window.location.href='/MarketFrontend/pbController_execute.action';
			                        } else{
			                        	if(document.getElementById('Un-claim'+randomNum)!=null){
			                        		document.getElementById('Un-claim'+selArr[selArr.length-1]).disabled=true;
			                        	}
			                        }
			                     } 
								 else if(http_request.status == 302){
									 alert('Your session has expired because of inactivity during last 30 mins.');
									 window.location.href='/MarketFrontend/pbController_execute.action';
			                     } 
			                     else
			                     {
			                        alert('An unexpected error occurred. Please contact the help desk if the error occurs again.');
			                     }
	}
	
}

function submitFormUsingAjax(f, url, randomNum) {
	var getstr = "?";
	//rndNum = randomNum;
	var formElements = "";
	if(document.getElementById('Un-claim'+randomNum)!=null){
		document.getElementById('Un-claim'+randomNum).disabled=true;
	}
	//alert('disabling the Un-claim'+randomNum);
	
				for (var n = 0; n < f.elements.length; n++) {
					if (f.elements[n].tagName == "INPUT" || f.elements[n].tagName == "input") {
						if (f.elements[n].type == "TEXT" || f.elements[n].type == "text") {
							getstr += f.elements[n].name + "=" + encodeURI(f.elements[n].value) + "&";
						}
						if (f.elements[n].type == "hidden") {
							getstr += f.elements[n].name + "=" + encodeURI(f.elements[n].value) + "&";
						}
						if (f.elements[n].type == "RADIO" || f.elements[n].type == "radio") {
							if (f.elements[n].checked) {
								getstr += f.elements[n].id + "=" + encodeURI(f.elements[n].value) + "&";
							}
						}
					}
					
					if (f.elements[n].tagName == "SELECT" || f.elements[n].tagName == "select") {
							try {
								var sel = f.elements[n];
								var selIndex = sel.selectedIndex;
								if(selIndex<0){
									selIndex = 0;
								}
								getstr += sel.id + "=" + encodeURI(sel.options[selIndex].value) + "&";
							} catch (e) {
									//alert("An exception occurred in the script. Error name: " + e.name + ". Error message: " + e.message); 
							}
					}

					if (f.elements[n].tagName == "TEXTAREA" || f.elements[n].tagName == "textarea") {
						getstr += f.elements[n].name + "=" + encodeURI(f.elements[n].value) + "&";
					}

				}
	makeRequest(url, getstr, alertContents);
}

             
     function populateQueueNotes(frm,elementId,target) {
              	//var selectedElement = document.getElementById(form.id).elements[elementId];
               // var selectedElement = document.getElementById(elementId);
               // Can not use document.getElementById because we have pages with same elementIds. Thanks to dijit.layout.contentPane
               var sel = frm.elements[elementId];
                // alert(sel);
                //var selIndex =selectedElement.selectedIndex;
                var selectedValue = sel.options[sel.selectedIndex].value;
                if(selectedValue!=null)
                {
                	//1. "${wfmTask.taskDesc}2.${wfmTask.soTaskId}
                	//3.${wfmTask.taskState}4.${wfmTask.taskCode}5.${wfmTask.requeueHours}
                	//6.${wfmTask.requeueMins}7.${wfmBuyerQueueDTOObj.queueId}
                    var selArr = selectedValue.split("|~|");
                    if(selArr.length >3){
                    		
	                        frm.elements[target].value = selArr[0];
	                        frm.elements['soTaskId'].value = selArr[1];
	                        frm.elements['taskState'].value = selArr[2];
	                        frm.elements['taskCode'].value = selArr[3];
	                                                
	                        if(selArr[2]=='requeue'){
	                        
	                        // get the date time rom the browser
	                        	var dateTime = computeRequeueDateTime(selArr[4],selArr[5]);
	                        	//var dateTime = getRequeueDateTime(selArr[4],selArr[5]);
	                        		frm.elements['requeueDate'].disabled=false;
				                	frm.elements['requeueTime'].disabled=false;
		                        var splArr = dateTime.split("|~|");
		                        if(splArr.length>1){
		                        	frm.elements['requeueDate'].value=splArr[0];
		                        	frm.elements['requeueTime'].value=splArr[1];
		                        }
	                        } else{
	                        		frm.elements['requeueDate'].value='';
		                        	frm.elements['requeueTime'].value='';
	                        		frm.elements['requeueDate'].disabled=true;
				                	frm.elements['requeueTime'].disabled=true;
	                        
	                        }
                     }else{
                     	frm.elements[target].value = '';
                     }
                }

        }

function submitUnClaim(url, confirmCheck , uniqueNumber) {
	// alert('submitUnClaim was called');
	var unclaimVerification = "false";
	if (confirmCheck == "verifyIfPendingQueuesNeedAction") {//Check if more queues need action
		unclaimVerification = "true";
	}
	var parameterRequest = "&unclaimVerification="+unclaimVerification+"&uniqueNumber="+uniqueNumber;
	makeRequest(url, parameterRequest, submitUnClaimCB);
}

function submitUnClaimCB() {


	if (http_request.readyState == 4) {
//	alert('inside submitUnClaimCB .....');
		if (http_request.status == 200) {
		    result = http_request.responseText;
		//   alert('result'+result);
		    var selArr = result.split("|~|");
		    
            if(selArr[0]==null||selArr[0]=="") { 
			     showWindow(selArr[1]);
		    } else if (selArr[0]=="primary_queue_action") {//Primary queue needs action
		    	showWindowAboutPrimaryQueue(selArr[1]);
		    } else if(selArr[0]=="Un-claim was unsuccessfull"){
		    	alert("This Service Order was un-claimed by another user or by the system. Please re-claim this Service Order.");
		    } else {
		     // submit the form again

               	 //document.getElementById('messageSpan').innerHTML = selArr[0];
               	 document.forms['backToWFM'+selArr[1]].submit();
		 		 return false;
		    }
		} else {
			alert("An unexpected error occurred while un-claiming the Service Order. Please contact the help desk if the error occurs again.");
		}
	}
}

/* This variable sets the position of the modal window relative to the un-claim button*/
var unclaimButtonPosition;
  function findPosY(obj)
  {
   var curtop = 0;
    if(obj.offsetParent)
        while(1)
        {
          curtop += obj.offsetTop;
          if(!obj.offsetParent)
            break;
          obj = obj.offsetParent;
        }
    else if(obj.y)
        curtop += obj.y;
    //alert('Y pos: ' + curtop);
    unclaimButtonPosition = curtop;
    //alert();
  }
	
function showWindow(uniqueNumber) {
	var modalwindowPosition=100;
	jQuery.noConflict();
	document.getElementById('modalDefineQueueNeedActionMessage'+uniqueNumber).style.display = 'block';
	
	if(!unclaimButtonPosition){
		unclaimButtonPosition=220;
	}
	modalwindowPosition = unclaimButtonPosition/55;
	//alert('modal1: ' + modalwindowPosition); 
	if(modalwindowPosition > 100) {
		modalwindowPosition=95;
	}
	document.getElementById('modalDefineQueueNeedActionMessage'+uniqueNumber).style.top = modalwindowPosition + '%';
}

function showWindowAboutPrimaryQueue(uniqueNumber) {
	var modalwindowPosition=100;
	jQuery.noConflict();
	document.getElementById('modalDefinePrimaryQueueNeedActionMessage'+uniqueNumber).style.display = 'block';
	
	if(!unclaimButtonPosition){
		unclaimButtonPosition=220;
	}
	modalwindowPosition = unclaimButtonPosition/55;
	
	if(modalwindowPosition > 100) {
		modalwindowPosition=95;
	}
	document.getElementById('modalDefinePrimaryQueueNeedActionMessage'+uniqueNumber).style.top = modalwindowPosition + '%';
}

function closeModal(modalWindowObject) {
	document.getElementById(modalWindowObject).style.display="none";
}

function resetAllForms(windowOrLayer) {
			  if (!windowOrLayer)
			    windowOrLayer = window;
			  for (var f = 0; f < document.forms.length; f++)
			    windowOrLayer.document.forms[f].reset();
			  if (document.layers)
			    for (var l = 0; l < windowOrLayer.document.layers.length; l++)
			      resetAllForms(windowOrLayer.document.layers[l]);
}

// Clears messages and resets all the forms
function clearFormsMsgs(spanId) {
	document.getElementById(spanId).innerHTML = '';
	resetAllForms();
}


  
            function changeTab(num, tab)
            {
                      
                if(num==1)
                {
                    document.getElementById('queueNotes'+tab).style.display ="none";
                    document.getElementById('quickLinksDiv'+tab).style.display ="block";
                }
                else
                {
                    document.getElementById('queueNotes'+tab).style.display ="block";
                    document.getElementById('quickLinksDiv'+tab).style.display ="none";
                }
                
            
            }
           
            function populateNotesFunc(elementId,queueId)
            {
                var selectedElement = document.getElementById(elementId);
                var selIndex =selectedElement.selectedIndex;
                var selectedValue = selectedElement.options[selIndex].value;
                if(selectedValue!=null)
                {
                    var selArr = selectedValue.split("|");
                    if(selArr.length >1)
                        document.getElementById('noteText'+queueId).value = selArr[0];
                }

            }
            
// showHideLinksNotes method shows/hides quick links and queue note tabs.
	function showHideLinksNotes(obj, suffix){
			var tab = 'pane' + obj.id;
			var tabObj = document.getElementById(tab);
			if(tabObj){
			    var ps='panequeueNotes'+suffix; 
				var qn='queueNotes'+suffix;
				var pl='panequickLinks'+suffix;
				var ql='quickLinks'+suffix;
				tabObj.style.display = 'block';
				
				if(tab == pl){
					var t = document.getElementById(ps);
					var tLink = document.getElementById(qn);
					t.style.display = 'none';
					tLink.className = '';
				}
					
				if(tab == ps){
					var t = document.getElementById(pl);
					var tLink = document.getElementById(ql);
					t.style.display = 'none';	
					tLink.className = '';
				}
				var myLink = document.getElementById(obj.id);
				myLink.className = 'selected';
			}
		
		}


	function reLoadPage(){
		  window.location.reload(); 
	}



