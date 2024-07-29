<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>


<!--UNZALA-->
<!--UNZALA-->

<html>
<head>
    <meta charset="UTF-8">
    <title>City List</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css">


</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div class="container mt-5">
        <div class="float-left">
            <h2>City List</h2>
        </div>
        <div class="float-right">
             <a class="btn btn-primary" href="${pageContext.request.contextPath}/maker/city-form" role="button">Back</a>
        </div>

        ${message}
        <table id="cityTable" class="table table-striped table-bordered" style="width:100%; font-size: small;">
            <thead class="thead-dark">
                <tr>
                    <th>ID</th>
                    <th>City Name</th>
                    <th>City Code</th>
                    <th>Std code</th>
                    <th>Record Status </th>
                    <th>Authorized By</th>
                    <th>Authorized Date</th>
                    <th>Modified By</th>
                    <th>Modified Date</th>
                    <th>Actions</th>

                </tr>
            </thead>
            <tbody>
                <c:forEach items="${cityTemps}" var="city">
                    <tr>
                        <td>${city.id}</td>
                        <td>${city.cityName}</td>
                        <td>${city.cityCode}</td>
                        <td>${city.cityCode}</td>
                        <td>${city.getMetaData().recordStatus}</td>
                        <td>${city.metaData.authorizedBy}</td>
                        <td>${city.metaData.authorizedDate}</td>
                        <td>${city.metaData.modifiedBy}</td>
                        <td>${city.metaData.modifiedDate}</td>
                        <td>
                        <div class="btn-group" role="group">
                            <a class="btn btn-primary btn-sm mr-1"  href="show-update-city-form?id=${city.id}&status=${city.metaData.recordStatus}" style="color: white;">Edit</a>
                            <a class="btn btn-danger btn-sm" href="delete-city?id=${city.id}&status=${city.metaData.recordStatus}" style="color: white;">Delete</a>
                        </div>

                        </td>
                    </tr>
                </c:forEach>

                <c:forEach items="${cityPerms}" var="city1">
                    <tr>
                        <td>${city1.id}</td>
                        <td>${city1.cityName}</td>
                        <td>${city1.cityCode}</td>
                        <td>${city1.cityCode}</td>
                        <td>${city1.getMetaData().recordStatus}</td>
                        <td>${city1.metaData.authorizedBy}</td>
                        <td>${city1.metaData.authorizedDate}</td>
                        <td>${city1.metaData.modifiedBy}</td>
                        <td>${city1.metaData.modifiedDate}</td>
                        <td>
                            <div class="btn-group" role="group">
                                <a class="btn btn-primary btn-sm mr-1"  href="show-update-city-form?id=${city1.id}&status=${city1.metaData.recordStatus}" style="color: white;">Edit</a>
                                <a class="btn btn-danger btn-sm" href="delete-city?id=${city1.id}&status=${city1.metaData.recordStatus}" style="color: white;">Delete</a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js"></script>
        <script>
        $(document).ready( function () {
            $('#cityTable').DataTable({
                              "scrollX": true
                                   });
            });
        </script>
</body>
</html>
