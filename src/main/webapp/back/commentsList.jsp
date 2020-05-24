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
    <title>诗歌检索系统前台反馈管理</title>
    <script>
        $(function(){
            $("#p_tb").jqGrid({
                url:"${app}/comment/queryAllByPage",
                editurl:"${app}/comment/edit",
                datatype:"json",
                styleUI:"Bootstrap",
                colNames:["ID","内容","时间"],
                pager:"#p_pager",
                rowNum:10,
                rowList:[5,10],
                viewrecords:true,
                autowidth:true,
                height:true,
                colModel:[
                    {name:"id"},
                    {name:"content",editable:true},
                    {name:"time",editType:"date",editable:true},
                ]
            }).jqGrid("navGrid","#p_pager",{edit:false,add:false,search:false},
                //edit
                {},
                //add
                {},
                //del
                {
                    closeAfterDel:true,
                    aftersubmit:function(response){
                        $("#p_tb").trigger("reloadGrid")
                    }
                })
        })
    </script>
</head>
<body>
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