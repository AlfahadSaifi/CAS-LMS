<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--Unzala-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pending Countries</title>
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
        <h1 class="mt-4">Countries</h1>

        ${message}
        <table id="pendingRequestsTable" class="table table-striped table-bordered" style="width:100%; font-size: small;">
            <thead class="thead-dark">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>ISO Code</th>
                    <th>ISD Code</th>
                    <th>Record Status</th>

                    <th>Authorized By</th>
                    <th>Authorized Date</th>
                    <th>Modified By</th>
                    <th>Modified Date</th>

                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="country" items="${countryListForApproval}">
                    <tr>
                        <td>${country.id}</td>
                        <td>${country.countryName}</td>
                        <td>${country.countryIsoCode}</td>
                        <td>${country.countryIsdCode}</td>
                        <td>${country.getMetaData().recordStatus}</td>
                        <td>${country.metaData.authorizedBy}</td>
                        <td>${country.metaData.authorizedDate}</td>
                        <td>${country.metaData.modifiedBy}</td>
                        <td>${country.metaData.modifiedDate}</td>
                        <td>
                        <div class="container" style="display:flex; gap:30px">
                        <form action="approve/${country.id}" method="post">
                            <button type="submit" class="btn btn-primary  "  >Approve</button>
                        </form>

                            <a href="reject/${country.id}" role="button" class="btn btn-danger reject-btn" onclick="deleteRow(this)"> Reject</button> </a>

                            </div>
                        </td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>
    </div>
      <script>
            function deleteRow(el) {
                      var row = $(el).closest('tr'); // Get the row
                      var dataTable = $('#pendingRequestsTable').DataTable();
                      dataTable.row(row).remove().draw(); // Remove and redraw the table
                    }
                    $(document).ready(function() {
                      var table = $('#pendingRequestsTable').DataTable();
                    });


      </script>
</body>
</html>