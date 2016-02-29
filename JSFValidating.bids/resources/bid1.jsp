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
  <h:outputText value="#{bidBean1.errorMessages}"
                escape="false"/>
  <TABLE>
  <TR>
    <TD>User ID: 
    <h:inputText value="#{bidBean1.userID}"/></TD></TR>
  <TR>
    <TD>Keyword: 
    <h:inputText value="#{bidBean1.keyword}"/></TD></TR>
  <TR>
    <TD>Bid Amount: 
    $<h:inputText value="#{bidBean1.bidAmount}"/></TD></TR>
  <TR>
    <TD>Duration: 
    <h:inputText value="#{bidBean1.bidDuration}"/></TD></TR>
  <TR><TH>
      <h:commandButton value="Send Bid!" 
                       action="#{bidBean1.doBid}"/></TH></TR>
  </TABLE>
</h:form>
</CENTER></BODY></HTML>
</f:view>