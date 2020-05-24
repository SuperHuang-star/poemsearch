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
    <title>诗歌检索系统后台检索热度排行榜</title>
    <script>
        $(function () {
            //初始化排行榜
            initRedisKeyword();
        })
        function initRedisKeyword() {
            $.ajax({
                url:"${app}/dic/findRedisKeywords",
                type:"post",
                success:function (data) {
                    $("#rediskeywordlists").empty();
                    $.each(data,function(i,redisKeyword){
                        var button=$("<button>").addClass("btn-primary").css({"margin-right":"5px"}).html("&nbsp;"+redisKeyword.value+"&nbsp;&nbsp;")
                        var span=$("<span>").addClass("badge").text(parseFloat(redisKeyword.score).toFixed(1));
                        if(redisKeyword.score>=10){
                            span.css({"color":"red"})
                        }
                        button.append(span)
                        $("#rediskeywordlists").append(button);
                    })
                }
            })
        }
    </script>
</head>
<body>
<div class="row">

    <%--redis热词推荐榜--%>
        <div class="panel panel-default">
            <div class="panel-heading">全网热搜榜:</div>
            <div class="panel-body" id="rediskeywordlists">
            </div>
        </div>
</div>
</body>
</html>
