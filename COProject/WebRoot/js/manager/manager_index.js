$(function() {	// 页面加载的时候执行
	$.post("pages/jsp/manager/notice/NoticeActionManager!uncount.action",
		{},function(data){
			$("#noticeCount").text(data) ;
		},"text") ;
})