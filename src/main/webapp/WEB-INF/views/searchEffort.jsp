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
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/searchEffort.js"></script>
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
	<c:url var="searchAction" value="/searchEffort/search"></c:url>

	<form:form action="${searchAction}" id="formId" commandName="searchEffort" method="GET">
		<fieldset>
			<legend>Search Effort</legend>
		</fieldset>
			
		
		<table width="100%" border="0">
			
			<div class="form-group">
				<tr>
					<td rowspan=3 valign="top" width="12%"><form:label path="applications" class="control-label"><spring:message text="Application" /></form:label></td>
					<td rowspan=3 width="18%">
						<form:select id="applicationsMultiselect" path="applications" multiple="multiple" class="form-control" cssStyle="height:135px">
						<c:forEach items="${applicationList}" var="application" varStatus="num">
							<form:option value="${application.appName}" label="${application.appName} " />
						</c:forEach>
						</form:select>
					</td>
					<td width="5%">&nbsp;</td>
					<td valign="top" width="12%"><form:label path="effortCategory" class="control-label"><spring:message text="Effort Category" /></form:label></td>
					<td valign="top" width="18%">
						<form:select id="categoryMultiselect" path="effortCategory" multiple="multiple" class="form-control" cssStyle="height:75px" >
						<c:forEach items="${categoryList}" var="category" varStatus="num">
							<form:option value="${category.description}" label="${category.description} " />
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
					<td><form:label path="snowId" class="control-label"><spring:message text="SNOW Id" /></form:label></td>
					<td>
						<form:input path="snowId" size="8" class="form-control" />
						<span class="hide"></span>
					</td>
					<td>&nbsp;</td>
					<td><form:label path="effortId" class="control-label"><spring:message text="Effort Id" /></form:label></td>
					<td>
						<form:input path="effortId" size="8" class="form-control"/>
						<span class="hide"></span>
					</td>
				</tr>
			</div>
			
			<div class="form-group">
				<tr><td colspan="8">&nbsp;</td></tr>
			</div>
			
			<div class="form-group">
				<tr>
					<td><form:label path="owner" class="control-label"><spring:message text="Owner*" /></form:label></td>
					<c:if test="${isAdmin=='true'}">
					<td>
						<form:input path="owner" size="8" class="form-control"/>
						<br><span class="hide">Please select Owner</span>
					</td>
					</c:if>
					<c:if test="${isAdmin=='false'}">
					<td>
						<form:input path="owner" size="8" class="form-control" readOnly="true"/>
						<br><span class="hide">Please select Owner</span>
					</td>
					</c:if>
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
			<input id="resetEffortSearch" class="btn btn-warning" type="reset" value="Reset" />
		</div>
		
		<hr size="4">
		
<!-- Table Display Section -->
		<fieldset>
			<legend>Effort List</legend>
		</fieldset>
		<div class="panel-body">
		
		<display:table id="data" name="effortList" pagesize="10" requestURI="${pageContext.request.contextPath}/searchEffort/search" export="true" class="table table-bordered table-striped" >
                
                <display:column title="Id" sortable="true" media="html">
                <c:set var="id" value="${data.id}" />  
        				<a href="${pageContext.request.contextPath}/editEffort/${id}">${id}</a>   
    			</display:column>
    			<display:column property="id" title="Effort Id" class="hide" headerClass="hide" />
    			
                <display:column property="application" title="Application" sortable="true" maxWords="3"></display:column>
                <display:column property="category" title="Effort Category" sortable="true" maxWords="3" />
                <display:column property="priority" title="Priority" sortable="true"/>
                <%-- 
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
    			--%>
    			
    			<display:column property="complexity" title="Complexity" sortable="true" />
    			 
                <display:column property="snowNumber" title="SNOW Id" sortable="true" />
                <display:column property="effortDescription" title="Effort Description" sortable="true" />
                <display:column property="effortHours" title="Effort (hours)" sortable="true" />
                <display:column property="createdBy" title="Created By" sortable="true" class="hide" headerClass="hide" />
                <display:column property="effortDate" title="Effort Date" sortable="true" format="{0,date,MM/dd/yyyy}" />
                <display:column property="effortDate" title="Effort Month" sortable="true" format="{0,date,MMM}" class="hide" headerClass="hide" />
                <display:column property="effortDate" title="Effort Year" sortable="true" format="{0,date,yyyy}" class="hide" headerClass="hide" />
                <display:column property="createdDate" title="Created Date" sortable="true" format="{0,date,MM/dd/yyyy HH:mm:ss}" class="hide" headerClass="hide"  />                
                <display:column property="updatedBy" title="Updated By" sortable="true" class="hide" headerClass="hide" />
                <display:column property="updatedDate" title="Updated Date" sortable="true" format="{0,date,MM/dd/yyyy HH:mm:ss}" />
                
                <display:setProperty name="export.csv.filename" value="EffortExport.csv"/>
                <display:setProperty name="export.pdf.filename" value="EffortExport.pdf"/>
                <display:setProperty name="export.xml.filename" value="EffortExport.xml"/>
                <display:setProperty name="export.excel.filename" value="EffortExport.xls"/>
                <display:setProperty name="export.xml" value="false" />
        		<display:setProperty name="export.csv" value="true" />
                <display:setProperty name="export.pdf" value="false" />
                <display:setProperty name="export.xls" value="true" />
		</display:table>
		</div>
	</form:form>
</div>
</body>
