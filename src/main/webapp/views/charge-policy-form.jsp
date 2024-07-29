<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@page isELIgnored = "false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Charge Policy</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

    <style>
        #loading {
          display: none;
          position: fixed;
          top: 0;
          left: 0;
          width: 100%;
          height: 100%;
          background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent black background */
          z-index: 9999;
        }

        .spinner {
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
        .horizontalLine
        {
            border-top: 3px solid #000;
            margin-bottom: 0px;
        }
        .parameter-table-head{
            background-color: #f2f2f2;
        }
        .table-body
        {
             background-color: #f8f8f85c;
        }
        .dropdown
        {
            width: 64%;
            height: 39px;
            border-radius: 6px;
            padding-left: 9px;
            font-size: 14px;
        }
        .asterisk
        {
            color:red;
        }
        .codeName
        {
            display: flex;
            justify-content: space-between;
        }
        .blue{
            font-size:15px;
            color:blue;
        }
        .labelName
        {
            font-weight: bold;
            font-size: 19px;
        }
        .buttonClass
        {
            display: flex;
            justify-content: flex-end;
        }
        .innerBtnClass
        {
            margin-right: 10px;
        }
        .headerdiv
        {
            margin-right:20px;
            display: flex;
            justify-content: space-between;
        }
        .errorColor
        {
            color:red;
        }
    </style>

</head>
<body>
<div id="loading">
    <div class="spinner"></div> <!-- Loading spinner -->
</div>
    <%@ include file="navbar.jsp"%>
    <div class="container my-4 card shadow">


        <form:form action="charge-policy" modelAttribute="chargePolicy" method="post" id="chargePolicyForm">
            <div>
                <div class="row justify-content-between align-items-center mb-3 my-4" >
                 <div class="col-auto">
                    <h2><spring:message code="policy.heading"/></h1>
                 </div>
                    <div class="buttonClass">
                        <button type="submit" name="action" value="save" class="innerBtnClass btn btn-primary"><spring:message code="button.save" /></button>
                        <button type="submit" name ="action" value="saveAndRequest" class="innerBtnClass btn btn-primary"><spring:message code="button.saveAndRequest" /></button>
                        <input type="hidden" name="action" value="">
                        <button type="button" class="innerBtnClass btn btn-secondary" onclick="backToChargePolicyDashboard()"><spring:message code="button.cancel" /></button>
                    </div>
                </div>
                <hr>
                <br>
                <div class="headerdiv">
                    <div>
                        <input type="submit" value="<spring:message code="policy.allFields"/>" disabled/><span class="blue"><spring:message code="policy.required"/><span class="asterisk">*</span></span>
                        </div>
                        <button id="addRow" type="button" class="innerBtnClass btn btn-primary"><spring:message code="button.add" /></button>
                        </div>
                        <br>
                    </div>
                    <div class="codeName">
                        <div class="form-group col-sm-4" style="">
                        <label for="policyCode" class="labelName"><spring:message code="policy.code.label" /></label><span class="asterisk">*</span>
                         <c:if test="${empty edit}">
                             <form:input type="text" class="form-control" id="policyCode" placeholder="Policy Code" required="true" path="policyCode"/>
                         </c:if>
                          <c:if test="${not empty edit}">
                             <form:input type="text" class="form-control" id="policyCode" placeholder="Policy Code" path="policyCode" readonly="true"/>
                          </c:if>

                        <form:errors class="errorColor" path="policyCode"/>
                        <h5 class="errorColor">${errorMessage}</h5>
                    </div>
                    <div class="form-group col-sm-4" style=" float:right">
                        <label for="policyName" class="labelName"><spring:message code="policy.name.label" /></label><span class="asterisk">*</span>


                         <c:if test="${empty edit}">
                            <form:input type="text" class="form-control" id="policyName" placeholder="Enter Policy Name" required="true" path="policyName"/>
                         </c:if>
                          <c:if test="${not empty edit}">
                            <form:input type="text" class="form-control" id="policyName" placeholder="Enter Policy Name" required="true" path="policyName" readonly="true"/>
                          </c:if>
                        <form:errors class="errorColor" path="policyName"/>
                    </div>
                    </div>
                    <br>
                    <hr>
                    <table class="table table-bordered" id="chargeParameters">
                        <thead class="parameter-table-head">
                        <tr>
                        <th><spring:message code="charge.label" /><span class="asterisk">*</span></th>
                        <th><spring:message code="operator.label" /><span class="asterisk">*</span></th>
                        <th><spring:message code="value.label" /><span class="asterisk">*</span></th>
                        <th><spring:message code="delete.label" /></th>

                        </tr>
                        </thead>
                    <tbody class="table-body">
                        <c:if test="${not empty chargePolicyParameter}">
                            <c:forEach var="item" items="${chargePolicyParameter}">
                            <tr>
                            <td>
                                <select class="dropdown chargeCode" name="chargeCodes" required="true">
                                    <c:forEach var="chargeDef" items="${charges}">
                                        <c:if test="${chargeDef.chargeDefinitionCode eq item.chargeDefinitionCode}">
                                        </c:if>
                                    </c:forEach>

                                    <c:forEach var="charge" items="${charges}" >
                                        <option  value="${charge.chargeDefinitionCode}">${charge.chargeName}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                <select class="dropdown" name="operator" required="true">
                                    <c:forEach var="operatorName" items="${operatorList}">
                                        <option value="${operatorName}" >${operatorName}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                <input type="number" class = "chargeValues" min="0" id="chargeValue"  name="value" required="true" class="dropdown" placeholder="${item.value}">
                            </td>
                            <td>
                                <button class="deleteRow innerBtnClass btn btn-secondary">-</button>
                            </td>
                        <tr>
                        </c:forEach>
                        </c:if>
                        <c:if test="${empty chargePolicyParameter}">
                        </c:if>
                    </tbody>

                </table>
                <br>
                <hr>
            </div>
        </form:form>

    </div>

    <script>
            $(document).ready(function(){
                $('#loading').hide();

                $("#addRow").click(function(){
                    var newRow = '<tr>' +
                    '<td>' +
                    '<select class="dropdown chargeCode" name="chargeCodes" required="true">' +
                    '<option value="" selected disabled hidden>Charge</option>';
                    <c:forEach var="charge" items="${charges}">
                        newRow += '<option value="${charge.chargeDefinitionCode}">${charge.chargeName}</option>';
                    </c:forEach>

                    newRow += '</select>' +
                    '</td>' +
                    '<td>' +
                    '<select class="dropdown" name="operator" required="true">' +
                    '<option value="" selected disabled hidden>Operator</option>';

                    <c:forEach var="operatorName" items="${operatorList}">
                        newRow += '<option value="${operatorName}">${operatorName}</option>';
                    </c:forEach>

                    newRow += '</select>' +
                        '</td>' +
                        '<td>' +
                        '<input type="number" class="chargeValues" min="0" name="value" required="true" class="dropdown" placeholder="value"/>' +
                        '</td>' +
                        '<td>' +
                        '<button class="deleteRow innerBtnClass btn btn-secondary">-</button>' +
                        '</td>'+
                        '</tr>';
                    $(".table-body").append(newRow);
                    });
                    $(".table-body").on("click", ".deleteRow", function(){
                    $(this).closest("tr").remove();
                });



                   $('#chargePolicyForm').submit(function(event) {
                       $('#loading').show();
                       console.log('Form submitted');

                       // Flag to track form validity
                       var isValid = true;

                       // Array to store AJAX promises
                       var ajaxPromises = [];

                       // Iterate over rows to validate charge codes and values
                       $('#chargeParameters tbody tr').each(function() {
                           var selectedChargeCode = $(this).find('.chargeCode').val();
                           var chargeValue = parseFloat($(this).find('.chargeValues').val());

                           console.log("charge code: " + selectedChargeCode);
                            if (selectedChargeCode !== undefined) {
                               // AJAX call for each row
                               var ajaxPromise = $.ajax({
                                   url: '${pageContext.request.contextPath}/maker/charge-details/' + selectedChargeCode,
                                   method: 'GET',
                                   dataType: 'json'
                               }).then(function(data) {
                                   console.log('data: ', data);
                                   var chargeName = data.chargeDefinition.chargeName;
                                   console.log('charge name = ', chargeName);

                                   var minAmount = parseFloat(data.chargeDefinition.minimumAmount);
                                   var maxAmount = parseFloat(data.chargeDefinition.maximumAmount);

                                   console.log('minimum', minAmount);
                                   console.log('maximum', maxAmount);

                                   if (chargeValue < minAmount || chargeValue > maxAmount) {
                                       alert('Charge value for charge code: ' + selectedChargeCode + ' and charge name: ' + chargeName + ' must be between ' + minAmount + ' and ' + maxAmount);
                                       $('#loading').hide();
                                       isValid = false;
                                   }
                               }, function(xhr, status, error) {
                                   console.error(xhr.responseText);
                                   isValid = false;
                               });
                            }
                           // Push the promise to the array
                           ajaxPromises.push(ajaxPromise);

                       });



                       // When all AJAX requests are complete
                       $.when.apply($, ajaxPromises).then(function() {
                           // Submit the form if all validations passed
                           if (isValid) {
                               $('#chargePolicyForm').off('submit').submit();
                               return true;
                           }
                       });

                       // Return false to prevent immediate form submission
                       return false;
                   });


            });

                    function backToChargePolicyDashboard() {
                        window.location.href = "charge-policy-table";
                    }
        </script>

</body>
</html>
