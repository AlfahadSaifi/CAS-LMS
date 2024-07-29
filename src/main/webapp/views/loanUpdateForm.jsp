<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Loan Application</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <style>
        .spinner {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent black background */
            z-index: 9999;
        }

        #loading {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            border: 5px solid #f3f3f3; /* Light grey */
            border-top: 5px solid #3498db; /* Blue */
            border-radius: 50%;
            width: 50px;
            height: 50px;
            animation: spin 1s linear infinite; /* Spin animation */
        }

        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
        .mainHeading {
            font-size: 24px;
            margin-bottom: 20px;
        }

        .leadHeading {
            font-size: 20px;
            color: rgb(130, 130, 252);
            margin-bottom: 20px;
        }

        .form-label {
            font-weight: bold;
        }

        .error {
            font-size: 14px;
        }
    </style>


    <script>
         $(document).ready(
            function () {

                $('.spinner').hide();



                // validation

                function validateLoanApplication() {
                    var isValid = true;

                    // Validate branch
                    var branch = $('#branch').val().trim();
                    if (branch === '') {
                        $('#branchError').text('Branch is required.');
                        isValid = false;
                    } else {
                        $('#branchError').text('');
                    }

                    // Validate loanAmount
                    var loanAmount = $('#loanAmount').val().trim();
                    if (loanAmount === '') {
                        $('#loanAmountError').text('Loan Amount is required.');
                        isValid = false;
                    } else if (parseFloat(loanAmount) <= 0) {
                       $('#loanAmountError').text('Loan Amount must be greater then zero.');
                       isValid = false;
                    }
                    else {
                        $('#loanAmountError').text('');
                    }


                    // Validate productType
                    var productType = $('#productType').val().trim();
                    if (productType === '') {
                        $('#productTypeError').text('Product Type is required.');
                        isValid = false;
                    } else {
                        $('#productTypeError').text('');
                    }

                    // Validate product
                    var product = $('#product').val().trim();
                    if (product === '') {
                        $('#productError').text('Loan product is required.');
                        isValid = false;
                    } else {
                        $('#productError').text('');
                    }


                    // Validate tenure
                    var tenure = $('#tenure').val().trim();
                    if (tenure === '') {
                        $('#tenureError').text('Tenure is required.');
                        isValid = false;
                    } else if (parseFloat(tenure) <= 0) {
                       $('#tenureError').text('Tenure must be greater then zero.');
                       isValid = false;
                    }
                    else {
                        $('#tenureError').text('');
                    }

                    // Validate rate
                    var rate = $('#rate').val().trim();
                    if (product === '') {
                        $('#rateError').text('Rate is required.');
                        isValid = false;
                    } else {
                        $('#rateError').text('');
                    }


                    return isValid;
                }

                $('#submitBtn').click(function () {
                    var isValidLoanApplication = validateLoanApplication();
                    if(!isValidLoanApplication)return false;
                });





                var productsData;
                $('#productType').change(function() {

                    $('.spinner').show();

                    $('#rate').val('');
                    var productTypeId = $(this).val();
                    $.ajax({
                        url: '${pageContext.request.contextPath}/maker/fetch-product',
                        type: 'GET',
                        data: { productTypeId: productTypeId },
                        success: function(products) {

                            $('.spinner').hide();

                            productsData=products;
                            $('#product').empty().append('<option value="">Select Product</option>');
                            $.each(products, function(index, product) {
                                $('#product').append('<option value="' + product.productId + '">' + product.productName + '</option>');

                            });
                        },
                        error: function(xhr, textStatus, errorThrown){
                            $('.spinner').hide();

                            console.error('Error : ' ,errorThrown);
                        }
                    });

                    // Event listener for changing the rate of interest
                    $('#product').change(function() {
                        var selectedProductId = $(this).val();
                        if (productsData) {
                            var selectedProduct = productsData.find(
                                function(product) {
                                    return product.productId == selectedProductId;
                                }
                            );
                            if (selectedProduct) {
                                $('#rate').val(selectedProduct.rateOfInterest);
                            } else {
                                console.error('Selected product not found.');
                            }
                        } else {
                            console.error('Products data is not yet available.');
                        }
                    });
                });
            }
         );
    </script>
</head>

<body>
    <%@ include file="navbar.jsp"%>
<div class="container my-4 card shadow">
    <div class="container mt-4">
        <h2 class="leadHeading"><spring:message code="label.loanInformation"/></h2>
        <hr>

        <div class="row">
            <div class="col">
                <form:form action="${pageContext.request.contextPath}/maker/update-loan-application" modelAttribute="loanApplication" method = "post">
                      <form:hidden  class="form-control" path="customerDTO.customerId" readonly="true"/>
                      <form:hidden  class="form-control" path="loanAccountNumber" readonly="true"/>
                      <form:hidden  class="form-control" path="loanId" readonly="true"/>
                      <form:hidden  class="form-control" path="recordStatus" readonly="true"/>
                      <form:hidden  class="form-control" path="creationDate" readonly="true"/>

                    <div class="form-group row">
                        <div class="form-group col-md-6">
                            <label for="branch" class="form-label">
                            <spring:message code="label.branch"/>
                            <span style="color: red;">*</span></label>
                            <form:input type="text" class="form-control" path="branch" placeholder="Branch" required="true" id="branch"/>

                            <span id="branchError" style="color: red;"></span>
                            <form:errors path="branch" style="color:red" />
                        </div>
                        <div class="form-group col-md-6">
                            <label for="loanAmount" class="form-label">
                            <spring:message code="label.loanAmount"/>
                            <span style="color: red;">*</span></label>
                            <form:input type="number" class="form-control" path="loanAmount"
                                placeholder="Enter amount" required="true" id="loanAmount"/>

                            <span id="loanAmountError" style="color: red;"></span>
                            <form:errors path="loanAmount" style="color:red" />
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="form-group col-md-6">
                             <label for="applicationCreationDate" class="form-label">
                            <spring:message code="label.applicationCreationDate"/>
                            </label>
                            <form:input type="date" id="datePicker" class="form-control" path="applicationCreationDate"
                                readonly="true" />
                        </div>
                        <div class="form-group col-md-6">
                            <label for="loanApplicationId" class="form-label">
                            <spring:message code="label.loanApplicationId"/>
                            </label>
                            <form:input type="text" class="form-control" id="loanApplicationId" path="loanApplicationId"
                                readonly="true" />
                        </div>
                    </div>


                    <div class="form-group row">
                        <div class="form-group col-md-6">
                             <label for="productType" class="form-label">
                             <spring:message code="label.productType"/>
                             <span style="color: red; padding:3px;">*</span></label>
                             <select id="productType" class="form-control" required="true">
                                <option value="">Select Product Type</option>
                                <c:forEach items="${productTypeDTOS}" var="productType">
                                    <option value="${productType.productTypeId}">${productType.productTypeCode}</option>
                                </c:forEach>
                             </select>
                             <span id="productTypeError" style="color: red;"></span>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="loanProductDTO" class="form-label">
                            <spring:message code="label.product"/>
                            </label>
                            <form:select id="product" class="form-control" path="loanProductDTO.productId" required="true">
                                <form:option value="">Select Product</form:option>
                            </form:select>
                            <span id="productError" style="color: red;"></span>
                        </div>
                    </div>


                    <div class="form-group row">
                        <div class="form-group col-md-6">
                             <label for="tenure" class="form-label">
                             <spring:message code="label.tenure"/>
                             </label>
                             <div class="input-group">
                                 <form:input type="text" class="form-control" path="tenure" required="true" id="tenure"/>
                                 <form:errors path="tenure" style="color:red" />
                                 <form:select class="form-control" path="tenureIn" required="true">
                                     <form:option value="Months">Months</form:option>
                                     <form:option value="Weeks">Weeks</form:option>
                                     <form:option value="Years">Years</form:option>
                                 </form:select>
                             </div>
                             <span id="tenureError" style="color: red;"></span>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="rate" class="form-label">
                            <spring:message code="label.rate"/>
                            <span style="color: red;">*</span></label>
                            <input type="text" class="form-control" id="rate" readonly="true" />
                            <span id="rateError" style="color: red;"></span>
                        </div>
                    </div>

                    <div class="form-group mt-3">
                        <button id="submitBtn" class="btn btn-primary"><spring:message code="button.saveNext"/></button>
                        <a href="${pageContext.request.contextPath}/maker/view-loan-applications" class="btn btn-secondary"><spring:message code="button.cancel"/></a>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

    <script>
        $(document).ready(function () {
            var now = new Date();
            var month = (now.getMonth() + 1);
            var day = now.getDate();
            if (month < 10)
                month = "0" + month;
            if (day < 10)
                day = "0" + day;
            var today = now.getFullYear() + '-' + month + '-' + day;
            $('#datePicker').val(today);
        });
    </script>



<!-- Search Modal -->
<div class="spinner">
    <div id="loading"></div> <!-- Loading spinner -->
</div>


</body>

</html>