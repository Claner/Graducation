if(typeof $ == "undefined")
    throw new Error("sew requires jQuery");

var sew = {
	  base_url: "http://139.199.165.150:8080/School",
      back_url: "",
	  debug: true
};

sew.log = function(msg) {
	if (this.debug) {
		console.log(msg);
	}
};

sew.post = function(url, data, success_handler, error_handler,complete_handler) {

    url = sew.base_url + url;
    $.ajax({
        type: "POST",
        async:false,
        url: url,
        data: data,
        dataType: "json",
        cache: false,
        xhrFields: {
        	withCredentials: true
        },
        success: function(resJson){
            if(resJson.code == 20000) {
            	success_handler? success_handler(resJson) : "";
            } else {
                sew.log(resJson);
            	error_handler? error_handler(resJson) : "";
            }
        },
        error: function(jqXHR, textStatus, error){
            sew.log('发生错误：' + jqXHR.status + ':'+ jqXHR.readyState +': ' + textStatus + ': ' +error);
            alert("网络发生错误了啦");
            return false;
        },
        complete: function (jqXHR,testStatus) {
            if(complete_handler){
                complete_handler();
            }
        }
    });
};

sew.get = function(url, data, success_handler, error_handler,complete_handler) {
    // if(data && typeof data === 'object') {
    //     for(var key in data) {
    //         if(data[key]) {
    //             url += '/' + key + '/' + data[key];
    //         }
    //     }
    // }

    url = sew.base_url + url;
    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        xhrFields: {
        	withCredentials: true
        },
        async:false,
        //cache: false,
        success: function(resJson){
            if(resJson.code == 20000){
                success_handler? success_handler(resJson) : '';
                return true;
            } else {
                sew.log('未处理的错误');
                return false;
            }
        },
        error: function(jqXHR, textStatus, error){
            sew.log('发生错误：' + textStatus + error);
            //alert("网络发生错误了啦");
            return true;
        },
        complete: function (jqXHR,testStatus) {
            if(complete_handler){
                complete_handler();
            }
        }
    });
};

// sew.login = function(url,data,success_handler,error_handler,complete_handler) {
// 	url = sew.base_url + url;
// 	$.ajax({
//         type: "POST",
//         async:false,
//         url: url,
//         data: data,
//         dataType: "json",
//         cache: false,
//         xhrFields: {
//         	withCredentials: true
//         },
//         success: function(resJson){
//             if(resJson.status == 20000) {
//             	success_handler? success_handler(resJson) : "";
//             } else {
//             	error_handler? error_handler(resJson) : "";
//             }
//         },
//         error: function(jqXHR, textStatus, error){
//             sew.log('发生错误：' + jqXHR.status + ':'+ jqXHR.readyState +': ' + textStatus + ': ' +error);
//             alert("网络发生错误了啦");
//             return false;
//         }
//     });
// }

// function classify() {//插入分类二级联动
//     sew.get("/Admin/Type/getType",null,function(res) {
//         sew.log(res);
//         for(var i=0;i<res.data.length;i++) {
//             $("#first").append("<option id="+res.data[i].id+" class="+i+">"+res.data[i].name_cn+"</option>");
//         }
//         $("#first").change(function() {
//             $("#second").html("");
//             var j = $(this).find("option:selected").attr("class");
//             for(var i=0;i<res.data[j].children.length;i++) {
//                 $("#second").append("<option id="+res.data[j].children[i].id+">"+res.data[j].children[i].name_cn+"</option>");
//             }
//         })
//     },function(err) {
//         sew.log(err);
//     })
// }

// function defaultClassify(faId,typeId) {
//     $("#first").find("option").each(function() {
//         if($(this).attr("id") == faId) {
//             $(this).attr("selected",true);
//         }
//     })
//     if($("#first").find("option:selected").attr("class") == "") {
//         //sew.log("空");
//         //sew.log("空"+$("#first").find("option:selected").attr("class"))
//     }else {
//         //sew.log("不为空")
//         //sew.log("不为空"+$("#first").find("option:selected").attr("class"));
//         sew.get("/Admin/Type/getType",null,function(res) {
//             var j = $("#first").find("option:selected").attr("class");
//             for(var i=0;i<res.data[j].children.length;i++) {
//                 $("#second").append("<option id="+res.data[j].children[i].id+">"+res.data[j].children[i].name_cn+"</option>");
//             }
//             $("#second").find("option").each(function() {
//                 if($(this).attr("id") == typeId) {
//                     $(this).attr("selected",true);
//                 }
//             })
//         },function(err) {
//             sew.log(err);
//         })
//     }
    
// }

// sew.formAjax = function (url, data, success_handler, error_handler,complete_handler) {
//     url = sew.base_url + url +sew.cn;
//     $.ajax({
//         url: url,
//         type: 'POST',
//         data: data,
//         async: true,
//         cache: false,
//         xhrFields: {
//             withCredentials: true
//         },
//         contentType: false,// 告诉jQuery不要去设置Content-Type请求头
//         processData: false,// 告诉jQuery不要去处理发送的数据
//         success: function(resJson){
//             if(resJson.status == 200) {
//                 success_handler? success_handler(resJson) : "";
//             } else {
//                 error_handler? error_handler(resJson) : "";
//             }
//         },
//         error: function(jqXHR, textStatus, error){
//             sew.log('发生错误：' + jqXHR.status + ':'+ jqXHR.readyState +': ' + textStatus + ': ' +error);
//             alert("网络发生错误了啦");
//             return false;
//         },
//         complete: function (jqXHR,testStatus) {
//             if(complete_handler){
//                 complete_handler();
//             }
//         }
//     });
// };
// sew.htlcode = function(str) {
//     str = str.replace(/&amp;/g, '&'); 
//     str = str.replace(/&lt;/g, '<');
//     str = str.replace(/&gt;/g, '>');
//     str = str.replace(/&quot;/g, "''");  
//     str = str.replace(/&#039;/g, "'");
//     return str;
// }

// sew.show = function show(name) {

//     $("#"+name).show();

// }

// sew.hide = function hide(name) {

//     $("#"+name).hide();

// }

// sew.send = function send(url,data,success_handler,error_handler,complete_handler) {

//     url = sew.base_url + url +sew.cn;

//     $.ajax({
//         url: url,
//         type: 'POST',
//         data: data,
//         xhrFields: {
//             withCredentials: true
//         },
//         contentType: false,// 告诉jQuery不要去设置Content-Type请求头
//         processData: false,// 告诉jQuery不要去处理发送的数据
//         xhr: function(){ //获取ajaxSettings中的xhr对象，为它的upload属性绑定progress事件的处理函数
//             myXhr = $.ajaxSettings.xhr();
//             if(myXhr.upload){ //检查upload属性是否存在
//             //绑定progress事件的回调函数
//                 myXhr.upload.addEventListener('progress',progressHandlingFunction, false);
//             }
//             return myXhr; //xhr对象返回给jQuery使用
//         },
//         success: function(resJson){
//             if(resJson.status == 200) {
//                 success_handler? success_handler(resJson) : "";
//             } else {
//                 error_handler? error_handler(resJson) : "";
//             }
//         },
//         error: function(jqXHR, textStatus, error){
//             sew.log('发生错误：' + jqXHR.status + ':'+ jqXHR.readyState +': ' + textStatus + ': ' +error);
//             alert("网络发生错误了啦");
//             return false;
//         },
//         complete: function (jqXHR,testStatus) {
//             if(complete_handler){
//                 complete_handler();
//             }
//         }
//     });
// }

// //上传进度回调函数：  
// function progressHandlingFunction(e) {  
//     if (e.lengthComputable) {  

//         $('progress').attr({value : e.loaded, max : e.total}); //更新数据到进度条  
//         var percent = e.loaded/e.total*100;  
//         $('.progressInfo').html(e.loaded + "/" + e.total+" bytes. " + percent.toFixed(2) + "%"); 
//     }
// }