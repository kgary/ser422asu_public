<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- 
Example of a JSP scriptlet.
-->
<HTML>
<HEAD>
  <TITLE>Color Testing</TITLE>
</HEAD>
<%-- example to show use of the getParameter method in a JSP with an expression --%>
<% 
String bgColor = request.getParameter("bgColor");
if ((bgColor == null) || (bgColor.trim().equals(""))) { 
  bgColor = "WHITE"; 
} 
%>
<BODY BGCOLOR="<%= bgColor %>">
<H2 ALIGN="CENTER">Testing a Background of "<%= bgColor %>".</H2>
</BODY></HTML>