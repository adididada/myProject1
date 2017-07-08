<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
			<c:if test="${user.level == 2}">
				项目经理
			</c:if>
			<c:if test="${user.level == 3}">
				雇员
			</c:if>
		</td>
	</tr>
</table>
