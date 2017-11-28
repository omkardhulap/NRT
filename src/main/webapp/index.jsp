<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
	<meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
	<title>Login Page</title>
	<!-- Bootstrap -->
	<link href="resources/css/bootstrap.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/css/index.css"	rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/css/jquery-ui.min.css" rel="stylesheet">
</head>

<body onload='document.loginForm.username.focus();'>

	<div class="container" style="vertical-align: middle; padding-top: 10%;">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title" style="font-size: 100%"><b>Nike Reporting</b></div>
					<div
						style="float: right; font-size: 80%; position: relative; top: -10px">
						<a href="mailto:Sachin.Ainapure@nike.com ?
				cc=omkar.dhulap@nike.com; vishal.lalwani@nike.com &amp;
				subject=Nike%20Reporting%20Password%20Recovery &amp;
				body=Please%20mention%20the%20details." style="font-weight: bold;">Forgot password ?</a>
					</div>
				</div>

				<div style="padding-top: 30px" class="panel-body" align="center">
					<c:if test="${not empty error}">
						<div class="error">${error}</div>
					</c:if>
					<c:if test="${not empty msg}">
						<div class="msg">${msg}</div>
					</c:if>

					<form id="loginForm" class="form-horizontal" action="<c:url value='/j_spring_security_check' />" method='POST'>

						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon">
								<i class="ui-icon ui-icon-person"></i></span> 
								<input id="username" type="text" class="form-control" name="username" value="" placeholder="username" maxlength="45">
						</div>

						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i class="ui-icon ui-icon-locked"></i></span> 
							<input id="password" type="password" class="form-control" name="password" placeholder="password" maxlength="45">
						</div>

						<div style="margin-top: 10px" class="form-group" align="center">
							<!-- Button -->
							<div class="col-sm-12 controls">
								<input name="submit" type="submit" value="Login" autofocus="autofocus" class="btn btn-success" /> 
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							</div>
						</div>

						<div class="form-group" align="center">
							<div class="col-md-12 control">
								<div
									style="border-top: 1px solid #888; padding-top: 15px; font-size: 85%">
									Don't have an account ? 
									<b><a href="#" onclick="document.location.href='${pageContext.request.contextPath}/signup';">
									Sign Up Here !</a></b>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
<footer>
	<div align="center"
		style="padding-top: 10px; padding-bottom: 0px; height: 40px; bottom: 0; color: grey">
		Powered by <a href="http://www.infosys.com/"
			style="font-weight: bold;">Infosys</a>
	</div>
</footer>
</html>