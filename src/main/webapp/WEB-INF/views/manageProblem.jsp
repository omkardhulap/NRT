<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<head>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/problemManagement.js"></script>
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
	
	<c:url var="addAction" value="/problemManagement/add"></c:url>

	<form:form action="${addAction}" id="problemManagementFormId" modelAttribute="problemManagementUI" method="POST">

		<fieldset>
			<c:if test="${problemManagementUI.id != 0 && problemManagementUI.id != null}">
				<legend>Edit Problem Management</legend>
			</c:if>
			<c:if test="${problemManagementUI.id == 0 || problemManagementUI.id == null}">
				<legend>Add Problem Management</legend>
			</c:if>
		</fieldset>
		
		<table width="100%" border="0">
		
		<div class="form-group">
		<tr>
			<td width="12%"><form:label path="innovationTitle" class="control-label"><spring:message text="Innovation Title*" /></form:label></td>
			<td colspan="4">
				<form:input path="innovationTitle" class="form-control" maxlength="900" />
				<span class="inputFieldErrors hide">Please enter Innovation Title</span>
			</td>
		</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td width="12%" rowspan="3"><form:label path="problemStatement" class="control-label"><spring:message text="Problem Statement*" /></form:label></td>
				<td  rowspan="3">
					<form:textarea path="problemStatement" class=" form-control" maxlength="4800" cssStyle="height:88px;"/>
					<span class="inputFieldErrors hide">Please enter Problem Statement</span>
				</td>
				<td width="4%" rowspan="3">&nbsp;</td>
				<td width="12%" valign="top">
					<form:label path="id" class="control-label"><spring:message text="Id" /></form:label>
				</td>				
				<td valign="top">
					<c:if test="${problemManagementUI.id != 0 && problemManagementUI.id != null}">
						<form:input path="id" readonly="true" size="8" class=" form-control" disabled="true" cssStyle="font-style: italic;"/>
					</c:if>
					<c:if test="${problemManagementUI.id == 0 || problemManagementUI.id == null}">
						<form:input path="id" readonly="true" size="8" class=" form-control" disabled="true" value="New Problem Management" cssStyle="font-style: italic;"/>
					</c:if>
					<form:hidden path="id" />
				</td>
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr>
				<td><form:label path="application.id" class="control-label"><spring:message text="Application*" /></form:label></td>
				<td>
					<form:select path="application.id" id="application" class="form-control">
						<c:forEach items="${applicationList}" var="application">
							<form:option value="${application.id}" label="${application.appName} " />
						</c:forEach>
					</form:select>
					<span class="inputFieldErrors hide">Please select Application</span>					
				</td>			
				</tr>		
			</tr>
		</div>

		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="solution" class="control-label"><spring:message text="Solution*" /></form:label></td>
				<td colspan="4">
					<form:input path="solution" class=" form-control" maxlength="900"/>
					<span class="inputFieldErrors hide">Please enter Solution</span>
				</td>			
			</tr>
		</div>
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="itBenefit" class="control-label"><spring:message text="IT Benefits*" /></form:label></td>
				<td>
					<form:textarea path="itBenefit" class=" form-control" maxlength="4800" cssStyle="height:110px;"/>
					<span class="inputFieldErrors hide">Please enter IT Benefits</span>
				</td>
				<td>&nbsp;</td>
				<td><form:label path="businessBenefit" class="control-label"><spring:message text="Business Benefits*" /></form:label></td>
				<td>
					<form:textarea path="businessBenefit" class=" form-control" maxlength="4800" cssStyle="height:110px;"/>
					<span class="inputFieldErrors hide">Please enter Business Benefits</span>
				</td>				
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="ideatedBy" class="control-label"><spring:message text="Ideated By*" /></form:label></td>
				<td>
					<form:input path="ideatedBy" class=" form-control" maxlength="90"/>
					<span class="inputFieldErrors hide">Please enter Ideated By</span>
				</td>				
				<td>&nbsp;</td>
				<td><form:label path="implementedBy" class="control-label"><spring:message text="Implemented By*" /></form:label></td>
				<td>
					<form:input path="implementedBy" class=" form-control" maxlength="90"/>
					<span class="inputFieldErrors hide">Please enter Implemented By</span>
				</td>				
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="initiationDate" class="control-label"><spring:message text="Initiation Date*" /></form:label></td>
				<td>
					<form:input path="initiationDate" class="datepicker form-control"/>
					<span class="inputFieldErrors hide">Please enter Initiation Date</span>
				</td>
				<td>&nbsp;</td>
				<td><form:label path="completionDate" class="control-label"><spring:message text="Completion Date*" /></form:label></td>
				<td>
					<form:input path="completionDate" class="datepicker form-control"/>
					<span class="inputFieldErrors hide">Please enter Completion Date</span>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>

		<div class="form-group">
			<tr>
				<td><form:label path="status.id" class="control-label"><spring:message text="Status*" /></form:label></td>
				<td>
					<form:select path="status.id" id="status" class="form-control">
						<c:forEach items="${statusList}" var="status">
							<form:option value="${status.id}" label="${status.description} " />
						</c:forEach>
					</form:select>
				</td>								
				<td>&nbsp;</td>
				<td><form:label path="completionPercentage" class="control-label"><spring:message text="Completion Percentage*" /></form:label></td>
				<td>
					<form:input path="completionPercentage" class=" form-control" maxlength="3"/>
					<span class="inputFieldErrors hide">Please enter Completion Percentage</span>
				</td>				
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>
		
		<div class="form-group">
			<tr>
				<td><form:label path="effortHours" class="control-label"><spring:message text="Effort (hrs)*" /></form:label></td>
				<td>
					<form:input path="effortHours" class=" form-control" maxlength="6"/>
					<span class="inputFieldErrors hide">Please enter Effort in hours</span>
				</td>
				<td>&nbsp;</td>
				<td><form:label path="sme" class="control-label"><spring:message text="SME" /></form:label></td>
				<td>
					<form:input path="sme" class=" form-control" maxlength="90"/>
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
				<td>&nbsp;</td>
				<td><form:label path="dollarSaving" class="control-label"><spring:message text="Dollar Saving" /></form:label></td>
				<td>
					<form:input path="dollarSaving" class=" form-control" maxlength="10"/>
					<span class="inputFieldErrors hide">Please enter a valid integer value</span>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>

		<div class="form-group">
			<tr>
				<td><form:label path="incidentReduction" class="control-label"><spring:message text="Incident Reduction/month" /></form:label></td>
				<td>
					<form:input path="incidentReduction" class=" form-control" maxlength="10"/>
					<span class="inputFieldErrors hide">Please enter a valid integer value</span>
				</td>
				<td>&nbsp;</td>
				<td><form:label path="effortSaving" class="control-label"><spring:message text="Effort Saving (Hrs)/month" /></form:label></td>
				<td>
					<form:input path="effortSaving" class=" form-control" maxlength="10"/>
					<span class="inputFieldErrors hide">Please enter a valid integer value</span>
				</td>
			</tr>
		</div>
		
		<div class="form-group">
			<tr><td colspan="5">&nbsp;</td></tr>
		</div>

		<div class="form-group">
			<tr>
				<td><form:label path="benefitTypes" class="control-label"><spring:message text="Benefit Types*" /></form:label></td>
				<td>
					<form:select path="benefitTypes" id="benefitTypesMultiselect" multiple="multiple" class="form-control" cssStyle="height:52px">
						<c:forEach items="${benefitTypeList}" var="benefitType" varStatus="num">
							<form:option value="${benefitType.value}" label="${benefitType.value} " />
						</c:forEach>
					</form:select>
					<span class="inputFieldErrors hide">Please select Benefit Type(s)</span>					
				</td>
				<td>&nbsp;</td>
				<td><form:label path="comments" class="control-label"><spring:message text="Comments" /></form:label></td>
				<td>
					<form:textarea path="comments" class=" form-control" maxlength="1800" cssStyle="height:52px;"/>
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
			<c:if test="${problemManagementUI.id != 0 && problemManagementUI.id != null}">
				<input id="editPM" class="btn btn-primary" type="submit" value="<spring:message text="Update"/>" autofocus="autofocus"/>
				&nbsp;&nbsp;
				<input id="cancelPMEdit" class="btn btn-warning" type="submit" value="Cancel" onClick="window.location.href='${pageContext.request.contextPath}/searchPM'; return false;"/>
			</c:if>
			<c:if test="${problemManagementUI.id == 0 || problemManagementUI.id == null}">
				<input id="addPM" class="btn btn-primary" type="submit" value="<spring:message text="Add"/>" autofocus="autofocus"/>
				&nbsp;&nbsp;
				<input id="resetPM" class="btn btn-warning" type="reset" value="Reset"/>
			</c:if>
		</div>

	</form:form>
</div>
</body>
