<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
	<!-- DataTables CSS -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquery.dataTables.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/dataTables.tableTools.css">
	  
	<!-- DataTables -->
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/js/dataTables.tableTools.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/highlightManagement.js"></script>
</head>

<body>
	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

<!-- Add/Update Area -->
		<div class="panel panel-default">
			<div id="Add_Heading" class="panel-heading">
				<h3 class="panel-title"><a style="color: grey;">Add Highlight</a></h3><!-- <span class="pull-right clickable"><i class="glyphicon glyphicon-chevron-up"></i></span> -->
			</div>
			<div class="panel-body">
			
				<p id="highlightEditMessage" class="hide"/>
			
				<c:url var="addAction" value="/manageHighlights/add"></c:url>
		
				<form:form action="${addAction}" id="highlightEditForm" commandName="highlightDTO" method="GET">
				
				<input type=hidden id="highlight_id" name="highlight_id" class="addField" value="0">
		
				<table width="100%" border="0">
					<div class="form-group">
						<tr>
							<td valign="top" width="12%"><form:label path="capabilities" class="control-label"><spring:message text="Capability*" /></form:label></td>
							<td width="18%">
								<form:select id="capabilityMultiselect" path="capabilities" multiple="multiple" class="addField form-control " cssStyle="height:65px" >
									<c:forEach items="${capabilityList}" var="capability" varStatus="num">
										<form:option value="${capability.description}" label="${capability.description} " />
									</c:forEach>
								</form:select>
								<span class="inputFieldErrors hide">Please select Capability</span>
							</td>
							<td width="5%">&nbsp;</td>
							<td width="12%"><form:label path="fromDate" class="control-label"><spring:message text="Start Date*" /></form:label></td>
							<td width="18%">
								<form:input path="fromDate" id="fromDate" class="addField datepicker form-control" />
								<span class="inputFieldErrors hide">Please select From date</span>
							</td>
							<td width="5%">&nbsp;</td>
							<td width="12%"><form:label path="toDate" class="control-label"><spring:message text="End Date*" /></form:label></td>
							<td width="18%">
								<form:input path="toDate" id="toDate" class="addField datepicker form-control"/>
								<span class="inputFieldErrors hide">Please select To date</span>
							</td>
						</tr>
					</div>
		
					<div class="form-group">
						<tr><td colspan="8">&nbsp;</td></tr>
					</div>
					
					<div class="form-group">
						<tr>
							<td valign="top" width="12%"><form:label path="description" class="control-label"><spring:message text="Description*" /></form:label></td>
							<td colspan="7">
								<form:textarea path="description" id="description" class="addField form-control" maxlength="4500"/>
								<span class="inputFieldErrors hide">Please enter Description</span>
							</td>
						</tr>
					</div>
					
					<div class="form-group">
						<tr><td colspan="8">&nbsp;</td></tr>
					</div>
		
				</table>
				
				<div align="center">
					<input id="Add" class="btn btn-primary" type="submit" value="Add" autofocus="autofocus" />
					&nbsp;&nbsp;
					<input id="resetInput" class="btn btn-warning" type="reset" value="Reset" />
				</div>
				
				</form:form>
			
			</div>
		</div>
		
<!-- Search Area --><hr>
		<div class="panel panel-default">
			<div id="Search_Heading" class="panel-heading">
				<h3 class="panel-title"><a style="color: gray;">Search Highlight</a></h3>
				<!-- <span class="pull-right clickable"><i class="glyphicon glyphicon-chevron-up"></i></span> -->
			</div>
			<div class="panel-body">
			
				<p id="highlightTableMessage" class="hide"/>
			
				<c:url var="searchAction" value="/manageHighlights/search"></c:url>
	
				<form:form action="${searchAction}" id="highlightSearchForm" commandName="searchHighlight" method="GET">
				
				<table width="100%" border="0">
					<div class="form-group">
						<tr>
						<td valign="top" width="12%" rowspan="2"><form:label path="capabilities" class="control-label"><spring:message text="Capability" /></form:label></td>
						<td valign="top" width="18%" rowspan="2">
							<form:select id="capabilityMultiselectSearch" name="searchHighlight.capabilities" path="capabilities" multiple="multiple" class="searchField form-control">
								<c:forEach items="${capabilityList}" var="capability" varStatus="num">
									<form:option value="${capability.description}" label="${capability.description}" name="${capability.description}"/>
								</c:forEach>
							</form:select>
						</td>
						<td width="5%">&nbsp;</td>
						<td width="12%"><form:label path="fromDate" class="control-label"><spring:message text="From Date" /></form:label></td>
						<td width="18%">
							<form:input path="fromDate" id="fromDateSearch" name="searchHighlight.fromDate" class="searchField datepicker form-control" />
							<span class="searchFieldErrors hide">Please select From date</span>
						</td>
						<td width="5%">&nbsp;</td>
						<td width="12%"><form:label path="toDate" class="control-label"><spring:message text="To Date" /></form:label></td>
						<td width="18%">
							<form:input path="toDate" id="toDateSearch" name="searchHighlight.toDate" class="searchField datepicker form-control"/>
							<span class="searchFieldErrors hide">Please select To date</span>
						</td>
						</tr>
					</div>
						
					<div class="form-group">
						<tr><td colspan="6">&nbsp;</td></tr>
					</div>
				</table>
				
				<div align="center">
					<input id="Search" class="btn btn-primary" type="submit" value="Search"/>
					&nbsp;&nbsp;
					<input id="resetSearch" class="btn btn-warning" type="reset" value="Reset" />
				</div>
				
				<hr>
				
				<div align="right">
					<input id="Edit" class="btn btn-primary hide" type="submit" value="Edit"/>
					<!--&nbsp;&nbsp; <input id="Generate" class="btn btn-primary" type="submit" value="Generate PPT"/>&nbsp;&nbsp; -->
					<c:if test="${isAdmin}">
						&nbsp;&nbsp;
						<input id="Delete" class="btn btn-warning hide" type="submit" value="Delete"/>&nbsp;&nbsp;
					</c:if>
				</div>
				
				<br>
				
				<input type=hidden id="contextRoot" value='${pageContext.request.contextPath}'>
				
				<!--  Place for data table --> 
				<div id="highlightsTableDiv"></div>
				
				</form:form>
			
			</div>
		</div>
</body>
</html>