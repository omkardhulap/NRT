<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<% 
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
 	response.setHeader("Pragma","no-cache"); //HTTP 1.0 
 	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server  
%>
<head>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/unplannedOutage.js"></script>
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

	<c:url var="addAction" value="/unplannedOutage/add"></c:url>

	<form:form action="${addAction}" id="unPlannedOutageFormId" modelAttribute="unplannedOutageUI" method="POST">
		<fieldset>
			<c:if test="${unplannedOutageUI.id != 0 && unplannedOutageUI.id != null }">
				<legend>Edit Unplanned Outage</legend>
			</c:if>
			<c:if test="${unplannedOutageUI.id == 0 || unplannedOutageUI.id == null}">
				<legend>Add Unplanned Outage</legend>
			</c:if>
		</fieldset>
			
		<table width="100%" border="0">
		
		<div class="form-group">
			<tr>
				<td width="12%"><form:label path="snowId" class="control-label"><spring:message text="Incident Id*" /></form:label></td>
				<td>
					<form:input path="snowId" class="form-control" maxlength="10" onblur="this.value=removeSpaces(this.value);"/>
					<span class="hide">Please enter valid Incident Id</span>
				</td>
				<td width="4%">&nbsp;</td>
				<td width="12%"><form:label path="id" class="control-label"><spring:message text="Outage Id" /></form:label></td>
				<td>
					<c:if test="${unplannedOutageUI.id != 0 && unplannedOutageUI.id != null }">
						<form:input path="id" readonly="true" size="8" class="form-control" disabled="true" cssStyle="font-style: italic;"/>
					</c:if>
					<c:if test="${unplannedOutageUI.id == 0 || unplannedOutageUI.id == null}">
						<form:input path="id" readonly="true" size="8" class="form-control" disabled="true" value="New Outage" cssStyle="font-style: italic;"/>
					</c:if>
					<form:hidden path="id" />
					<form:hidden path="outageType" value='U'/>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="applications" class="control-label"><spring:message text="Application*" /></form:label></td>
				<td>
					<form:select id="plannedMultiselect" path="applications" multiple="multiple" class=" form-control">
						<c:forEach items="${applicationList}" var="application" varStatus="num">
							<form:option value="${application.appName}" label="${application.appName} " />
						</c:forEach>
					</form:select>
					<span class="hide">Please select Application(s)</span>
				</td>
				<td>&nbsp;</td>
				<td><form:label path="dueTo" class="control-label"><spring:message text="Due To Any <br>Release?*" /></form:label>
			</td>
				<td>
					<form:input path="dueTo" class="form-control" maxlength="255"/> 
					<span class="hide">Please enter Due To</span>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="priority.id" class="control-label"><spring:message text="Priority*" /></form:label></td>
				<td>
					<form:select path="priority.id" id="priority" class="form-control">
						<c:forEach items="${priorityList}" var="priority">
							<form:option value="${priority.id}" label="${priority.description} " />
						</c:forEach>
					</form:select>
				</td>
				<td width="4%">&nbsp;</td>
				<td><form:label path="severity.id" class="control-label"><spring:message text="Severity*" /></form:label></td>
				<td>
					<form:select path="severity.id" id="severity" class="form-control">
						<c:forEach items="${severityList}" var="severity">
							<form:option value="${severity.id}" label="${severity.description} " />
						</c:forEach>
					</form:select>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="vendorAccountable" class="control-label"><spring:message text="Vendor Accountable*" /></form:label></td>
				<td>
					<form:radiobutton id="vendorAccountable" path="vendorAccountable" value="Yes" />&nbsp;&nbsp;Yes&nbsp;&nbsp;
					<form:radiobutton id="vendorAccountable" path="vendorAccountable" value="No" />&nbsp;&nbsp;No&nbsp;&nbsp;
					<form:radiobutton id="vendorAccountable" path="vendorAccountable" value="N/A" />&nbsp;&nbsp;N/A&nbsp;&nbsp;					
					<br><span class="hide">Please choose Vendor Accountable</span>
				</td>
				<td width="4%">&nbsp;</td>
				<td><form:label path="vendorAccountableName" class="control-label"><spring:message text="Vendor Name*" /></form:label></td>
				<td>
					<form:input path="vendorAccountableName" class="form-control" maxlength="45"/>
					<span class="hide">Please enter Vendor Name</span>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="reportedBy" class="control-label"><spring:message text="Reported By*" /></form:label></td>
				<td>
					<form:input path="reportedBy" class="form-control" maxlength="45"/>
					<span class="hide">Please enter Reported By</span>
				</td>
				<td>&nbsp;</td>
				<td><form:label path="reportedOn" class="control-label"><spring:message text="Reported On*" /></form:label></td>
				<td>
					<form:input path="reportedOn" class="datepicker form-control"/>
					<span class="hide">Please enter Reported On</span>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="deploymentStartDate" class="control-label"><spring:message text="Start Time*" /></form:label></td>
				<td>
					<form:input path="deploymentStartDate" class="datepicker form-control" />
					<span class="hide">Please enter Start Date</span>
				</td>
				<td>&nbsp;</td>
				<td><form:label path="deploymentEndDate" class="control-label"><spring:message text="End Time*" /></form:label></td>
				<td>
					<form:input path="deploymentEndDate" class="datepicker form-control"  />
					<span class="hide">Please enter End Date</span>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="description" class="control-label" ><spring:message text="Short Description*" /></form:label></td>
				<td colspan="4">
					<form:input path="description" class="form-control" maxlength="500"/>
					<span class="hide">Please enter Description</span>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="businessAffected" class="control-label"><spring:message text="Business Affected*" /></form:label></td>
				<td>
					<form:input path="businessAffected" class="form-control" maxlength="500"/>
					<span class="hide">Please enter Business Affected</span>
				</td>
				<td>&nbsp;</td>
				<td><form:label path="stOwner" class="control-label"><spring:message text="ST Owner*" /></form:label></td>
				<td>
					<form:input path="stOwner" class="form-control" maxlength="45"/>
					<span class="hide">Please enter ST Owner</span>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="executiveSummary" class="control-label"><spring:message text="Executive <br>Summary*" /></form:label></td>
				<td>
					<form:textarea path="executiveSummary"  class="form-control" cssStyle="height:120px;" maxlength="255"/>
					<span class="hide">Please enter Executive Summary</span>
				</td>
				<td>&nbsp;</td>
				<td><form:label path="technicalIssues" class="control-label"><spring:message text="Technical Issues*" /></form:label></td>
				<td>
					<form:textarea path="technicalIssues" class="form-control" cssStyle="height:120px;" maxlength="700"/>
                	<span class="hide">Please enter Technical Issues</span>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="rootCause" class="control-label"><spring:message text="Root cause*" /></form:label></td>
				<td>
					<form:textarea path="rootCause" class="form-control" cssStyle="height:120px;" maxlength="1000"/>
					<span class="hide">Please enter Root Cause</span>	
				</td>
				<td>&nbsp;</td>
				<td><form:label path="resolution" class="control-label"><spring:message text="Resolution*" /></form:label></td>
				<td>
					<form:textarea path="resolution" class="form-control" cssStyle="height:120px;" maxlength="500"/>
               		<span class="hide">Please enter Resolution</span>
               	</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="aarOwner" class="control-label"><spring:message text="AAR Owner*" /></form:label></td>
				<td>
					<form:input path="aarOwner" class="form-control" maxlength="45"/>
					<span class="hide">Please enter AAR Owner</span>
				</td>
				<td>&nbsp;</td>
				<td><form:label path="aarDate" class="control-label"><spring:message text="AAR Date*" /></form:label></td>
				<td>
					<form:input path="aarDate" class="datepicker form-control"/>
					<span class="hide">Please enter AAR Date</span>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="database" class="control-label"><spring:message text="Infrastructure*" /></form:label></td>
				<td>
					<form:input path="database" class="form-control" maxlength="45"/>
					<span class="hide">Please enter Database Details</span>
				</td>
				<td>&nbsp;</td>
				<td><form:label path="platform" class="control-label"><spring:message text="Platform*" /></form:label></td>
				<td>
					<form:input path="platform" class="form-control" maxlength="45"/>
					<span class="hide">Please enter Platform</span>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="pointOfFailures" class="control-label"><spring:message text="Point Of Failures*" /></form:label></td>
				<td>
					<form:select id="pointOfFailuresMultiselect" path="pointOfFailures" multiple="multiple" class=" form-control" cssStyle="height:55px">
						<c:forEach items="${pointOfFailureList}" var="pointOfFailure" varStatus="num">
							<form:option value="${pointOfFailure.pointOfFailureDesc}" label="${pointOfFailure.pointOfFailureDesc} " />
						</c:forEach>
					</form:select>
					<span class="hide">Please select Point Of Failure(s)</span>
				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		</table>
			
           <form:hidden path="userId" value='<%= session.getAttribute("user_id") %>'/>	

		<div align="center">
			<c:if test="${unplannedOutageUI.id != 0 && unplannedOutageUI.id != null }">
				<input id="editUnplannedOutage" class="btn btn-primary" type="submit" value="<spring:message text="Update"/>" autofocus="autofocus"/>
				&nbsp;&nbsp;
				<input id="cancelUnPlannedOutageEdit" class="btn btn-warning" type="submit" value="Cancel" onClick="window.location.href='${pageContext.request.contextPath}/searchOutage'; return false;"/>
			</c:if>
			<c:if test="${unplannedOutageUI.id == 0 || unplannedOutageUI.id == null}">
				<input id="addUnplannedOutage" class="btn btn-primary" type="submit" value="<spring:message text="Add"/>" autofocus="autofocus"/>
				&nbsp;&nbsp;
				<input id="resetUnplannedOutage" class="btn btn-warning" type="reset" value="Reset"/>
			</c:if>
		</div>
		
	</form:form>
</div>
</body>