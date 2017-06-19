<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
    <%@include file='components/head.jsp'%>
    <title><s:message code="index.title"/> </title>
</head>
    <body class="index-page theme-<s:theme code="themeName"/>">
    <%@include file='components/header.jsp'%>

    <div>
        <c:forEach items="${projects}" var="project">
            <p>${project.projectName}</p>
        </c:forEach>

        <c:forEach items="${news}" var="n">
            <article class="news col-md-6 col-sm-12">
                <div class="news-wrapper">
                    <h4><a href="#"><i class="fa fa-newspaper-o" aria-hidden="true"></i></a></h4>
                    <div class="description">${n.value}</div>
                    <div class="date">${n.key}</div>
                </div>
            </article>
        </c:forEach>
    </div>


    <%@include file="components/footer.jsp"%>
    <%@include file="components/script.jsp"%>
    </body>
</html>