<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
	    <meta name="decorator" content="mainspndecorator" />
		<title>ServiceLive : SPN Auditor : Search</title>
		<link rel="shortcut icon"
			href="${staticContextPath}/images/favicon.ico" />

		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/styles/screen.css"
			media="screen, projection">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/styles/plugins/superfish.css"
			media="screen, projection">
			
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js"></script>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/modals.css" media="screen, projection">


		<script type="text/javascript"
			src="${staticContextPath}/scripts/toolbox.js"></script>
		<!-- end blueprint base include -->

		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/styles/plugins/spn-auditor.css"
			media="screen, projection">
	<%-- 	<script type="text/javascript"
			src="${staticContextPath}/scripts/plugins/ui.core.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/scripts/plugins/ui.tabs.js"></script> --%>
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	      .ie7 .bannerDiv{margin-left:-1150px;}
	      .ie9 .bannerDiv{margin-left:-200px;}
	 	</style>
		<script type="text/javascript">
		$(document).ready(function() {
			
			$("#declinemodal").jqm({trigger: 'a.opendeclinemodal'});
			
		});
	</script>
	</head>




	<div id="content" class="span-24 clearfix">

		<div id="primary" class="span-24 first last">
			<div class="content">
			
				<jsp:include page="spn_auditor_subheader.jsp" />
							
				<ul class="ui-tabs-nav">
					<li>
					<c:choose>
						<c:when test="${spnApplicantCounts.newApplicantsCount != null && spnApplicantCounts.newApplicantsCount>0}">
							<a href="spnAuditorApplicantsTab_displayNewApplicant.action">
								New Applicants <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<s:property value="spnApplicantCounts.newApplicantsCount" />)</abbr>
							</a>
						</c:when>
						<c:otherwise>
							<div style="color: gray">
								New Applicants <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<s:property value="spnApplicantCounts.newApplicantsCount" />)</abbr>
							</div>
						</c:otherwise>
					</c:choose>
					</li>
					<li>
					<c:choose>
						<c:when test="${spnApplicantCounts.reApplicantsCount !=null && spnApplicantCounts.reApplicantsCount>0}">					
							<a href="spnAuditorApplicantsTab_displayReApplicant.action">
								Re-Applicants <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<s:property value="spnApplicantCounts.reApplicantsCount" />)</abbr>
							</a>
						</c:when>
						<c:otherwise>
							<div style="color: gray">
								Re-Applicants <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<s:property value="spnApplicantCounts.reApplicantsCount" />)</abbr>
							</div>
						</c:otherwise>	
					</c:choose>				
					</li>
					<li>
						<c:choose>
							<c:when test="${spnApplicantCounts.membershipUnderReviewCount != null && spnApplicantCounts.membershipUnderReviewCount>0}">
								<a href="spnAuditorApplicantsTab_displayMembershipUnderReview.action">
									Membership Under Review <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<span class="spnMemRevwCnt"><s:property value="spnApplicantCounts.membershipUnderReviewCount" /></span>)</abbr>
								</a>
							</c:when>
							<c:otherwise>
								<div style="color: gray">
									Membership Under Review <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<span class="spnMemRevwCnt"><s:property value="spnApplicantCounts.membershipUnderReviewCount" /></span>)</abbr>
								</div>
							</c:otherwise>
						</c:choose>
					</li>
					<li class="ui-tabs-selected" style="background-color:black">
						<a href="spnAuditorSearchTab_display.action" style="color:white">Search</a>
					</li>
				</ul>
				<div class="searchpanel">
					<jsp:include page="search/searchPanel.jsp"></jsp:include>
				</div>

				<div id="filterpanel">
					<jsp:include page="search/filterPanel.jsp"></jsp:include>
				</div>
				
				<div id="searchresults">
					<jsp:include page="search/searchResults.jsp"></jsp:include>
				</div>
				
				<!--SL-19387 To display Background Check details of resources -->
				<div id="loadingbg"></div>
				
			</div>
		</div>
	</div>

	<!-- begin modal -->
	<!-- add the class of 'opendeclinemodal' to the link you want to open this modal -->
	
				<div id="declinemodal" class="jqmWindow">
					<div class="modal-header clearfix"><h2 class="left">Decline Membership</h2><a href="#" class="right jqmClose">Close</a></div>
					<div class="modal-content">
						<p class="error">Error Message</p>
						<p>Items marked with an Asterix (<span class="req">*</span>) are required.</p>
						<p><strong>Enter information to send to the provider to explain why you declined this provider's application to your SPN.</strong></p>
						<label>Comments:</label>
							<textarea></textarea>
							<div class="clearfix">
								<a class="cancel jqmClose left" href="#">Cancel</a>
								<input type="submit" class="action right" value="Done">
							</div>
					</div>
				</div>
	<!-- end modal -->
</html>