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
                <i class="fa fa-circle" type="button" data-toggle="tooltip" data-placement="top" title="online" aria-hidden="true"></i> <span class="text-muted">admin</span>
            </div>
        </div>
        <div class="col-md-9 col-sm-8 col-xs-12">
            <div class="wrapper">
                <c:forEach items="${user.projects}" var="project">
                    <article class="project col-md-6 col-sm-12">
                        <div class="project-wrapper">
                            <h3><i class="fa fa-bars" aria-hidden="true"></i> <a href="/projects/${project.projectName}">${project.projectName}</a></h3>
                            <div class="description text-muted">${project.gitReadme}</div>
                            <div class="nav">
                                <ul class="project-attributes nav navbar-nav">
                                    <li class="nav-item">Developers: <span class="badge">${project.developers.size}</span></li>
                                    <c:if test="${project.enabled}">
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

<%@include file='components/footer.jsp' %>
<%@include file='components/script.jsp' %>
</body>
</html>