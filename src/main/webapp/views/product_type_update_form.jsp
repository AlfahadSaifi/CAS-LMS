<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType ="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Product Type</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</head>
<body>
<%@ include file="navbar.jsp"%>
    <div class="container my-4 card shadow">
        <form:form action="update-product-type" method="post" modelAttribute="productType">
        <form:hidden path="productTypeId" />
        <form:hidden path="metaData.recordStatus" />
            <div class="row justify-content-between align-items-center mb-3 my-4">
                <div class="col-auto">
                    <h2>Create Product Type</h2>
                </div>
                <div class="col-auto">
                    <input type="submit" class="btn btn-primary"/>
                </div>
            </div>

            <div class="form-container">
                <div class="row mb-3">
                    <div class="form-left col-md-6 mb-3">
                        <div class="mb-3">
                            <label for="productTypeCode">Product Type Code:</label>
                            <form:input path="productTypeCode" id="productTypeCode" class="form-control" required="true" readonly="true"/>
                        </div>
                        <div>
                            <label for="securedFlag">Secured Flag:</label><br>
                            <div class="form-check form-check-inline">
                                <form:radiobutton path="securedFlag" value="Y" checked="checked"/>
                                <label class="form-check-label mr-3 ml-1">Secured</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <form:radiobutton path="securedFlag" value="N" />
                                <label class="form-check-label ml-1">Unsecured</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-right col-md-6 mb-3">
                        <div class="mb-3">
                            <label for="description">Description:</label>
                            <form:input path="description" id="description" class="form-control" placeholder="Enter Description" required="true" />
                        </div>
                        <div>
                            <label for="fundBasedFlag">Fund-Based Flag:</label>
                            <form:select path="fundBasedFlag" id="repayFreq" class="form-control" required="true">
                                <form:option value="FUND_BASED">Fund-Based</form:option>
                                <form:option value="NON_FUND_BASED">Non-Fund-Based</form:option>
                                <form:option value="BOTH">Both</form:option>
                            </form:select>
                        </div>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
    <div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-labelledby="messageModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="messageModalLabel">Message</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="redirectToDashboard()">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p id="messageModalContent"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="redirectToDashboard()">Close</button>
                </div>
            </div>
        </div>
    </div>
    <script>
        function showModal(message) {
            $('#messageModal').modal('show');
            document.getElementById("messageModalContent").innerText = message;
        }
        $(document).ready(function() {
            var message = "<c:out value='${message}' />"; // Get the message from the JSP
            if (message.trim() !== '') { // Check if message is not empty
                showModal(message); // Call showModal() function
            }
        });
        function redirectToDashboard() {
            window.location.href = "product-type-dashboard";
        }
    </script>
</body>
</html>
