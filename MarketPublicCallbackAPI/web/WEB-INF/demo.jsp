<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="staticContextPath" scope="request"
	value='<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>' />
<html>
	<head>
		<meta content="text/html; charset=ISO-8859-1"
			http-equiv="content-type">
		<title>Test Public APIs</title>
		<style type="text/css" media="screen">
<!--
body {
	padding: 0;
	margin: 0;
	background-color: #666;
	color: #000;
	text-align: left;
}

#contents {
	margin-top: 10px;
	margin-bottom: 10px;
	margin-right: auto;
	margin-left: auto;
	width: 600px;
	padding: 10px;
	background-color: #FFF;
	color: #000;
	text-align: left;
}

h1 {
	color: #333;
	background-color: transparent;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 20px;
}

p {
	color: #333;
	background-color: transparent;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 0.8em;
}

.code {
	color: #339;
	background-color: transparent;
	font-family: times, serif;
	font-size: 0.9em;
	padding-left: 40px;
}
-->
</style>
		<script src="http://code.jquery.com/jquery-latest.js"
			type="text/javascript"></script>
		<link rel="stylesheet"
			href="http://dev.jquery.com/view/trunk/plugins/autocomplete/demo/main.css"
			type="text/css" />
		<link rel="stylesheet"
			href="http://dev.jquery.com/view/trunk/plugins/autocomplete/jquery.autocomplete.css"
			type="text/css" />
		<script type="text/javascript"
			src="http://dev.jquery.com/view/trunk/plugins/autocomplete/lib/jquery.bgiframe.min.js"></script>
		<script type="text/javascript"
			src="http://dev.jquery.com/view/trunk/plugins/autocomplete/lib/jquery.dimensions.js"></script>
		<script type="text/javascript"
			src="http://dev.jquery.com/view/trunk/plugins/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="/jquery.autocomplete.js"></script>
		<script type="text/javascript">
  function autocomplete() {
   	var xmlURL = "http://172.22.16.61:8080/ServiceLiveWebUtil/images/data.xml";
      $(document).ready(function()
      {
             $.ajax({
                     type: "GET",
                     url: xmlURL,
                     dataType: "xml",
                     success: parseXml
              });
      });
}

 

function parseXml(xml)
{
      var data ='';
      $(xml).find("row").each(function()
      {
           var field = $(this).find("field").text();
           data = data + field + ',';
      });

      var lastcomma = data.substring(0, data.length-1);
      splitdata = lastcomma.split(",");
      $("#keyword1").autocomplete(splitdata,{
                    minChars:2,
                    matchSubset:1,
                    matchContains:1,
                    cacheLength:1,
                    delay: 400,             
                    selectOnly:false,
                    selectFirst:false,
                    autoFill:false

      });         

}
  </script>

		<script type="text/javascript">
   
  //var path = '<c:out value="${staticContextPath}"/>';   
  autocomplete();
  

  </script>


	</head>

	<body style="background-color: rgb(55, 55, 55);">
		<center>

			<br>
			<table style="text-align: center; background-color: white;"
				border="0" cellpadding="0" cellspacing="0">
				<tbody>
					<tr style="color: rgb(51, 102, 255);">
						<td>
							<dl>
								<dd>
									<big><big><big> <span
												style="font-weight: bold;">Public API Simulator</span>
										</big>
									</big>
									</big>
									<input type="hidden" id="srchType" name="srchType"
										value='<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/public"%>' />
								</dd>
							</dl>
						</td>
					</tr>
					<tr>
						<td>


							<table style="background-color: lightblue;" border="1">
								<!-- zip Table -->
								<tr>
									<td>
										<small><i><b>Search By Zip</b>
										</i>
										</small>
										<form name="myform" method="get"
											action="/public/v1.1/providers/zip">
											<table
												style="text-align: left; margin-left: auto; margin-right: auto; width: 550px;"
												border="0" cellpadding="1" cellspacing="1">
												<tbody>
													<tr>
														<td>
															ZIP
														</td>
														<td>
															<input name="zip" size="6" value="95050">
														</td>
														<td>
															Max Distance
														</td>
														<td>
															<input name="maxdistance" size="4" value="10">
														</td>
													</tr>
													<tr>
														<td>
															ID(s)
														</td>
														<td>
															<input name="favoriteList" size="60">
														</td>
													</tr>
													<tr>
														<td>
															pageSize
														</td>
														<td>
															<select name="PageSize">
																<option value="25">
																	25
																</option>
																<option value="50">
																	50
																</option>
																<option value="100">
																	100
																</option>
																<option value="200">
																	200
																</option>
															</select>
														</td>
														<td>
															PageNumber
														</td>
														<td>
															<input name="pageNumber" size="4" value="1">
														</td>
													</tr>
													<tr>
														<td>
															Language
														</td>
														<td>
															<select name="language" style="width: 150px">
																<option value="English">
																	English
																</option>
																<option value="Spenish">
																	Spanish
																</option>
																<option value="French">
																	French
																</option>
																<option value="Hindi">
																	Hindi
																</option>
																<option value="Russian">
																	Russian
																</option>
																<option value="Italian">
																	Italian
																</option>
															</select>
														</td>
														<td>
															Category Id
														</td>
														<td>
															<input name="categoryid" size="4">
														</td>
													</tr>

													<tr>
														<td>
															Service Type
														</td>
														<td>
															<input name="serviceType" size="23">
														</td>
														<td>
															Result Mode
														</td>
														<td>
															<select name="resultMode" style="width: 80px">
																<option value="Minimum">
																	Minimum
																</option>
																<option value="Medium">
																	Medium
																</option>
																<option value="Large">
																	Large
																</option>
																<option value="All">
																	All
																</option>
															</select>
														</td>
													</tr>
												</tbody>
											</table>
										</form>
									</td>
								</tr>

								<tr align="center">
									<td>
										<button type="submit" value="sendMsg" name="button">
											Search By Zip
										</button>
									</td>
								</tr>
								<form action=""></form>
							</table>
							<!-- zip Table End -->

						</td>
					</tr>
					<tr>
						<td>
							<table style="background-color: lightgrey;" border="1">
								<!-- Id Table -->
								<tr>
									<td>
										<small><i><b>Search By IDs</b>
										</i>
										</small>
										<form name="myform" method="get"
											action="/public/v1.1/providers/">
											<table
												style="text-align: left; margin-left: auto; margin-right: auto; width: 550px;"
												border="0" cellpadding="1" cellspacing="1">
												<tbody>
													<tr>
														<td>
															ID(s)
														</td>
														<td>
															<input name="idlist" size="60">
														</td>
													</tr>
													<tr>
														<td>
															ZIP
														</td>
														<td>
															<input name="zip" size="6" value="95050">
														</td>
													</tr>
												</tbody>
											</table>
										</form>
									</td>
								</tr>

								<tr align="center">
									<td>
										<button type="submit" value="sendMsg" name="button">
											Search By IDs
										</button>
									</td>
								</tr>
								<form action=""></form>
							</table>
							<!-- id Table End -->

						</td>
					</tr>


					<br>
					<td></td>
					<tr></tr>
					<tr>
						<td>
							<table style="background-color: lightblue;" border="1">
								<!-- Id Table -->
								<tr>
									<td>
										<small><i><b>Search By Keyword</b>
										</i>
										</small>
										<form name="myform" method="get"
											action="/public/v1.1/marketplace/categories">
											<table
												style="text-align: left; margin-left: auto; margin-right: auto; width: 550px;"
												border="0" cellpadding="1" cellspacing="1">
												<tbody>
													<td>
														Keyword
													</td>
													<td>
														<input id="keyword1" name="keyword" size="56">
													</td>
												</tbody>
											</table>
										</form>
									</td>
								</tr>

								<tr align="center">
									<td>
										<button type="submit" value="sendMsg" name="button">
											Search By keyword
										</button>
									</td>
								</tr>
								<form action=""></form>
							</table>
							<!-- id Table End -->

						</td>
					</tr>

				</tbody>
			</table>

		</center>
	</body>
</html>