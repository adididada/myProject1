<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/c" %>
<script type="text/javascript" src="js/common/common_document_update_form.js"></script>
<% 
	String formUrl = request.getParameter("formUrl");
%>
<form class="form-horizontal" action="<%=formUrl%>" id="myform" method="post" enctype="multipart/form-data">
	<fieldset>
		<!-- 定义表单提示框 -->
		<legend>
			<label><span class="glyphicon glyphicon-pencil"></span>&nbsp;编辑文档信息</label>
		</legend>
		<!-- 定义输入表单样式，其中id主要用于设置颜色样式 -->
		<div class="form-group" id="document.titleDiv">
			<!-- 定义表单提示文字 -->
			<label class="col-md-3 control-label" for="document.title">文档标题：</label>
			<div class="col-md-5">
				<!-- 定义表单输入组件 -->
				<input type="text" id="document.title" name="document.title" class="form-control"
					placeholder="请输入文档标题" value="${document.title}">
			</div>
			<!-- 定义表单错误提示显示元素 -->
			<div class="col-md-4" id="document.titleMsg"></div>
		</div>
		<div class="form-group" id="doctype.dtidDiv">
			<!-- 定义表单提示文字 -->
			<label class="col-md-3 control-label" for="doctype.dtid">文档类型：</label>
			<div class="col-md-5">
				<select id="doctype.dtid" name="doctype.dtid" class="form-control">
					<c:forEach items="${allDoctypes}" var="dtype">
						<option value="${dtype.dtid}" ${dtype.dtid==document.doctype.dtid?"selected":""}>${dtype.title}</option>
					</c:forEach>
				</select>
			</div>
			<!-- 定义表单错误提示显示元素 -->
			<div class="col-md-4" id="doctype.dtidMsg"></div>
		</div>
		<!-- 定义输入表单样式，其中id主要用于设置颜色样式 -->
		<div class="form-group" id="document.contentDiv">
			<!-- 定义表单提示文字 -->
			<label class="col-md-3 control-label" for="document.content">文档简介：</label>
			<div class="col-md-5">
				<!-- 定义表单输入组件 -->
				<textarea id="document.content" name="document.content" class="form-control" placeholder="请输入文档的描述信息" rows="10">${document.content}</textarea>
			</div>
			<!-- 定义表单错误提示显示元素 -->
			<div class="col-md-4" id="document.noteMsg"></div>
		</div>
		<div class="form-group" id="fileDiv">
			<!-- 定义表单提示文字 -->
			<label class="col-md-3 control-label" for="file">文档附件：</label>
			<div class="col-md-5">
				<!-- 定义表单输入组件 -->
				<input type="file" id="file" name="file"
					class="form-control" placeholder="请选择要替换的文档附件信息">
			</div>
			<!-- 定义表单错误提示显示元素 -->
			<div class="col-md-4" id="fileMsg"></div>
		</div>
		<div class="form-group" id="downloadDiv">
			<div class="col-md-5 col-md-push-3">
				<c:if test="${document.file != null}">
					<a class="btn btn-info" href="upload/document/${document.file}">${document.file}</a>
				</c:if> 
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-5 col-md-offset-3">
				<input type="hidden" id="document.did" name="document.did" value="${document.did}">
				<input type="hidden" id="oldfilename" name="oldfilename" value="${document.file}">
				<button type="submit" class="btn btn-primary">修改</button>
				<button type="reset" class="btn btn-warning">重置</button>
			</div>
		</div>
	</fieldset>
</form>
