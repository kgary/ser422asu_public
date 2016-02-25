<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<!-- Dummy JSP to demonstrate Ant build process -->
  <title>SampleWebApplication: Logged in</title>
</head>
<body>
<jsp:useBean id="user" class="edu.asupoly.ser422.mvchg.model.User" scope="session" />

<h1>SampleWebApplication: Logged in</h1>
<br/>
<br/>
Welcome <jsp:getProperty name="user" property="displayName" />, you have successfully logged in!
Click <a href="controller?action=list">here</a> to order some books!

</body>
</html>
