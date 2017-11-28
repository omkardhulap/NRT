<%-- 
    Document   : userreg
    Created on : Jul 21, 2014, 5:22:56 PM
    Author     : vipul_raut
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<% 
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
 	response.setHeader("Pragma","no-cache"); //HTTP 1.0 
 	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server  
%>
<head>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/userManagement.js"></script>
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
		<c:if test="${not empty message}">
			<p class="text-success bg-success">${message}</p>
		</c:if>
		<c:if test="${not empty errorMessage}">
			<p class="text-error">${errorMessage}</p>
		</c:if>
	</div>

	<c:url var="addAction" value="/user/add"></c:url>

	<form:form action="${addAction}" modelAttribute="Nrt_user" method="POST">
		
		<fieldset>
			<c:if test="${Nrt_user.id != 0 && Nrt_user.id != null}">
				<legend>Update User</legend>
			</c:if>
			<c:if test="${Nrt_user.id == 0 || Nrt_user.id == null}">
				<legend>Add User</legend>
			</c:if>
		</fieldset>
		
		<table width="100%" border="0">
			
			<div  class="form-group">
				<tr>
				<td width="12%"><form:label path="nikeid" class="control-label"><spring:message text="User Id*" /></form:label></td>
				<td>
					<c:if test="${Nrt_user.id != 0 && Nrt_user.id != null}">
						<form:input  path="nikeid" class=" form-control" maxlength="45" readOnly="true"/>
					</c:if>
					<c:if test="${Nrt_user.id == 0 || Nrt_user.id == null}">
						<form:input  path="nikeid" class=" form-control" maxlength="45" placeholder="NTID"/>
						<span class="hide">Please enter a valid User Id</span>
					</c:if>					
				</td>
				<td width="4%">&nbsp;</td>
				<td width="12%"><form:label path="id" class="control-label"><spring:message text="Id Number" /></form:label></td>
				<td>
					<c:if test="${Nrt_user.id != 0 && Nrt_user.id != null}">
						<form:input path="id" readonly="true" size="8" class=" form-control" disabled="true" cssStyle="font-style: italic;"/>
					</c:if>
					<c:if test="${Nrt_user.id == 0 || Nrt_user.id == null}">
						<form:input path="id" readonly="true" size="8" class=" form-control" disabled="true" value="New User" cssStyle="font-style: italic;"/>
					</c:if>
					
			 		<form:hidden path="id" />
				</td>
				</tr>
			</div>
			
			<div class="form-group">
				<tr><td colspan="5">&nbsp;</td></tr>
			</div>
			
			<div class="form-group">
				<tr>
				<td><form:label path="fname" class="control-label"><spring:message text="First Name*" /></form:label></td>
				<td>
					<form:input  path="fname" class=" form-control" maxlength="45" placeholder="first name"/>
					<span class="hide">Please enter a valid First Name</span>
				</td>
				<td>&nbsp;</td>
				<td><form:label path="lname" class="control-label"><spring:message text="Last Name*" /></form:label></td>
				<td>
					<form:input  path="lname" class=" form-control" maxlength="45" placeholder="last name"/>
					<span class="hide">Please enter a valid Last Name</span>
				</td>
				</tr>
			</div>
			
			<div class="form-group">
				<tr><td colspan="5">&nbsp;</td></tr>
			</div>
			
			<div  class="form-group">
				<tr>
				<td><form:label path="password" class="control-label"><spring:message text="Password*" /></form:label></td>
				<td>
					<form:input  path="password" class=" form-control" type="password" maxlength="45" placeholder="password"/>
					<span class="hide">Please enter a valid Password</span>
				</td>
				<td>&nbsp;</td>
				<td><form:label path="password" class="control-label"><spring:message text="Retype Password*" /></form:label></td>
				<td>
					<input id="repassword" class=" form-control" type="password" maxlength="45" placeholder="password"/>
					<span class="hide">Password doesn't match</span>
				</td>
				</tr>
			</div>
			
			<div class="form-group">
				<tr><td colspan="5">&nbsp;</td></tr>
			</div>
			
			<div  class="form-group">
				<tr>
				<td><form:label path="nikeEmail" class="control-label"><spring:message text="Nike Email*" /></form:label></td>
				<td>
					<form:input  path="nikeEmail" class=" form-control" type="email" placeholder="Email" maxlength="60"/>
					<span class="hide">Please enter a valid Nike Email</span>
				</td>
				<td>&nbsp;</td>
				<td><form:label path="infyEmail" class="control-label"><spring:message text="Infosys Email*" /></form:label></td>
				<td>
					<form:input  path="infyEmail" class=" form-control" type="email" placeholder="Email" maxlength="60"/>
					<span class="hide">Please enter a valid Infosys Email</span>
				</td>
				</tr>
			</div>
				
			<div class="form-group">
				<tr><td colspan="5">&nbsp;</td></tr>
			</div>
			
			<div  class="form-group">
				<tr>
				<td><form:label path="role" class="control-label"><spring:message text="Role*" /></form:label></td>
				<td>
					<c:if test="${isAdmin}">
						<form:select path="role" class=" form-control" >
							<form:option value="User">User</form:option>
							<form:option value="Manager">Manager</form:option>
							<form:option value="Administrator">Administrator</form:option>
						</form:select>
					</c:if>
					<c:if test="${!isAdmin}">
						<form:select path="role" class=" form-control" disabled="true">							
							<form:option value="User">User</form:option>
							<form:option value="Manager">Manager</form:option>
							<form:option value="Administrator">Administrator</form:option>
						</form:select>
						<form:hidden path="role" class="form-control" readOnly="true"/>
					</c:if>					
					<span class="hide">Please enter a valid Role</span>
				</td>
				<td>&nbsp;</td>
				<td><form:label path="status" class="control-label"><spring:message text="Status*" /></form:label></td>
				<td>
					<form:select path="status" class="form-control">
						<form:option value="Y">Active</form:option>
						<form:option value="N">Inactive</form:option>
					</form:select>
					<span class="hide">Please enter a valid Staus</span>
				</td>				
				</tr>				
			</div>
			
			<div class="form-group">
				<tr><td colspan="5">&nbsp;</td></tr>
				<tr><td colspan="5">&nbsp;</td></tr>
			</div>
			
		</table>
		 
		<%--<div>
				<form:label path="infyid" class="control-label"><spring:message text="Infosys Id" /></form:label>
				<form:input  path="infyid" class=" form-control" maxlength="45"/>
				<span class="hide">Please enter a valid Infosys Id</span>
			</div>--%> 
		<form:hidden  path="infyid" class=" form-control" maxlength="45"/>
		
		<div align="center">
			<c:if test="${Nrt_user.id != 0 && Nrt_user.id != null}">
				<input id="editUser" class="btn btn-primary" type="submit" value="<spring:message text="Update"/>" autofocus="autofocus"/>						
				&nbsp;&nbsp;
				<input id="cancelUserEdit" class="btn btn-warning" type="submit" value="Cancel" onClick="window.location.href='${pageContext.request.contextPath}/listusers'; return false;"/>
			</c:if>
			<c:if test="${Nrt_user.id == 0 || Nrt_user.id == null}">
				<input id="addUser" class="btn btn-primary" type="submit" value="<spring:message text="Add"/>" autofocus="autofocus"/>
				&nbsp;&nbsp;
				<input id="resetUser" class="btn btn-warning" type="reset" value="Reset"/>
			</c:if>					
		</div>
		
		

	</form:form>

</div>

</body>
