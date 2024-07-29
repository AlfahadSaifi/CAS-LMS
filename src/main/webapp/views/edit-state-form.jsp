<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!--Ashish Goyal-->

<!DOCTYPE html>
<html xml:lang>
<head>
    <meta charset="UTF-8">
    <title>Edit State</title>
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
    </style>
</head>
<body>
${message}
    <div class="container-fluid">
    <div class="header">
    <h1 class="mt-2 mb-3" >Edit State</h1>



    </div>


        <form:form action="../edit-state-form/${stateDto.id}" method="post" modelAttribute="stateDto" style="padding-top:5px;">
            <div class="row">
                <div class="col-md-6">
                    <!-- Left side -->
                    <div class="form-group">

                        <label for="stateCode">State Code:</label>
                        <form:input path="stateCode" class="form-control" id="stateCode" value="${stateDto.stateCode}" />
                    </div>
                    <div class="form-group">
                        <form:label path="country.countryName">Country:</form:label><br>
                                <form:select path="country.countryName"  class="form-control">
                                    <form:option value="">Select Country</form:option>
                                    <form:options items="${countries}" itemValue="countryName" itemLabel="countryName"  />
                        </form:select>

                    </div>
                </div>
                <div class="col-md-5">
                    <!-- Right side -->
                    <div class="form-group">
                        <label for="stateName">State Name:</label>
                        <form:input path="stateName" class="form-control" id="stateName" value="${stateDto.stateName}" disabled="true"/>
                    </div>
                    <div class="form-group">
                        <label for="region">Region:</label>
                        <form:input path="region" class="form-control" id="region" value="${stateDto.region}" />
                    </div>
                </div>
            </div>

             <div class="footer">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-sm-12">
                            <!-- Buttons -->
                            <div class="float-right">
                                <button type="submit" name="saveButton" value="saveAndRequest" class="btn btn-primary">Edit and Request Approval</button>
                                <button type="button" class="btn btn-secondary">Cancel</button>
                            </div>
                        </div>
                    </div>
                </div>
             </div>
</div>
    </form:form>

    </div>



</body>
</html>

