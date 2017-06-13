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
            <a class="navbar-brand" href="#">Devman</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a class="text-uppercase" href="#"><strong>Dashboard</strong></a></li>
                <li id="login-popup">
                    <a href="#" class="text-uppercase" role="button" data-toggle="modal" data-target="#login-modal">
                        <strong><s:message code="header.login"/></strong>
                    </a>
                </li>
                <li id="user-dropdown">
                    <div class="dropdown" style="display: none;">
                        <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                            Dropdown
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                            <li class="dropdown-header">Signed in as <span id="user-dropdown-username"></span></li>
                            <li><a href="#">Profile</a></li>
                            <li><a href="#">My projects</a></li>
                            <li><a href="#">Something else here</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">Settings</a></li>
                            <li><a href="#">
                                <form:form action="/logout" method="POST">
                                    <button type="submit">Sign out</button>
                                </form:form>
                            </a></li>
                        </ul>
                    </div>
                </li>
            </ul>
            <form class="navbar-form navbar-right">
                <input class="form-control" placeholder="Search..." type="text">
            </form>
        </div>
    </nav>
</header>

<!-- BEGIN # MODAL LOGIN -->
<div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
     style="display: none;">
    <div class="modal-dialog">
        <div class="panel-heading">
            <div class="panel-title text-center">
                <h1 class="title text-uppercase">devman</h1>
                <hr/>
            </div>
        </div>
        <div class="modal-content col-xs-12">
            <div class="modal-header">
                <!--                    <img class="img-circle" id="img_logo" src="http://bootsnipp.com/img/logo.jpg">-->
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                </button>
            </div>

            <div class="state" id="modal-message-block">
                <div class="input-group col-xs-12">
                    <span id="modal-message-icon" class="input-group-addon glyphicon glyphicon-chevron-right"></span>
                    <input type="text" class="form-control" id="modal-message-text" readonly
                           value="Type your username and password." title="Output">
                </div>
            </div>

            <!-- Begin # DIV Form -->
            <div id="div-forms">
                <!-- Begin # Login Form -->
                <form action="<c:url value="/login"/>" id="login-form" method="POST" class="form-horizontal"
                      data-default-message="Type your username and password.">
                    <div class="modal-body">
                        <fieldset>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="form-group">
                                <label for="login-credentials" class="col-sm-3 control-label">Username:</label>
                                <div class="col-sm-9">
                                    <div class="input-group col-xs-12">
                                        <span class="input-group-addon"><i class="fa fa-users fa"
                                                                           aria-hidden="true"></i></span>
                                        <input type="text" class="form-control" id="login-credentials"
                                               name="credentials" placeholder="Username of email" required>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="login-password" class="col-sm-3 control-label">Password:</label>
                                <div class="col-sm-9">
                                    <div class="input-group col-xs-12">
                                        <span class="input-group-addon"><i class="fa fa-lock fa-lg"
                                                                           aria-hidden="true"></i></span>
                                        <input type="password" class="form-control" id="login-password" name="password"
                                               placeholder="********" required>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-9 col-sm-offset-3">
                                    <div class="col-xs-12">
                                        <div class="checkbox">
                                            <label><input type="checkbox" name="remember-me">Remember me</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                    <div class="modal-footer">
                        <div>
                            <button type="submit" class="btn btn-primary btn-lg btn-block"
                                    data-loading-text="Logging in...">Login
                            </button>
                        </div>
                        <div class="btn-group btn-group-justified" role="group" aria-label="...">
                            <div class="btn-group" role="group">
                                <button type="button" class="btn btn-link to-lost-btn">Lost Password?</button>
                            </div>
                            <div class="btn-group" role="group">
                                <button type="button" class="btn btn-link to-register-btn">Register</button>
                            </div>
                        </div>
                    </div>
                </form>
                <!-- End # Login Form -->

                <!-- Begin | Lost Password Form -->
                <form action="<s:url value="/lost"/>" method="post" id="lost-form" class="form-horizontal"
                      style="display:none;"
                      data-default-message="Type your username or e-mail.">
                    <div class="modal-body">
                        <fieldset>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="form-group">
                                <label for="lost-credentials" class="col-sm-3 control-label">Username:</label>
                                <div class="col-sm-9">
                                    <div class="input-group col-xs-12">
                                        <span class="input-group-addon"><i class="fa fa-users fa"
                                                                           aria-hidden="true"></i></span>
                                        <input type="text" class="form-control" id="lost-credentials" name="credentials"
                                               placeholder="Username or email" required>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                    <div class="modal-footer">
                        <div>
                            <button type="submit" class="btn btn-primary btn-lg btn-block"
                                    data-loading-text="Sending...">Send password to email
                            </button>
                        </div>
                        <div class="btn-group btn-group-justified" role="group" aria-label="...">
                            <div class="btn-group" role="group">
                                <button type="button" class="btn btn-link to-login-btn">Log In</button>
                            </div>
                            <div class="btn-group" role="group">
                                <button type="button" class="btn btn-link to-register-btn">Register</button>
                            </div>
                        </div>
                    </div>
                </form>
                <!-- End | Lost Password Form -->

                <!-- Begin | Register Form -->
                <form action="<s:url value="/registration"/>" method="POST" id="register-form" class="form-horizontal"
                      style="display:none;" enctype="multipart/form-data" data-default-message="Register an account.">
                    <fieldset>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-group">
                            <label for="firstName" class="col-sm-3 control-label">First name:</label>
                            <div class="col-sm-9">
                                <div class="input-group col-xs-12">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" id="firstName" name="firstName"
                                           placeholder="John" required autofocus>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastName" class="col-sm-3 control-label">Last name:</label>
                            <div class="col-sm-9">
                                <div class="input-group col-xs-12">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" id="lastName" name="lastName"
                                           placeholder="Doe" required>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="middleName" class="col-sm-3 control-label">Middle name:</label>
                            <div class="col-sm-9">
                                <div class="input-group col-xs-12">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" id="middleName" name="middleName"
                                           placeholder="Christopher">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="email" class="col-sm-3 control-label">Email:</label>
                            <div class="col-sm-9">
                                <div class="input-group col-xs-12">
                                    <span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>
                                    <input type="email" class="form-control" id="email" name="email"
                                           placeholder="john@doe.com" required>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Gender:</label>
                            <div class="col-sm-9">
                                <div class="radio">
                                    <label><input type="radio" name="gender" value="male"/>Male</label>
                                </div>
                                <div class="radio">
                                    <label><input type="radio" name="gender" value="female"/> Female</label>
                                </div>
                                <div class="radio">
                                    <label><input type="radio" name="gender" value="other" checked/> Other</label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="register-username" class="col-sm-3 control-label">Username:</label>
                            <div class="col-sm-9">
                                <div class="input-group col-xs-12">
                                    <span class="input-group-addon"><i class="fa fa-users fa"
                                                                       aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" id="register-username" name="username"
                                           placeholder="JohnDoe" required>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="register-password" class="col-sm-3 control-label">Password:</label>
                            <div class="col-sm-9">
                                <div class="input-group col-xs-12">
                                    <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                    <input type="password" class="form-control" id="register-password" name="password"
                                           placeholder="********" required>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="matchingPassword" class="col-sm-3 control-label">Password:</label>
                            <div class="col-sm-9">
                                <div class="input-group col-xs-12">
                                    <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                    <input type="password" class="form-control" id="matchingPassword"
                                           name="matchingPassword" placeholder="********" required>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="profileImage" class="col-sm-3 control-label">Profile image</label>
                            <div class="col-sm-9">
                                <input type="file" class="filestyle" id="profileImage" name="profileImage"
                                       accept=".png,.jpg,.jpeg,.gif" data-iconName="glyphicon glyphicon-inbox">
                                <p class="help-block">Max size is 2MB.</p>
                            </div>
                        </div>
                    </fieldset>
                    <div class="modal-footer">
                        <div>
                            <button type="submit" class="btn btn-primary btn-lg btn-block"
                                    data-loading-text="Submiting...">Register
                            </button>
                        </div>
                        <div class="btn-group btn-group-justified" role="group" aria-label="...">
                            <div class="btn-group" role="group">
                                <button type="button" class="btn btn-link to-login-btn">Log In</button>
                            </div>
                        </div>
                    </div>
                </form>
                <!-- End | Register Form -->
            </div>
            <!-- End # DIV Form -->

        </div>
    </div>
</div>
<!-- END # MODAL LOGIN -->

