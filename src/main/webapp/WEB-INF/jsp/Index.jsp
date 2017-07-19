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
												<a href="/create-proposal" data-toggle="tooltip" data-placement="top"
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
											<label class="control-label">Ngày :</label>
											<form:input path="datecreate" type="text"
												class="form-control date-picker2" />
										</div>
										<div class="form-group">
											<label class="control-label">Loại :</label>
											<form:select path="type" class="form-control set-height-25">
												<form:option value="0">Tất cả</form:option>
												<form:option value="1">Tạo</form:option>
												<form:option value="2">Chỉnh sửa</form:option>
												<form:option value="3">Hủy</form:option>
											</form:select>
										</div>
										<div class="form-group">
											<label class="control-label">Tình trạng :</label>
											<form:select path="stt" class="form-control set-height-25">
												<form:option value="-1">Tất cả</form:option>
												<form:option value="1">Đã duyệt</form:option>
												<form:option value="0">Chưa duyệt</form:option>
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
											<th style="width: 20%;">Tên</th>
											<th style="width: 20%;">Thời gian sử dụng</th>
											<th style="width: 10%;">Ngày tạo</th>
											<th style="width: 10%;">Loại</th>
											<th style="width: 10%;">Tình Trạng</th>
											<th style="width: 7%;" class="last-item-table">Chi tiết</th>
											<c:if test="${MODE == 'MODE_FIND_MY_PROPOSAL'}">
												<th style="width: 5%;" class="last-item-table">Sửa</th>
												<th style="width: 5%;" class="last-item-table">Hủy</th>
											</c:if>
											<c:if test="${MODE == 'MODE_FIND_PROPOSAL'}">
												<th style="width: 5%;" class="last-item-table">Xem</th>
											</c:if>

										</tr>
									</thead>
									<tbody>
										<c:forEach items="${listProposal}" var="list">
											<tr>
												<td><c:out value="${list.name}" /></td>
												<td><c:out value="${list.usefromdate}" /> đến <c:out value="${list.usetodate}" /></td>
												<td><c:out value="${list.userregister.dateregister}" /></td>
												<td><c:out value="${list.type.name}" /></td>
												<td><c:out value="${list.stt.name}"/> </td>
												<td class="last-item-table"><a href="#"
													data-toggle="tooltip" data-placement="right"
													data-html="true" title='<c:out value="${list.detail}"/>'>
														<i class="fa fa-info-circle fa-lg" aria-hidden="true"></i>
												</a></td>

												<c:if test="${MODE == 'MODE_FIND_MY_PROPOSAL'}">
													<td class="last-item-table">
														<a href="/change-proposal-${list.proposalID}"> 
															<i class="fa fa-pencil-square-o fa-lg" aria-hidden="true"></i>
														</a>
													</td>
													<td class="last-item-table"><a
														href="/cancel-proposal-${list.proposalID}"> <i
															class="fa fa-trash-o fa-lg" aria-hidden="true"></i>
													</a></td>
												</c:if>
												<c:if test="${MODE == 'MODE_FIND_PROPOSAL'}">
													<th style="width: 5%;" class="last-item-table">
														<a href="/confirm-proposal-${list.proposalID}">
															<i class="fa fa-pencil" aria-hidden="true"></i>
														</a>
														
													</th>
												</c:if>

											</tr>
										</c:forEach>
									</tbody>
								</table>
							</c:when>
							<c:when test='${MODE == "MODE_CREATE_PROPOSAL" || MODE == "MODE_CHANGE_PROPOSAL"}'>
								<c:if test='${MODE == "MODE_CREATE_PROPOSAL"}'>
									<div class="title-content">Tạo Đề nghị</div>
								</c:if>
								<c:if test='${MODE == "MODE_CHANGE_PROPOSAL"}'>
									<div class="title-content">Chỉnh sữa đề nghị</div>
								</c:if>
								<form:form method="post" modelAttribute="Proposal"
									action="${typeForm}" class="form-horizontal"
									enctype="multipart/form-data">
									<c:choose>
										<c:when test="${not empty message}">
											<div class="form-group">
	                            				<div style="color:red;font-size:15px;text-align:center;"><c:out value="${message}"/> </div>
	                            			</div>
	                            			<div class="form-group">
												<label class="control-label col-sm-3">Tên</label>
												<div class="col-sm-7">
													<form:input disabled="true" path="name" type="text" class="form-control"
														value="" />
													<c:out value="${proposalInfo.name}"></c:out>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-3">Chi Tiết</label>
												<div class="col-sm-7">
													<form:textarea disabled="true" path="detail" class="form-control" cols="30"
														rows="10"></form:textarea>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-3">Xe</label>
												<div class="col-sm-7">
													<form:select disabled="true" id="choicecar" path="carID"
														class="btn btn-choose">
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
															<div class="row"
																id='detail-car-<c:out value="${listcar.carID}"/>'>
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
																		<p>
																			<c:out value="${listcar.driver.name}" />
																			/p>
																		<p>
																			<c:out value="${listcar.driver.email}" />
																		</p>
																		<p>
																			<c:out value="${listcar.driver.birthday}" />
																		</p>
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
																		<p>
																			<c:out value="${listcar.driver.phone}" />
																		</p>
																		<p>
																			<c:out value="${listcar.driver.experience}" />
																			năm
																		</p>
																		<p>
																			<c:out value="${listcar.driver.license}" />
																		</p>
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
														<form:select disabled="true" path="typedateuse" class="btn btn-choose"
															id="select-time-use">
															<c:choose>
																<c:when test='${Proposal.typedateuse == "manyday" }'>
																	<option value="inday">Trong Ngày</option>
																	<option selected value="manyday">Nhiều Ngày</option>
																</c:when>
																<c:otherwise>
																	<option selected value="inday">Trong Ngày</option>
																	<option value="manyday">Nhiều Ngày</option>
																</c:otherwise>
															</c:choose>
														</form:select>
													</div>
													<div class="col-sm-4" id="in-day">
														<form:input disabled="true" path="useindate"
															class="form-control date-picker" type="text" />
													</div>
													<div class="col-sm-9 padding-right-zero" id="many-day">
														<div class="col-sm-1 padding-left-zero">
															<label class="control-label">Từ</label>
														</div>
														<div class="col-sm-5 padding-left-zero">
															<form:input disabled="true" path="usefromdate" type="text"
																class="form-control date-picker" />
														</div>
														<div class="col-sm-1 padding-left-zero">
															<label class="control-label">Đến</label>
														</div>
														<div class="col-sm-5 padding-right-zero">
															<form:input disabled="true" path="usetodate" type="text"
																class="form-control date-picker" />
														</div>
													</div>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-3">Tệp đính kèm</label>
												<c:if test='${MODE == "MODE_CHANGE_PROPOSAL" && Proposal.isFileExist == true}'>
													<a href="/static/file/${Proposal.proposalID}.pdf"
														class="control-label col-sm-3" style="text-align: left;"
														download>File</a>
												</c:if>
											</div>
											<div class="form-group">
												<div class="col-sm-offset-5 col-sm-2">
													<a href="/" class="btn btn-default">Trở về</a>
												</div>
											</div>
										</c:when>
										<c:otherwise>
											<div class="form-group">
												<label class="control-label col-sm-3">Tên</label>
												<div class="col-sm-7">
													<form:input path="name" type="text" class="form-control"
														value="" />
													<c:out value="${proposalInfo.name}"></c:out>
													<div class="has-error">
							                        	<form:errors class="control-label" path="name"/>
							                        </div>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-3">Chi Tiết</label>
												<div class="col-sm-7">
													<form:textarea path="detail" class="form-control" cols="30"
														rows="10"></form:textarea>
													<div class="has-error">
							                        	<form:errors class="control-label" path="detail"/>
							                        </div>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-3">Xe</label>
												<div class="col-sm-7">
													<form:select id="choicecar" path="carID"
														class="btn btn-choose">
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
													<div class="has-error">
							                        	<form:errors class="control-label" path="carID"/>
							                        </div>
												</div>
												<div class="col-sm-offset-3 col-sm-7">
													<div class="detail-car">
														<c:forEach items="${carsAvailble}" var="listcar">
															<div class="row"
																id='detail-car-<c:out value="${listcar.carID}"/>'>
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
																		<p>
																			<c:out value="${listcar.driver.name}" />
																			/p>
																		<p>
																			<c:out value="${listcar.driver.email}" />
																		</p>
																		<p>
																			<c:out value="${listcar.driver.birthday}" />
																		</p>
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
																		<p>
																			<c:out value="${listcar.driver.phone}" />
																		</p>
																		<p>
																			<c:out value="${listcar.driver.experience}" />
																			năm
																		</p>
																		<p>
																			<c:out value="${listcar.driver.license}" />
																		</p>
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
																	<option value="manyday">Nhiều Ngày</option>
																</c:otherwise>
															</c:choose>
														</form:select>
														<div class="has-error">
								                        	<form:errors class="control-label" path="typedateuse"/>
								                        </div>
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
													<div class="has-error">
								                    	<form:errors class="control-label" path="usefromdate"/>
								                    </div>
								                    <div class="has-error">
								                    	<form:errors class="control-label" path="usetodate"/>
								                    </div>
								                    <div class="has-error">
								                    	<form:errors class="control-label" path="useindate"/>
								                    </div>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-3">Tệp đính kèm</label>
												<div class="col-sm-5">
													<form:input path="file" type="file" class="form-control" />
												</div>
												<div class="has-error">
													<form:errors class="control-label" path="file" />
												</div>
												<c:if test='${MODE == "MODE_CHANGE_PROPOSAL" && Proposal.isFileExist == true}'>
													<a href="/static/file/${Proposal.proposalID}.pdf"
														class="control-label col-sm-3" style="text-align: left;"
														download>File</a>
												</c:if>
											</div>
											<div class="form-group">
												<c:if test='${MODE == "MODE_CREATE_PROPOSAL" }'>
													<div class="col-sm-offset-5 col-sm-2">
														<button type="submit" class="btn btn-default">Submit</button>
													</div>
												</c:if>
												<c:if test='${MODE == "MODE_CHANGE_PROPOSAL"}'>
													<div class="col-sm-offset-5 col-sm-4">
														<button type="submit" name="type" value="change" class="btn btn-default">Chỉnh sữa</button>
														<a href="/cancel-proposal" type="submit" class="btn btn-default">Hủy</a> 
														<a href="/" class="btn btn-default">Trở về</a>
													</div>
												</c:if>
											</div>
										</c:otherwise>
									</c:choose>
	
								</form:form>
							</c:when>
							<c:when test='${MODE == "MODE_CONFIRM_PROPOSAL"}'>
								<div class="title-content">Xem đề nghị</div>
								<form:form method="post" modelAttribute="Proposal"
									action="${typeForm}" class="form-horizontal"
									enctype="multipart/form-data">
									<c:if test="${not empty message}">
										<div class="form-group">
											<div style="color: red; font-size: 15px; text-align: center;">
												<c:out value="${message}" />
											</div>
										</div>
									</c:if>
									<div class="form-group">
										<label class="control-label col-sm-3">Tên</label>
										<div class="col-sm-7">
											<form:input path="name" disabled="true" type="text" class="form-control" />
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-3">Chi Tiết</label>
										<div class="col-sm-7">
											<form:textarea disabled="true" path="detail" class="form-control" cols="30" rows="10"></form:textarea>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-3">Xe</label>
										<div class="col-sm-7">
											<form:select disabled="true" id="choicecar" path="carID"
												class="btn btn-choose">
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
													<div class="row"
														id='detail-car-<c:out value="${listcar.carID}"/>'>
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
																<p>
																	<c:out value="${listcar.driver.name}" />
																	/p>
																<p>
																	<c:out value="${listcar.driver.email}" />
																</p>
																<p>
																	<c:out value="${listcar.driver.birthday}" />
																</p>
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
																<p>
																	<c:out value="${listcar.driver.phone}" />
																</p>
																<p>
																	<c:out value="${listcar.driver.experience}" />
																	năm
																</p>
																<p>
																	<c:out value="${listcar.driver.license}" />
																</p>
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
												<form:select disabled="true" path="typedateuse" class="btn btn-choose"
													id="select-time-use">
													<c:choose>
														<c:when test='${Proposal.typedateuse == "manyday" }'>
															<option value="inday">Trong Ngày</option>
															<option selected value="manyday">Nhiều Ngày</option>
														</c:when>
														<c:otherwise>
															<option selected value="inday">Trong Ngày</option>
															<option value="manyday">Nhiều Ngày</option>
														</c:otherwise>
													</c:choose>
												</form:select>
											</div>
											<div class="col-sm-4" id="in-day">
												<form:input disabled="true" path="useindate"
													class="form-control date-picker" type="text" />
											</div>
											<div class="col-sm-9 padding-right-zero" id="many-day">
												<div class="col-sm-1 padding-left-zero">
													<label class="control-label">Từ</label>
												</div>
												<div class="col-sm-5 padding-left-zero">
													<form:input disabled="true" path="usefromdate" type="text"
														class="form-control date-picker" />
												</div>
												<div class="col-sm-1 padding-left-zero">
													<label class="control-label">Đến</label>
												</div>
												<div class="col-sm-5 padding-right-zero">
													<form:input disabled="true" path="usetodate" type="text"
														class="form-control date-picker" />
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-3">Tệp đính kèm</label> <a
											href="/static/file/${Proposal.proposalID}.pdf"
											class="control-label col-sm-3" style="text-align: left;"
											download>File</a>
									</div>
									<div class="form-group">
										<div class="col-sm-offset-5 col-sm-4">
											<button type="submit" name="type" value="confirm"
												class="btn btn-default">Duyệt</button>
											<a href="/" class="btn btn-default">Trở về</a>
										</div>
									</div>
								</form:form>
							</c:when>
							<c:when test='${MODE == "MODE_FIND_CARS"}'>
								<div class="title-content">Tra cứu xe</div>
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
											<th>Chi tiết</th>
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
												<td class="show-deital-driver"><a href="#"
													data-toggle="tooltip" data-placement="top"
													title="Thông tin tài xế"> <i
														class="fa fa-info-circle fa-lg" aria-hidden="true"></i>
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
															<img src="https://goo.gl/ZVUgrD" width="70px" height="80px">
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
							<c:otherwise>
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
							</c:otherwise>
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
									<c:when test="${notifys.proposal.stt.sttproposalID == 1 && notifys.proposal.type.typeID != 3}">
										<div class="item-notify">
											<sec:authorize access="hasAuthority('CONFIRM_PROPOSAL')">
												<a href="confirm-proposal-${notifys.proposal.proposalID}">
													<div>
														<strong><c:out value="${notifys.proposal.infoconfirm.userconfirm.roleUser.name}" /></strong> đã duyệt đề
														nghị
														<strong><c:out value="${notifys.proposal.name}" /></strong>
														của <strong><c:out value="${notifys.proposal.userregister.user.name}" /></strong>
													</div>
													<p class="time-ago">
														<i class="fa fa-plus-circle" aria-hidden="true"></i> 
														<c:out value="${notifys.time}" />
													</p>
												</a>
											</sec:authorize>
											<sec:authorize access="hasAuthority('CHANGE_PROPOSAL')">
												<a href="change-proposal-${notifys.proposal.proposalID}">
													<div>
														<strong><c:out value="${notifys.proposal.infoconfirm.userconfirm.roleUser.name}" /></strong> đã duyệt đề
														nghị
														<c:out value="${notifys.proposal.name}" />
														của bạn
													</div>
													<p class="time-ago">
														<i class="fa fa-plus-circle" aria-hidden="true"></i>
														<c:out value="${notifys.time}" />
													</p>
												</a>
											</sec:authorize>
										</div>
									</c:when>
									<c:when test="${notifys.proposal.expired == true && notifys.proposal.stt.sttproposalID == 1}">
										<sec:authorize access="hasAuthority('CHANGE_PROPOSAL')">
											<div class="item-notify">
												<a href="change-proposal-${notifys.proposal.proposalID}">
													<div>
														Đề nghị 
														<strong><c:out value="${notifys.proposal.type.name}" /></strong> 
														đã bị hủy vì thời gian sử dụng đã quá hạn bởi <strong>Hệ Thống</strong>
													</div>
													<p class="time-ago">
														<i class="fa fa-trash" aria-hidden="true"></i>
														<c:out value="${notifys.time}" />
													</p>
												</a>
											</div>
										</sec:authorize>
									</c:when>
									<c:otherwise>
										<sec:authorize access="hasAuthority('CONFIRM_PROPOSAL')">
											<div class="item-notify">
												<a href="confirm-proposal-${notifys.proposal.proposalID}">
													<div>
														<strong>
															<c:out value="${notifys.proposal.userregister.user.name}"></c:out>
														</strong>
														đã
														<c:out value="${notifys.proposal.type.name}" />
														một đề nghị
													</div>
													<p class="time-ago">
														<c:choose>
															<c:when test="${notifys.proposal.type.name == 'Tạo'}">
																<i class="fa fa-plus-circle" aria-hidden="true"></i>
															</c:when>
															<c:when test="${notifys.proposal.type.name == 'Chỉnh Sửa'}">
																<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
															</c:when>
															<c:otherwise>
																<i class="fa fa-trash" aria-hidden="true"></i>
															</c:otherwise>
														</c:choose>
														<c:out value="${notifys.time}" />
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