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
    <title>诗歌检索系统前台</title>
    <link rel="stylesheet" href="${app}/boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="${app}/META-INF/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <script src="${app}/boot/js/jquery-2.2.1.min.js"></script>
    <script src="${app}/boot/js/bootstrap.js"></script>
    <style>
        .breadcrumb > li + li:before {
            content: "|";
        }
        .actives{
            color:blueviolet;
        }
    </style>
    <script>
        $(function(){
            var type
            var author
            //类别栏 点击事件
            $.ajax({
                url:"${app}/category/queryAll2",
                dataType:"json",
                type:"post",
                success:function (data) {
                    $.each(data,function (i,val) {
                        var cate = $("<li>").html(val.name+"  ")
                        cate.prop("id",val.name);
                        $("#ul1").append(cate)
                    })
                }
            })
            //作者栏  点击事件
            $.ajax({
                url:"${app}/poem/queryAuthor",
                dataType:"json",
                type:"post",
                success:function (data) {
                    $.each(data,function (i,val) {
                        var author = $("<li>").html(val+"  ")
                        author.prop("id",val);
                        $("#ul2").append(author)
                    })
                }
            })
            //联系我们
            $("#concact").click(function () {
                location.href="comment.jsp"
            })
            //选择栏事件
            $(".breadcrumb").on("click","li",function (e) {
                $(e.currentTarget).siblings().removeClass("actives");
                $(e.currentTarget).addClass("actives");
                type = $.trim($(".actives").first().text());
                $("#type").html=type;
                author = $.trim($(".actives").last().text());
                $("#author").html=author;
                var content = $("#searchText").val();
                if(content=='')content=null
                console.log(type);
                console.log(author);
                esterm(content,type,author);
            })
            //检索框事件
            $("#searchBtn").click(function(){
                var term = $("#searchText").val()
                if(term!==''){
                    $("#searchText").val("")
                    esterm(term);
                }
            })
                esterm();

        })
        //展示数据
        function esterm(content,cate,author) {
            $.ajax({
                url:"${app}/es/findByKeywords",
                data:{content:content,type:cate,author:author},
                dataType:"json",
                type:"post",
                success:function (data) {
                    $("#div3").empty()
                    $.each(data,function (i,val) {
                        var div = $("<div>").css({
                            "border": "1px black solid",
                            "float": "left",
                            "width": "19%",
                            "height": "260",
                            "margin": "10px 50px 15px 0px",
                            "border-radius": "10px",
                            "position":"relative"
                        })
                        var img = $("<img id="+i+" src="+val.imagePath+">")
                        var bigImg

                        img.on("mouseover",function (e) {
                            bigImg = $("<div  />").attr("id", "contenttext").css({
                                "background": "#eee",
                                "border": "1px red solid",
                                "position": "absolute",
                                "width": "400",
                                "left": $(e.currentTarget).position().left + 30,
                                "top": $(e.currentTarget).position().top + 30,
                                "border-radius": "5px",
                                "display":"none",
                                "z-index":"9999",
                            });
                            var big1 = $("<div  class=\"panel panel-default\">\n" +
                                "  <div class=\"panel-heading\">正文:</div>\n" +
                                "  <div class=\"panel-body\">\n"
                                +val.content +
                                "  </div>\n" +
                                "</div>"+
                                "<div class=\"panel panel-default\">\n" +
                                "  <div class=\"panel-heading\">作者简介</div>\n" +
                                "  <div class=\"panel-body\">\n"
                                +val.authordes +
                                "  </div>\n" +
                                "</div>")
                            bigImg.html(big1)
                            $(e.currentTarget).parent().parent().parent().parent().append(bigImg);
                            $("#contenttext").slideDown(3000);
                            $("#contenttext").css({
                                "top" : e.pageY + 5,
                                "left" : e.pageX + 5,
                                "position" : "absolute",
                                "height" : "300px",
                                "width":"300px"
                            })
                            $("body").append(bigImg);
                        })
                        img.on("mouseout",function(){
                            $("#contenttext").remove()})
                        img.on("mousemove",function(e) {
                            $("#contenttext").css({
                                "top": e.pageY + 5,
                                "left": e.pageX + 5,
                                "position": "absolute",
                                "height" : "300px",
                                "width":"300px"
                            })
                        })
                        var ul=  $("<ul>");
                        var name = $("<li>").html("<h4><a href=" + val.href + '>'+val.name + "</a></h4>")
                        var author = $("<li>").html("<h4>"+val.author+"·"+val.type+"</h4>");
                        var content = $("<li>").html(val.content).css("display","none").attr("id","content"+val.id);
                        var authordes = $("<li>").html(val.authordes).css("display","none").attr("id","authordes"+val.id);
                        ul.append(img).append(name).append(author).append(content).append(authordes)
                        div.append(ul)
                        $("#div3").append(div);
                    })
                }
            })
        }
    </script>

</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-6 col-md-1"></div>
        <div class="col-xs-6 col-md-10">
            <div class="page-header" align="center">
                <h2>诗歌检索系统</h2>
            </div>
            <div class="container-fluid">
                <div class="row">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label for="searchText" class="col-md-2 control-label"></label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="searchText" placeholder="输入检索条件....">
                            </div>
                            <div class="col-sm-2">
                                <button type="button" id="searchBtn" class="btn btn-default">检索</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        <div class="row">
            <div class="col-sm-8 col-sm-offset-2" style="margin-bottom: 10px;padding-left: 0px;" >
                    <ul class="breadcrumb" style="padding: 25px;" id="ul1">
                        <li class="actives" style="color:red;">所有</li>
                    </ul>
            </div>
            <br>
            <div class="col-sm-8 col-sm-offset-2" style="margin-bottom: 10px;padding-left: 0px;">
                    <ul class="breadcrumb" style="padding: 25px;"  id="ul2">
                        <li class="actives"  style="color:red;">所有</li>
                    </ul>
            </div>
        </div>
            <div class="row" >
                <div class="col-sm-6 col-md-12" id="div3"></div>
            </div>
        </div>
        <div class="col-xs-6 col-md-1">
            <div class="row">
                <span class="glyphicon glyphicon-flag" aria-hidden="true" id="concact">联系我们</span>
            </div>
        </div>
    </div>
</div>
<div>
    <span id="type"></span>
    <span id="author"></span>
</div>
</body>