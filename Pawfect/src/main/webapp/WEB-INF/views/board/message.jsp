<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="setting.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        .modal-overlay {
            position: fixed;
            top: 0; left: 0;
            width: 100vw; height: 100vh;
            background: rgba(0, 0, 0, 0.4);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 9999;
        }

        .modal-box {
            background: white;
            padding: 2rem 3rem;
            border-radius: 12px;
            text-align: center;
            box-shadow: 0 10px 10px rgba(0, 0, 0, 0.25);
            font-family: 'Noto Sans KR', sans-serif;
        }

        .modal-box p {
            margin-bottom: 1rem;
            font-size: 14px;
        }
    </style>

    <script>
        window.onload = function () {
            setTimeout(() => {
                location.href = '${redirectUrl}';
            }, 3000); // Redirect in 2 seconds
        };
    </script>
</head>
<body>
    <div class="modal-overlay">
        <div class="modal-box">
            <p>${msg}</p>
            <p>${msg_loading}</p>
        </div>
    </div>
</body>
</html>
