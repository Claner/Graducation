(function() {
	var accName = $.cookie("account");
	if(accName) {
		$("#account").html(accName);
	}else {
		window.location.href= "login.html";
	}
	if($.cookie('role')==2){
		$("#admin_name").text("管理员系统");
	}else if($.cookie('role')==3){
		$("#admin_name").text("超级管理员系统");
		$("#changePassword").hide();
	}else if($.cookie('role')==1){
		$("#admin_name").text("学生系统");
	}
})();
$("#loginout").click(function() {
	sew.get('/User/logout',null,function(res) {
		$.cookie('account',null,{
			path: '/'
		});
		 $.cookie("role",null,{
                path:"/"
         });

         swal({
	        title: "退出成功!",
	        text: "",
	        type: "success",
	        confirmButtonText: "",
	        closeOnConfirm: false
	    },function (){
	        window.location.href = "login.html";
	   });
	},function(err) {
		//sew.log("退出失败");
		notie.alert(3, '退出失败', 2);
	});
});

$("#modify").on("click",function () {
	if($("#pre_password").val()===""||$("#new_password").val()===""||$("#comfirmpwd").val()===""){
		notie.alert(3, '不能为空', 2);
		return;
	}
	if($("#new_password").val()!==$("#comfirmpwd").val()){
		notie.alert(3, '密码不一致', 2);
		return;
	}
	var data={
        pre_password:$("#pre_password").val(),
        new_password:$("#new_password").val()
	};
	sew.post("/User/modifyPassword",data,function (){
		swal({
	        title: "修改成功!",
	        text: "",
	        type: "success",
	        confirmButtonText: "",
	        closeOnConfirm: false
	    },function (){
	        window.location.reload();
	    });
	},function (err){
        notie.alert(3, err.message, 2);
	});
});
