$(function() {
	$("button[id*='updateBtn-']").each(function(){	// 取得修改按钮
		var snid = this.id.split("-")[1];	// 分离出id信息
		$(this).on("click",function(){
			var level = $("#level-" + snid).val() ;
			console.log("snid = " + snid + "，level = " + level) ;
			// 编写Ajax异步数据调用 
			$.post("pages/jsp/admin/notice/NoticeActionAdmin!updateLevel.action",
					{"notice.snid":snid,"notice.level":level},
					function(data){
						operateAlert(data.trim()=="true","公告阅读级别修改成功！","公告阅读级别修改失败！") ;
					},"text") ;
		}) ;
	}) ;
	$("#deleteBtn").on("click",function(){	// 绑定用户锁定操作
		operateChecked("确定要删除这些公告信息吗？","notice.snid",'pages/jsp/admin/notice/NoticeActionAdmin!delete.action?p=p') ;
	}) ;
}) ;