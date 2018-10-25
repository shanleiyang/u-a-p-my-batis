<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/tags/web-ui" prefix="uap"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
	<meta charset="utf-8" />
	<title>大数据项目</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<!-- BEGIN GLOBAL MANDATORY STYLES -->
	<uap:asset path="home/bootstrap.min.css,font-awesome.css,home/style-metro.css,home/style.css" />
	<!-- END GLOBAL MANDATORY STYLES -->
	<script type="text/javascript">	
		var context="<%=request.getContextPath()%>";
	</script>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed page-full-width">
	<!-- BEGIN HEADER -->
	<div class="header navbar navbar-inverse navbar-fixed-top">
		<!-- BEGIN TOP NAVIGATION BAR -->
		<div class="navbar-inner">
			<div class="container-fluid">
				<!-- BEGIN LOGO -->
				<a class="brand" href="#">
				<img src="<uap:ui img='logo.png'/>" alt="logo" />
				</a>
				<!-- END LOGO -->
				<!-- BEGIN HORIZANTAL MENU -->
				<div class="navbar hor-menu hidden-phone hidden-tablet">
					<div class="navbar-inner" id="menubar">
						<ul class="nav">
							<!-- 一级菜单 -->
							<li v-for="firstmenu in menuList" v-bind:class="{'active': firstmenu.id == toFirstId}" v-on:click="clickMenu(firstMenu,firstmenu.id)">
								<a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
									<span v-if="firstmenu.id == toFirstId" class="selected"></span>
									{{firstmenu.name}}
									<span class="arrow" v-if="firstmenu.childMenus && firstmenu.childMenus.length > 0"></span>
								</a>
								<ul class="dropdown-menu" v-if="firstmenu.childMenus && firstmenu.childMenus.length > 0">
									<!-- 二级菜单 -->
									<li v-for="secondmenu in firstmenu.childMenus" v-on:click="clickMenu(secondmenu,firstmenu.id,secondmenu.id)" v-bind:class="{'active': secondmenu.id == toSecondId , 'dropdown-submenu': (secondmenu.childMenus && secondmenu.childMenus.length > 0)}">
										<a href="javascript:;">
											{{secondmenu.name}}
											<span class="arrow" v-if="secondmenu.childMenus && secondmenu.childMenus.length > 0"></span>
										</a>
										<ul class="dropdown-menu" v-if="secondmenu.childMenus && secondmenu.childMenus.length > 0">
											<!-- 三级菜单 -->
											<li v-for="threemenu in secondmenu.childMenus" v-on:click="clickMenu(threemenu,firstmenu.id,secondmenu.id,threemenu.id)" v-bind:class="{'active': threemenu.id == toThreeId}">
												<a href="javascript:;">{{threemenu.name}}</a>
											</li>
										</ul>
									</li>
								</ul>
							</li>
							
						</ul>
					</div>
				</div>
				<!-- END HORIZANTAL MENU -->
				<!-- BEGIN TOP NAVIGATION MENU -->              
				<ul class="nav pull-right">
					<!-- BEGIN USER LOGIN DROPDOWN -->
					<li class="dropdown user">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<i class="fa fa-user fa-2x"></i>
						<span class="username">Bob Nilson</span>
						<i class="fa fa-angle-down"></i>
						</a>
						<ul class="dropdown-menu">
							<li><a href="extra_profile.html"><i class="fa fa-user"></i> My Profile</a></li>
							<li><a href="page_calendar.html"><i class="fa fa-calendar"></i> My Calendar</a></li>
							<li><a href="inbox.html"><i class="fa fa-envelope"></i> My Inbox(3)</a></li>
							<li><a href="#"><i class="fa fa-tasks"></i> My Tasks</a></li>
							<li class="divider"></li>
							<li><a href="extra_lock.html"><i class="fa fa-lock"></i> Lock Screen</a></li>
							<li><a href="login.html"><i class="fa fa-key"></i> Log Out</a></li>
						</ul>
					</li>
					<!-- END USER LOGIN DROPDOWN -->
				</ul>
				<!-- END TOP NAVIGATION MENU --> 
			</div>
		</div>
		<!-- END TOP NAVIGATION BAR -->
	</div>
	<!-- END HEADER -->
	<!-- BEGIN CONTAINER -->   
	<div class="page-container row-fluid" >
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN PAGE CONTAINER-->
			<div class="container-fluid">
				<iframe id="mainFrame" name="mainFrame" src="<%=request.getContextPath()%>/public/introduce.jsp" frameborder="0" allowtransparency="true" width="100%" height="100%"></iframe>
			</div>
			<!-- END PAGE CONTAINER--> 
		</div>
		<!-- END PAGE -->    
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<uap:asset path="jquery.js,bootstrap.js,vue.min.js" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/home.js"></script>
	<!--[if lt IE 9]>
	<script src="<uap:ui js='excanvas.min.js'/>"></script>
	<script src="<uap:ui js='respond.min.js'/>"></script>  
	<![endif]-->   
</body>
<!-- END BODY -->
</html>