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
    <title>反馈页</title>
    <link rel="stylesheet" href="${app}/boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="${app}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <script src="${app}/boot/js/jquery-2.2.1.min.js"></script>
    <script src="${app}/boot/js/bootstrap.js"></script>
    <script>
        $(function () {
            $("#form").submit(function () {
                var content = $("#content").val();
                console.log(content);
                if(content!=""){
                    $.ajax({
                        url:"${app}/comment/addComment",
                        data:{content:content},
                        type:"post",
                        success:function (data) {
                            location.href="${app}/front/fmain.jsp"
                        }
                    })
                }else{
                    alert("输入内容不能为空!");
                }
            })
        })
    </script>
    <style>
        .center{
            width:1000px;height:343px;margin:180px auto 0
        }
    </style>
</head>
<body>
<div class="container-fluid center">
    <div class="row">
        <div class="col-xs-6 col-md-1"></div>
        <div class="col-xs-6 col-md-10">
            <div class="container-fluid">
                <form class="form-horizontal" role="form" id="form" onsubmit="return false">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">内容：</label>
                        <div class="col-sm-8">
                            <textarea class="form-control" id="content" rows="6" placeholder="请输入您的意见或建议"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-warning">提交</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <div class="col-xs-6 col-md-1">
        </div>
    </div>
</div>
</body>
</html>
