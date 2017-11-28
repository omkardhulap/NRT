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
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/updateMTTRIncident.js"></script>
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
	
	<c:url var="updateAction" value="/searchMTTRIncidents/update"></c:url>

	<form:form action="${updateAction}" id="updateMTTRFormId" modelAttribute="excelMTTRDTO" method="POST">

		<fieldset>
				<legend>Update MTTR Breach Reason</legend>
		</fieldset>
		
		<table width="100%" border="0">
		
		<div class="form-group">
			<tr>
				<td width="12%" valign="top">
					<form:label path="mttrId" class="control-label"><spring:message text="MTTR Id" /></form:label>
				</td>
				<td valign="top">
					<form:input path="mttrId" size="8" class=" form-control" readonly="true" cssStyle="font-style: italic;"/>
				</td>
				<td width="4%" colspan="3">&nbsp;</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="3">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td width="12%"><form:label path="snowId" class="control-label"><spring:message text="SNOW Id" /></form:label></td>
				<td>
					<form:input path="snowId" id="snowId" class="form-control" maxlength="11" readonly="true" cssStyle="font-style: italic;"/>
				</td>
				<td width="4%">&nbsp;</td>
				<td><form:label path="priority" class="control-label"><spring:message text="Priority" /></form:label></td>
				<td>
					<form:input path="priority" id="priority" class="form-control" maxlength="11" readonly="true" cssStyle="font-style: italic;"/>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="3">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td width="12%"><form:label path="assignmentGroup" class="control-label"><spring:message text="Assignment Group" /></form:label></td>
				<td>
					<form:input path="assignmentGroup" id="assignmentGroup" class="form-control" maxlength="11" readonly="true" cssStyle="font-style: italic;"/>
				</td>
				<td width="4%">&nbsp;</td>
				<td><form:label path="assignedTo" class="control-label"><spring:message text="Assigned To" /></form:label></td>
				<td>
					<form:input path="assignedTo" id="assignedTo" class="form-control" maxlength="11" readonly="true" cssStyle="font-style: italic;"/>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="3">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td width="12%"><form:label path="createToResolveInHours" class="control-label"><spring:message text="MTTR in Hrs." /></form:label></td>
				<td>
					<form:input path="createToResolveInHours" id="createToResolveInHours" class="form-control" maxlength="11" readonly="true" cssStyle="font-style: italic;"/>
				</td>
				<td width="4%">&nbsp;</td>
				<td width="12%"><label class="control-label">PES SLA in Hrs.</label></td>
				<c:if test="${excelMTTRDTO.priority == '1 - Critical'}">
					<td>
						<input class="form-control" value="8" readOnly="true" disabled="true" cssStyle="font-style: italic;"/>
					</td>
				</c:if>
				<c:if test="${excelMTTRDTO.priority == '2 - High'}">
					<td>
						<input class="form-control" value="16" readOnly="true" disabled="true" cssStyle="font-style: italic;"/>
					</td>
				</c:if>
				<c:if test="${excelMTTRDTO.priority == '3 - Medium'}">
					<td>
						<input class="form-control" value="80" readOnly="true" disabled="true" cssStyle="font-style: italic;"/>
					</td>
				</c:if>
				<c:if test="${excelMTTRDTO.priority == '4 - Low'}">
					<td>
						<input class="form-control" value="160" readOnly="true" disabled="true" cssStyle="font-style: italic;"/>
					</td>
				</c:if>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="3">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td width="12%"><form:label path="resolutionCode" class="control-label"><spring:message text="Resolution Code" /></form:label></td>
				<td>
					<form:input path="resolutionCode" id="resolutionCode" class="form-control" maxlength="11" readonly="true" cssStyle="font-style: italic;"/>
				</td>
				<td width="4%">&nbsp;</td>
				<td><form:label path="resolutionNotes" class="control-label"><spring:message text="Resolution Notes" /></form:label></td>
				<td>
					<%-- <form:input path="resolutionNotes" id="resolutionNotes" class="form-control" maxlength="11" readonly="true" cssStyle="font-style: italic;"/> --%>
					<form:textarea path="resolutionNotes" id="resolutionNotes" class="form-control" cssStyle="font-style: italic;font-size:11px;height:70px;" maxlength="500" readonly="true" />
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="mttrBreachReason" class="control-label"><spring:message text="Reason for MTTR Breach" /></form:label></td>
				<td>
					<form:select path="mttrBreachReason" id="mttrBreachReason" class="form-control">
						<c:forEach items="${mttrBreachLookupList}" var="reportLookup">
							<form:option value="${reportLookup.value}" label="${reportLookup.value} " />
						</c:forEach>						
					</form:select>
					<span class="hide">Please select Reason for MTTR Breach</span>
				</td>
				<td width="4%">&nbsp;</td>
				<td><form:label path="remarks" class="control-label"><spring:message text="Remarks" /></form:label></td>
				<td>
					<%-- <form:input path="remarks" id="remarks" class="form-control" maxlength="500" placeholder="Enter remarks here in case of other reason"/> --%>
					<form:textarea path="remarks" id="remarks" class="form-control" cssStyle="height:50px;" maxlength="500" placeholder="Enter remarks here in case of other reason"/>
					<span class="hide">Remarks are mandatory in case of Other Reasons</span>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="3">&nbsp;</td></tr>
		</div>
		
		</table>
		
		<!--Button Section-->
		<div class="form-group" align="center">
			<input id="updateMTTR" class="btn btn-primary" type="submit" value="<spring:message text="Update"/>" autofocus="autofocus"/>
			&nbsp;&nbsp;
			<input id="cancelMTTREdit" class="btn btn-warning" type="submit" value="Cancel" onClick="window.location.href='${pageContext.request.contextPath}/searchMTTRIncidents'; return false;"/>
		</div>
	</form:form>
</div>
</body>
