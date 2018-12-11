<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.Map,com.sgcc.uap.config.util.PlatformConfigUtil"%>
<%@ taglib uri="/tags/web-ui" prefix="uap"%>
<%
Map userMap=(Map)session.getAttribute("user");
String username = (String)userMap.get("name"); 
String iscLoginUrl = PlatformConfigUtil.getString("ISC_LOGINURL");
String serverUrl = String.format("%s://%s:%s%s", request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath());
%>
<!DOCTYPE html>
<html lang="en">
<!-- BEGIN HEAD -->
<head>
<meta http-equiv="Content-Type" charset="utf-8" />
<title>大数据项目</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<uap:mediacss path="home/style.css" />
<!-- END GLOBAL MANDATORY STYLES -->
<script type="text/javascript">	
	var username = "<%=username%>";
	var iscLoginUrl = "<%=iscLoginUrl%>";
	var serverUrl = "<%=serverUrl%>";
	var context = "<%=request.getContextPath()%>";
</script>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>
	<div class="content" id="mycontent">
		<div class="top">
			<div class="logo"><img src="<uap:ui img='logo.gif'/>" /></div>		
			<div class="menu_box" id="navi">
				<ul class="nav_tab">
					<!-- 一级菜单 -->
					<li v-for="firstmenu in menuList" v-bind:class="{'nav_on': (firstmenu.id == toFirstId || (selectUrl!=''&&firstmenu.url.indexOf(selectUrl)!=-1))}" v-on:click="clickMenu(firstmenu,firstmenu.id)">
						<a href="javascript:;" v-text="firstmenu.name">
						</a>
					</li>
				</ul>
			</div>
						
			<div class="people">
				<ul id="ple">
					<li><a href="#"><img src="<uap:ui img='people.gif'/>" /></a>
						<ul>
							<li></li>
							<li v-on:click="logout()"><a href="javascript:;"><i></i>退出</a></li>
						</ul>
					</li>
				</ul>
			</div>
			<div class="hgun"><a href="#"><img src="<uap:ui img='img_06.gif'/>" /></a></div>
		</div>
		<iframe id="mainFrame" name="mainFrame" scrolling="no" align="center" border="0" marginwidth="0" marginheight="0"
			src="" frameborder="0" allowtransparency="true" height="100%" style="background-color: #090a1e;"></iframe>
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<uap:asset path="jquery.js,bootstrap.js,vue.min.js" />
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/public/home.js"></script>
	<!--[if lt IE 9]>
	<script src="<uap:ui js='excanvas.min.js'/>"></script>
	<script src="<uap:ui js='respond.min.js'/>"></script>  
	<![endif]-->
</body>
<!-- END BODY -->
</html>