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
	<meta http-equiv="cache-control" content="max-age=0" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
	<meta http-equiv="pragma" content="no-cache" />
	<script type="text/javascript">window.history.forward();function noBack(){window.history.forward();}</script>
	
	<!-- DataTables CSS -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquery.dataTables.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/dataTables.tableTools.css">
	  
	<!-- DataTables -->
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/js/dataTables.tableTools.js"></script>
	
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/js/searchMTTRIncidents.js"></script>
	
</head>

<body>
	<input type=hidden id="contextRoot" value='${pageContext.request.contextPath}'>
	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

	<div id="messages">
		<c:if test="${not empty successMessage}">
			<p id="mttrMessage" class="text-success bg-success">${successMessage}</p>
		</c:if>
		<c:if test="${not empty errorMessage}">
			<p id="mttrMessage" class="text-error">${errorMessage}</p>
		</c:if>
	</div>
<!-- Search Criteria Section -->
	<c:url var="searchAction" value="/searchMTTRIncidents/search"></c:url>
	<form:form action="${searchAction}" id="formId" commandName="searchMTTR" method="GET">
		<fieldset>
			<legend>MTTR Incident Analysis</legend>
		</fieldset>
		<table width="100%" border="0">
			<div class="form-group">
				<tr>
					<td width="15%"><form:label path="fromDate" class="control-label"><spring:message text="Incident Closed (From)" /></form:label></td>
					<td>
						<form:input path="fromDate" name="fromDate" id="fromDate" class="datepicker form-control" data-toggle="tooltip" title="Defaults to 1st of current month"/>
						<span class="hide">Please select Incident Closed (From)</span>
					</td>
					<td width="5%">&nbsp;</td>
					<td width="15%"><form:label path="toDate" class="control-label"><spring:message text="Incident Closed (To)" /></form:label></td>
					<td>
						<form:input path="toDate"  name="toDate" id="toDate" class="datepicker form-control" data-toggle="tooltip" title="Defaults to current date"/>
						<span class="hide">Please select Incident Closed (To)</span>
					</td>
					<td width="10%">&nbsp;</td>
					<td>
						<input id="searchButton" class="btn btn-primary" type="submit" value="GO" autofocus="autofocus" />
						<input id="exportBtn" type="button" class="btn btn-warning" value="Download"/>	
					</td>
				</tr>
				<tr><td colspan="7"><hr size="4"></td></tr>
			</div>
			</div>
		</table>
		<!--  Place for data table --> 
		<div id="searchMTTRIncidentsTableDiv"></div>
	</form:form>
</div>
</body>
