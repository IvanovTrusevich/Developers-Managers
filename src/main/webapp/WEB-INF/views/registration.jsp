<%--
  Created by IntelliJ IDEA.
  User: Илья
  Date: 13.05.2017
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:set var="page" value="registration"/>
<!DOCTYPE html>
<html>
    <%@include file='components/head.jsp'%>
    <style>
        .error {
            color: red;
        }
    </style>
    <body>
        <%@include file='components/header.jsp'%>

        <div class="inner-content">
            <div class="modal-window">
                <div class="modal-content">
                    <security:authentication property="principal.username" />
                </div>
            </div>
        </div>

        <%@include file='components/footer.jsp'%>
    </body>
</html>
