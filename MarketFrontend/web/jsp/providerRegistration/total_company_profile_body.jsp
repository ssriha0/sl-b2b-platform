<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", -1);
%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<script>
		var hostname = '<%=request.getServerName()%>';
		jQuery(document).ready(function($){
			var vlink = "https://sealinfo.verisign.com/splash?form_file=fdf/splash.fdf&sealid=1&dn=" + hostname +"&lang=en";
			$('a.verisign').attr('href', vlink);
			
			});
	</script>
		<!-- acquity: modified meta tag to set charset -->
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />

		<title>ServiceLive - Provider Profile</title>


		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />

		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/acquity.css" />
		<!--[if IE]>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
		<![endif]-->

		<!-- acquity: here is the new js, please minify/pack the toolbox and rename as you wish -->
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="http://maps.google.com/maps?file=api&v=2&key=ABQIAAAA2NKh7FSp8v7RSej1NP5MLRS3-9Rd8VXnXN-HdEgFJs6UcdMXEhR6pm5mlphQIDtWdJveFzGnLQepkg"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>

		<script type="text/javascript">
	 	jQuery(document).ready(function($){	
			$('#drawmap').jmap('init', {mapCenter:[42.062577,-88.144694]});
			$('#drawmap').jmap("addMarker", {pointLatLng:[42.062577,-88.144694]});
		});
		</script>

		<script language="JavaScript" type="text/javascript">
			function not_tab_two_click()
			{	
				document.getElementById('company_ratings').style.display='none';
				document.getElementById('provider_ratings').style.display='';
			}
			function tab_two_click()
			{	
				document.getElementById('company_ratings').style.display='';
				document.getElementById('provider_ratings').style.display='none';
			}
		</script>


	</head>
	<body class="tundra acquity">
	    
		<div id="page_margins">
			<div id="page">
				<div id="hpWrap" class="shaded clearfix">

					<div id="hpContent" class="pdtop20">

						<div id="hpIntro" class="clearfix">
							<div class="profilebox">
								<img class="left"
									src="${staticContextPath}/images/dynamic/profile.jpg" alt="" />

								<h2>
									Wyatt K.
									<span>ID#1234567890</span>
								</h2>
								<h3>
									&nbsp;
								</h3>
								<h3>
									Master Electrician
								</h3>

								<h3>
									Atlanta, GA
								</h3>
								<h3>
									17 Years of Experience
								</h3>

								<h4>
									Approved ServiceLive Provider
								</h4>
							</div>
		
							<div id="company_ratings" class="widget ratingsbox" style="display: none;">
								<h2>
									Detailed Company Ratings
								</h2>

								<table border="0">
									<tr>
										<td>
											Quality
										</td>
										<td>
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
										</td>
										<td>
											1024 Ratings
										</td>
									</tr>
									<tr>
										<td>
											Cleanliness
										</td>
										<td>
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
										</td>
										<td>
											1024 Ratings
										</td>
									</tr>
									<tr>
										<td>
											Communication
										</td>
										<td>
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
										</td>
										<td>
											1024 Ratings
										</td>
									</tr>
									<tr>
										<td>
											Timeliness
										</td>
										<td>
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
										</td>
										<td>
											1024 Ratings
										</td>
									</tr>

									<tr>
										<td>
											Value
										</td>
										<td>
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
										</td>
										<td>
											1024 Ratings
										</td>
									</tr>
									<tr>
										<td>
											Professionalism
										</td>
										<td>
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
										</td>
										<td>
											1024 Ratings
										</td>
									</tr>
									<tr>
										<td>
											Overall
										</td>
										<td>
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
										</td>
										<td>
											1024 Ratings
										</td>
									</tr>
								</table>
							</div>


							<div id="provider_ratings" class="widget ratingsbox">
								<h2>
									Detailed Provider Ratings
								</h2>

								<table border="0">
									<tr>
										<td>
											Quality
										</td>
										<td>
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
										</td>
										<td>
											1024 Ratings
										</td>
									</tr>
									<tr>
										<td>
											Cleanliness
										</td>
										<td>
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
										</td>
										<td>
											1024 Ratings
										</td>
									</tr>
									<tr>
										<td>
											Communication
										</td>
										<td>
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
										</td>
										<td>
											1024 Ratings
										</td>
									</tr>
									<tr>
										<td>
											Timeliness
										</td>
										<td>
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
										</td>
										<td>
											1024 Ratings
										</td>
									</tr>

									<tr>
										<td>
											Value
										</td>
										<td>
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
										</td>
										<td>
											1024 Ratings
										</td>
									</tr>
									<tr>
										<td>
											Professionalism
										</td>
										<td>
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
										</td>
										<td>
											1024 Ratings
										</td>
									</tr>
									<tr>
										<td>
											Overall
										</td>
										<td>
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
										</td>
										<td>
											1024 Ratings
										</td>
									</tr>
								</table>
							</div>

						</div>

						<div id="providerTabs">
							<ul class="clearfix botbor botmg10">
								<li>
									<a href="#fragment-1" onClick="not_tab_two_click();"><span>Provider Profile</span>
									</a>
								</li>
								<li>
									<a href="#fragment-2" onClick="tab_two_click();"><span>Business
											Profile</span>
									</a>
								</li>
								<li>
									<a href="#fragment-3" onClick="not_tab_two_click();"><span>Customer Feedback</span>
									</a>
								</li>

							</ul>
							<div id="fragment-1" class="clearfix">
								<div class="hpDescribe clearfix">
									<div>
										<h3 style="margin: 0px;">
											Company Overview
											<span>ID#1234567890</span>
										</h3>
										<p>
											Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
											do eiusmod tempor incididunt ut labore et dolore magna
											aliqua. Ut enim ad minim veniam, quis nostrud exercitation
											ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis
											aute irure dolor in reprehenderit in voluptate velit esse
											cillum dolore eu fugiat nulla pariatur. Excepteur sint
											occaecat cupidatat non proident, sunt in culpa qui officia
											deserunt mollit anim id est laborum.
										</p>
									</div>
								</div>

								<div class="hpDescribe clearfix">
									<h2>
										Availability
									</h2>
									<div class="clearfix weekcal">
										<table cellspacing="1">
											<tr>
												<td>
													Sun
												</td>
												<td>
													Mon
												</td>
												<td>
													Tue
												</td>
												<td>
													Wed
												</td>
												<td>
													Thur
												</td>
												<td>
													Fri
												</td>
												<td>
													Sat
												</td>
											</tr>
											<tr class="weekdays">
												<td>
													n/a
												</td>
												<td>
													9am - 5 pm
													<br />
													<a href="#">Schedule This Provider</a>
												</td>
												<td>
													n/a
													<br />
												</td>
												<td>
													9am - 5 pm
													<br />
													<a href="#">Schedule This Provider</a>
												</td>
												<td>
													9am - 5 pm
													<br />
													<a href="#">Schedule This Provider</a>
												</td>
												<td>
													9am - 5 pm
													<br />
													<a href="#">Schedule This Provider</a>
												</td>
												<td>
													n/a
												</td>
											</tr>
										</table>
									</div>
								</div>
								<a id='Details' ></a>
								<div class="hpDescribe clearfix">
									<h2>
										Details
									</h2>

									<h3 class="clearfix" style="display: block; margin: 0px;">
										Services
									</h3>

									<ul class="accList ui-accordion-container">
										<li>
											<div class="ui-accordion-left"></div>
											<a onClick="this.blur();" href='#Details' class="ui-accordion-link">Home &amp;
												Appliances</a>

											<div>
												<table class="offerings" border="0" cellspacing="0">
													<tr>
														<th>
															&nbsp;
														</th>
														<th>
															Delivery
														</th>
														<th>
															Installation
														</th>
														<th>
															Repair
														</th>
														<th>
															Maintenance
														</th>
													</tr>
													<tr>
														<td class="title">
															Plasma TV
														</td>

														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>
													<tr>
														<td class="title">
															LCD TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>

														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>
													<tr>
														<td class="title">
															LCD TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>

														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>
													<tr>
														<td class="title">
															LCD TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>

														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>
													<tr>
														<td class="title">
															LCD TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>

													</tr>
													<tr>
														<td class="title">
															LCD TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>

													<tr>
														<td class="title">
															LCD TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>
													<tr>

														<td class="title">
															LCD TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>
												</table>
											</div>
										</li>

										<li>
											<div class="ui-accordion-left"></div>
											<a onClick="this.blur();" href='#Details' class="ui-accordion-link">Product Assembly</a>
											<div>
												<table class="offerings" border="0" cellspacing="0">
													<tr>
														<th>
															&nbsp;
														</th>
														<th>
															Delivery
														</th>
														<th>
															Installation
														</th>
														<th>
															Repair
														</th>
														<th>
															Maintenance
														</th>

													</tr>
													<tr>
														<td class="title">
															Plasma TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>

													<tr>
														<td class="title">
															LCD TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>
													<tr>

														<td class="title">
															LCD TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>
													<tr>
														<td class="title">
															LCD TV
														</td>

														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>
													<tr>
														<td class="title">
															LCD TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>

														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>
													<tr>
														<td class="title">
															LCD TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>

														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>
													<tr>
														<td class="title">
															LCD TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>

														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>
													<tr>
														<td class="title">
															LCD TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>

													</tr>
												</table>
											</div>
										</li>
										<li>
											<div class="ui-accordion-left"></div>
											<a onClick="this.blur();" href='#Details' class="ui-accordion-link">Home Electronics</a>
											<div>
												<table class="offerings" border="0" cellspacing="0">

													<tr>
														<th>
															&nbsp;
														</th>
														<th>
															Delivery
														</th>
														<th>
															Installation
														</th>
														<th>
															Repair
														</th>
														<th>
															Maintenance
														</th>
													</tr>
													<tr>
														<td class="title">
															Plasma TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>

														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>
													<tr>
														<td class="title">
															LCD TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>

														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>
													<tr>
														<td class="title">
															LCD TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>

														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>
													<tr>
														<td class="title">
															LCD TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>

													</tr>
													<tr>
														<td class="title">
															LCD TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>

													<tr>
														<td class="title">
															LCD TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>
													<tr>

														<td class="title">
															LCD TV
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>
													<tr>
														<td class="title">
															LCD TV
														</td>

														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
														<td>
															<img src="${staticContextPath}/images/icons/02.png"
																alt="" />
														</td>
													</tr>
												</table>
											</div>
										</li>
									</ul>

									<h3 style="margin-top: 0px;">
										Statistics
									</h3>

									<div class="creds clearfix">
										<div class="credlist">
											<h4>
												122 Years in Business
											</h4>
										</div>
										<div class="credlist">
											<h4>
												1024 Jobs Completed with ServiceLive
											</h4>
										</div>

										<div class="credlist">

											<h4>
												Licensed & Bonded
											</h4>
										</div>



									</div>

									<h3>
										Credentials
									</h3>

									<div class="creds clearfix ">
										<div class="credlist">
											<h4>
												Some Credential
											</h4>
											Some Education Firm or Licensing
											<br />
											Date Verified: December 04, 2007
											<br />
											2001 - 2005
										</div>

										<div class="credlist">
											<h4>
												Some Credential
												<img alt="Verified Credential"
													src="${staticContextPath}/images/common/verified.png" />
											</h4>

											Some Education Firm or Licensing
											<br />
											Date Verified: December 04, 2007
											<br />
											2001 - 2005
										</div>

										<div class="credlist">
											<h4>
												Some Credential
												<img alt="Verified Credential"
													src="${staticContextPath}/images/common/verified.png" />
											</h4>
											Some Education Firm or Licensing
											<br />
											Date Verified: December 04, 2007
											<br />
											2001 - 2005
										</div>

										<div class="credlist">
											<h4>
												Some Credential
												<img alt="Verified Credential"
													src="${staticContextPath}/images/common/verified.png" />
											</h4>
											Some Education Firm or Licensing
											<br />
											Date Verified: December 04, 2007
											<br />
											2001 - 2005
										</div>

										<div class="credlist">
											<h4>
												Some Credential
											</h4>
											Some Education Firm or Licensing
											<br />
											Date Verified: December 04, 2007
											<br />
											2001 - 2005
										</div>

										<div class="credlist">
											<h4>
												Some Credential
												<img alt="Verified Credential"
													src="${staticContextPath}/images/common/verified.png" />
											</h4>
											Some Education Firm or Licensing
											<br />
											Date Verified: December 04, 2007
											<br />
											2001 - 2005
										</div>

										<div class="credlist">
											<h4>
												Some Credential
												<img alt="Verified Credential"
													src="${staticContextPath}/images/common/verified.png" />
											</h4>

											Some Education Firm or Licensing
											<br />
											Date Verified: December 04, 2007
											<br />
											2001 - 2005
										</div>

										<div class="credlist">
											<h4>
												Some Credential
												<img alt="Verified Credential"
													src="${staticContextPath}/images/common/verified.png" />
											</h4>
											Some Education Firm or Licensing
											<br />
											Date Verified: December 04, 2007
											<br />
											2001 - 2005
										</div>
									</div>
								</div>
							</div>
							<div id="fragment-2" class="clearfix">
								<div class="hpDescribe clearfix">
									<div>
										<h3 style="margin: 0px;">
											Company Overview
											<span>ID#1234567890</span>
										</h3>

										<p>
											Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
											do eiusmod tempor incididunt ut labore et dolore magna
											aliqua. Ut enim ad minim veniam, quis nostrud exercitation
											ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis
											aute irure dolor in reprehenderit in voluptate velit esse
											cillum dolore eu fugiat nulla pariatur. Excepteur sint
											occaecat cupidatat non proident, sunt in culpa qui officia
											deserunt mollit anim id est laborum.
										</p>
									</div>

								</div>


								<div class="hpDescribe clearfix">
									<h2>
										Details
									</h2>

									<h3 style="margin-top: 0px;">
										Statistics
									</h3>
									<div class="creds clearfix">
										<div class="credlist">
											<h4>
												Company ID# 123456
											</h4>
										</div>

										<div class="credlist">
											<h4>
												Business Structure: S Corporation
											</h4>
										</div>

										<div class="credlist">
											<h4>
												122 Years in Business
											</h4>
										</div>
										<div class="credlist">
											<h4>
												ServiceLive Member Since April 7, 2008
											</h4>
										</div>

										<div class="credlist">
											<h4>
												1024 Jobs Completed with ServiceLive
											</h4>
										</div>

										<div class="credlist">
											<h4>
												Primary Industry: Computer/Network Services
											</h4>
										</div>

										<div class="credlist">
											<h4>
												976 Positive Reviews
												<a style="font-weight: normal;" href="#">&raquo; view</a>
											</h4>
										</div>
										<div class="credlist">
											<h4>
												2-10 Employees
											</h4>

										</div>

									</div>

									<h3>
										Warranty Information
									</h3>

									<ul style="list-style-position: inside;">
										<li>
											This company charges for project estimates
										</li>
										<li>
											90 Day Warranty on all new parts
										</li>
									</ul>

									<h3>
										Insurance Policies
									</h3>


									<ul style="list-style-position: inside;">
										<li>
											None on file
										</li>
									</ul>

								</div>


								<div class="hpDescribe clearfix">
									<h2>
										Service Pro's
									</h2>

									<div class="clearfix">
										<div class="credlist clearfix">

											<a href="#"><img
													src="../../images/dynamic/provider_sm.jpg" class="left"
													style="border: 1px solid silver;" alt="" />
											</a>
											<h4>
												<a href="#">Wyatt K.</a>
											</h4>
											ID# 1234567890
											<br />
											<br />
											Master Electrician
											<br />
											<img alt="Full Star" src="../../images/common/full_star.gif" />
											<img alt="Full Star" src="../../images/common/full_star.gif" />
											<img alt="Full Star" src="../../images/common/full_star.gif" />
											<img alt="Full Star" src="../../images/common/full_star.gif" />
											<img alt="Full Star" src="../../images/common/full_star.gif" />
										</div>



										<div class="credlist clearfix">
											<a href="#"><img
													src="../../images/dynamic/default_sm.jpg" class="left"
													style="border: 1px solid silver;" alt="" />
											</a>

											<h4>
												<a href="#">Jon D.</a>
											</h4>
											ID# 1234567890
											<br />
											<br />
											General Contractor
											<br />
											<img alt="Full Star" src="../../images/common/full_star.gif" />
											<img alt="Full Star" src="../../images/common/full_star.gif" />
											<img alt="Full Star" src="../../images/common/full_star.gif" />
											<img alt="Full Star" src="../../images/common/full_star.gif" />
											<img alt="Full Star" src="../../images/common/full_star.gif" />
										</div>

										<div class="credlist clearfix">
											<a href="#"><img
													src="../../images/dynamic/default_sm.jpg" class="left"
													style="border: 1px solid silver;" alt="" />
											</a>
											<h4>
												<a href="#">Jon D.</a>
											</h4>
											ID# 1234567890
											<br />
											<br />
											General Contractor
											<br />
											<img alt="Full Star" src="../../images/common/full_star.gif" />
											<img alt="Full Star" src="../../images/common/full_star.gif" />
											<img alt="Full Star" src="../../images/common/full_star.gif" />
											<img alt="Full Star" src="../../images/common/full_star.gif" />
											<img alt="Full Star" src="../../images/common/full_star.gif" />

										</div>

										<div class="credlist clearfix">
											<a href="#"><img
													src="../../images/dynamic/default_sm.jpg" class="left"
													style="border: 1px solid silver;" alt="" />
											</a>
											<h4>
												<a href="#">Jon D.</a>
											</h4>
											ID# 1234567890
											<br />
											<br />
											General Contractor
											<br />
											<img alt="Full Star" src="../../images/common/full_star.gif" />
											<img alt="Full Star" src="../../images/common/full_star.gif" />
											<img alt="Full Star" src="../../images/common/full_star.gif" />
											<img alt="Full Star" src="../../images/common/full_star.gif" />
											<img alt="Full Star" src="../../images/common/full_star.gif" />
										</div>

									</div>


								</div>
							</div>
							<div id="fragment-3" class="clearfix">
								<div class="hpDescribe clearfix">
									<div class="quotes">
										<h3 style="margin: 0px;">
											Alvah R., Lafayette, IN
											<span>(four months ago)</span>
										</h3>
										<p>
											Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
											do eiusmod tempor incididunt ut labore et dolore magna
											aliqua. Ut enim ad minim veniam, quis nostrud exercitation
											ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis
											aute irure dolor in reprehenderit in voluptate velit esse
											cillum dolore eu fugiat nulla pariatur. Excepteur sint
											occaecat cupidatat non proident, sunt in culpa qui officia
											deserunt mollit anim id est laborum.
										</p>
										<div style="margin-left: 50px;">

											<h4>
												Job: Service / Service / Service / Installation
											</h4>
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
										</div>
									</div>

								</div>

								<div class="hpDescribe clearfix">
									<div class="quotes">
										<h3 style="margin: 0px;">
											Alvah R., Lafayette, IN
											<span>(four months ago)</span>
										</h3>
										<p>
											Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
											do eiusmod tempor incididunt ut labore et dolore magna
											aliqua. Ut enim ad minim veniam, quis nostrud exercitation
											ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis
											aute irure dolor in reprehenderit in voluptate velit esse
											cillum dolore eu fugiat nulla pariatur. Excepteur sint
											occaecat cupidatat non proident, sunt in culpa qui officia
											deserunt mollit anim id est laborum.
										</p>
										<div style="margin-left: 50px;">
											<h4>
												Job: Service / Service / Service / Installation
											</h4>

											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
										</div>
									</div>
								</div>

								<div class="hpDescribe clearfix">

									<div class="quotes">
										<h3 style="margin: 0px;">
											Alvah R., Lafayette, IN
											<span>(four months ago)</span>
										</h3>
										<p>
											Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
											do eiusmod tempor incididunt ut labore et dolore magna
											aliqua. Ut enim ad minim veniam, quis nostrud exercitation
											ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis
											aute irure dolor in reprehenderit in voluptate velit esse
											cillum dolore eu fugiat nulla pariatur. Excepteur sint
											occaecat cupidatat non proident, sunt in culpa qui officia
											deserunt mollit anim id est laborum.
										</p>
										<div style="margin-left: 50px;">
											<h4>
												Job: Service / Service / Service / Installation
											</h4>
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />

											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
										</div>
									</div>
								</div>

								<div class="hpDescribe clearfix">
									<div class="quotes">
										<h3 style="margin: 0px;">
											Richard S., Stewartville, MN
											<span>(four months ago)</span>
										</h3>

										<p>
											Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
											do eiusmod tempor incididunt ut labore et dolore magna
											aliqua. Ut enim ad minim veniam, quis nostrud exercitation
											ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis
											aute irure dolor in reprehenderit in voluptate velit esse
											cillum dolore eu fugiat nulla pariatur. Excepteur sint
											occaecat cupidatat non proident, sunt in culpa qui officia
											deserunt mollit anim id est laborum.
										</p>
										<div style="margin-left: 50px;">
											<h4>
												Job: Service / Service / Service / Installation
											</h4>
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />
											<img alt="Full Star"
												src="${staticContextPath}/images/common/full_star.gif" />

										</div>
									</div>
								</div>
							</div>
						</div>




					</div>
					<div id="hpSidebar">
						<div id="providerMap" class="widget clearfix">
							<h2>
								Coverage Area
							</h2>
							<!-- google maps this -->
							<div id="drawmap"
								style="width: 269px; height: 217px; margin-bottom: 5px; border: 1px solid silver;"></div>
							<div class="zipCov">
								Hoffman Estates, IL 55555
								<span>Within 30 Miles</span>
							</div>
						</div>

						<div id="hpTip" class="widget tips feedback">
							<div class="i31 provActivity">

								<h2>
									Recent Activity
								</h2>
								<dl>
									<dt>
										Service Orders (Total)
									</dt>
									<dd>
										1,012
									</dd>
									<dt>
										Last 30 Days
									</dt>
									<dd>
										73
									</dd>

									<dt>
										Last 60 Days
									</dt>
									<dd>
										135
									</dd>
								</dl>

								<dl>
									<dt>
										Released Service Orders
									</dt>
									<dd>
										3
									</dd>
									<dt>
										Counter Offers
									</dt>
									<dd>
										8%
									</dd>
								</dl>
							</div>
							<div class="i30 provFeedback">
								<h2>
									Customer Feedback
								</h2>

								<strong>Alvah R., Lafayette, IN</strong>
								<img alt="Full Star"
									src="${staticContextPath}/images/common/full_star.gif" />
								<img alt="Full Star"
									src="${staticContextPath}/images/common/full_star.gif" />
								<img alt="Full Star"
									src="${staticContextPath}/images/common/full_star.gif" />
								<img alt="Full Star"
									src="${staticContextPath}/images/common/full_star.gif" />
								<img alt="Full Star"
									src="${staticContextPath}/images/common/full_star.gif" />
								<p>
									<em>Lorem ipsum dolor sit amet, consectetur adipisicing
										elit, sed do eiusmod tempor incididunt ut labore et dolore
										magna aliqua. Ut enim ad minim veniam, quis nostrud
										exercitation .... </em><a href="#">Read More</a>
								</p>

								<strong>Richard S., Stewartville, MN</strong>
								<img alt="Full Star"
									src="${staticContextPath}/images/common/full_star.gif" />
								<img alt="Full Star"
									src="${staticContextPath}/images/common/full_star.gif" />
								<img alt="Full Star"
									src="${staticContextPath}/images/common/full_star.gif" />
								<img alt="Full Star"
									src="${staticContextPath}/images/common/full_star.gif" />
								<img alt="Full Star"
									src="${staticContextPath}/images/common/full_star.gif" />
								<p>
									<em>Lorem ipsum dolor sit amet, consectetur adipisicing
										elit, sed do eiusmod tempor incididunt ut labore et dolore
										magna aliqua. Ut enim ad minim veniam, quis nostrud
										exercitation .... </em><a href="#">Read More</a>
								</p>

							</div>
						</div>
					</div>
				</div>
				<!-- START FOOTER -->
				<div id="theFooter">
					<div id="footer">
						<div class="footericons">
							<%@ include file="/html/BBB_Image.html"%>
                            
					    	
					    	<c:set var="ref" scope="request" value="<%=request.getServerName()%>" />
							<a href="http://www.instantssl.com/wildcard-ssl.html"  style="text-decoration: none;">
           				       <img src="${staticContextPath}/images/common/comodo_secure_76x26_white.png" alt="Free SSL Certificate" width="76" height="26" style="border: 0px;position:relative;top:3px;"><br>
           				    </a><br>
							<!-- <a title="ServiceLive is Secured by Verisign" target="_blank" href="https://sealinfo.verisign.com/splash?form_file=fdf/splash.fdf&sealid=1&dn=${ref}&lang=en" class="verisign" ><img title="ServiceLive is Secured by Verisign" border="0" src="${staticContextPath}/images/common/veriSign.jpg" alt="ServiceLive is Secured by Verisign" /></a> -->
						</div>
						<p class="lightBlue">
							<a class="jqRproblem" href="#">Report A Problem</a>
							<span class="PIPES">|</span>
							<a
								href="/MarketFrontend/jsp/public/common/footer/terms_of_use.jsp">Terms
								of Use</a>
							<span class="PIPES">|</span>
							<a   
								href="https://transformco.com/privacy" target="_blank">Privacy
								Policy</a>
							<span class="PIPES">|</span>
							<a
								href="https://transformco.com/privacy#_Toc31123888" target="_blank">California Privacy
								Policy</a>
							<span class="PIPES">|</span>
							<a
								href="/MarketFrontend/jsp/public/common/footer/provider_agreement.jsp">Provider
								Agreement</a>
							<span class="PIPES">|</span>

							<a href="/MarketFrontend/jsp/public/common/footer/buyer.jsp">Buyer
								Agreement</a>
						</p>
 						<p class="faintgrey">
 							ServiceLive is a Transform Holdco Company. &copy; <c:import url="/jsp/public/common/copyrightYear.jsp" /> Transform ServiceLive LLC. (v 3.0) 
 						</p>
 						<p class="faintgrey">
        <!-- FRONT END DEV NOTE 12/6: Changed "states" to "U.S. territories"-->
        <!-- SL-21161: Removed "VT" from list of U.S. territories unable to fulfill buyer requests-->
        <b>We are unable to fulfill buyer requests in the following states/U.S. Territories: </b> AS,
        
        FM,

		GU,
        
        MH,
        
        MP,
        
        PW,

		VI </p>
					</div>
				</div>
				<!-- END FOOTER -->


			</div>
		</div>
		<!-- acquity: empty divs to ajax the modal content into -->
		
		<div id="serviceFinder" class="jqmWindow"></div>
		<div id="modal123" class="jqmWindowSteps"></div>
		<div id="zipCheck" class="jqmWindow"></div>

		<!-- Start of DoubleClick Spotlight Tag: Please do not remove-->
		<!-- Activity Name for this tag is:ServiceLive-Universal-Tag -->
		<!-- Web site URL where tag should be placed: http://www.servicelive.com  Global Footer-->
		<!-- This tag must be placed within the opening <body> tag, as close to the beginning of it as possible-->
		<!-- Creation Date:12/4/2008 -->
		<SCRIPT language="JavaScript">
		var axel = Math.random()+"";
		var a = axel * 10000000000000;
		document.write('<IFRAME SRC="http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num='+ a + '?" WIDTH=1 HEIGHT=1 FRAMEBORDER=0></IFRAME>');
		</SCRIPT>
		<NOSCRIPT>
		<IFRAME SRC="http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=1?" WIDTH=1 HEIGHT=1 FRAMEBORDER=0></IFRAME>
		</NOSCRIPT>
		<!-- End of DoubleClick Spotlight Tag: Please do not remove-->

	</body>
</html>
