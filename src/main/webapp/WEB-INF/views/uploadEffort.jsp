<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<% 
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
 	response.setHeader("Pragma","no-cache"); //HTTP 1.0 
 	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server  
%>

<head>
	<meta http-equiv="cache-control" content="max-age=0" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
	<meta http-equiv="pragma" content="no-cache" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/manageEffort.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap-filestyle.min.js"></script>
	<script type="text/javascript">window.history.forward();function noBack(){window.history.forward();}</script>
</head>

<body>
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

	<div id="messages">
		<c:if test="${not empty successMessage}">
			<p class="text-success bg-success">${successMessage}</p>
		</c:if>
		<c:if test="${not empty errorMessage}">
			<p class="text-error">${errorMessage}</p>
		</c:if>
	</div>
	
	<c:url var="uploadAction" value="/manageEffort/uploadEffort"></c:url>

	<form:form method="POST" enctype="multipart/form-data"  modelAttribute="file" action="${uploadAction}"> 
		<fieldset>
				<legend>Upload Effort</legend>
		</fieldset>
		<table width="100%" border="0">
			<div class="form-group">
				<tr width="100%">
					<!-- <td class="control-label">Upload File:</td> -->
					<td width="30%">&nbsp;</td>
					<td width="40%">
						<input type="file" name="file" class="filestyle" data-icon="false" data-buttonName="btn-primary" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
					</td>
					<td width="1%">&nbsp;</td>
					<td width="5%" valign="top" halign="left">
						<input id="uploadEffort" class="btn btn-success" type="submit" value="Upload" autofocus="autofocus"/>  
					</td>
					<td width="30%" align="center" valign="bottom">
						<i>
							<b><a href="${pageContext.request.contextPath}/downloadEffortTemplate" target="_blank">Download Effort Template</a></b>
						</i>
					</td>
				</tr>
			</div>
		</table>
	</form:form>
</div>
</body>
