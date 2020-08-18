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
		<title>ServiceLive : SPN Auditor : New Applicants</title>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	 .ie7 .bannerDiv{margin-left:-1150px;}
	 .ie8 .bannerDiv{margin-left:-1150px;}
	 .ie9 .bannerDiv{margin-left:-200px;}
			</style>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/modals.css" media="screen, projection">
		<link rel="shortcut icon"
			href="${staticContextPath}/images/favicon.ico" />

		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/styles/screen.css"
			media="screen, projection">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/styles/plugins/superfish.css"
			media="screen, projection">


		<script type="text/javascript"
			src="${staticContextPath}/scripts/toolbox.js"></script>
		<!-- end blueprint base include -->

		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/styles/plugins/spn-auditor.css"
			media="screen, projection">
		<script type="text/javascript"
			src="${staticContextPath}/scripts/plugins/ui.core.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/scripts/plugins/ui.tabs.js"></script>
		<script type="text/javascript" 
				src="${staticContextPath}/javascript/jquery/jqueryui/jquery-ui-1.10.4.custom.min.js"></script>
		<script>		
			$(document).ready(function() {
				$('div.tabs > ul').tabs();
			});
		</script>
	</head>




	
	<div id="content" class="span-24 clearfix">

		<div id="primary" class="span-24 first last">
			<div class="content">
			
				<jsp:include page="spn_auditor_subheader.jsp" />
			
				<%-- Start of Tabs --%>			
				<ul class="ui-tabs-nav">
					<c:if test="${tab == 'newapplicants'}">
						<li class="ui-tabs-selected" style="background-color:black">
								<div style="color: white">
									New Applicants <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<s:property value="newApplicantsCount" />)</abbr>
								</div>
						</li>
						<li>
						<c:choose>
							<c:when test="${reApplicantsCount != null && reApplicantsCount>0}">					
								<a href="spnAuditorApplicantsTab_displayReApplicant.action">
									Re-Applicants <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<s:property value="reApplicantsCount" />)</abbr>
								</a>
							</c:when>
							<c:otherwise>
								<div style="color: gray">
									Re-Applicants <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<s:property value="reApplicantsCount" />)</abbr>
								</div>
							</c:otherwise>
						</c:choose>					
						</li>
						<li>
							<c:choose>
								<c:when test="${membershipUnderReviewCount != null && membershipUnderReviewCount>0}">
									<a href="spnAuditorApplicantsTab_displayMembershipUnderReview.action">
										Membership Under Review <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<s:property value="membershipUnderReviewCount" />)</abbr>
									</a>
								</c:when>
								<c:otherwise>
									<div style="color: gray">
										Membership Under Review <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<s:property value="membershipUnderReviewCount" />)</abbr>
									</div>
								</c:otherwise>
							</c:choose>
						</li>
						<li>
							<a href="spnAuditorSearchTab_display.action">Search</a>
						</li>
					</c:if>
					<c:if test="${tab == 'reapplicants'}">
						<li>
						<c:choose>
							<c:when test="${newApplicantsCount != null && newApplicantsCount>0}">
								<a href="spnAuditorApplicantsTab_displayNewApplicant.action">
									New Applicants <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<s:property value="newApplicantsCount" />)</abbr>
								</a>
							</c:when>
							<c:otherwise>
								<div style="color: gray">
									New Applicants <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<s:property value="newApplicantsCount" />)</abbr>
								</div>
							</c:otherwise>
						</c:choose>
						</li>
						<li class="ui-tabs-selected" style="background-color:black">
							<div style="color: white">
								Re-Applicants <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<s:property value="reApplicantsCount" />)</abbr>
							</div>
						</li>
						<li>
							<c:choose>
								<c:when test="${membershipUnderReviewCount != null && membershipUnderReviewCount>0}">
									<a href="spnAuditorApplicantsTab_displayMembershipUnderReview.action">
										Membership Under Review <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<s:property value="membershipUnderReviewCount" />)</abbr>
									</a>
								</c:when>
								<c:otherwise>
									<div style="color: gray">
										Membership Under Review <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<s:property value="membershipUnderReviewCount" />)</abbr>
									</div>
								</c:otherwise>
							</c:choose>
						</li>
						<li>
							<a href="spnAuditorSearchTab_display.action">Search</a>
						</li>
					</c:if>		
					
						<c:if test="${tab == 'membership'}">
						<li>
						<c:choose>
							<c:when test="${newApplicantsCount != null && newApplicantsCount>0}">
								<a href="spnAuditorApplicantsTab_displayNewApplicant.action">
									New Applicants <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<s:property value="newApplicantsCount" />)</abbr>
								</a>
							</c:when>
							<c:otherwise>
								<div style="color: gray">
									New Applicants <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<s:property value="newApplicantsCount" />)</abbr>
								</div>
							</c:otherwise>
						</c:choose>
						</li>
							<li>
						<c:choose>
							<c:when test="${reApplicantsCount != null && reApplicantsCount>0}">					
								<a href="spnAuditorApplicantsTab_displayReApplicant.action">
									Re-Applicants <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<s:property value="reApplicantsCount" />)</abbr>
								</a>
							</c:when>
							<c:otherwise>
								<div style="color: gray">
									Re-Applicants <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<s:property value="reApplicantsCount" />)</abbr>
								</div>
							</c:otherwise>
						</c:choose>					
						</li>
						<li class="ui-tabs-selected" style="background-color:black">
							<div style="color: white">
								Membership Under Review <abbr title="The number shown only represents applicants available or unlocked in the queue.">(<s:property value="membershipUnderReviewCount" />)</abbr>
							</div>
						</li>
						<li>
							<a href="spnAuditorSearchTab_display.action">Search</a>
						</li>
					</c:if>				
				</ul>
				<%-- End of Tabs --%>
				
				<div class="ui-tabs-panel">
					<div class="clearfix">
						<div style="width: 700px; float: left;">
							<div class="clearfix">
								<c:if test="${showReleaseGetNextLink}">
									<a class="right" href="${releaseGetNextAction}">
										Release &amp; Get Next &raquo;
									</a>
								</c:if>
							</div>
							<s:include value="panel_applicant.jsp"/>
						</div>
						
						<%-- Right Side Column.  Remove for Search Page  --%>
						<div class="content right spncol">
							<div class="spninfo">
								<h4>
									<strong>View <abbr title="Select Provider Network">SPN</abbr>
										Criteria</strong>
								</h4>
								<label>
									<abbr title="Select Provider Network">SPN</abbr>:
								</label>
				
								<!-- Below is the same widget from 'Create SPN' page -->								
								<div id="spnSelectList" style="width:190px ">
									<jsp:include page="/jsp/spn/common/spn_list_audit.jsp" />
								</div>									
								<div class="information" style="width: 190px">
									<s:include value="/jsp/spn/buyer/campaign/create/right_side_create_campaign.jsp"/>
								</div>
								<!-- End of right side SPN details widget -->
									
								
							</div>
								
							</div>
							
							
						</div>

					</div>
				</div>
				
						
			</div>
		</div>



</html>


