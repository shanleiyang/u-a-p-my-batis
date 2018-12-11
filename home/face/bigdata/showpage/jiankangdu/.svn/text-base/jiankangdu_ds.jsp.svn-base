<%@page import="com.nrjh.framework.util.BspContextFactory,java.util.Calendar;"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/tags/web-ui" prefix="uap"%>
<!DOCTYPE html>
<html lang="en">
<!-- BEGIN HEAD -->
<head>
<meta http-equiv="Content-Type" charset="utf-8" />
<title>地市健康度评价</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<%
	String provinceCode = BspContextFactory.getComCode();
	Calendar date = Calendar.getInstance();
	String year = request.getParameter("year");
	String month = request.getParameter("month");
	String cityCode = request.getParameter("cityCode");
	cityCode = cityCode==null?"":cityCode;
	year = year==null||"".equals(year)?String.valueOf(date.get(Calendar.YEAR)):year;
	month = month==null||"".equals(month)?String.valueOf(date.get(Calendar.MONTH)+1):month;
	String ymDate = year+"-"+(month.length()==1?"0"+month:month);
%>
<script type="text/javascript">
var skincontext = "<uap:skincontext />";
var context="<%=request.getContextPath()%>";
var GlobalParma={
		provinceNo:'<%=provinceCode%>',
		cityCode:'<%=cityCode%>',//第一次初始化时，页面加载完成后获取第一个单位
		queryYear:'<%=year%>',
		queryMonth:'<%=month%>',
		dataUrl:context+"/rest/bigdata/dataProvider/getData",
		mapType:'<%=cityCode%>',//地图类型
		mapPath:""//地图js路径
};
var CityHealt = function(){
	this.charts = new Array();
};
CityHealt.prototype = {
		addObserve : function(obj){
			this.charts.push(obj);
		},
		showChart : function(){
			$.each(this.charts,function(i,n){
				n.showChart();
			});
		}
}
var cityHealt = new CityHealt();
//地图点击跳转
function changeByMap(countyCode){
	var year = GlobalParma.queryYear;
	var month = GlobalParma.queryMonth;
	window.parent.changeByChildren("/home/bigdata/showpage/jiankangdu/jiankangdu_qx.jsp");
	window.location.href = context+"/bigdata/showpage/jiankangdu/jiankangdu_qx.jsp?cityCode="+GlobalParma.cityCode+"&countyCode="+countyCode+"&year="+year+"&month="+month;
}
function query(){
	var arrmonth = new Date($("#arrmonth").val());
	GlobalParma.cityCode=$("#citySelect").val();
	GlobalParma.queryYear=arrmonth.getFullYear();
	GlobalParma.mapType=GlobalParma.cityCode;
	GlobalParma.mapPath="<uap:skincontext />" + "/js/map/country/"+GlobalParma.provinceNo+"/"+GlobalParma.provinceNo+"_"+GlobalParma.cityCode+".js";
	GlobalParma.queryMonth=(arrmonth.getMonth()+1).toString();
	vm.initChart();
}
</script>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<uap:asset path="font-awesome.css,form.css" />
<uap:mediacss path="home/style.css" />
<!-- END GLOBAL MANDATORY STYLES -->
<uap:asset path="jquery.js,echarts.min.js,/map/bigdatamap.js,toolkit.js"/>
<script src="<uap:skincontext/>/js/map/maplabel/<%=provinceCode %>_label.js"></script>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>
	<div class="main" id="totalPageDiv">
		<div class="filter_box"><b>筛选条件</b>
			<span class="main_box">
				  <input type="text"  class="select_box" id="arrmonth" value="<%=ymDate %>" readonly="readonly" placeholder="年月"/> 
			</span>
			<uap:orgSelect id="citySelect" name="citySelect" styleClass="main_box" area="CITY" />
			<button class="se_btn"  v-on:click="query"></button>			
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
		
		<div class="ds_con">
			<div class="c_l map_cl">
				<div class="dsjkdt">
					<div id="healcitymap" ></div>
				</div>
				
				<div class="dsjkbox" v-bind:class="{dsjkbox_dl: malfunc_show || healthinfo_show}">
					<dl>
						<dt><h3 v-on:click="toggleInfoShow"><a href="javascript:;">单位健康度排名LAST10</a></h3>
						</dt>
						<div v-show="healthinfo_show" style="display:none;">
				      		<h2><i>单位</i><i>健康度</i><i>排名</i></h2>
					      	<dd id="healthInfoLi">					      		
						     	<p v-for="data in healthInfoList"><i @mouseover="objmouseover($event,data.NAME)" @mouseout="objmouseout" @mousemove="objmousemove" v-text="data.NAME||'-'"></i><i @mouseover="objmouseover($event,data.VALUE)" @mouseout="objmouseout" @mousemove="objmousemove" v-text="data.VALUE||'-'"></i><i v-text="data.RANK_NO||'-'"></i></p>
						    </dd>			  	  
						</div>
			    	</dl>
			    	<dl>
			    		<dt>
			    			<h3 v-on:click="toggleFuncShow"><a href="javascript:;">单位故障率排名TOP10</a></h3>
			    		</dt>
			    		<div v-show="malfunc_show" style="display:none;">
				      		<h2><i>单位</i><i>故障率(%)</i><i>排名</i></h2>
					      	<dd id="malfunctionLi">					      		
						     	<p v-for="data in malfunctionList"><i @mouseover="objmouseover($event,data.NAME)" @mouseout="objmouseout" @mousemove="objmousemove" v-text="data.NAME||'-'"></i><i @mouseover="objmouseover($event,data.VALUE)" @mouseout="objmouseout" @mousemove="objmousemove" v-text="data.VALUE||'-'"></i><i v-text="data.RANK_NO||'-'"></i></p>
						    </dd>			  	  
			    		</div>
			    	</dl>
				</div>			
			</div>
			
			<div class="jkdpj dsboxt">
				<h2>健康度评价</h2>					
				<div class="jkdpjbox b2 dsjkmin">						
			      	<ul style="display: block;">
				      	<li>
				      		<h3 id="healthWeightAnalyse"></h3>
					    </li>
					    <li>
				      		<h3 id="malfunctionDistribute"></h3>
					    </li>
			    	</ul>		    				   					   
			    </div>
			    
			    <div class="gzpj dsgztj">
				<h2>历史故障情况统计</h2>
				<p  id="malfunctionHistoryStat"></p>					
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
	<script  type="text/javascript" src="<uap:ui js='bigdata/healthWeightAnalyse2.js'/>"></script>
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
GlobalParma.mapPath = "<uap:skincontext />" + "/js/map/country/"+GlobalParma.provinceNo+"/"+GlobalParma.provinceNo+"_"+GlobalParma.cityCode+".js";;
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
		healthInfoList:[],/*健康度排名列表数据*/
		malfunctionList:[],/*故障率排名列表数据*/
		healthinfo_show: false, /*单位健康度排名LAST10 显示标志*/
		malfunc_show: false /*单位故障率排名TOP10 显示标志*/
	},
	computed:{
		listDataGetter:function(){return new ListDataGetter();},
		malfunctionHistoryStat:function(){return new MalfunctionHistoryStat($("#malfunctionHistoryStat")[0],'dsjkd_lsgzqktj');},
		malfunctionDistribute: function(){return new MalfunctionDistribute($("#malfunctionDistribute")[0],'dsjkd_gzlxfb');},
		healthWeightAnalyse:function(){return new HealthWeightAnalyse($("#healthWeightAnalyse")[0],'healthWeight');},
		bigdatamap:function(){
			return new BigDataMap($('#healcitymap')[0],"dsjkd_map");//从全局变量中获取mapType
		}
	},
	mounted: function(){
		cityHealt.addObserve(this.bigdatamap);
		cityHealt.addObserve(this.malfunctionHistoryStat);
		cityHealt.addObserve(this.malfunctionDistribute);
		cityHealt.addObserve(this.healthWeightAnalyse);
	},
	methods:{
		initChart:function(){
			this.globalParam = GlobalParma;
			cityHealt.showChart();
			this.getTopInfo();
			this.getHealthInfoList();
			this.getMalfunctionList();
		},
		getTopInfo:function(){
			var topParam1={/*顶端数据获取参数*/
				"_TAGNO":"dsjkd_top_1",
				year:this.globalParam.queryYear,
				month:this.globalParam.queryMonth,
				cityCode:this.globalParam.cityCode
			};
			var month = this.globalParam.queryMonth;
			if(month<10) month='0'+month;
			var topParam2={/*顶端数据获取参数*/
				"_TAGNO":"dsjkd_top_2",
				ym:this.globalParam.queryYear+""+month,
				cityCode:this.globalParam.cityCode
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
		getHealthInfoList:function(){
			var healthInfoParam={/*健康度排名列表参数*/
				"_TAGNO":"dsjkd_jkdList",
				year:this.globalParam.queryYear,
				month:this.globalParam.queryMonth,
				cityCode:this.globalParam.cityCode
			};
			var t_this = this;
			this.listDataGetter.getDatas(this.globalParam.dataUrl,healthInfoParam,function(rslt){
				t_this.healthInfoList = rslt.data;
			});
		},
		getMalfunctionList:function(){
			var malfunctionParam={/*故障率排名列表参数*/
				"_TAGNO":"dsjkd_gzlList",
				year:this.globalParam.queryYear,
				month:this.globalParam.queryMonth,
				cityCode:this.globalParam.cityCode
			};
			var t_this = this;
			this.listDataGetter.getDatas(this.globalParam.dataUrl,malfunctionParam,function(rslt){
				t_this.malfunctionList = rslt.data;
			});
		},
		toggleInfoShow: function(){
			this.healthinfo_show = !this.healthinfo_show;
		},
		toggleFuncShow: function(){
			this.malfunc_show = !this.malfunc_show;
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
</script>
</html>