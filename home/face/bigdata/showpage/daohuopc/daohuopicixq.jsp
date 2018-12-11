<%@page import="com.nrjh.framework.util.BspContextFactory,java.util.Calendar;"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/tags/web-ui" prefix="uap"%>
<!DOCTYPE html>
<html lang="en">
<!-- BEGIN HEAD -->
<head>
<meta http-equiv="Content-Type" charset="utf-8" />
<title>到货批次综合查询</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<%
	String provinceCode = BspContextFactory.getComCode();
	Calendar date = Calendar.getInstance();
	String year = request.getParameter("year");
	String month = request.getParameter("month");
	year = year==null||"".equals(year)?String.valueOf(date.get(Calendar.YEAR)):year;
	month = month==null||"".equals(month)?String.valueOf(date.get(Calendar.MONTH)+1):month;
	String ymDate = year+"-"+(month.length()==1?"0"+month:month);
	String cityCode = request.getParameter("cityCode");
	cityCode = cityCode==null?"":cityCode;
	String countyCode = request.getParameter("countyCode");
	countyCode = countyCode==null?"":countyCode;
	String bidBatchNo = request.getParameter("bidBatchNo");
	bidBatchNo = bidBatchNo==null?"":bidBatchNo;
	String orgNo = request.getParameter("orgNo");
	orgNo = orgNo==null?"":orgNo;
	if("".equals(orgNo)){
		if("".equals(countyCode)){
			if("".equals(cityCode)){
				orgNo = provinceCode;
			}else{
				orgNo = cityCode;
			}
		}else{
			orgNo = countyCode;
		}
	}else{
		//orgtype-orgNo
		String[] orgNos =orgNo.split("-");
		String orgtype = orgNos[0];
		if("1".equals(orgtype)){
			provinceCode = orgNos[1];
		}else if("2".equals(orgtype)){
			cityCode = orgNos[1];
		}else if("3".equals(orgtype)){
			countyCode = orgNos[1];
		}
		orgNo = orgNos[1];
	}
%>
<script type="text/javascript">
var context="<%=request.getContextPath()%>";
var GlobalParma={
		provinceNo:'<%=provinceCode%>',
		cityCode:'<%=cityCode%>',//第一次初始化时，页面加载完成后获取第一个单位
		countyCode:'<%=countyCode%>',
		orgNo:'<%=orgNo%>',
		queryYear:'<%=year%>',
		queryMonth:'<%=month%>',
		bidBatchNo:'<%=bidBatchNo%>',
		dataUrl:context+"/rest/bigdata/dataProvider/getData"
};
var CityFault = function(){
	this.charts = new Array();
};
CityFault.prototype = {
		addObserve : function(obj){
			this.charts.push(obj);
		},
		showChart : function(){
			$.each(this.charts,function(i,n){
				n.showChart();
			});
		}
}
var cityFault = new CityFault();
function query(){
	GlobalParma.bidBatchNo=$("#bidBatchNo").val();
	if(GlobalParma.bidBatchNo==""){
		$.dialog({
			 type:"alert",
			 content:"请输入到货批次号",
			 autofocus: true
		 });
		return;
	}
	var arrmonth = new Date($("#arrmonth").val());
	var orgtype = $("#orgno").attr("orgtype");
	var orgNo = $("#orgno").val();
	GlobalParma.orgNo = orgNo;
	if(orgtype=='1'){
		GlobalParma.provinceNo = orgNo;
	}else if(orgtype=='2'){
		GlobalParma.cityCode = orgNo;
	}else if(orgtype=='3'){
		GlobalParma.countyCode = orgNo;
	}
	GlobalParma.queryYear=arrmonth.getFullYear();
	GlobalParma.queryMonth=(arrmonth.getMonth()+1).toString();
	vm.initChart();
}
</script>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<uap:asset path="font-awesome.css,form.css,ztree/ztree.css" />
<uap:mediacss path="home/style.css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<uap:asset path="jquery.js,echarts.min.js,jquery.ztree.all.min.js"/>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>
	<div class="main" id="totalPageDiv">
		<div class="filter_box"><b>筛选条件</b>	
			<span class="main_box">
				<input type="text"  class="select_box" id="arrmonth" value="<%=ymDate %>" readonly="readonly" placeholder="年月"/> 
			</span>
			<span class="main_box">
				<input type="hidden" id="orgno" value="<%=orgNo %>" orgtype="" />
				<input type="text"  class="select_box" id="orgname" value="" readonly="readonly" placeholder="单位"/> 
				<ul id="orgtree" class="ztree ztree_extend"></ul>
			</span>
			<span class="main_box">
				<input type="text" name="bidBatchNo" id="bidBatchNo" value="<%=bidBatchNo %>" class="select_box" placeholder="请输入到货批次号" />
			</span>
			<button class="se_btn" v-on:click="query"></button>			
			
			<div class="dh_nav"><a href="javascript:;" v-on:click="showMode('chart')" v-bind:class="{on: modeName=='chart'}">图表模式</a><a href="javascript:;" v-on:click="showMode('list')" v-bind:class="{on: modeName=='list'}">列表模式</a></div>	
		</div>
		
		<div class="dhpcbox">
			<h4 ><i>供应商：<span  v-text='batchBaseInfo.SUPLLIER_NAME'></span></i><i>型号: <span  v-text='batchBaseInfo.MODEL_NAME'></span></i><i>到货日期: <span  v-text='batchBaseInfo.ARRIVE_DATE'></span></i><i>到货数量: <span  v-text='batchBaseInfo.ARR_QTY'></span></i></h4>			
			<div class="dw_side">
				<ul>
					<li><span>累计安装量<b>（个）</b></span><i v-for="data in topInfo.INST_TOTAL_QTY" v-text="data"></i></li>
					<li><span>当月运行量<b>（个）</b></span><i v-for="data in topInfo.CUR_MONTH_RQTY" v-text="data"></i></li>
					<li><span>当月故障数<b>（个）</b></span><i v-for="data in topInfo.CUR_MONTH_FQTY" v-text="data"></i></li>
					<li><span>累计故障数<b>（个）</b></span><i v-for="data in topInfo.FAULT_TOTAL_QTY" v-text="data"></i></li>
					<li><span>当月故障率<b>（%）</b></span><i v-for="data in topInfo.FR" v-text="data"></i></li>
					<li><span>累计故障率<b>（%）</b></span><i v-for="data in topInfo.CFR" v-text="data"></i></li>					
				</ul>
			</div>			
		</div>
		
		<div class="dhpc_con" v-show="modeName=='chart'">
			<div class="c_l">
				<div class="jkdpj dspcboxt">
					<h2>故障类型故障分析</h2>					
					<div class="jkdpjbox b2 dspcjkmin">						
				      	<ul style="display: block;">
					      
						    <li>
					      		<h3 id="malfunctionDistribute"></h3>
						    </li>
						    
						    <li>
						    	<!-- <h3 ><b id='healthRate' style="width:90px;height:90px;margin-left:-6px;margin-top:-6px;">16.88</b></h3> -->
						    	<h3 ><b v-text="jkdData"></b></h3>
						    	<h4>故障率预测</h4>
						    	<h5>
						    		<template v-for="data in gzlycData">
								     <i v-html="splitYM(data.YM)"></i><b v-text="data.FR_PRE+'%'"></b>
								    </template>
						    	</h5>
						    </li>
				    	</ul>		    				   					   
				   </div> 
				</div>
				
				<div class="dhpc_left pc_main">
					<div class="dh_con dhlistbox">							
						<h2>到货批次运行分布<!-- <b><a href="#">导出</a></b> --></h2>												
						<div>
							<ul class="dhbcon listdh">
								<template v-if="globalParam.provinceNo=='13102'">
									<li class="listli"><span>管理单位</span><span>异常类型</span><span>健康度</span></li>	
								</template>							 
								<template v-else>
									<li><span>管理单位</span><span>一类故障</span><span>二类故障</span><span>三类故障</span><span>健康度</span></li>
								</template>	
							</ul>
							<ul class="dhablelist dhcmain">	
								<template v-if="globalParam.provinceNo=='13102'">
									<li class="listli" v-for="data in pcyxfbList"><span class="text_left" @mouseover="objmouseover($event,data.ORG_NAME)" @mouseout="objmouseout" @mousemove="objmousemove" v-text="data.ORG_NAME||'-'"></span><span class="text_left" @mouseover="objmouseover($event,data.THIRD_TYPE_NAME)" @mouseout="objmouseout" @mousemove="objmousemove" v-text="data.THIRD_TYPE_NAME||'-'"></span><span class="number_right" v-text="data.HR||'-'"></span></li>
								</template>							 
								<template v-else>
									<li v-for="data in pcyxfbList"><span class="text_left" @mouseover="objmouseover($event,data.ORG_NAME)" @mouseout="objmouseout" @mousemove="objmousemove" v-text="data.ORG_NAME||'-'"></span><span class="text_left" @mouseover="objmouseover($event,data.FIRST_TYPE_NAME)" @mouseout="objmouseout" @mousemove="objmousemove" v-text="data.FIRST_TYPE_NAME||'-'"></span><span class="text_left" @mouseover="objmouseover($event,data.SECOND_TYPE_NAME)" @mouseout="objmouseout" @mousemove="objmousemove" v-text="data.SECOND_TYPE_NAME||'-'"></span><span class="text_left" @mouseover="objmouseover($event,data.THIRD_TYPE_NAME)" @mouseout="objmouseout" @mousemove="objmousemove" v-text="data.THIRD_TYPE_NAME||'-'"></span><span class="number_right" v-text="data.HR||'-'"></span></li>
								</template>	
																																	
							</ul>
						</div>
					</div>
				</div>										
			</div>
			
			<div class="c_r">
				<div class="gzpj pcgzt">
					<h2>到货批次故障率趋势</h2>
					<p id="dhpcgzlqs"></p>					
				</div>
				<div class="gzpj pcgzt">
					<h2>运行时间故障分布</h2>
					<p id="yxsjgzfb"></p>					
				</div>
			</div>
		</div>
		
		<div class="dhpccx_con" v-show="modeName=='list'" style="display:none;">
			<div class="c_l">			
				<div class="dhpc_left dh_left">
					<div class="dh_con dhmen">							
						<h2>到货批次历史评价及预测<!-- <b><a href="#">导出</a></b> --></h2>
						<ul class="dhbcon dhico">
							<template v-if="globalParam.provinceNo=='13102'">
								<li class="listli"><span>管理单位</span><span>异常类型</span><span>年月</span><span class="rows">当月<br />故障数</span><span class="rows">累计<br />故障数</span><span>当月<br />故障率</span><span>累计<br />故障率</span><span>当月<br />运行数</span><span>累计<br />安装数</span><span class="secondline month" v-text="gzycMonth.month1+'月'"></span><span class="secondline month" v-text="gzycMonth.month2+'月'"></span><span class="secondline month" v-text="gzycMonth.month3+'月'"></span><b>故障率预测</b></li>
							</template>
							<template v-else>
								<li><span>管理单位</span><span>一类故障</span><span>二类故障</span><span>三类故障</span><span>年月</span><span>当月<br />故障数</span><span>累计<br />故障数</span><span>当月<br />故障率</span><span>累计<br />故障率</span><span>当月<br />运行数</span><span>累计<br />安装数</span><span v-text="gzycMonth.month1+'月'"></span><span v-text="gzycMonth.month2+'月'"></span><span v-text="gzycMonth.month3+'月'"></span><b>故障率预测</b></li>	
							</template>
						</ul>					
						<div>
							<ul class="dhablelist dhlb">
								<template v-if="globalParam.provinceNo=='13102'">
									<li class="listli" v-for="data in lspjList"><span class="text_left" v-text="data.ORG_NAME||'-'"></span><span class="text_left" v-text="data.THIRD_TYPE_NAME||'-'"></span><span class="text_left" v-text="data.YM||'-'"></span><span class="number_right" v-text="data.CUR_MONTH_FQTY||'-'"></span><span class="number_right" v-text="data.FAULT_TOTAL_QTY||'-'"></span><span class="number_right" v-text="data.FR||'-'"></span><span class="number_right" v-text="data.CFR||'-'"></span><span class="number_right" v-text="data.CUR_MONTH_RQTY||'-'"></span><span class="number_right" v-text="data.INST_TOTAL_QTY||'-'"></span><span class="number_right month" v-text="data.VALUE1||'-'"></span><span class="number_right month" v-text="data.VALUE2||'-'"></span><span class="number_right month" v-text="data.VALUE3||'-'"></span></li>
								</template>
								<template v-else>
									<li v-for="data in lspjList"><span class="text_left" @mouseover="objmouseover($event,data.ORG_NAME)" @mouseout="objmouseout" @mousemove="objmousemove"  v-text="data.ORG_NAME||'-'"></span><span class="text_left" @mouseover="objmouseover($event,data.FIRST_TYPE_NAME)" @mouseout="objmouseout" @mousemove="objmousemove"  v-text="data.FIRST_TYPE_NAME||'-'"></span><span class="text_left" @mouseover="objmouseover($event,data.SECOND_TYPE_NAME)" @mouseout="objmouseout" @mousemove="objmousemove"  v-text="data.SECOND_TYPE_NAME||'-'"></span><span class="text_left" @mouseover="objmouseover($event,data.THIRD_TYPE_NAME)" @mouseout="objmouseout" @mousemove="objmousemove"  v-text="data.THIRD_TYPE_NAME||'-'"></span><span class="text_left" v-text="data.YM||'-'"></span><span class="number_right" v-text="data.CUR_MONTH_FQTY||'-'"></span><span class="number_right" v-text="data.FAULT_TOTAL_QTY||'-'"></span><span class="number_right" v-text="data.FR||'-'"></span><span class="number_right" v-text="data.CFR||'-'"></span><span class="number_right" v-text="data.CUR_MONTH_RQTY||'-'"></span><span class="number_right" v-text="data.INST_TOTAL_QTY||'-'"></span><span class="number_right" v-text="data.VALUE1||'-'"></span><span class="number_right" v-text="data.VALUE2||'-'"></span><span class="number_right" v-text="data.VALUE3||'-'"></span></li>
								</template>
							</ul>								
						</div>
					</div>
				</div>						
			</div>
		</div>
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<uap:asset path="vue.min.js,form.js,ajaxsubmit.js" />
	<script  type="text/javascript" src="<uap:ui js='bigdata/malfunctionHistoryStat.js'/>"></script>
	<script  type="text/javascript" src="<uap:ui js='bigdata/malfunctionDistribute2.js'/>"></script>
	<script  type="text/javascript" src="<uap:ui js='bigdata/listDataGetter.js'/>"></script>
	<script  type="text/javascript" src="<uap:ui js='bigdata/orgtree.js'/>"></script>
	<!--[if lt IE 9]>
	<script src="<uap:ui js='excanvas.min.js'/>"></script>
	<script src="<uap:ui js='respond.min.js'/>"></script>  
	<![endif]-->
</body>
<!-- END BODY -->
<script type="text/javascript">	
if(GlobalParma.cityCode!=""){
	$("#citySelect").val(GlobalParma.cityCode).change();
}else{
	$("#citySelect").change();
	GlobalParma.cityCode = $("#citySelect").val();
	GlobalParma.mapType = $("#citySelect").val();
}
var vm = new Vue({
	el:'#totalPageDiv',
	data:{
		globalParam:GlobalParma,/*公共参数*/
		tagNos:{/*匹配sql语句的tagNo*/
			batchBaseInfo:'dhpcxq_pcjbxx',/*到货批次详情批次基本信息*/
			topData:'dhpcxq_top',/*顶端数据tagNo*/
			malfunctionDistribute:'dhpcxq_gzlxfb',/*故障类型分布tagNo*/
			gzlyc:'dhpcxq_gzyc',/*预测故障率tagNo*/
			jkd:'dhpcxq_jkd',/*健康度TagNo*/
			dhpcgzlqs:'dhpcxq_lsgzqktj',/*到货批次故障率趋势TagNo*/
			yxsjgzfb:'dhpcxq_yxsjgzfb',/*运行时间故障分布TagNo*/
			pcyxfbList:'dhpcxq_pcyxfb',/*批次运行分布列表TagNo*/
			lspjList:'dhpcxq_lspjList'/*到货批次历史评价及预测列表TagNo*/
		},
		modeName:'chart',
		topInfo:{
			INST_TOTAL_QTY:'0',
			CUR_MONTH_RQTY:'0',
			CUR_MONTH_FQTY:'0',
			FAULT_TOTAL_QTY:'0',
			FR:'0',
			CFR:'0'
			},/*顶端指标数据*/
		batchBaseInfo:{
			SUPLLIER_NAME:'',
			MODEL_NAME:'',
			ARRIVE_DATE:'',
			ARR_QTY:''
		},/*顶端批次基本信息*/
		gzlycData:[],/*故障率预测数据*/
		jkdData:'',/*健康度数据*/
		pcyxfbList:[],/*批次运行分布列表数据*/
		lspjList:[]/*到货批次历史评价及预测信息列表数据*/
	},
	computed:{
		listDataGetter:function(){return new ListDataGetter();},
		malfunctionDistribute: function(){return new MalfunctionDistribute($("#malfunctionDistribute")[0],this.tagNos.malfunctionDistribute);},
		dhpcgzlqs:function(){return new MalfunctionHistoryStat($("#dhpcgzlqs")[0],this.tagNos.dhpcgzlqs);},
		yxsjgzfb:function(){return new MalfunctionHistoryStat($("#yxsjgzfb")[0],this.tagNos.yxsjgzfb);},
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
		cityFault.addObserve(this.malfunctionDistribute);
		cityFault.addObserve(this.dhpcgzlqs);
		cityFault.addObserve(this.yxsjgzfb);
	},
	methods:{
		initChart:function(){
			this.globalParam = GlobalParma;
			cityFault.showChart();
			this.getTopInfo();
			this.getBatchBaseInfo();
			this.getGzlycData();
			this.getJkdData();
			this.getPcyxfbList();
			this.getLspjList();
		},
		showMode:function(mode){
			this.modeName = mode;
		},
		getTopInfo:function(){
			var topTagObj = {/*顶端数据获取参数*/
				"_TAGNO":this.tagNos.topData,
				year:this.globalParam.queryYear,
				month:this.globalParam.queryMonth,
				cityCode:this.globalParam.cityCode,
				countyCode:this.globalParam.countyCode,
				arriveBatchNo:this.globalParam.bidBatchNo
			};
			var t_this = this;
			this.listDataGetter.getDatas(this.globalParam.dataUrl,topTagObj,function(rslt){
				var rsltList = rslt.data;
				var rsltObj = {};
				if(rsltList!=null &&rsltList.length>0){
					rsltObj={'INST_TOTAL_QTY':String(rsltList[0]['INST_TOTAL_QTY']).split(''),
							'CUR_MONTH_RQTY':String(rsltList[0]['CUR_MONTH_RQTY']).split(''),
							'CUR_MONTH_FQTY':String(rsltList[0]['CUR_MONTH_FQTY']).split(''),
							'FAULT_TOTAL_QTY':String(rsltList[0]['FAULT_TOTAL_QTY']).split(''),
							'FR':String(rsltList[0]['FR']).split(''),
							'CFR':String(rsltList[0]['CFR']).split('')
					};
				}
				t_this.topInfo=rsltObj;
			});
		},
		getBatchBaseInfo:function(){/*批次基本信息*/
			var month = this.globalParam.queryMonth;
			if(month<10) month='0'+month;
			var topTagObj = {
					"_TAGNO":this.tagNos.batchBaseInfo,
					ym:this.globalParam.queryYear+month,
					orgNo:this.globalParam.countyCode||this.globalParam.cityCode||this.globalParam.provinceNo,
					arriveBatchNo:this.globalParam.bidBatchNo
				};
				var t_this = this;
				this.listDataGetter.getDatas(this.globalParam.dataUrl,topTagObj,function(rslt){
					var rsltList = rslt.data;
					var rsltObj = {};
					if(rsltList!=null &&rsltList.length>0){
						rsltObj={
								'SUPLLIER_NAME':rsltList[0]['SUPLLIER_NAME'],
								'MODEL_NAME':rsltList[0]['MODEL_NAME'],
								'ARRIVE_DATE':rsltList[0]['ARRIVE_DATE'],
								'ARR_QTY':rsltList[0]['ARR_QTY']
						};
					}
					t_this.batchBaseInfo=rsltObj;
				});
				
		},
		getGzlycData:function(){/*故障率预测数据*/
			var month = this.globalParam.queryMonth;
			if(month<10) month='0'+month;
			var gzlycTagObj = {
					"_TAGNO":this.tagNos.gzlyc,
					date:this.globalParam.queryYear+"-"+month+"-01",
					cityCode:this.globalParam.cityCode,
					countyCode:this.globalParam.countyCode,
					arriveBatchNo:this.globalParam.bidBatchNo
				};
			var t_this = this;
			this.listDataGetter.getDatas(this.globalParam.dataUrl,gzlycTagObj,function(rslt){
				t_this.gzlycData = rslt.data;
			});
		},
		getJkdData:function(){/*健康度数据*/
			var jkdTagObj = {
					"_TAGNO":this.tagNos.jkd,
					year:this.globalParam.queryYear,
					month:this.globalParam.queryMonth,
					cityCode:this.globalParam.cityCode,
					countyCode:this.globalParam.countyCode,
					arriveBatchNo:this.globalParam.bidBatchNo
				};
			var t_this = this;
				this.listDataGetter.getDatas(this.globalParam.dataUrl,jkdTagObj,function(rslt){
					var rsltList = rslt.data;
					var value = 0;
					if(rsltList != null && rsltList.length>0){
						value = rsltList[0].HR;
					}
					t_this.jkdData = value;
				});
		},
		getPcyxfbList:function(){/*批次运行分布列表*/
			var tagObj = {
					"_TAGNO":this.tagNos.pcyxfbList,
					year:this.globalParam.queryYear,
					month:this.globalParam.queryMonth,
					cityCode:this.globalParam.cityCode,
					countyCode:this.globalParam.countyCode,
					arriveBatchNo:this.globalParam.bidBatchNo
				};
			var t_this = this;
			this.listDataGetter.getDatas(this.globalParam.dataUrl,tagObj,function(rslt){
				t_this.pcyxfbList = rslt.data;
			});
		},
		getLspjList:function(){/*到货批次历史评价及预测列表*/
			var tagObj = {
					"_TAGNO":this.tagNos.lspjList,
					year:this.globalParam.queryYear,
					month:this.globalParam.queryMonth,
					cityCode:this.globalParam.cityCode,
					countyCode:this.globalParam.countyCode,
					arriveBatchNo:this.globalParam.bidBatchNo
				};
			var t_this = this;
			this.listDataGetter.getDatas(this.globalParam.dataUrl,tagObj,function(rslt){
				t_this.lspjList = rslt.data;
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
vm.initChart();

$('#orgtree').orgTree('orgno', 'orgname');

</script>
</html>