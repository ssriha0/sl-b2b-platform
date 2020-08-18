<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="darkGrayModuleHdr">
	Primary Contact Information &ndash; Provider Administrator
</div>

<div class="grayModuleContent mainWellContent clearfix">

	<div>
		<table cellpadding="0" cellspacing="0" style="">
			<tr>
				<td width="340">
					<p>
						<b>Role within Company</b>
						<br />
						<s:property value="companyProfileDto.role" />
					</p>
				</td>
				<td width="339">
					<p>
						<b>Job Title</b>
						<br />
						<s:property value="companyProfileDto.title" />
					</p>
				</td>
			</tr>
			<tr>
				<td width="340">
					<p>
						<b>First Name</b>
						<br />
						<s:property value="companyProfileDto.firstName" />
					</p>
				</td>
				<td width="339">
					<p>
						<b>Middle Name</b>
						<br />
						<s:property value="companyProfileDto.mi" />
					</p>
				</td>
			</tr>
			<tr>
				<td width="340">
					<p>
						<b>Last Name</b>
						<br />
						<s:property value="companyProfileDto.lastName" />
					</p>
				</td>
				<td width="339">
					<p>
						<b>Suffix (Jr., II, etc.)</b>
						<br />
						<s:property value="companyProfileDto.suffix" />
					</p>
				</td>
			</tr>
			<tr>
				<td width="340">
					<p>
						<b>Email</b>
						<br />
						<s:property value="companyProfileDto.email" />
					</p>
				</td>
				<td width="339">
					<p>
						<b>Alternate E-mail</b>
						<br />
						<s:property value="companyProfileDto.altEmail" />
					</p>
				</td>

			</tr>

		</table>
	</div>

</div>
