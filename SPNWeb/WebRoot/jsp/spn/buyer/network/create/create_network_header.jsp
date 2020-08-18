<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	jQuery(document).ready(function($) {
		$('#spnHeader-spnDescription').maxlength({ maxCharacters: 255 });
		$('#spnHeader-spnInstruction').maxlength({ maxCharacters: 255 });
	});
	
</script>

								<p>
									<label>
										Name of Network
										<span class="req">*</span>
									</label>
									<s:textfield id="spnHeader.spnName" name="spnHeader.spnName"
										value="%{spnHeader.spnName}" theme="simple" cssClass="text" maxlength="150" />
										<s:hidden name="spnHeader.spnId" id="spnHeader.spnId" value="%{spnHeader.spnId}" />
										<s:hidden name="buyerId" id="buyerId" value="%{buyerId}" />
								</p>

								<div class="clearfix">
									<div>
										<label>
											Contact Name
											<span class="req">*</span>
										</label>
										<s:textfield id="spnHeader.contactName"
											name="spnHeader.contactName" value="%{spnHeader.contactName}"
											theme="simple" cssClass="text" maxlength="50" />
									</div>
								</div>
								
								<div class="clearfix">
									<div class="half">
										<label>
											Contact Email
											<span class="req">*</span>
										</label>
										<s:textfield id="spnHeader.contactEmail"
											name="spnHeader.contactEmail"
											value="%{spnHeader.contactEmail}" theme="simple"
											cssClass="text"  maxlength="255" />
									</div>
									<div class="half">
										<label>
											Contact Phone
											<span class="req">*</span>
										</label>
										<input type="text" id="spnHeader.contactPhone"
											name="spnHeader.contactPhone" value="${spnHeader.contactPhone}"
											class="text" onkeyup="maskPhone('spnHeader.contactPhone');" onchange="maskPhone('spnHeader.contactPhone');" />
									</div>
								</div>

								<div class="tipwrap">

									<p>
										<label>
											<abbr title="Select Provider Network">SPN</abbr> Description
											<span class="req">*</span>
										</label>
										<s:textarea id="spnHeader-spnDescription"
											name="spnHeader.spnDescription"
											value="%{spnHeader.spnDescription}" theme="simple"
											cssClass="description showtip text" />
									</p>

									<div class="formtip" id="spn-description">
										<strong>Please provide a brief description</strong> explaining
										the benefits of membership.
										<br />
										Summarize requirements and remind providers this is a limited
										enrollment opportunity. This information will appear on the
										provider invitation. In
										<strong>"Documents"</strong>, you can specify attachments for
										the invitation, with more information.
									</div>

								</div>

								<div class="tipwrap">
									<p>
										<label>
											Special Instructions
										</label>
										<s:textarea id="spnHeader-spnInstruction"
											name="spnHeader.spnInstruction"
											value="%{spnHeader.spnInstruction}" theme="simple"
											cssClass="description showtip text" />
									</p>
									<div class="formtip" id="spn-description">
										<strong>Use this space for specific instructions</strong> or requirements or to
										   point out informational documents you would like the applicant
										   to read before applying. This information will appear on the
										   invitation.
									</div>
								</div>