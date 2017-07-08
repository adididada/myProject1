<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/c" %>
<script type="text/javascript" src="js/common/common_task_view.js"></script>
<div class="modal fade" id="taskInfo"  tabindex="-1" aria-labelledby="modalTitle" aria-hidden="true" data-keyboard="true"> 
	<div class="modal-dialog" style="width: 1000px">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h3 class="modal-title">
					<span class="glyphicon glyphicon-eye-open"></span>
					<strong>查看《<span id="titleHeadSpan2"></span>》任务信息</strong></h3>
			</div> 
			<div class="modal-body">
				<table class="table table-bordered table-hover table-condensed table-responsive">
					<tr>
						<td><strong>所属项目：</strong></td>
						<td><span id="projectSpan"></span></td>
					</tr>
					<tr>
						<td><strong>任务名称：</strong></td>
						<td><span id="titleSpan"></span></td>
					</tr>
					<tr>
						<td><strong>任务类型：</strong></td>
						<td><span id="typeSpan"></span></td>
					</tr>
					<tr>
						<td><strong>任务内容：</strong></td>
						<td><pre class="pre-scrollable" style="width:750px;text-align: left;background: white;"><span id="noteSpan"></span></pre></td>
					</tr>
					<tr>
						<td><strong>任务创建者：</strong></td>
						<td><span id="creatorSpan"></span></td>
					</tr>
					<tr>
						<td><strong>任务执行者：</strong></td>
						<td><span id="receiverSpan"></span></td>
					</tr>
					<tr>
						<td><strong>任务总结：</strong></td>
						<td><pre class="pre-scrollable" style="width:750px;text-align: left;background: white;"><span id="rnoteSpan"></span></pre></td>
					</tr>
					<tr>
						<td><strong>任务取消者：</strong></td>
						<td><span id="cancelerSpan"></span></td>
					</tr>
					<tr>
						<td><strong>取消原因：</strong></td>
						<td><pre class="pre-scrollable" style="width:750px;text-align: left;background: white;"><span id="cnoteSpan"></span></pre></td>
						</tr>
						<tr>
							<td><strong>优先级：</strong></td>
							<td><span id="prioritySpan"></span></td>
						</tr>
						<tr>
							<td><strong>创建日期：</strong></td>
							<td><span id="createdateSpan"></span></td>
						</tr>
						<tr>
							<td><strong>截止日期：</strong></td>
							<td><span id="expiredateSpan"></span></td>
						</tr>
						<tr>
							<td><strong>最后一次修改日期：</strong></td>
							<td><span id="lastupdatedateSpan"></span></td>
						</tr>
						<tr>
							<td><strong>预计工时：</strong></td>
							<td><span id="estimateSpan"></span></td>
						</tr>
						<tr>
							<td><strong>消耗工时：</strong></td>
							<td><span id="expendSpan"></span></td>
						</tr>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭窗口</button>
				</div>
			</div>
		</div>
	</div>
