<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!--Unzala-->

<!DOCTYPE html>
<html xml:lang>
<head>
    <meta charset="UTF-8">
    <title>Update Country</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .footer {
            padding-bottom:15px;
            padding-top:10px;
        }
        .header{
            border-bottom: 2px solid #dee2e6;

        }

        .form-group{
            width:70%;
        }

        .btn-status.active label.btn-primary {
            background-color: #007bff;
        }

    </style>
</head>
<body>
<%@ include file="navbar.jsp"%>
<!--Unzala-->
    <div class="container my-4 card shadow">
        ${message}
            ${approval}

            <div class="container-fluid">
                <div class="header">
                <h1 class="mt-4 mb-4">Update Country</h1>
                </div>

                <form:form action="edit-country" method="post" modelAttribute="countryDTO" style="padding-top:20px;">

                    <div class="row">
                        <div class="col-md-6">
                            <!-- Left side -->

                           <form:input hidden="true" path="id"/>
                           <form:input hidden="true" path="metaData.recordStatus"/>

                            <div class="form-group">
                                <label for="countryGroup">Country Group </label>
                                <form:select path="countryGroup" class="form-control" id="countryGroup">
                                    <form:option value="">Select Country Group</form:option>
                                    <form:options items="${countryGroups}" />
                                </form:select>
                            </div>
                            <div class="form-group">
                                <label for="countryIsdCode">Country ISD Code <span style="color: red; padding:3px;">*</span></label>
                                <form:input path="countryIsdCode" class="form-control" id="countryIsdCode" />
                            </div>
                            <div class="form-group">
                                <label for="nationality">Nationality <span style="color: red; padding:3px;">*</span></label>
                                <form:input path="nationality" class="form-control" id="nationality" />
                            </div>
                             <!-- Status button -->
                               <div class="form-group">
                                   <label>Status:</label>
                                   <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                       <label class="btn btn-primary btn-status active ">
                                           <input type="radio" name="status" id="statusActive" value="active"  > Active
                                       </label>
                                       <label class="btn btn-secondary btn-status ">
                                           <input type="radio" name="status" id="statusInactive" value="inactive"  > Inactive
                                       </label>
                                   </div>
                               </div>
                        </div>

                        <div class="col-md-6">
                            <!-- Right side -->
                            <div class="form-group">
                                <label for="countryName">Country Name <span style="color: red; padding:3px;">*</span></label>
                                <form:input path="countryName" class="form-control" id="countryName" readonly="true" />
                            </div>
                            <div class="form-group">
                                <label for="countryIsoCode">Country ISO Code <span style="color: red; padding:3px;">*</span></label>
                                <form:input path="countryIsoCode" class="form-control" id="countryIsoCode" />
                            </div>
                            <div class="form-group">
                                <label for="region">Region </label>
                                <form:input path="region" class="form-control" id="region" />
                            </div>
                        </div>
                    </div>
                    <!-- Footer with buttons -->
                        <div class="footer">
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-sm-12">

                                        <!-- Buttons -->
                                        <div class="float-left">
                                                                 <a class="btn btn-primary" href="${pageContext.request.contextPath}/" role="button"><spring:message code="button.back"/></a>
                                        </div>
                                        <div class="float-right">

                                            <button type="submit" name="action" value="save" class="btn btn-primary">Update</button>

                                            <button type="button" name="action" value="cancel" class="btn btn-secondary">Cancel</button>

                                        </div>


                                    </div>
                                </div>
                            </div>
                        </div>
                </form:form>

            </div>


    </div>


     <!-- JavaScript for toggling status -->


<script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const statusButtons = document.querySelectorAll(".btn-status input[type='radio']");

        statusButtons.forEach(function (button) {
            button.addEventListener("change", function () {

                document.querySelectorAll(".btn-status").forEach(function (label) {
                    label.classList.remove("active");
                });


                this.parentElement.classList.add("active");
            });
        });
    });
</script>



</body>
</html>
