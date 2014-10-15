<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html>
<head>
    <title>Device manager</title>

    <link rel="stylesheet" href="${ctx}/css/bootstrap.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="page-header">
            <h1>Device manager</h1>
        </div>

        <table class="table" id="deviceTable">
            <thead>
            <tr>
                <th>Id</th>
                <th>Device name</th>
                <th>Tv name</th>
                <th>Url</th>
                <th>Last heartbeat</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
</div>

<script data-main="${ctx}/js/app" src="${ctx}/js/3p/require.js"></script>
<script>
    define("App", { name: 'devices'});
</script>
</body>
</html>
