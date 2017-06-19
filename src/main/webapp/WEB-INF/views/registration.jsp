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

<!DOCTYPE html>
<html>
<head>
    <%@include file='components/head.jsp' %>
    <title>Registration</title>
</head>
<body class="registration-page theme-<s:theme code="themeName"/>">
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

                    <input type="text" class="form-control" id="modal-message-text" readonly
                           value="Register an account.">
                </div>
            </div>

            <sf:form id="register-form" class="form-horizontal" action="/registration" modelAttribute="userForm" method="post" enctype="multipart/form-data">
                <fieldset>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="form-group">
                        <label for="firstName" class="col-sm-3 control-label">First name:</label>
                        <div class="col-sm-9">
                            <div class="input-group col-xs-12">
                                <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                                <sf:input type="text" class="form-control" id="firstName" path="firstName"
                                       placeholder="John" required="required" autofocus="autofocus"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="lastName" class="col-sm-3 control-label">Last name:</label>
                        <div class="col-sm-9">
                            <div class="input-group col-xs-12">
                                <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                                <sf:input type="text" class="form-control" id="lastName" path="lastName" placeholder="Doe"
                                       required="required"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="middleName" class="col-sm-3 control-label">Middle name:</label>
                        <div class="col-sm-9">
                            <div class="input-group col-xs-12">
                                <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                                <sf:input type="text" class="form-control" id="middleName" path="middleName"
                                       placeholder="Christopher"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email" class="col-sm-3 control-label">Email:</label>
                        <div class="col-sm-9">
                            <div class="input-group col-xs-12">
                                <span class="input-group-addon"><i class="fa fa-envelope fa"
                                                                   aria-hidden="true"></i></span>
                                <sf:input type="email" class="form-control" id="email" path="email"
                                       placeholder="john@doe.com" required="required"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Gender:</label>
                        <div class="col-sm-9">
                            <div class="radio">
                                <label><sf:radiobutton path="gender" value="MALE"/>Male</label>
                            </div>
                            <div class="radio">
                                <label><sf:radiobutton path="gender" value="FEMALE"/>Female</label>
                            </div>
                            <div class="radio">
                                <label><sf:radiobutton path="gender" value="OTHER" checked="checked"/>Other</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="register-username" class="col-sm-3 control-label">Username:</label>
                        <div class="col-sm-9">
                            <div class="input-group col-xs-12">
                                <span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
                                <sf:input type="text" class="form-control" id="register-username" path="username"
                                       placeholder="JohnDoe" required="required"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="register-password" class="col-sm-3 control-label">Password:</label>
                        <div class="col-sm-9">
                            <div class="input-group col-xs-12">
                                <span class="input-group-addon"><i class="fa fa-lock fa-lg"
                                                                   aria-hidden="true"></i></span>
                                <sf:input type="password" class="form-control" id="register-password" path="password"
                                       placeholder="********" required="required" />
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="matchingPassword" class="col-sm-3 control-label">Password:</label>
                        <div class="col-sm-9">
                            <div class="input-group col-xs-12">
                                <span class="input-group-addon"><i class="fa fa-lock fa-lg"
                                                                   aria-hidden="true"></i></span>
                                <sf:input type="password" class="form-control" id="matchingPassword"
                                       path="matchingPassword" placeholder="********" required="required" />
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="profileImage" class="col-sm-3 control-label">Profile image</label>
                        <div class="col-sm-9">
                            <sf:input type="file" class="filestyle" id="profileImage" path="profileImage"
                                   accept=".png,.jpg,.jpeg,.gif" data-iconpath="glyphicon glyphicon-inbox"/>
                            <p class="help-block">Max size is 2MB.</p>
                        </div>
                    </div>
                </fieldset>
                <div class="form-error-container">
                    <sf:errors path="firstName" element="span" />
                    <sf:errors path="lastName" element="span" />
                    <sf:errors path="middleName" element="span" />
                    <sf:errors path="email" element="span" />
                    <sf:errors path="gender" element="span" />
                    <sf:errors path="username" element="span" />
                    <sf:errors path="password" element="span" />
                    <sf:errors path="matchingPassword" element="span" />
                    <sf:errors path="profileImage" element="span" />
                    <sf:errors path="*" element="span"/>
                </div>
                <div>
                    <button type="submit" class="btn btn-primary btn-lg btn-block" data-loading-text="Submiting...">
                        Register
                    </button>
                </div>
                <div class="btn-group btn-group-justified" role="group" aria-label="...">
                    <div class="btn-group" role="group">
                        <a href="<c:url value="/login"/>" class="btn btn-link">Sign In</a>
                    </div>
                </div>
            </sf:form>
        </div>
    </div>
</div>

<%@include file="components/footer.jsp"%>
<%@include file="components/script.jsp"%>

<c:if test="${not empty success}">
    <script>
        changeMessage($('#modal-message-block'), $('#modal-message-icon'), $('#modal-message-text'), "success", "glyphicon-ok", "Confirm your email");
    </script>
</c:if>
</body>
</html>
