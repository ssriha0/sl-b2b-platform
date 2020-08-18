<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<header>
     <div class="col-sm-8 col-md-3">
         <h1>Active Leads</h1> 
         <span class="badge tooltip-target" data-toggle="tooltip" data-placement="bottom" data-original-title="Total Active leads">${totalActiveLeadCount}</span>
         <span class="glyphicon glyphicon-search visible-xs" data-toggle="collapse" data-target="#search-wrap"></span>
     </div>

       <div id="search-wrap" class="collapse col-sm-4">
           <input class="search form-control" placeholder="Search" type="search" />
           <span class="glyphicon glyphicon-search"></span>
       </div><!-- / #search-wrap -->

        <div class="filter-wrap col-md-5">
            <div class="filter btn-group btn-group-justified" data-toggle="buttons">
                <label class="btn btn-default active" id="filter-all">
                    <input type="radio" name="options"> <i class="icon-all"></i> <span>All</span>
                </label>
                <label class="btn btn-default" id="filter-2">
                    <input type="radio" name="options"> <i class="icon-leadStatus-1"></i> <span>New</span>
                </label>
                <label class="btn btn-default" id="filter-3">
                 <input type="radio" name="options"> <i class="icon-leadStatus-2"></i> <span>Working</span>
                </label>
                <label class="btn btn-default" id="filter-4">
                    <input type="radio" name="options"> <i class="icon-leadStatus-3"></i> <span>Scheduled</span>
                </label>
            </div><!-- / .filter -->
        </div>    
</header><!-- /.row.list-heading -->
        <div class="row list-body">
            <ul class="list-group list">
            <c:if test="${fn:length(activeLeadDetails)>0}">
			<c:forEach var="leadDetail" items="${activeLeadDetails}" varStatus="status">
		  	<c:choose>
		  	 <c:when test="${leadDetail.leadStatus== 'new'}">
		  	<c:set var="className" value="list-group-item"/>
		  	<c:set var="iconName" value="icon-leadStatus-1"/>
		  </c:when>
		  <c:when test="${leadDetail.leadStatus== 'working'}">
		  	<c:set var="className" value="list-group-item"/>
		  	<c:set var="iconName" value="icon-leadStatus-2"/>
		  </c:when>
		  <c:when test="${leadDetail.leadStatus== 'scheduled'}">
		  	<c:set var="className" value="list-group-item"/>
		  	<c:set var="iconName" value="icon-leadStatus-3"/>
		  </c:when>
		  	</c:choose>
		 

  <!-- set [data-leadid] to target a specific lead's details page -->
        <li class="${className}" data-leadid="${leadDetail.leadId}">
            <div class="row">                                
                <div class="col-xs-11 col-md-4">
                  <h2>
                  <span class="name">${leadDetail.custFirstName}&nbsp;${leadDetail.custLastName}</span> <small class="leadid"><br>Service Request #${leadDetail.leadId}</small>
                  </h2> 
                  <div class="lead-info">
                    <span class="projectType">${leadDetail.projectType}</span> | <span class="skill">${leadDetail.skill}</span>
                  </div>
                  <div class="cust-info">
                    <span class="label label-info phone">${leadDetail.custPhoneNo}</span> <span class="city">${leadDetail.city}</span>, <span class="zip">${leadDetail.zip}</span>
                  </div>
                </div>
                <div class="service-details visible-md visible-lg col-md-6">
                  <h3>Service Details</h3> 
                  <!-- TODO Only pull in first 230 characters of description; after that it should end at the nearest word or ' ' and insert ellipses '...' -->
                  <c:choose>
                   <c:when test="${empty leadDetail.leadDescription}">
                     <p class="no-details">Not specified.</p>
                   </c:when>
                   <c:otherwise>
                     <p>${leadDetail.leadDescription}</p>
                   </c:otherwise>
                  </c:choose>
                </div>
                <div class="visible-md visible-lg col-md-2">
                  <!-- <a href="lead-12345-details.html" class="btn" role="button">View Full Details</a> -->
                  <a href="#" class="btn btn-main" role="button" onclick="viewDetails('${leadDetail.leadId}');">View Full Details</a>
                </div>

                <i class="glyphicon glyphicon-chevron-right hidden-md hidden-lg"></i>
                <!-- Only appears on same day or next day orders -->
                <!-- Commented as per SL-20893 requirments -->
               <!--  <c:if test="${not empty leadDetail.urgency && leadDetail.urgency!= 'after tomorrow'&& leadDetail.urgency!=' '}">
                	 <div class="urgency">${leadDetail.urgency}</div>
                     </c:if> -->
                <div class="secondary-info">
                  <!-- DateTime is when the lead was received by the provider.
                       Uses microformat YYYY-MM-DDTHH:MM:SS, can also adjust for timezone if needed.
                       See http://microformats.org/wiki/datetime-design-pattern and http://html5doctor.com/the-time-element/ 2013-11-22T18:00:24-->
                  <c:set var="timeValue" value="${leadDetail.formattedDreatedDate}"/>
                  <time class="timeago pull-right" datetime="${timeValue}" title="${leadDetail.createdDate}">
                  	${leadDetail.createdDate}
                  	</time>
                  <!-- .leadStatus value must be lowercase for filters to work -->
                  <i class="${iconName}"></i> <span class="leadStatus">${leadDetail.leadStatus}</span>
                </div>
            </div>
        </li>
  </c:forEach>
</c:if>        
</ul>          
            <!-- View more -->
             <c:choose>
             <c:when test="${totalActiveLeadCount>activeLeadDetailsCount}">
             		<div id ="viewStyle" style="margin-left:1.3%">
             		<p>Showing ${activeLeadDetailsCount} of ${totalActiveLeadCount}</p>
             		</div>
            		<a href="#" id="viewMoreActiveOrders" class="btn" role="button" onclick="viewMoreLeads();"><u>View More Leads</u></a>
           	</c:when>
           	</c:choose>
         <div class="list-group-item" id="emptyView" style="display:none;">
              <p>No search results found</p>
            </div>
</div><!-- /.row.list-body -->