<%@ page import="com.newco.marketplace.util.PropertiesUtils" %>
<%@ page import="com.newco.marketplace.constants.Constants" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="org.owasp.esapi.ESAPI"%>
<%
	String omnitureURL = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.OMNITURE_URL);
	
	String pageNameVar=request.getParameter("PageName");
	 String pageNameNew=ESAPI.encoder().canonicalize(pageNameVar);
	 String pageName=ESAPI.encoder().encodeForHTML(pageNameNew);	
				
 	String eventNameVar=request.getParameter("eventName");
 	 String eventNameNew=ESAPI.encoder().canonicalize(eventNameVar);
	 String eventName=ESAPI.encoder().encodeForHTML(eventNameNew);	

%>
<%
	if (omnitureURL != null && !omnitureURL.trim().equals("") && omnitureURL.trim().length() > 0) {
		java.util.List list = new java.util.ArrayList();
		list.add("HomePage");
		list.add("JoinNow.simple");
		list.add("JoinNow.simple.registrationStarted");
		list.add("JoinNow.simple.registrationComplete");
		list.add("JoinNow.simple.registrationCompleteNew");
		list.add("JoinNow.simple.createNewPassword");
		list.add("simple.findProviders");
		list.add("SSO - Select Location");
		list.add("SSO - Describe and Schedule");
		list.add("SSO - Review");
		list.add("SSO - Add Funds");
		list.add("home.orderPosted");
		list.add("AboutUs");
		list.add("AboutSP");
		list.add("JoinNow.provider.landing");
		list.add("ProRegStart");
		list.add("ProRegCongrats");
		list.add("ProDashboard");
		list.add("ProFirmProfile");
		list.add("AddPro");
		list.add("ProRegCongrats");
		list.add("TermsOfUse");
		list.add("PrivacyPolicy");
		list.add("CaPrivacyPolicy");
		list.add("TermsBuyerAgreement");
		list.add("TermsProviderAgreement");
		list.add("JoinNow.CommercialBuyer");
		list.add("simple.dashboard");
		list.add("common.dashboard");
		list.add("auditor.workflow");
		list.add("search.portal");
		list.add("forgot.userId.list");
		list.add("forgot.userId");
		list.add("forgot.userId.question");
		list.add("login.reset.password.success");
		list.add("login.reset.username.success");
		list.add("provider.hint");
		list.add("provider.new.password");
		list.add("reset.password.email");
		list.add("accountLocked");
		list.add("login");
		list.add("verification.zip.phone");
		list.add("FAQ");
		list.add("Tutorial");
		
		if (list.contains(pageName))
		{
%>
<script language="JavaScript"><!--
var s_account="<%=omnitureURL%>";

//--></script>
<script language="JavaScript" src="${staticContextPath}/javascript/s_code.js"></script>
<script language="JavaScript"><!--
/* You may give each page an identifying name, server, and channel on
the next lines. */
s.pageName="<%=pageName%>";
<%if (eventName != null && !eventName.trim().equals("") && eventName.trim().length() > 0) { %>s.events="<%=eventName%>";<%}%>
/************* DO NOT ALTER ANYTHING BELOW THIS LINE ! **************/
var s_code=s.t();if(s_code)document.write(s_code)//--></script>
<script language="JavaScript"><!--
if(navigator.appVersion.indexOf('MSIE')>=0)document.write(unescape('%3C')+'\!-'+'-')
//--></script><!--/DO NOT REMOVE/-->
<!-- End SiteCatalyst code version: H.14. -->

<script language="JavaScript">

function clickRequestOmniture(pagename)
{
	s.pageName=pagename;
	var tmp_s_code=s.t();
	if(tmp_s_code) 
	{
		document.write(tmp_s_code);
	}
}
</script>

<%
	}//END OF IF (0)
}//END OF MAIN IF
%>
 
