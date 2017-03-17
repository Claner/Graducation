function getAcademy(){
    var data={
        pageNo:0,
        pageSize:0
    };
    sew.post('/Admin/getAllAcademy',data,function (res){
        $("#professional_id").html('<option>专业获取中...</option>');
        var academyTpl = Handlebars.compile($("#academy-template").html());

        $("#academy_id").html(academyTpl(res.dataList));
        getProfessional();
        

    },function (err){
         swal(err.message,"","error");
    });
}

function getProfessional(){
    var data={
        academy_id:$("#academy_id").val(),
        pageNo:0,
        pageSize:0
    };
    sew.post('/Admin/getTargetProfessional',data,function (res){

        var professionalTpl = Handlebars.compile($("#professional-template").html());

        $("#professional_id").html(professionalTpl(res.dataList));
        
                
    },function (err){
         swal(err.message,"没有该学院专业","error");
    });
}