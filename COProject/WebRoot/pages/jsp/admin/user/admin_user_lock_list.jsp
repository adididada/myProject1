<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String insertUserUrl = basePath + "pages/jsp/admin/user/UserActionAdmin!insertPre.action" ;
	String showUser = basePath + "pages/jsp/admin/user/UserActionAdmin!show.action" ;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>协同办公管理系统</title>
<meta name="viewport" content="width=device-width,initial-scale=1">
<jsp:include page="/pages/include_javascript.jsp" />
<script type="text/javascript" src="js/admin/admin_user_lock_list.js"></script>
</head>
<body class="back">
	<div class="container contentback">
		<div id="headDiv" class="row">
			<div class="col-md-12 col-xs-12">
				<jsp:include page="/pages/include_menu_admin.jsp" />
			</div>
		</div>
		<div id="contentDiv" class="row">
			<div class="col-md-12 col-xs-12">
				<div class="panel panel-success">
					<div class="panel-heading">
						<strong>活跃用户列表</strong>
					</div>
					<div class="panel-body">
						<div id="splitSearchDiv">
							<jsp:include page="/pages/split_page_plugin_search.jsp"/>
							<br>
						</div>
						<table class="table table-condensed">
							<thead>
								<tr>
									<th class="text-center">
										<input type="checkbox" id="selectAll" onclick="checkboxSelectAll('user.userid',this.checked)">
									</th>
									<th class="text-center"><strong>用户ID</strong></th>
									<th class="text-center"><strong>姓名</strong></th>
									<th class="text-center"><strong>电话</strong></th>
									<th class="text-center"><strong>邮箱</strong></th>
									<th class="text-center"><strong>级别</strong></th>
									<th class="text-center"><strong>状态</strong></th>
									<th class="text-center"><strong>操作</strong></th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${allUsers != null}">
									<c:forEach items="${allUsers}" var="user">
										<tr>
											<td class="text-center">
												<input type="checkbox" id="user.userid" name="user.userid" value="${user.userid}">
											</td>
											<td class="text-center"><span id="userid-${user.userid}">${user.userid}</span></td>
											<td class="text-center"><a href="<%=showUser%>?user.userid=${user.userid}"><span id="name-${user.userid}">${user.name}</span></a></td>
											<td class="text-center"><span id="phone-${user.userid}">${user.phone}</span></td>
											<td class="text-center"><span id="email-${user.userid}">${user.email}</span></td>
											<td class="text-center">
												<select id="role.rid-${user.userid}" name="role.rid-${user.userid}" class="form-control">
													<option value="4" ${user.role.rid == 4 ?"selected" : ""}>项目经理</option>
													<option value="5" ${user.role.rid == 5 ?"selected" : ""}>普通雇员</option>
												</select>
											</td>
											<td class="text-center">${user.lockflag==0?"激活":"锁定"}</td>
											<td class="text-center">
												<div class="btn-group">
													<button type="button" class="btn btn-md btn-info dropdown-toggle" data-toggle="dropdown">
														<span class="glyphicon glyphicon-list"></span>&nbsp;编辑
													</button>
													<ul class="dropdown-menu" role="menu">
														<li><a id="passwordBtn-${user.userid}" onmouseover="this.style.cursor='hand'">
															<span class="glyphicon glyphicon-edit"></span>&nbsp;变更密码</a></li>
														<li class="divider"></li>
														<li><a id="userBtn-${user.userid}" onmouseover="this.style.cursor='hand'">
															<span class="glyphicon glyphicon-pencil"></span>&nbsp;用户信息</a>
														</li>
													</ul>
												</div>
												<button type="button" class="btn btn-success" id="updateBtn-${user.userid}">修改角色</button>
											</td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
						<div>
							<button class="btn btn-danger btn-lg" id="activeBtn">激活用户</button>
							<a href="<%=insertUserUrl%>" class="btn btn-info btn-lg">增加用户</a>
						</div>
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
		<div class="modal fade" id="userPassword"  tabindex="-1" aria-labelledby="modalTitle" aria-hidden="true" data-keyboard="true">
			<div class="modal-dialog" style="width: 1000px">
				<div class="modal-content">
					<div class="modal-header">
						<button class="close" type="button" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h3 class="modal-title">
							<span class="glyphicon glyphicon-cog"></span>
							<strong>修改“<span id="upTitle"></span>”用户密码</strong></h3>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" id="myform" method="post">
							<fieldset>
								<div class="form-group" id="passwordDiv">
									<!-- 定义表单提示文字 -->
									<label class="col-md-3 control-label" for="user.password">登录密码：</label>
									<div class="col-md-5">
										<!-- 定义表单输入组件 -->
										<input type="password" id="password" name="password"
											class="form-control" placeholder="请输入新的登录密码">
									</div>
									<!-- 定义表单错误提示显示元素 -->
									<div class="col-md-4" id="passwordMsg"></div>
								</div>
								<div class="form-group">
									<div class="col-md-5 col-md-offset-3">
										<input type="hidden" name="password.userid" id="password.userid">
										<button type="submit" class="btn btn-primary">修改密码</button>
									</div>
								</div>
							</fieldset>
						</form>
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
							<img src="upload/user/nophoto.jpg" id="photo" style="width:128px;height:128px;">
							<strong>修改“<span id="useridTitle"></span>”用户信息</strong></h3>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" id="userForm" action="" method="post">
							<div class="form-group" id="useridDiv">
								<!-- 定义表单提示文字 -->
								<label class="col-md-3 control-label" for="userid">登录名称：</label>
								<div class="col-md-5">
									<!-- 定义表单输入组件 -->
									<input type="text" id="userid" name="userid" class="form-control" placeholder="请输入登录用户名" value="MLDN" readonly="true">
								</div>
								<!-- 定义表单错误提示显示元素 -->
								<div class="col-md-4" id="useridMsg"></div>
							</div>
							<!-- 定义输入表单样式，其中id主要用于设置颜色样式 -->
							<div class="form-group" id="nameDiv">
								<!-- 定义表单提示文字 -->
								<label class="col-md-3 control-label" for="name">真实姓名：</label>
								<div class="col-md-5">
									<!-- 定义表单输入组件 -->
									<input type="text" id="name" name="name" class="form-control" placeholder="请输入用户真实姓名" value="魔乐科技">
								</div>
								<!-- 定义表单错误提示显示元素 --> 
								<div class="col-md-4" id="nameMsg"></div>
							</div>
							<!-- 定义输入表单样式，其中id主要用于设置颜色样式 -->
							<div class="form-group" id="phoneDiv">
								<!-- 定义表单提示文字 -->
								<label class="col-md-3 control-label" for="phone">联系电话：</label>
								<div class="col-md-5">
									<!-- 定义表单输入组件 -->
									<input type="text" id="phone" name="phone" class="form-control" placeholder="请输入联系电话" value="51283346">
								</div>
								<!-- 定义表单错误提示显示元素 -->
								<div class="col-md-4" id="phoneMsg"></div>
							</div>
							<!-- 定义输入表单样式，其中id主要用于设置颜色样式 -->
							<div class="form-group" id="emailDiv">
								<!-- 定义表单提示文字 -->
								<label class="col-md-3 control-label" for="email">联系邮箱：</label>
								<div class="col-md-5">
									<!-- 定义表单输入组件 -->
									<input type="text" id="email" name="email" class="form-control" placeholder="请输入联系邮箱" value="mldnqa@163.com">
								</div>
								<!-- 定义表单错误提示显示元素 -->
								<div class="col-md-4" id="emailMsg"></div>
							</div>
							<div class="form-group" id="levelDiv">
								<!-- 定义表单提示文字 -->
								<label class="col-md-3 control-label" for="level">用户级别：</label>
								<div class="col-md-5">
									<select id="level" name="level" class="form-control">
										<option value="3">普通雇员</option>
										<option value="2">项目经理</option>
									</select>
								</div>
								<!-- 定义表单错误提示显示元素 -->
								<div class="col-md-4" id="levelMsg"></div>
							</div>
							<div class="form-group">
								<div class="col-md-5 col-md-offset-3">
									<button type="submit" class="btn btn-primary" id="editBtn">修改</button>
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
</body>
</html>
