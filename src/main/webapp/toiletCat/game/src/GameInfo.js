var GameInfo = (function(_super){
    function GameInfo(){
        GameInfo.super(this);
        this.playAgain.on(Laya.Event.CLICK, this, this.restartGame);
    }
    Laya.class(GameInfo,"GameInfo", _super);
    var _proto = GameInfo.prototype;
    _proto.showGameEnd = function(){
        
    }

    _proto.playAgainDjs = function(data){
        this.playAgain.text = "再来一次（"+data+"）";
    }

    _proto.restartGame = function(){
        startGame();
    }

    return GameInfo;
})(ui.GameInfoUI);