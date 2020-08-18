<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>

     <script type="text/javascript">
      jQuery("#problemlink").load("soDetailsQuickLinks.action?soId=${SERVICE_ORDER_ID}");
      function clearFormAndErrorMsg(){
    	  document.getElementById('validationResponseMessage').style.display = 'block';
    	  document.getElementById('validationResponseMessage').style.visibility = 'hidden';
    	  document.getElementById('validationResponseMessage').innerHTML = "";
    	  document.getElementById('frmReportProblem').reset();
    	  
      }
     </script>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="SIMPLE_BUYER_ROLEID" value="<%= new Integer(OrderConstants.SIMPLE_BUYER_ROLEID)%>" />
<c:set var="BUYER_ROLEID" value="<%= new Integer(OrderConstants.BUYER_ROLEID)%>" />
<c:set var="PROVIDER_ROLEID" value="<%= new Integer(OrderConstants.PROVIDER_ROLEID)%>" />
<c:if test="${roleType == BUYER_ROLEID || roleType == SIMPLE_BUYER_ROLEID}">
	<c:set var="otherPartyName" value="provider" />
</c:if>
<c:if test="${roleType == PROVIDER_ROLEID}">
	<c:set var="otherPartyName" value="buyer" />
</c:if>

            <jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
                  <jsp:param name="PageName" value="ServiceOrderDetails.reportAProblem"/>
            </jsp:include>

	<div class="soNote">
	 <div id="rightsidemodules" class="colRight255 clearfix" >
           
         
         
         <p id="problemlink" style="color:#000;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;"><span> </span></p>
         
      
       
    </div>
<!-- START RIGHT SIDE MODULES -->
<a id="#" href="#"></a>
<!-- END RIGHT SIDE MODULES -->
<!-- NEW MODULE/ WIDGET-->
<div class="contentPane">
<c:set var="errmsg" value="${msg}" />
<c:if test='${errmsg != ""}'>
<c:if test='${errmsg != null}'>
<div class="errorBox clearfix" id="validationResponseMessage" style="width: 675px; overflow-y:hidden; visibility:visible;">
	<p class="errorMsg">
		&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${errmsg}" />
	</p>
</div>
<%request.removeAttribute("msg");%>
</c:if>
</c:if>

  <!--  Display Validation error messages here -->
  <jsp:include page="/jsp/details/body/html_sections/modules/detailsValidationMessages.jsp" />

<form id="frmReportProblem"  method="post" action="/MarketFrontend/soDetailsSubmitProblem.action">
<input type="hidden" id="pbDesc" name="pbDesc" />
<input type="hidden" id="resComment" name="resComment" />		
<input type="hidden" id="subStatusId" name="subStatusId" />
<input type="hidden" id="soId" name="soId" value="${SERVICE_ORDER_ID}"/>
  <div class="darkGrayModuleHdr">Problem Details</div>
  <div class="grayModuleContent">
    <p>Having a problem with your ${otherPartyName}?  Disputes are often nothing more than a simple miscommunication. Select a problem from the drop-down list, provide a detailed description and click 'report problem.'  A problem alert will be sent to the ${otherPartyName}.</p>
    
    <p><strong>Type of Problem</strong><br>
    
     <c:set	var="statusSubstatusList" value="${serviceOrderStatusVOList}" scope="session" />
     <select id="pbType" name="pbType" style="width: 650px;" 
     onchange='document.getElementById("pbDesc").value = document.getElementById("pbType").options[document.getElementById("pbType").selectedIndex].text;'>
     <option value="-1">Select One</option>
     <c:forEach var="statusSubstatusList" items="${serviceOrderStatusVOList}" varStatus="inx">
	 <c:choose>
			<c:when test="${inx.first}">
			<c:set var="firstIdx" value="${statusSubstatusList.statusId}"/>
			<c:forEach var="serviceOrderSubStatuses" items="${statusSubstatusList.serviceOrderSubStatusVO}" >									
				<option value="${serviceOrderSubStatuses.subStatusId}" >
					${serviceOrderSubStatuses.subStatusName}
				</option>
			</c:forEach>
            </c:when>
	</c:choose>
	</c:forEach>
    </select>
	</p>
    <p><strong>Additional Comments (Please be as detailed as possible)</strong><br>
      <textarea id="pbComment" name="pbComment" class="shadowBox" style="width: 645px;" maxlength="750" onkeydown="CheckMaxLength(this, 749);"></textarea>
    </p>
  </div>


  <div class="clearfix" style="height: 80px">
    <div class="formNavButtons">
    	<input id="submitReportProblemButton" type="image" src="${staticContextPath}/images/common/spacer.gif" width="115" height="20" 
			style="background-image:url(${staticContextPath}/images/btn/reportAProblem_gold.gif); cursor:pointer; vertical-align:middle;"  
			class="btnBevel" onclick="javascript:fnReportProblem();"/>	
    </div>
    <div class="bottomRightLink">
    	<img src="${staticContextPath}/images/common/spacer.gif" width="72" height="20" 
	    	style="background-image:url(${staticContextPath}/images/btn/cancel.gif);" class="btnBevel" 
	    	onclick="clearFormAndErrorMsg()" />
    </div>
  </div>
</form>


<div class="darkGrayModuleHdr">ServiceLive Service Order Problem Management</div>
  <div class="grayModuleContent">
	<strong>Indicate Problem/Issue</strong>
	<p>Indicating a problem on a service order will change the service order to a Problem status and alert the other party of the problem. A Buyer or Provider can indicate a problem on a service order that is either in Accepted or Active status. Problems can be resolved in a number of ways depending on the issue. For the most part, problems will get resolved in off-line conversations and either party can indicate that the service order problem has been resolved. </p>
	<p>To indicate a problem, click the Indicate Problem button. Select the reason (problem sub-status) and enter a brief description. The other party (Buyer or Provider) will be notified of the problem via email. </p>
	<p></p>
	<p><strong>Mitigating Issues / Disputes Before They Occur</strong></p>
	<p>At the start of every project there are guidelines one should follow to help avoid unexpected issues that may result in disputes between the Buyer and Service Provider.  No one likes surprises and it takes time to resolve disputes so a little up front planning and preparation can go a long way.</p>
	<ul style="margin: 0 20px">
		<li>When communicating with others, please be precise in terms of what you require, what you will provide and what you expect of the other party.  Avoid making assumptions or providing vague details.  
			<ul style="margin: 0 20px">
				<li>Buyers should post projects with clear scopes of work, well defined deliverables that are time bound and specify payment terms.</li>
				<li>Providers should only accept a proposal with clear scopes of work that completely specify the key deliverable(s), timeframe for completing the work and payment terms.  Ask all clarifying questions up front, document any ambiguous items and validate all assumptions.  </li>
			</ul>
		</li>
		<li>Communications are frequently open to some level of interpretation.  As such equal emphasis needs to be placed on listening to the other party and soliciting their feedback.  Ensure the message you intended to deliver is the message that has been received.  Ask the other party to paraphrase what you're asking them to do to ensure they received the message you intended to deliver... this will ensure any ambiguous items are clarified and all assumptions are validated.  </li>
		<li>Build a relationship founded on mutual respect and trust.  Say what you mean, mean what you say and do what you say you're going to do.</li>
		<li>Keep the communication lines open throughout the project.  Always provide constructive feedback as the goal is to institute a positive change.  Explain precisely what needs to be changed and a timeline for correcting any problems.</li>
	</ul>
	<p></p>
	<p><strong>When Things Start Getting Off Track</strong></p>
	<p>Occasionally projects encounter unforeseen challenges due to a variety of reasons.  Some are related to miscommunication or misunderstanding and others may simply be due to concealed damage or the unknown.  Work with the other party and put forth a good faith effort to do what is right and what is fair.  Remember no one wins when the Buyer refuses to pay or when the Provider walks off the job.</p>

	<ul style="margin: 0 20px">
		<li>Inform the other party of all issues and concerns as soon as possible.  Don't let things bottle up until you frustration overwhelms you.  Don't wait until after the shingles have been installed to point out some rotted wood you expected to be replaced.   </li>
		<li>Give each other the benefit of the doubt and don't rush to judgment.  State your concerns in a respectful way while remaining calm.  Give the other party equal time to respond with an explanation. </li>
	</ul>
	</div>
</div>
</div>
<!-- END TAB PANE -->
