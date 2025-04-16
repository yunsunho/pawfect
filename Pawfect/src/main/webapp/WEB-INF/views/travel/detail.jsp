<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <title>${common.title} - 상세정보</title>
  <link rel="stylesheet" href="/css/common.css">
  <link rel="stylesheet" href="/css/detail.css">
  <link rel="stylesheet" href="/css/reviewList.css">
  <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=53058506472e68663c191f4ba75fc7b0"></script>
</head>

<body data-logged-in="${not empty user}">
<div class="detail-wrapper">
  <!-- 🏷️ 제목 + 주소 -->
  <div class="place-header">
    <h1><c:choose>
	  <c:when test="${not empty common.title}">${common.title}</c:when>
	  <c:otherwise>제목 없음</c:otherwise>
	</c:choose></h1>

	<p><c:choose>
	  <c:when test="${not empty common.addr1}">${common.addr1}</c:when>
	  <c:otherwise>주소 없음</c:otherwise>
	</c:choose></p>

    <hr class="divider">
  </div>
</div>

  <!-- 📸 이미지 슬라이드 -->
<div class="main-slider-wrapper">
  <button class="main-prev" onclick="changeMainSlide(-1)">&#10094;</button>

  <div class="main-slider">
    <c:forEach var="img" items="${images}">
      <div class="main-slide">
        <img src="${img}" alt="슬라이드 이미지">
      </div>
    </c:forEach>
  </div>

  <button class="main-next" onclick="changeMainSlide(1)">&#10095;</button>
</div>

	<!-- 구분선 -->
	<hr class="section-divider" />

  <!-- 📝 소개글 + 지도 -->
  
   <div class="overview">
     <h3>상세정보</h3>
     <p>${common.overview}</p>
   </div>
   <div class="map-container">
     <div id="map" data-mapx="${common.mapx}" data-mapy="${common.mapy}" style="height:300px;"></div>
   </div>

  <!-- 📋 이용 정보 -->
  <div class="extra-info">
    <h3>이용 안내</h3>
    <ul>
      <c:if test="${not empty common.tel and common.tel ne '0' and common.tel ne '없음'}">
        <li><strong>문의처:</strong> ${common.tel}</li>
      </c:if>
      <c:if test="${not empty common.homepage and common.homepage ne '0' and common.homepage ne '없음'}">
        <li><strong>홈페이지:</strong> ${common.homepage}</li>
      </c:if>	  
	  <c:if test="${not empty pet.relaPosesFclty and pet.relaPosesFclty ne '0' and pet.relaPosesFclty ne '없음'}">
		<li><strong>보유 시설:</strong> ${pet.relaPosesFclty}</li>
	  </c:if>
	  <c:if test="${not empty pet.relaFrnshPrdlst and pet.relaFrnshPrdlst ne '0' and pet.relaFrnshPrdlst ne '없음'}">
	    <li><strong>제공 물품:</strong> ${pet.relaFrnshPrdlst}</li>
      </c:if>
	  <c:if test="${not empty pet.relaRntlPrdlst and pet.relaRntlPrdlst ne '0' and pet.relaRntlPrdlst ne '없음'}">
	    <li><strong>대여 물품:</strong> ${pet.relaRntlPrdlst}</li>
	  </c:if>
	  <c:if test="${not empty pet.acmpyNeedMtr and pet.acmpyNeedMtr ne '0' and pet.acmpyNeedMtr ne '없음'}">
	    <li><strong>필요 준비물:</strong> ${pet.acmpyNeedMtr}</li>
	  </c:if>
      <c:if test="${not empty Intro.accomcount  and Intro.accomcount ne '0' and Intro.accomcount ne '없음'}">
	    <li><strong>수용인원 : </strong> ${Intro.accomcount }</li>
	  </c:if>
	  <c:if test="${not empty Intro.chkcreditcard and Intro.chkcreditcard ne '0' and Intro.chkcreditcard ne '없음'}">
	    <li><strong>신용카드 가능 여부 : </strong> ${Intro.chkcreditcard }</li>
	  </c:if>
	  <c:if test="${not empty Intro.expagerange and Intro.expagerange ne '0' and Intro.expagerange ne '없음'}">
	    <li><strong>체험가능 연령 : </strong> ${Intro.expagerange }</li>
	  </c:if>
	  <c:if test="${not empty Intro.expguide and Intro.expguide ne '0' and Intro.expguide ne '없음'}">
	    <li><strong>체험안내 : </strong> ${Intro.expguide }</li>
	  </c:if>
	  <c:if test="${not empty Intro.heritage1 and Intro.heritage1 ne '0' and Intro.heritage1 ne '없음'}">
	    <li><strong>세계 문화유산 유무 : </strong> ${Intro.heritage1 }</li>
	  </c:if>
	  <c:if test="${not empty Intro.heritage2 and Intro.heritage2 ne '0' and Intro.heritage2 ne '없음'}">
	    <li><strong>세계 자연유산 유무 : </strong> ${Intro.heritage2 }</li>
	  </c:if>
	  <c:if test="${not empty Intro.heritage3 and Intro.heritage3 ne '0' and Intro.heritage3 ne '없음'}">
	    <li><strong>세계 기록유산 유무 : </strong> ${Intro.heritage3 }</li>
	  </c:if>
	  <c:if test="${not empty Intro.infocenter and Intro.infocenter ne '0' and Intro.infocenter ne '없음'}">
	    <li><strong>문의 및 안내 : </strong> ${Intro.infocenter }</li>
	  </c:if>
	  <c:if test="${not empty Intro.opendate and Intro.opendate ne '0' and Intro.opendate ne '없음'}">
	    <li><strong>개장일 : </strong> ${Intro.opendate }</li>
	  </c:if>
	  <c:if test="${not empty Intro.parking and Intro.parking ne '0' and Intro.parking ne '없음'}">
		  <li><strong>주차시설 : </strong> ${fn:replace(Intro.parking, '<br>', ' ')}</li>
		</c:if>
	  <c:if test="${not empty Intro.restdate and Intro.restdate ne '0' and Intro.restdate ne '없음'}">
	    <li><strong>쉬는날 : </strong> ${Intro.restdate }</li>
	  </c:if>
	  <c:if test="${not empty Intro.useseason and Intro.useseason ne '0' and Intro.useseason ne '없음'}">
	    <li><strong>이용시기 : </strong> ${Intro.useseason }</li>
	  </c:if>
	  <c:if test="${not empty Intro.usetime and Intro.usetime ne '0' and Intro.usetime ne '없음'}">
	    <li><strong>이용시간 : </strong> ${Intro.usetime }</li>
	  </c:if>
	  <c:if test="${not empty Intro.accomcountculture and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>수용인원 : </strong> ${Intro.accomcountculture }</li>
	  </c:if>
	  <c:if test="${not empty Intro.chkcreditcardculture and Intro.chkcreditcardculture ne '0' and Intro.chkcreditcardculture ne '없음'}">
	    <li><strong>신용카드 가능 여부 : </strong> ${Intro.chkcreditcardculture }</li>
	  </c:if>
	  <c:if test="${not empty Intro.discountinfo and Intro.discountinfo ne '0' and Intro.discountinfo ne '없음'}">
	    <li><strong>할인정보 : </strong> ${Intro.discountinfo }</li>
	  </c:if>
	  <c:if test="${not empty Intro.infocenterculture and Intro.infocenterculture ne '0' and Intro.infocenterculture ne '없음'}">
	    <li><strong>문의 및 안내 : </strong> ${Intro.infocenterculture }</li>
	  </c:if>
	  <c:if test="${not empty Intro.parkingculture and Intro.parkingculture ne '0' and Intro.parkingculture ne '없음'}">
	    <li><strong>주차시설 : </strong> ${Intro.parkingculture }</li>
	  </c:if>
	  <c:if test="${not empty Intro.parkingfee and Intro.parkingfee ne '0' and Intro.parkingfee ne '없음'}">
	    <li><strong>주차요금 : </strong> ${Intro.parkingfee }</li>
	  </c:if>
	  <c:if test="${not empty Intro.restdateculture and Intro.restdateculture ne '0' and Intro.restdateculture ne '없음'}">
	    <li><strong>쉬는날 : </strong> ${Intro.restdateculture }</li>
	  </c:if>
	  <c:if test="${not empty Intro.usefee and Intro.usefee ne '0' and Intro.usefee ne '없음'}">
	    <li><strong>이용요금 : </strong> ${Intro.usefee }</li>
	  </c:if>
	  <c:if test="${not empty Intro.usetimeculture and Intro.usetimeculture ne '0' and Intro.usetimeculture ne '없음'}">
	    <li><strong>이용시간 : </strong> ${Intro.usetimeculture }</li>
	  </c:if>
	  <c:if test="${not empty Intro.scale and Intro.scale ne '0' and Intro.scale ne '없음'}">
	    <li><strong>규모 : </strong> ${Intro.scale }</li>
	  </c:if>
	  <c:if test="${not empty Intro.spendtime and Intro.spendtime ne '0' and Intro.spendtime ne '없음'}">
	    <li><strong>관람 소요시간 : </strong> ${Intro.spendtime }</li>
	  </c:if>
	  <c:if test="${not empty Intro.agelimit and Intro.agelimit ne '0' and Intro.agelimit ne '없음'}">
	    <li><strong>관람 가능연령 : </strong> ${Intro.agelimit }</li>
	  </c:if>
	  <c:if test="${not empty Intro.bookingplace and Intro.bookingplace ne '0' and Intro.bookingplace ne '없음'}">
	    <li><strong>예매처 : </strong> ${Intro.bookingplace }</li>
	  </c:if>
	  <c:if test="${not empty Intro.discountinfofestival and Intro.discountinfofestival ne '0' and Intro.discountinfofestival ne '없음'}">
	    <li><strong>할인정보 : </strong> ${Intro.discountinfofestival }</li>
	  </c:if>
	  <c:if test="${not empty Intro.eventenddate and Intro.eventenddate ne '0' and Intro.eventenddate ne '없음'}">
	    <li><strong>행사 종료일 : </strong> ${Intro.eventenddate }</li>
	  </c:if>
	  <c:if test="${not empty Intro.eventhomepage and Intro.eventhomepage ne '0' and Intro.eventhomepage ne '없음'}">
	    <li><strong>행사 홈페이지 : </strong> ${Intro.eventhomepage }</li>
	  </c:if>
	  <c:if test="${not empty Intro.eventplace and Intro.eventplace ne '0' and Intro.eventplace ne '없음'}">
	    <li><strong>행사 장소 : </strong> ${Intro.eventplace }</li>
	  </c:if>
	  <c:if test="${not empty Intro.eventstartdate and Intro.eventstartdate ne '0' and Intro.eventstartdate ne '없음'}">
	    <li><strong>행사 시작일 : </strong> ${Intro.eventstartdate }</li>
	  </c:if>
	  <c:if test="${not empty Intro.festivalgrade and Intro.festivalgrade ne '0' and Intro.festivalgrade ne '없음'}">
	    <li><strong>축제등급 : </strong> ${Intro.festivalgrade }</li>
	  </c:if>
	  <c:if test="${not empty Intro.placeinfo and Intro.placeinfo ne '0' and Intro.placeinfo ne '없음'}">
	    <li><strong>행사장 위치안내 : </strong> ${Intro.placeinfo }</li>
	  </c:if>
	  <c:if test="${not empty Intro.playtime and Intro.playtime ne '0' and Intro.playtime ne '없음'}">
	    <li><strong>공연시간 : </strong> ${Intro.playtime }</li>
	  </c:if>
	  <c:if test="${not empty Intro.program and Intro.program ne '0' and Intro.program ne '없음'}">
	    <li><strong>행사 프로그램 : </strong> ${Intro.program }</li>
	  </c:if>
	  <c:if test="${not empty Intro.spendtimefestival and Intro.spendtimefestival ne '0' and Intro.spendtimefestival ne '없음'}">
	    <li><strong>관람 소요시간 : </strong> ${Intro.spendtimefestival }</li>
	  </c:if>
	  <c:if test="${not empty Intro.sponsor1 and Intro.sponsor1 ne '0' and Intro.sponsor1 ne '없음'}">
	    <li><strong>주최자 정보 : </strong> ${Intro.sponsor1 }</li>
	  </c:if>
	  <c:if test="${not empty Intro.sponsor1tel and Intro.sponsor1tel ne '0' and Intro.sponsor1tel ne '없음'}">
	    <li><strong>주최자 연락처 : </strong> ${Intro.sponsor1tel }</li>
	  </c:if>
	  <c:if test="${not empty Intro.sponsor2 and Intro.sponsor2 ne '0' and Intro.sponsor2 ne '없음'}">
	    <li><strong>주관사 정보 : </strong> ${Intro.sponsor2 }</li>
	  </c:if>
	  <c:if test="${not empty Intro.sponsor2tel and Intro.sponsor2tel ne '0' and Intro.sponsor2tel ne '없음'}">
	    <li><strong>주관사 연락처 : </strong> ${Intro.sponsor2tel }</li>
	  </c:if>
	  <c:if test="${not empty Intro.subevent and Intro.subevent ne '0' and Intro.subevent ne '없음'}">
	    <li><strong>부대행사 : </strong> ${Intro.subevent }</li>
	  </c:if>
	  <c:if test="${not empty Intro.usetimefestival and Intro.usetimefestival ne '0' and Intro.usetimefestival ne '없음'}">
	    <li><strong>이용요금 : </strong> ${Intro.usetimefestival }</li>
	  </c:if>
	  <c:if test="${not empty Intro.accomcountleports and Intro.chkcreditcardleports ne '0' and Intro.chkcreditcardleports ne '없음'}">
	    <li><strong>수용인원 : </strong> ${Intro.accomcountleports }</li>
	  </c:if>
	  <c:if test="${not empty Intro.chkcreditcardleports and Intro.chkcreditcardleports ne '0' and Intro.chkcreditcardleports ne '없음'}">
	    <li><strong>신용카드가능 정보 : </strong> ${Intro.chkcreditcardleports }</li>
	  </c:if>
	  <c:if test="${not empty Intro.expagerangeleports and Intro.expagerangeleports ne '0' and Intro.expagerangeleports ne '없음'}">
	    <li><strong>체험 가능연령 : </strong> ${Intro.expagerangeleports }</li>
	  </c:if>
	  <c:if test="${not empty Intro.infocenterleports and Intro.infocenterleports ne '0' and Intro.infocenterleports ne '없음'}">
	    <li><strong>문의 및 안내 : </strong> ${Intro.infocenterleports }</li>
	  </c:if>
	  <c:if test="${not empty Intro.openperiod and Intro.openperiod ne '0' and Intro.openperiod ne '없음'}">
	    <li><strong>개장기간 : </strong> ${Intro.openperiod }</li>
	  </c:if>
	  <c:if test="${not empty Intro.parkingfeeleports and Intro.parkingfeeleports ne '0' and Intro.parkingfeeleports ne '없음'}">
	    <li><strong>주차요금 : </strong> ${Intro.parkingfeeleports }</li>
	  </c:if>
	  <c:if test="${not empty Intro.parkingleports and Intro.parkingleports ne '0' and Intro.parkingleports ne '없음'}">
	    <li><strong>주차시설 : </strong> ${Intro.parkingleports }</li>
	  </c:if>
	  <c:if test="${not empty Intro.reservation and Intro.reservation ne '0' and Intro.reservation ne '없음'}">
	    <li><strong>예약안내 : </strong> ${Intro.reservation }</li>
	  </c:if>
	  <c:if test="${not empty Intro.restdateleports and Intro.restdateleports ne '0' and Intro.restdateleports ne '없음'}">
	    <li><strong>쉬는날 : </strong> ${Intro.restdateleports }</li>
	  </c:if>
	  <c:if test="${not empty Intro.scaleleports and Intro.scaleleports ne '0' and Intro.scaleleports ne '없음'}">
	    <li><strong>규모 : </strong> ${Intro.scaleleports }</li>
	  </c:if>
	  <c:if test="${not empty Intro.usefeeleports and Intro.usefeeleports ne '0' and Intro.usefeeleports ne '없음'}">
	    <li><strong>입장료 : </strong> ${Intro.usefeeleports }</li>
	  </c:if>
	  <c:if test="${not empty Intro.usetimeleports and Intro.usetimeleports ne '0' and Intro.usetimeleports ne '없음'}">
	    <li><strong>이용시간 : </strong> ${Intro.usetimeleports }</li>
	  </c:if>
	  <c:if test="${not empty Intro.accomcountlodging and Intro.accomcountlodging ne '0' and Intro.accomcountlodging ne '없음'}">
	    <li><strong> 수용 가능인원 : </strong> ${Intro.accomcountlodging }</li>
	  </c:if>
	  <c:if test="${not empty Intro.benikia and Intro.benikia ne '0' and Intro.benikia ne '없음'}">
	    <li><strong> 베니키아 여부 : </strong> ${Intro.benikia }</li>
	  </c:if>
	  <c:if test="${not empty Intro.checkintime and Intro.checkintime ne '0' and Intro.checkintime ne '없음'}">
	    <li><strong> 입실 시간 : </strong> ${Intro.checkintime }</li>
	  </c:if>
	  <c:if test="${not empty Intro.checkouttime and Intro.checkouttime ne '0' and Intro.checkouttime ne '없음'}">
	    <li><strong> 퇴실 시간 : </strong> ${Intro.checkouttime }</li>
	  </c:if>
	  <c:if test="${not empty Intro.chkcooking and Intro.chkcooking ne '0' and Intro.chkcooking ne '없음'}">
	    <li><strong> 객실내 취사 여부 : </strong> ${Intro.chkcooking }</li>
	  </c:if>
	  <c:if test="${not empty Intro.foodplace and Intro.foodplace ne '0' and Intro.foodplace ne '없음'}">
	    <li><strong> 식음료장 : </strong> ${Intro.foodplace }</li>
	  </c:if>
	  <c:if test="${not empty Intro.goodstay and Intro.goodstay ne '0' and Intro.goodstay ne '없음'}">
	    <li><strong> 굿스테이 여부 : </strong> ${Intro.goodstay }</li>
	  </c:if>
	  <c:if test="${not empty Intro.hanok and Intro.hanok ne '0' and Intro.hanok ne '없음'}">
	    <li><strong> 한옥 여부 : </strong> ${Intro.hanok }</li>
	  </c:if>
	  <c:if test="${not empty Intro.infocenterlodging and Intro.infocenterlodging ne '0' and Intro.infocenterlodging ne '없음'}">
	    <li><strong> 문의 및 안내 :</strong> ${Intro.infocenterlodging }</li>
	  </c:if>
	  <c:if test="${not empty Intro.parkinglodging and Intro.parkinglodging ne '0' and Intro.parkinglodging ne '없음'}">
	    <li><strong> 주차시설 : </strong> ${Intro.parkinglodging }</li>
	  </c:if>
	  <c:if test="${not empty Intro.pickup and Intro.pickup ne '0' and Intro.pickup ne '없음'}">
	    <li><strong> 픽업 서비스 : </strong> ${Intro.pickup }</li>
	  </c:if>
	  <c:if test="${not empty Intro.roomcount and Intro.roomcount ne '0' and Intro.roomcount ne '없음'}">
	    <li><strong> 객실수 : </strong> ${Intro.roomcount }</li>
	  </c:if>
	  <c:if test="${not empty Intro.reservationlodging and Intro.reservationlodging ne '0' and Intro.reservationlodging ne '없음'}">
	    <li><strong> 예약안내 : </strong> ${Intro.reservationlodging }</li>
	  </c:if>
	  <c:if test="${not empty Intro.reservationurl and Intro.reservationurl ne '0' and Intro.reservationurl ne '없음'}">
	    <li><strong> 예약안내 홈페이지 : </strong> ${Intro.reservationurl }</li>
	  </c:if>
	  <c:if test="${not empty Intro.roomtype and Intro.roomtype ne '0' and Intro.roomtype ne '없음'}">
	    <li><strong> 객실유형 : </strong> ${Intro.roomtype }</li>
	  </c:if>
	  <c:if test="${not empty Intro.scalelodging and Intro.scalelodging ne '0' and Intro.scalelodging ne '없음'}">
	    <li><strong> 규모 : </strong> ${Intro.scalelodging }</li>
	  </c:if>
	  <c:if test="${not empty Intro.subfacility and Intro.subfacility ne '0' and Intro.subfacility ne '없음'}">
	    <li><strong> 부대시설 (기타) : </strong> ${Intro.subfacility }</li>
	  </c:if>
	  <c:if test="${not empty Intro.barbecue and Intro.barbecue ne '0' and Intro.barbecue ne '없음'}">
	    <li><strong> 바비큐장 여부 : </strong> ${Intro.barbecue }</li>
	  </c:if>
	  <c:if test="${not empty Intro.beauty and Intro.beauty ne '0' and Intro.beauty ne '없음'}">
	    <li><strong> 뷰티시설 정보 : </strong> ${Intro.beauty }</li>
	  </c:if>
	  <c:if test="${not empty Intro.beverage and Intro.beverage ne '0' and Intro.beverage ne '없음'}">
	    <li><strong> 식음료장 여부 : </strong> ${Intro.beverage }</li>
	  </c:if>
	  <c:if test="${not empty Intro.bicycle and Intro.bicycle ne '0' and Intro.bicycle ne '없음'}">
	    <li><strong> 자전거 대여 여부 : </strong> ${Intro.bicycle }</li>
	  </c:if>
	  <c:if test="${not empty Intro.campfire and Intro.campfire ne '0' and Intro.campfire ne '없음'}">
	    <li><strong> 캠프파이어 여부 : </strong> ${Intro.campfire }</li>
	  </c:if>
	  <c:if test="${not empty Intro.fitness and Intro.fitness ne '0' and Intro.fitness ne '없음'}">
	    <li><strong> 휘트니스 센터 여부 : </strong> ${Intro.fitness }</li>
	  </c:if>
	  <c:if test="${not empty Intro.karaoke and Intro.karaoke ne '0' and Intro.karaoke ne '없음'}">
	    <li><strong> 노래방 여부 : </strong> ${Intro.karaoke }</li>
	  </c:if>
	  <c:if test="${not empty Intro.publicbath and Intro.publicbath ne '0' and Intro.publicbath ne '없음'}">
	    <li><strong> 공용 샤워실 여부 : </strong> ${Intro.publicbath }</li>
	  </c:if>
	  <c:if test="${not empty Intro.publicpc and Intro.publicpc ne '0' and Intro.publicpc ne '없음'}">
	    <li><strong> 공용 PC실 여부 : </strong> ${Intro.publicpc }</li>
	  </c:if>
	  <c:if test="${not empty Intro.sauna and Intro.sauna ne '0' and Intro.sauna ne '없음'}">
	    <li><strong> 사우나실 여부 : </strong> ${Intro.sauna }</li>
	  </c:if>
	  <c:if test="${not empty Intro.seminar and Intro.seminar ne '0' and Intro.seminar ne '없음'}">
	    <li><strong> 세미나실 여부 : </strong> ${Intro.seminar }</li>
	  </c:if>
	  <c:if test="${not empty Intro.sports and Intro.sports ne '0' and Intro.sports ne '없음'}">
	    <li><strong> 스포츠 시설 여부 : </strong> ${Intro.sports }</li>
	  </c:if>
	  <c:if test="${not empty Intro.refundregulation and Intro.refundregulation ne '0' and Intro.refundregulation ne '없음'}">
	    <li><strong> 환불규정 : </strong> ${Intro.refundregulation }</li>
	  </c:if>
	  <c:if test="${not empty Intro.chkcreditcardshopping and Intro.chkcreditcardshopping ne '0' and Intro.chkcreditcardshopping ne '없음'}">
	    <li><strong> 신용카드가능 정보 : </strong> ${Intro.chkcreditcardshopping }</li>
	  </c:if>
	  <c:if test="${not empty Intro.culturecenter and Intro.culturecenter ne '0' and Intro.culturecenter ne '없음'}">
	    <li><strong> 문화센터 바로가기 : </strong> ${Intro.culturecenter }</li>
	  </c:if>
	  <c:if test="${not empty Intro.fairday and Intro.fairday ne '0' and Intro.fairday ne '없음'}">
	    <li><strong> 장서는 날 : </strong> ${Intro.fairday }</li>
	  </c:if>
	  <c:if test="${not empty Intro.infocentershopping and Intro.infocentershopping ne '0' and Intro.infocentershopping ne '없음'}">
	    <li><strong> 문의 및 안내 : </strong> ${Intro.infocentershopping }</li>
	  </c:if>
	  <c:if test="${not empty Intro.opendateshopping and Intro.opendateshopping ne '0' and Intro.opendateshopping ne '없음'}">
	    <li><strong> 개장일 : </strong> ${Intro.opendateshopping }</li>
	  </c:if>
	  <c:if test="${not empty Intro.opentime and Intro.opentime ne '0' and Intro.opentime ne '없음'}">
	    <li><strong> 영업시간 : </strong> ${Intro.opentime }</li>
	  </c:if>
	  <c:if test="${not empty Intro.parkingshopping and Intro.parkingshopping ne '0' and Intro.parkingshopping ne '없음'}">
	    <li><strong> 주차시설 : </strong> ${Intro.parkingshopping }</li>
	  </c:if>
	  <c:if test="${not empty Intro.restdateshopping and Intro.restdateshopping ne '0' and Intro.restdateshopping ne '없음'}">
	    <li><strong> 쉬는날 : </strong> ${Intro.restdateshopping }</li>
	  </c:if>
	  <c:if test="${not empty Intro.restroom and Intro.restroom ne '0' and Intro.restroom ne '없음'}">
	    <li><strong> 화장실 설명 : </strong> ${Intro.restroom }</li>
	  </c:if>
	  <c:if test="${not empty Intro.saleitem and Intro.saleitem ne '0' and Intro.saleitem ne '없음'}">
	    <li><strong> 판매 품목 : </strong> ${Intro.saleitem }</li>
	  </c:if>
	  <c:if test="${not empty Intro.saleitemcost and Intro.saleitemcost ne '0' and Intro.saleitemcost ne '없음'}">
	    <li><strong> 판매 품목별 가격 : </strong> ${Intro.saleitemcost }</li>
	  </c:if>
	  <c:if test="${not empty Intro.scaleshopping and Intro.scaleshopping ne '0' and Intro.scaleshopping ne '없음'}">
	    <li><strong> 규모 : </strong> ${Intro.scaleshopping }</li>
	  </c:if>
	  <c:if test="${not empty Intro.shopguide and Intro.shopguide ne '0' and Intro.shopguide ne '없음'}">
	    <li><strong> 매장안내 : </strong> ${Intro.shopguide }</li>
	  </c:if>
	  <c:if test="${not empty Intro.chkcreditcardfood and Intro.chkcreditcardfood ne '0' and Intro.chkcreditcardfood ne '없음'}">
	    <li><strong> 신용카드가능 정보 : </strong> ${Intro.chkcreditcardfood }</li>
	  </c:if>
	  <c:if test="${not empty Intro.discountinfofood and Intro.discountinfofood ne '0' and Intro.discountinfofood ne '없음'}">
	    <li><strong> 할인정보 : </strong> ${Intro.discountinfofood }</li>
	  </c:if>
	  <c:if test="${not empty Intro.firstmenu and Intro.firstmenu ne '0' and Intro.firstmenu ne '없음'}">
	    <li><strong> 대표 메뉴 : </strong> ${Intro.firstmenu }</li>
	  </c:if>
	  <c:if test="${not empty Intro.infocenterfood and Intro.infocenterfood ne '0' and Intro.infocenterfood ne '없음'}">
	    <li><strong> 문의 및 안내 : </strong> ${Intro.infocenterfood }</li>
	  </c:if>
	  <c:if test="${not empty Intro.kidsfacility and Intro.kidsfacility ne '0' and Intro.kidsfacility ne '없음'}">
	    <li><strong> 어린이 놀이방 여부 : </strong> ${Intro.kidsfacility }</li>
	  </c:if>
	  <c:if test="${not empty Intro.opendatefood and Intro.opendatefood ne '0' and Intro.opendatefood ne '없음'}">
	    <li><strong> 개업일 : </strong> ${Intro.opendatefood }</li>
	  </c:if>
	  <c:if test="${not empty Intro.opentimefood and Intro.opentimefood ne '0' and Intro.opentimefood ne '없음'}">
	    <li><strong> 영업시간 : </strong> ${Intro.opentimefood }</li>
	  </c:if>
	  <c:if test="${not empty Intro.packing and Intro.packing ne '0' and Intro.packing ne '없음'}">
	    <li><strong> 포장 가능 : </strong> ${Intro.packing }</li>
	  </c:if>
	  <c:if test="${not empty Intro.parkingfood and Intro.parkingfood ne '0' and Intro.parkingfood ne '없음'}">
	    <li><strong> 주차시설 : </strong> ${Intro.parkingfood }</li>
	  </c:if>
	  <c:if test="${not empty Intro.reservationfood and Intro.reservationfood ne '0' and Intro.reservationfood ne '없음'}">
	    <li><strong> 예약안내 : </strong> ${Intro.reservationfood }</li>
	  </c:if>
	  <c:if test="${not empty Intro.restdatefood and Intro.restdatefood ne '0' and Intro.restdatefood ne '없음'}">
	    <li><strong> 쉬는날 : </strong> ${Intro.restdatefood }</li>
	  </c:if>
	  <c:if test="${not empty Intro.scalefood and Intro.scalefood ne '0' and Intro.scalefood ne '없음'}">
	    <li><strong> 규모 : </strong> ${Intro.scalefood }</li>
	  </c:if>
	  <c:if test="${not empty Intro.seat and Intro.seat ne '0' and Intro.seat ne '없음'}">
	    <li><strong> 좌석수 : </strong> ${Intro.seat }</li>
	  </c:if>
	  <c:if test="${not empty Intro.smoking and Intro.smoking ne '0' and Intro.smoking ne '없음'}">
	    <li><strong> 금연/흡연 여부 : </strong> ${Intro.smoking }</li>
	  </c:if>
	  <c:if test="${not empty Intro.treatmenu and Intro.treatmenu ne '0' and Intro.treatmenu ne '없음'}">
	    <li><strong> 취급 메뉴 : </strong> ${Intro.treatmenu }</li>
	  </c:if>
	<c:forEach var="info" items="${introList}">
	  <li><strong>${info.name}:</strong> ${info.text}</li>
	</c:forEach>
  </ul>
</div>

<c:if test="${not empty roomList}">
  <div class="info-box">
    <h3>객실안내</h3>
    <c:forEach var="room" items="${roomList}" varStatus="status">
      <div class="room-block">
        <h4>${room.title}</h4>
        <div style="display: flex; gap: 20px;">
          <div class="slider-container">
		  <div class="room-slider">
		    <c:forEach var="img" items="${room.images}">
		      <div class="slide">
		        <img src="${img}" alt="객실 이미지">
		      </div>
		    </c:forEach>
		  </div>
		  <button class="prev" onclick="plusSlide(-1, ${status.index})">&#10094;</button>
		  <button class="next" onclick="plusSlide(1, ${status.index})">&#10095;</button>
		</div>

          <ul>
            <li>객실크기: ${room.roomsize}</li>
            <li>숙박인원: ${room.basecount}명 (최대 ${room.maxcount}명)</li>
            <li>비수기주중: ${room.offmin}원</li>
            <li>비수기주말: ${room.offmax}원</li>
            <li>성수기주중: ${room.peakmin}원</li>
            <li>성수기주말: ${room.peakmax}원</li>
            <li>편의시설: ${room.amenities}</li>
          </ul>
        </div>
        <hr>
      </div>
    </c:forEach>
    <p class="notice">※ 위 정보는 공공데이터 포털에 등록된 기준이며, 변동될 수 있습니다.</p>
  </div>
</c:if>

<!-- 구분선 -->
	<hr class="section-divider" />

<!-- 💬 리뷰 영역 -->
<div class="review-section">
  <h3>리뷰</h3>
  
 <!-- 로그인한 사용자만 리뷰 작성 가능 -->
  
     <form action="/travel/reviewWrite" method="post" enctype="multipart/form-data">
	  <div class="review-form">
	  <c:if test="${not empty user}">
	    <textarea name="reviewContent" placeholder="리뷰를 작성해주세요." required></textarea>
	  </c:if>
	  <c:if test="${empty user}">
      	<textarea placeholder="로그인 후 작성 가능합니다." disabled></textarea>
      </c:if>
	    <label for="reviewRating">평점:</label>
	    <select name="reviewRating" required>
	    	<option value="5">★★★★★</option>
	    	<option value="4">★★★★</option>
	    	<option value="3">★★★</option>
			<option value="2">★★</option>
			<option value="1">★</option>
	    </select>
	
	    <div class="file-input-container">
		  <input type="file" name="reviewImages" id="reviewImages" accept="image/*" multiple hidden>
		  <div class="upload-info">
			  <c:if test="${not empty user}">
			    <button type="button" id="fileButton">사진 첨부</button>
			  </c:if>
			  <c:if test="${empty user}">
			    <button type="button" id="fileButton" disabled>사진 첨부</button>
			  </c:if>
			  <div class="file-limit-msg">최대 5개까지 첨부 가능합니다</div>
		  </div>
		  <div id="preview-container" class="preview-container"></div>
		</div>

	    <input type="hidden" name="contentId" value="${contentId}">
	    <input type="hidden" name="contentTypeId" value="${contentTypeId}">
	    <input type="hidden" name="title" value="${common.title}">
		
		<button type="submit" id="submitReviewBtn">등록</button>
	  </div>
	</form>
</div>

<div id="review-box"></div>

<div id="confirmModal" class="modal">
  <div class="modal-content">
    <p id="confirmModalMessage"></p>
    <button id="btnConfirmYes">예</button>
    <button id="btnConfirmNo">아니요</button>
  </div>
</div>

<div id="commonModal" class="modal">
  <div class="modal-content">
    <p id="modalMessage"></p>
    <button onclick="closeModal()">확인</button>
  </div>
</div>
<script>
document.addEventListener("DOMContentLoaded", function () {
    fetch("/travel/reviews/${contentId}")
        .then(res => res.text())
        .then(html => {
            document.getElementById("review-box").innerHTML = html;
        });
});
</script>
<script src='/js/reviewList.js'/></script>
<script src='/js/detail.js'/></script>
<script src='/js/modal.js'/></script>
</body>
</html>

