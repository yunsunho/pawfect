<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Pawfect Tour - 여행지 목록</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background: #f8f8f8;
        }

        .navbar {
            background-color: #fdd835;
            padding: 10px 30px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .navbar .logo {
            font-size: 24px;
            font-weight: bold;
            color: #444;
        }

        .navbar .menu {
            display: flex;
            gap: 20px;
            font-weight: bold;
        }

        .tour-container {
            padding: 30px;
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
        }

        .tour-card {
            background: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            position: relative;
        }

        .tour-card img {
            width: 100%;
            height: 160px;
            object-fit: cover;
        }

        .tour-info {
            padding: 15px;
        }

        .tour-info h3 {
            font-size: 18px;
            margin: 0 0 8px 0;
        }

        .bookmark {
            position: absolute;
            top: 10px;
            right: 10px;
            font-size: 24px;
            cursor: pointer;
        }

        .sort-box {
            text-align: right;
            margin: 20px 30px 0;
        }

        .sort-box select {
            padding: 5px 10px;
            font-size: 14px;
        }
    </style>
</head>
<body>

<div class="navbar">
    <div class="logo">🐾 Pawfect Tour</div>
    <div class="menu">
        <div>홈</div>
        <div>테마</div>
        <div>지역</div>
        <div>커뮤니티</div>
    </div>
</div>

<div class="sort-box">
    <select>
        <option selected>가나다순</option>
        <option disabled>리뷰순</option>
        <option disabled>별점순</option>
        <option disabled>북마크순</option>
    </select>
</div>

<div class="tour-container">
    <c:forEach var="tour" items="${tourList}">
        <div class="tour-card">
            <img src="${tour.firstimage}" alt="이미지 없음">
            <div class="bookmark">🔖</div>
            <div class="tour-info">
                <h3>${tour.title}</h3>
                <p>${tour.addr1}</p>
            </div>
        </div>
    </c:forEach>
</div>

</body>
</html>
