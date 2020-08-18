<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>	
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<!-- esapi4js dependencies -->
<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/lib/log4js.js"></script>
<!-- esapi4js core -->
<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/esapi.js"></script>
<!-- esapi4js i18n resources -->
<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/resources/i18n/ESAPI_Standard_en_US.properties.js"></script>
<!-- esapi4js configuration -->
<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/resources/Base.esapi.properties.js"></script>

<script type="text/javascript" language="JavaScript">
    Base.esapi.properties.application.Name = "SL Application";
    
    // Initialize the api
    org.owasp.esapi.ESAPI.initialize();

</script>
<head>

	<style type="text/css">
		th, td {
		    padding: 4px;
		}
		.sorting { 
			background: url('${staticContextPath}/images/sort/sort_gen.gif') no-repeat center right; 
		}
		.sorting_asc { 
			background: url('${staticContextPath}/images/sort/sort_desc.gif') no-repeat center right; 
		}
		.sorting_desc { 
			background: url('${staticContextPath}/images/sort/sort_asc.gif') no-repeat center right; 
		}
		.alignLeft {
    		text-align: left !important;
		}
		#coverageProvTable td {
			background-color: white !important;
    		border-bottom: 1px solid #CCCCCC;
    		border-right: 1px solid #CCCCCC;
    		border-left: 1px solid #CCCCCC;
    		padding: 2px 1px 2px 3px;
    		text-align: left;
		}
	</style>
	
	<script type="text/javascript">
		
		jQuery(document).ready(function($) {
			
				var oTable=$('#coverageProvTable').dataTable({
					"bAutoWidth": false,
	        		"sScrollY": "200px",
	        		"bServerSide": true,
	        	    "sDom": "frtiS",
	        	    "bScrollInfinite": true,
	        		"sAjaxSource": "spn/spnMonitorNetwork_viewCoverageDetailsAjax.action",
	        		"aaSorting": [[ 1, "desc" ]],
	        		"oScroller": {
	        			"loadingIndicator": true
	        		},
	        		"aoColumns":[
	        						  {"sClass":"alignLeft"},
	        						  {"sClass":"alignLeft"},
	        						  {"sClass":"alignLeft"},
	        						  {"sClass":"alignLeft"}
	        						  ],
					"fnDrawCallback":function(){
						var seconds = new Date().getTime() / 1000;
						jQuery("#coverageFooterDiv").load("spnReleaseTiers_buttonDeleteAllAjax.action?reloadInd=true&randomnum="+seconds,function(){
						
						});
					}
				});
			
			
				
				 
				 
			//coverage tables
			jQuery('#coverageProvTable_filter').css({'background-color':'#01A9DB','padding':'10px'});
			var label = jQuery('#coverageProvTable_filter').children("label");
			label.css('color','#01A9DB');
			jQuery('#coverageProvTable_info').hide();
			jQuery('#coverageProvTable_paginate').hide();
			label.children("input").attr('id','coverageInput');
			if('${model.routingDTO.spnHdr.criteriaLevel}'=='PROVIDER'){
				label.children("input").val("Search name, Firm or title ");
			}else if('${model.routingDTO.spnHdr.criteriaLevel}'=='FIRM'){
				label.children("input").val("Search Firm or status ");
			}
			
			label.children("input").css({'width':'25%','font-style': 'italic','color': '#AAAAAA','font-size': '11px','padding':'3px','margin-left':'-40px','-webkit-border-radius': '150px',
				'-moz-border-radius': '150px',
			'border-radius': '150px','border-color': 'transparent'});
			label.children("input").after("<i style='position: absolute;margin-left: -20px;margin-top: 7px;color:black;' class='icon-search'></i>");
			jQuery('#coverageProvTable_filter').
				after("<div id='coverageFilters' style='position: absolute;margin-left: 250px; margin-top: -30px;z-index: 999999'>"+
						"<u id='adProvFilter' style='font-size: 11px; color: #ffffff;cursor: pointer;padding-left: 10px;' onclick='showFilters(\"Prov\");'>Advanced Filter</u>"+
						"<div id='stateProv' class='picked pickedClick' style='display: none;padding: 0px;margin-left:5px;width: 120px;position: absolute; margin-top:-6px'>"+
							"<label style='background-position: 100px;'>All States</label>"+
						"</div>"+
						"<div id='stateProvList' class='select-options' style='background-color: #33393C;display: none;margin-top:20px;margin-left:-55px;z-index: 99999;'>"+
							"<c:forEach items='${model.routingDTO.stateList}' var='state' varStatus='status'>"+
								"<c:set var='checked' value='' ></c:set>"+
								"<c:forEach items='${model.routingDTO.selectedStates}' var='selectedState' >"+
									"<c:if test='${state.value == selectedState}'>"+
										"<c:set var='checked' value=' checked ' ></c:set>"+
									"</c:if>"+
								"</c:forEach>"+
								"<div style='clear: left;'>"+
									"<input type='checkbox' class='stateCheck' name='model.routingDTO.selectedStates[${status.count}]' value='${state.value}' ${checked}/>${state.key}"+
								"</div>"+
							"</c:forEach>"+
						"</div>"+
						"<div id='marketProv' class='picked pickedClick' style='display:none;padding: 0px;width: 120px;position: absolute; margin-top:-6px; margin-left: 140px'>"+
							"<label style='background-position: 100px;'>All Markets</label>"+
						"</div>"+
						"<div id='marketProvList' class='select-options' style='background-color: #33393C;display: none;margin-top: 20px;margin-left: 140px;z-index: 99999;'>"+
							"<c:forEach items='${model.routingDTO.marketList}' var='market' varStatus='status'>"+
								"<c:set var='checked' value='' ></c:set>"+
								"<c:forEach items='${model.routingDTO.selectedMarkets}' var='selectedMarket' >"+
									"<c:if test='${market.value == selectedMarket}'>"+
										"<c:set var='checked' value=' checked ' ></c:set>"+
									"</c:if>"+
								"</c:forEach>"+
								"<div style='clear: left;'>"+
									"<input type='checkbox' class='marketCheck' name='model.routingDTO.selectedMarkets[${status.count}]' value='${market.value}' ${checked}/>${market.key}"+
								"</div>"+
							"</c:forEach>"+
						"</div>"+
						"<select id='zipProv' class='chosen' name='model.routingDTO.selectedZip' data-placeholder='Zip Code' style='display:none;padding: 0px;width: 120px;position:absolute;'>"+
							"<option value='' style='display:none;'>Zip Code</option>"+
							"<c:forEach items='${model.routingDTO.zipList}' var='zip' varStatus='status'>"+
									"<c:if test='${zip == model.routingDTO.selectedZip}'>"+
										"<option value='${zip}' selected='selected'>${zip}</option>"+	
									"</c:if>"+
									"<c:if test='${zip != model.routingDTO.selectedZip}'>"+
										"<option value='${zip}'>${zip}</option>"+	
									"</c:if>"+	
								
							"</c:forEach>"+
						"</select>"+
						"<div id='resetProv' style='font-size: 11px; color: #ffffff;cursor: pointer;display:none;margin-left: 550px;' onclick='resetFilters(\"Prov\");'><u>Reset Filter</u></div>"+
					 "</div>"); 
						
			$('#coverageInput').focus(function(){
				clearInput();
			});
			
			$('#coverageInput').blur(function(){
				resetInput();
			});
			
			$('#coverageInput').keyup(function(){
				//resetCount();
			});
			
			$('#coverageInput').keydown(function(){
				//resetCount();
			});		
			
			$('body').unbind('click').click(function() {
				$('.select-options').hide();	
				var applyFilter = $("#applyFilterInd").val();		
				if(1 == applyFilter){
					$("#applyFilterInd").val(0);
					var count = $('#marketProvList').children('div').children(':checked').length;
					if (count > 0){
						$('#marketLabel').val(count + ' Selected');
					}
					else{
						$('#marketLabel').val('All Markets');
					}
					var marketInd = $("#marketInd").val();
					if(1 == marketInd){
						$("#filterDiv").jqm({modal : true,overlay : 0});
						$("#filterDiv").jqmShow();
						$("#marketInd").val(0);
						var marketList = [];
						$('#marketProvList').children('div').children(':checked').each(function() {
							marketList.push($(this).val());
						});
						var coverage = '${savedRoutingDTO.coveragelist}';
						if(null != marketList && marketList.length != 0){
							$('#zipProv').children().remove();
							$('#zipProv').append("<option value='' style='display:none;'>Zip Code</option>");
							var zips = [];
							for(var i=0; i<count; i++){
								if(null != marketList[i]){
									<c:forEach var="coverage" items="${savedRoutingDTO.coveragelist}">
										var market = "<c:out value="${coverage.marketId}"/>";
										if(market == marketList[i]){
											zips.push('${coverage.zip}');
										}
									</c:forEach>
								}
							}	
							if(null != zips && zips.length != 0){
								zips = eliminateDuplicates(zips);
								zips = zips.sort();
								for(var j=0; j<zips.length; j++){
									$('#zipProv').append("<option value='"+zips[j]+"'>"+zips[j]+"</option>");
								}
								$('#resetProv').css('margin-top','-25px');
				  				jQuery_1_4_2('.chosen').chosen();
				  				jQuery_1_4_2("#zipProv").trigger("liszt:updated");
				  				$('#zipProv_chzn').css('width','120px');
				  				$('#zipProv_chzn').css('margin-left','280px');
				  				$('#zipProv_chzn').css('margin-top','-6px');
				  				$('#zipProv_chzn').show();
							}
						}
						$("#filterDiv").jqmHide();
					}
					else{
						var resetInd = $("#resetInd").val();
						var url = 'spnReleaseTiers_buttonDeleteAllAjax.action';
						if(resetInd == 'true'){
							url = 'spnReleaseTiers_buttonDeleteAllAjax.action?resetInd=true';
						}
						var formValues = $('#routingPriorityForm').serializeArray();
						$("#filterDiv").jqm({modal : true,overlay : 0});
						$("#filterDiv").jqmShow();
						$.ajax({
					  		url: url,
					  		type: "POST",
				        	data: formValues,
							dataType : "html",
					  		success: function( data ) {
					  			$("#filterDiv").jqmHide();
					  			$("#coverageDiv").html(data);
					  			jQuery('#tabPHdr2').trigger("click");
								jQuery('#tabPHdr2').trigger("click");
								jQuery('#tabFHdr2').trigger("click");
								jQuery('#tabFHdr2').trigger("click");
					  			if($('#marketProvList').children('div').children(':checked').length >0){
					  				$('#resetProv').css('margin-top','-25px');
					  				jQuery_1_4_2('.chosen').chosen();
					  				$('#zipProv_chzn').css('width','120px');
					  				$('#zipProv_chzn').css('margin-left','280px');
					  				$('#zipProv_chzn').css('margin-top','-6px');
					  				$('#zipProv_chzn').show();
					  			}
							}
						});
					}
				}
			});
				 
			$('.select-options').unbind('click').click(function(event){
				event.stopPropagation();
			});
			
			$('.pickedClick').unbind('click').click(function(event) {
				event.stopPropagation();
				if(this.id == 'marketProv' && $('#stateProv').children('label').html() == 'All States'){
					openFilterOptions(this);	
				}
				else if(this.id == 'stateProv' && $('#marketProv').children('label').html() == 'All Markets'){
					openFilterOptions(this);	
				}
			});
			
			$('.pickedClick').each(function() {
				presetFilters(this);	
			});
			
			$('[type=checkbox]').click(function() {
				onFilterSelect($(this));
			});  
			
			var reset = $("#resetInd").val();
			if('true' == reset){
				$('#adProvFilter').hide();
				$('#stateProv').show();
				$('#marketProv').show();
				$('#resetProv').show();
			}
			
			<c:if test='${fn:length(model.routingDTO.selectedMarkets) > 0 
							|| fn:length(model.routingDTO.selectedStates) > 0 || null != model.routingDTO.selectedZip}'>
				$('#adProvFilter').hide();
				$('#stateProv').show();
				$('#marketProv').show();
				$('#resetProv').show();
			</c:if>
			
			<c:if test="${'Zip Code' != model.routingDTO.selectedZip && null != model.routingDTO.selectedZip}">
				$('#zipValue').val('${model.routingDTO.selectedZip}');
			</c:if>
					
			$('.marketCheck').click(function() {
				var count = $(".marketCheck[type='checkbox']:checked").length;
				if($(this).is(':checked') || count > 0){
					$("#applyFilterInd").val("1");
					$("#resetInd").val("false");
					$("#marketInd").val("1");
					$('#zipProv option[value=""]').attr('selected','selected');					
					jQuery('.chzn-single').find('span').html('Zip Code');
					jQuery('#zipValue').val(0);
				}				
				else {
					$("#marketInd").val("0");
					$('#zipProv').hide();
					$('#zipProv_chzn').hide();
					$('#resetProv').css('margin-top','0px');
					$('#zipProv option[value=""]').attr('selected','selected');					
					jQuery('.chzn-single').find('span').html('Zip Code');
					jQuery('#zipValue').val(0);
					var prevVal = $('#marketLabel').val();
					//To apply filter when user un checks all the selections
					if("All Markets" !== prevVal){
						$("#applyFilterInd").val("1");
						$("#resetInd").val("true");
						$("#marketInd").val("0");
					}
				}
			});
			
			$('.stateCheck').click(function() {
				var count = $(".stateCheck[type='checkbox']:checked").length;
				$('#zipProv').hide();
				$('#zipProv_chzn').hide();
				$('#resetProv').css('margin-top','0px');
				$('#zipProv option[value=""]').attr('selected','selected');
				jQuery('.chzn-single').find('span').html('Zip Code');
				jQuery('#zipValue').val(0);
				if($(this).is(':checked') || count > 0){
					$("#applyFilterInd").val("1");
					$("#resetInd").val("false");
				}				
				else {
					var prevVal = $('#stateLabel').val();
					//To apply filter when user un checks all the selections
					if("All States" !== prevVal){
						$("#applyFilterInd").val("1");
						$("#resetInd").val("true");
					}
				}
			});
			
			jQuery(document).click(function(e){ 
				var click=$(e.target);
				if(click.hasClass(".active-result")){
					 setTimeout(function(){
					    	var zip = jQuery('.chzn-single').find('span').html();
					    	var zipVal = jQuery('#zipValue').val();
							if(null != zip && 'Zip Code' != zip && zip != zipVal){
								jQuery('#zipValue').val(zip);								
								$('.select-options').hide();	
								var url = 'spnReleaseTiers_buttonDeleteAllAjax.action';
								var formValues = $('#routingPriorityForm').serializeArray();
								$("#filterDiv").jqm({modal : true,overlay : 0});
								$("#filterDiv").jqmShow();
								$.ajax({
								  	url: url,
								  	type: "POST",
							        data: formValues,
									dataType : "html",
								  	success: function( data ) {
								  		$("#filterDiv").jqmHide();
								  		$("#coverageDiv").html(data);
								  		jQuery('#tabPHdr2').trigger("click");
										jQuery('#tabPHdr2').trigger("click");
										jQuery('#tabFHdr2').trigger("click");
										jQuery('#tabFHdr2').trigger("click");
								  		if($('#marketProvList').children('div').children(':checked').length >0){
								  			$('#resetProv').css('margin-top','-25px');
								  			jQuery_1_4_2('.chosen').chosen();
								  			$('#zipProv_chzn').css('width','120px');
								  			$('#zipProv_chzn').css('margin-left','280px');
								  			$('#zipProv_chzn').css('margin-top','-6px');
								  			$('#zipProv_chzn').show();
								  		}
									}
								});
							}
						},100);
				}				
			});
			
			jQuery.extend( jQuery.fn.dataTableExt.oSort, {
		        "percent-pre": function ( a ){
		            var x = (a == "-") ? 0 : a.replace( /%/, "" );
		            return parseFloat( x );
		         },
		        "percent-asc": function ( a, b ){
		            return ((a < b) ? -1 : ((a > b) ? 1 : 0));
		         },
		        "percent-desc": function ( a, b ) {
		            return ((a < b) ? 1 : ((a > b) ? -1 : 0));
		         },
		         "provNo-pre": function ( a ){
			        return parseFloat( a );
			     },
			     "provNo-asc": function ( a, b ){
			        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
			     },
			     "provNo-desc": function ( a, b ) {
			        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
			     }
	        });
			
		    jQuery(document).keyup(function(e) {
		    	e.preventDefault();
		        $('.select-options').hide();
				if(e.which == 13) {
					var applyFilter = $("#applyFilterInd").val();	
				    if(0 == applyFilter){
					    setTimeout(function(){
					    	var zip = jQuery('.chzn-single').find('span').html();
					    	var zipVal = jQuery('#zipValue').val();
							if(null != zip && 'Zip Code' != zip && zip != zipVal){
								jQuery('#zipValue').val(zip);
								var url = 'spnReleaseTiers_buttonDeleteAllAjax.action';
								var formValues = $('#routingPriorityForm').serializeArray();
								$("#filterDiv").jqm({modal : true,overlay : 0});
								$("#filterDiv").jqmShow();
								$.ajax({
								  	url: url,
								  	type: "POST",
							        data: formValues,
									dataType : "html",
								  	success: function( data ) {
								  		$("#filterDiv").jqmHide();
								  		$("#coverageDiv").html(data);
								  		jQuery('#tabPHdr2').trigger("click");
										jQuery('#tabPHdr2').trigger("click");
										jQuery('#tabFHdr2').trigger("click");
										jQuery('#tabFHdr2').trigger("click");
								  		if($('#marketProvList').children('div').children(':checked').length >0){
								  			$('#resetProv').css('margin-top','-25px');
								  			jQuery_1_4_2('.chosen').chosen();
								  			$('#zipProv_chzn').css('width','120px');
								  			$('#zipProv_chzn').css('margin-left','280px');
								  			$('#zipProv_chzn').css('margin-top','-6px');
								  			$('#zipProv_chzn').show();
								  		}
									}
								});
							}
						},100);
					}
					else if(1 == applyFilter){
						jQuery('.chzn-single').find('span').html('Zip Code');
						jQuery('#zipValue').val(0);
						$("#applyFilterInd").val(0);
						var count = $('#marketProvList').children('div').children(':checked').length;
						if (count > 0){
							$('#marketLabel').val(count + ' Selected');
						}
						else{
							$('#marketLabel').val('All Markets');
						}
						var marketInd = $("#marketInd").val();
						if(1 == marketInd){
							$("#filterDiv").jqm({modal : true,overlay : 0});
							$("#filterDiv").jqmShow();
							$("#marketInd").val(0);
							var marketList = [];
							$('#marketProvList').children('div').children(':checked').each(function() {
								marketList.push($(this).val());
							});
							var coverage = '${savedRoutingDTO.coveragelist}';
							if(null != marketList && marketList.length != 0){
								$('#zipProv').children().remove();
								$('#zipProv').append("<option value='' style='display:none;'>Zip Code</option>");
								var zips = [];
								for(var i=0; i<count; i++){
									if(null != marketList[i]){
										<c:forEach var="coverage" items="${savedRoutingDTO.coveragelist}">
											var market = "<c:out value="${coverage.marketId}"/>";
											if(market == marketList[i]){
												zips.push('${coverage.zip}');
											}
										</c:forEach>
									}
								}	
								if(null != zips && zips.length != 0){
									zips = eliminateDuplicates(zips);
									zips = zips.sort();
									for(var j=0; j<zips.length; j++){
										$('#zipProv').append("<option value='"+zips[j]+"'>"+zips[j]+"</option>");
									}
									$('#resetProv').css('margin-top','-25px');
					  				jQuery_1_4_2('.chosen').chosen();
					  				jQuery_1_4_2("#zipProv").trigger("liszt:updated");
					  				$('#zipProv_chzn').css('width','120px');
					  				$('#zipProv_chzn').css('margin-left','280px');
					  				$('#zipProv_chzn').css('margin-top','-6px');
					  				$('#zipProv_chzn').show();
								}
							}
							$("#filterDiv").jqmHide();
						}
						else{
							var resetInd = $("#resetInd").val();
							var url = 'spnReleaseTiers_buttonDeleteAllAjax.action';
							if(resetInd == 'true'){
								url = 'spnReleaseTiers_buttonDeleteAllAjax.action?resetInd=true';
							}
							var formValues = $('#routingPriorityForm').serializeArray();
							$("#filterDiv").jqm({modal : true,overlay : 0});
							$("#filterDiv").jqmShow();
							$.ajax({
							 	url: url,
							 	type: "POST",
							    data: formValues,
								dataType : "html",
								success: function( data ) {
								  	$("#filterDiv").jqmHide();
								  	$("#coverageDiv").html(data);
								  	jQuery('#tabPHdr2').trigger("click");
									jQuery('#tabPHdr2').trigger("click");
									jQuery('#tabFHdr2').trigger("click");
									jQuery('#tabFHdr2').trigger("click");
								  	if($('#marketProvList').children('div').children(':checked').length >0){
								  		$('#resetProv').css('margin-top','-25px');
								  		jQuery_1_4_2('.chosen').chosen();
								  		$('#zipProv_chzn').css('width','120px');
								  		$('#zipProv_chzn').css('margin-left','280px');
								  		$('#zipProv_chzn').css('margin-top','-6px');
								  		$('#zipProv_chzn').show();
								  	}
								}
							});
						}
					}
				}
			});
		    
		    jQuery('.ids').css('font-size','11px');
			
		    
		});
		
		
		
		function presetFilters(myThis)
		{
			var numSelected = $('#'+myThis.id+'List').children('div').children(':checked').length;
			if (numSelected > 0){
				$('#'+myThis.id).children('label').html(numSelected + ' Selected');
				if(myThis.id == 'stateProv'){
					$('#stateLabel').val(numSelected + ' Selected');
					$('#marketProv').children('label').css('background-color','#D8D8D8');
				}
				else if(myThis.id == 'marketProv'){
					$('#marketLabel').val(numSelected + ' Selected');
					$('#stateProv').children('label').css('background-color','#D8D8D8');
				}
			}
			else{
				if(myThis.id == 'stateProv'){
					$('#'+myThis.id).children('label').html('All States');
					$('#stateLabel').val('All States');
					$('#marketProv').children('label').css('background-color','#fff');
				}
				else if(myThis.id == 'marketProv'){
					$('#'+myThis.id).children('label').html('All Markets');
					$('#marketLabel').val('All Markets');
					$('#stateProv').children('label').css('background-color','#fff');
				}
			}	
		}
		
		function openFilterOptions(myThis)
		{
			var hidden = false;
			if ($('#'+myThis.id+'List:hidden').length > 0){
				hidden = true;
			}			
			$('.select-options').hide();	
			if (hidden == true){
				$('#'+myThis.id+'List').show();
			}
		}
		
		function onFilterSelect(myThis)
		{
			if (myThis.parent('div').parent('div.select-options').length == 1){
				var id = myThis.parent('div').parent('div.select-options').attr('id');
				id = id.substring(0,id.length-4);
				var numSelected = myThis.parent('div').parent('div.select-options').children('div').children(':checked').length;
				if (numSelected > 0){
					$('#'+id).children('label').html(numSelected + ' Selected');
					if(id == 'stateProv'){
						$('#marketProv').children('label').css('background-color','#D8D8D8');
					}
					else if(id == 'marketProv'){
						$('#stateProv').children('label').css('background-color','#D8D8D8');
					}
				}
				else{
					if(id == 'stateProv'){
						$('#'+id).children('label').html('All States');
						$('#marketProv').children('label').css('background-color','#fff');
					}
					else if(id == 'marketProv'){
						$('#'+id).children('label').html('All Markets');
						$('#stateProv').children('label').css('background-color','#fff');
					}
				}	
			}
		}
		
		function sortIcon(){
			for(i=1;i<=4;i++){
				var element = jQuery('#tabFHdr'+i).children("i");
				element.removeClass('icon-sort');
				element.removeClass('icon-sort-up');
				element.removeClass('icon-sort-down');
				if(jQuery('#tabFHdr'+i).hasClass('sorting')){
					element.addClass('icon-sort');
				}
				else if(jQuery('#tabFHdr'+i).hasClass('sorting_asc')){
					element.addClass('icon-sort-up');
				}
				else if(jQuery('#tabFHdr'+i).hasClass('sorting_desc')){
					element.addClass('icon-sort-down');
				}
			}
		}
		
		function showFilters(level){
			jQuery("#ad"+level+"Filter").hide();
			jQuery("#state"+level).show();
			jQuery("#market"+level).show();
			jQuery("#reset"+level).show();
		}
		
		function resetFilters(level){
			var formValues = $('#routingPriorityForm').serializeArray();
			$("#filterDiv").jqm({modal : true,overlay : 0});
			$("#filterDiv").jqmShow();
			$.ajax({
				  url: 'spnReleaseTiers_buttonDeleteAllAjax.action?resetInd=true',
				  type: "POST",
			      data: formValues,
				  dataType : "html",
				  success: function( data ) {
				  	$("#filterDiv").jqmHide();
				  	var textArea = document.createElement('textarea');
  					textArea.innerHTML = $ESAPI.encoder().encodeForHTML(data);
				  	$("#coverageDiv").html(textArea.value);
				  	jQuery("#ad"+level+"Filter").show();
					jQuery("#state"+level).hide();
					jQuery("#state"+level).children('label').html('All States');
					jQuery("#state"+level+"List").children('div').children(':checked').attr('checked','');
					jQuery("#market"+level).hide();
					jQuery("#market"+level).children('label').html('All Markets');
					jQuery("#market"+level+"List").children('div').children(':checked').attr('checked','');
					jQuery("#zip"+level).hide();
					jQuery("#reset"+level).hide();
					jQuery('#tabPHdr2').trigger("click");
					jQuery('#tabPHdr2').trigger("click");
					jQuery('#tabFHdr2').trigger("click");
					jQuery('#tabFHdr2').trigger("click");
				  }
			});			
		}
		
		function clearInput(){
			var input = jQuery('#coverageProvTable_filter').children("label").children("input");
			if(input.val() == 'Search name, Firm or title ' || input.val() == 'Search Firm or status '){
				input.val("");
				input.css({'font-style': 'normal','color': '#000000'});
			}
		}
		
		function resetInput(){
			var input = jQuery('#coverageProvTable_filter').children("label").children("input");
			if(input.val() == ''){
				
			if('${model.routingDTO.spnHdr.criteriaLevel}'=='PROVIDER'){
				input.val("Search name, Firm or title ");
			}else if('${model.routingDTO.spnHdr.criteriaLevel}'=='FIRM'){
				input.val("Search Firm or status ");
			}
				input.css({'font-style': 'italic','color': '#AAAAAA'});
			}
		}
		
		function resetValue(value){
			var id = value.id;
			if(jQuery('#'+id).attr('value') == ''){
				jQuery('#'+id).attr('value','0');
			}
		}
		
		/* function resetCount(){
			var countText = jQuery('#coverageProvTable_info').html();
			var countRE = countText.match("of(.*)entries");
			var text = jQuery.trim(countRE[1]);
			var count = text.split(" ")[0];
			jQuery('#memberCnt').html(count);        
		    var total = 0;
		    var rows = $("#coverageProvTable").dataTable().$('tr', {"filter": "applied"});
		    for(var i=0;i<count;i++){
		    	var provCnt = $(rows[i]).find("td:eq(3)").text();
			    provCnt = parseInt(provCnt);		    	
			    if (!isNaN(provCnt)){
					total = total + provCnt;
				}
		    }
			$('#totalProv').html(total);
		} */
		
		function eliminateDuplicates(arr) {
			 var i,
			 	len=arr.length,
			 	out=[],
			 	obj={};
			 
			 for (i=0;i<len;i++) {
			 	obj[arr[i]]=0;
			 }
			 for (i in obj) {
				out.push(i);
			 }
			 return out;
		}
		
		//to open provider profile in new window
		function openProvProfile (resouceId, vendorId) {
			var url = "/MarketFrontend/providerProfileInfoAction_execute.action?resourceId="
						+ resouceId + "&companyId=" + vendorId + "&popup=true";
			newwindow = window.open (url, '_publicproviderprofile','resizable=yes, scrollbars=yes, status=no, height=700, width=1000');
			if (window.focus) {
				newwindow.focus ();
			}
			return false;		
		}
		
		//to open firm profile in new window
		function openFirmProfile(vendorId) {
			if (document.openProvURL != null){
				document.openProvURL.close();
			}
			var url = "/MarketFrontend/providerProfileFirmInfoAction_execute.action?vendorId=" + vendorId + "&popup=true";
			newwindow = window.open(url,'_publicproviderprofile','resizable=yes,scrollbars=yes,status=no,height=700,width=1000');
			if (window.focus) {
				newwindow.focus()
			}
			document.openProvURL = newwindow;
		}		
		
	</script>
</head>

<body>
    <c:if test="${model.routingDTO.spnHdr.criteriaLevel == 'PROVIDER'}">
        <div class="filterDiv">
            <b style="font-size: 13px;">Provider Details</b>
            <hr>
            
            <c:set var="disclaimer" value="0"></c:set> 
            <c:choose>             
	            <c:when test="${null != model.routingDTO.coveragelist && fn:length(model.routingDTO.coveragelist) > 0}">
	                <table id="coverageProvTable" cellpadding="0" cellspacing="0" border="0" style="background-color: #ffffff;">
	                    <thead>
	                        <tr style="background-color: #CCCCCC;">
	                            <th id="tabFHdr1" class="tabPHdr1">
	                                Provider Name
	                            </th>
	                            <th id="tabFHdr2" class="tabPHdr2">
	                                Performance Score
	                            </th>
	                            <th id="tabFHdr4" class="tabPHdr1">
	                                Firm
	                            </th>
	                            <th id="tabFHdr5" class="tabPHdr2">
	                                Title
	                            </th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                     </tbody>                       
	                </table>
	            </c:when>
	            <c:otherwise>
	                <div style="width:100%;padding-top:0px;height:20px;display:block;text-align:center;"><b>There are no providers in this SPN</b></div>
	            </c:otherwise>
            </c:choose>
           <div  id="coverageFooterDiv"></div>
        </div> 
    </c:if>                   
       
    <c:if test="${model.routingDTO.spnHdr.criteriaLevel == 'FIRM'}">
        <div class="filterDiv">
            <b style="font-size: 13px;">Firm Details</b>
            <hr>
            
            <c:set var="disclaimer" value="0"></c:set> 
            <c:choose>          
	            <c:when test="${null != model.routingDTO.coveragelist && fn:length(model.routingDTO.coveragelist) > 0}">
	                <table id="coverageProvTable" cellpadding="0" cellspacing="0" border="0" style="background-color: #ffffff;">
	                    <thead>
	                        <tr style="background-color: #CCCCCC;">
	                            <th id="tabFHdr1" class="tabPHdr1">
	                                Provider Firm
	                            </th>
	                            <th id="tabFHdr2" class="tabPHdr2">
	                                Performance Score
	                            </th>
	                            <th id="tabFHdr4" class="tabPHdr1">
	                                SL Status
	                            </th>                       
	                            <th id="tabFHdr5" class="tabPHdr2">
	                                # of SPN Approved Providers
	                            </th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                        
	                    </tbody>
	                </table>
	            </c:when>
	            <c:otherwise>
	                <div style="width:100%;padding-top:0px;height:20px;display:block;text-align:center;">
	                    <b>There are no firms in this SPN</b></div>
	            </c:otherwise>
            </c:choose>
           <div  id="coverageFooterDiv"></div>
        </div> 
    </c:if>   
   
    <input id="zipValue" type="hidden" value="0"/>   
   
    <div id="filterDiv" style="display: none;z-index: 999999" class="jqmWindow">
        <div style="padding: 10px;text-align: center;">
            <span>Filtering, please wait...</span>
            <div>
                <img src="${staticContextPath}/images/simple/searchloading.gif" />
            </div>
           
        </div>
    </div>
       
</body>