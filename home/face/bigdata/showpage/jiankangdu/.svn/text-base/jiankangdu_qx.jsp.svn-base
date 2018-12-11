<%@page import="com.nrjh.framework.util.BspContextFactory,java.util.Calendar;"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/tags/web-ui" prefix="uap"%>
<!DOCTYPE html>
<html lang="en">
<!-- BEGIN HEAD -->
<head>
<meta http-equiv="Content-Type" charset="utf-8" />
<title>区县健康度评价</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<%
	String provinceCode = BspContextFactory.getComCode();
	String cityCode = request.getParameter("cityCode");//"43401";
	Calendar date = Calendar.getInstance();
	String year = request.getParameter("year");
	String month = request.getParameter("month");
	String countyCode = request.getParameter("countyCode");
	countyCode = countyCode==null?"":countyCode;
	cityCode = cityCode==null?"":cityCode;
	year = year==null||"".equals(year)?String.valueOf(date.get(Calendar.YEAR)):year;
	month = month==null||"".equals(month)?String.valueOf(date.get(Calendar.MONTH)+1):month;
	String ymDate = year+"-"+(month.length()==1?"0"+month:month);
%>
<script type="text/javascript">
var context="<%=request.getContextPath()%>";
var GlobalParma={
		provinceNo:'<%=provinceCode%>',
		cityCode:'<%=cityCode%>',
		countyCode:'<%=countyCode%>',
		queryYear:'<%=year%>',
		queryMonth:'<%=month%>',
		dataUrl:context+"/rest/bigdata/dataProvider/getData"
};
var CountyHealt = function(){
	this.charts = new Array();
};
CountyHealt.prototype = {
		addObserve : function(obj){
			this.charts.push(obj);
		},
		showChart : function(){
			$.each(this.charts,function(i,n){
				n.showChart();
			});
		}
}
var contyHealt = new CountyHealt();
function query(){
	if($("#countySelect").val()==""){
		$.dialog({
			 type:"alert",
			 content:"请选择区县",
			 autofocus: true
		 });
		return;
	}
	var arrmonth = new Date($("#arrmonth").val());
	GlobalParma.cityCode=$("#citySelect").val();
	GlobalParma.countyCode=$("#countySelect").val();
	GlobalParma.queryYear=arrmonth.getFullYear();
	GlobalParma.queryMonth=(arrmonth.getMonth()+1).toString();
	vm.initChart();
}
/*到货批次钻取到到货批次详情页面*/
function gotoDHPCXQ(batchNo){
	var year = GlobalParma.queryYear;
	var month = GlobalParma.queryMonth;
	var provinceNo = GlobalParma.provinceNo;
	var cityCode = GlobalParma.cityCode;
	var countyCode = GlobalParma.countyCode;
	window.parent.changeByChildren("/home/bigdata/showpage/daohuopc/daohuopicixq.jsp");
	window.location.href = context+"/bigdata/showpage/daohuopc/daohuopicixq.jsp?provinceNo="+provinceNo+"&year="+year+"&month="+month+"&bidBatchNo="+batchNo+"&cityCode="+cityCode+"&countyCode="+countyCode;
}
</script>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<uap:asset path="font-awesome.css,form.css" />
<uap:mediacss path="home/style.css" />
<!-- END GLOBAL MANDATORY STYLES -->
<uap:asset path="jquery.js,echarts.min.js"/>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>
	<div class="main" id="totalPageDiv">
		<div class="filter_box"><b>筛选条件</b>
			<span class="main_box">
				  <input type="text"  class="select_box" id="arrmonth" value="<%=ymDate %>" readonly="readonly" placeholder="年月"/> 
			</span>
			<uap:orgSelect id="citySelect" name="citySelect" styleClass="main_box"  childrenID='countySelect' area="CITY"/>
			<uap:orgSelect id="countySelect" name="countySelect" styleClass="main_box" isLink="true" parentID='citySelect' area="COUNTY"/>
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
		
		<div class="qx_con">
			<div class="c_l">
				<div class="gzpj lstj">
					<h2>历史故障情况统计</h2>
					<p id="malfunctionHistoryStat"></p>					
				</div>
				
				<div class="e_left">
					<div class="b_con">							
						<h2>到货批次健康评价<!-- <b><a href="#">导出</a></b> --></h2>
											
						<div class="ntbox">
							<ul class="ctbcon ntu">
								<li><span>到货批次号</span><span>供应商</span><span>型号</span><span>累计安装数</span><span>当月运行数</span><span>当月故障数</span><span>累计故障数</span><span>当月故障率</span><span>累计故障率</span><span>健康度</span>									
								</li>
							</ul>
							<ul class="ctablelist nboxb">
								<li v-for="data in dhpcjkpjList"><span class="text_left" @mouseover="objmouseover($event,data.arriveBatchNo)" @mouseout="objmouseout" @mousemove="objmousemove" v-html="data.arriveBatchNo"></span><span class="text_left" @mouseover="objmouseover($event,data.SUPLLIER_NAME)" @mouseout="objmouseout" @mousemove="objmousemove" v-text="data.SUPLLIER_NAME||'-'"></span><span class="text_left" @mouseover="objmouseover($event,data.MODEL_NAME)" @mouseout="objmouseout" @mousemove="objmousemove" v-text="data.MODEL_NAME||'-'"></span><span class="number_right" v-text="data.INST_TOTAL_QTY||'-'"></span><span class="number_right" v-text="data.CUR_MONTH_RQTY||'-'"></span><span class="number_right" v-text="data.CUR_MONTH_FQTY||'-'"></span><span class="number_right" v-text="data.FAULT_TOTAL_QTY||'-'"></span><span class="number_right" v-text="data.FR||'-'"></span><span class="number_right" v-text="data.CFR||'-'"></span><span class="number_right" v-text="data.HR||'-'"></span></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			
			
			<div class="jkdpj qxpj">
				<h2>健康度评价</h2>					
				<div class="jkdpjbox b2">
					
			      	<ul style="display: block;">
				      	<li>
				      		<h3 id="healthWeightAnalyse"></h3>
					    </li>
					    <li>
				      		<h3 id="malfunctionDistribute"></h3>
					    </li>
			    	</ul>		    
			   					   
			    </div>					
			</div>					
		</div>			
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<uap:asset path="vue.min.js,form.js,ajaxsubmit.js" />
	<script  type="text/javascript" src="<uap:ui js='bigdata/malfunctionHistoryStat.js'/>"></script>
	<script  type="text/javascript" src="<uap:ui js='bigdata/healthEvaluate.js'/>"></script>
	<script  type="text/javascript" src="<uap:ui js='bigdata/malfunctionDistribute.js'/>"></script>
	<script  type="text/javascript" src="<uap:ui js='bigdata/healthWeightAnalyse.js'/>"></script>
	<script  type="text/javascript" src="<uap:ui js='bigdata/listDataGetter.js'/>"></script>
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
if(GlobalParma.countyCode!=""){
	$("#countySelect").val(GlobalParma.countyCode).change();
}else{
	$("#countySelect").change();
	GlobalParma.countyCode = $("#countySelect").val();
}
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
		dhpcjkpjList:[]/*到货批次健康评价列表数据*/
	},
	computed:{
		listDataGetter:function(){return new ListDataGetter();},
		malfunctionHistoryStat:function(){return new MalfunctionHistoryStat($("#malfunctionHistoryStat")[0],'qxjkd_lsgzqktj');},
		malfunctionDistribute: function(){return new MalfunctionDistribute($("#malfunctionDistribute")[0],'qxjkd_gzlxfb');},
		healthWeightAnalyse:function(){return new HealthWeightAnalyse($("#healthWeightAnalyse")[0],'healthWeight');}
	},
	mounted: function(){
		contyHealt.addObserve(this.malfunctionHistoryStat);
		contyHealt.addObserve(this.malfunctionDistribute);
		contyHealt.addObserve(this.healthWeightAnalyse);
	},
	methods:{
		initChart:function(){
			this.globalParam = GlobalParma;		
			contyHealt.showChart();
			this.getTopInfo();
			this.getDhpcjkpjList();
		},
		getTopInfo:function(){
			var topParam1={/*顶端数据获取参数*/
				"_TAGNO":"qxjkd_top_1",
				year:this.globalParam.queryYear,
				month:this.globalParam.queryMonth,
				countyCode:this.globalParam.countyCode
			};
			var month = this.globalParam.queryMonth;
			if(month<10) month='0'+month;
			var topParam2={/*顶端数据获取参数*/
				"_TAGNO":"dsjkd_top_2",
				ym:this.globalParam.queryYear+""+month,
				cityCode:this.globalParam.countyCode
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
		getDhpcjkpjList:function(){
			var month = this.globalParam.queryMonth;
			if(month<10) month='0'+month;
			var dhpcjkpjParam={
				"_TAGNO":"qxdhpcjkpj_list",
				date:this.globalParam.queryYear+"-"+month+"-01",
				countyCode:this.globalParam.countyCode
			};
			var t_this = this;
			this.listDataGetter.getDatas(this.globalParam.dataUrl,dhpcjkpjParam,function(rslt){
				var rsltList = rslt.data;
				if(rsltList!=null&&rsltList.length>0){
					for(var i=0;i<rsltList.length;i++){
						rsltList[i].arriveBatchNo = "<a herf='#' class='table_a_link' onclick='gotoDHPCXQ(\""+rsltList[i].ARRIVE_BATCH_NO+"\")'>"+rsltList[i].ARRIVE_BATCH_NO+"</a>";
					}
				}
				t_this.dhpcjkpjList = rsltList;
			});
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