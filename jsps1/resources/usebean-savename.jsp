<jsp:useBean id="var" class="edu.asupoly.cst425.UserData" scope="request"/>
<jsp:setProperty name="var" property="*"/> 
<html>
<body>
UseBean has grabbed data - you should also see values on the
<a HREF="usebean-nextpage.jsp">next page</a>
<p>
Name: <%= var.getUsername() %><br>
Var: <%= var.toString() %><br>

<% session.setAttribute("var", var); %>
</body>
</html>
