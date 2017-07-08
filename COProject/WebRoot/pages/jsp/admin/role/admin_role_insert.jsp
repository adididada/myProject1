<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String insertUrl = basePath + "pages/jsp/admin/role/RoleActionAdmin!insert.action" ;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>协同办公管理系统</title>
<meta name="viewport" content="width=device-width,initial-scale=1"> 
<jsp:include page="/pages/include_javascript.jsp" /> 
<script type="text/javascript" src="js/admin/admin_role_insert.js"></script>
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
						<strong>增加新角色</strong>
					</div>
					<div class="panel-body">
						<form class="form-horizontal" action="<%=insertUrl%>" id="myform" method="post">
							<fieldset>
								<!-- 定义表单提示框 -->
								<legend>
									<label><span class="glyphicon glyphicon-file"></span>&nbsp;增加角色</label>
								</legend>
								<!-- 定义输入表单样式，其中id主要用于设置颜色样式 -->
								<div class="form-group" id="role.titleDiv">
									<!-- 定义表单提示文字 -->
									<label class="col-md-3 control-label" for="role.title">角色名称：</label>
									<div class="col-md-5">
										<!-- 定义表单输入组件 -->
										<input type="text" id="role.title" name="role.title" class="form-control"
											placeholder="请输入角色名称">
									</div>
									<!-- 定义表单错误提示显示元素 -->
									<div class="col-md-4" id="role.titleMsg"></div>
								</div>
								<!-- 定义输入表单样式，其中id主要用于设置颜色样式 -->
								<div class="form-group" id="role.noteDiv">
									<!-- 定义表单提示文字 -->
									<label class="col-md-3 control-label" for="role.note">角色描述：</label>
									<div class="col-md-5"> 
										<!-- 定义表单输入组件 -->
										<input type="text" id="role.note" name="role.note"
											class="form-control" placeholder="请输入角色描述信息">
									</div>
									<!-- 定义表单错误提示显示元素 -->
									<div class="col-md-4" id="role.noteMsg"></div>
								</div> 
								<div class="form-group" id="gidsDiv">
									<!-- 定义表单提示文字 -->
									<label class="col-md-3 control-label" for="gids">包含权限：</label>
									<div class="col-md-5">
										<c:if test="${allGroups != null}">
											<c:forEach items="${allGroups}" var="gup">
												<div class="checkbox col-md-4">  
													<label><input type="checkbox" id="gids" name="gids" value="${gup.gid}">${gup.title}</label>
												</div>
											</c:forEach>
										</c:if>
									</div>
									<!-- 定义表单错误提示显示元素 -->
									<div class="col-md-4" id="gidsMsg"></div>
								</div> 
								<div class="form-group">
									<div class="col-md-5 col-md-offset-3">
										<button type="submit" class="btn btn-primary">增加</button>
										<button type="reset" class="btn btn-warning">重置</button>
									</div>
								</div>
							</fieldset>
						</form>
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
