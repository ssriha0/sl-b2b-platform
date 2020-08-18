<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
	<c:set var="staticContextPath" scope="request" value="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<html>
	<head>
		<title>ServiceLive - Select Provider Network - Member Management</title>
		
		
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/screen.css" media="screen, projection">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/superfish.css" media="screen, projection">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/modals.css" media="screen, projection">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/select-provider-network.css" media="screen, projection" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/jqueryui/jquery-ui-1.7.2.custom.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/ui.datepicker.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/modals.css" media="screen, projection" />
		<link rel="stylesheet" href="${staticContextPath}/css/memberManagement.css" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/jqueryui/jquery-ui-1.7.2.custom.css" />
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqueryui/jquery-ui-1.10.4.custom.min.js"></script>
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>	
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/jquery.tablesorter.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/memberManagementDriver.js"></script>

		<style type="text/css">
	      .ie7 .bannerDiv{margin-left:-1150px;}
	      .ie9 .bannerDiv{margin-left:-210px;}
	      .ff2 .bannerDiv[style]{margin-left:-208px;}
	 	</style>
		<script type="text/javascript">
			
                $(document).ready(function(){
                //added for preventing charcters other than numeric and semicolon for numeric search
                 $(".keyPressCheck").keypress(function(event) {
                	 if($('#spnMMSearchBy > option:selected').val() ==-1){
        			     return false;
        		     }
                	 else if ($('#spnMMSearchBy > option:selected').val() == 2 || $('#spnMMSearchBy > option:selected').val() == 4){
                  // Backspace, tab, enter, end, home, left, right
                  // We don't support the del key in Opera because del == . == 46.
                  var controlKeys = [8, 9, 13, 35, 36, 37, 59];
                  // IE doesn't support indexOf
                  var isControlKey = controlKeys.join(",").match(new RegExp(event.which));
                 if(event.shiftKey)
                    return false;
                  // Some browsers just don't raise events for control keys. Easy.
                  // e.g. Safari backspace.
                  if (!event.which || // Control keys in most browsers. e.g. Firefox tab is 0
                      (49 <= event.which && event.which <= 57) || // Always 1 through 9
                      (48 == event.which && $(this).val()) || // No 0 first digit
                      isControlKey) { // Opera assigns values for control keys.
                    return;
                  } else {
                    event.preventDefault();
                  }
                }else if($('#spnMMSearchBy > option:selected').val() == 1 || $('#spnMMSearchBy > option:selected').val() == 3){
                   if(event.which==37){
                     event.preventDefault();
                    }
                }
                else{
                	return;
                }
                });
                });
               
                </script>
	</head>
	<body id="select-provider-network">
		<div id="wrap" class="container">
			<jsp:include page="/jsp/spn/common/defaultheadernew.jsp" />
			<div id="content" class="span-24 clearfix">
				<div id="primary" class="span-24 first last">
					<div class="content">
						<h2 id="page-role">Administrator Office</h2>
						<h2 id="page-title">Select Provider Network (SPN)</h2>
						<h3 class="page-header">Member Management</h3>

						<div id="tabs">
							<c:import url="/jsp/spn/common_tabs.jsp">
								<c:param name="tabName" value="spnMemberManager" />
							</c:import>
						</div>	
							<div id="tab-content" class="clearfix">
								<div id="tab-content" class="clearfix">
									<h4>Network Status & Levels</h4>
									<h5>
										Search for Provider Firms or Providers to view or manage network
										profile information at the company or provider level.
									</h5>
									<input type="hidden" id="searchByViewAll" value="false" />
									<div id="spnMMSearchValidationError" class="error errorMsg errorMessage">
										<p class="errorText"></p>
									</div>
								</div>		
								<div id="spnMMSearchForm" class="clearfix">
									<label for="spnMMSearchBy">
										Search by
									</label>
									<select id="spnMMSearchBy">
										<option value="-1">
											- Select One -
										</option>
										<option value="1">
											Provider Name
										</option>
										<option value="2">
											Provider ID
										</option>
										<option value="3">
											Firm Name
										</option>
										<option value="4">
											Firm ID
										</option>
									</select>
									<fieldset id="spnMMSearchInput">
										<input type="text" id="spnMMSearchText"  class="keyPressCheck" />
										<label>
											Enter
											<span>Provider Name</span>
										</label>
									</fieldset>
									<input id="searchSubmit-1" type="submit" value="FIND" class="action" />
									<br />
								</div>
								<div id="searchresults"></div>
							</div>
						
					</div>
				</div>
			</div>
			<jsp:include page="/jsp/spn/common/defaultfootertmp.jsp" />
		</div>
		<div id="loadSPNSpinner" class="jqmWindow">
			<div class="modal-content">
				<label>
					<span>Gathering search results, please wait...</span>
				</label>
				<div>
					<img src="${staticContextPath}/images/simple/searchloading.gif" />
				</div>
				<div class="clearfix">
					<a class="cancel jqmClose left" href="#">Cancel</a>
				</div>
			</div>
		</div>
	</body>
</html>