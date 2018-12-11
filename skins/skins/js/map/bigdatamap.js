//bigdatamapDom:地图dom ，_tagNo：地图数据sql编号,mapType:地图类型（省市编码）；
var BigDataMap = function(bigdatamapDom,_tagNo){
	this._tagNo = _tagNo;
	this.bigdatamapDom = bigdatamapDom;
	this.mapOption;
	this.mapType;
};
BigDataMap.prototype ={
		mapOptionFunc : function(_mapType) {
				return {
					series : [
						{
							name: 'BigDataMap',
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
				//所有环状图series
				var pieSeries = [];
				var geoCoordMap = eval("geoCoordMap_"+GlobalParma.provinceNo);//43101网省单位
				for(var m in listData){
					//环状图Series
					var pieSerie = {
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
					//地图数据
					var mapData ={
							name:eval("map_label_"+GlobalParma.provinceNo)[listData[m].NAME],//网省（或区县）编码
							value:listData[m].VALUE,//健康度（或故障率）
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
					//环形图数据
					var circleData ={value:mapData.value,name: mapData.value};
					//默认环形数据
					var defcircleData = {
						 value:100-Number(mapData.value),
	                     name: " ",
	                     itemStyle: {
	                         normal: {
	                              borderWidth: 0
	                         }
	                     }
					};
					pieSerie.data =[]; 
					pieSerie.org_no=listData[m].NAME;
					pieSerie.data.push(circleData);
					pieSerie.data.push(defcircleData);
					if(typeof(geoCoordMap[pieSerie.org_no])!="undefined"){
						this.mapOption.series[0].data.push(mapData);
						pieSeries.push(pieSerie);
					}
				}
				this.mapChart.setOption(this.mapOption,true);
				for(var i=0;i<pieSeries.length;i++){
					//环状图经纬度坐标  geoCoordMap_43101
					pieSeries[i].center = this.mapChart.convertToPixel({seriesIndex: 0},geoCoordMap[pieSeries[i].org_no]);
				}
				//合并地图和所有环状图Series
				$.merge(this.mapOption.series,pieSeries );
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
								//绑定点击事件,获取点击的地市
								that.mapChart.on('click', function(params){
									var clickOrgNo = typeof(params.data)!="undefined"?params.data.click_org_no:"";
									if(clickOrgNo!=""&&typeof(clickOrgNo)!="undefined")
										changeByMap(clickOrgNo);
							     });
//								that.mapChart.on('georoam', function(params) {
//									that.resetPie(that.mapChart, params);
//								});
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
