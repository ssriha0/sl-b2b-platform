<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<title>ServiceLive - Select Provider Network - Manage Networks</title>
	<tiles:insertDefinition name="blueprint.base.meta"/>
	<link rel="stylesheet" type="text/css" href="${static_context_root}/styles/plugins/spn-invitation.css" media="screen, projection">
	<script type="text/javascript" src="${static_context_root}/scripts/plugins/ui.core.js"></script>
	<script type="text/javascript" src="${static_context_root}/scripts/plugins/ui.tabs.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('a.tab1').click(function(){
				$("#tab-1").toggle();
				$("#tab-2").hide();
			});
			$('a.tab2').click(function(){
				$("#tab-2").toggle();
				$("#tab-1").hide();
			});
			$('#reason').hide();
			$('.nothanks').click(function(){
				$('#reason').toggle();
				$('.action').toggle();
			});
		});
	</script>
</head>
<body id="select-provider-network">
	<div id="wrap" class="container">

		<tiles:insertDefinition name="blueprint.base.header"/>
		<tiles:insertDefinition name="blueprint.base.navigation"/>

		<div id="content" class="span-24 clearfix">

			<div id="primary" class="span-24 first last">
				<div class="content">

					<div id="invitation" class="content clearfix">
						<div id="invite-header" class="clearfix">
							<img class="logo left" src="${static_context_root}/images/dynamic/sample-logo.jpg" title="Company Logo">
							<div class="right contact">
								<h5>Contact Information:</h5>
							
								<ul>
									<li>Jenny Smith</li>
									<li><a href="mailto:jenny@assurant.com">jenny@assurant.com</a></li>
									<li><strong>(000) 867-5309</strong>
								</ul>
							</div>
						</div>
						<div class="invitation-content clearfix content">
							<h3>You are invited to join {SPN Name}!</h3>
								<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
							<h4>How to Join</h4>
								<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
							<h4>Learn More</h4>
								<p>Read these attachments to learn more about this opportunity and how to join.  Click to view and print.</p>
							<div id="documents" class="clearfix">
								<dl class="doc">
									<dt><a href="#">{Document}</a></dt>
									<dd>Microsoft Word File</dd>
								</dl>

								<dl class="doc">
									<dt><a href="#">{Another Dakine Document Title}</a></dt>
									<dd>Microsoft Word File</dd>
								</dl>


								<dl class="doc">
									<dt><a href="#">{A Document Title}</a></dt>
									<dd>Microsoft Word File</dd>
								</dl>


								<dl class="pdf">
									<dt><a href="#">{Dolor Document Title}</a></dt>
									<dd>Adobe Acrobat file, requires Acrobat Reader to open</dd>
								</dl>

								<dl class="pdf">
									<dt><a href="#">{Lorem Ipsum Document Title}</a></dt>
									<dd>Adobe Acrobat file, requires Acrobat Reader to open</dd>
								</dl>

								<dl class="pdf">
									<dt><a href="#">{Some Random Document Title}</a></dt>
									<dd>Adobe Acrobat file, requires Acrobat Reader to open</dd>
								</dl>
							</div>
						</div>

						<div id="applynow" class="invitation-content clearfix content">

							<div id="invitation-requirements" class="clearfix">
								<h3>Membership Criteria &amp; Credentials </h3>

								<p>Below are the required criteria and documentation to required for membership. Click "Apply For Membership" below to begin. </p>


								<h4>Required Criteria</h4>
								<p>Select a criteria level below to see how you qualify for this <abbr title="Select Provider Network">SPN</abbr>.</p>
								<div class="tabs">
									<ul class="clearfix">
										<li><a class="tab1"><span>Provider Requirements</span></a></li>
										<li><a class="tab2"><span>Company Requirements</span></a></li>
									</ul>
									<div id="tab-1" class="clearfix">
										<h4>Provider Requirements</h4>
										<p class="note">An asterix <span class="req">*</span> Indicates credentials that must be verified by ServiceLive for membership. See the <a href="http://community.servicelive.com/docs/ServiceLive-Verification-Guide.pdf" target="_slVerificationGuide">ServiceLive Verification Guide</a>.</p>
										
										<div class="spill">
										<dl>
											<dt>Services &amp; Skills: <span class="sm">General Plumbing &amp; Bathroom</span></dt>
											<dd class="success"><span class="icon"><a title="At least one provider meets this requirement."><img src="${static_context_root}/images/common/status-green.png" /></a></span><strong>Installation</strong></dd>
											<dd class="success"><span class="icon"><a title="At least one provider meets this requirement."><img src="${static_context_root}/images/common/status-green.png" /></a></span><strong>Repair</strong></dd>
											<dd class="success"><span class="icon"><a title="At least one provider meets this requirement."><img src="${static_context_root}/images/common/status-green.png" /></a></span><strong>Custom Bathtub/Shower Refinishing</strong></dd>
											<dd class="success"><span class="icon"><a title="At least one provider meets this requirement."><img src="${static_context_root}/images/common/status-green.png" /></a></span><strong>Shower Liners</strong></dd>
											<dd class="success"><span class="icon"><a title="At least one provider meets this requirement."><img src="${static_context_root}/images/common/status-green.png" /></a></span><strong>Custom Shower Doors</strong></dd>
											<dd class="success"><span class="icon"><a title="At least one provider meets this requirement."><img src="${static_context_root}/images/common/status-green.png" /></a></span><strong>Faucets</strong></dd>
											<dd class="success"><span class="icon"><a title="At least one provider meets this requirement."><img src="${static_context_root}/images/common/status-green.png" /></a></span></span><strong>General Sinks</strong></dd>
											<dd class="notice"><span class="icon"><a title="No providers meets this requirement."><img src="${static_context_root}/images/common/status-yellow.png" /></a></span><strong>Toilets</strong></dd>
										</dl>
										
										<dl>
											<dt>Credentials</dt>
											<dd class="notice"><span class="icon"><a title="No providers meets this requirement."><img src="${static_context_root}/images/common/status-yellow.png" /></a></span><strong>Handyman</strong> <small>Verified by Servicelive <span class="req">*</span></small></dd>
											<dd class="success"><span class="icon"><a title="At least one provider meets this requirement."><img src="${static_context_root}/images/common/status-green.png" /></a></span><strong>Plumbing</strong> <small>Verified by Servicelive <span class="req">*</span></small></dd>
										</dl>
										
										<dl>
											<dd class="success"><span class="icon"><a title="At least one provider meets this requirement."><img src="${static_context_root}/images/common/status-green.png" /></a></span><strong>Minimum Rating</strong>: <small>4.5 or Higher</small></dd>
											<dd class="notice"><span class="icon"><a title="No providers meets this requirement."><img src="${static_context_root}/images/common/status-yellow.png" /></a></span><strong>Minimum Completed Service Orders</strong>: <small>3</small></dd>
										</dl>
										</div>
										<a class="close tab1">close</a>
									</div>
									<div id="tab-2" class="clearfix">
										<h4>Company Requirements</h4>
										<p class="note">An asterix <span class="req">*</span> Indicates credentials that must be verified by ServiceLive for membership. See the <a href="http://community.servicelive.com/docs/ServiceLive-Verification-Guide.pdf" target="_slVerificationGuide">ServiceLive Verification Guide</a>.</p>
										
										<div class="spill">
										<dl>
											<dt>Credentials</dt>
											<dd class="success"><span class="icon"><a title="Your company meets this requirement."><img src="${static_context_root}/images/common/status-green.png" /></a></span><strong>Handyman</strong> <small>Verified by Servicelive <span class="req">*</span></small></dd>
											<dd class="notice"><span class="icon"><a title="Your company does not meet this requirement."><img src="${static_context_root}/images/common/status-yellow.png" /></a></span><strong>Plumbing</strong> <small>Verified by Servicelive <span class="req">*</span></small></dd>
										</dl>
										
										<dl>
											<dt>Insurance</dt>
											<dd class="notice"><span class="icon"><a title="Your company does not meet this requirement."><img src="${static_context_root}/images/common/status-yellow.png" /></a></span><strong>Auto Liability | $100,000</strong> <small>Verified by Servicelive <span class="req">*</span></small></dd>
											<dd class="success"><span class="icon"><a title="Your company meets this requirement."><img src="${static_context_root}/images/common/status-green.png" /></a></span><strong>Workers Compensation</strong> <small>State Required Maximum</small></dd>
										</dl>
										</div>
										<a class="close tab2">close</a>
									</div>
								</div>
								

								<h4>Documents</h4>
								<p>When you apply, you will need to provide these documents. Click to view and print.</p>

								<div class="tableWrap">
									<table id="spn-monitor" cellspacing="0" cellpadding="0" border="0">
										<thead>
											<tr>
												<th class="br textcenter" style="width: 69px">Status</th>
												<th class="textleft">Required Documents</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td class="br">
													<img src="${static_context_root}/images/common/status-yellow.png"/>
													<br/>
													<span class="sm">
														<abbr title="Some copy explaining what the status means">Incomplete</abbr>
													</span>
												</td>
												<td class="textleft">
													<strong><a href="#">{Document Title}</a></strong>
													<br />
													<small>Microsoft Word File</small>
												</td>
											</tr>

											<tr>
												<td class="br">
													<img src="${static_context_root}/images/common/status-green.png"/>
													<br/>
													<span class="sm">
														<abbr title="Some copy explaining what the status means">On File</abbr>
													</span>
												</td>
												<td class="textleft">
													<strong><a href="#">{Document Title}</a></strong>
													<br />
													<small>Microsoft Word File</small>
													<br />
													<br />
													<strong>Document On File:</strong> <small><a href="#">{File name}</a>.{file ext.}</small>
													 
													
												</td>
											</tr>


										</tbody>
									</table>
								</div>
								
							</div>
						</div>
							<div class="clearfix buttonarea">
								<a class="cancel left nothanks" href="#">No Thank You</a>
								<div id="reason" class="left clearfix">
									<p class="error">Please select a reason you are not interested to continue.</p>
									<p><strong>Please tell us why you are not interested:</strong>
										<select style="float: left;"><option>Select One</option></select>
										<input style="margin-top: 7px;" type="submit" class="button default" value="Submit">
										
									</p>
								</div>
								<input type="submit" class="button action right" value="Apply For Membership">
							</div>
					
					</div>
					
				</div>
			</div>
		</div>

		<tiles:insertDefinition name="blueprint.base.footer"/>

	</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
	<jsp:param name="PageName" value="spn:provider invitation"/>
</jsp:include>
</body>
</html>


