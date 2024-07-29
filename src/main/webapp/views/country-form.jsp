<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<!--Unzala-->

<!DOCTYPE html>
<html xml:lang>
<head>
    <meta charset="UTF-8">
    <title>Create Country</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>

        .footer{
            padding-top:10px;
            padding-bottom:15px;
        }
        .header{
            border-bottom: 1px solid grey;
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

               <div class="mt-4" style="background-color:light-blue">
                    ${message}
                    ${approval}
               </div>


                    <div class="container-fluid">
                        <div class="header" style="display:flex;align-items:center;justify-content:space-between;">

                            <h1 class="mt-4 mb-4 large">
                            <spring:message code="header.createCountry"/>
                            </h1>
                            <div style="padding-left:60%">
                            </div>

                        </div>
                        <form:form action="new-country" method="post" modelAttribute="countryDto" style="padding-top:20px;">
                            <div class="row">
                                <div class="col-md-6">
                                    <!-- Left side -->
                                    <div class="form-group">
                                        <label for="countryGroup">
                                            <spring:message code="label.country.countryGroup"/>

                                        </label>
                                        <form:select path="countryGroup" class="form-control" id="countryGroup">
                                            <form:option value=""><spring:message code="label.country.SelectCountryGroup"/></form:option>
                                            <form:options items="${countryGroups}" />
                                        </form:select>
                                        <form:errors path="countryGroup" cssClass="error" style="color: red; font-size: 0.9em; margin-top: 5px;"/>


                                    </div>

                                    <div class="form-group">
                                        <label for="countryIsdCode">
                                        <spring:message code="label.country.countryIsdCode"/>
                                        <span style="color: red; padding:3px;">*</span>
                                        </label>
                                        <form:input path="countryIsdCode" class="form-control" id="countryIsdCode" />
                                        <form:errors path="countryIsdCode" cssClass="error" style="color: red; font-size: 0.9em; margin-top: 5px;"/>
                                    </div>

                                    <div class="form-group">
                                        <label for="nationality">
                                            <spring:message code="label.country.nationality"/>
                                            <span style="color: red; padding:3px;">*</span>
                                        </label>
                                        <form:input path="nationality" class="form-control" id="nationality" />
                                        <form:errors path="nationality" cssClass="error" style="color: red; font-size: 0.9em; margin-top: 5px;"/>

                                    </div>

                                     <!-- Status button -->
                                       <div class="form-group">
                                           <label>
                                           <spring:message code="label.country.Status"/>
                                           </label>

                                           <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                               <label class="btn btn-primary btn-status active">
                                                   <input type="radio" name="status" id="statusActive" value="active"  >
                                                   <spring:message code="label.country.active"/>
                                               </label>

                                               <label class="btn btn-secondary btn-status ">
                                                   <input type="radio" name="status" id="statusInactive" value="inactive"  >
                                                   <spring:message code="label.country.inactive"/>
                                               </label>
                                           </div>

                                       </div>
                                </div>

                                <div class="col-md-6">
                                    <!-- Right side -->
                                    <div class="form-group">
                                        <label for="countryName">
                                            <spring:message code="label.country.countryName"/>
                                            <span style="color: red; padding:3px;">*</span>
                                         </label>
                                        <form:input path="countryName" class="form-control" id="countryName" />
                                        <form:errors path="countryName" cssClass="error" style="color: red; font-size: 0.9em; margin-top: 5px;"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="countryIsoCode">
                                            <spring:message code="label.country.countryIsoCode"/>
                                            <span style="color: red; padding:3px;">*</span>
                                         </label>
                                        <form:input path="countryIsoCode" class="form-control" id="countryIsoCode" />
                                        <form:errors path="countryIsoCode" cssClass="error" style="color: red; font-size: 0.9em; margin-top: 5px;"/>
                                    </div>

                                    <div class="form-group">
                                        <label for="region">
                                            <spring:message code="label.country.region"/>
                                            <span style="color: red; padding:3px;">*</span>
                                         </label>
                                        <form:input path="region" class="form-control" id="region" />
                                        <form:errors path="region" cssClass="error" style="color: red; font-size: 0.9em; margin-top: 5px;"/>
                                    </div>






                                </div>

                            </div>

                            <!-- Footer with buttons -->
                                <div class="footer">
                                    <div class="container-fluid">
                                        <div class="row">

                                            <div class="col-sm-12">
                                                <!-- Buttons -->
                                                <div class="float-right">
                                                    <button type="submit" name="action" value="save" class="btn btn-primary"><spring:message code="button.country.save"/></button>
                                                    <button type="submit" name="action" value="saveAndRequest" class="btn btn-primary"><spring:message code="button.country.saveAndRequest"/></button>
                                                    <button type="button" name="action" value="cancel" class="btn btn-secondary"><spring:message code="button.country.cancel"/></button>
                                                </div>

                                                <div class="float-left">
                                                    <a href="show-country-list">
                                                    <button type="button" class="btn btn-primary">
                                                        <spring:message code="button.showAllCountries"/>
                                                    </button></a>
                                                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/" role="button"><spring:message code="button.back"/></a>

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