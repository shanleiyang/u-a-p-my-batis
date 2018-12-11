<%@page import="com.nrjh.framework.util.BspContextFactory,java.util.Calendar;"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/tags/web-ui" prefix="uap"%>
<!DOCTYPE html>
<html>
<!-- BEGIN HEAD -->
<head>
<meta http-equiv="Content-Type" charset="utf-8" />
<title>智能表精准更换分析与监测</title>
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
		//queryMonth:'8',
		dataUrl:context+"/rest/bigdata/dataProvider/getData",
		mapType:<%=provinceCode%>,
		mapPath:"<uap:skincontext />" + "/js/map/province/"+<%=provinceCode %>+".js"
};
var HunanBig = function(){
	this.charts = new Array();
};
HunanBig.prototype = {
		addObserve : function(obj){
			this.charts.push(obj);
		},
		showChart : function(){
			$.each(this.charts,function(i,n){
				try{
					n.showChart();
				}catch(err){
					console.log(err);
				}
			});
		}
}
var hunanBig = new HunanBig();
</script>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<uap:asset path="bigscreen/hunan/big.css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<uap:asset path="jquery.js,echarts.min.js,/map/maplabel/43101_label.js,/map/bigdatamap_2pie.js"/>

</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>
<div id="pageContent">
	<div class="top">
		<h1><i>智能表精准更换分析与监测</i></h1>
		<div class="dpm_side">
		<ul>
			<li><span>供应商数量<b>（个）</b></span><i v-for="data in topInfo.SUPPLY_NUM" v-text="data"></i></li>
			<li><span>型号数量<b>（个）</b></span><i v-for="data in topInfo.MODEL_NUM" v-text="data"></i></li>
			<li><span>到货批次数<b>（个）</b></span><i v-for="data in topInfo.BATCH_NUM" v-text="data"></i></li>
			<li><span>累计安装量<b>（个）</b></span><i v-for="data in topInfo.INST_TOTAL_QTY" v-text="data"></i></li>
			<li><span>当月运行量<b>（个）</b></span><i v-for="data in topInfo.CUR_MONTH_RQTY" v-text="data"></i></li>
			<li><span>当月故障数<b>（个）</b></span><i v-for="data in topInfo.CUR_MONTH_FQTY" v-text="data"></i></li>
			<li><span>累计故障数<b>（个）</b></span><i v-for="data in topInfo.FAULT_TOTAL_QTY" v-text="data"></i></li>
			<li><span>健康度<b></b></span><i v-for="data in topInfo.HR" v-text="data"></i></li>
			<li><span>故障率<b>（%）</b></span><i v-for="data in topInfo.FR" v-text="data"></i></li>
		</ul>
		</div>	
		<div class="time">
			<span id="time3" v-text="getCurDate"></span>
			<div class="c_t" v-text="getCurTime"></div>
		</div>
	</div>
		
	<div class="content">
		<div class="box1">
			<div class="map" id="provmap"></div>
			<div class="pmli">
				<ul>
					<li class="on"><a href="javascript:void(0)">健康度</a></li>
					<li><a href="javascript:void(0)">故障率</a></li>
				</ul>
			</div>
		</div>
		
		<div class="box2">
			<ul class="list_a">
				<li>
					<h2>预测准确率</h2>
					<span id="predRate"></span>
				</li>
				<li>
					<h2>故障率健康度一致性</h2>
					<span id="bdFrHrConsistency"></span>
				</li>
			</ul>
		</div>
		
		<div class="box2">
			<ul class="list_b">
				<li>
					<h2>单位健康度排名LAST10</h2>
					<div id="bdHrRank"></div>
				</li>
			</ul>
		</div>
		
		<div class="box2">
			<ul class="list_b">
				<li>
					<h2>单位故障率排名TOP10</h2>
					<div id="bdFrRank"></div>
				</li>
			</ul>
		</div>
		
		<div class="box2">
			<ul class="list_c">
				<li>
					<h2>健康度影响因素权重分布</h2>
					<p><span v-for="data in healthWeightData" ><font v-text="data.NAME"></font><i v-text="data.VALUE"></i></span></p>
				</li>
				<li>
					<h2>故障类型故障分布</h2>
					<h3 id="gzlxfb"></h3>
				</li>
			</ul>
		</div>
		
		<div class="box2">
			<ul class="list_e">
				<li>
					<h2>到货批次故障TOP5%</h2>
					<dl>
					<dt><span>批次号</span><span>管理<br />单位</span><span>累计<br />故障率</span><span>健康度</span><span v-text="gzycMonth.month1+'月'">月度</span><span v-text="gzycMonth.month2+'月'">季度</span><span v-text="gzycMonth.month3+'月'">半年</span><i>故障率预测</i></dt>
					<dd>
						<p v-for="data in dhpcgzList"><span class="text_left" @mouseover="objmouseover($event,data.ARRIVE_BATCH_NO)" @mouseout="objmouseout" @mousemove="objmousemove" v-text="data.ARRIVE_BATCH_NO||'-'"></span><span class="text_left" @mouseover="objmouseover($event,data.ORG_NAME)" @mouseout="objmouseout" @mousemove="objmousemove" v-text="data.ORG_NAME||'-'"></span><span class="number_right" v-text="data.CFR||'-'"></span><span class="number_right" v-text="data.HR||'-'"></span><span class="number_right" v-text="data.VALUE1||'-'"></span><span class="number_right" v-text="data.VALUE2||'-'"></span><span class="number_right" v-text="data.VALUE3||'-'"></span></p>
					</dd>
					
					</dl>
				</li>
			</ul>
		</div>
		
		<div class="box3">
			<ul class="list_d">
				<li>
					<h2>故障历史变化情况</h2>
					<!-- 运行年月故障历史趋势 -->
					<span id="gzlsbhqk_yxny"></span>
				</li>
				<li>
					<h2>故障历史变化情况</h2>
					<!-- 运行时长故障历史趋势 -->
					<span id="gzlsbhqk_yxsc"></span>
				</li>
			</ul>
		</div>
		
	</div>
</div>	
	<!-- END CONTAINER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<uap:asset path="toolkit.js,vue.min.js,form.js,ajaxsubmit.js" />
	<script  type="text/javascript" src="<uap:ui js='bigdata/listDataGetter.js'/>"></script>
	<script  type="text/javascript" src="<uap:ui js='bigdata/bdFrHrConsistency.js'/>"></script>
	<script  type="text/javascript" src="<uap:ui js='bigdata/bdHrRank.js'/>"></script>
	<script  type="text/javascript" src="<uap:ui js='bigdata/bdFrRank.js'/>"></script>
	<script  type="text/javascript" src="<uap:ui js='bigdata/bdMalfunctionDistribute.js'/>"></script>
	<script  type="text/javascript" src="<uap:ui js='bigdata/bdMalfunctionHistoryStat.js'/>"></script>
	<script  type="text/javascript" src="<uap:ui js='bigdata/bdPredRate.js'/>"></script>
	<!--[if lt IE 9]>
	<script src="<uap:ui js='excanvas.min.js'/>"></script>
	<script src="<uap:ui js='respond.min.js'/>"></script>  
	<![endif]-->
</body>
<!-- END BODY -->
<script type="text/javascript">
	var vmContent = new Vue({
		el:'#pageContent',
		data:{
			curTime: '<%=System.currentTimeMillis()%>' //当前时间:毫秒
			,globalParam:GlobalParma/*公共参数*/
			,tagNos:{/*匹配sql语句的tagNo*/
				bdFrHrConsistency:'bd_frhryzx'/*故障率健康度一致性*/
				,bdHrRank:'bd_jkdrank_l10'/*大屏单位健康度排名LAST10*/
				,bdFrRank:'bd_gzlrank_t10'/*大屏单位故障率排名TOP10*/
				,gzlxfb:'bd_gzlxfb'/*故障类型分布*/
				,gzlsbhqk_yxny:'bd_gzls_yxny'/*故障历史变化情况故障率趋势TagNo*/
				,gzlsbhqk_yxsc:'bd_gzls_yxsc'/*故障历史变化情况运行时间故障分布TagNo*/
				,predRate:'bd_predRate'/*预测准确率*/
			}
			,topInfo:{
				HR:'0',
				FR:'0',
				INST_TOTAL_QTY:'0',
				CUR_MONTH_RQTY:'0',
				CUR_MONTH_FQTY:'0',
				FAULT_TOTAL_QTY:'0',
				SUPPLY_NUM:'0',
				MODEL_NUM:'0',
				BATCH_NUM:'0'
			}/*顶端指标数据*/
			,dhpcgzList:[]/*到货批次故障列表数据*/
			,healthWeightData:[]/*健康度影响因素权重分布数据*/
		},
		computed:{
			getCurDate: function() {
				return utils.dateformat_long2string(this.curTime,'yyyy年MM月dd日 EEE');
			}
			,getCurTime: function(){
				return utils.dateformat_long2string(this.curTime,'HH:mm:ss');
			}
			,bigdatamap: function(){
				return new BigDataMap2Pie($('#provmap')[0],'bd_hunan_map');
			},
			bdFrHrConsistency:function(){return new BdFrHrConsistency($("#bdFrHrConsistency")[0],this.tagNos.bdFrHrConsistency);},
			bdHrRank:function(){return new BdHrRank($("#bdHrRank")[0],this.tagNos.bdHrRank);},
			bdFrRank:function(){return new BdFrRank($("#bdFrRank")[0],this.tagNos.bdFrRank);},
			gzlxfb:function(){return new BdMalfunctionDistribute($("#gzlxfb")[0],this.tagNos.gzlxfb);},
			gzlsbhqk_yxny:function(){return new BdMalfunctionHistoryStat($("#gzlsbhqk_yxny")[0],this.tagNos.gzlsbhqk_yxny);},
			gzlsbhqk_yxsc:function(){return new BdMalfunctionHistoryStat($("#gzlsbhqk_yxsc")[0],this.tagNos.gzlsbhqk_yxsc);},
			listDataGetter:function(){return new ListDataGetter();},
			predRate:function(){return new PredRate($("#predRate")[0],this.tagNos.predRate);},
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
			this.$nextTick(function () {
				setInterval(this.timer,1000);
			});
			hunanBig.addObserve(this.bigdatamap);
			hunanBig.addObserve(this.bdFrHrConsistency);
			hunanBig.addObserve(this.bdHrRank);
			hunanBig.addObserve(this.bdFrRank);
			hunanBig.addObserve(this.gzlxfb);
			hunanBig.addObserve(this.gzlsbhqk_yxny);
			hunanBig.addObserve(this.gzlsbhqk_yxsc);
			hunanBig.addObserve(this.predRate);
		},
		methods:{
			initChart:function(){
				this.globalParam = GlobalParma;
				hunanBig.showChart();
				this.getTopInfo();
				this.getDhpcgzList();
				this.getHealthWeightData();
			}
			,timer: function(){
				this.curTime = parseInt(this.curTime) + 1000;
			}
			,getTopInfo:function(){
				var month = this.globalParam.queryMonth;
				if(month<10) month='0'+month;
				var topParam1={/*顶端数据获取参数*/
					"_TAGNO":"bd_top_1",
					ym:this.globalParam.queryYear+""+month,
					provinceNo:this.globalParam.provinceNo
				};
				var topParam2={/*顶端数据获取参数*/
					"_TAGNO":"bd_top_2",
					year:this.globalParam.queryYear,
					month:this.globalParam.queryMonth,
					provinceNo:this.globalParam.provinceNo
				};
				var t_this = this;
				this.listDataGetter.getDatas(this.globalParam.dataUrl,topParam1,function(rslt){
					var rsltList = rslt.data;
					var topData = t_this.topInfo;
					if(rsltList!=null &&rsltList.length>0){
						topData['SUPPLY_NUM'] = String(rsltList[0]['SUPPLY_NUM']).split('');
						topData['MODEL_NUM'] = String(rsltList[0]['MODEL_NUM']).split('');
						topData['BATCH_NUM'] = String(rsltList[0]['BATCH_NUM']).split('');
					}
					t_this.topInfo = topData;
				});
				this.listDataGetter.getDatas(this.globalParam.dataUrl,topParam2,function(rslt){
					var rsltList = rslt.data;
					var topData = t_this.topInfo;
					if(rsltList!=null &&rsltList.length>0){
						topData['INST_TOTAL_QTY'] = String(rsltList[0]['INST_TOTAL_QTY']).split('');
						topData['CUR_MONTH_RQTY'] = String(rsltList[0]['CUR_MONTH_RQTY']).split('');
						topData['CUR_MONTH_FQTY'] = String(rsltList[0]['CUR_MONTH_FQTY']).split('');
						topData['FAULT_TOTAL_QTY'] = String(rsltList[0]['FAULT_TOTAL_QTY']).split('');
						topData['HR'] = String(rsltList[0]['HR']).split('');
						topData['FR'] = String(rsltList[0]['FR']).split('');
					}
					t_this.topInfo = topData;
				});
			}
			,getDhpcgzList:function(){/*到货批次故障列表数据*/
				var month = this.globalParam.queryMonth;
				if(month<10) month='0'+month;
				var tagParam={
						"_TAGNO":"bd_dhpcgz_list",
						date:this.globalParam.queryYear+"-"+month
					};
				var t_this = this;
				this.listDataGetter.getDatas(this.globalParam.dataUrl,tagParam,function(rslt){
					if(rslt && rslt.data) {
						var rsltList = rslt.data;
						t_this.dhpcgzList = rsltList;
					}
				});
			}
			,getHealthWeightData:function(){/*健康度影响因素权重分布*/
				var tagParam={
						"_TAGNO":"healthWeight"
					};
				var t_this = this;
				this.listDataGetter.getDatas(this.globalParam.dataUrl,tagParam,function(rslt){
					if(rslt && rslt.data) {
						var rsltList = rslt.data;
						var datas = [];
						var others = {NAME:'其他因素',VALUE:0};
						for(var i=0;i<rsltList.length;i++){
							if(i<2){
								datas.push(rsltList[i]);
							}else{
								others.VALUE += rsltList[i].VALUE;
							}
						}
						if(datas.length<3){
							datas.push(others);
						}
						t_this.healthWeightData = datas;
					}
				});
			}
			,objmouseover: function(e, titledata){
				var myTitle = titledata;
		        if(!myTitle)
		        	return;
		        var tooltip = "<div id='tooltip_top' style='position:absolute;border:0px solid #e6e6e6;background:#000;padding:5px;color:#e6e6e6;display:none;font-size: 22px;z-index:9999;white-space:nowrap;'>"+ myTitle +"<\/div>"; //创建 div 元素 文字提示
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
	vmContent.initChart();
</script>
</html>