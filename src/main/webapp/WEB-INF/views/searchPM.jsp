<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<head>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/searchPM.js"></script>
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
	<c:url var="searchAction" value="/searchPM/search"></c:url>

	<form:form action="${searchAction}" id="formId" commandName="searchProblemManagement" method="GET">
		<fieldset>
			<legend>Search Problem Management</legend>
		</fieldset>
			
		
		<table width="100%" border="0">
			
			<div class="form-group">
				<tr>
					<td rowspan=5 valign="top" width="12%"><form:label path="applications" class="control-label"><spring:message text="Application" /></form:label></td>
					<td rowspan=5 width="18%">
						<form:select id="appsMultiselect" path="applications" multiple="multiple" class="form-control" cssStyle="height:170px">
						<c:forEach items="${applicationList}" var="application" varStatus="num">
							<form:option value="${application.appName}" label="${application.appName} " />
						</c:forEach>
						</form:select>
					</td>
					<td rowspan=3 width="5%">&nbsp;</td>
					<td rowspan=3 valign="top" width="12%"><form:label path="statuses" class="control-label"><spring:message text="Status" /></form:label></td>
					<td rowspan=3 valign="top" width="18%">
						<form:select id="statusMultiselect" path="statuses" multiple="multiple" class="form-control" cssStyle="height:115px" >
						<c:forEach items="${statusList}" var="status" varStatus="num">
							<form:option value="${status.description}" label="${status.description} " />
						</c:forEach>
						</form:select>
					</td>
					<td width="5%">&nbsp;</td>
					<!-- Add Tag for Engin -->
					<td valign="top" width="12%"><form:label path="benefitTypes" class="control-label"><spring:message text="Benefit Types" /></form:label></td>
					<td valign="top" width="18%">
						<form:select id="benefitTypeMultiselect" path="benefitTypes" multiple="multiple" class="form-control" cssStyle="height:60px" >
						<c:forEach items="${benefitTypeList}" var="benefitType" varStatus="num">
							<form:option value="${benefitType.value}" label="${benefitType.value} " />
						</c:forEach>
						</form:select>
					</td>
				</tr>
			</div>
			
			<div class="form-group">
				<tr><td colspan="6">&nbsp;</td></tr>
			</div>
			
			<div class="form-group">
				<tr>
					<td >&nbsp;</td> 
					<td><form:label path="owner" class="control-label"><spring:message text="Owner" /></form:label></td>
					<td>
						<form:input path="owner" id="owner" class="form-control"/>
					</td>
				</tr>
			</div>
			
			
			<div class="form-group">
				<tr><td colspan="6">&nbsp;</td></tr>
			</div>
			
			<div class="form-group">
				<tr>
					<td>&nbsp;</td>				
					<td><form:label path="fromDate" class="control-label"><spring:message text="From Date" /></form:label></td>
					<td>
						<form:input path="fromDate" id="fromDate" class="datepicker form-control" />
						<span class="hide">Please select From date</span>
					</td>
					<td>&nbsp;</td>
					<td><form:label path="toDate" class="control-label"><spring:message text="To Date" /></form:label></td>
					<td>
						<form:input path="toDate" id="toDate" class="datepicker form-control"/>
						<span class="hide">Please select To date</span>
					</td>
				</tr>
			</div>
			
			<div class="form-group">
				<tr><td colspan="8">&nbsp;</td></tr>
				<tr><td colspan="8">&nbsp;</td></tr>
			</div>

		</table>
		
		<div align="center">
			<input id="searchButton" class="btn btn-primary" type="submit" value="Search" autofocus="autofocus" />
			&nbsp;&nbsp;
			<input id="resetPMSearch" class="btn btn-warning" type="reset" value="Reset" />
		</div>
		
		<hr size="4">
		
<!-- Table Display Section -->
		<fieldset>
			<legend>Problem Management List</legend>
		</fieldset>
		<div class="panel-body">
		
		<display:table id="data" name="listProblemManagements" pagesize="10" requestURI="${pageContext.request.contextPath}/searchPM/search" export="true" class="table table-bordered table-striped" >
                
                <display:column title="Id" sortable="true" media="html">
                <c:set var="id" value="${data.id}" />  
        				<a href="${pageContext.request.contextPath}/editProblemManagement/${id}">${id}</a>   
    			</display:column>
    			<display:column property="id" title="Problem Management Id" class="hide" headerClass="hide" />
    			
                <display:column property="application" title="Application" sortable="true"></display:column>
                <display:column property="innovationTitle" title="Innovation Title" sortable="true"></display:column>
                <display:column property="problemStatement" title="Problem Statement" sortable="true" class="hide" headerClass="hide" />
                <display:column property="solution" title="Solution" sortable="true" class="hide" headerClass="hide" />
                <display:column property="itBenefit" title="IT Benefit" sortable="true" class="hide" headerClass="hide" />
                <display:column property="businessBenefit" title="Business Benefit" sortable="true" class="hide" headerClass="hide" />
                <display:column property="status" title="Status" sortable="true" />
                <display:column property="priority" title="Priority" sortable="true" class="hide" headerClass="hide" />
                <display:column property="effortHours" title="Effort Hours" sortable="true" class="hide" headerClass="hide" />
                <display:column property="ideatedBy" title="Ideated By" sortable="true" />
                <display:column property="implementedBy" title="Implemented By" sortable="true" />
                <display:column property="sme" title="SME" sortable="true" class="hide" headerClass="hide" />                
                <display:column property="initiationDate" title="Initiation Date" sortable="true" />
                <display:column property="completionDate" title="Completion Date" sortable="true" />
                <display:column property="completionPercentage" title="Completion Percentage" sortable="true" class="hide" headerClass="hide" />                
                <display:column property="comments" title="Comments" sortable="true" class="hide" headerClass="hide" />                
                <display:column property="benefitTypes" title="Benefit Types" sortable="true" class="hide" headerClass="hide" />                
                <display:column property="dollarSaving" title="Dollar Saving" sortable="true" class="hide" headerClass="hide" />              
                <display:column property="incidentReduction" title="Incident Reduction/month" sortable="true" class="hide" headerClass="hide" />              
                <display:column property="effortSaving" title="Effort Saving(hrs)/month" sortable="true" class="hide" headerClass="hide" />              
            			 
                <display:column property="updatedBy" title="Updated By" sortable="true" />
                <display:column property="updatedDate" title="Updated On" sortable="true" format="{0,date,MM/dd/yyyy HH:mm:ss}" />
                <display:column property="createdDate" title="Created On" sortable="true" class="hide" headerClass="hide" format="{0,date,MM/dd/yyyy HH:mm:ss}" />
                <display:column property="createdBy" title="Created By" sortable="true" class="hide" headerClass="hide" />
                
                <display:setProperty name="export.csv.filename" value="ProblemManagementExport.csv"/>
                <display:setProperty name="export.pdf.filename" value="ProblemManagementExport.pdf"/>
                <display:setProperty name="export.xml.filename" value="ProblemManagementExport.xml"/>
                <display:setProperty name="export.excel.filename" value="ProblemManagementExport.xls"/>
                <display:setProperty name="export.xml" value="false" />
        		<display:setProperty name="export.csv" value="true" />

                <display:setProperty name="export.pdf" value="false" />
                <display:setProperty name="export.xls" value="true" />
		</display:table>
		</div>
	</form:form>
</div>
</body>