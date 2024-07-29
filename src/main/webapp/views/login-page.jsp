<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">

<head>
    <title>Login</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet">

    <style>
        html,
        body {
            height: 100%;
            font-family: 'Roboto', sans-serif;
        }

        body {
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
            background-color: #f5f5f5;
        }

        .form-signin {
            width: 100%;
            max-width: 400px;
            padding: 30px;
            border-radius: 10px;
            background-color: #fff;
            box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.1);
            animation: slide-in 0.5s ease;
        }

        .form-signin .form-control {
            margin-bottom: 20px;
            border: 2px solid #e9ecef;
            border-radius: 8px;
            padding: 15px;
            font-size: 16px;
            transition: border-color 0.3s ease;
        }

        .form-signin .form-control:focus {
            outline: none;
            border-color: #007bff;
        }

        .form-signin .form-control::placeholder {
            color: #a8a8a8;
        }

        .form-signin .form-control:focus::placeholder {
            color: transparent;
        }

        .form-signin label {
            font-weight: 500;
            color: #333;
        }

        .form-signin .btn-login {
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 8px;
            padding: 15px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .form-signin .btn-login:hover {
            background-color: #0056b3;
        }


        .error-message {
            background-color: #ffe4e1;
            border: 1px solid #ff7f7f;
            border-radius: 5px;
            padding: 10px;
            margin: 10px 0;
            display: flex;
            align-items: center;
        }

        .error-text {
            color: #c62828;
            font-weight: bold;
        }


        .success-message {
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
            border-radius: 5px;
            padding: 10px;
            margin: 10px 0;
            display: flex;
            align-items: center;
        }

        .success-text {
            color: #155724;
            font-weight: bold;
        }

        .icon {
            margin-right: 10px;
            font-size: 20px;
        }

        /* Centering the logo */
        .logo-container {
            text-align: center;
            margin-bottom:20px;
        }

        @keyframes slide-in {
            from {
                opacity: 0;
                transform: translateY(-50px);
            }

            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
    </style>
</head>

<body>
    <form class="form-signin" method="post" action="${pageContext.request.contextPath}/login">
        <div class="logo-container">
            <img src="${pageContext.request.contextPath}/static/logo.jpg" alt="Nucleus Software Logo" width="100" height="100">
        </div>
        <h1 class="h3 mb-3 font-weight-normal" style="text-align:center"><spring:message code="companyName"/></h1>

        <c:if test="${not empty param.error}">
            <div class="error-message">
                <span class="icon">&#9888;</span>
                <span class="error-text"><spring:message code="loginErrorMsg"/></span>
            </div>
        </c:if>

        <c:if test="${not empty param.logout}">
            <div class="success-message">
                <span class="icon">&#10003;</span>
                <span class="success-text"><spring:message code="logoutMsg"/></span>
            </div>
        </c:if>

        <label for="username"><spring:message code="userName"/></label>
        <input type="text" id="username" name="username" class="form-control" placeholder="<spring:message code='userNamePlaceholder'/>" required autofocus>

        <label for="password"><spring:message code="password"/></label>
        <input type="password" id="password" name="password" class="form-control" placeholder="<spring:message code='passwordPlaceholder'/>" required>

        <button class="btn btn-login btn-block" type="submit"><spring:message code="login"/></button>
    </form>
</body>

</html>