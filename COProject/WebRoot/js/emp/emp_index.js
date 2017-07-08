$(function() {	// 页面加载的时候执行
	$.post("IndexActionEmp!allCount.action",
		{},function(data){
			$("#noticeCount").text(data.noticeCount) ;
			$("#status0Count").text(data.status0Count) ;
			$("#status1Count").text(data.status1Count) ;
		},"json") ;
})