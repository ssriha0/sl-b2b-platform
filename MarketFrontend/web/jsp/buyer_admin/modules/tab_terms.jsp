<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="BuyerAdmin.userProfile.termsAndCond"/>
		</jsp:include>	


<div class="darkGrayModuleHdr">
	Buyer Terms & Conditions
</div>
<div class="grayModuleContent mainWellContent">
	<p class="paddingBtm">
		Terms & Conditions You must agree to the following terms and
		conditions in order to complete your registration. Service buyers who
		fail to abide by these terms and conditions will be removed from the
		ServiceLive network.
	</p>
	<div class="inputArea" style="height: 200px;">
		<h3 align="center">
			Buyer Terms & Conditions
		</h3>
		<p>
			Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam
			risus. Maecenas volutpat. Nam euismod lectus et sem. Morbi id nisl.
			Sed id felis nec eros accumsan feugiat. Cras imperdiet consequat
			velit.
		</p>
		<p>
			Sed suscipit, massa eu pretium dictum, risus tellus luctus ipsum,
			facilisis commodo turpis arcu eu pede. Nulla facilisi. In lectus
			mauris, lacinia quis, pellentesque ut, adipiscing a, lectus. Etiam a
			mi. Nullam lacus ante, tristique a, accumsan vitae, sodales nec,
			eros. Integer sit amet diam. Nam gravida semper nulla. Donec suscipit
			magna vitae est. Nulla pulvinar felis a erat. Etiam ut massa.
		</p>
		<p>
			Sed suscipit, massa eu pretium dictum, risus tellus luctus ipsum,
			facilisis commodo turpis arcu eu pede. Nulla facilisi. In lectus
			mauris, lacinia quis, pellentesque ut, adipiscing a, lectus. Etiam a
			mi. Nullam lacus ante, tristique a, accumsan vitae, sodales nec,
			eros. Integer sit amet diam. Nam gravida semper nulla. Donec suscipit
			magna vitae est. Nulla pulvinar felis a erat. Etiam ut massa.
		</p>
		<p>
			Sed suscipit, massa eu pretium dictum, risus tellus luctus ipsum,
			facilisis commodo turpis arcu eu pede. Nulla facilisi. In lectus
			mauris, lacinia quis, pellentesque ut, adipiscing a, lectus. Etiam a
			mi. Nullam lacus ante, tristique a, accumsan vitae, sodales nec,
			eros. Integer sit amet diam. Nam gravida semper nulla. Donec suscipit
			magna vitae est. Nulla pulvinar felis a erat. Etiam ut massa.
		</p>
		<p>
			Sed suscipit, massa eu pretium dictum, risus tellus luctus ipsum,
			facilisis commodo turpis arcu eu pede. Nulla facilisi. In lectus
			mauris, lacinia quis, pellentesque ut, adipiscing a, lectus. Etiam a
			mi. Nullam lacus ante, tristique a, accumsan vitae, sodales nec,
			eros. Integer sit amet diam. Nam gravida semper nulla. Donec suscipit
			magna vitae est. Nulla pulvinar felis a erat. Etiam ut massa.
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
	<div class="formNavButtons">
		<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72"
			height="20"
			style="background-image: url(${staticContextPath}/images/btn/previous.gif);"
			class="btn20Bevel" />
		<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="49"
			height="20" style="background-image: url(${staticContextPath}/images/btn/save.gif);"
			class="btn20Bevel" />
	</div>
	<div class="bottomRightLink">
		<a href="">Cancel</a>
	</div>
</div>
