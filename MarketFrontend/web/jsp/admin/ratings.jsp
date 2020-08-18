<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive Ratings</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit.Dialog");
			dojo.require("dijit._Calendar");
			dojo.require("dojo.date.locale");
			dojo.require("dojo.parser");
			dojo.require("dijit.form.Slider");
			dojo.require("dijit.layout.LinkPane");
			//dojo.require("newco.servicelive.SOMRealTimeManager");
			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
		</script>
		<style type="text/css">
@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
</style>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/slider.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/sl_admin.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css" />
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<script language="javascript">
	selectedNav = function (){ $("mktplaceTools").addClass("selected"); } 
	window.addEvent('domready',selectedNav);
</script>
	</head>
	<body class="tundra">
	  
		<div id="page_margins">
			<div id="page">
				<!-- START HEADER -->
				<div id="headerSPReg">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
					<div id="pageHeader">
						<div>
							<img
								src="${staticContextPath}/images/sl_admin/hdr_mktplace_ratings.gif"
								width="203" height="17" alt="Marketplace | Ratings"
								title="Marketplace | Ratings" />
						</div>
					</div>
				</div>
				<!-- END HEADER -->
				<div class="colRight255 clearfix">
					<!-- #INCLUDE file="html_sections/modules/search_menu.jsp" -->

				</div>
				<div class="colLeft711">
					<div class="content">
						<p>
							Consectetuer adipiscing elit, sed diam nonummy nibh euismod
							tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi
							enim ad minim veniam, quis nostrud exerci tation ullamcorper
							suscipit lobortis nisl ut aliquip ex ea commodo consequat.
						</p>
						<h4>
							Summary of Ratings
						</h4>
						<table cellspacing="0" cellpadding="0"
							class="scrollerTableHdr ratingsGridHdr">
							<tbody>
								<tr>
									<td class="column1">
										View Details
									</td>
									<td class="column2">
										<a class="sortGridColumnUp" href="">Service Order #</a>
									</td>
									<td class="column3">
										<a class="sortGridColumnUp" href="">User ID#</a>
									</td>
									<td class="column4">
										<a class="sortGridColumnUp2" href="">Provider<br>
											Firm ID#</a>
									</td>
									<td class="column5">
										<a class="sortGridColumnUp2" href="">Buyer<br> Firm
											ID#</a>
									</td>
									<td class="column6">
										<a class="sortGridColumnUp" href="">Main Task Category</a>
									</td>
									<td class="column7">
										<a class="sortGridColumnUp" href="">Overall Rating</a>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="grayTableContainer ratingsGridContainer">
							<table cellspacing="0" cellpadding="0"
								class="ratingsGrid gridTable">
								<tbody>
									<tr>
										<td class="column1">
											<a href=""><img alt="Expand"
													src="${staticContextPath}/images/grid/right-arrow.gif" /> </a>
										</td>
										<td class="column2">
											<a href="">123-456-789-000 </a>
										</td>
										<td class="column3">
											<a href="">123456 </a>
										</td>
										<td class="column4">
											<a href="">234567 </a>
										</td>
										<td class="column5">
											<a href="">225874 </a>
										</td>
										<td class="column6">
											Consumer Electronics
										</td>
										<td class="column7">
											<span onmouseover="popUp(event,'userRatings')"
												onmouseout="popUp(event,'userRatings')" class="RATE"><img
													border="0"
													src="${staticContextPath}/images/common/full_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/full_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/half_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/empty_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/empty_star_gbg.gif" />
											</span>
										</td>
									</tr>
									<tr>
										<td class="column1">
											<a href=""><img alt="Expand"
													src="${staticContextPath}/images/grid/right-arrow.gif" /> </a>
										</td>
										<td class="column2">
											<a href="">123-456-789-000 </a>
										</td>
										<td class="column3">
											<a href="">123456 </a>
										</td>
										<td class="column4">
											<a href="">234567 </a>
										</td>
										<td class="column5">
											<a href="">225874 </a>
										</td>
										<td class="column6">
											Consumer Electronics
										</td>
										<td class="column7">
											<span onmouseover="popUp(event,'userRatings')"
												onmouseout="popUp(event,'userRatings')" class="RATE"><img
													border="0"
													src="${staticContextPath}/images/common/full_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/full_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/half_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/empty_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/empty_star_gbg.gif" />
											</span>
										</td>
									</tr>
									<tr>
										<td class="column1">
											<a href=""><img alt="Expand"
													src="${staticContextPath}/images/grid/right-arrow.gif" /> </a>
										</td>
										<td class="column2">
											<a href="">123-456-789-000 </a>
										</td>
										<td class="column3">
											<a href="">123456 </a>
										</td>
										<td class="column4">
											<a href="">234567 </a>
										</td>
										<td class="column5">
											<a href="">225874 </a>
										</td>
										<td class="column6">
											Consumer Electronics
										</td>
										<td class="column7">
											<span onmouseover="popUp(event,'userRatings')"
												onmouseout="popUp(event,'userRatings')" class="RATE"><img
													border="0"
													src="${staticContextPath}/images/common/full_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/full_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/half_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/empty_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/empty_star_gbg.gif" />
											</span>
										</td>
									</tr>
									<tr class="selected">
										<td class="column1">
											<a href=""><img alt="Collapse"
													src="${staticContextPath}/images/grid/down-arrow.gif" /> </a>
										</td>
										<td class="column2">
											<a href="">123-456-789-000 </a>
										</td>
										<td class="column3">
											<a href="">123456 </a>
										</td>
										<td class="column4">
											<a href="">234567 </a>
										</td>
										<td class="column5">
											<a href="">225874 </a>
										</td>
										<td class="column6">
											Consumer Electronics
										</td>
										<td class="column7">
											<span onmouseover="popUp(event,'userRatings')"
												onmouseout="popUp(event,'userRatings')" class="RATE"><img
													border="0"
													src="${staticContextPath}/images/common/full_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/full_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/half_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/empty_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/empty_star_gbg.gif" />
											</span>
										</td>
									</tr>
									<tr class="selected">
										<td colspan="7" class="last">
											<div class="expandedRatings">
												<h4>
													Service Buyer Ratings
												</h4>
												<table cellpadding="0" cellspacing="0">
													<tr>
														<td class="column1">
															<p>
																Quality
															</p>
															<p>
																Professionalism
															</p>
															<p>
																Timeliness
															</p>
															<p>
																Communication
															</p>
															<p>
																Value
															</p>
														</td>
														<td class="column2">
															<p>
																<span onmouseover="popUp(event,'userRatings')"
																	onmouseout="popUp(event,'userRatings')" class="RATE"><img
																		border="0"
																		src="${staticContextPath}/images/common/full_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/full_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/half_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/empty_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/empty_star_gbg.gif" />
																</span>
															</p>
															<p>
																<span onmouseover="popUp(event,'userRatings')"
																	onmouseout="popUp(event,'userRatings')" class="RATE"><img
																		border="0"
																		src="${staticContextPath}/images/common/full_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/full_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/half_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/empty_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/empty_star_gbg.gif" />
																</span>
															</p>
															<p>
																<span onmouseover="popUp(event,'userRatings')"
																	onmouseout="popUp(event,'userRatings')" class="RATE"><img
																		border="0"
																		src="${staticContextPath}/images/common/full_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/full_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/half_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/empty_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/empty_star_gbg.gif" />
																</span>
															</p>
															<p>
																<span onmouseover="popUp(event,'userRatings')"
																	onmouseout="popUp(event,'userRatings')" class="RATE"><img
																		border="0"
																		src="${staticContextPath}/images/common/full_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/full_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/half_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/empty_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/empty_star_gbg.gif" />
																</span>
															</p>
															<p>
																<span onmouseover="popUp(event,'userRatings')"
																	onmouseout="popUp(event,'userRatings')" class="RATE"><img
																		border="0"
																		src="${staticContextPath}/images/common/full_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/full_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/half_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/empty_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/empty_star_gbg.gif" />
																</span>
															</p>
														</td>
														<td class="column3">
															<p>
																<strong>Additional Comments</strong>
																<br />
																<textarea onfocus="clearTextbox(this)"
																	class="shadowBox lockedField"
																	style="width: 300px; height: 85px;">Quibus dignissim occuro tincidunt saluto antehabeo quia iriure eum at persto. Quibus
dignissim occuro tincidunt saluto antehabeo quia iriure eum at persto. Quibus dignissim
occuro tincidunt saluto antehabeo quia iriure eum at persto.
           </textarea>
															</p>
														</td>
													</tr>
												</table>
											</div>
											<div class="expandedRatings">
												<h4>
													Service Provider Ratings
												</h4>
												<table cellpadding="0" cellspacing="0">
													<tr>
														<td class="column1">
															<p>
																Quality
															</p>
															<p>
																Professionalism
															</p>
															<p>
																Timeliness
															</p>
															<p>
																Communication
															</p>
															<p>
																Value
															</p>
														</td>
														<td class="column2">
															<p>
																<span onmouseover="popUp(event,'userRatings')"
																	onmouseout="popUp(event,'userRatings')" class="RATE"><img
																		border="0"
																		src="${staticContextPath}/images/common/full_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/full_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/half_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/empty_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/empty_star_gbg.gif" />
																</span>
															</p>
															<p>
																<span onmouseover="popUp(event,'userRatings')"
																	onmouseout="popUp(event,'userRatings')" class="RATE"><img
																		border="0"
																		src="${staticContextPath}/images/common/full_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/full_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/half_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/empty_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/empty_star_gbg.gif" />
																</span>
															</p>
															<p>
																<span onmouseover="popUp(event,'userRatings')"
																	onmouseout="popUp(event,'userRatings')" class="RATE"><img
																		border="0"
																		src="${staticContextPath}/images/common/full_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/full_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/half_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/empty_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/empty_star_gbg.gif" />
																</span>
															</p>
															<p>
																<span onmouseover="popUp(event,'userRatings')"
																	onmouseout="popUp(event,'userRatings')" class="RATE"><img
																		border="0"
																		src="${staticContextPath}/images/common/full_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/full_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/half_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/empty_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/empty_star_gbg.gif" />
																</span>
															</p>
															<p>
																<span onmouseover="popUp(event,'userRatings')"
																	onmouseout="popUp(event,'userRatings')" class="RATE"><img
																		border="0"
																		src="${staticContextPath}/images/common/full_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/full_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/half_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/empty_star_gbg.gif" />
																	<img border="0"
																		src="${staticContextPath}/images/common/empty_star_gbg.gif" />
																</span>
															</p>
														</td>
														<td class="column3">
															<p>
																<strong>Additional Comments</strong>
																<br />
																<textarea onfocus="clearTextbox(this)"
																	class="shadowBox lockedField"
																	style="width: 300px; height: 85px;">Quibus dignissim occuro tincidunt saluto antehabeo quia iriure eum at persto. Quibus
dignissim occuro tincidunt saluto antehabeo quia iriure eum at persto. Quibus dignissim
occuro tincidunt saluto antehabeo quia iriure eum at persto.
           </textarea>
															</p>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
									<tr>
										<td class="column1">
											<a href=""><img alt="Expand"
													src="${staticContextPath}/images/grid/right-arrow.gif" /> </a>
										</td>
										<td class="column2">
											<a href="">123-456-789-000 </a>
										</td>
										<td class="column3">
											<a href="">123456 </a>
										</td>
										<td class="column4">
											<a href="">234567 </a>
										</td>
										<td class="column5">
											<a href="">225874 </a>
										</td>
										<td class="column6">
											Consumer Electronics
										</td>
										<td class="column7">
											<span onmouseover="popUp(event,'userRatings')"
												onmouseout="popUp(event,'userRatings')" class="RATE"><img
													border="0"
													src="${staticContextPath}/images/common/full_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/full_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/half_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/empty_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/empty_star_gbg.gif" />
											</span>
										</td>
									</tr>
									<tr>
										<td class="column1">
											<a href=""><img alt="Expand"
													src="${staticContextPath}/images/grid/right-arrow.gif" /> </a>
										</td>
										<td class="column2">
											<a href="">123-456-789-000 </a>
										</td>
										<td class="column3">
											<a href="">123456 </a>
										</td>
										<td class="column4">
											<a href="">234567 </a>
										</td>
										<td class="column5">
											<a href="">225874 </a>
										</td>
										<td class="column6">
											Consumer Electronics
										</td>
										<td class="column7">
											<span onmouseover="popUp(event,'userRatings')"
												onmouseout="popUp(event,'userRatings')" class="RATE"><img
													border="0"
													src="${staticContextPath}/images/common/full_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/full_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/half_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/empty_star_gbg.gif" />
												<img border="0"
													src="${staticContextPath}/images/common/empty_star_gbg.gif" />
											</span>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<!-- START FOOTER -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER -->
		</div>
	</body>
</html>
