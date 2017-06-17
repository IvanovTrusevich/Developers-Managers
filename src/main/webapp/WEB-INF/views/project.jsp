<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file='components/head.jsp' %>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="<s:url value="/res/libs/git/dist/github.min.css"/>">
    <!-- elfinder -->
    <!-- jQuery and jQuery UI (REQUIRED) -->
    <link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="<s:url value="/res/libs/elfinder/css/elfinder.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<s:url value="/res/libs/elfinder/css/theme.css"/>">
    <title>Project</title>
</head>
<body class="project-page">
<%@include file='components/header.jsp' %>
<div class="main-content">
    <div class="wrapper">
        <div class="content">
            <header>
                <ul class="nav nav-tabs nav-justified" role="tablist">
                    <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">Home</a></li>
                    <li role="presentation"><a href="#wiki" aria-controls="profile" role="tab" data-toggle="tab">Wiki</a></li>
                    <li role="presentation"><a href="#forum" aria-controls="messages" role="tab" data-toggle="tab">Forum</a></li>
                    <li role="presentation"><a href="#news" aria-controls="settings" role="tab" data-toggle="tab">News</a></li>
                    <li role="presentation"><a href="#managment" aria-controls="settings" role="tab" data-toggle="tab">Management</a>
                    </li>
                </ul>
            </header>
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane fade in active" id="home">
                    <div id="repo" class="component"></div>
                    <div>
                        <c:forTokens items="${readme}" delims="\n" var="paragraph">
                            <p>${paragraph}</p>
                        </c:forTokens>
                    </div>
                    <div>
                        <c:forEach items="${tags}" var="tag">
                            <p>${tag.tagName}</p>
                        </c:forEach>
                    </div>
                    <div>
                        ${repoUrl}
                        ${repoName}
                    </div>
                    <div class="page-header">
                        <h1 class="text-center">Title</h1>
                        <div id="elfinder"></div>
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane fade" id="wiki">
                    <h1 class="text-center">README</h1>

                </div>
                <div role="tabpanel" class="tab-pane fade" id="forum">
                </div>
                <div role="tabpanel" class="tab-pane fade" id="news">
                </div>
                <div role="tabpanel" class="tab-pane fade" id="managment">...</div>
            </div>
        </div>
    </div>
</div>

<%@include file='components/footer.jsp' %>
<%--git--%>
<%--<script src="<s:url value="/res/libs/git/git.js"/>"></script>--%>
<script type="text/javascript" src="<s:url value="/res/libs/undercore/underscore-min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/res/libs/git/dist/github.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/res/libs/git/src/github.js"/>"></script>
<!-- elfinder -->
<!-- jQuery and jQuery UI (REQUIRED) -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/jquery-ui.min.js"></script>
<script type="text/javascript" src="<s:url value="/res/libs/elfinder/js/elfinder.full.js"/>"></script>
<script type="text/javascript" src="<s:url value="/res/libs/elfinder/js/i18n/elfinder.ru.js"/>"></script>

<script src="<s:url value="/res/responsive-toolkit/dist/bootstrap-toolkit.min.js"/>"></script>
<script src="<s:url value="/res/libs/jqcloud/jqcloud-1.0.4.js"/>"></script>
<script src="<s:url value="/res/js/script.js"/> "></script>
<script type="text/javascript">
    $(function () {
        $('#elfinder').elfinder({
            url: '/connector',
            lang: '${pageContext.response.locale}'
        });

        $currentPage.displayRepo('ITransitionProjects', 'repo', '${repoName}');
        //displayOrganisation('IvanovTrusevich', 'org');

        $tagCloud.init("#tag-cloud", ${tags});
    });
</script>
</body>
</html>