
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Navbar</title>
    <!-- Bootstrap CSS -->
      <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
      <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.13.5/css/jquery.dataTables.min.css"/>
    <!-- Custom CSS -->
    <style>
        /* Navbar CSS */
        .navbar {
            background-color: #F8F9FA; /* Light Gray */
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            font-size: 120%;
        }

        .navbar-brand {
            color: #007BFF; /* Blue */
            display: flex;
            align-items: center;
            font-weight: bold;
        }

        .navbar-brand img {
            margin-right: 10px;
        }

        .navbar-text-movable {
            color: #333; /* Dark gray */
            font-size: 100%;
            width:100%;
            display: inline-block;
            white-space: nowrap;
            animation: slideRight 10s linear infinite;
        }

        .navbar-text {
            color: #333; /* Dark gray */
            font-size: 100%;
            display: inline-block;
        }

        .navbar-text a {
            color: #007BFF; /* Blue */
            font-weight: bold;
            text-decoration: none;
        }

        .navbar-text a:hover {
            color: #0056B3; /* Dark blue */
        }

        .username-wrapper {
        width:60%;
            display: flex;
            align-items: center;
            overflow: hidden;
        }

        @keyframes slideRight {
            0% { transform: translateX(-100%); }
            100% { transform: translateX(100%); }
        }

        .username-wrapper span {
            display: inline-block;
            vertical-align: middle;
            margin-right: 10px;
        }

        .navbar-text.home {
            margin-right: 10px; /* Adjust the margin as needed */
        }

        .navbar-text.logout {
            margin-left: 10px; /* Adjust the margin as needed */
            margin-right:10px;
        }

        #current-time{
        margin-left:10px;
        display: inline !important; /* Ensure it stays inline */
        color: green;
        font-weight:bolder;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light">
    <div class="container d-flex justify-content-between align-items-center">
        <!-- Logo and Brand Name -->
        <div class="navbar-brand">
            <img src="${pageContext.request.contextPath}/static/logo.jpg" alt="Logo" height="40" width="40">
            <span style="font-size:130%">Nucleus Software</span>
        </div>

        <!-- Username -->
        <div class="username-wrapper">
            <div class="navbar-text-movable">
                <span>Welcome,</span>
                <sec:authorize access="isAuthenticated()">
                    <span><sec:authentication property="principal.username" /></span>
                </sec:authorize>
            </div>
        </div>

        <!-- Logout Link -->
        <div>
            <div class="navbar-text home">
                <a href="${pageContext.request.contextPath}">Home</a>
            </div>
        </div>

        <div>
            <div class="navbar-text logout">
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </div>
        </div>

        <div>
            <div class="navbar-text"><p id="current-time"></p></div>
        </div>
    </div>
</nav>

<!-- Bootstrap JS -->

<script>
    function displayCurrentTime() {
        var currentTime = new Date();
        var hours = currentTime.getHours();
        var minutes = currentTime.getMinutes();
        var seconds = currentTime.getSeconds();

        // Add leading zeros if needed
        hours = (hours < 10 ? "0" : "") + hours;
        minutes = (minutes < 10 ? "0" : "") + minutes;
        seconds = (seconds < 10 ? "0" : "") + seconds;

        var timeString = hours + ":" + minutes + ":" + seconds;

        document.getElementById("current-time").innerHTML = timeString;

        // Update time every second
        setTimeout(displayCurrentTime, 1000);
    }

    // Call the function when the page is loaded
    window.onload = displayCurrentTime;
</script>



</body>
</html>