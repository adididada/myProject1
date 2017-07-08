$(function() {
	$("button[id*='showBtn-']").each(function(){	// 取得修改按钮
		var did = this.id.split("-")[1];	// 分离出id信息
		$(this).on("click",function(){
			console.log("did = " + did) ;
			loadDocument(did) ;
		}) ;
	}) ;
	
	$("a[id*='showUserBtn-']").each(function(){	// 取得修改按钮
		var userid = this.id.split("-")[1];	// 分离出id信息
		console.log("userid = " + userid) ;
		$(this).on("click",function(){
			loadUser(userid) ; 
		}) ; 
	}) ;
}) ; 

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

function loadDocument(did) {
	$.post("pages/jsp/common/document/DocumentActionCommon!show.action",{
		"document.did" : did 
	},function(data) {
		$("#titleHeadSpan").text(data.title) ;
		$("#titleSpan").text(data.title) ;
		$("#doctypeSpan").text(data.doctype) ;
		$("#useridSpan").html("<a class='btn btn-info' id='DocShowUserBtn' value='"+data.userid+"'>"+data.userid+"</a>") ;
		$("#pubdateSpan").text(data.pubdate) ;
		$("#contentSpan").text(data.content) ;
		console.log(data.file) ;
		$("#fileSpan").empty() ;
		$("#fileSpan").append($("<a class='btn btn-info' href='upload/document/"+data.file+"'>下载："+data.file+"</a>")) ;
		$("#DocShowUserBtn").on("click",function(){
			$("#documentInfo").modal("hide") ;
			loadUser($(this).attr("value")) ;
		}) ;
	} , "json") ;
	$("#documentInfo").modal("toggle") ;
}