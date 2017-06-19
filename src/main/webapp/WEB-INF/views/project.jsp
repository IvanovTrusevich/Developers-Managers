<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file='components/head.jsp' %>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="<s:url value="/res/libs/git/dist/github.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<s:url value="/res/libs/elfinder/css/elfinder.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<s:url value="/res/libs/elfinder/css/theme.css"/>">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
    <title>Project</title>
</head>
< class="project-page theme-<s:theme code="themeName"/>">
<%@include file='components/header.jsp' %>
<div class="main-content">
    <div class="wrapper">
        <div class="content">
            <header>
                <ul class="nav nav-tabs nav-justified" role="tablist">
                    <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab"><s:message code="project.menu.home"/></a></li>
                    <li role="presentation"><a href="#wiki" aria-controls="profile" role="tab" data-toggle="tab"><s:message code="project.menu.wiki"/></a></li>
                    <li role="presentation"><a href="#news" aria-controls="settings" role="tab" data-toggle="tab"><s:message code="project.menu.news"/></a></li>
                    <security:authorize access="hasRole('ROLE_MANAGER')">
                        <li role="presentation"><a href="#managment" aria-controls="settings" role="tab" data-toggle="tab"><s:message code="project.menu.managment"/></a></li>
                    </security:authorize>
                </ul>
            </header>
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane fade in active" id="home">
                    <div class="page-header">
                        <h1 class="text-center">${projectName}</h1>
                        <div>
                            <h3 class = 'text'><s:message code="project.clone"/></h3>
                            ${repoUrl}
                        </div>
                        <div id="elfinder"></div>
                    </div>
                    <h1 class="text-center">README</h1>
                    <div>
                        <c:forEach items="${readme}" var="paragraph">
                            <p>${paragraph}</p>
                        </c:forEach>
                    </div>
                    <div>
                        <c:forEach items="${tags}" var="tag">
                            <p>${tag.tagName}</p>
                        </c:forEach>
                    </div>
                    <div>
                        ${repoUrl}
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane fade" id="wiki">
                    <textarea id='mde'></textarea>
                    <button id="to-pdf-btn" class="btn btn-default">To PDF</button>
                </div>
                <div role="tabpanel" class="tab-pane fade" id="news">
                    <div id="repo" class="component"></div>
                    <div>
                        <p>${project.enabled}</p>
                    </div>
                </div>
                <security:authorize access="hasRole('ROLE_MANAGER')">
                    <div role="tabpanel" class="tab-pane fade" id="managment">
                        <div>
                            
                        </div>
                    </div>
                </security:authorize>
            </div>
        </div>
    </div>
</div>

<div>
    <p>wiki</p>
    <p>${wiki}</p>
    <p>wikiLastEditor</p>
    <p><a href=//profile/${wikiLastEditor}">${wikiLastEditor}</a></p>
</div>

<%@include file='components/footer.jsp' %>
<%@include file='components/script.jsp' %>
<script type="text/javascript" src="<s:url value="/res/libs/undercore/underscore-min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/res/libs/git/dist/github.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/res/libs/git/src/github.js"/>"></script>
<script type="text/javascript" src="<s:url value="/res/libs/elfinder/js/elfinder.full.js"/>"></script>
<script type="text/javascript" src="<s:url value="/res/libs/elfinder/js/i18n/elfinder.ru.js"/>"></script>
<script type="text/javascript" src="<s:url value="/res/libs/simplemde/js/simplemde.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/res/libs/jspdf/js/jspdf.min.js"/>"></script>
<script type="text/javascript">
    $(function() {
        $currentPage.displayRepo('ITransitionProjects', 'repo', '${repoName}');
        //displayOrganisation('IvanovTrusevich', 'org');

        $tagCloud.init("#tag-cloud", ${tags});

        $currentPage.initializeMDE();

        $(function () {
            $('#elfinder').elfinder({
                url: '/connector/${projectName}',
                lang: '${pageContext.response.locale}',
                commands : [
                ],
                contextmenu : {
                    // navbarfolder menu
                    navbar : [],
                    // current directory menu
                    cwd    : [],
                    // current directory file menu
                    files  : []
                }
            });
        });
    });
</script>
</body>
</html>