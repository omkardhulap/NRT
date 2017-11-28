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
	
	<c:url var="addAction" value="/manageEffort/add"></c:url>

	<form:form action="${addAction}" id="manageEffortFormId" modelAttribute="effort" method="POST">

		<fieldset>
			<c:if test="${effort.id != 0 && effort.id != null}">
				<legend>Edit Effort</legend>
			</c:if>
			<c:if test="${effort.id == 0 || effort.id == null}">
				<legend>Add Effort</legend>
			</c:if>
		</fieldset>
		
		<table width="100%" border="0">
		
		<div class="form-group">
			<tr>
				<td><form:label path="application.id" class="control-label"><spring:message text="Application" /></form:label></td>
				<td>
					<form:select path="application.id" id="application" class="form-control">
						<c:forEach items="${applicationList}" var="application">
							<form:option value="${application.id}" label="${application.appName} " />
						</c:forEach>
					</form:select>
				</td>
				<td width="4%">&nbsp;</td>
				<td width="12%" valign="top">
					<form:label path="id" class="control-label"><spring:message text="Effort Id" /></form:label>
				</td>
				<td valign="top">
					<c:if test="${effort.id != 0 && effort.id != null}">
						<form:input path="id" readonly="true" size="8" class=" form-control" disabled="true" cssStyle="font-style: italic;"/>
					</c:if>
					<c:if test="${effort.id == 0 || effort.id == null}">
						<form:input path="id" readonly="true" size="8" class=" form-control" disabled="true" value="New Effort" cssStyle="font-style: italic;"/>
					</c:if>
					<form:hidden path="id" />			
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="3">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="category.id" class="control-label"><spring:message text="Effort Category" /></form:label></td>
				<td>
					<form:select path="category.id" id="category" class="form-control">
						<c:forEach items="${categoryList}" var="category">
							<form:option value="${category.id}" label="${category.description} " />
						</c:forEach>
					</form:select>
				</td>
				<td width="4%">&nbsp;</td>
				<td width="12%"><form:label path="snowNumber" class="control-label"><spring:message text="SNOW Id" /></form:label></td>
				<td>
					<form:input path="snowNumber" id="snowNumber" class="form-control" maxlength="12" onblur="this.value=removeSpaces(this.value);"/>
					<span class="hide">Please enter valid SNOW Id</span>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="3">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="priority.id" class="control-label"><spring:message text="Priority" /></form:label></td>
				<td>
					<form:select path="priority.id" id="priority" class="form-control">
						<c:forEach items="${priorityList}" var="priority">
							<form:option value="${priority.id}" label="${priority.description} " />
						</c:forEach>
					</form:select>
					<span class="hide">Please select Priority</span>
				</td>
				<td width="4%">&nbsp;</td>
				<td><form:label path="complexity.id" class="control-label"><spring:message text="Complexity" /></form:label></td>
				<td>
					<form:select path="complexity.id" id="complexity" class="form-control">
						<c:forEach items="${complexityList}" var="complexity">
							<form:option value="${complexity.id}" label="${complexity.description} " />
						</c:forEach>
					</form:select>
					<span class="hide">Please select Complexity</span>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="3">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="effortDescription" class="control-label"><spring:message text="Effort Description*" /></form:label></td>
				<td colspan="4">
					<form:textarea path="effortDescription" id="effortDescription" class=" form-control" maxlength="500"/>
					<span class="hide">Please enter Effort Description</span>
				</td>			
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="3">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="effortHours" class="control-label"><spring:message text="Effort (hrs)*" /></form:label></td>
				<td>
					<form:input type="number" path="effortHours" id="effortHours" step="0.01" class="form-control" maxlength="5"/>
					<span class="hide">Please enter Effort (hrs)</span>
				</td>
				<td>&nbsp;</td>
				<td><form:label path="effortDate" class="control-label"><spring:message text="Effort Date*" /></form:label></td>
				<td>
					<form:input path="effortDate" id="effortDate" class="datepicker form-control"/>
					<span class="hide">Please enter valid Effort Date</span>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		</table>
		
		<!--Button Section-->
		<div class="form-group" align="center">
			<c:if test="${effort.id != 0 && effort.id != null}">
				<input id="editEffort" class="btn btn-primary" type="submit" value="<spring:message text="Update"/>" autofocus="autofocus"/>
				&nbsp;&nbsp;
				<input id="cancelEffortEdit" class="btn btn-warning" type="submit" value="Cancel" onClick="window.location.href='${pageContext.request.contextPath}/searchEffort'; return false;"/>
				&nbsp;&nbsp;
				<input id="deleteEffort" class="btn btn-danger" type="submit" value="<spring:message text="Delete"/>" onClick="window.location.href='${pageContext.request.contextPath}/deleteEffort/${effort.id}'; return false;"/>
			</c:if>
			<c:if test="${effort.id == 0 || effort.id == null}">
				<input id="addEffort" class="btn btn-primary" type="submit" value="<spring:message text="Add"/>" autofocus="autofocus"/>
				&nbsp;&nbsp;
				<input id="resetEffort" class="btn btn-warning" type="reset" value="Reset"/>
			</c:if>
		</div>

	</form:form>
</div>
</body>
