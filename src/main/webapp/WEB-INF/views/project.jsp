<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <%@include file='components/head.jsp'%>
    <!-- git -->
    <link rel="stylesheet" type="text/css" href="<s:url value="/res/libs/git/dist/github.min.css"/> ">
    <!-- elFinder CSS (REQUIRED) -->
    <link rel="stylesheet" type="text/css" href="<s:url value="/res/libs/elfinder/css/elfinder.min.css"/> ">
    <link rel="stylesheet" type="text/css" href="<s:url value="/res/libs/elfinder/css/theme.css"/> ">
    <title>Project</title>
</head>
<body>
<%@include file='components/header.jsp'%>


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
        <div id="org" class="border component"></div>
    </div>
</div>
<%@include file='components/footer.jsp'%>
<!-- git-->
<script src="<s:url value="/res/libs/undercore/underscore-min.js"/> "></script>
<script src="<s:url value="/res/libs/git/dist/github.min.js"/> "></script>
<!-- elFinder JS (REQUIRED) -->
<script src="<s:url value="/res/libs/elfinder/js/elfinder.min.js"/> "></script>
<!-- elFinder translation (OPTIONAL) -->
<script src="<s:url value="/res/libs/elfinder/js/i18n/elfinder.ru.js"/> "></script>
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
<script type="text/javascript">
    displayRepo('IvanovTrusevich', 'repo', 'Developers-Managers');
    displayOrganisation('IvanovTrusevich', 'org');
</script>
</body>
</html>