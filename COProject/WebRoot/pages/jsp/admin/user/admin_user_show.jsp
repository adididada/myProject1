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
</head>
<body class="back">
	<div class="container contentback" >
		<div id="headDiv" class="row">
			<div class="col-md-12 col-xs-12">
				<jsp:include page="/pages/include_menu_admin.jsp" />
			</div>
		</div>
		<div id="contentDiv" class="row">
			<div class="col-md-12 col-xs-12">
				<div class="panel panel-success">
					<div class="panel-heading">
						<strong>查看管理员的详细信息</strong>
					</div>
					<div class="panel-body">
						<table class="table table-bordered table-hover">
							<tr>
								<td colspan="3"><span class="h1"><span class="glyphicon glyphicon-user"></span>&nbsp;查看“${user.userid}”用户信息</span></td>
							</tr>
							<tr>
								<td rowspan="10" style="width:130px;">
									<img src="upload/user/${user.photo}" class="image" style="height:128px;width:128px;">
								</td>
							</tr>
							<tr>
								<td style="width:15%"><strong>用户名：</strong></td>
								<td>${user.userid}</td>
							</tr>
							<tr>
								<td><strong>真实姓名：</strong></td>
								<td>${user.name}</td>
							</tr>
							<tr>
								<td><strong>联系电话：</strong></td>
								<td>${user.phone}</td>
							</tr>
							<tr>
								<td><strong>联系邮箱：</strong></td>
								<td>${user.email}</td>
							</tr>
							<tr>
								<td><strong>用户状态：</strong></td>
								<td>${user.lockflag == 0 ? "激活" : "锁定"}</td>
							</tr>
							<tr>
								<td><strong>上次登录日期：</strong></td>
								<td>${user.lastlogin}</td>
							</tr>
							<tr>
								<td style="width:240px;"><strong>用户级别：</strong></td>
								<td>
									<c:if test="${user.level == 0}">
										超级管理员
									</c:if>
									<c:if test="${user.level == 1}">
										管理员
									</c:if>
									<c:if test="${user.level == 2}">
										项目经理
									</c:if>
									<c:if test="${user.level == 3}">
										员工
									</c:if>
								</td>
							</tr>
							<tr>
								<td><strong>所具备角色：</strong></td>
								<td>${user.role.title}</td>
							</tr>
							<tr>
								<td><strong>包含权限组：</strong></td>
								<td>
									<table class="table table-bordered table-hover">
										<c:forEach items="${user.role.groupses}" var="gup">
										<tr>
											<td style="width:20%"><strong>${gup.title}</strong></td>
											<td>
												<c:forEach items="${gup.actions }" var="act">
													${act.title}、
												</c:forEach>
											</td>
										</tr>
										</c:forEach>
									</table>
								</td>
							</tr>
						</table>
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
