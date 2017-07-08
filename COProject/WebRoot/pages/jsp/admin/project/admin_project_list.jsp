<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String showUrl = basePath + "pages/jsp/admin/admin/AdminActionAdmin!show.action" ;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>协同办公管理系统</title>
<meta name="viewport" content="width=device-width,initial-scale=1">
<jsp:include page="/pages/include_javascript.jsp" />
<script type="text/javascript" src="js/admin/admin_project_list.js"></script>
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
											<td class="text-center">${project.title}</td> 
											<td class="text-center"><a href="<%=showUrl%>?user.userid=${project.userByCreid.userid}">${project.userByCreid.userid}</a></td>
											<td class="text-center"><a href="<%=showUrl%>?user.userid=${project.userByMgr.userid}">${project.name}</a></td>
											<td class="text-center">${project.pubdate}</td>
											<td class="text-center">
												<a type="button" class="btn btn-primary" href="pages/jsp/admin/project/ProjectActionAdmin!updatePre.action?project.proid=1">编辑项目</a>
												<a type="button" class="btn btn-warning" href="pages/jsp/admin/task/TaskActionAdmin!list.action?project.proid=${project.proid}">项目任务</a>
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
</body>
</html>
