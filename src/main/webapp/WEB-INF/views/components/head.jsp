<%--
  Created by IntelliJ IDEA.
  User: Илья
  Date: 15.05.2017
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<head>
    <title><s:message code="${empty page ? 'default' : page}.title"/></title>
    <link rel="stylesheet" href="<c:url value="/res/css/normalize.css" />">
    <link rel="stylesheet" href="<c:url value="/res/css/style.css" />">
    <link rel="stylesheet" href="<c:url value="/res/css/bootstrap-tooltip.css"/>"/>
    <script src="<c:url value="/res/js/jquery.js"/>"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script type="text/javascript"
            src="//ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
    <script type="text/javascript"
            src="//ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.js"></script>
    <link rel="stylesheet" type="text/css"
          href="//ajax.googleapis.com/ajax/libs/jqueryui/1.7.1/themes/base/jquery-ui.css">
    <meta name="viewport" content="width=900">
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
