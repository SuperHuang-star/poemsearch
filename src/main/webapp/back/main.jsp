<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="app" value="${pageContext.request.contextPath}"></c:set>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>诗歌检索系统后台</title>
    <link rel="icon" href="../img/favicon.ico">
    <link rel="stylesheet" href="${app}/boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="${app}/jqgrid/css/jquery-ui.css">
    <link rel="stylesheet" href="${app}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入jquery的js--%>
    <script src="${app}/boot/js/jquery-2.2.1.min.js"></script>
    <%--引入bootstrap的js--%>
    <script src="${app}/boot/js/bootstrap.min.js"></script>
    <%--jqgird的国际化js--%>
    <script src="${app}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <%--jqgird的js--%>
    <script src="${app}/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${app}/boot/js/ajaxfileupload.js"></script>
    <script>
        $(function () {
            $("#log_out").click(function () {
                $.ajax({
                    url:"${app}/user/logout",
                    type:"post",
                    success:function (data) {
                        var message = data.message;
                        console.log(message);
                        if(message=="退出成功"){
                            console.log("正在跳转 ...");
                            location.href="${app}/back/login.jsp"
                            // onclick="window.open('/back/main.jsp','_self')"
                        }
                    }
                })
            })
        })
    </script>


</head>

<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="#">诗歌检索后台管理系统</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎:<shiro:principal></shiro:principal></a></li>
                <shiro:authenticated>
                <li>
                    <a href="#" id="log_out">登出<span class="glyphicon glyphicon-log-out"></span></a>
                </li>
                </shiro:authenticated>
            </ul>
        </div>
    </div><!-- /.container-fluid -->
</nav>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2">
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                <shiro:hasAnyRoles name="sadmin">
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                管理员管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <a href="javascript:$('#main').load('${app}/back/adminsList.jsp')">管理员列表</a>
                        </div>
                    </div>
                </div>
                </shiro:hasAnyRoles>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                诗歌管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body">
                            <a href="javascript:$('#main').load('${app}/back/poemsList.jsp')">诗歌列表</a>
                        </div>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="heading5">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse5" aria-expanded="false" aria-controls="collapse5">
                                反馈管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapse5" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading5">
                        <div class="panel-body">
                            <a href="javascript:$('#main').load('${app}/back/commentsList.jsp')">反馈列表</a>
                        </div>
                    </div>
                </div>


                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingThree">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                热词管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                        <div class="panel-body">
                            <a href="javascript:$('#main').load('${app}/back/keywords.jsp')">热词列表</a>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingFour">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                                检索管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
                        <div class="panel-body">
                            <a href="javascript:$('#main').load('${app}/back/keywordsList.jsp')">检索排行榜</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-10">
            <div  id="main">
                <jsp:include page="poemsList.jsp"></jsp:include>
            </div>
        </div>
    </div>
    </div>
    <nav class="navbar navbar-default navbar-fixed-bottom"><!--贴在底部，不会消失-->
        <div style="text-align:center"><br>@qk nyist@zparkhr.com</div>
    </nav>
</body>