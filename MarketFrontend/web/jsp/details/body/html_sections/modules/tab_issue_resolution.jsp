<%@page import="com.sears.os.service.ServiceConstants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
            <jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">                  <jsp:param name="PageName" value="ServiceOrderDetails.issueResolution"/>            </jsp:include><link href="${staticContextPath}/javascript/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqmodal/jqModal.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery-ui-personalized-1.5.2.packed.js"></script>
<script src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js" type="text/javascript"></script>
     <script type="text/javascript">
   jQuery("#issue").load("soDetailsQuickLinks.action?soId=${SERVICE_ORDER_ID}");
     function clickService(path)
{
    jQuery("#issueresolutionpane p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("div.menugroup_body").slideToggle(300);
    var ob=document.getElementById('issueImg').src;
	if(ob.indexOf('arrowRight')!=-1){
	document.getElementById('issueImg').src=path+"/images/widgets/arrowDown.gif";
	}
	if(ob.indexOf('arrowDown')!=-1){
	document.getElementById('issueImg').src=path+"/images/widgets/arrowRight.gif";
	}
    
}
      </script>
     <div class="soNote">
<div id="rightsidemodules" class="colRight255 clearfix" >
           
         <p id="issue" style="color:#000;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;"><span></span></p>
          
       
    </div>

<a id="#" href="#"></a>
<form id="frmReportResolution">
<!-- NEW MODULE/ WIDGET-->
<div class="contentPane">
<c:set var="errmsg" value="${msg}" />
<c:set var="errcd" value="${msgcode}" />
<c:set var="validcd" value="${ServiceConstants.VALID_RC}" />
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
	
 <div id="issueresolutionpane" class="menugroup_list">
  	<p class="menugroup_head" onClick="clickService('${staticContextPath}');">&nbsp;<img id="issueImg" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Service Categories</p>
    <div class="menugroup_body">
  <p>ServiceLive carefully screens the PROVIDERS/BUYERS we work with, but occasionally misunderstandings
    occur. If you're in a dispute, please download and review our issues resolution document. A ServiceLive
    representative will contact you shortly to help mediate and resolve your issue.
  </p>
  	
    <div class="issueHead">Problem Details</div>
    <div class="issueHead_body">
    <c:set	var="prbResolutionSoVO" value="${prbResolutionSoVO}" scope="request" />
    
   <table cellpadding="0" cellspacing="0" border="0" width="600">
      <tr>
        <td width="200"><strong>Problem Type:</strong></td>
        <td width="400"><c:out value="${prbResolutionSoVO.subStatusDesc}" /></td>
      </tr>
      <tr>
        <td width="200"><strong>Problem Details:</strong></td>
        <td width="400">
        	<c:out value="${prbResolutionSoVO.pbComment}" />
		</td>
      </tr>
      <tr>
        <td colspan="2"><strong>Resolution Comments:</strong></td>
      </tr>
      <tr>  
        <td colspan="2"><textarea id="resComment" name="resComment" class="shadowBox" style="width: 350px;" maxlength="750" onkeydown="CheckMaxLength(this, 749);"></textarea></td>
      </tr>
      <tr>
      	<td colspan="2" align="left">
      	<div class="formNavButtons">
      		<img src="${staticContextPath}/images/common/spacer.gif" width="129" height="20" style="background-image:url(${staticContextPath}/images/btn/markIssueResolved.gif); cursor: pointer;"  class="btn20Bevel" onclick="fnReportResolution();" />
    	</div>
      	</td>
      </tr>
    </table>
    </div>
  
  <!-- NEW NESTED WIDGET-->
  <div class="menuissue_list">
<div class="menuissue_head">ServiceLive Service Order Problem Management</div>
<div class="menuissue_body">
 
    
    <h3>Indicate Problem/Issue</h3>
    
	<p>If a service order is in a PROBLEM status, either the Buyer or Provider can view and resolve the problem by selecting the Issue Resolution tab on the service order details page. Once in the Issue Resolution tab, the user can read the problem and choose to resolve it. To resolve the problem, enter a problem resolution comment and click the Mark Issue Resolved button. The service order will revert back to it's previous status. </p>
	<p>As the service contract exists between the Buyer and Provider it is customary for the two parties to engage in constructive and respectful off-line conversations aimed at finding an equitable solution to resolve their issue/problem.  Key discussions and decisions should be documented in the service order notes pending an agreed upon resolution. Click on the following link to learn more about guidelines for avoiding and resolving issues/disputes: 
	<br /><a href="#appendixd">Buyer / Provider Self Resolution Guidelines</a></p>
	
	<h3>Unresolved Issues/Problems</h3>
	<p>When a Buyer and Provider are unable to resolve an issue/problem either party can contact ServiceLive support at 888-549-0640 to ask for assistance in resolving the dispute.  To learn more about this limited service click on the following link:
	 <br /><a href="#appendixe">Dispute Resolution Assistance</a>
	</p>
  </div>
  </div>
  </div>
 </div>
  



<a id="appendixd" name="appendixd"></a>
<div class="darkGrayModuleHdr">Mitigating Issues / Disputes Before They Occur</div>
 <div class="grayModuleContent">
        
	<p>At the start of every project there are guidelines one should follow to help avoid unexpected issues that may result in disputes between the Buyer and Service Provider.  No one likes surprises and it takes time to resolve disputes so a little up front planning and preparation can go a long way.</p>
		
	<ul style="margin: 0 20px">
		<li>When communicating with others be precise in terms of what you require, what you will provide and what you expect of the other party.  Avoid making assumptions or providing vague details.  
			<ul style="margin: 0 20px">
				<li>Buyers should post projects with clear scopes of work, well defined deliverables that are time bound and specify payment terms.</li>
				<li>Providers should only accept a proposal with clear scopes of work that  completely specify the key deliverable(s), timeframe for completing the work and payment terms.  Ask all clarifying questions up front, document any ambiguous items and validate all assumptions.  </li>
			</ul>
		</li>
		<li>Communications are frequently open to some level of interpretation.  As such equal emphasis needs to be placed on listening to the other party and soliciting their feedback.  Ensure the message you intended to deliver is the message that has been received.  Ask the other party to paraphrase what you're asking them to do to ensure they received the message you intended to deliver... this will ensure any ambiguous items are clarified and all assumptions are validated.  </li>
		<li>Build a relationship founded on mutual respect and trust.  Say what you mean, mean what you say and do what you say you're going to do.</li>
		<li>Keep the communication lines open throughout the project.  Always provide constructive feedback as the goal is to institute a positive change.  Explain precisely what needs to be changed and a timeline for correcting any problems.</li>
	</ul>

	<h3>When Things Start Getting Off Track</h3>
	<p>Occasionally projects encounter unforeseen challenges due to a variety of reasons.  Some are related to miscommunication or misunderstanding and others may simply be due to concealed damage or the unknown.  Work with the other party and put forth a good faith effort to do what is right and what is fair.  Remember no one wins when the Buyer refuses to pay or when the Provider walks off the job.</p>

	<ul style="margin: 0 20px">
		<li>Inform the other party of all issues and concerns as soon as possible.  Don't let things bottle up until your frustration overwhelms you.  Don't wait until after the shingles have been installed to point out some rotted wood you expected to be replaced.   </li>
		<li>Give each other the benefit of the doubt and don't rush to judgment.  State your concerns in a respectful way while remaining calm.  Give the other party equal time to respond with an explanation. </li>
	</ul>
  </div>
</div>


<a id="appendixe" name="appendixe"></a>
<div class="darkGrayModuleHdr" style="width: 695px">Dispute Resolution Assistance</div>
 	<div class="grayModuleContent" style="width: 678px">
	<p>ServiceLive offers a "Service Order Report a Problem" and "Issue Resolution" features that Buyers and Service Providers may leverage to document all communications between the parties to resolve issues / disputes.  ServiceLive further recognizes from time to time a Buyer and Service Provider may be unable to mutually agree upon an equitable resolution.  </p>
	<p>In such instances where a Buyer and Service Provider are unable to mutually agree upon an equitable resolution, ServiceLive may provide voluntary, limited assistance in resolving disputes between Buyers and Service Providers when both parties agree to participate in the ServiceLive Dispute Resolution Assistance process and when final payment for services has not been issued.  </p>
	<p>The table below provides an overview of the key steps associated with the ServiceLive Dispute Resolution Assistance process.  Please call the Support Center at 888-549-0640 if you wish to initiate such a request or to simply learn more about the process. </p>	
	<p>ServiceLive reserves the right to change or cancel this dispute resolution process at any time.  The results of this process are not binding on either party, however, ServiceLive does request, that out of respect for the community nature of our site, Buyers and Service Providers abide by any such determination.  However, each party is free to explore other means for resolving its disputes as may be available under local law (e.g., small claims actions).   Each party should determine for itself when and if it wishes to seek legal counsel.</p>
	
	</div>
	</form>





