$(function() {
	$("button[id*='showBtn-']").each(function(){	// 取得修改按钮
		var tid = this.id.split("-")[1];	// 分离出id信息
		$(this).on("click",function(){
			console.log("tid = " + tid) ;
			loadTaskEmp(tid) ;
		}) ;
	}) ;
	
	$("#myform").validate({
		debug : true, // 取消表单的提交操作
		submitHandler : function(form) {
			var rnote = $("#rnote").val() ;
			var tid = $("#tid").val() ;
			var expend = $("#expend").val() ;
			console.log("修改数据编号：" + tid + "，原因：" + rnote) ;
			$.post("pages/jsp/emp/task/TaskActionEmp!updateFinish.action",
					{
						"task.tid" : tid ,
						"task.rnote" : rnote ,
						"task.expend" : expend 
					},function(data){
						if (data.trim() == "true") {
							$("#statusSpan-"+tid).text("已完成") ;
							$("#statusSpan-"+tid).attr("class","text-success") ;
							$("#btn-" + tid).empty() ;
						}
						$("#taskFinishInfo").modal("hide") ;
						operateAlert(data.trim() == "true","任务回单填写成功！","任务回单填写失败！") ;
					},"text") ;
		},
		errorPlacement : function(error, element) {
			$("#" + $(element).attr("id").replace(".", "\\.") + "Msg").append(error);
		},
		highlight : function(element, errorClass) {
			$(element).fadeOut(1,function() {
				$(element).fadeIn(1, function() {
					$("#" + $(element).attr("id").replace(".","\\.") + "Div").attr("class","form-group has-error");
				});

			})
		},
		unhighlight : function(element, errorClass) {
			$(element).fadeOut(1,function() {
				$(element).fadeIn(1,function() {
						$("#" + $(element).attr("id").replace(".","\\.") + "Div").attr("class","form-group has-success");
				});
			})
		},
		errorClass : "text-danger",
		rules : {
			"cnote" : {
				required : true,
			}
		}
	});
	
	$("button[id*='finishBtn-']").each(function(){	// 取得修改按钮
		var tid = this.id.split("-")[1];	// 分离出id信息
		$(this).on("click",function(){
			console.log("tid = " + tid) ;
			$("#tid").val(tid) ;
			$("#taskFinishInfo").modal("show") ;
		}) ;
	}) ; 
	$("a[id*='showUserBtn-']").each(function(){	// 取得修改按钮
		var userid = this.id.split("-")[1];	// 分离出id信息
		console.log("userid = " + userid) ;
		$(this).on("click",function(){
			loadUser(userid) ; 
		}) ; 
	}) ;
})
function loadUser(userid) {
	$.post("pages/jsp/common/user/UserActionCommon!show.action",
			{"user.userid":userid},function(data){
				$("#useridHeadSpan2").text(userid) ; 
				$("#userPhoto").attr("src","upload/user/" + data.photo) ;
				$("#userUserid").text(data.userid) ;
				$("#userEmail").text(data.email) ;
				$("#userName").text(data.name) ;
				$("#userPhone").text(data.phone) ;
				$("#userLastlogin").text(data.lastlogin) ;
				$("#userLockflag").text(data.lockflag == 0 ? "活跃" : "锁定") ;
				if (data.level == 0 || data.level == 1) {
					$("#userLevel").text("管理员") ;
				} else if (data.level == 2) {
					$("#userLevel").text("项目经理") ;
				} else {
					$("#userLevel").text("员工") ;
				}
				$("#userInfo").modal("toggle") ;
			},"json") ;
}

function loadTaskEmp(tid) {
	// 编写Ajax异步数据调用，读取角色以及对应的权限组信息，同时显示模态窗口
	$.post("pages/jsp/emp/task/TaskActionEmp!show.action",
			{"task.tid":tid},
			function(data) {
				$("#titleHeadSpan2").text(data.title) ;
				$("#projectSpan").text(data.project) ;
				$("#titleSpan").text(data.title) ;
				$("#typeSpan").text(data.type) ;
				$("#noteSpan").text(data.note) ;
				$("#creatorSpan").html("<a class='btn btn-info' id='CNoticeShowUserBtn' value='"+data.creator+"'>"+data.creator+"</a>") ;
				$("#receiverSpan").html("<a class='btn btn-info' id='RNoticeShowUserBtn' value='"+data.receiver+"'>"+data.receiver+"</a>") ;
				$("#rnoteSpan").text(data.rnote) ;
				$("#cancelerSpan").html("<a class='btn btn-info' id='CENoticeShowUserBtn' value='"+data.cancel+"'>"+data.cancel+"</a>") ;
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
