// 菜单渲染
var menuBarVm = new Vue({
	el: '#menubar',
	data: {
		menuList: [],
		toFirstId: '', // 一级菜单选中id
		toSecondId: '', // 二级菜单选中id
		toThreeId: '', // 三级菜单选中id
	},
	created: function(){
		var that = this;
		jQuery.ajax({
			type: 'POST',
			url: context + '/rest/manage/menu/getMenus',
			success: function(resData){
				that.menuList = resData;
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
			    console.log(textStatus);
			}
		});
	},
	methods: {
		clickMenu: function(item, firstid, secondid, threeid){
			if(item['url']) {
				mainFrame.src = item['url'];
				if(firstid) {
					this.toFirstId = firstid;
					if(secondid) {
						this.toSecondId = secondid;
						if(threeid) {
							this.toThreeId = threeid;
						}
					}
				}
			}
		}
	}
});

var mainFrame = document.getElementById('mainFrame');
