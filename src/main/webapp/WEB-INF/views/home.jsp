<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<% 
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
 	response.setHeader("Pragma","no-cache"); //HTTP 1.0 
 	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server  
%>
<HEAD> 
	<meta http-equiv="cache-control" content="max-age=0" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
	<meta http-equiv="pragma" content="no-cache" />
	<script type="text/javascript">window.history.forward();function noBack(){window.history.forward();}</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/dashboard.js"></script>
	
	<style type="text/css">
		.tablespl{
		 height:12%;
		}
		
		.tbodyspl{
		  overflow-y: scroll;
		  height: 10%;
		  width: 93%;
		  position: absolute;
		}
		
		.trspl {
		width: 100%;
		display: inline-table;
		}
		/* Paste this css to your style sheet file or under head tag */
		/* This only works with JavaScript, 
		if it's not present, don't show loader */
		.no-js #loader { display: none;  }
		.js #loader { display: block; position: absolute; left: 100px; top: 0; }
		.se-pre-con {
			position: fixed;
			left: 0px;
			top: 0px;
			width: 100%;
			height: 100%;
			z-index: 9999;
			background: url(resources/images/Preloader_3.gif) center no-repeat #fff;
		}
	</style>
	
</HEAD> 
<!-- <div class="se-pre-con"></div> -->
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
		
		<c:url var="dashboardAction" value="/dashboard/search"></c:url>
		<form:form action="${dashboardAction}" id="formId" commandName="dashboardSearch" method="GET">
		
		<fieldset>
			<legend>Dashboard</legend>
		</fieldset>
			
<!-- Date Input Section -->		
		<table width="100%" border="0">
			
			<div class="form-group">
				<tr>
					<td width="8%"><form:label path="fromDate" class="control-label"><spring:message text="From Date" /></form:label></td>
					<td>
						<form:input path="fromDate" id="fromDate" class="datepicker form-control" data-toggle="tooltip" title="Defaults to 1st of current month"/>
						<span class="hide">Please select From date</span>
					</td>
					<td width="5%">&nbsp;</td>
					<td width="8%"><form:label path="toDate" class="control-label"><spring:message text="To Date"/></form:label></td>
					<td>
						<form:input path="toDate" id="toDate" class="datepicker form-control" data-toggle="tooltip" title="Defaults to current date"/>
						<span class="hide">Please select To date</span>
					</td>
					<td width="5%">&nbsp;</td>
					<td>
						<input id="searchButton" class="btn btn-primary" type="submit" value="GO" autofocus="autofocus" />
						<input id="exportBtn" type="button" class="btn btn-warning" value="Create MTTR pptx" style="font:8px" onClick="window.location.href='${pageContext.request.contextPath}/dashboard/createPPTX'; return false;"/>
						<span class="hide">Dates are changed. Please click GO first.</span>	
					</td>
				</tr>
			</div>
			
		</table>

<!-- Graph Section -->
		<table width = "100%" border="0">
			
			<tr><td colspan="2"><hr size="6"></td></tr>
			<tr>
				<td width="100%" align="center">
					<img src="${mttrPIEChartFilePath}?timeStamp=${timeStamp}" width="500" height="300" border="0" usemap="#chart">
				</td>	
				<td width="100%" align="center">
					<img src="${mttrPriorityWisePIEChartFilePath}?timeStamp=${timeStamp}" width="500" height="300" border="0" usemap="#chart">
				</td>			
			</tr>
			<tr><td colspan="2"><hr size="6"></td></tr>
			<tr>
				<td width="100%" align="center">
					<img src="${mttrReasonWisePIEChartFilePath}?timeStamp=${timeStamp}" width="500" height="300" border="0" usemap="#chart">
				</td>	
				<td width="100%" align="center">
					<img src="${mttrIncidentCountFileNamePath}?timeStamp=${timeStamp}" width="500" height="300" border="0" usemap="#chart">
				</td>			
			</tr>
			<%-- <tr><td colspan="2"><hr size="6"></td></tr>	
			<tr>
				<td colspan="2" width="100%" align="center">
					<img src="${mttrIncidentCountFileNamePath}" width="1000" height="350" border="0" usemap="#chart">
				</td>				
			</tr> --%>
			<tr><td colspan="2"><hr size="6"></td></tr>	
			<tr>
				<td width="100%" align="center">
					<img src="${mttrP1IncidentCountFileNamePath}?timeStamp=${timeStamp}" width="500" height="300" border="0" usemap="#chart">
				</td>	
				<td width="100%" align="center">
					<img src="${mttrP2IncidentCountFileNamePath}?timeStamp=${timeStamp}" width="500" height="300" border="0" usemap="#chart">
				</td>			
			</tr>
			<tr><td colspan="2"><hr size="6"></td></tr>	
			<tr>
				<td width="100%" align="center">
					<img src="${mttrP3IncidentCountFileNamePath}?timeStamp=${timeStamp}" width="500" height="300" border="0" usemap="#chart">
				</td>	
				<td width="100%" align="center">
					<img src="${mttrP4IncidentCountFileNamePath}?timeStamp=${timeStamp}" width="500" height="300" border="0" usemap="#chart">
				</td>			
			</tr>
			<tr><td colspan="2"><hr size="6"></td></tr>	
			<tr>
				<td width="100%" align="center">
					<img src="${mttrWeeklyPriorityHrsChartFileNamePath}?timeStamp=${timeStamp}" width="500" height="300" border="0" usemap="#chart">
				</td>	
				<td width="100%" align="center">
					<img src="${mttrMonthlyPriorityHrsChartFileNamePath}?timeStamp=${timeStamp}" width="500" height="300" border="0" usemap="#chart">
				</td>			
			</tr>
			<tr><td colspan="2"><hr size="6"></td></tr>	
			<tr>
				<td width="100%" align="center">
					<img src="${outageCountFilePath}?timeStamp=${timeStamp}" width="500" height="300" border="0" usemap="#chart">
				</td>	
				<td width="100%" align="center">
					<img src="${mtbfFilePath}?timeStamp=${timeStamp}" width="500" height="300" border="0" usemap="#chart">
				</td>			
			</tr>
			<%-- 
			<tr>
				<td width="100%" align="center">
					<img src="${pmCountFilePath}" width="1000" height="400" border="0" usemap="#chart">
				</td>				
			</tr>
			<tr><td><hr size="6"></td></tr>	
			
			<tr><td width="100%" align="center"><h3 style="font-weight: bold;">Highlights</h3></td></tr>
			<tr>
				<td width = "100%">
					<c:if test="${! empty highlightDtoList}">
						<table class="table table-striped table-bordered table-hover table-condensed tablespl">
							<thead>
								<tr class="trspl">
									<th width="24%">Capability</th>
									<th width="58%">Description</th>
									<th width="8%">Start Date</th>
									<th width="9%">End Date</th>
								</tr>
							</thead>
							<tbody class="tbodyspl">
								<c:forEach var="highlight" items="${highlightDtoList}">
									<tr class="trspl">
										<td width="24.5%">${highlight.capabilities}</td>
										<td width="59%">${highlight.description}</td>
										<td width="8%">${highlight.fromDate}</td>
										<td width="8%">${highlight.toDate}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<br><br><br><br>
					</c:if>
					<c:if test="${empty highlightDtoList}">
						<span> No Highlights to display !!!</span>
					</c:if>
					
				</td>
			</tr>
			<tr><td width="100%" height="50px;">&nbsp;</td></tr>
			--%>
		</table>
		
		<br><br><br>
		
	</form:form>
</div>
</body>
<div class="se-pre-con"></div>