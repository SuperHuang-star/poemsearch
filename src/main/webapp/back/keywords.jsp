<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2020/3/8
  Time: 19:28
  To change this template use File | Settings | File Templates.
--%>
<%@page isELIgnored="false" contentType="text/html;utf-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<html>
<head>
    <title>诗歌检索系统后台热词管理</title>
    <script>
        $(function () {
            //删除指定热词
            $("#keywordLists").on("click",".close",function (e) {
                var keyword = $(e.currentTarget).attr("name")
                console.log(keyword);
                $.ajax({
                    url:"${app}/dic/delOne",
                    type:"post",
                    data:{keyword:keyword},
                    dataType:"json",
                    success:function (data) {
                        if(data.success){
                            initHotDic();
                        }
                    }
                })
            })
            //添加指定热词
            $("#saveDic").click(function () {
                var keyword = $.trim($("#remotekeyword").val())
                if(!keyword.length){
                    alert("请输入热词！！");
                    return false;
                }
                $.ajax({
                    url:"${app}/dic/addOne",
                    type:"post",
                    data:{keyword:keyword},
                    success:function (data) {
                        if(data.success){
                            $("#remotekeyword").val("")
                            initHotDic();
                        }
                    }
                })
            })

            //初始化热词
            initHotDic();
        })
        function initHotDic() {
            $.ajax({
                url:"${app}/dic/findAll",
                type:"post",
                success:function (data) {
                    $("#keywordLists").empty();
                    $.each(data,function (i,keywords){
                        var div = $("<div>").css({"width": "120px", "float": "left", "margin-right": "10px"})
                        div.addClass("alert alert-success")
                        var button  = $("<button>").addClass("close").attr("name",keywords);
                        var span=$("<span>").html("&times;");
                        button.append(span);
                        div.append(button).append(keywords)
                        $("#keywordLists").append(div);
                    })
                }
            })
        }
    </script>
</head>
<body>
<div class="row">

        <%--水平表单--%>
        <div class="form-horizontal">
            <div class="form-group">
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="remotekeyword" placeholder="输入热词...">
                </div>
                <div class="col-sm-2">
                    <button class="btn btn-info" id="saveDic">添加远程词典</button>
                </div>
            </div>
        </div>
        <%--当前系统扩展词--%>
        <div id="keywordLists">

        </div>
</div>
</body>
</html>
