<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:set var="page" value="registration"/>
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
                    <input type="text" class="form-control" id="modal-message-text" readonly value="Enter new password.">
                </div>
            </div>

            <form id="recovery-form" class="form-horizontal" method="post">
                <fieldset>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <div class="form-group">
                        <label for="register-password" class="col-sm-3 control-label">Password:</label>
                        <div class="col-sm-9">
                            <div class="input-group col-xs-12">
                                <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                <input type="password" class="form-control" id="register-password" name="password" placeholder="********" required>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="matchingPassword" class="col-sm-3 control-label">Password:</label>
                        <div class="col-sm-9">
                            <div class="input-group col-xs-12">
                                <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                <input type="password" class="form-control" id="matchingPassword" name="matchingPassword" placeholder="********" required>
                            </div>
                        </div>
                    </div>
                </fieldset>
                <div class="form-error-container">
                    <!--
                    <span id="credentials.errors">User with this credentials is already exists</span>
                    <span id="user.errors">Ololo</span>
-->
                </div>
                <div>
                    <button type="submit" class="btn btn-primary btn-lg btn-block" data-loading-text="Submiting...">Register</button>
                </div>
                <div class="btn-group btn-group-justified" role="group" aria-label="...">
                    <div class="btn-group" role="group">
                        <a href="<c:url var="/login"/>" class="btn btn-link">Sign In</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<%@include file="components/footer.jsp"%>
<%@include file="components/script.jsp"%>
<c:if test="${success}">
    <script>
        changeMessage($('#modal-message-block'), $('#modal-message-icon'), $('#modal-message-text'), "success", "glyphicon-ok", "Confirm your email");
    </script>
</c:if>
</body>
</html>