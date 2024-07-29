<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Country List</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css">
    <style>
        *{
        padding:0px;
        margin:0px;
        }
    </style>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div class="container mt-4" ">
        <div class="float-left">
            <h2>Country List</h2>
        </div>
        <div class="float-right">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/maker/country-form" role="button">Back</a>
        </div>
        ${message}
        <table id="countryTable" class="table table-striped table-bordered" style="width:100%; font-size: small;">
            <thead class="thead-dark">
                <tr>
                    <th>ID</th>
                    <th>Country Name</th>
                    <th>ISD CODE </th>
                    <th>ISO CODE </th>
                    <th>Region </th>
                    <th>Status </th>
                    <th>Record Status </th>
                    <th>Authorized By</th>
                    <th>Authorized Date</th>
                    <th>Modified By</th>
                    <th>Modified Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${tempCountries}" var="country">
                    <tr>
                        <td>${country.id}</td>
                        <td>${country.countryName}</td>
                        <td>${country.countryIsdCode}</td>
                        <td>${country.countryIsoCode}</td>
                        <td>${country.region}</td>
                        <td>${country.status}</td>
                        <td>${country.getMetaData().recordStatus}</td>
                        <td>${country.metaData.authorizedBy}</td>
                        <td>${country.metaData.authorizedDate}</td>
                        <td>${country.metaData.modifiedBy}</td>
                        <td>${country.metaData.modifiedDate}</td>

                        <td>
                        <div class="btn-group" role="group">
                            <button type="button" class="btn btn-primary">
                                    <a href="show-update-country-form?id=${country.id}&status=${country.metaData.recordStatus}" style="color: white;">Edit</a>
                             </button>

                            <form action="deleteCountry/${country.id}" method="post" style="display: inline;">
                                <button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this country?')">Delete</button>
                            </form>
                        </div>


                        </td>
                    </tr>
                </c:forEach>

                <c:forEach items="${permCountries}" var="country1">
                    <tr>
                        <td>${country1.id}</td>
                        <td>${country1.countryName}</td>
                        <td>${country1.countryIsdCode}</td>
                        <td>${country1.countryIsoCode}</td>
                        <td>${country1.region}</td>
                        <td>${country1.status}</td>
                        <td>${country1.getMetaData().recordStatus}</td>
                        <td>${country1.metaData.authorizedBy}</td>
                        <td>${country1.metaData.authorizedDate}</td>
                        <td>${country1.metaData.modifiedBy}</td>
                        <td>${country1.metaData.modifiedDate}</td>
                        <td>
                            <div class="btn-group" role="group">
                                <button type="button" class="btn btn-primary">
                                    <a href="show-update-country-form?id=${country1.id}&status=${country1.metaData.recordStatus}" style="color: white;">Edit</a>
                                </button>
                                <form action="deleteCountry/${country1.id}" method="post" style="display: inline;">
                                    <button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this country?')">Delete</button>
                                </form>
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
                        $('#countryTable').DataTable({
                                          "scrollX": true
                                               });
                        });
                </script>
</body>
</html>
