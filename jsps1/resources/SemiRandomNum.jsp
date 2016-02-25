<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- 
One of four random-number examples. This one uses declarations. 
-->
<HTML>
<HEAD>
<TITLE>Semi-Random Number</TITLE>
<LINK REL=STYLESHEET
      HREF="JSP-Styles.css"
      TYPE="text/css">
</HEAD>
<%-- Example with a declaration and an expression --%>
<BODY>
<%! 
private int randomNum = coreservlets.RanUtilities.randomInt(10); 
%>
<H1>Semi-Random Number:<BR><%= randomNum %></H1>
</BODY>
</HTML>