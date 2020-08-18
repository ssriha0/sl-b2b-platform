<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/jquery-ajaxfileupload.js"></script>

<script type="text/javascript"> 
	function ajaxFileUpload()
	{
		$.ajaxFileUpload
		(
			{
				url:'spnCreateNetworkUploadDocument_uploadDocumentAjax.action',
				secureuri:false,
				fileElementId:'photoDoc',
				dataType: 'json',
				beforeSend:function()
				{
					$("#loading").show();
				},
				complete:function()
				{
					$("#loading").hide();
				},				
				success: function (data, status)
				{
					if(typeof(data.error) != 'undefined')
					{
						if(data.error != '')
						{
							alert(data.error);
						}else
						{
							alert(data.msg);
						}
					}
				},
				error: function (data, status, e)
				{
					alert(e);
				}
			}
		)
		
		return false;
 
	}
	</script> 
	
	<div id="loading" style="display: none;">I am loading</div>

<s:file name="photoDoc" id="photoDoc" />
<button class="button" id="buttonUpload" onclick="return ajaxFileUpload();">Upload</button>

<s:form action="spnCreateNetworkUploadDocument_uploadDocumentAjax"
	id="spnCreateNetworkUploadDocument_uploadDocumentAjax" name="spnCreateNetworkUploadDocument_uploadDocumentAjax"
	method="post" enctype="multipart/form-data" theme="simple">
</s:form>