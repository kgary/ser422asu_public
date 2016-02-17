<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<title>confirm</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<body>
<p>Purchase Successful</p>

<table border="0">
	<tbody>
		<tr>
			<td>Customer:</td>
			<td>${sessionScope.purchaseName}</td>
		</tr>
		<tr>
			<td><span>Card:</span></td>
			<td>${param.Creditcard}</td>
		</tr>
		<tr>
			<td>Card number:</td>
			<td>${param.Cardnumber}</td>
		</tr>
		<tr>
			<td>Amount:</td>
			<td>$${sessionScope.purchaseTotal}</td>
		</tr>
		<tr>
			<td>Express Delivery:</td>
			<td>
				<c:if test="${param.expressdelivery == null}">No</c:if>
				<c:if test="${param.expressdelivery != null}">Yes</c:if>
			</td>
		</tr>
	</tbody>
</table>
</body>
</html:html>
