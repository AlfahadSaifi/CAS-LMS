<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType ="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Loan Product</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
   <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
   <style>
       .error {
           color: red;
       }
   </style
</head>
<body>
<%@ include file="navbar.jsp"%>
    <div class="container my-4 card shadow">
        <form:form action="update-loan-product" method="post" modelAttribute="loanProduct">
        <form:hidden path="productId"/>
        <form:hidden path="metaData.recordStatus"/>
            <div class="row justify-content-between align-items-center mb-3 my-4">
                <div class="col-auto">
                    <h2>Create Loan Product</h2>
                </div>
                <div class="col-auto">
                    <input type="submit" value="Submit" class="btn btn-primary"/>
                </div>
            </div>

            <div class="form-container">
                <div class="row mb-3">
                    <div class="form-left col-md-6 mb-3">
                        <div class="mb-3">
                           <label for="productCode">Enter Loan Product Code: </label>
                           <form:input path="productCode" id="productCode" class="form-control" placeholder="Enter Loan Product Code" required="true" readonly="true"/>
                            <form:errors path="productCode" cssClass="error"/>
                        </div>

                        <div class="mb-3">
                           <label for="productType">Select Product Type code: </label>
                           <form:select path="productTypeCode" id="productTypeCode" class="form-control">
                               <form:option value="">Select Product Type</form:option>
                               <form:options items="${productTypeCodes}" />
                           </form:select>
                        </div>
                         <div class="mb-3">
                               <label for="productDescription">Enter Product Description: </label>
                               <form:textarea path="productDescription" id="productDescription" class="form-control" placeholder="Enter Product Description" rows="5" required="true" onkeyup="updateCharacterCount()"/>
                               <form:errors path="productDescription" cssClass="error"/>
                               <small id="char-count" class="form-text text-muted">Remaining characters: 100</small>
                         </div>
                    </div>
                    <div class="form-right col-md-6 mb-3">
                        <div class="mb-3">
                               <label for="productName">Enter Product Name: </label>
                               <form:input path="productName" id="productName" class="form-control" placeholder="Enter Product Name" required="true" />
                               <form:errors path="productName" cssClass="error"/>
                        </div>
                        <div class="mb-3">
                           <label>&nbsp;</label>
                           <input id="productTypeDesc" class="form-control" placeholder="Product Type Description" disabled="true" />
                        </div>
                        <div class="mb-3">
                            <label for="rateOfInterest">Enter Rate of Interest: </label>
                            <form:input path="rateOfInterest" id="rateOfInterest" class="form-control" placeholder="Rate of Interest (in %)" type="number" required="true" />
                            <form:errors path="rateOfInterest" cssClass="error"/>
                        </div>
                        <div>
                            <label for="securityType">Select Security Type: </label>
                                 <form:select path="securityType" id="securityType" class="form-control">
                                  <form:option value="">Select one option</form:option>
                                   <form:option value="GOLD">GOLD</form:option>
                                    <form:option value="PROPERTY">PROPERTY</form:option>
                                     <form:option value="VEHICLE">VEHICLE</form:option>
                                     <form:option value="NONE">NONE</form:option>
                               </form:select>
                        </div>
                    </div>
                </div>
            </div>



            <div class="container">
                    <h2>Map New Policies</h2>
                    <table class="table policy-table">
                        <thead>
                            <tr>
                                <th>Policy Type</th>
                                <th>Policy Code</th>
                                <th>Policy Name</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Charge Policy</td>
                                <td>
                                    <form:select path="chargePolicyCode" id="chargePolicyCode" class="form-control" >
                                        <form:option value="">Select Charge Policy Code</form:option>
                                        <form:options items="${chargePolicyCodes}" />
                                    </form:select>
                                </td>
                                <td>
                                    <input id="chargePolicyName" class="form-control" placeholder="Charge Policy Name" disabled="true" />
                                </td>
                            </tr>
                            <tr>
                                <td>Eligibility Policy</td>
                                <td>
                                    <form:select path="eligibilityPolicyCode" id="eligibilityPolicyCode" class="form-control">
                                          <form:option value="">Select Eligibility Policy Code</form:option>
                                          <form:options items="${eligibilityPolicyCodes}" />
                                    </form:select>
                                </td>
                                <td>
                                    <input id="eligibilityPolicyName" class="form-control" placeholder="Eligibility Policy Name" disabled="true" />
                                </td>
                            </tr>
                            <tr>
                                <td>Repayment Policy</td>
                                <td>
                                    <form:select path="repaymentPolicyCode" id="repaymentPolicyCode" class="form-control" >
                                         <form:option value="">Select Repayment Policy Code</form:option>
                                         <form:options items="${repaymentPolicyCodes}" />
                                    </form:select>
                                </td>
                                <td>
                                    <input id="repaymentPolicyName" class="form-control" placeholder="Repayment Policy Name" disabled="true" />
                                </td>
                            </tr>
                        </tbody>
                    </table>

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
               window.location.href = "loan-product-dashboard";
           }

           function updateCharacterCount() {
               var textArea = document.getElementById("productDescription");
               var counter = document.getElementById("char-count");
               var maxLength = 100;
               var currentLength = textArea.value.length;
               counter.textContent="Remaining characters: " +(maxLength - currentLength);
           }

           function bindChangeEvent(elementId, url, successCallback, errorCallback) {
               $(document).ready(function() {
                   $('#' + elementId).change(function() {
                       var selectedValue = $(this).val();
                       if (selectedValue) {
                           $.ajax({
                               url: url,
                               type: 'GET',
                               data: { selectedValue: selectedValue },
                               success: successCallback,
                               error: errorCallback
                           });
                       }
                   });
               });
           }

           bindChangeEvent('productTypeCode', 'fetch-product-type-description',
               function(response) {
                   $('#productTypeDesc').val(response);
               },
               function(xhr, status, error) {
                   console.error('Error fetching Product Type Description:', error);
               }
           );

           bindChangeEvent('chargePolicyCode', 'fetch-charge-policy-name',
               function(response) {
                   $('#chargePolicyName').val(response);
               },
               function(xhr, status, error) {
                   console.error('Error fetching Charge Policy Name:', error);
               }
           );

           bindChangeEvent('repaymentPolicyCode', 'fetch-repayment-policy-name',
               function(response) {
                   $('#repaymentPolicyName').val(response);
               },
               function(xhr, status, error) {
                   console.error('Error fetching Repayment Policy Name:', error);
               }
           );

           bindChangeEvent('eligibilityPolicyCode', 'fetch-eligibility-policy-name',
               function(response) {
                   $('#eligibilityPolicyName').val(response);
               },
               function(xhr, status, error) {
                   console.error('Error fetching Eligibility Policy Name:', error);
               }
           );

       </script>
</body>
</html>