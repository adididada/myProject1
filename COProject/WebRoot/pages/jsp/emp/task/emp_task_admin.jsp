<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/c" %>
 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>协同办公管理系统</title>
<meta name="viewport" content="width=device-width,initial-scale=1">
<jsp:include page="/pages/include_javascript.jsp" />
<script type="text/javascript" src="js/emp/emp_task_admin.js"></script>
</head>
<body class="userback">
	<div class="container contentback">
		<div id="headDiv" class="row">
			<div class="col-md-12 col-xs-12">
				<jsp:include page="/pages/include_menu_emp.jsp" />
			</div>
		</div>
		<div id="contentDiv" class="row">
			<div class="col-md-12 col-xs-12">
				<div class="panel panel-success">
					<div class="panel-heading">
						<strong>项目任务列表</strong>
					</div>
					<div class="panel-body">
						<div id="splitSearchDiv">
							<jsp:include page="/pages/split_page_plugin_search.jsp"/>
							<br>
						</div>
						<table class="table table-condensed">
							<thead>
								<tr>
									<th class="text-center"><strong>任务名称</strong></th>
									<th class="text-center"><strong>创建者</strong></th>
									<th class="text-center"><strong>实施者</strong></th>
									<th class="text-center"><strong>创建日期</strong></th>
									<th class="text-center"><strong>截至日期</strong></th>
									<th class="text-center"><strong>优先级</strong></th>
									<th class="text-center"><strong>预计工时</strong></th>
									<th class="text-center"><strong>状态</strong></th>
									<th class="text-center"><strong>操作</strong></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${allTasks}" var="task">
									<tr>
										<td class="text-center">${task.title}</td>
										<td class="text-center"><a class="btn btn-info" id="showUserBtn-${task.userByCreator.userid}">${task.userByCreator.userid}</a></td>
										<td class="text-center"><a class="btn btn-info" id="showUserBtn-${task.userByReceiver.userid}">${task.userByReceiver.userid}</a></td>
										<td class="text-center">${task.createdate}</td>
										<td class="text-center">${task.expiredate}</td>
										<td class="text-center">
											<c:if test="${task.priority == 0}">
												★★★
											</c:if>
											<c:if test="${task.priority == 1}">
												★★☆
											</c:if>
											<c:if test="${task.priority == 2}">
												★☆☆
											</c:if>
										</td>
										<td class="text-center">${task.estimate}</td>
										<td class="text-center">
											<c:if test="${task.status == 0}">
												<span id="statusSpan-${task.tid}">未开始</span>
											</c:if>
											<c:if test="${task.status == 1}">
												<span id="statusSpan-${task.tid}" class="text-warning">进行中</span>
											</c:if>
											<c:if test="${task.status == 2}">
												<span id="statusSpan-${task.tid}" class="text-danger">取消</span>
											</c:if>
											<c:if test="${task.status == 3}">
												<span id="statusSpan-${task.tid}" class="text-success">已完成</span>
											</c:if>
										</td>
										<td class="text-left">
											<button type="button" class="btn btn-info" id="showBtn-${task.tid}"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;查看详情</button>
											<c:if test="${emp.userid == task.userByReceiver.userid}">
												<c:if test="${task.status == 1}">
													<span id="btn-${task.tid}"><button type="button" class="btn btn-warning" id="finishBtn-${task.tid}"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;任务完成</button></span>
												</c:if>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div id="splitBarDiv" style="float:right">
							<jsp:include page="/pages/split_page_plugin_bars.jsp"/>
						</div>
						<jsp:include page="/pages/include_task_view.jsp"/>
						<div class="modal fade" id="userInfo"  tabindex="-1" aria-labelledby="modalTitle" aria-hidden="true" data-keyboard="true">
							<div class="modal-dialog" style="width: 1000px">
								<div class="modal-content">
									<div class="modal-header">
										<button class="close" type="button" data-dismiss="modal" aria-hidden="true">&times;</button>
										<h3 class="modal-title">
											<span class="glyphicon glyphicon-eye-open"></span>
											<strong>查看“<span id="useridHeadSpan2"></span>”用户信息</strong></h3>
									</div>
									<div class="modal-body">
										<div class="panel-body">
											<table class="table table-bordered table-hover">
												<tr>
													<td rowspan="10" style="width:130px;">
														<img src="upload/user/nophoto.jpg" id="userPhoto" class="image" style="height:128px;width:128px;">
													</td>
												</tr>
												<tr>
													<td style="width:15%"><strong>用户名：</strong></td>
													<td id="userUserid"></td>
												</tr>
												<tr>
													<td><strong>真实姓名：</strong></td>
													<td id="userName"></td>
												</tr>
												<tr>
													<td><strong>联系电话：</strong></td>
													<td id="userPhone"></td>
												</tr>
												<tr>
													<td><strong>联系邮箱：</strong></td>
													<td id="userEmail"></td>
												</tr>
												<tr>
													<td><strong>用户状态：</strong></td>
													<td id="userLockflag"></td>
												</tr>
												<tr>
													<td><strong>上次登录日期：</strong></td>
													<td id="userLastlogin"></td>
												</tr>
												<tr>
													<td style="width:240px;"><strong>用户级别：</strong></td>
													<td id="userLevel"></td>
												</tr>
											</table>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">关闭窗口</button>
									</div>
								</div>
							</div>
						</div>
					</div>
						<div class="modal fade" id="taskFinishInfo"  tabindex="-1" aria-labelledby="modalTitle" aria-hidden="true" data-keyboard="true"> 
							<div class="modal-dialog" style="width: 1000px">
								<div class="modal-content">
									<div class="modal-header">
										<button class="close" type="button" data-dismiss="modal" aria-hidden="true">&times;</button>
										<h3 class="modal-title">
											<span class="glyphicon glyphicon-ok-sign"></span>
											<span class="text-success"><strong>任务完成回馈</strong></span></h3>
									</div>
									<div class="modal-body">
										<form class="form-horizontal" id="myform" action="" method="post">
											<div class="form-group" id="expendDiv">
												<!-- 定义表单提示文字 -->
												<label class="col-md-3 control-label" for="expend">消耗工时（小时）：</label>
												<div class="col-md-5">
													<!-- 定义表单输入组件 -->
													<input type="text" id="expend" name="expend" class="form-control"
														placeholder="请输入任务完成所消耗的工时">
												</div>
												<!-- 定义表单错误提示显示元素 --> 
												<div class="col-md-4" id="expendMsg"></div>
											</div>
											<!-- 定义输入表单样式，其中id主要用于设置颜色样式 -->
											<div class="form-group" id="rnoteDiv">
												<!-- 定义表单提示文字 -->
												<label class="col-md-3 control-label" for="rnote">任务完成：</label>
												<div class="col-md-5">
													<textarea rows="10" id="rnote" name="rnote"
														class="form-control" placeholder="请输入任务完成过程中出现的问题以及个人总结"></textarea>
												</div>
												<!-- 定义表单错误提示显示元素 -->
												<div class="col-md-4" id="rnoteMsg"></div>
											</div>
											<div class="form-group">
												<div class="col-md-5 col-md-offset-3">
													<input type="hidden" name="tid" id="tid" value="">
													<button type="submit" class="btn btn-primary" id="editBtn">提交完成总结</button>
													<button type="reset" class="btn btn-warning">重置</button>
												</div>
											</div>
										</form>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">关闭窗口</button>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="panel-footer">
						<div class="alert alert-success" id="alertDiv" style="display: none;">
	                        <button type="button" class="close" data-dismiss="alert">&times;</button>
	                        <span id="alertText"></span>
	                    </div>
					</div>
				</div>
			</div>
		</div>
		<div id="footDiv" class="row navbar-fixed-bottom">
			<jsp:include page="/pages/include_foot.jsp" />
		</div>
	</div>
</body>
</html>
