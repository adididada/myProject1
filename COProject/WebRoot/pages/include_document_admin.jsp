<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/c" %>
<%
	String deleteUrl = request.getParameter("deleteUrl")  ;
	String updatePreUrl  = request.getParameter("updatePreUrl") ; 
	String showUrl  = request.getParameter("showUrl") ; 
%>
<script type="text/javascript" src="js/common/common_document_admin.js"></script>
<script type="text/javascript">
	var pageVarDeleteUrl = "${param.deleteUrl}" ;
</script>
<div id="splitBarDiv">
	<jsp:include page="/pages/split_page_plugin_search.jsp"/>
</div>
<table class="table table-condensed">
	<thead>
		<tr>
			<th class="text-center">
				<input type="checkbox" id="selectAll" onclick="checkboxSelectAll('document.did',this.checked)">
			</th>
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
					<td class="text-center">
						<input type="checkbox" id="document.did" name="document.did" value="${doc.did}:${doc.file}">
					</td>
					<td class="text-center">${doc.title}</td>
					<td class="text-center">
						<c:if test="${admin != null}">
							<a class="btn btn-info" href="<%=showUrl%>?user.userid=${doc.user.userid}">${doc.user.userid}</a>
						</c:if>
						<c:if test="${admin == null}">
							${doc.user.userid}
						</c:if>
					</td>
					<td class="text-center">${doc.pubdate}</td>
					<td class="text-center">${allDoctypes[doc.doctype.dtid]}</td>
					<td class="text-center">
						<a class="btn btn-warning" href="<%=updatePreUrl%>?document.did=${doc.did}">编辑文档</a>
						<c:if test="${doc.file != null}">
							<a class="btn btn-info" href="upload/document/${doc.file}">下载附件</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>
<div>
	<button class="btn btn-danger btn-lg" id="deleteBtn">删除文档</button>
</div>
<div id="splitBarDiv" style="float:right">
	<jsp:include page="/pages/split_page_plugin_bars.jsp"/>
</div>