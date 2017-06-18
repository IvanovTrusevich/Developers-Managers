<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<footer class="main-footer">
    <div class="container">
        <div class="col-sm-5 col-xs-12 footer-content">
            <ul class="nav navbar-nav">
                <li class="dropup" id="locale-dropup">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        <s:message code="footer.locale"/> <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="#">ru</a></li>
                        <li><a href="#">en</a></li>
                    </ul>
                </li>
                    <li class="dropup" id="theme-dropup">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                            <s:message code="footer.theme"/> <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="#">Default</a></li>
                            <li><a href="#">Purple</a></li>
                        </ul>
                    </li>
            </ul>
        </div>
        <c:if test="${not empty tags}">
            <div class="col-sm-7 col-xs-12">
                <div id="tag-cloud"></div>
            </div>
        </c:if>
    </div>
</footer>
