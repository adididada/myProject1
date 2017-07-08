$(function() {
	$("button[id*='updateBtn-']").each(function(){	// 取得修改按钮
		var rid = this.id.split("-")[1];	// 分离出id信息
		$(this).on("click",function(){
			var title = $("#title-" + rid).text() ;
			var note = $("#note-" + rid).text() ;
			console.log("rid = " + rid + "，title = " + title + "，note = " + note) ;
			// 编写Ajax异步数据调用，读取角色以及对应的权限组信息，同时显示模态窗口
			$.post("pages/jsp/admin/role/RoleActionAdmin!updatePre.action",
				{"role.rid" : rid},function(data){
					$("#roleTitleSpan").text(data.title) ;
					$("#role\\.rid").val(data.rid) ;
					$("#role\\.title").val(data.title) ;
					$("#role\\.note").val(data.note) ;
					$("#groupsDiv").empty() ;
					for(var x = 0 ; x < data.groups.length ; x ++) {
						var str = "<div class='checkbox col-md-4'><label><input type='checkbox' id='gids' name='gids' value='"+data.groups[x].gid+"' " + data.groups[x].ckd + ">"+data.groups[x].title+"</label></div>"
						$("#groupsDiv").append($(str)) ;
					}
				},"json") ;
			$("#roleInfo").modal("toggle") ;
		}) ;
	}) ;
	$("#submitBtn").on("click",function(){
		var rid = $("#role\\.rid").val() ;
		var title = $("#role\\.title").val() ;
		var note = $("#role\\.note").val() ;
		var ugid = "" ;
		$("#gids:checked").each(function() {
			ugid += this.value + "|" ;
		}) ;
		$.post("pages/jsp/admin/role/RoleActionAdmin!update.action",
			{"role.rid":rid,"role.title":title,"role.note":note,"ugid":ugid},
			function(data){
				if (data == "true") {
					$("#title-" + rid).text(title) ;
					$("#note-" + rid).text(note) ;
				}
				$("#roleInfo").modal("toggle") ;
				operateAlert(data == "true","角色信息修改成功！","角色信息修改失败！") ;
			},"text") ;
		// 编写Ajax异步更新操作
	}) ;
	$("#myform").validate({
		debug : true, // 取消表单的提交操作
		submitHandler : function(form) {
			form.submit(); // 提交表单
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
			"role.title" : {
				required : true,
				remote : {
					url : "pages/jsp/admin/role/RoleActionAdmin!checkTitleUpdate.action", // 后台处理程序
					type : "post", // 数据发送方式
					dataType : "html", // 接受数据格式
					data : { // 要传递的数据
						"role.title" : function() {
							return $("#role\\.title").val();
						} ,
						"role.rid" : function() {
							return $("#role\\.rid").val();
						}
					},
					dataFilter : function(data, type) {
						if (data.trim() == "true")
							return true;
						else
							return false;
					}
				}
			},
			"role.note" : {
				required : true
			} ,
			"gid" : {
				required : true
			}
		}
	});
})