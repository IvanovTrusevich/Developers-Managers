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

<c:set var="page" value="registration"/>
<!DOCTYPE html>
<html>
    <%@include file='components/head.jsp'%>
    <body>
        <%@include file='components/header.jsp'%>

        <div class="inner-content">
            <div class="modal-window">
                <div class="modal-content">
                    <sf:form method="POST" commandName="user" action="/registration">
                        <div class="clearfix">
                            <div class="input-text-less  column-1-of-2  left">
                                <div class="wrap">
                                    <sf:label path="email" >
                                        <s:message code="registration.email" />:</sf:label>
                                </div>
                            </div>
                            <div class="input-text-less  column-1-of-2  right">
                                <div class="wrap">
                                    <sf:input path="email" type="email" placeholder="john.doe@gmail.com" cssErrorClass="error" />
                                </div>
                            </div>
                        </div>
                        <div class="clearfix">
                            <div class="input-text-less  column-1-of-2  left">
                                <div class="wrap">
                                    <sf:label path="password">
                                        <s:message code="registration.password" />:</sf:label>
                                </div>
                            </div>
                            <div class="input-text-less  column-1-of-2  right">
                                <div class="wrap">
                                    <sf:password path="password" placeholder="*****" cssErrorClass="error" />
                                </div>
                            </div>
                        </div>
                        <div class="clearfix">
                            <div class="input-text-less  column-1-of-2  left">
                                <div class="wrap">
                                    <sf:label path="matchingPassword">
                                        <s:message code="registration.password" />:</sf:label>
                                </div>
                            </div>
                            <div class="input-text-less  column-1-of-2  right">
                                <div class="wrap">
                                    <sf:password path="matchingPassword" placeholder="*****" cssErrorClass="error" />
                                </div>
                            </div>
                        </div>
                        <div>
                            <div class="column-1-of-2  left">
                                <div class="wrap">
                                    <input type="submit" value="<s:message code="registration.submit" />" />
                                </div>
                            </div>
                            <div class="input-text-less  column-1-of-2  right">
                                <div class="wrap">
                                    <label style="color: green;"><c:if test="${not empty success}"><s:message code="registration.success"/></c:if></label>
                                </div>
                            </div>
                        </div>
                    </sf:form>
                </div>
            </div>
        </div>

        <%@include file='components/footer.jsp'%>
    </body>
</html>
