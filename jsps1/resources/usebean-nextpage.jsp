<jsp:useBean id="var" class="edu.asupoly.cst425.UserData" scope="session"/> 
<html>
<body>
You entered<br>
Name: <%= var.getUsername() %><br>
Email: <%= var.getEmail() %><br>
Age: <%= var.getAge() %><br>
Var: <%= var.toString() %><br>
</body>
</html>
