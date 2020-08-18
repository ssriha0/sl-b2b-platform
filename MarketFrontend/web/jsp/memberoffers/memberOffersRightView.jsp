<%@ page language="java" import="java.util.*, com.servicelive.routingrulesengine.RoutingRulesConstants" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>




<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<html>
<head>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/memberOffer.css"/>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
	
<script type="text/javascript">
jQuery(document).ready(function () {
		var sortType = '${offerCriterias.sortCriteria}';
		var viewCount = '${offerCriterias.perPageOfferCount}';
		var verticalViewClicked='${staticContextPath}/images/member_offers/list_view_selected.png';
		var treeViewClicked='${staticContextPath}/images/member_offers/grid_view_selected.png';
		var verticalView='${staticContextPath}/images/member_offers/list_view.png';
		var treeView='${staticContextPath}/images/member_offers/grid_view.png';
		var offerView ='${offerCriterias.offerView}';
		if(offerView=='tree'){
			$('.treeClass').attr('src',treeViewClicked);
			$('.verticalClass').attr('src',verticalView);
			
		}else{
			$('.verticalClass').attr('src',verticalViewClicked);
			$('.treeClass').attr('src',treeView);
		}
		$("#memberOffer_sortBy_bottom").val( sortType ).attr('selected',true);
		$("#memberOffer_sortBy_top").val( sortType ).attr('selected',true);
		$("#memberOffer_view_top").val( viewCount ).attr('selected',true);
		$("#memberOffer_view_bottom").val( viewCount ).attr('selected',true);
		
	
	$('.sortBy').change(function(){
		var sortOption = $(this).attr('value');
		$(".sortBy").val( sortOption ).attr('selected',true);
		var perPageCount = $('#memberOffer_view_top option:selected').val();
		var offerView = '${offerCriterias.offerView}';
		$('#rightMemberOfferSide').load("memberOffersFilter_applyFilters.action?sortCriteria="+sortOption+"&offerCount="+perPageCount+"&currentPageNo=1&offerview="+offerView);
		});
	
	$('.memberOfferView').change(function(){
		var sortOption = $('#memberOffer_sortBy_top option:selected').val();
		var perPageCount = $(this).attr('value');
		$(".memberOfferView").val( perPageCount ).attr('selected',true);
		var offerView = '${offerCriterias.offerView}';
		$('#rightMemberOfferSide').load("memberOffersFilter_applyFilters.action?sortCriteria="+sortOption+"&offerCount="+perPageCount+"&currentPageNo=1&offerview="+offerView);
		});	
	
	$('.treeClass').click(function(){
		var sortOption = $('#memberOffer_sortBy_top option:selected').val();
		var perPageCount = $('#memberOffer_view_top option:selected').val();
		var pageNo = '${offerCriterias.currentPageNo}';
		$('#rightMemberOfferSide').load("memberOffersFilter_applyFilters.action?sortCriteria="+sortOption+"&offerCount="+perPageCount+"&currentPageNo="+pageNo+"&offerview=tree");
		});
	
	$('.verticalClass').click(function(){
		var sortOption = $('#memberOffer_sortBy_top option:selected').val();
		var perPageCount = $('#memberOffer_view_top option:selected').val();
		var pageNo = '${offerCriterias.currentPageNo}';
		$('#rightMemberOfferSide').load("memberOffersFilter_applyFilters.action?sortCriteria="+sortOption+"&offerCount="+perPageCount+"&currentPageNo="+pageNo+"&offerview=vertical");
		});
	
	$('.paginationArrow').click(function(){
		var sortOption = $('#memberOffer_sortBy_top option:selected').val();
		var perPageCount = $('#memberOffer_view_top option:selected').val();
		var pageNo = $(this).attr('id');
		var offerView = '${offerCriterias.offerView}';
		$('#rightMemberOfferSide').load("memberOffersFilter_applyFilters.action?sortCriteria="+sortOption+"&offerCount="+perPageCount+"&currentPageNo="+pageNo+"&offerview="+offerView);
		});
	
	$('.tabControl').keypress(function (e) {
		var ENTER = 13;
        if (e.which == ENTER) {
        	$(this).click();
        }
    });
});
</script>
</head>
<body>
	<!-- Implementation of Right side panel header for member offers -->
	<c:if test="${fn:length(offerList) != 0}">
	<div class="rightTableHdr">
	    <div style="float: left;margin-left: 30px;vertical-align: middle;margin-top: 14px;">
			<fmt:message bundle="${serviceliveCopyBundle}" key="memberOffers.sortBy.label"/>
		</div>
		<div style="float: left;margin-left: 10px;vertical-align: middle;margin-top: 6px;">
			<select name="memberOffer_sortBy_top" style="font-size: 13px;" id ="memberOffer_sortBy_top" class = "sortBy" tabindex="1">
				<option value="3"><fmt:message bundle="${serviceliveCopyBundle}" key="sort.option.company.name.a.to.z"/></option>
                <option value="4"><fmt:message bundle="${serviceliveCopyBundle}" key="sort.option.company.name.z.to.a"/></option>
                <option value="1"><fmt:message bundle="${serviceliveCopyBundle}" key="sort.option.newest.to.oldest"/></option>
                <option value="2"><fmt:message bundle="${serviceliveCopyBundle}" key="sort.option.oldest.to.newest"/></option>
                <option value="5"><fmt:message bundle="${serviceliveCopyBundle}" key="sort.option.most.popular"/></option>
			</select>
		</div>
		<div style="float: left;margin-left: 20px;vertical-align: middle;margin-top: 14px;">
		   	<fmt:message bundle="${serviceliveCopyBundle}" key="memberOffers.view.label"/>
		</div>
		<div style="float: left;margin-left: 10px;vertical-align: middle;margin-top: 6px;">
			<select name="memberOffer_view_top" style="width: 60px;" id="memberOffer_view_top" class = "memberOfferView" tabindex="2">
				<option value="6"><fmt:message bundle="${serviceliveCopyBundle}" key="view.option.6"/></option>
				<option value="12"><fmt:message bundle="${serviceliveCopyBundle}" key="view.option.12"/></option>
			</select>
		</div>
		<div style="float: left;margin-left: 20px;margin-top: 14px;vertical-align: middle;">
			<fmt:message bundle="${serviceliveCopyBundle}" key="memberOffers.viewAs.label"/>
		</div>
		<div style="float: left;margin-left: 5px;margin-top: 12px;vertical-align: middle;">
			<div>
				<img id="treeView_top"	class="btn20Bevel treeClass tabControl"	style="visibility: visible;cursor: pointer;"  title="Tree View" tabindex="3"/>
				<img id="horizontalView_top" class="btn20Bevel verticalClass tabControl" style="visibility: visible;cursor: pointer;" title="Horizontal View" tabindex="4"/>
			</div>			
		</div>
		<div style="float: right;margin-right: 15px;margin-top: 10px;vertical-align: middle;font-size: 14px;">
			<c:if test="${offerCriterias.currentPageNo>1}">
				<a  class="paginationArrow tabControl" id="1" style="color: #00A0D2;cursor: pointer;" tabindex="5">
					<b style="font-size: 18px;"> << </b>
				</a>
				
				<a  class="paginationArrow tabControl" id="${offerCriterias.currentPageNo-1}" style="color: #00A0D2;cursor: pointer;" tabindex="6">
					<b style="font-size: 18px;"><</b>
				</a>
				
			</c:if>
			Page ${offerCriterias.currentPageNo} of ${offerCriterias.totalNumberOfPages}
			<c:if test="${offerCriterias.totalNumberOfPages != offerCriterias.currentPageNo}">
				<a  class="paginationArrow tabControl" id="${offerCriterias.currentPageNo+1}" style="color: #00A0D2;cursor: pointer;" tabindex="7">
					<b style="font-size: 18px;">></b>
				</a>
				<a  class="paginationArrow tabControl" id="${offerCriterias.totalNumberOfPages}" style="color: #00A0D2;cursor: pointer;" tabindex="8">
					<b style="font-size: 18px;">>></b>
				</a>
			</c:if>
		</div>
	</div>
	</c:if>
	<!--<s:set name="myListSize" value="offerList.size()"/>-->
	<!-- Implementation of Right side panel data for member offers -->
        <c:choose>
        <c:when test="${fn:length(offerList) == 0}">
         <div style="border: 1px solid #E2E5E7;">
				<div align="center" style="position:relative;height:478px;">
				  <div style="padding-top:200px;">
				    <h1>No offers available</h1>
				  </div>
				 </div>
				
		 </div>
        </c:when>
        <c:when test="${offerCriterias.offerView=='tree'}">
		   <div style="border: 1px solid #E2E5E7;">
				<div>&nbsp;&nbsp;</div>
				<jsp:include page="memberOffersTreeView.jsp" ></jsp:include>
			</div>							
		</c:when>
		<c:when test="${offerCriterias.offerView=='vertical'}">
		   <div style="border: 1px solid #E2E5E7;">
				<div style="height: 25px">&nbsp;&nbsp;</div>
				<jsp:include page="memberOffersVerticalView.jsp" ></jsp:include>
			</div>							
		</c:when>
		<c:otherwise></c:otherwise>
        </c:choose>
	<!-- Implementation of Right side panel footer for member offers -->
	<c:if test="${fn:length(offerList) != 0}">
	<div class="rightTableFooter">
		<div style="float: left;margin-left: 30px;vertical-align: middle;margin-top: 12px;">
			<fmt:message bundle="${serviceliveCopyBundle}" key="memberOffers.sortBy.label"/>
		</div>
		<div style="float: left;margin-left: 10px;vertical-align: middle;margin-top: 5px;">
			<select name="memberOffer_sortBy_bottom" style="font-size: 13px;" id ="memberOffer_sortBy_bottom" class ="sortBy" tabindex="9">
				<option value="3"><fmt:message bundle="${serviceliveCopyBundle}" key="sort.option.company.name.a.to.z"/></option>
                <option value="4"><fmt:message bundle="${serviceliveCopyBundle}" key="sort.option.company.name.z.to.a"/></option>
                <option value="1"><fmt:message bundle="${serviceliveCopyBundle}" key="sort.option.newest.to.oldest"/></option>
                <option value="2"><fmt:message bundle="${serviceliveCopyBundle}" key="sort.option.oldest.to.newest"/></option>
                <option value="5"><fmt:message bundle="${serviceliveCopyBundle}" key="sort.option.most.popular"/></option>
			</select>
		</div>
		<div style="float: left;margin-left: 20px;vertical-align: middle;margin-top: 14px;">
		   	<fmt:message bundle="${serviceliveCopyBundle}" key="memberOffers.view.label"/>
		</div>
		<div style="float: left;margin-left: 10px;vertical-align: middle;margin-top: 6px;">
			<select name="memberOffer_view_bottom" style="width: 60px;" id="memberOffer_view_bottom" class = "memberOfferView" tabindex="10">
				<option value="6"><fmt:message bundle="${serviceliveCopyBundle}" key="view.option.6"/></option>
				<option value="12"><fmt:message bundle="${serviceliveCopyBundle}" key="view.option.12"/></option>
			</select>
		</div>
		<div style="float: left;margin-left: 20px;margin-top: 14px;vertical-align: middle;">
			<fmt:message bundle="${serviceliveCopyBundle}" key="memberOffers.viewAs.label"/>
		</div>
		<div style="float: left;margin-left: 5px;margin-top: 12px;vertical-align: middle;">
			<div>
				<img id="treeView_top"	class="btn20Bevel treeClass tabControl"	style="visibility: visible;cursor: pointer;" src="${staticContextPath}/images/member_offers/grid_view.png" title="Tree View" tabindex="11"/>
				<img id="horizontalView_top" class="btn20Bevel verticalClass tabControl" style="visibility: visible;cursor: pointer;" src="${staticContextPath}/images/member_offers/list_view.png" title="Horizontal View" tabindex="12"/>
			</div>	
		</div>
		<div style="float: right;margin-right: 15px;margin-top: 10px;vertical-align: middle;font-size: 14px;">
			<c:if test="${offerCriterias.currentPageNo>1}">
				<a  class="paginationArrow tabControl" id="1" style="color: #00A0D2;cursor: pointer;" tabindex="13">
					<b style="font-size: 18px;"><<</b>
				</a>
				
				<a  class="paginationArrow tabControl" id="${offerCriterias.currentPageNo-1}" style="color: #00A0D2;cursor: pointer;" tabindex="14">
					<b style="font-size: 18px;"><</b>
				</a>
			</c:if>
			Page ${offerCriterias.currentPageNo} of ${offerCriterias.totalNumberOfPages}
			<c:if test="${offerCriterias.totalNumberOfPages != offerCriterias.currentPageNo}">
				<a  class="paginationArrow tabControl" id="${offerCriterias.currentPageNo+1}" style="color: #00A0D2;cursor: pointer;" tabindex="15">
					<b style="font-size: 18px;">></b>
				</a>
				<a  class="paginationArrow tabControl" id="${offerCriterias.totalNumberOfPages}" style="color: #00A0D2;cursor: pointer;" tabindex="16">
					<b style="font-size: 18px;">>></b>
				</a>
			</c:if>
		</div>
	</div>
 </c:if>
</body>
</html>
