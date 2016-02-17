<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>index</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<body>
<br/>
<jsp:useBean id="bookDAO" class="edu.asupoly.ser422.t6mvc2.service.BookDAO" scope="request" />

Please the following information:
<jsp:useBean id="user" class="edu.asupoly.ser422.t6mvc2.model.User" scope="session" />
<form action="${pageContext.request.contextPath}/controller?action=purchase" method="post">
<!--
<h:form>
  -->

<table border="0">
	<tbody>
		<tr>
			<td>Name:</td><td><jsp:getProperty name="user" property="displayName" /></td>
		</tr>
		<tr>
			<td>Quantity</td>
			<td><input type="text" name="Quantity" size="4" maxlength="12"></td>
		</tr>
		<tr>
			<td>Books</td>
			<td>
			<select size="4" name="Books" multiple="multiple">
			  <c:forEach items="${bookDAO.allBooks}" var="book">
      			  <option value="${book.value.id}">${book.value.name}</option>
 			  </c:forEach>
					
			</select>
			</td>
		</tr>
	</tbody>
</table>
<br>
<!-- 
<h:commandButton action="purchase.jsp"/>
</h:form>
 -->

	<input type="submit" name="SUBMIT"/>
</form> 

</body>
</html>
