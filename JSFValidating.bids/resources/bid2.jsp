<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD><TITLE>Enter Bid</TITLE>
<LINK REL="STYLESHEET"
      HREF="./css/styles.css"
      TYPE="text/css">
</HEAD>
<BODY>
<CENTER>
<TABLE BORDER=5>
  <TR><TH CLASS="TITLE">Enter Bid</TH></TR>
</TABLE>
<P>
<h:form>
  <TABLE>
  <TR>
    <TD>User ID: <h:inputText value="#{bidBean2.userID}" 
                              required="true"
                              id="userID"/></TD>
    <TD><h:message for="userID" styleClass="RED"/></TD></TR>
  <TR>
    <TD>Keyword: <h:inputText value="#{bidBean2.keyword}"
                              required="true" 
                              id="keyword"/></TD>
    <TD><h:message for="keyword" styleClass="RED"/></TD></TR>
  <TR>
    <TD>Bid Amount: $<h:inputText value="#{bidBean2.bidAmount}"
                                  id="amount"/></TD>
    <TD><h:message for="amount" styleClass="RED"/></TD></TR>
  <TR>
    <TD>Duration: <h:inputText value="#{bidBean2.bidDuration}" 
                               id="duration"/></TD>
    <TD><h:message for="duration" styleClass="RED"/></TD></TR>
  <TR><TH COLSPAN=2>
      <h:commandButton value="Send Bid!" 
                       action="#{bidBean2.doBid}"/></TH></TR>
  </TABLE>
</h:form>
</CENTER></BODY></HTML>
</f:view>