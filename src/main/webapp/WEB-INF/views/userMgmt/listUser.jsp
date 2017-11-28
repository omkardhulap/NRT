<%-- 
    Document   : listUser
    Created on : Dec 9, 2014, 4:21:36 PM
    Author     : Omkar_Dhulap
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
	<head>
	
	<!-- DataTables CSS -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquery.dataTables.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/dataTables.tableTools.css">
	  
	<!-- DataTables -->
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.js"></script>
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/js/dataTables.tableTools.js"></script>
	
	<script type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/resources/js/listUser.js"></script>
	
	</head>
	<body>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<fieldset>
				<legend>Nike Reporting Users</legend>
			</fieldset>

				<input type=hidden id="contextRoot" value='${pageContext.request.contextPath}'>
				
				<!--  Place for data table --> 
				<div id="listusersTableDiv"></div>
				
			</div>
	</body>
</html>
