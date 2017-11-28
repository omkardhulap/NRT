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
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/searchOutage.js"></script>
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
	<c:url var="searchAction" value="/searchOutage/search"></c:url>

	<form:form action="${searchAction}" id="formId" commandName="searchOutage" method="GET">
		<fieldset>
			<legend>Search Outage</legend>
		</fieldset>
			
		
		<table width="100%" border="0">
			
			<div class="form-group">
				<tr>
					<td rowspan=3 valign="top" width="12%"><form:label path="applications" class="control-label"><spring:message text="Application" /></form:label></td>
					<td rowspan=3 width="18%">
						<form:select id="plannedMultiselect" path="applications" multiple="multiple" class="form-control" cssStyle="height:135px">
						<c:forEach items="${applicationList}" var="application" varStatus="num">
							<form:option value="${application.appName}" label="${application.appName} " />
						</c:forEach>
						</form:select>
					</td>
					<td width="5%">&nbsp;</td>
					<td valign="top" width="12%"><form:label path="capabilities" class="control-label"><spring:message text="Capability" /></form:label></td>
					<td valign="top" width="18%">
						<form:select id="capabilityMultiselect" path="capabilities" multiple="multiple" class="form-control" cssStyle="height:75px" >
						<c:forEach items="${capabilityList}" var="capability" varStatus="num">
							<form:option value="${capability.description}" label="${capability.description} " />
						</c:forEach>
						</form:select>
					</td>
					<td width="5%">&nbsp;</td>
					<!-- Add Tag for Engin -->
					<td valign="top" width="12%"></td>
					<td valign="top" width="18%"></td>
				</tr>
			</div>
			
			<div class="form-group">
				<tr><td colspan="6">&nbsp;</td></tr>
			</div>
			
			<div class="form-group">
				<tr>
					<td>&nbsp;</td>
					<td><form:label path="snowId" class="control-label"><spring:message text="Snow Id" /></form:label></td>
					<td>
						<form:input path="snowId" size="8" class="form-control" />
						<span class="hide"></span>
					</td>
					<td>&nbsp;</td>
					<td><form:label path="outageId" class="control-label"><spring:message text="Outage Id" /></form:label></td>
					<td>
						<form:input path="outageId" size="8" class="form-control"/>
						<span class="hide"></span>
					</td>
				</tr>
			</div>
			
			<div class="form-group">
				<tr><td colspan="8">&nbsp;</td></tr>
			</div>
			
			<div class="form-group">
				<tr>
				<td><form:label path="typeOfOutage" class="control-label"><spring:message text="Outage Type*" /></form:label></td>
				<td>
					<form:checkbox id="chkPlanned"  class="" path="typeOfOutage" name="chk[]" value="P" />&nbsp;&nbsp;Planned
					&nbsp;&nbsp;&nbsp;  
					<form:checkbox id="chkUnPlanned" class="" path="typeOfOutage" name="chk[]" value="U" />&nbsp;&nbsp;Unplanned  
					<br><span class="hide">Please select Outage Type</span>
				</td>
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
			<input id="resetOutageSearch" class="btn btn-warning" type="reset" value="Reset" />
		</div>
		
		<hr size="4">
		
<!-- Table Display Section -->
		<fieldset>
			<legend>Outage List</legend>
		</fieldset>
		<div class="panel-body">
		
		<display:table id="data" name="listOutages" pagesize="10" requestURI="${pageContext.request.contextPath}/searchOutage/search" export="true" class="table table-bordered table-striped" >
                
                <display:column title="Id" sortable="true" media="html">
                <c:set var="id" value="${data.id}" />  
        				<a href="${pageContext.request.contextPath}/editOutage/${id}">${id}</a>   
    			</display:column>
    			<display:column property="id" title="Outage Id" class="hide" headerClass="hide" />
    			
                <display:column property="applications" title="Applications" sortable="true" maxWords="3"></display:column>
                <display:column property="associated_capabilities" title="Capabilities" sortable="true" maxWords="3" />
                <display:column property="description" title="Description" sortable="true" class="hide" headerClass="hide" />
                
                <display:column title="Type" sortable="true"  media="html">
                	<c:set var="outageType" value="${data.outageType}" />
                	<c:choose>
					    <c:when test="${outageType == 'UnPlanned'}">
					      	${outageType}<a href="${pageContext.request.contextPath}/createEOSPptx/${id}" target="_blank"><br>EOS - PPT</a>
					      	<!-- <a href="${pageContext.request.contextPath}/updateAAR/${id}"><br>AAR</a> -->
					    </c:when>					    
					    <c:otherwise>
					        ${outageType}
					    </c:otherwise>
					</c:choose>		   
    			</display:column>
    			<display:column property="outageType" title="Type" class="hide" headerClass="hide" />
    			 
                <display:column property="businessAffected" title="Business Affected" sortable="true" class="hide" headerClass="hide" />
                <display:column property="outageRequired" title="Outage Required?" sortable="true" />
                <display:column property="deploymentStartDate" title="Start Time" sortable="true" format="{0,date,MM/dd/yyyy HH:mm:ss}" />
                <display:column property="deploymentEndDate" title="End Time" sortable="true" format="{0,date,MM/dd/yyyy HH:mm:ss}" />
                <display:column property="outageDuration" title="Duration (mins)" sortable="true" />          
                <display:column property="snowIds" title="Snow Ids" sortable="true" maxWords="3" />
                <display:column property="priority" title="Priority" sortable="true" />
                <display:column property="updatedBy" title="Updated By" sortable="true" />
                <display:column property="updatedDate" title="Updated On" sortable="true" format="{0,date,MM/dd/yyyy HH:mm:ss}" />
                <display:column property="createdDate" title="Created On" sortable="true" class="hide" headerClass="hide" format="{0,date,MM/dd/yyyy HH:mm:ss}" />
                <display:column property="createdBy" title="Created By" sortable="true" class="hide" headerClass="hide" />
                <display:column property="scope" title="Scope" sortable="true" class="hide" headerClass="hide" />
                <display:column property="stOwner" title="ST Owner" class="hide" headerClass="hide" sortable="true" />
                <display:column property="approvedBy" title="Approved By" sortable="true" class="hide" headerClass="hide" />
                <display:column property="approvalDate" title="Approval Date" sortable="true" class="hide" headerClass="hide" format="{0,date,MM/dd/yyyy HH:mm:ss}" /> 
                <display:column property="executiveSummary" title="Executive Summary" sortable="true" class="hide" headerClass="hide" />   
                <display:column property="dueTo" title="Due To" sortable="true" class="hide" headerClass="hide" /> 
                <display:column property="aarOwner" title="AAR Owner" sortable="true" class="hide" headerClass="hide" />
                <display:column property="aarDate" title="AAR Date" sortable="true" class="hide" headerClass="hide" format="{0,date,MM/dd/yyyy HH:mm:ss}" />
                <display:column property="database" title="Database" sortable="true" class="hide" headerClass="hide" />
                <display:column property="platform" title="Platform" sortable="true" class="hide" headerClass="hide" /> 
                <display:column property="technicalIssues" title="Technical Issues" sortable="true" class="hide" headerClass="hide" />
                <display:column property="resolution" title="Resolution" sortable="true" class="hide" headerClass="hide" />
                <display:column property="rootCause" title="Root Cause" sortable="true" class="hide" headerClass="hide" />
                <display:column property="vendorAccountable" title="Vendor Accountable" sortable="true" class="hide" headerClass="hide" />               
                
                <display:setProperty name="export.csv.filename" value="OutageExport.csv"/>
                <display:setProperty name="export.pdf.filename" value="OutageExport.pdf"/>
                <display:setProperty name="export.xml.filename" value="OutageExport.xml"/>
                <display:setProperty name="export.excel.filename" value="OutageExport.xls"/>
                <display:setProperty name="export.xml" value="false" />
        		<display:setProperty name="export.csv" value="true" />
                <display:setProperty name="export.pdf" value="false" />
                <display:setProperty name="export.xls" value="true" />
		</display:table>
		</div>
	</form:form>
</div>
</body>
