<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html>
<head>
    <title>Менеджер устройств</title>

    <link rel="stylesheet" href="${ctx}/css/3p/bootstrap.min.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-offset-1 col-md-10">
            <div class="page-header">
                <h1>Менеджер устройств</h1>

                <c:if test="${not isEmailSet}">
                    <div class="alert alert-danger">
                        <h3>Необходима установка адреса почты. Вы можете сделать это на странице настроек.</h3>
                    </div>
                </c:if>

                <ul class="nav nav-tabs">
                    <li><a href="${ctx}/">Устройства</a></li>
                    <li class="active"><a href="${ctx}/settings">Настройки</a></li>
                </ul>
            </div>
        </div>

        <div class="col-md-offset-3 col-md-6">
            <form:form commandName="settings">
                <div class="form-group">
                    <form:label path="emailAddress" for="emailAddress">Адрес почты</form:label>
                    <form:input path="emailAddress" id="emailAddress" cssClass="form-control"/>
                    <form:errors path="emailAddress" cssClass="text-danger"/>
                </div>
                <button type="submit" class="btn btn-success">Сохранить настройки</button>
            </form:form>

        </div>
    </div>
</div>

</body>
</html>
