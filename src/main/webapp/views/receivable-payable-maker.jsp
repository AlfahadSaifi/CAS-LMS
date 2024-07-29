<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Receivable/Payable (Maker)</title>
                <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
                <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

                <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
                <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <style>

            #success-message {
                background-color: #d4edda;
                color: #155724;
                border: 1px solid #c3e6cb;
                padding: 10px;
                margin-bottom: 10px;
                border-radius: 5px;
            }

        </style>
    </head>
    <body>
        <%@ include file="navbar.jsp"%>
        <div class="container my-4 card shadow">
            <form:form action="insert-receivable-payable" modelAttribute="receivablePayable">
                <div class = "row justify-content-between align-items-center mb-3 my-4">
                    <div class = "col-auto">
                        <h2>Receivable (Create)</h2>
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-primary"><spring:message code="button.saveForReceivable" /></button>
                        <button type="button" class="btn btn-secondary" onclick="clearForm()"><spring:message code="button.cancelButton" /></button>
                    </div>
                </div>
                <c:if test="${not empty successMessage}">
                    <div id="success-message">${successMessage}</div>
                </c:if>
                <div class="form-row">
                    <div class="col-md-6 mb-3">
                        <label for="loan-account" ><spring:message code="label.loanAccount" /><span style="color: red;">*</span></label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="loan-account-number" name="loan-account-number" placeholder="Loan Account Number" required >
                            <div class="input-group-append">
                                <button type="button" class="btn btn-primary" id="searchButton" onclick="searchLoan()" ><spring:message code="search"/></button>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="customer-name" ><spring:message code="label.customerNameForReceivable" /></label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="customer-name" name="customer-name"  required disabled/>
                        </div>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-6 mb-3">
                        <label for="loan-branch" ><spring:message code="label.loanBranch" /><span style="color: red;">*</span></label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="loan-branch" name="loan-branch"  required disabled/>
                        </div>
                    </div>
                     <div class="col-md-6 mb-3">
                        <label for="currency" ><spring:message code="label.currency" /></label>
                        <form:select class="form-control" path="currency">
                            <form:option value="INR">INR</form:option>
                        </form:select>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-6 mb-3">
                        <label for="status" >
                            <spring:message code="label.status" /><span style="color: red;">*</span>
                        </label>
                        <input type="text" class="form-control" id="status" name="status"  disabled="true"/>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="receivablePayableType" >
                            <spring:message code="label.receivablePayableType" />
                        </label>
                        <form:select class="form-control" path="receivablePayableType" id="receivablePayableType">
                            <form:option value="RECEIVABLE">RECEIVABLE</form:option>
                            <form:option value="PAYABLE">PAYABLE</form:option>
                        </form:select>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-6 mb-3">
                        <label for="due-date" >
                            <spring:message code="label.receivable.dueDate" /><span style="color: red;">*</span>
                        </label>
                        <div class="input-group">
                            <form:input class="form-control" type="date" path="receivablePayableDueDate" />
                        </div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="receivablePayableAmount" ><spring:message code="label.amount" /><span style="color: red;">*</span></label>
                        <div class="input-group">
                            <form:input class="form-control" type="number" id="receivablePayableAmount" step="0.01" path="receivablePayableAmount" min="0" required="required"/>
                        </div>
                    </div>
                </div>

            </form:form>
        </div>
    </body>
    <script>
        $(document).ready(function() {
            // To Set focus on the first editable field
            $('input:not([disabled]), select:not([disabled]), textarea:not([disabled])').first().focus();

        });

        function searchLoan() {
            var loanAccountNumber = document.getElementById("loan-account-number").value;
            // AJAX request
            $.ajax({
                url: "../searchDetailsForMaker?loanAccountNumber="+loanAccountNumber,
                type: "GET",
                dataType: "json",
                success: function(data) {
                console.log(data);
                    // Populate form fields
                    document.getElementById("status").value = data.loanStatus;
                    document.getElementById("loan-branch").value = data.loanApplication.branch;
                    document.getElementById("customer-name").value = data.loanApplication.customer.personInfoTemp.fullName;
                },
                error: function(_, status, error) {
                    console.error("Error occurred while fetching data: " + error);
                    //alert("Loan account Number doesn't exist");
                }
            });
        }


    </script>
</html>
