<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Car Manager</title>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="static/css/main.css" rel="stylesheet" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.0/css/bootstrap-datepicker3.min.css"
	rel="stylesheet" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-timepicker/0.5.2/css/bootstrap-timepicker.min.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script type="text/javascript" src="static/js/main.js"></script>
<script type="text/javascript" src="static/js/caranddriver.js"></script>
<script type="text/javascript" src="static/js/validate.js"></script>
<script type="text/javascript" src="static/js/ckeditor/ckeditor.js"></script>

<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.0/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-timepicker/0.5.2/js/bootstrap-timepicker.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-timepicker/0.5.2/js/bootstrap-timepicker.min.js"></script>
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
							<c:when test='${MODE == "MODE_FIND_CARS"}'>
								<div class="title-content">
									<div class="row">
										Tra cứu xe
										<div class="col-sm-1" style="float: right; margin-top: 3px;">
											<a href="/create-car" data-toggle="tooltip"
												data-placement="top" title="Thêm xe mới"><i
												class="fa fa-plus fa-lg" aria-hidden="true"></i></a>
										</div>
									</div>
								</div>
								<div class="title-content2">
									<form:form method="post" modelAttribute="filter-car"
										action="/find-cars" class="form-inline form-filer-data">
										<div class="form-group">
											<label class="control-label">Loại :</label>
											<form:select path="type" class="form-control set-height-25">
												<form:option value="all">Tất cả</form:option>
												<c:forEach items="${listtype_seats}" var="car">
													<form:option value='${car.type}'>
														<c:out value="${car.type}" />
													</form:option>
												</c:forEach>
											</form:select>
										</div>
										<div class="form-group">
											<label class="control-label">Chỗ Ngồi :</label>
											<form:select path="seat" class="form-control set-height-25">
												<form:option value="-1">Tất cả</form:option>
												<c:forEach items="${listtype_seats}" var="car">
													<form:option value='${car.seats}'>
														<c:out value="${car.seats}" />
													</form:option>
												</c:forEach>
											</form:select>
										</div>
										<div style="float: right;">
											<button type="submit" class="btn btn-default set-height-25">Lọc</button>
											<a href="#" id="cancel-filter"> <i
												class="fa fa-times fa-lg" aria-hidden="true"></i>
											</a>
										</div>
									</form:form>
								</div>
								<table cellpadding="0" cellspacing="0"
									class="table table-striped table-content">
									<thead>
										<tr>
											<th>Số Xe</th>
											<th>Loại</th>
											<th>Chỗ Ngồi</th>
											<th>Tình Trạng</th>
											<th style="width: 8%;">Chi tiết</th>
											<th style="width: 6%;">Sửa</th>
											<th style="width: 6%;">Xóa</th>
										</tr>
									</thead>
									<tbody>
										<%
											int x = 0;
										%>
										<c:forEach items="${listcars}" var="car">
											<tr>
												<td><c:out value="${car.licenseplate}" /></td>
												<td><c:out value="${car.type}" /></td>
												<td><c:out value="${car.seats}" /> Chỗ</td>
												<td><c:out value="${car.sttcar.name}" /></td>
												<td class="show-deital-driver last-item-table"><a href="#"
													data-toggle="tooltip" data-placement="top"
													title="Thông tin tài xế"> <i
														class="fa fa-info-circle fa-lg" aria-hidden="true"></i>
												</a></td>
												<td ><a
													href="/change-car-${car.carID}"> <i
														class="fa fa-pencil-square-o fa-lg" aria-hidden="true"></i>
												</a></td>
												<td><a
													href="/remove-car-${car.carID}"> <i
														class="fa fa-trash-o fa-lg" aria-hidden="true"></i>
												</a></td>
											</tr>
											<tr class="driver-info" id="driver-info-<%=x%>">
												<%
													x += 2;
												%>
												<td colspan='5'>
													<div
														style="text-align: center; margin-bottom: 8px; border-bottom: 1px solid #ddd; padding-bottom: 3px;">
														<strong>Thông tin tài xế</strong>
													</div>
													<div class="row">
														<div class="col-sm-2 padding-right-zero"
															style="text-align: center;">
															<img
																src='static/img/user/<c:out value="${car.driver.email}"/>.jpg'
																width="70px" height="80px">
														</div>
														<div class="col-sm-5 padding-left-zero">
															<div class="col-sm-4">
																<p>
																	<strong>Tên :</strong>
																</p>
																<p>
																	<strong>Email:</strong>
																</p>
																<p>
																	<strong>Ngày sinh :</strong>
																</p>
															</div>
															<div class="col-sm-8">
																<p>
																	<c:out value="${car.driver.name}" />
																</p>
																<p>
																	<c:out value="${car.driver.email}" />
																</p>
																<p>
																	<c:out value="${car.driver.birthday}" />
																</p>
															</div>
														</div>
														<div class="col-sm-5">
															<div class="col-sm-5">
																<p>
																	<strong>SĐT :</strong>
																</p>
																<p>
																	<strong>Kinh nghiệm :</strong>
																</p>
																<p>
																	<strong>Bằng Lái :</strong>
																</p>
															</div>
															<div class="col-sm-7">
																<p>
																	<c:out value="${car.driver.phone}" />
																</p>
																<p>
																	<c:out value="${car.driver.experience}" />
																	năm
																</p>
																<p>
																	<c:out value="${car.driver.license}" />
																</p>
															</div>
														</div>
													</div>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</c:when>

							<c:when test='${MODE ==  "MODE_CHECK_STT_CARS"}'>
								<div class="title-content">Kiểm tra tình trạng hoạt động
									của xe</div>
								<div class="">
									<ul class="nav nav-tabs nav-justified">
										<li class="active"><a href="#ready" data-toggle="tab">Sẵn
												sàng</a></li>
										<li><a href="#registered" data-toggle="tab">Đã đăng
												ký</a></li>
										<li><a href="#inactive" data-toggle="tab">Không hoạt
												động</a></li>
									</ul>
								</div>
								
								<div class="tab-content">
									<div class="tab-pane fade in active" id="ready">
										<table cellpadding="0" cellspacing="0"
											class="table table-striped table-content">
											<thead>
												<tr>
													<th>Số xe</th>
													<th>Tên việc</th>
													<th>Thời gian</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${listCarReady}" var="carReady">
													<tr>
														<td><c:out value='${carReady.licenseplate}' /></td>
														<td><c:out value="${carReady.name}" /></td>
														<td><c:out value="${carReady.usefromdate}" /> - 
														<c:out value="${carReady.usetodate}" /></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div class="tab-pane fade" id="registered">
										<table cellpadding="0" cellspacing="0"
											class="table table-striped table-content">
											<thead>
												<tr>
													<th>Số Xe</th>
													<th>Tên việc</th>
													<th>Thời gian sử dụng</th>
													<th>Thời gian đăng ký</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${listCarRegistered}" var="carRegistered">
													<tr>
														<td><c:out value="${carRegistered.licenseplate}" /></td>
														<td>
															<c:forEach items="${carRegistered.listproposal}" var="proposalofcar">
																<p>
																	<c:out value="${proposalofcar.name}" />
																</p>
															</c:forEach>
														</td>
														<td>
															<c:forEach items="${carRegistered.listproposal}" var="proposalofcar">
																<p>
																	<c:out value="${proposalofcar.usefromdate}" /> - <c:out value="${proposalofcar.usetodate}" />
																</p>
															</c:forEach>
														</td>
														<td>
															<c:forEach items="${carRegistered.listproposal}" var="proposalofcar">
																<p><c:out value="${proposalofcar.userregister.dateregister}" /></p>
															</c:forEach>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div class="tab-pane fade" id="inactive">
										<table cellpadding="0" cellspacing="0"
											class="table table-striped table-content">
											<thead>
												<tr>
													<th>Số Xe</th>
													<th>Loại</th>
													<th>Chỗ ngồi</th>
													<th>Tình trạng</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${listCarNotRegistered}" var="carNotRegistered">
													<tr>
														<td><c:out value="${carNotRegistered.licenseplate}" /></td>
														<td><c:out value="${carNotRegistered.type}" /></td>
														<td><c:out value="${carNotRegistered.seats}" /> chỗ</td>
														<td><c:out value="${carNotRegistered.sttcar.name}" /></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</c:when>

							<c:when test="${MODE == 'MODE_CREATE_CAR' || MODE == 'MODE_CHANGE_CAR'}">
								<c:if test="${MODE == 'MODE_CREATE_CAR' }">
									<div class="title-content">Thêm Xe</div>
								</c:if>
								<c:if test="${MODE == 'MODE_CHANGE_CAR' }">
									<div class="title-content">Chỉnh sữa Xe</div>
								</c:if>
								
								<form:form method="post" modelAttribute="Car" action="${typeForm}"
									class="form-horizontal">
									<div class="form-group">
										<label class="control-label col-sm-3">Số xe</label>
										<div class="col-sm-7">
											<form:input path="licenseplate" type="text" class="form-control" />
											<div class="has-error">
												<form:errors class="control-label" path="name" />
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-3">Loại</label>
										<div class="col-sm-7">
											<form:input path="type" type="text" class="form-control" />
											<div class="has-error">
												<form:errors class="control-label" path="type" />
											</div>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-sm-3">Chỗ ngồi</label>
										<div class="col-sm-7">
											<form:input path="seats" type="number" class="form-control date-picker" />
											<div class="has-error">
												<form:errors class="control-label" path="seats" />
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-3">Tài xế</label>
										<div class="col-sm-7">
											<form:select  path="emailDriver" class="btn btn-choose">
												<c:forEach items="${listDriver}" var="driver">
													<form:option value="<c:out value='${driver.email}'/>">
														<c:out value='${driver.name}'/>
													</form:option>
												</c:forEach>
											</form:select>
											<div class="has-error">
												<form:errors class="control-label" path="emailDriver" />
											</div>
										</div>
									</div>
									<c:if test="${MODE == 'MODE_CHANGE_CAR' }">
										<div class="form-group">
											<label class="control-label col-sm-3">Tình trạng</label>
											<div class="col-sm-7">
												<form:select path="sttcarID" class="btn btn-choose">
												<c:forEach items="${liststtcar}" var="stt">
													<form:option value="<c:out value='${stt.name}'/>">
														<c:out value='${stt.name}'/>
													</form:option>
												</c:forEach>
												</form:select>
												<div class="has-error">
													<form:errors class="control-label" path="sttcarID" />
												</div>
											</div>
										</div>
									</c:if>
									
									<div class="form-group">
										<c:if test="${MODE == 'MODE_CREATE_CAR' }">
											<div class="col-sm-offset-5 col-sm-3">
												<button type="submit" class="btn btn-default">Submit</button>
												<a href="/" class="btn btn-default">Trở về</a>
											</div>
										</c:if>
										<c:if test="${MODE == 'MODE_CHANGE_CAR' }">
											<div class="col-sm-offset-5 col-sm-5">
												<button type="submit" class="btn btn-default">Chỉnh sữa</button>
												<a href="/remove-car" class="btn btn-default">Xóa</a>
												<a href="/" class="btn btn-default">Trở về</a>
											</div>
										</c:if>
										
									</div>
								</form:form>
							</c:when>

						</c:choose>
					</div>
				</div>
			<div class="col-sm-3">
				<div class="block">
					<div class="content">
						<div class="minicalendar">
							<div class="title-calendar">Calendar</div>
							${calendar}
						</div>
					</div>
				</div>
				<div class="block">
					<div class="mininotify">
						<div class="title-notify">Notification</div>
						<div class="list-notify">
							<c:forEach items="${listNotify}" var="notifys">
								<div class="item-notify">
									<sec:authorize access="hasAuthority('CONFIRM_PROPOSAL')">
										<a href="confirm-proposal-${notifys.proposalID}">
											${notifys.message}
											${notifys.time}
											</p>
										</a>
									</sec:authorize>
									<sec:authorize access="hasAuthority('CHANGE_PROPOSAL')">
										<a href="change-proposal-${notifys.proposalID}">
											${notifys.message}
											${notifys.time}
											</p>
										</a>
									</sec:authorize>
								</div>
							</c:forEach>
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