//背景图层
var Background = (function(_super){
    function Background1(){
        Background1.super(this);
        //创建游戏背景
        this.bg = new Laya.Sprite();
        //加载并显示背景图
        this.bg.loadImage("comp/gameback.png");
        //加载到容器中
        this.bg.zOrder = 100;
        this.addChild(this.bg);
        
        
    }

    function Background2(){
        Background2.super(this);
        this.bg2 = new Laya.Sprite();
        //加载并显示背景图
        this.bg2.loadImage("comp/blueback.png");
        //加载到容器中
        this.bg2.x=4;
        this.bg2.y=40;
        this.bg2.zOrder = 10;
        this.addChild(this.bg2);
    }


    //注册类
    Laya.class(Background1,"Background1", _super);
    Laya.class(Background2,"Background2", _super);
   
    return Background;
})(Laya.Sprite);