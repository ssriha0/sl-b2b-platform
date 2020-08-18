

jQuery(document).ready(function () {

//ensures only numbers are entered
jQuery('#autocloseSearchText').keyup(function () {
 			if(jQuery('#autocloseSearchBy > option:selected').val() == 1){
 				if(this.value != ''){
 				   this.value = this.value.replace(/[^0-9\;]/g,'');
 				}
 			}
 			if(jQuery('#autocloseSearchBy > option:selected').val() == 2){
 				if(this.value != ''){
 				   this.value = this.value.replace(/[^A-Za-z0-9\s]/g,'');
 				}
 			}	
 		});	
 		
 		//prefills the text area based on where it is-either search results or exclusion list
 		preFillTextarea = function(isSearchResult){
 			var removeText = 'Enter reason for removing from the override list';
 			var addText = 'Enter reason for adding to the override list';	
 			var resultText = 'Enter reason for overriding the reimbursement rate.';
 			var prefilTxt = '';
 			if(isSearchResult){
 				prefilTxt = resultText;
 			}else{
 				prefilTxt = jQuery('#listedLink').hasClass('disabled') ? removeText : addText;
 			}

 			return prefilTxt;
 		}
 		
 		jQuery("#layer1").css('visibility','hidden');
 		//.hide();
 		
 			
 		jQuery(document).click(function(e){ 
 			var click=jQuery(e.target);
 			if(click.parents().is("#layer1")){
 				e.stopPropagation();
 			}else if(click.closest("div").hasClass("comHistLayer")){
 				e.stopPropagation();
 			}else{
 				jQuery("#layer1").css('visibility','hidden');
 				jQuery(".comHistLayer:visible").hide();
 			}
 		});

 		jQuery('#close').click(function()
 		{
 			jQuery("#layer1").css('visibility','hidden');
 			//jQuery("#layer1").hide();
 		});
 		
 		jQuery('#listedLink').addClass('disabled');
 		//jQuery("#successMsg").hide();
 		var txt = preFillTextarea(false);

 		jQuery('.shadowBox').val(txt);

 		loadTableData = function(displayCount){
 			
 		var queryString="";
 		var activeInd = "";
 			
 			if(jQuery('#listedLink').hasClass('disabled')){
 			
 				jQuery('#listHead').text("Provider Firm Override List");
 				activeInd = 1;
 				queryString += "activeInd=" + activeInd +"&displayCount="+displayCount;
 				jQuery('#loadSearchSpinner').jqmShow();
 				jQuery('#overrideList').load('autoCloseHSR_getFirmOverrideList.action?'+queryString,function(){
 					jQuery('#loadSearchSpinner').jqmHide();
 				});
 			}
 			if(jQuery('#removedLink').hasClass('disabled')){
 				jQuery('#listHead').text("Provider Firms Removed from Override List");
 				activeInd = 0;
 				queryString += "activeInd=" + activeInd +"&displayCount="+displayCount;
 				jQuery('#loadSearchSpinner').jqmShow();			
 				jQuery('#overrideList').load('autoCloseHSR_getFirmOverrideList.action?'+queryString,function(){
 					jQuery('#loadSearchSpinner').jqmHide();
 				});		
 			}
 			
 		}
 		
 		jQuery('#prevList').click(function(){jQuery('#listedLink').click();});
 		jQuery('#nextList').click(function(){jQuery('#removedLink').click();});
 		
 					
 		jQuery('#listedLink').click(function()
 		{
 			if(jQuery('#listedLink').hasClass('disabled')){return false;}
 			jQuery("#successMsg").hide();
 			jQuery('#autocloseExcludeValidationError > .errorText').html("");
 			jQuery('#autocloseExcludeValidationError').hide();
 			jQuery('#listedLink').addClass('disabled');
 			jQuery('#listedLink').next().removeClass('disabled');
 			loadTableData('30');
 			
 		});
 		jQuery('#removedLink').click(function()
 		{
 			if(jQuery('#removedLink').hasClass('disabled')){return false;}
 			jQuery("#successMsg").hide();
 			jQuery('#autocloseExcludeValidationError > .errorText').html("");
 			jQuery('#autocloseExcludeValidationError').hide();
 			jQuery('#removedLink').addClass('disabled');
 			jQuery('#removedLink').prev().removeClass('disabled');
 			loadTableData('30');
 		});
 				
 		jQuery('#viewNext').click(function()
 		{
 			if(jQuery('#viewNext').hasClass('disabled')){return false;}
 			jQuery("#successMsg").hide();
 			loadTableData(jQuery(".scrollerTableRow").length + 30);
 		});
 		
 		jQuery('#viewAll').click(function()
 		{
 			if(jQuery('#viewAll').hasClass('disabled')){return false;}
 			jQuery("#successMsg").hide();
 			loadTableData(-1);
 		});			
 		
 		jQuery('.shadowBox').focusin(function()//.focus(function()
 		{
 			var obj = jQuery(this);
 			var txt = '';
 			if(jQuery(this).closest('tr').prop('id').indexOf('res') == -1){
 				txt = preFillTextarea(false);
 			}else{
 				txt = preFillTextarea(true);
 			}
 			if(obj.val() == txt){
 				obj.val('');
 			}
 		});
 		
 		jQuery('.shadowBox').focusout(function()//.blur(function()
 		{
 			var obj = jQuery(this);
 			if(obj.val() == ''){
 				var txt = '';
 				if(jQuery(this).closest('tr').prop('id').indexOf('res') == -1){
 					txt = preFillTextarea(false);
 				}else{
 					txt = preFillTextarea(true);
 				}
 				obj.val(txt);
 			}
 		});

 		jQuery('a[id*="firmID"]').click(function()
 		{
 			var obj = jQuery(this);
 			vendorId = obj.prop('id').replace('firmID','');
 			if (document.openProvURL != null)
 			{
 				document.openProvURL.close();
 			}
 			var url = "/MarketFrontend/providerProfileFirmInfoAction_execute.action?vendorId=" + vendorId + "&popup=true";
 			newwindow=window.open(url,'_publicproviderprofile','resizable=yes,scrollbars=yes,status=no,height=700,width=1000');
 			document.openProvURL = newwindow;
 		});

 		//Search panel functions
 		
 			jQuery('#autocloseSearchBy').change(function(){
 			if (jQuery('#autocloseSearchBy > option:selected').val() >0){
 				var texttoappend = jQuery('#autocloseSearchBy > option:selected').text() + ', minimum 3 chars';
 				if(jQuery('#autocloseSearchBy > option:selected').val() == 2){
 					texttoappend = texttoappend.replace('Name','First or Last Name');
 				}
 				if(jQuery('#autocloseSearchBy > option:selected').val() == 1){
 					texttoappend = 'multiples 	separated by ; up to 10';
 				}
 				jQuery('#autocloseSearchInput > label >span').html(texttoappend);
 			//	jQuery('#spnMMSearchInput > input' ).prop('id','spnMMSearchText' + jQuery('#spnMMSearchBy > option:selected').val());
 				jQuery('#autocloseSearchInput > input' ).prop('value','');

 				jQuery('#autocloseSearchInput > label').show();
 			}else{
 				jQuery('#autocloseSearchInput > label').hide();
 			}
 		});

 		if (jQuery('#searchByType').val() != "-1") {
 			var idName = '#searchByTypeVal' + jQuery('#searchByType').val();
 			var idText = '#searchByTypeText' + jQuery('#searchByType').val();
 			jQuery('.searchByTypeVal').hide();
 			jQuery(idName).show();
 			jQuery('.searchByTypeText').hide();
 			jQuery(idText).show();
 			jQuery('.searchSubmit').show();
 		}

 		jQuery('#searchByType').change(function() {
 			var searchSubmit = '#searchSubmit' + jQuery(this).val();
 			var idName = '#searchByTypeVal' + jQuery(this).val();
 			var idText = '#searchByTypeText' + jQuery(this).val();
 			jQuery('.searchByTypeVal').val('');
 			if (jQuery(this).val() == "3") {
 				jQuery(idName).val('-1');
 			}
 			jQuery('.searchByTypeVal').hide();
 			jQuery(idName).show();
 			jQuery('.searchByTypeText').hide();
 			jQuery(idText).show();
 			jQuery('.searchSubmit').show();
 			jQuery(searchSubmit).hide();
 		});

 		
 		jQuery('#searchByType').change(function() {
 			if (jQuery(this).val() == "-1") {
 				submitSearch();
 			}
 		});
 		
 		jQuery('#loadSearchSpinner').jqm( {
 			modal : true,
 			toTop : true
 		});
 		//enable submit on Enter
 		jQuery("input").keypress(function(e) {
 			if ((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)) {
 				clearFilters();
 				submitSearch();
 			}
 		});

 		//upon loading page, submit search if the "search by" fields contain values (e.g., user clicked back button)
 		if((jQuery('#autocloseSearchBy').length && jQuery('#autocloseSearchInput').length) /* check if fields exist */
 				&& jQuery('#autocloseSearchBy > option:selected').val() != -1
 					&& jQuery('#autocloseSearchInput > input').val() != "") {
 			submitSearch();
 		}
 		
 		jQuery(".plus").click(function(){
 			var posLeft = jQuery(this).width() + (1.1*jQuery(this).offset().left);
 			var posTop = (jQuery(this).offset().top - 50);
 			jQuery(".comLayer").css('top',posTop);	
 			jQuery(".comLayer").css('left',posLeft);	
 			jQuery(this).next(".comLayer").show();
 		});           

 		jQuery(".plus").blur(function(){
 			jQuery(this).next(".comLayer").hide();
 		});
 		
 		jQuery(".viewComHisLink").click(function(){
 			var posLeft = jQuery(this).width() + (1.05*jQuery(this).offset().left);
 			var posTop = (jQuery(this).offset().top - 0);
 			jQuery(".comHistLayer").css('top',posTop);	
 			jQuery(".comHistLayer").css('left',posLeft);	
 			jQuery(this).next(".comHistLayer").show();
 		});           

 	});	

	//gets the ids of the check boxes selected in the search results  
 	function getIdsToRemove(checkList){
 	  var idList = new Array();
 	  var ctr = 0;
 	  //find all ids from array of check box values  
 	  jQuery("input[name=" + checkList + "]:checked").each
 	  (
 	    function()
 	    {
 	      //fill the array with the values -- [ctr] = 
 	      if(jQuery(this).val().indexOf('resFirm') != -1 ){
 	      	idList.push(new String(jQuery(this).val().replace('resFirm','')));	        
 	      }
 	      if(jQuery(this).val().indexOf('resProv') != -1 ){
 	      	idList.push(new String(jQuery(this).val().replace('resProv','')));	        
 	      }	        

 	    }
 	  );
 	  return idList;//.serializeArray();
 	}

 	//gets the comments were checkboxes are checked in the search results 
 		function getCommentsArray(checkList){
 		  var idList = new Array();
 		
 		  //find all ids from array of check box values  
 		  jQuery("textarea[name=" + checkList + "]").each
 		  (function(){
 		  	var txt = preFillTextarea(true);
 		  	if((jQuery(this).val() != txt) && ($.trim(jQuery(this).val()) != '')){
 				idList.push(jQuery(this).val().replace(',','~*#'));	    			    	
 		  	}
 		  });
 		  return idList;//.serializeArray();
 		}	
 		//gets the reimbursement values
 		function getReimbursement(checkList){
 		  var idList = new Array();
 		  //find all ids from array of check box values  
 		  jQuery("select[name=" + checkList + "]").each
 		  (function(){
 		  	if(jQuery(this).val() != -1){
 				idList.push(jQuery(this).val());	    			    	
 		  	}
 		  });
 		  return idList;
 		}
 		
	function showCommentHistory(index){
		jQuery("#comHistLayer"+index).css('display','block');
	}
 	//To check maxlength of textarea
 	function CheckMaxLength(Object, MaxLen){
 		jQuery(document).ready(function($){
 			if(Object.value.length > MaxLen-1)
 			{      
 			  Object.value = Object.value.substring(0, MaxLen-1);
 			}
 		});		
 	}

 	function clearFilters() {
 			jQuery('.filterSelect').val("-1");
 			jQuery('#searchresults').html('');
 			jQuery('#autocloseExcludeValidationError').hide();
 			jQuery("#successMsg").hide();
 			
 		}
		/* This function is called when 'Remove from Override List', 
 		'Add to Override List' and 'Add selected' button is clicked */
 		
 		function updateFirmList(obj) 
 		{
 		jQuery('#autocloseExcludeValidationError').hide();
 		jQuery('#autocloseSearchValidationError').hide();
 		var errorMsg = "";
 		var isValid = true;
 		var queryString="";
 		var ruleAssocId = 0;
 		var removedAssocInd = 1;
 		var remibursementArray = new Array();
 		var	commentsArray = new Array();
 		var firmIdsArray = new Array();
 		var myArray = new Array();
 			if(obj.id.indexOf('Firm') != -1){
 				if(obj.id.indexOf('res') != -1){
 					firmIdsArray = getIdsToRemove('firmChk');
 					commentsArray = getCommentsArray('commentresFirm');
 					remibursementArray = getReimbursement('reimbursementRate');
 					if(firmIdsArray.length == 0){
 						errorMsg = errorMsg + "Please select a search result to add.<br/>";
 				    	isValid = false;
 						
 					}
 					if(remibursementArray.length == 0){
 						errorMsg = errorMsg + "Please select a reimbursement rate.<br/>";
 				    	isValid = false;
 					}
 					if(commentsArray.length == 0){
 						errorMsg = errorMsg + "Please enter the reason for adding to override list.<br/>";
 				    	isValid = false;
 						
 					}
 					
 				}else{
 					if(obj.id.indexOf('remove') != -1){
 						ruleAssocId = obj.id.replace('removeFirm','');
 						removedAssocInd = 0;
 					}else{
 						ruleAssocId = obj.id.replace('addFirm','');
 						removedAssocInd = 1;			
 					}
 					if(($.trim(jQuery('#comment'+ruleAssocId).val())!='') && ($.trim(jQuery('#comment'+ruleAssocId).val())!=preFillTextarea(false)) ){
 						commentsArray.push(jQuery('#comment'+ruleAssocId).val());
 					}
 					if((commentsArray.length == 0)){
 						jQuery('#autocloseExcludeValidationError > .errorText').html('Please'+preFillTextarea(false).replace('Enter',' enter')+'.');
 						jQuery('#autocloseExcludeValidationError').show();
 						return;
 					}				

 				}
 				//Need to change the method name all according to inhome autoclose
 				if(true == isValid){
 				var encodedAction = encodeURI('autoCloseHSR_updateFirmOverrideList.action?idsToAdd='+firmIdsArray);
 				jQuery('#loadSearchSpinner').jqmShow();
 				jQuery('#overrideList').load(encodedAction,
 				{
 					'ruleAssocId':ruleAssocId,
 					'removedAssocInd':removedAssocInd, 
 					'comment':encodeURIComponent(commentsArray),
 					'reimbursementvalue':encodeURIComponent(remibursementArray)
 				},
 				function(){
 					//removing results msg and add selected button if only all the results are added
 					if(jQuery('[id*="chkresFirm"]').length == firmIdsArray.length){
 						jQuery('#firmSearchResults').html('');
 					}else{//removing the selected results one by one after adding
 						for(var ctr=0;ctr<firmIdsArray.length;ctr++){
 							jQuery("#txtresFirm"+firmIdsArray[ctr]).remove();
 							jQuery("#chkresFirm"+firmIdsArray[ctr]).remove();
 						}				
 					}
 					if(removedAssocInd == 1){
 						jQuery("#successMsg").html('The selected <B>Provider Firm</B> has been <B>added</B> to the Auto Close Overridden List.');
 					}else{
 						jQuery("#successMsg").html('The selected <B>Provider Firm</B> has been <B>Removed</B> from the Auto Close Overridden List.');						
 					}jQuery('#loadSearchSpinner').jqmHide();
 					jQuery("#successMsg").show();
 				});	
	    	}
 				else{
 					errorMsg = errorMsg + "<br/>";
 		 			if("" != errorMsg){
 		 				jQuery('#autocloseSearchValidationError > .errorText').html(errorMsg);
 						jQuery('#autocloseSearchValidationError').show();
 		 				return;
 		 			}
 				}
 			}
 			
 		}

 	function enableComments(obj)
	{
		if(jQuery(obj).is(':checked')){
			jQuery('#txt'+jQuery(obj).val()).show();
		}else{
			var txt = '';
			if(jQuery(obj).val().indexOf('res') == -1){
				txt = preFillTextarea(false);
			}else{
				txt = preFillTextarea(true);
			}
			jQuery('#txt'+jQuery(obj).val()).hide();			
			if(jQuery(obj).prop('name') == ''){
				jQuery('#comment'+jQuery(obj).val()).val(txt);
			}else if(jQuery(obj).prop('name') == 'firmChk'){
				jQuery('#commentresFirm'+jQuery(obj).val()).val(txt);
			}else if(jQuery(obj).prop('name') == 'provChk'){
				jQuery('#commentresProv'+jQuery(obj).val()).val(txt);
			}
		}

	}

 	function searchMembers(){
 		jQuery('.filterSelect').val("-1");
		jQuery('#searchresults').html('');
		jQuery('#autocloseExcludeValidationError').hide();
		jQuery("#successMsg").hide();
 		submitSearch();
 	}

 	function submitSearch() {
 		jQuery(document).ready(function($){

 			var runSearch = function(){
 				//run search and display results; call this after validation

 				var searchType = jQuery('#autocloseSearchBy').val();
 				var textId = '#autocloseSearchText';
 				var textValue = $.trim(jQuery(textId).val());//alert('textValue:'+textValue);
 				
 				var queryString = "";
 				if(searchType == 1) {
 					queryString += "providerFirmIdsStr="	+ textValue	+ "&";
 				}
 				else if (searchType == 2){
 					queryString += "providerFirmName=" + textValue + "&";
 				}
 				
 				
 				//queryString += "searchCriteriaVO.filterCriteria.spnId=" + 1	+ "&" + "searchCriteriaVO.filterCriteria.stateCode=";
 				queryString += "isViewAll="+ 3 + "&"	+ "searchByType=" + searchType;
 				var encodedAction = encodeURI('autoCloseHSR_searchMembers.action?' + queryString);

 				jQuery('#loadSearchSpinner').jqmShow();
 					jQuery('#searchresults').load(encodedAction,function(){
 					//loginPage set in login.jsp
 					/*if(typeof loginPage == 'boolean' && loginPage == true){
 						top.location = "/spn/spnLoginAction_display.action"; //prevent the login page from appearing in a div
 					}*/
 						jQuery('#loadSearchSpinner').jqmHide();
 						jQuery('#autocloseSearchResultsTable').tablesorter({headers:{2:{sorter:false}, 3:{sorter:false}}});
 						jQuery('#autocloseSearchBy').css('background','#fff');
 						jQuery('#autocloseSearchInput > input').css('background','#fff');
 					});
 			}

 					//input validation
 				var maxIDs = 10;
 				var msgValidCriteria = "Please enter a minimum of three characters to search.";
 				var msgNoCriteria = "Please enter a search term to search.";			
 				var msgIdLimit = "Please provide up to " + maxIDs + " IDs.";
 				var msgNumbersOnly = "Please use numeric IDs only, separated by semicolons (;)";
 				var msgValidOnly = "Please enter valid IDs only, separated by semicolons (;)";
 				var msgSelectCtgry = "Please select an option to search by.";

 					if(jQuery('#autocloseSearchBy > option:selected').val() == -1){

 						jQuery('#autocloseSearchValidationError > .errorText').html(msgSelectCtgry);
 						jQuery('#autocloseSearchValidationError').show();
 						jQuery('#autocloseSearchBy').focus();
 						jQuery('#autocloseSearchBy').css('background','#fff8bf');
 					} else if($.trim(jQuery('#autocloseSearchInput > input').val()).length == 0){
 						jQuery('#autocloseSearchValidationError').hide();
 						jQuery('#autocloseSearchValidationError > .errorText').html(msgNoCriteria);
 						jQuery('#autocloseSearchValidationError').show();
 						jQuery('#autocloseSearchInput > input').focus();
 						jQuery('#autocloseSearchInput > input').css('background','#fff8bf');
 						jQuery('#autocloseSearchBy').css('background','#fff');
 					}else if(jQuery('#autocloseSearchInput > input').val().length < 3){
 						jQuery('#autocloseSearchValidationError').hide();
 						jQuery('#autocloseSearchValidationError > .errorText').html(msgValidCriteria);
 						jQuery('#autocloseSearchValidationError').show();
 						jQuery('#autocloseSearchInput > input').focus();
 						jQuery('#autocloseSearchInput > input').css('background','#fff8bf');
 						jQuery('#autocloseSearchBy').css('background','#fff');

 					}else if (jQuery('#autocloseSearchBy > option:selected').val() == 1){
 						//check IDs
 						jQuery('#autocloseSearchValidationError').hide();
 						jQuery('#autocloseSearchInput > input').val(jQuery('#autocloseSearchInput > input').val().replace(/,/g,';').replace(/ /g,''));
 						var idArray = jQuery('#autocloseSearchInput > input').val().split(';');
 						if(idArray.length > maxIDs){
 							jQuery('#autocloseSearchValidationError').hide();
 							jQuery('#autocloseSearchValidationError > .errorText').html(msgIdLimit);
 							jQuery('#autocloseSearchValidationError').show();

 						}else{
 							var numbersOnly = true;
 							var valid = true;
 							for(i=0; i<idArray.length; i++){
 								if(isNaN(idArray[i]) || $.trim(idArray[i]) == '') {
 									if(i != (idArray.length -1)){
 									numbersOnly = false;
 										break;								
 									}
 								}
 								if($.trim(idArray[i]).length > 10){
 									valid = false; 
 									break;
 								}
 							}
 							if(!numbersOnly){
 								jQuery('#autocloseSearchValidationError').hide();
 								jQuery('#autocloseSearchValidationError > .errorText').html(msgNumbersOnly);
 								jQuery('#autocloseSearchValidationError').show();
 							}else if(!valid){
 								jQuery('#autocloseSearchValidationError').hide();
 								jQuery('#autocloseSearchValidationError > .errorText').html(msgValidOnly);
 								jQuery('#autocloseSearchValidationError').show();						
 							}else{
 								jQuery('#autocloseSearchValidationError').hide();
 								runSearch();
 							}
 						}

 					}else {
 						jQuery('#autocloseSearchValidationError').hide();
 						runSearch();
 					}
 		});
 	}	
 