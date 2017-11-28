<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h2>HTTP Status 403 - Access Denied</h2>

	<c:choose>
		<c:when test="${empty username}">
			<h4>You do not have permission to access this page!</h4>
		</c:when>
		<c:otherwise>
			<h4>${username} does not have permission to access this page!<br><br>Please contact System Administrator.</h4>
		</c:otherwise>
	</c:choose>
</div>
</body>
</html>