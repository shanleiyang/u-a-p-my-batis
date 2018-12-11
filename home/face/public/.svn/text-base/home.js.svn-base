// 菜单渲染
var menuBarVm = new Vue({
	el: '#mycontent',
	data: {
		menuList: [],
		toFirstId: '', // 一级菜单选中id
		selectUrl:''//根据url判断当前选中的菜单
	},
	created: function(){
		var that = this;
		jQuery.ajax({
			type: 'POST',
			url: context + '/rest/manage/menu/getMenus',
			success: function(resData){
				if(resData && resData.length > 0) {
					that.menuList = resData;
					that.toFirstId = resData[0].id;
					jQuery('#mainFrame').attr('src', context + '/' + resData[0].url);
				}
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
			    console.log(textStatus);
			}
		});
	},
	methods: {
		clickMenu: function(item, firstid){
			if(item['url']) {
				jQuery('#mainFrame').attr('src', context + '/' + item['url']);
				this.toFirstId = firstid;
				this.selectUrl = '';
			}
		},
		logout: function(){
			if(!confirm("确定要退出吗?")) 
		  	{
		   	 	return false;
		  	}
			var params = {params : {userName : username}};
			jQuery.ajax({
				url: context + "/../iscintegrate/rest/common/loginuser/loginout",
				type:"POST",
				timeout:60000,
				contentType: "application/json",
		        dataType: "json",
				async:false,
				cache:false,
				data:params,
				success:function(p_context){
					window.location.href = iscLoginUrl + "/../logout?service="+serverUrl+"/..";
				},
				error:function(p_error){
					alert("系统注销失败!请与管理员联系!"+p_error.responseText);
				}
			});
		}
	}
});
/**提供给子页面调用，触发菜单选中**/
function changeByChildren(menuUrl){
	menuBarVm.toFirstId='';
	menuBarVm.selectUrl = menuUrl;
}