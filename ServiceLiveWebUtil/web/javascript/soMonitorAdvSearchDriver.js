//Advanced Search driver
	function getSelectedListObject(strValue){
		  var objRegExp  = /^[1-9]/;
		  var list_str = "";
		  
		  if(objRegExp.test(strValue)){
		  		switch(parseInt(strValue)){
		  			case 10:	list_str = "check_number_list"; 	break;
		  			case 4:		list_str = "customer_name_list";	break;
		  			case 1:		list_str = "phone_list";			break;
		  			case 8:		list_str = "pro_firm_id_list";		break;
		  			case 3:		list_str = "so_id_list";			break;
		  			case 5:		list_str = "service_pro_id_list";	break;
		  			case 6:		list_str = "service_pro_name_list";	break;
		  			case 2:		list_str = "zipcode_list";			break;
		  			case 14:	list_str = "startDate_list";		break;
		  			case 15:	list_str = "endDate_list";			break;
		  			case 20:	list_str = "autocloseRules_list";   break;
		  		}
		  }else{
		  	list_str = "custom_list" ;
		  }
		 
		  return list_str;
	}
	
	function contains(a, obj) {
  		var i = a.length;
  			while (i--) {
    		if (a[i] === obj) {
      		return true;
    		}
  			}
  			return false;
		}
	
	function setToSelectedList(selectedTypeValue,selectedValue){
		var selectedType = document.getElementById("searchType");
		var autocloseRule = document.getElementById("autocloseRules");
		if(selectedTypeValue==''){
			alert("Please select 'Search Criteria'");
		}
		
		if(selectedValue==''){
			alert("Please select 'Search Criteria Value'");
		}
		var ruleAdded=true;
		if(selectedTypeValue=='20'){
	     	var autocloserule=new Array();
	     	var node_list =  jQuery('#searchSelectionsList > #'+'autocloseRules_list'+' > ul >li');
				if(null != node_list && node_list.length > 0){
	                for(var j=0; j < node_list.length; j++){
	                	var nodeid = node_list[j].id;
	                	var selValueHTML = node_list[j].innerHTML;	 
	                	selValue=nodeid.substring(nodeid.indexOf('@')+1);
						autocloserule.push(selValue);
					}
			}
			if(contains(autocloserule,autocloseRule.value)){
				alert("Rule already added.");
				ruleAdded=false;
				document.getElementById("autocloseRules").style.display="none";
				document.getElementById("searchValue").style.display="block";
				document.getElementById("autocloseWarning").style.display="none";
				document.getElementById("searchValue").value = '';
				document.getElementById("searchType").value = ''; 
				document.getElementById("pickDate").style.display = "none";
				document.getElementById("autocloseRules").selectedIndex=0;
	    	}

	    }
		if(selectedTypeValue!='' && selectedValue!='' && ruleAdded){
		var myobj = document.createElement('object');
		
		var listObj = getSelectedListObject(selectedTypeValue);
		
		if(listObj!=null && listObj.length > 0){
			var objRegExp  = /^[1-9]/;
			if(!objRegExp.test(selectedTypeValue))
			{
				
				myobj.setAttribute("id",selectedTypeValue);
			}
			else
			{
			myobj.setAttribute("id",selectedTypeValue+"_"+(jQuery('#searchSelectionsList > #'+listObj+' > ul >li').length));
			}
			if(!objRegExp.test(selectedTypeValue)) 
			{
				myobj.setAttribute("title",selectedType.options[selectedType.selectedIndex].text+"#"+selectedValue);
			}
			else 
			{
				myobj.setAttribute("title",selectedValue);
				
			}
			//check if autoclose rule
			if(selectedTypeValue=='20')
			{
				myobj.setAttribute("id",selectedTypeValue+"_"+(jQuery('#searchSelectionsList > #'+listObj+' > ul >li').length)+"@"+autocloseRule.value);
				
	         }
			selectableItemClick(myobj,listObj);
			
		}
		document.getElementById("autocloseRules").style.display="none";
		document.getElementById("searchValue").style.display="block";
		document.getElementById("autocloseWarning").style.display="none";
		document.getElementById("searchValue").value = '';
		document.getElementById("searchType").value = ''; 
		document.getElementById("pickDate").style.display = "none";
		document.getElementById("autocloseRules").selectedIndex=0;
		
		}
	}
	
	function validateFields(){
		document.getElementById('saveFilterWarning').style.display="none";
		var selectedType = document.getElementById("searchType");
		var selectedTypeValue = selectedType.value;
		//alert("validate()"+selectedTypeValue);
		var selectedValue = document.getElementById("searchValue").value;	
		selectedValue=jQuery.trim(selectedValue);
		//alert("validate()"+selectedValue);
		if(selectedTypeValue!='' && selectedValue!=''){
			//Phone Validation
				if(selectedTypeValue==1){
						var containsAlphabet = checkforAlphabets(selectedValue);
						if(containsAlphabet){
								alert( "Phone Number value can only be numeric.");
								return;
						}else{
							if (selectedValue.length == 12) {
								selectedValue = formatPhone(selectedValue);	
							}
						}						
					}	
				//Zip Code
				 if(selectedTypeValue=='2'){
				 	if (selectedValue.length == 5) {
						var containsAlphabet = checkforAlphabets(selectedValue);
						if(containsAlphabet){
							alert( "Zip Code value can only be numeric.");
							return;		
						}
					}else{
						alert("Zip Code should have  5 digits");
						return;
					}
				}
				//SO-ID formatting
				if(selectedTypeValue=='3'){
						var containsAlphabet = checkforAlphabets(selectedValue);
						if(containsAlphabet){
							alert( "SO ID value can only be numeric.");
							return;		
						}else{
							selectedValue = formatSOId(selectedValue);	
						}
				}				
				//Search by Name
		/*		 if(selectedTypeValue=='4'||selectedTypeValue=='6'){
					var isNumeric = checkNumInNames(selectedValue);
					if(isNumeric){
						alert( "Search by name value can only be alphabets and space.");
						return;				
					}			
				}*/
			}
		
			setToSelectedList(selectedTypeValue,selectedValue);
	}	
	function checkforAlphabets(searchValue){
		var tempString=false;
        var regExp = /^[A-Za-z]$/;
        if(searchValue != null && searchValue != ""){
          for(var i = 0; i < searchValue.length; i++){
            if (searchValue.charAt(i).match(regExp)){
              tempString=true;
              break;
            }
          }
          if( tempString==true){
          	return true;
          }else{
          	return false;
          }
        }
        else{
          return false;
        }			
	}
	var statequery = "",skillquery = "", marketquery = "", sermaincatgquery = "", sersubcatgquery = "",customquery = "",
			checkNumberQuery = "", customerNameQuery = "", phoneQuery = "", proFirmIdQuery = "", serviceOrderIdQuery = "", 
			statusQuery = "",serviceProIdQuery = "", serviceProNameQuery = "", zipcodeQuery = "",
			startDateQuery = "", endDateQuery = "",autocloseRuleQuery= "", orderAcceptanceQuery = "", pricingTypeQuery = "",
			resAssignmentQuery = "", postingMethodQuery = "",pendingRescheduleQuery ="",closureMethodQuery = "";	
			
	 function searchCriteriaTreeNodesParser(searchform){
		var selectedSearchCriteria = document.getElementById("selectedSearchCriteria");
		statequery = ""; skillquery = ""; marketquery = ""; sermaincatgquery = ""; sersubcatgquery = ""; customquery = "";
			checkNumberQuery = ""; customerNameQuery = ""; phoneQuery = ""; proFirmIdQuery = ""; serviceOrderIdQuery = ""; 
			statusQuery = "";serviceProIdQuery = ""; serviceProNameQuery = ""; zipcodeQuery = "",
			startDateQuery = "", endDateQuery = "",autocloseRuleQuery= "", orderAcceptanceQuery = "", pricingTypeQuery = "",
			resAssignmentQuery = "", postingMethodQuery = "" ,pendingRescheduleQuery ="",closureMethodQuery = "";

		if(null != selectedSearchCriteria){ // if atleast one search criteria exist
			var state_list = jQuery('#searchSelectionsList > #state_list > ul >li');
			if(null != state_list){
                for(var i=0; i < state_list.length; i++){
                    statequery = i==0 ? statequery + getNodeId(state_list[i].id) : statequery + "|" + getNodeId(state_list[i].id);
                }
                //document.getElementById("stateQuery").value = statequery;
                searchform.stateVals.value = statequery;
			}
 
			var skill_list = jQuery('#searchSelectionsList > #skill_list > ul >li');
			if(null != skill_list){
                for(var i=0; i < skill_list.length; i++){
                	var selValueHTML = skill_list[i].innerHTML;                	
                	//var selValue = selValueHTML.substr(0,selValueHTML.indexOf("<"));	
                    
                    var ind = selValueHTML.indexOf("</");	                	
	                var len = ind-6;
	                var selValue = selValueHTML.substr(6,len);	                	
                    skillquery = i==0 ? skillquery + selValue : skillquery + "|" + selValue;
                }
                //document.getElementById("skillQuery").value = skillquery;
                searchform.skillVals.value = skillquery;
			}
						
			var market_list =  jQuery('#searchSelectionsList > #market_list > ul >li');
			if(null != market_list){
                for(var i=0; i < market_list.length; i++){
                    marketquery = i==0 ? marketquery + getNodeId(market_list[i].id) : marketquery + "|" + getNodeId(market_list[i].id);
                }
                //document.getElementById("marketQuery").value = marketquery;
                searchform.marketVals.value = marketquery;
			}

	 		var category_list = jQuery('#searchSelectionsList > #category_list > ul >li');
	 		var secLevelExist = false,thirdLevelExist = false;
	 		if(null != category_list){
	 			for(var i=0; i < category_list.length ; i++){
				 	var secLevelNodes = jQuery('#'+category_list[i].id+' > ul > li');
			 		for(var j=0; j < secLevelNodes.length ; j++){
			 			secLevelExist = true;
			 			var thirdLevelNodes = jQuery('#'+secLevelNodes[j].id+' > ul > li');
			 			for(var k=0; k < thirdLevelNodes.length ; k++){
			 				thirdLevelExist = true;
			 				sersubcatgquery =  sersubcatgquery.length == 0 ? sersubcatgquery + getNodeId(thirdLevelNodes[k].id) : 
			 										 sersubcatgquery + "|" + getNodeId(thirdLevelNodes[k].id);
			 			}
			 			if(!thirdLevelExist){
							sersubcatgquery =  sersubcatgquery.length == 0  ? sersubcatgquery + getNodeId(secLevelNodes[j].id)  : 
			 										 sersubcatgquery + "|" + getNodeId(secLevelNodes[j].id);			 			
			 			}
			 			thirdLevelExist = false;
			 		} 
	 				if(!secLevelExist){
						sermaincatgquery =  sermaincatgquery.length == 0  ? sermaincatgquery + getNodeId(category_list[i].id)  : 
	 										  sermaincatgquery + "|" + getNodeId(category_list[i].id);
	 				}
					secLevelExist = false;
				} 	
       			//document.getElementById("sermaincatgQuery").value = sermaincatgquery;        
       			//document.getElementById("sersubcatgQuery").value = sersubcatgquery;
       			searchform.mainCatId.value = sermaincatgquery;
       			searchform.catAndSubCatId.value = sersubcatgquery;
			}
	 		
	 	
	 		var status_list = jQuery('#searchSelectionsList > #status_list > ul >li');
	 		secLevelExist = false;
	 		if(null != status_list){
	 			for(var i=0; i < status_list.length ; i++){
				 	var secLevelNodes = jQuery('#'+status_list[i].id+' > ul > li');
			 		for(var j=0; j < secLevelNodes.length ; j++){
			 			secLevelExist = true;
			 			statusQuery = statusQuery.length==0 ? (j == 0 ? getNodeId(status_list[i].id) + ":" + 
			 															getNodeId(secLevelNodes[j].id) 
			 														  : statusQuery + "|" + getNodeId(secLevelNodes[j].id))
			 												: (j == 0 ? statusQuery + "#" + getNodeId(status_list[i].id) + ":" + 
			 															getNodeId(secLevelNodes[j].id) 
			 														  : statusQuery + "|" + getNodeId(secLevelNodes[j].id));
			 		} 
	 				if(!secLevelExist){
	 					statusQuery = statusQuery.length==0 ? statusQuery + getNodeId(status_list[i].id) : 
	 						statusQuery + "#" + getNodeId(status_list[i].id);
	 				}
					secLevelExist = false;
				} 	
       			//document.getElementById("statusQuery").value = statusQuery;
	 			searchform.statusVals.value = statusQuery;
			}
	 		
	 		//order acceptance
	 		var order_acceptance = jQuery('#searchSelectionsList > #order_acceptance > ul >li');
			if(null != order_acceptance){
                for(var i=0; i < order_acceptance.length; i++){
                    orderAcceptanceQuery = i==0 ? orderAcceptanceQuery + getNodeId(order_acceptance[i].id) : orderAcceptanceQuery + "|" + getNodeId(order_acceptance[i].id);
                }
                searchform.acceptanceVals.value = orderAcceptanceQuery;
			}
			
			//pricing type
	 		var pricing_type = jQuery('#searchSelectionsList > #pricing_type > ul >li');
			if(null != pricing_type){
                for(var i=0; i < pricing_type.length; i++){
                    pricingTypeQuery = i==0 ? pricingTypeQuery + getNodeId(pricing_type[i].id) : pricingTypeQuery + "|" + getNodeId(pricing_type[i].id);
                }
                searchform.pricingVals.value = pricingTypeQuery;
			}
			
			//resource assignment
	 		var res_assignment = jQuery('#searchSelectionsList > #res_assignment > ul >li');
			if(null != res_assignment){
                for(var i=0; i < res_assignment.length; i++){
                    resAssignmentQuery = i==0 ? resAssignmentQuery + getNodeId(res_assignment[i].id) : resAssignmentQuery + "|" + getNodeId(res_assignment[i].id);
                }
                searchform.assignmetVals.value = resAssignmentQuery;
			}
			
			//posting method
	 		var posting_method = jQuery('#searchSelectionsList > #posting_method > ul >li');
			if(null != posting_method){
                for(var i=0; i < posting_method.length; i++){
                    postingMethodQuery = i==0 ? postingMethodQuery + getNodeId(posting_method[i].id) : postingMethodQuery + "|" + getNodeId(posting_method[i].id);
                }
                searchform.postingVals.value = postingMethodQuery;
			}
			
			//R12_1
			//SL-20362
			//pending reschedule
	 		var pending_reschedule = jQuery('#searchSelectionsList > #pending_reschedule > ul >li');
			if(null != pending_reschedule){
                for(var i=0; i < pending_reschedule.length; i++){
                	pendingRescheduleQuery = i==0 ? pendingRescheduleQuery + getNodeId(pending_reschedule[i].id) : pendingRescheduleQuery + "|" + getNodeId(pending_reschedule[i].id);
                }
                searchform.pendingRescheduleVals.value = pendingRescheduleQuery;
			}
			//R12_1
			//SL-20554
			//Closure method
	 		var closure_method = jQuery('#searchSelectionsList > #closure_method > ul >li');
			if(null != closure_method){
                for(var i=0; i < closure_method.length; i++){
                	closureMethodQuery = i==0 ? closureMethodQuery + getNodeId(closure_method[i].id) : closureMethodQuery + "|" + getNodeId(closure_method[i].id);
                }
                searchform.closureMethodVals.value = closureMethodQuery;
			}
			
			
 			var query_list = ["check_number_list","customer_name_list","phone_list","pro_firm_id_list","so_id_list",
							  "service_pro_id_list","service_pro_name_list","zipcode_list","startDate_list","endDate_list","autocloseRules_list"];
     		
     		for(var i=0; i < query_list.length; i++){
     			var node_list =  jQuery('#searchSelectionsList > #'+query_list[i]+' > ul >li');
				if(null != node_list && node_list.length > 0){
	                for(var j=0; j < node_list.length; j++){
	                	var nodeid = node_list[j].id;
	                	var selId = nodeid.substring(0,nodeid.indexOf('_'));
						if(selId == '' || selId == null) selId = nodeid;	                	
	                	var selValueHTML = node_list[j].innerHTML;	 
	                	var ind = selValueHTML.indexOf("</");
	               		var len = ind-6;
	                	var selValue = selValueHTML.substr(6,len);
	                		 
	              		if(selId=='20')
						{
							selValue=nodeid.substring(nodeid.indexOf('@')+1);
							
						}
	                	                	
	                	setQueryString(searchform,j,selId,selValue);
	                }
	              
	                setQueryString(searchform,0,selId);
				}
     		}     		
 
 			var custom_list = jQuery('#searchSelectionsList > #custom_list > ul >li');
			if(null != custom_list){
                for(var i=0; i < custom_list.length; i++){
                	var selValueHTML = custom_list[i].innerHTML;
                	var selValue = custom_list[i].id;
                	selValue = selValue.substr(3,selValue.length);   	
                	var x = selValueHTML.indexOf("#");
                  	var y = selValueHTML.indexOf("</");                  	
                  	var l = y-x;                  	
                  	var val = selValueHTML.substr(x,l);                  
                    selValue += selValueHTML.substr(x,l);  
                   
	                //var selValue = selValueHTML.substr(0,selValueHTML.indexOf("<"));
	                
                    customquery = i==0 ? customquery + selValue : customquery + "|" + selValue;
                }
              //  document.getElementById("customQuery").value = customquery;
                searchform.custRefVals.value = customquery;
			} 
			var criteria = document.getElementById("searchSelections").innerHTML;
			var jsonCriteria = parseSearchCriteria();
			criteria=criteria.replace(/\"/g,'\'');
			//searchform.selectedSearchCriteria.value = criteria;
			searchform.selectedSearchCriteria.value = jsonCriteria;
			
		}
	}
	
	function setQueryString(searchform,indexInt,selId,selValue){
		switch(parseInt(selId)){
  			case 14:	if(null == selValue || "" == selValue){
  							//document.getElementById("startDateQuery").value = startDateQuery;
  							searchform.startDate.value = startDateQuery;
  						}else{
  							startDateQuery = indexInt==0 ? startDateQuery + selValue : startDateQuery + "|" + selValue;
  						}
  				break;
  			case 15:	if(null == selValue || "" == selValue){
  							//document.getElementById("endDateQuery").value = endDateQuery;
  							searchform.endDate.value = endDateQuery;
  						}else{
  							endDateQuery = indexInt==0 ? endDateQuery + selValue : endDateQuery + "|" + selValue;
  						}
  				break;
  			case 10:	if(null == selValue || "" == selValue){
  							//document.getElementById("checkNumberQuery").value = checkNumberQuery;
  							searchform.checkNumberVals.value = checkNumberQuery;
  						}else{	
  							checkNumberQuery = indexInt==0 ? checkNumberQuery + selValue : checkNumberQuery + "|" + selValue;
  						}	
  				break;
  			case 4:		if(null == selValue || "" == selValue){
  							//document.getElementById("customerNameQuery").value = customerNameQuery;
  							searchform.customerNameVals.value = customerNameQuery;
  						}else{
  							customerNameQuery = indexInt==0 ? customerNameQuery + selValue : customerNameQuery + "|" + selValue;
  						}	
  				break;
  			case 1:		if(null == selValue || "" == selValue){
  							//document.getElementById("phoneQuery").value = phoneQuery;
  							searchform.phoneVals.value = phoneQuery;
  						}else{
  							phoneQuery = indexInt==0 ? phoneQuery + selValue : phoneQuery + "|" + selValue;
  						}
  				break;
  			case 8:		if(null == selValue || "" == selValue){
  							//document.getElementById("proFirmIdQuery").value = proFirmIdQuery;
  							searchform.providerFirmIdVals.value = proFirmIdQuery;
  						}else{
  							proFirmIdQuery = indexInt==0 ? proFirmIdQuery + selValue : proFirmIdQuery + "|" + selValue;
  						}
  				break;
  			case 3:		if(null == selValue || "" == selValue){
  							//document.getElementById("serviceOrderIdQuery").value = serviceOrderIdQuery;
  							
  							searchform.serviceOrderIdVals.value = serviceOrderIdQuery;
  						}else{
  							serviceOrderIdQuery = indexInt==0 ? serviceOrderIdQuery + selValue : serviceOrderIdQuery + "|" + selValue;
  							}	
  				break;
  			case 5:		if(null == selValue || "" == selValue){
  							//document.getElementById("serviceProIdQuery").value = serviceProIdQuery;
  							searchform.serviceProIdVals.value = serviceProIdQuery;
  						}else{	serviceProIdQuery
  							serviceProIdQuery = indexInt==0 ? serviceProIdQuery + selValue : serviceProIdQuery + "|" + selValue;	
  						}	
  				break;
  			case 6:		if(null == selValue || "" == selValue){
  							//document.getElementById("serviceProNameQuery").value = serviceProNameQuery;
  							searchform.serviceProNameVals.value = serviceProNameQuery;
  						}else{
  							serviceProNameQuery = indexInt==0 ? serviceProNameQuery + selValue : serviceProNameQuery + "|" + selValue;
  						}
  				break;
  			case 2:		if(null == selValue || "" == selValue){
  							//document.getElementById("zipcodeQuery").value = zipcodeQuery;
  							searchform.zipCodeVals.value = zipcodeQuery;
  						}else{
  							zipcodeQuery = indexInt==0 ? zipcodeQuery + selValue : zipcodeQuery + "|" + selValue;
  						}
  				break;
  			case 20:		if(null == selValue || "" == selValue){
  							//document.getElementById("serviceOrderIdQuery").value = serviceOrderIdQuery;
  							
  							searchform.autocloseRuleVals.value = autocloseRuleQuery;
  						}else{
  							autocloseRuleQuery = indexInt==0 ? autocloseRuleQuery + selValue : autocloseRuleQuery + "|" + selValue;
  								
  						}	
  				break;
  		} 
	}
	
  	function getStringQuery(i,node,strQuery){
  		var innernode = node.innerHTML;
        return (i==0 ? strQuery + innernode : strQuery + "|" + innernode);
  	}
  	 
  	function getNodeId(nodeId){
  		return (nodeId.substr(nodeId.indexOf("l") + 1 , nodeId.length));  		
  		
  	}
  	
  	function clearselections(){
  		var iFrameRef = document.getElementById("SearchmyIframe");
		var searchform = iFrameRef.contentWindow.document.getElementById("searchHandler"); 
		document.getElementById("selectedSearchCriteria").value = '';
		searchform.selectedSearchCriteria.value = '';
		var result = iFrameRef.contentWindow.document.getElementById("resultSet");
		result.innerHTML='';
  	}
  	
  	function parseSearchCriteria (){
  			var savedCriteriaString = "[";
  	  		var cntr=0;
  	  	 	jQuery('#searchSelectionsList > li').each(function(){
  	  	  			if(jQuery(this).css('display') != 'none'){
  	  	  				if(cntr>0){
  	  	  				savedCriteriaString+= ',{';
  	  	  				}else{
  	  	  				savedCriteriaString+= '{';
  	  	  				}
  	  	  				savedCriteriaString +='"id":"'+ jQuery(this).attr('id')+'","values":['
  	  	  			jQuery(this).children('ul').each(function(i){
  	  	  					if(i>0) {
  	  	  						savedCriteriaString += ",{";
  		  					}else{
  		  						savedCriteriaString += "{";
  		  					}
  	  	  					if(jQuery(this).attr('id')!='' && jQuery(this).attr('id')!=null){
  	  	  					savedCriteriaString += '"id":"'+jQuery(this).attr('id')+'","values":[';
  	  	  					}else{
  	  	  					savedCriteriaString += '"values":[';
  	  	  					}
  	  	  				jQuery(this).children('li').each(function(j){
  	  	  					if(j>0) {
  							savedCriteriaString += ",{";
  	  	  					}else{
  	  	  					savedCriteriaString += "{";
  	  	  					}
  	  	  					savedCriteriaString +='"id":"'+ jQuery(this).attr('id')+'","name":"'+jQuery(this).children('span').html()+'","values":[';
  		  	  				jQuery(this).children('ul').each(function(k){
  		  	  					if(k>0) {
  		  	  						savedCriteriaString += ",{";
  		  	  					}else{
  		  	  					savedCriteriaString += "{";
  		  	  					}
  		  	  				savedCriteriaString +='"id":"'+ jQuery(this).attr('id')+'","values":[';
  		  	  					jQuery(this).children('li').each(function(l){
  		  	  						if(l>0) {
  		  							savedCriteriaString += ",{";
  		  							}else{
  		  							savedCriteriaString += "{";
  		  							}
  		  	  						savedCriteriaString +='"id":"'+ jQuery(this).attr('id')+'","name":"'+jQuery(this).children('span').html()+'","values":[';
  		  	  						jQuery(this).children('ul').each(function(m){
  		  	  							if(m>0) {
  		  	  							savedCriteriaString += ",{";
  		  	  							}else{
  		  	  							savedCriteriaString += "{";
  		  	  							}
  		  	  							savedCriteriaString +='"id":"'+ jQuery(this).attr('id')+'","values":[';
  		  	  							jQuery(this).children('li').each(function(n){
  		  	  								if(n>0){
  		  	  								savedCriteriaString += ',';
  		  	  								}
  		  	  							savedCriteriaString +='{"id":"'+ jQuery(this).attr('id')+'","name":"'+jQuery(this).children('span').html()+'"}';
  		  	  							});
  		  	  						savedCriteriaString += ']';
  		  	  						savedCriteriaString += '}';
  		  	  						});
  		  	  					savedCriteriaString += ']';
  		  	  					savedCriteriaString += '}';
  		  	  					});
  		  	  				savedCriteriaString += ']';
  		  	  				savedCriteriaString += '}';
  		  	  				});
  		  	  			savedCriteriaString += ']';
  		  	  			savedCriteriaString += '}';
  	  	  				});
  	  	  			savedCriteriaString += ']';	
  	  	  			savedCriteriaString += '}';
  	  	  			});
  	  	  			savedCriteriaString += ']';
  	  	  			savedCriteriaString += '}';	
  	  	  		cntr++;
  	  	  		}
  	  	  	});
  	  	 	savedCriteriaString += ']';
  		return encodeURI(savedCriteriaString);
  	}
  	
  	
  	function repopulateSearchCriteria(s){
  		jQuery(document).ready(function($){
  			var arr = eval(decodeURI(s));
  			jQuery('#searchSelectionsList').find('a').click(); //this removes existing selections
  			$(arr).each(function(i){
  				$('#'+arr[i].id).show(); //show the main category
  				if(typeof arr[i].values == 'object'){
  					var arr2 = arr[i].values;
  					$(arr2).each(function(j){
  						if(typeof arr2[j].values == 'object'){
  							var arr3 = arr2[j].values;
  							$(arr3).each(function(k){
  								var dHtml = '<li id="'+arr3[k].id+'" onclick="toggleChildList(this,event);"><span>'+arr3[k].name+'</span><a href="#" onclick="removeElement(this);return false;">[x]</a>';
  								$('#'+arr[i].id+'>ul').append(dHtml); 
  								if(typeof arr3[k].values == 'object'){
  									var arr4 = arr3[k].values;
  									$(arr4).each(function(l){
  										if(arr4[l].id.indexOf('ul') == 0){
  											var dHtml = '<ul id='+arr4[l].id+'></ul>';	
  											$('#'+arr[i].id).find('#'+arr3[k].id).append(dHtml);
  										}
  										if(typeof arr4[l].values == 'object'){
  											var arr5 = arr4[l].values;
  											$(arr5).each(function(m){
  				  								var dHtml = '<li id="'+arr5[m].id+'" onclick="toggleChildList(this,event);"><span>'+arr5[m].name+'</span><a href="#" onclick="removeElement(this);return false;">[x]</a>';
  												$('#'+arr[i].id).find('#'+arr4[l].id).append(dHtml);
  												$('#'+arr[i].id).find('#'+arr3[k].id).css('list-style-image',minusIcon);
  												if(typeof arr5[m].values == 'object'){
  													var arr6 = arr5[m].values;
  													$(arr6).each(function(n){
  														if(arr6[n].id.indexOf('ul') == 0){
  															var dHtml = '<ul id='+arr6[n].id+'></ul>';	
  				  											$('#'+arr[i].id).find('#'+arr5[m].id).append(dHtml);
  														}
  														if(typeof arr6[n].values == 'object'){
  															var arr7 = arr6[n].values;
  															$(arr7).each(function(o){
  																var dHtml = '<li id="'+arr7[o].id+'" onclick="toggleChildList(this,event);"><span>'+arr7[o].name+'</span><a href="#" onclick="removeElement(this);return false;">[x]</a>';
  																$('#'+arr[i].id).find('#'+arr6[n].id).append(dHtml);
  																$('#'+arr[i].id).find('#'+arr5[m].id).css('list-style-image',minusIcon);
  															});
  														}
  														
  													});
  												}
  											});
  										}
  									});
  								}
  							});
  						}
  					});
  				}
  			});
  		});
  	}
  	
  	
  	function sendData(){
		document.getElementById('saveFilterWarning').style.display="none";

  		//parseSearchCriteria();
  		if(document.getElementById("isInitialLoad") != null){
	  		document.getElementById("isInitialLoad").value = 1;
	  	}
 		var iFrameRef = document.getElementById("SearchmyIframe");
		var result = iFrameRef.contentWindow.document.getElementById("resultSet");
		if(result!=null){
		result.innerHTML="<img src='"+newco.jsutils.getStaticContext()+"/images/spinner_small.gif'/>";
		}
		var searchForm = null;
		if(!dojo.isIE){
			searchForm = iFrameRef.contentDocument.getElementById("searchHandler");
		}else{
			searchForm = iFrameRef.contentWindow.document.getElementById("searchHandler");		
		}
		searchCriteriaTreeNodesParser(searchForm);
   		var selectedType = document.getElementById("searchType");
		var selectedTypeData = selectedType.value;
		document.getElementById("searchValue").value = jQuery.trim(document.getElementById("searchValue").value);
		var searchValue = document.getElementById("searchValue").value;
		searchForm.searchType.value = selectedTypeData;
		searchForm.searchValue.value = searchValue;
		searchForm.isSearchClicked.value = 1;
		searchForm.selectedFilterName.value = "";
		try {
		searchForm.status.value = document.getElementById("statusIdSearch").options[document.getElementById("statusIdSearch").selectedIndex].value;
		searchForm.subStatus.value = document.getElementById("subStatusIdSearch").options[document.getElementById("subStatusIdSearch").selectedIndex].value;
		}catch(e){}
		searchForm.submit();

	}
	
	function formatSOId(searchValue)
	{
		var success = true;
				var pattern1 = /(^\d{3}-\d{4}-\d{4}-\d{2}$)/;
	     if (!pattern1.test(searchValue)) 
	     {
		     var pattern2 = /^(\d{3})(\d{4})(\d{4})(\d{2})$/;
		     searchValue = searchValue.replace(/-/g, "");
		     searchValue = searchValue.replace(pattern2,"$1-$2-$3-$4");
         }
	return searchValue;
	}
	function formatPhone(searchValue)
	{
		var success = true;
		var patternhyphen = /(^\d{3}-\d{3}-\d{4}$)/;
		var patterndot = /(^\d{3}.\d{3}.\d{4}$)/;
		var pattern2 = /^(\d{3})(\d{3})(\d{4})$/;		
	     if (patternhyphen.test(searchValue)) 
	     {		     
		     searchValue = searchValue.replace(/-/g, "");
		     searchValue = searchValue.replace(pattern2,"$1$2$3");
         }
         if (patterndot.test(searchValue)) 
	     {		     
		     searchValue = searchValue.replace(/\./g, "");		     
		     searchValue = searchValue.replace(pattern2,"$1$2$3");
         }
		return searchValue;
	}
	function checkNumInNames(searchValue)
	{
		var numChars = "0123456789";
		var value;
		var isNum = false;
			
		for(i=0;i<searchValue.length &&isNum == false;i++)
		{	
			Value = searchValue.charAt(i);
			if(numChars.indexOf(Value)!= -1){	
				isNum = true;
			}
		}		
		return isNum;
	}
	function clearSearchData(tabName)
	{
		var searchType = document.getElementById("searchType");
		searchType.selectedIndex = 0;
		
		var searchValue = document.getElementById("searchValue");
		searchValue.value = "";		
		
		var status = document.getElementById("statusId"+tabName);
		status.selectedIndex = 0;
		
		var subStatus = document.getElementById("subStatusId"+tabName);
		subStatus.selectedIndex = 0;
		
	}


	var minusIcon = "url(/ServiceLiveWebUtil/images/icons/minusIcon.gif)";
	var plusIcon = "url(/ServiceLiveWebUtil/images/icons/plusIcon.gif)";
	var blankIcon = "url(/ServiceLiveWebUtil/images/icons/blankListIcon.gif)";
	var marketsLoaded = false;
	function loadMarketList(obj){
		jQuery('#marketsList').toggle(function(){
			if (jQuery(this).css('display') == 'block'){
				jQuery(obj).css('list-style-image',minusIcon);
			}else{
				jQuery(obj).css('list-style-image',plusIcon);
			}
		});
	}
	
	var listLoaded;
	function loadMarketListSub(obj,listId,sIndex,eIndex){
			if(listLoaded != listId){
				jQuery('#'+listId).load('searchMarketLoader.action?sIndex='+sIndex+'&eIndex='+eIndex);
				listLoaded = listId;
				jQuery(obj).find('img').attr('src','/ServiceLiveWebUtil/images/icons/minusIcon.gif');
			}else if (jQuery('#'+listId).css('display') == 'block'){
				jQuery('#'+listId).hide();
				jQuery(obj).find('img').attr('src','/ServiceLiveWebUtil/images/icons/plusIcon.gif');
			}else{
				jQuery('#'+listId).show();
				jQuery(obj).find('img').attr('src','/ServiceLiveWebUtil/images/icons/minusIcon.gif');
			}
	}
	
		function showDateCal(){
		
		var searchValue = document.getElementById("searchValue");
		searchValue.value = "";
		if(jQuery("#searchType").val()=='20'){
			document.getElementById('saveFilterWarning').style.display="none";

			jQuery("#autocloseWarning").css("display","block");
			jQuery("#searchValue").css("display","none");
			jQuery("#autocloseRules").css("display","block");
			document.getElementById("autocloseRules").selectedIndex=0;
		}
		else{
		
		jQuery("#autocloseWarning").css("display","none");
		jQuery("#autocloseRules").css("display","none");
		jQuery("#searchValue").css("display","block");
		}
					
		if(jQuery("#searchType").val()=='14'||jQuery("#searchType").val()=='15'){
			jQuery("#pickDate").css("display","block");
			jQuery("#searchValue").val('');
		}else{
			jQuery("#pickDate").css("display","none");
			
		}
		
	}
	
	function setAutocloseSearchValue(){
	
	var searchValue = document.getElementById("searchValue");
	searchValue.value = "";	
	var autocloseRule = document.getElementById("autocloseRules");
	var selected_index = autocloseRule.selectedIndex;
	var text = autocloseRule.options[selected_index].text;
	searchValue.value= text;
	
	}
	
	function showCalendar(){
		
		if((jQuery("#searchType").val()=='14')){
			showCalendarControl('pickDate','hidStartDate');
		}
		if(jQuery("#searchType").val()=='15'){
			showCalendarControl('pickDate','hidEndDate');
		}
		
	}
	
	var statesLoaded = false;
	function loadStatesList(obj){		
		jQuery('#statesList').toggle(function(){
			if (jQuery(this).css('display') == 'block'){
				jQuery(obj).css('list-style-image',minusIcon);
			}else{
				jQuery(obj).css('list-style-image',plusIcon);
			}
		});
		 if(!statesLoaded){
		 jQuery('#statesList').load('soLoadState_getStatesAjax.action',{},function(){statesLoaded = true;});
		}
	}
	
	//order_acceptance
	var accLoaded = false;
	function loadAcceptanceList(obj){	
		jQuery('#orderAcceptance').children("li").css('list-style-image',blankIcon).css('list-style-type','none');
		jQuery('#orderAcceptance').toggle(function(){
			if (jQuery(this).css('display') == 'block'){
				jQuery(obj).css('list-style-image',minusIcon);
			}else{
				jQuery(obj).css('list-style-image',plusIcon);
			}
		});
		 if(!accLoaded){
		 	accLoaded = true;
		}
	}
	
	//pricing_type
	var pricingLoaded = false;
	function loadPricingList(obj){
		jQuery('#pricingType').children("li").css('list-style-image',blankIcon).css('list-style-type','none');
		jQuery('#pricingType').toggle(function(){
			if (jQuery(this).css('display') == 'block'){
				jQuery(obj).css('list-style-image',minusIcon);
			}else{
				jQuery(obj).css('list-style-image',plusIcon);
			}
		});
		 if(!pricingLoaded){
		 	pricingLoaded = true;
		}
	}
	
	//resource_assignment
	var assgnmntLoaded = false;
	function loadAssgnmntList(obj){
		jQuery('#assignment').children("li").css('list-style-image',blankIcon).css('list-style-type','none');
		jQuery('#assignment').toggle(function(){
			if (jQuery(this).css('display') == 'block'){
				jQuery(obj).css('list-style-image',minusIcon);
			}else{
				jQuery(obj).css('list-style-image',plusIcon);
			}
		});
		 if(!assgnmntLoaded){
		 	assgnmntLoaded = true;
		}
	}
		
	//posting_method
	var postingLoaded = false;
	function loadPostingList(obj){	
		jQuery('#posting').children("li").css('list-style-image',blankIcon).css('list-style-type','none');
		jQuery('#posting').toggle(function(){
			if (jQuery(this).css('display') == 'block'){
				jQuery(obj).css('list-style-image',minusIcon);
			}else{
				jQuery(obj).css('list-style-image',plusIcon);
			}
		});
		 if(!postingLoaded){		 
		 	postingLoaded = true;		 
		}
	}
	
	
	//R12_1
	//SL-20362:pending reschedule
	var pendingReschedule = false;
	function loadPendingRescheduleList(obj){	
		jQuery('#pendingReschedule').children("li").css('list-style-image',blankIcon).css('list-style-type','none');
		jQuery('#pendingReschedule').toggle(function(){
			if (jQuery(this).css('display') == 'block'){
				jQuery(obj).css('list-style-image',minusIcon);
			}else{
				jQuery(obj).css('list-style-image',plusIcon);
			}
		});
		 if(!pendingReschedule){		 
			 pendingReschedule = true;		 
		}
	}
	
	//R12_1
	//SL-20554:method of closure
	var closureMethod = false;
	function loadClosureMethodList(obj){	
		jQuery('#closureMethod').children("li").css('list-style-image',blankIcon).css('list-style-type','none');
		jQuery('#closureMethod').toggle(function(){
			if (jQuery(this).css('display') == 'block'){
				jQuery(obj).css('list-style-image',minusIcon);
			}else{
				jQuery(obj).css('list-style-image',plusIcon);
			}
		});
		 if(!closureMethod){		 
			 closureMethod = true;		 
		}
	}
	
	var skillsLoaded = false;
	function loadSkillList(obj){		
			jQuery('#skillList').toggle(function(){
				if (jQuery(this).css('display') == 'block'){
					jQuery(obj).css('list-style-image',minusIcon);
				}else{
					jQuery(obj).css('list-style-image',plusIcon);
				}
		});
		 if(!skillsLoaded){
		 jQuery('#skillList').load('soLoadSkill_getSkillsAjax.action',{},function(){skillsLoaded = true;});
		}
	}
	
	var categoriesLoaded = false;
	function loadCategoryList(obj){
			jQuery('#categoryList').toggle(function(){
				if (jQuery(this).css('display') == 'block'){
					jQuery(obj).css('list-style-image',minusIcon);
				}else{
					jQuery(obj).css('list-style-image',plusIcon);
				}
		});
		 if(!categoriesLoaded){
		 jQuery('#categoryList').load('soSearch_loadMainCategories.action',{},function(){categoriesLoaded = true;});
		}
	}
	
	var statusLoaded = false;
	function loadStatus(obj){
	
			jQuery('#statusList').toggle(function(){
				if (jQuery(this).css('display') == 'block'){
					jQuery(obj).css('list-style-image',minusIcon);
				}else{
					jQuery(obj).css('list-style-image',plusIcon);
				}
		});
		
		 if(!statusLoaded){
		}
	}
	
	var subCategoriesLoaded; 
	function loadSubCategoryList(obj,id){
		var theId = jQuery(obj).attr('id');
		var el = jQuery('#'+theId+'>ul');
		var lis = jQuery('#'+theId+'>ul>li').length;
		if(subCategoriesLoaded != id){
			jQuery('#'+theId+'> .subList').load('soSearch_loadCategoryAndSubCategories.action?selectedId='+ id, {}, function() {
				subCategoriesLoaded = id;
				jQuery(el).show();
				jQuery(obj).find('img').eq(0).attr('src','/ServiceLiveWebUtil/images/icons/minusIcon.gif');
				});
		}else{
			jQuery(el).toggle();
			if (jQuery(el).css('display') == 'block' && lis != 0){
				jQuery(obj).find('img').eq(0).attr('src','/ServiceLiveWebUtil/images/icons/minusIcon.gif');
			}else if(lis != 0){
				jQuery(obj).find('img').eq(0).attr('src','/ServiceLiveWebUtil/images/icons/plusIcon.gif');
			}	
		}
	}
	
	var subStatusLoaded; 
	function loadSubStatusList(obj,id){
		var theId = jQuery(obj).attr('id');
		var el = jQuery('#'+theId+'>ul');
		var lis = jQuery('#'+theId+'>ul>li').length;
			if(subStatusLoaded != id){
			jQuery('#'+theId+'> .subList').load('soSearch_loadSubStatuses.action?subStatId='+ id, {}, function() {
				subStatusLoaded = id;
				jQuery(el).show();
				jQuery(obj).find('img').eq(0).attr('src','/ServiceLiveWebUtil/images/icons/minusIcon.gif');
			});
		}else{
			jQuery(el).toggle();
			if (jQuery(el).css('display') == 'block' && lis != 0){
				jQuery(obj).find('img').eq(0).attr('src','/ServiceLiveWebUtil/images/icons/minusIcon.gif');
			}else if(lis != 0){
				jQuery(obj).find('img').eq(0).attr('src','/ServiceLiveWebUtil/images/icons/plusIcon.gif');
			}else{
				jQuery(obj).find('img').eq(0).attr('src','/ServiceLiveWebUtil/images/icons/plusIcon.gif');
			}		
		}
	}
	
	var subSubCategoriesLoaded; 
	function loadSubSubCategoryList(obj,id){
		var theId = jQuery(obj).attr('id');
		var el = jQuery('#'+theId+'>ul');
		var lis = jQuery('#'+theId+'>ul>li').length;
		if(subSubCategoriesLoaded != id){
			jQuery('#'+theId+'> .subList').load('soSearch_loadCategoryAndSubCategories2.action?selectedId='+ id, {}, 
			function(responseText) {
				subSubCategoriesLoaded = id;
				if(responseText.indexOf('<li') > 0) {
					jQuery(el).show();
					jQuery(obj).find('img').eq(0).attr('src','/ServiceLiveWebUtil/images/icons/minusIcon.gif');
				}
			});
		}else{
			jQuery(el).toggle();
			if (jQuery(el).css('display') == 'block' && lis != 0){
				jQuery(obj).find('img').eq(0).attr('src','/ServiceLiveWebUtil/images/icons/minusIcon.gif');
			}else if(lis != 0){
				jQuery(obj).find('img').eq(0).attr('src','/ServiceLiveWebUtil/images/icons/plusIcon.gif');
			}	
		}
	}
	
	function toggleSelectionsList(obj){
		var theId = jQuery(obj).attr('id');
		var el = jQuery('#'+theId+'>ul');
		var lis = jQuery('#'+theId+'>ul>li').length;
		
			jQuery(el).toggle();
			if (jQuery(el).css('display') == 'block' && lis != 0){
				jQuery(obj).css('list-style-image',minusIcon);
			}else if(lis != 0){
				jQuery(obj).css('list-style-image',plusIcon);
			
			}
		}
	
	function removeElement(obj){
		var parentEl = jQuery(obj).parent().parent();
	/*	//clear autocloserule array if obj represents an autoclose rule.
		var ruleId=jQuery(obj).parent().attr("id");
		var ind = ruleId.indexOf("_");	
	    var len = ind-0;
	    var autoclose = ruleId.substr(0,len);
		if(autoclose=='20'){
			var ind1 = ruleId.indexOf("@");
			var leng = ruleId.length-ind1;
			var ruleHdrId = ruleId.substr(ind1+1,leng);
			autocloserule.splice(autocloserule.indexOf(ruleHdrId),1);
			//var str= document.getElementById('autocloseRulesTempArray').value;
		}
		//end of clear autocloserule array.*/
		
		jQuery(obj).parent().remove();
		if(jQuery(parentEl).children().length == 0){
			//SL-10701
				var query_list = ["check_number_list","customer_name_list","phone_list","pro_firm_id_list",
					"so_id_list","service_pro_id_list","service_pro_name_list","market_list","status_list",
					"category_list","state_list","skill_list","custom_list","zipcode_list","startDate_list",
					"endDate_list","autocloseRules_list","order_acceptance","pricing_type","res_assignment","posting_method","pending_reschedule","closure_method"];
				for(var i=0; i < query_list.length; i++){
					if(jQuery(parentEl).parent().attr("id")== query_list[i]){
						//Hiding the parent tag only if they are the top most nodes
						jQuery(parentEl).parent().hide();
					}//if
				}//for			
		}
	}
	function checkClear(){	
			document.getElementById('saveFilterWarning').style.display="none";	
			var query_list = ["check_number_list","customer_name_list","phone_list","pro_firm_id_list","so_id_list",
							  "service_pro_id_list","service_pro_name_list","market_list","status_list","category_list",
							  "state_list","skill_list","custom_list","zipcode_list","startDate_list","endDate_list",
							  "autocloseRules_list","order_acceptance","pricing_type","res_assignment","posting_method","pending_reschedule","closure_method"];
     		for(var i=0; i < query_list.length; i++){
     			var node_list =  jQuery('#searchSelectionsList > #'+query_list[i]+' > ul >li');
     			jQuery(node_list).parent().parent().hide();
				node_list.remove();
				}
				document.getElementById("selectedSearchCriteria").value = '';

				var iFrameRef = document.getElementById("SearchmyIframe");
				
				if(iFrameRef!=null){
				clearselections();
				var result = iFrameRef.contentWindow.document.getElementById("resultSet");
				}
				
				if(result!=null){
				
				result.innerHTML='';
				}
				
				
				newco.jsutils.displayRightSideMenu(); //this hides the right-rail widget
			return false;
	
	}
	function stopClickPropagation(e){
		if(e.stopPropagation) {e.stopPropagation();}
		e.cancelBubble = true;
	}
	
	function selectableItemClick(obj,list,sublist,sublist2,e) {
		if(selectedSearchTermsInitialHtml == ''){
			storeInitialHtml(); //before changing the selections pane html, store it for the purpose of clearing
		}
		if(e){
			if(e.stopPropagation) {e.stopPropagation();}
			e.cancelBubble = true;
		}
		var strippedId = jQuery(obj).attr('id').replace(/term/,'').replace(/status/,''); 
	 	var newHtml = '<li id="'+strippedId+'" onclick="toggleChildList(this,event);"><span>'+jQuery(obj).attr('title')+'</span><a href="#" onclick="removeElement(this);return false;">[x]</a>';
	 	if((list == 'category_list' || list == 'status_list') && !sublist2){
	 	newHtml += '<ul id=ul'+strippedId+'></ul>';
	 	}
	 	newHtml += '</li>';
	 	jQuery('#'+list).show();
	 	jQuery('#'+list+'>ul').show();
	 
	 	var dList = jQuery('#searchSelectionsList > #'+list+' > ul >li');
	 	//Priority 18
	 	//Code to show error message when maximum count 
	 	//per custom reference exceed pre-defined value
	 	//Start 
	 	if(list == 'custom_list')
	 	{
	 	var arr = [];
	 	var counts = {};
	 	var inList = false;
	 		for (i=0; i<dList.length; i++){
	 			arr.push(dList[i].id);
	 	}
	 	
	 		for(var i = 0; i< arr.length; i++) {
	 	    var num = arr[i];
	 	    counts[num] = counts[num] ? counts[num]+1 : 1;
	 	}
	 	
	 		if(counts[strippedId] == 5){
	 		alert("Please limit the number of custom references per search to 5.");
	 		return;
 			}
	 	}else
	 		{
	 		var inList = false;
	 		for (i=0; i<dList.length; i++){
	 		if(dList[i].id == strippedId){
	 			inList = true;
	 		}
	 	}
	 		}
	 	
	 	if(sublist) {
	 	var strippedSubListId = jQuery(sublist).attr('id').replace(/term/,'').replace(/status/,'');
		 	var	subList = jQuery('#searchSelectionsList > #'+list+' > ul > #'+strippedSubListId+' >ul>li'); 	 	
		 	var inSubList = false;
		 	for (i=0; i<subList.length; i++){
		 		if(subList[i].id == strippedId){
		 			inSubList = true;
		 		}
		 	}
	 	}
	 	
	 	if(sublist2){
	 		var sublist2Id = '#ul'+jQuery(obj).parent().parent().attr('id').replace(/term/,'').replace(/status/,'');
	 		var inSubList2 = false;
	 		for(i=0; i<jQuery(sublist2Id+'>li').length; i++){
	 			if(jQuery(sublist2Id+'>li:eq('+i+')').attr('id') == strippedId){
	 				inSubList2 = true;
	 			}else {
	 				inSubList2 = false;
	 			}
	 		}
	 	}
	 	
	 	if(sublist){
	 		if(sublist2){
	 			var strippedParentId = jQuery(obj).parent().parent().attr('id').replace(/term/,'').replace(/status/,'');
	 			var strippedParentAncestorId = jQuery(obj).parent().parent().parent().parent().attr('id').replace(/term/,'').replace(/status/,'');
	 			if(!inSubList2){
	 				jQuery('#ul'+strippedParentId).parent().show()
	 					.css('list-style-image',minusIcon);
	 				
	 				var tempId = jQuery('#ul'+strippedParentId).parent().attr('id');
	 				jQuery('#'+tempId+'>span').css('cursor','pointer');
	 				
	 				if(jQuery('#'+strippedParentAncestorId).length > 0 && jQuery('#ul'+strippedParentId).length > 0){
	 				jQuery('#ul'+strippedParentId).append(newHtml).show();
	 				}else if(jQuery('#'+strippedParentAncestorId).length > 0){
	 					var parentHtml = '<li id="'+strippedSubListId+'" onclick="toggleChildList(this,event);"><span>';
		 				parentHtml += jQuery(sublist).find('span').find('a').html();
		 				parentHtml += '</span><a href="#" onclick="removeElement(this);return false;">[x]</a><ul id="ul'+strippedSubListId+'"></ul></li>';
		 				jQuery('#ul'+strippedParentAncestorId).append(parentHtml).show();
		 				jQuery('#'+strippedParentAncestorId).show();
		 				jQuery('#ul'+strippedParentId).parent().show()
	 						.css('list-style-image',minusIcon).css('cursor','pointer');
		 				jQuery('#searchSelectionsList > #'+list).css('list-style-image',minusIcon);
		 				jQuery('#ul'+strippedParentId).append(newHtml).show();
	 				}else{
	 					var parentHtml = '<li id="'+strippedParentAncestorId+'" onclick="toggleChildList(this,event);"><span>';
		 				parentHtml += jQuery('#'+strippedParentAncestorId+'term').find('span').find('a').html();
		 				parentHtml += '</span><a href="#" onclick="removeElement(this);return false;">[x]</a><ul id="ul'+strippedParentAncestorId+'">';
	 					parentHtml += '<li id="'+strippedSubListId+'" onclick="toggleChildList(this,event);"><span>';
		 				parentHtml += jQuery(sublist).find('span').find('a').html();
		 				parentHtml += '</span><a href="#" onclick="removeElement(this);return false;">[x]</a><ul id="ul'+strippedSubListId+'"></ul></li></ul></li>';
		 				jQuery('#searchSelectionsList > #'+list+' > ul').append(parentHtml).show();
		 				jQuery('#'+strippedParentAncestorId).show()
		 				.css('list-style-image',minusIcon).css('cursor','pointer');;
		 				jQuery('#ul'+strippedParentId).parent().show()
	 						.css('list-style-image',minusIcon).css('cursor','pointer');
		 				jQuery('#searchSelectionsList > #'+list).css('list-style-image',minusIcon);
		 				jQuery('#ul'+strippedParentId).append(newHtml).show();
	 			}
	 			}
	 		}else if(!inSubList){
 			jQuery('#searchSelectionsList > #'+list+' > ul >#'+strippedSubListId+'>ul').parent().show()
 				.css('list-style-image',minusIcon);
 			var tempId = jQuery('#searchSelectionsList > #'+list+' > ul >#'+strippedSubListId+'>ul').parent().attr('id');
 			jQuery('#'+tempId+'>span').css('cursor','pointer');
				if(jQuery('#searchSelectionsList > #'+list+' > ul >#'+strippedSubListId+'>ul').length > 0){
		 	jQuery('#searchSelectionsList > #'+list+' > ul >#'+strippedSubListId+'>ul').append(newHtml).show();
	 			}else{
	 				var parentHtml = '<li id="'+strippedSubListId+'" onclick="toggleChildList(this,event);"><span>';
	 				parentHtml += jQuery(sublist).find('span').find('a').html();
	 				parentHtml += '</span><a href="#" onclick="removeElement(this);return false;">[x]</a><ul id="ul'+strippedSubListId+'"></ul></li>';
	 				jQuery('#searchSelectionsList > #'+list+' > ul').append(parentHtml).show();
	 				jQuery('#searchSelectionsList > #'+list+' > ul >#'+strippedSubListId)
	 					.css('list-style-image',minusIcon).css('cursor','pointer');
	 				jQuery('#searchSelectionsList > #'+list+' > ul >#'+strippedSubListId+'>ul').append(newHtml).show();
	 				jQuery('#searchSelectionsList > #'+list).css('list-style-image',minusIcon);
			}
		 	}
	 	}else {
		 	if(!inList){
		 	jQuery('#searchSelectionsList > #'+list+' > ul').append(newHtml).show();
		 		jQuery('#searchSelectionsList > #'+list).css('list-style-image',minusIcon);
		 	var tempId = jQuery('#searchSelectionsList > #'+list).attr('id');
		 	jQuery('#'+tempId+'>span').css('cursor','pointer');
		 		jQuery('#searchSelectionsList > #'+list).css('list-style-image',minusIcon);
			}
	 	
	 	}
	 	
	 	document.getElementById("saveFilterName").style.display= "block";
	}
	var selectedSearchTermsInitialHtml = '';
	function storeInitialHtml(){
		jQuery(document).ready(function($){
			selectedSearchTermsInitialHtml = $('#searchSelectionsList').html();
			
			
		});
	}
	
	function toggleChildList(obj,e){
		if(e.stopPropagation) {e.stopPropagation();}
		e.cancelBubble = true;
		var objId = jQuery(obj).attr('id');
		jQuery('#'+objId+'>ul').toggle(function(){
			if(jQuery(this).find('li').length == 0){
				jQuery(obj).css('list-style-image',blankIcon).css('list-style-type','none');
			}else if(jQuery(this).css('display')=='block'){
				jQuery(obj).css('list-style-image',minusIcon);
			}else{
				jQuery(obj).css('list-style-image',plusIcon);
			}
			
		});
	}
	
	/* 	ss: no need for this - jQuery already has a trim() method
	 function trimAll(sString) {
	while (sString.substring(0, 1) == " ") {
		sString = sString.substring(1, sString.length);
	}
	while (sString.substring(sString.length - 1, sString.length) == " ") {
		sString = sString.substring(0, sString.length - 1);
	}
	return sString;
   }*/	
	
	function loadSelectedTerms(){		
		document.getElementById('saveFilterWarning').style.display="none";

		var selectedFilter = document.getElementById("savedFilter");
		var index = selectedFilter.selectedIndex;
		
		var name = selectedFilter.options[index].text;		
		
		var iFrameRef = document.getElementById("SearchmyIframe");		
		var result = iFrameRef.contentWindow.document.getElementById("resultSet");
		result.innerHTML="<img src='"+newco.jsutils.getStaticContext()+"/images/spinner_small.gif'/>";
				
		var searchForm = null;
		if(!dojo.isIE){
			searchForm = iFrameRef.contentDocument.getElementById("searchHandler");
		}else{
			searchForm = iFrameRef.contentWindow.document.getElementById("searchHandler");		
		}
		searchForm.selectedFilterName.value = name;	
		searchForm.isSearchClicked.value = 1;	
		searchForm.submit();		
	}
	function showButton_DeleteOption(){		
		var selectedFilter = document.getElementById("savedFilter");
		var index = selectedFilter.selectedIndex;
		
		var name = selectedFilter.options[index].text;
		if(name != 'Select A Filter'){			
			document.getElementById("filterSearch").style.display= "block";			
			document.getElementById("deleteFilter").style.display= "block";
		}else{
			document.getElementById("filterSearch").style.display= "none";
			document.getElementById("deleteFilter").style.display= "none";
		}
	}
	function deleteFilter(){
		var selectedFilter = document.getElementById("savedFilter");
		var index = selectedFilter.selectedIndex;
		
		var name = selectedFilter.options[index].text;		
		
		var iFrameRef = document.getElementById("SearchmyIframe");
		var searchForm = null;
		if(!dojo.isIE){
			searchForm = iFrameRef.contentDocument.getElementById("searchHandler");
		}else{
			searchForm = iFrameRef.contentWindow.document.getElementById("searchHandler");		
		}
		searchForm.selectedFilterName.value = name;	
		searchForm.action="soSearch_deleteSearchFilters.action";
		searchForm.submit();
	}
	
	function callPendingReschedule()
	{
		jQuery('#reschdRequest').trigger("click");
		jQuery('#pendingReschedule').trigger("click");	
		jQuery('#SearchSom').trigger("click");
	}
	
	
