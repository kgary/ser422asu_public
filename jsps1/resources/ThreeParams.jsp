<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Reading Three Request Parameters</TITLE>
<LINK REL=STYLESHEET
      HREF="JSP-Styles.css"
      TYPE="text/css">
</HEAD>
<%-- Reading request parameters example, straight from notes --%>
<BODY>
<H1>Reading Three Request Parameters</H1>
<UL>
  <LI><B>param1</B>: <%= request.getParameter("param1") %>
  <LI><B>param2</B>: <%= request.getParameter("param2") %>
  <LI><B>param3</B>: <%= request.getParameter("param3") %>
</UL>
</BODY></HTML>