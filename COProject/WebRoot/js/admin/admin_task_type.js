$(function() {
	$("button[id*='updateBtn-']").each(function(){	// 取得修改按钮
		var ttid = this.id.split("-")[1];	// 分离出id信息
		bindUpdateEvent(ttid) ;
	}) ;
	$("#insertBtn").on("click",function(){
		$("#taskTypeInfo").modal("toggle") ;
	}) ; 
	$("#myform").validate({
		debug : true, // 取消表单的提交操作
		submitHandler : function(form) {
			var title = $("#tasktype\\.title").val() ;
			$.post("pages/jsp/admin/task/TasktypeActionAdmin!insert.action" ,
					{"tasktype.title":title},function(data){
						if (data.flag == true) {
							var ttid = data.ttid ;
							addRow(ttid,title) ;
						}
						$("#taskTypeInfo").modal("hide") ;
						operateAlert(data.flag,"任务类型信息增加成功！","任务类型信息增加失败！") ;
					} , "json") ;
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
			"tasktype.title" : {
				required : true,
//				remote : {
//									url : "check.jsp", // 后台处理程序
//									type : "post", // 数据发送方式
//									dataType : "html", // 接受数据格式
//									data : { // 要传递的数据
//										code : function() {
//											return $("#code").val();
//										}
//									},
//									dataFilter : function(data, type) {
//										if (data.trim() == "true")
//											return true;
//										else
//											return false;
//									}
//				} 
			}
		}
	});
})
/**
 * 增加新的数据行信息
 * @param dtid 新增加的文档分类编号
 * @param title 新增的文档类型标题
 */
function addRow(ttid,title) {
	var str = "<tr><td class='text-center'>" + ttid
			+ "</td><td class='text-center'><input type='text' id='title-" + ttid + "' name='title-" + ttid + "' class='form-control input-sm' value='" + title + "'>" 
			+ "</td><td class='text-center'>" +
					"<button class='btn btn-primary' id='updateBtn-" + ttid + "'>" 
			+ "<span class='glyphicon glyphicon-edit'></span>&nbsp;修改</button></td></tr>";
	$("#ttTab").append($(str)) ;
	bindUpdateEvent(ttid) ;
	$("#taskTypeInfo").modal("toggle") ;
	operateAlert(true,"任务类型信息增加成功！","任务类型信息增加失败！") ;
}
function bindUpdateEvent(ttid) {
	$("#updateBtn-" + ttid).on("click",function() {
		var title = $("#title-" + ttid).val() ;
		console.log("ttid = " + ttid + "，title = " + title) ;
		$.post("pages/jsp/admin/task/TasktypeActionAdmin!update.action" ,
				{"tasktype.ttid":ttid,"tasktype.title":title},function(data){
					operateAlert(data == "true","文档类型信息修改成功！","文档类型信息修改失败！") ;
				},"text") ;
	}) ;
}