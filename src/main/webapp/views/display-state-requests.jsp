<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!--Ashish Goyal-->

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">

    <title>States</title>
       <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
       <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
       <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
       <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
       <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css">
<style>
.id{
text-align:right;}
</style>


</head>
<body>
<%@ include file="navbar.jsp"%>

    <div class="container">
        <h1 class="mt-4">States</h1>
        <table id="pendingStatesTable" class="table table-striped table-bordered" style="width:100%; font-size: small;">
            <thead class="thead-dark">

                <tr>
                    <th>ID</th>
                    <th>State Name</th>
                    <th>State Code</th>
                    <th>Region</th>
                    <th>Record Status</th>
                    <th>Authorized By</th>
                    <th>Authorized Date</th>
                    <th>Created By</th>
                    <th>Creation Date</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="state" items="${stateListForApproval}">
                    <tr>
                        <td class="id">${state.id}</td>
                        <td>${state.stateName}</td>
                        <td>${state.stateCode}</td>
                        <td>${state.region}</td>
                        <td>${state.getMetaDataTemp().recordStatus}</td>
                        <td>${state.getMetaDataTemp().authorizedBy}</td>
                        <td>${state.getMetaDataTemp().authorizedDate}</td>
                        <td>${state.getMetaDataTemp().createdBy}</td>
                        <td>${state.getMetaDataTemp().creationDate}</td>
                        <td>
                           <div class="container" style="display:flex; gap:30px">
                                <a class="btn btn-primary" href="${pageContext.request.contextPath}/checker/approveState/${state.id}" onclick="deleteRow(this)" role="button" >Approve</a>
                                <a class="btn btn-danger" href="${pageContext.request.contextPath}/checker/rejectState/${state.id}" onclick="deleteRow(this)" role="button">Reject</a>
                           </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/" role="button">Back</a>

    </div>

    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js"></script>


    <script>
        function deleteRow(el) {
          var row = $(el).closest('tr'); // Get the row
          var dataTable = $('#pendingStatesTable').DataTable();
          dataTable.row(row).remove().draw();
        }
        $(document).ready(function() {
          var table = $('#pendingStatesTable').DataTable({
                                                       "scrollX": true
                                                            });
        });

    </script>
</body>
</html>

