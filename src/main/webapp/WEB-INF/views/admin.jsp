<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<!DOCTYPE html>
<html>
<head>
    <%@include file='components/head.jsp' %>
</head>
<body class="theme-<s:theme code="themeName"/>">
<%@include file='components/header.jsp' %>

<div class="main-content">
    <div class="wrapper">
        <div class="col-md-offset-2 col-md-8 col-sm-offset-1 col-sm-10 col-xs-12">
            <div class="panel-heading">
                <div class="panel-title text-center">
                    <h1 class="title text-uppercase">devman</h1>
                    <hr/>
                </div>
            </div>
            <h2 class="text-uppercase">You are on admin page</h2>
        </div>
    </div>
</div>
</body>
</html>