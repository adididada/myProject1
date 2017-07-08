<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/c" %>
<%
	String showUrl  = request.getParameter("showUrl") ; 
%>
<script type="text/javascript" src="js/common/common_document_list.js"></script>
<div id="splitBarDiv">
	<jsp:include page="/pages/split_page_plugin_search.jsp"/>
</div>
<table class="table table-condensed">
	<thead>
		<tr>
			<th class="text-center"><strong>标题</strong></th>
			<th class="text-center"><strong>发布者</strong></th>
			<th class="text-center"><strong>发布日期</strong></th>
			<th class="text-center"><strong>文档类型</strong></th>
			<th class="text-center"><strong>操作</strong></th>
		</tr>
	</thead>  
	<tbody>
		<c:if test="${allDocuments != null}">
			<c:forEach items="${allDocuments}" var="doc">
				<tr>
					<td class="text-center">${doc.title}</td>
					<td class="text-center"><a class="btn btn-info" id="showUserBtn-${doc.user.userid}">${doc.user.userid}</a></td>
					<td class="text-center">${doc.pubdate}</td>
					<td class="text-center">${allDoctypes[doc.doctype.dtid]}</td> 
					<td class="text-center">
						<button type="button" class="btn btn-success" id="showBtn-${doc.did}">查看文档</button>
						<c:if test="${doc.file != null}">
							<a class="btn btn-info" href="upload/document/${doc.file}">下载附件</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>
<div id="splitBarDiv" style="float:right">
	<jsp:include page="/pages/split_page_plugin_bars.jsp"/>
</div>
<div class="modal fade" id="documentInfo"  tabindex="-1" aria-labelledby="modalTitle" aria-hidden="true" data-keyboard="true">
	<div class="modal-dialog" style="width: 1000px">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h3 class="modal-title">
					<span class="glyphicon glyphicon-eye-open"></span>
					<strong>查看“<span id="titleHeadSpan"></span>”文档</strong></h3>
			</div>
			<div class="modal-body">
				<table class="table table-bordered table-hover table-condensed table-responsive">
					<tr>
						<td><strong>文档名称：</strong></td>
						<td><span id="titleSpan"></span></td>
					</tr>
					<tr>
						<td><strong>文档类型：</strong></td>
						<td><span id="doctypeSpan"></span></td>
					</tr>
					<tr>
						<td><strong>文档发布者：</strong></td>
						<td><span id="useridSpan"></span></td>
					</tr>
					<tr>
						<td><strong>发布日期：</strong></td>
						<td><span id="pubdateSpan">2017-10-10</span></td>
					</tr>
					<tr>
						<td><strong>公告内容：</strong></td>
						<td><pre class="pre-scrollable" style="width:750px;text-align: left;background: white;"><span id="contentSpan">公告描述内容</span></pre></td>
					</tr>
					<tr>
						<td><strong>文档附件：</strong></td>
						<td id="fileSpan"></td>
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