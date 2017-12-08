<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>马桶猫管理后台</title>

    <%@ include file="header.jsp"%>

</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.html">娃娃机管理后台</a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">

                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                        </li>
                        <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="login.html">
                                <i class="fa fa-sign-out fa-fw"></i>退出
                            </a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li>
                            <a href="/toiletCat/back/user/user.jsp"><i class="fa fa-group fa-fw"></i> 用户管理</a>
                        </li>
                        <li>
                            <a href="/toiletCat/back/machine/machine.jsp"><i class="fa fa-gear fa-fw"></i> 娃娃机管理</a>
                        </li>
                        <li>
                            <a href="/toiletCat/back/toy/toy.jsp"><i class="fa fa-paw fa-fw"></i> 玩具管理</a>
                        </li>
                        <li>
                            <a href="/toiletCat/back/deliver/deliver.jsp"><i class="fa fa-truck fa-fw"></i> 快递管理</a>
                        </li>
                        <li>
                            <a href="/toiletCat/back/gameRoom/gameRoom.jsp"><i class="fa fa-gamepad fa-fw"></i>游戏房间管理</a>
                        </li>
                        <li>
                            <a href="/toiletCat/back/bannerImg/bannerImg.jsp"><i class="fa fa-image fa-fw"></i>Banner图管理</a>
                        </li>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>
    </div>