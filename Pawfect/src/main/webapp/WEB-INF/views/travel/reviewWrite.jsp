<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>리뷰 작성</title>
  <link rel="stylesheet" href="/css/reviewWrite.css">
</head>
<body>
  <div class="review-write-wrapper">
    <h2>리뷰 작성</h2>
	<form class="review-form" action="/travel/reviewWrite" method="post" enctype="multipart/form-data">
	  <div class="form-group">
	    <label for="rating">별점:</label>
	    <select name="reviewRating" id="rating" required>
	      <option value="">선택</option>
	      <option value="1">★☆☆☆☆</option>
	      <option value="2">★★☆☆☆</option>
	      <option value="3">★★★☆☆</option>
	      <option value="4">★★★★☆</option>
	      <option value="5">★★★★★</option>
	    </select>
	  </div>
	
	  <div> <label for="reviewContent">리뷰 내용:</label> </div>
	  <div> <textarea name="reviewContent" id="reviewContent" placeholder="리뷰를 작성해 주세요" required></textarea> </div>
	  
	
	  <div> <label for="images">사진 첨부 (최소 1장):</label></div>
      <div> <input type="file" name="images" id="images" accept="image/*" multiple required></div>
	
	  <input type="hidden" name="contentId" value="${contentId}">
	  <input type="hidden" name="contentTypeId" value="${contentTypeId}">
	
	  <button type="submit">등록</button>
	</form>

  </div>
</body>
</html>
