<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
<link href="static/css/bootstrap.min.css" rel="stylesheet"/>
<link href="static/css/main.css" rel="stylesheet"/>
<script type="text/javascript" src="static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="static/js/main.js"></script>
</head>
<body>
	<t:header>
		<t:narbar></t:narbar>
	</t:header>
	<div class="container">
	    <div id="main-region">
	        <div class="row">
	            <div class="col-sm-9">
	                <div class="main-content">
	                <c:choose>
	                	<c:when test='${MODE == "STUF"}'>
	                		<div class="title-content">XXXX</div>
		                    <table cellpadding="0" cellspacing="0" class="table table-striped table-content">
		                        <thead>
		                            <tr>
		                                <th>Số Xe</th>
		                                <th>Thông Tin</th>
		                                <th>Loại</th>
		                                <th>Ngày</th>
		                                <th>Tình Trạng</th>
		                            </tr>
		                        </thead>
		                        <tbody>
		                            <tr>
		                                <td>4</td>
		                                <td>2</td>
		                                <td>1</td>
		                                <td>3</td>
		                                <td>5asdasdasdasdasdasd</td>
		                            </tr>
		                        </tbody>
		                    </table>
	                	</c:when>
	                	<c:when test='${MODE == "MODE_CREATE_FORM"}'>
	                		
	                	</c:when>
	                </c:choose>
	                    
	                </div>
	            </div>
	            <div class="col-sm-3">
	                <div class="block">
	                    <div class="content">
	                        <div class="minicalendar">
	                            <div class="title-calendar">
	                                Calendar
	                            </div>
	                            <div class="calendar-controls">
	                                <a class="arrow_link previous" href="#" title="Tháng trước">
	                                    <span class="arrow ">◄</span>
	                                    <span class="accesshide ">
	                                    &nbsp;
	                                    <span class="arrow_text"></span>
	                                    </span>
	                                </a>
	                                <span class="hide"> | </span>
	                                <span class="current">
	            <a title="Tháng này" href="#">July 2017</a>
	          </span>
	                                <span class="hide"> | </span>
	                                <a class="arrow_link next" style="float:right;" href="#" title="Tháng tới">
	                                    <span class="accesshide ">
	              <span class="arrow_text"></span> &nbsp;
	                                    </span>
	                                    <span class="arrow">►</span>
	                                </a>
	                            </div>
	                            <table class="calendartable">
	                                <tbody>
	                                    <tr class="weekdays">
	                                        <th scope="col">T2</th>
	                                        <th scope="col">T3</th>
	                                        <th scope="col">T4</th>
	                                        <th scope="col">T5</th>
	                                        <th scope="col">T6</th>
	                                        <th scope="col">T7</th>
	                                        <th scope="col">CN</th>
	                                    </tr>
	                                    <tr>
	                                        <td class="dayblank">&nbsp;</td>
	                                        <td class="dayblank">&nbsp;</td>
	                                        <td class="dayblank">&nbsp;</td>
	                                        <td class="dayblank">&nbsp;</td>
	                                        <td class="dayblank">&nbsp;</td>
	                                        <td class="day today eventnone">
	                                            <a href="#">1</a>
	                                        </td>
	                                        <td class="weekend day">2</td>
	                                    </tr>
	                                    <tr>
	                                        <td class="day">3</td>
	                                        <td class="day">4</td>
	                                        <td class="day">5</td>
	                                        <td class="day">6</td>
	                                        <td class="day">7</td>
	                                        <td class="day">8</td>
	                                        <td class="weekend day">9</td>
	                                    </tr>
	                                    <tr>
	                                        <td class="day">10</td>
	                                        <td class="day">11</td>
	                                        <td class="day">12</td>
	                                        <td class="day">13</td>
	                                        <td class="day">14</td>
	                                        <td class="day">15</td>
	                                        <td class="weekend day">16</td>
	                                    </tr>
	                                    <tr>
	                                        <td class="day">17</td>
	                                        <td class="day">18</td>
	                                        <td class="day">19</td>
	                                        <td class="day">20</td>
	                                        <td class="day">21</td>
	                                        <td class="day">22</td>
	                                        <td class="weekend day">23</td>
	                                    </tr>
	                                    <tr>
	                                        <td class="day">24</td>
	                                        <td class="day">25</td>
	                                        <td class="day">26</td>
	                                        <td class="day">27</td>
	                                        <td class="day">28</td>
	                                        <td class="day">29</td>
	                                        <td class="weekend day">30</td>
	                                    </tr>
	                                    <tr>
	                                        <td class="day">31</td>
	                                        <td class="dayblank">&nbsp;</td>
	                                        <td class="dayblank">&nbsp;</td>
	                                        <td class="dayblank">&nbsp;</td>
	                                        <td class="dayblank">&nbsp;</td>
	                                        <td class="dayblank">&nbsp;</td>
	                                        <td class="dayblank">&nbsp;</td>
	                                    </tr>
	                                </tbody>
	                            </table>
	                        </div>
	                    </div>
	                </div>
	                <div class="block">
	                    <div class="mininotify">
	                        <div class="title-notify">Notification</div>
	                        <div class="list-notify">
	                            <a href="#">Tham quan Renasas </a>
	                            <p>Hôm Nay, 1/7/2017 <span class="time-notify-left">(Còn 6d 2h)</span></p>
	                            <a href="#">Tham quan Renasas</a>
	                            <p>Hôm Nay, 1/7/2017</p>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
	<t:footer></t:footer>
</body>
</html>