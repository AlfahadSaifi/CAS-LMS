<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!--Unzala-->

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">

    <title>Cities</title>
<!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- DataTables CSS -->
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.css">
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- DataTables JavaScript -->
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.js"></script>


</head>
<body>
<%@ include file="navbar.jsp"%>

    <div class="container">
        <h1 class="mt-4">Cities</h1>
        <table id="pendingCitiesTable" class="table table-striped table-bordered" style="width:100%; font-size: small;">
            <thead class="thead-dark">
                <tr>
                    <th>ID</th>
                    <th>City Name</th>
                    <th>City Code</th>
                    <th>Record status </th>
                    <th>Authorized By</th>
                    <th>Authorized Date</th>
                    <th>Modified By</th>
                    <th>Modified Date</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="city" items="${citiesTemp}">
                    <tr>
                        <td>${city.id}</td>
                        <td>${city.cityName}</td>
                        <td>${city.cityCode}</td>
                        <td>${city.getMetaData().recordStatus}</td>
                        <td>${city.metaData.authorizedBy}</td>
                        <td>${city.metaData.authorizedDate}</td>
                        <td>${city.metaData.modifiedBy}</td>
                        <td>${city.metaData.modifiedDate}</td>
                        <td>
                           <div class="container" style="display:flex; gap:30px">
                                <a class="btn btn-primary" href="${pageContext.request.contextPath}/checker/approveCity/${city.id}" onclick="deleteRow(this)" role="button" >Approve</a>
                                <a class="btn btn-danger" href="${pageContext.request.contextPath}/checker/rejectCity/${city.id}" onclick="deleteRow(this)" role="button">Reject</a>
                           </div>
                        </td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/" role="button">Back</a>

    </div>


    <script>
        function deleteRow(el) {
          var row = $(el).closest('tr'); // Get the row
          var dataTable = $('#pendingCitiesTable').DataTable();
          dataTable.row(row).remove().draw(); // Remove and redraw the table
        }
        $(document).ready(function() {
          var table = $('#pendingCitiesTable').DataTable();
        });

    </script>
</body>
</html>
