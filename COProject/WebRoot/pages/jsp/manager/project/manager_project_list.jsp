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
<script type="text/javascript" src="js/manager/manager_project_list.js"></script>
</head>
<body class="manback">
	<div class="container contentback">
		<div id="headDiv" class="row">
			<div class="col-md-12 col-xs-12">
				<jsp:include page="/pages/include_menu_manager.jsp" />
			</div>
		</div>
		<div id="contentDiv" class="row">
			<div class="col-md-12 col-xs-12">
				<div class="panel panel-success">
					<div class="panel-heading">
						<strong>项目信息列表</strong>
					</div>
					<div class="panel-body">
						<div id="splitSearchDiv">
							<jsp:include page="/pages/split_page_plugin_search.jsp"/>
							<br>
						</div>
						<table class="table table-condensed">
							<thead>
								<tr>
									<th class="text-center"><strong>项目名称</strong></th>
									<th class="text-center"><strong>发布者</strong></th>
									<th class="text-center"><strong>项目负责人</strong></th>
									<th class="text-center"><strong>发布日期</strong></th>
									<th class="text-center"><strong>操作</strong></th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${allProjects != null}">
									<c:forEach items="${allProjects}" var="project">
										<tr>
											<td class="text-center"><a id="showProjectBtn-${project.proid}">${project.title}</a></td>
											<td class="text-center"><a id="showUserBtn-${project.userByCreid.userid}">${project.userByCreid.userid}</a></td>
											<td class="text-center"><a id="showUserBtn-${project.userByMgr.userid}">${project.name}</a></td>
											<td class="text-center">${project.pubdate}</td>
											<td class="text-center">
												<c:if test="${manager.userid==project.userByMgr.userid}">
													<a type="button" class="btn btn-primary" href="pages/jsp/manager/task/TaskActionManager!insertPre.action?proid=${project.proid}">创建任务</a>
												</c:if>
												<a type="button" class="btn btn-warning" href="pages/jsp/manager/task/TaskActionManager!list.action?project.proid=${project.proid}">任务列表</a>
											</td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
						<div id="splitBarDiv" style="float:right">
							<jsp:include page="/pages/split_page_plugin_bars.jsp"/>
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
<div class="modal fade" id="projectInfo"  tabindex="-1" aria-labelledby="modalTitle" aria-hidden="true" data-keyboard="true">
	<div class="modal-dialog" style="width: 1000px">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h3 class="modal-title">
					<span class="glyphicon glyphicon-eye-open"></span>
					<strong>查看“<span id="titleHeadSpan"></span>”项目</strong></h3>
			</div>
			<div class="modal-body">
				<table class="table table-bordered table-hover table-condensed table-responsive">
					<tr>
						<td><strong>项目名称：</strong></td>
						<td><span id="titleSpan"></span></td>
					</tr>
					<tr>
						<td><strong>项目发布者：</strong></td>
						<td id="creidSpan"></td>
					</tr>
					<tr>
						<td><strong>项目经理：</strong></td>
						<td id="mgrSpan"></td>
					</tr>
					<tr>
						<td><strong>发布日期：</strong></td>
						<td><span id="pubdateSpan"></span></td>
					</tr>
					<tr>
						<td><strong>项目简介：</strong></td>
						<td><pre class="pre-scrollable" style="width:750px;text-align: left;background: white;"><span id="noteSpan"></span></pre></td>
					</tr>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭窗口</button>
			</div>
		</div>
	</div>
</div>

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
</body>
</html>
