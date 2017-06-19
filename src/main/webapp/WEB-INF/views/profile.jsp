<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>--%>

<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
    <%--<%@include file='components/head.jsp' %>--%>
<%--</head>--%>
<%--<body class="theme-<s:theme code="themeName"/>">--%>
<%--<%@include file='components/header.jsp' %>--%>

<%--<div class="main-content">--%>
    <%--<div class="wrapper">--%>
        <%--<div class="col-md-offset-2 col-md-8 col-sm-offset-1 col-sm-10 col-xs-12">--%>
            <%--<div class="panel-heading">--%>
                <%--<div class="panel-title text-center">--%>
                    <%--<h1 class="title text-uppercase">devman</h1>--%>
                    <%--<hr/>--%>

                    <%--<div>--%>
                            <%--<p>${user.username}</p>--%>
                            <%--<p>${user.firstName}</p>--%>
                            <%--<p>${user.lastName}</p>--%>
                            <%--<p>${user.middleName}</p>--%>
                            <%--<c:forEach items="${news}" var="pieceOfNews">--%>
                                <%--<p>${pieceOfNews}</p>--%>
                                <%--<p></p>--%>
                                <%--<p></p>--%>
                            <%--</c:forEach>--%>


                            <%--<img src="user.photo.thumbnail" alt="альтернативный текст">--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<h2 class="text-uppercase">You are on user page</h2>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>
<%--</body>--%>
<%--</html>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file='components/head.jsp' %>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="<s:url value="/res/libs/git/dist/github.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<s:url value="/res/libs/elfinder/css/elfinder.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<s:url value="/res/libs/elfinder/css/theme.css"/>">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
    <title>${user.username}</title>
</head>
<body class="profile-page theme-<s:theme code="themeName"/>">
<%@include file='components/header.jsp' %>
<div class="main-content">
    <div class="wrapper">
        <div class="col-md-3 col-sm-4 col-xs-12">
            <c:choose>
                <c:when test="${user.id == authId}">
                    <a href="<s:url value="/settings"/>"><img src="${user.photo.image}" class="img-thumbnail img-responsive center-block" alt="Cinque Terre"></a>
                </c:when>
                <c:otherwise>
                    <img src="${user.photo.image}" class="img-thumbnail img-responsive center-block" alt="Cinque Terre">
                </c:otherwise>
            </c:choose>
            <div class="text-center">
                <h3>${user.username} </h3>
                <h4>${user.firstName} ${user.middleName} ${user.lastName}</h4>
                <i class="fa fa-circle" type="button" data-toggle="tooltip" data-placement="top" title="online" aria-hidden="true"></i> <span class="text-muted">${role}</span>
            </div>
        </div>
        <div class="col-md-9 col-sm-8 col-xs-12">
            <div class="wrapper">
                <c:forEach items="${projects}" var="project">
                    <article class="project col-md-6 col-sm-12">
                        <div class="project-wrapper">
                            <h3><i class="fa fa-bars" aria-hidden="true"></i> <a href="/projects/${project.key.projectName}">${project.key.projectName}</a></h3>
                            <div class="description text-muted">${project.key.gitReadme}</div>
                            <div class="nav">
                                <ul class="project-attributes nav navbar-nav">
                                    <li class="nav-item">Developers: <span class="badge">${project.value}</span></li>
                                    <c:if test="${not project.key.enabled}">
                                        <li class="nav-item">
                                            <i class="fa fa-archive" type="button" data-toggle="tooltip" data-placement="top" title="Project was archived" aria-hidden="true"></i>
                                        </li>
                                    </c:if>
                                </ul>
                            </div>
                        </div>
                    </article>
                </c:forEach>
            </div>
        </div>
    </div>
</div>


<div>
    <c:forEach items="${news}" var="pieceOfNews">
        <p> News: ${pieceOfNews.value}</p>
        <p> Date: ${pieceOfNews.key}</p>
    </c:forEach>
</div>

<%@include file='components/footer.jsp' %>
<%@include file='components/script.jsp' %>
</body>
</html>