<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sucurity" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fs" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file='components/head.jsp' %>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="<s:url value="/res/libs/git/dist/github.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<s:url value="/res/libs/elfinder/css/elfinder.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<s:url value="/res/libs/elfinder/css/theme.css"/>">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
    <title><sucurity:authentication property="principal.username"/></title>
</head>
<body class="settings-page theme-<s:theme code="themeName"/>">
<%@include file='components/header.jsp' %>
<div class="main-content">
    <div class="wrapper">
        <div class="col-md-2 col-xs-12">
            <ul class="nav settings-list" role="tablist">
                <li role="presentation">
                    <a class="list-group-item active" href="#personal" aria-controls="personal" role="tab" data-toggle="tab" aria-expanded="true"><s:message code="profile.personal"/></a>
                </li>
                <li role="presentation">
                    <a class="list-group-item" href="#account" aria-controls="account" role="tab" data-toggle="tab"><s:message code="profile.account"/></a>
                </li>
            </ul>
        </div>
        <div class="col-md-10 col-xs-12">
            <div class="panel-heading">
                <div class="panel-title">
                    <h2 class="title"><s:message code="setting.header"/></h2>
                    <hr/>
                </div>
            </div>
            <div class="col-md-8 col-xs-12">
                <div class="form-state-line" id="modal-message-block">
                    <div class="input-group col-xs-12">
                        <span id="modal-message-icon" class="input-group-addon glyphicon glyphicon-chevron-right"></span>
                        <input type="text" class="form-control" id="modal-message-text" readonly value="Update your profile.">
                    </div>
                </div>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane fade in active" id="personal">
                        <sf:form id="personal-settings-form" class="form-horizontal" action="/settings/personal" modelAttribute="personalForm" method="post">
                            <fieldset>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <div class="form-group">
                                    <label for="firstName" class="col-sm-3 control-label"><s:message code="registration.firstName"/> :</label>
                                    <div class="col-sm-9">
                                        <div class="input-group col-xs-12">
                                            <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                                            <sf:input type="text" class="form-control" id="firstName" path="firstName" placeholder="First name" autofocus="autofocus"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="lastName" class="col-sm-3 control-label"><s:message code="registration.lastName"/>:</label>
                                    <div class="col-sm-9">
                                        <div class="input-group col-xs-12">
                                            <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                                            <sf:input type="text" class="form-control" id="lastName" path="lastName" placeholder="Last name"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="middleName" class="col-sm-3 control-label"><s:message code="registration.middleName"/>:</label>
                                    <div class="col-sm-9">
                                        <div class="input-group col-xs-12">
                                            <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                                            <sf:input type="text" class="form-control" id="middleName" path="middleName" placeholder="Middle name"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><s:message code="registration.gender"/>:</label>
                                    <div class="col-sm-9">
                                        <div class="radio">
                                            <label><sf:radiobutton path="gender" value="MALE"/><s:message code="registration.gender.male"/></label>
                                        </div>
                                        <div class="radio">
                                            <label><sf:radiobutton path="gender" value="FEMALE"/><s:message code="registration.gender.female"/></label>
                                        </div>
                                        <div class="radio">
                                            <label><sf:radiobutton path="gender" value="OTHER" checked="checked"/><s:message code="registration.gender.other"/></label>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                            <div class="form-error-container">
                            </div>
                            <div>
                                <button type="submit" class="btn btn-primary btn-lg btn-block" data-loading-text="<s:message code="settings.profile.submit.loading"/>"><s:message code="settings.profile.submit"/></button>
                            </div>
                        </sf:form>
                    </div>
                    <div role="tabpanel" class="tab-pane fade" id="account">
                        <sf:form id="account-settings-form" class="form-horizontal" modelAttribute="accountForm" action="/settings/account" method="post">
                            <fieldset>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <div class="form-group">
                                    <label for="old-password" class="col-sm-3 control-label"><s:message code="settings.account.oldPassword"/>:</label>
                                    <div class="col-sm-9">
                                        <div class="input-group col-xs-12">
                                            <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                            <fs:password class="form-control" id="old-password" path="oldPassword" placeholder="********" required="required"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="account-setting-password" class="col-sm-3 control-label"><s:message code="settings.account.newPassword"/>:</label>
                                    <div class="col-sm-9">
                                        <div class="input-group col-xs-12">
                                            <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                            <fs:password class="form-control" id="account-setting-password" path="password" placeholder="********" required="required"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="matchingPassword" class="col-sm-3 control-label"><s:message code="settings.account.newPassword"/>:</label>
                                    <div class="col-sm-9">
                                        <div class="input-group col-xs-12">
                                            <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                            <fs:password class="form-control" id="matchingPassword" path="matchingPassword" placeholder="********" required="required"/>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                            <div class="form-error-container"></div>
                            <div>
                                <button type="submit" class="btn btn-primary btn-lg btn-block" data-loading-text="<s:message code="settings.account.submit.loading"/>"><s:message code="settings.account.submit"/></button>
                            </div>
                        </sf:form>
                    </div>
                </div>
            </div>

            <div class="col-md-4 col-xs-12">
                <div id="profile-image-spinner">
                    <img id="profile-image" src="${user.photo.image}" class="img-thumbnail img-responsive center-block" alt="User picture">
                </div>
                <div class="popover-static">
                    <div class="popover bottom">
                        <div class="arrow"></div>
                        <h3 class="popover-title title lead text-center text-uppercase"><s:message code="settings.photo"/></h3>
                        <div class="popover-content">
                            <div class="form-group">
                                <form id="update-photo-form"  action="<s:url value="/settings/photo"/>" enctype="multipart/form-data" method="post">
                                    <fieldset>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                        <input type="file" class="filestyle" id="profileImage" name="profileImage" accept=".png,.jpg,.jpeg,.gif" data-iconName="glyphicon glyphicon-inbox">
                                    </fieldset>
                                    <p class="help-block"><s:message code="registration.profileImage.hint"/></p>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file='components/footer.jsp' %>
<%@include file='components/script.jsp' %>

<c:if test="${not empty success}">
    <script>
        changeMessage($('#modal-message-block'), $('#modal-message-icon'), $('#modal-message-text'), "success", "glyphicon-ok", "<s:message code="settings.updated"/>");
    </script>
</c:if>
</body>
</body>
</html>