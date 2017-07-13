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
<title>Home</title>
<!-- 
	<link href="static/css/bootstrap.min.css" rel="stylesheet"/>
	<link href="static/css/main.css" rel="stylesheet"/>
	<link href="static/css/bootstrap-datepicker3.min.css" rel="stylesheet"/>
	<link href="static/css/font-awesome.min.css" rel="stylesheet"/>
	<script type="text/javascript" src="static/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="static/js/main.js"></script>
	<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
	 -->
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
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script type="text/javascript" src="static/js/main.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.0/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
							<c:when test='${MODE == "MODE_FIND_PROPOSAL" || MODE == "MODE_FIND_MY_PROPOSAL"}'>
								<c:if test="${MODE == 'MODE_FIND_PROPOSAL'}">
									<div class="title-content">
										<div class="row">Tìm đề nghị</div>
									</div>
								</c:if>
								<c:if test="${MODE == 'MODE_FIND_MY_PROPOSAL'}">
									<div class="title-content">
										<div class="row">
											Đề nghị của tôi
											<div class="col-sm-1" style="float: right; margin-top: 3px;">
												<a href="#" data-toggle="tooltip" data-placement="top"
													title="Tạo đề nghị"><i class="fa fa-plus fa-lg"
													aria-hidden="true"></i></a>
											</div>
										</div>
									</div>
								</c:if>
								<div class="title-content2">
									<form:form method="post" modelAttribute="filter-model"
										action="/" class="form-inline form-filer-data">
										<div class="form-group">
											<label>Ngày :</label>
											<form:input path="datecreate" type="text"
												class="form-control date-picker2" />
										</div>
										<div class="form-group">
											<label>Loại :</label>
											<form:select path="type" class="form-control set-height-25">
												<option value="all">Tất cả</option>
												<option value="create">Tạo</option>
												<option value="change">Chỉnh sửa</option>
												<option value="cancel">Hủy</option>
											</form:select>
										</div>
										<div class="form-group">
											<label>Tình trạng :</label>
											<form:select path="stt" class="form-control set-height-25">
												<option value="all">Tất cả</option>
												<option value="comfirm">Đã duyệt</option>
												<option value="notcomfirm">Chưa duyệt</option>
											</form:select>
										</div>
										<div style="float: right;">
											<button type="submit" class="btn btn-default set-height-25">Lọc</button>
											<a href="/" id="cancel-filter"> <i
												class="fa fa-times fa-lg" aria-hidden="true"></i>
											</a>
										</div>
									</form:form>
								</div>
								<table cellpadding="0" cellspacing="0"
									class="table table-striped table-content">
									<thead>
										<tr>
											<th style="width: 20%;">Tên</th>
											<th style="width: 20%;">Thời gian sử dụng</th>
											<th style="width: 10%;">Ngày tạo</th>
											<th style="width: 5%;">Loại</th>
											<th style="width: 10%;">Tình Trạng</th>
											<th style="width: 7%;" class="last-item-table">Chi tiết</th>
											<c:if test="${MODE == 'MODE_FIND_MY_PROPOSAL'}">
												<th style="width: 5%;" class="last-item-table">Sửa</th>
												<th style="width: 5%;" class="last-item-table">Hủy</th>
											</c:if>
											<c:if test="${MODE == 'MODE_FIND_PROPOSAL'}">
												<th style="width: 5%;" class="last-item-table">Duyệt</th>
												<th style="width: 5%;" class="last-item-table">Xem</th>
											</c:if>
											
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${listProposal}" var="list">
											<tr>
												<td><c:out value="${list.name}" /></td>
												<td><c:out value="${list.usefromdate}" /> đến <c:out
														value="${list.usetodate}" /></td>
												<td><c:out value="${list.userregister.dateregister}" /></td>
												<td><c:out value="${list.type.name}" /></td>
												<td><c:choose>
														<c:when test="${list.stt == 0}">
				                                			Chưa duyệt
				                                		</c:when>
														<c:otherwise>
				                                			Đã duyệt
				                                		</c:otherwise>
													</c:choose></td>
												<td class="last-item-table"><a href="#"
													data-toggle="tooltip" data-placement="right"
													data-html="true" title='<c:out value="${list.detail}"/>'>
														<i class="fa fa-info-circle fa-lg" aria-hidden="true"></i>
												</a></td>
												
												<c:if test="${MODE == 'MODE_FIND_MY_PROPOSAL'}">
													<td class="last-item-table"><a
														href="/change-proposal-${list.proposalID}"> <i
															class="fa fa-pencil-square-o fa-lg" aria-hidden="true"></i>
													</a></td>
													<td class="last-item-table"><a
														href="/cancel-proposal-${list.proposalID}"> <i
															class="fa fa-trash-o fa-lg" aria-hidden="true"></i>
													</a></td>
												</c:if>
												<c:if test="${MODE == 'MODE_FIND_PROPOSAL'}">
													<th style="width: 5%;" class="last-item-table">Xem</th>
												</c:if>
												
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</c:when>
							<c:when test='${MODE == "MODE_CREATE_PROPOSAL" || MODE == "MODE_CHANGE_PROPOSAL" || MODE == "MODE_CONFIRM_PROPOSAL"}'>
								<c:if test='${MODE == "MODE_CREATE_PROPOSAL"}'>
									<div class="title-content">Tạo Đề nghị</div>
								</c:if>
								<c:if test='${MODE == "MODE_CHANGE_PROPOSAL"}'>
									<div class="title-content">Chỉnh sữa đề nghị</div>
								</c:if>
								<c:if test='${MODE == "MODE_CONFIRM_PROPOSAL"}'>
									<div class="title-content">Xem đề nghị</div>
								</c:if>
								<form:form method="post" modelAttribute="Proposal"
									action="${typeForm}" class="form-horizontal"
									enctype="multipart/form-data">
									<div class="form-group">
										<label class="control-label col-sm-3">Tên</label>
										<div class="col-sm-7">
											<form:input path="name" type="text" class="form-control" value=""/>
											<c:out value="${proposalInfo.name}"></c:out>
											
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-3">Chi Tiết</label>
										<div class="col-sm-7">
											<form:textarea path="detail" class="form-control" cols="30"
												rows="10"></form:textarea>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-3">Xe</label>
										<div class="col-sm-7">
											<form:select id="choicecar" path="carID" class="btn btn-choose">
												<c:forEach items="${carsAvailble}" var="listcar">
													<option value='<c:out value="${listcar.carID}"/>'>
														<c:out value="${listcar.licenseplate}" /> -
														<c:out value="${listcar.type}" /> -
														<c:out value="${listcar.seats}" /> Chỗ
													</option>
												</c:forEach>
											</form:select>
											<i class="fa fa-info-circle fa-lg icon-show-deital"
												aria-hidden="true"></i>
										</div>
										<div class="col-sm-offset-3 col-sm-7">
											<div class="detail-car">
												<c:forEach items="${carsAvailble}" var="listcar">
													<div class="row" id='detail-car-<c:out value="${listcar.carID}"/>'>
														<div class="col-sm-2">
															<img
																src='static/img/user/<c:out value="${listcar.driver.email}"/>.jpg'
																width="70px" height="80px">
														</div>
														<div class="col-sm-5 padding-zero">
															<div class="col-sm-6">
																<p>
																	<strong>Tên :</strong>
																</p>
																<p>
																	<strong>Email :</strong>
																</p>
																<p>
																	<strong>Ngày sinh :</strong>
																</p>
															</div>
															<div class="col-sm-6 padding-zero">
																<p><c:out value="${listcar.driver.name}"/>/p>
																<p><c:out value="${listcar.driver.email}"/></p>
																<p><c:out value="${listcar.driver.birthday}"/></p>
															</div>
														</div>
														<div class="col-sm-5 padding-zero">
															<div class="col-sm-6 padding-zero">
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
															<div class="col-sm-6 padding-zero">
																<p><c:out value="${listcar.driver.phone}"/></p>
																<p><c:out value="${listcar.driver.experience}"/> năm</p>
																<p><c:out value="${listcar.driver.license}"/></p>
															</div>
														</div>
													</div>
													
												</c:forEach>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-3">Thời gian sử
											dụng</label>
										<div class="col-sm-7">
											<div class="col-sm-3" style="padding-left: 0px;">
												<form:select path="typedateuse" class="btn btn-choose"
													id="select-time-use">
													<c:choose>
														<c:when test='${Proposal.typedateuse == "manyday" }'>
															<option value="inday">Trong Ngày</option>
															<option selected value="manyday">Nhiều Ngày</option>
														</c:when>
														<c:otherwise>
															<option selected value="inday">Trong Ngày</option>
															<option  value="manyday">Nhiều Ngày</option>
														</c:otherwise>
													</c:choose>
													
												</form:select>
											</div>
											<div class="col-sm-4" id="in-day">
												<form:input path="useindate"
													class="form-control date-picker" type="text" />
											</div>
											<div class="col-sm-9 padding-right-zero" id="many-day">
												<div class="col-sm-1 padding-left-zero">
													<label class="control-label">Từ</label>
												</div>
												<div class="col-sm-5 padding-left-zero">
													<form:input path="usefromdate" type="text"
														class="form-control date-picker" />
												</div>
												<div class="col-sm-1 padding-left-zero">
													<label class="control-label">Đến</label>
												</div>
												<div class="col-sm-5 padding-right-zero">
													<form:input path="usetodate" type="text"
														class="form-control date-picker" />
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-3">Tệp đính kèm</label>
										<sec:authorize access="hasAuthority('CREATE_PROPOSAL')">
											<div class="col-sm-5">
												<form:input path="file" type="file" class="form-control" />
											</div>
										</sec:authorize>
										<c:if test='${MODE == "MODE_CHANGE_PROPOSAL"}'>
											<a href="/static/file/${Proposal.proposalID}.pdf" class="control-label col-sm-3"
												style="text-align: left;" download>File</a>
										</c:if>
									</div>
									<div class="form-group">
										<sec:authorize access="hasAuthority('CREATE_PROPOSAL')">
											<c:if test='${MODE == "MODE_CREATE_PROPOSAL"}'>
												<div class="col-sm-offset-5 col-sm-2">
													<button type="submit" class="btn btn-default">Submit</button>
												</div>
											</c:if>
											<c:if test='${MODE == "MODE_CHANGE_PROPOSAL"}'>
												<div class="col-sm-offset-5 col-sm-4">
													<button type="submit" name="type" value="change"
														class="btn btn-default">Chỉnh sữa</button>
													<a href="/delete-proposal" type="submit" class="btn btn-default">Xóa</a>
													<a href="/" class="btn btn-default">Trở về</a>
												</div>
											</c:if>
										</sec:authorize>
										<sec:authorize access="hasAuthority('CONFIRM_FORM')">
											<button type="submit" name="type" value="confirm"
												class="btn btn-default">Chỉnh sữa</button>
											<button type="submit" name="type" class="btn btn-default"
												value="cancel">Xóa</button>
											<button class="btn btn-default">Trở về</button>
										</sec:authorize>
									</div>
								</form:form>
							</c:when>
							<c:when test='${MODE == "MODE_CHECK_STT_CARS"}'>
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
									<div class="tab-content">
										<div class="tab-pane fade in active" id="ready">
											<table cellpadding="0" cellspacing="0"
												class="table table-striped table-content">
												<thead>
													<tr>
														<th>Số Xe</th>
														<th>Thông Tin</th>
														<th>Loại</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>4</td>
														<td>2</td>
														<td>1</td>
													</tr>
												</tbody>
											</table>
										</div>
										<div class="tab-pane fade" id="registered">
											<table cellpadding="0" cellspacing="0"
												class="table table-striped table-content">
												<thead>
													<tr>
														<th>Số Xe</th>
														<th>Thông Tin</th>
														<th>Loại</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>4</td>
														<td>2</td>
														<td>1</td>
													</tr>
												</tbody>
											</table>
										</div>
										<div class="tab-pane fade" id="inactive">
											<table cellpadding="0" cellspacing="0"
												class="table table-striped table-content">
												<thead>
													<tr>
														<th>Số Xe</th>
														<th>Thông Tin</th>
														<th>Loại</th>
														<th>Loại</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>4</td>
														<td>2</td>
														<td>1</td>
														<td>1</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</c:when>
							<c:when test='${MODE == "MODE_FIND_CARS"}'>
								<div class="title-content">Kiểm tra tình trạng hoạt động
									của xe</div>
								<table cellpadding="0" cellspacing="0"
									class="table table-striped table-content">
									<thead>
										<tr>
											<th>Số Xe</th>
											<th>Thông Tin</th>
											<th>Loại</th>
											<th>Thông Tin</th>
											<th>Chi tiết</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>4</td>
											<td>2</td>
											<td>1</td>
											<td>2</td>
											<td class="show-deital-driver"><a href="#"><i
													class="fa fa-info-circle fa-lg" aria-hidden="true"></i></a></td>
										</tr>
										<tr class="driver-info" id="driver-info-0">
											<td colspan='5'>
												<div>Thông tin tài xế</div>
												<div class="row">
													<div class="col-sm-2">
														<img src="https://goo.gl/ZVUgrD" width="70px"
															height="80px">
													</div>
													<div class="col-sm-4">
														<div class="col-sm-6">
															<p>
																<strong>Tên :</strong>
															</p>
															<p>
																<strong>Ngày Sinh :</strong>
															</p>
															<p>
																<strong>Bằng Lái :</strong>
															</p>
														</div>
														<div class="col-sm-6">
															<p>Nguyễn Văn A</p>
															<p>24/9/1996</p>
															<p>A1</p>
														</div>
													</div>
													<div class="col-sm-6">
														<div class="col-sm-4">
															<p>
																<strong>Tên :</strong>
															</p>
															<p>
																<strong>Ngày Sinh :</strong>
															</p>
															<p>
																<strong>Bằng Lái :</strong>
															</p>
														</div>
														<div class="col-sm-6">
															<p>Nguyễn Văn A</p>
															<p>24/9/1996</p>
															<p>A1</p>
														</div>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td>4</td>
											<td>2</td>
											<td>1</td>
											<td>2</td>
											<td class="show-deital-driver"><a href="#"><i
													class="fa fa-info-circle fa-lg" aria-hidden="true"></i></a></td>
										</tr>
										<tr class="driver-info" id="driver-info-2">
											<td colspan='5'>
												<div>Thông tin tài xế</div>
												<div class="row">
													<div class="col-sm-2">
														<img src="https://goo.gl/ZVUgrD" width="70px"
															height="80px">
													</div>
													<div class="col-sm-4">
														<div class="col-sm-6">
															<p>
																<strong>Tên :</strong>
															</p>
															<p>
																<strong>Ngày Sinh :</strong>
															</p>
															<p>
																<strong>Bằng Lái :</strong>
															</p>
														</div>
														<div class="col-sm-6">
															<p>Nguyễn Văn A</p>
															<p>24/9/1996</p>
															<p>A1</p>
														</div>
													</div>
													<div class="col-sm-6">
														<div class="col-sm-4">
															<p>
																<strong>Tên :</strong>
															</p>
															<p>
																<strong>Ngày Sinh :</strong>
															</p>
															<p>
																<strong>Bằng Lái :</strong>
															</p>
														</div>
														<div class="col-sm-6">
															<p>Nguyễn Văn A</p>
															<p>24/9/1996</p>
															<p>A1</p>
														</div>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td>4</td>
											<td>2</td>
											<td>1</td>
											<td>2</td>
											<td><a href="#">show</a></td>
										</tr>
										<tr class="driver-info" id="driver-info-4">
											<td colspan='5'>
												<div>Thông tin tài xế</div>
												<div class="row">
													<div class="col-sm-2">
														<img src="https://goo.gl/ZVUgrD" width="70px"
															height="80px">
													</div>
													<div class="col-sm-4">
														<div class="col-sm-6">
															<p>
																<strong>Tên :</strong>
															</p>
															<p>
																<strong>Ngày Sinh :</strong>
															</p>
															<p>
																<strong>Bằng Lái :</strong>
															</p>
														</div>
														<div class="col-sm-6">
															<p>Nguyễn Văn A</p>
															<p>24/9/1996</p>
															<p>A1</p>
														</div>
													</div>
													<div class="col-sm-6">
														<div class="col-sm-4">
															<p>
																<strong>Tên :</strong>
															</p>
															<p>
																<strong>Ngày Sinh :</strong>
															</p>
															<p>
																<strong>Bằng Lái :</strong>
															</p>
														</div>
														<div class="col-sm-6">
															<p>Nguyễn Văn A</p>
															<p>24/9/1996</p>
															<p>A1</p>
														</div>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td>4</td>
											<td>2</td>
											<td>1</td>
											<td>2</td>
											<td><a href="#">show</a></td>
										</tr>
									</tbody>
								</table>
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
									<c:choose>
										<c:when test="${notifys.stt == 1}">
											<div class="item-notify">
												<sec:authorize access="hasAuthority('CONFIRM_PROPOSAL')">
													<a href="confirm-proposal-${notifys.proposal.proposalID}">
					                                    <div>
					                                       <strong><c:out value="${notifys.user.roleUser.name}"/></strong> 
					                                        đã duyệt đề nghị <c:out value="${notifys.proposal.name}"/> của 
					                                        <strong><c:out value="${notifys.proposal.userregister.user.name}"/></strong>
					                                    </div>
					                                    <p class="time-ago"><i class="fa fa-plus-circle" aria-hidden="true"></i> Vừa xong</p>
					                                </a>
				                                </sec:authorize>
				                                <sec:authorize  access="hasAuthority('CHANGE_PROPOSAL')">
				                                	<a href="change-proposal-${notifys.proposal.proposalID}">
					                                    <div>
					                                       <strong><c:out value="${notifys.user.roleUser.name}"/></strong> 
					                                        đã duyệt đề nghị <c:out value="${notifys.proposal.name}"/> của bạn
					                                    </div>
					                                    <p class="time-ago"><i class="fa fa-plus-circle" aria-hidden="true"></i> Vừa xong</p>
					                                </a>
				                                </sec:authorize>
				                            </div>
										</c:when>
										<c:otherwise>
											<sec:authorize access="hasAuthority('CONFIRM_PROPOSAL')">
												<div class="item-notify">
					                                <a href="#">
					                                    <div>
					                                       <strong><c:out value="${notifys.user.name}"></c:out></strong> 
					                                        đã <c:out value="${notifys.type.name}"/> một đề nghị
					                                    </div>
					                                    <p class="time-ago">
					                                    	<c:choose>
					                                    		<c:when test="${notifys.type.name == 'Tạo'}">
					                                    			<i class="fa fa-plus-circle" aria-hidden="true"></i>
					                                    		</c:when>
					                                    		<c:when test="${notifys.type.name == 'Chỉnh Sửa'}">
					                                    			<i class="fa fa-pencil-square-o" aria-hidden="true"></i> 
					                                    		</c:when>
					                                    		<c:otherwise>
					                                    			<i class="fa fa-trash" aria-hidden="true"></i> 
					                                    		</c:otherwise>
					                                    	</c:choose>
						                                    <c:out value="${notifys.time}"/>
					                                    </p>
					                                </a>
					                            </div>
											</sec:authorize>
										</c:otherwise>
									</c:choose>
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