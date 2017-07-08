$(function(){
	$("#lockBtn").on("click",function(){	// 绑定用户锁定操作
		operateChecked("确定要锁定这些用户吗？","user.userid",'pages/jsp/admin/user/UserActionAdmin!updateLock.action?p=p') ;
	}) ;
	$("a[id*='passwordBtn-']").each(function(){	// 取得修改按钮
		var userid = this.id.split("-")[1];	// 分离出id信息
		$(this).on("click",function(){
			$("#upTitle").text($("#userid-" + userid).text()) ;
			$("#password\\.userid").val(userid) ;
			$("#userPassword").modal("toggle") ;	// 显示模态窗口
		}) ;
	}) ;
	$("a[id*='userBtn-']").each(function(){	// 取得修改按钮
		var userid = this.id.split("-")[1];	// 分离出id信息
		$(this).on("click",function(){	// 进行异步数据的读取以及模态窗口的显示
			$.post("pages/jsp/admin/user/UserActionAdmin!updatePre.action",
					{
						"user.userid" : userid 
					},function(data){
						$("#useridTitle").text(data.userid) ;
						$("#userid").val(data.userid) ;
						$("#name").val(data.name) ;
						$("#phone").val(data.phone) ;
						$("#email").val(data.email) ;
						$("#photo").attr("src","upload/user/" + data.photo) ;
						$("#level option").each(function(ind){
							$(this).prop("selected",false) ;
						}) ;
						$("#level option").each(function(ind){
							if ($(this).val() == data.level) {
								$(this).prop("selected",true) ;
							}
						})
					},"json") ;
			$("#userInfo").modal("toggle") ;	// 显示模态窗口
		}) ;
	}) ;
	$("#userForm").validate({
		debug : true, // 取消表单的提交操作
		submitHandler : function(form) {
			var userid = $("#userid").val() ;
			var name = $("#name").val() ;
			var phone = $("#phone").val() ;
			var email = $("#email").val() ;
			var level = $("#level").val() ;
			var rid = 0 ;
			$.post("pages/jsp/admin/user/UserActionAdmin!updateInfo.action",
				{
					"user.userid" : userid ,
					"user.name" : name ,
					"user.phone" : phone ,
					"user.email" : email ,
					"user.level" : level 
				},function(data) {
					operateAlert(data == "true","用户信息修改成功！","用户信息修改失败！") ;
					if (data == "true") {
						$("#name-" + userid).text(name) ;
						$("#phone-" + userid).text(phone) ;
						$("#email-" + userid).text(email) ;
						if (level == 2) {
							rid = 4 ;
						} else {
							rid = 5 ;
						}
						$("#role\\.rid-"+userid+" option:selected").each(function(ind){
							console.log($(this).val() + "，" + $(this).text() + "，" + $(this).prop("selected")) ;
							$(this).prop("selected",false) ;
						}) ;
						$("#role\\.rid-"+userid+" option").each(function(ind){
							if ($(this).val() == rid) {
								$(this).prop("selected",true) ;
							}
						})
					}
				} , "text") ;
			$("#userInfo").modal("toggle") ;
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
			"userid" : {
				required : true
			},
			"name" : {
				required : true
			},
			"password" : {
				required : true
			},
			"phone" : {
				required : true
			},
			"email" : {
				required : true,
				email : true
			},
			"level" : {
				required : true
			}
		} 
	});
	$("#myform").validate({
		debug : true, // 取消表单的提交操作
		submitHandler : function(form) {
			var id = $("#password\\.userid").val() ;
			var password = $("#password").val() ;
			// 编写Ajax异步数据调用
			$.post("pages/jsp/admin/user/UserActionAdmin!updatePassword.action" ,
				{
					"user.userid" : id ,
					"user.password" : password
				},function(data) {
					$("#userPassword").modal("hide") ;
					operateAlert(data == "true","密码修改成功！","密码修改失败！") ;
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
			"password" : {
				required : true
			}
		}
	});
	// 处理管理员列表页面按钮
	$("button[id*='updateBtn-']").each(function(){	// 取得修改按钮
		var userid = this.id.split("-")[1];	// 分离出id信息
		$(this).on("click",function(){
			var rid = $("#role\\.rid-" + userid).val() ;
			console.log("userid = " + userid + "，rid = " + rid) ;
			$.post("pages/jsp/admin/user/UserActionAdmin!updateRole.action",
					{
						"user.userid" : userid ,
						"role.rid" : rid
					},
					function(data){
						operateAlert(data == "true","管理员角色信息修改成功！","管理员角色修改失败！") ;
					},"text") ;
		}) ;
	}) ;
})

