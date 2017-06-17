<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<footer class="main-footer">
    <div class="container">
        <div class="col-sm-5 col-xs-12 footer-content">
            <span class="text-muted">Place sticky footer content here.</span>
        </div>
        <c:if test="${not empty tags}">
            <div class="col-sm-7 col-xs-12">
                <div id="tag-cloud"></div>
            </div>
        </c:if>
    </div>
</footer>
