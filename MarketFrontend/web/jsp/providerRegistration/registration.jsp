<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive [Provider Registration]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit.Dialog");
			dojo.require("dojo.parser");
		dojo.require("dijit.layout.LinkPane");
			//dojo.require("newco.servicelive.SOMRealTimeManager");
			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
		</script>
		<style type="text/css">
@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css"
	;

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css"
	;
</style>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/slider.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"
			href=".${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/service_order_wizard.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />
		<script language="JavaScript"
			src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
	</head>
	<body class="tundra">
	    
		<div id="page_margins">
			<div id="page" class="clearfix">
				<!-- BEGIN HEADER -->
				<div id="headerSPReg" dojoType="dijit.layout.ContentPane"
					title="header" href="html_sections/header/reg_hdr_provider.jsp">
				</div>
				<!-- BEGIN RIGHT PANE -->
				<div class="colRight255 clearfix">
					Right column
				</div>
				<div class="colLeft711">
					<div class="content">
						<p class="noTopPad">
							Sign up for a provider account so you can accept work and provide
							service for members of the ServiceLive community. Enter your
							primary contact information and we'll email you a temporary
							password that you can use to build your profile on our secure
							server.
						</p>
						<div style="margin: 10px 0pt;" class="errorBox clearfix">
							<p class="errorMsg">
								<strong>Error: Please check the following and try
									again.</strong>
							</p>
							<ul>
								<li>
									Please fill in the Administrator's Last Name.
								</li>
								<li>
									Invalid E-mail Address. Please try again.
								</li>
								<li>
									That User Name is already taken. Please try again.
								</li>
							</ul>
						</div>
						<!-- NEW MODULE/ WIDGET-->
						<div class="grayModuleHdr">
							Business Information
						</div>
						<div class="grayModuleContent">
							<p>
								The fields below give you an opportunity to tell buyers a little
								more about your business. Your written description will be
								included as part of your public profile
							</p>
							<table cellpadding="0" cellspacing="0" width="650">
								<tr>
									<td width="325">
										<p>
											<label>
												Legal Business Name
											</label>
											<br />
											<input type="text" onfocus="clearTextbox(this)"
												value="[Business Name]" style="width: 250px;"
												class="shadowBox grayText" />
										</p>
										<p>
											<label>
												Main Business Phone
											</label>
											<br />
											<input type="text" class="shadowBox grayText"
												style="width: 30px;" value="###"
												onfocus="clearTextbox(this)" maxlength="3" />
											-
											<input type="text" class="shadowBox grayText"
												style="width: 30px;" value="###"
												onfocus="clearTextbox(this)" maxlength="3" />
											-
											<input type="text" class="shadowBox grayText"
												style="width: 45px;" value="####"
												onfocus="clearTextbox(this)" maxlength="4" />
											Ext.
											<input type="text" class="shadowBox grayText"
												style="width: 45px;" value="####"
												onfocus="clearTextbox(this)" maxlength="4" />
										</p>
										<p>
											<label>
												Primary Industry
											</label>
											<br />
											<select style="width: 256px;" class="grayText"
												onclick="changeDropdown(this)">
												<option>
													Select One
												</option>
											</select>
										</p>
									</td>
									<td width="325">
										<p>
											<label>
												Doing Business As (DBA)
											</label>
											<br />
											<input type="text" onfocus="clearTextbox(this)"
												value="[DBA Name]" style="width: 250px;"
												class="shadowBox grayText" />
											Optional
										</p>
										<p>
											<label>
												Business Fax
											</label>
											<br />
											<input type="text" class="shadowBox grayText"
												style="width: 30px;" value="###"
												onfocus="clearTextbox(this)" maxlength="3" />
											-
											<input type="text" class="shadowBox grayText"
												style="width: 30px;" value="###"
												onfocus="clearTextbox(this)" maxlength="3" />
											-
											<input type="text" class="shadowBox grayText"
												style="width: 45px;" value="####"
												onfocus="clearTextbox(this)" maxlength="4" />
											Optional
										</p>
										<p>
											<label>
												Website Address
											</label>
											<br />
											<input type="text" onfocus="clearTextbox(this)"
												value="http://www.webaddress.com" style="width: 250px;"
												class="shadowBox grayText" />
											Optional
										</p>
									</td>
								</tr>
							</table>
						</div>
						<!-- NEW MODULE/ WIDGET-->
						<div class="grayModuleHdr">
							Business Address
						</div>
						<div class="grayModuleContent">
							<table cellpadding="0" cellspacing="0" width="650">
								<tr>
									<td width="325">
										<p>
											<label>
												Street Name
											</label>
											<br />
											<input type="text" onfocus="clearTextbox(this)"
												value="[Address 1]" style="width: 250px;"
												class="shadowBox grayText" />
										</p>
										<p>
											<input type="text" onfocus="clearTextbox(this)"
												value="[Address 1]" style="width: 250px;"
												class="shadowBox grayText" />
										</p>
									</td>
									<td width="325">
										<p>
											<label>
												Apt. #
											</label>
											<br />
											<input type="text" onfocus="clearTextbox(this)" value="[Apt]"
												style="width: 100px;" class="shadowBox grayText" />
										</p>
										<p style="line-height: 14px;">
											&nbsp;
										</p>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<table cellpadding="0" cellspacing="0">
											<tr>
												<td width="205">
													<p>
														<label>
															City
														</label>
														<br />
														<input type="text" class="shadowBox grayText"
															style="width: 190px;" value="[City]"
															onfocus="clearTextbox(this)" />
													</p>
												</td>
												<td width="55">
													<p>
														<label>
															State
														</label>
														<br />
														<select style="width: 50px;" class="grayText"
															onclick="changeDropdown(this)">
															<option>
																AL
															</option>
														</select>
													</p>
												</td>
												<td width="130">
													<p>
														<label>
															ZIP
														</label>
														<br />
														<input type="text" onfocus="clearTextbox(this)"
															value="#####" style="width: 50px;"
															class="shadowBox grayText" />
													</p>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</div>
						<!-- NEW MODULE/ WIDGET-->
						<div class="grayModuleHdr">
							Mailing Address
						</div>
						<div class="grayModuleContent">
							<p>
								<input type="checkbox" />
								The mailing address is the same as the business address.
							</p>
							<table cellpadding="0" cellspacing="0" width="650">
								<tr>
									<td width="325">
										<p>
											<label>
												Street Name
											</label>
											<br />
											<input type="text" onfocus="clearTextbox(this)"
												value="[Address 1]" style="width: 250px;"
												class="shadowBox grayText" />
										</p>
										<p>
											<input type="text" onfocus="clearTextbox(this)"
												value="[Address 1]" style="width: 250px;"
												class="shadowBox grayText" />
										</p>
									</td>
									<td width="325">
										<p>
											<label>
												Apt. #
											</label>
											<br />
											<input type="text" onfocus="clearTextbox(this)" value="[Apt]"
												style="width: 100px;" class="shadowBox grayText" />
										</p>
										<p style="line-height: 14px;">
											&nbsp;
										</p>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<table cellpadding="0" cellspacing="0">
											<tr>
												<td width="205">
													<p>
														<label>
															City
														</label>
														<br />
														<input type="text" class="shadowBox grayText"
															style="width: 190px;" value="[City]"
															onfocus="clearTextbox(this)" />
													</p>
												</td>
												<td width="55">
													<p>
														<label>
															State
														</label>
														<br />
														<select style="width: 50px;" class="grayText"
															onclick="changeDropdown(this)">
															<option>
																AL
															</option>
														</select>
													</p>
												</td>
												<td width="130">
													<p>
														<label>
															ZIP
														</label>
														<br />
														<input type="text" onfocus="clearTextbox(this)"
															value="#####" style="width: 50px;"
															class="shadowBox grayText" />
													</p>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</div>
						<!-- NEW MODULE/ WIDGET-->
						<div class="grayModuleHdr">
							Primary Contact Information – Administrator
						</div>
						<div class="grayModuleContent">
							<p>
								By opening this account, you become the ServiceLive
								administrator for your company. You'll be the primary contact
								for all communications with ServiceLive. You will also be
								authorized to enroll additional service providers and support
								staff for your company.
							</p>
							<table cellpadding="0" cellspacing="0" width="650">
								<tr>
									<td width="325">
										<p>
											<label>
												Role within Company
											</label>
											<br />
											<select style="width: 256px;" class="grayText"
												onclick="changeDropdown(this)">
												<option>
													Select One
												</option>
											</select>
										</p>
									</td>
									<td width="325">
										<p>
											<label>
												Job Title
											</label>
											<br />
											<input type="text" onfocus="clearTextbox(this)"
												value="[Job Title]" style="width: 250px;"
												class="shadowBox grayText" />
											Optional
										</p>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<p>
											Will this person will perform service calls in the
											marketplace?
											<br>
											<span class="formFieldOffset"> <input type="radio"
													class="antiRadioOffsets" name="r2">
												Yes&nbsp;&nbsp;&nbsp; <input type="radio"
													class="antiRadioOffsets" name="r2" checked> No</span>
										</p>
									</td>
								</tr>
								<tr>
									<td>
										<p>
											<label>
												First Name
											</label>
											<br />
											<input type="text" onfocus="clearTextbox(this)"
												value="[First Name]" style="width: 250px;"
												class="shadowBox grayText" />
										</p>
										<div>
											<p class="errorBox">
												<label>
													Last Name
												</label>
												<br />
												<input type="text" onfocus="clearTextbox(this)"
													value="[Last Name]" style="width: 250px;"
													class="shadowBox grayText" />
												<br>
												<span class="errorMsg">Please fill in the
													Administrator's Last Name.</span>
											</p>
										</div>
										<p>
											<label>
												E-mail Address
											</label>
											<br />
											<input type="text" onfocus="clearTextbox(this)"
												value="[E-mail Address]" style="width: 250px;"
												class="shadowBox grayText" />
										</p>

										<p>
											<label class="error">
												Alternate E-mail Address
											</label>
											<br />
											<input type="text" onfocus="clearTextbox(this)"
												value="[Alternate E-mail Address]" style="width: 250px;"
												class="shadowBox grayText" />
											<br>
											<span class="errorMsg">Invalid E-mail Address. Please
												try again.</span>
										</p>
										<p>
											<label>
												User Name
											</label>
											<br />
											<input type="text" onfocus="clearTextbox(this)"
												value="[User Name]" style="width: 250px;"
												class="shadowBox grayText" />
										</p>
									</td>
									<td>
										<p>
											<label>
												Middle Name
											</label>
											<br />
											<input type="text" onfocus="clearTextbox(this)"
												value="[First Name]" style="width: 250px;"
												class="shadowBox grayText" />
											Optional
										</p>
										<p>
											<label>
												Suffix (Jr., II, etc.)
											</label>
											<br />
											<input type="text" onfocus="clearTextbox(this)" value="[Jr]"
												style="width: 100px;" class="shadowBox grayText" />
											Optional
										</p>
										<p>
											<label>
												Confirm E-mail Address
											</label>
											<br />
											<input type="text" onfocus="clearTextbox(this)"
												value="[E-mail Address]" style="width: 250px;"
												class="shadowBox grayText" />
										</p>
										<p>
											<label>
												Confirm Alternate E-mail Address
											</label>
											<br />
											<input type="text" onfocus="clearTextbox(this)"
												value="[Alternate E-mail Address]" style="width: 250px;"
												class="shadowBox grayText" />
										</p>
									</td>
								</tr>
								<tr>
									<td>
										<p>
											How did you hear about ServiceLive?
											<br>
											<select style="width: 256px;" class="grayText"
												onclick="changeDropdown(this)">
												<option>
													Select One
												</option>
											</select>
										</p>
									</td>
									<td>
										<p>
											<label>
												Promotional Code
											</label>
											<br />
											<input type="text" onfocus="clearTextbox(this)"
												value="[Promotional Code]" style="width: 250px;"
												class="shadowBox grayText" />
										</p>
									</td>
								</tr>
							</table>
						</div>
						<div class="grayModuleHdr">
							Site Terms & Conditions
						</div>
						<div class="grayModuleContent">
							<p class="paddingBtm">
								Please read the following terms and conditions. After accepting,
								you'll receive an e-mail confirming your account. Use the
								temporary password in that e-mail to log back onto the system so
								you can begin building your profile?
							</p>
							<div class="inputArea" style="height: 200px;">
								<h3 align="center">
									Site Terms & Conditions
								</h3>
								<p>
									Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam
									risus. Maecenas volutpat. Nam euismod lectus et sem. Morbi id
									nisl. Sed id felis nec eros accumsan feugiat. Cras imperdiet
									consequat velit.
								</p>
								<p>
									Sed suscipit, massa eu pretium dictum, risus tellus luctus
									ipsum, facilisis commodo turpis arcu eu pede. Nulla facilisi.
									In lectus mauris, lacinia quis, pellentesque ut, adipiscing a,
									lectus. Etiam a mi. Nullam lacus ante, tristique a, accumsan
									vitae, sodales nec, eros. Integer sit amet diam. Nam gravida
									semper nulla. Donec suscipit magna vitae est. Nulla pulvinar
									felis a erat. Etiam ut massa.
								</p>
								<p>
									Sed suscipit, massa eu pretium dictum, risus tellus luctus
									ipsum, facilisis commodo turpis arcu eu pede. Nulla facilisi.
									In lectus mauris, lacinia quis, pellentesque ut, adipiscing a,
									lectus. Etiam a mi. Nullam lacus ante, tristique a, accumsan
									vitae, sodales nec, eros. Integer sit amet diam. Nam gravida
									semper nulla. Donec suscipit magna vitae est. Nulla pulvinar
									felis a erat. Etiam ut massa.
								</p>
								<p>
									Sed suscipit, massa eu pretium dictum, risus tellus luctus
									ipsum, facilisis commodo turpis arcu eu pede. Nulla facilisi.
									In lectus mauris, lacinia quis, pellentesque ut, adipiscing a,
									lectus. Etiam a mi. Nullam lacus ante, tristique a, accumsan
									vitae, sodales nec, eros. Integer sit amet diam. Nam gravida
									semper nulla. Donec suscipit magna vitae est. Nulla pulvinar
									felis a erat. Etiam ut massa.
								</p>
								<p>
									Sed suscipit, massa eu pretium dictum, risus tellus luctus
									ipsum, facilisis commodo turpis arcu eu pede. Nulla facilisi.
									In lectus mauris, lacinia quis, pellentesque ut, adipiscing a,
									lectus. Etiam a mi. Nullam lacus ante, tristique a, accumsan
									vitae, sodales nec, eros. Integer sit amet diam. Nam gravida
									semper nulla. Donec suscipit magna vitae est. Nulla pulvinar
									felis a erat. Etiam ut massa.
								</p>
							</div>
							<p>
								<input type="radio" class="antiRadioOffsets" name="r1">
								I accept the Terms & Conditions.
							</p>
							<p>
								<input type="radio" class="antiRadioOffsets" name="r1"
									checked="checked">
								I do not accept the Terms & Conditions.
							</p>
						</div>

						<div class="clearfix">
							<div class="formNavButtons2">
								<input type="image"
									src="${staticContextPath}/images/images_registration/common/spacer.gif"
									width="127" height="20"
									style="background-image: url(${staticContextPath}/images/images_registration/btn/submitReg_whiteBg.gif);"
									class="btn20Bevel" />

							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="clear"></div>
			<!-- START FOOTER -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER -->
		</div>
	</body>
</html>
