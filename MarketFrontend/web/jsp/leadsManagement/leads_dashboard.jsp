<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>	

$(document).ready(function() {

});
		 function showActiveLeads(){
				var activeCount = $("#activeDetailsCount").attr("value");
			 window.location.href="${contextPath}/leadsManagementController.action?status=active&count="+activeCount+"&activeLoad=true";

			 // Call the action and get the active leads and display the data
			 // View More leads is available only for the active leads
			/* var activeCount = $("#activeDetailsCount").attr("value");
			 $('#activeLeads').html('<img src="${staticContextPath}/images/loading.gif" width="772px" height="560px"/>');
			 $('#activeLeads').load('leadsManagementController_viewActiveLeads.action?status=active&count='+activeCount,  function(data) {
		     	 $('#activeLeads').show();
			 });
			 
			 $('#activeLeads').show();
				$("#activeLeadButton").addClass('active');
				$("#inactiveLeadButton").removeClass('active');
				 $('#inActiveLeads').html("");
				$('#inActiveLeads').hide();
				var activeCount = $("#activeDetailsCount").attr("value");
				*/
	     	
		 }
		 		 
		 function showInactiveLeads(){
			 	var inactiveCount = $("#inactiveDetailsCount").attr("value");
				window.location.href="${contextPath}/leadsManagementController.action?status=inactive&count="+inactiveCount+"&inactiveLoad=true";

			// Call the action and get the inactive leads and display the data
			/* $('#inActiveLeads').html('<img src="${staticContextPath}/images/loading.gif" width="772px" height="560px"/>');
			$('#inActiveLeads').load('leadsManagementController_viewInactiveLeads.action?status=inactive',  function(data) {
				$('#inActiveLeads').show();
			 }); 
			 $('#inActiveLeads').show();
				$("#inactiveLeadButton").addClass('active');
				$("#activeLeadButton").removeClass('active');
				$('#activeLeads').hide();	
			*/
				
		 }
		 function viewMoreLeads(){
			 window.location.href="${contextPath}/leadsManagementController.action?status=active&count=20&viewMore=true&activeLoad=true";
		}
		 function viewMoreInActiveLeads(){
			 window.location.href="${contextPath}/leadsManagementController.action?status=inactive&count=20&viewMore=true&inactiveLoad=true";
		}
		function viewDetails(leadId){
    		window.location.href="${contextPath}/leadsManagementController_viewLeadDetails.action?leadId="+leadId;
		}
	</script>


       <div id="leads-list">       
       <!-- View active leads -->
            <input id="activeDetailsCount" name="activeDetailsCount" type="hidden" value="${activeLeadDetailsCount}"/>
            <input id="inactiveDetailsCount" name="inactiveDetailsCount" type="hidden" value="${inactiveLeadDetailsCount}"/>
       
 		<div id="activeLeads" <c:if test="${tabName=='active'}">style="display:block"</c:if><c:if test="${tabName!='active'}">style="display:none"</c:if>>
           <c:if test="${tabName=='active'}">
           <jsp:include page="leads_dashboard_active.jsp"/>
           </c:if>
      </div>
      <!-- View Inactive leads -->
      <div id="inActiveLeads" <c:if test="${tabName=='inactive'}">style="display:block"</c:if><c:if test="${tabName!='inactive'}">style="display:none"</c:if>>
                <c:if test="${tabName=='inactive'}">
                 <jsp:include page="leads_dashboard_inactive.jsp"/></c:if>
      </div> 
          
      </div><!-- /#leads-list -->
       
       <div class="list-footer row">
           <div class="btn-group btn-group-justified">
             <a role="button" class="btn btn-primary <c:if test="${tabName=='active'}">active</c:if>" id="activeLeadButton" onclick="showActiveLeads();">Active Leads</a>
             <a role="button" class="btn btn-primary  <c:if test="${tabName=='inactive'}">active</c:if>" id="inactiveLeadButton" onclick="showInactiveLeads();">Inactive Leads</a>
           </div>
       </div>
