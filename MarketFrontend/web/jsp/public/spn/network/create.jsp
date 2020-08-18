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
	<title>ServiceLive - Select Provider Network - Create Networks</title>
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

					<h3 class="">Create A Select Provider Network</h3>

					<div id="tabs">
						<ul class="tabsmenu">
							<li><a href="/MarketFrontend/jsp/public/spn/network/monitor.jsp"><span><abbr title="Select Provider Network">SPN</abbr> Monitor</span></a></li>
							<li class="selected"><a href="/MarketFrontend/jsp/public/spn/network/create.jsp"><span>Create Network</span></a></li>
							<li><a href="/MarketFrontend/jsp/public/spn/campaign/monitor.jsp"><span>Campaign Monitor</span></a></li>
							<li><a href="/MarketFrontend/jsp/public/spn/campaign/create.jsp"><span>Create Campaigns</span></a></li>
						</ul>
						<div id="tab-content" class="clearfix">
							<div class="information right">
								<jsp:include page="information.jsp" />
							</div>
							<div class="clearfix span-14">								
								<h3 class="collapse c-general" title="c-general"><span>Name &amp; General Information</span><span class="plus c-general"></span><span class="min c-general"></span></h3>

								<div class="toggle c-general">
									<fieldset>
										<p>Items marked with an asterix(<span class="req">*</span>) are required.</p>

										<div class="error">
											Please enter the name of the network.
										</div>

										<p>
											<label>Name of Network <span class="req">*</span></label>
											<input type="text" class="text" />
										</p>

										<div class="clearfix">
											<div class="half">
												<label>Contact Name <span class="req">*</span></label>
												<input type="text" class="text" />
											</div>
											<div class="half">
												<label>Contact Email <span class="req">*</span></label>
												<input type="text" class="text" />
											</div>
										</div>

										<div class="tipwrap">

										<p>
											<label><abbr title="Select Provider Network">SPN</abbr> Description <span class="req">*</span></label>
											<textarea class="showtip text"></textarea>
										</p>

										<div class="formtip" id="spn-description">
											<strong>Please provide a brief description</strong> explaining the benefits of membership.
											<br />Summarize requirements and remind providers this is a limited enrollment opportunity.  This information will appear on the provider invitation.  In <strong>"Documents"</strong>, you can specify attachments for the invitation, with more information.
										</div>
										
										</div>
										
										<div class="tipwrap">
										<p>
											<label>Special Instructions</label>
											<textarea class="showtip text"></textarea>
										</p>
										<div class="formtip" id="spn-description">
											<strong>Please get this copy</strong> from Barb August.
											<br />Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
										</div>
										</div>

										<div class="clearfix" style="height: 60px;">
											<div class="half multiselect">
												<label>Main Services <span class="req">*</span></label>
												<select id="services" name="services" class="arc90_multiselect" multiple="multiple" size="4" title="Services">
													<option value="">Automotive</option>
													<option value="">Cabinets & Countertops</option>
													<option value="">Carpentry & Woodworking</option>
													<option value="">Computer / Network Services</option>
													<option value="">Electrician</option>
													<option value="">Garage & Shed</option>
													<option value="">General Plumbing & Bathrooms</option>
													<option value="">Handyman Services</option>
													<option value="">Heating & Cooling</option>
													<option value="">Home Appliances</option>
													<option value="">Home Electronics</option>
												</select>
											</div>


											<div class="half multiselect">
												<label>Skills <span class="req">*</span></label>
												<select id="skills" multiple="multiple" name="skills" class="arc90_multiselect" size="4" title="Skills">
													<option value="">Install</option>
													<option value="">Repair</option>
													<option value="">Maintenance</option>
												</select>
											</div>
										</div>
									</fieldset>
								</div>


								<h3 class="collapse c-approval" title="c-approval"><span>Approval Criteria &amp; Credentials</span><span class="plus"></span><span class="min"></span></h3>
								<div class="toggle c-approval">
									<fieldset>
										<p>Items marked with an asterix(<span class="req">*</span>) are required.</p>

										<div class="clearfix" style="height: 60px;">
											<div class="half multiselect">
												<label>Minimum Rating</label>
												<select id="rating" name="rating" class="arc90_multiselect" multiple="multiple" size="4" title="Rating">
													<option value="">More than 3 Stars</option>
													<option value="" selected="true">More than 1 Star</option>
												</select>
											</div>
											<div class="half multiselect">
												<label>Languages</label>	
												<select id="lang" name="lang" class="arc90_multiselect" multiple="multiple" size="4" title="Languages">
													<option value="fr">French</option>
													<option value="en" selected="true">English</option>
													<option value="sp">Spanish</option>
													<option value="jp" selected="true">Japanese</option>
													<option value="du">Dutch</option>
													<option value="kk">Klingon</option>
													<option value="et">Esperanto</option>
													<option value="ru">Russian</option>
													<option value="ce">Canadian English</option>
													<option value="ar">Arabic</option>
													<option value="gr">German</option>
													<option value="ar">Aramaic</option>
													<option value="ch">Chinese</option>
													<option value="po">Polish</option>
													<option value="yi">Yiddish</option>
													<option value="kr">Korean</option>
												</select>
											</div>
										</div>

										<p>
											<label>Minimum Completed Service Orders</label>
											<input type="text" class="text short" />
										</p>


										<table border="0" cellpadding="5" cellspacing="0">
											<thead>
												<th colspan="2">
													Insurance
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
													<td class="textleft verify">
														<div class="left checkbox vchecktoggle">
															<input type="checkbox" class="checkbox vchecktoggle">
														</div>
														<div class="left vchecktoggle">
															<span class="checkbox vchecktoggle">Must Be Verified</span>
														</div>
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
													<td class="textleft verify">
														<div class="left checkbox wchecktoggle">
															<input type="checkbox" class="checkbox wchecktoggle">
														</div>
														<div class="left wchecktoggle">
															<span class="checkbox wchecktoggle">Must Be Verified</span>
														</div>
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
													<tr>
													<td class="textleft verify">
														<div class="left checkbox cchecktoggle">
															<input type="checkbox" class="checkbox cchecktoggle">
														</div>
														<div class="left cchecktoggle">
															<span class="checkbox cchecktoggle">Must Be Verified</span>
														</div>
													</td>
												</tr>
											</tbody>
										</table>

										<div class="notice clearfix">
											<label class="checkbox">
												<div class="left checkbox">
													<input type="checkbox" class="checkbox">
												</div>
												<div class="left">
													<span>A meeting with the Provider Firm is required for approval.</span></label>
												</div>
											</fieldset>
										</div>

										<h3 class="collapse c-documents" title="c-documents"><span>Documents</span><span class="plus"></span><span class="min"></span></h3>
										
										<div class="toggle c-documents">
											<fieldset>
												<p>Items marked with an asterix(<span class="req">*</span>) are required.</p>

												<p>
													<label>Select Documents <span class="req">*</span></label>
													<select class="select left">
														<option>Contract A (Sign & Return)</option>
														<option>Terms &amp; Conditions A (Electronic Agreement)</option>
														<option>Company Policy (Information Only)</option>
														<option>W9 (Sign & Return)</option>
													</select>

													<input type="submit" value="Add" class="default documentbutton" />		
												</p>
												
												
												
												<div class="information main clearfix">
												<dl class="collapse">
													<dt><span>Upload New Document(s)</span> <span class="open"></span> <span class="closed"></span></dt>
													<dd class="clearfix" style="clear: left;">
														<div class="uploadform">
															<div class="clearfix">
																<div class="half">
																<input type="text" class="text" />
																<input type="submit" class="default button" value="Browse">
																</div>
																<div class="half" style="width: 250px; padding-left: 10px">
																<span class="sm"><strong>Accepted File Types:</strong><br />.jpg | .pdf | .doc | .gif | .tiff | .png | .bmp</span>
																<span class="sm"><br /><strong>Max file size:</strong> 100MB</span>
																</div>
															</div>

															<div class="clearfix">
																<div class="half">
																	<label>Document Title<span class="req">*</span></label>
																	<input type="text" class="text" />
																</div>
																<div class="half">
																	<label>Document Type <span class="req">*</span></label>
																	<select class="select"><option>Select One</option></select>
																</div>
															</div>

															<p><label>Document Description <span class="req">*</span></label>
																<textarea></textarea>
															</p>
															
															
															<a href="#" class="left">Upload &amp; Add More</a>
															<input type="submit" class="button action" value="Upload &amp; Close">
															
														</div>
													</dd>
												</dl>
												</div>

												<table border="0" cellpadding="5" cellspacing="0" class="hoverEffect" style="clear: left;">
													<thead>	
														<tr>
															<th>Document Title</th>
															<th colspan="2">Type</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td><label>Contract A</label></td>
															<td>Sign & Return</td>
															<td class="textright"><a href="#" class="cancel" title="Remove This Document"><img src="/ServiceLiveWebUtil/images/s_icons/cancel.png" alt="Delete" /></a></td>
														</tr>
														<tr>
															<td><label>Terms &amp; Conditions A</label></td>
															<td>Electronic Agreement</td>
															<td class="textright"><a href="#" class="cancel" title="Remove This Document"><img src="/ServiceLiveWebUtil/images/s_icons/cancel.png" alt="Delete" /></a></td>
														</tr>
														<tr>
															<td><label>Contract A</label></td>
															<td>Sign & Return</td>
															<td class="textright"><a href="#" class="cancel" title="Remove This Document"><img src="/ServiceLiveWebUtil/images/s_icons/cancel.png" alt="Delete" /></a></td>
														</tr>
														<tr>
															<td><label>Contract B</label></td>
															<td>Sign & Return</td>
															<td class="textright"><a href="#" class="cancel" title="Remove This Document"><img src="/ServiceLiveWebUtil/images/s_icons/cancel.png" alt="Delete" /></a></td>
														</tr>
														<tr>
															<td><label>Company Policy</label></td>
															<td>Information Only</td>
															<td class="textright"><a href="#" class="cancel" title="Remove This Document"><img src="/ServiceLiveWebUtil/images/s_icons/cancel.png" alt="Delete" /></a></td>
														</tr>
														<tr>
															<td><label>W9</label></td>
															<td>Sign & Return</td>
															<td class="textright"><a href="#" class="cancel" title="Remove This Document"><img src="/ServiceLiveWebUtil/images/s_icons/cancel.png" alt="Delete" /></a></td>
														</tr>
													</tbody>
												</table>



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
