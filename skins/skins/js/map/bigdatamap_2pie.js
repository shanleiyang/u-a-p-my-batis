//bigdatamapDom:地图dom ，_tagNo：地图数据sql编号,mapType:地图类型（省市编码）；
var BigDataMap2Pie = function(bigdatamapDom,_tagNo){
	this._tagNo = _tagNo;
	this.bigdatamapDom = bigdatamapDom;
	this.mapOption;
	this.mapType;
};
BigDataMap2Pie.prototype ={
		mapOptionFunc : function(_mapType) {
				return {
					series : [
						{
							name: 'BigDataMap2Pie',
							type: 'map',
							left: 40, top: 40, right: 40, bottom: 40,
//							roam: true,
							mapType: _mapType,
							zlevel: 3,
							label: {
								show: true,
								fontSize:17,
								fontFamily:'方正兰亭黑简体',
								color:'#fff'
							},
							itemStyle: {
								borderColor:'#55AEDD',
								areaColor: '#000'
							},
							emphasis:{
								label: {
									show: true,
									color:'#fff'
								},
								itemStyle: {
									areaColor: '#000'//鼠标悬浮变色
								}
							},
							data:[]
//							,nameMap:eval("map_label_"+GlobalParma.provinceNo)
						}
					]
				}
			},
			labelformatter:function(params){//转换地图名称label
				return eval("map_label_"+GlobalParma.provinceNo)[params.name];
			},
			convertMapDta : function(listData){
				//健康度环状图series
				var jiankangPieSeries = [];
				//故障率环图
				var guzhangPieSeries = [];
				var geoCoordMap = eval("geoCoordMap_"+GlobalParma.provinceNo);//43101网省单位
				var geoCoordMap_guzhang = eval("geoCoordMap_GuZhang_"+GlobalParma.provinceNo);//43101网省单位_故障率
				for(var m in listData){
					//健康度环状图Series
					var jiankangPieSerie = {
						                 radius: ["22","24" ],
						                 clockWise: true,
						                 hoverAnimation: false,
						                 avoidLabelOverlap:false,
						                 type: "pie",
						                 center: "",
						                 zlevel: 4,
						                 org_no:"",
						                 startAngle:90,
						                 label: {
						                     normal: {
						                         show: true,
						                         position: 'center',
						                         fontSize: 18,
						                         fontFamily:"Agency FB",
				                                 fontWeight: "bold"
						                         
						                     }
						                 },
						                 itemStyle: {
						                     normal: {
						                         color: "rgb(246,171,0)",
						                         borderColor: "rgb(246,171,0)",
						                         borderWidth: 6
						                     }
						                 },
						                 data: []
						     };
					//故障率环状图Series2
					var guzhangPieSerie = {
						                 radius: ["22","24" ],
						                 clockWise: true,
						                 hoverAnimation: false,
						                 avoidLabelOverlap:false,
						                 type: "pie",
						                 center: "",
						                 zlevel: 4,
						                 org_no:"",
						                 startAngle:90,
						                 label: {
						                     normal: {
						                         show: true,
						                         position: 'center',
						                         fontSize: 18,
						                         fontFamily:"Agency FB",
				                                 fontWeight: "bold"
						                         
						                     }
						                 },
						                 itemStyle: {
						                     normal: {
						                         color: "rgb(0,137,116)",
						                         borderColor: "rgb(0,137,116)",
						                         borderWidth: 6
						                     }
						                 },
						                 data: []
						     };
					//地图数据
					var mapData ={
							name:eval("map_label_"+GlobalParma.provinceNo)[listData[m].NAME],//网省（或区县）编码
							value:listData[m].HR_VAL,//健康度
							click_org_no:listData[m].NAME,
							itemStyle: {
								areaColor: '#010D40',
								color:'#010D40'
							},
							emphasis:{
								label: {
									show: true,
									color:'#fff'
								},
								itemStyle: {
									areaColor: 'rgb(0,157,133)'//鼠标悬浮变色
								}
							},
							label:{
//									formatter:this.labelformatter
								}
					};
					//健康度环形图数据
					var jiankangCircleData ={value:listData[m].HR_VAL,name: listData[m].HR_VAL};
					//故障率环形图数据
					var guzhangCircleData ={value:listData[m].FR_VAL,name: listData[m].FR_VAL};
					//健康度默认环形数据
					var jiankangDefcircleData = {
						 value:100-Number(listData[m].HR_VAL),
	                     name: " ",
	                     itemStyle: {
	                         normal: {
	                              borderWidth: 0
	                         }
	                     }
					};
					//故障率默认环形数据
					var guzhangDefcircleData = {
						 value:100-Number(listData[m].FR_VAL),
	                     name: " ",
	                     itemStyle: {
	                         normal: {
	                              borderWidth: 0
	                         }
	                     }
					};
					//健康度环图
					jiankangPieSerie.data =[]; 
					jiankangPieSerie.org_no=listData[m].NAME;
					jiankangPieSerie.data.push(jiankangCircleData);
					jiankangPieSerie.data.push(jiankangDefcircleData);
					//故障率环图
					guzhangPieSerie.data =[]; 
					guzhangPieSerie.org_no=listData[m].NAME;
					guzhangPieSerie.data.push(guzhangCircleData);
					guzhangPieSerie.data.push(guzhangDefcircleData);
					if(typeof(geoCoordMap[jiankangPieSerie.org_no])!="undefined"){
						this.mapOption.series[0].data.push(mapData);
						jiankangPieSeries.push(jiankangPieSerie);
						guzhangPieSeries.push(guzhangPieSerie);
					}
				}
				//
				this.mapChart.setOption(this.mapOption,true);
				for(var i=0;i<jiankangPieSeries.length;i++){
					//环状图经纬度坐标  geoCoordMap_43101
					jiankangPieSeries[i].center = this.mapChart.convertToPixel({seriesIndex: 0},geoCoordMap[jiankangPieSeries[i].org_no]);
					guzhangPieSeries[i].center = this.mapChart.convertToPixel({seriesIndex: 0},geoCoordMap_guzhang[guzhangPieSeries[i].org_no]);
				}
				//合并地图和健康度环状图Series
				$.merge(this.mapOption.series,jiankangPieSeries );
				//合并地图和故障率环状图Series
				$.merge(this.mapOption.series,guzhangPieSeries );
			},
			resetPie:function (myChart, params) {
				geoCoordMap = eval("geoCoordMap_"+GlobalParma.provinceNo);//43101网省单位
			    var op = myChart.getOption();
			    var ops = op.series;
			    ops.forEach(function(v, i) {
			        if (i > 0) {
			            var geoCoord = geoCoordMap[v.org_no];
			            var p = myChart.convertToPixel({
			                seriesIndex: 0
			            }, geoCoord);
			            v.center = p;
			        }
			    });
			    myChart.setOption(op, true);
			},
			showChart:function(){//显示地图
				if(this.mapChart && this.mapChart.dispose){
					this.mapChart.dispose();
		    	}
				var map_path= GlobalParma.mapPath;
				this.mapType = GlobalParma.mapType;
				var that = this;
				$.ajax({
				    cache: true,
				    url: map_path,
				    dataType: 'script', // optional, can omit if your server returns proper contentType for js files.
				    success: function () {
						//后台取数
						var url = GlobalParma.dataUrl;//context+"/rest/bigdata/dataProvider/getData";
						var jsonData = {"_TAGNO":that._tagNo,"year":GlobalParma.queryYear,"month":GlobalParma.queryMonth,"citycode":GlobalParma.cityCode};
						var queryData = [];
						$.fn.postSubmit(url,JSON.stringify(jsonData), function(data){
							if(data.success){
								that.mapChart = echarts.init(that.bigdatamapDom);
								queryData = data.data;
								that.mapOption = that.mapOptionFunc(that.mapType);
								that.convertMapDta(queryData);
								that.mapChart.setOption(that.mapOption);
							}else{
								$.dialog({
									 type:"alert",
									 content:data.msg,
									 autofocus: true
								 });
							}
						});
				    }
				});
			}
};
