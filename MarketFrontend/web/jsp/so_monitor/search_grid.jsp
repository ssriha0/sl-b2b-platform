<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils,org.owasp.esapi.ESAPI;"%>

<c:set var="role" value="${SERVICE_ORDER_CRITERIA_KEY.roleId}" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="isAutocloseOn" scope="session"
	value="<%=session.getAttribute("isAutocloseOn")%>" />
<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="soMonitor.searchGrid"/>
	</jsp:include>	

<div class="errorBox clearfix" id ="searchErrorMsg" style="display: none">
</div>
<div class="searchBar">
	
	<!-- display if saved filters exist -->
	<c:if test="${not empty userSearchFilters}">
	<div class="savedFilters">
		<label class="left">Select a Saved Filter:</label>
		<select class="left" name="savedFilter" id="savedFilter" onchange="showButton_DeleteOption();">
			<option selected="selected" value=''><fmt:message bundle="${serviceliveCopyBundle}" key="search.select.filter" /></option>
			<s:iterator value="userSearchFilters" status="counter">		  
				<option value='<s:property value="searchFilterId"/>'><s:property value="filterName"/></option>
			</s:iterator>
		</select>
		
		<!-- display after a filter has been selected from the dropdown -->
			<table>
				<tr>
					<td>
						<input type="button" class="submitButton" value="Search" id="filterSearch" style="display: none" onclick="loadSelectedTerms();">
					</td>
					
					<td>
						<a href="#" class="deleteFilter" id="deleteFilter" style="display: none" onclick="deleteFilter();">Delete Filter</a>
					</td>
				</tr>
			</table>	
			
			
		<!-- end display after a filter has been selected from the dropdown -->
		
		
	</div>
	</c:if>
	<!-- end display if saved filters exist -->
	<div id="searchTerms">	
	<label><!-- if saved filters exist display OR text -->
	<c:if test="${not empty userSearchFilters}">Or
	<!-- end saved filters exist display OR text -->
	</c:if> Select Search Terms:</label>
	<ul id="searchTermsList">
		<li onclick="loadStatesList(this);"><span class="mainCategory">State</span></li>
		<ul id="statesList"></ul>
		
		<li onclick="loadMarketList(this);"><span class="mainCategory">Market</span></li>
			<ul id="marketsList" onclick="stopClickPropagation(event);">
				<li class="plusIcon" onclick="loadMarketListSub(this,'mktAE','A','E');">
				<img src="${staticContextPath}/images/icons/plusIcon.gif" />
				<span>A-E</span></li>
					<ul id="mktAE" onclick="stopClickPropagation(event);"></ul>
				<li class="plusIcon" onclick="loadMarketListSub(this,'mktFJ','F','J');">
				<img src="${staticContextPath}/images/icons/plusIcon.gif" />
				<span>F-J</span></li>
					<ul id="mktFJ" onclick="stopClickPropagation(event);"></ul>
				<li class="plusIcon" onclick="loadMarketListSub(this,'mktKO','K','O');">
				<img src="${staticContextPath}/images/icons/plusIcon.gif" />
				<span>K-O</span></li>
					<ul id="mktKO" onclick="stopClickPropagation(event);"></ul>
				<li class="plusIcon" onclick="loadMarketListSub(this,'mktPT','P','T');">
				<img src="${staticContextPath}/images/icons/plusIcon.gif" />
				<span>P-T</span></li>
					<ul id="mktPT" onclick="stopClickPropagation(event);"></ul>
				<li class="plusIcon" onclick="loadMarketListSub(this,'mktUZ','U','Z');">
				<img src="${staticContextPath}/images/icons/plusIcon.gif" />
				<span>U-Z</span></li>
					<ul id="mktUZ" onclick="stopClickPropagation(event);"></ul>
			</ul>
		<li onclick="loadSkillList(this);"><span class="mainCategory">Skill</span></li>
		<ul id="skillList"></ul>
		
		<li onclick="loadCategoryList(this);"><span class="mainCategory">Service Category</span></li>
		<ul id="categoryList"></ul>
		
		<li onclick="loadStatus(this);"><span class="mainCategory">Status</span></li>
		<ul id="statusList"><s:include value="/jsp/so_monitor/statusVals.jsp" /></ul>
		
		<li onclick="loadAcceptanceList(this);"><span class="mainCategory">Order Acceptance</span></li>
		<ul id="orderAcceptance" style="display: none;">
			<li id="automaticterm" title="Automatic" class="selectableItem" onclick="selectableItemClick(this,'order_acceptance');">
				<a href="#" onclick="return false;">
					<fmt:message bundle="${serviceliveCopyBundle}" key="autoacceptance.acceptance.automatic"/>
				</a>
			</li>
			<li id="manuaLterm" title="Manual" class="selectableItem" onclick="selectableItemClick(this,'order_acceptance');">
				<a href="#" onclick="return false;">
					<fmt:message bundle="${serviceliveCopyBundle}" key="autoacceptance.acceptance.manual"/>
				</a>
			</li>
		</ul>
		
		<li onclick="loadPricingList(this);"><span class="mainCategory">Pricing Type</span></li>
		<ul id="pricingType" style="display: none;">
			<li id="name_priceterm" title="Name Your Price" class="selectableItem" onclick="selectableItemClick(this,'pricing_type');">
				<a href="#" onclick="return false;">
					<fmt:message bundle="${serviceliveCopyBundle}" key="autoacceptance.pricingType.NYP"/>
				</a>
			</li>
			<c:if test="${!isNonFundedBuyer || statusSubstatusList.statusId != 165}">
			<li id="zero_price_bidterm" title="Bid Request" class="selectableItem" onclick="selectableItemClick(this,'pricing_type');">
				<a href="#" onclick="return false;">
					<fmt:message bundle="${serviceliveCopyBundle}" key="autoacceptance.pricingType.bid"/>
				</a>
			</li>
			</c:if>
		</ul>
		
		<li onclick="loadAssgnmntList(this);"><span class="mainCategory">Resource Assignment</span></li>
		<ul id="assignment" style="display: none;">
			<li id="firmterm" title="Unassigned" class="selectableItem" onclick="selectableItemClick(this,'res_assignment');">
				<a href="#" onclick="return false;">
					<fmt:message bundle="${serviceliveCopyBundle}" key="autoacceptance.assignment.unassigned"/>
				</a>
			</li>
			<li id="providerterm" title="Assigned" class="selectableItem" onclick="selectableItemClick(this,'res_assignment');">
				<a href="#" onclick="return false;">
					<fmt:message bundle="${serviceliveCopyBundle}" key="autoacceptance.assignment.assigned"/>
				</a>
			</li>
		</ul>
		
		<li onclick="loadPostingList(this);"><span class="mainCategory">Posting Method</span></li>
		<ul id="posting" style="display: none;">
			<li id="generaLterm" title="General" class="selectableItem" onclick="selectableItemClick(this,'posting_method');" >
				<a href="#" onclick="return false;">
					<fmt:message bundle="${serviceliveCopyBundle}" key="autoacceptance.posting.general"/>
				</a>
			</li>
			<li id="excLusiveterm" title="Exclusive" class="selectableItem" onclick="selectableItemClick(this,'posting_method');" >
				<a href="#" onclick="return false;">
					<fmt:message bundle="${serviceliveCopyBundle}" key="autoacceptance.posting.exclusive"/>
				</a>
			</li>
		</ul>
		
		<li onclick="loadPendingRescheduleList(this);"><span class="mainCategory" id="reschdRequest">Reschedule Request</span></li>
		<ul id="pendingReschedule" style="display: none;">
			<li id=pending title="Pending" class="selectableItem" onclick="selectableItemClick(this,'pending_reschedule');" >
				<a href="#" onclick="return false;">
					<fmt:message bundle="${serviceliveCopyBundle}" key="reschedule.request.pending"/>
				</a>
			</li>
		</ul>
		<c:if test="${isInHomeBuyer}">
		<li onclick="loadClosureMethodList(this);"><span class="mainCategory" id="closureType">Order Completion</span></li>
		<ul id="closureMethod" style="display: none;">
			<li id="pending Auto CLose" title="Pending Auto Close" class="selectableItem" onclick="selectableItemClick(this,'closure_method');" >
				<a href="#" onclick="return false;">
					<fmt:message bundle="${serviceliveCopyBundle}" key="closure.method.pendingAutoClose"/>
				</a>
			</li>
			<li id="manuaL review" title="Manual Review" class="selectableItem" onclick="selectableItemClick(this,'closure_method');" >
				<a href="#" onclick="return false;">
					<fmt:message bundle="${serviceliveCopyBundle}" key="closure.method.manualReview"/>
				</a>
			</li>
		</ul>
		</c:if>
	</ul>
<span id="autocloseWarning"  class="autoclosewarningBox" style="display:none;width: 600px;margin-top:5px">Auto Close Rules search will include Services Orders in completed status only.</span>
<span id="saveFilterWarning"  class="errorBox" style="display:none;width: 600px;margin-top:5px">Filter Name already exists.</span>
<label style="clear:both;">Define Search Criteria</label><br/>
<table cellspacing="7px" >
<tr>
<td>
<select name="searchType" id="searchType" class="left" onchange="showDateCal();">
		<option selected="selected" value=''><fmt:message bundle="${serviceliveCopyBundle}" key="search.please.select" /></option>
		<option value='1'><fmt:message bundle="${serviceliveCopyBundle}" key="search.phone"/></option>
		<option value='3'><fmt:message bundle="${serviceliveCopyBundle}" key="search.service.order"/></option>
		<option value='4'><fmt:message bundle="${serviceliveCopyBundle}" key="search.cust.name"/></option>
		<c:if test="${role == 3 && isAutocloseOn}">
		<option value='20'><fmt:message bundle="${serviceliveCopyBundle}" key="search.autoclose"/></option>
		</c:if>
		<c:if test="${role != 1}">
			<option value='10'><fmt:message bundle="${serviceliveCopyBundle}" key="search.buyer.check.number" /></option>
		</c:if>
		<option value='15'><fmt:message bundle="${serviceliveCopyBundle}" key="search.enddate"/></option>
		<c:if test="${role == 3}">
			<option value='8'><fmt:message bundle="${serviceliveCopyBundle}" key="search.provider.firm.id" /></option>
		</c:if>	
		<%-- drop in the Custom References --%>
		<s:iterator value="buyerRefs" status="counter">
		  <c:if test="${searchable == 1}">
			<option value='CR-<s:property value="buyerRefTypeId"/>'>Ref: <s:property value="referenceType"/></option>
          </c:if>	
		</s:iterator>
		
		<option value='5'><fmt:message bundle="${serviceliveCopyBundle}" key="search.service.pro.id"/></option>
		<option value='6'><fmt:message bundle="${serviceliveCopyBundle}" key="search.service.pro.name"/></option>
		<option value='14'><fmt:message bundle="${serviceliveCopyBundle}" key="search.startdate"/></option>
		<option value='2'><fmt:message bundle="${serviceliveCopyBundle}" key="search.zip"/></option>
		<!--  <option value='startDate'>Start Date</option>
		<option value='endDate'>End Date</option>-->
	</select>
	<input type="hidden" id="hidStartDate" name="hidStartDate" />
	<input type="hidden" id="hidEndDate" name="hidEndDate" />
</td>
<td>
	<select id="autocloseRules" name="autocloseRules" style="display:none; height: 22px;width: 158px;"  onchange="setAutocloseSearchValue();">
		<option selected="selected">-Select One-</option>
		
		<c:forEach items="${autocloseRulesList}" var="rule">
		<option value=${rule.id}>  <fmt:message bundle="${serviceliveCopyBundle}"	key="search.autocloserule.${rule.id}" /></option>
		</c:forEach>
	</select>
	<div ><input type="text" id="searchValue" class="shadowBox left"   style="height: 17px;width: 145px;"/>
	<input type="image" width="10" height="22" id="pickDate" style="display:none;" onclick="showCalendar();"  src="${staticContextPath}/images/icons/calendarIcon.gif"/>
	<div>
</td>
<td>
	<input type="button" value="Add" class="submitButton right"   onclick="validateFields();" />
	<br style="clear:both;"/>
</td>
<td>
 <input type="button" value="Search" onclick="sendData();"  class="submitButton right" id="SearchSom" name="SearchSom"/>
 
</td>
</tr>
</table>
	</div>
	
	
	<div id="searchSelections">
	<label class="left">Selected Search Terms:</label>
	<span id="clearAllLabel"><a href="#" id="clearAllLink" onClick="checkClear();return false;">Clear All</a></span>

	<ul id="searchSelectionsList" class="left">
		<li class="selectionListItem" id="market_list" onclick="toggleSelectionsList(this); stopClickPropagation(event);"><span onclick="stopClickPropagation(event);">Market</span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		
		<li class="selectionListItem" id="state_list" onclick="toggleSelectionsList(this); stopClickPropagation(event);"><span onclick="stopClickPropagation(event);">State</span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		
		<li class="selectionListItem" id="skill_list" onclick="toggleSelectionsList(this); stopClickPropagation(event);"><span onclick="stopClickPropagation(event);">Skill</span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		
		<li class="selectionListItem" id="category_list" onclick="toggleSelectionsList(this); stopClickPropagation(event);"><span onclick="stopClickPropagation(event);">Service Category</span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		<li class="selectionListItem" id="status_list" onclick="toggleSelectionsList(this); stopClickPropagation(event);"><span onclick="stopClickPropagation(event);">Status</span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		<li class="selectionListItem" id="order_acceptance" onclick="toggleSelectionsList(this); stopClickPropagation(event);"><span onclick="stopClickPropagation(event);">Order Acceptance</span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		<li class="selectionListItem" id="pricing_type" onclick="toggleSelectionsList(this); stopClickPropagation(event);"><span onclick="stopClickPropagation(event);">Pricing Type</span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		<li class="selectionListItem" id="res_assignment" onclick="toggleSelectionsList(this); stopClickPropagation(event);"><span onclick="stopClickPropagation(event);">Resource Assignment</span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		<li class="selectionListItem" id="posting_method" onclick="toggleSelectionsList(this); stopClickPropagation(event);"><span onclick="stopClickPropagation(event);">Posting Method</span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		
		<li class="selectionListItem" id="pending_reschedule" onclick="toggleSelectionsList(this); stopClickPropagation(event);"><span onclick="stopClickPropagation(event);">Reschedule Request</span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		
		<li class="selectionListItem" id="closure_method" onclick="toggleSelectionsList(this); stopClickPropagation(event);"><span onclick="stopClickPropagation(event);">Order Completion</span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		
		<li class="selectionListItem" id="check_number_list" onclick="toggleSelectionsList(this); stopClickPropagation(event);">
			<span onclick="stopClickPropagation(event);">Check Number</span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>		
		<li class="selectionListItem" id="customer_name_list" onclick="toggleSelectionsList(this); stopClickPropagation(event);">
			<span onclick="stopClickPropagation(event);"><fmt:message bundle="${serviceliveCopyBundle}" key="search.cust.name"/></span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		<li class="selectionListItem" id="phone_list" onclick="toggleSelectionsList(this); stopClickPropagation(event);">
			<span onclick="stopClickPropagation(event);"><fmt:message bundle="${serviceliveCopyBundle}" key="search.phone"/></span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		<li class="selectionListItem" id="pro_firm_id_list" onclick="toggleSelectionsList(this); stopClickPropagation(event);">
			<span onclick="stopClickPropagation(event);"><fmt:message bundle="${serviceliveCopyBundle}" key="search.provider.firm.id" /></span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		<li class="selectionListItem" id="so_id_list" onclick="toggleSelectionsList(this); stopClickPropagation(event);">
			<span onclick="stopClickPropagation(event);"><fmt:message bundle="${serviceliveCopyBundle}" key="search.service.order"/></span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		<li class="selectionListItem" id="service_pro_id_list" onclick="toggleSelectionsList(this); stopClickPropagation(event);">
			<span onclick="stopClickPropagation(event);"><fmt:message bundle="${serviceliveCopyBundle}" key="search.service.pro.id"/></span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		<li class="selectionListItem" id="service_pro_name_list" onclick="toggleSelectionsList(this); stopClickPropagation(event);">
			<span onclick="stopClickPropagation(event);"><fmt:message bundle="${serviceliveCopyBundle}" key="search.service.pro.name"/></span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		<li class="selectionListItem" id="zipcode_list" onclick="toggleSelectionsList(this); stopClickPropagation(event);">
			<span onclick="stopClickPropagation(event);"><fmt:message bundle="${serviceliveCopyBundle}" key="search.zip"/></span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		<li class="selectionListItem" id="startDate_list" onclick="toggleSelectionsList(this); stopClickPropagation(event);">
			<span onclick="stopClickPropagation(event);"><fmt:message bundle="${serviceliveCopyBundle}" key="search.startdate"/></span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		<li class="selectionListItem" id="endDate_list" onclick="toggleSelectionsList(this); stopClickPropagation(event);">
		<span onclick="stopClickPropagation(event);"><fmt:message bundle="${serviceliveCopyBundle}" key="search.enddate"/></span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		<li class="selectionListItem" id="custom_list" onclick="toggleSelectionsList(this); stopClickPropagation(event);">
		<span onclick="stopClickPropagation(event);">Custom Ref Fields</span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>
		<li class="selectionListItem" id="autocloseRules_list" onclick="toggleSelectionsList(this); stopClickPropagation(event);">
		<span onclick="stopClickPropagation(event);"><fmt:message bundle="${serviceliveCopyBundle}" key="search.autoclose"/></span>
			<ul onclick="stopClickPropagation(event);"></ul>
		</li>	
	</ul>
	<!--  <input type="button" value="Clear All" onclick="clearselections();" /> -->
	
     <input type="hidden" name="selectedSearchCriteria" id="selectedSearchCriteria" value=""/>
	</div>	
	<br style="clear:both;"/>
	<!-- display after a search has been performed -->
	<div class="savedFilters" id="saveFilterName" style="display:none" >
		<label class="left">Save results as a new filter:</label>
		<input type="text" class="shadowBox left" id="filterName" >		
		<input type="button" class="submitButton left" value="Save" onclick="saveCriteria();">		
	</div>
	<!-- end display after a search has been performed -->
	<br style="clear:both;"/>
</div>

<div class="monitorTab-leftCol">
	
	<!-- For SL-10191 -->
	<c:choose>
		 <c:when test="${wfFlag != null && wfFlag != ''  && pbFilterName != null  && pbFilterName != ''}" >
			<div id="search_grid_pb_filter_name">
				<p align="right" style="width: 690px">
					<c:set var="pbFilterName" value="<%=request.getAttribute("pbFilterName") %>"/>
					   <c:if test="${pbFilterName != null}">
					     <strong>
					     <font color="red">     
					      <fmt:message bundle="${serviceliveCopyBundle}" key="pb.workflow.search.filter" /><c:out value="${pbFilterName}"></c:out></font>
					    </strong> 
					 </c:if>	
				</p>
			</div>
		</c:when>
	</c:choose>


	<div class="clear"></div>
	<br>
	<div style="color: blue;font-size:13px;" id="cancelMessage" class="cancelMessage">
	</div><br>
	<div class="grid-table-container" style="position: relative">
		<table cellpadding="0px" cellspacing="0px" width="710px" class="scrollerTableHdr recdOrdersHdr" id="searchResultsGrid" border=0>
			<tr>
				<td class="column1">
					<fmt:message bundle="${serviceliveCopyBundle}" key="grid.label.view.detail" />
				</td>
				<td class="column2">
				<img src="${staticContextPath}/images/grid/arrow-up-white.gif" class="funky_img" border="0" style="display:none" id="sortByStatus<%=request.getAttribute("tab")%>" />
					 <a href="#" style="color:white" onclick="sortByColumn('<%=request.getAttribute("tab")%>','Status', 'searchHandler','<%=request.getAttribute("pbFilterId")%>')">
					  <fmt:message bundle="${serviceliveCopyBundle}" key="grid.label.status" />
					  </a>
				</td>
				<td class="column3">
				<img src="${staticContextPath}/images/grid/arrow-down-white.gif" class="funky_img" border="0" style="display:none" id="sortBySoId<%=request.getAttribute("tab")%>" />
    			 	<a href="#" style="color:white" onclick="sortByColumn('<%=request.getAttribute("tab")%>','SoId', 'searchHandler','<%=request.getAttribute("pbFilterId")%>')">
					  <fmt:message bundle="${serviceliveCopyBundle}" key="grid.label.soid" />
					</a>
				</td>
				<td class="column4">
					<fmt:message bundle="${serviceliveCopyBundle}" key="grid.label.title" />
				</td>
				<td class="column5">
				<img src="${staticContextPath}/images/grid/arrow-up-white.gif" class="funky_img" border="0" style="display:none" id="sortByServiceDate<%=request.getAttribute("tab")%>" />
    			 	<a href="#" style="color:white" onclick="sortByColumn('<%=request.getAttribute("tab")%>','ServiceDate', 'searchHandler','<%=request.getAttribute("pbFilterId")%>')">
					  <fmt:message bundle="${serviceliveCopyBundle}" key="grid.label.service.date" />
					</a>
					
				</td>
				<td class="column6">
					<fmt:message bundle="${serviceliveCopyBundle}" key="grid.label.location" />
				</td>
			</tr>
			<tr>
				<td colspan="6">
				 			<% 	String wfFlagVar=(String)request.getParameter("wfFlag");
								String wfFlag1=ESAPI.encoder().canonicalize(wfFlagVar);
								String wfFlag = ESAPI.encoder().encodeForHTML(wfFlag1);
								%>
							<c:set var="wfFlag" value="<%=wfFlag %>"/>
				
				<c:choose>
				 <c:when test="${wfFlag != null}">
						 <% String tabVar=(String)request.getAttribute("tab");
   							String tab1=ESAPI.encoder().canonicalize(tabVar);
							String tab = ESAPI.encoder().encodeForHTML(tab1);
							   							
   							String pbFilterIdVar=(String)request.getParameter("pbFilterId");
   							String pbFilterId1=ESAPI.encoder().canonicalize(pbFilterIdVar);
							String pbFilterId = ESAPI.encoder().encodeForHTML(pbFilterId1);
   							
   							String pbFilterNameVar=(String)request.getParameter("pbFilterName");
   							String pbFilterName1=ESAPI.encoder().canonicalize(pbFilterNameVar);
							String pbFilterName = ESAPI.encoder().encodeForHTML(pbFilterName1);
   							
   							String pbFilterOptVar=(String)request.getParameter("pbFilterOpt");
   							String pbFilterOpt1=ESAPI.encoder().canonicalize(pbFilterOptVar);
							String pbFilterOpt = ESAPI.encoder().encodeForHTML(pbFilterOpt1);
   						
   						   							
   							String refTypeVar=(String)request.getParameter("refType");
   							String refType1=ESAPI.encoder().canonicalize(refTypeVar);
							String refType = ESAPI.encoder().encodeForHTML(refType1);
   							
   							
   							String refValVar=(String)request.getParameter("refVal");
   							String refVal1=ESAPI.encoder().canonicalize(refValVar);
							String refVal = ESAPI.encoder().encodeForHTML(refVal1);
   							
   							
   							String wfm_sodFlagVar=(String)request.getAttribute("wfmSodFlag");
   							String wfmSodFlag1=ESAPI.encoder().canonicalize(wfm_sodFlagVar);
							String wfmSodFlag = ESAPI.encoder().encodeForHTML(wfmSodFlag1);
   						
   							
   							String soIdVar=(String)request.getAttribute("soId");
   							String soId1=ESAPI.encoder().canonicalize(soIdVar);
							String soId = ESAPI.encoder().encodeForHTML(soId1);
   							 %>
   							 
   							 <c:set var="wfmSodFlag" value="<%=wfmSodFlag %>"/>
   							 <c:set var="soId" value="<%=soId %>"/>
				    <iframe width="100%" height="780" marginwidth="0" scrolling="yes"
						marginheight="0" frameborder="0"						
						src="/MarketFrontend/monitor/PBWorkflowSearch.action?tab=<%=tab %>&wfFlag=<%=wfFlag%>&pbFilterId=<%=pbFilterId%>&pbFilterName=<%=pbFilterName%>&pbFilterOpt=<%=pbFilterOpt%>&refType=<%=refType%>&refVal=<%=refVal%>&wfmSodFlag=<%=wfmSodFlag%>&soId=<%=soId%>&fromWFM=${fromWFM}&pendingReschedule=${pendingReschedule}"
						id="<%=tab %>myIframe"
						name="<%=tab %>myIframe">
					</iframe>
				      
                 </c:when> 
                  <c:otherwise>				
					 <% String tabVar=(String)request.getAttribute("tab");
   						String tab=StringEscapeUtils.escapeXml(tabVar); %>  
					<iframe width="100%" height="780" marginwidth="0" scrolling="yes"
						marginheight="0" frameborder="0"
						src="/MarketFrontend/monitor/soSearch.action?tab=<%=tab %>&fromWFM=${fromWFM}"
						id="<%=tab %>myIframe"
						name="<%=tab %>myIframe">
					</iframe>
				 </c:otherwise>
      			</c:choose>
			
				</td>

			</tr>
		</table>
	</div>
</div>
<!-- adjust the membership widget alignment based on search filter -->
<c:choose>
	<c:when test="${not empty userSearchFilters}">
		<div class="monitorTab-rightCol" style="margin-top: -275px;">
	</c:when>
	<c:otherwise>
		<div class="monitorTab-rightCol" style="margin-top: -250px;">
	</c:otherwise>
</c:choose>



	<c:if test="${SecurityContext.slAdminInd}">
			<jsp:include page="/jsp/so_monitor/menu/widgetAdminMemberInfo.jsp" />
	</c:if>

	<div id="rightMenu_${theTab}" name="rightMenu_${theTab}"
		style="display: none; z-index: 5000;">
		</br>
		</br>
		<jsp:include flush="true"
			page="/jsp/so_monitor/menu/soMonitorRightMenu.jsp" />
	</div>
</div>