<%@page import="com.nrjh.framework.util.BspContextFactory,java.util.Calendar;"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/tags/web-ui" prefix="uap"%>
<!DOCTYPE html>
<html lang="en">
<!-- BEGIN HEAD -->
<head>
<meta http-equiv="Content-Type" charset="utf-8" />
<title>整体故障预测</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<%
	String provinceCode = BspContextFactory.getComCode();
	Calendar date = Calendar.getInstance();
	String year = String.valueOf(date.get(Calendar.YEAR));
	String month = String.valueOf(date.get(Calendar.MONTH)+1);
	String ymDate = year+"-"+(month.length()==1?"0"+month:month);
%>
<script type="text/javascript">
var skincontext = "<uap:skincontext />";
var context="<%=request.getContextPath()%>";
var GlobalParma={
		provinceNo:'<%=provinceCode%>',
		queryYear:'<%=year%>',
		queryMonth:'<%=month%>',
		dataUrl:context+"/rest/bigdata/dataProvider/getData",
		mapType:<%=provinceCode%>,
		mapPath:"<uap:skincontext />" + "/js/map/province/"+<%=provinceCode %>+".js"
};
var ProvFault = function(){
	this.charts = new Array();
};
ProvFault.prototype = {
		addObserve : function(obj){
			this.charts.push(obj);
		},
		showChart : function(){
			$.each(this.charts,function(i,n){
				n.showChart();
			});
		}
}
var provFault = new ProvFault();
//地图点击跳转
function changeByMap(cityCode){
	var year = GlobalParma.queryYear;
	var month = GlobalParma.queryMonth;
	window.parent.changeByChildren("/home/bigdata/showpage/guzhangyc/guzhangyc_ds.jsp");
	window.location.href = context+"/bigdata/showpage/guzhangyc/guzhangyc_ds.jsp?cityCode="+cityCode+"&year="+year+"&month="+month;
}
/*到货批次钻取到到货批次详情页面*/
function gotoDHPCXQ(batchNo){
	var year = GlobalParma.queryYear;
	var month = GlobalParma.queryMonth;
	var provinceNo = GlobalParma.provinceNo;
	window.parent.changeByChildren("/home/bigdata/showpage/daohuopc/daohuopicixq.jsp");
	window.location.href = context+"/bigdata/showpage/daohuopc/daohuopicixq.jsp?provinceNo="+provinceNo+"&year="+year+"&month="+month+"&bidBatchNo="+batchNo;
}
function query(){
	var arrmonth = new Date($("#arrmonth").val());
	GlobalParma.queryYear=arrmonth.getFullYear();
	GlobalParma.queryMonth=(arrmonth.getMonth()+1).toString();
	vm.initChart();
}
</script>

<!-- BEGIN GLOBAL MANDATORY STYLES -->
<uap:asset path="font-awesome.css,form.css" />
<uap:mediacss path="home/style.css" />
<!-- END GLOBAL MANDATORY STYLES -->
<uap:asset path="jquery.js,echarts.min.js,jquery.slimscroll.min.js,/map/bigdatamap.js"/>
<script src="<uap:skincontext/>/js/map/maplabel/<%=provinceCode %>_label.js"></script>

</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>
	<div class="main" id="totalPageDiv">
		<div class="filter_box"><b>筛选条件</b>
			<span class="main_box">
				  <input type="text" class="select_box" id="arrmonth" value="<%=ymDate %> " readonly="readonly" placeholder="年月"/> 
			</span>			
			<button class="se_btn" v-on:click="query"></button>			
		</div>
		<div class="dw_side">
			<ul>
				<li><span>累计安装量<b>（个）</b></span><i v-for="data in topInfo.INST_TOTAL_QTY" v-text="data"></i></li>
				<li><span>当月运行量<b>（个）</b></span><i v-for="data in topInfo.CUR_MONTH_RQTY" v-text="data"></i></li>
				<li><span>当月故障数<b>（个）</b></span><i v-for="data in topInfo.CUR_MONTH_FQTY" v-text="data"></i></li>
				<li><span>累计故障数<b>（个）</b></span><i v-for="data in topInfo.FAULT_TOTAL_QTY" v-text="data"></i></li>
				<li><span>供应商数量<b>（个）</b></span><i v-for="data in topInfo.SUPPLY_NUM" v-text="data"></i></li>
				<li><span>型号数量<b>（个）</b></span><i v-for="data in topInfo.MODEL_NUM" v-text="data"></i></li>
				<li><span>到货批次数<b>（个）</b></span><i v-for="data in topInfo.BATCH_NUM" v-text="data"></i></li>
		</ul>
		</div>
		
		<div class="ztgz_con">
			<div class="c_l">
				<ul class="gzlist">
					<li>
						<h2>当月故障率</h2>
						<div id="malfunctionRateMonth" ></div>
					</li>
					<li>
						<h2>累计故障率</h2>
						<div id="malfunctionRateTotal" ></div>
					</li>
				</ul>					
				<ul class="gzlist gzpic">
					<li>
						<h2>预测故障率</h2>							
						<p>
							<template v-for="data in gzlycData">
						      <i v-html="splitYM(data.YM)"></i><b v-text="data.FR_PRE+'%'"></b>
						    </template>
						</p>
					</li>
				</ul>					
			</div>
			
			<div class="ztditu">
			
				<div id="faultprovmap" ></div>
			
			</div>
			
			<div class="dhpc_left ztyccon_left">
				<div class="dh_con ztycbox">							
					<h2>到货批次故障率TOP5%<!-- <b><a href="#">导出</a></b> --></h2>												
					<div style="height:255px;">
						<ul class="dhbcon ztycin">
							<li><span>到货批次号</span><span v-text="gzycMonth.month1+'月'">12月</span><span v-text="gzycMonth.month2+'月'">1月</span><span v-text="gzycMonth.month3+'月'">2月</span></li>	
						</ul>
						<ul class="dhablelist ztyclist">									 
							<li v-for="data in dhpcgzlList"><span class="text_left" @mouseover="objmouseover($event,data.arriveBatchNo)" @mouseout="objmouseout" @mousemove="objmousemove" v-html="data.arriveBatchNo"></span><span class="number_right" v-text="data.VALUE1||'-'"></span><span class="number_right" v-text="data.VALUE2||'-'"></span><span class="number_right" v-text="data.VALUE3||'-'"></span></li>
						</ul>
					</div>
				</div>
			</div>					
						
		</div>
		
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<uap:asset path="vue.min.js,form.js,ajaxsubmit.js" />
	<script  type="text/javascript" src="<uap:ui js='bigdata/listDataGetter.js'/>"></script>
	<script  type="text/javascript" src="<uap:ui js='bigdata/malfunctionRate.js'/>"></script>
	<script  type="text/javascript" src="<uap:ui js='bigdata/malfunctionRateMonth.js'/>"></script>
	<!--[if lt IE 9]>
	<script src="<uap:ui js='excanvas.min.js'/>"></script>
	<script src="<uap:ui js='respond.min.js'/>"></script>  
	<![endif]-->
</body>
<!-- END BODY -->
<script type="text/javascript">
var vm = new Vue({
	el:'#totalPageDiv',
	data:{
		globalParam:GlobalParma,/*公共参数*/
		topInfo:{
			INST_TOTAL_QTY:'0',
			CUR_MONTH_RQTY:'0',
			CUR_MONTH_FQTY:'0',
			FAULT_TOTAL_QTY:'0',
			SUPPLY_NUM:'0',
			MODEL_NUM:'0',
			BATCH_NUM:'0'
		},/*顶端指标数据*/
		gzlycData:[],/*故障率预测数据*/
		dhpcgzlList:[]/*到货批次故障率列表数据*/
	},
	computed:{
		listDataGetter:function(){return new ListDataGetter();},
		malfunctionRateMonth:function(){return new MalfunctionRateMonth($("#malfunctionRateMonth")[0],'ztgzyc_gzlchart');},
		malfunctionRateTotal: function(){return new MalfunctionRate($("#malfunctionRateTotal")[0],'ztgzyc_gzlchart');},
		bigdatamap:function(){
			return new BigDataMap($('#faultprovmap')[0],"ztgz_map");//从全局变量中获取mapType
		},
		gzycMonth:function(){/*获取故障预测年月*/
			var month1,month2,month3;/*分别为下月，第二月，第三月*/
			var queryMonthInt = parseInt(this.globalParam.queryMonth);
			if(queryMonthInt < 12){
				month1 = queryMonthInt + 1;
			}else{
				month1 = 1;
			}
			if(month1 < 12){
				month2 = month1 + 1;
			}else{
				month2 = 1;
			}
			if(month2 < 12){
				month3 = month2 + 1;
			}else{
				month3 = 1;
			}
			return {'month1':month1,'month2':month2,'month3':month3};
		}
	},
	mounted: function(){
		provFault.addObserve(this.bigdatamap);
		provFault.addObserve(this.malfunctionRateMonth);
		provFault.addObserve(this.malfunctionRateTotal);
	},
	methods:{
		initChart:function(){
			this.globalParam = GlobalParma;
			provFault.showChart();
			this.getTopInfo();
			this.getGzlycData();
			this.getDhpcgzlList();
		},
		getTopInfo:function(){
			var topParam1={/*顶端数据获取参数*/
				"_TAGNO":"ztjkd_top_1",
				year:this.globalParam.queryYear,
				month:this.globalParam.queryMonth,
				provinceNo:this.globalParam.provinceNo
			};
			var month = this.globalParam.queryMonth;
			if(month<10) month='0'+month;
			var topParam2={/*顶端数据获取参数*/
				"_TAGNO":"ztjkd_top_2",
				ym:this.globalParam.queryYear+""+month
			};
			var t_this = this;
			this.listDataGetter.getDatas(this.globalParam.dataUrl,topParam1,function(rslt){
				var rsltList = rslt.data;
				var topData = t_this.topInfo;
				if(rsltList!=null &&rsltList.length>0){
					topData['INST_TOTAL_QTY'] = String(rsltList[0]['INST_TOTAL_QTY']).split('');
					topData['CUR_MONTH_RQTY'] = String(rsltList[0]['CUR_MONTH_RQTY']).split('');
					topData['CUR_MONTH_FQTY'] = String(rsltList[0]['CUR_MONTH_FQTY']).split('');
					topData['FAULT_TOTAL_QTY'] = String(rsltList[0]['FAULT_TOTAL_QTY']).split('');
				}
				t_this.topInfo = topData;
			});
			this.listDataGetter.getDatas(this.globalParam.dataUrl,topParam2,function(rslt){
				var rsltList = rslt.data;
				var topData = t_this.topInfo;
				if(rsltList!=null &&rsltList.length>0){
					topData['SUPPLY_NUM'] = String(rsltList[0]['SUPPLY_NUM']).split('');
					topData['MODEL_NUM'] = String(rsltList[0]['MODEL_NUM']).split('');
					topData['BATCH_NUM'] = String(rsltList[0]['BATCH_NUM']).split('');
				}
				t_this.topInfo = topData;
			});
		},
		getGzlycData:function(){
			var month = this.globalParam.queryMonth;
			if(month<10) month='0'+month;
			var gzlycParam={
				"_TAGNO":"ztgzyc_ycgzl",
				date:this.globalParam.queryYear+"-"+month+"-01",
				provinceNo:this.globalParam.provinceNo
			};
			var t_this = this;
			this.listDataGetter.getDatas(this.globalParam.dataUrl,gzlycParam,function(rslt){
				t_this.gzlycData = rslt.data;
			});
		},
		getDhpcgzlList:function(){
			var month = this.globalParam.queryMonth;
			if(month<10) month='0'+month;
			var dhpcgzlParam={
				"_TAGNO":"ztgzlyc_dhpcgzl",
				date:this.globalParam.queryYear+"-"+month+"-01",
				provinceNo:this.globalParam.provinceNo
			};
			var t_this = this;
			this.listDataGetter.getDatas(this.globalParam.dataUrl,dhpcgzlParam,function(rslt){
				var rsltList = rslt.data;
				if(rsltList!=null&&rsltList.length>0){
					for(var i=0;i<rsltList.length;i++){
						rsltList[i].arriveBatchNo = "<a herf='#' class='table_a_link' onclick='gotoDHPCXQ(\""+rsltList[i].ARRIVE_BATCH_NO+"\")'>"+rsltList[i].ARRIVE_BATCH_NO+"</a>";
					}
				}
				t_this.dhpcgzlList = rsltList;
			});
		},
		splitYM:function(YM){ //分割年月
			if(YM) {
				return YM.substr(0,4) + '<br/>' + YM.substr(4);
			}
			return '';
		}
		,objmouseover: function(e, titledata){
			var myTitle = titledata;
	        if(!myTitle)
	        	return;
	        var tooltip = "<div id='tooltip_top' style='position:absolute;border:0px solid #e6e6e6;background:#000;padding:5px;color:#e6e6e6;display:none;font-size: 22px;z-index:9999;'>"+ myTitle +"<\/div>"; //创建 div 元素 文字提示
	        jQuery("body").append(tooltip);    //把它追加到文档中
	        jQuery("#tooltip_top").css({
				                "top": (e.pageY+20) + "px",
				                "left": (e.pageX+10)  + "px"
			            	}).show("fast");      //设置x坐标和y坐标，并且显示
		}
		,objmouseout: function(){
			jQuery("#tooltip_top").remove();    //移除 
		}
		,objmousemove: function(e){
			jQuery("#tooltip_top").css({
                "top": (e.pageY+20) + "px",
                "left": (e.pageX+10)  + "px"
            });
		}
	}
});
vm.initChart();
$('#arrmonth').datetimepicker({
    language: "zh-CN",
    autoclose: 1,
    startView: 3,
    minView: 3,
    maxView: 4,
    endDate: new Date(),
    bootcssVer:3,
    format: "yyyy-mm"
});
</script>
</html>