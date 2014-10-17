<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html>
<head>
    <title>Device manager</title>

    <link rel="stylesheet" href="${ctx}/css/3p/bootstrap.min.css">
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
                    <li class="active"><a href="${ctx}/">Devices</a></li>
                    <li><a href="${ctx}/settings">Settings</a></li>
                </ul>

            </div>

            <table class="table" id="deviceTable">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Device name</th>
                    <th>Tv name</th>
                    <th>Url</th>
                    <th>Last heartbeat</th>
                    <th>#</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
</div>

<script data-main="${ctx}/js/app" src="${ctx}/js/3p/require.js"></script>
</body>
</html>
