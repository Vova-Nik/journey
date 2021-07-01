<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/reststyle.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/static/favicon.ico" type="image/x-icon">
    <!-- <script src="https://vova-nik.github.io/get.borders/getborders.js"></script> -->
    <script src="${pageContext.request.contextPath}/resources/static/rest.js"></script>
    <title>J Clients</title>
</head>

<body>
    <div class="h1">Client API Service</div>
    <div class="ref"><a href="${pageContext.request.contextPath}/tl/index">Home</a></div>
    <div class="content-wrapper">
        <div class="content">
            <div class="client_form_header">
                <div class="client_row">
                    <div class='client_num'>#</div>
                    <div class='client_id'>ID</div>
                    <div class='client_name'>Name</div>
                    <div class='client_surname'>Surname</div>
                    <div class='client_email'>Email</div>
                    <div class='client_password'>Password</div>
                    <div class='client_row_button'></div>
                    <div class='client_row_button'></div>
                    <div class='client_row_button'></div>
                </div>

                <!-- <div class="client_row client_form">
                    <div class='client_num client_button'>Update</div>
                    <div class='client_id'></div>
                    <div class='client_form_input client_name' contenteditable="true"></div>
                    <div class='client_form_input client_surname' contenteditable="true"></div>
                    <div class='client_form_input client_email' contenteditable="true"></div>
                    <div class='client_form_input client_password' contenteditable="true"></div>
                    <div class='client_row_button'>Create</div>
                </div> -->
            </div>
            <div class="row_container">

            </div>
        </div>

        <div class="copyright">By Volodymyr Nikolenko. Java Enterprize. Odessa Hillel 2021</div>
        <div class="root_path">${pageContext.request.contextPath}</div>

    </div>
</body>


</html>
