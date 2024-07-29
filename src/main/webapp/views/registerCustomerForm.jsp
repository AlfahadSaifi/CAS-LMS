<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Customer Form</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

     <style>

        /* Styling for loading screen */
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

        .row{
          width:100%;
        }

        .form-section {
            margin-bottom: 20px;
        }
        .form-section .form-title {
            background-color: #f0f0f0;
            padding: 10px 20px;
            border-top-left-radius: 5px;
            border-top-right-radius: 5px;
            cursor: pointer;
        }
        .form-section .form-content {
            display: none;
            padding: 20px;
            border: 1px solid #ced4da;
            border-top: none;
            border-bottom-left-radius: 5px;
            border-bottom-right-radius: 5px;
            background-color: #fff;
        }
        .form-section.active .form-title {
            background-color: #007bff;
            color: #fff;
        }
        .form-section.active .form-content {
            display: block;
        }

        .form-title {
            cursor: pointer;
        }

        .arrow-icon {
            display: inline-block;
            margin-left: 5px;
            transition: transform 0.4s ease;
        }

        .collapsed .arrow-icon {
            transform: rotate(-90deg);
        }


    </style>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(
            function () {

                function validatePersonalInfo() {
                    var isValid = true;

                    // Validate first name
                    var firstName = $('#firstName').val().trim();
                    if (firstName === '') {
                        $('#firstNameError').text('First name is required.');
                        isValid = false;
                    } else {
                        $('#firstNameError').text('');
                    }

                    // Validate full name
                    var fullName = $('#fullName').val().trim();
                    if (fullName === '') {
                        $('#fullNameError').text('Full name is required.');
                        isValid = false;
                    } else {
                        $('#fullNameError').text('');
                    }

                    // Validate gender
                    var gender = $('#gender').val();
                    if (gender === '') {
                        $('#genderError').text('Please select a gender.');
                        isValid = false;
                    } else {
                        $('#genderError').text('');
                    }

                    // Validate date of birth
                    var dateOfBirth = $('#dateOfBirth').val();
                    if (dateOfBirth === '') {
                        $('#dateOfBirthError').text('Date of birth is required.');
                        isValid = false;
                    } else {
                        $('#dateOfBirthError').text('');
                    }

                    // Validate contact number
                    var contactNumber = $('#contactNumber').val().trim();
                    if (contactNumber === '') {
                        $('#contactNumberError').text('Contact number is required.');
                        isValid = false;
                    } else if (!/^\d{10}$/.test(contactNumber)) {
                        $('#contactNumberError').text('Contact number should be exactly 10 digits.');
                        isValid = false;
                    } else {
                        $('#contactNumberError').text('');
                    }

                    // Validate email address
                    var emailAddress = $('#emailAddress').val().trim();
                    if (emailAddress === '') {
                        $('#emailAddressError').text('Email address is required.');
                        isValid = false;
                    } else if (!isValidEmail(emailAddress)) {
                        $('#emailAddressError').text('Please enter a valid email address.');
                        isValid = false;
                    } else {
                        $('#emailAddressError').text('');
                    }
                    return isValid;
                }


                function validateFinancialInfo() {
                    var isValid = true;

                    // Validate monthlyIncome
                    var monthlyIncome = $('#monthlyIncome').val().trim();
                    if (monthlyIncome === '') {
                        $('#monthlyIncomeError').text('Monthly income is required.');
                        isValid = false;
                    } else {
                        $('#monthlyIncomeError').text('');
                    }

                    // Validate monthlyExpense
                    var monthlyExpense = $('#monthlyExpense').val().trim();
                    if (monthlyExpense === '') {
                        $('#monthlyExpenseError').text('Monthly expense is required.');
                        isValid = false;
                    } else {
                        $('#monthlyExpenseError').text('');
                    }
                    return isValid;
                }


                function validateEmploymentInfo() {
                    var isValid = true;

                    // Validate organizationName
                    var organizationName = $('#organizationName').val().trim();
                    if (organizationName === '') {
                        $('#organizationNameError').text('Organization Name is required.');
                        isValid = false;
                    } else {
                        $('#organizationNameError').text('');
                    }

                    // Validate professionType
                    var professionType = $('#professionType').val().trim();
                    if (professionType === '') {
                        $('#professionTypeError').text('Profession type is required.');
                        isValid = false;
                    } else {
                        $('#professionTypeError').text('');
                    }

                    // Validate ageOfRetirement
                    var ageOfRetirement = $('#ageOfRetirement').val().trim();
                    if (ageOfRetirement === '') {
                        $('#ageOfRetirementError').text('Age of retirement is required.');
                        isValid = false;
                    } else {
                        $('#ageOfRetirementError').text('');
                    }

                    return isValid;
                }

                function isValidEmail(email) {
                    // Regular expression for email validation
                    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                    return emailRegex.test(email);
                }

                $('#submitBtn').click(function () {
                    var isValidPersonInfo = validatePersonalInfo();

                    var isValidFinancialInfo = validateFinancialInfo();

                    var isValidEmploymentInfo = validateEmploymentInfo();

                    var isValid = true;
                    if (!isValidPersonInfo) {
                        // If validation fails, open the PersonInfo section
                        if (!$('#personal-info-form').hasClass('show')) {
                            $('#personal-info-form').addClass('show');
                            $('.personal-section').addClass('active');
                            $('.personal').removeClass('collapsed');
                            $('.arrow-icon').removeClass('collapsed');
                        }
                        isValid= false;
                    }

                    if (!isValidFinancialInfo) {
                        // If validation fails, open the PersonInfo section
                        if (!$('#financial-info-form').hasClass('show')) {
                            $('#financial-info-form').addClass('show');
                            $('.finn-section').addClass('active');
                            $('.finn').removeClass('collapsed');
                            $('.arrow-icon').removeClass('collapsed');
                        }
                        isValid= false;
                    }

                    if (!isValidEmploymentInfo) {
                        // If validation fails, open the PersonInfo section
                        if (!$('#employment-info-form').hasClass('show')) {
                            $('#employment-info-form').addClass('show');
                            $('.emp-section').addClass('active');
                            $('.emp').removeClass('collapsed');
                            $('.arrow-icon').removeClass('collapsed');
                        }
                        isValid= false;
                    }
                    if(!isValid)return false;
                });


                $('#dateOfBirth').change(function() {
                    var inputDate = $(this).val();
                    var today = new Date().toISOString().slice(0, 10);
                    if (inputDate > today) {
                        $('#dateErrorMessage').text('Date must be before today.');
                        $('#dateErrorMessage').show(); // Show the error message
                        $(this).val(''); // Clear the input field
                    } else {
                        $('#dateErrorMessage').hide(); // Hide the error message
                    }
                });

                $('#firstName, #middleName, #lastName').on('input', function() {
                    var fullName = $('#firstName').val() + ' ' + $('#middleName').val() + ' ' + $('#lastName').val();
                    $('#fullName').val(fullName);
                });

                $('.form-section').addClass('collapsed');

                $('.form-title').on('click', function() {
                    $(this).parent().toggleClass('active').toggleClass('collapsed');
                });

                $('.spinner').hide();

                function addAddress() {
                    $('.spinner').show();

                    // Retrieve values from input fields
                    var country = $("#country").val();
                    var state = $("#state").val();
                    var district = $("#district").val();
                    var city = $("#city").val();
                    var flatNumber = $("#flatNumber").val();
                    var fullAddress = $("#fullAddress").val();
                    var addressType = $("#addressType").val();

                    // Create an object to get the address details
                    var addressData = {
                        "countryName": country,
                        "stateName": state,
                        "district": district,
                        "cityName": city,
                        "flatNumber": flatNumber,
                        "fullAddress": fullAddress,
                        "addressType": addressType
                    };


                    $.ajax({
                        type: "POST",
                        url: "${pageContext.request.contextPath}/maker/add-address",
                        data: JSON.stringify(addressData),
                        contentType: "application/json",
                        success: function (addressDTO) {
                            addAddressToTable(addressDTO);
                            $('.spinner').hide();
                            alert('Successfully added address');
                        },
                        error: function (xhr, status, error) {
                            $('.spinner').hide();
                            console.error('Error:', xhr.statusText);
                        }
                    });

                }

                // Function to add the address details to the table
                function addAddressToTable(addressDTO) {
                    // Get the table body
                    var tableBody = $("#addressTable").find('tbody');

                    // Check if the table contains only one row with "No address added" text
                    if (tableBody.find('tr').length === 1 && tableBody.find('tr td').html() === "No address added") {
                        tableBody.empty(); // Remove the "No address added" row
                    }

                    // Create a new row
                    var newRow = $("<tr>");

                    // Append cells to the new row
                    newRow.append($("<td>").text(addressDTO.countryName));
                    newRow.append($("<td>").text(addressDTO.stateName));
                    newRow.append($("<td>").text(addressDTO.district));
                    newRow.append($("<td>").text(addressDTO.cityName));
                    newRow.append($("<td>").text(addressDTO.flatNumber));
                    newRow.append($("<td>").text(addressDTO.fullAddress));
                    newRow.append($("<td>").text(addressDTO.addressType));

                    var deleteButton = $("<button>").attr("type", "button").addClass("btn btn-danger").text("Delete").click(function() {
                        deleteAddress(newRow.index());
                    });
                    var deleteCell = $("<td>").append(deleteButton);
                    newRow.append(deleteCell);

                    var addressIndex = $("#addressTable tbody tr").length;

                    // Append the new row to the table body
                    tableBody.append(newRow);

                    // Clear the form fields
                    $("#country").val("");
                    $("#state").val("");
                    $("#district").val("");
                    $("#city").val("");
                    $("#flatNumber").val("");
                    $("#fullAddress").val("");
                    $("#addressType").val("");
                    $("#pinCode").val("");
                }

                $("#addButton").click(
                    function () {
                        addAddress();
                    }
                );

                // populate state on change of country

                $('#country').change(function() {
                    $('.spinner').show();

                    var selectedCountry = $(this).val();
                    $.ajax({
                        url: '${pageContext.request.contextPath}/maker/fetch-states',
                        type: 'GET',
                        data: { countryName: selectedCountry },
                        success: function(data) {
                            $('.spinner').hide();

                            $('#state').empty().append('<option value="">Select</option>');
                            $.each(data, function(index, state) {
                                $('#state').append('<option value="' + state.stateName + '">' + state.stateName + '</option>');
                            });
                        },
                        error: function(xhr, textStatus, errorThrown){
                           console.error('Error : ' ,errorThrown);
                           $('.spinner').hide();
                        }
                    });
                });

                    // populate city on change of state
                    $('#state').change(function() {
                        $('.spinner').show();
                        var selectedState = $(this).val();
                        $.ajax({
                            url: '${pageContext.request.contextPath}/maker/fetch-cities',
                            type: 'GET',
                            data: { stateName: selectedState },
                            success: function(data) {
                                $('.spinner').hide();

                                $('#city').empty().append('<option value="">Select</option>');
                                $.each(data, function(index, city) {
                                    $('#city').append('<option value="' + city.cityName + '">' + city.cityName + '</option>');
                                });
                            },
                            error: function(xhr, textStatus, errorThrown){
                               console.error('Error : ' ,errorThrown);
                               $('.spinner').hide();
                            }
                        });
                    });

            }
        );

        function deleteAddress(index) {
            $('.spinner').show();
            $.ajax({
                url: '${pageContext.request.contextPath}/maker/delete-address',
                type: 'POST',
                data: { index: index },
                success: function(response) {
                    $('.spinner').hide();
                    $('#addressTable tbody tr:eq(' + index + ')').remove();

                    // if the table is empty add no data row
                    if ($('#addressTable tbody tr').length === 0) {
                        // Append row indicating no addresses added
                        var newRow = $('<tr>').append($('<td>').attr('colspan', 8).addClass('text-center').text('No address added'));
                        $('#addressTable tbody').append(newRow);
                    }
                },
                error: function(xhr, status, error) {
                    $('.spinner').hide();
                    console.error('Error deleting address:', error);
                }
            });
        }
    </script>

</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="container my-4 card shadow border rounded" id="professional-form-container">
  <form:form action="${pageContext.request.contextPath}/maker/register" method="post" role="form" modelAttribute="customer">

         <div class="form-section personal-section border-bottom py-3">
            <div class="form-title personal" data-toggle="collapse" data-target="#personal-info-form" aria-expanded="true" aria-controls="personal-info-form">
                <spring:message code="button.personalInfo"/>
                <span class="arrow-icon">&#9660;</span>
            </div>
            <div class="form-content collapse" id="personal-info-form">
                <div class="form-group row">
                    <div class="form-group col-md-4">
                        <label for="firstName" class="form-label"><spring:message code="label.firstName"/></label>
                        <span style="color: red;">*</span>
                        <form:input type="text" class="form-control" id="firstName" path="personInfoDTO.firstName" />
                        <form:errors path="personInfoDTO.firstName" style="color:red"/>
                        <span id="firstNameError" style="color: red;"></span>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="middleName" class="form-label"><spring:message code="label.middleName"/></label>
                        <form:input type="text" class="form-control" id="middleName" path="personInfoDTO.middleName" />
                    </div>
                    <div class="form-group col-md-4">
                        <label for="lastName" class="form-label"><spring:message code="label.lastName"/></label>
                        <form:input type="text" class="form-control" id="lastName" path="personInfoDTO.lastName" />
                    </div>
                </div>

                <div class="form-group">
                    <label for="fullName" class="form-label"><spring:message code="label.fullName"/></label>
                    <span style="color: red;">*</span>
                    <form:input type="text" class="form-control" id="fullName" path="personInfoDTO.fullName" readonly="true" required="true" />
                    <span id="fullNameError" style="color: red;"></span>
                </div>

                <div class="form-group row">
                    <div class="col-md-4">
                        <label for="gender" class="form-label"><spring:message code="label.gender"/></label>
                        <span style="color: red;">*</span>
                        <select class="form-control" id="gender" name="personInfoDTO.gender">
                            <option value="">Select Gender</option>
                            <option value="MALE">Male</option>
                            <option value="FEMALE">Female</option>
                            <option value="OTHER">Other</option>
                        </select>
                        <span id="genderError" style="color: red;"></span>
                        <form:errors path="personInfoDTO.gender" style="color:red"/>
                    </div>

                    <div class="col-md-4">
                        <label for="dateOfBirth" class="form-label"><spring:message code="label.dateOfBirth"/></label>
                        <span style="color: red;">*</span>
                        <form:input type="date" class="form-control" id="dateOfBirth" path="personInfoDTO.dateOfBirth" required="true"/>

                        <span id="dateErrorMessage" style="color: red; display: none;"></span>
                        <span id="dateOfBirthError" style="color: red;"></span>

                        <form:errors path="personInfoDTO.dateOfBirth" style="color:red"/>
                    </div>

                    <div class="col-md-4">
                        <label for="placeOfBirth" class="form-label"><spring:message code="label.placeOfBirth"/></label>
                        <form:input type="text" class="form-control" id="placeOfBirth" path="personInfoDTO.placeOfBirth" />
                    </div>
                </div>


                <div class="form-group row">
                    <div class="col-md-6">
                        <label for="contactNumber" class="form-label"><spring:message code="label.contactNumber"/></label>
                        <span style="color: red;">*</span>
                        <form:input type="text" class="form-control" id="contactNumber" path="contactNumber" pattern="\d{10}" title="Contact number should be exactly 10 digits" required="true"/>
                        <span id="contactNumberError" style="color: red;"></span>
                        <form:errors path="contactNumber" style="color:red"/>
                    </div>

                    <div class="col-md-6">
                        <label for="emailAddress" class="form-label"><spring:message code="label.emailAddress"/></label>
                        <span style="color: red;">*</span>
                        <form:input type="email" class="form-control" id="emailAddress" path="emailAddress" required="true"/>
                        <span id="emailAddressError" style="color: red;"></span>
                        <form:errors path="emailAddress" style="color:red"/>
                    </div>
                </div>
            </div>
        </div>

        <!-- Address Section -->

        <div class="form-section border-bottom pb-3">
            <div class="form-title" data-toggle="collapse" data-target="#address-info-form" aria-expanded="true" aria-controls="address-info-form">
                <spring:message code="all.address"/>
                <span class="arrow-icon">&#9660;</span>
            </div>
            <div class="form-content collapse" id="address-info-form">

                <div class="form-group row">
                    <div class="col-md-4">
                        <label for="country" class="form-label">
                        <spring:message code="label.country"/>
                        <span style="color: red; padding:3px;">*</span></label>
                        <select path="country" id="country" class="form-control">
                           <option value="">Select Country</option>

                           <c:forEach items="${countryDTOS}" var="country">
                               <option value="${country.countryName}">${country.countryName}</option>
                           </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <label for="state" class="form-label">
                        <spring:message code="label.state"/>
                        <span style="color: red; padding:3px;">*</span></label>
                        <select name="state" id="state" class="form-control">
                            <option value="">Select State</option>
                        </select>
                    </div>

                    <div class="col-md-4">
                        <label for="city" class="form-label">
                        <spring:message code="label.city"/>
                        </label>
                        <select name="city" id="city" class="form-control">
                            <option value="">Select City</option>
                        </select>
                    </div>
                </div>

                <div class="form-group row">
                    <div class="col-md-4">
                        <label for="flatNumber" class="form-label">
                        <spring:message code="label.flatNumber"/>
                        <span style="color: red; padding:3px;">*</span></label>
                        <input name="flatNumber" id="flatNumber" class="form-control" />
                    </div>
                    <div class="col-md-4">
                        <label for="district" class="form-label">
                        <spring:message code="label.district"/>
                        <span style="color: red; padding:3px;">*</span></label>
                        <input name="district" id="district" class="form-control" />
                    </div>
                    <div class="col-md-4">
                        <label for="pinCode" class="form-label">
                        <spring:message code="label.pinCode"/>
                        <span style="color: red; padding:3px;">*</span></label>
                        <input type="number" name="pinCode" id="pinCode" class="form-control" />
                    </div>
                </div>

                <div class="form-group">
                    <label for="fullAddress" class="form-label">
                    <spring:message code="label.fullAddress"/>
                    </label>
                    <textarea name="fullAddress" id="fullAddress" class="form-control" rows="4"></textarea>
                </div>

                <div class="form-group">
                    <label for="addressType" class="form-label">
                    <spring:message code="label.addressType"/>
                    <span style="color: red; padding:3px;">*</span></label>
                    <select name="addressType" id="addressType" class="form-control">
                        <option value="">Select Address Type</option>
                        <option value="Home">Home</option>
                        <option value="Work">Work</option>
                    </select>
                </div>

                <div class="form-group">
                    <button id="addButton" type="button" class="btn btn-primary"><spring:message code="button.add"/></button>
                    <button type="button" onclick="window.location.href='/cancelAddress'" class="btn btn-secondary"><spring:message code="button.clear"/></button>
                </div>



                <!-- Data Table -->
               <div class="row">
                   <div class="col-md-12">
                       <h3 class="text-center"><spring:message code="all.address"/></h3>
                       <table id="addressTable" class="table table-bordered ">
                           <thead class="thead-dark">
                               <tr>
                                   <th><spring:message code="label.country"/></th>
                                   <th><spring:message code="label.state"/></th>
                                   <th><spring:message code="label.district"/></th>
                                   <th><spring:message code="label.city"/></th>
                                   <th><spring:message code="label.flatNumber"/></th>
                                   <th><spring:message code="label.fullAddress"/></th>
                                   <th><spring:message code="label.addressType"/></th>
                                   <th><spring:message code="label.action"/></th>
                               </tr>
                           </thead>
                           <tbody>

                               <c:choose>
                                    <c:when test="${addressDTOS == null || addressDTOS.isEmpty()}">
                                        <tr>
                                           <td colspan="8" class="text-center">No address added</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${addressDTOS}" var="address" varStatus="loop">
                                          <tr>
                                              <td>${address.countryName}</td>
                                              <td>${address.stateName}</td>
                                              <td>${address.district}</td>
                                              <td>${address.cityName}</td>
                                              <td>${address.flatNumber}</td>
                                              <td>${address.countryName}</td>
                                              <td>${address.addressType}</td>
                                              <td>
                                                  <button type="button" class="btn btn-danger" onclick="deleteAddress(${loop.index})">Delete</button>
                                              </td>
                                          </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                           </tbody>
                       </table>
                   </div>
               </div>


            </div>
        </div>



        <div class="form-section emp-section border-bottom pb-3">
            <div class="form-title emp" data-toggle="collapse" data-target="#employment-info-form" aria-expanded="true" aria-controls="employment-info-form">
                <spring:message code="button.empDetails"/>
                <span class="arrow-icon">&#9660;</span>
            </div>
            <div class="form-content collapse" id="employment-info-form">

                <div class="form-group row">

                    <div class="form-group col-md-4">
                        <label for="ageOfRetirement">
                            <spring:message code="label.ageOfRetirement"/>
                        </label>
                        <span style="color: red;">*</span>
                        <form:input type="number" class="form-control" path="occupationInfoDTO.ageOfRetirement" id="ageOfRetirement"/>
                        <span id="ageOfRetirementError" style="color: red;"></span>

                        <form:errors path="occupationInfoDTO.ageOfRetirement" style="color:red"/>
                    </div>

                    <div class="form-group col-md-4">
                        <label for="totalYearOfExperience">
                            <spring:message code="label.totalYearOfExperience"/>
                       </label>
                       <form:input type="number" class="form-control" path="occupationInfoDTO.totalYearOfExperience" />
                    </div>

                    <div class="form-group col-md-4">
                        <label for="professionType">
                            <spring:message code="label.professionType"/>
                        </label>
                        <span style="color: red;">*</span>
                        <select class="form-control" id="professionType" name="occupationInfoDTO.professionType" >
                            <option value="">Select Profession Type</option>
                            <option value="SELF_EMPLOYED">Self Employed</option>
                            <option value="SALARIED">Salaried</option>
                        </select>

                        <span id="professionTypeError" style="color: red;"></span>
                        <form:errors path="occupationInfoDTO.professionType" style="color:red"/>

                    </div>
                </div>

                <div class="form-group row">
                    <div class="form-group col-md-4">
                        <label for="designation">
                            <spring:message code="label.designation"/>
                        </label>
                        <form:input type="text" class="form-control" path="occupationInfoDTO.designation" />
                     </div>
                     <div class="form-group col-md-4">
                        <label for="organizationName">
                            <spring:message code="label.organizationName"/>
                        </label>
                        <span style="color: red;">*</span>
                        <form:input type="text" class="form-control" path="occupationInfoDTO.organizationName" id="organizationName"/>
                        <span id="organizationNameError" style="color: red;"></span>

                        <form:errors path="occupationInfoDTO.organizationName" style="color:red"/>
                     </div>
                     <div class="form-group col-md-4">
                        <label for="organizationLocation">
                            <spring:message code="label.organizationLocation"/>
                        </label>
                        <form:input type="text" class="form-control" path="occupationInfoDTO.organizationLocation" />
                     </div>
                </div>
            </div>
        </div>



        <div class="form-section finn-section border-bottom pb-3">
            <div class="form-title finn" data-toggle="collapse" data-target="#financial-info-form" aria-expanded="true" aria-controls="financial-info-form">
                <spring:message code="button.financialDetails"/>
                <span class="arrow-icon">&#9660;</span>
            </div>
            <div class="form-content collapse" id="financial-info-form">

                <div class="form-group row">

                    <div class="form-group col-md-6">
                        <label for="monthlyIncome">
                            <spring:message code="label.monthlyIncome"/>
                        </label>
                        <span style="color: red;">*</span>
                        <form:input type="number" class="form-control" path="financialInfoDTO.monthlyIncome" id="monthlyIncome"/>
                        <span id="monthlyIncomeError" style="color: red;"></span>

                        <form:errors path="financialInfoDTO.monthlyIncome" style="color:red"/>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="rentIncome">
                            <spring:message code="label.rentIncome"/>
                        </label>
                        <form:input type="number" class="form-control" path="financialInfoDTO.rentIncome" />
                    </div>

                </div>

                <div class="form-group row">

                    <div class="form-group col-md-6">
                        <label for="monthlyExpense">
                            <spring:message code="label.monthlyExpense"/>
                        </label>
                        <span style="color: red;">*</span>
                        <form:input type="number" class="form-control" path="financialInfoDTO.monthlyExpense"  id="monthlyExpense"/>
                        <span id="monthlyExpenseError" style="color: red;"></span>

                        <form:errors path="financialInfoDTO.monthlyExpense" style="color:red"/>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="medicalExpense">
                            <spring:message code="label.medicalExpense"/>
                        </label>
                        <form:input type="number" class="form-control" path="financialInfoDTO.medicalExpense" />
                    </div>

                </div>
            </div>
        </div>

         <!-- Save Button -->
        <div class="text-center mb-3">
            <button id="submitBtn" type="submit" class="btn btn-primary"><spring:message code="button.saveNext"/></button>
        </div>
  </form:form>
</div>

<!-- Search Modal -->
<div class="spinner">
    <div id="loading"></div> <!-- Loading spinner -->
</div>

</body>
</html>
