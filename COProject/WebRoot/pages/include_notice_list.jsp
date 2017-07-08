<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/c" %>
<script type="text/javascript" src="js/common/common_notice_list.js"></script>
<div id="splitSearchDiv">
	<jsp:include page="/pages/split_page_plugin_search.jsp"/>
	<br>
</div>
<table class="table table-condensed">
	<thead>
		<tr>
			<th class="text-center"><strong>标题</strong></th>
			<th class="text-center"><strong>发布者</strong></th>
			<th class="text-center"><strong>发布日期</strong></th>
			<th class="text-center"><strong>阅读量</strong></th>
			<th class="text-center"><strong>阅读级别</strong></th>
			<th class="text-center"><strong>操作</strong></th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${allNotices != null}">
			<c:forEach items="${allNotices}" var="notice">
				<tr>
					<td class="text-center">
						<c:if test="${unread[notice.snid]}">
							<span class="glyphicon glyphicon-bookmark" id="flag-${notice.snid}"></span>&nbsp;
						</c:if>
					${notice.title}</td>
					<td class="text-center"><a class="btn btn-info" id="showUserBtn-${notice.user.userid}">${notice.user.userid}</a></td>
					<td class="text-center">${notice.pubdate }</td>
					<td class="text-center">${notice.rnum}</td>
					<td class="text-center">${notice.level == 2 ? "项目经理" : "员工"}</td>
					<td class="text-center">
						<button type="button" class="btn btn-success" id="showBtn-${notice.snid}">查看公告</button>
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>
<div id="splitBarDiv" style="float:right">
	<jsp:include page="/pages/split_page_plugin_bars.jsp"/>
</div>
<div class="modal fade" id="noticeInfo"  tabindex="-1" aria-labelledby="modalTitle" aria-hidden="true" data-keyboard="true">
	<div class="modal-dialog" style="width: 1000px">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h3 class="modal-title">
					<span class="glyphicon glyphicon-eye-open"></span>
					<strong>查看“<span id="titleHeadSpan"></span>”公告</strong></h3>
			</div>
			<div class="modal-body">
				<table class="table table-bordered table-hover table-condensed table-responsive">
					<tr>
						<td><strong>公告名称：</strong></td>
						<td><span id="titleSpan">协同办公系统发布</span></td>
					</tr>
					<tr>
						<td><strong>公告发布者：</strong></td>
						<td id="useridSpan"></td>
					</tr>
					<tr>
						<td><strong>发布日期：</strong></td>
						<td><span id="pubdateSpan">2017-10-10</span></td>
					</tr>
					<tr>
						<td><strong>阅读级别：</strong></td>
						<td><span id="levelSpan">项目经理</span></td>
					</tr>
					<tr>
						<td><strong>公告阅读量：</strong></td>
						<td><span id="rnumSpan">500</span></td>
					</tr>
					<tr>
						<td><strong>公告内容：</strong></td>
						<td><pre class="pre-scrollable" style="width:750px;text-align: left;background: white;"><span id="contentSpan">公告描述内容</span></pre></td>
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