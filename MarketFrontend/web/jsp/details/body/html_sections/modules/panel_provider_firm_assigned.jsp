<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery.modal.min.css" type="text/css"></link>
	<style>
		.modal {
			    max-width: 100%;
			    width: 100%;
		}
	</style>					
<script type="text/javascript">
jQuery(document).ready(function($){

$('.distanceSort').click(function(){
	var sortOrder='ascending';
	var sortProperty='distance';
	var distarrowDown=$('#distanceArrowDown').css('display');
	var distarrowUp=$('#distanceArrowUp').css('display');
	var groupInd = '${checkGroup}';
	if(distarrowDown=='block'&& distarrowUp=='none' ){
		sortOrder='ascending';
		jQuery("#assign_to_provider").load('SortProviderNameAndDistanceAjax.action?sortProperty='+ sortProperty +'&sortOrder='+ sortOrder+'&isGroup='+groupInd ,function(){
	    	$('#distanceArrowDown').css('display','none');
	    	$('#distanceArrowUp').css('display','block');
	 	});
	}else if(distarrowDown=='none'&& distarrowUp=='block'){
		sortOrder='descending';
		jQuery("#assign_to_provider").load('SortProviderNameAndDistanceAjax.action?sortProperty='+ sortProperty +'&sortOrder='+ sortOrder+'&isGroup='+groupInd ,function(){
			$('#distanceArrowDown').css('display','block');
	    	$('#distanceArrowUp').css('display','none');
		});
	}
	else if(distarrowDown=='none'&& distarrowUp=='none'){
		sortOrder='ascending';
		jQuery("#assign_to_provider").load('SortProviderNameAndDistanceAjax.action?sortProperty='+ sortProperty +'&sortOrder='+ sortOrder+'&isGroup='+groupInd ,function(){
			$('#distanceArrowDown').css('display','none');
	    	$('#distanceArrowUp').css('display','block');
		});
	}
});
});

jQuery('.nameSort').click(function(){
	var groupInd = '${checkGroup}';
	var nameUpArrow = $('#nameArrowUp').css('display');
	var nameDownArrow = $('#nameArrowDown').css('display');
	var sortOrder='ascending';
	var sortProperty='name';
	if('none'==nameUpArrow && 'none' ==nameDownArrow){
		
		sortOrder='ascending';
		jQuery("#assign_to_provider").load('SortProviderNameAndDistanceAjax.action?sortProperty='+ sortProperty +'&sortOrder='+ sortOrder+'&isGroup='+groupInd ,function(){
			$('#nameArrowUp').css('display','block');
			$('#nameArrowDown').css('display','none');		
		});
		
	}else if('block' == nameUpArrow){
		sortOrder='descending';
		jQuery("#assign_to_provider").load('SortProviderNameAndDistanceAjax.action?sortProperty='+ sortProperty +'&sortOrder='+ sortOrder+'&isGroup='+groupInd ,function(){
			$('#nameArrowUp').css('display','none');
			$('#nameArrowDown').css('display','block');		
		});
		}else if('block' == nameDownArrow){
		sortOrder='ascending';
		jQuery("#assign_to_provider").load('SortProviderNameAndDistanceAjax.action?sortProperty='+ sortProperty +'&sortOrder='+ sortOrder+'&isGroup='+groupInd ,function(){
		$('#nameArrowDown').css('display','none');
		$('#nameArrowUp').css('display','block');
		});
	}
});
	
</script>
<c:set var="numberOfProviders" value="${fn:length(summaryDTO.routedProvExceptCounterOffer)}" />
<c:set var="height" value="50"></c:set>
<c:choose>
<c:when test="${numberOfProviders >0 && numberOfProviders < 10}">
	<c:set var="height" value="${ numberOfProviders * 25  }"></c:set>
</c:when>
<c:when test="${numberOfProviders >= 10 }">
	<c:set var="height" value="180"></c:set>	
</c:when>
</c:choose>

<div style="width: 500px; height: 20px; border: 1px solid #9F9F9F;"
	class="hdrTable">
	<div style="float: left; width: 300px;padding-left: 20px;">
		<div id="nameId" style="cursor: pointer; float: left;" id="distanceId" class="nameSort">
			Name </div>
		<div style="float: left;cursor: pointer;" class="nameSort">
		<img id="nameArrowUp"
			src="${staticContextPath}/images/grid/arrow-up-white.gif"
			style="display: none;padding-top: 8px; padding-left: 5px;" />
		<img id="nameArrowDown"
			src="${staticContextPath}/images/grid/arrow-down-white.gif"
			style="display: block;padding-top: 8px; padding-left: 5px;" />
		</div>
	</div>
	<div style="float: left;">
		<div style="cursor: pointer; float: left;" id="distanceId" class="distanceSort">
			Distance(in miles)</div>
		<div style="float: left;cursor: pointer;" class="distanceSort">
			<img
				id="distanceArrowUp"
				src="${staticContextPath}/images/grid/arrow-up-white.gif"
				style="display: none; padding-top: 8px; padding-left: 5px;" />
			<img id="distanceArrowDown"
				src="${staticContextPath}/images/grid/arrow-down-white.gif"
				style="display: block; padding-top: 8px; padding-left: 5px;" />
			
		</div>
	</div>
</div>

<div style="overflow: auto; overflow-x:hidden; height:auto;border: 1px solid #9F9F9F;">
	<c:forEach var="routedResource"
		items="${summaryDTO.routedProvExceptCounterOffer}"
		varStatus="rowCounter">
		<div style="height: 25px;">
			<div style="float: left; width: 290px;padding: 5px 0px 0px 5px;border-right: 1px solid #9F9F9F;">
				<div style="float: left;padding-top: 3px;">
					<input type="radio" id="${routedResource.resourceId}"
						name="routedProvider"
						onclick="selectProvider('${routedResource.resourceId}')" />
				</div>
				<div style="padding-left: 15px;word-wrap: break-word;">
					${routedResource.providerFirstName}
					${routedResource.providerLastName} (${routedResource.resourceId})</div>
			</div>
			<div style="float: left;margin-left: 50px;">
				<div style="padding-left: 5px;">${routedResource.distanceFromBuyer}</div>
			</div>
		</div>
	</c:forEach>
</div>