<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Charge Definition Form</title>
    <style>
        table{
        margin-top:40px;
        margin-left:100px;}
        table td {
                padding: 20px; /* Adjust the padding value as needed */
            }
         .mandatory::after {
                                    content: '*';
                                    color: red;
                                }
        </style>

</head>
<body>
<%@ include file="navbar.jsp"%>
    <div class="container my-4 card shadow">

    <form:form action="modifyCharge" method="post" modelAttribute="chargeDefinitionDto">
     <div class="row justify-content-between align-items-center mb-3 my-4">
                   <div class="col-auto">
                       <h2><spring:message code="chargeDefinitionForm.label" /></h2>
                   </div>
                   <div class="col-auto">

                               <input type="submit" class="btn btn-primary"  name="action" value="UpdateAndRequest" />
                                        <input type="submit" class="btn btn-primary" name="action" value="cancel" />
                   </div>
               </div>
               <hr>
    <table>
                <tr>
                    <td>
                        <label for="chargeDefinitionCode" class="mandatory"><spring:message code="chargeDefinitionCode.label" /></label>
                         <form:input type="text" path="chargeDefinitionCode" readonly="true" />
                    </td>
                    <td>
                        <label for="chargeName" class="mandatory"><spring:message code="chargeName.label" /></label>
                        <form:input type="text" path="chargeName" required="true" />

                    </td>
                </tr>
                <tr>
                    <td><label for="maximumAmount"><spring:message code="maximumAmount.label" /></label>
                                <form:input type="number" step="0.01" path="maximumAmount"/>

                    <td><label for="minimumAmount"><spring:message code="minimumAmount.label" /></label>
                                <form:input type="number" step="0.01" path="minimumAmount"/>

                </tr>
                    <td><label for="description"><spring:message code="description.label" /></label>
                                <form:textarea id="description" path="description" rows="2" cols="30" /></td>
                <tr>
            </table>

    </form:form>

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.css">
     <link rel = "stylesheet" href  = "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
          <script src = "https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
          <script src = "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

</body>
</html>
