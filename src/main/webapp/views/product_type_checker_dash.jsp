<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>
<html>
<head>
    <title>Product Type Checker Dashboard</title>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
    <style>
        /* Additional styles if needed */
    </style>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="container mt-5">
    <h2>List of Product Types</h2>
    <hr>
</div>
<div class="container">
    <table id="productTypeTable" class="table table-striped table-bordered" style="width:100%; font-size: small;">
        <thead class="thead-dark">
        <tr>
            <th>ProductType Code</th>
            <th>Record Status</th>
            <th>Active/Inactive</th>
            <th>Created By</th>
            <th>Creation Date</th>
            <th>Modified By</th>
            <th>Modified Date</th>
            <th>Authorized By</th>
            <th>Authorized Date</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${productTypeList}" var="parameter">
            <tr>
                <td>
                    <a href="javascript:void(0);" class="open-modal"
                       data-parameter="${parameter}">
                        ${parameter.productTypeCode}
                    </a>
                </td>
                <td>${parameter.metaData.recordStatus}</td>
                <td>${parameter.metaData.activeInactiveFlag}</td>
                <td>${parameter.metaData.createdBy}</td>
                <td>${parameter.metaData.creationDate}</td>
                <td>${parameter.metaData.modifiedBy}</td>
                <td>${parameter.metaData.modifiedDate}</td>
                <td>${parameter.metaData.authorizedBy}</td>
                <td>${parameter.metaData.authorizedDate}</td>
                <td>
                    <c:if test="${parameter.metaData.recordStatus ne 'A'}">
                        <div class="btn-group" role="group">
                            <button type="button" class="btn btn-primary">
                                <a href="approve-product-type/${parameter.productTypeId}" style="color: white;">Approve</a>
                            </button>
                            <button type="button" class="btn btn-danger">
                                <a href="reject-product-type/${parameter.productTypeId}" style="color: white;">Reject</a>
                            </button>
                        </div>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<!-- Modal Structure -->
<div class="modal fade" id="productTypeModal" tabindex="-1" role="dialog" aria-labelledby="productTypeModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="productTypeModalLabel">Product Type Details</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <table class="table table-bordered">
                    <tbody id="modal-body"></tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<hr>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    $(document).ready(function() {
        $('#productTypeTable').DataTable();
        $('.open-modal').on('click', function() {
            var parameter = $(this).data('parameter');
            var modalBody = $('#modal-body');

            modalBody.empty();

            var objString = parameter.substring(parameter.indexOf("{") + 1, parameter.lastIndexOf("}"));
            var objArray = objString.split(", ");

            $.each(objArray, function(index, value) {
                var pair = value.split("=");
                var attributeName = pair[0].trim();
                var attributeValue = pair[1].trim().replace(/['"]+/g, ''); // Remove surrounding quotes

                if (attributeName === "productTypeId") {
                    return;
                }

                if (attributeName === "metaData") {
                    return false;
                }

                // Capitalize the first letter of attribute name and replace underscores with spaces
                attributeName = attributeName.replace(/([A-Z])/g, ' $1').trim();
                attributeName = attributeName.charAt(0).toUpperCase() + attributeName.slice(1);

                modalBody.append('<tr><th>' + attributeName + '</th><td>' + attributeValue + '</td></tr>');
            });

            $('#productTypeModal').modal('show');
        });
    });
</script>
</body>
</html>
