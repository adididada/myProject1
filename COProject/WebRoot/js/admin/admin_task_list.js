$(function() {
	$("button[id*='showBtn-']").each(function(){	// 取得修改按钮
		var tid = this.id.split("-")[1];	// 分离出id信息
		$(this).on("click",function(){
			console.log("tid = " + tid) ;
			loadTaskAdmin(tid) ;
		}) ;
	}) ;
})
function loadTaskAdmin(tid) {
	// 编写Ajax异步数据调用，读取角色以及对应的权限组信息，同时显示模态窗口
	$.post("pages/jsp/common/task/TaskActionCommon!show.action",
			{"task.tid":tid},
			function(data) {
				$("#titleHeadSpan2").text(data.title) ;
				$("#projectSpan").text(data.project) ;
				$("#titleSpan").text(data.title) ;
				$("#typeSpan").text(data.type) ;
				$("#noteSpan").text(data.note) ;
				$("#creatorSpan").html("<a href='pages/jsp/admin/admin/AdminActionAdmin!show.action?user.userid="+data.creator+"'>"+data.creator+"</a>") ;
				$("#receiverSpan").html("<a href='pages/jsp/admin/admin/AdminActionAdmin!show.action?user.userid="+data.receiver+"'>"+data.receiver+"</a>") ;
				$("#rnoteSpan").text(data.rnote) ;
				$("#cancelerSpan").html("<a href='pages/jsp/admin/admin/AdminActionAdmin!show.action?user.userid="+data.cancel+"'>"+data.cancel+"</a>") ;
				$("#cnoteSpan").text(data.cnote) ;
				if (data.pri == 0) {	// ★★☆
					$("#prioritySpan").text("★★★") ;
				} else if (data.pri == 1) {
					$("#prioritySpan").text("★★☆") ;
				} else {
					$("#prioritySpan").text("★☆☆") ;
				}
				
				$("#createdateSpan").text(data.createdate) ;
				$("#expiredateSpan").text(data.expiredate) ;
				$("#lastupdatedateSpan").text(data.lastupdatedate) ;
				$("#estimateSpan").text(data.es) ;
				$("#expendSpan").text(data.ep) ;
				$("#CNoticeShowUserBtn").on("click",function(){
					$("#taskInfo").modal("hide") ;
					loadUser($(this).attr("value")) ;
				}) ;
				$("#RNoticeShowUserBtn").on("click",function(){
					$("#taskInfo").modal("hide") ; 
					loadUser($(this).attr("value")) ;
				}) ;
				$("#CENoticeShowUserBtn").on("click",function(){
					$("#taskInfo").modal("hide") ;
					loadUser($(this).attr("value")) ;
				}) ;
			},"json") ;
	$("#taskInfo").modal("toggle") ;
}