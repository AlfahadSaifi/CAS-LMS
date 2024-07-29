<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false" contentType="text/html; charset=UTF-8" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.userdetails.UserDetails" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>Sidebar</title>
    <style>
       /* Custom colors */
       :root {
           --primary-color: #F8F9FA; /* Light Gray - aligns with navbar background color */
           --secondary-color: #6B7280; /* Gray */
           --bg-color: #FFFFFF; /* White */
           --text-color: #111827; /* Dark Gray */
           --accent-color: #D4AF37; /* Blue - aligns with navbar brand color */
           --hover-color: #85B8CC; /* Light blue */
           --light-gray: #E5E7EB; /* Light Gray */
           --light-secondary-color: #8B96A8; /* Lighter Gray */
           --transition-speed: 0.3s; /* Transition Speed */
       }

       /* Sidebar CSS */
       .sidebar {
           width: 21%; /* Adjust as needed */
           padding: 20px;
           background-color: var(--primary-color);
           color: var(--text-color);
           box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
           transition: width var(--transition-speed);
       }

       .sidebar:hover {
           width: 23%; /* Adjust as needed */
       }

       .btn,
       .btn-toggle,
       .btn-toggle-nav li a {
           width: 100%;
           max-width: 100%;
       }

       .btn-toggle,
       .btn-toggle-nav li a {
           background-color: transparent;
           color: var(--text-color);
           border: none;
           padding: 12px 20px;
           margin: 5px 0;
           font-size: 130%; /* Adjust font size for buttons */
           font-weight: 500;
           transition: background-color var(--transition-speed), color var(--transition-speed), transform var(--transition-speed);
           border-radius: 20px;
           font-family: 'Arial', sans-serif; /* Apply font to all buttons and links */
       }

       .btn-toggle::before {
           content: "\25B8"; /* Arrow symbol */
           margin-right: 10px;
           transition: transform var(--transition-speed) ease;
       }

       .btn-toggle[aria-expanded="true"]::before {
           transform: rotate(90deg);
       }

       .btn-toggle:hover,
       .btn-toggle-nav li a:hover,
       .btn-toggle:focus,
       .btn-toggle-nav li a:focus {
           background-color: var(--light-gray);
           color: var(--hover-color);
           transform: scale(1.1);
       }

       /* Adjusted CSS for positioning collapsed content */
       .collapse {
           position: relative;
           top: 0;
           left: 30%; /* Position the collapsed content to the right of the button */
           transform: translateX(-100%); /* Initially hide the collapsed content */
           z-index: 1;
           background-color: var(--primary-color);
           border-radius: 5px;
           box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
           margin-top: 10px;
           transition: max-height var(--transition-speed) ease-out, transform var(--transition-speed); /* Added transform transition */
           max-height: 0;
           overflow: hidden;
       }

       /* Show animation */
       .collapse.show {
           max-height: none;
           transform: translateX(0); /* Slide in from the right */
       }

       .btn-toggle-nav li a {
           display: block;
           padding: 10px 20px;
           border-radius: 15px;
           color: var(--text-color);
       }

       .btn-toggle.active,
       .btn-toggle.active::before {
           color: var(--accent-color) !important;
       }

       .btn-toggle-nav li a:hover,
       .btn-toggle-nav li a:focus {
           background-color: var(--light-gray);
           color: var(--hover-color);
           transform: scale(1.05);
           text-decoration: none;
       }

       .btn-toggle-nav li a:focus {
           outline: none; /* Remove focus outline */
       }

       .btn-toggle-nav li a:active {
           background-color: var(--light-gray); /* Darken background on click */
       }

       /* Responsive Styles */


       @media (max-width: 768px) {
           .sidebar {
               width: 20%;
               min-width: 250px;
               transition: width var(--transition-speed);
           }

           .sidebar:hover {
               width: 22%;
           }
       }
    </style>
</head>

<body>

<sec:authorize access="isAuthenticated()">
    <%
    String role = ((org.springframework.security.core.authority.SimpleGrantedAuthority)
    SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0]).getAuthority();
    if("ROLE_MAKER".equalsIgnoreCase(role))
    pageContext.setAttribute("role", "maker");
    if("ROLE_CHECKER".equalsIgnoreCase(role))
    pageContext.setAttribute("role", "checker");
    %>
</sec:authorize>


<div class="sidebar">
    <ul class="list-unstyled ps-0" style="list-style-type:none;">
        <li class="mb-1">
            <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed"
                    data-bs-toggle="collapse" data-bs-target="#policies-collapse" aria-expanded="false">
                Policies
            </button>
            <div class="collapse" id="policies-collapse">
                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                    <li>
                        <a href="${pageContext.request.contextPath}/${role}/make-repayment-policy" class="link-body-emphasis d-inline-flex text-decoration-none rounded">Repayment Policy
                        </a>
                    </li>
                    <li>
                        <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed inner-btn" data-bs-toggle="collapse" data-bs-target="#eligibility-collapse" aria-expanded="false">Eligibility policy
                        </button>
                        <div class="collapse" id="eligibility-collapse">
                            <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                <li><a href="${pageContext.request.contextPath}/maker/create-eligibility-policy" class="link-body-emphasis d-inline-flex text-decoration-none rounded">Create Eligibility Policy</a>
                                </li>
                                <li><a href="${pageContext.request.contextPath}/${role}/show-all-eligibility-policies" class="link-body-emphasis d-inline-flex text-decoration-none rounded">All Eligibility Policies</a>
                                </li>
                                <li><a href="${pageContext.request.contextPath}/${role}/eligibility-parameter" class="link-body-emphasis d-inline-flex text-decoration-none rounded">Eligibility Parameters</a>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed inner-btn"
                                data-bs-toggle="collapse" data-bs-target="#charge-collapse" aria-expanded="false">Charge Policy
                        </button>
                        <div class="collapse" id="charge-collapse">
                            <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                <li><a href="${pageContext.request.contextPath}/${role}/charge-definition"
                                       class="link-body-emphasis d-inline-flex text-decoration-none rounded">
                                    Charge Policy Definition
                                </a>
                                </li>
                                <li><a href="${pageContext.request.contextPath}/${role}/charge-policy-table"
                                       class="link-body-emphasis d-inline-flex text-decoration-none rounded">
                                    All Charge Policies
                                </a>
                                </li>
                            </ul>
                        </div>
                    </li>
                </ul>
            </div>
        </li>


        <li class="mb-1">
            <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed"
                    data-bs-toggle="collapse" data-bs-target="#products-collapse" aria-expanded="false">
                Products
            </button>
            <div class="collapse" id="products-collapse">
                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                    <li><a href="${pageContext.request.contextPath}/${role}/loan-product-dashboard"
                           class="link-body-emphasis d-inline-flex text-decoration-none rounded">
                        Loan Products
                    </a>
                    </li>
                    <li><a href="${pageContext.request.contextPath}/${role}/product-type-dashboard"
                           class="link-body-emphasis d-inline-flex text-decoration-none rounded">
                        Product Types
                    </a></li>
                </ul>
            </div>
        </li>


        <li class="mb-1">
            <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed"
                    data-bs-toggle="collapse" data-bs-target="#loan-application-collapse" aria-expanded="false">
                Loan Application
            </button>
            <div class="collapse" id="loan-application-collapse">
                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                    <li><a href="${pageContext.request.contextPath}/${role}/view-loan-applications"
                           class="link-body-emphasis d-inline-flex text-decoration-none rounded">
                        All Loan Applications
                    </a></li>
                </ul>
            </div>
        </li>
        <li class="mb-1">
            <a href="${pageContext.request.contextPath}/${role}/loan-account"
               class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed"
               data-bs-toggle="collapse" data-bs-target="#customer-collapse" aria-expanded="false">
                Loan Account
            </a>
        </li>

        <li class="mb-1">
            <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed"
                    data-bs-toggle="collapse" data-bs-target="#address-collapse" aria-expanded="false">
                Address Setup
            </button>
            <div class="collapse" id="address-collapse">
                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                    <li><a href="${pageContext.request.contextPath}/${role}/country-form"
                           class="link-body-emphasis d-inline-flex text-decoration-none rounded">
                       Country
                    </a>
                    </li>
                    <li><a href="${pageContext.request.contextPath}/${role}/state-form"
                           class="link-body-emphasis d-inline-flex text-decoration-none rounded">
                        State
                    </a></li>
                    <li><a href="${pageContext.request.contextPath}/${role}/city-form"
                           class="link-body-emphasis d-inline-flex text-decoration-none rounded">
                        City
                    </a></li>
                </ul>
            </div>
        </li>

        <li class="mb-1">
            <a href="${pageContext.request.contextPath}/${role}/receivable-payable"
               class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed"
               data-bs-toggle="collapse" data-bs-target="#customer-profile-collapse" aria-expanded="false">
                Receivable/Payable
            </a>
        </li>


        <li class="mb-1">
            <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed"
                    data-bs-toggle="collapse" data-bs-target="#receipt-payment-collapse" aria-expanded="false">
                Receipt/Payment
            </button>
            <div class="collapse" id="receipt-payment-collapse">
                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                    <li><a href="${pageContext.request.contextPath}/${role}/payment"
                           class="link-body-emphasis d-inline-flex text-decoration-none rounded">
                        Payment
                    </a>
                    </li>
                    <li><a href="${pageContext.request.contextPath}/${role}/receipt"
                           class="link-body-emphasis d-inline-flex text-decoration-none rounded">
                        Receipt
                    </a></li>
                </ul>
            </div>
        </li>


        <li class="mb-1">
            <a href="${pageContext.request.contextPath}/checker/allocation"
               class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed"
               data-bs-toggle="collapse" data-bs-target="#allocation-collapse" aria-expanded="false">
                Allocation
            </a>
        </li>


        <li class="mb-1">
            <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed"
                    data-bs-toggle="collapse" data-bs-target="#closure-collapse" aria-expanded="false">
                Closure
            </button>
            <div class="collapse" id="closure-collapse">
                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                    <li><a href="${pageContext.request.contextPath}/${role}/regular-closure"
                           class="link-body-emphasis d-inline-flex text-decoration-none rounded">
                        Loan Closure
                    </a>
                    </li>
                    <li><a href="${pageContext.request.contextPath}/${role}/early-closure"
                           class="link-body-emphasis d-inline-flex text-decoration-none rounded">
                        Early Closure
                    </a></li>
                </ul>
            </div>
        </li>

        <li class="mb-1">
            <a href="${pageContext.request.contextPath}/${role}/repay-schedule"
               class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed"
               data-bs-toggle="collapse" data-bs-target="#reschedule-collapse" aria-expanded="false">
                Repay Schedule
            </a>
        </li>
        <li class="mb-1">
            <a href="${pageContext.request.contextPath}/${role}/reschedule"
               class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed"
               data-bs-toggle="collapse" data-bs-target="#reschedule-collapse" aria-expanded="false">
                Reschedule
            </a>
        </li>


        <li class="mb-1">
            <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed"
                    data-bs-toggle="collapse" data-bs-target="#report-collapse" aria-expanded="false">
                Reports
            </button>
            <div class="collapse" id="report-collapse">
                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                    <li><a href="${pageContext.request.contextPath}/loan-summary"
                           class="link-body-emphasis d-inline-flex text-decoration-none rounded">
                        Loan Summary
                    </a>
                    </li>
                    <li><a href="${pageContext.request.contextPath}/disbursal-report"
                           class="link-body-emphasis d-inline-flex text-decoration-none rounded">
                        Loan Disbursal
                    </a></li>
                    <li><a href="${pageContext.request.contextPath}/repay-schedule-details"
                           class="link-body-emphasis d-inline-flex text-decoration-none rounded">
                        Repayment Schedule
                    </a>
                    </li>
                    <li><a href="${pageContext.request.contextPath}/receipt-report"
                           class="link-body-emphasis d-inline-flex text-decoration-none rounded">
                        Receipt
                    </a>
                    </li>
                </ul>
            </div>
        </li>


    </ul>
</div>
<script>
        document.addEventListener("DOMContentLoaded", function () {
            // Get all buttons that toggle a collapse element
            var collapseBtns = document.querySelectorAll('[data-bs-toggle="collapse"]');

            // Loop through each collapse button
            collapseBtns.forEach(function (btn) {
                // Add event listener for button click
                btn.addEventListener("click", function () {
                    // Get the target collapse element ID from the button's data attribute
                    var targetId = btn.getAttribute("data-bs-target");

                    // Get the collapse element by ID
                    var targetCollapse = document.querySelector(targetId);

                    // Check if it's an outer button
                    if (!btn.classList.contains('inner-btn')) {
                        // Get all collapse elements
                        var allCollapses = document.querySelectorAll('.collapse');

                        // Loop through all collapse elements
                        allCollapses.forEach(function (collapse) {
                            // Check if the collapse is not the target collapse
                            if (collapse !== targetCollapse) {
                                // Close the collapse element
                                collapse.classList.remove("show");
                                // Set aria-expanded attribute to false for accessibility
                                var collapseBtn = document.querySelector('[data-bs-target="#' + collapse.id + '"]');
                                if (collapseBtn) {
                                    collapseBtn.setAttribute("aria-expanded", "false");
                                }
                            }
                        });
                    } else {
                        // Get all inner collapse elements
                        var innerCollapses = btn.closest('.collapse').querySelectorAll('.collapse');

                        // Loop through all inner collapse elements
                        innerCollapses.forEach(function (innerCollapse) {
                            // Check if the inner collapse is not the target collapse
                            if (innerCollapse !== targetCollapse) {
                                // Close the inner collapse element
                                innerCollapse.classList.remove("show");
                                // Set aria-expanded attribute to false for accessibility
                                var collapseBtn = innerCollapse.parentElement.querySelector('[data-bs-target="#' + innerCollapse.id + '"]');
                                if (collapseBtn) {
                                    collapseBtn.setAttribute("aria-expanded", "false");
                                }
                            }
                        });
                    }

                    // Toggle the target collapse element's display if it's not null
                    if (targetCollapse) {
                        if (targetCollapse.classList.contains("show")) {
                            // If collapse element is currently shown, hide it
                            targetCollapse.classList.remove("show");
                            // Set aria-expanded attribute to false for accessibility
                            btn.setAttribute("aria-expanded", "false");
                        } else {
                            // If collapse element is currently hidden, show it
                            targetCollapse.classList.add("show");
                            // Set aria-expanded attribute to true for accessibility
                            btn.setAttribute("aria-expanded", "true");
                        }
                    }
                });
            });
        });

</script>
</body>

</html>