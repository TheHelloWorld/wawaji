var CLASS$=Laya.class;
var STATICATTR$=Laya.static;
var View=laya.ui.View;
var Dialog=laya.ui.Dialog;
var showMsgFailUI=(function(_super){
		function showMsgFailUI(){
			
		    this.playAgain=null;
		    this.noPlay=null;

			showMsgFailUI.__super.call(this);
		}

		CLASS$(showMsgFailUI,'ui.showMsgFailUI',_super);
		var __proto__=showMsgFailUI.prototype;
		__proto__.createChildren=function(){
		    
			laya.ui.Component.prototype.createChildren.call(this);
			this.createView(showMsgFailUI.uiView);

		}

		showMsgFailUI.uiView={"type":"View","props":{"width":753,"height":1092},"child":[{"type":"Image","props":{"y":95,"x":230,"width":306,"skin":"comp/ku.png","height":402}},{"type":"Label","props":{"y":560,"x":158,"width":483,"text":"蓝瘦~香菇！","height":103,"fontSize":50,"color":"#ffffff","align":"center"}},{"type":"Label","props":{"y":680,"x":217,"width":330,"var":"playAgain","text":"再来一次（10）","height":57,"fontSize":45,"color":"#fdfdfd","bold":true,"bgColor":"#ff3968","align":"center"}},{"type":"Label","props":{"y":799,"x":217,"width":330,"var":"noPlay","text":"看一会","height":57,"fontSize":45,"color":"#ffffff","bold":true,"bgColor":"#ff3968","align":"center"}}]};
		return showMsgFailUI;
	})(View);