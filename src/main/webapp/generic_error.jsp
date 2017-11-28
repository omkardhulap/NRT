<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<!-- <style type="text/css">
body
        {
            background:url('/NikeReportingTool/resources/images/Nike_droplets_on_leather_1920x1200.jpg') no-repeat center center fixed;
            background-size: cover;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            margin: 0;
            padding: 0;
        }
</style>
-->
<title>System Error</title>
</head>
<body>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<br><br>
		<h2 style="color: black; padding-left: 10px;">Oops...System Issue!!! <a href="/NikeReportingTool/">Please try again</a> or contact System  
		<a href="mailto:Sachin.Ainapure@nike.com?
				cc=omkar.dhulap@nike.com; vishal.lalwani@nike.com &amp;
				subject=Nike%20Reporting%20Tool%20Issue &amp;
				body=Please%20Copy%20the%20Error%20Trace%20Here..">Administrator</a>.</h2>
		<hr>
		<br><br>
		<h4>ErrorMessage:</h4>
		<c:if test="${not empty errMsg}">
			<h4 style="color: black; padding-left: 10px;">${errMsg}</h4>
		</c:if>				
</body>
</html>