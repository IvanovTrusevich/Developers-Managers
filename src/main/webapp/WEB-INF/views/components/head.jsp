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


    <%--<link rel="stylesheet" type="text/css" href="res/libs/jqcloud/jqcloud.css" />--%>
    <%--<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>--%>
    <%--<script type="text/javascript" src="res/libs/jqcloud/jqcloud-1.0.4.js"></script>--%>
    <%--<script type="text/javascript">--%>
        <%--/*!--%>
         <%--* Create an array of word objects, each representing a word in the cloud--%>
         <%--*/--%>
        <%--var word_array = [--%>
            <%--{text: "Lorem", weight: 15},--%>
            <%--{text: "Ipsum", weight: 9, link: "http://jquery.com/"},--%>
            <%--{text: "Dolor", weight: 6, html: {title: "I can haz any html attribute"}},--%>
            <%--{text: "Sit", weight: 7},--%>
            <%--{text: "Amet", weight: 5}--%>
            <%--// ...as many words as you want--%>
        <%--];--%>
        <%--$(function() {--%>
            <%--// When DOM is ready, select the container element and call the jQCloud method, passing the array of words as the first argument.--%>
            <%--$("#example").jQCloud(word_array);--%>
        <%--});--%>
    <%--</script>--%>


    <!-- elfinder -->
    <!-- jQuery and jQuery UI (REQUIRED) -->
    <link rel="stylesheet" type="text/css" href="webjars/jquery-ui-themes/1.11.4/smoothness/jquery-ui.min.css">
    <script src="webjars/jquery/1.12.4/jquery.min.js"></script>
    <script src="webjars/jquery-ui/1.11.4/jquery-ui.min.js"></script>

    <!-- elFinder CSS (REQUIRED) -->
    <link rel="stylesheet" type="text/css" href="webjars/elfinder/2.1.11/css/elfinder.min.css">
    <link rel="stylesheet" type="text/css" href="webjars/elfinder/2.1.11/css/theme.css">

    <!-- elFinder JS (REQUIRED) -->
    <script src="webjars/elfinder/2.1.11/js/elfinder.min.js"></script>

    <!-- elFinder translation (OPTIONAL) -->
    <script src="webjars/elfinder/2.1.11/js/i18n/elfinder.ru.js"></script>

    <!-- elFinder initialization (REQUIRED) -->
    <script type="text/javascript" charset="utf-8">
        // Documentation for client options:
        // https://github.com/Studio-42/elFinder/wiki/Client-configuration-options
        $(document).ready(function() {
            $('#elfinder').elfinder({
                url : 'elfinder-servlet/connector',
                // , lang: 'ru'                    // language (OPTIONAL)
            });
        });
    </script>

</head>
