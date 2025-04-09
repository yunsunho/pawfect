<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <title>${common.title} - 상세정보</title>
  <link rel="stylesheet" href="/css/common.css">
  <link rel="stylesheet" href="/css/detail.css">
  <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=53058506472e68663c191f4ba75fc7b0"></script>
</head>
<body>

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

  <!-- 📸 이미지 슬라이드 -->
  <div class="slider">
    <c:forEach var="img" items="${images}">
      <img src="${img}" alt="슬라이드 이미지">
    </c:forEach>
  </div>

  <!-- 📝 소개글 + 지도 -->
  
   <div class="overview">
     <h3>상세정보</h3>
     <p>${common.overview}</p>
   </div>
   <div class="map-container">
     <div id="map" style="width:100%; height:300px;"></div>
   </div>

  <!-- 📋 이용 정보 -->
  <div class="extra-info">
    <h3>이용 안내</h3>
    <ul>
      <c:if test="${not empty common.tel}">
        <li><strong>문의처:</strong> ${common.tel}</li>
      </c:if>
      <c:if test="${not empty common.homepage}">
        <li><strong>홈페이지:</strong> ${common.homepage}</li>
      </c:if>
	  <c:if test="${not empty pet.acmpyTypeCd}">
		  <li><strong>동반 유형:</strong> ${pet.acmpyTypeCd}</li>
	  </c:if>
	  <c:if test="${not empty pet.relaPosesFclty}">
		<li><strong>보유 시설:</strong> ${pet.relaPosesFclty}</li>
	  </c:if>
	  <c:if test="${not empty pet.relaFrnshPrdlst}">
	    <li><strong>제공 물품:</strong> ${pet.relaFrnshPrdlst}</li>
      </c:if>
	  <c:if test="${not empty pet.etcAcmpyInfo}">
	    <li><strong>기타 동반 정보:</strong> ${pet.etcAcmpyInfo}</li>
	  </c:if>
	  <c:if test="${not empty pet.acmpyPsblCpam}">
	    <li><strong>동반 가능:</strong> ${pet.acmpyPsblCpam}</li>
	  </c:if>
	  <c:if test="${not empty pet.relaRntlPrdlst}">
	    <li><strong>대여 물품:</strong> ${pet.relaRntlPrdlst}</li>
	  </c:if>
	  <c:if test="${not empty pet.acmpyNeedMtr}">
	    <li><strong>필요 준비물:</strong> ${pet.acmpyNeedMtr}</li>
	  </c:if>
      <c:if test="${not empty Intro.accomcount }">
	    <li><strong>수용인원 : </strong> ${Intro.accomcount }</li>
	  </c:if>
	  <c:if test="${not empty Intro.chkcreditcard }">
	    <li><strong>신용카드 가능 여부 : </strong> ${Intro.chkcreditcard }</li>
	  </c:if>
	  <c:if test="${not empty Intro.expagerange }">
	    <li><strong>체험가능 연령 : </strong> ${Intro.expagerange }</li>
	  </c:if>
	  <c:if test="${not empty Intro.expguide }">
	    <li><strong>체험안내 : </strong> ${Intro.expguide }</li>
	  </c:if>
	  <c:if test="${not empty Intro.heritage1 and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>세계 문화유산 유무 : </strong> ${Intro.heritage1 }</li>
	  </c:if>
	  <c:if test="${not empty Intro.heritage2 and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>세계 자연유산 유무 : </strong> ${Intro.heritage2 }</li>
	  </c:if>
	  <c:if test="${not empty Intro.heritage3 and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>세계 기록유산 유무 : </strong> ${Intro.heritage3 }</li>
	  </c:if>
	  <c:if test="${not empty Intro.infocenter and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>문의 및 안내 : </strong> ${Intro.infocenter }</li>
	  </c:if>
	  <c:if test="${not empty Intro.opendate and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>개장일 : </strong> ${Intro.opendate }</li>
	  </c:if>
	  <c:if test="${not empty Intro.parking and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>주차시설 : </strong> ${Intro.parking }</li>
	  </c:if>
	  <c:if test="${not empty Intro.restdate and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>쉬는날 : </strong> ${Intro.restdate }</li>
	  </c:if>
	  <c:if test="${not empty Intro.useseason and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>이용시기 : </strong> ${Intro.useseason }</li>
	  </c:if>
	  <c:if test="${not empty Intro.usetime and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>이용시간 : </strong> ${Intro.usetime }</li>
	  </c:if>
	  <c:if test="${not empty Intro.accomcountculture and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>수용인원 : </strong> ${Intro.accomcountculture }</li>
	  </c:if>
	  <c:if test="${not empty Intro.chkcreditcardculture and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>신용카드 가능 여부 : </strong> ${Intro.chkcreditcardculture }</li>
	  </c:if>
	  <c:if test="${not empty Intro.discountinfo and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>할인정보 : </strong> ${Intro.discountinfo }</li>
	  </c:if>
	  <c:if test="${not empty Intro.infocenterculture and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>문의 및 안내 : </strong> ${Intro.infocenterculture }</li>
	  </c:if>
	  <c:if test="${not empty Intro.parkingculture and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>주차시설 : </strong> ${Intro.parkingculture }</li>
	  </c:if>
	  <c:if test="${not empty Intro.parkingfee and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>주차요금 : </strong> ${Intro.parkingfee }</li>
	  </c:if>
	  <c:if test="${not empty Intro.restdateculture and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>쉬는날 : </strong> ${Intro.restdateculture }</li>
	  </c:if>
	  <c:if test="${not empty Intro.usefee and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>이용요금 : </strong> ${Intro.usefee }</li>
	  </c:if>
	  <c:if test="${not empty Intro.usetimeculture and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>이용시간 : </strong> ${Intro.usetimeculture }</li>
	  </c:if>
	  <c:if test="${not empty Intro.scale and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>규모 : </strong> ${Intro.scale }</li>
	  </c:if>
	  <c:if test="${not empty Intro.spendtime and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>관람 소요시간 : </strong> ${Intro.spendtime }</li>
	  </c:if>
	  <c:if test="${not empty Intro.agelimit and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>관람 가능연령 : </strong> ${Intro.agelimit }</li>
	  </c:if>
	  <c:if test="${not empty Intro.bookingplace and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>예매처 : </strong> ${Intro.bookingplace }</li>
	  </c:if>
	  <c:if test="${not empty Intro.discountinfofestival and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>할인정보 : </strong> ${Intro.discountinfofestival }</li>
	  </c:if>
	  <c:if test="${not empty Intro.eventenddate and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>행사 종료일 : </strong> ${Intro.eventenddate }</li>
	  </c:if>
	  <c:if test="${not empty Intro.eventhomepage and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>행사 홈페이지 : </strong> ${Intro.eventhomepage }</li>
	  </c:if>
	  <c:if test="${not empty Intro.eventplace and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>행사 장소 : </strong> ${Intro.eventplace }</li>
	  </c:if>
	  <c:if test="${not empty Intro.eventstartdate and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>행사 시작일 : </strong> ${Intro.eventstartdate }</li>
	  </c:if>
	  <c:if test="${not empty Intro.festivalgrade and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>축제등급 : </strong> ${Intro.festivalgrade }</li>
	  </c:if>
	  <c:if test="${not empty Intro.placeinfo and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>행사장 위치안내 : </strong> ${Intro.placeinfo }</li>
	  </c:if>
	  <c:if test="${not empty Intro.playtime and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>공연시간 : </strong> ${Intro.playtime }</li>
	  </c:if>
	  <c:if test="${not empty Intro.program and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>행사 프로그램 : </strong> ${Intro.program }</li>
	  </c:if>
	  <c:if test="${not empty Intro.spendtimefestival and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>관람 소요시간 : </strong> ${Intro.spendtimefestival }</li>
	  </c:if>
	  <c:if test="${not empty Intro.sponsor1 and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>주최자 정보 : </strong> ${Intro.sponsor1 }</li>
	  </c:if>
	  <c:if test="${not empty Intro.sponsor1tel and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>주최자 연락처 : </strong> ${Intro.sponsor1tel }</li>
	  </c:if>
	  <c:if test="${not empty Intro.sponsor2 and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>주관사 정보 : </strong> ${Intro.sponsor2 }</li>
	  </c:if>
	  <c:if test="${not empty Intro.sponsor2tel and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>주관사 연락처 : </strong> ${Intro.sponsor2tel }</li>
	  </c:if>
	  <c:if test="${not empty Intro.subevent and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>부대행사 : </strong> ${Intro.subevent }</li>
	  </c:if>
	  <c:if test="${not empty Intro.usetimefestival and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>이용요금 : </strong> ${Intro.usetimefestival }</li>
	  </c:if>
	  <c:if test="${not empty Intro.accomcountleports and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>수용인원 : </strong> ${Intro.accomcountleports }</li>
	  </c:if>
	  <c:if test="${not empty Intro.chkcreditcardleports and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>신용카드가능 정보 : </strong> ${Intro.chkcreditcardleports }</li>
	  </c:if>
	  <c:if test="${not empty Intro.expagerangeleports and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>체험 가능연령 : </strong> ${Intro.expagerangeleports }</li>
	  </c:if>
	  <c:if test="${not empty Intro.infocenterleports and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>문의 및 안내 : </strong> ${Intro.infocenterleports }</li>
	  </c:if>
	  <c:if test="${not empty Intro.openperiod and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>개장기간 : </strong> ${Intro.openperiod }</li>
	  </c:if>
	  <c:if test="${not empty Intro.parkingfeeleports and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>주차요금 : </strong> ${Intro.parkingfeeleports }</li>
	  </c:if>
	  <c:if test="${not empty Intro.parkingleports and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>주차시설 : </strong> ${Intro.parkingleports }</li>
	  </c:if>
	  <c:if test="${not empty Intro.reservation and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>예약안내 : </strong> ${Intro.reservation }</li>
	  </c:if>
	  <c:if test="${not empty Intro.restdateleports and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>쉬는날 : </strong> ${Intro.restdateleports }</li>
	  </c:if>
	  <c:if test="${not empty Intro.scaleleports and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>규모 : </strong> ${Intro.scaleleports }</li>
	  </c:if>
	  <c:if test="${not empty Intro.usefeeleports and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
	    <li><strong>입장료 : </strong> ${Intro.usefeeleports }</li>
	  </c:if>
	  <c:if test="${not empty Intro.usetimeleports and detail.필드명 ne '0' and detail.필드명 ne '없음'}">
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
    </ul>
  </div>

  <!-- 💬 리뷰 영역 -->
  <div class="review-section">
    <h3>리뷰</h3>
    <textarea placeholder="로그인 후 작성 가능합니다." disabled></textarea>
    <button disabled>등록</button>
  </div>

</div>
<script>
  window.onload = function() {
    var mapX = parseFloat('${common.mapx}');
    var mapY = parseFloat('${common.mapy}');

    var mapContainer = document.getElementById('map');
    var mapOption = {
      center: new kakao.maps.LatLng(mapY, mapX),
      level: 3
    };

    var map = new kakao.maps.Map(mapContainer, mapOption);

    var marker = new kakao.maps.Marker({
      position: map.getCenter()
    });
    marker.setMap(map);
  };
  
</script>
</body>
</html>
