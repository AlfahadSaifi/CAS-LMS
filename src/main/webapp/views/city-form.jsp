<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

                    <!--Unzala -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create City</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <style>
        .header{
            border-bottom:2px solid black;
        }
        .form-group{
                margin-left:10px;
                width: 70%
                }

        .error{
                color:red;
        }
        .footer{
            padding-bottom:15px;
            padding-top:10px;
        }
    </style>
</head>
<script>
     $(document).ready(
        function () {
            $('#countryDropDown').change(function() {
                var countryName = $(this).val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/maker/getStates',
                    type: 'GET',
                    data: { countryName: countryName },
                    success: function(states) {
                        $('#stateDropDown').empty().append('<option value="">Select State</option>');
                        $.each(states, function(index, state) {
                            $('#stateDropDown').append('<option value="' + state.id + '">' + state.stateName + '</option>');

                        });
                    },
                    error: function(xhr, textStatus, errorThrown){
                        console.error('Error : ' ,errorThrown);
                    }
                });
            });
        }
     );
</script>

<body>
    <%@ include file="navbar.jsp"%>
    <div class="container my-4 card shadow" >

            <div class="mt-4">
                ${message}
            <div>
            <div class="container-fluid">
            <div class="header">
            <h1 class="mt-4 mb-4" >Create City</h1>

            </div>

                <form:form action="new-city" method="post" modelAttribute="cityDTO" style="padding-top:5px;">
                    <div class="row">
                        <div class="col-md-6">

                            <!-- Left side -->

                            <div class="form-group">
                                <form:label path="stateDto.country.countryName" required="true" ><spring:message code="label.city.country"/>  <span style="color:red">*</span></form:label><br>
                                <select id="countryDropDown" class="form-control" path="stateDto.country.countryName" required>
                                   <option value=""><spring:message code="label.city.selectCountry"/></option>
                                   <c:forEach items="${countries}" var="country">
                                       <option value="${country.countryName}">${country.countryName}</option>
                                   </c:forEach>
                                </select>

                            </div>

                            <div class="form-group">
                                <label for="cityCode"><spring:message code="label.city.cityCode"/>  <span style="color:red">*</span></label>
                                <form:input path="cityCode" class="form-control" id="cityCode" />
                                <form:errors path="cityCode"  cssClass="error"/>

                            </div>

                            <div class="form-group">
                                <label for="cityMICRCode"><spring:message code="label.city.cityMICRCode"/> </label>
                                <form:input path="cityMICRCode" class="form-control" id="cityMICRCode" />
                            </div>
                        </div>

                        <div class="col-md-6">
                            <!-- Right side -->

                            <div class="form-group">
                                <form:label path="stateDto.stateName"><spring:message code="label.city.State"/> <span style="color:red">*</span></form:label><br>
                                <form:select path="stateDto.id" class="form-control" id="stateDropDown" required="required">
                                    <form:option value=""><spring:message code="label.city.selectState"/></form:option>
                                </form:select>
                                </div>

                            <div class="form-group">
                                <label for="cityName"><spring:message code="label.city.cityName"/> <span style="color:red">*</span></label>
                                <form:input path="cityName" class="form-control" id="cityName" />
                                <form:errors path="cityName"  cssClass="error"/>

                            </div>

                            <div class="form-group">
                                <label for="stdCode"><spring:message code="label.city.stdCode"/></label>
                                <form:input path="stdCode" class="form-control" id="stdCode" />
                            </div>

                        </div>

                    </div>


                 <div class="footer">
                 <div class="container-fluid">
                 <div class="row">
                 <div class="col-sm-12">
                 <div class="float-right">
                 <button type="submit" name ="action" value="save" class="btn btn-primary"><spring:message code="button.save"/></button>
                 <button type="submit" name="action" value="saveAndRequest" class="btn btn-primary"><spring:message code="button.saveRequestApproval"/></button>
                 <button type="button" name="action" value="cancel" class="btn btn-secondary"><spring:message code="button.cancel"/></button>
                 </div>
                 <div class="float-left">

                     <a href="show-city-list"<button type="button" class="btn btn-primary"><spring:message code="label.city.showAllCities"/> </button></a>
                     <a class="btn btn-primary" href="${pageContext.request.contextPath}/" role="button"><spring:message code="button.back"/></a>

                 </div>
                 </div>
                 </div>
                 </div>
                 </div>

                </form:form>
            </div>


    </div>


</body>
</html>

