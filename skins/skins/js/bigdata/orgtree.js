(function (jQuery){
	var setting = {
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "all"
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onCheck: function(event, treeId, treeNode){
					
				}
			}
		};
	var zNodes =[];
	jQuery.fn.extend({
		orgTree : function(treevalue, treename){
			var t_this = this;
			// 单位编号对象
			var treevalueObj = jQuery('#'+treevalue);
			// 单位名称对象
			var treenameObj = jQuery('#'+treename);
			var curId = $(t_this).prop("id");
			jQuery(document).click(function(){
				$(t_this).hide();
			});
			treenameObj.click(function(e){
				$(t_this).toggle();
				e.stopPropagation();
			});
			jQuery(t_this).click(function(e){
				e.stopPropagation();
			});
			var t_setting = {
					callback: {
						onCheck: function(event, treeId, treeNode){
							treevalueObj.val(treeNode.id);
							treevalueObj.attr('orgtype', treeNode.orgtype);
							treenameObj.val(treeNode.name);
						}
					}
			};
			jQuery.extend(setting, t_setting);
			jQuery.fn.postSubmit(context+'/../home/rest/bigdata/dataProvider/getData', "{\"_TAGNO\":\"_org_tree\"}", function(rslt){
				zNodes = [];
				var tmp_json = {};
				if(rslt && rslt.data) {
					var rsData = rslt.data;
					for(var i in rsData) {
						if(!tmp_json[rsData[i]['PROVINCE_CODE']]) {
							zNodes.push({id: rsData[i]['PROVINCE_CODE'], pId:0, name:rsData[i]['PROVINCE_NAME'], orgtype: 1, open:true});
							tmp_json[rsData[i]['PROVINCE_CODE']] = rsData[i]['PROVINCE_NAME'];
						}
						if(!tmp_json[rsData[i]['CITY_CODE']]) {
							zNodes.push({id: rsData[i]['CITY_CODE'], pId:rsData[i]['PROVINCE_CODE'], name:rsData[i]['CITY_NAME'], orgtype: 2});
							tmp_json[rsData[i]['CITY_CODE']] = rsData[i]['CITY_NAME'];
						}
						zNodes.push({id: rsData[i]['COUNTY_CODE'], pId:rsData[i]['CITY_CODE'], name:rsData[i]['COUNTY_NAME'], orgtype: 3});
						tmp_json[rsData[i]['COUNTY_CODE']] = rsData[i]['COUNTY_NAME'];
					}
				}
				// 初始化时对单位编号进行字典翻译
				if(treevalueObj.val()) {
					treenameObj.val(tmp_json[treevalueObj.val()]);
				}
				jQuery.fn.zTree.init($("#"+curId), setting, zNodes);
			});
		}
	});
})(jQuery);