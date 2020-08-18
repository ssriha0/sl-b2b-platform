<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="darkGrayModuleHdr">
	Warranty Information
</div>


<div class="grayModuleContent mainWellContent">

	<s:if test="%{companyProfileDto.freeEstimate==1}">
		<p>
			<label>
				<b>Do you charge for project estimates?</b>
			</label>
			<br />
			Yes
		</p>
	</s:if>

	<s:if test="%{companyProfileDto.warrOfferedLabor==1}">
		<p>
			<label>
				<b>Do you offer a warranty on labor?</b>
			</label>
			<br />
			Yes -
			<s:property value="companyProfileDto.warrPeriodLabor" />
		</p>
	</s:if>

	<s:if test="%{companyProfileDto.warrOfferedParts==1}">
		<p>
			<label>
				<b>Do you offer a warranty on parts?</b>
			</label>
			<br />
			Yes -
			<s:property value="companyProfileDto.warrPeriodParts" />
		</p>
	</s:if>

</div>
