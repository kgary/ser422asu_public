<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD><TITLE>Sample (Classic Syntax)</TITLE></HEAD>
<BODY BGCOLOR="#FDF5E6">
<CENTER>
<H1>Sample (Classic Syntax)</H1>

<%-- Simple sample JSP with expressions, scriptlet, and declaration. Inspect the resulting servlet --%>
<H2>Num1: <%= Math.random()*10 %></H2>
<% double num2 = Math.random()*100; %>
<H2>Num2: <%= num2 %></H2>
<%! private double num3 = Math.random()*1000; %>
<H2>Num3: <%= num3 %></H2>

</CENTER>
</BODY></HTML>