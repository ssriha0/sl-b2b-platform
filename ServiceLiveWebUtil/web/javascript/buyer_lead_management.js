/* Buyer Lead Management Related Scripts */
$(document).ready(function() {	
	var iCount=$("#newInitialLeadCount").val();//alert('iCount'+iCount);
	iCount=parseInt(iCount);
	//iCount = Math.round(iCount / 10) * 10;//alert('1');
	$('#buyerLeadSearchText').click(function(event){
		 var searchTxtstr =$('#buyerLeadSearchText').val();
		 if(searchTxtstr == "Enter text...."){
			 $('#buyerLeadSearchText').val("");
		 }	 
	});
	$('#buyer-lead-monitor_length').hide();
	$('#buyer-lead-monitor_filter').hide();
	$('#buyer-lead-monitor_paginate').hide();
	var oTable= $('#buyer-lead-monitor').dataTable({
	"sScrollY": "100",
	"bScrollCollapse": true,
	"sDom": "<'H'lfrp>t<'F'ip>",
		 "bServerSide": true,
		 "bRetrieve": true,
		  "sAjaxSource": "MarketFrontend/buyerLeadManagementControllerAction_loadBuyerLeadManagementTabInformationAction.action",
		    "iDisplayLength": iCount,
		  "iDisplayStart": 0,
		   // "sDom":"Rfrtlip",
		   "sPaginationType": "full_numbers",

	      "aaSorting": [[ 7, "desc" ]],
	      "jqueryUI": true,
	      "oLanguage": {
		         "oPaginate": {
		            "sLast": ">>",
		            "sFirst": "<<",
		            "sNext": "&nbsp;>&nbsp;",
		            "sPrevious": "&nbsp;<&nbsp;"

		          },


    				 "sLengthMenu":"_MENU_",
    				 //"sInfo":" Showing _START_ to _END_  total _MENU_ records",
    				 "sInfoEmpty":" ",
         			 "sEmptyTable":"<div style='background-color:white;width:100%;padding-top:5px;height:20px;display:block;text-align:center;'><b>No records matched your search.</b></div>"
		         },
		         "aoColumns":[
		     				  {"sClass":"alignLeft"},
		    				  {"sClass":"alignLeft"},
		    				  {"sClass":"alignLeft"},
		    				  {"sClass":"alignLeft"},
		    				  {"sClass":"alignLeft"},
		    				  {"sClass":"alignLeft"},
		    				  {"sClass":"alignLeft"},
		    				  {"sClass":"alignLeft"}
		    				  ],
		    				  "bAutoWidth":false,
		    				  "fnDrawCallback":function(){
		    				//  alert('aaData'+ _START_);//nRow, aaData, iStart, iEnd, aiDisplay
		    					  $("#buyerLeadSearchSubmit").attr("disabled",false);
		    					  $('#buyerLeadDiv').show();
		    					  $('#buyerLeadLoadingLogo').hide();
		    					  $('#buyer-lead-monitor').show();
		    					  $('#buyer-lead-monitor_info').show();
		    					  $("#buyer-lead-monitor_filter").hide();
		    					  $('#buyer-lead-monitor_wrapper').css("width","100%");
		    					//  $('#buyer-lead-monitor_wrapper').css("padding-left","2%");
		    					 // $('#buyer-lead-monitor_info').css("position","relative");
		    					//  $('#buyer-lead-monitor_info').css("left","83%");
		    					 ///// $('#buyer-lead-monitor_info').css("top","-497px");
		    					 // $('#buyer-lead-monitor_info').css("background-color","#FFFFFF");
		    					 // $('#buyer-lead-monitor_info').css("background-color","#FFFFFF");
		    						$('#buyer-lead-monitor_paginate').show();
//		    						$('#lastUpdated').show();
		    						$('#buyer-lead-monitor').show();
//		    						$('#not_found').hide();
		    						$('#buyer-lead-monitor_info').addClass("leadCount");
		    						/*var info=$("#buyer-lead-monitor_info").html();//alert('info'+info);
								var ret = info.split(" ");
								var newInfo="";
								if(ret.length>1)
								{
								var str1 = ret[1];
								var str3 = ret[3];
								var str5 = ret[5];
								//alert('str5--'+str5);
								//alert('vv-'+str1+"-"+str3);
								var newCount=str3-str1+1;
								//alert('newCount'+newCount);
								$("#buyer-lead-monitor_info").empty();
								newInfo=newCount+" of "+str5;
								}
								else
								{
								$("#buyer-lead-monitor_paginate").hide();
								$("#buyer-lead-monitor_info").empty();
								newInfo="0 Result found";
								}*/
		    					$(".dataTables_paginate").show();
								var info=$("#buyer-lead-monitor_info").html();
								if(info==" ")
								{
								$("#buyer-lead-monitor_paginate").hide();
								$("#buyer-lead-monitor_info").hide();
								$(".dataTables_paginate").hide();
								$("#buyer-lead-monitor_wrapper > :nth-child(3n) div:nth-child(2)").hide();
								}
								else
								{
								info=info.replace("to","-");
								info=info.replace("entries","total leads");
								$("#buyer-lead-monitor_info").html(info);
								$("#buyer-lead-monitor_wrapper > :nth-child(3n) div:nth-child(2)").show();
								$("#buyer-lead-monitor_wrapper > :nth-child(3n) div:nth-child(2)").addClass("bottomLeadInfo");
								$("#buyer-lead-monitor_wrapper > :nth-child(3n) div:nth-child(2) span .paginate_button").addClass("pageStyle");
								}
								//$("#buyer-lead-monitor_info").html(newInfo);
		    						$('#buyer-lead-monitor_paginate').addClass("leadInfo");
		    						 $('#buyer-lead-monitor_length').hide();
		    					//	$("#buyer-lead-monitor_wrapper").css("position","fixed");
		    						//$("#buyer-lead-monitor_wrapper").css("padding-top","10px");
		    					 //	$("div #buyer-lead-monitor_filter input").addClass("filterStyle");
		    					 //	$("div #buyer-lead-monitor_filter input").prop("id","buyerLeadSearchText");
		    					 	//$("div #buyer-lead-monitor_filter input").prop("value","Enter Text...");
		    					   //     $("div #buyer-lead-monitor_filter input").addClass("buyerLeadSearchList");
		    					     $("#buyer-lead-monitor_paginate span .paginate_button").addClass("pageStyle");
		    					     $("#buyer-lead-monitor_paginate span .paginate_button").addClass("pageStyle");
		    					     $(".dataTables_paginate span a").addClass("pageStyle");
		    					   //  $("#buyer-lead-monitor_info").addClass("leadCount");
		    					   $( ".dataTables_scroll" ).contents().unwrap();
		    					   jQuery("#buyer-lead-monitor").wrap('<div class="dataTables_scroll" />');
		    					   $(".paginate_button_disabled").css("text-decoration","none");
		    					   $(".paginate_active").css("font-weight","bold");
		    					   fixedDataTableHeaderCss();
		    					  }
	});
	//$("#buyer-lead-monitor_info").addClass("leadCount");
	$('#buyer-lead-monitor_length').hide();
	$('#buyer-lead-monitor_filter').hide();
	$('#buyer-lead-monitor_paginate').hide();

	$('#buyerLeadSearchSubmit').click(function(event){
		$('#buyerLeadLoadingLogo').show();
		$('#buyer-lead-monitor').hide();
		$('#buyer-lead-monitor_info').hide();
		$('#buyerLeadDiv').hide();
								$('#buyerLeadFilter option:first').attr('selected', true);//alert('1');
								//$('#buyerLeadSearchText').val("");
								$("#buyerLeadSearchSubmit").attr("disabled",true);
								$("div #buyer-lead-monitor_filter input").hide();
								var filterCriteria =$('#buyerLeadSearch').val();//alert('filterCriteria'+filterCriteria);
								var searchError=""
								if(filterCriteria==-1)
									{
									searchError="Please select the search drop down";
									$("#buyerLeadSearchError").show();
									$("#buyerLeadSearchError").html(searchError);
									$("#buyerLeadSearchError").show();
									$("#buyerLeadSearchSubmit").attr("disabled",false);
									}
								else
									{
									 $("#buyerLeadSearchError").hide();
									 var filterString =$('#buyerLeadSearchText').val();//alert('filterString'+filterString);
									 filterString=$.trim(filterString);
									 var filterName=$("#buyerLeadSearch :selected").text();
									 if(filterCriteria==4)
										 {
										 filterName="Service Request Id";
										 }
									 else if(filterCriteria==11)
										 {
											 filterName="LMS Service Request Id";
										 }
									 if(filterCriteria>=1 && filterCriteria<=6 || filterCriteria==11)
										 {

											 if(filterString==''|| filterString=='Enter text....')
											 {
												 searchError="Please provide a valid "+filterName;
												 $("#buyerLeadSearchError").html(searchError);
												 $("#buyerLeadSearchError").show();
												 $("#buyerLeadSearchSubmit").attr("disabled",false);
											 }
											 else
											 {
											  if(filterCriteria == 1 && (isNaN(filterString)|| filterString.length<10 || filterString.length>10 )){
												  searchError="Please provide a valid Phone Number.";
												  $("#buyerLeadSearchError").show();
												  $("#buyerLeadSearchError").html(searchError);
												  $("#buyerLeadSearchError").show();
												  $("#buyerLeadSearchSubmit").attr("disabled",false);
											  }else{
												  oTable.fnFilter(filterCriteria,'1');
												  oTable.fnFilter(filterString);
												  oTable.fnSort( [ [7,'desc'] ] );
											  }
											 }
										 }
									 else if(filterCriteria==7)
										 {
										 var stateSelected=$("#stateSearch").val();
										 if(stateSelected==-1)
											 {
											 searchError="Please select a state for search ";
											 $("#buyerLeadSearchError").html(searchError);
											 $("#buyerLeadSearchError").show();
											 $("#buyerLeadSearchSubmit").attr("disabled",false);
											 }
										 else
											 {
											 var stateId=$("#stateSearch option:selected").attr("id");
											 oTable.fnFilter(filterCriteria,'1');
											 oTable.fnFilter(stateId);
											 oTable.fnSort( [ [7,'desc'] ] );
											 }

										 }
									 else if(filterCriteria==8)
									 {
									 var createdDateSearch=$("#createdDateSearch").val();//alert('createdDateSearch'+createdDateSearch);
									 if(createdDateSearch==-1)
										 {
										 searchError="Please select a created date for search ";
										 $("#buyerLeadSearchError").html(searchError);
										 $("#buyerLeadSearchError").show();
										 $("#buyerLeadSearchSubmit").attr("disabled",false);
										 }
									 else
										 {
										 var createdDate=$("#createdDateSearch option:selected").val();//alert('createdDate'+createdDate);
											 if(createdDate==-1)
											 {
											 searchError="Please select a specific or range date for search ";
											 $("#buyerLeadSearchError").html(searchError);
											 $("#buyerLeadSearchError").show();
											 $("#buyerLeadSearchSubmit").attr("disabled",false);
											 }
											 else if(createdDate==1)
											 {//alert('insside');
											 $("#specificDateSearch").show();
											 var specificDateGiven=$("#specificeFromDate").val();
											 if(specificDateGiven=='')
											 {
											  searchError="Please provide a valid specific date.";
											  $("#buyerLeadSearchError").html(searchError);
											  $("#buyerLeadSearchError").show();
											  $("#buyerLeadSearchSubmit").attr("disabled",false);
											  }
											  else
											  {
											  oTable.fnFilter(specificDateGiven);
											  oTable.fnFilter(filterCriteria,'1');
											  oTable.fnSort( [ [7,'desc'] ] );
											 }
											 }
											 else if(createdDate==2)

											 {
											  var fromDate=$("#dateRangeFromDate").val();
											  var fromDate1 = new Date(fromDate);
											  var toDate=$("#dateRangeToDate").val();
											  var toDate1 = new Date(toDate);
											  if(fromDate=='')
											  {
											  searchError="Please provide a valid from date.";
											  $("#buyerLeadSearchError").html(searchError);
											   $("#buyerLeadSearchError").show();
											   $("#buyerLeadSearchSubmit").attr("disabled",false);
											   }
											   else if(toDate=='')
											  {
											  searchError="Please provide a valid to date.";
											  $("#buyerLeadSearchError").html(searchError);
											   $("#buyerLeadSearchError").show();
											   $("#buyerLeadSearchSubmit").attr("disabled",false);
											   }
 											    else if (fromDate1>toDate1)
											   	  {
											   	  searchError="From date should be less than to date.";
											   	  $("#buyerLeadSearchError").html(searchError);
											   	   $("#buyerLeadSearchError").show();
											   	$("#buyerLeadSearchSubmit").attr("disabled",false);
											   }

											   else
											   {
										          var rangeDate=fromDate+","+toDate;
											 // $("#rangeDateSearch").show();
											  oTable.fnFilter(rangeDate);
											 oTable.fnFilter(filterCriteria,'1');
											 oTable.fnSort( [ [7,'desc'] ] );
											 }

											 }
										 }

									 }
									 else if(filterCriteria==9)
									 {
									 var projectType=$("#projectTypeSearch").val();
									 if(projectType==-1)
										 {
										 searchError="Please select a project type from the drop down ";
										 $("#buyerLeadSearchError").html(searchError);
										 $("#buyerLeadSearchError").show();
										 $("#buyerLeadSearchSubmit").attr("disabled",false);
										 }
									 else
										 {
										 var projectTypeSelected=$("select#projectTypeSearch option").filter(":selected").val();
										// alert('projectTypeSelected'+projectTypeSelected);
										 oTable.fnFilter(filterCriteria,'1');
										 oTable.fnFilter(projectTypeSelected);
										 oTable.fnSort( [ [7,'desc'] ] );
										 }

									 }
									 else if(filterCriteria==10)
									 {
									 var leadStatusSearch=$("#leadSearch").val();
									 if(leadStatusSearch==-1)
										 {
										 searchError="Please select a lead status from the drop down ";
										 $("#buyerLeadSearchError").html(searchError);
										 $("#buyerLeadSearchError").show();
										 $("#buyerLeadSearchSubmit").attr("disabled",false);
										 }
									 else
										 {
										 var leadStatusSelected=$("#leadSearch option:selected").text();
										 oTable.fnFilter(filterCriteria,'1');
										 oTable.fnFilter(leadStatusSelected);
										 oTable.fnSort( [ [7,'desc'] ] );
										 }

									 }

									}

							    // oTable.push({ "sSearch_1": "IL12"});
								// alert('filterString'+filterString);
								// var colIndex=6;

	});
	$('#buyerLeadSearch').change(function(event)
  	{
		$(".buyerLeadSearchList").hide();
  		var searchType = $('#buyerLeadSearch').val();
  		$("#specificeFromDate").val("");
  		$("#dateRangeFromDate").val("");
		$("#dateRangeToDate").val("");
  		$("#specificDateSearch").hide();
  		$("#rangeDateSearch").hide();
  		$("#buyerLeadSearchText").removeAttr("maxlength");
  		//$("#buyer-lead-monitor_info").css("top","355px");
  		if(searchType ==-1)
  		{
  		 oTable.fnFilter('','1');
  		oTable.fnSort( [ [7,'desc'] ] );
  		// $('#buyer-lead-monitor').dataTable().fnDestroy();
  		// oTable._fnAjaxUpdate("MarketFrontend/buyerLeadManagementControllerAction_loadBuyerLeadManagementTabInformationAction.action");
  		// $('#buyer-lead-monitor').dataTable();


  		// oDataTable.fnDraw();
  		 $("#buyerLeadSearchText").val("Enter text....");
		 $("#buyerLeadSearchText").show();
  		 }
  		 else if ((searchType >-1) &&  (searchType <=6) || searchType==11)
		{
			$("#buyerLeadSearchText").val("Enter text....");
			$("#buyerLeadSearchText").show();
			if (searchType==1)
				{
					$("#buyerLeadSearchText").attr("maxlength","10");
				}

		}
		else
		if(searchType==7)
			{
			$('#stateSearch option:first').attr('selected', true)
			$("#stateSearch").show();
			}
		else
			if(searchType==8)
			{
			$('#createdDateSearch option:first').attr('selected', true)
			$("#createdDateSearch").show();
			}
		else
			if(searchType==9)
			{
			$('#projectTypeSearch option:first').attr('selected', true)
			$("#projectTypeSearch").show();
			}
		else
			if(searchType==10)
			{
			$('#leadSearch option:first').attr('selected', true)
			$("#leadSearch").show();
			}
	});

	$('#buyerLeadFilter').change(function(event)
		  	{
		$('#buyerLeadLoadingLogo').show();
		$('#buyer-lead-monitor').hide();
		$('#buyer-lead-monitor_info').hide();
		$('#buyerLeadDiv').hide();
		$(".buyerLeadSearchList").hide();
		$("#buyerLeadSearchText").show();
		$('#buyerLeadSearch option:first').attr('selected', true);
		$("#buyerLeadSearchText").val("Enter text....");
		var filterType = $('#buyerLeadFilter').val();
		 $("#buyerLeadSearchError").hide();
		var searchError=""
  		if(filterType==-1)
  			{
  			 //searchError="Please select a filter for search ";
			 //$("#buyerLeadSearchError").html(searchError);
			 //$("#buyerLeadSearchError").show();

  			 oTable.fnFilter('','1');
  			 oTable.fnSort( [ [7,'desc'] ] );
  			 $('#buyerLeadFilter option:first').attr('selected', true);
  			 $('#buyerLeadSearch option:first').attr('selected', true);
  			 $(".buyerLeadSearchList").hide();
  			 $("#buyerLeadSearchText").show();
  			 $("#buyerLeadSearchText").val("Enter text....");
  			 $("#specificDateSearch").hide();
  			 $("#rangeDateSearch").hide();
  			 $("#buyerLeadSearchError").hide();
  			}
  		/*else if (filterType==2) {
  			 oTable.fnFilter(filterType);
			 oTable.fnFilter("filter",'1');
			 oTable.fnSort([[5,'desc'], [7,'desc']] );
		}*/
		
  			else
  			{
  				 oTable.fnFilter(filterType);
				 oTable.fnFilter("filter",'1');
				 oTable.fnSort( [ [7,'desc'] ] );
  			}

		  	});

	$('#resetBuyerLeadFilter').click(function(event){
		 $('#buyerLeadLoadingLogo').show();
		 $('#buyer-lead-monitor').hide();
		 $('#buyer-lead-monitor_info').hide();
		 $('#buyerLeadDiv').hide();
		 oTable.fnFilter('','1');
		 oTable.fnSort( [ [7,'desc'] ] );
		 $('#buyerLeadFilter option:first').attr('selected', true);
		 $('#buyerLeadSearch option:first').attr('selected', true);
		 $(".buyerLeadSearchList").hide();
		 $("#buyerLeadSearchText").show();
		 $("#buyerLeadSearchText").val("Enter text....");
		 $("#specificDateSearch").hide();
		 $("#rangeDateSearch").hide();
		 $("#buyerLeadSearchError").hide();


	});

	$('#createdDateSearch').click(function(event){
	$("#specificeFromDate").val("");
  	$("#dateRangeFromDate").val("");
	$("#dateRangeToDate").val("");
	var createdDateType=$('#createdDateSearch').val();
	if(createdDateType==1)
	{
		$("#rangeDateSearch").hide();
		$("#specificDateSearch").show();
		//$("#buyer-lead-monitor_info").css("top","385px");
	}
	else if(createdDateType==2)
		{
		$("#specificDateSearch").hide();
		$("#rangeDateSearch").show();
		//$("#buyer-lead-monitor_info").css("top","385px");
	}
	else
	{
	      $("#specificDateSearch").hide();
	      $("#rangeDateSearch").hide();
	     // $("#buyer-lead-monitor_info").css("top","355px");
	}
	});


	$('#specificeFromDate').focus(function(event){
	buyerLeadDatepicker('specificeFromDate');
	});

	$('#dateRangeFromDate').focus(function(event){
		buyerLeadDatepicker('dateRangeFromDate');
	});
	$('#dateRangeToDate').focus(function(event){
			buyerLeadDatepicker('dateRangeToDate');
	});
	function buyerLeadDatepicker(clickedId) {//alert('2-->'+clickedId);
						$('#' + clickedId).datepicker({
							dateFormat : 'mm/dd/yy',
							yearRange : '-50:+50',
							numberOfMonths : 2
						}).datepicker( "show" );
	}
    function fixedDataTableHeaderCss()
    {
    							$(".dataTables_scrollBody").css("overflow","hidden");
		    					$(".dataTables_scrollBody").remove("height");
								$(".dataTables_scrollBody").css("border-bottom","1px solid #CCCCCC");
								$(".dataTables_scrollHead .dataTable").css("height","35px");
								$("#buyer-lead-monitor_wrapper .dataTables_scrollBody").css("height","450px");
								$(".dataTables_scrollHead .dataTables_scrollHeadInner").css("width","100%");
   								$(".dataTables_scrollHead .dataTable").css("display","block");
   								$(".dataTables_scrollHead .dataTables_scroll").css("overflow","hidden");
   								$(".dataTables_scrollHeadInner #firmStatus").css("width","95px");
   								$(".dataTables_scrollHeadInner #slLeadId").css("width","155px");
   								$(".dataTables_scrollHeadInner #providerFirm").css("width","250px");
   								$(".dataTables_scrollHeadInner #description").css("width","130px");
   								$(".dataTables_scrollHeadInner #customerName").css("width","125px");
   								$(".dataTables_scrollHeadInner #phoneNumber").css("width","120px");
   								$(".dataTables_scrollHeadInner #location").css("width","75px");
   								$(".dataTables_scrollHeadInner #createdDate").css("width","190px");
   								$(".dataTables_scrollHead table.dataTable").css("width","100%");
   								//$(".dataTables_scrollHeadInner #lmsLeadId p").css("padding-left","17px");
   								//$(".dataTables_scrollHeadInner #customerName p").css("padding-left","0px");
   								//$(".dataTables_scrollHeadInner #location p").css("padding-left","3px");
   								//$(".dataTables_scrollHeadInner #phoneNumber p").css("padding-left","0px");
   								//$(".dataTables_scrollHeadInner #customerName p").css("padding-left","5px");
   								//$(".dataTables_scrollHeadInner #description p").css("padding-left","12px");
   							    $("#buyer-lead-monitor #firmStatus").css("width","95px");
   								$("#buyer-lead-monitor #slLeadId").css("width","150px");
   								$("#buyer-lead-monitor #providerFirm").css("width","80px");
   								$("#buyer-lead-monitor #description").css("width","180px");
   								$("#buyer-lead-monitor #customerName").css("width","145px");
   								$("#buyer-lead-monitor #phoneNumber").css("width","125px");
   								$("#buyer-lead-monitor #location").css("width","72px");
   								$("#buyer-lead-monitor #createdDate").css("width","200px"); 
    }
	var ProjectNew = {
		    initializeApp : function(){
		        ProjectNew.initTabs("#orderSummaryTab"); //Init tabs

		        //Event listeners
		        $(".tabHandle").bind("click", function(){
		            $(".tabHandle").removeClass("active");
		            $(this).addClass("active");
		            var pageToShow = $(this).attr("data-tab");

		            $(".tabArea").hide();
		            $("#"+pageToShow).show();
		        });

		        $(".accordionHead").bind("click", function(){
		            var accContentPage = $(this).next();
		            $(".accordionContent").hide();
		            $(accContentPage).slideDown("fast");
		        });
		    },

		    initTabs : function(initTabToShow){
		        $(".tabArea").hide();
		        $(initTabToShow).show();
		    },

		    initAccordion : function(initPageToShow){
		        $(".accordionContent").hide();
		        $(initPageToShow).show();
		    },

			editName : function(){
				var providerName = $("#providerName").html();
				$("#editHandle").hide();
				$("#providerName").hide();
				$("#editProvider").show();

				$("#providerNameTextBox").val(providerName);
			},

			cancelEditName : function(){
				$("#editHandle").show();

				$("#providerName").show();
				$("#editProvider").hide();

				$("#providerNameTextBox").empty();
			},

			submitName : function(){
				var nameEntered = $("#providerNameTextBox").val();
				alert("Submit "+nameEntered);

				//Hide the edit area after submit
				this.cancelEditName();
		    }
		};
});

