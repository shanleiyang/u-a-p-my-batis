<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.nrjh.framework.util.BspContextFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/tags/web-ui" prefix="uap"%>
<!DOCTYPE html>
<html lang="en">
<!-- BEGIN HEAD -->
<head>
<meta http-equiv="Content-Type" charset="utf-8" />
<title>到货批次详情</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<%
String provinceCode = BspContextFactory.getComCode();
Calendar calendar = Calendar.getInstance();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
String date = sdf.format(calendar.getTime());
int year = calendar.get(Calendar.YEAR);
int month = calendar.get(Calendar.MONTH)+1;
String cityCode = request.getParameter("cityCode");
cityCode = cityCode==null?"":cityCode;
String countyCode = request.getParameter("countyCode");
countyCode = countyCode==null?"":countyCode;
%>
<script type="text/javascript">
var context="<%=request.getContextPath()%>";
var GlobalParma={
		provinceNo:'<%=provinceCode%>',
		cityCode:'<%=cityCode%>',//第一次初始化时，页面加载完成后获取第一个单位
		countyCode:'<%=countyCode%>',
		date:'<%=date%>',
		queryYear:'<%=year%>',
		queryMonth:'<%=month%>',
		arriveBatchNo:'',/*到货批次号*/
		arriveDateBegin:'',/*到货日期起*/
		arriveDateEnd:'',/*到货日期止*/
		supplyName:'',/*供应商*/
		modelName:'',/*型号*/
		hrMin:'',
		hrMax:'',
		dataUrl:context+"/rest/bigdata/dataProvider/getData"
};
function query(){
	GlobalParma.date = $('#faultYm').val();
	GlobalParma.queryYear = parseInt(GlobalParma.date.split("-")[0]);
	GlobalParma.queryMonth = parseInt(GlobalParma.date.split("-")[1]);
	GlobalParma.arriveBatchNo = $("#arriveBatchNo").val();
	GlobalParma.arriveDateBegin = $("#arriveDateBegin").val();
	GlobalParma.arriveDateEnd = $("#arriveDateEnd").val();
	GlobalParma.supplyName = $("#supplyName").val();
	GlobalParma.modelName = $("#modelName").val();
	GlobalParma.hrMin = $("#hrMin").val();
	GlobalParma.hrMax = $("#hrMax").val();
	vm.initChart();
}
/*到货批次钻取到到货批次详情页面*/
function gotoDHPCXQ(batchNo,orgNo){
	var year = GlobalParma.queryYear;
	var month = GlobalParma.queryMonth;
	var provinceNo = GlobalParma.provinceNo;
	window.parent.changeByChildren("/home/bigdata/showpage/daohuopc/daohuopicixq.jsp");
	window.location.href = context+"/bigdata/showpage/daohuopc/daohuopicixq.jsp?provinceNo="+provinceNo+"&year="+year+"&month="+month+"&bidBatchNo="+batchNo+"&orgNo="+orgNo;
}
</script>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<uap:asset path="font-awesome.css,form.css" />
<uap:mediacss path="home/style.css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<uap:asset path="jquery.js"/>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>
	<div class="main" id="totalPageDiv">
		<div class="filter_box"><b>筛选条件</b>	
			<span class="main_box input_zhcx">
				  <input type="text"  class="select_box input_zhcx" id="faultYm" value="<%=date%>" readonly="readonly" placeholder="故障年月"/> 
			</span>
			<span class="main_box input_zhcx">
				<input type="text" name="arriveBatchNo" id="arriveBatchNo" value="" class="select_box input_zhcx" placeholder="到货批次号">		
			</span>		
			<span class="main_box input_zhcx">
				  <input type="text"  class="select_box input_zhcx" id="arriveDateBegin" value="" readonly="readonly" placeholder="到货日期起"/> 
			</span>
			<span class="main_box input_zhcx">
				  <input type="text"  class="select_box input_zhcx" id="arriveDateEnd" value="" readonly="readonly" placeholder="到货日期止"/> 
			</span>
			<span class="main_box input_zhcx">
			<input type="text" name="supplyName" id="supplyName" value="" class="select_box input_zhcx" placeholder="供应商">		
			</span>
			<span class="main_box input_zhcx">
			<input type="text" name="modelName" id="modelName" value="" class="select_box input_zhcx" placeholder="型号">	
			</span>
			<span class="main_box input_zhcx">
			<input type="text" name="hrMin" id="hrMin" value="" class="select_box input_zhcx" placeholder="最小健康度">	
			</span>
			<span class="main_box input_zhcx">
			<input type="text" name="hrMax" id="hrMax" value="" class="select_box input_zhcx" placeholder="最大健康度">	
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
		
		<div class="dhpccx_con">
			<div class="c_l">			
				<div class="dhpc_left">
					<div class="dh_con">							
						<h2>到货批次健康评价<!-- <b><a href="#">导出</a></b> --></h2>
						<ul class="dhbcon dhpczht"><li><span>到货批次号</span><span>供应商</span><span>型号</span><span>管理单位</span><span>累计<br />安装数</span><span>当月<br />运行数</span><span>当月<br />故障数</span><span>累计<br />故障数</span><span>当月<br />故障率</span><span>累计<br />故障率</span><span v-text="gzycMonth.month1+'月'">12月</span><span v-text="gzycMonth.month2+'月'">1月</span><span v-text="gzycMonth.month3+'月'">2月</span><b>故障率预测</b></li>	
						</ul>					
						<div>
							<ul class="dhablelist dhpczhlist">
								<li v-for="data in zhcxList"><span class="text_left" @mouseover="objmouseover($event,data.arriveBatchNo)" @mouseout="objmouseout" @mousemove="objmousemove" v-html="data.arriveBatchNo"></span> <span class="text_left" @mouseover="objmouseover($event,data.SUPLLIER_NAME)" @mouseout="objmouseout" @mousemove="objmousemove" v-text="data.SUPLLIER_NAME||'-'"></span> <span class="text_left" @mouseover="objmouseover($event,data.MODEL_NAME)" @mouseout="objmouseout" @mousemove="objmousemove" v-text="data.MODEL_NAME||'-'"></span> <span class="text_left" @mouseover="objmouseover($event,data.ORG_NAME)" @mouseout="objmouseout" @mousemove="objmousemove" v-text="data.ORG_NAME||'-'"></span> <span class="number_right" v-text="data.INST_TOTAL_QTY||'-'"></span> <span class="number_right" v-text="data.CUR_MONTH_RQTY"></span> <span class="number_right" v-text="data.CUR_MONTH_FQTY||'-'"></span> <span class="number_right" v-text="data.FAULT_TOTAL_QTY||'-'"></span> <span class="number_right" v-text="data.FR||'-'"></span> <span class="number_right" v-text="data.CFR||'-'"></span> <span class="number_right" v-text="data.VALUE1||'-'"></span> <span class="number_right" v-text="data.VALUE2||'-'"></span> <span class="number_right" v-text="data.VALUE3||'-'"></span></li>
							</ul>								
							<p class="pbtn"><a href="javascript:void();" v-on:click="nextPage()">下一页</a><a href="javascript:void();" v-on:click="prePage()">上一页</a></p>
						</div>
					</div>
				</div>						
		</div>			
	</div>	
	<!-- END CONTAINER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<uap:asset path="vue.min.js,form.js,ajaxsubmit.js,dateCascade.js" />
	<script  type="text/javascript" src="<uap:ui js='bigdata/listDataGetter.js'/>"></script>
	<!--[if lt IE 9]>
	<script src="<uap:ui js='excanvas.min.js'/>"></script>
	<script src="<uap:ui js='respond.min.js'/>"></script>  
	<![endif]-->
	<script type="text/javascript">	
var vm = new Vue({
	el:'#totalPageDiv',
	data:{
		globalParam:GlobalParma,/*公共参数*/
		listPage:{
			pageStart:1,/*页面第一条记录索引*/
			pageEnd:8,/*页面最后一条记录索引*/
			pageSize:8,/*页面条数*/
			pageIndex:1,/*页面索引，第几页*/
			pageCount:0/*页面实际记录条数*/
		},
		topInfo:{
			INST_TOTAL_QTY:'0',
			CUR_MONTH_RQTY:'0',
			CUR_MONTH_FQTY:'0',
			FAULT_TOTAL_QTY:'0',
			SUPPLY_NUM:'0',
			MODEL_NUM:'0',
			BATCH_NUM:'0'
			},/*顶端指标数据*/
		zhcxList: []/*到货批次健康评价列表数据*/
	},
	computed:{
		listDataGetter:function(){return new ListDataGetter();},
		gzycMonth:function(){/*获取故障预测年月*/
			var month1,month2,month3;/*分别为下月，第二月，第三月*/
			var queryMonthInt = parseInt(this.globalParam.date.split('-')[1]);
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
	methods:{
		initChart:function(){
			this.globalParam = GlobalParma;
			this.listPage = {
				pageStart:1,/*页面第一条记录索引*/
				pageEnd:8,/*页面最后一条记录索引*/
				pageSize:8,/*页面条数*/
				pageIndex:1,/*页面索引，第几页*/
				pageCount:0/*页面实际记录条数*/
			};
			this.getTopInfo();
			this.getZhcxList();
		},
		nextPage:function(){/*下一页*/
			if(this.listPage.pageCount==this.listPage.pageSize){
				this.listPage.pageIndex ++;
				this.listPage.pageStart +=  this.listPage.pageSize;
				this.listPage.pageEnd += this.listPage.pageSize;
				this.getZhcxList();
			}
		},
		prePage:function(){/*上一页*/
			if(this.listPage.pageIndex>1){
				this.listPage.pageIndex --;
				this.listPage.pageStart -=  this.listPage.pageSize;
				this.listPage.pageEnd -= this.listPage.pageSize;
				this.getZhcxList();
			}
		},
		getZhcxList:function(){
			var month = this.globalParam.queryMonth;
			if(month<10) month='0'+month;
			var zhcxListParam={
				"_TAGNO":"dhpczhcx_list",
				date:this.globalParam.date,
				arriveBatchNo:this.globalParam.arriveBatchNo,/*到货批次号*/
				arriveDateBegin:this.globalParam.arriveDateBegin ,/*到货日期起*/
				arriveDateEnd:this.globalParam.arriveDateEnd ,/*到货日期止*/
				supplyName:this.globalParam.supplyName,/*供应商*/
				modelName:this.globalParam.modelName,/*型号*/
				hrMin:this.globalParam.hrMin,
				hrMax:this.globalParam.hrMax,
				pageStart:this.listPage.pageStart,/*页面第一条记录索引*/
				pageEnd:this.listPage.pageEnd/*页面最后一条记录索引*/
			};
			var t_this = this;
			this.listDataGetter.getDatas(this.globalParam.dataUrl,zhcxListParam,function(rslt){
				var rsltList = rslt.data;
				t_this.listPage.pageCount = rsltList.length;
				if(rsltList!=null&&rsltList.length>0){
					for(var i=0;i<rsltList.length;i++){
						rsltList[i].arriveBatchNo = "<a herf='#' class='table_a_link' onclick='gotoDHPCXQ(\""+rsltList[i].ARRIVE_BATCH_NO+"\",\""+rsltList[i].ORG_CODE+"\")'>"+rsltList[i].ARRIVE_BATCH_NO+"</a>";
					}
					
				}
				t_this.zhcxList = rsltList;
			});
		},
		getTopInfo:function(){
			var topParam1={/*顶端数据获取参数*/
				"_TAGNO":"dhpczccx_top_1",
				date:this.globalParam.date,
				arriveBatchNo:this.globalParam.arriveBatchNo,/*到货批次号*/
				arriveDateBegin:this.globalParam.arriveDateBegin ,/*到货日期起*/
				arriveDateEnd:this.globalParam.arriveDateEnd ,/*到货日期止*/
				supplyName:this.globalParam.supplyName,/*供应商*/
				modelName:this.globalParam.modelName,/*型号*/
				hrMin:this.globalParam.hrMin,
				hrMax:this.globalParam.hrMax
			};
			var month = this.globalParam.queryMonth;
			if(month<10) month='0'+month;
			var topParam2={/*顶端数据获取参数*/
				"_TAGNO":"dhpczhcx_top_2",
				ym:this.globalParam.date.replace('-',''),
				arriveBatchNo:this.globalParam.arriveBatchNo,/*到货批次号*/
				arriveDateBegin:this.globalParam.arriveDateBegin ,/*到货日期起*/
				arriveDateEnd:this.globalParam.arriveDateEnd ,/*到货日期止*/
				supplyName:this.globalParam.supplyName,/*供应商*/
				modelName:this.globalParam.modelName,/*型号*/
				hrMin:this.globalParam.hrMin,
				hrMax:this.globalParam.hrMax
			};
			var t_this = this;
			this.listDataGetter.getDatas(this.globalParam.dataUrl,topParam1,function(rslt){
				var rsltList = rslt.data;
				var topData = t_this.topInfo;
				if(rsltList!=null &&rsltList.length>0){
					topData['INST_TOTAL_QTY']=String(rsltList[0]['INST_TOTAL_QTY']).split('');
					topData['CUR_MONTH_RQTY']=String(rsltList[0]['CUR_MONTH_RQTY']).split('');
					topData['CUR_MONTH_FQTY']=String(rsltList[0]['CUR_MONTH_FQTY']).split('');
					topData['FAULT_TOTAL_QTY']=String(rsltList[0]['FAULT_TOTAL_QTY']).split('');
				}
				t_this.topInfo = topData;
			});
			this.listDataGetter.getDatas(this.globalParam.dataUrl,topParam2,function(rslt){
				var rsltList = rslt.data;
				var topData = t_this.topInfo;
				if(rsltList!=null &&rsltList.length>0){
					topData['SUPPLY_NUM']=String(rsltList[0]['SUPPLY_NUM']).split('');
					topData['MODEL_NUM']=String(rsltList[0]['MODEL_NUM']).split('');
					topData['BATCH_NUM']=String(rsltList[0]['BATCH_NUM']).split('');
				}
				t_this.topInfo = topData;
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
initDateTimePicker('#arriveDateBegin','#arriveDateEnd','yyyy-mm-dd',2);
vm.initChart();
$('#faultYm').datetimepicker({
	language: "zh-CN",
	autoclose : true,
	todayHighlight : true,
	endDate : new Date(),
	format : 'yyyy-mm',
	startView : 3,
	minView : 4,
})
</script>
</body>
<!-- END BODY -->
</html>