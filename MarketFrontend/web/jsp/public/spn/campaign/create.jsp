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
	<title>ServiceLive - Select Provider Network - Create Campaigns</title>
	<tiles:insertDefinition name="blueprint.base.meta"/>
	<link rel="stylesheet" type="text/css" href="${static_context_root}/styles/plugins/select-provider-network.css" media="screen, projection">
	<script type="text/javascript" src="${static_context_root}/scripts/plugins/jquery.multiselect.js"></script>

	<script type="text/javascript">
	$(document).ready(function() {

		$('.toggle, .vchecktoggle, .wchecktoggle, .cchecktoggle, dl.collapse dd, span.open, div.filter-option, tr.info, a.dateedit, h3 span.min, .formtip').hide();

		$('.c-general').show();
		$('.c-general span.min').show();
		$('.c-general span.plus').hide();

		$('h3.collapse').click(function() {
			$('.toggle').hide();
			var theName = $(this).attr('title');
			$('span.min').hide();	
			$('span.plus').show();	
			$(this).children('span.plus').hide();
			$(this).children('span.min').show();
			$('div.' + theName).show();
		});


		$('input.vshcheck').click(function() {
			$('.vchecktoggle').toggle();				
		});


		$('input.wshcheck').click(function() {
			$('.wchecktoggle').toggle();				
		});

		$('input.cshcheck').click(function() {
			$('.cchecktoggle').toggle();				
		});

		$('dl.collapse dt').click(function() {
			$(this).parents("dl").removeClass("closed");
		},function(){
			$(this).parents("dl").addClass("closed");
		});		

		$('dl.collapse dt').click(function() {					
			$(this).parents("dl").children("dd").toggle();	
			$(this).children("span.open").toggle();		
			$(this).children("span.closed").toggle();	
		});

		$('textarea.showtip').focus(function(){
			$(this).parents('div.tipwrap').children('div.formtip').show();
		});

		$('.showtip').blur(function(){
			$(this).parents('div.tipwrap').children('div.formtip').hide();
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
					<h2 id="page-role">Administrator</h2>
					<h2 id="page-title">Select Provider Network (SPN)</h2>

					<h3 class="">Create A Invite Campaign</h3>

					<div id="tabs">
						<ul class="tabsmenu">
							<li><a href="/MarketFrontend/jsp/public/spn/network/monitor.jsp"><span><abbr title="Select Provider Network">SPN</abbr> Monitor</span></a></li>
							<li><a href="/MarketFrontend/jsp/public/spn/network/create.jsp"><span>Create Network</span></a></li>
							<li><a href="/MarketFrontend/jsp/public/spn/campaign/monitor.jsp"><span>Campaign Monitor</span></a></li>
							<li class="selected"><a href="/MarketFrontend/jsp/public/spn/campaign/create.jsp"><span>Create Campaigns</span></a></li>
						</ul>
						<div id="tab-content" class="clearfix">
							<div class="information right">
								<jsp:include page="information.jsp" />
							</div>
							<div class="clearfix span-14">																							
									<h3 class="collapse c-saved" title="c-saved"><span>Saved Campaigns</span><span class="plus c-saved"></span><span class="min c-saved"></span></h3>
									<div class="toggle c-saved">
										<fieldset>
											<p>Items marked with an asterix(<span class="req">*</span>) are required.</p>
										</fieldset>
									</div>
									
									<h3 class="collapse c-general" title="c-general"><span>New Campaign</span><span class="plus c-general"></span><span class="min c-general"></span></h3>
									<div class="toggle c-general">
										<fieldset>
											<p>Items marked with an asterix(<span class="req">*</span>) are required.</p>
											
											
											<h3 class="noexpand">Target Locations <span class="req">*</span></h3>
											<p>Select or enter at least one market or state below.</p>
											
											
											<div class="results">
												<div class="even clearfix">
													<h4>Providers Matching <abbr title="Select Provider Network">SPN</abbr> Criteria for Target Locations:</h4>
													<label>Firms</label><span>30</span>
													<label>Providers</label><span>100</span>
												</div>
												<div class="odd clearfix">
													<h4>Providers Matching Invitation:</h4>
													<label>Firms</label><span>30</span>
													<label>Providers</label><span>100</span>
													
													<input type="submit" class="button action right" value="Update Results" />
																										
												</div>
											</div>

											<table border="0" cellpadding="5" cellspacing="0">
												<thead>
													<th colspan="2">
														Insurance Coverage
													</th>
												</thead>
												<tbody>
													<tr>
														<td>
															<div class="left checkbox">
																<input type="checkbox" class="checkbox vshcheck">
															</div>
															<label>Vehicle Liability</label>
														</td>
														</tr>
														<tr>
															<td class="textleft verify">
																<label class="vchecktoggle sm">Minimum Amount<span class="req">*</span> $</label>
																<input class="vchecktoggle text" />
															</td>
														</tr>
													<tr>
														<td>
															<div class="left checkbox">
																<input type="checkbox" class="checkbox wshcheck">
															</div>
															<label>Workers Compensation</label>
														</td>
														</tr>
													<tr>
														<td>

															<div class="left checkbox">
																<input type="checkbox" class="checkbox cshcheck">
															</div>

															<label>Commercial General Liability</label>
														</td>
														</tr>
														<tr>
															<td class="textleft verify">
																<label class="cchecktoggle sm">Minimum Amount<span class="req">*</span> $</label>
																<input class="cchecktoggle text" />
															</td>
														</tr>
												</tbody>
											</table>




											<h3 class="noexpand">Maximum Firm Size</h3>

											
											
										</fieldset>
									</div>

									<h3 class="collapse c-cred" title="c-cred"><span>Provider Credentials</span><span class="plus c-cred"></span><span class="min c-cred"></span></h3>
									<div class="toggle c-cred">
										<fieldset>
											<p>Items marked with an asterix(<span class="req">*</span>) are required.</p>
										</fieldset>
									</div>



									<h3 class="collapse c-serv" title="c-serv"><span>Services &amp; Skills</span><span class="plus c-serv"></span><span class="min c-serv"></span></h3>
									<div class="toggle c-serv">
										<fieldset>
											<p>Items marked with an asterix(<span class="req">*</span>) are required.</p>
										</fieldset>
									</div>




									<h3 class="collapse c-cat" title="c-cat"><span>Specify Categories</span><span class="plus c-cat"></span><span class="min c-cat"></span></h3>
									<div class="toggle c-cat">
										<fieldset>
											<p>Items marked with an asterix(<span class="req">*</span>) are required.</p>
										</fieldset>
									</div>
									
										<div class="clearfix buttonarea">
										<a class="cancel left" href="monitor.jsp">Cancel</a>
										<input type="submit" class="button action right" value="Save &amp; Done">
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<tiles:insertDefinition name="blueprint.base.footer"/>

			</div>
			<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			<jsp:param name="PageName" value="spn:create networks"/>
		</jsp:include>
	</body>
	</html>
