<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html>
<head>
    <title>Менеджер устройств</title>

    <link rel="stylesheet" href="${ctx}/css/3p/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctx}/css/style.css"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="page-header">
                <h1>Менеджер устройств</h1>

                <c:if test="${not isEmailSet}">
                    <div class="alert alert-danger">
                        <h3>Необходима установка адреса почты. Вы можете сделать это на странице настроек.</h3>
                    </div>
                </c:if>

                <ul class="nav nav-tabs">
                    <li class="active"><a href="${ctx}/">Устройста</a></li>
                    <li><a href="${ctx}/settings">Настройки</a></li>
                </ul>

            </div>

            <table class="table" id="deviceTable"">
                <thead>
                <tr>
                    <th>Номер</th>
                    <th>Имя устройства</th>
                    <th>Имя ТВ</th>
                    <th>Ссылка</th>
                    <th>Время получения последнего запроса</th>
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
