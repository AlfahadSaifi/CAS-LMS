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
    <div class="container my-4 card shadow">
            ${message}
                <div class="container-fluid">
                <div class="header">
                <h1 class="mt-4 mb-4" >Create City</h1>

                </div>

                    <form:form action="edit-city" method="post" modelAttribute="cityDTO" style="padding-top:5px;">
                        <div class="row">
                            <div class="col-md-6">

                                <!-- Left side -->
                                <form:input hidden="true" path="id"/>
                                <form:input hidden="true" path="metaData.recordStatus"/>

                                <div class="form-group">
                                    <form:label path="stateDto.country.countryName" required="true" >Country  <span style="color:red">*</span></form:label><br>
                                    <select id="countryDropDown" class="form-control" path="stateDto.country.countryName" required>
                                       <option value="">Select Country</option>
                                       <c:forEach items="${countries}" var="country">
                                           <option value="${country.countryName}">${country.countryName}</option>
                                       </c:forEach>
                                    </select>

                                </div>

                                <div class="form-group">
                                    <label for="cityCode">City Code  <span style="color:red">*</span></label>
                                    <form:input path="cityCode" class="form-control" id="cityCode" />
                                    <form:errors path="cityCode"  cssClass="error"/>

                                </div>

                                <div class="form-group">
                                    <label for="cityMICRCode">City MICR Code </label>
                                    <form:input path="cityMICRCode" class="form-control" id="cityMICRCode" />
                                </div>
                            </div>

                            <div class="col-md-6">
                                <!-- Right side -->

                                <div class="form-group">
                                    <form:label path="stateDto.stateName">State <span style="color:red">*</span></form:label><br>
                                    <form:select path="stateDto.id" class="form-control" id="stateDropDown" required="required">
                                        <form:option value="">Select State</form:option>
                                    </form:select>
                                    </div>

                                <div class="form-group">
                                    <label for="cityName">City Name <span style="color:red">*</span></label>
                                    <form:input path="cityName" class="form-control" id="cityName" readonly="true"/>
                                    <form:errors path="cityName"  cssClass="error"/>

                                </div>

                                <div class="form-group">
                                    <label for="stdCode">STD Code:</label>
                                    <form:input path="stdCode" class="form-control" id="stdCode" />
                                </div>

                            </div>

                        </div>


                     <div class="footer">
                     <div class="container-fluid">
                     <div class="row">
                     <div class="col-sm-12">
                     <div class="float-left">
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/" role="button"><spring:message code="button.back"/></a>
                     </div>
                     <div class="float-right">
                             <button type="submit" name ="action" value="save" class="btn btn-primary">Save</button>
                             <button type="button" name="action" value="cancel" class="btn btn-secondary">Cancel</button>
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
