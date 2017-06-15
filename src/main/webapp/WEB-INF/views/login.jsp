<%--
  Created by IntelliJ IDEA.
  User: Илья
  Date: 14.05.2017
  Time: 12:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<c:set var="page" value="login"/>
<!DOCTYPE html>
<html>
<head>
    <%@include file='components/head.jsp' %>
</head>
<body>
<%@include file='components/header.jsp' %>

<div class="main-content">
    <div class="wrapper">
        <div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8 col-xs-12">
            <div class="panel-heading">
                <div class="panel-title text-center">
                    <h1 class="title text-uppercase">devman</h1>
                    <hr/>
                </div>
            </div>
            <div class="form-state-line" id="modal-message-block">
                <div class="input-group col-xs-12">
                    <span id="modal-message-icon" class="input-group-addon glyphicon glyphicon-chevron-right"></span>
                    <input type="text" class="form-control" id="modal-message-text" readonly value="Enter username and password.">
                </div>
            </div>

            <div class="" id="login-modal">
                <div id="div-forms">
                    <!-- Begin # Login Form -->
                    <form id="login-form" action="<s:url value="/login"/>" method="post" class="form-horizontal" data-default-message="Type your username and password.">
                        <div class="modal-body">
                            <fieldset>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <div class="form-group">
                                    <label for="login-credentials" class="col-sm-3 control-label">Username:</label>
                                    <div class="col-sm-9">

                                        <div class="input-group col-xs-12">
                                            <span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
                                            <input type="text" class="form-control" id="login-credentials" name="credentials" placeholder="Username of credentials" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="login-password" class="col-sm-3 control-label">Password:</label>
                                    <div class="col-sm-9">
                                        <div class="input-group col-xs-12">
                                            <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                            <input type="password" class="form-control" id="login-password" name="password" placeholder="********" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-9 col-sm-offset-3">
                                        <div class="col-xs-12">
                                            <div class="checkbox">
                                                <label><input type="checkbox" name="rememer-me">Remember me</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                        </div>
                        <div class="modal-footer">
                            <div>
                                <button type="submit" class="btn btn-primary btn-lg btn-block" data-loading-text="Logging in...">Login</button>
                            </div>
                            <div class="btn-group btn-group-justified" role="group" aria-label="...">
                                <div class="btn-group" role="group">
                                    <button type="button" class="btn btn-link to-lost-btn">Lost Password?</button>
                                </div>
                                <div class="btn-group" role="group">
                                    <a href="<s:url value="/registration"/>" class="btn btn-link">Register</a>
                                </div>
                            </div>
                        </div>
                    </form>
                    <!-- End # Login Form -->

                    <!-- Begin | Lost Password Form -->
                    <form id="lost-form" action="<s:url value="/lost"/>" method="post" class="form-horizontal" style="display:none;" data-default-message="Type your username or e-mail.">
                        <div class="modal-body">
                            <fieldset>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <div class="form-group">
                                    <label for="lost-credentials" class="col-sm-3 control-label">Username:</label>
                                    <div class="col-sm-9">
                                        <div class="input-group col-xs-12">
                                            <span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
                                            <input type="text" class="form-control" id="lost-credentials" name="credentials" placeholder="Username or credentials" required="required"/>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                        </div>
                        <div>
                            <button type="submit" class="btn btn-primary btn-lg btn-block" data-loading-text="Sending...">Send</button>
                        </div>
                        <div class="btn-group btn-group-justified" role="group" aria-label="...">
                            <div class="btn-group" role="group">
                                <button type="button" class="btn btn-link to-login-btn">Sign In</button>
                            </div>
                        </div>
                    </form>
                    <!-- End | Lost Password Form -->
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file='components/footer.jsp' %>
<c:set var="lost" value='<%= request.getParameter("lost") %>'/>
<c:if test="${not empty lost && lost eq 'true'}">
    <script>
        $(function() {
            $('.to-lost-btn')[0].click();
        });
    </script>
</c:if>
<c:set var="hasError" value='<%= request.getParameter("error") %>'/>
<c:if test="${not empty hasError && hasError eq 'true' && not empty param.error}">
    <script>
        $(function () {
            changeMessage($('#modal-message-block'), $('#modal-message-icon'), $('#modal-message-text'), "error", "glyphicon-remove", "${SPRING_SECURITY_LAST_EXCEPTION}");
        });
    </script>
</c:if>
</body>
</html>
