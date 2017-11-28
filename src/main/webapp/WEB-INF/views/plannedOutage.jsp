<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<% 
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
 	response.setHeader("Pragma","no-cache"); //HTTP 1.0 
 	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server  
%>
<head>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/plannedOutage.js"></script>
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
	
	<c:url var="addAction" value="/plannedOutage/add"></c:url>

	<form:form action="${addAction}" id="plannedOutageFormId" modelAttribute="plannedOutageUI" method="POST">

		<fieldset>
			<c:if test="${plannedOutageUI.id != 0 && plannedOutageUI.id != null}">
				<legend>Edit Planned Outage</legend>
			</c:if>
			<c:if test="${plannedOutageUI.id == 0 || plannedOutageUI.id == null}">
				<legend>Add Planned Outage</legend>
			</c:if>
		</fieldset>
		
		<table width="100%" border="0">
		
		<div class="form-group">
		<tr>
		<td width="12%" rowspan=3 valign="top" ><form:label path="applications" class="control-label"><spring:message text="Applications*" /></form:label></td>
		<td rowspan=3>
			<form:select id="plannedMultiselect" path="applications" multiple="multiple" class=" form-control" cssStyle="height:130px">
				<c:forEach items="${applicationList}" var="application" varStatus="num">
					<form:option value="${application.appName}" label="${application.appName} " />
				</c:forEach>
			</form:select>				
			<span class="hide">Please select Application(s)</span>
		</td>
		<td width="4%">&nbsp;</td>
		<td width="12%" valign="top">
			<form:label path="id" class="control-label"><spring:message text="Outage Id" /></form:label>
		</td>
		<td valign="top">
			<c:if test="${plannedOutageUI.id != 0 && plannedOutageUI.id != null}">
				<form:input path="id" readonly="true" size="8" class=" form-control" disabled="true" cssStyle="font-style: italic;"/>
			</c:if>
			<c:if test="${plannedOutageUI.id == 0 || plannedOutageUI.id == null}">
				<form:input path="id" readonly="true" size="8" class=" form-control" disabled="true" value="New Outage" cssStyle="font-style: italic;"/>
			</c:if>
			<form:hidden path="id" />
			<form:hidden path="outageType" value='P'/>
		</td>
		</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="3">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
		<tr>
		<td>&nbsp;</td>
		<td valign="top"><form:label path="outageRequired" class="control-label"><spring:message text="Outage Required*" /></form:label></td>
		<td valign="top">
			<form:radiobutton id="outageRequired" path="outageRequired" value='true' />&nbsp;&nbsp;Yes
			&nbsp;&nbsp;
			<form:radiobutton id="outageRequired" path="outageRequired" value='false' />&nbsp;&nbsp;No
			<span class="hide">Please choose Yes or No</span>
		</td>
		
		</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
		<tr>
			<td><form:label path="snowId" class="control-label"><spring:message text="Snow Id*" /></form:label></td>
			<td colspan="4">
				<form:input  path="snowId" class=" form-control" onblur="this.value=removeSpaces(this.value);"/>
				<span class="hide">Please enter a valid SNOW Id</span>
			</td>		
		</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="deploymentStartDate" class="control-label"><spring:message text="Start Date*" /></form:label></td>
				<td>
					<form:input path="deploymentStartDate" class="datepicker  form-control" />
					<span class="hide">Please enter Start Date</span>
				</td>
				<td>&nbsp;</td>
				<td><form:label path="deploymentEndDate" class="control-label"><spring:message text="End Date*" /></form:label></td>
				<td>
					<form:input path="deploymentEndDate" class="datepicker  form-control" />
					<span class="hide">Please enter End Date</span>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="approvedBy.id" class="control-label"><spring:message text="Approved By*" /></form:label></td>
				<td>
					<c:forEach items="${reportLookupList}" var="reportLookup">
						<form:radiobutton id="approvedBy" path="approvedBy.id" value="${reportLookup.id}" /> &nbsp; ${reportLookup.value} &nbsp;&nbsp;
		            </c:forEach>
					<br><span class="hide">Please choose Approved By</span>
				</td>
				<td>&nbsp;</td>
				<td><form:label path="approvalDate" class="control-label"><spring:message text="Approval Date*" /></form:label></td>
				<td>
					<form:input path="approvalDate" class="datepicker  form-control" />
					<span class="hide">Please enter Approval Date</span>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="scope" class="control-label" ><spring:message text="Scope*" /></form:label></td>
				<td colspan="4">
					<form:input path="scope" class=" form-control" maxlength="20"/>
					<span class="hide">Please enter Scope</span>
				</td>			
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="description" class="control-label"><spring:message text="Description*" /></form:label></td>
				<td colspan="4">
					<form:textarea path="description" class=" form-control" maxlength="500"/>
					<span class="hide">Please enter Description</span>
				</td>			
			</tr>
		</div>

		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		</table>
		
		<form:hidden path="userId" value='<%= session.getAttribute("user_id") %>'/>

		<!--Button Section-->
		<div class="form-group" align="center">
			<c:if test="${plannedOutageUI.id != 0 && plannedOutageUI.id != null}">
				<input id="editPlannedOutage" class="btn btn-primary" type="submit" value="<spring:message text="Update"/>" autofocus="autofocus"/>
				&nbsp;&nbsp;
				<input id="cancelPlannedOutageEdit" class="btn btn-warning" type="submit" value="Cancel" onClick="window.location.href='${pageContext.request.contextPath}/searchOutage'; return false;"/>
			</c:if>
			<c:if test="${plannedOutageUI.id == 0 || plannedOutageUI.id == null}">
				<input id="addPlannedOutage" class="btn btn-primary" type="submit" value="<spring:message text="Add"/>" autofocus="autofocus"/>
				&nbsp;&nbsp;
				<input id="resetPlannedOutage" class="btn btn-warning" type="reset" value="Reset"/>
			</c:if>
		</div>

	</form:form>
</div>
</body>
