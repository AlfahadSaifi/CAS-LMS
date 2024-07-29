<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<!--Ashish Goyal-->

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create States</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .header{
            border-bottom:2px solid black;
            display:flex;


            justify-content: space-between;
        }
        .form-group{
                margin-left:10px;
                width: 70%
                }
        .error{
            color: red;
        }
        .footer{
            padding-top:10px;
            padding-bottom:15px;
        }
    </style>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="container my-4 card shadow">
    <h4 style="color:red; margin-left:20px; margin-top:7px" >${message}</h4>
        <div class="container-fluid">
        <div class="header">
        <h1 class="mt-2 mb-3" ><spring:message code="createState" /></h1>


        </div>

            <form:form action="submit-state-form" method="post" modelAttribute="stateDto" style="padding-top:5px;">
                <div class="row">
                    <div class="col-md-6">
                        <!-- Left side -->
                        <div class="form-group">
                            <label for="stateCode"><spring:message code="label.stateCode"/> <span style="color:red">*</span></label>
                            <form:input path="stateCode" class="form-control" id="stateCode" />
                            <form:errors path="stateCode"  cssClass="error"/>
                        </div>
                        <div class="form-group">
                            <form:label path="country.countryName" ><spring:message code="label.country"/> <span style="color:red">*</span></form:label><br>
                            <form:select path="country.countryName" class="form-control" required="true" >
                                <form:option value=""><spring:message code="selectCountry"/></form:option>
                                <form:options items="${countries}" itemValue="countryName" itemLabel="countryName" />

                            </form:select>

                        </div>
                    </div>
                    <div class="col-md-5">
                        <!-- Right side -->
                        <div class="form-group">
                            <label for="stateName"><spring:message code="label.stateName"/> <span style="color:red">*</span></label>
                            <form:input path="stateName" class="form-control" id="stateName"/>
                            <form:errors path="stateName"  cssClass="error"/>

                        </div>
                        <div class="form-group">
                            <label for="region"><spring:message code="label.region"/></label>
                            <form:input path="region" class="form-control" id="region" />
                        </div>
                    </div>
                </div>

                 <div class="footer">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-sm-12">
                                <!-- Buttons -->
                                 <div class="float-left">
                                                <a class="btn btn-primary" href="edit-form" role="button"><spring:message code="button.viewAllStates"/></a>
                                                <a class="btn btn-primary" href="${pageContext.request.contextPath}/" role="button"><spring:message code="button.back"/></a>
                                 </div>
                                <div class="float-right">
                                    <button type="submit" name="saveButton" value="save" class="btn btn-primary"><spring:message code="button.save"/></button>
                                    <button type="submit" name="saveButton" value="saveAndRequest" class="btn btn-primary"><spring:message code="button.saveRequestApproval"/></button>
                                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/" role="button"><spring:message code="button.cancel"/></a>
                                </div>
                            </div>
                        </div>
                    </div>
                 </div>
    </div>
        </form:form>

        </div>

</div>


<script>
    function updateCheckbox() {
            var checkbox = document.getElementById('activeCheckbox');
            var isActive = checkbox.checked;

            // Set the hidden field value to true or false based on checkbox state
            document.getElementById('activeField').value = isActive;

            // Submit the form
            document.getElementById('updateForm').submit();
        }
</script>
</body>
</html>

