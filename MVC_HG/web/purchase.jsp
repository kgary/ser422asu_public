<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>validate</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<body>
<jsp:useBean id="user" class="edu.asupoly.ser422.mvchg.model.User" scope="session" />
<jsp:useBean id="bookDAO" class="edu.asupoly.ser422.mvchg.service.BookDAO" scope="page" />

<c:set var="total" value="0" />

<h3>Thank you <jsp:getProperty name="user" property="displayName" /></h3>
<p>Your Order</p>
<table border="2" style="padding: 1in" cellpadding="2">
	<tbody>
		<tr>
			<td><b>Book</b></td>
			<td><b>Quantity</b></td>
			<td><b>Unit Cost</b></td>
			<td><b>Total Cost</b></td>
		</tr>
		<c:forEach items="${paramValues.Books}" var="bookid">
			<c:set var="book" value="${bookDAO.allBooks[bookid]}" />
			<tr>
				<td align="left"><c:out value="${book.name}" /></td>
				<td align="right">${param.Quantity}</td>
				<td align="right">$${book.price}</td>
				<td align="right"><c:out value="$${book.price * param.Quantity}" /> </td>
			</tr>
			<c:set var="total" value="${total + book.price * param.Quantity}"  />
		</c:forEach>
	</tbody>
</table>

<c:set var="purchaseTotal" value="${total}" scope="session"/>
<c:set var="purchaseName" value="${user.displayName}" scope="session"/>

<p>Total cost is $<c:out value="${sessionScope.purchaseTotal}" /></p>
<p><br>Please enter your Credit Card information:
</p>
<form method="post" action="controller?action=confirm">
<table border="0">

	<tbody>
		<tr>
			<td>Credit Card</td>
			<td width="92"><select name="Creditcard">
				<option>MasterCard</option>
				<option>Visa</option>
				<option>Amex</option>
			</select></td>
		</tr>
		<tr>
			<td>Card Number</td>
			<td width="92"> <input type="text" name="Cardnumber" size="16"></td>			
		</tr>
		<tr>
			<td>Express Delivery</td>
			<td width="92"><input type="checkbox" name="expressdelivery"></td>
		</tr>
	</tbody>
</table>
<input type="submit" name="Purchase" value="Purchase">
</form></body>
</html>
