<%@page isELIgnored="false" contentType="text/html;utf-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>诗歌检索系统后台诗歌管理</title>
    <script>
        $(function(){
            $("#p_tb").jqGrid({
                url:"${app}/poem/queryByPage",
                editurl:"${app}/poem/edit",
                datatype:"json",
                styleUI:"Bootstrap",
                colNames:["ID","诗词名","作者","类型","来源","内容","作者简介"],
                pager:"#p_pager",
                rowNum:10,
                rowList:[5,10],
                viewrecords:true,
                autowidth:true,
                height:true,
                colModel:[
                    {name:"id"},
                    {name:"name",editable:true},
                    {name:"author",editable:true},
                    {
                        name:"type",
                        editable:true,
                        edittype:"select",
                        editoptions:{
                            dataUrl:"${app}/category/queryAll",
                        },
                        formatter:function (value,opt,rowObject) {
                            return rowObject.category.name
                        }
                    },
                    {name:"origin",editable:true},
                    {name:"content",editable:true},
                    {name:"authordes",editable:true},
                ]
            }).jqGrid("navGrid","#p_pager",{search:false},
                //edit
                {
                    closeAfterEdit:true
                },
                //add

                {
                    closeAfterAdd:true,
                    aftersubmit:function(response){
                        $("#p_tb").trigger("reloadGrid")
                    }
                },
                //del
                {
                    closeAfterDel:true,
                    aftersubmit:function(response){
                        $("#p_tb").trigger("reloadGrid")
                    }
                })


                //清空文档
                $("#btn1").click(function () {
                    if(window.confirm("此操作不可恢复，确定清空所有文档？")){
                        $.ajax({
                            url:"${app}/es/delAll",
                            type:"post",
                            success:function (data) {

                            }
                        })
                    }
                })
                //重建索引
                $("#btn2").click(function () {
                    if(window.confirm("确定要 重建索引吗？重建索引需要一段时间，请耐心等待！！！")){
                        $.ajax({
                            url:"${app}/es/createIndex",
                            type:"post",
                            success:function (data) {

                            }
                        })
                    }
                })
        })
    </script>
</head>
<body>
<nav class="navbar navbar-default">

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <button type="button" class="btn btn-danger navbar-btn"  id= "btn1">清空ES所有文档</button>
        <button id= "btn2" type="button" class="btn btn-primary">基于基础数据重建ES索引库</button>
    </div>
</nav>
<%--中心内容栅格系统--%>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-12" style="padding-left: 0px;padding-right: 0px;">
            <table id="p_tb"></table>
            <div id="p_pager" style="height: 50px"></div>
        </div>
    </div>
    <hr>
</div>
</body>
</html>