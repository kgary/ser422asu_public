<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD><TITLE>Bid Accepted</TITLE>
<LINK REL="STYLESHEET"
      HREF="./css/styles.css"
      TYPE="text/css">
</HEAD>
<BODY>
<CENTER>
<TABLE BORDER=5>
  <TR><TH CLASS="TITLE">Bid Accepted</TH></TR>
</TABLE>
<H2>You have bid successfully.</H2>
<UL>
  <LI>User ID: <h:outputText value="#{bidBean2.userID}"/>
  <LI>Keyword: <h:outputText value="#{bidBean2.keyword}"/>
  <LI>Bid Amount: $<h:outputText value="#{bidBean2.bidAmount}"/>
  <LI>Duration: <h:outputText value="#{bidBean2.bidDuration}"/>
</UL>
(Version 2)
</CENTER></BODY></HTML>
</f:view>