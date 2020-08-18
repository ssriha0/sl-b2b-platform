var currentType;
var currentAmount;
var credentialId;
var button;
var sameDocInd;
var sameDocName;
var docErrorMsg="";
var isSubmitted=false;

function clearTextbox(el) {
	if (el.defaultValue==el.value) {
		el.value='';
		//el.style.cssText += "color: red"; 
		el.className ="shadowBox"; }
}

function clearTextboxNew(el) {
	if (el.defaultValue==el.value) {
		el.value='';
		//el.style.cssText += "color: red"; 
		el.className ="text"; }
}

function clearAuxNavLogin(el) {
	if (el.defaultValue==el.value) {
		el.value='';
		//el.style.cssText += "color: red"; 
		 }
}

function changeDropdown(el) {
		//el.style.cssText += "color: red"; 
		el.className ="";
}

function submitPage(test){
	for (var i = 0;  i < document.forms.length; i++){
		   var name = document.forms[i].name;
		   if (name == "addInsuranceAction"){
				document.forms[i].action=test;
				document.forms[i].submit();
		   }           
	 }  
}

function submitInsTypeInfo(val)
{	  
		var credId;
		if(val != 0){
			credId = val.substring(val.lastIndexOf("credId=")+7,val.lastIndexOf("catId=")-5);
			val = val.substring(val.lastIndexOf("=")+1,val.lastIndexOf("=")+5);
		}
		
		for (var i = 0;  i < document.forms.length; i++)
         {
               var name = document.forms[i].name;
              
               if (name == "addInsuranceAction")
               {
               		document.forms[i].credId.value=credId;
               		document.forms[i].category.value=val;
               		document.forms[i].action="saveInsuranceActionloadInsuranceTypePage.action";
               		document.forms[i].submit();
               }           
         }  
}



function deleteInsuranceTypeInfo()
{
	for (var i = 0;  i < document.forms.length; i++){
           var name = document.forms[i].name;
           if (name == "saveInsuranceTypeActiondoSave")
           {	
           		document.forms[i].action = "deleteInsuranceActiondoDelete.action";
				document.forms[i].submit();
           }           
     }   
}

function saveInsuranceTypePage(val)
{ 
            for (var i = 0;  i < document.forms.length; i++)
            {
                  var name = document.forms[i].name;          
                  if (name == "insuranceForm")
                  {
                  	document.forms[i].action = val;
					document.forms[i].submit();
                  }           
            } 
             
}

 

function cancelInsurance(val)
{
	for (var i = 0;  i < document.forms.length; i++)
     {
           var name = document.forms[i].name;
           if (name == "saveInsuranceTypeActiondoSave")
           {	
           		document.forms[i].action = "saveInsuranceTypeAction!doCancel.action";
				document.forms[i].method.value = "cancel";
				document.forms[i].submit();
           }           
     }   
}

function saveInsuranceTypeMethod(val)
{
	for (var i = 0;  i < document.forms.length; i++)
     {
           var name = document.forms[i].name;
           if (name == "saveInsuranceTypeActiondoSave")
           {	
           		document.forms[i].action = val;
				//document.forms[i].submit();
           }           
     }   
}

function changeStatus(){
            document.forms[0].action = "listInsuranceActionchangeStatus.action";
            document.forms[0].submit();
}

function addCredentials(url){
	var urlNext=url;
	for (var i = 0;  i < document.forms.length; i++){
		  var name = document.forms[i].name;
			if(name=='teamCredentialAction'){
				//document.forms[i].action = urlNext;
				document.forms[i].action="teamCredentialAction!addCredentilDetails.action";
				//document.forms[i].submit();
		   }
	}    
}

function editCredentials(url){
	var resourceId = url.substring(url.indexOf("resourceCredId"), url.length);
	for (var i = 0;  i < document.forms.length; i++){
		  var name = document.forms[i].name;
			if(name=='teamCredentialAction'){
				//document.forms[i].action="teamCredentialAction!loadCredentialDetails.action?"+ resourceId;
				document.forms[i].action="teamCredentialAction!editCredentilDetails.action?"+ resourceId;
				document.forms[i].submit();
		   }
	}   
		//To Do add method to call edit user Functionality
}

    function saveCredentilDetail(){
	for (var i = 0;  i < document.forms.length; i++){
		  var name = document.forms[i].name;
			if(name=='teamCredentialActionloadCredentialDetails'){
				//document.forms[i].action="teamCredentialAction!loadCredentialDetails.action?"+ resourceId;
				document.forms[i].action="teamCredentialAction!saveCredentilDetail.action";
				//document.forms[i].submit();
		   }
		}   
		//To Do add method to call add user Functionality
	}
	
	function attachDocument(){
	for (var i = 0;  i < document.forms.length; i++){
		  var name = document.forms[i].name;
			if(name=='teamCredentialActionloadCredentialDetails'){
				document.forms[i].action="teamCredentialAction!attachDocument.action";
		   }
		}   
		//To Do add method to call add user Functionality
	}
	
	function removeDocumentDetail(){
		for (var i = 0;  i < document.forms.length; i++){
			  var name = document.forms[i].name;
				if(name=='teamCredentialActionloadCredentialDetails'){
					//document.forms[i].action="teamCredentialAction!loadCredentialDetails.action?"+ resourceId;
					document.forms[i].action="teamCredentialAction!removeDocument.action";
					//document.forms[i].submit();
			   }
			}   
		//To Do add method to call add user Functionality
	}

	function changeTypeOfCredential(){
		//To Do add method to call add user Functionality
		for (var i = 0;  i < document.forms.length; i++){
		  var name = document.forms[i].name;
			if(name=='teamCredentialActionloadCredentialDetails'){
				//document.forms[i].action="teamCredentialAction!loadCredentialDetails.action?"+ resourceId;
				document.forms[i].action="teamCredentialAction!changeCredentialCatType.action";
				document.forms[i].submit();
		   }
		}   
	}

	function removeCredentilDetail(){
		for (var i = 0;  i < document.forms.length; i++){
		  var name = document.forms[i].name;
			if(name=='teamCredentialActionloadCredentialDetails'){
				document.forms[i].action="teamCredentialAction!removeCredentilDetail.action";
				document.forms[i].submit();
		   }
		}   
	}
	
	function nextCredentilList(){
		for (var i = 0;  i < document.forms.length; i++){
		  var name = document.forms[i].name;
			if(name=='teamCredentialAction'){
				//document.forms[i].action = urlNext;
				document.forms[i].action="teamCredentialAction!doNextOnCredentialList.action"
				document.forms[i].submit();
		   }
		}    
	}
	
	 function saveTermsPage(val)
	{           
            for (var i = 0;  i < document.forms.length; i++)
            {
                  var name = document.forms[i].name;
                  if (name == "termsAction")
                  {
                  	document.forms[i].action = val;
					document.forms[i].submit();
                  }           
            }           
	}
	
	function submitServicePage(test){
			
		if(!isSubmitted){
			isSubmitted = true;		
			for (var i = 0;  i < document.forms.length; i++)
	        {
	        	var name = document.forms[i].name;
	            if (name == "resourceskillAssignAction")
	            {
	            	document.forms[i].action=test;
				}           
	      	}       
	   	}
	}
	
	function submitSkillsPage(test){
		
	
		 for (var i = 0;  i < document.forms.length; i++)
            {
                  var name = document.forms[i].name;
                  if (name == "skillAssignGeneralAction")
                  {
                  	document.forms[i].action=test;
					
				}           
            }       
	}
	
	
	function preCredentilList(){
		for (var i = 0;  i < document.forms.length; i++){
		  var name = document.forms[i].name;
			if(name=='teamCredentialAction'){
				//document.forms[i].action = urlNext;
				document.forms[i].action="teamCredentialAction!doPrevOnCredentialList.action";
				document.forms[i].submit();
		   }
		}    
	}
	function cancelCredential(){
		for (var i = 0;  i < document.forms.length; i++){
		  var name = document.forms[i].name;
			if(name=='teamCredentialActionloadCredentialDetails'){
				//document.forms[i].action = urlNext;
				document.forms[i].action="teamCredentialAction!doCancelCredentialDetail.action"
				document.forms[i].submit();
		   }
		}    
	}
	
	function EnableSubmit(radio)
	{
		
		var ctrl1 = document.getElementById("previousID");
		var ctrl2 = document.getElementById("saveID");

		if (radio.value == "1")
		{
			ctrl1.disabled = false;
			ctrl2.disabled = false;
		}
		else{
			ctrl1.disabled = true;
			ctrl2.disabled = true;
		}
	
	}
	
	function enableChkUserRadio(radio)
	{
		var ctrl1 = document.getElementById("userVal");
		var userId = document.getElementById("userId");
		var val=userId.value;
		var textboxproperty;
		if( radio.value=="1")
		{
			ctrl1.style.visibility ="visible";
			userId.value=val;
		}
		else
		{
			textboxproperty = document.getElementById("userId").readOnly;
			if (textboxproperty)
			{
				userId.value=val;
			}
			else
			{
				userId.value="";
			}
			ctrl1.style.visibility ="hidden";
		}
	}
	
	function checkEnable()
		{	
			var radioCtrl0 =  document.getElementById("yesID0");
			var radioCtrl1 =  document.getElementById("yesID1");
			var ctrl = document.getElementById("SaveID");
			if (radioCtrl1.checked == true)
				ctrl.disabled = false;
			else
				ctrl.disabled = true;
		}		
		

function submitDashBoard(actionName){
	document.regDashboardAction.action=actionName;
	document.regDashboardAction.submit();
}

function submitRegistration(actionName){

for (var i = 0;  i < document.forms.length; i++)
          {
                var name = document.forms[i].name;
                if (name == "submitRegAction")
                {
                	document.forms[i].action=actionName;
					//document.forms[i].submit();
				}           
          } 

}

function SubmitInsuranceNone(action)
{
	for (var i = 0;  i < document.forms.length; i++)
          {
                var name = document.forms[i].name;
                if (name == "skillAssignGeneralAction")
                {
                	document.forms[i].action=test;
				document.forms[i].submit();
			}           
          }     
}
				
function doCheckAll(param)
{
	for (var j = 0;  j < document.forms.length; j++)
    {
		var name = document.forms[j].name;
        if (name == "resourceskillAssignAction")
        {
        	
        	doCheckAll4List(param, j);
        }
    }
}                       
                        
 function doCheckAll4List(param, formCount){

                        var fieldName=param.name;

                        var tokenArray=fieldName.split("_");

                        for (var i=0; i < document.forms[formCount].elements.length; i++) {

                                                var element = document.forms[formCount].elements[i];

                                                            if((element.name.indexOf("chk_")!=-1 && element.name.indexOf("chk_")==0 )){

                                                                        var elementArray=element.name.split("_");

                                                                        if(elementArray.length==5 && elementArray[2]==tokenArray[1]){

                                                                                    if(param.checked){

                                                                                                document.forms[formCount].elements[i].checked=1;

                                                                                    }else{

                                                                                                document.forms[formCount].elements[i].checked=0;

                                                                                    }

                                                                        

                                                                        }//end elementArray.length==5

                                                             

                                                            }//end element.name.indexOf("chk_"

                                    }// end for

  }//end function doCheckAll4List

function checkCheckBox(param)
{
	for (var j = 0;  j < document.forms.length; j++)
    {
		var name = document.forms[j].name;
        if (name == "resourceskillAssignAction")
        {
        	
        	checkCheckBox4List(j);
        }
    }
}

function checkCheckBox4List(formCount){

            var arrayLength=document.forms[formCount].hiddHeaders.length;

            if(arrayLength>0){

                        var key;

                        var found=true;

                        for(var i=0;i<arrayLength;i++){

                                    key=document.forms[formCount].hiddHeaders[i].value;

                                    for (var j=0; j < document.forms[formCount].elements.length;j++) {

                                                var element = document.forms[formCount].elements[j];

                                                            if((element.name.indexOf("chk_")!=-1 && element.name.indexOf("chk_")==0 )){

                                                                        var elementArray=element.name.split("_");

                                                                        if(elementArray.length==5 && elementArray[2]==key){

                                                                                    if(!element.checked){

                                                                                    found=false;

                                                                                    break;

                                                                                    }

                                                                        }

                                                            }

                                    }//end of for 

                                       var mth = eval("document.forms[formCount].ckeckAll_"+key+".checked"); 

                                       if(found){

                                                  eval("document.forms[formCount].ckeckAll_"+key+".checked=1");

                                       }else{
                                                   eval("document.forms[formCount].ckeckAll_"+key+".checked=0");

                                       }

                           found=true;

                           key=null;

                        }//end of for arrayLength

            }//if(arrayLength>0)

}//end function checkCheckBox4List

 function checkChlidObjects(param)
{
	for (var j = 0;  j < document.forms.length; j++)
    {
		var name = document.forms[j].name;
        if (name == "resourceskillAssignAction")
        {
        	
        	checkChlidObjects4List(param,j);
        }
    }
}

function checkChlidObjects4List(param, formCount){

	var fieldName=param.name; 
	var labelId = fieldName.replace("chk","lbl");
	var labelElement = document.getElementById(labelId);
	var labelValue = labelElement.value;
	var labelArray=labelValue.split("_");	
	var tokenNodeId = labelArray[1];	
	var tokenLevel = labelArray[4]	
	var tokenArray=fieldName.split("_");
	var searchKey
	var searchIndex

    if(tokenArray[4]==2){
    
		searchKey=tokenArray[1];
		searchIndex=3;
		
    }//tokenArray[3]==1
    else{
    
		searchKey=tokenArray[3];
		searchIndex=1;

    } 
               
	for (var i=0; i < document.forms[formCount].elements.length; i++) {
	
		var element = document.forms[formCount].elements[i];
		if((element.name.indexOf("chk_")!=-1 && element.name.indexOf("chk_")==0 )){

			var elementArray=element.name.split("_");        
        	var elementName=element.name;
            var elementLabelId = elementName.replace("chk","lbl");
            var elementLabelElement = document.getElementById(elementLabelId);
            var elementLabelValue = elementLabelElement.value;
			var elementLabelArray=elementLabelValue.split("_");

			if(elementArray.length==5 && elementArray[searchIndex]==searchKey&&elementArray[2]==tokenArray[2]){

				if(param.checked){
                
                 	if(checkAllObjcets(param)||tokenArray[4]==2){
                     	document.forms[formCount].elements[i].checked=1;
          			}
         			
                }else{
                
                 	if(tokenArray[4]==2){
                     	document.forms[formCount].elements[i].checked=0;	
                  	} 
                                                        
               	 }//end if param.checked                                                                        

			}// end elementArray.length==4 && elementArray[searchIndex]==searchKey&&elementArray[2]==tokenArray[2]

			if(labelArray[2]==elementArray[2] ){ 
			        
				if(param.checked){
				
					if(tokenLevel==3 ){
					
						if(elementLabelArray[4]==1 ||  (elementLabelArray[4]==2  && elementLabelArray[1] == labelArray[3] )){
						
							document.forms[formCount].elements[i].checked=1;
						}
						
					}else{
					
						if(tokenLevel==2 ){
						
							if(elementLabelArray[4]==1 ){
							
								document.forms[formCount].elements[i].checked=1;
								
							}else{
							
								if(elementLabelArray[4]==3  && elementLabelArray[3] == tokenNodeId ){
								
									document.forms[formCount].elements[i].checked=1;
						
								}
							}
						}
					}
						
			    }else{
			    
			    	if(tokenLevel==1 ){
			    	
						document.forms[formCount].elements[i].checked=0;
									
					}else{
							
						if(tokenLevel==2 && elementLabelArray[4]==3  && elementLabelArray[3] == tokenNodeId ){
						
							document.forms[formCount].elements[i].checked=0;
						
						}
					}
			    }
			} 	 

		} // end if element.name.indexOf("chk_")!=-1 && element.name.indexOf("chk_")==0 )

	}// end for

}// function checkChlidObjects

 function checkAllObjcets(param)
{
	for (var j = 0;  j < document.forms.length; j++)
    {
		var name = document.forms[j].name;
        if (name == "resourceskillAssignAction")
        {
        	
        	checkAllObjcets4List(param,j);
        }
    }
}

  function checkAllObjcets4List(param,j){

            var fieldName=param.name;

            var tokenArray=fieldName.split("_");

            var searchKey

            var searchIndex

            searchKey=tokenArray[3];

            searchIndex=3;

            for (var i=0; i < document.forms[j].elements.length; i++) {

                                                var element = document.forms[j].elements[i];

                                                            if((element.name.indexOf("chk_")!=-1 && element.name.indexOf("chk_")==0 )){

                                                                        var elementArray=element.name.split("_");

                                                                        if(elementArray.length==5 && elementArray[searchIndex]==searchKey&&elementArray[2]==tokenArray[2]){

                                                                                    if(!document.forms[j].elements[i].checked){

                                                                                                return false;

                                                                                    

                                                                                    } //end if param.checked

                                                                        

                                                                        }// end elementArray.length==4 && elementArray[searchIndex]==searchKey&&elementArray[2]==tokenArray[2]

                                                             

                                                            } // end if element.name.indexOf("chk_")!=-1 && element.name.indexOf("chk_")==0 )

                                    }// end for
					
                        return true;

  }

function licensesIssueDatePicker(formField,dateFormat,form){
	for (var i = 0;  i < document.forms.length; i++){
		 var name = document.forms[i].name;
		 if (name == "licensesAndCertActiondoLoad?type=cat"){
			displayCalendar(document.forms[i].issueDate1,'mm/dd/yyyy',form)
		 }           
   }
}


function licensesExpDatePicker(formField,dateFormat,form){
	for (var i = 0;  i < document.forms.length; i++){
		 var name = document.forms[i].name;
		 if (name == "licensesAndCertActiondoLoad?type=cat"){
			displayCalendar(document.forms[i].expirationDate1,'mm/dd/yyyy',form)
		 }           
   }
}
	
	
function cancelResourceSkillAssign(val)
{           
    for (var i = 0; i < document.forms.length; i++)
	{	
		var name = document.forms[i].name;
		
		if (name == "resourceskillAssignAction")
		{ 	
			document.forms[i].action = "resourceSkillAssignAction!doCancel.action";
			document.forms[i].method.value = "cancel";
			document.forms[i].submit();
		} 
	}           
}

function cancelSkillAssignGen(val)
{           
    for (var i = 0; i < document.forms.length; i++)
	{
		var name = document.forms[i].name;
		if (name == "skillAssignGeneralAction")
		{ 	
			document.forms[i].action = "skillAssignGeneralAction!doCancel.action";
			document.forms[i].method.value = "cancel";
			document.forms[i].submit();
		} 
	} 
}	

function cancelProTerms(val)
{
	for (var i = 0; i < document.forms.length; i++)
	{
		var name = document.forms[i].name;
		if (name == "termsAction")
		{ 
			document.forms[i].action = "termsAction!doCancel.action";
			document.forms[i].method.value = "cancel";
			document.forms[i].submit();
		} 
	} 
}
	
function cancelMarketPlace(val)
{
	for (var i = 0; i < document.forms.length; i++)
	{
		var name = document.forms[i].name;
		if (name == "marketPlaceAction")
		{ 
			document.forms[i].action = "marketPlaceAction!doCancel.action";
			document.forms[i].method.value = "cancel";
			document.forms[i].submit();
		} 
	} 
}

function removeLicDocument(val)
{
	for (var i = 0;  i < document.forms.length; i++){
	  var name = document.forms[i].name;
		if(name=='licensesAndCertActiondoLoad?type=cat'){
			document.forms[i].action="licensesAndCertActiondoLoad!removeDocument.action";
	   }
	}   
}
function saveLicCert(val)
{
	for (var i = 0;  i < document.forms.length; i++){
	  var name = document.forms[i].name;
		if(name=='licensesAndCertActiondoLoad?type=cat'){
			document.forms[i].action="licensesAndCertActiondoSave.action";
	   }
	}   
}

function attachLicDocument(val)
{
	for (var i = 0;  i < document.forms.length; i++){
	  var name = document.forms[i].name;
		if(name=='licensesAndCertActiondoLoad?type=cat'){
			document.forms[i].action="licensesAndCertActiondoLoad!attachDocument.action";
	   }
	}   
}

function saveLCInformation(saveLCInformation){
	for (var i = 0;  i < document.forms.length; i++){
	  var name = document.forms[i].name;
		if(name=='licensesAndCertActiondoLoad?type=cat'){
			document.forms[i].action=saveLCInformation;
			document.forms[i].submit();
			
	   }
	}   
}


function checkCredential(chkForm)
{
	chkForm.action = "licensesAndCertAction!doCheckCredential.action";
	chkForm.submit();	
}

function checkTeamCredential(chkForm)
{
	chkForm.action = "teamCredentialAction!checkCredentialList.action";
	chkForm.submit();	
}

function openpublicSkillTree(nodeId,vendorResourceId){
	// alert("This should open the Manage User - Individual Profile page - Part if iterartion 14");
	// To Do add method to call summary user Functionality
	// this will be done later - meanwhile forwarding to edit user page
	document.profilePublic.nodeId.value=nodeId;
	document.profilePublic.resourceId.value=vendorResourceId;
	document.profilePublic.action="profileTabAction.action?tabView=tab2";
	document.profilePublic.submit();		

}

function openSkillTree(nodeId){
	// alert("This should open the Manage User - Individual Profile page - Part if iterartion 14");
	// To Do add method to call summary user Functionality
	// this will be done later - meanwhile forwarding to edit user page
	document.publicProfile.nodeId.value=nodeId;
	document.publicProfile.action="profileTabAction.action?tabView=tab1";
	document.publicProfile.submit();		

}

function editUser(resourceId){
			
			document.publicProfile.resourceId.value=resourceId ;
			document.publicProfile.action = "serviceProAllTab!load.action";
			document.publicProfile.submit();
					
		}
		function returnToList(){
			
			document.publicProfile.action = "manageUserAction!loadUsers.action";
			document.publicProfile.submit();
					
		}
	
		function returnToListPublic(){
			
			document.profilePublic.action = "manageUserAction!loadUsers.action";
			document.profilePublic.submit();
					
		}	
		
function communitypopup() 
 {
	 communitywindow = window.open ("http://training.servicelive.com/wp-content/uploads/Provider_Background_Check_Tab_Service_Pro.pdf","communitywindow");
 } 

 function communitypopuprecertification()
 {
	 communitywindowrecertification = window.open ("http://training.servicelive.com/wp-content/uploads/Provider_Background_Check_Tab_Service_Pro.pdf","communitywindowrecertification");
 }
 
 	function loadUploadModal(buyerId){
 		document.getElementById('buyerId').value = buyerId;
		
			//spnMonitorAction_selectDocumentPopUp.action?&DocTitle='+title+'&buyerId='+23	
			//var policyInfoDisplayAjaxURL = "processInsuranceAction_doLoad.action?&policyAmount="+ policyAmount+ "&buttonType="+ buttonType+ "&credId="+credId+ "&category="+ category +"&currentDocId="+currentDocId +"&currentDocName="+currentDocName+ "&ts="+new Date().valueOf();
		//	newco.jsutils.doAjaxURLSubmit(policyInfoDisplayAjaxURL, displayPolicyCB);	
		
	}
 

function open_insurance_details(policyAmount,buttonType,credId,category)
{     
      var retVal;
      var s;
      if(category == 'General Liability'){
		s=document.getElementById("CBGLIAmount").value
	  }else if (category == 'Auto Liability'){
		s=document.getElementById("VLIAmount").value
	}else if(category == 'Workmans Compensation'){ 
		s=document.getElementById("WCIAmount").value
	}    
	  policyAmount = s;	      
      
      var url = "processInsuranceAction_doLoad.action?&policyAmount="+ policyAmount+ "&buttonType="+ buttonType+ "&credId="+credId+ "&category="+category;
      retVal=window.showModalDialog(url,'name','dialogWidth:700px;dialogHeight:500px');
      if(retVal==''){         
            return false;
      }
      if (retVal=='Refresh'){
             window.location.href = encodeURI(unescape(window.location.pathname));
      }
      return true;
}


	function checkFieldEnable(docId)
	{
		sameDocInd = 1;
		var uploadInd =  document.getElementById("updateDocumentInd");	
		if (uploadInd.checked == true){
			docErrorMsg='';
			document.getElementById("sameDocId").value = docId;
			document.getElementById("uploadFile").style.display = "none";
			var policyInfoDisplayAjaxURL = "processInsuranceAction_loadPolicyInfoForSelectedDocument.action?&docId="+ docId+ "&sameDocName="+ sameDocName + "&ts="+new Date().valueOf();
			newco.jsutils.doAjaxURLSubmit(policyInfoDisplayAjaxURL, displayPolicyCB);
		}else{
			document.getElementById("uploadFile").style.display ="block";
		}
	}		
	/*Changes related to SL-20301: Details based on LastUploadedDocument*/
	function checkFieldEnable_addtnlInsurance(docId)
	{
		sameDocInd = 1;
		var uploadInd =  document.getElementById("updateDocumentInd");	
		if (uploadInd.checked == true){
			docErrorMsg='';
			document.getElementById("sameDocId").value = docId;			
			document.getElementById("uploadFile1").style.display="none";
			
			jQuery.ajax({
				url: 'processInsuranceAction_loadAdditionalInsPolicyInfoForSelectedDocument.action?&docId='+docId+'&sameDocName='+sameDocName+'&ts='+new Date().valueOf(),
		  		dataType : "json",
		  		success: function( data ) {
		  			document.getElementById("newDocId").value = docId;		  			
		  			document.getElementById("insurancePolicyDto.agencyCountry1").value = data.agencyCountry;
		  			document.getElementById("insurancePolicyDto.agencyState1").value = data.agencyState;
		  			document.getElementById("insurancePolicyDto.carrierName1").value = data.carrierName;
		  			document.getElementById("insurancePolicyDto.agencyName1").value = data.agencyName;
		  			document.getElementById('modal2ConditionalChangeDateAdditionalIss').value = data.policyIssueDate;
		  			document.getElementById('modal2ConditionalChangeDateExpAdditional').value = data.policyExpirationDate;
		  			document.getElementById("insurancePolicyDto.file").value = data.file;
		  			
		  		}
		  	});
			
		}else{
			document.getElementById("uploadFile1").style.display ="block";
			document.getElementById("insurancePolicyDto.agencyCountry1").value = "";
  			document.getElementById("insurancePolicyDto.agencyState1").value = "";
  			document.getElementById("insurancePolicyDto.carrierName1").value = "";
  			document.getElementById("insurancePolicyDto.agencyName1").value = "";
  			document.getElementById('modal2ConditionalChangeDateAdditionalIss').value = "";
  			document.getElementById('modal2ConditionalChangeDateExpAdditional').value = ""; 			
  			
		}
	}	
		
	function policyInfoCancel() {			
		window.returnValue ='';
		window.close();		
	}

	function savePolicyDetails(credID,category) {
       window.returnValue="Refresh";
       window.close();                          
	}

	function disableWCIBtn(){
		var wciStatus = document.insuranceForm.insuranceInfoDtoWCI[0];
		if (wciStatus.value == true){		 					
			document.getElementById("WCIButton").disabled=true;
			document.getElementById('WCIAmount').value = 0;
			if(document.getElementById("lblWCIAmount")!= null){
				document.getElementById("lblWCIAmount").innerHTML = 0;}
			document.getElementById("WCIAmount").disabled=true;
			document.getElementById('insuranceInfoDto.WCI').value='0';
			document.insuranceForm.insuranceInfoDtoWCI[1].checked = true; 
			document.insuranceForm.insuranceInfoDtoWCI[0].checked = false;   
		}	
	}	
	
	function disableCBGLIBtn(){
		var cbgliStatus = document.insuranceForm.insuranceInfoDtoCBGLI[0];
		if (cbgliStatus.value == true){		 					
			document.getElementById("CBGLIButton").disabled=true;
			document.getElementById('CBGLIAmount').value = 0;
			if(document.getElementById("lblCBGLIAmount")!=null){
				document.getElementById("lblCBGLIAmount").innerHTML = 0; }
			document.getElementById("CBGLIAmount").disabled=true;
			document.getElementById('insuranceInfoDto.CBGLI').value='0';
			document.insuranceForm.insuranceInfoDtoCBGLI[1].checked = true; 
			document.insuranceForm.insuranceInfoDtoCBGLI[0].checked = false;   
		}	
	}	
	
	function disableVLIBtn(){
		var vliStatus = document.insuranceForm.insuranceInfoDtoVLI[0];
		if (vliStatus.value == true){		 					
			document.getElementById("VLIButton").disabled=true;
			document.getElementById('VLIAmount').value = 0;
			document.getElementById("VLIAmount").disabled=true;
			document.getElementById('insuranceInfoDto.VLI').value='0';
			if(document.getElementById("lblVLIAmount")!= null){
				document.getElementById("lblVLIAmount").innerHTML = 0;}
			document.insuranceForm.insuranceInfoDtoVLI[1].checked = true; 
			document.insuranceForm.insuranceInfoDtoVLI[0].checked = false;   
		}	
	}	

	function enableButton(){
		var wciStatus =  document.getElementById("insuranceInfoDto.WCI");				
		if (wciStatus.checked == true){			
			document.getElementById("WCIButton").disabled=false;
			
		}	
	}
	
	function showCBGLIAmount(){ 	 
		var indicator = document.insuranceForm.insuranceInfoDtoCBGLI[0];  
      	if (indicator.value == true){     
           if(document.getElementById('CBGLIButton').disabled){      
           		document.getElementById('CBGLIButton').disabled=false;         	
         	}        
         	document.insuranceForm.insuranceInfoDtoCBGLI[1].checked = false; 
         	document.getElementById('insuranceInfoDto.CBGLI').value='1';
         	document.insuranceForm.insuranceInfoDtoCBGLI[0].checked = true; 
         	if(document.getElementById('CBGLIAmount').disabled){
         		document.getElementById("CBGLIAmount").disabled=false; 
         		if(document.getElementById("insuranceForm_hdn_CBGLIAmount")!=null){
         			document.getElementById("CBGLIAmount").value = document.getElementById("insuranceForm_hdn_CBGLIAmount").value;
         		}
         	}
         	if(document.getElementById('lblCBGLIAmount')!= null){
         		if(document.getElementById("insuranceForm_hdn_CBGLIAmount")!=null){
         			document.getElementById("lblCBGLIAmount").innerHTML = document.getElementById("insuranceForm_hdn_CBGLIAmount").value;
         		}
         	}
         	
      	}      	
	}	
	
	function showVLIAmount(){     
	 	var indicator = document.insuranceForm.insuranceInfoDtoVLI[0];   
      	if (indicator.value == true){     
        	if(document.getElementById('VLIButton').disabled){      
           		document.getElementById('VLIButton').disabled=false;         	
         	}        
         	document.insuranceForm.insuranceInfoDtoVLI[1].checked = false; 
         	document.getElementById('insuranceInfoDto.VLI').value='1';
         	document.insuranceForm.insuranceInfoDtoVLI[0].checked = true;
         	if(document.getElementById('VLIAmount').disabled){
         		document.getElementById('VLIAmount').disabled=false;
         		if(document.getElementById("insuranceForm_hdn_VLIAmount")!=null){
         			document.getElementById("VLIAmount").value = document.getElementById("insuranceForm_hdn_VLIAmount").value;
         		}
         	}
         	if(document.getElementById('lblVLIAmount')!= null){
         		if(document.getElementById("insuranceForm_hdn_VLIAmount")!=null){
         			document.getElementById("lblVLIAmount").innerHTML = document.getElementById("insuranceForm_hdn_VLIAmount").value;
         		}
         	}
      	}      	
	}	
	
	
	function showWCIAmount(){  
	    var indicator = document.insuranceForm.insuranceInfoDtoWCI[0];   
      	if (indicator.value == true){     
           if(document.getElementById('WCIButton').disabled){      
           		document.getElementById('WCIButton').disabled=false;         	
         	}        
         	document.insuranceForm.insuranceInfoDtoWCI[1].checked = false; 
         	document.getElementById('insuranceInfoDto.WCI').value='1';
         	document.insuranceForm.insuranceInfoDtoWCI[0].checked = true; 
         	if(document.getElementById('WCIAmount').disabled){
         		document.getElementById('WCIAmount').disabled=false;
         		if(document.getElementById("insuranceForm_hdn_WCIAmount")!=null){
         			document.getElementById("WCIAmount").value = document.getElementById("insuranceForm_hdn_WCIAmount").value;
         		}
         	}
         	if(document.getElementById('lblWCIAmount')!= null){
         		if(document.getElementById("insuranceForm_hdn_WCIAmount")!=null){
         			document.getElementById("lblWCIAmount").innerHTML = document.getElementById("insuranceForm_hdn_WCIAmount").value;
         		}
         	}
      	}
	} 
		
	function fnSubmitToAction(theActionName){			
		var addDundsForm = document.getElementById('addFundsForm');			
		addDundsForm.action='csoAddFunds_'+theActionName+'.action';
		addDundsForm.submit();
				
	}	
		
	function policyInfoSubmit(policyAmount,buttonType,credId,category,currentDocId,currentDocName){
		sameDocInd = 0;		
		policyAmount = currentAmount;
		
		if(category ==  currentType){	
		
			var policyInfoDisplayAjaxURL = "processInsuranceAction_doLoad.action?&policyAmount="+ policyAmount+ "&buttonType="+ buttonType+ "&credId="+credId+ "&category="+ category +"&currentDocId="+currentDocId +"&currentDocName="+escape(currentDocName)+ "&ts="+new Date().valueOf();
			
			newco.jsutils.doAjaxURLSubmit(policyInfoDisplayAjaxURL, displayPolicyCB);	
		}
	}
		
	function setDefaultPolicyDetails(policyAmount,category){		
		document.getElementById('insurancePolicyDto.agencyCountry').value="";
		document.getElementById('insurancePolicyDto.agencyName').value="";
		document.getElementById('insurancePolicyDto.agencyState').value="";
		document.getElementById('insurancePolicyDto.amount').value=policyAmount; 
		document.getElementById('insurancePolicyDto.carrierName').value="";
		document.getElementById('insurancePolicyDto.policyNumber').value="";		
	}
	
	function parseXMLElement(element, data) {
		var retVal = "";
			if (data.getElementsByTagName(element)[0]) {
				if (data.getElementsByTagName(element)[0].childNodes[0]) { 			
				retVal = data.getElementsByTagName(element)[0].childNodes[0].nodeValue;
			} else {
				retVal = null;
			}
			return retVal;
		} else {
			return null;
		}
	}	
	
	function parseXMLResponse(data) {
		
		//SL-21233: Document Retrieval Code Starts
		
		var dataContainer = new Array();
		var insuranceDetails = data.getElementsByTagName('insurance_details');
		
		for(var i=0; i<insuranceDetails.length; i++){
							
		dataContainer.push({
							pass_fail_status	 : parseXMLElement('pass_fail',data),
							resultMessage		 : parseXMLElement('message',data),
							// optional information. This will be null if the action has not set it
							addtional1    	 : parseXMLElement('addtional_01'+i,data),
							addtional2    	 : parseXMLElement('addtional_02'+i,data),
							addtional3    	 : parseXMLElement('addtional_03'+i,data),
							addtional4    	 : parseXMLElement('addtional_04'+i,data),
							addtional5    	 : parseXMLElement('addtional_05'+i,data),
							addtional6	  	 : parseXMLElement('addtional_06'+i,data),
							addtional7 	 	 : parseXMLElement('addtional_07'+i,data),
							addtional8	  	 : parseXMLElement('addtional_08'+i,data),
							addtional9	  	 : parseXMLElement('addtional_09'+i,data),
							addtional10   	 : parseXMLElement('addtional_10'+i,data),
							addtional11   	 : parseXMLElement('addtional_11'+i,data),
							addtional12   	 : parseXMLElement('addtional_12'+i,data),
							addtional13   	 : parseXMLElement('addtional_13'+i,data),
							addtional14   	 : parseXMLElement('addtional_14'+i,data),
							addtional15   	 : parseXMLElement('addtional_15'+i,data)
					}); 
		}
							
		return dataContainer;
		
		//SL-21233: Document Retrieval Code Ends
	}
	
	
	var displayPolicyCB = function(data) {
		
		//SL-21233: Document Retrieval Code Starts
		
		var passFailResult = new Array();
		
		passFailResult = parseXMLResponse(data);
		
		//jQuery.noConflict();
		//jQuery(document).ready(function($) {		
	
			//Provider Insurance Tab Modal Dialogs
			if (passFailResult[0].pass_fail_status == "1") { // Success
				
			//	$('#providerInsuranceDetailsModal').jqm({modal: true,opacity:true});
				if(button =="ADD" && sameDocInd!='1'){
					document.getElementById('insurancePolicyDto.agencyName').value = "";
					document.getElementById('insurancePolicyDto.agencyState').value = "AL";
					document.getElementById('insurancePolicyDto.carrierName').value = "";
					document.getElementById('insurancePolicyDto.policyNumber').value = "";					
					document.getElementById('insurancePolicyDto.agencyCountry').value = "";
					document.getElementById('insurancePolicyDto.amount').value = "";
					
					var useSameDocHTML8 = "<b style ='text-align: center; vertical-align: middle; font-size: 100%;'>There are no documents to show.</b>";
					
					document.getElementById('expander_Details').innerHTML = useSameDocHTML8;
					
					
					if(passFailResult[0].addtional4 != null){
						document.getElementById('insurancePolicyDto.amount').value = passFailResult[0].addtional4;
					}					
					if(passFailResult[0].addtional7 != '0' || passFailResult[0].addtional7 != 'null' && passFailResult[0].addtional8 != 'null'){	
						sameDocName = passFailResult[0].addtional8;					
						var useSameDocHTML1 = "<p class='policyNote' style='font-size:11px'><strong>Document on file:</strong> <span> <a href='jsp/providerRegistration/processInsuranceAction_displayTheDocument.action?docId="+passFailResult[0].addtional7+"' target='blank'>"+passFailResult[0].addtional8+"</a></span>";
						var useSameDocHTML2 = "<br/><div style='background-color:#f4f2f2'><b>Note:</b><br/><p style='margin-left: 50px;font-size:11px'>If your coverage is on the same insurance certificate,select "; 
						var useSameDocHTML3 = "'Use the <br/>same insurance certificate' and verify the information below:</p>";
					
						var useSameDocHTML4 = "<input type='checkbox' id='updateDocumentInd' label='checkbox test' name='insuranceInfoDto.updateDocumentInd' tabindex='5' onclick='javascript:checkFieldEnable("+passFailResult[0].addtional7+")' /><label style='font-size:11px'> Use the same insurance certificate</label><br/></div></p>";
						
						document.getElementById('documentUpload').innerHTML = useSameDocHTML1+useSameDocHTML2+useSameDocHTML3+useSameDocHTML4;
					}
					document.getElementById('modal2ConditionalChangeDate2').value = ""; 
					document.getElementById('modal2ConditionalChangeDate1').value = ""; 
					//Code added as part of Jira SL-20645 -To capture time while auditing insurance
					if(passFailResult[0].addtional13 != null){
						document.getElementById('insurancePolicyDto.auditTimeLoggingIdNew').value = passFailResult[0].addtional13;
						
						var auditTimeId=document.getElementById('credentialRequest.auditTimeLoggingIdNew');
						if(auditTimeId!=null && auditTimeId!='undefined')
							{
								document.getElementById('credentialRequest.auditTimeLoggingIdNew').value = passFailResult[0].addtional13;
							}
					}else{
						document.getElementById('insurancePolicyDto.auditTimeLoggingIdNew').value="";
					}
				}else if(button =="EDIT" || sameDocInd == '1'){
					if(passFailResult[0].addtional1 != null){					
						document.getElementById('insurancePolicyDto.agencyCountry').value = passFailResult[0].addtional1;
					}else{
						document.getElementById('insurancePolicyDto.agencyCountry').value="";
					}
					if(passFailResult[0].addtional2 != null){
						document.getElementById('insurancePolicyDto.agencyName').value = passFailResult[0].addtional2;
					}else{
						document.getElementById('insurancePolicyDto.agencyName').value="";
					}
					if(passFailResult[0].addtional3 != null){
						document.getElementById('insurancePolicyDto.agencyState').value = passFailResult[0].addtional3;
					}else{
						document.getElementById('insurancePolicyDto.agencyState').value ="";
					}
					if(passFailResult[0].addtional4 != null){
						if(sameDocInd == '1'){
							document.getElementById('insurancePolicyDto.amount').value = "";
						}else{
							document.getElementById('insurancePolicyDto.amount').value = passFailResult[0].addtional4;
						}
					}else{
						document.getElementById('insurancePolicyDto.amount').value = "";
					}
					if(passFailResult[0].addtional5 != null){
						document.getElementById('insurancePolicyDto.carrierName').value = passFailResult[0].addtional5;
					}else{
						document.getElementById('insurancePolicyDto.carrierName').value ="";
					}
					if(passFailResult[0].addtional6 != null){
						if(sameDocInd == '1'){
							document.getElementById('insurancePolicyDto.policyNumber').value = "";
						}else{
							document.getElementById('insurancePolicyDto.policyNumber').value = passFailResult[0].addtional6;
						}
					}else{
						document.getElementById('insurancePolicyDto.policyNumber').value = "";
					}
					if(passFailResult[0].addtional9 != null){
						document.getElementById('modal2ConditionalChangeDate2').value = passFailResult[0].addtional9;
					}else{
						document.getElementById('modal2ConditionalChangeDate2').value="";
					}
					if(passFailResult[0].addtional10 != null){
						document.getElementById('modal2ConditionalChangeDate1').value = passFailResult[0].addtional10;
					}else{
						document.getElementById('modal2ConditionalChangeDate1').value="";
					}
					
					//Code added as part of Jira SL-20645 -To capture time while auditing insurance
					if(passFailResult[0].addtional13 != null){
						document.getElementById('insurancePolicyDto.auditTimeLoggingIdNew').value = passFailResult[0].addtional13;
						
						var auditTimeId=document.getElementById('credentialRequest.auditTimeLoggingIdNew');
						if(auditTimeId!=null && auditTimeId!='undefined')
							{
								document.getElementById('credentialRequest.auditTimeLoggingIdNew').value = passFailResult[0].addtional13;
							}
					}else{
						document.getElementById('insurancePolicyDto.auditTimeLoggingIdNew').value="";
					}
					
					if(passFailResult[0].addtional7 != '0' || passFailResult[0].addtional7 != 'null' && passFailResult[0].addtional8 != 'null'){						
						if(sameDocInd == '1'){
							var useSameDocHTML1 = "<p class='policyNote' style='font-size:11px'><strong>Created Date: </strong><span id='created_date'>"+passFailResult[0].addtional14+"</span>&nbsp;&nbsp;<strong>Document on file:</strong> <span> <a href='jsp/providerRegistration/processInsuranceAction_displayTheDocument.action?docId="+passFailResult[0].addtional7+"' target='blank'>"+passFailResult[0].addtional8+"</a></span>";
							var useSameDocHTML2 = "<br/><div style='background-color:#f4f2f2'><b>Note:</b><br/><p style='margin-left: 50px;font-size:11px'>If your coverage is on the same insurance certificate,select "; 
							var useSameDocHTML3 = "'Use the <br/>same insurance certificate' and verify the information below:<p/>";
							var useSameDocHTML4 = "<input type='checkbox' id='updateDocumentInd' label='checkbox test' name='insuranceInfoDto.updateDocumentInd' tabindex='5' checked ='checked' onclick='javascript:checkFieldEnable("+passFailResult[0].addtional7+")' /><label style='font-size:11px'> Use the same insurance certificate</label><br/></div></p>";
							
							var useSameDocHTML5 = "";
							var useSameDocHTML6 = "<div class='innerScrollable' id = 'scrollableDiv'>";
							var useSameDocHTML7 = "</div>";
							var useSameDocHTML8 = "<b style ='text-align: center; vertical-align: middle; font-size: 100%;'>There are no documents to show.</b>";
							
							for(var j=1; j<passFailResult.length; j++){
							
								useSameDocHTML5 += "<div class='Docs'><li id='Details"+j+"'><img  id = 'minus"+j+"' class='imageMinus' src='/ServiceLiveWebUtil/images/icons/minusIcon.gif' />&ensp;<a href='jsp/providerRegistration/processInsuranceAction_displayTheDocument.action?docId="+passFailResult[j].addtional7+"' target='blank'>"+passFailResult[j].addtional8+"</a></li></div><br />";
							}
							
							document.getElementById('documentUpload').innerHTML = useSameDocHTML1+useSameDocHTML2+useSameDocHTML3+useSameDocHTML4;
							
							if (passFailResult.length > 1){
							document.getElementById('expander_Details').innerHTML = useSameDocHTML6+useSameDocHTML5+useSameDocHTML7;
							}else{
							document.getElementById('expander_Details').innerHTML = useSameDocHTML8;
							}
							
						}else{	
							if(passFailResult[0].addtional11 != '0' && passFailResult[0].addtional11 != 'null' && passFailResult[0].addtional12 != 'null'){	
								var useSameDocHTML1 = "<p class='policyNote' style='font-size:11px'><strong>Created Date: </strong><span id='created_date'>"+passFailResult[0].addtional14+"</span>&nbsp;&nbsp;<strong>Document on file:</strong> <span> <a href='jsp/providerRegistration/processInsuranceAction_displayTheDocument.action?docId="+passFailResult[0].addtional11+"' target='blank'>"+passFailResult[0].addtional12+"</a></span></p>";
								
							var useSameDocHTML5 = "";
							var useSameDocHTML6 = "<div class='innerScrollable'>";
							var useSameDocHTML7 = "</div>";
							var useSameDocHTML8 = "<b style ='text-align: center; vertical-align: middle; font-size: 100%;'>There are no documents to show.</b>";
							
							for(var j=1; j<passFailResult.length; j++){
							
								useSameDocHTML5 += "<div class='Docs'><li id='Details"+j+"'><img  id = 'minus"+j+"' class='imageMinus' src='/ServiceLiveWebUtil/images/icons/minusIcon.gif' />&ensp;<a href='jsp/providerRegistration/processInsuranceAction_displayTheDocument.action?docId="+passFailResult[j].addtional7+"' target='blank'>"+passFailResult[j].addtional8+"</a></li></div><br />";
							}
								
								document.getElementById('documentUpload').innerHTML = useSameDocHTML1;
								document.getElementById('docId').value = passFailResult[0].addtional11;
							
								if (passFailResult.length > 1){
								document.getElementById('expander_Details').innerHTML = useSameDocHTML6+useSameDocHTML5+useSameDocHTML7;
								}else{
								document.getElementById('expander_Details').innerHTML = useSameDocHTML8;
								}
							}
						}
					}	
				}
				
				document.getElementById('category').value = currentType;
				document.getElementById('credId').value = credentialId;
				document.getElementById('buttonType').value = button;										
		
			} 
			//SL-21233: Document Retrieval Code Ends
			
		//});				
	}
	
	function open_insurance_details_first(policyAmount,buttonType,credId,category)
	{     
    	var retVal;
        var s;
        if(category == 'General Liability'){
			s=document.getElementById("CBGLIAmount").value
	  	}else if (category == 'Auto Liability'){
			s=document.getElementById("VLIAmount").value
		}else if(category == 'Workmans Compensation'){ 
			s=document.getElementById("WCIAmount").value
		}    
	  	policyAmount = s;	      
      
     	var url = "processInsuranceAction_doLoad.action?&policyAmount="+ policyAmount+ "&buttonType="+ buttonType+ "&credId="+credId+ "&category="+category+"&currentDocId="+currentDocId +"&currentDocName="+currentDocName+ "&ts="+new Date().valueOf();
      	retVal=window.showModalDialog(url,'name','dialogWidth:700px;dialogHeight:500px');
     	 if(retVal==''){         
            return false;
      	}
      	if (retVal=='Refresh'){
             window.location.href = encodeURI(unescape(window.location.pathname));
      	}
     	return true;
	}

	function setPolicyModalAmount(category){
		var amount;
		if(category == 'General Liability'){
			amount=document.getElementById("CBGLIAmount").value;
		}else if (category == 'Auto Liability'){
			amount=document.getElementById("VLIAmount").value;
		}else if(category == 'Workmans Compensation'){ 
			amount=document.getElementById("WCIAmount").value;
		}  
	
		return amount; 
	}
		
	function setPolicyModalTitle(insuranceType){
		if(insuranceType == 'General Liability'){			
			document.getElementById('insurancePopupHeader').innerHTML = 'General Liability Information';
		}
		if (insuranceType == 'Auto Liability'){
			document.getElementById('insurancePopupHeader').innerHTML = 'Vehicle Liability Information';
		}
		if(insuranceType == 'Workmans Compensation'){ 
			document.getElementById('insurancePopupHeader').innerHTML ='Workers Compensation Information';
		}
		
	}

	function saveModal(val){
		var saveFlag =0;
		var frm=val.form;
		saveFlag = validateForm(saveFlag);
		if(saveFlag == 1){
			document.getElementById('errorMessages').style.display="block";
		}else if(!isSubmitted){		
			isSubmitted = true;
			frm.action='insurancePolicyDetailsAction_doSave.action';	
			frm.submit();	
		}
	}

	
	function validateForm(saveFlag){
		var errorMessage='';	
		var policyNumber = document.getElementById('insurancePolicyDto.policyNumber').value;
		var amount = document.getElementById('insurancePolicyDto.amount').value;		
		var carrierName = document.getElementById('insurancePolicyDto.carrierName').value;
		var agencyName = document.getElementById('insurancePolicyDto.agencyName').value;
		var agencyState = document.getElementById('insurancePolicyDto.agencyState').value; 
		var issueDate = document.getElementById('modal2ConditionalChangeDate2').value; 
		var expirationDate = document.getElementById('modal2ConditionalChangeDate1').value; 
		if(policyNumber == ''){
			errorMessage = "Please provide the policy number.<br/>";
		}
		if(amount == ''){
			errorMessage = errorMessage +"Please provide your current coverage amount for the insurance.<br/>";			
		}else{
			var isNumber = IsNumeric(amount);
			if(!isNumber){
				errorMessage = errorMessage +"Amount should be number.<br/>";
			}else{
				if(parseInt(amount) < 0){
					errorMessage = errorMessage +"Amount cannot be a negative number.<br/>";
				}else if(parseInt(amount) == 0){
					errorMessage = errorMessage +"Please provide your current coverage amount for the insurance.<br/>";
				}else if(amount.indexOf("\.") >= 0){
					var digitsAfterDecimal = amount.substr(amount.indexOf("\."), amount.length); 
					if(digitsAfterDecimal.length > 3){
						errorMessage = errorMessage +"Amount should contain only two digits after decimal.<br/>";
					}
				}
			}
		}
		if(carrierName == ''){
			errorMessage = errorMessage +"Please provide the name of the insurance carrier.<br/>";
		}	
		if(agencyName == ''){
			errorMessage = errorMessage +"Please provide the name of the insurance Agency.<br/>";
		}
		if(agencyState == ''){
			errorMessage = errorMessage +"Please select the state where your agency is located.<br/>";
		}	
		if(issueDate == ''){
			errorMessage = errorMessage +"Please select the issue date  for your current policy.<br/>";
		}
		if(expirationDate == ''){
			errorMessage = errorMessage +"Please select the expiration date  for your current policy.<br/>";
		}
		
		if(issueDate != '' && expirationDate != ''){
		    var issueDtCheckFlag = validateDatePickerValue(issueDate);
			if ( issueDtCheckFlag == false){
				errorMessage = errorMessage +"Please enter a valid Policy Issue Date.<br/>";
			}
			if ( validateDatePickerValue(expirationDate) == false){
				errorMessage = errorMessage +"Please enter a valid Policy Expiration date.<br/>";
			}else{
				var expirationDt = expirationDate.split('/');
				var expirationDte = new Date(expirationDt[2]+'/'+expirationDt[0]+'/'+expirationDt[1]);			
				
				//Check whether expiration date is greater than or equal to current date
				errorMessage = validateExpirationDate(expirationDte,errorMessage)				
			 	
			 	//Check whether expiration date is greater than the issue date
			 	if(issueDtCheckFlag == true){
					var issueDt = issueDate.split('/');
					var issueDte = new Date(issueDt[2]+'/'+issueDt[0]+'/'+issueDt[1]);
					if(issueDte>expirationDte){
						errorMessage = errorMessage +"Policy Expiration Date should be greater than Policy Issue date.<br/>";
					}
				}
			}
		}
		document.getElementById('errorMessages').innerHTML=errorMessage+docErrorMsg; 
		if(errorMessage != ''|| docErrorMsg !=''){
			saveFlag = 1;
		}else{
			saveFlag = 0;
		}
		return saveFlag;
	}

	function validateDatePickerValue(dateStr) {
	// Check if the date format is mm/dd/yyyy
		var datePat = /^(\d{1,2})(\/|-)(\d{1,2})\2(\d{4})$/;
		var matchArray = dateStr.match(datePat); 
		if (matchArray == null) {
			return false;
		}
		// parse date into variables
		month = matchArray[1]; 
		day = matchArray[3];
		year = matchArray[4];
		
		// check month range
		if (month < 1 || month > 12) { 
			return false;
		}
		
		// check day range
		if (day < 1 || day > 31) {
			return false;
		}
		if ((month==4 || month==6 || month==9 || month==11) && day==31) {
			return false;
		}
		
		// check for february 29th
		if (month == 2) { 
			var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
			if (day>29 || (day==29 && !isleap)) {
				return false;
	   		}
		}
		
		// date is valid
		return true;  
	}

	function IsNumeric(sText)
	{
   		var ValidChars = "0123456789.-";
   		var IsNumber=true;
   		var Char; 
   		for (i = 0; i < sText.length && IsNumber == true; i++) 
      	{ 
     	  Char = sText.charAt(i); 
    	  if (ValidChars.indexOf(Char) == -1) 
          {
         	IsNumber = false;
          }
     	}
   		return IsNumber;   
   }


	function checkExtension(filename,submitId){
		var hash = {
  		'.jpg':1 ,
 		'.pdf':1,
 		'.doc':1,
 		'.gif':1
	  	};
	  	var lastAmpersand = filename.lastIndexOf("&");
		if(lastAmpersand >  -1) {
			docErrorMsg = "Special character '&' in the file name is not permitted";
			return false;
		}
		var lastDotId = filename.lastIndexOf(".");
		if (lastDotId > -1) {
			var ext = filename.substr(lastDotId);
			ext = ext.toLowerCase();
			if (hash[ext]) {
				docErrorMsg = "";
				return true;
			}
		}
		docErrorMsg = "Please upload either a .jpg, .pdf, .doc or .gif.<br/>";
		
		return false;
	}
	
	function validateExpirationDate(expirationDte,errorMessage){
			var currentDate =new Date;	
			var dy = currentDate.getFullYear();
			var dm = currentDate.getMonth() + 1;
			var dd = currentDate.getDate();
			if ( dy < 1970 ) dy = dy + 100;
			var ys = new String(dy);
			var ms = new String(dm);
			var ds = new String(dd);
			if ( ms.length == 1 ) ms = "0" + ms;
			if ( ds.length == 1 ) ds = "0" + ds;
			ys = ys + "/" + ms + "/" + ds;
			var expDate = Date.parse(expirationDte);
			var todayDate = Date.parse(ys);
			if(expDate < todayDate){
				errorMessage = errorMessage +"Expiration date cannot be a past date.<br/>"; 
			}
			return errorMessage;	
	}
	
	function setCredentialStatus(category){
		if(category == 'General Liability'){	   		
			document.getElementById('provider_cred_status').innerHTML = 'Current status: '+document.getElementById('cbgli_cred_status').innerHTML;
		}
		if (category == 'Auto Liability'){
			document.getElementById('provider_cred_status').innerHTML = 'Current status: '+document.getElementById('vli_cred_status').innerHTML;
		}
		if(category == 'Workmans Compensation'){ 
			document.getElementById('provider_cred_status').innerHTML = 'Current status: '+document.getElementById('wci_cred_status').innerHTML;
		}
	}
	
	function checkInsuranceRadio(){
		if(document.getElementById('insuranceInfoDto.CBGLI').value == '0'){
			disableCBGLIBtn(); }
		if(document.getElementById('insuranceInfoDto.VLI').value == '0'){
			disableVLIBtn(); }
		if(document.getElementById('insuranceInfoDto.WCI').value == '0'){
			disableWCIBtn(); }
		
	}
	
	// SL-19459
	//Code added for new alert message for 'Remove Credential'
    function modalOpenAddCustomer(dialog) {
        dialog.overlay.fadeIn('fast', function() {
        dialog.container.fadeIn('fast', function() {
        dialog.data.hide().slideDown('slow');
        });
    });

}

 function modalOnClose(dialog) {
   dialog.data.fadeOut('slow', function() {
       dialog.container.slideUp('slow', function() {
           dialog.overlay.fadeOut('slow', function() {
               jQuery.modal.close(); 
           });
       });
   });
} 
	
	
	function removeCred()
	{
		
		 jQuery("#declinemodal").modal({
  			 onOpen: modalOpenAddCustomer,
  			 onClose: modalOnClose,
   			persist: true,
  			 	containerCss: ({ width: "625px", height: "140px", marginLeft: "-350px", top: "750px" ,border: "3px solid lightgrey"})
		});
		 
		 jQuery(".modalContainer").children("a.modalClose").removeClass("modalCloseImg");
		

}	
	
	
	function showRemoveWC()
	{
		var wciStatus = document.insuranceForm.insuranceInfoDtoWCI[0];
		var wciCredId= document.getElementById('wciCredId').value
		
		if (wciStatus.value == true){		 
			
			if(wciCredId>0)
			{
			jQuery("#declinemodal2").modal({
	  			 onOpen: modalOpenAddCustomer,
	  			 onClose: modalOnClose,
	   			persist: true,
	  			 	containerCss: ({ width: "625px", height: "150px", marginLeft: "-150px", top: "600px"})
			});
			 
			 jQuery(".modalContainer").children("a.modalClose").removeClass("modalCloseImg");
			
			}
		else
			{
			document.getElementById("WCIButton").disabled=true;
			document.getElementById('WCIAmount').value = 0;
			if(document.getElementById("lblWCIAmount")!= null){
				document.getElementById("lblWCIAmount").innerHTML = 0;}
			document.getElementById("WCIAmount").disabled=true;
			document.getElementById('insuranceInfoDto.WCI').value='0';
			document.insuranceForm.insuranceInfoDtoWCI[1].checked = true; 
			document.insuranceForm.insuranceInfoDtoWCI[0].checked = false;  
			} 
		}	
		
}
	
	function cancelRemoveWC()
	{
			document.insuranceForm.insuranceInfoDtoWCI[1].checked = false; 
			document.insuranceForm.insuranceInfoDtoWCI[0].checked = true;  
			jQuery.modal.impl.close(true);
	}

	
    function loadAdditionalInsurance(val) {
    	document.getElementById('errorMessages').style.display="none";	
    	document.getElementById('errorMessages').value="";
    	jQuery(document).ready(function($) {
    		$("otherInsDiv").hide();
    		$("input#insurancePolicyDto.agencyCountry1").value = "";
    		$("input#insurancePolicyDto.amount1").value = "";
    		$("input#insurancePolicyDto.agencyName1").value = "";
    		$("input#insurancePolicyDto.agencyState1").value = "";
    		$("input#insurancePolicyDto.carrierName1").value = "";
    		$("input#insurancePolicyDto.policyNumber1").value = "";
    		$("input#modal2ConditionalChangeDateAdditionalIss").value = "";
    		$("input#modal2ConditionalChangeDateExpAdditional").value = "";
    	});
    	
    	 for (var i = 0;  i < document.forms.length; i++)
         {
               var name = document.forms[i].name;          
               if (name == "insuranceForm")
               {
               	document.forms[i].action = val;
					document.forms[i].submit();
               }           
         } 
    }
    
    
    function saveInsurance(val){
		
		var saveFlag =0;
		var attachFlag =0;
		saveFlag = validateInsuranceForm(saveFlag, attachFlag);
		/*Changes related to SL-20301: Details based on LastUploadedDocument*/
		var issueDate = document.getElementById('modal2ConditionalChangeDateAdditionalIss').value; 
		var expirationDate = document.getElementById('modal2ConditionalChangeDateExpAdditional').value; 
		if(saveFlag == 1){
			document.getElementById('errorMessages1').style.display="block";
		}else if(!isSubmitted){	
			isSubmitted = true;
			document.getElementById('errorMessages1').style.display="none";
			for (var i = 0;  i < document.forms.length; i++)
	         {
	               var name = document.forms[i].name;          
	               if (name == "policyInformationForm")
	               {
	           		document.forms[i].action ="insurancePolicyDetailsAction_doSaveAdditionalInsurance.action?&issueDate1="+issueDate+"&expirationDate1="+expirationDate;
	               	document.forms[i].submit();
	               }           
	         } 
		}
	}
    
    function closeAdditionalInsForm(val)
    {
    	 for (var i = 0;  i < document.forms.length; i++)
         {
               var name = document.forms[i].name;          
               if (name == "policyInformationForm")
               {
               	document.forms[i].action = val;
					document.forms[i].submit();
               }           
         } 
    }

	
	function validateInsuranceForm(saveFlag, attachFlag){
		var errorMessage='';	
		var categoryType = document.getElementById('insurancePolicyDto.credentialCategoryId').value;
		var categoryDesc = document.getElementById('insurancePolicyDto.credentialCategoryDesc').value;
		var policyNumber = document.getElementById('insurancePolicyDto.policyNumber1').value;
		var amount = document.getElementById('insurancePolicyDto.amount1').value;		
		var carrierName = document.getElementById('insurancePolicyDto.carrierName1').value;
		var agencyName = document.getElementById('insurancePolicyDto.agencyName1').value;
		var agencyState = document.getElementById('insurancePolicyDto.agencyState1').value; 
		var issueDate = document.getElementById('modal2ConditionalChangeDateAdditionalIss').value; 
		var expirationDate = document.getElementById('modal2ConditionalChangeDateExpAdditional').value; 
		if(policyNumber == ''){
			errorMessage = "Please provide the policy number.<br/>";
		}
		if(amount == ''){
			errorMessage = errorMessage +"Please provide your current coverage amount for the insurance.<br/>";			
		}else{
			var isNumber = IsNumeric(amount);
			if(!isNumber){
				errorMessage = errorMessage +"Amount should be number.<br/>";
			}else{
				if(parseInt(amount) < 0){
					errorMessage = errorMessage +"Amount cannot be a negative number.<br/>";
				}else if(parseInt(amount) == 0){
					errorMessage = errorMessage +"Please provide your current coverage amount for the insurance.<br/>";
				}
				/*
				 * change for SL-20253. validation of 2 places after decimal
				 */
				else if(amount.indexOf("\.") >= 0){
					var digitsAfterDecimal = amount.substr(amount.indexOf("\."), amount.length); 
					if(digitsAfterDecimal.length > 3){
						errorMessage = errorMessage +"Amount should contain only two digits after decimal.<br/>";
					}
				}
			}
		}
		if(categoryType=='' || categoryType=='-1')
			{
			errorMessage = errorMessage +"Please select the category type.<br/>";
			}
		else
			{
			if(categoryType=='150')
				{
				if(categoryDesc == '')
					{
					errorMessage = errorMessage +"Please provide the name of other insurance type.<br/>";
					}
				}
				
			}
		if(carrierName == ''){
			errorMessage = errorMessage +"Please provide the name of the insurance carrier.<br/>";
		}	
		if(agencyName == ''){
			errorMessage = errorMessage +"Please provide the name of the insurance Agency.<br/>";
		}
		if(agencyState == ''){
			errorMessage = errorMessage +"Please select the state where your agency is located.<br/>";
		}	
		if(issueDate == ''){
			errorMessage = errorMessage +"Please select the issue date  for your current policy.<br/>";
		}
		if(expirationDate == ''){
			errorMessage = errorMessage +"Please select the expiration date  for your current policy.<br/>";
		}
		
		if(issueDate != '' && expirationDate != ''){
		    var issueDtCheckFlag = validateDatePickerValue(issueDate);
			if ( issueDtCheckFlag == false){
				errorMessage = errorMessage +"Please enter a valid Policy Issue Date.<br/>";
			}
			if ( validateDatePickerValue(expirationDate) == false){
				errorMessage = errorMessage +"Please enter a valid Policy Expiration date.<br/>";
			}else{
				var expirationDt = expirationDate.split('/');
				var expirationDte = new Date(expirationDt[2]+'/'+expirationDt[0]+'/'+expirationDt[1]);			
				
				//Check whether expiration date is greater than or equal to current date
				errorMessage = validateExpirationDate(expirationDte,errorMessage)				
			 	
			 	//Check whether expiration date is greater than the issue date
			 	if(issueDtCheckFlag == true){
					var issueDt = issueDate.split('/');
					var issueDte = new Date(issueDt[2]+'/'+issueDt[0]+'/'+issueDt[1]);
					if(issueDte>expirationDte){
						errorMessage = errorMessage +"Policy Expiration Date should be greater than Policy Issue date.<br/>";
					}
				}
			}
		}
		
		if(attachFlag == 1){
			var fileName = document.getElementById('insurancePolicyDto.file').value;
			if(fileName == ''||fileName=='[Select file]'){
				errorMessage = errorMessage +"No file selected. Please select a file to upload.<br/>";
			}
		}
		
		document.getElementById('errorMessages1').innerHTML=errorMessage+docErrorMsg; 
		
		if(errorMessage != ''|| docErrorMsg !=''){
			saveFlag = 1;
		}else{
			saveFlag = 0;
		}
		
		return saveFlag;
	}
	
	function removeAdditionalInsurance()
	{
		for (var i = 0;  i < document.forms.length; i++){
	           var name = document.forms[i].name;
	           if (name == "policyInformationForm")
	           {	
	           		document.forms[i].action = "insurancePolicyDetailsAction_doDelete.action";
					document.forms[i].submit();
	           }           
	     }   
	}
	
	function checkForOther(val){
	var categoryType = document.getElementById('insurancePolicyDto.credentialCategoryId').value;
	if(categoryType=='150')
		{
		document.getElementById('otherInsDiv').style.display='block';
		}
	else
		{
		document.getElementById('otherInsDiv').style.display='none';
		document.getElementById('insurancePolicyDto.credentialCategoryDesc').value='';
		}
	}
	
	function removeWCInsurance(val)
	{
		var vCredId=val;
		var url = "insurancePolicyDetailsAction_doDeleteWC.action?&credId="+ vCredId;
		for (var i = 0;  i < document.forms.length; i++){
	           var name = document.forms[i].name;
	           if (name == "insuranceForm")
	           {	
	           		document.forms[i].action = url;
					document.forms[i].submit();
	           }           
	     }   
	}
	
	
	function attachAdditionalInsForm(val)
    {
		var saveFlag =0;
		var attachFlag =1;
		saveFlag = validateInsuranceForm(saveFlag, attachFlag);
		if(saveFlag == 1){
			document.getElementById('errorMessages1').style.display="block";
		}else {
			document.getElementById('errorMessages1').style.display="none";
			var errorMessage='';
			var fileName = document.getElementById('insurancePolicyDto.file').value;
			if(fileName == ''||fileName=='[Select file]'){
				errorMessage = errorMessage +"No file selected. Please select a file to upload.<br/>";
				document.getElementById('errorMessages1').innerHTML=errorMessage;
				document.getElementById('errorMessages1').style.display="block";
			}
			else{
				val='insurancePolicyDetailsAction_attachDocument.action?&attachFlag='+ attachFlag;
		    	 for (var i = 0;  i < document.forms.length; i++)
		         {
		               var name = document.forms[i].name;          
		               if (name == "policyInformationForm")
		               {
		               	document.forms[i].action = val;
							document.forms[i].submit();
		               }           
		         } 
			}
			
		}
	
    }