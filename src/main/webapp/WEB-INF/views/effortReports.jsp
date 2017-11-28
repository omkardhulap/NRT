<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<% 
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
 	response.setHeader("Pragma","no-cache"); //HTTP 1.0 
 	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server  
%>
<head>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/effortReports.js"></script>
	<meta http-equiv="cache-control" content="max-age=0" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
	<meta http-equiv="pragma" content="no-cache" />
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

<!-- Search Criteria Section -->
	<c:url var="effortReportsAction" value="/effortReports/report"></c:url>

	<form:form action="${effortReportsAction}" id="formId" commandName="effortReports" method="GET">
		<fieldset>
			<legend>Effort Reports</legend>
		</fieldset>
		<table width="100%" border="0">
			
			<div class="container-fluid">
				<tr>
					<td width="30%" class="text-primary">&middot; Resource Wise Weekly Effort Report </td>
					<td width="17%">
						<i>
							<b><a href="${pageContext.request.contextPath}/downloadWeeklyEffortReport" target="_blank">: Download</a></b>
						</i>
					</td>
					<td width="6%">&nbsp;</td>
					<td valign="top" width="30%"></td>
					<td valign="top" width="17%"></td>
				</tr>
				<tr><td colspan="5">&nbsp;</td></tr>
				<tr>
					<td width="30%" class="text-primary">&middot; Application Wise Weekly Effort Report </td>
					<td width="17%">
						<i>
							<b><a href="${pageContext.request.contextPath}/downloadWeeklyApplicationWiseEffort" target="_blank">: Download</a></b>
						</i>
					</td>
					<td width="6%">&nbsp;</td>
					<td valign="top" width="30%"></td>
					<td valign="top" width="17%"></td>					
				</tr>
				<tr><td colspan="5">&nbsp;</td></tr>
				<tr>
					<td width="30%" class="text-primary">&middot; Capability Wise Weekly Effort Report </td>
					<td width="17%">
						<i>
							<b><a href="${pageContext.request.contextPath}/downloadWeeklyCapabilityWiseEffort" target="_blank">: Download</a></b>
						</i>
					</td>
					<td width="6%">&nbsp;</td>
					<td valign="top" width="30%"></td>
					<td valign="top" width="17%"></td>					
				</tr>
			</div>
			
			<div class="form-group">
				<tr><td colspan="5">&nbsp;</td></tr>
			</div>
			</table>
			
			<div class="container-fluid" height="5px">&nbsp;</div>
			
			<fieldset>
			<legend>MTTR Reports</legend>
			</fieldset>
			
			<table width="100%" border="0">
			<div class="container-fluid">
				<tr>
					<td width="30%" class="text-primary">&middot; Report1 </td>
					<td width="17%">
						<i>
							<b><a href="${pageContext.request.contextPath}/downloadWeeklyEffortReport" target="_blank">: Download</a></b>
						</i>
					</td>
					<td width="6%">&nbsp;</td>
					<td valign="top" width="30%"></td>
					<td valign="top" width="17%"></td>
				</tr>
				<tr><td colspan="5">&nbsp;</td></tr>
				<tr>
					<td width="30%" class="text-primary">&middot; Report1 </td>
					<td width="17%">
						<i>
							<b><a href="${pageContext.request.contextPath}/downloadWeeklyApplicationWiseEffort" target="_blank">: Download</a></b>
						</i>
					</td>
					<td width="6%">&nbsp;</td>
					<td valign="top" width="30%"></td>
					<td valign="top" width="17%"></td>					
				</tr>
				<tr><td colspan="5">&nbsp;</td></tr>
				<tr>
					<td width="30%" class="text-primary">&middot; Report1 </td>
					<td width="17%">
						<i>
							<b><a href="${pageContext.request.contextPath}/downloadWeeklyCapabilityWiseEffort" target="_blank">: Download</a></b>
						</i>
					</td>
					<td width="6%">&nbsp;</td>
					<td valign="top" width="30%"></td>
					<td valign="top" width="17%"></td>					
				</tr>
			</div>
			
			<div class="form-group">
				<tr><td colspan="5">&nbsp;</td></tr>
			</div>
			</table>
			
	</form:form>
</div>
</body>
