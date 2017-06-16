<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <%@include file='components/head.jsp'%>
    <!-- git -->
    <title>Project</title>

        <meta charset="utf-8">

        <%--<!-- jQuery and jQuery UI (REQUIRED) -->--%>
        <%--<link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/themes/smoothness/jquery-ui.css">--%>
        <%--<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>--%>
        <%--<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/jquery-ui.min.js"></script>--%>

        <%--<!-- elFinder CSS (REQUIRED) -->--%>
        <%--<link rel="stylesheet" type="text/css" href="/res/libs/elfinder/css/elfinder.min.css">--%>
        <%--<link rel="stylesheet" type="text/css" href="/res/libs/elfinder/css/theme.css">--%>

        <%--<!-- elFinder JS (REQUIRED) -->--%>
        <%--<script src="/res/libs/elfinder/js/elfinder.full.js"></script>--%>

        <%--<!-- elFinder translation (OPTIONAL) -->--%>
        <%--<script src="/res/libs/elfinder/js/i18n/elfinder.ru.js"></script>--%>

        <%--<!-- elFinder initialization (REQUIRED) -->--%>
        <%--<script type="text/javascript" charset="utf-8">--%>
            <%--// Documentation for client options:--%>
            <%--// https://github.com/Studio-42/elFinder/wiki/Client-configuration-options--%>
            <%--$(document).ready(function() {--%>
                <%--$('#elfinder').elfinder({--%>
                    <%--url : 'connector',  			// connector URL (REQUIRED)--%>
                    <%--lang: 'en'                  					// language (OPTIONAL)--%>
                <%--});--%>
            <%--});--%>
        <%--</script>--%>

    <script src="res/libs/git/git.js"></script>
    <!-- gitapi -->
    <link rel="stylesheet" type="text/css"
          href="res/libs/git/dist/github.min.css">
    <script type="text/javascript"
            src="res/libs/undercore/underscore-min.js"></script>
    <script type="text/javascript"
            src="res/libs/git/dist/github.min.js"></script>
    <script type="text/javascript" src="res/libs/git/src/github.js"></script>

    <!-- elfinder -->
    <!-- jQuery and jQuery UI (REQUIRED) -->
    <link rel="stylesheet" type="text/css"
          href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/themes/smoothness/jquery-ui.css">
    <script
            src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
    <script
            src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/jquery-ui.min.js"></script>

    <!-- elFinder CSS (REQUIRED) -->
    <link rel="stylesheet" type="text/css"
          href="res/libs/elfinder/css/elfinder.min.css">
    <link rel="stylesheet" type="text/css"
          href="res/libs/elfinder/css/theme.css">

    <!-- elFinder JS (REQUIRED) -->
    <script type="text/javascript"
            src="res/libs/elfinder/js/elfinder.full.js"></script>

    <!-- elFinder translation (OPTIONAL) -->
    <script type="text/javascript"
            src="res/libs/elfinder/js/i18n/elfinder.ru.js"></script>

    <script type="text/javascript">
        //Documentation for client options:
        // https://github.com/Studio-42/elFinder/wiki/Client-configuration-options
        $(document).ready(function() {
            $('#elfinder').elfinder({
                url : 'connector',  			// connector URL (REQUIRED)
                lang: 'en'                  					// language (OPTIONAL)
            });
        });
    </script>



    </head>

<body>
<%--<%@include file='components/header.jsp'%>--%>


<div class="main-content">
    <div class="wrapper">
        <header>
            <ul class="nav nav-tabs nav-justified">
                <li role="presentation" class="active"><a href="#">Home</a></li>
                <li role="presentation"><a href="#">Profile</a></li>
                <li role="presentation"><a href="#">Messages</a></li>
            </ul>
        </header>
        <div class="page-header">
            <h1 class="text-center">Title</h1>
            <div id="elfinder"></div>
        </div>
        <div id="repo" class="border component"></div>
    </div>
</div>


<script type="text/javascript">
    displayRepo('ITransitionProjects', 'repo', 'Socket');
    //displayOrganisation('IvanovTrusevich', 'org');
</script>


<%--<%@include file='components/footer.jsp'%>--%>
<%--<script src="<s:url value="/res/js/jquery.min.js"/> "></script>--%>
<%--<script src="<s:url value="/res/js/jquery.form.min.js"/> "></script>--%>
<%--<script src="<s:url value="/res/js/jquery-ui.min.js"/> "></script>--%>
<%--<script src="<s:url value="/res/js/jquery.validate.min.js"/> "></script>--%>
<%--<script src="<s:url value="/res/js/bootstrap.min.js"/> "></script>--%>
<%--<script src="<s:url value="/res/js/bootstrap-filestyle.min.js"/> "></script>--%>
<%--<script src="<s:url value="/res/tooltipster/dist/js/tooltipster.bundle.min.js"/> "></script>--%>
<script src="<s:url value="/res/js/script.js"/> "></script>

<%--&lt;%&ndash;<!-- git-->&ndash;%&gt;--%>
<%--<script src="<s:url value="/res/libs/undercore/underscore-min.js"/> "></script>--%>
<%--<script src="<s:url value="/res/libs/git/dist/github.min.js"/> "></script>--%>


<%--<script type="text/javascript">--%>
    <%--displayRepo('IvanovTrusevich', 'repo', 'Developers-Managers');--%>
    <%--displayOrganisation('IvanovTrusevich', 'org');--%>
<%--</script>--%>

</body>
</html>