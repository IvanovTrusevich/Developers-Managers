<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<security:authorize access="isAuthenticated()" var="authenticated"/>

<header class="main-navigation">
    <nav class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><s:message code="header.company"/></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li class="nav-item"><a class="text-uppercase" href="#"><strong><s:message code="header.news"/></strong></a></li>
                <c:choose>
                    <c:when test="${not empty authenticated && authenticated == true}">
                        <li class="dropdowm-menu" id="user-dropdown">
                            <a class="text-uppercase dropdown-toggle" type="button" id="dropdownMenu1"
                               data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" href="">
                                <strong><s:message code="header.profile"/></strong>
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                                <li class="dropdown-header"><s:message code="header.profile.singInAs"/> <span id="user-dropdown-username"><security:authentication property="principal.username"/></span></li>
                                <li><a href="/profile/<security:authentication property="principal.username"/>}"><s:message code="header.profile.myProfile"/></a></li>
                                <%--<li><a href="#"><s:message code="header.profile.myProjects"/></a></li>--%>
                                <li role="separator" class="divider"></li>
                                <li><a href="<s:url value="/settings"/>"><s:message code="header.profile.settings"/></a></li>
                                <li>
                                    <a id="logout-trigger" type="submit" href=""><s:message code="header.profile.signOut"/></a>
                                    <form id="logout-form" action="<s:url value="/logout"/>" method="POST">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                    </form>
                                </li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item"><a class="text-uppercase" href="<s:url value="/login"/>"><strong><s:message
                                code="header.login"/></strong></a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
            <form class="navbar-form navbar-right">
                <input class="form-control" placeholder="<s:message code="header.search"/>" type="text">
            </form>
        </div>
    </nav>
</header>


