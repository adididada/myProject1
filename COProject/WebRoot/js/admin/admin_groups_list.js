$(function() {
	$("button[id*='updateBtn-']").each(function(){	// 取得修改按钮
		var gid = this.id.split("-")[1];	// 分离出id信息
		$(this).on("click",function(){
			var title = $("#title-" + gid).val() ;
			var note = $("#note-" + gid).val() ;
			console.log("gid = " + gid + "，title = " + title + "，note = " + note) ;
			$.post("pages/jsp/admin/role/GroupsActionAdmin!update.action",
					{"groups.gid":gid,"groups.title":title,"groups.note":note},
					function(data){
						operateAlert(data == "true","权限组信息修改成功！","权限组信息修改失败！") ;
					},"text")
			// 编写Ajax异步更新操作
		}) ;
	}) ;
	
	$("button[id*='showBtn-']").each(function(){	// 取得显示按钮
		var gid = this.id.split("-")[1];	// 分离出id信息
		$(this).on("click",function(){
			console.log("gid = " + gid) ;
			// 编写Ajax异步更新操作，读取所有的权限信息
			$.post("pages/jsp/admin/role/GroupsActionAdmin!show.action",
				{"groups.gid":gid},function(data){
					$("#actionsTab tr:gt(0)").remove() ;
					$("#groupsTitleSpan").text(data.title) ;
					for (var x = 0 ; x < data.actions.length ; x ++) {
						var tr = "<tr><td class='text-center'>"+data.actions[x].actid+"</td><td class='text-center'>"+data.actions[x].title+"</td><td>"+data.actions[x].url+"</td></tr>" ;
						$("#actionsTab").append($(tr)) ;
					}
					$("#groupsInfo").modal("toggle") ;
				},"json") ;
		}) ;
	}) ;
})