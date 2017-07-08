$(function() {
	$("a[id*='showBtn-']").each(function(){	// 取得修改按钮
		var tid = this.id.split("-")[1];	// 分离出id信息
		$(this).on("click",function(){
			console.log("tid = " + tid) ;
			loadTask(tid) ;
		}) ;
	}) ;
	
	$("#myform").validate({
		debug : true, // 取消表单的提交操作
		submitHandler : function(form) {
			var cnote = $("#cnote").val() ;
			var tid = $("#tid").val() ;
			var proid = $("#proid").val() ;	
			console.log("修改数据编号：" + tid + "，原因：" + cnote) ;
			$("#taskCancelInfo").modal("hide") ;
			$.post("pages/jsp/manager/task/TaskActionManager!cancel.action",
					{"task.tid":tid,"task.cnote":cnote,"project.proid":proid},
					function(data) {
						if (data.trim() == "true") {
							$("#statusSpan-"+tid).text("取消") ;
							$("#statusSpan-"+tid).attr("class","text-danger") ;
						}
						$("#taskCancelInfo").modal("hide") ;
						operateAlert(data.trim() == "true","任务取消成功！","任务取消失败！") ;
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
	
	$("a[id*='cancelBtn-']").each(function(){	// 取得修改按钮
		var tid = this.id.split("-")[1];	// 分离出id信息
		var proid = this.id.split("-")[2] ;	// 项目ID
		$(this).on("click",function(){
			console.log("tid = " + tid) ;
			$("#tid").val(tid) ;
			$("#proid").val(proid) ;
			$("#taskCancelInfo").modal("show") ;
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
