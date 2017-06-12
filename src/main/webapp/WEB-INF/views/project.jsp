<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="<s:url value="/res/css/main.min.css"/> "/>
    <link rel="stylesheet" href="<s:url value="/res/css/bootstrap.min.css"/> "/>
    <link rel="stylesheet" href="<s:url value="/res/font-awesome/css/font-awesome.min.css"/> ">
    <link rel="stylesheet" type="text/css" href="<s:url value="/res/tooltipster/dist/css/tooltipster.bundle.min.css"/> "/>
    <link rel="stylesheet" href="<s:url value="/res/css/jquery-ui.min.css"/> "/>
    <!-- git -->
    <link rel="stylesheet" type="text/css" href="<s:url value="/res/libs/git/dist/github.min.css"/> ">
    <!-- elFinder CSS (REQUIRED) -->
    <link rel="stylesheet" type="text/css" href="<s:url value="/res/libs/elfinder/css/elfinder.min.css"/> ">
    <link rel="stylesheet" type="text/css" href="<s:url value="/res/libs/elfinder/css/theme.css"/> ">
    <title>Project</title>
</head>
<body>
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
            <a class="navbar-brand" href="#">Project name</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a class="text-uppercase" href="#"><strong>Dashboard</strong></a></li>
                <li><a class="text-uppercase" href="#"><strong>Settings</strong></a></li>
                <li><a class="text-uppercase" href="#"><strong>Profile</strong></a></li>
                <li>
                    <a href="#" class="text-uppercase" role="button" data-toggle="modal" data-target="#login-modal">Login/registrate</a>
                </li>
                <li>
                    <p class="navbar-text navbar-right">Signed in as <a href="#" class="navbar-link">Mark Otto</a></p>
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
                <form id="login-form" class="form-horizontal" data-default-message="Type your username and password.">
                    <div class="modal-body">
                        <fieldset>
                            <div class="form-group">
                                <label for="login-credentials" class="col-sm-3 control-label">Username:</label>
                                <div class="col-sm-9">
                                    <div class="input-group col-xs-12">
                                        <span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
                                        <input type="text" class="form-control" id="login-credentials"
                                               name="credentials" placeholder="Username of email" required>
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
                <form id="lost-form" class="form-horizontal" style="display:none;"
                      data-default-message="Type your username or e-mail.">
                    <div class="modal-body">
                        <fieldset>
                            <div class="form-group">
                                <label for="lost-credentials" class="col-sm-3 control-label">Username:</label>
                                <div class="col-sm-9">
                                    <div class="input-group col-xs-12">
                                        <span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
                                        <input type="text" class="form-control" id="lost-credentials" name="credentials" placeholder="Username or email" required>
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
                <form id="register-form" class="form-horizontal" style="display:none;" enctype="multipart/form-data"
                      method="post" data-default-message="Register an account.">
                    <fieldset>
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

<div class="main-content">
    <div class="wrapper">
        <header>
            <ul class="nav nav-tabs nav-justified">
                <li role="presentation" class="active"><a href="#">Home</a></li>
                <li role="presentation"><a href="#">Profile</a></li>
                <li role="presentation"><a href="#">Messages</a></li>
            </ul>
        </header>
        <div id="elfinder" class="page-header">
            <h1 class="text-center">Title</h1>
        </div>
        <div id="repo" class="border component"></div>
        <div id="org" class="border component"></div>
    </div>
</div>
<script src="<s:url value="/res/js/jquery.min.js"/> ">
</script>
<script src="<s:url value="/res/js/jquery-ui.min.js"/> "></script>
<script src="<s:url value="/res/js/jquery.validate.min.js"/> "></script>

<script src="<s:url value="/res/js/bootstrap.min.js"/> "></script>
<script src="<s:url value="/res/js/bootstrap-filestyle.min.js"/> "></script>

<script src="<s:url value="/res/tooltipster/dist/js/tooltipster.bundle.min.js"/> "></script>

<script src="<s:url value="/res/js/script.js"/> "></script>
<!-- git-->
<script src="<s:url value="/res/libs/undercore/underscore-min.js"/> "></script>
<script src="<s:url value="/res/libs/git/dist/github.min.js"/> "></script>
<!-- elFinder JS (REQUIRED) -->
<script src="<s:url value="/res/libs/elfinder/js/elfinder.min.js"/> "></script>
<!-- elFinder translation (OPTIONAL) -->
<script src="<s:url value="/res/libs/elfinder/js/i18n/elfinder.ru.js"/> "></script>
<!--
<script>
//Documentation for client options:
// https://github.com/Studio-42/elFinder/wiki/Client-configuration-options
$(document).ready(function() {
    $('#elfinder').elfinder({
        url: 'elfinder-servlet/connector', // connector URL (REQUIRED)
        lang: 'en' // language (OPTIONAL)
    });
});
</script>
-->
<script type="text/javascript">
    displayRepo('IvanovTrusevich', 'repo', 'Developers-Managers');
    displayOrganisation('IvanovTrusevich', 'org');
</script>
</body>
</html>