<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html>
<head>
    <title>Device manager</title>

    <link rel="stylesheet" href="${ctx}/css/3p/bootstrap.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-offset-1 col-md-10">
            <div class="page-header">
                <h1>Device manager</h1>

                <c:if test="${not isEmailSet}">
                    <div class="alert alert-danger">
                        <h3>Please set the mail address on the settings page.</h3>
                    </div>
                </c:if>

                <ul class="nav nav-tabs">
                    <li><a href="${ctx}/">Devices</a></li>
                    <li class="active"><a href="${ctx}/settings">Settings</a></li>
                </ul>
            </div>
        </div>

        <div class="col-md-offset-3 col-md-6">
            <form:form commandName="settings">
                <div class="form-group">
                    <form:label path="emailAddress" for="emailAddress">Email address</form:label>
                    <form:input path="emailAddress" id="emailAddress" cssClass="form-control"/>
                    <form:errors path="emailAddress" cssClass="text-danger"/>
                </div>
                <button type="submit" class="btn btn-success">Save settings</button>
            </form:form>

        </div>
    </div>
</div>

</body>
</html>
