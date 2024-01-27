<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/registration" method="post">
    <label for="name">Name:</label>
    <input type="text" name="name" id="name"/><br>

    <label for="birthday">Birthday:</label>
    <input type="date" name="birthday" id="birthday"/><br>

    <label for="email">Email:</label>
    <input type="text" name="email" id="email"/><br>

    <label for="pwd">Password:</label>
    <input type="password" name="pwd" id="pwd"/><br>

    <select>
        <c:forEach var="role" items="${requestScope.roles}">
            <option label="${role}">${role}</option>
        </c:forEach>
    </select><br>

    <c:forEach var="gender" items="${requestScope.genders}">
        <input type="radio" name="gender" value="${gender}"/> ${gender} <br>
    </c:forEach>

    <input type="submit" value="Send"/>
</form>

<c:if test="${not empty requestScope.errors}">
    <div style="color: red">
        <c:forEach var="error" items="${requestScope.errors}">
            <span>${error.message}</span><br>
        </c:forEach>
    </div>
</c:if>

</body>
</html>
